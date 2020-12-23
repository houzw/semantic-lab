package org.egc.semantic.test;

import org.egc.semantic.query.GraphDB;
import org.junit.Test;

/**
 * @author houzhiwei
 * @date 2020/12/21 16:01
 */
public class GraphDBTest {
    static final String REPO_SERVER =
            "http://localhost:7200/repositories";
    static final String REPO_ID = "egc_model";
    static final String REPO_QUERY =
            "http://localhost:7200/repositories/egc_model";
    static final String REPO_UPDATE =
            "http://localhost:7200/repositories/egc_model/statements";

    String query = "PREFIX process: <http://www.egc.org/ont/process#>\n" +
            "\n" +
            "select * where { \n" +
            "\tprocess:hasMandatorySourceData ?p ?o .\n" +
            "} limit 100 ";
    @Test
    public void testJena() {
        GraphDB db = new GraphDB(REPO_SERVER, REPO_ID);
        db.query(query,"o");
    }
}
