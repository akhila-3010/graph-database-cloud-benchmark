package com.graphbenchmark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI graphBenchmarkApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Graph Database Benchmark API")
                                .description(
                                        "REST API for benchmarking graph databases using traversal, lookup, aggregation and mixed workloads.")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .name("Jayasai Karthik")
                                                .email("example@example.com"))
                                .license(
                                        new License()
                                                .name("MIT License")));

    }

}