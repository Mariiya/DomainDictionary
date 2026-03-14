package com.domaindictionary.service.parser;

import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DictionaryParser {
    private static final Logger log = LoggerFactory.getLogger(DictionaryParser.class);

    private final DictionaryEntryDao dictionaryEntryDao;

    public void parseAndLoad(String content, ElectronicDictionary dictionary) throws IOException {
        dictionaryEntryDao.ensureIndexExists();

        String articleSep = dictionary.getRule().getArticleSeparator();
        String termSep = dictionary.getRule().getTermSeparator();

        String[] articles = content.split(articleSep);
        List<DictionaryEntryDoc> batch = new ArrayList<>();

        for (String article : articles) {
            String trimmed = article.trim();
            if (trimmed.isEmpty()) continue;

            int sepIdx = trimmed.indexOf(termSep);
            if (sepIdx <= 0) continue;

            String term = trimmed.substring(0, sepIdx).trim();
            String definition = trimmed.substring(sepIdx + termSep.length()).trim();

            if (term.isEmpty() || definition.isEmpty()) continue;
            if (term.length() > 100 || definition.length() > 2000) continue;

            DictionaryEntryDoc entry = DictionaryEntryDoc.builder()
                    .id(UUID.randomUUID().toString())
                    .term(term)
                    .resourceId(dictionary.getId())
                    .definitions(Collections.singletonList(definition))
                    .build();
            batch.add(entry);

            if (batch.size() >= 500) {
                dictionaryEntryDao.bulkInsert(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            dictionaryEntryDao.bulkInsert(batch);
        }

        log.info("Parsed and loaded dictionary '{}': {} entries", dictionary.getName(), articles.length);
    }
}
