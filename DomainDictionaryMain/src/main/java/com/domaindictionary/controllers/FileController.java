package com.domaindictionary.controllers;

import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.service.DomainDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final DomainDictionaryService domainDictionaryService;

    @Autowired
    public FileController(DomainDictionaryService domainDictionaryService) {
        this.domainDictionaryService = domainDictionaryService;
    }

    @PostMapping("/save-domain-dictionary")
    public ResponseEntity saveDomainDictionaryToFile(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody List<DictionaryEntry> datasourceDE) {
        ByteArrayInputStream arrayInputStream = domainDictionaryService.createDomainDictionary(datasourceDE);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new Date().getTime() + "DomainDictionary" + ".pdf";
        if(user!=null) {
            fileName = new Date().getTime() + user.getUser().getName()+ user.hashCode() + ".pdf";
        }
        headers.add("Content-Disposition", "inline; filename="+fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(arrayInputStream));
    }
}
