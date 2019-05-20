package com.sbt.dao.enricher;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;

public interface Enricher<T> {
    void enrich(T record) throws DaoSystemException, NoSuchEntityException;
}
