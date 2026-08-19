package com.graphbenchmark.exception;

@SuppressWarnings("serial")
public class DatasetImportException
        extends RuntimeException {

    public DatasetImportException(String message) {
        super(message);
    }

}