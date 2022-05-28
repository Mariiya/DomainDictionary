/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition;

/**
 * Анализатор определений. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 */
public class DefinitionAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("THESIS")
     */
    public static final String ANALYZER_NAME = "THESIS";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Тезисы";
    }


    @Override
    public String getDescription() {
        return "Утверждения и определения";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new DefinitionAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.definition.internal.MetaDefin.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.definition.internal.MetaDefin.IMAGEDEFID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("defin.png"));
        res.put(com.pullenti.ner.definition.internal.MetaDefin.IMAGEASSID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("assert.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, DefinitionReferent.OBJ_TYPENAME)) 
            return new DefinitionReferent();
        return null;
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
        return new com.pullenti.ner.core.AnalyzerData();
    }

    // Основная функция выделения объектов
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        if (kit.baseLanguage.equals(com.pullenti.morph.MorphLang.EN)) {
            com.pullenti.ner.definition.internal.DefinitionAnalyzerEn.process(kit, ad);
            return;
        }
        boolean glosRegime = false;
        com.pullenti.ner.core.TerminCollection onto = null;
        java.util.HashMap<String, Boolean> oh = new java.util.HashMap<String, Boolean>();
        if (kit.ontology != null) {
            onto = new com.pullenti.ner.core.TerminCollection();
            for (com.pullenti.ner.ExtOntologyItem it : kit.ontology.items) {
                if (it.referent instanceof DefinitionReferent) {
                    String termin = it.referent.getStringValue(DefinitionReferent.ATTR_TERMIN);
                    if (!oh.containsKey(termin)) {
                        oh.put(termin, true);
                        onto.add(com.pullenti.ner.core.Termin._new1189(termin, termin));
                    }
                }
            }
            if (onto.termins.size() == 0) 
                onto = null;
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            if (!glosRegime && t.isNewlineBefore()) {
                com.pullenti.ner.Token tt = _tryAttachGlossary(t);
                if (tt != null) {
                    t = tt;
                    glosRegime = true;
                    continue;
                }
            }
            int maxChar = 0;
            boolean ok = false;
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                ok = true;
            else if (((t.isValue("ЧТО", null) && t.getNext() != null && t.getPrevious() != null) && t.getPrevious().isComma() && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB)) {
                ok = true;
                t = t.getNext();
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                    t = t.getNext();
            }
            else if (t.isNewlineBefore() && glosRegime) 
                ok = true;
            else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) && t.getPrevious() != null && t.getPrevious().isChar(':')) {
                ok = true;
                t = t.getNext();
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, true, t, false)) {
                        maxChar = tt.getPrevious().getEndChar();
                        break;
                    }
                }
            }
            else if (t.isNewlineBefore() && t.getPrevious() != null && t.getPrevious().isCharOf(";:")) 
                ok = true;
            if (!ok) 
                continue;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> prs = tryAttach(t, glosRegime, onto, maxChar, false);
            if (prs == null) 
                prs = this.tryAttachEnd(t, onto, maxChar);
            if (prs != null) {
                for (com.pullenti.ner.ReferentToken pr : prs) {
                    if (pr.referent != null) {
                        pr.referent = ad.registerReferent(pr.referent);
                        pr.referent.addOccurenceOfRefTok(pr);
                    }
                    t = pr.getEndToken();
                }
            }
            else {
                if (t.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        t = br.getEndToken();
                        continue;
                    }
                }
                boolean ign = false;
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) {
                        if (tt.getPrevious().isChar(';')) 
                            ign = true;
                        break;
                    }
                }
                if (glosRegime && !t.isNewlineBefore()) {
                }
                else if (!ign) 
                    glosRegime = false;
            }
        }
    }

    private static com.pullenti.ner.Token _tryAttachGlossary(com.pullenti.ner.Token t) {
        if (t == null || !t.isNewlineBefore()) 
            return null;
        for (; t != null; t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) 
                break;
        }
        if (t == null) 
            return null;
        if (t.isValue("ГЛОССАРИЙ", null) || t.isValue("ОПРЕДЕЛЕНИЕ", null)) 
            t = t.getNext();
        else if (t.isValue("СПИСОК", null) && t.getNext() != null && t.getNext().isValue("ОПРЕДЕЛЕНИЕ", null)) 
            t = t.getNext().getNext();
        else {
            boolean use = false;
            boolean ponat = false;
            com.pullenti.ner.Token t0 = t;
            for (; t != null; t = t.getNext()) {
                if (t.isValue("ИСПОЛЬЗОВАТЬ", null)) 
                    use = true;
                else if (t.isValue("ПОНЯТИЕ", null) || t.isValue("ОПРЕДЕЛЕНИЕ", null)) 
                    ponat = true;
                else if (t.isChar(':')) {
                    if (use && ponat && t.isNewlineAfter()) 
                        return t;
                }
                else if (t != t0 && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                    break;
            }
            return null;
        }
        if (t == null) 
            return null;
        if (t.isAnd() && t.getNext() != null && t.getNext().isValue("СОКРАЩЕНИЕ", null)) 
            t = t.getNext().getNext();
        if (t != null && t.isCharOf(":.")) 
            t = t.getNext();
        if (t != null && t.isNewlineBefore()) 
            return t.getPrevious();
        return null;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> li = tryAttach(begin, false, null, 0, false);
        if (li == null || li.size() == 0) 
            return null;
        return li.get(0);
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (begin == null) 
            return null;
        com.pullenti.ner.Token t1 = null;
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            if (t.isHiphen() && ((t.isWhitespaceBefore() || t.isWhitespaceAfter()))) 
                break;
            else 
                t1 = t;
        }
        if (t1 == null) 
            return null;
        DefinitionReferent dre = new DefinitionReferent();
        dre.addSlot(DefinitionReferent.ATTR_TERMIN, com.pullenti.ner.core.MiscHelper.getTextValue(begin, t1, com.pullenti.ner.core.GetTextAttr.NO), false, 0);
        return new com.pullenti.ner.ReferentToken(dre, begin, t1, null);
    }

    private static com.pullenti.ner.Token _ignoreListPrefix(com.pullenti.ner.Token t) {
        for (; t != null; t = t.getNext()) {
            if (t.isNewlineAfter()) 
                break;
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS) 
                    break;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0, null);
                if (npt != null && npt.getEndChar() > t.getEndChar()) 
                    break;
                continue;
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!t.chars.isLetter()) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                    break;
                continue;
            }
            if (t.getLengthChar() == 1 && t.getNext() != null && t.getNext().isCharOf(").")) 
                continue;
            break;
        }
        return t;
    }

    public static java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(com.pullenti.ner.Token t, boolean glosRegime, com.pullenti.ner.core.TerminCollection onto, int maxChar, boolean thisIsDef) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        t = _ignoreListPrefix(t);
        if (t == null) 
            return null;
        boolean hasPrefix = false;
        if (t0 != t) 
            hasPrefix = true;
        t0 = t;
        com.pullenti.ner.Referent _decree = null;
        com.pullenti.ner.definition.internal.ParenthesisToken pt = com.pullenti.ner.definition.internal.ParenthesisToken.tryAttach(t);
        if (pt != null) {
            _decree = pt.ref;
            t = pt.getEndToken().getNext();
            if (t != null && t.isChar(',')) 
                t = t.getNext();
        }
        if (t == null) 
            return null;
        com.pullenti.ner.Token l0 = null;
        com.pullenti.ner.Token l1 = null;
        String altName = null;
        String name0 = null;
        boolean normalLeft = false;
        boolean canNextSent = false;
        DefinitionKind coef = DefinitionKind.UNDEFINED;
        if (glosRegime) 
            coef = DefinitionKind.DEFINITION;
        boolean isOntoTermin = false;
        String ontoPrefix = null;
        if (t.isValue("ПОД", null)) {
            t = t.getNext();
            normalLeft = true;
        }
        else if (t.isValue("ИМЕННО", null)) 
            t = t.getNext();
        if ((t != null && t.isValue("УТРАТИТЬ", null) && t.getNext() != null) && t.getNext().isValue("СИЛА", null)) {
            for (; t != null; t = t.getNext()) {
                if (t.isNewlineAfter()) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> re0 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                    re0.add(new com.pullenti.ner.ReferentToken(null, t0, t, null));
                    return re0;
                }
            }
            return null;
        }
        com.pullenti.ner.MetaToken miscToken = null;
        for (; t != null; t = t.getNext()) {
            if (t != t0 && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                break;
            if (maxChar > 0 && t.getEndChar() > maxChar) 
                break;
            com.pullenti.ner.MetaToken mt = _tryAttachMiscToken(t);
            if (mt != null) {
                miscToken = mt;
                t = mt.getEndToken();
                normalLeft = mt.getMorph().getCase().isNominative();
                continue;
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.Referent r = t.getReferent();
                if (r != null && ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREEPART")))) {
                    _decree = r;
                    if (l0 == null) {
                        if ((t.getNext() != null && t.getNext().getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB) && t.getNext().getNext() != null) && t.getNext().getNext().isComma()) {
                            t = t.getNext().getNext();
                            if (t.getNext() != null && t.getNext().isValue("ЧТО", null)) 
                                t = t.getNext();
                            continue;
                        }
                        l0 = t;
                    }
                    l1 = t;
                    continue;
                }
                if (r != null && (((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PERSONPROPERTY") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "STREET")) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO")))) {
                    if (l0 == null) 
                        l0 = t;
                    l1 = t;
                    continue;
                }
                if ((t instanceof com.pullenti.ner.NumberToken) && com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null) != null) {
                }
                else 
                    break;
            }
            pt = com.pullenti.ner.definition.internal.ParenthesisToken.tryAttach(t);
            if (pt != null && pt.ref != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(pt.ref.getTypeName(), "DECREE") || com.pullenti.unisharp.Utils.stringsEq(pt.ref.getTypeName(), "DECREEPART")) 
                    _decree = pt.ref;
                t = pt.getEndToken().getNext();
                if (l0 == null) 
                    continue;
                break;
            }
            if (!t.chars.isLetter()) {
                if (t.isHiphen()) {
                    if (t.isWhitespaceAfter() || t.isWhitespaceBefore()) 
                        break;
                    continue;
                }
                if (t.isChar('(')) {
                    if (l1 == null) 
                        break;
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br == null) 
                        break;
                    com.pullenti.ner.Token tt1 = t.getNext();
                    if (tt1.isValue("ДАЛЕЕ", null)) {
                        tt1 = tt1.getNext();
                        if (!tt1.chars.isLetter()) 
                            tt1 = tt1.getNext();
                        if (tt1 == null) 
                            return null;
                    }
                    altName = com.pullenti.ner.core.MiscHelper.getTextValue(tt1, br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                    if (br.getBeginToken().getNext() == br.getEndToken().getPrevious()) {
                        t = br.getEndToken();
                        continue;
                    }
                    t = br.getEndToken().getNext();
                    break;
                }
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && l0 == null && com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null) != null) {
                        l0 = t.getNext();
                        l1 = br.getEndToken().getPrevious();
                        altName = null;
                        t = br.getEndToken().getNext();
                    }
                    else if (br != null && l0 != null) {
                        l1 = br.getEndToken();
                        altName = null;
                        t = br.getEndToken();
                        continue;
                    }
                }
                break;
            }
            if (t.isValue("ЭТО", null)) 
                break;
            if (t.getMorph()._getClass().isConjunction()) {
                if (!glosRegime || !t.isAnd()) 
                    break;
                continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt;
            if (t.isValue("ДАВАТЬ", null) || t.isValue("ДАТЬ", null) || t.isValue("ФОРМУЛИРОВАТЬ", null)) {
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.noun.isValue("ОПРЕДЕЛЕНИЕ", null)) {
                    t = npt.getEndToken();
                    if (t.getNext() != null && t.getNext().isValue("ПОНЯТИЕ", null)) 
                        t = t.getNext();
                    l0 = null;
                    l1 = null;
                    normalLeft = true;
                    canNextSent = true;
                    coef = DefinitionKind.DEFINITION;
                    continue;
                }
            }
            altName = null;
            if (onto != null) {
                com.pullenti.ner.core.TerminToken took = onto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (took != null) {
                    if (l0 != null) {
                        if (ontoPrefix != null) 
                            break;
                        ontoPrefix = com.pullenti.ner.core.MiscHelper.getTextValue(l0, l1, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                    }
                    if (!isOntoTermin) {
                        isOntoTermin = true;
                        l0 = t;
                    }
                    name0 = took.termin.getCanonicText();
                    t = (l1 = took.getEndToken());
                    continue;
                }
            }
            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
            if (npt != null && npt.internalNoun != null) 
                break;
            if (npt == null) {
                if (l0 != null) 
                    break;
                if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isVerb()) 
                    break;
                if (t.getMorph()._getClass().isAdjective()) {
                    com.pullenti.ner.Token tt;
                    int ve = 0;
                    for (tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getMorphClassInDictionary().isVerb()) 
                            ve++;
                        else 
                            break;
                    }
                    if ((ve > 0 && tt != null && tt.isValue("ТАКОЙ", null)) && com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null) != null) {
                        l0 = (l1 = t);
                        t = t.getNext();
                        break;
                    }
                }
                if (!t.chars.isAllLower() && t.getLengthChar() > 2 && t.getMorphClassInDictionary().isUndefined()) {
                }
                else 
                    continue;
            }
            if (l0 == null) {
                if (t.getMorph()._getClass().isPreposition()) 
                    break;
                if (m_VerbotFirstWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null && onto == null) 
                    break;
                l0 = t;
            }
            else if (t.getMorph()._getClass().isPreposition()) {
                if (m_VerbotLastWords.tryParse(npt.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null || m_VerbotLastWords.tryParse(npt.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                    t = npt.getEndToken().getNext();
                    break;
                }
            }
            if (npt != null) {
                if (m_VerbotFirstWords.tryParse(npt.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null && onto == null) 
                    break;
                boolean ok1 = true;
                if (!glosRegime) {
                    for (com.pullenti.ner.Token tt = npt.getBeginToken(); tt != null && tt.getEndChar() <= npt.getEndChar(); tt = tt.getNext()) {
                        if (tt.getMorph()._getClass().isPronoun() || tt.getMorph()._getClass().isPersonalPronoun()) {
                            if (tt.isValue("ИНОЙ", null)) {
                            }
                            else {
                                ok1 = false;
                                break;
                            }
                        }
                    }
                }
                if (!ok1) 
                    break;
                t = (l1 = npt.getEndToken());
            }
            else 
                l1 = t;
        }
        if (!(t instanceof com.pullenti.ner.TextToken) || ((l1 == null && !isOntoTermin)) || t.getNext() == null) 
            return null;
        if (onto != null && name0 == null) 
            return null;
        boolean isNot = false;
        com.pullenti.ner.Token r0 = t;
        com.pullenti.ner.Token r1 = null;
        if (t.isValue("НЕ", null)) {
            t = t.getNext();
            if (t == null) 
                return null;
            isNot = true;
        }
        boolean normalRight = false;
        int ok = 0;
        boolean hasthis = false;
        if (t.isHiphen() || t.isCharOf(":") || ((canNextSent && t.isChar('.')))) {
            if ((t.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term, "ЭТО")) {
                ok = 2;
                t = t.getNext().getNext();
                hasthis = true;
            }
            else if (glosRegime) {
                ok = 2;
                t = t.getNext();
            }
            else if (isOntoTermin) {
                ok = 1;
                t = t.getNext();
            }
            else if (t.isHiphen() && t.isWhitespaceBefore() && t.isWhitespaceAfter()) {
                com.pullenti.ner.Token tt = t.getNext();
                if (tt != null && tt.isValue("НЕ", null)) {
                    isNot = true;
                    tt = tt.getNext();
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getMorph().getCase().isNominative()) {
                    ok = 2;
                    t = tt;
                }
                else if ((tt != null && tt.getMorph().getCase().isNominative() && tt.getMorph()._getClass().isVerb()) && tt.getMorph()._getClass().isAdjective()) {
                    ok = 2;
                    t = tt;
                }
            }
            else {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rt0 = tryAttach(t.getNext(), false, null, maxChar, false);
                if (rt0 != null) {
                    for (com.pullenti.ner.ReferentToken rt : rt0) {
                        if (coef == DefinitionKind.DEFINITION && ((DefinitionReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DefinitionReferent.class)).getKind() == DefinitionKind.ASSERTATION) 
                            ((DefinitionReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DefinitionReferent.class)).setKind(coef);
                    }
                    return rt0;
                }
            }
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ЭТО")) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                ok = 1;
                t = t.getNext();
                hasthis = true;
            }
        }
        else if (t.isValue("ЯВЛЯТЬСЯ", null) || t.isValue("ПРИЗНАВАТЬСЯ", null) || t.isValue("ЕСТЬ", null)) {
            if (t.isValue("ЯВЛЯТЬСЯ", null)) 
                normalRight = true;
            com.pullenti.ner.Token t11 = t.getNext();
            for (; t11 != null; t11 = t11.getNext()) {
                if (t11.isComma() || t11.getMorph()._getClass().isPreposition() || t11.getMorph()._getClass().isConjunction()) {
                }
                else 
                    break;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null || t11.getMorphClassInDictionary().isAdjective()) {
                ok = 1;
                t = t11;
                normalLeft = true;
            }
            else if ((t11 != null && t11.isValue("ОДИН", null) && t11.getNext() != null) && t11.getNext().isValue("ИЗ", null)) {
                ok = 1;
                t = t11;
                normalLeft = true;
            }
            if (isOntoTermin) 
                ok = 1;
            else if (l0 == l1 && npt != null && l0.getMorph()._getClass().isAdjective()) {
                if (((short)((l0.getMorph().getGender().value()) & (npt.getMorph().getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) || ((short)((l0.getMorph().getNumber().value()) & (npt.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) 
                    name0 = (l0.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, npt.getMorph().getGender(), false) + " " + npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, npt.getMorph().getGender(), false));
                else 
                    ok = 0;
            }
        }
        else if (t.isValue("ОЗНАЧАТЬ", null) || t.isValue("НЕСТИ", null)) {
            com.pullenti.ner.Token t11 = t.getNext();
            if (t11 != null && t11.isChar(':')) 
                t11 = t11.getNext();
            if (t11.isValue("НЕ", null) && t11.getNext() != null) {
                isNot = true;
                t11 = t11.getNext();
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null || isOntoTermin) {
                ok = 1;
                t = t11;
            }
        }
        else if (t.isValue("ВЫРАЖАТЬ", null)) {
            com.pullenti.ner.Token t11 = t.getNext();
            for (; t11 != null; t11 = t11.getNext()) {
                if ((t11.getMorph()._getClass().isPronoun() || t11.isComma() || t11.getMorph()._getClass().isPreposition()) || t11.getMorph()._getClass().isConjunction()) {
                }
                else 
                    break;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null || isOntoTermin) {
                ok = 1;
                t = t11;
            }
        }
        else if (((t.isValue("СЛЕДОВАТЬ", null) || t.isValue("МОЖНО", null))) && t.getNext() != null && ((t.getNext().isValue("ПОНИМАТЬ", null) || t.getNext().isValue("ОПРЕДЕЛИТЬ", null) || t.getNext().isValue("СЧИТАТЬ", null)))) {
            com.pullenti.ner.Token t11 = t.getNext().getNext();
            if (t11 == null) 
                return null;
            if (t11.isValue("КАК", null)) 
                t11 = t11.getNext();{
                    ok = 2;
                    t = t11;
                }
        }
        else if (t.isValue("ПРЕДСТАВЛЯТЬ", null) && t.getNext() != null && t.getNext().isValue("СОБОЙ", null)) {
            com.pullenti.ner.Token t11 = t.getNext().getNext();
            if (t11 == null) 
                return null;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null || t11.getMorph()._getClass().isAdjective() || isOntoTermin) {
                ok = 1;
                t = t11;
            }
        }
        else if ((((t.isValue("ДОЛЖЕН", null) || t.isValue("ДОЛЖНЫЙ", null))) && t.getNext() != null && t.getNext().isValue("ПРЕДСТАВЛЯТЬ", null)) && t.getNext().getNext() != null && t.getNext().getNext().isValue("СОБОЙ", null)) {
            com.pullenti.ner.Token t11 = t.getNext().getNext().getNext();
            if (t11 == null) 
                return null;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null || t11.getMorph()._getClass().isAdjective() || isOntoTermin) {
                ok = 1;
                t = t11;
            }
        }
        else if (t.isValue("ДОЛЖНЫЙ", null)) {
            if (t.getNext() != null && t.getNext().getMorph()._getClass().isVerb()) 
                t = t.getNext();
            ok = 1;
        }
        else if (((((((((t.isValue("МОЖЕТ", null) || t.isValue("МОЧЬ", null) || t.isValue("ВПРАВЕ", null)) || t.isValue("ЗАПРЕЩЕНО", null) || t.isValue("РАЗРЕШЕНО", null)) || t.isValue("ОТВЕЧАТЬ", null) || t.isValue("ПРИЗНАВАТЬ", null)) || t.isValue("ОСВОБОЖДАТЬ", null) || t.isValue("ОСУЩЕСТВЛЯТЬ", null)) || t.isValue("ПРОИЗВОДИТЬ", null) || t.isValue("ПОДЛЕЖАТЬ", null)) || t.isValue("ПРИНИМАТЬ", null) || t.isValue("СЧИТАТЬ", null)) || t.isValue("ИМЕТЬ", null) || t.isValue("ВПРАВЕ", null)) || t.isValue("ОБЯЗАН", null) || t.isValue("ОБЯЗАТЬ", null))) 
            ok = 1;
        if (ok == 0) 
            return null;
        if (t == null) 
            return null;
        if (t.isValue("НЕ", null)) {
            if (!isOntoTermin) 
                return null;
        }
        DefinitionReferent dr = new DefinitionReferent();
        normalLeft = true;
        String nam = (name0 != null ? name0 : com.pullenti.ner.core.MiscHelper.getTextValue(l0, l1, (normalLeft ? com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE : com.pullenti.ner.core.GetTextAttr.NO)));
        if (nam == null) 
            return null;
        if (name0 == null) {
        }
        if (name0 == null) 
            dr.tag = com.pullenti.ner.MetaToken._new899(l0, l1, normalLeft);
        if (l0 == l1 && l0.getMorph()._getClass().isAdjective() && l0.getMorph().getCase().isInstrumental()) {
            if (t != null && t.isValue("ТАКОЙ", null)) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getMorph().getCase().isNominative()) {
                    String str = l0.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, (npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL ? com.pullenti.morph.MorphNumber.SINGULAR : com.pullenti.morph.MorphNumber.UNDEFINED), npt.getMorph().getGender(), false);
                    if (str == null) 
                        str = l0.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    nam = (str + " " + npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                }
            }
        }
        if (_decree != null) {
            for (com.pullenti.ner.Token tt = l0; tt != null && tt.getEndChar() <= l1.getEndChar(); tt = tt.getNext()) {
                if (tt.getReferent() == _decree) {
                    _decree = null;
                    break;
                }
            }
        }
        if (nam.endsWith(")") && altName == null) {
            int ii = nam.lastIndexOf('(');
            if (ii > 0) {
                altName = nam.substring(ii + 1, ii + 1 + nam.length() - ii - 2).trim();
                nam = nam.substring(0, 0 + ii).trim();
            }
        }
        dr.addSlot(DefinitionReferent.ATTR_TERMIN, nam, false, 0);
        if (altName != null) 
            dr.addSlot(DefinitionReferent.ATTR_TERMIN, altName, false, 0);
        if (!isOntoTermin) {
            com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(l0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt2 != null && npt2.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(l0, l1, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE);
                if (nam != null) 
                    dr.addSlot(DefinitionReferent.ATTR_TERMIN, nam, false, 0);
            }
        }
        if (miscToken != null) {
            if (miscToken.getMorph()._getClass().isNoun()) 
                dr.addSlot(DefinitionReferent.ATTR_TERMIN_ADD, (String)com.pullenti.unisharp.Utils.cast(miscToken.tag, String.class), false, 0);
            else 
                dr.addSlot(DefinitionReferent.ATTR_MISC, (String)com.pullenti.unisharp.Utils.cast(miscToken.tag, String.class), false, 0);
        }
        com.pullenti.ner.Token t1 = null;
        java.util.ArrayList<com.pullenti.ner.MetaToken> multiParts = null;
        for (; t != null; t = t.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                break;
            if (maxChar > 0 && t.getEndChar() > maxChar) 
                break;
            t1 = t;
            if (t.isChar('(') && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.Referent r = t.getNext().getReferent();
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREEPART")) {
                    _decree = r;
                    t1 = (t = t.getNext());
                    while (t.getNext() != null) {
                        if (t.getNext().isCommaAnd() && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.unisharp.Utils.stringsEq(t.getNext().getNext().getReferent().getTypeName(), "DECREE") || com.pullenti.unisharp.Utils.stringsEq(t.getNext().getNext().getReferent().getTypeName(), "DECREEPART")))) 
                            t1 = (t = t.getNext().getNext());
                        else 
                            break;
                    }
                    if (t1.getNext() != null && t1.getNext().isChar(')')) 
                        t = (t1 = t1.getNext());
                    continue;
                }
            }
            if (t.isChar('(') && t.getNext() != null && t.getNext().isValue("ДАЛЕЕ", null)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = (t1 = br.getEndToken());
                    continue;
                }
            }
            if (t.isChar(':') && t.isWhitespaceAfter()) {
                com.pullenti.ner.MetaToken mt = _tryParseListItem(t.getNext());
                if (mt != null) {
                    multiParts = new java.util.ArrayList<com.pullenti.ner.MetaToken>();
                    multiParts.add(mt);
                    for (com.pullenti.ner.Token tt = mt.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                        if (maxChar > 0 && tt.getEndChar() > maxChar) 
                            break;
                        mt = _tryParseListItem(tt);
                        if (mt == null) 
                            break;
                        multiParts.add(mt);
                        tt = mt.getEndToken();
                    }
                    break;
                }
            }
            if (!t.isCharOf(";.")) 
                r1 = t;
        }
        if (r1 == null) 
            return null;
        if (r0.getNext() != null && (r0 instanceof com.pullenti.ner.TextToken) && !r0.chars.isLetter()) 
            r0 = r0.getNext();
        normalRight = false;
        String df = com.pullenti.ner.core.MiscHelper.getTextValue(r0, r1, com.pullenti.ner.core.GetTextAttr.of((((normalRight ? com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE : com.pullenti.ner.core.GetTextAttr.NO)).value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        if (multiParts != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res1 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
            dr.setKind((isNot ? DefinitionKind.NEGATION : DefinitionKind.ASSERTATION));
            for (com.pullenti.ner.MetaToken mp : multiParts) {
                com.pullenti.ner.Referent dr1 = dr.clone();
                StringBuilder tmp = new StringBuilder();
                if (df != null) {
                    tmp.append(df);
                    if (tmp.length() > 0 && tmp.charAt(tmp.length() - 1) == ':') 
                        tmp.setLength(tmp.length() - 1);
                    tmp.append(": ");
                    tmp.append(com.pullenti.ner.core.MiscHelper.getTextValue(mp.getBeginToken(), mp.getEndToken(), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                }
                dr1.addSlot(DefinitionReferent.ATTR_VALUE, tmp.toString(), false, 0);
                res1.add(new com.pullenti.ner.ReferentToken(dr1, (res1.size() == 0 ? t0 : mp.getBeginToken()), mp.getEndToken(), null));
            }
            return res1;
        }
        if (df == null || (df.length() < 20)) 
            return null;
        if (ontoPrefix != null) 
            df = (ontoPrefix + " " + df);
        if ((coef == DefinitionKind.UNDEFINED && ok > 1 && !isNot) && multiParts == null) {
            boolean allNps = true;
            int couNpt = 0;
            for (com.pullenti.ner.Token tt = l0; tt != null && tt.getEndChar() <= l1.getEndChar(); tt = tt.getNext()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                if (npt == null && tt.getMorph()._getClass().isPreposition()) 
                    npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt == null) {
                    allNps = false;
                    break;
                }
                couNpt++;
                tt = npt.getEndToken();
            }
            if (allNps && (couNpt < 5)) {
                if ((df.length() / 3) > nam.length()) 
                    coef = DefinitionKind.DEFINITION;
            }
        }
        if ((t1.isChar(';') && t1.isNewlineAfter() && onto != null) && !hasPrefix && multiParts == null) {
            StringBuilder tmp = new StringBuilder();
            tmp.append(df);
            for (t = t1.getNext(); t != null; t = t.getNext()) {
                if (t.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        t = br.getEndToken();
                        continue;
                    }
                }
                com.pullenti.ner.Token tt = _ignoreListPrefix(t);
                if (tt == null) 
                    break;
                com.pullenti.ner.Token tt1 = null;
                for (com.pullenti.ner.Token ttt1 = tt; ttt1 != null; ttt1 = ttt1.getNext()) {
                    if (ttt1.isNewlineAfter()) {
                        tt1 = ttt1;
                        break;
                    }
                }
                if (tt1 == null) 
                    break;
                String df1 = com.pullenti.ner.core.MiscHelper.getTextValue(tt, (tt1.isCharOf(".;") ? tt1.getPrevious() : tt1), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                if (df1 == null) 
                    break;
                tmp.append(";\n ").append(df1);
                t = (t1 = tt1);
                if (!tt1.isChar(';')) 
                    break;
            }
            df = tmp.toString();
        }
        dr.addSlot(DefinitionReferent.ATTR_VALUE, df, false, 0);
        if (isNot) 
            coef = DefinitionKind.NEGATION;
        else if (hasthis && thisIsDef) 
            coef = DefinitionKind.DEFINITION;
        else if (miscToken != null && !miscToken.getMorph()._getClass().isNoun()) 
            coef = DefinitionKind.ASSERTATION;
        if (coef == DefinitionKind.UNDEFINED) 
            coef = DefinitionKind.ASSERTATION;
        if (_decree != null) 
            dr.addSlot(DefinitionReferent.ATTR_DECREE, _decree, false, 0);
        dr.setKind(coef);
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(new com.pullenti.ner.ReferentToken(dr, t0, t1, null));
        return res;
    }

    // Это распознавание случая, когда термин находится в конце
    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttachEnd(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection onto, int maxChar) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        t = _ignoreListPrefix(t);
        if (t == null) 
            return null;
        boolean hasPrefix = false;
        if (t0 != t) 
            hasPrefix = true;
        t0 = t;
        com.pullenti.ner.Referent _decree = null;
        com.pullenti.ner.definition.internal.ParenthesisToken pt = com.pullenti.ner.definition.internal.ParenthesisToken.tryAttach(t);
        if (pt != null) {
            _decree = pt.ref;
            t = pt.getEndToken().getNext();
            if (t != null && t.isChar(',')) 
                t = t.getNext();
        }
        if (t == null) 
            return null;
        com.pullenti.ner.Token r0 = t0;
        com.pullenti.ner.Token r1 = null;
        com.pullenti.ner.Token l0 = null;
        for (; t != null; t = t.getNext()) {
            if (t != t0 && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                break;
            if (maxChar > 0 && t.getEndChar() > maxChar) 
                break;
            if (t.isValue("НАЗЫВАТЬ", null) || t.isValue("ИМЕНОВАТЬ", null)) {
            }
            else 
                continue;
            r1 = t.getPrevious();
            for (com.pullenti.ner.Token tt = r1; tt != null; tt = tt.getPrevious()) {
                if ((tt.isValue("БУДЕМ", null) || tt.isValue("ДАЛЬНЕЙШИЙ", null) || tt.isValue("ДАЛЕЕ", null)) || tt.isValue("В", null)) 
                    r1 = tt.getPrevious();
                else 
                    break;
            }
            l0 = t.getNext();
            for (com.pullenti.ner.Token tt = l0; tt != null; tt = tt.getNext()) {
                if ((tt.isValue("БУДЕМ", null) || tt.isValue("ДАЛЬНЕЙШИЙ", null) || tt.isValue("ДАЛЕЕ", null)) || tt.isValue("В", null)) 
                    l0 = tt.getNext();
                else 
                    break;
            }
            break;
        }
        if (l0 == null || r1 == null) 
            return null;
        com.pullenti.ner.Token l1 = null;
        int cou = 0;
        for (t = l0; t != null; t = t.getNext()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null && t != l0 && t.getMorph()._getClass().isPreposition()) 
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null) 
                break;
            l1 = (t = npt.getEndToken());
            cou++;
        }
        if (l1 == null || cou > 3) 
            return null;
        if ((((l1.getEndChar() - l0.getEndChar())) * 2) > ((r1.getEndChar() - r0.getEndChar()))) 
            return null;
        DefinitionReferent dr = DefinitionReferent._new1191(DefinitionKind.DEFINITION);
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(l0, l1, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
        if (nam == null) 
            return null;
        dr.addSlot(DefinitionReferent.ATTR_TERMIN, nam, false, 0);
        String df = com.pullenti.ner.core.MiscHelper.getTextValue(r0, r1, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
        dr.addSlot(DefinitionReferent.ATTR_VALUE, df, false, 0);
        t = l1.getNext();
        if (t == null) {
        }
        else if (t.isCharOf(".;")) 
            l1 = t;
        else if (t.isComma()) 
            l1 = t;
        else if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
        }
        else 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(new com.pullenti.ner.ReferentToken(dr, r0, l1, null));
        return res;
    }

    private static com.pullenti.ner.MetaToken _tryAttachMiscToken(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isChar('(')) {
            com.pullenti.ner.MetaToken mt = _tryAttachMiscToken(t.getNext());
            if (mt != null && mt.getEndToken().getNext() != null && mt.getEndToken().getNext().isChar(')')) {
                mt.setBeginToken(t);
                mt.setEndToken(mt.getEndToken().getNext());
                return mt;
            }
            return null;
        }
        if (t.isValue("КАК", null)) {
            com.pullenti.ner.Token t1 = null;
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore()) 
                    break;
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 == null) 
                    break;
                if (t1 == null || npt1.getMorph().getCase().isGenitive()) {
                    t1 = (tt = npt1.getEndToken());
                    continue;
                }
                break;
            }
            if (t1 != null) {
                com.pullenti.ner.MetaToken res = com.pullenti.ner.MetaToken._new899(t, t1, com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES));
                res.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
                return res;
            }
            return null;
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0, null);
        if (npt != null) {
            if (m_MiscFirstWords.tryParse(npt.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                com.pullenti.ner.MetaToken res = com.pullenti.ner.MetaToken._new899(t, npt.getEndToken(), npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                res.getMorph().setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                return res;
            }
        }
        if (t.isValue("В", null)) {
            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if (npt.noun.isValue("СМЫСЛ", null)) {
                    com.pullenti.ner.MetaToken res = com.pullenti.ner.MetaToken._new899(t, npt.getEndToken(), com.pullenti.ner.core.MiscHelper.getTextValue(t, npt.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                    res.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
                    return res;
                }
            }
        }
        return null;
    }

    private static com.pullenti.ner.MetaToken _tryParseListItem(com.pullenti.ner.Token t) {
        if (t == null || !t.isWhitespaceBefore()) 
            return null;
        com.pullenti.ner.Token tt = null;
        int pr = 0;
        for (tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isWhitespaceBefore() && tt != t) 
                break;
            if (tt instanceof com.pullenti.ner.NumberToken) {
                pr++;
                continue;
            }
            com.pullenti.ner.NumberToken nex = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt);
            if (nex != null) {
                pr++;
                tt = nex.getEndToken();
                continue;
            }
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!tt.chars.isLetter()) {
                if (!tt.isChar('(')) 
                    pr++;
            }
            else if (tt.getLengthChar() > 1 || tt.isWhitespaceAfter()) 
                break;
            else 
                pr++;
        }
        if (tt == null) 
            return null;
        if (pr == 0) {
            if (t.isChar('(')) 
                return null;
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllLower()) 
                pr++;
        }
        if (pr == 0) 
            return null;
        com.pullenti.ner.MetaToken res = new com.pullenti.ner.MetaToken(tt, tt, null);
        for (; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore() && tt != t) 
                break;
            else 
                res.setEndToken(tt);
        }
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_MiscFirstWords;

    private static com.pullenti.ner.core.TerminCollection m_VerbotFirstWords;

    private static com.pullenti.ner.core.TerminCollection m_VerbotLastWords;

    public static void initialize() throws Exception {
        if (m_Proc0 != null) 
            return;
        com.pullenti.ner.definition.internal.MetaDefin.initialize();
        try {
            m_Proc0 = com.pullenti.ner.ProcessorService.createEmptyProcessor();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            m_MiscFirstWords = new com.pullenti.ner.core.TerminCollection();
            for (String s : new String[] {"ЧЕРТА", "ХАРАКТЕРИСТИКА", "ОСОБЕННОСТЬ", "СВОЙСТВО", "ПРИЗНАК", "ПРИНЦИП", "РАЗНОВИДНОСТЬ", "ВИД", "ПОКАЗАТЕЛЬ", "ЗНАЧЕНИЕ"}) {
                m_MiscFirstWords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
            }
            m_VerbotFirstWords = new com.pullenti.ner.core.TerminCollection();
            for (String s : new String[] {"ЦЕЛЬ", "БОЛЬШИНСТВО", "ЧАСТЬ", "ЗАДАЧА", "ИСКЛЮЧЕНИЕ", "ПРИМЕР", "ЭТАП", "ШАГ", "СЛЕДУЮЩИЙ", "ПОДОБНЫЙ", "АНАЛОГИЧНЫЙ", "ПРЕДЫДУЩИЙ", "ПОХОЖИЙ", "СХОЖИЙ", "НАЙДЕННЫЙ", "НАИБОЛЕЕ", "НАИМЕНЕЕ", "ВАЖНЫЙ", "РАСПРОСТРАНЕННЫЙ"}) {
                m_VerbotFirstWords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
            }
            m_VerbotLastWords = new com.pullenti.ner.core.TerminCollection();
            for (String s : new String[] {"СТАТЬЯ", "ГЛАВА", "РАЗДЕЛ", "КОДЕКС", "ЗАКОН", "ФОРМУЛИРОВКА", "НАСТОЯЩИЙ", "ВЫШЕУКАЗАННЫЙ", "ДАННЫЙ"}) {
                m_VerbotLastWords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
            }
            com.pullenti.ner.definition.internal.ParenthesisToken.initialize();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new DefinitionAnalyzer());
    }

    /**
     * Вычисление коэффициента семантической близости 2-х текстов. 
     * Учитываются именные группы (существительные с возможными прилагательными).
     * @param text1 первый текст
     * @param text2 второй текст
     * @return 0 - ничего общего, 100 - полное соответствие (тождество)
     */
    public static int calcSemanticCoef(String text1, String text2) {
        com.pullenti.ner.AnalysisResult ar1 = m_Proc0.process(new com.pullenti.ner.SourceOfAnalysis(text1), null, null);
        if (ar1 == null || ar1.firstToken == null) 
            return 0;
        com.pullenti.ner.AnalysisResult ar2 = m_Proc0.process(new com.pullenti.ner.SourceOfAnalysis(text2), null, null);
        if (ar2 == null || ar2.firstToken == null) 
            return 0;
        java.util.ArrayList<String> terms1 = new java.util.ArrayList<String>();
        java.util.ArrayList<String> terms2 = new java.util.ArrayList<String>();
        for (int k = 0; k < 2; k++) {
            java.util.ArrayList<String> terms = (k == 0 ? terms1 : terms2);
            for (com.pullenti.ner.Token t = (k == 0 ? ar1.firstToken : ar2.firstToken); t != null; t = t.getNext()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) {
                    String term = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (term == null) 
                        continue;
                    if (!terms.contains(term)) 
                        terms.add(term);
                    continue;
                }
            }
        }
        if (terms2.size() == 0 || terms1.size() == 0) 
            return 0;
        int coef = 0;
        for (String w : terms1) {
            if (terms2.contains(w)) 
                coef += 2;
        }
        return (coef * 100) / ((terms1.size() + terms2.size()));
    }

    /**
     * Выделить ключевые концепты из текста. 
     * Концепт - это нормализованная комбинация ключевых слов, причём дериватная нормализация 
     * (СЛУЖИТЬ -> СЛУЖБА).
     * @param txt текст
     * @param doNormalizeForEnglish делать ли для английского языка нормализацию по дериватам
     * @return список концептов
     */
    public static java.util.ArrayList<String> getConcepts(String txt, boolean doNormalizeForEnglish) {
        com.pullenti.ner.AnalysisResult ar = m_Proc0.process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        java.util.ArrayList<String> tmp = new java.util.ArrayList<String>();
        StringBuilder tmp2 = new StringBuilder();
        if (ar != null) {
            for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
                com.pullenti.ner.Token t1 = null;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0, null);
                if (npt != null) 
                    t1 = npt.getEndToken();
                else if ((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) 
                    t1 = t;
                if (t1 == null) 
                    continue;
                for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                    com.pullenti.ner.core.NounPhraseToken npt2;
                    if (tt.isAnd()) {
                        npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0, null);
                        if (npt2 != null) {
                            tt = (t1 = npt2.getEndToken());
                            continue;
                        }
                        break;
                    }
                    npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0, null);
                    if (npt2 != null) {
                        if (npt2.preposition != null) {
                            tt = (t1 = npt2.getEndToken());
                            continue;
                        }
                        else if (npt2.getMorph().getCase().isGenitive() || npt2.getMorph().getCase().isInstrumental()) {
                            tt = (t1 = npt2.getEndToken());
                            continue;
                        }
                    }
                    break;
                }
                java.util.ArrayList<java.util.ArrayList<String>> vars = new java.util.ArrayList<java.util.ArrayList<String>>();
                for (com.pullenti.ner.Token tt = t; tt != null && tt.getEndChar() <= t1.getEndChar(); tt = tt.getNext()) {
                    if (!(tt instanceof com.pullenti.ner.TextToken)) 
                        continue;
                    if (tt.isCommaAnd() || t.getMorph()._getClass().isPreposition()) 
                        continue;
                    String w = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
                    if (w.length() < 3) 
                        continue;
                    if (tt.chars.isLatinLetter() && !doNormalizeForEnglish) {
                    }
                    else {
                        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> dg = com.pullenti.semantic.utils.DerivateService.findDerivates(w, true, null);
                        if (dg != null && dg.size() == 1) {
                            if (dg.get(0).words.size() > 0) 
                                w = dg.get(0).words.get(0).spelling.toUpperCase();
                        }
                    }
                    if (tt.getPrevious() != null && tt.getPrevious().isCommaAnd() && vars.size() > 0) 
                        vars.get(vars.size() - 1).add(w);
                    else {
                        java.util.ArrayList<String> li = new java.util.ArrayList<String>();
                        li.add(w);
                        vars.add(li);
                    }
                }
                t = t1;
                if (vars.size() == 0) 
                    continue;
                int[] inds = new int[vars.size()];
                while (true) {
                    tmp.clear();
                    for (int i = 0; i < vars.size(); i++) {
                        String w = vars.get(i).get(inds[i]);
                        if (!tmp.contains(w)) 
                            tmp.add(w);
                    }
                    java.util.Collections.sort(tmp);
                    tmp2.setLength(0);
                    for (int i = 0; i < tmp.size(); i++) {
                        if (tmp2.length() > 0) 
                            tmp2.append(' ');
                        tmp2.append(tmp.get(i));
                    }
                    String ww = tmp2.toString();
                    if (!res.contains(ww)) 
                        res.add(ww);
                    int j;
                    for (j = vars.size() - 1; j >= 0; j--) {
                        if ((inds[j] + 1) < vars.get(j).size()) {
                            inds[j]++;
                            break;
                        }
                        else 
                            inds[j] = 0;
                    }
                    if (j < 0) 
                        break;
                }
            }
        }
        return res;
    }

    private static com.pullenti.ner.Processor m_Proc0;
    public DefinitionAnalyzer() {
        super();
    }
}
