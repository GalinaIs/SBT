package com.sbt.jdbc.connectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryJdbc implements ConnectionFactory {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String LOGIN = "sa";
    private static final String PASSWORD = "";

    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, LOGIN, PASSWORD);
    }
}
