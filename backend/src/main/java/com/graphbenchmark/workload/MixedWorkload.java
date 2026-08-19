package com.graphbenchmark.workload;

import java.util.Random;

import org.neo4j.driver.Driver;
import org.springframework.stereotype.Component;

@Component
public class MixedWorkload implements GraphWorkload {

    private final TraversalWorkload traversal;
    private final LookupWorkload lookup;
    private final AggregationWorkload aggregation;

    private final Random random =
            new Random();

    public MixedWorkload(
            TraversalWorkload traversal,
            LookupWorkload lookup,
            AggregationWorkload aggregation) {

        this.traversal = traversal;
        this.lookup = lookup;
        this.aggregation = aggregation;
    }

    @Override
    public String getName() {
        return "Mixed Workload";
    }

    @Override
    public void execute(Driver driver) {

        int value =
                random.nextInt(3);

        switch (value) {

            case 0 -> lookup.execute(driver);

            case 1 -> {

                traversal.setDepth(1);

                traversal.execute(driver);
            }

            default -> aggregation.execute(driver);
        }
    }
}