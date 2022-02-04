package com.domaindictionary.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RegexConstants {
    public static Collection<String> getTemplatesForRelator() {
        return relatorTemplates.keySet();
    }

    public static Collection<String> getTemplatesForArticleSeparator() {
        return articleSeparatorTemplates.keySet();
    }

    public static Map<String, String> relatorTemplates = new HashMap<>() {{
        put("1. 2. 3. 4. ....", "");
        put("1) 2) 3) 4) ....", "");
        put("I. II. III. IV. ....", "");
        put("I) II) III) IV) ....", "");
        put("i. ii. iii. iv. ....", "");
        put("a. b. c. d. ....", "");
        put("a) b) c) d) ....", "");
        put("A. B. C. D. ....", "");
        put("A) B) C) D) ....", "");
    }};

    public static Map<String, String> articleSeparatorTemplates = new HashMap<>() {{
        put("New Line", "");
        put("Empty Line", "");
    }};

}
