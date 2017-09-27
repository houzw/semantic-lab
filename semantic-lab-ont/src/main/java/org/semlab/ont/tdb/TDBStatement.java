package org.semlab.ont.tdb;

import org.apache.jena.rdf.model.Statement;

import java.util.List;

/**
 * TDB陈述CRUD操作，包含事务
 *
 * @author houzhiwei
 * @date 2017/4/16 20:59
 * @since 2015年12月21日 下午5:26:00
 */
public interface TdbStatement
{
    /**
     * 往模型中添加陈述
     *
     * @param modelName 目标模型 uri
     * @param subject  主语 uri
     * @param property 谓词 uri
     * @param object   宾语 uri
     */
    void addStatement(String modelName, String subjectUri, String propertyUri, String objectUri);

    /**
     * 更新操作实际是移除原来索引，然后添加新的索引和记录，而实际记录没有移除。这是tdb的一种设计策略
     */
    void updateStatement(String modelName, Statement oldStmt, Statement newStmt);

    /**
     * 获取陈述，可能会有多个：param为null时
     *
     * @param modelUri
     * @param subject
     * @param property
     * @param object
     * @return
     */
    List<Statement> getStatements(String modelName, String subjectUri, String propertyUri, String objectUri);

    /**
     * 从模型中移除一个statement
     *
     * @param modelUri
     * @param subject  主语 uri
     * @param property 谓词 uri
     * @param object   宾语 uri
     */
    void removeStatement(String modelName, String subjectUri, String propertyUri, String objectUri);
}
