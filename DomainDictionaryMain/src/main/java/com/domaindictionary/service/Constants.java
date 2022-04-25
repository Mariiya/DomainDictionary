package com.domaindictionary.service;

import com.domaindictionary.webapi.InternetResourceSearchAPI;
import com.domaindictionary.webapi.SumInUaAPI;
import com.domaindictionary.webapi.WikipediaAPI;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
  Map<String, Class<? extends InternetResourceSearchAPI>> internetResources = new HashMap<String, Class<? extends InternetResourceSearchAPI>>() {{
        put(WIKIPEDIA,  WikipediaAPI.class);
        put(SUMINUA, SumInUaAPI.class);
    }};

    String WIKIPEDIA = "wikipedia";
    String SUMINUA = "suminua";
}
