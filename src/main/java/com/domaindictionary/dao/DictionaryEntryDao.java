package com.domaindictionary.dao;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class DictionaryEntryDao {
    private static final Logger log = LoggerFactory.getLogger(DictionaryEntryDao.class);
    private final ElasticsearchClient client;

    public void saveOrUpdate(DictionaryEntryDoc entry) throws IOException {
        client.index(i -> i
                .index(DictionaryEntryDoc.INDEX)
                .id(entry.getId())
                .document(entry));
    }

    public void bulkInsert(List<DictionaryEntryDoc> entries) throws IOException {
        if (entries.isEmpty()) return;
        List<BulkOperation> operations = entries.stream()
                .map(entry -> BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index(DictionaryEntryDoc.INDEX)
                                .id(entry.getId())
                                .document(entry))))
                .toList();
        BulkResponse response = client.bulk(b -> b.operations(operations));
        if (response.errors()) {
            log.error("Bulk insert had errors");
        }
    }

    public List<DictionaryEntryDoc> search(List<String> terms) throws IOException {
        List<DictionaryEntryDoc> results = new ArrayList<>();
        for (String term : terms) {
            List<DictionaryEntryDoc> found = searchOneTerm(term);
            if (found.isEmpty()) {
                found = fuzzySearchOneTerm(term);
            }
            if (!found.isEmpty()) {
                // Merge definitions for same term
                DictionaryEntryDoc merged = DictionaryEntryDoc.builder()
                        .term(term)
                        .definitions(new ArrayList<>())
                        .build();
                for (DictionaryEntryDoc doc : found) {
                    if (doc.getDefinitions() != null) {
                        merged.getDefinitions().addAll(doc.getDefinitions());
                    }
                }
                results.add(merged);
            }
        }
        return results;
    }

    public List<DictionaryEntryDoc> searchOneTerm(String term) throws IOException {
        SearchResponse<DictionaryEntryDoc> response = client.search(s -> s
                .index(DictionaryEntryDoc.INDEX)
                .query(q -> q.match(m -> m.field("term").query(term))),
                DictionaryEntryDoc.class);
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<DictionaryEntryDoc> fuzzySearchOneTerm(String term) throws IOException {
        SearchResponse<DictionaryEntryDoc> response = client.search(s -> s
                .index(DictionaryEntryDoc.INDEX)
                .query(q -> q.fuzzy(f -> f
                        .field("term")
                        .value(term)
                        .fuzziness("AUTO"))),
                DictionaryEntryDoc.class);
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .toList();
    }

    public void delete(String id) throws IOException {
        client.delete(d -> d.index(DictionaryEntryDoc.INDEX).id(id));
    }

    public void deleteByResourceId(Long resourceId) throws IOException {
        client.deleteByQuery(d -> d
                .index(DictionaryEntryDoc.INDEX)
                .query(q -> q.term(t -> t.field("resourceId").value(resourceId))));
    }

    public void ensureIndexExists() throws IOException {
        boolean exists = client.indices().exists(e -> e.index(DictionaryEntryDoc.INDEX)).value();
        if (!exists) {
            client.indices().create(c -> c.index(DictionaryEntryDoc.INDEX));
        }
    }
}
