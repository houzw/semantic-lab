package org.egc.semantic.rdf;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.egc.semantic.dto.DataGrid;
import org.egc.semantic.util.StringUtil;

import java.util.*;

/**
 * @author houzhiwei
 * @date 2020/9/5 16:15
 */
public class OntModelUtils {

    public static DataGrid<Map<String, Object>> getPrefixMapping(OntModel model) {
        Map<String, Object> kvMap = null;
        DataGrid<Map<String, Object>> result = new DataGrid<>();
        Map<String, String> pm = model.getNsPrefixMap();
        List<Map<String, Object>> pms = new ArrayList<>();
        for (Map.Entry<String, String> pmkv : pm.entrySet()) {
            if (StringUtils.isNotBlank(pmkv.getKey())) {
                kvMap = new HashMap<>();
                kvMap.put("prefix", pmkv.getKey());
                kvMap.put("ns", pmkv.getValue());
                pms.add(kvMap);
            }
        }
        result.setTotal(pms.size());
        result.setRows(pms);
        return result;
    }

    public static DataGrid<String> allIndivduals(OntModel model) {
        DataGrid<String> dg = new DataGrid<>();
        List<String> indisList = new ArrayList<>();
        List<Individual> allIndi = model.listIndividuals().toList();
        for (Individual indi : allIndi) {
            indisList.add(indi.getURI());
        }
        dg.setTotal(allIndi.size());
        dg.setRows(indisList);
        return dg;
    }

    public static DataGrid<String> getAllClasses(OntModel model) {
        List<OntClass> allCls = model.listNamedClasses().toList();
        List<String> clsList = new ArrayList<>();
        for (OntClass cls : allCls) {
            clsList.add(cls.getURI());
        }
        return new DataGrid<>(allCls.size(), clsList);
    }


    public static List<String> getNsList(OntModel model) {
        NsIterator nsitr = model.listNameSpaces();
        List<String> nslist = new ArrayList<>();
        while (nsitr.hasNext()) {
            String ns = nsitr.next();
            if (!RdfUtils.isIgnoredNS(ns)) {
                nslist.add(ns);
            }
        }
        return nslist;
    }


    public List<String> getAllNsList(OntModel model) {
        return model.listNameSpaces().toList();
    }

    public static DataGrid<Map<String, Object>> getStmts(OntModel model) {
        StmtIterator stmts = model.listStatements();
        List<Map<String, Object>> stmtList = new ArrayList<>();
        Map<String, Object> stmtMap = null;
        String subPrefix = "", subStr = "", predStr = "", objPrefix = "", objStr = "";
        int count = 0;
        Statement stmt = null;
        Resource sub = null;
        RDFNode obj = null;
        Property pred = null;
        while (stmts.hasNext()) {
            stmtMap = new HashMap<>();
            stmt = stmts.nextStatement();
            sub = stmt.getSubject();
            obj = stmt.getObject();
            pred = stmt.getPredicate();
            if (sub != null) {
                if (sub.getLocalName() == null) {
                    continue;
                }
                subPrefix = sub.getModel().getNsURIPrefix(sub.getNameSpace());
                if (subPrefix == null) {
                    subStr = sub.getLocalName();
                } else {
                    subStr = subPrefix + ":" + sub.getLocalName();
                }
            }

            predStr = pred.getModel().getNsURIPrefix(pred.getNameSpace()) + ":" + pred.getLocalName();
            if (obj != null) {
                if (obj.isURIResource()) {
                    objPrefix = obj.asResource().getModel().getNsURIPrefix(obj.asResource().getNameSpace());
                    objStr = objPrefix != null ? objPrefix + ":" : "";
                    objStr = objStr + obj.asResource().getLocalName();
                } else {
                    objStr = obj.toString().replaceAll(XSD.getURI(), "xsd:");
                    // getValue、getLexicalForm与直接toString差不多
                }
                stmtMap.put(Consts.SUB, subStr);
                stmtMap.put(Consts.PRED, predStr);
                stmtMap.put(Consts.OBJ, objStr);
                stmtList.add(stmtMap);
                count++;
            }
        }
        return new DataGrid<>(count,stmtList);
    }

    public static List<String> getAllNs(OntModel model) {
        return model.listNameSpaces().toList();
    }


    public static DataGrid<String> getAllProperties(OntModel model) {
        List<OntProperty> props = model.listAllOntProperties().toList();
        List<String> propsList = new ArrayList<>();
        for (OntProperty property : props) {
            propsList.add(property.getURI());
        }
        return new DataGrid<>(propsList.size(),propsList);
    }


    public static List<Map<String, Object>> getAllIndivdualNameURI(OntModel model) {
        List<Map<String, Object>> resMaps = new ArrayList<>();
        Map<String, Object> resMap = null;
        ExtendedIterator<Individual> indiitr = model.listIndividuals();
        String resName = "";
        while (indiitr.hasNext()) {
            resMap = new HashMap<>();
            Individual r = indiitr.next();
            if (ignoreRes(r.getNameSpace())) {
                continue;
            }
            resName = r.getLocalName();
            if (StringUtils.isBlank(resName)) {
                continue;
            }
            resMap.put("Name", StringUtil.removeFirstUnderline(resName));
            resMap.put("URI", r.getURI());
            resMaps.add(resMap);
        }
        return resMaps;
    }

    public static List<String> getAllIndivdualSKOSLabels(OntModel ontModel) {
        List<String> indisList = new ArrayList<>();
        List<Individual> allIndi = ontModel.listIndividuals().toList();
        String prefStr = null, altStr = null, hiddenStr = null;
        Property pref = ontModel.getProperty(Consts.NS_SKOS, "prefLabel");
        Property alt = ontModel.getProperty(Consts.NS_SKOS, "altLabel");
        Property hidden = ontModel.getProperty(Consts.NS_SKOS, "hiddenLabel");
        // 语言标签模式，如@cn，@en，@zh-CN等
        String langTagPattern = "@[a-zA-Z\\-]+";
        for (Individual indi : allIndi) {
            if (indi.getPropertyValue(pref) != null) {
                prefStr = StringUtils.removePattern(indi.getPropertyValue(pref).toString(), langTagPattern);
            }
            if (indi.getPropertyValue(alt) != null) {
                altStr = StringUtils.removePattern(indi.getPropertyValue(alt).toString(), langTagPattern);
            }
            if (indi.getPropertyValue(hidden) != null) {
                hiddenStr = StringUtils.removePattern(indi.getPropertyValue(hidden).toString(), langTagPattern);
            }
            if (StringUtils.isNotBlank(prefStr)) {
                indisList.add(prefStr);
            }
            if (StringUtils.isNotBlank(altStr)) {
                indisList.add(altStr);
            }
            if (StringUtils.isNotBlank(hiddenStr)) {
                indisList.add(hiddenStr);
            }
        }
        return indisList;
    }

    public static boolean ignoreRes(String uri) {
        if (StringUtils.isBlank(uri)) {
            return true;
        }
        String[] ignoreNs = {XSD.getURI(),
                "http://www.w3.org/2001/XMLSchema#",
                "urn:swrl#",
                Consts.NS_FN,
                Consts.NS_SWRL,
                Consts.NS_SPARQL,
                OWL2.getURI(),
                RDFS.getURI(),
                RDF.getURI()};
        for (String ig : ignoreNs) {
            if (uri.equalsIgnoreCase(ig)) {
                return true;
            }
        }
        return false;
    }

}
