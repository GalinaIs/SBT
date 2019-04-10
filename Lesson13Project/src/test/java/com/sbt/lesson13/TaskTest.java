package com.sbt.lesson13;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void get() {
        final Task<Integer> task = new Task<Integer>(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                //return new Random().nextInt(100);
                throw new Exception("Some problem with calculation");
            }
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
}