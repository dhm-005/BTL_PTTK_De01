package com.pttk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO implements AutoCloseable {

    protected Connection con;

    public DAO() {
        String dbUrl = "jdbc:mysql://localhost:3306/libman?autoReconnect=true&useSSL=false";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl, "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Could not connect to the library database.", e);
        }
    }

    @Override
    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
