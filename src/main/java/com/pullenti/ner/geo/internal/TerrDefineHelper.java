/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class TerrDefineHelper {

    private static com.pullenti.ner.ReferentToken _tryAttachMoscowAO(java.util.ArrayList<TerrItemToken> li, com.pullenti.ner.core.AnalyzerData ad) {
        if (li.get(0).terminItem == null || !li.get(0).terminItem.isMoscowRegion) 
            return null;
        if (li.get(0).isDoubt) {
            boolean ok = false;
            if (CityAttachHelper.checkCityAfter(li.get(0).getEndToken().getNext())) 
                ok = true;
            else {
                java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken> ali = com.pullenti.ner.address.internal.AddressItemToken.tryParseList(li.get(0).getEndToken().getNext(), 2);
                if (ali != null && ali.size() > 0 && ali.get(0).getTyp() == com.pullenti.ner.address.internal.AddressItemType.STREET) 
                    ok = true;
            }
            if (!ok) 
                return null;
        }
        com.pullenti.ner.geo.GeoReferent reg = new com.pullenti.ner.geo.GeoReferent();
        String typ = "АДМИНИСТРАТИВНЫЙ ОКРУГ";
        reg.addTyp(typ);
        String name = li.get(0).terminItem.getCanonicText();
        if (com.pullenti.morph.LanguageHelper.endsWith(name, typ)) 
            name = name.substring(0, 0 + name.length() - typ.length() - 1).trim();
        reg.addName(name);
        return new com.pullenti.ner.ReferentToken(reg, li.get(0).getBeginToken(), li.get(0).getEndToken(), null);
    }

    public static com.pullenti.ner.ReferentToken tryDefine(java.util.ArrayList<TerrItemToken> li, com.pullenti.ner.core.AnalyzerData ad, boolean attachAlways, java.util.ArrayList<CityItemToken> cits, java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> exists) {
        if (li == null || li.size() == 0) 
            return null;
        TerrItemToken exObj = null;
        TerrItemToken newName = null;
        java.util.ArrayList<TerrItemToken> adjList = new java.util.ArrayList<TerrItemToken>();
        TerrItemToken noun = null;
        TerrItemToken addNoun = null;
        com.pullenti.ner.ReferentToken rt = _tryAttachMoscowAO(li, ad);
        if (rt != null) 
            return rt;
        boolean canBeCityBefore = false;
        boolean adjTerrBefore = false;
        if (cits != null) {
            if (cits.get(0).typ == CityItemToken.ItemType.CITY) 
                canBeCityBefore = true;
            else if (cits.get(0).typ == CityItemToken.ItemType.NOUN && cits.size() > 1) 
                canBeCityBefore = true;
        }
        int k;
        for (k = 0; k < li.size(); k++) {
            if (li.get(k).ontoItem != null) {
                if (exObj != null || newName != null) 
                    break;
                if (noun != null) {
                    if (k == 1) {
                        if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "РАЙОН") || com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "ОБЛАСТЬ") || com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "СОЮЗ")) {
                            if (li.get(k).ontoItem.referent instanceof com.pullenti.ner.geo.GeoReferent) {
                                if (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(k).ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) 
                                    break;
                            }
                            boolean ok = false;
                            com.pullenti.ner.Token tt = li.get(k).getEndToken().getNext();
                            if (tt == null) 
                                ok = true;
                            else if (tt.isCharOf(",.")) 
                                ok = true;
                            if ((!ok && li.size() >= 4 && li.get(2).terminItem != null) && li.get(3).terminItem == null) 
                                ok = true;
                            if (!ok) 
                                ok = MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken(), false);
                            if (!ok) 
                                ok = MiscLocationHelper.checkGeoObjectAfter(li.get(1).getEndToken(), false, false);
                            if (!ok) {
                                com.pullenti.ner.address.internal.AddressItemToken adr = com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt, false, null, null);
                                if (adr != null) {
                                    if (adr.getTyp() == com.pullenti.ner.address.internal.AddressItemType.STREET) 
                                        ok = true;
                                }
                            }
                            if (!ok) 
                                break;
                        }
                        if (li.get(k).ontoItem != null) {
                            if (noun.getBeginToken().isValue("МО", null) || noun.getBeginToken().isValue("ЛО", null)) 
                                return null;
                        }
                    }
                }
                exObj = li.get(k);
            }
            else if (li.get(k).terminItem != null) {
                if (noun != null) 
                    break;
                if (li.get(k).terminItem.isAlwaysPrefix && k > 0) 
                    break;
                if (k > 0 && li.get(k).isDoubt) {
                    if (li.get(k).getBeginToken() == li.get(k).getEndToken() && li.get(k).getBeginToken().isValue("ЗАО", null)) 
                        break;
                }
                if (li.get(k).terminItem.isAdjective || li.get(k).isGeoInDictionary) 
                    adjList.add(li.get(k));
                else {
                    if (exObj != null) {
                        com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(exObj.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (_geo == null) 
                            break;
                        if (exObj.isAdjective && ((com.pullenti.unisharp.Utils.stringsEq(li.get(k).terminItem.getCanonicText(), "СОЮЗ") || com.pullenti.unisharp.Utils.stringsEq(li.get(k).terminItem.getCanonicText(), "ФЕДЕРАЦИЯ")))) {
                            String str = exObj.ontoItem.toString();
                            if (!(str.indexOf(li.get(k).terminItem.getCanonicText()) >= 0)) 
                                break;
                        }
                        if (com.pullenti.unisharp.Utils.stringsEq(li.get(k).terminItem.getCanonicText(), "РАЙОН") || com.pullenti.unisharp.Utils.stringsEq(li.get(k).terminItem.getCanonicText(), "ОКРУГ") || com.pullenti.unisharp.Utils.stringsEq(li.get(k).terminItem.getCanonicText(), "КРАЙ")) {
                            StringBuilder tmp = new StringBuilder();
                            for (com.pullenti.ner.Slot s : _geo.getSlots()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) 
                                    tmp.append(s.getValue()).append(";");
                            }
                            if (!(tmp.toString().toUpperCase().indexOf(li.get(k).terminItem.getCanonicText()) >= 0)) {
                                if (k != 1 || newName != null) 
                                    break;
                                newName = li.get(0);
                                newName.isAdjective = true;
                                newName.ontoItem = null;
                                exObj = null;
                            }
                        }
                    }
                    noun = li.get(k);
                    if (k == 0 && !li.get(k).isNewlineBefore()) {
                        TerrItemToken tt = TerrItemToken.tryParse(li.get(k).getBeginToken().getPrevious(), null, null);
                        if (tt != null && tt.getMorph()._getClass().isAdjective()) 
                            adjTerrBefore = true;
                    }
                }
            }
            else {
                if (exObj != null) 
                    break;
                if (newName != null) {
                    if ((k == 2 && newName == li.get(1) && li.size() == 3) && noun != null) {
                        CityItemToken cit = CityItemToken.tryParse(newName.getBeginToken(), null, false, null);
                        if (cit != null && cit.typ == CityItemToken.ItemType.NOUN && cit.getEndToken().getNext() == li.get(k).getBeginToken()) {
                            if ((noun.terminItem.getCanonicText().indexOf("МУНИЦИПАЛ") >= 0)) {
                                CityItemToken nam = CityItemToken.tryParse(cit.getEndToken().getNext(), null, false, null);
                                if (nam != null && ((nam.typ == CityItemToken.ItemType.PROPERNAME || nam.typ == CityItemToken.ItemType.CITY))) {
                                    if (nam.getEndToken().getNext() != null && nam.getEndToken().getNext().isAnd()) {
                                        java.util.ArrayList<TerrItemToken> li2 = TerrItemToken.tryParseList(nam.getEndToken().getNext().getNext(), 4, null);
                                        if (li2 != null && li2.size() >= 2 && ((li2.get(0).terminItem != null || li2.get(1).terminItem != null))) {
                                            com.pullenti.ner.geo.GeoReferent geo1 = new com.pullenti.ner.geo.GeoReferent();
                                            com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(geo1, li.get(0).getBeginToken(), li2.get(1).getEndToken(), null);
                                            geo1.addTyp(noun.terminItem.getCanonicText());
                                            geo1.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, com.pullenti.ner.core.MiscHelper.getTextValue(li.get(1).getBeginToken(), rt1.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), false, 0);
                                            return rt1;
                                        }
                                    }
                                }
                            }
                        }
                        else 
                            break;
                    }
                    else 
                        break;
                }
                newName = li.get(k);
            }
        }
        String name = null;
        String altName = null;
        String fullName = null;
        com.pullenti.ner.MorphCollection _morph = null;
        if (exObj != null && noun != null) {
            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(exObj.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class);
            if (_geo != null && !_geo.isCity() && ((_geo.isState() || (noun.terminItem.getCanonicText().length() < 3) || _geo.containsType(noun.terminItem.getCanonicText())))) {
            }
            else {
                newName = exObj;
                exObj = null;
            }
        }
        if (exObj != null) {
            if (exObj.isAdjective && !exObj.getMorph().getLanguage().isEn() && noun == null) {
                if (attachAlways && exObj.getEndToken().getNext() != null) {
                    com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(exObj.getBeginToken());
                    if (exObj.getEndToken().getNext().isCommaAnd()) {
                    }
                    else if (npt == null) {
                    }
                    else {
                        com.pullenti.ner.address.internal.StreetItemToken str = com.pullenti.ner.address.internal.StreetItemToken.tryParse(exObj.getEndToken().getNext(), null, false, null);
                        if (str != null) {
                            if (str.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN && str.getEndToken() == npt.getEndToken()) 
                                return null;
                        }
                    }
                }
                else {
                    CityItemToken cit = CityItemToken.tryParse(exObj.getEndToken().getNext(), null, false, null);
                    if (cit != null && ((cit.typ == CityItemToken.ItemType.NOUN || cit.typ == CityItemToken.ItemType.CITY))) {
                        com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(exObj.getBeginToken());
                        if (npt != null && npt.getEndToken() == cit.getEndToken()) {
                        }
                        else 
                            return null;
                    }
                    else if (exObj.getBeginToken().isValue("ПОДНЕБЕСНЫЙ", null)) {
                    }
                    else 
                        return null;
                }
            }
            if (noun == null && ((exObj.canBeCity || exObj.canBeSurname))) {
                CityItemToken cit0 = CityItemToken.tryParseBack(exObj.getBeginToken().getPrevious(), false);
                if (cit0 != null && cit0.typ != CityItemToken.ItemType.PROPERNAME) 
                    return null;
            }
            if (exObj.isDoubt && noun == null) {
                boolean ok2 = false;
                if (_canBeGeoAfter(exObj.getEndToken().getNext())) 
                    ok2 = true;
                else if (!exObj.canBeSurname && !exObj.canBeCity) {
                    if ((exObj.getEndToken().getNext() != null && exObj.getEndToken().getNext().isChar(')') && exObj.getBeginToken().getPrevious() != null) && exObj.getBeginToken().getPrevious().isChar('(')) 
                        ok2 = true;
                    else if (exObj.chars.isLatinLetter() && exObj.getBeginToken().getPrevious() != null) {
                        if (exObj.getBeginToken().getPrevious().isValue("IN", null)) 
                            ok2 = true;
                        else if (exObj.getBeginToken().getPrevious().isValue("THE", null) && exObj.getBeginToken().getPrevious().getPrevious() != null && exObj.getBeginToken().getPrevious().getPrevious().isValue("IN", null)) 
                            ok2 = true;
                    }
                }
                if (!ok2) {
                    CityItemToken cit0 = CityItemToken.tryParseBack(exObj.getBeginToken().getPrevious(), false);
                    if (cit0 != null && cit0.typ != CityItemToken.ItemType.PROPERNAME) {
                    }
                    else if (MiscLocationHelper.checkGeoObjectBefore(exObj.getBeginToken().getPrevious(), false)) {
                    }
                    else 
                        return null;
                }
            }
            name = exObj.ontoItem.getCanonicText();
            _morph = exObj.getMorph();
        }
        else if (newName != null) {
            if (noun == null) 
                return null;
            for (int j = 1; j < k; j++) {
                if (li.get(j).isNewlineBefore() && ((!li.get(0).isNewlineBefore() || !li.get(j).isNewlineAfter()))) {
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(li.get(j).getBeginToken(), false, false)) {
                    }
                    else {
                        if (j < (k - 1)) 
                            return null;
                        if (li.size() == 2 && k == 2) {
                        }
                        else {
                            java.util.ArrayList<TerrItemToken> li2 = new java.util.ArrayList<TerrItemToken>(li);
                            for(int jjj = 0 + k - 1, mmm = 0; jjj >= mmm; jjj--) li2.remove(jjj);
                            com.pullenti.ner.ReferentToken rt2 = tryDefine(li2, ad, false, null, null);
                            if (rt2 != null && rt2.getEndToken() == li2.get(li2.size() - 1).getEndToken()) {
                            }
                            else 
                                return null;
                        }
                    }
                }
            }
            _morph = noun.getMorph();
            if (newName.isAdjective) {
                if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.acronym, "АО")) {
                    if (noun.getBeginToken() != noun.getEndToken()) 
                        return null;
                    if (newName.getMorph().getGender() != com.pullenti.morph.MorphGender.FEMINIE) 
                        return null;
                }
                com.pullenti.ner.geo.GeoReferent geoBefore = null;
                com.pullenti.ner.Token tt0 = li.get(0).getBeginToken().getPrevious();
                if (tt0 != null && tt0.isCommaAnd()) 
                    tt0 = tt0.getPrevious();
                if (!li.get(0).isNewlineBefore() && tt0 != null) 
                    geoBefore = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt0.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (li.indexOf(noun) < li.indexOf(newName)) {
                    if (noun.terminItem.isState) 
                        return null;
                    if (newName.canBeSurname && geoBefore == null) {
                        if (((com.pullenti.morph.MorphCase.ooBitand(noun.getMorph().getCase(), newName.getMorph().getCase()))).isUndefined()) 
                            return null;
                    }
                    boolean dontCheck = false;
                    if (com.pullenti.ner.core.MiscHelper.isExistsInDictionary(newName.getBeginToken(), newName.getEndToken(), com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.PRONOUN, com.pullenti.morph.MorphClass.VERB)))) {
                        if (noun.getBeginToken() != newName.getBeginToken()) {
                            if (geoBefore == null) {
                                if (li.size() == 2 && _canBeGeoAfter(li.get(1).getEndToken().getNext())) {
                                }
                                else if (li.size() == 3 && li.get(2).terminItem != null && _canBeGeoAfter(li.get(2).getEndToken().getNext())) {
                                }
                                else if (li.size() == 4 && li.get(2).terminItem != null && li.get(3).terminItem == null) {
                                }
                                else if (newName.isGeoInDictionary) {
                                }
                                else if (newName.getEndToken().isNewlineAfter()) {
                                }
                                else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(newName.getEndToken().getNext(), false)) {
                                }
                                else if (newName.getEndToken().getNext() != null && newName.getEndToken().getNext().isValue("КИЛОМЕТР", null)) {
                                }
                                else if (OrgItemToken.tryParse(newName.getEndToken().getNext(), null) != null) {
                                }
                                else 
                                    return null;
                                dontCheck = true;
                            }
                        }
                    }
                    if (!dontCheck) {
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(newName.getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS, 0, null);
                        if (npt != null && npt.getEndToken() != newName.getEndToken()) {
                            if (li.size() == 3 && li.get(2).terminItem != null && npt.getEndToken() == li.get(2).getEndToken()) 
                                addNoun = li.get(2);
                            else if (li.size() == 4 && li.get(2).terminItem != null && npt.getEndToken() == li.get(2).getEndToken()) {
                            }
                            else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(npt.getEndToken(), false)) {
                            }
                            else if (MiscLocationHelper.checkGeoObjectAfter(newName.getEndToken(), false, false)) {
                            }
                            else if (npt.getEndToken().isValue("КИЛОМЕТР", null)) {
                            }
                            else if (OrgItemToken.tryParse(newName.getEndToken().getNext(), null) != null) {
                            }
                            else 
                                return null;
                        }
                    }
                    com.pullenti.ner.ReferentToken rtp = newName.kit.processReferent("PERSON", newName.getBeginToken(), null);
                    if (rtp != null) 
                        return null;
                    name = com.pullenti.ner.core.ProperNameHelper.getNameEx(newName.getBeginToken(), newName.getEndToken(), com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphCase.UNDEFINED, noun.terminItem.getGender(), false, false);
                }
                else {
                    boolean ok = false;
                    if (((k + 1) < li.size()) && li.get(k).terminItem == null && li.get(k + 1).terminItem != null) 
                        ok = true;
                    else if ((k < li.size()) && li.get(k).ontoItem != null) 
                        ok = true;
                    else if (k == li.size() && !newName.isAdjInDictionary) {
                        ok = true;
                        if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "ТЕРРИТОРИЯ")) {
                            CityItemToken cit1 = CityItemToken.tryParseBack(li.get(0).getBeginToken().getPrevious(), false);
                            if (cit1 != null && cit1.typ == CityItemToken.ItemType.NOUN) 
                                return null;
                        }
                    }
                    else if (MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken(), false) || canBeCityBefore) 
                        ok = true;
                    else if (MiscLocationHelper.checkGeoObjectAfter(li.get(k - 1).getEndToken(), false, false)) 
                        ok = true;
                    else if (li.size() == 3 && k == 2) {
                        CityItemToken cit = CityItemToken.tryParse(li.get(2).getBeginToken(), null, false, null);
                        if (cit != null) {
                            if (cit.typ == CityItemToken.ItemType.CITY || cit.typ == CityItemToken.ItemType.NOUN) 
                                ok = true;
                        }
                    }
                    else if (li.size() == 2) 
                        ok = _canBeGeoAfter(li.get(li.size() - 1).getEndToken().getNext());
                    if (!ok && !li.get(0).isNewlineBefore() && !li.get(0).chars.isAllLower()) {
                        com.pullenti.ner.ReferentToken rt00 = li.get(0).kit.processReferent("PERSONPROPERTY", li.get(0).getBeginToken().getPrevious(), null);
                        if (rt00 != null) 
                            ok = true;
                    }
                    if (noun.terminItem != null && noun.terminItem.isStrong && newName.isAdjective) 
                        ok = true;
                    if (noun.isDoubt && adjList.size() == 0 && geoBefore == null) 
                        return null;
                    name = com.pullenti.ner.core.ProperNameHelper.getNameEx(newName.getBeginToken(), newName.getEndToken(), com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphCase.UNDEFINED, noun.terminItem.getGender(), false, false);
                    if (!ok && !attachAlways) {
                        if (com.pullenti.ner.core.MiscHelper.isExistsInDictionary(newName.getBeginToken(), newName.getEndToken(), com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.PRONOUN, com.pullenti.morph.MorphClass.VERB)))) {
                            if (exists != null) {
                                for (com.pullenti.ner.geo.GeoReferent e : exists) {
                                    if (e.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, name, true) != null) {
                                        ok = true;
                                        break;
                                    }
                                }
                            }
                            if (!ok) 
                                return null;
                        }
                    }
                    fullName = (com.pullenti.ner.core.ProperNameHelper.getNameEx(li.get(0).getBeginToken(), noun.getBeginToken().getPrevious(), com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphCase.UNDEFINED, noun.terminItem.getGender(), false, false) + " " + noun.terminItem.getCanonicText());
                }
            }
            else {
                if (!attachAlways || ((noun.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "ФЕДЕРАЦИЯ")))) {
                    boolean isLatin = noun.chars.isLatinLetter() && newName.chars.isLatinLetter();
                    if (li.indexOf(noun) > li.indexOf(newName)) {
                        if (!isLatin) 
                            return null;
                    }
                    if (!newName.isDistrictName && !com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(newName.getBeginToken(), false, false)) {
                        if (adjList.size() == 0 && com.pullenti.ner.core.MiscHelper.isExistsInDictionary(newName.getBeginToken(), newName.getEndToken(), com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN))) {
                            if (li.size() == 2 && noun.isCityRegion() && (noun.getWhitespacesAfterCount() < 2)) {
                            }
                            else if (MiscLocationHelper.checkGeoObjectAfter(newName.getEndToken(), false, false)) {
                            }
                            else if (li.size() < 3) 
                                return null;
                            else {
                                int ii = li.indexOf(newName);
                                java.util.ArrayList<TerrItemToken> li2 = new java.util.ArrayList<TerrItemToken>(li);
                                for(int jjj = 0 + ii + 1 - 1, mmm = 0; jjj >= mmm; jjj--) li2.remove(jjj);
                                com.pullenti.ner.ReferentToken rt2 = tryDefine(li2, ad, false, null, null);
                                if (rt2 == null) 
                                    return null;
                            }
                        }
                        if (!isLatin) {
                            if ((noun.terminItem.isRegion && !attachAlways && ((!adjTerrBefore || newName.isDoubt))) && !noun.isCityRegion() && !noun.terminItem.isSpecificPrefix) {
                                if (!MiscLocationHelper.checkGeoObjectBefore(noun.getBeginToken(), false)) {
                                    if (!noun.isDoubt && noun.getBeginToken() != noun.getEndToken()) {
                                    }
                                    else if ((noun.terminItem.isAlwaysPrefix && li.size() == 2 && li.get(0) == noun) && li.get(1) == newName) {
                                    }
                                    else 
                                        return null;
                                }
                            }
                            if (noun.isDoubt && adjList.size() == 0) {
                                if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.acronym, "МО") || com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.acronym, "ЛО")) {
                                    if (k == (li.size() - 1) && li.get(k).terminItem != null) {
                                        addNoun = li.get(k);
                                        k++;
                                    }
                                    else if (li.size() == 2 && noun == li.get(0) && newName.toString().endsWith("совет")) {
                                    }
                                    else 
                                        return null;
                                }
                                else 
                                    return null;
                            }
                            if (newName.getBeginToken().isValue("КОРОЛЕВ", null)) {
                            }
                            else {
                                com.pullenti.ner.ReferentToken pers = newName.kit.processReferent("PERSON", newName.getBeginToken(), null);
                                if (pers != null) 
                                    return null;
                            }
                        }
                    }
                }
                name = com.pullenti.ner.core.MiscHelper.getTextValue(newName.getBeginToken(), newName.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (newName.getBeginToken() != newName.getEndToken()) {
                    for (com.pullenti.ner.Token ttt = newName.getBeginToken().getNext(); ttt != null && ttt.getEndChar() <= newName.getEndChar(); ttt = ttt.getNext()) {
                        if (ttt.chars.isLetter()) {
                            TerrItemToken ty = TerrItemToken.tryParse(ttt, null, null);
                            if ((ty != null && ty.terminItem != null && noun != null) && (((ty.terminItem.getCanonicText().indexOf(noun.terminItem.getCanonicText()) >= 0) || (noun.terminItem.getCanonicText().indexOf(ty.terminItem.getCanonicText()) >= 0)))) {
                                name = com.pullenti.ner.core.MiscHelper.getTextValue(newName.getBeginToken(), ttt.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                                break;
                            }
                        }
                    }
                }
                if (adjList.size() > 0) {
                    com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(adjList.get(0).getBeginToken());
                    if (npt != null && npt.getEndToken() == noun.getEndToken()) 
                        altName = (npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false) + " " + name);
                }
            }
        }
        else {
            if ((li.size() == 1 && noun != null && noun.getEndToken().getNext() != null) && (noun.getEndToken().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(noun.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (noun.terminItem != null) {
                    String tyy = noun.terminItem.getCanonicText().toLowerCase();
                    boolean ooo = false;
                    if (g.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, tyy, true) != null) 
                        ooo = true;
                    else if (tyy.endsWith("район") && g.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "район", true) != null) 
                        ooo = true;
                    if (ooo) 
                        return com.pullenti.ner.ReferentToken._new786(g, noun.getBeginToken(), noun.getEndToken().getNext(), noun.getBeginToken().getMorph());
                }
            }
            if ((li.size() == 1 && noun == li.get(0) && li.get(0).terminItem != null) && TerrItemToken.tryParse(li.get(0).getEndToken().getNext(), null, null) == null && TerrItemToken.tryParse(li.get(0).getBeginToken().getPrevious(), null, null) == null) {
                if (li.get(0).getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    return null;
                int cou = 0;
                String str = li.get(0).terminItem.getCanonicText().toLowerCase();
                for (com.pullenti.ner.Token tt = li.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isNewlineAfter()) 
                        cou += 10;
                    else 
                        cou++;
                    if (cou > 500) 
                        break;
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    if (g == null) 
                        continue;
                    boolean ok = true;
                    cou = 0;
                    for (tt = li.get(0).getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isNewlineBefore()) 
                            cou += 10;
                        else 
                            cou++;
                        if (cou > 500) 
                            break;
                        TerrItemToken tee = TerrItemToken.tryParse(tt, null, null);
                        if (tee == null) 
                            continue;
                        ok = false;
                        break;
                    }
                    if (ok) {
                        for (int ii = 0; g != null && (ii < 3); g = g.getHigher(),ii++) {
                            if (g.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, str, true) != null) 
                                return com.pullenti.ner.ReferentToken._new786(g, li.get(0).getBeginToken(), li.get(0).getEndToken(), noun.getBeginToken().getMorph());
                        }
                    }
                    break;
                }
            }
            return null;
        }
        com.pullenti.ner.geo.GeoReferent ter = null;
        if (exObj != null && (exObj.tag instanceof com.pullenti.ner.geo.GeoReferent)) 
            ter = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(exObj.tag, com.pullenti.ner.geo.GeoReferent.class);
        else {
            ter = new com.pullenti.ner.geo.GeoReferent();
            if (exObj != null) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(exObj.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class);
                if (_geo != null && !_geo.isCity() && (((_geo.isState() || noun == null || (noun.terminItem.getCanonicText().length() < 3)) || _geo.containsType(noun.terminItem.getCanonicText())))) 
                    ter.mergeSlots2(_geo, li.get(0).kit.baseLanguage);
                else 
                    ter.addName(name);
                if (noun == null && exObj.canBeCity) 
                    ter.addTypCity(li.get(0).kit.baseLanguage);
                else {
                }
            }
            else if (newName != null) {
                ter.addName(name);
                if (altName != null) 
                    ter.addName(altName);
            }
            if (noun != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "АО")) 
                    ter.addTyp((li.get(0).kit.baseLanguage.isUa() ? "АВТОНОМНИЙ ОКРУГ" : "АВТОНОМНЫЙ ОКРУГ"));
                else if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "МУНИЦИПАЛЬНОЕ СОБРАНИЕ") || com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "МУНІЦИПАЛЬНЕ ЗБОРИ")) 
                    ter.addTyp((li.get(0).kit.baseLanguage.isUa() ? "МУНІЦИПАЛЬНЕ УТВОРЕННЯ" : "МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ"));
                else if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.acronym, "МО") && addNoun != null) 
                    ter.addTyp(addNoun.terminItem.getCanonicText());
                else {
                    if (com.pullenti.unisharp.Utils.stringsEq(noun.terminItem.getCanonicText(), "СОЮЗ") && exObj != null && exObj.getEndChar() > noun.getEndChar()) 
                        return com.pullenti.ner.ReferentToken._new786(ter, exObj.getBeginToken(), exObj.getEndToken(), exObj.getMorph());
                    ter.addTyp(noun.terminItem.getCanonicText());
                    if (noun.terminItem.isRegion && ter.isState()) 
                        ter.addTypReg(li.get(0).kit.baseLanguage);
                }
                if (noun.terminItem2 != null) 
                    ter.addTyp(noun.terminItem2.getCanonicText());
            }
            if (ter.isState() && ter.isRegion()) {
                for (TerrItemToken a : adjList) {
                    if (a.terminItem.isRegion) {
                        ter.addTypReg(li.get(0).kit.baseLanguage);
                        break;
                    }
                }
            }
            if (ter.isState()) {
                if (fullName != null) 
                    ter.addName(fullName);
            }
        }
        if (noun == null) {
            while (k > 1 && adjList.contains(li.get(k - 1))) {
                k--;
            }
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(ter, li.get(0).getBeginToken(), li.get(k - 1).getEndToken(), null);
        if (noun != null && noun.getMorph()._getClass().isNoun()) 
            res.setMorph(noun.getMorph());
        else {
            res.setMorph(new com.pullenti.ner.MorphCollection(null));
            for (int ii = 0; ii < k; ii++) {
                for (com.pullenti.morph.MorphBaseInfo v : li.get(ii).getMorph().getItems()) {
                    com.pullenti.morph.MorphBaseInfo bi = new com.pullenti.morph.MorphBaseInfo();
                    bi.copyFrom(v);
                    if (noun != null) {
                        if (bi._getClass().isAdjective()) 
                            bi._setClass(com.pullenti.morph.MorphClass.NOUN);
                    }
                    res.getMorph().addItem(bi);
                }
            }
        }
        if (li.get(0).terminItem != null && li.get(0).terminItem.isSpecificPrefix) 
            res.setBeginToken(li.get(0).getEndToken().getNext());
        if (addNoun != null && addNoun.getEndChar() > res.getEndChar()) 
            res.setEndToken(addNoun.getEndToken());
        if ((res.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && (res.getWhitespacesBeforeCount() < 2)) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class);
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "АР")) {
                for (String ty : ter.getTyps()) {
                    if ((ty.indexOf("республика") >= 0) || (ty.indexOf("республіка") >= 0)) {
                        res.setBeginToken(tt);
                        break;
                    }
                }
            }
        }
        if (li.size() == 3 && li.get(1) == noun) {
            if (li.get(2).terminItem == noun.terminItem) 
                res.setEndToken(li.get(2).getEndToken());
        }
        if (noun != null && (noun.terminItem.getCanonicText().indexOf("МЕЖСЕЛЕН") >= 0)) {
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            if (tt != null && tt.isComma()) 
                tt = tt.getNext();
            if ((tt != null && tt.isValue("НАХОДИТЬСЯ", null) && tt.getNext() != null) && tt.getNext().isValue("ВНЕ", null)) {
                for (tt = tt.getNext().getNext(); tt != null; tt = tt.getNext()) {
                    com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                    if (!mc.isNoun()) 
                        continue;
                    if (tt.isValue("ГРАНИЦА", null) || tt.isValue("ПРЕДЕЛ", null) || tt.isValue("ПОСЕЛЕНИЕ", null)) 
                        res.setEndToken(tt);
                    else if (!mc.isAdjective()) 
                        break;
                }
            }
        }
        return res;
    }

    private static boolean _canBeGeoAfter(com.pullenti.ner.Token tt) {
        while (tt != null && ((tt.isComma() || com.pullenti.ner.core.BracketHelper.isBracket(tt, true)))) {
            tt = tt.getNext();
        }
        if (tt == null) 
            return false;
        if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
            return true;
        java.util.ArrayList<TerrItemToken> tli = TerrItemToken.tryParseList(tt, 2, null);
        if (tli != null && tli.size() > 1) {
            if (tli.get(0).terminItem == null && tli.get(1).terminItem != null) 
                return true;
            else if (tli.get(0).terminItem != null && tli.get(1).terminItem == null) 
                return true;
        }
        if (CityAttachHelper.checkCityAfter(tt)) 
            return true;
        if (tryAttachStateUSATerritory(tt) != null) 
            return true;
        return false;
    }

    public static com.pullenti.ner.ReferentToken tryAttachStateUSATerritory(com.pullenti.ner.Token t) {
        if (t == null || !t.chars.isLatinLetter()) 
            return null;
        com.pullenti.ner.core.TerminToken tok = TerrItemToken.m_GeoAbbrs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.geo.GeoReferent.class);
        if (g == null) 
            return null;
        if (tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isChar('.')) 
            tok.setEndToken(tok.getEndToken().getNext());
        com.pullenti.ner.Referent gg = g.clone();
        gg.getOccurrence().clear();
        return new com.pullenti.ner.ReferentToken(gg, tok.getBeginToken(), tok.getEndToken(), null);
    }
    public TerrDefineHelper() {
    }
}
