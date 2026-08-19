package com.graphbenchmark.connector;

import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {

    private final GraphDatabaseFactory factory;

    private GraphDatabaseConnector connector;

    public DatabaseManager(
            GraphDatabaseFactory factory) {

        this.factory = factory;

        // Default Database
        this.connector =
                factory.getDatabase(
                        GraphDatabaseType.COGNODB);

    }

    public void selectDatabase(
            GraphDatabaseType type) {

        connector =
                factory.getDatabase(type);

    }

    public GraphDatabaseConnector getConnector() {

        return connector;

    }

    public boolean connect() {

        return connector.connect();

    }

    public void disconnect() {

        connector.disconnect();

    }

}