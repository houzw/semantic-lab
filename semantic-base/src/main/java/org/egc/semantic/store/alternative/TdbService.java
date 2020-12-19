package org.egc.semantic.store.alternative;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;

/**
 * TODO
 * portal to operate Jena TDB
 *
 * @author houzhiwei
 * @date 2017 /4/17 14:03
 * @since 2015年12月21日 下午9:52:03
 */
public interface TdbService
{
    /**
     * 往模型中添加内容。不载入引用本体
     *
     * @param modelName  the model name
     * @param sourcePath the source path
     * @param override   the override
     * @return int
     * @date 2016年4月1日下午11 :36:13
     */
    int loadModel(String modelName, String sourcePath, boolean override);

    /**
     * 导入本体。OntModel不支持事务，同时载入引用本体
     *
     * @param modelName  模型uri
     * @param sourcePath 本体文件（集成文件）地址
     * @param override   是否覆盖
     * @return int
     * @date 2016年4月1日下午11 :36:09
     */
    int loadOntModel(String modelName, String sourcePath, boolean override);

    /**
     * 获得TDB中所有的本体URI<br/>
     * （imported的本体不算）
     *
     * @return json ontology ur is
     * @date 2015年12月22日下午3 :28:06
     */
    String getOntologyURIs();

    /**
     * 从TDB获得模型
     *
     * @param modelName the model name
     * @return the named model
     */
    Model getNamedModel(String modelName);

    /**
     * 从TDB获得本体模型<br/>
     * 本体模型（OntModel）比普通Model具有更多的操作本体的功能
     *
     * @param modelName the model name
     * @return named ont model
     * @date 2015年12月22日下午3 :19:55
     */
    OntModel getNamedOntModel(String modelName);

    /**
     * <b>必须</b>关闭TDB连接
     *
     * @date 2015年12月21日下午10 :04:43
     */
    void closeTDBConnection();
}
