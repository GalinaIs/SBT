package com.sbt.jdbc.transactionManager;

import com.sbt.jdbc.connectionFactory.ConnectionFactory;

import java.sql.Connection;
import java.util.concurrent.Callable;

public class TransactionManagerImpl implements TransactionManager {
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final ConnectionFactory factory;

    public TransactionManagerImpl(ConnectionFactory factory) {
        this.factory = factory;
    }

    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        Connection connection = factory.newConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);

        connectionHolder.set(connection);
        try {
            T result = unitOfWork.call();
            connection.commit();
            return result;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.close();
            connectionHolder.remove();
        }

    }

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
