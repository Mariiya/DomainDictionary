/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.transport.internal;

public class TransItemToken extends com.pullenti.ner.MetaToken {

    public TransItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public Typs typ = Typs.NOUN;

    public String value;

    public String altValue;

    public com.pullenti.ner.transport.TransportKind kind = com.pullenti.ner.transport.TransportKind.UNDEFINED;

    public boolean isDoubt;

    public boolean isAfterConjunction;

    public com.pullenti.ner.ReferentToken state;

    public com.pullenti.ner.Referent ref;

    public java.util.ArrayList<Object> routeItems;

    @Override
    public String toString() {
        return (typ.toString() + ": " + ((value != null ? value : ((ref == null ? "" : ref.toString())))) + " " + ((altValue != null ? altValue : "")));
    }

    public static java.util.ArrayList<TransItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount) {
        TransItemToken tr = tryParse(t, null, false, false);
        if (tr == null) 
            return null;
        if ((tr.typ == Typs.ORG || tr.typ == Typs.NUMBER || tr.typ == Typs.CLASS) || tr.typ == Typs.DATE) 
            return null;
        TransItemToken tr0 = tr;
        java.util.ArrayList<TransItemToken> res = new java.util.ArrayList<TransItemToken>();
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
        boolean brareg = false;
        for (; t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            if (tr0.typ == Typs.NOUN || tr0.typ == Typs.ORG) {
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
                    brareg = true;
                    tr = tryParse(t.getNext(), tr0, false, false);
                    if (tr != null) {
                        if (tr.typ != Typs.NUMBER && tr.typ != Typs.GEO) 
                            tr = null;
                        else if (tr.getEndToken().getNext() != null) {
                            tr.setBeginToken(t);
                            if (tr.getEndToken().getNext().isChar(')')) {
                                tr.setEndToken(tr.getEndToken().getNext());
                                brareg = false;
                            }
                        }
                    }
                    if (tr == null) {
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
            }
            if (tr == null && t.isHiphen()) {
                if (tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) 
                    tr = tryParse(t.getNext(), tr0, false, false);
            }
            if (tr == null && t.isComma()) {
                if (((tr0.typ == Typs.NAME || tr0.typ == Typs.BRAND || tr0.typ == Typs.MODEL) || tr0.typ == Typs.CLASS || tr0.typ == Typs.DATE) || tr0.typ == Typs.GEO) {
                    tr = tryParse(t.getNext(), tr0, true, false);
                    if (tr != null) {
                        if (tr.typ == Typs.NUMBER) {
                        }
                        else 
                            tr = null;
                    }
                }
            }
            if (tr == null) {
                if (tr0.typ == Typs.NAME) {
                    if (t.isChar(',')) 
                        tr = tryParse(t.getNext(), tr0, true, false);
                    else if (t.getMorph()._getClass().isConjunction() && t.isAnd()) {
                        tr = tryParse(t.getNext(), tr0, true, false);
                        andConj = true;
                    }
                }
                if (tr != null) {
                    if (tr.typ != Typs.NAME) 
                        break;
                    tr.isAfterConjunction = true;
                }
            }
            if (t.isCommaAnd() && tr == null) {
                TransItemToken ne = tryParse(t.getNext(), tr0, true, false);
                if (ne != null && ne.typ == Typs.NUMBER) {
                    boolean exi = false;
                    for (TransItemToken v : res) {
                        if (v.typ == ne.typ) {
                            exi = true;
                            break;
                        }
                    }
                    if (!exi) 
                        tr = ne;
                }
            }
            if (tr == null && brareg && t.isChar(')')) {
                brareg = false;
                tr0.setEndToken(t);
                continue;
            }
            if (tr == null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false)) {
                tr0.setEndToken(t);
                continue;
            }
            if (tr == null) 
                break;
            if (t.isNewlineBefore()) {
                if (tr.typ != Typs.NUMBER) 
                    break;
            }
            res.add(tr);
            if (tr.typ == Typs.ORG && tr0.typ == Typs.NOUN) {
            }
            else 
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
        if ((res.size() > 1 && res.get(0).typ == Typs.BRAND && res.get(1).typ == Typs.MODEL) && res.get(1).getLengthChar() == 1 && !(res.get(1).getBeginToken() instanceof com.pullenti.ner.NumberToken)) 
            return null;
        return res;
    }

    public static TransItemToken tryParse(com.pullenti.ner.Token t, TransItemToken prev, boolean afterConj, boolean attachHigh) {
        TransItemToken res = _TryParse(t, prev, afterConj, attachHigh);
        if (res == null) 
            return null;
        if (res.typ == Typs.NAME) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.getBeginToken().isChar('(')) {
                String alt = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(res.value, alt)) {
                    res.altValue = alt;
                    res.setEndToken(br.getEndToken());
                }
            }
        }
        if ((res.typ == Typs.BRAND && t.getMorphClassInDictionary().isProperName() && prev == null) && t.chars.isCapitalUpper()) {
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t, null);
            if (rt != null) 
                return null;
        }
        if (res.typ == Typs.NOUN && com.pullenti.unisharp.Utils.stringsEq(res.value, "судно")) {
            if (res.getEndToken().isValue("СУД", null)) {
                if ((res.getWhitespacesAfterCount() < 3) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getEndToken().getNext(), true, false)) {
                }
                else 
                    return null;
            }
        }
        return res;
    }

    private static TransItemToken _TryParse(com.pullenti.ner.Token t, TransItemToken prev, boolean afterConj, boolean attachHigh) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t1 = t;
        if (t1.isChar(',')) 
            t1 = t1.getNext();
        if (t1 != null) {
            if (t1.isValue("ПРИНАДЛЕЖАТЬ", "НАЛЕЖАТИ") || t1.isValue("СУДОВЛАДЕЛЕЦ", "СУДНОВЛАСНИК") || t1.isValue("ВЛАДЕЛЕЦ", "ВЛАСНИК")) 
                t1 = t1.getNext();
        }
        if (t1 instanceof com.pullenti.ner.ReferentToken) {
            if (com.pullenti.unisharp.Utils.stringsEq(t1.getReferent().getTypeName(), "ORGANIZATION")) 
                return _new2882(t, t1, Typs.ORG, t1.getReferent(), t1.getMorph());
        }
        if (t1 != null && t1.isValue("ФЛАГ", null)) {
            com.pullenti.ner.Token tt = t1.getNext();
            while (tt != null) {
                if (tt.isHiphen() || tt.isChar(':')) 
                    tt = tt.getNext();
                else 
                    break;
            }
            if ((tt instanceof com.pullenti.ner.ReferentToken) && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                return _new2883(t, tt, Typs.GEO, tt.getReferent());
        }
        if (t1 != null && t1.isValue("ПОРТ", null)) {
            com.pullenti.ner.Token tt = t1.getNext();
            for (; tt != null; tt = tt.getNext()) {
                if (tt.isValue("ПРИПИСКА", null) || tt.isChar(':')) {
                }
                else 
                    break;
            }
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                return _new2883(t, tt, Typs.GEO, tt.getReferent());
        }
        boolean route = false;
        if (t1 != null && ((t1.isValue("СЛЕДОВАТЬ", "СЛІДУВАТИ") || t1.isValue("ВЫПОЛНЯТЬ", "ВИКОНУВАТИ")))) {
            t1 = t1.getNext();
            route = true;
        }
        if (t1 != null && t1.getMorph()._getClass().isPreposition()) 
            t1 = t1.getNext();
        if (t1 != null && ((t1.isValue("РЕЙС", null) || t1.isValue("МАРШРУТ", null)))) {
            t1 = t1.getNext();
            route = true;
        }
        if (t1 instanceof com.pullenti.ner.ReferentToken) {
            if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (_geo.isState() || _geo.isCity()) {
                    TransItemToken tit = _new2885(t, t1, Typs.ROUTE, new java.util.ArrayList<Object>());
                    tit.routeItems.add(_geo);
                    for (t1 = t1.getNext(); t1 != null; t1 = t1.getNext()) {
                        if (t1.isHiphen()) 
                            continue;
                        if (t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()) 
                            continue;
                        _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                        if (_geo == null) 
                            break;
                        if (!_geo.isCity() && !_geo.isState()) 
                            break;
                        tit.routeItems.add(_geo);
                        tit.setEndToken(t1);
                    }
                    if (tit.routeItems.size() > 1 || route) 
                        return tit;
                }
            }
            else if ((t1.getReferent() instanceof com.pullenti.ner.date.DateReferent) && (t1.getWhitespacesBeforeCount() < 3)) {
                TransItemToken tit = _new2883(t, t1, Typs.DATE, t1.getReferent());
                if (t1.getNext() != null) {
                    if (t1.getNext().isValue("В", null) && t1.getNext().getNext() != null && t1.getNext().getNext().isChar('.')) 
                        tit.setEndToken(t1.getNext().getNext());
                    else if (t1.getNext().isValue("ВЫП", null) || t1.getNext().isValue("ВЫПУСК", null)) {
                        tit.setEndToken(t1.getNext());
                        if (t1.getNext().getNext() != null && t1.getNext().getNext().isChar('.')) 
                            tit.setEndToken(t1.getNext().getNext());
                    }
                }
                return tit;
            }
        }
        if (t instanceof com.pullenti.ner.TextToken) {
            com.pullenti.ner.Token num = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
            if (num != null) {
                TransItemToken tit = _attachRusAutoNumber(num);
                if (tit == null) 
                    tit = _attachNumber(num, false);
                if (tit != null) {
                    tit.setBeginToken(t);
                    return tit;
                }
            }
            com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null && ((t.isValue("С", null) || t.isValue("C", null) || t.isValue("ЗА", null)))) 
                tok = m_Ontology.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            boolean isBr = false;
            if (tok == null && com.pullenti.ner.core.BracketHelper.isBracket(t, true)) {
                com.pullenti.ner.core.TerminToken tok1 = m_Ontology.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null && com.pullenti.ner.core.BracketHelper.isBracket(tok1.getEndToken().getNext(), true)) {
                    tok = tok1;
                    tok.setBeginToken(t);
                    tok.setEndToken(tok.getEndToken().getNext());
                    tok.setBeginToken(t);
                    isBr = true;
                }
                else if (tok1 != null) {
                    TransTermin tt = (TransTermin)com.pullenti.unisharp.Utils.cast(tok1.termin, TransTermin.class);
                    if (tt.typ == Typs.BRAND) {
                        tok = tok1;
                        tok.setBeginToken(t);
                    }
                }
                if (tok != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tok.getEndToken().getNext(), true, null, false)) {
                    tok.setEndToken(tok.getEndToken().getNext());
                    isBr = true;
                }
            }
            if (tok == null && t.isValue("МАРКА", null)) {
                TransItemToken res1 = _TryParse(t.getNext(), prev, afterConj, false);
                if (res1 != null) {
                    if (res1.typ == Typs.NAME || res1.typ == Typs.BRAND) {
                        res1.setBeginToken(t);
                        res1.typ = Typs.BRAND;
                        return res1;
                    }
                }
            }
            if (tok != null) {
                TransTermin tt = (TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class);
                TransItemToken tit;
                if (tt.typ == Typs.NUMBER) {
                    tit = _attachRusAutoNumber(tok.getEndToken().getNext());
                    if (tit == null) 
                        tit = _attachNumber(tok.getEndToken().getNext(), false);
                    if (tit != null) {
                        tit.setBeginToken(t);
                        return tit;
                    }
                    else 
                        return null;
                }
                if (tt.isDoubt && !attachHigh) {
                    if (prev == null || prev.typ != Typs.NOUN) {
                        if ((prev != null && prev.typ == Typs.BRAND && tt.typ == Typs.BRAND) && com.pullenti.unisharp.Utils.stringsCompare(tt.getCanonicText(), prev.value, true) == 0) {
                        }
                        else 
                            return null;
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(tt.getCanonicText(), "СУДНО")) {
                    if (((short)((tok.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tok.getEndToken().getNext(), false, false)) 
                            return null;
                    }
                }
                tit = _new2887(tok.getBeginToken(), tok.getEndToken(), tt.kind, tt.typ, tt.isDoubt && !isBr, tok.chars, tok.getMorph());
                tit.value = tt.getCanonicText();
                if (tit.typ == Typs.NOUN) {
                    tit.value = tit.value.toLowerCase();
                    if (((tit.getEndToken().getNext() != null && tit.getEndToken().getNext().isHiphen() && !tit.getEndToken().isWhitespaceAfter()) && (tit.getEndToken().getNext().getNext() instanceof com.pullenti.ner.TextToken) && !tit.getEndToken().getNext().isWhitespaceAfter()) && tit.getEndToken().getNext().getNext().getMorphClassInDictionary().isNoun()) {
                        tit.setEndToken(tit.getEndToken().getNext().getNext());
                        tit.value = (tit.value + "-" + ((String)com.pullenti.unisharp.Utils.notnull(tit.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), "?"))).toLowerCase();
                    }
                }
                else 
                    tit.value = tit.value.toUpperCase();
                return tit;
            }
            if (tok == null && t.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.adjectives.size() > 0) {
                    com.pullenti.ner.ReferentToken _state = null;
                    for (com.pullenti.ner.Token tt = t; tt != null && tt.getPrevious() != npt.getEndToken(); tt = tt.getNext()) {
                        tok = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok == null && _state == null) 
                            _state = tt.kit.processReferent("GEO", tt, null);
                        if (tok != null && tok.getEndToken() == npt.getEndToken()) {
                            if (((TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class)).typ == Typs.NOUN) {
                                TransItemToken tit = _new2887(t, tok.getEndToken(), ((TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class)).kind, Typs.NOUN, ((TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class)).isDoubt, tok.chars, npt.getMorph());
                                tit.value = ((TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class)).getCanonicText().toLowerCase();
                                tit.altValue = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                                if (com.pullenti.morph.LanguageHelper.endsWithEx(tit.altValue, "суд", "суда", null, null)) {
                                    if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tok.getEndToken().getNext(), false, false)) 
                                        continue;
                                }
                                if (_state != null) {
                                    if (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(_state.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) 
                                        tit.state = _state;
                                }
                                return tit;
                            }
                        }
                    }
                }
            }
        }
        if (t != null && t.isValue("КЛАСС", null) && t.getNext() != null) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) 
                return _new2889(t, br.getEndToken(), Typs.CLASS, com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO));
        }
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        if (nt != null) {
            if (prev == null || nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
                return null;
            if (prev.typ == Typs.BRAND) 
                return _attachModel(t, false, prev);
            else 
                return null;
        }
        TransItemToken res;
        if ((((res = _attachRusAutoNumber(t)))) != null) {
            if (!res.isDoubt) 
                return res;
            if (prev != null && prev.typ == Typs.NOUN && prev.kind == com.pullenti.ner.transport.TransportKind.AUTO) 
                return res;
            if (prev != null && ((prev.typ == Typs.BRAND || prev.typ == Typs.MODEL))) 
                return res;
        }
        t1 = t;
        if (t.isHiphen()) 
            t1 = t.getNext();
        if (prev != null && prev.typ == Typs.BRAND && t1 != null) {
            TransItemToken tit = _attachModel(t1, true, prev);
            if (tit != null) {
                tit.setBeginToken(t);
                return tit;
            }
        }
        if (prev != null && ((prev.typ == Typs.NOUN || afterConj))) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.isQuoteType()) {
                TransItemToken tit = tryParse(br.getBeginToken().getNext(), prev, afterConj, false);
                if (tit != null && tit.getEndToken().getNext() == br.getEndToken()) {
                    if (!tit.isDoubt || tit.typ == Typs.BRAND) {
                        tit.setBeginToken(br.getBeginToken());
                        tit.setEndToken(br.getEndToken());
                        return tit;
                    }
                }
                String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s) && (s.length() < 30)) {
                    int _chars = 0;
                    int digs = 0;
                    int un = 0;
                    for (char c : s.toCharArray()) {
                        if (!com.pullenti.unisharp.Utils.isWhitespace(c)) {
                            if (Character.isLetter(c)) 
                                _chars++;
                            else if (Character.isDigit(c)) 
                                digs++;
                            else 
                                un++;
                        }
                    }
                    if (((digs == 0 && un == 0 && t.getNext().chars.isCapitalUpper())) || prev.kind == com.pullenti.ner.transport.TransportKind.SHIP || prev.kind == com.pullenti.ner.transport.TransportKind.SPACE) 
                        return _new2889(br.getBeginToken(), br.getEndToken(), Typs.NAME, s);
                    if (digs > 0 && (_chars < 5)) 
                        return _new2889(br.getBeginToken(), br.getEndToken(), Typs.MODEL, s.replace(" ", ""));
                }
            }
        }
        if (prev != null && (((prev.typ == Typs.NOUN || prev.typ == Typs.BRAND || prev.typ == Typs.NAME) || prev.typ == Typs.MODEL))) {
            TransItemToken tit = _attachModel(t, prev.typ != Typs.NAME, prev);
            if (tit != null) 
                return tit;
        }
        if (((prev != null && prev.typ == Typs.NOUN && prev.kind == com.pullenti.ner.transport.TransportKind.AUTO) && (t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) && !t.chars.isAllLower() && (t.getWhitespacesBeforeCount() < 2)) {
            com.pullenti.ner.ReferentToken pt = t.kit.processReferent("PERSON", t, null);
            if (pt == null) {
                TransItemToken tit = _new2892(t, t, Typs.BRAND);
                tit.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isNoun()) 
                    tit.isDoubt = true;
                return tit;
            }
        }
        if (((prev != null && prev.typ == Typs.NOUN && ((prev.kind == com.pullenti.ner.transport.TransportKind.SHIP || prev.kind == com.pullenti.ner.transport.TransportKind.SPACE)))) || afterConj) {
            if (t.chars.isCapitalUpper()) {
                boolean ok = true;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.adjectives.size() > 0) 
                    ok = false;
                else {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t, null);
                    if (rt != null) {
                        if (prev.chars.isCyrillicLetter() && rt.chars.isLatinLetter()) {
                        }
                        else 
                            ok = false;
                    }
                }
                if (t.getMorphClassInDictionary().isProperSurname()) {
                    if (!t.getMorph().getCase().isNominative()) 
                        ok = false;
                }
                if (ok) {
                    t1 = t;
                    TransItemToken tit;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getWhitespacesBeforeCount() > 1) 
                            break;
                        if (!tt.chars.equals(t.chars)) 
                            break;
                        if ((((tit = tryParse(tt, null, false, false)))) != null) 
                            break;
                        t1 = tt;
                    }
                    String s = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.NO);
                    if (s != null) {
                        TransItemToken res1 = _new2893(t, t1, Typs.NAME, true, s);
                        if (!t1.isNewlineAfter()) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) {
                                res1.setEndToken(br.getEndToken());
                                res1.altValue = res1.value;
                                res1.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                            }
                        }
                        return res1;
                    }
                }
            }
        }
        return null;
    }

    private static TransItemToken _attachModel(com.pullenti.ner.Token t, boolean canBeFirstWord, TransItemToken prev) {
        TransItemToken res = _new2892(t, t, Typs.MODEL);
        StringBuilder cyr = new StringBuilder();
        StringBuilder lat = new StringBuilder();
        com.pullenti.ner.Token t0 = t;
        boolean num = false;
        for (; t != null; t = t.getNext()) {
            if (t != t0 && t.getWhitespacesBeforeCount() > 1) 
                break;
            if (t == t0) {
                if (t.isHiphen() || t.chars.isAllLower()) {
                    if (prev == null || prev.typ != Typs.BRAND) 
                        return null;
                }
            }
            else {
                TransItemToken pp = tryParse(t, null, false, false);
                if (pp != null) 
                    break;
            }
            if (t.isHiphen()) {
                num = false;
                continue;
            }
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (nt != null) {
                if (num) 
                    break;
                num = true;
                if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
                    break;
                if (cyr != null) 
                    cyr.append(nt.getValue());
                if (lat != null) 
                    lat.append(nt.getValue());
                res.setEndToken(t);
                continue;
            }
            if (t != t0 && tryParse(t, null, false, false) != null) 
                break;
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            if (num && t.isWhitespaceBefore()) 
                break;
            num = false;
            com.pullenti.ner.core.MiscHelper.CyrLatWord vv = com.pullenti.ner.core.MiscHelper.getCyrLatWord(t, 3);
            if (vv == null) {
                if (canBeFirstWord && t == t0) {
                    if (t.chars.isLetter() && t.chars.isCapitalUpper()) {
                        if ((((vv = com.pullenti.ner.core.MiscHelper.getCyrLatWord(t, 0)))) != null) {
                            if (t.getMorph().getCase().isGenitive() && ((prev == null || prev.typ != Typs.BRAND))) 
                                vv = null;
                            else if (prev != null && prev.typ == Typs.NOUN && ((prev.kind == com.pullenti.ner.transport.TransportKind.SHIP || prev.kind == com.pullenti.ner.transport.TransportKind.SPACE))) 
                                vv = null;
                            else 
                                res.isDoubt = true;
                        }
                    }
                    if (((vv == null && (t instanceof com.pullenti.ner.TextToken) && !t.chars.isAllLower()) && t.chars.isLatinLetter() && prev != null) && prev.typ == Typs.BRAND) {
                        lat.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                        res.setEndToken(t);
                        continue;
                    }
                }
                if (vv == null) 
                    break;
            }
            if ((vv.getLength() < 4) || t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) {
                if (t.isWhitespaceBefore() && t.isWhitespaceAfter()) {
                    if (t.getPrevious() != null && !t.getPrevious().isHiphen()) {
                        if (t.chars.isAllLower()) 
                            break;
                    }
                }
            }
            if (cyr != null) {
                if (vv.cyrWord != null) 
                    cyr.append(vv.cyrWord);
                else 
                    cyr = null;
            }
            if (lat != null) {
                if (vv.latWord != null) 
                    lat.append(vv.latWord);
                else 
                    lat = null;
            }
            res.setEndToken(t);
        }
        if (lat == null && cyr == null) 
            return null;
        if (lat != null && lat.length() > 0) {
            res.value = lat.toString();
            if (cyr != null && cyr.length() > 0 && com.pullenti.unisharp.Utils.stringsNe(res.value, cyr.toString())) 
                res.altValue = cyr.toString();
        }
        else if (cyr != null && cyr.length() > 0) 
            res.value = cyr.toString();
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(res.value)) 
            return null;
        if (res.kit.processReferent("PERSON", res.getBeginToken(), null) != null) 
            return null;
        return res;
    }

    public static TransItemToken _attachNumber(com.pullenti.ner.Token t, boolean ignoreRegion) {
        if (t == null) 
            return null;
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                TransItemToken res1 = _attachNumber(t.getNext(), false);
                if (res1 != null && res1.getEndToken().getNext() == br.getEndToken()) {
                    res1.setBeginToken(t);
                    res1.setEndToken(br.getEndToken());
                    return res1;
                }
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        if (t.isValue("НА", null)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.noun.isValue("ФОН", null)) 
                t = npt.getEndToken().getNext();
        }
        StringBuilder res = null;
        for (; t != null; t = t.getNext()) {
            if (t.isNewlineBefore()) 
                break;
            if (t != t0 && t.getWhitespacesBeforeCount() > 1) 
                break;
            if (t.isHiphen()) 
                continue;
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (nt != null) {
                if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT || nt.getMorph()._getClass().isAdjective()) 
                    break;
                if (res == null) 
                    res = new StringBuilder();
                else if (Character.isDigit(res.charAt(res.length() - 1))) 
                    res.append(' ');
                res.append(nt.getSourceText());
                t1 = t;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) {
                if ((t instanceof com.pullenti.ner.MetaToken) && (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken().getLengthChar() < 3) && (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken() instanceof com.pullenti.ner.TextToken)) 
                    tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken(), com.pullenti.ner.TextToken.class);
                else 
                    break;
            }
            if (!tt.chars.isLetter()) 
                break;
            if (!tt.chars.isAllUpper() && tt.isWhitespaceBefore()) 
                break;
            if (tt.getLengthChar() > 3) 
                break;
            if (res == null) 
                res = new StringBuilder();
            res.append(tt.term);
            t1 = t;
        }
        if (res == null || (res.length() < 4)) 
            return null;
        TransItemToken re = _new2889(t0, t1, Typs.NUMBER, res.toString());
        if (!ignoreRegion) {
            for (int k = 0, i = res.length() - 1; i > 4; i--,k++) {
                if (!Character.isDigit(res.charAt(i))) {
                    if (res.charAt(i) == ' ' && ((k == 2 || k == 3))) {
                        re.altValue = re.value.substring(i + 1);
                        re.value = re.value.substring(0, 0 + i);
                    }
                    break;
                }
            }
        }
        re.value = re.value.replace(" ", "");
        if (ignoreRegion) 
            re.altValue = com.pullenti.ner.core.MiscHelper.createCyrLatAlternative(re.value);
        return re;
    }

    public static TransItemToken _attachRusAutoNumber(com.pullenti.ner.Token t) {
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                TransItemToken res1 = _attachRusAutoNumber(t.getNext());
                if (res1 != null && res1.getEndToken().getNext() == br.getEndToken()) {
                    res1.setBeginToken(t);
                    res1.setEndToken(br.getEndToken());
                    return res1;
                }
            }
        }
        com.pullenti.ner.core.MiscHelper.CyrLatWord v1 = com.pullenti.ner.core.MiscHelper.getCyrLatWord(t, 1);
        if (v1 == null || v1.cyrWord == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        int doubt = 0;
        if (!t.chars.isAllUpper() || t.isWhitespaceAfter()) 
            doubt++;
        t = t.getNext();
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        if ((nt == null || nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT || nt.getMorph()._getClass().isAdjective()) || (nt.getEndChar() - nt.getBeginChar()) != 2) 
            return null;
        t = t.getNext();
        com.pullenti.ner.core.MiscHelper.CyrLatWord v2 = com.pullenti.ner.core.MiscHelper.getCyrLatWord(t, 2);
        if (v2 == null || v2.cyrWord == null || v2.getLength() != 2) 
            return null;
        if (!t.chars.isAllUpper() || t.isWhitespaceAfter()) 
            doubt++;
        TransItemToken res = _new2896(t0, t, Typs.NUMBER, com.pullenti.ner.transport.TransportKind.AUTO);
        res.value = (v1.cyrWord + nt.getSourceText() + v2.cyrWord);
        nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
        if (((nt != null && nt.getIntValue() != null && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT) && !nt.getMorph()._getClass().isAdjective() && nt.getIntValue() != null) && (nt.getIntValue() < 1000) && (t.getWhitespacesAfterCount() < 2)) {
            String n = nt.getValue();
            if (n.length() < 2) 
                n = "0" + n;
            res.altValue = n;
            res.setEndToken(nt);
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("RUS", null)) {
            res.setEndToken(res.getEndToken().getNext());
            doubt = 0;
        }
        if (doubt > 1) 
            res.isDoubt = true;
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static com.pullenti.ner.Token checkNumberKeyword(com.pullenti.ner.Token t) {
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        TransTermin tt = (TransTermin)com.pullenti.unisharp.Utils.cast(tok.termin, TransTermin.class);
        if (tt != null && tt.typ == Typs.NUMBER) 
            return tok.getEndToken().getNext();
        return null;
    }

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        TransTermin t;
        t = TransTermin._new2897("автомобиль", true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.AUTO);
        t.addAbridge("а-м");
        t.addVariant("автомашина", false);
        t.addVariant("ТРАНСПОРТНОЕ СРЕДСТВО", false);
        t.addVariant("автомобіль", false);
        m_Ontology.add(t);
        for (String s : new String[] {"ВНЕДОРОЖНИК", "ПОЗАШЛЯХОВИК", "АВТОБУС", "МИКРОАВТОБУС", "ГРУЗОВИК", "МОТОЦИКЛ", "МОПЕД"}) {
            m_Ontology.add(TransTermin._new2897(s, true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.AUTO));
        }
        t = TransTermin._new2897("", true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.AUTO);
        t.addAbridge("а-м");
        m_Ontology.add(t);
        t = TransTermin._new2900("государственный номер", true, Typs.NUMBER, "ИМО");
        t.addAbridge("г-н");
        t.addAbridge("н\\з");
        t.addAbridge("г\\н");
        t.addVariant("госномер", false);
        t.addAbridge("гос.номер");
        t.addAbridge("гос.ном.");
        t.addAbridge("г.н.з.");
        t.addAbridge("г.р.з.");
        t.addVariant("ГРЗ", false);
        t.addVariant("ГНЗ", false);
        t.addVariant("регистрационный знак", false);
        t.addAbridge("рег. знак");
        t.addVariant("государственный регистрационный знак", false);
        t.addVariant("бортовой номер", false);
        m_Ontology.add(t);
        t = TransTermin._new2901("державний номер", true, Typs.NUMBER, com.pullenti.morph.MorphLang.UA);
        t.addVariant("держномер", false);
        t.addAbridge("держ.номер");
        t.addAbridge("держ.ном.");
        m_Ontology.add(t);
        t = TransTermin._new2902("номер", true, Typs.NUMBER);
        m_Ontology.add(t);
        for (String s : new String[] {"КРУИЗНЫЙ ЛАЙНЕР", "ТЕПЛОХОД", "ПАРОХОД", "ЯХТА", "ЛОДКА", "КАТЕР", "КОРАБЛЬ", "СУДНО", "ПОДВОДНАЯ ЛОДКА", "АПК", "ШХУНА", "ПАРОМ", "КРЕЙСЕР", "АВИАНОСЕЦ", "ЭСМИНЕЦ", "ФРЕГАТ", "ЛИНКОР", "АТОМОХОД", "ЛЕДОКОЛ", "ПЛАВБАЗА", "ТАНКЕР", "СУПЕРТАНКЕР", "СУХОГРУЗ", "ТРАУЛЕР", "РЕФРИЖЕРАТОР"}) {
            m_Ontology.add((t = TransTermin._new2897(s, true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.SHIP)));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "АПК")) 
                t.isDoubt = true;
        }
        for (String s : new String[] {"КРУЇЗНИЙ ЛАЙНЕР", "ПАРОПЛАВ", "ПАРОПЛАВ", "ЯХТА", "ЧОВЕН", "КОРАБЕЛЬ", "СУДНО", "ПІДВОДНИЙ ЧОВЕН", "АПК", "ШХУНА", "ПОРОМ", "КРЕЙСЕР", "АВІАНОСЕЦЬ", "ЕСМІНЕЦЬ", "ФРЕГАТ", "ЛІНКОР", "АТОМОХІД", "КРИГОЛАМ", "ПЛАВБАЗА", "ТАНКЕР", "СУПЕРТАНКЕР", "СУХОВАНТАЖ", "ТРАУЛЕР", "РЕФРИЖЕРАТОР"}) {
            m_Ontology.add((t = TransTermin._new2904(s, true, Typs.NOUN, com.pullenti.morph.MorphLang.UA, com.pullenti.ner.transport.TransportKind.SHIP)));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "АПК")) 
                t.isDoubt = true;
        }
        for (String s : new String[] {"САМОЛЕТ", "АВИАЛАЙНЕР", "ИСТРЕБИТЕЛЬ", "БОМБАРДИРОВЩИК", "ВЕРТОЛЕТ"}) {
            m_Ontology.add(TransTermin._new2897(s, true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.FLY));
        }
        for (String s : new String[] {"ЛІТАК", "АВІАЛАЙНЕР", "ВИНИЩУВАЧ", "БОМБАРДУВАЛЬНИК", "ВЕРТОЛІТ"}) {
            m_Ontology.add(TransTermin._new2904(s, true, Typs.NOUN, com.pullenti.morph.MorphLang.UA, com.pullenti.ner.transport.TransportKind.FLY));
        }
        for (String s : new String[] {"КОСМИЧЕСКИЙ КОРАБЛЬ", "ЗВЕЗДОЛЕТ", "КОСМИЧЕСКАЯ СТАНЦИЯ", "РАКЕТА-НОСИТЕЛЬ"}) {
            m_Ontology.add(TransTermin._new2897(s, true, Typs.NOUN, com.pullenti.ner.transport.TransportKind.SPACE));
        }
        for (String s : new String[] {"КОСМІЧНИЙ КОРАБЕЛЬ", "ЗОРЕЛІТ", "КОСМІЧНА СТАНЦІЯ", "РАКЕТА-НОСІЙ"}) {
            m_Ontology.add(TransTermin._new2904(s, true, Typs.NOUN, com.pullenti.morph.MorphLang.UA, com.pullenti.ner.transport.TransportKind.SPACE));
        }
        _loadBrands(m_Cars, com.pullenti.ner.transport.TransportKind.AUTO);
        _loadBrands(m_Flys, com.pullenti.ner.transport.TransportKind.FLY);
    }

    public static class TransTermin extends com.pullenti.ner.core.Termin {
    
        public com.pullenti.ner.transport.TransportKind kind = com.pullenti.ner.transport.TransportKind.UNDEFINED;
    
        public Typs typ = Typs.NOUN;
    
        public boolean isDoubt;
    
        public TransTermin(String source, boolean addLemmaVariant) {
            super(null, null, false);
            this.initByNormalText(source, null);
        }
    
        public static TransTermin _new2897(String _arg1, boolean _arg2, Typs _arg3, com.pullenti.ner.transport.TransportKind _arg4) {
            TransTermin res = new TransTermin(_arg1, _arg2);
            res.typ = _arg3;
            res.kind = _arg4;
            return res;
        }
    
        public static TransTermin _new2900(String _arg1, boolean _arg2, Typs _arg3, String _arg4) {
            TransTermin res = new TransTermin(_arg1, _arg2);
            res.typ = _arg3;
            res.acronym = _arg4;
            return res;
        }
    
        public static TransTermin _new2901(String _arg1, boolean _arg2, Typs _arg3, com.pullenti.morph.MorphLang _arg4) {
            TransTermin res = new TransTermin(_arg1, _arg2);
            res.typ = _arg3;
            res.lang = _arg4;
            return res;
        }
    
        public static TransTermin _new2902(String _arg1, boolean _arg2, Typs _arg3) {
            TransTermin res = new TransTermin(_arg1, _arg2);
            res.typ = _arg3;
            return res;
        }
    
        public static TransTermin _new2904(String _arg1, boolean _arg2, Typs _arg3, com.pullenti.morph.MorphLang _arg4, com.pullenti.ner.transport.TransportKind _arg5) {
            TransTermin res = new TransTermin(_arg1, _arg2);
            res.typ = _arg3;
            res.lang = _arg4;
            res.kind = _arg5;
            return res;
        }
        public TransTermin() {
            super();
        }
    }


    private static void _loadBrands(String str, com.pullenti.ner.transport.TransportKind _kind) {
        String[] cars = com.pullenti.unisharp.Utils.split(str, String.valueOf(';'), false);
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        for (String c : cars) {
            String[] its = com.pullenti.unisharp.Utils.split(c, String.valueOf(','), false);
            vars.clear();
            boolean doubt = false;
            for (String it : its) {
                String s = it.trim();
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s, "true")) 
                        doubt = true;
                    else 
                        vars.add(s);
                }
            }
            if (vars.size() == 0) 
                continue;
            for (String v : vars) {
                TransTermin t = new TransTermin(v, false);
                t.setCanonicText(vars.get(0));
                t.kind = _kind;
                t.typ = Typs.BRAND;
                t.isDoubt = doubt;
                m_Ontology.add(t);
            }
        }
    }

    private static String m_Flys = "\n        Boeing, Боинг;\n        Airbus, Аэробус, Эрбас;\n        Ил, Илюшин, true;\n        Ту, Туполев, true;\n        Ан, Антонов, true;\n        Су, Сухой, Sukhoi, Sukhoy, true;\n        Як, Яковлев, true;\n        BAE Systems, БАЕ Системз;\n        ATR, АТР, true;\n        AVIC;\n        Bombardier, Бомбардье;  \n        Britten-Norman, Бриттен-Норман;\n        Cessna, Цессна;\n        Dornier, Дорнье;\n        Embraer, Эмбраер;\n        Fairchild, Fairchild Aerospace, Фэйрчайлд;\n        Fokker, Фоккер;\n        Hawker Beechcraft, Хокер Бичкрафт;\n        Indonesian Aerospace, Индонезиан;\n        Lockheed Martin, Локхид Мартин;\n        LZ Auronautical Industries, LET;\n        Douglas, McDonnell Douglas, Дуглас;\n        NAMC, НАМК;\n        Pilatus, Пилатус, true;\n        Piper Aircraft;\n        Saab, Сааб, true;\n        Shorts, Шортс, true;\n";

    private static String m_Cars = "\n        AC Cars;\n        Acura, Акура;\n        Abarth;\n        Alfa Romeo, Альфа Ромео;\n        ALPINA, Альпина, true;\n        Ariel Motor, Ариэль Мотор;\n        ARO, true;\n        Artega, true;\n        Aston Martin;\n        AUDI, Ауди;\n        Austin Healey;\n        BAW;\n        Beijing Jeep;\n        Bentley, Бентли;\n        Bitter, Биттер, true;\n        BMW, БМВ;\n        Brilliance;\n        Bristol, Бристоль, true;\n        Bugatti, Бугатти;\n        Buick, Бьюик;\n        BYD, true;\n        Cadillac, Кадиллак, Кадилак;\n        Caterham;\n        Chery, trye;\n        Chevrolet, Шевроле, Шеврале;\n        Chrysler, Крайслер;\n        Citroen, Ситроен, Ситроэн;\n        Dacia;\n        DADI;\n        Daewoo, Дэо;\n        Dodge, Додж;\n        Daihatsu;\n        Daimler, Даймлер;\n        DKW;\n        Derways;\n        Eagle, true;\n        Elfin Sports Cars;\n        FAW, true;\n        Ferrari, Феррари, Ферари;\n        FIAT, Фиат;\n        Fisker Karma;\n        Ford, Форд;\n        Geely;\n        GEO, true;\n        GMC, true;\n        Gonow;\n        Great Wall, true;\n        Gumpert;\n        Hafei;\n        Haima;\n        Honda, Хонда;\n        Horch;\n        Hudson, true;\n        Hummer, Хаммер;\n        Harley, Харлей;\n        Hyundai, Хюндай, Хундай;\n        Infiniti, true;\n        Isuzu, Исузу;\n        Jaguar, Ягуар, true;\n        Jeep, Джип, true;\n        Kia, Киа, true;\n        Koenigsegg;\n        Lamborghini, Ламборджини;\n        Land Rover, Лендровер, Лэндровер;\n        Landwind;\n        Lancia;\n        Lexus, Лексус;\n        Leyland;\n        Lifan;\n        Lincoln, Линкольн, true;\n        Lotus, true;\n        Mahindra;\n        Maserati;\n        Maybach;\n        Mazda, Мазда;\n        Mercedes-Benz, Mercedes, Мерседес, Мэрседес, Мерседес-бенц;\n        Mercury, true;\n        Mini, true;\n        Mitsubishi, Mitsubishi Motors, Мицубиши, Мицубиси;\n        Morgan, true;\n        Nissan, Nissan Motor, Ниссан, Нисан;\n        Opel, Опель;\n        Pagani;\n        Peugeot, Пежо;\n        Plymouth;\n        Pontiac, Понтиак;\n        Porsche, Порше;\n        Renault, Рено;\n        Rinspeed;\n        Rolls-Royce, Роллс-Ройс;\n        SAAB, Сааб;\n        Saleen;\n        Saturn, Сатурн, true;\n        Scion;\n        Seat, true;\n        Skoda, Шкода;\n        Smart, true;\n        Spyker, true;\n        Ssang Yong, Ссанг янг;\n        Subaru, Субару;\n        Suzuki, Судзуки;\n        Tesla, true;\n        Toyota, Тойота;\n        Vauxhall;\n        Volkswagen, Фольксваген;\n        Volvo, Вольво;\n        Wartburg;\n        Wiesmann;\n        Yamaha, Ямаха;\n        Zenvo;\n\n        ВАЗ, VAZ;\n        ГАЗ, GAZ, true;\n        ЗАЗ, ZAZ;\n        ЗИЛ, ZIL;\n        АЗЛК, AZLK;\n        Иж, true;\n        Москвич, true;\n        УАЗ, UAZ;\n        ТАГАЗ, TaGAZ;\n        Лада, Жигули, true;\n\n";

    public static TransItemToken _new2882(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, com.pullenti.ner.Referent _arg4, com.pullenti.ner.MorphCollection _arg5) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static TransItemToken _new2883(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, com.pullenti.ner.Referent _arg4) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static TransItemToken _new2885(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, java.util.ArrayList<Object> _arg4) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.routeItems = _arg4;
        return res;
    }

    public static TransItemToken _new2887(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.transport.TransportKind _arg3, Typs _arg4, boolean _arg5, com.pullenti.morph.CharsInfo _arg6, com.pullenti.ner.MorphCollection _arg7) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.kind = _arg3;
        res.typ = _arg4;
        res.isDoubt = _arg5;
        res.chars = _arg6;
        res.setMorph(_arg7);
        return res;
    }

    public static TransItemToken _new2889(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static TransItemToken _new2892(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static TransItemToken _new2893(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, boolean _arg4, String _arg5) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isDoubt = _arg4;
        res.value = _arg5;
        return res;
    }

    public static TransItemToken _new2896(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, com.pullenti.ner.transport.TransportKind _arg4) {
        TransItemToken res = new TransItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.kind = _arg4;
        return res;
    }

    public static class Typs implements Comparable<Typs> {
    
        public static final Typs NOUN; // 0
    
        public static final Typs BRAND; // 1
    
        public static final Typs MODEL; // 2
    
        public static final Typs NUMBER; // 3
    
        public static final Typs NAME; // 4
    
        public static final Typs ORG; // 5
    
        public static final Typs ROUTE; // 6
    
        public static final Typs CLASS; // 7
    
        public static final Typs DATE; // 8
    
        public static final Typs GEO; // 9
    
    
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
            ORG = new Typs(5, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            ROUTE = new Typs(6, "ROUTE");
            mapIntToEnum.put(ROUTE.value(), ROUTE);
            mapStringToEnum.put(ROUTE.m_str.toUpperCase(), ROUTE);
            CLASS = new Typs(7, "CLASS");
            mapIntToEnum.put(CLASS.value(), CLASS);
            mapStringToEnum.put(CLASS.m_str.toUpperCase(), CLASS);
            DATE = new Typs(8, "DATE");
            mapIntToEnum.put(DATE.value(), DATE);
            mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
            GEO = new Typs(9, "GEO");
            mapIntToEnum.put(GEO.value(), GEO);
            mapStringToEnum.put(GEO.m_str.toUpperCase(), GEO);
            java.util.Collection<Typs> col = mapIntToEnum.values();
            m_Values = new Typs[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public TransItemToken() {
        super();
    }
    public static TransItemToken _globalInstance;
    
    static {
        try { _globalInstance = new TransItemToken(); } 
        catch(Exception e) { }
    }
}
