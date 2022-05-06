/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Вариант расщепления именной группы, у которой слиплись существительные. 
 * Получается методом GetMultivars() у NounPhraseToken, у которой MultiNouns = true.
 * Расщепление именной группы
 */
public class NounPhraseMultivarToken extends com.pullenti.ner.MetaToken {

    public NounPhraseMultivarToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Исходная именная группа
     */
    public NounPhraseToken source;

    /**
     * Начальный индекс прилагательных (из списка Adjectives у Source), который относится к расщеплённой группе
     */
    public int adjIndex1;

    /**
     * Конечный индекс прилагательных (из списка Adjectives у Source), который относится к расщеплённой группе
     */
    public int adjIndex2;

    @Override
    public String toString() {
        return (source.adjectives.get(adjIndex1).toString() + " " + source.noun);
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        if (gender == com.pullenti.morph.MorphGender.UNDEFINED) 
            gender = source.getMorph().getGender();
        StringBuilder res = new StringBuilder();
        for (int k = adjIndex1; k <= adjIndex2; k++) {
            String adj = source.adjectives.get(k).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), num, gender, keepChars);
            if (adj == null || com.pullenti.unisharp.Utils.stringsEq(adj, "?")) 
                adj = MiscHelper.getTextValueOfMetaToken(source.adjectives.get(k), (keepChars ? GetTextAttr.KEEPREGISTER : GetTextAttr.NO));
            res.append(((adj != null ? adj : "?"))).append(" ");
        }
        String noun = null;
        if ((source.noun.getBeginToken() instanceof com.pullenti.ner.ReferentToken) && source.getBeginToken() == source.noun.getEndToken()) 
            noun = source.noun.getBeginToken().getNormalCaseText(null, num, gender, keepChars);
        else {
            com.pullenti.morph.MorphClass cas = com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN);
            if (mc != null && !mc.isUndefined()) 
                cas = mc;
            noun = source.noun.getNormalCaseText(cas, num, gender, keepChars);
        }
        if (noun == null || com.pullenti.unisharp.Utils.stringsEq(noun, "?")) 
            noun = source.noun.getNormalCaseText(null, num, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.append((noun != null ? noun : "?"));
        return res.toString();
    }

    public static NounPhraseMultivarToken _new513(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, NounPhraseToken _arg3, int _arg4, int _arg5) {
        NounPhraseMultivarToken res = new NounPhraseMultivarToken(_arg1, _arg2);
        res.source = _arg3;
        res.adjIndex1 = _arg4;
        res.adjIndex2 = _arg5;
        return res;
    }
    public NounPhraseMultivarToken() {
        super();
    }
}
