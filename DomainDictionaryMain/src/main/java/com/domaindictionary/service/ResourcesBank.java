package com.domaindictionary.service;


import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.model.enumeration.ResourceSybtype;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.utils.RegexConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@Service
public class ResourcesBank {
    private static final Logger LOG = Logger.getLogger(ResourcesBank.class);
    private final DictionaryDao dictionaryDao;
    private final DictionaryService dictionaryService;
    private final FileUploadService fileUploadService;


    @Autowired
    public ResourcesBank(DictionaryDao dictionaryDao, DictionaryService service, FileUploadService fileUploadService) {
        this.dictionaryService = service;
        this.dictionaryDao = dictionaryDao;
        this.fileUploadService = fileUploadService;
    }

    public boolean createElectronicDictionary(ElectronicDictionary dictionary, MultipartFile file) {
        try {
            String generatedFilePath = fileUploadService.saveFile(file, dictionary.getPathToFile());
            if (!generatedFilePath.isEmpty()) {
                dictionary.setPathToFile(generatedFilePath);
                dictionaryDao.createElectronicDictionary(dictionary);
                return true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    public Collection<SearchResource> getResources() {
        return dictionaryDao.getResources();
    }

    public Collection<SearchResource> getCatalog(ResourceType type, ResourceSybtype sybtype) {
        return getResources();
    }

    public SearchResource getResource(BigInteger id) throws FileNotFoundException {
        for (SearchResource resource : getResources()) {
            if (resource.getId().equals(id))
                return resource;
        }
        LOG.error("Resource with ID " + id + " not found");
        throw new FileNotFoundException("Resource not found");
    }

    public List<ResourceType> getPossibleResourceTypes() {
        return List.of(ResourceType.values());
    }

    public Collection<ResourceSybtype> getPossibleResourceSybtypes(ResourceType resourceType) {
        if (ResourceType.GENERAL.equals(resourceType)) {
            return ResourceSybtype.language;
        }
        return ResourceSybtype.domain;
    }

    public Collection<String> getPossibleRelators() {
         return RegexConstants.getTemplatesForRelator();
    }

    public Collection<String> getPossibleArticleSeparator() {
        return RegexConstants.getTemplatesForArticleSeparator();
    }

}
