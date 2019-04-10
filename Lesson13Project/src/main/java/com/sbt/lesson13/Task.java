package com.sbt.lesson13;

import java.util.concurrent.Callable;

public class Task<T> {
    private final Object lock = new Object();
    private final Callable<? extends T> callable;
    private volatile T resultTask;
    private volatile TaskException resultTaskException;
    private volatile boolean isResult;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    private void calculateResult() {
        if (!isResult) {
            try {
                isResult = true;
                resultTask = callable.call();
            } catch (Exception e) {
                resultTaskException = new TaskException(
                        "Exception in Task.get() function. Thread: " + Thread.currentThread().getName(), e);
            }
        }
    }

    public synchronized T get() {
        if (!isResult) {
            synchronized (lock) {
                calculateResult();
                lock.notify();
            }
        }

        if (resultTaskException != null) throw resultTaskException;

        return resultTask;
    }
}
