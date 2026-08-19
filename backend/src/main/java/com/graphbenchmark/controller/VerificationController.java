package com.graphbenchmark.controller;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphbenchmark.connector.DatabaseManager;
import com.graphbenchmark.connector.GraphDatabaseConnector;

@RestController
public class VerificationController {

    private final DatabaseManager databaseManager;

    public VerificationController(
            DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;

    }

    private GraphDatabaseConnector connector() {

        return databaseManager.getConnector();

    }

    @GetMapping("/verify")
    public String verify() {

        if (!connector().connect()) {

            return connector().getDatabaseName()
                    + " connection failed";

        }

        Driver driver =
                connector().getDriver();

        try (Session session = driver.session()) {

            long nodes =
                    session.run(
                            "MATCH (n) RETURN count(n) AS count")
                    .single()
                    .get("count")
                    .asLong();

            long relationships =
                    session.run(
                            "MATCH ()-[r]->() RETURN count(r) AS count")
                    .single()
                    .get("count")
                    .asLong();

            return """
                    ==========================
                    Graph Verification
                    ==========================
                    Database       : %s
                    Nodes          : %d
                    Relationships  : %d
                    ==========================
                    """
                    .formatted(
                            connector().getDatabaseName(),
                            nodes,
                            relationships);

        }
        finally {

            connector().disconnect();

        }

    }

}