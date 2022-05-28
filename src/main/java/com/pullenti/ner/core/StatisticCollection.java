/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Статистическая информация о словоформах и их биграммах в тексте - поле AnalysisKit.Statistic.
 * Статистика
 */
public class StatisticCollection {

    public void prepare(com.pullenti.ner.Token first) {
        StatisticWordInfo prev = null;
        com.pullenti.ner.Token prevt = null;
        for (com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (t.isHiphen()) 
                continue;
            StatisticWordInfo it = null;
            if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && t.getLengthChar() > 1) && !t.chars.isAllLower()) 
                it = this.addToken((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class));
            else if ((((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getLengthChar() == 1 && t.chars.isAllUpper()) && t.getNext() != null && t.getNext().isChar('.')) && !t.isWhitespaceAfter()) {
                it = this.addToken((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class));
                t = t.getNext();
            }
            if (prev != null && it != null) {
                this.addBigramm(prev, it);
                if (prevt.chars.equals(t.chars)) {
                    prev.addAfter(it);
                    it.addBefore(prev);
                }
            }
            prev = it;
            prevt = t;
        }
        for (com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (t.chars.isLetter() && (t instanceof com.pullenti.ner.TextToken)) {
                StatisticWordInfo it = this.findItem((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class), false);
                if (it != null) {
                    if (t.chars.isAllLower()) 
                        it.lowerCount++;
                    else if (t.chars.isAllUpper()) 
                        it.upperCount++;
                    else if (t.chars.isCapitalUpper()) 
                        it.capitalCount++;
                }
            }
        }
    }

    private StatisticWordInfo addToken(com.pullenti.ner.TextToken tt) {
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        vars.add(tt.term);
        String s = MiscHelper.getAbsoluteNormalValue(tt.term, false);
        if (s != null && !vars.contains(s)) 
            vars.add(s);
        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf == null) 
                continue;
            if (wf.normalCase != null && !vars.contains(wf.normalCase)) 
                vars.add(wf.normalCase);
            if (wf.normalFull != null && !vars.contains(wf.normalFull)) 
                vars.add(wf.normalFull);
        }
        StatisticWordInfo res = null;
        for (String v : vars) {
            com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapres560 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
            boolean inoutres561 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, v, wrapres560);
            res = wrapres560.value;
            if (inoutres561) 
                break;
        }
        if (res == null) 
            res = StatisticWordInfo._new562(tt.lemma);
        for (String v : vars) {
            if (!m_Items.containsKey(v)) 
                m_Items.put(v, res);
        }
        res.totalCount++;
        if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isAllLower()) {
            if (tt.getNext().chars.isCyrillicLetter() && tt.getNext().getMorphClassInDictionary().isVerb()) {
                com.pullenti.morph.MorphGender g = tt.getNext().getMorph().getGender();
                if (g == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.femaleVerbsAfterCount++;
                else if (((short)((g.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.maleVerbsAfterCount++;
            }
        }
        if (tt.getPrevious() != null) {
            if ((tt.getPrevious() instanceof com.pullenti.ner.TextToken) && tt.getPrevious().chars.isLetter() && !tt.getPrevious().chars.isAllLower()) {
            }
            else 
                res.notCapitalBeforeCount++;
        }
        return res;
    }

    private java.util.HashMap<String, StatisticWordInfo> m_Items = new java.util.HashMap<String, StatisticWordInfo>();

    private StatisticWordInfo findItem(com.pullenti.ner.TextToken tt, boolean doAbsolute) {
        if (tt == null) 
            return null;
        StatisticWordInfo res;
        com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapres569 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
        boolean inoutres570 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, tt.term, wrapres569);
        res = wrapres569.value;
        if (inoutres570) 
            return res;
        if (doAbsolute) {
            String s = MiscHelper.getAbsoluteNormalValue(tt.term, false);
            if (s != null) {
                com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapres563 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
                boolean inoutres564 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, s, wrapres563);
                res = wrapres563.value;
                if (inoutres564) 
                    return res;
            }
        }
        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf == null) 
                continue;
            com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapres567 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
            boolean inoutres568 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, (wf.normalCase != null ? wf.normalCase : ""), wrapres567);
            res = wrapres567.value;
            if (inoutres568) 
                return res;
            com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapres565 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
            boolean inoutres566 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, wf.normalFull, wrapres565);
            res = wrapres565.value;
            if (wf.normalFull != null && inoutres566) 
                return res;
        }
        return null;
    }

    private void addBigramm(StatisticWordInfo b1, StatisticWordInfo b2) {
        java.util.HashMap<String, Integer> di;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>> wrapdi573 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>>();
        boolean inoutres574 = com.pullenti.unisharp.Utils.tryGetValue(m_Bigramms, b1.normal, wrapdi573);
        di = wrapdi573.value;
        if (!inoutres574) 
            m_Bigramms.put(b1.normal, (di = new java.util.HashMap<String, Integer>()));
        if (di.containsKey(b2.normal)) 
            di.put(b2.normal, di.get(b2.normal) + 1);
        else 
            di.put(b2.normal, 1);
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>> wrapdi571 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>>();
        boolean inoutres572 = com.pullenti.unisharp.Utils.tryGetValue(m_BigrammsRev, b2.normal, wrapdi571);
        di = wrapdi571.value;
        if (!inoutres572) 
            m_BigrammsRev.put(b2.normal, (di = new java.util.HashMap<String, Integer>()));
        if (di.containsKey(b1.normal)) 
            di.put(b1.normal, di.get(b1.normal) + 1);
        else 
            di.put(b1.normal, 1);
    }

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_Bigramms = new java.util.HashMap<String, java.util.HashMap<String, Integer>>();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_BigrammsRev = new java.util.HashMap<String, java.util.HashMap<String, Integer>>();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_Initials = new java.util.HashMap<String, java.util.HashMap<String, Integer>>();

    private java.util.HashMap<String, java.util.HashMap<String, Integer>> m_InitialsRev = new java.util.HashMap<String, java.util.HashMap<String, Integer>>();

    /**
     * Получить статистическую информацию о биграмме токенов
     * @param t1 первый токен биграммы
     * @param t2 второй токен биграммы
     * @return информация о биграмме по всему тексту
     * 
     */
    public StatisticBigrammInfo getBigrammInfo(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        StatisticWordInfo si1 = this.findItem((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class), true);
        StatisticWordInfo si2 = this.findItem((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.TextToken.class), true);
        if (si1 == null || si2 == null) 
            return null;
        return this._getBigramsInfo(si1, si2);
    }

    private StatisticBigrammInfo _getBigramsInfo(StatisticWordInfo si1, StatisticWordInfo si2) {
        StatisticBigrammInfo res = StatisticBigrammInfo._new575(si1.totalCount, si2.totalCount);
        java.util.HashMap<String, Integer> di12 = null;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>> wrapdi12577 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>>();
        com.pullenti.unisharp.Utils.tryGetValue(m_Bigramms, si1.normal, wrapdi12577);
        di12 = wrapdi12577.value;
        java.util.HashMap<String, Integer> di21 = null;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>> wrapdi21576 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, Integer>>();
        com.pullenti.unisharp.Utils.tryGetValue(m_BigrammsRev, si2.normal, wrapdi21576);
        di21 = wrapdi21576.value;
        if (di12 != null) {
            if (!di12.containsKey(si2.normal)) 
                res.firstHasOtherSecond = true;
            else {
                res.pairCount = di12.get(si2.normal);
                if (di12.size() > 1) 
                    res.firstHasOtherSecond = true;
            }
        }
        if (di21 != null) {
            if (!di21.containsKey(si1.normal)) 
                res.secondHasOtherFirst = true;
            else if (!di21.containsKey(si1.normal)) 
                res.secondHasOtherFirst = true;
            else if (di21.size() > 1) 
                res.secondHasOtherFirst = true;
        }
        return res;
    }

    public StatisticBigrammInfo getInitialInfo(String ini, com.pullenti.ner.Token sur) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(ini)) 
            return null;
        StatisticWordInfo si2 = this.findItem((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(sur, com.pullenti.ner.TextToken.class), true);
        if (si2 == null) 
            return null;
        StatisticWordInfo si1 = null;
        com.pullenti.unisharp.Outargwrapper<StatisticWordInfo> wrapsi1578 = new com.pullenti.unisharp.Outargwrapper<StatisticWordInfo>();
        boolean inoutres579 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, ini.substring(0, 0 + 1), wrapsi1578);
        si1 = wrapsi1578.value;
        if (!inoutres579) 
            return null;
        if (si1 == null) 
            return null;
        return this._getBigramsInfo(si1, si2);
    }

    /**
     * Получить информацию о словоформе токена
     * @param t токен
     * @return статистическая информация по тексту
     * 
     */
    public StatisticWordInfo getWordInfo(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        return this.findItem(tt, true);
    }
    public StatisticCollection() {
    }
}
