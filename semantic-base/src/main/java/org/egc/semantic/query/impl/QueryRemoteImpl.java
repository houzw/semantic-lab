package org.egc.semantic.query.impl;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.egc.semantic.query.QueryRemote;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/12/17 20:27
 */
public class QueryRemoteImpl implements QueryRemote {
    @Override
    public ResultSet query(String queryEndpoint, String strQuery) {
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(queryEndpoint, strQuery);
        ResultSet results = queryExecution.execSelect();
        queryExecution.close();
        return results;
    }

    @Override
    public void update(String updateEndpoint, String strUpdate) {
        UpdateRequest updateRequest = UpdateFactory.create(strUpdate);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, updateEndpoint);
        updateProcessor.execute();
    }
}
