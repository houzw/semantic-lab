package org.egc.semantic.store;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.jdbc.JenaDriver;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017 /4/24 20:25
 */
public abstract class JenaConnection {

    public static final String DBPEDIA = "http://dbpedia.org/";
    public static final String SPARQL = "sparql";
    public static final String UPDATE = "update";
    private static final Logger logger = LoggerFactory.getLogger(JenaConnection.class);
    private Connection conn = null;

    /**
     * <pre>
     * Ensure that the driver we wish to use is registered
     * with the JDBC driver manager before establish a connection
     * e.g.: TDBDriver.register();
     * </pre>
     *
     * @param driver JenaDriver
     * @param msg    exception message
     * @throws SQLException the sql exception
     */
    public void registerJenaDriver(JenaDriver driver, String msg) throws SQLException
    {
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            logger.error(msg);
            throw new SQLException(msg, e);
        }
    }

    /**
     * Open a Jena JDBC Connection
     *
     * @param url connection url
     * @return java.sql.Connection connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection(String url) throws SQLException
    {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Open Jena Connection " + url + " failed!");
            throw new SQLException("Open Jena Connection " + url + " failed!", e);
        }
        return conn;
    }

    /**
     * close a Jena JDBC connection
     *
     * @param msg exception message
     * @throws SQLException the sql exception
     */
    public void closeConnection(String msg) throws SQLException
    {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new SQLException(msg, e);
            }
        } else {
            logger.info("Not Connected");
        }
    }

    /**
     * Rdf connection fuseki rdf connection.
     *
     * @param endpoint       endpoint url
     * @param queryEndpoint  the query endpoint
     * @param updateEndpoint the update endpoint
     * @return rdf connection
     */
    public RDFConnection rdfConnectionFuseki(String endpoint, String queryEndpoint, String updateEndpoint) {

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(endpoint);
        if (StringUtils.isBlank(queryEndpoint)) {
            queryEndpoint = SPARQL;
        }
        builder.queryEndpoint(queryEndpoint);
        if (StringUtils.isNotBlank(updateEndpoint)) {
            //updateEndpoint = "update";
            builder.updateEndpoint(updateEndpoint);
        }
        return builder.build();
    }

    /**
     * Rdf remote connection rdf connection.
     *
     * @param endpoint       endpoint url
     * @param queryEndpoint  set to "sparql" if blank
     * @param updateEndpoint e.g., "update", set to null if query only
     * @return rdf connection
     */
    public RDFConnection rdfRemoteConnection(String endpoint, String queryEndpoint, String updateEndpoint) {
        if (StringUtils.isBlank(queryEndpoint)) {
            queryEndpoint = SPARQL;
        }
        RDFConnectionRemoteBuilder builder = RDFConnectionRemote.create().destination(endpoint);
        builder.queryEndpoint(queryEndpoint);
        if (StringUtils.isNotBlank(updateEndpoint)) {
            //updateEndpoint = "update";
            builder.updateEndpoint(updateEndpoint);
        }
        return builder.build();
    }
}
