package com.sbt.dao;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Student;

import java.util.List;

public interface StudentDao extends Dao<Student> {
    List<Student> selectByFirstName(String firstName) throws DaoSystemException, NoSuchEntityException;
    List<Student> selectByLastName(String lastName) throws DaoSystemException, NoSuchEntityException;
}
