package com.domaindictionary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public abstract class InternalResource {
    private BigInteger id;
    private String name;
    private User user;
    private String pathToFile;
    private String language;

    public abstract List<Entry> getEntries();
}
