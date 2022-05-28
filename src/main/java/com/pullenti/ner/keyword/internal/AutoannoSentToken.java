/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.keyword.internal;

public class AutoannoSentToken extends com.pullenti.ner.MetaToken {

    public AutoannoSentToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public double rank;

    public String value;

    @Override
    public String toString() {
        return (((Double)rank).toString() + ": " + value);
    }

    private static AutoannoSentToken tryParse(com.pullenti.ner.Token t) {
        if (t == null || !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
            return null;
        AutoannoSentToken res = new AutoannoSentToken(t, t);
        boolean hasVerb = false;
        for (; t != null; t = t.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t) && t != res.getBeginToken()) 
                break;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof com.pullenti.ner.keyword.KeywordReferent) {
                res.rank += ((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.keyword.KeywordReferent.class)).rank;
                if (((com.pullenti.ner.keyword.KeywordReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.keyword.KeywordReferent.class)).getTyp() == com.pullenti.ner.keyword.KeywordType.PREDICATE) 
                    hasVerb = true;
            }
            else if (t instanceof com.pullenti.ner.TextToken) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isPronoun() || mc.isPersonalPronoun()) 
                    res.rank -= (1.0);
                else if (t.getLengthChar() > 1) 
                    res.rank -= 0.1;
            }
            res.setEndToken(t);
        }
        if (!hasVerb) 
            res.rank /= (3.0);
        res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        return res;
    }

    public static com.pullenti.ner.keyword.KeywordReferent createAnnotation(com.pullenti.ner.core.AnalysisKit _kit, int maxSents) {
        java.util.ArrayList<AutoannoSentToken> sents = new java.util.ArrayList<AutoannoSentToken>();
        for (com.pullenti.ner.Token t = _kit.firstToken; t != null; t = t.getNext()) {
            AutoannoSentToken sent = tryParse(t);
            if (sent == null) 
                continue;
            if (sent.rank > 0) 
                sents.add(sent);
            t = sent.getEndToken();
        }
        if (sents.size() < 2) 
            return null;
        for (int i = 0; i < sents.size(); i++) {
            sents.get(i).rank *= (((double)((sents.size() - i))) / ((double)sents.size()));
        }
        if ((maxSents * 3) > sents.size()) {
            maxSents = sents.size() / 3;
            if (maxSents == 0) 
                maxSents = 1;
        }
        while (sents.size() > maxSents) {
            int mini = 0;
            double min = sents.get(0).rank;
            for (int i = 1; i < sents.size(); i++) {
                if (sents.get(i).rank <= min) {
                    min = sents.get(i).rank;
                    mini = i;
                }
            }
            sents.remove(mini);
        }
        com.pullenti.ner.keyword.KeywordReferent ano = new com.pullenti.ner.keyword.KeywordReferent();
        ano.setTyp(com.pullenti.ner.keyword.KeywordType.ANNOTATION);
        StringBuilder tmp = new StringBuilder();
        for (AutoannoSentToken s : sents) {
            if (tmp.length() > 0) 
                tmp.append(' ');
            tmp.append(s.value);
            ano.getOccurrence().add(com.pullenti.ner.TextAnnotation._new1748(s.getBeginChar(), s.getEndChar(), ano, _kit.getSofa()));
        }
        ano.addSlot(com.pullenti.ner.keyword.KeywordReferent.ATTR_VALUE, tmp.toString(), true, 0);
        return ano;
    }
    public AutoannoSentToken() {
        super();
    }
}
