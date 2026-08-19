package com.graphbenchmark.connector;

import org.neo4j.driver.Driver;

public interface GraphDatabaseConnector {

    String getDatabaseName();

    boolean connect();

    void disconnect();

    boolean isConnected();

    Driver getDriver();

}