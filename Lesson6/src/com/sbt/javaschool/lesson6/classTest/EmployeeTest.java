package com.sbt.javaschool.lesson6.classTest;

public class EmployeeTest {
    private Emlpoyee person;

    public EmployeeTest() {
        this(new Emlpoyee());
    }

    public EmployeeTest(Emlpoyee person) {
        this.person = person;
    }

    public Emlpoyee getPerson() {
        return person;
    }

    public void setPerson(Emlpoyee person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "EmployeeTest{" +
                "person=" + person +
                '}';
    }
}
