package org.egc.semantic.infer;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/17 16:54
 */
public interface InferenceService
{
    /**
     * 执行推理
     *
     * @return
     * @Houzw at 2015年12月29日上午12:39:24
     */
    InfModel execInfer(OntModel model, List<Rule> rules, ReasonerType reasonerType);

    InfModel execInfer(OntModel model, String rawRules, ReasonerType reasonerType);

    /**
     * 使用指定的推理文件进行推理
     *
     * @param model
     * @param rawRules
     * @param reasonerType
     * @return
     * @Houzw at 2016年1月11日下午3:51:43
     */
    InfModel execInferWithRuleFile(OntModel model, String rawRules, ReasonerType reasonerType);

    /**
     * 获取推理模型
     *
     * @param model
     * @param reasoner
     * @return
     * @Houzw at 2016年1月9日下午6:53:50
     */
    InfModel getInfModel(OntModel model, Reasoner reasoner);

    /**
     * 适用于OWLReasoner和RDFSReasoner
     *
     * @param reasoner
     * @param fileSchema
     * @param fileData
     * @return
     * @Houzw at 2016年1月9日下午6:53:35
     */
    InfModel getInfModel(Reasoner reasoner, String fileSchema, String fileData);

    /**
     * 创建推理器<br/>
     * GenericRuleReasoner时需要rules
     *
     * @param rules
     * @param reasonerType
     * @return
     * @Houzw at 2016年1月9日下午6:54:07
     */
    Reasoner getReasoner(List<Rule> rules, ReasonerType reasonerType);

    /**
     * 使用默认/配置的推理文件进行推理
     *
     * @param model
     * @param reasonerType
     * @return
     * @Houzw at 2016年1月11日下午2:27:17
     */
    InfModel execInferWithRuleFile(OntModel model, ReasonerType reasonerType);

    /**
     * 本地测试用
     *
     * @param model
     * @param reasonerType
     * @return
     * @Houzw at 2016年1月12日下午4:47:30
     */
    @Deprecated
    InfModel execInferWithLocalRuleFile(OntModel model, ReasonerType reasonerType);
}
