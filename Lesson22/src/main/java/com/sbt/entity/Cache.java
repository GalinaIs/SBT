package com.sbt.entity;

import java.util.Arrays;

public class Cache {
    private int id;
    private String name;
    private byte[] result;

    public Cache(int id, String name, byte[] result) {
        this.id = id;
        this.name = name;
        this.result = result;
    }

    public Cache(String name, byte[] result) {
        this.name = name;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
