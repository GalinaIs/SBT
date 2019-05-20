package com.sbt.dao;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonDao extends Dao<Lesson> {
    List<Lesson> selectByName(String name) throws DaoSystemException, NoSuchEntityException;
    List<Lesson> selectByDate(LocalDate date) throws DaoSystemException, NoSuchEntityException;
}
