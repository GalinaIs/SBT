package com.sbt.cachable.cacheExample;

import com.sbt.cachable.cache.Cachable;

public interface Service {
    @Cachable(persistent = true)
    Person getPeople();
}