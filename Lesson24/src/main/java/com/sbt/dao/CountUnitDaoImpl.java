package com.sbt.dao;

import com.sbt.entity.CountUnit;
import com.sbt.exception.NoUniqueEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CountUnitDaoImpl extends AbstractDao<CountUnit> implements CountUnitDao {
    private static final String SELECT_BY_ID = "select id, name from countUnit where id = :id";
    private static final String SELECT_ALL = "select id, name from countUnit";
    private static final String DELETE_BY_ID = "delete from countUnit where id = :id";
    private static final String INSERT = "INSERT INTO countUnit (name) VALUES (:name)";
    private static final String UPDATE = "UPDATE countUnit SET name = :name WHERE id = :id";;
    private static final String SELECT_BY_NAME = "select id, name from countUnit where name = :name";

    private final RowMapper<CountUnit> rowMapper = (resultSet, i) ->
            new CountUnit(resultSet.getLong("id"), resultSet.getString("name"));

    @Autowired
    protected CountUnitDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public CountUnit selectById(long id) {
        return selectById(id, SELECT_BY_ID, rowMapper);
    }

    @Override
    public List<CountUnit> selectAll() {
        return selectAll(SELECT_ALL, rowMapper);
    }

    @Override
    public void insert(CountUnit entity) throws NoUniqueEntityException {
        long id = insert(entity, INSERT, SELECT_BY_NAME, rowMapper).getId();
        entity.setId(id);
    }

    @Override
    public void update(CountUnit entity) throws NoUniqueEntityException {
        update(entity, UPDATE, SELECT_BY_NAME, rowMapper);
    }

    @Override
    public void delete(CountUnit entity) {
        delete(entity.getId(), DELETE_BY_ID);
    }

    @Override
    public CountUnit findCountUnitByName(String name) {
        return findByName(name, SELECT_BY_NAME, rowMapper);
    }
}
