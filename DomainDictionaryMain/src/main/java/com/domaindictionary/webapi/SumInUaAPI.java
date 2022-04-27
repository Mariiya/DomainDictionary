package com.domaindictionary.webapi;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

@Component
public class SumInUaAPI implements InternetResourceSearchAPI {

    private InternetResource internetResource;
    private static final String GET_URL = "http://sum.in.ua/?swrd=";

    public SumInUaAPI() {
        this.internetResource = new InternetResource(
                BigInteger.valueOf(System.currentTimeMillis()),
                "SumInUa",
                "http://sum.in.ua",
                ResourceType.GENERAL,
                ResourceSubtype.UKRAINIAN,
                new Rule());
    }

    @Override
    public DictionaryEntry search(String term, String language) throws Exception {
        String def = sendHttpGETRequest(term.toLowerCase());
        return new DictionaryEntry(BigInteger.valueOf(System.currentTimeMillis()).toString(),
                term, Collections.singletonList(def), BigInteger.ONE);
    }


    private String sendHttpGETRequest(String term) throws IOException {
        URL obj = new URL(GET_URL + term);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder definition = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("</abbr>")) {
                    definition.append(inputLine);
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.contains("&nbsp;")) {
                            break;
                        }
                        definition.append(inputLine);
                    }
                }
            }
            in.close();
            return Jsoup.parse(definition.toString()).text();
        } else {
            System.out.println("GET request not worked");
        }
        return "";
    }

    public InternetResource getInternetResource() {
        return internetResource;
    }

    public void setInternetResource(InternetResource internetResource) {
        this.internetResource = internetResource;
    }
}
