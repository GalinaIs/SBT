package com.sbt.lesson16;

public class Outer {
    private byte[] data = new byte[16 * 1024 * 1024];

    /*static*/ class ListenerOrCallback {
    }

    public void register() {
        Storage.memory.add(new ListenerOrCallback());
    }
}
