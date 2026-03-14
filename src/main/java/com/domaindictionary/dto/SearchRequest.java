package com.domaindictionary.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private List<String> terms;
    private boolean analyzeEnabled;
    private String domainContext;
}
