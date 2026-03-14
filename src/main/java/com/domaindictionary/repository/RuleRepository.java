package com.domaindictionary.repository;

import com.domaindictionary.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
