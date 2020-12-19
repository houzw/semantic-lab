package org.egc.semantic.infer;

import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * parse rules
 *
 * @author houzhiwei
 * @date 2016年1月5日 上午12:21:42
 */
public interface RuleService
{
    /**
     * 从规则文件中读取推理规则<br/>
     * ruleFilePath="/ont-config/reasoner.rules"
     *
     * @param ruleFilePath the rule file path
     * @return list
     * @date 2015年12月29日上午12 :45:38
     */
    List<Rule> parseRules(String ruleFilePath);

    /**
     * 从规则文件中读取推理规则: 本地测试用<br/>
     * ruleFilePath="/ont-config/reasoner.rules"
     *
     * @param ruleFilePath the rule file path
     * @return list
     * @date 2015年12月29日上午12 :45:38
     */
    @Deprecated
    List<Rule> parseRulesLocal(String ruleFilePath);

    /**
     * 解析字符串形式的推理规则
     *
     * @param rawRules the raw rules
     * @return list
     * @date 2015年12月29日上午12 :44:15
     */
    List<Rule> parseStrRules(String rawRules);
}
