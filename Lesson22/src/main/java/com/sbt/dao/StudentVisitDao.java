package com.sbt.dao;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Lesson;
import com.sbt.entity.Student;
import com.sbt.entity.StudentVisit;

import java.util.List;

public interface StudentVisitDao extends Dao<StudentVisit> {
    int deleteByLesson(Lesson lesson) throws DaoSystemException, NoSuchEntityException;

    int deleteByStudent(Student student) throws DaoSystemException, NoSuchEntityException;

    List<StudentVisit> selectByLesson(Lesson lesson) throws DaoSystemException, NoSuchEntityException;

    List<StudentVisit> selectByStudent(Student student) throws DaoSystemException, NoSuchEntityException;
}
