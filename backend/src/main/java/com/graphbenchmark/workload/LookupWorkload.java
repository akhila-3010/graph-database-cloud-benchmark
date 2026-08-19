package com.graphbenchmark.workload;

import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

import com.graphbenchmark.sampler.NodeSampler;

@Component
public class LookupWorkload implements GraphWorkload {

    private final NodeSampler sampler;

    public LookupWorkload(NodeSampler sampler) {
        this.sampler = sampler;
    }

    @Override
    public String getName() {
        return "Point Lookup";
    }

    @Override
    public void execute(Driver driver) {

        long id =
                sampler.getRandomNode(driver);

        try (Session session = driver.session()) {

            session.run("""
                    MATCH (n:Node {id:$id})
                    RETURN n
                    """,
                    Map.of("id", id))
                    .consume();
        }
    }
}