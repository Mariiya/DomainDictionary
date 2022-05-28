package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Service
public class ElectronicDictionarySearchService implements SearchService {
    private static final Logger LOG = Logger.getLogger(ElectronicDictionarySearchService.class);
    private final DictionaryDao dictionaryDao;
    private RestHighLevelClient client;

    public ElectronicDictionarySearchService(DictionaryDao dictionaryDao, RestHighLevelClient client) {
        this.dictionaryDao = dictionaryDao;
        this.client = client;
    }

    public DictionaryEntry search(String term, Map<String, Object> params) throws IOException {
        Collection<DictionaryEntry> dictionaryEntries = elasticSearchOneTerm(term, new BigInteger((String) params.get("resourceId")));
        if (!dictionaryEntries.isEmpty()) {
            return dictionaryEntries.iterator().next();
        }
        return new DictionaryEntry(term);
    }

    public Collection<DictionaryEntry> search(Collection<String> terms, Map<String, Object> params) throws IOException {
        // Collection<DictionaryEntry> result = elasticSearchBatchTerms(terms, new BigInteger((String) params.get("resourceId")));
        Collection<DictionaryEntry> dictionaryEntries = new ArrayList<>();
        for (String t : terms) {
            dictionaryEntries.add(search(t, params));
        }
        return dictionaryEntries;
    }


    public Collection<DictionaryEntry> elasticSearchBatchTerms(Collection<String> terms, BigInteger resourceId) throws IOException {
       Collection<DictionaryEntry> result = new ArrayList<>();
      /*   MultiSearchRequest request = new MultiSearchRequest();
        for (String t : terms) {
            SearchRequest firstSearchRequest = new SearchRequest(DictionaryEntry.getIndex());
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("term", t).fuzziness(Fuzziness.AUTO).boost(1.0f)
                    .prefixLength(0).fuzzyTranspositions(true));
            firstSearchRequest.source(searchSourceBuilder);
            request.add(firstSearchRequest);
        }

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        for (MultiSearchResponse.Item item : response.getResponses()) {
            for (SearchHit hit : item.getResponse().getHits().getHits()) {
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                Collection<DictionaryEntry> fuzzyEntries = new ArrayList<>();
                fuzzyEntries.add(extractDictionaryEntry(sourceMap, hit.getIndex(), resourceId));
                DictionaryEntry extractedDE = collapseIntoOne(terms., fuzzyEntries);

                for (String t : terms) {
                    if (extractedDE.getTerm().toLowerCase().contains(t.toLowerCase())) {
                        extractedDE.setTerm(t);
                    }
                }
                result.add(extractedDE);
                break;
            }
        }
        removeDuplicates(result);*/
        return result;
    }

    public Collection<DictionaryEntry> elasticSearchOneTerm(String term, BigInteger resourceId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(DictionaryEntry.getIndex());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("term", term).fuzziness(Fuzziness.ONE).boost(1.0f)
                        .prefixLength(0).fuzzyTranspositions(true))
                .should(QueryBuilders.matchQuery("resourceId", resourceId));

        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Collection<DictionaryEntry> res = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            DictionaryEntry de = extractDictionaryEntry(hit.getSourceAsMap(), term, resourceId);
            de.setTerm(term);
            res.add(de);
        }

        removeDuplicates(res);
        return res;
    }

    private void removeDuplicates(Collection<DictionaryEntry> dictionaryEntries) {
        List<DictionaryEntry> toRemove = new ArrayList<>();
        for (DictionaryEntry de : dictionaryEntries) {
            for (DictionaryEntry de2 : dictionaryEntries) {
                if (de.getTerm().equals(de2.getTerm()) && !de.getId().equals(de2.getId())
                        && !toRemove.contains(de2) && !toRemove.contains(de)) {
                    de.getDefinition().addAll(de2.getDefinition());
                    toRemove.add(de2);
                }
            }
        }
        dictionaryEntries.removeAll(toRemove);
    }

    private DictionaryEntry extractDictionaryEntry(Map<String, Object> sourceMap, String term, BigInteger resourceId) {
        DictionaryEntry result = new DictionaryEntry(null, term, Collections.EMPTY_LIST, resourceId);
        if (sourceMap.containsKey("id")) {
            String id = (String) sourceMap.get("id");
            if (sourceMap.containsKey("term")) {
                String termFound = (String) sourceMap.get("term");
                if (sourceMap.containsKey("definition")) {
                    Collection definitionFound = (Collection) sourceMap.get("definition");
                    BigInteger resourceIdFound = resourceId;
                    if (sourceMap.containsKey("resourceId") && sourceMap.get("resourceId") != null) {
                        resourceIdFound = new BigInteger(sourceMap.get("resourceId").toString());
                    }
                    if (!termFound.isEmpty() && !definitionFound.isEmpty() && !id.isEmpty()) {
                        DictionaryEntry de = new DictionaryEntry(id, termFound, definitionFound, resourceIdFound);
                        return de;
                    }
                }
            }
        }
        return result;
    }

    protected List<DictionaryEntry> searchWithOutResource(Collection<String> terms) {
        return Collections.EMPTY_LIST;
    }
}
