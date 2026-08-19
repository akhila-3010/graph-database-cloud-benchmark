package com.graphbenchmark.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.graphbenchmark.service.DatasetImportService;

@RestController
@RequestMapping("/datasets")
@CrossOrigin
public class DatasetController {

    private final DatasetImportService service;

    public DatasetController(
            DatasetImportService service) {

        this.service = service;
    }


    @PostMapping(
            value="/import",
            consumes="multipart/form-data")
    public String importDataset(
            @RequestParam("file") MultipartFile file) {


        try {


            System.out.println(
                    "FILE NAME : "
                    + file.getOriginalFilename()
            );


            return service.importDataset(file);


        }
        catch(Exception e) {


            e.printStackTrace();


            return "ERROR : "
                    + e.getMessage();

        }

    }

}