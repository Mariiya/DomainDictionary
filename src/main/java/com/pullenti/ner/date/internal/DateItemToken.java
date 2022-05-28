/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

// Примитив, из которых состоит дата
public class DateItemToken extends com.pullenti.ner.MetaToken {

    public DateItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        lTyp = FirstLastTyp.NO;
    }

    public DateItemType typ = DateItemType.NUMBER;

    public String stringValue;

    public int intValue;

    public com.pullenti.ner.date.DatePointerType ptr = com.pullenti.ner.date.DatePointerType.NO;

    public com.pullenti.morph.MorphLang lang;

    public int newAge = 0;

    public boolean relate;

    public FirstLastTyp lTyp = FirstLastTyp.NO;

    public java.util.ArrayList<DateItemToken> newStyle = null;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append((intValue == 0 ? stringValue : ((Integer)intValue).toString()));
        if (ptr != com.pullenti.ner.date.DatePointerType.NO) 
            res.append(" (").append(ptr.toString()).append(")");
        if (lTyp != FirstLastTyp.NO) 
            res.append(" ").append(lTyp.toString());
        if (relate) 
            res.append(" relate");
        if (newStyle != null) {
            for (DateItemToken ns : newStyle) {
                res.append(" (new style: ").append(ns.toString()).append(")");
            }
        }
        return res.toString();
    }

    public int getYear() {
        if (m_Year > 0) 
            return m_Year;
        if (intValue == 0) 
            return 0;
        if (newAge == 0) {
            if (intValue < 16) 
                return 2000 + intValue;
            if (intValue <= ((java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.of(0, 0)).getYear() - 2000) + 5)) 
                return 2000 + intValue;
            if (intValue < 100) 
                return 1900 + intValue;
        }
        return intValue;
    }

    public int setYear(int value) {
        m_Year = value;
        return value;
    }


    private int m_Year = -1;

    public int getYear0() {
        if (newAge < 0) 
            return -getYear();
        return getYear();
    }


    public boolean canBeYear() {
        if (lTyp != FirstLastTyp.NO) 
            return false;
        if (typ == DateItemType.YEAR) 
            return true;
        if (typ == DateItemType.MONTH || typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR) 
            return false;
        if (intValue >= 50 && (intValue < 100)) 
            return true;
        if ((intValue < 1000) || intValue > 2100) 
            return false;
        return true;
    }


    public boolean canByMonth() {
        if (lTyp != FirstLastTyp.NO) 
            return false;
        if (m_CanByMonth >= 0) 
            return m_CanByMonth == 1;
        if (typ == DateItemType.MONTH) 
            return true;
        if (typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR || typ == DateItemType.POINTER) 
            return false;
        return intValue > 0 && intValue <= 12;
    }

    public boolean setByMonth(boolean value) {
        m_CanByMonth = (value ? 1 : 0);
        return value;
    }


    private int m_CanByMonth = -1;

    public boolean canBeDay() {
        if ((typ == DateItemType.MONTH || typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR) || typ == DateItemType.POINTER) 
            return false;
        if (lTyp != FirstLastTyp.NO) 
            return false;
        return intValue > 0 && intValue <= 31;
    }


    public boolean canBeHour() {
        if (lTyp != FirstLastTyp.NO) 
            return false;
        if (typ != DateItemType.NUMBER) 
            return typ == DateItemType.HOUR;
        if (getLengthChar() != 2) {
            if (getLengthChar() != 1 || intValue >= 24) 
                return false;
            if (intValue == 0) 
                return true;
            if (getBeginToken().getNext() == null || !getBeginToken().getNext().isCharOf(":.") || isWhitespaceAfter()) 
                return false;
            if (!(this.getBeginToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                return false;
            if (getBeginToken().getNext().getNext().getLengthChar() != 2) 
                return false;
            if (getBeginToken().getNext().isChar('.')) {
                if (getBeginToken().getPrevious() != null && getBeginToken().getPrevious().isValue("В", null)) {
                }
                else 
                    return false;
            }
            if (isWhitespaceBefore()) 
                return true;
            if (getBeginToken().getPrevious() != null && getBeginToken().getPrevious().isCharOf("(,")) 
                return true;
            return false;
        }
        return intValue >= 0 && (intValue < 24);
    }


    public boolean canBeMinute() {
        if (lTyp != FirstLastTyp.NO) 
            return false;
        if (typ != DateItemType.NUMBER) 
            return typ == DateItemType.MINUTE;
        if (getLengthChar() != 2) 
            return false;
        return intValue >= 0 && (intValue < 60);
    }


    public boolean isZeroHeaded() {
        return kit.getSofa().getText().charAt(this.getBeginChar()) == '0';
    }


    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        DateAnalyzerData ad = com.pullenti.ner.date.DateAnalyzer.getData(t0);
        if (ad == null) 
            return;
        java.util.ArrayList<DateItemToken> prevs = new java.util.ArrayList<DateItemToken>();
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            prevs.clear();
            int kk = 0;
            com.pullenti.ner.Token tt0 = t;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (kk < 10); tt = tt.getPrevious(),kk++) {
                DateTokenData d0 = (DateTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, DateTokenData.class);
                if (d0 == null) 
                    continue;
                if (d0.dat == null) 
                    continue;
                if (d0.dat.getEndToken().getNext() == tt0) {
                    prevs.add(0, d0.dat);
                    tt0 = tt;
                }
                else if (d0.dat.getEndChar() < tt.getEndChar()) 
                    break;
            }
            DateTokenData d = (DateTokenData)com.pullenti.unisharp.Utils.cast(t.tag, DateTokenData.class);
            DateItemToken ter = tryParse(t, prevs, false);
            if (ter != null) {
                if (d == null) 
                    d = new DateTokenData(t);
                d.dat = ter;
            }
        }
    }

    public static DateItemToken tryParse(com.pullenti.ner.Token t, java.util.ArrayList<DateItemToken> prev, boolean detailRegime) {
        if (t == null) 
            return null;
        DateAnalyzerData ad = com.pullenti.ner.date.DateAnalyzer.getData(t);
        if ((ad != null && SPEEDREGIME && ad.dRegime) && !detailRegime) {
            DateTokenData d = (DateTokenData)com.pullenti.unisharp.Utils.cast(t.tag, DateTokenData.class);
            if (d != null) 
                return d.dat;
            return null;
        }
        if (ad != null) {
            if (ad.level > 3) 
                return null;
            ad.level++;
        }
        DateItemToken res = tryParseInt(t, prev, detailRegime);
        if (ad != null) 
            ad.level--;
        return res;
    }

    private static DateItemToken tryParseInt(com.pullenti.ner.Token t, java.util.ArrayList<DateItemToken> prev, boolean detailRegime) {
        com.pullenti.ner.Token t0 = t;
        if (t0.isChar('_')) {
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isNewlineBefore()) 
                    return null;
                if (!t.isChar('_')) 
                    break;
            }
        }
        else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0, true, false)) {
            boolean ok = false;
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, t0, false)) {
                    ok = true;
                    break;
                }
                else if (!t.isChar('_')) 
                    break;
            }
            if (!ok) 
                t = t0;
            else 
                for (t = t.getNext(); t != null; t = t.getNext()) {
                    if (!t.isChar('_')) 
                        break;
                }
        }
        else if ((t0 instanceof com.pullenti.ner.TextToken) && t0.isValue("THE", null)) {
            DateItemToken res0 = _TryAttach(t.getNext(), prev, detailRegime);
            if (res0 != null) {
                res0.setBeginToken(t);
                return res0;
            }
        }
        DateItemToken res = _TryAttach(t, prev, detailRegime);
        if (res == null) 
            return null;
        res.setBeginToken(t0);
        if (!res.isWhitespaceAfter() && res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('_')) {
            for (t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
                if (!t.isChar('_')) 
                    break;
                else 
                    res.setEndToken(t);
            }
        }
        if (res.typ == DateItemType.YEAR || res.typ == DateItemType.CENTURY || res.typ == DateItemType.NUMBER) {
            com.pullenti.ner.core.TerminToken tok = null;
            int ii = 0;
            t = res.getEndToken().getNext();
            if (t != null && t.isValue("ДО", null)) {
                tok = m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                ii = -1;
            }
            else if (t != null && t.isValue("ОТ", "ВІД")) {
                tok = m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                ii = 1;
            }
            else {
                tok = m_NewAge.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                ii = 1;
            }
            if (tok != null) {
                res.newAge = (ii < 0 ? -1 : 1);
                res.setEndToken(tok.getEndToken());
                if (res.typ == DateItemType.NUMBER) 
                    res.typ = DateItemType.YEAR;
            }
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('(')) {
            com.pullenti.ner.Token t1 = res.getEndToken().getNext().getNext();
            java.util.ArrayList<DateItemToken> li = tryAttachList(t1, 20);
            if ((li != null && li.size() > 0 && ((li.get(0).typ == DateItemType.NUMBER || li.get(0).typ == DateItemType.DAY))) && li.get(li.size() - 1).getEndToken().getNext() != null && li.get(li.size() - 1).getEndToken().getNext().isChar(')')) {
                res.newStyle = li;
                res.setEndToken(li.get(li.size() - 1).getEndToken().getNext());
                if (res.canBeYear() && res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ГОД", "РІК")) {
                    res.setEndToken(res.getEndToken().getNext());
                    res.typ = DateItemType.YEAR;
                }
            }
        }
        return res;
    }

    private static boolean _isNewAge(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t.isValue("ДО", null)) 
            return m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null;
        else if (t.isValue("ОТ", "ВІД")) 
            return m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null;
        return m_NewAge.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null;
    }

    private static DateItemToken _TryAttach(com.pullenti.ner.Token t, java.util.ArrayList<DateItemToken> prev, boolean detailRegime) {
        if (t == null) 
            return null;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token begin = t;
        com.pullenti.ner.Token end = t;
        boolean isInBrack = false;
        if ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false) && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
            nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
            end = t.getNext().getNext();
            isInBrack = true;
        }
        if ((t.isNewlineBefore() && com.pullenti.ner.core.BracketHelper.isBracket(t, false) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && com.pullenti.ner.core.BracketHelper.isBracket(t.getNext().getNext(), false)) {
            nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
            end = t.getNext().getNext();
            isInBrack = true;
        }
        FirstLastTyp flt = FirstLastTyp.NO;
        if (t.isValue("ПЕРВЫЙ", null)) 
            flt = FirstLastTyp.FIRST;
        else if (t.isValue("ПОСЛЕДНИЙ", null)) 
            flt = FirstLastTyp.LAST;
        if (flt != FirstLastTyp.NO && t.getNext() != null) {
            com.pullenti.ner.Token t1 = t.getNext();
            if (t1 instanceof com.pullenti.ner.NumberToken) 
                t1 = t1.getNext();
            DateItemType dty = DateItemType.NUMBER;
            if (t1.isValue("ДЕНЬ", null)) 
                dty = DateItemType.DAY;
            else if (t1.isValue("МЕСЯЦ", "МІСЯЦЬ")) 
                dty = DateItemType.MONTH;
            else if (t1.isValue("КВАРТАЛ", null)) 
                dty = DateItemType.QUARTAL;
            else if (t1.isValue("ПОЛУГОДИЕ", "ПІВРІЧЧЯ") || t1.isValue("ПОЛГОДА", "ПІВРОКУ")) 
                dty = DateItemType.HALFYEAR;
            if (dty != DateItemType.NUMBER) {
                DateItemToken res = _new668(t, t1, dty, flt, 1);
                if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) 
                    res.intValue = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                return res;
            }
            if (t1.isValue("ГОД", "РІК") && com.pullenti.ner.core.NumberHelper.tryParseRoman(t1.getNext()) != null) 
                return _new669(t, t1, DateItemType.POINTER, (flt == FirstLastTyp.LAST ? com.pullenti.ner.date.DatePointerType.END : com.pullenti.ner.date.DatePointerType.BEGIN));
        }
        if (nt != null) {
            if (nt.getIntValue() == null) 
                return null;
            if (nt.typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                if (nt.getMorph()._getClass().isNoun() && !nt.getMorph()._getClass().isAdjective()) {
                    if (t.getNext() != null && ((t.getNext().isValue("КВАРТАЛ", null) || t.getNext().isValue("ПОЛУГОДИЕ", null) || t.getNext().isValue("ПІВРІЧЧЯ", null)))) {
                    }
                    else 
                        return null;
                }
            }
            if (com.pullenti.ner.core.NumberHelper.tryParseAge(nt) != null) 
                return null;
            com.pullenti.ner.Token tt;
            DateItemToken res = _new670(begin, end, DateItemType.NUMBER, nt.getIntValue(), nt.getMorph());
            if ((res.intValue == 20 && (nt.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) && nt.getNext().getLengthChar() == 2 && prev != null) {
                int num = 2000 + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                if ((num < 2030) && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.MONTH) {
                    boolean ok = false;
                    if (nt.getWhitespacesAfterCount() == 1) 
                        ok = true;
                    else if (nt.isNewlineAfter() && nt.isNewlineAfter()) 
                        ok = true;
                    if (ok) {
                        nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class);
                        res.setEndToken(nt);
                        res.intValue = num;
                    }
                }
            }
            if (res.intValue == 20 || res.intValue == 201) {
                tt = t.getNext();
                if (tt != null && tt.isChar('_')) {
                    for (; tt != null; tt = tt.getNext()) {
                        if (!tt.isChar('_')) 
                            break;
                    }
                    tt = testYearRusWord(tt, false);
                    if (tt != null) {
                        res.intValue = 0;
                        res.setEndToken(tt);
                        res.typ = DateItemType.YEAR;
                        return res;
                    }
                }
            }
            if (res.intValue <= 12 && t.getNext() != null && (t.getWhitespacesAfterCount() < 3)) {
                tt = t.getNext();
                if (tt.isValue("ЧАС", null)) {
                    if (((t.getPrevious() instanceof com.pullenti.ner.TextToken) && !t.getPrevious().chars.isLetter() && !t.isWhitespaceBefore()) && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) && !t.getPrevious().isWhitespaceBefore()) {
                    }
                    else {
                        res.typ = DateItemType.HOUR;
                        res.setEndToken(tt);
                        tt = tt.getNext();
                        if (tt != null && tt.isChar('.')) {
                            res.setEndToken(tt);
                            tt = tt.getNext();
                        }
                    }
                }
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isValue("УТРО", "РАНОК")) {
                        res.setEndToken(tt);
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("ВЕЧЕР", "ВЕЧІР") && tt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        res.setEndToken(tt);
                        res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("ДЕНЬ", null) && tt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        res.setEndToken(tt);
                        if (res.intValue < 10) 
                            res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("НОЧЬ", "НІЧ") && tt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        res.setEndToken(tt);
                        if (res.intValue == 12) 
                            res.intValue = 0;
                        else if (res.intValue > 9) 
                            res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isComma() || tt.getMorph()._getClass().isAdverb()) 
                        continue;
                    break;
                }
                if (res.typ == DateItemType.HOUR) 
                    return res;
            }
            boolean _canBeYear = true;
            if (prev != null && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.MONTH) {
            }
            else if ((prev != null && prev.size() >= 4 && prev.get(prev.size() - 1).typ == DateItemType.DELIM) && prev.get(prev.size() - 2).canByMonth()) {
            }
            else if (nt.getNext() != null && (nt.getNext().isValue("ГОД", "РІК"))) {
                if (res.intValue < 1000) 
                    _canBeYear = false;
            }
            tt = testYearRusWord(nt.getNext(), false);
            if (tt != null && _isNewAge(tt.getNext())) {
                res.typ = DateItemType.YEAR;
                res.setEndToken(tt);
            }
            else if (_canBeYear) {
                if (res.canBeYear() || res.typ == DateItemType.NUMBER) {
                    if ((((tt = testYearRusWord(nt.getNext(), res.isNewlineBefore())))) != null) {
                        if ((tt.isValue("Г", null) && !tt.isWhitespaceBefore() && t.getPrevious() != null) && ((t.getPrevious().isValue("КОРПУС", null) || t.getPrevious().isValue("КОРП", null)))) {
                        }
                        else if ((((nt.getNext().isValue("Г", null) && (t.getWhitespacesBeforeCount() < 3) && t.getPrevious() != null) && t.getPrevious().isValue("Я", null) && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isCharOf("\\/") && t.getPrevious().getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().getPrevious().isValue("А", null)) 
                            return null;
                        else if (nt.getNext().getLengthChar() == 1 && !res.canBeYear() && ((prev == null || ((prev.size() > 0 && prev.get(prev.size() - 1).typ != DateItemType.DELIM))))) {
                        }
                        else {
                            res.setEndToken(tt);
                            res.typ = DateItemType.YEAR;
                            res.lang = tt.getMorph().getLanguage();
                        }
                    }
                    if (((res.typ == DateItemType.NUMBER && !t.isNewlineBefore() && t.getPrevious() != null) && t.getPrevious().getMorph()._getClass().isPreposition() && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isValue("ГОД", "")) 
                        res.typ = DateItemType.YEAR;
                }
                else if (tt != null && (nt.getWhitespacesAfterCount() < 2) && (nt.getEndChar() - nt.getBeginChar()) == 1) {
                    res.setEndToken(tt);
                    res.typ = DateItemType.YEAR;
                    res.lang = tt.getMorph().getLanguage();
                }
            }
            if (nt.getPrevious() != null) {
                if (nt.getPrevious().isValue("В", "У") || nt.getPrevious().isValue("К", null) || nt.getPrevious().isValue("ДО", null)) {
                    if ((((tt = testYearRusWord(nt.getNext(), false)))) != null) {
                        boolean ok = false;
                        if ((res.intValue < 100) && (tt instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "ГОДА") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "РОКИ")))) {
                        }
                        else {
                            ok = true;
                            if (nt.getPrevious().isValue("ДО", null) && nt.getNext().isValue("Г", null)) {
                                int cou = 0;
                                for (com.pullenti.ner.Token ttt = nt.getPrevious().getPrevious(); ttt != null && (cou < 10); ttt = ttt.getPrevious(),cou++) {
                                    com.pullenti.ner.measure.internal.MeasureToken mt = com.pullenti.ner.measure.internal.MeasureToken.tryParse(ttt, null, false, false, false, false);
                                    if (mt != null && mt.getEndChar() > nt.getEndChar()) {
                                        ok = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (ok) {
                            res.setEndToken(tt);
                            res.typ = DateItemType.YEAR;
                            res.lang = tt.getMorph().getLanguage();
                            res.setBeginToken(nt.getPrevious());
                        }
                    }
                }
                else if (((nt.getPrevious().isValue("IN", null) || nt.getPrevious().isValue("SINCE", null))) && res.canBeYear()) {
                    com.pullenti.ner.measure.internal.NumbersWithUnitToken uu = (nt.getPrevious().isValue("IN", null) ? com.pullenti.ner.measure.internal.NumbersWithUnitToken.tryParse(nt, null, com.pullenti.ner.measure.internal.NumberWithUnitParseAttr.NO) : null);
                    if (uu != null && uu.units.size() > 0) {
                    }
                    else {
                        res.typ = DateItemType.YEAR;
                        res.setBeginToken(nt.getPrevious());
                    }
                }
                else if (nt.getPrevious().isValue("NEL", null) || nt.getPrevious().isValue("DEL", null)) {
                    if (res.canBeYear()) {
                        res.typ = DateItemType.YEAR;
                        res.lang = com.pullenti.morph.MorphLang.IT;
                        res.setBeginToken(nt.getPrevious());
                    }
                }
                else if (nt.getPrevious().isValue("IL", null) && res.canBeDay()) {
                    res.lang = com.pullenti.morph.MorphLang.IT;
                    res.setBeginToken(nt.getPrevious());
                }
            }
            com.pullenti.ner.Token t1 = res.getEndToken().getNext();
            if (t1 != null) {
                if (t1.isValue("ЧАС", "ГОДИНА") || t1.isValue("HOUR", null)) {
                    if ((((prev != null && prev.size() == 2 && prev.get(0).canBeHour()) && prev.get(1).typ == DateItemType.DELIM && !prev.get(1).isWhitespaceAfter()) && !prev.get(1).isWhitespaceAfter() && res.intValue >= 0) && (res.intValue < 59)) {
                        prev.get(0).typ = DateItemType.HOUR;
                        res.typ = DateItemType.MINUTE;
                        res.setEndToken(t1);
                    }
                    else if (res.intValue < 24) {
                        if (t1.getNext() != null && t1.getNext().isChar('.')) 
                            t1 = t1.getNext();
                        res.typ = DateItemType.HOUR;
                        res.setEndToken(t1);
                    }
                }
                else if ((res.intValue < 60) && ((t1.isValue("МИНУТА", "ХВИЛИНА") || t1.isValue("МИН", null) || t.isValue("MINUTE", null)))) {
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.typ = DateItemType.MINUTE;
                    res.setEndToken(t1);
                }
                else if ((res.intValue < 60) && ((t1.isValue("СЕКУНДА", null) || t1.isValue("СЕК", null) || t1.isValue("SECOND", null)))) {
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.typ = DateItemType.SECOND;
                    res.setEndToken(t1);
                }
                else if ((res.intValue < 30) && ((t1.isValue("ВЕК", "ВІК") || t1.isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")))) {
                    res.typ = DateItemType.CENTURY;
                    res.setEndToken(t1);
                }
                else if ((res.intValue < 10) && ((t1.isValue("ДЕСЯТИЛЕТИЕ", "ДЕСЯТИЛІТТЯ") || t1.isValue("ДЕКАДА", null)))) {
                    res.typ = DateItemType.TENYEARS;
                    res.setEndToken(t1);
                }
                else if (res.intValue <= 4 && t1.isValue("КВАРТАЛ", null)) {
                    res.typ = DateItemType.QUARTAL;
                    res.setEndToken(t1);
                }
                else if (res.intValue <= 2 && ((t1.isValue("ПОЛУГОДИЕ", null) || t1.isValue("ПІВРІЧЧЯ", null)))) {
                    res.typ = DateItemType.HALFYEAR;
                    res.setEndToken(t1);
                }
            }
            return res;
        }
        com.pullenti.ner.TextToken t0 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (t0 == null) 
            return null;
        String txt = t0.getSourceText();
        if ((txt.charAt(0) == 'I' || txt.charAt(0) == 'X' || txt.charAt(0) == 'Х') || txt.charAt(0) == 'V' || ((t0.chars.isLatinLetter() && t0.chars.isAllUpper()))) {
            com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if (lat != null && lat.getEndToken().getNext() != null && lat.getIntValue() != null) {
                int val = lat.getIntValue();
                com.pullenti.ner.Token tt = lat.getEndToken().getNext();
                if (tt.isValue("КВАРТАЛ", null) && val > 0 && val <= 4) 
                    return _new671(t, tt, DateItemType.QUARTAL, val);
                if (tt.isValue("ПОЛУГОДИЕ", "ПІВРІЧЧЯ") && val > 0 && val <= 2) 
                    return _new671(t, lat.getEndToken().getNext(), DateItemType.HALFYEAR, val);
                if (tt.isValue("ВЕК", "ВІК") || tt.isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")) 
                    return _new671(t, lat.getEndToken().getNext(), DateItemType.CENTURY, val);
                if (tt.isValue("ДЕСЯТИЛЕТИЕ", "ДЕСЯТИЛІТТЯ") || tt.isValue("ДЕКАДА", null)) 
                    return _new671(t, lat.getEndToken().getNext(), DateItemType.TENYEARS, val);
                if (((tt.isValue("В", null) || tt.isValue("ВВ", null))) && tt.getNext() != null && tt.getNext().isChar('.')) {
                    if (prev != null && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.POINTER) 
                        return _new671(t, tt.getNext(), DateItemType.CENTURY, val);
                    if (_isNewAge(tt.getNext().getNext()) || !tt.isWhitespaceBefore()) 
                        return _new671(t, tt.getNext(), DateItemType.CENTURY, val);
                }
                if (tt.isHiphen()) {
                    com.pullenti.ner.NumberToken lat2 = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt.getNext());
                    if (lat2 != null && lat2.getIntValue() != null && lat2.getEndToken().getNext() != null) {
                        if (lat2.getEndToken().getNext().isValue("ВЕК", "ВІК") || lat2.getEndToken().getNext().isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")) {
                            DateItemToken ddd = tryParse(tt.getNext(), null, false);
                            return _new677(t, lat.getEndToken(), DateItemType.CENTURY, val, (ddd != null ? ddd.newAge : 0));
                        }
                    }
                }
                java.util.ArrayList<DateItemToken> pr0 = null;
                for (com.pullenti.ner.Token ttt = tt; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isHiphen() || ttt.isCommaAnd()) 
                        continue;
                    com.pullenti.morph.MorphClass mc = ttt.getMorphClassInDictionary();
                    if (mc.isPreposition()) 
                        continue;
                    DateItemToken nex = tryParse(ttt, pr0, false);
                    if (nex == null) 
                        break;
                    if (nex.typ == DateItemType.POINTER) {
                        if (pr0 == null) 
                            pr0 = new java.util.ArrayList<DateItemToken>();
                        pr0.add(nex);
                        ttt = nex.getEndToken();
                        continue;
                    }
                    if (nex.typ == DateItemType.CENTURY || nex.typ == DateItemType.QUARTAL) 
                        return _new677(t, lat.getEndToken(), nex.typ, val, nex.newAge);
                    break;
                }
            }
        }
        if (t == null) 
            return null;
        if (t != null && t.isValue("НАПРИКІНЦІ", null)) 
            return _new679(t, t, DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.END, "конец");
        if (t != null && t.isValue("ДОНЕДАВНА", null)) 
            return _new679(t, t, DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.TODAY, "сегодня");
        if (prev == null) {
            if (t != null) {
                if (t.isValue("ОКОЛО", "БІЛЯ") || t.isValue("ПРИМЕРНО", "ПРИБЛИЗНО") || t.isValue("ABOUT", null)) 
                    return _new679(t, t, DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.ABOUT, "около");
            }
            if (t.isValue("ОК", null) || t.isValue("OK", null)) {
                if (t.getNext() != null && t.getNext().isChar('.')) 
                    return _new679(t, t.getNext(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.ABOUT, "около");
                return _new679(t, t, DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.ABOUT, "около");
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Seasons.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if ((tok != null && ((com.pullenti.ner.date.DatePointerType)tok.termin.tag) == com.pullenti.ner.date.DatePointerType.SUMMER && t.getMorph().getLanguage().isRu()) && (t instanceof com.pullenti.ner.TextToken)) {
            String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
            if (com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТОМ") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТА") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТО")) 
                tok = null;
        }
        if (tok != null) 
            return _new669(t, tok.getEndToken(), DateItemType.POINTER, (com.pullenti.ner.date.DatePointerType)tok.termin.tag);
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null) {
            tok = m_Seasons.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if ((tok != null && ((com.pullenti.ner.date.DatePointerType)tok.termin.tag) == com.pullenti.ner.date.DatePointerType.SUMMER && t.getMorph().getLanguage().isRu()) && (t instanceof com.pullenti.ner.TextToken)) {
                String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТОМ") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТА") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТО")) 
                    tok = null;
            }
            if (tok != null) 
                return _new669(t, tok.getEndToken(), DateItemType.POINTER, (com.pullenti.ner.date.DatePointerType)tok.termin.tag);
            DateItemType _typ = DateItemType.NUMBER;
            if (npt.noun.isValue("КВАРТАЛ", null)) 
                _typ = DateItemType.QUARTAL;
            else if (npt.getEndToken().isValue("ПОЛУГОДИЕ", "ПІВРІЧЧЯ")) 
                _typ = DateItemType.HALFYEAR;
            else if (npt.getEndToken().isValue("ДЕСЯТИЛЕТИЕ", "ДЕСЯТИЛІТТЯ") || npt.getEndToken().isValue("ДЕКАДА", null)) 
                _typ = DateItemType.TENYEARS;
            else if (npt.getEndToken().isValue("НАЧАЛО", "ПОЧАТОК")) 
                return _new679(t, npt.getEndToken(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.BEGIN, "начало");
            else if (npt.getEndToken().isValue("СЕРЕДИНА", null)) 
                return _new679(t, npt.getEndToken(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.CENTER, "середина");
            else if (npt.getEndToken().isValue("КОНЕЦ", null) || npt.getEndToken().isValue("КІНЕЦЬ", null) || npt.getEndToken().isValue("НАПРИКІНЕЦЬ", null)) 
                return _new679(t, npt.getEndToken(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.END, "конец");
            else if (npt.getEndToken().isValue("ВРЕМЯ", null) && npt.adjectives.size() > 0 && npt.getEndToken().getPrevious().isValue("НАСТОЯЩЕЕ", null)) 
                return _new679(t, npt.getEndToken(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.TODAY, "сегодня");
            else if (npt.getEndToken().isValue("ЧАС", null) && npt.adjectives.size() > 0 && npt.getEndToken().getPrevious().isValue("ДАНИЙ", null)) 
                return _new679(t, npt.getEndToken(), DateItemType.POINTER, com.pullenti.ner.date.DatePointerType.TODAY, "сегодня");
            if (_typ != DateItemType.NUMBER || detailRegime) {
                int delta = 0;
                boolean ok = false;
                if (npt.adjectives.size() > 0) {
                    if (npt.adjectives.get(0).isValue("ПОСЛЕДНИЙ", "ОСТАННІЙ")) 
                        return _new691(t0, npt.getEndToken(), _typ, (_typ == DateItemType.QUARTAL ? 4 : (_typ == DateItemType.TENYEARS ? 9 : 2)), FirstLastTyp.LAST);
                    if (npt.adjectives.get(0).isValue("ПРЕДЫДУЩИЙ", "ПОПЕРЕДНІЙ") || npt.adjectives.get(0).isValue("ПРОШЛЫЙ", null)) 
                        delta = -1;
                    else if (npt.adjectives.get(0).isValue("СЛЕДУЮЩИЙ", null) || npt.adjectives.get(0).isValue("ПОСЛЕДУЮЩИЙ", null) || npt.adjectives.get(0).isValue("НАСТУПНИЙ", null)) 
                        delta = 1;
                    else if (npt.adjectives.get(0).isValue("ЭТОТ", "ЦЕЙ") || npt.adjectives.get(0).isValue("ТЕКУЩИЙ", "ПОТОЧНИЙ")) 
                        delta = 0;
                    else 
                        return null;
                    ok = true;
                }
                else if (npt.getBeginToken().isValue("ЭТОТ", "ЦЕЙ")) 
                    ok = true;
                int cou = 0;
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (cou > 200) 
                        break;
                    com.pullenti.ner.date.DateRangeReferent dr = (com.pullenti.ner.date.DateRangeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateRangeReferent.class);
                    if (dr == null) 
                        continue;
                    if (_typ == DateItemType.QUARTAL) {
                        int ii = dr.getQuarterNumber();
                        if (ii < 1) 
                            continue;
                        ii += delta;
                        if ((ii < 1) || ii > 4) 
                            continue;
                        return _new671(t0, npt.getEndToken(), _typ, ii);
                    }
                    if (_typ == DateItemType.HALFYEAR) {
                        int ii = dr.getHalfyearNumber();
                        if (ii < 1) 
                            continue;
                        ii += delta;
                        if ((ii < 1) || ii > 2) 
                            continue;
                        return _new671(t0, npt.getEndToken(), _typ, ii);
                    }
                }
                if (ok && _typ == DateItemType.TENYEARS) 
                    return _new694(t0, npt.getEndToken(), _typ, delta, true);
            }
        }
        String term = t0.term;
        if (!Character.isLetterOrDigit(term.charAt(0))) {
            if (t0.isCharOf(".\\/:") || t0.isHiphen()) 
                return _new695(t0, t0, DateItemType.DELIM, term);
            else if (t0.isChar(',')) 
                return _new695(t0, t0, DateItemType.DELIM, term);
            else 
                return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "O") || com.pullenti.unisharp.Utils.stringsEq(term, "О")) {
            if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceAfter() && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue().length() == 1) 
                return _new671(t, t.getNext(), DateItemType.NUMBER, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue());
        }
        if (Character.isLetter(term.charAt(0))) {
            com.pullenti.ner.core.TerminToken inf = m_Monthes.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (inf != null && inf.termin.tag == null) 
                inf = m_Monthes.tryParse(inf.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (inf != null && (inf.termin.tag instanceof Integer)) 
                return _new698(inf.getBeginToken(), inf.getEndToken(), DateItemType.MONTH, (int)inf.termin.tag, inf.termin.lang);
        }
        return null;
    }

    public static com.pullenti.ner.core.TerminCollection DAYSOFWEEK;

    private static com.pullenti.ner.core.TerminCollection m_NewAge;

    private static com.pullenti.ner.core.TerminCollection m_Monthes;

    private static com.pullenti.ner.core.TerminCollection m_Seasons;

    public static void initialize() {
        if (m_NewAge != null) 
            return;
        m_NewAge = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin tt = com.pullenti.ner.core.Termin._new699("НОВАЯ ЭРА", com.pullenti.morph.MorphLang.RU, true, "НОВОЙ ЭРЫ");
        tt.addVariant("НАША ЭРА", true);
        tt.addAbridge("Н.Э.");
        m_NewAge.add(tt);
        tt = com.pullenti.ner.core.Termin._new699("НОВА ЕРА", com.pullenti.morph.MorphLang.UA, true, "НОВОЇ ЕРИ");
        tt.addVariant("НАША ЕРА", true);
        tt.addAbridge("Н.Е.");
        m_NewAge.add(tt);
        tt = new com.pullenti.ner.core.Termin("РОЖДЕСТВО ХРИСТОВО", com.pullenti.morph.MorphLang.RU, true);
        tt.addAbridge("Р.Х.");
        m_NewAge.add(tt);
        tt = new com.pullenti.ner.core.Termin("РІЗДВА ХРИСТОВОГО", com.pullenti.morph.MorphLang.UA, true);
        tt.addAbridge("Р.Х.");
        m_NewAge.add(tt);
        m_Seasons = new com.pullenti.ner.core.TerminCollection();
        m_Seasons.add(com.pullenti.ner.core.Termin._new552("ЗИМА", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.WINTER));
        m_Seasons.add(com.pullenti.ner.core.Termin._new552("WINTER", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.WINTER));
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new552("ВЕСНА", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.SPRING);
        t.addVariant("ПРОВЕСНА", true);
        m_Seasons.add(t);
        m_Seasons.add(com.pullenti.ner.core.Termin._new552("SPRING", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.SPRING));
        t = com.pullenti.ner.core.Termin._new552("ЛЕТО", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.SUMMER);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new552("ЛІТО", com.pullenti.morph.MorphLang.UA, true, com.pullenti.ner.date.DatePointerType.SUMMER);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new552("ОСЕНЬ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new552("AUTUMN", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new552("ОСІНЬ", com.pullenti.morph.MorphLang.UA, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        m_Monthes = new com.pullenti.ner.core.TerminCollection();
        String[] months = new String[] {"ЯНВАРЬ", "ФЕВРАЛЬ", "МАРТ", "АПРЕЛЬ", "МАЙ", "ИЮНЬ", "ИЮЛЬ", "АВГУСТ", "СЕНТЯБРЬ", "ОКТЯБРЬ", "НОЯБРЬ", "ДЕКАБРЬ"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new552(months[i], com.pullenti.morph.MorphLang.RU, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"СІЧЕНЬ", "ЛЮТИЙ", "БЕРЕЗЕНЬ", "КВІТЕНЬ", "ТРАВЕНЬ", "ЧЕРВЕНЬ", "ЛИПЕНЬ", "СЕРПЕНЬ", "ВЕРЕСЕНЬ", "ЖОВТЕНЬ", "ЛИСТОПАД", "ГРУДЕНЬ"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new552(months[i], com.pullenti.morph.MorphLang.UA, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new552(months[i], com.pullenti.morph.MorphLang.EN, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GUINGO", "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new552(months[i], com.pullenti.morph.MorphLang.IT, true, i + 1);
            m_Monthes.add(t);
        }
        for (String m : new String[] {"ЯНВ", "ФЕВ", "ФЕВР", "МАР", "АПР", "ИЮН", "ИЮЛ", "АВГ", "СЕН", "СЕНТ", "ОКТ", "НОЯ", "НОЯБ", "ДЕК", "JAN", "FEB", "MAR", "APR", "JUN", "JUL", "AUG", "SEP", "SEPT", "OCT", "NOV", "DEC"}) {
            for (com.pullenti.ner.core.Termin ttt : m_Monthes.termins) {
                if (ttt.terms.get(0).getCanonicalText().startsWith(m)) {
                    ttt.addAbridge(m);
                    m_Monthes.reindex(ttt);
                    break;
                }
            }
        }
        for (String m : new String[] {"OF"}) {
            m_Monthes.add(new com.pullenti.ner.core.Termin(m, com.pullenti.morph.MorphLang.EN, true));
        }
        m_EmptyWords = new java.util.HashMap<String, Object>();
        m_EmptyWords.put("IN", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("SINCE", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("THE", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("NEL", com.pullenti.morph.MorphLang.IT);
        m_EmptyWords.put("DEL", com.pullenti.morph.MorphLang.IT);
        m_EmptyWords.put("IL", com.pullenti.morph.MorphLang.IT);
        DAYSOFWEEK = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin te = com.pullenti.ner.core.Termin._new552("SUNDAY", com.pullenti.morph.MorphLang.EN, true, 7);
        te.addAbridge("SUN");
        te.addVariant("ВОСКРЕСЕНЬЕ", true);
        te.addVariant("ВОСКРЕСЕНИЕ", true);
        te.addAbridge("ВС");
        te.addVariant("НЕДІЛЯ", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("MONDAY", com.pullenti.morph.MorphLang.EN, true, 1);
        te.addAbridge("MON");
        te.addVariant("ПОНЕДЕЛЬНИК", true);
        te.addAbridge("ПОН");
        te.addVariant("ПОНЕДІЛОК", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("TUESDAY", com.pullenti.morph.MorphLang.EN, true, 2);
        te.addAbridge("TUE");
        te.addVariant("ВТОРНИК", true);
        te.addAbridge("ВТ");
        te.addVariant("ВІВТОРОК", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("WEDNESDAY", com.pullenti.morph.MorphLang.EN, true, 3);
        te.addAbridge("WED");
        te.addVariant("СРЕДА", true);
        te.addAbridge("СР");
        te.addVariant("СЕРЕДА", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("THURSDAY", com.pullenti.morph.MorphLang.EN, true, 4);
        te.addAbridge("THU");
        te.addVariant("ЧЕТВЕРГ", true);
        te.addAbridge("ЧТ");
        te.addVariant("ЧЕТВЕР", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("FRIDAY", com.pullenti.morph.MorphLang.EN, true, 5);
        te.addAbridge("FRI");
        te.addVariant("ПЯТНИЦА", true);
        te.addAbridge("ПТ");
        te.addVariant("ПЯТНИЦЯ", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new552("SATURDAY", com.pullenti.morph.MorphLang.EN, true, 6);
        te.addAbridge("SAT");
        te.addVariant("СУББОТА", true);
        te.addAbridge("СБ");
        te.addVariant("СУБОТА", true);
        DAYSOFWEEK.add(te);
    }

    private static java.util.HashMap<String, Object> m_EmptyWords;

    private static com.pullenti.ner.Token testYearRusWord(com.pullenti.ner.Token t0, boolean ignoreNewline) {
        com.pullenti.ner.Token tt = t0;
        if (tt == null) 
            return null;
        if (tt.isValue("ГОД", null) || tt.isValue("РІК", null)) 
            return tt;
        if (!ignoreNewline && tt.getPrevious() != null && tt.isNewlineBefore()) 
            return null;
        if ((tt.isValue("Г", null) && tt.getNext() != null && tt.getNext().isCharOf("\\/.")) && tt.getNext().getNext() != null && tt.getNext().getNext().isValue("Б", null)) 
            return null;
        if (((tt.getMorph().getLanguage().isRu() && ((tt.isValue("ГГ", null) || tt.isValue("Г", null))))) || ((tt.getMorph().getLanguage().isUa() && ((tt.isValue("Р", null) || tt.isValue("РР", null)))))) {
            if (tt.getNext() != null && tt.getNext().isChar('.')) {
                tt = tt.getNext();
                if ((tt.getNext() != null && (tt.getWhitespacesAfterCount() < 4) && ((((tt.getNext().isValue("Г", null) && tt.getNext().getMorph().getLanguage().isRu())) || ((tt.getNext().getMorph().getLanguage().isUa() && tt.getNext().isValue("Р", null)))))) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar('.')) 
                    tt = tt.getNext().getNext();
                return tt;
            }
            else 
                return tt;
        }
        return null;
    }

    public static java.util.ArrayList<DateItemToken> tryAttachList(com.pullenti.ner.Token t, int maxCount) {
        DateItemToken p = tryParse(t, null, false);
        if (p == null) 
            return null;
        if (p.typ == DateItemType.DELIM) 
            return null;
        java.util.ArrayList<DateItemToken> res = new java.util.ArrayList<DateItemToken>();
        res.add(p);
        com.pullenti.ner.Token tt = p.getEndToken().getNext();
        while (tt != null) {
            if (tt instanceof com.pullenti.ner.TextToken) {
                if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).checkValue(m_EmptyWords) != null) {
                    tt = tt.getNext();
                    continue;
                }
            }
            DateItemToken p0 = tryParse(tt, res, false);
            if (p0 == null) {
                if (tt.isNewlineBefore()) 
                    break;
                if (tt.chars.isLatinLetter()) 
                    break;
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (((mc.isAdjective() || mc.isPronoun())) && !mc.isVerb()) {
                    tt = tt.getNext();
                    continue;
                }
                if (tt.isValue("В", null)) {
                    p0 = tryParse(tt.getNext(), res, false);
                    if (p0 != null && p0.canBeYear()) 
                        p0.setBeginToken(tt);
                    else 
                        p0 = null;
                }
                if (p0 == null) 
                    break;
            }
            if (tt.isNewlineBefore()) {
                if (p.typ == DateItemType.MONTH && p0.canBeYear()) {
                }
                else if (p.typ == DateItemType.NUMBER && p.canBeDay() && p0.typ == DateItemType.MONTH) {
                }
                else 
                    break;
            }
            if (p0.canBeYear() && p0.typ == DateItemType.NUMBER) {
                if (p.typ == DateItemType.HALFYEAR || p.typ == DateItemType.QUARTAL) 
                    p0.typ = DateItemType.YEAR;
                else if (p.typ == DateItemType.POINTER && p0.intValue > 1990) 
                    p0.typ = DateItemType.YEAR;
            }
            p = p0;
            res.add(p);
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            tt = p.getEndToken().getNext();
        }
        for (int i = res.size() - 1; i >= 0; i--) {
            if (res.get(i).typ == DateItemType.DELIM) 
                res.remove(i);
            else 
                break;
        }
        if (res.size() > 0 && res.get(res.size() - 1).typ == DateItemType.NUMBER) {
            com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(res.get(res.size() - 1).getBeginToken());
            if (nex != null && nex.exTyp != com.pullenti.ner.core.NumberExType.HOUR) {
                if (res.size() > 3 && res.get(res.size() - 2).typ == DateItemType.DELIM && com.pullenti.unisharp.Utils.stringsEq(res.get(res.size() - 2).stringValue, ":")) {
                }
                else if (res.get(res.size() - 1).canBeYear() && nex.getEndToken() == res.get(res.size() - 1).getEndToken()) {
                }
                else 
                    res.remove(res.size() - 1);
            }
        }
        if (res.size() == 0) 
            return null;
        for (int i = 1; i < (res.size() - 1); i++) {
            if (res.get(i).typ == DateItemType.DELIM && res.get(i).getBeginToken().isComma()) {
                if ((i == 1 && res.get(i - 1).typ == DateItemType.MONTH && res.get(i + 1).canBeYear()) && (i + 1) == (res.size() - 1)) 
                    res.remove(i);
            }
        }
        if (res.get(res.size() - 1).typ == DateItemType.NUMBER) {
            DateItemToken rr = res.get(res.size() - 1);
            if (rr.canBeYear() && res.size() > 1 && res.get(res.size() - 2).typ == DateItemType.MONTH) {
            }
            else {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(rr.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getEndChar() > rr.getEndChar()) {
                    res.remove(res.size() - 1);
                    if (res.size() > 0 && res.get(res.size() - 1).typ == DateItemType.DELIM) 
                        res.remove(res.size() - 1);
                }
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() == 2 && !res.get(0).isWhitespaceAfter()) {
            if (!res.get(0).isWhitespaceBefore() && !res.get(1).isWhitespaceAfter()) 
                return null;
        }
        if (res.size() == 1 && (res.get(0).tag instanceof FirstLastTyp) && ((FirstLastTyp)res.get(0).tag) == FirstLastTyp.LAST) 
            return null;
        return res;
    }

    public static DateItemToken _new668(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, FirstLastTyp _arg4, int _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.lTyp = _arg4;
        res.intValue = _arg5;
        return res;
    }

    public static DateItemToken _new669(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, com.pullenti.ner.date.DatePointerType _arg4) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ptr = _arg4;
        return res;
    }

    public static DateItemToken _new670(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, com.pullenti.ner.MorphCollection _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static DateItemToken _new671(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        return res;
    }

    public static DateItemToken _new677(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, int _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.newAge = _arg5;
        return res;
    }

    public static DateItemToken _new679(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, com.pullenti.ner.date.DatePointerType _arg4, String _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ptr = _arg4;
        res.stringValue = _arg5;
        return res;
    }

    public static DateItemToken _new691(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, Object _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.tag = _arg5;
        return res;
    }

    public static DateItemToken _new694(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, boolean _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.relate = _arg5;
        return res;
    }

    public static DateItemToken _new695(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, String _arg4) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.stringValue = _arg4;
        return res;
    }

    public static DateItemToken _new698(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, com.pullenti.morph.MorphLang _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.lang = _arg5;
        return res;
    }

    public static class DateItemType implements Comparable<DateItemType> {
    
        public static final DateItemType NUMBER; // 0
    
        public static final DateItemType YEAR; // 1
    
        public static final DateItemType MONTH; // 2
    
        public static final DateItemType DAY; // 3
    
        public static final DateItemType DELIM; // 4
    
        public static final DateItemType HOUR; // 5
    
        public static final DateItemType MINUTE; // 6
    
        public static final DateItemType SECOND; // 7
    
        public static final DateItemType HALFYEAR; // 8
    
        public static final DateItemType QUARTAL; // 9
    
        public static final DateItemType POINTER; // 10
    
        public static final DateItemType CENTURY; // 11
    
        public static final DateItemType TENYEARS; // 12
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DateItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(DateItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DateItemType> mapIntToEnum; 
        private static java.util.HashMap<String, DateItemType> mapStringToEnum; 
        private static DateItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static DateItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DateItemType item = new DateItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DateItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static DateItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, DateItemType>();
            mapStringToEnum = new java.util.HashMap<String, DateItemType>();
            NUMBER = new DateItemType(0, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            YEAR = new DateItemType(1, "YEAR");
            mapIntToEnum.put(YEAR.value(), YEAR);
            mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
            MONTH = new DateItemType(2, "MONTH");
            mapIntToEnum.put(MONTH.value(), MONTH);
            mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
            DAY = new DateItemType(3, "DAY");
            mapIntToEnum.put(DAY.value(), DAY);
            mapStringToEnum.put(DAY.m_str.toUpperCase(), DAY);
            DELIM = new DateItemType(4, "DELIM");
            mapIntToEnum.put(DELIM.value(), DELIM);
            mapStringToEnum.put(DELIM.m_str.toUpperCase(), DELIM);
            HOUR = new DateItemType(5, "HOUR");
            mapIntToEnum.put(HOUR.value(), HOUR);
            mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
            MINUTE = new DateItemType(6, "MINUTE");
            mapIntToEnum.put(MINUTE.value(), MINUTE);
            mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
            SECOND = new DateItemType(7, "SECOND");
            mapIntToEnum.put(SECOND.value(), SECOND);
            mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
            HALFYEAR = new DateItemType(8, "HALFYEAR");
            mapIntToEnum.put(HALFYEAR.value(), HALFYEAR);
            mapStringToEnum.put(HALFYEAR.m_str.toUpperCase(), HALFYEAR);
            QUARTAL = new DateItemType(9, "QUARTAL");
            mapIntToEnum.put(QUARTAL.value(), QUARTAL);
            mapStringToEnum.put(QUARTAL.m_str.toUpperCase(), QUARTAL);
            POINTER = new DateItemType(10, "POINTER");
            mapIntToEnum.put(POINTER.value(), POINTER);
            mapStringToEnum.put(POINTER.m_str.toUpperCase(), POINTER);
            CENTURY = new DateItemType(11, "CENTURY");
            mapIntToEnum.put(CENTURY.value(), CENTURY);
            mapStringToEnum.put(CENTURY.m_str.toUpperCase(), CENTURY);
            TENYEARS = new DateItemType(12, "TENYEARS");
            mapIntToEnum.put(TENYEARS.value(), TENYEARS);
            mapStringToEnum.put(TENYEARS.m_str.toUpperCase(), TENYEARS);
            java.util.Collection<DateItemType> col = mapIntToEnum.values();
            m_Values = new DateItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static class FirstLastTyp implements Comparable<FirstLastTyp> {
    
        public static final FirstLastTyp NO; // 0
    
        public static final FirstLastTyp FIRST; // 1
    
        public static final FirstLastTyp LAST; // 2
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private FirstLastTyp(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(FirstLastTyp v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, FirstLastTyp> mapIntToEnum; 
        private static java.util.HashMap<String, FirstLastTyp> mapStringToEnum; 
        private static FirstLastTyp[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static FirstLastTyp of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            FirstLastTyp item = new FirstLastTyp(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static FirstLastTyp of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static FirstLastTyp[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, FirstLastTyp>();
            mapStringToEnum = new java.util.HashMap<String, FirstLastTyp>();
            NO = new FirstLastTyp(0, "NO");
            mapIntToEnum.put(NO.value(), NO);
            mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
            FIRST = new FirstLastTyp(1, "FIRST");
            mapIntToEnum.put(FIRST.value(), FIRST);
            mapStringToEnum.put(FIRST.m_str.toUpperCase(), FIRST);
            LAST = new FirstLastTyp(2, "LAST");
            mapIntToEnum.put(LAST.value(), LAST);
            mapStringToEnum.put(LAST.m_str.toUpperCase(), LAST);
            java.util.Collection<FirstLastTyp> col = mapIntToEnum.values();
            m_Values = new FirstLastTyp[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public DateItemToken() {
        super();
        if(_globalInstance == null) return;
        lTyp = FirstLastTyp.NO;
    }
    public static DateItemToken _globalInstance;
    
    static {
        try { _globalInstance = new DateItemToken(); } 
        catch(Exception e) { }
    }
}
