package com.graphbenchmark.sampler;

import java.util.Random;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

@Component
public class NodeSampler {

    private final Random random = new Random();

    public long getRandomNode(Driver driver) {

        try (Session session = driver.session()) {

            Record record = session.run("""
                    MATCH (n:Node)
                    RETURN n.id AS id
                    ORDER BY rand()
                    LIMIT 1
                    """).single();

            return record.get("id").asLong();
        }
    }

    public long randomFallback() {
        return random.nextInt(1000) + 1;
    }
}