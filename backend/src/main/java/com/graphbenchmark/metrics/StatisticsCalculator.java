package com.graphbenchmark.metrics;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class StatisticsCalculator {

    private final PercentileCalculator percentileCalculator;

    public StatisticsCalculator(
            PercentileCalculator percentileCalculator) {

        this.percentileCalculator = percentileCalculator;
    }

    public BenchmarkMetric calculate(
            String benchmarkName,
            List<Double> latencies,
            double totalExecutionTime) {

        double sum = latencies.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        double avg = sum / latencies.size();

        double min = latencies.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0);

        double max = latencies.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);

        double p50 =
                percentileCalculator.percentile(latencies, 0.50);

        double p95 =
                percentileCalculator.percentile(latencies, 0.95);

        double throughput =
                latencies.size() / (totalExecutionTime / 1000.0);

        return new BenchmarkMetric(
                benchmarkName,
                avg,
                min,
                max,
                p50,
                p95,
                throughput);
    }
}