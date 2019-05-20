package com.sbt.dao;

import com.sbt.dao.enricher.Enricher;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;
import com.sbt.dao.extractor.Extractor;
import com.sbt.dao.filler.Filler;
import com.sbt.jdbc.transactionManager.TransactionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    protected TransactionManager transactionManager;

    protected AbstractDao(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    protected Connection getConnection() {
        Connection connection = transactionManager.getConnection();
        if (connection == null) {
            String msg = "Call DAO methods only 'under Transaction Manager'";
            throw new IllegalStateException(msg);
        }
        return connection;
    }

    protected T getOneEntity(ResultSet resultSet, Extractor<T> extractor, Enricher<T> enricher)
            throws SQLException, DaoSystemException, NoSuchEntityException {
        T record = extractor.extractOne(resultSet);
        enricher.enrich(record);
        return record;
    }

    protected List<T> getAllEntity(ResultSet resultSet, Extractor<T> extractor, Enricher<T> enricher)
            throws SQLException, DaoSystemException, NoSuchEntityException {
        List<T> result = new ArrayList<>();
        do {
            result.add(getOneEntity(resultSet, extractor, enricher));
        } while (resultSet.next());
        return result;
    }

    protected List<T> selectAll(String sql, Extractor<T> extractor, Enricher<T> enricher)
            throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (!resultSet.next()) {
                throw new NoSuchEntityException("No find entity from SQL '" + sql + "'");
            }
            return getAllEntity(resultSet, extractor, enricher);
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL='" + sql + "'", ex);
        }
    }

    protected T selectById(int id, String sql, Extractor<T> extractor, Enricher<T> enricher)
            throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return getOneEntity(resultSet, extractor, enricher);

            throw new NoSuchEntityException("No find entity from SQL '" + sql + "' where id = " + id);
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where id = " + id, ex);
        }
    }

    protected List<T> selectByString(String param, String sql, Extractor<T> extractor, Enricher<T> enricher)
            throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, param);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("No find entity from SQL '" + sql + "' where param = " + param);
            }
            return getAllEntity(resultSet, extractor, enricher);
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where param = " + param, ex);
        }
    }

    private void fillPrepStat(T entity, PreparedStatement preparedStatement, Filler<T> filler, Operation operation)
            throws SQLException {
        switch (operation) {
            case UPDATE:
                filler.fillParamForUpdate(entity, preparedStatement);
                break;
            case DELETE:
                filler.fillParamForDelete(entity, preparedStatement);
                break;
            case INSERT:
                filler.fillParamForInsert(entity, preparedStatement);
                break;
        }
    }

    private int CUDOperation(T entity, String sql, Filler<T> filler, Operation operation) throws DaoSystemException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillPrepStat(entity, preparedStatement, filler, operation);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where entity = " + entity, ex);
        }
    }

    private void checkUniqueEntity(T entity, String sql, Filler<T> filler)
            throws DaoSystemException, NoUniqueEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            filler.fillParamForInsert(entity, preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                throw new NoUniqueEntityException(entity + " already exists in DB");
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where entity = " + entity, ex);
        }
    }

    protected int delete(T entity, String sql, Filler<T> filler) throws DaoSystemException, NoSuchEntityException {
        int result = CUDOperation(entity, sql, filler, AbstractDao.Operation.DELETE);
        if (result == 0) {
            throw new NoSuchEntityException("Entity '" + entity + "' not exists in DB");
        }
        return result;
    }

    protected int insert(T entity, String sqlUnique, String sql, Filler<T> filler)
            throws DaoSystemException, NoUniqueEntityException {
        if (sqlUnique != null)
            checkUniqueEntity(entity, sqlUnique, filler);
        return CUDOperation(entity, sql, filler, AbstractDao.Operation.INSERT);
    }

    protected int update(T entity, String sqlUnique, String sql, Filler<T> filler)
            throws DaoSystemException, NoUniqueEntityException {
        if (sqlUnique != null)
            checkUniqueEntity(entity, sqlUnique, filler);
        return CUDOperation(entity, sql, filler, AbstractDao.Operation.UPDATE);
    }

    private enum Operation {
        UPDATE, DELETE, INSERT
    }
}
