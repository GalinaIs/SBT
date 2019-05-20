package com.sbt.cachable.cacheExample;

public class ServiceImpl implements Service {
    @Override
    public Person getPeople() {
        return new Person("John", 18);
    }
}