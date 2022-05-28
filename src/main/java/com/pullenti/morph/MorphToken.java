/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Элементы, на которые разбивается исходный текст (токены)
 * морф.токен
 */
public class MorphToken {

    /**
     * Начальная позиция
     */
    public int beginChar;

    /**
     * Конечная позиция
     */
    public int endChar;

    /**
     * Нормализованный фрагмент исходного текста (верхний регистр, замена некотрых букв)
     */
    public String term;

    public int getLength() {
        return (term == null ? 0 : term.length());
    }


    /**
     * Извлечь фрагмент из исходного текста, соответствующий токену
     * @param text полный исходный текст
     * @return фрагмент
     */
    public String getSourceText(String text) {
        return text.substring(beginChar, beginChar + (endChar + 1) - beginChar);
    }

    /**
     * Лемма (вариант морфологической нормализации)
     */
    public String getLemma() {
        if (m_Lemma != null) 
            return m_Lemma;
        String res = null;
        if (wordForms != null && wordForms.size() > 0) {
            if (wordForms.size() == 1) 
                res = (wordForms.get(0).normalFull != null ? wordForms.get(0).normalFull : wordForms.get(0).normalCase);
            if (res == null && !charInfo.isAllLower()) {
                for (MorphWordForm m : wordForms) {
                    if (m._getClass().isProperSurname()) {
                        String s = (m.normalFull != null ? m.normalFull : m.normalCase);
                        if (LanguageHelper.endsWithEx(s, "ОВ", "ЕВ", null, null)) {
                            res = s;
                            break;
                        }
                    }
                    else if (m._getClass().isProperName() && m.isInDictionary()) 
                        return m.normalCase;
                }
            }
            if (res == null) {
                MorphWordForm best = null;
                for (MorphWordForm m : wordForms) {
                    if (best == null) 
                        best = m;
                    else if (this.compareForms(best, m) > 0) 
                        best = m;
                }
                res = (best.normalFull != null ? best.normalFull : best.normalCase);
            }
        }
        if (res != null) {
            if (LanguageHelper.endsWithEx(res, "АНЫЙ", "ЕНЫЙ", null, null)) 
                res = res.substring(0, 0 + res.length() - 3) + "ННЫЙ";
            else if (LanguageHelper.endsWith(res, "ЙСЯ")) 
                res = res.substring(0, 0 + res.length() - 2);
            else if (LanguageHelper.endsWith(res, "АНИЙ") && com.pullenti.unisharp.Utils.stringsEq(res, term)) {
                for (MorphWordForm wf : wordForms) {
                    if (wf.isInDictionary()) 
                        return res;
                }
                return res.substring(0, 0 + res.length() - 1) + "Е";
            }
            return res;
        }
        return (term != null ? term : "?");
    }

    private String m_Lemma;

