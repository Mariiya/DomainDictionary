package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class ExternalResourceSearchService {

    public List<DictionaryEntry> search(List<String> terms) {
        List<DictionaryEntry> result = new ArrayList<>();

        for (String term : terms) {
            String definition = getResponse(
                    "Hi, give me just a definition of a term in this term language: " + term);

            DictionaryEntry e = new DictionaryEntry();
            e.setDefinition(Collections.singletonList(definition));
            e.setTerm(term);
            result.add(e);
        }

        return result;
    }

    public List<String> getLinksToExternalResources(List<String> terms) {
        String result = getResponse(
                "Hi, give me just a list of sites with dictionaries. " +
                        "Separate them with #");
        return Arrays.asList(result.split("#"));
    }

    public static String getResponse(String request) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "YOUR API KEY HERE";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + term + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

}
