package com.graphbenchmark.dataset;

public class GraphEdge {

    private final long source;
    private final long destination;

    public GraphEdge(long source, long destination) {
        this.source = source;
        this.destination = destination;
    }

    public long getSource() {
        return source;
    }

    public long getDestination() {
        return destination;
    }
}