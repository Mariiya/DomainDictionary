package com.domaindictionary.model.builders;

import com.domaindictionary.model.Rule;

import java.math.BigInteger;

public class RuleBuilder {

    private BigInteger id;
    private String articleSeparator;
    private String termSeparator;
    private String definitionSeparator;
    private String stylisticZone;

    public RuleBuilder withId(BigInteger id) {
        this.id = id;
        return this;
    }

    public RuleBuilder withArticleSeparator(String articleSeparator) {
        this.articleSeparator = articleSeparator;
        return this;
    }

    public RuleBuilder withTermSeparator(String termSeparator) {
        this.termSeparator = termSeparator;
        return this;
    }

    public RuleBuilder withStylisticZone(String stylisticZone) {
        this.stylisticZone = stylisticZone;
        return this;
    }

    public RuleBuilder withDefinitionSeparator(String definitionSeparator) {
        this.definitionSeparator =definitionSeparator;
        return this;
    }


    public Rule build() {
        return new Rule(id, articleSeparator, termSeparator,definitionSeparator, stylisticZone);
    }
}
