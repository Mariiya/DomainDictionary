package com.onpu.domaindictionary.service;

import com.onpu.domaindictionary.model.DictionaryEntry;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class ElectronicDictionarySearchService {
    public DictionaryEntry search(String term) {
        return new DictionaryEntry(new BigInteger("2"),".",new ArrayList<>());
    }
}
