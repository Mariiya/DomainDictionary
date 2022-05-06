/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Выделение именных групп - это существительное с согласованными прилагательными (если они есть).
 * 
 * Хелпер именных групп
 */
public class NounPhraseHelper {

    /**
     * Попробовать создать именную группу с указанного токена
     * @param t начальный токен
     * @param attrs атрибуты (можно битовую маску)
     * @param maxCharPos максимальная позиция в тексте, до которой выделять (если 0, то без ограничений)
     * @param noun это если нужно выделить только прилагательные для ранее выделенного существительного (из другой группы)
     * @return именная группа или null
     */
    public static NounPhraseToken tryParse(com.pullenti.ner.Token t, NounPhraseParseAttr attrs, int maxCharPos, com.pullenti.ner.MetaToken noun) {
        if (t == null) 
            return null;
        if (attrs == NounPhraseParseAttr.NO && (t instanceof com.pullenti.ner.TextToken)) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt.noNpt) 
                return null;
            if (tt.npt != null) {
                boolean ok = true;
                for (com.pullenti.ner.Token ttt = tt; ttt != null && ttt.getBeginChar() <= tt.npt.getEndChar(); ttt = ttt.getNext()) {
                    if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                        ok = false;
                }
                if (ok) 
                    return tt.npt.clone();
            }
        }
        NounPhraseToken res = _NounPraseHelperInt.tryParse(t, attrs, maxCharPos, (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.core.internal.NounPhraseItem.class));
        if (res != null) {
            if (attrs == NounPhraseParseAttr.NO && (t instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                tt.noNpt = false;
                tt.npt = res;
            }
            if ((((attrs.value()) & (NounPhraseParseAttr.PARSEPREPOSITION.value()))) != (NounPhraseParseAttr.NO.value())) {
                if (res.getBeginToken() == res.getEndToken() && t.getMorph()._getClass().isPreposition()) {
                    PrepositionToken prep = PrepositionHelper.tryParse(t);
                    if (prep != null) {
                        NounPhraseToken res2 = _NounPraseHelperInt.tryParse(t.getNext(), attrs, maxCharPos, (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.core.internal.NounPhraseItem.class));
                        if (res2 != null) {
                            if (!((com.pullenti.morph.MorphCase.ooBitand(prep.nextCase, res2.getMorph().getCase()))).isUndefined()) {
                                res2.getMorph().removeItems(prep.nextCase);
                                res2.preposition = prep;
                                res2.setBeginToken(t);
                                return res2;
                            }
                        }
                    }
                }
            }
            return res;
        }
        if ((((attrs.value()) & (NounPhraseParseAttr.PARSEPREPOSITION.value()))) != (NounPhraseParseAttr.NO.value())) {
            PrepositionToken prep = PrepositionHelper.tryParse(t);
            if (prep != null && (prep.getNewlinesAfterCount() < 2)) {
                res = _NounPraseHelperInt.tryParse(prep.getEndToken().getNext(), attrs, maxCharPos, (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.core.internal.NounPhraseItem.class));
                if (res != null) {
                    res.preposition = prep;
                    res.setBeginToken(t);
                    if (!((com.pullenti.morph.MorphCase.ooBitand(prep.nextCase, res.getMorph().getCase()))).isUndefined()) 
                        res.getMorph().removeItems(prep.nextCase);
                    else if (t.getMorph()._getClass().isAdverb()) 
                        return null;
                    return res;
                }
            }
        }
        if (attrs == NounPhraseParseAttr.NO && (t instanceof com.pullenti.ner.TextToken)) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            tt.noNpt = true;
        }
        return null;
    }
    public NounPhraseHelper() {
    }
}
