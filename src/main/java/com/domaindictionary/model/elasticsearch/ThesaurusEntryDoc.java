package com.domaindictionary.model.elasticsearch;

import com.domaindictionary.model.enumeration.RelationType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThesaurusEntryDoc {
    public static final String INDEX = "thesaurus_entries";

    private String id;
    private String term;
    private Long resourceId;
    private List<Relation> relations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Relation {
        private List<String> synsets;
        private RelationType type;
        private double coef;
    }
}
