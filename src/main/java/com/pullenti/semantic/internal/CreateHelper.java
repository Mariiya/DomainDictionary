/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class CreateHelper {

    public static void _setMorph(com.pullenti.semantic.SemObject obj, com.pullenti.morph.MorphWordForm wf) {
        if (wf == null) 
            return;
        obj.morph.normalCase = wf.normalCase;
        obj.morph.normalFull = (wf.normalFull != null ? wf.normalFull : wf.normalCase);
        obj.morph.setNumber(wf.getNumber());
        obj.morph.setGender(wf.getGender());
        obj.morph.misc = wf.misc;
    }

    public static void _setMorph0(com.pullenti.semantic.SemObject obj, com.pullenti.morph.MorphBaseInfo bi) {
        obj.morph.setNumber(bi.getNumber());
        obj.morph.setGender(bi.getGender());
    }

    public static com.pullenti.semantic.SemObject createNounGroup(com.pullenti.semantic.SemGraph gr, com.pullenti.ner.core.NounPhraseToken npt) {
        com.pullenti.ner.Token noun = npt.noun.getBeginToken();
        com.pullenti.semantic.SemObject sem = new com.pullenti.semantic.SemObject(gr);
        sem.tokens.add(npt.noun);
        sem.typ = com.pullenti.semantic.SemObjectType.NOUN;
        if (npt.noun.getMorph()._getClass().isPersonalPronoun()) 
            sem.typ = com.pullenti.semantic.SemObjectType.PERSONALPRONOUN;
        else if (npt.noun.getMorph()._getClass().isPronoun()) 
            sem.typ = com.pullenti.semantic.SemObjectType.PRONOUN;
        if (npt.noun.getBeginToken() != npt.noun.getEndToken()) {
            sem.morph.normalCase = npt.noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
            sem.morph.normalFull = npt.noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
            sem.morph._setClass(com.pullenti.morph.MorphClass.NOUN);
            sem.morph.setNumber(npt.getMorph().getNumber());
            sem.morph.setGender(npt.getMorph().getGender());
            sem.morph.setCase(npt.getMorph().getCase());
        }
        else if (noun instanceof com.pullenti.ner.TextToken) {
            for (com.pullenti.morph.MorphBaseInfo wf : noun.getMorph().getItems()) {
                if (wf.checkAccord(npt.getMorph(), false, false) && (wf instanceof com.pullenti.morph.MorphWordForm)) {
                    _setMorph(sem, (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class));
                    break;
                }
            }
            if (sem.morph.normalCase == null) {
                sem.morph.normalCase = noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                sem.morph.normalFull = noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
            }
            java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(sem.morph.normalFull, true, null);
            if (grs != null && grs.size() > 0) 
                sem.concept = grs.get(0);
        }
        else if (noun instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.Referent r = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.ReferentToken.class)).referent;
            if (r == null) 
                return null;
            sem.morph.normalFull = (sem.morph.normalCase = r.toString());
            sem.concept = r;
        }
        else if (noun instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.NumberToken num = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(noun, com.pullenti.ner.NumberToken.class);
            sem.morph.setGender(noun.getMorph().getGender());
            sem.morph.setNumber(noun.getMorph().getNumber());
            if (num.getIntValue() != null) {
                sem.morph.normalCase = com.pullenti.ner.core.NumberHelper.getNumberAdjective(num.getIntValue(), noun.getMorph().getGender(), noun.getMorph().getNumber());
                sem.morph.normalFull = com.pullenti.ner.core.NumberHelper.getNumberAdjective(num.getIntValue(), com.pullenti.morph.MorphGender.MASCULINE, com.pullenti.morph.MorphNumber.SINGULAR);
            }
            else 
                sem.morph.normalFull = (sem.morph.normalCase = noun.getSourceText().toUpperCase());
        }
        noun.tag = sem;
        if (npt.adjectives.size() > 0) {
            for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                if (npt.multiNouns && a != npt.adjectives.get(0)) 
                    break;
                com.pullenti.semantic.SemObject asem = createNptAdj(gr, npt, a);
                if (asem != null) 
                    gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, sem, asem, "какой", false, null);
            }
        }
        if (npt.internalNoun != null) {
            com.pullenti.semantic.SemObject intsem = createNounGroup(gr, npt.internalNoun);
            if (intsem != null) 
                gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, sem, intsem, null, false, null);
        }
        gr.objects.add(sem);
        return sem;
    }

    public static com.pullenti.semantic.SemObject createNumber(com.pullenti.semantic.SemGraph gr, com.pullenti.ner.measure.internal.NumbersWithUnitToken num) {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rs = num.createRefenetsTokensWithRegister(null, null, false);
        if (rs == null || rs.size() == 0) 
            return null;
        com.pullenti.ner.measure.MeasureReferent mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(rs.get(rs.size() - 1).referent, com.pullenti.ner.measure.MeasureReferent.class);
        com.pullenti.semantic.SemObject sem = new com.pullenti.semantic.SemObject(gr);
        gr.objects.add(sem);
        sem.tokens.add(num);
        sem.morph.normalFull = (sem.morph.normalCase = mr.toStringEx(true, null, 0));
        sem.typ = com.pullenti.semantic.SemObjectType.NOUN;
        sem.measure = mr.getKind();
        for (int i = 0; i < sem.morph.normalCase.length(); i++) {
            char ch = sem.morph.normalCase.charAt(i);
            if (Character.isDigit(ch) || com.pullenti.unisharp.Utils.isWhitespace(ch) || "[].+-".indexOf(ch) >= 0) 
                continue;
            sem.quantity = new com.pullenti.semantic.SemQuantity(sem.morph.normalCase.substring(0, 0 + i).trim(), num.getBeginToken(), num.getEndToken());
            sem.morph.normalCase = sem.morph.normalCase.substring(i).trim();
            if (num.units.size() == 1 && num.units.get(0).unit != null) {
                sem.morph.normalFull = num.units.get(0).unit.fullnameCyr;
                if (com.pullenti.unisharp.Utils.stringsEq(sem.morph.normalFull, "%")) 
                    sem.morph.normalFull = "процент";
            }
            break;
        }
        sem.concept = mr;
        return sem;
    }

    public static com.pullenti.semantic.SemObject createAdverb(com.pullenti.semantic.SemGraph gr, AdverbToken adv) {
        com.pullenti.semantic.SemObject res = new com.pullenti.semantic.SemObject(gr);
        gr.objects.add(res);
        res.tokens.add(adv);
        res.typ = com.pullenti.semantic.SemObjectType.ADVERB;
        res.not = adv.not;
        res.morph.normalCase = (res.morph.normalFull = adv.getSpelling());
        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(res.morph.normalFull, true, null);
        if (grs != null && grs.size() > 0) 
            res.concept = grs.get(0);
        return res;
    }

    public static com.pullenti.semantic.SemObject createNptAdj(com.pullenti.semantic.SemGraph gr, com.pullenti.ner.core.NounPhraseToken npt, com.pullenti.ner.MetaToken a) {
        if (a.getMorph()._getClass().isPronoun()) {
            com.pullenti.semantic.SemObject asem = new com.pullenti.semantic.SemObject(gr);
            gr.objects.add(asem);
            asem.tokens.add(a);
            asem.typ = (a.getBeginToken().getMorph()._getClass().isPersonalPronoun() ? com.pullenti.semantic.SemObjectType.PERSONALPRONOUN : com.pullenti.semantic.SemObjectType.PRONOUN);
            for (com.pullenti.morph.MorphBaseInfo it : a.getBeginToken().getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (wf == null) 
                    continue;
                if (!npt.getMorph().getCase().isUndefined()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(npt.getMorph().getCase(), wf.getCase()))).isUndefined()) 
                        continue;
                }
                _setMorph(asem, wf);
                if (com.pullenti.unisharp.Utils.stringsEq(asem.morph.normalFull, "КАКОВ")) 
                    asem.morph.normalFull = "КАКОЙ";
                break;
            }
            if (asem.morph.normalFull == null) 
                asem.morph.normalFull = (asem.morph.normalCase = a.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
            return asem;
        }
        if (!a.getMorph()._getClass().isVerb()) {
            com.pullenti.semantic.SemObject asem = new com.pullenti.semantic.SemObject(gr);
            gr.objects.add(asem);
            asem.tokens.add(a);
            asem.typ = com.pullenti.semantic.SemObjectType.ADJECTIVE;
            for (com.pullenti.morph.MorphBaseInfo wf : a.getBeginToken().getMorph().getItems()) {
                if (wf.checkAccord(npt.getMorph(), false, false) && wf._getClass().isAdjective() && (wf instanceof com.pullenti.morph.MorphWordForm)) {
                    _setMorph(asem, (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class));
                    break;
                }
            }
            if (asem.morph.normalCase == null) {
                asem.morph.normalCase = a.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                asem.morph.normalFull = a.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
                _setMorph0(asem, a.getBeginToken().getMorph());
            }
            java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(asem.morph.normalFull, true, null);
            if (grs != null && grs.size() > 0) 
                asem.concept = grs.get(0);
            return asem;
        }
        return null;
    }

    public static com.pullenti.semantic.SemObject createVerbGroup(com.pullenti.semantic.SemGraph gr, com.pullenti.ner.core.VerbPhraseToken vpt) {
        java.util.ArrayList<com.pullenti.semantic.SemObject> sems = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
        java.util.ArrayList<com.pullenti.semantic.SemAttribute> attrs = new java.util.ArrayList<com.pullenti.semantic.SemAttribute>();
        java.util.ArrayList<com.pullenti.semantic.SemObject> adverbs = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
        for (int i = 0; i < vpt.items.size(); i++) {
            com.pullenti.ner.core.VerbPhraseItemToken v = vpt.items.get(i);
            if (v.isAdverb) {
                AdverbToken adv = AdverbToken.tryParse(v.getBeginToken());
                if (adv == null) 
                    continue;
                if (adv.typ != com.pullenti.semantic.SemAttributeType.UNDEFINED) {
                    attrs.add(com.pullenti.semantic.SemAttribute._new3112(adv.not, adv.typ, adv.getSpelling()));
                    continue;
                }
                com.pullenti.semantic.SemObject adverb = createAdverb(gr, adv);
                if (attrs.size() > 0) {
                    com.pullenti.unisharp.Utils.addToArrayList(adverb.attrs, attrs);
                    attrs.clear();
                }
                adverbs.add(adverb);
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(v.getNormal(), "БЫТЬ")) {
                int j;
                for (j = i + 1; j < vpt.items.size(); j++) {
                    if (!vpt.items.get(j).isAdverb) 
                        break;
                }
                if (j < vpt.items.size()) 
                    continue;
            }
            com.pullenti.semantic.SemObject sem = new com.pullenti.semantic.SemObject(gr);
            gr.objects.add(sem);
            sem.tokens.add(v);
            v.tag = sem;
            _setMorph(sem, v.getVerbMorph());
            sem.morph.normalCase = (sem.morph.normalFull = v.getNormal());
            if (v.isParticiple() || v.isDeeParticiple()) {
                sem.typ = com.pullenti.semantic.SemObjectType.PARTICIPLE;
                sem.morph.normalFull = (String)com.pullenti.unisharp.Utils.notnull(v.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.VERB, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), (sem != null && sem.morph != null ? sem.morph.normalCase : null));
                sem.morph.normalCase = v.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                if (com.pullenti.unisharp.Utils.stringsEq(sem.morph.normalCase, sem.morph.normalFull) && v.getNormal().endsWith("Й")) {
                    java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs2 = com.pullenti.semantic.utils.DerivateService.findDerivates(v.getNormal(), true, null);
                    if (grs2 != null) {
                        for (com.pullenti.semantic.utils.DerivateGroup g : grs2) {
                            for (com.pullenti.semantic.utils.DerivateWord w : g.words) {
                                if (w.lang.equals(v.getEndToken().getMorph().getLanguage()) && w._class.isVerb() && !w._class.isAdjective()) {
                                    sem.morph.normalFull = w.spelling;
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(sem.morph.normalCase, sem.morph.normalFull) && v.isParticiple() && sem.morph.normalFull.endsWith("Ь")) {
                    for (com.pullenti.morph.MorphBaseInfo it : v.getEndToken().getMorph().getItems()) {
                        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                        if (wf == null) 
                            continue;
                        if (wf.normalCase.endsWith("Й") || ((wf.normalFull != null && wf.normalFull.endsWith("Й")))) {
                            sem.morph.normalCase = (wf.normalFull != null ? wf.normalFull : wf.normalCase);
                            break;
                        }
                    }
                    if (com.pullenti.unisharp.Utils.stringsEq(sem.morph.normalCase, sem.morph.normalFull)) {
                        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs2 = com.pullenti.semantic.utils.DerivateService.findDerivates(sem.morph.normalCase, true, null);
                        if (grs2 != null) {
                            for (com.pullenti.semantic.utils.DerivateGroup g : grs2) {
                                for (com.pullenti.semantic.utils.DerivateWord w : g.words) {
                                    if (w.lang.equals(v.getEndToken().getMorph().getLanguage()) && w._class.isVerb() && w._class.isAdjective()) {
                                        sem.morph.normalCase = w.spelling;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            else 
                sem.typ = com.pullenti.semantic.SemObjectType.VERB;
            if (v.getVerbMorph() != null && v.getVerbMorph().containsAttr("возвр.", null)) {
                if (sem.morph.normalFull.endsWith("СЯ") || sem.morph.normalFull.endsWith("СЬ")) 
                    sem.morph.normalFull = sem.morph.normalFull.substring(0, 0 + sem.morph.normalFull.length() - 2);
            }
            java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(sem.morph.normalFull, true, null);
            if (grs != null && grs.size() > 0) {
                sem.concept = grs.get(0);
                if (v.getVerbMorph() != null && v.getVerbMorph().misc.getAspect() == com.pullenti.morph.MorphAspect.IMPERFECTIVE) {
                    for (com.pullenti.semantic.utils.DerivateWord w : grs.get(0).words) {
                        if (w._class.isVerb() && !w._class.isAdjective()) {
                            if (w.aspect == com.pullenti.morph.MorphAspect.PERFECTIVE) {
                                sem.morph.normalFull = w.spelling;
                                break;
                            }
                        }
                    }
                }
            }
            sem.not = v.not;
            sems.add(sem);
            if (attrs.size() > 0) {
                com.pullenti.unisharp.Utils.addToArrayList(sem.attrs, attrs);
                attrs.clear();
            }
            if (adverbs.size() > 0) {
                for (com.pullenti.semantic.SemObject a : adverbs) {
                    gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, sem, a, "как", false, null);
                }
            }
            adverbs.clear();
        }
        if (sems.size() == 0) 
            return null;
        if (attrs.size() > 0) 
            com.pullenti.unisharp.Utils.addToArrayList(sems.get(sems.size() - 1).attrs, attrs);
        if (adverbs.size() > 0) {
            com.pullenti.semantic.SemObject sem = sems.get(sems.size() - 1);
            for (com.pullenti.semantic.SemObject a : adverbs) {
                gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, sem, a, "как", false, null);
            }
        }
        for (int i = sems.size() - 1; i > 0; i--) {
            gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, sems.get(i - 1), sems.get(i), "что делать", false, null);
        }
        return sems.get(0);
    }

    public static String createQuestion(NGItem li) {
        String res = (((li.source.prep != null ? li.source.prep : ""))).toLowerCase();
        if (res.length() > 0) 
            res += " ";
        com.pullenti.morph.MorphCase cas = li.source.source.getMorph().getCase();
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(li.source.prep)) {
            com.pullenti.morph.MorphCase cas1 = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(li.source.prep);
            if (!cas1.isUndefined()) {
                if (!((com.pullenti.morph.MorphCase.ooBitand(cas1, cas))).isUndefined()) 
                    cas = com.pullenti.morph.MorphCase.ooBitand(cas, cas1);
            }
        }
        if (cas.isGenitive()) 
            res += "чего";
        else if (cas.isInstrumental()) 
            res += "чем";
        else if (cas.isDative()) 
            res += "чему";
        else if (cas.isAccusative()) 
            res += "что";
        else if (cas.isPrepositional()) 
            res += "чём";
        return res;
    }
    public CreateHelper() {
    }
}
