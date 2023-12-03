package com.domaindictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThesaurusEntry extends Entry {
    public static final String INDEX = "tentity";
    private List<Relation> relations;
}
