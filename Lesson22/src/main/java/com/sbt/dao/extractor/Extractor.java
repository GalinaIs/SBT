package com.sbt.dao.extractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Extractor<T>{
    public abstract T extractOne(ResultSet resultSet) throws SQLException;

    public List<T> extractAll(ResultSet resultSet) throws SQLException {
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractOne(resultSet));
        }
        return result;
    }
}
