package org.semlab.ont.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * SPARQL查询 关键字，本体前缀、自定义功能函数
 *
 * @author houzhiwei
 * @date 2016年1月4日 下午10:52:51
 */
public class SparqlVocab
{
    // [start]** SPARQL Keywords **
    public static final String ASK = "ASK";
    public static final String SELECT = "SELECT";
    public static final String WHERE = "WHERE";
    public static final String CONSTRUCT = "CONSTRUCT";
    public static final String FILTER = "FILTER";
    public static final String BIND = "BIND";
    // [end]

    // [start]**** Prefixes ****
    public static final String PREFIX_RDF = prefixConcat("rdf", RDF.getURI());
    public static final String PREFIX_RDFS = prefixConcat("rdfs", RDFS.getURI());
    public static final String PREFIX_OWL = prefixConcat("owl", OWL2.getURI());
    public static final String PREFIX_XSD = prefixConcat("xsd", XSD.getURI());

    /**
     * 文本查询
     */
    public static final String PREFIX_TEXT = "PREFIX text: <http://jena.apache.org/text#>";
    /**
     * ARQ函数
     */
    public static final String PREFIX_AFN = "PREFIX afn: <http://jena.hpl.hp.com/ARQ/function#>";
    /**
     * 都柏林core
     */
    public static final String PREFIX_DC = "PREFIX dc: <http://purl.org/dc/elements/1.1/>";

    // [end]

    // [start]***** Functions *****

    /**
     * 获得本类（cn.geodata.ont.query.SPARQL）中定义的前缀
     *
     * @param prefix
     * @return
     * @throws Exception
     * @Houzw at 2016年1月8日下午10:26:44
     */
    public String getPrefix(String prefix) throws Exception
    {
        if (StringUtils.isBlank(prefix))
            return null;
        Field[] fields = SparqlVocab.class.getFields();

        for (Field field : fields) {
            if (prefix.equalsIgnoreCase(field.getName().substring(7)))// "PREFIX_"
            {
                return (String) SparqlVocab.class.getField(field.getName()).get(new SparqlVocab());
            }
        }
        return null;
    }

    /**
     * sparql语句前缀定义语句
     *
     * @param prefix
     * @param ns
     * @return "PREFIX prefix: &lt;ns&gt; "
     */
    public static String prefixConcat(String prefix, String ns)
    {
        StringBuilder builder = new StringBuilder("PREFIX ");
        builder.append(prefix);
        builder.append(": ");
        builder.append("<");
        builder.append(ns);
        builder.append("> ");
        return builder.toString();
    }

    /**
     * 构造SPARQL查询中的前缀
     *
     * @param ns
     * @return "PREFIX prefix: &lt;ns&gt; "
     * @Houzw at 2016年1月8日下午10:24:49
     */
    public static String prefixConcat(String ns)
    {
        String prefix = "";
        prefix = RegisterPrefix.getPrefixStr(ns);

        StringBuilder builder = new StringBuilder("PREFIX ");
        builder.append(prefix);
        builder.append(":");
        builder.append(" ");
        builder.append("<");
        builder.append(ns);
        builder.append("> ");
        return builder.toString();
    }

    // 需要确保所有属性都是前缀声明
    public List<String> getAllPrefixes() throws IllegalArgumentException, IllegalAccessException
    {
        List<String> prefixList = new ArrayList<>();
        Field[] fields = SparqlVocab.class.getFields();
        SparqlVocab pre = new SparqlVocab();
        for (Field field : fields) {
            if ("PREFIX_".equals(field.getName().substring(0, 6)))
                prefixList.add((String) field.get(pre));
        }
        return prefixList;
    }

    /**
     * 字符串正则匹配 FILTER regex(?var, "pattern")
     *
     * @param var           变量(<b> 无需带 "?" </b>)
     * @param pattern
     * @param caseSensitive 大小写敏感
     * @return
     */
    public static String filterRegexConcat(String var, String pattern, boolean caseSensitive)
    {
        StringBuilder builder = new StringBuilder("FILTER regex(");
        builder.append("?");
        builder.append(var);
        builder.append(", \"");
        builder.append(pattern);
        if (!caseSensitive) {
            builder.append("\", ");
            builder.append("i");
        }
        builder.append(")");
        return builder.toString();
    }
    // [end]
}
