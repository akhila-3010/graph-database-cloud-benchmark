package com.graphbenchmark.connector;

import org.springframework.stereotype.Component;

@Component
public class GraphDatabaseFactory {

    private final CognoDBConnector cognoDBConnector;
    private final Neo4jConnector neo4jConnector;
    private final MemgraphConnector memgraphConnector;
    private final FalkorDBConnector falkorDBConnector;

    public GraphDatabaseFactory(
            CognoDBConnector cognoDBConnector,
            Neo4jConnector neo4jConnector,
            MemgraphConnector memgraphConnector,
            FalkorDBConnector falkorDBConnector) {

        this.cognoDBConnector = cognoDBConnector;
        this.neo4jConnector = neo4jConnector;
        this.memgraphConnector = memgraphConnector;
        this.falkorDBConnector = falkorDBConnector;
    }

    public GraphDatabaseConnector getDatabase(
            GraphDatabaseType type) {

        return switch (type) {

	        case COGNODB -> cognoDBConnector;
	
	        case NEO4J -> neo4jConnector;
	
	        case MEMGRAPH -> memgraphConnector;
	
	        case FALKORDB -> falkorDBConnector;

            default ->
                    throw new IllegalArgumentException(
                            "Database not implemented : " + type);

        };

    }

}