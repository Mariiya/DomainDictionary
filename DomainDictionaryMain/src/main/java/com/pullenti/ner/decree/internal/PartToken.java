/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

// Примитив, из которых состоит часть декрета (статья, пункт и часть)
public class PartToken extends com.pullenti.ner.MetaToken {

    public PartToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        altTyp = ItemType.UNDEFINED;
    }

    public ItemType typ = ItemType.UNDEFINED;

    public ItemType altTyp = ItemType.UNDEFINED;

    public static class PartValue extends com.pullenti.ner.MetaToken {
    
        public PartValue(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
            super(begin, end, null);
        }
    
        public String value;
    
        public String getSourceValue() {
            com.pullenti.ner.Token t0 = getBeginToken();
            com.pullenti.ner.Token t1 = getEndToken();
            if (t1.isChar('.')) 
                t1 = t1.getPrevious();
            else if (t1.isChar(')') && !t0.isChar('(')) 
                t1 = t1.getPrevious();
            return (new com.pullenti.ner.MetaToken(t0, t1, null)).getSourceText();
        }
    
    
        public int getIntValue() {
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(value)) 
                return 0;
            int num;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapnum1106 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres1107 = com.pullenti.unisharp.Utils.parseInteger(value, 0, null, wrapnum1106);
            num = (wrapnum1106.value != null ? wrapnum1106.value : 0);
            if (inoutres1107) 
                return num;
            return 0;
        }
    
    
        @Override
        public String toString() {
            return value;
        }
    
        public void correctValue() {
            if ((getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(this.getEndToken().getNext(), com.pullenti.ner.TextToken.class)).getLengthChar() == 1 && getEndToken().getNext().chars.isLetter()) {
                if (!getEndToken().isWhitespaceAfter()) {
                    value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(this.getEndToken().getNext(), com.pullenti.ner.TextToken.class)).term;
                    setEndToken(this.getEndToken().getNext());
                }
                else if ((getEndToken().getWhitespacesAfterCount() < 2) && getEndToken().getNext().getNext() != null && getEndToken().getNext().getNext().isChar(')')) {
                    value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(this.getEndToken().getNext(), com.pullenti.ner.TextToken.class)).term;
                    setEndToken(this.getEndToken().getNext().getNext());
                }
            }
            if ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(this.getEndToken().getNext(), false, false) && (getEndToken().getNext().getNext() instanceof com.pullenti.ner.TextToken) && getEndToken().getNext().getNext().getLengthChar() == 1) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(this.getEndToken().getNext().getNext().getNext(), false, this.getEndToken().getNext(), false)) {
                value = (value + "." + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(this.getEndToken().getNext().getNext(), com.pullenti.ner.TextToken.class)).term);
                setEndToken(this.getEndToken().getNext().getNext().getNext());
            }
            for (com.pullenti.ner.Token t = getEndToken().getNext(); t != null; t = t.getNext()) {
                if (t.isWhitespaceBefore()) {
                    if (t.getWhitespacesBeforeCount() > 1) 
                        break;
                    if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.getNext() != null) && t.getNext().isChar(')')) {
                        value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                        setEndToken((t = t.getNext()));
                    }
                    break;
                }
                if (t.isCharOf("_.") && !t.isWhitespaceAfter()) {
                    if (t.getNext() instanceof com.pullenti.ner.NumberToken) {
                        value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                        setEndToken((t = t.getNext()));
                        continue;
                    }
                    if (((t.getNext() != null && t.getNext().isChar('(') && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(')')) {
                        value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                        setEndToken(t.getNext().getNext().getNext());
                        continue;
                    }
                }
                if ((t.isHiphen() && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                    int n1;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn11108 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres1109 = com.pullenti.unisharp.Utils.parseInteger(value, 0, null, wrapn11108);
                    n1 = (wrapn11108.value != null ? wrapn11108.value : 0);
                    if (inoutres1109) {
                        if (n1 >= ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue()) {
                            value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                            setEndToken((t = t.getNext()));
                            continue;
                        }
                    }
                }
                if ((t.isCharOf("(<") && (t.getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext() != null) && t.getNext().getNext().isCharOf(")>")) {
                    value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    setEndToken((t = t.getNext().getNext()));
                    if (t.getNext() != null && t.getNext().isChar('.') && !t.isWhitespaceAfter()) 
                        t = t.getNext();
                    continue;
                }
                if ((t.isChar('\\') && (t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceBefore()) && !t.isWhitespaceAfter()) {
                    value = (((value != null ? value : "")) + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    setEndToken((t = t.getNext()));
                    continue;
                }
                break;
            }
            if (getEndToken().getNext() != null && getEndToken().getNext().isCharOf(".") && !getEndToken().isWhitespaceAfter()) {
                if (getEndToken().getNext().getNext() != null && (getEndToken().getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && !getEndToken().getNext().isNewlineAfter()) 
                    setEndToken(this.getEndToken().getNext());
            }
            if (getBeginToken() == getEndToken() && getEndToken().getNext() != null && getEndToken().getNext().isChar(')')) {
                boolean ok = true;
                int lev = 0;
                for (com.pullenti.ner.Token ttt = getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                    if (ttt.isNewlineAfter()) 
                        break;
                    if (ttt.isChar(')')) 
                        lev++;
                    else if (ttt.isChar('(')) {
                        lev--;
                        if (lev < 0) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (ok) {
                    com.pullenti.ner.Token tt = getEndToken().getNext().getNext();
                    if (tt != null) {
                        if ((tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) || PartToken.tryAttach(tt, null, false, false) != null)
                            setEndToken(this.getEndToken().getNext());
                    }
                }
            }
        }
    
        public static PartValue _new1110(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
            PartValue res = new PartValue(_arg1, _arg2);
            res.value = _arg3;
            return res;
        }
        public PartValue() {
            super();
        }
    }


    public java.util.ArrayList<PartValue> values = new java.util.ArrayList<PartValue>();

    public String name;

    public int ind;

    public com.pullenti.ner.decree.DecreeReferent decree;

    public boolean isDoubt;

    public boolean delimAfter;

    public boolean hasTerminator;

    public com.pullenti.ner.TextToken anaforRef;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(typ.toString());
        for (PartValue v : values) {
            res.append(" ").append(v);
        }
        if (delimAfter) 
            res.append(", DelimAfter");
        if (isDoubt) 
            res.append(", Doubt");
        if (hasTerminator) 
            res.append(", Terminator");
        if (anaforRef != null) 
            res.append(", Ref='").append(anaforRef.term).append("'");
        return res.toString();
    }

    public static PartToken tryAttach(com.pullenti.ner.Token t, PartToken prev, boolean inBracket, boolean ignoreNumber) {
        if (t == null) 
            return null;
        PartToken res = null;
        if (t.getMorph()._getClass().isPersonalPronoun() && (t.getWhitespacesAfterCount() < 2)) {
            res = tryAttach(t.getNext(), prev, false, false);
            if (res != null) {
                res.anaforRef = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                res.setBeginToken(t);
                return res;
            }
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if ((t instanceof com.pullenti.ner.NumberToken) && t.getNext() != null && (t.getWhitespacesAfterCount() < 3)) {
            PartToken re = _createPartTyp0(t.getNext(), prev);
            if (re != null) {
                com.pullenti.ner.Token t11 = re.getEndToken().getNext();
                boolean ok1 = false;
                if (t11 != null && (t11.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                    ok1 = true;
                else if (prev != null && t11 != null && !(t11 instanceof com.pullenti.ner.NumberToken)) 
                    ok1 = true;
                if (!ok1) {
                    PartToken res1 = tryAttach(t11, null, false, false);
                    if (res1 != null) 
                        ok1 = true;
                }
                if (ok1 || inBracket) {
                    re.setBeginToken(t);
                    re.values.add(PartValue._new1110(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString()));
                    return re;
                }
            }
        }
        if (((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && prev == null) && t.getPrevious() != null) {
            com.pullenti.ner.Token t0 = t.getPrevious();
            boolean delim = false;
            if (t0.isChar(',') || t0.getMorph()._getClass().isConjunction()) {
                delim = true;
                t0 = t0.getPrevious();
            }
            if (t0 == null) 
                return null;
            com.pullenti.ner.decree.DecreePartReferent dr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t0.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
            if (dr == null) {
                if (t0.isChar('(') && t.getNext() != null) {
                    if (t.getNext().isValue("ЧАСТЬ", null) || t.getNext().isValue("Ч", null)) {
                        com.pullenti.ner.Token te = t.getNext();
                        if (te.getNext() != null && te.getNext().isChar('.')) 
                            te = te.getNext();
                        res = _new860(t, te, ItemType.PART);
                        res.values.add(PartValue._new1110(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString()));
                        return res;
                    }
                }
                return null;
            }
            if (dr.getClause() == null) 
                return null;
            res = _new1113(t, t, ItemType.CLAUSE, !delim);
            PartValue pv = PartValue._new1110(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            res.values.add(pv);
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isWhitespaceBefore()) 
                    break;
                else if (t.isCharOf("._") && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                    t = t.getNext();
                    pv.setEndToken(res.setEndToken(t));
                    pv.value = (pv.value + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                }
                else 
                    break;
            }
            return res;
        }
        if (((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && prev != null) && prev.typ == ItemType.PREFIX && (t.getWhitespacesBeforeCount() < 3)) {
            PartValue pv = PartValue._new1110(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            pv.correctValue();
            com.pullenti.ner.Token ttt1 = pv.getEndToken().getNext();
            DecreeToken ne = DecreeToken.tryAttach(ttt1, null, false);
            boolean ok = false;
            if (ne != null && ne.typ == DecreeToken.ItemType.TYP) 
                ok = true;
            else if (com.pullenti.ner.decree.DecreeAnalyzer._checkOtherTyp(ttt1, true) != null) 
                ok = true;
            else if (com.pullenti.ner.decree.DecreeAnalyzer._getDecree(ttt1) != null) 
                ok = true;
            if (ok) {
                res = _new860(t, pv.getEndToken(), ItemType.ITEM);
                res.values.add(pv);
                return res;
            }
        }
        if (tt == null) 
            return null;
        if (tt.getLengthChar() == 1 && !tt.chars.isAllLower()) {
            if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                return null;
        }
        com.pullenti.ner.Token t1 = tt;
        res = _createPartTyp0(t1, prev);
        if (res != null) 
            t1 = res.getEndToken();
        else if ((t1.isValue("СИЛУ", null) || t1.isValue("СОГЛАСНО", null) || t1.isValue("СООТВЕТСТВИЕ", null)) || t1.isValue("ПОЛОЖЕНИЕ", null)) {
            if (t1.isValue("СИЛУ", null) && t1.getPrevious() != null && t1.getPrevious().getMorph()._getClass().isVerb()) 
                return null;
            if (t1.isValue("ПОЛОЖЕНИЕ", null) && prev != null && !t1.chars.isAllLower()) 
                return null;
            res = _new860(t1, t1, ItemType.PREFIX);
            if (t1.getNext() != null && t1.getNext().isValue("С", null)) 
                res.setEndToken(t1.getNext());
            return res;
        }
        else if (((t1.isValue("УГОЛОВНОЕ", null) || t1.isValue("КРИМІНАЛЬНА", null))) && t1.getNext() != null && ((t1.getNext().isValue("ДЕЛО", null) || t1.getNext().isValue("СПРАВА", null)))) {
            t1 = t1.getNext();
            if (t1.getNext() != null && t1.getNext().isValue("ПО", null)) 
                t1 = t1.getNext();
            return _new860(t, t1, ItemType.PREFIX);
        }
        else if ((((t1.isValue("МОТИВИРОВОЧНЫЙ", null) || t1.isValue("МОТИВУВАЛЬНИЙ", null) || t1.isValue("РЕЗОЛЮТИВНЫЙ", null)) || t1.isValue("РЕЗОЛЮТИВНИЙ", null))) && t1.getNext() != null && ((t1.getNext().isValue("ЧАСТЬ", null) || t1.getNext().isValue("ЧАСТИНА", null)))) {
            PartToken rr = _new860(t1, t1.getNext(), ItemType.PART);
            rr.values.add(PartValue._new1110(t1, t1, (t1.isValue("МОТИВИРОВОЧНЫЙ", null) || t1.isValue("МОТИВУВАЛЬНИЙ", null) ? "мотивировочная" : "резолютивная")));
            return rr;
        }
        if (res == null) 
            return null;
        if (ignoreNumber) 
            return res;
        if (res.isNewlineAfter()) {
            if (res.chars.isAllUpper()) 
                return null;
        }
        if (t1.getNext() != null && t1.getNext().isChar('.')) {
            if (!t1.getNext().isNewlineAfter() || (t1.getLengthChar() < 3)) {
                t1 = t1.getNext();
                if (t1.getNext() != null && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t1.getNext())) {
                    if (t1.getNext() instanceof com.pullenti.ner.TextToken) 
                        return null;
                    if ((t1.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS) 
                        return null;
                }
            }
        }
        t1 = t1.getNext();
        if (t1 == null) 
            return null;
        if (res.typ == ItemType.CLAUSE) {
            if (((t1 instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue(), "3") && !t1.isWhitespaceAfter()) && t1.getNext() != null && t1.getNext().getLengthChar() == 2) 
                return null;
        }
        if (res.typ == ItemType.CLAUSE && t1.isValue("СТ", null)) {
            t1 = t1.getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
        }
        else if (res.typ == ItemType.PART && t1.isValue("Ч", null)) {
            t1 = t1.getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
        }
        else if (res.typ == ItemType.ITEM && t1.isValue("П", null)) {
            t1 = t1.getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
            res.altTyp = ItemType.SUBITEM;
        }
        else if ((res.typ == ItemType.ITEM && t1.isCharOf("\\/") && t1.getNext() != null) && t1.getNext().isValue("П", null)) {
            t1 = t1.getNext().getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
            res.altTyp = ItemType.SUBITEM;
        }
        if (t1 == null) 
            return null;
        if (res.typ == ItemType.CLAUSE && (t1.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && t1.getNext() != null) {
            res.decree = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            t1 = t1.getNext();
        }
        com.pullenti.ner.Token ttn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1);
        com.pullenti.ner.TextToken firstNumPrefix = null;
        if (ttn != null) {
            firstNumPrefix = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
            t1 = ttn;
        }
        if (t1 == null) 
            return null;
        res.setEndToken(t1);
        boolean and = false;
        com.pullenti.ner.NumberSpellingType ntyp = com.pullenti.ner.NumberSpellingType.DIGIT;
        com.pullenti.ner.Token tt1 = t1;
        while (t1 != null) {
            if (t1.getWhitespacesBeforeCount() > 15) 
                break;
            if (t1 != tt1 && t1.isNewlineBefore()) 
                break;
            if (ttn != null) {
                ttn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1);
                if (ttn != null) 
                    t1 = ttn;
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                boolean ok = true;
                java.util.ArrayList<PartValue> newP = null;
                for (com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (ttt.getEndChar() > br.getEndToken().getPrevious().getEndChar()) 
                        break;
                    if (ttt.isChar(',')) 
                        continue;
                    if (ttt instanceof com.pullenti.ner.NumberToken) {
                        if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue(), "0")) {
                            ok = false;
                            break;
                        }
                        if (newP == null) 
                            newP = new java.util.ArrayList<PartValue>();
                        newP.add(PartValue._new1110(ttt, ttt, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue().toString()));
                        continue;
                    }
                    com.pullenti.ner.TextToken to = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.TextToken.class);
                    if (to == null) {
                        ok = false;
                        break;
                    }
                    if ((res.typ != ItemType.ITEM && res.typ != ItemType.SUBITEM && res.typ != ItemType.INDENTION) && res.typ != ItemType.SUBINDENTION) {
                        ok = false;
                        break;
                    }
                    if (!to.chars.isLetter() || to.getLengthChar() != 1) {
                        ok = false;
                        break;
                    }
                    if (newP == null) 
                        newP = new java.util.ArrayList<PartValue>();
                    PartValue pv = PartValue._new1110(ttt, ttt, to.term);
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt.getPrevious(), false, false)) 
                        pv.setBeginToken(ttt.getPrevious());
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(ttt.getNext(), false, null, false)) 
                        pv.setEndToken(ttt.getNext());
                    newP.add(pv);
                }
                if (newP == null || !ok) 
                    break;
                com.pullenti.unisharp.Utils.addToArrayList(res.values, newP);
                res.setEndToken(br.getEndToken());
                t1 = br.getEndToken().getNext();
                if (and) 
                    break;
                if (t1 != null && t1.isHiphen() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1.getNext(), false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if ((br1 != null && (t1.getNext().getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().getNext().getLengthChar() == 1) && t1.getNext().getNext().getNext() == br1.getEndToken()) {
                        res.values.add(PartValue._new1110(br1.getBeginToken(), br1.getEndToken(), ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.TextToken.class)).term));
                        res.setEndToken(br1.getEndToken());
                        t1 = br1.getEndToken().getNext();
                    }
                }
                continue;
            }
            if (((t1 instanceof com.pullenti.ner.TextToken) && t1.getLengthChar() == 1 && t1.chars.isLetter()) && res.values.size() == 0) {
                if (t1.chars.isAllUpper() && res.typ == ItemType.SUBPROGRAM) {
                    res.values.add(PartValue._new1110(t1, t1, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term));
                    res.setEndToken(t1);
                    return res;
                }
                boolean ok = true;
                int lev = 0;
                for (com.pullenti.ner.Token ttt = t1.getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                    if (ttt.isNewlineAfter()) 
                        break;
                    if (ttt.isChar('(')) {
                        lev--;
                        if (lev < 0) {
                            ok = false;
                            break;
                        }
                    }
                    else if (ttt.isChar(')')) 
                        lev++;
                }
                if (ok && t1.getNext() != null && t1.getNext().isChar(')')) {
                    res.values.add(PartValue._new1110(t1, t1.getNext(), ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term));
                    res.setEndToken(t1.getNext());
                    t1 = t1.getNext().getNext();
                    continue;
                }
                if (((ok && t1.getNext() != null && t1.getNext().isChar('.')) && !t1.getNext().isWhitespaceAfter() && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && t1.getNext().getNext().getNext() != null && t1.getNext().getNext().getNext().isChar(')')) {
                    res.values.add(PartValue._new1110(t1, t1.getNext().getNext().getNext(), (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue())));
                    res.setEndToken(t1.getNext().getNext().getNext());
                    t1 = res.getEndToken().getNext();
                    continue;
                }
                if (ok && tryAttach(t1.getNext(), null, false, false) != null) {
                    if (((t1.isAnd() || t1.isValue("К", null))) && t1.chars.isAllLower()) {
                    }
                    else {
                        res.values.add(PartValue._new1110(t1, t1, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term));
                        res.setEndToken(t1);
                        t1 = t1.getNext();
                        continue;
                    }
                }
            }
            com.pullenti.ner.Token prefTo = null;
            if (res.values.size() > 0 && !(t1 instanceof com.pullenti.ner.NumberToken) && firstNumPrefix != null) {
                ttn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1);
                if (ttn != null) {
                    prefTo = t1;
                    t1 = ttn;
                }
            }
            if (t1 instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.Token tt0 = (prefTo != null ? prefTo : t1);
                if (res.values.size() > 0) {
                    if (res.values.get(0).getIntValue() == 0 && !Character.isDigit(res.values.get(0).value.charAt(0))) 
                        break;
                    if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).typ != ntyp) 
                        break;
                }
                ntyp = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).typ;
                PartValue val = PartValue._new1110(tt0, t1, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue().toString());
                val.correctValue();
                res.values.add(val);
                res.setEndToken(val.getEndToken());
                t1 = res.getEndToken().getNext();
                if (and) 
                    break;
                continue;
            }
            com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t1);
            if (nt != null) {
                PartValue pv = PartValue._new1110(t1, nt.getEndToken(), nt.getValue().toString());
                res.values.add(pv);
                pv.correctValue();
                res.setEndToken(pv.getEndToken());
                t1 = res.getEndToken().getNext();
                continue;
            }
            if ((t1 == tt1 && ((res.typ == ItemType.APPENDIX || res.typ == ItemType.ADDAGREE)) && t1.isValue("К", null)) && t1.getNext() != null && (t1.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                res.values.add(PartValue._new1110(t1, t1, ""));
                break;
            }
            if (res.typ == ItemType.ADDAGREE && firstNumPrefix != null && res.values.size() == 0) {
                DecreeToken ddd = DecreeToken.tryAttach(firstNumPrefix, null, false);
                if (ddd != null && ddd.typ == DecreeToken.ItemType.NUMBER && ddd.value != null) {
                    res.values.add(PartValue._new1110(t1, ddd.getEndToken(), ddd.value));
                    t1 = res.setEndToken(ddd.getEndToken());
                    break;
                }
            }
            if (res.values.size() == 0) 
                break;
            if (t1.isCharOf(",.")) {
                if (t1.isNewlineAfter() && t1.isChar('.')) 
                    break;
                t1 = t1.getNext();
                continue;
            }
            if (t1.isHiphen() && res.values.get(res.values.size() - 1).value.indexOf('.') > 0) {
                t1 = t1.getNext();
                continue;
            }
            if (t1.isAnd() || t1.isOr()) {
                t1 = t1.getNext();
                and = true;
                continue;
            }
            if (t1.isHiphen()) {
                if (!(t1.getNext() instanceof com.pullenti.ner.NumberToken) || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() == null) 
                    break;
                int min = res.values.get(res.values.size() - 1).getIntValue();
                if (min == 0) 
                    break;
                int max = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                if (max < min) 
                    break;
                if ((max - min) > 200) 
                    break;
                PartValue val = PartValue._new1110(t1.getNext(), t1.getNext(), ((Integer)max).toString());
                val.correctValue();
                res.values.add(val);
                res.setEndToken(val.getEndToken());
                t1 = res.getEndToken().getNext();
                continue;
            }
            break;
        }
        if (res.values.size() == 0 && !res.isNewlineAfter() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getEndToken(), true, false)) {
            int lev = _getRank(res.typ);
            if (lev > 0 && (lev < _getRank(ItemType.CLAUSE))) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(res.getEndToken(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    res.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    res.setEndToken(br.getEndToken());
                }
            }
        }
        if (res.values.size() == 0 && res.name == null) {
            if (!ignoreNumber && res.typ != ItemType.PREAMBLE && res.typ != ItemType.SUBPROGRAM) 
                return null;
            if (res.getBeginToken() != res.getEndToken()) 
                res.setEndToken(res.getEndToken().getPrevious());
        }
        return res;
    }

    private static PartToken _createPartTyp0(com.pullenti.ner.Token t1, PartToken prev) {
        boolean isShort;
        com.pullenti.unisharp.Outargwrapper<Boolean> wrapisShort1133 = new com.pullenti.unisharp.Outargwrapper<Boolean>();
        PartToken pt = __createPartTyp(t1, prev, wrapisShort1133);
        isShort = (wrapisShort1133.value != null ? wrapisShort1133.value : false);
        if (pt == null) 
            return null;
        if ((isShort && !pt.getEndToken().isWhitespaceAfter() && pt.getEndToken().getNext() != null) && pt.getEndToken().getNext().isChar('.')) {
            if (!pt.getEndToken().getNext().isNewlineAfter()) 
                pt.setEndToken(pt.getEndToken().getNext());
        }
        return pt;
    }

    private static PartToken __createPartTyp(com.pullenti.ner.Token t1, PartToken prev, com.pullenti.unisharp.Outargwrapper<Boolean> isShort) {
        isShort.value = false;
        if (t1 == null) 
            return null;
        if (t1.isValue("ЧАСТЬ", "ЧАСТИНА") || t1.isValue("PART", null)) 
            return _new860(t1, t1, ItemType.PART);
        if (t1.isValue("Ч", null)) {
            isShort.value = true;
            return _new860(t1, t1, ItemType.PART);
        }
        if (t1.isValue("ГЛАВА", null) || t1.isValue("ГЛ", null) || t1.isValue("CHAPTER", null)) {
            isShort.value = t1.getLengthChar() == 2;
            return _new860(t1, t1, ItemType.CHAPTER);
        }
        if (t1.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК") || t1.isValue("ПРИЛ", null) || t1.isValue("APPENDIX", null)) {
            if ((t1.isNewlineBefore() && t1.getLengthChar() > 6 && t1.getNext() != null) && t1.getNext().isChar(':')) 
                return null;
            isShort.value = t1.getLengthChar() < 5;
            return _new860(t1, t1, ItemType.APPENDIX);
        }
        if (t1.isValue("ПРИМЕЧАНИЕ", "ПРИМІТКА") || t1.isValue("ПРИМ", null)) {
            isShort.value = t1.getLengthChar() < 5;
            return _new860(t1, t1, ItemType.NOTICE);
        }
        if (t1.isValue("СТАТЬЯ", "СТАТТЯ") || t1.isValue("СТ", null) || t1.isValue("ARTICLE", null)) {
            isShort.value = t1.getLengthChar() < 3;
            return _new860(t1, t1, ItemType.CLAUSE);
        }
        if (t1.isValue("ПУНКТ", null) || t1.isValue("П", null) || t1.isValue("ПП", null)) {
            isShort.value = t1.getLengthChar() < 3;
            return _new1140(t1, t1, ItemType.ITEM, (t1.isValue("ПП", null) ? ItemType.SUBITEM : ItemType.UNDEFINED));
        }
        if (t1.isValue("ПОДПУНКТ", "ПІДПУНКТ")) 
            return _new860(t1, t1, ItemType.SUBITEM);
        if (t1.isValue("ПРЕАМБУЛА", null)) 
            return _new860(t1, t1, ItemType.PREAMBLE);
        if (t1.isValue("ПОДП", null) || t1.isValue("ПІДП", null)) {
            isShort.value = true;
            return _new860(t1, t1, ItemType.SUBITEM);
        }
        if (t1.isValue("РАЗДЕЛ", "РОЗДІЛ") || t1.isValue("РАЗД", null) || t1.isValue("SECTION", null)) {
            isShort.value = t1.getLengthChar() < 5;
            return _new860(t1, t1, ItemType.SECTION);
        }
        if (((t1.isValue("Р", null) || t1.isValue("P", null))) && t1.getNext() != null && t1.getNext().isChar('.')) {
            if (prev != null) {
                if (prev.typ == ItemType.ITEM || prev.typ == ItemType.SUBITEM) {
                    isShort.value = true;
                    return _new860(t1, t1.getNext(), ItemType.SECTION);
                }
            }
        }
        if (t1.isValue("ПОДРАЗДЕЛ", "ПІДРОЗДІЛ") || t1.isValue("SUBSECTION", null)) 
            return _new860(t1, t1, ItemType.SUBSECTION);
        if (t1.isValue("ПАРАГРАФ", null) || t1.isValue("§", null) || t1.isValue("PARAGRAPH", null)) 
            return _new860(t1, t1, ItemType.PARAGRAPH);
        if (t1.isValue("АБЗАЦ", null) || t1.isValue("АБЗ", null)) {
            isShort.value = t1.getLengthChar() < 7;
            return _new860(t1, t1, ItemType.INDENTION);
        }
        if (t1.isValue("СТРАНИЦА", "СТОРІНКА") || t1.isValue("СТР", "СТОР") || t1.isValue("PAGE", null)) {
            isShort.value = t1.getLengthChar() < 7;
            return _new860(t1, t1, ItemType.PAGE);
        }
        if (t1.isValue("ПОДАБЗАЦ", "ПІДАБЗАЦ") || t1.isValue("ПОДАБЗ", "ПІДАБЗ")) 
            return _new860(t1, t1, ItemType.SUBINDENTION);
        if (t1.isValue("ПОДПАРАГРАФ", "ПІДПАРАГРАФ") || t1.isValue("SUBPARAGRAPH", null)) 
            return _new860(t1, t1, ItemType.SUBPARAGRAPH);
        if (t1.isValue("ПОДПРОГРАММА", "ПІДПРОГРАМА")) 
            return _new860(t1, t1, ItemType.SUBPROGRAM);
        if (t1.isValue("ДОПСОГЛАШЕНИЕ", null)) 
            return _new860(t1, t1, ItemType.ADDAGREE);
        if (((t1.isValue("ДОП", null) || t1.isValue("ДОПОЛНИТЕЛЬНЫЙ", "ДОДАТКОВА"))) && t1.getNext() != null) {
            com.pullenti.ner.Token tt = t1.getNext();
            if (tt.isChar('.') && tt.getNext() != null) 
                tt = tt.getNext();
            if (tt.isValue("СОГЛАШЕНИЕ", "УГОДА")) 
                return _new860(t1, tt, ItemType.ADDAGREE);
        }
        return null;
    }

    public static java.util.ArrayList<PartToken> tryAttachList(com.pullenti.ner.Token t, boolean inBracket, int maxCount) {
        PartToken p = tryAttach(t, null, inBracket, false);
        if (p == null) 
            return null;
        java.util.ArrayList<PartToken> res = new java.util.ArrayList<PartToken>();
        res.add(p);
        if (p.isNewlineAfter() && p.isNewlineBefore()) {
            if (!p.getBeginToken().chars.isAllLower()) 
                return res;
        }
        com.pullenti.ner.Token tt = p.getEndToken().getNext();
        while (tt != null) {
            if (tt.getWhitespacesBeforeCount() > 15) {
                if (tt.getPrevious() != null && tt.getPrevious().isCommaAnd()) {
                }
                else 
                    break;
            }
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            boolean delim = false;
            if (((tt.isCharOf(",;.") || tt.isAnd() || tt.isOr())) && tt.getNext() != null) {
                if (tt.isCharOf(";.")) 
                    res.get(res.size() - 1).hasTerminator = true;
                else {
                    res.get(res.size() - 1).delimAfter = true;
                    if ((tt.getNext() != null && tt.getNext().isValue("А", null) && tt.getNext().getNext() != null) && tt.getNext().getNext().isValue("ТАКЖЕ", "ТАКОЖ")) 
                        tt = tt.getNext().getNext();
                }
                tt = tt.getNext();
                delim = true;
            }
            if (tt == null) 
                break;
            if (tt.isNewlineBefore()) {
                if (tt.chars.isLetter() && !tt.chars.isAllLower()) 
                    break;
            }
            if (tt.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    java.util.ArrayList<PartToken> li = tryAttachList(tt.getNext(), true, 40);
                    if (li != null && li.size() > 0) {
                        if (li.get(0).typ == ItemType.PARAGRAPH || li.get(0).typ == ItemType.PART || li.get(0).typ == ItemType.ITEM) {
                            if (li.get(li.size() - 1).getEndToken().getNext() == br.getEndToken()) {
                                if (p.values.size() > 1) {
                                    for (int ii = 1; ii < p.values.size(); ii++) {
                                        PartToken pp = _new860(p.values.get(ii).getBeginToken(), (ii == (p.values.size() - 1) ? p.getEndToken() : p.values.get(ii).getEndToken()), p.typ);
                                        pp.values.add(p.values.get(ii));
                                        res.add(pp);
                                    }
                                    if (p.values.get(1).getBeginToken().getPrevious() != null && p.values.get(1).getBeginToken().getPrevious().getEndChar() >= p.getBeginToken().getBeginChar()) 
                                        p.setEndToken(p.values.get(1).getBeginToken().getPrevious());
                                    for(int jjj = 1 + p.values.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) p.values.remove(jjj);
                                }
                                com.pullenti.unisharp.Utils.addToArrayList(res, li);
                                li.get(li.size() - 1).setEndToken(br.getEndToken());
                                tt = br.getEndToken().getNext();
                                continue;
                            }
                        }
                    }
                }
            }
            PartToken p0 = tryAttach(tt, p, inBracket, false);
            if (p0 == null && ((tt.isValue("В", null) || tt.isValue("К", null) || tt.isValue("ДО", null)))) 
                p0 = tryAttach(tt.getNext(), p, inBracket, false);
            if (p0 == null) {
                if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        p0 = tryAttach(br.getEndToken().getNext(), null, false, false);
                        if (p0 != null && p0.typ != ItemType.PREFIX && p0.values.size() > 0) {
                            res.get(res.size() - 1).setEndToken(br.getEndToken());
                            res.get(res.size() - 1).name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                            if (p0.isNewlineBefore()) 
                                break;
                            p = p0;
                            res.add(p);
                            tt = p.getEndToken().getNext();
                            continue;
                        }
                        if (com.pullenti.ner.core.BracketHelper.isBracket(tt, true) && (tt.getWhitespacesBeforeCount() < 3) && res.get(res.size() - 1).typ == ItemType.APPENDIX) {
                            res.get(res.size() - 1).setEndToken(br.getEndToken());
                            res.get(res.size() - 1).name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                            tt = br.getEndToken().getNext();
                            continue;
                        }
                    }
                }
                if (tt.isNewlineBefore()) {
                    if (res.size() == 1 && res.get(0).isNewlineBefore()) 
                        break;
                    if (tt.getPrevious() != null && tt.getPrevious().isCommaAnd()) {
                    }
                    else 
                        break;
                }
                if ((tt instanceof com.pullenti.ner.NumberToken) && delim) {
                    p0 = null;
                    if (p.typ == ItemType.CLAUSE || inBracket) 
                        p0 = _new860(tt, tt, ItemType.CLAUSE);
                    else if (res.size() > 1 && res.get(res.size() - 2).typ == ItemType.CLAUSE && res.get(res.size() - 1).typ == ItemType.PART) 
                        p0 = _new860(tt, tt, ItemType.CLAUSE);
                    else if ((res.size() > 2 && res.get(res.size() - 3).typ == ItemType.CLAUSE && res.get(res.size() - 2).typ == ItemType.PART) && res.get(res.size() - 1).typ == ItemType.ITEM) 
                        p0 = _new860(tt, tt, ItemType.CLAUSE);
                    else if (res.size() > 0 && res.get(res.size() - 1).values.size() > 0 && (res.get(res.size() - 1).values.get(0).value.indexOf(".") >= 0)) 
                        p0 = _new860(tt, tt, res.get(res.size() - 1).typ);
                    if (p0 == null) 
                        break;
                    PartValue vv = PartValue._new1110(tt, tt, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    p0.values.add(vv);
                    vv.correctValue();
                    p0.setEndToken(vv.getEndToken());
                    tt = p0.getEndToken().getNext();
                    if (tt != null && tt.isHiphen() && (tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        tt = tt.getNext();
                        vv = PartValue._new1110(tt, tt, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        vv.correctValue();
                        p0.values.add(vv);
                        p0.setEndToken(vv.getEndToken());
                        tt = p0.getEndToken().getNext();
                    }
                }
            }
            if (tt != null && tt.isChar(',') && !tt.isNewlineAfter()) {
                PartToken p1 = tryAttach(tt.getNext(), p, false, false);
                if (p1 != null && _getRank(p1.typ) > 0 && _getRank(p.typ) > 0) {
                    if (_getRank(p1.typ) < _getRank(p.typ)) 
                        p0 = p1;
                }
            }
            if (p0 == null) 
                break;
            if (p0.isNewlineBefore() && res.size() == 1 && res.get(0).isNewlineBefore()) 
                break;
            if (p0.typ == ItemType.ITEM && p.typ == ItemType.ITEM) {
                if (p0.altTyp == ItemType.UNDEFINED && p.altTyp == ItemType.SUBITEM) {
                    p.typ = ItemType.SUBITEM;
                    p.altTyp = ItemType.UNDEFINED;
                }
                else if (p.altTyp == ItemType.UNDEFINED && p0.altTyp == ItemType.SUBITEM) {
                    p0.typ = ItemType.SUBITEM;
                    p0.altTyp = ItemType.UNDEFINED;
                }
            }
            p = p0;
            res.add(p);
            tt = p.getEndToken().getNext();
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == ItemType.PART && res.get(i + 1).typ == ItemType.PART && res.get(i).values.size() > 1) {
                int v1 = res.get(i).values.get(res.get(i).values.size() - 2).getIntValue();
                int v2 = res.get(i).values.get(res.get(i).values.size() - 1).getIntValue();
                if (v1 == 0 || v2 == 0) 
                    continue;
                if ((v2 - v1) < 10) 
                    continue;
                PartToken pt = _new860(res.get(i).getEndToken(), res.get(i).getEndToken(), ItemType.CLAUSE);
                pt.values.add(PartValue._new1110(res.get(i).getEndToken(), res.get(i).getEndToken(), ((Integer)v2).toString()));
                res.get(i).values.remove(res.get(i).values.size() - 1);
                if (res.get(i).getEndToken() != res.get(i).getBeginToken()) 
                    res.get(i).setEndToken(res.get(i).getEndToken().getPrevious());
                res.add(i + 1, pt);
            }
        }
        if ((res.size() == 1 && res.get(0).typ == ItemType.SUBPROGRAM && res.get(0).getEndToken().getNext() != null) && res.get(0).getEndToken().getNext().isChar('.')) 
            res.get(0).setEndToken(res.get(0).getEndToken().getNext());
        for (int i = res.size() - 1; i >= 0; i--) {
            p = res.get(i);
            if (p.isNewlineAfter() && p.isNewlineBefore() && p.typ != ItemType.SUBPROGRAM) {
                for(int jjj = i + res.size() - i - 1, mmm = i; jjj >= mmm; jjj--) res.remove(jjj);
                continue;
            }
            if (((i == 0 && ((p.isNewlineBefore() || ((p.getBeginToken().getPrevious() != null && p.getBeginToken().getPrevious().isTableControlChar())))) && p.hasTerminator) && p.getEndToken().getNext() != null && p.getEndToken().getNext().isChar('.')) && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(p.getEndToken().getNext().getNext())) {
                res.remove(i);
                continue;
            }
        }
        return (res.size() == 0 ? null : res);
    }

    public boolean canBeNextNarrow(PartToken p) {
        if (typ == p.typ) {
            if (typ != ItemType.SUBITEM) 
                return false;
            if (p.values != null && p.values.size() > 0 && p.values.get(0).getIntValue() == 0) {
                if (values != null && values.size() > 0 && values.get(0).getIntValue() > 0) 
                    return true;
            }
            return false;
        }
        if (typ == ItemType.PART || p.typ == ItemType.PART) 
            return true;
        int i1 = _getRank(typ);
        int i2 = _getRank(p.typ);
        if (i1 >= 0 && i2 >= 0) 
            return i1 < i2;
        return false;
    }

    public static boolean isPartBefore(com.pullenti.ner.Token t0) {
        if (t0 == null) 
            return false;
        int i = 0;
        for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if (tt.isNewlineAfter() || (tt instanceof com.pullenti.ner.ReferentToken)) 
                break;
            else {
                PartToken st = PartToken.tryAttach(tt, null, false, false);
                if (st != null) {
                    if (st.getEndToken().getNext() == t0) 
                        return true;
                    break;
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) {
                    if ((++i) > 2) 
                        break;
                }
            }
        }
        return false;
    }

    public static int _getRank(ItemType t) {
        if (t == ItemType.DOCPART) 
            return 1;
        if (t == ItemType.APPENDIX) 
            return 1;
        if (t == ItemType.SECTION) 
            return 2;
        if (t == ItemType.SUBPROGRAM) 
            return 2;
        if (t == ItemType.SUBSECTION) 
            return 3;
        if (t == ItemType.CHAPTER) 
            return 4;
        if (t == ItemType.PREAMBLE) 
            return 5;
        if (t == ItemType.PARAGRAPH) 
            return 5;
        if (t == ItemType.SUBPARAGRAPH) 
            return 6;
        if (t == ItemType.PAGE) 
            return 6;
        if (t == ItemType.CLAUSE) 
            return 7;
        if (t == ItemType.PART) 
            return 8;
        if (t == ItemType.NOTICE) 
            return 8;
        if (t == ItemType.ITEM) 
            return 9;
        if (t == ItemType.SUBITEM) 
            return 10;
        if (t == ItemType.INDENTION) 
            return 11;
        if (t == ItemType.SUBINDENTION) 
            return 12;
        return 0;
    }

    public static String _getAttrNameByTyp(ItemType _typ) {
        if (_typ == ItemType.CHAPTER) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_CHAPTER;
        if (_typ == ItemType.APPENDIX) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_APPENDIX;
        if (_typ == ItemType.CLAUSE) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE;
        if (_typ == ItemType.INDENTION) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_INDENTION;
        if (_typ == ItemType.ITEM) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_ITEM;
        if (_typ == ItemType.PARAGRAPH) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_PARAGRAPH;
        if (_typ == ItemType.SUBPARAGRAPH) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPARAGRAPH;
        if (_typ == ItemType.PART) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_PART;
        if (_typ == ItemType.SECTION) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SECTION;
        if (_typ == ItemType.SUBSECTION) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBSECTION;
        if (_typ == ItemType.SUBINDENTION) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBINDENTION;
        if (_typ == ItemType.SUBITEM) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBITEM;
        if (_typ == ItemType.PREAMBLE) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_PREAMBLE;
        if (_typ == ItemType.NOTICE) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_NOTICE;
        if (_typ == ItemType.SUBPROGRAM) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPROGRAM;
        if (_typ == ItemType.ADDAGREE) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_ADDAGREE;
        if (_typ == ItemType.DOCPART) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_DOCPART;
        if (_typ == ItemType.PAGE) 
            return com.pullenti.ner.decree.DecreePartReferent.ATTR_PAGE;
        return null;
    }

    public static com.pullenti.ner.instrument.InstrumentKind _getInstrKindByTyp(ItemType _typ) {
        if (_typ == ItemType.CHAPTER) 
            return com.pullenti.ner.instrument.InstrumentKind.CHAPTER;
        if (_typ == ItemType.APPENDIX) 
            return com.pullenti.ner.instrument.InstrumentKind.APPENDIX;
        if (_typ == ItemType.CLAUSE) 
            return com.pullenti.ner.instrument.InstrumentKind.CLAUSE;
        if (_typ == ItemType.INDENTION) 
            return com.pullenti.ner.instrument.InstrumentKind.INDENTION;
        if (_typ == ItemType.ITEM) 
            return com.pullenti.ner.instrument.InstrumentKind.ITEM;
        if (_typ == ItemType.PARAGRAPH) 
            return com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH;
        if (_typ == ItemType.SUBPARAGRAPH) 
            return com.pullenti.ner.instrument.InstrumentKind.SUBPARAGRAPH;
        if (_typ == ItemType.PART) 
            return com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART;
        if (_typ == ItemType.SECTION) 
            return com.pullenti.ner.instrument.InstrumentKind.SECTION;
        if (_typ == ItemType.SUBSECTION) 
            return com.pullenti.ner.instrument.InstrumentKind.SUBSECTION;
        if (_typ == ItemType.SUBITEM) 
            return com.pullenti.ner.instrument.InstrumentKind.SUBITEM;
        if (_typ == ItemType.PREAMBLE) 
            return com.pullenti.ner.instrument.InstrumentKind.PREAMBLE;
        if (_typ == ItemType.NOTICE) 
            return com.pullenti.ner.instrument.InstrumentKind.NOTICE;
        if (_typ == ItemType.DOCPART) 
            return com.pullenti.ner.instrument.InstrumentKind.DOCPART;
        return com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;
    }

    public static ItemType _getTypeByAttrName(String _name) {
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_CHAPTER)) 
            return ItemType.CHAPTER;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_APPENDIX)) 
            return ItemType.APPENDIX;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE)) 
            return ItemType.CLAUSE;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_INDENTION)) 
            return ItemType.INDENTION;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_ITEM)) 
            return ItemType.ITEM;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_PARAGRAPH)) 
            return ItemType.PARAGRAPH;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPARAGRAPH)) 
            return ItemType.SUBPARAGRAPH;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_PART)) 
            return ItemType.PART;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SECTION)) 
            return ItemType.SECTION;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBSECTION)) 
            return ItemType.SUBSECTION;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBINDENTION)) 
            return ItemType.SUBINDENTION;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBITEM)) 
            return ItemType.SUBITEM;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_NOTICE)) 
            return ItemType.NOTICE;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_PREAMBLE)) 
            return ItemType.PREAMBLE;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPROGRAM)) 
            return ItemType.SUBPROGRAM;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_ADDAGREE)) 
            return ItemType.ADDAGREE;
        if (com.pullenti.unisharp.Utils.stringsEq(_name, com.pullenti.ner.decree.DecreePartReferent.ATTR_DOCPART)) 
            return ItemType.DOCPART;
        return ItemType.PREFIX;
    }

    public static java.util.ArrayList<com.pullenti.ner.decree.DecreePartReferent> tryCreateBetween(com.pullenti.ner.decree.DecreePartReferent p1, com.pullenti.ner.decree.DecreePartReferent p2) {
        String notEqAttr = null;
        String val1 = null;
        String val2 = null;
        for (com.pullenti.ner.Slot s1 : p1.getSlots()) {
            if (p2.findSlot(s1.getTypeName(), s1.getValue(), true) != null) 
                continue;
            else {
                if (notEqAttr != null) 
                    return null;
                val2 = p2.getStringValue(s1.getTypeName());
                if (val2 == null) 
                    return null;
                notEqAttr = s1.getTypeName();
                val1 = (String)com.pullenti.unisharp.Utils.cast(s1.getValue(), String.class);
            }
        }
        if (val1 == null || val2 == null) 
            return null;
        java.util.ArrayList<String> diap = com.pullenti.ner.instrument.internal.NumberingHelper.createDiap(val1, val2);
        if (diap == null || (diap.size() < 3)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.decree.DecreePartReferent> res = new java.util.ArrayList<com.pullenti.ner.decree.DecreePartReferent>();
        for (int i = 1; i < (diap.size() - 1); i++) {
            com.pullenti.ner.decree.DecreePartReferent dpr = new com.pullenti.ner.decree.DecreePartReferent();
            for (com.pullenti.ner.Slot s : p1.getSlots()) {
                Object val = s.getValue();
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), notEqAttr)) 
                    val = diap.get(i);
                dpr.addSlot(s.getTypeName(), val, false, 0);
            }
            res.add(dpr);
        }
        return res;
    }

    public static int getNumber(String str) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1168 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1169 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1168);
        i = (wrapi1168.value != null ? wrapi1168.value : 0);
        if (inoutres1169) 
            return i;
        if (!Character.isLetter(str.charAt(0))) 
            return 0;
        char ch = Character.toUpperCase(str.charAt(0));
        if (((int)ch) < 0x80) {
            i = (((int)ch) - ((int)'A')) + 1;
            if ((ch == 'Z' && str.length() > 2 && str.charAt(1) == '.') && Character.isDigit(str.charAt(2))) {
                int n;
                com.pullenti.unisharp.Outargwrapper<Integer> wrapn1164 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres1165 = com.pullenti.unisharp.Utils.parseInteger(str.substring(2), 0, null, wrapn1164);
                n = (wrapn1164.value != null ? wrapn1164.value : 0);
                if (inoutres1165) 
                    i += n;
            }
        }
        else if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) {
            i = ruNums.indexOf(ch);
            if (i < 0) 
                return 0;
            i++;
            if ((ch == 'Я' && str.length() > 2 && str.charAt(1) == '.') && Character.isDigit(str.charAt(2))) {
                int n;
                com.pullenti.unisharp.Outargwrapper<Integer> wrapn1166 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres1167 = com.pullenti.unisharp.Utils.parseInteger(str.substring(2), 0, null, wrapn1166);
                n = (wrapn1166.value != null ? wrapn1166.value : 0);
                if (inoutres1167) 
                    i += n;
            }
        }
        if (i < 0) 
            return 0;
        return i;
    }

    private static String ruNums = "АБВГДЕЖЗИКЛМНОПРСТУФХЦЧШЩЭЮЯ";

    public static PartToken _new860(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3) {
        PartToken res = new PartToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static PartToken _new1113(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, boolean _arg4) {
        PartToken res = new PartToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static PartToken _new1140(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, ItemType _arg4) {
        PartToken res = new PartToken(_arg1, _arg2);
        res.typ = _arg3;
        res.altTyp = _arg4;
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType UNDEFINED; // 0
    
        public static final ItemType PREFIX; // 1
    
        public static final ItemType APPENDIX; // 2
    
        public static final ItemType DOCPART; // 3
    
        public static final ItemType PART; // 4
    
        public static final ItemType SECTION; // 5
    
        public static final ItemType SUBSECTION; // 6
    
        public static final ItemType CHAPTER; // 7
    
        public static final ItemType CLAUSE; // 8
    
        public static final ItemType PARAGRAPH; // 9
    
        public static final ItemType SUBPARAGRAPH; // 10
    
        public static final ItemType ITEM; // 11
    
        public static final ItemType SUBITEM; // 12
    
        public static final ItemType INDENTION; // 13
    
        public static final ItemType SUBINDENTION; // 14
    
        public static final ItemType PREAMBLE; // 15
    
        public static final ItemType NOTICE; // 16
    
        public static final ItemType SUBPROGRAM; // 17
    
        public static final ItemType PAGE; // 18
    
        public static final ItemType ADDAGREE; // 19
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(ItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ItemType> mapIntToEnum; 
        private static java.util.HashMap<String, ItemType> mapStringToEnum; 
        private static ItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ItemType item = new ItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ItemType>();
            mapStringToEnum = new java.util.HashMap<String, ItemType>();
            UNDEFINED = new ItemType(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            PREFIX = new ItemType(1, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            APPENDIX = new ItemType(2, "APPENDIX");
            mapIntToEnum.put(APPENDIX.value(), APPENDIX);
            mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
            DOCPART = new ItemType(3, "DOCPART");
            mapIntToEnum.put(DOCPART.value(), DOCPART);
            mapStringToEnum.put(DOCPART.m_str.toUpperCase(), DOCPART);
            PART = new ItemType(4, "PART");
            mapIntToEnum.put(PART.value(), PART);
            mapStringToEnum.put(PART.m_str.toUpperCase(), PART);
            SECTION = new ItemType(5, "SECTION");
            mapIntToEnum.put(SECTION.value(), SECTION);
            mapStringToEnum.put(SECTION.m_str.toUpperCase(), SECTION);
            SUBSECTION = new ItemType(6, "SUBSECTION");
            mapIntToEnum.put(SUBSECTION.value(), SUBSECTION);
            mapStringToEnum.put(SUBSECTION.m_str.toUpperCase(), SUBSECTION);
            CHAPTER = new ItemType(7, "CHAPTER");
            mapIntToEnum.put(CHAPTER.value(), CHAPTER);
            mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
            CLAUSE = new ItemType(8, "CLAUSE");
            mapIntToEnum.put(CLAUSE.value(), CLAUSE);
            mapStringToEnum.put(CLAUSE.m_str.toUpperCase(), CLAUSE);
            PARAGRAPH = new ItemType(9, "PARAGRAPH");
            mapIntToEnum.put(PARAGRAPH.value(), PARAGRAPH);
            mapStringToEnum.put(PARAGRAPH.m_str.toUpperCase(), PARAGRAPH);
            SUBPARAGRAPH = new ItemType(10, "SUBPARAGRAPH");
            mapIntToEnum.put(SUBPARAGRAPH.value(), SUBPARAGRAPH);
            mapStringToEnum.put(SUBPARAGRAPH.m_str.toUpperCase(), SUBPARAGRAPH);
            ITEM = new ItemType(11, "ITEM");
            mapIntToEnum.put(ITEM.value(), ITEM);
            mapStringToEnum.put(ITEM.m_str.toUpperCase(), ITEM);
            SUBITEM = new ItemType(12, "SUBITEM");
            mapIntToEnum.put(SUBITEM.value(), SUBITEM);
            mapStringToEnum.put(SUBITEM.m_str.toUpperCase(), SUBITEM);
            INDENTION = new ItemType(13, "INDENTION");
            mapIntToEnum.put(INDENTION.value(), INDENTION);
            mapStringToEnum.put(INDENTION.m_str.toUpperCase(), INDENTION);
            SUBINDENTION = new ItemType(14, "SUBINDENTION");
            mapIntToEnum.put(SUBINDENTION.value(), SUBINDENTION);
            mapStringToEnum.put(SUBINDENTION.m_str.toUpperCase(), SUBINDENTION);
            PREAMBLE = new ItemType(15, "PREAMBLE");
            mapIntToEnum.put(PREAMBLE.value(), PREAMBLE);
            mapStringToEnum.put(PREAMBLE.m_str.toUpperCase(), PREAMBLE);
            NOTICE = new ItemType(16, "NOTICE");
            mapIntToEnum.put(NOTICE.value(), NOTICE);
            mapStringToEnum.put(NOTICE.m_str.toUpperCase(), NOTICE);
            SUBPROGRAM = new ItemType(17, "SUBPROGRAM");
            mapIntToEnum.put(SUBPROGRAM.value(), SUBPROGRAM);
            mapStringToEnum.put(SUBPROGRAM.m_str.toUpperCase(), SUBPROGRAM);
            PAGE = new ItemType(18, "PAGE");
            mapIntToEnum.put(PAGE.value(), PAGE);
            mapStringToEnum.put(PAGE.m_str.toUpperCase(), PAGE);
            ADDAGREE = new ItemType(19, "ADDAGREE");
            mapIntToEnum.put(ADDAGREE.value(), ADDAGREE);
            mapStringToEnum.put(ADDAGREE.m_str.toUpperCase(), ADDAGREE);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public PartToken() {
        super();
        if(_globalInstance == null) return;
        altTyp = ItemType.UNDEFINED;
    }
    public static PartToken _globalInstance;
    
    static {
        try { _globalInstance = new PartToken(); } 
        catch(Exception e) { }
    }
}
