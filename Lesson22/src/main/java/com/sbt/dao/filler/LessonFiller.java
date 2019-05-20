package com.sbt.dao.filler;

import com.sbt.entity.Lesson;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LessonFiller extends Filler<Lesson> {

    private void fillParamForInsUpd(Lesson entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setString(1, entity.getName());
        prepStatement.setDate(2, Date.valueOf(entity.getDate()));
    }

    @Override
    public void fillParamForInsert(Lesson entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
    }

    @Override
    public void fillParamForUpdate(Lesson entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
        prepStatement.setInt(3, entity.getId());
    }

    @Override
    public void fillParamForDelete(Lesson entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setInt(1, entity.getId());
    }
}
