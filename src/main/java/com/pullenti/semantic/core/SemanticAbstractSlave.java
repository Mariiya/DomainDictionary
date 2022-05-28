/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.core;

public class SemanticAbstractSlave extends com.pullenti.ner.MetaToken {

    public SemanticAbstractSlave(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public String preposition;

    public com.pullenti.ner.MetaToken source;

    public static SemanticAbstractSlave createFromNoun(com.pullenti.ner.core.NounPhraseToken npt) {
        SemanticAbstractSlave res = new SemanticAbstractSlave(npt.getBeginToken(), npt.getEndToken());
        if (npt.preposition != null) 
            res.preposition = npt.preposition.normal;
        res.setMorph(npt.getMorph());
        res.source = npt;
        return res;
    }

    @Override
    public String toString() {
        if (preposition != null) 
            return (preposition + ": " + this.getSourceText());
        return this.getSourceText();
    }

    public boolean getHasPronoun() {
        com.pullenti.ner.core.NounPhraseToken npt = (com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(source, com.pullenti.ner.core.NounPhraseToken.class);
        if (npt == null) 
            return false;
        for (com.pullenti.ner.MetaToken a : npt.adjectives) {
            if (a.getBeginToken().getMorph()._getClass().isPronoun()) 
                return true;
        }
        return false;
    }

    public SemanticAbstractSlave() {
        super();
    }
}
