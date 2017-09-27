package org.semlab.ont.tdb.impl;

import com.alibaba.fastjson.JSON;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.semlab.commons.PropertiesUtil;
import org.semlab.ont.tdb.TdbDataset;
import org.semlab.ont.tdb.TdbService;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/17 14:11
 */
@Service
public class TdbServiceImpl implements TdbService
{
    private String tdbLocation = null;

    private TdbDataset conn;

    public TdbServiceImpl()
    {
        tdbLocation = PropertiesUtil.getProperty("jena.tdb.dbLocation");
        String rootpath = TdbServiceImpl.class.getResource("/").getPath();
        conn = new TdbDatasetImpl(rootpath + tdbLocation);
    }

    public TdbServiceImpl(String tdbLocation)
    {
        String rootpath = TdbServiceImpl.class.getResource("/").getPath();
        conn = new TdbDatasetImpl(rootpath + tdbLocation);
    }

    public TdbServiceImpl(File assemblerFile)
    {
        conn = new TdbDatasetImpl(assemblerFile);
    }

    /**
     * 往模型中添加内容。不载入引用本体
     *
     * @param modelUri
     * @param sourcePath
     * @param override
     * @return
     * @date 2016年4月1日下午11:36:13
     */
    @Override
    public int loadModel(String modelUri, String sourcePath, boolean override)
    {
        return conn.loadModel(modelUri, sourcePath, override);
    }

    /**
     * 导入本体。OntModel不支持事务，同时载入引用本体
     *
     * @param modelName   模型uri
     * @param sourcePath 本体文件（集成文件）地址
     * @param override   是否覆盖
     * @return
     * @date 2016年4月1日下午11:36:09
     */
    @Override
    public int loadOntModel(String modelName, String sourcePath, boolean override)
    {
        return conn.loadOntModel(modelName, sourcePath, override);
    }

    /**
     * 获得TDB目录
     *
     * @date 2016年4月1日下午11:36:30
     */
    private void getTdbLocation()
    {
        Properties tdbProperties = new Properties();
        try {
            tdbProperties.load(this.getClass().getResourceAsStream("/ont-config/ont.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tdbLocation = tdbProperties.getProperty("tdb.location");
    }

    /**
     * 获得TDB中所有的本体URI<br/>
     * （imported的本体不算）
     *
     * @return json
     * @date 2015年12月22日下午3:28:06
     */
    @Override
    public String getOntologyURIs()
    {
        List<String> onts = conn.listModels();
        Map<String, Object> ontMap = new HashMap<String, Object>();
        ontMap.put("ROWS", onts);
        ontMap.put("TOTAL", onts.size());
        return JSON.toJSONString(ontMap);
    }

    /**
     * 从TDB获得模型
     */
    @Override
    public Model getNamedModel(String modelName)
    {
        return conn.getModel(modelName);
    }

    /**
     * 从TDB获得本体模型<br/>
     * 本体模型（OntModel）比普通Model具有更多的操作本体的功能
     *
     * @param modelName
     * @return
     * @date 2015年12月22日下午3:19:55
     */
    @Override
    public OntModel getNamedOntModel(String modelName)
    {
        Model model = conn.getModel(modelName);
        return (OntModel) ModelFactory.createOntologyModel().add(model);
    }

    /**
     * <b>必须</b>关闭TDB连接
     *
     * @Houzw at 2015年12月21日下午10:04:43
     */
    @Override
    @PreDestroy
    public void closeTDBConnection()
    {
        conn.close();
    }
}
