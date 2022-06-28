package org.diplomado.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
public class ConexionBaseDatos {
    private static String url="jdbc:mysql://localhost:3300/java_curso";
    private static String username="root";
    private static String password="12345";
    //private static Connection conn;

    private static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException{
        if (pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            //cantidad de conexiones
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(5);
            pool.setMaxTotal(8);
        }

        return pool;
    }

     public static Connection getConnection() throws SQLException{
        return getInstance().getConnection();
     }
}
