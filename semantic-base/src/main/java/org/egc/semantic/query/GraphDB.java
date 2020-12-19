package org.egc.semantic.query;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://henrietteharmse.com/2018/06/29/getting-started-with-ontotext-graphdb-and-rdf4j/
 * https://henrietteharmse.com/2018/06/29/getting-started-with-ontotext-and-jena/
 *
 * @author houzhiwei
 * @date 2020/12/16 14:50
 */
public class GraphDB {
    private static final Logger log = LoggerFactory.getLogger(GraphDB.class);

    private static final String REPO_SERVER =
            "http://localhost:7200/repositories";
    private static final String REPO_ID = "egc_model";

    private static final String REPO_QUERY =
            "http://localhost:7200/repositories/egc_model";
    private static final String REPO_UPDATE =
            "http://localhost:7200/repositories/egc_model/statements";


    /*************************************
     *              jena
     ************************************/
    private static void insert(String strInsert) {
        UpdateRequest updateRequest = UpdateFactory.create(strInsert);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, REPO_UPDATE);
        updateProcessor.execute();
    }


    private static void query(String strQuery) {
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(REPO_QUERY, strQuery);
        for (ResultSet results = queryExecution.execSelect(); results.hasNext(); ) {
            QuerySolution qs = results.next();
            String strName = qs.get("?name").toString();
            log.trace("name = " + strName);
        }
        queryExecution.close();
    }
}
