package org.egc.semantic.query;

/**
 * SPARQL查询 关键字，本体前缀、自定义功能函数
 *
 * @author houzhiwei
 * @date 2016年1月4日 下午10:52:51
 */
public class SparqlVocab {
    // SPARQL Keywords

    public static final String ASK = "ASK";
    public static final String SELECT = "SELECT";
    public static final String WHERE = "WHERE";
    public static final String CONSTRUCT = "CONSTRUCT";
    public static final String FILTER = "FILTER";
    public static final String BIND = "BIND";

    // [start]***** Functions *****


    /**
     * 字符串正则匹配 FILTER regex(?var, "pattern")
     *
     * @param var           变量(<b> 无需带 "?" </b>)
     * @param pattern       the pattern
     * @param caseSensitive 大小写敏感
     * @return string
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
