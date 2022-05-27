package com.pullenti.domaindictionary.controllers;

import com.pullenti.domaindictionary.model.ElectronicDictionary;
import com.pullenti.domaindictionary.model.SearchResource;
import com.pullenti.domaindictionary.model.enumeration.ResourceSubtype;
import com.pullenti.domaindictionary.model.enumeration.ResourceType;
import com.pullenti.domaindictionary.secutity.services.UserDetailsImpl;
import com.pullenti.domaindictionary.service.ResourcesBank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/dictionary")
public class ResourceBankController {
    private final ResourcesBank resourcesBack;

    public ResourceBankController(ResourcesBank resourcesBack) {
        this.resourcesBack = resourcesBack;
    }

    @PostMapping("/create-dictionary")
    public boolean createElectronicDictionary(@AuthenticationPrincipal UserDetailsImpl creator, @RequestParam String dictionary, @RequestParam MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ElectronicDictionary electronicDictionary = objectMapper.readValue(dictionary, ElectronicDictionary.class);
        return resourcesBack.createElectronicDictionary(electronicDictionary, file);
    }

    @GetMapping("/possible-resource-subtypes")
    public Collection<ResourceSubtype> getPossibleResourceSubtypes(@RequestParam ResourceType type) {
        return resourcesBack.getPossibleResourceSubtypes(type);
    }

    @GetMapping("/catalog")
    public Map<ResourceType, Map<ResourceSubtype, Collection<SearchResource>>> getCatalog() {
        return resourcesBack.getCatalog();
    }

    @GetMapping("/search-resources")
    public Collection<SearchResource> getSearchResources() {
        return resourcesBack.getResources();
    }

    @GetMapping("/number-of-resources")
    public int getNumberOfRsources() {
        return resourcesBack.getResources().size();
    }

    @GetMapping("/possible-resource-types")
    public Collection<ResourceType> getPossibleResourceTypes() {
        return resourcesBack.getPossibleResourceTypes();
    }

    @GetMapping("/possible-relators")
    public Collection<String> getPossibleRelators() {
        return resourcesBack.getPossibleRelators();
    }

    @GetMapping("/possible-article-separator")
    public Collection<String> getPossibleArticleSeparator() {
        return resourcesBack.getPossibleArticleSeparator();
    }

    @GetMapping("/number-or-dd/{id}")
    public int getNumberOfDDByUser(@PathVariable BigInteger id) {
        return resourcesBack.getNumberOfDDByUser(id);
    }
}
