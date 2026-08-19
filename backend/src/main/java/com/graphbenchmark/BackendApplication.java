package com.graphbenchmark;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();


        System.setProperty(
            "COGNODB_URI",
            dotenv.get("COGNODB_URI")
        );

        System.setProperty(
            "COGNODB_USERNAME",
            dotenv.get("COGNODB_USERNAME")
        );

        System.setProperty(
            "COGNODB_PASSWORD",
            dotenv.get("COGNODB_PASSWORD")
        );


        SpringApplication.run(BackendApplication.class, args);
    }
}