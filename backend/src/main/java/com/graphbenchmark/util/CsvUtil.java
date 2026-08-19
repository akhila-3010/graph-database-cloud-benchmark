package com.graphbenchmark.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class CsvUtil {

    private CsvUtil() {
    }

    public static void write(
            String file,
            List<String[]> rows)
            throws IOException {

        try (FileWriter writer =
                     new FileWriter(file)) {

            for (String[] row : rows) {

                writer.write(
                        String.join(",", row));

                writer.write("\n");

            }

        }

    }

}