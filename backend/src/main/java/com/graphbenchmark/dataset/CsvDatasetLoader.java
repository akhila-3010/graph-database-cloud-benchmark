package com.graphbenchmark.dataset;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDatasetLoader {

    public List<GraphEdge> load(String path) throws Exception {

        List<GraphEdge> edges = new ArrayList<>();

        try(BufferedReader reader =
                new BufferedReader(new FileReader(path))) {

            String line;

            reader.readLine();

            while((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                edges.add(
                    new GraphEdge(
                        Long.parseLong(data[0].trim()),
                        Long.parseLong(data[1].trim())
                    )
                );
            }
        }

        return edges;
    }
}