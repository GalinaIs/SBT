package com.sbt.async;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        long startTime = System.nanoTime();

        Future<Long> someValueFuture = executor.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return 1L;
        });

        // Doing something while value is calculated
        TimeUnit.SECONDS.sleep(3);

        Long someValue = someValueFuture.get();
        long endTime = System.nanoTime();

        System.out.println("It took " + TimeUnit.NANOSECONDS.toSeconds(endTime - startTime) + " seconds");
        executor.shutdown();
    }

}
