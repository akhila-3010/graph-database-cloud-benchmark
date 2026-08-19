package com.graphbenchmark.util;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    private FileUtil() {
    }

    public static void createDirectory(String path) {

        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs();
        }

    }

    public static boolean exists(String file) {
        return new File(file).exists();
    }

    public static long size(String file) {

        File f = new File(file);

        return f.exists() ? f.length() : 0;

    }

    public static String absolutePath(String file)
            throws IOException {

        return new File(file)
                .getCanonicalPath();

    }

}