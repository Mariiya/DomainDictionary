package com.domaindictionary.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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
    //private static final String SCHEME = "https";
    private static final String SCHEME = "http";

    public  RestHighLevelClient restHighLevelClient;
    /**
     * Implemented Singleton pattern here
     * so that there is just one connection at a time.
     *
     * @return RestHighLevelClient
     */
    @Bean
    public synchronized RestHighLevelClient makeConnection() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
       // credentialsProvider.setCredentials(AuthScope.ANY,
             //   new UsernamePasswordCredentials(environment.getProperty("spring.elasticsearch.rest.username"),
                //        environment.getProperty("spring.elasticsearch.rest.password")));
       // this.HOST = environment.getProperty("spring.elasticsearch.rest.uris");
        if(HOST!=null && !HOST.isEmpty()) {
            if (restHighLevelClient == null) {
                restHighLevelClient = new RestHighLevelClient(
                       RestClient.builder(
                                new HttpHost(HOST, PORT_ONE, SCHEME))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(
                                    HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder
                                        .setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }));
            }
        }
        return restHighLevelClient;
    }

    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }

}
