package com.graphbenchmark.metrics;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PercentileCalculator {

    public double percentile(List<Double> values, double percentile) {

        if (values.isEmpty()) {
            return 0;
        }

        Collections.sort(values);

        int index = (int) Math.ceil(percentile * values.size()) - 1;

        index = Math.max(0, Math.min(index, values.size() - 1));

        return values.get(index);
    }
}