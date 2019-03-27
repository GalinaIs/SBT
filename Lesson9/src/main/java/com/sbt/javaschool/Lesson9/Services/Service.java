package com.sbt.javaschool.Lesson9.Services;

import com.sbt.javaschool.Lesson9.*;

import java.util.Date;
import java.util.List;

public interface Service {

    public double doHardWork(String line, double x);

    //@Cache(cacheType = CacheType.IN_MEMORY , cacheNamePrefix = "data", identityBy = {String.class})
    @Cache(cacheType = CacheType.FILE, cacheNamePrefix = "data", identityBy = {String.class}, zip = true)
    String doHardWork(String item, double value, Date date);

    @ Cache (cacheType = CacheType.IN_MEMORY, listSize = 1)
    //@Cache(cacheType = CacheType.FILE, listSize = 1, zip = true)
    List<String> doHardWork(String item);

    @Cache(cacheType = CacheType.IN_MEMORY)
    Person getPeople();
}