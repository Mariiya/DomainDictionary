/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Словарь некоторых обозначений, терминов, сокращений. Очень полезный класс! 
 * Рассчитан на быстрый поиск токена или группы токенов среди большого списка терминов.
 * 
 * Словарь
 */
public class TerminCollection {

    /**
     * Добавить термин. После добавления нельзя вносить изменения в термин, 
     * кроме как в значения Tag и Tag2 (иначе потом нужно вызвать Reindex).
     * @param term термин
     */
    public void add(Termin term) {
        termins.add(term);
        m_HashCanonic = null;
        this.reindex(term);
    }

    /**
     * Добавить строку в качестве записи словаря (термина).
     * @param _termins строка, которая подвергается морфологическому анализу, и в термин добавляются все варианты разбора
     * @param _tag это просто значения Tag для термина
     * @param lang язык (можно null, если язык анализируемого текста)
     * @param isNormalText если true, то исходный текст не нужно морфологически разбирать - он уже в нормальной форме и верхнем регистре
     * @return добавленный термин
     */
    public Termin addString(String _termins, Object _tag, com.pullenti.morph.MorphLang lang, boolean isNormalText) {
        Termin t = new Termin(_termins, lang, isNormalText || allAddStrsNormalized);
        t.tag = _tag;
        if (_tag != null && t.terms.size() == 1) {
        }
        this.add(t);
        return t;
    }

    /**
     * Полный список терминов (Termin)
     */
    public java.util.ArrayList<Termin> termins = new java.util.ArrayList<Termin>();

    // Если установлено true, то все входные термины уже нормализованы
    // (сделано для ускорения загрузки в Питоне).
    public boolean allAddStrsNormalized = false;

    /**
     * Возможный словарь синонимов (если в словаре комбинация не найдена, а она есть в синонимах, 
     * то синонимы ищутся в текущем словаре, и если есть, то ОК). Обычно null.
     */
    public TerminCollection synonyms = null;

    /**
     * Используйте произвольным образом
     */
    public Object tag;

    public static class CharNode {
    
        public java.util.HashMap<Short, CharNode> children;
    
        public java.util.ArrayList<com.pullenti.ner.core.Termin> termins;
        public CharNode() {
        }
    }


    private CharNode m_Root;

    private CharNode m_RootUa;

    private CharNode _getRoot(com.pullenti.morph.MorphLang lang, boolean isLat) {
        if (lang != null && lang.isUa() && !lang.isRu()) 
            return m_RootUa;
        return m_Root;
    }

    private java.util.HashMap<Short, java.util.ArrayList<Termin>> m_Hash1 = new java.util.HashMap<Short, java.util.ArrayList<Termin>>();

    private java.util.HashMap<String, java.util.ArrayList<Termin>> m_HashCanonic = null;

    /**
     * Переиндексировать термин (если после добавления у него что-либо поменялось)
     * @param t термин для переиндексации
     */
    public void reindex(Termin t) {
        if (t == null) 
            return;
        if (t.terms.size() > 20) {
        }
        if (t.acronymSmart != null) 
            this.addToHash1((short)t.acronymSmart.charAt(0), t);
        if (t.abridges != null) {
            for (Termin.Abridge a : t.abridges) {
                if (a.parts.get(0).value.length() == 1) 
                    this.addToHash1((short)a.parts.get(0).value.charAt(0), t);
            }
        }
        for (String v : t.getHashVariants()) {
            this._AddToTree(v, t);
        }
        if (t.additionalVars != null) {
            for (Termin av : t.additionalVars) {
                av.ignoreTermsOrder = t.ignoreTermsOrder;
                for (String v : av.getHashVariants()) {
                    this._AddToTree(v, t);
                }
            }
        }
    }

    public void remove(Termin t) {
        for (String v : t.getHashVariants()) {
            this._RemoveFromTree(v, t);
        }
        for (java.util.ArrayList<Termin> li : m_Hash1.values()) {
            for (Termin tt : li) {
                if (tt == t) {
                    li.remove(tt);
                    break;
                }
            }
        }
        int i = termins.indexOf(t);
        if (i >= 0) 
            termins.remove(i);
    }

