package com.sbt.dao;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Cache;

public interface CacheDao extends Dao<Cache> {
    Cache selectByName(String name) throws DaoSystemException, NoSuchEntityException;
}