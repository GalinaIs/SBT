package com.sbt.dao;

import com.sbt.dao.enricher.Enricher;
import com.sbt.dao.enricher.StudentEnricher;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;
import com.sbt.dao.extractor.Extractor;
import com.sbt.dao.extractor.StudentExtractor;
import com.sbt.dao.filler.Filler;
import com.sbt.dao.filler.StudentFiller;
import com.sbt.entity.Student;
import com.sbt.jdbc.transactionManager.TransactionManager;

import java.util.List;

public class StudentDaoImpl extends AbstractDao<Student> implements StudentDao {
    private static final String SELECT_BY_FIRST_NAME = "SELECT id, lastName, firstName FROM student WHERE firstName = ?";
    private static final String SELECT_BY_LAST_NAME = "SELECT id, lastName, firstName FROM student WHERE lastName = ?";
    private static final String SELECT_BY_LAST_NAME_FIRST_NAME =
            "SELECT id, lastName, firstName FROM student WHERE lastName = ? AND firstName = ?";
    private static final String SELECT_BY_ID = "SELECT id, lastName, firstName FROM student WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, lastName, firstName FROM student";
    private static final String INSERT_ONE = "INSERT INTO student (lastName, firstName) VALUES (?, ?)";
    private static final String UPDATE_ONE = "UPDATE student SET lastName = ?, firstName = ? WHERE id = ?";
    private static final String DELETE_ONE = "DELETE FROM student WHERE id = ?";

    private final Filler<Student> filler = new StudentFiller();
    private final Extractor<Student> extractor = new StudentExtractor();
    private final Enricher<Student> enricher = new StudentEnricher();

    public StudentDaoImpl(TransactionManager transactionManager) {
        super(transactionManager);
    }

    @Override
    public List<Student> selectByFirstName(String firstName) throws DaoSystemException, NoSuchEntityException {
        return selectByString(firstName, SELECT_BY_FIRST_NAME, extractor, enricher);
    }

    @Override
    public List<Student> selectByLastName(String lastName) throws DaoSystemException, NoSuchEntityException {
        return selectByString(lastName, SELECT_BY_LAST_NAME, extractor, enricher);
    }

    @Override
    public Student selectById(int id) throws DaoSystemException, NoSuchEntityException {
        return selectById(id, SELECT_BY_ID, extractor, enricher);
    }

    @Override
    public List<Student> selectAll() throws DaoSystemException, NoSuchEntityException {
        return selectAll(SELECT_ALL, extractor, enricher);
    }

    @Override
    public int insert(Student newEntity) throws DaoSystemException, NoUniqueEntityException {
        return insert(newEntity, SELECT_BY_LAST_NAME_FIRST_NAME, INSERT_ONE, filler);
    }

    @Override
    public int update(Student newEntity) throws DaoSystemException, NoUniqueEntityException {
        return update(newEntity, SELECT_BY_LAST_NAME_FIRST_NAME, UPDATE_ONE, filler);
    }

    @Override
    public int delete(Student entity) throws DaoSystemException, NoSuchEntityException {
        return delete(entity, DELETE_ONE, filler);
    }
}
