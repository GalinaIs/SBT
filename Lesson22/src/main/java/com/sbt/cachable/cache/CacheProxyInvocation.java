package com.sbt.cachable.cache;

import com.sbt.cachable.cacheUtils.CacheException;
import com.sbt.cachable.cacheUtils.CacheStorage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.sbt.cachable.cacheUtils.CacheUtils.defineCacheStorage;

public class CacheProxyInvocation implements InvocationHandler {
    private Object obj;

    CacheProxyInvocation(Object obj) {
        this.obj = obj;
    }

    private Object methodWithoutCache(Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        Object result = method.invoke(obj, args);
        System.out.println("No annotation Cachable. Finished " + method.getName() + ". Result '" + result + "'");
        return result;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        System.out.println("\nStarted " + method.getName());

        if (!method.isAnnotationPresent(Cachable.class))
            return methodWithoutCache(method, args);

        try {
            CacheStorage cacheStorage = defineCacheStorage(method, args);
            Object result = cacheStorage.checkCache();

            if (result == null) {
                result = method.invoke(obj, args);
                cacheStorage.putInCache(result);
            }

            System.out.println("Annotation Cachable. Finished " + method.getName() + ". Result '" + result + "'");
            return result;
        } catch (CacheException ex) {
            ex.printStackTrace();
            return methodWithoutCache(method, args);
        }
    }
}
