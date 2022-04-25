package com.domaindictionary.elasticsearch.api;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class EntriesLoader {
    private static final Logger LOG = Logger.getLogger(EntriesLoader.class);
    private ObjectMapper mapper;
    private RestHighLevelClient restHighLevelClient;

    public EntriesLoader(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
        mapper = new ObjectMapper();
    }

    public DictionaryEntry insertDictionaryEntry(DictionaryEntry de) throws JsonProcessingException {
        System.out.println("Inserting value" + de.getTerm());
        de.setId(UUID.randomUUID().toString());
        IndexRequest indexRequest = new IndexRequest(DictionaryEntry.getIndex())
                .source(mapper.writeValueAsString(de), XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return de;
    }

    public List<DictionaryEntry> insertDictionaryEntry(List<DictionaryEntry> de) throws IOException {
        System.out.println("DE to insert "+ de.size());
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueMinutes(1));
        for (DictionaryEntry dictionaryEntry : de) {
            dictionaryEntry.setId(UUID.randomUUID().toString());
            bulkRequest.add(new IndexRequest(DictionaryEntry.getIndex())
                    .source(mapper.writeValueAsString(dictionaryEntry), XContentType.JSON));
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void onResponse(Object o) {
                System.out.println(o);
            }

            @Override
            public void onFailure(Exception e) {
                LOG.error(e.getMessage(), e);
                throw new RuntimeException("Elastic search initialisation error");
            }
        };
        try {

            System.out.println("INSERT START" + bulkRequest);
                restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
            System.out.println("INSERT END" + bulkRequest);
        } catch (ElasticsearchException e) {
            LOG.error(e.getMessage(), e);
        }
        return de;
    }
}
