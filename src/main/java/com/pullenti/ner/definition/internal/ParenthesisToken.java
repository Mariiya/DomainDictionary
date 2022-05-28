/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition.internal;

// Анализ вводных слов и словосочетаний
public class ParenthesisToken extends com.pullenti.ner.MetaToken {

    public ParenthesisToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public com.pullenti.ner.Referent ref;

    public static ParenthesisToken tryAttach(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            ParenthesisToken res = new ParenthesisToken(t, tok.getEndToken());
            return res;
        }
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        boolean ok = false;
        com.pullenti.ner.Token t1;
        if (mc.isAdverb()) 
            ok = true;
        else if (mc.isAdjective()) {
            if (t.getMorph().containsAttr("сравн.", null) && t.getMorph().containsAttr("кач.прил.", null)) 
                ok = true;
        }
        if (ok && t.getNext() != null) {
            if (t.getNext().isChar(',')) 
                return new ParenthesisToken(t, t);
            t1 = t.getNext();
            if (t1.getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB)) {
                if (t1.getMorph().containsAttr("н.вр.", null) && t1.getMorph().containsAttr("нес.в.", null) && t1.getMorph().containsAttr("дейст.з.", null)) 
                    return new ParenthesisToken(t, t1);
            }
        }
        t1 = null;
        if ((t.isValue("В", null) && t.getNext() != null && t.getNext().isValue("СООТВЕТСТВИЕ", null)) && t.getNext().getNext() != null && t.getNext().getNext().getMorph()._getClass().isPreposition()) 
            t1 = t.getNext().getNext().getNext();
        else if (t.isValue("СОГЛАСНО", null)) 
            t1 = t.getNext();
        else if (t.isValue("В", null) && t.getNext() != null) {
            if (t.getNext().isValue("СИЛА", null)) 
                t1 = t.getNext().getNext();
            else if (t.getNext().getMorph()._getClass().isAdjective() || t.getNext().getMorph()._getClass().isPronoun()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) {
                    if (npt.noun.isValue("ВИД", null) || npt.noun.isValue("СЛУЧАЙ", null) || npt.noun.isValue("СФЕРА", null)) 
                        return new ParenthesisToken(t, npt.getEndToken());
                }
            }
        }
        if (t1 != null) {
            if (t1.getNext() != null) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 != null) {
                    if (npt1.noun.isValue("НОРМА", null) || npt1.noun.isValue("ПОЛОЖЕНИЕ", null) || npt1.noun.isValue("УКАЗАНИЕ", null)) 
                        t1 = npt1.getEndToken().getNext();
                }
            }
            com.pullenti.ner.Referent r = t1.getReferent();
            if (r != null) {
                ParenthesisToken res = _new1188(t, t1, r);
                if (t1.getNext() != null && t1.getNext().isComma()) {
                    boolean sila = false;
                    for (com.pullenti.ner.Token ttt = t1.getNext().getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isValue("СИЛА", null) || ttt.isValue("ДЕЙСТВИЕ", null)) {
                            sila = true;
                            continue;
                        }
                        if (ttt.isComma()) {
                            if (sila) 
                                res.setEndToken(ttt.getPrevious());
                            break;
                        }
                        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt, false, false)) 
                            break;
                    }
                }
                return res;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) 
                return new ParenthesisToken(t, npt.getEndToken());
        }
        com.pullenti.ner.Token tt = t;
        if (tt.isValue("НЕ", null) && t != null) 
            tt = tt.getNext();
        if (tt.getMorph()._getClass().isPreposition() && tt != null) {
            tt = tt.getNext();
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt1 != null) {
                tt = npt1.getEndToken();
                if (tt.getNext() != null && tt.getNext().isComma()) 
                    return new ParenthesisToken(t, tt.getNext());
                if (npt1.noun.isValue("ОЧЕРЕДЬ", null)) 
                    return new ParenthesisToken(t, tt);
            }
        }
        if (t.isValue("ВЕДЬ", null)) 
            return new ParenthesisToken(t, t);
        return null;
    }

    public static void initialize() {
        if (m_Termins != null) 
            return;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"ИТАК", "СЛЕДОВАТЕЛЬНО", "ТАКИМ ОБРАЗОМ"}) {
            m_Termins.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    public static ParenthesisToken _new1188(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Referent _arg3) {
        ParenthesisToken res = new ParenthesisToken(_arg1, _arg2);
        res.ref = _arg3;
        return res;
    }
    public ParenthesisToken() {
        super();
    }
}
