package org.semlab.ont.inf.impl;

import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileUtils;
import org.semlab.ont.inf.RuleService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houzhiwei
 * @date: 2016年1月5日 上午12:22:33
 */
@Service
public class RuleServiceImpl implements RuleService
{
    @Override
    public List<Rule> parseStrRules(String rawRules)
    {
        List<Rule> rules = null;
        InputStream in = new ByteArrayInputStream(rawRules.getBytes());
        BufferedReader br = new BufferedReader(FileUtils.asBufferedUTF8(in));
        rules = Rule.parseRules(Rule.rulesParserFromReader(br));
        return rules;
    }

    @Override
    public List<Rule> parseRules(String ruleFilePath)
    {
        List<Rule> rules = new ArrayList<Rule>();
        try {
            URL in = this.getClass().getResource(ruleFilePath);
            // 读取规则文件
            BufferedReader br = new BufferedReader(new FileReader(in.getFile()));
            // 获取规则
            rules = Rule.parseRules(Rule.rulesParserFromReader(br));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }

    @Override
    public List<Rule> parseRulesLocal(String ruleFilePath)
    {
        List<Rule> rules = new ArrayList<Rule>();
        try {
            // ------------- 本地运行测试，不然的话就要运行spring的测试
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + ruleFilePath));
            // -------------
            // 获取规则
            rules = Rule.parseRules(Rule.rulesParserFromReader(br));
            // or
            // List rules = Rule.rulesFromURL("file:myfile.rules");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }
}
