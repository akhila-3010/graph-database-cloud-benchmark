package com.graphbenchmark.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.graphbenchmark.connector.DatabaseManager;
import com.graphbenchmark.connector.GraphDatabaseConnector;
import com.graphbenchmark.dataset.CsvDatasetLoader;
import com.graphbenchmark.dataset.GraphEdge;
import com.graphbenchmark.loader.BatchLoader;

@Service
public class DatasetImportService {

    private final CsvDatasetLoader loader;
    private final BatchLoader batchLoader;
    private final DatabaseManager databaseManager;

    @Value("${benchmark.batch-size:5000}")
    private int batchSize;

    public DatasetImportService(
            CsvDatasetLoader loader,
            BatchLoader batchLoader,
            DatabaseManager databaseManager) {

        this.loader = loader;
        this.batchLoader = batchLoader;
        this.databaseManager = databaseManager;

    }

    private GraphDatabaseConnector connector() {

        return databaseManager.getConnector();

    }

    public String importDataset(String path)
            throws Exception {

        return processDataset(path);

    }

    public String importDataset(
            MultipartFile file)
            throws Exception {

        File tempFile =
                File.createTempFile(
                        "uploaded-",
                        ".csv");

        file.transferTo(tempFile);

        try {

            return processDataset(
                    tempFile.getAbsolutePath());

        }
        finally {

            tempFile.delete();

        }

    }

    private String processDataset(
            String path)
            throws Exception {

        if (!connector().connect()) {

            throw new RuntimeException(
                    connector().getDatabaseName()
                    + " connection failed");

        }

        try {

            System.out.println(
                    "Loading Dataset : "
                    + path);

            List<GraphEdge> edges =
                    loader.load(path);

            int MAX_ROWS = 1_000_000;

            if (edges.size() > MAX_ROWS) {

                edges = edges.subList(
                        0,
                        MAX_ROWS);

            }

            System.out.println(
                    "Total Relationships : "
                    + edges.size());

            long start =
                    System.currentTimeMillis();

            for (
                    int i = 0;
                    i < edges.size();
                    i += batchSize) {

                int end =
                        Math.min(
                                i + batchSize,
                                edges.size());

                batchLoader.loadBatch(
                        connector().getDriver(),
                        edges.subList(i, end));

                System.out.printf(
                        "Imported %,d / %,d%n",
                        end,
                        edges.size());

            }

            long time =
                    System.currentTimeMillis()
                    - start;

            return """
                    ==================================
                    Dataset Import Completed
                    ==================================
                    Database       : %s
                    File           : %s
                    Relationships  : %d
                    Time (ms)      : %d
                    Time (sec)     : %.2f
                    ==================================
                    """
                    .formatted(
                            connector().getDatabaseName(),
                            new File(path).getName(),
                            edges.size(),
                            time,
                            time / 1000.0);

        }
        finally {

            connector().disconnect();

        }

    }

}