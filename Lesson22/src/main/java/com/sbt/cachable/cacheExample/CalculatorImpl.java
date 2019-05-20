package com.sbt.cachable.cacheExample;

public class CalculatorImpl implements Calculator {
    public int fibonachi(int n) {
        if (n < 0) throw new IllegalArgumentException(n + " must be more than 0");

        if (n == 0 || n == 1) return 1;

        return fibonachi(n - 1) + fibonachi(n - 2);
    }
}