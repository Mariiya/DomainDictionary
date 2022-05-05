package com.domaindictionary.service;


import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.DomainDictionary;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.model.User;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.utils.RegexConstants;
import com.itextpdf.text.DocumentException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Service
public class ResourcesBank {
    private static final Logger LOG = Logger.getLogger(ResourcesBank.class);
    private final DictionaryDao dictionaryDao;
    private final SearchManager searchManager;
    private final FileUploadService fileUploadService;


    @Autowired
    public ResourcesBank(DictionaryDao dictionaryDao, SearchManager service, FileUploadService fileUploadService) {
        this.searchManager = service;
        this.dictionaryDao = dictionaryDao;
        this.fileUploadService = fileUploadService;
    }

    public boolean createElectronicDictionary(ElectronicDictionary dictionary, MultipartFile file) {
        try {
            String generatedFilePath = fileUploadService.saveFile(file, dictionary.getPathToFile());
            if (!generatedFilePath.isEmpty()) {
                dictionary.setPathToFile(generatedFilePath);
                for (SearchResource searcresource : dictionaryDao.getResources()) {
                    if (searcresource != null && searcresource.getName() != null
                            && searcresource.getName().equals(dictionary.getName())) {
                        dictionary.setName(dictionary.getName() + "_" + searcresource.getId());
                    }
                }
                dictionaryDao.createElectronicDictionary(dictionary);
                return true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    public Map<ResourceType, Map<ResourceSubtype, Collection<SearchResource>>> getCatalog() {
        Map<ResourceType, Map<ResourceSubtype, Collection<SearchResource>>> result = new HashMap<>();

        for (SearchResource r : getResources()) {
            if (result.get(r.getType()) == null) {
                result.put(r.getType(), new HashMap() {{
                    put(r.getSubtype(), new ArrayList() {{
                        add(r);
                    }});
                }});
            } else {
                if (result.get(r.getType()).get(r.getSubtype()) == null) {
                    result.get(r.getType()).put(r.getSubtype(),
                            new ArrayList() {{
                                add(r);
                            }});
                } else {
                    result.get(r.getType()).get(r.getSubtype()).add(r);
                }
            }
        }

        return result;
    }

    public Collection<SearchResource> getResources() {
        List<SearchResource> result = new ArrayList<>();
        List<SearchResource> temp = dictionaryDao.getResources();
        for (SearchResource searchResource : temp) {
            if (searchResource.getName() != null && !searchResource.getName().isEmpty()) {
                result.add(searchResource);
            }
        }
        return result;
    }

    public ElectronicDictionary getElectronicDictionary(BigInteger resourceId) {
        return dictionaryDao.getElectronicDictionary(resourceId);
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
        return Arrays.asList(ResourceType.values());
    }

    public Collection<ResourceSubtype> getPossibleResourceSubtypes(ResourceType resourceType) {
        if (ResourceType.GENERAL.equals(resourceType)) {
            return ResourceSubtype.language;
        }
        return ResourceSubtype.domain;
    }

    public Collection<String> getPossibleRelators() {
        return RegexConstants.getTemplatesForRelator();
    }

    public Collection<String> getPossibleArticleSeparator() {
        return RegexConstants.getTemplatesForArticleSeparator();
    }

    public ByteArrayInputStream createDomainDictionary(User user, List<DictionaryEntry> entries) {
        try {
            ByteArrayInputStream byteArrayInputStream = fileUploadService.createFileForDomainDictionary(entries);
            String path = fileUploadService.saveToFile(byteArrayInputStream, user.getName());
            DomainDictionary dd = new DomainDictionary();
            dd.setPathToFile(path);
            dd.setCreatedAt(new Date());
            dd.setAuthor(user);
            dd.setEntries(entries);
            dictionaryDao.createDomainDictionary(dd);
            return byteArrayInputStream;
        } catch (DocumentException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
        throw new RuntimeException("Error during file creation");
    }

    public int getNumberOfDDByUser(BigInteger userId) {
        return dictionaryDao.getNumberOfDDByUser(userId);
    }
}
