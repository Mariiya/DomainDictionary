/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Термин, понятие, система обозначений чего-либо и варианты его написания. Элемент словаря TerminCollection.
 * 
 * Термин словаря
 */
public class Termin {

    /**
     * Создать термин из строки с добавлением всех морфологических вариантов написания
     * @param source строка
     * @param _lang возможный язык (null, если совпадает с текущим языком анализируемого текста)
     * @param sourceIsNormal при true морфварианты не добавляются 
     * (эквивалентно вызову InitByNormalText)
     */
    public Termin(String source, com.pullenti.morph.MorphLang _lang, boolean sourceIsNormal) {
        if(_globalInstance == null) return;
        if (source == null) 
            return;
        if (sourceIsNormal || ASSIGNALLTEXTSASNORMAL) {
            this.initByNormalText(source, _lang);
            return;
        }
        java.util.ArrayList<com.pullenti.morph.MorphToken> toks = com.pullenti.morph.MorphologyService.process(source, _lang, null);
        if (toks != null) {
            for (int i = 0; i < toks.size(); i++) {
                com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(toks.get(i), null, -1, -1);
                terms.add(new Term(tt, !sourceIsNormal, null));
            }
        }
        lang = new com.pullenti.morph.MorphLang();
        if (_lang != null) 
            lang.value = _lang.value;
    }

    // Используется внутренним образом (для ускорения Питона)
    public static boolean ASSIGNALLTEXTSASNORMAL = false;

