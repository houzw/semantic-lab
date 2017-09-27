package org.semlab.ont.util;

import org.apache.jena.rdf.model.*;

/**
 * TODO
 * Utilities to operate ontology
 *
 * @author houzhiwei
 * @date 2017 /4/16 21:16
 */
public class OntologyUtil
{
    /**
     * 创建一个statement
     *
     * @param model
     * @param subject  主语 uri
     * @param property 谓词 uri
     * @param object   宾语 uri
     * @return
     */
    public static Statement createStmt(Model model, String subject, String property, String object)
    {
        Resource sub = model.createResource(subject);
        Property prop = model.createProperty(property);
        RDFNode obj = model.createResource(object);
        Statement stmt = model.createStatement(sub, prop, obj);
        return stmt;
    }

    /**
     * Create simple selector selector:
     * (Resource)subject - (Property)property - (RDFNode)object
     *
     * @see
     * @param model    the model
     * @param subject  the subject
     * @param property the property
     * @param object   the object
     * @return the selector
     */
    public static Selector createSimpleSelector(Model model, String subject, String property, String object)
    {
        Selector selector = new SimpleSelector(
                (subject != null) ? model.createResource(subject) : (Resource) null,
                (property != null) ? model.createProperty(property) : (Property) null,
                (object != null) ? model.createResource(object) : (RDFNode) null
        );
        return selector;
    }
}
