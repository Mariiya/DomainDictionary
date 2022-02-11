package com.domaindictionary.model;


import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;
import java.util.List;

public class InternetResource implements SearchResource {

    private BigInteger id;
    private String name;
    private String url;
    private ResourceType type;
    private ResourceSubtype subtype;
    private Rule rule;
    private List<DictionaryEntry> entries;

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(ResourceType type) {
        this.type = type;
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

    public List<DictionaryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DictionaryEntry> entries) {
        this.entries = entries;
    }

    public String getName() {
        return null;
    }

    public ResourceType getType() {
        return null;
    }

    public ResourceSubtype getSubtype() {
        return null;
    }

    public BigInteger getId() {
        return null;
    }
}
