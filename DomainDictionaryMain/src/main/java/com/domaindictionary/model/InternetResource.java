package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import java.math.BigInteger;


public class InternetResource implements SearchResource {

    private BigInteger id;
    private String name;
    private String url;
    private ResourceType type;
    private ResourceSubtype subtype;
    private Rule rule;

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

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceType getType() {
        return type;
    }

    @Override
    public ResourceSubtype getSubtype() {
        return subtype;
    }

    public InternetResource(BigInteger id, String name, String url, ResourceType type, ResourceSubtype subtype, Rule rule) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.subtype = subtype;
        this.rule = rule;
    }
}
