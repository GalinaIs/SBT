package com.sbt.javaschool.lesson6.classTest;

public class PersonTest {
    private Person person;

    public PersonTest() {
        this(new Person());
    }
    public PersonTest(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonTest{" +
                "person=" + person +
                '}';
    }
}
