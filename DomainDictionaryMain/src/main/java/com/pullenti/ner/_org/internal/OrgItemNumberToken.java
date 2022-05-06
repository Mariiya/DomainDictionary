/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemNumberToken extends com.pullenti.ner.MetaToken {

    public OrgItemNumberToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public String number;

    @Override
    public String toString() {
        return ("№ " + ((number != null ? number : "?")));
    }

    public static OrgItemNumberToken tryAttach(com.pullenti.ner.Token t, boolean canBePureNumber, OrgItemTypeToken typ) {
        if (t == null) 
            return null;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt != null) {
            com.pullenti.ner.Token t1 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt);
            if ((t1 instanceof com.pullenti.ner.NumberToken) && !t1.isNewlineBefore()) {
                OrgItemNumberToken res = _new1990(tt, t1, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue().toString());
                if (t1.getNext() != null && t1.getNext().isCharOf("\\/") && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    if (typ != null && ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "офис") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "банк") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "отделение")))) {
                        res.setEndToken(res.getEndToken().getNext().getNext());
                        res.number = (res.number + "/" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue());
                    }
                }
                return res;
            }
        }
        if ((t.isHiphen() && (t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceBefore()) && !t.isWhitespaceAfter()) {
            if (com.pullenti.ner.core.NumberHelper.tryParseAge(t.getNext()) == null) 
                return _new1990(t, t.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            if ((!t.isWhitespaceBefore() && t.getPrevious() != null && t.getPrevious().isHiphen())) 
                return _new1990(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            if (typ != null && typ.typ != null && (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "войсковая часть") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "військова частина") || (typ.typ.indexOf("колония") >= 0)) || (typ.typ.indexOf("колонія") >= 0) || (typ.typ.indexOf("школа") >= 0)))) {
                if (t.getLengthChar() >= 4 || t.getLengthChar() <= 6) {
                    OrgItemNumberToken res = _new1990(t, t, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    if (t.getNext() != null && ((t.getNext().isHiphen() || t.getNext().isCharOf("\\/"))) && !t.getNext().isWhitespaceAfter()) {
                        if ((t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((t.getLengthChar() + t.getNext().getNext().getLengthChar()) < 9)) {
                            res.setEndToken(t.getNext().getNext());
                            res.number = (res.number + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue());
                        }
                        else if ((t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getNext().getLengthChar() == 1 && t.getNext().getNext().chars.isLetter()) {
                            res.setEndToken(t.getNext().getNext());
                            res.number = (res.number + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class)).term);
                        }
                    }
                    else if (((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getLengthChar() == 1 && t.getNext().chars.isLetter()) && !t.isWhitespaceAfter()) {
                        res.setEndToken(t.getNext());
                        res.number = (res.number + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class)).term);
                    }
                    return res;
                }
            }
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.chars.isLetter()) && ((!t.isWhitespaceAfter() || (((t.getWhitespacesAfterCount() < 2) && t.chars.isAllUpper()))))) {
            if (typ != null && typ.typ != null && (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "войсковая часть") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "військова частина") || (typ.typ.indexOf("колония") >= 0)) || (typ.typ.indexOf("колонія") >= 0)))) {
                com.pullenti.ner.Token tt1 = t.getNext();
                if (tt1 != null && tt1.isHiphen()) 
                    tt1 = tt1.getNext();
                if (tt1 instanceof com.pullenti.ner.NumberToken) {
                    OrgItemNumberToken res = new OrgItemNumberToken(t, tt1);
                    res.number = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.NumberToken.class)).getValue());
                    return res;
                }
            }
        }
        return null;
    }

    public static OrgItemNumberToken _new1990(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        OrgItemNumberToken res = new OrgItemNumberToken(_arg1, _arg2);
        res.number = _arg3;
        return res;
    }
    public OrgItemNumberToken() {
        super();
    }
}
