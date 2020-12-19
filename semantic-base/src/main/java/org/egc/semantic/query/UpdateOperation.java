package org.egc.semantic.query;

import org.apache.jena.query.Dataset;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

/**
 * Description:
 * <pre>
 * https://www.w3.org/TR/sparql11-update
 * https://jena.apache.org/documentation/query/update.html
 * each operation added needs to be a complete SPARQL Update operation, including prefixes if needed.
 * </pre>
 *
 * @author houzhiwei
 * @date 2019 /9/3 10:55
 */
public class UpdateOperation {

    /**
     * Update by sparql update file.
     *
     * @param ruFilePath the sparql update file path, e.g., "update.ru"
     * @param dataset    the dataset
     */
    public void updateByFile(String ruFilePath, Dataset dataset) {
        UpdateAction.readExecute(ruFilePath, dataset);
    }

    /**
     * Update by sparql string.
     *
     * @param sparql  the sparql
     * @param dataset the dataset
     */
    public void updateByString(String sparql, Dataset dataset) {
        UpdateAction.parseExecute(sparql, dataset);
    }

    /**
     * Exec update.
     *
     * @param sparql  the sparql
     * @param dataset the dataset
     */
    public void execUpdate(String sparql, Dataset dataset) {
        UpdateRequest request = UpdateFactory.create();
        request.add(sparql);
        UpdateAction.execute(request, dataset);
    }

}
