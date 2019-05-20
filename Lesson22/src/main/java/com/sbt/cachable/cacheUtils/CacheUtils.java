package com.sbt.cachable.cacheUtils;

import com.sbt.cachable.cache.Cachable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class CacheUtils {
    private static String getCacheMemberName(Method method, Object[] args) {
        StringBuilder result = new StringBuilder();
        int indexArgs = 0;

        for (Type type : method.getParameterTypes()) {
            if (result.length() > 0) result.append(" ");
            result.append(type).append("_");
            result.append("args").append(indexArgs).append("_");
            result.append(args[indexArgs]);
            indexArgs++;
        }

        return result.toString();
    }

    public static CacheStorage defineCacheStorage(Method method, Object[] args) {
        Cachable cache = method.getAnnotation(Cachable.class);
        String identityName = method.getName() + "_" + getCacheMemberName(method, args);

        if (cache.persistent())
            return new CacheDb(identityName);

        return new CacheMemory(identityName);
    }
}
