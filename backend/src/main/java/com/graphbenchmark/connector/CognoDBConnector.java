package com.graphbenchmark.connector;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graphbenchmark.database.Neo4jDriverManager;

@Component
public class CognoDBConnector implements GraphDatabaseConnector {

    @Value("${cognodb.uri:}")
    private String uri;

    @Value("${cognodb.username:}")
    private String username;

    @Value("${cognodb.password:}")
    private String password;

    private final Neo4jDriverManager driverManager =
            new Neo4jDriverManager();

    @Override
    public String getDatabaseName() {
        return "CognoDB";
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

            System.err.println(
                    "CognoDB connection failed: "
                    + e.getMessage());

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