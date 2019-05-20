package com.sbt.cachable.cacheExample;

import com.sbt.cachable.cache.CacheProxy;

public class CacheMain {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy();
        Calculator calculator = (Calculator) cacheProxy.cache(new CalculatorImpl());

        calculator.fibonachi(45);
        calculator.fibonachi(45);
        calculator.fibonachi(46);
        calculator.fibonachi(46);
        calculator.fibonachi(45);
        calculator.fibonachi(45);
        calculator.fibonachi(45);
        calculator.fibonachi(45);

        Service service = (Service) cacheProxy.cache(new ServiceImpl());
        service.getPeople();
        service.getPeople();
        service.getPeople();
        service.getPeople();
    }
}
