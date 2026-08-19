package com.graphbenchmark.report;

import java.io.IOException;

public interface ReportWriter {

    void write(String fileName, String content) throws IOException;

}