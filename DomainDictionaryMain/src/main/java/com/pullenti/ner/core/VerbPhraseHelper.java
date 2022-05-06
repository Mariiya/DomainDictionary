/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Работа с глагольными группами (последовательность из глаголов и наречий)
 * Хелпер глагольных групп
 */
public class VerbPhraseHelper {

    /**
     * Создать глагольную группу
     * @param t первый токен группы
     * @param canBePartition выделять ли причастия
     * @param canBeAdjPartition это бывают чистые прилагательные используются в режиме причастий (действия, опасные для жизни)
     * @param forceParse всегда ли пытаться выделять, даже при сомнительных случаях (false по умолчанию)
     * @return группа или null
     */
    public static VerbPhraseToken tryParse(com.pullenti.ner.Token t, boolean canBePartition, boolean canBeAdjPartition, boolean forceParse) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (!t.chars.isLetter()) 
            return null;
        if (t.chars.isCyrillicLetter()) 
            return tryParseRu(t, canBePartition, canBeAdjPartition, forceParse);
        return null;
    }

    private static VerbPhraseToken tryParseRu(com.pullenti.ner.Token t, boolean canBePartition, boolean canBeAdjPartition, boolean forceParse) {
        VerbPhraseToken res = null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token not = null;
        boolean hasVerb = false;
        boolean verbBeBefore = false;
        PrepositionToken prep = null;
        for (; t != null; t = t.getNext()) {
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            boolean isParticiple = false;
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "НЕ")) {
                not = t;
                continue;
            }
            int ty = 0;
            String norm = null;
            com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "НЕТ")) {
                if (hasVerb) 
                    break;
                ty = 1;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ДОПУСТИМО")) 
                ty = 3;
            else if (mc.isAdverb() && !mc.isVerb()) 
                ty = 2;
            else if (tt.isPureVerb() || tt.isVerbBe()) {
                ty = 1;
                if (hasVerb) {
                    if (!tt.getMorph().containsAttr("инф.", null)) {
                        if (verbBeBefore) {
                        }
                        else 
                            break;
                    }
                }
            }
            else if (mc.isVerb()) {
                if (mc.isPreposition() || mc.isMisc() || mc.isPronoun()) {
                }
                else if (mc.isNoun()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "СТАЛИ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "СТЕКЛО") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "БЫЛИ")) 
                        ty = 1;
                    else if (!tt.chars.isAllLower() && !MiscHelper.canBeStartOfSentence(tt)) 
                        ty = 1;
                    else if (mc.isAdjective() && canBePartition) 
                        ty = 1;
                    else if (forceParse) 
                        ty = 1;
                }
                else if (mc.isProper()) {
                    if (tt.chars.isAllLower()) 
                        ty = 1;
                }
                else 
                    ty = 1;
                if (mc.isAdjective()) 
                    isParticiple = true;
                if (!tt.getMorph().getCase().isUndefined()) 
                    isParticiple = true;
                if (!canBePartition && isParticiple) 
                    break;
                if (hasVerb) {
                    if (tt.getMorph().containsAttr("инф.", null)) {
                    }
                    else if (!isParticiple) {
                    }
                    else 
                        break;
                }
            }
            else if ((mc.isAdjective() && tt.getMorph().containsAttr("к.ф.", null) && tt.term.endsWith("О")) && NounPhraseHelper.tryParse(tt, NounPhraseParseAttr.NO, 0, null) == null) 
                ty = 2;
            else if (mc.isAdjective() && ((canBePartition || canBeAdjPartition))) {
                if (tt.getMorph().containsAttr("к.ф.", null) && !canBeAdjPartition) 
                    break;
                norm = tt.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
                if (norm.endsWith("ЙШИЙ")) {
                }
                else {
                    java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(norm, true, null);
                    if (grs != null && grs.size() > 0) {
                        boolean hVerb = false;
                        boolean hPart = false;
                        for (com.pullenti.semantic.utils.DerivateGroup gr : grs) {
                            for (com.pullenti.semantic.utils.DerivateWord w : gr.words) {
                                if (w._class != null && w._class.isAdjective() && w._class.isVerb()) {
                                    if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, norm)) 
                                        hPart = true;
                                }
                                else if (w._class != null && w._class.isVerb()) 
                                    hVerb = true;
                            }
                        }
                        if (hPart && hVerb) 
                            ty = 3;
                        else if (canBeAdjPartition) 
                            ty = 3;
                        if (ty != 3 && !com.pullenti.unisharp.Utils.isNullOrEmpty(grs.get(0).prefix) && norm.startsWith(grs.get(0).prefix)) {
                            hVerb = false;
                            hPart = false;
                            String norm1 = norm.substring(grs.get(0).prefix.length());
                            grs = com.pullenti.semantic.utils.DerivateService.findDerivates(norm1, true, null);
                            if (grs != null && grs.size() > 0) {
                                for (com.pullenti.semantic.utils.DerivateGroup gr : grs) {
                                    for (com.pullenti.semantic.utils.DerivateWord w : gr.words) {
                                        if (w._class.isAdjective() && w._class.isVerb()) {
                                            if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, norm1)) 
                                                hPart = true;
                                        }
                                        else if (w._class.isVerb()) 
                                            hVerb = true;
                                    }
                                }
                            }
                            if (hPart && hVerb) 
                                ty = 3;
                        }
                    }
                }
            }
            if (ty == 0 && t == t0 && canBePartition) {
                prep = PrepositionHelper.tryParse(t);
                if (prep != null) {
                    t = prep.getEndToken();
                    continue;
                }
            }
            if (ty == 0) 
                break;
            if (res == null) 
                res = new VerbPhraseToken(t0, t);
            res.setEndToken(t);
            VerbPhraseItemToken it = VerbPhraseItemToken._new628(t, t, new com.pullenti.ner.MorphCollection(t.getMorph()));
            if (not != null) {
                it.setBeginToken(not);
                it.not = true;
                not = null;
            }
            it.isAdverb = ty == 2;
            if (prep != null && !t.getMorph().getCase().isUndefined() && res.items.size() == 0) {
                if (((com.pullenti.morph.MorphCase.ooBitand(prep.nextCase, t.getMorph().getCase()))).isUndefined()) 
                    return null;
                it.getMorph().removeItems(prep.nextCase);
                res.preposition = prep;
            }
            if (norm == null) {
                norm = t.getNormalCaseText((ty == 3 ? com.pullenti.morph.MorphClass.ADJECTIVE : (ty == 2 ? com.pullenti.morph.MorphClass.ADVERB : com.pullenti.morph.MorphClass.VERB)), com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
                if (ty == 1 && !tt.getMorph().getCase().isUndefined()) {
                    com.pullenti.morph.MorphWordForm mi = com.pullenti.morph.MorphWordForm._new629(com.pullenti.morph.MorphCase.NOMINATIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE);
                    for (com.pullenti.morph.MorphBaseInfo mit : tt.getMorph().getItems()) {
                        if (mit instanceof com.pullenti.morph.MorphWordForm) {
                            mi.misc = ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(mit, com.pullenti.morph.MorphWordForm.class)).misc;
                            break;
                        }
                    }
                    String nnn = com.pullenti.morph.MorphologyService.getWordform("КК" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, mi);
                    if (nnn != null) 
                        norm = nnn.substring(2);
                }
            }
            it.setNormal(norm);
            res.items.add(it);
            if (!hasVerb && ((ty == 1 || ty == 3))) {
                res.setMorph(it.getMorph());
                hasVerb = true;
            }
            if (ty == 1 || ty == 3) {
                if (ty == 1 && tt.isVerbBe()) 
                    verbBeBefore = true;
                else 
                    verbBeBefore = false;
            }
        }
        if (!hasVerb) 
            return null;
        for (int i = res.items.size() - 1; i > 0; i--) {
            if (res.items.get(i).isAdverb) {
                res.items.remove(i);
                res.setEndToken(res.items.get(i - 1).getEndToken());
            }
            else 
                break;
        }
        return res;
    }
    public VerbPhraseHelper() {
    }
}
