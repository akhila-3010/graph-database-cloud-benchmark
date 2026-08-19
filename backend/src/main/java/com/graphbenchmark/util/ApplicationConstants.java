package com.graphbenchmark.util;

public final class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String NODE_LABEL = "Node";

    public static final String RELATIONSHIP =
            "CONNECTED_TO";

    public static final String REPORT_DIRECTORY =
            "reports";

    public static final String DATA_DIRECTORY =
            "data";

    public static final String DEFAULT_DATASET =
            "data/pac_soc.csv";

    public static final int DEFAULT_BATCH_SIZE = 1000;

    public static final int DEFAULT_ITERATIONS = 100;

    public static final int DEFAULT_WARMUP = 10;

}