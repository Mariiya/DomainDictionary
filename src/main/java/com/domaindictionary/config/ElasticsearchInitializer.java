package com.domaindictionary.config;

import com.domaindictionary.dao.DictionaryEntryDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElasticsearchInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchInitializer.class);

    private final DictionaryEntryDao dictionaryEntryDao;

    @Override
    public void run(String... args) {
        try {
            dictionaryEntryDao.ensureIndexExists();
            log.info("Elasticsearch indices initialized");
        } catch (Exception e) {
            log.warn("Could not initialize Elasticsearch indices: {}", e.getMessage());
        }
    }
}
