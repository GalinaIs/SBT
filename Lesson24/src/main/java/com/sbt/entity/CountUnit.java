package com.sbt.entity;

public class CountUnit {
    private long id;
    private String name;

    public CountUnit() {
    }

    public CountUnit(String name) {
        this.name = name;
    }

    public CountUnit(long id) {
        this.id = id;
    }

    public CountUnit(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
