package com.sbt.cachable.cache;

import java.lang.reflect.Proxy;

public class CacheProxy {

    public Object cache(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), new CacheProxyInvocation(object));
    }
}
