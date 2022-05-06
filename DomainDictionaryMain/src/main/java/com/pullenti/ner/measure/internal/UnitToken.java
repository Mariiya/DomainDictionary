/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

public class UnitToken extends com.pullenti.ner.MetaToken {

    public UnitToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public Unit unit;

    public int pow = 1;

    public boolean isDoubt = false;

    public com.pullenti.ner.Token keyword;

    public com.pullenti.ner.measure.UnitReferent extOnto;

    public String unknownName;

    @Override
    public String toString() {
        String res = (unknownName != null ? unknownName : ((extOnto == null ? unit.toString() : extOnto.toString())));
        if (pow != 1) 
            res = (res + "<" + pow + ">");
        if (isDoubt) 
            res += "?";
        if (keyword != null) 
            res = (res + " (<-" + keyword.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false) + ")");
        return res;
    }

    public static boolean canBeEquals(java.util.ArrayList<UnitToken> ut1, java.util.ArrayList<UnitToken> ut2) {
        if (ut1.size() != ut2.size()) 
            return false;
        for (int i = 0; i < ut1.size(); i++) {
            if (ut1.get(i).unit != ut2.get(i).unit || ut1.get(i).extOnto != ut2.get(i).extOnto) 
                return false;
            if (ut1.get(i).pow != ut2.get(i).pow) 
                return false;
        }
        return true;
    }

    public static com.pullenti.ner.measure.MeasureKind calcKind(java.util.ArrayList<UnitToken> units) {
        if (units == null || units.size() == 0) 
            return com.pullenti.ner.measure.MeasureKind.UNDEFINED;
        UnitToken u0 = units.get(0);
        if (u0.unit == null) 
            return com.pullenti.ner.measure.MeasureKind.UNDEFINED;
        if (units.size() == 1) {
            if (u0.pow == 1) 
                return u0.unit.kind;
            if (u0.pow == 2) {
                if (u0.unit.kind == com.pullenti.ner.measure.MeasureKind.LENGTH) 
                    return com.pullenti.ner.measure.MeasureKind.AREA;
            }
            if (u0.pow == 3) {
                if (u0.unit.kind == com.pullenti.ner.measure.MeasureKind.LENGTH) 
                    return com.pullenti.ner.measure.MeasureKind.VOLUME;
            }
            return com.pullenti.ner.measure.MeasureKind.UNDEFINED;
        }
        if (units.size() == 2) {
            if (units.get(1).unit == null) 
                return com.pullenti.ner.measure.MeasureKind.UNDEFINED;
            if ((u0.unit.kind == com.pullenti.ner.measure.MeasureKind.LENGTH && u0.pow == 1 && units.get(1).unit.kind == com.pullenti.ner.measure.MeasureKind.TIME) && units.get(1).pow == -1) 
                return com.pullenti.ner.measure.MeasureKind.SPEED;
        }
        return com.pullenti.ner.measure.MeasureKind.UNDEFINED;
    }

    private static com.pullenti.ner.measure.UnitReferent _createReferent(Unit u) {
        com.pullenti.ner.measure.UnitReferent ur = new com.pullenti.ner.measure.UnitReferent();
        ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, u.nameCyr, false, 0);
        ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, u.nameLat, false, 0);
        ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_FULLNAME, u.fullnameCyr, false, 0);
        ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_FULLNAME, u.fullnameLat, false, 0);
        ur.tag = u;
        ur.m_Unit = u;
        return ur;
    }

    public com.pullenti.ner.measure.UnitReferent createReferentWithRegister(com.pullenti.ner.core.AnalyzerData ad) {
        com.pullenti.ner.measure.UnitReferent ur = extOnto;
        if (unit != null) 
            ur = _createReferent(unit);
        else if (unknownName != null) {
            ur = new com.pullenti.ner.measure.UnitReferent();
            ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, unknownName, false, 0);
            ur.setUnknown(true);
        }
        if (pow != 1) 
            ur.addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_POW, ((Integer)pow).toString(), false, 0);
        java.util.ArrayList<com.pullenti.ner.measure.UnitReferent> owns = new java.util.ArrayList<com.pullenti.ner.measure.UnitReferent>();
        owns.add(ur);
        if (unit != null) {
            for (Unit uu = unit.baseUnit; uu != null; uu = uu.baseUnit) {
                com.pullenti.ner.measure.UnitReferent ur0 = _createReferent(uu);
                owns.add(ur0);
            }
        }
        for (int i = owns.size() - 1; i >= 0; i--) {
            if (ad != null) 
                com.pullenti.unisharp.Utils.putArrayValue(owns, i, (com.pullenti.ner.measure.UnitReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(owns.get(i)), com.pullenti.ner.measure.UnitReferent.class));
            if (i > 0) {
                owns.get(i - 1).addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_BASEUNIT, owns.get(i), false, 0);
                if (((Unit)com.pullenti.unisharp.Utils.cast(owns.get(i - 1).tag, Unit.class)).baseMultiplier != 0) 
                    owns.get(i - 1).addSlot(com.pullenti.ner.measure.UnitReferent.ATTR_BASEFACTOR, com.pullenti.ner.core.NumberHelper.doubleToString(((Unit)com.pullenti.unisharp.Utils.cast(owns.get(i - 1).tag, Unit.class)).baseMultiplier), false, 0);
            }
        }
        return owns.get(0);
    }

    public static java.util.ArrayList<UnitToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean parseUnknownUnits) {
        UnitToken ut = tryParse(t, addUnits, null, parseUnknownUnits);
        if (ut == null) 
            return null;
        java.util.ArrayList<UnitToken> res = new java.util.ArrayList<UnitToken>();
        res.add(ut);
        for (com.pullenti.ner.Token tt = ut.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (tt.getWhitespacesBeforeCount() > 2) 
                break;
            ut = tryParse(tt, addUnits, res.get(res.size() - 1), true);
            if (ut == null) {
                if (tt.isCharOf("\\/") && tt.getNext() != null && tt.getNext().isChar('(')) {
                    java.util.ArrayList<UnitToken> li = tryParseList(tt.getNext().getNext(), addUnits, parseUnknownUnits);
                    if (li != null && li.get(li.size() - 1).getEndToken().getNext() != null && li.get(li.size() - 1).getEndToken().getNext().isChar(')')) {
                        for (UnitToken v : li) {
                            v.pow = -v.pow;
                            res.add(v);
                        }
                        li.get(li.size() - 1).setEndToken(li.get(li.size() - 1).getEndToken().getNext());
                    }
                }
                break;
            }
            if (ut.unit != null && ut.unit.kind != com.pullenti.ner.measure.MeasureKind.UNDEFINED) {
                if (res.get(res.size() - 1).unit != null && res.get(res.size() - 1).unit.kind == ut.unit.kind) 
                    break;
            }
            res.add(ut);
            tt = ut.getEndToken();
            if (res.size() > 2) 
                break;
        }
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).unit != null && res.get(i).unit.baseUnit != null && res.get(i).unit.multUnit != null) {
                UnitToken ut2 = new UnitToken(res.get(i).getBeginToken(), res.get(i).getEndToken());
                ut2.unit = res.get(i).unit.multUnit;
                res.add(i + 1, ut2);
                res.get(i).unit = res.get(i).unit.baseUnit;
            }
        }
        for (int i = res.size() - 1; i > 0; i--) {
            UnitToken r = res.get(i);
            if ((r.isDoubt && r.getBeginToken() == r.getEndToken() && r.unit == null) && !r.getBeginToken().getMorphClassInDictionary().isUndefined()) 
                res.remove(i);
            else 
                break;
        }
        if (res.size() > 1) {
            for (UnitToken r : res) {
                r.isDoubt = false;
            }
        }
        return res;
    }

    public static UnitToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, UnitToken prev, boolean parseUnknownUnits) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        int _pow = 1;
        boolean isNeg = false;
        if ((t.isCharOf("\\/") || t.isValue("НА", null) || t.isValue("OF", null)) || t.isValue("PER", null)) {
            if (prev == null) 
                return null;
            isNeg = true;
            t = t.getNext();
        }
        else if (t.isValue("В", null) && prev != null) {
            isNeg = true;
            t = t.getNext();
        }
        else if (MeasureHelper.isMultChar(t)) 
            t = t.getNext();
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "КВ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "КВАДР") || tt.isValue("КВАДРАТНЫЙ", null)) {
            _pow = 2;
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
            if (tt != null && tt.isChar('.')) 
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
            if (tt == null) 
                return null;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "КУБ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "КУБИЧ") || tt.isValue("КУБИЧЕСКИЙ", null)) {
            _pow = 3;
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
            if (tt != null && tt.isChar('.')) 
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class);
            if (tt == null) 
                return null;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "µ")) {
            UnitToken res = tryParse(tt.getNext(), addUnits, prev, false);
            if (res != null) {
                for (Unit u : UnitsHelper.UNITS) {
                    if (u.factor == UnitsFactors.MICRO && com.pullenti.unisharp.Utils.stringsCompare("мк" + u.nameCyr, res.unit.nameCyr, true) == 0) {
                        res.unit = u;
                        res.setBeginToken(tt);
                        res.pow = _pow;
                        if (isNeg) 
                            res.pow = -_pow;
                        return res;
                    }
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks = UnitsHelper.TERMINS.tryParseAll(tt, com.pullenti.ner.core.TerminParseAttr.NO);
        if (toks != null) {
            if ((prev != null && tt == t0 && toks.size() == 1) && t.isWhitespaceBefore()) 
                return null;
            if (toks.get(0).getBeginToken() == toks.get(0).getEndToken() && tt.getMorph()._getClass().isPreposition() && (tt.getWhitespacesAfterCount() < 3)) {
                if (com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null) != null) 
                    return null;
                if (tt.getNext() instanceof com.pullenti.ner.NumberToken) {
                    if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
                        return null;
                }
                UnitToken nex = tryParse(tt.getNext(), addUnits, null, false);
                if (nex != null) 
                    return null;
            }
            if (toks.get(0).getBeginToken().getLengthChar() == 1 && toks.get(0).getBeginToken().chars.isAllLower()) {
                if (toks.get(0).getBeginToken() == toks.get(0).getEndToken() || ((toks.get(0).getEndToken().isChar('.') && toks.get(0).getBeginToken().getNext() == toks.get(0).getEndToken()))) {
                    if (toks.get(0).getBeginToken().isValue("М", null) || toks.get(0).getBeginToken().isValue("M", null)) {
                        if (prev != null && prev.unit != null && prev.unit.kind == com.pullenti.ner.measure.MeasureKind.LENGTH) {
                            UnitToken res = _new1780(t0, toks.get(0).getEndToken(), UnitsHelper.UMINUTE);
                            res.pow = _pow;
                            if (isNeg) 
                                res.pow = -_pow;
                            return res;
                        }
                    }
                    if (toks.get(0).getBeginToken().isValue("S", null) || toks.get(0).getBeginToken().isValue("С", null)) {
                        if (prev != null && prev.unit != null && ((prev.unit.kind == com.pullenti.ner.measure.MeasureKind.LENGTH || isNeg))) {
                            UnitToken res = _new1780(t0, toks.get(0).getEndToken(), UnitsHelper.USEC);
                            res.pow = _pow;
                            if (isNeg) 
                                res.pow = -_pow;
                            return res;
                        }
                    }
                }
            }
            java.util.ArrayList<UnitToken> uts = new java.util.ArrayList<UnitToken>();
            for (com.pullenti.ner.core.TerminToken tok : toks) {
                UnitToken res = _new1780(t0, tok.getEndToken(), (Unit)com.pullenti.unisharp.Utils.cast(tok.termin.tag, Unit.class));
                res.pow = _pow;
                if (isNeg) 
                    res.pow = -_pow;
                if (res.unit.baseMultiplier == 1000000 && (t0 instanceof com.pullenti.ner.TextToken) && Character.isLowerCase(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).getSourceText().charAt(0))) {
                    for (Unit u : UnitsHelper.UNITS) {
                        if (u.factor == UnitsFactors.MILLI && com.pullenti.unisharp.Utils.stringsCompare(u.nameCyr, res.unit.nameCyr, true) == 0) {
                            res.unit = u;
                            break;
                        }
                    }
                }
                res._correct();
                res._checkDoubt();
                uts.add(res);
            }
            int max = 0;
            UnitToken best = null;
            for (UnitToken ut : uts) {
                if (ut.keyword != null) {
                    if (ut.keyword.getBeginChar() >= max) {
                        max = ut.keyword.getBeginChar();
                        best = ut;
                    }
                }
            }
            if (best != null) 
                return best;
            for (UnitToken ut : uts) {
                if (!ut.isDoubt) 
                    return ut;
            }
            return uts.get(0);
        }
        com.pullenti.ner.Token t1 = null;
        if (t.isCharOf("º°")) 
            t1 = t;
        else if ((t.isChar('<') && t.getNext() != null && t.getNext().getNext() != null) && t.getNext().getNext().isChar('>') && ((t.getNext().isValue("О", null) || t.getNext().isValue("O", null) || (((t.getNext() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue(), "0")))))) 
            t1 = t.getNext().getNext();
        if (t1 != null) {
            UnitToken res = _new1780(t0, t1, UnitsHelper.UGRADUS);
            res._checkDoubt();
            t = t1.getNext();
            if (t != null && t.isComma()) 
                t = t.getNext();
            if (t != null && t.isValue("ПО", null)) 
                t = t.getNext();
            if (t instanceof com.pullenti.ner.TextToken) {
                String vv = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(vv, "C") || com.pullenti.unisharp.Utils.stringsEq(vv, "С") || vv.startsWith("ЦЕЛЬС")) {
                    res.unit = UnitsHelper.UGRADUSC;
                    res.isDoubt = false;
                    res.setEndToken(t);
                }
                if (com.pullenti.unisharp.Utils.stringsEq(vv, "F") || vv.startsWith("ФАР")) {
                    res.unit = UnitsHelper.UGRADUSF;
                    res.isDoubt = false;
                    res.setEndToken(t);
                }
            }
            return res;
        }
        if ((t instanceof com.pullenti.ner.TextToken) && ((t.isValue("ОС", null) || t.isValue("OC", null)))) {
            String str = t.getSourceText();
            if (com.pullenti.unisharp.Utils.stringsEq(str, "оС") || com.pullenti.unisharp.Utils.stringsEq(str, "oC")) {
                UnitToken res = _new1900(t, t, UnitsHelper.UGRADUSC, false);
                return res;
            }
        }
        if (t.isChar('%')) {
            com.pullenti.ner.Token tt1 = t.getNext();
            if (tt1 != null && tt1.isChar('(')) 
                tt1 = tt1.getNext();
            if ((tt1 instanceof com.pullenti.ner.TextToken) && ((tt1.isValue("ОБ", null) || tt1.isValue("ОБОРОТ", null)))) {
                UnitToken re = _new1780(t, tt1, UnitsHelper.UALCO);
                if (re.getEndToken().getNext() != null && re.getEndToken().getNext().isChar('.')) 
                    re.setEndToken(re.getEndToken().getNext());
                if (re.getEndToken().getNext() != null && re.getEndToken().getNext().isChar(')') && t.getNext().isChar('(')) 
                    re.setEndToken(re.getEndToken().getNext());
                return re;
            }
            return _new1780(t, t, UnitsHelper.UPERCENT);
        }
        if (addUnits != null) {
            com.pullenti.ner.core.TerminToken tok = addUnits.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                UnitToken res = _new1903(t0, tok.getEndToken(), (com.pullenti.ner.measure.UnitReferent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.measure.UnitReferent.class));
                if (tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isChar('.')) 
                    tok.setEndToken(tok.getEndToken().getNext());
                res.pow = _pow;
                if (isNeg) 
                    res.pow = -_pow;
                res._correct();
                return res;
            }
        }
        if (!parseUnknownUnits) 
            return null;
        if ((t.getWhitespacesBeforeCount() > 2 || !t.chars.isLetter() || t.getLengthChar() > 5) || !(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
            return null;
        t1 = t;
        if (t.getNext() != null && t.getNext().isChar('.')) 
            t1 = t;
        boolean ok = false;
        if (t1.getNext() == null || t1.getWhitespacesAfterCount() > 2) 
            ok = true;
        else if (t1.getNext().isComma() || t1.getNext().isCharOf("\\/") || t1.getNext().isTableControlChar()) 
            ok = true;
        else if (MeasureHelper.isMultChar(t1.getNext())) 
            ok = true;
        if (!ok) 
            return null;
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        if (mc.isUndefined()) {
        }
        else if (t.getLengthChar() > 7) 
            return null;
        UnitToken res1 = _new1904(t0, t1, _pow, true);
        res1.unknownName = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getSourceText();
        if (prev != null && com.pullenti.unisharp.Utils.stringsEq(res1.unknownName, "d")) 
            res1.unit = UnitsHelper.UDAY;
        res1._correct();
        return res1;
    }

    private void _correct() {
        com.pullenti.ner.Token t = getEndToken().getNext();
        if (t == null) 
            return;
        int num = 0;
        boolean neg = pow < 0;
        if (t.isChar('³')) 
            num = 3;
        else if (t.isChar('²')) 
            num = 2;
        else if (!t.isWhitespaceBefore() && (t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "3") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "2")))) 
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
        else if ((t.isChar('<') && (t.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) && t.getNext().getNext() != null && t.getNext().getNext().isChar('>')) {
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
            t = t.getNext().getNext();
        }
        else if (((t.isChar('<') && t.getNext() != null && t.getNext().isHiphen()) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) && t.getNext().getNext().getNext() != null && t.getNext().getNext().getNext().isChar('>')) {
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
            neg = true;
            t = t.getNext().getNext().getNext();
        }
        else {
            if (t.isValue("B", null) && t.getNext() != null) 
                t = t.getNext();
            if ((t.isValue("КВ", null) || t.isValue("КВАДР", null) || t.isValue("КВАДРАТНЫЙ", null)) || t.isValue("КВАДРАТ", null)) {
                num = 2;
                if (t.getNext() != null && t.getNext().isChar('.')) 
                    t = t.getNext();
            }
            else if (t.isValue("КУБ", null) || t.isValue("КУБИЧ", null) || t.isValue("КУБИЧЕСКИЙ", null)) {
                num = 3;
                if (t.getNext() != null && t.getNext().isChar('.')) 
                    t = t.getNext();
            }
        }
        if (num != 0) {
            pow = num;
            if (neg) 
                pow = -num;
            setEndToken(t);
        }
        t = this.getEndToken().getNext();
        if ((t != null && t.isValue("ПО", null) && t.getNext() != null) && t.getNext().isValue("U", null)) 
            setEndToken(t.getNext());
    }

    private void _checkDoubt() {
        isDoubt = false;
        if (pow != 1) 
            return;
        if ((getBeginToken().getLengthChar() < 3) || getBeginToken().isValue("ARE", null)) {
            isDoubt = true;
            if ((getBeginToken().chars.isCapitalUpper() || getBeginToken().chars.isAllUpper() || getBeginToken().chars.isLastLower()) || getBeginToken().chars.isAllLower()) {
            }
            else if (unit.psevdo.size() > 0) {
            }
            else 
                isDoubt = false;
        }
        int cou = 0;
        for (com.pullenti.ner.Token t = getBeginToken().getPrevious(); t != null && (cou < 30); t = t.getPrevious(),cou++) {
            com.pullenti.ner.measure.MeasureReferent mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.measure.MeasureReferent.class);
            if (mr != null) {
                for (com.pullenti.ner.Slot s : mr.getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.measure.UnitReferent) {
                        com.pullenti.ner.measure.UnitReferent ur = (com.pullenti.ner.measure.UnitReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.measure.UnitReferent.class);
                        for (Unit u = unit; u != null; u = u.baseUnit) {
                            if (ur.findSlot(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, u.nameCyr, true) != null) 
                                isDoubt = false;
                            else if (unit.psevdo.size() > 0) {
                                for (Unit uu : unit.psevdo) {
                                    if (ur.findSlot(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, uu.nameCyr, true) != null) {
                                        unit = uu;
                                        isDoubt = false;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!(t instanceof com.pullenti.ner.TextToken) || (t.getLengthChar() < 3)) 
                continue;
            for (Unit u = unit; u != null; u = u.baseUnit) {
                for (String k : u.keywords) {
                    if (t.isValue(k, null)) {
                        keyword = t;
                        isDoubt = false;
                        return;
                    }
                }
                for (Unit uu : u.psevdo) {
                    for (String k : uu.keywords) {
                        if (t.isValue(k, null)) {
                            unit = uu;
                            keyword = t;
                            isDoubt = false;
                            return;
                        }
                    }
                }
            }
        }
    }

    public static String outUnits(java.util.ArrayList<UnitToken> units) {
        if (units == null || units.size() == 0) 
            return null;
        StringBuilder res = new StringBuilder();
        res.append(units.get(0).unit.nameCyr);
        if (units.get(0).pow != 1) 
            res.append("<").append(units.get(0).pow).append(">");
        for (int i = 1; i < units.size(); i++) {
            String mnem = units.get(i).unit.nameCyr;
            int _pow = units.get(i).pow;
            if (_pow < 0) {
                res.append("/").append(mnem);
                if (_pow != -1) 
                    res.append("<").append((-_pow)).append(">");
            }
            else {
                res.append("*").append(mnem);
                if (_pow > 1) 
                    res.append("<").append(_pow).append(">");
            }
        }
        return res.toString();
    }

    public static UnitToken _new1780(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Unit _arg3) {
        UnitToken res = new UnitToken(_arg1, _arg2);
        res.unit = _arg3;
        return res;
    }

    public static UnitToken _new1900(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Unit _arg3, boolean _arg4) {
        UnitToken res = new UnitToken(_arg1, _arg2);
        res.unit = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static UnitToken _new1903(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.measure.UnitReferent _arg3) {
        UnitToken res = new UnitToken(_arg1, _arg2);
        res.extOnto = _arg3;
        return res;
    }

    public static UnitToken _new1904(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, int _arg3, boolean _arg4) {
        UnitToken res = new UnitToken(_arg1, _arg2);
        res.pow = _arg3;
        res.isDoubt = _arg4;
        return res;
    }
    public UnitToken() {
        super();
    }
}
