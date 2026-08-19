package com.graphbenchmark.service;

import org.springframework.stereotype.Service;

import com.graphbenchmark.benchmark.BenchmarkSuite;
import com.graphbenchmark.connector.DatabaseManager;
import com.graphbenchmark.connector.GraphDatabaseConnector;
import com.graphbenchmark.metrics.BenchmarkMetric;
import com.graphbenchmark.workload.AggregationWorkload;
import com.graphbenchmark.workload.BenchmarkRunner;
import com.graphbenchmark.workload.LookupWorkload;
import com.graphbenchmark.workload.MixedWorkload;
import com.graphbenchmark.workload.TraversalWorkload;

@Service
public class BenchmarkService {

    private final DatabaseManager databaseManager;
    private final BenchmarkRunner runner;
    private final TraversalWorkload traversalWorkload;
    private final LookupWorkload lookupWorkload;
    private final AggregationWorkload aggregationWorkload;
    private final MixedWorkload mixedWorkload;
    
    public BenchmarkService(
            DatabaseManager databaseManager,
            BenchmarkRunner runner,
            TraversalWorkload traversalWorkload,
            LookupWorkload lookupWorkload,
            AggregationWorkload aggregationWorkload,
            MixedWorkload mixedWorkload) {

        this.databaseManager = databaseManager;
        this.runner = runner;
        this.traversalWorkload = traversalWorkload;
        this.lookupWorkload = lookupWorkload;
        this.aggregationWorkload = aggregationWorkload;
        this.mixedWorkload = mixedWorkload;

    }

    private GraphDatabaseConnector connector() {

        return databaseManager.getConnector();

    }

    public String getDatabaseName() {

        return connector().getDatabaseName();

    }

    public String connect() {

        try {

            return connector().connect()
                    ? "Connected Successfully"
                    : "Connection Failed";

        }
        catch(Exception e) {

            return "Connection Failed : "
                    + e.getMessage();

        }
        finally {

            connector().disconnect();

        }

    }

    public BenchmarkMetric runTraversalBenchmark(
            int depth) {

        connector().connect();

        try {

            traversalWorkload.setDepth(depth);

            return runner.run(
                    traversalWorkload,
                    connector().getDriver());

        }
        finally {

            connector().disconnect();

        }

    }

    public BenchmarkMetric runLookupBenchmark() {

        connector().connect();

        try {

            return runner.run(
                    lookupWorkload,
                    connector().getDriver());

        }
        finally {

            connector().disconnect();

        }

    }

    public BenchmarkMetric runAggregationBenchmark() {

        connector().connect();

        try {

            return runner.run(
                    aggregationWorkload,
                    connector().getDriver());

        }
        finally {

            connector().disconnect();

        }

    }

    public BenchmarkMetric runMixedBenchmark() {

        connector().connect();

        try {

            return runner.run(
                    mixedWorkload,
                    connector().getDriver());

        }
        finally {

            connector().disconnect();

        }

    }

    public BenchmarkSuite runBenchmarkSuite() {

        connector().connect();

        BenchmarkSuite suite =
                new BenchmarkSuite();

        try {

            traversalWorkload.setDepth(1);

            suite.addResult(
                    runner.run(
                            traversalWorkload,
                            connector().getDriver()));

            traversalWorkload.setDepth(2);

            suite.addResult(
                    runner.run(
                            traversalWorkload,
                            connector().getDriver()));

            traversalWorkload.setDepth(3);

            suite.addResult(
                    runner.run(
                            traversalWorkload,
                            connector().getDriver()));

            suite.addResult(
                    runner.run(
                            lookupWorkload,
                            connector().getDriver()));

            suite.addResult(
                    runner.run(
                            aggregationWorkload,
                            connector().getDriver()));

            suite.addResult(
                    runner.run(
                            mixedWorkload,
                            connector().getDriver()));

            return suite;

        }
        finally {

            connector().disconnect();

        }

    }

}