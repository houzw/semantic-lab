package org.egc.semantic.store.alternative;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import java.util.List;

/**
 * <pre>
 * 与 {@link TdbConnection} 采用的方式不一样
 * 可采用命令行 tdbloader 方式将 owl 导入到 tdb 数据库
 * @author houzhiwei
 * @date 2017/4/16 16:34
 * @since 2015年12月28日 下午6:22:46
 */
public interface TdbDataset {
    /**
     * 往模型中添加内容。不载入引用本体
     *
     * @param modelName  目标模型 uri
     * @param sourcePath 内容文件URI 或 file:filename 形式路径
     * @param isOverride 是否覆盖已有模型。若模型不存在，此项不起作用
     */
    int loadModel(String modelName, String sourcePath, Boolean isOverride);

    /**
     * 导入本体。OntModel不支持事务，同时载入引用本体
     *
     * @param modelName  the model name
     * @param sourcePath the source path
     * @param isOverride the is override
     * @return the int
     * @date 2015年12月20日下午5 :57:46
     */
    int loadOntModel(String modelName, String sourcePath, Boolean isOverride);

    /**
     * Gets default model.
     *
     * @return the default model
     */
    Model getDefaultModel();

    /**
     * 获得一个模型
     *
     * @param modelName the model name
     * @return model
     */
    Model getModel(String modelName);

    /**
     * 默认模型.defaultmodel没有name
     *
     * @param sourcePath the source path
     */
    void loadDefaultModel(String sourcePath);

    /**
     * 移除模型
     *
     * @param modelName the model name
     */
    void removeModel(String modelName);

    /**
     * 列出TDB中所有的模型URI<br/>
     * 如果是getDefaultModel，则为空
     *
     * @return the list
     */
    List<String> listModels();

    /**
     * 必须关闭TDB连接
     *
     * @date 2015年12月28日下午6:25:19
     */
    void close();

    /**
     * get TDB's dataset
     *
     * @return dataset
     */
    Dataset getDataset();
}
