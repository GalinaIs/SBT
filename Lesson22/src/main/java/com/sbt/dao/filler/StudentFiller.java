package com.sbt.dao.filler;

import com.sbt.entity.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentFiller extends Filler<Student> {
    private void fillParamForInsUpd(Student entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setString(1, entity.getLastName());
        prepStatement.setString(2, entity.getFirstName());
    }

    @Override
    public void fillParamForInsert(Student entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
    }

    @Override
    public void fillParamForUpdate(Student entity, PreparedStatement prepStatement) throws SQLException {
        fillParamForInsUpd(entity, prepStatement);
        prepStatement.setInt(3, entity.getId());
    }

    @Override
    public void fillParamForDelete(Student entity, PreparedStatement prepStatement) throws SQLException {
        prepStatement.setInt(1, entity.getId());
    }
}
