package com.domaindictionary.service;

import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.webapi.InternetResourceSearchAPI;
import com.domaindictionary.webapi.WikipediaAPI;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public interface Constants {
  Map<String, Class<? extends InternetResourceSearchAPI>> internetResources = new HashMap<String, Class<? extends InternetResourceSearchAPI>>() {{
        put(WIKIPEDIA,  WikipediaAPI.class);
    }};

    String WIKIPEDIA = "wikipedia";

}
