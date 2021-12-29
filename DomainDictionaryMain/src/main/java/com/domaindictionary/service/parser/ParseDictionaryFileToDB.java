package com.domaindictionary.service.parser;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ParseDictionaryFileToDB {
    public void parse(ElectronicDictionary dictionary, DictionaryDao dictionaryDao) {
        initializeEntries(dictionary);
        dictionaryDao.createElectronicDictionary(dictionary);
        dictionaryDao.createEntries(dictionary);
    }

    private void initializeEntries(ElectronicDictionary dictionary) {
        StringBuffer builder = new StringBuffer();
        List<DictionaryEntry> dictionaryEntries = new ArrayList<DictionaryEntry>();

        String line;
        FileInputStream fstream = null;
        BufferedReader br = null;
        try {
            fstream = new FileInputStream(dictionary.getPathToFile());

            br = new BufferedReader(new InputStreamReader(fstream));

            while ((line = br.readLine()) != null) {
                if (!line.contains(dictionary.getRule().getArticleSeparator())) {
                    builder.append(line);
                } else {
                    DictionaryEntry entry = createEntry(dictionary, String.valueOf(builder));
                    if (entry.getTerm().length() == 0 || entry.getDefinition().toString().length() == 0)
                        continue;
                    if (entry.getTerm().length() < 100 && entry.getDefinition().toString().length() < 1999) {
                        dictionaryEntries.add(entry);
                    }
                    builder.delete(0, builder.length());
                }
            }

            dictionary.setEntries(dictionaryEntries);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return new DictionaryEntry(new BigInteger("2"), term, Collections.singletonList(definition));
    }

    public List<String> splitDefinitions(String definition, String definitionSeparator) {
        String[] arr = definition.split(definitionSeparator);
        System.out.println(Arrays.toString(arr));
        return Arrays.asList(arr);
    }
}
