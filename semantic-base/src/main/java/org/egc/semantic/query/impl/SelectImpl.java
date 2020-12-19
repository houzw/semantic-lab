package org.egc.semantic.query.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL2;
import org.egc.semantic.query.Ask;
import org.egc.semantic.query.Select;
import org.egc.semantic.rdf.Consts;
import org.egc.semantic.rdf.OntologyUtils;
import org.egc.semantic.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 执行SPARQL的Select查询
 *
 * @author houzhiwei
 * @date 2017/4/17 16:33
 */
public class SelectImpl implements Select {
    Ask ask = new AskImpl();

    @Override
    public String execSelectJSON(String sparqlStr, Model model) {
        return new BaseSelect().execSelectJSON(sparqlStr, model);
    }

    @Override
    public ResultSet execSelect(String sparqlStr, Model model) {
        return new BaseSelect().execSelect(sparqlStr, model);
    }

    @Override
    public String[] getQueryVars(Query query) {
        return new BaseSelect().getQueryVars(query);
    }


    @Override
    public List<String> getIndividualLocalNames(String sparqlStr, Model model) {
        QuerySolution solution = null;
        List<String> indisList = new ArrayList<>();
        try {
            Query query = QueryFactory.create(sparqlStr);
            String[] varArr = getQueryVars(query);
            QueryExecution queryExec = QueryExecutionFactory.create(query, model);
            ResultSet results = queryExec.execSelect();
            // 只留下实例名称
            while (results.hasNext()) {
                solution = results.next();
                for (String var : varArr) {
                    try {
                        String localName = solution.get(var).asResource().getLocalName();
                        if (localName.startsWith("_"))
                            localName = StringUtil.removeFirstUnderline(localName);
                        String uri = solution.get(var).asResource().getURI();
                        if (indisList.contains(localName))
                            continue;
                        if (ask.isIndividual(uri, model))
                            indisList.add(localName);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            indisList.add(e.getLocalizedMessage() + "\n 查询失败! 请检查SPARQL!");
        }
        return indisList;
    }

    @Override
    public List<Individual> getIndividuals(String sparqlStr, Model model) {
        QuerySolution solution = null;
        List<Individual> iList = new ArrayList<>();
        Query query = QueryFactory.create(sparqlStr);
        String[] varArr = getQueryVars(query);
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        ResultSet results = queryExec.execSelect();
        // 只留下实例
        OntModel om = OntologyUtils.toOntModel(model);

        while (results.hasNext()) {
            solution = results.next();
            for (String var : varArr) {
                try {
                    RDFNode node = solution.get(var);
                    if (node.isURIResource()) {
                        String uri = node.asResource().getURI();
                        Individual i = om.getIndividual(uri);
                        if (i != null) {
                            // if (iList.size() > 0 && iList.contains(i))
                            if (iList.contains(i))
                                continue;
                            iList.add(i);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return iList;
    }


    @Override
    public List<String> getIndividualURIs(String sparqlStr, Model model) {
        QuerySolution solution = null;
        List<String> urisList = new ArrayList<>();
        try {
            Query query = QueryFactory.create(sparqlStr);
            String[] varArr = getQueryVars(query);
            QueryExecution queryExec = QueryExecutionFactory.create(query, model);
            ResultSet results = queryExec.execSelect();
            // 只留下实例uri
            while (results.hasNext()) {
                solution = results.next();
                for (String var : varArr) {
                    try {
                        String uri = solution.get(var).asResource().getURI();
                        if (urisList.contains(uri))
                            continue;
                        if (ask.isIndividual(uri, model))
                            urisList.add(uri);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            urisList.add(e.getLocalizedMessage() + "\n 查询失败! 请检查SPARQL!");
        }
        return urisList;
    }


    @Override
    public List<Resource> getResourcesByLocalName(Model model, String name) {
        if (!StringUtils.isAllLowerCase(name)) {
            // 转为小写：不区分大小写，这样即使输入小写也可以获得本体中包含大写字母的术语。查询中是大小写敏感的
            name = name.toLowerCase();
        }
        List<Resource> reslist = new ArrayList<>();
        StringBuilder sparqlStr = new StringBuilder();
        sparqlStr.append("SELECT DISTINCT ?s WHERE { ?s a ?o. ");
        sparqlStr.append("BIND( STRENDS(LCASE(STR(?s)) , '");
        sparqlStr.append(name);
        sparqlStr.append("')  as ?b).  FILTER(?b=true)}");
        ResultSet resultSet = execSelect(sparqlStr.toString(), model);
        QuerySolution solution = null;
        Resource r = null;
        while (resultSet.hasNext()) {
            solution = resultSet.next();
            String uri = solution.get("s").asResource().getURI();
            r = model.getResource(uri);
            if (reslist.contains(r))
                continue;
            reslist.add(r);
        }
        /*
         * 计算resName，即查询语句分词出来的对象与已有本体类、实例的相关度；
         * 取最大值者，若大于一个阈值，则添加此resName为相关本体的一个新资源； 利用相关度，以原有资源为种子进行本体扩展。 ？？如何求算？？
         * 如果返回null，则在SPARQL中作为string值进行检索？
         */
        return reslist;
    }

    @Override
    public List<Individual> getIndividualsByLocalName(OntModel model, String name) {
        if (!StringUtils.isAllLowerCase(name)) {
            // 转为小写：不区分大小写。查询中是大小写敏感的
            name = name.toLowerCase();
        }
        List<Individual> ilist = new ArrayList<>();
        StringBuilder sparqlStr = new StringBuilder();
        sparqlStr.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>");
        sparqlStr.append("SELECT DISTINCT ?s WHERE { ?s a owl:NamedIndividual. ");
        // LCASE()表示转为小写
        sparqlStr.append("BIND( STRENDS(LCASE(STR(?s)) , '");
        sparqlStr.append(name);
        sparqlStr.append("') AS ?b).  FILTER(?b=true)}");
        ResultSet resultSet = execSelect(sparqlStr.toString(), model);
        QuerySolution solution = null;
        Individual i = null;
        while (resultSet.hasNext()) {
            solution = resultSet.next();
            String uri = solution.get("s").asResource().getURI();
            i = model.getIndividual(uri);
            if (ilist.contains(i))
                continue;
            ilist.add(i);
        }

        return ilist;
    }

    @Override
    public List<String> getSKOSLabelsByLocalName(Model model, String localname) {
        if (!StringUtils.isAllLowerCase(localname)) {
            // 转为小写：不区分大小写。查询中是大小写敏感的
            localname = localname.toLowerCase();
        }
        List<String> labelList = new ArrayList<>();
        StringBuilder sparqlStr = new StringBuilder();
        sparqlStr.append("PREFIX owl: <").append(OWL2.NS).append(">");
        sparqlStr.append("PREFIX skos: <").append(Consts.NS_SKOS).append(">");
        sparqlStr.append("SELECT DISTINCT ?pref ?alt ?hidden WHERE{");
        sparqlStr.append(" ?s skos:prefLabel ?pref. ");
        sparqlStr.append(" OPTIONAL {?s skos:altLabel ?alt. }");
        sparqlStr.append(" OPTIONAL {?s skos:hiddenLabel ?hidden.}");
        sparqlStr.append("{ SELECT DISTINCT ?s WHERE { ?s a owl:NamedIndividual. ");
        sparqlStr.append("BIND( STRENDS(LCASE(STR(?s)) , '");
        sparqlStr.append(localname);
        sparqlStr.append("') AS ?b).  FILTER(?b=true)}");
        sparqlStr.append("}}");
        ResultSet resultSet = execSelect(sparqlStr.toString(), model);
        QuerySolution solution;
        RDFNode prefNode, altNode, hiddenNode;
        String pref, alt, hidden;
        // 语言标签模式，如@cn，@en，@zh-CN等
        String langTagPattern = "@[a-zA-Z\\-]+";
        while (resultSet.hasNext()) {
            solution = resultSet.next();
            prefNode = solution.get("pref");
            altNode = solution.get("alt");
            hiddenNode = solution.get("hidden");
            if (prefNode != null) {
                pref = StringUtils.removePattern(prefNode.toString(), langTagPattern);
                labelList.add(pref);
            }
            if (altNode != null) {
                alt = StringUtils.removePattern(altNode.toString(), langTagPattern);
                labelList.add(alt);
            }
            if (hiddenNode != null) {
                hidden = StringUtils.removePattern(hiddenNode.toString(), langTagPattern);
                labelList.add(hidden);
            }
        }
        return labelList;
    }
}
