package com.domaindictionary.elasticsearch.model;

import java.math.BigInteger;
import java.util.List;

public class DictionaryEntry {
    private static final String INDEX = "entity";
    private String id;
    private String term;
    private List<String> definition;
    private String isStylisticZone;
    private BigInteger resourceId;

    public DictionaryEntry(String id, String term, List<String> definition, BigInteger resourceId) {
        this.id = id;
        this.term = term;
        this.definition = definition;
        this.resourceId = resourceId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<String> getDefinition() {
        return definition;
    }

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getIndex() {
        return INDEX;
    }

    public BigInteger getResourceId() {
        return resourceId;
    }

    public void setResourceId(BigInteger resourceId) {
        this.resourceId = resourceId;
    }

    public String getIsStylisticZone() {
        return isStylisticZone;
    }

    public void setIsStylisticZone(String isStylisticZone) {
        this.isStylisticZone = isStylisticZone;
    }
}
