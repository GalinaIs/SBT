package com.sbt.lesson12.account;

public class Account {
    private static int countAccount = 0;
    private final int id;
    private int amount;

    public Account(int amount) {
        this.amount = amount;
        countAccount++;
        id = countAccount;
    }

    public void payCheck(int sumCheck) {
        amount -= sumCheck;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}