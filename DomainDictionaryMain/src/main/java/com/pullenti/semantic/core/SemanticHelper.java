/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.core;

/**
 * Полезные фукнции для семантического анализа
 * 
 * Хелпер семантики
 */
public class SemanticHelper {

    public static String getKeyword(com.pullenti.ner.MetaToken mt) {
        com.pullenti.ner.core.VerbPhraseToken vpt = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.VerbPhraseToken.class);
        if (vpt != null) 
            return (vpt.getLastVerb().getVerbMorph().normalFull != null ? vpt.getLastVerb().getVerbMorph().normalFull : vpt.getLastVerb().getVerbMorph().normalCase);
        com.pullenti.ner.core.NounPhraseToken npt = (com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.NounPhraseToken.class);
        if (npt != null) 
            return npt.noun.getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
        return null;
    }

    public static java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> findDerivates(com.pullenti.ner.Token t) {
        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> res = null;
        com.pullenti.morph.MorphClass cla = null;
        if (t instanceof com.pullenti.ner.core.NounPhraseToken) {
            t = ((com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.core.NounPhraseToken.class)).noun.getEndToken();
            cla = com.pullenti.morph.MorphClass.NOUN;
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if (!mc.isNoun()) 
                cla = mc;
        }
        if (t instanceof com.pullenti.ner.TextToken) {
            for (com.pullenti.morph.MorphBaseInfo f : t.getMorph().getItems()) {
                if (f instanceof com.pullenti.morph.MorphWordForm) {
                    if (cla != null) {
                        if (((com.pullenti.morph.MorphClass.ooBitand(cla, f._getClass()))).isUndefined()) 
                            continue;
                    }
                    res = com.pullenti.semantic.utils.DerivateService.findDerivates((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class)).normalFull != null ? ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class)).normalFull : ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class)).normalCase), true, null);
                    if (res != null && res.size() > 0) 
                        return res;
                }
            }
            return null;
        }
        if (t instanceof com.pullenti.ner.core.VerbPhraseToken) 
            return findDerivates(((com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.core.VerbPhraseToken.class)).getLastVerb());
        if (t instanceof com.pullenti.ner.core.VerbPhraseItemToken) {
            com.pullenti.ner.core.VerbPhraseItemToken vpt = (com.pullenti.ner.core.VerbPhraseItemToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.core.VerbPhraseItemToken.class);
            if (vpt.getVerbMorph() != null) {
                res = com.pullenti.semantic.utils.DerivateService.findDerivates(vpt.getVerbMorph().normalCase, true, t.getMorph().getLanguage());
                if (res == null || (res.size() == 0 && vpt.getVerbMorph().normalFull != null && com.pullenti.unisharp.Utils.stringsNe(vpt.getVerbMorph().normalCase, vpt.getVerbMorph().normalFull))) 
                    res = com.pullenti.semantic.utils.DerivateService.findDerivates(vpt.getVerbMorph().normalFull, true, t.getMorph().getLanguage());
            }
            return res;
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "1")) 
                return com.pullenti.semantic.utils.DerivateService.findDerivates("ОДИН", true, com.pullenti.morph.MorphLang.RU);
        }
        if (t instanceof com.pullenti.ner.MetaToken) 
            return findDerivates(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getEndToken());
        return null;
    }

    public static com.pullenti.semantic.utils.DerivateWord findWordInGroup(com.pullenti.ner.MetaToken mt, com.pullenti.semantic.utils.DerivateGroup gr) {
        if (gr == null || mt == null) 
            return null;
        com.pullenti.ner.Token t = null;
        if (mt instanceof com.pullenti.ner.core.NounPhraseToken) 
            t = ((com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.NounPhraseToken.class)).noun.getEndToken();
        else if ((mt instanceof SemanticAbstractSlave) && (((SemanticAbstractSlave)com.pullenti.unisharp.Utils.cast(mt, SemanticAbstractSlave.class)).source instanceof com.pullenti.ner.core.NounPhraseToken)) 
            t = ((com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(((SemanticAbstractSlave)com.pullenti.unisharp.Utils.cast(mt, SemanticAbstractSlave.class)).source, com.pullenti.ner.core.NounPhraseToken.class)).noun.getEndToken();
        else 
            t = mt.getEndToken();
        for (com.pullenti.semantic.utils.DerivateWord w : gr.words) {
            if (w._class != null && w._class.isNoun() && ((w.lang == null || w.lang.isRu()))) {
                if (t.isValue(w.spelling, null)) 
                    return w;
            }
        }
        return null;
    }

    private static com.pullenti.semantic.utils.ControlModelItem findControlItem(com.pullenti.ner.MetaToken mt, com.pullenti.semantic.utils.DerivateGroup gr) {
        if (gr == null) 
            return null;
        if (mt instanceof com.pullenti.ner.core.NounPhraseToken) {
            com.pullenti.ner.Token t = ((com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.NounPhraseToken.class)).noun.getEndToken();
            for (com.pullenti.semantic.utils.ControlModelItem m : gr.model.items) {
                if (m.word != null) {
                    if (t.isValue(m.word, null)) 
                        return m;
                }
            }
            for (com.pullenti.semantic.utils.DerivateWord w : gr.words) {
                if (w.attrs.isVerbNoun()) {
                    if (t.isValue(w.spelling, null)) 
                        return gr.model.findItemByTyp(com.pullenti.semantic.utils.ControlModelItemType.NOUN);
                }
            }
            return null;
        }
        if (mt instanceof com.pullenti.ner.core.VerbPhraseItemToken) {
            com.pullenti.ner.core.VerbPhraseItemToken ti = (com.pullenti.ner.core.VerbPhraseItemToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.VerbPhraseItemToken.class);
            boolean rev = ti.isVerbReversive() || ti.isVerbPassive();
            for (com.pullenti.semantic.utils.ControlModelItem it : gr.model.items) {
                if (rev && it.typ == com.pullenti.semantic.utils.ControlModelItemType.REFLEXIVE) 
                    return it;
                else if (!rev && it.typ == com.pullenti.semantic.utils.ControlModelItemType.VERB) 
                    return it;
            }
        }
        return null;
    }

    /**
     * Попробовать создать семантическую связь между элементами. 
     * Элементом м.б. именная (NounPhraseToken) или глагольная группа (VerbPhraseToken).
     * @param master основной элемент
     * @param slave стыкуемый элемент (также м.б. SemanticAbstractSlave)
     * @param onto дополнительный онтологический словарь
     * @return список вариантов (возможно, пустой)
     * 
     */
    public static java.util.ArrayList<SemanticLink> tryCreateLinks(com.pullenti.ner.MetaToken master, com.pullenti.ner.MetaToken slave, ISemanticOnto onto) {
        java.util.ArrayList<SemanticLink> res = new java.util.ArrayList<SemanticLink>();
        com.pullenti.ner.core.VerbPhraseToken vpt1 = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(master, com.pullenti.ner.core.VerbPhraseToken.class);
        com.pullenti.ner.core.VerbPhraseToken vpt2 = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(slave, com.pullenti.ner.core.VerbPhraseToken.class);
        com.pullenti.ner.core.NounPhraseToken npt1 = (com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(master, com.pullenti.ner.core.NounPhraseToken.class);
        if (slave instanceof com.pullenti.ner.core.NounPhraseToken) 
            slave = SemanticAbstractSlave.createFromNoun((com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(slave, com.pullenti.ner.core.NounPhraseToken.class));
        SemanticAbstractSlave sla2 = (SemanticAbstractSlave)com.pullenti.unisharp.Utils.cast(slave, SemanticAbstractSlave.class);
        if (vpt2 != null) {
            if (!vpt2.getFirstVerb().isVerbInfinitive() || !vpt2.getLastVerb().isVerbInfinitive()) 
                return res;
        }
        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = findDerivates(master);
        if (grs == null || grs.size() == 0) {
            java.util.ArrayList<SemanticLink> rl = (vpt1 != null ? _tryCreateVerb(vpt1, slave, null) : _tryCreateNoun(npt1, slave, null));
            if (rl != null) 
                com.pullenti.unisharp.Utils.addToArrayList(res, rl);
        }
        else 
            for (com.pullenti.semantic.utils.DerivateGroup gr : grs) {
                java.util.ArrayList<SemanticLink> rl = (vpt1 != null ? _tryCreateVerb(vpt1, slave, gr) : _tryCreateNoun(npt1, slave, gr));
                if (rl == null || rl.size() == 0) 
                    continue;
                com.pullenti.unisharp.Utils.addToArrayList(res, rl);
            }
        if ((npt1 != null && sla2 != null && sla2.getMorph().getCase().isGenitive()) && sla2.preposition == null) {
            if (npt1.noun.getBeginToken().getMorphClassInDictionary().isPersonalPronoun()) {
            }
            else {
                boolean hasGen = false;
                for (SemanticLink r : res) {
                    if (r.question == com.pullenti.semantic.utils.ControlModelQuestion.getBaseGenetive()) {
                        hasGen = true;
                        break;
                    }
                }
                if (!hasGen) 
                    res.add(SemanticLink._new3075(true, npt1, sla2, 0.5, com.pullenti.semantic.utils.ControlModelQuestion.getBaseGenetive()));
            }
        }
        if (onto != null) {
            String str1 = getKeyword(master);
            String str2 = getKeyword(slave);
            if (str2 != null) {
                if (onto.checkLink(str1, str2)) {
                    if (res.size() > 0) {
                        for (SemanticLink r : res) {
                            r.rank += (3.0);
                            if (r.role == SemanticRole.COMMON) 
                                r.role = SemanticRole.STRONG;
                        }
                    }
                    else 
                        res.add(SemanticLink._new3076(SemanticRole.STRONG, master, slave, 3.0));
                }
            }
        }
        if (npt1 != null) {
            if (((npt1.adjectives.size() > 0 && npt1.adjectives.get(0).getBeginToken().getMorph()._getClass().isPronoun())) || npt1.anafor != null) {
                for (SemanticLink r : res) {
                    if (r.question == com.pullenti.semantic.utils.ControlModelQuestion.getBaseGenetive()) {
                        r.rank -= 0.5;
                        if (r.role == SemanticRole.STRONG) 
                            r.role = SemanticRole.COMMON;
                    }
                }
            }
        }
        for (SemanticLink r : res) {
            if (r.role == SemanticRole.STRONG) {
                for (SemanticLink rr : res) {
                    if (rr != r && rr.role != SemanticRole.STRONG) 
                        rr.rank /= (2.0);
                }
            }
        }
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < (res.size() - 1); j++) {
                if (res.get(j).compareTo(res.get(j + 1)) > 0) {
                    SemanticLink r = res.get(j);
                    com.pullenti.unisharp.Utils.putArrayValue(res, j, res.get(j + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(res, j + 1, r);
                }
            }
        }
        for (SemanticLink r : res) {
            r.master = master;
            r.slave = slave;
        }
        return res;
    }

    private static java.util.ArrayList<SemanticLink> _tryCreateInf(com.pullenti.ner.MetaToken master, com.pullenti.ner.core.VerbPhraseToken vpt2, com.pullenti.semantic.utils.DerivateGroup gr) {
        com.pullenti.semantic.utils.ControlModelItem cit = findControlItem(master, gr);
        java.util.ArrayList<SemanticLink> res = new java.util.ArrayList<SemanticLink>();
        SemanticRole rol = null;
        if (cit != null && cit.links.containsKey(com.pullenti.semantic.utils.ControlModelQuestion.getToDo())) 
            rol = cit.links.get(com.pullenti.semantic.utils.ControlModelQuestion.getToDo());
        if (rol != null) 
            res.add(SemanticLink._new3077((double)(rol != SemanticRole.COMMON ? 2 : 1), com.pullenti.semantic.utils.ControlModelQuestion.getToDo()));
        return res;
    }

    private static java.util.ArrayList<SemanticLink> _tryCreateNoun(com.pullenti.ner.core.NounPhraseToken npt1, com.pullenti.ner.MetaToken slave, com.pullenti.semantic.utils.DerivateGroup gr) {
        if (npt1 == null || slave == null) 
            return null;
        if (slave instanceof com.pullenti.ner.core.VerbPhraseToken) 
            return _tryCreateInf(npt1, (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(slave, com.pullenti.ner.core.VerbPhraseToken.class), gr);
        SemanticAbstractSlave sla2 = (SemanticAbstractSlave)com.pullenti.unisharp.Utils.cast(slave, SemanticAbstractSlave.class);
        java.util.ArrayList<SemanticLink> res = new java.util.ArrayList<SemanticLink>();
        if (sla2 == null) 
            return res;
        com.pullenti.semantic.utils.ControlModelItem cit = findControlItem(npt1, gr);
        _createRoles(cit, sla2.preposition, sla2.getMorph().getCase(), res, false, false);
        if (res.size() == 1 && res.get(0).role == SemanticRole.AGENT && res.get(0).question == com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental()) {
            if (gr.model.items.size() > 0 && gr.model.items.get(0).typ == com.pullenti.semantic.utils.ControlModelItemType.VERB && gr.model.items.get(0).links.containsKey(com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental())) 
                res.get(0).role = gr.model.items.get(0).links.get(com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental());
        }
        boolean ok = false;
        com.pullenti.semantic.utils.DerivateWord w = findWordInGroup(npt1, gr);
        if (w != null && w.nextWords != null && w.nextWords.size() > 0) {
            for (String n : w.nextWords) {
                if (sla2.source != null) {
                    if (sla2.source.getEndToken().isValue(n, null)) {
                        ok = true;
                        break;
                    }
                }
            }
        }
        if (gr != null && gr.model.pacients.size() > 0) {
            for (String n : gr.model.pacients) {
                if (sla2.source != null) {
                    if (sla2.source.getEndToken().isValue(n, null)) {
                        ok = true;
                        break;
                    }
                }
            }
        }
        if (ok) {
            if (res.size() == 0) 
                res.add(SemanticLink._new3078(com.pullenti.semantic.utils.ControlModelQuestion.getBaseGenetive(), SemanticRole.PACIENT, true));
            for (SemanticLink r : res) {
                r.rank += (4.0);
                if (r.role == SemanticRole.COMMON) 
                    r.role = SemanticRole.STRONG;
                if (npt1.getEndToken().getNext() == sla2.getBeginToken()) 
                    r.rank += (2.0);
                r.idiom = true;
            }
        }
        return res;
    }

    private static java.util.ArrayList<SemanticLink> _tryCreateVerb(com.pullenti.ner.core.VerbPhraseToken vpt1, com.pullenti.ner.MetaToken slave, com.pullenti.semantic.utils.DerivateGroup gr) {
        if (slave instanceof com.pullenti.ner.core.VerbPhraseToken) 
            return _tryCreateInf(vpt1, (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(slave, com.pullenti.ner.core.VerbPhraseToken.class), gr);
        SemanticAbstractSlave sla2 = (SemanticAbstractSlave)com.pullenti.unisharp.Utils.cast(slave, SemanticAbstractSlave.class);
        java.util.ArrayList<SemanticLink> res = new java.util.ArrayList<SemanticLink>();
        if (sla2 == null) 
            return res;
        com.pullenti.semantic.utils.ControlModelItem cit = findControlItem(vpt1.getLastVerb(), gr);
        String prep = sla2.preposition;
        com.pullenti.morph.MorphBaseInfo _morph = sla2.getMorph();
        boolean isRev1 = vpt1.getLastVerb().isVerbReversive() || vpt1.getLastVerb().isVerbPassive();
        boolean noNomin = false;
        boolean noInstr = false;
        if (prep == null && _morph.getCase().isNominative() && !vpt1.getFirstVerb().isParticiple()) {
            if (vpt1.getFirstVerb().getVerbMorph() == null) 
                noNomin = true;
            else {
                boolean ok = true;
                boolean err = false;
                com.pullenti.morph.MorphWordForm vm = vpt1.getFirstVerb().getVerbMorph();
                if (vm.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (_morph.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                        if (!vpt1.getFirstVerb().isVerbInfinitive()) 
                            ok = false;
                    }
                }
                if (!checkMorphAccord(_morph, false, vm, false)) {
                    if (!err && !vpt1.getFirstVerb().isVerbInfinitive()) 
                        ok = false;
                }
                else if (vm.misc.getPerson() != com.pullenti.morph.MorphPerson.UNDEFINED) {
                    if (((short)((vm.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.THIRD.value()))) == (com.pullenti.morph.MorphPerson.UNDEFINED.value())) {
                        if (((short)((vm.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.FIRST.value()))) == (com.pullenti.morph.MorphPerson.FIRST.value())) {
                            if (!_morph.containsAttr("1 л.", null)) 
                                ok = false;
                        }
                        if (((short)((vm.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.SECOND.value()))) == (com.pullenti.morph.MorphPerson.SECOND.value())) {
                            if (!_morph.containsAttr("2 л.", null)) 
                                ok = false;
                        }
                    }
                }
                noNomin = true;
                if (ok) {
                    com.pullenti.semantic.utils.ControlModelItem cit00 = cit;
                    boolean isRev0 = isRev1;
                    if (vpt1.getFirstVerb() != vpt1.getLastVerb() && ((vpt1.getFirstVerb().isVerbReversive() || vpt1.getFirstVerb().isVerbPassive() || com.pullenti.unisharp.Utils.stringsEq(vpt1.getFirstVerb().getNormal(), "ИМЕТЬ")))) {
                        cit00 = null;
                        isRev0 = true;
                        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = findDerivates(vpt1.getFirstVerb());
                        if (grs != null) {
                            for (com.pullenti.semantic.utils.DerivateGroup gg : grs) {
                                if ((((cit00 = findControlItem(vpt1.getFirstVerb(), gg)))) != null) 
                                    break;
                            }
                        }
                    }
                    SemanticLink sl = null;
                    boolean addagent = false;
                    if (cit00 == null) 
                        sl = SemanticLink._new3079(true, (isRev0 ? SemanticRole.PACIENT : SemanticRole.AGENT), 1.0, com.pullenti.semantic.utils.ControlModelQuestion.getBaseNominative(), isRev0);
                    else 
                        for (java.util.Map.Entry<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole> kp : cit00.links.entrySet()) {
                            com.pullenti.semantic.utils.ControlModelQuestion q = kp.getKey();
                            if (q.check(null, com.pullenti.morph.MorphCase.NOMINATIVE)) {
                                sl = SemanticLink._new3080(kp.getValue(), 2.0, q, isRev0);
                                if (sl.role == SemanticRole.AGENT) 
                                    sl.isPassive = false;
                                else if (sl.role == SemanticRole.PACIENT && cit00.nominativeCanBeAgentAndPacient && vpt1.getLastVerb().isVerbReversive()) 
                                    addagent = true;
                                break;
                            }
                        }
                    if (sl != null) {
                        if (cit00 == null && _morph.getCase().isInstrumental() && isRev0) 
                            sl.rank -= 0.5;
                        if (_morph.getCase().isAccusative()) 
                            sl.rank -= 0.5;
                        if (sla2.getBeginChar() > vpt1.getBeginChar()) 
                            sl.rank -= 0.5;
                        if (err) 
                            sl.rank -= 0.5;
                        res.add(sl);
                        if (addagent) 
                            res.add(SemanticLink._new3081(SemanticRole.AGENT, sl.rank, sl.question));
                    }
                }
            }
        }
        if (prep == null && isRev1 && _morph.getCase().isInstrumental()) {
            noInstr = true;
            com.pullenti.semantic.utils.ControlModelItem cit00 = cit;
            SemanticLink sl = null;
            if (cit00 == null) 
                sl = SemanticLink._new3079(true, SemanticRole.AGENT, 1.0, com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental(), true);
            else 
                for (java.util.Map.Entry<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole> kp : cit00.links.entrySet()) {
                    com.pullenti.semantic.utils.ControlModelQuestion q = kp.getKey();
                    if (q.check(null, com.pullenti.morph.MorphCase.INSTRUMENTAL)) {
                        sl = SemanticLink._new3081(kp.getValue(), 2.0, q);
                        if (sl.role == SemanticRole.AGENT) 
                            sl.isPassive = true;
                        break;
                    }
                }
            if (sl != null) {
                if (cit00 == null && _morph.getCase().isNominative()) 
                    sl.rank -= 0.5;
                if (_morph.getCase().isAccusative()) 
                    sl.rank -= 0.5;
                if (sla2.getBeginChar() < vpt1.getBeginChar()) 
                    sl.rank -= 0.5;
                res.add(sl);
                if ((gr != null && gr.model.items.size() > 0 && gr.model.items.get(0).typ == com.pullenti.semantic.utils.ControlModelItemType.VERB) && gr.model.items.get(0).links.containsKey(com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental())) {
                    sl.rank = 0.0;
                    SemanticLink sl0 = SemanticLink._new3084(sl.question, 1.0, gr.model.items.get(0).links.get(com.pullenti.semantic.utils.ControlModelQuestion.getBaseInstrumental()));
                    res.add(0, sl0);
                }
            }
        }
        if (prep == null && _morph.getCase().isDative() && ((cit == null || !cit.links.containsKey(com.pullenti.semantic.utils.ControlModelQuestion.getBaseDative())))) {
            SemanticLink sl = SemanticLink._new3085(cit == null, SemanticRole.STRONG, 1.0, com.pullenti.semantic.utils.ControlModelQuestion.getBaseDative());
            if (_morph.getCase().isAccusative() || _morph.getCase().isNominative()) 
                sl.rank -= 0.5;
            if (vpt1.getEndToken().getNext() != sla2.getBeginToken()) 
                sl.rank -= 0.5;
            if (cit != null) 
                sl.rank -= 0.5;
            res.add(sl);
        }
        _createRoles(cit, prep, _morph.getCase(), res, noNomin, noInstr);
        if (gr != null && gr.model.pacients.size() > 0) {
            boolean ok = false;
            for (String n : gr.model.pacients) {
                if (sla2.source != null) {
                    if (sla2.source.getEndToken().isValue(n, null)) {
                        ok = true;
                        break;
                    }
                }
                else if (sla2.getEndToken().isValue(n, null)) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                if (res.size() == 0) {
                    ok = false;
                    if (prep == null && isRev1 && _morph.getCase().isNominative()) 
                        ok = true;
                    else if (prep == null && !isRev1 && _morph.getCase().isAccusative()) 
                        ok = true;
                    if (ok) 
                        res.add(SemanticLink._new3086(SemanticRole.PACIENT, (isRev1 ? com.pullenti.semantic.utils.ControlModelQuestion.getBaseNominative() : com.pullenti.semantic.utils.ControlModelQuestion.getBaseAccusative()), true));
                }
                else 
                    for (SemanticLink r : res) {
                        r.rank += (4.0);
                        if (r.role == SemanticRole.COMMON) 
                            r.role = SemanticRole.STRONG;
                        if (vpt1.getEndToken().getNext() == sla2.getBeginToken()) 
                            r.rank += (2.0);
                        r.idiom = true;
                    }
            }
        }
        return res;
    }

    private static void _createRoles(com.pullenti.semantic.utils.ControlModelItem cit, String prep, com.pullenti.morph.MorphCase cas, java.util.ArrayList<SemanticLink> res, boolean ignoreNominCase, boolean ignoreInstrCase) {
        if (cit == null) 
            return;
        java.util.HashMap<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole> roles = null;
        for (java.util.Map.Entry<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole> li : cit.links.entrySet()) {
            com.pullenti.semantic.utils.ControlModelQuestion q = li.getKey();
            if (q.check(prep, cas)) {
                if (ignoreNominCase && q._case.isNominative() && q.preposition == null) 
                    continue;
                if (ignoreInstrCase && q._case.isInstrumental() && q.preposition == null) 
                    continue;
                if (roles == null) 
                    roles = new java.util.HashMap<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole>();
                SemanticRole r = li.getValue();
                if (q.isAbstract) {
                    com.pullenti.semantic.utils.ControlModelQuestion qq = q.checkAbstract(prep, cas);
                    if (qq != null) {
                        q = qq;
                        r = SemanticRole.COMMON;
                    }
                }
                if (!roles.containsKey(q)) 
                    roles.put(q, r);
                else if (r != SemanticRole.COMMON) 
                    roles.put(q, r);
            }
        }
        if (roles != null) {
            for (java.util.Map.Entry<com.pullenti.semantic.utils.ControlModelQuestion, SemanticRole> kp : roles.entrySet()) {
                SemanticLink sl = SemanticLink._new3081(kp.getValue(), 2.0, kp.getKey());
                if (kp.getValue() == SemanticRole.AGENT) {
                    if (!kp.getKey().isBase) 
                        sl.role = SemanticRole.COMMON;
                }
                if (sl.role == SemanticRole.STRONG) 
                    sl.rank += (2.0);
                res.add(sl);
            }
        }
    }

    public static boolean checkMorphAccord(com.pullenti.morph.MorphBaseInfo m, boolean plural, com.pullenti.morph.MorphBaseInfo vf, boolean checkCase) {
        if (checkCase && !m.getCase().isUndefined() && !vf.getCase().isUndefined()) {
            if (((com.pullenti.morph.MorphCase.ooBitand(m.getCase(), vf.getCase()))).isUndefined()) 
                return false;
        }
        double coef = 0.0;
        if (vf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (plural) 
                coef++;
            else if (m.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((m.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) 
                    coef++;
                else 
                    return false;
            }
        }
        else if (vf.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
            if (plural) 
                return false;
            if (m.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((m.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) == (com.pullenti.morph.MorphNumber.SINGULAR.value())) 
                    coef++;
                else 
                    return false;
            }
            if (m.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (vf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (m.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                        if (((short)((vf.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            coef++;
                        else 
                            return false;
                    }
                    else if (((short)((m.getGender().value()) & (vf.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        coef++;
                    else if (m.getGender() == com.pullenti.morph.MorphGender.MASCULINE && vf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                    }
                    else 
                        return false;
                }
            }
        }
        return coef >= 0;
    }
    public SemanticHelper() {
    }
}
