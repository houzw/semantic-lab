package org.egc.semantic.rdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

/**
 * TODO
 * RDF三元组工具类
 *
 * @author houzhiwei
 * @date 2017 /4/17 16:36
 */
public class RdfUtils {

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
     * Is resource with the uri is a RDF Class type.
     *
     * @param model the model
     * @param uri   the uri
     * @return the boolean
     */
    public static boolean isClass(Model model, String uri) {
        Resource r = model.createResource(uri);
        return model.contains(r, RDF.type, OWL2.Class);
    }

    /**
     * 创建或得到一个谓语
     *
     * @param predURI the pred uri
     * @param model   the model
     * @return property
     * @date 2016年1月7日下午12 :01:24
     */
    public static Property createPredicate(String predURI, Model model) {
        // 得到或创建
        return (!StringUtils.isBlank(predURI)) ? model.createProperty(predURI) : (Property) null;
    }

    /**
     * 创建或得到一个宾语资源
     *
     * @param objURI the obj uri
     * @param model  the model
     * @return rdf node
     * @date 2016年1月7日下午12 :01:28
     */
    public static RDFNode createObjectRDFNode(String objURI, Model model) {
        return (!StringUtils.isBlank(objURI)) ? model.createResource(objURI) : (RDFNode) null;
    }

    /**
     * 获得或创建一个资源
     *
     * @param subURI the sub uri
     * @param model  the model
     * @return resource
     * @date 2016年1月7日下午12 :01:18
     */
    public static Resource createResource(String subURI, Model model) {
        // 得到或创建
        return (!StringUtils.isBlank(subURI)) ? model.createResource(subURI) : (Resource) null;
    }

    /**
     * Is resource with the uri is a RDF property type.
     *
     * @param model the model
     * @param uri   the uri
     * @return the boolean
     */
    public static boolean isProperty(Model model, String uri) {
        Resource r = model.createResource(uri);
        return model.contains(r, RDF.type, RDF.Property);
    }

    public static boolean isIgnoredNS(String ns) {
        String[] ignoreNs = {RDF.getURI(), RDFS.getURI(), OWL.getURI(), Consts.NS_SWRL,
                Consts.NS_FN, Consts.NS_JA, Consts.NS_SPARQL, XSD.getURI()};
        for (String igns : ignoreNs) {
            if (igns.equals(ns)) {
                return true;
            }
        }
        return false;
    }
}
