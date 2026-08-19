package com.graphbenchmark;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BenchmarkControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void connectApiExists() throws Exception {

        mockMvc.perform(
                get("/benchmark/connect"))
                .andExpect(status().isOk());

    }

    @Test
    void traversalApiExists() throws Exception {

        mockMvc.perform(
                get("/benchmark/traversal/1"))
                .andExpect(status().isOk());

    }

}