/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class StreetDefineHelper {

    public static boolean checkStreetAfter(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        while (t != null && ((t.isCharOf(",;") || t.getMorph()._getClass().isPreposition()))) {
            t = t.getNext();
        }
        java.util.ArrayList<StreetItemToken> li = StreetItemToken.tryParseList(t, 10, null);
        if (li == null) 
            return false;
        AddressItemToken rt = tryParseStreet(li, false, false, false);
        if (rt != null && rt.getBeginToken() == t) 
            return true;
        else 
            return false;
    }

    public static com.pullenti.ner.ReferentToken tryParseExtStreet(java.util.ArrayList<StreetItemToken> sli) {
        AddressItemToken a = tryParseStreet(sli, true, false, false);
        if (a != null) 
            return new com.pullenti.ner.ReferentToken(a.referent, a.getBeginToken(), a.getEndToken(), null);
        return null;
    }

    public static AddressItemToken tryParseStreet(java.util.ArrayList<StreetItemToken> sli, boolean extOntoRegim, boolean forMetro, boolean streetBefore) {
        if (sli == null || sli.size() == 0) 
            return null;
        int i;
        int j;
        for (i = 0; i < sli.size(); i++) {
            if (i == 0 && sli.get(i).typ == StreetItemType.FIX && ((sli.size() == 1 || sli.get(1).typ != StreetItemType.NOUN || sli.get(0)._org != null))) 
                return _tryParseFix(sli);
            else if (sli.get(i).typ == StreetItemType.NOUN) {
                if (((i + 1) < sli.size()) && sli.get(i + 1)._org != null) 
                    return null;
                if (i == 0 && com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "УЛИЦА") && ((i + 2) < sli.size())) {
                    if (sli.get(i + 1).typ == StreetItemType.NOUN && StreetItemToken._isRegion(sli.get(i + 1).termin.getCanonicText())) {
                        sli.get(i + 1).setBeginToken(sli.get(i).getBeginToken());
                        sli.remove(i);
                    }
                    else if ((((i + 2) < sli.size()) && sli.get(i + 1).typ == StreetItemType.NUMBER && sli.get(i + 2).typ == StreetItemType.NOUN) && StreetItemToken._isRegion(sli.get(i + 2).termin.getCanonicText())) {
                        sli.get(i + 1).setBeginToken(sli.get(i).getBeginToken());
                        sli.remove(i);
                        i--;
                        continue;
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "МЕТРО")) {
                    if ((i + 1) < sli.size()) {
                        java.util.ArrayList<StreetItemToken> sli1 = new java.util.ArrayList<StreetItemToken>();
                        for (int ii = i + 1; ii < sli.size(); ii++) {
                            sli1.add(sli.get(ii));
                        }
                        AddressItemToken str1 = tryParseStreet(sli1, extOntoRegim, true, false);
                        if (str1 != null) {
                            str1.setBeginToken(sli.get(i).getBeginToken());
                            str1.isDoubt = sli.get(i).isAbridge;
                            if (sli.get(i + 1).isInBrackets) 
                                str1.isDoubt = false;
                            return str1;
                        }
                    }
                    else if (i == 1 && sli.get(0).typ == StreetItemType.NAME) {
                        forMetro = true;
                        break;
                    }
                    if (i == 0 && sli.size() > 0) {
                        forMetro = true;
                        break;
                    }
                    return null;
                }
                if (i == 0 && (i + 1) >= sli.size() && ((com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ВОЕННЫЙ ГОРОДОК") || com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПРОМЗОНА")))) {
                    com.pullenti.ner.address.StreetReferent stri0 = new com.pullenti.ner.address.StreetReferent();
                    stri0.addTyp("микрорайон");
                    stri0.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sli.get(i).termin.getCanonicText(), false, 0);
                    return AddressItemToken._new167(AddressItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), stri0, true);
                }
                if (i == 0 && (i + 1) >= sli.size() && com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "МИКРОРАЙОН")) {
                    com.pullenti.ner.address.StreetReferent stri0 = new com.pullenti.ner.address.StreetReferent();
                    stri0.setKind(com.pullenti.ner.address.StreetKind.AREA);
                    stri0.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, sli.get(i).termin.getCanonicText().toLowerCase(), false, 0);
                    return AddressItemToken._new167(AddressItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), stri0, true);
                }
                if (com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПЛОЩА")) {
                    com.pullenti.ner.Token tt = sli.get(i).getEndToken().getNext();
                    if (tt != null && ((tt.isHiphen() || tt.isChar(':')))) 
                        tt = tt.getNext();
                    com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                    if (nex != null) 
                        return null;
                }
                break;
            }
        }
        if (i >= sli.size()) 
            return tryDetectNonNoun(sli, extOntoRegim, forMetro, streetBefore);
        StreetItemToken name = null;
        String number = null;
        String age = null;
        StreetItemToken adj = null;
        StreetItemToken noun = sli.get(i);
        StreetItemToken altNoun = null;
        boolean isMicroRaion = StreetItemToken._isRegion(noun.termin.getCanonicText());
        int before = 0;
        int after = 0;
        for (j = 0; j < i; j++) {
            if (((sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) || sli.get(j).typ == StreetItemType.STDADJECTIVE || sli.get(j).typ == StreetItemType.STDPARTOFNAME) || sli.get(j).typ == StreetItemType.AGE) 
                before++;
            else if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (sli.get(j).isNewlineAfter()) 
                    return null;
                if (sli.get(j).number != null && sli.get(j).number.getMorph()._getClass().isAdjective()) 
                    before++;
                else if (isMicroRaion) 
                    before++;
                else if (sli.get(i).numberHasPrefix || sli.get(i).isNumberKm) 
                    before++;
            }
            else 
                before++;
        }
        for (j = i + 1; j < sli.size(); j++) {
            if (((sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) || sli.get(j).typ == StreetItemType.STDADJECTIVE || sli.get(j).typ == StreetItemType.STDPARTOFNAME) || sli.get(j).typ == StreetItemType.AGE) 
                after++;
            else if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (sli.get(j).number != null && sli.get(j).number.getMorph()._getClass().isAdjective()) 
                    after++;
                else if (isMicroRaion) 
                    after++;
                else if (sli.get(j).numberHasPrefix || sli.get(j).isNumberKm) 
                    after++;
                else if (extOntoRegim) 
                    after++;
                else if (sli.size() == 2 && sli.get(0).typ == StreetItemType.NOUN && j == 1) 
                    after++;
                else if ((sli.size() == 3 && sli.get(0).typ == StreetItemType.NOUN && sli.get(2).typ == StreetItemType.NOUN) && j == 1 && com.pullenti.unisharp.Utils.stringsEq(sli.get(2).termin.getCanonicText(), "ЛИНИЯ")) 
                    after++;
            }
            else if (sli.get(j).typ == StreetItemType.NOUN) 
                break;
            else 
                after++;
        }
        java.util.ArrayList<StreetItemToken> rli = new java.util.ArrayList<StreetItemToken>();
        int n0;
        int n1;
        if (before > after) {
            if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МЕТРО")) 
                return null;
            if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ") && !extOntoRegim && !streetBefore) {
                if (sli.get(0).typ == StreetItemType.NUMBER && sli.size() == 2) {
                    if (!AddressItemToken.checkHouseAfter(sli.get(1).getEndToken().getNext(), false, false)) {
                        if (!com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken(), false)) 
                            return null;
                        if (sli.get(0).getBeginToken().getPrevious() != null && sli.get(0).getBeginToken().getPrevious().getMorphClassInDictionary().isPreposition()) 
                            return null;
                    }
                }
            }
            com.pullenti.ner.Token tt = sli.get(0).getBeginToken();
            if (tt == sli.get(0).getEndToken() && noun.getBeginToken() == sli.get(0).getEndToken().getNext()) {
                if (!tt.getMorph()._getClass().isAdjective() && !(tt instanceof com.pullenti.ner.NumberToken)) {
                    if ((sli.get(0).isNewlineBefore() || !com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken(), false) || noun.getMorph().getCase().isGenitive()) || noun.getMorph().getCase().isInstrumental()) {
                        boolean ok = false;
                        if (AddressItemToken.checkHouseAfter(noun.getEndToken().getNext(), false, true)) 
                            ok = true;
                        else if (noun.getEndToken().getNext() == null) 
                            ok = true;
                        else if (noun.isNewlineAfter() && com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken(), false)) 
                            ok = true;
                        if (!ok) {
                            if ((noun.chars.isLatinLetter() && noun.chars.isCapitalUpper() && sli.get(0).chars.isLatinLetter()) && sli.get(0).chars.isCapitalUpper()) 
                                ok = true;
                        }
                        if (!ok) 
                            return null;
                    }
                }
            }
            n0 = 0;
            n1 = i - 1;
        }
        else if (i == 1 && sli.get(0).typ == StreetItemType.NUMBER) {
            if (!sli.get(0).isWhitespaceAfter()) 
                return null;
            number = (sli.get(0).number == null ? sli.get(0).value : sli.get(0).number.getIntValue().toString());
            if (sli.get(0).isNumberKm) 
                number += "км";
            n0 = i + 1;
            n1 = sli.size() - 1;
            rli.add(sli.get(0));
            rli.add(sli.get(i));
        }
        else if (after > before) {
            n0 = i + 1;
            n1 = sli.size() - 1;
            rli.add(sli.get(i));
        }
        else if (after == 0) 
            return null;
        else if ((sli.size() > 2 && ((sli.get(0).typ == StreetItemType.NAME || sli.get(0).typ == StreetItemType.STDADJECTIVE || sli.get(0).typ == StreetItemType.STDNAME)) && sli.get(1).typ == StreetItemType.NOUN) && sli.get(2).typ == StreetItemType.NUMBER) {
            n0 = 0;
            n1 = 0;
            boolean num = false;
            com.pullenti.ner.Token tt2 = sli.get(2).getEndToken().getNext();
            if (sli.get(2).isNumberKm) 
                num = true;
            else if (sli.get(0).getBeginToken().getPrevious() != null && sli.get(0).getBeginToken().getPrevious().isValue("КИЛОМЕТР", null)) {
                sli.get(2).isNumberKm = true;
                num = true;
            }
            else if (sli.get(2).getBeginToken().getPrevious().isComma()) {
            }
            else if (sli.get(2).getBeginToken() != sli.get(2).getEndToken()) 
                num = true;
            else if (AddressItemToken.checkHouseAfter(sli.get(2).getEndToken().getNext(), false, true)) 
                num = true;
            else if (sli.get(2).getMorph()._getClass().isAdjective() && (sli.get(2).getWhitespacesBeforeCount() < 2)) {
                if (sli.get(2).getEndToken().getNext() == null || sli.get(2).getEndToken().isComma() || sli.get(2).isNewlineAfter()) 
                    num = true;
            }
            if (num) {
                number = (sli.get(2).number == null ? sli.get(2).value : sli.get(2).number.getIntValue().toString());
                if (sli.get(2).isNumberKm) 
                    number += "км";
                rli.add(sli.get(2));
            }
            else 
                for(int jjj = 2 + sli.size() - 2 - 1, mmm = 2; jjj >= mmm; jjj--) sli.remove(jjj);
        }
        else if ((sli.size() > 2 && sli.get(0).typ == StreetItemType.STDADJECTIVE && sli.get(1).typ == StreetItemType.NOUN) && sli.get(2).typ == StreetItemType.STDNAME) {
            n0 = 0;
            n1 = -1;
            rli.add(sli.get(0));
            rli.add(sli.get(2));
            adj = sli.get(0);
            name = sli.get(2);
        }
        else 
            return null;
        String secNumber = null;
        for (j = n0; j <= n1; j++) {
            if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (sli.get(j).isNewlineBefore() && j > 0) 
                    break;
                if (number != null) {
                    if (name != null && name.typ == StreetItemType.STDNAME) {
                        secNumber = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                        if (sli.get(j).isNumberKm) 
                            secNumber += "км";
                        rli.add(sli.get(j));
                        continue;
                    }
                    if (((j + 1) < sli.size()) && sli.get(j + 1).typ == StreetItemType.STDNAME) {
                        secNumber = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                        if (sli.get(j).isNumberKm) 
                            secNumber += "км";
                        rli.add(sli.get(j));
                        continue;
                    }
                    break;
                }
                if (sli.get(j).number != null && sli.get(j).number.typ == com.pullenti.ner.NumberSpellingType.DIGIT && !sli.get(j).number.getMorph()._getClass().isAdjective()) {
                    if (sli.get(j).getWhitespacesBeforeCount() > 2 && j > 0) 
                        break;
                    if (sli.get(j).number != null && sli.get(j).number.getIntValue() > 20) {
                        if (j > n0) {
                            if (((j + 1) < sli.size()) && sli.get(j + 1).typ == StreetItemType.NOUN) {
                            }
                            else 
                                break;
                        }
                    }
                    if (j == n0 && n0 > 0) {
                    }
                    else if (j == n0 && n0 == 0 && sli.get(j).getWhitespacesAfterCount() == 1) {
                    }
                    else if (sli.get(j).numberHasPrefix || sli.get(j).isNumberKm) {
                    }
                    else if (j == n1 && ((n1 + 1) < sli.size()) && sli.get(n1 + 1).typ == StreetItemType.NOUN) {
                    }
                    else 
                        break;
                }
                number = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                if (sli.get(j).isNumberKm) 
                    number += "км";
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.AGE) {
                if (number != null || age != null) 
                    break;
                age = sli.get(j).number.getIntValue().toString();
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.STDADJECTIVE) {
                if (adj != null) 
                    return null;
                adj = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) {
                if (name != null) {
                    if (j > 1 && sli.get(j - 2).typ == StreetItemType.NOUN) {
                        if (name.nounCanBeName && com.pullenti.unisharp.Utils.stringsEq(sli.get(j - 2).termin.getCanonicText(), "УЛИЦА") && j == (sli.size() - 1)) 
                            noun = name;
                        else 
                            break;
                    }
                    else if (i < j) 
                        break;
                    else 
                        return null;
                }
                name = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.STDPARTOFNAME && j == n1) {
                if (name != null) 
                    break;
                name = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.NOUN) {
                if ((sli.get(0) == noun && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "УЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ВУЛИЦЯ"))) && j > 0) && name == null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "ЛИНИЯ")) 
                        name = sli.get(j);
                    else {
                        altNoun = noun;
                        noun = sli.get(j);
                    }
                    rli.add(sli.get(j));
                }
                else 
                    break;
            }
        }
        if (((n1 < i) && number == null && ((i + 1) < sli.size())) && sli.get(i + 1).typ == StreetItemType.NUMBER && sli.get(i + 1).numberHasPrefix) {
            number = (sli.get(i + 1).number == null ? sli.get(i + 1).value : sli.get(i + 1).number.getIntValue().toString());
            rli.add(sli.get(i + 1));
        }
        else if ((((i < n0) && ((name != null || adj != null)) && (j < sli.size())) && sli.get(j).typ == StreetItemType.NOUN && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "УЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ВУЛИЦЯ")))) && (((com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "БУЛЬВАР") || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "ПЛОЩА")) || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "МАЙДАН") || (j + 1) == sli.size()))) {
            altNoun = noun;
            noun = sli.get(j);
            rli.add(sli.get(j));
        }
        if (name == null) {
            if (number == null && age == null && adj == null) 
                return null;
            if (noun.isAbridge) {
                if (isMicroRaion) {
                }
                else if (noun.termin != null && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЕЗД") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЇЗД")))) {
                }
                else if (adj == null || adj.isAbridge) 
                    return null;
            }
            if (adj != null && adj.isAbridge) 
                return null;
        }
        if (!rli.contains(sli.get(i))) 
            rli.add(sli.get(i));
        com.pullenti.ner.address.StreetReferent street = new com.pullenti.ner.address.StreetReferent();
        if (!forMetro) {
            street.addTyp(noun.termin.getCanonicText().toLowerCase());
            if (noun.altTermin != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(noun.altTermin.getCanonicText(), "ПРОСПЕКТ") && number != null) {
                }
                else 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, noun.altTermin.getCanonicText().toLowerCase(), false, 0);
            }
        }
        else 
            street.addTyp("метро");
        AddressItemToken res = AddressItemToken._new72(AddressItemType.STREET, rli.get(0).getBeginToken(), rli.get(0).getEndToken(), street);
        if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ЛИНИЯ")) {
            if (number == null) {
                if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken(), false)) {
                }
                else 
                    return null;
            }
            res.isDoubt = true;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПУНКТ")) {
            if (!com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken(), false)) 
                return null;
            if (name == null || number != null) 
                return null;
        }
        for (StreetItemToken r : rli) {
            if (res.getBeginChar() > r.getBeginChar()) 
                res.setBeginToken(r.getBeginToken());
            if (res.getEndChar() < r.getEndChar()) 
                res.setEndToken(r.getEndToken());
        }
        if (forMetro && rli.contains(noun) && com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МЕТРО")) 
            rli.remove(noun);
        if (noun.isAbridge && (noun.getLengthChar() < 4)) 
            res.isDoubt = true;
        else if (noun.nounIsDoubtCoef > 0) {
            res.isDoubt = true;
            if ((name != null && name.getEndChar() > noun.getEndChar() && noun.chars.isAllLower()) && !name.chars.isAllLower() && !(name.getBeginToken() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(name.getBeginToken());
                if (npt2 != null && npt2.getEndChar() > name.getEndChar()) {
                }
                else if (AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), false, false)) 
                    res.isDoubt = false;
                else if (name.chars.isCapitalUpper() && noun.nounIsDoubtCoef == 1) 
                    res.isDoubt = false;
            }
        }
        StringBuilder nameBase = new StringBuilder();
        StringBuilder nameAlt = new StringBuilder();
        String nameAlt2 = null;
        com.pullenti.morph.MorphGender gen = noun.termin.getGender();
        com.pullenti.morph.MorphGender adjGen = com.pullenti.morph.MorphGender.UNDEFINED;
        if (number != null) {
            street.setNumber(number);
            if (secNumber != null) 
                street.setSecNumber(secNumber);
        }
        if (age != null) {
            if (street.getNumber() == null) 
                street.setNumber(age);
            else 
                street.setSecNumber(age);
        }
        if (name != null && name.value != null) {
            if (street.getKind() == com.pullenti.ner.address.StreetKind.ROAD) {
                for (StreetItemToken r : rli) {
                    if (r.typ == StreetItemType.NAME && r != name) {
                        nameAlt.append(r.value);
                        break;
                    }
                }
            }
            if (name.altValue != null && nameAlt.length() == 0) 
                nameAlt.append(nameBase.toString()).append(" ").append(name.altValue);
            nameBase.append(" ").append(name.value);
        }
        else if (name != null) {
            boolean isAdj = false;
            if (name.getEndToken() instanceof com.pullenti.ner.TextToken) {
                for (com.pullenti.morph.MorphBaseInfo wf : name.getEndToken().getMorph().getItems()) {
                    if ((wf instanceof com.pullenti.morph.MorphWordForm) && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                        isAdj = wf._getClass().isAdjective() | wf._getClass().isProperGeo();
                        adjGen = wf.getGender();
                        break;
                    }
                    else if (wf._getClass().isAdjective() | wf._getClass().isProperGeo()) 
                        isAdj = true;
                }
            }
            if (isAdj) {
                StringBuilder tmp = new StringBuilder();
                java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
                for (com.pullenti.ner.Token t = name.getBeginToken(); t != null; t = t.getNext()) {
                    com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                    if (tt == null) 
                        break;
                    if (tmp.length() > 0 && tmp.charAt(tmp.length() - 1) != ' ') 
                        tmp.append(' ');
                    if (t == name.getEndToken()) {
                        boolean isPadez = false;
                        if (!noun.isAbridge) {
                            if (!noun.getMorph().getCase().isUndefined() && !noun.getMorph().getCase().isNominative()) 
                                isPadez = true;
                            else if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ШОССЕ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ШОСЕ")) 
                                isPadez = true;
                        }
                        if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                            isPadez = true;
                        if (!isPadez) {
                            tmp.append(tt.term);
                            break;
                        }
                        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                            if (((wf._getClass().isAdjective() || wf._getClass().isProperGeo())) && ((short)((wf.getGender().value()) & (gen.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                if (noun.getMorph().getCase().isUndefined() || !((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), noun.getMorph().getCase()))).isUndefined()) {
                                    com.pullenti.morph.MorphWordForm wff = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                                    if (wff == null) 
                                        continue;
                                    if (gen == com.pullenti.morph.MorphGender.MASCULINE && (wff.normalCase.indexOf("ОЙ") >= 0)) 
                                        continue;
                                    if (!vars.contains(wff.normalCase)) 
                                        vars.add(wff.normalCase);
                                }
                            }
                        }
                        if (!vars.contains(tt.term) && sli.indexOf(name) > sli.indexOf(noun)) 
                            vars.add(tt.term);
                        if (vars.size() == 0) 
                            vars.add(tt.term);
                        break;
                    }
                    if (!tt.isHiphen()) 
                        tmp.append(tt.term);
                }
                if (vars.size() == 0) 
                    nameBase.append(" ").append(tmp.toString());
                else {
                    String head = nameBase.toString();
                    nameBase.append(" ").append(tmp.toString()).append(vars.get(0));
                    if (vars.size() > 1) {
                        nameAlt.setLength(0);
                        nameAlt.append(head).append(" ").append(tmp.toString()).append(vars.get(1));
                    }
                    if (vars.size() > 2) 
                        nameAlt2 = (head + " " + tmp.toString() + vars.get(2));
                }
            }
            else {
                String strNam = null;
                java.util.ArrayList<String> nits = new java.util.ArrayList<String>();
                boolean hasAdj = false;
                boolean hasProperName = false;
                for (com.pullenti.ner.Token t = name.getBeginToken(); t != null && t.getEndChar() <= name.getEndChar(); t = t.getNext()) {
                    if (t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isConjunction()) 
                        hasAdj = true;
                    if ((t instanceof com.pullenti.ner.TextToken) && !t.isHiphen()) {
                        if (name.termin != null) {
                            nits.add(name.termin.getCanonicText());
                            break;
                        }
                        else if (!t.chars.isLetter() && nits.size() > 0) 
                            com.pullenti.unisharp.Utils.putArrayValue(nits, nits.size() - 1, nits.get(nits.size() - 1) + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                        else {
                            nits.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                            if (t == name.getBeginToken() && t.getMorphClassInDictionary().isProperName()) 
                                hasProperName = true;
                        }
                    }
                    else if ((t instanceof com.pullenti.ner.ReferentToken) && name.termin == null) 
                        nits.add(t.getSourceText().toUpperCase());
                }
                if (!hasAdj && !hasProperName && !name.isInDictionary) 
                    java.util.Collections.sort(nits);
                strNam = String.join(" ", java.util.Arrays.asList(nits.toArray(new String[nits.size()])));
                if (hasProperName && nits.size() == 2) {
                    nameAlt.setLength(0);
                    nameAlt.append(nameBase.toString()).append(" ").append(nits.get(1));
                }
                nameBase.append(" ").append(strNam);
            }
        }
        String adjStr = null;
        boolean adjCanBeInitial = false;
        if (adj != null) {
            String s;
            if (adjGen == com.pullenti.morph.MorphGender.UNDEFINED && name != null && ((short)((name.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                if (name.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || name.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || name.getMorph().getGender() == com.pullenti.morph.MorphGender.NEUTER) 
                    adjGen = name.getMorph().getGender();
            }
            if (name != null && ((short)((name.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                s = com.pullenti.morph.MorphologyService.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new170(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.PLURAL));
            else if (adjGen != com.pullenti.morph.MorphGender.UNDEFINED) 
                s = com.pullenti.morph.MorphologyService.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new171(com.pullenti.morph.MorphClass.ADJECTIVE, adjGen));
            else if (((short)((adj.getMorph().getGender().value()) & (gen.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                s = com.pullenti.morph.MorphologyService.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new171(com.pullenti.morph.MorphClass.ADJECTIVE, adj.getMorph().getGender()));
            else 
                s = com.pullenti.morph.MorphologyService.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new171(com.pullenti.morph.MorphClass.ADJECTIVE, gen));
            adjStr = s;
            if (name != null && (sli.indexOf(adj) < sli.indexOf(name))) {
                if (adj.getEndToken().isChar('.') && adj.getLengthChar() <= 3 && !adj.getBeginToken().chars.isAllLower()) 
                    adjCanBeInitial = true;
            }
        }
        String s1 = nameBase.toString().trim();
        String s2 = nameAlt.toString().trim();
        if ((s1.length() < 3) && street.getKind() != com.pullenti.ner.address.StreetKind.ROAD) {
            if (street.getNumber() != null) {
                if (adjStr != null) {
                    if (adj.isAbridge) 
                        return null;
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, adjStr, false, 0);
                }
            }
            else if (adjStr == null) {
                if (s1.length() < 1) 
                    return null;
                if (isMicroRaion) {
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s2)) 
                        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s2, false, 0);
                }
                else 
                    return null;
            }
            else {
                if (adj.isAbridge) 
                    return null;
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, adjStr, false, 0);
            }
        }
        else if (adjCanBeInitial) {
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, com.pullenti.ner.core.MiscHelper.getTextValue(adj.getBeginToken(), name.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        }
        else if (adjStr == null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
        else 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        if (nameAlt.length() > 0) {
            s1 = nameAlt.toString().trim();
            if (adjStr == null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
            else 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        }
        if (nameAlt2 != null) {
            if (adjStr == null) {
                if (forMetro && noun != null) 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (altNoun.termin.getCanonicText() + " " + nameAlt2.trim()), false, 0);
                else 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, nameAlt2.trim(), false, 0);
            }
            else 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + nameAlt2.trim()), false, 0);
        }
        if (name != null && name.altValue2 != null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, name.altValue2, false, 0);
        if ((name != null && adj == null && name.existStreet != null) && !forMetro) {
            for (String n : name.existStreet.getNames()) {
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, n, false, 0);
            }
        }
        if (altNoun != null && !forMetro) 
            street.addTyp(altNoun.termin.getCanonicText().toLowerCase());
        if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПЛОЩА")) {
            res.isDoubt = true;
            if (name != null && name.isInDictionary) 
                res.isDoubt = false;
            else if (altNoun != null || forMetro) 
                res.isDoubt = false;
            else if (res.getBeginToken().getPrevious() == null || com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(res.getBeginToken().getPrevious(), false)) {
                if (res.getEndToken().getNext() == null || AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), false, true)) 
                    res.isDoubt = false;
            }
        }
        if (com.pullenti.morph.LanguageHelper.endsWith(noun.termin.getCanonicText(), "ГОРОДОК")) {
            street.setKind(com.pullenti.ner.address.StreetKind.AREA);
            for (com.pullenti.ner.Slot s : street.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_TYP)) 
                    street.uploadSlot(s, "микрорайон");
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_NAME)) 
                    street.uploadSlot(s, (noun.termin.getCanonicText() + " " + s.getValue()));
            }
            if (street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, null, true) == null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, noun.termin.getCanonicText(), false, 0);
        }
        com.pullenti.ner.Token t1 = res.getEndToken().getNext();
        if (t1 != null && t1.isComma()) 
            t1 = t1.getNext();
        StreetItemToken non = StreetItemToken.tryParse(t1, null, false, null);
        if (non != null && non.typ == StreetItemType.NOUN && street.getTyps().size() > 0) {
            if (AddressItemToken.checkHouseAfter(non.getEndToken().getNext(), false, true)) {
                street.correct();
                java.util.ArrayList<String> nams = street.getNames();
                for (String t : street.getTyps()) {
                    for (String n : nams) {
                        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (t.toUpperCase() + " " + n), false, 0);
                    }
                }
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, non.termin.getCanonicText().toLowerCase(), false, 0);
                res.setEndToken(non.getEndToken());
            }
        }
        if (street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "ПРОЕКТИРУЕМЫЙ", true) != null && street.getNumber() == null) {
            if (non != null && non.typ == StreetItemType.NUMBER) {
                street.setNumber(non.number.getValue());
                res.setEndToken(non.getEndToken());
            }
            else {
                com.pullenti.ner.Token ttt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(res.getEndToken().getNext());
                if (ttt != null) {
                    non = StreetItemToken.tryParse(ttt, null, false, null);
                    if (non != null && non.typ == StreetItemType.NUMBER) {
                        street.setNumber(non.number.getValue());
                        res.setEndToken(non.getEndToken());
                    }
                }
            }
        }
        if (res.isDoubt) {
            if (noun.isRoad()) {
                street.setKind(com.pullenti.ner.address.StreetKind.ROAD);
                if (street.getNumber() != null && com.pullenti.unisharp.Utils.endsWithString(street.getNumber(), "КМ", true)) 
                    res.isDoubt = false;
                else if (AddressItemToken.checkKmAfter(res.getEndToken().getNext())) 
                    res.isDoubt = false;
                else if (AddressItemToken.checkKmBefore(res.getBeginToken().getPrevious())) 
                    res.isDoubt = false;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЕЗД") && street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "ПРОЕКТИРУЕМЫЙ", true) != null) 
                res.isDoubt = false;
            for (com.pullenti.ner.Token tt0 = res.getBeginToken().getPrevious(); tt0 != null; tt0 = tt0.getPrevious()) {
                if (tt0.isCharOf(",.") || tt0.isCommaAnd()) 
                    continue;
                com.pullenti.ner.address.StreetReferent str0 = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(tt0.getReferent(), com.pullenti.ner.address.StreetReferent.class);
                if (str0 != null) 
                    res.isDoubt = false;
                break;
            }
            if (res.isDoubt) {
                if (AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), false, false)) 
                    res.isDoubt = false;
                else if (AddressItemToken.checkStreetAfter(res.getEndToken().getNext(), false)) 
                    res.isDoubt = false;
                else if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(res.getBeginToken(), false)) 
                    res.isDoubt = false;
                for (com.pullenti.ner.Token ttt = res.getBeginToken().getNext(); ttt != null && ttt.getEndChar() <= res.getEndChar(); ttt = ttt.getNext()) {
                    if (ttt.isNewlineBefore()) 
                        res.isDoubt = true;
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ") && (res.getWhitespacesAfterCount() < 2) && number == null) {
            AddressItemToken ait = AddressItemToken.tryParsePureItem(res.getEndToken().getNext(), null, null);
            if (ait != null && ait.getTyp() == AddressItemType.NUMBER && ait.value != null) {
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, ait.value, false, 0);
                res.setEndToken(ait.getEndToken());
            }
        }
        if (age != null && street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, null, true) == null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "ЛЕТ", false, 0);
        return res;
    }

    private static AddressItemToken tryDetectNonNoun(java.util.ArrayList<StreetItemToken> sli, boolean ontoRegim, boolean forMetro, boolean streetBefore) {
        if (sli.size() > 1 && sli.get(sli.size() - 1).typ == StreetItemType.NUMBER && !sli.get(sli.size() - 1).numberHasPrefix) 
            sli.remove(sli.size() - 1);
        com.pullenti.ner.address.StreetReferent street;
        if (sli.size() == 1 && sli.get(0).typ == StreetItemType.NAME && ((ontoRegim || forMetro))) {
            String s = com.pullenti.ner.core.MiscHelper.getTextValue(sli.get(0).getBeginToken(), sli.get(0).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
            if (s == null) 
                return null;
            if (!forMetro && !sli.get(0).isInDictionary && sli.get(0).existStreet == null) {
                com.pullenti.ner.Token tt = sli.get(0).getEndToken().getNext();
                if (tt != null && tt.isComma()) 
                    tt = tt.getNext();
                AddressItemToken ait1 = AddressItemToken.tryParsePureItem(tt, null, null);
                if (ait1 != null && ((ait1.getTyp() == AddressItemType.NUMBER || ait1.getTyp() == AddressItemType.HOUSE))) {
                }
                else 
                    return null;
            }
            street = new com.pullenti.ner.address.StreetReferent();
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, (forMetro ? "метро" : (sli.get(0).kit.baseLanguage.isUa() ? "вулиця" : "улица")), false, 0);
            if (sli.get(0).value != null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sli.get(0).value, false, 0);
            if (sli.get(0).altValue != null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sli.get(0).altValue, false, 0);
            if (sli.get(0).altValue2 != null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sli.get(0).altValue2, false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s, false, 0);
            AddressItemToken res0 = AddressItemToken._new167(AddressItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), street, true);
            if (sli.get(0).isInBrackets) 
                res0.isDoubt = false;
            return res0;
        }
        int i1 = 0;
        if (sli.size() == 1 && ((sli.get(0).typ == StreetItemType.STDNAME || sli.get(0).typ == StreetItemType.NAME))) {
            if (!ontoRegim) {
                boolean isStreetBefore = streetBefore;
                com.pullenti.ner.Token tt = sli.get(0).getBeginToken().getPrevious();
                if ((tt != null && tt.isCommaAnd() && tt.getPrevious() != null) && (tt.getPrevious().getReferent() instanceof com.pullenti.ner.address.StreetReferent)) 
                    isStreetBefore = true;
                int cou = 0;
                for (tt = sli.get(0).getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (!tt.isCommaAnd() || tt.getNext() == null) 
                        break;
                    java.util.ArrayList<StreetItemToken> sli2 = StreetItemToken.tryParseList(tt.getNext(), 10, null);
                    if (sli2 == null) 
                        break;
                    StreetItemToken noun = null;
                    boolean empty = true;
                    for (StreetItemToken si : sli2) {
                        if (si.typ == StreetItemType.NOUN) 
                            noun = si;
                        else if ((si.typ == StreetItemType.NAME || si.typ == StreetItemType.STDNAME || si.typ == StreetItemType.NUMBER) || si.typ == StreetItemType.STDADJECTIVE) 
                            empty = false;
                    }
                    if (empty) 
                        break;
                    if (noun == null) {
                        if (tt.isAnd() && !isStreetBefore) 
                            break;
                        if ((++cou) > 4) 
                            break;
                        tt = sli2.get(sli2.size() - 1).getEndToken();
                        continue;
                    }
                    if (!tt.isAnd() && !isStreetBefore) 
                        break;
                    java.util.ArrayList<StreetItemToken> tmp = new java.util.ArrayList<StreetItemToken>();
                    tmp.add(sli.get(0));
                    tmp.add(noun);
                    AddressItemToken re = tryParseStreet(tmp, false, forMetro, false);
                    if (re != null) {
                        re.setEndToken(tmp.get(0).getEndToken());
                        return re;
                    }
                }
            }
        }
        else if (sli.size() == 2 && ((sli.get(0).typ == StreetItemType.STDADJECTIVE || sli.get(0).typ == StreetItemType.NUMBER || sli.get(0).typ == StreetItemType.AGE)) && ((sli.get(1).typ == StreetItemType.STDNAME || sli.get(1).typ == StreetItemType.NAME))) 
            i1 = 1;
        else if (sli.size() == 2 && ((sli.get(0).typ == StreetItemType.STDNAME || sli.get(0).typ == StreetItemType.NAME)) && sli.get(1).typ == StreetItemType.NUMBER) 
            i1 = 0;
        else if (sli.size() == 1 && sli.get(0).typ == StreetItemType.NUMBER && sli.get(0).isNumberKm) {
            for (com.pullenti.ner.Token tt = sli.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.getLengthChar() == 1) 
                    continue;
                com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (geo == null) 
                    break;
                boolean ok1 = false;
                if (geo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "станция", true) != null) 
                    ok1 = true;
                if (ok1) {
                    street = new com.pullenti.ner.address.StreetReferent();
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, (sli.get(0).number.getValue() + "км"), false, 0);
                    AddressItemToken res0 = AddressItemToken._new167(AddressItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), street, true);
                    if (sli.get(0).isInBrackets) 
                        res0.isDoubt = false;
                    return res0;
                }
            }
            return null;
        }
        else 
            return null;
        String val = sli.get(i1).value;
        String altVal = sli.get(i1).altValue;
        if (val == null) {
            if (sli.get(i1).existStreet != null) {
                java.util.ArrayList<String> names = sli.get(i1).existStreet.getNames();
                if (names.size() > 0) {
                    val = names.get(0);
                    if (names.size() > 1) 
                        altVal = names.get(1);
                }
            }
            else {
                com.pullenti.ner.TextToken te = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(sli.get(i1).getBeginToken(), com.pullenti.ner.TextToken.class);
                if (te != null) {
                    for (com.pullenti.morph.MorphBaseInfo wf : te.getMorph().getItems()) {
                        if (wf._getClass().isAdjective() && wf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                            val = ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase;
                            break;
                        }
                    }
                }
                if (i1 > 0 && sli.get(0).typ == StreetItemType.AGE) 
                    val = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(sli.get(i1), com.pullenti.ner.core.GetTextAttr.NO);
            }
        }
        boolean veryDoubt = false;
        if (val == null && sli.size() == 1 && sli.get(0).chars.isCapitalUpper()) {
            veryDoubt = true;
            com.pullenti.ner.Token t0 = sli.get(0).getBeginToken().getPrevious();
            if (t0 != null && t0.isChar(',')) 
                t0 = t0.getPrevious();
            if ((t0 instanceof com.pullenti.ner.ReferentToken) && (t0.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                val = com.pullenti.ner.core.MiscHelper.getTextValue(sli.get(0).getBeginToken(), sli.get(0).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
        }
        if (val == null) 
            return null;
        com.pullenti.ner.Token t = sli.get(sli.size() - 1).getEndToken().getNext();
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        if (t == null || t.isNewlineBefore()) 
            return null;
        boolean ok = false;
        boolean doubt = true;
        if (sli.get(i1).termin != null && ((StreetItemType)sli.get(i1).termin.tag) == StreetItemType.FIX) {
            ok = true;
            doubt = false;
        }
        else if (((sli.get(i1).existStreet != null || sli.get(0).existStreet != null)) && sli.get(0).getBeginToken() != sli.get(i1).getEndToken()) {
            ok = true;
            doubt = false;
            if (t.kit.processReferent("PERSON", sli.get(0).getBeginToken(), null) != null) {
                if (AddressItemToken.checkHouseAfter(t, false, false)) {
                }
                else 
                    doubt = true;
            }
        }
        else if (AddressItemToken.checkHouseAfter(t, false, false)) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("АРЕНДА", "ОРЕНДА") || t.getPrevious().isValue("СДАЧА", "ЗДАЧА") || t.getPrevious().isValue("СЪЕМ", "ЗНІМАННЯ")) 
                    return null;
            }
            com.pullenti.ner.core.NounPhraseToken vv = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(t.getPrevious());
            if (vv != null && vv.getEndChar() >= t.getBeginChar()) 
                return null;
            ok = true;
        }
        else {
            AddressItemToken ait = AddressItemToken.tryParsePureItem(t, null, null);
            if (ait == null) 
                return null;
            if (ait.getTyp() == AddressItemType.HOUSE && ait.value != null) 
                ok = true;
            else if (veryDoubt) 
                return null;
            else if (((com.pullenti.unisharp.Utils.stringsEq(val, "ТАБЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(val, "РИСУНОК") || com.pullenti.unisharp.Utils.stringsEq(val, "ДИАГРАММА")) || com.pullenti.unisharp.Utils.stringsEq(val, "ТАБЛИЦЯ") || com.pullenti.unisharp.Utils.stringsEq(val, "МАЛЮНОК")) || com.pullenti.unisharp.Utils.stringsEq(val, "ДІАГРАМА")) 
                return null;
            else if (ait.getTyp() == AddressItemType.NUMBER && (ait.getBeginToken().getWhitespacesBeforeCount() < 2)) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ait.getBeginToken(), com.pullenti.ner.NumberToken.class);
                if ((nt == null || nt.getIntValue() == null || nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT) || nt.getMorph()._getClass().isAdjective()) 
                    return null;
                if (ait.getEndToken().getNext() != null && !ait.getEndToken().isNewlineAfter()) {
                    com.pullenti.morph.MorphClass mc = ait.getEndToken().getNext().getMorphClassInDictionary();
                    if (mc.isAdjective() || mc.isNoun()) 
                        return null;
                }
                if (nt.getIntValue() > 100) 
                    return null;
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(ait.getBeginToken());
                if (nex != null) 
                    return null;
                for (t = sli.get(0).getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
                    if (t.isNewlineAfter()) 
                        break;
                    if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                        ok = true;
                        break;
                    }
                    if (t.isChar(',')) 
                        continue;
                    if (t.isChar('.')) 
                        break;
                    AddressItemToken ait0 = AddressItemToken.tryParsePureItem(t, null, null);
                    if (ait != null) {
                        if (ait.getTyp() == AddressItemType.PREFIX) {
                            ok = true;
                            break;
                        }
                    }
                    if (t.chars.isLetter()) 
                        break;
                }
            }
        }
        if (!ok) 
            return null;
        com.pullenti.ner.geo.internal.OrgItemToken ooo = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(sli.get(0).getBeginToken(), null);
        if (ooo == null && sli.size() > 1) 
            ooo = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(sli.get(1).getBeginToken(), null);
        if (ooo != null) 
            return null;
        street = new com.pullenti.ner.address.StreetReferent();
        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, (sli.get(0).kit.baseLanguage.isUa() ? "вулиця" : "улица"), false, 0);
        if (sli.size() > 1) {
            if (sli.get(0).typ == StreetItemType.NUMBER || sli.get(0).typ == StreetItemType.AGE) 
                street.setNumber((sli.get(0).number == null ? sli.get(0).value : sli.get(0).number.getIntValue().toString()));
            else if (sli.get(1).typ == StreetItemType.NUMBER || sli.get(1).typ == StreetItemType.AGE) 
                street.setNumber((sli.get(1).number == null ? sli.get(1).value : sli.get(1).number.getIntValue().toString()));
            else {
                java.util.ArrayList<String> adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(sli.get(0).getBeginToken(), sli.get(1).getMorph().getGender(), sli.get(1).getMorph().getNumber(), true);
                if (adjs == null) 
                    adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(sli.get(0).getBeginToken(), com.pullenti.morph.MorphGender.FEMINIE, com.pullenti.morph.MorphNumber.SINGULAR, false);
                if (adjs != null) {
                    if (adjs.size() > 1) 
                        altVal = (adjs.get(1) + " " + val);
                    val = (adjs.get(0) + " " + val);
                }
            }
        }
        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, val, false, 0);
        if (altVal != null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, altVal, false, 0);
        return AddressItemToken._new167(AddressItemType.STREET, sli.get(0).getBeginToken(), sli.get(sli.size() - 1).getEndToken(), street, doubt);
    }

    private static AddressItemToken _tryParseFix(java.util.ArrayList<StreetItemToken> sits) {
        if (sits.size() < 1) 
            return null;
        if (sits.get(0)._org != null) {
            com.pullenti.ner.geo.internal.OrgItemToken o = sits.get(0)._org;
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.addTyp("территория");
            for (com.pullenti.ner.Slot s : o.referent.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "NAME") || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "NUMBER")) 
                    str.addSlot(s.getTypeName(), s.getValue(), false, 0);
            }
            for (String ty : o.referent.getStringValues("TYPE")) {
                if (Character.isUpperCase(ty.charAt(0)) || (ty.indexOf(' ') < 0)) {
                    java.util.ArrayList<String> names = o.referent.getStringValues("NAME");
                    if (names.size() == 0) 
                        str.addSlot("NAME", ty.toUpperCase(), false, 0);
                    else 
                        for (String nam : names) {
                            str.addSlot("NAME", (ty.toUpperCase() + " " + nam), false, 0);
                        }
                }
            }
            boolean noOrg = false;
            if (o.referent.findSlot("TYPE", "владение", true) != null || o.referent.findSlot("TYPE", "участок", true) != null) 
                noOrg = true;
            if (str.findSlot("NAME", null, true) == null) {
                String typ = null;
                for (com.pullenti.ner.Slot s : o.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                        String ss = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        if (typ == null || typ.length() > ss.length()) 
                            typ = ss;
                    }
                }
                if (typ != null) 
                    str.addSlot("NAME", typ.toUpperCase(), false, 0);
            }
            if (noOrg || o.referent.findSlot("TYPE", null, true) == null) 
                str.setKind(com.pullenti.ner.address.StreetKind.AREA);
            else {
                str.setKind(com.pullenti.ner.address.StreetKind.ORG);
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_REF, o.referent, false, 0);
                str.addExtReferent(sits.get(0)._org);
            }
            com.pullenti.ner.Token b = sits.get(0).getBeginToken();
            com.pullenti.ner.Token e = sits.get(0).getEndToken();
            if (sits.get(0).getLengthChar() > 500) {
            }
            AddressItemToken re = new AddressItemToken(AddressItemType.STREET, b, e);
            re.referent = str;
            re.refToken = o;
            re.refTokenIsGsk = o.isGsk || o.hasTerrKeyword;
            re.isDoubt = o.isDoubt;
            if (!o.isGsk && !o.hasTerrKeyword) {
                if (!AddressItemToken.checkHouseAfter(sits.get(0).getEndToken().getNext(), false, false)) 
                    re.isDoubt = true;
            }
            return re;
        }
        if (sits.get(0).isRailway) {
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.setKind(com.pullenti.ner.address.StreetKind.RAILWAY);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "железная дорога", false, 0);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sits.get(0).value.replace(" ЖЕЛЕЗНАЯ ДОРОГА", ""), false, 0);
            com.pullenti.ner.Token t0 = sits.get(0).getBeginToken();
            com.pullenti.ner.Token t1 = sits.get(0).getEndToken();
            if (sits.size() > 1 && sits.get(1).typ == StreetItemType.NUMBER) {
                String num = (sits.get(1).number == null ? sits.get(1).value : sits.get(1).number.getIntValue().toString());
                if (t0.getPrevious() != null && ((t0.getPrevious().isValue("КИЛОМЕТР", null) || t0.getPrevious().isValue("КМ", null)))) {
                    t0 = t0.getPrevious();
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
                else if (sits.get(1).isNumberKm) {
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
            }
            else if (sits.get(0).nounIsDoubtCoef > 1) 
                return null;
            return AddressItemToken._new72(AddressItemType.STREET, t0, t1, str);
        }
        if (sits.get(0).termin == null) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(sits.get(0).termin.acronym, "МКАД")) {
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.setKind(com.pullenti.ner.address.StreetKind.ROAD);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "автодорога", false, 0);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "МОСКОВСКАЯ КОЛЬЦЕВАЯ", false, 0);
            com.pullenti.ner.Token t0 = sits.get(0).getBeginToken();
            com.pullenti.ner.Token t1 = sits.get(0).getEndToken();
            if (sits.size() > 1 && sits.get(1).typ == StreetItemType.NUMBER) {
                String num = (sits.get(1).number == null ? sits.get(1).value : sits.get(1).number.getIntValue().toString());
                if (t0.getPrevious() != null && ((t0.getPrevious().isValue("КИЛОМЕТР", null) || t0.getPrevious().isValue("КМ", null)))) {
                    t0 = t0.getPrevious();
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
                else if (sits.get(1).isNumberKm) {
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
            }
            return AddressItemToken._new72(AddressItemType.STREET, t0, t1, str);
        }
        if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sits.get(0).getBeginToken(), false) || AddressItemToken.checkHouseAfter(sits.get(0).getEndToken().getNext(), false, true)) {
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "улица", false, 0);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sits.get(0).termin.getCanonicText(), false, 0);
            return AddressItemToken._new72(AddressItemType.STREET, sits.get(0).getBeginToken(), sits.get(0).getEndToken(), str);
        }
        return null;
    }

    public static AddressItemToken tryParseSecondStreet(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        java.util.ArrayList<StreetItemToken> sli = StreetItemToken.tryParseList(t1, 10, null);
        if (sli == null || (sli.size() < 1) || sli.get(0).typ != StreetItemType.NOUN) 
            return null;
        java.util.ArrayList<StreetItemToken> sli2 = StreetItemToken.tryParseList(t2, 10, null);
        if (sli2 == null || sli2.size() == 0) 
            return null;
        sli2.add(0, sli.get(0));
        AddressItemToken res = tryParseStreet(sli2, true, false, false);
        if (res == null) 
            return null;
        res.setBeginToken(sli2.get(1).getBeginToken());
        return res;
    }
    public StreetDefineHelper() {
    }
}
