package com.sbt.dao;

import com.sbt.dao.enricher.Enricher;
import com.sbt.dao.enricher.StudentVisitEnricher;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;
import com.sbt.dao.extractor.Extractor;
import com.sbt.dao.extractor.StudentVisitExtractor;
import com.sbt.dao.filler.Filler;
import com.sbt.dao.filler.StudentVisitFiller;
import com.sbt.entity.Lesson;
import com.sbt.entity.Student;
import com.sbt.entity.StudentVisit;
import com.sbt.jdbc.transactionManager.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentVisitDaoImpl extends AbstractDao<StudentVisit> implements StudentVisitDao {
    private static final String SELECT_BY_ID = "SELECT id, studentId, lessonId FROM studentVisit WHERE id = ?";
    private static final String SELECT_BY_STUDENT_ID_LESSON_ID =
            "SELECT id, studentId, lessonId FROM studentVisit WHERE studentId = ? AND lessonId = ?";
    private static final String SELECT_BY_STUDENT = "SELECT id, studentId, lessonId FROM studentVisit WHERE studentId = ?";
    private static final String SELECT_BY_LESSON = "SELECT id, studentId, lessonId FROM studentVisit WHERE lessonId = ?";
    private static final String SELECT_ALL = "SELECT id, studentId, lessonId FROM studentVisit";
    private static final String INSERT_ONE = "INSERT INTO studentVisit (studentId, lessonId) VALUES (?, ?)";
    private static final String UPDATE_ONE = "UPDATE studentVisit SET studentId = ?, lessonId = ? WHERE id = ?";
    private static final String DELETE_ONE = "DELETE FROM studentVisit WHERE id = ?";
    private static final String DELETE_BY_STUDENT = "DELETE FROM studentVisit WHERE studentId = ?";
    private static final String DELETE_BY_LESSON = "DELETE FROM studentVisit WHERE lessonId = ?";


    private final Filler<StudentVisit> filler = new StudentVisitFiller();
    private final Extractor<StudentVisit> extractor = new StudentVisitExtractor();
    private final Enricher<StudentVisit> enricher;

    public StudentVisitDaoImpl(TransactionManager transactionManager, StudentDao studentDao, LessonDao lessonDao) {
        super(transactionManager);
        enricher = new StudentVisitEnricher(studentDao, lessonDao);
    }

    @Override
    public StudentVisit selectById(int id) throws DaoSystemException, NoSuchEntityException {
        return selectById(id, SELECT_BY_ID, extractor, enricher);
    }

    @Override
    public List<StudentVisit> selectAll() throws DaoSystemException, NoSuchEntityException {
        return selectAll(SELECT_ALL, extractor, enricher);
    }

    @Override
    public int insert(StudentVisit newEntity) throws DaoSystemException, NoUniqueEntityException {
        return insert(newEntity, SELECT_BY_STUDENT_ID_LESSON_ID, INSERT_ONE, filler);
    }

    @Override
    public int update(StudentVisit newEntity) throws DaoSystemException, NoUniqueEntityException {
        return update(newEntity, SELECT_BY_STUDENT_ID_LESSON_ID, UPDATE_ONE, filler);
    }

    @Override
    public int delete(StudentVisit entity) throws DaoSystemException, NoSuchEntityException {
        return delete(entity, DELETE_ONE, filler);
    }


    private int deleteByParam(int id, String sql) throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new NoSuchEntityException("No find entity from SQL '" + sql + "' where id = " + id);
            }
            return result;
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where id = " + id, ex);
        }
    }

    @Override
    public int deleteByLesson(Lesson lesson) throws DaoSystemException, NoSuchEntityException {
        return deleteByParam(lesson.getId(), DELETE_BY_LESSON);
    }

    @Override
    public int deleteByStudent(Student student) throws DaoSystemException, NoSuchEntityException {
        return deleteByParam(student.getId(), DELETE_BY_STUDENT);
    }

    private List<StudentVisit> selectByParam(int id, String sql) throws DaoSystemException, NoSuchEntityException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NoSuchEntityException("No find entity from SQL '" + sql + "'");
            }
            return getAllEntity(resultSet, extractor, enricher);
        } catch (SQLException ex) {
            throw new DaoSystemException("Can't execute SQL = '" + sql + "' where id = " + id, ex);
        }
    }

    @Override
    public List<StudentVisit> selectByLesson(Lesson lesson) throws DaoSystemException, NoSuchEntityException {
        return selectByParam(lesson.getId(), SELECT_BY_LESSON);
    }

    @Override
    public List<StudentVisit> selectByStudent(Student student) throws DaoSystemException, NoSuchEntityException {
        return selectByParam(student.getId(), SELECT_BY_STUDENT);
    }
}
