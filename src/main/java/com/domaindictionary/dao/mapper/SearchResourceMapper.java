package com.domaindictionary.dao.mapper;


import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.InternalResource;
import com.domaindictionary.model.builders.ElectronicDictionaryBuilder;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResourceMapper implements RowMapper<InternalResource> {
    public InternalResource mapRow(ResultSet resultSet, int i) throws SQLException {
        if ("INTERNET_RESOURCE".equals(resultSet.getString("type"))) {
            return null;
        }
        ElectronicDictionary dictionary = ElectronicDictionary.builder()
                .id(new BigInteger(resultSet.getString("RESOURCE_ID")))
                .name(resultSet.getString("name"))
                .withType(ResourceType.valueOf(resultSet.getString("type")))
                .withSubType(ResourceSubtype.valueOf(resultSet.getString("subtype")))
                .build();
        return dictionary;
    }
}
