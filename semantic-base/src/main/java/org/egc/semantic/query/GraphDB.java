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

    private String REPO_QUERY;
    private String REPO_UPDATE;

    private String repoServer;
    private String repoId;

    public GraphDB(String repoServer, String repoId) {
        this.repoId = repoId;
        this.repoServer = repoServer;
        this.REPO_QUERY = repoServer + "/" + repoId;
        this.REPO_UPDATE = repoServer + "/" + repoId + "/statements";
    }

    /*************************************
     *              jena
     ************************************/
    public void insert(String strInsert) {
        UpdateRequest updateRequest = UpdateFactory.create(strInsert);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, REPO_UPDATE);
        updateProcessor.execute();
    }

    public ResultSet query(String strQuery) {
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(REPO_QUERY, strQuery);
        ResultSet results = queryExecution.execSelect();
//        RDFOutput.encodeAsModel(results);
        queryExecution.close();
        return results;
    }

    public void query(String strQuery, String variable) {
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(REPO_QUERY, strQuery);
        for (ResultSet results = queryExecution.execSelect(); results.hasNext(); ) {
            QuerySolution qs = results.next();
            String strName = qs.get("?" + variable).toString();
            System.out.println(variable + " = " + strName);
        }
        queryExecution.close();
    }
}
