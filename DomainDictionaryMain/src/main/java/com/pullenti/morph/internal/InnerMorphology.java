/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class InnerMorphology {

    public InnerMorphology() {
    }

    private MorphEngine m_EngineRu = new MorphEngine();

    private MorphEngine m_EngineEn = new MorphEngine();

    private MorphEngine m_EngineUa = new MorphEngine();

    private MorphEngine m_EngineBy = new MorphEngine();

    private MorphEngine m_EngineKz = new MorphEngine();

    private Object m_Lock = new Object();

    public com.pullenti.morph.MorphLang getLoadedLanguages() {
        return com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.ooBitor(m_EngineRu.language, com.pullenti.morph.MorphLang.ooBitor(m_EngineEn.language, m_EngineUa.language)), com.pullenti.morph.MorphLang.ooBitor(m_EngineBy.language, m_EngineKz.language));
    }


    public void loadLanguages(com.pullenti.morph.MorphLang langs, boolean lazyLoad) throws Exception, java.io.IOException {
        if (langs.isRu() && !m_EngineRu.language.isRu()) {
            synchronized (m_Lock) {
                if (!m_EngineRu.language.isRu()) {
                    if (!m_EngineRu.initialize(com.pullenti.morph.MorphLang.RU, lazyLoad)) 
                        throw new Exception("Not found resource file m_ru.dat in Morphology");
                }
            }
        }
        if (langs.isEn() && !m_EngineEn.language.isEn()) {
            synchronized (m_Lock) {
                if (!m_EngineEn.language.isEn()) {
                    if (!m_EngineEn.initialize(com.pullenti.morph.MorphLang.EN, lazyLoad)) 
                        throw new Exception("Not found resource file m_en.dat in Morphology");
                }
            }
        }
        if (langs.isUa() && !m_EngineUa.language.isUa()) {
            synchronized (m_Lock) {
                if (!m_EngineUa.language.isUa()) 
                    m_EngineUa.initialize(com.pullenti.morph.MorphLang.UA, lazyLoad);
            }
        }
        if (langs.isBy() && !m_EngineBy.language.isBy()) {
            synchronized (m_Lock) {
                if (!m_EngineBy.language.isBy()) 
                    m_EngineBy.initialize(com.pullenti.morph.MorphLang.BY, lazyLoad);
            }
        }
        if (langs.isKz() && !m_EngineKz.language.isKz()) {
            synchronized (m_Lock) {
                if (!m_EngineKz.language.isKz()) 
                    m_EngineKz.initialize(com.pullenti.morph.MorphLang.KZ, lazyLoad);
            }
        }
    }

    public void unloadLanguages(com.pullenti.morph.MorphLang langs) {
        if (langs.isRu() && m_EngineRu.language.isRu()) 
            m_EngineRu = new MorphEngine();
        if (langs.isEn() && m_EngineEn.language.isEn()) 
            m_EngineEn = new MorphEngine();
        if (langs.isUa() && m_EngineUa.language.isUa()) 
            m_EngineUa = new MorphEngine();
        if (langs.isBy() && m_EngineBy.language.isBy()) 
            m_EngineBy = new MorphEngine();
        if (langs.isKz() && m_EngineKz.language.isKz()) 
            m_EngineKz = new MorphEngine();
        System.gc();
    }

    public void setEngines(MorphEngine engine) {
        if (engine != null) {
            m_EngineRu = engine;
            m_EngineEn = engine;
            m_EngineUa = engine;
            m_EngineBy = engine;
        }
    }

    private void onProgress(int val, int max, com.pullenti.unisharp.ProgressEventHandler progress) {
        int p = val;
        if (max > 0xFFFF) 
            p = p / ((max / 100));
        else 
            p = (p * 100) / max;
        if (p != lastPercent && progress != null) 
            progress.call(null, new com.pullenti.unisharp.ProgressEventArgs(p, null));
        lastPercent = p;
    }

    private int lastPercent;

    public java.util.ArrayList<com.pullenti.morph.MorphToken> run(String text, boolean onlyTokenizing, com.pullenti.morph.MorphLang dlang, boolean goodText, com.pullenti.unisharp.ProgressEventHandler progress) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        TextWrapper twr = new TextWrapper(text, goodText);
        TextWrapper.CharsList twrch = twr.chars;
        java.util.ArrayList<com.pullenti.morph.MorphToken> res = new java.util.ArrayList<com.pullenti.morph.MorphToken>(text.length() / 6);
        java.util.HashMap<String, UniLexWrap> uniLex = new java.util.HashMap<String, UniLexWrap>();
        int i;
        int j;
        String term0 = null;
        int pureRusWords = 0;
        int pureUkrWords = 0;
        int pureByWords = 0;
        int pureKzWords = 0;
        int totRusWords = 0;
        int totUkrWords = 0;
        int totByWords = 0;
        int totKzWords = 0;
        for (i = 0; i < twr.length; i++) {
            int ty = this.getCharTyp(twrch.getIndexerItem(i));
            if (ty == 0) 
                continue;
            if (ty > 2) 
                j = i + 1;
            else 
                for (j = i + 1; j < twr.length; j++) {
                    if (this.getCharTyp(twrch.getIndexerItem(j)) != ty) 
                        break;
                }
            String wstr = text.substring(i, i + j - i);
            String term = null;
            if (goodText) 
                term = wstr;
            else {
                String trstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(wstr, term0, false);
                term = com.pullenti.morph.LanguageHelper.correctWord(trstr);
            }
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(term)) {
                i = j - 1;
                continue;
            }
            if (term.startsWith("ЗАВ")) {
                String term1 = term.substring(3);
                if (term1.startsWith("ОТДЕЛ") || term1.startsWith("ЛАБОРАТ") || term1.startsWith("КАФЕДР")) {
                    term = "ЗАВ";
                    j = i + 3;
                }
            }
            com.pullenti.morph.MorphLang lang = com.pullenti.morph.LanguageHelper.getWordLang(term);
            if (term.length() > 2) {
                if (lang.equals(com.pullenti.morph.MorphLang.UA)) 
                    pureUkrWords++;
                else if (lang.equals(com.pullenti.morph.MorphLang.RU)) 
                    pureRusWords++;
                else if (lang.equals(com.pullenti.morph.MorphLang.BY)) 
                    pureByWords++;
                else if (lang.equals(com.pullenti.morph.MorphLang.KZ)) 
                    pureKzWords++;
            }
            if (lang.isRu()) 
                totRusWords++;
            if (lang.isUa()) 
                totUkrWords++;
            if (lang.isBy()) 
                totByWords++;
            if (lang.isKz()) 
                totKzWords++;
            if (ty == 1) 
                term0 = term;
            UniLexWrap lemmas = null;
            if (ty == 1 && !onlyTokenizing) {
                com.pullenti.unisharp.Outargwrapper<UniLexWrap> wraplemmas1 = new com.pullenti.unisharp.Outargwrapper<UniLexWrap>();
                boolean inoutres2 = com.pullenti.unisharp.Utils.tryGetValue(uniLex, term, wraplemmas1);
                lemmas = wraplemmas1.value;
                if (!inoutres2) {
                    UniLexWrap nuni = new UniLexWrap(lang);
                    uniLex.put(term, nuni);
                    lemmas = nuni;
                }
            }
            com.pullenti.morph.MorphToken tok = new com.pullenti.morph.MorphToken();
            tok.term = term;
            tok.beginChar = i;
            if (i == 733860) {
            }
            tok.endChar = j - 1;
            tok.tag = lemmas;
            res.add(tok);
            i = j - 1;
        }
        com.pullenti.morph.MorphLang defLang = new com.pullenti.morph.MorphLang();
        if (dlang != null) 
            defLang.value = dlang.value;
        if (pureRusWords > pureUkrWords && pureRusWords > pureByWords && pureRusWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.RU;
        else if (totRusWords > totUkrWords && ((totRusWords > totByWords || ((totRusWords == totByWords && pureByWords == 0)))) && totRusWords > totKzWords) 
            defLang = com.pullenti.morph.MorphLang.RU;
        else if (pureUkrWords > pureRusWords && pureUkrWords > pureByWords && pureUkrWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.UA;
        else if (totUkrWords > totRusWords && totUkrWords > totByWords && totUkrWords > totKzWords) 
            defLang = com.pullenti.morph.MorphLang.UA;
        else if ((pureKzWords > pureRusWords && ((totKzWords + pureKzWords) > totRusWords) && pureKzWords > pureUkrWords) && pureKzWords > pureByWords) 
            defLang = com.pullenti.morph.MorphLang.KZ;
        else if (totKzWords > totRusWords && totKzWords > totUkrWords && totKzWords > totByWords) 
            defLang = com.pullenti.morph.MorphLang.KZ;
        else if (pureByWords > pureRusWords && pureByWords > pureUkrWords && pureByWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.BY;
        else if (totByWords > totRusWords && totByWords > totUkrWords && totByWords > totKzWords) {
            if (totRusWords > 10 && totByWords > (totRusWords + 20)) 
                defLang = com.pullenti.morph.MorphLang.BY;
            else if (totRusWords == 0 || totByWords >= (totRusWords * 2)) 
                defLang = com.pullenti.morph.MorphLang.BY;
        }
        if (((defLang.isUndefined() || defLang.isUa())) && totRusWords > 0) {
            if (((totUkrWords > totRusWords && m_EngineUa.language.isUa())) || ((totByWords > totRusWords && m_EngineBy.language.isBy())) || ((totKzWords > totRusWords && m_EngineKz.language.isKz()))) {
                int cou0 = 0;
                totRusWords = (totByWords = (totUkrWords = (totKzWords = 0)));
                for (java.util.Map.Entry<String, UniLexWrap> kp : uniLex.entrySet()) {
                    com.pullenti.morph.MorphLang lang = new com.pullenti.morph.MorphLang();
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wraplang3 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(lang);
                    kp.getValue().wordForms = this.processOneWord(kp.getKey(), wraplang3);
                    lang = wraplang3.value;
                    if (kp.getValue().wordForms != null) {
                        for (com.pullenti.morph.MorphWordForm wf : kp.getValue().wordForms) {
                            lang = com.pullenti.morph.MorphLang.ooBitor(lang, wf.getLanguage());
                        }
                    }
                    kp.getValue().lang = lang;
                    if (lang.isRu()) 
                        totRusWords++;
                    if (lang.isUa()) 
                        totUkrWords++;
                    if (lang.isBy()) 
                        totByWords++;
                    if (lang.isKz()) 
                        totKzWords++;
                    if (lang.isCyrillic()) 
                        cou0++;
                    if (cou0 >= 100) 
                        break;
                }
                if (totRusWords > ((totByWords / 2)) && totRusWords > ((totUkrWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.RU;
                else if (totUkrWords > ((totRusWords / 2)) && totUkrWords > ((totByWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.UA;
                else if (totByWords > ((totRusWords / 2)) && totByWords > ((totUkrWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.BY;
            }
            else if (defLang.isUndefined()) 
                defLang = com.pullenti.morph.MorphLang.RU;
        }
        int cou = 0;
        totRusWords = (totByWords = (totUkrWords = (totKzWords = 0)));
        for (java.util.Map.Entry<String, UniLexWrap> kp : uniLex.entrySet()) {
            com.pullenti.morph.MorphLang lang = defLang;
            if (lang.isUndefined()) {
                if (totRusWords > totByWords && totRusWords > totUkrWords && totRusWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.RU;
                else if (totUkrWords > totRusWords && totUkrWords > totByWords && totUkrWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.UA;
                else if (totByWords > totRusWords && totByWords > totUkrWords && totByWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.BY;
                else if (totKzWords > totRusWords && totKzWords > totUkrWords && totKzWords > totByWords) 
                    lang = com.pullenti.morph.MorphLang.KZ;
            }
            com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wraplang4 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(lang);
            kp.getValue().wordForms = this.processOneWord(kp.getKey(), wraplang4);
            lang = wraplang4.value;
            kp.getValue().lang = lang;
            if (lang.isRu()) 
                totRusWords++;
            if (lang.isUa()) 
                totUkrWords++;
            if (lang.isBy()) 
                totByWords++;
            if (lang.isKz()) 
                totKzWords++;
            if (progress != null) 
                this.onProgress(cou, uniLex.size(), progress);
            cou++;
        }
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> emptyList = null;
        for (com.pullenti.morph.MorphToken r : res) {
            UniLexWrap uni = (UniLexWrap)com.pullenti.unisharp.Utils.cast(r.tag, UniLexWrap.class);
            r.tag = null;
            if (uni == null || uni.wordForms == null || uni.wordForms.size() == 0) {
                if (emptyList == null) 
                    emptyList = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                r.wordForms = emptyList;
                if (uni != null) 
                    r.setLanguage(uni.lang);
            }
            else 
                r.wordForms = uni.wordForms;
        }
        if (!goodText) {
            for (i = 0; i < (res.size() - 2); i++) {
                UnicodeInfo ui0 = twrch.getIndexerItem(res.get(i).beginChar);
                UnicodeInfo ui1 = twrch.getIndexerItem(res.get(i + 1).beginChar);
                UnicodeInfo ui2 = twrch.getIndexerItem(res.get(i + 2).beginChar);
                if (ui1.isQuot()) {
                    int p = res.get(i + 1).beginChar;
                    if ((p >= 2 && "БбТт".indexOf(text.charAt(p - 1)) >= 0 && ((p + 3) < text.length())) && "ЕеЯяЁё".indexOf(text.charAt(p + 1)) >= 0) {
                        String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord((res.get(i).getSourceText(text) + "Ъ" + res.get(i + 2).getSourceText(text))), null, false);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                            res.get(i).endChar = res.get(i + 2).endChar;
                            res.get(i).term = wstr;
                            res.get(i).wordForms = li;
                            for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                    else if ((ui1.isApos() && p > 0 && Character.isLetter(text.charAt(p - 1))) && ((p + 1) < text.length()) && Character.isLetter(text.charAt(p + 1))) {
                        if (defLang.equals(com.pullenti.morph.MorphLang.UA) || res.get(i).getLanguage().isUa() || res.get(i + 2).getLanguage().isUa()) {
                            String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord((res.get(i).getSourceText(text) + res.get(i + 2).getSourceText(text))), null, false);
                            java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                            boolean okk = true;
                            if (okk) {
                                res.get(i).endChar = res.get(i + 2).endChar;
                                res.get(i).term = wstr;
                                if (li == null) 
                                    li = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                                if (li != null && li.size() > 0) 
                                    res.get(i).setLanguage(li.get(0).getLanguage());
                                res.get(i).wordForms = li;
                                for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                            }
                        }
                    }
                }
                else if (((ui1.uniChar == '3' || ui1.uniChar == '4')) && res.get(i + 1).getLength() == 1) {
                    String src = (ui1.uniChar == '3' ? "З" : "Ч");
                    int i0 = i + 1;
                    if ((res.get(i).endChar + 1) == res.get(i + 1).beginChar && ui0.isCyrillic()) {
                        i0--;
                        src = res.get(i0).getSourceText(text) + src;
                    }
                    int i1 = i + 1;
                    if ((res.get(i + 1).endChar + 1) == res.get(i + 2).beginChar && ui2.isCyrillic()) {
                        i1++;
                        src += res.get(i1).getSourceText(text);
                    }
                    if (src.length() > 2) {
                        String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord(src), null, false);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                            res.get(i0).endChar = res.get(i1).endChar;
                            res.get(i0).term = wstr;
                            res.get(i0).wordForms = li;
                            for(int jjj = i0 + 1 + i1 - i0 - 1, mmm = i0 + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                }
                else if ((ui1.isHiphen() && ui0.isLetter() && ui2.isLetter()) && res.get(i).endChar > res.get(i).beginChar && res.get(i + 2).endChar > res.get(i + 2).beginChar) {
                    boolean newline = false;
                    int sps = 0;
                    for (j = res.get(i + 1).endChar + 1; j < res.get(i + 2).beginChar; j++) {
                        if (text.charAt(j) == '\r' || text.charAt(j) == '\n') {
                            newline = true;
                            sps++;
                        }
                        else if (!com.pullenti.unisharp.Utils.isWhitespace(text.charAt(j))) 
                            break;
                        else 
                            sps++;
                    }
                    String fullWord = com.pullenti.morph.LanguageHelper.correctWord(res.get(i).getSourceText(text) + res.get(i + 2).getSourceText(text));
                    if (!newline) {
                        if (uniLex.containsKey(fullWord) || com.pullenti.unisharp.Utils.stringsEq(fullWord, "ИЗЗА")) 
                            newline = true;
                        else if (text.charAt(res.get(i + 1).beginChar) == ((char)0x00AD)) 
                            newline = true;
                        else if (com.pullenti.morph.LanguageHelper.endsWithEx(res.get(i).getSourceText(text), "О", "о", null, null) && res.get(i + 2).wordForms.size() > 0 && res.get(i + 2).wordForms.get(0).isInDictionary()) {
                            if (text.charAt(res.get(i + 1).beginChar) == '¬') {
                                java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                                if (li != null && li.size() > 0 && li.get(0).isInDictionary()) 
                                    newline = true;
                            }
                        }
                        else if ((res.get(i).endChar + 2) == res.get(i + 2).beginChar) {
                            if (!Character.isUpperCase(text.charAt(res.get(i + 2).beginChar)) && (sps < 2) && fullWord.length() > 4) {
                                newline = true;
                                if ((i + 3) < res.size()) {
                                    UnicodeInfo ui3 = twrch.getIndexerItem(res.get(i + 3).beginChar);
                                    if (ui3.isHiphen()) 
                                        newline = false;
                                }
                            }
                        }
                        else if (((res.get(i).endChar + 1) == res.get(i + 1).beginChar && sps > 0 && (sps < 3)) && fullWord.length() > 4) 
                            newline = true;
                    }
                    if (newline) {
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                        if (li != null && li.size() > 0 && ((li.get(0).isInDictionary() || uniLex.containsKey(fullWord)))) {
                            res.get(i).endChar = res.get(i + 2).endChar;
                            res.get(i).term = fullWord;
                            res.get(i).wordForms = li;
                            for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                    else {
                    }
                }
                else if ((ui1.isLetter() && ui0.isLetter() && res.get(i).getLength() > 2) && res.get(i + 1).getLength() > 1) {
                    if (ui0.isUpper() != ui1.isUpper()) 
                        continue;
                    if (!ui0.isCyrillic() || !ui1.isCyrillic()) 
                        continue;
                    boolean newline = false;
                    for (j = res.get(i).endChar + 1; j < res.get(i + 1).beginChar; j++) {
                        if (twrch.getIndexerItem(j).code == 0xD || twrch.getIndexerItem(j).code == 0xA) {
                            newline = true;
                            break;
                        }
                    }
                    if (!newline) 
                        continue;
                    String fullWord = com.pullenti.morph.LanguageHelper.correctWord(res.get(i).getSourceText(text) + res.get(i + 1).getSourceText(text));
                    if (!uniLex.containsKey(fullWord)) 
                        continue;
                    java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                    if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                        res.get(i).endChar = res.get(i + 1).endChar;
                        res.get(i).term = fullWord;
                        res.get(i).wordForms = li;
                        res.remove(i + 1);
                    }
                }
            }
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphToken mt = res.get(i);
            mt.charInfo = new com.pullenti.morph.CharsInfo();
            UnicodeInfo ui0 = twrch.getIndexerItem(mt.beginChar);
            UnicodeInfo ui00 = UnicodeInfo.ALLCHARS.get((int)(mt.term.charAt(0)));
            for (j = mt.beginChar + 1; j <= mt.endChar; j++) {
                if (ui0.isLetter()) 
                    break;
                ui0 = twrch.getIndexerItem(j);
            }
            if (ui0.isLetter()) {
                mt.charInfo.setLetter(true);
                if (ui00.isLatin()) 
                    mt.charInfo.setLatinLetter(true);
                else if (ui00.isCyrillic()) 
                    mt.charInfo.setCyrillicLetter(true);
                if (mt.getLanguage().isUndefined()) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillic(mt.term)) 
                        mt.setLanguage((defLang.isUndefined() ? com.pullenti.morph.MorphLang.RU : defLang));
                }
                if (goodText) 
                    continue;
                boolean allUp = true;
                boolean allLo = true;
                for (j = mt.beginChar; j <= mt.endChar; j++) {
                    if (twrch.getIndexerItem(j).isUpper() || twrch.getIndexerItem(j).isDigit()) 
                        allLo = false;
                    else 
                        allUp = false;
                }
                if (allUp) 
                    mt.charInfo.setAllUpper(true);
                else if (allLo) 
                    mt.charInfo.setAllLower(true);
                else if (((ui0.isUpper() || twrch.getIndexerItem(mt.beginChar).isDigit())) && mt.endChar > mt.beginChar) {
                    allLo = true;
                    for (j = mt.beginChar + 1; j <= mt.endChar; j++) {
                        if (twrch.getIndexerItem(j).isUpper() || twrch.getIndexerItem(j).isDigit()) {
                            allLo = false;
                            break;
                        }
                    }
                    if (allLo) 
                        mt.charInfo.setCapitalUpper(true);
                    else if (twrch.getIndexerItem(mt.endChar).isLower() && (mt.endChar - mt.beginChar) > 1) {
                        allUp = true;
                        for (j = mt.beginChar; j < mt.endChar; j++) {
                            if (twrch.getIndexerItem(j).isLower()) {
                                allUp = false;
                                break;
                            }
                        }
                        if (allUp) 
                            mt.charInfo.setLastLower(true);
                    }
                }
            }
            if (mt.charInfo.isLastLower() && mt.getLength() > 2 && mt.charInfo.isCyrillicLetter()) {
                String pref = text.substring(mt.beginChar, mt.beginChar + mt.endChar - mt.beginChar);
                boolean ok = false;
                for (com.pullenti.morph.MorphWordForm wf : mt.wordForms) {
                    if (com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, pref) || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, pref)) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    com.pullenti.morph.MorphWordForm wf0 = com.pullenti.morph.MorphWordForm._new5(pref, com.pullenti.morph.MorphClass.NOUN, (short)1);
                    mt.wordForms = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>(mt.wordForms);
                    mt.wordForms.add(0, wf0);
                }
            }
        }
        if (goodText || onlyTokenizing) 
            return res;
        for (i = 0; i < res.size(); i++) {
            if (res.get(i).getLength() == 1 && res.get(i).charInfo.isLatinLetter()) {
                char ch = res.get(i).term.charAt(0);
                if (ch == 'C' || ch == 'A' || ch == 'P') {
                }
                else 
                    continue;
                boolean isRus = false;
                for (int ii = i - 1; ii >= 0; ii--) {
                    if ((res.get(ii).endChar + 1) != res.get(ii + 1).beginChar) 
                        break;
                    else if (res.get(ii).charInfo.isLetter()) {
                        isRus = res.get(ii).charInfo.isCyrillicLetter();
                        break;
                    }
                }
                if (!isRus) {
                    for (int ii = i + 1; ii < res.size(); ii++) {
                        if ((res.get(ii - 1).endChar + 1) != res.get(ii).beginChar) 
                            break;
                        else if (res.get(ii).charInfo.isLetter()) {
                            isRus = res.get(ii).charInfo.isCyrillicLetter();
                            break;
                        }
                    }
                }
                if (isRus) {
                    res.get(i).term = com.pullenti.morph.LanguageHelper.transliteralCorrection(res.get(i).term, null, true);
                    res.get(i).charInfo.setCyrillicLetter(true);
                    res.get(i).charInfo.setLatinLetter(true);
                }
            }
        }
        for (com.pullenti.morph.MorphToken r : res) {
            if (r.charInfo.isAllUpper() || r.charInfo.isCapitalUpper()) {
                if (r.getLanguage().isCyrillic()) {
                    boolean ok = false;
                    for (com.pullenti.morph.MorphWordForm wf : r.wordForms) {
                        if (wf._getClass().isProperSurname()) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        r.wordForms = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>(r.wordForms);
                        m_EngineRu.processSurnameVariants(r.term, r.wordForms);
                    }
                }
            }
        }
        for (com.pullenti.morph.MorphToken r : res) {
            for (com.pullenti.morph.MorphWordForm mv : r.wordForms) {
                if (mv.normalCase == null) 
                    mv.normalCase = r.term;
            }
        }
        for (i = 0; i < (res.size() - 2); i++) {
            if (res.get(i).charInfo.isLatinLetter() && res.get(i).charInfo.isAllUpper() && res.get(i).getLength() == 1) {
                if (twrch.getIndexerItem(res.get(i + 1).beginChar).isQuot() && res.get(i + 2).charInfo.isLatinLetter() && res.get(i + 2).getLength() > 2) {
                    if ((res.get(i).endChar + 1) == res.get(i + 1).beginChar && (res.get(i + 1).endChar + 1) == res.get(i + 2).beginChar) {
                        String wstr = (res.get(i).term + res.get(i + 2).term);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null) 
                            res.get(i).wordForms = li;
                        res.get(i).endChar = res.get(i + 2).endChar;
                        res.get(i).term = wstr;
                        if (res.get(i + 2).charInfo.isAllLower()) {
                            res.get(i).charInfo.setAllUpper(false);
                            res.get(i).charInfo.setCapitalUpper(true);
                        }
                        else if (!res.get(i + 2).charInfo.isAllUpper()) 
                            res.get(i).charInfo.setAllUpper(false);
                        for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                    }
                }
            }
        }
        for (i = 0; i < (res.size() - 1); i++) {
            if (!res.get(i).charInfo.isLetter() && !res.get(i + 1).charInfo.isLetter() && (res.get(i).endChar + 1) == res.get(i + 1).beginChar) {
                if (twrch.getIndexerItem(res.get(i).beginChar).isHiphen() && twrch.getIndexerItem(res.get(i + 1).beginChar).isHiphen()) {
                    if (i == 0 || !twrch.getIndexerItem(res.get(i - 1).beginChar).isHiphen()) {
                    }
                    else 
                        continue;
                    if ((i + 2) == res.size() || !twrch.getIndexerItem(res.get(i + 2).beginChar).isHiphen()) {
                    }
                    else 
                        continue;
                    res.get(i).endChar = res.get(i + 1).endChar;
                    res.remove(i + 1);
                }
            }
        }
        return res;
    }

    public int getCharTyp(UnicodeInfo ui) {
        if (ui.isLetter()) 
            return 1;
        if (ui.isDigit()) 
            return 2;
        if (ui.isWhitespace()) 
            return 0;
        if (ui.isUdaren()) 
            return 1;
        return ui.code;
    }

    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> getAllWordforms(String word, com.pullenti.morph.MorphLang lang) {
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (lang != null) {
                if (m_EngineRu.language.isRu() && lang.isRu()) 
                    return m_EngineRu.getAllWordforms(word);
                if (m_EngineUa.language.isUa() && lang.isUa()) 
                    return m_EngineUa.getAllWordforms(word);
                if (m_EngineBy.language.isBy() && lang.isBy()) 
                    return m_EngineBy.getAllWordforms(word);
                if (m_EngineKz.language.isKz() && lang.isKz()) 
                    return m_EngineKz.getAllWordforms(word);
            }
            return m_EngineRu.getAllWordforms(word);
        }
        else 
            return m_EngineEn.getAllWordforms(word);
    }

    public String getWordform(String word, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphLang lang, com.pullenti.morph.MorphWordForm addInfo) {
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (m_EngineRu.language.isRu() && lang.isRu()) 
                return m_EngineRu.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineUa.language.isUa() && lang.isUa()) 
                return m_EngineUa.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineBy.language.isBy() && lang.isBy()) 
                return m_EngineBy.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineKz.language.isKz() && lang.isKz()) 
                return m_EngineKz.getWordform(word, cla, gender, cas, num, addInfo);
            return m_EngineRu.getWordform(word, cla, gender, cas, num, addInfo);
        }
        else 
            return m_EngineEn.getWordform(word, cla, gender, cas, num, addInfo);
    }

    public java.util.ArrayList<String> correctWordByMorph(String word, com.pullenti.morph.MorphLang lang, boolean oneVar) {
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (lang != null) {
                if (m_EngineRu.language.isRu() && lang.isRu()) 
                    return m_EngineRu.correctWordByMorph(word, oneVar);
                if (m_EngineUa.language.isUa() && lang.isUa()) 
                    return m_EngineUa.correctWordByMorph(word, oneVar);
                if (m_EngineBy.language.isBy() && lang.isBy()) 
                    return m_EngineBy.correctWordByMorph(word, oneVar);
                if (m_EngineKz.language.isKz() && lang.isKz()) 
                    return m_EngineKz.correctWordByMorph(word, oneVar);
            }
            return m_EngineRu.correctWordByMorph(word, oneVar);
        }
        else 
            return m_EngineEn.correctWordByMorph(word, oneVar);
    }

    private java.util.ArrayList<com.pullenti.morph.MorphWordForm> processOneWord0(String wstr) {
        com.pullenti.morph.MorphLang dl = new com.pullenti.morph.MorphLang();
        com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wrapdl6 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(dl);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> inoutres7 = this.processOneWord(wstr, wrapdl6);
        dl = wrapdl6.value;
        return inoutres7;
    }

    private java.util.ArrayList<com.pullenti.morph.MorphWordForm> processOneWord(String wstr, com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> defLang) {
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.LanguageHelper.getWordLang(wstr);
        if (lang.isUndefined()) {
            defLang.value = new com.pullenti.morph.MorphLang();
            return null;
        }
        if (lang.equals(com.pullenti.morph.MorphLang.EN)) 
            return m_EngineEn.process(wstr, false);
        if (defLang.value.equals(com.pullenti.morph.MorphLang.RU)) {
            if (lang.isRu()) 
                return m_EngineRu.process(wstr, false);
        }
        if (lang.equals(com.pullenti.morph.MorphLang.RU)) {
            defLang.value = lang;
            return m_EngineRu.process(wstr, false);
        }
        if (defLang.value.equals(com.pullenti.morph.MorphLang.UA)) {
            if (lang.isUa()) 
                return m_EngineUa.process(wstr, false);
        }
        if (lang.equals(com.pullenti.morph.MorphLang.UA)) {
            defLang.value = lang;
            return m_EngineUa.process(wstr, false);
        }
        if (defLang.value.equals(com.pullenti.morph.MorphLang.BY)) {
            if (lang.isBy()) 
                return m_EngineBy.process(wstr, false);
        }
        if (lang.equals(com.pullenti.morph.MorphLang.BY)) {
            defLang.value = lang;
            return m_EngineBy.process(wstr, false);
        }
        if (defLang.value.equals(com.pullenti.morph.MorphLang.KZ)) {
            if (lang.isKz()) 
                return m_EngineKz.process(wstr, false);
        }
        if (lang.equals(com.pullenti.morph.MorphLang.KZ)) {
            defLang.value = lang;
            return m_EngineKz.process(wstr, false);
        }
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> ru = null;
        if (lang.isRu()) 
            ru = m_EngineRu.process(wstr, false);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> ua = null;
        if (lang.isUa()) 
            ua = m_EngineUa.process(wstr, false);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> by = null;
        if (lang.isBy()) 
            by = m_EngineBy.process(wstr, false);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> kz = null;
        if (lang.isKz()) 
            kz = m_EngineKz.process(wstr, false);
        boolean hasRu = false;
        boolean hasUa = false;
        boolean hasBy = false;
        boolean hasKz = false;
        if (ru != null) {
            for (com.pullenti.morph.MorphWordForm wf : ru) {
                if (wf.isInDictionary()) 
                    hasRu = true;
            }
        }
        if (ua != null) {
            for (com.pullenti.morph.MorphWordForm wf : ua) {
                if (wf.isInDictionary()) 
                    hasUa = true;
            }
        }
        if (by != null) {
            for (com.pullenti.morph.MorphWordForm wf : by) {
                if (wf.isInDictionary()) 
                    hasBy = true;
            }
        }
        if (kz != null) {
            for (com.pullenti.morph.MorphWordForm wf : kz) {
                if (wf.isInDictionary()) 
                    hasKz = true;
            }
        }
        if ((hasRu && !hasUa && !hasBy) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.RU;
            return ru;
        }
        if ((hasUa && !hasRu && !hasBy) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.UA;
            return ua;
        }
        if ((hasBy && !hasRu && !hasUa) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.BY;
            return by;
        }
        if ((hasKz && !hasRu && !hasUa) && !hasBy) {
            defLang.value = com.pullenti.morph.MorphLang.KZ;
            return kz;
        }
        if ((ru == null && ua == null && by == null) && kz == null) 
            return null;
        if ((ru != null && ua == null && by == null) && kz == null) 
            return ru;
        if ((ua != null && ru == null && by == null) && kz == null) 
            return ua;
        if ((by != null && ru == null && ua == null) && kz == null) 
            return by;
        if ((kz != null && ru == null && ua == null) && by == null) 
            return kz;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
        if (ru != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.RU);
            com.pullenti.unisharp.Utils.addToArrayList(res, ru);
        }
        if (ua != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.UA);
            com.pullenti.unisharp.Utils.addToArrayList(res, ua);
        }
        if (by != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.BY);
            com.pullenti.unisharp.Utils.addToArrayList(res, by);
        }
        if (kz != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.KZ);
            com.pullenti.unisharp.Utils.addToArrayList(res, kz);
        }
        return res;
    }
}
