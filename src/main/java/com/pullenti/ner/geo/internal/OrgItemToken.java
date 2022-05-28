/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class OrgItemToken extends com.pullenti.ner.ReferentToken {

    public OrgItemToken(com.pullenti.ner.Referent r, com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(r, b, e, null);
    }

    public boolean isDoubt;

    public boolean hasTerrKeyword;

    public boolean keywordAfter;

    public boolean isGsk;

    public void setGsk() {
        isGsk = false;
        for (com.pullenti.ner.Slot s : referent.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE") && (s.getValue() instanceof String)) {
                String ty = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                if ((((((ty.indexOf("товарищество") >= 0) || (ty.indexOf("кооператив") >= 0) || (ty.indexOf("коллектив") >= 0)) || com.pullenti.morph.LanguageHelper.endsWithEx(ty, "поселок", " отдыха", " часть", "хозяйство") || (ty.indexOf("партнерство") >= 0)) || (ty.indexOf("объединение") >= 0) || (ty.indexOf("бизнес") >= 0)) || (((ty.indexOf("станция") >= 0) && !(ty.indexOf("заправоч") >= 0))) || (ty.indexOf("аэропорт") >= 0)) || (ty.indexOf("пансионат") >= 0) || (ty.indexOf("санаторий") >= 0)) {
                    isGsk = true;
                    return;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(ty, "АОЗТ")) {
                    isGsk = true;
                    return;
                }
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "NAME") && (s.getValue() instanceof String)) {
                String nam = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                if (com.pullenti.morph.LanguageHelper.endsWithEx(nam, "ГЭС", "АЭС", "ТЭС", null)) {
                    isGsk = true;
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (isDoubt) 
            tmp.append("? ");
        if (hasTerrKeyword) 
            tmp.append("Terr ");
        if (isGsk) 
            tmp.append("Gsk ");
        tmp.append(referent.toString());
        return tmp.toString();
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.oRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            OrgItemToken _org = tryParse(t, ad);
            if (_org != null) {
                if (d == null) 
                    d = new GeoTokenData(t);
                d._org = _org;
                if (_org.hasTerrKeyword || ((_org.isGsk && !_org.keywordAfter))) {
                    for (com.pullenti.ner.Token tt = _org.getBeginToken(); tt != null && tt.getEndChar() <= _org.getEndChar(); tt = tt.getNext()) {
                        GeoTokenData dd = (GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, GeoTokenData.class);
                        if (dd == null) 
                            dd = new GeoTokenData(tt);
                        dd.noGeo = true;
                    }
                    if (!_org.hasTerrKeyword) 
                        t = _org.getEndToken();
                }
            }
        }
        ad.oRegime = true;
    }

    public static OrgItemToken tryParse(com.pullenti.ner.Token t, GeoAnalyzerData ad) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (SPEEDREGIME && ((ad.oRegime || ad.allRegime))) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            if (d != null) 
                return d._org;
            return null;
        }
        if (ad.oLevel > 1) 
            return null;
        ad.oLevel++;
        OrgItemToken res = _TryParse(t, false, 0, ad);
        ad.oLevel--;
        return res;
    }

    private static OrgItemToken _TryParse(com.pullenti.ner.Token t, boolean afterTerr, int lev, GeoAnalyzerData ad) {
        if (lev > 3 || t == null || t.isComma()) 
            return null;
        com.pullenti.ner.Token tt2 = MiscLocationHelper.checkTerritory(t);
        if (tt2 != null && tt2.getNext() != null) {
            tt2 = tt2.getNext();
            boolean br = false;
            if (com.pullenti.ner.core.BracketHelper.isBracket(tt2, true)) {
                br = true;
                tt2 = tt2.getNext();
            }
            if (tt2 == null || lev > 3) 
                return null;
            OrgItemToken re2 = _TryParse(tt2, true, lev + 1, ad);
            if (re2 != null) {
                com.pullenti.ner.Analyzer a = t.kit.processor.findAnalyzer("GEO");
                if (a != null) {
                    com.pullenti.ner.ReferentToken rt = a.processReferent(tt2, null);
                    if (rt != null) 
                        return null;
                }
                re2.setBeginToken(t);
                if (br && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(re2.getEndToken().getNext(), false, null, false)) 
                    re2.setEndToken(re2.getEndToken().getNext());
                re2.hasTerrKeyword = true;
                return re2;
            }
            else if ((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.startsWith("ТЕР") && (tt2.getWhitespacesBeforeCount() < 3)) {
                NameToken nam1 = NameToken.tryParse(tt2, NameTokenType.ORG, 0);
                if (nam1 != null && nam1.name != null) {
                    if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(tt2)) 
                        return null;
                    if (t.getNext() != nam1.getEndToken() && com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(nam1.getEndToken())) 
                        return null;
                    if (TerrItemToken.checkKeyword(tt2) != null) 
                        return null;
                    if (t.getNext() != nam1.getEndToken() && TerrItemToken.checkKeyword(nam1.getEndToken()) != null) 
                        return null;
                    com.pullenti.ner.core.IntOntologyToken ter = TerrItemToken.checkOntoItem(tt2);
                    if (ter != null) {
                        com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(ter.item.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (_geo.isCity() || _geo.isState()) 
                            return null;
                    }
                    if (CityItemToken.checkKeyword(tt2) != null) 
                        return null;
                    if (CityItemToken.checkOntoItem(tt2) != null) 
                        return null;
                    com.pullenti.ner.Token tt = nam1.getEndToken();
                    boolean ok = false;
                    if (tt.isNewlineAfter()) 
                        ok = true;
                    else if (tt.getNext() != null && tt.getNext().isComma()) 
                        ok = true;
                    else if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(tt2, false, false)) 
                        ok = true;
                    else {
                        com.pullenti.ner.address.internal.AddressItemToken a2 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(nam1.getEndToken().getNext(), false, null, ad);
                        if (a2 != null) {
                            com.pullenti.ner.address.internal.AddressItemToken a1 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt2, false, null, ad);
                            if (a1 == null || (a1.getEndChar() < a2.getEndChar())) 
                                ok = true;
                        }
                    }
                    if (ok) {
                        com.pullenti.ner.Referent org1 = t.kit.createReferent("ORGANIZATION");
                        org1.addSlot("NAME", nam1.name, false, 0);
                        if (nam1.number != null) 
                            org1.addSlot("NUMBER", nam1.number, false, 0);
                        OrgItemToken res1 = new OrgItemToken(org1, t, nam1.getEndToken());
                        res1.data = t.kit.getAnalyzerDataByAnalyzerName("ORGANIZATION");
                        res1.hasTerrKeyword = true;
                        return res1;
                    }
                }
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("NAMEDENTITY", tt2, null);
                if (rt != null) {
                    OrgItemToken res1 = new OrgItemToken(rt.referent, t, rt.getEndToken());
                    res1.data = t.kit.getAnalyzerDataByAnalyzerName("NAMEDENTITY");
                    res1.hasTerrKeyword = true;
                    return res1;
                }
            }
            if (!t.isValue("САД", null)) 
                return null;
        }
        boolean typAfter = false;
        boolean doubt0 = false;
        OrgTypToken tokTyp = OrgTypToken.tryParse(t, afterTerr, ad);
        NameToken nam = null;
        if (tokTyp == null) {
            int ok = 0;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                ok = 2;
            else if (t.isValue("ИМ", null)) 
                ok = 2;
            else if ((t instanceof com.pullenti.ner.TextToken) && !t.chars.isAllLower() && t.getLengthChar() > 1) 
                ok = 1;
            else if (afterTerr) 
                ok = 1;
            if (ok == 0) 
                return null;
            if ((t.getLengthChar() > 5 && (t instanceof com.pullenti.ner.TextToken) && !t.chars.isAllUpper()) && !t.chars.isAllLower() && !t.chars.isCapitalUpper()) {
                String namm = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getSourceText();
                if (Character.isUpperCase(namm.charAt(0)) && Character.isUpperCase(namm.charAt(1))) {
                    for (int i = 0; i < namm.length(); i++) {
                        if (Character.isLowerCase(namm.charAt(i)) && i > 2) {
                            String abbr = namm.substring(0, 0 + i - 1);
                            com.pullenti.ner.core.Termin te = com.pullenti.ner.core.Termin._new969(abbr, abbr);
                            java.util.ArrayList<com.pullenti.ner.core.Termin> li = OrgTypToken.findTerminByAcronym(abbr);
                            if (li != null && li.size() > 0) {
                                nam = new NameToken(t, t);
                                nam.name = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.substring(i - 1);
                                tokTyp = new OrgTypToken(t, t);
                                tokTyp.vals.add(li.get(0).getCanonicText().toLowerCase());
                                tokTyp.vals.add(abbr);
                                nam.tryAttachNumber();
                                break;
                            }
                        }
                    }
                }
            }
            if (nam == null) {
                if (afterTerr) 
                    ok = 2;
                if (ok < 2) {
                    int kk = 0;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null && (kk < 5); tt = tt.getNext(),kk++) {
                        OrgTypToken ty22 = OrgTypToken.tryParse(tt, false, ad);
                        if (ty22 == null || ty22.isDoubt) 
                            continue;
                        ok = 2;
                        break;
                    }
                }
                if (ok < 2) 
                    return null;
                typAfter = true;
                nam = NameToken.tryParse(t, NameTokenType.ORG, 0);
                if (nam == null) 
                    return null;
                tokTyp = OrgTypToken.tryParse(nam.getEndToken().getNext(), afterTerr, ad);
                if (nam.name == null) {
                    if (nam.number != null && tokTyp != null) {
                    }
                    else 
                        return null;
                }
                if (tokTyp != null) {
                    if (nam.getBeginToken() == nam.getEndToken()) {
                        com.pullenti.morph.MorphClass mc = nam.getMorphClassInDictionary();
                        if (mc.isConjunction() || mc.isPreposition() || mc.isPronoun()) 
                            return null;
                    }
                    NameToken nam2 = NameToken.tryParse(tokTyp.getEndToken().getNext(), NameTokenType.ORG, 0);
                    OrgItemToken rt2 = tryParse(tokTyp.getBeginToken(), null);
                    if (rt2 != null && rt2.getEndChar() > tokTyp.getEndChar()) {
                        if ((nam.number == null && nam2 != null && nam2.name == null) && nam2.number != null && nam2.getEndToken() == rt2.getEndToken()) {
                            nam.number = nam2.number;
                            tokTyp = tokTyp.clone();
                            tokTyp.setEndToken(nam2.getEndToken());
                        }
                        else 
                            return null;
                    }
                    else if ((nam.number == null && nam2 != null && nam2.name == null) && nam2.number != null) {
                        nam.number = nam2.number;
                        tokTyp = tokTyp.clone();
                        tokTyp.setEndToken(nam2.getEndToken());
                    }
                    nam.setEndToken(tokTyp.getEndToken());
                    doubt0 = true;
                }
                else {
                    if (nam.name.endsWith("ПЛАЗА") || nam.name.startsWith("БИЗНЕС")) {
                    }
                    else if (nam.getBeginToken() == nam.getEndToken()) 
                        return null;
                    else if ((((tokTyp = OrgTypToken.tryParse(nam.getEndToken(), false, ad)))) == null) 
                        return null;
                    else if (nam.getMorph().getCase().isGenitive() && !nam.getMorph().getCase().isNominative()) 
                        nam.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(nam, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE).replace("-", " ");
                    if (tokTyp == null) {
                        tokTyp = new OrgTypToken(t, t);
                        tokTyp.vals.add("бизнес центр");
                        tokTyp.vals.add("БЦ");
                    }
                    nam.isDoubt = false;
                }
            }
        }
        else {
            if (tokTyp.getWhitespacesAfterCount() > 3) 
                return null;
            com.pullenti.ner.Token tt3 = MiscLocationHelper.checkTerritory(tokTyp.getEndToken().getNext());
            if (tt3 != null) {
                tokTyp = tokTyp.clone();
                tokTyp.setEndToken(tt3);
                afterTerr = true;
                OrgTypToken tokTyp2 = OrgTypToken.tryParse(tokTyp.getEndToken().getNext(), true, ad);
                if (tokTyp2 != null && !tokTyp2.isDoubt) 
                    tokTyp.mergeWith(tokTyp2);
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tokTyp.getEndToken().getNext(), true, false)) {
                OrgTypToken tokTyp2 = OrgTypToken.tryParse(tokTyp.getEndToken().getNext().getNext(), afterTerr, ad);
                if (tokTyp2 != null && !tokTyp2.isDoubt) {
                    tokTyp = tokTyp.clone();
                    tokTyp.isDoubt = false;
                    nam = NameToken.tryParse(tokTyp2.getEndToken().getNext(), NameTokenType.ORG, 0);
                    if (nam != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(nam.getEndToken().getNext(), false, null, false)) {
                        tokTyp.mergeWith(tokTyp2);
                        nam.setEndToken(nam.getEndToken().getNext());
                    }
                    else if (nam != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(nam.getEndToken(), false, null, false)) 
                        tokTyp.mergeWith(tokTyp2);
                    else 
                        nam = null;
                }
            }
        }
        if (nam == null) 
            nam = NameToken.tryParse(tokTyp.getEndToken().getNext(), NameTokenType.ORG, 0);
        if (nam == null) 
            return null;
        if (tokTyp.isDoubt && ((nam.isDoubt || nam.chars.isAllUpper()))) 
            return null;
        if ((tokTyp.getLengthChar() < 3) && nam.name == null && nam.pref == null) 
            return null;
        com.pullenti.ner.Referent _org = t.kit.createReferent("ORGANIZATION");
        OrgItemToken res = new OrgItemToken(_org, t, nam.getEndToken());
        res.data = t.kit.getAnalyzerDataByAnalyzerName("ORGANIZATION");
        res.hasTerrKeyword = afterTerr;
        res.isDoubt = doubt0 || tokTyp.isDoubt;
        res.keywordAfter = typAfter;
        for (String ty : tokTyp.vals) {
            _org.addSlot("TYPE", ty, false, 0);
        }
        boolean ignoreNext = false;
        if ((res.getWhitespacesAfterCount() < 3) && res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ТЕРРИТОРИЯ", null)) {
            if (_TryParse(res.getEndToken().getNext().getNext(), true, lev + 1, ad) == null) {
                res.setEndToken(res.getEndToken().getNext());
                ignoreNext = true;
            }
        }
        if (res.getWhitespacesAfterCount() < 3) {
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            OrgItemToken _next = _TryParse(tt, false, lev + 1, ad);
            if (_next != null) {
                if (_next.isGsk) 
                    _next = null;
                else 
                    res.setEndToken(_next.getEndToken());
                ignoreNext = true;
            }
            else {
                if (tt != null && tt.isValue("ПРИ", null)) 
                    tt = tt.getNext();
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("ORGANIZATION", tt, null);
                if (rt != null) {
                }
                if (rt != null) {
                    res.setEndToken(rt.getEndToken());
                    com.pullenti.ner.core.IntOntologyToken ter = TerrItemToken.checkOntoItem(res.getEndToken().getNext());
                    if (ter != null) 
                        res.setEndToken(ter.getEndToken());
                    ignoreNext = true;
                }
            }
        }
        String suffName = null;
        if (!ignoreNext && (res.getWhitespacesAfterCount() < 2)) {
            OrgTypToken tokTyp2 = OrgTypToken.tryParse(res.getEndToken().getNext(), true, ad);
            if (tokTyp2 != null) {
                res.setEndToken(tokTyp2.getEndToken());
                if (tokTyp2.isDoubt && nam.name != null) 
                    suffName = tokTyp2.vals.get(0);
                else 
                    for (String ty : tokTyp2.vals) {
                        _org.addSlot("TYPE", ty, false, 0);
                    }
                if (nam.number == null) {
                    NameToken nam2 = NameToken.tryParse(res.getEndToken().getNext(), NameTokenType.ORG, 0);
                    if ((nam2 != null && nam2.number != null && nam2.name == null) && nam2.pref == null) {
                        nam.number = nam2.number;
                        res.setEndToken(nam2.getEndToken());
                    }
                }
            }
        }
        if (nam.name != null) {
            if (nam.pref != null) {
                _org.addSlot("NAME", (nam.pref + " " + nam.name), false, 0);
                if (suffName != null) 
                    _org.addSlot("NAME", (nam.pref + " " + nam.name + " " + suffName), false, 0);
            }
            else {
                _org.addSlot("NAME", nam.name, false, 0);
                if (suffName != null) 
                    _org.addSlot("NAME", (nam.name + " " + suffName), false, 0);
            }
        }
        else if (nam.pref != null) 
            _org.addSlot("NAME", nam.pref, false, 0);
        else if (nam.number != null && (res.getWhitespacesAfterCount() < 2)) {
            NameToken nam2 = NameToken.tryParse(res.getEndToken().getNext(), NameTokenType.ORG, 0);
            if (nam2 != null && nam2.name != null && nam2.number == null) {
                res.setEndToken(nam2.getEndToken());
                _org.addSlot("NAME", nam2.name, false, 0);
            }
        }
        if (nam.number != null) 
            _org.addSlot("NUMBER", nam.number, false, 0);
        boolean ok1 = false;
        int cou = 0;
        for (com.pullenti.ner.Token tt = res.getBeginToken(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() > 1) {
                if (nam != null && tt.getBeginChar() >= nam.getBeginChar() && tt.getEndChar() <= nam.getEndChar()) {
                    if (tokTyp != null && tt.getBeginChar() >= tokTyp.getBeginChar() && tt.getEndChar() <= tokTyp.getEndChar()) {
                    }
                    else 
                        cou++;
                }
                if (!tt.chars.isAllLower()) 
                    ok1 = true;
            }
            else if (tt instanceof com.pullenti.ner.ReferentToken) 
                ok1 = true;
        }
        res.setGsk();
        if (!ok1) {
            if (!res.isGsk && !res.hasTerrKeyword) 
                return null;
        }
        if (cou > 4) 
            return null;
        return res;
    }

    public static com.pullenti.ner.address.internal.StreetItemToken tryParseRailway(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isLetter()) 
            return null;
        if (t.isValue("ДОРОГА", null) && (t.getWhitespacesAfterCount() < 3)) {
            com.pullenti.ner.address.internal.StreetItemToken _next = tryParseRailway(t.getNext());
            if (_next != null) {
                _next.setBeginToken(t);
                return _next;
            }
        }
        GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad.oLevel > 0) 
            return null;
        ad.oLevel++;
        com.pullenti.ner.address.internal.StreetItemToken res = _tryParseRailway(t);
        ad.oLevel--;
        return res;
    }

    private static com.pullenti.ner.ReferentToken _tryParseRailwayOrg(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        int cou = 0;
        boolean ok = false;
        for (com.pullenti.ner.Token tt = t; tt != null && (cou < 4); tt = tt.getNext(),cou++) {
            if (tt instanceof com.pullenti.ner.TextToken) {
                String val = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(val, "Ж") || val.startsWith("ЖЕЛЕЗ")) {
                    ok = true;
                    break;
                }
                if (com.pullenti.morph.LanguageHelper.endsWith(val, "ЖД")) {
                    ok = true;
                    break;
                }
            }
        }
        if (!ok) 
            return null;
        com.pullenti.ner.ReferentToken rt = t.kit.processReferent("ORGANIZATION", t, null);
        if (rt == null) 
            return null;
        for (String ty : rt.referent.getStringValues("TYPE")) {
            if (ty.endsWith("дорога")) 
                return rt;
        }
        return null;
    }

    private static com.pullenti.ner.address.internal.StreetItemToken _tryParseRailway(com.pullenti.ner.Token t) {
        com.pullenti.ner.ReferentToken rt0 = _tryParseRailwayOrg(t);
        if (rt0 != null) {
            com.pullenti.ner.address.internal.StreetItemToken res = com.pullenti.ner.address.internal.StreetItemToken._new1267(t, rt0.getEndToken(), com.pullenti.ner.address.internal.StreetItemType.FIX, true);
            res.value = rt0.referent.getStringValue("NAME");
            t = res.getEndToken().getNext();
            if (t != null && t.isComma()) 
                t = t.getNext();
            com.pullenti.ner.address.internal.StreetItemToken _next = _tryParseRzdDir(t);
            if (_next != null) {
                res.setEndToken(_next.getEndToken());
                res.value = (res.value + " " + _next.value);
            }
            else if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isAdjective() && !t.chars.isAllLower()) {
                boolean ok = false;
                if (t.isNewlineAfter() || t.getNext() == null) 
                    ok = true;
                else if (t.getNext().isCharOf(".,")) 
                    ok = true;
                else if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(t.getNext(), false, false) || com.pullenti.ner.address.internal.AddressItemToken.checkKmAfter(t.getNext())) 
                    ok = true;
                if (ok) {
                    res.value = (res.value + " " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term + " НАПРАВЛЕНИЕ");
                    res.setEndToken(t);
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "РОССИЙСКИЕ ЖЕЛЕЗНЫЕ ДОРОГИ")) 
                res.nounIsDoubtCoef = 2;
            return res;
        }
        com.pullenti.ner.address.internal.StreetItemToken dir = _tryParseRzdDir(t);
        if (dir != null && dir.nounIsDoubtCoef == 0) 
            return dir;
        return null;
    }

    private static com.pullenti.ner.address.internal.StreetItemToken _tryParseRzdDir(com.pullenti.ner.Token t) {
        com.pullenti.ner.Token napr = null;
        com.pullenti.ner.Token tt0 = null;
        com.pullenti.ner.Token tt1 = null;
        String val = null;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t; tt != null && (cou < 4); tt = tt.getNext(),cou++) {
            if (tt.isCharOf(",.")) 
                continue;
            if (tt.isNewlineBefore()) 
                break;
            if (tt.isValue("НАПРАВЛЕНИЕ", null)) {
                napr = tt;
                continue;
            }
            if (tt.isValue("НАПР", null)) {
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                napr = tt;
                continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(tt);
            if (npt != null && npt.adjectives.size() > 0 && npt.noun.isValue("КОЛЬЦО", null)) {
                tt0 = tt;
                tt1 = npt.getEndToken();
                val = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                break;
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && ((!tt.chars.isAllLower() || napr != null)) && ((short)((tt.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                tt0 = (tt1 = tt);
                continue;
            }
            if ((((tt instanceof com.pullenti.ner.TextToken) && ((!tt.chars.isAllLower() || napr != null)) && tt.getNext() != null) && tt.getNext().isHiphen() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && ((short)((tt.getNext().getNext().getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                tt0 = tt;
                tt = tt.getNext().getNext();
                tt1 = tt;
                continue;
            }
            break;
        }
        if (tt0 == null) 
            return null;
        com.pullenti.ner.address.internal.StreetItemToken res = com.pullenti.ner.address.internal.StreetItemToken._new1268(tt0, tt1, com.pullenti.ner.address.internal.StreetItemType.FIX, true, 1);
        if (val != null) 
            res.value = val;
        else {
            res.value = tt1.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.NEUTER, false);
            if (tt0 != tt1) 
                res.value = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt0, com.pullenti.ner.TextToken.class)).term + " " + res.value);
            res.value += " НАПРАВЛЕНИЕ";
        }
        if (napr != null && napr.getEndChar() > res.getEndChar()) 
            res.setEndToken(napr);
        t = res.getEndToken().getNext();
        if (t != null && t.isComma()) 
            t = t.getNext();
        if (t != null) {
            com.pullenti.ner.ReferentToken rt0 = _tryParseRailwayOrg(t);
            if (rt0 != null) {
                res.value = (rt0.referent.getStringValue("NAME") + " " + res.value);
                res.setEndToken(rt0.getEndToken());
                res.nounIsDoubtCoef = 0;
            }
        }
        return res;
    }
    public OrgItemToken() {
        super();
    }
}
