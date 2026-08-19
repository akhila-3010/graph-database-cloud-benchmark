package com.graphbenchmark.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJson(Object object) {

        try {

            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}