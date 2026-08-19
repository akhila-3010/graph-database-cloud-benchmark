package com.graphbenchmark.metrics;

public class BenchmarkMetric {

    private String benchmarkName;
    private double averageLatency;
    private double minLatency;
    private double maxLatency;
    private double p50Latency;
    private double p95Latency;
    private double throughput;

    public BenchmarkMetric() {
    }

    public BenchmarkMetric(String benchmarkName,
                           double averageLatency,
                           double minLatency,
                           double maxLatency,
                           double p50Latency,
                           double p95Latency,
                           double throughput) {

        this.benchmarkName = benchmarkName;
        this.averageLatency = averageLatency;
        this.minLatency = minLatency;
        this.maxLatency = maxLatency;
        this.p50Latency = p50Latency;
        this.p95Latency = p95Latency;
        this.throughput = throughput;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public double getAverageLatency() {
        return averageLatency;
    }

    public void setAverageLatency(double averageLatency) {
        this.averageLatency = averageLatency;
    }

    public double getMinLatency() {
        return minLatency;
    }

    public void setMinLatency(double minLatency) {
        this.minLatency = minLatency;
    }

    public double getMaxLatency() {
        return maxLatency;
    }

    public void setMaxLatency(double maxLatency) {
        this.maxLatency = maxLatency;
    }

    public double getP50Latency() {
        return p50Latency;
    }

    public void setP50Latency(double p50Latency) {
        this.p50Latency = p50Latency;
    }

    public double getP95Latency() {
        return p95Latency;
    }

    public void setP95Latency(double p95Latency) {
        this.p95Latency = p95Latency;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }
}