package com.sbt.jdbc.transactionManager;

import java.sql.Connection;
import java.util.concurrent.Callable;

public interface TransactionManager {
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;

    Connection getConnection();
}