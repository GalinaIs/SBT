package com.sbt.entity;

public class Company {
    private final String name;
    private final String inn;

    public Company(String name, String inn) {
        this.name = name;
        this.inn = inn;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}
