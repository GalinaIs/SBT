package com.sbt.dao.extractor;

import com.sbt.entity.Cache;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CacheExtractor extends Extractor<Cache> {
    @Override
    public Cache extractOne(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        byte[] result = resultSet.getBytes("result");
        return new Cache(id, name, result);
    }
}