    /**
     * Быстрая инициализация без морф.вариантов, производится только 
     * токенизация текста. Используется для ускорения работы со словарём в случае, 
     * когда изначально известно, что на входе уже нормализованные строки.
     * @param text исходно нормализованный текст
     * @param _lang возможный язык (можно null)
     */
    public void initByNormalText(String text, com.pullenti.morph.MorphLang _lang) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return;
        text = text.toUpperCase();
        if (text.indexOf('\'') >= 0) 
            text = text.replace("'", "");
        boolean tok = false;
        boolean sp = false;
        for (char ch : text.toCharArray()) {
            if (!Character.isLetter(ch)) {
                if (ch == ' ') 
                    sp = true;
                else {
                    tok = true;
                    break;
                }
            }
        }
        if (!tok && !sp) {
            com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(null, null, -1, -1);
            tt.term = text;
            terms.add(new Term(tt, false, null));
        }
        else if (!tok && sp) {
            String[] wrds = com.pullenti.unisharp.Utils.split(text, String.valueOf(' '), false);
            for (int i = 0; i < wrds.length; i++) {
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(wrds[i])) 
                    continue;
                com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(null, null, -1, -1);
                tt.term = wrds[i];
                terms.add(new Term(tt, false, null));
            }
        }
        else {
            java.util.ArrayList<com.pullenti.morph.MorphToken> toks = com.pullenti.morph.MorphologyService.tokenize(text);
            if (toks != null) {
                for (int i = 0; i < toks.size(); i++) {
                    com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(toks.get(i), null, -1, -1);
                    terms.add(new Term(tt, false, null));
                }
            }
        }
        lang = new com.pullenti.morph.MorphLang();
        if (_lang != null) 
            lang.value = _lang.value;
    }

    public void initBy(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, Object _tag, boolean addLemmaVariant) {
        if (_tag != null) 
            tag = _tag;
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            if (lang.isUndefined() && !t.getMorph().getLanguage().isUndefined()) 
                lang = t.getMorph().getLanguage();
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt != null) 
                terms.add(new Term(tt, addLemmaVariant, null));
            else if (t instanceof com.pullenti.ner.NumberToken) 
                terms.add(new Term(null, false, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue()));
            if (t == end) 
                break;
        }
    }

    // Морфологические токены полного написания - список Term.
    public java.util.ArrayList<Term> terms = new java.util.ArrayList<Term>();

    /**
     * Дополнительные варианты (список Termin, обычно null)
     */
    public java.util.ArrayList<Termin> additionalVars = null;

    /**
     * Добавить дополнительный вариант полного написания
     * @param var строка варианта
     * @param sourceIsNormal при true морфварианты не добавляются, иначе добавляются
     */
    public void addVariant(String var, boolean sourceIsNormal) {
        if (additionalVars == null) 
            additionalVars = new java.util.ArrayList<Termin>();
        additionalVars.add(new Termin(var, com.pullenti.morph.MorphLang.UNKNOWN, sourceIsNormal));
    }

    /**
     * Добавить дополнительный вариант написания
     * @param t термин
     */
    public void addVariantTerm(Termin t) {
        if (additionalVars == null) 
            additionalVars = new java.util.ArrayList<Termin>();
        additionalVars.add(t);
    }

    // Элемент термина (слово или число)
    public static class Term {
    
        public Term(com.pullenti.ner.TextToken src, boolean addLemmaVariant, String number) {
            m_Source = src;
            if (src != null) {
                getVariants().add(src.term);
                if (src.term.length() > 0 && Character.isDigit(src.term.charAt(0))) {
                    com.pullenti.ner.NumberToken nt = new com.pullenti.ner.NumberToken(src, src, src.term, com.pullenti.ner.NumberSpellingType.DIGIT, null);
                    m_Number = nt.getValue();
                    m_Source = null;
                    return;
                }
                if (addLemmaVariant) {
                    String lemma = src.lemma;
                    if (lemma != null && com.pullenti.unisharp.Utils.stringsNe(lemma, src.term)) 
                        getVariants().add(lemma);
                    for (com.pullenti.morph.MorphBaseInfo wff : src.getMorph().getItems()) {
                        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                        if (wf != null && wf.isInDictionary()) {
                            String s = (wf.normalFull != null ? wf.normalFull : wf.normalCase);
                            if (com.pullenti.unisharp.Utils.stringsNe(s, lemma) && com.pullenti.unisharp.Utils.stringsNe(s, src.term)) 
                                getVariants().add(s);
                        }
                    }
                }
            }
            if (number != null) {
                m_Number = number;
                getVariants().add(number);
            }
        }
    
        private com.pullenti.ner.TextToken m_Source;
    
        public boolean isPatternAny;
    
        private String m_Number;
    
        public java.util.Collection<String> getVariants() {
            return m_Variants;
        }
    
    
        private java.util.ArrayList<String> m_Variants = new java.util.ArrayList<String>();
    
        public String getCanonicalText() {
            return (m_Variants.size() > 0 ? m_Variants.get(0) : "?");
        }
    
    
        @Override
        public String toString() {
            if (isPatternAny) 
                return "IsPatternAny";
            StringBuilder res = new StringBuilder();
            for (String v : getVariants()) {
                if (res.length() > 0) 
                    res.append(", ");
                res.append(v);
            }
            return res.toString();
        }
    
        public boolean isNumber() {
            return m_Source == null || m_Number != null;
        }
    
    
        public boolean isHiphen() {
            return m_Source != null && com.pullenti.unisharp.Utils.stringsEq(m_Source.term, "-");
        }
    
    
        public boolean isPoint() {
            return m_Source != null && com.pullenti.unisharp.Utils.stringsEq(m_Source.term, ".");
        }
    
    
        public com.pullenti.morph.MorphGender getGender() {
            if (m_Gender != com.pullenti.morph.MorphGender.UNDEFINED) 
                return m_Gender;
            com.pullenti.morph.MorphGender res = com.pullenti.morph.MorphGender.UNDEFINED;
            if (m_Source != null) {
                for (com.pullenti.morph.MorphBaseInfo wf : m_Source.getMorph().getItems()) {
                    if ((wf instanceof com.pullenti.morph.MorphWordForm) && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                        res = com.pullenti.morph.MorphGender.of((short)((res.value()) | (wf.getGender().value())));
                }
            }
            return res;
        }
    
        public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
            m_Gender = value;
            if (m_Source != null) {
                for (int i = m_Source.getMorph().getItemsCount() - 1; i >= 0; i--) {
                    if (((short)((m_Source.getMorph().getIndexerItem(i).getGender().value()) & (value.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        m_Source.getMorph().removeItem(i);
                }
            }
            return value;
        }
    
    
        private com.pullenti.morph.MorphGender m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;
    
        public boolean isNoun() {
            if (m_Source != null) {
                for (com.pullenti.morph.MorphBaseInfo wf : m_Source.getMorph().getItems()) {
                    if (wf._getClass().isNoun()) 
                        return true;
                }
            }
            return false;
        }
    
    
        public boolean isAdjective() {
            if (m_Source != null) {
                for (com.pullenti.morph.MorphBaseInfo wf : m_Source.getMorph().getItems()) {
                    if (wf._getClass().isAdjective()) 
                        return true;
                }
            }
            return false;
        }
    
    
        public Iterable<com.pullenti.morph.MorphWordForm> getMorphWordForms() {
            java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
            if (m_Source != null) {
                for (com.pullenti.morph.MorphBaseInfo wf : m_Source.getMorph().getItems()) {
                    if (wf instanceof com.pullenti.morph.MorphWordForm) 
                        res.add((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class));
                }
            }
            return res;
        }
    
    
        public boolean checkByTerm(Term t) {
            if (isNumber()) 
                return com.pullenti.unisharp.Utils.stringsEq(m_Number, t.m_Number);
            if (m_Variants != null && t.m_Variants != null) {
                for (String v : m_Variants) {
                    if (t.m_Variants.contains(v)) 
                        return true;
                }
            }
            return false;
        }
    
        public boolean checkByToken(com.pullenti.ner.Token t) {
            return this._check(t, 0);
        }
    
        private boolean _check(com.pullenti.ner.Token t, int lev) {
            if (lev > 10) 
                return false;
            if (isPatternAny) 
                return true;
            if (t instanceof com.pullenti.ner.TextToken) {
                if (isNumber()) 
                    return false;
                for (String v : getVariants()) {
                    if (t.isValue(v, null)) 
                        return true;
                }
                return false;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (isNumber()) 
                    return com.pullenti.unisharp.Utils.stringsEq(m_Number, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                com.pullenti.ner.NumberToken num = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if (num.getBeginToken() == num.getEndToken()) 
                    return this._check(num.getBeginToken(), lev);
                return false;
            }
            if (t instanceof com.pullenti.ner.MetaToken) {
                com.pullenti.ner.MetaToken mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class);
                if (mt.getBeginToken() == mt.getEndToken()) {
                    if (this._check(mt.getBeginToken(), lev + 1)) 
                        return true;
                }
            }
            return false;
        }
    
        public boolean checkByPrefToken(Term prefix, com.pullenti.ner.TextToken t) {
            if (prefix == null || prefix.m_Source == null || t == null) 
                return false;
            String pref = prefix.getCanonicalText();
            String tterm = t.term;
            if (pref.charAt(0) != tterm.charAt(0)) 
                return false;
            if (!tterm.startsWith(pref)) 
                return false;
            for (String v : getVariants()) {
                if (t.isValue(pref + v, null)) 
                    return true;
            }
            return false;
        }
    
        public boolean checkByStrPrefToken(String pref, com.pullenti.ner.TextToken t) {
            if (pref == null || t == null) 
                return false;
            for (String v : getVariants()) {
                if (v.startsWith(pref) && v.length() > pref.length()) {
                    if (t.isValue(v.substring(pref.length()), null)) 
                        return true;
                }
            }
            return false;
        }
    
        public static Term _new2166(com.pullenti.ner.TextToken _arg1, boolean _arg2) {
            Term res = new Term(_arg1, false, null);
            res.isPatternAny = _arg2;
            return res;
        }
        public Term() {
        }
    }


    public String getCanonicText() {
        if (m_CanonicText != null) 
            return m_CanonicText;
        if (terms.size() > 0) {
            StringBuilder tmp = new StringBuilder();
            for (Term v : terms) {
                if (tmp.length() > 0) 
                    tmp.append(' ');
                tmp.append(v.getCanonicalText());
            }
            m_CanonicText = tmp.toString();
        }
        else if (acronym != null) 
            m_CanonicText = acronym;
        return (m_CanonicText != null ? m_CanonicText : "?");
    }

    public String setCanonicText(String value) {
        m_CanonicText = value;
        return value;
    }


    private String m_CanonicText;

    /**
     * Порядок токенов неважен (то есть привязка с точностью до перестановок)
     */
    public boolean ignoreTermsOrder;

    /**
     * Возможная аббревиатура (всегда слитно в верхнем регистре)
     */
    public String acronym;

    /**
     * "Мягкая" аббревиатура, допускающая разбивку, точки и т.п.
     */
    public String acronymSmart;

    /**
     * Аббревиатура м.б. в нижнем регистре
     */
    public boolean acronymCanBeLower;

    /**
     * Установить стандартную аббревиатуру
     */
    public void setStdAcronim(boolean smart) {
        StringBuilder acr = new StringBuilder();
        for (Term t : terms) {
            String s = t.getCanonicalText();
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
                continue;
            if (s.length() > 2) 
                acr.append(s.charAt(0));
        }
        if (acr.length() > 1) {
            if (smart) 
                acronymSmart = acr.toString();
            else 
                acronym = acr.toString();
        }
    }

    // Список возможных сокращений Abridge
    public java.util.ArrayList<Abridge> abridges;

    public static class Abridge {
    
        public java.util.ArrayList<com.pullenti.ner.core.Termin.AbridgePart> parts = new java.util.ArrayList<com.pullenti.ner.core.Termin.AbridgePart>();
    
        public void addPart(String val, boolean hasDelim) {
            parts.add(com.pullenti.ner.core.Termin.AbridgePart._new585(val, hasDelim));
        }
    
        public String tail;
    
        @Override
        public String toString() {
            if (tail != null) 
                return (parts.get(0).toString() + "-" + tail);
            StringBuilder res = new StringBuilder();
            for (com.pullenti.ner.core.Termin.AbridgePart p : parts) {
                res.append(p);
            }
            return res.toString();
        }
    
        public com.pullenti.ner.core.TerminToken tryAttach(com.pullenti.ner.Token t0) {
            com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class);
            if (t1 == null) 
                return null;
            if (com.pullenti.unisharp.Utils.stringsNe(t1.term, parts.get(0).value)) {
                if (parts.size() != 1 || !t1.isValue(parts.get(0).value, null)) 
                    return null;
            }
            if (tail == null) {
                com.pullenti.ner.Token te = t1;
                boolean point = false;
                if (te.getNext() != null) {
                    if (te.getNext().isChar('.')) {
                        te = te.getNext();
                        point = true;
                    }
                    else if (parts.size() > 1) {
                        while (te.getNext() != null) {
                            if (te.getNext().isCharOf("\\/.") || te.getNext().isHiphen()) {
                                te = te.getNext();
                                point = true;
                            }
                            else 
                                break;
                        }
                    }
                }
                if (te == null) 
                    return null;
                com.pullenti.ner.Token tt = te.getNext();
                for (int i = 1; i < parts.size(); i++) {
                    if (tt != null && tt.getWhitespacesBeforeCount() > 2) 
                        return null;
                    if (tt != null && ((tt.isHiphen() || tt.isCharOf("\\/.")))) 
                        tt = tt.getNext();
                    else if (!point && parts.get(i - 1).hasDelim) 
                        return null;
                    if (tt == null) 
                        return null;
                    if (tt instanceof com.pullenti.ner.TextToken) {
                        com.pullenti.ner.TextToken tet = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class);
                        if (com.pullenti.unisharp.Utils.stringsNe(tet.term, parts.get(i).value)) {
                            if (!tet.isValue(parts.get(i).value, null)) 
                                return null;
                        }
                    }
                    else if (tt instanceof com.pullenti.ner.MetaToken) {
                        com.pullenti.ner.MetaToken mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class);
                        if (mt.getBeginToken() != mt.getEndToken()) 
                            return null;
                        if (!mt.getBeginToken().isValue(parts.get(i).value, null)) 
                            return null;
                    }
                    te = tt;
                    if (tt.getNext() != null && ((tt.getNext().isCharOf(".\\/") || tt.getNext().isHiphen()))) {
                        tt = tt.getNext();
                        point = true;
                        if (tt != null) 
                            te = tt;
                    }
                    else 
                        point = false;
                    tt = tt.getNext();
                }
                com.pullenti.ner.core.TerminToken res = com.pullenti.ner.core.TerminToken._new586(t0, te, t0 == te);
                if (point) 
                    res.setMorph(new com.pullenti.ner.MorphCollection(null));
                return res;
            }
            t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class);
            if (t1 == null || !t1.isCharOf("-\\/")) 
                return null;
            t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class);
            if (t1 == null) 
                return null;
            if (t1.term.charAt(0) != tail.charAt(0)) 
                return null;
            return new com.pullenti.ner.core.TerminToken(t0, t1);
        }
        public Abridge() {
        }
    }


    public static class AbridgePart {
    
        public String value;
    
        public boolean hasDelim;
    
        @Override
        public String toString() {
            if (hasDelim) 
                return value + ".";
            else 
                return value;
        }
    
        public static AbridgePart _new585(String _arg1, boolean _arg2) {
            AbridgePart res = new AbridgePart();
            res.value = _arg1;
            res.hasDelim = _arg2;
            return res;
        }
    
        public static AbridgePart _new587(String _arg1) {
            AbridgePart res = new AbridgePart();
            res.value = _arg1;
            return res;
        }
        public AbridgePart() {
        }
    }


    /**
     * Добавить сокращение в термин
     * @param abr сокращение, например, "нас.п." или "д-р наук"
     * @return разобранное сокращение, добавленное в термин
     */
    public Abridge addAbridge(String abr) {
        if (com.pullenti.unisharp.Utils.stringsEq(abr, "В/ГОР")) {
        }
        Abridge a = new Abridge();
        if (abridges == null) 
            abridges = new java.util.ArrayList<Abridge>();
        int i;
        for (i = 0; i < abr.length(); i++) {
            if (!Character.isLetter(abr.charAt(i))) 
                break;
        }
        if (i == 0) 
            return null;
        a.parts.add(AbridgePart._new587(abr.substring(0, 0 + i).toUpperCase()));
        abridges.add(a);
        if (((i + 1) < abr.length()) && abr.charAt(i) == '-') 
            a.tail = abr.substring(i + 1).toUpperCase();
        else if (i < abr.length()) {
            if (!com.pullenti.unisharp.Utils.isWhitespace(abr.charAt(i))) 
                a.parts.get(0).hasDelim = true;
            for (; i < abr.length(); i++) {
                if (Character.isLetter(abr.charAt(i))) {
                    int j;
                    for (j = i + 1; j < abr.length(); j++) {
                        if (!Character.isLetter(abr.charAt(j))) 
                            break;
                    }
                    AbridgePart p = AbridgePart._new587(abr.substring(i, i + j - i).toUpperCase());
                    if (j < abr.length()) {
                        if (!com.pullenti.unisharp.Utils.isWhitespace(abr.charAt(j))) 
                            p.hasDelim = true;
                    }
                    a.parts.add(p);
                    i = j;
                }
            }
        }
        return a;
    }

    /**
     * Язык
     */
    public com.pullenti.morph.MorphLang lang = new com.pullenti.morph.MorphLang();

    /**
     * Используется произвольным образом
     */
    public Object tag;

    /**
     * Используется произвольным образом
     */
    public Object tag2;

    /**
     * Используется произвольным образом
     */
    public Object tag3;

    public com.pullenti.morph.MorphGender getGender() {
        if (terms.size() > 0) {
            if (terms.size() > 0 && terms.get(0).isAdjective() && terms.get(terms.size() - 1).isNoun()) 
                return terms.get(terms.size() - 1).getGender();
            return terms.get(0).getGender();
        }
        else 
            return com.pullenti.morph.MorphGender.UNDEFINED;
    }

    public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
        if (terms.size() > 0) 
            terms.get(0).setGender(value);
        return value;
    }


    public void copyTo(Termin dst) {
        dst.terms = terms;
        dst.ignoreTermsOrder = ignoreTermsOrder;
        dst.acronym = acronym;
        dst.abridges = abridges;
        dst.lang = lang;
        dst.m_CanonicText = m_CanonicText;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (terms.size() > 0) {
            for (int i = 0; i < terms.size(); i++) {
                if (i > 0) 
                    res.append(' ');
                res.append(terms.get(i).getCanonicalText());
            }
        }
        if (acronym != null) {
            if (res.length() > 0) 
                res.append(", ");
            res.append(acronym);
        }
        if (acronymSmart != null) {
            if (res.length() > 0) 
                res.append(", ");
            res.append(acronymSmart);
        }
        if (abridges != null) {
            for (Abridge a : abridges) {
                if (res.length() > 0) 
                    res.append(", ");
                res.append(a);
            }
        }
        return res.toString();
    }

    private static String[] m_StdAbridePrefixes;

    public void addStdAbridges() {
        if (terms.size() != 2) 
            return;
        String first = terms.get(0).getCanonicalText();
        int i;
        for (i = 0; i < m_StdAbridePrefixes.length; i++) {
            if (first.startsWith(m_StdAbridePrefixes[i])) 
                break;
        }
        if (i >= m_StdAbridePrefixes.length) 
            return;
        String head = m_StdAbridePrefixes[i];
        String second = terms.get(1).getCanonicalText();
        for (i = 0; i < head.length(); i++) {
            if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(head.charAt(i))) {
                Abridge a = new Abridge();
                a.addPart(head.substring(0, 0 + i + 1), false);
                a.addPart(second, false);
                if (abridges == null) 
                    abridges = new java.util.ArrayList<Abridge>();
                abridges.add(a);
            }
        }
    }

    /**
     * Добавить все сокращения (с первой буквы до любого согласного)
     */
    public void addAllAbridges(int tailLen, int maxFirstLen, int minFirstLen) {
        if (terms.size() < 1) 
            return;
        String txt = terms.get(0).getCanonicalText();
        if (tailLen == 0) {
            for (int i = txt.length() - 2; i >= 0; i--) {
                if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(txt.charAt(i))) {
                    if (minFirstLen > 0 && (i < (minFirstLen - 1))) 
                        break;
                    Abridge a = new Abridge();
                    a.addPart(txt.substring(0, 0 + i + 1), false);
                    for (int j = 1; j < terms.size(); j++) {
                        a.addPart(terms.get(j).getCanonicalText(), false);
                    }
                    if (abridges == null) 
                        abridges = new java.util.ArrayList<Abridge>();
                    abridges.add(a);
                }
            }
        }
        else {
            String tail = txt.substring(txt.length() - tailLen);
            txt = txt.substring(0, 0 + txt.length() - tailLen - 1);
            for (int i = txt.length() - 2; i >= 0; i--) {
                if (maxFirstLen > 0 && i >= maxFirstLen) {
                }
                else if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(txt.charAt(i))) 
                    this.addAbridge((txt.substring(0, 0 + i + 1) + "-" + tail));
            }
        }
    }

    public java.util.ArrayList<String> getHashVariants() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (int j = 0; j < terms.size(); j++) {
            for (String v : terms.get(j).getVariants()) {
                if (!res.contains(v)) 
                    res.add(v);
            }
            if (((j + 2) < terms.size()) && terms.get(j + 1).isHiphen()) {
                String pref = terms.get(j).getCanonicalText();
                for (String v : terms.get(j + 2).getVariants()) {
                    if (!res.contains(pref + v)) 
                        res.add(pref + v);
                }
            }
            if (!ignoreTermsOrder) 
                break;
        }
        if (acronym != null) {
            if (!res.contains(acronym)) 
                res.add(acronym);
        }
        if (acronymSmart != null) {
            if (!res.contains(acronymSmart)) 
                res.add(acronymSmart);
        }
        if (abridges != null) {
            for (Abridge a : abridges) {
                if (a.parts.get(0).value.length() > 1) {
                    if (!res.contains(a.parts.get(0).value)) 
                        res.add(a.parts.get(0).value);
                }
            }
        }
        return res;
    }

    public boolean isEqual(Termin t) {
        if (t.acronym != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(acronym, t.acronym) || com.pullenti.unisharp.Utils.stringsEq(acronymSmart, t.acronym)) 
                return true;
        }
        if (t.acronymSmart != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(acronym, t.acronymSmart) || com.pullenti.unisharp.Utils.stringsEq(acronymSmart, t.acronymSmart)) 
                return true;
        }
        if (t.terms.size() != terms.size()) 
            return false;
        for (int i = 0; i < terms.size(); i++) {
            if (!terms.get(i).checkByTerm(t.terms.get(i))) 
                return false;
        }
        return true;
    }

    /**
     * Попробовать привязать термин
     * @param t0 начальный токен
     * @param pars дополнительные параметры привязки
     * @return метатокен привязки или null
     */
    public TerminToken tryParse(com.pullenti.ner.Token t0, TerminParseAttr pars) {
        if (t0 == null) 
            return null;
        String term = null;
        String term0 = null;
        if (t0 instanceof com.pullenti.ner.TextToken) {
            term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term;
            term0 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term0;
            if (com.pullenti.unisharp.Utils.stringsEq(term0, term)) 
                term0 = null;
        }
        if (acronymSmart != null && (((pars.value()) & (TerminParseAttr.FULLWORDSONLY.value()))) == (TerminParseAttr.NO.value()) && term != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(acronymSmart, term) || com.pullenti.unisharp.Utils.stringsEq(acronymSmart, term0)) {
                if (t0.getNext() != null && t0.getNext().isChar('.') && !t0.isWhitespaceAfter()) 
                    return TerminToken._new424(t0, t0.getNext(), this);
                else 
                    return TerminToken._new424(t0, t0, this);
            }
            int i;
            com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class);
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class);
            for (i = 0; i < acronym.length(); i++) {
                if (tt == null) 
                    break;
                String term1 = tt.term;
                if (term1.length() != 1 || tt.isWhitespaceAfter()) 
                    break;
                if (i > 0 && tt.isWhitespaceBefore()) 
                    break;
                if (term1.charAt(0) != acronym.charAt(i)) 
                    break;
                if (tt.getNext() == null || !tt.getNext().isChar('.')) 
                    break;
                t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class);
            }
            if (i >= acronym.length()) 
                return TerminToken._new424(t0, t1, this);
        }
        if (acronym != null && term != null && ((com.pullenti.unisharp.Utils.stringsEq(acronym, term) || com.pullenti.unisharp.Utils.stringsEq(acronym, term0)))) {
            if (t0.chars.isAllUpper() || acronymCanBeLower || ((!t0.chars.isAllLower() && term.length() >= 3))) 
                return TerminToken._new424(t0, t0, this);
        }
        if (acronym != null && t0.chars.isLastLower() && t0.getLengthChar() > 3) {
            if (t0.isValue(acronym, null)) 
                return TerminToken._new424(t0, t0, this);
        }
        int cou = 0;
        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).isHiphen()) 
                cou--;
            else 
                cou++;
        }
        if (terms.size() > 0 && ((!ignoreTermsOrder || cou == 1))) {
            com.pullenti.ner.Token t1 = t0;
            com.pullenti.ner.Token tt = t0;
            com.pullenti.ner.Token e = null;
            com.pullenti.ner.Token eUp = null;
            boolean ok = true;
            com.pullenti.ner.MorphCollection mc = null;
            boolean dontChangeMc = false;
            int i;
            for (i = 0; i < terms.size(); i++) {
                if (terms.get(i).isHiphen()) 
                    continue;
                if (tt != null && tt.isHiphen() && i > 0) 
                    tt = tt.getNext();
                if (i > 0 && tt != null) {
                    if ((((pars.value()) & (TerminParseAttr.IGNOREBRACKETS.value()))) != (TerminParseAttr.NO.value()) && !tt.chars.isLetter() && BracketHelper.isBracket(tt, false)) 
                        tt = tt.getNext();
                }
                if (((((pars.value()) & (TerminParseAttr.CANBEGEOOBJECT.value()))) != (TerminParseAttr.NO.value()) && i > 0 && (tt instanceof com.pullenti.ner.ReferentToken)) && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "GEO")) 
                    tt = tt.getNext();
                if ((tt instanceof com.pullenti.ner.ReferentToken) && e == null) {
                    eUp = tt;
                    e = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class)).getEndToken();
                    tt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class)).getBeginToken();
                }
                if (tt == null) {
                    ok = false;
                    break;
                }
                if (!terms.get(i).checkByToken(tt)) {
                    if (tt.getNext() != null && tt.isCharOf(".,") && terms.get(i).checkByToken(tt.getNext())) 
                        tt = tt.getNext();
                    else if (((i > 0 && tt.getNext() != null && (tt instanceof com.pullenti.ner.TextToken)) && ((tt.getMorph()._getClass().isPreposition() || MiscHelper.isEngArticle(tt))) && terms.get(i).checkByToken(tt.getNext())) && !terms.get(i - 1).isPatternAny) 
                        tt = tt.getNext();
                    else {
                        ok = false;
                        if (((i + 2) < terms.size()) && terms.get(i + 1).isHiphen() && terms.get(i + 2).checkByPrefToken(terms.get(i), (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))) {
                            i += 2;
                            ok = true;
                        }
                        else if (((!tt.isWhitespaceAfter() && tt.getNext() != null && (tt instanceof com.pullenti.ner.TextToken)) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).getLengthChar() == 1 && tt.getNext().isCharOf("\"'`’“”")) && !tt.getNext().isWhitespaceAfter() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                            if (terms.get(i).checkByStrPrefToken(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class))) {
                                ok = true;
                                tt = tt.getNext().getNext();
                            }
                        }
                        if (!ok) {
                            if (i > 0 && (((pars.value()) & (TerminParseAttr.IGNORESTOPWORDS.value()))) != (TerminParseAttr.NO.value())) {
                                if (tt instanceof com.pullenti.ner.TextToken) {
                                    if (!tt.chars.isLetter()) {
                                        tt = tt.getNext();
                                        i--;
                                        continue;
                                    }
                                    com.pullenti.morph.MorphClass mc1 = tt.getMorphClassInDictionary();
                                    if (mc1.isConjunction() || mc1.isPreposition()) {
                                        tt = tt.getNext();
                                        i--;
                                        continue;
                                    }
                                }
                                if (tt instanceof com.pullenti.ner.NumberToken) {
                                    tt = tt.getNext();
                                    i--;
                                    continue;
                                }
                            }
                            break;
                        }
                    }
                }
                if (tt.getMorph().getItemsCount() > 0 && !dontChangeMc) {
                    mc = new com.pullenti.ner.MorphCollection(tt.getMorph());
                    if (((mc._getClass().isNoun() || mc._getClass().isVerb())) && !mc._getClass().isAdjective()) {
                        if (((i + 1) < terms.size()) && terms.get(i + 1).isHiphen()) {
                        }
                        else 
                            dontChangeMc = true;
                    }
                }
                if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                    dontChangeMc = true;
                if (tt == e) {
                    tt = eUp;
                    eUp = null;
                    e = null;
                }
                if (e == null) 
                    t1 = tt;
                tt = tt.getNext();
            }
            if (ok && i >= terms.size()) {
                if (t1.getNext() != null && t1.getNext().isChar('.') && abridges != null) {
                    for (Abridge a : abridges) {
                        if (a.tryAttach(t0) != null) {
                            t1 = t1.getNext();
                            break;
                        }
                    }
                }
                if (t0 != t1 && t0.getMorph()._getClass().isAdjective()) {
                    NounPhraseToken npt = NounPhraseHelper.tryParse(t0, NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getEndChar() <= t1.getEndChar()) 
                        mc = npt.getMorph();
                }
                return TerminToken._new594(t0, t1, mc);
            }
        }
        if (terms.size() > 1 && ignoreTermsOrder) {
            java.util.ArrayList<Term> _terms = new java.util.ArrayList<Term>(terms);
            com.pullenti.ner.Token t1 = t0;
            com.pullenti.ner.Token tt = t0;
            while (_terms.size() > 0) {
                if (tt != t0 && tt != null && tt.isHiphen()) 
                    tt = tt.getNext();
                if (tt == null) 
                    break;
                int j;
                for (j = 0; j < _terms.size(); j++) {
                    if (_terms.get(j).checkByToken(tt)) 
                        break;
                }
                if (j >= _terms.size()) {
                    if (tt != t0 && (((pars.value()) & (TerminParseAttr.IGNORESTOPWORDS.value()))) != (TerminParseAttr.NO.value())) {
                        if (tt instanceof com.pullenti.ner.TextToken) {
                            if (!tt.chars.isLetter()) {
                                tt = tt.getNext();
                                continue;
                            }
                            com.pullenti.morph.MorphClass mc1 = tt.getMorphClassInDictionary();
                            if (mc1.isConjunction() || mc1.isPreposition()) {
                                tt = tt.getNext();
                                continue;
                            }
                        }
                        if (tt instanceof com.pullenti.ner.NumberToken) {
                            tt = tt.getNext();
                            continue;
                        }
                    }
                    break;
                }
                _terms.remove(j);
                t1 = tt;
                tt = tt.getNext();
            }
            for (int i = _terms.size() - 1; i >= 0; i--) {
                if (_terms.get(i).isHiphen()) 
                    _terms.remove(i);
            }
            if (_terms.size() == 0) 
                return new TerminToken(t0, t1);
        }
        if (abridges != null && (((pars.value()) & (TerminParseAttr.FULLWORDSONLY.value()))) == (TerminParseAttr.NO.value())) {
            TerminToken res = null;
            for (Abridge a : abridges) {
                TerminToken r = a.tryAttach(t0);
                if (r == null) 
                    continue;
                if (r.abridgeWithoutPoint && terms.size() > 0) {
                    if (!(t0 instanceof com.pullenti.ner.TextToken)) 
                        continue;
                    if (com.pullenti.unisharp.Utils.stringsNe(a.parts.get(0).value, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term)) 
                        continue;
                }
                if (res == null || (res.getLengthChar() < r.getLengthChar())) 
                    res = r;
            }
            if (res != null) 
                return res;
        }
        return null;
    }

    // Попробовать привязать термин с использованием "похожести"
    public TerminToken tryParseSim(com.pullenti.ner.Token t0, double simD, TerminParseAttr pars) {
        if (t0 == null) 
            return null;
        if (simD >= 1 || (simD < 0.05)) 
            return this.tryParse(t0, pars);
        String term = null;
        if (t0 instanceof com.pullenti.ner.TextToken) 
            term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term;
        if (acronymSmart != null && (((pars.value()) & (TerminParseAttr.FULLWORDSONLY.value()))) == (TerminParseAttr.NO.value()) && term != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(acronymSmart, term)) {
                if (t0.getNext() != null && t0.getNext().isChar('.') && !t0.isWhitespaceAfter()) 
                    return TerminToken._new424(t0, t0.getNext(), this);
                else 
                    return TerminToken._new424(t0, t0, this);
            }
            int i;
            com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class);
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class);
            for (i = 0; i < acronym.length(); i++) {
                if (tt == null) 
                    break;
                String term1 = tt.term;
                if (term1.length() != 1 || tt.isWhitespaceAfter()) 
                    break;
                if (i > 0 && tt.isWhitespaceBefore()) 
                    break;
                if (term1.charAt(0) != acronym.charAt(i)) 
                    break;
                if (tt.getNext() == null || !tt.getNext().isChar('.')) 
                    break;
                t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class);
            }
            if (i >= acronym.length()) 
                return TerminToken._new424(t0, t1, this);
        }
        if (acronym != null && term != null && com.pullenti.unisharp.Utils.stringsEq(acronym, term)) {
            if (t0.chars.isAllUpper() || acronymCanBeLower || ((!t0.chars.isAllLower() && term.length() >= 3))) 
                return TerminToken._new424(t0, t0, this);
        }
        if (acronym != null && t0.chars.isLastLower() && t0.getLengthChar() > 3) {
            if (t0.isValue(acronym, null)) 
                return TerminToken._new424(t0, t0, this);
        }
        if (terms.size() > 0) {
            com.pullenti.ner.Token t1 = null;
            com.pullenti.ner.Token tt = t0;
            com.pullenti.ner.MorphCollection mc = null;
            int termInd = -1;
            double termsLen = 0.0;
            double tkCnt = 0.0;
            double termsFoundCnt = 0.0;
            boolean wrOder = false;
            for (Term it : terms) {
                if ((it.getCanonicalText().length() < 2) || it.isHiphen() || it.isPoint()) 
                    termsLen += 0.3;
                else if (it.isNumber() || it.isPatternAny) 
                    termsLen += 0.7;
                else 
                    termsLen += (1.0);
            }
            double maxTksLen = termsLen / simD;
            double curJM = simD;
            java.util.ArrayList<Integer> termsFound = new java.util.ArrayList<Integer>();
            while (tt != null && (tkCnt < maxTksLen) && (termsFoundCnt < termsLen)) {
                com.pullenti.morph.MorphClass mcls = null;
                com.pullenti.ner.TextToken ttt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class);
                boolean mm = false;
                if (tt.getLengthChar() < 2) 
                    tkCnt += 0.3;
                else if (tt instanceof com.pullenti.ner.NumberToken) 
                    tkCnt += 0.7;
                else if (ttt == null) 
                    tkCnt++;
                else {
                    mcls = ttt.getMorph()._getClass();
                    mm = ((mcls.isConjunction() || mcls.isPreposition() || mcls.isPronoun()) || mcls.isMisc() || mcls.isUndefined());
                    if (mm) 
                        tkCnt += 0.3;
                    else 
                        tkCnt += (1.0);
                }
                for (int i = 0; i < terms.size(); i++) {
                    if (!termsFound.contains(i)) {
                        Term trm = terms.get(i);
                        if (trm.isPatternAny) {
                            termsFoundCnt += 0.7;
                            termsFound.add(i);
                            break;
                        }
                        else if (trm.getCanonicalText().length() < 2) {
                            termsFoundCnt += 0.3;
                            termsFound.add(i);
                            break;
                        }
                        else if (trm.checkByToken(tt)) {
                            termsFound.add(i);
                            if (mm) {
                                termsLen -= 0.7;
                                termsFoundCnt += 0.3;
                            }
                            else 
                                termsFoundCnt += (trm.isNumber() ? 0.7 : 1.0);
                            if (!wrOder) {
                                if (i < termInd) 
                                    wrOder = true;
                                else 
                                    termInd = i;
                            }
                            break;
                        }
                    }
                }
                if (termsFoundCnt < 0.2) 
                    return null;
                double newJM = (termsFoundCnt / (((tkCnt + termsLen) - termsFoundCnt))) * ((wrOder ? 0.7 : 1.0));
                if (curJM < newJM) {
                    t1 = tt;
                    curJM = newJM;
                }
                tt = tt.getNext();
            }
            if (t1 == null) 
                return null;
            if (t0.getMorph().getItemsCount() > 0) 
                mc = new com.pullenti.ner.MorphCollection(t0.getMorph());
            return TerminToken._new600(t0, t1, mc, this);
        }
        if (abridges != null && (((pars.value()) & (TerminParseAttr.FULLWORDSONLY.value()))) == (TerminParseAttr.NO.value())) {
            TerminToken res = null;
            for (Abridge a : abridges) {
                TerminToken r = a.tryAttach(t0);
                if (r == null) 
                    continue;
                if (r.abridgeWithoutPoint && terms.size() > 0) {
                    if (!(t0 instanceof com.pullenti.ner.TextToken)) 
                        continue;
                    if (com.pullenti.unisharp.Utils.stringsNe(a.parts.get(0).value, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term)) 
                        continue;
                }
                if (res == null || (res.getLengthChar() < r.getLengthChar())) 
                    res = r;
            }
            if (res != null) 
                return res;
        }
        return null;
    }

    public static Termin _new100(String _arg1, Object _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        return res;
    }

    public static Termin _new101(String _arg1, Object _arg2, com.pullenti.morph.MorphLang _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.lang = _arg3;
        return res;
    }

    public static Termin _new102(String _arg1, Object _arg2, Object _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.tag2 = _arg3;
        return res;
    }

    public static Termin _new126(String _arg1, String _arg2, Object _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        return res;
    }

    public static Termin _new128(String _arg1, Object _arg2, String _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.acronym = _arg3;
        return res;
    }

    public static Termin _new237(String _arg1, Object _arg2, com.pullenti.morph.MorphGender _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static Termin _new238(String _arg1, Object _arg2, com.pullenti.morph.MorphLang _arg3, com.pullenti.morph.MorphGender _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.lang = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static Termin _new240(String _arg1, Object _arg2, Object _arg3, com.pullenti.morph.MorphGender _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.tag2 = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static Termin _new241(String _arg1, Object _arg2, com.pullenti.morph.MorphLang _arg3, Object _arg4, com.pullenti.morph.MorphGender _arg5) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.lang = _arg3;
        res.tag2 = _arg4;
        res.setGender(_arg5);
        return res;
    }

    public static Termin _new269(String _arg1, String _arg2, Object _arg3, Object _arg4, com.pullenti.morph.MorphGender _arg5) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        res.tag2 = _arg4;
        res.setGender(_arg5);
        return res;
    }

    public static Termin _new270(String _arg1, Object _arg2, String _arg3, Object _arg4, com.pullenti.morph.MorphGender _arg5) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.acronym = _arg3;
        res.tag2 = _arg4;
        res.setGender(_arg5);
        return res;
    }

    public static Termin _new290(String _arg1, String _arg2, Object _arg3, com.pullenti.morph.MorphLang _arg4, Object _arg5, com.pullenti.morph.MorphGender _arg6) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        res.lang = _arg4;
        res.tag2 = _arg5;
        res.setGender(_arg6);
        return res;
    }

    public static Termin _new291(String _arg1, String _arg2, Object _arg3, com.pullenti.morph.MorphGender _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        res.tag = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static Termin _new295(String _arg1, Object _arg2, String _arg3, com.pullenti.morph.MorphGender _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.acronym = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static Termin _new320(Object _arg1, boolean _arg2) {
        Termin res = new Termin(null, null, false);
        res.tag = _arg1;
        res.ignoreTermsOrder = _arg2;
        return res;
    }

    public static Termin _new372(String _arg1, com.pullenti.morph.MorphLang _arg2, Object _arg3) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.tag = _arg3;
        return res;
    }

    public static Termin _new433(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4, Object _arg5) {
        Termin res = new Termin(_arg1, _arg2, _arg3);
        res.setCanonicText(_arg4);
        res.tag = _arg5;
        return res;
    }

    public static Termin _new552(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, _arg2, _arg3);
        res.tag = _arg4;
        return res;
    }

    public static Termin _new699(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4) {
        Termin res = new Termin(_arg1, _arg2, _arg3);
        res.setCanonicText(_arg4);
        return res;
    }

    public static Termin _new968(String _arg1, com.pullenti.morph.MorphLang _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.lang = _arg2;
        return res;
    }

    public static Termin _new969(String _arg1, String _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        return res;
    }

    public static Termin _new980(String _arg1, com.pullenti.morph.MorphLang _arg2, Object _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.tag = _arg3;
        res.tag2 = _arg4;
        return res;
    }

    public static Termin _new984(String _arg1, com.pullenti.morph.MorphLang _arg2, Object _arg3, String _arg4) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.tag = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static Termin _new990(String _arg1, String _arg2, com.pullenti.morph.MorphLang _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.lang = _arg3;
        res.tag = _arg4;
        return res;
    }

    public static Termin _new994(String _arg1, Object _arg2, String _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.acronym = _arg3;
        res.tag2 = _arg4;
        return res;
    }

    public static Termin _new996(String _arg1, com.pullenti.morph.MorphLang _arg2, Object _arg3, String _arg4, Object _arg5) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.tag = _arg3;
        res.acronym = _arg4;
        res.tag2 = _arg5;
        return res;
    }

    public static Termin _new1016(String _arg1, String _arg2, Object _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        res.tag = _arg3;
        res.tag2 = _arg4;
        return res;
    }

    public static Termin _new1067(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.setCanonicText(_arg3);
        res.tag = _arg4;
        return res;
    }

    public static Termin _new1102(String _arg1, Object _arg2, Object _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.tag = _arg2;
        res.tag3 = _arg3;
        return res;
    }

    public static Termin _new1103(String _arg1, com.pullenti.morph.MorphLang _arg2, Object _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.tag = _arg3;
        res.tag3 = _arg4;
        return res;
    }

    public static Termin _new1189(String _arg1, String _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        return res;
    }

    public static Termin _new1210(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.setCanonicText(_arg3);
        return res;
    }

    public static Termin _new1223(String _arg1, boolean _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.ignoreTermsOrder = _arg2;
        return res;
    }

    public static Termin _new1272(String _arg1, String _arg2, boolean _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        res.acronymCanBeLower = _arg3;
        return res;
    }

    public static Termin _new1274(String _arg1, String _arg2, String _arg3, boolean _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        res.acronymSmart = _arg3;
        res.acronymCanBeLower = _arg4;
        return res;
    }

    public static Termin _new1405(String _arg1, com.pullenti.morph.MorphLang _arg2) {
        Termin res = new Termin(null, null, false);
        res.acronym = _arg1;
        res.lang = _arg2;
        return res;
    }

    public static Termin _new1664(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3) {
        Termin res = new Termin(_arg1, _arg2, false);
        res.acronym = _arg3;
        return res;
    }

    public static Termin _new2563(String _arg1, Object _arg2) {
        Termin res = new Termin(_arg1, null, false);
        res.tag2 = _arg2;
        return res;
    }

    public static Termin _new2849(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, _arg2, _arg3);
        res.tag2 = _arg4;
        return res;
    }

    public static Termin _new2937(String _arg1, String _arg2, Object _arg3, String _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static Termin _new2947(String _arg1, String _arg2, Object _arg3, String _arg4, boolean _arg5) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        res.acronym = _arg4;
        res.acronymCanBeLower = _arg5;
        return res;
    }

    public static Termin _new2960(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4, Object _arg5, Object _arg6) {
        Termin res = new Termin(_arg1, _arg2, _arg3);
        res.setCanonicText(_arg4);
        res.tag = _arg5;
        res.tag2 = _arg6;
        return res;
    }

    public static Termin _new2961(String _arg1, String _arg2, Object _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.tag = _arg3;
        res.tag2 = _arg4;
        return res;
    }

    public static Termin _new2964(String _arg1, String _arg2, String _arg3, Object _arg4, Object _arg5, boolean _arg6) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.acronym = _arg3;
        res.tag = _arg4;
        res.tag2 = _arg5;
        res.acronymCanBeLower = _arg6;
        return res;
    }

    public static Termin _new2989(String _arg1, String _arg2, Object _arg3) {
        Termin res = new Termin(_arg1, null, false);
        res.acronym = _arg2;
        res.tag = _arg3;
        return res;
    }

    public static Termin _new3000(String _arg1, String _arg2, String _arg3, Object _arg4) {
        Termin res = new Termin(_arg1, null, false);
        res.setCanonicText(_arg2);
        res.acronym = _arg3;
        res.tag = _arg4;
        return res;
    }

    public Termin() {
        this(null, null, false);
    }
    public static Termin _globalInstance;
    
    static {
        try { _globalInstance = new Termin(); } 
        catch(Exception e) { }
        m_StdAbridePrefixes = new String[] {"НИЖ", "ВЕРХ", "МАЛ", "БОЛЬШ", "НОВ", "СТАР"};
    }
}
