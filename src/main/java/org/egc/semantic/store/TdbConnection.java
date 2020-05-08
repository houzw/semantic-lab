package org.egc.semantic.store;

import org.apache.jena.jdbc.JenaDriver;
import org.apache.jena.jdbc.tdb.TDBDriver;
import org.apache.jena.jdbc.tdb.connections.TDBConnection;
import org.egc.commons.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/24 16:07
 */
@Service
public class TdbConnection extends JenaConnection {
    private static final Logger logger = LoggerFactory.getLogger(TdbConnection.class);
    private Connection conn = null;
    private String connectionUrl = "";

    /**
     * Persistent disk backed RDF database
     * Only	driver that	supports transactions
     * Maven artifact: jena-jdbc-driver-tdb
     *
     * @return URL tdb connection url
     */
    public String getTdbConnectionUrl() {
        Properties properties = PropertiesUtil.readPropertiesFromConfig("semantic");
        connectionUrl = JenaDriver.DRIVER_PREFIX + TDBDriver.TDB_DRIVER_PREFIX
                + TDBDriver.PARAM_LOCATION + "=" + properties.getProperty("jena.tdb.dbLocation")
                + TDBDriver.PARAM_MUST_EXIST + "=" + properties.getProperty("jena.jdbc.tdb.must-exist");
        return connectionUrl;
    }

    /**
     * Gets tdb connection.
     *
     * @return the tdb connection
     * @throws SQLException the sql exception
     */
    public TDBConnection getTdbConnection() throws SQLException {
        TDBDriver driver = new TDBDriver();
        // driver must registered with the JDBC driver manager before connect
        registerJenaDriver(driver, "The TDBDriver cannot be registered");
        return (TDBConnection) getConnection(getTdbConnectionUrl());
    }

    /**
     * Close tdb connection.
     *
     * @throws SQLException the sql exception
     */
    public void closeTdbConnection() throws SQLException {
        String msg = "Close TDB JDBC Connection failed";
        logger.error(msg);
        closeConnection(msg);
    }


}
