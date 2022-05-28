/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class NumberExHelper {

    public static com.pullenti.ner.core.NumberExToken tryParseNumberWithPostfix(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        String isDollar = null;
        if (t.getLengthChar() == 1 && t.getNext() != null) {
            if ((((isDollar = com.pullenti.ner.core.NumberHelper.isMoneyChar(t)))) != null) 
                t = t.getNext();
        }
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        if (nt == null) {
            if ((!(t.getPrevious() instanceof com.pullenti.ner.NumberToken) && t.isChar('(') && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                com.pullenti.ner.core.TerminToken toks1 = m_Postfixes.tryParse(t.getNext().getNext().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (toks1 != null && ((com.pullenti.ner.core.NumberExType)toks1.termin.tag) == com.pullenti.ner.core.NumberExType.MONEY) {
                    com.pullenti.ner.NumberToken nt0 = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
                    com.pullenti.ner.core.NumberExToken res = com.pullenti.ner.core.NumberExToken._new420(t, toks1.getEndToken(), nt0.getValue(), nt0.typ, com.pullenti.ner.core.NumberExType.MONEY, nt0.getRealValue(), toks1.getBeginToken().getMorph());
                    return _correctMoney(res, toks1.getBeginToken());
                }
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null || !tt.getMorph()._getClass().isAdjective()) 
                return null;
            String val = tt.term;
            for (int i = 4; i < (val.length() - 5); i++) {
                String v = val.substring(0, 0 + i);
                java.util.ArrayList<com.pullenti.ner.core.Termin> li = com.pullenti.ner.core.NumberHelper.m_Nums.findTerminsByString(v, tt.getMorph().getLanguage());
                if (li == null) 
                    continue;
                String vv = val.substring(i);
                java.util.ArrayList<com.pullenti.ner.core.Termin> lii = m_Postfixes.findTerminsByString(vv, tt.getMorph().getLanguage());
                if (lii != null && lii.size() > 0) {
                    com.pullenti.ner.core.NumberExToken re = com.pullenti.ner.core.NumberExToken._new421(t, t, li.get(0).tag.toString(), com.pullenti.ner.NumberSpellingType.WORDS, (com.pullenti.ner.core.NumberExType)lii.get(0).tag, t.getMorph());
                    _correctExtTypes(re);
                    return re;
                }
                break;
            }
            return null;
        }
        if (t.getNext() == null && isDollar == null) 
            return null;
        double f = nt.getRealValue();
        if (Double.isNaN(f)) 
            return null;
        com.pullenti.ner.Token t1 = nt.getNext();
        if (((t1 != null && t1.isCharOf(",."))) || (((t1 instanceof com.pullenti.ner.NumberToken) && (t1.getWhitespacesBeforeCount() < 3)))) {
            double d;
            com.pullenti.ner.NumberToken tt11 = com.pullenti.ner.core.NumberHelper.tryParseRealNumber(nt, false, false);
            if (tt11 != null) {
                t1 = tt11.getEndToken().getNext();
                f = tt11.getRealValue();
            }
        }
        if (t1 == null) {
            if (isDollar == null) 
                return null;
        }
        else if (t1.getNext() != null && t1.isValue("С", "З") && t1.getNext().isValue("ПОЛОВИНА", null)) {
            f += 0.5;
            t1 = t1.getNext().getNext();
        }
        else if ((t1.getNext() != null && t1.getNext().isValue("С", "З") && t1.getNext().getNext() != null) && t1.getNext().getNext().isValue("ПОЛОВИНА", null)) {
            f += 0.5;
            t1 = t1.getNext().getNext().getNext();
        }
        if (t1 != null && t1.isHiphen() && t1.getNext() != null) 
            t1 = t1.getNext();
        boolean det = false;
        double altf = f;
        if (((t1 instanceof com.pullenti.ner.NumberToken) && t1.getPrevious() != null && t1.getPrevious().isHiphen()) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getIntValue() == 0 && t1.getLengthChar() == 2) 
            t1 = t1.getNext();
        if ((t1 != null && t1.getNext() != null && t1.isChar('(')) && (((t1.getNext() instanceof com.pullenti.ner.NumberToken) || t1.getNext().isValue("НОЛЬ", null))) && t1.getNext().getNext() != null) {
            com.pullenti.ner.NumberToken nt1 = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class);
            double val = 0.0;
            if (nt1 != null) 
                val = nt1.getRealValue();
            if (Math.floor(f) == Math.floor(val)) {
                com.pullenti.ner.Token ttt = t1.getNext().getNext();
                if (ttt.isChar(')')) {
                    t1 = ttt.getNext();
                    det = true;
                    if ((t1 instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getIntValue() == 0) 
                        t1 = t1.getNext();
                }
                else if (((((ttt instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getRealValue() < 100) && ttt.getNext() != null) && ttt.getNext().isChar('/') && ttt.getNext().getNext() != null) && com.pullenti.unisharp.Utils.stringsEq(ttt.getNext().getNext().getSourceText(), "100") && ttt.getNext().getNext().getNext() != null) && ttt.getNext().getNext().getNext().isChar(')')) {
                    int rest = getDecimalRest100(f);
                    if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getIntValue() != null && rest == ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getIntValue()) {
                        t1 = ttt.getNext().getNext().getNext().getNext();
                        det = true;
                    }
                }
                else if ((ttt.isValue("ЦЕЛЫХ", null) && (ttt.getNext() instanceof com.pullenti.ner.NumberToken) && ttt.getNext().getNext() != null) && ttt.getNext().getNext().getNext() != null && ttt.getNext().getNext().getNext().isChar(')')) {
                    com.pullenti.ner.NumberToken num2 = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class);
                    altf = num2.getRealValue();
                    if (ttt.getNext().getNext().isValue("ДЕСЯТЫЙ", null)) 
                        altf /= (10.0);
                    else if (ttt.getNext().getNext().isValue("СОТЫЙ", null)) 
                        altf /= (100.0);
                    else if (ttt.getNext().getNext().isValue("ТЫСЯЧНЫЙ", null)) 
                        altf /= (1000.0);
                    else if (ttt.getNext().getNext().isValue("ДЕСЯТИТЫСЯЧНЫЙ", null)) 
                        altf /= (10000.0);
                    else if (ttt.getNext().getNext().isValue("СТОТЫСЯЧНЫЙ", null)) 
                        altf /= (100000.0);
                    else if (ttt.getNext().getNext().isValue("МИЛЛИОННЫЙ", null)) 
                        altf /= (1000000.0);
                    if (altf < 1) {
                        altf += val;
                        t1 = ttt.getNext().getNext().getNext().getNext();
                        det = true;
                    }
                }
                else {
                    com.pullenti.ner.core.TerminToken toks1 = m_Postfixes.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (toks1 != null) {
                        if (((com.pullenti.ner.core.NumberExType)toks1.termin.tag) == com.pullenti.ner.core.NumberExType.MONEY) {
                            if (toks1.getEndToken().getNext() != null && toks1.getEndToken().getNext().isChar(')')) {
                                com.pullenti.ner.core.NumberExToken res = com.pullenti.ner.core.NumberExToken._new422(t, toks1.getEndToken().getNext(), nt.getValue(), nt.typ, com.pullenti.ner.core.NumberExType.MONEY, f, altf, toks1.getBeginToken().getMorph());
                                return _correctMoney(res, toks1.getBeginToken());
                            }
                        }
                    }
                    com.pullenti.ner.core.NumberExToken res2 = tryParseNumberWithPostfix(t1.getNext());
                    if (res2 != null && res2.getEndToken().getNext() != null && res2.getEndToken().getNext().isChar(')')) {
                        res2.setBeginToken(t);
                        res2.setEndToken(res2.getEndToken().getNext());
                        res2.altRealValue = res2.getRealValue();
                        res2.setRealValue(f);
                        _correctExtTypes(res2);
                        if (res2.getWhitespacesAfterCount() < 2) {
                            com.pullenti.ner.core.TerminToken toks2 = m_Postfixes.tryParse(res2.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                            if (toks2 != null) {
                                if (((com.pullenti.ner.core.NumberExType)toks2.termin.tag) == com.pullenti.ner.core.NumberExType.MONEY) 
                                    res2.setEndToken(toks2.getEndToken());
                            }
                        }
                        return res2;
                    }
                }
            }
            else if (nt1 != null && nt1.typ == com.pullenti.ner.NumberSpellingType.WORDS && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                altf = nt1.getRealValue();
                com.pullenti.ner.Token ttt = t1.getNext().getNext();
                if (ttt.isChar(')')) {
                    t1 = ttt.getNext();
                    det = true;
                }
                if (!det) 
                    altf = f;
            }
        }
        if ((t1 != null && t1.isChar('(') && t1.getNext() != null) && t1.getNext().isValue("СУММА", null)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) 
                t1 = br.getEndToken().getNext();
        }
        if (isDollar != null) {
            com.pullenti.ner.Token te = null;
            if (t1 != null) 
                te = t1.getPrevious();
            else 
                for (t1 = t0; t1 != null; t1 = t1.getNext()) {
                    if (t1.getNext() == null) 
                        te = t1;
                }
            if (te == null) 
                return null;
            if (te.isHiphen() && te.getNext() != null) {
                if (te.getNext().isValue("МИЛЛИОННЫЙ", null)) {
                    f *= (1000000.0);
                    altf *= (1000000.0);
                    te = te.getNext();
                }
                else if (te.getNext().isValue("МИЛЛИАРДНЫЙ", null)) {
                    f *= (1000000000.0);
                    altf *= (1000000000.0);
                    te = te.getNext();
                }
            }
            if (!te.isWhitespaceAfter() && (te.getNext() instanceof com.pullenti.ner.TextToken)) {
                if (te.getNext().isValue("M", null)) {
                    f *= (1000000.0);
                    altf *= (1000000.0);
                    te = te.getNext();
                }
                else if (te.getNext().isValue("BN", null)) {
                    f *= (1000000000.0);
                    altf *= (1000000000.0);
                    te = te.getNext();
                }
            }
            return com.pullenti.ner.core.NumberExToken._new423(t0, te, "", nt.typ, com.pullenti.ner.core.NumberExType.MONEY, f, altf, isDollar);
        }
        if (t1 == null || ((t1.isNewlineBefore() && !det))) 
            return null;
        com.pullenti.ner.core.TerminToken toks = m_Postfixes.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
        if ((toks == null && det && (t1 instanceof com.pullenti.ner.NumberToken)) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue(), "0")) 
            toks = m_Postfixes.tryParse(t1.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (toks == null && t1.isChar('р')) {
            int cou = 10;
            for (com.pullenti.ner.Token ttt = t0.getPrevious(); ttt != null && cou > 0; ttt = ttt.getPrevious(),cou--) {
                if (ttt.isValue("СУММА", null) || ttt.isValue("НАЛИЧНЫЙ", null) || ttt.isValue("БАЛАНС", null)) {
                }
                else if (ttt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(ttt.getReferent().getTypeName(), "MONEY")) {
                }
                else 
                    continue;
                toks = com.pullenti.ner.core.TerminToken._new424(t1, t1, m_Postfixes.findTerminsByCanonicText("RUB").get(0));
                if (t1.getNext() != null && t1.getNext().isChar('.')) 
                    toks.setEndToken(t1.getNext());
                com.pullenti.ner.core.NumberExType ty = (com.pullenti.ner.core.NumberExType)toks.termin.tag;
                return com.pullenti.ner.core.NumberExToken._new425(t, toks.getEndToken(), nt.getValue(), nt.typ, ty, f, altf, toks.getBeginToken().getMorph(), "RUB");
            }
        }
        if (toks != null) {
            t1 = toks.getEndToken();
            if (!t1.isChar('.') && t1.getNext() != null && t1.getNext().isChar('.')) {
                if ((t1 instanceof com.pullenti.ner.TextToken) && t1.isValue(toks.termin.terms.get(0).getCanonicalText(), null)) {
                }
                else if (!t1.chars.isLetter()) {
                }
                else 
                    t1 = t1.getNext();
            }
            if (com.pullenti.unisharp.Utils.stringsEq(toks.termin.getCanonicText(), "LTL")) 
                return null;
            if (toks.getBeginToken() == t1) {
                if (t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()) {
                    if (t1.isWhitespaceBefore() && t1.isWhitespaceAfter()) 
                        return null;
                }
            }
            com.pullenti.ner.core.NumberExType ty = (com.pullenti.ner.core.NumberExType)toks.termin.tag;
            com.pullenti.ner.core.NumberExToken res = com.pullenti.ner.core.NumberExToken._new422(t, t1, nt.getValue(), nt.typ, ty, f, altf, toks.getBeginToken().getMorph());
            if (ty != com.pullenti.ner.core.NumberExType.MONEY) {
                _correctExtTypes(res);
                return res;
            }
            return _correctMoney(res, toks.getBeginToken());
        }
        com.pullenti.ner.core.NumberExToken pfx = _attachSpecPostfix(t1);
        if (pfx != null) {
            pfx.setBeginToken(t);
            pfx.setValue(nt.getValue());
            pfx.typ = nt.typ;
            pfx.setRealValue(f);
            pfx.altRealValue = altf;
            return pfx;
        }
        if (t1.getNext() != null && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()))) {
            if (t1.isValue("НА", null)) {
            }
            else {
                com.pullenti.ner.core.NumberExToken nn = tryParseNumberWithPostfix(t1.getNext());
                if (nn != null) 
                    return com.pullenti.ner.core.NumberExToken._new427(t, t, nt.getValue(), nt.typ, nn.exTyp, f, altf, nn.exTyp2, nn.exTypParam);
            }
        }
        if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken) && (t1 instanceof com.pullenti.ner.TextToken)) {
            String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term;
            com.pullenti.ner.core.NumberExType ty = com.pullenti.ner.core.NumberExType.UNDEFINED;
            if (com.pullenti.unisharp.Utils.stringsEq(term, "СМХ") || com.pullenti.unisharp.Utils.stringsEq(term, "CMX")) 
                ty = com.pullenti.ner.core.NumberExType.SANTIMETER;
            else if (com.pullenti.unisharp.Utils.stringsEq(term, "MX") || com.pullenti.unisharp.Utils.stringsEq(term, "МХ")) 
                ty = com.pullenti.ner.core.NumberExType.METER;
            else if (com.pullenti.unisharp.Utils.stringsEq(term, "MMX") || com.pullenti.unisharp.Utils.stringsEq(term, "ММХ")) 
                ty = com.pullenti.ner.core.NumberExType.MILLIMETER;
            if (ty != com.pullenti.ner.core.NumberExType.UNDEFINED) 
                return com.pullenti.ner.core.NumberExToken._new428(t, t1, nt.getValue(), nt.typ, ty, f, altf, true);
        }
        return null;
    }

    private static int getDecimalRest100(double f) {
        int rest = ((int)(((((f - com.pullenti.unisharp.Utils.mathTruncate(f)) + 0.0001)) * (10000.0)))) / 100;
        return rest;
    }

    public static com.pullenti.ner.core.NumberExToken tryAttachPostfixOnly(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Postfixes.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        com.pullenti.ner.core.NumberExToken res = null;
        if (tok != null) 
            res = com.pullenti.ner.core.NumberExToken._new429(t, tok.getEndToken(), "", com.pullenti.ner.NumberSpellingType.DIGIT, (com.pullenti.ner.core.NumberExType)tok.termin.tag, tok.termin);
        else 
            res = _attachSpecPostfix(t);
        if (res != null) {
            if (res.exTyp == com.pullenti.ner.core.NumberExType.MONEY) 
                _correctMoney(res, res.getBeginToken());
            else 
                _correctExtTypes(res);
        }
        return res;
    }

    private static com.pullenti.ner.core.NumberExToken _attachSpecPostfix(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isCharOf("%")) 
            return new com.pullenti.ner.core.NumberExToken(t, t, "", com.pullenti.ner.NumberSpellingType.DIGIT, com.pullenti.ner.core.NumberExType.PERCENT);
        String money = com.pullenti.ner.core.NumberHelper.isMoneyChar(t);
        if (money != null) 
            return com.pullenti.ner.core.NumberExToken._new430(t, t, "", com.pullenti.ner.NumberSpellingType.DIGIT, com.pullenti.ner.core.NumberExType.MONEY, money);
        return null;
    }

    private static void _correctExtTypes(com.pullenti.ner.core.NumberExToken ex) {
        com.pullenti.ner.Token t = ex.getEndToken().getNext();
        if (t == null) 
            return;
        com.pullenti.ner.core.NumberExType ty = ex.exTyp;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wrapty432 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(ty);
        com.pullenti.ner.Token tt = _corrExTyp2(t, wrapty432);
        ty = wrapty432.value;
        if (tt != null) {
            ex.exTyp = ty;
            ex.setEndToken(tt);
            t = tt.getNext();
        }
        if (t == null || t.getNext() == null) 
            return;
        if (t.isCharOf("/\\") || t.isValue("НА", null)) {
        }
        else 
            return;
        com.pullenti.ner.core.TerminToken tok = m_Postfixes.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && (((com.pullenti.ner.core.NumberExType)tok.termin.tag) != com.pullenti.ner.core.NumberExType.MONEY)) {
            ex.exTyp2 = (com.pullenti.ner.core.NumberExType)tok.termin.tag;
            ex.setEndToken(tok.getEndToken());
            ty = ex.exTyp2;
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wrapty431 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(ty);
            tt = _corrExTyp2(ex.getEndToken().getNext(), wrapty431);
            ty = wrapty431.value;
            if (tt != null) {
                ex.exTyp2 = ty;
                ex.setEndToken(tt);
                t = tt.getNext();
            }
        }
    }

    private static com.pullenti.ner.Token _corrExTyp2(com.pullenti.ner.Token t, com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> typ) {
        if (t == null) 
            return null;
        int num = 0;
        com.pullenti.ner.Token tt = t;
        if (t.isChar('³')) 
            num = 3;
        else if (t.isChar('²')) 
            num = 2;
        else if (!t.isWhitespaceBefore() && (t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "3") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "2")))) 
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
        else if ((t.isChar('<') && (t.getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext() != null) && t.getNext().getNext().isChar('>') && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
            tt = t.getNext().getNext();
        }
        if (num == 3) {
            if (typ.value == com.pullenti.ner.core.NumberExType.METER) {
                typ.value = com.pullenti.ner.core.NumberExType.METER3;
                return tt;
            }
            if (typ.value == com.pullenti.ner.core.NumberExType.SANTIMETER) {
                typ.value = com.pullenti.ner.core.NumberExType.SANTIMETER3;
                return tt;
            }
        }
        if (num == 2) {
            if (typ.value == com.pullenti.ner.core.NumberExType.METER) {
                typ.value = com.pullenti.ner.core.NumberExType.METER2;
                return tt;
            }
            if (typ.value == com.pullenti.ner.core.NumberExType.SANTIMETER) {
                typ.value = com.pullenti.ner.core.NumberExType.SANTIMETER2;
                return tt;
            }
        }
        return null;
    }

    private static com.pullenti.ner.core.NumberExToken _correctMoney(com.pullenti.ner.core.NumberExToken res, com.pullenti.ner.Token t1) {
        if (t1 == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks = m_Postfixes.tryParseAll(t1, com.pullenti.ner.core.TerminParseAttr.NO);
        if (toks == null || toks.size() == 0) 
            return null;
        com.pullenti.ner.Token tt = toks.get(0).getEndToken().getNext();
        com.pullenti.ner.Referent r = (tt == null ? null : tt.getReferent());
        String alpha2 = null;
        if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO")) 
            alpha2 = r.getStringValue("ALPHA2");
        if (alpha2 != null && toks.size() > 0) {
            for (int i = toks.size() - 1; i >= 0; i--) {
                if (!toks.get(i).termin.getCanonicText().startsWith(alpha2)) 
                    toks.remove(i);
            }
            if (toks.size() == 0) 
                toks = m_Postfixes.tryParseAll(t1, com.pullenti.ner.core.TerminParseAttr.NO);
        }
        if (toks.size() > 1) {
            alpha2 = null;
            String str = toks.get(0).termin.terms.get(0).getCanonicalText();
            if (com.pullenti.unisharp.Utils.stringsEq(str, "РУБЛЬ") || com.pullenti.unisharp.Utils.stringsEq(str, "RUBLE")) 
                alpha2 = "RU";
            else if (com.pullenti.unisharp.Utils.stringsEq(str, "ДОЛЛАР") || com.pullenti.unisharp.Utils.stringsEq(str, "ДОЛАР") || com.pullenti.unisharp.Utils.stringsEq(str, "DOLLAR")) 
                alpha2 = "US";
            else if (com.pullenti.unisharp.Utils.stringsEq(str, "ФУНТ") || com.pullenti.unisharp.Utils.stringsEq(str, "POUND")) 
                alpha2 = "UK";
            if (alpha2 != null) {
                for (int i = toks.size() - 1; i >= 0; i--) {
                    if (!toks.get(i).termin.getCanonicText().startsWith(alpha2) && com.pullenti.unisharp.Utils.stringsNe(toks.get(i).termin.getCanonicText(), "GBP")) 
                        toks.remove(i);
                }
            }
            alpha2 = null;
        }
        if (toks.size() < 1) 
            return null;
        res.exTypParam = toks.get(0).termin.getCanonicText();
        if (alpha2 != null && tt != null) 
            res.setEndToken(tt);
        tt = res.getEndToken().getNext();
        if (tt != null && tt.isCommaAnd()) 
            tt = tt.getNext();
        if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null && (tt.getWhitespacesAfterCount() < 4)) {
            com.pullenti.ner.Token tt1 = tt.getNext();
            if ((tt1 != null && tt1.isChar('(') && (tt1.getNext() instanceof com.pullenti.ner.NumberToken)) && tt1.getNext().getNext() != null && tt1.getNext().getNext().isChar(')')) {
                if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1.getNext(), com.pullenti.ner.NumberToken.class)).getValue())) 
                    tt1 = tt1.getNext().getNext().getNext();
            }
            com.pullenti.ner.core.TerminToken tok = m_SmallMoney.tryParse(tt1, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null && tt1 != null && tt1.isChar(')')) 
                tok = m_SmallMoney.tryParse(tt1.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                int max = (int)tok.termin.tag;
                int val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getIntValue();
                if (val < max) {
                    double f = (double)val;
                    f /= ((double)max);
                    double f0 = res.getRealValue() - ((double)((long)res.getRealValue()));
                    int re0 = (int)(((f0 * (100.0)) + 0.0001));
                    if (re0 > 0 && val != re0) 
                        res.altRestMoney = val;
                    else if (f0 == 0) 
                        res.setRealValue(res.getRealValue() + f);
                    f0 = res.altRealValue - ((double)((long)res.altRealValue));
                    re0 = (int)(((f0 * (100.0)) + 0.0001));
                    if (re0 > 0 && val != re0) 
                        res.altRestMoney = val;
                    else if (f0 == 0) 
                        res.altRealValue += f;
                    res.setEndToken(tok.getEndToken());
                }
            }
        }
        else if ((tt instanceof com.pullenti.ner.TextToken) && tt.isValue("НОЛЬ", null)) {
            com.pullenti.ner.core.TerminToken tok = m_SmallMoney.tryParse(tt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) 
                res.setEndToken(tok.getEndToken());
        }
        return res;
    }

    public static void initialize() {
        if (m_Postfixes != null) 
            return;
        com.pullenti.ner.core.Termin t;
        m_Postfixes = new com.pullenti.ner.core.TerminCollection();
        t = com.pullenti.ner.core.Termin._new433("КВАДРАТНЫЙ МЕТР", com.pullenti.morph.MorphLang.RU, true, "кв.м.", com.pullenti.ner.core.NumberExType.METER2);
        t.addAbridge("КВ.МЕТР");
        t.addAbridge("КВ.МЕТРА");
        t.addAbridge("КВ.М.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КВАДРАТНИЙ МЕТР", com.pullenti.morph.MorphLang.UA, true, "КВ.М.", com.pullenti.ner.core.NumberExType.METER2);
        t.addAbridge("КВ.МЕТР");
        t.addAbridge("КВ.МЕТРА");
        t.addAbridge("КВ.М.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КВАДРАТНЫЙ КИЛОМЕТР", com.pullenti.morph.MorphLang.RU, true, "кв.км.", com.pullenti.ner.core.NumberExType.KILOMETER2);
        t.addVariant("КВАДРАТНИЙ КІЛОМЕТР", true);
        t.addAbridge("КВ.КМ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ГЕКТАР", com.pullenti.morph.MorphLang.RU, true, "га", com.pullenti.ner.core.NumberExType.GEKTAR);
        t.addAbridge("ГА");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("АР", com.pullenti.morph.MorphLang.RU, true, "ар", com.pullenti.ner.core.NumberExType.AR);
        t.addVariant("СОТКА", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КУБИЧЕСКИЙ МЕТР", com.pullenti.morph.MorphLang.RU, true, "куб.м.", com.pullenti.ner.core.NumberExType.METER3);
        t.addVariant("КУБІЧНИЙ МЕТР", true);
        t.addAbridge("КУБ.МЕТР");
        t.addAbridge("КУБ.М.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МЕТР", com.pullenti.morph.MorphLang.RU, true, "м.", com.pullenti.ner.core.NumberExType.METER);
        t.addAbridge("М.");
        t.addAbridge("M.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "м.", com.pullenti.ner.core.NumberExType.METER);
        t.addVariant("МЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИМЕТР", com.pullenti.morph.MorphLang.RU, true, "мм.", com.pullenti.ner.core.NumberExType.MILLIMETER);
        t.addAbridge("ММ");
        t.addAbridge("MM");
        t.addVariant("МІЛІМЕТР", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "мм.", com.pullenti.ner.core.NumberExType.MILLIMETER);
        t.addVariant("МІЛІМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "см.", com.pullenti.ner.core.NumberExType.SANTIMETER);
        t.addAbridge("СМ");
        t.addAbridge("CM");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("САНТИМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "см.", com.pullenti.ner.core.NumberExType.SANTIMETER);
        t.addVariant("САНТИМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КВАДРАТНЫЙ САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "кв.см.", com.pullenti.ner.core.NumberExType.SANTIMETER2);
        t.addVariant("КВАДРАТНИЙ САНТИМЕТР", true);
        t.addAbridge("КВ.СМ.");
        t.addAbridge("СМ.КВ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КУБИЧЕСКИЙ САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "куб.см.", com.pullenti.ner.core.NumberExType.SANTIMETER3);
        t.addVariant("КУБІЧНИЙ САНТИМЕТР", true);
        t.addAbridge("КУБ.САНТИМЕТР");
        t.addAbridge("КУБ.СМ.");
        t.addAbridge("СМ.КУБ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КИЛОМЕТР", com.pullenti.morph.MorphLang.RU, true, "км.", com.pullenti.ner.core.NumberExType.KILOMETER);
        t.addAbridge("КМ");
        t.addAbridge("KM");
        t.addVariant("КІЛОМЕТР", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КИЛОМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "км.", com.pullenti.ner.core.NumberExType.KILOMETER);
        t.addVariant("КІЛОМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЯ", com.pullenti.morph.MorphLang.RU, true, "миль", com.pullenti.ner.core.NumberExType.KILOMETER);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ГРАММ", com.pullenti.morph.MorphLang.RU, true, "гр.", com.pullenti.ner.core.NumberExType.GRAMM);
        t.addAbridge("ГР");
        t.addAbridge("Г");
        t.addVariant("ГРАМ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "гр.", com.pullenti.ner.core.NumberExType.GRAMM);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КИЛОГРАММ", com.pullenti.morph.MorphLang.RU, true, "кг.", com.pullenti.ner.core.NumberExType.KILOGRAM);
        t.addAbridge("КГ");
        t.addVariant("КІЛОГРАМ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КИЛОГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "кг.", com.pullenti.ner.core.NumberExType.KILOGRAM);
        t.addVariant("КІЛОГРАМОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИГРАММ", com.pullenti.morph.MorphLang.RU, true, "мг.", com.pullenti.ner.core.NumberExType.MILLIGRAM);
        t.addAbridge("МГ");
        t.addVariant("МІЛІГРАМ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "мг.", com.pullenti.ner.core.NumberExType.MILLIGRAM);
        t.addVariant("МИЛЛИГРАМОВЫЙ", true);
        t.addVariant("МІЛІГРАМОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ТОННА", com.pullenti.morph.MorphLang.RU, true, "т.", com.pullenti.ner.core.NumberExType.TONNA);
        t.addAbridge("Т");
        t.addAbridge("T");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ТОННЫЙ", com.pullenti.morph.MorphLang.RU, true, "т.", com.pullenti.ner.core.NumberExType.TONNA);
        t.addVariant("ТОННИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ЛИТР", com.pullenti.morph.MorphLang.RU, true, "л.", com.pullenti.ner.core.NumberExType.LITR);
        t.addAbridge("Л");
        t.addVariant("ЛІТР", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ЛИТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "л.", com.pullenti.ner.core.NumberExType.LITR);
        t.addVariant("ЛІТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИЛИТР", com.pullenti.morph.MorphLang.RU, true, "мл.", com.pullenti.ner.core.NumberExType.MILLILITR);
        t.addAbridge("МЛ");
        t.addVariant("МІЛІЛІТР", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИЛЛИЛИТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "мл.", com.pullenti.ner.core.NumberExType.MILLILITR);
        t.addVariant("МІЛІЛІТРОВИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ЧАС", com.pullenti.morph.MorphLang.RU, true, "ч.", com.pullenti.ner.core.NumberExType.HOUR);
        t.addAbridge("Ч.");
        t.addVariant("ГОДИНА", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МИНУТА", com.pullenti.morph.MorphLang.RU, true, "мин.", com.pullenti.ner.core.NumberExType.MINUTE);
        t.addAbridge("МИН.");
        t.addVariant("ХВИЛИНА", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("СЕКУНДА", com.pullenti.morph.MorphLang.RU, true, "сек.", com.pullenti.ner.core.NumberExType.SECOND);
        t.addAbridge("СЕК.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ГОД", com.pullenti.morph.MorphLang.RU, true, "г.", com.pullenti.ner.core.NumberExType.YEAR);
        t.addAbridge("Г.");
        t.addAbridge("ЛЕТ");
        t.addVariant("ЛЕТНИЙ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("МЕСЯЦ", com.pullenti.morph.MorphLang.RU, true, "мес.", com.pullenti.ner.core.NumberExType.MONTH);
        t.addAbridge("МЕС.");
        t.addVariant("МЕСЯЧНЫЙ", true);
        t.addVariant("КАЛЕНДАРНЫЙ МЕСЯЦ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ДЕНЬ", com.pullenti.morph.MorphLang.RU, true, "дн.", com.pullenti.ner.core.NumberExType.DAY);
        t.addAbridge("ДН.");
        t.addVariant("ДНЕВНЫЙ", true);
        t.addVariant("СУТКИ", true);
        t.addVariant("СУТОЧНЫЙ", true);
        t.addVariant("КАЛЕНДАРНЫЙ ДЕНЬ", true);
        t.addVariant("РАБОЧИЙ ДЕНЬ", true);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("НЕДЕЛЯ", com.pullenti.morph.MorphLang.RU, true, "нед.", com.pullenti.ner.core.NumberExType.WEEK);
        t.addVariant("НЕДЕЛЬНЫЙ", true);
        t.addVariant("КАЛЕНДАРНАЯ НЕДЕЛЯ", false);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ПРОЦЕНТ", com.pullenti.morph.MorphLang.RU, true, "%", com.pullenti.ner.core.NumberExType.PERCENT);
        t.addVariant("%", false);
        t.addVariant("ПРОЦ", true);
        t.addAbridge("ПРОЦ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ШТУКА", com.pullenti.morph.MorphLang.RU, true, "шт.", com.pullenti.ner.core.NumberExType.SHUK);
        t.addVariant("ШТ", false);
        t.addAbridge("ШТ.");
        t.addAbridge("ШТ-К");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("УПАКОВКА", com.pullenti.morph.MorphLang.RU, true, "уп.", com.pullenti.ner.core.NumberExType.UPAK);
        t.addVariant("УПАК", true);
        t.addVariant("УП", true);
        t.addAbridge("УПАК.");
        t.addAbridge("УП.");
        t.addAbridge("УП-КА");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("РУЛОН", com.pullenti.morph.MorphLang.RU, true, "рулон", com.pullenti.ner.core.NumberExType.RULON);
        t.addVariant("РУЛ", true);
        t.addAbridge("РУЛ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("НАБОР", com.pullenti.morph.MorphLang.RU, true, "набор", com.pullenti.ner.core.NumberExType.NABOR);
        t.addVariant("НАБ", true);
        t.addAbridge("НАБ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("КОМПЛЕКТ", com.pullenti.morph.MorphLang.RU, true, "компл.", com.pullenti.ner.core.NumberExType.KOMPLEKT);
        t.addVariant("КОМПЛ", true);
        t.addAbridge("КОМПЛ.");
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ПАРА", com.pullenti.morph.MorphLang.RU, true, "пар", com.pullenti.ner.core.NumberExType.PARA);
        m_Postfixes.add(t);
        t = com.pullenti.ner.core.Termin._new433("ФЛАКОН", com.pullenti.morph.MorphLang.RU, true, "флак.", com.pullenti.ner.core.NumberExType.FLAKON);
        t.addVariant("ФЛ", true);
        t.addAbridge("ФЛ.");
        t.addVariant("ФЛАК", true);
        t.addAbridge("ФЛАК.");
        m_Postfixes.add(t);
        for (com.pullenti.ner.core.Termin te : m_Postfixes.termins) {
            com.pullenti.ner.core.NumberExType ty = (com.pullenti.ner.core.NumberExType)te.tag;
            if (!m_NormalsTyps.containsKey(ty)) 
                m_NormalsTyps.put(ty, te.getCanonicText());
        }
        m_SmallMoney = new com.pullenti.ner.core.TerminCollection();
        t = com.pullenti.ner.core.Termin._new126("УСЛОВНАЯ ЕДИНИЦА", "УЕ", com.pullenti.ner.core.NumberExType.MONEY);
        t.addAbridge("У.Е.");
        t.addAbridge("У.E.");
        t.addAbridge("Y.Е.");
        t.addAbridge("Y.E.");
        m_Postfixes.add(t);
        boolean bb = com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL;
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        for (int k = 0; k < 3; k++) {
            String str = ResourceHelper.getString((k == 0 ? "Money.csv" : (k == 1 ? "MoneyUA.csv" : "MoneyEN.csv")));
            if (str == null) 
                continue;
            com.pullenti.morph.MorphLang lang = (k == 0 ? com.pullenti.morph.MorphLang.RU : (k == 1 ? com.pullenti.morph.MorphLang.UA : com.pullenti.morph.MorphLang.EN));
            if (str == null) 
                continue;
            for (String line0 : com.pullenti.unisharp.Utils.split(str, String.valueOf('\n'), false)) {
                String line = line0.trim();
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(line)) 
                    continue;
                String[] parts = com.pullenti.unisharp.Utils.split(line.toUpperCase(), String.valueOf(';'), false);
                if (parts == null || parts.length != 5) 
                    continue;
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(parts[1]) || com.pullenti.unisharp.Utils.isNullOrEmpty(parts[2])) 
                    continue;
                t = new com.pullenti.ner.core.Termin(parts[1], lang, false);
                t.setCanonicText(parts[2]);
                t.acronym = parts[2];
                t.tag = com.pullenti.ner.core.NumberExType.MONEY;
                for (String p : com.pullenti.unisharp.Utils.split(parts[0], String.valueOf(','), false)) {
                    if (com.pullenti.unisharp.Utils.stringsNe(p, parts[1])) {
                        com.pullenti.ner.core.Termin t0 = new com.pullenti.ner.core.Termin(p, lang, false);
                        t.addVariantTerm(t0);
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "РУБЛЬ")) 
                    t.addAbridge("РУБ.");
                else if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "ГРИВНЯ") || com.pullenti.unisharp.Utils.stringsEq(parts[1], "ГРИВНА")) 
                    t.addAbridge("ГРН.");
                else if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "ДОЛЛАР")) {
                    t.addAbridge("ДОЛ.");
                    t.addAbridge("ДОЛЛ.");
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "ДОЛАР")) 
                    t.addAbridge("ДОЛ.");
                else if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "ИЕНА")) 
                    t.addVariant("ЙЕНА", false);
                else if (com.pullenti.unisharp.Utils.stringsEq(parts[1], "БИТКОЙН")) 
                    t.addVariant("БИТКОИН", false);
                m_Postfixes.add(t);
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(parts[3])) 
                    continue;
                int num = 0;
                int i = parts[3].indexOf(' ');
                if (i < 2) 
                    continue;
                com.pullenti.unisharp.Outargwrapper<Integer> wrapnum478 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres479 = com.pullenti.unisharp.Utils.parseInteger(parts[3].substring(0, 0 + i), 0, null, wrapnum478);
                num = (wrapnum478.value != null ? wrapnum478.value : 0);
                if (!inoutres479) 
                    continue;
                String vv = parts[3].substring(i).trim();
                t = new com.pullenti.ner.core.Termin(null, null, false);
                t.initByNormalText(parts[4], lang);
                t.tag = num;
                if (com.pullenti.unisharp.Utils.stringsNe(vv, parts[4])) {
                    com.pullenti.ner.core.Termin t0 = new com.pullenti.ner.core.Termin(null, null, false);
                    t0.initByNormalText(vv, null);
                    t.addVariantTerm(t0);
                }
                if (com.pullenti.unisharp.Utils.stringsEq(parts[4], "КОПЕЙКА") || com.pullenti.unisharp.Utils.stringsEq(parts[4], "КОПІЙКА")) 
                    t.addAbridge("КОП.");
                m_SmallMoney.add(t);
            }
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = bb;
    }

    public static com.pullenti.ner.core.TerminCollection m_Postfixes;

    public static java.util.HashMap<com.pullenti.ner.core.NumberExType, String> m_NormalsTyps;

    private static com.pullenti.ner.core.TerminCollection m_SmallMoney;

    public NumberExHelper() {
    }
    
    static {
        m_NormalsTyps = new java.util.HashMap<com.pullenti.ner.core.NumberExType, String>();
    }
}
