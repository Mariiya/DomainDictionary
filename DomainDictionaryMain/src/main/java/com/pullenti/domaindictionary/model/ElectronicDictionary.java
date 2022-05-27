package com.pullenti.domaindictionary.model;

import com.pullenti.domaindictionary.model.enumeration.ResourceSubtype;
import com.pullenti.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;

public class ElectronicDictionary implements SearchResource {
    private BigInteger id;
    private String name;
    private String author;
    private String pathToFile;
    private ResourceType type;
    private ResourceSubtype subtype;
    private Rule rule;

    public ElectronicDictionary(BigInteger id, String name, String author, String pathToFile, String type,String subtype, Rule rule) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pathToFile = pathToFile;
        this.type = ResourceType.valueOf(type);
        this.subtype = ResourceSubtype.valueOf(subtype);
        this.rule = rule;
    }

    public ElectronicDictionary() {
       }

    public ElectronicDictionary(BigInteger id, String name, String author, String pathToFile, ResourceType type, ResourceSubtype subtype, Rule rule) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pathToFile = pathToFile;
        this.type = type;
        this.subtype = subtype;
        this.rule = rule;
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

    public ResourceSubtype getSubtype() {
        return subtype;
    }

    public void setSubtype(ResourceSubtype subtype) {
        this.subtype = subtype;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public BigInteger getId() {
        return id;
    }
}
