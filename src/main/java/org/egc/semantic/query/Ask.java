package org.egc.semantic.query;

import org.apache.jena.rdf.model.Model;

/**
 * TODO
 * 利用SPARQL的ASK操作查询数据
 * @author houzhiwei
 * @date 2017/4/17 16:29
 * @date 2016年4月18日 下午4:57:16
 */
public interface Ask
{
    /**
     * 执行ASK查询
     *
     * @param sparqlStr
     * @param model
     * @return T/F
     * @Houzw at 2016年4月18日下午5:03:54
     */
    boolean execAsk(String sparqlStr, Model model);

    /**
     * 执行ASK查询
     *
     * @param sub_uri
     * @param prop_uri
     * @param obj_uri
     * @param model
     * @return T/F
     * @Houzw at 2016年3月30日下午3:22:02
     */
    boolean execAsk(String sub_uri, String prop_uri, String obj_uri, Model model);

    /**
     * 实例是否存在
     *
     * @param uri
     * @param model
     * @return T/F
     * @Houzw at 2016年4月1日下午10:18:23
     */
    boolean isIndividual(String uri, Model model);
}
