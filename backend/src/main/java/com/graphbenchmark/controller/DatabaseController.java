package com.graphbenchmark.controller;

import org.springframework.web.bind.annotation.*;

import com.graphbenchmark.connector.DatabaseManager;
import com.graphbenchmark.connector.GraphDatabaseType;

@RestController
@RequestMapping("/database")
@CrossOrigin
public class DatabaseController {

    private final DatabaseManager databaseManager;

    public DatabaseController(
            DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    @PostMapping("/select/{database}")
    public String selectDatabase(
            @PathVariable String database) {

        GraphDatabaseType type =
                GraphDatabaseType.valueOf(
                        database.toUpperCase());

        databaseManager.selectDatabase(type);

        return "Database selected : "
                + type;
    }

}