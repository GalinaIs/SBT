package com.sbt.javaschool.Lesson9;

import java.lang.reflect.Proxy;

public class CacheProxy {
    private final String filePath;

    public CacheProxy(String filePath) {
        this.filePath = filePath;
    }

    public Object cache(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), new CacheProxyInvocation(filePath, object));
    }
}