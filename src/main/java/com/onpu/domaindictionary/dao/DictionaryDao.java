package com.onpu.domaindictionary.dao;


import com.onpu.domaindictionary.dao.mapper.ElectronicDictionaryMapper;
import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.ElectronicDictionary;
import com.onpu.domaindictionary.model.SearchResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DictionaryDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DictionaryDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public DictionaryEntry search(String term, BigInteger resourceId) {
       List<String> definition =  jdbcTemplate.queryForList(Constants.SEARCH_TERM,String.class, term,term,resourceId);
       return  new DictionaryEntry(BigInteger.ONE,term,definition);
    }

    public ElectronicDictionary getElectronicDictionary(BigInteger id) {
        return jdbcTemplate.queryForObject(Constants.GET_ELECTRONIC_DICTIONARY, new ElectronicDictionaryMapper(), id);
    }

    public boolean createEntries(ElectronicDictionary dictionary) {
        jdbcTemplate.batchUpdate(Constants.CREATE_DICTIONARY_ENTRY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                DictionaryEntry entry = dictionary.getEntries().get(i);
                preparedStatement.setString(1, entry.getTerm());
                preparedStatement.setString(2, entry.getDefinition().toString());
                preparedStatement.setString(3, dictionary.getId().toString());
            }

            @Override
            public int getBatchSize() {
                return dictionary.getEntries().size();
            }
        });
        return true;
    }

    public boolean createElectronicDictionary(ElectronicDictionary dictionary) {
        jdbcTemplate.update(Constants.CREATE_ELECTRONIC_DICTIONARY,
                dictionary.getName(),
                dictionary.getAuthor(),
                dictionary.getPathToFile());
     /*   jdbcTemplate.update(Constants.CREATE_RULE,
                dictionary.getRule().getArticleSeparator(),
                dictionary.getRule().getTermSeparator(),
                dictionary.getRule().getStylisticZone());*/
        addToDictionaryBank(dictionary);
        return true;
    }

    private boolean addToDictionaryBank(SearchResource resource) {
        return true;
    }

    public List<SearchResource> getResources() {
        List resources = jdbcTemplate.query(Constants.GET_RESOURCES, new ElectronicDictionaryMapper());
        System.out.println(resources);
        return resources;
    }

}
