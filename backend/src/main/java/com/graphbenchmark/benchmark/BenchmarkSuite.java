package com.graphbenchmark.benchmark;

import java.util.ArrayList;
import java.util.List;

import com.graphbenchmark.metrics.BenchmarkMetric;

public class BenchmarkSuite {

    private final List<BenchmarkMetric> results =
            new ArrayList<>();

    public void addResult(BenchmarkMetric metric) {
        results.add(metric);
    }

    public List<BenchmarkMetric> getResults() {
        return results;
    }
}