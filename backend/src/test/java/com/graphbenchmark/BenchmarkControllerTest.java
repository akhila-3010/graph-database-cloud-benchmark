package com.graphbenchmark;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graphbenchmark.controller.BenchmarkController;

@SpringBootTest
class BenchmarkControllerTest {

    @Autowired
    private BenchmarkController controller;

    @Test
    void controllerShouldLoad() {

        assertNotNull(controller);

    }

}