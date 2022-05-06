/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org;

/**
 * Анализатор организаций
 */
public class OrganizationAnalyzer extends com.pullenti.ner.Analyzer {

    private static final int maxOrgName = 200;

    private static com.pullenti.ner.ReferentToken tryAttachOrg(com.pullenti.ner.Token t, AttachType attachTyp, com.pullenti.ner._org.internal.OrgItemTypeToken multTyp, boolean isAdditionalAttach, int step) {
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad.level > 4) 
            return null;
        ad.level++;
        com.pullenti.ner.ReferentToken res = _TryAttachOrgInt(t, attachTyp, multTyp, isAdditionalAttach, step);
        ad.level--;
        return res;
    }

    private static com.pullenti.ner.ReferentToken _TryAttachOrgInt(com.pullenti.ner.Token t, AttachType attachTyp, com.pullenti.ner._org.internal.OrgItemTypeToken multTyp, boolean isAdditionalAttach, int step) {
        if (t == null) 
            return null;
        if (t.chars.isLatinLetter() && com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
            com.pullenti.ner.ReferentToken re = tryAttachOrg(t.getNext(), attachTyp, multTyp, isAdditionalAttach, step);
            if (re != null) {
                re.setBeginToken(t);
                return re;
            }
        }
        OrganizationReferent __org = null;
        java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken> types = null;
        if (multTyp != null) {
            types = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken>();
            types.add(multTyp);
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otExLi = null;
        com.pullenti.ner._org.internal.OrgItemTypeToken typ = null;
        boolean hiph = false;
        boolean specWordBefore = false;
        boolean ok;
        boolean inBrackets = false;
        com.pullenti.ner.ReferentToken rt0 = null;
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
        for (; t != null; t = t.getNext()) {
            if (t.getReferent() instanceof OrganizationReferent) 
                break;
            rt0 = attachGlobalOrg(t, attachTyp, null);
            if ((rt0 == null && typ != null && typ.geo != null) && typ.getBeginToken().getNext() == typ.getEndToken()) {
                rt0 = attachGlobalOrg(typ.getEndToken(), attachTyp, typ.geo);
                if (rt0 != null) 
                    rt0.setBeginToken(typ.getBeginToken());
            }
            if (rt0 != null) {
                if (attachTyp == AttachType.MULTIPLE) {
                    if (types == null || types.size() == 0) 
                        return null;
                    if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypeAccords((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class), types.get(0))) 
                        return null;
                    ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addType(types.get(0), false);
                    if ((rt0.getBeginToken().getBeginChar() - types.get(0).getEndToken().getNext().getEndChar()) < 3) 
                        rt0.setBeginToken(types.get(0).getBeginToken());
                    break;
                }
                if (typ != null && !typ.getEndToken().getMorph()._getClass().isVerb()) {
                    if (_isMvdOrg((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)) != null && typ.typ != null && (typ.typ.indexOf("служба") >= 0)) {
                        rt0 = null;
                        break;
                    }
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypeAccords((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class), typ)) {
                        rt0.setBeginToken(typ.getBeginToken());
                        ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addType(typ, false);
                    }
                }
                break;
            }
            if (t.isHiphen()) {
                if (t == t0 || types == null) {
                    if (otExLi != null) 
                        break;
                    return null;
                }
                if (t.isWhitespaceBefore() && t.isWhitespaceAfter()) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false) != null) 
                        break;
                }
                if ((typ != null && typ.root != null && typ.root.canHasNumber) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                }
                else 
                    hiph = true;
                continue;
            }
            if (ad != null && otExLi == null) {
                boolean ok1 = false;
                com.pullenti.ner.Token tt = t;
                if (t.getInnerBool()) 
                    ok1 = true;
                else if (t.chars.isAllLower()) {
                }
                else if (t.chars.isLetter()) 
                    ok1 = true;
                else if (t.getPrevious() != null && com.pullenti.ner.core.BracketHelper.isBracket(t.getPrevious(), false)) 
                    ok1 = true;
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) && t.getNext() != null) {
                    ok1 = true;
                    tt = t.getNext();
                }
                if (ok1 && tt != null) {
                    otExLi = ad.locOrgs.tryAttach(tt, null, false);
                    if (otExLi == null && t.kit.ontology != null) {
                        if ((((otExLi = t.kit.ontology.attachToken(OrganizationReferent.OBJ_TYPENAME, tt)))) != null) {
                        }
                    }
                    if ((otExLi == null && tt.getLengthChar() == 2 && tt.chars.isAllUpper()) && (ad.localOntology.getItems().size() < 1000)) {
                        otExLi = ad.localOntology.tryAttach(tt, null, false);
                        if (otExLi != null) {
                            if (tt.kit.getSofa().getText().length() > 300) 
                                otExLi = null;
                        }
                    }
                }
                if (otExLi != null) 
                    t.setInnerBool(true);
            }
            if ((step >= 0 && !t.getInnerBool() && t == t0) && (t instanceof com.pullenti.ner.TextToken)) 
                typ = null;
            else {
                typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, attachTyp == AttachType.EXTONTOLOGY);
                if (typ == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), attachTyp == AttachType.EXTONTOLOGY);
                        if (typ != null && typ.getEndToken() == br.getEndToken().getPrevious() && ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(br.getEndToken().getNext(), true, false) || t.isChar('(')))) {
                            typ = typ.clone();
                            typ.setEndToken(br.getEndToken());
                            typ.setBeginToken(t);
                        }
                        else 
                            typ = null;
                    }
                }
            }
            if (typ == null) 
                break;
            if (types == null) {
                if ((((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "главное управление") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "главное территориальное управление") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "головне управління")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "головне територіальне управління") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "пограничное управление"))) && otExLi != null) 
                    break;
                types = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken>();
                t0 = typ.getBeginToken();
                if (typ.isNotTyp && typ.getEndToken().getNext() != null) 
                    t0 = typ.getEndToken().getNext();
                if (com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(typ.getBeginToken().getPrevious())) 
                    specWordBefore = true;
            }
            else {
                ok = true;
                for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) 
                    break;
                if (typ.isDep()) 
                    break;
                if (inBrackets) 
                    break;
                com.pullenti.ner._org.internal.OrgItemTypeToken typ0 = _lastTyp(types);
                if (hiph && ((t.getWhitespacesBeforeCount() > 0 && ((typ0 != null && typ0.isDoubtRootWord()))))) 
                    break;
                if (typ.getEndToken() == typ.getBeginToken()) {
                    if (typ.isValue("ОРГАНИЗАЦИЯ", "ОРГАНІЗАЦІЯ") || typ.isValue("УПРАВЛІННЯ", "")) 
                        break;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(typ0.typ, "банк") && typ.root != null && typ.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) {
                    com.pullenti.ner.ReferentToken rt = tryAttachOrg(typ.getBeginToken(), attachTyp, null, false, -1);
                    if (rt != null && (rt.referent.toString().indexOf("Сбербанк") >= 0)) 
                        return null;
                }
                if (typ0.isDep() || com.pullenti.unisharp.Utils.stringsEq(typ0.typ, "департамент")) 
                    break;
                if ((typ0.root != null && typ0.root.isPurePrefix && typ.root != null) && !typ.root.isPurePrefix && !typ.getBeginToken().chars.isAllLower()) {
                    if ((typ0.typ.indexOf("НИИ") >= 0)) 
                        break;
                }
                boolean pref0 = typ0.root != null && typ0.root.isPurePrefix;
                boolean pref = typ.root != null && typ.root.isPurePrefix;
                if (!pref0 && !pref) {
                    if (typ0.name != null && typ0.name.length() != typ0.typ.length()) {
                        if (t.getWhitespacesBeforeCount() > 1) 
                            break;
                    }
                    if (!typ0.getMorph().getCase().isUndefined() && !typ.getMorph().getCase().isUndefined()) {
                        if (!((com.pullenti.morph.MorphCase.ooBitand(typ0.getMorph().getCase(), typ.getMorph().getCase()))).isNominative() && !hiph) {
                            if (!typ.getMorph().getCase().isNominative()) 
                                break;
                        }
                    }
                    if (typ0.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && typ.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                        if (((short)((typ0.getMorph().getNumber().value()) & (typ.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                            break;
                    }
                }
                if (!pref0 && pref && !hiph) {
                    boolean nom = false;
                    for (com.pullenti.morph.MorphBaseInfo m : typ.getMorph().getItems()) {
                        if (m.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && m.getCase().isNominative()) {
                            nom = true;
                            break;
                        }
                    }
                    if (!nom) {
                        if (com.pullenti.morph.LanguageHelper.endsWith(typ0.typ, "фракция") || com.pullenti.morph.LanguageHelper.endsWith(typ0.typ, "фракція") || com.pullenti.unisharp.Utils.stringsEq(typ0.typ, "банк")) {
                        }
                        else 
                            break;
                    }
                }
                for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) 
                        return null;
                }
            }
            types.add(typ);
            inBrackets = false;
            if (typ.name != null) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(typ.getBeginToken().getPrevious(), true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(typ.getEndToken().getNext(), false, null, false)) {
                    typ = typ.clone();
                    typ.setBeginToken(typ.getBeginToken().getPrevious());
                    typ.setEndToken(typ.getEndToken().getNext());
                    if (typ.getBeginToken().getEndChar() < t0.getBeginChar()) 
                        t0 = typ.getBeginToken();
                    inBrackets = true;
                }
            }
            t = typ.getEndToken();
            hiph = false;
        }
        if ((types == null && otExLi == null && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) && rt0 == null) {
            ok = false;
            if (!ok) {
                if (t0 != null && t0.getMorph()._getClass().isAdjective() && t0.getNext() != null) {
                    if ((((rt0 = tryAttachOrg(t0.getNext(), attachTyp, multTyp, isAdditionalAttach, step)))) != null) {
                        if (rt0.getBeginToken() == t0) 
                            return rt0;
                    }
                }
                if (attachTyp == AttachType.NORMAL) {
                    if ((((rt0 = tryAttachOrgMed(t)))) != null) 
                        return rt0;
                }
                if ((((t0 instanceof com.pullenti.ner.TextToken) && t0.getPrevious() != null && t0.getLengthChar() > 2) && !t0.chars.isAllLower() && !t0.isNewlineAfter()) && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t0)) {
                    typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t0.getNext(), false);
                    if (typ != null) {
                        com.pullenti.ner.ReferentToken rrr = tryAttachOrg(t0.getNext(), attachTyp, multTyp, isAdditionalAttach, step);
                        if (rrr == null) {
                            if (specWordBefore || t0.getPrevious().isValue("ТЕРРИТОРИЯ", null)) {
                                OrganizationReferent org0 = new OrganizationReferent();
                                org0.addType(typ, false);
                                org0.addName(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0, com.pullenti.ner.TextToken.class)).term, false, t0);
                                t1 = typ.getEndToken();
                                t1 = (com.pullenti.ner.Token)com.pullenti.unisharp.Utils.notnull(attachTailAttributes(org0, t1.getNext(), false, AttachType.NORMAL, false), t1);
                                return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                            }
                        }
                    }
                }
                for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                    if (tt.isAnd()) {
                        if (tt == t) 
                            break;
                        continue;
                    }
                    if ((((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !tt.chars.isAllLower()) && !tt.chars.isCapitalUpper() && tt.getLengthChar() > 1) && (tt.getWhitespacesAfterCount() < 2)) {
                        String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "СНВ")) 
                            break;
                        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                        if (mc.isUndefined()) {
                        }
                        else if (((tt.getLengthChar() < 5) && !mc.isConjunction() && !mc.isPreposition()) && !mc.isNoun()) {
                        }
                        else if ((tt.getLengthChar() <= 3 && (tt.getPrevious() instanceof com.pullenti.ner.TextToken) && tt.getPrevious().chars.isLetter()) && !tt.getPrevious().chars.isAllUpper()) {
                        }
                        else 
                            break;
                    }
                    else 
                        break;
                    if ((tt.getNext() instanceof com.pullenti.ner.ReferentToken) && (tt.getNext().getReferent() instanceof OrganizationReferent)) {
                        com.pullenti.ner.Token ttt = t.getPrevious();
                        if ((((ttt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !ttt.chars.isAllLower()) && !ttt.chars.isCapitalUpper() && ttt.getLengthChar() > 1) && ttt.getMorphClassInDictionary().isUndefined() && (ttt.getWhitespacesAfterCount() < 2)) 
                            break;
                        com.pullenti.ner.Token tt0 = t;
                        for (t = t.getPrevious(); t != null; t = t.getPrevious()) {
                            if (!(t instanceof com.pullenti.ner.TextToken) || t.getWhitespacesAfterCount() > 2) 
                                break;
                            else if (t.isAnd()) {
                            }
                            else if ((t.chars.isLetter() && !t.chars.isAllLower() && !t.chars.isCapitalUpper()) && t.getLengthChar() > 1 && t.getMorphClassInDictionary().isUndefined()) 
                                tt0 = t;
                            else 
                                break;
                        }
                        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (com.pullenti.unisharp.Utils.stringsEq(nam, "СЭД") || com.pullenti.unisharp.Utils.stringsEq(nam, "ЕОСЗ")) 
                            break;
                        OrganizationReferent own = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getNext().getReferent(), OrganizationReferent.class);
                        if (own.getProfiles().contains(OrgProfile.UNIT)) 
                            break;
                        if ((own.toString().toUpperCase().indexOf("СОЮЗ") >= 0)) 
                            break;
                        if (com.pullenti.unisharp.Utils.stringsEq(nam, "НК") || com.pullenti.unisharp.Utils.stringsEq(nam, "ГК")) 
                            return new com.pullenti.ner.ReferentToken(own, t, tt.getNext(), null);
                        OrganizationReferent org0 = new OrganizationReferent();
                        org0.addProfile(OrgProfile.UNIT);
                        org0.addName(nam, true, null);
                        if (nam.indexOf(' ') > 0) 
                            org0.addName(nam.replace(" ", ""), true, null);
                        org0.setHigher(own);
                        t1 = tt.getNext();
                        com.pullenti.ner.Token ttt1 = attachTailAttributes(org0, t1, true, attachTyp, false);
                        if (tt0.kit.ontology != null) {
                            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = tt0.kit.ontology.attachToken(OrganizationReferent.OBJ_TYPENAME, tt0);
                            if (li != null) {
                                for (com.pullenti.ner.core.IntOntologyToken v : li) {
                                }
                            }
                        }
                        return new com.pullenti.ner.ReferentToken(org0, tt0, (ttt1 != null ? ttt1 : t1), null);
                    }
                }
                if (((t instanceof com.pullenti.ner.TextToken) && t.isNewlineBefore() && t.getLengthChar() > 1) && !t.chars.isAllLower() && t.getMorphClassInDictionary().isUndefined()) {
                    t1 = t.getNext();
                    if (t1 != null && !t1.isNewlineBefore() && (t1 instanceof com.pullenti.ner.TextToken)) 
                        t1 = t1.getNext();
                    if (t1 != null && t1.isNewlineBefore()) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ0 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, false);
                        if ((typ0 != null && typ0.root != null && typ0.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) && typ0.isNewlineAfter()) {
                            if (tryAttachOrg(t1, AttachType.NORMAL, null, false, -1) == null) {
                                __org = new OrganizationReferent();
                                __org.addType(typ0, false);
                                __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, t1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO), true, null);
                                t1 = typ0.getEndToken();
                                com.pullenti.ner.Token ttt1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                                return new com.pullenti.ner.ReferentToken(__org, t, (ttt1 != null ? ttt1 : t1), null);
                            }
                        }
                        if (t1.isChar('(')) {
                            if ((((typ0 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false)))) != null) {
                                if (typ0.getEndToken().getNext() != null && typ0.getEndToken().getNext().isChar(')') && typ0.getEndToken().getNext().isNewlineAfter()) {
                                    __org = new OrganizationReferent();
                                    __org.addType(typ0, false);
                                    __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, t1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO), true, null);
                                    t1 = typ0.getEndToken().getNext();
                                    com.pullenti.ner.Token ttt1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                                    return new com.pullenti.ner.ReferentToken(__org, t, (ttt1 != null ? ttt1 : t1), null);
                                }
                            }
                        }
                    }
                }
                if ((t instanceof com.pullenti.ner.TextToken) && t.isNewlineBefore() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && br.isNewlineAfter() && (br.getLengthChar() < 100)) {
                        t1 = br.getEndToken().getNext();
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ0 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, false);
                        if ((typ0 != null && typ0.root != null && typ0.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) && typ0.isNewlineAfter()) {
                            if (tryAttachOrg(t1, AttachType.NORMAL, null, false, -1) == null) {
                                __org = new OrganizationReferent();
                                __org.addType(typ0, false);
                                __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, t1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO), true, null);
                                t1 = typ0.getEndToken();
                                com.pullenti.ner.Token ttt1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                                return new com.pullenti.ner.ReferentToken(__org, t, (ttt1 != null ? ttt1 : t1), null);
                            }
                        }
                        if (t1 != null && t1.isChar('(')) {
                            if ((((typ0 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false)))) != null) {
                                if (typ0.getEndToken().getNext() != null && typ0.getEndToken().getNext().isChar(')') && typ0.getEndToken().getNext().isNewlineAfter()) {
                                    __org = new OrganizationReferent();
                                    __org.addType(typ0, false);
                                    __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, t1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO), true, null);
                                    t1 = typ0.getEndToken().getNext();
                                    com.pullenti.ner.Token ttt1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                                    return new com.pullenti.ner.ReferentToken(__org, t, (ttt1 != null ? ttt1 : t1), null);
                                }
                            }
                        }
                    }
                }
                return null;
            }
        }
        if (types != null && types.size() > 1 && attachTyp != AttachType.EXTONTOLOGY) {
            if (com.pullenti.unisharp.Utils.stringsEq(types.get(0).typ, "предприятие") || com.pullenti.unisharp.Utils.stringsEq(types.get(0).typ, "підприємство")) {
                types.remove(0);
                t0 = types.get(0).getBeginToken();
            }
        }
        if (rt0 == null) {
            rt0 = _TryAttachOrg_(t0, t, types, specWordBefore, attachTyp, multTyp, isAdditionalAttach);
            if (rt0 != null && otExLi != null) {
                for (com.pullenti.ner.core.IntOntologyToken ot : otExLi) {
                    if ((ot.getEndChar() > rt0.getEndChar() && ot.item != null && ot.item.owner != null) && ot.item.owner.isExtOntology) {
                        rt0 = null;
                        break;
                    }
                    else if (ot.getEndChar() < rt0.getBeginChar()) {
                        otExLi = null;
                        break;
                    }
                    else if (ot.getEndChar() < rt0.getEndChar()) {
                        if (ot.getEndToken().getNext().getMorphClassInDictionary().isPreposition()) {
                            rt0 = null;
                            break;
                        }
                    }
                }
            }
            if (rt0 != null) {
                if (types != null && rt0.getBeginToken() == types.get(0).getBeginToken()) {
                    for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                        ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addType(ty, true);
                    }
                }
                if ((rt0.getBeginToken() == t0 && t0.getPrevious() != null && t0.getPrevious().getMorph()._getClass().isAdjective()) && (t0.getWhitespacesBeforeCount() < 2)) {
                    if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).getGeoObjects().size() == 0) {
                        Object _geo = isGeo(t0.getPrevious(), true);
                        if (_geo != null) {
                            if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addGeoObject(_geo)) 
                                rt0.setBeginToken(t0.getPrevious());
                        }
                    }
                }
            }
        }
        if (otExLi != null && rt0 == null && (otExLi.size() < 10)) {
            for (com.pullenti.ner.core.IntOntologyToken ot : otExLi) {
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ot.item.referent, OrganizationReferent.class);
                if (org0 == null) 
                    continue;
                if (org0.getNames().size() == 0 && org0.getEponyms().size() == 0) 
                    continue;
                com.pullenti.ner._org.internal.OrgItemTypeToken tyty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(ot.getBeginToken(), true);
                if (tyty != null && tyty.getBeginToken() == ot.getEndToken()) 
                    continue;
                com.pullenti.ner.Token ts = ot.getBeginToken();
                com.pullenti.ner.Token te = ot.getEndToken();
                boolean isQuots = false;
                boolean isVeryDoubt = false;
                boolean nameEq = false;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ts.getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.isBracket(ts.getPrevious(), false)) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(te.getNext(), false, null, false)) {
                        if (ot.getLengthChar() < 2) 
                            continue;
                        if (ot.getLengthChar() == 2 && !org0.getNames().contains(te.getSourceText())) {
                        }
                        else {
                            isQuots = true;
                            ts = ts.getPrevious();
                            te = te.getNext();
                        }
                    }
                    else 
                        continue;
                }
                ok = types != null;
                if (ot.getEndToken().getNext() != null && (ot.getEndToken().getNext().getReferent() instanceof OrganizationReferent)) 
                    ok = true;
                else if (ot.getEndToken() != ot.getBeginToken()) {
                    if (step == 0) {
                        if (!ot.kit.miscData.containsKey("o2step")) 
                            ot.kit.miscData.put("o2step", null);
                        continue;
                    }
                    if (!ot.getBeginToken().chars.isAllLower()) 
                        ok = true;
                    else if (specWordBefore || isQuots) 
                        ok = true;
                }
                else if (ot.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                    if (step == 0) {
                        if (!t.kit.miscData.containsKey("o2step")) 
                            t.kit.miscData.put("o2step", null);
                        continue;
                    }
                    ok = false;
                    int len = ot.getBeginToken().getLengthChar();
                    if (!ot.chars.isAllLower()) {
                        if (!ot.chars.isAllUpper() && ot.getMorph()._getClass().isPreposition()) 
                            continue;
                        for (String n : org0.getNames()) {
                            if (ot.getBeginToken().isValue(n, null)) {
                                nameEq = true;
                                break;
                            }
                        }
                        com.pullenti.ner.TextAnnotation ano = org0.findNearOccurence(ot.getBeginToken());
                        if (ano == null) {
                            if (!ot.item.owner.isExtOntology) {
                                if (len < 3) 
                                    continue;
                                else 
                                    isVeryDoubt = true;
                            }
                        }
                        else {
                            if (len == 2 && !t.chars.isAllUpper()) 
                                continue;
                            int d = ano.beginChar - ot.getBeginToken().getBeginChar();
                            if (d < 0) 
                                d = -d;
                            if (d > 2000) {
                                if (len < 3) 
                                    continue;
                                else if (len < 5) 
                                    isVeryDoubt = true;
                            }
                            else if (d > 300) {
                                if (len < 3) 
                                    continue;
                            }
                            else if (len < 3) {
                                if (d > 100 || !ot.getBeginToken().chars.isAllUpper()) 
                                    isVeryDoubt = true;
                            }
                        }
                        if (((ot.getBeginToken().chars.isAllUpper() || ot.getBeginToken().chars.isLastLower())) && ((len > 3 || ((len == 3 && ((nameEq || ano != null))))))) 
                            ok = true;
                        else if ((specWordBefore || types != null || isQuots) || nameEq) 
                            ok = true;
                        else if ((ot.getLengthChar() < 3) && isVeryDoubt) 
                            continue;
                        else if (ot.item.owner.isExtOntology && ot.getBeginToken().getMorphClassInDictionary().isUndefined() && ((len > 3 || ((len == 3 && ((nameEq || ano != null))))))) 
                            ok = true;
                        else if (ot.getBeginToken().chars.isLatinLetter()) 
                            ok = true;
                        else if ((nameEq && !ot.chars.isAllLower() && !ot.item.owner.isExtOntology) && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(ot.getBeginToken())) 
                            ok = true;
                    }
                }
                else if (ot.getBeginToken() instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = ot.getBeginToken().getReferent();
                    if (com.pullenti.unisharp.Utils.stringsNe(r.getTypeName(), "DENOMINATION") && !isQuots) 
                        ok = false;
                }
                if (!ok) {
                }
                if (ok) {
                    ok = false;
                    __org = new OrganizationReferent();
                    if (types != null) {
                        for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                            __org.addType(ty, false);
                        }
                        if (!__org.canBeEquals(org0, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) 
                            continue;
                    }
                    else 
                        for (String ty : org0.getTypes()) {
                            __org.addTypeStr(ty);
                        }
                    if (org0.getNumber() != null && (ot.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && __org.getNumber() == null) {
                        if (com.pullenti.unisharp.Utils.stringsNe(org0.getNumber(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ot.getBeginToken().getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString()) && (ot.getBeginToken().getWhitespacesBeforeCount() < 2)) {
                            if (__org.getNames().size() > 0 || __org.getHigher() != null) {
                                isVeryDoubt = false;
                                ok = true;
                                __org.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ot.getBeginToken().getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                                if (org0.getHigher() != null) 
                                    __org.setHigher(org0.getHigher());
                                t0 = ot.getBeginToken().getPrevious();
                            }
                        }
                    }
                    if (__org.getNumber() == null) {
                        com.pullenti.ner.Token ttt = ot.getEndToken().getNext();
                        com.pullenti.ner._org.internal.OrgItemNumberToken nnn = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(ttt, (org0.getNumber() != null || !ot.isWhitespaceAfter()), null);
                        if (nnn == null && !ot.isWhitespaceAfter() && ttt != null) {
                            if (ttt.isHiphen() && ttt.getNext() != null) 
                                ttt = ttt.getNext();
                            if (ttt instanceof com.pullenti.ner.NumberToken) 
                                nnn = com.pullenti.ner._org.internal.OrgItemNumberToken._new1990(ot.getEndToken().getNext(), ttt, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        }
                        if (nnn != null) {
                            __org.setNumber(nnn.number);
                            te = nnn.getEndToken();
                        }
                    }
                    boolean norm = (ot.getEndToken().getEndChar() - ot.getBeginToken().getBeginChar()) > 5;
                    String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(ot, com.pullenti.ner.core.GetTextAttr.of((((norm ? com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE : com.pullenti.ner.core.GetTextAttr.NO)).value()) | (com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES.value())));
                    __org.addName(s, true, (norm ? null : ot.getBeginToken()));
                    if (types == null || types.size() == 0) {
                        String s1 = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(ot, com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES);
                        if (com.pullenti.unisharp.Utils.stringsNe(s1, s) && norm) 
                            __org.addName(s1, true, ot.getBeginToken());
                    }
                    t1 = te;
                    if (t1.isChar(')') && t1.isNewlineAfter()) {
                    }
                    else {
                        t1 = (com.pullenti.ner.Token)com.pullenti.unisharp.Utils.notnull(attachMiddleAttributes(__org, t1.getNext()), t1);
                        if (attachTyp != AttachType.NORMALAFTERDEP) 
                            t1 = (com.pullenti.ner.Token)com.pullenti.unisharp.Utils.notnull(attachTailAttributes(__org, t1.getNext(), false, AttachType.NORMAL, false), t1);
                    }
                    OrganizationReferent hi = null;
                    if (t1.getNext() != null) 
                        hi = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getReferent(), OrganizationReferent.class);
                    if (org0.getHigher() != null && hi != null && otExLi.size() == 1) {
                        if (hi.canBeEquals(org0.getHigher(), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                            __org.setHigher(hi);
                            t1 = t1.getNext();
                        }
                    }
                    if ((__org.getEponyms().size() == 0 && __org.getNumber() == null && isVeryDoubt) && !nameEq && types == null) 
                        continue;
                    if (!__org.canBeEqualsEx(org0, true, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                        if (t != null && com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t.getPrevious())) 
                            ok = true;
                        else if (!isVeryDoubt && ok) {
                        }
                        else {
                            if (!isVeryDoubt) {
                                if (__org.getEponyms().size() > 0 || __org.getNumber() != null || __org.getHigher() != null) 
                                    ok = true;
                            }
                            ok = false;
                        }
                    }
                    else if (__org.canBeEquals(org0, com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS)) {
                        __org.mergeSlots(org0, false);
                        ok = true;
                    }
                    else if (org0.getHigher() == null || __org.getHigher() != null || ot.item.owner.isExtOntology) {
                        ok = true;
                        __org.mergeSlots(org0, false);
                    }
                    else if (!ot.item.owner.isExtOntology && __org.canBeEquals(org0, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                        if (org0.getHigher() == null) 
                            __org.mergeSlots(org0, false);
                        ok = true;
                    }
                    if (!ok) 
                        continue;
                    if (ts.getBeginChar() < t0.getBeginChar()) 
                        t0 = ts;
                    rt0 = new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
                    if (__org.getKind() == OrganizationKind.DEPARTMENT) 
                        correctDepAttrs(rt0, typ, false);
                    _correctAfter(rt0);
                    if (ot.item.owner.isExtOntology) {
                        for (com.pullenti.ner.Slot sl : __org.getSlots()) {
                            if (sl.getValue() instanceof com.pullenti.ner.Referent) {
                                boolean ext = false;
                                for (com.pullenti.ner.Slot ss : org0.getSlots()) {
                                    if (ss.getValue() == sl.getValue()) {
                                        ext = true;
                                        break;
                                    }
                                }
                                if (!ext) 
                                    continue;
                                com.pullenti.ner.Referent rr = ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class)).clone();
                                rr.getOccurrence().clear();
                                __org.uploadSlot(sl, rr);
                                com.pullenti.ner.ReferentToken rtEx = new com.pullenti.ner.ReferentToken(rr, t0, t1, null);
                                rtEx.setDefaultLocalOnto(t0.kit.processor);
                                __org.addExtReferent(rtEx);
                                for (com.pullenti.ner.Slot sss : rr.getSlots()) {
                                    if (sss.getValue() instanceof com.pullenti.ner.Referent) {
                                        com.pullenti.ner.Referent rrr = ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sss.getValue(), com.pullenti.ner.Referent.class)).clone();
                                        rrr.getOccurrence().clear();
                                        rr.uploadSlot(sss, rrr);
                                        com.pullenti.ner.ReferentToken rtEx2 = new com.pullenti.ner.ReferentToken(rrr, t0, t1, null);
                                        rtEx2.setDefaultLocalOnto(t0.kit.processor);
                                        ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class)).addExtReferent(rtEx2);
                                    }
                                }
                            }
                        }
                    }
                    _correctAfter(rt0);
                    return rt0;
                }
            }
        }
        if ((rt0 == null && types != null && types.size() == 1) && types.get(0).name == null) {
            com.pullenti.ner.Token tt0 = null;
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(types.get(0).getBeginToken())) 
                tt0 = types.get(0).getBeginToken();
            else if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(types.get(0).getEndToken().getNext())) 
                tt0 = types.get(0).getBeginToken();
            else {
                com.pullenti.ner.Token tt00 = types.get(0).getBeginToken().getPrevious();
                if (tt00 != null && (tt00.getWhitespacesAfterCount() < 2) && tt00.chars.isLatinLetter() == types.get(0).chars.isLatinLetter()) {
                    if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt00)) 
                        tt0 = tt00;
                    else if (tt00.getMorph()._getClass().isPreposition() || tt00.getMorph()._getClass().isPronoun()) 
                        tt0 = tt00.getNext();
                }
            }
            int cou = 100;
            if (tt0 != null) {
                for (com.pullenti.ner.Token tt00 = tt0.getPrevious(); tt00 != null && cou > 0; tt00 = tt00.getPrevious(),cou--) {
                    if (tt00.getReferent() instanceof OrganizationReferent) {
                        if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypeAccords((OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt00.getReferent(), OrganizationReferent.class), types.get(0))) {
                            if ((types.get(0).getWhitespacesAfterCount() < 3) && com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(types.get(0).getEndToken().getNext(), true) != null) {
                            }
                            else 
                                rt0 = new com.pullenti.ner.ReferentToken(tt00.getReferent(), tt0, types.get(0).getEndToken(), null);
                        }
                        break;
                    }
                }
            }
        }
        if (rt0 != null) 
            correctOwnerBefore(rt0);
        if (hiph && !inBrackets && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) {
            boolean ok1 = false;
            if (rt0 != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt0.getEndToken(), true, null, false)) {
                if (types.size() > 0) {
                    com.pullenti.ner._org.internal.OrgItemTypeToken ty = types.get(types.size() - 1);
                    if (ty.getEndToken().getNext() != null && ty.getEndToken().getNext().isHiphen() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ty.getEndToken().getNext().getNext(), true, false)) 
                        ok1 = true;
                }
            }
            else if (rt0 != null && rt0.getEndToken().getNext() != null && rt0.getEndToken().getNext().isHiphen()) {
                com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(rt0.getEndToken().getNext().getNext(), false);
                if (ty == null) 
                    ok1 = true;
            }
            if (!ok1) 
                return null;
        }
        if (attachTyp == AttachType.MULTIPLE && t != null) {
            if (t.chars.isAllLower()) 
                return null;
        }
        if (rt0 == null) 
            return rt0;
        boolean doubt = rt0.tag != null;
        __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
        if (doubt && ad != null && (ad.localOntology.getItems().size() < 1000)) {
            java.util.ArrayList<com.pullenti.ner.Referent> rli = ad.localOntology.tryAttachByReferent(__org, null, true);
            if (rli != null && rli.size() > 0) 
                doubt = false;
            else 
                for (com.pullenti.ner.core.IntOntologyItem it : ad.localOntology.getItems()) {
                    if (it.referent != null) {
                        if (it.referent.canBeEquals(__org, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                            doubt = false;
                            break;
                        }
                    }
                }
        }
        if ((t != null && t.kit.ontology != null && attachTyp == AttachType.NORMAL) && doubt) {
            java.util.ArrayList<com.pullenti.ner.ExtOntologyItem> rli = t.kit.ontology.attachReferent(__org);
            if (rli != null) {
                if (rli.size() >= 1) 
                    doubt = false;
            }
        }
        if (doubt) 
            return null;
        _correctAfter(rt0);
        return rt0;
    }

    private static void _correctAfter(com.pullenti.ner.ReferentToken rt0) {
        if (rt0 == null) 
            return;
        if (!rt0.isNewlineAfter() && rt0.getEndToken().getNext() != null && rt0.getEndToken().getNext().isChar('(')) {
            com.pullenti.ner.Token tt = rt0.getEndToken().getNext().getNext();
            if (tt instanceof com.pullenti.ner.TextToken) {
                if (tt.isChar(')')) 
                    rt0.setEndToken(tt);
                else if ((tt.getLengthChar() > 2 && (tt.getLengthChar() < 7) && tt.chars.isLatinLetter()) && tt.chars.isAllUpper()) {
                    String act = tt.getSourceText().toUpperCase();
                    if ((tt.getNext() instanceof com.pullenti.ner.NumberToken) && !tt.isWhitespaceAfter() && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                        tt = tt.getNext();
                        act += tt.getSourceText();
                    }
                    if (tt.getNext() != null && tt.getNext().isChar(')')) {
                        rt0.referent.addSlot(OrganizationReferent.ATTR_MISC, act, false, 0);
                        rt0.setEndToken(tt.getNext());
                    }
                }
                else {
                    OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                    if (__org.getKind() == OrganizationKind.BANK && tt.chars.isLatinLetter()) {
                    }
                    com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(tt, AttachType.NORMAL, null, false, -1);
                    if (rt1 != null && rt1.getEndToken().getNext() != null && rt1.getEndToken().getNext().isChar(')')) {
                        if (__org.canBeEquals(rt1.referent, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) {
                            __org.mergeSlots(rt1.referent, true);
                            rt0.setEndToken(rt1.getEndToken().getNext());
                        }
                    }
                }
            }
        }
        if (rt0.isNewlineBefore() && rt0.isNewlineAfter() && rt0.getEndToken().getNext() != null) {
            com.pullenti.ner.Token t1 = rt0.getEndToken().getNext();
            com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, false);
            if ((typ1 != null && typ1.isNewlineAfter() && typ1.root != null) && typ1.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) {
                if (tryAttachOrg(t1, AttachType.NORMAL, null, false, -1) == null) {
                    ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addType(typ1, false);
                    rt0.setEndToken(typ1.getEndToken());
                }
            }
            if (t1.isChar('(')) {
                if ((((typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false)))) != null) {
                    if ((typ1.root != null && typ1.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX && typ1.getEndToken().getNext() != null) && typ1.getEndToken().getNext().isChar(')') && typ1.getEndToken().getNext().isNewlineAfter()) {
                        ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addType(typ1, false);
                        rt0.setEndToken(typ1.getEndToken().getNext());
                    }
                }
            }
        }
    }

    private static com.pullenti.ner._org.internal.OrgItemTypeToken _lastTyp(java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken> types) {
        if (types == null) 
            return null;
        for (int i = types.size() - 1; i >= 0; i--) {
            return types.get(i);
        }
        return null;
    }

    private static com.pullenti.ner.ReferentToken _TryAttachOrg_(com.pullenti.ner.Token t0, com.pullenti.ner.Token t, java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken> types, boolean specWordBefore, AttachType attachTyp, com.pullenti.ner._org.internal.OrgItemTypeToken multTyp, boolean isAdditionalAttach) {
        if (t0 == null) 
            return null;
        com.pullenti.ner.Token t1 = t;
        com.pullenti.ner._org.internal.OrgItemTypeToken typ = _lastTyp(types);
        if (typ != null) {
            if (typ.isDep()) {
                com.pullenti.ner.ReferentToken rt0 = tryAttachDep(typ, attachTyp, specWordBefore);
                if (rt0 != null) 
                    return rt0;
                if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "группа") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "група")) {
                    typ = typ.clone();
                    typ.setDep(false);
                }
                else 
                    return null;
            }
            if (typ.isNewlineAfter() && typ.name == null) {
                if (t1 != null && (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && typ.getProfiles().contains(OrgProfile.STATE)) {
                }
                else if (typ.root != null && ((typ.root.coeff >= 3 || typ.root.isPurePrefix))) {
                }
                else if (typ.getCoef() >= 4) {
                }
                else if ((typ.getCoef() >= 3 && (typ.getNewlinesAfterCount() < 2) && typ.getEndToken().getNext() != null) && typ.getEndToken().getNext().getMorph()._getClass().isPreposition()) {
                }
                else if (specWordBefore) {
                }
                else 
                    return null;
            }
            if (typ != multTyp && ((typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL && !Character.isUpperCase(typ.typ.charAt(0))))) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                }
                else if (typ.getEndToken().isValue("ВЛАСТЬ", null)) {
                }
                else 
                    return null;
            }
            if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                if (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "підприємство"))) && !specWordBefore && types.size() == 1) 
                    return null;
            }
        }
        OrganizationReferent __org = new OrganizationReferent();
        if (types != null) {
            for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                __org.addType(ty, false);
            }
        }
        if (typ != null && typ.root != null && typ.root.isPurePrefix) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && !t.isNewlineAfter()) {
                com.pullenti.ner.core.BracketSequenceToken b = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (b != null && b.isQuoteType()) {
                    __org.addTypeStr(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    t = t.getNext();
                }
                else {
                    String s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    if (s.length() == 2 && s.charAt(s.length() - 1) == 'К') {
                        __org.addTypeStr(s);
                        t = t.getNext();
                    }
                    else if (((t.getMorphClassInDictionary().isUndefined() && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isCapitalUpper() && t.getNext().getNext() != null) && !t.getNext().isNewlineAfter()) {
                        if (t.getNext().getNext().isCharOf(",.;") || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
                            __org.addTypeStr(s);
                            t = t.getNext();
                        }
                    }
                }
            }
            else if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isAdjective() && !t.chars.isAllLower()) {
                com.pullenti.ner.ReferentToken rtg = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(isGeo(t, true), com.pullenti.ner.ReferentToken.class);
                if (rtg != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rtg.getEndToken().getNext(), false, false)) {
                    __org.addGeoObject(rtg);
                    t = rtg.getEndToken().getNext();
                }
            }
            else if ((t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && t.getNext() != null) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                __org.addGeoObject(t.getReferent());
                t = t.getNext();
            }
        }
        com.pullenti.ner.Token te = null;
        OrganizationKind ki0 = __org.getKind();
        if (((((ki0 == OrganizationKind.GOVENMENT || ki0 == OrganizationKind.AIRPORT || ki0 == OrganizationKind.FACTORY) || ki0 == OrganizationKind.SEAPORT || ki0 == OrganizationKind.PARTY) || ki0 == OrganizationKind.JUSTICE || ki0 == OrganizationKind.MILITARY)) && t != null) {
            Object g = isGeo(t, false);
            if (g == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) 
                g = isGeo(t.getNext(), false);
            if (g != null) {
                if (__org.addGeoObject(g)) {
                    te = (t1 = getGeoEndToken(g, t));
                    t = t1.getNext();
                    java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, false);
                    if (gt == null && t != null && t.kit.baseLanguage.isUa()) 
                        gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, false);
                    if (gt != null && gt.size() == 1) {
                        if (__org.canBeEquals(gt.get(0).item.referent, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) {
                            __org.mergeSlots(gt.get(0).item.referent, false);
                            return new com.pullenti.ner.ReferentToken(__org, t0, gt.get(0).getEndToken(), null);
                        }
                    }
                }
            }
        }
        if (typ != null && typ.root != null && ((typ.root.canBeSingleGeo && !typ.root.canHasSingleName))) {
            if (__org.getGeoObjects().size() > 0 && te != null) 
                return new com.pullenti.ner.ReferentToken(__org, t0, te, null);
            Object r = null;
            te = (t1 = (typ != multTyp ? typ.getEndToken() : t0.getPrevious()));
            if (t != null && t1.getNext() != null) {
                r = isGeo(t1.getNext(), false);
                if (r == null && t1.getNext().getMorph()._getClass().isPreposition()) 
                    r = isGeo(t1.getNext().getNext(), false);
            }
            if (r != null) {
                if (!__org.addGeoObject(r)) 
                    return null;
                te = getGeoEndToken(r, t1.getNext());
            }
            if (__org.getGeoObjects().size() > 0 && te != null) {
                com.pullenti.ner.core.NounPhraseToken npt11 = com.pullenti.ner.core.NounPhraseHelper.tryParse(te.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt11 != null && (te.getWhitespacesAfterCount() < 2) && npt11.noun.isValue("ДЕПУТАТ", null)) {
                }
                else {
                    com.pullenti.ner.ReferentToken res11 = new com.pullenti.ner.ReferentToken(__org, t0, te, null);
                    if (__org.findSlot(OrganizationReferent.ATTR_TYPE, "посольство", true) != null) {
                        if (te.getNext() != null && te.getNext().isValue("В", null)) {
                            r = isGeo(te.getNext().getNext(), false);
                            if (__org.addGeoObject(r)) 
                                res11.setEndToken(getGeoEndToken(r, te.getNext().getNext()));
                        }
                    }
                    if (typ.root.canHasNumber) {
                        com.pullenti.ner._org.internal.OrgItemNumberToken num11 = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(res11.getEndToken().getNext(), false, null);
                        if (num11 != null) {
                            res11.setEndToken(num11.getEndToken());
                            __org.setNumber(num11.number);
                        }
                    }
                    return res11;
                }
            }
        }
        if (typ != null && (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "милиция") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "полиция") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "міліція")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "поліція")))) {
            if (__org.getGeoObjects().size() > 0 && te != null) 
                return new com.pullenti.ner.ReferentToken(__org, t0, te, null);
            else 
                return null;
        }
        if (t != null && t.getMorph()._getClass().isProperName()) {
            com.pullenti.ner.ReferentToken rt1 = t.kit.processReferent("PERSON", t, null);
            if (rt1 != null && (rt1.getWhitespacesAfterCount() < 2)) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt1.getEndToken().getNext(), true, false)) 
                    t = rt1.getEndToken().getNext();
                else if (rt1.getEndToken().getNext() != null && rt1.getEndToken().getNext().isHiphen() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt1.getEndToken().getNext().getNext(), true, false)) 
                    t = rt1.getEndToken().getNext().getNext();
            }
        }
        else if ((t != null && t.chars.isCapitalUpper() && t.getMorph()._getClass().isProperSurname()) && t.getNext() != null && (t.getWhitespacesAfterCount() < 2)) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) 
                t = t.getNext();
            else if (((t.getNext().isCharOf(":") || t.getNext().isHiphen())) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext().getNext(), true, false)) 
                t = t.getNext().getNext();
        }
        com.pullenti.ner.Token tMax = null;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        if (t != null) {
            br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (typ != null && br == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                    OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                    if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(__org, org0)) {
                        org0.mergeSlots(__org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext(), null);
                    }
                }
                if (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "компания") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "организация")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "компанія") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "підприємство")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "організація")) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1)) 
                        return null;
                }
                com.pullenti.ner._org.internal.OrgItemTypeToken ty2 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false);
                if (ty2 != null) {
                    java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken> typs2 = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemTypeToken>();
                    typs2.add(ty2);
                    com.pullenti.ner.ReferentToken rt2 = _TryAttachOrg_(t.getNext(), ty2.getEndToken().getNext(), typs2, true, AttachType.HIGH, null, isAdditionalAttach);
                    if (rt2 != null) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt2.referent, OrganizationReferent.class);
                        if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(__org, org0)) {
                            org0.mergeSlots(__org, false);
                            rt2.setBeginToken(t0);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt2.getEndToken().getNext(), false, null, false)) 
                                rt2.setEndToken(rt2.getEndToken().getNext());
                            return rt2;
                        }
                    }
                }
            }
        }
        if (br != null && typ != null && __org.getKind() == OrganizationKind.GOVENMENT) {
            if (typ.root != null && !typ.root.canHasSingleName) 
                br = null;
        }
        if (br != null && br.isQuoteType()) {
            if (br.getBeginToken().getNext().isValue("О", null) || br.getBeginToken().getNext().isValue("ОБ", null)) 
                br = null;
            else if (br.getBeginToken().getPrevious() != null && br.getBeginToken().getPrevious().isChar(':')) 
                br = null;
        }
        if (br != null && br.isQuoteType() && ((br.getOpenChar() != '<' || ((typ != null && typ.root != null && typ.root.isPurePrefix))))) {
            if (t.isNewlineBefore() && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) {
                if (!br.isNewlineAfter()) {
                    if (typ == null) 
                        return null;
                    if (typ.isNewlineBefore() || ((typ.getBeginToken().getPrevious() != null && typ.getBeginToken().getPrevious().isTableControlChar()))) {
                    }
                    else 
                        return null;
                }
            }
            if (__org.findSlot(OrganizationReferent.ATTR_TYPE, "организация", true) != null || __org.findSlot(OrganizationReferent.ATTR_TYPE, "організація", true) != null) {
                if (typ.getBeginToken() == typ.getEndToken()) {
                    if (!specWordBefore) 
                        return null;
                }
            }
            if (typ != null && ((((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "компания") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "организация")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "компанія") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "підприємство")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "організація")))) {
                if (com.pullenti.ner._org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1)) 
                    return null;
            }
            com.pullenti.ner._org.internal.OrgItemNameToken nn = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, false, true);
            if (nn != null && nn.isIgnoredPart) 
                t = nn.getEndToken();
            OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
            if (org0 != null) {
                if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(__org, org0) && t.getNext().getNext() != null) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
                        org0.mergeSlots(__org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext().getNext(), null);
                    }
                    if ((t.getNext().getNext().getReferent() instanceof OrganizationReferent) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext().getNext(), false, null, false)) {
                        org0.mergeSlots(__org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext(), null);
                    }
                }
                return null;
            }
            com.pullenti.ner._org.internal.OrgItemNameToken na0 = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, false, true);
            if (na0 != null && na0.isEmptyWord && na0.getEndToken().getNext() == br.getEndToken()) 
                return null;
            com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t.getNext(), attachTyp, null, isAdditionalAttach, -1);
            if (br.internal.size() > 1) {
                if (rt0 != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt0.getEndToken(), false, null, false)) 
                    br.setEndToken(rt0.getEndToken());
                else 
                    return null;
            }
            String abbr = null;
            com.pullenti.ner.Token tt00 = (rt0 == null ? null : rt0.getBeginToken());
            if (((rt0 == null && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isAllUpper() && t.getNext().getLengthChar() > 2) && t.getNext().chars.isCyrillicLetter()) {
                rt0 = tryAttachOrg(t.getNext().getNext(), attachTyp, null, isAdditionalAttach, -1);
                if (rt0 != null && rt0.getBeginToken() == t.getNext().getNext()) {
                    tt00 = t.getNext();
                    abbr = t.getNext().getSourceText();
                }
                else 
                    rt0 = null;
            }
            boolean ok2 = false;
            if (rt0 != null) {
                if (rt0.getEndToken() == br.getEndToken().getPrevious() || rt0.getEndToken() == br.getEndToken()) 
                    ok2 = true;
                else if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt0.getEndToken(), false, null, false) && rt0.getEndChar() > br.getEndChar()) {
                    com.pullenti.ner.core.BracketSequenceToken br2 = com.pullenti.ner.core.BracketHelper.tryParse(br.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br2 != null && rt0.getEndToken() == br2.getEndToken()) 
                        ok2 = true;
                }
            }
            if (ok2 && (rt0.referent instanceof OrganizationReferent)) {
                org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                if (typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба") && ((org0.getKind() == OrganizationKind.MEDIA || org0.getKind() == OrganizationKind.PRESS))) {
                    if (br.getBeginToken() == rt0.getBeginToken() && br.getEndToken() == rt0.getEndToken()) 
                        return rt0;
                }
                com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = null;
                if (tt00 != t.getNext()) {
                    typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false);
                    if (typ1 != null && typ1.getEndToken().getNext() == tt00) 
                        __org.addType(typ1, false);
                }
                boolean hi = false;
                if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org0, __org, true)) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(org0, __org)) 
                        hi = true;
                }
                if (hi) {
                    __org.setHigher(org0);
                    rt0.setDefaultLocalOnto(t.kit.processor);
                    __org.addExtReferent(rt0);
                    if (typ1 != null) 
                        __org.addType(typ1, true);
                    if (abbr != null) 
                        __org.addName(abbr, true, null);
                }
                else if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(org0, __org)) {
                    __org.mergeSlots(org0, true);
                    if (abbr != null) {
                        for (com.pullenti.ner.Slot s : __org.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NAME)) 
                                __org.uploadSlot(s, (abbr + " " + s.getValue()));
                        }
                    }
                }
                else 
                    rt0 = null;
                if (rt0 != null) {
                    com.pullenti.ner.Token t11 = br.getEndToken();
                    if (rt0.getEndChar() > t11.getEndChar()) 
                        t11 = rt0.getEndToken();
                    com.pullenti.ner._org.internal.OrgItemEponymToken ep11 = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t11.getNext(), true);
                    if (ep11 != null) {
                        t11 = ep11.getEndToken();
                        for (String e : ep11.eponyms) {
                            __org.addEponym(e);
                        }
                    }
                    t1 = attachTailAttributes(__org, t11.getNext(), true, attachTyp, false);
                    if (t1 == null) 
                        t1 = t11;
                    if (typ != null) {
                        if ((typ.name != null && typ.geo == null && __org.getNames().size() > 0) && !__org.getNames().contains(typ.name)) 
                            __org.addTypeStr(typ.name.toLowerCase());
                    }
                    return new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
                }
            }
            if (rt0 != null && (rt0.getEndChar() < br.getEndToken().getPrevious().getEndChar())) {
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(rt0.getEndToken().getNext(), attachTyp, null, isAdditionalAttach, -1);
                if (rt1 != null && rt1.getEndToken().getNext() == br.getEndToken()) 
                    return rt1;
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (org1 != null && br.getEndToken().getPrevious() == rt0.getEndToken()) {
                }
            }
            for (int step = 0; step < 2; step++) {
                com.pullenti.ner.Token tt0 = t.getNext();
                com.pullenti.ner.Token tt1 = null;
                boolean pref = true;
                int notEmpty = 0;
                for (t1 = t.getNext(); t1 != null && t1 != br.getEndToken(); t1 = t1.getNext()) {
                    if (t1.isChar('(')) {
                        if (notEmpty == 0) 
                            break;
                        com.pullenti.ner.Referent r = null;
                        if (t1.getNext() != null) 
                            r = t1.getNext().getReferent();
                        if (r != null && t1.getNext().getNext() != null && t1.getNext().getNext().isChar(')')) {
                            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                                __org.addGeoObject(r);
                                break;
                            }
                        }
                        com.pullenti.ner.ReferentToken rt = tryAttachOrg(t1.getNext(), AttachType.HIGH, null, false, -1);
                        if (rt != null && rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar(')')) {
                            if (!OrganizationReferent.canBeSecondDefinition(__org, (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class))) 
                                break;
                            __org.mergeSlots(rt.referent, false);
                        }
                        break;
                    }
                    else if ((((org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), OrganizationReferent.class)))) != null) {
                        if (((t1.getPrevious() instanceof com.pullenti.ner.NumberToken) && t1.getPrevious().getPrevious() == br.getBeginToken() && !com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(__org, org0)) && org0.getNumber() == null) {
                            org0.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                            org0.mergeSlots(__org, false);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
                                t1 = t1.getNext();
                            return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                        }
                        com.pullenti.ner._org.internal.OrgItemNameToken ne = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, attachTyp == AttachType.EXTONTOLOGY, true);
                        if (ne != null && ne.isIgnoredPart && ne.getEndToken().getNext() == t1) {
                            org0.mergeSlots(__org, false);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
                                t1 = t1.getNext();
                            return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                        }
                        return null;
                    }
                    else {
                        typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, false);
                        if (typ != null && types != null) {
                            for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                                if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) {
                                    typ = null;
                                    break;
                                }
                            }
                        }
                        if (typ != null) {
                            if (typ.isDoubtRootWord() && ((typ.getEndToken().getNext() == br.getEndToken() || ((typ.getEndToken().getNext() != null && typ.getEndToken().getNext().isHiphen()))))) 
                                typ = null;
                            else if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                                typ = null;
                            else if (!typ.getMorph().getCase().isUndefined() && !typ.getMorph().getCase().isNominative()) 
                                typ = null;
                            else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "управление")) 
                                typ = null;
                            else if (typ.getBeginToken() == typ.getEndToken()) {
                                com.pullenti.ner.Token ttt = typ.getEndToken().getNext();
                                if (ttt != null && ttt.isHiphen()) 
                                    ttt = ttt.getNext();
                                if (ttt != null) {
                                    if (ttt.isValue("БАНК", null)) 
                                        typ = null;
                                }
                            }
                        }
                        com.pullenti.ner._org.internal.OrgItemEponymToken ep = null;
                        if (typ == null) 
                            ep = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t1, false);
                        com.pullenti.ner._org.internal.OrgItemNumberToken nu = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1, false, null);
                        if (nu != null && !(t1 instanceof com.pullenti.ner.NumberToken)) {
                            __org.setNumber(nu.number);
                            tt1 = t1.getPrevious();
                            t1 = nu.getEndToken();
                            notEmpty += 2;
                            continue;
                        }
                        boolean brSpec = false;
                        if ((br.internal.size() == 0 && (br.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && ((!br.getEndToken().getNext().chars.isAllLower() && br.getEndToken().getNext().chars.isLetter()))) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(br.getEndToken().getNext().getNext(), true, null, false)) 
                            brSpec = true;
                        if (typ != null && ((pref || !typ.isDep()))) {
                            if (notEmpty > 1) {
                                com.pullenti.ner.ReferentToken rrr = tryAttachOrg(typ.getBeginToken(), AttachType.NORMAL, null, false, -1);
                                if (rrr != null) {
                                    br.setEndToken((t1 = typ.getBeginToken().getPrevious()));
                                    break;
                                }
                            }
                            if (((attachTyp == AttachType.EXTONTOLOGY || attachTyp == AttachType.HIGH)) && ((typ.root == null || !typ.root.isPurePrefix))) 
                                pref = false;
                            else if (typ.name == null) {
                                __org.addType(typ, false);
                                if (pref) 
                                    tt0 = typ.getEndToken().getNext();
                                else if (typ.root != null && typ.root.isPurePrefix) {
                                    tt1 = typ.getBeginToken().getPrevious();
                                    break;
                                }
                            }
                            else if (typ.getEndToken().getNext() != br.getEndToken()) {
                                __org.addType(typ, false);
                                if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "банк")) 
                                    pref = false;
                                else {
                                    __org.addTypeStr(typ.name.toLowerCase());
                                    __org.addTypeStr(typ.altTyp);
                                    if (pref) 
                                        tt0 = typ.getEndToken().getNext();
                                }
                            }
                            else if (brSpec) {
                                __org.addType(typ, false);
                                __org.addTypeStr(typ.name.toLowerCase());
                                notEmpty += 2;
                                tt0 = br.getEndToken().getNext();
                                t1 = tt0.getNext();
                                br.setEndToken(t1);
                                break;
                            }
                            if (typ != multTyp) {
                                t1 = typ.getEndToken();
                                if (typ.geo != null) 
                                    __org.addType(typ, false);
                            }
                        }
                        else if (ep != null) {
                            for (String e : ep.eponyms) {
                                __org.addEponym(e);
                            }
                            notEmpty += 3;
                            t1 = ep.getBeginToken().getPrevious();
                            break;
                        }
                        else if (t1 == t.getNext() && (t1 instanceof com.pullenti.ner.TextToken) && t1.chars.isAllLower()) 
                            return null;
                        else if (t1.chars.isLetter() || (t1 instanceof com.pullenti.ner.NumberToken)) {
                            if (brSpec) {
                                tt0 = br.getBeginToken();
                                t1 = br.getEndToken().getNext().getNext();
                                String ss = com.pullenti.ner.core.MiscHelper.getTextValue(br.getEndToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
                                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(ss)) {
                                    __org.addName(ss, true, br.getEndToken().getNext());
                                    br.setEndToken(t1);
                                }
                                break;
                            }
                            pref = false;
                            notEmpty++;
                        }
                    }
                }
                boolean canHasNum = false;
                boolean canHasLatinName = false;
                if (types != null) {
                    for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                        if (ty.root != null) {
                            if (ty.root.canHasNumber) 
                                canHasNum = true;
                            if (ty.root.canHasLatinName) 
                                canHasLatinName = true;
                        }
                    }
                }
                te = (tt1 != null ? tt1 : t1);
                if (te != null && tt0 != null && (tt0.getBeginChar() < te.getBeginChar())) {
                    for (com.pullenti.ner.Token ttt = tt0; ttt != te && ttt != null; ttt = ttt.getNext()) {
                        com.pullenti.ner._org.internal.OrgItemNameToken oin = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(ttt, null, attachTyp == AttachType.EXTONTOLOGY, ttt == tt0);
                        if (oin != null) {
                            if (oin.isIgnoredPart && ttt == tt0) {
                                tt0 = oin.getEndToken().getNext();
                                if (tt0 == null) 
                                    break;
                                ttt = tt0.getPrevious();
                                continue;
                            }
                            if (oin.isStdTail) {
                                com.pullenti.ner._org.internal.OrgItemEngItem ei = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(oin.getBeginToken(), false);
                                if (ei == null && oin.getBeginToken().isComma()) 
                                    ei = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(oin.getBeginToken().getNext(), false);
                                if (ei != null) {
                                    __org.addTypeStr(ei.fullValue);
                                    if (ei.shortValue != null) 
                                        __org.addTypeStr(ei.shortValue);
                                }
                                te = ttt.getPrevious();
                                break;
                            }
                        }
                        if ((ttt != tt0 && (ttt instanceof com.pullenti.ner.ReferentToken) && ttt.getNext() == te) && (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            if (ttt.getPrevious() != null && ttt.getPrevious().getMorphClassInDictionary().isAdjective()) 
                                continue;
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                            if (npt != null && npt.getEndToken() == ttt) {
                            }
                            else {
                                te = ttt.getPrevious();
                                if (te.getMorph()._getClass().isPreposition() && te.getPrevious() != null) 
                                    te = te.getPrevious();
                            }
                            __org.addGeoObject(ttt.getReferent());
                            break;
                        }
                    }
                }
                if (te != null && tt0 != null && (tt0.getBeginChar() < te.getBeginChar())) {
                    if ((te.getPrevious() instanceof com.pullenti.ner.NumberToken) && canHasNum) {
                        boolean err = false;
                        com.pullenti.ner.NumberToken num1 = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(te.getPrevious(), com.pullenti.ner.NumberToken.class);
                        if (__org.getNumber() != null && com.pullenti.unisharp.Utils.stringsNe(__org.getNumber(), num1.getValue().toString())) 
                            err = true;
                        else if (te.getPrevious().getPrevious() == null) 
                            err = true;
                        else if (!te.getPrevious().getPrevious().isHiphen() && !te.getPrevious().getPrevious().chars.isLetter()) 
                            err = true;
                        else if (com.pullenti.unisharp.Utils.stringsEq(num1.getValue(), "0")) 
                            err = true;
                        if (!err) {
                            __org.setNumber(num1.getValue().toString());
                            te = te.getPrevious().getPrevious();
                            if (te != null && ((te.isHiphen() || te.isValue("N", null) || te.isValue("№", null)))) 
                                te = te.getPrevious();
                        }
                    }
                }
                String s = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValue(tt0, te, com.pullenti.ner.core.GetTextAttr.NO));
                String s1 = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValue(tt0, te, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
                if ((te != null && (te.getPrevious() instanceof com.pullenti.ner.NumberToken) && canHasNum) && __org.getNumber() == null) {
                    __org.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(te.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                    com.pullenti.ner.Token tt11 = te.getPrevious();
                    if (tt11.getPrevious() != null && tt11.getPrevious().isHiphen()) 
                        tt11 = tt11.getPrevious();
                    if (tt11.getPrevious() != null) {
                        s = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt11.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                        s1 = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt11.getPrevious(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    }
                }
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (tt0.getMorph()._getClass().isPreposition() && tt0 != br.getBeginToken().getNext()) {
                        for (String ty : __org.getTypes()) {
                            if (!(ty.indexOf(" ") >= 0) && Character.isLowerCase(ty.charAt(0))) {
                                s = (ty.toUpperCase() + " " + s);
                                s1 = null;
                                break;
                            }
                        }
                    }
                    if (s.length() > maxOrgName) 
                        return null;
                    if (s1 != null && com.pullenti.unisharp.Utils.stringsNe(s1, s) && s1.length() <= s.length()) 
                        __org.addName(s1, true, null);
                    __org.addName(s, true, tt0);
                    typ = _lastTyp(types);
                    if (typ != null && typ.root != null && typ.root.getCanonicText().startsWith("ИНДИВИДУАЛЬН")) {
                        com.pullenti.ner.ReferentToken pers = typ.kit.processReferent("PERSON", tt0, null);
                        if (pers != null && pers.getEndToken().getNext() == te) {
                            __org.addExtReferent(pers);
                            __org.addSlot(OrganizationReferent.ATTR_OWNER, pers.referent, false, 0);
                        }
                    }
                    boolean ok1 = false;
                    for (char c : s.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            ok1 = true;
                            break;
                        }
                    }
                    if (!ok1) 
                        return null;
                    if (br.getBeginToken().getNext().chars.isAllLower()) 
                        return null;
                    if (__org.getTypes().size() == 0) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken ty = _lastTyp(types);
                        if (ty != null && ty.getCoef() >= 4) {
                        }
                        else {
                            if (attachTyp == AttachType.NORMAL) 
                                return null;
                            if (__org.getNames().size() == 1 && (__org.getNames().get(0).length() < 2) && (br.getLengthChar() < 5)) 
                                return null;
                        }
                    }
                }
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br1 == null) 
                        break;
                    t = br1.getBeginToken();
                    br = br1;
                    continue;
                }
                else if (((__org.getNumber() != null || __org.getEponyms().size() > 0)) && t1 == br.getEndToken()) {
                }
                else if (__org.getGeoObjects().size() > 0 && __org.getTypes().size() > 2) {
                }
                else 
                    return null;
                t1 = br.getEndToken();
                if (__org.getNumber() == null && t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner._org.internal.OrgItemNumberToken num1 = (com.pullenti.ner._org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1) ? null : com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, typ));
                    if (num1 != null) {
                        __org.setNumber(num1.number);
                        t1 = num1.getEndToken();
                    }
                    else 
                        t1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                }
                else 
                    t1 = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                if (t1 == null) 
                    t1 = br.getEndToken();
                boolean ok0 = false;
                if (types != null) {
                    for (com.pullenti.ner._org.internal.OrgItemTypeToken ty : types) {
                        if (ty.name != null) 
                            __org.addTypeStr(ty.name.toLowerCase());
                        if (attachTyp != AttachType.MULTIPLE && (ty.getBeginChar() < t0.getBeginChar()) && !ty.isNotTyp) 
                            t0 = ty.getBeginToken();
                        if (!ty.isDoubtRootWord() || ty.getCoef() > 0 || ty.geo != null) 
                            ok0 = true;
                        else if (com.pullenti.unisharp.Utils.stringsEq(ty.typ, "движение") && ((!br.getBeginToken().getNext().chars.isAllLower() || !ty.chars.isAllLower()))) {
                            if (!br.getBeginToken().getNext().getMorph().getCase().isGenitive()) 
                                ok0 = true;
                        }
                        else if (com.pullenti.unisharp.Utils.stringsEq(ty.typ, "АО")) {
                            if (ty.getBeginToken().chars.isAllUpper() && (ty.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.isBracket(ty.getEndToken().getNext(), true)) 
                                ok0 = true;
                            else 
                                for (com.pullenti.ner.Token tt2 = t1.getNext(); tt2 != null; tt2 = tt2.getNext()) {
                                    if (tt2.isComma()) 
                                        continue;
                                    if (tt2.isValue("ИМЕНОВАТЬ", null)) 
                                        ok0 = true;
                                    if (tt2.isValue("В", null) && tt2.getNext() != null) {
                                        if (tt2.getNext().isValue("ЛИЦО", null) || tt2.getNext().isValue("ДАЛЬШЕЙШЕМ", null) || tt2.getNext().isValue("ДАЛЕЕ", null)) 
                                            ok0 = true;
                                    }
                                    break;
                                }
                        }
                    }
                }
                if (__org.getEponyms().size() == 0 && (t1.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner._org.internal.OrgItemEponymToken ep = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
                    if (ep != null) {
                        for (String e : ep.eponyms) {
                            __org.addEponym(e);
                        }
                        ok0 = true;
                        t1 = ep.getEndToken();
                    }
                }
                if (__org.getNames().size() == 0) {
                    s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    s1 = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
                    __org.addName(s, true, br.getBeginToken().getNext());
                    __org.addName(s1, true, null);
                }
                if (!ok0) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())) 
                        ok0 = true;
                }
                if (!ok0 && attachTyp != AttachType.NORMAL) 
                    ok0 = true;
                typ = _lastTyp(types);
                if (typ != null && typ.getBeginToken() != typ.getEndToken()) 
                    ok0 = true;
                if (ok0) 
                    return new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
                else 
                    return com.pullenti.ner.ReferentToken._new788(__org, t0, t1, __org);
            }
        }
        com.pullenti.ner._org.internal.OrgItemNumberToken num = null;
        com.pullenti.ner._org.internal.OrgItemNumberToken _num;
        com.pullenti.ner._org.internal.OrgItemEponymToken epon = null;
        com.pullenti.ner._org.internal.OrgItemEponymToken _epon;
        java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemNameToken> names = null;
        com.pullenti.ner._org.internal.OrgItemNameToken pr = null;
        com.pullenti.ner.ReferentToken ownOrg = null;
        if (t1 == null) 
            t1 = t0;
        else if (t != null && t.getPrevious() != null && t.getPrevious().getBeginChar() >= t0.getBeginChar()) 
            t1 = t.getPrevious();
        br = null;
        boolean ok = false;
        for (; t != null; t = t.getNext()) {
            if (t.getReferent() instanceof OrganizationReferent) {
            }
            com.pullenti.ner.ReferentToken rt;
            if ((((rt = attachGlobalOrg(t, attachTyp, null)))) != null) {
                if (t == t0) {
                    if (!t.chars.isAllLower()) 
                        return rt;
                    return null;
                }
                rt = tryAttachOrg(t, attachTyp, multTyp, isAdditionalAttach, -1);
                if (rt != null) 
                    return rt;
            }
            if ((((_num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t, typ != null && typ.root != null && typ.root.canHasNumber, typ)))) != null) {
                if ((typ == null || typ.root == null || !typ.root.canHasNumber) || num != null) 
                    break;
                if (t.getWhitespacesBeforeCount() > 2) {
                    if (typ.getEndToken().getNext() == t && com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t) != null) {
                    }
                    else 
                        break;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(typ.root.getCanonicText(), "СУД") && typ.name != null) {
                    if ((((typ.name.startsWith("ВЕРХОВНЫЙ") || typ.name.startsWith("АРБИТРАЖНЫЙ") || typ.name.startsWith("ВЫСШИЙ")) || typ.name.startsWith("КОНСТИТУЦИОН") || typ.name.startsWith("ВЕРХОВНИЙ")) || typ.name.startsWith("АРБІТРАЖНИЙ") || typ.name.startsWith("ВИЩИЙ")) || typ.name.startsWith("КОНСТИТУЦІЙН")) {
                        typ.setCoef(3.0F);
                        break;
                    }
                }
                num = _num;
                t1 = (t = num.getEndToken());
                continue;
            }
            if ((((_epon = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t, false)))) != null) {
                epon = _epon;
                t1 = (t = epon.getEndToken());
                continue;
            }
            if ((((typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, false)))) != null) {
                if (typ.getMorph().getCase().isGenitive()) {
                    if (typ.getEndToken().isValue("СЛУЖБА", null) || typ.getEndToken().isValue("УПРАВЛЕНИЕ", "УПРАВЛІННЯ") || typ.getEndToken().isValue("ХОЗЯЙСТВО", null)) 
                        typ = null;
                }
                if (typ != null) {
                    if (!typ.isDoubtRootWord() && attachTyp != AttachType.EXTONTOLOGY) 
                        break;
                    if (types == null && t0 == t) 
                        break;
                    if (_lastTyp(types) != null && attachTyp != AttachType.EXTONTOLOGY) {
                        if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticTT(typ, _lastTyp(types))) {
                            if (names != null && ((typ.getMorph().getCase().isGenitive() || typ.getMorph().getCase().isInstrumental())) && (t.getWhitespacesBeforeCount() < 2)) {
                            }
                            else 
                                break;
                        }
                    }
                }
            }
            if ((((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100)))) != null) {
                if (ownOrg != null && !((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class)).isFromGlobalOntos) 
                    break;
                if (t.isNewlineBefore() && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) 
                    break;
                typ = _lastTyp(types);
                if ((__org.findSlot(OrganizationReferent.ATTR_TYPE, "организация", true) != null || __org.findSlot(OrganizationReferent.ATTR_TYPE, "движение", true) != null || __org.findSlot(OrganizationReferent.ATTR_TYPE, "організація", true) != null) || __org.findSlot(OrganizationReferent.ATTR_TYPE, "рух", true) != null) {
                    if (((typ == null || (typ.getCoef() < 2))) && !specWordBefore) 
                        return null;
                }
                if (br.isQuoteType()) {
                    if (br.getOpenChar() == '<' || br.getWhitespacesBeforeCount() > 1) 
                        break;
                    rt = tryAttachOrg(t, AttachType.HIGH, null, false, -1);
                    if (rt == null) 
                        break;
                    OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (names != null && names.size() == 1) {
                        if (((!names.get(0).isNounPhrase && names.get(0).chars.isAllUpper())) || org0.getNames().size() > 0) {
                            if (!names.get(0).getBeginToken().getMorph()._getClass().isPreposition()) {
                                if (org0.getNames().size() == 0) 
                                    __org.addTypeStr(names.get(0).value);
                                else {
                                    for (String n : org0.getNames()) {
                                        __org.addName((names.get(0).value + " " + n), true, null);
                                        if (typ != null && typ.root != null && typ.root.getTyp() != com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) 
                                            __org.addName((typ.typ.toUpperCase() + " " + com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(names.get(0), com.pullenti.ner.core.GetTextAttr.NO) + " " + n), true, null);
                                    }
                                    if (typ != null) 
                                        typ.setCoef(4.0F);
                                }
                                names = null;
                            }
                        }
                    }
                    if (names != null && names.size() > 0 && !specWordBefore) 
                        break;
                    if (!__org.canBeEquals(org0, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) 
                        break;
                    __org.mergeSlots(org0, true);
                    t1 = (tMax = (t = rt.getEndToken()));
                    ok = true;
                    continue;
                }
                else if (br.getOpenChar() == '(') {
                    if (t.getNext().getReferent() != null && t.getNext().getNext() == br.getEndToken()) {
                        com.pullenti.ner.Referent r = t.getNext().getReferent();
                        if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                            __org.addGeoObject(r);
                            tMax = (t1 = (t = br.getEndToken()));
                            continue;
                        }
                    }
                    else if (((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLetter() && !t.getNext().chars.isAllLower()) && t.getNext().getNext() == br.getEndToken()) {
                        typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true);
                        if (typ != null) {
                            OrganizationReferent or0 = new OrganizationReferent();
                            or0.addType(typ, false);
                            if (or0.getKind() != OrganizationKind.UNDEFINED && __org.getKind() != OrganizationKind.UNDEFINED) {
                                if (__org.getKind() != or0.getKind()) 
                                    break;
                            }
                            if (com.pullenti.ner.core.MiscHelper.testAcronym(t.getNext(), t0, t.getPrevious())) 
                                __org.addName(t.getNext().getSourceText(), true, null);
                            else 
                                __org.addType(typ, false);
                            t1 = (t = (tMax = br.getEndToken()));
                            continue;
                        }
                        else {
                            com.pullenti.ner._org.internal.OrgItemNameToken nam = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, attachTyp == AttachType.EXTONTOLOGY, true);
                            if (nam != null && nam.isEmptyWord) 
                                break;
                            if (attachTyp == AttachType.NORMAL) {
                                OrganizationReferent org0 = new OrganizationReferent();
                                org0.addName(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term, true, t.getNext());
                                if (!OrganizationReferent.canBeSecondDefinition(__org, org0)) 
                                    break;
                            }
                            __org.addName(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term, true, t.getNext());
                            tMax = (t1 = (t = br.getEndToken()));
                            continue;
                        }
                    }
                }
                break;
            }
            if (ownOrg != null) {
                if (names == null && t.isValue("ПО", null)) {
                }
                else if (names != null && t.isCommaAnd()) {
                }
                else 
                    break;
            }
            typ = _lastTyp(types);
            if (typ != null && typ.root != null && typ.root.isPurePrefix) {
                if (pr == null && names == null) {
                    pr = new com.pullenti.ner._org.internal.OrgItemNameToken(t, t);
                    pr.getMorph().setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                }
            }
            com.pullenti.ner._org.internal.OrgItemNameToken na = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, names == null);
            if (na == null && t != null) {
                if (__org.getKind() == OrganizationKind.CHURCH || ((typ != null && typ.typ != null && (typ.typ.indexOf("фермер") >= 0)))) {
                    com.pullenti.ner.ReferentToken prt = t.kit.processReferent("PERSON", t, null);
                    if (prt != null) {
                        na = com.pullenti.ner._org.internal.OrgItemNameToken._new2545(t, prt.getEndToken(), true);
                        na.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(na, com.pullenti.ner.core.GetTextAttr.NO);
                        na.chars = com.pullenti.morph.CharsInfo._new2546(true);
                        na.setMorph(prt.getMorph());
                        String sur = prt.referent.getStringValue("LASTNAME");
                        if (sur != null) {
                            for (com.pullenti.ner.Token tt = t; tt != null && tt.getEndChar() <= prt.getEndChar(); tt = tt.getNext()) {
                                if (tt.isValue(sur, null)) {
                                    na.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt, com.pullenti.ner.core.GetTextAttr.NO);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (na == null) {
                if (attachTyp == AttachType.EXTONTOLOGY) {
                    if (t.isChar(',') || t.isAnd()) 
                        continue;
                }
                if (t.getReferent() instanceof OrganizationReferent) {
                    ownOrg = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    continue;
                }
                if (t.isValue("ПРИ", null) && (t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                    t = t.getNext();
                    ownOrg = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    continue;
                }
                if ((((names == null && t.isChar('/') && (t.getNext() instanceof com.pullenti.ner.TextToken)) && !t.isWhitespaceAfter() && t.getNext().chars.isAllUpper()) && t.getNext().getLengthChar() >= 3 && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext().isChar('/')) 
                    na = com.pullenti.ner._org.internal.OrgItemNameToken._new2547(t, t.getNext().getNext(), t.getNext().getSourceText().toUpperCase(), t.getNext().chars);
                else if (names == null && typ != null && ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "движение") || __org.getKind() == OrganizationKind.PARTY))) {
                    com.pullenti.ner.Token tt1 = null;
                    if (t.isValue("ЗА", null) || t.isValue("ПРОТИВ", null)) 
                        tt1 = t.getNext();
                    else if (t.isValue("В", null) && t.getNext() != null) {
                        if (t.getNext().isValue("ЗАЩИТА", null) || t.getNext().isValue("ПОДДЕРЖКА", null)) 
                            tt1 = t.getNext();
                    }
                    else if (typ.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(typ.getBeginToken())) {
                        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                        if ((mc.isAdverb() || mc.isPronoun() || mc.isPersonalPronoun()) || mc.isVerb() || mc.isConjunction()) {
                        }
                        else if (t.chars.isLetter()) 
                            tt1 = t;
                        else if (typ.getBeginToken() != typ.getEndToken()) 
                            typ.setCoef(typ.getCoef() + (3.0F));
                    }
                    if (tt1 != null) {
                        na = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(tt1, pr, true, false);
                        if (na != null) {
                            na.setBeginToken(t);
                            typ.setCoef(typ.getCoef() + (3.0F));
                        }
                    }
                }
                if (na == null) 
                    break;
            }
            if (num != null || epon != null) 
                break;
            if (attachTyp == AttachType.MULTIPLE || attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                if (!na.isStdTail && !na.chars.isLatinLetter() && na.stdOrgNameNouns == 0) {
                    if (t.getMorph()._getClass().isProperName() && !t.chars.isAllUpper()) 
                        break;
                    com.pullenti.morph.MorphClass cla = t.getMorphClassInDictionary();
                    if (cla.isProperSurname() || ((t.getMorph().getLanguage().isUa() && t.getMorph()._getClass().isProperSurname()))) {
                        if (names == null && ((__org.getKind() == OrganizationKind.AIRPORT || __org.getKind() == OrganizationKind.SEAPORT))) {
                        }
                        else if (typ != null && typ.root != null && com.pullenti.unisharp.Utils.stringsEq(typ.root.acronym, "ФОП")) {
                        }
                        else if (typ != null && (typ.typ.indexOf("фермер") >= 0)) {
                        }
                        else 
                            break;
                    }
                    if (cla.isUndefined() && na.chars.isCyrillicLetter() && na.chars.isCapitalUpper()) {
                        if ((t.getPrevious() != null && !t.getPrevious().getMorph()._getClass().isPreposition() && !t.getPrevious().getMorph()._getClass().isConjunction()) && t.getPrevious().chars.isAllLower()) {
                            if ((t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLetter()) && !t.getNext().chars.isAllLower()) 
                                break;
                        }
                    }
                    if (typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "союз") && !t.getMorph().getCase().isGenitive()) 
                        break;
                    com.pullenti.ner.ReferentToken pit = t.kit.processReferent("PERSONPROPERTY", t, null);
                    if (pit != null) {
                        if (pit.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && pit.getBeginToken() != pit.getEndToken()) {
                            if (typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба") && pit.getMorph().getCase().isGenitive()) {
                            }
                            else 
                                break;
                        }
                    }
                    pit = t.kit.processReferent("DECREE", t, null);
                    if (pit != null) {
                        com.pullenti.ner.core.NounPhraseToken nptt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (nptt != null && nptt.getEndToken().isValue("РЕШЕНИЕ", null)) {
                        }
                        else 
                            break;
                    }
                    pit = t.kit.processReferent("NAMEDENTITY", t, null);
                    if (pit != null && pit.getEndToken() != t) 
                        break;
                    if (t.isValue("АО", null)) 
                        break;
                    if (t.getNewlinesBeforeCount() > 1) 
                        break;
                }
            }
            if (t.isValue("ИМЕНИ", "ІМЕНІ") || t.isValue("ИМ", "ІМ")) 
                break;
            pr = na;
            if (attachTyp == AttachType.EXTONTOLOGY) {
                if (names == null) 
                    names = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemNameToken>();
                names.add(na);
                t1 = (t = na.getEndToken());
                continue;
            }
            if (names == null) {
                if (tMax != null) 
                    break;
                if (t.getPrevious() != null && t.isNewlineBefore() && attachTyp != AttachType.EXTONTOLOGY) {
                    if (typ != null && typ.getEndToken().getNext() == t && typ.isNewlineBefore()) {
                    }
                    else {
                        if (t.getNewlinesAfterCount() > 1 || !t.chars.isAllLower()) 
                            break;
                        if (t.getNewlinesBeforeCount() > 1) 
                            break;
                        if (t.getMorph()._getClass().isPreposition() && typ != null && (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комитет") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комиссия") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комітет")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комісія")))) {
                        }
                        else if (na.stdOrgNameNouns > 0) {
                        }
                        else 
                            break;
                    }
                }
                else if (t.getPrevious() != null && t.getWhitespacesBeforeCount() > 1 && attachTyp != AttachType.EXTONTOLOGY) {
                    if (t.getWhitespacesBeforeCount() > 10) 
                        break;
                    if (!t.chars.equals(t.getPrevious().chars)) 
                        break;
                }
                if (t.chars.isAllLower() && __org.getKind() == OrganizationKind.JUSTICE) {
                    if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("ПРАВО", null)) {
                    }
                    else if (t.isValue("З", null) && t.getNext() != null && t.getNext().isValue("ПРАВ", null)) {
                    }
                    else 
                        break;
                }
                if (__org.getKind() == OrganizationKind.FEDERATION) {
                    if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) 
                        break;
                }
                if (t.chars.isAllLower() && ((__org.getKind() == OrganizationKind.AIRPORT || __org.getKind() == OrganizationKind.SEAPORT || __org.getKind() == OrganizationKind.HOTEL))) 
                    break;
                if ((typ != null && typ.getLengthChar() == 2 && ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "АО") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "СП")))) && !specWordBefore && attachTyp == AttachType.NORMAL) {
                    if (!na.chars.isLatinLetter()) 
                        break;
                }
                if (t.chars.isLatinLetter() && typ != null && com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "служба", "сервис", "сервіс", null)) 
                    break;
                if (typ != null && ((typ.root == null || !typ.root.isPurePrefix))) {
                    if (typ.chars.isLatinLetter() && na.chars.isLatinLetter()) {
                        if (!t.isValue("OF", null)) 
                            break;
                    }
                    if ((na.isInDictionary && na.getMorph().getLanguage().isCyrillic() && na.chars.isAllLower()) && !na.getMorph().getCase().isUndefined()) {
                        if (na.preposition == null) {
                            if (!na.getMorph().getCase().isGenitive()) 
                                break;
                            if (__org.getKind() == OrganizationKind.PARTY && !specWordBefore) {
                                if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "лига")) {
                                }
                                else 
                                    break;
                            }
                            if (na.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                                com.pullenti.ner.ReferentToken prr = t.kit.processReferent("PERSONPROPERTY", t, null);
                                if (prr != null) {
                                    if (com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(na.getEndToken().getNext(), false) != null) {
                                    }
                                    else if (typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба")) 
                                        typ.setCoef(typ.getCoef() + (3.0F));
                                    else 
                                        break;
                                }
                            }
                        }
                    }
                    if (na.preposition != null) {
                        if (__org.getKind() == OrganizationKind.PARTY) {
                            if (com.pullenti.unisharp.Utils.stringsEq(na.preposition, "ЗА") || com.pullenti.unisharp.Utils.stringsEq(na.preposition, "ПРОТИВ")) {
                            }
                            else if (com.pullenti.unisharp.Utils.stringsEq(na.preposition, "В")) {
                                if (na.value.startsWith("ЗАЩИТ") && na.value.startsWith("ПОДДЕРЖ")) {
                                }
                                else 
                                    break;
                            }
                            else 
                                break;
                        }
                        else {
                            if (com.pullenti.unisharp.Utils.stringsEq(na.preposition, "В")) 
                                break;
                            if (typ.isDoubtRootWord()) {
                                if (com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "комитет", "комиссия", "комітет", "комісія") && ((t.isValue("ПО", null) || t.isValue("З", null)))) {
                                }
                                else if (names == null && na.stdOrgNameNouns > 0) {
                                }
                                else 
                                    break;
                            }
                        }
                    }
                    else if (na.chars.isCapitalUpper() && na.chars.isCyrillicLetter()) {
                        com.pullenti.ner.ReferentToken prt = na.kit.processReferent("PERSON", na.getBeginToken(), null);
                        if (prt != null) {
                            if (__org.getKind() == OrganizationKind.CHURCH) {
                                na.setEndToken(prt.getEndToken());
                                na.isStdName = true;
                                na.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(na, com.pullenti.ner.core.GetTextAttr.NO);
                            }
                            else if ((typ != null && typ.typ != null && (typ.typ.indexOf("фермер") >= 0)) && names == null) 
                                na.setEndToken(prt.getEndToken());
                            else 
                                break;
                        }
                    }
                }
                if (na.isEmptyWord) 
                    break;
                if (na.isStdTail) {
                    if (na.chars.isLatinLetter() && na.chars.isAllUpper() && (na.getLengthChar() < 4)) {
                        na.isStdTail = false;
                        na.value = na.getSourceText().toUpperCase();
                    }
                    else 
                        break;
                }
                names = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemNameToken>();
            }
            else {
                com.pullenti.ner._org.internal.OrgItemNameToken na0 = names.get(names.size() - 1);
                if (na0.isStdTail) 
                    break;
                if (na.preposition == null) {
                    if ((!na.chars.isLatinLetter() && na.chars.isAllLower() && !na.isAfterConjunction) && !na.getMorph().getCase().isGenitive()) 
                        break;
                }
            }
            names.add(na);
            t1 = (t = na.getEndToken());
        }
        typ = _lastTyp(types);
        boolean doHigherAlways = false;
        if (typ != null) {
            if (((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP)) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                if (names != null && names.size() > 0 && names.get(names.size() - 1).isNewlineAfter()) {
                }
                else if (num != null && num.isNewlineAfter()) {
                }
                else 
                    return null;
            }
            if (com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "комитет", "комиссия", "комітет", "комісія")) {
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба") && ownOrg != null && typ.name != null) {
                OrganizationKind ki = ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class)).getKind();
                if (ki == OrganizationKind.PRESS || ki == OrganizationKind.MEDIA) {
                    typ.setCoef(typ.getCoef() + (3.0F));
                    doHigherAlways = true;
                }
                else 
                    ownOrg = null;
            }
            else if ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба") && ownOrg != null && num == null) && _isMvdOrg((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class)) != null && (((((typ.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && (typ.getWhitespacesBeforeCount() < 3))) || names != null))) {
                typ.setCoef(typ.getCoef() + (4.0F));
                if (typ.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) {
                    t0 = typ.getBeginToken().getPrevious();
                    num = com.pullenti.ner._org.internal.OrgItemNumberToken._new1990(t0, t0, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(typ.getBeginToken().getPrevious(), com.pullenti.ner.NumberToken.class)).getValue());
                }
            }
            else if ((((typ.isDoubtRootWord() || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "организация") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "управление")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "служба") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "общество")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "союз") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "організація")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "керування") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "суспільство")) 
                ownOrg = null;
            if (__org.getKind() == OrganizationKind.GOVENMENT) {
                if (names == null && ((typ.name == null || com.pullenti.unisharp.Utils.stringsCompare(typ.name, typ.typ, true) == 0))) {
                    if ((attachTyp != AttachType.EXTONTOLOGY && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "следственный комитет") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "кабинет министров")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "слідчий комітет")) {
                        if (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "администрация") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "адміністрація"))) && (typ.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                            com.pullenti.ner.ReferentToken rt1 = typ.kit.processReferent("PERSONPROPERTY", typ.getEndToken().getNext(), null);
                            if (rt1 != null && typ.getEndToken().getNext().getMorph().getCase().isGenitive()) {
                                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt1.referent.getSlotValue("REF"), com.pullenti.ner.geo.GeoReferent.class);
                                if (_geo != null) {
                                    __org.addName("АДМИНИСТРАЦИЯ " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(typ.getEndToken().getNext(), com.pullenti.ner.TextToken.class)).term, true, null);
                                    __org.addGeoObject(_geo);
                                    return new com.pullenti.ner.ReferentToken(__org, typ.getBeginToken(), rt1.getEndToken(), null);
                                }
                            }
                        }
                        if ((typ.getCoef() < 5) || typ.chars.isAllLower()) 
                            return null;
                    }
                }
            }
        }
        else if (names != null && names.get(0).chars.isAllLower()) {
            if (attachTyp != AttachType.EXTONTOLOGY) 
                return null;
        }
        boolean always = false;
        String _name = null;
        if (((num != null || __org.getNumber() != null || epon != null) || attachTyp == AttachType.HIGH || attachTyp == AttachType.EXTONTOLOGY) || ownOrg != null) {
            int cou0 = __org.getSlots().size();
            if (names != null) {
                if ((names.size() == 1 && names.get(0).chars.isAllUpper() && attachTyp == AttachType.EXTONTOLOGY) && isAdditionalAttach) 
                    __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), names.get(names.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), true, names.get(0).getBeginToken());
                else {
                    _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), names.get(names.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if ((names.get(0).isNounPhrase && typ != null && typ.root != null) && !typ.root.isPurePrefix && multTyp == null) 
                        _name = (((typ.name != null ? typ.name : (typ != null && typ.typ != null ? typ.typ.toUpperCase() : null))) + " " + _name);
                }
            }
            else if (typ != null && typ.name != null && ((typ.root == null || !typ.root.isPurePrefix))) {
                if (typ.chars.isAllLower() && !typ.canBeOrganization && (typ.getNameWordsCount() < 3)) 
                    __org.addTypeStr(typ.name.toLowerCase());
                else 
                    _name = typ.name;
                if (typ != multTyp) {
                    if (t1.getEndChar() < typ.getEndToken().getEndChar()) 
                        t1 = typ.getEndToken();
                }
            }
            if (_name != null) {
                if (_name.length() > maxOrgName) 
                    return null;
                __org.addName(_name, true, null);
            }
            if (num != null) 
                __org.setNumber(num.number);
            if (epon != null) {
                for (String e : epon.eponyms) {
                    __org.addEponym(e);
                }
            }
            ok = attachTyp == AttachType.EXTONTOLOGY;
            if (typ != null && typ.root != null && typ.root.canBeNormalDep) 
                ok = true;
            for (com.pullenti.ner.Slot a : __org.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), OrganizationReferent.ATTR_NUMBER)) {
                    if (typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "корпус")) {
                    }
                    else 
                        ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), OrganizationReferent.ATTR_GEO)) {
                    if (typ.root != null && typ.root.canBeSingleGeo) 
                        ok = true;
                }
                else if (com.pullenti.unisharp.Utils.stringsNe(a.getTypeName(), OrganizationReferent.ATTR_TYPE) && com.pullenti.unisharp.Utils.stringsNe(a.getTypeName(), OrganizationReferent.ATTR_PROFILE)) {
                    ok = true;
                    break;
                }
            }
            if (attachTyp == AttachType.NORMAL) {
                if (typ == null) 
                    ok = false;
                else if ((typ.getEndChar() - typ.getBeginChar()) < 2) {
                    if (num == null && epon == null) 
                        ok = false;
                    else if (epon == null) {
                        if (t1.isWhitespaceAfter() || t1.getNext() == null) {
                        }
                        else if (t1.getNext().isCharOf(".,;") && t1.getNext().isWhitespaceAfter()) {
                        }
                        else 
                            ok = false;
                    }
                }
            }
            if ((!ok && typ != null && typ.canBeDepBeforeOrganization) && ownOrg != null) {
                __org.addTypeStr((ownOrg.kit.baseLanguage.isUa() ? "підрозділ" : "подразделение"));
                __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                t1 = ownOrg;
                ok = true;
            }
            else if (typ != null && ownOrg != null && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class), __org, true)) {
                if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class), __org)) {
                    if (__org.getKind() == OrganizationKind.DEPARTMENT && !typ.canBeDepBeforeOrganization) {
                    }
                    else {
                        __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                        if (t1.getEndChar() < ownOrg.getEndChar()) 
                            t1 = ownOrg;
                        ok = true;
                    }
                }
                else if (typ.root != null && ((typ.root.canBeNormalDep || (ownOrg.referent.toString().indexOf("Сбербанк") >= 0)))) {
                    __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                    if (t1.getEndChar() < ownOrg.getEndChar()) 
                        t1 = ownOrg;
                    ok = true;
                }
            }
        }
        else if (names != null) {
            if (typ == null) {
                if (names.get(0).isStdName && specWordBefore) {
                    __org.addName(names.get(0).value, true, null);
                    t1 = names.get(0).getEndToken();
                    t = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                    if (t != null) 
                        t1 = t;
                    return new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
                }
                return null;
            }
            if (typ.root != null && typ.root.mustHasCapitalName) {
                if (names.get(0).chars.isAllLower()) 
                    return null;
            }
            if (names.get(0).chars.isLatinLetter()) {
                if (typ.root != null && !typ.root.canHasLatinName) {
                    if (!typ.chars.isLatinLetter()) 
                        return null;
                }
                if (names.get(0).chars.isAllLower() && !typ.chars.isLatinLetter()) 
                    return null;
                StringBuilder tmp = new StringBuilder();
                tmp.append(names.get(0).value);
                t1 = names.get(0).getEndToken();
                for (int j = 1; j < names.size(); j++) {
                    if (!names.get(j).isStdTail && ((names.get(j).isNewlineBefore() || !names.get(j).chars.isLatinLetter()))) {
                        tMax = names.get(j).getBeginToken().getPrevious();
                        if (typ.geo == null && __org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                            __org.getSlots().remove(__org.findSlot(OrganizationReferent.ATTR_GEO, null, true));
                        break;
                    }
                    else {
                        t1 = names.get(j).getEndToken();
                        if (names.get(j).isStdTail) {
                            com.pullenti.ner._org.internal.OrgItemEngItem ei = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(names.get(j).getBeginToken(), false);
                            if (ei != null) {
                                __org.addTypeStr(ei.fullValue);
                                if (ei.shortValue != null) 
                                    __org.addTypeStr(ei.shortValue);
                            }
                            break;
                        }
                        if (names.get(j - 1).getEndToken().isChar('.') && !names.get(j - 1).value.endsWith(".")) 
                            tmp.append(".").append(names.get(j).value);
                        else 
                            tmp.append(" ").append(names.get(j).value);
                    }
                }
                if (tmp.length() > maxOrgName) 
                    return null;
                String nnn = tmp.toString();
                if (nnn.startsWith("OF ") || nnn.startsWith("IN ")) 
                    tmp.insert(0, (((typ.name != null ? typ.name : typ.typ))).toUpperCase() + " ");
                if (tmp.length() < 3) {
                    if (tmp.length() < 2) 
                        return null;
                    if (types != null && names.get(0).chars.isAllUpper()) {
                    }
                    else 
                        return null;
                }
                ok = true;
                __org.addName(tmp.toString(), true, null);
            }
            else if (typ.root != null && typ.root.isPurePrefix) {
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(typ.getEndToken(), com.pullenti.ner.TextToken.class);
                if (tt == null) 
                    return null;
                if (tt.isNewlineAfter()) {
                    if (names.get(0).isNewlineAfter() && typ.isNewlineBefore()) {
                    }
                    else 
                        return null;
                }
                if (typ.getBeginToken() == typ.getEndToken() && tt.chars.isAllLower()) 
                    return null;
                if (names.get(0).chars.isAllLower()) {
                    if (!names.get(0).getMorph().getCase().isGenitive()) 
                        return null;
                }
                t1 = names.get(0).getEndToken();
                for (int j = 1; j < names.size(); j++) {
                    if (names.get(j).isNewlineBefore() || !names.get(j).chars.equals(names.get(0).chars)) 
                        break;
                    else 
                        t1 = names.get(j).getEndToken();
                }
                ok = true;
                _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
                if (num == null && (t1 instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    com.pullenti.ner.Token tt1 = t1.getPrevious();
                    if (tt1 != null && tt1.isHiphen()) 
                        tt1 = tt1.getPrevious();
                    if (tt1 != null && tt1.getEndChar() > names.get(0).getBeginChar() && (tt1 instanceof com.pullenti.ner.TextToken)) {
                        _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), tt1, com.pullenti.ner.core.GetTextAttr.NO);
                        __org.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    }
                }
                if (_name.length() > maxOrgName) 
                    return null;
                __org.addName(_name, true, names.get(0).getBeginToken());
            }
            else {
                if (typ.isDep()) 
                    return null;
                if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL && attachTyp != AttachType.MULTIPLE) 
                    return null;
                StringBuilder tmp = new StringBuilder();
                float koef = typ.getCoef();
                if (koef >= 4) 
                    always = true;
                if (__org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                    koef += (1.0F);
                if (specWordBefore) 
                    koef += (1.0F);
                if (names.get(0).chars.isAllLower() && typ.chars.isAllLower() && !specWordBefore) {
                    if (koef >= 3) {
                        if (t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        }
                        else 
                            koef -= (3.0F);
                    }
                }
                if (typ.charsRoot.isCapitalUpper()) 
                    koef += ((float)0.5);
                if (types.size() > 1) 
                    koef += ((float)(types.size() - 1));
                if (typ.name != null) {
                    for (com.pullenti.ner.Token to = typ.getBeginToken(); to != typ.getEndToken() && to != null; to = to.getNext()) {
                        if (com.pullenti.ner._org.internal.OrgItemTypeToken.isStdAdjective(to, false)) 
                            koef += (2.0F);
                        if (to.chars.isCapitalUpper()) 
                            koef += ((float)0.5);
                    }
                }
                OrganizationKind ki = __org.getKind();
                if (attachTyp == AttachType.MULTIPLE && ((typ.name == null || typ.name.length() == typ.typ.length()))) {
                }
                else if ((((((ki == OrganizationKind.MEDIA || ki == OrganizationKind.PARTY || ki == OrganizationKind.PRESS) || ki == OrganizationKind.FACTORY || ki == OrganizationKind.AIRPORT) || ki == OrganizationKind.SEAPORT || ((typ.root != null && typ.root.mustHasCapitalName))) || ki == OrganizationKind.BANK || (typ.typ.indexOf("предприятие") >= 0)) || (typ.typ.indexOf("организация") >= 0) || (typ.typ.indexOf("підприємство") >= 0)) || (typ.typ.indexOf("організація") >= 0)) {
                    if (typ.name != null) 
                        __org.addTypeStr(typ.name.toLowerCase());
                }
                else 
                    tmp.append((typ.name != null ? typ.name : (typ != null && typ.typ != null ? typ.typ.toUpperCase() : null)));
                if (typ != multTyp) 
                    t1 = typ.getEndToken();
                for (int j = 0; j < names.size(); j++) {
                    if (names.get(j).isNewlineBefore() && j > 0) {
                        if (names.get(j).getNewlinesBeforeCount() > 1) 
                            break;
                        if (names.get(j).chars.isAllLower()) {
                        }
                        else 
                            break;
                    }
                    if (!names.get(j).chars.equals(names.get(0).chars) && !names.get(j).getBeginToken().chars.equals(names.get(0).chars)) 
                        break;
                    if (names.get(j).isNounPhrase != names.get(0).isNounPhrase) 
                        break;
                    if (j == 0 && names.get(j).preposition == null && names.get(j).isInDictionary) {
                        if (!names.get(j).getMorph().getCase().isGenitive() && ((typ.root != null && !typ.root.canHasSingleName))) 
                            break;
                    }
                    if (j == 0 && com.pullenti.unisharp.Utils.stringsEq(names.get(0).preposition, "ПО") && (((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комитет") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комиссия") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комітет")) || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "комісія")))) 
                        koef += 2.5F;
                    if ((j == 0 && names.get(j).getWhitespacesBeforeCount() > 2 && names.get(j).getNewlinesBeforeCount() == 0) && names.get(j).getBeginToken().getPrevious() != null) 
                        koef -= (((float)names.get(j).getWhitespacesBeforeCount()) / (2.0F));
                    if (names.get(j).isStdName) 
                        koef += (4.0F);
                    else if (names.get(j).stdOrgNameNouns > 0 && ((ki == OrganizationKind.GOVENMENT || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "центр")))) 
                        koef += ((float)names.get(j).stdOrgNameNouns);
                    if (((ki == OrganizationKind.AIRPORT || ki == OrganizationKind.SEAPORT)) && j == 0) 
                        koef++;
                    t1 = names.get(j).getEndToken();
                    if (names.get(j).isNounPhrase) {
                        if (!names.get(j).chars.isAllLower()) {
                            com.pullenti.morph.MorphCase ca = names.get(j).getMorph().getCase();
                            if ((ca.isDative() || ca.isGenitive() || ca.isInstrumental()) || ca.isPrepositional()) 
                                koef += ((float)0.5);
                            else 
                                continue;
                        }
                        else if (((j == 0 || names.get(j).isAfterConjunction)) && names.get(j).getMorph().getCase().isGenitive() && names.get(j).preposition == null) 
                            koef += ((float)0.5);
                        if (j == (names.size() - 1)) {
                            if (names.get(j).getEndToken().getNext() instanceof com.pullenti.ner.TextToken) {
                                if (names.get(j).getEndToken().getNext().getMorphClassInDictionary().isVerb()) 
                                    koef += 0.5F;
                            }
                        }
                    }
                    for (com.pullenti.ner.Token to = names.get(j).getBeginToken(); to != null; to = to.getNext()) {
                        if (to instanceof com.pullenti.ner.TextToken) {
                            if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                                if (to.chars.isCapitalUpper()) 
                                    koef += ((float)0.5);
                                else if ((j == 0 && ((to.chars.isAllUpper() || to.chars.isLastLower())) && to.getLengthChar() > 2) && typ.root != null && typ.root.canHasLatinName) 
                                    koef += (1.0F);
                            }
                            else if (to.chars.isAllUpper() || to.chars.isCapitalUpper()) 
                                koef += (1.0F);
                        }
                        if (to == names.get(j).getEndToken()) 
                            break;
                    }
                }
                for (com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                    if (ttt.getReferent() instanceof OrganizationReferent) {
                        koef += (1.0F);
                        break;
                    }
                    else if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                        break;
                    else if (ttt.chars.isLetter()) 
                        break;
                }
                OrganizationKind oki = __org.getKind();
                if (oki == OrganizationKind.GOVENMENT || oki == OrganizationKind.STUDY || oki == OrganizationKind.PARTY) 
                    koef += ((float)names.size());
                if (attachTyp != AttachType.NORMAL && attachTyp != AttachType.NORMALAFTERDEP) 
                    koef += (3.0F);
                com.pullenti.ner.core.BracketSequenceToken br1 = null;
                if ((t1.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1.getNext(), true, false)) {
                    br1 = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br1 != null && (br1.getLengthChar() < 30)) {
                        String sss = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br1, com.pullenti.ner.core.GetTextAttr.NO);
                        if (sss != null && sss.length() > 2) {
                            __org.addName(sss, true, br1.getBeginToken().getNext());
                            koef += (1.0F);
                            t1 = br1.getEndToken();
                        }
                        else 
                            br1 = null;
                    }
                }
                if (koef >= 3 && t1.getNext() != null) {
                    com.pullenti.ner.Referent r = t1.getNext().getReferent();
                    if (r != null && ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), OrganizationReferent.OBJ_TYPENAME)))) 
                        koef += (1.0F);
                    else if (isGeo(t1.getNext(), false) != null) 
                        koef += (1.0F);
                    else if (t1.getNext().isChar('(') && isGeo(t1.getNext().getNext(), false) != null) 
                        koef += (1.0F);
                    else if (specWordBefore && t1.kit.processReferent("PERSON", t1.getNext(), null) != null) 
                        koef += (1.0F);
                }
                if (koef >= 4) 
                    ok = true;
                if (!ok) {
                    if ((oki == OrganizationKind.PRESS || oki == OrganizationKind.FEDERATION || __org.getTypes().contains("агентство")) || ((oki == OrganizationKind.PARTY && com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())))) {
                        if (!names.get(0).isNewlineBefore() && !names.get(0).getMorph()._getClass().isProper()) {
                            if (names.get(0).getMorph().getCase().isGenitive() && names.get(0).isInDictionary) {
                                if (typ.chars.isAllLower() && !names.get(0).chars.isAllLower()) {
                                    ok = true;
                                    t1 = names.get(0).getEndToken();
                                }
                            }
                            else if (!names.get(0).isInDictionary && names.get(0).chars.isAllUpper()) {
                                ok = true;
                                tmp.setLength(0);
                                t1 = names.get(0).getEndToken();
                            }
                        }
                    }
                }
                if ((!ok && oki == OrganizationKind.FEDERATION && names.get(0).getMorph().getCase().isGenitive()) && koef > 0) {
                    if (isGeo(names.get(names.size() - 1).getEndToken().getNext(), false) != null) 
                        ok = true;
                }
                if (!ok && typ != null && typ.root != null) {
                    if (names.size() == 1 && ((names.get(0).chars.isAllUpper() || names.get(0).chars.isLastLower()))) {
                        if ((ki == OrganizationKind.BANK || ki == OrganizationKind.CULTURE || ki == OrganizationKind.HOTEL) || ki == OrganizationKind.MEDIA || ki == OrganizationKind.MEDICAL) 
                            ok = true;
                    }
                }
                if (((!ok && typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "компания")) && names.size() == 1 && !names.get(0).chars.isAllLower()) && (typ.getWhitespacesAfterCount() < 3)) 
                    ok = true;
                if (ok) {
                    com.pullenti.ner.Token tt1 = t1;
                    if (br1 != null) 
                        tt1 = br1.getBeginToken().getPrevious();
                    if ((tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt1.getReferent(), com.pullenti.ner.geo.GeoReferent.class)).isState()) {
                        if (names.get(0).getBeginToken() != tt1) {
                            tt1 = t1.getPrevious();
                            __org.addGeoObject(t1.getReferent());
                        }
                    }
                    String s = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), tt1, com.pullenti.ner.core.GetTextAttr.NO);
                    if ((tt1 == names.get(0).getEndToken() && typ != null && typ.typ != null) && (typ.typ.indexOf("фермер") >= 0) && names.get(0).value != null) 
                        s = names.get(0).value;
                    com.pullenti.morph.MorphClass cla = tt1.getMorphClassInDictionary();
                    if ((names.get(0).getBeginToken() == t1 && s != null && t1.getMorph().getCase().isGenitive()) && t1.chars.isCapitalUpper()) {
                        if (cla.isUndefined() || cla.isProperGeo()) {
                            if (ki == OrganizationKind.MEDICAL || ki == OrganizationKind.JUSTICE) {
                                com.pullenti.ner.geo.GeoReferent _geo = new com.pullenti.ner.geo.GeoReferent();
                                _geo.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, t1.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), false, 0);
                                _geo.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, (t1.kit.baseLanguage.isUa() ? "місто" : "город"), false, 0);
                                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(_geo, t1, t1, null);
                                rt.data = OrganizationAnalyzer.getData(t1);
                                __org.addGeoObject(rt);
                                s = null;
                            }
                        }
                    }
                    if (s != null) {
                        if (tmp.length() == 0) {
                            if (names.get(0).getMorph().getCase().isGenitive() || names.get(0).preposition != null) {
                                if (names.get(0).chars.isAllLower()) 
                                    tmp.append((typ.name != null ? typ.name : typ.typ));
                            }
                        }
                        if (tmp.length() > 0) 
                            tmp.append(' ');
                        tmp.append(s);
                    }
                    if (tmp.length() > maxOrgName) 
                        return null;
                    __org.addName(tmp.toString(), true, names.get(0).getBeginToken());
                    if (types.size() > 1 && types.get(0).name != null) 
                        __org.addTypeStr(types.get(0).name.toLowerCase());
                }
            }
        }
        else {
            if (typ == null) 
                return null;
            if (types.size() == 2 && types.get(0).getCoef() > typ.getCoef()) 
                typ = types.get(0);
            if ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "банк") && (t instanceof com.pullenti.ner.ReferentToken) && !t.isNewlineBefore()) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                if (typ.name != null) {
                    if (typ.getBeginToken().chars.isAllLower()) 
                        __org.addTypeStr(typ.name.toLowerCase());
                    else {
                        __org.addName(typ.name, true, null);
                        String s0 = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(typ, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                        if (com.pullenti.unisharp.Utils.stringsNe(s0, typ.name)) 
                            __org.addName(s0, true, null);
                    }
                }
                com.pullenti.ner.Referent r = t.getReferent();
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME) && !t.getMorph().getCase().equals(com.pullenti.morph.MorphCase.NOMINATIVE)) {
                    __org.addGeoObject(r);
                    if (types.size() == 1 && (t.getWhitespacesAfterCount() < 3)) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false);
                        if (typ1 != null && typ1.root != null && typ1.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) {
                            __org.addType(typ1, false);
                            t = typ1.getEndToken();
                        }
                    }
                    return new com.pullenti.ner.ReferentToken(__org, t0, t, null);
                }
            }
            if (((typ.root != null && typ.root.isPurePrefix)) && (typ.getCoef() < 4)) 
                return null;
            if (typ.root != null && typ.root.mustHasCapitalName) 
                return null;
            if (typ.name == null) {
                if (((typ.typ.endsWith("университет") || typ.typ.endsWith("університет"))) && isGeo(typ.getEndToken().getNext(), false) != null) 
                    always = true;
                else if (((__org.getKind() == OrganizationKind.JUSTICE || __org.getKind() == OrganizationKind.AIRPORT || __org.getKind() == OrganizationKind.SEAPORT)) && __org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) {
                }
                else if (typ.getCoef() >= 4) 
                    always = true;
                else if (typ.chars.isCapitalUpper()) {
                    if (typ.getEndToken().getNext() != null && ((typ.getEndToken().getNext().isHiphen() || typ.getEndToken().getNext().isCharOf(":")))) {
                    }
                    else {
                        com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
                        java.util.ArrayList<com.pullenti.ner.core.IntOntologyItem> li = (ad == null || ad.localOntology.getItems().size() > 1000 ? null : ad.localOntology.tryAttachByItem(__org.createOntologyItem()));
                        if (li != null && li.size() > 0) {
                            for (com.pullenti.ner.core.IntOntologyItem ll : li) {
                                com.pullenti.ner.Referent r = (ll.referent != null ? ll.referent : ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(ll.tag, com.pullenti.ner.Referent.class)));
                                if (r != null) {
                                    if (__org.canBeEquals(r, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) {
                                        com.pullenti.ner.Token ttt = typ.getEndToken();
                                        com.pullenti.ner._org.internal.OrgItemNumberToken nu = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(ttt.getNext(), true, null);
                                        if (nu != null) {
                                            if (com.pullenti.unisharp.Utils.stringsNe(((OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, OrganizationReferent.class)).getNumber(), nu.number)) 
                                                ttt = null;
                                            else {
                                                __org.setNumber(nu.number);
                                                ttt = nu.getEndToken();
                                            }
                                        }
                                        else if (li.size() > 1) 
                                            ttt = null;
                                        if (ttt != null) 
                                            return new com.pullenti.ner.ReferentToken(r, typ.getBeginToken(), ttt, null);
                                    }
                                }
                            }
                        }
                    }
                    return null;
                }
                else {
                    int cou = 0;
                    for (com.pullenti.ner.Token tt = typ.getBeginToken().getPrevious(); tt != null && (cou < 200); tt = tt.getPrevious(),cou++) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                        if (org0 == null) 
                            continue;
                        if (!org0.canBeEquals(__org, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                            continue;
                        tt = (com.pullenti.ner.Token)com.pullenti.unisharp.Utils.notnull(attachTailAttributes(__org, typ.getEndToken().getNext(), false, attachTyp, false), typ.getEndToken());
                        if (!org0.canBeEquals(__org, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                            break;
                        __org.mergeSlots(org0, true);
                        return new com.pullenti.ner.ReferentToken(__org, typ.getBeginToken(), tt, null);
                    }
                    if (typ.root != null && typ.root.canBeSingleGeo && t1.getNext() != null) {
                        Object ggg = isGeo(t1.getNext(), false);
                        if (ggg != null) {
                            __org.addGeoObject(ggg);
                            t1 = getGeoEndToken(ggg, t1.getNext());
                            return new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
                        }
                    }
                    return null;
                }
            }
            if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL || typ == multTyp) 
                return null;
            float koef = typ.getCoef();
            if (typ.getNameWordsCount() == 1 && typ.name != null && typ.name.length() > typ.typ.length()) 
                koef++;
            if (specWordBefore) 
                koef += (1.0F);
            ok = false;
            if (typ.charsRoot.isCapitalUpper()) {
                koef += ((float)0.5);
                if (typ.getNameWordsCount() == 1) 
                    koef += ((float)0.5);
            }
            if (epon != null) 
                koef += (2.0F);
            boolean hasNonstdWords = false;
            for (com.pullenti.ner.Token to = typ.getBeginToken(); to != typ.getEndToken() && to != null; to = to.getNext()) {
                if (com.pullenti.ner._org.internal.OrgItemTypeToken.isStdAdjective(to, false)) {
                    if (typ.root != null && typ.root.coeff > 0) 
                        koef += ((float)(com.pullenti.ner._org.internal.OrgItemTypeToken.isStdAdjective(to, true) ? 1 : (int)0.5F));
                }
                else 
                    hasNonstdWords = true;
                if (to.chars.isCapitalUpper() && !to.getMorph()._getClass().isPronoun()) 
                    koef += ((float)0.5);
            }
            if (!hasNonstdWords && __org.getKind() == OrganizationKind.GOVENMENT) 
                koef -= (2.0F);
            if (typ.chars.isAllLower() && (typ.getCoef() < 4)) 
                koef -= (2.0F);
            if (koef > 1 && typ.getNameWordsCount() > 2) 
                koef += (2.0F);
            for (com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    koef += (1.0F);
                    break;
                }
                else if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                    break;
                else if (ttt.chars.isLetter()) 
                    break;
            }
            for (com.pullenti.ner.Token ttt = typ.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    koef += (1.0F);
                    break;
                }
                else if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                    break;
                else if (ttt.chars.isLetter()) 
                    break;
            }
            if (typ.getWhitespacesBeforeCount() > 4 && typ.getWhitespacesAfterCount() > 4) 
                koef += ((float)0.5);
            if (typ.canBeOrganization) {
                for (com.pullenti.ner.Slot s : __org.getSlots()) {
                    if ((com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_EPONYM) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NAME) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_GEO)) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NUMBER)) {
                        koef += (3.0F);
                        break;
                    }
                }
            }
            __org.addType(typ, false);
            if (((__org.getKind() == OrganizationKind.BANK || __org.getKind() == OrganizationKind.JUSTICE)) && typ.name != null && typ.name.length() > typ.typ.length()) 
                koef += (1.0F);
            if (__org.getKind() == OrganizationKind.JUSTICE && __org.getGeoObjects().size() > 0) 
                always = true;
            if (__org.getKind() == OrganizationKind.AIRPORT || __org.getKind() == OrganizationKind.SEAPORT) {
                for (com.pullenti.ner.geo.GeoReferent g : __org.getGeoObjects()) {
                    if (g.isCity()) 
                        always = true;
                }
            }
            if (koef > 3 || always) 
                ok = true;
            if (((__org.getKind() == OrganizationKind.PARTY || __org.getKind() == OrganizationKind.JUSTICE)) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                if (__org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null && typ.name != null && typ.name.length() > typ.typ.length()) 
                    ok = true;
                else if (typ.getCoef() >= 4) 
                    ok = true;
                else if (typ.getNameWordsCount() > 2) 
                    ok = true;
            }
            if (ok) {
                if (typ.name != null && !typ.isNotTyp) {
                    if (typ.name.length() > maxOrgName || com.pullenti.unisharp.Utils.stringsCompare(typ.name, typ.typ, true) == 0) 
                        return null;
                    __org.addName(typ.name, true, null);
                }
                t1 = typ.getEndToken();
            }
        }
        if (!ok || __org.getSlots().size() == 0) 
            return null;
        if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
            ok = always;
            for (com.pullenti.ner.Slot s : __org.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), OrganizationReferent.ATTR_TYPE) && com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), OrganizationReferent.ATTR_PROFILE)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return null;
        }
        if (tMax != null && (t1.getEndChar() < tMax.getBeginChar())) 
            t1 = tMax;
        t = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
        if (t != null) 
            t1 = t;
        if (ownOrg != null && __org.getHigher() == null) {
            if (doHigherAlways || com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class), __org, false)) {
                __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                if (ownOrg.getBeginChar() > t1.getBeginChar()) {
                    t1 = ownOrg;
                    t = attachTailAttributes(__org, t1.getNext(), true, attachTyp, false);
                    if (t != null) 
                        t1 = t;
                }
            }
        }
        if (((ownOrg != null && typ != null && com.pullenti.unisharp.Utils.stringsEq(typ.typ, "банк")) && typ.geo != null && __org.getHigher() == ownOrg.referent) && (ownOrg.referent.toString().indexOf("Сбербанк") >= 0)) {
            com.pullenti.ner.Token tt2 = t1.getNext();
            if (tt2 != null) {
                if (tt2.isComma() || tt2.isValue("В", null)) 
                    tt2 = tt2.getNext();
            }
            if (tt2 != null && (tt2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                com.pullenti.ner.Slot s = __org.findSlot(OrganizationReferent.ATTR_GEO, null, true);
                if (s != null) 
                    __org.getSlots().remove(s);
                if (__org.addGeoObject(tt2)) 
                    t1 = tt2;
            }
        }
        if (t1.isNewlineAfter() && t0.isNewlineBefore()) {
            com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false);
            if (typ1 != null && typ1.isNewlineAfter()) {
                if (tryAttachOrg(t1.getNext(), AttachType.NORMAL, null, false, -1) == null) {
                    __org.addType(typ1, false);
                    t1 = typ1.getEndToken();
                }
            }
            if (t1.getNext() != null && t1.getNext().isChar('(')) {
                if ((((typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext().getNext(), false)))) != null) {
                    if (typ1.getEndToken().getNext() != null && typ1.getEndToken().getNext().isChar(')') && typ1.getEndToken().getNext().isNewlineAfter()) {
                        __org.addType(typ1, false);
                        t1 = typ1.getEndToken().getNext();
                    }
                }
            }
        }
        if (attachTyp == AttachType.NORMAL && ((typ == null || (typ.getCoef() < 4)))) {
            if (__org.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null || ((typ != null && typ.geo != null))) {
                boolean isAllLow = true;
                for (t = t0; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
                    if (t.chars.isLetter()) {
                        if (!t.chars.isAllLower()) 
                            isAllLow = false;
                    }
                    else if (!(t instanceof com.pullenti.ner.TextToken)) 
                        isAllLow = false;
                }
                if (isAllLow && !specWordBefore) 
                    return null;
            }
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
        if (types != null && types.size() > 0) {
            res.setMorph(types.get(0).getMorph());
            if (types.get(0).isNotTyp && types.get(0).getBeginToken() == t0 && (types.get(0).getEndChar() < t1.getEndChar())) 
                res.setBeginToken(types.get(0).getEndToken().getNext());
        }
        else 
            res.setMorph(t0.getMorph());
        if ((__org.getNumber() == null && t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2)) && typ != null && ((typ.root == null || typ.root.canHasNumber))) {
            com.pullenti.ner._org.internal.OrgItemNumberToken num1 = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, typ);
            if (num1 == null && t1.getNext().isHiphen()) 
                num1 = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1.getNext().getNext(), false, typ);
            if (num1 != null) {
                if (com.pullenti.ner._org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 2)) {
                }
                else {
                    __org.setNumber(num1.number);
                    t1 = num1.getEndToken();
                    res.setEndToken(t1);
                }
            }
        }
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachOrgBefore(com.pullenti.ner.Token t) {
        if (t == null || t.getPrevious() == null) 
            return null;
        int minEndChar = t.getPrevious().getEndChar();
        int maxEndChar = t.getEndChar();
        com.pullenti.ner.Token t0 = t.getPrevious();
        if ((t0 instanceof com.pullenti.ner.ReferentToken) && (t0.getReferent() instanceof OrganizationReferent) && t0.getPrevious() != null) {
            minEndChar = t0.getPrevious().getEndChar();
            t0 = t0.getPrevious();
        }
        com.pullenti.ner.ReferentToken res = null;
        for (; t0 != null; t0 = t0.getPrevious()) {
            if (t0.getWhitespacesAfterCount() > 1) 
                break;
            int cou = 0;
            com.pullenti.ner.Token tt0 = t0;
            String num = null;
            com.pullenti.ner.Token numEt = null;
            for (com.pullenti.ner.Token ttt = t0; ttt != null; ttt = ttt.getPrevious()) {
                if (ttt.getWhitespacesAfterCount() > 1) 
                    break;
                if (ttt.isHiphen() || ttt.isChar('.')) 
                    continue;
                if (ttt instanceof com.pullenti.ner.NumberToken) {
                    if (num != null) 
                        break;
                    num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.NumberToken.class)).getValue().toString();
                    numEt = ttt;
                    tt0 = ttt.getPrevious();
                    continue;
                }
                com.pullenti.ner._org.internal.OrgItemNumberToken nn = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(ttt, false, null);
                if (nn != null) {
                    num = nn.number;
                    numEt = nn.getEndToken();
                    tt0 = ttt.getPrevious();
                    continue;
                }
                if ((++cou) > 10) 
                    break;
                if (ttt.isValue("НАПРАВЛЕНИЕ", "НАПРЯМОК")) {
                    if (num != null || (((ttt.getPrevious() instanceof com.pullenti.ner.NumberToken) && (ttt.getWhitespacesBeforeCount() < 3)))) {
                        OrganizationReferent oo = new OrganizationReferent();
                        oo.addProfile(OrgProfile.UNIT);
                        oo.addTypeStr(((ttt.getMorph().getLanguage().isUa() ? "НАПРЯМОК" : "НАПРАВЛЕНИЕ")).toLowerCase());
                        com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(oo, ttt, ttt, null);
                        if (numEt != null && num != null) {
                            oo.addSlot(OrganizationReferent.ATTR_NUMBER, num, false, 0);
                            rt0.setEndToken(numEt);
                            return rt0;
                        }
                        if (ttt.getPrevious() instanceof com.pullenti.ner.NumberToken) {
                            rt0.setBeginToken(ttt.getPrevious());
                            oo.addSlot(OrganizationReferent.ATTR_NUMBER, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttt.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString(), false, 0);
                            return rt0;
                        }
                    }
                }
                com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(ttt, true);
                if (typ1 == null || typ1.getBeginToken() != ttt) {
                    if (cou == 1) 
                        break;
                    continue;
                }
                if (typ1.getEndToken() == tt0) 
                    t0 = ttt;
            }
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t0, AttachType.NORMAL, null, false, -1);
            if (rt != null) {
                if (rt.getBeginToken() != t0) 
                    return null;
                if (rt.getEndChar() >= minEndChar && rt.getEndChar() <= maxEndChar) {
                    OrganizationReferent oo = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (oo.getHigher() != null && oo.getHigher().getHigher() != null && oo.getHigher() == rt.getEndToken().getReferent()) 
                        return rt;
                    if (rt.getBeginChar() < t.getBeginChar()) 
                        return rt;
                    res = rt;
                }
                else 
                    break;
            }
            else if (!(t0 instanceof com.pullenti.ner.TextToken)) 
                break;
            else if (!t0.chars.isLetter()) {
                if (!com.pullenti.ner.core.BracketHelper.isBracket(t0, false)) 
                    break;
            }
        }
        if (res != null) 
            return null;
        com.pullenti.ner._org.internal.OrgItemTypeToken typ = null;
        for (t0 = t.getPrevious(); t0 != null; t0 = t0.getPrevious()) {
            if (t0.getWhitespacesAfterCount() > 1) 
                break;
            if (t0 instanceof com.pullenti.ner.NumberToken) 
                continue;
            if (t0.isChar('.') || t0.isHiphen()) 
                continue;
            if (!(t0 instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!t0.chars.isLetter()) 
                break;
            com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t0, true);
            if (ty != null) {
                com.pullenti.ner._org.internal.OrgItemNumberToken nn = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(ty.getEndToken().getNext(), true, ty);
                if (nn != null) {
                    ty.setEndToken(nn.getEndToken());
                    ty.number = nn.number;
                }
                else if ((ty.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) && (ty.getWhitespacesAfterCount() < 2)) {
                    ty.setEndToken(ty.getEndToken().getNext());
                    ty.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ty.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue().toString();
                }
                if (typ != null && ty.getBeginChar() > typ.getBeginChar()) 
                    break;
                if (ty.getEndChar() >= minEndChar && ty.getEndChar() <= maxEndChar) 
                    typ = ty;
                else 
                    break;
            }
        }
        if (typ != null && typ.isDep()) 
            res = tryAttachDepBeforeOrg(typ, null);
        return res;
    }

    private static com.pullenti.ner.ReferentToken tryAttachDepBeforeOrg(com.pullenti.ner._org.internal.OrgItemTypeToken typ, com.pullenti.ner.ReferentToken rtOrg) {
        if (typ == null) 
            return null;
        OrganizationReferent __org = (rtOrg == null ? null : (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rtOrg.referent, OrganizationReferent.class));
        com.pullenti.ner.Token t = typ.getEndToken();
        if (__org == null) {
            t = t.getNext();
            if (t != null && ((t.isValue("ПРИ", null) || t.isValue("AT", null) || t.isValue("OF", null)))) 
                t = t.getNext();
            if (t == null) 
                return null;
            __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
        }
        else 
            t = rtOrg.getEndToken();
        if (__org == null) 
            return null;
        com.pullenti.ner.Token t1 = t;
        if (t1.getNext() instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.geo.GeoReferent geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geo0 != null && com.pullenti.unisharp.Utils.stringsEq(geo0.getAlpha2(), "RU")) 
                t1 = t1.getNext();
        }
        OrganizationReferent dep = new OrganizationReferent();
        dep.addType(typ, false);
        if (typ.name != null) {
            String nam = typ.name;
            if (Character.isDigit(nam.charAt(0))) {
                int i = nam.indexOf(' ');
                if (i > 0) {
                    dep.setNumber(nam.substring(0, 0 + i));
                    nam = nam.substring(i + 1).trim();
                }
            }
            dep.addName(nam, true, null);
        }
        String ttt = (typ.root != null ? typ.root.getCanonicText() : typ.typ.toUpperCase());
        if ((((com.pullenti.unisharp.Utils.stringsEq(ttt, "ОТДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(ttt, "ИНСПЕКЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(ttt, "ВІДДІЛЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(ttt, "ІНСПЕКЦІЯ"))) && !t1.isNewlineAfter()) {
            com.pullenti.ner._org.internal.OrgItemNumberToken num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, typ);
            if (num != null) {
                dep.setNumber(num.number);
                t1 = num.getEndToken();
            }
        }
        if (dep.getTypes().contains("главное управление") || dep.getTypes().contains("головне управління") || (dep.getTypeName().indexOf("пограничное управление") >= 0)) {
            if (typ.getBeginToken() == typ.getEndToken()) {
                if (__org.getKind() != OrganizationKind.GOVENMENT && __org.getKind() != OrganizationKind.BANK) 
                    return null;
            }
        }
        if (!com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(__org, dep, false) && ((typ.root == null || !typ.root.canBeNormalDep))) {
            if (dep.getTypes().size() > 0 && __org.getTypes().contains(dep.getTypes().get(0)) && dep.canBeEquals(__org, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) 
                dep.mergeSlots(__org, false);
            else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "управление") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "управління")) 
                dep.setHigher(__org);
            else 
                return null;
        }
        else 
            dep.setHigher(__org);
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, typ.getBeginToken(), t1, null);
        if ((res.getBeginToken().getPrevious() instanceof com.pullenti.ner.ReferentToken) && __org != null && __org.canBeEquals(res.getBeginToken().getPrevious().getReferent(), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
            res.setBeginToken(res.getBeginToken().getPrevious());
        correctDepAttrs(res, typ, false);
        if (typ.root != null && !typ.root.canBeNormalDep && dep.getNumber() == null) {
            if (typ.name != null && (typ.name.indexOf(" ") >= 0)) {
            }
            else if (dep.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) {
            }
            else if (typ.root.coeff > 0 && typ.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "управління") && typ.chars.isCapitalUpper()) {
            }
            else 
                return null;
        }
        return res;
    }

    private static com.pullenti.ner.ReferentToken tryAttachDepAfterOrg(com.pullenti.ner._org.internal.OrgItemTypeToken typ) {
        if (typ == null) 
            return null;
        com.pullenti.ner.Token t = typ.getBeginToken().getPrevious();
        if (t != null && t.isCharOf(":(")) 
            t = t.getPrevious();
        if (t == null) 
            return null;
        OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
        if (__org == null) 
            return null;
        com.pullenti.ner.Token t1 = typ.getEndToken();
        OrganizationReferent dep = new OrganizationReferent();
        dep.addType(typ, false);
        if (typ.name != null) 
            dep.addName(typ.name, true, null);
        if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(__org, dep, false)) 
            dep.setHigher(__org);
        else if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(dep, __org, false) && __org.getHigher() == null) {
            __org.setHigher(dep);
            t = t.getNext();
        }
        else 
            t = t.getNext();
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, t, t1, null);
        correctDepAttrs(res, typ, false);
        if (dep.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null) 
            return null;
        return res;
    }

    private static com.pullenti.ner.ReferentToken tryAttachDep(com.pullenti.ner._org.internal.OrgItemTypeToken typ, AttachType attachTyp, boolean specWordBefore) {
        if (typ == null) 
            return null;
        OrganizationReferent afterOrg = null;
        boolean afterOrgTemp = false;
        if ((typ.isNewlineAfter() && typ.name == null && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "курс")) && ((typ.root == null || !typ.root.canBeNormalDep))) {
            com.pullenti.ner.Token tt2 = typ.getEndToken().getNext();
            if (!specWordBefore || tt2 == null) 
                return null;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt2, false, false)) {
            }
            else 
                return null;
        }
        if (typ.getEndToken().getNext() != null && (typ.getEndToken().getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner._org.internal.OrgItemNameToken na0 = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(typ.getEndToken().getNext(), null, false, true);
            boolean inBr = false;
            if (na0 != null && ((na0.stdOrgNameNouns > 0 || na0.isStdName))) 
                specWordBefore = true;
            else {
                com.pullenti.ner.ReferentToken rt00 = tryAttachOrg(typ.getEndToken().getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
                if (rt00 == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(typ.getEndToken().getNext(), true, false)) {
                    rt00 = tryAttachOrg(typ.getEndToken().getNext().getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
                    if (rt00 != null) {
                        inBr = true;
                        if (rt00.getEndToken().getNext() == null) {
                        }
                        else if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt00.getEndToken(), true, null, false)) {
                        }
                        else if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt00.getEndToken().getNext(), true, null, false)) 
                            rt00.setEndToken(rt00.getEndToken().getNext());
                        else 
                            rt00 = null;
                        if (rt00 != null) 
                            rt00.setBeginToken(typ.getEndToken().getNext());
                    }
                }
                if (rt00 != null) {
                    afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt00.referent, OrganizationReferent.class);
                    specWordBefore = true;
                    afterOrgTemp = true;
                    if (afterOrg.containsProfile(OrgProfile.UNIT) && inBr) {
                        afterOrg = null;
                        afterOrgTemp = false;
                    }
                }
                else if ((typ.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && typ.getEndToken().getNext().chars.isAllUpper()) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> rrr = tryAttachOrgs(typ.getEndToken().getNext(), 0);
                    if (rrr != null && rrr.size() == 1) {
                        afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rrr.get(0).referent, OrganizationReferent.class);
                        specWordBefore = true;
                        afterOrgTemp = true;
                    }
                }
            }
        }
        if ((((((((typ.root != null && typ.root.canBeNormalDep && !specWordBefore) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "отдел") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "отделение")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "инспекция") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "филиал")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "аппарат") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "відділення")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "інспекція") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "філія")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "апарат") && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "совет")) && com.pullenti.unisharp.Utils.stringsNe(typ.typ, "рада") && (typ.typ.indexOf(' ') < 0)) && attachTyp != AttachType.EXTONTOLOGY) 
            return null;
        if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (!typ.getBeginToken().isValue("ОСП", null)) 
                return null;
        }
        OrganizationReferent dep = null;
        com.pullenti.ner.Token t0 = typ.getBeginToken();
        com.pullenti.ner.Token t1 = typ.getEndToken();
        dep = new OrganizationReferent();
        dep.addTypeStr(typ.typ.toLowerCase());
        dep.addProfile(OrgProfile.UNIT);
        if (typ.number != null) 
            dep.setNumber(typ.number);
        else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "курс") && !typ.isNewlineBefore()) {
            com.pullenti.ner.NumberToken nnn = com.pullenti.ner.core.NumberHelper.tryParseRomanBack(typ.getBeginToken().getPrevious());
            if (nnn != null && nnn.getIntValue() != null) {
                if (nnn.getIntValue() >= 1 && nnn.getIntValue() <= 6) {
                    dep.setNumber(nnn.getValue().toString());
                    t0 = nnn.getBeginToken();
                }
            }
        }
        com.pullenti.ner.Token t = typ.getEndToken().getNext();
        t1 = typ.getEndToken();
        if ((typ.number == null && ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "отдел") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "лаборатория"))) && (typ.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) && specWordBefore && (typ.getWhitespacesAfterCount() < 3)) {
            t1 = typ.getEndToken().getNext();
            dep.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue());
            typ.setCoef(typ.getCoef() + (2.0F));
        }
        if ((t instanceof com.pullenti.ner.TextToken) && afterOrg == null && (((com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "аппарат") || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "апарат") || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "совет")) || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "рада")))) {
            com.pullenti.ner.Token tt1 = t;
            if (tt1.isValue("ПРИ", null)) 
                tt1 = tt1.getNext();
            com.pullenti.ner.ReferentToken pr1 = t.kit.processReferent("PERSON", tt1, null);
            if (pr1 != null && com.pullenti.unisharp.Utils.stringsEq(pr1.referent.getTypeName(), "PERSONPROPERTY")) {
                dep.addSlot(OrganizationReferent.ATTR_OWNER, pr1.referent, true, 0);
                pr1.setDefaultLocalOnto(t.kit.processor);
                dep.addExtReferent(pr1);
                if (com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "рат")) 
                    return new com.pullenti.ner.ReferentToken(dep, t0, pr1.getEndToken(), null);
                t1 = pr1.getEndToken();
                t = t1.getNext();
            }
        }
        com.pullenti.ner.Referent beforeOrg = null;
        for (com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
            if (ttt.getReferent() instanceof OrganizationReferent) {
                beforeOrg = ttt.getReferent();
                break;
            }
            else if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                break;
            else if (ttt.chars.isLetter()) 
                break;
        }
        com.pullenti.ner._org.internal.OrgItemNumberToken num = null;
        java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemNameToken> names = null;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        com.pullenti.ner.core.BracketSequenceToken br00 = null;
        com.pullenti.ner._org.internal.OrgItemNameToken pr = null;
        com.pullenti.ner._org.internal.OrgItemTypeToken ty0;
        boolean isPureOrg = false;
        boolean isPureDep = false;
        if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "операционное управление") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "операційне управління")) 
            isPureDep = true;
        com.pullenti.ner.Token afterOrgTok = null;
        com.pullenti.ner.core.BracketSequenceToken brName = null;
        float coef = typ.getCoef();
        for (; t != null; t = t.getNext()) {
            if (afterOrgTemp) 
                break;
            if (t.isChar(':')) {
                if (t.isNewlineAfter()) 
                    break;
                if (names != null || typ.name != null) 
                    break;
                continue;
            }
            if ((((num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t, false, typ)))) != null) {
                if (t.isNewlineBefore() || typ.number != null) 
                    break;
                if (typ.root != null && !typ.root.canHasNumber) 
                    break;
                if ((typ.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && (typ.getWhitespacesBeforeCount() < 2)) {
                    com.pullenti.ner._org.internal.OrgItemTypeToken typ2 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(num.getEndToken().getNext(), true);
                    if (typ2 != null && typ2.root != null && ((typ2.root.canHasNumber || typ2.isDep()))) {
                        typ.setBeginToken(typ.getBeginToken().getPrevious());
                        typ.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(typ.getBeginToken(), com.pullenti.ner.NumberToken.class)).getValue();
                        dep.setNumber(typ.number);
                        num = null;
                        coef += (1.0F);
                        break;
                    }
                }
                t1 = num.getEndToken();
                t = num.getEndToken().getNext();
                break;
            }
            else if ((((ty0 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, true)))) != null && ty0.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL && !ty0.isDoubtRootWord()) 
                break;
            else if ((((br00 = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100)))) != null && names == null) {
                br = br00;
                if (!br.isQuoteType() || brName != null) 
                    br = null;
                else if (t.isNewlineBefore() && !specWordBefore) 
                    br = null;
                else {
                    boolean ok1 = true;
                    for (com.pullenti.ner.Token tt = br.getBeginToken(); tt != br.getEndToken(); tt = tt.getNext()) {
                        if (tt instanceof com.pullenti.ner.ReferentToken) {
                            ok1 = false;
                            break;
                        }
                    }
                    if (ok1) {
                        brName = br;
                        t1 = br.getEndToken();
                        t = t1.getNext();
                    }
                    else 
                        br = null;
                }
                break;
            }
            else {
                com.pullenti.ner.Referent r = t.getReferent();
                if ((r == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    dep.addGeoObject(t.getNext().getReferent());
                    t = t.getNext();
                    break;
                }
                if (r != null) {
                    if (r instanceof OrganizationReferent) {
                        afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, OrganizationReferent.class);
                        afterOrgTok = t;
                        break;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) && names != null && t.getPrevious() != null) {
                        boolean isName = false;
                        if (t.getPrevious().isValue("СУБЪЕКТ", null) || t.getPrevious().isValue("СУБЄКТ", null)) 
                            isName = true;
                        if (!isName) 
                            break;
                    }
                    else 
                        break;
                }
                com.pullenti.ner._org.internal.OrgItemEponymToken epo = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t, true);
                if (epo != null) {
                    for (String e : epo.eponyms) {
                        dep.addEponym(e);
                    }
                    t1 = epo.getEndToken();
                    break;
                }
                if (!typ.chars.isAllUpper() && t.chars.isAllUpper()) {
                    com.pullenti.ner._org.internal.OrgItemNameToken na1 = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, false);
                    if (na1 != null && ((na1.isStdName || na1.stdOrgNameNouns > 0))) {
                    }
                    else 
                        break;
                }
                if ((t instanceof com.pullenti.ner.NumberToken) && typ.root != null && dep.getNumber() == null) {
                    if (t.getWhitespacesBeforeCount() > 1 || !typ.root.canHasNumber) 
                        break;
                    if ((typ.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && (typ.getWhitespacesBeforeCount() < 2)) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ2 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true);
                        if (typ2 != null && typ2.root != null && ((typ2.root.canHasNumber || typ2.isDep()))) {
                            typ.setBeginToken(typ.getBeginToken().getPrevious());
                            dep.setNumber((typ.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(typ.getBeginToken(), com.pullenti.ner.NumberToken.class)).getValue()));
                            coef += (1.0F);
                            break;
                        }
                    }
                    dep.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    t1 = t;
                    continue;
                }
                if (isPureDep) 
                    break;
                if (!t.chars.isAllLower()) {
                    com.pullenti.ner.ReferentToken rtp = t.kit.processReferent("PERSON", t, null);
                    if (rtp != null && com.pullenti.unisharp.Utils.stringsEq(rtp.referent.getTypeName(), "PERSONPROPERTY")) {
                        if (rtp.getMorph().getCase().isGenitive() && t == typ.getEndToken().getNext() && (t.getWhitespacesBeforeCount() < 4)) 
                            rtp = null;
                    }
                    if (rtp != null) 
                        break;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "генеральный штаб") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "генеральний штаб")) {
                    com.pullenti.ner.ReferentToken rtp = t.kit.processReferent("PERSONPROPERTY", t, null);
                    if (rtp != null) 
                        break;
                }
                com.pullenti.ner._org.internal.OrgItemNameToken na = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, names == null);
                if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("РАЙОН", null)) 
                    na = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t.getNext().getNext(), pr, attachTyp == AttachType.EXTONTOLOGY, true);
                if (t.getMorph()._getClass().isPreposition() && ((t.isValue("ПРИ", null) || t.isValue("OF", null) || t.isValue("AT", null)))) {
                    if ((t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                        afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                        break;
                    }
                    com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t.getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
                    if (rt0 != null) {
                        afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                        afterOrgTemp = true;
                        break;
                    }
                }
                if (na == null) 
                    break;
                if (names == null) {
                    if (t.isNewlineBefore()) 
                        break;
                    if (com.pullenti.ner.core.NumberHelper.tryParseRoman(t) != null) 
                        break;
                    com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t, AttachType.NORMALAFTERDEP, null, false, -1);
                    if (rt0 != null) {
                        afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                        afterOrgTemp = true;
                        break;
                    }
                    names = new java.util.ArrayList<com.pullenti.ner._org.internal.OrgItemNameToken>();
                }
                else {
                    if (t.getWhitespacesBeforeCount() > 2 && !na.chars.equals(pr.chars)) 
                        break;
                    if (t.getNewlinesBeforeCount() > 2) 
                        break;
                }
                names.add(na);
                pr = na;
                t1 = (t = na.getEndToken());
            }
        }
        if (afterOrg == null) {
            for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ttt.getReferent(), OrganizationReferent.class);
                    break;
                }
                else if (!(ttt instanceof com.pullenti.ner.TextToken)) 
                    break;
                else if ((ttt.chars.isLetter() && !ttt.isValue("ПРИ", null) && !ttt.isValue("В", null)) && !ttt.isValue("OF", null) && !ttt.isValue("AT", null)) 
                    break;
            }
        }
        if ((afterOrg == null && t != null && t != t0) && (t.getWhitespacesBeforeCount() < 2)) {
            com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t, AttachType.NORMALAFTERDEP, null, false, -1);
            if (rt0 == null && (((t.isValue("В", null) || t.isValue("ПРИ", null) || t.isValue("OF", null)) || t.isValue("AT", null)))) 
                rt0 = tryAttachOrg(t.getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
            if (rt0 != null) {
                afterOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                afterOrgTemp = true;
            }
        }
        if (typ.chars.isCapitalUpper()) 
            coef += 0.5F;
        else if (!typ.chars.isAllLower() && typ.getBeginToken().chars.isCapitalUpper()) 
            coef += 0.5F;
        if (br != null && names == null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(nam)) {
                if (nam.length() > 100) 
                    return null;
                coef += (3.0F);
                com.pullenti.ner._org.internal.OrgItemNameToken na = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, false, true);
                if (na != null && na.isStdName) {
                    coef += (1.0F);
                    if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "группа")) {
                        dep.getSlots().clear();
                        typ.typ = "группа компаний";
                        isPureOrg = true;
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "група")) {
                        dep.getSlots().clear();
                        typ.typ = "група компаній";
                        isPureOrg = true;
                    }
                }
                if (isPureOrg) {
                    dep.addType(typ, false);
                    dep.addName(nam, true, null);
                }
                else 
                    dep.addNameStr(nam, typ, 1);
            }
        }
        else if (names != null) {
            int j;
            if (afterOrg != null || attachTyp == AttachType.HIGH) {
                coef += (3.0F);
                j = names.size();
            }
            else 
                for (j = 0; j < names.size(); j++) {
                    if (((names.get(j).isNewlineBefore() && !typ.isNewlineBefore() && !names.get(j).isAfterConjunction)) || ((!names.get(j).chars.equals(names.get(0).chars) && names.get(j).stdOrgNameNouns == 0))) 
                        break;
                    else {
                        if (names.get(j).chars.equals(typ.chars) && !typ.chars.isAllLower()) 
                            coef += ((float)0.5);
                        if (names.get(j).isStdName) 
                            coef += (2.0F);
                        if (names.get(j).stdOrgNameNouns > 0) {
                            if (!typ.chars.isAllLower()) 
                                coef += ((float)names.get(j).stdOrgNameNouns);
                        }
                    }
                }
            t1 = names.get(j - 1).getEndToken();
            String s = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                if (s.length() > 150 && attachTyp != AttachType.EXTONTOLOGY) 
                    return null;
                dep.addNameStr(s, typ, 1);
            }
            if (num != null) {
                dep.setNumber(num.number);
                coef += (2.0F);
                t1 = num.getEndToken();
            }
        }
        else if (num != null) {
            dep.setNumber(num.number);
            coef += (2.0F);
            t1 = num.getEndToken();
            if (typ != null && ((com.pullenti.unisharp.Utils.stringsEq(typ.typ, "лаборатория") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "лабораторія")))) 
                coef += (1.0F);
            if (typ.name != null) 
                dep.addNameStr(null, typ, 1);
        }
        else if (typ.name != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "курс") && Character.isDigit(typ.name.charAt(0))) 
                dep.setNumber(typ.name.substring(0, 0 + typ.name.indexOf(' ')));
            else 
                dep.addNameStr(null, typ, 1);
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "кафедра") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "факультет")) {
            t = typ.getEndToken().getNext();
            if (t != null && t.isChar(':')) 
                t = t.getNext();
            if ((t != null && (t instanceof com.pullenti.ner.TextToken) && !t.isNewlineBefore()) && t.getMorph()._getClass().isAdjective()) {
                if (typ.getMorph().getGender() == t.getMorph().getGender()) {
                    String s = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (s != null) {
                        dep.addNameStr((s + " " + typ.typ.toUpperCase()), null, 1);
                        coef += (2.0F);
                        t1 = t;
                    }
                }
            }
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "курс")) {
            t = typ.getEndToken().getNext();
            if (t != null && t.isChar(':')) 
                t = t.getNext();
            if (t != null && !t.isNewlineBefore()) {
                int val = 0;
                if (t instanceof com.pullenti.ner.NumberToken) {
                    if (!t.getMorph()._getClass().isNoun() && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        if (t.isWhitespaceAfter() || t.getNext().isCharOf(";,")) 
                            val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
                    }
                }
                else {
                    com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                    if (nt != null && nt.getIntValue() != null) {
                        val = nt.getIntValue();
                        t = nt.getEndToken();
                    }
                }
                if (val > 0 && (val < 8)) {
                    dep.setNumber(((Integer)val).toString());
                    t1 = t;
                    coef += (4.0F);
                }
            }
            if (dep.getNumber() == null) {
                t = typ.getBeginToken().getPrevious();
                if (t != null && !t.isNewlineAfter()) {
                    int val = 0;
                    if (t instanceof com.pullenti.ner.NumberToken) {
                        if (!t.getMorph()._getClass().isNoun() && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                            if (t.isWhitespaceBefore() || t.getPrevious().isCharOf(",")) 
                                val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
                        }
                    }
                    else {
                        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRomanBack(t);
                        if (nt != null && nt.getIntValue() != null) {
                            val = nt.getIntValue();
                            t = nt.getBeginToken();
                        }
                    }
                    if (val > 0 && (val < 8)) {
                        dep.setNumber(((Integer)val).toString());
                        t0 = t;
                        coef += (4.0F);
                    }
                }
            }
        }
        else if (typ.root != null && typ.root.canBeNormalDep && afterOrg != null) {
            coef += (3.0F);
            if (!afterOrgTemp) 
                dep.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(afterOrg, OrganizationReferent.class));
            else 
                dep.m_TempParentOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(afterOrg, OrganizationReferent.class);
            if (afterOrgTok != null) 
                t1 = afterOrgTok;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "генеральный штаб") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "генеральний штаб")) 
            coef += (3.0F);
        if (beforeOrg != null) 
            coef += (1.0F);
        if (afterOrg != null) {
            coef += (2.0F);
            if (((typ.name != null || ((typ.root != null && typ.root.canBeNormalDep)))) && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(afterOrg, OrganizationReferent.class), dep, false)) {
                coef += (1.0F);
                if (!typ.chars.isAllLower()) 
                    coef += 0.5F;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "курс") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "группа") || com.pullenti.unisharp.Utils.stringsEq(typ.typ, "група")) {
            if (dep.getNumber() == null) 
                coef = 0.0F;
            else if (com.pullenti.unisharp.Utils.stringsEq(typ.typ, "курс")) {
                int n;
                com.pullenti.unisharp.Outargwrapper<Integer> wrapn2549 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres2550 = com.pullenti.unisharp.Utils.parseInteger(dep.getNumber(), 0, null, wrapn2549);
                n = (wrapn2549.value != null ? wrapn2549.value : 0);
                if (inoutres2550) {
                    if (n > 0 && (n < 9)) 
                        coef += (2.0F);
                }
            }
        }
        if (t1.getNext() != null && t1.getNext().isChar('(')) {
            com.pullenti.ner.Token ttt = t1.getNext().getNext();
            if ((ttt != null && ttt.getNext() != null && ttt.getNext().isChar(')')) && (ttt instanceof com.pullenti.ner.TextToken)) {
                if (dep.getNameVars().containsKey(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.TextToken.class)).term)) {
                    coef += (2.0F);
                    dep.addName(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.TextToken.class)).term, true, ttt);
                    t1 = ttt.getNext();
                }
            }
        }
        com.pullenti.ner._org.internal.OrgItemEponymToken ep = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
        if (ep != null) {
            coef += (2.0F);
            for (String e : ep.eponyms) {
                dep.addEponym(e);
            }
            t1 = ep.getEndToken();
        }
        if (brName != null) {
            String str1 = com.pullenti.ner.core.MiscHelper.getTextValue(brName.getBeginToken().getNext(), brName.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
            if (str1 != null) 
                dep.addName(str1, true, null);
        }
        if (dep.getSlots().size() == 0) 
            return null;
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, t0, t1, null);
        correctDepAttrs(res, typ, afterOrgTemp);
        if (dep.getNumber() != null) 
            coef += (2.0F);
        if (isPureDep) 
            coef += (2.0F);
        if (specWordBefore) {
            if (dep.findSlot(OrganizationReferent.ATTR_NAME, null, true) != null) 
                coef += (2.0F);
        }
        if ((typ != null && typ.root != null && typ.root.canBeNormalDep) && typ.name != null && typ.name.indexOf(' ') > 0) 
            coef += (4.0F);
        if (coef > 3 || attachTyp == AttachType.EXTONTOLOGY) 
            return res;
        else 
            return null;
    }

    private static void correctDepAttrs(com.pullenti.ner.ReferentToken res, com.pullenti.ner._org.internal.OrgItemTypeToken typ, boolean afterTempOrg) {
        com.pullenti.ner.Token t0 = res.getBeginToken();
        OrganizationReferent dep = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(res.referent, OrganizationReferent.class);
        if ((((((((typ != null && typ.root != null && typ.root.canHasNumber)) || dep.getTypes().contains("офис") || dep.getTypes().contains("офіс")) || dep.getTypes().contains("отдел") || dep.getTypes().contains("отделение")) || dep.getTypes().contains("инспекция") || dep.getTypes().contains("лаборатория")) || dep.getTypes().contains("управление") || dep.getTypes().contains("управління")) || dep.getTypes().contains("відділ") || dep.getTypes().contains("відділення")) || dep.getTypes().contains("інспекція") || dep.getTypes().contains("лабораторія")) {
            if (((t0.getPrevious() instanceof com.pullenti.ner.NumberToken) && (t0.getWhitespacesBeforeCount() < 3) && !t0.getPrevious().getMorph()._getClass().isNoun()) && t0.getPrevious().isWhitespaceBefore()) {
                String nn = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue().toString();
                if (dep.getNumber() == null || com.pullenti.unisharp.Utils.stringsEq(dep.getNumber(), nn)) {
                    dep.setNumber(nn);
                    t0 = t0.getPrevious();
                    res.setBeginToken(t0);
                }
            }
            if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(res.getEndToken().getNext()) != null && (res.getEndToken().getWhitespacesAfterCount() < 3) && dep.getNumber() == null) {
                com.pullenti.ner._org.internal.OrgItemNumberToken num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(res.getEndToken().getNext(), false, typ);
                if (num != null) {
                    dep.setNumber(num.number);
                    res.setEndToken(num.getEndToken());
                }
            }
        }
        if (dep.getTypes().contains("управление") || dep.getTypes().contains("департамент") || dep.getTypes().contains("управління")) {
            for (com.pullenti.ner.Slot s : dep.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class);
                    if (g.isState() && com.pullenti.unisharp.Utils.stringsEq(g.getAlpha2(), "RU")) {
                        dep.getSlots().remove(s);
                        break;
                    }
                }
            }
        }
        com.pullenti.ner.Token t1 = res.getEndToken();
        if (t1.getNext() == null || afterTempOrg) 
            return;
        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
        if (br != null && (t1.getWhitespacesAfterCount() < 2) && br.isQuoteType()) {
            Object g = isGeo(br.getBeginToken().getNext(), false);
            if (g instanceof com.pullenti.ner.ReferentToken) {
                if (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(g, com.pullenti.ner.ReferentToken.class)).getEndToken().getNext() == br.getEndToken()) {
                    dep.addGeoObject(g);
                    t1 = res.setEndToken(br.getEndToken());
                }
            }
            else if ((g instanceof com.pullenti.ner.Referent) && br.getBeginToken().getNext().getNext() == br.getEndToken()) {
                dep.addGeoObject(g);
                t1 = res.setEndToken(br.getEndToken());
            }
            else if (br.getBeginToken().getNext().isValue("О", null) || br.getBeginToken().getNext().isValue("ОБ", null)) {
            }
            else {
                String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) {
                    dep.addName(nam, true, br.getBeginToken().getNext());
                    t1 = res.setEndToken(br.getEndToken());
                }
            }
        }
        boolean prep = false;
        if (t1.getNext() != null) {
            if (t1.getNext().getMorph()._getClass().isPreposition()) {
                if (t1.getNext().isValue("В", null) || t1.getNext().isValue("ПО", null)) {
                    t1 = t1.getNext();
                    prep = true;
                }
            }
            if (t1.getNext() != null && (t1.getNext().getWhitespacesBeforeCount() < 3)) {
                if (t1.getNext().isValue("НА", null) && t1.getNext().getNext() != null && t1.getNext().getNext().isValue("ТРАНСПОРТ", null)) 
                    res.setEndToken((t1 = t1.getNext().getNext()));
            }
        }
        for (int k = 0; k < 2; k++) {
            if (t1.getNext() == null) 
                return;
            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            boolean ge = false;
            if (_geo != null) {
                if (!dep.addGeoObject(_geo)) 
                    return;
                res.setEndToken(t1.getNext());
                ge = true;
            }
            else {
                com.pullenti.ner.ReferentToken rgeo = t1.kit.processReferent("GEO", t1.getNext(), null);
                if (rgeo != null) {
                    if (!rgeo.getMorph()._getClass().isAdjective()) {
                        if (!dep.addGeoObject(rgeo)) 
                            return;
                        res.setEndToken(rgeo.getEndToken());
                        ge = true;
                    }
                }
            }
            if (!ge) 
                return;
            t1 = res.getEndToken();
            if (t1.getNext() == null) 
                return;
            boolean isAnd = false;
            if (t1.getNext().isAnd()) 
                t1 = t1.getNext();
            if (t1 == null) 
                return;
        }
    }

    private static com.pullenti.ner.ReferentToken attachGlobalOrg(com.pullenti.ner.Token t, AttachType attachTyp, Object extGeo) {
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLatinLetter()) {
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
                com.pullenti.ner.ReferentToken res11 = attachGlobalOrg(t.getNext(), attachTyp, extGeo);
                if (res11 != null) {
                    res11.setBeginToken(t);
                    return res11;
                }
            }
        }
        com.pullenti.ner.ReferentToken rt00 = tryAttachPoliticParty(t, true);
        if (rt00 != null) 
            return rt00;
        if (!(t instanceof com.pullenti.ner.TextToken)) {
            if (t != null && t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI")) {
                com.pullenti.ner.ReferentToken rt = attachGlobalOrg(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(), attachTyp, null);
                if (rt != null && rt.getEndChar() == t.getEndChar()) {
                    rt.setBeginToken(rt.setEndToken(t));
                    return rt;
                }
            }
            return null;
        }
        String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ВС")) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("ПРЕЗИДИУМ", null) || t.getPrevious().isValue("ПЛЕНУМ", null) || t.getPrevious().isValue("СЕССИЯ", null)) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), false, AttachType.NORMAL, true);
                    if (org00.getGeoObjects().size() == 1 && com.pullenti.unisharp.Utils.stringsEq(org00.getGeoObjects().get(0).getAlpha2(), "SU")) {
                        org00.addName("ВЕРХОВНЫЙ СОВЕТ", true, null);
                        org00.addName("ВС", true, null);
                        org00.addTypeStr("совет");
                        org00.addProfile(OrgProfile.STATE);
                    }
                    else {
                        org00.addName("ВЕРХОВНЫЙ СУД", true, null);
                        org00.addName("ВС", true, null);
                        org00.addTypeStr("суд");
                        org00.addProfile(OrgProfile.JUSTICE);
                    }
                    return new com.pullenti.ner.ReferentToken(org00, t, (te != null ? te : t), null);
                }
            }
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                boolean isVc = false;
                com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
                if (t.getPrevious() != null && (t.getPrevious().getReferent() instanceof OrganizationReferent) && ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getPrevious().getReferent(), OrganizationReferent.class)).getKind() == OrganizationKind.MILITARY) 
                    isVc = true;
                else if (ad != null) {
                    for (com.pullenti.ner.Referent r : ad.getReferents()) {
                        if (r.findSlot(OrganizationReferent.ATTR_NAME, "ВООРУЖЕННЫЕ СИЛЫ", true) != null) {
                            isVc = true;
                            break;
                        }
                    }
                }
                if (isVc) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    org00.addName("ВООРУЖЕННЫЕ СИЛЫ", true, null);
                    org00.addName("ВС", true, null);
                    org00.addTypeStr("армия");
                    org00.addProfile(OrgProfile.ARMY);
                    com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), false, AttachType.NORMAL, true);
                    return new com.pullenti.ner.ReferentToken(org00, t, (te != null ? te : t), null);
                }
            }
        }
        if ((t.chars.isAllUpper() && ((com.pullenti.unisharp.Utils.stringsEq(term, "АН") || com.pullenti.unisharp.Utils.stringsEq(term, "ВАС") || com.pullenti.unisharp.Utils.stringsEq(term, "АМН"))) && t.getNext() != null) && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
            OrganizationReferent org00 = new OrganizationReferent();
            if (com.pullenti.unisharp.Utils.stringsEq(term, "АН")) {
                org00.addName("АКАДЕМИЯ НАУК", true, null);
                org00.addTypeStr("академия");
                org00.addProfile(OrgProfile.SCIENCE);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(term, "АМН")) {
                org00.addName("АКАДЕМИЯ МЕДИЦИНСКИХ НАУК", true, null);
                org00.addTypeStr("академия");
                org00.addProfile(OrgProfile.SCIENCE);
            }
            else {
                org00.addName("ВЫСШИЙ АРБИТРАЖНЫЙ СУД", true, null);
                org00.addName("ВАС", true, null);
                org00.addTypeStr("суд");
                org00.addProfile(OrgProfile.JUSTICE);
            }
            com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), false, AttachType.NORMAL, true);
            return new com.pullenti.ner.ReferentToken(org00, t, (te != null ? te : t), null);
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ГД") && t.getPrevious() != null) {
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSONPROPERTY", t.getPrevious(), null);
            if (rt != null && rt.referent != null && com.pullenti.unisharp.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY")) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addName("ГОСУДАРСТВЕННАЯ ДУМА", true, null);
                org00.addName("ГОСДУМА", true, null);
                org00.addName("ГД", true, null);
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), false, AttachType.NORMAL, true);
                return new com.pullenti.ner.ReferentToken(org00, t, (te != null ? te : t), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "МЮ")) {
            boolean ok = false;
            if ((t.getPrevious() != null && t.getPrevious().isValue("В", null) && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isValue("ЗАРЕГИСТРИРОВАТЬ", null)) 
                ok = true;
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                ok = true;
            if (ok) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("министерство");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("МИНИСТЕРСТВО ЮСТИЦИИ", true, null);
                org00.addName("МИНЮСТ", true, null);
                com.pullenti.ner.Token t1 = t;
                if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    t1 = t.getNext();
                    org00.addGeoObject(t1.getReferent());
                }
                return new com.pullenti.ner.ReferentToken(org00, t, t1, null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ФС")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("ФЕДЕРАЛЬНОЕ СОБРАНИЕ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "МП")) {
            com.pullenti.ner.Token tt0 = t.getPrevious();
            if (tt0 != null && tt0.isChar('(')) 
                tt0 = tt0.getPrevious();
            OrganizationReferent org0 = null;
            boolean prev = false;
            if (tt0 != null) {
                org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt0.getReferent(), OrganizationReferent.class);
                if (org0 != null) 
                    prev = true;
            }
            if (t.getNext() != null && org0 == null) 
                org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
            if (org0 != null && org0.getKind() == OrganizationKind.CHURCH) {
                OrganizationReferent glob = new OrganizationReferent();
                glob.addTypeStr("патриархия");
                glob.addName("МОСКОВСКАЯ ПАТРИАРХИЯ", true, null);
                glob.setHigher(org0);
                glob.addProfile(OrgProfile.RELIGION);
                com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(glob, t, t, null);
                if (!prev) 
                    res.setEndToken(t.getNext());
                else {
                    res.setBeginToken(tt0);
                    if (tt0 != t.getPrevious() && res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar(')')) 
                        res.setEndToken(res.getEndToken().getNext());
                }
                return res;
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ГШ")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent) && ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class)).getKind() == OrganizationKind.MILITARY) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("генеральный штаб");
                org00.addProfile(OrgProfile.ARMY);
                org00.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class));
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ЗС")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("ЗАКОНОДАТЕЛЬНОЕ СОБРАНИЕ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "СФ")) {
            t.setInnerBool(true);
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("совет");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("СОВЕТ ФЕДЕРАЦИИ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            if (t.getNext() != null) {
                if (t.getNext().isValue("ФС", null) || (((t.getNext().getReferent() instanceof OrganizationReferent) && t.getNext().getReferent().findSlot(OrganizationReferent.ATTR_NAME, "ФЕДЕРАЛЬНОЕ СОБРАНИЕ", true) != null))) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    org00.addTypeStr("совет");
                    org00.addProfile(OrgProfile.STATE);
                    org00.addName("СОВЕТ ФЕДЕРАЦИИ", true, null);
                    return new com.pullenti.ner.ReferentToken(org00, t, t, null);
                }
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "ФК")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("казначейство");
                org00.addProfile(OrgProfile.FINANCE);
                org00.addName("ФЕДЕРАЛЬНОЕ КАЗНАЧЕЙСТВО", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            if (attachTyp == AttachType.NORMALAFTERDEP) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("казначейство");
                org00.addProfile(OrgProfile.FINANCE);
                org00.addName("ФЕДЕРАЛЬНОЕ КАЗНАЧЕЙСТВО", true, null);
                return new com.pullenti.ner.ReferentToken(org00, t, t, null);
            }
        }
        if (t.chars.isAllUpper() && ((com.pullenti.unisharp.Utils.stringsEq(term, "СК") || com.pullenti.unisharp.Utils.stringsEq(term, "CK")))) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                boolean ok1 = false;
                com.pullenti.ner.core.AnalyzerData ad = t.kit.getAnalyzerDataByAnalyzerName("ORGANIZATION");
                for (com.pullenti.ner.Referent e : ad.getReferents()) {
                    for (com.pullenti.ner.Slot s : e.getSlots()) {
                        if (s.getValue() instanceof String) {
                            if ((((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class)).toUpperCase().indexOf("СЛЕДСТВЕННЫЙ КОМИТЕТ") >= 0)) {
                                ok1 = true;
                                break;
                            }
                        }
                    }
                    if (ok1) 
                        break;
                }
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt instanceof com.pullenti.ner.TextToken) {
                        if (ok1) 
                            break;
                        if (tt.isCommaAnd()) 
                            continue;
                        if (tt instanceof com.pullenti.ner.NumberToken) 
                            continue;
                        if (!tt.chars.isLetter()) 
                            continue;
                        if ((tt.isValue("ЧАСТЬ", null) || tt.isValue("СТАТЬЯ", null) || tt.isValue("ПУНКТ", null)) || tt.isValue("СТ", null) || tt.isValue("П", null)) 
                            return null;
                        break;
                    }
                }
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("комитет");
                org00.addProfile(OrgProfile.UNIT);
                org00.addName("СЛЕДСТВЕННЫЙ КОМИТЕТ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt1 = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGS.tryAttach(t.getNext(), null, false);
            if (gt1 == null && t.getNext() != null && t.kit.baseLanguage.isUa()) 
                gt1 = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t.getNext(), null, false);
            boolean ok = false;
            if (gt1 != null && gt1.get(0).item.referent.findSlot(OrganizationReferent.ATTR_NAME, "МВД", true) != null) 
                ok = true;
            if (ok) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("комитет");
                org00.addName("СЛЕДСТВЕННЫЙ КОМИТЕТ", true, null);
                org00.addProfile(OrgProfile.UNIT);
                return new com.pullenti.ner.ReferentToken(org00, t, t, null);
            }
        }
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, true);
        if (gt == null) 
            gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, false);
        if (gt == null && t != null && t.kit.baseLanguage.isUa()) {
            gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, true);
            if (gt == null) 
                gt = com.pullenti.ner._org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, false);
        }
        if (gt == null) 
            return null;
        for (com.pullenti.ner.core.IntOntologyToken ot : gt) {
            OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ot.item.referent, OrganizationReferent.class);
            if (org0 == null) 
                continue;
            if (ot.getBeginToken() == ot.getEndToken()) {
                if (gt.size() == 1) {
                    if ((ot.getBeginToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ot.getBeginToken(), com.pullenti.ner.TextToken.class)).term, "МГТУ")) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(ot.getBeginToken(), false);
                        if (ty != null) 
                            continue;
                    }
                }
                else {
                    com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
                    if (ad == null) 
                        return null;
                    boolean ok = false;
                    for (com.pullenti.ner.Referent o : ad.getReferents()) {
                        if (o.canBeEquals(org0, com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) 
                        return null;
                }
            }
            if (((t.chars.isAllLower() && attachTyp != AttachType.EXTONTOLOGY && extGeo == null) && !t.isValue("МИД", null) && !org0._typesContains("факультет")) && org0.getKind() != OrganizationKind.JUSTICE) {
                if (ot.getBeginToken() == ot.getEndToken()) 
                    continue;
                if (ot.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    continue;
                com.pullenti.ner._org.internal.OrgItemTypeToken tyty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, true);
                if (tyty != null && tyty.getEndToken() == ot.getEndToken()) 
                    continue;
                if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                }
                else if (com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t.getPrevious())) {
                }
                else 
                    continue;
            }
            if ((ot.getBeginToken() == ot.getEndToken() && (t.getLengthChar() < 6) && !t.chars.isAllUpper()) && !t.chars.isLastLower()) {
                if (org0.findSlot(OrganizationReferent.ATTR_NAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, true) == null) {
                    if (t.isValue("МИД", null)) {
                    }
                    else 
                        continue;
                }
                else if (t.chars.isAllLower()) 
                    continue;
                else if (t.getLengthChar() < 3) 
                    continue;
                else if (t.getLengthChar() == 4) {
                    boolean hasVow = false;
                    for (char ch : ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.toCharArray()) {
                        if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                            hasVow = true;
                    }
                    if (hasVow) 
                        continue;
                }
            }
            if (ot.getBeginToken() == ot.getEndToken() && com.pullenti.unisharp.Utils.stringsEq(term, "МЭР")) 
                continue;
            if (ot.getBeginToken() == ot.getEndToken()) {
                if (t.getPrevious() == null || t.isWhitespaceBefore()) {
                }
                else if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && ((t.getPrevious().isCharOf(",:") || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)))) {
                }
                else if (t.getMorphClassInDictionary().isUndefined() && t.chars.isCapitalUpper()) {
                }
                else 
                    continue;
                if (t.getNext() == null || t.isWhitespaceAfter()) {
                }
                else if ((t.getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().isCharOf(",.") || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext(), false, null, false)))) {
                }
                else if (t.getMorphClassInDictionary().isUndefined() && t.chars.isCapitalUpper()) {
                }
                else 
                    continue;
                if (t instanceof com.pullenti.ner.TextToken) {
                    boolean hasName = false;
                    for (String n : org0.getNames()) {
                        if (t.isValue(n, null)) {
                            hasName = true;
                            break;
                        }
                    }
                    if (!hasName) 
                        continue;
                    if (t.getLengthChar() < 3) {
                        boolean ok1 = true;
                        if (t.getNext() != null && !t.isNewlineBefore()) {
                            if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t.getNext()) != null) 
                                ok1 = false;
                            else if (t.getNext().isHiphen() || (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                                ok1 = false;
                        }
                        if (!ok1) 
                            continue;
                    }
                }
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("TRANSPORT", t, null);
                if (rt != null) 
                    continue;
            }
            OrganizationReferent __org = null;
            if (t instanceof com.pullenti.ner.TextToken) {
                if ((t.isValue("ДЕПАРТАМЕНТ", null) || t.isValue("КОМИТЕТ", "КОМІТЕТ") || t.isValue("МИНИСТЕРСТВО", "МІНІСТЕРСТВО")) || t.isValue("КОМИССИЯ", "КОМІСІЯ")) {
                    com.pullenti.ner._org.internal.OrgItemNameToken nnn = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, true, true);
                    if (nnn != null && nnn.getEndChar() > ot.getEndChar()) {
                        __org = new OrganizationReferent();
                        for (OrgProfile p : org0.getProfiles()) {
                            __org.addProfile(p);
                        }
                        __org.addTypeStr(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).lemma.toLowerCase());
                        __org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, nnn.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE), true, null);
                        ot.setEndToken(nnn.getEndToken());
                    }
                }
            }
            if (__org == null) {
                __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(org0.clone(), OrganizationReferent.class);
                if (__org.getGeoObjects().size() > 0) {
                    for (com.pullenti.ner.Slot s : __org.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            com.pullenti.ner.Referent gg = ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class)).clone();
                            gg.getOccurrence().clear();
                            com.pullenti.ner.ReferentToken rtg = new com.pullenti.ner.ReferentToken(gg, t, t, null);
                            rtg.data = t.kit.getAnalyzerDataByAnalyzerName("GEO");
                            __org.getSlots().remove(s);
                            __org.addGeoObject(rtg);
                            break;
                        }
                    }
                }
                __org.addName(ot.termin.getCanonicText(), true, null);
            }
            if (extGeo != null) 
                __org.addGeoObject(extGeo);
            __org.isFromGlobalOntos = true;
            for (com.pullenti.ner.Token tt = ot.getBeginToken(); tt != null && (tt.getEndChar() < ot.getEndChar()); tt = tt.getNext()) {
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    __org.addGeoObject(tt);
                    break;
                }
            }
            if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesBeforeCount() < 2) && t.getPrevious().getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.ReferentToken gg = t.kit.processReferent("GEO", t.getPrevious(), null);
                if (gg != null && gg.getMorph()._getClass().isAdjective()) {
                    t = t.getPrevious();
                    __org.addGeoObject(gg);
                }
            }
            com.pullenti.ner.Token t1 = null;
            if (!org0.getTypes().contains("академия") && attachTyp != AttachType.NORMALAFTERDEP && attachTyp != AttachType.EXTONTOLOGY) 
                t1 = attachTailAttributes(__org, ot.getEndToken().getNext(), false, AttachType.NORMAL, true);
            else if ((((((org0.getTypes().contains("министерство") || org0.getTypes().contains("парламент") || org0.getTypes().contains("совет")) || org0.getKind() == OrganizationKind.SCIENCE || org0.getKind() == OrganizationKind.GOVENMENT) || org0.getKind() == OrganizationKind.STUDY || org0.getKind() == OrganizationKind.JUSTICE) || org0.getKind() == OrganizationKind.MILITARY)) && (ot.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(ot.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (_geo != null && _geo.isState()) {
                    __org.addGeoObject(_geo);
                    t1 = ot.getEndToken().getNext();
                }
            }
            if (t1 == null) 
                t1 = ot.getEndToken();
            com.pullenti.ner._org.internal.OrgItemEponymToken epp = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
            if (epp != null) {
                boolean exi = false;
                for (String v : epp.eponyms) {
                    if (__org.findSlot(OrganizationReferent.ATTR_EPONYM, v, true) != null) {
                        exi = true;
                        break;
                    }
                }
                if (!exi) {
                    for (int i = __org.getSlots().size() - 1; i >= 0; i--) {
                        if (com.pullenti.unisharp.Utils.stringsEq(__org.getSlots().get(i).getTypeName(), OrganizationReferent.ATTR_EPONYM)) 
                            __org.getSlots().remove(i);
                    }
                    for (String vv : epp.eponyms) {
                        __org.addEponym(vv);
                    }
                }
                t1 = epp.getEndToken();
            }
            if (t1.getWhitespacesAfterCount() < 2) {
                com.pullenti.ner._org.internal.OrgItemTypeToken typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false);
                if (typ != null) {
                    if (com.pullenti.ner._org.internal.OrgItemTypeToken.isTypeAccords(__org, typ)) {
                        if (typ.chars.isLatinLetter() && typ.root != null && typ.root.canBeNormalDep) {
                        }
                        else {
                            com.pullenti.ner.ReferentToken rrr = OrganizationAnalyzer.processReferentStat(t1.getNext(), null);
                            if (rrr != null && !rrr.referent.canBeEquals(__org, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                            }
                            else {
                                __org.addType(typ, false);
                                t1 = typ.getEndToken();
                            }
                        }
                    }
                }
            }
            if (__org.getGeoObjects().size() == 0 && t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.ReferentToken grt = t.kit.processReferent("GEO", t.getPrevious(), null);
                if (grt != null && grt.getEndToken().getNext() == t) {
                    __org.addGeoObject(grt);
                    t = t.getPrevious();
                }
            }
            if (__org.findSlot(OrganizationReferent.ATTR_NAME, "ВТБ", true) != null && t1.getNext() != null) {
                com.pullenti.ner.Token tt = t1.getNext();
                if (tt.isHiphen() && tt.getNext() != null) 
                    tt = tt.getNext();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    __org.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                    t1 = tt;
                }
            }
            if (!t.isWhitespaceBefore() && !t1.isWhitespaceAfter()) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), true, null, false)) {
                    t = t.getPrevious();
                    t1 = t1.getNext();
                }
            }
            return new com.pullenti.ner.ReferentToken(__org, t, t1, null);
        }
        return null;
    }

    private static com.pullenti.ner.MetaToken _tryAttachOrgMedTyp(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        String s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        if (((t != null && com.pullenti.unisharp.Utils.stringsEq(s, "Г") && t.getNext() != null) && t.getNext().isCharOf("\\/.") && t.getNext().getNext() != null) && t.getNext().getNext().isValue("Б", null)) {
            com.pullenti.ner.Token t1 = t.getNext().getNext();
            if (t.getNext().isChar('.') && t1.getNext() != null && t1.getNext().isChar('.')) 
                t1 = t1.getNext();
            return com.pullenti.ner.MetaToken._new2552(t, t1, "городская больница", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.FEMINIE));
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(s, "ИН") && t.getNext() != null && t.getNext().isHiphen()) && t.getNext().getNext() != null && t.getNext().getNext().isValue("Т", null)) 
            return com.pullenti.ner.MetaToken._new2552(t, t.getNext().getNext(), "институт", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.MASCULINE));
        if ((com.pullenti.unisharp.Utils.stringsEq(s, "Б") && t.getNext() != null && t.getNext().isHiphen()) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().getNext().isValue("ЦА", null) || t.getNext().getNext().isValue("ЦУ", null)))) 
            return com.pullenti.ner.MetaToken._new2552(t, t.getNext().getNext(), "больница", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.FEMINIE));
        if (com.pullenti.unisharp.Utils.stringsEq(s, "ГКБ")) 
            return com.pullenti.ner.MetaToken._new2552(t, t, "городская клиническая больница", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("ПОЛИКЛИНИКА", null)) 
            return com.pullenti.ner.MetaToken._new2552(t, t, "поликлиника", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("БОЛЬНИЦА", null)) 
            return com.pullenti.ner.MetaToken._new2552(t, t, "больница", com.pullenti.ner.MorphCollection._new2551(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("ДЕТСКИЙ", null)) {
            com.pullenti.ner.MetaToken mt = _tryAttachOrgMedTyp(t.getNext());
            if (mt != null) {
                mt.setBeginToken(t);
                mt.tag = ((mt.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE ? "детская" : "детский") + " " + mt.tag);
                return mt;
            }
        }
        return null;
    }

    private static com.pullenti.ner.ReferentToken tryAttachOrgMed(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.getPrevious() == null || t.getPrevious().getPrevious() == null) 
            return null;
        if ((t.getPrevious().getMorph()._getClass().isPreposition() && t.getPrevious().getPrevious().isValue("ДОСТАВИТЬ", null)) || t.getPrevious().getPrevious().isValue("ПОСТУПИТЬ", null)) {
        }
        else 
            return null;
        if (t.isValue("ТРАВМПУНКТ", null)) 
            t = t.getNext();
        else if (t.isValue("ТРАВМ", null)) {
            if ((t.getNext() != null && t.getNext().isChar('.') && t.getNext().getNext() != null) && t.getNext().getNext().isValue("ПУНКТ", null)) 
                t = t.getNext().getNext().getNext();
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.MetaToken tt = _tryAttachOrgMedTyp(t.getNext());
            if (tt != null) {
                OrganizationReferent org1 = new OrganizationReferent();
                org1.addTypeStr(((String)com.pullenti.unisharp.Utils.cast(tt.tag, String.class)).toLowerCase());
                org1.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                return new com.pullenti.ner.ReferentToken(org1, t, tt.getEndToken(), null);
            }
        }
        com.pullenti.ner.MetaToken typ = _tryAttachOrgMedTyp(t);
        String adj = null;
        if (typ == null && t.chars.isCapitalUpper() && t.getMorph()._getClass().isAdjective()) {
            typ = _tryAttachOrgMedTyp(t.getNext());
            if (typ != null) 
                adj = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, typ.getMorph().getGender(), false);
        }
        if (typ == null) 
            return null;
        OrganizationReferent __org = new OrganizationReferent();
        String s = (String)com.pullenti.unisharp.Utils.cast(typ.tag, String.class);
        __org.addTypeStr(s.toLowerCase());
        if (adj != null) 
            __org.addName((adj + " " + s.toUpperCase()), true, null);
        com.pullenti.ner.Token t1 = typ.getEndToken();
        com.pullenti.ner._org.internal.OrgItemEponymToken epo = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
        if (epo != null) {
            for (String v : epo.eponyms) {
                __org.addEponym(v);
            }
            t1 = epo.getEndToken();
        }
        if (t1.getNext() instanceof com.pullenti.ner.TextToken) {
            if (t1.getNext().isValue("СКЛИФОСОФСКОГО", null) || t1.getNext().isValue("СЕРБСКОГО", null) || t1.getNext().isValue("БОТКИНА", null)) {
                __org.addEponym(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)).term);
                t1 = t1.getNext();
            }
        }
        com.pullenti.ner._org.internal.OrgItemNumberToken num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, null);
        if (num != null) {
            __org.setNumber(num.number);
            t1 = num.getEndToken();
        }
        if (__org.getSlots().size() > 1) 
            return new com.pullenti.ner.ReferentToken(__org, t, t1, null);
        return null;
    }

    private static com.pullenti.ner.ReferentToken tryAttachPropNames(com.pullenti.ner.Token t) {
        com.pullenti.ner.ReferentToken rt = _tryAttachOrgSportAssociations(t);
        if (rt == null) 
            rt = _tryAttachOrgNames(t);
        if (rt == null) 
            return null;
        com.pullenti.ner.Token t0 = rt.getBeginToken().getPrevious();
        if ((t0 instanceof com.pullenti.ner.TextToken) && (t0.getWhitespacesAfterCount() < 2) && t0.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.ReferentToken rt0 = t0.kit.processReferent("GEO", t0, null);
            if (rt0 != null && rt0.getMorph()._getClass().isAdjective()) {
                rt.setBeginToken(rt0.getBeginToken());
                ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).addGeoObject(rt0);
            }
        }
        if (rt.getEndToken().getWhitespacesAfterCount() < 2) {
            com.pullenti.ner.Token tt1 = attachTailAttributes((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class), rt.getEndToken().getNext(), true, AttachType.NORMAL, true);
            if (tt1 != null) 
                rt.setEndToken(tt1);
        }
        return rt;
    }

    private static com.pullenti.ner.ReferentToken _tryAttachOrgNames(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        com.pullenti.ner.Token tName1 = null;
        OrgProfile prof = OrgProfile.UNDEFINED;
        OrgProfile prof2 = OrgProfile.UNDEFINED;
        String typ = null;
        boolean ok = false;
        com.pullenti.ner.ReferentToken uri = null;
        if (!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isLetter()) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                if ((((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 15)))) != null) 
                    t = t0.getNext();
                else 
                    return null;
            }
            else if (t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI")) {
                com.pullenti.ner.Referent r = t.getReferent();
                String s = r.getStringValue("SCHEME");
                if (com.pullenti.unisharp.Utils.stringsEq(s, "HTTP")) {
                    prof = OrgProfile.MEDIA;
                    tName1 = t;
                }
            }
            else if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && t.chars.isLetter()) {
                if ((t.getNext() != null && (t.getNext().getWhitespacesAfterCount() < 3) && t.getNext().chars.isLatinLetter()) && ((t.getNext().isValue("POST", null) || t.getNext().isValue("TODAY", null)))) {
                    tName1 = t.getNext();
                    if (_isStdPressEnd(tName1)) 
                        prof = OrgProfile.MEDIA;
                }
                else 
                    return null;
            }
            else 
                return null;
        }
        else if (t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ИА")) {
            prof = OrgProfile.MEDIA;
            t = t.getNext();
            typ = "информационное агенство";
            if (t == null || t.getWhitespacesBeforeCount() > 2) 
                return null;
            com.pullenti.ner.ReferentToken re = _tryAttachOrgNames(t);
            if (re != null) {
                re.setBeginToken(t0);
                ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(re.referent, OrganizationReferent.class)).addTypeStr(typ);
                return re;
            }
            if (t.chars.isLatinLetter()) {
                com.pullenti.ner._org.internal.OrgItemEngItem nam = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(t, false);
                if (nam != null) {
                    ok = true;
                    tName1 = nam.getEndToken();
                }
                else {
                    com.pullenti.ner._org.internal.OrgItemNameToken nam1 = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t, null, false, true);
                    if (nam1 != null) {
                        ok = true;
                        tName1 = nam1.getEndToken();
                    }
                }
            }
        }
        else if (((t.chars.isLatinLetter() && t.getNext() != null && t.getNext().isChar('.')) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext() != null) && t.getNext().getNext().chars.isLatinLetter()) {
            tName1 = t.getNext().getNext();
            prof = OrgProfile.MEDIA;
            if (tName1.getNext() == null) {
            }
            else if (tName1.getWhitespacesAfterCount() > 0) {
            }
            else if (tName1.getNext().isChar(',')) {
            }
            else if (tName1.getLengthChar() > 1 && tName1.getNext().isCharOf(".") && tName1.getNext().isWhitespaceAfter()) {
            }
            else if (br != null && br.getEndToken().getPrevious() == tName1) {
            }
            else 
                return null;
        }
        else if (t.chars.isAllLower() && br == null) 
            return null;
        com.pullenti.ner.Token t00 = t0.getPrevious();
        if (t00 != null && t00.getMorph()._getClass().isAdjective()) 
            t00 = t00.getPrevious();
        if (t00 != null && t00.getMorph()._getClass().isPreposition()) 
            t00 = t00.getPrevious();
        com.pullenti.ner.core.TerminToken tok = m_PropNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.chars.isLatinLetter() && t.isValue("THE", null)) 
            tok = m_PropNames.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && t.isValue("ВЕДУЩИЙ", null) && tok.getBeginToken() == tok.getEndToken()) 
            tok = null;
        if (tok != null) 
            prof = (OrgProfile)tok.termin.tag;
        if (br != null) {
            com.pullenti.ner.Token t1 = br.getEndToken().getPrevious();
            for (com.pullenti.ner.Token tt = br.getBeginToken(); tt != null && tt.getEndChar() <= br.getEndChar(); tt = tt.getNext()) {
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (mc.equals(com.pullenti.morph.MorphClass.VERB)) 
                    return null;
                if (mc.equals(com.pullenti.morph.MorphClass.ADVERB)) 
                    return null;
                if (tt.isCharOf("?:")) 
                    return null;
                if (tt == br.getBeginToken().getNext() || tt == br.getEndToken().getPrevious()) {
                    if (((tt.isValue("ЖУРНАЛ", null) || tt.isValue("ГАЗЕТА", null) || tt.isValue("ПРАВДА", null)) || tt.isValue("ИЗВЕСТИЯ", null) || tt.isValue("НОВОСТИ", null)) || tt.isValue("ВЕДОМОСТИ", null)) {
                        ok = true;
                        prof = OrgProfile.MEDIA;
                        prof2 = OrgProfile.PRESS;
                    }
                }
            }
            if (!ok && _isStdPressEnd(t1)) {
                if (br.getBeginToken().getNext().chars.isCapitalUpper() && (br.getLengthChar() < 15)) {
                    ok = true;
                    prof = OrgProfile.MEDIA;
                    prof2 = OrgProfile.PRESS;
                }
            }
            else if (t1.isValue("FM", null)) {
                ok = true;
                prof = OrgProfile.MEDIA;
                typ = "радиостанция";
            }
            else if (((t1.isValue("РУ", null) || t1.isValue("RU", null) || t1.isValue("NET", null))) && t1.getPrevious() != null && t1.getPrevious().isChar('.')) 
                prof = OrgProfile.MEDIA;
            com.pullenti.ner.Token b = br.getBeginToken().getNext();
            if (b.isValue("THE", null)) 
                b = b.getNext();
            if (_isStdPressEnd(b) || b.isValue("ВЕЧЕРНИЙ", null)) {
                ok = true;
                prof = OrgProfile.MEDIA;
            }
        }
        if ((tok == null && !ok && tName1 == null) && prof == OrgProfile.UNDEFINED) {
            if (br == null || !t.chars.isCapitalUpper()) 
                return null;
            if (!(t00 instanceof com.pullenti.ner.TextToken)) 
                return null;
            com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(t00, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null) {
                OrgProfile pr = (OrgProfile)tok1.termin.tag;
                if (prof != OrgProfile.UNDEFINED && prof != pr) 
                    return null;
            }
            else {
                if (t.chars.isLetter() && !t.chars.isCyrillicLetter()) {
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            continue;
                        if (tt.getWhitespacesBeforeCount() > 2) 
                            break;
                        if (!tt.chars.isLetter() || tt.chars.isCyrillicLetter()) 
                            break;
                        if (_isStdPressEnd(tt)) {
                            tName1 = tt;
                            prof = OrgProfile.MEDIA;
                            ok = true;
                            break;
                        }
                    }
                }
                if (tName1 == null) 
                    return null;
            }
        }
        if (tok != null) {
            if (tok.getBeginToken().chars.isAllLower() && br == null) {
            }
            else if (tok.getBeginToken() != tok.getEndToken()) 
                ok = true;
            else if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tok.getBeginToken())) 
                return null;
            else if (br == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tok.getBeginToken().getPrevious(), false, false)) 
                return null;
            else if (tok.chars.isAllUpper()) 
                ok = true;
        }
        if (!ok) {
            int cou = 0;
            for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null && (cou < 100); tt = tt.getPrevious(),cou++) {
                if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext())) 
                    break;
                com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null) {
                    OrgProfile pr = (OrgProfile)tok1.termin.tag;
                    if (prof != OrgProfile.UNDEFINED && prof != pr) 
                        continue;
                    if (tok1.termin.tag2 != null && prof == OrgProfile.UNDEFINED) 
                        continue;
                    prof = pr;
                    ok = true;
                    break;
                }
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org1 != null && org1.findSlot(OrganizationReferent.ATTR_PROFILE, null, true) != null) {
                    if ((org1.containsProfile(prof) || prof == OrgProfile.UNDEFINED)) {
                        ok = true;
                        prof = org1.getProfiles().get(0);
                        break;
                    }
                }
            }
            cou = 0;
            if (!ok) {
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null && (cou < 10); tt = tt.getNext(),cou++) {
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt) && prof != OrgProfile.SPORT) 
                        break;
                    com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok1 != null) {
                        OrgProfile pr = (OrgProfile)tok1.termin.tag;
                        if (prof != OrgProfile.UNDEFINED && prof != pr) 
                            continue;
                        if (tok1.termin.tag2 != null && prof == OrgProfile.UNDEFINED) 
                            continue;
                        prof = pr;
                        ok = true;
                        break;
                    }
                    OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                    if (org1 != null && org1.findSlot(OrganizationReferent.ATTR_PROFILE, null, true) != null) {
                        if ((org1.containsProfile(prof) || prof == OrgProfile.UNDEFINED)) {
                            ok = true;
                            prof = org1.getProfiles().get(0);
                            break;
                        }
                    }
                }
            }
            if (!ok) 
                return null;
        }
        if (prof == OrgProfile.UNDEFINED) 
            return null;
        OrganizationReferent __org = new OrganizationReferent();
        __org.addProfile(prof);
        if (prof2 != OrgProfile.UNDEFINED) 
            __org.addProfile(prof2);
        if (prof == OrgProfile.SPORT) 
            __org.addTypeStr("спортивный клуб");
        if (typ != null) 
            __org.addTypeStr(typ);
        if (br != null && ((tok == null || tok.getEndToken() != br.getEndToken().getPrevious()))) {
            String nam;
            if (tok != null) {
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getEndToken().getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) 
                    nam = (tok.termin.getCanonicText() + " " + nam);
                else 
                    nam = tok.termin.getCanonicText();
            }
            else 
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            if (nam != null) 
                __org.addName(nam, true, null);
        }
        else if (tName1 != null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, tName1, com.pullenti.ner.core.GetTextAttr.NO);
            if (nam != null) 
                nam = nam.replace(". ", ".");
            __org.addName(nam, true, null);
        }
        else if (tok != null) {
            __org.addName(tok.termin.getCanonicText(), true, null);
            if (tok.termin.acronym != null) 
                __org.addName(tok.termin.acronym, true, null);
            if (tok.termin.additionalVars != null) {
                for (com.pullenti.ner.core.Termin v : tok.termin.additionalVars) {
                    __org.addName(v.getCanonicText(), true, null);
                }
            }
        }
        else 
            return null;
        if (((((prof.value()) & (OrgProfile.MEDIA.value()))) != (OrgProfile.UNDEFINED.value())) && t0.getPrevious() != null) {
            if (t0.getPrevious().isValue("ГРУППА", null) || t0.getPrevious().isValue("РОКГРУППА", null)) {
                t0 = t0.getPrevious();
                __org.addTypeStr(t0.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase());
                __org.addProfile(OrgProfile.CULTURE);
            }
            else if ((t0.getPrevious().isValue("ЖУРНАЛ", null) || t0.getPrevious().isValue("ИЗДАНИЕ", null) || t0.getPrevious().isValue("ИЗДАТЕЛЬСТВО", null)) || t0.getPrevious().isValue("АГЕНТСТВО", null)) {
                t0 = t0.getPrevious();
                __org.addTypeStr(t0.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase());
                if (!t0.getPrevious().isValue("АГЕНТСТВО", null)) 
                    __org.addProfile(OrgProfile.PRESS);
            }
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(__org, t0, t, null);
        if (br != null) 
            res.setEndToken(br.getEndToken());
        else if (tok != null) 
            res.setEndToken(tok.getEndToken());
        else if (tName1 != null) 
            res.setEndToken(tName1);
        else 
            return null;
        return res;
    }

    private static boolean _isStdPressEnd(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return false;
        String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        if ((((((((com.pullenti.unisharp.Utils.stringsEq(str, "NEWS") || com.pullenti.unisharp.Utils.stringsEq(str, "PRESS") || com.pullenti.unisharp.Utils.stringsEq(str, "PRESSE")) || com.pullenti.unisharp.Utils.stringsEq(str, "ПРЕСС") || com.pullenti.unisharp.Utils.stringsEq(str, "НЬЮС")) || com.pullenti.unisharp.Utils.stringsEq(str, "TIMES") || com.pullenti.unisharp.Utils.stringsEq(str, "TIME")) || com.pullenti.unisharp.Utils.stringsEq(str, "ТАЙМС") || com.pullenti.unisharp.Utils.stringsEq(str, "POST")) || com.pullenti.unisharp.Utils.stringsEq(str, "ПОСТ") || com.pullenti.unisharp.Utils.stringsEq(str, "TODAY")) || com.pullenti.unisharp.Utils.stringsEq(str, "ТУДЕЙ") || com.pullenti.unisharp.Utils.stringsEq(str, "DAILY")) || com.pullenti.unisharp.Utils.stringsEq(str, "ДЕЙЛИ") || com.pullenti.unisharp.Utils.stringsEq(str, "ИНФОРМ")) || com.pullenti.unisharp.Utils.stringsEq(str, "INFORM")) 
            return true;
        return false;
    }

    private static com.pullenti.ner.ReferentToken _tryAttachOrgSportAssociations(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        int cou = 0;
        String typ = null;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.geo.GeoReferent _geo = null;
        if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (rt.getEndToken().isValue("ФЕДЕРАЦИЯ", null) || rt.getBeginToken().isValue("ФЕДЕРАЦИЯ", null)) {
                typ = "федерация";
                _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            }
            t1 = t;
            if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isAdjective()) {
                if (m_Sports.tryParse(t.getPrevious(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                    cou++;
                    t = t.getPrevious();
                }
            }
        }
        else {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null) 
                return null;
            if (npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                return null;
            if (((npt.noun.isValue("АССОЦИАЦИЯ", null) || npt.noun.isValue("ФЕДЕРАЦИЯ", null) || npt.noun.isValue("СОЮЗ", null)) || npt.noun.isValue("СБОРНАЯ", null) || npt.noun.isValue("КОМАНДА", null)) || npt.noun.isValue("КЛУБ", null)) 
                typ = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
            else if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ФК")) 
                typ = "команда";
            else 
                return null;
            if (com.pullenti.unisharp.Utils.stringsEq(typ, "команда")) 
                cou--;
            for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                com.pullenti.ner.core.TerminToken tok = m_Sports.tryParse(a.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) 
                    cou++;
                else if (a.getBeginToken().isValue("ОЛИМПИЙСКИЙ", null)) 
                    cou++;
            }
            if (t1 == null) 
                t1 = npt.getEndToken();
        }
        com.pullenti.ner.Token t11 = t1;
        String propname = null;
        String delWord = null;
        for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.getWhitespacesBeforeCount() > 3) 
                break;
            if (tt.isCommaAnd()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition() && !tt.getMorph()._getClass().isAdverb() && !tt.getMorph()._getClass().isVerb()) 
                continue;
            if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                t1 = tt;
                _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (com.pullenti.unisharp.Utils.stringsEq(typ, "сборная")) 
                    cou++;
                continue;
            }
            if (tt.isValue("СТРАНА", null) && (tt instanceof com.pullenti.ner.TextToken)) {
                t1 = (t11 = tt);
                delWord = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                continue;
            }
            com.pullenti.ner.core.TerminToken tok = m_Sports.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                cou++;
                t1 = (t11 = (tt = tok.getEndToken()));
                continue;
            }
            if (tt.chars.isAllLower() || tt.getMorphClassInDictionary().isVerb()) {
            }
            else 
                tok = m_PropNames.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                propname = tok.termin.getCanonicText();
                cou++;
                t1 = (tt = tok.getEndToken());
                if (cou == 0 && com.pullenti.unisharp.Utils.stringsEq(typ, "команда")) 
                    cou++;
                continue;
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                tok = m_PropNames.tryParse(tt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null || cou > 0) {
                    propname = com.pullenti.ner.core.MiscHelper.getTextValue(tt.getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    cou++;
                    tt = (t1 = br.getEndToken());
                    continue;
                }
                break;
            }
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt1 == null) 
                break;
            tok = m_Sports.tryParse(npt1.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) 
                break;
            cou++;
            t1 = (t11 = (tt = tok.getEndToken()));
        }
        if (cou <= 0) 
            return null;
        OrganizationReferent __org = new OrganizationReferent();
        __org.addTypeStr(typ);
        if (com.pullenti.unisharp.Utils.stringsEq(typ, "федерация")) 
            __org.addTypeStr("ассоциация");
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(t, t11, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) | (com.pullenti.ner.core.GetTextAttr.IGNOREGEOREFERENT.value())));
        if (_name != null && delWord != null) {
            if ((_name.indexOf(" " + delWord) >= 0)) 
                _name = _name.replace(" " + delWord, "");
        }
        if (_name != null) 
            _name = _name.replace(" РОССИЯ", "").replace(" РОССИИ", "");
        if (propname != null) {
            __org.addName(propname, true, null);
            if (_name != null) 
                __org.addTypeStr(_name.toLowerCase());
        }
        else if (_name != null) 
            __org.addName(_name, true, null);
        if (_geo != null) 
            __org.addGeoObject(_geo);
        __org.addProfile(OrgProfile.SPORT);
        return new com.pullenti.ner.ReferentToken(__org, t, t1, null);
    }

    private static com.pullenti.ner.core.TerminCollection m_Sports;

    private static com.pullenti.ner.core.TerminCollection m_PropNames;

    private static com.pullenti.ner.core.TerminCollection m_PropPref;

    private static void _initSport() {
        m_Sports = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"акробатика;акробатический;акробат", "бадминтон;бадминтонный;бадминтонист", "баскетбол;баскетбольный;баскетболист", "бейсбол;бейсбольный;бейсболист", "биатлон;биатлонный;биатлонист", "бильярд;бильярдный;бильярдист", "бобслей;бобслейный;бобслеист", "боулинг", "боевое искуство", "бокс;боксерский;боксер", "борьба;борец", "водное поло", "волейбол;волейбольный;волейболист", "гандбол;гандбольный;гандболист", "гольф;гольфный;гольфист", "горнолыжный спорт", "слалом;;слаломист", "сквош", "гребля", "дзюдо;дзюдоистский;дзюдоист", "карате;;каратист", "керлинг;;керлингист", "коньки;конькобежный;конькобежец", "легкая атлетика;легкоатлетический;легкоатлет", "лыжных гонок", "мотоцикл;мотоциклетный;мотоциклист", "тяжелая атлетика;тяжелоатлетический;тяжелоатлет", "ориентирование", "плавание;;пловец", "прыжки", "регби;;регбист", "пятиборье", "гимнастика;гимнастический;гимнаст", "самбо;;самбист", "сумо;;сумист", "сноуборд;сноубордический;сноубордист", "софтбол;софтбольный;софтболист", "стрельба;стрелковый", "спорт;спортивный", "теннис;теннисный;теннисист", "триатлон", "тхэквондо", "ушу;;ушуист", "фехтование;фехтовальный;фехтовальщик", "фигурное катание;;фигурист", "фристайл;фристальный", "футбол;футбольный;футболист", "мини-футбол", "хоккей;хоккейный;хоккеист", "хоккей на траве", "шахматы;шахматный;шахматист", "шашки;шашечный"}) {
            String[] pp = com.pullenti.unisharp.Utils.split(s.toUpperCase(), String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, null, false);
            t.initByNormalText(pp[0], com.pullenti.morph.MorphLang.RU);
            if (pp.length > 1 && !com.pullenti.unisharp.Utils.isNullOrEmpty(pp[1])) 
                t.addVariant(pp[1], true);
            if (pp.length > 2 && !com.pullenti.unisharp.Utils.isNullOrEmpty(pp[2])) 
                t.addVariant(pp[2], true);
            m_Sports.add(t);
        }
        for (String s : new String[] {"байдарка", "каноэ", "лук", "трава", "коньки", "трамплин", "двоеборье", "батут", "вода", "шпага", "сабля", "лыжи", "скелетон"}) {
            m_Sports.add(com.pullenti.ner.core.Termin._new2563(s.toUpperCase(), s));
        }
        m_PropNames = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"СПАРТАК", "ЦСКА", "ЗЕНИТ!", "ТЕРЕК", "КРЫЛЬЯ СОВЕТОВ", "ДИНАМО", "АНЖИ", "КУБАНЬ", "АЛАНИЯ", "ТОРПЕДО", "АРСЕНАЛ!", "ЛОКОМОТИВ", "МЕТАЛЛУРГ!", "РОТОР", "СКА", "СОКОЛ!", "ХИМИК!", "ШИННИК", "РУБИН", "ШАХТЕР", "САЛАВАТ ЮЛАЕВ", "ТРАКТОР!", "АВАНГАРД!", "АВТОМОБИЛИСТ!", "АТЛАНТ!", "ВИТЯЗЬ!", "НАЦИОНАЛЬНАЯ ХОККЕЙНАЯ ЛИГА;НХЛ", "КОНТИНЕНТАЛЬНАЯ ХОККЕЙНАЯ ЛИГА;КХЛ", "СОЮЗ ЕВРОПЕЙСКИХ ФУТБОЛЬНЫХ АССОЦИАЦИЙ;УЕФА;UEFA", "Женская теннисная ассоциация;WTA", "Международная федерация бокса;IBF", "Всемирная боксерская организация;WBO", "РЕАЛ", "МАНЧЕСТЕР ЮНАЙТЕД", "манчестер сити", "БАРСЕЛОНА!", "БАВАРИЯ!", "ЧЕЛСИ", "ЛИВЕРПУЛЬ!", "ЮВЕНТУС", "НАПОЛИ", "БОЛОНЬЯ", "ФУЛХЭМ", "ЭВЕРТОН", "ФИЛАДЕЛЬФИЯ", "ПИТТСБУРГ", "ИНТЕР!", "Аякс", "ФЕРРАРИ;FERRARI", "РЕД БУЛЛ;RED BULL", "МАКЛАРЕН;MCLAREN", "МАКЛАРЕН-МЕРСЕДЕС;MCLAREN-MERCEDES"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0 + ss.length() - 1);
            }
            String[] pp = com.pullenti.unisharp.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new100(pp[0], OrgProfile.SPORT);
            if (!isBad) 
                t.tag2 = ss;
            if (pp.length > 1) {
                if (pp[1].length() < 4) 
                    t.acronym = pp[1];
                else 
                    t.addVariant(pp[1], false);
            }
            m_PropNames.add(t);
        }
        for (String s : new String[] {"ИТАР ТАСС;ТАСС;Телеграфное агентство советского союза", "Интерфакс;Interfax", "REGNUM", "ЛЕНТА.РУ;Lenta.ru", "Частный корреспондент;ЧасКор", "РИА Новости;Новости!;АПН", "Росбалт;RosBalt", "УНИАН", "ИНФОРОС;inforos", "Эхо Москвы", "Сноб!", "Серебряный дождь", "Вечерняя Москва;Вечерка", "Московский Комсомолец;Комсомолка", "Коммерсантъ;Коммерсант", "Афиша", "Аргументы и факты;АИФ", "Викиновости", "РосБизнесКонсалтинг;РБК", "Газета.ру", "Русский Репортер!", "Ведомости", "Вести!", "Рамблер Новости", "Живой Журнал;ЖЖ;livejournal;livejournal.ru", "Новый Мир", "Новая газета", "Правда!", "Известия!", "Бизнес!", "Русская жизнь!", "НТВ Плюс", "НТВ", "ВГТРК", "ТНТ", "Муз ТВ;МузТВ", "АСТ", "Эксмо", "Астрель", "Терра!", "Финанс!", "Собеседник!", "Newsru.com", "Nature!", "Россия сегодня;Russia Today;RT!", "БЕЛТА", "Ассошиэйтед Пресс;Associated Press", "France Press;France Presse;Франс пресс;Agence France Presse;AFP", "СИНЬХУА", "Gallup", "Cable News Network;CNN", "CBS News", "ABC News", "GoogleNews;Google News", "FoxNews;Fox News", "Reuters;Рейтер", "British Broadcasting Corporation;BBC;БиБиСи;BBC News", "MSNBC", "Голос Америки", "Аль Джазира;Al Jazeera", "Радио Свобода", "Радио Свободная Европа", "Guardian;Гардиан", "Daily Telegraph", "Times;Таймс!", "Independent!", "Financial Times", "Die Welt", "Bild!", "La Pepublica;Република!", "Le Monde", "People Daily", "BusinessWeek", "Economist!", "Forbes;Форбс", "Los Angeles Times", "New York Times", "Wall Street Journal;WSJ", "Washington Post", "Le Figaro;Фигаро", "Bloomberg", "DELFI!"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0 + ss.length() - 1);
            }
            String[] pp = com.pullenti.unisharp.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new100(pp[0], OrgProfile.MEDIA);
            if (!isBad) 
                t.tag2 = ss;
            for (int ii = 1; ii < pp.length; ii++) {
                if ((pp[ii].length() < 4) && t.acronym == null) 
                    t.acronym = pp[ii];
                else 
                    t.addVariant(pp[ii], false);
            }
            m_PropNames.add(t);
        }
        for (String s : new String[] {"Машина времени!", "ДДТ", "Биттлз;Bittles", "ABBA;АББА", "Океан Эльзы;Океан Эльзи", "Аквариум!", "Крематорий!", "Наутилус;Наутилус Помпилиус!", "Пусси Райот;Пусси Риот;Pussy Riot", "Кино!", "Алиса!", "Агата Кристи!", "Чайф", "Ария!", "Земфира!", "Браво!", "Черный кофе!", "Воскресение!", "Урфин Джюс", "Сплин!", "Пикник!", "Мумий Троль", "Коррозия металла", "Арсенал!", "Ночные снайперы!", "Любэ", "Ласковый май!", "Noize MC", "Linkin Park", "ac dc", "green day!", "Pink Floyd;Пинк Флойд", "Depeche Mode", "Bon Jovi", "Nirvana;Нирвана!", "Queen;Квин!", "Nine Inch Nails", "Radioheads", "Pet Shop Boys", "Buggles"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0 + ss.length() - 1);
            }
            String[] pp = com.pullenti.unisharp.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new100(pp[0], OrgProfile.MUSIC);
            if (!isBad) 
                t.tag2 = ss;
            for (int ii = 1; ii < pp.length; ii++) {
                if ((pp[ii].length() < 4) && t.acronym == null) 
                    t.acronym = pp[ii];
                else 
                    t.addVariant(pp[ii], false);
            }
            m_PropNames.add(t);
        }
        m_PropPref = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"ФАНАТ", "БОЛЕЛЬЩИК", "гонщик", "вратарь", "нападающий", "голкипер", "полузащитник", "полу-защитник", "центрфорвард", "центр-форвард", "форвард", "игрок", "легионер", "спортсмен"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), OrgProfile.SPORT));
        }
        for (String s : new String[] {"защитник", "капитан", "пилот", "игра", "поле", "стадион", "гонка", "чемпионат", "турнир", "заезд", "матч", "кубок", "олипмиада", "финал", "полуфинал", "победа", "поражение", "разгром", "дивизион", "олипмиада", "финал", "полуфинал", "играть", "выигрывать", "выиграть", "проигрывать", "проиграть", "съиграть"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new102(s.toUpperCase(), OrgProfile.SPORT, s));
        }
        for (String s : new String[] {"корреспондент", "фотокорреспондент", "репортер", "журналист", "тележурналист", "телеоператор", "главный редактор", "главред", "телеведущий", "редколлегия", "обозреватель", "сообщать", "сообщить", "передавать", "передать", "писать", "написать", "издавать", "пояснить", "пояснять", "разъяснить", "разъяснять", "сказать", "говорить", "спрашивать", "спросить", "отвечать", "ответить", "выяснять", "выяснить", "цитировать", "процитировать", "рассказать", "рассказывать", "информировать", "проинформировать", "поведать", "напечатать", "напоминать", "напомнить", "узнать", "узнавать", "репортаж", "интервью", "информации", "сведение", "ИА", "информагенство", "информагентство", "информационный", "газета", "журнал"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), OrgProfile.MEDIA));
        }
        for (String s : new String[] {"сообщение", "статья", "номер", "журнал", "издание", "издательство", "агентство", "цитата", "редактор", "комментатор", "по данным", "оператор", "вышедший", "отчет", "вопрос", "читатель", "слушатель", "телезритель", "источник", "собеедник"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new102(s.toUpperCase(), OrgProfile.MEDIA, s));
        }
        for (String s : new String[] {"музыкант", "певец", "певица", "ударник", "гитарист", "клавишник", "солист", "солистка", "исполнять", "исполнить", "концерт", "гастроль", "выступление", "известный", "известнейший", "популярный", "популярнейший", "рокгруппа", "панкгруппа", "артгруппа", "группа", "пластинка", "грампластинка", "концертный", "музыка", "запись", "студия"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), OrgProfile.MUSIC));
        }
    }

    private static com.pullenti.ner.ReferentToken tryAttachArmy(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.NumberToken) || t.getWhitespacesAfterCount() > 2) 
            return null;
        com.pullenti.ner._org.internal.OrgItemTypeToken typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true);
        if (typ == null) 
            return null;
        if (typ.root != null && typ.root.profiles.contains(OrgProfile.ARMY)) {
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t.getNext(), AttachType.HIGH, null, false, -1);
            if (rt != null) {
                if (rt.getBeginToken() == typ.getBeginToken()) {
                    rt.setBeginToken(t);
                    ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                }
                return rt;
            }
            OrganizationReferent __org = new OrganizationReferent();
            __org.addType(typ, true);
            __org.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            return new com.pullenti.ner.ReferentToken(__org, t, typ.getEndToken(), null);
        }
        return null;
    }

    /**
     * Имя анализатора ("ORGANIZATION")
     */
    public static final String ANALYZER_NAME = "ORGANIZATION";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new OrganizationAnalyzer();
    }

    @Override
    public String getCaption() {
        return "Организации";
    }


    @Override
    public String getDescription() {
        return "Организации, предприятия, компании...";
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner._org.internal.MetaOrganization.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(OrgProfile.UNIT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("dep.png"));
        res.put(OrgProfile.UNION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("party.png"));
        res.put(OrgProfile.COMPETITION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("festival.png"));
        res.put(OrgProfile.HOLDING.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("holding.png"));
        res.put(OrgProfile.STATE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrgProfile.FINANCE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("bank.png"));
        res.put(OrgProfile.EDUCATION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("study.png"));
        res.put(OrgProfile.SCIENCE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("science.png"));
        res.put(OrgProfile.INDUSTRY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("factory.png"));
        res.put(OrgProfile.TRADE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("trade.png"));
        res.put(OrgProfile.POLICY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("politics.png"));
        res.put(OrgProfile.JUSTICE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("justice.png"));
        res.put(OrgProfile.ENFORCEMENT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrgProfile.ARMY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("military.png"));
        res.put(OrgProfile.SPORT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("sport.png"));
        res.put(OrgProfile.RELIGION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("church.png"));
        res.put(OrgProfile.MUSIC.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("music.png"));
        res.put(OrgProfile.MEDIA.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("media.png"));
        res.put(OrgProfile.PRESS.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("press.png"));
        res.put(OrgProfile.HOTEL.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("hotel.png"));
        res.put(OrgProfile.MEDICINE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("medicine.png"));
        res.put(OrgProfile.TRANSPORT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("train.png"));
        res.put(OrganizationKind.BANK.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("bank.png"));
        res.put(OrganizationKind.CULTURE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("culture.png"));
        res.put(OrganizationKind.DEPARTMENT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("dep.png"));
        res.put(OrganizationKind.FACTORY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("factory.png"));
        res.put(OrganizationKind.GOVENMENT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrganizationKind.MEDICAL.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("medicine.png"));
        res.put(OrganizationKind.PARTY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("party.png"));
        res.put(OrganizationKind.STUDY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("study.png"));
        res.put(OrganizationKind.FEDERATION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("federation.png"));
        res.put(OrganizationKind.CHURCH.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("church.png"));
        res.put(OrganizationKind.MILITARY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("military.png"));
        res.put(OrganizationKind.AIRPORT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("avia.png"));
        res.put(OrganizationKind.FESTIVAL.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("festival.png"));
        res.put(com.pullenti.ner._org.internal.MetaOrganization.ORGIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("org.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, OrganizationReferent.OBJ_TYPENAME)) 
            return new OrganizationReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME});
    }


    @Override
    public int getProgressWeight() {
        return 45;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner._org.internal.OrgAnalyzerData();
    }

    public static com.pullenti.ner._org.internal.OrgAnalyzerData getData(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        return (com.pullenti.ner._org.internal.OrgAnalyzerData)com.pullenti.unisharp.Utils.cast(t.kit.getAnalyzerDataByAnalyzerName(ANALYZER_NAME), com.pullenti.ner._org.internal.OrgAnalyzerData.class);
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = (com.pullenti.ner._org.internal.OrgAnalyzerData)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), com.pullenti.ner._org.internal.OrgAnalyzerData.class);
        int tlen = kit.getSofa().getText().length();
        if (kit.getSofa().ignoredEndChar > 0) 
            tlen = kit.getSofa().ignoredBeginChar + ((tlen - kit.getSofa().ignoredEndChar));
        if (tlen > 400000) 
            ad.largeTextRegim = true;
        else 
            ad.largeTextRegim = false;
        com.pullenti.ner._org.internal.OrgItemTypeToken.SPEEDREGIME = true;
        com.pullenti.ner._org.internal.OrgItemTypeToken.prepareAllData(kit.firstToken);
        ad.tRegime = true;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            t.setInnerBool(false);
        }
        int steps = 2;
        int max = steps;
        int delta = 100000;
        int parts = (((tlen + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        max *= parts;
        int cur = 0;
        for (int step = 0; step < steps; step++) {
            int nextPos = delta;
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isIgnored()) 
                    continue;
                if (t.getBeginChar() > nextPos) {
                    nextPos += delta;
                    if (nextPos <= t.getBeginChar()) 
                        nextPos = t.getBeginChar() + delta;
                    cur++;
                    if (cur > max) 
                        cur = max;
                    if (!this.onProgress(cur, max, kit)) 
                        return;
                }
                if (t.isValue("СК", null)) {
                }
                if (step > 0 && (t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), t.getNext());
                    if (mt != null) {
                        if (ad != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), t.getReferent(), false);
                            ad.aliases.add(term);
                        }
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new786(t.getReferent(), t, mt.getEndToken(), t.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
                for (int kk = 0; kk < 5; kk++) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = tryAttachOrgs(t, step);
                    if (rts == null || rts.size() == 0) 
                        break;
                    if (!com.pullenti.ner.MetaToken.check(rts)) 
                        break;
                    boolean emb = false;
                    com.pullenti.ner.Token tt0 = t;
                    for (com.pullenti.ner.ReferentToken rt : rts) {
                        if (!((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).checkCorrection()) 
                            continue;
                        rt.referent = ad.registerReferent(rt.referent);
                        if (rt.getBeginToken().getReferent() == rt.referent || rt.getEndToken().getReferent() == rt.referent) 
                            continue;
                        kit.embedToken(rt);
                        emb = true;
                        if (rt.getBeginChar() <= t.getBeginChar()) 
                            t = rt;
                    }
                    if ((rts.size() == 1 && t == rts.get(0) && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rts.get(0).referent, OrganizationReferent.class);
                        OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                        if (org1.getHigher() == null && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org0, org1, false) && !com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org1, org0, false)) {
                            com.pullenti.ner.ReferentToken rtt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class);
                            kit.debedToken(rtt);
                            org1.setHigher(org0);
                            com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(ad.registerReferent(org1), t, rtt.getEndToken(), t.getNext().getMorph());
                            kit.embedToken(rt1);
                            t = rt1;
                        }
                    }
                    if (emb && !(t instanceof com.pullenti.ner.ReferentToken)) 
                        continue;
                    break;
                }
                if (step > 0) {
                    com.pullenti.ner.ReferentToken rt = checkOwnership(t);
                    if (rt != null) {
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
                if ((t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.ReferentToken rt0 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    for (int kk = 0; rt0 != null && (kk < 4); kk++) {
                        com.pullenti.ner.ReferentToken rt00 = this.tryAttachOrgBefore(rt0);
                        if (rt00 == null) 
                            break;
                        rt0 = rt00;
                        _doPostAnalyze(rt0);
                        rt0.referent = ad.registerReferent(rt0.referent);
                        kit.embedToken(rt0);
                        t = rt0;
                    }
                }
                if (step > 0 && (t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), t.getNext());
                    if (mt != null) {
                        if (ad != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), t.getReferent(), false);
                            ad.aliases.add(term);
                        }
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new786(t.getReferent(), t, mt.getEndToken(), t.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
            }
            if (ad.getReferents().size() == 0) {
                if (!kit.miscData.containsKey("o2step")) 
                    break;
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> list = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (__org == null) 
                continue;
            com.pullenti.ner.Token t1 = t.getNext();
            if (((t1 != null && t1.isChar('(') && t1.getNext() != null) && (t1.getNext().getReferent() instanceof OrganizationReferent) && t1.getNext().getNext() != null) && t1.getNext().getNext().isChar(')')) {
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getReferent(), OrganizationReferent.class);
                if (org0 == __org || __org.getHigher() == org0) {
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(__org, t, t1.getNext().getNext(), t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    t1 = t.getNext();
                }
                else if (__org.getHigher() == null && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org0, __org, false) && !com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(__org, org0, false)) {
                    __org.setHigher(org0);
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new786(__org, t, t1.getNext().getNext(), t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    t1 = t.getNext();
                }
            }
            com.pullenti.ner.TextToken ofTok = null;
            if (t1 != null) {
                if (t1.isCharOf(",") || t1.isHiphen()) 
                    t1 = t1.getNext();
                else if (!kit.ontoRegime && t1.isChar(';')) 
                    t1 = t1.getNext();
                else if (t1.isValue("ПРИ", null) || t1.isValue("OF", null) || t1.isValue("AT", null)) {
                    ofTok = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
                    t1 = t1.getNext();
                }
            }
            if (t1 == null) 
                break;
            OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), OrganizationReferent.class);
            if (org1 == null) 
                continue;
            if (ofTok == null) {
                if (__org.getHigher() == null) {
                    if (!com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org1, __org, false)) {
                        if (t1.getPrevious() != t || t1.getWhitespacesAfterCount() > 2) 
                            continue;
                        com.pullenti.ner.ReferentToken pp = t.kit.processReferent("PERSON", t1.getNext(), null);
                        if (pp != null) {
                        }
                        else 
                            continue;
                    }
                }
            }
            if (__org.getHigher() != null) {
                if (!__org.getHigher().canBeEquals(org1, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                    continue;
            }
            list.clear();
            list.add((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
            list.add((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.ReferentToken.class));
            if (ofTok != null && __org.getHigher() == null) {
                for (com.pullenti.ner.Token t2 = t1.getNext(); t2 != null; t2 = t2.getNext()) {
                    if (((t2 instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.TextToken.class)).term, ofTok.term) && t2.getNext() != null) && (t2.getNext().getReferent() instanceof OrganizationReferent)) {
                        t2 = t2.getNext();
                        if (org1.getHigher() != null) {
                            if (!org1.getHigher().canBeEquals(t2.getReferent(), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                                break;
                        }
                        list.add((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.ReferentToken.class));
                        org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t2.getReferent(), OrganizationReferent.class);
                    }
                    else 
                        break;
                }
            }
            com.pullenti.ner.ReferentToken rt0 = list.get(list.size() - 1);
            for (int i = list.size() - 2; i >= 0; i--) {
                __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(list.get(i).referent, OrganizationReferent.class);
                org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                if (__org.getHigher() == null) {
                    __org.setHigher(org1);
                    __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(__org), OrganizationReferent.class);
                }
                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(__org, list.get(i), rt0, null);
                kit.embedToken(rt);
                t = rt;
                rt0 = rt;
            }
        }
        java.util.HashMap<String, java.util.ArrayList<OrganizationReferent>> owners = new java.util.HashMap<String, java.util.ArrayList<OrganizationReferent>>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (__org == null) 
                continue;
            OrganizationReferent hi = __org.getHigher();
            if (hi == null) 
                continue;
            for (String ty : __org.getTypes()) {
                java.util.ArrayList<OrganizationReferent> li;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<OrganizationReferent>> wrapli2577 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<OrganizationReferent>>();
                boolean inoutres2578 = com.pullenti.unisharp.Utils.tryGetValue(owners, ty, wrapli2577);
                li = wrapli2577.value;
                if (!inoutres2578) 
                    owners.put(ty, (li = new java.util.ArrayList<OrganizationReferent>()));
                java.util.ArrayList<OrganizationReferent> childs = null;
                if (!li.contains(hi)) {
                    li.add(hi);
                    hi.tag = (childs = new java.util.ArrayList<OrganizationReferent>());
                }
                else 
                    childs = (java.util.ArrayList<OrganizationReferent>)com.pullenti.unisharp.Utils.cast(hi.tag, java.util.ArrayList.class);
                if (childs != null && !childs.contains(__org)) 
                    childs.add(__org);
            }
        }
        java.util.ArrayList<OrganizationReferent> owns = new java.util.ArrayList<OrganizationReferent>();
        com.pullenti.ner.Token lastMvdOrg = null;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (__org == null) 
                continue;
            if (_isMvdOrg(__org) != null) 
                lastMvdOrg = t;
            if (__org.getHigher() != null) 
                continue;
            owns.clear();
            for (String ty : __org.getTypes()) {
                java.util.ArrayList<OrganizationReferent> li;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<OrganizationReferent>> wrapli2579 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<OrganizationReferent>>();
                boolean inoutres2580 = com.pullenti.unisharp.Utils.tryGetValue(owners, ty, wrapli2579);
                li = wrapli2579.value;
                if (!inoutres2580) 
                    continue;
                for (OrganizationReferent h : li) {
                    if (!owns.contains(h)) 
                        owns.add(h);
                }
            }
            if (owns.size() != 1) 
                continue;
            if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(owns.get(0), __org, true)) {
                java.util.ArrayList<OrganizationReferent> childs = (java.util.ArrayList<OrganizationReferent>)com.pullenti.unisharp.Utils.cast(owns.get(0).tag, java.util.ArrayList.class);
                if (childs == null) 
                    continue;
                boolean hasNum = false;
                boolean hasGeo = false;
                for (OrganizationReferent oo : childs) {
                    if (oo.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                        hasGeo = true;
                    if (oo.findSlot(OrganizationReferent.ATTR_NUMBER, null, true) != null) 
                        hasNum = true;
                }
                if (hasNum != (__org.findSlot(OrganizationReferent.ATTR_NUMBER, null, true) != null)) 
                    continue;
                if (hasGeo != (__org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null)) 
                    continue;
                __org.setHigher(owns.get(0));
                if (__org.getKind() != OrganizationKind.DEPARTMENT) 
                    __org.setHigher(null);
            }
        }
        for (com.pullenti.ner.Token t = lastMvdOrg; t != null; t = t.getPrevious()) {
            if (t.isIgnored()) 
                continue;
            if (!(t instanceof com.pullenti.ner.ReferentToken)) 
                continue;
            OrganizationReferent mvd = _isMvdOrg((OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class));
            if (mvd == null) 
                continue;
            com.pullenti.ner.Token t1 = null;
            boolean br = false;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.isChar(')')) {
                    br = true;
                    continue;
                }
                if (br) {
                    if (tt.isChar('(')) 
                        br = false;
                    continue;
                }
                if (!(tt instanceof com.pullenti.ner.TextToken)) 
                    break;
                if (tt.getLengthChar() < 2) 
                    continue;
                if (tt.chars.isAllUpper() || ((!tt.chars.isAllUpper() && !tt.chars.isAllLower() && !tt.chars.isCapitalUpper()))) 
                    t1 = tt;
                break;
            }
            if (t1 == null) 
                continue;
            com.pullenti.ner.Token t0 = t1;
            if ((t0.getPrevious() instanceof com.pullenti.ner.TextToken) && (t0.getWhitespacesBeforeCount() < 2) && t0.getPrevious().getLengthChar() >= 2) {
                if (t0.getPrevious().chars.isAllUpper() || ((!t0.getPrevious().chars.isAllUpper() && !t0.getPrevious().chars.isAllLower() && !t0.getPrevious().chars.isCapitalUpper()))) 
                    t0 = t0.getPrevious();
            }
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.NO);
            if ((com.pullenti.unisharp.Utils.stringsEq(nam, "ОВД") || com.pullenti.unisharp.Utils.stringsEq(nam, "ГУВД") || com.pullenti.unisharp.Utils.stringsEq(nam, "УВД")) || com.pullenti.unisharp.Utils.stringsEq(nam, "ГУ")) 
                continue;
            com.pullenti.morph.MorphClass mc = t0.getMorphClassInDictionary();
            if (!mc.isUndefined()) 
                continue;
            mc = t1.getMorphClassInDictionary();
            if (!mc.isUndefined()) 
                continue;
            OrganizationReferent __org = new OrganizationReferent();
            __org.addProfile(OrgProfile.UNIT);
            __org.addName(nam, true, null);
            __org.setHigher(mvd);
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(__org), t0, t1, null);
            kit.embedToken(rt);
            t = rt.getNext();
            if (t == null) 
                break;
        }
        ad.tRegime = false;
    }

    private static OrganizationReferent _isMvdOrg(OrganizationReferent __org) {
        if (__org == null) 
            return null;
        OrganizationReferent res = null;
        for (int i = 0; i < 5; i++) {
            if (res == null) {
                for (com.pullenti.ner.Slot s : __org.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_TYPE)) {
                        res = __org;
                        break;
                    }
                }
            }
            if (__org.findSlot(OrganizationReferent.ATTR_NAME, "МВД", true) != null || __org.findSlot(OrganizationReferent.ATTR_NAME, "ФСБ", true) != null) 
                return (res != null ? res : __org);
            __org = __org.getHigher();
            if (__org == null) 
                break;
        }
        return null;
    }

    public static com.pullenti.ner.MetaToken _checkAliasAfter(com.pullenti.ner.ReferentToken rt, com.pullenti.ner.Token t) {
        if ((t != null && t.isChar('<') && t.getNext() != null) && t.getNext().getNext() != null && t.getNext().getNext().isChar('>')) 
            t = t.getNext().getNext().getNext();
        if (t == null || t.getNext() == null || !t.isChar('(')) 
            return null;
        t = t.getNext();
        if (t.isValue("ДАЛЕЕ", null) || t.isValue("ДАЛІ", null)) 
            t = t.getNext();
        else if (t.isValue("HEREINAFTER", null) || t.isValue("ABBREVIATED", null) || t.isValue("HEREAFTER", null)) {
            t = t.getNext();
            if (t != null && t.isValue("REFER", null)) 
                t = t.getNext();
        }
        else 
            return null;
        while (t != null) {
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            else if (!t.chars.isLetter()) 
                t = t.getNext();
            else if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isMisc() || t.isValue("ИМЕНОВАТЬ", null)) 
                t = t.getNext();
            else 
                break;
        }
        if (t == null) 
            return null;
        com.pullenti.ner.Token t1 = null;
        int nli = 0;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) {
                nli++;
                if (nli > 1) 
                    break;
            }
            else if (tt.isChar(')')) {
                t1 = tt.getPrevious();
                break;
            }
        }
        if (t1 == null) 
            return null;
        com.pullenti.ner.MetaToken mt = new com.pullenti.ner.MetaToken(t, t1.getNext(), null);
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
        mt.tag = nam;
        if (nam.indexOf(' ') < 0) {
            for (com.pullenti.ner.Token tt = rt.getBeginToken(); tt != null && tt.getEndChar() <= rt.getEndChar(); tt = tt.getNext()) {
                if (tt.isValue((String)com.pullenti.unisharp.Utils.cast(mt.tag, String.class), null)) 
                    return mt;
            }
            return null;
        }
        return mt;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        return processReferentStat(begin, param);
    }

    public static com.pullenti.ner.ReferentToken processReferentStat(com.pullenti.ner.Token begin, String param) {
        if (begin == null) 
            return null;
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = getData(begin);
        if (ad == null) 
            return null;
        if (ad.level > 2) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(param, "TYPE")) {
            ad.level++;
            com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(begin, false);
            ad.level--;
            if (ty != null && ty.getBeginToken() == begin) {
                OrganizationReferent __org = new OrganizationReferent();
                __org.addType(ty, false);
                return com.pullenti.ner.ReferentToken._new786(__org, begin, ty.getEndToken(), ty.getMorph());
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(param, "MINTYPE")) {
            ad.level++;
            com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttachPureKeywords(begin);
            ad.level--;
            if (ty == null) 
                return null;
            OrganizationReferent __org = new OrganizationReferent();
            __org.addType(ty, false);
            return com.pullenti.ner.ReferentToken._new786(__org, begin, ty.getEndToken(), ty.getMorph());
        }
        ad.level++;
        com.pullenti.ner.ReferentToken rt = tryAttachOrg(begin, (com.pullenti.unisharp.Utils.stringsEq(param, "STRONG") ? AttachType.HIGH : AttachType.NORMAL), null, false, -1);
        if (rt == null) 
            rt = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttachOrg(begin, false);
        if (rt == null) 
            rt = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttachOrg(begin, true);
        if (rt == null) 
            rt = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttachReferenceToExistOrg(begin);
        ad.level--;
        if (rt == null) 
            return null;
        rt.data = ad;
        return rt;
    }

    private static java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttachOrgs(com.pullenti.ner.Token t, int step) {
        if (t == null) 
            return null;
        if (t.chars.isLatinLetter() && com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res11 = tryAttachOrgs(t.getNext(), step);
            if (res11 != null && res11.size() > 0) {
                res11.get(0).setBeginToken(t);
                return res11;
            }
        }
        com.pullenti.ner.ReferentToken rt = null;
        com.pullenti.ner._org.internal.OrgItemTypeToken typ = null;
        if (step == 0 || t.getInnerBool() || t.tag != null) {
            typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, false);
            if (typ != null) 
                t.setInnerBool(true);
            if (typ == null || typ.chars.isLatinLetter()) {
                com.pullenti.ner._org.internal.OrgItemEngItem ltyp = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(t, false);
                if (ltyp != null) 
                    t.setInnerBool(true);
                else if (t.chars.isLatinLetter()) {
                    com.pullenti.ner.ReferentToken rte = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttachOrg(t, false);
                    if (rte != null) {
                        _doPostAnalyze(rte);
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> ree = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                        ree.add(rte);
                        return ree;
                    }
                }
            }
        }
        com.pullenti.ner.ReferentToken rt00 = tryAttachSpec(t);
        if (rt00 == null) 
            rt00 = _tryAttachOrgByAlias(t);
        if (rt00 != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
            _doPostAnalyze(rt00);
            res0.add(rt00);
            return res0;
        }
        if (typ != null) {
            if (typ.root == null || !typ.root.isPurePrefix) {
                if (((short)((typ.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                    com.pullenti.ner.Token t1 = typ.getEndToken();
                    boolean ok = true;
                    boolean ok1 = false;
                    if (t1.getNext() != null && t1.getNext().isChar(',')) {
                        t1 = t1.getNext();
                        ok1 = true;
                        if (t1.getNext() != null && t1.getNext().isValue("КАК", null)) 
                            t1 = t1.getNext();
                        else 
                            ok = false;
                    }
                    if (t1.getNext() != null && t1.getNext().isValue("КАК", null)) {
                        t1 = t1.getNext();
                        ok1 = true;
                    }
                    if (t1.getNext() != null && t1.getNext().isChar(':')) 
                        t1 = t1.getNext();
                    if (t1 == t && t1.isNewlineAfter()) 
                        ok = false;
                    rt = null;
                    if (ok) {
                        if (!ok1 && typ.getCoef() > 0) 
                            ok1 = true;
                        if (ok1) 
                            rt = tryAttachOrg(t1.getNext(), AttachType.MULTIPLE, typ, false, -1);
                    }
                    if (rt != null) {
                        _doPostAnalyze(rt);
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                        res.add(rt);
                        OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
                        if (ok1) 
                            rt.setBeginToken(t);
                        t1 = rt.getEndToken().getNext();
                        ok = true;
                        for (; t1 != null; t1 = t1.getNext()) {
                            if (t1.isNewlineBefore()) {
                                ok = false;
                                break;
                            }
                            boolean last = false;
                            if (t1.isChar(',')) {
                            }
                            else if (t1.isAnd() || t1.isOr()) 
                                last = true;
                            else {
                                if (res.size() < 2) 
                                    ok = false;
                                break;
                            }
                            t1 = t1.getNext();
                            com.pullenti.ner._org.internal.OrgItemTypeToken typ1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, true);
                            if (typ1 != null) {
                                ok = false;
                                break;
                            }
                            rt = tryAttachOrg(t1, AttachType.MULTIPLE, typ, false, -1);
                            if (rt != null && rt.getBeginToken() == rt.getEndToken()) {
                                if (!rt.getBeginToken().getMorphClassInDictionary().isUndefined() && rt.getBeginToken().chars.isAllUpper()) 
                                    rt = null;
                            }
                            if (rt == null) {
                                if (res.size() < 2) 
                                    ok = false;
                                break;
                            }
                            _doPostAnalyze(rt);
                            res.add(rt);
                            if (res.size() > 100) {
                                ok = false;
                                break;
                            }
                            __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
                            __org.addType(typ, false);
                            if (last) 
                                break;
                            t1 = rt.getEndToken();
                        }
                        if (ok && res.size() > 1) 
                            return res;
                    }
                }
            }
        }
        rt = null;
        if (typ != null && ((typ.isDep() || typ.canBeDepBeforeOrganization))) {
            rt = tryAttachDepBeforeOrg(typ, null);
            if (rt == null) 
                rt = tryAttachDepAfterOrg(typ);
            if (rt == null) 
                rt = tryAttachOrg(typ.getEndToken().getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
        }
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (((step == 0 && rt == null && tt != null) && !tt.chars.isAllLower() && tt.chars.isCyrillicLetter()) && tt.getMorphClassInDictionary().isUndefined()) {
            String s = tt.term;
            if (((s.startsWith("ГУ") || s.startsWith("РУ"))) && s.length() > 3 && ((s.length() > 4 || com.pullenti.unisharp.Utils.stringsEq(s, "ГУВД")))) {
                tt.term = (com.pullenti.unisharp.Utils.stringsEq(s, "ГУВД") ? "МВД" : tt.term.substring(2));
                short inv = tt.invariantPrefixLengthOfMorphVars;
                tt.invariantPrefixLengthOfMorphVars = (short)0;
                short max = tt.maxLengthOfMorphVars;
                tt.maxLengthOfMorphVars = (short)tt.term.length();
                com.pullenti.ner._org.internal.OrgItemTypeToken.recalcData(tt);
                rt = tryAttachOrg(tt, AttachType.NORMALAFTERDEP, null, false, -1);
                tt.term = s;
                tt.invariantPrefixLengthOfMorphVars = inv;
                tt.maxLengthOfMorphVars = max;
                com.pullenti.ner._org.internal.OrgItemTypeToken.recalcData(tt);
                if (rt != null) {
                    if (ad != null && ad.locOrgs.tryAttach(tt, null, false) != null) 
                        rt = null;
                    if (t.kit.ontology != null && t.kit.ontology.attachToken(OrganizationReferent.OBJ_TYPENAME, tt) != null) 
                        rt = null;
                }
                if (rt != null) {
                    typ = new com.pullenti.ner._org.internal.OrgItemTypeToken(tt, tt);
                    typ.typ = (s.startsWith("ГУ") ? "главное управление" : "региональное управление");
                    com.pullenti.ner.ReferentToken rt0 = tryAttachDepBeforeOrg(typ, rt);
                    if (rt0 != null) {
                        if (ad != null) 
                            rt.referent = ad.registerReferent(rt.referent);
                        rt.referent.addOccurence(new com.pullenti.ner.TextAnnotation(t, rt.getEndToken(), rt.referent));
                        ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class));
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> li2 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                        _doPostAnalyze(rt0);
                        li2.add(rt0);
                        return li2;
                    }
                }
            }
            else if ((((((((((s.charAt(0) == 'У' && s.length() > 3 && tt.getMorphClassInDictionary().isUndefined())) || com.pullenti.unisharp.Utils.stringsEq(s, "ОВД") || com.pullenti.unisharp.Utils.stringsEq(s, "РОВД")) || com.pullenti.unisharp.Utils.stringsEq(s, "ОМВД") || com.pullenti.unisharp.Utils.stringsEq(s, "ОСБ")) || com.pullenti.unisharp.Utils.stringsEq(s, "УПФ") || com.pullenti.unisharp.Utils.stringsEq(s, "УФНС")) || com.pullenti.unisharp.Utils.stringsEq(s, "ИФНС") || com.pullenti.unisharp.Utils.stringsEq(s, "ИНФС")) || com.pullenti.unisharp.Utils.stringsEq(s, "УВД") || com.pullenti.unisharp.Utils.stringsEq(s, "УФМС")) || com.pullenti.unisharp.Utils.stringsEq(s, "УФСБ") || com.pullenti.unisharp.Utils.stringsEq(s, "ОУФМС")) || com.pullenti.unisharp.Utils.stringsEq(s, "ОФМС") || com.pullenti.unisharp.Utils.stringsEq(s, "УФК")) || com.pullenti.unisharp.Utils.stringsEq(s, "УФССП")) {
                if (com.pullenti.unisharp.Utils.stringsEq(s, "ОВД") || com.pullenti.unisharp.Utils.stringsEq(s, "УВД") || com.pullenti.unisharp.Utils.stringsEq(s, "РОВД")) 
                    tt.term = "МВД";
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "ОСБ")) 
                    tt.term = "СБЕРБАНК";
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "УПФ")) 
                    tt.term = "ПФР";
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "УФНС") || com.pullenti.unisharp.Utils.stringsEq(s, "ИФНС") || com.pullenti.unisharp.Utils.stringsEq(s, "ИНФС")) 
                    tt.term = "ФНС";
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "УФМС") || com.pullenti.unisharp.Utils.stringsEq(s, "ОУФМС") || com.pullenti.unisharp.Utils.stringsEq(s, "ОФМС")) 
                    tt.term = "ФМС";
                else 
                    tt.term = tt.term.substring(1);
                short inv = tt.invariantPrefixLengthOfMorphVars;
                tt.invariantPrefixLengthOfMorphVars = (short)0;
                short max = tt.maxLengthOfMorphVars;
                tt.maxLengthOfMorphVars = (short)tt.term.length();
                com.pullenti.ner._org.internal.OrgItemTypeToken.recalcData(tt);
                rt = tryAttachOrg(tt, AttachType.NORMALAFTERDEP, null, false, -1);
                tt.term = s;
                tt.invariantPrefixLengthOfMorphVars = inv;
                tt.maxLengthOfMorphVars = max;
                com.pullenti.ner._org.internal.OrgItemTypeToken.recalcData(tt);
                if (rt != null) {
                    OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (org1.getGeoObjects().size() == 0 && rt.getEndToken().getNext() != null) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null && g.isState()) {
                            org1.addGeoObject(g);
                            rt.setEndToken(rt.getEndToken().getNext());
                        }
                    }
                    typ = new com.pullenti.ner._org.internal.OrgItemTypeToken(tt, tt);
                    typ.typ = (s.charAt(0) == 'О' ? "отделение" : (s.charAt(0) == 'И' ? "инспекция" : "управление"));
                    com.pullenti.morph.MorphGender gen = (s.charAt(0) == 'И' ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.NEUTER);
                    if (s.startsWith("ОУ")) 
                        typ.typ = "управление";
                    else if (s.startsWith("РО")) {
                        typ.typ = "отдел";
                        typ.altTyp = "районный отдел";
                        typ.nameIsName = true;
                        gen = com.pullenti.morph.MorphGender.MASCULINE;
                    }
                    com.pullenti.ner.ReferentToken rt0 = tryAttachDepBeforeOrg(typ, rt);
                    if (rt0 != null) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class);
                        org0.addProfile(OrgProfile.UNIT);
                        if (org0.getNumber() == null && !tt.isNewlineAfter()) {
                            com.pullenti.ner._org.internal.OrgItemNumberToken num = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(tt.getNext(), true, typ);
                            if (num != null) {
                                org0.setNumber(num.number);
                                rt0.setEndToken(num.getEndToken());
                            }
                        }
                        Object _geo;
                        if (rt0.referent.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null) {
                            if ((((_geo = isGeo(rt0.getEndToken().getNext(), false)))) != null) {
                                if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addGeoObject(_geo)) 
                                    rt0.setEndToken(getGeoEndToken(_geo, rt0.getEndToken().getNext()));
                            }
                            else if (rt0.getEndToken().getWhitespacesAfterCount() < 3) {
                                com.pullenti.ner._org.internal.OrgItemNameToken nam = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(rt0.getEndToken().getNext(), null, false, true);
                                if (nam != null && !nam.value.startsWith("СУБЪЕКТ")) {
                                    if ((((_geo = isGeo(nam.getEndToken().getNext(), false)))) != null) {
                                        if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addGeoObject(_geo)) 
                                            rt0.setEndToken(getGeoEndToken(_geo, nam.getEndToken().getNext()));
                                        ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).addName(nam.value, true, null);
                                    }
                                }
                            }
                        }
                        if (rt0.referent.getSlots().size() > 3) {
                            if (tt.getPrevious() != null && ((tt.getPrevious().getMorph()._getClass().isAdjective() && !tt.getPrevious().getMorph()._getClass().isVerb())) && tt.getWhitespacesBeforeCount() == 1) {
                                String adj = com.pullenti.morph.MorphologyService.getWordform(tt.getPrevious().getSourceText().toUpperCase(), com.pullenti.morph.MorphBaseInfo._new2583(com.pullenti.morph.MorphClass.ADJECTIVE, gen, tt.getPrevious().getMorph().getLanguage()));
                                if (adj != null && !adj.startsWith("УПОЛНОМОЧ") && !adj.startsWith("ОПЕРУПОЛНОМОЧ")) {
                                    String tyy = (adj.toLowerCase() + " " + typ.typ);
                                    rt0.setBeginToken(tt.getPrevious());
                                    if (rt0.getBeginToken().getPrevious() != null && rt0.getBeginToken().getPrevious().isHiphen() && rt0.getBeginToken().getPrevious().getPrevious() != null) {
                                        com.pullenti.ner.Token tt0 = rt0.getBeginToken().getPrevious().getPrevious();
                                        if (tt0.chars.equals(rt0.getBeginToken().chars) && (tt0 instanceof com.pullenti.ner.TextToken)) {
                                            adj = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt0, com.pullenti.ner.TextToken.class)).term;
                                            if (tt0.getMorph()._getClass().isAdjective() && !tt0.getMorph().containsAttr("неизм.", null)) 
                                                adj = com.pullenti.morph.MorphologyService.getWordform(adj, com.pullenti.morph.MorphBaseInfo._new2583(com.pullenti.morph.MorphClass.ADJECTIVE, gen, tt0.getMorph().getLanguage()));
                                            tyy = (adj.toLowerCase() + " " + tyy);
                                            rt0.setBeginToken(tt0);
                                        }
                                    }
                                    if (typ.nameIsName) 
                                        org0.addName(tyy.toUpperCase(), true, null);
                                    else 
                                        org0.addTypeStr(tyy);
                                }
                            }
                            for (com.pullenti.ner.geo.GeoReferent g : org1.getGeoObjects()) {
                                if (!g.isState()) {
                                    com.pullenti.ner.Slot sl = org1.findSlot(OrganizationReferent.ATTR_GEO, g, true);
                                    if (sl != null) 
                                        org1.getSlots().remove(sl);
                                    if (rt.getBeginToken().getBeginChar() < rt0.getBeginToken().getBeginChar()) 
                                        rt0.setBeginToken(rt.getBeginToken());
                                    org0.addGeoObject(g);
                                    org1.moveExtReferent(org0, g);
                                }
                            }
                            if (ad != null) 
                                rt.referent = ad.registerReferent(rt.referent);
                            rt.referent.addOccurence(new com.pullenti.ner.TextAnnotation(t, rt.getEndToken(), rt.referent));
                            ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, OrganizationReferent.class)).setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class));
                            _doPostAnalyze(rt0);
                            java.util.ArrayList<com.pullenti.ner.ReferentToken> li2 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                            li2.add(rt0);
                            return li2;
                        }
                    }
                    rt = null;
                }
            }
        }
        if (rt == null) {
            if (step > 0 && typ == null) {
                if (!com.pullenti.ner.core.BracketHelper.isBracket(t, false)) {
                    if (!t.chars.isLetter()) 
                        return null;
                    if (t.chars.isAllLower()) 
                        return null;
                }
            }
            rt = tryAttachOrg(t, AttachType.NORMAL, null, false, step);
            if (rt == null && step == 0) 
                rt = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttachOrg(t, false);
            if (rt != null) {
            }
        }
        if (((rt == null && step == 1 && typ != null) && typ.isDep() && typ.root != null) && !typ.root.canBeNormalDep) {
            if (com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(typ.getBeginToken().getPrevious())) 
                rt = tryAttachDep(typ, AttachType.HIGH, true);
        }
        if (rt == null && step == 0 && t != null) {
            boolean ok = false;
            if (t.getLengthChar() > 2 && !t.chars.isAllLower() && t.chars.isLatinLetter()) 
                ok = true;
            else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                ok = true;
            if (ok && t.getWhitespacesBeforeCount() != 1) 
                ok = false;
            if (ok && !com.pullenti.ner._org.internal.OrgItemTypeToken.checkPersonProperty(t.getPrevious())) 
                ok = false;
            if (ok) {
                OrganizationReferent __org = new OrganizationReferent();
                rt = new com.pullenti.ner.ReferentToken(__org, t, t, null);
                if (t.chars.isLatinLetter() && com.pullenti.ner.core.NumberHelper.tryParseRoman(t) == null) {
                    com.pullenti.ner._org.internal.OrgItemNameToken nam = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(t, null, false, true);
                    if (nam != null) {
                        StringBuilder _name = new StringBuilder();
                        _name.append(nam.value);
                        rt.setEndToken(nam.getEndToken());
                        for (com.pullenti.ner.Token ttt = nam.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (!ttt.chars.isLatinLetter()) 
                                break;
                            nam = com.pullenti.ner._org.internal.OrgItemNameToken.tryAttach(ttt, null, false, false);
                            if (nam == null) 
                                break;
                            rt.setEndToken(nam.getEndToken());
                            if (!nam.isStdTail) 
                                _name.append(" ").append(nam.value);
                            else {
                                com.pullenti.ner._org.internal.OrgItemEngItem ei = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(nam.getBeginToken(), false);
                                if (ei != null) {
                                    __org.addTypeStr(ei.fullValue);
                                    if (ei.shortValue != null) 
                                        __org.addTypeStr(ei.shortValue);
                                }
                            }
                        }
                        __org.addName(_name.toString(), true, null);
                    }
                }
                else {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        com.pullenti.ner.ReferentToken rt11 = tryAttachOrg(t.getNext(), AttachType.NORMAL, null, false, -1);
                        if (rt11 != null && ((rt11.getEndToken() == br.getEndToken().getPrevious() || rt11.getEndToken() == br.getEndToken()))) {
                            rt11.setBeginToken(t);
                            rt11.setEndToken(br.getEndToken());
                            rt = rt11;
                            __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt11.referent, OrganizationReferent.class);
                        }
                        else {
                            __org.addName(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), true, null);
                            __org.addName(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO), true, br.getBeginToken().getNext());
                            if (br.getBeginToken().getNext() == br.getEndToken().getPrevious() && br.getBeginToken().getNext().getMorphClassInDictionary().isUndefined()) {
                                for (com.pullenti.morph.MorphBaseInfo wf : br.getBeginToken().getNext().getMorph().getItems()) {
                                    if (wf.getCase().isGenitive() && (wf instanceof com.pullenti.morph.MorphWordForm)) 
                                        __org.addName(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase, true, null);
                                }
                            }
                            rt.setEndToken(br.getEndToken());
                        }
                    }
                }
                if (__org.getSlots().size() == 0) 
                    rt = null;
            }
        }
        if (rt == null) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null || br.getLengthChar() > 100) 
                    br = null;
                if (br != null) {
                    com.pullenti.ner.Token t1 = br.getEndToken().getNext();
                    if (t1 != null && t1.isComma()) 
                        t1 = t1.getNext();
                    if (t1 != null && (t1.getWhitespacesBeforeCount() < 3)) {
                        if ((((typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t1, false)))) != null && typ.root != null && typ.root.getTyp() == com.pullenti.ner._org.internal.OrgItemTypeTyp.PREFIX) {
                            com.pullenti.ner.Token t2 = typ.getEndToken().getNext();
                            boolean ok = false;
                            if (t2 == null || t2.isNewlineBefore()) 
                                ok = true;
                            else if (t2.isCharOf(".,:;")) 
                                ok = true;
                            else if (t2 instanceof com.pullenti.ner.ReferentToken) 
                                ok = true;
                            if (ok) {
                                OrganizationReferent __org = new OrganizationReferent();
                                rt = new com.pullenti.ner.ReferentToken(__org, t, typ.getEndToken(), null);
                                __org.addType(typ, false);
                                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                                __org.addName(nam, true, null);
                                com.pullenti.ner.ReferentToken rt11 = tryAttachOrg(br.getBeginToken().getNext(), AttachType.NORMAL, null, false, -1);
                                if (rt11 != null && rt11.getEndChar() <= typ.getEndChar()) 
                                    __org.mergeSlots(rt11.referent, true);
                            }
                        }
                    }
                }
            }
            if (rt == null) 
                return null;
        }
        _doPostAnalyze(rt);
        if (step > 0) {
            com.pullenti.ner.MetaToken mt = _checkAliasAfter(rt, rt.getEndToken().getNext());
            if (mt != null) {
                if (ad != null) {
                    com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                    term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt.referent, false);
                    ad.aliases.add(term);
                }
                rt.setEndToken(mt.getEndToken());
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> li = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        li.add(rt);
        com.pullenti.ner.Token tt1 = rt.getEndToken().getNext();
        if (tt1 != null && tt1.isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) 
                tt1 = br.getEndToken().getNext();
        }
        if (tt1 != null && tt1.isCommaAnd()) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1.getNext(), true, false)) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt.getEndToken(), true, null, false)) {
                    boolean ok = false;
                    for (com.pullenti.ner.Token ttt = tt1; ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isChar('.')) {
                            ok = true;
                            break;
                        }
                        if (ttt.isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(ttt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br1 != null) {
                                ttt = br1.getEndToken();
                                continue;
                            }
                        }
                        if (!ttt.isCommaAnd()) 
                            break;
                        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt.getNext(), true, false)) 
                            break;
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br == null) 
                            break;
                        boolean addTyp = false;
                        com.pullenti.ner.ReferentToken rt1 = _TryAttachOrg_(ttt.getNext().getNext(), ttt.getNext().getNext(), null, true, AttachType.NORMAL, null, false);
                        if (rt1 == null || (rt1.getEndChar() < (br.getEndChar() - 1))) {
                            addTyp = true;
                            rt1 = _TryAttachOrg_(ttt.getNext(), ttt.getNext(), null, true, AttachType.HIGH, null, false);
                        }
                        if (rt1 == null || (rt1.getEndChar() < (br.getEndChar() - 1))) 
                            break;
                        li.add(rt1);
                        OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, OrganizationReferent.class);
                        if (typ != null) 
                            ok = true;
                        if (org1.getTypes().size() == 0) 
                            addTyp = true;
                        if (addTyp) {
                            if (typ != null) 
                                org1.addType(typ, false);
                            String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                            if (s != null) {
                                boolean ex = false;
                                for (String n : org1.getNames()) {
                                    if (s.startsWith(n)) {
                                        ex = true;
                                        break;
                                    }
                                }
                                if (!ex) 
                                    org1.addName(s, true, br.getBeginToken().getNext());
                            }
                        }
                        if (ttt.isAnd()) {
                            ok = true;
                            break;
                        }
                        ttt = rt1.getEndToken();
                    }
                    if (!ok && li.size() > 1) 
                        for(int jjj = 1 + li.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) li.remove(jjj);
                }
            }
        }
        return li;
    }

    private static com.pullenti.ner.ReferentToken tryAttachSpec(com.pullenti.ner.Token t) {
        com.pullenti.ner.ReferentToken rt = tryAttachPropNames(t);
        if (rt == null) 
            rt = tryAttachPoliticParty(t, false);
        if (rt == null) 
            rt = tryAttachArmy(t);
        return rt;
    }

    private static boolean _corrBrackets(com.pullenti.ner.ReferentToken rt) {
        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt.getBeginToken().getPrevious(), true, false) || !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt.getEndToken().getNext(), true, null, false)) 
            return false;
        rt.setBeginToken(rt.getBeginToken().getPrevious());
        rt.setEndToken(rt.getEndToken().getNext());
        return true;
    }

    private static void _doPostAnalyze(com.pullenti.ner.ReferentToken rt) {
        if (rt.getMorph().getCase().isUndefined()) {
            if (!rt.getBeginToken().chars.isAllUpper()) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(rt.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 == null) 
                    npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(rt.getBeginToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 != null) 
                    rt.setMorph(npt1.getMorph());
            }
        }
        OrganizationReferent o = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
        if ((rt.kit.ontology != null && o.ontologyItems == null && o.getHigher() == null) && o.m_TempParentOrg == null) {
            java.util.ArrayList<com.pullenti.ner.ExtOntologyItem> ot = rt.kit.ontology.attachReferent(o);
            if (ot != null && ot.size() == 1 && (ot.get(0).referent instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ot.get(0).referent, OrganizationReferent.class);
                o.mergeSlots(oo, false);
                o.ontologyItems = ot;
                for (com.pullenti.ner.Slot sl : o.getSlots()) {
                    if (sl.getValue() instanceof com.pullenti.ner.Referent) {
                        boolean ext = false;
                        for (com.pullenti.ner.Slot ss : oo.getSlots()) {
                            if (ss.getValue() == sl.getValue()) {
                                ext = true;
                                break;
                            }
                        }
                        if (!ext) 
                            continue;
                        com.pullenti.ner.Referent rr = ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class)).clone();
                        rr.getOccurrence().clear();
                        o.uploadSlot(sl, rr);
                        com.pullenti.ner.ReferentToken rtEx = new com.pullenti.ner.ReferentToken(rr, rt.getBeginToken(), rt.getEndToken(), null);
                        rtEx.setDefaultLocalOnto(rt.kit.processor);
                        o.addExtReferent(rtEx);
                        for (com.pullenti.ner.Slot sss : rr.getSlots()) {
                            if (sss.getValue() instanceof com.pullenti.ner.Referent) {
                                com.pullenti.ner.Referent rrr = ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sss.getValue(), com.pullenti.ner.Referent.class)).clone();
                                rrr.getOccurrence().clear();
                                rr.uploadSlot(sss, rrr);
                                com.pullenti.ner.ReferentToken rtEx2 = new com.pullenti.ner.ReferentToken(rrr, rt.getBeginToken(), rt.getEndToken(), null);
                                rtEx2.setDefaultLocalOnto(rt.kit.processor);
                                ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class)).addExtReferent(rtEx2);
                            }
                        }
                    }
                }
            }
        }
        if (o.getHigher() == null && o.m_TempParentOrg == null) {
            if ((rt.getBeginToken().getPrevious() instanceof com.pullenti.ner.ReferentToken) && (rt.getBeginToken().getPrevious().getReferent() instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.getBeginToken().getPrevious().getReferent(), OrganizationReferent.class);
                if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(oo, o, false)) 
                    o.m_TempParentOrg = oo;
            }
            if (o.m_TempParentOrg == null && (rt.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && (rt.getEndToken().getNext().getReferent() instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(oo, o, false)) 
                    o.m_TempParentOrg = oo;
            }
            if (o.m_TempParentOrg == null) {
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(rt.getEndToken().getNext(), AttachType.NORMALAFTERDEP, null, false, -1);
                if (rt1 != null && rt.getEndToken().getNext() == rt1.getBeginToken()) {
                    if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, OrganizationReferent.class), o, false)) 
                        o.m_TempParentOrg = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, OrganizationReferent.class);
                }
            }
        }
        if (rt.getEndToken().getNext() == null) 
            return;
        _corrBrackets(rt);
        if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().getMorph()._getClass().isAdjective() && (rt.getWhitespacesBeforeCount() < 2)) {
            if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).getGeoObjects().size() == 0) {
                Object _geo = isGeo(rt.getBeginToken().getPrevious(), true);
                if (_geo != null) {
                    if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).addGeoObject(_geo)) 
                        rt.setBeginToken(rt.getBeginToken().getPrevious());
                }
            }
        }
        com.pullenti.ner.Token ttt = rt.getEndToken().getNext();
        int errs = 1;
        boolean br = false;
        if (ttt != null && ttt.isChar('(')) {
            br = true;
            ttt = ttt.getNext();
        }
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
        boolean _keyword = false;
        boolean hasInn = false;
        int hasOk = 0;
        com.pullenti.ner.Token te = null;
        for (; ttt != null; ttt = ttt.getNext()) {
            if (ttt.isCharOf(",;") || ttt.getMorph()._getClass().isPreposition()) 
                continue;
            if (ttt.isChar(')')) {
                if (br) 
                    te = ttt;
                break;
            }
            com.pullenti.ner.Referent rr = ttt.getReferent();
            if (rr != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "ADDRESS") || com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "DATE") || ((com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "GEO") && br))) {
                    if (_keyword || br || (ttt.getWhitespacesBeforeCount() < 2)) {
                        refs.add(rr);
                        te = ttt;
                        continue;
                    }
                    break;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "URI")) {
                    String sch = rr.getStringValue("SCHEME");
                    if (sch == null) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsEq(sch, "ИНН")) {
                        errs = 5;
                        hasInn = true;
                    }
                    else if (sch.startsWith("ОК")) 
                        hasOk++;
                    else if (com.pullenti.unisharp.Utils.stringsNe(sch, "КПП") && com.pullenti.unisharp.Utils.stringsNe(sch, "ОГРН") && !br) 
                        break;
                    refs.add(rr);
                    te = ttt;
                    if (ttt.getNext() != null && ttt.getNext().isChar('(')) {
                        com.pullenti.ner.core.BracketSequenceToken brrr = com.pullenti.ner.core.BracketHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (brrr != null) 
                            ttt = brrr.getEndToken();
                    }
                    continue;
                }
                else if (rr == rt.referent) 
                    continue;
            }
            if (ttt.isNewlineBefore() && !br) 
                break;
            if (ttt instanceof com.pullenti.ner.TextToken) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) {
                    if ((npt.getEndToken().isValue("ДАТА", null) || npt.getEndToken().isValue("РЕГИСТРАЦИЯ", null) || npt.getEndToken().isValue("ЛИЦО", null)) || npt.getEndToken().isValue("ЮР", null) || npt.getEndToken().isValue("АДРЕС", null)) {
                        ttt = npt.getEndToken();
                        _keyword = true;
                        continue;
                    }
                }
                if (ttt.isValue("REGISTRATION", null) && ttt.getNext() != null && ttt.getNext().isValue("NUMBER", null)) {
                    StringBuilder tmp = new StringBuilder();
                    for (com.pullenti.ner.Token tt3 = ttt.getNext().getNext(); tt3 != null; tt3 = tt3.getNext()) {
                        if (tt3.isWhitespaceBefore() && tmp.length() > 0) 
                            break;
                        if (((tt3.isCharOf(":") || tt3.isHiphen())) && tmp.length() == 0) 
                            continue;
                        if (tt3 instanceof com.pullenti.ner.TextToken) 
                            tmp.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt3, com.pullenti.ner.TextToken.class)).term);
                        else if (tt3 instanceof com.pullenti.ner.NumberToken) 
                            tmp.append(tt3.getSourceText());
                        else 
                            break;
                        rt.setEndToken((ttt = tt3));
                    }
                    if (tmp.length() > 0) 
                        rt.referent.addSlot(OrganizationReferent.ATTR_MISC, tmp.toString(), false, 0);
                    continue;
                }
                if ((ttt.isValue("REGISTERED", null) && ttt.getNext() != null && ttt.getNext().isValue("IN", null)) && (ttt.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && (ttt.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    rt.referent.addSlot(OrganizationReferent.ATTR_MISC, ttt.getNext().getNext().getReferent(), false, 0);
                    rt.setEndToken((ttt = ttt.getNext().getNext()));
                    continue;
                }
                if (br) {
                    com.pullenti.ner._org.internal.OrgItemTypeToken otyp = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(ttt, true);
                    if (otyp != null && (ttt.getWhitespacesBeforeCount() < 2) && otyp.geo == null) {
                        OrganizationReferent or1 = new OrganizationReferent();
                        or1.addType(otyp, false);
                        if (!com.pullenti.ner._org.internal.OrgItemTypeToken.isTypesAntagonisticOO(o, or1) && otyp.getEndToken().getNext() != null && otyp.getEndToken().getNext().isChar(')')) {
                            o.addType(otyp, false);
                            rt.setEndToken((ttt = otyp.getEndToken()));
                            if (br && ttt.getNext() != null && ttt.getNext().isChar(')')) {
                                rt.setEndToken(ttt.getNext());
                                break;
                            }
                            continue;
                        }
                    }
                }
            }
            _keyword = false;
            if ((--errs) <= 0) 
                break;
        }
        if (te != null && refs.size() > 0 && ((te.isChar(')') || hasInn || hasOk > 0))) {
            for (com.pullenti.ner.Referent rr : refs) {
                if (com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), gEONAME)) 
                    ((OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class)).addGeoObject(rr);
                else 
                    rt.referent.addSlot(OrganizationReferent.ATTR_MISC, rr, false, 0);
            }
            rt.setEndToken(te);
        }
        if ((rt.getWhitespacesBeforeCount() < 2) && (rt.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && rt.getBeginToken().getPrevious().chars.isAllUpper()) {
            String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(rt.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class)).term;
            for (com.pullenti.ner.Slot s : o.getSlots()) {
                if (s.getValue() instanceof String) {
                    String a = com.pullenti.ner.core.MiscHelper.getAbbreviation((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
                    if (a != null && com.pullenti.unisharp.Utils.stringsEq(a, term)) {
                        rt.setBeginToken(rt.getBeginToken().getPrevious());
                        break;
                    }
                }
            }
        }
    }

    private static com.pullenti.ner.ReferentToken _tryAttachOrgByAlias(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        boolean br = false;
        if (t0.getNext() != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0, true, false)) {
            t = t0.getNext();
            br = true;
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) {
            if (t.getLengthChar() > 3) {
            }
            else if (t.getLengthChar() > 1 && t.chars.isAllUpper()) {
            }
            else 
                return null;
        }
        else 
            return null;
        com.pullenti.ner._org.internal.OrgAnalyzerData ad = OrganizationAnalyzer.getData(t);
        if (ad != null) {
            com.pullenti.ner.core.TerminToken tok = ad.aliases.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.Referent.class), t0, tok.getEndToken(), null);
                if (br) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tok.getEndToken().getNext(), true, null, false)) 
                        rt0.setEndToken(tok.getEndToken().getNext());
                    else 
                        return null;
                }
                return rt0;
            }
        }
        if (!br) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                return null;
            if (!com.pullenti.ner._org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())) 
                return null;
            if (t.chars.isLatinLetter()) {
                if (t.getNext() != null && t.getNext().chars.isLatinLetter()) 
                    return null;
            }
            else if (t.getNext() != null && ((t.getNext().chars.isCyrillicLetter() || !t.getNext().chars.isAllLower()))) 
                return null;
        }
        else if (!com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext(), true, null, false)) 
            return null;
        int cou = 0;
        for (com.pullenti.ner.Token ttt = t.getPrevious(); ttt != null && (cou < 100); ttt = ttt.getPrevious(),cou++) {
            OrganizationReferent org00 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(ttt.getReferent(), OrganizationReferent.class);
            if (org00 == null) 
                continue;
            for (String n : org00.getNames()) {
                String str = n;
                int ii = n.indexOf(' ');
                if (ii > 0) 
                    str = n.substring(0, 0 + ii);
                if (t.isValue(str, null)) {
                    if (ad != null) 
                        ad.aliases.add(com.pullenti.ner.core.Termin._new100(str, org00));
                    String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    if (ii < 0) 
                        org00.addName(term, true, t);
                    if (br) 
                        t = t.getNext();
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(org00, t0, t, null);
                    return rt;
                }
            }
        }
        return null;
    }

    private static com.pullenti.ner.Token attachMiddleAttributes(OrganizationReferent __org, com.pullenti.ner.Token t) {
        com.pullenti.ner.Token te = null;
        for (; t != null; t = t.getNext()) {
            com.pullenti.ner._org.internal.OrgItemNumberToken ont = com.pullenti.ner._org.internal.OrgItemNumberToken.tryAttach(t, false, null);
            if (ont != null) {
                __org.setNumber(ont.number);
                te = (t = ont.getEndToken());
                continue;
            }
            com.pullenti.ner._org.internal.OrgItemEponymToken oet = com.pullenti.ner._org.internal.OrgItemEponymToken.tryAttach(t, false);
            if (oet != null) {
                for (String v : oet.eponyms) {
                    __org.addEponym(v);
                }
                te = (t = oet.getEndToken());
                continue;
            }
            break;
        }
        return te;
    }

    private static final String gEONAME = "GEO";

    private static Object isGeo(com.pullenti.ner.Token t, boolean canBeAdjective) {
        if (t == null) 
            return null;
        if (t.isValue("В", null) && t.getNext() != null) 
            t = t.getNext();
        com.pullenti.ner.Referent r = t.getReferent();
        if (r != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                if (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken().isValue("ТЕРРИТОРИЯ", null)) 
                    return null;
                if (t.getWhitespacesBeforeCount() <= 15 || t.getMorph().getCase().isGenitive()) 
                    return r;
            }
            if (r instanceof com.pullenti.ner.address.AddressReferent) {
                com.pullenti.ner.Token tt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken();
                if (tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), gEONAME)) {
                    if (t.getWhitespacesBeforeCount() < 3) 
                        return tt.getReferent();
                }
            }
            return null;
        }
        if (t.getWhitespacesBeforeCount() > 15 && !canBeAdjective) 
            return null;
        if (t.isValue("ТЕРРИТОРИЯ", null) || t.isValue("ПОСЕЛЕНИЕ", null)) 
            return null;
        com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t, null);
        if (rt == null) 
            return null;
        if (t.getPrevious() != null && t.getPrevious().isValue("ОРДЕН", null)) 
            return null;
        if (!canBeAdjective) {
            if (rt.getMorph()._getClass().isAdjective()) 
                return null;
        }
        return rt;
    }

    private static com.pullenti.ner.Token getGeoEndToken(Object _geo, com.pullenti.ner.Token t) {
        if (_geo instanceof com.pullenti.ner.ReferentToken) {
            if (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(_geo, com.pullenti.ner.ReferentToken.class)).getReferent() instanceof com.pullenti.ner.address.AddressReferent) 
                return t.getPrevious();
            return ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(_geo, com.pullenti.ner.ReferentToken.class)).getEndToken();
        }
        else if (t != null && t.getNext() != null && t.getMorph()._getClass().isPreposition()) 
            return t.getNext();
        else 
            return t;
    }

    private static com.pullenti.ner.Token attachTailAttributes(OrganizationReferent __org, com.pullenti.ner.Token t, boolean attachForNewOrg, AttachType attachTyp, boolean isGlobal) {
        com.pullenti.ner.Token t1 = null;
        OrganizationKind ki = __org.getKind();
        boolean canHasGeo = true;
        if (!canHasGeo) {
            if (__org._typesContains("комитет") || __org._typesContains("академия") || __org._typesContains("инспекция")) 
                canHasGeo = true;
        }
        for (; t != null; t = (t == null ? null : t.getNext())) {
            if (((t.isValue("ПО", null) || t.isValue("В", null) || t.isValue("IN", null))) && t.getNext() != null) {
                if (attachTyp == AttachType.NORMALAFTERDEP) 
                    break;
                if (!canHasGeo) 
                    break;
                Object r = isGeo(t.getNext(), false);
                if (r == null) 
                    break;
                if (!__org.addGeoObject(r)) 
                    break;
                t1 = getGeoEndToken(r, t.getNext());
                t = t1;
                continue;
            }
            if (t.isValue("ИЗ", null) && t.getNext() != null) {
                if (attachTyp == AttachType.NORMALAFTERDEP) 
                    break;
                if (!canHasGeo) 
                    break;
                Object r = isGeo(t.getNext(), false);
                if (r == null) 
                    break;
                if (!__org.addGeoObject(r)) 
                    break;
                t1 = getGeoEndToken(r, t.getNext());
                t = t1;
                continue;
            }
            if (canHasGeo && __org.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null && !t.isNewlineBefore()) {
                Object r = isGeo(t, false);
                if (r != null) {
                    if (!__org.addGeoObject(r)) 
                        break;
                    t = (t1 = getGeoEndToken(r, t));
                    continue;
                }
                if (t.isChar('(')) {
                    r = isGeo(t.getNext(), false);
                    if ((r instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class)).getEndToken().getNext() != null && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class)).getEndToken().getNext().isChar(')')) {
                        if (!__org.addGeoObject(r)) 
                            break;
                        t = (t1 = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class)).getEndToken().getNext());
                        continue;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                        if (!__org.addGeoObject(r)) 
                            break;
                        t = (t1 = t.getNext().getNext());
                        continue;
                    }
                }
            }
            if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (t.getWhitespacesBeforeCount() < 2)) {
                if (__org.findSlot(OrganizationReferent.ATTR_GEO, t.getReferent(), true) != null) {
                    t1 = t;
                    continue;
                }
            }
            if (((t.isValue("ПРИ", null) || t.isValue("В", null))) && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.Referent r = t.getNext().getReferent();
                if (r instanceof OrganizationReferent) {
                    if (t.isValue("В", null) && !com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, OrganizationReferent.class), __org, false)) {
                    }
                    else {
                        __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, OrganizationReferent.class));
                        t1 = t.getNext();
                        t = t1;
                        continue;
                    }
                }
            }
            if (t.chars.isLatinLetter() && (t.getWhitespacesBeforeCount() < 2)) {
                boolean hasLatinName = false;
                for (String s : __org.getNames()) {
                    if (com.pullenti.morph.LanguageHelper.isLatinChar(s.charAt(0))) {
                        hasLatinName = true;
                        break;
                    }
                }
                if (hasLatinName) {
                    com.pullenti.ner._org.internal.OrgItemEngItem eng = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttach(t, false);
                    if (eng != null) {
                        __org.addTypeStr(eng.fullValue);
                        if (eng.shortValue != null) 
                            __org.addTypeStr(eng.shortValue);
                        t = (t1 = eng.getEndToken());
                        continue;
                    }
                }
            }
            Object re = isGeo(t, false);
            if (re == null && t.isChar(',')) 
                re = isGeo(t.getNext(), false);
            if (re != null) {
                if (attachTyp != AttachType.NORMALAFTERDEP) {
                    if ((!canHasGeo && ki != OrganizationKind.BANK && ki != OrganizationKind.FEDERATION) && !__org.getTypes().contains("университет")) 
                        break;
                    if ((__org.toString().indexOf("Сбербанк") >= 0) && __org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                        break;
                    if (!__org.addGeoObject(re)) 
                        break;
                    if (t.isChar(',')) 
                        t = t.getNext();
                    t1 = getGeoEndToken(re, t);
                    if (t1.getEndChar() <= t.getEndChar()) 
                        break;
                    t = t1;
                    continue;
                }
                else 
                    break;
            }
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                if (t.getNext() != null && t.getNext().getReferent() != null) {
                    if (t.getNext().getNext() != br.getEndToken()) 
                        break;
                    com.pullenti.ner.Referent r = t.getNext().getReferent();
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                        if (!__org.addGeoObject(r)) 
                            break;
                        t = (t1 = br.getEndToken());
                        continue;
                    }
                    if ((r instanceof OrganizationReferent) && !isGlobal) {
                        if (!attachForNewOrg && !__org.canBeEquals(r, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                            break;
                        __org.mergeSlots(r, true);
                        t = (t1 = br.getEndToken());
                        continue;
                    }
                    break;
                }
                if (!isGlobal) {
                    if (attachTyp != AttachType.EXTONTOLOGY) {
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true);
                        if (typ != null && typ.getEndToken() == br.getEndToken().getPrevious() && !typ.isDep()) {
                            __org.addType(typ, false);
                            if (typ.name != null) 
                                __org.addTypeStr(typ.name.toLowerCase());
                            t = (t1 = br.getEndToken());
                            continue;
                        }
                    }
                    com.pullenti.ner.ReferentToken rte = com.pullenti.ner._org.internal.OrgItemEngItem.tryAttachOrg(br.getBeginToken(), false);
                    if (rte != null) {
                        if (__org.canBeEquals(rte.referent, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) {
                            __org.mergeSlots(rte.referent, true);
                            t = (t1 = rte.getEndToken());
                            continue;
                        }
                    }
                    String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    if (nam != null) {
                        boolean eq = false;
                        for (com.pullenti.ner.Slot s : __org.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NAME)) {
                                if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(nam, s.getValue().toString())) {
                                    __org.addName(nam, true, br.getBeginToken().getNext());
                                    eq = true;
                                    break;
                                }
                            }
                        }
                        if (eq) {
                            t = (t1 = br.getEndToken());
                            continue;
                        }
                    }
                    boolean oldName = false;
                    com.pullenti.ner.Token tt0 = t.getNext();
                    if (tt0 != null) {
                        if (tt0.isValue("РАНЕЕ", null)) {
                            oldName = true;
                            tt0 = tt0.getNext();
                        }
                        else if (tt0.getMorph()._getClass().isAdjective() && tt0.getNext() != null && ((tt0.getNext().isValue("НАЗВАНИЕ", null) || tt0.getNext().isValue("НАИМЕНОВАНИЕ", null)))) {
                            oldName = true;
                            tt0 = tt0.getNext().getNext();
                        }
                        if (oldName && tt0 != null) {
                            if (tt0.isHiphen() || tt0.isCharOf(",:")) 
                                tt0 = tt0.getNext();
                        }
                    }
                    com.pullenti.ner.ReferentToken rt = tryAttachOrg(tt0, AttachType.HIGH, null, false, 0);
                    if (rt == null) 
                        break;
                    if (!__org.canBeEquals(rt.referent, com.pullenti.ner.core.ReferentsEqualType.FORMERGING)) 
                        break;
                    if (rt.getEndToken() != br.getEndToken().getPrevious()) 
                        break;
                    if (!attachForNewOrg && !__org.canBeEquals(rt.referent, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                        break;
                    if (attachTyp == AttachType.NORMAL) {
                        if (!oldName && !OrganizationReferent.canBeSecondDefinition(__org, (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class))) 
                            break;
                        com.pullenti.ner._org.internal.OrgItemTypeToken typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true);
                        if (typ != null && typ.isDouterOrg) 
                            break;
                    }
                    __org.mergeSlots(rt.referent, true);
                    t = (t1 = br.getEndToken());
                    continue;
                }
                break;
            }
            else if (attachTyp == AttachType.EXTONTOLOGY && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) 
                    __org.addName(nam, true, br.getBeginToken().getNext());
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(t.getNext(), AttachType.HIGH, null, true, -1);
                if (rt1 != null && rt1.getEndToken().getNext() == br.getEndToken()) {
                    __org.mergeSlots(rt1.referent, true);
                    t = (t1 = br.getEndToken());
                }
            }
            else 
                break;
        }
        if (t != null && (t.getWhitespacesBeforeCount() < 2) && ((ki == OrganizationKind.UNDEFINED || ki == OrganizationKind.BANK))) {
            com.pullenti.ner._org.internal.OrgItemTypeToken ty1 = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, false);
            if (ty1 != null && ty1.root != null && ty1.root.isPurePrefix) {
                com.pullenti.ner.ReferentToken rt22 = tryAttachOrg(t, AttachType.NORMAL, null, false, -1);
                if (rt22 == null) {
                    __org.addType(ty1, false);
                    t1 = ty1.getEndToken();
                }
            }
        }
        return t1;
    }

    private static void correctOwnerBefore(com.pullenti.ner.ReferentToken res) {
        if (res == null) 
            return;
        if (((OrganizationReferent)com.pullenti.unisharp.Utils.cast(res.referent, OrganizationReferent.class)).getKind() == OrganizationKind.PRESS) {
            if (res.getBeginToken().isValue("КОРРЕСПОНДЕНТ", null) && res.getBeginToken() != res.getEndToken()) 
                res.setBeginToken(res.getBeginToken().getNext());
        }
        OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(res.referent, OrganizationReferent.class);
        if (__org.getHigher() != null || __org.m_TempParentOrg != null) 
            return;
        OrganizationReferent hiBefore = null;
        int couBefore = 0;
        com.pullenti.ner.Token t0 = null;
        for (com.pullenti.ner.Token t = res.getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
            couBefore += t.getWhitespacesAfterCount();
            if (t.isChar(',')) {
                couBefore += 5;
                continue;
            }
            else if (t.isValue("ПРИ", null)) 
                return;
            if (t instanceof com.pullenti.ner.ReferentToken) {
                if ((((hiBefore = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class)))) != null) 
                    t0 = t;
            }
            break;
        }
        if (t0 == null) 
            return;
        if (!com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(hiBefore, __org, false)) 
            return;
        if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(__org, hiBefore, false)) 
            return;
        OrganizationReferent hiAfter = null;
        int couAfter = 0;
        for (com.pullenti.ner.Token t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            couBefore += t.getWhitespacesBeforeCount();
            if (t.isChar(',') || t.isValue("ПРИ", null)) {
                couAfter += 5;
                continue;
            }
            if (t instanceof com.pullenti.ner.ReferentToken) {
                hiAfter = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
                break;
            }
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t, AttachType.NORMAL, null, false, -1);
            if (rt != null) 
                hiAfter = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
            break;
        }
        if (hiAfter != null) {
            if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(hiAfter, __org, false)) {
                if (couBefore >= couAfter) 
                    return;
            }
        }
        if (__org.getKind() == hiBefore.getKind() && __org.getKind() != OrganizationKind.UNDEFINED) {
            if (__org.getKind() != OrganizationKind.DEPARTMENT & __org.getKind() != OrganizationKind.GOVENMENT) 
                return;
        }
        __org.setHigher(hiBefore);
        res.setBeginToken(t0);
    }

    private static com.pullenti.ner.ReferentToken checkOwnership(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.ReferentToken res = null;
        OrganizationReferent __org = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), OrganizationReferent.class);
        if (__org == null) 
            return null;
        com.pullenti.ner.Token tt0 = t;
        for (; t != null; ) {
            com.pullenti.ner.Token tt = t.getNext();
            boolean always = false;
            boolean br = false;
            if (tt != null && tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ПРИ", null)) 
                    always = true;
                else if (tt.isValue("В", null)) {
                }
                else 
                    break;
                tt = tt.getNext();
            }
            if ((tt != null && tt.isChar('(') && (tt.getNext() instanceof com.pullenti.ner.ReferentToken)) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar(')')) {
                br = true;
                tt = tt.getNext();
            }
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                OrganizationReferent org2 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org2 != null) {
                    boolean ok = com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org2, __org, false);
                    if (always || ok) 
                        ok = true;
                    else if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org2, __org, true)) {
                        com.pullenti.ner.Token t0 = t.getPrevious();
                        if (t0 != null && t0.isChar(',')) 
                            t0 = t0.getPrevious();
                        com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t0, null);
                        if (rt != null && com.pullenti.unisharp.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY") && rt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                            ok = true;
                    }
                    if (ok && ((__org.getHigher() == null || __org.getHigher().canBeEquals(org2, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)))) {
                        __org.setHigher(org2);
                        if (br) 
                            tt = tt.getNext();
                        if (__org.getHigher() == org2) {
                            if (res == null) 
                                res = com.pullenti.ner.ReferentToken._new786(__org, t, tt, tt0.getMorph());
                            else 
                                res.setEndToken(tt);
                            t = tt;
                            if (__org.getGeoObjects().size() == 0) {
                                com.pullenti.ner.Token ttt = t.getNext();
                                if (ttt != null && ttt.isValue("В", null)) 
                                    ttt = ttt.getNext();
                                if (isGeo(ttt, false) != null) {
                                    __org.addGeoObject(ttt);
                                    res.setEndToken(ttt);
                                    t = ttt;
                                }
                            }
                            __org = org2;
                            continue;
                        }
                    }
                    if (__org.getHigher() != null && __org.getHigher().getHigher() == null && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org2, __org.getHigher(), false)) {
                        __org.getHigher().setHigher(org2);
                        res = new com.pullenti.ner.ReferentToken(__org, t, tt, null);
                        if (br) 
                            res.setEndToken(tt.getNext());
                        return res;
                    }
                    if ((__org.getHigher() != null && org2.getHigher() == null && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(__org.getHigher(), org2, false)) && com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org2, __org, false)) {
                        org2.setHigher(__org.getHigher());
                        __org.setHigher(org2);
                        res = new com.pullenti.ner.ReferentToken(__org, t, tt, null);
                        if (br) 
                            res.setEndToken(tt.getNext());
                        return res;
                    }
                }
            }
            break;
        }
        if (res != null) 
            return res;
        if (__org.getKind() == OrganizationKind.DEPARTMENT && __org.getHigher() == null && __org.m_TempParentOrg == null) {
            int cou = 0;
            for (com.pullenti.ner.Token tt = tt0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.isNewlineAfter()) 
                    cou += 10;
                if ((++cou) > 100) 
                    break;
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org0 == null) 
                    continue;
                java.util.ArrayList<OrganizationReferent> tmp = new java.util.ArrayList<OrganizationReferent>();
                for (; org0 != null; org0 = org0.getHigher()) {
                    if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(org0, __org, false)) {
                        __org.setHigher(org0);
                        break;
                    }
                    if (org0.getKind() != OrganizationKind.DEPARTMENT) 
                        break;
                    if (tmp.contains(org0)) 
                        break;
                    tmp.add(org0);
                }
                break;
            }
        }
        return null;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (begin == null) 
            return null;
        com.pullenti.ner.ReferentToken rt = tryAttachOrg(begin, AttachType.EXTONTOLOGY, null, begin.getPrevious() != null, -1);
        if (rt != null) {
            OrganizationReferent r = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, OrganizationReferent.class);
            if (r.getHigher() == null && rt.getEndToken().getNext() != null) {
                OrganizationReferent h = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (h != null) {
                    if (com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(h, r, true) || !com.pullenti.ner._org.internal.OrgOwnershipHelper.canBeHigher(r, h, true)) {
                        r.setHigher(h);
                        rt.setEndToken(rt.getEndToken().getNext());
                    }
                }
            }
            if (rt.getBeginToken() != begin) {
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(begin, rt.getBeginToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(nam)) {
                    OrganizationReferent org0 = new OrganizationReferent();
                    org0.addName(nam, true, begin);
                    org0.setHigher(r);
                    rt = new com.pullenti.ner.ReferentToken(org0, begin, rt.getEndToken(), null);
                }
            }
            return rt;
        }
        com.pullenti.ner.Token t = begin;
        com.pullenti.ner.Token et = begin;
        for (; t != null; t = t.getNext()) {
            if (t.isCharOf(",;")) 
                break;
            et = t;
        }
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(begin, et, com.pullenti.ner.core.GetTextAttr.NO);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(_name)) 
            return null;
        OrganizationReferent __org = new OrganizationReferent();
        __org.addName(_name, true, begin);
        return new com.pullenti.ner.ReferentToken(__org, begin, et, null);
    }

    private static boolean m_Inited = false;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner._org.internal.MetaOrganization.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            _initSport();
            _initPolitic();
            com.pullenti.ner._org.internal.OrgItemTypeToken.initialize();
            com.pullenti.ner._org.internal.OrgItemEngItem.initialize();
            com.pullenti.ner._org.internal.OrgItemNameToken.initialize();
            com.pullenti.ner._org.internal.OrgGlobal.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new OrganizationAnalyzer());
    }

    private static com.pullenti.ner.ReferentToken tryAttachPoliticParty(com.pullenti.ner.Token t, boolean onlyAbbrs) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.core.TerminToken nameTok = null;
        com.pullenti.ner.Token root = null;
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> prevToks = null;
        int prevWords = 0;
        com.pullenti.ner.ReferentToken _geo = null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        double coef = 0.0;
        int wordsAfter = 0;
        boolean isFraction = false;
        boolean isPolitic = false;
        for (; t != null; t = t.getNext()) {
            if (t != t0 && t.isNewlineBefore()) 
                break;
            if (onlyAbbrs) 
                break;
            if (t.isHiphen()) {
                if (prevToks == null) 
                    return null;
                continue;
            }
            com.pullenti.ner.core.TerminToken tokN = m_PoliticNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tokN != null) {
                if (!t.chars.isAllLower()) 
                    break;
                t1 = tokN.getEndToken();
            }
            com.pullenti.ner.core.TerminToken tok = m_PoliticPrefs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) {
                if (t.getMorph()._getClass().isAdjective()) {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t, null);
                    if (rt != null) {
                        _geo = rt;
                        t1 = (t = rt.getEndToken());
                        coef += 0.5;
                        continue;
                    }
                }
                if (t.getEndChar() < t1.getEndChar()) 
                    continue;
                break;
            }
            if (tok.termin.tag != null && tok.termin.tag2 != null) {
                if (t.getEndChar() < t1.getEndChar()) 
                    continue;
                break;
            }
            if (tok.termin.tag == null && tok.termin.tag2 == null) 
                isPolitic = true;
            if (prevToks == null) 
                prevToks = new java.util.ArrayList<com.pullenti.ner.core.TerminToken>();
            prevToks.add(tok);
            if (tok.termin.tag == null) {
                coef += (1.0);
                prevWords++;
            }
            else if (tok.getMorph()._getClass().isAdjective()) 
                coef += 0.5;
            t = tok.getEndToken();
            if (t.getEndChar() > t1.getEndChar()) 
                t1 = t;
        }
        if (t == null) 
            return null;
        if (t.isValue("ПАРТИЯ", null) || t.isValue("ФРОНТ", null) || t.isValue("ГРУППИРОВКА", null)) {
            if (!t.isValue("ПАРТИЯ", null)) 
                isPolitic = true;
            root = t;
            coef += 0.5;
            if (t.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                coef += 0.5;
            t1 = t;
            t = t.getNext();
        }
        else if (t.isValue("ФРАКЦИЯ", null)) {
            root = (t1 = t);
            isFraction = true;
            if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent)) 
                coef += (2.0);
            else 
                return null;
        }
        com.pullenti.ner.core.BracketSequenceToken br = null;
        if ((((nameTok = m_PoliticNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null && !t.chars.isAllLower()) {
            coef += 0.5;
            isPolitic = true;
            if (!t.chars.isAllLower()) 
                coef += 0.5;
            if (nameTok.getLengthChar() > 10) 
                coef += 0.5;
            else if (t.chars.isAllUpper()) 
                coef += 0.5;
            t1 = nameTok.getEndToken();
            t = t1.getNext();
        }
        else if ((((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 10)))) != null) {
            if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                return null;
            if ((((nameTok = m_PoliticNames.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) 
                coef += 1.5;
            else if (onlyAbbrs) 
                return null;
            else if (t.getNext() != null && t.getNext().isValue("О", null)) 
                return null;
            else 
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.getEndChar() <= br.getEndChar(); tt = tt.getNext()) {
                    com.pullenti.ner.core.TerminToken tok2 = m_PoliticPrefs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null && tok2.termin.tag == null) {
                        if (tok2.termin.tag2 == null) 
                            isPolitic = true;
                        coef += 0.5;
                        wordsAfter++;
                    }
                    else if (m_PoliticSuffs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                        coef += 0.5;
                        wordsAfter++;
                    }
                    else if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        coef += 0.5;
                    else if (tt instanceof com.pullenti.ner.ReferentToken) {
                        coef = 0.0;
                        break;
                    }
                    else {
                        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                        if ((mc.equals(com.pullenti.morph.MorphClass.VERB) || mc.equals(com.pullenti.morph.MorphClass.ADVERB) || mc.isPronoun()) || mc.isPersonalPronoun()) {
                            coef = 0.0;
                            break;
                        }
                        if (mc.isNoun() || mc.isUndefined()) 
                            coef -= 0.5;
                    }
                }
            t1 = br.getEndToken();
            t = t1.getNext();
        }
        else if (onlyAbbrs) 
            return null;
        else if (root != null) {
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt.chars.isAllLower() && tt == t && tt.getMorphClassInDictionary().isPreposition()) 
                    break;
                if (tt.isCommaAnd() && tt == t) 
                    break;
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    break;
                if (tt.getWhitespacesBeforeCount() > 2) 
                    break;
                if (tt.getMorph()._getClass().isPreposition()) {
                    if (tt != root.getNext()) 
                        break;
                    continue;
                }
                if (tt.isAnd()) {
                    com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                    if (npt2 != null && m_PoliticSuffs.tryParse(npt2.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null && npt2.getEndToken().chars.equals(tt.getPrevious().chars)) 
                        continue;
                    break;
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                if (npt == null) 
                    break;
                if (npt.noun.isValue("ПАРТИЯ", null) || npt.noun.isValue("ФРОНТ", null)) 
                    break;
                double co = 0.0;
                for (com.pullenti.ner.Token ttt = tt; ttt != null && ttt.getEndChar() <= npt.getEndChar(); ttt = ttt.getNext()) {
                    com.pullenti.ner.core.TerminToken tok2 = m_PoliticPrefs.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null && tok2.termin.tag == null) {
                        if (tok2.termin.tag2 == null) 
                            isPolitic = true;
                        co += 0.5;
                        wordsAfter++;
                    }
                    else if (m_PoliticSuffs.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                        co += 0.5;
                        wordsAfter++;
                    }
                    else if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        co += 0.5;
                }
                if (co == 0) {
                    if (!npt.getMorph().getCase().isGenitive()) 
                        break;
                    com.pullenti.ner.core.TerminToken lastSuf = m_PoliticSuffs.tryParse(tt.getPrevious(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if (((wordsAfter > 0 && npt.getEndToken().chars.equals(tt.getPrevious().chars))) || ((lastSuf != null && lastSuf.termin.tag != null)) || ((tt.getPrevious() == root && npt.getEndToken().chars.isAllLower() && npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) && root.chars.isCapitalUpper())) {
                        com.pullenti.ner.ReferentToken pp = tt.kit.processReferent("PERSON", tt, null);
                        if (pp != null) 
                            break;
                        wordsAfter++;
                    }
                    else 
                        break;
                }
                t1 = (tt = npt.getEndToken());
                t = t1.getNext();
                coef += co;
            }
        }
        if (t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (t.getWhitespacesBeforeCount() < 3)) {
            t1 = t;
            coef += 0.5;
        }
        for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if (!(tt instanceof com.pullenti.ner.TextToken)) {
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org1 != null && org1.containsProfile(OrgProfile.POLICY)) 
                    coef += 0.5;
                continue;
            }
            if (!tt.chars.isLetter()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (m_PoliticPrefs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                coef += 0.5;
                if (tt.isValue("ФРАКЦИЯ", null)) 
                    coef += 0.5;
            }
            else 
                break;
        }
        if (coef < 1) 
            return null;{
                if (root == null) {
                    if (nameTok == null && br == null) 
                        return null;
                }
                else if ((nameTok == null && wordsAfter == 0 && br == null) && !isFraction) {
                    if ((coef < 2) || prevWords == 0) 
                        return null;
                }
            }
        OrganizationReferent __org = new OrganizationReferent();
        if (br != null && nameTok != null && (nameTok.getEndChar() < br.getEndToken().getPrevious().getEndChar())) 
            nameTok = null;
        if (nameTok != null) 
            isPolitic = true;
        if (isFraction) {
            __org.addProfile(OrgProfile.POLICY);
            __org.addProfile(OrgProfile.UNIT);
        }
        else if (isPolitic) {
            __org.addProfile(OrgProfile.POLICY);
            __org.addProfile(OrgProfile.UNION);
        }
        else 
            __org.addProfile(OrgProfile.UNION);
        if (nameTok != null) {
            isPolitic = true;
            __org.addName(nameTok.termin.getCanonicText(), true, null);
            if (nameTok.termin.additionalVars != null) {
                for (com.pullenti.ner.core.Termin v : nameTok.termin.additionalVars) {
                    __org.addName(v.getCanonicText(), true, null);
                }
            }
            if (nameTok.termin.acronym != null) {
                com.pullenti.ner.geo.GeoReferent geo1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nameTok.termin.tag, com.pullenti.ner.geo.GeoReferent.class);
                if (geo1 == null) 
                    __org.addName(nameTok.termin.acronym, true, null);
                else if (_geo != null) {
                    if (geo1.canBeEquals(_geo.referent, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                        __org.addName(nameTok.termin.acronym, true, null);
                }
                else if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    if (geo1.canBeEquals(t1.getReferent(), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                        __org.addName(nameTok.termin.acronym, true, null);
                }
                else if (nameTok.getBeginToken() == nameTok.getEndToken() && nameTok.getBeginToken().isValue(nameTok.termin.acronym, null)) {
                    __org.addName(nameTok.termin.acronym, true, null);
                    com.pullenti.ner.ReferentToken rtg = new com.pullenti.ner.ReferentToken(geo1.clone(), nameTok.getBeginToken(), nameTok.getEndToken(), null);
                    rtg.setDefaultLocalOnto(t0.kit.processor);
                    __org.addGeoObject(rtg);
                }
            }
        }
        else if (br != null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
            __org.addName(nam, true, null);
            if (root == null) {
                String nam2 = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                if (com.pullenti.unisharp.Utils.stringsNe(nam2, nam)) 
                    __org.addName(nam, true, null);
            }
        }
        if (root != null) {
            com.pullenti.ner.Token typ1 = root;
            if (_geo != null) 
                typ1 = _geo.getBeginToken();
            if (prevToks != null) {
                for (com.pullenti.ner.core.TerminToken p : prevToks) {
                    if (p.termin.tag == null) {
                        if (p.getBeginChar() < typ1.getBeginChar()) 
                            typ1 = p.getBeginToken();
                        break;
                    }
                }
            }
            String typ = com.pullenti.ner.core.MiscHelper.getTextValue(typ1, root, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            if (typ != null) {
                if (br == null) {
                    String nam = null;
                    com.pullenti.ner.Token t2 = t1;
                    if (t2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        t2 = t2.getPrevious();
                    if (t2.getEndChar() > root.getEndChar()) {
                        nam = (typ + " " + com.pullenti.ner.core.MiscHelper.getTextValue(root.getNext(), t2, com.pullenti.ner.core.GetTextAttr.NO));
                        __org.addName(nam, true, null);
                    }
                }
                if (__org.getNames().size() == 0 && typ1 != root) 
                    __org.addName(typ, true, null);
                else 
                    __org.addTypeStr(typ.toLowerCase());
            }
            if (isFraction && (t1.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                __org.addTypeStr("фракция");
                t1 = t1.getNext();
                __org.setHigher((OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), OrganizationReferent.class));
                if (t1.getNext() != null && t1.getNext().isValue("В", null) && (t1.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                    OrganizationReferent oo = (OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext().getReferent(), OrganizationReferent.class);
                    if (oo != null && oo.getKind() == OrganizationKind.GOVENMENT) {
                        t1 = t1.getNext().getNext();
                        __org.addSlot(OrganizationReferent.ATTR_MISC, oo, false, 0);
                    }
                    else if (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                        t1 = t1.getNext().getNext();
                        __org.addSlot(OrganizationReferent.ATTR_MISC, t1.getReferent(), false, 0);
                    }
                }
            }
        }
        if (_geo != null) 
            __org.addGeoObject(_geo);
        else if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
            __org.addGeoObject(t1.getReferent());
        return new com.pullenti.ner.ReferentToken(__org, t0, t1, null);
    }

    private static void _initPolitic() {
        m_PoliticPrefs = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"либеральный", "либерал", "лейбористский", "демократический", "коммунистрический", "большевистский", "социальный", "социал", "национал", "националистическая", "свободный", "радикальный", "леворадикальный", "радикал", "революционная", "левый", "правый", "социалистический", "рабочий", "трудовой", "республиканский", "народный", "аграрный", "монархический", "анархический", "прогрессивый", "прогрессистский", "консервативный", "гражданский", "фашистский", "марксистский", "ленинский", "маоистский", "имперский", "славянский", "анархический", "баскский", "конституционный", "пиратский", "патриотический", "русский"}) {
            m_PoliticPrefs.add(new com.pullenti.ner.core.Termin(s.toUpperCase(), null, false));
        }
        for (String s : new String[] {"объединенный", "всероссийский", "общероссийский", "христианский", "независимый", "альтернативный"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new2563(s.toUpperCase(), s));
        }
        for (String s : new String[] {"политический", "правящий", "оппозиционный", "запрешенный", "террористический", "запрещенный", "экстремистский"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), s));
        }
        for (String s : new String[] {"активист", "член", "руководство", "лидер", "глава", "демонстрация", "фракция", "съезд", "пленум", "террорист", "парламент", "депутат", "парламентарий", "оппозиция", "дума", "рада"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new102(s.toUpperCase(), s, s));
        }
        m_PoliticSuffs = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"коммунист", "социалист", "либерал", "республиканец", "националист", "радикал", "лейборист", "анархист", "патриот", "консерватор", "левый", "правый", "новый", "зеленые", "демократ", "фашист", "защитник", "труд", "равенство", "прогресс", "жизнь", "мир", "родина", "отечество", "отчизна", "республика", "революция", "революционер", "народовластие", "фронт", "сила", "платформа", "воля", "справедливость", "преображение", "преобразование", "солидарность", "управление", "демократия", "народ", "гражданин", "предприниматель", "предпринимательство", "бизнес", "пенсионер", "христианин"}) {
            m_PoliticSuffs.add(new com.pullenti.ner.core.Termin(s.toUpperCase(), null, false));
        }
        for (String s : new String[] {"реформа", "свобода", "единство", "развитие", "освобождение", "любитель", "поддержка", "возрождение", "независимость"}) {
            m_PoliticSuffs.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), s));
        }
        m_PoliticNames = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"Республиканская партия", "Демократическая партия;Демпартия", "Христианско демократический союз;ХДС", "Свободная демократическая партия;СвДП", "ЯБЛОКО", "ПАРНАС", "ПАМЯТЬ", "Движение против нелегальной иммиграции;ДПНИ", "НАЦИОНАЛ БОЛЬШЕВИСТСКАЯ ПАРТИЯ;НБП", "НАЦИОНАЛЬНЫЙ ФРОНТ;НАЦФРОНТ", "Национальный патриотический фронт", "Батькивщина;Батькiвщина", "НАРОДНАЯ САМООБОРОНА", "Гражданская платформа", "Народная воля", "Славянский союз", "ПРАВЫЙ СЕКТОР", "ПЕГИДА;PEGIDA", "Венгерский гражданский союз;ФИДЕС", "БЛОК ЮЛИИ ТИМОШЕНКО;БЮТ", "Аль Каида;Аль Каеда;Аль Кайда;Al Qaeda;Al Qaida", "Талибан;движение талибан", "Бригады мученников Аль Аксы", "Хезболла;Хезбалла;Хизбалла", "Народный фронт освобождения палестины;НФОП", "Организация освобождения палестины;ООП", "Союз исламского джихада;Исламский джихад", "Аль-Джихад;Египетский исламский джихад", "Братья-мусульмане;Аль Ихван альМуслимун", "ХАМАС", "Движение за освобождение Палестины;ФАТХ", "Фронт Аль Нусра;Аль Нусра", "Джабхат ан Нусра"}) {
            String[] pp = com.pullenti.unisharp.Utils.split(s.toUpperCase(), String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new100(pp[0], OrgProfile.POLICY);
            for (int i = 0; i < pp.length; i++) {
                if ((pp[i].length() < 5) && t.acronym == null) {
                    t.acronym = pp[i];
                    if (t.acronym.endsWith("Р") || t.acronym.endsWith("РФ")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("RU");
                    else if (t.acronym.endsWith("У")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("UA");
                    else if (t.acronym.endsWith("СС")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("СССР");
                }
                else 
                    t.addVariant(pp[i], false);
            }
            m_PoliticNames.add(t);
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_PoliticPrefs;

    private static com.pullenti.ner.core.TerminCollection m_PoliticSuffs;

    private static com.pullenti.ner.core.TerminCollection m_PoliticNames;

    public static class AttachType implements Comparable<AttachType> {
    
        public static final AttachType NORMAL; // 0
    
        public static final AttachType NORMALAFTERDEP; // 1
    
        public static final AttachType MULTIPLE; // 2
    
        public static final AttachType HIGH; // 3
    
        public static final AttachType EXTONTOLOGY; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private AttachType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(AttachType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, AttachType> mapIntToEnum; 
        private static java.util.HashMap<String, AttachType> mapStringToEnum; 
        private static AttachType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static AttachType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            AttachType item = new AttachType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static AttachType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static AttachType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, AttachType>();
            mapStringToEnum = new java.util.HashMap<String, AttachType>();
            NORMAL = new AttachType(0, "NORMAL");
            mapIntToEnum.put(NORMAL.value(), NORMAL);
            mapStringToEnum.put(NORMAL.m_str.toUpperCase(), NORMAL);
            NORMALAFTERDEP = new AttachType(1, "NORMALAFTERDEP");
            mapIntToEnum.put(NORMALAFTERDEP.value(), NORMALAFTERDEP);
            mapStringToEnum.put(NORMALAFTERDEP.m_str.toUpperCase(), NORMALAFTERDEP);
            MULTIPLE = new AttachType(2, "MULTIPLE");
            mapIntToEnum.put(MULTIPLE.value(), MULTIPLE);
            mapStringToEnum.put(MULTIPLE.m_str.toUpperCase(), MULTIPLE);
            HIGH = new AttachType(3, "HIGH");
            mapIntToEnum.put(HIGH.value(), HIGH);
            mapStringToEnum.put(HIGH.m_str.toUpperCase(), HIGH);
            EXTONTOLOGY = new AttachType(4, "EXTONTOLOGY");
            mapIntToEnum.put(EXTONTOLOGY.value(), EXTONTOLOGY);
            mapStringToEnum.put(EXTONTOLOGY.m_str.toUpperCase(), EXTONTOLOGY);
            java.util.Collection<AttachType> col = mapIntToEnum.values();
            m_Values = new AttachType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public OrganizationAnalyzer() {
        super();
    }
    public static OrganizationAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new OrganizationAnalyzer(); } 
        catch(Exception e) { }
    }
}
