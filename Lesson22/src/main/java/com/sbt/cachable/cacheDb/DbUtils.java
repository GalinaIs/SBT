package com.sbt.cachable.cacheDb;

import com.sbt.cachable.cacheUtils.CacheException;
import com.sbt.dao.CacheDao;
import com.sbt.dao.CacheDaoImpl;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Cache;
import com.sbt.jdbc.connectionFactory.ConnectionFactory;
import com.sbt.jdbc.connectionFactory.ConnectionFactoryProxool;
import com.sbt.jdbc.transactionManager.TransactionManager;
import com.sbt.jdbc.transactionManager.TransactionManagerImpl;

public class DbUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactoryProxool();
    private static TransactionManager txManager = new TransactionManagerImpl(connectionFactory);
    private static CacheDao cacheDao = new CacheDaoImpl(txManager);

    public static byte[] getResultFromDb(String name) throws CacheException {
        try {
            Cache cache = txManager.doInTransaction(() -> cacheDao.selectByName(name));
            return cache.getResult();
        } catch (NoSuchEntityException e) {
            return null;
        } catch (Exception ex) {
            throw new CacheException("Exception in getResultFromDb", ex);
        }
    }

    public static void saveResultInDb(String name, byte[] result) throws CacheException {
        try {
            Cache cache = new Cache(name, result);
            System.out.println(txManager.doInTransaction(() -> cacheDao.insert(cache)));
        } catch (Exception ex) {
            throw new CacheException("Exception in getResultFromDb", ex);
        }
    }
}
