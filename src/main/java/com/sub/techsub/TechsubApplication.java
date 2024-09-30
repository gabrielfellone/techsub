package com.sub.techsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechsubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechsubApplication.class, args);
    }

}
