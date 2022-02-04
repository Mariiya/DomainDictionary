package com.domaindictionary.controllers;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.service.ResourcesBank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/dictionary")
public class ResourceBankController {
    private final ResourcesBank resourcesBack;

    public ResourceBankController(ResourcesBank resourcesBack) {
        this.resourcesBack = resourcesBack;
    }

    @PostMapping("/create-dictionary")
    public ResponseEntity<?> createElectronicDictionary(@AuthenticationPrincipal UserDetailsImpl creator, @RequestParam String dictionary, @RequestParam MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ElectronicDictionary electronicDictionary = objectMapper.readValue(dictionary, ElectronicDictionary.class);
        resourcesBack.createElectronicDictionary(electronicDictionary, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/possible-resource-sybtypes")
    public Collection<ResourceSybtype> getPossibleResourceSybtypes(@RequestParam ResourceType type) {
        return resourcesBack.getPossibleResourceSybtypes(type);
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
}
