package com.sbt.javaschool.lesson6.classTest;

public class Emlpoyee extends Person {
    private int salary;

    public Emlpoyee() {
        super();
        salary = 20000;
    }

    public Emlpoyee(String name, int age, boolean man, int salary) {
        super(name, age, man);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "salary=" + salary +
                '}';
    }
}
