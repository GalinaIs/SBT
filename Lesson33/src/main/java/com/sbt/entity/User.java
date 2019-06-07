package com.sbt.entity;

import java.time.LocalDate;

public class User {
    private final String name;
    private final LocalDate dateOfBirth;
    private final Company company;

    public User(String name, LocalDate dateOfBirth, Company company) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", company=" + company +
                '}';
    }
}
