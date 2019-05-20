package com.sbt.dao;

import com.sbt.dao.enricher.Enricher;
import com.sbt.dao.enricher.LessonEnricher;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;
import com.sbt.dao.extractor.Extractor;
import com.sbt.dao.extractor.LessonExtractor;
import com.sbt.dao.filler.Filler;
import com.sbt.dao.filler.LessonFiller;
import com.sbt.entity.Lesson;
import com.sbt.jdbc.transactionManager.TransactionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class LessonDaoImpl extends AbstractDao<Lesson> implements LessonDao {
    private static final String SELECT_BY_NAME = "SELECT id, name, date FROM lesson WHERE name = ?";
    private static final String SELECT_BY_DATE = "SELECT id, name, date FROM lesson WHERE date = ?";
    private static final String SELECT_BY_ID = "SELECT id, name, date FROM lesson WHERE id = ?";
    private static final String SELECT_BY_NAME_DATE = "SELECT id, name, date FROM lesson WHERE name = ? AND date = ?";
    private static final String SELECT_ALL = "SELECT id, name, date FROM lesson";
    private static final String INSERT_ONE = "INSERT INTO lesson (name, date) VALUES (?, ?)";
    private static final String UPDATE_ONE = "UPDATE lesson SET name = ?, date = ? WHERE id = ?";
    private static final String DELETE_ONE = "DELETE FROM lesson WHERE id = ?";

    private final Filler<Lesson> filler = new LessonFiller();
    private final Extractor<Lesson> extractor = new LessonExtractor();
    private final Enricher<Lesson> enricher = new LessonEnricher();

    public LessonDaoImpl(TransactionManager transactionManager) {
        super(transactionManager);
    }

    @Override
    public List<Lesson> selectByName(String name) throws DaoSystemException, NoSuchEntityException {
        return selectByString(name, SELECT_BY_NAME, extractor, enricher);
    }

    @Override
    public List<Lesson> selectByDate(LocalDate date) throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_DATE)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("No find entity from SQL " + SELECT_BY_DATE + " where date = " + date);
            }
            return getAllEntity(resultSet, extractor, enricher);
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = " + SELECT_BY_DATE + " where date = " + date, ex);
        }
    }

    @Override
    public Lesson selectById(int id) throws DaoSystemException, NoSuchEntityException {
        return selectById(id, SELECT_BY_ID, extractor, enricher);
    }

    @Override
    public List<Lesson> selectAll() throws DaoSystemException, NoSuchEntityException {
        return selectAll(SELECT_ALL, extractor, enricher);
    }

    @Override
    public int insert(Lesson newEntity) throws DaoSystemException, NoUniqueEntityException {
        return insert(newEntity, SELECT_BY_NAME_DATE, INSERT_ONE, filler);
    }

    @Override
    public int update(Lesson newEntity) throws DaoSystemException, NoUniqueEntityException {
        return update(newEntity, SELECT_BY_NAME_DATE, UPDATE_ONE, filler);
    }

    @Override
    public int delete(Lesson entity) throws DaoSystemException, NoSuchEntityException {
        return delete(entity, DELETE_ONE, filler);
    }
}
