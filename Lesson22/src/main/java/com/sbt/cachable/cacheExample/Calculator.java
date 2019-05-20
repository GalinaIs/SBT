package com.sbt.cachable.cacheExample;

import com.sbt.cachable.cache.Cachable;

public interface Calculator {

    @Cachable(persistent = true)
    int fibonachi(int n);

}
