package com.sbt.dao.extractor;

import com.sbt.entity.StudentVisit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentVisitExtractor extends Extractor<StudentVisit> {
    @Override
    public StudentVisit extractOne(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idStudent = resultSet.getInt("studentId");
        int idLesson = resultSet.getInt("lessonId");

        return new StudentVisit(id, idStudent, idLesson);
    }
}