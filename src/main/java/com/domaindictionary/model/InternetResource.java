package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternetResource {

    private BigInteger id;
    private String name;
    private String url;
    private ResourceType type;
    private Rule rule;

}
