package com.graphbenchmark.report;

import org.springframework.stereotype.Component;

import com.graphbenchmark.metrics.BenchmarkMetric;

@Component
public class CsvReportGenerator implements ReportGenerator {

    @Override
    public String generate(BenchmarkReport report) {

        StringBuilder builder = new StringBuilder();

        builder.append(
                "Benchmark,"
                + "Average,"
                + "Minimum,"
                + "Maximum,"
                + "P50,"
                + "P95,"
                + "Throughput\n"
        );

        for (BenchmarkMetric metric : report.getBenchmarks()) {

            builder.append(metric.getBenchmarkName()).append(",");

            builder.append(metric.getAverageLatency()).append(",");

            builder.append(metric.getMinLatency()).append(",");

            builder.append(metric.getMaxLatency()).append(",");

            builder.append(metric.getP50Latency()).append(",");

            builder.append(metric.getP95Latency()).append(",");

            builder.append(metric.getThroughput()).append("\n");

        }

        return builder.toString();

    }

}