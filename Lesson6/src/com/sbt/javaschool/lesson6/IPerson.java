package com.sbt.javaschool.lesson6;

public interface IPerson {
    @Cache(cacheName = "personCache")
    String getName();

    void setName(String name);
}
