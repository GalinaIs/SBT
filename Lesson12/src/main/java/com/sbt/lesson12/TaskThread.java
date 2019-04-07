package com.sbt.lesson12;

import com.sbt.lesson12.account.Account;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TaskThread implements Runnable {
    private Account account;
    private int sum;
    private final int timeWait;

    public TaskThread(Account account, int sum, int timeWait) {
        this.account = account;
        this.sum = sum;
        this.timeWait = timeWait;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        int checkSum = rnd.nextInt(sum);
        if (account.getAmount() >= checkSum) {
            try {
                TimeUnit.SECONDS.sleep(timeWait);
                account.payCheck(checkSum);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            System.out.printf("%s: account payed check %d. Account: %s\n", Thread.currentThread(), checkSum,
                    account);
        }

    }
}
