/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class AddressItemToken extends com.pullenti.ner.MetaToken {

    public AddressItemToken(AddressItemType _typ, com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        setTyp(_typ);
    }

    public AddressItemType getTyp() {
        return m_Typ;
    }

    public AddressItemType setTyp(AddressItemType _value) {
        m_Typ = _value;
        if (_value == AddressItemType.HOUSE) {
        }
        return _value;
    }


    private AddressItemType m_Typ = AddressItemType.PREFIX;

    public String value;

    public com.pullenti.ner.Referent referent;

    public com.pullenti.ner.ReferentToken refToken;

    public boolean refTokenIsGsk;

    public boolean isDoubt;

    public com.pullenti.ner.address.AddressDetailType detailType = com.pullenti.ner.address.AddressDetailType.UNDEFINED;

    public com.pullenti.ner.address.AddressBuildingType buildingType = com.pullenti.ner.address.AddressBuildingType.UNDEFINED;

    public com.pullenti.ner.address.AddressHouseType houseType = com.pullenti.ner.address.AddressHouseType.UNDEFINED;

    public int detailMeters = 0;

    public AddressItemToken clone() {
        AddressItemToken res = new AddressItemToken(getTyp(), getBeginToken(), getEndToken());
        res.setMorph(this.getMorph());
        res.value = value;
        res.referent = referent;
        res.refToken = refToken;
        res.refTokenIsGsk = refTokenIsGsk;
        res.isDoubt = isDoubt;
        res.detailType = detailType;
        res.buildingType = buildingType;
        res.houseType = houseType;
        res.detailMeters = detailMeters;
        return res;
    }

    public boolean isStreetRoad() {
        if (getTyp() != AddressItemType.STREET) 
            return false;
        if (!(referent instanceof com.pullenti.ner.address.StreetReferent)) 
            return false;
        return ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.ROAD;
    }


    public boolean isDigit() {
        if (com.pullenti.unisharp.Utils.stringsEq(value, "Б/Н")) 
            return true;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(value)) 
            return false;
        if (Character.isDigit(value.charAt(0))) 
            return true;
        if (value.length() > 1) {
            if (Character.isLetter(value.charAt(0)) && Character.isDigit(value.charAt(1))) 
                return true;
        }
        if (value.length() != 1 || !Character.isLetter(value.charAt(0))) 
            return false;
        if (!getBeginToken().chars.isAllLower()) 
            return false;
        return true;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.getTyp().toString()).append(" ").append(((value != null ? value : "")));
        if (referent != null) 
            res.append(" <").append(referent.toString()).append(">");
        if (detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED || detailMeters > 0) 
            res.append(" [").append(detailType.toString()).append(", ").append(detailMeters).append("]");
        return res.toString();
    }

    private static AddressItemToken _findAddrTyp(com.pullenti.ner.Token t, int maxChar, int lev) {
        if (t == null || t.getEndChar() > maxChar) 
            return null;
        if (lev > 5) 
            return null;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geo != null) {
                for (com.pullenti.ner.Slot s : geo.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                        String ty = s.getValue().toString();
                        if ((ty.indexOf("район") >= 0)) 
                            return null;
                    }
                }
            }
            for (com.pullenti.ner.Token tt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
                if (tt.getEndChar() > maxChar) 
                    break;
                AddressItemToken ty = _findAddrTyp(tt, maxChar, lev + 1);
                if (ty != null) 
                    return ty;
            }
        }
        else {
            AddressItemToken ai = _tryAttachDetail(t, null);
            if (ai != null) {
                if (ai.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED || ai.detailMeters > 0) 
                    return ai;
            }
        }
        return null;
    }

    public static AddressItemToken tryParse(com.pullenti.ner.Token t, boolean prefixBefore, AddressItemToken prev, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad.aLevel > 1) 
            return null;
        ad.aLevel++;
        AddressItemToken res = _TryParse(t, prefixBefore, prev, ad);
        ad.aLevel--;
        if (((res != null && !res.isWhitespaceAfter() && res.getEndToken().getNext() != null) && res.getEndToken().getNext().isHiphen() && !res.getEndToken().getNext().isWhitespaceAfter()) && res.value != null) {
            if (res.getTyp() == AddressItemType.HOUSE || res.getTyp() == AddressItemType.BUILDING || res.getTyp() == AddressItemType.CORPUS) {
                com.pullenti.ner.Token tt = res.getEndToken().getNext().getNext();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    res.value = (res.value + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                    res.setEndToken(tt);
                    if ((!tt.isWhitespaceAfter() && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getLengthChar() == 1) && tt.getNext().chars.isAllUpper()) {
                        tt = tt.getNext();
                        res.setEndToken(tt);
                        res.value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                    }
                    if ((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isCharOf("\\/")) && (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        res.setEndToken((tt = tt.getNext().getNext()));
                        res.value = (res.value + "/" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                    }
                    if ((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isHiphen()) && (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        res.setEndToken((tt = tt.getNext().getNext()));
                        res.value = (res.value + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                        if ((!tt.isWhitespaceAfter() && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getLengthChar() == 1) && tt.getNext().chars.isAllUpper()) {
                            tt = tt.getNext();
                            res.setEndToken(tt);
                            res.value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                        }
                    }
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.chars.isAllUpper()) {
                    res.value = (res.value + "-" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                    res.setEndToken(tt);
                }
            }
        }
        return res;
    }

    private static AddressItemToken _TryParse(com.pullenti.ner.Token t, boolean prefixBefore, AddressItemToken prev, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            AddressItemType ty;
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.geo.GeoReferent.class);
            if (geo != null) {
                if (geo.isCity()) 
                    ty = AddressItemType.CITY;
                else if (geo.isState()) 
                    ty = AddressItemType.COUNTRY;
                else 
                    ty = AddressItemType.REGION;
                AddressItemToken res = _new72(ty, t, t, rt.referent);
                if (ty != AddressItemType.CITY) 
                    return res;
                for (com.pullenti.ner.Token tt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
                    if (tt instanceof com.pullenti.ner.ReferentToken) {
                        if (tt.getReferent() == geo) {
                            AddressItemToken res1 = _TryParse(tt, false, prev, ad);
                            if (res1 != null && ((res1.detailMeters > 0 || res1.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED))) {
                                res1.setBeginToken(res1.setEndToken(t));
                                return res1;
                            }
                        }
                        continue;
                    }
                    AddressItemToken det = _tryParsePureItem(tt, false, null);
                    if (det != null) {
                        if (det.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED && res.detailType == com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
                            res.detailType = det.detailType;
                        if (det.detailMeters > 0) 
                            res.detailMeters = det.detailMeters;
                    }
                }
                return res;
            }
        }
        if (prev != null) {
            if (t.isValue("КВ", null) || t.isValue("КВАРТ", null)) {
                if ((((prev.getTyp() == AddressItemType.HOUSE || prev.getTyp() == AddressItemType.NUMBER || prev.getTyp() == AddressItemType.BUILDING) || prev.getTyp() == AddressItemType.FLOOR || prev.getTyp() == AddressItemType.POTCH) || prev.getTyp() == AddressItemType.CORPUS || prev.getTyp() == AddressItemType.CORPUSORFLAT) || prev.getTyp() == AddressItemType.DETAIL) 
                    return tryParsePureItem(t, prev, null);
            }
        }
        java.util.ArrayList<StreetItemToken> sli = StreetItemToken.tryParseList(t, 10, ad);
        if (sli != null) {
            AddressItemToken rt = StreetDefineHelper.tryParseStreet(sli, prefixBefore, false, (prev != null && prev.getTyp() == AddressItemType.STREET));
            if (rt == null && sli.get(0).typ != StreetItemType.FIX) {
                com.pullenti.ner.geo.internal.OrgItemToken _org = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(t, null);
                if (_org != null) {
                    StreetItemToken si = StreetItemToken._new73(t, _org.getEndToken(), StreetItemType.FIX, _org);
                    sli.clear();
                    sli.add(si);
                    rt = StreetDefineHelper.tryParseStreet(sli, prefixBefore || prev != null, false, false);
                }
            }
            if (rt != null) {
                if (sli.size() > 2) {
                }
                if (rt.getBeginChar() > sli.get(0).getBeginChar()) 
                    return null;
                boolean crlf = false;
                for (com.pullenti.ner.Token ttt = rt.getBeginToken(); ttt != rt.getEndToken() && (ttt.getEndChar() < rt.getEndChar()); ttt = ttt.getNext()) {
                    if (ttt.isNewlineAfter()) {
                        crlf = true;
                        break;
                    }
                }
                if (crlf) {
                    for (com.pullenti.ner.Token ttt = rt.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                        if (ttt.getMorph()._getClass().isPreposition() || ttt.isComma()) 
                            continue;
                        if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            crlf = false;
                        break;
                    }
                    if (sli.get(0).typ == StreetItemType.NOUN && (sli.get(0).termin.getCanonicText().indexOf("ДОРОГА") >= 0)) 
                        crlf = false;
                }
                if (crlf) {
                    AddressItemToken aat = tryParsePureItem(rt.getEndToken().getNext(), null, null);
                    if (aat == null) 
                        return null;
                    if (aat.getTyp() != AddressItemType.HOUSE) 
                        return null;
                }
                return rt;
            }
            if (sli.size() == 1 && sli.get(0).typ == StreetItemType.NOUN) {
                com.pullenti.ner.Token tt = sli.get(0).getEndToken().getNext();
                if (tt != null && ((tt.isHiphen() || tt.isChar('_') || tt.isValue("НЕТ", null)))) {
                    com.pullenti.ner.Token ttt = tt.getNext();
                    if (ttt != null && ttt.isComma()) 
                        ttt = ttt.getNext();
                    AddressItemToken att = tryParsePureItem(ttt, null, null);
                    if (att != null) {
                        if (att.getTyp() == AddressItemType.HOUSE || att.getTyp() == AddressItemType.CORPUS || att.getTyp() == AddressItemType.BUILDING) 
                            return new AddressItemToken(AddressItemType.STREET, t, tt);
                    }
                }
            }
        }
        return tryParsePureItem(t, prev, ad);
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        com.pullenti.ner.geo.internal.GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.aRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            com.pullenti.ner.geo.internal.GeoTokenData d = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
            AddressItemToken prev = null;
            int kk = 0;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (kk < 10); tt = tt.getPrevious(),kk++) {
                com.pullenti.ner.geo.internal.GeoTokenData dd = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
                if (dd == null || dd.street == null) 
                    continue;
                if (dd.street.getEndToken().getNext() == t) 
                    prev = dd.addr;
                if (t.getPrevious() != null && t.getPrevious().isComma() && dd.street.getEndToken().getNext() == t.getPrevious()) 
                    prev = dd.addr;
            }
            AddressItemToken str = tryParsePureItem(t, prev, null);
            if (str != null) {
                if (d == null) 
                    d = new com.pullenti.ner.geo.internal.GeoTokenData(t);
                d.addr = str;
            }
        }
        ad.aRegime = true;
    }

    public static AddressItemToken tryParsePureItem(com.pullenti.ner.Token t, AddressItemToken prev, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (t.isChar(',')) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (SPEEDREGIME && ((ad.aRegime || ad.allRegime)) && !(t instanceof com.pullenti.ner.ReferentToken)) {
            com.pullenti.ner.geo.internal.GeoTokenData d = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
            if (d == null) 
                return null;
            if (d.addr == null) 
                return null;
            return d.addr;
        }
        if (ad.aLevel > 0) 
            return null;
        ad.level++;
        AddressItemToken res = _tryParsePureItem(t, false, prev);
        if (res != null && res.getTyp() == AddressItemType.DETAIL) {
        }
        else {
            AddressItemToken det = _tryAttachDetail(t, null);
            if (res == null) 
                res = det;
            else if (det != null && det.getEndChar() > res.getEndChar()) 
                res = det;
        }
        ad.level--;
        return res;
    }

    private static AddressItemToken _tryParsePureItem(com.pullenti.ner.Token t, boolean prefixBefore, AddressItemToken prev) {
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.NumberToken n = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (((n.getLengthChar() == 6 || n.getLengthChar() == 5)) && n.typ == com.pullenti.ner.NumberSpellingType.DIGIT && !n.getMorph()._getClass().isAdjective()) 
                return _new74(AddressItemType.ZIP, t, t, n.getValue().toString());
            boolean ok = false;
            if ((t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition() && t.getNext() != null) && t.getNext().chars.isLetter() && t.getNext().chars.isAllLower()) 
                ok = true;
            else if (t.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isNoun()) 
                ok = true;
            com.pullenti.ner.core.TerminToken tok0 = m_Ontology.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok0 != null && (tok0.termin.tag instanceof AddressItemType)) {
                if (tok0.getEndToken().getNext() == null || tok0.getEndToken().getNext().isComma() || tok0.getEndToken().isNewlineAfter()) 
                    ok = true;
                AddressItemType typ0 = (AddressItemType)tok0.termin.tag;
                if (typ0 == AddressItemType.FLAT) {
                    if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().isValue("КВ", null)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(t.getNext().getSourceText(), "кВ")) 
                            return null;
                    }
                    if ((tok0.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) && (tok0.getEndToken().getWhitespacesAfterCount() < 3)) {
                        if (prev != null && ((prev.getTyp() == AddressItemType.STREET || prev.getTyp() == AddressItemType.CITY))) 
                            return _new74(AddressItemType.NUMBER, t, t, n.getValue().toString());
                    }
                }
                if (tok0.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) {
                }
                else if ((typ0 == AddressItemType.KILOMETER || typ0 == AddressItemType.FLOOR || typ0 == AddressItemType.BLOCK) || typ0 == AddressItemType.POTCH || typ0 == AddressItemType.FLAT) 
                    return _new74(typ0, t, tok0.getEndToken(), n.getValue().toString());
            }
        }
        boolean prepos = false;
        com.pullenti.ner.core.TerminToken tok = null;
        if (t.getMorph()._getClass().isPreposition()) {
            if ((((tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) == null) {
                if (t.getBeginChar() < t.getEndChar()) 
                    return null;
                if (!t.isCharOf("КСкс")) 
                    t = t.getNext();
                prepos = true;
            }
        }
        if (tok == null) 
            tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        com.pullenti.ner.Token t1 = t;
        AddressItemType _typ = AddressItemType.NUMBER;
        com.pullenti.ner.address.AddressHouseType houseTyp = com.pullenti.ner.address.AddressHouseType.UNDEFINED;
        com.pullenti.ner.address.AddressBuildingType buildTyp = com.pullenti.ner.address.AddressBuildingType.UNDEFINED;
        if (tok != null) {
            if (t.isValue("УЖЕ", null)) 
                return null;
            if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ТАМ ЖЕ")) {
                int cou = 0;
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (cou > 1000) 
                        break;
                    com.pullenti.ner.Referent r = tt.getReferent();
                    if (r == null) 
                        continue;
                    if (r instanceof com.pullenti.ner.address.AddressReferent) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r.getSlotValue(com.pullenti.ner.address.AddressReferent.ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null) 
                            return _new72(AddressItemType.CITY, t, tok.getEndToken(), g);
                        break;
                    }
                    else if (r instanceof com.pullenti.ner.geo.GeoReferent) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
                        if (!g.isState()) 
                            return _new72(AddressItemType.CITY, t, tok.getEndToken(), g);
                    }
                }
                return null;
            }
            if (tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType) 
                return _tryAttachDetail(t, tok);
            t1 = tok.getEndToken().getNext();
            if (tok.termin.tag instanceof AddressItemType) {
                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressHouseType) 
                    houseTyp = (com.pullenti.ner.address.AddressHouseType)tok.termin.tag2;
                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressBuildingType) 
                    buildTyp = (com.pullenti.ner.address.AddressBuildingType)tok.termin.tag2;
                _typ = (AddressItemType)tok.termin.tag;
                if (_typ == AddressItemType.PLOT) {
                    if (t.getPrevious() != null && ((t.getPrevious().isValue("СУДЕБНЫЙ", "СУДОВИЙ") || t.getPrevious().isValue("ИЗБИРАТЕЛЬНЫЙ", "ВИБОРЧИЙ")))) 
                        return null;
                }
                if (_typ == AddressItemType.PREFIX) {
                    for (; t1 != null; t1 = t1.getNext()) {
                        if (((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction())) && t1.getWhitespacesAfterCount() == 1) 
                            continue;
                        if (t1.isChar(':')) {
                            t1 = t1.getNext();
                            break;
                        }
                        if (t1.isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null && (br.getLengthChar() < 50)) {
                                t1 = br.getEndToken();
                                continue;
                            }
                        }
                        if (t1 instanceof com.pullenti.ner.TextToken) {
                            if (t1.chars.isAllLower() || (t1.getWhitespacesBeforeCount() < 3)) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(t1);
                                if (npt != null && ((npt.chars.isAllLower() || npt.getMorph().getCase().isGenitive()))) {
                                    if (com.pullenti.ner.geo.internal.CityItemToken.checkKeyword(npt.getEndToken()) == null && com.pullenti.ner.geo.internal.TerrItemToken.checkKeyword(npt.getEndToken()) == null) {
                                        t1 = npt.getEndToken();
                                        continue;
                                    }
                                }
                            }
                        }
                        if (t1.isValue("УКАЗАННЫЙ", null) || t1.isValue("ЕГРИП", null) || t1.isValue("ФАКТИЧЕСКИЙ", null)) 
                            continue;
                        if (t1.isComma()) {
                            if (t1.getNext() != null && t1.getNext().isValue("УКАЗАННЫЙ", null)) 
                                continue;
                        }
                        break;
                    }
                    if (t1 != null) {
                        com.pullenti.ner.Token t0 = t;
                        if (((t0.getPrevious() != null && !t0.isNewlineBefore() && t0.getPrevious().isChar(')')) && (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken) && t0.getPrevious().getPrevious().getPrevious() != null) && t0.getPrevious().getPrevious().getPrevious().isChar('(')) {
                            t = t0.getPrevious().getPrevious().getPrevious().getPrevious();
                            if (t != null && t.getMorphClassInDictionary().isAdjective() && !t.isNewlineAfter()) 
                                t0 = t;
                        }
                        AddressItemToken res = new AddressItemToken(AddressItemType.PREFIX, t0, t1.getPrevious());
                        for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                            if (tt.getNewlinesAfterCount() > 3) 
                                break;
                            if (tt.isCommaAnd() || tt.isCharOf("().")) 
                                continue;
                            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                                break;
                            if (((tt.isValue("ПОЧТОВЫЙ", null) || tt.isValue("ЮРИДИЧЕСКИЙ", null) || tt.isValue("ЮР", null)) || tt.isValue("ФАКТИЧЕСКИЙ", null) || tt.isValue("ФАКТ", null)) || tt.isValue("ПОЧТ", null) || tt.isValue("АДРЕС", null)) 
                                res.setBeginToken(tt);
                            else 
                                break;
                        }
                        return res;
                    }
                    else 
                        return null;
                }
                else if ((_typ == AddressItemType.CORPUSORFLAT && !tok.isWhitespaceBefore() && !tok.isWhitespaceAfter()) && tok.getBeginToken() == tok.getEndToken() && tok.getBeginToken().isValue("К", null)) 
                    _typ = AddressItemType.CORPUS;
                if (_typ == AddressItemType.DETAIL && t.isValue("У", null)) {
                    if (!com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(t, false)) 
                        return null;
                }
                if (_typ == AddressItemType.FLAT && t.isValue("КВ", null)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(t.getSourceText(), "кВ")) 
                        return null;
                }
                if (_typ == AddressItemType.KILOMETER || _typ == AddressItemType.FLOOR || _typ == AddressItemType.POTCH) 
                    return new AddressItemToken(_typ, t, tok.getEndToken());
                if ((_typ == AddressItemType.HOUSE || _typ == AddressItemType.BUILDING || _typ == AddressItemType.CORPUS) || _typ == AddressItemType.PLOT) {
                    if (t1 != null && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction())) && (t1.getWhitespacesAfterCount() < 2)) {
                        com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(t1.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null && (tok2.termin.tag instanceof AddressItemType)) {
                            AddressItemType typ2 = (AddressItemType)tok2.termin.tag;
                            if (typ2 != _typ && ((typ2 == AddressItemType.PLOT || ((typ2 == AddressItemType.HOUSE && _typ == AddressItemType.PLOT))))) {
                                _typ = typ2;
                                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressHouseType) 
                                    houseTyp = (com.pullenti.ner.address.AddressHouseType)tok.termin.tag2;
                                t1 = tok2.getEndToken().getNext();
                                if (t1 == null) 
                                    return _new79(_typ, t, tok2.getEndToken(), "0", houseTyp);
                            }
                        }
                    }
                }
                if (_typ == AddressItemType.FIELD) {
                    AddressItemToken re = new AddressItemToken(_typ, t, tok.getEndToken());
                    StringBuilder nnn = new StringBuilder();
                    for (com.pullenti.ner.Token tt = tok.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                        com.pullenti.ner.NumberToken ll = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt);
                        if (ll != null && ll.getIntValue() != null) {
                            if (nnn.length() > 0) 
                                nnn.append("-");
                            nnn.append(com.pullenti.ner.core.NumberHelper.getNumberRoman(ll.getIntValue()));
                            re.setEndToken((tt = ll.getEndToken()));
                            continue;
                        }
                        if (tt.isHiphen()) 
                            continue;
                        if (tt.isWhitespaceBefore()) 
                            break;
                        if (tt instanceof com.pullenti.ner.NumberToken) {
                            if (nnn.length() > 0) 
                                nnn.append("-");
                            nnn.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                            re.setEndToken(tt);
                            continue;
                        }
                        if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllUpper()) {
                            if (nnn.length() > 0) 
                                nnn.append("-");
                            nnn.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                            re.setEndToken(tt);
                            continue;
                        }
                        break;
                    }
                    if (nnn.length() > 0) {
                        re.value = nnn.toString();
                        return re;
                    }
                }
                if (_typ != AddressItemType.NUMBER) {
                    if (t1 == null && t.getLengthChar() > 1) 
                        return _new80(_typ, t, tok.getEndToken(), houseTyp, buildTyp);
                    if ((t1 instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue(), "0")) 
                        return _new81(_typ, t, t1, "0", houseTyp, buildTyp);
                }
            }
        }
        if (t1 != null && t1.isChar('.') && t1.getNext() != null) {
            if (!t1.isWhitespaceAfter()) 
                t1 = t1.getNext();
            else if ((t1.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && (t1.getWhitespacesAfterCount() < 2)) 
                t1 = t1.getNext();
        }
        if ((t1 != null && !t1.isWhitespaceAfter() && ((t1.isHiphen() || t1.isChar('_')))) && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) 
            t1 = t1.getNext();
        tok = m_Ontology.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && (tok.termin.tag instanceof AddressItemType) && ((AddressItemType)tok.termin.tag) == AddressItemType.NUMBER) 
            t1 = tok.getEndToken().getNext();
        else if (tok != null && (tok.termin.tag instanceof AddressItemType) && ((AddressItemType)tok.termin.tag) == AddressItemType.NONUMBER) {
            AddressItemToken re0 = _new81(_typ, t, tok.getEndToken(), "0", houseTyp, buildTyp);
            if (!re0.isWhitespaceAfter() && (re0.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) {
                re0.setEndToken(re0.getEndToken().getNext());
                re0.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(re0.getEndToken(), com.pullenti.ner.NumberToken.class)).getValue().toString();
            }
            return re0;
        }
        else if (t1 != null) {
            if (_typ == AddressItemType.FLAT) {
                com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok2 != null && ((AddressItemType)tok2.termin.tag) == AddressItemType.FLAT) 
                    t1 = tok2.getEndToken().getNext();
            }
            if (t1.isValue("СТРОИТЕЛЬНЫЙ", null) && t1.getNext() != null) 
                t1 = t1.getNext();
            com.pullenti.ner.Token ttt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1);
            if (ttt != null) {
                t1 = ttt;
                if (t1.isHiphen() || t1.isChar('_')) 
                    t1 = t1.getNext();
            }
        }
        if (t1 == null) 
            return null;
        StringBuilder num = new StringBuilder();
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class);
        AddressItemToken re11;
        if (nt != null) {
            if (nt.getIntValue() == null || nt.getIntValue() == 0) 
                return null;
            num.append(nt.getValue());
            if (nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT || nt.typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                if (((nt.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getEndToken(), com.pullenti.ner.TextToken.class)).term, "Е") && nt.getEndToken().getPrevious() == nt.getBeginToken()) && !nt.getEndToken().isWhitespaceBefore()) 
                    num.append("Е");
                boolean drob = false;
                boolean hiph = false;
                boolean lit = false;
                com.pullenti.ner.Token et = nt.getNext();
                if (et != null && ((et.isCharOf("\\/") || et.isValue("ДРОБЬ", null)))) {
                    drob = true;
                    et = et.getNext();
                    if (et != null && et.isCharOf("\\/")) 
                        et = et.getNext();
                    t1 = et;
                }
                else if (et != null && ((et.isHiphen() || et.isChar('_')))) {
                    hiph = true;
                    et = et.getNext();
                }
                else if ((et != null && et.isChar('.') && (et.getNext() instanceof com.pullenti.ner.NumberToken)) && !et.isWhitespaceAfter()) 
                    return null;
                if (et instanceof com.pullenti.ner.NumberToken) {
                    if (drob) {
                        num.append("/").append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.NumberToken.class)).getValue());
                        drob = false;
                        t1 = et;
                        et = et.getNext();
                        if (et != null && et.isCharOf("\\/") && (et.getNext() instanceof com.pullenti.ner.NumberToken)) {
                            t1 = et.getNext();
                            num.append("/").append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue());
                            et = t1.getNext();
                        }
                    }
                    else if ((hiph && !t1.isWhitespaceAfter() && (et instanceof com.pullenti.ner.NumberToken)) && !et.isWhitespaceBefore()) {
                        AddressItemToken numm = tryParsePureItem(et, null, null);
                        if (numm != null && numm.getTyp() == AddressItemType.NUMBER) {
                            boolean merge = false;
                            if (_typ == AddressItemType.FLAT || _typ == AddressItemType.PLOT) 
                                merge = true;
                            else if (_typ == AddressItemType.HOUSE || _typ == AddressItemType.BUILDING || _typ == AddressItemType.CORPUS) {
                                com.pullenti.ner.Token ttt = numm.getEndToken().getNext();
                                if (ttt != null && ttt.isComma()) 
                                    ttt = ttt.getNext();
                                AddressItemToken numm2 = tryParsePureItem(ttt, null, null);
                                if (numm2 != null) {
                                    if ((numm2.getTyp() == AddressItemType.FLAT || numm2.getTyp() == AddressItemType.BUILDING || ((numm2.getTyp() == AddressItemType.CORPUSORFLAT && numm2.value != null))) || numm2.getTyp() == AddressItemType.CORPUS) 
                                        merge = true;
                                }
                            }
                            if (merge) {
                                num.append("/").append(numm.value);
                                t1 = numm.getEndToken();
                                et = t1.getNext();
                            }
                        }
                    }
                }
                else if (et != null && ((et.isHiphen() || et.isChar('_') || et.isValue("НЕТ", null))) && drob) 
                    t1 = et;
                com.pullenti.ner.Token ett = et;
                if ((ett != null && ett.isCharOf(",.") && (ett.getWhitespacesAfterCount() < 2)) && (ett.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.BracketHelper.isBracket(ett.getNext(), false)) 
                    ett = ett.getNext();
                if (((com.pullenti.ner.core.BracketHelper.isBracket(ett, false) && (ett.getNext() instanceof com.pullenti.ner.TextToken) && ett.getNext().getLengthChar() == 1) && ett.getNext().isLetters() && com.pullenti.ner.core.BracketHelper.isBracket(ett.getNext().getNext(), false)) && !ett.isWhitespaceAfter() && !ett.getNext().isWhitespaceAfter()) {
                    String ch = correctCharToken(ett.getNext());
                    if (ch == null) 
                        return null;
                    num.append(ch);
                    t1 = ett.getNext().getNext();
                }
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ett, true, false) && (ett.getWhitespacesBeforeCount() < 2)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(ett, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getBeginToken().getNext() instanceof com.pullenti.ner.TextToken) && br.getBeginToken().getNext().getNext() == br.getEndToken()) {
                        String s = correctCharToken(br.getBeginToken().getNext());
                        if (s != null) {
                            num.append(s);
                            t1 = br.getEndToken();
                        }
                    }
                }
                else if ((et instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.TextToken.class)).getLengthChar() == 1) {
                    String s = correctCharToken(et);
                    if (s != null) {
                        if (((com.pullenti.unisharp.Utils.stringsEq(s, "К") || com.pullenti.unisharp.Utils.stringsEq(s, "С"))) && (et.getNext() instanceof com.pullenti.ner.NumberToken) && !et.isWhitespaceAfter()) {
                        }
                        else if ((com.pullenti.unisharp.Utils.stringsEq(s, "Б") && et.getNext() != null && et.getNext().isCharOf("/\\")) && (et.getNext().getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().getNext().isValue("Н", null)) 
                            t1 = (et = et.getNext().getNext());
                        else {
                            boolean ok = false;
                            if (drob || hiph || lit) 
                                ok = true;
                            else if (!et.isWhitespaceBefore() || ((et.getWhitespacesBeforeCount() == 1 && ((et.chars.isAllUpper() || ((et.isNewlineAfter() || ((et.getNext() != null && et.getNext().isComma()))))))))) {
                                ok = true;
                                if (et.getNext() instanceof com.pullenti.ner.NumberToken) {
                                    if (!et.isWhitespaceBefore() && et.isWhitespaceAfter()) {
                                    }
                                    else 
                                        ok = false;
                                }
                            }
                            else if (((et.getNext() == null || et.getNext().isComma())) && (et.getWhitespacesBeforeCount() < 2)) 
                                ok = true;
                            else if (et.isWhitespaceBefore() && et.chars.isAllLower() && et.isValue("В", "У")) {
                            }
                            else {
                                AddressItemToken aitNext = tryParsePureItem(et.getNext(), null, null);
                                if (aitNext != null) {
                                    if ((aitNext.getTyp() == AddressItemType.CORPUS || aitNext.getTyp() == AddressItemType.FLAT || aitNext.getTyp() == AddressItemType.BUILDING) || aitNext.getTyp() == AddressItemType.OFFICE) 
                                        ok = true;
                                }
                            }
                            if (ok) {
                                num.append(s);
                                t1 = et;
                                if (et.getNext() != null && et.getNext().isCharOf("\\/") && et.getNext().getNext() != null) {
                                    if (et.getNext().getNext() instanceof com.pullenti.ner.NumberToken) {
                                        num.append("/").append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(et.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                                        t1 = (et = et.getNext().getNext());
                                    }
                                    else if (et.getNext().getNext().isHiphen() || et.getNext().getNext().isChar('_') || et.getNext().getNext().isValue("НЕТ", null)) 
                                        t1 = (et = et.getNext().getNext());
                                }
                            }
                        }
                    }
                }
                else if ((et instanceof com.pullenti.ner.TextToken) && !et.isWhitespaceBefore()) {
                    String val = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.TextToken.class)).term;
                    if (com.pullenti.unisharp.Utils.stringsEq(val, "КМ") && _typ == AddressItemType.HOUSE) {
                        t1 = et;
                        num.append("КМ");
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(val, "БН")) 
                        t1 = et;
                    else if (((val.length() == 2 && val.charAt(1) == 'Б' && et.getNext() != null) && et.getNext().isCharOf("\\/") && et.getNext().getNext() != null) && et.getNext().getNext().isValue("Н", null)) {
                        num.append(val.charAt(0));
                        t1 = (et = et.getNext().getNext());
                    }
                }
            }
        }
        else if ((((re11 = _tryAttachVCH(t1, _typ)))) != null) {
            re11.setBeginToken(t);
            re11.houseType = houseTyp;
            re11.buildingType = buildTyp;
            return re11;
        }
        else if (((t1 instanceof com.pullenti.ner.TextToken) && t1.getLengthChar() == 2 && t1.isLetters()) && !t1.isWhitespaceBefore() && (t1.getPrevious() instanceof com.pullenti.ner.NumberToken)) {
            String src = t1.getSourceText();
            if ((src != null && src.length() == 2 && ((src.charAt(0) == 'к' || src.charAt(0) == 'k'))) && Character.isUpperCase(src.charAt(1))) {
                char ch = correctChar(src.charAt(1));
                if (ch != ((char)0)) 
                    return _new74(AddressItemType.CORPUS, t1, t1, (String.valueOf(ch)));
            }
        }
        else if ((t1 instanceof com.pullenti.ner.TextToken) && t1.getLengthChar() == 1 && t1.isLetters()) {
            String ch = correctCharToken(t1);
            if (ch != null) {
                if (_typ == AddressItemType.NUMBER) 
                    return null;
                if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                    if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(ch, "Д") && _typ == AddressItemType.PLOT) {
                    AddressItemToken rrr = tryParsePureItem(t1, null, null);
                    if (rrr != null) {
                        rrr.setTyp(AddressItemType.PLOT);
                        rrr.setBeginToken(t);
                        return rrr;
                    }
                }
                if (t1.chars.isAllLower() && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()))) {
                    if ((t1.getWhitespacesAfterCount() < 2) && t1.getNext().chars.isLetter()) 
                        return null;
                }
                if (t.chars.isAllUpper() && t.getLengthChar() == 1 && t.getNext().isChar('.')) 
                    return null;
                num.append(ch);
                if ((t1.getNext() != null && ((t1.getNext().isHiphen() || t1.getNext().isChar('_'))) && !t1.isWhitespaceAfter()) && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && !t1.getNext().isWhitespaceAfter()) {
                    num.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    t1 = t1.getNext().getNext();
                }
                else if ((t1.getNext() instanceof com.pullenti.ner.NumberToken) && !t1.isWhitespaceAfter() && t1.chars.isAllUpper()) {
                    num.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    t1 = t1.getNext();
                }
                if (num.length() == 1 && _typ == AddressItemType.OFFICE) 
                    return null;
            }
            if (_typ == AddressItemType.BOX && num.length() == 0) {
                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t1);
                if (rom != null) 
                    return _new74(_typ, t, rom.getEndToken(), rom.getValue().toString());
            }
        }
        else if (((com.pullenti.ner.core.BracketHelper.isBracket(t1, false) && (t1.getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().getLengthChar() == 1) && t1.getNext().isLetters() && com.pullenti.ner.core.BracketHelper.isBracket(t1.getNext().getNext(), false)) && !t1.isWhitespaceAfter() && !t1.getNext().isWhitespaceAfter()) {
            String ch = correctCharToken(t1.getNext());
            if (ch == null) 
                return null;
            num.append(ch);
            t1 = t1.getNext().getNext();
        }
        else if ((t1 instanceof com.pullenti.ner.TextToken) && ((((t1.getLengthChar() == 1 && ((t1.isHiphen() || t1.isChar('_'))))) || t1.isValue("НЕТ", null) || t1.isValue("БН", null))) && (((_typ == AddressItemType.CORPUS || _typ == AddressItemType.CORPUSORFLAT || _typ == AddressItemType.BUILDING) || _typ == AddressItemType.HOUSE || _typ == AddressItemType.FLAT))) {
            while (t1.getNext() != null && ((t1.getNext().isHiphen() || t1.getNext().isChar('_'))) && !t1.isWhitespaceAfter()) {
                t1 = t1.getNext();
            }
            String val = null;
            if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) {
                t1 = t1.getNext();
                val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue().toString();
            }
            if (t1.isValue("БН", null)) 
                val = "0";
            return _new74(_typ, t, t1, val);
        }
        else {
            if (((_typ == AddressItemType.FLOOR || _typ == AddressItemType.KILOMETER || _typ == AddressItemType.POTCH)) && (t.getPrevious() instanceof com.pullenti.ner.NumberToken)) 
                return new AddressItemToken(_typ, t, t1.getPrevious());
            if ((t1 instanceof com.pullenti.ner.ReferentToken) && (t1.getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
                AddressItemToken nn = tryParsePureItem(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.ReferentToken.class)).getBeginToken(), null, null);
                if (nn != null && nn.getEndChar() == t1.getEndChar() && nn.getTyp() == AddressItemType.NUMBER) {
                    nn.setBeginToken(t);
                    nn.setEndToken(t1);
                    nn.setTyp(_typ);
                    return nn;
                }
            }
            if ((t1 instanceof com.pullenti.ner.TextToken) && ((_typ == AddressItemType.HOUSE || _typ == AddressItemType.BUILDING || _typ == AddressItemType.CORPUS))) {
                String ter = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(ter, "АБ") || com.pullenti.unisharp.Utils.stringsEq(ter, "АБВ") || com.pullenti.unisharp.Utils.stringsEq(ter, "МГУ")) 
                    return _new81(_typ, t, t1, ter, houseTyp, buildTyp);
                String ccc = _corrNumber(ter);
                if (ccc != null) 
                    return _new81(_typ, t, t1, ccc, houseTyp, buildTyp);
                if (t1.chars.isAllUpper()) {
                    if (prev != null && ((prev.getTyp() == AddressItemType.STREET || prev.getTyp() == AddressItemType.CITY))) 
                        return _new81(_typ, t, t1, ter, houseTyp, buildTyp);
                    if (_typ == AddressItemType.CORPUS && (t1.getLengthChar() < 4)) 
                        return _new81(_typ, t, t1, ter, houseTyp, buildTyp);
                }
            }
            if (_typ == AddressItemType.BOX) {
                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t1);
                if (rom != null) 
                    return _new74(_typ, t, rom.getEndToken(), rom.getValue().toString());
            }
            if (_typ == AddressItemType.PLOT && t1 != null) {
                if ((t1.isValue("ОКОЛО", null) || t1.isValue("РЯДОМ", null) || t1.isValue("НАПРОТИВ", null)) || t1.isValue("БЛИЗЬКО", null) || t1.isValue("НАВПАКИ", null)) 
                    return _new74(_typ, t, t1, t1.getSourceText().toLowerCase());
            }
            return null;
        }
        if (_typ == AddressItemType.NUMBER && prepos) 
            return null;
        if (t1 == null) {
            t1 = t;
            while (t1.getNext() != null) {
                t1 = t1.getNext();
            }
        }
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.getEndChar() <= t1.getEndChar(); tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                return null;
        }
        return _new92(_typ, t, t1, num.toString(), t.getMorph(), houseTyp, buildTyp);
    }

    private static AddressItemToken _tryAttachVCH(com.pullenti.ner.Token t, AddressItemType ty) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token tt = t;
        if ((((tt.isValue("В", null) || tt.isValue("B", null))) && tt.getNext() != null && tt.getNext().isCharOf("./\\")) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getNext().isValue("Ч", null)) {
            tt = tt.getNext().getNext();
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                tt = tt.getNext();
            com.pullenti.ner.Token tt2 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt.getNext());
            if (tt2 != null) 
                tt = tt2;
            if (tt.getNext() != null && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && (tt.getWhitespacesAfterCount() < 2)) 
                tt = tt.getNext();
            return _new74(ty, t, tt, "В/Ч");
        }
        if (((tt.isValue("ВОЙСКОВОЙ", null) || tt.isValue("ВОИНСКИЙ", null))) && tt.getNext() != null && tt.getNext().isValue("ЧАСТЬ", null)) {
            tt = tt.getNext();
            com.pullenti.ner.Token tt2 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt.getNext());
            if (tt2 != null) 
                tt = tt2;
            if (tt.getNext() != null && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && (tt.getWhitespacesAfterCount() < 2)) 
                tt = tt.getNext();
            return _new74(ty, t, tt, "В/Ч");
        }
        if (ty == AddressItemType.FLAT) {
            if (tt.getWhitespacesBeforeCount() > 1) 
                return null;
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                return null;
            if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term.startsWith("ОБЩ")) {
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                AddressItemToken re = _tryAttachVCH(tt.getNext(), ty);
                if (re != null) 
                    return re;
                return _new74(ty, t, tt, "ОБЩ");
            }
            if (tt.chars.isAllUpper() && tt.getLengthChar() > 1) {
                AddressItemToken re = _new74(ty, t, tt, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isAllUpper()) {
                    tt = tt.getNext();
                    re.setEndToken(tt);
                    re.value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                }
                return re;
            }
        }
        return null;
    }

    private static AddressItemToken _tryAttachDetail(com.pullenti.ner.Token t, com.pullenti.ner.core.TerminToken tok) {
        if (t == null || (t instanceof com.pullenti.ner.ReferentToken)) 
            return null;
        com.pullenti.ner.Token tt = t;
        if (t.chars.isCapitalUpper() && !t.getMorph()._getClass().isPreposition()) 
            return null;
        if (tok == null) 
            tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) {
            tt = t.getNext();
            if (tt instanceof com.pullenti.ner.NumberToken) {
            }
            else {
                if (tt.chars.isCapitalUpper() && !tt.getMorph()._getClass().isPreposition()) 
                    return null;
                tok = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            }
        }
        AddressItemToken res = null;
        boolean firstNum = false;
        if (tok == null) {
            if (tt instanceof com.pullenti.ner.NumberToken) {
                firstNum = true;
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                if (nex != null && ((nex.exTyp == com.pullenti.ner.core.NumberExType.METER || nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER))) {
                    res = new AddressItemToken(AddressItemType.DETAIL, t, nex.getEndToken());
                    com.pullenti.ner.core.NumberExType tyy = com.pullenti.ner.core.NumberExType.METER;
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wraptyy97 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(tyy);
                    res.detailMeters = (int)nex.normalizeValue(wraptyy97);
                    tyy = wraptyy97.value;
                }
            }
            if (res == null) 
                return null;
        }
        else {
            if (!(tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType)) 
                return null;
            res = _new98(AddressItemType.DETAIL, t, tok.getEndToken(), (com.pullenti.ner.address.AddressDetailType)tok.termin.tag);
        }
        for (tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (tt instanceof com.pullenti.ner.ReferentToken) 
                break;
            if (!tt.getMorph()._getClass().isPreposition()) {
                if (tt.chars.isCapitalUpper() || tt.chars.isAllUpper()) 
                    break;
            }
            tok = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && (tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType)) {
                com.pullenti.ner.address.AddressDetailType ty = (com.pullenti.ner.address.AddressDetailType)tok.termin.tag;
                if (ty != com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                    if (ty == com.pullenti.ner.address.AddressDetailType.NEAR && res.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED && res.detailType != ty) {
                    }
                    else 
                        res.detailType = ty;
                }
                res.setEndToken((tt = tok.getEndToken()));
                continue;
            }
            if (tt.isValue("ОРИЕНТИР", null) || tt.isValue("НАПРАВЛЕНИЕ", null) || tt.isValue("ОТ", null)) {
                res.setEndToken(tt);
                continue;
            }
            if (tt.isComma() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null) {
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                if (nex != null && ((nex.exTyp == com.pullenti.ner.core.NumberExType.METER || nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER))) {
                    res.setEndToken((tt = nex.getEndToken()));
                    com.pullenti.ner.core.NumberExType tyy = com.pullenti.ner.core.NumberExType.METER;
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wraptyy99 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(tyy);
                    res.detailMeters = (int)nex.normalizeValue(wraptyy99);
                    tyy = wraptyy99.value;
                    continue;
                }
            }
            break;
        }
        if (firstNum && res.detailType == com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
            return null;
        if (res != null && res.getEndToken().getNext() != null && res.getEndToken().getNext().getMorph()._getClass().isPreposition()) {
            if (res.getEndToken().getWhitespacesAfterCount() == 1 && res.getEndToken().getNext().getWhitespacesAfterCount() == 1) 
                res.setEndToken(res.getEndToken().getNext());
        }
        return res;
    }

    public static boolean checkStreetAfter(com.pullenti.ner.Token t, boolean checkThisAndNotNext) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.isHiphen() || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        if (t.isNewlineBefore()) 
            return false;
        AddressItemToken ait = tryParse(t, false, null, null);
        if (ait == null || ait.getTyp() != AddressItemType.STREET) 
            return false;
        if (ait.refToken != null) {
            if (!ait.refTokenIsGsk) 
                return false;
            com.pullenti.ner.geo.internal.OrgItemToken oo = (com.pullenti.ner.geo.internal.OrgItemToken)com.pullenti.unisharp.Utils.cast(ait.refToken, com.pullenti.ner.geo.internal.OrgItemToken.class);
            if (oo != null && oo.isDoubt) 
                return false;
        }
        if (!checkThisAndNotNext) 
            return true;
        if (t.getNext() == null || ait.getEndChar() <= t.getEndChar()) 
            return true;
        AddressItemToken ait2 = tryParse(t.getNext(), false, null, null);
        if (ait2 == null) 
            return true;
        java.util.ArrayList<AddressItemToken> aits1 = tryParseList(t, 20);
        java.util.ArrayList<AddressItemToken> aits2 = tryParseList(t.getNext(), 20);
        if (aits1 != null && aits2 != null) {
            if (aits2.get(aits2.size() - 1).getEndChar() >= aits1.get(aits1.size() - 1).getEndChar()) 
                return false;
        }
        return true;
    }

    public static boolean checkHouseAfter(com.pullenti.ner.Token t, boolean leek, boolean pureHouse) {
        if (t == null) 
            return false;
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        if (t.isNewlineBefore()) 
            return false;
        AddressItemToken ait = tryParsePureItem(t, null, null);
        if (ait != null) {
            if (pureHouse) 
                return ait.getTyp() == AddressItemType.HOUSE || ait.getTyp() == AddressItemType.PLOT;
            if ((ait.getTyp() == AddressItemType.HOUSE || ait.getTyp() == AddressItemType.FLOOR || ait.getTyp() == AddressItemType.OFFICE) || ait.getTyp() == AddressItemType.FLAT || ait.getTyp() == AddressItemType.PLOT) {
                if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && t.getNext() != null) && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                    return false;
                if ((t instanceof com.pullenti.ner.TextToken) && t.getNext() == ait.getEndToken() && t.getNext().isHiphen()) 
                    return false;
                return true;
            }
            if (leek) {
                if (ait.getTyp() == AddressItemType.NUMBER) 
                    return true;
            }
            if (ait.getTyp() == AddressItemType.NUMBER) {
                com.pullenti.ner.Token t1 = t.getNext();
                while (t1 != null && t1.isCharOf(".,")) {
                    t1 = t1.getNext();
                }
                ait = tryParsePureItem(t1, null, null);
                if (ait != null && (((ait.getTyp() == AddressItemType.BUILDING || ait.getTyp() == AddressItemType.CORPUS || ait.getTyp() == AddressItemType.FLAT) || ait.getTyp() == AddressItemType.FLOOR || ait.getTyp() == AddressItemType.OFFICE))) 
                    return true;
            }
        }
        return false;
    }

    public static boolean checkKmAfter(com.pullenti.ner.Token t) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        AddressItemToken km = tryParsePureItem(t, null, null);
        if (km != null && km.getTyp() == AddressItemType.KILOMETER) 
            return true;
        if (!(t instanceof com.pullenti.ner.NumberToken) || t.getNext() == null) 
            return false;
        if (t.getNext().isValue("КИЛОМЕТР", null) || t.getNext().isValue("МЕТР", null) || t.getNext().isValue("КМ", null)) 
            return true;
        return false;
    }

    public static boolean checkKmBefore(com.pullenti.ner.Token t) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getPrevious(),cou++) {
            if (t.isCharOf(",.")) {
            }
            else if (t.isValue("КМ", null) || t.isValue("КИЛОМЕТР", null) || t.isValue("МЕТР", null)) 
                return true;
        }
        return false;
    }

    public static char correctChar(char v) {
        if (v == 'A' || v == 'А') 
            return 'А';
        if (v == 'Б' || v == 'Г') 
            return v;
        if (v == 'B' || v == 'В') 
            return 'В';
        if (v == 'C' || v == 'С') 
            return 'С';
        if (v == 'D' || v == 'Д') 
            return 'Д';
        if (v == 'E' || v == 'Е') 
            return 'Е';
        if (v == 'H' || v == 'Н') 
            return 'Н';
        if (v == 'K' || v == 'К') 
            return 'К';
        return (char)0;
    }

    private static String correctCharToken(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        String v = tt.term;
        if (v.length() != 1) 
            return null;
        char corr = correctChar(v.charAt(0));
        if (corr != ((char)0)) 
            return (String.valueOf(corr));
        if (t.chars.isCyrillicLetter()) 
            return v;
        return null;
    }

    private static String _corrNumber(String num) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(num)) 
            return null;
        if (num.charAt(0) != 'З') 
            return null;
        String res = "3";
        int i;
        for (i = 1; i < num.length(); i++) {
            if (num.charAt(i) == 'З') 
                res += "3";
            else if (num.charAt(i) == 'О') 
                res += "0";
            else 
                break;
        }
        if (i == num.length()) 
            return res;
        if ((i + 1) < num.length()) 
            return null;
        if (num.charAt(i) == 'А' || num.charAt(i) == 'Б' || num.charAt(i) == 'В') 
            return (res + num.charAt(i));
        return null;
    }

    public static void initialize() throws Exception, java.io.IOException {
        if (m_Ontology != null) 
            return;
        StreetItemToken.initialize();
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new100("ДОМ", AddressItemType.HOUSE);
        t.addAbridge("Д.");
        t.addVariant("КОТТЕДЖ", false);
        t.addAbridge("КОТ.");
        t.addVariant("ДАЧА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new101("БУДИНОК", AddressItemType.HOUSE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("Б.");
        t.addVariant("КОТЕДЖ", false);
        t.addAbridge("БУД.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("ВЛАДЕНИЕ", AddressItemType.HOUSE, com.pullenti.ner.address.AddressHouseType.ESTATE);
        t.addAbridge("ВЛАД.");
        t.addAbridge("ВЛД.");
        t.addAbridge("ВЛ.");
        m_Ontology.add(t);
        M_OWNER = t;
        t = com.pullenti.ner.core.Termin._new102("ДОМОВЛАДЕНИЕ", AddressItemType.HOUSE, com.pullenti.ner.address.AddressHouseType.HOUSEESTATE);
        t.addVariant("ДОМОВЛАДЕНИЕ", false);
        t.addAbridge("ДВЛД.");
        t.addAbridge("ДМВЛД.");
        t.addVariant("ДОМОВЛ", false);
        t.addVariant("ДОМОВА", false);
        t.addVariant("ДОМОВЛАД", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПОДЪЕЗД ДОМА", AddressItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПОДВАЛ ДОМА", AddressItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КРЫША ДОМА", AddressItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЭТАЖ", AddressItemType.FLOOR);
        t.addAbridge("ЭТ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПОДЪЕЗД", AddressItemType.POTCH);
        t.addAbridge("ПОД.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КОРПУС", AddressItemType.CORPUS);
        t.addAbridge("КОРП.");
        t.addAbridge("КОР.");
        t.addAbridge("Д.КОРП.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("К", AddressItemType.CORPUSORFLAT);
        t.addAbridge("К.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("СТРОЕНИЕ", AddressItemType.BUILDING);
        t.addAbridge("СТРОЕН.");
        t.addAbridge("СТР.");
        t.addAbridge("СТ.");
        t.addAbridge("ПОМ.СТР.");
        t.addAbridge("Д.СТР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("СООРУЖЕНИЕ", AddressItemType.BUILDING, com.pullenti.ner.address.AddressBuildingType.CONSTRUCTION);
        t.addAbridge("СООР.");
        t.addAbridge("СООРУЖ.");
        t.addAbridge("СООРУЖЕН.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("ЛИТЕРА", AddressItemType.BUILDING, com.pullenti.ner.address.AddressBuildingType.LITER);
        t.addAbridge("ЛИТ.");
        t.addVariant("ЛИТЕР", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("УЧАСТОК", AddressItemType.PLOT);
        t.addAbridge("УЧАСТ.");
        t.addAbridge("УЧ.");
        t.addAbridge("УЧ-К");
        t.addVariant("ЗЕМЕЛЬНЫЙ УЧАСТОК", false);
        t.addAbridge("ЗЕМ.УЧ.");
        t.addAbridge("ЗЕМ.УЧ-К");
        t.addAbridge("З/У");
        t.addAbridge("ПОЗ.");
        m_Ontology.add(t);
        M_PLOT = t;
        t = com.pullenti.ner.core.Termin._new100("ПОЛЕ", AddressItemType.FIELD);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КВАРТИРА", AddressItemType.FLAT);
        t.addAbridge("КВАРТ.");
        t.addAbridge("КВАР.");
        t.addAbridge("КВ.");
        t.addAbridge("KB.");
        t.addAbridge("КВ-РА");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОФИС", AddressItemType.OFFICE);
        t.addAbridge("ОФ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new101("ОФІС", AddressItemType.OFFICE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("ОФ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПАВИЛЬОН", AddressItemType.PAVILION);
        t.addAbridge("ПАВ.");
        t.addVariant("ТОРГОВЫЙ ПАВИЛЬОН", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new101("ПАВІЛЬЙОН", AddressItemType.PAVILION, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("ПАВ.");
        t.addVariant("ТОРГОВИЙ ПАВІЛЬЙОН", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЛОК", AddressItemType.BLOCK);
        t.addVariant("РЯД", false);
        t.addVariant("СЕКТОР", false);
        t.addAbridge("СЕК.");
        t.addVariant("МАССИВ", false);
        t.addVariant("ОЧЕРЕДЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("БОКС", AddressItemType.BOX);
        t.addVariant("ГАРАЖ", false);
        t.addVariant("САРАЙ", false);
        t.addAbridge("ГАР.");
        t.addVariant("МАШИНОМЕСТО", false);
        t.addVariant("ПОМЕЩЕНИЕ", false);
        t.addAbridge("ПОМ.");
        t.addVariant("НЕЖИЛОЕ ПОМЕЩЕНИЕ", false);
        t.addAbridge("Н.П.");
        t.addAbridge("НП");
        t.addVariant("ПОДВАЛ", false);
        t.addVariant("ПОГРЕБ", false);
        t.addVariant("ПОДВАЛЬНОЕ ПОМЕЩЕНИЕ", false);
        t.addVariant("ПОДЪЕЗД", false);
        t.addAbridge("ГАРАЖ-БОКС");
        t.addVariant("ГАРАЖНЫЙ БОКС", false);
        t.addAbridge("ГБ.");
        t.addAbridge("Г.Б.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КОМНАТА", AddressItemType.OFFICE);
        t.addAbridge("КОМ.");
        t.addAbridge("КОМН.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КАБИНЕТ", AddressItemType.OFFICE);
        t.addAbridge("КАБ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НОМЕР", AddressItemType.NUMBER);
        t.addAbridge("НОМ.");
        t.addAbridge("№");
        t.addAbridge("N");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new126("БЕЗ НОМЕРА", "Б/Н", AddressItemType.NONUMBER);
        t.addAbridge("Б.Н.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("АБОНЕНТСКИЙ ЯЩИК", AddressItemType.POSTOFFICEBOX);
        t.addAbridge("А.Я.");
        t.addVariant("ПОЧТОВЫЙ ЯЩИК", false);
        t.addAbridge("П.Я.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new128("ГОРОДСКАЯ СЛУЖЕБНАЯ ПОЧТА", AddressItemType.CSP, "ГСП");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("АДРЕС", AddressItemType.PREFIX);
        t.addVariant("ЮРИДИЧЕСКИЙ АДРЕС", false);
        t.addVariant("ФАКТИЧЕСКИЙ АДРЕС", false);
        t.addAbridge("ЮР.АДРЕС");
        t.addAbridge("ПОЧТ.АДРЕС");
        t.addAbridge("ФАКТ.АДРЕС");
        t.addAbridge("П.АДРЕС");
        t.addVariant("ЮРИДИЧЕСКИЙ/ФАКТИЧЕСКИЙ АДРЕС", false);
        t.addVariant("ПОЧТОВЫЙ АДРЕС", false);
        t.addVariant("АДРЕС ПРОЖИВАНИЯ", false);
        t.addVariant("МЕСТО НАХОЖДЕНИЯ", false);
        t.addVariant("МЕСТОНАХОЖДЕНИЕ", false);
        t.addVariant("МЕСТОПОЛОЖЕНИЕ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("АДРЕСА", AddressItemType.PREFIX);
        t.addVariant("ЮРИДИЧНА АДРЕСА", false);
        t.addVariant("ФАКТИЧНА АДРЕСА", false);
        t.addVariant("ПОШТОВА АДРЕСА", false);
        t.addVariant("АДРЕСА ПРОЖИВАННЯ", false);
        t.addVariant("МІСЦЕ ПЕРЕБУВАННЯ", false);
        t.addVariant("ПРОПИСКА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КИЛОМЕТР", AddressItemType.KILOMETER);
        t.addAbridge("КИЛОМ.");
        t.addAbridge("КМ.");
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПЕРЕСЕЧЕНИЕ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("НА ПЕРЕСЕЧЕНИИ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПЕРЕКРЕСТОК", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("НА ПЕРЕКРЕСТКЕ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("НА ТЕРРИТОРИИ", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕРЕДИНА", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПРИМЫКАТЬ", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ГРАНИЧИТЬ", com.pullenti.ner.address.AddressDetailType.NEAR));
        t = com.pullenti.ner.core.Termin._new100("ВБЛИЗИ", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addVariant("У", false);
        t.addAbridge("ВБЛ.");
        t.addVariant("ВОЗЛЕ", false);
        t.addVariant("ОКОЛО", false);
        t.addVariant("НЕДАЛЕКО ОТ", false);
        t.addVariant("РЯДОМ С", false);
        t.addVariant("ГРАНИЦА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("РАЙОН", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addAbridge("Р-Н");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new126("В РАЙОНЕ", "РАЙОН", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addAbridge("В Р-НЕ");
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПРИМЕРНО", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПОРЯДКА", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ПРИБЛИЗИТЕЛЬНО", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ОРИЕНТИР", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("НАПРАВЛЕНИЕ", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        t = com.pullenti.ner.core.Termin._new100("ОБЩЕЖИТИЕ", com.pullenti.ner.address.AddressDetailType.HOSTEL);
        t.addAbridge("ОБЩ.");
        t.addAbridge("ПОМ.ОБЩ.");
        m_Ontology.add(t);
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕРНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕР", com.pullenti.ner.address.AddressDetailType.NORTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮЖНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮГ", com.pullenti.ner.address.AddressDetailType.SOUTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.WEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЗАПАД", com.pullenti.ner.address.AddressDetailType.WEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.EAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ВОСТОК", com.pullenti.ner.address.AddressDetailType.EAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕРО-ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕРО-ЗАПАД", com.pullenti.ner.address.AddressDetailType.NORTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕРО-ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("СЕВЕРО-ВОСТОК", com.pullenti.ner.address.AddressDetailType.NORTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮГО-ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮГО-ЗАПАД", com.pullenti.ner.address.AddressDetailType.SOUTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮГО-ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ЮГО-ВОСТОК", com.pullenti.ner.address.AddressDetailType.SOUTHEAST));
        t = new com.pullenti.ner.core.Termin("ТАМ ЖЕ", null, false);
        t.addAbridge("ТАМЖЕ");
        m_Ontology.add(t);
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static com.pullenti.ner.core.Termin M_PLOT;

    public static com.pullenti.ner.core.Termin M_OWNER;

    public static java.util.ArrayList<AddressItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount) {
        if (t == null) 
            return null;
        com.pullenti.ner.geo.internal.GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad != null) {
            if (ad.level > 0) 
                return null;
            ad.level++;
        }
        java.util.ArrayList<AddressItemToken> res = tryParseListInt(t, maxCount);
        if (ad != null) 
            ad.level--;
        if (res != null && res.size() == 0) 
            return null;
        return res;
    }

    private static java.util.ArrayList<AddressItemToken> tryParseListInt(com.pullenti.ner.Token t, int maxCount) {
        if (t instanceof com.pullenti.ner.NumberToken) {
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() == null) 
                return null;
            int v = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
            if ((v < 100000) || v >= 10000000) {
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && !t.getMorph()._getClass().isAdjective()) {
                    if (t.getNext() == null || (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        if (t.getPrevious() == null || !t.getPrevious().getMorph()._getClass().isPreposition()) 
                            return null;
                    }
                }
            }
        }
        AddressItemToken it = tryParse(t, false, null, null);
        if (it == null) 
            return null;
        if (it.getTyp() == AddressItemType.NUMBER) 
            return null;
        if (it.getTyp() == AddressItemType.KILOMETER && (it.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken)) {
            it = it.clone();
            it.setBeginToken(it.getBeginToken().getPrevious());
            it.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(it.getBeginToken(), com.pullenti.ner.NumberToken.class)).getValue().toString();
            if (it.getBeginToken().getPrevious() != null && it.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                it.setBeginToken(it.getBeginToken().getPrevious());
        }
        java.util.ArrayList<AddressItemToken> res = new java.util.ArrayList<AddressItemToken>();
        res.add(it);
        boolean pref = it.getTyp() == AddressItemType.PREFIX;
        for (t = it.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            AddressItemToken last = res.get(res.size() - 1);
            if (res.size() > 1) {
                if (last.isNewlineBefore() && res.get(res.size() - 2).getTyp() != AddressItemType.PREFIX) {
                    int i;
                    for (i = 0; i < (res.size() - 1); i++) {
                        if (res.get(i).getTyp() == last.getTyp()) {
                            if (i == (res.size() - 2) && ((last.getTyp() == AddressItemType.CITY || last.getTyp() == AddressItemType.REGION))) {
                                int jj;
                                for (jj = 0; jj < i; jj++) {
                                    if ((res.get(jj).getTyp() != AddressItemType.PREFIX && res.get(jj).getTyp() != AddressItemType.ZIP && res.get(jj).getTyp() != AddressItemType.REGION) && res.get(jj).getTyp() != AddressItemType.COUNTRY) 
                                        break;
                                }
                                if (jj >= i) 
                                    continue;
                            }
                            break;
                        }
                    }
                    if ((i < (res.size() - 1)) || last.getTyp() == AddressItemType.ZIP) {
                        res.remove(last);
                        break;
                    }
                }
            }
            if (t.isTableControlChar()) 
                break;
            if (t.isChar(',')) 
                continue;
            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false) && last.getTyp() == AddressItemType.STREET) 
                continue;
            if (t.isChar('.')) {
                if (t.isNewlineAfter()) 
                    break;
                if (t.getPrevious() != null && t.getPrevious().isChar('.')) 
                    break;
                continue;
            }
            if (t.isHiphen() || t.isChar('_')) {
                if (((it.getTyp() == AddressItemType.NUMBER || it.getTyp() == AddressItemType.STREET)) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                    continue;
            }
            if (it.getTyp() == AddressItemType.DETAIL && it.detailType == com.pullenti.ner.address.AddressDetailType.CROSS) {
                AddressItemToken str1 = tryParse(t, true, null, null);
                if (str1 != null && str1.getTyp() == AddressItemType.STREET) {
                    if (str1.getEndToken().getNext() != null && ((str1.getEndToken().getNext().isAnd() || str1.getEndToken().getNext().isHiphen()))) {
                        AddressItemToken str2 = tryParse(str1.getEndToken().getNext().getNext(), true, null, null);
                        if (str2 == null || str2.getTyp() != AddressItemType.STREET) {
                            str2 = StreetDefineHelper.tryParseSecondStreet(str1.getBeginToken(), str1.getEndToken().getNext().getNext());
                            if (str2 != null && str2.isDoubt) {
                                str2 = str2.clone();
                                str2.isDoubt = false;
                            }
                        }
                        if (str2 != null && str2.getTyp() == AddressItemType.STREET) {
                            res.add(str1);
                            res.add(str2);
                            t = str2.getEndToken();
                            it = str2;
                            continue;
                        }
                    }
                }
            }
            boolean pre = pref;
            if (it.getTyp() == AddressItemType.KILOMETER || it.getTyp() == AddressItemType.HOUSE) {
                if (!t.isNewlineBefore()) 
                    pre = true;
            }
            AddressItemToken it0 = tryParse(t, pre, it, null);
            if (it0 == null) {
                if (t.getNewlinesBeforeCount() > 2) 
                    break;
                if (it.getTyp() == AddressItemType.POSTOFFICEBOX) 
                    break;
                com.pullenti.ner.Token tt1 = StreetItemToken.checkStdName(t);
                if (tt1 != null) {
                    t = tt1;
                    continue;
                }
                if (t.getMorph()._getClass().isPreposition()) {
                    it0 = tryParse(t.getNext(), false, it, null);
                    if (it0 != null && it0.getTyp() == AddressItemType.BUILDING && it0.getBeginToken().isValue("СТ", null)) {
                        it0 = null;
                        break;
                    }
                    if (it0 != null) {
                        if ((it0.getTyp() == AddressItemType.DETAIL && it.getTyp() == AddressItemType.CITY && it.detailMeters > 0) && it.detailType == com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                            it.detailType = it0.detailType;
                            t = it.setEndToken(it0.getEndToken());
                            continue;
                        }
                        if ((it0.getTyp() == AddressItemType.HOUSE || it0.getTyp() == AddressItemType.BUILDING || it0.getTyp() == AddressItemType.CORPUS) || it0.getTyp() == AddressItemType.STREET || it0.getTyp() == AddressItemType.DETAIL) {
                            res.add((it = it0));
                            t = it.getEndToken();
                            continue;
                        }
                    }
                }
                if (it.getTyp() == AddressItemType.HOUSE || it.getTyp() == AddressItemType.BUILDING || it.getTyp() == AddressItemType.NUMBER) {
                    if ((!t.isWhitespaceBefore() && t.getLengthChar() == 1 && t.chars.isLetter()) && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        String ch = correctCharToken(t);
                        if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                            it0 = _new74((com.pullenti.unisharp.Utils.stringsEq(ch, "К") ? AddressItemType.CORPUS : AddressItemType.BUILDING), t, t.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                            it = it0;
                            res.add(it);
                            t = it.getEndToken();
                            com.pullenti.ner.Token tt = t.getNext();
                            if (((tt != null && !tt.isWhitespaceBefore() && tt.getLengthChar() == 1) && tt.chars.isLetter() && !tt.isWhitespaceAfter()) && (tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                                ch = correctCharToken(tt);
                                if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                                    it = _new74((com.pullenti.unisharp.Utils.stringsEq(ch, "К") ? AddressItemType.CORPUS : AddressItemType.BUILDING), tt, tt.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                                    res.add(it);
                                    t = it.getEndToken();
                                }
                            }
                            continue;
                        }
                    }
                }
                if (t.getMorph()._getClass().isPreposition()) {
                    if ((((t.isValue("У", null) || t.isValue("ВОЗЛЕ", null) || t.isValue("НАПРОТИВ", null)) || t.isValue("НА", null) || t.isValue("В", null)) || t.isValue("ВО", null) || t.isValue("ПО", null)) || t.isValue("ОКОЛО", null)) 
                        continue;
                }
                if (t.getMorph()._getClass().isNoun()) {
                    if ((t.isValue("ДВОР", null) || t.isValue("ПОДЪЕЗД", null) || t.isValue("КРЫША", null)) || t.isValue("ПОДВАЛ", null)) 
                        continue;
                }
                if (t.isValue("ТЕРРИТОРИЯ", "ТЕРИТОРІЯ")) 
                    continue;
                if (t.isChar('(') && t.getNext() != null) {
                    it0 = tryParse(t.getNext(), pre, null, null);
                    if (it0 != null && it0.getEndToken().getNext() != null && it0.getEndToken().getNext().isChar(')')) {
                        it0 = it0.clone();
                        it0.setBeginToken(t);
                        it0.setEndToken(it0.getEndToken().getNext());
                        it = it0;
                        res.add(it);
                        t = it.getEndToken();
                        continue;
                    }
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getLengthChar() < 100)) {
                        if (t.getNext().isValue("БЫВШИЙ", null) || t.getNext().isValue("БЫВШ", null)) {
                            it = new AddressItemToken(AddressItemType.DETAIL, t, br.getEndToken());
                            res.add(it);
                        }
                        t = br.getEndToken();
                        continue;
                    }
                }
                boolean checkKv = false;
                if (t.isValue("КВ", null) || t.isValue("KB", null)) {
                    if (it.getTyp() == AddressItemType.NUMBER && res.size() > 1 && res.get(res.size() - 2).getTyp() == AddressItemType.STREET) 
                        checkKv = true;
                    else if ((it.getTyp() == AddressItemType.HOUSE || it.getTyp() == AddressItemType.BUILDING || it.getTyp() == AddressItemType.CORPUS) || it.getTyp() == AddressItemType.CORPUSORFLAT) {
                        for (int jj = res.size() - 2; jj >= 0; jj--) {
                            if (res.get(jj).getTyp() == AddressItemType.STREET || res.get(jj).getTyp() == AddressItemType.CITY) 
                                checkKv = true;
                        }
                    }
                    if (checkKv) {
                        com.pullenti.ner.Token tt2 = t.getNext();
                        if (tt2 != null && tt2.isChar('.')) 
                            tt2 = tt2.getNext();
                        AddressItemToken it22 = tryParsePureItem(tt2, null, null);
                        if (it22 != null && it22.getTyp() == AddressItemType.NUMBER) {
                            it22 = it22.clone();
                            it22.setBeginToken(t);
                            it22.setTyp(AddressItemType.FLAT);
                            res.add(it22);
                            t = it22.getEndToken();
                            continue;
                        }
                    }
                }
                if (res.get(res.size() - 1).getTyp() == AddressItemType.CITY) {
                    if (((t.isHiphen() || t.isChar('_') || t.isValue("НЕТ", null))) && t.getNext() != null && t.getNext().isComma()) {
                        AddressItemToken att = tryParsePureItem(t.getNext().getNext(), null, null);
                        if (att != null) {
                            if (att.getTyp() == AddressItemType.HOUSE || att.getTyp() == AddressItemType.BUILDING || att.getTyp() == AddressItemType.CORPUS) {
                                it = new AddressItemToken(AddressItemType.STREET, t, t);
                                res.add(it);
                                continue;
                            }
                        }
                    }
                }
                if (t.getLengthChar() == 2 && (t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper()) {
                    String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(term) && term.charAt(0) == 'Р') 
                        continue;
                }
                break;
            }
            if (t.getWhitespacesBeforeCount() > 15) {
                if (it0.getTyp() == AddressItemType.STREET && last.getTyp() == AddressItemType.CITY) {
                }
                else 
                    break;
            }
            if (t.isNewlineBefore() && it0.getTyp() == AddressItemType.STREET && it0.refToken != null) {
                if (!it0.refTokenIsGsk) 
                    break;
            }
            if (it0.getTyp() == AddressItemType.STREET && t.isValue("КВ", null)) {
                if (it != null) {
                    if (it.getTyp() == AddressItemType.HOUSE || it.getTyp() == AddressItemType.BUILDING || it.getTyp() == AddressItemType.CORPUS) {
                        AddressItemToken it2 = tryParsePureItem(t, null, null);
                        if (it2 != null && it2.getTyp() == AddressItemType.FLAT) 
                            it0 = it2;
                    }
                }
            }
            if (it0.getTyp() == AddressItemType.PREFIX) 
                break;
            if (it0.getTyp() == AddressItemType.NUMBER) {
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(it0.value)) 
                    break;
                if (!Character.isDigit(it0.value.charAt(0))) 
                    break;
                int cou = 0;
                for (int i = res.size() - 1; i >= 0; i--) {
                    if (res.get(i).getTyp() == AddressItemType.NUMBER) 
                        cou++;
                    else 
                        break;
                }
                if (cou > 5) 
                    break;
                if (it.isDoubt && t.isNewlineBefore()) 
                    break;
            }
            if (it0.getTyp() == AddressItemType.CORPUSORFLAT && it != null && it.getTyp() == AddressItemType.FLAT) 
                it0.setTyp(AddressItemType.OFFICE);
            if (((((it0.getTyp() == AddressItemType.FLOOR || it0.getTyp() == AddressItemType.POTCH || it0.getTyp() == AddressItemType.BLOCK) || it0.getTyp() == AddressItemType.KILOMETER)) && com.pullenti.unisharp.Utils.isNullOrEmpty(it0.value) && it.getTyp() == AddressItemType.NUMBER) && it.getEndToken().getNext() == it0.getBeginToken()) {
                it = it.clone();
                com.pullenti.unisharp.Utils.putArrayValue(res, res.size() - 1, it);
                it.setTyp(it0.getTyp());
                it.setEndToken(it0.getEndToken());
            }
            else if ((((it.getTyp() == AddressItemType.FLOOR || it.getTyp() == AddressItemType.POTCH)) && com.pullenti.unisharp.Utils.isNullOrEmpty(it.value) && it0.getTyp() == AddressItemType.NUMBER) && it.getEndToken().getNext() == it0.getBeginToken()) {
                it = it.clone();
                com.pullenti.unisharp.Utils.putArrayValue(res, res.size() - 1, it);
                it.value = it0.value;
                it.setEndToken(it0.getEndToken());
            }
            else {
                it = it0;
                res.add(it);
            }
            t = it.getEndToken();
        }
        if (res.size() > 0) {
            it = res.get(res.size() - 1);
            AddressItemToken it0 = (res.size() > 1 ? res.get(res.size() - 2) : null);
            if (it.getTyp() == AddressItemType.NUMBER && it0 != null && it0.refToken != null) {
                for (com.pullenti.ner.Slot s : it0.refToken.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                        String ss = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        if ((ss.indexOf("гараж") >= 0) || ((ss.charAt(0) == 'Г' && ss.charAt(ss.length() - 1) == 'К'))) {
                            it.setTyp(AddressItemType.BOX);
                            break;
                        }
                    }
                }
            }
            if (it.getTyp() == AddressItemType.NUMBER || it.getTyp() == AddressItemType.ZIP) {
                boolean del = false;
                if (it.getBeginToken().getPrevious() != null && it.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                    del = true;
                else if (it.getMorph()._getClass().isNoun()) 
                    del = true;
                if ((!del && it.getEndToken().getWhitespacesAfterCount() == 1 && it.getWhitespacesBeforeCount() > 0) && it.getTyp() == AddressItemType.NUMBER) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(it.getEndToken().getNext());
                    if (npt != null) 
                        del = true;
                }
                if (del) 
                    res.remove(res.size() - 1);
                else if ((it.getTyp() == AddressItemType.NUMBER && it0 != null && it0.getTyp() == AddressItemType.STREET) && it0.refToken == null) {
                    if (it.getBeginToken().getPrevious().isChar(',') || it.isNewlineAfter()) {
                        it = it.clone();
                        com.pullenti.unisharp.Utils.putArrayValue(res, res.size() - 1, it);
                        it.setTyp(AddressItemType.HOUSE);
                        it.isDoubt = true;
                    }
                }
            }
        }
        if (res.size() == 0) 
            return null;
        for (AddressItemToken r : res) {
            if (r.getTyp() == AddressItemType.CITY) {
                AddressItemToken ty = _findAddrTyp(r.getBeginToken(), r.getEndChar(), 0);
                if (ty != null) {
                    if (r.detailType == com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
                        r.detailType = ty.detailType;
                    if (ty.detailMeters > 0) 
                        r.detailMeters = ty.detailMeters;
                }
            }
        }
        for (int i = 0; i < (res.size() - 2); i++) {
            if (res.get(i).getTyp() == AddressItemType.STREET && res.get(i + 1).getTyp() == AddressItemType.NUMBER) {
                if ((res.get(i + 2).getTyp() == AddressItemType.BUILDING || res.get(i + 2).getTyp() == AddressItemType.CORPUS || res.get(i + 2).getTyp() == AddressItemType.OFFICE) || res.get(i + 2).getTyp() == AddressItemType.FLAT) {
                    com.pullenti.unisharp.Utils.putArrayValue(res, i + 1, res.get(i + 1).clone());
                    res.get(i + 1).setTyp(AddressItemType.HOUSE);
                }
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i).getTyp() == AddressItemType.STREET && res.get(i + 1).getTyp() == AddressItemType.KILOMETER && (res.get(i).referent instanceof com.pullenti.ner.address.StreetReferent)) && ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, com.pullenti.ner.address.StreetReferent.class)).getNumber() == null) {
                com.pullenti.unisharp.Utils.putArrayValue(res, i, res.get(i).clone());
                ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, com.pullenti.ner.address.StreetReferent.class)).setNumber(res.get(i + 1).value + "км");
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.remove(i + 1);
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i + 1).getTyp() == AddressItemType.STREET && res.get(i).getTyp() == AddressItemType.KILOMETER && (res.get(i + 1).referent instanceof com.pullenti.ner.address.StreetReferent)) && ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i + 1).referent, com.pullenti.ner.address.StreetReferent.class)).getNumber() == null) {
                com.pullenti.unisharp.Utils.putArrayValue(res, i + 1, res.get(i + 1).clone());
                ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i + 1).referent, com.pullenti.ner.address.StreetReferent.class)).setNumber(res.get(i).value + "км");
                res.get(i + 1).setBeginToken(res.get(i).getBeginToken());
                res.remove(i);
                break;
            }
        }
        while (res.size() > 0) {
            AddressItemToken last = res.get(res.size() - 1);
            if (last.getTyp() != AddressItemType.STREET || !(last.refToken instanceof com.pullenti.ner.geo.internal.OrgItemToken)) 
                break;
            if (((com.pullenti.ner.geo.internal.OrgItemToken)com.pullenti.unisharp.Utils.cast(last.refToken, com.pullenti.ner.geo.internal.OrgItemToken.class)).isGsk || ((com.pullenti.ner.geo.internal.OrgItemToken)com.pullenti.unisharp.Utils.cast(last.refToken, com.pullenti.ner.geo.internal.OrgItemToken.class)).hasTerrKeyword) 
                break;
            res.remove(res.size() - 1);
        }
        return res;
    }

    public static AddressItemToken _new72(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.Referent _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.referent = _arg4;
        return res;
    }

    public static AddressItemToken _new74(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        return res;
    }

    public static AddressItemToken _new79(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.address.AddressHouseType _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.houseType = _arg5;
        return res;
    }

    public static AddressItemToken _new80(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.address.AddressHouseType _arg4, com.pullenti.ner.address.AddressBuildingType _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.houseType = _arg4;
        res.buildingType = _arg5;
        return res;
    }

    public static AddressItemToken _new81(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.address.AddressHouseType _arg5, com.pullenti.ner.address.AddressBuildingType _arg6) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.houseType = _arg5;
        res.buildingType = _arg6;
        return res;
    }

    public static AddressItemToken _new92(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5, com.pullenti.ner.address.AddressHouseType _arg6, com.pullenti.ner.address.AddressBuildingType _arg7) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.setMorph(_arg5);
        res.houseType = _arg6;
        res.buildingType = _arg7;
        return res;
    }

    public static AddressItemToken _new98(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.address.AddressDetailType _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.detailType = _arg4;
        return res;
    }

    public static AddressItemToken _new167(AddressItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.Referent _arg4, boolean _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.referent = _arg4;
        res.isDoubt = _arg5;
        return res;
    }
    public AddressItemToken() {
        super();
    }
}
