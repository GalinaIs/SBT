package com.sbt.dao;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;

import java.util.List;

public interface Dao<T> {
    T selectById(int id) throws DaoSystemException, NoSuchEntityException;

    List<T> selectAll() throws DaoSystemException, NoSuchEntityException;

    int insert(T newEntity) throws DaoSystemException, NoUniqueEntityException;

    int update(T newEntity) throws DaoSystemException, NoUniqueEntityException;

    int delete(T entity) throws DaoSystemException, NoSuchEntityException;
}