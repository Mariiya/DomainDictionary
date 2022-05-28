/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.phone.internal;

// Примитив, из которых состоит телефонный номер
public class PhoneItemToken extends com.pullenti.ner.MetaToken {

    public PhoneItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public PhoneItemType itemType = PhoneItemType.NUMBER;

    public String value;

    public com.pullenti.ner.phone.PhoneKind kind = com.pullenti.ner.phone.PhoneKind.UNDEFINED;

    public com.pullenti.ner.phone.PhoneKind kind2 = com.pullenti.ner.phone.PhoneKind.UNDEFINED;

    public boolean isInBrackets;

    public boolean canBeCountryPrefix() {
        if (value != null && com.pullenti.unisharp.Utils.stringsEq(PhoneHelper.getCountryPrefix(value), value)) 
            return true;
        else 
            return false;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(itemType.toString()).append(": ").append(value);
        if (kind != com.pullenti.ner.phone.PhoneKind.UNDEFINED) 
            res.append(" (").append(kind.toString()).append(")");
        if (kind2 != com.pullenti.ner.phone.PhoneKind.UNDEFINED) 
            res.append(" (").append(kind2.toString()).append(")");
        return res.toString();
    }

    public static PhoneItemToken tryAttach(com.pullenti.ner.Token t0) {
        PhoneItemToken res = _TryAttach(t0);
        if (res == null) 
            return null;
        if (res.itemType != PhoneItemType.PREFIX) 
            return res;
        for (com.pullenti.ner.Token t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore()) 
                break;
            PhoneItemToken res2 = _TryAttach(t);
            if (res2 != null) {
                if (res2.itemType == PhoneItemType.PREFIX) {
                    if (res.kind == com.pullenti.ner.phone.PhoneKind.UNDEFINED) 
                        res.kind = res2.kind;
                    t = res.setEndToken(res2.getEndToken());
                    continue;
                }
                break;
            }
            if (t.isChar(':')) {
                res.setEndToken(t);
                break;
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            if (t0.getLengthChar() == 1) 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                t = npt.getEndToken();
                if (t.isValue("ПОСЕЛЕНИЕ", null) || t.isValue("СУД", null)) 
                    return null;
                res.setEndToken(t);
                continue;
            }
            if (t.getMorphClassInDictionary().isProper()) {
                res.setEndToken(t);
                continue;
            }
            if (t.getMorph()._getClass().isPreposition()) 
                continue;
            break;
        }
        return res;
    }

    private static PhoneItemToken _TryAttach(com.pullenti.ner.Token t0) {
        if (t0 == null) 
            return null;
        if (t0 instanceof com.pullenti.ner.NumberToken) {
            if (com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t0) != null && !t0.isWhitespaceAfter()) {
                com.pullenti.ner.ReferentToken rt = t0.kit.processReferent("PHONE", t0.getNext(), null);
                if (rt == null) 
                    return null;
            }
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && !t0.getMorph()._getClass().isAdjective()) 
                return _new2832(t0, t0, PhoneItemType.NUMBER, t0.getSourceText());
            return null;
        }
        if (t0.isChar('.')) 
            return _new2832(t0, t0, PhoneItemType.DELIM, ".");
        if (t0.isHiphen()) 
            return _new2832(t0, t0, PhoneItemType.DELIM, "-");
        if (t0.isChar('+')) {
            if (!(t0.getNext() instanceof com.pullenti.ner.NumberToken) || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0.getNext(), com.pullenti.ner.NumberToken.class)).typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
                return null;
            else {
                String val = t0.getNext().getSourceText();
                int i;
                for (i = 0; i < val.length(); i++) {
                    if (val.charAt(i) != '0') 
                        break;
                }
                if (i >= val.length()) 
                    return null;
                if (i > 0) 
                    val = val.substring(i);
                return _new2832(t0, t0.getNext(), PhoneItemType.COUNTRYCODE, val);
            }
        }
        if (t0.isChar((char)0x2011) && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && t0.getNext().getLengthChar() == 2) 
            return _new2832(t0, t0, PhoneItemType.DELIM, "-");
        if (t0.isCharOf("(")) {
            if (t0.getNext() instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.Token et = t0.getNext();
                StringBuilder val = new StringBuilder();
                for (; et != null; et = et.getNext()) {
                    if (et.isChar(')')) 
                        break;
                    if ((et instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                        val.append(et.getSourceText());
                    else if (!et.isHiphen() && !et.isChar('.')) 
                        return null;
                }
                if (et == null || val.length() == 0) 
                    return null;
                else 
                    return _new2837(t0, et, PhoneItemType.CITYCODE, val.toString(), true);
            }
            else {
                com.pullenti.ner.core.TerminToken tt1 = m_PhoneTermins.tryParse(t0.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tt1 == null || tt1.termin.tag != null) {
                }
                else if (tt1.getEndToken().getNext() == null || !tt1.getEndToken().getNext().isChar(')')) {
                }
                else 
                    return _new2838(t0, tt1.getEndToken().getNext(), PhoneItemType.PREFIX, true, "");
                return null;
            }
        }
        if ((t0.isChar('/') && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && t0.getNext().getNext() != null) && t0.getNext().getNext().isChar('/') && t0.getNext().getLengthChar() == 3) 
            return _new2837(t0, t0.getNext().getNext(), PhoneItemType.CITYCODE, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString(), true);
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.phone.PhoneKind ki = com.pullenti.ner.phone.PhoneKind.UNDEFINED;
        if ((t0.isValue("Т", null) && t0.getNext() != null && t0.getNext().isCharOf("\\/")) && t0.getNext().getNext() != null && ((t0.getNext().getNext().isValue("Р", null) || t0.getNext().getNext().isValue("М", null)))) {
            t1 = t0.getNext().getNext();
            ki = (t1.isValue("Р", null) ? com.pullenti.ner.phone.PhoneKind.WORK : com.pullenti.ner.phone.PhoneKind.MOBILE);
        }
        else {
            com.pullenti.ner.core.TerminToken tt = m_PhoneTermins.tryParse(t0, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tt == null || tt.termin.tag != null) {
                if (t0.isValue("НОМЕР", null)) {
                    PhoneItemToken rr = _TryAttach(t0.getNext());
                    if (rr != null && rr.itemType == PhoneItemType.PREFIX) {
                        rr.setBeginToken(t0);
                        return rr;
                    }
                }
                return null;
            }
            if (tt.termin.tag2 instanceof com.pullenti.ner.phone.PhoneKind) 
                ki = (com.pullenti.ner.phone.PhoneKind)tt.termin.tag2;
            t1 = tt.getEndToken();
        }
        PhoneItemToken res = _new2840(t0, t1, PhoneItemType.PREFIX, "", ki);
        while (true) {
            if (t1.getNext() != null && t1.getNext().isCharOf(".:")) 
                res.setEndToken((t1 = t1.getNext()));
            else if (t1.getNext() != null && t1.getNext().isTableControlChar()) 
                t1 = t1.getNext();
            else if ((t1.getNext() != null && t1.getNext().isCharOf("/\\") && t1.getNext().getNext() != null) && t1.getNext().getNext().isValue("ФАКС", null)) {
                res.kind2 = com.pullenti.ner.phone.PhoneKind.FAX;
                res.setEndToken((t1 = t1.getNext().getNext()));
                break;
            }
            else 
                break;
        }
        if (t0 == t1 && ((t0.getBeginChar() == t0.getEndChar() || t0.chars.isAllUpper()))) {
            if (!t0.isWhitespaceAfter()) 
                return null;
        }
        return res;
    }

    public static PhoneItemToken tryAttachAdditional(com.pullenti.ner.Token t0) {
        com.pullenti.ner.Token t = t0;
        if (t == null) 
            return null;
        if (t.isChar(',')) 
            t = t.getNext();
        else if (t.isCharOf("*#") && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
            String val0 = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getSourceText();
            com.pullenti.ner.Token t1 = t.getNext();
            if ((t1.getNext() != null && t1.getNext().isHiphen() && !t1.isWhitespaceAfter()) && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && !t1.getNext().isWhitespaceAfter()) {
                t1 = t1.getNext().getNext();
                val0 += t1.getSourceText();
            }
            if (val0.length() >= 3 && (val0.length() < 7)) 
                return _new2832(t, t1, PhoneItemType.ADDNUMBER, val0);
        }
        boolean br = false;
        if (t != null && t.isChar('(')) {
            if (t.getPrevious() != null && t.getPrevious().isComma()) 
                return null;
            br = true;
            t = t.getNext();
        }
        com.pullenti.ner.core.TerminToken to = m_PhoneTermins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (to == null) {
            if (!br) 
                return null;
            if (t0.getWhitespacesBeforeCount() > 1) 
                return null;
        }
        else if (to.termin.tag == null) 
            return null;
        else 
            t = to.getEndToken().getNext();
        if (t == null) 
            return null;
        if (((t.isValue("НОМЕР", null) || t.isValue("N", null) || t.isValue("#", null)) || t.isValue("№", null) || t.isValue("NUMBER", null)) || ((t.isChar('+') && br))) 
            t = t.getNext();
        else if (to == null && !br) 
            return null;
        else if (t.isValue("НОМ", null) || t.isValue("ТЕЛ", null)) {
            t = t.getNext();
            if (t != null && t.isChar('.')) 
                t = t.getNext();
        }
        if (t != null && t.isCharOf(":,") && !t.isNewlineAfter()) 
            t = t.getNext();
        if (!(t instanceof com.pullenti.ner.NumberToken)) 
            return null;
        String val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getSourceText();
        if ((t.getNext() != null && t.getNext().isHiphen() && !t.isWhitespaceAfter()) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
            val += t.getNext().getNext().getSourceText();
            t = t.getNext().getNext();
        }
        if ((val.length() < 2) || val.length() > 7) 
            return null;
        if (br) {
            if (t.getNext() == null || !t.getNext().isChar(')')) 
                return null;
            t = t.getNext();
        }
        PhoneItemToken res = _new2832(t0, t, PhoneItemType.ADDNUMBER, val);
        return res;
    }

    public static java.util.ArrayList<PhoneItemToken> tryAttachAll(com.pullenti.ner.Token t0, int maxCount) {
        if (t0 == null) 
            return null;
        PhoneItemToken p = tryAttach(t0);
        boolean br = false;
        if (p == null && t0.isChar('(')) {
            br = true;
            p = tryAttach(t0.getNext());
            if (p != null) {
                p.setBeginToken(t0);
                p.isInBrackets = true;
                if (p.itemType == PhoneItemType.PREFIX) 
                    br = false;
            }
        }
        if (p == null || p.itemType == PhoneItemType.DELIM) 
            return null;
        java.util.ArrayList<PhoneItemToken> res = new java.util.ArrayList<PhoneItemToken>();
        res.add(p);
        com.pullenti.ner.Token t;
        for (t = p.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) {
                if (res.size() == 1 && res.get(0).itemType == PhoneItemType.PREFIX) 
                    continue;
                else 
                    break;
            }
            if (br && t.isChar(')')) {
                br = false;
                continue;
            }
            PhoneItemToken p0 = tryAttach(t);
            if (p0 == null) {
                if (t.isNewlineBefore()) 
                    break;
                if (p.itemType == PhoneItemType.PREFIX && ((t.isCharOf("\\/") || t.isHiphen()))) {
                    p0 = tryAttach(t.getNext());
                    if (p0 != null && p0.itemType == PhoneItemType.PREFIX) {
                        p.setEndToken(p0.getEndToken());
                        t = p.getEndToken();
                        continue;
                    }
                }
                if ((res.get(0).itemType == PhoneItemType.PREFIX && t.isCharOf("\\/") && !t.isWhitespaceAfter()) && !t.isWhitespaceBefore() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                    int sumNum = 0;
                    for (PhoneItemToken pp : res) {
                        if (pp.itemType == PhoneItemType.CITYCODE || pp.itemType == PhoneItemType.COUNTRYCODE || pp.itemType == PhoneItemType.NUMBER) 
                            sumNum += pp.value.length();
                    }
                    if (sumNum < 7) {
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                            if (tt.isWhitespaceBefore()) 
                                break;
                            else if (tt instanceof com.pullenti.ner.NumberToken) 
                                sumNum += tt.getLengthChar();
                            else if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter()) {
                            }
                            else 
                                break;
                        }
                        if (sumNum == 10 || sumNum == 11) 
                            continue;
                    }
                }
                if (p0 == null) 
                    break;
            }
            if (t.isNewlineBefore()) {
                if (p.itemType == PhoneItemType.PREFIX) {
                }
                else 
                    break;
            }
            if (t.getWhitespacesBeforeCount() > 1) {
                boolean ok = false;
                for (PhoneItemToken pp : res) {
                    if (pp.itemType == PhoneItemType.PREFIX || pp.itemType == PhoneItemType.COUNTRYCODE) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) 
                    break;
            }
            if (br && p.itemType == PhoneItemType.NUMBER) 
                p.itemType = PhoneItemType.CITYCODE;
            p = p0;
            if (p.itemType == PhoneItemType.NUMBER && res.get(res.size() - 1).itemType == PhoneItemType.NUMBER) 
                res.add(_new2832(t, t, PhoneItemType.DELIM, " "));
            if (br) 
                p.isInBrackets = true;
            res.add(p);
            t = p.getEndToken();
            if (res.size() > maxCount) 
                break;
        }
        if ((((p = tryAttachAdditional(t)))) != null) 
            res.add(p);
        for (int i = 1; i < (res.size() - 1); i++) {
            if (res.get(i).itemType == PhoneItemType.DELIM && res.get(i + 1).isInBrackets) {
                res.remove(i);
                break;
            }
            else if (res.get(i).itemType == PhoneItemType.DELIM && res.get(i + 1).itemType == PhoneItemType.DELIM) {
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.remove(i + 1);
                i--;
            }
        }
        if ((res.size() > 1 && res.get(0).isInBrackets && res.get(0).itemType == PhoneItemType.PREFIX) && res.get(res.size() - 1).getEndToken().getNext() != null && res.get(res.size() - 1).getEndToken().getNext().isChar(')')) 
            res.get(res.size() - 1).setEndToken(res.get(res.size() - 1).getEndToken().getNext());
        if (res.get(0).itemType == PhoneItemType.PREFIX) {
            for (int i = 2; i < (res.size() - 1); i++) {
                if (res.get(i).itemType == PhoneItemType.PREFIX && res.get(i + 1).itemType != PhoneItemType.PREFIX) {
                    for(int jjj = i + res.size() - i - 1, mmm = i; jjj >= mmm; jjj--) res.remove(jjj);
                    break;
                }
            }
        }
        while (res.size() > 0) {
            if (res.get(res.size() - 1).itemType == PhoneItemType.DELIM) 
                res.remove(res.size() - 1);
            else 
                break;
        }
        return res;
    }

    public static PhoneItemToken tryAttachAlternate(com.pullenti.ner.Token t0, com.pullenti.ner.phone.PhoneReferent ph0, java.util.ArrayList<PhoneItemToken> pli) {
        if (t0 == null) 
            return null;
        if (t0.isCharOf("\\/") && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && (t0.getNext().getEndChar() - t0.getNext().getBeginChar()) <= 1) {
            java.util.ArrayList<PhoneItemToken> pli1 = PhoneItemToken.tryAttachAll(t0.getNext(), 15);
            if (pli1 != null && pli1.size() > 1) {
                if (pli1.get(pli1.size() - 1).itemType == PhoneItemType.DELIM) 
                    pli1.remove(pli1.size() - 1);
                if (pli1.size() <= pli.size()) {
                    int ii;
                    String num = "";
                    for (ii = 0; ii < pli1.size(); ii++) {
                        PhoneItemToken p1 = pli1.get(ii);
                        PhoneItemToken p0 = pli.get((pli.size() - pli1.size()) + ii);
                        if (p1.itemType != p0.itemType) 
                            break;
                        if (p1.itemType != PhoneItemType.NUMBER && p1.itemType != PhoneItemType.DELIM) 
                            break;
                        if (p1.itemType == PhoneItemType.NUMBER) {
                            if (p1.getLengthChar() != p0.getLengthChar()) 
                                break;
                            num += p1.value;
                        }
                    }
                    if (ii >= pli1.size()) 
                        return _new2832(t0, pli1.get(pli1.size() - 1).getEndToken(), PhoneItemType.ALT, num);
                }
            }
            return _new2832(t0, t0.getNext(), PhoneItemType.ALT, t0.getNext().getSourceText());
        }
        if (t0.isHiphen() && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && (t0.getNext().getEndChar() - t0.getNext().getBeginChar()) <= 1) {
            com.pullenti.ner.Token t1 = t0.getNext().getNext();
            boolean ok = false;
            if (t1 == null) 
                ok = true;
            else if (t1.isNewlineBefore() || t1.isCharOf(",.")) 
                ok = true;
            if (ok) 
                return _new2832(t0, t0.getNext(), PhoneItemType.ALT, t0.getNext().getSourceText());
        }
        if ((t0.isChar('(') && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && (t0.getNext().getEndChar() - t0.getNext().getBeginChar()) == 1) && t0.getNext().getNext() != null && t0.getNext().getNext().isChar(')')) 
            return _new2832(t0, t0.getNext().getNext(), PhoneItemType.ALT, t0.getNext().getSourceText());
        if ((t0.isCharOf("/-") && (t0.getNext() instanceof com.pullenti.ner.NumberToken) && ph0.m_Template != null) && com.pullenti.morph.LanguageHelper.endsWith(ph0.m_Template, ((Integer)((t0.getNext().getEndChar() - t0.getNext().getBeginChar()) + 1)).toString())) 
            return _new2832(t0, t0.getNext(), PhoneItemType.ALT, t0.getNext().getSourceText());
        return null;
    }

    public static void initialize() {
        if (m_PhoneTermins != null) 
            return;
        m_PhoneTermins = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("ТЕЛЕФОН", com.pullenti.morph.MorphLang.RU, true);
        t.addAbridge("ТЕЛ.");
        t.addAbridge("TEL.");
        t.addAbridge("Т-Н");
        t.addAbridge("Т.");
        t.addAbridge("T.");
        t.addAbridge("TEL.EXT.");
        t.addVariant("ТЛФ", false);
        t.addVariant("ТЛФН", false);
        t.addAbridge("Т/Ф");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("МОБИЛЬНЫЙ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.MOBILE);
        t.addAbridge("МОБ.");
        t.addAbridge("Т.М.");
        t.addAbridge("М.Т.");
        t.addAbridge("М.");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("СОТОВЫЙ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.MOBILE);
        t.addAbridge("СОТ.");
        t.addAbridge("CELL.");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("РАБОЧИЙ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.WORK);
        t.addAbridge("РАБ.");
        t.addAbridge("Т.Р.");
        t.addAbridge("Р.Т.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ГОРОДСКОЙ", com.pullenti.morph.MorphLang.RU, true);
        t.addAbridge("ГОР.");
        t.addAbridge("Г.Т.");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("ДОМАШНИЙ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.HOME);
        t.addAbridge("ДОМ.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("КОНТАКТНЫЙ", com.pullenti.morph.MorphLang.RU, true);
        t.addVariant("КОНТАКТНЫЕ ДАННЫЕ", false);
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("МНОГОКАНАЛЬНЫЙ", com.pullenti.morph.MorphLang.RU, true);
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("ФАКС", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.FAX);
        t.addAbridge("Ф.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ЗВОНИТЬ", com.pullenti.morph.MorphLang.RU, true);
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("ПРИЕМНАЯ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.phone.PhoneKind.WORK);
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("PHONE", com.pullenti.morph.MorphLang.EN, true);
        t.addAbridge("PH.");
        t.addVariant("TELEFON", true);
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("DIRECT LINE", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.phone.PhoneKind.WORK);
        t.addVariant("DIRECT LINES", true);
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("MOBILE", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.phone.PhoneKind.MOBILE);
        t.addAbridge("MOB.");
        t.addVariant("MOBIL", true);
        t.addAbridge("M.");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("FAX", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.phone.PhoneKind.FAX);
        t.addAbridge("F.");
        m_PhoneTermins.add(t);
        t = com.pullenti.ner.core.Termin._new2849("HOME", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.phone.PhoneKind.HOME);
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("CALL", com.pullenti.morph.MorphLang.EN, true);
        t.addVariant("SEDIU", true);
        t.addVariant("CALL AT", false);
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ДОПОЛНИТЕЛЬНЫЙ", com.pullenti.morph.MorphLang.RU, true);
        t.tag = t;
        t.addAbridge("ДОП.");
        t.addAbridge("EXT.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ДОБАВОЧНЫЙ", com.pullenti.morph.MorphLang.RU, true);
        t.tag = t;
        t.addAbridge("ДОБ.");
        t.addAbridge("Д.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ВНУТРЕННИЙ", com.pullenti.morph.MorphLang.RU, true);
        t.tag = t;
        t.addAbridge("ВНУТР.");
        t.addAbridge("ВН.");
        t.addAbridge("ВНТ.");
        t.addAbridge("Т.ВН.");
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("TONE MODE", com.pullenti.morph.MorphLang.EN, true);
        t.tag = t;
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("TONE", com.pullenti.morph.MorphLang.EN, true);
        t.tag = t;
        m_PhoneTermins.add(t);
        t = new com.pullenti.ner.core.Termin("ADDITIONAL", com.pullenti.morph.MorphLang.EN, true);
        t.addAbridge("ADD.");
        t.tag = t;
        t.addVariant("INTERNAL", true);
        t.addAbridge("INT.");
        m_PhoneTermins.add(t);
    }

    private static com.pullenti.ner.core.TerminCollection m_PhoneTermins;

    public static PhoneItemToken _new2832(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PhoneItemType _arg3, String _arg4) {
        PhoneItemToken res = new PhoneItemToken(_arg1, _arg2);
        res.itemType = _arg3;
        res.value = _arg4;
        return res;
    }

    public static PhoneItemToken _new2837(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PhoneItemType _arg3, String _arg4, boolean _arg5) {
        PhoneItemToken res = new PhoneItemToken(_arg1, _arg2);
        res.itemType = _arg3;
        res.value = _arg4;
        res.isInBrackets = _arg5;
        return res;
    }

    public static PhoneItemToken _new2838(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PhoneItemType _arg3, boolean _arg4, String _arg5) {
        PhoneItemToken res = new PhoneItemToken(_arg1, _arg2);
        res.itemType = _arg3;
        res.isInBrackets = _arg4;
        res.value = _arg5;
        return res;
    }

    public static PhoneItemToken _new2840(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PhoneItemType _arg3, String _arg4, com.pullenti.ner.phone.PhoneKind _arg5) {
        PhoneItemToken res = new PhoneItemToken(_arg1, _arg2);
        res.itemType = _arg3;
        res.value = _arg4;
        res.kind = _arg5;
        return res;
    }

    public static class PhoneItemType implements Comparable<PhoneItemType> {
    
        public static final PhoneItemType NUMBER; // 0
    
        public static final PhoneItemType CITYCODE; // 1
    
        public static final PhoneItemType DELIM; // 2
    
        public static final PhoneItemType PREFIX; // 3
    
        public static final PhoneItemType ADDNUMBER; // 4
    
        public static final PhoneItemType COUNTRYCODE; // 5
    
        public static final PhoneItemType ALT; // 6
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private PhoneItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(PhoneItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, PhoneItemType> mapIntToEnum; 
        private static java.util.HashMap<String, PhoneItemType> mapStringToEnum; 
        private static PhoneItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static PhoneItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            PhoneItemType item = new PhoneItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static PhoneItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static PhoneItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, PhoneItemType>();
            mapStringToEnum = new java.util.HashMap<String, PhoneItemType>();
            NUMBER = new PhoneItemType(0, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            CITYCODE = new PhoneItemType(1, "CITYCODE");
            mapIntToEnum.put(CITYCODE.value(), CITYCODE);
            mapStringToEnum.put(CITYCODE.m_str.toUpperCase(), CITYCODE);
            DELIM = new PhoneItemType(2, "DELIM");
            mapIntToEnum.put(DELIM.value(), DELIM);
            mapStringToEnum.put(DELIM.m_str.toUpperCase(), DELIM);
            PREFIX = new PhoneItemType(3, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            ADDNUMBER = new PhoneItemType(4, "ADDNUMBER");
            mapIntToEnum.put(ADDNUMBER.value(), ADDNUMBER);
            mapStringToEnum.put(ADDNUMBER.m_str.toUpperCase(), ADDNUMBER);
            COUNTRYCODE = new PhoneItemType(5, "COUNTRYCODE");
            mapIntToEnum.put(COUNTRYCODE.value(), COUNTRYCODE);
            mapStringToEnum.put(COUNTRYCODE.m_str.toUpperCase(), COUNTRYCODE);
            ALT = new PhoneItemType(6, "ALT");
            mapIntToEnum.put(ALT.value(), ALT);
            mapStringToEnum.put(ALT.m_str.toUpperCase(), ALT);
            java.util.Collection<PhoneItemType> col = mapIntToEnum.values();
            m_Values = new PhoneItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public PhoneItemToken() {
        super();
    }
    public static PhoneItemToken _globalInstance;
    
    static {
        try { _globalInstance = new PhoneItemToken(); } 
        catch(Exception e) { }
    }
}
