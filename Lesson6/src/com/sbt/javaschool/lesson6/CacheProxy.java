package com.sbt.javaschool.lesson6;

import java.lang.reflect.*;
import java.util.*;

public class CacheProxy implements InvocationHandler {
    private Object obj;
    private HashMap<String, Object> cacheObject;

    public CacheProxy(Object obj) {
        this.obj = obj;
        cacheObject = new HashMap<>();
    }

    private Object checkCache(Method method, Object[] args) {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            String key = cache.cacheName() + " " + method.getName();
            if (args != null)
                key += args.toString();
            Object result = cacheObject.get(key);
            if (result != null) return result;
        }
        return null;
    }

    private void putInCache(Method method, Object[] args, Object result) {
        if (!method.isAnnotationPresent(Cache.class)) return;

        Cache cache = method.getAnnotation(Cache.class);
        String key = cache.cacheName() + " " + method.getName();
        if (args != null)
            key += args.toString();
        cacheObject.put(key, result);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        System.out.println("Started " + method.getName());
        Object result = checkCache(method, args);
        if (result == null) {
            result = method.invoke(obj, args);
            putInCache(method, args, result);
        }

        System.out.println("Finished " + method.getName() + ". Result " + result);
        return result;
    }
}
