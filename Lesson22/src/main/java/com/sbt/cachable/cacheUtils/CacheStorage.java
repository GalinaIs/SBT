package com.sbt.cachable.cacheUtils;

public interface CacheStorage {
    Object checkCache() throws CacheException;
    void putInCache(Object result) throws CacheException;
}