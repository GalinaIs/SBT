package com.sbt.dao.enricher;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Cache;

public class CacheEnricher implements Enricher<Cache> {
    @Override
    public void enrich(Cache record) throws DaoSystemException, NoSuchEntityException {
        /*NOP*/
    }
}
