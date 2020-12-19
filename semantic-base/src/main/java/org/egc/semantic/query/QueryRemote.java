package org.egc.semantic.query;

import org.apache.jena.query.ResultSet;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/12/17 20:25
 */
public interface QueryRemote {

    ResultSet query(String queryEndpoint, String strQuery);

    void update(String updateEndpoint, String strUpdate);

}
