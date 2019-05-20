package com.sbt.dao.filler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Filler<T> {
    public abstract void fillParamForInsert(T entity, PreparedStatement prepStatement) throws SQLException;

    public abstract void fillParamForUpdate(T entity, PreparedStatement prepStatement) throws SQLException;

    public abstract void fillParamForDelete(T entity, PreparedStatement prepStatement) throws SQLException;
}
