package com.graphbenchmark.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class CsvExporter implements ReportWriter {

    @Override
    public void write(String fileName, String content)
            throws IOException {

        File folder = new File("reports");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {

            writer.write(content);

        }

    }

}