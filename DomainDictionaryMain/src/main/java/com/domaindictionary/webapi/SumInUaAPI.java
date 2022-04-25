package com.domaindictionary.webapi;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class SumInUaAPI implements InternetResourceSearchAPI {

    private InternetResource internetResource;

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
       /* Request request = new Request.Builder().method("GET", null)
                .url("http://sum.in.ua/?swrd=сніг")
                .build();
        OkHttpClient httpClient = new OkHttpClient();
        try
        {
            Response response = httpClient.newBuilder()
                    .readTimeout(1, TimeUnit.SECONDS)
                    .build()
                    .newCall(request)
                    .execute();
            if(response.isSuccessful())
            {
           //     System.out.println(response.body());
            }
            else
            {
                System.out.println("ERROR");
            }
        }
        catch (IOException e1)
        {
            // notification about other problems
        }
*/
        return new DictionaryEntry("1",term,new ArrayList<>(),internetResource.getId());
    }


    public InternetResource getInternetResource() {
        return internetResource;
    }

    public void setInternetResource(InternetResource internetResource) {
        this.internetResource = internetResource;
    }
}
