package com.domaindictionary.service;

import com.domaindictionary.dto.SearchResult;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchOrchestratorTest {

    @Mock
    private DictionarySearchService dictionarySearchService;

    @Mock
    private ExternalResourceSearchService externalResourceSearchService;

    @Mock
    private DomainAnalyzer domainAnalyzer;

    @InjectMocks
    private SearchOrchestrator searchOrchestrator;

    @Test
    void search_singleTerm_foundInDictionary() throws IOException {
        DictionaryEntryDoc doc = DictionaryEntryDoc.builder()
                .term("Слон")
                .definitions(List.of("Великий ссавець із хоботом"))
                .build();

        when(dictionarySearchService.search(List.of("Слон"))).thenReturn(List.of(doc));

        List<SearchResult> results = searchOrchestrator.search(List.of("Слон"), false, null);

        assertEquals(1, results.size());
        assertEquals("Слон", results.get(0).getTerm());
        assertFalse(results.get(0).getDefinitions().isEmpty());
        assertEquals("internal", results.get(0).getSource());
    }

    @Test
    void search_termNotFound_returnsEmpty() throws IOException {
        when(dictionarySearchService.search(anyList())).thenReturn(List.of());
        when(externalResourceSearchService.search("НеіснуючийТермін"))
                .thenReturn(SearchResult.builder()
                        .term("НеіснуючийТермін")
                        .definitions(List.of())
                        .source("not_found")
                        .build());

        List<SearchResult> results = searchOrchestrator.search(List.of("НеіснуючийТермін"), false, null);

        assertEquals(1, results.size());
        assertTrue(results.get(0).getDefinitions().isEmpty());
    }

    @Test
    void search_multiwordTerm_searchesSubterms() throws IOException {
        when(dictionarySearchService.search(List.of("Порцелянова ваза"))).thenReturn(List.of());
        DictionaryEntryDoc doc = DictionaryEntryDoc.builder()
                .term("ваза")
                .definitions(List.of("Посудина для квітів"))
                .build();
        when(dictionarySearchService.search(List.of("Порцелянова"))).thenReturn(List.of());
        when(dictionarySearchService.search(List.of("ваза"))).thenReturn(List.of(doc));

        List<SearchResult> results = searchOrchestrator.search(List.of("Порцелянова ваза"), false, null);

        assertEquals(1, results.size());
        assertEquals("Порцелянова ваза", results.get(0).getTerm());
        assertFalse(results.get(0).getDefinitions().isEmpty());
    }

    @Test
    void search_withDomainAnalysis_filtersDefinitions() throws IOException {
        DictionaryEntryDoc doc = DictionaryEntryDoc.builder()
                .term("Мрія")
                .definitions(List.of("Бажання, мета", "Транспортний літак Ан-225"))
                .build();
        when(dictionarySearchService.search(List.of("Мрія"))).thenReturn(List.of(doc));
        when(domainAnalyzer.filterDefinitionsByDomain("Мрія",
                List.of("Бажання, мета", "Транспортний літак Ан-225"), "Мрія"))
                .thenReturn(List.of("Транспортний літак Ан-225"));

        List<SearchResult> results = searchOrchestrator.search(List.of("Мрія"), true, null);

        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getDefinitions().size());
    }
}
