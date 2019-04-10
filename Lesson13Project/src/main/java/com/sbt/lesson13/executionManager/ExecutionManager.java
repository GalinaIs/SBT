package com.sbt.lesson13.executionManager;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}