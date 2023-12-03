package com.domaindictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thesaurus extends InternalResource{
    private List<ThesaurusEntry> entries;
}
