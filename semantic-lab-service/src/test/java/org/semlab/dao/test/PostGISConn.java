package org.semlab.dao.test;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/3/31 23:53
 */
public class PostGISConn
{
    private Connection conn;

    @Before
    public void conn() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/postgis";
        conn = DriverManager.getConnection(url, "username", "password");
    }

    public void exec(String sql) throws Exception
    {
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    @After
    public void close() throws Exception
    {
        conn.close();
    }
}
