package com.domaindictionary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictionaryEntry extends Entry {
    public static final String INDEX = "entity";
    private Collection<String> definition;
}
