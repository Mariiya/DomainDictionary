package com.onpu.domaindictionary.model.builders;


import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.ElectronicDictionary;
import com.onpu.domaindictionary.model.Rule;
import com.onpu.domaindictionary.model.enumeration.ResourceSybtype;
import com.onpu.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;
import java.util.List;

public class ElectronicDictionaryBuilder {
    private BigInteger id;
    private String name;
    private String author;
    private String pathToFile;
    private ResourceType type;
    private ResourceSybtype sybtype;
    private Rule rule;
    private List<DictionaryEntry> entries;

    public ElectronicDictionaryBuilder withId(BigInteger id) {
        this.id = id;
        return this;
    }

    public ElectronicDictionaryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ElectronicDictionaryBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public ElectronicDictionaryBuilder withPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
        return this;
    }

    public ElectronicDictionaryBuilder withType(ResourceType type) {
        this.type = type;
        return this;
    }

    public ElectronicDictionaryBuilder withSybType(ResourceSybtype sybtype) {
        this.sybtype = sybtype;
        return this;
    }

    public ElectronicDictionaryBuilder withRule(Rule rule) {
        this.rule = rule;
        return this;
    }

    public ElectronicDictionaryBuilder withEntries(List<DictionaryEntry> entries) {
        this.entries = entries;
        return this;
    }

    public ElectronicDictionary build() {
        return new ElectronicDictionary(id, name, author, pathToFile, type, sybtype, rule, entries);
    }
}
