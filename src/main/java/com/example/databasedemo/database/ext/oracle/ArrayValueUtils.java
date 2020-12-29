package com.example.databasedemo.database.ext.oracle;

import oracle.jdbc.driver.OracleConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;

public class ArrayValueUtils {
    private ArrayValueUtils(){
        /**/
    }

    public static<T> Array createOracleArray(Connection conn, String typeName, T[] values) throws SQLException {
        if (conn.isWrapperFor(OracleConnection.class)) {
            OracleConnection oracleConn = conn.unwrap(OracleConnection.class);
            return oracleConn.createOracleArray(typeName, values);
        } else {
            // recover, not an oracle connection
            return conn.createArrayOf(typeName, values);
        }
    }
}
