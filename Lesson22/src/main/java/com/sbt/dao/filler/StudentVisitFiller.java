package com.sbt.dao.filler;

import com.sbt.entity.StudentVisit;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentVisitFiller extends Filler<StudentVisit> {
    private void fillParamForInsUpd(StudentVisit entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setInt(1, entity.getStudent().getId());
        prepStatement.setInt(2, entity.getLesson().getId());
    }

    @Override
    public void fillParamForInsert(StudentVisit entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
    }

    @Override
    public void fillParamForUpdate(StudentVisit entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
        prepStatement.setInt(3, entity.getId());
    }

    @Override
    public void fillParamForDelete(StudentVisit entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setInt(1, entity.getId());
    }
}
