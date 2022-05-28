/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonIdentityToken extends com.pullenti.ner.MetaToken {

    public PersonIdentityToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public float coef;

    public PersonMorphCollection firstname;

    public PersonMorphCollection lastname;

    public Object middlename;

    public com.pullenti.ner.person.PersonReferent ontologyPerson;

    public com.pullenti.morph.MorphGender getProbableGender() {
        if (getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE) 
            return getMorph().getGender();
        int fem = 0;
        int mus = 0;
        for (int i = 0; i < 2; i++) {
            PersonMorphCollection col = (i == 0 ? firstname : lastname);
            if (col == null) 
                continue;
            boolean isf = false;
            boolean ism = false;
            for (PersonMorphCollection.PersonMorphVariant v : col.items) {
                if (((short)((v.gender.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    ism = true;
                if (((short)((v.gender.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    isf = true;
            }
            if (ism) 
                mus++;
            if (isf) 
                fem++;
        }
        if (mus > fem) 
            return com.pullenti.morph.MorphGender.MASCULINE;
        if (fem > mus) 
            return com.pullenti.morph.MorphGender.FEMINIE;
        return com.pullenti.morph.MorphGender.UNDEFINED;
    }


    public FioTemplateType typ = FioTemplateType.UNDEFINED;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(coef).append(" ").append(typ.toString()).append(": ").append((lastname == null ? "" : lastname.toString()));
        res.append(" ").append((firstname == null ? "" : firstname.toString())).append(" ").append((middlename == null ? "" : middlename.toString())).append("; ").append(this.getMorph().toString());
        return res.toString();
    }

    public static PersonMorphCollection createLastname(PersonItemToken pit, com.pullenti.morph.MorphBaseInfo inf) {
        PersonMorphCollection res = new PersonMorphCollection();
        if (pit.lastname == null) 
            setValue(res, pit.getBeginToken(), inf);
        else 
            setValue2(res, pit.lastname, inf);
        return res;
    }

    public static com.pullenti.ner.person.PersonReferent tryAttachLatinSurname(PersonItemToken pit, com.pullenti.ner.core.IntOntologyCollection ontos) {
        if (pit == null) 
            return null;
        if (pit.lastname != null && ((pit.lastname.isInDictionary || pit.lastname.isLastnameHasStdTail))) {
            com.pullenti.ner.person.PersonReferent p = new com.pullenti.ner.person.PersonReferent();
            p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pit.lastname.vars.get(0).value, false, 0);
            return p;
        }
        return null;
    }

    public static com.pullenti.ner.person.PersonReferent tryAttachOntoForSingle(PersonItemToken pit, com.pullenti.ner.core.IntOntologyCollection ontos) {
        if ((pit == null || ontos == null || pit.value == null) || pit.typ == PersonItemToken.ItemType.INITIAL) 
            return null;
        if (ontos.getItems().size() > 30) 
            return null;
        com.pullenti.ner.person.PersonReferent p0 = null;
        int cou = 0;
        boolean fi = false;
        boolean sur = true;
        for (com.pullenti.ner.core.IntOntologyItem p : ontos.getItems()) {
            if (p.referent instanceof com.pullenti.ner.person.PersonReferent) {
                com.pullenti.ner.person.PersonReferent p00 = null;
                if (pit.firstname != null) {
                    for (PersonItemToken.MorphPersonItemVariant v : pit.firstname.vars) {
                        if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, v.value, true) != null) {
                            p00 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(p.referent, com.pullenti.ner.person.PersonReferent.class);
                            fi = true;
                            break;
                        }
                    }
                }
                if (pit.lastname != null) {
                    for (PersonItemToken.MorphPersonItemVariant v : pit.lastname.vars) {
                        if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, v.value, true) != null) {
                            p00 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(p.referent, com.pullenti.ner.person.PersonReferent.class);
                            sur = true;
                            break;
                        }
                    }
                }
                if (p00 == null) {
                    if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, pit.value, true) != null) {
                        p00 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(p.referent, com.pullenti.ner.person.PersonReferent.class);
                        fi = true;
                    }
                    else if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pit.value, true) != null) {
                        p00 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(p.referent, com.pullenti.ner.person.PersonReferent.class);
                        sur = true;
                    }
                }
                if (p00 != null) {
                    p0 = p00;
                    cou++;
                }
            }
        }
        if (p0 != null && cou == 1) {
            if (fi) {
                java.util.ArrayList<PersonItemToken> li = new java.util.ArrayList<PersonItemToken>();
                li.add(pit);
                PersonIdentityToken king = tryAttachKing(li, 0, pit.getMorph(), false);
                if (king != null) 
                    return null;
            }
            return p0;
        }
        return null;
    }

    public static com.pullenti.ner.person.PersonReferent tryAttachOntoForDuble(PersonItemToken pit0, PersonItemToken pit1) {
        if ((pit0 == null || pit0.firstname == null || pit1 == null) || pit1.middlename == null) 
            return null;
        PersonAnalyzerData ad = com.pullenti.ner.person.PersonAnalyzer.getData(pit0);
        if (ad.localOntology.getItems().size() > 100) 
            return null;
        com.pullenti.ner.person.PersonReferent p0 = null;
        int cou = 0;
        for (com.pullenti.ner.core.IntOntologyItem p : ad.localOntology.getItems()) {
            if (p.referent != null) {
                for (PersonItemToken.MorphPersonItemVariant v : pit0.firstname.vars) {
                    if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, v.value, true) == null) 
                        continue;
                    if (p.referent.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, pit1.middlename.vars.get(0).value, true) == null) 
                        continue;
                    p0 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(p.referent, com.pullenti.ner.person.PersonReferent.class);
                    cou++;
                    break;
                }
            }
        }
        if (p0 != null && cou == 1) 
            return p0;
        return null;
    }

    public static PersonIdentityToken tryAttachOntoExt(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, com.pullenti.ner.ExtOntology ontos) {
        if (ind >= pits.size() || pits.get(ind).typ == PersonItemToken.ItemType.INITIAL || ontos == null) 
            return null;
        if (ontos.items.size() > 1000) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otl = ontos.attachToken(com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, pits.get(ind).getBeginToken());
        return _TryAttachOnto(pits, ind, inf, otl, false, false);
    }

    public static PersonIdentityToken tryAttachOntoInt(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, com.pullenti.ner.core.IntOntologyCollection ontos) {
        if (ind >= pits.size() || pits.get(ind).typ == PersonItemToken.ItemType.INITIAL) 
            return null;
        if (ontos.getItems().size() > 1000) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otl = ontos.tryAttach(pits.get(ind).getBeginToken(), null, false);
        PersonIdentityToken res = _TryAttachOnto(pits, ind, inf, otl, false, false);
        if (res != null) 
            return res;
        return null;
    }

    private static PersonIdentityToken _TryAttachOnto(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otl, boolean isLocal, boolean isAttrBefore) {
        if (otl == null || otl.size() == 0) 
            return null;
        java.util.ArrayList<PersonIdentityToken> res = new java.util.ArrayList<PersonIdentityToken>();
        java.util.ArrayList<com.pullenti.ner.person.PersonReferent> ontoPersons = new java.util.ArrayList<com.pullenti.ner.person.PersonReferent>();
        if (otl != null) {
            for (com.pullenti.ner.core.IntOntologyToken ot : otl) {
                if (ot.getEndToken() == pits.get(ind).getEndToken()) {
                    com.pullenti.ner.person.PersonReferent pers = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(ot.item.referent, com.pullenti.ner.person.PersonReferent.class);
                    if (pers == null) 
                        continue;
                    if (ontoPersons.contains(pers)) 
                        continue;
                    PersonIdentityToken pit;
                    if (ot.termin.ignoreTermsOrder) {
                        if (ind != 0) 
                            continue;
                        pit = tryAttachIdentity(pits, inf);
                        if (pit == null) 
                            continue;
                        com.pullenti.ner.person.PersonReferent p = new com.pullenti.ner.person.PersonReferent();
                        p.addIdentity(pit.lastname);
                        pit.ontologyPerson = p;
                        ontoPersons.add(pers);
                        res.add(pit);
                        continue;
                    }
                    if (inf.getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                        if (pers.isFemale()) 
                            continue;
                    }
                    else if (inf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                        if (pers.isMale()) 
                            continue;
                    }
                    com.pullenti.morph.MorphBaseInfo inf0 = com.pullenti.morph.MorphBaseInfo._new2689(inf.getCase(), inf.getGender());
                    if (!ot.getMorph().getCase().isUndefined() && inf0.getCase().equals(com.pullenti.morph.MorphCase.ALLCASES) && ot.getBeginToken() == ot.getEndToken()) 
                        inf0.setCase(ot.getMorph().getCase());
                    if (pers.isMale()) 
                        inf0.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                    else if (pers.isFemale()) 
                        inf0.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                    java.util.ArrayList<PersonIdentityToken> vars = new java.util.ArrayList<PersonIdentityToken>();
                    if (ind > 1) {
                        if ((((pit = tryAttachIISurname(pits, ind - 2, inf0)))) != null) 
                            vars.add(pit);
                        if ((((pit = tryAttachNameSecnameSurname(pits, ind - 2, inf0, false)))) != null) 
                            vars.add(pit);
                    }
                    if (ind > 0) {
                        if ((((pit = tryAttachIISurname(pits, ind - 1, inf0)))) != null) 
                            vars.add(pit);
                        if ((((pit = tryAttachNameSurname(pits, ind - 1, inf0, false, isAttrBefore)))) != null) 
                            vars.add(pit);
                    }
                    if ((ind + 2) < pits.size()) {
                        if ((((pit = tryAttachSurnameII(pits, ind, inf0)))) != null) 
                            vars.add(pit);
                        if ((((pit = tryAttachSurnameNameSecname(pits, ind, inf0, false, false)))) != null) 
                            vars.add(pit);
                    }
                    if ((ind + 1) < pits.size()) {
                        if ((((pit = tryAttachSurnameName(pits, ind, inf0, false)))) != null) {
                            PersonIdentityToken pit0 = null;
                            for (PersonIdentityToken v : vars) {
                                if (v.typ == FioTemplateType.SURNAMENAMESECNAME) {
                                    pit0 = v;
                                    break;
                                }
                            }
                            if (pit0 == null || (pit0.coef < pit.coef)) 
                                vars.add(pit);
                        }
                    }
                    if ((((pit = tryAttachAsian(pits, ind, inf0, 3, false)))) != null) 
                        vars.add(pit);
                    else if ((((pit = tryAttachAsian(pits, ind, inf0, 2, false)))) != null) 
                        vars.add(pit);
                    pit = null;
                    for (PersonIdentityToken v : vars) {
                        if (v.coef < 0) 
                            continue;
                        com.pullenti.ner.person.PersonReferent p = new com.pullenti.ner.person.PersonReferent();
                        if (v.ontologyPerson != null) 
                            p = v.ontologyPerson;
                        else {
                            if (v.typ == FioTemplateType.ASIANNAME) 
                                p.addIdentity(v.lastname);
                            else 
                                p.addFioIdentity(v.lastname, v.firstname, v.middlename);
                            v.ontologyPerson = p;
                        }
                        if (!pers.canBeEquals(p, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                            if (pit != null && v.coef >= pit.coef) 
                                pit = null;
                        }
                        else if (pit == null) 
                            pit = v;
                        else if (pit.coef < v.coef) 
                            pit = v;
                    }
                    if ((pit == null && pits.size() == 1 && vars.size() == 0) && pers.findSlot(null, pits.get(0).value, true) != null) {
                        com.pullenti.ner.person.PersonReferent p = new com.pullenti.ner.person.PersonReferent();
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_UNDEFNAME, pits.get(0).value, false, 0);
                        pit = _new2690(pits.get(0).getBeginToken(), pits.get(0).getEndToken(), 3.0F, FioTemplateType.UNDEFINED);
                        pit.ontologyPerson = p;
                        pit.setMorph(pits.get(0).getMorph());
                    }
                    else if (pit == null) {
                        pit = tryAttachSingleSurname(pits, ind, inf0);
                        if (pit == null || (pit.coef < 2)) 
                            continue;
                        if (pit.getEndToken() != pits.get(pits.size() - 1).getEndToken() && vars.size() > 0) 
                            continue;
                        com.pullenti.ner.person.PersonReferent p = new com.pullenti.ner.person.PersonReferent();
                        p.addFioIdentity(pit.lastname, null, null);
                        pit.ontologyPerson = p;
                    }
                    ontoPersons.add(pers);
                    res.add(pit);
                }
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() == 1) {
            res.get(0).ontologyPerson.mergeSlots(ontoPersons.get(0), true);
            return res.get(0);
        }
        return null;
    }

    public static PersonIdentityToken createTyp(java.util.ArrayList<PersonItemToken> pits, FioTemplateType _typ, com.pullenti.morph.MorphBaseInfo inf) {
        if (_typ == FioTemplateType.SURNAMENAMESECNAME) 
            return tryAttachSurnameNameSecname(pits, 0, inf, false, true);
        return null;
    }

    public static void sort(java.util.ArrayList<PersonIdentityToken> li) {
        if (li != null && li.size() > 1) {
            for (int k = 0; k < li.size(); k++) {
                boolean ch = false;
                for (int i = 0; i < (li.size() - 1); i++) {
                    if (li.get(i).coef < li.get(i + 1).coef) {
                        ch = true;
                        PersonIdentityToken v = li.get(i);
                        com.pullenti.unisharp.Utils.putArrayValue(li, i, li.get(i + 1));
                        com.pullenti.unisharp.Utils.putArrayValue(li, i + 1, v);
                    }
                }
                if (!ch) 
                    break;
            }
        }
    }

    public static java.util.ArrayList<PersonIdentityToken> tryAttachForExtOnto(java.util.ArrayList<PersonItemToken> pits) {
        PersonIdentityToken pit = null;
        if (pits.size() == 3) {
            if (pits.get(0).typ == PersonItemToken.ItemType.VALUE && pits.get(1).typ == PersonItemToken.ItemType.INITIAL && pits.get(2).typ == PersonItemToken.ItemType.VALUE) {
                pit = _new2691(pits.get(0).getBeginToken(), pits.get(2).getEndToken(), FioTemplateType.NAMEISURNAME);
                manageFirstname(pit, pits.get(0), null);
                manageLastname(pit, pits.get(2), null);
                manageMiddlename(pit, pits.get(1), null);
                pit.coef = 2.0F;
            }
            else if (pits.get(0).typ == PersonItemToken.ItemType.VALUE && pits.get(1).typ == PersonItemToken.ItemType.VALUE && pits.get(2).typ == PersonItemToken.ItemType.VALUE) {
                boolean ok = false;
                if (pits.get(0).firstname == null && pits.get(1).middlename == null && ((pits.get(1).firstname != null || pits.get(2).middlename != null))) 
                    ok = true;
                else if (pits.get(0).firstname != null && ((pits.get(0).firstname.isLastnameHasStdTail || pits.get(0).firstname.isInDictionary))) 
                    ok = true;
                if (ok) {
                    pit = _new2691(pits.get(0).getBeginToken(), pits.get(2).getEndToken(), FioTemplateType.SURNAMENAMESECNAME);
                    manageFirstname(pit, pits.get(1), null);
                    manageLastname(pit, pits.get(0), null);
                    manageMiddlename(pit, pits.get(2), null);
                    pit.coef = 2.0F;
                }
            }
        }
        else if (pits.size() == 2 && pits.get(0).typ == PersonItemToken.ItemType.VALUE && pits.get(1).typ == PersonItemToken.ItemType.VALUE) {
            PersonItemToken nam = null;
            PersonItemToken sur = null;
            for (int i = 0; i < 2; i++) {
                if (((pits.get(i).firstname != null && pits.get(i).firstname.isInDictionary)) || ((pits.get(i ^ 1).lastname != null && ((pits.get(i ^ 1).lastname.isInDictionary || pits.get(i ^ 1).lastname.isLastnameHasStdTail))))) {
                    nam = pits.get(i);
                    sur = pits.get(i ^ 1);
                    break;
                }
            }
            if (nam != null) {
                pit = _new2691(pits.get(0).getBeginToken(), pits.get(1).getEndToken(), (nam == pits.get(0) ? FioTemplateType.NAMESURNAME : FioTemplateType.SURNAMENAME));
                manageFirstname(pit, nam, null);
                manageLastname(pit, sur, null);
                pit.coef = 2.0F;
            }
        }
        if (pit == null) 
            return null;
        java.util.ArrayList<PersonIdentityToken> res = new java.util.ArrayList<PersonIdentityToken>();
        res.add(pit);
        return res;
    }

    public static java.util.ArrayList<PersonIdentityToken> tryAttach(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, com.pullenti.ner.Token firstTok, boolean king, boolean isAttrBefore) {
        java.util.ArrayList<PersonIdentityToken> res = new java.util.ArrayList<PersonIdentityToken>();
        FioTemplateType ty = FioTemplateType.UNDEFINED;
        if (firstTok != null) {
            for (com.pullenti.ner.Token t = firstTok.getPrevious(); t != null; t = t.getPrevious()) {
                com.pullenti.ner.person.PersonReferent pf = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.person.PersonReferent.class);
                if (pf != null) {
                    ty = pf.m_PersonIdentityTyp;
                    break;
                }
                if (t.isNewlineBefore()) 
                    break;
                if (t.chars.isLetter() && !t.isAnd()) 
                    break;
            }
        }
        PersonIdentityToken pit;
        PersonIdentityToken pit1;
        if ((((pit = tryAttachGlobal(pits, ind, inf)))) != null) {
            res.add(pit);
            return res;
        }
        if ((((pit = tryAttachSurnameII(pits, ind, inf)))) != null) 
            res.add(pit);
        if ((((pit = tryAttachIISurname(pits, ind, inf)))) != null) 
            res.add(pit);
        if ((((pit = tryAttachAsian(pits, ind, inf, 3, ty == FioTemplateType.ASIANNAME)))) != null) 
            res.add(pit);
        else if ((((pit = tryAttachArabic(pits, ind, inf, ty == FioTemplateType.ARABICLONG)))) != null) 
            res.add(pit);
        else {
            if ((((pit = tryAttachNameSurname(pits, ind, inf, ty == FioTemplateType.NAMESURNAME, isAttrBefore)))) != null) 
                res.add(pit);
            if ((((pit1 = tryAttachSurnameName(pits, ind, inf, ty == FioTemplateType.SURNAMENAME)))) != null) {
                res.add(pit1);
                if (pit != null && (pit.coef + (1.0F)) >= pit1.coef && ty != FioTemplateType.SURNAMENAME) 
                    pit1.coef -= ((float)0.5);
            }
            if ((((pit1 = tryAttachNameSecnameSurname(pits, ind, inf, ty == FioTemplateType.NAMESECNAMESURNAME)))) != null) {
                if (pit != null && pit1.coef > 2 && pit.coef > pit1.coef) {
                    if (ind == 0 && pits.size() == 3) 
                        pit1.coef = (float)((((double)pit.coef) + 0.5));
                }
                res.add(pit1);
            }
            if ((((pit = tryAttachSurnameNameSecname(pits, ind, inf, ty == FioTemplateType.SURNAMENAMESECNAME, false)))) != null) 
                res.add(pit);
            if ((((pit = tryAttachAsian(pits, ind, inf, 2, ty == FioTemplateType.ASIANNAME)))) != null) 
                res.add(pit);
        }
        if (king) {
            if ((((pit = tryAttachNameSecname(pits, ind, inf, ty == FioTemplateType.NAMESECNAME)))) != null) {
                for (PersonIdentityToken r : res) {
                    if (r.typ == FioTemplateType.NAMESURNAME) 
                        r.coef = pit.coef - 0.5F;
                }
                res.add(pit);
            }
        }
        if ((((pit = tryAttachKing(pits, ind, inf, ty == FioTemplateType.KING || king)))) != null) {
            for (PersonIdentityToken r : res) {
                if (r.typ == FioTemplateType.NAMESURNAME) 
                    r.coef = pit.coef - 0.5F;
            }
            res.add(pit);
        }
        if (inf.getGender() == com.pullenti.morph.MorphGender.MASCULINE || inf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            for (PersonIdentityToken p : res) {
                if (p.getMorph().getGender() == com.pullenti.morph.MorphGender.UNDEFINED || (p.getMorph().getGender().value()) == ((short)((com.pullenti.morph.MorphGender.FEMINIE.value()) | (com.pullenti.morph.MorphGender.MASCULINE.value())))) {
                    p.getMorph().setGender(inf.getGender());
                    if (p.getMorph().getCase().isUndefined()) 
                        p.getMorph().setCase(inf.getCase());
                }
            }
        }
        for (PersonIdentityToken r : res) {
            for (com.pullenti.ner.Token tt = r.getBeginToken(); tt != r.getEndToken(); tt = tt.getNext()) {
                if (tt.isNewlineAfter()) 
                    r.coef -= (1.0F);
            }
            com.pullenti.ner.Token ttt = r.getBeginToken().getPrevious();
            if (ttt != null && ttt.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB)) {
                com.pullenti.ner.Token tte = r.getEndToken().getNext();
                if (tte == null || tte.isChar('.') || tte.isNewlineBefore()) {
                }
                else 
                    continue;
                r.coef += (1.0F);
            }
            if (r.coef >= 0 && ind == 0 && r.getEndToken() == pits.get(pits.size() - 1).getEndToken()) 
                r.coef += _calcCoefAfter(pits.get(pits.size() - 1).getEndToken().getNext());
        }
        if (ty != FioTemplateType.UNDEFINED && ind == 0) {
            for (PersonIdentityToken r : res) {
                if (r.typ == ty) 
                    r.coef += ((float)1.5);
                else if (((r.typ == FioTemplateType.SURNAMENAME && ty == FioTemplateType.SURNAMENAMESECNAME)) || ((r.typ == FioTemplateType.SURNAMENAMESECNAME && ty == FioTemplateType.SURNAMENAME))) 
                    r.coef += ((float)0.5);
            }
        }
        sort(res);
        return res;
    }

    public static void manageLastname(PersonIdentityToken res, PersonItemToken pit, com.pullenti.morph.MorphBaseInfo inf) {
        if (pit.lastname == null) {
            res.lastname = new PersonMorphCollection();
            setValue(res.lastname, pit.getBeginToken(), inf);
            if (pit.isInDictionary) 
                res.coef--;
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(pit.getBeginToken(), com.pullenti.ner.TextToken.class);
            if ((tt != null && !tt.chars.isLatinLetter() && tt.chars.isCapitalUpper()) && tt.getLengthChar() > 2 && !tt.chars.isLatinLetter()) {
                boolean ok = true;
                for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                    if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                        ok = false;
                        break;
                    }
                }
                if (ok) 
                    res.coef += (1.0F);
            }
        }
        else {
            res.coef++;
            if (!isAccords(pit.lastname, inf)) 
                res.coef--;
            res.lastname = new PersonMorphCollection();
            setValue2(res.lastname, pit.lastname, inf);
            if (res.lastname.items.size() == 0 && pit.value != null) 
                res.lastname.items.add(PersonMorphCollection.PersonMorphVariant._new2694(pit.value));
            if (pit.lastname.term != null) {
                if (res.getMorph().getCase().isUndefined() || res.getMorph().getCase().isNominative()) {
                    if (!pit.lastname.isInDictionary && !res.lastname.getValues().contains(pit.lastname.term)) {
                        if (inf.getCase().isNominative() || inf.getCase().isUndefined()) {
                            if (pit.lastname.getMorph()._getClass().isAdjective() && inf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                            }
                            else 
                                res.lastname.add(pit.lastname.term, null, pit.getMorph().getGender(), false);
                        }
                    }
                }
            }
            if (pit.isInDictionary) 
                res.coef--;
            if (pit.lastname.isInDictionary || pit.lastname.isInOntology) 
                res.coef++;
            if (pit.lastname.isLastnameHasHiphen) 
                res.coef += (1.0F);
            if (pit.middlename != null && pit.middlename.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                res.coef--;
        }
        if (pit.firstname != null && !pit.chars.isLatinLetter()) 
            res.coef--;
        if (pit.getBeginToken() instanceof com.pullenti.ner.ReferentToken) 
            res.coef--;
    }

    public static void manageFirstname(PersonIdentityToken res, PersonItemToken pit, com.pullenti.morph.MorphBaseInfo inf) {
        if (pit.firstname == null) {
            res.firstname = new PersonMorphCollection();
            if (pit.value.indexOf('-') > 0 && pit.getEndToken().getMorphClassInDictionary().isProperName()) {
                if (pit.lastname != null) 
                    setValue2(res.firstname, pit.lastname, inf);
                else 
                    res.firstname.items.add(PersonMorphCollection.PersonMorphVariant._new2694(pit.value));
            }
            else {
                if (pit.lastname != null) 
                    res.coef--;
                if (pit.isInDictionary) 
                    res.coef--;
                setValue(res.firstname, pit.getBeginToken(), inf);
            }
        }
        else {
            res.coef++;
            if (!isAccords(pit.firstname, inf)) 
                res.coef--;
            res.firstname = new PersonMorphCollection();
            setValue2(res.firstname, pit.firstname, inf);
            if (pit.isInDictionary && !pit.firstname.isInDictionary) 
                res.coef--;
        }
        if (pit.middlename != null && pit.middlename != pit.firstname) 
            res.coef--;
        if (pit.lastname != null && ((pit.lastname.isInDictionary || pit.lastname.isInOntology))) 
            res.coef--;
        if (pit.getBeginToken() instanceof com.pullenti.ner.ReferentToken) 
            res.coef -= (2.0F);
    }

    private static void manageMiddlename(PersonIdentityToken res, PersonItemToken pit, com.pullenti.morph.MorphBaseInfo inf) {
        PersonMorphCollection mm = new PersonMorphCollection();
        res.middlename = mm;
        if (pit.middlename == null) 
            setValue(mm, pit.getBeginToken(), inf);
        else {
            res.coef++;
            if (!isAccords(pit.middlename, inf)) 
                res.coef--;
            setValue2(mm, pit.middlename, inf);
        }
    }

    private static PersonIdentityToken tryAttachSingleSurname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf) {
        if (ind >= pits.size() || pits.get(ind).lastname == null) 
            return null;
        PersonIdentityToken res = new PersonIdentityToken(pits.get(ind).getBeginToken(), pits.get(ind).getEndToken());
        if (ind == 0 && pits.size() == 1) 
            res.coef++;
        else {
            if (ind > 0 && ((!pits.get(ind - 1).isInDictionary || pits.get(ind - 1).typ == PersonItemToken.ItemType.INITIAL || pits.get(ind - 1).firstname != null))) 
                res.coef--;
            if (((ind + 1) < pits.size()) && ((!pits.get(ind + 1).isInDictionary || pits.get(ind + 1).typ == PersonItemToken.ItemType.INITIAL || pits.get(ind + 1).firstname != null))) 
                res.coef--;
        }
        res.setMorph(accordMorph(inf, pits.get(ind).lastname, null, null, pits.get(ind).getEndToken().getNext()));
        manageLastname(res, pits.get(ind), inf);
        return res;
    }

    private static PersonIdentityToken tryAttachNameSurname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp, boolean isAttrBefore) {
        if ((ind + 1) >= pits.size() || pits.get(ind + 1).typ != PersonItemToken.ItemType.VALUE || pits.get(ind).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(ind + 1).lastname == null) {
            if (!prevHasThisTyp) {
                if (pits.get(ind).chars.isLatinLetter()) {
                }
                else {
                    if (pits.get(ind).firstname == null || pits.get(ind + 1).middlename != null) 
                        return null;
                    if (pits.get(ind + 1).isNewlineAfter()) {
                    }
                    else if (pits.get(ind + 1).getEndToken().getNext() != null && pits.get(ind + 1).getEndToken().getNext().isCharOf(",.);?!")) {
                    }
                    else 
                        return null;
                }
            }
        }
        if (pits.get(ind).isNewlineAfter() || pits.get(ind).isHiphenAfter) 
            return null;
        if (pits.get(ind + 1).middlename != null && pits.get(ind + 1).middlename.isInDictionary && pits.get(ind + 1).middlename.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
            return null;
        if (isBothSurnames(pits.get(ind), pits.get(ind + 1))) {
            if (pits.size() == 2 && ind == 0 && isAttrBefore) {
            }
            else 
                return null;
        }
        PersonIdentityToken res = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken(), FioTemplateType.NAMESURNAME);
        res.coef -= ((float)ind);
        res.setMorph(accordMorph(inf, pits.get(ind + 1).lastname, pits.get(ind).firstname, null, pits.get(ind + 1).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            if (pits.get(ind + 1).lastname != null && !pits.get(ind + 1).lastname.getMorph().getCase().isUndefined()) {
                if ((pits.get(ind).lastname != null && pits.get(ind).lastname.isLastnameHasStdTail && pits.get(ind + 1).firstname != null) && pits.get(ind + 1).firstname.isInDictionary) 
                    res.coef -= (1.0F);
                else 
                    res.coef += (1.0F);
            }
            inf = res.getMorph();
        }
        manageFirstname(res, pits.get(ind), inf);
        manageLastname(res, pits.get(ind + 1), inf);
        if (pits.get(ind).firstname != null) {
            if (pits.get(ind + 1).getBeginToken() instanceof com.pullenti.ner.ReferentToken) 
                res.coef++;
            else if (((ind == 0 && pits.get(ind + 1).firstname == null && pits.get(ind + 1).middlename == null) && !pits.get(ind + 1).isInDictionary && pits.get(ind).chars.isCapitalUpper()) && pits.get(ind + 1).chars.isCapitalUpper()) {
                com.pullenti.morph.MorphClass mc1 = pits.get(ind).getBeginToken().getMorphClassInDictionary();
                if (!mc1.isNoun()) 
                    res.coef++;
            }
        }
        com.pullenti.morph.MorphClass mc = pits.get(ind).getBeginToken().getMorphClassInDictionary();
        if (mc.isVerb()) {
            if (pits.get(ind).getBeginToken().chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(pits.get(ind).getBeginToken())) {
            }
            else 
                res.coef -= (1.0F);
        }
        if (mc.isPronoun() && pits.get(ind).firstname == null) 
            return null;
        if (pits.get(ind).firstname != null && ((pits.get(ind + 1).isNewlineAfter() || ((pits.get(ind + 1).getEndToken().getNext() != null && (pits.get(ind + 1).getEndToken().getNext().isCharOf(",.")))))) && !pits.get(ind + 1).isNewlineBefore()) {
            if (pits.get(ind + 1).firstname == null && pits.get(ind + 1).middlename == null) 
                res.coef++;
            else if (pits.get(ind + 1).chars.isLatinLetter() && (ind + 2) == pits.size()) 
                res.coef++;
        }
        if (pits.get(ind + 1).middlename != null) {
            com.pullenti.ner.core.StatisticWordInfo info = pits.get(ind).kit.statistics.getWordInfo(pits.get(ind + 1).getBeginToken());
            if (info != null && info.notCapitalBeforeCount > 0) {
            }
            else {
                res.coef -= ((float)(1 + ind));
                if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                    if (pits.get(ind + 1).lastname != null && ((pits.get(ind + 1).lastname.isInDictionary || pits.get(ind + 1).lastname.isInOntology))) {
                    }
                    else 
                        for (PersonItemToken.MorphPersonItemVariant v : pits.get(ind + 1).middlename.vars) {
                            if (((short)((v.getGender().value()) & (res.getMorph().getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                res.coef -= (1.0F);
                                break;
                            }
                        }
                }
            }
        }
        if (!pits.get(ind).chars.equals(pits.get(ind + 1).chars)) {
            if (pits.get(ind).chars.isCapitalUpper() && pits.get(ind + 1).chars.isAllUpper()) {
            }
            else if (pits.get(ind).chars.isAllUpper() && pits.get(ind + 1).chars.isCapitalUpper() && pits.get(ind).firstname == null) 
                res.coef -= (10.0F);
            else if (pits.get(ind + 1).getEndToken().chars.isCapitalUpper() && pits.get(ind).chars.isCapitalUpper()) {
            }
            else 
                res.coef -= (1.0F);
            if (pits.get(ind).firstname == null || !pits.get(ind).firstname.isInDictionary || pits.get(ind).chars.isAllUpper()) 
                res.coef -= (1.0F);
        }
        else if (pits.get(ind).chars.isAllUpper()) 
            res.coef -= ((float)0.5);
        if (pits.get(ind).isInDictionary) {
            if (pits.get(ind + 1).isInDictionary) {
                res.coef -= (2.0F);
                if (pits.get(ind + 1).isNewlineAfter()) 
                    res.coef++;
                else if (pits.get(ind + 1).getEndToken().getNext() != null && pits.get(ind + 1).getEndToken().getNext().isCharOf(".,:")) 
                    res.coef++;
                if (pits.get(ind).isInDictionary && pits.get(ind).firstname == null) 
                    res.coef--;
            }
            else if (((pits.get(ind).firstname == null || !pits.get(ind).firstname.isInDictionary)) && (pits.get(ind).value.indexOf('-') < 0)) {
                if (inf.getCase().isUndefined()) 
                    res.coef -= (1.0F);
                else 
                    for (com.pullenti.morph.MorphBaseInfo mi : pits.get(ind).getBeginToken().getMorph().getItems()) {
                        if (!((com.pullenti.morph.MorphCase.ooBitand(mi.getCase(), inf.getCase()))).isUndefined()) {
                            if ((mi instanceof com.pullenti.morph.MorphWordForm) && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(mi, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                                res.coef -= (1.0F);
                                break;
                            }
                        }
                    }
            }
        }
        if (!pits.get(ind).chars.isLatinLetter()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(pits.get(ind).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getEndChar() >= pits.get(ind + 1).getBeginChar()) {
                if (pits.get(ind).getBeginToken().getMorphClassInDictionary().isAdjective()) 
                    res.coef -= (2.0F);
                else if (pits.get(ind + 1).getBeginToken().getMorphClassInDictionary().isNoun()) 
                    res.coef -= (2.0F);
            }
        }
        correctCoefAfterLastname(res, pits, ind + 2);
        if (ind > 0 && res.coef > 0 && pits.get(ind).isHiphenBefore) {
            com.pullenti.ner.core.StatisticBigrammInfo b1 = pits.get(ind).kit.statistics.getBigrammInfo(pits.get(ind - 1).getBeginToken(), pits.get(ind).getBeginToken());
            if (b1 != null && b1.secondCount == b1.pairCount) {
                PersonIdentityToken res0 = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken(), FioTemplateType.NAMESURNAME);
                manageFirstname(res0, pits.get(ind - 1), inf);
                res.firstname = PersonMorphCollection.addPrefix(res0.firstname, res.firstname);
                res.coef++;
                res.setBeginToken(pits.get(ind - 1).getBeginToken());
            }
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getBeginToken().getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(res.getEndToken().getNext(), false, null, false)) 
            res.coef -= (2.0F);
        com.pullenti.ner.core.StatisticBigrammInfo bi = pits.get(0).getBeginToken().kit.statistics.getInitialInfo(pits.get(ind).value, pits.get(ind + 1).getBeginToken());
        if (bi != null && bi.pairCount > 0) 
            res.coef += (2.0F);
        if ((!pits.get(0).isInDictionary && pits.get(1).lastname != null && pits.get(1).lastname.isLastnameHasStdTail) && !pits.get(1).isInDictionary) 
            res.coef += 0.5F;
        if (res.firstname != null && pits.get(ind).getBeginToken().isValue("СЛАВА", null)) 
            res.coef -= (3.0F);
        else if (checkLatinAfter(res) != null) 
            res.coef += (2.0F);
        if (pits.get(0).firstname == null || ((pits.get(0).firstname != null && !pits.get(0).firstname.isInDictionary))) {
            if (pits.get(0).getBeginToken().getMorphClassInDictionary().isProperGeo() && pits.get(1).lastname != null && pits.get(1).lastname.isInOntology) 
                res.coef -= (2.0F);
        }
        if (ind == 0 && pits.size() == 2 && pits.get(0).chars.isLatinLetter()) {
            if (pits.get(0).firstname != null) {
                if (!isAttrBefore && (pits.get(0).getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && pits.get(0).getBeginToken().getPrevious().chars.isCapitalUpper()) 
                    res.coef -= (1.0F);
                else 
                    res.coef += (1.0F);
            }
            if (pits.get(0).chars.isAllUpper() && pits.get(1).chars.isCapitalUpper()) 
                res.coef = 0.0F;
        }
        else if (ind == 0 && pits.size() == 2 && pits.get(1).isInDictionary) {
            if (pits.get(0).isInDictionary) 
                res.coef -= (1.0F);
            else if (pits.get(1).lastname == null || !pits.get(1).lastname.isInDictionary) {
                mc = pits.get(1).getMorphClassInDictionary();
                if (mc.isVerb() && !mc.isAdjective()) 
                    res.coef -= (1.0F);
            }
        }
        if (ind == 0 && pits.size() == 2 && pits.get(1).middlename != null) {
            com.pullenti.ner.Token t0 = pits.get(0).getBeginToken();
            if (t0.getPrevious() != null && t0.getPrevious().isComma()) 
                t0 = t0.getPrevious();
            if ((t0.getPrevious() instanceof com.pullenti.ner.TextToken) && t0.getPrevious().isNewlineBefore()) {
                com.pullenti.ner.mail.internal.MailLine li = com.pullenti.ner.mail.internal.MailLine.parse(t0.getPrevious(), 0, 0);
                if (li != null && li.typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO) 
                    return null;
            }
        }
        return res;
    }

    private static PersonIdentityToken tryAttachNameSecnameSurname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp) {
        if ((ind + 2) >= pits.size() || pits.get(ind).typ != PersonItemToken.ItemType.VALUE || pits.get(ind + 2).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(ind).isNewlineAfter()) {
            if ((pits.size() == 3 && pits.get(0).firstname != null && pits.get(1).middlename != null) && pits.get(2).lastname != null) {
            }
            else 
                return null;
        }
        if (pits.get(ind + 2).lastname == null && !prevHasThisTyp && !pits.get(ind).getMorph().getLanguage().isEn()) 
            return null;
        boolean ok = false;
        boolean needTestNameSurname = false;
        int addCoef = 0;
        if (pits.get(ind + 1).typ == PersonItemToken.ItemType.INITIAL) 
            ok = true;
        else if (pits.get(ind + 1).typ == PersonItemToken.ItemType.VALUE && pits.get(ind + 1).middlename != null) 
            ok = true;
        else if (pits.get(ind + 1).typ == PersonItemToken.ItemType.VALUE && pits.get(ind + 2).firstname == null) {
            com.pullenti.ner.core.StatisticBigrammInfo b1 = pits.get(0).kit.statistics.getBigrammInfo(pits.get(ind + 1).getBeginToken(), pits.get(ind + 2).getBeginToken());
            com.pullenti.ner.core.StatisticBigrammInfo b2 = pits.get(0).kit.statistics.getBigrammInfo(pits.get(ind).getBeginToken(), pits.get(ind + 2).getBeginToken());
            if (b1 != null) {
                if (b1.pairCount == b1.firstCount && b1.pairCount == b1.secondCount) {
                    ok = true;
                    com.pullenti.ner.core.StatisticBigrammInfo b3 = pits.get(0).kit.statistics.getBigrammInfo(pits.get(ind).getBeginToken(), pits.get(ind + 1).getBeginToken());
                    if (b3 != null) {
                        if (b3.secondCount > b3.pairCount) 
                            ok = false;
                        else if (b3.secondCount == b3.pairCount && pits.get(ind + 2).isHiphenBefore) 
                            ok = false;
                    }
                }
                else if (b2 != null && (b2.pairCount + b1.pairCount) == b1.secondCount) 
                    ok = true;
            }
            else if ((ind + 3) == pits.size() && pits.get(ind + 2).lastname != null && !pits.get(ind + 2).isInDictionary) 
                ok = true;
            if (!ok) {
                b1 = pits.get(0).kit.statistics.getInitialInfo(pits.get(ind).value, pits.get(ind + 2).getBeginToken());
                if (b1 != null && b1.pairCount > 0) {
                    ok = true;
                    addCoef = 2;
                }
            }
            if (!ok) {
                com.pullenti.ner.core.StatisticWordInfo wi = pits.get(0).kit.statistics.getWordInfo(pits.get(ind + 2).getEndToken());
                if (wi != null && wi.lowerCount == 0) {
                    if (wi.maleVerbsAfterCount > 0 || wi.femaleVerbsAfterCount > 0) {
                        ok = true;
                        addCoef = 2;
                        needTestNameSurname = true;
                        if (pits.get(ind + 1).firstname != null && pits.get(ind + 1).middlename == null) {
                            if (pits.get(ind).firstname == null && pits.get(ind).value != null && pits.get(ind).isInDictionary) 
                                ok = false;
                        }
                        if (pits.get(ind + 1).lastname != null && ((pits.get(ind + 1).lastname.isInDictionary || pits.get(ind + 1).lastname.isInOntology))) 
                            ok = false;
                    }
                }
            }
            if (!ok) {
                if ((ind == 0 && pits.size() == 3 && pits.get(0).chars.isLatinLetter()) && pits.get(1).chars.isLatinLetter() && pits.get(2).chars.isLatinLetter()) {
                    if (pits.get(0).firstname != null && pits.get(2).lastname != null) 
                        ok = true;
                }
            }
        }
        if (!ok) 
            return null;
        if (isBothSurnames(pits.get(ind), pits.get(ind + 2))) 
            return null;
        ok = false;
        for (int i = ind; i < (ind + 3); i++) {
            if (pits.get(i).typ == PersonItemToken.ItemType.INITIAL) 
                ok = true;
            else if (!pits.get(i).isInDictionary) {
                com.pullenti.morph.MorphClass cla = pits.get(i).getBeginToken().getMorphClassInDictionary();
                if (cla.isProperName() || cla.isProperSurname() || cla.isProperSecname()) 
                    ok = true;
                else if (cla.isUndefined()) 
                    ok = true;
            }
        }
        if (!ok) 
            return null;
        PersonIdentityToken res = new PersonIdentityToken(pits.get(ind).getBeginToken(), pits.get(ind + 2).getEndToken());
        res.typ = (pits.get(ind + 1).typ == PersonItemToken.ItemType.INITIAL ? FioTemplateType.NAMEISURNAME : FioTemplateType.NAMESECNAMESURNAME);
        res.coef -= ((float)ind);
        res.setMorph(accordMorph(inf, pits.get(ind + 2).lastname, pits.get(ind).firstname, pits.get(ind + 1).middlename, pits.get(ind + 2).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            res.coef += (1.0F);
            inf = res.getMorph();
        }
        manageFirstname(res, pits.get(ind), inf);
        manageLastname(res, pits.get(ind + 2), inf);
        if (pits.get(ind + 1).middlename != null && pits.get(ind + 1).middlename.vars.size() > 0) {
            res.coef++;
            res.middlename = pits.get(ind + 1).middlename.vars.get(0).value;
            if (pits.get(ind + 1).middlename.vars.size() > 1) {
                res.middlename = new PersonMorphCollection();
                setValue2((PersonMorphCollection)com.pullenti.unisharp.Utils.cast(res.middlename, PersonMorphCollection.class), pits.get(ind + 1).middlename, inf);
            }
            if (pits.get(ind + 2).lastname != null) {
                if (pits.get(ind + 2).lastname.isInDictionary || pits.get(ind + 2).lastname.isLastnameHasStdTail || pits.get(ind + 2).lastname.isHasStdPostfix) 
                    res.coef++;
            }
        }
        else if (pits.get(ind + 1).typ == PersonItemToken.ItemType.INITIAL) {
            res.middlename = pits.get(ind + 1).value;
            res.coef++;
            if (pits.get(ind + 2).lastname != null) {
            }
            else {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(pits.get(ind + 2).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS.value())), 0, null);
                if (npt != null && npt.getEndChar() > pits.get(ind + 2).getEndChar()) 
                    res.coef -= (2.0F);
            }
        }
        else if (pits.get(ind + 1).firstname != null && pits.get(ind + 2).middlename != null && pits.size() == 3) 
            res.coef -= (2.0F);
        else {
            manageMiddlename(res, pits.get(ind + 1), inf);
            res.coef += ((float)0.5);
        }
        if (!pits.get(ind).chars.equals(pits.get(ind + 2).chars)) {
            res.coef -= (1.0F);
            if (pits.get(ind).chars.isAllUpper()) 
                res.coef -= (1.0F);
        }
        else if (pits.get(ind + 1).typ != PersonItemToken.ItemType.INITIAL && !pits.get(ind).chars.equals(pits.get(ind + 1).chars)) 
            res.coef -= (1.0F);
        correctCoefAfterLastname(res, pits, ind + 3);
        res.coef += ((float)addCoef);
        if (pits.get(ind).isInDictionary && pits.get(ind + 1).isInDictionary && pits.get(ind + 2).isInDictionary) 
            res.coef--;
        return res;
    }

    private static PersonIdentityToken tryAttachNameSecname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp) {
        if ((ind != 0 || (ind + 2) != pits.size() || pits.get(ind).typ != PersonItemToken.ItemType.VALUE) || pits.get(ind + 1).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(ind).isNewlineAfter()) 
            return null;
        if (pits.get(ind).firstname == null || pits.get(ind + 1).middlename == null) 
            return null;
        PersonIdentityToken res = new PersonIdentityToken(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken());
        res.typ = FioTemplateType.NAMESECNAME;
        res.setMorph(accordMorph(inf, null, pits.get(ind).firstname, pits.get(ind + 1).middlename, pits.get(ind + 1).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            res.coef += (1.0F);
            inf = res.getMorph();
        }
        manageFirstname(res, pits.get(ind), inf);
        manageMiddlename(res, pits.get(ind + 1), inf);
        res.coef = 2.0F;
        return res;
    }

    private static void correctCoefAfterLastname(PersonIdentityToken res, java.util.ArrayList<PersonItemToken> pits, int ind) {
        if (!pits.get(ind - 1).isNewlineAfter()) {
            PersonAttrToken pat = PersonAttrToken.tryAttach(pits.get(ind - 1).getBeginToken(), PersonAttrToken.PersonAttrAttachAttrs.ONLYKEYWORD);
            if (pat != null) 
                res.coef -= (1.0F);
        }
        if (ind >= pits.size()) {
            if (checkLatinAfter(res) != null) 
                res.coef += (2.0F);
            com.pullenti.ner.Token te = pits.get(ind - 1).getEndToken();
            com.pullenti.ner.core.StatisticWordInfo stat = te.kit.statistics.getWordInfo(te);
            if (stat != null) {
                if (stat.hasBeforePersonAttr) 
                    res.coef++;
            }
            te = pits.get(ind - 1).getEndToken().getNext();
            if (te == null) 
                return;
            if (PersonHelper.isPersonSayOrAttrAfter(te)) {
                res.coef++;
                if (res.chars.isLatinLetter() && res.typ == FioTemplateType.NAMESURNAME) 
                    res.coef += (2.0F);
            }
            if (!te.chars.isLetter() && !te.chars.isAllLower()) 
                return;
            com.pullenti.ner.core.StatisticWordInfo wi = te.kit.statistics.getWordInfo(te);
            if (wi != null) {
                if (wi.lowerCount > 0) 
                    res.coef--;
                else if ((wi.femaleVerbsAfterCount + wi.maleVerbsAfterCount) > 0) 
                    res.coef++;
            }
            return;
        }
        if (ind == 0) 
            return;
        if (pits.get(ind).typ == PersonItemToken.ItemType.VALUE && ((pits.get(ind).firstname == null || ind == (pits.size() - 1)))) {
            com.pullenti.ner.core.StatisticBigrammInfo b1 = pits.get(0).kit.statistics.getBigrammInfo(pits.get(ind - 1).getBeginToken(), pits.get(ind).getBeginToken());
            if ((b1 != null && b1.firstCount == b1.pairCount && b1.secondCount == b1.pairCount) && b1.pairCount > 0) {
                boolean ok = false;
                if (b1.pairCount > 1 && pits.get(ind).getWhitespacesBeforeCount() == 1) 
                    ok = true;
                else if (pits.get(ind).isHiphenBefore && pits.get(ind).lastname != null) 
                    ok = true;
                if (ok) {
                    PersonIdentityToken res1 = new PersonIdentityToken(pits.get(ind).getBeginToken(), pits.get(ind).getEndToken());
                    manageLastname(res1, pits.get(ind), res.getMorph());
                    res.lastname = PersonMorphCollection.addPrefix(res.lastname, res1.lastname);
                    res.setEndToken(pits.get(ind).getEndToken());
                    res.coef++;
                    ind++;
                    if (ind >= pits.size()) 
                        return;
                }
            }
        }
        if (pits.get(ind - 1).getWhitespacesBeforeCount() > pits.get(ind - 1).getWhitespacesAfterCount()) 
            res.coef -= (1.0F);
        else if (pits.get(ind - 1).getWhitespacesBeforeCount() == pits.get(ind - 1).getWhitespacesAfterCount()) {
            if (pits.get(ind).lastname != null || pits.get(ind).firstname != null) {
                if (!pits.get(ind).isInDictionary) 
                    res.coef -= (1.0F);
            }
        }
    }

    private static void correctCoefForLastname(PersonIdentityToken pit, PersonItemToken it) {
        if (it.getBeginToken() != it.getEndToken()) 
            return;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(it.getBeginToken(), com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return;
        boolean inDic = false;
        boolean hasStd = false;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isProperSurname()) {
            }
            else if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                inDic = true;
        }
        if (it.lastname != null) 
            hasStd = it.lastname.isLastnameHasStdTail;
        if (!hasStd && inDic) 
            pit.coef -= 1.5F;
    }

    private static PersonIdentityToken tryAttachSurnameName(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp) {
        if ((ind + 1) >= pits.size() || pits.get(ind + 1).typ != PersonItemToken.ItemType.VALUE || pits.get(ind).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(ind).lastname == null && !prevHasThisTyp) 
            return null;
        if (isBothSurnames(pits.get(ind), pits.get(ind + 1))) 
            return null;
        PersonIdentityToken res = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken(), FioTemplateType.SURNAMENAME);
        res.coef -= ((float)ind);
        if (pits.get(ind).isNewlineAfter()) {
            res.coef--;
            if (pits.get(ind).getWhitespacesAfterCount() > 15) 
                res.coef--;
        }
        res.setMorph(accordMorph(inf, pits.get(ind).lastname, pits.get(ind + 1).firstname, null, pits.get(ind + 1).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            if (pits.get(ind).lastname != null && !pits.get(ind).lastname.getMorph().getCase().isUndefined()) 
                res.coef += (1.0F);
            inf = res.getMorph();
        }
        manageLastname(res, pits.get(ind), inf);
        manageFirstname(res, pits.get(ind + 1), inf);
        correctCoefForLastname(res, pits.get(ind));
        if (!pits.get(ind).chars.equals(pits.get(ind + 1).chars)) {
            res.coef -= (1.0F);
            if (pits.get(ind + 1).firstname == null || !pits.get(ind + 1).firstname.isInDictionary || pits.get(ind + 1).chars.isAllUpper()) 
                res.coef -= (1.0F);
        }
        else if (pits.get(ind).chars.isAllUpper()) 
            res.coef -= ((float)0.5);
        if (pits.get(ind + 1).isInDictionary && ((pits.get(ind + 1).firstname == null || !pits.get(ind + 1).firstname.isInDictionary))) 
            res.coef -= (1.0F);
        correctCoefAfterName(res, pits, ind + 2);
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(pits.get(ind + 1).getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null && npt.getEndToken() != pits.get(ind + 1).getEndToken()) 
            res.coef -= (1.0F);
        if (ind == 0) 
            correctCoefSNS(res, pits, ind + 2);
        if (pits.get(ind).getEndToken().getNext().isHiphen()) 
            res.coef -= (2.0F);
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getBeginToken().getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(res.getEndToken().getNext(), false, null, false)) 
            res.coef -= (2.0F);
        if (pits.get(ind).isInDictionary) {
            com.pullenti.morph.MorphClass mc = pits.get(ind).getBeginToken().getMorphClassInDictionary();
            if (mc.isPronoun() || mc.isPersonalPronoun()) 
                return null;
        }
        if (((pits.size() == 2 && ind == 0 && pits.get(0).chars.isAllUpper()) && pits.get(1).chars.isCapitalUpper() && !pits.get(1).isInDictionary) && (res.coef < 0)) {
            if (pits.get(1).lastname != null && ((pits.get(1).lastname.isHasStdPostfix || pits.get(1).lastname.isInDictionary || pits.get(1).lastname.isLastnameHasStdTail))) {
            }
            else 
                res.coef = 0.0F;
        }
        if (pits.size() == 2 && ind == 0 && pits.get(1).getBeginToken().isValue("ЛАВРА", null)) {
            if (pits.get(0).getEndToken().getMorph()._getClass().isAdjective()) 
                return null;
        }
        return res;
    }

    private static void correctCoefSNS(PersonIdentityToken res, java.util.ArrayList<PersonItemToken> pits, int indAfter) {
        if (indAfter >= pits.size()) 
            return;
        if (pits.get(0).lastname == null || !pits.get(0).lastname.isLastnameHasStdTail) {
            com.pullenti.ner.core.StatisticWordInfo stat = pits.get(0).kit.statistics.getWordInfo(pits.get(1).getBeginToken());
            com.pullenti.ner.core.StatisticWordInfo statA = pits.get(0).kit.statistics.getWordInfo(pits.get(2).getBeginToken());
            com.pullenti.ner.core.StatisticWordInfo statB = pits.get(0).kit.statistics.getWordInfo(pits.get(0).getBeginToken());
            if (stat != null && statA != null && statB != null) {
                if (stat.likeCharsAfterWords != null && stat.likeCharsBeforeWords != null) {
                    int couA = 0;
                    int couB = 0;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapcouA2700 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.tryGetValue(stat.likeCharsAfterWords, statA, wrapcouA2700);
                    couA = (wrapcouA2700.value != null ? wrapcouA2700.value : 0);
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapcouB2699 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.tryGetValue(stat.likeCharsBeforeWords, statB, wrapcouB2699);
                    couB = (wrapcouB2699.value != null ? wrapcouB2699.value : 0);
                    if (couA == stat.totalCount && (couB < stat.totalCount)) 
                        res.coef -= (2.0F);
                }
            }
            return;
        }
        if (pits.get(1).firstname == null) 
            return;
        PersonItemToken.MorphPersonItem middle = null;
        if (indAfter > 2 && pits.get(2).middlename != null) 
            middle = pits.get(2).middlename;
        com.pullenti.morph.MorphBaseInfo inf = new com.pullenti.morph.MorphBaseInfo();
        com.pullenti.morph.MorphBaseInfo mi1 = accordMorph(inf, pits.get(0).lastname, pits.get(1).firstname, middle, null);
        if (mi1.getCase().isUndefined()) 
            res.coef -= (1.0F);
        if (pits.get(indAfter).lastname == null || !pits.get(indAfter).lastname.isLastnameHasStdTail) 
            return;
        com.pullenti.morph.MorphBaseInfo mi2 = accordMorph(inf, pits.get(indAfter).lastname, pits.get(1).firstname, middle, pits.get(indAfter).getEndToken().getNext());
        if (!mi2.getCase().isUndefined()) 
            res.coef -= (1.0F);
    }

    private static PersonIdentityToken tryAttachSurnameNameSecname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp, boolean always) {
        if ((ind + 2) >= pits.size() || pits.get(ind + 1).typ != PersonItemToken.ItemType.VALUE || pits.get(ind).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(ind).lastname == null && !prevHasThisTyp) {
            if (ind > 0) 
                return null;
            if (pits.size() == 3 && !always) {
                com.pullenti.ner.Token tt1 = pits.get(2).getEndToken().getNext();
                if (tt1 != null && tt1.isComma()) 
                    tt1 = tt1.getNext();
                if (tt1 != null && !tt1.isNewlineBefore() && PersonAttrToken.tryAttach(tt1, PersonAttrToken.PersonAttrAttachAttrs.ONLYKEYWORD) != null) {
                }
                else 
                    return null;
            }
        }
        if (!always) {
            if (isBothSurnames(pits.get(ind), pits.get(ind + 2))) 
                return null;
            if (isBothSurnames(pits.get(ind), pits.get(ind + 1))) {
                if (pits.size() == 3 && ind == 0 && pits.get(2).middlename != null) {
                }
                else 
                    return null;
            }
        }
        PersonIdentityToken res = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 2).getEndToken(), FioTemplateType.SURNAMENAMESECNAME);
        if (pits.get(ind + 2).middlename == null) {
            if ((ind + 2) == (pits.size() - 1) && prevHasThisTyp) 
                res.coef += (1.0F);
            else if (pits.get(ind + 1).firstname != null && pits.get(ind + 2).firstname != null) {
            }
            else if (!always) 
                return null;
        }
        res.coef -= ((float)ind);
        if (pits.get(ind).isNewlineAfter()) {
            if (pits.get(ind).isNewlineBefore() && pits.get(ind + 2).isNewlineAfter()) {
            }
            else if (pits.get(ind).getBeginToken().getPrevious() != null && pits.get(ind).getBeginToken().getPrevious().isTableControlChar()) {
            }
            else {
                res.coef--;
                if (pits.get(ind).getWhitespacesAfterCount() > 15) 
                    res.coef--;
            }
        }
        if (pits.get(ind + 1).isNewlineAfter()) {
            if (pits.get(ind).isNewlineBefore() && pits.get(ind + 2).isNewlineAfter()) {
            }
            else if (pits.get(ind).getBeginToken().getPrevious() != null && pits.get(ind).getBeginToken().getPrevious().isTableControlChar()) {
            }
            else {
                res.coef--;
                if (pits.get(ind + 1).getWhitespacesAfterCount() > 15) 
                    res.coef--;
            }
        }
        res.setMorph(accordMorph(inf, pits.get(ind).lastname, pits.get(ind + 1).firstname, pits.get(ind + 2).middlename, pits.get(ind + 2).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
            res.coef += 1.5F;
            inf = res.getMorph();
        }
        manageLastname(res, pits.get(ind), inf);
        correctCoefForLastname(res, pits.get(ind));
        manageFirstname(res, pits.get(ind + 1), inf);
        if (pits.get(ind + 2).middlename != null && pits.get(ind + 2).middlename.vars.size() > 0) {
            res.coef++;
            res.middlename = pits.get(ind + 2).middlename.vars.get(0).value;
            if (pits.get(ind + 2).middlename.vars.size() > 1) {
                res.middlename = new PersonMorphCollection();
                setValue2((PersonMorphCollection)com.pullenti.unisharp.Utils.cast(res.middlename, PersonMorphCollection.class), pits.get(ind + 2).middlename, inf);
            }
            if (pits.get(ind + 1).firstname != null && pits.size() == 3 && !pits.get(ind).isInDictionary) 
                res.coef++;
        }
        else 
            manageMiddlename(res, pits.get(ind + 2), inf);
        if (!pits.get(ind).chars.equals(pits.get(ind + 1).chars) || !pits.get(ind).chars.equals(pits.get(ind + 2).chars)) {
            res.coef -= (1.0F);
            if (pits.get(ind).chars.isAllUpper() && pits.get(ind + 1).chars.isCapitalUpper() && pits.get(ind + 2).chars.isCapitalUpper()) 
                res.coef += (2.0F);
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(pits.get(ind).getBeginToken(), com.pullenti.ner.TextToken.class);
        if (tt != null) {
            if (tt.isValue("УВАЖАЕМЫЙ", null) || tt.isValue("ДОРОГОЙ", null)) 
                res.coef -= (2.0F);
        }
        correctCoefAfterName(res, pits, ind + 3);
        if (ind == 0) 
            correctCoefSNS(res, pits, ind + 3);
        if (pits.get(ind).isInDictionary && pits.get(ind + 1).isInDictionary && pits.get(ind + 2).isInDictionary) 
            res.coef--;
        else if (pits.get(ind).isInDictionary && pits.get(ind).chars.isCapitalUpper() && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(pits.get(ind).getBeginToken())) {
            com.pullenti.morph.MorphClass mc = pits.get(ind).getBeginToken().getMorphClassInDictionary();
            if ((mc.isAdverb() || mc.isVerb() || mc.isPreposition()) || mc.isConjunction()) 
                res.coef--;
        }
        return res;
    }

    private static void correctCoefAfterName(PersonIdentityToken res, java.util.ArrayList<PersonItemToken> pits, int ind) {
        if (ind >= pits.size()) 
            return;
        if (ind == 0) 
            return;
        if (pits.get(ind - 1).getWhitespacesBeforeCount() > pits.get(ind - 1).getWhitespacesAfterCount()) 
            res.coef -= (1.0F);
        else if (pits.get(ind - 1).getWhitespacesBeforeCount() == pits.get(ind - 1).getWhitespacesAfterCount()) {
            if (pits.get(ind).lastname != null || pits.get(ind).firstname != null || pits.get(ind).middlename != null) 
                res.coef -= (1.0F);
        }
        com.pullenti.ner.Token t = pits.get(ind - 1).getEndToken().getNext();
        if (t != null && t.getNext() != null && t.getNext().isChar(',')) 
            t = t.getNext();
        if (t != null) {
            if (PersonAttrToken.tryAttach(t, PersonAttrToken.PersonAttrAttachAttrs.ONLYKEYWORD) != null) 
                res.coef += (1.0F);
        }
    }

    private static float _calcCoefAfter(com.pullenti.ner.Token tt) {
        if (tt != null && tt.isComma()) 
            tt = tt.getNext();
        PersonAttrToken attr = PersonAttrToken.tryAttach(tt, PersonAttrToken.PersonAttrAttachAttrs.ONLYKEYWORD);
        if (attr != null && attr.age != null) 
            return 3.0F;
        if (tt != null && tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "DATE")) {
            float co = 1.0F;
            if (tt.getNext() != null && tt.getNext().isValue("Р", null)) 
                co += (2.0F);
            return co;
        }
        return 0.0F;
    }

    private static PersonIdentityToken tryAttachSurnameII(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf) {
        if ((ind + 1) >= pits.size() || pits.get(ind + 1).typ != PersonItemToken.ItemType.INITIAL || pits.get(ind).typ == PersonItemToken.ItemType.INITIAL) 
            return null;
        if (pits.get(ind).isNewlineAfter()) 
            return null;
        if (pits.get(ind).lastname == null) 
            return null;
        PersonIdentityToken res = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken(), FioTemplateType.SURNAMEI);
        res.coef -= ((float)ind);
        manageLastname(res, pits.get(ind), inf);
        if (pits.get(ind).isAsianItem(false) && pits.get(ind).lastname != null && pits.get(ind).lastname.isChinaSurname()) {
        }
        else if (pits.get(ind).firstname != null && pits.get(ind).firstname.isInDictionary) {
            if (pits.get(ind).lastname == null || !pits.get(ind).lastname.isLastnameHasStdTail) {
                if ((ind == 0 && pits.size() == 3 && !pits.get(1).isNewlineAfter()) && !pits.get(2).isWhitespaceAfter()) {
                }
                else 
                    res.coef -= (2.0F);
            }
        }
        res.setMorph((pits.get(ind).lastname == null ? pits.get(ind).getMorph() : pits.get(ind).lastname.getMorph()));
        if (res.lastname.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
            res.getMorph().setGender(res.lastname.getGender());
        if (pits.get(ind).getWhitespacesAfterCount() < 2) 
            res.coef += ((float)0.5);
        res.firstname = new PersonMorphCollection();
        res.firstname.add(pits.get(ind + 1).value, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        int i1 = ind + 2;
        if ((i1 < pits.size()) && pits.get(i1).typ == PersonItemToken.ItemType.INITIAL) {
            res.typ = FioTemplateType.SURNAMEII;
            res.setEndToken(pits.get(i1).getEndToken());
            res.middlename = pits.get(i1).value;
            if (pits.get(i1).getWhitespacesBeforeCount() < 2) 
                res.coef += ((float)0.5);
            i1++;
        }
        if (i1 >= pits.size()) {
            if (pits.get(0).lastname != null && ((pits.get(0).lastname.isInDictionary || pits.get(0).lastname.isInOntology)) && pits.get(0).firstname == null) 
                res.coef++;
            return res;
        }
        if (pits.get(ind).getWhitespacesAfterCount() > pits.get(i1).getWhitespacesBeforeCount()) 
            res.coef--;
        else if (pits.get(ind).getWhitespacesAfterCount() == pits.get(i1).getWhitespacesBeforeCount() && pits.get(i1).lastname != null) {
            if ((i1 + 3) == pits.size() && pits.get(i1 + 1).typ == PersonItemToken.ItemType.INITIAL && pits.get(i1 + 2).typ == PersonItemToken.ItemType.INITIAL) {
            }
            else {
                if (pits.get(i1).isInDictionary && pits.get(i1).getBeginToken().getMorphClassInDictionary().isNoun()) {
                }
                else 
                    res.coef--;
                boolean ok = true;
                for (com.pullenti.ner.Token tt = pits.get(ind).getBeginToken(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isNewlineBefore()) 
                        break;
                    else if (tt.getReferent() != null && !(tt.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
                        ok = false;
                        break;
                    }
                    else if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && tt != pits.get(ind).getBeginToken()) {
                        ok = false;
                        break;
                    }
                }
                if (ok) 
                    res.coef++;
            }
        }
        com.pullenti.morph.MorphClass mc = pits.get(0).getBeginToken().getMorphClassInDictionary();
        if (!mc.isProper() && mc.isNoun()) {
            if (((short)((pits.get(0).getBeginToken().getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.PLURAL.value())) 
                res.coef--;
            else if (pits.get(0).getBeginToken().isValue("ГЛАВА", null) || pits.get(0).getBeginToken().isValue("ЧАСТЬ", "ЧАСТИНА") || pits.get(0).getBeginToken().isValue("СТАТЬЯ", "СТАТТЯ")) 
                return null;
        }
        return res;
    }

    private static PersonIdentityToken tryAttachIISurname(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf) {
        if ((ind + 1) >= pits.size() || pits.get(ind).typ != PersonItemToken.ItemType.INITIAL) 
            return null;
        if (ind > 0) {
            if (pits.get(ind - 1).typ == PersonItemToken.ItemType.INITIAL) 
                return null;
        }
        if (pits.get(ind).isNewlineAfter()) 
            return null;
        PersonIdentityToken res = _new2691(pits.get(ind).getBeginToken(), pits.get(ind + 1).getEndToken(), FioTemplateType.ISURNAME);
        res.coef -= ((float)ind);
        res.firstname = new PersonMorphCollection();
        res.firstname.add(pits.get(ind).value, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        int i1 = ind + 1;
        if (pits.get(i1).typ == PersonItemToken.ItemType.INITIAL) {
            res.typ = FioTemplateType.IISURNAME;
            res.middlename = pits.get(i1).value;
            if (pits.get(i1).getWhitespacesBeforeCount() < 2) 
                res.coef += ((float)0.5);
            i1++;
        }
        if (i1 >= pits.size() || pits.get(i1).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if (pits.get(i1).isNewlineBefore() && !pits.get(i1).isNewlineAfter()) 
            return null;
        res.setEndToken(pits.get(i1).getEndToken());
        PersonItemToken prev = null;
        if (!pits.get(ind).isNewlineBefore()) {
            if (ind > 0) 
                prev = pits.get(ind - 1);
            else {
                prev = PersonItemToken.tryAttach(pits.get(ind).getBeginToken().getPrevious(), (pits.get(i1).chars.isLatinLetter() ? PersonItemToken.ParseAttr.CANBELATIN : PersonItemToken.ParseAttr.NO), null);
                if (prev != null) {
                    if (PersonAttrToken.tryAttachWord(prev.getBeginToken(), true) != null) {
                        prev = null;
                        res.coef++;
                    }
                }
            }
        }
        manageLastname(res, pits.get(i1), inf);
        if (pits.get(i1).lastname != null && pits.get(i1).lastname.isInOntology) 
            res.coef++;
        if (pits.get(i1).firstname != null && pits.get(i1).firstname.isInDictionary) {
            if (pits.get(i1).lastname == null || ((!pits.get(i1).lastname.isLastnameHasStdTail && !pits.get(i1).lastname.isInOntology))) 
                res.coef -= (2.0F);
        }
        if (prev != null) {
            com.pullenti.morph.MorphClass mc = prev.getBeginToken().getMorphClassInDictionary();
            if (mc.isPreposition() || mc.isAdverb() || mc.isVerb()) {
                res.coef += ((float)ind);
                if (pits.get(i1).lastname != null) {
                    if (pits.get(i1).lastname.isInDictionary || pits.get(i1).lastname.isInOntology) 
                        res.coef += (1.0F);
                }
            }
            if (prev.lastname != null && ((prev.lastname.isLastnameHasStdTail || prev.lastname.isInDictionary))) 
                res.coef -= (1.0F);
        }
        res.setMorph((pits.get(i1).lastname == null ? pits.get(i1).getMorph() : pits.get(i1).lastname.getMorph()));
        if (res.lastname.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
            res.getMorph().setGender(res.lastname.getGender());
        if (pits.get(i1).getWhitespacesBeforeCount() < 2) {
            if (!pits.get(ind).isNewlineBefore() && (pits.get(ind).getWhitespacesBeforeCount() < 2) && prev != null) {
            }
            else 
                res.coef += ((float)0.5);
        }
        if (prev == null) {
            if (pits.get(ind).isNewlineBefore() && pits.get(i1).isNewlineAfter()) 
                res.coef += (1.0F);
            else if (pits.get(i1).getEndToken().getNext() != null && ((pits.get(i1).getEndToken().getNext().isCharOf(";,.") || pits.get(i1).getEndToken().getNext().getMorph()._getClass().isConjunction()))) 
                res.coef += (1.0F);
            else if (pits.get(i1).isNewlineAfter()) 
                res.coef += (1.0F);
            return res;
        }
        if (prev.getWhitespacesAfterCount() < pits.get(i1).getWhitespacesBeforeCount()) 
            res.coef--;
        else if (prev.getWhitespacesAfterCount() == pits.get(i1).getWhitespacesBeforeCount() && prev.lastname != null) 
            res.coef--;
        return res;
    }

    private static PersonIdentityToken tryAttachKing(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp) {
        if (ind > 0 || ind >= pits.size()) 
            return null;
        if (pits.get(0).isNewlineBefore()) 
            return null;
        if (pits.get(0).firstname == null) {
            if (pits.get(0).isInDictionary || !pits.get(0).chars.isCyrillicLetter()) 
                return null;
        }
        if (pits.get(0).getBeginToken().isValue("ТОМ", null)) 
            return null;
        if (pits.get(0).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        int i = 0;
        if (pits.size() > 1 && ((pits.get(1).firstname != null || pits.get(1).middlename != null))) 
            i++;
        int num = 0;
        boolean roman = false;
        boolean ok = false;
        com.pullenti.ner.Token t = pits.get(i).getEndToken().getNext();
        com.pullenti.ner.Token numt = null;
        if (t instanceof com.pullenti.ner.NumberToken) {
            if (pits.get(i).getWhitespacesAfterCount() > 2) 
                return null;
            if (t.chars.isAllLower() || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() == null) 
                return null;
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
            numt = t;
            if (!t.getMorph()._getClass().isAdjective()) 
                return null;
        }
        else {
            if (((i + 2) < pits.size()) && pits.get(i + 1).typ == PersonItemToken.ItemType.INITIAL) 
                return null;
            com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if ((nt != null && nt.getIntValue() != null && com.pullenti.unisharp.Utils.stringsNe(nt.getValue(), "100")) && (nt.getWhitespacesBeforeCount() < 3) && t.chars.isAllUpper()) {
                num = nt.getIntValue();
                roman = true;
                t = (numt = nt.getEndToken());
            }
        }
        if (num < 1) {
            if (((pits.get(0).firstname != null || pits.size() == 1)) && prevHasThisTyp) {
                if (pits.size() == 1) 
                    ok = true;
                else if (pits.size() == 2 && ((pits.get(0).getEndToken().getNext().isHiphen() || pits.get(1).firstname != null))) 
                    ok = true;
            }
            if (!ok) 
                return null;
        }
        PersonIdentityToken res = _new2691(pits.get(0).getBeginToken(), pits.get(0).getEndToken(), FioTemplateType.KING);
        res.setMorph(accordMorph(inf, null, pits.get(0).firstname, (pits.size() == 2 ? ((pits.get(1).middlename != null ? pits.get(1).middlename : pits.get(1).firstname)) : null), pits.get((pits.size() == 2 ? 1 : 0)).getEndToken().getNext()));
        if (res.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || res.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
            inf = res.getMorph();
        if (inf.getGender() != com.pullenti.morph.MorphGender.FEMINIE && inf.getGender() != com.pullenti.morph.MorphGender.MASCULINE && !roman) 
            return null;
        if (pits.get(0).firstname != null) 
            manageFirstname(res, pits.get(0), inf);
        else 
            manageLastname(res, pits.get(0), inf);
        if (i > 0) {
            manageMiddlename(res, pits.get(1), inf);
            res.setEndToken(pits.get(1).getEndToken());
        }
        if (num > 0) {
            if (res.firstname == null && res.lastname != null) 
                res.firstname = res.lastname;
            res.lastname = new PersonMorphCollection();
            res.lastname.number = num;
            res.setEndToken(numt);
            if (i == 0 && (numt.getWhitespacesAfterCount() < 2)) {
                java.util.ArrayList<PersonItemToken> pits1 = PersonItemToken.tryAttachList(numt.getNext(), PersonItemToken.ParseAttr.NO, 2);
                if (pits1 != null && pits1.size() == 1 && pits1.get(0).firstname != null) {
                    manageMiddlename(res, pits1.get(0), inf);
                    res.setEndToken(pits1.get(0).getEndToken());
                }
            }
        }
        res.coef = (float)(num > 0 ? 3 : 2);
        return res;
    }

    private static PersonIdentityToken tryAttachArabic(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, boolean prevHasThisTyp) {
        if (ind > 0 || (pits.size() < 4)) 
            return null;
        int i;
        boolean hasHiph = false;
        int errs = 0;
        for (i = 0; i < pits.size(); i++) {
            PersonItemToken p = pits.get(i);
            if (i > 0 && p.getWhitespacesBeforeCount() > 2) 
                break;
            if (p.middlename != null || p.value == null) 
                return null;
            if (p.value.indexOf('-') > 0) {
                hasHiph = true;
                continue;
            }
            if (!p.chars.isCapitalUpper()) 
                break;
            if (p.isInDictionary && p.firstname == null) 
                errs++;
        }
        if ((i < 4) || errs > 1) 
            return null;
        if ((((i < 5) || i != pits.size())) && !hasHiph) {
            if (((pits.size() == 4 && i == pits.size() && pits.get(0).firstname != null) && pits.get(1).firstname != null && pits.get(2).firstname != null) && pits.get(3).firstname == null && pits.get(3).middlename == null) {
            }
            else 
                return null;
        }
        PersonIdentityToken res = _new2691(pits.get(0).getBeginToken(), pits.get(i - 1).getEndToken(), FioTemplateType.ARABICLONG);
        res.coef = (float)(hasHiph ? 3 : 2);
        res.setMorph(pits.get(0).getMorph());
        res.lastname = new PersonMorphCollection();
        if (pits.get(0).firstname != null) {
            manageFirstname(res, pits.get(0), inf);
            res.lastname = res.firstname;
            res.firstname = null;
        }
        else if (pits.get(0).lastname != null) 
            manageLastname(res, pits.get(0), inf);
        else 
            res.lastname.items.add(PersonMorphCollection.PersonMorphVariant._new2694(pits.get(0).value));
        for (int j = 1; j < i; j++) {
            PersonItemToken p = pits.get(j);
            for (PersonMorphCollection.PersonMorphVariant v : res.lastname.items) {
                v.value = (v.value + " " + p.value);
            }
        }
        return res;
    }

    private static PersonIdentityToken tryAttachAsian(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf, int cou, boolean prevHasThisTyp) {
        if (ind > 0 || ind >= pits.size() || ((pits.size() != cou && pits.size() != (cou * 2)))) 
            return null;
        if (pits.get(0).lastname != null && pits.get(0).lastname.isChinaSurname() && pits.get(0).chars.isCapitalUpper()) {
            if (cou == 3) {
                if (!pits.get(1).isAsianItem(false)) 
                    return null;
                if (!pits.get(2).isAsianItem(true)) 
                    return null;
            }
            else if (cou == 2) {
                if (pits.get(1).typ != PersonItemToken.ItemType.VALUE) 
                    return null;
            }
        }
        else if (cou == 3) {
            if (!pits.get(0).isAsianItem(false)) 
                return null;
            if (!pits.get(1).isAsianItem(false)) 
                return null;
            if (!pits.get(2).isAsianItem(true)) 
                return null;
        }
        else {
            if (!pits.get(0).isAsianItem(false)) 
                return null;
            if (!pits.get(1).isAsianItem(true)) 
                return null;
        }
        com.pullenti.morph.MorphClass mc0 = pits.get(0).getMorphClassInDictionary();
        if (mc0.isPronoun() && pits.get(1).firstname != null) 
            return null;
        cou--;
        boolean isChineSur = pits.get(0).lastname != null && pits.get(0).lastname.isChinaSurname();
        PersonIdentityToken res = _new2691(pits.get(0).getBeginToken(), pits.get(cou).getEndToken(), FioTemplateType.ASIANNAME);
        if (pits.get(cou).lastname != null) 
            res.setMorph(accordMorph(inf, pits.get(cou).lastname, null, null, pits.get(cou).getEndToken().getNext()));
        if (!res.getMorph().getCase().isUndefined()) 
            inf = res.getMorph();
        if (isChineSur) {
            res.typ = FioTemplateType.ASIANSURNAMENAME;
            res.coef = 2.0F;
            if (pits.get(1).isAsianItem(true)) 
                res.coef += (1.0F);
            manageLastname(res, pits.get(0), inf);
            String tr = com.pullenti.ner.person.PersonReferent._DelSurnameEnd(pits.get(0).value);
            if (com.pullenti.unisharp.Utils.stringsNe(tr, pits.get(0).value)) 
                res.lastname.add(tr, null, com.pullenti.morph.MorphGender.MASCULINE, false);
            res.firstname = new PersonMorphCollection();
            String pref = (cou == 2 ? pits.get(1).value : "");
            if (pits.get(cou).isAsianItem(false)) {
                res.firstname.add(pref + pits.get(cou).value, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                res.firstname.add(pref + pits.get(cou).value, null, com.pullenti.morph.MorphGender.FEMINIE, false);
                if (pref.length() > 0) {
                    res.firstname.add(pref + "-" + pits.get(cou).value, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                    res.firstname.add(pref + "-" + pits.get(cou).value, null, com.pullenti.morph.MorphGender.FEMINIE, false);
                }
            }
            else {
                String v = com.pullenti.ner.person.PersonReferent._DelSurnameEnd(pits.get(cou).value);
                if (com.pullenti.unisharp.Utils.stringsEq(pref, "") && v.indexOf('-') > 0) {
                    pref = v.substring(0, 0 + v.indexOf('-'));
                    v = v.substring(v.indexOf('-') + 1);
                }
                res.firstname.add(pref + v, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                if (pref.length() > 0) 
                    res.firstname.add(pref + "-" + v, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                String ss = pits.get(cou).getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                if (com.pullenti.unisharp.Utils.stringsNe(ss, v) && ss.length() <= v.length()) {
                    res.firstname.add(pref + ss, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                    if (pref.length() > 0) 
                        res.firstname.add(pref + "-" + ss, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                }
                inf.setGender(com.pullenti.morph.MorphGender.MASCULINE);
            }
        }
        else {
            if (inf.getGender() == com.pullenti.morph.MorphGender.MASCULINE) 
                manageLastname(res, pits.get(cou), inf);
            else {
                res.lastname = new PersonMorphCollection();
                if (pits.get(cou).isAsianItem(false)) {
                    res.lastname.add(pits.get(cou).value, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                    res.lastname.add(pits.get(cou).value, null, com.pullenti.morph.MorphGender.FEMINIE, false);
                }
                else {
                    String v = com.pullenti.ner.person.PersonReferent._DelSurnameEnd(pits.get(cou).value);
                    res.lastname.add(v, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                    String ss = pits.get(cou).getEndToken().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (com.pullenti.unisharp.Utils.stringsNe(ss, v) && ss.length() <= v.length()) 
                        res.lastname.add(ss, null, com.pullenti.morph.MorphGender.MASCULINE, false);
                    inf.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                }
            }
            if (cou == 2) {
                res.coef = 2.0F;
                if ((res.getWhitespacesAfterCount() < 2) && pits.size() > 3) 
                    res.coef--;
                res.lastname.addPrefixStr((pits.get(0).value + " " + pits.get(1).value + " "));
            }
            else {
                res.coef = 1.0F;
                res.lastname.addPrefixStr(pits.get(0).value + " ");
            }
            for (int i = 0; i < pits.size(); i++) {
                if (pits.get(i).isInDictionary) {
                    com.pullenti.morph.MorphClass mc = pits.get(i).getBeginToken().getMorphClassInDictionary();
                    if ((mc.isConjunction() || mc.isPronoun() || mc.isPreposition()) || mc.isPersonalPronoun()) 
                        res.coef -= 0.5F;
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(pits.get(0).value, pits.get(1).value)) 
            res.coef -= 0.5F;
        if (cou == 2) {
            if (com.pullenti.unisharp.Utils.stringsEq(pits.get(0).value, pits.get(2).value)) 
                res.coef -= 0.5F;
            if (com.pullenti.unisharp.Utils.stringsEq(pits.get(1).value, pits.get(2).value)) 
                res.coef -= 0.5F;
        }
        if (!pits.get(cou).isWhitespaceAfter()) {
            com.pullenti.ner.Token t = pits.get(cou).getEndToken().getNext();
            if (t != null && t.isHiphen()) 
                res.coef -= 0.5F;
            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, false, null, false)) 
                res.coef -= 0.5F;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(pits.get(0).getBeginToken().getPrevious(), false, false)) 
            res.coef -= 0.5F;
        return res;
    }

    public static PersonIdentityToken tryAttachIdentity(java.util.ArrayList<PersonItemToken> pits, com.pullenti.morph.MorphBaseInfo inf) {
        if (pits.size() == 1) {
            if (pits.get(0).typ != PersonItemToken.ItemType.REFERENT) 
                return null;
        }
        else {
            if (pits.size() != 2 && pits.size() != 3) 
                return null;
            for (PersonItemToken p : pits) {
                if (p.typ != PersonItemToken.ItemType.VALUE) 
                    return null;
                if (!p.chars.equals(pits.get(0).chars)) 
                    return null;
            }
        }
        com.pullenti.ner.TextToken begin = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(pits.get(0).getBeginToken(), com.pullenti.ner.TextToken.class);
        com.pullenti.ner.TextToken end = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(pits.get(pits.size() - 1).getEndToken(), com.pullenti.ner.TextToken.class);
        if (begin == null || end == null) 
            return null;
        PersonIdentityToken res = new PersonIdentityToken(begin, end);
        res.lastname = new PersonMorphCollection();
        String s = com.pullenti.ner.core.MiscHelper.getTextValue(begin, end, com.pullenti.ner.core.GetTextAttr.NO);
        if (s.length() > 100) 
            return null;
        StringBuilder tmp = new StringBuilder();
        for (com.pullenti.ner.Token t = begin; t != null && t.getPrevious() != end; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                continue;
            if (tt.isHiphen()) {
                tmp.append('-');
                continue;
            }
            if (tmp.length() > 0) {
                if (tmp.charAt(tmp.length() - 1) != '-') 
                    tmp.append(' ');
            }
            if (tt.getLengthChar() < 3) {
                tmp.append(tt.term);
                continue;
            }
            String sss = tt.term;
            for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                if (wf != null && wf.normalCase != null && (wf.normalCase.length() < sss.length())) 
                    sss = wf.normalCase;
            }
            tmp.append(sss);
        }
        String ss = tmp.toString();
        if (inf.getCase().isNominative()) {
            res.lastname.add(s, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
            if (com.pullenti.unisharp.Utils.stringsNe(s, ss)) 
                res.lastname.add(ss, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        }
        else {
            if (com.pullenti.unisharp.Utils.stringsNe(s, ss)) 
                res.lastname.add(ss, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
            res.lastname.add(s, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        }
        for (PersonItemToken p : pits) {
            if (p != pits.get(0)) {
                if (p.isNewlineBefore()) 
                    res.coef -= (1.0F);
                else if (p.getWhitespacesBeforeCount() > 1) 
                    res.coef -= ((float)0.5);
            }
            res.coef += ((float)0.5);
            if (p.getLengthChar() > 4) {
                if (p.isInDictionary) 
                    res.coef -= ((float)1.5);
                if (p.lastname != null && ((p.lastname.isInDictionary || p.lastname.isInOntology))) 
                    res.coef -= (1.0F);
                if (p.firstname != null && p.firstname.isInDictionary) 
                    res.coef -= (1.0F);
                if (p.middlename != null) 
                    res.coef -= (1.0F);
                if (p.chars.isAllUpper()) 
                    res.coef -= ((float)0.5);
            }
            else if (p.chars.isAllUpper()) 
                res.coef -= (1.0F);
        }
        if (pits.size() == 2 && pits.get(1).lastname != null && ((pits.get(1).lastname.isLastnameHasStdTail || pits.get(1).lastname.isInDictionary))) 
            res.coef -= 0.5F;
        return res;
    }

    private static PersonIdentityToken tryAttachGlobal(java.util.ArrayList<PersonItemToken> pits, int ind, com.pullenti.morph.MorphBaseInfo inf) {
        if (ind > 0 || pits.get(0).typ != PersonItemToken.ItemType.VALUE) 
            return null;
        if ((pits.size() == 4 && com.pullenti.unisharp.Utils.stringsEq(pits.get(0).value, "АУН") && com.pullenti.unisharp.Utils.stringsEq(pits.get(1).value, "САН")) && com.pullenti.unisharp.Utils.stringsEq(pits.get(2).value, "СУ") && com.pullenti.unisharp.Utils.stringsEq(pits.get(3).value, "ЧЖИ")) {
            PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(3).getEndToken());
            res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
            res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_IDENTITY, "АУН САН СУ ЧЖИ", false, 0);
            res.ontologyPerson.setFemale(true);
            res.coef = 10.0F;
            return res;
        }
        if (pits.size() == 2 && pits.get(0).firstname != null && pits.get(0).firstname.isInDictionary) {
            if (pits.get(0).getBeginToken().isValue("ИВАН", null) && pits.get(1).getBeginToken().isValue("ГРОЗНЫЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, "ИВАН", false, 0);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, "ГРОЗНЫЙ", false, 0);
                res.ontologyPerson.setMale(true);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(0).getBeginToken().isValue("ЮРИЙ", null) && pits.get(1).getBeginToken().isValue("ДОЛГОРУКИЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, "ЮРИЙ", false, 0);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, "ДОЛГОРУКИЙ", false, 0);
                res.ontologyPerson.setMale(true);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(1).getBeginToken().isValue("ВЕЛИКИЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(1).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(0), pits.get(1).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "ВЕЛИКИЙ" : "ВЕЛИКАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(1).getBeginToken().isValue("СВЯТОЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(1).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(0), pits.get(1).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "СВЯТОЙ" : "СВЯТАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(1).getBeginToken().isValue("ПРЕПОДОБНЫЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(1).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(0), pits.get(1).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "ПРЕПОДОБНЫЙ" : "ПРЕПОДОБНАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(1).getBeginToken().isValue("БЛАЖЕННЫЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(0).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(1).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(0), pits.get(1).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "БЛАЖЕННЫЙ" : "БЛАЖЕННАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
        }
        if (pits.size() == 2 && pits.get(1).firstname != null && pits.get(1).firstname.isInDictionary) {
            if (pits.get(0).getBeginToken().isValue("СВЯТОЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || pits.get(0).getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(0).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(1), pits.get(0).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "СВЯТОЙ" : "СВЯТАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(0).getBeginToken().isValue("ПРЕПОДОБНЫЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(0).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(1), pits.get(0).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "ПРЕПОДОБНЫЙ" : "ПРЕПОДОБНАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
            if (pits.get(0).getBeginToken().isValue("БЛАЖЕННЫЙ", null)) {
                PersonIdentityToken res = new PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(1).getEndToken());
                res.ontologyPerson = new com.pullenti.ner.person.PersonReferent();
                if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.ontologyPerson.setFemale(true);
                else if (pits.get(1).firstname.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || ((short)((pits.get(0).getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    res.ontologyPerson.setMale(true);
                else 
                    return null;
                manageFirstname(res, pits.get(1), pits.get(0).getMorph());
                res.ontologyPerson.addFioIdentity(null, res.firstname, null);
                res.ontologyPerson.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, (res.ontologyPerson.isMale() ? "БЛАЖЕННЫЙ" : "БЛАЖЕННАЯ"), false, 0);
                res.coef = 10.0F;
                return res;
            }
        }
        return null;
    }

    private static com.pullenti.ner.MorphCollection accordMorph(com.pullenti.morph.MorphBaseInfo inf, PersonItemToken.MorphPersonItem p1, PersonItemToken.MorphPersonItem p2, PersonItemToken.MorphPersonItem p3, com.pullenti.ner.Token _next) {
        com.pullenti.ner.MorphCollection res = new com.pullenti.ner.MorphCollection(null);
        java.util.ArrayList<PersonItemToken.MorphPersonItem> pp = new java.util.ArrayList<PersonItemToken.MorphPersonItem>();
        if (p1 != null) 
            pp.add(p1);
        if (p2 != null) 
            pp.add(p2);
        if (p3 != null) 
            pp.add(p3);
        if (pp.size() == 0) 
            return res;
        if (inf != null && p1 != null && ((p1.isLastnameHasStdTail || p1.isInDictionary))) {
            if (((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), p1.getMorph().getCase()))).isUndefined()) 
                inf = null;
        }
        if (inf != null && p2 != null && p2.isInDictionary) {
            if (((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), p2.getMorph().getCase()))).isUndefined()) 
                inf = null;
        }
        for (int i = 0; i < 2; i++) {
            com.pullenti.morph.MorphGender g = (i == 0 ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE);
            if (inf != null && inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && ((short)((inf.getGender().value()) & (g.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                continue;
            com.pullenti.morph.MorphCase cas = com.pullenti.morph.MorphCase.ALLCASES;
            for (PersonItemToken.MorphPersonItem p : pp) {
                com.pullenti.morph.MorphCase ca = new com.pullenti.morph.MorphCase();
                for (PersonItemToken.MorphPersonItemVariant v : p.vars) {
                    if (v.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                        if (((short)((v.getGender().value()) & (g.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            continue;
                    }
                    if (inf != null && !inf.getCase().isUndefined() && !v.getCase().isUndefined()) {
                        if (((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), v.getCase()))).isUndefined()) 
                            continue;
                    }
                    if (!v.getCase().isUndefined()) 
                        ca = com.pullenti.morph.MorphCase.ooBitor(ca, v.getCase());
                    else 
                        ca = com.pullenti.morph.MorphCase.ALLCASES;
                }
                cas = com.pullenti.morph.MorphCase.ooBitand(cas, ca);
            }
            if (!cas.isUndefined()) {
                if (inf != null && !inf.getCase().isUndefined() && !((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), cas))).isUndefined()) 
                    cas = com.pullenti.morph.MorphCase.ooBitand(cas, inf.getCase());
                res.addItem(com.pullenti.morph.MorphBaseInfo._new2708(g, cas));
            }
        }
        com.pullenti.morph.MorphGender verbGend = com.pullenti.morph.MorphGender.UNDEFINED;
        if ((_next != null && (_next instanceof com.pullenti.ner.TextToken) && _next.chars.isAllLower()) && _next.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB) && _next.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
            if (_next.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || _next.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                verbGend = _next.getMorph().getGender();
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(_next.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if ((npt != null && npt.getMorph().getCase().isNominative() && npt.getMorph().getGender() == verbGend) && npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                    verbGend = com.pullenti.morph.MorphGender.UNDEFINED;
            }
        }
        if (verbGend != com.pullenti.morph.MorphGender.UNDEFINED && res.getItemsCount() > 1) {
            int cou = 0;
            for (com.pullenti.morph.MorphBaseInfo it : res.getItems()) {
                if (it.getCase().isNominative() && it.getGender() == verbGend) 
                    cou++;
            }
            if (cou == 1) {
                for (int i = res.getItemsCount() - 1; i >= 0; i--) {
                    if (!res.getIndexerItem(i).getCase().isNominative() || res.getIndexerItem(i).getGender() != verbGend) 
                        res.removeItem(i);
                }
            }
        }
        return res;
    }

    private static boolean isAccords(PersonItemToken.MorphPersonItem mt, com.pullenti.morph.MorphBaseInfo inf) {
        if (inf == null) 
            return true;
        if (mt.vars.size() == 0) 
            return true;
        for (PersonItemToken.MorphPersonItemVariant wf : mt.vars) {
            boolean ok = true;
            if (!inf.getCase().isUndefined() && !wf.getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), inf.getCase()))).isUndefined()) 
                    ok = false;
            }
            if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (((short)((inf.getGender().value()) & (wf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    ok = false;
            }
            if (ok) 
                return true;
        }
        return false;
    }

    private static boolean isBothSurnames(PersonItemToken p1, PersonItemToken p2) {
        if (p1 == null || p2 == null) 
            return false;
        if (p1.lastname == null || p2.lastname == null) 
            return false;
        if (!p1.lastname.isInDictionary && !p1.lastname.isInOntology && !p1.lastname.isLastnameHasStdTail) 
            return false;
        if (p1.firstname != null || p2.middlename != null) 
            return false;
        if (!p2.lastname.isInDictionary && !p2.lastname.isInOntology && !p2.lastname.isLastnameHasStdTail) 
            return false;
        if (p2.firstname != null || p2.middlename != null) 
            return false;
        if (!(p1.getEndToken() instanceof com.pullenti.ner.TextToken) || !(p2.getEndToken() instanceof com.pullenti.ner.TextToken)) 
            return false;
        String v1 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(p1.getEndToken(), com.pullenti.ner.TextToken.class)).term;
        String v2 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(p2.getEndToken(), com.pullenti.ner.TextToken.class)).term;
        if (v1.charAt(v1.length() - 1) == v2.charAt(v2.length() - 1)) 
            return false;
        return true;
    }

    private static String getValue(PersonItemToken.MorphPersonItem mt, com.pullenti.morph.MorphBaseInfo inf) {
        for (PersonItemToken.MorphPersonItemVariant wf : mt.vars) {
            if (inf != null) {
                if (!inf.getCase().isUndefined() && !wf.getCase().isUndefined()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), inf.getCase()))).isUndefined()) 
                        continue;
                }
                if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (((short)((inf.getGender().value()) & (wf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                }
            }
            return wf.value;
        }
        return mt.term;
    }

    private static void setValue2(PersonMorphCollection res, PersonItemToken.MorphPersonItem mt, com.pullenti.morph.MorphBaseInfo inf) {
        boolean ok = false;
        for (PersonItemToken.MorphPersonItemVariant wf : mt.vars) {
            if (inf != null) {
                if (!inf.getCase().isUndefined() && !wf.getCase().isUndefined()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), inf.getCase()))).isUndefined()) 
                        continue;
                }
                if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (((short)((inf.getGender().value()) & (wf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                }
                ok = true;
            }
            res.add(wf.value, wf.shortValue, wf.getGender(), false);
        }
        if (res.getValues().size() == 0) {
            if ((inf != null && !inf.getCase().isUndefined() && mt.vars.size() > 0) && mt.isLastnameHasStdTail) {
                for (PersonItemToken.MorphPersonItemVariant wf : mt.vars) {
                    res.add(wf.value, wf.shortValue, wf.getGender(), false);
                }
            }
            res.add(mt.term, null, inf.getGender(), false);
        }
    }

    private static void setValue(PersonMorphCollection res, com.pullenti.ner.Token t, com.pullenti.morph.MorphBaseInfo inf) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null && (t instanceof com.pullenti.ner.MetaToken) && ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken() == ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getEndToken()) 
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken(), com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isVerb()) 
                continue;
            if (wf.containsAttr("к.ф.", null)) 
                continue;
            if (inf != null && inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (((short)((wf.getGender().value()) & (inf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    continue;
            }
            if (inf != null && !inf.getCase().isUndefined() && !wf.getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), inf.getCase()))).isUndefined()) 
                    continue;
            }
            String str = (t.chars.isLatinLetter() ? tt.term : ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase);
            res.add(str, null, wf.getGender(), false);
        }
        res.add(tt.term, null, (inf == null ? com.pullenti.morph.MorphGender.UNDEFINED : inf.getGender()), false);
    }

    public static boolean correctXFML(java.util.ArrayList<PersonIdentityToken> pli0, java.util.ArrayList<PersonIdentityToken> pli1, java.util.ArrayList<PersonAttrToken> attrs) {
        PersonIdentityToken p0 = null;
        PersonIdentityToken p1 = null;
        for (PersonIdentityToken p : pli0) {
            if (p.typ == FioTemplateType.SURNAMENAMESECNAME) {
                p0 = p;
                break;
            }
        }
        for (PersonIdentityToken p : pli1) {
            if (p.typ == FioTemplateType.NAMESECNAMESURNAME) {
                p1 = p;
                break;
            }
        }
        if (p0 == null || p1 == null) {
            for (PersonIdentityToken p : pli0) {
                if (p.typ == FioTemplateType.SURNAMENAME) {
                    p0 = p;
                    break;
                }
            }
            for (PersonIdentityToken p : pli1) {
                if (p.typ == FioTemplateType.NAMESURNAME) {
                    p1 = p;
                    break;
                }
            }
        }
        if (p0 == null || p1 == null) 
            return false;
        if (p1.coef > p0.coef) 
            return false;
        for (com.pullenti.ner.Token tt = p1.getBeginToken(); tt != p1.getEndToken(); tt = tt.getNext()) {
            if (tt.isNewlineAfter()) 
                return false;
        }
        if (!p1.getEndToken().isNewlineAfter()) {
            if (PersonItemToken.tryAttach(p1.getEndToken().getNext(), PersonItemToken.ParseAttr.NO, null) != null) 
                return false;
        }
        if (p0.lastname == null || p1.lastname == null) 
            return false;
        if (p1.lastname.getHasLastnameStandardTail()) {
            if (!p0.lastname.getHasLastnameStandardTail()) {
                p1.coef = p0.coef + ((float)0.1);
                return true;
            }
        }
        if (attrs == null || attrs.size() == 0) {
            if (!p1.lastname.getHasLastnameStandardTail() && p0.lastname.getHasLastnameStandardTail()) 
                return false;
        }
        com.pullenti.ner.Token t = p1.getEndToken().getNext();
        if (t != null && !t.chars.isCapitalUpper() && !t.chars.isAllUpper()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(p1.getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getEndToken() != npt.getBeginToken()) 
                return false;
            com.pullenti.morph.MorphClass cl1 = p0.getBeginToken().getMorphClassInDictionary();
            com.pullenti.morph.MorphClass cl2 = p1.getEndToken().getMorphClassInDictionary();
            if (cl2.isNoun() && !cl1.isNoun()) 
                return false;
            p1.coef = p0.coef + ((float)0.1);
            return true;
        }
        return false;
    }

    public static PersonIdentityToken checkLatinAfter(PersonIdentityToken pit) {
        if (pit == null) 
            return null;
        com.pullenti.ner.Token t = pit.getEndToken().getNext();
        if (t == null || !t.isChar('(')) 
            return null;
        t = t.getNext();
        PersonItemToken p1 = PersonItemToken.tryAttachLatin(t);
        if (p1 == null) 
            return null;
        PersonItemToken p2 = PersonItemToken.tryAttachLatin(p1.getEndToken().getNext());
        if (p2 == null) 
            return null;
        if (p2.getEndToken().getNext() == null) 
            return null;
        PersonItemToken p3 = null;
        com.pullenti.ner.Token et = p2.getEndToken().getNext();
        if (p2.getEndToken().getNext().isChar(')')) {
        }
        else {
            p3 = PersonItemToken.tryAttachLatin(et);
            if (p3 == null) 
                return null;
            et = p3.getEndToken().getNext();
            if (et == null || !et.isChar(')')) 
                return null;
        }
        PersonItemToken sur = null;
        PersonItemToken nam = null;
        PersonItemToken sec = null;
        if (pit.typ == FioTemplateType.NAMESURNAME && pit.firstname != null && pit.lastname != null) {
            int eq = 0;
            if (p1.typ == PersonItemToken.ItemType.VALUE) {
                if (pit.firstname.checkLatinVariant(p1.value)) 
                    eq++;
                nam = p1;
                if (p2.typ == PersonItemToken.ItemType.VALUE && p3 == null) {
                    sur = p2;
                    if (pit.lastname.checkLatinVariant(p2.value)) 
                        eq++;
                }
                else if (p2.typ == PersonItemToken.ItemType.INITIAL && p3 != null) {
                    if (pit.lastname.checkLatinVariant(p3.value)) 
                        eq++;
                    sur = p3;
                }
            }
            if (eq == 0) 
                return null;
        }
        else if ((pit.typ == FioTemplateType.NAMESECNAMESURNAME && pit.firstname != null && pit.middlename != null) && pit.lastname != null && p3 != null) {
            int eq = 0;
            if (p1.typ == PersonItemToken.ItemType.VALUE) {
                if (pit.firstname.checkLatinVariant(p1.value)) 
                    eq++;
                nam = p1;
                if (p2.typ == PersonItemToken.ItemType.VALUE) {
                    sec = p2;
                    if (pit.middlename instanceof PersonMorphCollection) {
                        if (((PersonMorphCollection)com.pullenti.unisharp.Utils.cast(pit.middlename, PersonMorphCollection.class)).checkLatinVariant(p2.value)) 
                            eq++;
                    }
                }
                if (p3.typ == PersonItemToken.ItemType.VALUE) {
                    sur = p3;
                    if (pit.lastname.checkLatinVariant(p3.value)) 
                        eq++;
                }
            }
            if (eq == 0) 
                return null;
        }
        if (nam == null || sur == null) 
            return null;
        PersonIdentityToken res = _new2691(t, et, pit.typ);
        res.lastname = new PersonMorphCollection();
        res.lastname.add(sur.value, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.firstname = new PersonMorphCollection();
        res.firstname.add(nam.value, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (sec != null) {
            res.middlename = new PersonMorphCollection();
            ((PersonMorphCollection)com.pullenti.unisharp.Utils.cast(res.middlename, PersonMorphCollection.class)).add(sec.value, null, com.pullenti.morph.MorphGender.UNDEFINED, false);
        }
        return res;
    }

    public static PersonIdentityToken _new2690(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, float _arg3, FioTemplateType _arg4) {
        PersonIdentityToken res = new PersonIdentityToken(_arg1, _arg2);
        res.coef = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonIdentityToken _new2691(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, FioTemplateType _arg3) {
        PersonIdentityToken res = new PersonIdentityToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public PersonIdentityToken() {
        super();
    }
}
