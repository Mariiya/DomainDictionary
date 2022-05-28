/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class NameToken extends com.pullenti.ner.MetaToken {

    public NameToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public String name;

    public String number;

    public String pref;

    public boolean isDoubt;

    private int m_lev;

    private NameTokenType m_typ = NameTokenType.ANY;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (isDoubt) 
            res.append("? ");
        if (pref != null) 
            res.append(pref).append(" ");
        if (name != null) 
            res.append("\"").append(name).append("\"");
        if (number != null) 
            res.append(" N").append(number);
        return res.toString();
    }

    public static NameToken tryParse(com.pullenti.ner.Token t, NameTokenType ty, int lev) {
        if (t == null || lev > 3) 
            return null;
        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
        NameToken res = null;
        com.pullenti.ner.Token ttt;
        com.pullenti.ner.NumberToken num;
        com.pullenti.ner.core.TerminToken ttok;
        if (br != null) {
            if (!com.pullenti.ner.core.BracketHelper.isBracket(t, true)) 
                return null;
            NameToken nam = tryParse(t.getNext(), ty, lev + 1);
            if (nam != null && nam.getEndToken().getNext() == br.getEndToken()) {
                res = nam;
                nam.setBeginToken(t);
                nam.setEndToken(br.getEndToken());
                res.isDoubt = false;
            }
            else {
                res = new NameToken(t, br.getEndToken());
                com.pullenti.ner.Token tt = br.getEndToken().getPrevious();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    res.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue();
                    tt = tt.getPrevious();
                    if (tt != null && tt.isHiphen()) 
                        tt = tt.getPrevious();
                }
                if (tt != null && tt.getBeginChar() > br.getBeginChar()) 
                    res.name = com.pullenti.ner.core.MiscHelper.getTextValue(t.getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
            }
        }
        else if ((t instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken() && !((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken().chars.isAllLower()) {
            res = _new1249(t, t, true);
            res.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO);
        }
        else if (((ttt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t))) instanceof com.pullenti.ner.NumberToken) {
            res = _new1250(t, ttt, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue());
            if (ttt.getWhitespacesAfterCount() < 2) {
                NameToken nam = tryParse(ttt.getNext(), ty, lev + 1);
                if (nam != null && nam.name != null && nam.number == null) {
                    res.name = nam.name;
                    res.setEndToken(nam.getEndToken());
                }
            }
        }
        else if ((((num = com.pullenti.ner.core.NumberHelper.tryParseAge(t)))) != null) 
            res = _new1251(t, num.getEndToken(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num, com.pullenti.ner.NumberToken.class)).getValue() + " ЛЕТ");
        else if ((((num = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(t)))) != null) 
            res = _new1251(t, num.getEndToken(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num, com.pullenti.ner.NumberToken.class)).getValue() + " ЛЕТ");
        else if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.core.NumberExToken nn = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
            if (nn != null) {
                if (nn.exTyp != com.pullenti.ner.core.NumberExType.UNDEFINED) 
                    return null;
            }
            res = _new1250(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
        }
        else if (t.isHiphen() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
            num = com.pullenti.ner.core.NumberHelper.tryParseAge(t.getNext());
            if (num == null) 
                num = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(t.getNext());
            if (num != null) 
                res = _new1251(t, num.getEndToken(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num, com.pullenti.ner.NumberToken.class)).getValue() + " ЛЕТ");
            else 
                res = _new1255(t, t.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue(), true);
        }
        else if ((t instanceof com.pullenti.ner.ReferentToken) && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "DATE")) {
            String year = t.getReferent().getStringValue("YEAR");
            if (year != null) 
                res = _new1251(t, t, year + " ГОДА");
            else {
                String mon = t.getReferent().getStringValue("MONTH");
                String day = t.getReferent().getStringValue("DAY");
                if (day != null && mon == null && t.getReferent().getParentReferent() != null) 
                    mon = t.getReferent().getParentReferent().getStringValue("MONTH");
                if (mon != null) 
                    res = _new1257(t, t, t.getReferent().toString().toUpperCase());
            }
        }
        else if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        else if (t.getLengthChar() == 1) {
            if ((((ty != NameTokenType.ORG && ty != NameTokenType.STRONG)) || !t.chars.isAllUpper() || t.isWhitespaceAfter()) || !t.chars.isLetter()) 
                return null;
            NameToken _next = tryParse(t.getNext(), ty, lev + 1);
            if (_next != null && _next.number != null && _next.name == null) {
                res = _next;
                res.setBeginToken(t);
                res.name = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
            }
            else if (t.getNext() != null && t.getNext().isChar('.')) {
                StringBuilder nam = new StringBuilder();
                nam.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                com.pullenti.ner.Token t1 = t.getNext();
                for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                    if (!(tt instanceof com.pullenti.ner.TextToken) || tt.getLengthChar() != 1 || !tt.chars.isLetter()) 
                        break;
                    if (tt.getNext() == null || !tt.getNext().isChar('.')) 
                        break;
                    nam.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                    tt = tt.getNext();
                    t1 = tt;
                }
                if (nam.length() >= 3) 
                    res = _new1257(t, t1, nam.toString());
                else {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t, null);
                    if (rt != null) {
                        res = _new1257(t, rt.getEndToken(), rt.referent.getStringValue("LASTNAME"));
                        if (res.name == null) 
                            res.name = rt.referent.toStringEx(false, null, 0).toUpperCase();
                        else 
                            for (com.pullenti.ner.Token tt = t; tt != null && tt.getEndChar() <= rt.getEndChar(); tt = tt.getNext()) {
                                if ((tt instanceof com.pullenti.ner.TextToken) && tt.isValue(res.name, null)) {
                                    res.name = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                                    break;
                                }
                            }
                    }
                }
            }
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ИМЕНИ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ИМ")) {
            com.pullenti.ner.Token tt = t.getNext();
            if (t.isValue("ИМ", null) && tt != null && tt.isChar('.')) 
                tt = tt.getNext();
            NameToken nam = tryParse(tt, NameTokenType.STRONG, lev + 1);
            if (nam != null) {
                nam.setBeginToken(t);
                nam.isDoubt = false;
                res = nam;
            }
        }
        else if ((((ttok = m_Onto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) 
            res = _new1257(t, ttok.getEndToken(), ttok.termin.getCanonicText());
        else {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getBeginToken() == npt.getEndToken()) 
                npt = null;
            if (npt != null && npt.getEndToken().chars.isAllLower()) {
                if (t.chars.isAllLower()) 
                    npt = null;
                else if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(npt.getEndToken())) 
                    npt = null;
            }
            if (npt != null) 
                res = _new1261(t, npt.getEndToken(), npt.getMorph(), com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO).replace("-", " "));
            else if (!t.chars.isAllLower() || t.isValue("МЕСТНОСТЬ", null)) {
                if (TerrItemToken.checkKeyword(t) != null) 
                    return null;
                res = _new1262(t, t, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, t.getMorph());
                if ((((com.pullenti.morph.LanguageHelper.endsWith(res.name, "ОВ") || com.pullenti.morph.LanguageHelper.endsWith(res.name, "ВО"))) && (t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()) && t.getNext().getLengthChar() > 1 && !t.getNext().getMorphClassInDictionary().isUndefined()) {
                    if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(t.getNext())) {
                    }
                    else if (OrgTypToken.tryParse(t.getNext(), false, null) != null) {
                    }
                    else {
                        res.setEndToken(t.getNext());
                        res.name = (res.name + " " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                        res.setMorph(t.getNext().getMorph());
                    }
                }
                if ((t.getWhitespacesAfterCount() < 2) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLetter()) {
                    boolean ok = false;
                    if (t.getNext().getLengthChar() >= 3 && t.getNext().getMorphClassInDictionary().isUndefined()) 
                        ok = true;
                    else {
                        boolean ok1 = false;
                        if ((((t.getNext().getLengthChar() < 4) || t.getMorphClassInDictionary().isUndefined())) && t.getNext().chars.equals(t.chars)) 
                            ok1 = true;
                        else if (t.isValue("МЕСТНОСТЬ", null) && !t.getNext().chars.isAllLower()) 
                            ok = true;
                        if (ok1) {
                            NameToken _next = tryParse(t.getNext(), ty, lev + 1);
                            if (_next == null || _next.getBeginToken() == _next.getEndToken()) 
                                ok = true;
                        }
                    }
                    if (!ok && t.getNext().getMorphClassInDictionary().isAdjective()) {
                        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                        if (mc.isNoun() || mc.isProperGeo()) {
                            if (((short)((t.getMorph().getGender().value()) & (t.getNext().getMorph().getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                com.pullenti.ner.Token tt = t.getNext().getNext();
                                if (tt == null) 
                                    ok = true;
                                else if (tt.isComma() || tt.isNewlineAfter()) 
                                    ok = true;
                                else if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(tt, false, false)) 
                                    ok = true;
                                else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(tt, false)) 
                                    ok = true;
                            }
                        }
                    }
                    if (ok) {
                        if (OrgTypToken.tryParse(t.getNext(), false, null) != null) 
                            ok = false;
                    }
                    if (ok) {
                        res.setEndToken(t.getNext());
                        res.name = (res.name + " " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                    }
                }
            }
            if (res != null && res.getEndToken().isValue("УСАДЬБА", null) && (res.getWhitespacesAfterCount() < 2)) {
                NameToken res1 = tryParse(res.getEndToken().getNext(), ty, lev + 1);
                if (res1 != null && res1.name != null) {
                    res.setEndToken(res1.getEndToken());
                    res.name = (res.name + " " + res1.name);
                }
            }
        }
        if (res == null || res.getWhitespacesAfterCount() > 2) 
            return res;
        ttt = res.getEndToken().getNext();
        if (ttt != null && ttt.isHiphen()) {
            num = com.pullenti.ner.core.NumberHelper.tryParseAge(ttt.getNext());
            if (num == null) 
                num = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(ttt.getNext());
            if (num != null) {
                res.pref = num.getValue() + " ЛЕТ";
                res.setEndToken(num.getEndToken());
            }
            else if ((ttt.getNext() instanceof com.pullenti.ner.NumberToken) && res.number == null) {
                res.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class)).getValue();
                res.setEndToken(ttt.getNext());
            }
            if ((ttt.getNext() instanceof com.pullenti.ner.TextToken) && !ttt.isWhitespaceAfter() && res.name != null) {
                res.name = (res.name + " " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.TextToken.class)).term);
                res.setEndToken(ttt.getNext());
            }
        }
        else if ((((num = com.pullenti.ner.core.NumberHelper.tryParseAge(ttt)))) != null) {
            res.pref = num.getValue() + " ЛЕТ";
            res.setEndToken(num.getEndToken());
        }
        else if ((((num = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(ttt)))) != null) {
            res.pref = num.getValue() + " ЛЕТ";
            res.setEndToken(num.getEndToken());
        }
        else if (ttt instanceof com.pullenti.ner.NumberToken) {
            boolean ok = false;
            if (ty == NameTokenType.ORG) 
                ok = true;
            if (ok) {
                if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(ttt.getNext())) 
                    ok = false;
                else if (ttt.getNext() != null) {
                    if (ttt.getNext().isValue("КМ", null) || ttt.getNext().isValue("КИЛОМЕТР", null)) 
                        ok = false;
                }
            }
            if (ok) {
                res.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue();
                res.setEndToken(ttt);
            }
        }
        if (res.number == null) {
            ttt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(res.getEndToken().getNext());
            if (ttt instanceof com.pullenti.ner.NumberToken) {
                res.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue();
                res.setEndToken(ttt);
            }
        }
        if ((res.getWhitespacesAfterCount() < 3) && res.name == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getEndToken().getNext(), false, false)) {
            NameToken nam = tryParse(res.getEndToken().getNext(), ty, lev + 1);
            if (nam != null) {
                res.name = nam.name;
                res.setEndToken(nam.getEndToken());
                res.isDoubt = false;
            }
        }
        if (res.pref != null && res.name == null && res.number == null) {
            NameToken nam = tryParse(res.getEndToken().getNext(), ty, lev + 1);
            if (nam != null && nam.name != null && nam.pref == null) {
                res.name = nam.name;
                res.number = nam.number;
                res.setEndToken(nam.getEndToken());
            }
        }
        res.m_lev = lev;
        res.m_typ = ty;
        if (res.getWhitespacesAfterCount() < 3) {
            com.pullenti.ner.core.TerminToken nn = m_Onto.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (nn != null) {
                res.setEndToken(nn.getEndToken());
                res.name = (res.name + " " + com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(nn, com.pullenti.ner.core.GetTextAttr.NO));
            }
        }
        res.tryAttachNumber();
        return res;
    }

    public void tryAttachNumber() {
        if (getWhitespacesAfterCount() > 2) 
            return;
        if (number == null) {
            NameToken nam2 = tryParse(this.getEndToken().getNext(), m_typ, m_lev + 1);
            if ((nam2 != null && nam2.number != null && nam2.name == null) && nam2.pref == null) {
                if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(nam2.getEndToken().getNext())) {
                }
                else {
                    number = nam2.number;
                    setEndToken(nam2.getEndToken());
                }
            }
        }
        if ((m_typ == NameTokenType.ORG && (getEndToken() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(number, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(this.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue())) && !isWhitespaceAfter()) {
            StringBuilder tmp = new StringBuilder(number);
            String delim = null;
            for (com.pullenti.ner.Token tt = getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isWhitespaceBefore()) 
                    break;
                if (tt.isCharOf(",.") || tt.isTableControlChar()) 
                    break;
                if (tt.isCharOf("\\/")) {
                    delim = "/";
                    continue;
                }
                else if (tt.isHiphen()) {
                    delim = "-";
                    continue;
                }
                if ((tt instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    if (delim != null && Character.isDigit(tmp.charAt(tmp.length() - 1))) 
                        tmp.append(delim);
                    delim = null;
                    tmp.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                    setEndToken(tt);
                    continue;
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.chars.isLetter()) {
                    if (delim != null && Character.isLetter(tmp.charAt(tmp.length() - 1))) 
                        tmp.append(delim);
                    delim = null;
                    tmp.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                    setEndToken(tt);
                    continue;
                }
                break;
            }
            number = tmp.toString();
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Onto;

    public static void initialize() {
        m_Onto = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin("СОВЕТСКОЙ АРМИИ И ВОЕННО МОРСКОГО ФЛОТА", null, false);
        t.addVariant("СА И ВМФ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new969("СОВЕТСКОЙ АРМИИ", "СА");
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new969("МИНИСТЕРСТВО ОБОРОНЫ", "МО");
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new969("ВОЕННО МОРСКОЙ ФЛОТ", "ВМФ");
        m_Onto.add(t);
        m_Onto.add(new com.pullenti.ner.core.Termin("МОЛОДАЯ ГВАРДИЯ", null, false));
        m_Onto.add(new com.pullenti.ner.core.Termin("ЗАЩИТНИКИ БЕЛОГО ДОМА", null, false));
    }

    public static NameToken _new1249(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.isDoubt = _arg3;
        return res;
    }

    public static NameToken _new1250(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.number = _arg3;
        return res;
    }

    public static NameToken _new1251(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.pref = _arg3;
        return res;
    }

    public static NameToken _new1255(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.number = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static NameToken _new1257(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.name = _arg3;
        return res;
    }

    public static NameToken _new1261(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, String _arg4) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.name = _arg4;
        return res;
    }

    public static NameToken _new1262(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.MorphCollection _arg4) {
        NameToken res = new NameToken(_arg1, _arg2);
        res.name = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public NameToken() {
        super();
    }
}
