package com.domaindictionary.dao;

import com.domaindictionary.dao.mapper.ElectronicDictionaryMapper;
import com.domaindictionary.dao.mapper.SearchResourceMapper;
import com.domaindictionary.elasticsearch.api.EntriesLoader;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.DomainDictionary;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.parser.ParseDictionaryFileToStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Repository
public class DictionaryDao {

    private final JdbcTemplate jdbcTemplate;
    private ParseDictionaryFileToStorage parser;
    private EntriesLoader entriesLoader;

    @Autowired
    public DictionaryDao(DataSource dataSource, ParseDictionaryFileToStorage parser, EntriesLoader entriesLoader) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.parser = parser;
        this.entriesLoader = entriesLoader;
    }

    public ElectronicDictionary getElectronicDictionary(BigInteger id) {
        return jdbcTemplate.queryForObject(Constants.GET_ELECTRONIC_DICTIONARY, new ElectronicDictionaryMapper(), id);
    }

    public boolean createElectronicDictionary(ElectronicDictionary dictionary) throws IOException {
        jdbcTemplate.update(Constants.CREATE_ELECTRONIC_DICTIONARY,
                dictionary.getName(),
                dictionary.getAuthor(),
                dictionary.getPathToFile());
        if (dictionary.getRule() != null) {
            jdbcTemplate.update(Constants.CREATE_RULE,
                    dictionary.getRule().getArticleSeparator(),
                    dictionary.getRule().getTermSeparator(),
                    dictionary.getRule().getDefinitionSeparator(),
                    dictionary.getRule().getStylisticZone());
            addToDictionaryBank(dictionary);
        }
        parser.parse(dictionary);
        jdbcTemplate.execute("commit");
        return true;
    }

    public boolean createDomainDictionary(DomainDictionary dictionary) throws IOException {
        jdbcTemplate.update(Constants.CREATE_DOMAIN_DICTIONARY,
                dictionary.getCreatedAt(),
                dictionary.getAuthor().getId(),
                dictionary.getPathToFile());

        BigInteger ddId = jdbcTemplate.queryForObject(Constants.GET_DOMAIN_DICTIONARY,
                BigInteger.class, dictionary.getAuthor().getId(), dictionary.getPathToFile());

        for (DictionaryEntry de : dictionary.getEntries()) {
            de.setResourceId(ddId);
        }
        entriesLoader.insertDictionaryEntry(dictionary.getEntries());
        jdbcTemplate.execute("commit");
        return true;
    }

    private boolean addToDictionaryBank(SearchResource resource) {
        int result = jdbcTemplate.update(Constants.ADD_TO_RESOURCE_BANK,
                resource.getType().name(),
                resource.getSubtype().name());
        jdbcTemplate.execute("commit");
        return result > 0;
    }

    public List<SearchResource> getResources() {
        List resources = jdbcTemplate.query(Constants.GET_RESOURCES, new SearchResourceMapper());
        return resources;
    }

    public int getNumberOfDDByUser(BigInteger userId) {
        return jdbcTemplate.queryForObject(Constants.GET_NUMBER_OF_DD_BY_USER, Integer.class, userId);
    }

}