    private int compareForms(MorphWordForm x, MorphWordForm y) {
        String vx = (x.normalFull != null ? x.normalFull : x.normalCase);
        String vy = (y.normalFull != null ? y.normalFull : y.normalCase);
        if (com.pullenti.unisharp.Utils.stringsEq(vx, vy)) 
            return 0;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(vx)) 
            return 1;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(vy)) 
            return -1;
        char lastx = vx.charAt(vx.length() - 1);
        char lasty = vy.charAt(vy.length() - 1);
        if (x._getClass().isProperSurname() && !charInfo.isAllLower()) {
            if (LanguageHelper.endsWithEx(vx, "ОВ", "ЕВ", "ИН", null)) {
                if (!y._getClass().isProperSurname()) 
                    return -1;
            }
        }
        if (y._getClass().isProperSurname() && !charInfo.isAllLower()) {
            if (LanguageHelper.endsWithEx(vy, "ОВ", "ЕВ", "ИН", null)) {
                if (!x._getClass().isProperSurname()) 
                    return 1;
                if (vx.length() > vy.length()) 
                    return -1;
                if (vx.length() < vy.length()) 
                    return 1;
                return 0;
            }
        }
        if (x._getClass().equals(y._getClass())) {
            if (x._getClass().isAdjective()) {
                if (lastx == 'Й' && lasty != 'Й') 
                    return -1;
                if (lastx != 'Й' && lasty == 'Й') 
                    return 1;
                if (!LanguageHelper.endsWith(vx, "ОЙ") && LanguageHelper.endsWith(vy, "ОЙ")) 
                    return -1;
                if (LanguageHelper.endsWith(vx, "ОЙ") && !LanguageHelper.endsWith(vy, "ОЙ")) 
                    return 1;
            }
            if (x._getClass().isNoun()) {
                if (x.getNumber() == MorphNumber.SINGULAR && y.getNumber() == MorphNumber.PLURAL && vx.length() <= (vy.length() + 1)) 
                    return -1;
                if (x.getNumber() == MorphNumber.PLURAL && y.getNumber() == MorphNumber.SINGULAR && vx.length() >= (vy.length() - 1)) 
                    return 1;
            }
            if (vx.length() < vy.length()) 
                return -1;
            if (vx.length() > vy.length()) 
                return 1;
            return 0;
        }
        if (x._getClass().isAdverb()) 
            return 1;
        if (x._getClass().isNoun() && x.isInDictionary()) {
            if (y._getClass().isAdjective() && y.isInDictionary()) {
                if (!y.misc.getAttrs().contains("к.ф.")) 
                    return 1;
            }
            return -1;
        }
        if (x._getClass().isAdjective()) {
            if (!x.isInDictionary() && y._getClass().isNoun() && y.isInDictionary()) 
                return 1;
            return -1;
        }
        if (x._getClass().isVerb()) {
            if (y._getClass().isNoun() || y._getClass().isAdjective() || y._getClass().isPreposition()) 
                return 1;
            return -1;
        }
        if (y._getClass().isAdverb()) 
            return -1;
        if (y._getClass().isNoun() && y.isInDictionary()) 
            return 1;
        if (y._getClass().isAdjective()) {
            if (((x._getClass().isNoun() || x._getClass().isProperSecname())) && x.isInDictionary()) 
                return -1;
            if (x._getClass().isNoun() && !y.isInDictionary()) {
                if (vx.length() < vy.length()) 
                    return -1;
            }
            return 1;
        }
        if (y._getClass().isVerb()) {
            if (x._getClass().isNoun() || x._getClass().isAdjective() || x._getClass().isPreposition()) 
                return -1;
            if (x._getClass().isProper()) 
                return -1;
            return 1;
        }
        if (vx.length() < vy.length()) 
            return -1;
        if (vx.length() > vy.length()) 
            return 1;
        return 0;
    }

    /**
     * Используется произвольным образом
     */
    public Object tag;

    public MorphLang getLanguage() {
        if (m_Language != null && !m_Language.isUndefined()) 
            return m_Language;
        MorphLang lang = new MorphLang();
        if (wordForms != null) {
            for (MorphWordForm wf : wordForms) {
                if (!wf.getLanguage().isUndefined()) 
                    lang = MorphLang.ooBitor(lang, wf.getLanguage());
            }
        }
        return lang;
    }

    public MorphLang setLanguage(MorphLang value) {
        m_Language = value;
        return value;
    }


    private MorphLang m_Language;

    /**
     * Варианты словоформ
     */
    public java.util.ArrayList<MorphWordForm> wordForms;

    /**
     * Информация о токене
     */
    public CharsInfo charInfo;

    public MorphToken() {
    }

    @Override
    public String toString() {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(term)) 
            return "Null";
        String str = term;
        if (charInfo.isAllLower()) 
            str = str.toLowerCase();
        else if (charInfo.isCapitalUpper() && str.length() > 0) 
            str = (String.valueOf(term.charAt(0)) + term.substring(1).toLowerCase());
        else if (charInfo.isLastLower()) 
            str = (term.substring(0, 0 + term.length() - 1) + term.substring(term.length() - 1).toLowerCase());
        if (wordForms == null) 
            return str;
        StringBuilder res = new StringBuilder(str);
        for (MorphWordForm l : wordForms) {
            res.append(", ").append(l.toString());
        }
        return res.toString();
    }
}
