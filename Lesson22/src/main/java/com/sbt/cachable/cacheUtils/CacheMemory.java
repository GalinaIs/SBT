package com.sbt.cachable.cacheUtils;

import java.util.HashMap;
import java.util.Map;

public class CacheMemory implements CacheStorage {
    private static Map<String, Object> cacheMemory = new HashMap<>();
    private final String identityName;

    CacheMemory(String identityName) {
        this.identityName = identityName;
    }

    @Override
    public Object checkCache() {
        return cacheMemory.get(identityName);
    }

    @Override
    public void putInCache(Object result) {
        cacheMemory.put(identityName, result);
    }
}