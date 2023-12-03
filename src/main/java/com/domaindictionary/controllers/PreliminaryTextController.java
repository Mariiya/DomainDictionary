package com.domaindictionary.controllers;

import com.domaindictionary.service.KeyWordsExtractorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/text-preliminary")
public class PreliminaryTextController {

    private final KeyWordsExtractorService keyWordsExtractorService;

    public PreliminaryTextController(KeyWordsExtractorService keyWordsExtractorService) {
        this.keyWordsExtractorService = keyWordsExtractorService;
    }

    @GetMapping("/text")
    public ResponseEntity<List<String>> getKeyWords(@RequestParam String text) throws Exception {
        return ResponseEntity.ok(keyWordsExtractorService.getKeyWords(text));
    }

    @GetMapping("/file")
    public ResponseEntity<List<String>> getKeyWords(@RequestParam File file) throws Exception {
        if (validateFile(file)) {
            return ResponseEntity.ok(
                    keyWordsExtractorService.getKeyWords(file));
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean validateFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int character;
            while ((character = reader.read()) != -1) {
                if (!isTextCharacter(character)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isTextCharacter(int character) {
        return Character.isDefined(character);
    }
}
