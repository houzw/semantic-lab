package org.egc.semantic.query;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NsIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shacl.vocabulary.SHACL;
import org.apache.jena.vocabulary.*;
import org.egc.semantic.rdf.Consts;

/**
 * 注册命名空间前缀
 *
 * @author houzhiwei
 * @date 2015年11月8日
 */
public class SparqlPrefix {
    static String[][] preNS = new String[][]{
            {"rdfs", RDFS.getURI()},
            {"rdf", RDF.getURI()},
            {"owl", OWL2.getURI()},
            {"xsd", XSD.getURI()},
            {"rss", RSS.getURI()},
            {"sh", SHACL.getURI()},
            {"vcard", VCARD.getURI()},
            {"foaf", Consts.NS_FOAF},
            {"swrl", Consts.NS_SWRL},
            {"fn", Consts.NS_FN},
            {"sfn", Consts.NS_SPARQL}};

    public static final String PREFIX_RDF = prefixConcat("rdf", RDF.getURI());
    public static final String PREFIX_RDFS = prefixConcat("rdfs", RDFS.getURI());
    public static final String PREFIX_OWL = prefixConcat("owl", OWL2.getURI());
    public static final String PREFIX_XSD = prefixConcat("xsd", XSD.getURI());
    public static final String PREFIX_SHACL = prefixConcat("sh", SHACL.getURI());
    /**
     * 文本查询
     */
    public static final String PREFIX_TEXT = prefixConcat("text", "http://jena.apache.org/text#");
    /**
     * ARQ函数
     */
    public static final String PREFIX_AFN = prefixConcat("afn", "http://jena.hpl.hp.com/ARQ/function#");
    /**
     * 都柏林core
     */
    public static final String PREFIX_DC = prefixConcat("dc", "http://purl.org/dc/elements/1.1/");

    /**
     * 给模型注册命名空间前缀
     *
     * @param model the model
     * @return model ont model
     */
    public static OntModel registerPrefixes(OntModel model)
    {
        model = registerOntPrefixes(model);
        NsIterator nsIter = model.listNameSpaces();
        String ns = null, pre = null;
        while (nsIter.hasNext()) {
            ns = nsIter.next();
            if (StringUtils.isBlank(model.getNsURIPrefix(ns))) {
                pre = getPrefixStr(ns);
                if (!StringUtils.isBlank(pre)) {
                    // 设置命名空间前缀
                    model.setNsPrefix(pre, ns);
                }
            }
        }
        return model;
    }

    /**
     * 注册公用本体前缀<br/>
     * RDF/RDFS/OWL/SWRL。。 因为部分前缀无法自动取出，<br/>
     * 如rdf: http://www.w3.org/1999/02/22-rdf-syntax-ns#
     *
     * @param model the model
     * @return ont model
     */
    protected static OntModel registerOntPrefixes(OntModel model)
    {
        for (int i = 0, leni = preNS.length; i < leni; i++) {
            model.setNsPrefix(preNS[i][0], preNS[i][1]);
        }
        return model;
    }

    /**
     * 设置命名空间前缀
     *
     * @param resource the resource
     * @return res prefix
     */
    public static String setResPrefix(Resource resource)
    {
        String ns = resource.getNameSpace();
        String prefix = null;
        Model resModel = resource.getModel();
        if (resModel != null && ns != null) {
            // ns = ns.substring(0, ns.lastIndexOf("#") + 1);//
            // 将#后面的名称去掉，这样即使后面有/也不用担心下一行报错
            String pre = getPrefixStr(ns);
            if (StringUtils.isBlank(pre))
                return null;
            try {
                resModel.setNsPrefix(pre, ns);
                prefix = resModel.getNsURIPrefix(ns);
            } catch (Exception e) {
                // e.printStackTrace();
                return pre;
            }
        }
        return prefix;
    }

    public static String getPrefixStr(String ns)
    {
        String pre = null;
        for (int i = 0, leni = preNS.length; i < leni; i++) {
            if (ns.equalsIgnoreCase(preNS[i][1])) {
                return preNS[i][0];
            }
        }
        if (ns.indexOf('#') > -1) {
            ns = ns.substring(0, ns.lastIndexOf('#') + 1);
            pre = ns.substring(ns.lastIndexOf('/') + 1, ns.indexOf('#'));
            int owl = pre.indexOf(".owl");
            // 去掉.owl
            if (owl > -1) {
                pre = pre.substring(0, owl);
            }
        }
        return pre;
    }

    /**
     * sparql语句前缀定义语句
     *
     * @param prefix the prefix
     * @param ns     the ns
     * @return "PREFIX prefix: &lt;ns&gt; "
     */
    public static String prefixConcat(String prefix, String ns)
    {
        StringBuilder builder = new StringBuilder("PREFIX ");
        builder.append(prefix);
        builder.append(": <");
        builder.append(ns);
        builder.append("> ");
        return builder.toString();
    }

    /**
     * 构造SPARQL查询中的前缀
     *
     * @param ns the ns
     * @return "PREFIX prefix: &lt;ns&gt; "
     * @author houzhiwei at 2016年1月8日下午10:24:49
     */
    public static String prefixConcat(String ns)
    {
        String prefix = "";
        prefix = SparqlPrefix.getPrefixStr(ns);

        StringBuilder builder = new StringBuilder("PREFIX ");
        builder.append(prefix);
        builder.append(": <");
        builder.append(ns);
        builder.append("> ");
        return builder.toString();
    }


    // 需要确保所有属性都是前缀声明
   /* public List<String> getAllPrefixes() throws IllegalArgumentException, IllegalAccessException
    {
        List<String> prefixList = new ArrayList<>();
        Field[] fields = SparqlVocab.class.getFields();
        SparqlVocab pre = new SparqlVocab();
        for (Field field : fields) {
            if ("PREFIX_".equals(field.getName().substring(0, 6))) {
                prefixList.add((String) field.get(pre));
            }
        }
        return prefixList;
    }*/
}
