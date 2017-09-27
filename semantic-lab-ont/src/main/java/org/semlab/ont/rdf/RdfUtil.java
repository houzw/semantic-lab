package org.semlab.ont.rdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;

/**
 * TODO
 * RDF三元组工具类
 *
 * @author houzhiwei
 * @date 2017/4/17 16:36
 */
public class RdfUtil
{
    /**
     * 转换为OntModel
     *
     * @param model
     * @return
     * @date 2016年1月12日下午3:08:35
     */
    public static OntModel toOntModel(Model model)
    {
        return (OntModel) ModelFactory.createOntologyModel().add(model);
    }

    /**
     * Is resource with the uri is a RDF Class type.
     *
     * @param model the model
     * @param uri   the uri
     * @return the boolean
     */
    public static boolean isClass(Model model, String uri)
    {
        Resource r = model.createResource(uri);
        return model.contains(r, RDF.type, OWL2.Class);
    }

    /**
     * 创建或得到一个谓语
     *
     * @param predURI
     * @param model
     * @return
     * @date 2016年1月7日下午12:01:24
     */
    public static Property createPredicate(String predURI, Model model)
    {
        Property pred = (!StringUtils.isBlank(predURI)) ? model.createProperty(predURI) : (Property) null;// 得到或创建
        return pred;
    }

    /**
     * 创建或得到一个宾语资源
     *
     * @param objURI
     * @param model
     * @return
     * @date 2016年1月7日下午12:01:28
     */
    public static RDFNode createObjectRDFNode(String objURI, Model model)
    {
        RDFNode obj = (!StringUtils.isBlank(objURI)) ? model.createResource(objURI) : (RDFNode) null;
        return obj;
    }

    /**
     * 获得或创建一个资源
     *
     * @param subURI
     * @param model
     * @return
     * @date 2016年1月7日下午12:01:18
     */
    public static Resource createResource(String subURI, Model model)
    {
        Resource sub = (!StringUtils.isBlank(subURI)) ? model.createResource(subURI) : (Resource) null;// 得到或创建
        return sub;
    }

    /**
     * Is resource with the uri is a RDF peoperty type.
     *
     * @param model the model
     * @param uri   the uri
     * @return the boolean
     */
    public static boolean isPeoperty(Model model, String uri)
    {
        Resource r = model.createResource(uri);
        return model.contains(r, RDF.type, RDF.Property);
    }
}
