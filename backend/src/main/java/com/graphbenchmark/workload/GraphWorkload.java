package com.graphbenchmark.workload;

import org.neo4j.driver.Driver;

public interface GraphWorkload {

    String getName();

    void execute(Driver driver);

}