package com.sbt.dao;

import com.sbt.dao.enricher.CacheEnricher;
import com.sbt.dao.enricher.Enricher;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.dao.exception.NoUniqueEntityException;
import com.sbt.dao.extractor.CacheExtractor;
import com.sbt.dao.extractor.Extractor;
import com.sbt.dao.filler.CacheFiller;
import com.sbt.dao.filler.Filler;
import com.sbt.entity.Cache;
import com.sbt.jdbc.transactionManager.TransactionManager;

import java.util.List;

public class CacheDaoImpl extends AbstractDao<Cache> implements CacheDao {
    private static final String SELECT_BY_NAME = "SELECT id, name, result FROM cache WHERE name = ?";
    private static final String SELECT_BY_ID = "SELECT id, name, result FROM cache WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, name, result FROM cache";
    private static final String INSERT_ONE = "INSERT INTO cache (name, result) VALUES (?, ?)";
    private static final String UPDATE_ONE = "UPDATE cache SET name = ?, result = ? WHERE id = ?";
    private static final String DELETE_ONE = "DELETE FROM cache WHERE id = ?";

    private final Filler<Cache> filler = new CacheFiller();
    private final Extractor<Cache> extractor = new CacheExtractor();
    private final Enricher<Cache> enricher = new CacheEnricher();

    public CacheDaoImpl(TransactionManager transactionManager) {
        super(transactionManager);
    }

    public Cache selectByName(String name) throws DaoSystemException, NoSuchEntityException {
        return selectByString(name, SELECT_BY_NAME, extractor, enricher).get(0);
    }

    @Override
    public Cache selectById(int id) throws DaoSystemException, NoSuchEntityException {
        return selectById(id, SELECT_BY_ID, extractor, enricher);
    }

    @Override
    public List<Cache> selectAll() throws DaoSystemException, NoSuchEntityException {
        return selectAll(SELECT_ALL, extractor, enricher);
    }

    @Override
    public int insert(Cache newEntity) throws DaoSystemException, NoUniqueEntityException {
        return insert(newEntity, null, INSERT_ONE, filler);
    }

    @Override
    public int update(Cache newEntity) throws DaoSystemException, NoUniqueEntityException {
        return update(newEntity, null, UPDATE_ONE, filler);
    }

    @Override
    public int delete(Cache entity) throws DaoSystemException, NoSuchEntityException {
        return delete(entity, DELETE_ONE, filler);
    }
}
