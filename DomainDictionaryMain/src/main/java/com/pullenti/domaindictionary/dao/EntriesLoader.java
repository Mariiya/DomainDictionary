package com.pullenti.domaindictionary.dao;

import com.pullenti.domaindictionary.model.DictionaryEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service("elasticLoader")
public class EntriesLoader {
    private static final Logger LOG = Logger.getLogger(EntriesLoader.class);
    private ObjectMapper mapper;
    private RestHighLevelClient restHighLevelClient;

    public EntriesLoader(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
        mapper = new ObjectMapper();
    }

    public List<DictionaryEntry> insertDictionaryEntry(List<DictionaryEntry> de) throws IOException {
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
                if(e instanceof ActionRequestValidationException){
                    LOG.error(e.getMessage(), e);
                }else {
                    throw new RuntimeException("Elastic search initialisation error");
                }
            }
        };
        try {
            try {
                bulkRequest.validate();
            } catch (ActionRequestValidationException e) {
                LOG.error(e.getMessage(), e);
                return null;
            }
            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
        } catch (ElasticsearchException e) {
            LOG.error(e.getMessage(), e);
        }
        return de;
    }
}
