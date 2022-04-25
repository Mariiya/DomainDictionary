package com.domaindictionary.webapi;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.enumeration.ResourceSubtype;
import com.domaindictionary.model.enumeration.ResourceType;
import org.springframework.stereotype.Component;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Component
public class WikipediaAPI implements InternetResourceSearchAPI {

    private InternetResource internetResource;

    public WikipediaAPI() {
        this.internetResource = new InternetResource(
                BigInteger.valueOf(System.currentTimeMillis()),
                "Wikipedia",
                "https://www.wikipedia.org/",
                ResourceType.GENERAL,
                ResourceSubtype.RUSSIAN,
                new Rule());
    }

    public DictionaryEntry search(String term, String language) throws IOException, MediaWikiApiErrorException {
        DictionaryEntry de = new DictionaryEntry();
        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();
        wbdf.getFilter().setLanguageFilter(Collections.singleton(language));
        List<WbSearchEntitiesResult> articles = wbdf.searchEntities(term, language);
        List<String> titles = new ArrayList<>();
        for (WbSearchEntitiesResult article : articles) {
            titles.add(article.getTitle());
        }
        wbdf.getFilter().setLanguageFilter(Collections.singleton(language));
        wbdf.getFilter().setPropertyFilter(
                Collections.emptySet());

        Map<String, EntityDocument> results = wbdf.getEntityDocuments(titles);
        Collection<String> definitions = new ArrayList<>();
        String id = "";
        try {
            for (Map.Entry<String, EntityDocument> entry : results.entrySet()) {
                if (entry != null) {
                    if (((ItemDocument) entry.getValue()).getDescriptions().get(language) != null) {
                        definitions.add(((ItemDocument) entry.getValue()).getDescriptions().get(language).getText());
                    } else {
                        if (entry instanceof ItemDocument) {
                            definitions.add(((ItemDocument) entry.getValue()).getDescriptions().entrySet().iterator().next().getValue().getText());
                        }
                    }
                    id = ((ItemDocument) entry.getValue()).getEntityId().getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            de.setTerm(term);
            de.setDefinition(definitions);
            de.setId(id);
            return de;
        }
    }


    public InternetResource getInternetResource() {
        return internetResource;
    }

    public void setInternetResource(InternetResource internetResource) {
        this.internetResource = internetResource;
    }
}
