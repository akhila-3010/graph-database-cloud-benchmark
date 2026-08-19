package com.graphbenchmark.exception;

@SuppressWarnings("serial")
public class DatabaseConnectionException
        extends RuntimeException {

    public DatabaseConnectionException(String message) {
        super(message);
    }

}