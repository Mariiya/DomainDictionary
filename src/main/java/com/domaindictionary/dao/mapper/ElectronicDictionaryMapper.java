package com.domaindictionary.dao.mapper;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.jdbc.core.RowMapper;
import com.domaindictionary.model.User;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectronicDictionaryMapper implements RowMapper<ElectronicDictionary> {
    public ElectronicDictionary mapRow(ResultSet resultSet, int i) throws SQLException {
        return ElectronicDictionary.builder()
                .id(new BigInteger(resultSet.getString("electronic_dictionary_id")))
                .name(resultSet.getString("name"))
                .user(User.builder().id(new BigInteger(resultSet.getString("author"))).build())
                        .pathToFile(resultSet.getString("path_to_file"))
                .rule(Rule.builder()
                        .id(new BigInteger(resultSet.getString("rule_id")))
                        .articleSeparator(resultSet.getString("article_separator"))
                        .termSeparator(resultSet.getString("term_separator"))
                        .definitionSeparator("definition_separator")
                        .build())
                .build();
    }
}
