package com.domaindictionary.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.mapper.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ConnectionConfig {
    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final String SCHEME = "http";

    public  RestHighLevelClient restHighLevelClient;
    private  ObjectMapper objectMapper;

    private static final String INDEX = "persondata";
    private static final String TYPE = "person";


    /**
     * Implemented Singleton pattern here
     * so that there is just one connection at a time.
     *
     * @return RestHighLevelClient
     */
    @Bean
    public synchronized RestHighLevelClient makeConnection() {
        if (restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME)));
        }

        return restHighLevelClient;
    }

    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }

}
