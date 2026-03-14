package com.domaindictionary.controller;

import com.domaindictionary.service.KeyWordsExtractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/text-preliminary")
@RequiredArgsConstructor
public class PreliminaryTextController {

    private final KeyWordsExtractorService keyWordsExtractorService;

    @GetMapping("/text")
    public ResponseEntity<List<String>> getKeyWordsFromText(@RequestParam String text) {
        return ResponseEntity.ok(keyWordsExtractorService.extractKeyWords(text));
    }

    @PostMapping("/file")
    public ResponseEntity<List<String>> getKeyWordsFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.endsWith(".txt")) {
            return ResponseEntity.badRequest().build();
        }
        if (file.getSize() > 50 * 1024 * 1024) {
            return ResponseEntity.badRequest().build();
        }
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        return ResponseEntity.ok(keyWordsExtractorService.extractKeyWords(content));
    }
}
