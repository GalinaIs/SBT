package com.sbt.jdbc.connectionFactory;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryProxool implements ConnectionFactory {
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String LOGIN = "sa";
    private static final String PASSWORD = "";
    private static final String PROXOOL_ALIAS = "proxool.javadb";

    public ConnectionFactoryProxool() {
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("There is no Proxool diver in classpath");
        }
        Properties info = new Properties();
        info.setProperty("proxool.maximum-connection-count", "10");
        info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
        info.setProperty("user", LOGIN);
        info.setProperty("password", PASSWORD);
        String url = PROXOOL_ALIAS + ":" + DRIVER_CLASS_NAME + ":" + JDBC_URL;

        try {
            ProxoolFacade.registerConnectionPool(url, info);
        } catch (ProxoolException e) {
            throw new RuntimeException("Some error with configuration Proxool", e);
        }
    }

    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(PROXOOL_ALIAS);
    }
}
