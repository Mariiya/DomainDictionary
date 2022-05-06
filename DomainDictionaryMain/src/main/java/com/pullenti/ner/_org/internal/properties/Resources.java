/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal.properties;

public class Resources {
    static String[] m_Names = new String[] { "festival.png", "train.png", "federation.png", "avia.png", "church.png", "holding.png", "hotel.png", "justice.png", "music.png", "politics.png", "science.png", "sport.png", "study.png", "dep.png", "medicine.png", "gov.png", "factory.png", "culture.png", "bank.png", "press.png", "org.png", "media.png", "military.png", "trade.png", "party.png", "NameNouns_ru.dat", "NameNouns_ua.dat", "Orgs_en.dat", "Orgs_ru.dat", "Orgs_ua.dat", "OrgTypes.dat", "pattrs_en.dat", "pattrs_ru.dat", "pattrs_ua.dat" }; 
    public static String[] getNames() { return m_Names; } 
    public static com.pullenti.unisharp.Stream getStream(String name) throws java.io.IOException {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) throw new Exception("Can't find resource file " + m_Names[i]);
                        byte[] dat = com.pullenti.unisharp.Utils.readAllBytes(istr);
                        istr.close();
                        return new com.pullenti.unisharp.MemoryStream(dat);
                    }
                }
            }
        } catch (Exception ee) {
               //System.out.println(ee);
        }
        return null;
    }
    public static Object getResourceInfo(String name) {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) return null;
                        istr.close();
                        return m_Names[i];
                    }
                }
            }
        } catch (Exception ee) {
        }
        return null;
    }
}
