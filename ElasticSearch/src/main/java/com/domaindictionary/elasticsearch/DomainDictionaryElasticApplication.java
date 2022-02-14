package com.domaindictionary.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class DomainDictionaryElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(DomainDictionaryElasticApplication.class, args);
    }
}
