package com.sbt.lesson12;

interface ThreadPool {
    void start();
    void execute(Runnable runnable);
    void stop();
}
