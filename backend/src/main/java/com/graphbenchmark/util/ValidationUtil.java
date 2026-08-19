package com.graphbenchmark.util;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkDepth(int depth) {

        if (depth < 1 || depth > 3) {

            throw new IllegalArgumentException(
                    "Traversal depth must be between 1 and 3.");

        }

    }

    public static void requireNotBlank(
            String value,
            String field) {

        if (value == null ||
                value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    field + " cannot be empty.");

        }

    }

}