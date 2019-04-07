package com.sbt.lesson12;

import java.util.*;
import java.util.concurrent.*;

public class FixedThreadPool implements ThreadPool {
    private List<WorkerThread> listThread;
    private BlockingQueue<Runnable> tasks;

    public FixedThreadPool(int countThread) {
        tasks = new ArrayBlockingQueue<>(countThread);
        listThread = new ArrayList<>(countThread);
        for (int i = 0; i < countThread; i++) {
            listThread.add(new WorkerThread());
        }
    }

    public void start() {
        for (WorkerThread workerThread : listThread) {
            workerThread.start();
        }
    }

    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        for (WorkerThread workerThread : listThread) {
            workerThread.interrupt();
        }
    }


    class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
