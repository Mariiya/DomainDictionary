/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

public class _NounPraseHelperInt {

    public static NounPhraseToken tryParse(com.pullenti.ner.Token first, NounPhraseParseAttr typ, int maxCharPos, com.pullenti.ner.core.internal.NounPhraseItem noun) {
        if (first == null) 
            return null;
        if (first.getNotNounPhrase()) {
            if (typ == NounPhraseParseAttr.NO) 
                return null;
        }
        int cou = 0;
        for (com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (maxCharPos > 0 && t.getBeginChar() > maxCharPos) 
                break;
            if (t.getMorph().getLanguage().isCyrillic() || (((t instanceof com.pullenti.ner.NumberToken) && t.getMorph()._getClass().isAdjective() && !t.chars.isLatinLetter())) || (((t instanceof com.pullenti.ner.ReferentToken) && (((typ.value()) & (NounPhraseParseAttr.REFERENTCANBENOUN.value()))) != (NounPhraseParseAttr.NO.value()) && !t.chars.isLatinLetter()))) {
                NounPhraseToken res = tryParseRu(first, typ, maxCharPos, noun);
                if (res == null && typ == NounPhraseParseAttr.NO) 
                    first.setNotNounPhrase(true);
                return res;
            }
            else if (t.chars.isLatinLetter()) {
                NounPhraseToken res = tryParseEn(first, typ, maxCharPos);
                if (res == null && typ == NounPhraseParseAttr.NO) 
                    first.setNotNounPhrase(true);
                return res;
            }
            else if ((++cou) > 0) 
                break;
        }
        return null;
    }

