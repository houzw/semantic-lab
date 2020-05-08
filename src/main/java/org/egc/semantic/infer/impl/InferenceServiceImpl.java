package org.egc.semantic.infer.impl;

import openllet.jena.PelletReasonerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.egc.commons.util.PropertiesUtil;
import org.egc.semantic.infer.ReasonerType;
import org.egc.semantic.infer.RuleService;
import org.egc.semantic.infer.InferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Ontology Inference Service
 *
 * @author houzhiwei
 * @date 2017/4/17 16:56
 * @date 2019/9/3
 */
@Service
public class InferenceServiceImpl implements InferenceService {
    static final Logger log = LoggerFactory.getLogger(InferenceServiceImpl.class);

    @Autowired
    RuleService ruleService;

    @Override
    public InfModel getInfModel(OntModel model, Reasoner reasoner) {
        reasoner.setDerivationLogging(true);
        // 推理模型
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        // 验证报告：可选
        ValidityReport report = inf.validate();
        if (report.isValid()) {
            log.debug("Validity OK");
            System.out.println("Validity OK");
        } else {
            System.out.println("Conflicts");
            log.error("Conflicts");
            for (Iterator<ValidityReport.Report> i = report.getReports(); i.hasNext(); ) {
                ValidityReport.Report r = i.next();
                System.out.println(r);
                log.error(r.description);
            }
        }
        return inf;
    }

    @Override
    public Reasoner getReasoner(List<Rule> rules, ReasonerType reasonerType) {
        // 推理器
        Reasoner reasoner = null;
        switch (reasonerType) {
            case OWL_REASONER:
                reasoner = ReasonerRegistry.getOWLReasoner();
                // or: OntModelSpec.OWL_MEM_RULE_INF
                // reasoner.bindSchema(schema);
                // ModelFactory.createInfModel(reasoner, data);
                break;
            case RDFS_RULE_REASONER:
                reasoner = ReasonerRegistry.getRDFSReasoner();
                // or: ModelFactory.createRDFSModel(schema, data);
                break;
            case TRANSITIVE_REASONER:
                reasoner = ReasonerRegistry.getTransitiveReasoner();
                break;
            case OPENPLLET:
                reasoner = PelletReasonerFactory.theInstance().create();
                break;
            case GENERIC_RULE_REASONER:
            default:
                GenericRuleReasoner gr = new GenericRuleReasoner(rules);
                gr.setOWLTranslation(true);
                // gr.setTransitiveClosureCaching(true);
                // 前向+后向推理
                gr.setMode(GenericRuleReasoner.HYBRID);
                reasoner = gr;
                break;
        }
        return reasoner;
    }

    @Override
    public InfModel getInfModel(Reasoner reasoner, String fileSchema, String fileData) {
        // 推理模型
        InfModel inf = null;
        reasoner.setDerivationLogging(true);
        Model schema = FileManager.get().loadModel(fileSchema);
        Model data = FileManager.get().loadModel(fileData);

        if (reasoner.getClass().equals(ReasonerRegistry.getOWLReasoner().getClass())) {
            reasoner.bindSchema(schema);
            inf = ModelFactory.createInfModel(reasoner, data);
        }
        if (reasoner.getClass().equals(ReasonerRegistry.getRDFSReasoner().getClass())) {
            inf = ModelFactory.createRDFSModel(schema, data);
        }
        // 验证报告：可选
        ValidityReport report = inf.validate();
        if (report.isValid()) {
            System.out.println("Validity OK");
            log.debug("Validity OK");
        } else {
            System.out.println("Conflicts");
            log.error("Conflicts");
            for (Iterator<ValidityReport.Report> i = report.getReports(); i.hasNext(); ) {
                ValidityReport.Report r = i.next();
                System.out.println(" - " + i.next());
                log.error(r.description);
            }
        }
        return inf;
    }

    @Override
    public InfModel execInfer(OntModel model, List<Rule> rules, ReasonerType reasonerType) {
        Reasoner reasoner = getReasoner(rules, reasonerType);
        return getInfModel(model, reasoner);
    }

    @Override
    public InfModel execInfer(OntModel model, String rawRules, ReasonerType reasonerType) {
        List<Rule> rules = ruleService.parseStrRules(rawRules);
        return execInfer(model, rules, reasonerType);
    }

    @Override
    public InfModel execInferWithRuleFile(OntModel model, String ruleFile, ReasonerType reasonerType) {
        List<Rule> rules = ruleService.parseRules(ruleFile);
        return execInfer(model, rules, reasonerType);
    }

    @Override
    public InfModel execInferWithRuleFile(OntModel model, ReasonerType reasonerType) {
        String ruleFile = PropertiesUtil.getPropertyFromConfig("jena.inf.rules", "semantic");
        if (StringUtils.isBlank(ruleFile)) {
            // 本地测试前面+src/main/resources
            ruleFile = "/ont-config/reasoner.rules";
        }
        List<Rule> rules = ruleService.parseRules(ruleFile);
        return execInfer(model, rules, reasonerType);
    }

    @Override
    public InfModel execInferWithLocalRuleFile(OntModel model, ReasonerType reasonerType) {
        RuleService ruleService = new RuleServiceImpl();
        String ruleFile = PropertiesUtil.getPropertyFromConfig("jena.inf.rules", "semantic");
        if (StringUtils.isBlank(ruleFile)) {
            ruleFile = "/ont-config/reasoner.rules";
        }
        List<Rule> rules = ruleService.parseRulesLocal(ruleFile);
        return execInfer(model, rules, reasonerType);
    }
}