    private void _AddToTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = this._getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                nod.children = new java.util.HashMap<Short, CharNode>();
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn601 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres602 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn601);
            nn = wrapnn601.value;
            if (!inoutres602) 
                nod.children.put(ch, (nn = new CharNode()));
            nod = nn;
        }
        if (nod.termins == null) 
            nod.termins = new java.util.ArrayList<Termin>();
        if (!nod.termins.contains(t)) 
            nod.termins.add(t);
    }

    private void _RemoveFromTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = this._getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                return;
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn603 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres604 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn603);
            nn = wrapnn603.value;
            if (!inoutres604) 
                return;
            nod = nn;
        }
        if (nod.termins == null) 
            return;
        if (nod.termins.contains(t)) 
            nod.termins.remove(t);
    }

    private java.util.ArrayList<Termin> _FindInTree(String key, com.pullenti.morph.MorphLang lang) {
        if (key == null) 
            return null;
        CharNode nod = this._getRoot(lang, ((lang == null || lang.isUndefined())) && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            CharNode nn = null;
            if (nod.children != null) {
                com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn605 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
                com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn605);
                nn = wrapnn605.value;
            }
            if (nn == null) {
                if (ch == ((short)32)) {
                    if (nod.termins != null) {
                        String[] pp = com.pullenti.unisharp.Utils.split(key, String.valueOf(' '), false);
                        java.util.ArrayList<Termin> res = null;
                        for (Termin t : nod.termins) {
                            if (t.terms.size() == pp.length) {
                                int k;
                                for (k = 1; k < pp.length; k++) {
                                    if (!t.terms.get(k).getVariants().contains(pp[k])) 
                                        break;
                                }
                                if (k >= pp.length) {
                                    if (res == null) 
                                        res = new java.util.ArrayList<Termin>();
                                    res.add(t);
                                }
                            }
                        }
                        return res;
                    }
                }
                return null;
            }
            nod = nn;
        }
        return nod.termins;
    }

    private void addToHash1(short key, Termin t) {
        java.util.ArrayList<Termin> li = null;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapli606 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
        boolean inoutres607 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash1, key, wrapli606);
        li = wrapli606.value;
        if (!inoutres607) 
            m_Hash1.put(key, (li = new java.util.ArrayList<Termin>()));
        if (!li.contains(t)) 
            li.add(t);
    }

    public Termin find(String key) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(key)) 
            return null;
        java.util.ArrayList<Termin> li;
        if (com.pullenti.morph.LanguageHelper.isLatinChar(key.charAt(0))) 
            li = this._FindInTree(key, com.pullenti.morph.MorphLang.EN);
        else {
            li = this._FindInTree(key, com.pullenti.morph.MorphLang.RU);
            if (li == null) 
                li = this._FindInTree(key, com.pullenti.morph.MorphLang.UA);
        }
        return (li != null && li.size() > 0 ? li.get(0) : null);
    }

    /**
     * Попытка найти термин в словаре для начального токена
     * @param token начальный токен
     * @param attrs атрибуты выделения
     * @return результирующий токен, если привязалось несколько, то первый, если ни одного, то null
     * 
     */
    public TerminToken tryParse(com.pullenti.ner.Token token, TerminParseAttr attrs) {
        if (termins.size() == 0) 
            return null;
        java.util.ArrayList<TerminToken> li = this.tryParseAll(token, attrs);
        if (li != null) 
            return li.get(0);
        else 
            return null;
    }

    /**
     * Попытка привязать все возможные термины
     * @param token начальный токен
     * @param attrs атрибуты выделения
     * @return список из всех подходящих привязок TerminToken или null
     * 
     */
    public java.util.ArrayList<TerminToken> tryParseAll(com.pullenti.ner.Token token, TerminParseAttr attrs) {
        if (token == null) 
            return null;
        java.util.ArrayList<TerminToken> re = this._TryAttachAll_(token, attrs, false);
        if (re == null && token.getMorph().getLanguage().isUa()) 
            re = this._TryAttachAll_(token, attrs, true);
        if (re == null && synonyms != null) {
            TerminToken re0 = synonyms.tryParse(token, TerminParseAttr.NO);
            if (re0 != null && (re0.termin.tag instanceof java.util.ArrayList)) {
                Termin term = this.find(re0.termin.getCanonicText());
                for (String syn : (java.util.ArrayList<String>)com.pullenti.unisharp.Utils.cast(re0.termin.tag, java.util.ArrayList.class)) {
                    if (term != null) 
                        break;
                    term = this.find(syn);
                }
                if (term != null) {
                    re0.termin = term;
                    java.util.ArrayList<TerminToken> res1 = new java.util.ArrayList<TerminToken>();
                    res1.add(re0);
                    return res1;
                }
            }
        }
        return re;
    }

    // Привязка с точностью до похожести
    // simD - параметр "похожесть (0.05..1)"
    public java.util.ArrayList<TerminToken> tryParseAllSim(com.pullenti.ner.Token token, double simD) {
        if (simD >= 1 || (simD < 0.05)) 
            return this.tryParseAll(token, TerminParseAttr.NO);
        if (termins.size() == 0 || token == null) 
            return null;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null && (token instanceof com.pullenti.ner.ReferentToken)) 
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.ReferentToken.class)).getBeginToken(), com.pullenti.ner.TextToken.class);
        java.util.ArrayList<TerminToken> res = null;
        for (Termin t : termins) {
            if (!t.lang.isUndefined()) {
                if (!token.getMorph().getLanguage().isUndefined()) {
                    if (((com.pullenti.morph.MorphLang.ooBitand(token.getMorph().getLanguage(), t.lang))).isUndefined()) 
                        continue;
                }
            }
            TerminToken ar = t.tryParseSim(tt, simD, TerminParseAttr.NO);
            if (ar == null) 
                continue;
            ar.termin = t;
            if (res == null || ar.getTokensCount() > res.get(0).getTokensCount()) {
                res = new java.util.ArrayList<TerminToken>();
                res.add(ar);
            }
            else if (ar.getTokensCount() == res.get(0).getTokensCount()) 
                res.add(ar);
        }
        return res;
    }

    private java.util.ArrayList<TerminToken> _TryAttachAll_(com.pullenti.ner.Token token, TerminParseAttr pars, boolean mainRoot) {
        if (termins.size() == 0 || token == null) 
            return null;
        String s = null;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null && (token instanceof com.pullenti.ner.MetaToken)) 
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.MetaToken.class)).getBeginToken(), com.pullenti.ner.TextToken.class);
        java.util.ArrayList<TerminToken> res = null;
        boolean wasVars = false;
        CharNode root = (mainRoot ? m_Root : this._getRoot(token.getMorph().getLanguage(), token.chars.isLatinLetter()));
        if (tt != null) {
            s = tt.term;
            CharNode nod = root;
            boolean noVars = false;
            int len0 = 0;
            if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) {
            }
            else if (tt.invariantPrefixLengthOfMorphVars <= s.length()) {
                len0 = (int)tt.invariantPrefixLengthOfMorphVars;
                for (int i = 0; i < tt.invariantPrefixLengthOfMorphVars; i++) {
                    short ch = (short)s.charAt(i);
                    if (nod.children == null) {
                        noVars = true;
                        break;
                    }
                    CharNode nn;
                    com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn608 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
                    boolean inoutres609 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn608);
                    nn = wrapnn608.value;
                    if (!inoutres609) {
                        noVars = true;
                        break;
                    }
                    nod = nn;
                }
                if ((noVars && tt.term0 != null && com.pullenti.unisharp.Utils.stringsNe(tt.term, tt.term0)) && tt.invariantPrefixLengthOfMorphVars <= tt.term0.length()) {
                    nod = root;
                    s = tt.term0;
                    noVars = false;
                    for (int i = 0; i < tt.invariantPrefixLengthOfMorphVars; i++) {
                        short ch = (short)s.charAt(i);
                        if (nod.children == null) {
                            noVars = true;
                            break;
                        }
                        CharNode nn;
                        com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn610 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
                        boolean inoutres611 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn610);
                        nn = wrapnn610.value;
                        if (!inoutres611) {
                            noVars = true;
                            break;
                        }
                        nod = nn;
                    }
                }
            }
            if (!noVars) {
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres616 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                boolean inoutres617 = this._manageVar(token, pars, s, nod, len0, wrapres616);
                res = wrapres616.value;
                if (inoutres617) 
                    wasVars = true;
                for (int i = 0; i < tt.getMorph().getItemsCount(); i++) {
                    if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) 
                        continue;
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(i), com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if ((((pars.value()) & (TerminParseAttr.INDICTIONARYONLY.value()))) != (TerminParseAttr.NO.value())) {
                        if (!wf.isInDictionary()) 
                            continue;
                    }
                    int j;
                    boolean ok = true;
                    if (wf.normalCase == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, s)) 
                        ok = false;
                    else {
                        for (j = 0; j < i; j++) {
                            com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                            if (wf2 != null) {
                                if (com.pullenti.unisharp.Utils.stringsEq(wf2.normalCase, wf.normalCase) || com.pullenti.unisharp.Utils.stringsEq(wf2.normalFull, wf.normalCase)) 
                                    break;
                            }
                        }
                        if (j < i) 
                            ok = false;
                    }
                    if (ok) {
                        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres612 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                        boolean inoutres613 = this._manageVar(token, pars, wf.normalCase, nod, (int)tt.invariantPrefixLengthOfMorphVars, wrapres612);
                        res = wrapres612.value;
                        if (inoutres613) 
                            wasVars = true;
                    }
                    if (wf.normalFull == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, wf.normalCase) || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, s)) 
                        continue;
                    for (j = 0; j < i; j++) {
                        com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                        if (wf2 != null && com.pullenti.unisharp.Utils.stringsEq(wf2.normalFull, wf.normalFull)) 
                            break;
                    }
                    if (j < i) 
                        continue;
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres614 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                    boolean inoutres615 = this._manageVar(token, pars, wf.normalFull, nod, (int)tt.invariantPrefixLengthOfMorphVars, wrapres614);
                    res = wrapres614.value;
                    if (inoutres615) 
                        wasVars = true;
                }
            }
        }
        else if (token instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres618 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
            boolean inoutres619 = this._manageVar(token, pars, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.NumberToken.class)).getValue().toString(), root, 0, wrapres618);
            res = wrapres618.value;
            if (inoutres619) 
                wasVars = true;
        }
        else 
            return null;
        if (!wasVars && s != null && s.length() == 1) {
            java.util.ArrayList<Termin> vars;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapvars620 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
            boolean inoutres621 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash1, (short)s.charAt(0), wrapvars620);
            vars = wrapvars620.value;
            if (inoutres621) {
                for (Termin t : vars) {
                    if (!t.lang.isUndefined()) {
                        if (!token.getMorph().getLanguage().isUndefined()) {
                            if (((com.pullenti.morph.MorphLang.ooBitand(token.getMorph().getLanguage(), t.lang))).isUndefined()) 
                                continue;
                        }
                    }
                    TerminToken ar = t.tryParse(tt, TerminParseAttr.NO);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res == null) {
                        res = new java.util.ArrayList<TerminToken>();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() > res.get(0).getTokensCount()) {
                        res.clear();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() == res.get(0).getTokensCount()) 
                        res.add(ar);
                }
            }
        }
        if (res != null) {
            int ii = 0;
            int max = 0;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i).getLengthChar() > max) {
                    max = res.get(i).getLengthChar();
                    ii = i;
                }
            }
            if (ii > 0) {
                TerminToken v = res.get(ii);
                res.remove(ii);
                res.add(0, v);
            }
        }
        return res;
    }

    private boolean _manageVar(com.pullenti.ner.Token token, TerminParseAttr pars, String v, CharNode nod, int i0, com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> res) {
        for (int i = i0; i < v.length(); i++) {
            short ch = (short)v.charAt(i);
            if (nod.children == null) 
                return false;
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn622 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres623 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn622);
            nn = wrapnn622.value;
            if (!inoutres623) 
                return false;
            nod = nn;
        }
        java.util.ArrayList<Termin> vars = nod.termins;
        if (vars == null || vars.size() == 0) 
            return false;
        for (Termin t : vars) {
            TerminToken ar = t.tryParse(token, pars);
            if (ar != null) {
                ar.termin = t;
                if (res.value == null) {
                    res.value = new java.util.ArrayList<TerminToken>();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                    res.value.clear();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                    int j;
                    for (j = 0; j < res.value.size(); j++) {
                        if (res.value.get(j).termin == ar.termin) 
                            break;
                    }
                    if (j >= res.value.size()) 
                        res.value.add(ar);
                }
            }
            if (t.additionalVars != null) {
                for (Termin av : t.additionalVars) {
                    ar = av.tryParse(token, pars);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res.value == null) {
                        res.value = new java.util.ArrayList<TerminToken>();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                        res.value.clear();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                        int j;
                        for (j = 0; j < res.value.size(); j++) {
                            if (res.value.get(j).termin == ar.termin) 
                                break;
                        }
                        if (j >= res.value.size()) 
                            res.value.add(ar);
                    }
                }
            }
        }
        return v.length() > 1;
    }

    /**
     * Поискать эквивалентные термины
     * @param termin термин
     * @return список эквивалентных терминов Termin или null
     */
    public java.util.ArrayList<Termin> findTerminsByTermin(Termin termin) {
        java.util.ArrayList<Termin> res = null;
        for (String v : termin.getHashVariants()) {
            java.util.ArrayList<Termin> vars = this._FindInTree(v, termin.lang);
            if (vars == null) 
                continue;
            for (Termin t : vars) {
                if (t.isEqual(termin)) {
                    if (res == null) 
                        res = new java.util.ArrayList<Termin>();
                    if (!res.contains(t)) 
                        res.add(t);
                }
            }
        }
        return res;
    }

    /**
     * Поискать термины по строке
     * @param str поисковая строка
     * @param lang возможный язык (null)
     * @return список терминов Termin или null
     */
    public java.util.ArrayList<Termin> findTerminsByString(String str, com.pullenti.morph.MorphLang lang) {
        return this._FindInTree(str, lang);
    }

    public java.util.ArrayList<Termin> findTerminsByCanonicText(String text) {
        if (m_HashCanonic == null) {
            m_HashCanonic = new java.util.HashMap<String, java.util.ArrayList<Termin>>();
            for (Termin t : termins) {
                String ct = t.getCanonicText();
                java.util.ArrayList<Termin> li;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapli624 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
                boolean inoutres625 = com.pullenti.unisharp.Utils.tryGetValue(m_HashCanonic, ct, wrapli624);
                li = wrapli624.value;
                if (!inoutres625) 
                    m_HashCanonic.put(ct, (li = new java.util.ArrayList<Termin>()));
                if (!li.contains(t)) 
                    li.add(t);
            }
        }
        java.util.ArrayList<Termin> res;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapres626 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
        boolean inoutres627 = com.pullenti.unisharp.Utils.tryGetValue(m_HashCanonic, text, wrapres626);
        res = wrapres626.value;
        if (!inoutres627) 
            return null;
        else 
            return res;
    }
    public TerminCollection() {
        if(_globalInstance == null) return;
        m_Root = new CharNode();
        m_RootUa = new CharNode();
    }
    public static TerminCollection _globalInstance;
    
    static {
        try { _globalInstance = new TerminCollection(); } 
        catch(Exception e) { }
    }
}
