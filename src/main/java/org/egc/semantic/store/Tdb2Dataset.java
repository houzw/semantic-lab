package org.egc.semantic.store;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.dboe.base.file.Location;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBException;
import org.apache.jena.tdb2.TDB2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre/>
 * Tdb2 dataset.
 * https://jena.apache.org/documentation/tdb2/
 * https://jena.apache.org/documentation/tdb2/tdb2_cmds.html
 *
 * @author houzhiwei
 * @date 2020 /5/8 0:39
 */
public class Tdb2Dataset {

    public static final Logger log = LoggerFactory.getLogger(Tdb2Dataset.class);

    private Dataset ds;
    private String tdb2Dir;
    private boolean useAssemblerFile;

    public String getTdb2Dir() {
        if (useAssemblerFile) {
            log.warn("Null! Assembler file used");
        }
        return tdb2Dir;
    }

    public String getAssemblerFile() {
        if (!useAssemblerFile) {
            log.warn("Null! Tdb2 directory is used");
        }
        return assemblerFile;
    }

    private String assemblerFile;

    public static Tdb2Dataset getInstance(String tdb2Dir) {
        return new Tdb2Dataset(tdb2Dir, false);
    }

    /**
     * https://jena.apache.org/documentation/tdb/assembler.html
     *
     * @param assemblerFile the assembler file
     * @return instance with assembler
     */
    public static Tdb2Dataset getInstanceWithAssembler(String assemblerFile) {
        return new Tdb2Dataset(assemblerFile, true);
    }

    private Tdb2Dataset(String tdb2DirOrAssembler, boolean assembler) {
        this.useAssemblerFile = assembler;
        if (assembler) {
            this.assemblerFile = tdb2DirOrAssembler;
            this.ds = TDB2Factory.assembleDataset(tdb2DirOrAssembler);
            log.info("Connect to dataset use assembler file {}", tdb2DirOrAssembler);
        } else {
            this.tdb2Dir = tdb2DirOrAssembler;
            this.ds = TDB2Factory.connectDataset(Location.create(tdb2Dir));
            log.info("Connect to dataset directory {}", tdb2DirOrAssembler);
        }
    }

    /**
     * Gets dataset.
     *
     * @return the dataset
     */
    public Dataset getDataset() {
        if (ds == null) {
            throw new TDBException("TDB not connected");
        }
        return ds;
    }

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

    public void removeModel(String modelName) {
        if (!ds.isInTransaction())
            ds.begin(ReadWrite.WRITE);
        try {
            ds.removeNamedModel(modelName);
            ds.commit();
            log.warn(modelName + " 已被移除");
        } finally {
            ds.end();
        }
    }

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
    public void close() {
        if (ds != null) {
            ds.close();
        }
    }


}
