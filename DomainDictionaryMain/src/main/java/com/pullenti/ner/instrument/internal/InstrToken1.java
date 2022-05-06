/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class InstrToken1 extends com.pullenti.ner.MetaToken {

    public InstrToken1(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        if(_globalInstance == null) return;
        typ = Types.LINE;
        titleTyp = StdTitleType.UNDEFINED;
    }

    public com.pullenti.ner.instrument.InstrumentReferent iRef;

    public boolean isExpired;

    public java.util.ArrayList<String> numbers = new java.util.ArrayList<String>();

    public String minNumber;

    public NumberTypes numTyp = NumberTypes.UNDEFINED;

    public String numSuffix;

    public com.pullenti.ner.Token numBeginToken;

    public com.pullenti.ner.Token numEndToken;

    public boolean isNumDoubt = false;

    public int getLastNumber() {
        if (numbers.size() < 1) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(numbers.size() - 1));
    }


    public int getFirstNumber() {
        if (numbers.size() < 1) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(0));
    }


    public int getMiddleNumber() {
        if (numbers.size() < 2) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(1));
    }


    public int getLastMinNumber() {
        if (minNumber == null) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(minNumber);
    }


    public boolean getHasChanges() {
        for (com.pullenti.ner.Token t = (numEndToken != null ? numEndToken : getBeginToken()); t != null; t = t.getNext()) {
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) 
                return true;
            if (t.getEndChar() > getEndChar()) 
                break;
        }
        return false;
    }


    public Types typ = Types.LINE;

    public java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> signValues = new java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken>();

    public String value;

    public boolean allUpper;

    public boolean hasVerb;

    public boolean hasManySpecChars;

    public StdTitleType titleTyp = StdTitleType.UNDEFINED;

    public boolean indexNoKeyword = false;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append(numTyp.toString()).append(" ");
        if (isNumDoubt) 
            res.append("(?) ");
        if (isExpired) 
            res.append("(Expired) ");
        if (getHasChanges()) 
            res.append("(HasChanges) ");
        for (int i = 0; i < numbers.size(); i++) {
            res.append((i > 0 ? "." : "")).append(numbers.get(i));
        }
        if (numSuffix != null) 
            res.append(" Suf='").append(numSuffix).append("'");
        if (value != null) 
            res.append(" '").append(value).append("'");
        for (com.pullenti.ner.decree.internal.DecreeToken s : signValues) {
            res.append(" [").append(s.toString()).append("]");
        }
        if (allUpper) 
            res.append(" AllUpper");
        if (hasVerb) 
            res.append(" HasVerb");
        if (hasManySpecChars) 
            res.append(" HasManySpecChars");
        if (titleTyp != StdTitleType.UNDEFINED) 
            res.append(" ").append(titleTyp.toString());
        if (value == null) 
            res.append(": ").append(this.getSourceText());
        return res.toString();
    }

    public static InstrToken1 parse(com.pullenti.ner.Token t, boolean ignoreDirectives, FragToken cur, int lev, InstrToken1 prev, boolean isCitat, int maxChar, boolean canBeTableCell, boolean isInIndex) {
        if (t == null) 
            return null;
        if (lev > 30) 
            return null;
        if (t.isCharOf("({")) {
            InstrToken1 edt = null;
            FragToken fr = FragToken._createEditions(t);
            if (fr != null) 
                edt = _new1690(fr.getBeginToken(), fr.getEndToken(), Types.EDITIONS);
            else {
                com.pullenti.ner.Token t2 = _createEdition(t);
                if (t2 != null) 
                    edt = _new1690(t, t2, Types.EDITIONS);
            }
            if (edt != null) {
                if (edt.getEndToken().getNext() != null && edt.getEndToken().getNext().isChar('.')) 
                    edt.setEndToken(edt.getEndToken().getNext());
                return edt;
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t00 = null;
        InstrToken1 res = _new1692(t0, t, true);
        for (; t != null; t = (t == null ? null : t.getNext())) {
            if (!t.isTableControlChar()) 
                break;
            else {
                if (t.isChar((char)0x1E)) {
                    boolean isTable = false;
                    java.util.ArrayList<com.pullenti.ner.core.TableRowToken> rows = com.pullenti.ner.core.TableHelper.tryParseRows(t, 0, true);
                    if (rows != null && rows.size() > 0) {
                        isTable = true;
                        if (rows.get(0).cells.size() > 2 || rows.get(0).cells.size() == 0) {
                        }
                        else if (lev >= 10) 
                            isTable = false;
                        else {
                            InstrToken1 it11 = parse(rows.get(0).getBeginToken(), true, null, lev + 1, null, false, maxChar, canBeTableCell, false);
                            if (canBeTableCell) {
                                if (it11 != null) 
                                    return it11;
                            }
                            if (it11 != null && it11.numbers.size() > 0) {
                                if (it11.getTypContainerRank() > 0 || it11.getLastNumber() == 1 || it11.titleTyp != StdTitleType.UNDEFINED) 
                                    isTable = false;
                            }
                        }
                    }
                    if (isTable) {
                        int le = 1;
                        for (t = t.getNext(); t != null; t = t.getNext()) {
                            if (t.isChar((char)0x1E)) 
                                le++;
                            else if (t.isChar((char)0x1F)) {
                                if ((--le) == 0) {
                                    res.setEndToken(t);
                                    res.hasVerb = true;
                                    res.allUpper = false;
                                    return res;
                                }
                            }
                        }
                    }
                }
                if (t != null) 
                    res.setEndToken(t);
            }
        }
        if (t == null) {
            if (t0 instanceof com.pullenti.ner.TextToken) 
                return null;
            t = res.getEndToken();
        }
        com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
        if (dt == null && (((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)))) {
            dt = com.pullenti.ner.decree.internal.DecreeToken._new906(t, t, com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER);
            dt.ref = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        }
        if (dt != null && dt.getEndToken().isNewlineAfter()) {
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                res.typ = Types.SIGNS;
                res.signValues.add(dt);
                res.setEndToken(dt.getEndToken());
                res.allUpper = false;
                return res;
            }
        }
        if (t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК") && t.getMorph().getCase().isNominative() && t.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
            if (t.getNext() != null && ((t.getNext().isValue("В", null) || t.getNext().isChar(':')))) {
            }
            else {
                res.typ = Types.APPENDIX;
                if (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) 
                    t = t.kit.debedToken(t);
                for (t = t.getNext(); t != null; t = t.getNext()) {
                    if (res.numEndToken == null) {
                        com.pullenti.ner.Token ttt = (com.pullenti.ner.Token)com.pullenti.unisharp.Utils.notnull(com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t), t);
                        NumberingHelper.parseNumber(ttt, res, prev);
                        if (res.numEndToken != null) {
                            res.setEndToken((t = res.numEndToken));
                            continue;
                        }
                    }
                    dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                    if (dt != null) {
                        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                            res.numBeginToken = dt.getBeginToken();
                            res.numEndToken = dt.getEndToken();
                            if (dt.value != null) 
                                res.numbers.add(dt.value.toUpperCase());
                        }
                        t = res.setEndToken(dt.getEndToken());
                        continue;
                    }
                    if ((t instanceof com.pullenti.ner.NumberToken) && ((t.isNewlineAfter() || ((t.getNext() != null && t.getNext().isChar('.') && t.getNext().isNewlineAfter()))))) {
                        res.numBeginToken = t;
                        res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        if (t.getNext() != null && t.getNext().isChar('.')) 
                            t = t.getNext();
                        res.numEndToken = t;
                        res.setEndToken(t);
                        continue;
                    }
                    if (((t instanceof com.pullenti.ner.NumberToken) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getLengthChar() == 1) && ((t.getNext().isNewlineAfter() || ((t.getNext().getNext() != null && t.getNext().getNext().isChar('.')))))) {
                        res.numBeginToken = t;
                        res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                        res.numTyp = NumberTypes.COMBO;
                        t = t.getNext();
                        if (t.getNext() != null && t.getNext().isChar('.')) 
                            t = t.getNext();
                        res.numEndToken = t;
                        res.setEndToken(t);
                        continue;
                    }
                    if (res.numEndToken == null) {
                        NumberingHelper.parseNumber(t, res, prev);
                        if (res.numEndToken != null) {
                            res.setEndToken((t = res.numEndToken));
                            continue;
                        }
                    }
                    if (t.isValue("К", "ДО") && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                        break;
                    if (t.chars.isLetter()) {
                        com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                        if (lat != null && !t.isValue("C", null) && !t.isValue("С", null)) {
                            res.numBeginToken = t;
                            res.numbers.add(lat.getValue().toString());
                            res.numTyp = NumberTypes.ROMAN;
                            t = lat.getEndToken();
                            if (t.getNext() != null && ((t.getNext().isChar('.') || t.getNext().isChar(')')))) 
                                t = t.getNext();
                            res.numEndToken = t;
                            res.setEndToken(t);
                            continue;
                        }
                        if (t.getLengthChar() == 1 && t.chars.isAllUpper() && (t instanceof com.pullenti.ner.TextToken)) {
                            res.numBeginToken = t;
                            res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                            res.numTyp = NumberTypes.LETTER;
                            if (t.getNext() != null && ((t.getNext().isChar('.') || t.getNext().isChar(')')))) 
                                t = t.getNext();
                            res.numEndToken = t;
                            res.setEndToken(t);
                            continue;
                        }
                    }
                    if (InstrToken._checkEntered(t) != null) 
                        break;
                    if (t instanceof com.pullenti.ner.TextToken) {
                        if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) {
                            res.typ = Types.LINE;
                            break;
                        }
                    }
                    break;
                }
                if (res.typ != Types.LINE) 
                    return res;
            }
        }
        if (t.isNewlineBefore()) {
            if (t.isValue("МНЕНИЕ", "ДУМКА") || ((t.isValue("ОСОБОЕ", "ОСОБЛИВА") && t.getNext() != null && t.getNext().isValue("МНЕНИЕ", "ДУМКА")))) {
                com.pullenti.ner.Token t1 = t.getNext();
                if (t1 != null && t1.isValue("МНЕНИЕ", "ДУМКА")) 
                    t1 = t1.getNext();
                boolean ok = false;
                if (t1 != null) {
                    if (t1.isNewlineBefore() || (t1.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) 
                        ok = true;
                }
                if (ok) {
                    res.typ = Types.APPENDIX;
                    res.setEndToken(t1.getPrevious());
                    return res;
                }
            }
            if ((t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                res.typ = Types.APPROVED;
        }
        if (t.isValue("КОНСУЛЬТАНТПЛЮС", null) || t.isValue("ГАРАНТ", null) || ((t.isValue("ИНФОРМАЦИЯ", null) && t.isNewlineBefore()))) {
            com.pullenti.ner.Token t1 = t.getNext();
            boolean ok = false;
            if (t.isValue("ИНФОРМАЦИЯ", null)) {
                if (((t.getNext() != null && t.getNext().isValue("О", null) && t.getNext().getNext() != null) && t.getNext().getNext().isValue("ИЗМЕНЕНИЕ", null) && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(':')) {
                    t1 = t.getNext().getNext().getNext().getNext();
                    ok = true;
                }
            }
            else if (t1 != null && t1.isChar(':')) {
                t1 = t1.getNext();
                ok = true;
            }
            if (t1 != null && ((t1.isValue("ПРИМЕЧАНИЕ", null) || ok))) {
                if (t1.getNext() != null && t1.getNext().isChar('.')) 
                    t1 = t1.getNext();
                InstrToken1 re = _new1690(t, t1, Types.COMMENT);
                boolean hiph = false;
                for (t1 = t1.getNext(); t1 != null; t1 = t1.getNext()) {
                    re.setEndToken(t1);
                    if (!t1.isNewlineAfter()) 
                        continue;
                    if (t1.getNext() == null) 
                        break;
                    if (t1.getNext().isValue("СМ", null) || t1.getNext().isValue("ПУНКТ", null)) 
                        continue;
                    if (!t1.getNext().isHiphen()) 
                        hiph = false;
                    else if (t1.getNext().isHiphen()) {
                        if (t1.isChar(':')) 
                            hiph = true;
                        if (hiph) 
                            continue;
                    }
                    break;
                }
                return re;
            }
        }
        int checkComment = 0;
        for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
            if (((ttt.isNewlineBefore() || ttt.isTableControlChar())) && ttt != t) 
                break;
            if (ttt.getMorph()._getClass().isPreposition()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null) 
                break;
            if (npt.noun.isValue("ПРИМЕНЕНИЕ", "ЗАСТОСУВАННЯ") || npt.noun.isValue("ВОПРОС", "ПИТАННЯ")) {
                checkComment++;
                ttt = npt.getEndToken();
            }
            else 
                break;
        }
        if (checkComment > 0 || t.isValue("О", "ПРО")) {
            com.pullenti.ner.Token t1 = null;
            boolean ok = false;
            com.pullenti.ner.decree.DecreeReferent dref = null;
            for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null; ttt = ttt.getNext()) {
                t1 = ttt;
                if (t1.isValue("СМ", null) && t1.getNext() != null && t1.getNext().isChar('.')) {
                    if (checkComment > 0) 
                        ok = true;
                    if ((t1.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && (((t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) || (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)))) {
                        ok = true;
                        dref = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext().getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                    }
                }
                if (ttt.isNewlineAfter()) 
                    break;
            }
            if (ok) {
                InstrToken1 cmt = _new1690(t, t1, Types.COMMENT);
                if (dref != null && t1.getNext() != null && t1.getNext().getReferent() == dref) {
                    if (t1.getNext().getNext() != null && t1.getNext().getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) {
                        for (com.pullenti.ner.Token ttt = t1.getNext().getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (ttt.isNewlineBefore()) 
                                break;
                            cmt.setEndToken(ttt);
                        }
                    }
                }
                return cmt;
            }
        }
        com.pullenti.ner.Token tt = InstrToken._checkApproved(t);
        if (tt != null) {
            res.setEndToken(tt);
            if (tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                res.typ = Types.APPROVED;
                res.setEndToken(tt.getNext());
                return res;
            }
            com.pullenti.ner.Token tt1 = tt;
            if (tt1.isChar(':') && tt1.getNext() != null) 
                tt1 = tt1.getNext();
            if ((tt1.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (tt1.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)) {
                res.typ = Types.APPROVED;
                res.setEndToken(tt1);
                return res;
            }
            com.pullenti.ner.decree.internal.DecreeToken dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt.getNext(), null, false);
            if (dt1 != null && dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                res.typ = Types.APPROVED;
                int err = 0;
                for (com.pullenti.ner.Token ttt = dt1.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(ttt, false) != null) 
                        break;
                    dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt, null, false);
                    if (dt1 != null) {
                        if (dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) 
                            break;
                        res.setEndToken((ttt = dt1.getEndToken()));
                        continue;
                    }
                    if (ttt.getMorph()._getClass().isPreposition() || ttt.getMorph()._getClass().isConjunction()) 
                        continue;
                    if (ttt.getWhitespacesBeforeCount() > 15) 
                        break;
                    if ((++err) > 10) 
                        break;
                }
                return res;
            }
        }
        String val = null;
        com.pullenti.unisharp.Outargwrapper<String> wrapval1701 = new com.pullenti.unisharp.Outargwrapper<String>();
        com.pullenti.ner.Token tt2 = _checkDirective(t, wrapval1701);
        val = wrapval1701.value;
        if (tt2 != null) {
            if (tt2.isNewlineAfter() || ((tt2.getNext() != null && ((tt2.getNext().isCharOf(":") || ((tt2.getNext().isChar('.') && tt2 != t)))) && ((tt2.getNext().isNewlineAfter() || t.chars.isAllUpper()))))) 
                return _new1696(t, (tt2.isNewlineAfter() ? tt2 : tt2.getNext()), Types.DIRECTIVE, val);
        }
        if ((lev < 3) && t != null) {
            if ((t.isValue("СОДЕРЖИМОЕ", "ВМІСТ") || t.isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || t.isValue("ОГЛАВЛЕНИЕ", "ЗМІСТ")) || ((t.isValue("СПИСОК", null) && t.getNext() != null && t.getNext().isValue("РАЗДЕЛ", null)))) {
                com.pullenti.ner.Token t11 = t.getNext();
                if (t.isValue("СПИСОК", null)) 
                    t11 = t11.getNext();
                if (t11 != null && !t11.isNewlineAfter()) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getMorph().getCase().isGenitive()) 
                        t11 = npt.getEndToken().getNext();
                }
                if (t11 != null && t11.isCharOf(":.;")) 
                    t11 = t11.getNext();
                if (t11 != null && t11.isNewlineBefore()) {
                    InstrToken1 first = parse(t11, ignoreDirectives, null, lev + 1, null, false, 0, false, true);
                    if (first != null && (first.getLengthChar() < 4)) 
                        first = parse(first.getEndToken().getNext(), ignoreDirectives, null, lev + 1, null, false, 0, false, false);
                    String fstr = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(first, com.pullenti.ner.core.GetTextAttr.NO);
                    if (first != null) {
                        int cou = 0;
                        InstrToken1 itprev = null;
                        boolean hasApp = false;
                        for (tt = first.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                            if (tt.isValue("ПРИЛОЖЕНИЕ", null)) {
                            }
                            if (tt.isNewlineBefore()) {
                                if ((++cou) > 400) 
                                    break;
                            }
                            InstrToken1 it = parse(tt, ignoreDirectives, null, lev + 1, null, false, 0, false, true);
                            if (it == null) 
                                break;
                            boolean ok = false;
                            if (first.numbers.size() == 1 && it.numbers.size() == 1) {
                                if (it.typ == Types.APPENDIX && first.typ != Types.APPENDIX) {
                                }
                                else if (com.pullenti.unisharp.Utils.stringsEq(first.numbers.get(0), it.numbers.get(0))) 
                                    ok = true;
                            }
                            else if (first.value != null && it.value != null && first.value.startsWith(it.value)) 
                                ok = true;
                            else {
                                String str = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(it, com.pullenti.ner.core.GetTextAttr.NO);
                                if (com.pullenti.unisharp.Utils.stringsEq(str, fstr)) 
                                    ok = true;
                            }
                            if ((ok && first.typ != Types.APPENDIX && itprev != null) && itprev.typ == Types.APPENDIX) {
                                InstrToken1 it2 = parse(it.getEndToken().getNext(), ignoreDirectives, null, lev + 1, null, false, 0, false, true);
                                if (it2 != null && it2.typ == Types.APPENDIX) 
                                    ok = false;
                            }
                            if (!ok && cou > 4 && first.numbers.size() > 0) {
                                if (it.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(it.numbers.get(0), "1")) {
                                    if (it.titleTyp == StdTitleType.OTHERS) 
                                        ok = true;
                                }
                            }
                            if (ok) {
                                if (t.getPrevious() == null) 
                                    return null;
                                res.setEndToken(tt.getPrevious());
                                res.typ = Types.INDEX;
                                return res;
                            }
                            if (it.typ == Types.APPENDIX) 
                                hasApp = true;
                            tt = it.getEndToken();
                            itprev = it;
                        }
                        cou = 0;
                        for (tt = first.getBeginToken(); tt != null && tt.getEndChar() <= first.getEndChar(); tt = tt.getNext()) {
                            if (tt.isTableControlChar()) 
                                cou++;
                        }
                        if (cou > 5) {
                            res.setEndToken(first.getEndToken());
                            res.typ = Types.INDEX;
                            return res;
                        }
                    }
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> pts = (t == null ? null : com.pullenti.ner.decree.internal.PartToken.tryAttachList((t.isValue("ПОЛОЖЕНИЕ", "ПОЛОЖЕННЯ") ? t.getNext() : t), false, 40));
        if ((pts != null && pts.size() > 0 && pts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) && pts.get(0).values.size() > 0 && !pts.get(0).isNewlineAfter()) {
            boolean ok = false;
            tt = pts.get(pts.size() - 1).getEndToken().getNext();
            if (tt != null && tt.isCharOf(".)]")) {
            }
            else 
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isValue("ПРИМЕНЯТЬСЯ", "ЗАСТОСОВУВАТИСЯ")) 
                        ok = true;
                    if ((tt.isValue("ВСТУПАТЬ", "ВСТУПАТИ") && tt.getNext() != null && tt.getNext().getNext() != null) && tt.getNext().getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
                        ok = true;
                    if (tt.isNewlineAfter()) {
                        if (ok) 
                            return _new1690(t, tt, Types.COMMENT);
                        break;
                    }
                }
        }
        if (t != null && (((t.isNewlineBefore() || isInIndex || isCitat) || ((t.getPrevious() != null && t.getPrevious().isTableControlChar())))) && !t.isTableControlChar()) {
            boolean ok = true;
            if (t.getNext() != null && t.chars.isAllLower()) {
                if (!t.getMorph().getCase().isNominative()) 
                    ok = false;
                else if (t.getNext() != null && t.getNext().isCharOf(",:;.")) 
                    ok = false;
                else {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getEndToken() == t) 
                        ok = false;
                }
            }
            if (ok && (t instanceof com.pullenti.ner.TextToken)) {
                ok = false;
                String s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(s, "ГЛАВА") || com.pullenti.unisharp.Utils.stringsEq(s, "ГОЛОВА")) {
                    res.typ = Types.CHAPTER;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "СТАТЬЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "СТАТТЯ")) {
                    res.typ = Types.CLAUSE;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "РАЗДЕЛ") || com.pullenti.unisharp.Utils.stringsEq(s, "РОЗДІЛ")) {
                    res.typ = Types.SECTION;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "ЧАСТЬ") || com.pullenti.unisharp.Utils.stringsEq(s, "ЧАСТИНА")) {
                    res.typ = Types.DOCPART;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "ПОДРАЗДЕЛ") || com.pullenti.unisharp.Utils.stringsEq(s, "ПІДРОЗДІЛ")) {
                    res.typ = Types.SUBSECTION;
                    t = t.getNext();
                    ok = true;
                }
                else if ((com.pullenti.unisharp.Utils.stringsEq(s, "ПРИМЕЧАНИЕ") || com.pullenti.unisharp.Utils.stringsEq(s, "ПРИМІТКА") || com.pullenti.unisharp.Utils.stringsEq(s, "ПРИМЕЧАНИЯ")) || com.pullenti.unisharp.Utils.stringsEq(s, "ПРИМІТКИ")) {
                    res.typ = Types.NOTICE;
                    t = t.getNext();
                    if (t != null && t.isCharOf(".:")) 
                        t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "§") || com.pullenti.unisharp.Utils.stringsEq(s, "ПАРАГРАФ")) {
                    res.typ = Types.PARAGRAPH;
                    t = t.getNext();
                    ok = true;
                }
                if (ok) {
                    com.pullenti.ner.Token ttt = t;
                    if (ttt != null && (ttt instanceof com.pullenti.ner.NumberToken)) 
                        ttt = ttt.getNext();
                    if (ttt != null && !ttt.isNewlineBefore()) {
                        if (com.pullenti.ner.decree.internal.PartToken.tryAttach(ttt, null, false, false) != null) 
                            res.typ = Types.LINE;
                        else if (InstrToken._checkEntered(ttt) != null) {
                            res.typ = Types.EDITIONS;
                            t00 = res.getBeginToken();
                        }
                        else if (res.getBeginToken().chars.isAllLower()) {
                            if (res.getBeginToken().getNewlinesBeforeCount() > 3) {
                            }
                            else 
                                res.typ = Types.LINE;
                        }
                    }
                }
            }
        }
        boolean num = res.typ != Types.EDITIONS;
        boolean hasLetters = false;
        boolean isApp = cur != null && ((cur.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX || cur.kind == com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT));
        for (; t != null; t = t.getNext()) {
            if (maxChar > 0 && t.getBeginChar() > maxChar) 
                break;
            if (t.isNewlineBefore() && t != res.getBeginToken()) {
                if (res.numbers.size() == 2) {
                    if (com.pullenti.unisharp.Utils.stringsEq(res.numbers.get(0), "3") && com.pullenti.unisharp.Utils.stringsEq(res.numbers.get(1), "4")) {
                    }
                }
                boolean isNewLine = true;
                if ((t.getNewlinesBeforeCount() == 1 && t.getPrevious() != null && t.getPrevious().chars.isLetter()) && lev == 0) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getEndChar() > t.getBeginChar()) 
                        isNewLine = false;
                    else if (t.getPrevious().getMorphClassInDictionary().isAdjective()) {
                        npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt != null && npt.getMorph().checkAccord(t.getPrevious().getMorph(), false, false)) 
                            isNewLine = false;
                    }
                    if (!isNewLine) {
                        InstrToken1 tes = InstrToken1.parse(t, true, cur, lev + 1, null, false, 0, false, false);
                        if (tes != null && tes.numbers.size() > 0) 
                            break;
                    }
                    else if (res.numbers.size() > 0) {
                        InstrToken1 tes = InstrToken1.parse(t, true, cur, lev + 1, null, false, 0, false, false);
                        if (tes != null && tes.numbers.size() > 0) 
                            break;
                    }
                }
                if (isNewLine && t.chars.isLetter()) {
                    if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                        if (t.getPrevious() != null && t.getPrevious().isCharOf(":;.")) {
                        }
                        else if (t.isValue("НЕТ", null) || t.isValue("НЕ", null) || t.isValue("ОТСУТСТВОВАТЬ", null)) {
                        }
                        else if ((res.numbers.size() > 0 && t.getPrevious() != null && t.getPrevious().chars.isAllUpper()) && !t.chars.isAllUpper()) {
                        }
                        else if (t.getPrevious() != null && ((t.getPrevious().isValue("ИЛИ", null) || t.getPrevious().isCommaAnd())) && res.numbers.size() > 0) {
                            InstrToken1 vvv = parse(t, true, cur, lev + 1, null, false, 0, false, false);
                            if (vvv != null && vvv.numbers.size() > 0) 
                                isNewLine = true;
                        }
                        else 
                            isNewLine = false;
                    }
                }
                if (isNewLine) 
                    break;
                else {
                }
            }
            if (t.isTableControlChar() && t != res.getBeginToken()) {
                if (canBeTableCell || t.isChar((char)0x1E) || t.isChar((char)0x1F)) 
                    break;
                if (num && res.numbers.size() > 0) 
                    num = false;
                else if (t.getPrevious() == res.numEndToken) {
                }
                else if (!t.isNewlineAfter()) 
                    continue;
                else 
                    break;
            }
            if (isInIndex && !t.isNewlineBefore() && !t.chars.isAllLower()) {
                com.pullenti.ner.decree.internal.PartToken _typ = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false);
                if (_typ != null) {
                    if (((_typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CHAPTER || _typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE || _typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SECTION) || _typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBSECTION || _typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PARAGRAPH) || _typ.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.APPENDIX) {
                        if (_typ.values.size() == 1) 
                            break;
                    }
                }
                com.pullenti.ner.decree.DecreePartReferent dp = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
                if (dp != null) {
                    if (((dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_CHAPTER) != null || dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE) != null || dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_SECTION) != null) || dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBSECTION) != null || dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_PARAGRAPH) != null) || dp.getSlotValue(com.pullenti.ner.decree.DecreePartReferent.ATTR_APPENDIX) != null) {
                        t = t.kit.debedToken(t);
                        break;
                    }
                }
            }
            if ((t.isChar('[') && t == t0 && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(']')) {
                num = false;
                res.numbers.add(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                res.numTyp = NumberTypes.DIGIT;
                res.numSuffix = "]";
                res.numBeginToken = t;
                res.numEndToken = t.getNext().getNext();
                t = res.numEndToken;
                continue;
            }
            if (t.isChar('(')) {
                num = false;
                if (FragToken._createEditions(t) != null) 
                    break;
                if (_createEdition(t) != null) 
                    break;
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    if (t == res.getBeginToken()) {
                        com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t.getNext());
                        if (lat != null && lat.getEndToken().getNext() == br.getEndToken()) {
                            res.numbers.add(lat.getValue().toString());
                            res.numSuffix = ")";
                            res.numBeginToken = t;
                            res.numEndToken = br.getEndToken();
                            res.numTyp = (lat.typ == com.pullenti.ner.NumberSpellingType.ROMAN ? NumberTypes.ROMAN : NumberTypes.DIGIT);
                        }
                        else if (((t == t0 && t.isNewlineBefore() && br.getLengthChar() == 3) && br.getEndToken() == t.getNext().getNext() && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isLatinLetter()) {
                            res.numBeginToken = t;
                            res.numTyp = NumberTypes.LETTER;
                            res.numbers.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                            res.setEndToken((res.numEndToken = t.getNext().getNext()));
                        }
                    }
                    t = res.setEndToken(br.getEndToken());
                    continue;
                }
            }
            if (num) {
                NumberingHelper.parseNumber(t, res, prev);
                num = false;
                if (res.numbers.size() > 0) {
                }
                if (res.numEndToken != null && res.numEndToken.getEndChar() >= t.getEndChar()) {
                    t = res.numEndToken;
                    continue;
                }
            }
            if (res.numbers.size() == 0) 
                num = false;
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                hasLetters = true;
                if (t00 == null) 
                    t00 = t;
                num = false;
                if (t.chars.isCapitalUpper() && res.getLengthChar() > 20) {
                    if (t.isValue("РУКОВОДСТВУЯСЬ", null)) {
                        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t) || t.getPrevious().isComma()) 
                            break;
                    }
                    else if (t.isValue("НА", null) && t.getNext() != null && t.getNext().isValue("ОСНОВАНИЕ", null)) {
                        InstrToken1 ttt = parse(t, true, null, lev + 1, null, false, 0, false, false);
                        if (ttt != null && (ttt.toString().toUpperCase().indexOf("РУКОВОДСТВУЯСЬ") >= 0)) {
                            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                                break;
                        }
                    }
                }
                if (!t.chars.isAllUpper()) 
                    res.allUpper = false;
                if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) {
                    if (t.chars.isCyrillicLetter()) {
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse((t.getMorph()._getClass().isPreposition() ? t.getNext() : t), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt != null) {
                        }
                        else 
                            res.hasVerb = true;
                    }
                }
            }
            else if (t instanceof com.pullenti.ner.ReferentToken) {
                hasLetters = true;
                if (t00 == null) 
                    t00 = t;
                num = false;
                if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) {
                    res.hasVerb = true;
                    res.allUpper = false;
                }
                if (t.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) {
                    if (!t.chars.isAllUpper()) 
                        res.allUpper = false;
                }
            }
            if (t != res.getBeginToken() && _isFirstLine(t)) 
                break;
            String tmp;
            com.pullenti.unisharp.Outargwrapper<String> wraptmp1698 = new com.pullenti.unisharp.Outargwrapper<String>();
            tt2 = _checkDirective(t, wraptmp1698);
            tmp = wraptmp1698.value;
            if (tt2 != null) {
                if (tt2.getNext() != null && tt2.getNext().isCharOf(":.") && tt2.getNext().isNewlineAfter()) {
                    if (ignoreDirectives && !t.isNewlineBefore()) 
                        t = tt2;
                    else 
                        break;
                }
            }
            res.setEndToken(t);
        }
        if (res.getTypContainerRank() > 0 && t00 != null) {
            if (t00.chars.isAllLower()) {
                res.typ = Types.LINE;
                res.numbers.clear();
                res.numTyp = NumberTypes.UNDEFINED;
            }
        }
        if (t00 != null) {
            int len = res.getEndChar() - t00.getBeginChar();
            if (len < 1000) {
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t00, res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.morph.LanguageHelper.endsWith(res.value, ".")) 
                    res.value = res.value.substring(0, 0 + res.value.length() - 1);
            }
        }
        if (!hasLetters) 
            res.allUpper = false;
        if (res.numTyp != NumberTypes.UNDEFINED && res.getBeginToken() == res.numBeginToken && res.getEndToken() == res.numEndToken) {
            boolean ok = false;
            if (prev != null) {
                if (NumberingHelper.calcDelta(prev, res, true) == 1) 
                    ok = true;
            }
            if (!ok) {
                InstrToken1 res1 = parse(res.getEndToken().getNext(), true, null, lev + 1, null, false, 0, false, false);
                if (res1 != null) {
                    if (NumberingHelper.calcDelta(res, res1, true) == 1) 
                        ok = true;
                }
            }
            if (!ok) {
                res.numTyp = NumberTypes.UNDEFINED;
                res.numbers.clear();
            }
        }
        if (res.typ == Types.APPENDIX || res.getTypContainerRank() > 0) {
            if (res.typ == Types.CLAUSE && res.getLastNumber() == 17) {
            }
            tt = (((res.numEndToken != null ? res.numEndToken : res.getBeginToken()))).getNext();
            if (tt != null) {
                com.pullenti.ner.Token ttt = InstrToken._checkEntered(tt);
                if (ttt != null) {
                    if (tt.isValue("УТРАТИТЬ", null) && tt.getPrevious() != null && tt.getPrevious().isChar('.')) {
                        res.value = null;
                        res.setEndToken(tt.getPrevious());
                        res.isExpired = true;
                    }
                    else {
                        res.typ = Types.EDITIONS;
                        res.numbers.clear();
                        res.numTyp = NumberTypes.UNDEFINED;
                        res.value = null;
                    }
                }
            }
        }
        if (res.typ == Types.DOCPART) {
        }
        boolean badNumber = false;
        if ((res.getTypContainerRank() > 0 && res.numTyp != NumberTypes.UNDEFINED && res.numEndToken != null) && !res.numEndToken.isNewlineAfter() && res.numEndToken.getNext() != null) {
            com.pullenti.ner.Token t1 = res.numEndToken.getNext();
            boolean bad = false;
            if (t1.chars.isAllLower()) 
                bad = true;
            if (bad) 
                badNumber = true;
        }
        if (res.numTyp != NumberTypes.UNDEFINED && !isCitat) {
            if (res.isNewlineBefore() || isInIndex) {
            }
            else if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isTableControlChar()) {
            }
            else 
                badNumber = true;
            if (com.pullenti.unisharp.Utils.stringsEq(res.numSuffix, "-")) 
                badNumber = true;
        }
        if (res.typ == Types.LINE && res.numbers.size() > 0 && isCitat) {
            com.pullenti.ner.Token tt0 = res.getBeginToken().getPrevious();
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt0, true, true)) 
                tt0 = tt0.getPrevious();
            if (tt0 != null) 
                tt0 = tt0.getPrevious();
            for (; tt0 != null; tt0 = tt0.getPrevious()) {
                if (tt0.isValue("ГЛАВА", "ГОЛОВА")) 
                    res.typ = Types.CHAPTER;
                else if (tt0.isValue("СТАТЬЯ", "СТАТТЯ")) 
                    res.typ = Types.CLAUSE;
                else if (tt0.isValue("РАЗДЕЛ", "РОЗДІЛ")) 
                    res.typ = Types.SECTION;
                else if (tt0.isValue("ЧАСТЬ", "ЧАСТИНА")) 
                    res.typ = Types.DOCPART;
                else if (tt0.isValue("ПОДРАЗДЕЛ", "ПІДРОЗДІЛ")) 
                    res.typ = Types.SUBSECTION;
                else if (tt0.isValue("ПАРАГРАФ", null)) 
                    res.typ = Types.PARAGRAPH;
                else if (tt0.isValue("ПРИМЕЧАНИЕ", "ПРИМІТКА")) 
                    res.typ = Types.NOTICE;
                if (tt0.isNewlineBefore()) 
                    break;
            }
        }
        if (badNumber) {
            res.typ = Types.LINE;
            res.numTyp = NumberTypes.UNDEFINED;
            res.value = null;
            res.numbers.clear();
            res.numBeginToken = (res.numEndToken = null);
        }
        if ((res.typ == Types.SECTION || res.typ == Types.PARAGRAPH || res.typ == Types.CHAPTER) || res.typ == Types.CLAUSE) {
            if (res.numbers.size() == 0) 
                res.typ = Types.LINE;
        }
        if (res.getEndToken().isChar('>') && res.getBeginToken().isValue("ПУТЕВОДИТЕЛЬ", null)) {
            res.typ = Types.COMMENT;
            for (com.pullenti.ner.Token ttt = res.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                InstrToken1 li2 = parse(ttt, true, null, lev + 1, null, false, 0, false, false);
                if (li2 != null && li2.getEndToken().isChar('>')) {
                    res.setEndToken((ttt = li2.getEndToken()));
                    continue;
                }
                break;
            }
            return res;
        }
        if (res.typ == Types.LINE) {
            if (res.numTyp != NumberTypes.UNDEFINED) {
                com.pullenti.ner.Token ttt = res.getBeginToken().getPrevious();
                if (ttt instanceof com.pullenti.ner.TextToken) {
                    if (ttt.isValue("ПУНКТ", null)) {
                        res.numTyp = NumberTypes.UNDEFINED;
                        res.value = null;
                        res.numbers.clear();
                    }
                }
                for (String nn : res.numbers) {
                    int vv;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapvv1699 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres1700 = com.pullenti.unisharp.Utils.parseInteger(nn, 0, null, wrapvv1699);
                    vv = (wrapvv1699.value != null ? wrapvv1699.value : 0);
                    if (inoutres1700) {
                        if (vv > 1000 && res.numBeginToken == res.getBeginToken()) {
                            res.numTyp = NumberTypes.UNDEFINED;
                            res.value = null;
                            res.numbers.clear();
                            break;
                        }
                    }
                }
            }
            if (_isFirstLine(res.getBeginToken())) 
                res.typ = Types.FIRSTLINE;
            if (res.numTyp == NumberTypes.DIGIT) {
                if (res.numSuffix == null) 
                    res.isNumDoubt = true;
            }
            if (res.numbers.size() == 0) {
                com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(res.getBeginToken(), null, false, false);
                if (pt != null && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) {
                    tt = pt.getEndToken().getNext();
                    if (tt != null && ((tt.isCharOf(".") || tt.isHiphen()))) 
                        tt = tt.getNext();
                    tt = InstrToken._checkEntered(tt);
                    if (tt != null) {
                        res.typ = Types.EDITIONS;
                        res.isExpired = tt.isValue("УТРАТИТЬ", "ВТРАТИТИ");
                    }
                }
                else {
                    tt = InstrToken._checkEntered(res.getBeginToken());
                    if (tt != null && tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                        res.typ = Types.EDITIONS;
                    else if (res.getBeginToken().isValue("АБЗАЦ", null) && res.getBeginToken().getNext() != null && res.getBeginToken().getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) 
                        res.isExpired = true;
                }
            }
        }
        if (res.typ == Types.LINE && res.numTyp == NumberTypes.ROMAN) {
            InstrToken1 res1 = parse(res.getEndToken().getNext(), true, cur, lev + 1, null, false, 0, false, false);
            if (res1 != null && res1.typ == Types.CLAUSE) 
                res.typ = Types.CHAPTER;
        }
        int specs = 0;
        int _chars = 0;
        if (res.numbers.size() == 2 && com.pullenti.unisharp.Utils.stringsEq(res.numbers.get(0), "2") && com.pullenti.unisharp.Utils.stringsEq(res.numbers.get(1), "3")) {
        }
        for (tt = (res.numEndToken == null ? res.getBeginToken() : res.numEndToken.getNext()); tt != null; tt = tt.getNext()) {
            if (tt.getEndChar() > res.getEndToken().getEndChar()) 
                break;
            com.pullenti.ner.TextToken tto = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class);
            if (tto == null) 
                continue;
            if (!tto.chars.isLetter()) {
                if (!tto.isCharOf(",;.():") && !com.pullenti.ner.core.BracketHelper.isBracket(tto, false)) 
                    specs += tto.getLengthChar();
            }
            else 
                _chars += tto.getLengthChar();
        }
        if ((specs + _chars) > 0) {
            if ((((specs * 100) / ((specs + _chars)))) > 10) 
                res.hasManySpecChars = true;
        }
        res.titleTyp = StdTitleType.UNDEFINED;
        int words = 0;
        for (tt = (res.numBeginToken == null ? res.getBeginToken() : res.numBeginToken.getNext()); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
            if (!(tt instanceof com.pullenti.ner.TextToken) || tt.isChar('_')) {
                res.titleTyp = StdTitleType.UNDEFINED;
                break;
            }
            if (!tt.chars.isLetter() || tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                words++;
                int ii;
                for (ii = 0; ii < m_StdReqWords.size(); ii++) {
                    if (npt.noun.isValue(m_StdReqWords.get(ii), null)) 
                        break;
                }
                if (ii < m_StdReqWords.size()) {
                    tt = npt.getEndToken();
                    res.titleTyp = StdTitleType.REQUISITES;
                    continue;
                }
                if (npt.noun.isValue("ВВЕДЕНИЕ", "ВВЕДЕННЯ") || npt.noun.isValue("ВСТУПЛЕНИЕ", "ВСТУП")) {
                    words++;
                    tt = npt.getEndToken();
                    res.titleTyp = StdTitleType.OTHERS;
                    continue;
                }
                if (((npt.noun.isValue("ПОЛОЖЕНИЕ", "ПОЛОЖЕННЯ") || npt.noun.isValue("СОКРАЩЕНИЕ", "СКОРОЧЕННЯ") || npt.noun.isValue("ТЕРМИН", "ТЕРМІН")) || npt.noun.isValue("ОПРЕДЕЛЕНИЕ", "ВИЗНАЧЕННЯ") || npt.noun.isValue("АББРЕВИАТУРА", "АБРЕВІАТУРА")) || npt.noun.isValue("ЛИТЕРАТУРА", "ЛІТЕРАТУРА") || npt.noun.isValue("НАЗВАНИЕ", "НАЗВА")) {
                    tt = npt.getEndToken();
                    res.titleTyp = StdTitleType.OTHERS;
                    continue;
                }
                if (npt.noun.isValue("ПАСПОРТ", null)) {
                    tt = npt.getEndToken();
                    res.titleTyp = StdTitleType.OTHERS;
                    com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt2 != null && npt2.getMorph().getCase().isGenitive() && (npt2.getWhitespacesBeforeCount() < 3)) 
                        tt = npt2.getEndToken();
                    continue;
                }
                if (npt.noun.isValue("ПРЕДМЕТ", null)) {
                    tt = npt.getEndToken();
                    res.titleTyp = StdTitleType.SUBJECT;
                    continue;
                }
                if (npt.getEndToken() instanceof com.pullenti.ner.TextToken) {
                    String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                    if (com.pullenti.unisharp.Utils.stringsEq(term, "ПРИЛОЖЕНИЯ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПРИЛОЖЕНИЙ")) {
                        tt = npt.getEndToken();
                        res.titleTyp = StdTitleType.OTHERS;
                        continue;
                    }
                }
                if (((npt.noun.isValue("МОМЕНТ", null) || npt.noun.isValue("ЗАКЛЮЧЕНИЕ", "ВИСНОВОК") || npt.noun.isValue("ДАННЫЕ", null)) || npt.isValue("ДОГОВОР", "ДОГОВІР") || npt.isValue("КОНТРАКТ", null)) || npt.isValue("СПИСОК", null) || npt.isValue("ПЕРЕЧЕНЬ", "ПЕРЕЛІК")) {
                    tt = npt.getEndToken();
                    continue;
                }
            }
            ParticipantToken pp = ParticipantToken.tryAttach(tt, null, null, false);
            if (pp != null && pp.kind == ParticipantToken.Kinds.PURE) {
                tt = pp.getEndToken();
                continue;
            }
            res.titleTyp = StdTitleType.UNDEFINED;
            break;
        }
        if (res.titleTyp != StdTitleType.UNDEFINED && res.numbers.size() == 0) {
            t = res.getBeginToken();
            if (!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isLetter() || t.chars.isAllLower()) 
                res.titleTyp = StdTitleType.UNDEFINED;
        }
        if ((res.numbers.size() == 0 && !res.isNewlineBefore() && res.getBeginToken().getPrevious() != null) && res.getBeginToken().getPrevious().isTableControlChar()) 
            res.titleTyp = StdTitleType.UNDEFINED;
        for (t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (!t.isTableControlChar()) 
                break;
            else if (t.isChar((char)0x1E)) 
                break;
            else 
                res.setEndToken(t);
        }
        return res;
    }

    private static java.util.ArrayList<String> m_StdReqWords;

    private static boolean _isFirstLine(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        String v = tt.term;
        if ((((com.pullenti.unisharp.Utils.stringsEq(v, "ИСХОДЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ВИХОДЯЧИ"))) && t.getNext() != null && t.getNext().isValue("ИЗ", "З")) && t.getNext().getNext() != null && t.getNext().getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if ((((com.pullenti.unisharp.Utils.stringsEq(v, "НА") || com.pullenti.unisharp.Utils.stringsEq(v, "HA"))) && t.getNext() != null && t.getNext().isValue("ОСНОВАНИЕ", "ПІДСТАВА")) && t.getNext().getNext() != null && t.getNext().getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if (((com.pullenti.unisharp.Utils.stringsEq(v, "УЧИТЫВАЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ВРАХОВУЮЧИ"))) && t.getNext() != null && t.getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if ((com.pullenti.unisharp.Utils.stringsEq(v, "ЗАСЛУШАВ") || com.pullenti.unisharp.Utils.stringsEq(v, "РАССМОТРЕВ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЗАСЛУХАВШИ")) || com.pullenti.unisharp.Utils.stringsEq(v, "РОЗГЛЯНУВШИ")) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(v, "РУКОВОДСТВУЯСЬ") || com.pullenti.unisharp.Utils.stringsEq(v, "КЕРУЮЧИСЬ")) 
            return tt.isNewlineBefore();
        return false;
    }

    public static com.pullenti.ner.Token _createEdition(com.pullenti.ner.Token t) {
        if (t == null || t.getNext() == null) 
            return null;
        boolean ok = false;
        com.pullenti.ner.Token t1 = t;
        int br = 0;
        if (t.isChar('(') && t.isNewlineBefore()) {
            ok = true;
            br = 1;
            t1 = t.getNext();
        }
        if (!ok || t1 == null) 
            return null;
        ok = false;
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> dts = com.pullenti.ner.decree.internal.PartToken.tryAttachList(t1, true, 40);
        if (dts != null && dts.size() > 0) 
            t1 = dts.get(dts.size() - 1).getEndToken().getNext();
        com.pullenti.ner.Token t2 = InstrToken._checkEntered(t1);
        if (t2 == null && t1 != null) 
            t2 = InstrToken._checkEntered(t1.getNext());
        if (t2 != null) 
            ok = true;
        if (!ok) 
            return null;
        for (t1 = t2; t1 != null; t1 = t1.getNext()) {
            if (t1.isChar(')')) {
                if ((--br) == 0) 
                    return t1;
            }
            else if (t1.isChar('(')) 
                br++;
            else if (t1.isNewlineAfter()) 
                break;
        }
        return null;
    }

    public static com.pullenti.ner.Token _checkDirective(com.pullenti.ner.Token t, com.pullenti.unisharp.Outargwrapper<String> val) {
        val.value = null;
        if (t == null || t.getMorph()._getClass().isAdjective()) 
            return null;
        for (int ii = 0; ii < InstrToken.m_Directives.size(); ii++) {
            if (t.isValue(InstrToken.m_Directives.get(ii), null)) {
                val.value = InstrToken.m_DirectivesNorm.get(ii);
                if (t.getWhitespacesBeforeCount() < 7) {
                    if (((((com.pullenti.unisharp.Utils.stringsNe(val.value, "ПРИКАЗ") && com.pullenti.unisharp.Utils.stringsNe(val.value, "ПОСТАНОВЛЕНИЕ") && com.pullenti.unisharp.Utils.stringsNe(val.value, "УСТАНОВЛЕНИЕ")) && com.pullenti.unisharp.Utils.stringsNe(val.value, "РЕШЕНИЕ") && com.pullenti.unisharp.Utils.stringsNe(val.value, "ЗАЯВЛЕНИЕ")) && com.pullenti.unisharp.Utils.stringsNe(val.value, "НАКАЗ") && com.pullenti.unisharp.Utils.stringsNe(val.value, "ПОСТАНОВА")) && com.pullenti.unisharp.Utils.stringsNe(val.value, "ВСТАНОВЛЕННЯ") && com.pullenti.unisharp.Utils.stringsNe(val.value, "РІШЕННЯ")) && com.pullenti.unisharp.Utils.stringsNe(val.value, "ЗАЯВУ")) {
                        if ((t.getNext() != null && t.getNext().isChar(':') && t.getNext().isNewlineAfter()) && t.chars.isAllUpper()) {
                        }
                        else 
                            break;
                    }
                }
                if (t.getNext() != null && t.getNext().isValue("СЛЕДУЮЩЕЕ", "НАСТУПНЕ")) 
                    return t.getNext();
                if (((com.pullenti.unisharp.Utils.stringsEq(val.value, "ЗАЯВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(val.value, "ЗАЯВА"))) && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
                    t = t.getNext();
                return t;
            }
        }
        if (t.chars.isLetter() && t.getLengthChar() == 1) {
            if (t.isNewlineBefore() || ((t.getNext() != null && t.getNext().chars.isLetter() && t.getNext().getLengthChar() == 1))) {
                for (int ii = 0; ii < InstrToken.m_Directives.size(); ii++) {
                    com.pullenti.ner.Token res = com.pullenti.ner.core.MiscHelper.tryAttachWordByLetters(InstrToken.m_Directives.get(ii), t, true);
                    if (res != null) {
                        val.value = InstrToken.m_DirectivesNorm.get(ii);
                        return res;
                    }
                }
            }
        }
        return null;
    }

    public int getTypContainerRank() {
        int res = _calcRank(typ);
        return res;
    }


    public static int _calcRank(Types ty) {
        if (ty == Types.DOCPART) 
            return 1;
        if (ty == Types.SECTION) 
            return 2;
        if (ty == Types.SUBSECTION) 
            return 3;
        if (ty == Types.CHAPTER) 
            return 4;
        if (ty == Types.PARAGRAPH) 
            return 5;
        if (ty == Types.SUBPARAGRAPH) 
            return 6;
        if (ty == Types.CLAUSE) 
            return 7;
        return 0;
    }

    public boolean canBeContainerFor(InstrToken1 lt) {
        int r = _calcRank(typ);
        int r1 = _calcRank(lt.typ);
        if (r > 0 && r1 > 0) 
            return r < r1;
        return false;
    }

    public static InstrToken1 _new1690(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static InstrToken1 _new1692(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.allUpper = _arg3;
        return res;
    }

    public static InstrToken1 _new1696(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, String _arg4) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types LINE; // 0
    
        public static final Types FIRSTLINE; // 1
    
        public static final Types SIGNS; // 2
    
        public static final Types APPENDIX; // 3
    
        public static final Types APPROVED; // 4
    
        public static final Types BASE; // 5
    
        public static final Types INDEX; // 6
    
        public static final Types TITLE; // 7
    
        public static final Types DIRECTIVE; // 8
    
        public static final Types CHAPTER; // 9
    
        public static final Types CLAUSE; // 10
    
        public static final Types DOCPART; // 11
    
        public static final Types SECTION; // 12
    
        public static final Types SUBSECTION; // 13
    
        public static final Types PARAGRAPH; // 14
    
        public static final Types SUBPARAGRAPH; // 15
    
        public static final Types CLAUSEPART; // 16
    
        public static final Types EDITIONS; // 17
    
        public static final Types COMMENT; // 18
    
        public static final Types NOTICE; // 19
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        private static Types[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Types[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Types>();
            mapStringToEnum = new java.util.HashMap<String, Types>();
            LINE = new Types(0, "LINE");
            mapIntToEnum.put(LINE.value(), LINE);
            mapStringToEnum.put(LINE.m_str.toUpperCase(), LINE);
            FIRSTLINE = new Types(1, "FIRSTLINE");
            mapIntToEnum.put(FIRSTLINE.value(), FIRSTLINE);
            mapStringToEnum.put(FIRSTLINE.m_str.toUpperCase(), FIRSTLINE);
            SIGNS = new Types(2, "SIGNS");
            mapIntToEnum.put(SIGNS.value(), SIGNS);
            mapStringToEnum.put(SIGNS.m_str.toUpperCase(), SIGNS);
            APPENDIX = new Types(3, "APPENDIX");
            mapIntToEnum.put(APPENDIX.value(), APPENDIX);
            mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
            APPROVED = new Types(4, "APPROVED");
            mapIntToEnum.put(APPROVED.value(), APPROVED);
            mapStringToEnum.put(APPROVED.m_str.toUpperCase(), APPROVED);
            BASE = new Types(5, "BASE");
            mapIntToEnum.put(BASE.value(), BASE);
            mapStringToEnum.put(BASE.m_str.toUpperCase(), BASE);
            INDEX = new Types(6, "INDEX");
            mapIntToEnum.put(INDEX.value(), INDEX);
            mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
            TITLE = new Types(7, "TITLE");
            mapIntToEnum.put(TITLE.value(), TITLE);
            mapStringToEnum.put(TITLE.m_str.toUpperCase(), TITLE);
            DIRECTIVE = new Types(8, "DIRECTIVE");
            mapIntToEnum.put(DIRECTIVE.value(), DIRECTIVE);
            mapStringToEnum.put(DIRECTIVE.m_str.toUpperCase(), DIRECTIVE);
            CHAPTER = new Types(9, "CHAPTER");
            mapIntToEnum.put(CHAPTER.value(), CHAPTER);
            mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
            CLAUSE = new Types(10, "CLAUSE");
            mapIntToEnum.put(CLAUSE.value(), CLAUSE);
            mapStringToEnum.put(CLAUSE.m_str.toUpperCase(), CLAUSE);
            DOCPART = new Types(11, "DOCPART");
            mapIntToEnum.put(DOCPART.value(), DOCPART);
            mapStringToEnum.put(DOCPART.m_str.toUpperCase(), DOCPART);
            SECTION = new Types(12, "SECTION");
            mapIntToEnum.put(SECTION.value(), SECTION);
            mapStringToEnum.put(SECTION.m_str.toUpperCase(), SECTION);
            SUBSECTION = new Types(13, "SUBSECTION");
            mapIntToEnum.put(SUBSECTION.value(), SUBSECTION);
            mapStringToEnum.put(SUBSECTION.m_str.toUpperCase(), SUBSECTION);
            PARAGRAPH = new Types(14, "PARAGRAPH");
            mapIntToEnum.put(PARAGRAPH.value(), PARAGRAPH);
            mapStringToEnum.put(PARAGRAPH.m_str.toUpperCase(), PARAGRAPH);
            SUBPARAGRAPH = new Types(15, "SUBPARAGRAPH");
            mapIntToEnum.put(SUBPARAGRAPH.value(), SUBPARAGRAPH);
            mapStringToEnum.put(SUBPARAGRAPH.m_str.toUpperCase(), SUBPARAGRAPH);
            CLAUSEPART = new Types(16, "CLAUSEPART");
            mapIntToEnum.put(CLAUSEPART.value(), CLAUSEPART);
            mapStringToEnum.put(CLAUSEPART.m_str.toUpperCase(), CLAUSEPART);
            EDITIONS = new Types(17, "EDITIONS");
            mapIntToEnum.put(EDITIONS.value(), EDITIONS);
            mapStringToEnum.put(EDITIONS.m_str.toUpperCase(), EDITIONS);
            COMMENT = new Types(18, "COMMENT");
            mapIntToEnum.put(COMMENT.value(), COMMENT);
            mapStringToEnum.put(COMMENT.m_str.toUpperCase(), COMMENT);
            NOTICE = new Types(19, "NOTICE");
            mapIntToEnum.put(NOTICE.value(), NOTICE);
            mapStringToEnum.put(NOTICE.m_str.toUpperCase(), NOTICE);
            java.util.Collection<Types> col = mapIntToEnum.values();
            m_Values = new Types[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static class StdTitleType implements Comparable<StdTitleType> {
    
        public static final StdTitleType UNDEFINED; // 0
    
        public static final StdTitleType SUBJECT; // 1
    
        public static final StdTitleType REQUISITES; // 2
    
        public static final StdTitleType OTHERS; // 3
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private StdTitleType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(StdTitleType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, StdTitleType> mapIntToEnum; 
        private static java.util.HashMap<String, StdTitleType> mapStringToEnum; 
        private static StdTitleType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static StdTitleType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            StdTitleType item = new StdTitleType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static StdTitleType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static StdTitleType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, StdTitleType>();
            mapStringToEnum = new java.util.HashMap<String, StdTitleType>();
            UNDEFINED = new StdTitleType(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            SUBJECT = new StdTitleType(1, "SUBJECT");
            mapIntToEnum.put(SUBJECT.value(), SUBJECT);
            mapStringToEnum.put(SUBJECT.m_str.toUpperCase(), SUBJECT);
            REQUISITES = new StdTitleType(2, "REQUISITES");
            mapIntToEnum.put(REQUISITES.value(), REQUISITES);
            mapStringToEnum.put(REQUISITES.m_str.toUpperCase(), REQUISITES);
            OTHERS = new StdTitleType(3, "OTHERS");
            mapIntToEnum.put(OTHERS.value(), OTHERS);
            mapStringToEnum.put(OTHERS.m_str.toUpperCase(), OTHERS);
            java.util.Collection<StdTitleType> col = mapIntToEnum.values();
            m_Values = new StdTitleType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static InstrToken1 _new1709(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3, Types _arg4) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.indexNoKeyword = _arg3;
        res.typ = _arg4;
        return res;
    }

    public InstrToken1() {
        super();
        if(_globalInstance == null) return;
        typ = Types.LINE;
        titleTyp = StdTitleType.UNDEFINED;
    }
    public static InstrToken1 _globalInstance;
    
    static {
        try { _globalInstance = new InstrToken1(); } 
        catch(Exception e) { }
        m_StdReqWords = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"РЕКВИЗИТ", "ПОДПИСЬ", "СТОРОНА", "АДРЕС", "ТЕЛЕФОН", "МЕСТО", "НАХОЖДЕНИЕ", "МЕСТОНАХОЖДЕНИЕ", "ТЕРМИН", "ОПРЕДЕЛЕНИЕ", "СЧЕТ", "РЕКВІЗИТ", "ПІДПИС", "СТОРОНА", "АДРЕСА", "МІСЦЕ", "ЗНАХОДЖЕННЯ", "МІСЦЕЗНАХОДЖЕННЯ", "ТЕРМІН", "ВИЗНАЧЕННЯ", "РАХУНОК"}));
    }
}
