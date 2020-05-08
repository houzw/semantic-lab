package org.egc.semantic.infer;

/**
 * Jena推理器类型
 *
 * @author houzhiwei
 * @date: 2015年12月29日 上午12:47:17
 */
public enum ReasonerType {
    OWL_REASONER(
            "OWL 推理器. A set of useful but incomplete implementation of the OWL/Lite subset of the OWL/Full language"),
    GENERIC_RULE_REASONER(
            "一般规则推理器.A rule based reasoner that supports user defined rules. Forward chaining, tabled backward chaining and hybrid execution strategies are supported."),
    RDFS_RULE_REASONER("RDFS 推理器. Implements a configurable subset of the RDFS entailments"),
    TRANSITIVE_REASONER(
            "传递性推理器. Provides support for storing and traversing class and property lattices. This implements just the transitive and reflexive properties of rdfs:subPropertyOf and rdfs:subClassOf"),
    OPENPLLET("An OWL 2 reasoner in Java, build on top of Pellet. ");
    private String description;

    ReasonerType(String description)
    {
        this.description = description;
    }

    /**
     * 获得枚举的描述
     *
     * @return
     * @Houzw at 2015年12月29日上午12:54:58
     */
    public String getDescription()
    {
        return description;
    }
}
