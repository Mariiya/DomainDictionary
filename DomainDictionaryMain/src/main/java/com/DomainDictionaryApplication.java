package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
@SpringBootApplication
public class DomainDictionaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainDictionaryApplication.class, args);
    }

}
