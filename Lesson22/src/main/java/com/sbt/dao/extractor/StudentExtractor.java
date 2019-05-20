package com.sbt.dao.extractor;

import com.sbt.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentExtractor extends Extractor<Student> {
    @Override
    public Student extractOne(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String lastName = resultSet.getString("lastName");
        String firstName = resultSet.getString("firstName");

        return new Student(id, lastName, firstName);
    }
}
