package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;
import java.util.List;


public class ElectronicDictionary implements SearchResource {
    private BigInteger id;
    private String name;
    private String author;
    private String pathToFile;
    private ResourceType type;
    private ResourceSybtype sybtype;
    private Rule rule;
    private List<DictionaryEntry> entries;

    public ElectronicDictionary(BigInteger id, String name, String author, String pathToFile, ResourceType type, ResourceSybtype sybtype, Rule rule, List<DictionaryEntry> entries) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pathToFile = pathToFile;
        this.type = type;
        this.sybtype = sybtype;
        this.rule = rule;
        this.entries = entries;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public ResourceSybtype getSybtype() {
        return sybtype;
    }

    public void setSybtype(ResourceSybtype sybtype) {
        this.sybtype = sybtype;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public List<DictionaryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DictionaryEntry> entries) {
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public ResourceSybtype getSubtype() {
        return sybtype;
    }

    public BigInteger getId() {
        return id;
    }
}
