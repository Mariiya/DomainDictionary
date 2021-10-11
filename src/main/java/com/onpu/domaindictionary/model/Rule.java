package com.onpu.domaindictionary.model;

import java.math.BigInteger;

public class Rule {
    private BigInteger id;
    private String articleSeparator;
    private String termSeparator;
    private String definitionSeparator;
    private String stylisticZone;

    public Rule(BigInteger id, String articleSeparator, String termSeparator, String definitionSeparator, String stylisticZone) {
        this.id = id;
        this.articleSeparator = articleSeparator;
        this.termSeparator = termSeparator;
        this.definitionSeparator = definitionSeparator;
        this.stylisticZone = stylisticZone;
    }

    public String getRegex() {
        return "";
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getArticleSeparator() {
        return articleSeparator;
    }

    public void setArticleSeparator(String articleSeparator) {
        this.articleSeparator = articleSeparator;
    }

    public String getTermSeparator() {
        return termSeparator;
    }

    public void setTermSeparator(String termSeparator) {
        this.termSeparator = termSeparator;
    }

    public String getStylisticZone() {
        return stylisticZone;
    }

    public String getDefinitionSeparator() {
        if(definitionSeparator.contains("num")){
            return "\\d"+definitionSeparator.replace("num","");
        }
        return definitionSeparator;
    }

    public void setDefinitionSeparator(String definitionSeparator) {
        this.definitionSeparator = definitionSeparator;
    }

    public void setStylisticZone(String stylisticZone) {
        this.stylisticZone = stylisticZone;
    }
}
