package com.domaindictionary.service;


import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

@Service
public class DictionaryBank {
    private final DictionaryDao dictionaryDao;

    private Collection<SearchResource> resources;
    private DictionaryService dictionaryService;

    @Autowired
    public DictionaryBank(DictionaryDao dictionaryDao, DictionaryService service) {
     //  resources = dictionaryDao.getResources();
        this.dictionaryService = service;
        this.dictionaryDao = dictionaryDao;
        //setting test-data
      /*  ElectronicDictionary electronicDictionary = new ElectronicDictionary(BigInteger.ONE, "Oze",
                "Ozhegov", "src/main/resources/dictionaries/ozhegov.txt",
                null, null, new Rule(new BigInteger("56"), "<ar><k>", ",", "num.", "none"), null);

        dictionaryService.initialize(electronicDictionary);*/
    }

    public Collection<SearchResource> getResources() {
        return resources;
    }

    public Collection<SearchResource> getCatalog(ResourceType type, ResourceSybtype sybtype) {
        return resources;
    }

    public SearchResource getResource(BigInteger id) {
        for (SearchResource resource : resources) {
            if (resource.getId().equals(id))
                return resource;
        }
        return new ElectronicDictionary(BigInteger.ONE,"","","",null,null, null,Collections.emptyList());
    }

}
