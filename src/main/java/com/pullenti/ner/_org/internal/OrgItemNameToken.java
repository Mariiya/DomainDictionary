/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemNameToken extends com.pullenti.ner.MetaToken {

    public OrgItemNameToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public String value;

    public boolean isNounPhrase;

    public boolean isDenomination;

    public boolean isInDictionary;

    public boolean isStdTail;

    public boolean isStdName;

    public boolean isEmptyWord;

    public boolean isIgnoredPart;

    public int stdOrgNameNouns = 0;

    public com.pullenti.ner._org.OrgProfile orgStdProf = com.pullenti.ner._org.OrgProfile.UNDEFINED;

    public boolean isAfterConjunction;

    public String preposition;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(value);
        if (isNounPhrase) 
            res.append(" NounPrase");
        if (isDenomination) 
            res.append(" Denom");
        if (isInDictionary) 
            res.append(" InDictionary");
        if (isAfterConjunction) 
            res.append(" IsAfterConjunction");
        if (isStdTail) 
            res.append(" IsStdTail");
        if (isStdName) 
            res.append(" IsStdName");
        if (isIgnoredPart) 
            res.append(" IsIgnoredPart");
        if (preposition != null) 
            res.append(" IsAfterPreposition '").append(preposition).append("'");
        res.append(" ").append(chars.toString()).append(" (").append(this.getSourceText()).append(")");
        return res.toString();
    }

    public static OrgItemNameToken tryAttach(com.pullenti.ner.Token t, OrgItemNameToken prev, boolean extOnto, boolean first) {
        if (t == null) 
            return null;
        if (t.isValue("ОРДЕНА", null) && t.getNext() != null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                com.pullenti.ner.Token t1 = npt.getEndToken();
                if (((t1.isValue("ЗНАК", null) || t1.isValue("ДРУЖБА", null))) && (t1.getWhitespacesAfterCount() < 2)) {
                    npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null) 
                        t1 = npt.getEndToken();
                }
                return _new1970(t, t1, true);
            }
            if (t.getNext().getMorphClassInDictionary().isProperSurname()) 
                return _new1970(t, t.getNext(), true);
            com.pullenti.ner.ReferentToken ppp = t.kit.processReferent("PERSON", t.getNext(), null);
            if (ppp != null) 
                return _new1970(t, ppp.getEndToken(), true);
            if ((t.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NEARCLOSEBRACKET, 10);
                if (br != null && (br.getLengthChar() < 40)) 
                    return _new1970(t, br.getEndToken(), true);
            }
        }
        if (first && t.chars.isCyrillicLetter() && t.getMorph()._getClass().isPreposition()) {
            if (!t.isValue("ПО", null) && !t.isValue("ПРИ", null)) 
                return null;
        }
        OrgItemNameToken res = _TryAttach(t, prev, extOnto);
        if (res == null) {
            if (extOnto) {
                if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) || (((t instanceof com.pullenti.ner.TextToken) && !t.isChar(';')))) 
                    return _new1974(t, t, t.getSourceText());
            }
            return null;
        }
        if (prev == null && !extOnto) {
            if (t.kit.ontology != null) {
                OrgAnalyzerData ad = (OrgAnalyzerData)com.pullenti.unisharp.Utils.cast(t.kit.ontology._getAnalyzerData(com.pullenti.ner._org.OrganizationAnalyzer.ANALYZER_NAME), OrgAnalyzerData.class);
                if (ad != null) {
                    com.pullenti.ner.core.TerminToken tok = ad.orgPureNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok != null && tok.getEndChar() > res.getEndChar()) 
                        res.setEndToken(tok.getEndToken());
                }
            }
        }
        if (prev != null && !extOnto) {
            if ((prev.chars.isAllLower() && !res.chars.isAllLower() && !res.isStdTail) && !res.isStdName) {
                if (prev.chars.isLatinLetter() && res.chars.isLatinLetter()) {
                }
                else if (m_StdNouns.tryParse(res.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                }
                else 
                    return null;
            }
        }
        if ((res.getEndToken().getNext() != null && !res.getEndToken().isWhitespaceAfter() && res.getEndToken().getNext().isHiphen()) && !res.getEndToken().getNext().isWhitespaceAfter()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.TextToken.class);
            if (tt != null) {
                if (tt.chars.equals(res.chars) || tt.chars.isAllUpper()) {
                    res.setEndToken(tt);
                    res.value = (res.value + "-" + tt.term);
                }
            }
        }
        if ((res.getEndToken().getNext() != null && res.getEndToken().getNext().isAnd() && res.getEndToken().getWhitespacesAfterCount() == 1) && res.getEndToken().getNext().getWhitespacesAfterCount() == 1) {
            OrgItemNameToken res1 = _TryAttach(res.getEndToken().getNext().getNext(), prev, extOnto);
            if (res1 != null && res1.chars.equals(res.chars) && OrgItemTypeToken.tryAttach(res.getEndToken().getNext().getNext(), false) == null) {
                if (!((com.pullenti.morph.MorphCase.ooBitand(res1.getMorph().getCase(), res.getMorph().getCase()))).isUndefined()) {
                    res.setEndToken(res1.getEndToken());
                    res.value = (res.value + " " + (res.kit.baseLanguage.isUa() ? "ТА" : "И") + " " + res1.value);
                }
            }
        }
        for (com.pullenti.ner.Token tt = res.getBeginToken(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
            if (m_StdNouns.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                res.stdOrgNameNouns++;
        }
        if (m_StdNouns.tryParse(res.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
            int cou = 1;
            boolean non = false;
            com.pullenti.ner.Token et = res.getEndToken();
            if (!_isNotTermNoun(res.getEndToken())) 
                non = true;
            boolean br = false;
            for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isTableControlChar()) 
                    break;
                if (tt.isChar('(')) {
                    if (!non) 
                        break;
                    br = true;
                    continue;
                }
                if (tt.isChar(')')) {
                    br = false;
                    et = tt;
                    break;
                }
                if (!(tt instanceof com.pullenti.ner.TextToken)) 
                    break;
                if (tt.getWhitespacesBeforeCount() > 1) {
                    if (tt.getNewlinesBeforeCount() > 1) 
                        break;
                    if (!tt.chars.equals(res.getEndToken().chars)) 
                        break;
                }
                if (tt.getMorph()._getClass().isPreposition() || tt.isCommaAnd()) 
                    continue;
                com.pullenti.morph.MorphClass dd = tt.getMorphClassInDictionary();
                if (!dd.isNoun() && !dd.isAdjective()) 
                    break;
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt2 == null) {
                    if (dd.equals(com.pullenti.morph.MorphClass.ADJECTIVE)) 
                        continue;
                    break;
                }
                if (m_StdNouns.tryParse(npt2.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) == null) 
                    break;
                if (!npt2.getEndToken().chars.equals(res.getEndToken().chars)) 
                    break;
                if ((npt2.getEndToken().isValue("УПРАВЛЕНИЕ", null) || npt2.getEndToken().isValue("ИНСТИТУТ", null) || npt2.getEndToken().isValue("УПРАВЛІННЯ", null)) || npt2.getEndToken().isValue("ІНСТИТУТ", null) || tt.getPrevious().isValue("ПРИ", null)) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner._org.OrganizationAnalyzer.processReferentStat(tt, null);
                    if (rt != null) 
                        break;
                }
                cou++;
                tt = npt2.getEndToken();
                if (!_isNotTermNoun(tt)) {
                    non = true;
                    et = tt;
                }
            }
            if (non && !br) {
                res.stdOrgNameNouns += cou;
                res.setEndToken(et);
            }
        }
        return res;
    }

    private static java.util.ArrayList<String> m_NotTerminateNouns;

    private static boolean _isNotTermNoun(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return false;
        if (!(t.getPrevious() instanceof com.pullenti.ner.TextToken)) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.TextToken.class)).term, "ПО")) 
            return false;
        for (String v : m_NotTerminateNouns) {
            if (t.isValue(v, null)) 
                return true;
        }
        return false;
    }

    private static OrgItemNameToken _TryAttach(com.pullenti.ner.Token t, OrgItemNameToken prev, boolean extOnto) {
        if (t == null) 
            return null;
        com.pullenti.ner.Referent r = t.getReferent();
        if (r != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DENOMINATION")) 
                return _new1975(t, t, r.toStringEx(true, t.kit.baseLanguage, 0), true);
            if ((r instanceof com.pullenti.ner.geo.GeoReferent) && t.chars.isLatinLetter()) {
                OrgItemNameToken res2 = _TryAttach(t.getNext(), prev, extOnto);
                if (res2 != null && res2.chars.isLatinLetter()) {
                    res2.setBeginToken(t);
                    res2.value = (com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class), com.pullenti.ner.core.GetTextAttr.NO) + " " + res2.value);
                    res2.isInDictionary = false;
                    return res2;
                }
            }
            return null;
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        OrgItemNameToken res = null;
        com.pullenti.ner.core.TerminToken tok = m_StdTails.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.isChar(',')) 
            tok = m_StdTails.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) 
            return _new1976(t, tok.getEndToken(), tok.termin.getCanonicText(), tok.termin.tag == null, tok.termin.tag != null, tok.getMorph());
        if ((((tok = m_StdNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) 
            return _new1977(t, tok.getEndToken(), tok.termin.getCanonicText(), true);
        OrgItemEngItem eng = OrgItemEngItem.tryAttach(t, false);
        if (eng == null && t.isChar(',')) 
            eng = OrgItemEngItem.tryAttach(t.getNext(), false);
        if (eng != null) 
            return _new1978(t, eng.getEndToken(), eng.fullValue, true);
        if (tt.chars.isAllLower() && prev != null) {
            if (!prev.chars.isAllLower() && !prev.chars.isCapitalUpper()) 
                return null;
        }
        if (tt.isChar(',') && prev != null) {
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt1 == null || !npt1.chars.equals(prev.chars) || ((com.pullenti.morph.MorphCase.ooBitand(npt1.getMorph().getCase(), prev.getMorph().getCase()))).isUndefined()) 
                return null;
            OrgItemTypeToken ty = OrgItemTypeToken.tryAttach(t.getNext(), false);
            if (ty != null) 
                return null;
            if (npt1.getEndToken().getNext() == null || !npt1.getEndToken().getNext().isValue("И", null)) 
                return null;
            com.pullenti.ner.Token t1 = npt1.getEndToken().getNext();
            com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt2 == null || !npt2.chars.equals(prev.chars) || ((com.pullenti.morph.MorphCase.ooBitand(npt2.getMorph().getCase(), com.pullenti.morph.MorphCase.ooBitand(npt1.getMorph().getCase(), prev.getMorph().getCase())))).isUndefined()) 
                return null;
            ty = OrgItemTypeToken.tryAttach(t1.getNext(), false);
            if (ty != null) 
                return null;
            res = _new1979(npt1.getBeginToken(), npt1.getEndToken(), npt1.getMorph(), npt1.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
            res.isNounPhrase = true;
            res.isAfterConjunction = true;
            if (prev.preposition != null) 
                res.preposition = prev.preposition;
            return res;
        }
        if (((tt.isChar('&') || tt.isValue("AND", null) || tt.isValue("UND", null))) && prev != null) {
            if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.getNext().chars.isLatinLetter()) {
                res = _new1980(tt, tt.getNext(), tt.getNext().chars);
                res.isAfterConjunction = true;
                res.value = "& " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class)).term;
                return res;
            }
            res = OrgItemNameToken.tryAttach(tt.getNext(), null, extOnto, false);
            if (res == null || !res.chars.equals(prev.chars)) 
                return null;
            res.isAfterConjunction = true;
            res.value = "& " + res.value;
            return res;
        }
        if (!tt.chars.isLetter()) 
            return null;
        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> expinf = null;
        if (prev != null && prev.getEndToken().getMorphClassInDictionary().isNoun()) {
            String wo = prev.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
            expinf = com.pullenti.semantic.utils.DerivateService.findDerivates(wo, true, prev.getEndToken().getMorph().getLanguage());
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null && npt.internalNoun != null) 
            npt = null;
        boolean explOk = false;
        if (npt != null && prev != null && prev.getEndToken().getMorphClassInDictionary().isNoun()) {
            com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(prev.getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt0 != null) {
                java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(npt0, npt, null);
                if (links.size() > 0) 
                    explOk = true;
            }
        }
        if (npt != null && ((explOk || npt.getMorph().getCase().isGenitive() || ((prev != null && !((com.pullenti.morph.MorphCase.ooBitand(prev.getMorph().getCase(), npt.getMorph().getCase()))).isUndefined()))))) {
            com.pullenti.morph.MorphClass mc = npt.getBeginToken().getMorphClassInDictionary();
            if (mc.isVerb() || mc.isPronoun()) 
                return null;
            if (mc.isAdverb()) {
                if (npt.getBeginToken().getNext() != null && npt.getBeginToken().getNext().isHiphen()) {
                }
                else 
                    return null;
            }
            if (mc.isPreposition()) 
                return null;
            if (mc.isNoun() && npt.chars.isAllLower()) {
                com.pullenti.morph.MorphCase ca = npt.getMorph().getCase();
                if ((!ca.isDative() && !ca.isGenitive() && !ca.isInstrumental()) && !ca.isPrepositional()) 
                    return null;
            }
            res = _new1979(npt.getBeginToken(), npt.getEndToken(), npt.getMorph(), npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
            res.isNounPhrase = true;
            if ((npt.getEndToken().getWhitespacesAfterCount() < 2) && (npt.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt2 != null && npt2.getMorph().getCase().isGenitive() && npt2.chars.isAllLower()) {
                    OrgItemTypeToken typ = OrgItemTypeToken.tryAttach(npt.getEndToken().getNext(), true);
                    OrgItemEponymToken epo = OrgItemEponymToken.tryAttach(npt.getEndToken().getNext(), false);
                    com.pullenti.ner.ReferentToken rtt = t.kit.processReferent("PERSONPROPERTY", npt.getEndToken().getNext(), null);
                    if (typ == null && epo == null && ((rtt == null || rtt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL))) {
                        res.setEndToken(npt2.getEndToken());
                        res.value = (res.value + " " + com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt2, com.pullenti.ner.core.GetTextAttr.NO));
                    }
                }
                else if (npt.getEndToken().getNext().isComma() && (npt.getEndToken().getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                    com.pullenti.ner.Token tt2 = npt.getEndToken().getNext().getNext();
                    com.pullenti.morph.MorphClass mv2 = tt2.getMorphClassInDictionary();
                    if (mv2.isAdjective() && mv2.isVerb()) {
                        com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new1982(npt.getMorph().getCase(), npt.getMorph().getGender(), npt.getMorph().getNumber());
                        if (tt2.getMorph().checkAccord(bi, false, false)) {
                            npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt2.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt2 != null && ((npt2.getMorph().getCase().isDative() || npt2.getMorph().getCase().isGenitive())) && npt2.chars.isAllLower()) {
                                res.setEndToken(npt2.getEndToken());
                                res.value = (res.value + " " + com.pullenti.ner.core.MiscHelper.getTextValue(npt.getEndToken().getNext(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                            }
                        }
                    }
                }
            }
            if (explOk) 
                res.isAfterConjunction = true;
        }
        else if (npt != null && ((((prev != null && prev.isNounPhrase && npt.getMorph().getCase().isInstrumental())) || extOnto))) {
            res = _new1979(npt.getBeginToken(), npt.getEndToken(), npt.getMorph(), npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
            res.isNounPhrase = true;
        }
        else if (tt.isAnd()) {
            res = tryAttach(tt.getNext(), prev, extOnto, false);
            if (res == null || !res.isNounPhrase || prev == null) 
                return null;
            if (((com.pullenti.morph.MorphCase.ooBitand(prev.getMorph().getCase(), res.getMorph().getCase()))).isUndefined()) 
                return null;
            if (prev.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((prev.getMorph().getNumber().value()) & (res.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                    if (!prev.chars.equals(res.chars)) 
                        return null;
                    OrgItemTypeToken ty = OrgItemTypeToken.tryAttach(res.getEndToken().getNext(), false);
                    if (ty != null) 
                        return null;
                }
            }
            com.pullenti.morph.CharsInfo ci = res.chars;
            res.chars = ci;
            res.isAfterConjunction = true;
            return res;
        }
        else if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПО") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПРИ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ЗА")) || com.pullenti.unisharp.Utils.stringsEq(tt.term, "С") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "В")) || com.pullenti.unisharp.Utils.stringsEq(tt.term, "НА")) {
            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if (m_VervotWords.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                    return null;
                boolean ok = false;
                if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПО")) 
                    ok = npt.getMorph().getCase().isDative();
                else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "С")) 
                    ok = npt.getMorph().getCase().isInstrumental();
                else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ЗА")) 
                    ok = npt.getMorph().getCase().isGenitive() | npt.getMorph().getCase().isInstrumental();
                else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "НА")) 
                    ok = npt.getMorph().getCase().isPrepositional();
                else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "В")) {
                    ok = npt.getMorph().getCase().isDative() | npt.getMorph().getCase().isPrepositional();
                    if (ok) {
                        ok = false;
                        if (t.getNext().isValue("СФЕРА", null) || t.getNext().isValue("ОБЛАСТЬ", null)) 
                            ok = true;
                    }
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПРИ")) {
                    ok = npt.getMorph().getCase().isPrepositional();
                    if (ok) {
                        if (OrgItemTypeToken.tryAttach(tt.getNext(), true) != null) 
                            ok = false;
                        else {
                            com.pullenti.ner.ReferentToken rt = com.pullenti.ner._org.OrganizationAnalyzer.processReferentStat(tt.getNext(), null);
                            if (rt != null) 
                                ok = false;
                        }
                    }
                    String s = npt.noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (com.pullenti.unisharp.Utils.stringsEq(s, "ПОДДЕРЖКА") || com.pullenti.unisharp.Utils.stringsEq(s, "УЧАСТИЕ")) 
                        ok = false;
                }
                else 
                    ok = npt.getMorph().getCase().isPrepositional();
                if (ok) {
                    res = _new1984(t, npt.getEndToken(), npt.getMorph(), npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.chars);
                    res.isNounPhrase = true;
                    res.preposition = tt.term;
                    if (((com.pullenti.unisharp.Utils.stringsEq(res.value, "ДЕЛО") || com.pullenti.unisharp.Utils.stringsEq(res.value, "ВОПРОС"))) && !res.isNewlineAfter()) {
                        OrgItemNameToken res2 = _TryAttach(res.getEndToken().getNext(), res, extOnto);
                        if (res2 != null && res2.getMorph().getCase().isGenitive()) {
                            res.value = (res.value + " " + res2.value);
                            res.setEndToken(res2.getEndToken());
                            for (com.pullenti.ner.Token ttt = res2.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                                if (!ttt.isCommaAnd()) 
                                    break;
                                OrgItemNameToken res3 = _TryAttach(ttt.getNext(), res2, extOnto);
                                if (res3 == null) 
                                    break;
                                res.value = (res.value + " " + res3.value);
                                res.setEndToken(res3.getEndToken());
                                if (ttt.isAnd()) 
                                    break;
                                ttt = res.getEndToken();
                            }
                        }
                    }
                }
            }
            if (res == null) 
                return null;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "OF")) {
            com.pullenti.ner.Token t1 = tt.getNext();
            if (t1 != null && com.pullenti.ner.core.MiscHelper.isEngArticle(t1)) 
                t1 = t1.getNext();
            if (t1 != null && t1.chars.isLatinLetter() && !t1.chars.isAllLower()) {
                res = _new1985(t, t1, t1.chars, t1.getMorph());
                for (com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (ttt.getWhitespacesBeforeCount() > 2) 
                        break;
                    if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(ttt)) {
                        ttt = ttt.getNext();
                        continue;
                    }
                    if (!ttt.chars.isLatinLetter()) 
                        break;
                    if (ttt.getMorph()._getClass().isPreposition()) 
                        break;
                    t1 = res.setEndToken(ttt);
                }
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES);
                res.preposition = tt.term;
                return res;
            }
        }
        if (res == null) {
            if (tt.chars.isLatinLetter() && tt.getLengthChar() == 1) {
            }
            else if (tt.chars.isAllLower() || (tt.getLengthChar() < 2)) {
                if (!tt.chars.isLatinLetter() || prev == null || !prev.chars.isLatinLetter()) 
                    return null;
            }
            if (tt.chars.isCyrillicLetter()) {
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (mc.isVerb() || mc.isAdverb()) 
                    return null;
            }
            else if (tt.chars.isLatinLetter() && !tt.isWhitespaceAfter()) {
                if (!tt.isWhitespaceAfter() && (tt.getLengthChar() < 5)) {
                    if (tt.getNext() instanceof com.pullenti.ner.NumberToken) 
                        return null;
                }
            }
            res = _new1986(tt, tt, tt.term, tt.getMorph());
            for (t = tt.getNext(); t != null; t = t.getNext()) {
                if ((((t.isHiphen() || t.isCharOf("\\/"))) && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) && !t.isWhitespaceBefore() && !t.isWhitespaceAfter()) {
                    t = t.getNext();
                    res.setEndToken(t);
                    res.value = (res.value + (t.getPrevious().isChar('.') ? '.' : '-') + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                }
                else if (t.isChar('.')) {
                    if (!t.isWhitespaceAfter() && !t.isWhitespaceBefore() && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                        res.setEndToken(t.getNext());
                        t = t.getNext();
                        res.value = (res.value + "." + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    }
                    else if ((t.getNext() != null && !t.isNewlineAfter() && t.getNext().chars.isLatinLetter()) && tt.chars.isLatinLetter()) 
                        res.setEndToken(t);
                    else 
                        break;
                }
                else 
                    break;
            }
        }
        for (com.pullenti.ner.Token t0 = res.getBeginToken(); t0 != null; t0 = t0.getNext()) {
            if ((((tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)))) != null && tt.isLetters()) {
                if (!tt.getMorph()._getClass().isConjunction() && !tt.getMorph()._getClass().isPreposition()) {
                    for (com.pullenti.morph.MorphBaseInfo mf : tt.getMorph().getItems()) {
                        if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(mf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                            res.isInDictionary = true;
                    }
                }
            }
            if (t0 == res.getEndToken()) 
                break;
        }
        if (res.getBeginToken() == res.getEndToken() && res.getBeginToken().chars.isAllUpper()) {
            if (res.getEndToken().getNext() != null && !res.getEndToken().isWhitespaceAfter()) {
                com.pullenti.ner.Token t1 = res.getEndToken().getNext();
                if (t1.getNext() != null && !t1.isWhitespaceAfter() && t1.isHiphen()) 
                    t1 = t1.getNext();
                if (t1 instanceof com.pullenti.ner.NumberToken) {
                    res.value += ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue();
                    res.setEndToken(t1);
                }
            }
        }
        if (res.getBeginToken() == res.getEndToken() && res.getBeginToken().chars.isLastLower()) {
            String src = res.getBeginToken().getSourceText();
            for (int i = src.length() - 1; i >= 0; i--) {
                if (Character.isUpperCase(src.charAt(i))) {
                    res.value = src.substring(0, 0 + i + 1);
                    break;
                }
            }
        }
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_StdNames;

    private static com.pullenti.ner.core.TerminCollection m_StdTails;

    private static com.pullenti.ner.core.TerminCollection m_VervotWords;

    private static com.pullenti.ner.core.TerminCollection m_StdNouns;

    public static com.pullenti.ner.core.TerminCollection m_DepStdNames;

    public static void initialize() throws Exception, java.io.IOException {
        m_StdTails = new com.pullenti.ner.core.TerminCollection();
        m_StdNames = new com.pullenti.ner.core.TerminCollection();
        m_VervotWords = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("INCORPORATED", null, false);
        t.addAbridge("INC.");
        m_StdTails.add(t);
        t = new com.pullenti.ner.core.Termin("CORPORATION", null, false);
        t.addAbridge("CORP.");
        m_StdTails.add(t);
        t = new com.pullenti.ner.core.Termin("LIMITED", null, false);
        t.addAbridge("LTD.");
        m_StdTails.add(t);
        t = new com.pullenti.ner.core.Termin("AG", null, false);
        m_StdTails.add(t);
        t = new com.pullenti.ner.core.Termin("GMBH", null, false);
        m_StdTails.add(t);
        for (String s : new String[] {"ЗАКАЗЧИК", "ИСПОЛНИТЕЛЬ", "РАЗРАБОТЧИК", "БЕНЕФИЦИАР", "ПОЛУЧАТЕЛЬ", "ОТПРАВИТЕЛЬ", "ИЗГОТОВИТЕЛЬ", "ПРОИЗВОДИТЕЛЬ", "ПОСТАВЩИК", "АБОНЕНТ", "КЛИЕНТ", "ВКЛАДЧИК", "СУБЪЕКТ", "ПРОДАВЕЦ", "ПОКУПАТЕЛЬ", "АРЕНДОДАТЕЛЬ", "АРЕНДАТОР", "СУБАРЕНДАТОР", "НАЙМОДАТЕЛЬ", "НАНИМАТЕЛЬ", "АГЕНТ", "ПРИНЦИПАЛ", "ПРОДАВЕЦ", "ПОСТАВЩИК", "ПОДРЯДЧИК", "СУБПОДРЯДЧИК"}) {
            m_StdTails.add(com.pullenti.ner.core.Termin._new100(s, s));
        }
        for (String s : new String[] {"ЗАМОВНИК", "ВИКОНАВЕЦЬ", "РОЗРОБНИК", "БЕНЕФІЦІАР", "ОДЕРЖУВАЧ", "ВІДПРАВНИК", "ВИРОБНИК", "ВИРОБНИК", "ПОСТАЧАЛЬНИК", "АБОНЕНТ", "КЛІЄНТ", "ВКЛАДНИК", "СУБ'ЄКТ", "ПРОДАВЕЦЬ", "ПОКУПЕЦЬ", "ОРЕНДОДАВЕЦЬ", "ОРЕНДАР", "СУБОРЕНДАР", "НАЙМОДАВЕЦЬ", "НАЙМАЧ", "АГЕНТ", "ПРИНЦИПАЛ", "ПРОДАВЕЦЬ", "ПОСТАЧАЛЬНИК", "ПІДРЯДНИК", "СУБПІДРЯДНИК"}) {
            m_StdTails.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, s));
        }
        t = new com.pullenti.ner.core.Termin("РАЗРАБОТКА ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ", null, false);
        t.addAbridge("РАЗРАБОТКИ ПО");
        m_StdNames.add(t);
        for (String s : new String[] {"СПЕЦИАЛЬНОСТЬ", "ДИАГНОЗ"}) {
            m_VervotWords.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        for (String s : new String[] {"СПЕЦІАЛЬНІСТЬ", "ДІАГНОЗ"}) {
            m_VervotWords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.UA, false));
        }
        m_StdNouns = new com.pullenti.ner.core.TerminCollection();
        for (int k = 0; k < 2; k++) {
            String name = (k == 0 ? "NameNouns_ru.dat" : "NameNouns_ua.dat");
            byte[] dat = ResourceHelper.getBytes(name);
            if (dat == null) 
                throw new Exception(("Can't file resource file " + name + " in Organization analyzer"));
            String str = com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), OrgItemTypeToken.deflate(dat), 0, -1);
            for (String line0 : com.pullenti.unisharp.Utils.split(str, String.valueOf('\n'), false)) {
                String line = line0.trim();
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(line)) 
                    continue;
                if (k == 0) 
                    m_StdNouns.add(new com.pullenti.ner.core.Termin(line, null, false));
                else 
                    m_StdNouns.add(com.pullenti.ner.core.Termin._new968(line, com.pullenti.morph.MorphLang.UA));
            }
        }
    }

    public static OrgItemNameToken _new1970(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.isIgnoredPart = _arg3;
        return res;
    }

    public static OrgItemNameToken _new1974(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        return res;
    }

    public static OrgItemNameToken _new1975(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.isDenomination = _arg4;
        return res;
    }

    public static OrgItemNameToken _new1976(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.isStdTail = _arg4;
        res.isEmptyWord = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static OrgItemNameToken _new1977(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.isStdName = _arg4;
        return res;
    }

    public static OrgItemNameToken _new1978(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.isStdTail = _arg4;
        return res;
    }

    public static OrgItemNameToken _new1979(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, String _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.value = _arg4;
        return res;
    }

    public static OrgItemNameToken _new1980(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.morph.CharsInfo _arg3) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.chars = _arg3;
        return res;
    }

    public static OrgItemNameToken _new1984(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, String _arg4, com.pullenti.morph.CharsInfo _arg5) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.value = _arg4;
        res.chars = _arg5;
        return res;
    }

    public static OrgItemNameToken _new1985(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.morph.CharsInfo _arg3, com.pullenti.ner.MorphCollection _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.chars = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static OrgItemNameToken _new1986(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.MorphCollection _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static OrgItemNameToken _new2545(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.isStdName = _arg3;
        return res;
    }

    public static OrgItemNameToken _new2547(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.morph.CharsInfo _arg4) {
        OrgItemNameToken res = new OrgItemNameToken(_arg1, _arg2);
        res.value = _arg3;
        res.chars = _arg4;
        return res;
    }

    public OrgItemNameToken() {
        super();
    }
    
    static {
        m_NotTerminateNouns = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"РАБОТА", "ВОПРОС", "ДЕЛО", "УПРАВЛЕНИЕ", "ОРГАНИЗАЦИЯ", "ОБЕСПЕЧЕНИЕ", "РОБОТА", "ПИТАННЯ", "СПРАВА", "УПРАВЛІННЯ", "ОРГАНІЗАЦІЯ", "ЗАБЕЗПЕЧЕННЯ"}));
    }
}
