package com.sbt.lesson12;

import java.util.*;

public class ScalableThreadPool implements ThreadPool {
    private int minCountThread;
    private int maxCountThread;
    private List<WorkerThread> listThread;
    private List<Runnable> tasks;
    private final Thread demonThread = new Thread(new ControlCountThreadDemon());
    private int countFreeThread;
    private final Object lockTask = new Object();
    private final Object lockCountFreeTread = new Object();

    public ScalableThreadPool(int min, int max) {
        minCountThread = min;
        maxCountThread = max;

        tasks = Collections.synchronizedList(new LinkedList<>());
        listThread = Collections.synchronizedList(new ArrayList<>(max));
        for (int i = 0; i < min; i++) {
            listThread.add(new WorkerThread());
        }
    }

    public void start() {
        for (WorkerThread workerThread : listThread) {
            workerThread.start();
        }

        countFreeThread = minCountThread;
        demonThread.setDaemon(true);
        demonThread.start();
    }

    public void execute(Runnable runnable) {
        tasks.add(runnable);
        synchronized (lockTask) {
            lockTask.notify();
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
                    Runnable task;
                    synchronized (lockTask) {
                        while (tasks.size() == 0)
                            lockTask.wait();
                        task = tasks.remove(0);
                    }

                    synchronized (lockCountFreeTread) {
                        countFreeThread--;
                    }

                    task.run();

                    synchronized (lockCountFreeTread) {
                        countFreeThread++;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class ControlCountThreadDemon implements Runnable {
        private void addNewThread() {
            int countThreadsForAdd = 0;
            int countTask = tasks.size();
            if (countTask > 0 && listThread.size() < maxCountThread) {
                countThreadsForAdd = Math.min(countTask, maxCountThread - listThread.size());
            }

            for (int k = 0; k < countThreadsForAdd; k++) {
                WorkerThread workerThread = new WorkerThread();
                listThread.add(workerThread);
                workerThread.start();
            }
        }

        private void deleteThread() {
            int countThreadsForDelete = 0;
            synchronized (lockCountFreeTread) {
                if (tasks.size() == 0 && countFreeThread > minCountThread)
                    countThreadsForDelete = countFreeThread - minCountThread;
            }

            for (int k = 0; k < countThreadsForDelete; k++) {
                listThread.remove(listThread.size() - 1).interrupt();
            }
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                addNewThread();
                deleteThread();
            }
        }
    }
}
