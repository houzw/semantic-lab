package org.egc.semantic.store.impl;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.egc.semantic.store.TdbStatement;
import org.egc.semantic.util.OntologyUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理TDB陈述
 *
 * @author houzhiwei
 * @date 2017/4/16 21:04
 * @since 2015年12月21日 下午5:26:00
 */
@Component
public class TdbStatementImpl implements TdbStatement
{
    Dataset ds = null;
    public TdbStatementImpl(){}

    public TdbStatementImpl(Dataset ds)
    {
        this.ds = ds;
    }

    /**
     * 往模型中添加陈述
     * add a statement into a tdb named model
     *
     * @param modelName    目标模型 model uri
     * @param subjectUri  主语 subject uri
     * @param propertyUri 谓词 property uri
     * @param objectUri   宾语 object uri
     */
    @Override
    public void addStatement(String modelName, String subjectUri, String propertyUri, String objectUri)
    {
        Model model = null;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel(modelName);
            model.begin();
            Statement stmt = OntologyUtils.createStmt(model, subjectUri, propertyUri, objectUri);
            model.add(stmt);
            model.commit();
            ds.commit();
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
    }

    /**
     * 更新操作实际是移除原来索引，然后添加新的索引和记录，而实际记录没有移除。这是tdb的一种设计策略
     */
    @Override
    public void updateStatement(String modelName, Statement oldStmt, Statement newStmt)
    {
        // 先remove
        // 在add
        Model model = null;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel(modelName);
            model.begin();
            model.remove(oldStmt);
            model.add(newStmt);
            model.commit();
            ds.commit();
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
    }

    /**
     * 获取陈述，可能会有多个：param为null时
     *
     * @param modelName
     * @param subjectUri
     * @param propertyUri
     * @param objectUri
     * @return
     */
    @Override
    public List<Statement> getStatements(String modelName, String subjectUri, String propertyUri, String objectUri)
    {
        Model model = null;
        List<Statement> stmts = new ArrayList<Statement>();
        ds.begin(ReadWrite.READ);
        try {
            model = ds.getNamedModel(modelName);
            Selector selector = OntologyUtils.createSimpleSelector(model, subjectUri, propertyUri, objectUri);
            StmtIterator iterator = model.listStatements(selector);
            Statement statement = null;
            while (iterator.hasNext()) {
                statement = iterator.next();
                stmts.add(statement);
            }
            ds.commit();
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
        return stmts;
    }

    /**
     * 从模型中移除一个statement
     *
     * @param modelName
     * @param subject  主语 uri
     * @param property 谓词 uri
     * @param object   宾语 uri
     */
    @Override
    public void removeStatement(String modelName, String subject, String property, String object)
    {
        Model model = null;
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel(modelName);
            model.begin();
            Statement stmt = OntologyUtils.createStmt(model, subject, property, object);
            model.remove(stmt);
            model.commit();
            ds.commit();
        } finally {
            if (model != null)
                model.close();
            ds.end();
        }
    }
}
