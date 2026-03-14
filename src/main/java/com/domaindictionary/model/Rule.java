package com.domaindictionary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_separator")
    private String articleSeparator;

    @Column(name = "term_separator")
    private String termSeparator;

    @Column(name = "definition_separator")
    private String definitionSeparator;
}
