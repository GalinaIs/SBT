package com.sbt.dao.extractor;

import com.sbt.entity.Lesson;

import java.sql.*;
import java.time.LocalDate;

public class LessonExtractor extends Extractor<Lesson> {
    @Override
    public Lesson extractOne(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        LocalDate date = resultSet.getDate("date").toLocalDate();

        return new Lesson(id, name, date);
    }
}