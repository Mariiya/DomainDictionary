/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - именная группа (это существительное с возможными прилагательными, морфологичски согласованными). 
 * Выделяется методом TryParse() класса NounPhraseHelper.
 * 
 * Именная группа
 */
public class NounPhraseToken extends com.pullenti.ner.MetaToken {

    public NounPhraseToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Корень группы (существительное, местоимение или сущность)
     */
    public com.pullenti.ner.MetaToken noun;

    /**
     * Прилагательные (и причастия, если задан атрибут NounPhraseParseAttr.ParseVerbs)
     */
    public java.util.ArrayList<com.pullenti.ner.MetaToken> adjectives = new java.util.ArrayList<com.pullenti.ner.MetaToken>();

    /**
     * Наречия (если задан атрибут NounPhraseParseAttr.ParseAdverbs при выделении)
     */
    public java.util.ArrayList<com.pullenti.ner.TextToken> adverbs = null;

    /**
     * Внутренняя именная группа. Например, для случая "по современным на данный момент представлениям" 
     * это будет "данный момент"
     */
    public NounPhraseToken internalNoun;

    /**
     * Токен с анафорической ссылкой-местоимением (если есть), например: старшего своего брата
     */
    public com.pullenti.ner.Token anafor;

    // Используется внешней системой для нахождения анафоры
    public NounPhraseToken anaforaRef;

    /**
     * Начальный предлог (если задан атрибут NounPhraseParseAttr.ParsePreposition)
     */
    public PrepositionToken preposition;

