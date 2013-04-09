/**
 * JWatch - Quartz Monitor: http://code.google.com/p/jwatch/
 * Copyright (C) 2011 Roy Russo and the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 **/

package org.jwatch.persistence;

import org.apache.log4j.Logger;
import org.hsqldb.Server;
import org.jwatch.util.SettingsUtil;

import java.sql.*;

/**
 * Some portions taken from: http://hsqldb.org/doc/guide/apb.html
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: 7/7/11 1:55 PM
 */
public class ConnectionUtil
{
    static Logger log = Logger.getLogger(ConnectionUtil.class);
    Connection conn;
    // 'Server' is a class of HSQLDB representing
    // the database server
    Server hsqlServer = null;

    public ConnectionUtil(String db_file_name_prefix) throws Exception
    {
        hsqlServer = new Server();
        // HSQLDB prints out a lot of informations when
        // starting and closing, which we don't need now.
        // Normally you should point the setLogWriter
        // to some Writer object that could store the logs.
        //hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(false);
        hsqlServer.setDatabaseName(0, "JWATCHDB");
        hsqlServer.setDatabasePath(0, "file:" + SettingsUtil.getDBFilePath());
        hsqlServer.start();

        Class.forName("org.hsqldb.jdbcDriver");
        // "jdbc:hsqldb:hsql://localhost/xdb", "sa", "");
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/JWATCHDB", "SA", "");
        //conn = DriverManager.getConnection("jdbc:hsqldb:file:" + db_file_name_prefix, "sa", "");
        log.debug("JWatch Database connected.");
    }

    public void shutdown() throws SQLException
    {
        Statement st = conn.createStatement();

        st.execute("SHUTDOWN");
        conn.close();

        if (hsqlServer != null)
        {
            hsqlServer.shutdown();
        }
        log.debug("JWatch Database shutdown.");
    }

    public synchronized void query(String expression) throws SQLException
    {
        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement();         // statement objects can be reused with

        // repeated calls to execute but we
        // choose to make a new one each time
        rs = st.executeQuery(expression);    // run the query

        // do something with the result set.
        dump(rs);
        st.close();    // NOTE!! if you close a statement the associated ResultSet is

        // closed too
        // so you should copy the contents to some other object.
        // the result set is invalidated also  if you recycle an Statement
        // and try to execute some other query before the result set has been
        // completely examined.
    }

    //use for SQL commands CREATE, DROP, INSERT and UPDATE
    public synchronized void update(String expression) throws SQLException
    {
        Statement st = null;
        st = conn.createStatement();    // statements
        int i = st.executeUpdate(expression);    // run the query
        if (i == -1)
        {
            System.out.println("db error : " + expression);
        }
        st.close();
    }

    public static void dump(ResultSet rs) throws SQLException
    {        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta = rs.getMetaData();
        int colmax = meta.getColumnCount();
        int i;
        Object o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); )
        {
            for (i = 0; i < colmax; ++i)
            {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }
            System.out.println(" @@ ");
        }
    }

}
