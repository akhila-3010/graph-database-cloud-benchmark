package com.graphbenchmark.loader;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

import com.graphbenchmark.dataset.GraphEdge;

@Component
public class BatchLoader {

    private static final String CYPHER = """
            UNWIND $rows AS row
            MERGE (a:Node {id: row.source})
            MERGE (b:Node {id: row.destination})
            MERGE (a)-[:CONNECTED_TO]->(b)
            """;

    public void loadBatch(
            Driver driver,
            List<GraphEdge> edges) {

        List<Map<String, Long>> rows =
                edges.stream()
                        .map(edge -> Map.of(
                                "source", edge.getSource(),
                                "destination", edge.getDestination()))
                        .toList();

        try (Session session = driver.session()) {

            session.executeWrite(tx -> {
                tx.run(CYPHER, Map.of("rows", rows)).consume();
                return null;
            });
        }
    }
}