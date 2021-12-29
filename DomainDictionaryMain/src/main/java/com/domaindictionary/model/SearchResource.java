package com.domaindictionary.model;


import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;

public interface SearchResource {
    String getName();

    ResourceType getType();

    ResourceSybtype getSubtype();

    BigInteger getId();
}
