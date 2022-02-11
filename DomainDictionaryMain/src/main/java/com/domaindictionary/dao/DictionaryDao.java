package com.domaindictionary.dao;

import com.domaindictionary.dao.mapper.ElectronicDictionaryMapper;
import com.domaindictionary.dao.mapper.SearchResourceMapper;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.parser.ParseDictionaryFileToStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Repository
public class DictionaryDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ParseDictionaryFileToStorage parser;

    @Autowired
    public DictionaryDao(DataSource dataSource, ParseDictionaryFileToStorage parser) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.parser = parser;
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
        return true;
    }

    private boolean addToDictionaryBank(SearchResource resource) {
        return jdbcTemplate.update(Constants.ADD_TO_RESOURCE_BANK,
                resource.getType().name(),
                resource.getSubtype().name()) > 0;
    }

    public List<SearchResource> getResources() {
        List resources = jdbcTemplate.query(Constants.GET_RESOURCES, new SearchResourceMapper());
        return resources;
    }

}
