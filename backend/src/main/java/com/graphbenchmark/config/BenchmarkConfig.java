package com.graphbenchmark.config;

import org.springframework.context.annotation.Configuration;


@Configuration
public class BenchmarkConfig {

    public static final int WARMUP_ITERATIONS = 10;

    public static final int BENCHMARK_ITERATIONS = 100;

    public static final int BATCH_SIZE = 1000;

}