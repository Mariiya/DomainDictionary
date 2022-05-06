/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.weapon.internal;

public class WeaponItemToken extends com.pullenti.ner.MetaToken {

    public WeaponItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public Typs typ = Typs.NOUN;

    public String value;

    public String altValue;

    public boolean isDoubt;

    public boolean isAfterConjunction;

    public boolean isInternal;

    private java.util.ArrayList<WeaponItemToken> innerTokens = new java.util.ArrayList<WeaponItemToken>();

    public com.pullenti.ner.Referent ref;

    @Override
    public String toString() {
        return (typ.toString() + ": " + ((value != null ? value : ((ref == null ? "" : ref.toString())))) + " " + ((altValue != null ? altValue : "")) + (isInternal ? "[int]" : ""));
    }

    public static java.util.ArrayList<WeaponItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount) {
        WeaponItemToken tr = tryParse(t, null, false, false);
        if (tr == null) 
            return null;
        if (tr.typ == Typs.CLASS || tr.typ == Typs.DATE) 
            return null;
        WeaponItemToken tr0 = tr;
        java.util.ArrayList<WeaponItemToken> res = new java.util.ArrayList<WeaponItemToken>();
        if (tr.innerTokens.size() > 0) {
            com.pullenti.unisharp.Utils.addToArrayList(res, tr.innerTokens);
            if (res.get(0).getBeginChar() > tr.getBeginChar()) 
                res.get(0).setBeginToken(tr.getBeginToken());
        }
        res.add(tr);
        t = tr.getEndToken().getNext();
        if (tr.typ == Typs.NOUN) {
            for (; t != null; t = t.getNext()) {
                if (t.isChar(':') || t.isHiphen()) {
                }
                else 
                    break;
            }
        }
        boolean andConj = false;
        for (; t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            if (t.isChar(':')) 
                continue;
            if (tr0.typ == Typs.NOUN) {
                if (t.isHiphen() && t.getNext() != null) 
                    t = t.getNext();
            }
            tr = tryParse(t, tr0, false, false);
            if (tr == null) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false) && t.getNext() != null) {
                    if (tr0.typ == Typs.MODEL || tr0.typ == Typs.BRAND) {
                        com.pullenti.ner.Token tt1 = t.getNext();
                        if (tt1 != null && tt1.isComma()) 
                            tt1 = tt1.getNext();
                        tr = tryParse(tt1, tr0, false, false);
                    }
                }
            }
            if (tr == null && (t instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                if (rt.getBeginToken() == rt.getEndToken() && (rt.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                    tr = tryParse(rt.getBeginToken(), tr0, false, false);
                    if (tr != null && tr.getBeginToken() == tr.getEndToken()) 
                        tr.setBeginToken(tr.setEndToken(t));
                }
            }
            if (tr == null && t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    com.pullenti.ner.Token tt = br.getEndToken().getNext();
                    if (tt != null && tt.isComma()) 
                        tt = tt.getNext();
                    tr = tryParse(tt, tr0, false, false);
                    if (tr != null && tr.typ == Typs.NUMBER) {
                    }
                    else 
                        tr = null;
                }
            }
            if (tr == null && t.isHiphen()) {
                if (tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) 
                    tr = tryParse(t.getNext(), tr0, false, false);
            }
            if (tr == null && t.isComma()) {
                if ((tr0.typ == Typs.NAME || tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) || tr0.typ == Typs.CLASS || tr0.typ == Typs.DATE) {
                    tr = tryParse(t.getNext(), tr0, true, false);
                    if (tr != null) {
                        if (tr.typ == Typs.NUMBER) {
                        }
                        else 
                            tr = null;
                    }
                }
            }
            if (tr == null) 
                break;
            if (t.isNewlineBefore()) {
                if (tr.typ != Typs.NUMBER) 
                    break;
            }
            if (tr.innerTokens.size() > 0) 
                com.pullenti.unisharp.Utils.addToArrayList(res, tr.innerTokens);
            res.add(tr);
            tr0 = tr;
            t = tr.getEndToken();
            if (andConj) 
                break;
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == Typs.MODEL && res.get(i + 1).typ == Typs.MODEL) {
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.get(i).value = (res.get(i).value + (res.get(i).getEndToken().getNext() != null && res.get(i).getEndToken().getNext().isHiphen() ? '-' : ' ') + res.get(i + 1).value);
                res.remove(i + 1);
                i--;
            }
        }
        return res;
    }

    public static WeaponItemToken tryParse(com.pullenti.ner.Token t, WeaponItemToken prev, boolean afterConj, boolean attachHigh) {
        WeaponItemToken res = _TryParse(t, prev, afterConj, attachHigh);
        if (res == null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.noun.getBeginChar() > npt.getBeginChar()) {
                res = _TryParse(npt.noun.getBeginToken(), prev, afterConj, attachHigh);
                if (res != null) {
                    if (res.typ == Typs.NOUN) {
                        String str = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        if (com.pullenti.unisharp.Utils.stringsEq(str, "РУЧНОЙ ГРАНАТ")) 
                            str = "РУЧНАЯ ГРАНАТА";
                        if ((((str != null ? str : ""))).endsWith(res.value)) {
                            if (res.altValue == null) 
                                res.altValue = str;
                            else {
                                str = str.substring(0, 0 + str.length() - res.value.length()).trim();
                                res.altValue = (str + " " + res.altValue);
                            }
                            res.setBeginToken(t);
                            return res;
                        }
                    }
                }
            }
            return null;
        }
        if (res.typ == Typs.NAME) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.isChar('(')) {
                String alt = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(res.value, alt)) {
                    res.altValue = alt;
                    res.setEndToken(br.getEndToken());
                }
            }
        }
        return res;
    }

    private static WeaponItemToken _TryParse(com.pullenti.ner.Token t, WeaponItemToken prev, boolean afterConj, boolean attachHigh) {
        if (t == null) 
            return null;
        if (com.pullenti.ner.core.BracketHelper.isBracket(t, true)) {
            WeaponItemToken wit = _TryParse(t.getNext(), prev, afterConj, attachHigh);
            if (wit != null) {
                if (wit.getEndToken().getNext() == null) {
                    wit.setBeginToken(t);
                    return wit;
                }
                if (com.pullenti.ner.core.BracketHelper.isBracket(wit.getEndToken().getNext(), true)) {
                    wit.setBeginToken(t);
                    wit.setEndToken(wit.getEndToken().getNext());
                    return wit;
                }
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            WeaponItemToken res = new WeaponItemToken(t, tok.getEndToken());
            res.typ = (Typs)tok.termin.tag;
            if (res.typ == Typs.NOUN) {
                res.value = tok.termin.getCanonicText();
                if (tok.termin.tag2 != null) 
                    res.isDoubt = true;
                for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.getWhitespacesBeforeCount() > 2) 
                        break;
                    WeaponItemToken wit = _TryParse(tt, null, false, false);
                    if (wit != null) {
                        if (wit.typ == Typs.BRAND) {
                            res.innerTokens.add(wit);
                            res.setEndToken((tt = wit.getEndToken()));
                            continue;
                        }
                        break;
                    }
                    if (!(tt instanceof com.pullenti.ner.TextToken)) 
                        break;
                    com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                    if (mc.equals(com.pullenti.morph.MorphClass.ADJECTIVE)) {
                        if (res.altValue == null) 
                            res.altValue = res.value;
                        if (res.altValue.endsWith(res.value)) 
                            res.altValue = res.altValue.substring(0, 0 + res.altValue.length() - res.value.length());
                        res.altValue = (res.altValue + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term + " " + res.value);
                        res.setEndToken(tt);
                        continue;
                    }
                    break;
                }
                return res;
            }
            if (res.typ == Typs.BRAND || res.typ == Typs.NAME) {
                res.value = tok.termin.getCanonicText();
                return res;
            }
            if (res.typ == Typs.MODEL) {
                res.value = tok.termin.getCanonicText();
                if (tok.termin.tag2 instanceof java.util.ArrayList) {
                    java.util.ArrayList<com.pullenti.ner.core.Termin> li = (java.util.ArrayList<com.pullenti.ner.core.Termin>)com.pullenti.unisharp.Utils.cast(tok.termin.tag2, java.util.ArrayList.class);
                    for (com.pullenti.ner.core.Termin to : li) {
                        WeaponItemToken wit = _new2966(t, tok.getEndToken(), (Typs)to.tag, to.getCanonicText(), tok.getBeginToken() == tok.getEndToken());
                        res.innerTokens.add(wit);
                        if (to.additionalVars != null && to.additionalVars.size() > 0) 
                            wit.altValue = to.additionalVars.get(0).getCanonicText();
                    }
                }
                res._correctModel();
                return res;
            }
        }
        com.pullenti.ner.Token nnn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
        if (nnn != null) {
            com.pullenti.ner.transport.internal.TransItemToken tit = com.pullenti.ner.transport.internal.TransItemToken._attachNumber(nnn, true);
            if (tit != null) {
                WeaponItemToken res = _new2967(t, tit.getEndToken(), Typs.NUMBER);
                res.value = tit.value;
                res.altValue = tit.altValue;
                return res;
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && t.chars.isAllUpper()) && (t.getLengthChar() < 4)) {
            if ((t.getNext() != null && ((t.getNext().isHiphen() || t.getNext().isChar('.'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                WeaponItemToken res = _new2968(t, t.getNext(), Typs.MODEL, true);
                res.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                res._correctModel();
                return res;
            }
            if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceAfter()) {
                WeaponItemToken res = _new2968(t, t, Typs.MODEL, true);
                res.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                res._correctModel();
                return res;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "СП") && (t.getWhitespacesAfterCount() < 3) && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                WeaponItemToken pp = _TryParse(t.getNext(), null, false, false);
                if (pp != null && ((pp.typ == Typs.MODEL || pp.typ == Typs.BRAND))) {
                    WeaponItemToken res = _new2967(t, t, Typs.NOUN);
                    res.value = "ПИСТОЛЕТ";
                    res.altValue = "СЛУЖЕБНЫЙ ПИСТОЛЕТ";
                    return res;
                }
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) && t.getLengthChar() > 2) {
            boolean ok = false;
            if (prev != null && ((prev.typ == Typs.NOUN || prev.typ == Typs.MODEL || prev.typ == Typs.BRAND))) 
                ok = true;
            else if (prev == null && t.getPrevious() != null && t.getPrevious().isCommaAnd()) 
                ok = true;
            if (ok) {
                WeaponItemToken res = _new2968(t, t, Typs.NAME, true);
                res.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().chars.equals(t.chars)) {
                    res.value = (res.value + "-" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class)).term);
                    res.setEndToken(t.getNext().getNext());
                }
                if (prev != null && prev.typ == Typs.NOUN) 
                    res.typ = Typs.BRAND;
                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && (res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    res.typ = Typs.MODEL;
                    res._correctModel();
                }
                else if (!res.getEndToken().isWhitespaceAfter() && (res.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    res.typ = Typs.MODEL;
                    res._correctModel();
                }
                return res;
            }
        }
        if (t.isValue("МАРКА", null)) {
            WeaponItemToken res = _TryParse(t.getNext(), prev, afterConj, false);
            if (res != null && res.typ == Typs.BRAND) {
                res.setBeginToken(t);
                return res;
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) 
                    return _new2972(t, br.getEndToken(), Typs.BRAND, com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
            }
            if (((t instanceof com.pullenti.ner.TextToken) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getLengthChar() > 1) && !t.getNext().chars.isAllLower()) 
                return _new2972(t, t.getNext(), Typs.BRAND, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
        }
        if (t.isValue("КАЛИБР", "КАЛІБР")) {
            com.pullenti.ner.Token tt1 = t.getNext();
            if (tt1 != null && ((tt1.isHiphen() || tt1.isChar(':')))) 
                tt1 = tt1.getNext();
            com.pullenti.ner.measure.internal.NumbersWithUnitToken num = com.pullenti.ner.measure.internal.NumbersWithUnitToken.tryParse(tt1, null, com.pullenti.ner.measure.internal.NumberWithUnitParseAttr.NO);
            if (num != null && num.singleVal != null) 
                return _new2972(t, num.getEndToken(), Typs.CALIBER, com.pullenti.ner.core.NumberHelper.doubleToString(num.singleVal));
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.measure.internal.NumbersWithUnitToken num = com.pullenti.ner.measure.internal.NumbersWithUnitToken.tryParse(t, null, com.pullenti.ner.measure.internal.NumberWithUnitParseAttr.NO);
            if (num != null && num.singleVal != null) {
                if (num.units.size() == 1 && num.units.get(0).unit != null && com.pullenti.unisharp.Utils.stringsEq(num.units.get(0).unit.nameCyr, "мм")) 
                    return _new2972(t, num.getEndToken(), Typs.CALIBER, com.pullenti.ner.core.NumberHelper.doubleToString(num.singleVal));
                if (num.getEndToken().getNext() != null && num.getEndToken().getNext().isValue("КАЛИБР", "КАЛІБР")) 
                    return _new2972(t, num.getEndToken().getNext(), Typs.CALIBER, com.pullenti.ner.core.NumberHelper.doubleToString(num.singleVal));
            }
        }
        if (t.isValue("ПРОИЗВОДСТВО", "ВИРОБНИЦТВО")) {
            com.pullenti.ner.Token tt1 = t.getNext();
            if (tt1 != null && ((tt1.isHiphen() || tt1.isChar(':')))) 
                tt1 = tt1.getNext();
            if (tt1 instanceof com.pullenti.ner.ReferentToken) {
                if ((tt1.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) || (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                    return _new2977(t, tt1, Typs.DEVELOPER, tt1.getReferent());
            }
        }
        return null;
    }

    private void _correctModel() {
        com.pullenti.ner.Token tt = getEndToken().getNext();
        if (tt == null || tt.getWhitespacesBeforeCount() > 2) 
            return;
        if (tt.isValue(":\\/.", null) || tt.isHiphen()) 
            tt = tt.getNext();
        if (tt instanceof com.pullenti.ner.NumberToken) {
            StringBuilder tmp = new StringBuilder();
            tmp.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
            boolean isLat = com.pullenti.morph.LanguageHelper.isLatinChar(value.charAt(0));
            setEndToken(tt);
            for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.chars.isLetter()) {
                    if (!tt.isWhitespaceBefore() || ((tt.getPrevious() != null && tt.getPrevious().isHiphen()))) {
                        char ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term.charAt(0);
                        setEndToken(tt);
                        char ch2 = (char)0;
                        if (com.pullenti.morph.LanguageHelper.isLatinChar(ch) && !isLat) {
                            ch2 = com.pullenti.morph.LanguageHelper.getCyrForLat(ch);
                            if (ch2 != ((char)0)) 
                                ch = ch2;
                        }
                        else if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch) && isLat) {
                            ch2 = com.pullenti.morph.LanguageHelper.getLatForCyr(ch);
                            if (ch2 != ((char)0)) 
                                ch = ch2;
                        }
                        tmp.append(ch);
                        continue;
                    }
                }
                break;
            }
            value = (value + "-" + tmp.toString());
            altValue = com.pullenti.ner.core.MiscHelper.createCyrLatAlternative(value);
        }
        if (!getEndToken().isWhitespaceAfter() && getEndToken().getNext() != null && ((this.getEndToken().getNext().isHiphen() || this.getEndToken().getNext().isCharOf("\\/")))) {
            if (!getEndToken().getNext().isWhitespaceAfter() && (getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                setEndToken(this.getEndToken().getNext().getNext());
                value = (value + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(this.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue());
                if (altValue != null) 
                    altValue = (altValue + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(this.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue());
            }
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        com.pullenti.ner.core.Termin tt;
        java.util.ArrayList<com.pullenti.ner.core.Termin> li;
        t = com.pullenti.ner.core.Termin._new100("ПИСТОЛЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("РЕВОЛЬВЕР", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВИНТОВКА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("РУЖЬЕ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("АВТОМАТ", Typs.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("КАРАБИН", Typs.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new126("ПИСТОЛЕТ-ПУЛЕМЕТ", "ПИСТОЛЕТ-ПУЛЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПУЛЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ГРАНАТОМЕТ", Typs.NOUN);
        t.addVariant("СТРЕЛКОВО ГРАНАТОМЕТНЫЙ КОМПЛЕКС", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОГНЕМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("МИНОМЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2989("ПЕРЕНОСНОЙ ЗЕНИТНО РАКЕТНЫЙ КОМПЛЕКС", "ПЗРК", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new2989("ПРОТИВОТАНКОВЫЙ РАКЕТНЫЙ КОМПЛЕКС", "ПТРК", Typs.NOUN);
        t.addVariant("ПЕРЕНОСНОЙ ПРОТИВОТАНКОВЫЙ РАКЕТНЫЙ КОМПЛЕКС", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("АВИАЦИОННАЯ ПУШКА", Typs.NOUN);
        t.addVariant("АВИАПУШКА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НАРУЧНИКИ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("БРОНЕЖИЛЕТ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ГРАНАТА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЛИМОНКА", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НОЖ", Typs.NOUN);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЗРЫВАТЕЛЬ", Typs.NOUN);
        m_Ontology.add(t);
        for (String s : new String[] {"МАКАРОВ", "КАЛАШНИКОВ", "СИМОНОВ", "СТЕЧКИН", "ШМАЙСЕР", "МОСИН", "СЛОСТИН", "НАГАН", "МАКСИМ", "ДРАГУНОВ", "СЕРДЮКОВ", "ЯРЫГИН", "НИКОНОВ", "МАУЗЕР", "БРАУНИНГ", "КОЛЬТ", "ВИНЧЕСТЕР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, Typs.BRAND));
        }
        for (String s : new String[] {"УЗИ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, Typs.NAME));
        }
        t = com.pullenti.ner.core.Termin._new3000("ТУЛЬСКИЙ ТОКАРЕВА", "ТТ", "ТТ", Typs.MODEL);
        li = new java.util.ArrayList<com.pullenti.ner.core.Termin>();
        li.add(com.pullenti.ner.core.Termin._new100("ПИСТОЛЕТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new100("ТОКАРЕВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new3000("ПИСТОЛЕТ МАКАРОВА", "ПМ", "ПМ", Typs.MODEL);
        li = new java.util.ArrayList<com.pullenti.ner.core.Termin>();
        li.add(com.pullenti.ner.core.Termin._new100("ПИСТОЛЕТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new100("МАКАРОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new3000("ПИСТОЛЕТ МАКАРОВА МОДЕРНИЗИРОВАННЫЙ", "ПММ", "ПММ", Typs.MODEL);
        li = new java.util.ArrayList<com.pullenti.ner.core.Termin>();
        li.add((tt = com.pullenti.ner.core.Termin._new100("ПИСТОЛЕТ", Typs.NOUN)));
        tt.addVariant("МОДЕРНИЗИРОВАННЫЙ ПИСТОЛЕТ", false);
        li.add(com.pullenti.ner.core.Termin._new100("МАКАРОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new3000("АВТОМАТ КАЛАШНИКОВА", "АК", "АК", Typs.MODEL);
        li = new java.util.ArrayList<com.pullenti.ner.core.Termin>();
        li.add(com.pullenti.ner.core.Termin._new100("АВТОМАТ", Typs.NOUN));
        li.add(com.pullenti.ner.core.Termin._new100("КАЛАШНИКОВ", Typs.BRAND));
        t.tag2 = li;
        m_Ontology.add(t);
    }

    public static WeaponItemToken _new2966(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4, boolean _arg5) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.isInternal = _arg5;
        return res;
    }

    public static WeaponItemToken _new2967(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static WeaponItemToken _new2968(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, boolean _arg4) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static WeaponItemToken _new2972(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static WeaponItemToken _new2977(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, com.pullenti.ner.Referent _arg4) {
        WeaponItemToken res = new WeaponItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static class Typs implements Comparable<Typs> {
    
        public static final Typs NOUN; // 0
    
        public static final Typs BRAND; // 1
    
        public static final Typs MODEL; // 2
    
        public static final Typs NUMBER; // 3
    
        public static final Typs NAME; // 4
    
        public static final Typs CLASS; // 5
    
        public static final Typs DATE; // 6
    
        public static final Typs CALIBER; // 7
    
        public static final Typs DEVELOPER; // 8
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Typs(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(Typs v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Typs> mapIntToEnum; 
        private static java.util.HashMap<String, Typs> mapStringToEnum; 
        private static Typs[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Typs of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Typs item = new Typs(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Typs of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Typs[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Typs>();
            mapStringToEnum = new java.util.HashMap<String, Typs>();
            NOUN = new Typs(0, "NOUN");
            mapIntToEnum.put(NOUN.value(), NOUN);
            mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
            BRAND = new Typs(1, "BRAND");
            mapIntToEnum.put(BRAND.value(), BRAND);
            mapStringToEnum.put(BRAND.m_str.toUpperCase(), BRAND);
            MODEL = new Typs(2, "MODEL");
            mapIntToEnum.put(MODEL.value(), MODEL);
            mapStringToEnum.put(MODEL.m_str.toUpperCase(), MODEL);
            NUMBER = new Typs(3, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            NAME = new Typs(4, "NAME");
            mapIntToEnum.put(NAME.value(), NAME);
            mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
            CLASS = new Typs(5, "CLASS");
            mapIntToEnum.put(CLASS.value(), CLASS);
            mapStringToEnum.put(CLASS.m_str.toUpperCase(), CLASS);
            DATE = new Typs(6, "DATE");
            mapIntToEnum.put(DATE.value(), DATE);
            mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
            CALIBER = new Typs(7, "CALIBER");
            mapIntToEnum.put(CALIBER.value(), CALIBER);
            mapStringToEnum.put(CALIBER.m_str.toUpperCase(), CALIBER);
            DEVELOPER = new Typs(8, "DEVELOPER");
            mapIntToEnum.put(DEVELOPER.value(), DEVELOPER);
            mapStringToEnum.put(DEVELOPER.m_str.toUpperCase(), DEVELOPER);
            java.util.Collection<Typs> col = mapIntToEnum.values();
            m_Values = new Typs[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public WeaponItemToken() {
        super();
    }
    public static WeaponItemToken _globalInstance;
    
    static {
        try { _globalInstance = new WeaponItemToken(); } 
        catch(Exception e) { }
    }
}
