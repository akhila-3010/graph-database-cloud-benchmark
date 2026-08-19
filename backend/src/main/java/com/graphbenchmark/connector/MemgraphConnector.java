package com.graphbenchmark.connector;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graphbenchmark.database.Neo4jDriverManager;

@Component
public class MemgraphConnector implements GraphDatabaseConnector {

    @Value("${memgraph.uri:}")
    private String uri;

    @Value("${memgraph.username:}")
    private String username;

    @Value("${memgraph.password:}")
    private String password;

    private final Neo4jDriverManager driverManager =
            new Neo4jDriverManager();

    @Override
    public String getDatabaseName() {
        return "Memgraph";
    }

    @Override
    public boolean connect() {

        try {

            driverManager.connect(
                    uri,
                    username,
                    password);

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    @Override
    public Driver getDriver() {
        return driverManager.getDriver();
    }

    @Override
    public void disconnect() {
        driverManager.close();
    }

    @Override
    public boolean isConnected() {
        return getDriver() != null;
    }
}