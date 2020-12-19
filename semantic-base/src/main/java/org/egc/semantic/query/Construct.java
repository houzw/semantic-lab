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
    /**
     * Construct model.
     *
     * @param sparql the sparql
     * @param model  the model
     * @return the model
     */
    default Model execConstruct(String sparql, Model model) {
        Query query = QueryFactory.create(sparql, Syntax.syntaxARQ);
        QueryExecution queryExec = QueryExecutionFactory.create(query, model);
        return queryExec.execConstruct(model);
    }

    /**
     * Print result.
     *
     * @param constructResult the execConstruct result
     */
    void printResult(Model constructResult);
}
