package com.domaindictionary.dao.mapper;


import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.model.builders.ElectronicDictionaryBuilder;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResourceMapper implements RowMapper<SearchResource> {
    public SearchResource mapRow(ResultSet resultSet, int i) throws SQLException {
        if ("INTERNET_RESOURCE".equals(resultSet.getString("type"))) {
            return null;//new InternetResource();
        }
        ElectronicDictionary dictionary = new ElectronicDictionaryBuilder()
                .withId(new BigInteger(resultSet.getString("RESOURCE_ID")))
                .withName(resultSet.getString("name"))
                .withType(ResourceType.valueOf(resultSet.getString("type")))
                .withSubType(ResourceSubtype.valueOf(resultSet.getString("subtype")))
                .build();
        return dictionary;
    }
}
