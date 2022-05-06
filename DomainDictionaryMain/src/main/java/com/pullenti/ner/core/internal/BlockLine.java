/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class BlockLine extends com.pullenti.ner.MetaToken {

    public BlockLine(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public boolean isAllUpper;

    public boolean hasVerb;

    public boolean isExistName;

    public boolean hasContentItemTail;

    public int words;

    public int notWords;

    public com.pullenti.ner.Token numberEnd;

    public BlkTyps typ = BlkTyps.UNDEFINED;

    public static BlockLine create(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminCollection names) {
        if (t == null) 
            return null;
        BlockLine res = new BlockLine(t, t);
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt != t && tt.isNewlineBefore()) 
                break;
            else 
                res.setEndToken(tt);
        }
        int nums = 0;
        while (t != null && t.getNext() != null && t.getEndChar() <= res.getEndChar()) {
            if (t instanceof com.pullenti.ner.NumberToken) {
            }
            else {
                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                if (rom != null && rom.getEndToken().getNext() != null) 
                    t = rom.getEndToken();
                else 
                    break;
            }
            if (t.getNext().isChar('.')) {
            }
            else if ((t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()) {
            }
            else 
                break;
            res.numberEnd = t;
            t = t.getNext();
            if (t.isChar('.') && t.getNext() != null) {
                res.numberEnd = t;
                t = t.getNext();
            }
            if (t.isNewlineBefore()) 
                return res;
            nums++;
        }
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) {
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt1 != null && npt1.getEndToken() != npt1.getBeginToken()) 
                tok = m_Ontology.tryParse(npt1.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
        }
        if (tok != null) {
            if (t.getPrevious() != null && t.getPrevious().isChar(':')) 
                tok = null;
        }
        if (tok != null) {
            BlkTyps _typ = (BlkTyps)tok.termin.tag;
            if (_typ == BlkTyps.CONSLUSION) {
                if (t.isNewlineAfter()) {
                }
                else if (t.getNext() != null && t.getNext().getMorph()._getClass().isPreposition() && t.getNext().getNext() != null) {
                    com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(t.getNext().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null && ((BlkTyps)tok2.termin.tag) == BlkTyps.CHAPTER) {
                    }
                    else 
                        tok = null;
                }
                else 
                    tok = null;
            }
            if (t.kit.baseLanguage != t.getMorph().getLanguage()) 
                tok = null;
            if (_typ == BlkTyps.INDEX && !t.isValue("ОГЛАВЛЕНИЕ", null)) {
                if (!t.isNewlineAfter() && t.getNext() != null) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.isNewlineAfter() && npt.getMorph().getCase().isGenitive()) 
                        tok = null;
                    else if (npt == null) 
                        tok = null;
                }
            }
            if ((_typ == BlkTyps.INTRO && tok != null && !tok.isNewlineAfter()) && t.isValue("ВВЕДЕНИЕ", null)) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getMorph().getCase().isGenitive()) 
                    tok = null;
            }
            if (tok != null) {
                if (res.numberEnd == null) {
                    res.numberEnd = tok.getEndToken();
                    if (res.numberEnd.getEndChar() > res.getEndChar()) 
                        res.setEndToken(res.numberEnd);
                }
                res.typ = _typ;
                t = tok.getEndToken();
                if (t.getNext() != null && t.getNext().isCharOf(":.")) {
                    t = t.getNext();
                    res.setEndToken(t);
                }
                if (t.isNewlineAfter() || t.getNext() == null) 
                    return res;
                t = t.getNext();
            }
        }
        if (t.isChar('§') && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
            res.typ = BlkTyps.CHAPTER;
            res.numberEnd = t;
            t = t.getNext();
        }
        if (names != null) {
            com.pullenti.ner.core.TerminToken tok2 = names.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok2 != null && tok2.getEndToken().isNewlineAfter()) {
                res.setEndToken(tok2.getEndToken());
                res.isExistName = true;
                if (res.typ == BlkTyps.UNDEFINED) {
                    BlockLine li2 = create((res.numberEnd == null ? null : res.numberEnd.getNext()), null);
                    if (li2 != null && ((li2.typ == BlkTyps.LITERATURE || li2.typ == BlkTyps.INTRO || li2.typ == BlkTyps.CONSLUSION))) 
                        res.typ = li2.typ;
                    else 
                        res.typ = BlkTyps.CHAPTER;
                }
                return res;
            }
        }
        com.pullenti.ner.Token t1 = res.getEndToken();
        if ((((t1 instanceof com.pullenti.ner.NumberToken) || t1.isChar('.'))) && t1.getPrevious() != null) {
            t1 = t1.getPrevious();
            if (t1.isChar('.')) {
                res.hasContentItemTail = true;
                for (; t1 != null && t1.getBeginChar() > res.getBeginChar(); t1 = t1.getPrevious()) {
                    if (!t1.isChar('.')) 
                        break;
                }
            }
        }
        res.isAllUpper = true;
        for (; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
            if (!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isLetter()) 
                res.notWords++;
            else {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isUndefined()) 
                    res.notWords++;
                else if (t.getLengthChar() > 2) 
                    res.words++;
                if (!t.chars.isAllUpper()) 
                    res.isAllUpper = false;
                if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) {
                    if (!((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.endsWith("ING")) 
                        res.hasVerb = true;
                }
            }
        }
        if (res.typ == BlkTyps.UNDEFINED) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse((res.numberEnd == null ? res.getBeginToken() : res.numberEnd.getNext()), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if (npt.noun.isValue("ХАРАКТЕРИСТИКА", null) || npt.noun.isValue("СОДЕРЖАНИЕ", "ЗМІСТ")) {
                    boolean ok = true;
                    for (com.pullenti.ner.Token tt = npt.getEndToken().getNext(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
                        if (tt.isChar('.')) 
                            continue;
                        com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt2 == null || !npt2.getMorph().getCase().isGenitive()) {
                            ok = false;
                            break;
                        }
                        tt = npt2.getEndToken();
                        if (tt.getEndChar() > res.getEndChar()) {
                            res.setEndToken(tt);
                            if (!tt.isNewlineAfter()) {
                                for (; res.getEndToken().getNext() != null; res.setEndToken(res.getEndToken().getNext())) {
                                    if (res.getEndToken().isNewlineAfter()) 
                                        break;
                                }
                            }
                        }
                    }
                    if (ok) {
                        res.typ = BlkTyps.INTRO;
                        res.isExistName = true;
                    }
                }
                else if (npt.noun.isValue("ВЫВОД", "ВИСНОВОК") || npt.noun.isValue("РЕЗУЛЬТАТ", "ДОСЛІДЖЕННЯ")) {
                    boolean ok = true;
                    for (com.pullenti.ner.Token tt = npt.getEndToken().getNext(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
                        if (tt.isCharOf(",.") || tt.isAnd()) 
                            continue;
                        com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt1 != null) {
                            if (npt1.noun.isValue("РЕЗУЛЬТАТ", "ДОСЛІДЖЕННЯ") || npt1.noun.isValue("РЕКОМЕНДАЦИЯ", "РЕКОМЕНДАЦІЯ") || npt1.noun.isValue("ИССЛЕДОВАНИЕ", "ДОСЛІДЖЕННЯ")) {
                                tt = npt1.getEndToken();
                                if (tt.getEndChar() > res.getEndChar()) {
                                    res.setEndToken(tt);
                                    if (!tt.isNewlineAfter()) {
                                        for (; res.getEndToken().getNext() != null; res.setEndToken(res.getEndToken().getNext())) {
                                            if (res.getEndToken().isNewlineAfter()) 
                                                break;
                                        }
                                    }
                                }
                                continue;
                            }
                        }
                        ok = false;
                        break;
                    }
                    if (ok) {
                        res.typ = BlkTyps.CONSLUSION;
                        res.isExistName = true;
                    }
                }
                if (res.typ == BlkTyps.UNDEFINED && npt != null && npt.getEndChar() <= res.getEndChar()) {
                    boolean ok = false;
                    int publ = 0;
                    if (_isPub(npt)) {
                        ok = true;
                        publ = 1;
                    }
                    else if ((npt.noun.isValue("СПИСОК", null) || npt.noun.isValue("УКАЗАТЕЛЬ", "ПОКАЖЧИК") || npt.noun.isValue("ПОЛОЖЕНИЕ", "ПОЛОЖЕННЯ")) || npt.noun.isValue("ВЫВОД", "ВИСНОВОК") || npt.noun.isValue("РЕЗУЛЬТАТ", "ДОСЛІДЖЕННЯ")) {
                        if (npt.getEndChar() == res.getEndChar()) 
                            return null;
                        ok = true;
                    }
                    if (ok) {
                        if (npt.getBeginToken() == npt.getEndToken() && npt.noun.isValue("СПИСОК", null) && npt.getEndChar() == res.getEndChar()) 
                            ok = false;
                        for (com.pullenti.ner.Token tt = npt.getEndToken().getNext(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
                            if (tt.isCharOf(",.:") || tt.isAnd() || tt.getMorph()._getClass().isPreposition()) 
                                continue;
                            if (tt.isValue("ОТРАЖЕНЫ", "ВІДОБРАЖЕНІ")) 
                                continue;
                            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt == null) {
                                ok = false;
                                break;
                            }
                            if (((_isPub(npt) || npt.noun.isValue("РАБОТА", "РОБОТА") || npt.noun.isValue("ИССЛЕДОВАНИЕ", "ДОСЛІДЖЕННЯ")) || npt.noun.isValue("АВТОР", null) || npt.noun.isValue("ТРУД", "ПРАЦЯ")) || npt.noun.isValue("ТЕМА", null) || npt.noun.isValue("ДИССЕРТАЦИЯ", "ДИСЕРТАЦІЯ")) {
                                tt = npt.getEndToken();
                                if (_isPub(npt)) 
                                    publ++;
                                if (tt.getEndChar() > res.getEndChar()) {
                                    res.setEndToken(tt);
                                    if (!tt.isNewlineAfter()) {
                                        for (; res.getEndToken().getNext() != null; res.setEndToken(res.getEndToken().getNext())) {
                                            if (res.getEndToken().isNewlineAfter()) 
                                                break;
                                        }
                                    }
                                }
                                continue;
                            }
                            ok = false;
                            break;
                        }
                        if (ok) {
                            res.typ = BlkTyps.LITERATURE;
                            res.isExistName = true;
                            if (publ == 0 && (res.getEndChar() < (((res.kit.getSofa().getText().length() * 2) / 3)))) {
                                if (res.numberEnd != null) 
                                    res.typ = BlkTyps.MISC;
                                else 
                                    res.typ = BlkTyps.UNDEFINED;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private static boolean _isPub(com.pullenti.ner.core.NounPhraseToken t) {
        if (t == null) 
            return false;
        if (((t.noun.isValue("ПУБЛИКАЦИЯ", "ПУБЛІКАЦІЯ") || t.noun.isValue("REFERENCE", null) || t.noun.isValue("ЛИТЕРАТУРА", "ЛІТЕРАТУРА")) || t.noun.isValue("ИСТОЧНИК", "ДЖЕРЕЛО") || t.noun.isValue("БИБЛИОГРАФИЯ", "БІБЛІОГРАФІЯ")) || t.noun.isValue("ДОКУМЕНТ", null)) 
            return true;
        for (com.pullenti.ner.MetaToken a : t.adjectives) {
            if (a.isValue("БИБЛИОГРАФИЧЕСКИЙ", null)) 
                return true;
        }
        return false;
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"СОДЕРЖАНИЕ", "СОДЕРЖИМОЕ", "ОГЛАВЛЕНИЕ", "ПЛАН", "PLAN", "ЗМІСТ", "CONTENTS", "INDEX"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.INDEX));
        }
        for (String s : new String[] {"ГЛАВА", "CHAPTER", "РАЗДЕЛ", "ПАРАГРАФ", "VOLUME", "SECTION", "РОЗДІЛ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.CHAPTER));
        }
        for (String s : new String[] {"ВВЕДЕНИЕ", "ВСТУПЛЕНИЕ", "ПРЕДИСЛОВИЕ", "INTRODUCTION"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.INTRO));
        }
        for (String s : new String[] {"ВСТУП", "ПЕРЕДМОВА"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, BlkTyps.INTRO));
        }
        for (String s : new String[] {"ВЫВОДЫ", "ВЫВОД", "ЗАКЛЮЧЕНИЕ", "CONCLUSION", "ВИСНОВОК", "ВИСНОВКИ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.CONSLUSION));
        }
        for (String s : new String[] {"ПРИЛОЖЕНИЕ", "APPENDIX", "ДОДАТОК"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.APPENDIX));
        }
        for (String s : new String[] {"СПИСОК СОКРАЩЕНИЙ", "СПИСОК УСЛОВНЫХ СОКРАЩЕНИЙ", "СПИСОК ИСПОЛЬЗУЕМЫХ СОКРАЩЕНИЙ", "УСЛОВНЫЕ СОКРАЩЕНИЯ", "ОБЗОР ЛИТЕРАТУРЫ", "АННОТАЦИЯ", "ANNOTATION", "БЛАГОДАРНОСТИ", "SUPPLEMENT", "ABSTRACT", "СПИСОК СКОРОЧЕНЬ", "ПЕРЕЛІК УМОВНИХ СКОРОЧЕНЬ", "СПИСОК ВИКОРИСТОВУВАНИХ СКОРОЧЕНЬ", "УМОВНІ СКОРОЧЕННЯ", "ОГЛЯД ЛІТЕРАТУРИ", "АНОТАЦІЯ", "ПОДЯКИ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, BlkTyps.MISC));
        }
    }
    public BlockLine() {
        super();
    }
}
