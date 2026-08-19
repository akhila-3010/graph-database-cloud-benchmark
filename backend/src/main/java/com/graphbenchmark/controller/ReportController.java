package com.graphbenchmark.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphbenchmark.report.BenchmarkReport;
import com.graphbenchmark.service.ReportService;

@RestController
public class ReportController {

    private final ReportService service;


    public ReportController(
            ReportService service) {

        this.service = service;
    }



    @GetMapping("/report")
    public BenchmarkReport report() {

        return service.generateReport();

    }



    @GetMapping("/report/json")
    public String jsonReport() {

        return service.generateJsonReport();

    }



    @GetMapping(
            value = "/report/csv",
            produces = "text/csv")
    public String csvReport() {

        return service.generateCsvReport();

    }



    @GetMapping("/report/export/json")
    public String exportJson()
            throws Exception {

        return service.exportJsonReport();

    }



    @GetMapping("/report/export/csv")
    public String exportCsv()
            throws Exception {

        return service.exportCsvReport();

    }




    @GetMapping("/report/download/json")
    public ResponseEntity<ByteArrayResource> downloadJson()
            throws Exception {


        String json =
                service.generateJsonReport();



        ByteArrayResource resource =
                new ByteArrayResource(
                        json.getBytes()
                );



        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=benchmark-report.json"
                )

                .contentType(
                        MediaType.APPLICATION_JSON
                )

                .body(resource);

    }




    @GetMapping("/report/download/csv")
    public ResponseEntity<ByteArrayResource> downloadCsv()
            throws Exception {


        String csv =
                service.generateCsvReport();



        ByteArrayResource resource =
                new ByteArrayResource(
                        csv.getBytes()
                );



        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=benchmark-report.csv"
                )

                .contentType(
                        MediaType.TEXT_PLAIN
                )

                .body(resource);

    }


}