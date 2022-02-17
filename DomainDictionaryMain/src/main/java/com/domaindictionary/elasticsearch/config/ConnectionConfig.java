package com.domaindictionary.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Configuration
@PropertySource({"classpath:application.properties"})
public class ConnectionConfig {
    @Autowired
    Environment environment;
    //The config parameters for the connection
    private static String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final String SCHEME = "https";

    public  RestHighLevelClient restHighLevelClient;
    /**
     * Implemented Singleton pattern here
     * so that there is just one connection at a time.
     *
     * @return RestHighLevelClient
     */
    @Bean
    public synchronized RestHighLevelClient makeConnection() {
        this.HOST = environment.getProperty("spring.elasticsearch.rest.uris");
        if(HOST!=null && !HOST.isEmpty()) {
            if (restHighLevelClient == null) {
                restHighLevelClient = new RestHighLevelClient(
                        RestClient.builder(
                                new HttpHost(HOST, PORT_ONE, SCHEME)));
            }
        }
        return restHighLevelClient;
    }

    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }

}
