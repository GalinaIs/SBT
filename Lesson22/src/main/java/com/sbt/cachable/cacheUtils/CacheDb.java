package com.sbt.cachable.cacheUtils;

import java.io.IOException;

import static com.sbt.cachable.cacheDb.DbUtils.getResultFromDb;
import static com.sbt.cachable.cacheDb.DbUtils.saveResultInDb;
import static com.sbt.cachable.serialize.SerializeUtil.arrayBytesToObject;
import static com.sbt.cachable.serialize.SerializeUtil.objectToArrayByte;

public class CacheDb implements CacheStorage {
    private final String identityName;

    CacheDb(String identityName) {
        this.identityName = identityName;
    }

    @Override
    public Object checkCache() throws CacheException {
        try {
            byte[] bytes = getResultFromDb(identityName);
            if (bytes == null) return null;
            return arrayBytesToObject(bytes);
        } catch (IOException | ClassNotFoundException e) {
            throw new CacheException("Exception in checkCache ", e);
        }
    }

    @Override
    public void putInCache(Object result) throws CacheException{
        try {
            byte[] bytes = objectToArrayByte(result);
            saveResultInDb(identityName, bytes);
        } catch (IOException e) {
            throw new CacheException("Exception in putInCache ", e);
        }
    }
}
