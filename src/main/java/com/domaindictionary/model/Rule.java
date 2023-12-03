package com.domaindictionary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule {
    private BigInteger id;
    private String articleSeparator;
    private String termSeparator;
    private String definitionSeparator;
}
