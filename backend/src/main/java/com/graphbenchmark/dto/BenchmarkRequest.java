package com.graphbenchmark.dto;

public class BenchmarkRequest {

    private int depth;

    public BenchmarkRequest() {
    }

    public BenchmarkRequest(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}