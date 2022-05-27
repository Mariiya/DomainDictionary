package com.pullenti.domaindictionary.controllers;

import com.pullenti.domaindictionary.model.DictionaryEntry;
import com.pullenti.domaindictionary.model.User;
import com.pullenti.domaindictionary.secutity.services.UserDetailsImpl;
import com.pullenti.domaindictionary.secutity.services.UserDetailsServiceImpl;
import com.pullenti.domaindictionary.service.ResourcesBank;
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

    private final ResourcesBank resourcesBank;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public FileController(ResourcesBank resourcesBank, UserDetailsServiceImpl userService) {
        this.resourcesBank = resourcesBank;
        this.userService = userService;
    }

    @PostMapping("/save-domain-dictionary")
    public ResponseEntity saveDomainDictionaryToFile(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody List<DictionaryEntry> datasourceDE) {
        User userFromDB = userService.getUserById(user.getId());
        ByteArrayInputStream arrayInputStream = resourcesBank.createDomainDictionary(userFromDB, datasourceDE);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new Date().getTime() + "DomainDictionary" + ".pdf";
        if (user != null) {
            fileName = new Date().getTime() + user.getUser().getName() + user.hashCode() + ".pdf";
        }
        headers.add("Content-Disposition", "inline; filename=" + fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(arrayInputStream));
    }
}
