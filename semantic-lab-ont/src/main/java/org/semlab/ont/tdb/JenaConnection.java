package org.semlab.ont.tdb;

import org.apache.jena.jdbc.JenaDriver;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Jena JDBC Connection
 *
 * @author houzhiwei
 * @date 2017/4/24 19:14
 */
public interface JenaConnection
{
    /**
     * <pre>
     * Ensure that the driver we wish to use is registered
     * with the JDBC driver manager before establish a connection
     * e.g.: TDBDriver.register();
     * </pre>
     *
     * @param driver JenaDriver
     * @param msg    exception message
     * @throws SQLException
     */
    void registerJenaDriver(JenaDriver driver, String msg) throws SQLException;

    /**
     * Open a Jena JDBC Connection
     *
     * @param url connection url
     * @return java.sql.Connection
     * @throws SQLException
     */
    Connection getConnection(String url) throws SQLException;

    /**
     * close a Jena JDBC connection
     *
     * @param msg exception message
     * @throws SQLException
     */
    void closeConnection(String msg) throws SQLException;
}
