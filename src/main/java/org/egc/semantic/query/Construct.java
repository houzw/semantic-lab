package org.egc.semantic.query;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/9/3 10:21
 */
public interface Construct {

    Model construct(String sparql, Model model);

    default Model execConstruct(String sparql, Model model) {
        Query query = QueryFactory.create(sparql, Syntax.syntaxARQ);
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        Model resultModel = queryExec.execConstruct(model);
        return resultModel;
    }

}
