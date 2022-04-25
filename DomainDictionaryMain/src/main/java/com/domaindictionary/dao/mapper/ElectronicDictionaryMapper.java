package com.domaindictionary.dao.mapper;

import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.builders.ElectronicDictionaryBuilder;
import com.domaindictionary.model.builders.RuleBuilder;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectronicDictionaryMapper implements RowMapper<ElectronicDictionary> {
    public ElectronicDictionary mapRow(ResultSet resultSet, int i) throws SQLException {
        ElectronicDictionary dictionary = new ElectronicDictionaryBuilder()
                .withId(new BigInteger(resultSet.getString("electronic_dictionary_id")))
                .withName(resultSet.getString("name"))
                .withAuthor(resultSet.getString("author"))
                .withPathToFile(resultSet.getString("path_to_file"))
                .withType(ResourceType.valueOf(resultSet.getString("type")))
                .withSubType(ResourceSubtype.valueOf(resultSet.getString("subtype")))
                .withRule(new RuleBuilder()
                        .withId(new BigInteger(resultSet.getString("rule_id")))
                        .withArticleSeparator(resultSet.getString("article_separator"))
                        .withTermSeparator(resultSet.getString("term_separator"))
                        .withTermSeparator(resultSet.getString("relator"))
                        .withStylisticZone(resultSet.getBoolean("stylistic_zone"))
                        .build())
                .build();
        return dictionary;
    }
}
