package com.onpu.domaindictionary.service;

import com.onpu.domaindictionary.model.DomainDictionary;
import org.springframework.stereotype.Service;

@Service
public class DomainAnalyzerService {
    public DomainDictionary analyze(DomainDictionary dd) {
        return dd;
    }

    public DomainDictionary analyze(DomainDictionary dd, String stylisticZone) {
        return dd;
    }
}
