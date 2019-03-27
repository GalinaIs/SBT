package com.sbt.javaschool.Lesson9;

import com.sbt.javaschool.Lesson9.Utils.*;

import java.io.NotSerializableException;
import java.lang.reflect.*;
import java.util.*;

public class CacheProxyInvocation implements InvocationHandler {
    private Object obj;
    private CacheUtils cacheUtils;

    public CacheProxyInvocation(String filePath, Object obj) {
        this.obj = obj;
        FileCacheUtils.setFilePath(filePath);
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
                cacheUtils = new FileCacheUtils(identityName, cache.listSize(), cache.zip());
                break;
            case IN_MEMORY:
                cacheUtils = new MemoryCacheUtils(identityName, cache.listSize());
                break;

        }
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException, NotSerializableException {
        System.out.println("\nStarted " + method.getName());
        if (!method.isAnnotationPresent(Cache.class)) {
            Object result = method.invoke(obj, args);
            System.out.println("Finished " + method.getName() + ". Result '" + result + "'");
            return result;
        }

        defineCacheUtils(method, args);
        Object result = cacheUtils.checkCache();

        if (result == null) {
            result = method.invoke(obj, args);
            cacheUtils.putInCache(result);
        }

        System.out.println("Finished " + method.getName() + ". Result '" + result + "'\n");
        return result;
    }
}
