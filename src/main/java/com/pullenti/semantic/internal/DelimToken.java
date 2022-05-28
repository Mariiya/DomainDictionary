/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class DelimToken extends com.pullenti.ner.MetaToken {

    public DelimToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public DelimType typ = DelimType.UNDEFINED;

    public boolean doublt;

    @Override
    public String toString() {
        return (typ.toString() + (doublt ? "?" : "") + ": " + super.toString());
    }

    public static DelimToken tryParse(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (t.isCommaAnd()) {
            DelimToken res0 = tryParse(t.getNext());
            if (res0 != null) {
                res0.setBeginToken(t);
                return res0;
            }
            return null;
        }
        com.pullenti.ner.core.TerminToken tok = m_Onto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            if (t.isValue("ХОТЕТЬ", null) && (t instanceof com.pullenti.ner.TextToken)) {
                if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ХОТЯ")) 
                    return null;
            }
            DelimToken res = new DelimToken(t, tok.getEndToken());
            res.typ = (DelimType)tok.termin.tag;
            res.doublt = tok.termin.tag2 != null;
            DelimToken res2 = tryParse(res.getEndToken().getNext());
            if (res2 != null) {
                if (res2.typ == res.typ) {
                    res.setEndToken(res2.getEndToken());
                    res.doublt = false;
                }
            }
            if (t.getMorph()._getClass().isPronoun()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS, 0, null);
                if (npt != null && npt.getEndChar() > res.getEndChar()) 
                    return null;
            }
            return res;
        }
        return null;
    }

    private static com.pullenti.ner.core.TerminCollection m_Onto;

    public static void initialize() {
        m_Onto = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new100("НО", DelimType.BUT);
        t.addVariant("А", false);
        t.addVariant("ОДНАКО", false);
        t.addVariant("ХОТЯ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЕСЛИ", DelimType.IF);
        t.addVariant("В СЛУЧАЕ ЕСЛИ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new102("КОГДА", DelimType.IF, m_Onto);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ТО", DelimType.THEN);
        t.addVariant("ТОГДА", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ИНАЧЕ", DelimType.ELSE);
        t.addVariant("В ПРОТИВНОМ СЛУЧАЕ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ТАК КАК", DelimType.BECAUSE);
        t.addVariant("ПОТОМУ ЧТО", false);
        t.addVariant("ПО ПРИЧИНЕ ТОГО ЧТО", false);
        t.addVariant("ИЗ ЗА ТОГО ЧТО", false);
        t.addVariant("ИЗЗА ТОГО ЧТО", false);
        t.addVariant("ИЗ-ЗА ТОГО ЧТО", false);
        t.addVariant("ТО ЕСТЬ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЧТОБЫ", DelimType.FOR);
        t.addVariant("ДЛЯ ТОГО ЧТОБЫ", false);
        m_Onto.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЧТО", DelimType.WHAT);
        m_Onto.add(t);
    }
    public DelimToken() {
        super();
    }
}
