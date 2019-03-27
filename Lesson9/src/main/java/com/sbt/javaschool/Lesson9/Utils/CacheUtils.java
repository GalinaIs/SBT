package com.sbt.javaschool.Lesson9.Utils;

import java.io.NotSerializableException;

public interface CacheUtils {
    Object checkCache();
    void putInCache(Object result) throws NotSerializableException;
}
