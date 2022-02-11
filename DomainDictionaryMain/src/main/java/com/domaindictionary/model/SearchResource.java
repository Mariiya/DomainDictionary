package com.domaindictionary.model;


import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;

public interface SearchResource {
    String getName();

    ResourceType getType();

    ResourceSubtype getSubtype();

    BigInteger getId();

}
