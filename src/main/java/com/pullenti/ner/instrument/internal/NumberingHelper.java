/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

// Поддержка анализа нумерации
public class NumberingHelper {

    public static int calcDelta(InstrToken1 prev, InstrToken1 next, boolean canSubNumbers) {
        int n1 = prev.getLastNumber();
        int n2 = next.getLastNumber();
        if (next.getLastMinNumber() > 0) 
            n2 = next.getLastMinNumber();
        if (prev.numbers.size() == next.numbers.size()) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == next.numTyp) {
            }
            else 
                return 0;
            if (prev.numbers.size() > 1) {
                for (int i = 0; i < (prev.numbers.size() - 1); i++) {
                    if (com.pullenti.unisharp.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                        return 0;
                }
            }
            if (n1 >= n2) 
                return 0;
            return n2 - n1;
        }
        if (!canSubNumbers) 
            return 0;
        if ((prev.numbers.size() + 1) == next.numbers.size() && next.numbers.size() > 0) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.DIGIT && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else if (prev.numTyp == NumberTypes.TWODIGITS && next.numTyp == NumberTypes.THREEDIGITS) {
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.FOURDIGITS) {
            }
            else if (prev.numTyp == NumberTypes.LETTER && next.numTyp == NumberTypes.TWODIGITS && Character.isLetter(next.numbers.get(0).charAt(0))) {
            }
            else 
                return 0;
            for (int i = 0; i < prev.numbers.size(); i++) {
                if (com.pullenti.unisharp.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            return n2;
        }
        if ((prev.numbers.size() - 1) == next.numbers.size() && prev.numbers.size() > 1) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.TWODIGITS) {
                if (next.numTyp == NumberTypes.DIGIT) {
                }
                else if (next.numTyp == NumberTypes.LETTER && Character.isLetter(prev.numbers.get(0).charAt(0))) {
                }
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.THREEDIGITS) {
            }
            else 
                return 0;
            for (int i = 0; i < (prev.numbers.size() - 2); i++) {
                if (com.pullenti.unisharp.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn11710 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1711 = com.pullenti.unisharp.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 2), 0, null, wrapn11710);
            n1 = (wrapn11710.value != null ? wrapn11710.value : 0);
            if (!inoutres1711) {
                if (prev.numbers.size() == 2) 
                    n1 = prev.getFirstNumber();
                else 
                    return 0;
            }
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        if ((prev.numbers.size() - 2) == next.numbers.size() && prev.numbers.size() > 2) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.DIGIT) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else 
                return 0;
            for (int i = 0; i < (prev.numbers.size() - 3); i++) {
                if (com.pullenti.unisharp.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn11712 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1713 = com.pullenti.unisharp.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 3), 0, null, wrapn11712);
            n1 = (wrapn11712.value != null ? wrapn11712.value : 0);
            if (!inoutres1713) 
                return 0;
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        if ((prev.numbers.size() - 3) == next.numbers.size() && prev.numbers.size() > 3) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.DIGIT) {
            }
            else 
                return 0;
            for (int i = 0; i < (prev.numbers.size() - 4); i++) {
                if (com.pullenti.unisharp.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn11714 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1715 = com.pullenti.unisharp.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 4), 0, null, wrapn11714);
            n1 = (wrapn11714.value != null ? wrapn11714.value : 0);
            if (!inoutres1715) 
                return 0;
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        return 0;
    }

    public static java.util.ArrayList<InstrToken1> extractMainSequence(java.util.ArrayList<InstrToken1> lines, boolean checkSpecTexts, boolean canSubNumbers) {
        java.util.ArrayList<InstrToken1> res = null;
        int manySpecCharLines = 0;
        for (int i = 0; i < lines.size(); i++) {
            InstrToken1 li = lines.get(i);
            if (li.allUpper && li.titleTyp != InstrToken1.StdTitleType.UNDEFINED) {
                if (res != null && res.size() > 0 && res.get(res.size() - 1).tag == null) 
                    res.get(res.size() - 1).tag = li;
            }
            if (li.numbers.size() == 0) 
                continue;
            if (li.getLastNumber() == 901) {
            }
            if (li.numTyp == NumberTypes.LETTER) {
            }
            if (li.typ != InstrToken1.Types.LINE) 
                continue;
            if (res == null) {
                res = new java.util.ArrayList<InstrToken1>();
                if (li.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(li.numbers.get(0), "1") && li.numTyp == NumberTypes.DIGIT) {
                    if ((((i + 1) < lines.size()) && lines.get(i + 1).numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(lines.get(i + 1).numbers.get(0), "1")) && lines.get(i + 1).numTyp == NumberTypes.DIGIT) {
                        for (int ii = i + 2; ii < lines.size(); ii++) {
                            if (lines.get(ii).numTyp == NumberTypes.ROMAN && lines.get(ii).numbers.size() > 0) {
                                if (com.pullenti.unisharp.Utils.stringsEq(lines.get(ii).numbers.get(0), "2")) 
                                    li.numTyp = NumberTypes.ROMAN;
                                break;
                            }
                        }
                    }
                }
            }
            else {
                if (res.get(0).numSuffix != null) {
                    if (li.numSuffix != null && com.pullenti.unisharp.Utils.stringsNe(li.numSuffix, res.get(0).numSuffix)) 
                        continue;
                }
                if (res.get(0).numbers.size() != li.numbers.size()) {
                    if (li.getBeginToken().getPrevious() != null && li.getBeginToken().getPrevious().isChar(':')) 
                        continue;
                    if (res.get(0).numSuffix == null || calcDelta(res.get(res.size() - 1), li, true) != 1) 
                        continue;
                    if (!canSubNumbers) {
                        if (((i + 1) < lines.size()) && calcDelta(res.get(res.size() - 1), lines.get(i + 1), false) == 1 && calcDelta(li, lines.get(i + 1), true) == 1) {
                        }
                        else 
                            continue;
                    }
                }
                else {
                    if (res.get(0).numTyp == NumberTypes.ROMAN && li.numTyp != NumberTypes.ROMAN) 
                        continue;
                    if (res.get(0).numTyp != NumberTypes.ROMAN && li.numTyp == NumberTypes.ROMAN) {
                        if (li.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(li.numbers.get(0), "1") && res.size() == 1) {
                            res.clear();
                            res.add(li);
                            continue;
                        }
                        continue;
                    }
                    if (res.get(0).numTyp != NumberTypes.LETTER && li.numTyp == NumberTypes.LETTER) 
                        continue;
                }
            }
            res.add(li);
            if (li.hasManySpecChars) 
                manySpecCharLines++;
        }
        if (res == null) 
            return null;
        if (checkSpecTexts) {
            if (manySpecCharLines > (res.size() / 2)) 
                return null;
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (calcDelta(res.get(i), res.get(i + 1), false) == 2) {
                int ii0 = lines.indexOf(res.get(i));
                int ii1 = com.pullenti.unisharp.Utils.indexOf(lines, res.get(i + 1), ii0);
                for (int j = ii0 + 1; j < ii1; j++) {
                    if (lines.get(j).numbers.size() > 0) {
                        if (calcDelta(res.get(i), lines.get(j), true) == 1 && NumberingHelper.calcDelta(lines.get(j), res.get(i + 1), true) == 1) {
                            res.add(i + 1, lines.get(j));
                            break;
                        }
                    }
                }
            }
        }
        boolean ch = true;
        while (ch) {
            ch = false;
            for (int i = 1; i < res.size(); i++) {
                int d = calcDelta(res.get(i - 1), res.get(i), false);
                if (com.pullenti.unisharp.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i).numSuffix)) {
                    if (d == 1) 
                        continue;
                    if (((d > 1 && (d < 20))) || ((d == 0 && res.get(i - 1).numTyp == res.get(i).numTyp && res.get(i - 1).numbers.size() == res.get(i).numbers.size()))) {
                        if (calcDelta(res.get(i), res.get(i - 1), false) > 0) {
                            if (res.get(i - 1).tag != null && i > 2) {
                                for(int jjj = i + res.size() - i - 1, mmm = i; jjj >= mmm; jjj--) res.remove(jjj);
                                ch = true;
                                i--;
                                continue;
                            }
                        }
                        if ((i + 1) < res.size()) {
                            int dd = calcDelta(res.get(i), res.get(i + 1), false);
                            if (dd == 1) {
                                if (res.get(i).getLastNumber() == 1 && res.get(i).numbers.size() == res.get(i - 1).numbers.size()) {
                                }
                                else 
                                    continue;
                            }
                            else {
                                dd = calcDelta(res.get(i - 1), res.get(i + 1), false);
                                if (dd == 1) {
                                    res.remove(i);
                                    i--;
                                    ch = true;
                                    continue;
                                }
                            }
                        }
                        else if (d > 3) {
                            res.remove(i);
                            i--;
                            ch = true;
                            continue;
                        }
                        else 
                            continue;
                    }
                }
                int j;
                for (j = i + 1; j < res.size(); j++) {
                    int dd = calcDelta(res.get(j - 1), res.get(j), false);
                    if (dd != 1 && dd != 2) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsNe(res.get(j - 1).numSuffix, res.get(j).numSuffix)) 
                        break;
                }
                if ((d == 0 && calcDelta(res.get(i - 1), res.get(i), true) == 1 && res.get(i - 1).numSuffix != null) && com.pullenti.unisharp.Utils.stringsEq(res.get(i).numSuffix, res.get(i - 1).numSuffix)) 
                    d = 1;
                if (d != 1 && j > (i + 1)) {
                    for(int jjj = i + j - i - 1, mmm = i; jjj >= mmm; jjj--) res.remove(jjj);
                    i--;
                    ch = true;
                    continue;
                }
                if (d == 1) {
                    if ((i + 1) >= res.size()) 
                        continue;
                    int dd = calcDelta(res.get(i), res.get(i + 1), false);
                    if (dd == 1 && com.pullenti.unisharp.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i + 1).numSuffix)) {
                        if (com.pullenti.unisharp.Utils.stringsNe(res.get(i).numSuffix, res.get(i - 1).numSuffix)) {
                            res.get(i).numSuffix = res.get(i - 1).numSuffix;
                            res.get(i).isNumDoubt = false;
                            ch = true;
                        }
                        continue;
                    }
                }
                if ((i + 1) < res.size()) {
                    int dd = calcDelta(res.get(i - 1), res.get(i + 1), false);
                    if (dd == 1 && com.pullenti.unisharp.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i + 1).numSuffix)) {
                        if (d == 1 && calcDelta(res.get(i), res.get(i + 1), true) == 1) {
                        }
                        else {
                            res.remove(i);
                            ch = true;
                            continue;
                        }
                    }
                }
                else if (d == 0 || d > 10 || com.pullenti.unisharp.Utils.stringsNe(res.get(i - 1).numSuffix, res.get(i).numSuffix)) {
                    res.remove(i);
                    ch = true;
                    continue;
                }
            }
        }
        int hasSuf = 0;
        for (InstrToken1 r : res) {
            if ((r.numSuffix != null || r.getTypContainerRank() > 0 || r.numbers.size() > 1) || r.allUpper || r.numTyp == NumberTypes.ROMAN) 
                hasSuf++;
        }
        if (hasSuf == 0) {
            if (res.size() < 5) 
                return null;
        }
        if (res.size() >= 2) {
            if (res.get(0) != lines.get(0)) {
                int tot = res.get(0).getBeginToken().getBeginChar() - lines.get(0).getBeginToken().getBeginChar();
                tot += (lines.get(lines.size() - 1).getEndToken().getEndChar() - res.get(res.size() - 1).getEndToken().getEndChar());
                int blk = res.get(res.size() - 1).getEndToken().getEndChar() - res.get(0).getBeginToken().getBeginChar();
                int i = lines.indexOf(res.get(res.size() - 1));
                if (i > 0) {
                    java.util.ArrayList<InstrToken1> lines1 = new java.util.ArrayList<InstrToken1>(lines);
                    for(int jjj = 0 + i + 1 - 1, mmm = 0; jjj >= mmm; jjj--) lines1.remove(jjj);
                    java.util.ArrayList<InstrToken1> res1 = extractMainSequence(lines1, checkSpecTexts, canSubNumbers);
                    if (res1 != null && res1.size() > 2) 
                        blk += (res1.get(res1.size() - 1).getEndChar() - res1.get(0).getBeginChar());
                }
                if ((blk * 3) < tot) {
                    if ((blk * 5) < tot) 
                        return null;
                    for (InstrToken1 r : res) {
                        if (!r.allUpper && !r.getHasChanges()) 
                            return null;
                    }
                }
            }
            if (res.get(0).getLastNumber() == 1 && res.get(0).numbers.size() == 1) {
                java.util.ArrayList<InstrToken1> res0 = new java.util.ArrayList<InstrToken1>();
                res0.add(res.get(0));
                int i;
                for (i = 1; i < res.size(); i++) {
                    int j;
                    for (j = i + 1; j < res.size(); j++) {
                        if (res.get(j).getLastNumber() == 1 && res.get(j).numbers.size() == 1) 
                            break;
                    }
                    if ((j - i) < 3) 
                        break;
                    j--;
                    int jj;
                    int errs = 0;
                    for (jj = i + 1; jj < j; jj++) {
                        int d = calcDelta(res.get(jj - 1), res.get(jj), false);
                        if (d == 1) {
                        }
                        else if (d > 1 && (d < 3)) 
                            errs++;
                        else 
                            break;
                    }
                    if ((jj < j) || errs > 1) 
                        break;
                    if (j < (res.size() - 1)) {
                        if (calcDelta(res0.get(res0.size() - 1), res.get(j), false) != 1) 
                            break;
                        res0.add(res.get(j));
                    }
                    i = j;
                }
                if (i >= res.size() && res0.size() > 1) 
                    return res0;
            }
            if (res.size() > 500) 
                return null;
            return res;
        }
        if (res.size() == 1 && lines.get(0) == res.get(0)) {
            if (hasSuf > 0) 
                return res;
            if (lines.size() > 1 && lines.get(1).numbers.size() == (lines.get(0).numbers.size() + 1)) {
                for (int i = 0; i < lines.get(0).numbers.size(); i++) {
                    if (com.pullenti.unisharp.Utils.stringsNe(lines.get(1).numbers.get(i), lines.get(0).numbers.get(i))) 
                        return null;
                }
                return res;
            }
        }
        return null;
    }

    public static void createNumber(FragToken owner, InstrToken1 itok) {
        if (itok.numBeginToken == null || itok.numEndToken == null) 
            return;
        FragToken num = FragToken._new1716(itok.numBeginToken, itok.numEndToken, com.pullenti.ner.instrument.InstrumentKind.NUMBER, true, itok);
        owner.children.add(num);
        if (itok.numTyp == NumberTypes.TWODIGITS) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = itok.getLastNumber();
        }
        else if (itok.numTyp == NumberTypes.THREEDIGITS) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = itok.getMiddleNumber();
            owner.subNumber2 = itok.getLastNumber();
        }
        else if (itok.numTyp == NumberTypes.FOURDIGITS && itok.numbers.size() == 4) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = com.pullenti.ner.decree.internal.PartToken.getNumber(itok.numbers.get(1));
            owner.subNumber2 = com.pullenti.ner.decree.internal.PartToken.getNumber(itok.numbers.get(2));
            owner.subNumber3 = itok.getLastNumber();
        }
        else 
            owner.number = itok.getLastNumber();
        owner.minNumber = itok.getLastMinNumber();
        owner.itok = itok;
    }

    public static void parseNumber(com.pullenti.ner.Token t, InstrToken1 res, InstrToken1 prev) {
        _parseNumber(t, res, prev);
        if ((res.numbers.size() > 0 && res.numEndToken != null && !res.isNewlineAfter()) && res.numEndToken.getNext() != null && res.numEndToken.getNext().isHiphen()) {
            InstrToken1 res1 = new InstrToken1(res.numEndToken.getNext().getNext(), res.numEndToken.getNext().getNext());
            _parseNumber(res1.getBeginToken(), res1, res);
            if (res1.numbers.size() == res.numbers.size()) {
                int i;
                for (i = 0; i < (res.numbers.size() - 1); i++) {
                    if (com.pullenti.unisharp.Utils.stringsNe(res.numbers.get(i), res1.numbers.get(i))) 
                        break;
                }
                if (i >= (res.numbers.size() - 1) && (res.getLastNumber() < res1.getLastNumber()) && res1.numEndToken != null) {
                    res.minNumber = res.numbers.get(res.numbers.size() - 1);
                    com.pullenti.unisharp.Utils.putArrayValue(res.numbers, res.numbers.size() - 1, res1.numbers.get(res.numbers.size() - 1));
                    res.numSuffix = res1.numSuffix;
                    res.setEndToken((res.numEndToken = res1.numEndToken));
                }
            }
        }
        if (res.numbers.size() > 0 && res.numEndToken != null && res.typ == InstrToken1.Types.LINE) {
            com.pullenti.ner.Token tt = res.numEndToken;
            boolean ok = true;
            if (tt.getNext() != null && tt.getNext().isHiphen()) 
                ok = false;
            else if (!tt.isWhitespaceAfter()) {
                if (tt.getNext() != null && ((tt.getNext().chars.isCapitalUpper() || tt.getNext().chars.isAllUpper() || (tt.getNext() instanceof com.pullenti.ner.ReferentToken)))) {
                }
                else 
                    ok = false;
            }
            if (!ok) {
                res.numbers.clear();
                res.numEndToken = (res.numBeginToken = null);
            }
        }
    }

    private static void _parseNumber(com.pullenti.ner.Token t, InstrToken1 res, InstrToken1 prev) {
        if (((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() < 3000)) {
            if (res.numbers.size() >= 4) {
            }
            if (t.getMorph()._getClass().isAdjective() && res.getTypContainerRank() == 0) 
                return;
            com.pullenti.ner.core.NumberExToken nwp = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
            if (nwp != null) {
                if (nwp.getEndToken().isWhitespaceBefore()) {
                }
                else 
                    return;
            }
            if ((t.getNext() != null && (t.getWhitespacesAfterCount() < 3) && t.getNext().chars.isLetter()) && t.getNext().chars.isAllLower()) {
                if (!t.isWhitespaceAfter() && t.getNext().getLengthChar() == 1) {
                }
                else if (res.numbers.size() == 0) {
                    res.numTyp = NumberTypes.DIGIT;
                    res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    res.numBeginToken = (res.numEndToken = res.setEndToken(t));
                    return;
                }
                else 
                    return;
            }
            if (res.numTyp == NumberTypes.UNDEFINED) 
                res.numTyp = NumberTypes.DIGIT;
            else 
                res.numTyp = NumberTypes.COMBO;
            if (res.numbers.size() > 0 && t.isWhitespaceBefore()) 
                return;
            if (res.numbers.size() == 0) 
                res.numBeginToken = t;
            if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() > ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue()) {
                res.minNumber = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString();
                t = t.getNext().getNext();
            }
            else if (((t.getNext() != null && t.getNext().isCharOf(")") && t.getNext().getNext() != null) && t.getNext().getNext().isHiphen() && (t.getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() > ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue()) {
                res.minNumber = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString();
                t = t.getNext().getNext().getNext();
            }
            res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            res.setEndToken((res.numEndToken = t));
            res.numSuffix = null;
            for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null && (res.numbers.size() < 4); ttt = ttt.getNext()) {
                boolean ok1 = false;
                boolean ok2 = false;
                if ((ttt.isCharOf("._") && !ttt.isWhitespaceAfter() && (ttt.getNext() instanceof com.pullenti.ner.NumberToken)) && ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT || ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS) && ttt.getNext().chars.isLatinLetter() && !ttt.isWhitespaceAfter())))) 
                    ok1 = true;
                else if ((ttt.isCharOf("(<") && (ttt.getNext() instanceof com.pullenti.ner.NumberToken) && ttt.getNext().getNext() != null) && ttt.getNext().getNext().isCharOf(")>")) 
                    ok2 = true;
                if (ok1 || ok2) {
                    ttt = ttt.getNext();
                    res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    res.numTyp = (res.numbers.size() == 2 ? NumberTypes.TWODIGITS : (res.numbers.size() == 3 ? NumberTypes.THREEDIGITS : NumberTypes.FOURDIGITS));
                    if ((ttt.getNext() != null && ttt.getNext().isCharOf(")>") && ttt.getNext().getNext() != null) && ttt.getNext().getNext().isChar('.')) 
                        ttt = ttt.getNext();
                    else if (ok2) 
                        ttt = ttt.getNext();
                    t = res.setEndToken((res.numEndToken = ttt));
                    continue;
                }
                if (((ttt instanceof com.pullenti.ner.TextToken) && ttt.getLengthChar() == 1 && ttt.chars.isLetter()) && !ttt.isWhitespaceBefore() && res.numbers.size() == 1) {
                    res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.TextToken.class)).term);
                    res.numTyp = NumberTypes.COMBO;
                    t = res.setEndToken((res.numEndToken = ttt));
                    continue;
                }
                break;
            }
            if (t.getNext() != null && t.getNext().isCharOf(").")) {
                res.numSuffix = t.getNext().getSourceText();
                t = res.setEndToken((res.numEndToken = t.getNext()));
            }
            return;
        }
        if (((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS && res.getTypContainerRank() > 0) && res.numbers.size() == 0) {
            res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            res.numTyp = NumberTypes.DIGIT;
            res.numBeginToken = t;
            if (t.getNext() != null && t.getNext().isChar('.')) {
                t = t.getNext();
                res.numSuffix = ".";
            }
            res.setEndToken((res.numEndToken = t));
            return;
        }
        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
        if ((nt != null && com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "10") && t.getNext() != null) && t.getNext().isChar(')')) 
            nt = null;
        if (nt != null && com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "100")) 
            nt = null;
        if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.ROMAN) {
            if (res.numTyp == NumberTypes.UNDEFINED) 
                res.numTyp = NumberTypes.ROMAN;
            else 
                res.numTyp = NumberTypes.COMBO;
            if (res.numbers.size() > 0 && t.isWhitespaceBefore()) 
                return;
            if (res.numbers.size() == 0) 
                res.numBeginToken = t;
            res.numbers.add(nt.getValue().toString());
            t = res.setEndToken((res.numEndToken = nt.getEndToken()));
            if (res.numTyp == NumberTypes.ROMAN && ((res.typ == InstrToken1.Types.CHAPTER || res.typ == InstrToken1.Types.SECTION || res.typ == InstrToken1.Types.LINE))) {
                if ((t.getNext() != null && t.getNext().isCharOf("._<") && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    t = t.getNext().getNext();
                    res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    res.numTyp = NumberTypes.TWODIGITS;
                    if (t.getNext() != null && t.getNext().isChar('>')) 
                        t = t.getNext();
                    res.setEndToken((res.numEndToken = t));
                    if ((t.getNext() != null && t.getNext().isCharOf("._<") && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                        t = t.getNext().getNext();
                        res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        res.numTyp = NumberTypes.THREEDIGITS;
                        if (t.getNext() != null && t.getNext().isChar('>')) 
                            t = t.getNext();
                        res.setEndToken((res.numEndToken = t));
                    }
                }
            }
            if (t.getNext() != null && t.getNext().isCharOf(").")) {
                res.numSuffix = t.getNext().getSourceText();
                t = res.setEndToken((res.numEndToken = t.getNext()));
            }
            return;
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.chars.isLetter()) && t == res.getBeginToken()) {
            if ((!t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext() != null) && t.getNext().getNext().isChar('.')) {
                res.numBeginToken = t;
                res.numTyp = NumberTypes.DIGIT;
                res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                res.numSuffix = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term + ".";
                t = res.setEndToken((res.numEndToken = t.getNext().getNext()));
                return;
            }
            if (t.getNext() != null && t.getNext().isCharOf(".)")) {
                if (((t.getNext().isChar('.') && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(')') && !t.getNext().isWhitespaceAfter()) && !t.getNext().getNext().isWhitespaceAfter()) {
                    res.numTyp = NumberTypes.TWODIGITS;
                    res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                    res.numSuffix = ")";
                    res.numBeginToken = t;
                    t = res.setEndToken((res.numEndToken = t.getNext().getNext().getNext()));
                    return;
                }
                if (t.getNext().isChar('.') && ((t.chars.isAllUpper() || (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)))) {
                }
                else {
                    InstrToken1 tmp1 = new InstrToken1(t, t.getNext());
                    tmp1.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    if (tmp1.getLastNumber() > 1 && t.getNext().isCharOf(".") && ((prev == null || (prev.getLastNumber() + 1) != tmp1.getLastNumber()))) {
                    }
                    else {
                        if (res.numbers.size() == 0) 
                            res.numBeginToken = t;
                        res.numTyp = NumberTypes.LETTER;
                        res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                        res.numBeginToken = t;
                        t = res.setEndToken((res.numEndToken = t.getNext()));
                        res.numSuffix = t.getSourceText();
                        return;
                    }
                }
            }
        }
    }

    public static boolean correctChildNumbers(FragToken root, java.util.ArrayList<FragToken> children) {
        boolean hasNum = false;
        if (root.number > 0) {
            for (FragToken ch : root.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER) {
                    hasNum = true;
                    break;
                }
                else if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.KEYWORD) 
                    break;
            }
        }
        if (!hasNum) 
            return false;
        if (root.subNumber == 0) {
            boolean ok = true;
            for (FragToken ch : children) {
                if (ch.number > 0) {
                    if (ch.number == root.number && ch.subNumber > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for (FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber;
                        ch.subNumber = ch.subNumber2;
                        ch.subNumber2 = ch.subNumber3;
                        ch.subNumber3 = 0;
                    }
                }
            }
            return ok;
        }
        if (root.subNumber > 0 && root.subNumber2 == 0) {
            boolean ok = true;
            for (FragToken ch : children) {
                if (ch.number > 0) {
                    if (ch.number == root.number && ch.subNumber == root.subNumber && ch.subNumber2 > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for (FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber2;
                        ch.subNumber = ch.subNumber3;
                        ch.subNumber2 = (ch.subNumber3 = 0);
                    }
                }
            }
            return ok;
        }
        if (root.subNumber > 0 && root.subNumber2 > 0 && root.subNumber3 == 0) {
            boolean ok = true;
            for (FragToken ch : children) {
                if (ch.number > 0) {
                    if ((ch.number == root.number && ch.subNumber == root.subNumber && ch.subNumber2 == root.subNumber2) && ch.subNumber3 > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for (FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber3;
                        ch.subNumber = (ch.subNumber2 = (ch.subNumber3 = 0));
                    }
                }
            }
            return ok;
        }
        return false;
    }

    public static java.util.ArrayList<String> createDiap(String s1, String s2) {
        int n1;
        int n2;
        int i;
        String pref = null;
        if (s2.startsWith(s1)) {
            i = s1.length();
            if (((i + 1) < s2.length()) && s2.charAt(i) == '.' && Character.isDigit(s2.charAt(i + 1))) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrapn21717 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres1718 = com.pullenti.unisharp.Utils.parseInteger(s2.substring(i + 1), 0, null, wrapn21717);
                n2 = (wrapn21717.value != null ? wrapn21717.value : 0);
                if (inoutres1718) {
                    java.util.ArrayList<String> res0 = new java.util.ArrayList<String>();
                    res0.add(s1);
                    for (i = 1; i <= n2; i++) {
                        res0.add((s1 + "." + i));
                    }
                    return res0;
                }
            }
        }
        if ((((i = s1.lastIndexOf('.')))) > 0) {
            pref = s1.substring(0, 0 + i + 1);
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn11721 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1722 = com.pullenti.unisharp.Utils.parseInteger(s1.substring(i + 1), 0, null, wrapn11721);
            n1 = (wrapn11721.value != null ? wrapn11721.value : 0);
            if (!inoutres1722) 
                return null;
            if (!s2.startsWith(pref)) 
                return null;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn21719 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1720 = com.pullenti.unisharp.Utils.parseInteger(s2.substring(i + 1), 0, null, wrapn21719);
            n2 = (wrapn21719.value != null ? wrapn21719.value : 0);
            if (!inoutres1720) 
                return null;
        }
        else {
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn11725 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1726 = com.pullenti.unisharp.Utils.parseInteger(s1, 0, null, wrapn11725);
            n1 = (wrapn11725.value != null ? wrapn11725.value : 0);
            if (!inoutres1726) 
                return null;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapn21723 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1724 = com.pullenti.unisharp.Utils.parseInteger(s2, 0, null, wrapn21723);
            n2 = (wrapn21723.value != null ? wrapn21723.value : 0);
            if (!inoutres1724) 
                return null;
        }
        if (n2 <= n1) 
            return null;
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (i = n1; i <= n2; i++) {
            if (pref == null) 
                res.add(((Integer)i).toString());
            else 
                res.add(pref + (((Integer)i).toString()));
        }
        return res;
    }
    public NumberingHelper() {
    }
}
