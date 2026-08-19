package com.graphbenchmark;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graphbenchmark.service.BenchmarkService;

@SpringBootTest
class BenchmarkServiceTest {

    @Autowired
    private BenchmarkService benchmarkService;

    @Test
    void benchmarkServiceShouldLoad() {

        assertNotNull(benchmarkService);

    }

}