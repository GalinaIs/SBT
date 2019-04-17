package com.sbt.javaschool.Lesson9;

import com.sbt.javaschool.Lesson9.Utils.*;

import java.lang.reflect.*;
import java.util.*;

public class CacheProxyInvocation implements InvocationHandler {
    private Object delegate;
    private CacheUtils cacheUtils;
    private String filePath;

    public CacheProxyInvocation(String filePath, Object delegate) {
        this.delegate = delegate;
        this.filePath = filePath;
    }

    private String getCacheName(Method method, Object[] args, Class[] identityBy) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        int indexArgs = 0;
        boolean isConsiderAllArgs = identityBy.length == 0;

        for (Type type : method.getParameterTypes()) {
            if (isConsiderAllArgs || type.equals(identityBy[index])) {
                if (result.length() > 0) result.append(" ");
                result.append(type).append("_");
                result.append("args").append(indexArgs).append("_");
                result.append(args[indexArgs]);
                if (!isConsiderAllArgs) {
                    index++;
                    if (index == identityBy.length) break;
                }
            }
            indexArgs++;
        }

        return result.toString();
    }

    private void defineCacheUtils(Method method, Object[] args) {
        Cache cache = method.getAnnotation(Cache.class);
        String cacheName = cache.cacheNamePrefix().equals("") ? method.getName() : cache.cacheNamePrefix();
        String identityName = cacheName + "_" + getCacheName(method, args, cache.identityBy());

        List<Class> listInterface = Arrays.asList(method.getReturnType().getInterfaces());
        if (listInterface.contains(List.class)) {
            identityName += " listSize:" + cache.listSize();
        }

        switch (cache.cacheType()) {
            case FILE:
                if (cache.zip())
                    cacheUtils = new FileZipCacheUtils(identityName, cache.listSize(), filePath);
                else
                    cacheUtils = new FileCacheUtils(identityName, cache.listSize(), filePath);

                //return new CacheUtilsInfo(cacheUtilsFile, identityName, cache.listSize());
            case IN_MEMORY:
                cacheUtils = new MemoryCacheUtils(identityName, cache.listSize());
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        System.out.println("Cache Started " + method.getName());

        if (!method.isAnnotationPresent(Cache.class)) {
            Object result = method.invoke(delegate, args);
            System.out.println("Finished " + method.getName() + ". Result '" + result + "'");
            return result;
        }

        defineCacheUtils(method, args);

        Object result = cacheUtils.checkCache();

        if (result == null) {
            result = method.invoke(delegate, args);
            cacheUtils.putInCache(result);
        }

        System.out.println("Cache Finished " + method.getName() + ". Result '" + result + "'");
        return result;
    }
}
