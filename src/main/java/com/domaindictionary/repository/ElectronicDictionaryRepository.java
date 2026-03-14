package com.domaindictionary.repository;

import com.domaindictionary.model.ElectronicDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectronicDictionaryRepository extends JpaRepository<ElectronicDictionary, Long> {
    List<ElectronicDictionary> findByCreatedById(Long userId);
}
