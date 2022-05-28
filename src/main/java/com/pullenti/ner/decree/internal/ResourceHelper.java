/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

/**
 * Это для поддержки получения встроенных ресурсов
 */
public class ResourceHelper {

    /**
     * Получить встроенный ресурс
     * @param name имя, на который оканчивается ресурс
     * @return 
     */
    public static byte[] getBytes(String name) {
        
        String[] names = com.pullenti.ner.booklink.properties.Resources.getNames();
        for (String n : names) {
            if (com.pullenti.unisharp.Utils.endsWithString(n, name, true)) {
                if (name.length() < n.length()) {
                    if (n.charAt(n.length() - name.length() - 1) != '.') 
                        continue;
                }
                try {
                    Object inf = com.pullenti.ner.booklink.properties.Resources.getResourceInfo(n);
                    if (inf == null) 
                        continue;
                    try (com.pullenti.unisharp.Stream stream = com.pullenti.ner.booklink.properties.Resources.getStream(n)) {
                        byte[] buf = new byte[(int)stream.length()];
                        stream.read(buf, 0, buf.length);
                        return buf;
                    }
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }

    public static String getString(String name) {
        byte[] arr = getBytes(name);
        if (arr == null) 
            return null;
        if ((arr.length > 3 && arr[0] == ((byte)0xEF) && arr[1] == ((byte)0xBB)) && arr[2] == ((byte)0xBF)) 
            return com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), arr, 3, arr.length - 3);
        else 
            return com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), arr, 0, -1);
    }
    public ResourceHelper() {
    }
}
