package com.domaindictionary.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegexConstants {
    public static Collection<String> getTemplatesForRelator() {
        return relatorTemplates.keySet();
    }

    public static Collection<String> getTemplatesForArticleSeparator() {
        return articleSeparatorTemplates.keySet();
    }

    public static Map<String, Pattern> relatorTemplates = new HashMap<String, Pattern>() {{
        put("1. 2. 3. 4. ....", Pattern.compile("\\d{1,2}\\."));
        put("1) 2) 3) 4) ....", Pattern.compile("\\d{1,2}\\."));
        put("I. II. III. IV. ....", Pattern.compile("\\d{1,2}\\."));
        put("I) II) III) IV) ....", Pattern.compile("\\d{1,2}\\."));
        put("i. ii. iii. iv. ....", Pattern.compile("\\d{1,2}\\."));
        put("a. b. c. d. ....", Pattern.compile("\\d{1,2}\\."));
        put("a) b) c) d) ....", Pattern.compile("\\d{1,2}\\."));
        put("A. B. C. D. ....", Pattern.compile("\\d{1,2}\\."));
        put("A) B) C) D) ....", Pattern.compile("\\d{1,2}\\."));
    }};

    public static Map<String, String> articleSeparatorTemplates = new HashMap<String, String>() {{
        put("New Line", "\\n");
        put("Empty Line", "");
    }};

}
