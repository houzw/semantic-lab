package org.semlab.ont.tdb.impl;

import org.apache.jena.jdbc.JenaDriver;
import org.semlab.ont.tdb.JenaConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/24 20:25
 */
public abstract class JenaConnectionImpl implements JenaConnection
{
    private static final Logger logger = LoggerFactory.getLogger(JenaConnectionImpl.class);
    private Connection conn = null;

    @Override
    public void registerJenaDriver(JenaDriver driver, String msg) throws SQLException
    {
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            logger.error(msg);
            throw new SQLException(msg, e);
        }
    }

    @Override
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

    @Override
    public void closeConnection(String msg) throws SQLException
    {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new SQLException(msg, e);
            }
        } else
            logger.info("Jena JDBC Not Connected");
    }
}
