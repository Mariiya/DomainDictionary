package com.domaindictionary.controllers;

import com.domaindictionary.model.InternalResource;
import com.domaindictionary.model.User;
import com.domaindictionary.secutity.services.UserDetailsImpl;
import com.domaindictionary.secutity.services.UserService;
import com.domaindictionary.service.ResourceService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/internal-resources")
public class InternalResourcesController {

    private final ResourceService resourceService;
    private final UserService userService;

    @Autowired
    public InternalResourcesController(ResourceService resourceService, UserService userService) {
        this.resourceService = resourceService;
        this.userService = userService;
    }

    @PostMapping("/save-dictionary")
    public ResponseEntity saveDictionary(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody InternalResource resource) throws DocumentException, IOException {
        User userFromDB = userService.getUserById(user.getId());
        File arrayInputStream = resourceService.saveResource(resource, userFromDB);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new Date().getTime() + "Dictionary" + ".pdf";
        if (user != null) {
            fileName = new Date().getTime() + user.getUser().getName() + user.hashCode() + ".pdf";
        }
        headers.add("Content-Disposition", "inline; filename=" + fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(arrayInputStream);
    }
}
