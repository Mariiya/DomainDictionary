package com.domaindictionary.controllers;

import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.service.ResourceService;
import com.domaindictionary.service.SearchOrchestrator;
import org.junit.Test;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchTest {
    @Autowired
    Environment env;
    @Autowired
    SearchOrchestrator searchOrchestrator;

    @Test
    @CsvFileSource(resources = "/testTerms.csv", delimiter = ',')
    public void testSearchWithAnalysis(String... strings) {
        List <String> terms = Arrays.asList(strings);
        System.out.println("Terms: " + terms);
        long start = System.currentTimeMillis();

        try {
            Collection<DictionaryEntry> entries =   searchOrchestrator.search(terms, true);

            long end = System.currentTimeMillis();
            System.out.println("Results: ");
            int notFound = 0;
            int numberOfDefinitions = 0;
            for (DictionaryEntry de : entries) {
                if (de.getDefinition().isEmpty() || de.getDefinition().iterator().next().isEmpty()) {
                    notFound++;
                }
                System.out.println(de.getTerm() + ": ");
                int count = 1;
                for (String d : de.getDefinition()) {
                    numberOfDefinitions++;
                    System.out.println(count + ") " + d);
                    count++;
                }
            }

            System.out.println("Took time: " + ((float) (end - start) / 10000));
            System.out.println("Number of not found terms: " + notFound);
            System.out.println("Number of definitions per term: " + ((float)numberOfDefinitions/10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @CsvFileSource(resources = "/testTerms.csv", delimiter = ',')
    public void testSearchWithoutAnalysis(String... strings) {
        List <String> terms = Arrays.asList(strings);
        System.out.println("Terms: " + terms);
        long start = System.currentTimeMillis();

        try {
            Collection<DictionaryEntry> entries =   searchOrchestrator.search(terms, false);

            long end = System.currentTimeMillis();
            System.out.println("Results: ");
            int notFound = 0;
            int numberOfDefinitions = 0;
            for (DictionaryEntry de : entries) {
                if (de.getDefinition().isEmpty() || de.getDefinition().iterator().next().isEmpty()) {
                    notFound++;
                }
                System.out.println(de.getTerm() + ": ");
                int count = 1;
                for (String d : de.getDefinition()) {
                    numberOfDefinitions++;
                    System.out.println(count + ") " + d);
                    count++;
                }
            }

            System.out.println("Took time: " + ((float) (end - start) / 10000));
            System.out.println("Number of not found terms: " + notFound);
            System.out.println("Number of definitions per term: " + ((float)numberOfDefinitions/10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
