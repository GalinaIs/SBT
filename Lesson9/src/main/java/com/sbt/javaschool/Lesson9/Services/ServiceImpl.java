package com.sbt.javaschool.Lesson9.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public double doHardWork(String line, double x) {
        System.out.println("Working doHardWork(String, double)");
        return x + line.length();
    }

    @Override
    public String doHardWork(String item, double value, Date date) {
        System.out.println("Working doHardWork(String, double, Date)");
        return item + value + date;
    }

    @Override
    public List<String> doHardWork(String item) {
        System.out.println("Working doHardWork(String)");
        List<String> listString = new ArrayList<>();
        listString.add(item);
        listString.add(item);
        return  listString;
    }

    @Override
    public Person getPeople() {
        return new Person("Jonh", 18);
    }
}