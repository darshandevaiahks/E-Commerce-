package com.amesingstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String MYSQL_HOST = System.getenv("MYSQLHOST");
    private static final String MYSQL_PORT = System.getenv("MYSQLPORT");
    private static final String MYSQL_DB   = System.getenv("MYSQLDATABASE");
    private static final String MYSQL_USER = System.getenv("MYSQLUSER");
    private static final String MYSQL_PASS = System.getenv("MYSQLPASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + MYSQL_DB
                     + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, MYSQL_USER, MYSQL_PASS);
    }
}
