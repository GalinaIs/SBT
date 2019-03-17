package com.sbt.javaschool.lesson6.classTest;
import com.sbt.javaschool.lesson6.IPerson;

public class Person implements IPerson {
    private String name;
    private int age;
    private boolean man;
    public final String STR = "STR";
    private final String STR1 = "STR1";

    public Person() {
        this("Tom", 18, true);
    }

    public Person(String name, int age, boolean man) {
        this.name = name;
        this.age = age;
        this.man = man;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", man=" + man +
                '}';
    }
}
