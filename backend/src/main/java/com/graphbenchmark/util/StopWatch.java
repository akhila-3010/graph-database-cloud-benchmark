package com.graphbenchmark.util;

public class StopWatch {

    private long start;
    private long end;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        end = System.nanoTime();
    }

    public double elapsedMillis() {
        return (end - start) / 1_000_000.0;
    }
}