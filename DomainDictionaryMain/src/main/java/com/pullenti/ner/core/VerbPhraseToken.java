/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - глагольная группа (последовательность глаголов, наречий и причастий). 
 * Создаётся методом VerbPhraseHelper.TryParse.
 * Глагольная группа
 */
public class VerbPhraseToken extends com.pullenti.ner.MetaToken {

    public VerbPhraseToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Элементы глагольной группы - список VerbPhraseItemToken
     */
    public java.util.ArrayList<VerbPhraseItemToken> items = new java.util.ArrayList<VerbPhraseItemToken>();

    public VerbPhraseItemToken getFirstVerb() {
        for (VerbPhraseItemToken it : items) {
            if (!it.isAdverb) 
                return it;
        }
        return null;
    }


    public VerbPhraseItemToken getLastVerb() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isAdverb) 
                return items.get(i);
        }
        return null;
    }


    /**
     * Предлог перед (для причастий)
     */
    public PrepositionToken preposition;

    public boolean isVerbPassive() {
        VerbPhraseItemToken fi = getFirstVerb();
        if (fi == null || fi.getVerbMorph() == null) 
            return false;
        return fi.getVerbMorph().misc.getVoice() == com.pullenti.morph.MorphVoice.PASSIVE;
    }


    public void mergeWith(VerbPhraseToken v) {
        com.pullenti.unisharp.Utils.addToArrayList(items, v.items);
        setEndToken(v.getEndToken());
    }

    @Override
    public String toString() {
        if (items.size() == 1) 
            return (items.get(0).toString() + ", " + this.getMorph().toString());
        StringBuilder tmp = new StringBuilder();
        for (VerbPhraseItemToken it : items) {
            if (tmp.length() > 0) 
                tmp.append(' ');
            tmp.append(it);
        }
        tmp.append(", ").append(this.getMorph().toString());
        return tmp.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return super.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, num, gender, keepChars);
    }
    public VerbPhraseToken() {
        super();
    }
}
