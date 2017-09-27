package org.semlab.ont.tdb;

import org.apache.jena.jdbc.tdb.connections.TDBConnection;

import java.sql.SQLException;

/**
 * <pre>
 * Connect to Jena TDB with Jena JDBC TDBDriver
 * create/connect TDBDataset and query/update it through SPARQL: Select/Ask/Construct/Describe
 * </pre>
 *
 * @author houzhiwei
 * @date 2017 /4/24 15:32
 */
public interface TdbConnection
{
    /**
     * Persistent disk backed RDF database
     * Only	driver that	supports transactions
     * Maven artifact: jena-jdbc-driver-tdb
     *
     * @return URL tdb connection url
     */
    String getTdbConnectionUrl();

    /**
     * Gets tdb connection.
     *
     * @return the tdb connection
     * @throws SQLException the sql exception
     */
    TDBConnection getTdbConnection() throws SQLException;

    /**
     * Close tdb connection.
     *
     * @throws SQLException the sql exception
     */
    void closeTdbConnection() throws SQLException;
}
