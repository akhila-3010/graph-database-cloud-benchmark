package com.graphbenchmark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphbenchmark.benchmark.BenchmarkSuite;
import com.graphbenchmark.dto.BenchmarkResponse;
import com.graphbenchmark.dto.ConnectionResponse;
import com.graphbenchmark.metrics.BenchmarkMetric;
import com.graphbenchmark.service.BenchmarkService;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final BenchmarkService service;

    public BenchmarkController(BenchmarkService service) {
        this.service = service;
    }

    @GetMapping("/connect")
    public ConnectionResponse connect() {

        String message = service.connect();

        return new ConnectionResponse(
                message.equals("Connected Successfully"),
                service.getDatabaseName(),
                message);
    }

    @GetMapping("/traversal/{depth}")
    public BenchmarkResponse traversal(
            @PathVariable int depth) {

        if (depth < 1 || depth > 3) {
            throw new IllegalArgumentException(
                    "Depth must be between 1 and 3");
        }

        BenchmarkMetric metric =
                service.runTraversalBenchmark(depth);

        return new BenchmarkResponse(
                "Traversal Benchmark Completed",
                metric);
    }

    @GetMapping("/lookup")
    public BenchmarkResponse lookup() {

        BenchmarkMetric metric =
                service.runLookupBenchmark();

        return new BenchmarkResponse(
                "Lookup Benchmark Completed",
                metric);
    }

    @GetMapping("/aggregation")
    public BenchmarkResponse aggregation() {

        BenchmarkMetric metric =
                service.runAggregationBenchmark();

        return new BenchmarkResponse(
                "Aggregation Benchmark Completed",
                metric);
    }

    @GetMapping("/mixed")
    public BenchmarkResponse mixed() {

        BenchmarkMetric metric =
                service.runMixedBenchmark();

        return new BenchmarkResponse(
                "Mixed Benchmark Completed",
                metric);
    }

    @GetMapping("/run")
    public BenchmarkSuite runBenchmarkSuite() {
        return service.runBenchmarkSuite();
    }

}