    public NounPhraseToken clone() {
        NounPhraseToken res = new NounPhraseToken(getBeginToken(), getEndToken());
        res.setMorph(this.getMorph().clone());
        res.chars = chars;
        if (noun instanceof com.pullenti.ner.core.internal.NounPhraseItem) 
            res.noun = ((com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.core.internal.NounPhraseItem.class)).clone();
        else 
            res.noun = noun;
        res.internalNoun = internalNoun;
        if (adverbs != null) 
            res.adverbs = new java.util.ArrayList<com.pullenti.ner.TextToken>(adverbs);
        for (com.pullenti.ner.MetaToken a : adjectives) {
            if (a instanceof com.pullenti.ner.core.internal.NounPhraseItem) 
                res.adjectives.add(((com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(a, com.pullenti.ner.core.internal.NounPhraseItem.class)).clone());
            else 
                res.adjectives.add(a);
        }
        res.anafor = anafor;
        res.anaforaRef = anaforaRef;
        res.preposition = preposition;
        return res;
    }

    /**
     * Это когда Noun как бы слепленный для разных прилагательных (грузовой и легковой автомобили)
     */
    public boolean multiNouns;

    /**
     * Это если MultiNouns = true, то можно как бы расщепить на варианты 
     * (грузовой и легковой автомобили -> грузовой автомобиль и легковой автомобиль)
     * @return список NounPhraseMultivarToken
     */
    public java.util.ArrayList<NounPhraseMultivarToken> getMultivars() {
        java.util.ArrayList<NounPhraseMultivarToken> res = new java.util.ArrayList<NounPhraseMultivarToken>();
        for (int i = 0; i < adjectives.size(); i++) {
            NounPhraseMultivarToken v = NounPhraseMultivarToken._new513(adjectives.get(i).getBeginToken(), adjectives.get(i).getEndToken(), this, i, i);
            for (; i < (adjectives.size() - 1); i++) {
                if (adjectives.get(i + 1).getBeginToken() == adjectives.get(i).getEndToken().getNext()) {
                    v.setEndToken(adjectives.get(i + 1).getEndToken());
                    v.adjIndex2 = i + 1;
                }
                else 
                    break;
            }
            if (i == (adjectives.size() - 1)) 
                v.setEndToken(this.getEndToken());
            res.add(v);
        }
        return res;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        StringBuilder res = new StringBuilder();
        if (gender == com.pullenti.morph.MorphGender.UNDEFINED) 
            gender = this.getMorph().getGender();
        if (adverbs != null && adverbs.size() > 0) {
            int i = 0;
            if (adjectives.size() > 0) {
                for (int j = 0; j < adjectives.size(); j++) {
                    for (; i < adverbs.size(); i++) {
                        if (adverbs.get(i).getBeginChar() < adjectives.get(j).getBeginChar()) 
                            res.append(adverbs.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ADVERB, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false)).append(" ");
                        else 
                            break;
                    }
                    String s = adjectives.get(j).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), num, gender, keepChars);
                    res.append(((s != null ? s : "?"))).append(" ");
                }
            }
            for (; i < adverbs.size(); i++) {
                res.append(adverbs.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ADVERB, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false)).append(" ");
            }
        }
        else 
            for (com.pullenti.ner.MetaToken t : adjectives) {
                String s = t.getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), num, gender, keepChars);
                res.append(((s != null ? s : "?"))).append(" ");
            }
        String r = null;
        if ((noun.getBeginToken() instanceof com.pullenti.ner.ReferentToken) && noun.getBeginToken() == noun.getEndToken()) 
            r = noun.getBeginToken().getNormalCaseText(null, num, gender, keepChars);
        else {
            com.pullenti.morph.MorphClass cas = com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN);
            if (mc != null && !mc.isUndefined()) 
                cas = mc;
            r = noun.getNormalCaseText(cas, num, gender, keepChars);
        }
        if (r == null || com.pullenti.unisharp.Utils.stringsEq(r, "?")) 
            r = noun.getNormalCaseText(null, num, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.append((r != null ? r : noun.toString()));
        return res.toString();
    }

    public String getNormalCaseTextWithoutAdjective(int adjIndex) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < adjectives.size(); i++) {
            if (i != adjIndex) {
                String s = adjectives.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                res.append(((s != null ? s : "?"))).append(" ");
            }
        }
        String r = noun.getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN), com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (r == null) 
            r = noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.append((r != null ? r : noun.toString()));
        return res.toString();
    }

    /**
     * Сгенерировать текст именной группы в нужном падеже и числе
     * @param cas нужный падеж
     * @param plural нужное число
     * @return результирующая строка
     */
    public String getMorphVariant(com.pullenti.morph.MorphCase cas, boolean plural) {
        com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.MorphBaseInfo._new514(cas, com.pullenti.morph.MorphLang.RU);
        if (plural) 
            mi.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
        else 
            mi.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
        String res = null;
        for (com.pullenti.ner.MetaToken a : adjectives) {
            String tt = MiscHelper.getTextValueOfMetaToken(a, GetTextAttr.NO);
            if (a.getBeginToken() != a.getEndToken() || !(a.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
            }
            else {
                String tt2 = com.pullenti.morph.MorphologyService.getWordform(tt, mi);
                if (tt2 != null) 
                    tt = tt2;
            }
            if (res == null) 
                res = tt;
            else 
                res = (res + " " + tt);
        }
        if (noun != null) {
            String tt = MiscHelper.getTextValueOfMetaToken(noun, GetTextAttr.NO);
            if (noun.getBeginToken() != noun.getEndToken() || !(noun.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
            }
            else {
                String tt2 = com.pullenti.morph.MorphologyService.getWordform(tt, mi);
                if (tt2 != null) 
                    tt = tt2;
            }
            if (res == null) 
                res = tt;
            else 
                res = (res + " " + tt);
        }
        return res;
    }

    @Override
    public String toString() {
        if (internalNoun == null) 
            return (((String)com.pullenti.unisharp.Utils.notnull(this.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), "?")) + " " + this.getMorph().toString());
        else 
            return (((String)com.pullenti.unisharp.Utils.notnull(this.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), "?")) + " " + this.getMorph().toString() + " / " + internalNoun.toString());
    }

    public void removeLastNounWord() {
        if (noun != null) {
            for (com.pullenti.morph.MorphBaseInfo it : noun.getMorph().getItems()) {
                com.pullenti.ner.core.internal.NounPhraseItemTextVar ii = (com.pullenti.ner.core.internal.NounPhraseItemTextVar)com.pullenti.unisharp.Utils.cast(it, com.pullenti.ner.core.internal.NounPhraseItemTextVar.class);
                if (ii == null || ii.normalValue == null) 
                    continue;
                int j = ii.normalValue.indexOf('-');
                if (j > 0) 
                    ii.normalValue = ii.normalValue.substring(0, 0 + j);
                if (ii.singleNumberValue != null) {
                    if ((((j = ii.singleNumberValue.indexOf('-')))) > 0) 
                        ii.singleNumberValue = ii.singleNumberValue.substring(0, 0 + j);
                }
            }
        }
    }

    public static NounPhraseToken _new481(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PrepositionToken _arg3) {
        NounPhraseToken res = new NounPhraseToken(_arg1, _arg2);
        res.preposition = _arg3;
        return res;
    }

    public static NounPhraseToken _new3153(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        NounPhraseToken res = new NounPhraseToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public NounPhraseToken() {
        super();
    }
}
