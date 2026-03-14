package com.domaindictionary.model;

import com.domaindictionary.model.enumeration.ResourceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "electronic_dictionary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectronicDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "path_to_file")
    private String pathToFile;

    private String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rule_id")
    private Rule rule;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
