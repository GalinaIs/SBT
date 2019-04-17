package com.sbt.javaschool.Lesson9;

import com.sbt.javaschool.Lesson9.Services.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class MultiThreadExecution {

    public static void start() throws ExecutionException, InterruptedException {
        CacheProxy cacheProxy = new CacheProxy("D:\\JavaSBTGit\\SBT\\Lesson15\\src\\main\\resources\\cache");
        Service service = (Service) cacheProxy.cache(new ServiceImpl());

        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        Supplier<List<String>> supplier = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return service.doHardWork("Hello");
        };

        Supplier<List<String>> supplier1 = () -> service.doHardWork("Hello");

        List<Future<List<String>>> futures = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            futures.add(CompletableFuture.supplyAsync(supplier, threadPool));
            futures.add(CompletableFuture.supplyAsync(supplier1, threadPool));
        }

        for (Future future : futures) {
            System.out.println(future.get());
        }

        threadPool.shutdown();

    }
}
