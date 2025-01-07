package com.marciliojr.compraz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.marciliojr.compraz.controller",
        "com.marciliojr.compraz.repository",
        "com.marciliojr.compraz.service",
        "com.marciliojr.compraz.model",
        "com.marciliojr.compraz.infra"})
public class ComprazApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComprazApplication.class, args);


    }

}
