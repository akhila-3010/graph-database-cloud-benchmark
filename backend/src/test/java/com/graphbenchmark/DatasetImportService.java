package com.graphbenchmark;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graphbenchmark.service.DatasetImportService;

@SpringBootTest
class DatasetImportServiceTest {

    @Autowired
    private DatasetImportService service;

    @Test
    void datasetImportServiceShouldLoad() {

        assertNotNull(service);

    }

}