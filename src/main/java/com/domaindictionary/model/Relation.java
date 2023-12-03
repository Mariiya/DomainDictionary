package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.RelationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relation {
    private String id;
    private List<String> synsets;
    private RelationType relationType;
    private double coef;
}
