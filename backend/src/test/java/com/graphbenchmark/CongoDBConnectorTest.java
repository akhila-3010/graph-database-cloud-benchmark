package com.graphbenchmark;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graphbenchmark.connector.CognoDBConnector;

@SpringBootTest
class CognoDBConnectorTest {

    @Autowired
    private CognoDBConnector connector;

    @Test
    void connectorShouldLoad() {

        assertNotNull(connector);

    }

}