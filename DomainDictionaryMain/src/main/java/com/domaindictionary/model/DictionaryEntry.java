package com.domaindictionary.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

public class DictionaryEntry {
    private static final String INDEX = "entity";
    private String id;
    private String term;
    private Collection<String> definition;
    private String isStylisticZone;
    private BigInteger resourceId;

    public DictionaryEntry(String id, String term, Collection<String> definition, BigInteger resourceId) {
        this.id = id;
        this.term = term;
        this.definition = definition;
        this.resourceId = resourceId;
    }

    public DictionaryEntry(String term) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.term = term;
        this.definition = Collections.EMPTY_LIST;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Collection<String> getDefinition() {
        return definition;
    }

    public void setDefinition(Collection<String> definition) {
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
