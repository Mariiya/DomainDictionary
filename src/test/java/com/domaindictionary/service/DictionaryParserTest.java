package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import com.domaindictionary.service.parser.DictionaryParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DictionaryParserTest {

    @Mock
    private DictionaryEntryDao dictionaryEntryDao;

    @InjectMocks
    private DictionaryParser dictionaryParser;

    @Test
    void parseAndLoad_validContent_createsEntries() throws IOException {
        String content = "Слон - Великий ссавець\n\nМрія - Бажання або літак";
        Rule rule = Rule.builder()
                .articleSeparator("\n\n")
                .termSeparator(" - ")
                .definitionSeparator(";")
                .build();
        ElectronicDictionary dict = ElectronicDictionary.builder()
                .id(1L)
                .name("test")
                .rule(rule)
                .build();

        dictionaryParser.parseAndLoad(content, dict);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<DictionaryEntryDoc>> captor = ArgumentCaptor.forClass(List.class);
        verify(dictionaryEntryDao).bulkInsert(captor.capture());

        List<DictionaryEntryDoc> entries = captor.getValue();
        assertEquals(2, entries.size());
        assertEquals("Слон", entries.get(0).getTerm());
        assertEquals("Мрія", entries.get(1).getTerm());
    }
}
