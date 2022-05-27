/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.keyword;

/**
 * Анализатор ключевых комбинаций. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 */
public class KeywordAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("KEYWORD")
     */
    public static final String ANALYZER_NAME = "KEYWORD";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Ключевые комбинации";
    }


    @Override
    public String getDescription() {
        return "Ключевые слова для различных аналитических систем";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new KeywordAnalyzer();
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ALL"});
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.keyword.internal.KeywordMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEOBJ, com.pullenti.ner.core.internal.ResourceHelper.getBytes("kwobject.png"));
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEPRED, com.pullenti.ner.core.internal.ResourceHelper.getBytes("kwpredicate.png"));
        res.put(com.pullenti.ner.keyword.internal.KeywordMeta.IMAGEREF, com.pullenti.ner.core.internal.ResourceHelper.getBytes("kwreferent.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, KeywordReferent.OBJ_TYPENAME)) 
            return new KeywordReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    // Основная функция выделения телефонов
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        boolean hasDenoms = false;
        for (com.pullenti.ner.Analyzer a : kit.processor.getAnalyzers()) {
            if ((a instanceof com.pullenti.ner.denomination.DenominationAnalyzer) && !a.getIgnoreThisAnalyzer()) 
                hasDenoms = true;
        }
        if (!hasDenoms) {
            com.pullenti.ner.denomination.DenominationAnalyzer a = new com.pullenti.ner.denomination.DenominationAnalyzer();
            a.process(kit);
        }
        java.util.ArrayList<KeywordReferent> li = new java.util.ArrayList<KeywordReferent>();
        StringBuilder tmp = new StringBuilder();
        java.util.ArrayList<String> tmp2 = new java.util.ArrayList<String>();
        int max = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            max++;
        }
        int cur = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext(),cur++) {
            if (t.isIgnored()) 
                continue;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r != null) {
                t = this._addReferents(ad, t, cur, max);
                continue;
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                continue;
            if (!t.chars.isLetter() || (t.getLengthChar() < 3)) 
                continue;
            String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
            if (com.pullenti.unisharp.Utils.stringsEq(term, "ЕСТЬ")) {
                if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && t.getPrevious().getMorph()._getClass().isVerb()) {
                }
                else 
                    continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt = null;
            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0, null);
            if (npt == null) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isVerb() && !mc.isPreposition()) {
                    if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isVerbBe()) 
                        continue;
                    if (t.isValue("МОЧЬ", null) || t.isValue("WOULD", null)) 
                        continue;
                    KeywordReferent kref = KeywordReferent._new1749(KeywordType.PREDICATE);
                    String norm = t.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (norm == null) 
                        norm = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).lemma;
                    if (norm.endsWith("ЬСЯ")) 
                        norm = norm.substring(0, 0 + norm.length() - 2);
                    kref.addSlot(KeywordReferent.ATTR_VALUE, norm, false, 0);
                    java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> drv = com.pullenti.semantic.utils.DerivateService.findDerivates(norm, true, t.getMorph().getLanguage());
                    _addNormals(kref, drv, norm);
                    kref = (KeywordReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                    _setRank(kref, cur, max);
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(ad.registerReferent(kref), t, t, t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    continue;
                }
                continue;
            }
            if (npt.internalNoun != null) 
                continue;
            if (npt.getEndToken().isValue("ЦЕЛОМ", null) || npt.getEndToken().isValue("ЧАСТНОСТИ", null)) {
                if (npt.preposition != null) {
                    t = npt.getEndToken();
                    continue;
                }
            }
            if (npt.getEndToken().isValue("СТОРОНЫ", null) && npt.preposition != null && com.pullenti.unisharp.Utils.stringsEq(npt.preposition.normal, "С")) {
                t = npt.getEndToken();
                continue;
            }
            if (npt.getBeginToken() == npt.getEndToken()) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isPreposition()) 
                    continue;
                else if (mc.isAdverb()) {
                    if (t.isValue("ПОТОМ", null)) 
                        continue;
                }
            }
            else {
            }
            li.clear();
            com.pullenti.ner.Token t0 = t;
            for (com.pullenti.ner.Token tt = t; tt != null && tt.getEndChar() <= npt.getEndChar(); tt = tt.getNext()) {
                if (!(tt instanceof com.pullenti.ner.TextToken)) 
                    continue;
                if (tt.isValue("NATURAL", null)) {
                }
                if ((tt.getLengthChar() < 3) || !tt.chars.isLetter()) 
                    continue;
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if ((mc.isPreposition() || mc.isPronoun() || mc.isPersonalPronoun()) || mc.isConjunction()) {
                    if (tt.isValue("ОТНОШЕНИЕ", null)) {
                    }
                    else 
                        continue;
                }
                if (mc.isMisc()) {
                    if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
                        continue;
                }
                KeywordReferent kref = KeywordReferent._new1749(KeywordType.OBJECT);
                String norm = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
                kref.addSlot(KeywordReferent.ATTR_VALUE, norm, false, 0);
                if (com.pullenti.unisharp.Utils.stringsNe(norm, "ЕСТЬ")) {
                    java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> drv = com.pullenti.semantic.utils.DerivateService.findDerivates(norm, true, tt.getMorph().getLanguage());
                    _addNormals(kref, drv, norm);
                }
                kref = (KeywordReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                _setRank(kref, cur, max);
                com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(kref, tt, tt, tt.getMorph());
                kit.embedToken(rt1);
                if (tt == t && li.size() == 0) 
                    t0 = rt1;
                t = rt1;
                li.add(kref);
            }
            if (li.size() > 1) {
                KeywordReferent kref = KeywordReferent._new1749(KeywordType.OBJECT);
                tmp.setLength(0);
                tmp2.clear();
                boolean hasNorm = false;
                for (KeywordReferent kw : li) {
                    String s = kw.getStringValue(KeywordReferent.ATTR_VALUE);
                    if (tmp.length() > 0) 
                        tmp.append(' ');
                    tmp.append(s);
                    String n = kw.getStringValue(KeywordReferent.ATTR_NORMAL);
                    if (n != null) {
                        hasNorm = true;
                        tmp2.add(n);
                    }
                    else 
                        tmp2.add(s);
                    kref.addSlot(KeywordReferent.ATTR_REF, kw, false, 0);
                }
                String val = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                kref.addSlot(KeywordReferent.ATTR_VALUE, val, false, 0);
                tmp.setLength(0);
                java.util.Collections.sort(tmp2);
                for (String s : tmp2) {
                    if (tmp.length() > 0) 
                        tmp.append(' ');
                    tmp.append(s);
                }
                String norm = tmp.toString();
                if (com.pullenti.unisharp.Utils.stringsNe(norm, val)) 
                    kref.addSlot(KeywordReferent.ATTR_NORMAL, norm, false, 0);
                kref = (KeywordReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(kref), KeywordReferent.class);
                _setRank(kref, cur, max);
                com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(kref, t0, t, npt.getMorph());
                kit.embedToken(rt1);
                t = rt1;
            }
        }
        cur = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext(),cur++) {
            if (t.isIgnored()) 
                continue;
            KeywordReferent kw = (KeywordReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), KeywordReferent.class);
            if (kw == null || kw.getTyp() != KeywordType.OBJECT) 
                continue;
            if (t.getNext() == null || kw.getChildWords() > 2) 
                continue;
            com.pullenti.ner.Token t1 = t.getNext();
            if (t1.isValue("OF", null) && (t1.getWhitespacesAfterCount() < 3) && t1.getNext() != null) {
                t1 = t1.getNext();
                if ((t1 instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.MiscHelper.isEngArticle(t1) && t1.getNext() != null) 
                    t1 = t1.getNext();
            }
            else if (!t1.getMorph().getCase().isGenitive() || t.getWhitespacesAfterCount() > 1) 
                continue;
            KeywordReferent kw2 = (KeywordReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), KeywordReferent.class);
            if (kw2 == null) 
                continue;
            if (kw == kw2) 
                continue;
            if (kw2.getTyp() != KeywordType.OBJECT || (kw.getChildWords() + kw2.getChildWords()) > 3) 
                continue;
            KeywordReferent kwUn = new KeywordReferent();
            kwUn.union(kw, kw2, com.pullenti.ner.core.MiscHelper.getTextValue(t1, t1, com.pullenti.ner.core.GetTextAttr.NO));
            kwUn = (KeywordReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(kwUn), KeywordReferent.class);
            _setRank(kwUn, cur, max);
            com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(kwUn, t, t1, t.getMorph());
            kit.embedToken(rt1);
            t = rt1;
        }
        if (SORTKEYWORDSBYRANK) {
            java.util.ArrayList<com.pullenti.ner.Referent> all = new java.util.ArrayList<com.pullenti.ner.Referent>(ad.getReferents());
            // PYTHON: sort(key=attrgetter('rank'), reverse=True)
            all.sort(new CompByRank());
            ad.setReferents(all);
        }
        if (ANNOTATIONMAXSENTENCES > 0) {
            KeywordReferent ano = com.pullenti.ner.keyword.internal.AutoannoSentToken.createAnnotation(kit, ANNOTATIONMAXSENTENCES);
            if (ano != null) 
                ad.registerReferent(ano);
        }
    }

    private static int _calcRank(com.pullenti.semantic.utils.DerivateGroup gr) {
        if (gr.isDummy) 
            return 0;
        int res = 0;
        for (com.pullenti.semantic.utils.DerivateWord w : gr.words) {
            if (w.lang.isRu() && w._class != null) {
                if (w._class.isVerb() && w._class.isAdjective()) {
                }
                else 
                    res++;
            }
        }
        if (gr.prefix == null) 
            res += 3;
        return res;
    }

    private static void _addNormals(KeywordReferent kref, java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs, String norm) {
        if (grs == null || grs.size() == 0) 
            return;
        for (int k = 0; k < grs.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (grs.size() - 1); i++) {
                if (_calcRank(grs.get(i)) < _calcRank(grs.get(i + 1))) {
                    com.pullenti.semantic.utils.DerivateGroup gr = grs.get(i);
                    com.pullenti.unisharp.Utils.putArrayValue(grs, i, grs.get(i + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(grs, i + 1, gr);
                    ch = true;
                }
            }
            if (!ch) 
                break;
        }
        for (int i = 0; (i < 3) && (i < grs.size()); i++) {
            if (!grs.get(i).isDummy && grs.get(i).words.size() > 0) {
                if (com.pullenti.unisharp.Utils.stringsNe(grs.get(i).words.get(0).spelling, norm)) 
                    kref.addSlot(KeywordReferent.ATTR_NORMAL, grs.get(i).words.get(0).spelling, false, 0);
            }
        }
    }

    public static class CompByRank implements java.util.Comparator<com.pullenti.ner.Referent> {
    
        @Override
        public int compare(com.pullenti.ner.Referent x, com.pullenti.ner.Referent y) {
            double d1 = ((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.unisharp.Utils.cast(x, com.pullenti.ner.keyword.KeywordReferent.class)).rank;
            double d2 = ((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.unisharp.Utils.cast(y, com.pullenti.ner.keyword.KeywordReferent.class)).rank;
            if (d1 > d2) 
                return -1;
            if (d1 < d2) 
                return 1;
            return 0;
        }
        public CompByRank() {
        }
    }


    private com.pullenti.ner.Token _addReferents(com.pullenti.ner.core.AnalyzerData ad, com.pullenti.ner.Token t, int cur, int max) {
        if (!(t instanceof com.pullenti.ner.ReferentToken)) 
            return t;
        com.pullenti.ner.Referent r = t.getReferent();
        if (r == null) 
            return t;
        if (r instanceof com.pullenti.ner.denomination.DenominationReferent) {
            com.pullenti.ner.denomination.DenominationReferent dr = (com.pullenti.ner.denomination.DenominationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.denomination.DenominationReferent.class);
            KeywordReferent kref0 = KeywordReferent._new1749(KeywordType.REFERENT);
            for (com.pullenti.ner.Slot s : dr.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.denomination.DenominationReferent.ATTR_VALUE)) 
                    kref0.addSlot(KeywordReferent.ATTR_NORMAL, s.getValue(), false, 0);
            }
            kref0.addSlot(KeywordReferent.ATTR_REF, dr, false, 0);
            com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(ad.registerReferent(kref0), t, t, null);
            t.kit.embedToken(rt0);
            return rt0;
        }
        if ((r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) 
            return t;
        if (r instanceof com.pullenti.ner.money.MoneyReferent) {
            com.pullenti.ner.money.MoneyReferent mr = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.money.MoneyReferent.class);
            KeywordReferent kref0 = KeywordReferent._new1749(KeywordType.OBJECT);
            kref0.addSlot(KeywordReferent.ATTR_NORMAL, mr.getCurrency(), false, 0);
            com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(ad.registerReferent(kref0), t, t, null);
            t.kit.embedToken(rt0);
            return rt0;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "BOOKLINKREF")) 
            return t;
        for (com.pullenti.ner.Token tt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
            if (tt instanceof com.pullenti.ner.ReferentToken) 
                this._addReferents(ad, tt, cur, max);
        }
        KeywordReferent kref = KeywordReferent._new1749(KeywordType.REFERENT);
        String norm = null;
        if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO")) 
            norm = r.getStringValue("ALPHA2");
        if (norm == null) 
            norm = r.toStringEx(true, null, 0);
        if (norm != null) 
            kref.addSlot(KeywordReferent.ATTR_NORMAL, norm.toUpperCase(), false, 0);
        kref.addSlot(KeywordReferent.ATTR_REF, t.getReferent(), false, 0);
        _setRank(kref, cur, max);
        com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(ad.registerReferent(kref), t, t, null);
        t.kit.embedToken(rt1);
        return rt1;
    }

    private static void _setRank(KeywordReferent kr, int cur, int max) {
        double rank = 1.0;
        KeywordType ty = kr.getTyp();
        if (ty == KeywordType.PREDICATE) 
            rank = 1.0;
        else if (ty == KeywordType.OBJECT) {
            String v = (String)com.pullenti.unisharp.Utils.notnull(kr.getStringValue(KeywordReferent.ATTR_VALUE), kr.getStringValue(KeywordReferent.ATTR_NORMAL));
            if (v != null) {
                for (int i = 0; i < v.length(); i++) {
                    if (v.charAt(i) == ' ' || v.charAt(i) == '-') 
                        rank++;
                }
            }
        }
        else if (ty == KeywordType.REFERENT) {
            rank = 3.0;
            com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(kr.getSlotValue(KeywordReferent.ATTR_REF), com.pullenti.ner.Referent.class);
            if (r != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PERSON")) 
                    rank = 4.0;
            }
        }
        if (max > 0) 
            rank *= (((1.0) - (((0.5 * ((double)cur)) / ((double)max)))));
        kr.rank += rank;
    }

    /**
     * Сортировать ли в списке Entities ключевые слова в порядке убывания ранга
     */
    public static boolean SORTKEYWORDSBYRANK = true;

    /**
     * Максимально предложений в автоаннотацию (KeywordReferent с типом Annotation). 
     * Если 0, то не делать автоаннотацию. По умолчанию = 3.
     */
    public static int ANNOTATIONMAXSENTENCES = 3;

    private static boolean m_Initialized = false;

    public static void initialize() throws Exception {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        try {
            com.pullenti.ner.keyword.internal.KeywordMeta.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.denomination.DenominationAnalyzer.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
            com.pullenti.ner.ProcessorService.registerAnalyzer(new KeywordAnalyzer());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }
    public KeywordAnalyzer() {
        super();
    }
    public static KeywordAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new KeywordAnalyzer(); } 
        catch(Exception e) { }
    }
}
