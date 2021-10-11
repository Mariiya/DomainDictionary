package com.onpu.domaindictionary.model;


import com.onpu.domaindictionary.model.enumeration.ResourceSybtype;
import com.onpu.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;
import java.util.List;

public class InternetResource implements SearchResource {

    private BigInteger id;
    private String name;
    private String url;
    private ResourceType type;
    private ResourceSybtype sybtype;
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
        return null;
    }

    public ResourceType getType() {
        return null;
    }

    public ResourceSybtype getSubtype() {
        return null;
    }

    public BigInteger getId() {
        return null;
    }
}
