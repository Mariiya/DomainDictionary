package com.domaindictionary.repository;

import com.domaindictionary.model.Thesaurus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesaurusRepository extends JpaRepository<Thesaurus, Long> {
    List<Thesaurus> findByCreatedById(Long userId);
}
