package org.semlab.ont.query.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.semlab.ont.query.Ask;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2016年3月31日 下午2:19:12
 */
@Component
public class AskImpl implements Ask
{
    @Override
    public boolean execAsk(String sparqlStr, Model model)
    {
        Query query = QueryFactory.create(sparqlStr);
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        return queryExec.execAsk();
    }

    @Override
    public boolean execAsk(String sub_uri, String prop_uri, String obj_uri, Model model)
    {
        StringBuilder s = new StringBuilder("ASK { ");
        if (StringUtils.isBlank(sub_uri))
            s.append("?s ");
        else
            s.append("<" + sub_uri + "> ");
        if (StringUtils.isBlank(prop_uri))
            s.append("?p ");
        else
            s.append("<" + prop_uri + "> ");
        if (StringUtils.isBlank(obj_uri))
            s.append("?o");
        else
            s.append("<" + obj_uri + "> ");

        s.append(" }");
        Query query = QueryFactory.create(s.toString());
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        return queryExec.execAsk();
    }

    @Override
    public boolean isIndividual(String uri, Model model)
    {
        return execAsk(uri, RDF.type.getURI(), OWL2.NamedIndividual.getURI(), model);
    }
}
