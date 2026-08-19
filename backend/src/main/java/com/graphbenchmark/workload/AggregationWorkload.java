package com.graphbenchmark.workload;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Component;

@Component
public class AggregationWorkload implements GraphWorkload {

    @Override
    public String getName() {
        return "Aggregation";
    }

    @Override
    public void execute(Driver driver) {

        try (Session session = driver.session()) {

            session.run("""
                    MATCH (n:Node)
                    RETURN count(n)
                    """).consume();
        }
    }
}