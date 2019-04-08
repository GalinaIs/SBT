import com.sbt.lesson12.*;
import com.sbt.lesson12.account.Account;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String... args) {
        //testFixedThreadPool();
        //testScalableThreadPool();
    }

    private static void testScalableThreadPool() {
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(5, 10);
        scalableThreadPool.start();

        List<TaskThread> tasks = new ArrayList<>();
        for (int k = 0; k < 10; k++) {
            tasks.add(new TaskThread(new Account(10000), 1000, 3));
        }

        for (TaskThread taskThread : tasks) {
            scalableThreadPool.execute(taskThread);
        }

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int k = 0; k < 5; k++) {
            scalableThreadPool.execute(tasks.get(k));
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scalableThreadPool.stop();
    }

    private static void testFixedThreadPool() {
        FixedThreadPool fixedThreadPool = new FixedThreadPool(5);
        fixedThreadPool.start();

        List<TaskThread> tasks = new ArrayList<>();
        for (int k = 0; k < 15; k++) {
            tasks.add(new TaskThread(new Account(10000), 1000, 3));
        }

        for (TaskThread taskThread : tasks) {
            fixedThreadPool.execute(taskThread);
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fixedThreadPool.stop();
    }
}