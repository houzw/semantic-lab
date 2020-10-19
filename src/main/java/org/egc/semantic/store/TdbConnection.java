package org.egc.semantic.store;

import org.apache.jena.jdbc.JenaDriver;
import org.apache.jena.jdbc.tdb.TDBDriver;
import org.apache.jena.jdbc.tdb.connections.TDBConnection;
import org.egc.commons.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static TdbConnection createTdbConnection() {
        return new TdbConnection();
    }

    private TdbConnection() {

    }

    /**
     * Persistent disk backed RDF database
     * Only	driver that	supports transactions
     * Maven artifact: jena-jdbc-driver-tdb
     *
     * @return URL tdb connection url
     */
    public String getTdbConnectionUrl() {
        Properties properties = PropertiesUtil.readPropertiesFromConfig("semantic");
        return JenaDriver.DRIVER_PREFIX + TDBDriver.TDB_DRIVER_PREFIX
                + TDBDriver.PARAM_LOCATION + "=" + properties.getProperty("jena.tdb.dbLocation")
                + TDBDriver.PARAM_MUST_EXIST + "=" + properties.getProperty("jena.jdbc.tdb.must-exist");
    }

    /**
     * Gets tdb connection.
     *
     * @return the tdb connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException {
        TDBDriver driver = new TDBDriver();
        // driver must registered with the JDBC driver manager before connect
        registerJenaDriver(driver, "The TDBDriver cannot be registered");
        this.conn = getConnection(getTdbConnectionUrl());
        return this.conn;
    }

    /**
     * Close tdb connection.
     *
     * @throws SQLException the sql exception
     */
    public void closeConnection() throws SQLException {
        String msg = "Close TDB JDBC Connection failed";
        closeConnection(this.conn, msg);
    }

}
