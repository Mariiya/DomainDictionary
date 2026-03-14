package com.domaindictionary.repository;

import com.domaindictionary.model.InternetResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternetResourceRepository extends JpaRepository<InternetResource, Long> {
}
