package com.graphbenchmark.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class JsonReportGenerator implements ReportGenerator {

    private final ObjectMapper mapper;

    public JsonReportGenerator() {

        mapper = new ObjectMapper();

        mapper.registerModule(
                new JavaTimeModule()
        );
    }


    @Override
    public String generate(BenchmarkReport report) {

        try {

            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(report);

        }
        catch(Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to generate JSON report.",
                    e
            );

        }

    }

}