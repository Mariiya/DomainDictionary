/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class NGLink implements Comparable<NGLink> {

    public NGLinkType typ = NGLinkType.UNDEFINED;

    public NGItem from;

    public com.pullenti.ner.MorphCollection getFromMorph() {
        if (from.source.source != null) 
            return from.source.source.getMorph();
        return null;
    }


    public String getFromPrep() {
        return (from.source.prep != null ? from.source.prep : "");
    }


    public NGItem to;

    public com.pullenti.ner.MorphCollection getToMorph() {
        if (to != null && to.source.source != null) 
            return to.source.source.getMorph();
        return null;
    }


    public com.pullenti.ner.core.VerbPhraseToken toVerb;

    public double coef;

    public int plural = -1;

    public boolean fromIsPlural;

    public boolean reverce;

    public boolean toAllListItems;

    public boolean canBePacient;

    public boolean canBeParticiple;

    public NGLink altLink;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(coef).append(": ").append(typ.toString()).append(" ");
        if (plural == 1) 
            tmp.append(" PLURAL ");
        else if (plural == 0) 
            tmp.append(" SINGLE ");
        if (reverce) 
            tmp.append(" REVERCE ");
        tmp.append(from.source.toString());
        if (toAllListItems) 
            tmp.append(" ALLLISTITEMS ");
        if (to != null) 
            tmp.append(" -> ").append(to.source.toString());
        else if (toVerb != null) 
            tmp.append(" -> ").append(toVerb.toString());
        if (altLink != null) 
            tmp.append(" / ALTLINK: ").append(altLink.toString());
        return tmp.toString();
    }

    @Override
    public int compareTo(NGLink other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public void calcCoef(boolean noplural) {
        coef = -1.0;
        canBePacient = false;
        toAllListItems = false;
        plural = -1;
        if (typ == NGLinkType.GENETIVE && to != null) 
            this._calcGenetive();
        else if (typ == NGLinkType.NAME && to != null) 
            this._calcName(noplural);
        else if (typ == NGLinkType.BE && to != null) 
            this._calcBe();
        else if (typ == NGLinkType.LIST) 
            this._calcList();
        else if (typ == NGLinkType.PARTICIPLE && to != null) 
            this._calcParticiple(noplural);
        else if (toVerb != null && toVerb.getFirstVerb() != null) {
            if (typ == NGLinkType.AGENT) 
                this._calcAgent(noplural);
            else if (typ == NGLinkType.PACIENT) 
                this._calcPacient(noplural);
            else if (typ == NGLinkType.ACTANT) 
                this._calcActant();
        }
        else if (typ == NGLinkType.ADVERB) 
            this._calcAdverb();
    }

    private void _calcGenetive() {
        if (!from.source.canBeNoun()) 
            return;
        if (from.source.typ == SentItemType.FORMULA) {
            if (to.source.typ != SentItemType.NOUN) 
                return;
            coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef;
            return;
        }
        com.pullenti.ner.MorphCollection frmorph = getFromMorph();
        if (to.source.typ == SentItemType.FORMULA) {
            if (from.source.typ != SentItemType.NOUN) 
                return;
            if (frmorph.getCase().isGenitive()) 
                coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef;
            else if (frmorph.getCase().isUndefined()) 
                coef = 0.0;
            return;
        }
        if (from.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) {
            if (from.order != (to.order + 1)) 
                return;
            com.pullenti.ner.measure.internal.NumbersWithUnitToken num = (com.pullenti.ner.measure.internal.NumbersWithUnitToken)com.pullenti.unisharp.Utils.cast(from.source.source, com.pullenti.ner.measure.internal.NumbersWithUnitToken.class);
            com.pullenti.ner.measure.MeasureKind ki = com.pullenti.ner.measure.internal.UnitToken.calcKind(num.units);
            if (ki != com.pullenti.ner.measure.MeasureKind.UNDEFINED) {
                if (com.pullenti.ner.measure.internal.UnitsHelper.checkKeyword(ki, to.source.source)) {
                    coef = com.pullenti.semantic.SemanticService.PARAMS.nextModel * (3.0);
                    return;
                }
            }
            if (to.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) 
                return;
        }
        java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(to.source.source, from.source.source, null);
        if (links != null) {
            for (com.pullenti.semantic.core.SemanticLink li : links) {
                if (li.role != com.pullenti.semantic.core.SemanticRole.AGENT) {
                    coef = li.rank;
                    if (coef < 0.5) 
                        coef = 0.5;
                    if (li.role == com.pullenti.semantic.core.SemanticRole.STRONG || li.idiom) 
                        coef *= (2.0);
                    return;
                }
            }
        }
        boolean nonGenText = false;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep()) && !(from.source.source instanceof com.pullenti.ner.core.VerbPhraseToken)) {
            if (from.order != (to.order + 1)) 
                nonGenText = true;
        }
        if (nonGenText || !com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep())) 
            return;
        com.pullenti.morph.MorphCase cas0 = frmorph.getCase();
        if (cas0.isGenitive() || cas0.isInstrumental() || cas0.isDative()) {
            if ((to.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) && cas0.isGenitive()) 
                coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef;
            else {
                coef = com.pullenti.semantic.SemanticService.PARAMS.ngLink;
                if (cas0.isNominative() || from.source.typ == SentItemType.PARTBEFORE) 
                    coef /= (2.0);
                if (!cas0.isGenitive()) 
                    coef /= (2.0);
            }
        }
        else if (from.source.source instanceof com.pullenti.ner.core.VerbPhraseToken) 
            coef = 0.1;
        if ((to.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) && to.source.getEndToken().isValue("ЧЕМ", null)) 
            coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef * (2.0);
    }

    private void _calcBe() {
        if (to.source.typ != SentItemType.NOUN || from.source.typ != SentItemType.NOUN) 
            return;
        com.pullenti.ner.MorphCollection fm = from.source.source.getMorph();
        com.pullenti.ner.MorphCollection tm = to.source.source.getMorph();
        if (!(tm.getCase().isNominative())) 
            return;
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep())) 
            return;
        if (from.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) {
            coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef;
            return;
        }
        if (!fm.getCase().isUndefined()) {
            if (!fm.getCase().isNominative()) 
                return;
        }
        coef = 0.0;
    }

    private void _calcName(boolean noplural) {
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep())) 
            return;
        if (!(from.source.source instanceof com.pullenti.ner.core.NounPhraseToken) || from.source.typ != SentItemType.NOUN) 
            return;
        if (from.source.getBeginToken().chars.isAllLower()) 
            return;
        if (!(to.source.source instanceof com.pullenti.ner.core.NounPhraseToken) || to.source.typ != SentItemType.NOUN) 
            return;
        if (from.order != (to.order + 1) && !noplural) 
            return;
        com.pullenti.ner.MorphCollection fm = from.source.source.getMorph();
        com.pullenti.ner.MorphCollection tm = to.source.source.getMorph();
        if (!fm.getCase().isUndefined() && !tm.getCase().isUndefined()) {
            if (((com.pullenti.morph.MorphCase.ooBitand(tm.getCase(), fm.getCase()))).isUndefined()) 
                return;
        }
        if (fm.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (noplural) {
                if (fromIsPlural) {
                }
                else if (((short)((tm.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return;
            }
            plural = 1;
            coef = com.pullenti.semantic.SemanticService.PARAMS.verbPlural;
        }
        else {
            if (fm.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                plural = 0;
            if (_checkMorphAccord(fm, false, tm)) 
                coef = com.pullenti.semantic.SemanticService.PARAMS.morphAccord;
        }
    }

    private void _calcAdverb() {
        if (toVerb != null) 
            coef = 1.0;
        else if (to == null) 
            return;
        else if (to.source.typ == SentItemType.ADVERB) 
            coef = 1.0;
        else 
            coef = 0.5;
    }

    private void _calcList() {
        com.pullenti.morph.MorphCase cas0 = getFromMorph().getCase();
        if (to == null) {
            if (toVerb == null) 
                return;
            return;
        }
        if (from.source.typ != to.source.typ) {
            if (com.pullenti.unisharp.Utils.stringsEq(from.source.prep, to.source.prep) && ((from.source.typ == SentItemType.NOUN || from.source.typ == SentItemType.PARTBEFORE || from.source.typ == SentItemType.PARTAFTER)) && ((to.source.typ == SentItemType.NOUN || to.source.typ == SentItemType.PARTBEFORE || to.source.typ == SentItemType.PARTAFTER))) {
            }
            else 
                return;
        }
        com.pullenti.morph.MorphCase cas1 = getToMorph().getCase();
        if (!((com.pullenti.morph.MorphCase.ooBitand(cas0, cas1))).isUndefined()) {
            coef = com.pullenti.semantic.SemanticService.PARAMS.list;
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep()) && !com.pullenti.unisharp.Utils.isNullOrEmpty(to.source.prep)) 
                coef /= (2.0);
            else if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep()) && com.pullenti.unisharp.Utils.isNullOrEmpty(to.source.prep)) 
                coef /= (4.0);
        }
        else {
            if (!cas0.isUndefined() && !cas1.isUndefined()) 
                return;
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep()) && com.pullenti.unisharp.Utils.isNullOrEmpty(to.source.prep)) 
                return;
            coef = com.pullenti.semantic.SemanticService.PARAMS.list;
        }
        com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(from.source.getEndToken(), com.pullenti.ner.TextToken.class);
        com.pullenti.ner.TextToken t2 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(to.source.getEndToken(), com.pullenti.ner.TextToken.class);
        if (t1 != null && t2 != null) {
            if (t1.isValue(t2.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false), null)) 
                coef *= (10.0);
        }
        if (from.source.typ != to.source.typ) 
            coef /= (2.0);
    }

    private double _calcParticiple(boolean noplural) {
        com.pullenti.ner.MorphCollection fm = from.source.source.getMorph();
        com.pullenti.ner.MorphCollection tm = to.source.source.getMorph();
        if (to.source.typ == SentItemType.PARTBEFORE) 
            return coef = -1.0;
        if (from.source.typ == SentItemType.DEEPART) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(to.source.prep)) 
                return coef = -1.0;
            if (tm.getCase().isNominative()) 
                return coef = com.pullenti.semantic.SemanticService.PARAMS.morphAccord;
            if (tm.getCase().isUndefined()) 
                return coef = 0.0;
            return coef = -1.0;
        }
        if (from.source.typ != SentItemType.PARTBEFORE && from.source.typ != SentItemType.SUBSENT) 
            return coef = -1.0;
        if (!fm.getCase().isUndefined() && !tm.getCase().isUndefined()) {
            if (((com.pullenti.morph.MorphCase.ooBitand(fm.getCase(), tm.getCase()))).isUndefined()) {
                if (from.source.typ == SentItemType.PARTBEFORE) 
                    return coef = -1.0;
            }
        }
        if (fm.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (noplural) {
                if (fromIsPlural) {
                }
                else if (((short)((tm.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return coef = -1.0;
            }
            plural = 1;
            coef = com.pullenti.semantic.SemanticService.PARAMS.verbPlural;
        }
        else {
            if (fm.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                plural = 0;
            if (fm.getItems().size() > 0) {
                for (com.pullenti.morph.MorphBaseInfo wf : fm.getItems()) {
                    if (_checkMorphAccord(tm, false, wf)) {
                        coef = com.pullenti.semantic.SemanticService.PARAMS.morphAccord;
                        if (tm.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                            if (((short)((tm.getGender().value()) & (wf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                coef /= (2.0);
                        }
                        break;
                    }
                }
            }
        }
        return coef;
    }

    private double _calcAgent(boolean noplural) {
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep())) 
            return coef = -1.0;
        com.pullenti.morph.MorphWordForm vf = toVerb.getFirstVerb().getVerbMorph();
        if (vf == null) 
            return coef = -1.0;
        com.pullenti.morph.MorphWordForm vf2 = toVerb.getLastVerb().getVerbMorph();
        if (vf2 == null) 
            return coef = -1.0;
        if (vf.misc.getMood() == com.pullenti.morph.MorphMood.IMPERATIVE) 
            return coef = -1.0;
        java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(toVerb, from.source.source, null);
        if (links != null) {
            for (com.pullenti.semantic.core.SemanticLink li : links) {
                if (li.role == com.pullenti.semantic.core.SemanticRole.AGENT) {
                    coef = li.rank;
                    if (coef < 0.5) 
                        coef = 0.5;
                    return coef;
                }
            }
        }
        com.pullenti.ner.MorphCollection _morph = getFromMorph();
        if (vf2.misc.getVoice() == com.pullenti.morph.MorphVoice.PASSIVE || toVerb.getLastVerb().getMorph().containsAttr("страд.з.", null)) {
            if (!_morph.getCase().isUndefined()) {
                if (_morph.getCase().isInstrumental()) {
                    coef = com.pullenti.semantic.SemanticService.PARAMS.transitiveCoef;
                    if (vf2.getCase().isInstrumental()) 
                        coef /= (2.0);
                    return coef;
                }
                return coef = -1.0;
            }
            return coef = 0.0;
        }
        if (vf.misc.getAttrs().contains("инф.")) 
            return coef = -1.0;
        if (_isRevVerb(vf2)) 
            return coef = -1.0;
        if (vf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (!_morph.getCase().isUndefined()) {
                if (vf.getCase().isUndefined()) {
                    if (!_morph.getCase().isNominative()) 
                        return coef = -1.0;
                }
                else if (((com.pullenti.morph.MorphCase.ooBitand(vf.getCase(), _morph.getCase()))).isUndefined()) 
                    return coef = -1.0;
            }
            if (noplural) {
                if (fromIsPlural) {
                }
                else if (((short)((_morph.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return coef = -1.0;
                else if (!_checkMorphAccord(_morph, false, vf)) 
                    return coef = -1.0;
                else if (_morph.getItems().size() > 0 && !vf.getCase().isUndefined()) {
                    boolean ok = false;
                    for (com.pullenti.morph.MorphBaseInfo it : _morph.getItems()) {
                        if (((short)((it.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) {
                            if (!it.getCase().isUndefined() && ((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), vf.getCase()))).isUndefined()) 
                                continue;
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) 
                        return coef = -1.0;
                }
            }
            plural = 1;
            coef = com.pullenti.semantic.SemanticService.PARAMS.verbPlural;
            if (com.pullenti.unisharp.Utils.stringsEq(vf2.normalCase, "БЫТЬ")) {
                if (_morph.getCase().isUndefined() && from.source.getBeginToken().getBeginChar() > toVerb.getEndChar()) 
                    coef /= (2.0);
            }
        }
        else {
            if (vf.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                plural = 0;
                if (fromIsPlural) 
                    return coef = -1.0;
            }
            if (!_checkMorphAccord(_morph, false, vf)) 
                return coef = -1.0;
            if (!_morph.getCase().isUndefined()) {
                if (!_morph.getCase().isNominative()) {
                    if (toVerb.getFirstVerb().isParticiple()) {
                    }
                    else 
                        return coef = -1.0;
                }
            }
            if (vf.misc.getPerson() != com.pullenti.morph.MorphPerson.UNDEFINED) {
                if (((short)((vf.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.THIRD.value()))) == (com.pullenti.morph.MorphPerson.UNDEFINED.value())) {
                    if (((short)((vf.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.FIRST.value()))) == (com.pullenti.morph.MorphPerson.FIRST.value())) {
                        if (!_morph.containsAttr("1 л.", null)) 
                            return coef = -1.0;
                    }
                    if (((short)((vf.misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.SECOND.value()))) == (com.pullenti.morph.MorphPerson.SECOND.value())) {
                        if (!_morph.containsAttr("2 л.", null)) 
                            return coef = -1.0;
                    }
                }
            }
            coef = com.pullenti.semantic.SemanticService.PARAMS.morphAccord;
            if (_morph.getCase().isUndefined()) 
                coef /= (4.0);
        }
        return coef;
    }

    private static boolean _isRevVerb(com.pullenti.morph.MorphWordForm vf) {
        if (vf.misc.getAttrs().contains("возвр.")) 
            return true;
        if (vf.normalCase != null) {
            if (vf.normalCase.endsWith("СЯ") || vf.normalCase.endsWith("СЬ")) 
                return true;
        }
        return false;
    }

    private double _calcPacient(boolean noplural) {
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(this.getFromPrep())) 
            return coef = -1.0;
        com.pullenti.morph.MorphWordForm vf = toVerb.getFirstVerb().getVerbMorph();
        if (vf == null) 
            return -1.0;
        com.pullenti.morph.MorphWordForm vf2 = toVerb.getLastVerb().getVerbMorph();
        if (vf2 == null) 
            return -1.0;
        java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(toVerb, from.source.source, null);
        if (links != null) {
            for (com.pullenti.semantic.core.SemanticLink li : links) {
                if (li.role == com.pullenti.semantic.core.SemanticRole.PACIENT) {
                    coef = li.rank;
                    if (coef < 0.5) 
                        coef = 0.5;
                    return coef;
                }
            }
        }
        com.pullenti.ner.MorphCollection _morph = getFromMorph();
        if (vf2.misc.getVoice() == com.pullenti.morph.MorphVoice.PASSIVE || toVerb.getLastVerb().getMorph().containsAttr("страд.з.", null)) {
            if (vf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                if (noplural) {
                    if (fromIsPlural) {
                    }
                    else if (!_checkMorphAccord(_morph, false, vf)) 
                        return -1.0;
                    else if (_morph.getItems().size() > 0 && !vf.getCase().isUndefined()) {
                        boolean ok = false;
                        for (com.pullenti.morph.MorphBaseInfo it : _morph.getItems()) {
                            if (((short)((it.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) {
                                if (!it.getCase().isUndefined() && ((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), vf.getCase()))).isUndefined()) 
                                    continue;
                                ok = true;
                                break;
                            }
                        }
                        if (!ok) 
                            return coef = -1.0;
                    }
                }
                coef = com.pullenti.semantic.SemanticService.PARAMS.verbPlural;
                plural = 1;
            }
            else {
                if (vf.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                    plural = 0;
                    if (fromIsPlural) 
                        return -1.0;
                }
                if (!_checkMorphAccord(_morph, false, vf)) 
                    return -1.0;
                coef = com.pullenti.semantic.SemanticService.PARAMS.morphAccord;
            }
            return coef;
        }
        return coef = -1.0;
    }

    private double _calcActant() {
        if (canBeParticiple) 
            return coef = -1.0;
        com.pullenti.morph.MorphWordForm vf2 = toVerb.getLastVerb().getVerbMorph();
        if (vf2 == null) 
            return -1.0;
        if (getFromPrep() == null) 
            return coef = 0.0;
        java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(toVerb, from.source.source, null);
        if (links != null) {
            for (com.pullenti.semantic.core.SemanticLink li : links) {
                if (li.role != com.pullenti.semantic.core.SemanticRole.AGENT && li.role != com.pullenti.semantic.core.SemanticRole.PACIENT) {
                    coef = li.rank;
                    if (coef < 0.5) 
                        coef = 0.5;
                    return coef;
                }
            }
        }
        return coef = 0.1;
    }

    private static boolean _checkMorphAccord(com.pullenti.ner.MorphCollection m, boolean _plural, com.pullenti.morph.MorphBaseInfo vf) {
        double _coef = 0.0;
        if (vf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (_plural) 
                _coef++;
            else if (m.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((m.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) 
                    _coef++;
                else 
                    return false;
            }
        }
        else if (vf.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
            if (_plural) 
                return false;
            if (m.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((m.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) == (com.pullenti.morph.MorphNumber.SINGULAR.value())) 
                    _coef++;
                else 
                    return false;
            }
            if (m.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (vf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (m.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                        if (((short)((vf.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            _coef++;
                        else 
                            return false;
                    }
                    else if (((short)((m.getGender().value()) & (vf.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        _coef++;
                    else if (m.getGender() == com.pullenti.morph.MorphGender.MASCULINE && vf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                    }
                    else 
                        return false;
                }
            }
        }
        return _coef >= 0;
    }

    public static NGLink _new3128(NGLinkType _arg1, NGItem _arg2, NGItem _arg3, boolean _arg4) {
        NGLink res = new NGLink();
        res.typ = _arg1;
        res.from = _arg2;
        res.to = _arg3;
        res.reverce = _arg4;
        return res;
    }

    public static NGLink _new3130(NGLinkType _arg1) {
        NGLink res = new NGLink();
        res.typ = _arg1;
        return res;
    }

    public static NGLink _new3131(NGItem _arg1, NGLinkType _arg2) {
        NGLink res = new NGLink();
        res.from = _arg1;
        res.typ = _arg2;
        return res;
    }

    public static NGLink _new3139(NGItem _arg1, com.pullenti.ner.core.VerbPhraseToken _arg2, NGLinkType _arg3) {
        NGLink res = new NGLink();
        res.from = _arg1;
        res.toVerb = _arg2;
        res.typ = _arg3;
        return res;
    }

    public static NGLink _new3155(NGLinkType _arg1, NGItem _arg2, com.pullenti.ner.core.VerbPhraseToken _arg3) {
        NGLink res = new NGLink();
        res.typ = _arg1;
        res.from = _arg2;
        res.toVerb = _arg3;
        return res;
    }
    public NGLink() {
    }
}
