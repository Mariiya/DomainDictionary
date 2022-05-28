/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class DecreeChangeToken extends com.pullenti.ner.MetaToken {

    public DecreeChangeToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public DecreeChangeTokenTyp typ = DecreeChangeTokenTyp.UNDEFINED;

    public com.pullenti.ner.decree.DecreeReferent decree;

    public DecreeToken decreeTok;

    public java.util.ArrayList<PartToken> parts;

    public java.util.ArrayList<PartToken> newParts;

    public com.pullenti.ner.decree.DecreePartReferent realPart;

    public com.pullenti.ner.decree.DecreeChangeValueReferent changeVal;

    public boolean hasName;

    public boolean hasText;

    public com.pullenti.ner.decree.DecreeChangeKind actKind = com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED;

    public PartToken.ItemType partTyp = PartToken.ItemType.UNDEFINED;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(typ.toString());
        if (actKind != com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) 
            tmp.append(" Kind=").append(actKind.toString());
        if (hasName) 
            tmp.append(" HasName");
        if (hasText) 
            tmp.append(" HasText");
        if (parts != null) {
            for (PartToken p : parts) {
                tmp.append(" ").append(p);
            }
        }
        if (realPart != null) 
            tmp.append(" RealPart=").append(realPart.toString());
        if (newParts != null) {
            for (PartToken p : newParts) {
                tmp.append(" New=").append(p);
            }
        }
        if (partTyp != PartToken.ItemType.UNDEFINED) 
            tmp.append(" PTyp=").append(partTyp.toString());
        if (decreeTok != null) 
            tmp.append(" DecTok=").append(decreeTok.toString());
        if (decree != null) 
            tmp.append(" Ref=").append(decree.toStringEx(true, null, 0));
        if (changeVal != null) 
            tmp.append(" ChangeVal=").append(changeVal.toStringEx(true, null, 0));
        return tmp.toString();
    }

    public boolean isStart() {
        return typ == DecreeChangeTokenTyp.STARTSINGLE || typ == DecreeChangeTokenTyp.STARTMULTU || typ == DecreeChangeTokenTyp.SINGLE;
    }


    public static DecreeChangeToken tryAttach(com.pullenti.ner.Token t, com.pullenti.ner.decree.DecreeChangeReferent main, boolean ignoreNewlines, java.util.ArrayList<com.pullenti.ner.Referent> changeStack, boolean isInEdition) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token tt = t;
        if (t.isNewlineBefore() && !ignoreNewlines) {
            for (tt = t; tt != null; tt = tt.getNext()) {
                if (tt == t && com.pullenti.ner.core.BracketHelper.isBracket(tt, false) && !tt.isChar('(')) 
                    break;
                else if ((tt == t && (tt instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "СТАТЬЯ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "СТАТТЯ")))) && (tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                    com.pullenti.ner.Token tt1 = tt.getNext().getNext();
                    if (tt1 != null && tt1.isChar('.')) {
                        tt1 = tt1.getNext();
                        if (tt1 != null && !tt1.isNewlineBefore() && tt1.isValue("ВНЕСТИ", "УНЕСТИ")) 
                            continue;
                        if (tt1 != null && tt1.isNewlineBefore()) 
                            return null;
                        tt = tt1;
                    }
                    break;
                }
                else if (tt == t && PartToken.tryAttach(tt, null, false, false) != null) 
                    break;
                else if ((tt instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                }
                else if (tt.isHiphen()) {
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter() && !tt.isWhitespaceBefore()) {
                }
                else if (((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && (tt.getNext() instanceof com.pullenti.ner.TextToken)) && !tt.getNext().chars.isLetter()) {
                }
                else 
                    break;
            }
        }
        if (tt == null) 
            return null;
        DecreeChangeToken res = null;
        if (((tt instanceof com.pullenti.ner.TextToken) && t.isNewlineBefore() && !ignoreNewlines) && tt.isValue("ВНЕСТИ", "УНЕСТИ") && ((((tt.getNext() != null && tt.getNext().isValue("В", "ДО"))) || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "ВНЕСТИ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "УНЕСТИ")))) {
            res = _new856(tt, tt, DecreeChangeTokenTyp.STARTMULTU);
            if (tt.getNext() != null && tt.getNext().isValue("В", "ДО")) 
                res.setEndToken((tt = tt.getNext()));
            boolean hasChange = false;
            for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore()) 
                    break;
                if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                    if (res.decree != null && tt.getReferent() != res.decree) 
                        break;
                    res.decree = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                    res.setEndToken(tt);
                    continue;
                }
                java.util.ArrayList<PartToken> li = PartToken.tryAttachList(tt, false, 40);
                if (li != null && li.size() > 0) {
                    res.parts = li;
                    tt = res.setEndToken(li.get(li.size() - 1).getEndToken());
                    continue;
                }
                if (tt.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        tt = br.getEndToken();
                        continue;
                    }
                }
                if (tt.isNewlineBefore()) 
                    break;
                res.setEndToken(tt);
                if (tt.isChar(',') && hasChange) {
                    res.typ = DecreeChangeTokenTyp.STARTSINGLE;
                    break;
                }
                if (tt.isValue("ИЗМЕНЕНИЕ", "ЗМІНА") || tt.isValue("ДОПОЛНЕНИЕ", "ДОДАТОК")) 
                    hasChange = true;
                else if (tt.isValue("СЛЕДУЮЩИЙ", "НАСТУПНИЙ")) {
                }
                else if (tt.isValue("ТАКОЙ", "ТАКИЙ")) {
                }
            }
            if (!hasChange) 
                return null;
            if (res.decree == null) 
                return null;
            tt = res.getEndToken().getNext();
            if (res.typ == DecreeChangeTokenTyp.STARTSINGLE && res.parts == null && tt != null) {
                if ((tt.isValue("ИЗЛОЖИВ", "ВИКЛАВШИ") || tt.isValue("ДОПОЛНИВ", "ДОПОВНИВШИ") || tt.isValue("ИСКЛЮЧИВ", "ВИКЛЮЧИВШИ")) || tt.isValue("ЗАМЕНИВ", "ЗАМІНИВШИ")) {
                    tt = tt.getNext();
                    if (tt != null && tt.getMorph()._getClass().isPreposition()) 
                        tt = tt.getNext();
                    res.parts = PartToken.tryAttachList(tt, false, 40);
                    if (res.parts != null) {
                        tt = res.getEndToken().getNext();
                        if (tt.isValue("ДОПОЛНИВ", "ДОПОВНИВШИ")) 
                            res.actKind = com.pullenti.ner.decree.DecreeChangeKind.APPEND;
                        else if (tt.isValue("ИСКЛЮЧИВ", "ВИКЛЮЧИВШИ")) 
                            res.actKind = com.pullenti.ner.decree.DecreeChangeKind.REMOVE;
                        else if (tt.isValue("ИЗЛОЖИВ", "ВИКЛАВШИ")) 
                            res.actKind = com.pullenti.ner.decree.DecreeChangeKind.NEW;
                        else if (tt.isValue("ЗАМЕНИВ", "ЗАМІНИВШИ")) 
                            res.actKind = com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE;
                        res.setEndToken(res.parts.get(res.parts.size() - 1));
                    }
                }
            }
            return res;
        }
        if (((!ignoreNewlines && t.isNewlineBefore() && ((tt.isValue("ПРИЗНАТЬ", "ВИЗНАТИ") || tt.isValue("СЧИТАТЬ", "ВВАЖАТИ")))) && tt.getNext() != null && tt.getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) && tt.getNext().getNext() != null && tt.getNext().getNext().isValue("СИЛА", "ЧИННІСТЬ")) {
            res = _new857(tt, tt.getNext().getNext(), DecreeChangeTokenTyp.ACTION, com.pullenti.ner.decree.DecreeChangeKind.EXPIRE);
            for (tt = tt.getNext().getNext().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isChar(':')) {
                    res.typ = DecreeChangeTokenTyp.STARTMULTU;
                    res.setEndToken(tt);
                    break;
                }
                if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                    if (res.decree != null) 
                        break;
                    res.typ = DecreeChangeTokenTyp.STARTSINGLE;
                    res.decree = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                    res.setEndToken(tt);
                    continue;
                }
                java.util.ArrayList<PartToken> li = PartToken.tryAttachList(tt, false, 40);
                if (li != null && li.size() > 0) {
                    if (res.parts != null) 
                        break;
                    res.typ = DecreeChangeTokenTyp.STARTSINGLE;
                    res.parts = li;
                    tt = res.setEndToken(li.get(li.size() - 1).getEndToken());
                    continue;
                }
                if (tt.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        tt = br.getEndToken();
                        continue;
                    }
                }
                if (tt.isNewlineBefore()) 
                    break;
            }
            return res;
        }
        if ((!ignoreNewlines && ((t.isNewlineBefore() || tt == t)) && tt.isValue("УТРАТИТЬ", "ВТРАТИТИ")) && tt.getNext() != null && tt.getNext().isValue("СИЛА", "ЧИННІСТЬ")) {
            res = _new856(tt, tt.getNext(), DecreeChangeTokenTyp.UNDEFINED);
            for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                res.setEndToken(tt);
                if (tt.isNewlineAfter()) 
                    break;
            }
            return res;
        }
        if (!ignoreNewlines && t.isNewlineBefore()) {
            if (tt.isValue("СЛОВО", null)) {
            }
            res = _new856(tt, tt, DecreeChangeTokenTyp.STARTSINGLE);
            for (; tt != null; tt = tt.getNext()) {
                if (tt != t && tt.isNewlineBefore()) 
                    break;
                if (tt.isValue("К", null) || tt.isValue("В", null) || tt.isValue("ИЗ", null)) 
                    continue;
                if (tt.isValue("ПЕРЕЧЕНЬ", "ПЕРЕЛІК") && tt.getNext() != null && tt.getNext().isValue("ИЗМЕНЕНИЕ", "ЗМІНА")) {
                    if (tt == t) 
                        res.setBeginToken(res.setEndToken(tt.getNext()));
                    tt = tt.getNext().getNext();
                    res.typ = DecreeChangeTokenTyp.STARTMULTU;
                    if (tt != null && tt.isChar(',')) 
                        tt = tt.getNext();
                    if (tt != null && tt.isValue("ВНОСИМЫЙ", "ВНЕСЕНИЙ")) 
                        tt = tt.getNext();
                    if (tt == null) 
                        break;
                    continue;
                }
                if (tt.isValue("НАИМЕНОВАНИЕ", "НАЙМЕНУВАННЯ") || tt.isValue("НАЗВАНИЕ", "НАЗВА")) {
                    res.setEndToken(tt);
                    if ((tt.getNext() != null && tt.getNext().isAnd() && tt.getNext().getNext() != null) && tt.getNext().getNext().isValue("ТЕКСТ", null)) {
                        res.hasText = true;
                        res.setEndToken((tt = tt.getNext().getNext()));
                    }
                    res.hasName = true;
                    continue;
                }
                if (tt.isValue("ТЕКСТ", null)) {
                    PartToken pt = PartToken.tryAttach(tt.getNext(), null, false, true);
                    if (pt != null && pt.getEndToken().getNext() != null && pt.getEndToken().getNext().isValue("СЧИТАТЬ", "ВВАЖАТИ")) {
                        res.setEndToken(pt.getEndToken());
                        if (changeStack != null && changeStack.size() > 0 && (changeStack.get(0) instanceof com.pullenti.ner.decree.DecreePartReferent)) 
                            res.realPart = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(changeStack.get(0), com.pullenti.ner.decree.DecreePartReferent.class);
                        res.actKind = com.pullenti.ner.decree.DecreeChangeKind.CONSIDER;
                        res.partTyp = pt.typ;
                        res.hasText = true;
                        return res;
                    }
                }
                if ((res.parts == null && !res.hasName && tt.isValue("ДОПОЛНИТЬ", "ДОПОВНИТИ")) && tt.getNext() != null) {
                    res.actKind = com.pullenti.ner.decree.DecreeChangeKind.APPEND;
                    com.pullenti.ner.Token tt1 = DecreeToken.isKeyword(tt.getNext(), false);
                    if (tt1 == null || tt1.getMorph().getCase().isInstrumental()) 
                        tt1 = tt.getNext();
                    else 
                        tt1 = tt1.getNext();
                    if (tt1 != null && tt1.isValue("НОВЫЙ", "НОВИЙ")) 
                        tt1 = tt1.getNext();
                    if (tt1 != null && tt1.getMorph().getCase().isInstrumental()) {
                        PartToken pt = PartToken.tryAttach(tt1, null, false, false);
                        if (pt == null) 
                            pt = PartToken.tryAttach(tt1, null, false, true);
                        if (pt != null && pt.typ != PartToken.ItemType.PREFIX) {
                            res.partTyp = pt.typ;
                            tt = res.setEndToken(pt.getEndToken());
                            if (res.newParts == null) 
                                res.newParts = new java.util.ArrayList<PartToken>();
                            res.newParts.add(pt);
                            if (tt.getNext() != null && tt.getNext().isAnd()) {
                                pt = PartToken.tryAttach(tt.getNext().getNext(), null, false, false);
                                if (pt == null) 
                                    pt = PartToken.tryAttach(tt.getNext().getNext(), null, false, true);
                                if (pt != null) {
                                    res.newParts.add(pt);
                                    tt = res.setEndToken(pt.getEndToken());
                                }
                            }
                        }
                        continue;
                    }
                }
                java.util.ArrayList<PartToken> li = PartToken.tryAttachList(tt, false, 40);
                if (li == null && tt.isValue("ПРИМЕЧАНИЕ", "ПРИМІТКА")) {
                    li = new java.util.ArrayList<PartToken>();
                    li.add(PartToken._new860(tt, tt, PartToken.ItemType.NOTICE));
                }
                if (li != null && li.size() > 0 && li.get(0).typ == PartToken.ItemType.PREFIX) 
                    li = null;
                if (li != null && li.size() > 0) {
                    if (li.size() == 1 && PartToken._getRank(li.get(0).typ) > 0 && tt == t) {
                        if (li.get(0).isNewlineAfter()) 
                            return null;
                        if (li.get(0).getEndToken().getNext() != null && li.get(0).getEndToken().getNext().isChar('.')) 
                            return null;
                    }
                    if (res.actKind != com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                        if (res.parts != null) 
                            break;
                        res.parts = li;
                    }
                    tt = res.setEndToken(li.get(li.size() - 1).getEndToken());
                    continue;
                }
                if ((tt.getMorph()._getClass().isNoun() && changeStack != null && changeStack.size() > 0) && (changeStack.get(0) instanceof com.pullenti.ner.decree.DecreePartReferent)) {
                    PartToken pa = PartToken.tryAttach(tt, null, false, true);
                    if (pa != null) {
                        if (changeStack.get(0).getStringValue(PartToken._getAttrNameByTyp(pa.typ)) != null) {
                            res.realPart = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(changeStack.get(0), com.pullenti.ner.decree.DecreePartReferent.class);
                            res.setEndToken(tt);
                            continue;
                        }
                    }
                }
                if (res.actKind == com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                    PartToken pa = PartToken.tryAttach(tt, null, false, true);
                    if (pa != null) {
                        if (res.newParts == null) 
                            res.newParts = new java.util.ArrayList<PartToken>();
                        res.newParts.add(pa);
                        res.setEndToken(pa.getEndToken());
                        continue;
                    }
                }
                if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                    res.decree = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                    res.setEndToken(tt);
                    if (tt.getNext() != null && tt.getNext().isChar('(')) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null) 
                            res.setEndToken((tt = br.getEndToken()));
                    }
                    continue;
                }
                PartToken pt0 = PartToken.tryAttach(tt, null, false, true);
                if (pt0 != null && ((res.hasName || pt0.typ == PartToken.ItemType.APPENDIX)) && pt0.typ != PartToken.ItemType.PREFIX) {
                    tt = res.setEndToken(pt0.getEndToken());
                    res.partTyp = pt0.typ;
                    if (pt0.typ == PartToken.ItemType.APPENDIX && res.parts == null) {
                        res.parts = new java.util.ArrayList<PartToken>();
                        res.parts.add(pt0);
                    }
                    continue;
                }
                if (res.changeVal == null && !isInEdition) {
                    DecreeChangeToken res1 = null;
                    if (tt == res.getBeginToken() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                    }
                    else 
                        res1 = tryAttach(tt, main, true, null, false);
                    if (res1 != null && res1.typ == DecreeChangeTokenTyp.VALUE && res1.changeVal != null) {
                        res.changeVal = res1.changeVal;
                        if (res.actKind == com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) 
                            res.actKind = res1.actKind;
                        tt = res.setEndToken(res1.getEndToken());
                        if (tt.getNext() != null && tt.getNext().isValue("К", null)) 
                            tt = tt.getNext();
                        continue;
                    }
                    if (tt.isValue("ПОСЛЕ", "ПІСЛЯ")) {
                        pt0 = PartToken.tryAttach(tt.getNext(), null, true, false);
                        if (pt0 != null && pt0.typ != PartToken.ItemType.PREFIX) {
                            if (res.parts == null) {
                                res.parts = new java.util.ArrayList<PartToken>();
                                res.parts.add(pt0);
                            }
                            tt = res.setEndToken(pt0.getEndToken());
                            continue;
                        }
                    }
                    if (tt.isValue("ТЕКСТ", null) && tt.getPrevious() != null && tt.getPrevious().isValue("В", "У")) 
                        continue;
                    if (tt.isValue("ИЗМЕНЕНИЕ", "ЗМІНА")) {
                        res.setEndToken(tt);
                        continue;
                    }
                }
                if (tt != t && ((res.hasName || res.parts != null)) && res.decree == null) {
                    java.util.ArrayList<DecreeToken> dts = DecreeToken.tryAttachList(tt, null, 10, false);
                    if (dts != null && dts.size() > 0 && dts.get(0).typ == DecreeToken.ItemType.TYP) {
                        tt = res.setEndToken(dts.get(dts.size() - 1).getEndToken());
                        if (main != null && res.decree == null && res.decreeTok == null) {
                            com.pullenti.ner.decree.DecreeReferent dec = null;
                            for (com.pullenti.ner.Referent v : main.getOwners()) {
                                if (v instanceof com.pullenti.ner.decree.DecreeReferent) {
                                    dec = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(v, com.pullenti.ner.decree.DecreeReferent.class);
                                    break;
                                }
                                else if (v instanceof com.pullenti.ner.decree.DecreePartReferent) {
                                    dec = ((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(v, com.pullenti.ner.decree.DecreePartReferent.class)).getOwner();
                                    if (dec != null) 
                                        break;
                                }
                            }
                            if (dec != null && com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), dts.get(0).value)) {
                                res.decree = dec;
                                res.decreeTok = dts.get(0);
                            }
                        }
                        continue;
                    }
                }
                if (tt == res.getBeginToken() && main != null) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null) {
                        com.pullenti.ner.Token tt1 = npt.getEndToken().getNext();
                        if ((tt1 != null && tt1.isValue("ИЗЛОЖИТЬ", "ВИКЛАСТИ") && tt1.getNext() != null) && tt1.getNext().isValue("В", null)) {
                            PartToken pt = PartToken._new860(tt, npt.getEndToken(), PartToken.ItemType.APPENDIX);
                            pt.name = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                            res.parts = new java.util.ArrayList<PartToken>();
                            res.parts.add(pt);
                            res.setEndToken(pt.getEndToken());
                            break;
                        }
                    }
                }
                com.pullenti.ner.Token ttt = DecreeToken.isKeyword(tt, false);
                if (ttt != null && res.parts == null) {
                    com.pullenti.ner.Token ttt0 = ttt;
                    for (; ttt != null; ttt = ttt.getNext()) {
                        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(ttt)) 
                            break;
                        if (ttt.isChar('(') && ttt.getNext() != null && ttt.getNext().isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
                            if (ttt.isNewlineBefore()) 
                                break;
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(ttt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br == null) 
                                break;
                            PartToken pt = PartToken.tryAttach(ttt.getNext(), null, false, false);
                            if (pt == null) 
                                PartToken.tryAttach(ttt.getNext(), null, false, true);
                            if (pt != null) {
                                res.parts = new java.util.ArrayList<PartToken>();
                                res.parts.add(pt);
                                tt = res.setEndToken(br.getEndToken());
                                break;
                            }
                        }
                    }
                    if (res.parts != null) 
                        continue;
                    if (res.actKind == com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                        tt = res.setEndToken(ttt0);
                        continue;
                    }
                    tt = ttt0;
                    continue;
                }
                break;
            }
            if (((res.hasName || res.parts != null || res.decree != null) || res.realPart != null || res.actKind != com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) || res.changeVal != null) {
                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar(':') && res.getEndToken().getNext().isNewlineAfter()) {
                    res.typ = DecreeChangeTokenTyp.SINGLE;
                    res.setEndToken(res.getEndToken().getNext());
                }
                return res;
            }
            if (res.getBeginToken() == tt) {
                com.pullenti.ner.core.TerminToken tok1 = m_Terms.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null) {
                }
                else 
                    return null;
            }
            else 
                return null;
        }
        com.pullenti.ner.core.TerminToken tok = m_Terms.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tt.getMorph()._getClass().isAdjective() && (((tt instanceof com.pullenti.ner.NumberToken) || tt.isValue("ПОСЛЕДНИЙ", "ОСТАННІЙ") || tt.isValue("ПРЕДПОСЛЕДНИЙ", "ПЕРЕДОСТАННІЙ")))) {
            tok = m_Terms.tryParse(tt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && (tok.termin.tag instanceof com.pullenti.ner.decree.DecreeChangeValueKind)) {
            }
            else 
                tok = null;
        }
        if (tok != null) {
            if (tok.termin.tag instanceof com.pullenti.ner.decree.DecreeChangeKind) {
                res = _new857(tt, tok.getEndToken(), DecreeChangeTokenTyp.ACTION, (com.pullenti.ner.decree.DecreeChangeKind)tok.termin.tag);
                if (((res.actKind == com.pullenti.ner.decree.DecreeChangeKind.APPEND || res.actKind == com.pullenti.ner.decree.DecreeChangeKind.CONSIDER)) && tok.getEndToken().getNext() != null && tok.getEndToken().getNext().getMorph().getCase().isInstrumental()) {
                    PartToken pt = PartToken.tryAttach(tok.getEndToken().getNext(), null, false, false);
                    if (pt == null) 
                        pt = PartToken.tryAttach(tok.getEndToken().getNext(), null, false, true);
                    if (pt != null && pt.typ != PartToken.ItemType.PREFIX) {
                        if (res.actKind == com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                            res.partTyp = pt.typ;
                            if (res.newParts == null) 
                                res.newParts = new java.util.ArrayList<PartToken>();
                            res.newParts.add(pt);
                        }
                        else if (res.actKind == com.pullenti.ner.decree.DecreeChangeKind.CONSIDER) {
                            res.changeVal = new com.pullenti.ner.decree.DecreeChangeValueReferent();
                            res.changeVal.setValue(pt.getSourceText());
                        }
                        tt = res.setEndToken(pt.getEndToken());
                        if (tt.getNext() != null && tt.getNext().isAnd() && res.actKind == com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                            pt = PartToken.tryAttach(tt.getNext().getNext(), null, false, false);
                            if (pt == null) 
                                pt = PartToken.tryAttach(tt.getNext().getNext(), null, false, true);
                            if (pt != null) {
                                res.newParts.add(pt);
                                tt = res.setEndToken(pt.getEndToken());
                            }
                        }
                    }
                }
                return res;
            }
            if (tok.termin.tag instanceof com.pullenti.ner.decree.DecreeChangeValueKind) {
                res = _new856(tt, tok.getEndToken(), DecreeChangeTokenTyp.VALUE);
                res.changeVal = new com.pullenti.ner.decree.DecreeChangeValueReferent();
                res.changeVal.setKind((com.pullenti.ner.decree.DecreeChangeValueKind)tok.termin.tag);
                tt = tok.getEndToken().getNext();
                if (tt == null) 
                    return null;
                if (res.changeVal.getKind() == com.pullenti.ner.decree.DecreeChangeValueKind.SEQUENCE || res.changeVal.getKind() == com.pullenti.ner.decree.DecreeChangeValueKind.FOOTNOTE) {
                    if (tt instanceof com.pullenti.ner.NumberToken) {
                        res.changeVal.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        res.setEndToken(tt);
                        tt = tt.getNext();
                    }
                    else if (res.getBeginToken() instanceof com.pullenti.ner.NumberToken) 
                        res.changeVal.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                    else if (res.getBeginToken().getMorph()._getClass().isAdjective()) 
                        res.changeVal.setNumber(res.getBeginToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false) && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt.getNext().getNext(), false, null, false)) {
                        res.changeVal.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                        res.setEndToken((tt = tt.getNext().getNext()));
                        tt = tt.getNext();
                    }
                }
                if (tt != null && tt.isValue("ИЗЛОЖИТЬ", "ВИКЛАСТИ") && res.actKind == com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) {
                    res.actKind = com.pullenti.ner.decree.DecreeChangeKind.NEW;
                    tt = tt.getNext();
                    if (tt != null && tt.isValue("В", null)) 
                        tt = tt.getNext();
                }
                if ((tt != null && ((tt.isValue("СЛЕДУЮЩИЙ", "НАСТУПНИЙ") || tt.isValue("ТАКОЙ", "ТАКИЙ"))) && tt.getNext() != null) && ((tt.getNext().isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || tt.getNext().isValue("СОДЕРЖИМОЕ", "ВМІСТ") || tt.getNext().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")))) 
                    tt = tt.getNext().getNext();
                else if (tt != null && tt.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
                    tt = tt.getNext();
                if (tt != null && tt.isChar(':')) 
                    tt = tt.getNext();
                boolean canBeStart = false;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) 
                    canBeStart = true;
                else if ((tt instanceof com.pullenti.ner.MetaToken) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken(), true, false)) 
                    canBeStart = true;
                else if (tt != null && tt.isNewlineBefore() && tt.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
                    if ((tt.getPrevious() != null && tt.getPrevious().isChar(':') && tt.getPrevious().getPrevious() != null) && tt.getPrevious().getPrevious().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
                        canBeStart = true;
                }
                if (canBeStart) {
                    for (com.pullenti.ner.Token ttt = (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false) ? tt.getNext() : tt); ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isCharOf(".;") && ttt.isNewlineAfter()) {
                            res.changeVal.setValue((new com.pullenti.ner.MetaToken(tt.getNext(), ttt.getPrevious(), null)).getSourceText());
                            res.setEndToken(ttt);
                            break;
                        }
                        if (com.pullenti.ner.core.BracketHelper.isBracket(ttt, true)) {
                        }
                        else if ((ttt instanceof com.pullenti.ner.MetaToken) && com.pullenti.ner.core.BracketHelper.isBracket(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.MetaToken.class)).getEndToken(), true)) {
                        }
                        else 
                            continue;
                        if (ttt.getNext() == null || ttt.isNewlineAfter()) {
                        }
                        else if (ttt.getNext().isCharOf(".;") && ttt.getNext().isNewlineAfter()) {
                        }
                        else if (ttt.getNext().isCommaAnd() && tryAttach(ttt.getNext().getNext(), main, false, changeStack, true) != null) {
                        }
                        else if (tryAttach(ttt.getNext(), main, false, changeStack, true) != null || m_Terms.tryParse(ttt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                        }
                        else 
                            continue;
                        String val = (new com.pullenti.ner.MetaToken((com.pullenti.ner.core.BracketHelper.isBracket(tt, true) ? tt.getNext() : tt), (com.pullenti.ner.core.BracketHelper.isBracket(ttt, true) ? ttt.getPrevious() : ttt), null)).getSourceText();
                        res.setEndToken(ttt);
                        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) 
                            val = val.substring(1);
                        if (!com.pullenti.ner.core.BracketHelper.isBracket(ttt, true)) 
                            val = val.substring(0, 0 + val.length() - 1);
                        res.changeVal.setValue(val);
                        break;
                    }
                    if (res.changeVal.getValue() == null) 
                        return null;
                    if (res.changeVal.getKind() == com.pullenti.ner.decree.DecreeChangeValueKind.WORDS) {
                        tok = m_Terms.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok != null && (tok.termin.tag instanceof com.pullenti.ner.decree.DecreeChangeValueKind) && ((com.pullenti.ner.decree.DecreeChangeValueKind)tok.termin.tag) == com.pullenti.ner.decree.DecreeChangeValueKind.ROBUSTWORDS) {
                            res.changeVal.setKind(com.pullenti.ner.decree.DecreeChangeValueKind.ROBUSTWORDS);
                            res.setEndToken(tok.getEndToken());
                        }
                    }
                }
                return res;
            }
        }
        int isNexChange = 0;
        if (t != null && t.isValue("В", "У") && t.getNext() != null) {
            t = t.getNext();
            if (t.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ") && t.getNext() != null) {
                isNexChange = 1;
                t = t.getNext();
            }
        }
        if (((t.isValue("СЛЕДУЮЩИЙ", "НАСТУПНИЙ") || tt.isValue("ТАКОЙ", "ТАКИЙ"))) && t.getNext() != null && ((t.getNext().isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || t.getNext().isValue("СОДЕРЖИМОЕ", "ВМІСТ") || t.getNext().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")))) {
            isNexChange = 2;
            t = t.getNext().getNext();
        }
        if (t.isChar(':') && t.getNext() != null) {
            if (t.getPrevious() != null && t.getPrevious().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
                isNexChange++;
            tt = (t = t.getNext());
            if (isNexChange > 0) 
                isNexChange++;
        }
        if ((t == tt && t.getPrevious() != null && t.getPrevious().isChar(':')) && com.pullenti.ner.core.BracketHelper.isBracket(t, false) && !t.isChar('(')) 
            isNexChange = 1;
        if (((isNexChange > 0 && com.pullenti.ner.core.BracketHelper.isBracket(t, true))) || ((isNexChange > 1 && t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")))) {
            res = _new856(t, t, DecreeChangeTokenTyp.VALUE);
            res.changeVal = com.pullenti.ner.decree.DecreeChangeValueReferent._new865(com.pullenti.ner.decree.DecreeChangeValueKind.TEXT);
            if (isInEdition) 
                return res;
            com.pullenti.ner.Token t0 = (com.pullenti.ner.core.BracketHelper.isBracket(t, true) ? t.getNext() : t);
            com.pullenti.ner.Token doubt1 = null;
            com.pullenti.ner.instrument.internal.InstrToken1 clauseLast = null;
            for (tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (!tt.isNewlineAfter()) 
                    continue;
                boolean isDoubt = false;
                com.pullenti.ner.instrument.internal.InstrToken1 instr = com.pullenti.ner.instrument.internal.InstrToken1.parse(tt.getNext(), true, null, 0, null, false, 0, false, false);
                DecreeChangeToken dcNext = tryAttach(tt.getNext(), null, false, null, true);
                if (dcNext == null) 
                    dcNext = tryAttach(tt.getNext(), null, true, null, true);
                if (tt.getNext() == null) {
                }
                else if (dcNext != null && ((dcNext.isStart() || dcNext.changeVal != null || dcNext.typ == DecreeChangeTokenTyp.UNDEFINED))) {
                }
                else {
                    isDoubt = true;
                    PartToken pt = PartToken.tryAttach(tt.getNext(), null, false, false);
                    if (pt != null && pt.typ == PartToken.ItemType.CLAUSE && ((pt.isNewlineAfter() || ((pt.getEndToken().getNext() != null && pt.getEndToken().getNext().isChar('.')))))) {
                        isDoubt = false;
                        if (clauseLast != null && instr != null && com.pullenti.ner.instrument.internal.NumberingHelper.calcDelta(clauseLast, instr, true) == 1) 
                            isDoubt = true;
                    }
                }
                if (instr != null && instr.typ == com.pullenti.ner.instrument.internal.InstrToken1.Types.CLAUSE) 
                    clauseLast = instr;
                if (isDoubt && instr != null) {
                    for (com.pullenti.ner.Token ttt = tt; ttt != null && ttt.getEndChar() <= instr.getEndChar(); ttt = ttt.getNext()) {
                        if (ttt.isValue("УТРАТИТЬ", "ВТРАТИТИ") && ttt.getNext() != null && ttt.getNext().isValue("СИЛА", "ЧИННІСТЬ")) {
                            isDoubt = false;
                            break;
                        }
                    }
                }
                res.setEndToken(tt);
                com.pullenti.ner.Token tt1 = tt;
                if (tt1.isCharOf(";.")) 
                    tt1 = res.setEndToken(tt1.getPrevious());
                if (com.pullenti.ner.core.BracketHelper.isBracket(tt1, true)) 
                    tt1 = tt1.getPrevious();
                else if ((tt1 instanceof com.pullenti.ner.MetaToken) && com.pullenti.ner.core.BracketHelper.isBracket(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.MetaToken.class)).getEndToken(), true)) {
                }
                else 
                    continue;
                if (isDoubt) {
                    if (doubt1 == null) 
                        doubt1 = tt1;
                    continue;
                }
                if (tt1.getBeginChar() > t.getEndChar()) {
                    res.changeVal.setValue((new com.pullenti.ner.MetaToken(t0, tt1, null)).getSourceText());
                    return res;
                }
                break;
            }
            if (doubt1 != null) {
                res.changeVal.setValue((new com.pullenti.ner.MetaToken(t0, doubt1, null)).getSourceText());
                res.setEndToken(doubt1);
                if (com.pullenti.ner.core.BracketHelper.isBracket(doubt1.getNext(), true)) 
                    res.setEndToken(doubt1.getNext());
                return res;
            }
            return null;
        }
        if (t.isValue("ПОСЛЕ", "ПІСЛЯ")) {
            res = tryAttach(t.getNext(), null, false, null, false);
            if (res != null && res.typ == DecreeChangeTokenTyp.VALUE) {
                res.typ = DecreeChangeTokenTyp.AFTERVALUE;
                res.setBeginToken(t);
                return res;
            }
        }
        return null;
    }

    private static java.util.ArrayList<DecreeChangeToken> tryAttachList(com.pullenti.ner.Token t) {
        if (t == null || t.isNewlineBefore()) 
            return null;
        DecreeChangeToken d0 = tryAttach(t, null, false, null, false);
        if (d0 == null) 
            return null;
        java.util.ArrayList<DecreeChangeToken> res = new java.util.ArrayList<DecreeChangeToken>();
        res.add(d0);
        t = d0.getEndToken().getNext();
        for (; t != null; t = t.getNext()) {
            if (t.isNewlineBefore()) {
                if ((t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК") && t.getPrevious() != null && t.getPrevious().isChar(':')) && t.getPrevious().getPrevious() != null && t.getPrevious().getPrevious().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) {
                }
                else 
                    break;
            }
            DecreeChangeToken d = tryAttach(t, null, false, null, false);
            if (d == null && t.isChar('.') && !t.isNewlineAfter()) 
                continue;
            if (d == null) {
                if (t.isValue("НОВЫЙ", "НОВИЙ")) 
                    continue;
                if (t.isValue("НА", null)) 
                    continue;
                if (t.isChar(':') && ((!t.isNewlineAfter() || res.get(res.size() - 1).actKind == com.pullenti.ner.decree.DecreeChangeKind.NEW))) 
                    continue;
                if ((t instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ТЕКСТОМ")) 
                    continue;
                java.util.ArrayList<PartToken> pts = PartToken.tryAttachList(t, false, 40);
                if (pts != null) 
                    d = _new866(pts.get(0).getBeginToken(), pts.get(pts.size() - 1).getEndToken(), DecreeChangeTokenTyp.UNDEFINED, pts);
                else {
                    PartToken pt = PartToken.tryAttach(t, null, true, false);
                    if (pt == null) 
                        pt = PartToken.tryAttach(t, null, true, true);
                    if (pt != null) {
                        d = new DecreeChangeToken(pt.getBeginToken(), pt.getEndToken());
                        if (t.getPrevious() != null && t.getPrevious().isValue("НОВЫЙ", "НОВИЙ")) {
                            d.newParts = new java.util.ArrayList<PartToken>();
                            d.newParts.add(pt);
                        }
                        else 
                            d.partTyp = pt.typ;
                    }
                }
            }
            if (d == null) 
                break;
            if (d.typ == DecreeChangeTokenTyp.SINGLE || d.typ == DecreeChangeTokenTyp.STARTMULTU || d.typ == DecreeChangeTokenTyp.STARTSINGLE) 
                break;
            res.add(d);
            t = d.getEndToken();
        }
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_Terms;

    public static void initialize() {
        if (m_Terms != null) 
            return;
        m_Terms = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new100("ИЗЛОЖИТЬ В СЛЕДУЮЩЕЙ РЕДАКЦИИ", com.pullenti.ner.decree.DecreeChangeKind.NEW);
        t.addVariant("ИЗЛОЖИВ ЕГО В СЛЕДУЮЩЕЙ РЕДАКЦИИ", false);
        t.addVariant("ИЗЛОЖИТЬ В РЕДАКЦИИ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ВИКЛАСТИ В НАСТУПНІЙ РЕДАКЦІЇ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.NEW);
        t.addVariant("ВИКЛАВШИ В ТАКІЙ РЕДАКЦІЇ", false);
        t.addVariant("ВИКЛАВШИ ЙОГО В НАСТУПНІЙ РЕДАКЦІЇ", false);
        t.addVariant("ВИКЛАСТИ В РЕДАКЦІЇ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПРИЗНАТЬ УТРАТИВШИМ СИЛУ", com.pullenti.ner.decree.DecreeChangeKind.EXPIRE);
        t.addVariant("СЧИТАТЬ УТРАТИВШИМ СИЛУ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ВИЗНАТИ таким, що ВТРАТИВ ЧИННІСТЬ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.EXPIRE);
        t.addVariant("ВВАЖАТИ таким, що ВТРАТИВ ЧИННІСТЬ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ИСКЛЮЧИТЬ", com.pullenti.ner.decree.DecreeChangeKind.REMOVE);
        t.addVariant("ИСКЛЮЧИВ ИЗ НЕГО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ВИКЛЮЧИТИ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.REMOVE);
        t.addVariant("ВИКЛЮЧИВШИ З НЬОГО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("СЧИТАТЬ", com.pullenti.ner.decree.DecreeChangeKind.CONSIDER);
        t.addVariant("СЧИТАТЬ СООТВЕТСТВЕННО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ВВАЖАТИ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.CONSIDER);
        t.addVariant("ВВАЖАТИ ВІДПОВІДНО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЗАМЕНИТЬ", com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE);
        t.addVariant("ЗАМЕНИВ В НЕМ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ЗАМІНИТИ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE);
        t.addVariant("ЗАМІНИВШИ В НЬОМУ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ДОПОЛНИТЬ", com.pullenti.ner.decree.DecreeChangeKind.APPEND);
        t.addVariant("ДОПОЛНИВ ЕГО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ДОПОВНИТИ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeKind.APPEND);
        t.addVariant("ДОПОВНИВШИ ЙОГО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("СЛОВО", com.pullenti.ner.decree.DecreeChangeValueKind.WORDS);
        t.addVariant("АББРЕВИАТУРА", false);
        t.addVariant("АБРЕВІАТУРА", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЦИФРА", com.pullenti.ner.decree.DecreeChangeValueKind.NUMBERS);
        t.addVariant("ЧИСЛО", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПРЕДЛОЖЕНИЕ", com.pullenti.ner.decree.DecreeChangeValueKind.SEQUENCE);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ПРОПОЗИЦІЯ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeValueKind.SEQUENCE);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("СНОСКА", com.pullenti.ner.decree.DecreeChangeValueKind.FOOTNOTE);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("ВИНОСКА", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeValueKind.FOOTNOTE);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЛОК", com.pullenti.ner.decree.DecreeChangeValueKind.BLOCK);
        t.addVariant("БЛОК СО СЛОВАМИ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("БЛОК", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeValueKind.BLOCK);
        t.addVariant("БЛОК ЗІ СЛОВАМИ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new100("В СООТВЕТСТВУЮЩИХ ЧИСЛЕ И ПАДЕЖЕ", com.pullenti.ner.decree.DecreeChangeValueKind.ROBUSTWORDS);
        t.addVariant("В СООТВЕТСТВУЮЩЕМ ПАДЕЖЕ", false);
        t.addVariant("В СООТВЕТСТВУЮЩЕМ ЧИСЛЕ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new372("У ВІДПОВІДНОМУ ЧИСЛІ ТА ВІДМІНКУ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner.decree.DecreeChangeValueKind.ROBUSTWORDS);
        t.addVariant("У ВІДПОВІДНОМУ ВІДМІНКУ", false);
        t.addVariant("У ВІДПОВІДНОМУ ЧИСЛІ", false);
        m_Terms.add(t);
    }

    public static java.util.ArrayList<com.pullenti.ner.ReferentToken> attachReferents(com.pullenti.ner.Referent dpr, DecreeChangeToken tok0) {
        if (dpr == null || tok0 == null) 
            return null;
        com.pullenti.ner.Token tt0 = tok0.getEndToken().getNext();
        if (tt0 != null && tt0.isCommaAnd() && tok0.actKind == com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) 
            tt0 = tt0.getNext();
        if (tt0 != null && tt0.isChar(':')) 
            tt0 = tt0.getNext();
        java.util.ArrayList<DecreeChangeToken> toks = tryAttachList(tt0);
        if (toks == null) 
            toks = new java.util.ArrayList<DecreeChangeToken>();
        toks.add(0, tok0);
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        com.pullenti.ner.decree.DecreeChangeReferent dcr = new com.pullenti.ner.decree.DecreeChangeReferent();
        dcr.addSlot(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_OWNER, dpr, false, 0);
        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dcr, tok0.getBeginToken(), tok0.getEndToken(), null);
        res.add(rt);
        java.util.ArrayList<String> newItems = null;
        while (true) {
            for (int i = 0; i < toks.size(); i++) {
                DecreeChangeToken tok = toks.get(i);
                if (tok.hasText && tok.hasName) 
                    dcr.setOwnerNameAndText(true);
                else if (tok.hasName) 
                    dcr.setOwnerName(true);
                else if (tok.hasText) 
                    dcr.setOnlyText(true);
                rt.setEndToken(tok.getEndToken());
                if (tok.typ == DecreeChangeTokenTyp.AFTERVALUE) {
                    if (tok.changeVal != null) {
                        dcr.setParam(tok.changeVal);
                        if (tok.getEndChar() > rt.getEndChar()) 
                            rt.setEndToken(tok.getEndToken());
                        res.add(res.size() - 1, new com.pullenti.ner.ReferentToken(tok.changeVal, tok.getBeginToken(), tok.getEndToken(), null));
                    }
                    continue;
                }
                if (tok.actKind != com.pullenti.ner.decree.DecreeChangeKind.UNDEFINED) {
                    dcr.setKind(tok.actKind);
                    if (tok.actKind == com.pullenti.ner.decree.DecreeChangeKind.EXPIRE) 
                        break;
                }
                if (tok.changeVal != null) {
                    if (((i + 2) < toks.size()) && ((toks.get(i + 1).actKind == com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE || toks.get(i + 1).actKind == com.pullenti.ner.decree.DecreeChangeKind.NEW)) && toks.get(i + 2).changeVal != null) {
                        dcr.setParam(tok.changeVal);
                        com.pullenti.ner.ReferentToken rt11 = new com.pullenti.ner.ReferentToken(tok.changeVal, tok.getBeginToken(), tok.getEndToken(), null);
                        if (tok.parts != null && tok.parts.size() > 0) 
                            rt11.setBeginToken(tok.parts.get(tok.parts.size() - 1).getEndToken().getNext());
                        res.add(res.size() - 1, rt11);
                        dcr.setValue(toks.get(i + 2).changeVal);
                        dcr.setKind(toks.get(i + 1).actKind);
                        i += 2;
                        tok = toks.get(i);
                    }
                    else if (((i + 1) < toks.size()) && toks.get(i + 1).changeVal != null && dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE) {
                        dcr.setParam(tok.changeVal);
                        res.add(res.size() - 1, new com.pullenti.ner.ReferentToken(tok.changeVal, tok.getBeginToken(), tok.getEndToken(), null));
                        dcr.setValue(toks.get(i + 1).changeVal);
                        i += 1;
                        tok = toks.get(i);
                    }
                    else if (dcr.getValue() == null) 
                        dcr.setValue(tok.changeVal);
                    else if ((dcr.getValue().getKind() != com.pullenti.ner.decree.DecreeChangeValueKind.TEXT && tok.changeVal.getKind() == com.pullenti.ner.decree.DecreeChangeValueKind.TEXT && tok.changeVal.getValue() != null) && dcr.getValue().getValue() == null) 
                        dcr.getValue().setValue(tok.changeVal.getValue());
                    else 
                        dcr.setValue(tok.changeVal);
                    if (tok.getEndChar() > rt.getEndChar()) 
                        rt.setEndToken(tok.getEndToken());
                    res.add(res.size() - 1, new com.pullenti.ner.ReferentToken(tok.changeVal, tok.getBeginToken(), tok.getEndToken(), null));
                    if (dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.CONSIDER || dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.NEW) 
                        break;
                }
                if (dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.APPEND && tok.newParts != null) {
                    for (PartToken np : tok.newParts) {
                        int rank = PartToken._getRank(np.typ);
                        if (rank == 0) 
                            continue;
                        String eqLevVal = null;
                        if (dpr instanceof com.pullenti.ner.decree.DecreePartReferent) {
                            if (!((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(dpr, com.pullenti.ner.decree.DecreePartReferent.class)).isAllItemsOverThisLevel(np.typ)) {
                                eqLevVal = dpr.getStringValue(PartToken._getAttrNameByTyp(np.typ));
                                if (eqLevVal == null) 
                                    continue;
                            }
                        }
                        dcr.setKind(com.pullenti.ner.decree.DecreeChangeKind.APPEND);
                        if (newItems == null) 
                            newItems = new java.util.ArrayList<String>();
                        String nam = PartToken._getAttrNameByTyp(np.typ);
                        if (nam == null) 
                            continue;
                        if (np.values.size() == 0) {
                            if (eqLevVal == null) 
                                newItems.add(nam);
                            else {
                                int n;
                                com.pullenti.unisharp.Outargwrapper<Integer> wrapn889 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                                boolean inoutres890 = com.pullenti.unisharp.Utils.parseInteger(eqLevVal, 0, null, wrapn889);
                                n = (wrapn889.value != null ? wrapn889.value : 0);
                                if (inoutres890) 
                                    newItems.add((nam + " " + (n + 1)));
                                else 
                                    newItems.add(nam);
                            }
                        }
                        else if (np.values.size() == 2 && np.values.get(0).getEndToken().getNext().isHiphen()) {
                            java.util.ArrayList<String> vv = com.pullenti.ner.instrument.internal.NumberingHelper.createDiap(np.values.get(0).value, np.values.get(1).value);
                            if (vv != null) {
                                for (String v : vv) {
                                    newItems.add((nam + " " + v));
                                }
                            }
                        }
                        if (newItems.size() == 0) {
                            for (PartToken.PartValue v : np.values) {
                                newItems.add((nam + " " + v.value));
                            }
                        }
                    }
                }
            }
            if (!dcr.checkCorrect()) 
                return null;
            if (newItems != null && dcr.getValue() != null && dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.APPEND) {
                for (String v : newItems) {
                    dcr.getValue().addSlot(com.pullenti.ner.decree.DecreeChangeValueReferent.ATTR_NEWITEM, v, false, 0);
                }
            }
            newItems = null;
            if (rt.getEndToken().getNext() == null || !rt.getEndToken().getNext().isComma()) 
                break;
            toks = tryAttachList(rt.getEndToken().getNext().getNext());
            if (toks == null) 
                break;
            com.pullenti.ner.decree.DecreeChangeReferent dts1 = new com.pullenti.ner.decree.DecreeChangeReferent();
            for (com.pullenti.ner.Referent o : dcr.getOwners()) {
                dts1.addSlot(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_OWNER, o, false, 0);
            }
            rt = new com.pullenti.ner.ReferentToken(dts1, toks.get(0).getBeginToken(), toks.get(0).getEndToken(), null);
            res.add(rt);
            dcr = dts1;
        }
        return res;
    }

    public static DecreeChangeToken _new856(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DecreeChangeTokenTyp _arg3) {
        DecreeChangeToken res = new DecreeChangeToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static DecreeChangeToken _new857(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DecreeChangeTokenTyp _arg3, com.pullenti.ner.decree.DecreeChangeKind _arg4) {
        DecreeChangeToken res = new DecreeChangeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.actKind = _arg4;
        return res;
    }

    public static DecreeChangeToken _new866(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DecreeChangeTokenTyp _arg3, java.util.ArrayList<PartToken> _arg4) {
        DecreeChangeToken res = new DecreeChangeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.parts = _arg4;
        return res;
    }
    public DecreeChangeToken() {
        super();
    }
}
