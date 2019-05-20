package com.sbt.jdbc.connectionFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryC3P0 implements ConnectionFactory {
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String LOGIN = "sa";
    private static final String PASSWORD = "";

    private final ComboPooledDataSource poolSource;

    public ConnectionFactoryC3P0() {
        try {
            poolSource = new ComboPooledDataSource();
            poolSource.setDriverClass(DRIVER_CLASS_NAME);
            poolSource.setJdbcUrl(JDBC_URL);
            poolSource.setUser(LOGIN);
            poolSource.setPassword(PASSWORD);

            /*Внутренний тюнинг пула*/
            poolSource.setMaxStatements(180);
            poolSource.setMaxStatementsPerConnection(10);
            poolSource.setMinPoolSize(1);
            poolSource.setAcquireIncrement(1);
            poolSource.setMaxPoolSize(20);

        } catch (PropertyVetoException e) {
            throw new RuntimeException("Exception during configuration C3P0", e);
        }
    }

    public Connection newConnection() throws SQLException {
        return poolSource.getConnection();
    }
}
