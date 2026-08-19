package com.graphbenchmark.util;

public final class TimerUtil {

    private TimerUtil() {
    }

    public static long start() {
        return System.nanoTime();
    }

    public static double stopMilliseconds(long startTime) {
        return (System.nanoTime() - startTime) / 1_000_000.0;
    }

}