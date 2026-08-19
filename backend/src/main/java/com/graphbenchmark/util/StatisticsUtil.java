package com.graphbenchmark.util;

import java.util.Collections;
import java.util.List;

public final class StatisticsUtil {

    private StatisticsUtil() {
    }

    public static double median(
            List<Double> values) {

        if (values.isEmpty()) {

            return 0;

        }

        Collections.sort(values);

        int middle =
                values.size() / 2;

        if (values.size() % 2 == 0) {

            return (
                    values.get(middle - 1)
                            + values.get(middle))
                    / 2.0;

        }

        return values.get(middle);

    }

    public static double percentile(
            List<Double> values,
            double percentile) {

        if (values.isEmpty()) {

            return 0;

        }

        Collections.sort(values);

        int index =
                (int) Math.ceil(
                        percentile / 100.0
                                * values.size());

        return values.get(
                Math.min(index - 1,
                        values.size() - 1));

    }

}