package com.sbt.dao;

import com.sbt.exception.NoUniqueEntityException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

abstract class AbstractDao<T> {
    NamedParameterJdbcTemplate jdbc;

    AbstractDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    T selectById(long id, String sql, RowMapper<T> rowMapper) {
        List<T> result = jdbc.query(sql, new MapSqlParameterSource("id", id), rowMapper);

        if (result.size() == 0) return null;
        return result.get(0);
    }

    List<T> selectAll(String sql, RowMapper<T> rowMapper) {
        return jdbc.query(sql, rowMapper);
    }

    void update(T entity, String sql, String sqlCheckUnique, RowMapper<T> rowMapper)
            throws NoUniqueEntityException {
        checkUnique(entity, sqlCheckUnique, rowMapper);
        jdbc.update(sql, new BeanPropertySqlParameterSource(entity));
    }

    T insert(T entity, String sql, String sqlCheckUnique, RowMapper<T> rowMapper)
            throws NoUniqueEntityException {
        update(entity, sql, sqlCheckUnique, rowMapper);
        return jdbc.query(sqlCheckUnique, new BeanPropertySqlParameterSource(entity), rowMapper).get(0);
    }

    private void checkUnique(T entity, String sqlCheckUnique, RowMapper<T> rowMapper) throws NoUniqueEntityException {
        if (jdbc.query(sqlCheckUnique, new BeanPropertySqlParameterSource(entity), rowMapper).size() > 0)
            throw new NoUniqueEntityException("Entity " + entity + " already exists in DB");
    }

    void delete(long id, String sql) {
        jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    T findByName(String name, String sql, RowMapper<T> rowMapper) {
        List<T> ingredientList = jdbc.query(sql, new MapSqlParameterSource("name", name), rowMapper);
        if (ingredientList.size() == 0) return null;
        return ingredientList.get(0);
    }
}
