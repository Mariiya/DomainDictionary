package com.domaindictionary.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DomainDictionary {
    private BigInteger id;
    private Date createdAt;
    private User author;
    private List<DictionaryEntry> entries;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<DictionaryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DictionaryEntry> entries) {
        this.entries = entries;
    }
}
