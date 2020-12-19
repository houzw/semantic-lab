package org.egc.semantic.rdf;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;

/**
 * TODO
 * Utilities to operate owl ontology
 *
 * @author houzhiwei
 * @date 2017 /4/16 21:16
 */
public class OntologyUtils {

    /**
     * 转换为OntModel
     *
     * @param model the model
     * @return ont model
     * @date 2016年1月12日下午3 :08:35
     */
    public static OntModel toOntModel(Model model) {
        return (OntModel) ModelFactory.createOntologyModel().add(model);
    }
    /**
     * 创建一个statement
     *
     * @param model    the model
     * @param subject  主语 uri
     * @param property 谓词 uri
     * @param object   宾语 uri
     * @return statement
     */
    public static Statement createStmt(Model model, String subject, String property, String object) {
        Resource sub = model.createResource(subject);
        Property prop = model.createProperty(property);
        RDFNode obj = model.createResource(object);
        return model.createStatement(sub, prop, obj);
    }

    /**
     * Create simple selector selector:
     * (Resource)subject - (Property)property - (RDFNode)object
     *
     * @param model    the model
     * @param subject  the subject
     * @param property the property
     * @param object   the object
     * @return the selector
     */
    public static Selector createSimpleSelector(Model model, String subject, String property, String object) {
      /*
      StmtIterator iter = model.listStatements(
                new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                    @Override
                    public boolean selects(Statement s) {
                        return s.getString().endsWith("Smith");
                    }
                });
       */
        return new SimpleSelector(
                (subject != null) ? model.createResource(subject) : (Resource) null,
                (property != null) ? model.createProperty(property) : (Property) null,
                (object != null) ? model.createResource(object) : (RDFNode) null
        );
    }

}
