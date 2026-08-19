package com.graphbenchmark.util;

import java.util.List;

public final class BenchmarkUtil {

    private BenchmarkUtil() {
    }

    public static double average(
            List<Double> values) {

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

    }

    public static double min(
            List<Double> values) {

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0);

    }

    public static double max(
            List<Double> values) {

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);

    }

}