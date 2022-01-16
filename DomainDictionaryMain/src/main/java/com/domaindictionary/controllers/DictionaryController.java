package com.domaindictionary.controllers;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.service.ResourcesBank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final ResourcesBank resourcesBack;

    public DictionaryController(ResourcesBank resourcesBack) {
        this.resourcesBack = resourcesBack;
    }

    @PostMapping("/create-dictionary")
    public ResponseEntity<?> createElectronicDictionary(@AuthenticationPrincipal UserDetailsImpl creator, @RequestParam String dictionary, @RequestParam MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ElectronicDictionary electronicDictionary = objectMapper.readValue(dictionary, ElectronicDictionary.class);
        resourcesBack.createElectronicDictionary(electronicDictionary, file);
        return ResponseEntity.ok().build();
    }
}
