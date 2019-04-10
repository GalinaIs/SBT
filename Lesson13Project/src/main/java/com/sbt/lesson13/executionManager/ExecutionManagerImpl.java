package com.sbt.lesson13.executionManager;

import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {
    private static final int MAX_COUNT_THREAD = 10;
    private BlockingQueue<Runnable> tasksQueue;
    private Runnable callback;
    private volatile int completedTaskCount;
    private volatile int failedTaskCount;
    private volatile int interruptedTaskCount;
    private volatile boolean isFinished;
    private final Object lockTaskQueue = new Object();
    private final Object lockCompletedTask = new Object();
    private final Object lockFailedTask = new Object();
    private final Object lockIsFinished = new Object();

    public Context execute(Runnable callback, Runnable... tasks) {
        tasksQueue = new ArrayBlockingQueue<>(tasks.length);

        for (Runnable task : tasks) {
            try {
                tasksQueue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.callback = callback;
        start();

        return new ContextImpl();
    }

    private void start() {
        int countThread = Math.min(tasksQueue.size(), MAX_COUNT_THREAD);
        for (int i = 0; i < countThread; i++) {
            new WorkerThread().start();
        }
    }

    private void stop() {
        synchronized (lockTaskQueue) {
            interruptedTaskCount = tasksQueue.size();
            tasksQueue.clear();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isFinished) {
                try {
                    workThread();
                } catch (InterruptedException e) {
                    synchronized (lockFailedTask) {
                        failedTaskCount++;
                    }
                }
            }
        }

        private void taskRun(Runnable task) {
            task.run();
            synchronized (lockCompletedTask) {
                completedTaskCount++;
            }
        }

        private void workThread() throws InterruptedException {
            taskRun(tasksQueue.take());

            Runnable myCallback = null;
            synchronized (lockIsFinished) {
                if (!isFinished && tasksQueue.size() == 0) {
                    myCallback = callback;
                    isFinished = true;
                }
            }

            if (myCallback != null) {
                taskRun(myCallback);
            }
        }
    }

    private class ContextImpl implements Context {

        public int getCompletedTaskCount() {
            return completedTaskCount;
        }

        public int getFailedTaskCount() {
            return failedTaskCount;
        }

        public int getInterruptedTaskCount() {
            return interruptedTaskCount;
        }

        public void interrupt() {
            stop();
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
