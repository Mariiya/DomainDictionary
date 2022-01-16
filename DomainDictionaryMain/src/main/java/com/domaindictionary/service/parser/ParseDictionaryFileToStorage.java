package com.domaindictionary.service.parser;

import com.domaindictionary.elasticsearch.api.EntriesLoader;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ParseDictionaryFileToStorage {
    private static final Logger LOG = Logger.getLogger(ParseDictionaryFileToStorage.class);
    private final EntriesLoader entriesLoader;

    public ParseDictionaryFileToStorage(EntriesLoader entriesLoader) {
        this.entriesLoader = entriesLoader;
    }

    public void parse(ElectronicDictionary dictionary) throws IOException {
        if (dictionary.getRule() != null && dictionary.getRule().getStylisticZone()) {
            initializeEntriesWithStylisticZone(dictionary);
        }
        initializeEntriesNoStylisticZone(dictionary);
    }

    private void initializeEntriesNoStylisticZone(ElectronicDictionary dictionary) throws IOException {
        List<DictionaryEntry> entries = initializeEntries(dictionary);
        entriesLoader.insertDictionaryEntry(entries);
    }

    private void initializeEntriesWithStylisticZone(ElectronicDictionary dictionary) throws IOException {
        List<DictionaryEntry> entries = initializeEntries(dictionary);
        entriesLoader.insertDictionaryEntry(entries);
    }

    private List<DictionaryEntry> initializeEntries(ElectronicDictionary dictionary) throws FileNotFoundException {
        BufferedReader br = readFile(dictionary.getPathToFile());
        List<DictionaryEntry> entries = new ArrayList<>();
        StringBuffer builder = new StringBuffer();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!line.contains(dictionary.getRule().getArticleSeparator())) {
                    builder.append(line);
                } else {
                    DictionaryEntry entry = createEntry(dictionary, String.valueOf(builder));
                    if (entry.getTerm().length() == 0 || entry.getDefinition().toString().length() == 0)
                        continue;
                    if (isTermValid(entry.getTerm()) && entry.getDefinition().toString().length() < 1999) {
                        entries.add(entry);
                    }
                    builder.delete(0, builder.length());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private BufferedReader readFile(String filePath) throws FileNotFoundException {
        FileInputStream fstream = null;
        if (Files.isReadable(Path.of(filePath))) {
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
        return new DictionaryEntry("", term, Collections.singletonList(definition), electronicDictionary.getId());
    }

    private boolean isTermValid(String term){
        return term.length() < 100;
    }
    public List<String> splitDefinitions(String definition, String definitionSeparator) {
        String[] arr = definition.split(definitionSeparator);
        return Arrays.asList(arr);
    }
}
