package com.domaindictionary.controller;

import com.domaindictionary.dto.SaveResourceRequest;
import com.domaindictionary.security.UserDetailsImpl;
import com.domaindictionary.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class InternalResourcesController {

    private final ResourceService resourceService;

    @PostMapping("/save")
    public ResponseEntity<byte[]> saveResource(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody SaveResourceRequest request) {
        byte[] pdf = resourceService.generateDocument(request, user.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + request.getName() + ".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/upload-dictionary")
    public ResponseEntity<?> uploadDictionary(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam String language,
            @RequestParam String articleSeparator,
            @RequestParam String termSeparator,
            @RequestParam String definitionSeparator) throws IOException {
        resourceService.uploadAndParseDictionary(
                file, name, language, user.getId(),
                articleSeparator, termSeparator, definitionSeparator);
        return ResponseEntity.ok().build();
    }
}
