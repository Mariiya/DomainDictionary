/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition.internal;

public class DefinitionAnalyzerEn {

    public static void process(com.pullenti.ner.core.AnalysisKit kit, com.pullenti.ner.core.AnalyzerData ad) {
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                continue;
            com.pullenti.ner.ReferentToken rt = tryParseThesis(t);
            if (rt == null) 
                continue;
            rt.referent = ad.registerReferent(rt.referent);
            kit.embedToken(rt);
            t = rt;
        }
    }

    private static com.pullenti.ner.ReferentToken tryParseThesis(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token tt = t;
        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
        com.pullenti.ner.MetaToken preamb = null;
        if (mc.isConjunction()) 
            return null;
        if (t.isValue("LET", null)) 
            return null;
        if (mc.isPreposition() || mc.isMisc() || mc.isAdverb()) {
            if (!com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) {
                for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isComma()) 
                        break;
                    if (tt.isChar('(')) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null) {
                            tt = br.getEndToken();
                            continue;
                        }
                    }
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                        break;
                    com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN.value())), 0, null);
                    if (npt0 != null) {
                        tt = npt0.getEndToken();
                        continue;
                    }
                    if (tt.getMorphClassInDictionary().isVerb()) 
                        break;
                }
                if (tt == null || !tt.isComma() || tt.getNext() == null) 
                    return null;
                preamb = new com.pullenti.ner.MetaToken(t0, tt.getPrevious(), null);
                tt = tt.getNext();
            }
        }
        com.pullenti.ner.Token t1 = tt;
        mc = tt.getMorphClassInDictionary();
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS.value())), 0, null);
        if (npt == null && (tt instanceof com.pullenti.ner.TextToken)) {
            if (tt.chars.isAllUpper()) 
                npt = new com.pullenti.ner.core.NounPhraseToken(tt, tt);
            else if (!tt.chars.isAllLower()) {
                if (mc.isProper() || preamb != null) 
                    npt = new com.pullenti.ner.core.NounPhraseToken(tt, tt);
            }
        }
        if (npt == null) 
            return null;
        if (mc.isPersonalPronoun()) 
            return null;
        com.pullenti.ner.Token t2 = npt.getEndToken().getNext();
        if (t2 == null || com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t2) || !(t2 instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (!t2.getMorphClassInDictionary().isVerb()) 
            return null;
        com.pullenti.ner.Token t3 = t2;
        for (tt = t2.getNext(); tt != null; tt = tt.getNext()) {
            if (!tt.getMorphClassInDictionary().isVerb()) 
                break;
        }
        for (; tt != null; tt = tt.getNext()) {
            if (tt.getNext() == null) {
                t3 = tt;
                break;
            }
            if (tt.isCharOf(".;!?")) {
                if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext())) {
                    t3 = tt;
                    break;
                }
            }
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                continue;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    tt = br.getEndToken();
                    continue;
                }
            }
        }
        tt = t3;
        if (t3.isCharOf(";.!?")) 
            tt = tt.getPrevious();
        String txt = com.pullenti.ner.core.MiscHelper.getTextValue(t2, tt, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        if (txt == null || (txt.length() < 15)) 
            return null;
        if (t0 != t1) {
            tt = t1.getPrevious();
            if (tt.isComma()) 
                tt = tt.getPrevious();
            String txt0 = com.pullenti.ner.core.MiscHelper.getTextValue(t0, tt, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
            if (txt0 != null && txt0.length() > 10) {
                if (t0.chars.isCapitalUpper()) 
                    txt0 = (String.valueOf(Character.toLowerCase(txt0.charAt(0)))) + txt0.substring(1);
                txt = (txt + ", " + txt0);
            }
        }
        tt = t1;
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
            tt = tt.getNext();
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt, t2.getPrevious(), com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
        if (nam.startsWith("SO-CALLED")) 
            nam = nam.substring(9).trim();
        com.pullenti.ner.definition.DefinitionReferent dr = new com.pullenti.ner.definition.DefinitionReferent();
        dr.setKind(com.pullenti.ner.definition.DefinitionKind.ASSERTATION);
        dr.addSlot(com.pullenti.ner.definition.DefinitionReferent.ATTR_TERMIN, nam, false, 0);
        dr.addSlot(com.pullenti.ner.definition.DefinitionReferent.ATTR_VALUE, txt, false, 0);
        return new com.pullenti.ner.ReferentToken(dr, t0, t3, null);
    }
    public DefinitionAnalyzerEn() {
    }
}
