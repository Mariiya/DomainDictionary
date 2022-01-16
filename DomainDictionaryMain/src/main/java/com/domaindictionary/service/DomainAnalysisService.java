package com.domaindictionary.service;

import com.domaindictionary.model.DomainDictionary;
import org.springframework.stereotype.Service;

@Service
public class DomainAnalysisService {
    public DomainDictionary analyze(DomainDictionary dd) {
        return dd;
    }

    public DomainDictionary analyze(DomainDictionary dd, String stylisticZone) {
        return dd;
    }
}
