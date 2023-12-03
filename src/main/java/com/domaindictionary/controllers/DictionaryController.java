package com.domaindictionary.controllers;

import com.domaindictionary.model.InternalResource;
import com.domaindictionary.model.User;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.service.ResourceService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final ResourceService resourcesService;

    public DictionaryController(ResourceService resourceService) {
        this.resourcesService = resourceService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteDictionary(@RequestParam String resourceId, @RequestParam User user) {
        resourcesService.deleteDictionary(resourceId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-new")
    public ResponseEntity deleteNewDictionary(@RequestParam String resourceId) {
        resourcesService.deleteNewDictionary(resourceId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<File> editDictionary(@RequestBody InternalResource resource) throws DocumentException, IOException {
        return ResponseEntity.ok(resourcesService.editDictionary(resource));
    }

}
