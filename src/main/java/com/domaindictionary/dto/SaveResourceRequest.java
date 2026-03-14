package com.domaindictionary.dto;

import com.domaindictionary.model.enumeration.ResourceType;
import lombok.Data;

import java.util.List;

@Data
public class SaveResourceRequest {
    private String name;
    private String language;
    private ResourceType type;
    private List<SearchResult> entries;
}
