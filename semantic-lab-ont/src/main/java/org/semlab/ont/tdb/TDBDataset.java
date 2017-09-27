package org.semlab.ont.tdb;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import java.util.List;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/16 16:34
 * @since 2015年12月28日 下午6:22:46
 */
public interface TdbDataset
{
    /**
     * 往模型中添加内容。不载入引用本体
     *
     * @param modelName   目标模型 uri
     * @param sourcePath 内容文件URI 或 file:filename 形式路径
     * @param isOverride 是否覆盖已有模型。若模型不存在，此项不起作用
     */
    int loadModel(String modelName, String sourcePath, Boolean isOverride);

    /**
     * 导入本体。OntModel不支持事务，同时载入引用本体
     *
     * @param modelName
     * @param sourcePath
     * @param isOverride
     * @date 2015年12月20日下午5:57:46
     */
    int loadOntModel(String modelName, String sourcePath, Boolean isOverride);

    Model getDefaultModel();

    /**
     * 获得一个模型
     *
     * @param modelName
     * @return
     */
    Model getModel(String modelName);

    /**
     * 默认模型.defaultmodel没有name
     *
     * @param sourcePath
     */
    void loadDefaultModel(String sourcePath);

    /**
     * 移除模型
     *
     * @param modelName
     */
    void removeModel(String modelName);

    /**
     * 列出TDB中所有的模型URI<br/>
     * 如果是getDefaultModel，则为空
     */
    List<String> listModels();

    /**
     * 必须关闭TDB连接
     * @date 2015年12月28日下午6:25:19
     */
    void close();

    /**
     * get TDB's dataset
     * @return
     */
    Dataset getDataset();
}
