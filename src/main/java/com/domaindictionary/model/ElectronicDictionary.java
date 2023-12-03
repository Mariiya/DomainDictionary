package com.domaindictionary.model;
import com.domaindictionary.model.enumeration.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
public class ElectronicDictionary extends InternalResource {
    private ResourceType type;
    private Rule rule;
    private List<DictionaryEntry> entries;
}
