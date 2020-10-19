package org.egc.semantic.store.impl;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBException;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.base.file.Location;
import org.egc.semantic.store.TdbDataset;
import org.egc.semantic.util.OntFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TDB模型CRUD操作，包含事务
 *
 * @author houzhiwei
 * @date 2017/4/16 16:36
 * @date 2015年12月2日
 */
@Component
public class TdbDatasetImpl implements TdbDataset {
    final static Logger logger = LoggerFactory.getLogger(TdbDatasetImpl.class);

    /**
     * 必须close
     */
    Dataset ds = null;

    // needed for spring
    public TdbDatasetImpl() {
    }

    /**
     * 使用配置文件连接TDB ?
     *
     * @param assemblerFile assembler.ttl
     */
    public TdbDatasetImpl(File assemblerFile) {
        String file = Preconditions.checkNotNull(assemblerFile, "TDB assembler file can not be null!").getAbsolutePath();
        ds = TDBFactory.assembleDataset(file);
    }

    /**
     * Instantiates a new Tdb connection use tdb path.
     *
     * @param tdbPath the tdb path
     */
    public TdbDatasetImpl(String tdbPath) {
        Preconditions.checkNotNull(tdbPath, "TDB directory name can not be null!");
        Location location = Location.create(tdbPath);
        ds = TDBFactory.createDataset(location);
    }

    @Override
    public int loadModel(String modelName, String sourcePath, Boolean isOverride) {
        Model model = null;
        ds.begin(ReadWrite.WRITE);
        try {
            if (ds.containsNamedModel(modelName)) {
                if (isOverride){// 覆盖
                    removeModel(modelName);
                    loadModel(modelName, sourcePath, false);
                }
            } else {
                model = ds.getNamedModel(modelName);// 没有则创建一个，model不会为null
                model.begin();
                RDFDataMgr.read(model, sourcePath);
                model.commit();
            }
            // 已有，但是不覆盖，则直接返回
            ds.commit();
            logger.info("本体模型数据已经导入");
            logger.info("OntModel data loaded success!");
            return 1;
        } catch (Exception e) {
            return 0;
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
    }

    @Override
    public int loadOntModel(String modelName, String sourcePath, Boolean isOverride) {
        // 不支持事务
        OntModel model = ModelFactory.createOntologyModel();
        ds.begin(ReadWrite.WRITE);
        try {
            if (ds.containsNamedModel(modelName)) {
                // 覆盖
                if (isOverride) {
                    removeModel(modelName);
                    loadOntModel(modelName, sourcePath, false);
                }
            } else {
                model = OntFile.loadOntModelWithLocMapper(sourcePath);
                ds.addNamedModel(modelName, model);

            }
            // 已有，但是不覆盖，则直接返回
            ds.commit();
            System.out.println(modelName + " 已导入");
            logger.info(modelName + " 已导入");
            return 1;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            logger.error(e.getLocalizedMessage());
            return 0;
        } finally {
            ds.end();
        }
    }

    @Override
    public Model getDefaultModel() {
        ds.begin(ReadWrite.READ);
        Model model;
        try {
            model = ds.getDefaultModel();
            ds.commit();
        } finally {
            ds.end();
        }
        return model;
    }

    @Override
    public Model getModel(String modelName) {
        Model model = null;
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getNamedModel(modelName);
        } finally {
            ds.end();
        }
        return model;
    }

    @Override
    public void loadDefaultModel(String sourcePath) {
        Model model = null;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getDefaultModel();
            model.begin();
            if (!StringUtils.isBlank(sourcePath))
                RDFDataMgr.read(model, sourcePath);
            model.commit();
            ds.commit();
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
    }

    @Override
    public void removeModel(String modelName) {
        if (!ds.isInTransaction())
            ds.begin(ReadWrite.WRITE);
        try {
            ds.removeNamedModel(modelName);
            ds.commit();
            System.out.println(modelName + " 已被移除");
            logger.info(modelName + " 已被移除");
        } finally {
            ds.end();
        }
    }

    @Override
    public List<String> listModels() {
        ds.begin(ReadWrite.READ);
        List<String> uriList = new ArrayList<>();
        try {
            // DefaultModel没有name
            Iterator<String> names = ds.listNames();
            String name = null;
            while (names.hasNext()) {
                name = names.next();
                uriList.add(name);
            }
        } finally {
            ds.end();
        }
        return uriList;
    }

    /**
     * 必须关闭TDB连接
     */
    @Override
    public void close() {
        if (ds != null) {
            ds.close();
        }
    }

    @Override
    public Dataset getDataset() {
        if (ds == null) throw new TDBException("TDB not connected");
        return ds;
    }

}
