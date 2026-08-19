package com.graphbenchmark.report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.graphbenchmark.metrics.BenchmarkMetric;

public class BenchmarkReport {

    private String databaseName;
    private LocalDateTime generatedTime;
    private List<BenchmarkMetric> benchmarks;

    public BenchmarkReport() {
        this.generatedTime = LocalDateTime.now();
        this.benchmarks = new ArrayList<>();
    }

    public BenchmarkReport(String databaseName) {
        this.databaseName = databaseName;
        this.generatedTime = LocalDateTime.now();
        this.benchmarks = new ArrayList<>();
    }

    public void addMetric(BenchmarkMetric metric) {
        benchmarks.add(metric);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
    }

    public List<BenchmarkMetric> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(List<BenchmarkMetric> benchmarks) {
        this.benchmarks = benchmarks;
    }
}