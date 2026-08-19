package com.graphbenchmark.model;

public class BenchmarkReport {

    private String database;

    private String benchmarkName;

    private double averageLatency;

    private double p50Latency;

    private double p95Latency;

    private double throughput;


    public BenchmarkReport(
            String database,
            String benchmarkName,
            double averageLatency,
            double p50Latency,
            double p95Latency,
            double throughput) {

        this.database = database;
        this.benchmarkName = benchmarkName;
        this.averageLatency = averageLatency;
        this.p50Latency = p50Latency;
        this.p95Latency = p95Latency;
        this.throughput = throughput;
    }

    public String getDatabase() {
        return database;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public double getAverageLatency() {
        return averageLatency;
    }

    public double getP50Latency() {
        return p50Latency;
    }

    public double getP95Latency() {
        return p95Latency;
    }

    public double getThroughput() {
        return throughput;
    }

}