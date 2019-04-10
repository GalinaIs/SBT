import com.sbt.lesson13.Task;
import com.sbt.lesson13.executionManager.*;
import com.sbt.lesson13.executionManager.account.Account;

import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String... args) {
        //testTask();
        try {
            testExecutionManager();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testTask() {
        final Task<Integer> task = new Task<>(() -> {
            TimeUnit.SECONDS.sleep(2);
            return new Random().nextInt(100);
            //throw new Exception("Some problem with calculation");
        });

        Runnable runnable = new Runnable() {
            public void run() {
                int result = task.get();
                System.out.println(Thread.currentThread().getName() + " " + result);
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    private static void printContextInfo(Context context) {
        System.out.println("getCompletedTaskCount: " + context.getCompletedTaskCount());
        System.out.println("getFailedTaskCount: " + context.getFailedTaskCount());
        System.out.println("getInterruptedTaskCount: " + context.getInterruptedTaskCount());
        System.out.println("isFinished: " + context.isFinished());
    }

    private static void testExecutionManager() throws InterruptedException {

        Thread[] tasks = new Thread[20];
        for (int k = 0; k < 10; k++) {
            tasks[2 * k] = new TaskThread(new Account(10000), 1000, 3);
            tasks[2 * k + 1] = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Context contextExecutionManager = new ExecutionManagerImpl().execute(() -> {
            System.out.println("I am callback");
        }, tasks);

        printContextInfo(contextExecutionManager);

        TimeUnit.SECONDS.sleep(2);
        contextExecutionManager.interrupt();

        printContextInfo(contextExecutionManager);

        TimeUnit.SECONDS.sleep(2);

        printContextInfo(contextExecutionManager);
    }
}