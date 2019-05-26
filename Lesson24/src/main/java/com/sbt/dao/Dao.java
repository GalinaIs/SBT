package com.sbt.dao;

import com.sbt.exception.NoUniqueEntityException;

import java.util.List;

public interface Dao<T> {
    T selectById(long id);

    List<T> selectAll();

    void insert(T entity) throws NoUniqueEntityException;

    void update(T entity) throws NoUniqueEntityException;

    void delete(T entity);
}
