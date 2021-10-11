package com.onpu.domaindictionary.model;


import com.onpu.domaindictionary.model.enumeration.ResourceSybtype;
import com.onpu.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;

public interface SearchResource {
    String getName();

    ResourceType getType();

    ResourceSybtype getSubtype();

    BigInteger getId();
}
