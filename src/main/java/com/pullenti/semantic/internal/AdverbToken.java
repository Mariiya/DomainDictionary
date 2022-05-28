/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class AdverbToken extends com.pullenti.ner.MetaToken {

    public AdverbToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public com.pullenti.semantic.SemAttributeType typ = com.pullenti.semantic.SemAttributeType.UNDEFINED;

    public boolean not;

    public String getSpelling() {
        if (m_Spelling != null) 
            return m_Spelling;
        return com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(this, com.pullenti.ner.core.GetTextAttr.NO);
    }

    public String setSpelling(String value) {
        m_Spelling = value;
        return value;
    }


    private String m_Spelling;

    @Override
    public String toString() {
        if (typ == com.pullenti.semantic.SemAttributeType.UNDEFINED) 
            return getSpelling();
        return (typ.toString() + ": " + (not ? "НЕ " : "") + this.getSpelling());
    }

    public static AdverbToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if ((t instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "НЕ")) {
            AdverbToken nn = tryParse(t.getNext());
            if (nn != null) {
                nn.not = true;
                nn.setBeginToken(t);
                return nn;
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1;
        if (t.getNext() != null && t.getMorph()._getClass().isPreposition()) 
            t = t.getNext();
        if (t.isValue("ДРУГ", null) || t.isValue("САМ", null)) {
            t1 = t.getNext();
            if (t1 != null && t1.getMorph()._getClass().isPreposition()) 
                t1 = t1.getNext();
            if (t1 != null) {
                if (t1.isValue("ДРУГ", null) && t.isValue("ДРУГ", null)) 
                    return _new3088(t0, t1, com.pullenti.semantic.SemAttributeType.EACHOTHER);
                if (t1.isValue("СЕБЯ", null) && t.isValue("САМ", null)) 
                    return _new3088(t0, t1, com.pullenti.semantic.SemAttributeType.HIMELF);
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            AdverbToken res = _new3088(t0, tok.getEndToken(), (com.pullenti.semantic.SemAttributeType)tok.termin.tag);
            t = res.getEndToken().getNext();
            if (t != null && t.isComma()) 
                t = t.getNext();
            if (res.typ == com.pullenti.semantic.SemAttributeType.LESS || res.typ == com.pullenti.semantic.SemAttributeType.GREAT) {
                if (t != null && t.isValue("ЧЕМ", null)) 
                    res.setEndToken(t);
            }
            return res;
        }
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        if (mc.isAdverb()) 
            return new AdverbToken(t, t);
        if (t.isValue("ВСТРЕЧА", null) && t.getPrevious() != null && t.getPrevious().isValue("НА", null)) {
            AdverbToken ne = tryParse(t.getNext());
            if (ne != null && ne.typ == com.pullenti.semantic.SemAttributeType.EACHOTHER) 
                return new AdverbToken(t.getPrevious(), t);
        }
        return null;
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    public static void initialize() {
        if (m_Termins != null) 
            return;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new100("ЕЩЕ", com.pullenti.semantic.SemAttributeType.STILL);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("УЖЕ", com.pullenti.semantic.SemAttributeType.ALREADY);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВСЕ", com.pullenti.semantic.SemAttributeType.ALL);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЛЮБОЙ", com.pullenti.semantic.SemAttributeType.ANY);
        t.addVariant("ЛЮБОЙ", false);
        t.addVariant("КАЖДЫЙ", false);
        t.addVariant("ЧТО УГОДНО", false);
        t.addVariant("ВСЯКИЙ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("НЕКОТОРЫЙ", com.pullenti.semantic.SemAttributeType.SOME);
        t.addVariant("НЕКИЙ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ДРУГОЙ", com.pullenti.semantic.SemAttributeType.OTHER);
        t.addVariant("ИНОЙ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕСЬ", com.pullenti.semantic.SemAttributeType.WHOLE);
        t.addVariant("ЦЕЛИКОМ", false);
        t.addVariant("ПОЛНОСТЬЮ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОЧЕНЬ", com.pullenti.semantic.SemAttributeType.VERY);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("МЕНЬШЕ", com.pullenti.semantic.SemAttributeType.LESS);
        t.addVariant("МЕНЕЕ", false);
        t.addVariant("МЕНЕЕ", false);
        t.addVariant("МЕНЬШЕ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("БОЛЬШЕ", com.pullenti.semantic.SemAttributeType.GREAT);
        t.addVariant("БОЛЕЕ", false);
        t.addVariant("СВЫШЕ", false);
        m_Termins.add(t);
    }

    public static AdverbToken _new3088(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.semantic.SemAttributeType _arg3) {
        AdverbToken res = new AdverbToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public AdverbToken() {
        super();
    }
}
