package org.egc.semantic.rdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Factory;
import org.apache.jena.graph.Graph;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.vocabulary.*;

/**
 * TODO
 * RDF三元组工具类 <br/>
 * 部分方法来自 {@link org.topbraid.jenax.util.JenaUtil}
 *
 * @author houzhiwei
 * @date 2017 /4/17 16:36
 */
public class RdfUtils {


    public static Graph createDefaultGraph() {
        return Factory.createDefaultGraph();
    }

    public static void ensurePrefix(PrefixMapping prefixMapping, String prefix, String uristr) {
        if (!uristr.equals(prefixMapping.getNsPrefixURI(prefix))) {
            prefixMapping.setNsPrefix(prefix, uristr);
        }
    }

    public static Literal dateLiteral(int year, int month, int day) {
       /* Calendar c = Calendar.getInstance();
        month = month < 1 ? 0 : month - 1;
        day = Math.max(day, 1);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);*/
        String date = String.valueOf(year);
        month = Math.max(month, 1);
        day = Math.max(day, 1);
        date += "-" + (month < 10 ? "0" + month : String.valueOf(month));
        date += "-" + (day < 10 ? "0" + day : String.valueOf(day));
        return ResourceFactory.createTypedLiteral(date, XSDDatatype.XSDdate);
    }

    public static void initNamespaces(PrefixMapping prefixMapping) {
        ensurePrefix(prefixMapping, "rdf", RDF.getURI());
        ensurePrefix(prefixMapping, "rdfs", RDFS.getURI());
        ensurePrefix(prefixMapping, "owl", OWL.getURI());
        ensurePrefix(prefixMapping, "xsd", XSD.getURI());
        ensurePrefix(prefixMapping, "skos", SKOS.getURI());
        ensurePrefix(prefixMapping, "dct", DCTerms.getURI());
    }

    public static Model createDefaultModel() {
        Model m = ModelFactory.createModelForGraph(createDefaultGraph());
        initNamespaces(m);
        return m;
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
     * @param predUri the pred uri
     * @param model   the model
     * @return property
     * @date 2016年1月7日下午12 :01:24
     */
    public static Property createPredicate(String predUri, Model model) {
        // 得到或创建
        return (!StringUtils.isBlank(predUri)) ? model.createProperty(predUri) : (Property) null;
    }

    public static ObjectProperty createObjectProperty(OntModel model, String predUri) {
        // 得到或创建
        return (!StringUtils.isBlank(predUri)) ? model.createObjectProperty(predUri) : (ObjectProperty) null;
    }

    public static DatatypeProperty createDatatypeProperty(OntModel model, String predUri) {
        // 得到或创建
        return (!StringUtils.isBlank(predUri)) ? model.createDatatypeProperty(predUri) : (DatatypeProperty) null;
    }

    /**
     * 创建或得到一个宾语资源
     *
     * @param objUri the obj uri
     * @param model  the model
     * @return rdf node
     * @date 2016年1月7日下午12 :01:28
     */
    public static RDFNode createObjectRDFNode(String objUri, Model model) {
        return (!StringUtils.isBlank(objUri)) ? model.createResource(objUri) : (RDFNode) null;
    }

    /**
     * 获得或创建一个资源
     *
     * @param subUri the sub uri
     * @return resource
     * @date 2016年1月7日下午12 :01:18
     */
    public static Resource createResource(String subUri) {
        // 得到或创建
        return (!StringUtils.isBlank(subUri)) ? ResourceFactory.createResource(subUri) : (Resource) null;
    }

    /**
     * Create resource resource.
     *
     * @param namespace the namespace, mostly end with a "#", e.g.,"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
     * @param localName the local name
     * @return the resource
     */
    public static Resource createResource(String namespace, String localName) {
        return createResource(namespace + localName);
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

    public static boolean isIgnoredNs(String ns) {
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
