package com.domaindictionary.service.parser;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.dao.EntriesLoader;
import com.domaindictionary.model.DictionaryEntry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DictionaryParser {
    private static final Logger LOG = Logger.getLogger(DictionaryParser.class);
    private final EntriesLoader entriesLoader;

    public DictionaryParser(EntriesLoader entriesLoader) {
        this.entriesLoader = entriesLoader;
    }

    public void parse(ElectronicDictionary dictionary) throws IOException {
        initializeEntries(dictionary);
    }

    private void initializeEntries(ElectronicDictionary dictionary) throws IOException {
        BufferedReader br = readFile(dictionary.getPathToFile());
        List<DictionaryEntry> entries = new ArrayList<>();
        StringBuffer builder = new StringBuffer();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!line.trim().contains(dictionary.getRule().getArticleSeparator())
                        || !line.trim().equals(dictionary.getRule().getArticleSeparator())) {
                    builder.append(line);
                } else {
                    DictionaryEntry entry = createEntry(dictionary, String.valueOf(builder));
                    if (entry.getTerm().length() == 0 || entry.getDefinition().toString().length() == 0)
                        continue;
                    if (isTermValid(entry.getTerm()) && entry.getDefinition().toString().length() < 1999) {
                        entries.add(entry);
                        if (entries.size() > 1000) {
                            entriesLoader.insertDictionaryEntry(entries);
                            entries.clear();
                        }
                    }
                    builder.delete(0, builder.length());
                }
            }
            entriesLoader.insertDictionaryEntry(entries);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entriesLoader.insertDictionaryEntry(entries);
    }

    private BufferedReader readFile(String filePath) throws FileNotFoundException {
        FileInputStream fstream = null;
        if (Files.exists(Paths.get((filePath)))) {
            fstream = new FileInputStream(filePath);

        } else {
            LOG.error("Dictionary file not found. File path: " + filePath);
            throw new FileNotFoundException("Error during opening file");
        }
        return new BufferedReader(new InputStreamReader(fstream));
    }

    private DictionaryEntry createEntry(ElectronicDictionary electronicDictionary, String str) {
        String term = "", definition = "";

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == electronicDictionary.getRule().getTermSeparator().charAt(0)) {
                term = str.substring(0, i);
                definition = str.substring(i);
                break;
            }
        }
        DictionaryEntry e = new DictionaryEntry();
        e.setTerm(term);
        e.setDefinition(Collections.singletonList(definition));
        e.setResourceId(electronicDictionary.getId());
        return e;
    }

    private boolean isTermValid(String term) {
        return term.length() < 100;
    }

}
