package com.sbt.javaschool.lesson6.classTest;

public class Test {
    private String name;
    private boolean man;

    public Test() {
        this("Test", false);
    }

    public Test(String name, boolean man) {
        this.name = name;
        this.man = man;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", man=" + man +
                '}';
    }
}
