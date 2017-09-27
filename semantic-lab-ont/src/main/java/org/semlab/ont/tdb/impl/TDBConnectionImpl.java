package org.semlab.ont.tdb.impl;

import org.apache.jena.jdbc.JenaDriver;
import org.apache.jena.jdbc.tdb.TDBDriver;
import org.apache.jena.jdbc.tdb.connections.TDBConnection;
import org.semlab.ont.tdb.TdbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/24 16:07
 */
@Service
public class TdbConnectionImpl extends JenaConnectionImpl implements TdbConnection
{
    private static final Logger logger = LoggerFactory.getLogger(TdbConnectionImpl.class);
    private Connection conn = null;
    private String connectionUrl = "";

    // get properties from application-[profile].yml
    @Autowired
    private Environment env;

    @Override
    public String getTdbConnectionUrl()
    {
        connectionUrl = JenaDriver.DRIVER_PREFIX + TDBDriver.TDB_DRIVER_PREFIX
                + TDBDriver.PARAM_LOCATION + "=" + env.getProperty("jena.tdb.dbLocation")
                + TDBDriver.PARAM_MUST_EXIST + "=" + env.getProperty("jena.jdbc.tdb.must-exist");
        return connectionUrl;
    }

    @Override
    public TDBConnection getTdbConnection() throws SQLException
    {
        TDBDriver driver = new TDBDriver();
        // driver must registered with the JDBC driver manager before connect
        registerJenaDriver(driver, "The TDBDriver cannot be registered");
        TDBConnection tdbConnection = (TDBConnection) getConnection(getTdbConnectionUrl());
        return tdbConnection;
    }

    @Override
    public void closeTdbConnection() throws SQLException
    {
        String msg = "Close TDB JDBC Connection failed";
        logger.error(msg);
        closeConnection(msg);
    }


}
