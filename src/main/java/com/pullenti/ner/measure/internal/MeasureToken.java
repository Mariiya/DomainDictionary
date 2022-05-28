/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

public class MeasureToken extends com.pullenti.ner.MetaToken {

    public MeasureToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public NumbersWithUnitToken nums;

    public String name;

    public java.util.ArrayList<MeasureToken> internals = new java.util.ArrayList<MeasureToken>();

    public MeasureToken internalEx;

    public boolean isSet;

    public boolean reliable;

    public boolean isEmpty;

    @Override
    public String toString() {
        if (name == null) 
            return nums.toString();
        return (name + ": " + nums.toString());
    }

    public String getNormValues() {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> li = this.createRefenetsTokensWithRegister(null, false);
        if (li == null || (li.size() < 1)) 
            return null;
        com.pullenti.ner.measure.MeasureReferent mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(li.get(li.size() - 1).referent, com.pullenti.ner.measure.MeasureReferent.class);
        if (mr == null) 
            return null;
        return mr.toStringEx(true, null, 0);
    }

    public java.util.ArrayList<com.pullenti.ner.ReferentToken> createRefenetsTokensWithRegister(com.pullenti.ner.core.AnalyzerData ad, boolean register) {
        if (internals.size() == 0 && !reliable) {
            if (nums.units.size() == 1 && nums.units.get(0).isDoubt) {
                if (nums.units.get(0).unknownName != null) {
                }
                else if (nums.isNewlineBefore()) {
                }
                else if (nums.units.get(0).getBeginToken().getLengthChar() > 1 && nums.units.get(0).getBeginToken().getMorphClassInDictionary().isUndefined()) {
                }
                else if (nums.fromVal == null || nums.toVal == null) 
                    return null;
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        if (((nums == null || nums.plusMinusPercent)) && internals.size() > 0) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> liEx = null;
            if (internalEx != null) {
                liEx = internalEx.createRefenetsTokensWithRegister(ad, true);
                if (liEx != null) 
                    com.pullenti.unisharp.Utils.addToArrayList(res, liEx);
            }
            com.pullenti.ner.measure.MeasureReferent mr = new com.pullenti.ner.measure.MeasureReferent();
            String templ0 = "1";
            String templ = null;
            if (name != null) 
                mr.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_NAME, name, false, 0);
            java.util.ArrayList<com.pullenti.ner.measure.MeasureReferent> ints = new java.util.ArrayList<com.pullenti.ner.measure.MeasureReferent>();
            for (int k = 0; k < internals.size(); k++) {
                MeasureToken ii = internals.get(k);
                ii.reliable = true;
                java.util.ArrayList<com.pullenti.ner.ReferentToken> li = ii.createRefenetsTokensWithRegister(ad, false);
                if (li == null) 
                    continue;
                com.pullenti.unisharp.Utils.addToArrayList(res, li);
                com.pullenti.ner.measure.MeasureReferent mr0 = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(res.get(res.size() - 1).referent, com.pullenti.ner.measure.MeasureReferent.class);
                if (liEx != null) 
                    mr0.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_REF, liEx.get(liEx.size() - 1), false, 0);
                if (k == 0 && !isEmpty) {
                    templ0 = mr0.getTemplate();
                    mr0.setTemplate("1");
                }
                if (ad != null) 
                    mr0 = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(mr0), com.pullenti.ner.measure.MeasureReferent.class);
                mr.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_VALUE, mr0, false, 0);
                ints.add(mr0);
                if (templ == null) 
                    templ = "1";
                else {
                    int nu = mr.getStringValues(com.pullenti.ner.measure.MeasureReferent.ATTR_VALUE).size();
                    templ = (templ + (isSet ? ", " : " × ") + nu);
                }
            }
            if (isSet) 
                templ = "{" + templ + "}";
            if (com.pullenti.unisharp.Utils.stringsNe(templ0, "1")) 
                templ = templ0.replace("1", templ);
            if (nums != null && nums.plusMinusPercent && nums.singleVal != null) {
                templ = ("[" + templ + " ±" + (internals.size() + 1) + "%]");
                mr.addValue(nums.singleVal);
            }
            mr.setTemplate(templ);
            int i;
            boolean hasLength = false;
            com.pullenti.ner.measure.UnitReferent uref = null;
            for (i = 0; i < ints.size(); i++) {
                if (ints.get(i).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH) {
                    hasLength = true;
                    uref = (com.pullenti.ner.measure.UnitReferent)com.pullenti.unisharp.Utils.cast(ints.get(i).getSlotValue(com.pullenti.ner.measure.MeasureReferent.ATTR_UNIT), com.pullenti.ner.measure.UnitReferent.class);
                }
                else if (ints.get(i).getUnits().size() > 0) 
                    break;
            }
            if (ints.size() > 1 && hasLength && uref != null) {
                for (com.pullenti.ner.measure.MeasureReferent ii : ints) {
                    if (ii.findSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_UNIT, null, true) == null) {
                        ii.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_UNIT, uref, false, 0);
                        ii.setKind(com.pullenti.ner.measure.MeasureKind.LENGTH);
                    }
                }
            }
            if (ints.size() == 3) {
                if (ints.get(0).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH && ints.get(1).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH && ints.get(2).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH) 
                    mr.setKind(com.pullenti.ner.measure.MeasureKind.VOLUME);
                else if (ints.get(0).getUnits().size() == 0 && ints.get(1).getUnits().size() == 0 && ints.get(2).getUnits().size() == 0) {
                    String nam = mr.getStringValue(com.pullenti.ner.measure.MeasureReferent.ATTR_NAME);
                    if (nam != null) {
                        if ((nam.indexOf("РАЗМЕР") >= 0) || (nam.indexOf("ГАБАРИТ") >= 0)) 
                            mr.setKind(com.pullenti.ner.measure.MeasureKind.VOLUME);
                    }
                }
            }
            if (ints.size() == 2) {
                if (ints.get(0).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH && ints.get(1).getKind() == com.pullenti.ner.measure.MeasureKind.LENGTH) 
                    mr.setKind(com.pullenti.ner.measure.MeasureKind.AREA);
            }
            if (!isEmpty) {
                if (ad != null) 
                    mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(mr), com.pullenti.ner.measure.MeasureReferent.class);
                res.add(new com.pullenti.ner.ReferentToken(mr, getBeginToken(), getEndToken(), null));
            }
            return res;
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> re2 = nums.createRefenetsTokensWithRegister(ad, name, register);
        for (MeasureToken ii : internals) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> li = ii.createRefenetsTokensWithRegister(ad, true);
            if (li == null) 
                continue;
            com.pullenti.unisharp.Utils.addToArrayList(res, li);
            re2.get(re2.size() - 1).referent.addSlot(com.pullenti.ner.measure.MeasureReferent.ATTR_REF, res.get(res.size() - 1).referent, false, 0);
        }
        re2.get(re2.size() - 1).setBeginToken(this.getBeginToken());
        re2.get(re2.size() - 1).setEndToken(this.getEndToken());
        com.pullenti.unisharp.Utils.addToArrayList(res, re2);
        return res;
    }

    public static MeasureToken tryParseMinimal(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean canOmitNumber) {
        if (t == null || (t instanceof com.pullenti.ner.ReferentToken)) 
            return null;
        java.util.ArrayList<NumbersWithUnitToken> mt = NumbersWithUnitToken.tryParseMulti(t, addUnits, (canOmitNumber ? NumberWithUnitParseAttr.CANOMITNUMBER : NumberWithUnitParseAttr.NO));
        if (mt == null) 
            return null;
        if (mt.get(0).units.size() == 0) 
            return null;
        if ((mt.size() == 1 && mt.get(0).units.size() == 1 && mt.get(0).units.get(0).isDoubt) && !mt.get(0).isNewlineBefore()) 
            return null;
        MeasureToken res;
        if (mt.size() == 1) {
            res = _new1766(mt.get(0).getBeginToken(), mt.get(mt.size() - 1).getEndToken(), mt.get(0));
            res._parseInternals(addUnits);
            return res;
        }
        res = new MeasureToken(mt.get(0).getBeginToken(), mt.get(mt.size() - 1).getEndToken());
        for (NumbersWithUnitToken m : mt) {
            res.internals.add(_new1766(m.getBeginToken(), m.getEndToken(), m));
        }
        return res;
    }

    private void _parseInternals(com.pullenti.ner.core.TerminCollection addUnits) {
        if (getEndToken().getNext() != null && ((this.getEndToken().getNext().isCharOf("\\/") || this.getEndToken().getNext().isValue("ПРИ", null)))) {
            MeasureToken mt1 = tryParse(this.getEndToken().getNext().getNext(), addUnits, true, false, false, false);
            if (mt1 != null) {
                internals.add(mt1);
                setEndToken(mt1.getEndToken());
            }
            else {
                NumbersWithUnitToken mt = NumbersWithUnitToken.tryParse(this.getEndToken().getNext().getNext(), addUnits, NumberWithUnitParseAttr.NO);
                if (mt != null && mt.units.size() > 0 && !UnitToken.canBeEquals(nums.units, mt.units)) {
                    internals.add(_new1766(mt.getBeginToken(), mt.getEndToken(), mt));
                    setEndToken(mt.getEndToken());
                }
            }
        }
    }

    public static MeasureToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection addUnits, boolean canBeSet, boolean canUnitsAbsent, boolean isResctriction, boolean isSubval) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (t.isTableControlChar()) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.MetaToken whd = null;
        int minmax = 0;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapminmax1779 = new com.pullenti.unisharp.Outargwrapper<Integer>(minmax);
        com.pullenti.ner.Token tt = NumbersWithUnitToken._isMinOrMax(t0, wrapminmax1779);
        minmax = (wrapminmax1779.value != null ? wrapminmax1779.value : 0);
        if (tt != null) 
            t = tt.getNext();
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREBRACKETS.value())), 0, null);
        if (npt == null) {
            whd = NumbersWithUnitToken._tryParseWHL(t);
            if (whd != null) 
                npt = new com.pullenti.ner.core.NounPhraseToken(t0, whd.getEndToken());
            else if (t0.isValue("КПД", null)) 
                npt = new com.pullenti.ner.core.NounPhraseToken(t0, t0);
            else if ((t0 instanceof com.pullenti.ner.TextToken) && t0.getLengthChar() > 3 && t0.getMorphClassInDictionary().isUndefined()) 
                npt = new com.pullenti.ner.core.NounPhraseToken(t0, t0);
            else if (t0.isValue("T", null) && t0.chars.isAllLower()) {
                npt = new com.pullenti.ner.core.NounPhraseToken(t0, t0);
                t = t0;
                if (t.getNext() != null && t.getNext().isChar('=')) 
                    npt.setEndToken(t.getNext());
            }
            else if ((t0 instanceof com.pullenti.ner.TextToken) && t0.chars.isLetter() && isSubval) {
                if (NumbersWithUnitToken.tryParse(t, addUnits, NumberWithUnitParseAttr.NO) != null) 
                    return null;
                npt = new com.pullenti.ner.core.NounPhraseToken(t0, t0);
                for (t = t0.getNext(); t != null; t = t.getNext()) {
                    if (t.getWhitespacesBeforeCount() > 2) 
                        break;
                    else if (!(t instanceof com.pullenti.ner.TextToken)) 
                        break;
                    else if (!t.chars.isLetter()) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null) 
                            npt.setEndToken((t = br.getEndToken()));
                        else 
                            break;
                    }
                    else if (NumbersWithUnitToken.tryParse(t, addUnits, NumberWithUnitParseAttr.NO) != null) 
                        break;
                    else 
                        npt.setEndToken(t);
                }
            }
            else 
                return null;
        }
        else if (com.pullenti.ner.core.NumberHelper.tryParseRealNumber(t, true, false) != null) 
            return null;
        else {
            com.pullenti.ner.date.internal.DateItemToken dtok = com.pullenti.ner.date.internal.DateItemToken.tryParse(t, null, false);
            if (dtok != null) 
                return null;
        }
        com.pullenti.ner.Token t1 = npt.getEndToken();
        t = npt.getEndToken();
        com.pullenti.ner.MetaToken _name = com.pullenti.ner.MetaToken._new527(npt.getBeginToken(), npt.getEndToken(), npt.getMorph());
        java.util.ArrayList<UnitToken> units = null;
        java.util.ArrayList<UnitToken> units2 = null;
        java.util.ArrayList<MeasureToken> _internals = new java.util.ArrayList<MeasureToken>();
        boolean not = false;
        for (tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            if (tt.isTableControlChar()) 
                break;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapminmax1771 = new com.pullenti.unisharp.Outargwrapper<Integer>(minmax);
            com.pullenti.ner.Token tt2 = NumbersWithUnitToken._isMinOrMax(tt, wrapminmax1771);
            minmax = (wrapminmax1771.value != null ? wrapminmax1771.value : 0);
            if (tt2 != null) {
                t1 = (t = (tt = tt2));
                continue;
            }
            if ((tt.isValue("БЫТЬ", null) || tt.isValue("ДОЛЖЕН", null) || tt.isValue("ДОЛЖНЫЙ", null)) || tt.isValue("МОЖЕТ", null) || ((tt.isValue("СОСТАВЛЯТЬ", null) && !tt.getMorphClassInDictionary().isAdjective()))) {
                t1 = (t = tt);
                if (tt.getPrevious().isValue("НЕ", null)) 
                    not = true;
                continue;
            }
            com.pullenti.ner.MetaToken www = NumbersWithUnitToken._tryParseWHL(tt);
            if (www != null) {
                whd = www;
                t1 = (t = (tt = www.getEndToken()));
                continue;
            }
            if (tt.isValue("ПРИ", null)) {
                MeasureToken mt1 = tryParse(tt.getNext(), addUnits, false, false, true, false);
                if (mt1 != null) {
                    _internals.add(mt1);
                    t1 = (t = (tt = mt1.getEndToken()));
                    continue;
                }
                NumbersWithUnitToken n1 = NumbersWithUnitToken.tryParse(tt.getNext(), addUnits, NumberWithUnitParseAttr.NO);
                if (n1 != null && n1.units.size() > 0) {
                    mt1 = _new1766(n1.getBeginToken(), n1.getEndToken(), n1);
                    _internals.add(mt1);
                    t1 = (t = (tt = mt1.getEndToken()));
                    continue;
                }
            }
            if (tt.isValue("ПО", null) && tt.getNext() != null && tt.getNext().isValue("U", null)) {
                t1 = (t = (tt = tt.getNext()));
                continue;
            }
            if (_internals.size() > 0) {
                if (tt.isChar(':')) 
                    break;
                MeasureToken mt1 = tryParse(tt.getNext(), addUnits, false, false, true, false);
                if (mt1 != null && mt1.reliable) {
                    _internals.add(mt1);
                    t1 = (t = (tt = mt1.getEndToken()));
                    continue;
                }
            }
            if ((tt instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                com.pullenti.ner.core.NounPhraseToken npt3 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0, null);
                if (npt3 != null) {
                    t1 = (tt = npt3.getEndToken());
                    if (_internals.size() == 0) 
                        _name.setEndToken(t1);
                    continue;
                }
            }
            if (((tt.isHiphen() && !tt.isWhitespaceBefore() && !tt.isWhitespaceAfter()) && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && (tt.getPrevious() instanceof com.pullenti.ner.TextToken)) && tt.getPrevious().chars.isAllUpper()) {
                t1 = (tt = (t = tt.getNext()));
                if (_internals.size() == 0) 
                    _name.setEndToken(t1);
                continue;
            }
            if (((tt instanceof com.pullenti.ner.NumberToken) && !tt.isWhitespaceBefore() && (tt.getPrevious() instanceof com.pullenti.ner.TextToken)) && tt.getPrevious().chars.isAllUpper()) {
                t1 = (t = tt);
                if (_internals.size() == 0) 
                    _name.setEndToken(t1);
                continue;
            }
            if ((((tt instanceof com.pullenti.ner.NumberToken) && !tt.isWhitespaceAfter() && tt.getNext().isHiphen()) && !tt.getNext().isWhitespaceAfter() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && tt.getNext().getNext().getLengthChar() > 2) {
                t1 = (t = (tt = tt.getNext().getNext()));
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 != null && npt1.getEndChar() > tt.getEndChar()) 
                    t1 = (t = (tt = npt1.getEndToken()));
                if (_internals.size() == 0) 
                    _name.setEndToken(t1);
                continue;
            }
            if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getPrevious() != null) {
                if (tt.getPrevious().isValue("USB", null)) {
                    t1 = (t = tt);
                    if (_internals.size() == 0) 
                        _name.setEndToken(t1);
                    for (com.pullenti.ner.Token ttt = tt.getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isWhitespaceBefore()) 
                            break;
                        if (ttt.isCharOf(",:")) 
                            break;
                        t1 = (t = (tt = ttt));
                        if (_internals.size() == 0) 
                            _name.setEndToken(t1);
                    }
                    continue;
                }
            }
            NumbersWithUnitToken mt0 = NumbersWithUnitToken.tryParse(tt, addUnits, NumberWithUnitParseAttr.NO);
            if (mt0 != null) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0, null);
                if (npt1 != null && npt1.getEndChar() > mt0.getEndChar()) {
                    t1 = (t = (tt = npt1.getEndToken()));
                    if (_internals.size() == 0) 
                        _name.setEndToken(t1);
                    continue;
                }
                break;
            }
            if (((tt.isComma() || tt.isChar('('))) && tt.getNext() != null) {
                www = NumbersWithUnitToken._tryParseWHL(tt.getNext());
                if (www != null) {
                    whd = www;
                    t1 = (t = (tt = www.getEndToken()));
                    if (tt.getNext() != null && tt.getNext().isComma()) 
                        t1 = (tt = tt.getNext());
                    if (tt.getNext() != null && tt.getNext().isChar(')')) {
                        t1 = (tt = tt.getNext());
                        continue;
                    }
                }
                java.util.ArrayList<UnitToken> uu = UnitToken.tryParseList(tt.getNext(), addUnits, false);
                if (uu != null) {
                    t1 = (t = uu.get(uu.size() - 1).getEndToken());
                    units = uu;
                    if (tt.isChar('(') && t1.getNext() != null && t1.getNext().isChar(')')) {
                        t1 = (t = (tt = t1.getNext()));
                        continue;
                    }
                    else if (t1.getNext() != null && t1.getNext().isChar('(')) {
                        uu = UnitToken.tryParseList(t1.getNext().getNext(), addUnits, false);
                        if (uu != null && uu.get(uu.size() - 1).getEndToken().getNext() != null && uu.get(uu.size() - 1).getEndToken().getNext().isChar(')')) {
                            units2 = uu;
                            t1 = (t = (tt = uu.get(uu.size() - 1).getEndToken().getNext()));
                            continue;
                        }
                        www = NumbersWithUnitToken._tryParseWHL(t1.getNext());
                        if (www != null) {
                            whd = www;
                            t1 = (t = (tt = www.getEndToken()));
                            continue;
                        }
                    }
                    if (uu != null && uu.size() > 0 && !uu.get(0).isDoubt) 
                        break;
                    if (t1.getNext() != null) {
                        if (t1.getNext().isTableControlChar() || t1.isNewlineAfter()) 
                            break;
                    }
                    units = null;
                }
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false) && !(tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t1 = (t = (tt = br.getEndToken()));
                    continue;
                }
            }
            if (tt.isValue("НЕ", null) && tt.getNext() != null) {
                com.pullenti.morph.MorphClass mc = tt.getNext().getMorphClassInDictionary();
                if (mc.isAdverb() || mc.isMisc()) 
                    break;
                continue;
            }
            if (tt.isValue("ЯМЗ", null)) {
            }
            com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREBRACKETS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value())), 0, null);
            if (npt2 == null) {
                if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) {
                    com.pullenti.ner.core.TerminToken to = NumbersWithUnitToken.m_Termins.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (to != null) {
                        if ((to.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && to.getEndToken().getNext().isLetters()) {
                        }
                        else 
                            break;
                    }
                    t1 = tt;
                    continue;
                }
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && tt.getLengthChar() > 1) && (((tt.chars.isAllUpper() || mc.isAdverb() || mc.isUndefined()) || mc.isAdjective()))) {
                    java.util.ArrayList<UnitToken> uu = UnitToken.tryParseList(tt, addUnits, false);
                    if (uu != null) {
                        if (uu.get(0).getLengthChar() > 1 || uu.size() > 1) {
                            units = uu;
                            t1 = (t = uu.get(uu.size() - 1).getEndToken());
                            break;
                        }
                    }
                    t1 = (t = tt);
                    if (_internals.size() == 0) 
                        _name.setEndToken(tt);
                    continue;
                }
                if (tt.isComma()) 
                    continue;
                if (tt.isChar('.')) {
                    if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext())) 
                        continue;
                    java.util.ArrayList<UnitToken> uu = UnitToken.tryParseList(tt.getNext(), addUnits, false);
                    if (uu != null) {
                        if (uu.get(0).getLengthChar() > 2 || uu.size() > 1) {
                            units = uu;
                            t1 = (t = uu.get(uu.size() - 1).getEndToken());
                            break;
                        }
                    }
                }
                break;
            }
            t1 = (t = (tt = npt2.getEndToken()));
            if (_internals.size() > 0) {
            }
            else if (t.isValue("ПРЕДЕЛ", null) || t.isValue("ГРАНИЦА", null) || t.isValue("ДИАПАЗОН", null)) {
            }
            else if (t.chars.isLetter()) 
                _name.setEndToken(t1);
        }
        com.pullenti.ner.Token t11 = t1;
        for (t1 = t1.getNext(); t1 != null; t1 = t1.getNext()) {
            if (t1.isNewlineBefore() && t1.isTableControlChar()) 
                return null;
            else if (t1.isTableControlChar()) {
            }
            else if (t1.isCharOf(":,_")) {
                if (isResctriction) 
                    return null;
                com.pullenti.ner.MetaToken www = NumbersWithUnitToken._tryParseWHL(t1.getNext());
                if (www != null) {
                    whd = www;
                    t1 = (t = www.getEndToken());
                    continue;
                }
                java.util.ArrayList<UnitToken> uu = UnitToken.tryParseList(t1.getNext(), addUnits, false);
                if (uu != null) {
                    if (uu.get(0).getLengthChar() > 1 || uu.size() > 1) {
                        units = uu;
                        t1 = (t = uu.get(uu.size() - 1).getEndToken());
                        continue;
                    }
                }
                if (t1.isChar(':')) {
                    java.util.ArrayList<MeasureToken> li = new java.util.ArrayList<MeasureToken>();
                    for (com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isHiphen() || ttt.isTableControlChar()) 
                            continue;
                        if ((ttt instanceof com.pullenti.ner.TextToken) && !ttt.chars.isLetter()) 
                            continue;
                        MeasureToken mt1 = tryParse(ttt, addUnits, true, true, false, true);
                        if (mt1 == null) 
                            break;
                        li.add(mt1);
                        ttt = mt1.getEndToken();
                        if (ttt.getNext() != null && ttt.getNext().isChar(';')) 
                            ttt = ttt.getNext();
                        if (ttt.isChar(';')) {
                        }
                        else if (ttt.isNewlineAfter() && mt1.isNewlineBefore()) {
                        }
                        else 
                            break;
                    }
                    if (li.size() > 1) {
                        MeasureToken res0 = _new1772(t0, li.get(li.size() - 1).getEndToken(), li, true);
                        if (_internals != null && _internals.size() > 0) 
                            res0.internalEx = _internals.get(0);
                        String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(_name, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                        li.get(0).setBeginToken(t0);
                        for (MeasureToken v : li) {
                            v.name = (nam + " (" + ((v.name != null ? v.name : "")) + ")").trim();
                            if (v.nums != null && v.nums.units.size() == 0 && units != null) 
                                v.nums.units = units;
                        }
                        return res0;
                    }
                }
            }
            else if (t1.isHiphen() && t1.isWhitespaceAfter() && t1.isWhitespaceBefore()) {
            }
            else if (t1.isHiphen() && t1.getNext() != null && t1.getNext().isChar('(')) {
            }
            else 
                break;
        }
        if (t1 == null) 
            return null;
        NumberWithUnitParseAttr attrs = NumberWithUnitParseAttr.CANBENON;
        if (not) 
            attrs = NumberWithUnitParseAttr.of((attrs.value()) | (NumberWithUnitParseAttr.NOT.value()));
        if (isResctriction) 
            attrs = NumberWithUnitParseAttr.of((attrs.value()) | (NumberWithUnitParseAttr.ISSECOND.value()));
        java.util.ArrayList<NumbersWithUnitToken> mts = NumbersWithUnitToken.tryParseMulti(t1, addUnits, attrs);
        if (mts == null) {
            if (units != null && units.size() > 0) {
                if (t1 == null || t1.getPrevious().isChar(':')) {
                    mts = new java.util.ArrayList<NumbersWithUnitToken>();
                    if (t1 == null) {
                        for (t1 = t11; t1 != null && t1.getNext() != null; t1 = t1.getNext()) {
                        }
                    }
                    else 
                        t1 = t1.getPrevious();
                    mts.add(NumbersWithUnitToken._new1773(t0, t1, Double.NaN));
                }
            }
            if (mts == null) 
                return null;
        }
        NumbersWithUnitToken mt = mts.get(0);
        if (mt.getBeginToken() == mt.getEndToken() && !(mt.getBeginToken() instanceof com.pullenti.ner.NumberToken)) 
            return null;
        if (!isSubval && _name.getBeginToken().getMorph()._getClass().isPreposition()) 
            _name.setBeginToken(_name.getBeginToken().getNext());
        if (mt.wHL != null) 
            whd = mt.wHL;
        for (int kk = 0; kk < 10; kk++) {
            if (whd != null && whd.getEndToken() == _name.getEndToken()) {
                _name.setEndToken(whd.getBeginToken().getPrevious());
                continue;
            }
            if (units != null) {
                if (units.get(units.size() - 1).getEndToken() == _name.getEndToken()) {
                    _name.setEndToken(units.get(0).getBeginToken().getPrevious());
                    continue;
                }
            }
            break;
        }
        if (mts.size() > 1 && _internals.size() == 0) {
            if (mt.units.size() == 0) {
                if (units != null) {
                    for (NumbersWithUnitToken m : mts) {
                        m.units = units;
                    }
                }
            }
            MeasureToken res1 = _new1774(t0, mts.get(mts.size() - 1).getEndToken(), _name.getMorph(), true);
            res1.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(_name, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            for (int k = 0; k < mts.size(); k++) {
                MeasureToken ttt = _new1766(mts.get(k).getBeginToken(), mts.get(k).getEndToken(), mts.get(k));
                if (whd != null) {
                    java.util.ArrayList<String> nams = (java.util.ArrayList<String>)com.pullenti.unisharp.Utils.cast(whd.tag, java.util.ArrayList.class);
                    if (k < nams.size()) 
                        ttt.name = nams.get(k);
                }
                res1.internals.add(ttt);
            }
            com.pullenti.ner.Token tt1 = res1.getEndToken().getNext();
            if (tt1 != null && tt1.isChar('±')) {
                NumbersWithUnitToken nn = NumbersWithUnitToken._tryParse(tt1, addUnits, NumberWithUnitParseAttr.ISSECOND);
                if (nn != null && nn.plusMinusPercent) {
                    res1.setEndToken(nn.getEndToken());
                    res1.nums = nn;
                    if (nn.units.size() > 0 && units == null && mt.units.size() == 0) {
                        for (NumbersWithUnitToken m : mts) {
                            m.units = nn.units;
                        }
                    }
                }
            }
            return res1;
        }
        if (!mt.isWhitespaceBefore()) {
            if (mt.getBeginToken().getPrevious() == null) 
                return null;
            if (mt.getBeginToken().getPrevious().isCharOf(":),") || mt.getBeginToken().getPrevious().isTableControlChar() || mt.getBeginToken().getPrevious().isValue("IP", null)) {
            }
            else if (mt.getBeginToken().isHiphen() && mt.units.size() > 0 && !mt.units.get(0).isDoubt) {
            }
            else 
                return null;
        }
        if (mt.units.size() == 0 && units != null) {
            mt.units = units;
            if (mt.divNum != null && units.size() > 1 && mt.divNum.units.size() == 0) {
                for (int i = 1; i < units.size(); i++) {
                    if (units.get(i).pow == -1) {
                        for (int j = i; j < units.size(); j++) {
                            mt.divNum.units.add(units.get(j));
                            units.get(j).pow = -units.get(j).pow;
                        }
                        for(int jjj = i + units.size() - i - 1, mmm = i; jjj >= mmm; jjj--) mt.units.remove(jjj);
                        break;
                    }
                }
            }
        }
        if ((minmax < 0) && mt.singleVal != null) {
            mt.fromVal = mt.singleVal;
            mt.fromInclude = true;
            mt.singleVal = null;
        }
        if (minmax > 0 && mt.singleVal != null) {
            mt.toVal = mt.singleVal;
            mt.toInclude = true;
            mt.singleVal = null;
        }
        if (mt.units.size() == 0) {
            units = UnitToken.tryParseList(mt.getEndToken().getNext(), addUnits, true);
            if (units == null) {
                if (canUnitsAbsent) {
                }
                else 
                    return null;
            }
            else 
                mt.units = units;
        }
        MeasureToken res = _new1776(t0, mt.getEndToken(), _name.getMorph(), _internals);
        if (((!t0.isWhitespaceBefore() && t0.getPrevious() != null && t0 == _name.getBeginToken()) && t0.getPrevious().isHiphen() && !t0.getPrevious().isWhitespaceBefore()) && (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken)) 
            _name.setBeginToken(res.setBeginToken(_name.getBeginToken().getPrevious().getPrevious()));
        res.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(_name, (!isSubval ? com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE : com.pullenti.ner.core.GetTextAttr.NO));
        res.nums = mt;
        for (UnitToken u : res.nums.units) {
            if (u.keyword != null) {
                if (u.keyword.getBeginChar() >= res.getBeginChar()) 
                    res.reliable = true;
            }
        }
        res._parseInternals(addUnits);
        if (res.internals.size() > 0 || !canBeSet) 
            return res;
        t1 = res.getEndToken().getNext();
        if (t1 != null && t1.isCommaAnd()) 
            t1 = t1.getNext();
        java.util.ArrayList<NumbersWithUnitToken> mts1 = NumbersWithUnitToken.tryParseMulti(t1, addUnits, NumberWithUnitParseAttr.NO);
        if ((mts1 != null && mts1.size() == 1 && (t1.getWhitespacesBeforeCount() < 3)) && mts1.get(0).units.size() > 0 && !UnitToken.canBeEquals(mts.get(0).units, mts1.get(0).units)) {
            res.isSet = true;
            res.nums = null;
            res.internals.add(_new1766(mt.getBeginToken(), mt.getEndToken(), mt));
            res.internals.add(_new1766(mts1.get(0).getBeginToken(), mts1.get(0).getEndToken(), mts1.get(0)));
            res.setEndToken(mts1.get(0).getEndToken());
        }
        return res;
    }

    public static MeasureToken _new1766(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, NumbersWithUnitToken _arg3) {
        MeasureToken res = new MeasureToken(_arg1, _arg2);
        res.nums = _arg3;
        return res;
    }

    public static MeasureToken _new1772(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, java.util.ArrayList<MeasureToken> _arg3, boolean _arg4) {
        MeasureToken res = new MeasureToken(_arg1, _arg2);
        res.internals = _arg3;
        res.isEmpty = _arg4;
        return res;
    }

    public static MeasureToken _new1774(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, boolean _arg4) {
        MeasureToken res = new MeasureToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.reliable = _arg4;
        return res;
    }

    public static MeasureToken _new1776(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, java.util.ArrayList<MeasureToken> _arg4) {
        MeasureToken res = new MeasureToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.internals = _arg4;
        return res;
    }
    public MeasureToken() {
        super();
    }
}
