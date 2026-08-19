package com.graphbenchmark.service;

import org.springframework.stereotype.Service;

import com.graphbenchmark.benchmark.BenchmarkSuite;
import com.graphbenchmark.metrics.BenchmarkMetric;
import com.graphbenchmark.report.BenchmarkReport;
import com.graphbenchmark.report.CsvExporter;
import com.graphbenchmark.report.CsvReportGenerator;
import com.graphbenchmark.report.JsonExporter;
import com.graphbenchmark.report.JsonReportGenerator;

@Service
public class ReportService {

    private final BenchmarkService benchmarkService;
    private final JsonReportGenerator jsonGenerator;
    private final CsvReportGenerator csvGenerator;
    private final JsonExporter jsonExporter;
    private final CsvExporter csvExporter;

    public ReportService(
            BenchmarkService benchmarkService,
            JsonReportGenerator jsonGenerator,
            CsvReportGenerator csvGenerator,
            JsonExporter jsonExporter,
            CsvExporter csvExporter) {

        this.benchmarkService = benchmarkService;
        this.jsonGenerator = jsonGenerator;
        this.csvGenerator = csvGenerator;
        this.jsonExporter = jsonExporter;
        this.csvExporter = csvExporter;

    }
    public BenchmarkReport generateReport() {

        BenchmarkSuite suite =
                benchmarkService.runBenchmarkSuite();

        BenchmarkReport report =
                new BenchmarkReport("CognoDB");

        for (BenchmarkMetric metric : suite.getResults()) {
            report.addMetric(metric);
        }

        return report;
    }
    
    public String generateJsonReport() {

        return jsonGenerator.generate(
                generateReport());

    }

    public String generateCsvReport() {

        return csvGenerator.generate(
                generateReport());

    }
    
    public String exportJsonReport() throws Exception {

        String report =
                generateJsonReport();

        String file =
                "benchmark-report.json";

        jsonExporter.write(file, report);

        return "Saved : reports/" + file;

    }

    public String exportCsvReport() throws Exception {

        String report =
                generateCsvReport();

        String file =
                "benchmark-report.csv";

        csvExporter.write(file, report);

        return "Saved : reports/" + file;

    }

}