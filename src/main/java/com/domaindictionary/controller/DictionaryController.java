package com.domaindictionary.controller;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.repository.ElectronicDictionaryRepository;
import com.domaindictionary.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionaries")
@RequiredArgsConstructor
public class DictionaryController {

    private final ElectronicDictionaryRepository dictionaryRepository;

    @GetMapping
    public ResponseEntity<List<ElectronicDictionary>> getMyDictionaries(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(dictionaryRepository.findByCreatedById(user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDictionary(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl user) {
        return dictionaryRepository.findById(id)
                .filter(d -> d.getCreatedBy().getId().equals(user.getId()))
                .map(d -> {
                    dictionaryRepository.delete(d);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
