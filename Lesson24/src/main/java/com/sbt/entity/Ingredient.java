package com.sbt.entity;

public class Ingredient {
    private long id;
    private String name;
    private long count;
    private CountUnit countUnit;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(long id, String name, long count, CountUnit countUnit) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.countUnit = countUnit;
    }

    public Ingredient(String name, long count, CountUnit countUnit) {
        this.name = name;
        this.count = count;
        this.countUnit = countUnit;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public CountUnit getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(CountUnit countUnit) {
        this.countUnit = countUnit;
    }

    @Override
    public String toString() {
        return "\n\t\t" +
                " Название: " + name +
                ", количество: " + count + " " + countUnit;
    }
}
