package com.pullenti.domaindictionary.model;


import com.pullenti.domaindictionary.model.enumeration.ResourceSubtype;
import com.pullenti.domaindictionary.model.enumeration.ResourceType;

import java.math.BigInteger;

public interface SearchResource {
    String getName();

    ResourceType getType();

    ResourceSubtype getSubtype();

    BigInteger getId();

}
