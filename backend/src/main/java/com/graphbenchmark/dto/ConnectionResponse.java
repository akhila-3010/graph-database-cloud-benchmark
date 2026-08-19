package com.graphbenchmark.dto;

public class ConnectionResponse {

    private boolean connected;
    private String database;
    private String message;

    public ConnectionResponse() {
    }

    public ConnectionResponse(
            boolean connected,
            String database,
            String message) {

        this.connected = connected;
        this.database = database;
        this.message = message;

    }

    public boolean isConnected() {
        return connected;
    }

    public String getDatabase() {
        return database;
    }

    public String getMessage() {
        return message;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}