package com.domaindictionary.model.elasticsearch;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryEntryDoc {
    public static final String INDEX = "dictionary_entries";

    private String id;
    private String term;
    private Long resourceId;
    private List<String> definitions;
}
