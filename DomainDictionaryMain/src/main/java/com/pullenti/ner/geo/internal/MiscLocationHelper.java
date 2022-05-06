/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class MiscLocationHelper {

    public static void prepareAllData(com.pullenti.ner.Token t0) {
    }

    public static com.pullenti.ner.core.NounPhraseToken tryParseNpt(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        return com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
    }

    public static com.pullenti.ner.Token checkTerritory(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Terrs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        if (t.isValue("САД", null)) {
            OrgTypToken oo = OrgTypToken.tryParse(t, false, null);
            if (oo != null) 
                return null;
        }
        com.pullenti.ner.Token tt2 = tok.getEndToken();
        if (tt2.getNext() != null && tt2.getNext().isValue("ВЛАДЕНИЕ", null)) 
            tt2 = tt2.getNext();
        com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(tt2.getNext());
        if (npt != null && npt.getEndToken().isValue("ЗЕМЛЯ", null)) 
            tt2 = npt.getEndToken();
        return tt2;
    }

    public static boolean checkGeoObjectBefore(com.pullenti.ner.Token t, boolean pureGeo) {
        if (t == null) 
            return false;
        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if ((tt.isCharOf(",.;:") || tt.isHiphen() || tt.isAnd()) || tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if (m_Terrs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                continue;
            if (m_GeoBefore.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                return true;
            if (tt.getLengthChar() == 2 && (tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllUpper()) {
                String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(term) && term.charAt(0) == 'Р') 
                    return true;
            }
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt != null) {
                if (rt.referent instanceof com.pullenti.ner.geo.GeoReferent) 
                    return true;
                if (!pureGeo) {
                    if ((rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                        return true;
                }
            }
            break;
        }
        return false;
    }

    public static boolean checkGeoObjectBeforeBrief(com.pullenti.ner.Token t, GeoAnalyzerData ad) {
        if (t == null) 
            return false;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return false;
        int miss = 0;
        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if (tt.isNewlineAfter()) 
                break;
            if (tt.isCharOf(",.;") || tt.isHiphen() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (checkTerritory(tt) != null) 
                return true;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt != null) {
                if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) || (rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                    return true;
                break;
            }
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, GeoTokenData.class);
            if (d != null) {
                if (d.cit != null && ((d.cit.typ == CityItemToken.ItemType.NOUN || d.cit.typ == CityItemToken.ItemType.CITY))) 
                    return true;
                if (d.terr != null && ((d.terr.terminItem != null || d.terr.ontoItem != null))) 
                    return true;
                if (d.street != null && d.street.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN && d.street.nounIsDoubtCoef == 0) 
                    return true;
            }
            if ((++miss) > 2) 
                break;
        }
        return false;
    }

    public static boolean checkGeoObjectAfterBrief(com.pullenti.ner.Token t, GeoAnalyzerData ad) {
        if (t == null) 
            return false;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return false;
        int miss = 0;
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            if (tt.isCharOf(",.;") || tt.isHiphen() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (checkTerritory(tt) != null) 
                return true;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt != null) {
                if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) || (rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                    return true;
                break;
            }
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, GeoTokenData.class);
            if (d != null) {
                if (d.cit != null && ((d.cit.typ == CityItemToken.ItemType.NOUN || d.cit.typ == CityItemToken.ItemType.CITY))) 
                    return true;
                if (d.terr != null && ((d.terr.terminItem != null || d.terr.ontoItem != null))) 
                    return true;
                if (d.street != null && d.street.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN && d.street.nounIsDoubtCoef == 0) 
                    return true;
            }
            if (CityItemToken.checkKeyword(tt) != null) 
                return true;
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllLower()) {
                if (!tt.getMorph()._getClass().isPreposition()) 
                    break;
            }
            miss++;
            if (miss > 4) 
                break;
        }
        return false;
    }

    public static boolean checkGeoObjectAfter(com.pullenti.ner.Token t, boolean dontCheckCity, boolean checkTerr) {
        if (t == null) 
            return false;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isCharOf(",.;") || tt.isHiphen() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition()) {
                if (!dontCheckCity && tt.isValue("С", null) && tt.getNext() != null) {
                    com.pullenti.ner.Token ttt = tt.getNext();
                    if (ttt.isChar('.') && (ttt.getWhitespacesAfterCount() < 3)) 
                        ttt = ttt.getNext();
                    java.util.ArrayList<CityItemToken> cits = CityItemToken.tryParseList(ttt, 3, null);
                    if (cits != null && cits.size() == 1 && ((cits.get(0).typ == CityItemToken.ItemType.PROPERNAME || cits.get(0).typ == CityItemToken.ItemType.CITY))) {
                        if (tt.chars.isAllUpper() && !cits.get(0).chars.isAllUpper()) {
                        }
                        else 
                            return true;
                    }
                }
                continue;
            }
            if (checkTerritory(tt) != null) 
                return true;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt == null) {
                if (!dontCheckCity && cou == 0) {
                    java.util.ArrayList<CityItemToken> cits = CityItemToken.tryParseList(tt, 3, null);
                    if ((cits != null && cits.size() >= 2 && cits.get(0).typ == CityItemToken.ItemType.NOUN) && ((cits.get(1).typ == CityItemToken.ItemType.PROPERNAME || cits.get(1).typ == CityItemToken.ItemType.CITY))) {
                        if (cits.get(0).chars.isAllUpper() && !cits.get(1).chars.isAllUpper()) {
                        }
                        else 
                            return true;
                    }
                    if (cits != null && cits.get(0).typ == CityItemToken.ItemType.NOUN && (cits.get(0).getWhitespacesAfterCount() < 3)) {
                        if (OrgItemToken.tryParse(cits.get(0).getEndToken().getNext(), null) != null) 
                            return true;
                    }
                }
                if (checkTerr && cou == 0) {
                    java.util.ArrayList<TerrItemToken> ters = TerrItemToken.tryParseList(tt, 4, null);
                    if (ters != null) {
                        if (ters.size() == 2 && (ters.get(0).getWhitespacesAfterCount() < 3)) {
                            if (ters.get(0).terminItem != null && ters.get(1).terminItem == null && ters.get(1).ontoItem == null) 
                                return true;
                            if (ters.get(1).terminItem != null && ters.get(0).terminItem == null) 
                                return true;
                        }
                        if (ters.size() == 1 && ters.get(0).ontoItem != null) 
                            return true;
                    }
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() > 2 && cou == 0) {
                    cou++;
                    continue;
                }
                else 
                    break;
            }
            if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) || (rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                return true;
            break;
        }
        return false;
    }

    public static com.pullenti.ner.Token checkNearBefore(com.pullenti.ner.Token t, GeoAnalyzerData ad) {
        if (t == null || t.getPrevious() == null) 
            return null;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (cou < 5); tt = tt.getPrevious(),cou++) {
            if (tt.getMorph()._getClass().isPreposition() && (cou < 2)) {
                if (m_Near.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                    return tt;
            }
            com.pullenti.ner.address.internal.AddressItemToken ait = com.pullenti.ner.address.internal.AddressItemToken.tryParsePureItem(tt, null, ad);
            if (ait != null && ait.getTyp() == com.pullenti.ner.address.internal.AddressItemType.DETAIL) 
                return tt;
        }
        return null;
    }

    public static com.pullenti.ner.Token checkUnknownRegion(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(t);
        if (npt == null) 
            return null;
        if (TerrItemToken.m_UnknownRegions.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.FULLWORDSONLY) != null) 
            return npt.getEndToken();
        return null;
    }

    public static java.util.ArrayList<String> getStdAdjFull(com.pullenti.ner.Token t, com.pullenti.morph.MorphGender gen, com.pullenti.morph.MorphNumber num, boolean strict) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        return getStdAdjFullStr(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, gen, num, strict);
    }

    public static java.util.ArrayList<String> getStdAdjFullStr(String v, com.pullenti.morph.MorphGender gen, com.pullenti.morph.MorphNumber num, boolean strict) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        if (v.startsWith("Б")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("БОЛЬШИЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("БОЛЬШИЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("БОЛЬШАЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("БОЛЬШОЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("БОЛЬШОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("М")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("МАЛЫЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("МАЛЫЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("МАЛАЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("МАЛЫЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("МАЛОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("В")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("ВЕРХНИЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("ВЕРХНИЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("ВЕРХНЯЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("ВЕРХНИЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("ВЕРХНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(v, "Н")) {
            java.util.ArrayList<String> r1 = getStdAdjFullStr("НОВ", gen, num, strict);
            java.util.ArrayList<String> r2 = getStdAdjFullStr("НИЖ", gen, num, strict);
            if (r1 == null && r2 == null) 
                return null;
            if (r1 == null) 
                return r2;
            if (r2 == null) 
                return r1;
            r1.add(1, r2.get(0));
            r2.remove(0);
            com.pullenti.unisharp.Utils.addToArrayList(r1, r2);
            return r1;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(v, "С") || com.pullenti.unisharp.Utils.stringsEq(v, "C")) {
            java.util.ArrayList<String> r1 = getStdAdjFullStr("СТ", gen, num, strict);
            java.util.ArrayList<String> r2 = getStdAdjFullStr("СР", gen, num, strict);
            if (r1 == null && r2 == null) 
                return null;
            if (r1 == null) 
                return r2;
            if (r2 == null) 
                return r1;
            r1.add(1, r2.get(0));
            r2.remove(0);
            com.pullenti.unisharp.Utils.addToArrayList(r1, r2);
            return r1;
        }
        if (v.startsWith("НОВ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("НОВЫЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("НОВЫЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("НОВАЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("НОВЫЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("НОВОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("НИЖ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("НИЖНИЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("НИЖНИЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("НИЖНЯЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("НИЖНИЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("НИЖНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("СТ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("СТАРЫЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("СТАРЫЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("СТАРАЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("СТАРЫЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("СТАРОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("СР")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("СРЕДНИЕ");
                return res;
            }
            if (!strict && ((short)((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("СРЕДНИЕ");
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("СРЕДНЯЯ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("СРЕДНИЙ");
            }
            if (((short)((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("СРЕДНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        return null;
    }

    public static com.pullenti.ner.geo.GeoReferent getGeoReferentByName(String name) {
        com.pullenti.ner.geo.GeoReferent res = null;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent> wrapres1246 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent>();
        boolean inoutres1247 = com.pullenti.unisharp.Utils.tryGetValue(m_GeoRefByName, name, wrapres1246);
        res = wrapres1246.value;
        if (inoutres1247) 
            return res;
        for (com.pullenti.ner.Referent r : TerrItemToken.m_AllStates) {
            if (r.findSlot(null, name, true) != null) {
                res = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
                break;
            }
        }
        m_GeoRefByName.put(name, res);
        return res;
    }

    private static java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent> m_GeoRefByName;

    public static com.pullenti.ner.MetaToken tryAttachNordWest(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Nords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        com.pullenti.ner.MetaToken res = com.pullenti.ner.MetaToken._new527(t, t, t.getMorph());
        com.pullenti.ner.Token t1 = null;
        if ((t.getNext() != null && t.getNext().isHiphen() && !t.isWhitespaceAfter()) && !t.isWhitespaceAfter()) 
            t1 = t.getNext().getNext();
        else if (t.getMorph()._getClass().isAdjective() && (t.getWhitespacesAfterCount() < 2)) 
            t1 = t.getNext();
        if (t1 != null) {
            if ((((tok = m_Nords.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                res.setEndToken(tok.getEndToken());
                res.setMorph(tok.getMorph());
            }
        }
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_Terrs;

    private static com.pullenti.ner.core.TerminCollection m_GeoBefore;

    private static com.pullenti.ner.core.TerminCollection m_Near;

    public static void initialize() {
        if (m_Nords != null) 
            return;
        m_Nords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"СЕВЕРНЫЙ", "ЮЖНЫЙ", "ЗАПАДНЫЙ", "ВОСТОЧНЫЙ", "ЦЕНТРАЛЬНЫЙ", "БЛИЖНИЙ", "ДАЛЬНИЙ", "СРЕДНИЙ", "СЕВЕР", "ЮГ", "ЗАПАД", "ВОСТОК", "СЕВЕРО", "ЮГО", "ЗАПАДНО", "ВОСТОЧНО", "СЕВЕРОЗАПАДНЫЙ", "СЕВЕРОВОСТОЧНЫЙ", "ЮГОЗАПАДНЫЙ", "ЮГОВОСТОЧНЫЙ"}) {
            m_Nords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
        }
        m_Near = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"У", "ОКОЛО", "ВБЛИЗИ", "ВБЛИЗИ ОТ", "НЕДАЛЕКО ОТ", "НЕПОДАЛЕКУ ОТ"}) {
            m_Near.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        m_GeoBefore = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"ПРОЖИВАТЬ", "ПРОЖИВАТИ", "РОДИТЬ", "НАРОДИТИ", "ЗАРЕГИСТРИРОВАТЬ", "ЗАРЕЄСТРУВАТИ", "АДРЕС", "УРОЖЕНЕЦ", "УРОДЖЕНЕЦЬ", "УРОЖЕНКА", "УРОДЖЕНКА"}) {
            m_GeoBefore.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        m_Terrs = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin("ТЕРРИТОРИЯ", null, false);
        t.addVariant("ТЕР", false);
        t.addVariant("ТЕРР", false);
        t.addVariant("ТЕРИТОРІЯ", false);
        t.addAbridge("ТЕР.");
        t.addAbridge("ТЕРР.");
        m_Terrs.add(t);
        m_Terrs.add(new com.pullenti.ner.core.Termin("САД", null, false));
        m_Terrs.add(new com.pullenti.ner.core.Termin("ГРАНИЦА", null, false));
        m_Terrs.add(new com.pullenti.ner.core.Termin("В ГРАНИЦАХ", null, false));
        String table = "\nAF\tAFG\nAX\tALA\nAL\tALB\nDZ\tDZA\nAS\tASM\nAD\tAND\nAO\tAGO\nAI\tAIA\nAQ\tATA\nAG\tATG\nAR\tARG\nAM\tARM\nAW\tABW\nAU\tAUS\nAT\tAUT\nAZ\tAZE\nBS\tBHS\nBH\tBHR\nBD\tBGD\nBB\tBRB\nBY\tBLR\nBE\tBEL\nBZ\tBLZ\nBJ\tBEN\nBM\tBMU\nBT\tBTN\nBO\tBOL\nBA\tBIH\nBW\tBWA\nBV\tBVT\nBR\tBRA\nVG\tVGB\nIO\tIOT\nBN\tBRN\nBG\tBGR\nBF\tBFA\nBI\tBDI\nKH\tKHM\nCM\tCMR\nCA\tCAN\nCV\tCPV\nKY\tCYM\nCF\tCAF\nTD\tTCD\nCL\tCHL\nCN\tCHN\nHK\tHKG\nMO\tMAC\nCX\tCXR\nCC\tCCK\nCO\tCOL\nKM\tCOM\nCG\tCOG\nCD\tCOD\nCK\tCOK\nCR\tCRI\nCI\tCIV\nHR\tHRV\nCU\tCUB\nCY\tCYP\nCZ\tCZE\nDK\tDNK\nDJ\tDJI\nDM\tDMA\nDO\tDOM\nEC\tECU\nEG\tEGY\nSV\tSLV\nGQ\tGNQ\nER\tERI\nEE\tEST\nET\tETH\nFK\tFLK\nFO\tFRO\nFJ\tFJI\nFI\tFIN\nFR\tFRA\nGF\tGUF\nPF\tPYF\nTF\tATF\nGA\tGAB\nGM\tGMB\nGE\tGEO\nDE\tDEU\nGH\tGHA\nGI\tGIB\nGR\tGRC\nGL\tGRL\nGD\tGRD\nGP\tGLP\nGU\tGUM\nGT\tGTM\nGG\tGGY\nGN\tGIN\nGW\tGNB\nGY\tGUY\nHT\tHTI\nHM\tHMD\nVA\tVAT\nHN\tHND\nHU\tHUN\nIS\tISL\nIN\tIND\nID\tIDN\nIR\tIRN\nIQ\tIRQ\nIE\tIRL\nIM\tIMN\nIL\tISR\nIT\tITA\nJM\tJAM\nJP\tJPN\nJE\tJEY\nJO\tJOR\nKZ\tKAZ\nKE\tKEN\nKI\tKIR\nKP\tPRK\nKR\tKOR\nKW\tKWT\nKG\tKGZ\nLA\tLAO\nLV\tLVA\nLB\tLBN\nLS\tLSO\nLR\tLBR\nLY\tLBY\nLI\tLIE\nLT\tLTU\nLU\tLUX\nMK\tMKD\nMG\tMDG\nMW\tMWI\nMY\tMYS\nMV\tMDV\nML\tMLI\nMT\tMLT\nMH\tMHL\nMQ\tMTQ\nMR\tMRT\nMU\tMUS\nYT\tMYT\nMX\tMEX\nFM\tFSM\nMD\tMDA\nMC\tMCO\nMN\tMNG\nME\tMNE\nMS\tMSR\nMA\tMAR\nMZ\tMOZ\nMM\tMMR\nNA\tNAM\nNR\tNRU\nNP\tNPL\nNL\tNLD\nAN\tANT\nNC\tNCL\nNZ\tNZL\nNI\tNIC\nNE\tNER\nNG\tNGA\nNU\tNIU\nNF\tNFK\nMP\tMNP\nNO\tNOR\nOM\tOMN\nPK\tPAK\nPW\tPLW\nPS\tPSE\nPA\tPAN\nPG\tPNG\nPY\tPRY\nPE\tPER\nPH\tPHL\nPN\tPCN\nPL\tPOL\nPT\tPRT\nPR\tPRI\nQA\tQAT\nRE\tREU\nRO\tROU\nRU\tRUS\nRW\tRWA\nBL\tBLM\nSH\tSHN\nKN\tKNA\nLC\tLCA\nMF\tMAF\nPM\tSPM\nVC\tVCT\nWS\tWSM\nSM\tSMR\nST\tSTP\nSA\tSAU\nSN\tSEN\nRS\tSRB\nSC\tSYC\nSL\tSLE\nSG\tSGP\nSK\tSVK\nSI\tSVN\nSB\tSLB\nSO\tSOM\nZA\tZAF\nGS\tSGS\nSS\tSSD\nES\tESP\nLK\tLKA\nSD\tSDN\nSR\tSUR\nSJ\tSJM\nSZ\tSWZ\nSE\tSWE\nCH\tCHE\nSY\tSYR\nTW\tTWN\nTJ\tTJK\nTZ\tTZA\nTH\tTHA\nTL\tTLS\nTG\tTGO\nTK\tTKL\nTO\tTON\nTT\tTTO\nTN\tTUN\nTR\tTUR\nTM\tTKM\nTC\tTCA\nTV\tTUV\nUG\tUGA\nUA\tUKR\nAE\tARE\nGB\tGBR\nUS\tUSA\nUM\tUMI\nUY\tURY\nUZ\tUZB\nVU\tVUT\nVE\tVEN\nVN\tVNM\nVI\tVIR\nWF\tWLF\nEH\tESH\nYE\tYEM\nZM\tZMB\nZW\tZWE ";
        for (String s : com.pullenti.unisharp.Utils.split(table, String.valueOf('\n'), false)) {
            String ss = s.trim();
            if ((ss.length() < 6) || !com.pullenti.unisharp.Utils.isWhitespace(ss.charAt(2))) 
                continue;
            String cod2 = ss.substring(0, 0 + 2);
            String cod3 = ss.substring(3).trim();
            if (cod3.length() != 3) 
                continue;
            if (!m_Alpha2_3.containsKey(cod2)) 
                m_Alpha2_3.put(cod2, cod3);
            if (!m_Alpha3_2.containsKey(cod3)) 
                m_Alpha3_2.put(cod3, cod2);
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Nords;

    public static java.util.HashMap<String, String> m_Alpha2_3;

    public static java.util.HashMap<String, String> m_Alpha3_2;

    public static byte[] deflate(byte[] zip) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.MemoryStream unzip = new com.pullenti.unisharp.MemoryStream()) {
            com.pullenti.unisharp.MemoryStream data = new com.pullenti.unisharp.MemoryStream(zip);
            data.setPosition(0L);
            com.pullenti.morph.internal.MorphDeserializer.deflateGzip(data, unzip);
            data.close();
            return unzip.toByteArray();
        }
    }

    public MiscLocationHelper() {
    }
    
    static {
        m_GeoRefByName = new java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent>();
        m_Alpha2_3 = new java.util.HashMap<String, String>();
        m_Alpha3_2 = new java.util.HashMap<String, String>();
    }
}
