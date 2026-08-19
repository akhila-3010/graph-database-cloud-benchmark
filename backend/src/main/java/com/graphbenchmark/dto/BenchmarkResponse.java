package com.graphbenchmark.dto;

import com.graphbenchmark.metrics.BenchmarkMetric;

public class BenchmarkResponse {

    private String message;
    private BenchmarkMetric metric;

    public BenchmarkResponse() {
    }

    public BenchmarkResponse(
            String message,
            BenchmarkMetric metric) {

        this.message = message;
        this.metric = metric;

    }

    public String getMessage() {
        return message;
    }

    public BenchmarkMetric getMetric() {
        return metric;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMetric(BenchmarkMetric metric) {
        this.metric = metric;
    }

}