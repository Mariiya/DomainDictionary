package com.domaindictionary.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DictionaryEntry {
    private BigInteger id;
    private String term;
    private List<String> definition;

    public DictionaryEntry(BigInteger id, String term, List<String> definition) {
        this.id = id;
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<String> getDefinition() {
        return            definition;
    }

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
