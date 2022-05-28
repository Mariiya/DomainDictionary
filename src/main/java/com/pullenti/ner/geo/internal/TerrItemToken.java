/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class TerrItemToken extends com.pullenti.ner.MetaToken {

    public TerrItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public com.pullenti.ner.core.IntOntologyItem ontoItem;

    public com.pullenti.ner.core.IntOntologyItem ontoItem2;

    public TerrTermin terminItem;

    public TerrTermin terminItem2;

    public boolean isAdjective;

    public boolean isDistrictName;

    public com.pullenti.ner.ReferentToken adjectiveRef;

    public boolean canBeCity;

    public boolean canBeSurname;

    public boolean isAdjInDictionary;

    public boolean isGeoInDictionary;

    public boolean isDoubt;

    public boolean isCityRegion() {
        if (terminItem == null) 
            return false;
        return ((terminItem.getCanonicText().indexOf("ГОРОДС") >= 0) || (terminItem.getCanonicText().indexOf("МІСЬК") >= 0) || (terminItem.getCanonicText().indexOf("МУНИЦИПАЛ") >= 0)) || (terminItem.getCanonicText().indexOf("МУНІЦИПАЛ") >= 0) || com.pullenti.unisharp.Utils.stringsEq(terminItem.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ");
    }


    public String additionalTyp;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (ontoItem != null) 
            res.append(ontoItem.getCanonicText()).append(" ");
        else if (terminItem != null) 
            res.append(terminItem.getCanonicText()).append(" ");
        else 
            res.append(super.toString()).append(" ");
        if (adjectiveRef != null) 
            res.append(" (Adj: ").append(adjectiveRef.referent.toString()).append(")");
        return res.toString().trim();
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.tRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            TerrItemToken ter = tryParse(t, null, ad);
            if (ter != null) {
                if (d == null) 
                    d = new GeoTokenData(t);
                d.terr = ter;
            }
        }
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            if (d == null || d.terr == null || d.terr.terminItem == null) 
                continue;
            com.pullenti.ner.Token tt = d.terr.getEndToken().getNext();
            if (tt == null) 
                continue;
            GeoTokenData dd = (GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, GeoTokenData.class);
            TerrItemToken ter = tryParse(tt, d.terr, ad);
            if (ter == null) 
                continue;
            if (dd == null) 
                dd = new GeoTokenData(tt);
            if (dd.terr == null || (dd.terr.getEndChar() < ter.getEndChar())) 
                dd.terr = ter;
        }
        ad.tRegime = true;
    }

    public static TerrItemToken tryParse(com.pullenti.ner.Token t, TerrItemToken prev, GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (ad == null) 
            ad = (GeoAnalyzerData)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.geo.GeoAnalyzer.getData(t), GeoAnalyzerData.class);
        if (ad == null) 
            return null;
        GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
        if (SPEEDREGIME && ((ad.tRegime || ad.allRegime)) && ((t.getLengthChar() != 2 || !t.chars.isAllUpper()))) {
            if (d != null) 
                return d.terr;
            if (t.tag == null) 
                return null;
        }
        if (ad.tLevel > 1) 
            return null;
        ad.tLevel++;
        TerrItemToken res = _TryParse(t, prev, false);
        ad.tLevel--;
        if (res == null) {
            if (t.chars.isAllUpper() && t.getLengthChar() == 2 && (t instanceof com.pullenti.ner.TextToken)) {
                String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (((com.pullenti.unisharp.Utils.stringsEq(term, "РБ") || com.pullenti.unisharp.Utils.stringsEq(term, "РК") || com.pullenti.unisharp.Utils.stringsEq(term, "TC")) || com.pullenti.unisharp.Utils.stringsEq(term, "ТС") || com.pullenti.unisharp.Utils.stringsEq(term, "РТ")) || com.pullenti.unisharp.Utils.stringsEq(term, "УР") || com.pullenti.unisharp.Utils.stringsEq(term, "РД")) {
                    if ((com.pullenti.unisharp.Utils.stringsEq(term, "РБ") && (t.getPrevious() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesBeforeCount() < 3)) && !t.getPrevious().chars.isAllLower() && t.getPrevious().getMorph()._getClass().isAdjective()) 
                        return null;
                    for (com.pullenti.ner.core.IntOntologyItem it : ad.localOntology.getItems()) {
                        if (it.referent instanceof com.pullenti.ner.geo.GeoReferent) {
                            String alph2 = ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(it.referent, com.pullenti.ner.geo.GeoReferent.class)).getAlpha2();
                            if (((com.pullenti.unisharp.Utils.stringsEq(alph2, "BY") && com.pullenti.unisharp.Utils.stringsEq(term, "РБ"))) || ((com.pullenti.unisharp.Utils.stringsEq(alph2, "KZ") && com.pullenti.unisharp.Utils.stringsEq(term, "РК")))) 
                                return _new1321(t, t, it);
                            if (com.pullenti.unisharp.Utils.stringsEq(term, "РТ")) {
                                if (it.referent.findSlot(null, "ТАТАРСТАН", true) != null) 
                                    return _new1321(t, t, it);
                            }
                            if (com.pullenti.unisharp.Utils.stringsEq(term, "РД")) {
                                if (it.referent.findSlot(null, "ДАГЕСТАН", true) != null) 
                                    return _new1321(t, t, it);
                            }
                            if (com.pullenti.unisharp.Utils.stringsEq(term, "РБ")) {
                                if (it.referent.findSlot(null, "БАШКИРИЯ", true) != null) 
                                    return _new1321(t, t, it);
                            }
                        }
                    }
                    boolean ok = false;
                    if ((t.getWhitespacesBeforeCount() < 2) && (t.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                        String term2 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.TextToken.class)).term;
                        if ((t.getPrevious().isValue("КОДЕКС", null) || t.getPrevious().isValue("ЗАКОН", null) || com.pullenti.unisharp.Utils.stringsEq(term2, "КОАП")) || com.pullenti.unisharp.Utils.stringsEq(term2, "ПДД") || com.pullenti.unisharp.Utils.stringsEq(term2, "МЮ")) 
                            ok = true;
                        else if ((t.getPrevious().chars.isAllUpper() && t.getPrevious().getLengthChar() > 1 && (t.getPrevious().getLengthChar() < 4)) && term2.endsWith("К")) 
                            ok = true;
                        else if (com.pullenti.unisharp.Utils.stringsEq(term, "РТ") || com.pullenti.unisharp.Utils.stringsEq(term, "УР") || com.pullenti.unisharp.Utils.stringsEq(term, "РД")) {
                            com.pullenti.ner.Token tt = t.getPrevious();
                            if (tt != null && tt.isComma()) 
                                tt = tt.getPrevious();
                            if (tt != null) {
                                if ((tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class)).getAlpha2(), "RU")) 
                                    ok = true;
                                else if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getLengthChar() == 6 && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                                    ok = true;
                            }
                        }
                        if (!ok) {
                            int cou = 0;
                            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (cou < 4); tt = tt.getPrevious(),cou++) {
                                OrgItemToken _org = OrgItemToken.tryParse(tt, null);
                                if (_org != null) {
                                    ok = true;
                                    break;
                                }
                                com.pullenti.ner.ReferentToken kk = tt.kit.processReferent("PERSONPROPERTY", tt, null);
                                if (kk != null) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                    }
                    else if (((t.getWhitespacesBeforeCount() < 2) && (t.getPrevious() instanceof com.pullenti.ner.NumberToken) && t.getPrevious().getLengthChar() == 6) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                        ok = true;
                    if (ok) {
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "РК") && m_Kazahstan != null) 
                            return _new1321(t, t, m_Kazahstan);
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "РТ") && m_Tatarstan != null) 
                            return _new1321(t, t, m_Tatarstan);
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "РД") && m_Dagestan != null) 
                            return _new1321(t, t, m_Dagestan);
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "УР") && m_Udmurtia != null) 
                            return _new1321(t, t, m_Udmurtia);
                        if (com.pullenti.unisharp.Utils.stringsEq(term, "РБ") && m_Belorussia != null && ad.step > 0) 
                            return _new1321(t, t, m_Belorussia);
                        if (((com.pullenti.unisharp.Utils.stringsEq(term, "ТС") || com.pullenti.unisharp.Utils.stringsEq(term, "TC"))) && m_TamogSous != null) 
                            return _new1321(t, t, m_TamogSous);
                    }
                }
            }
            if (((t instanceof com.pullenti.ner.TextToken) && ((t.isValue("Р", null) || t.isValue("P", null))) && t.getNext() != null) && t.getNext().isChar('.') && !t.getNext().isNewlineAfter()) {
                res = tryParse(t.getNext().getNext(), null, ad);
                if (res != null && res.ontoItem != null) {
                    String str = res.ontoItem.toString().toUpperCase();
                    if ((str.indexOf("РЕСПУБЛИКА") >= 0)) {
                        res.setBeginToken(t);
                        res.isDoubt = false;
                        return res;
                    }
                }
            }
            return _tryParseDistrictName(t, 0);
        }
        if ((res.getBeginToken().getLengthChar() == 1 && res.getBeginToken().chars.isAllUpper() && res.getBeginToken().getNext() != null) && res.getBeginToken().getNext().isChar('.')) 
            return null;
        if ((res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.') && res.getEndToken().getNext().getNext() != null) && res.getEndToken().getNext().getNext().isComma()) 
            res.setEndToken(res.getEndToken().getNext());
        if (res.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(res.terminItem.getCanonicText(), "ОКРУГ")) {
            if (t.getPrevious() != null && ((t.getPrevious().isValue("ГОРОДСКОЙ", null) || t.getPrevious().isValue("МІСЬКИЙ", null)))) 
                return null;
        }
        if (res.ontoItem != null) {
            com.pullenti.ner.core.IntOntologyToken cit1 = CityItemToken.checkOntoItem(res.getBeginToken());
            if (cit1 != null && cit1.item.miscAttr != null) {
                if (cit1.getEndToken().isValue("CITY", null)) 
                    return null;
                if (cit1.getEndToken() == res.getEndToken()) {
                    res.canBeCity = true;
                    if (cit1.getEndToken().getNext() != null && cit1.getEndToken().getNext().isValue("CITY", null)) 
                        return null;
                }
            }
            CityItemToken cit = CityItemToken.tryParseBack(res.getBeginToken().getPrevious(), true);
            if (cit != null && cit.typ == CityItemToken.ItemType.NOUN && ((res.isAdjective || (cit.getWhitespacesAfterCount() < 1)))) 
                res.canBeCity = true;
        }
        if (res.terminItem != null) {
            if (((com.pullenti.unisharp.Utils.stringsEq(res.terminItem.getCanonicText(), "МУНИЦИПАЛЬНЫЙ ОКРУГ") || com.pullenti.unisharp.Utils.stringsEq(res.terminItem.getCanonicText(), "ГОРОДСКОЙ ОКРУГ"))) && (res.getWhitespacesAfterCount() < 3)) {
                TerrItemToken next0 = tryParse(res.getEndToken().getNext(), null, null);
                if (next0 != null && next0.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(next0.terminItem.acronym, "ЗАТО")) {
                    res.setEndToken(next0.getEndToken());
                    res.terminItem2 = next0.terminItem;
                }
                com.pullenti.ner.core.IntOntologyToken _next = CityItemToken.checkKeyword(res.getEndToken().getNext());
                if (_next != null) {
                    res.setEndToken(_next.getEndToken());
                    res.additionalTyp = _next.termin.getCanonicText();
                }
            }
            res.isDoubt = res.terminItem.isDoubt;
            if (!res.terminItem.isRegion) {
                if (res.terminItem.isMoscowRegion && res.getBeginToken() == res.getEndToken()) 
                    res.isDoubt = true;
                else if (com.pullenti.unisharp.Utils.stringsEq(res.terminItem.acronym, "МО") && res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                    if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isValue("ВЕТЕРАН", null)) 
                        return null;
                    res.isDoubt = true;
                    if (res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                        if (res.getBeginToken().getPrevious() == null || res.getBeginToken().getPrevious().isCharOf(",") || res.getBeginToken().isNewlineBefore()) {
                            if (res.getEndToken().getNext() == null || res.getEndToken().getNext().isCharOf(",") || res.isNewlineAfter()) {
                                res.terminItem = null;
                                res.ontoItem = m_MosRegRU;
                            }
                        }
                        com.pullenti.ner.Token tt = res.getEndToken().getNext();
                        if (tt != null && tt.isComma()) 
                            tt = tt.getNext();
                        CityItemToken cit = CityItemToken.tryParse(tt, null, false, null);
                        boolean isReg = false;
                        if (cit != null && cit.typ == CityItemToken.ItemType.NOUN) 
                            isReg = true;
                        else {
                            tt = t.getPrevious();
                            if (tt != null && tt.isComma()) 
                                tt = tt.getPrevious();
                            cit = CityItemToken.tryParseBack(tt, false);
                            if (cit != null && cit.typ == CityItemToken.ItemType.CITY) 
                                isReg = true;
                            else if (cit != null && cit.typ == CityItemToken.ItemType.PROPERNAME) {
                                cit = CityItemToken.tryParseBack(cit.getBeginToken().getPrevious(), true);
                                if (cit != null && cit.typ == CityItemToken.ItemType.NOUN) 
                                    isReg = true;
                            }
                        }
                        if (isReg) {
                            res.terminItem = null;
                            res.isDoubt = false;
                            res.ontoItem = m_MosRegRU;
                        }
                    }
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(res.terminItem.acronym, "ЛО") && res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                    res.isDoubt = true;
                    if (res.getBeginToken().getPrevious() == null || res.getBeginToken().getPrevious().isCommaAnd() || res.getBeginToken().isNewlineBefore()) {
                        res.terminItem = null;
                        res.ontoItem = m_LenRegRU;
                    }
                    else {
                        com.pullenti.ner.Token tt = res.getEndToken().getNext();
                        if (tt != null && tt.isComma()) 
                            tt = tt.getNext();
                        CityItemToken cit = CityItemToken.tryParse(tt, null, false, null);
                        if (cit != null && cit.typ == CityItemToken.ItemType.NOUN) {
                            res.terminItem = null;
                            res.ontoItem = m_LenRegRU;
                        }
                    }
                }
                else if (!res.getMorph().getCase().isNominative() && !res.getMorph().getCase().isAccusative()) 
                    res.isDoubt = true;
                else if (res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (res.terminItem.isMoscowRegion && res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                    }
                    else 
                        res.isDoubt = true;
                }
            }
            if (((res.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(res.terminItem.getCanonicText(), "АО"))) || ((res.ontoItem == m_MosRegRU && res.getLengthChar() == 2 && res.isDoubt))) {
                com.pullenti.ner.Token tt = res.getEndToken().getNext();
                com.pullenti.ner.ReferentToken rt = res.kit.processReferent("ORGANIZATION", res.getBeginToken(), null);
                if (rt == null) 
                    rt = res.kit.processReferent("ORGANIZATION", res.getBeginToken().getNext(), null);
                if (rt != null) {
                    for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                            String ty = s.getValue().toString();
                            if (res.terminItem != null && com.pullenti.unisharp.Utils.stringsNe(ty, res.terminItem.getCanonicText())) 
                                return null;
                        }
                    }
                }
            }
        }
        if (res != null && res.getBeginToken() == res.getEndToken() && res.terminItem == null) {
            if (t instanceof com.pullenti.ner.TextToken) {
                String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(str, "ЧАДОВ") || com.pullenti.unisharp.Utils.stringsEq(str, "ТОГОВ")) 
                    return null;
            }
            if ((((t.getNext() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesAfterCount() < 2) && !t.getNext().chars.isAllLower()) && t.chars.equals(t.getNext().chars) && !t.chars.isLatinLetter()) && ((!t.getMorph().getCase().isGenitive() && !t.getMorph().getCase().isAccusative()))) {
                com.pullenti.morph.MorphClass mc = t.getNext().getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperSecname()) 
                    res.isDoubt = true;
            }
            if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesBeforeCount() < 2) && !t.getPrevious().chars.isAllLower()) {
                com.pullenti.morph.MorphClass mc = t.getPrevious().getMorphClassInDictionary();
                if (mc.isProperSurname()) 
                    res.isDoubt = true;
            }
            if ((t.getLengthChar() <= 2 && res.ontoItem != null && !t.isValue("РФ", null)) && !t.isValue("МО", null)) {
                res.isDoubt = true;
                com.pullenti.ner.Token tt = t.getNext();
                if (tt != null && ((tt.isCharOf(":") || tt.isHiphen()))) 
                    tt = tt.getNext();
                if (tt != null && tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "PHONE")) 
                    res.isDoubt = false;
                else if (t.getLengthChar() == 2 && t.chars.isAllUpper() && t.chars.isLatinLetter()) 
                    res.isDoubt = false;
            }
        }
        return res;
    }

    private static TerrItemToken _TryParse(com.pullenti.ner.Token t, TerrItemToken prev, boolean ignoreOnto) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = null;
        if (!ignoreOnto) {
            if (t.kit.ontology != null) 
                li = t.kit.ontology.attachToken(com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, t);
            if (li == null || li.size() == 0) 
                li = m_TerrOntology.tryAttach(t, null, false);
            else {
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li1 = m_TerrOntology.tryAttach(t, null, false);
                if (li1 != null && li1.size() > 0) {
                    if (li1.get(0).getLengthChar() > li.get(0).getLengthChar()) 
                        li = li1;
                }
            }
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (li != null) {
            for (int i = li.size() - 1; i >= 0; i--) {
                if (li.get(i).item != null) {
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).item.referent, com.pullenti.ner.geo.GeoReferent.class);
                    if (g == null) 
                        continue;
                    if (g.isCity() && !g.isRegion() && !g.isState()) 
                        li.remove(i);
                    else if (g.isState() && t.getLengthChar() == 2 && li.get(i).getLengthChar() == 2) {
                        if (!t.isWhitespaceBefore() && t.getPrevious() != null && t.getPrevious().isChar('.')) 
                            li.remove(i);
                        else if (t.getPrevious() != null && t.getPrevious().isValue("ДОМЕН", null)) 
                            li.remove(i);
                    }
                }
            }
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null && !(nt.termin.tag instanceof com.pullenti.ner.core.IntOntologyItem)) {
                    if (!com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false) || nt.getBeginToken() != nt.getEndToken()) {
                        TerrItemToken res0 = _new1331(nt.getBeginToken(), nt.getEndToken(), nt.item, nt.getMorph());
                        if (nt.getEndToken().getMorph()._getClass().isAdjective() && nt.getBeginToken() == nt.getEndToken()) {
                            if (nt.getBeginToken().getMorphClassInDictionary().isProperGeo()) {
                            }
                            else 
                                res0.isAdjective = true;
                        }
                        if (nt.getBeginToken() == nt.getEndToken() && nt.chars.isLatinLetter()) {
                            if (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nt.item.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) {
                            }
                            else if (nt.item.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "state", true) != null) {
                            }
                            else 
                                res0.isDoubt = true;
                        }
                        if (nt.getBeginToken() == nt.getEndToken()) {
                            for (com.pullenti.morph.MorphBaseInfo wf : nt.getBeginToken().getMorph().getItems()) {
                                com.pullenti.morph.MorphWordForm f = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                                if (!f.isInDictionary()) 
                                    continue;
                                if (((wf._getClass().isProperSurname() || wf._getClass().isProperName())) && f.isInDictionary()) 
                                    res0.canBeSurname = true;
                            }
                        }
                        if ((li.size() == 2 && nt == li.get(0) && li.get(1).item != null) && !(li.get(1).termin.tag instanceof com.pullenti.ner.core.IntOntologyItem)) 
                            res0.ontoItem2 = li.get(1).item;
                        return res0;
                    }
                }
            }
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null && (nt.termin.tag instanceof com.pullenti.ner.core.IntOntologyItem)) {
                    if (nt.getEndToken().getNext() == null || !nt.getEndToken().getNext().isHiphen()) {
                        TerrItemToken res1 = _new1332(nt.getBeginToken(), nt.getEndToken(), nt.item, true, nt.getMorph());
                        if ((li.size() == 2 && nt == li.get(0) && li.get(1).item != null) && (li.get(1).termin.tag instanceof com.pullenti.ner.core.IntOntologyItem)) 
                            res1.ontoItem2 = li.get(1).item;
                        if (t.kit.baseLanguage.isUa() && com.pullenti.unisharp.Utils.stringsEq(res1.ontoItem.getCanonicText(), "СУДАН") && t.isValue("СУД", null)) 
                            return null;
                        return res1;
                    }
                }
            }
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.termin != null && nt.item == null) {
                    if (nt.getEndToken().getNext() == null || !nt.getEndToken().getNext().isHiphen() || !((TerrTermin)com.pullenti.unisharp.Utils.cast(nt.termin, TerrTermin.class)).isAdjective) {
                        TerrItemToken res1 = _new1333(nt.getBeginToken(), nt.getEndToken(), (TerrTermin)com.pullenti.unisharp.Utils.cast(nt.termin, TerrTermin.class), ((TerrTermin)com.pullenti.unisharp.Utils.cast(nt.termin, TerrTermin.class)).isAdjective, nt.getMorph());
                        if (!res1.isAdjective) {
                            if (com.pullenti.unisharp.Utils.stringsEq(res1.terminItem.getCanonicText(), "РАЙОН")) {
                                if (t.getPrevious() != null) {
                                    if (t.getPrevious().isValue("МИКРО", null)) 
                                        return null;
                                    if (t.getPrevious().isCharOf("\\/.") && t.getPrevious().getPrevious() != null && t.getPrevious().getPrevious().isValue("М", null)) 
                                        return null;
                                }
                                if (!res1.getBeginToken().isValue(res1.terminItem.getCanonicText(), null)) {
                                    if (res1.getEndToken().getNext() != null && res1.getEndToken().getNext().isChar('.')) 
                                        res1.setEndToken(res1.getEndToken().getNext());
                                }
                            }
                            if (com.pullenti.unisharp.Utils.stringsEq(res1.terminItem.getCanonicText(), "РЕСПУБЛИКА") || com.pullenti.unisharp.Utils.stringsEq(res1.terminItem.getCanonicText(), "ШТАТ")) {
                                com.pullenti.ner.core.NounPhraseToken npt1 = MiscLocationHelper.tryParseNpt(res1.getBeginToken().getPrevious());
                                if (npt1 != null && npt1.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                                    TerrItemToken res2 = tryParse(res1.getEndToken().getNext(), null, null);
                                    if ((res2 != null && res2.ontoItem != null && res2.ontoItem.referent != null) && res2.ontoItem.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "республика", true) != null) {
                                    }
                                    else 
                                        return null;
                                }
                            }
                            if (com.pullenti.unisharp.Utils.stringsEq(res1.terminItem.getCanonicText(), "ГОСУДАРСТВО")) {
                                if (t.getPrevious() != null && t.getPrevious().isValue("СОЮЗНЫЙ", null)) 
                                    return null;
                            }
                            if (nt.getBeginToken() == nt.getEndToken() && nt.getBeginToken().isValue("ОПС", null)) {
                                if (!MiscLocationHelper.checkGeoObjectBefore(nt.getBeginToken(), false)) 
                                    return null;
                            }
                        }
                        return res1;
                    }
                }
            }
        }
        if (tt == null) 
            return null;
        if (!tt.chars.isCapitalUpper() && !tt.chars.isAllUpper()) 
            return null;
        if (((tt.getLengthChar() == 2 || tt.getLengthChar() == 3)) && tt.chars.isAllUpper()) {
            if (m_Alpha2State.containsKey(tt.term)) {
                boolean ok = false;
                com.pullenti.ner.Token tt2 = tt.getNext();
                if (tt2 != null && tt2.isChar(':')) 
                    tt2 = tt2.getNext();
                if (tt2 instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = tt2.getReferent();
                    if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PHONE")) 
                        ok = true;
                }
                if (ok) 
                    return _new1321(tt, tt, m_Alpha2State.get(tt.term));
            }
        }
        if (tt.getLengthChar() < 3) 
            return null;
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
            return null;
        if (tt.getLengthChar() < 5) {
            if (tt.getNext() == null || !tt.getNext().isHiphen()) 
                return null;
        }
        com.pullenti.ner.TextToken t0 = tt;
        String prefix = null;
        if (t0.getNext() != null && t0.getNext().isHiphen() && (t0.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t0.getNext().getNext(), com.pullenti.ner.TextToken.class);
            if (!tt.chars.isAllLower() && ((t0.isWhitespaceAfter() || t0.getNext().isWhitespaceAfter()))) {
                TerrItemToken tit = _TryParse(tt, prev, false);
                if (tit != null) {
                    if (tit.ontoItem != null) 
                        return null;
                }
            }
            if (tt.getLengthChar() > 1) {
                if (tt.chars.isCapitalUpper()) 
                    prefix = t0.term;
                else if (!tt.isWhitespaceBefore() && !t0.isWhitespaceAfter()) 
                    prefix = t0.term;
                if (((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isHiphen()) && !tt.getNext().isWhitespaceAfter() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && tt.getNext().getNext().chars.equals(t0.chars)) {
                    prefix = (prefix + "-" + tt.term);
                    tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class);
                }
            }
            if (prefix == null) 
                tt = t0;
        }
        if (tt.getMorph()._getClass().isAdverb()) 
            return null;
        if (CityItemToken.checkKeyword(t0) != null) 
            return null;
        if (!tt.getMorph()._getClass().isAdjective()) {
            if (CityItemToken.checkOntoItem(t0) != null) 
                return null;
        }
        com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(t0);
        if (npt != null) {
            if (((npt.noun.isValue("ФЕДЕРАЦИЯ", null) || npt.noun.isValue("ФЕДЕРАЦІЯ", null))) && npt.adjectives.size() == 1) {
                if (com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("РОССИЙСКАЯ", npt.adjectives.get(0)) || com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("РОСІЙСЬКА", npt.adjectives.get(0))) 
                    return _new1331(npt.getBeginToken(), npt.getEndToken(), (t0.kit.baseLanguage.isUa() ? m_RussiaUA : m_RussiaRU), npt.getMorph());
            }
        }
        if (t0.getMorph()._getClass().isProperName()) {
            if (t0.isWhitespaceAfter() || t0.getNext().isWhitespaceAfter()) 
                return null;
        }
        if (!t0.chars.isAllLower()) {
            com.pullenti.ner.core.TerminToken tok2 = m_SpecNames.tryParse(t0, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok2 != null) 
                return _new1336(t0, tok2.getEndToken(), true);
        }
        if (npt != null && npt.getEndToken() != npt.getBeginToken()) {
            if (npt.getEndToken().isValue("КИЛОМЕТР", null)) 
                npt = null;
            else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(npt.getEndToken(), true)) 
                npt = null;
            else {
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = m_TerrOntology.tryAttach(npt.getEndToken(), null, false);
                if (tok != null) 
                    npt = null;
                else {
                    TerrItemToken _next = tryParse(npt.getEndToken(), null, null);
                    if (_next != null && _next.terminItem != null) {
                        if (MiscLocationHelper.checkGeoObjectAfter(npt.getEndToken().getPrevious(), false, false)) 
                            npt = null;
                    }
                    else if (CityItemToken.checkKeyword(npt.getEndToken()) != null) {
                        if (MiscLocationHelper.checkGeoObjectAfter(npt.getEndToken().getPrevious(), false, false)) 
                            npt = null;
                    }
                }
            }
        }
        if (npt != null && npt.getEndToken() == tt.getNext()) {
            boolean adj = false;
            boolean regAfter = false;
            if (npt.adjectives.size() == 1 && !t0.chars.isAllLower()) {
                if (((((tt.getNext().isValue("РАЙОН", null) || tt.getNext().isValue("ОБЛАСТЬ", null) || tt.getNext().isValue("КРАЙ", null)) || tt.getNext().isValue("ВОЛОСТЬ", null) || tt.getNext().isValue("УЛУС", null)) || tt.getNext().isValue("ОКРУГ", null) || tt.getNext().isValue("АВТОНОМИЯ", "АВТОНОМІЯ")) || tt.getNext().isValue("РЕСПУБЛИКА", "РЕСПУБЛІКА") || tt.getNext().isValue("COUNTY", null)) || tt.getNext().isValue("STATE", null) || tt.getNext().isValue("REGION", null)) 
                    regAfter = true;
                else {
                    java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = m_TerrOntology.tryAttach(tt.getNext(), null, false);
                    if (tok != null) {
                        if ((((com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "РАЙОН") || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ОБЛАСТЬ") || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "УЛУС")) || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "КРАЙ") || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ВОЛОСТЬ")) || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ОКРУГ") || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "АВТОНОМИЯ")) || com.pullenti.unisharp.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "АВТОНОМІЯ") || ((tok.get(0).chars.isLatinLetter() && (tok.get(0).termin instanceof TerrTermin) && ((TerrTermin)com.pullenti.unisharp.Utils.cast(tok.get(0).termin, TerrTermin.class)).isRegion))) 
                            regAfter = true;
                    }
                }
            }
            if (regAfter) {
                adj = true;
                for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if (wf._getClass().isVerb() && wf.isInDictionary()) {
                        adj = false;
                        break;
                    }
                    else if (wf.isInDictionary() && !wf._getClass().isAdjective()) {
                    }
                }
                if (!adj && prefix != null) 
                    adj = true;
                if (!adj) {
                    if (CityItemToken.checkKeyword(tt.getNext().getNext()) != null || CityItemToken.checkOntoItem(tt.getNext().getNext()) != null) 
                        adj = true;
                }
                if (!adj) {
                    if (MiscLocationHelper.checkGeoObjectBefore(npt.getBeginToken(), false)) 
                        adj = true;
                }
                com.pullenti.ner.Token te = tt.getNext().getNext();
                if (te != null && te.isCharOf(",")) 
                    te = te.getNext();
                if (!adj && (te instanceof com.pullenti.ner.ReferentToken)) {
                    if (te.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        adj = true;
                }
                if (!adj) {
                    te = t0.getPrevious();
                    if (te != null && te.isCharOf(",")) 
                        te = te.getPrevious();
                    if (te instanceof com.pullenti.ner.ReferentToken) {
                        if (te.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            adj = true;
                    }
                }
                if (adj && npt.adjectives.get(0).getBeginToken() != npt.adjectives.get(0).getEndToken()) {
                    if (!npt.adjectives.get(0).getBeginToken().chars.equals(npt.adjectives.get(0).getEndToken().chars)) 
                        return null;
                }
            }
            else if ((npt.adjectives.size() == 1 && (npt.getEndToken() instanceof com.pullenti.ner.TextToken) && npt.getEndToken().getMorphClassInDictionary().isNoun()) && prev != null && prev.terminItem != null) {
                adj = true;
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class);
            }
            if (!adj && !t0.chars.isLatinLetter()) 
                return null;
        }
        TerrItemToken res = new TerrItemToken(t0, tt);
        res.isAdjective = tt.getMorph()._getClass().isAdjective();
        res.setMorph(tt.getMorph());
        if (npt != null && npt.getEndChar() > res.getEndChar() && npt.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            res.setMorph(new com.pullenti.ner.MorphCollection(tt.getMorph()));
            res.getMorph().removeItems(npt.getMorph().getGender());
        }
        if (t0 instanceof com.pullenti.ner.TextToken) {
            for (com.pullenti.morph.MorphBaseInfo wf : t0.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm f = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                if (!f.isInDictionary()) 
                    continue;
                if (((wf._getClass().isProperSurname() || wf._getClass().isProperName())) && f.isInDictionary()) 
                    res.canBeSurname = true;
                else if (wf._getClass().isAdjective() && f.isInDictionary()) 
                    res.isAdjInDictionary = true;
                else if (wf._getClass().isProperGeo()) {
                    if (!t0.chars.isAllLower()) 
                        res.isGeoInDictionary = true;
                }
            }
        }
        if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isCapitalUpper()) {
            com.pullenti.ner.MetaToken dir = MiscLocationHelper.tryAttachNordWest(tt.getNext());
            if (dir != null) 
                res.setEndToken(dir.getEndToken());
        }
        if (((res.getBeginToken() == res.getEndToken() && res.isAdjective && (res.getWhitespacesAfterCount() < 2)) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && res.getEndToken().getNext().chars.isCapitalUpper()) && prev != null) {
            if (MiscLocationHelper.checkGeoObjectAfter(res.getEndToken().getNext(), false, false)) 
                res.setEndToken(res.getEndToken().getNext());
            else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(res.getEndToken().getNext().getNext(), false)) 
                res.setEndToken(res.getEndToken().getNext());
        }
        return res;
    }

    private static TerrItemToken _tryParseDistrictName(com.pullenti.ner.Token t, int lev) {
        if (lev > 2) 
            return null;
        if (!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isCapitalUpper() || !t.chars.isCyrillicLetter()) 
            return null;
        if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().chars.equals(t.chars)) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = m_TerrOntology.tryAttach(t, null, false);
            if ((tok != null && tok.get(0).item != null && (tok.get(0).item.referent instanceof com.pullenti.ner.geo.GeoReferent)) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tok.get(0).item.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) 
                return null;
            tok = m_TerrOntology.tryAttach(t.getNext().getNext(), null, false);
            if ((tok != null && tok.get(0).item != null && (tok.get(0).item.referent instanceof com.pullenti.ner.geo.GeoReferent)) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tok.get(0).item.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) 
                return null;
            return new TerrItemToken(t, t.getNext().getNext());
        }
        if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.equals(t.chars)) {
            com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(t);
            if (npt != null && npt.getEndToken() == t.getNext() && npt.adjectives.size() == 1) {
                if (!npt.getEndToken().getMorph()._getClass().isAdjective() || ((npt.getEndToken().getMorph().getCase().isNominative() && (npt.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.LanguageHelper.endsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class)).term, "О")))) {
                    TerrItemToken ty = _TryParse(t.getNext(), null, false);
                    if (ty != null && ty.terminItem != null) 
                        return null;
                    return new TerrItemToken(t, t.getNext());
                }
            }
        }
        String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        TerrItemToken res = _new1337(t, t, true);
        if (!com.pullenti.morph.LanguageHelper.endsWith(str, "О")) 
            res.isDoubt = true;
        com.pullenti.ner.MetaToken dir = MiscLocationHelper.tryAttachNordWest(t);
        if (dir != null) {
            res.setEndToken(dir.getEndToken());
            res.isDoubt = false;
            if (res.getEndToken().getWhitespacesAfterCount() < 2) {
                TerrItemToken res2 = _tryParseDistrictName(res.getEndToken().getNext(), lev + 1);
                if (res2 != null && res2.terminItem == null) 
                    res.setEndToken(res2.getEndToken());
            }
        }
        return res;
    }

    public static java.util.ArrayList<TerrItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount, GeoAnalyzerData ad) {
        TerrItemToken ci = TerrItemToken.tryParse(t, null, ad);
        if (ci == null) 
            return null;
        java.util.ArrayList<TerrItemToken> li = new java.util.ArrayList<TerrItemToken>();
        li.add(ci);
        t = ci.getEndToken().getNext();
        if (t == null) 
            return li;
        if (ci.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(ci.terminItem.getCanonicText(), "АВТОНОМИЯ")) {
            if (t.getMorph().getCase().isGenitive()) 
                return null;
        }
        for (t = ci.getEndToken().getNext(); t != null; ) {
            ci = TerrItemToken.tryParse(t, li.get(li.size() - 1), ad);
            if (ci == null) {
                if (t.chars.isCapitalUpper() && li.size() == 1 && ((li.get(0).isCityRegion() || ((li.get(0).terminItem != null && li.get(0).terminItem.isSpecificPrefix))))) {
                    CityItemToken cit = CityItemToken.tryParse(t, null, false, null);
                    if (cit != null && cit.typ == CityItemToken.ItemType.PROPERNAME) 
                        ci = new TerrItemToken(cit.getBeginToken(), cit.getEndToken());
                }
                else if ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false) && t.getNext() != null && ((t.getNext().chars.isCapitalUpper() || t.getNext().chars.isAllUpper()))) && li.size() == 1 && ((li.get(0).isCityRegion() || ((li.get(0).terminItem != null && li.get(0).terminItem.isSpecificPrefix))))) {
                    CityItemToken cit = CityItemToken.tryParse(t.getNext(), null, false, null);
                    if (cit != null && ((cit.typ == CityItemToken.ItemType.PROPERNAME || cit.typ == CityItemToken.ItemType.CITY)) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(cit.getEndToken().getNext(), false, null, false)) 
                        ci = new TerrItemToken(t, cit.getEndToken().getNext());
                    else {
                        com.pullenti.ner.core.BracketSequenceToken brr = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (brr != null) {
                            boolean ok = false;
                            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("ORGANIZATION", t.getNext(), null);
                            if (rt != null && (rt.toString().toUpperCase().indexOf("СОВЕТ") >= 0)) 
                                ok = true;
                            else if (brr.getLengthChar() < 40) 
                                ok = true;
                            if (ok) 
                                ci = new TerrItemToken(t, brr.getEndToken());
                        }
                    }
                }
                else if (t.isChar('(')) {
                    ci = TerrItemToken.tryParse(t.getNext(), null, ad);
                    if (ci != null && ci.getEndToken().getNext() != null && ci.getEndToken().getNext().isChar(')')) {
                        TerrItemToken ci0 = li.get(li.size() - 1);
                        if (ci0.ontoItem != null && ci.ontoItem == ci0.ontoItem) {
                            ci0.setEndToken(ci.getEndToken().getNext());
                            t = ci0.getEndToken().getNext();
                        }
                        else {
                            TerrItemToken ci1 = _new1338(t, ci.getEndToken().getNext(), ci.ontoItem, ci.terminItem);
                            li.add(ci1);
                            t = ci1.getEndToken();
                        }
                        continue;
                    }
                }
                else if ((t.isComma() && li.size() == 1 && li.get(0).terminItem == null) && (t.getWhitespacesAfterCount() < 3)) {
                    java.util.ArrayList<TerrItemToken> li2 = tryParseList(t.getNext(), 2, null);
                    if (li2 != null && li2.size() == 1 && li2.get(0).terminItem != null) {
                        com.pullenti.ner.Token tt2 = li2.get(0).getEndToken().getNext();
                        boolean ok = false;
                        if (tt2 == null || tt2.getWhitespacesBeforeCount() > 3) 
                            ok = true;
                        else if (((tt2.getLengthChar() == 1 && !tt2.isLetters())) || !(tt2 instanceof com.pullenti.ner.TextToken)) 
                            ok = true;
                        if (ok) {
                            li.add(li2.get(0));
                            t = li2.get(0).getEndToken();
                            break;
                        }
                    }
                }
                if (ci == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                    java.util.ArrayList<TerrItemToken> lii = tryParseList(t.getNext(), maxCount, null);
                    if (lii != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(lii.get(lii.size() - 1).getEndToken().getNext(), false, null, false)) {
                        com.pullenti.unisharp.Utils.addToArrayList(li, lii);
                        return li;
                    }
                }
                if (ci == null) 
                    break;
            }
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore()) {
                if (t.getNewlinesBeforeCount() > 1) 
                    break;
                if (li.size() > 0 && li.get(li.size() - 1).isAdjective && ci.terminItem != null) {
                }
                else if (li.size() == 1 && li.get(0).terminItem != null && ci.terminItem == null) {
                }
                else 
                    break;
            }
            if (ci.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(ci.terminItem.getCanonicText(), "ТЕРРИТОРИЯ")) 
                break;
            li.add(ci);
            t = ci.getEndToken().getNext();
            if (maxCount > 0 && li.size() >= maxCount) 
                break;
        }
        for (TerrItemToken cc : li) {
            if (cc.ontoItem != null && !cc.isAdjective) {
                if (!cc.getBeginToken().chars.isCyrillicLetter()) 
                    continue;
                String alpha2 = null;
                if (cc.ontoItem.referent instanceof com.pullenti.ner.geo.GeoReferent) 
                    alpha2 = ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(cc.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class)).getAlpha2();
                if (com.pullenti.unisharp.Utils.stringsEq(alpha2, "TG")) {
                    if (cc.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                        if (com.pullenti.unisharp.Utils.stringsNe(cc.getBeginToken().getSourceText(), "Того")) 
                            return null;
                        if (li.size() == 1 && cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isChar('.')) 
                            return null;
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(cc.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS, 0, null);
                        if (npt != null && npt.getEndToken() != cc.getBeginToken()) 
                            return null;
                        if (cc.getBeginToken().getNext() != null) {
                            if (cc.getBeginToken().getNext().getMorph()._getClass().isPersonalPronoun() || cc.getBeginToken().getNext().getMorph()._getClass().isPronoun()) 
                                return null;
                        }
                    }
                    if (li.size() < 2) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(alpha2, "PE")) {
                    if (cc.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                        if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(cc.getBeginToken(), com.pullenti.ner.TextToken.class)).getSourceText(), "Перу")) 
                            return null;
                        if (li.size() == 1 && cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isChar('.')) 
                            return null;
                    }
                    if (li.size() < 2) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(alpha2, "DM")) {
                    if (cc.getEndToken().getNext() != null) {
                        if (cc.getEndToken().getNext().chars.isCapitalUpper() || cc.getEndToken().getNext().chars.isAllUpper()) 
                            return null;
                    }
                    return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(alpha2, "JE")) {
                    if (cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isHiphen()) 
                        return null;
                }
                return li;
            }
            else if (cc.ontoItem != null && cc.isAdjective) {
                String alpha2 = null;
                if (cc.ontoItem.referent instanceof com.pullenti.ner.geo.GeoReferent) 
                    alpha2 = ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(cc.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class)).getAlpha2();
                if (com.pullenti.unisharp.Utils.stringsEq(alpha2, "SU")) {
                    if (cc.getEndToken().getNext() == null || !cc.getEndToken().getNext().isValue("СОЮЗ", null)) 
                        cc.ontoItem = null;
                }
            }
        }
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).ontoItem != null && li.get(i).ontoItem2 != null) {
                com.pullenti.ner.core.Termin nou = null;
                if (i > 0 && li.get(i - 1).terminItem != null) 
                    nou = li.get(i - 1).terminItem;
                else if (((i + 1) < li.size()) && li.get(i + 1).terminItem != null) 
                    nou = li.get(i + 1).terminItem;
                if (nou == null || li.get(i).ontoItem.referent == null || li.get(i).ontoItem2.referent == null) 
                    continue;
                if (li.get(i).ontoItem.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, nou.getCanonicText().toLowerCase(), true) == null && li.get(i).ontoItem2.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, nou.getCanonicText().toLowerCase(), true) != null) {
                    li.get(i).ontoItem = li.get(i).ontoItem2;
                    li.get(i).ontoItem2 = null;
                }
                else if (li.get(i).ontoItem.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "республика", true) != null && com.pullenti.unisharp.Utils.stringsNe(nou.getCanonicText(), "РЕСПУБЛИКА")) {
                    li.get(i).ontoItem = li.get(i).ontoItem2;
                    li.get(i).ontoItem2 = null;
                }
            }
        }
        if ((li.size() >= 3 && li.get(0).terminItem == null && li.get(1).terminItem != null) && li.get(2).terminItem == null) {
            if (li.size() == 3 || ((li.size() >= 5 && ((((li.get(3).terminItem != null && li.get(4).terminItem == null)) || ((li.get(4).terminItem != null && li.get(3).terminItem == null))))))) {
                com.pullenti.ner.Token t1 = li.get(0).getBeginToken().getPrevious();
                if (t1 != null && t1.isChar('.') && t1.getPrevious() != null) {
                    t1 = t1.getPrevious();
                    CityItemToken cit = CityItemToken.tryParseBack(t1, false);
                    if (cit != null) 
                        li.remove(0);
                    else if (t1.chars.isAllLower() && ((t1.isValue("С", null) || t1.isValue("П", null) || t1.isValue("ПОС", null)))) 
                        li.remove(0);
                }
            }
        }
        for (TerrItemToken cc : li) {
            if (cc.ontoItem != null || ((cc.terminItem != null && !cc.isAdjective))) 
                return li;
        }
        return null;
    }

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (m_TerrOntology != null) 
            return;
        m_TerrOntology = new com.pullenti.ner.core.IntOntologyCollection();
        M_TERRADJS = new com.pullenti.ner.core.TerminCollection();
        M_MANSBYSTATE = new com.pullenti.ner.core.TerminCollection();
        m_UnknownRegions = new com.pullenti.ner.core.TerminCollection();
        m_TerrNounAdjectives = new com.pullenti.ner.core.TerminCollection();
        m_CapitalsByState = new com.pullenti.ner.core.TerminCollection();
        m_GeoAbbrs = new com.pullenti.ner.core.TerminCollection();
        TerrTermin t = TerrTermin._new1339("РЕСПУБЛИКА", com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("РЕСП.");
        t.addAbridge("РЕСП-КА");
        t.addAbridge("РЕСПУБ.");
        t.addAbridge("РЕСПУБЛ.");
        t.addAbridge("Р-КА");
        t.addAbridge("РЕСП-КА");
        m_TerrOntology.add(t);
        m_TerrOntology.add(TerrTermin._new1340("РЕСПУБЛІКА", com.pullenti.morph.MorphLang.UA, com.pullenti.morph.MorphGender.FEMINIE));
        t = TerrTermin._new1341("ГОСУДАРСТВО", true, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("ГОС-ВО");
        m_TerrOntology.add(t);
        t = TerrTermin._new1342("ДЕРЖАВА", com.pullenti.morph.MorphLang.UA, true, com.pullenti.morph.MorphGender.FEMINIE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1339("АВТОНОМНАЯ СОВЕТСКАЯ СОЦИАЛИСТИЧЕСКАЯ РЕСПУБЛИКА", com.pullenti.morph.MorphGender.FEMINIE);
        t.acronym = "АССР";
        m_TerrOntology.add(t);
        for (String s : new String[] {"СОЮЗ", "СОДРУЖЕСТВО", "ФЕДЕРАЦИЯ", "КОНФЕДЕРАЦИЯ"}) {
            m_TerrOntology.add(TerrTermin._new1344(s, true, true));
        }
        for (String s : new String[] {"СОЮЗ", "СПІВДРУЖНІСТЬ", "ФЕДЕРАЦІЯ", "КОНФЕДЕРАЦІЯ"}) {
            m_TerrOntology.add(TerrTermin._new1345(s, com.pullenti.morph.MorphLang.UA, true, true));
        }
        for (String s : new String[] {"КОРОЛЕВСТВО", "КНЯЖЕСТВО", "ГЕРЦОГСТВО", "ИМПЕРИЯ", "ЦАРСТВО", "KINGDOM", "DUCHY", "EMPIRE"}) {
            m_TerrOntology.add(TerrTermin._new1346(s, true));
        }
        for (String s : new String[] {"КОРОЛІВСТВО", "КНЯЗІВСТВО", "ГЕРЦОГСТВО", "ІМПЕРІЯ"}) {
            m_TerrOntology.add(TerrTermin._new1347(s, com.pullenti.morph.MorphLang.UA, true));
        }
        for (String s : new String[] {"НЕЗАВИСИМЫЙ", "ОБЪЕДИНЕННЫЙ", "СОЕДИНЕННЫЙ", "НАРОДНЫЙ", "НАРОДНО", "ФЕДЕРАТИВНЫЙ", "ДЕМОКРАТИЧЕСКИЙ", "СОВЕТСКИЙ", "СОЦИАЛИСТИЧЕСКИЙ", "КООПЕРАТИВНЫЙ", "ИСЛАМСКИЙ", "АРАБСКИЙ", "МНОГОНАЦИОНАЛЬНЫЙ", "СУВЕРЕННЫЙ", "САМОПРОВОЗГЛАШЕННЫЙ", "НЕПРИЗНАННЫЙ"}) {
            m_TerrOntology.add(TerrTermin._new1348(s, true, true));
        }
        for (String s : new String[] {"НЕЗАЛЕЖНИЙ", "ОБЄДНАНИЙ", "СПОЛУЧЕНИЙ", "НАРОДНИЙ", "ФЕДЕРАЛЬНИЙ", "ДЕМОКРАТИЧНИЙ", "РАДЯНСЬКИЙ", "СОЦІАЛІСТИЧНИЙ", "КООПЕРАТИВНИЙ", "ІСЛАМСЬКИЙ", "АРАБСЬКИЙ", "БАГАТОНАЦІОНАЛЬНИЙ", "СУВЕРЕННИЙ"}) {
            m_TerrOntology.add(TerrTermin._new1349(s, com.pullenti.morph.MorphLang.UA, true, true));
        }
        t = TerrTermin._new1350("ОБЛАСТЬ", true, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ОБЛ.");
        m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new100("ОБЛАСТНОЙ", t));
        m_TerrOntology.add(t);
        t = TerrTermin._new1352("REGION", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1353("ОБЛАСТЬ", com.pullenti.morph.MorphLang.UA, true, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ОБЛ.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1354(null, true, "АО");
        t.addVariant("АОБЛ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1355(null, com.pullenti.morph.MorphLang.UA, true, "АО");
        m_TerrOntology.add(t);
        t = TerrTermin._new1350("РАЙОН", true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("Р-Н");
        t.addAbridge("Р-ОН");
        t.addAbridge("РН.");
        m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new100("РАЙОННЫЙ", t));
        m_TerrOntology.add(t);
        t = TerrTermin._new1353("РАЙОН", com.pullenti.morph.MorphLang.UA, true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("Р-Н");
        t.addAbridge("Р-ОН");
        t.addAbridge("РН.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1350("УЛУС", true, com.pullenti.morph.MorphGender.MASCULINE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1350("УЕЗД", true, com.pullenti.morph.MorphGender.MASCULINE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1361("ГУБЕРНАТОРСТВО", true, true, com.pullenti.morph.MorphGender.NEUTER);
        m_TerrOntology.add(t);
        t = TerrTermin._new1361("ШТАТ", true, true, com.pullenti.morph.MorphGender.MASCULINE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1352("STATE", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1364("ШТАТ", com.pullenti.morph.MorphLang.UA, true, true, com.pullenti.morph.MorphGender.MASCULINE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1361("ПРОВИНЦИЯ", true, true, com.pullenti.morph.MorphGender.FEMINIE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1364("ПРОВІНЦІЯ", com.pullenti.morph.MorphLang.UA, true, true, com.pullenti.morph.MorphGender.FEMINIE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1352("PROVINCE", true);
        t.addVariant("PROVINCIAL", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1368("ПРЕФЕКТУРА", true, com.pullenti.morph.MorphGender.FEMINIE, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1369("PREFECTURE", true, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1361("ГРАФСТВО", true, true, com.pullenti.morph.MorphGender.NEUTER);
        m_TerrOntology.add(t);
        t = TerrTermin._new1350("АВТОНОМИЯ", true, com.pullenti.morph.MorphGender.FEMINIE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1352("AUTONOMY", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1353("АВТОНОМІЯ", com.pullenti.morph.MorphLang.UA, true, com.pullenti.morph.MorphGender.FEMINIE);
        m_TerrOntology.add(t);
        t = TerrTermin._new1374("ЗАКРЫТОЕ АДМИНИСТРАТИВНО ТЕРРИТОРИАЛЬНОЕ ОБРАЗОВАНИЕ", true, com.pullenti.morph.MorphGender.NEUTER, "ЗАТО");
        m_TerrOntology.add(t);
        for (String s : new String[] {"РЕСПУБЛИКА", "КРАЙ", "ОКРУГ", "ФЕДЕРАЛЬНЫЙ ОКРУГ", "АВТОНОМНЫЙ ОКРУГ", "АВТОНОМНАЯ ОБЛАСТЬ", "НАЦИОНАЛЬНЫЙ ОКРУГ", "ВОЛОСТЬ", "ФЕДЕРАЛЬНАЯ ЗЕМЛЯ", "ВОЕВОДСТВО", "МУНИЦИПАЛЬНЫЙ РАЙОН", "МУНИЦИПАЛЬНЫЙ ОКРУГ", "АДМИНИСТРАТИВНЫЙ ОКРУГ", "ГОРОДСКОЙ РАЙОН", "ВНУТРИГОРОДСКОЙ РАЙОН", "АДМИНИСТРАТИВНЫЙ РАЙОН", "СУДЕБНЫЙ РАЙОН", "ВНУТРИГОРОДСКОЕ МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ", "СЕЛЬСКОЕ МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ", "ВНУТРИГОРОДСКАЯ ТЕРРИТОРИЯ", "МЕЖСЕЛЕННАЯ ТЕРРИТОРИЯ", "REPUBLIC", "COUNTY", "BOROUGH", "PARISH", "MUNICIPALITY", "CENSUS AREA", "AUTONOMOUS REGION", "ADMINISTRATIVE REGION", "SPECIAL ADMINISTRATIVE REGION"}) {
            t = TerrTermin._new1375(s, true, (s.indexOf(" ") >= 0));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "КРАЙ")) {
                t.addAbridge("КР.");
                m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new100("КРАЕВОЙ", t));
                t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ОКРУГ")) {
                m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new100("ОКРУЖНОЙ", t));
                t.addAbridge("ОКР.");
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ФЕДЕРАЛЬНЫЙ ОКРУГ")) 
                t.acronym = "ФО";
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "РАЙОН")) {
                t.addAbridge(s.replace("РАЙОН", "Р-Н"));
                t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                if (com.pullenti.unisharp.Utils.stringsEq(s, "МУНИЦИПАЛЬНЫЙ РАЙОН")) {
                    t.addAbridge("М.Р-Н");
                    t.addAbridge("М Р-Н");
                    t.addAbridge("МУН.Р-Н");
                }
            }
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "ОКРУГ")) 
                t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "ОБРАЗОВАНИЕ")) 
                t.setGender(com.pullenti.morph.MorphGender.NEUTER);
            m_TerrOntology.add(t);
        }
        for (String s : new String[] {"РЕСПУБЛІКА", "КРАЙ", "ОКРУГ", "ФЕДЕРАЛЬНИЙ ОКРУГ", "АВТОНОМНИЙ ОКРУГ", "АВТОНОМНА ОБЛАСТЬ", "НАЦІОНАЛЬНИЙ ОКРУГ", "ВОЛОСТЬ", "ФЕДЕРАЛЬНА ЗЕМЛЯ", "МУНІЦИПАЛЬНИЙ РАЙОН", "МУНІЦИПАЛЬНИЙ ОКРУГ", "АДМІНІСТРАТИВНИЙ ОКРУГ", "МІСЬКИЙ РАЙОН", "ВНУТРИГОРОДСКОЕ МУНІЦИПАЛЬНЕ УТВОРЕННЯ"}) {
            t = TerrTermin._new1378(s, com.pullenti.morph.MorphLang.UA, true, (s.indexOf(" ") >= 0));
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "РАЙОН")) {
                t.addAbridge(s.replace("РАЙОН", "Р-Н"));
                t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
            }
            m_TerrOntology.add(t);
        }
        t = TerrTermin._new1350("ГОРОДСКОЙ ОКРУГ", true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ГОР. ОКРУГ");
        t.addAbridge("Г.О.");
        t.addAbridge("Г/ОКРУГ");
        t.addAbridge("Г/О");
        m_TerrOntology.add(t);
        t = TerrTermin._new1350("СЕЛЬСКИЙ ОКРУГ", true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("С.О.");
        t.addAbridge("C.O.");
        t.addAbridge("ПС С.О.");
        t.addAbridge("С/ОКРУГ");
        t.addAbridge("С/О");
        m_TerrOntology.add(t);
        t = TerrTermin._new1353("СІЛЬСЬКИЙ ОКРУГ", com.pullenti.morph.MorphLang.UA, true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("С.О.");
        t.addAbridge("C.O.");
        t.addAbridge("С/ОКРУГ");
        m_TerrOntology.add(t);
        t = TerrTermin._new1382("СЕЛЬСКИЙ СОВЕТ", "СЕЛЬСКИЙ ОКРУГ", true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("СЕЛЬСОВЕТ", false);
        t.addAbridge("С.С.");
        t.addAbridge("С/С");
        t.addVariant("СЕЛЬСКАЯ АДМИНИСТРАЦИЯ", false);
        t.addAbridge("С.А.");
        t.addAbridge("С.АДМ.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1352("ПОСЕЛКОВЫЙ ОКРУГ", true);
        t.addVariant("ПОСЕЛКОВАЯ АДМИНИСТРАЦИЯ", false);
        t.addAbridge("П.А.");
        t.addAbridge("П.АДМ.");
        t.addAbridge("П/А");
        m_TerrOntology.add(t);
        t = TerrTermin._new1382("ПОСЕЛКОВЫЙ СОВЕТ", "ПОСЕЛКОВЫЙ ОКРУГ", true, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("П.С.");
        m_TerrOntology.add(t);
        m_TerrOntology.add(TerrTermin._new1385("АВТОНОМНЫЙ", true, true));
        m_TerrOntology.add(TerrTermin._new1386("АВТОНОМНИЙ", com.pullenti.morph.MorphLang.UA, true, true));
        m_TerrOntology.add(TerrTermin._new1387("МУНИЦИПАЛЬНОЕ СОБРАНИЕ", true, true, true));
        m_TerrOntology.add(TerrTermin._new1388("МУНІЦИПАЛЬНЕ ЗБОРИ", com.pullenti.morph.MorphLang.UA, true, true, true));
        t = TerrTermin._new1389("МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ", "МО", com.pullenti.morph.MorphGender.NEUTER);
        m_TerrOntology.add(t);
        t = TerrTermin._new1390("МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ МУНИЦИПАЛЬНЫЙ РАЙОН", "МОМР", true);
        t.addVariant("МО МР", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1391("МУНИЦИПАЛЬНЫЙ ОКРУГ ГОРОДСКОЙ ОКРУГ", "МУНИЦИПАЛЬНЫЙ ОКРУГ", "МОГО", true);
        t.addVariant("МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ ГОРОДСКОЙ ОКРУГ", false);
        t.addVariant("МО ГО", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ЦЕНТРАЛЬНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЦАО");
        t.addVariant("ЦЕНТРАЛЬНЫЙ АО", false);
        t.addVariant("ЦЕНТРАЛЬНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("СЕВЕРНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("САО");
        t.addVariant("СЕВЕРНЫЙ АО", false);
        t.addVariant("СЕВЕРНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("СЕВЕРО-ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("СВАО");
        t.addVariant("СЕВЕРО-ВОСТОЧНЫЙ АО", false);
        t.addVariant("СЕВЕРО-ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ВАО");
        t.addVariant("ВОСТОЧНЫЙ АО", false);
        t.addVariant("ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ЮГО-ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЮВАО");
        t.addVariant("ЮГО-ВОСТОЧНЫЙ АО", false);
        t.addVariant("ЮГО-ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ЮЖНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЮАО");
        t.addVariant("ЮЖНЫЙ АО", false);
        t.addVariant("ЮЖНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ЗАПАДНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЗАО");
        t.addVariant("ЗАПАДНЫЙ АО", false);
        t.addVariant("ЗАПАДНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("СЕВЕРО-ЗАПАДНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("СЗАО");
        t.addVariant("СЕВЕРО-ЗАПАДНЫЙ АО", false);
        t.addVariant("СЕВЕРО-ЗАПАДНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ЗЕЛЕНОГРАДСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЗЕЛАО");
        t.addVariant("ЗЕЛЕНОГРАДСКИЙ АО", false);
        t.addVariant("ЗЕЛЕНОГРАДСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ТРОИЦКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ТАО");
        t.addVariant("ТРОИЦКИЙ АО", false);
        t.addVariant("ТРОИЦКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("НОВОМОСКОВСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("НАО");
        t.addVariant("НОВОМОСКОВСКИЙ АО", false);
        t.addVariant("НОВОМОСКОВСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1392("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ТИНАО");
        t.addAbridge("НИТАО");
        t.addVariant("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ АО", false);
        t.addVariant("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        m_SpecNames = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"МАРЬИНА РОЩА", "ПРОСПЕКТ ВЕРНАДСКОГО"}) {
            m_SpecNames.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        m_Alpha2State = new java.util.HashMap<String, com.pullenti.ner.core.IntOntologyItem>();
        byte[] dat = com.pullenti.ner.address.internal.ResourceHelper.getBytes("t.dat");
        if (dat == null) 
            throw new Exception("Not found resource file t.dat in Analyzer.Location");
        dat = MiscLocationHelper.deflate(dat);
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(dat)) {
            tmp.setPosition(0L);
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.load(tmp);
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
                org.w3c.dom.Node a = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "l");
                if (a != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "en")) 
                        lang = com.pullenti.morph.MorphLang.EN;
                    else if (com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "ua")) 
                        lang = com.pullenti.morph.MorphLang.UA;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "state")) 
                    loadState(x, lang);
                else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "reg")) 
                    loadRegion(x, lang);
                else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "unknown")) {
                    a = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "name");
                    if (a != null && a.getNodeValue() != null) 
                        m_UnknownRegions.add(com.pullenti.ner.core.Termin._new968(a.getNodeValue(), lang));
                }
            }
        }
    }

    public static com.pullenti.ner.core.IntOntologyCollection m_TerrOntology;

    public static com.pullenti.ner.core.TerminCollection m_GeoAbbrs;

    private static com.pullenti.ner.core.IntOntologyItem m_RussiaRU;

    private static com.pullenti.ner.core.IntOntologyItem m_RussiaUA;

    private static com.pullenti.ner.core.IntOntologyItem m_MosRegRU;

    private static com.pullenti.ner.core.IntOntologyItem m_LenRegRU;

    private static com.pullenti.ner.core.IntOntologyItem m_Belorussia;

    private static com.pullenti.ner.core.IntOntologyItem m_Kazahstan;

    private static com.pullenti.ner.core.IntOntologyItem m_TamogSous;

    private static com.pullenti.ner.core.IntOntologyItem m_Tatarstan;

    private static com.pullenti.ner.core.IntOntologyItem m_Udmurtia;

    private static com.pullenti.ner.core.IntOntologyItem m_Dagestan;

    public static com.pullenti.ner.core.TerminCollection M_TERRADJS;

    public static com.pullenti.ner.core.TerminCollection M_MANSBYSTATE;

    public static com.pullenti.ner.core.TerminCollection m_UnknownRegions;

    public static com.pullenti.ner.core.TerminCollection m_TerrNounAdjectives;

    public static com.pullenti.ner.core.TerminCollection m_CapitalsByState;

    public static java.util.HashMap<String, com.pullenti.ner.core.IntOntologyItem> m_Alpha2State;

    private static com.pullenti.ner.core.TerminCollection m_SpecNames;

    public static java.util.ArrayList<com.pullenti.ner.Referent> m_AllStates;

    private static void loadState(org.w3c.dom.Node xml, com.pullenti.morph.MorphLang lang) {
        com.pullenti.ner.geo.GeoReferent state = new com.pullenti.ner.geo.GeoReferent();
        com.pullenti.ner.core.IntOntologyItem c = new com.pullenti.ner.core.IntOntologyItem(state);
        java.util.ArrayList<String> acrs = null;
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), null);
                c.termins.add(te);
                state.addName(x.getTextContent());
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "acr")) {
                c.termins.add(com.pullenti.ner.core.Termin._new1405(x.getTextContent(), lang));
                state.addName(x.getTextContent());
                if (acrs == null) 
                    acrs = new java.util.ArrayList<String>();
                acrs.add(x.getTextContent());
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "a")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = c;
                c.termins.add(te);
                M_TERRADJS.add(te);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "a2")) 
                state.setAlpha2(x.getTextContent());
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "m")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                te.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                M_MANSBYSTATE.add(te);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "w")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                te.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                M_MANSBYSTATE.add(te);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "cap")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                m_CapitalsByState.add(te);
            }
        }
        c.setShortestCanonicalText(true);
        if (com.pullenti.unisharp.Utils.stringsEq(c.getCanonicText(), "ГОЛЛАНДИЯ") || c.getCanonicText().startsWith("КОРОЛЕВСТВО НИДЕР")) 
            c.setCanonicText("НИДЕРЛАНДЫ");
        else if (com.pullenti.unisharp.Utils.stringsEq(c.getCanonicText(), "ГОЛЛАНДІЯ") || c.getCanonicText().startsWith("КОРОЛІВСТВО НІДЕР")) 
            c.setCanonicText("НІДЕРЛАНДИ");
        if (com.pullenti.unisharp.Utils.stringsEq(state.getAlpha2(), "RU")) {
            if (lang.isUa()) 
                m_RussiaUA = c;
            else 
                m_RussiaRU = c;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(state.getAlpha2(), "BY")) {
            if (!lang.isUa()) 
                m_Belorussia = c;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(state.getAlpha2(), "KZ")) {
            if (!lang.isUa()) 
                m_Kazahstan = c;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(c.getCanonicText(), "ТАМОЖЕННЫЙ СОЮЗ")) {
            if (!lang.isUa()) 
                m_TamogSous = c;
        }
        if (state.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, null, true) == null) {
            if (lang.isUa()) 
                state.addTypState(lang);
            else {
                state.addTypState(com.pullenti.morph.MorphLang.RU);
                state.addTypState(com.pullenti.morph.MorphLang.EN);
            }
        }
        m_TerrOntology.addItem(c);
        if (lang.isRu()) 
            m_AllStates.add(state);
        String a2 = state.getAlpha2();
        if (a2 != null) {
            if (!m_Alpha2State.containsKey(a2)) 
                m_Alpha2State.put(a2, c);
            String a3;
            com.pullenti.unisharp.Outargwrapper<String> wrapa31406 = new com.pullenti.unisharp.Outargwrapper<String>();
            boolean inoutres1407 = com.pullenti.unisharp.Utils.tryGetValue(MiscLocationHelper.m_Alpha2_3, a2, wrapa31406);
            a3 = wrapa31406.value;
            if (inoutres1407) {
                if (!m_Alpha2State.containsKey(a3)) 
                    m_Alpha2State.put(a3, c);
            }
        }
        if (acrs != null) {
            for (String a : acrs) {
                if (!m_Alpha2State.containsKey(a)) 
                    m_Alpha2State.put(a, c);
            }
        }
    }

    private static void loadRegion(org.w3c.dom.Node xml, com.pullenti.morph.MorphLang lang) {
        com.pullenti.ner.geo.GeoReferent reg = new com.pullenti.ner.geo.GeoReferent();
        com.pullenti.ner.core.IntOntologyItem r = new com.pullenti.ner.core.IntOntologyItem(reg);
        com.pullenti.ner.core.Termin aTerm = null;
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                if (v.startsWith("ЦЕНТРАЛ")) {
                }
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(v, lang);
                if (lang.isRu() && m_MosRegRU == null && com.pullenti.unisharp.Utils.stringsEq(v, "ПОДМОСКОВЬЕ")) {
                    m_MosRegRU = r;
                    te.addAbridge("МОС.ОБЛ.");
                    te.addAbridge("МОСК.ОБЛ.");
                    te.addAbridge("МОСКОВ.ОБЛ.");
                    te.addAbridge("МОС.ОБЛАСТЬ");
                    te.addAbridge("МОСК.ОБЛАСТЬ");
                    te.addAbridge("МОСКОВ.ОБЛАСТЬ");
                }
                else if (lang.isRu() && m_LenRegRU == null && com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕНОБЛАСТЬ")) {
                    te.acronym = "ЛО";
                    te.addAbridge("ЛЕН.ОБЛ.");
                    te.addAbridge("ЛЕН.ОБЛАСТЬ");
                    m_LenRegRU = r;
                }
                r.termins.add(te);
                reg.addName(v);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "t")) 
                reg.addTyp(x.getTextContent());
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "a")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, null, false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = r;
                r.termins.add(te);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "ab")) {
                if (aTerm == null) 
                    aTerm = com.pullenti.ner.core.Termin._new372(reg.getStringValue(com.pullenti.ner.geo.GeoReferent.ATTR_NAME), lang, reg);
                aTerm.addAbridge(x.getTextContent());
            }
        }
        if (aTerm != null) 
            m_GeoAbbrs.add(aTerm);
        r.setShortestCanonicalText(true);
        if (r.getCanonicText().startsWith("КАРАЧАЕВО")) 
            r.setCanonicText("КАРАЧАЕВО - ЧЕРКЕССИЯ");
        if ((r.getCanonicText().indexOf("ТАТАРСТАН") >= 0)) 
            m_Tatarstan = r;
        else if ((r.getCanonicText().indexOf("УДМУРТ") >= 0)) 
            m_Udmurtia = r;
        else if ((r.getCanonicText().indexOf("ДАГЕСТАН") >= 0)) 
            m_Dagestan = r;
        if (reg.isState() && reg.isRegion()) 
            reg.addTypReg(lang);
        m_TerrOntology.addItem(r);
    }

    public static com.pullenti.ner.core.IntOntologyToken checkOntoItem(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = m_TerrOntology.tryAttach(t, null, false);
        if (li != null) {
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null) 
                    return nt;
            }
        }
        return null;
    }

    public static com.pullenti.ner.MetaToken checkKeyword(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = m_TerrOntology.tryAttach(t, null, false);
        if (li != null) {
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item == null) 
                    return nt;
            }
        }
        return null;
    }

    public static TerrItemToken _new1321(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        return res;
    }

    public static TerrItemToken _new1331(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3, com.pullenti.ner.MorphCollection _arg4) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static TerrItemToken _new1332(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3, boolean _arg4, com.pullenti.ner.MorphCollection _arg5) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        res.isAdjective = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static TerrItemToken _new1333(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, TerrTermin _arg3, boolean _arg4, com.pullenti.ner.MorphCollection _arg5) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.terminItem = _arg3;
        res.isAdjective = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static TerrItemToken _new1336(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.isDistrictName = _arg3;
        return res;
    }

    public static TerrItemToken _new1337(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.isDoubt = _arg3;
        return res;
    }

    public static TerrItemToken _new1338(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3, TerrTermin _arg4) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        res.terminItem = _arg4;
        return res;
    }

    public TerrItemToken() {
        super();
    }
    
    static {
        m_AllStates = new java.util.ArrayList<com.pullenti.ner.Referent>();
    }
}
