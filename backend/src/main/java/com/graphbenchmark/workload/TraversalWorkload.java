package com.graphbenchmark.workload;

import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

import com.graphbenchmark.sampler.NodeSampler;

@Component
public class TraversalWorkload implements GraphWorkload {

    private final NodeSampler sampler;

    private int depth = 1;

    public TraversalWorkload(NodeSampler sampler) {
        this.sampler = sampler;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String getName() {
        return depth + "-Hop Traversal";
    }

    @Override
    public void execute(Driver driver) {

        long nodeId =
                sampler.getRandomNode(driver);

        String cypher = switch (depth) {

            case 1 -> """
                    MATCH (n:Node {id:$id})-[:CONNECTED_TO]->(m)
                    RETURN count(m)
                    """;

            case 2 -> """
                    MATCH (n:Node {id:$id})-[:CONNECTED_TO]->()-[:CONNECTED_TO]->(m)
                    RETURN count(m)
                    """;

            default -> """
                    MATCH (n:Node {id:$id})-[:CONNECTED_TO]->()-[:CONNECTED_TO]->()-[:CONNECTED_TO]->(m)
                    RETURN count(m)
                    """;
        };

        try (Session session = driver.session()) {

            session.run(
                    cypher,
                    Map.of("id", nodeId)
            ).consume();
        }
    }
}