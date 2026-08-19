package com.graphbenchmark.database;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jDriverManager {

    private Driver driver;

    public void connect(
            String uri,
            String username,
            String password) {
    	
        driver =
                GraphDatabase.driver(
                        uri,
                        AuthTokens.basic(
                                username,
                                password
                        )
                );

        driver.verifyConnectivity();
    }


    public Driver getDriver() {
        return driver;
    }

    public void close() {
        if(driver != null) {
            driver.close();
        }
    }
}