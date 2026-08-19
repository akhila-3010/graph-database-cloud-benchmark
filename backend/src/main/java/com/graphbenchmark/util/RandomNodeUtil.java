package com.graphbenchmark.util;

import java.util.Random;

public final class RandomNodeUtil {

    private static final Random RANDOM =
            new Random();

    private RandomNodeUtil() {
    }

    public static long nextLong(
            long min,
            long max) {

        return min +
                (long) (RANDOM.nextDouble()
                        * (max - min + 1));

    }

}