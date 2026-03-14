package com.domaindictionary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "internet_resource")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternetResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    private String language;
}
