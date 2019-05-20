package com.sbt.dao.filler;

import com.sbt.entity.Cache;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CacheFiller extends Filler<Cache> {

    private void fillParamForInsUpd(Cache entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setString(1, entity.getName());
        prepStatement.setBytes(2, entity.getResult());
    }

    @Override
    public void fillParamForInsert(Cache entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
    }

    @Override
    public void fillParamForUpdate(Cache entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
        prepStatement.setInt(3, entity.getId());
    }

    @Override
    public void fillParamForDelete(Cache entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setInt(1, entity.getId());
    }
}
