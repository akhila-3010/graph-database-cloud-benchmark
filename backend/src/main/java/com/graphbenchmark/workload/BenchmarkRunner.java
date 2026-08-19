package com.graphbenchmark.workload;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.Driver;
import org.springframework.stereotype.Component;

import com.graphbenchmark.metrics.BenchmarkMetric;
import com.graphbenchmark.metrics.StatisticsCalculator;

@Component
public class BenchmarkRunner {

	private static final int WARMUP = 1;
	private static final int ITERATIONS = 5;

    private final StatisticsCalculator calculator;

    public BenchmarkRunner(StatisticsCalculator calculator) {
        this.calculator = calculator;
    }

    public BenchmarkMetric run(
            GraphWorkload workload,
            Driver driver) {

        for (int i = 0; i < WARMUP; i++) {
            workload.execute(driver);
        }

        List<Double> latencies = new ArrayList<>();

        long totalStart = System.nanoTime();

        for (int i = 0; i < ITERATIONS; i++) {

            long start = System.nanoTime();

            workload.execute(driver);

            long end = System.nanoTime();

            latencies.add(
                    (end - start) / 1_000_000.0
            );
        }

        long totalEnd = System.nanoTime();

        double totalTime =
                (totalEnd - totalStart) / 1_000_000.0;

        return calculator.calculate(
                workload.getName(),
                latencies,
                totalTime
        );
    }
}