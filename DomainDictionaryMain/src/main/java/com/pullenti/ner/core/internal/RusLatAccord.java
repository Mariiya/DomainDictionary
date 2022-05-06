/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class RusLatAccord {

    private RusLatAccord(String ru, String la, boolean brus, boolean blat) {
        rus = ru.toUpperCase();
        lat = la.toUpperCase();
        rusToLat = brus;
        latToRus = blat;
    }

    public String rus;

    public String lat;

    public boolean rusToLat;

    public boolean latToRus;

    public boolean onTail = false;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("'").append(rus).append("'");
        if (rusToLat && latToRus) 
            tmp.append(" <-> ");
        else if (rusToLat) 
            tmp.append(" -> ");
        else if (latToRus) 
            tmp.append(" <- ");
        tmp.append("'").append(lat).append("'");
        return tmp.toString();
    }

    private static java.util.ArrayList<RusLatAccord> m_Accords;

    private static java.util.ArrayList<RusLatAccord> getAccords() {
        if (m_Accords != null) 
            return m_Accords;
        m_Accords = new java.util.ArrayList<RusLatAccord>();
        m_Accords.add(new RusLatAccord("а", "a", true, true));
        m_Accords.add(new RusLatAccord("а", "aa", true, true));
        m_Accords.add(new RusLatAccord("б", "b", true, true));
        m_Accords.add(new RusLatAccord("в", "v", true, true));
        m_Accords.add(new RusLatAccord("в", "w", true, true));
        m_Accords.add(new RusLatAccord("г", "g", true, true));
        m_Accords.add(new RusLatAccord("д", "d", true, true));
        m_Accords.add(new RusLatAccord("е", "e", true, true));
        m_Accords.add(new RusLatAccord("е", "yo", true, true));
        m_Accords.add(new RusLatAccord("е", "io", true, true));
        m_Accords.add(new RusLatAccord("е", "jo", true, true));
        m_Accords.add(new RusLatAccord("ж", "j", true, true));
        m_Accords.add(new RusLatAccord("дж", "j", true, true));
        m_Accords.add(new RusLatAccord("з", "z", true, true));
        m_Accords.add(new RusLatAccord("и", "e", true, true));
        m_Accords.add(new RusLatAccord("и", "i", true, true));
        m_Accords.add(new RusLatAccord("и", "y", true, true));
        m_Accords.add(new RusLatAccord("и", "ea", true, true));
        m_Accords.add(new RusLatAccord("й", "i", true, true));
        m_Accords.add(new RusLatAccord("й", "y", true, true));
        m_Accords.add(new RusLatAccord("к", "c", true, true));
        m_Accords.add(new RusLatAccord("к", "k", true, true));
        m_Accords.add(new RusLatAccord("к", "ck", true, true));
        m_Accords.add(new RusLatAccord("кс", "x", true, true));
        m_Accords.add(new RusLatAccord("л", "l", true, true));
        m_Accords.add(new RusLatAccord("м", "m", true, true));
        m_Accords.add(new RusLatAccord("н", "n", true, true));
        m_Accords.add(new RusLatAccord("о", "a", true, true));
        m_Accords.add(new RusLatAccord("о", "o", true, true));
        m_Accords.add(new RusLatAccord("о", "ow", true, true));
        m_Accords.add(new RusLatAccord("о", "oh", true, true));
        m_Accords.add(new RusLatAccord("п", "p", true, true));
        m_Accords.add(new RusLatAccord("р", "r", true, true));
        m_Accords.add(new RusLatAccord("с", "s", true, true));
        m_Accords.add(new RusLatAccord("с", "c", true, true));
        m_Accords.add(new RusLatAccord("т", "t", true, true));
        m_Accords.add(new RusLatAccord("у", "u", true, true));
        m_Accords.add(new RusLatAccord("у", "w", true, true));
        m_Accords.add(new RusLatAccord("ф", "f", true, true));
        m_Accords.add(new RusLatAccord("ф", "ph", true, true));
        m_Accords.add(new RusLatAccord("х", "h", true, true));
        m_Accords.add(new RusLatAccord("х", "kh", true, true));
        m_Accords.add(new RusLatAccord("ц", "ts", true, true));
        m_Accords.add(new RusLatAccord("ц", "c", true, true));
        m_Accords.add(new RusLatAccord("ч", "ch", true, true));
        m_Accords.add(new RusLatAccord("ш", "sh", true, true));
        m_Accords.add(new RusLatAccord("щ", "shch", true, true));
        m_Accords.add(new RusLatAccord("ы", "i", true, true));
        m_Accords.add(new RusLatAccord("э", "e", true, true));
        m_Accords.add(new RusLatAccord("э", "a", true, true));
        m_Accords.add(new RusLatAccord("ю", "iu", true, true));
        m_Accords.add(new RusLatAccord("ю", "ju", true, true));
        m_Accords.add(new RusLatAccord("ю", "yu", true, true));
        m_Accords.add(new RusLatAccord("ю", "ew", true, true));
        m_Accords.add(new RusLatAccord("я", "ia", true, true));
        m_Accords.add(new RusLatAccord("я", "ja", true, true));
        m_Accords.add(new RusLatAccord("я", "ya", true, true));
        m_Accords.add(new RusLatAccord("ъ", "", true, true));
        m_Accords.add(new RusLatAccord("ь", "", true, true));
        m_Accords.add(new RusLatAccord("", "gh", true, true));
        m_Accords.add(new RusLatAccord("", "h", true, true));
        m_Accords.add(_new480("", "e", true));
        m_Accords.add(new RusLatAccord("еи", "ei", true, true));
        m_Accords.add(new RusLatAccord("аи", "ai", true, true));
        m_Accords.add(new RusLatAccord("ай", "i", true, true));
        m_Accords.add(new RusLatAccord("уи", "ui", true, true));
        m_Accords.add(new RusLatAccord("уи", "w", true, true));
        m_Accords.add(new RusLatAccord("ои", "oi", true, true));
        m_Accords.add(new RusLatAccord("ей", "ei", true, true));
        m_Accords.add(new RusLatAccord("ей", "ey", true, true));
        m_Accords.add(new RusLatAccord("ай", "ai", true, true));
        m_Accords.add(new RusLatAccord("ай", "ay", true, true));
        m_Accords.add(new RusLatAccord(" ", " ", true, true));
        m_Accords.add(new RusLatAccord("-", "-", true, true));
        return m_Accords;
    }


    private static boolean _isPref(String str, int i, String pref) {
        if ((pref.length() + i) > str.length()) 
            return false;
        for (int j = 0; j < pref.length(); j++) {
            if (pref.charAt(j) != str.charAt(i + j)) 
                return false;
        }
        return true;
    }

    private static java.util.ArrayList<RusLatAccord> _getVarsPref(String _rus, int ri, String _lat, int li) {
        java.util.ArrayList<RusLatAccord> res = null;
        for (RusLatAccord a : getAccords()) {
            if (_isPref(_rus, ri, a.rus) && _isPref(_lat, li, a.lat) && a.rusToLat) {
                if (a.onTail) {
                    if ((ri + a.rus.length()) < _rus.length()) 
                        continue;
                    if ((li + a.lat.length()) < _lat.length()) 
                        continue;
                }
                if (res == null) 
                    res = new java.util.ArrayList<RusLatAccord>();
                res.add(a);
            }
        }
        return res;
    }

    public static java.util.ArrayList<String> getVariants(String rusOrLat) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(rusOrLat)) 
            return res;
        rusOrLat = rusOrLat.toUpperCase();
        boolean isRus = com.pullenti.morph.LanguageHelper.isCyrillicChar(rusOrLat.charAt(0));
        java.util.ArrayList<java.util.ArrayList<RusLatAccord>> stack = new java.util.ArrayList<java.util.ArrayList<RusLatAccord>>();
        int i;
        for (i = 0; i < rusOrLat.length(); i++) {
            java.util.ArrayList<RusLatAccord> li = new java.util.ArrayList<RusLatAccord>();
            int maxlen = 0;
            for (RusLatAccord a : getAccords()) {
                String pref = null;
                if (isRus && a.rus.length() > 0) 
                    pref = a.rus;
                else if (!isRus && a.lat.length() > 0) 
                    pref = a.lat;
                else 
                    continue;
                if (pref.length() < maxlen) 
                    continue;
                if (!_isPref(rusOrLat, i, pref)) 
                    continue;
                if (a.onTail) {
                    if ((pref.length() + i) < rusOrLat.length()) 
                        continue;
                }
                if (pref.length() > maxlen) {
                    maxlen = pref.length();
                    li.clear();
                }
                li.add(a);
            }
            if (li.size() == 0 || maxlen == 0) 
                return res;
            stack.add(li);
            i += (maxlen - 1);
        }
        if (stack.size() == 0) 
            return res;
        java.util.ArrayList<Integer> ind = new java.util.ArrayList<Integer>();
        for (i = 0; i < stack.size(); i++) {
            ind.add(0);
        }
        StringBuilder tmp = new StringBuilder();
        while (true) {
            tmp.setLength(0);
            for (i = 0; i < ind.size(); i++) {
                RusLatAccord a = stack.get(i).get(ind.get(i));
                tmp.append((isRus ? a.lat : a.rus));
            }
            boolean ok = true;
            if (!isRus) {
                for (i = 0; i < tmp.length(); i++) {
                    if (tmp.charAt(i) == 'Й') {
                        if (i == 0) {
                            ok = false;
                            break;
                        }
                        if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(tmp.charAt(i - 1))) {
                            ok = false;
                            break;
                        }
                    }
                }
            }
            if (ok) 
                res.add(tmp.toString());
            for (i = ind.size() - 1; i >= 0; i--) {
                if ((com.pullenti.unisharp.Utils.putArrayValue(ind, i, ind.get(i) + 1)) < stack.get(i).size()) 
                    break;
                else 
                    com.pullenti.unisharp.Utils.putArrayValue(ind, i, 0);
            }
            if (i < 0) 
                break;
        }
        return res;
    }

    public static boolean canBeEquals(String _rus, String _lat) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(_rus) || com.pullenti.unisharp.Utils.isNullOrEmpty(_lat)) 
            return false;
        _rus = _rus.toUpperCase();
        _lat = _lat.toUpperCase();
        java.util.ArrayList<RusLatAccord> vs = _getVarsPref(_rus, 0, _lat, 0);
        if (vs == null) 
            return false;
        java.util.ArrayList<java.util.ArrayList<RusLatAccord>> stack = new java.util.ArrayList<java.util.ArrayList<RusLatAccord>>();
        stack.add(vs);
        while (stack.size() > 0) {
            if (stack.size() == 0) 
                break;
            int ri = 0;
            int li = 0;
            for (java.util.ArrayList<RusLatAccord> s : stack) {
                ri += s.get(0).rus.length();
                li += s.get(0).lat.length();
            }
            if (ri >= _rus.length() && li >= _lat.length()) 
                return true;
            vs = _getVarsPref(_rus, ri, _lat, li);
            if (vs != null) {
                stack.add(0, vs);
                continue;
            }
            while (stack.size() > 0) {
                stack.get(0).remove(0);
                if (stack.get(0).size() > 0) 
                    break;
                stack.remove(0);
            }
        }
        return false;
    }

    public static int findAccordsRusToLat(String txt, int pos, java.util.ArrayList<String> res) {
        if (pos >= txt.length()) 
            return 0;
        char ch0 = txt.charAt(pos);
        boolean ok = false;
        if ((pos + 1) < txt.length()) {
            char ch1 = txt.charAt(pos + 1);
            for (RusLatAccord a : getAccords()) {
                if ((a.rusToLat && a.rus.length() == 2 && a.rus.charAt(0) == ch0) && a.rus.charAt(1) == ch1) {
                    res.add(a.lat);
                    ok = true;
                }
            }
            if (ok) 
                return 2;
        }
        for (RusLatAccord a : getAccords()) {
            if (a.rusToLat && a.rus.length() == 1 && a.rus.charAt(0) == ch0) {
                res.add(a.lat);
                ok = true;
            }
        }
        if (ok) 
            return 1;
        return 0;
    }

    public static int findAccordsLatToRus(String txt, int pos, java.util.ArrayList<String> res) {
        if (pos >= txt.length()) 
            return 0;
        int i;
        int j;
        int maxLen = 0;
        for (RusLatAccord a : getAccords()) {
            if (a.latToRus && a.lat.length() >= maxLen) {
                for (i = 0; i < a.lat.length(); i++) {
                    if ((pos + i) >= txt.length()) 
                        break;
                    if (txt.charAt(pos + i) != a.lat.charAt(i)) 
                        break;
                }
                if ((i < a.lat.length()) || (a.lat.length() < 1)) 
                    continue;
                if (a.lat.length() > maxLen) {
                    res.clear();
                    maxLen = a.lat.length();
                }
                res.add(a.rus);
            }
        }
        return maxLen;
    }

    public static RusLatAccord _new480(String _arg1, String _arg2, boolean _arg3) {
        RusLatAccord res = new RusLatAccord(_arg1, _arg2, true, true);
        res.onTail = _arg3;
        return res;
    }
    public RusLatAccord() {
    }
}
