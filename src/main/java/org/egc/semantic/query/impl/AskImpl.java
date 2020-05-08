package org.egc.semantic.query.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.egc.semantic.query.Ask;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2016年3月31日 下午2:19:12
 */
public class AskImpl implements Ask {
    @Override
    public boolean execAsk(String sparqlStr, Model model) {
        Query query = QueryFactory.create(sparqlStr);
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        return queryExec.execAsk();
    }

    @Override
    public boolean execAsk(String subUri, String propUri, String objUri, Model model) {
        StringBuilder s = new StringBuilder("ASK { ");
        if (StringUtils.isBlank(subUri)) {
            s.append("?s ");
        } else {
            s.append("<").append(subUri).append("> ");
        }
        if (StringUtils.isBlank(propUri)) {
            s.append("?p ");
        } else {
            s.append("<").append(propUri).append("> ");
        }
        if (StringUtils.isBlank(objUri)) {
            s.append("?o");
        } else {
            s.append("<").append(objUri).append("> ");
        }
        s.append(" }");
        Query query = QueryFactory.create(s.toString());
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        return queryExec.execAsk();
    }

    @Override
    public boolean isIndividual(String uri, Model model) {
        return execAsk(uri, RDF.type.getURI(), OWL2.NamedIndividual.getURI(), model);
    }
}
