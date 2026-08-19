package com.graphbenchmark.exception;

@SuppressWarnings("serial")
public class BenchmarkException extends RuntimeException {

    public BenchmarkException(String message) {
        super(message);
    }

    public BenchmarkException(String message, Throwable cause) {
        super(message, cause);
    }

}