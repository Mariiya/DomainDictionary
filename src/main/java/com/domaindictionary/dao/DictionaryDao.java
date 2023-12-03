package com.domaindictionary.dao;

import com.domaindictionary.dao.mapper.ElectronicDictionaryMapper;
import com.domaindictionary.dao.mapper.SearchResourceMapper;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.InternalResource;
import com.domaindictionary.model.Thesaurus;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class DictionaryDao {

    private final JdbcTemplate jdbcTemplate;

    public ElectronicDictionary getElectronicDictionary(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(Constants.GET_ELECTRONIC_DICTIONARY, new ElectronicDictionaryMapper(), id);
        } catch (EmptyResultDataAccessException e) {

        }
        return null;
    }

    public boolean createDictionary(InternalResource resource) throws IOException {

        if (resource instanceof ElectronicDictionary) {
            jdbcTemplate.update(Constants.CREATE_ELECTRONIC_DICTIONARY,
                    resource.getName(),
                    resource.getUser(),
                    resource.getPathToFile(),
                    ((ElectronicDictionary) resource).getType(),
                    new Date());
            if (((ElectronicDictionary) resource).getRule() != null) {
                jdbcTemplate.update(Constants.CREATE_RULE,
                        ((ElectronicDictionary) resource).getRule().getArticleSeparator(),
                        ((ElectronicDictionary) resource).getRule().getTermSeparator(),
                        ((ElectronicDictionary) resource).getRule().getDefinitionSeparator());
            }

            jdbcTemplate.execute("commit");
            return true;
        } else if (resource instanceof Thesaurus) {
            jdbcTemplate.update(Constants.CREATE_THESAURUS,
                    resource.getName(),
                    resource.getUser(),
                    resource.getPathToFile(),
                    resource.getLanguage(),
                    new Date());

            jdbcTemplate.execute("commit");
            return true;
        }
    }


    public void updateDictionary(InternalResource resource) {
        jdbcTemplate.update(Constants.UPDATE_DICTIONARY,
                resource.getName(),
                resource.getUser(),
                resource.getPathToFile(),
                new Date());
    }

    public void deleteDictionary(String resourceId) {
        jdbcTemplate.update(Constants.DELETE_DICTIONARY, resourceId);
    }

    public InternalResource getResource(String resourceId) {
        return jdbcTemplate.queryForObject(Constants.GET_RESOURCE, resourceId, new InternalResourceMapper());
    }
}