    private static NounPhraseToken tryParseRu(com.pullenti.ner.Token first, NounPhraseParseAttr typ, int maxCharPos, com.pullenti.ner.core.internal.NounPhraseItem defNoun) {
        if (first == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.internal.NounPhraseItem> items = null;
        java.util.ArrayList<com.pullenti.ner.TextToken> adverbs = null;
        PrepositionToken prep = null;
        boolean kak = false;
        com.pullenti.ner.Token t0 = first;
        if ((((typ.value()) & (NounPhraseParseAttr.PARSEPREPOSITION.value()))) != (NounPhraseParseAttr.NO.value()) && t0.isValue("КАК", null)) {
            t0 = t0.getNext();
            prep = PrepositionHelper.tryParse(t0);
            if (prep != null) 
                t0 = prep.getEndToken().getNext();
            kak = true;
        }
        NounPhraseToken internalNounPrase = null;
        boolean conjBefore = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (maxCharPos > 0 && t.getBeginChar() > maxCharPos) 
                break;
            if ((t.getMorph()._getClass().isConjunction() && !t.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isPronoun()) && !t.getMorph()._getClass().isNoun()) {
                if (conjBefore) 
                    break;
                if ((((typ.value()) & (NounPhraseParseAttr.CANNOTHASCOMMAAND.value()))) != (NounPhraseParseAttr.NO.value())) 
                    break;
                if (items != null && ((t.isAnd() || t.isOr()))) {
                    conjBefore = true;
                    if ((t.getNext() != null && t.getNext().isCharOf("\\/") && t.getNext().getNext() != null) && t.getNext().getNext().isOr()) 
                        t = t.getNext().getNext();
                    if (((t.getNext() != null && t.getNext().isChar('(') && t.getNext().getNext() != null) && t.getNext().getNext().isOr() && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(')')) 
                        t = t.getNext().getNext().getNext();
                    continue;
                }
                break;
            }
            else if (t.isComma()) {
                if (conjBefore || items == null) 
                    break;
                if ((((typ.value()) & (NounPhraseParseAttr.CANNOTHASCOMMAAND.value()))) != (NounPhraseParseAttr.NO.value())) 
                    break;
                com.pullenti.morph.MorphClass mc = t.getPrevious().getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperSecname()) 
                    break;
                conjBefore = true;
                if (kak && t.getNext() != null && t.getNext().isValue("ТАК", null)) {
                    t = t.getNext();
                    if (t.getNext() != null && t.getNext().isAnd()) 
                        t = t.getNext();
                    PrepositionToken pr = PrepositionHelper.tryParse(t.getNext());
                    if (pr != null) 
                        t = pr.getEndToken();
                }
                if (items.get(items.size() - 1).canBeNoun && items.get(items.size() - 1).getEndToken().getMorph()._getClass().isPronoun()) 
                    break;
                continue;
            }
            else if (t.isChar('(')) {
                if (items == null) 
                    return null;
                BracketSequenceToken brr = BracketHelper.tryParse(t, BracketParseAttr.NO, 100);
                if (brr == null) 
                    break;
                if (brr.getLengthChar() > 100) 
                    break;
                t = brr.getEndToken();
                continue;
            }
            else if (t.isValue("НЕ", null) && (((typ.value()) & (NounPhraseParseAttr.PARSENOT.value()))) != (NounPhraseParseAttr.NO.value())) 
                continue;
            if (t instanceof com.pullenti.ner.ReferentToken) {
                if ((((typ.value()) & (NounPhraseParseAttr.REFERENTCANBENOUN.value()))) == (NounPhraseParseAttr.NO.value())) 
                    break;
            }
            else if (t.chars.isLatinLetter()) 
                break;
            com.pullenti.ner.core.internal.NounPhraseItem it = com.pullenti.ner.core.internal.NounPhraseItem.tryParse(t, items, typ);
            if (it == null || ((!it.canBeAdj && !it.canBeNoun))) {
                if (((it != null && items != null && t.chars.isCapitalUpper()) && (t.getWhitespacesBeforeCount() < 3) && t.getLengthChar() > 3) && !t.getMorphClassInDictionary().isNoun() && !t.getMorphClassInDictionary().isAdjective()) {
                    it.canBeNoun = true;
                    items.add(it);
                    break;
                }
                if ((((typ.value()) & (NounPhraseParseAttr.PARSEADVERBS.value()))) != (NounPhraseParseAttr.NO.value()) && (t instanceof com.pullenti.ner.TextToken) && ((t.getMorph()._getClass().isAdverb() || t.getMorph().containsAttr("неизм.", null)))) {
                    if (adverbs == null) 
                        adverbs = new java.util.ArrayList<com.pullenti.ner.TextToken>();
                    adverbs.add((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class));
                    if (t.getNext() != null && t.getNext().isHiphen()) 
                        t = t.getNext();
                    continue;
                }
                break;
            }
            it.conjBefore = conjBefore;
            conjBefore = false;
            if (!it.canBeAdj && !it.canBeNoun) 
                break;
            if (t.isNewlineBefore() && t != first) {
                if ((((typ.value()) & (NounPhraseParseAttr.MULTILINES.value()))) != (NounPhraseParseAttr.NO.value())) {
                }
                else if (items != null && !t.chars.equals(items.get(items.size() - 1).chars)) {
                    if (t.chars.isAllLower() && items.get(items.size() - 1).chars.isCapitalUpper()) {
                    }
                    else 
                        break;
                }
            }
            if (items == null) 
                items = new java.util.ArrayList<com.pullenti.ner.core.internal.NounPhraseItem>();
            else {
                com.pullenti.ner.core.internal.NounPhraseItem it0 = items.get(items.size() - 1);
                if (it0.canBeNoun && it0.isPersonalPronoun()) {
                    if (it.isPronoun()) 
                        break;
                    if ((it0.getBeginToken().getPrevious() != null && it0.getBeginToken().getPrevious().getMorphClassInDictionary().isVerb() && !it0.getBeginToken().getPrevious().getMorphClassInDictionary().isAdjective()) && !it0.getBeginToken().getPrevious().getMorphClassInDictionary().isPreposition()) {
                        if (t.getMorph().getCase().isNominative() || t.getMorph().getCase().isAccusative()) {
                        }
                        else 
                            break;
                    }
                    if (it.canBeNoun && it.isVerb()) {
                        if (it0.getPrevious() == null) {
                        }
                        else if ((it0.getPrevious() instanceof com.pullenti.ner.TextToken) && !it0.getPrevious().chars.isLetter()) {
                        }
                        else 
                            break;
                    }
                }
            }
            items.add(it);
            t = it.getEndToken();
            if (t.isNewlineAfter() && !t.chars.isAllLower()) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isProperSurname()) 
                    break;
                if (t.getMorph()._getClass().isProperSurname() && mc.isUndefined()) 
                    break;
            }
        }
        if (items == null) 
            return null;
        com.pullenti.ner.Token tt1;
        if (items.size() == 1 && items.get(0).canBeAdj) {
            boolean and = false;
            for (tt1 = items.get(0).getEndToken().getNext(); tt1 != null; tt1 = tt1.getNext()) {
                if (tt1.isAnd() || tt1.isOr()) {
                    and = true;
                    break;
                }
                if (tt1.isComma() || tt1.isValue("НО", null) || tt1.isValue("ТАК", null)) 
                    continue;
                break;
            }
            if (and) {
                if (items.get(0).canBeNoun && items.get(0).isPersonalPronoun()) 
                    and = false;
            }
            if (and) {
                com.pullenti.ner.Token tt2 = tt1.getNext();
                if (tt2 != null && tt2.getMorph()._getClass().isPreposition()) 
                    tt2 = tt2.getNext();
                NounPhraseToken npt1 = tryParseRu(tt2, typ, maxCharPos, null);
                if (npt1 != null && npt1.adjectives.size() > 0) {
                    boolean ok1 = false;
                    for (com.pullenti.ner.core.internal.NounPhraseItemTextVar av : items.get(0).adjMorph) {
                        for (com.pullenti.ner.core.internal.NounPhraseItemTextVar v : ((com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(npt1.noun, com.pullenti.ner.core.internal.NounPhraseItem.class)).nounMorph) {
                            if (v.checkAccord(av, false, false)) {
                                items.get(0).getMorph().addItem(av);
                                ok1 = true;
                            }
                        }
                    }
                    if (ok1) {
                        npt1.setBeginToken(items.get(0).getBeginToken());
                        npt1.setEndToken(items.get(0).getEndToken());
                        npt1.adjectives.clear();
                        npt1.adjectives.add(items.get(0));
                        return npt1;
                    }
                }
            }
        }
        if (defNoun != null) 
            items.add(defNoun);
        com.pullenti.ner.core.internal.NounPhraseItem last1 = items.get(items.size() - 1);
        boolean check = true;
        for (com.pullenti.ner.core.internal.NounPhraseItem it : items) {
            if (!it.canBeAdj) {
                check = false;
                break;
            }
            else if (it.canBeNoun && it.isPersonalPronoun()) {
                check = false;
                break;
            }
        }
        tt1 = last1.getEndToken().getNext();
        if ((tt1 != null && check && ((tt1.getMorph()._getClass().isPreposition() || tt1.getMorph().getCase().isInstrumental()))) && (tt1.getWhitespacesBeforeCount() < 2)) {
            NounPhraseToken inp = NounPhraseHelper.tryParse(tt1, NounPhraseParseAttr.of((typ.value()) | (NounPhraseParseAttr.PARSEPREPOSITION.value())), maxCharPos, null);
            if (inp != null) {
                tt1 = inp.getEndToken().getNext();
                NounPhraseToken npt1 = tryParseRu(tt1, typ, maxCharPos, null);
                if (npt1 != null) {
                    boolean ok = true;
                    for (int ii = 0; ii < items.size(); ii++) {
                        com.pullenti.ner.core.internal.NounPhraseItem it = items.get(ii);
                        if (com.pullenti.ner.core.internal.NounPhraseItem.tryAccordAdjAndNoun(it, (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(npt1.noun, com.pullenti.ner.core.internal.NounPhraseItem.class))) 
                            continue;
                        if (ii > 0) {
                            NounPhraseToken inp2 = NounPhraseHelper.tryParse(it.getBeginToken(), typ, maxCharPos, null);
                            if (inp2 != null && inp2.getEndToken() == inp.getEndToken()) {
                                for(int jjj = ii + items.size() - ii - 1, mmm = ii; jjj >= mmm; jjj--) items.remove(jjj);
                                inp = inp2;
                                break;
                            }
                        }
                        ok = false;
                        break;
                    }
                    if (ok) {
                        if (npt1.getMorph().getCase().isGenitive() && !inp.getMorph().getCase().isInstrumental()) 
                            ok = false;
                    }
                    if (ok) {
                        for (int i = 0; i < items.size(); i++) {
                            npt1.adjectives.add(i, items.get(i));
                        }
                        npt1.internalNoun = inp;
                        com.pullenti.ner.MorphCollection mmm = new com.pullenti.ner.MorphCollection(npt1.getMorph());
                        for (com.pullenti.ner.core.internal.NounPhraseItem it : items) {
                            mmm.removeItems(it.adjMorph.get(0));
                        }
                        if (mmm.getGender() != com.pullenti.morph.MorphGender.UNDEFINED || mmm.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED || !mmm.getCase().isUndefined()) 
                            npt1.setMorph(mmm);
                        if (adverbs != null) {
                            if (npt1.adverbs == null) 
                                npt1.adverbs = adverbs;
                            else 
                                com.pullenti.unisharp.Utils.insertToArrayList(npt1.adverbs, adverbs, 0);
                        }
                        npt1.setBeginToken(first);
                        return npt1;
                    }
                }
                if (tt1 != null && tt1.getMorph()._getClass().isNoun() && !tt1.getMorph().getCase().isGenitive()) {
                    com.pullenti.ner.core.internal.NounPhraseItem it = com.pullenti.ner.core.internal.NounPhraseItem.tryParse(tt1, items, typ);
                    if (it != null && it.canBeNoun) {
                        internalNounPrase = inp;
                        inp.setBeginToken(items.get(0).getEndToken().getNext());
                        items.add(it);
                    }
                }
            }
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).canBeAdj && items.get(i).getBeginToken().getMorph()._getClass().isVerb()) {
                com.pullenti.ner.Token it = items.get(i).getBeginToken();
                if (!it.getMorphClassInDictionary().isVerb()) 
                    continue;
                if (it.isValue("УПОЛНОМОЧЕННЫЙ", null)) 
                    continue;
                if ((((typ.value()) & (NounPhraseParseAttr.PARSEVERBS.value()))) == (NounPhraseParseAttr.NO.value())) 
                    continue;
                NounPhraseToken inp = tryParseRu(items.get(i).getEndToken().getNext(), NounPhraseParseAttr.NO, maxCharPos, null);
                if (inp == null) 
                    continue;
                if (inp.anafor != null && i == (items.size() - 1) && com.pullenti.ner.core.internal.NounPhraseItem.tryAccordAdjAndNoun(items.get(i), (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(inp.noun, com.pullenti.ner.core.internal.NounPhraseItem.class))) {
                    inp.setBeginToken(first);
                    for (int ii = 0; ii < items.size(); ii++) {
                        inp.adjectives.add(ii, items.get(ii));
                    }
                    return inp;
                }
                if (inp.getEndToken().getWhitespacesAfterCount() > 3) 
                    continue;
                NounPhraseToken npt1 = tryParseRu(inp.getEndToken().getNext(), NounPhraseParseAttr.NO, maxCharPos, null);
                if (npt1 == null) 
                    continue;
                boolean ok = true;
                for (int j = 0; j <= i; j++) {
                    if (!com.pullenti.ner.core.internal.NounPhraseItem.tryAccordAdjAndNoun(items.get(j), (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(npt1.noun, com.pullenti.ner.core.internal.NounPhraseItem.class))) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) 
                    continue;
                VerbPhraseToken verb = VerbPhraseHelper.tryParse(it, true, false, false);
                if (verb == null) 
                    continue;
                java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> vlinks2 = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(verb, npt1, null);
                if (vlinks2.size() > 0) 
                    continue;
                java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> vlinks = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(verb, inp, null);
                java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> nlinks = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(inp, npt1, null);
                if (vlinks.size() == 0 && nlinks.size() > 0) 
                    continue;
                for (int j = 0; j <= i; j++) {
                    npt1.adjectives.add(j, items.get(j));
                }
                items.get(i).setEndToken(inp.getEndToken());
                com.pullenti.ner.MorphCollection mmm = new com.pullenti.ner.MorphCollection(npt1.getMorph());
                java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> bil = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
                for (int j = 0; j <= i; j++) {
                    bil.clear();
                    for (com.pullenti.ner.core.internal.NounPhraseItemTextVar m : items.get(j).adjMorph) {
                        bil.add(m);
                    }
                    mmm.removeItemsListCla(bil, null);
                }
                if (mmm.getGender() != com.pullenti.morph.MorphGender.UNDEFINED || mmm.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED || !mmm.getCase().isUndefined()) 
                    npt1.setMorph(mmm);
                if (adverbs != null) {
                    if (npt1.adverbs == null) 
                        npt1.adverbs = adverbs;
                    else 
                        com.pullenti.unisharp.Utils.insertToArrayList(npt1.adverbs, adverbs, 0);
                }
                npt1.setBeginToken(first);
                return npt1;
            }
        }
        boolean ok2 = false;
        if ((items.size() == 1 && (((typ.value()) & (NounPhraseParseAttr.ADJECTIVECANBELAST.value()))) != (NounPhraseParseAttr.NO.value()) && (items.get(0).getWhitespacesAfterCount() < 3)) && !items.get(0).isAdverb()) {
            if (!items.get(0).canBeAdj) 
                ok2 = true;
            else if (items.get(0).isPersonalPronoun() && items.get(0).canBeNoun) 
                ok2 = true;
        }
        if (ok2) {
            com.pullenti.ner.core.internal.NounPhraseItem it = com.pullenti.ner.core.internal.NounPhraseItem.tryParse(items.get(0).getEndToken().getNext(), null, typ);
            if (it != null && it.canBeAdj && it.getBeginToken().chars.isAllLower()) {
                ok2 = true;
                if (it.isAdverb() || it.isVerb()) 
                    ok2 = false;
                if (it.isPronoun() && items.get(0).isPronoun()) {
                    ok2 = false;
                    if (it.canBeAdjForPersonalPronoun() && items.get(0).isPersonalPronoun()) 
                        ok2 = true;
                }
                if (ok2 && com.pullenti.ner.core.internal.NounPhraseItem.tryAccordAdjAndNoun(it, items.get(0))) {
                    NounPhraseToken npt1 = tryParseRu(it.getBeginToken(), typ, maxCharPos, null);
                    if (npt1 != null && ((npt1.getEndChar() > it.getEndChar() || npt1.adjectives.size() > 0))) {
                    }
                    else 
                        items.add(0, it);
                }
            }
        }
        com.pullenti.ner.core.internal.NounPhraseItem noun = null;
        com.pullenti.ner.core.internal.NounPhraseItem adjAfter = null;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).canBeNoun) {
                if (items.get(i).conjBefore) 
                    continue;
                boolean hasNoun = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (items.get(j).canBeNoun && !items.get(j).canBeAdj) 
                        hasNoun = true;
                }
                if (hasNoun) 
                    continue;
                if (i > 0 && !items.get(i - 1).canBeAdj) 
                    continue;
                if (i > 0 && items.get(i - 1).canBeNoun) {
                    if (items.get(i - 1).isDoubtAdjective) 
                        continue;
                    if (items.get(i - 1).isPronoun() && items.get(i).isPronoun()) {
                        if (items.get(i).isPronoun() && items.get(i - 1).canBeAdjForPersonalPronoun()) {
                        }
                        else 
                            continue;
                    }
                }
                noun = items.get(i);
                for(int jjj = i + items.size() - i - 1, mmm = i; jjj >= mmm; jjj--) items.remove(jjj);
                if (adjAfter != null) 
                    items.add(adjAfter);
                else if (items.size() > 0 && items.get(0).canBeNoun && !items.get(0).canBeAdj) {
                    noun = items.get(0);
                    items.clear();
                }
                break;
            }
        }
        if (noun == null) 
            return null;
        NounPhraseToken res = NounPhraseToken._new481(first, noun.getEndToken(), prep);
        if (adverbs != null) {
            for (com.pullenti.ner.TextToken a : adverbs) {
                if (a.getBeginChar() < noun.getBeginChar()) {
                    if (items.size() == 0 && prep == null) 
                        return null;
                    if (res.adverbs == null) 
                        res.adverbs = new java.util.ArrayList<com.pullenti.ner.TextToken>();
                    res.adverbs.add(a);
                }
            }
        }
        res.noun = noun;
        res.multiNouns = noun.multiNouns;
        if (kak) 
            res.multiNouns = true;
        res.internalNoun = internalNounPrase;
        for (com.pullenti.ner.core.internal.NounPhraseItemTextVar v : noun.nounMorph) {
            noun.getMorph().addItem(v);
        }
        res.setMorph(noun.getMorph());
        if (res.getMorph().getCase().isNominative() && first.getPrevious() != null && first.getPrevious().getMorph()._getClass().isPreposition()) 
            res.getMorph().setCase(com.pullenti.morph.MorphCase.ooBitxor(res.getMorph().getCase(), com.pullenti.morph.MorphCase.NOMINATIVE));
        if ((((typ.value()) & (NounPhraseParseAttr.PARSEPRONOUNS.value()))) == (NounPhraseParseAttr.NO.value()) && ((res.getMorph()._getClass().isPronoun() || res.getMorph()._getClass().isPersonalPronoun()))) 
            return null;
        java.util.HashMap<Character, Integer> stat = null;
        if (items.size() > 1) 
            stat = new java.util.HashMap<Character, Integer>();
        boolean needUpdateMorph = false;
        if (items.size() > 0) {
            java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> okList = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
            boolean isNumNot = false;
            for (com.pullenti.ner.core.internal.NounPhraseItemTextVar vv : noun.nounMorph) {
                int i;
                com.pullenti.ner.core.internal.NounPhraseItemTextVar v = vv;
                for (i = 0; i < items.size(); i++) {
                    boolean ok = false;
                    for (com.pullenti.ner.core.internal.NounPhraseItemTextVar av : items.get(i).adjMorph) {
                        if (v.checkAccord(av, false, false)) {
                            ok = true;
                            if (!((com.pullenti.morph.MorphCase.ooBitand(av.getCase(), v.getCase()))).isUndefined() && !av.getCase().equals(v.getCase())) 
                                v.setCase(av.setCase(com.pullenti.morph.MorphCase.ooBitand(av.getCase(), v.getCase())));
                            break;
                        }
                    }
                    if (!ok) {
                        if (items.get(i).canBeNumericAdj() && items.get(i).tryAccordVar(v, false)) {
                            ok = true;
                            com.pullenti.ner.core.internal.NounPhraseItemTextVar v1 = new com.pullenti.ner.core.internal.NounPhraseItemTextVar(null, null);
                            v1.copyFromItem(v);
                            v1.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                            isNumNot = true;
                            v1.setCase(new com.pullenti.morph.MorphCase());
                            for (com.pullenti.ner.core.internal.NounPhraseItemTextVar a : items.get(i).adjMorph) {
                                v1.setCase(com.pullenti.morph.MorphCase.ooBitor(v1.getCase(), a.getCase()));
                            }
                            v = v1;
                        }
                        else 
                            break;
                    }
                }
                if (i >= items.size()) 
                    okList.add(v);
            }
            if (okList.size() > 0 && (((okList.size() < res.getMorph().getItemsCount()) || isNumNot))) {
                res.setMorph(new com.pullenti.ner.MorphCollection(null));
                for (com.pullenti.morph.MorphBaseInfo v : okList) {
                    res.getMorph().addItem(v);
                }
                if (!isNumNot) 
                    noun.setMorph(res.getMorph());
            }
        }
        for (int i = 0; i < items.size(); i++) {
            for (com.pullenti.ner.core.internal.NounPhraseItemTextVar av : items.get(i).adjMorph) {
                for (com.pullenti.ner.core.internal.NounPhraseItemTextVar v : noun.nounMorph) {
                    if (v.checkAccord(av, false, false)) {
                        if (!((com.pullenti.morph.MorphCase.ooBitand(av.getCase(), v.getCase()))).isUndefined() && !av.getCase().equals(v.getCase())) {
                            v.setCase(av.setCase(com.pullenti.morph.MorphCase.ooBitand(av.getCase(), v.getCase())));
                            needUpdateMorph = true;
                        }
                        items.get(i).getMorph().addItem(av);
                        if (stat != null && av.normalValue != null && av.normalValue.length() > 1) {
                            char last = av.normalValue.charAt(av.normalValue.length() - 1);
                            if (!stat.containsKey(last)) 
                                stat.put(last, 1);
                            else 
                                stat.put(last, stat.get(last) + 1);
                        }
                    }
                }
            }
            if (items.get(i).isPronoun() || items.get(i).isPersonalPronoun()) {
                res.anafor = items.get(i).getBeginToken();
                if ((((typ.value()) & (NounPhraseParseAttr.PARSEPRONOUNS.value()))) == (NounPhraseParseAttr.NO.value())) 
                    continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(items.get(i).getBeginToken(), com.pullenti.ner.TextToken.class);
            if (tt != null && !tt.term.startsWith("ВЫСШ")) {
                boolean err = false;
                for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                    if (wf._getClass().isAdjective()) {
                        if (wf.containsAttr("прев.", null)) {
                            if ((((typ.value()) & (NounPhraseParseAttr.IGNOREADJBEST.value()))) != (NounPhraseParseAttr.NO.value())) 
                                err = true;
                        }
                        if (wf.containsAttr("к.ф.", null) && tt.getMorph()._getClass().isPersonalPronoun()) 
                            return null;
                    }
                }
                if (err) 
                    continue;
            }
            if (res.getMorph().getCase().isNominative()) {
                String v = MiscHelper.getTextValueOfMetaToken(items.get(i), GetTextAttr.KEEPQUOTES);
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(v)) {
                    if (com.pullenti.unisharp.Utils.stringsNe(items.get(i).getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), v)) {
                        com.pullenti.ner.core.internal.NounPhraseItemTextVar wf = new com.pullenti.ner.core.internal.NounPhraseItemTextVar(items.get(i).getMorph(), null);
                        wf.normalValue = v;
                        wf._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
                        wf.setCase(res.getMorph().getCase());
                        if (res.getMorph().getCase().isPrepositional() || res.getMorph().getGender() == com.pullenti.morph.MorphGender.NEUTER || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                            items.get(i).getMorph().addItem(wf);
                        else 
                            items.get(i).getMorph().insertItem(0, wf);
                    }
                }
            }
            res.adjectives.add(items.get(i));
            if (items.get(i).getEndChar() > res.getEndChar()) 
                res.setEndToken(items.get(i).getEndToken());
        }
        for (int i = 0; i < (res.adjectives.size() - 1); i++) {
            if (res.adjectives.get(i).getWhitespacesAfterCount() > 5) {
                if (!res.adjectives.get(i).chars.equals(res.adjectives.get(i + 1).chars)) {
                    if (!res.adjectives.get(i + 1).chars.isAllLower()) 
                        return null;
                    if (res.adjectives.get(i).chars.isAllUpper() && res.adjectives.get(i + 1).chars.isCapitalUpper()) 
                        return null;
                    if (res.adjectives.get(i).chars.isCapitalUpper() && res.adjectives.get(i + 1).chars.isAllUpper()) 
                        return null;
                }
                if (res.adjectives.get(i).getWhitespacesAfterCount() > 10) {
                    if (res.adjectives.get(i).getNewlinesAfterCount() == 1) {
                        if (res.adjectives.get(i).chars.isCapitalUpper() && i == 0 && res.adjectives.get(i + 1).chars.isAllLower()) 
                            continue;
                        if (res.adjectives.get(i).chars.equals(res.adjectives.get(i + 1).chars)) 
                            continue;
                    }
                    return null;
                }
            }
        }
        if (needUpdateMorph) {
            noun.setMorph(new com.pullenti.ner.MorphCollection(null));
            for (com.pullenti.ner.core.internal.NounPhraseItemTextVar v : noun.nounMorph) {
                noun.getMorph().addItem(v);
            }
            res.setMorph(noun.getMorph());
        }
        if (res.adjectives.size() > 0) {
            if (noun.getBeginToken().getPrevious() != null) {
                if (noun.getBeginToken().getPrevious().isCommaAnd()) {
                    if (res.adjectives.get(0).getBeginChar() > noun.getBeginChar()) {
                    }
                    else 
                        return null;
                }
            }
            int zap = 0;
            int and = 0;
            int cou = 0;
            boolean lastAnd = false;
            for (int i = 0; i < (res.adjectives.size() - 1); i++) {
                com.pullenti.ner.Token te = res.adjectives.get(i).getEndToken().getNext();
                if (te == null) 
                    return null;
                if (te.isChar('(')) {
                }
                else if (te.isComma()) {
                    zap++;
                    lastAnd = false;
                }
                else if (te.isAnd() || te.isOr()) {
                    and++;
                    lastAnd = true;
                }
                if (!res.adjectives.get(i).getBeginToken().getMorph()._getClass().isPronoun()) 
                    cou++;
            }
            if ((zap + and) > 0) {
                boolean err = false;
                if (and > 1) 
                    err = true;
                else if (and == 1 && !lastAnd) 
                    err = true;
                else if ((zap + and) != cou) {
                    if (and == 1) {
                    }
                    else 
                        err = true;
                }
                else if (zap > 0 && and == 0) {
                }
                com.pullenti.ner.core.internal.NounPhraseItem last = (com.pullenti.ner.core.internal.NounPhraseItem)com.pullenti.unisharp.Utils.cast(res.adjectives.get(res.adjectives.size() - 1), com.pullenti.ner.core.internal.NounPhraseItem.class);
                if (last.isPronoun() && !lastAnd) 
                    err = true;
                if (err) {
                    if ((((typ.value()) & (NounPhraseParseAttr.CANNOTHASCOMMAAND.value()))) == (NounPhraseParseAttr.NO.value())) 
                        return tryParseRu(first, NounPhraseParseAttr.of((typ.value()) | (NounPhraseParseAttr.CANNOTHASCOMMAAND.value())), maxCharPos, defNoun);
                    return null;
                }
            }
        }
        if (stat != null) {
            for (com.pullenti.ner.core.internal.NounPhraseItem adj : items) {
                if (adj.getMorph().getItemsCount() > 1) {
                    com.pullenti.ner.core.internal.NounPhraseItemTextVar w1 = (com.pullenti.ner.core.internal.NounPhraseItemTextVar)com.pullenti.unisharp.Utils.cast(adj.getMorph().getIndexerItem(0), com.pullenti.ner.core.internal.NounPhraseItemTextVar.class);
                    com.pullenti.ner.core.internal.NounPhraseItemTextVar w2 = (com.pullenti.ner.core.internal.NounPhraseItemTextVar)com.pullenti.unisharp.Utils.cast(adj.getMorph().getIndexerItem(1), com.pullenti.ner.core.internal.NounPhraseItemTextVar.class);
                    if ((w1.normalValue.length() < 2) || (w2.normalValue.length() < 2)) 
                        break;
                    char l1 = w1.normalValue.charAt(w1.normalValue.length() - 1);
                    char l2 = w2.normalValue.charAt(w2.normalValue.length() - 1);
                    int i1 = 0;
                    int i2 = 0;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapi1483 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.tryGetValue(stat, l1, wrapi1483);
                    i1 = (wrapi1483.value != null ? wrapi1483.value : 0);
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapi2482 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.tryGetValue(stat, l2, wrapi2482);
                    i2 = (wrapi2482.value != null ? wrapi2482.value : 0);
                    if (i1 < i2) {
                        adj.getMorph().removeItem(1);
                        adj.getMorph().insertItem(0, w2);
                    }
                }
            }
        }
        if (res.getBeginToken().getMorphClassInDictionary().isVerb() && items.size() > 0) {
            if (!res.getBeginToken().chars.isAllLower() || res.getBeginToken().getPrevious() == null) {
            }
            else if (res.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) {
            }
            else {
                boolean comma = false;
                for (com.pullenti.ner.Token tt = res.getBeginToken().getPrevious(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getPrevious()) {
                    if (tt.getMorph()._getClass().isAdverb()) 
                        continue;
                    if (tt.isCharOf(".;")) 
                        break;
                    if (tt.isComma()) {
                        comma = true;
                        continue;
                    }
                    if (tt.isValue("НЕ", null)) 
                        continue;
                    if (((tt.getMorph()._getClass().isNoun() || tt.getMorph()._getClass().isProper())) && comma) {
                        for (com.pullenti.morph.MorphBaseInfo it : res.getBeginToken().getMorph().getItems()) {
                            if (it._getClass().isVerb() && (it instanceof com.pullenti.morph.MorphWordForm)) {
                                if (tt.getMorph().checkAccord(it, false, false)) {
                                    if (res.getMorph().getCase().isInstrumental()) 
                                        return null;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        if (res.getBeginToken() == res.getEndToken()) {
            com.pullenti.morph.MorphClass mc = res.getBeginToken().getMorphClassInDictionary();
            if (mc.isAdverb()) {
                if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) {
                }
                else if (mc.isNoun() && !mc.isPreposition() && !mc.isConjunction()) {
                }
                else if (res.getBeginToken().isValue("ВЕСЬ", null)) {
                }
                else 
                    return null;
            }
        }
        if (defNoun != null && defNoun.getEndToken() == res.getEndToken() && res.adjectives.size() > 0) 
            res.setEndToken(res.adjectives.get(res.adjectives.size() - 1).getEndToken());
        return res;
    }

    private static NounPhraseToken tryParseEn(com.pullenti.ner.Token first, NounPhraseParseAttr typ, int maxCharPos) {
        if (first == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.internal.NounPhraseItem> items = null;
        boolean hasArticle = false;
        boolean hasProp = false;
        boolean hasMisc = false;
        if (first.getPrevious() != null && first.getPrevious().getMorph()._getClass().isPreposition() && (first.getWhitespacesBeforeCount() < 3)) 
            hasProp = true;
        for (com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (maxCharPos > 0 && t.getBeginChar() > maxCharPos) 
                break;
            if (!t.chars.isLatinLetter()) 
                break;
            if (t != first && t.getWhitespacesBeforeCount() > 2) {
                if ((((typ.value()) & (NounPhraseParseAttr.MULTILINES.value()))) != (NounPhraseParseAttr.NO.value())) {
                }
                else if (MiscHelper.isEngArticle(t.getPrevious())) {
                }
                else 
                    break;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (t == first && tt != null) {
                if (MiscHelper.isEngArticle(tt)) {
                    hasArticle = true;
                    continue;
                }
            }
            if (t instanceof com.pullenti.ner.ReferentToken) {
                if ((((typ.value()) & (NounPhraseParseAttr.REFERENTCANBENOUN.value()))) == (NounPhraseParseAttr.NO.value())) 
                    break;
            }
            else if (tt == null) 
                break;
            if ((t.isValue("SO", null) && t.getNext() != null && t.getNext().isHiphen()) && t.getNext().getNext() != null) {
                if (t.getNext().getNext().isValue("CALL", null)) {
                    t = t.getNext().getNext();
                    continue;
                }
            }
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if (mc.isConjunction() || mc.isPreposition()) 
                break;
            if (mc.isPronoun() || mc.isPersonalPronoun()) {
                if ((((typ.value()) & (NounPhraseParseAttr.PARSEPRONOUNS.value()))) == (NounPhraseParseAttr.NO.value())) 
                    break;
            }
            else if (mc.isMisc()) {
                if (t.isValue("THIS", null) || t.isValue("THAT", null)) {
                    hasMisc = true;
                    if ((((typ.value()) & (NounPhraseParseAttr.PARSEPRONOUNS.value()))) == (NounPhraseParseAttr.NO.value())) 
                        break;
                }
            }
            boolean isAdj = false;
            if (((hasArticle || hasProp || hasMisc)) && items == null) {
            }
            else if (t instanceof com.pullenti.ner.ReferentToken) {
            }
            else {
                if (!mc.isNoun() && !mc.isAdjective()) {
                    if (mc.isUndefined() && hasArticle) {
                    }
                    else if (items == null && mc.isUndefined() && t.chars.isCapitalUpper()) {
                    }
                    else if (mc.isPronoun()) {
                    }
                    else if (tt.term.endsWith("EAN")) 
                        isAdj = true;
                    else if (MiscHelper.isEngAdjSuffix(tt.getNext())) {
                    }
                    else 
                        break;
                }
                if (mc.isVerb()) {
                    if (t.getNext() != null && t.getNext().getMorph()._getClass().isVerb() && (t.getWhitespacesAfterCount() < 2)) {
                    }
                    else if (t.chars.isCapitalUpper() && !MiscHelper.canBeStartOfSentence(t)) {
                    }
                    else if ((t.chars.isCapitalUpper() && mc.isNoun() && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isCapitalUpper()) {
                    }
                    else if (t instanceof com.pullenti.ner.ReferentToken) {
                    }
                    else 
                        break;
                }
            }
            if (items == null) 
                items = new java.util.ArrayList<com.pullenti.ner.core.internal.NounPhraseItem>();
            com.pullenti.ner.core.internal.NounPhraseItem it = new com.pullenti.ner.core.internal.NounPhraseItem(t, t);
            if (mc.isNoun()) 
                it.canBeNoun = true;
            if (mc.isAdjective() || mc.isPronoun() || isAdj) 
                it.canBeAdj = true;
            items.add(it);
            t = it.getEndToken();
            if (items.size() == 1) {
                if (MiscHelper.isEngAdjSuffix(t.getNext())) {
                    mc.setNoun(false);
                    mc.setAdjective(true);
                    t = t.getNext().getNext();
                }
            }
        }
        if (items == null) 
            return null;
        com.pullenti.ner.core.internal.NounPhraseItem noun = items.get(items.size() - 1);
        NounPhraseToken res = new NounPhraseToken(first, noun.getEndToken());
        res.noun = noun;
        res.setMorph(new com.pullenti.ner.MorphCollection(null));
        for (com.pullenti.morph.MorphBaseInfo v : noun.getEndToken().getMorph().getItems()) {
            if (v._getClass().isVerb()) 
                continue;
            if (v._getClass().isProper() && noun.getBeginToken().chars.isAllLower()) 
                continue;
            if (v instanceof com.pullenti.morph.MorphWordForm) {
                com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm();
                wf.copyFromWordForm((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class));
                if (hasArticle && v.getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) 
                    wf.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
                res.getMorph().addItem(wf);
            }
            else {
                com.pullenti.morph.MorphBaseInfo bi = new com.pullenti.morph.MorphBaseInfo();
                bi.copyFrom(v);
                if (hasArticle && v.getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) 
                    bi.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
                res.getMorph().addItem(bi);
            }
        }
        if (res.getMorph().getItemsCount() == 0 && hasArticle) 
            res.getMorph().addItem(com.pullenti.morph.MorphBaseInfo._new170(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR));
        for (int i = 0; i < (items.size() - 1); i++) {
            res.adjectives.add(items.get(i));
        }
        return res;
    }
    public _NounPraseHelperInt() {
    }
}
