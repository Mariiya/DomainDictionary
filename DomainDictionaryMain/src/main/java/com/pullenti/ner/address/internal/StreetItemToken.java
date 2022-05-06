/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class StreetItemToken extends com.pullenti.ner.MetaToken {

    public StreetItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public StreetItemType typ = StreetItemType.NOUN;

    public com.pullenti.ner.core.Termin termin;

    public com.pullenti.ner.core.Termin altTermin;

    public com.pullenti.ner.address.StreetReferent existStreet;

    public com.pullenti.ner.NumberToken number;

    public boolean numberHasPrefix;

    public boolean isNumberKm;

    public String value;

    public String altValue;

    public String altValue2;

    public boolean isAbridge;

    public boolean isInDictionary;

    public boolean isInBrackets;

    public boolean hasStdSuffix;

    public int nounIsDoubtCoef;

    public boolean nounCanBeName;

    public boolean isRoadName;

    public boolean isRoad() {
        if (termin == null) 
            return false;
        if ((com.pullenti.unisharp.Utils.stringsEq(termin.getCanonicText(), "АВТОДОРОГА") || com.pullenti.unisharp.Utils.stringsEq(termin.getCanonicText(), "ШОССЕ") || com.pullenti.unisharp.Utils.stringsEq(termin.getCanonicText(), "АВТОШЛЯХ")) || com.pullenti.unisharp.Utils.stringsEq(termin.getCanonicText(), "ШОСЕ")) 
            return true;
        return false;
    }


    public boolean isRailway;

    public com.pullenti.ner.geo.internal.Condition cond;

    public boolean noGeoInThisToken;

    public com.pullenti.ner.geo.internal.OrgItemToken _org;

    public StreetItemToken clone() {
        StreetItemToken res = new StreetItemToken(getBeginToken(), getEndToken());
        res.setMorph(this.getMorph());
        res.typ = typ;
        res.termin = termin;
        res.altTermin = altTermin;
        res.value = value;
        res.altValue = altValue;
        res.altValue2 = altValue2;
        res.isRailway = isRailway;
        res.isRoadName = isRoadName;
        res.nounCanBeName = nounCanBeName;
        res.nounIsDoubtCoef = nounIsDoubtCoef;
        res.hasStdSuffix = hasStdSuffix;
        res.isInBrackets = isInBrackets;
        res.isAbridge = isAbridge;
        res.isInDictionary = isInDictionary;
        res.existStreet = existStreet;
        res.number = number;
        res.numberHasPrefix = numberHasPrefix;
        res.isNumberKm = isNumberKm;
        res.cond = cond;
        res._org = _org;
        return res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString());
        if (value != null) {
            res.append(" ").append(value);
            if (altValue != null) 
                res.append("/").append(altValue);
        }
        if (existStreet != null) 
            res.append(" ").append(existStreet.toString());
        if (termin != null) {
            res.append(" ").append(termin.toString());
            if (altTermin != null) 
                res.append("/").append(altTermin.toString());
        }
        else if (number != null) {
            res.append(" ").append(number.toString());
            if (isNumberKm) 
                res.append("км");
        }
        else 
            res.append(" ").append(super.toString());
        if (_org != null) 
            res.append(" Org: ").append(_org);
        if (isAbridge) 
            res.append(" (?)");
        return res.toString();
    }

    private boolean _isSurname() {
        if (typ != StreetItemType.NAME) 
            return false;
        if (!(this.getEndToken() instanceof com.pullenti.ner.TextToken)) 
            return false;
        String nam = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(this.getEndToken(), com.pullenti.ner.TextToken.class)).term;
        if (nam.length() > 4) {
            if (com.pullenti.morph.LanguageHelper.endsWithEx(nam, "А", "Я", "КО", "ЧУКА")) {
                if (!com.pullenti.morph.LanguageHelper.endsWithEx(nam, "АЯ", "ЯЯ", null, null)) 
                    return true;
            }
        }
        return false;
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        com.pullenti.ner.geo.internal.GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.sRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            com.pullenti.ner.geo.internal.GeoTokenData d = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
            StreetItemToken prev = null;
            int kk = 0;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (kk < 10); tt = tt.getPrevious(),kk++) {
                com.pullenti.ner.geo.internal.GeoTokenData dd = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
                if (dd == null || dd.street == null) 
                    continue;
                if (dd.street.getEndToken().getNext() == t) 
                    prev = dd.street;
                if (t.getPrevious() != null && t.getPrevious().isHiphen() && dd.street.getEndToken().getNext() == t.getPrevious()) 
                    prev = dd.street;
            }
            StreetItemToken str = tryParse(t, prev, false, ad);
            if (str != null) {
                if (d == null) 
                    d = new com.pullenti.ner.geo.internal.GeoTokenData(t);
                d.street = str;
                if (str.noGeoInThisToken) {
                    for (com.pullenti.ner.Token tt = str.getBeginToken(); tt != null && tt.getEndChar() <= str.getEndChar(); tt = tt.getNext()) {
                        com.pullenti.ner.geo.internal.GeoTokenData dd = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
                        if (dd == null) 
                            dd = new com.pullenti.ner.geo.internal.GeoTokenData(tt);
                        dd.noGeo = true;
                    }
                }
            }
        }
        ad.sRegime = true;
    }

    public static StreetItemToken tryParse(com.pullenti.ner.Token t, StreetItemToken prev, boolean inSearch, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.isCharOf(",.:")) 
            return null;
        if (ad == null) 
            ad = (com.pullenti.ner.geo.internal.GeoAnalyzerData)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.geo.GeoAnalyzer.getData(t), com.pullenti.ner.geo.internal.GeoAnalyzerData.class);
        if (ad == null) 
            return null;
        if ((SPEEDREGIME && ((ad.sRegime || ad.allRegime)) && !inSearch) && !(t instanceof com.pullenti.ner.ReferentToken)) {
            com.pullenti.ner.geo.internal.GeoTokenData d = (com.pullenti.ner.geo.internal.GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, com.pullenti.ner.geo.internal.GeoTokenData.class);
            if (d == null) 
                return null;
            if (d.street != null) {
                if (d.street.cond == null) 
                    return d.street;
                if (d.street.cond.check()) 
                    return d.street;
                return null;
            }
            if (d._org != null) 
                return _new73(t, d._org.getEndToken(), StreetItemType.FIX, d._org);
            return null;
        }
        if (ad.sLevel > 3) 
            return null;
        ad.sLevel++;
        StreetItemToken res = _tryParse(t, false, prev, inSearch);
        ad.sLevel--;
        return res;
    }

    public static StreetItemToken _tryParse(com.pullenti.ner.Token t, boolean ignoreOnto, StreetItemToken prev, boolean inSearch) {
        if (t == null) 
            return null;
        if (prev != null && prev.isRoad()) {
            java.util.ArrayList<StreetItemToken> res1 = tryParseSpec(t, prev);
            if (res1 != null && res1.get(0).typ == StreetItemType.NAME) 
                return res1.get(0);
        }
        if (t.isValue("ТЕРРИТОРИЯ", null)) 
            return null;
        if ((t.isValue("А", null) || t.isValue("АД", null) || t.isValue("АВТ", null)) || t.isValue("АВТОДОР", null)) {
            com.pullenti.ner.Token tt1 = t;
            if (t.isValue("А", null)) {
                tt1 = t.getNext();
                if (tt1 != null && tt1.isCharOf("\\/")) 
                    tt1 = tt1.getNext();
                if (tt1 != null && ((tt1.isValue("Д", null) || tt1.isValue("М", null)))) {
                }
                else 
                    tt1 = null;
            }
            else if (tt1.getNext() != null && tt1.getNext().isChar('.')) 
                tt1 = tt1.getNext();
            if (tt1 != null) {
                StreetItemToken res = _new181(t, tt1, StreetItemType.NOUN, m_Road);
                if (prev != null && ((prev.isRoadName || prev.isRoad()))) 
                    return res;
                StreetItemToken _next = tryParse(tt1.getNext(), res, false, null);
                if (_next != null && _next.isRoadName) 
                    return res;
                if (t.getPrevious() != null) {
                    if (t.getPrevious().isValue("КМ", null) || t.getPrevious().isValue("КИЛОМЕТР", null)) 
                        return res;
                }
            }
        }
        com.pullenti.ner.Token tn = null;
        if (t.isValue("ИМЕНИ", null) || t.isValue("ІМЕНІ", null)) 
            tn = t;
        else if (t.isValue("ИМ", null) || t.isValue("ІМ", null)) {
            tn = t;
            if (tn.getNext() != null && tn.getNext().isChar('.')) 
                tn = tn.getNext();
        }
        if (tn != null) {
            com.pullenti.ner.geo.internal.OrgItemToken __org = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(t, null);
            if (__org != null) 
                return _new73(t, __org.getEndToken(), StreetItemType.FIX, __org);
            if (tn.getNext() == null || tn.getNewlinesAfterCount() > 1) 
                return null;
            t = tn.getNext();
        }
        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseAge(t);
        if (nt != null && nt.getIntValue() != null) 
            return _new183(nt.getBeginToken(), nt.getEndToken(), StreetItemType.AGE, nt);
        if ((((nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)))) != null) {
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt, com.pullenti.ner.NumberToken.class)).getIntValue() == null || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt, com.pullenti.ner.NumberToken.class)).getIntValue() == 0) 
                return null;
            StreetItemToken res = _new184(nt, nt, StreetItemType.NUMBER, nt, nt.getMorph());
            if ((t.getNext() != null && t.getNext().isHiphen() && t.getNext().getNext() != null) && t.getNext().getNext().isValue("Я", null)) 
                res.setEndToken(t.getNext().getNext());
            com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
            if (nex != null) {
                if (nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER) {
                    res.isNumberKm = true;
                    res.setEndToken(nex.getEndToken());
                    com.pullenti.ner.Token tt2 = res.getEndToken().getNext();
                    if (tt2 != null && tt2.isHiphen()) 
                        tt2 = tt2.getNext();
                    com.pullenti.ner.core.NumberExToken nex2 = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt2);
                    if (nex2 != null && nex2.exTyp == com.pullenti.ner.core.NumberExType.METER) 
                        res.setEndToken(nex2.getEndToken());
                }
                else 
                    return null;
            }
            AddressItemToken aaa = AddressItemToken.tryParsePureItem(t, null, null);
            if (aaa != null && aaa.getTyp() == AddressItemType.NUMBER && aaa.getEndChar() > t.getEndChar()) {
                if (prev != null && prev.typ == StreetItemType.NOUN && (((com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "ЛИНИЯ") || com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "АЛЛЕЯ")) || com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "ДОРОГА")))) {
                    if (m_Ontology.tryParse(aaa.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                    }
                    else {
                        res.setEndToken(aaa.getEndToken());
                        res.value = aaa.value;
                        res.number = null;
                    }
                }
                else 
                    return null;
            }
            if (nt.typ == com.pullenti.ner.NumberSpellingType.WORDS && nt.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(t);
                if (npt2 != null && npt2.getEndChar() > t.getEndChar() && npt2.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (t.getNext() != null && !t.getNext().chars.isAllLower()) {
                    }
                    else 
                        return null;
                }
            }
            if (!res.isNumberKm && prev != null && prev.getBeginToken().isValue("КИЛОМЕТР", null)) 
                res.isNumberKm = true;
            return res;
        }
        com.pullenti.ner.Token ntt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
        if ((ntt != null && (ntt instanceof com.pullenti.ner.NumberToken) && prev != null) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ntt, com.pullenti.ner.NumberToken.class)).getIntValue() != null) 
            return _new185(t, ntt, StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ntt, com.pullenti.ner.NumberToken.class), true);
        StreetItemToken rrr = com.pullenti.ner.geo.internal.OrgItemToken.tryParseRailway(t);
        if (rrr != null) 
            return rrr;
        if ((t instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken() && !t.chars.isAllLower()) {
            if (prev != null && prev.typ == StreetItemType.NOUN) {
                if (((short)((prev.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return _new186(t, t, StreetItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO));
            }
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        com.pullenti.ner.core.NounPhraseToken npt = null;
        if (tt != null && tt.getMorph()._getClass().isAdjective()) {
            if (tt.chars.isCapitalUpper() || ((prev != null && prev.typ == StreetItemType.NUMBER && tt.isValue("ТРАНСПОРТНЫЙ", null)))) {
                npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(tt);
                if (npt != null && (com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt.noun, com.pullenti.ner.core.GetTextAttr.NO).indexOf("-") >= 0)) 
                    npt = null;
                com.pullenti.ner.Token tte = tt.getNext();
                if (npt != null && npt.adjectives.size() == 1) 
                    tte = npt.getEndToken();
                if (tte != null) {
                    if ((((((((((tte.isValue("ВАЛ", null) || tte.isValue("ТРАКТ", null) || tte.isValue("ПОЛЕ", null)) || tte.isValue("МАГИСТРАЛЬ", null) || tte.isValue("СПУСК", null)) || tte.isValue("ВЗВОЗ", null) || tte.isValue("РЯД", null)) || tte.isValue("СЛОБОДА", null) || tte.isValue("РОЩА", null)) || tte.isValue("ПРУД", null) || tte.isValue("СЪЕЗД", null)) || tte.isValue("КОЛЬЦО", null) || tte.isValue("МАГІСТРАЛЬ", null)) || tte.isValue("УЗВІЗ", null) || tte.isValue("ЛІНІЯ", null)) || tte.isValue("УЗВІЗ", null) || tte.isValue("ГАЙ", null)) || tte.isValue("СТАВОК", null) || tte.isValue("ЗЇЗД", null)) || tte.isValue("КІЛЬЦЕ", null)) {
                        StreetItemToken sit = _new187(tt, tte, true);
                        sit.typ = StreetItemType.NAME;
                        if (npt == null || npt.adjectives.size() == 0) 
                            sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tte, com.pullenti.ner.core.GetTextAttr.NO);
                        else if (npt.getMorph().getCase().isGenitive()) {
                            sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tte, com.pullenti.ner.core.GetTextAttr.NO);
                            sit.altValue = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        }
                        else 
                            sit.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null && tok2.termin != null && tok2.getEndToken() == tte) 
                            sit.termin = tok2.termin;
                        return sit;
                    }
                }
                if (npt != null && npt.getBeginToken() != npt.getEndToken() && npt.adjectives.size() <= 1) {
                    com.pullenti.ner.core.TerminToken oo = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (oo != null && ((StreetItemType)oo.termin.tag) == StreetItemType.NOUN) 
                        npt = null;
                }
                if (npt != null && npt.getBeginToken() != npt.getEndToken() && npt.adjectives.size() <= 1) {
                    com.pullenti.ner.Token tt1 = npt.getEndToken().getNext();
                    if (tt1 != null && tt1.isComma()) 
                        tt1 = tt1.getNext();
                    boolean ok = false;
                    StreetItemToken sti1 = tryParse(tt1, null, false, null);
                    if (sti1 != null && sti1.typ == StreetItemType.NOUN) 
                        ok = true;
                    else if (tt1 != null && tt1.isHiphen() && (tt1.getNext() instanceof com.pullenti.ner.NumberToken)) 
                        ok = true;
                    else {
                        AddressItemToken ait = AddressItemToken.tryParsePureItem(tt1, null, null);
                        if (ait != null) {
                            if (ait.getTyp() == AddressItemType.HOUSE) 
                                ok = true;
                            else if (ait.getTyp() == AddressItemType.NUMBER) {
                                AddressItemToken ait2 = AddressItemToken.tryParsePureItem(npt.getEndToken(), null, null);
                                if (ait2 == null) 
                                    ok = true;
                            }
                        }
                    }
                    if (ok) {
                        sti1 = tryParse(npt.getEndToken(), null, false, null);
                        if (sti1 != null && sti1.typ == StreetItemType.NOUN) 
                            ok = sti1.nounIsDoubtCoef >= 2 && com.pullenti.unisharp.Utils.stringsNe(sti1.termin.getCanonicText(), "КВАРТАЛ");
                        else {
                            com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                            if (tok2 != null) {
                                StreetItemType _typ = (StreetItemType)tok2.termin.tag;
                                if (_typ == StreetItemType.NOUN || _typ == StreetItemType.STDPARTOFNAME) 
                                    ok = false;
                            }
                        }
                    }
                    if (ok) {
                        StreetItemToken sit = new StreetItemToken(tt, npt.getEndToken());
                        sit.typ = StreetItemType.NAME;
                        sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, npt.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        sit.altValue = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        return sit;
                    }
                }
            }
        }
        if (tt != null && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isCapitalUpper()) {
            if ((tt.isValue("ВАЛ", null) || tt.isValue("ТРАКТ", null) || tt.isValue("ПОЛЕ", null)) || tt.isValue("КОЛЬЦО", null) || tt.isValue("КІЛЬЦЕ", null)) {
                StreetItemToken sit = tryParse(tt.getNext(), null, false, null);
                if (sit != null && sit.typ == StreetItemType.NAME) {
                    if (sit.value != null) 
                        sit.value = (sit.value + " " + tt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    else 
                        sit.value = (sit.getSourceText().toUpperCase() + " " + tt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    if (sit.altValue != null) 
                        sit.altValue = (sit.altValue + " " + tt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    sit.setBeginToken(tt);
                    return sit;
                }
            }
        }
        if (((tt != null && tt.getLengthChar() == 1 && tt.chars.isAllLower()) && tt.getNext() != null && tt.getNext().isChar('.')) && tt.kit.baseLanguage.isRu()) {
            if (tt.isValue("М", null) || tt.isValue("M", null)) {
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                }
                else {
                    com.pullenti.ner.core.TerminToken tok1 = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok1 != null && com.pullenti.unisharp.Utils.stringsEq(tok1.termin.getCanonicText(), "МИКРОРАЙОН")) 
                        return _new188(tt, tok1.getEndToken(), tok1.termin, StreetItemType.NOUN);
                    return _new189(tt, tt.getNext(), m_Metro, StreetItemType.NOUN, true);
                }
            }
        }
        com.pullenti.ner.core.IntOntologyToken ot = null;
        if (t.kit.ontology != null && ot == null) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = t.kit.ontology.attachToken(com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME, t);
            if (ots != null) 
                ot = ots.get(0);
        }
        if (ot != null && ot.getBeginToken() == ot.getEndToken() && ot.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.core.TerminToken tok0 = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok0 != null) {
                if (((StreetItemType)tok0.termin.tag) == StreetItemType.STDADJECTIVE) 
                    ot = null;
            }
        }
        if (ot != null) {
            StreetItemToken res0 = _new190(ot.getBeginToken(), ot.getEndToken(), StreetItemType.NAME, (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(ot.item.referent, com.pullenti.ner.address.StreetReferent.class), ot.getMorph(), true);
            return res0;
        }
        if (prev != null && prev.typ == StreetItemType.NOUN && com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "ПРОЕЗД")) {
            if (t.isValue("ПР", null)) {
                StreetItemToken res1 = _new186(t, t, StreetItemType.NAME, "ПРОЕКТИРУЕМЫЙ");
                if (t.getNext() != null && t.getNext().isChar('.')) 
                    res1.setEndToken(t.getNext());
                return res1;
            }
        }
        com.pullenti.ner.core.TerminToken tok = (ignoreOnto ? null : m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO));
        com.pullenti.ner.core.TerminToken tokEx = (ignoreOnto ? null : m_OntologyEx.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO));
        if (tok == null) 
            tok = tokEx;
        else if (tokEx != null && tokEx.getEndChar() > tok.getEndChar()) 
            tok = tokEx;
        if (tok != null && com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "НАБЕРЕЖНАЯ") && !tok.chars.isAllLower()) {
            StreetItemToken nex = tryParse(tok.getEndToken().getNext(), null, false, null);
            if (nex != null && ((nex.typ == StreetItemType.NOUN || nex.typ == StreetItemType.STDADJECTIVE))) 
                tok = null;
            else if (nex != null && nex.typ == StreetItemType.NAME && ((nex.getBeginToken().isValue("РЕКА", null) || nex.getBeginToken().isValue("РЕЧКА", "РІЧКА")))) {
                nex.setBeginToken(t);
                nex.value = ("НАБЕРЕЖНАЯ " + nex.value);
                if (nex.altValue != null) 
                    nex.altValue = ("НАБЕРЕЖНАЯ " + nex.altValue);
                return nex;
            }
            else if (((short)((t.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value()) && t.getLengthChar() > 7) 
                tok = null;
        }
        if ((tok != null && t.getLengthChar() == 1 && t.isValue("Б", null)) && (t.getPrevious() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue(), "26")) 
            tok = null;
        if (tok != null && tok.getBeginToken() == tok.getEndToken()) {
            if ((((StreetItemType)tok.termin.tag) == StreetItemType.NAME || t.isValue("ГАРАЖНО", null) || t.getLengthChar() == 1) || t.isValue("СТ", null)) {
                com.pullenti.ner.geo.internal.OrgItemToken __org = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(t, null);
                if (__org != null) {
                    tok = null;
                    if (t.getLengthChar() < 3) 
                        return _new73(t, __org.getEndToken(), StreetItemType.FIX, __org);
                }
            }
        }
        if (tok != null && !ignoreOnto) {
            if (((StreetItemType)tok.termin.tag) == StreetItemType.NUMBER) {
                if ((tok.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tok.getEndToken().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) 
                    return _new193(t, tok.getEndToken().getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tok.getEndToken().getNext(), com.pullenti.ner.NumberToken.class), true, tok.getMorph());
                return null;
            }
            if (tt == null) 
                return null;
            boolean abr = true;
            switch (((StreetItemType)tok.termin.tag).value()) { 
            case 3: // StreetItemType.STDADJECTIVE 
                if (tt.chars.isAllLower() && prev == null && !inSearch) 
                    return null;
                if (tt.isValue(tok.termin.getCanonicText(), null)) 
                    abr = false;
                else if (tt.getLengthChar() == 1) {
                    if (!tt.isWhitespaceBefore() && !tt.getPrevious().isCharOf(":,.")) 
                        break;
                    if (!tok.getEndToken().isChar('.')) {
                        if (!tt.chars.isAllUpper() && !inSearch) 
                            break;
                        boolean oo2 = false;
                        if (tok.getEndToken().isNewlineAfter() && prev != null) 
                            oo2 = true;
                        else if (inSearch) 
                            oo2 = true;
                        else {
                            StreetItemToken _next = tryParse(tok.getEndToken().getNext(), null, false, null);
                            if (_next != null && ((_next.typ == StreetItemType.NAME || _next.typ == StreetItemType.NOUN))) 
                                oo2 = true;
                            else if (AddressItemToken.checkHouseAfter(tok.getEndToken().getNext(), false, true) && prev != null) 
                                oo2 = true;
                        }
                        if (oo2) 
                            return _new194(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDADJECTIVE, tok.termin, abr, tok.getMorph());
                        break;
                    }
                    com.pullenti.ner.Token tt2 = tok.getEndToken().getNext();
                    if (tt2 != null && tt2.isHiphen()) 
                        tt2 = tt2.getNext();
                    if (tt2 instanceof com.pullenti.ner.TextToken) {
                        if (tt2.getLengthChar() == 1 && tt2.chars.isAllUpper()) 
                            break;
                        if (tt2.chars.isCapitalUpper()) {
                            boolean isSur = false;
                            String txt = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.TextToken.class)).term;
                            if (com.pullenti.morph.LanguageHelper.endsWith(txt, "ОГО")) 
                                isSur = true;
                            else 
                                for (com.pullenti.morph.MorphBaseInfo wf : tt2.getMorph().getItems()) {
                                    if (wf._getClass().isProperSurname() && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                                        if (wf.getCase().isGenitive()) {
                                            isSur = true;
                                            break;
                                        }
                                    }
                                }
                            if (isSur) 
                                break;
                        }
                    }
                }
                return _new194(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDADJECTIVE, tok.termin, abr, tok.getMorph());
            case 0: // StreetItemType.NOUN 
                if (tt.isValue(tok.termin.getCanonicText(), null) || tok.getEndToken().isValue(tok.termin.getCanonicText(), null) || tt.isValue("УЛ", null)) 
                    abr = false;
                else if (tok.getBeginToken() != tok.getEndToken() && ((tok.getBeginToken().getNext().isHiphen() || tok.getBeginToken().getNext().isCharOf("/\\")))) {
                }
                else if (!tt.chars.isAllLower() && tt.getLengthChar() == 1) 
                    break;
                else if (tt.getLengthChar() == 1) {
                    if (!tt.isWhitespaceBefore()) {
                        if (tt.getPrevious() != null && tt.getPrevious().isCharOf(",")) {
                        }
                        else 
                            return null;
                    }
                    if (tok.getEndToken().isChar('.')) {
                    }
                    else if (tok.getBeginToken() != tok.getEndToken() && tok.getBeginToken().getNext() != null && ((tok.getBeginToken().getNext().isHiphen() || tok.getBeginToken().getNext().isCharOf("/\\")))) {
                    }
                    else if (tok.getLengthChar() > 5) {
                    }
                    else if (tok.getBeginToken() == tok.getEndToken() && tt.isValue("Ш", null) && tt.chars.isAllLower()) {
                        if (prev != null && ((prev.typ == StreetItemType.NAME || prev.typ == StreetItemType.STDNAME || prev.typ == StreetItemType.STDPARTOFNAME))) {
                        }
                        else {
                            StreetItemToken sii = tryParse(tt.getNext(), null, false, null);
                            if (sii != null && (((sii.typ == StreetItemType.NAME || sii.typ == StreetItemType.STDNAME || sii.typ == StreetItemType.STDPARTOFNAME) || sii.typ == StreetItemType.AGE))) {
                            }
                            else 
                                return null;
                        }
                    }
                    else 
                        return null;
                }
                else if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "КВ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "КВАРТ"))) && !tok.getEndToken().isValue("Л", null)) {
                }
                if ((tok.getEndToken() == tok.getBeginToken() && !t.chars.isAllLower() && t.getMorph()._getClass().isProperSurname()) && t.chars.isCyrillicLetter()) {
                    if (((short)((t.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ДОРОГОЙ")) 
                    return null;
                com.pullenti.ner.core.Termin alt = null;
                if (tok.getBeginToken().isValue("ПР", null) && ((tok.getBeginToken() == tok.getEndToken() || tok.getBeginToken().getNext().isChar('.')))) 
                    alt = m_Prospect;
                StreetItemToken res = _new196(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NOUN, tok.termin, alt, abr, tok.getMorph(), (tok.termin.tag2 instanceof Integer ? (int)tok.termin.tag2 : 0));
                if (tok.getBeginToken() == tok.getEndToken() && tok.getBeginToken().chars.isCapitalUpper() && tok.getBeginToken().getMorphClassInDictionary().isNoun()) {
                    if (tok.getMorph().getCase().isNominative() && !tok.getMorph().getCase().isGenitive()) 
                        res.nounCanBeName = true;
                }
                if (res.isRoad()) {
                    StreetItemToken _next = _tryParse(res.getEndToken().getNext(), false, null, false);
                    if (_next != null && _next.isRoad()) {
                        res.setEndToken(_next.getEndToken());
                        res.nounIsDoubtCoef = 0;
                        res.isAbridge = false;
                    }
                }
                return res;
            case 4: // StreetItemType.STDNAME 
                boolean isPostOff = com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ");
                if (tok.getBeginToken().chars.isAllLower() && !isPostOff && tok.getEndToken().chars.isAllLower()) 
                    return null;
                StreetItemToken sits = _new197(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDNAME, tok.getMorph(), tok.termin.getCanonicText());
                if (tok.getBeginToken() != tok.getEndToken() && !isPostOff) {
                    String vv = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getBeginToken(), tok.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if (com.pullenti.unisharp.Utils.stringsNe(vv, sits.value)) {
                        if (vv.length() < sits.value.length()) 
                            sits.altValue = vv;
                        else {
                            sits.altValue = sits.value;
                            sits.value = vv;
                        }
                    }
                    if (((m_StdOntMisc.tryParse(tok.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null || tok.getBeginToken().getMorphClassInDictionary().isProperName() || (tok.getBeginToken().getLengthChar() < 4))) && tok.getEndToken().getLengthChar() > 2 && ((tok.getEndToken().getMorph()._getClass().isProperSurname() || !tok.getEndToken().getMorphClassInDictionary().isProperName()))) 
                        sits.altValue2 = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getEndToken(), tok.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    else if (((tok.getEndToken().getMorphClassInDictionary().isProperName() || m_StdOntMisc.tryParse(tok.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null)) && ((tok.getBeginToken().getMorph()._getClass().isProperSurname() || !tok.getBeginToken().getMorphClassInDictionary().isProperName()))) 
                        sits.altValue2 = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getBeginToken(), tok.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO);
                }
                return sits;
            case 5: // StreetItemType.STDPARTOFNAME 
                if (prev != null && prev.typ == StreetItemType.NAME) {
                    String nam = (prev.value != null ? prev.value : com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(prev, com.pullenti.ner.core.GetTextAttr.NO));
                    if (prev.altValue == null) 
                        prev.altValue = (tok.termin.getCanonicText() + " " + nam);
                    else 
                        prev.altValue = (tok.termin.getCanonicText() + " " + prev.altValue);
                    prev.setEndToken(tok.getEndToken());
                    prev.value = nam;
                    return tryParse(tok.getEndToken().getNext(), prev, false, null);
                }
                StreetItemToken sit = tryParse(tok.getEndToken().getNext(), null, false, null);
                if (sit == null) {
                    if (tok.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        return _new197(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(tok, com.pullenti.ner.core.GetTextAttr.NO));
                    if (inSearch) 
                        return _new199(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDPARTOFNAME, tok.getMorph(), tok.termin);
                    return null;
                }
                if (sit.typ != StreetItemType.NAME && sit.typ != StreetItemType.NOUN) 
                    return null;
                if (sit.typ == StreetItemType.NOUN) {
                    if (tok.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        return _new197(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(tok, com.pullenti.ner.core.GetTextAttr.NO));
                    else 
                        return _new199(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), tok.termin);
                }
                if (sit.value != null) {
                    if (sit.altValue == null) 
                        sit.altValue = (tok.termin.getCanonicText() + " " + sit.value);
                    else 
                        sit.value = (tok.termin.getCanonicText() + " " + sit.value);
                }
                else if (sit.existStreet == null) {
                    sit.altValue = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(sit.getBeginToken(), com.pullenti.ner.TextToken.class)).term;
                    sit.value = (tok.termin.getCanonicText() + " " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(sit.getBeginToken(), com.pullenti.ner.TextToken.class)).term);
                }
                sit.setBeginToken(tok.getBeginToken());
                return sit;
            case 1: // StreetItemType.NAME 
                if (tok.getBeginToken().chars.isAllLower()) {
                    if (prev != null && prev.typ == StreetItemType.STDADJECTIVE) {
                    }
                    else if (prev != null && prev.typ == StreetItemType.NOUN && AddressItemToken.checkHouseAfter(tok.getEndToken().getNext(), true, false)) {
                    }
                    else if (t.isValue("ПРОЕКТИРУЕМЫЙ", null) || t.isValue("МИРА", null)) {
                    }
                    else {
                        StreetItemToken nex = tryParse(tok.getEndToken().getNext(), null, false, null);
                        if (nex != null && nex.typ == StreetItemType.NOUN) {
                            com.pullenti.ner.Token tt2 = nex.getEndToken().getNext();
                            while (tt2 != null && tt2.isCharOf(",.")) {
                                tt2 = tt2.getNext();
                            }
                            if (tt2 == null || tt2.getWhitespacesBeforeCount() > 1) 
                                return null;
                            if (AddressItemToken.checkHouseAfter(tt2, false, true)) {
                            }
                            else 
                                return null;
                        }
                        else 
                            return null;
                    }
                }
                StreetItemToken sit0 = tryParse(tok.getBeginToken(), prev, true, null);
                if (sit0 != null && sit0.typ == StreetItemType.NAME && sit0.getEndChar() > tok.getEndChar()) {
                    sit0.isInDictionary = true;
                    return sit0;
                }
                StreetItemToken sit1 = _new202(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), true);
                if ((!tok.isWhitespaceAfter() && tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isHiphen()) && !tok.getEndToken().getNext().isWhitespaceAfter()) {
                    StreetItemToken sit2 = tryParse(tok.getEndToken().getNext().getNext(), null, false, null);
                    if (sit2 != null && ((sit2.typ == StreetItemType.NAME || sit2.typ == StreetItemType.STDPARTOFNAME || sit2.typ == StreetItemType.STDNAME))) 
                        sit1.setEndToken(sit2.getEndToken());
                }
                if (npt != null && (sit1.getEndChar() < npt.getEndChar()) && m_Ontology.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) == null) {
                    StreetItemToken sit2 = _tryParse(t, true, prev, inSearch);
                    if (sit2 != null && sit2.getEndChar() > sit1.getEndChar()) 
                        return sit2;
                }
                return sit1;
            case 7: // StreetItemType.FIX 
                return _new203(tok.getBeginToken(), tok.getEndToken(), StreetItemType.FIX, tok.getMorph(), true, tok.termin);
            }
        }
        if (tt != null && ((tt.isValue("КИЛОМЕТР", null) || tt.isValue("КМ", null)))) {
            com.pullenti.ner.Token tt1 = tt;
            if (tt1.getNext() != null && tt1.getNext().isChar('.')) 
                tt1 = tt1.getNext();
            if ((tt1.getWhitespacesAfterCount() < 3) && (tt1.getNext() instanceof com.pullenti.ner.NumberToken)) {
                StreetItemToken sit = _new204(tt, tt1.getNext(), StreetItemType.NUMBER);
                sit.number = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1.getNext(), com.pullenti.ner.NumberToken.class);
                sit.isNumberKm = true;
                return sit;
            }
            StreetItemToken _next = tryParse(tt.getNext(), null, inSearch, null);
            if (_next != null && ((_next.isRailway || _next.isRoad()))) {
                _next.setBeginToken(tt);
                return _next;
            }
        }
        if (tt != null) {
            if (((tt.isValue("РЕКА", null) || tt.isValue("РЕЧКА", "РІЧКА"))) && tt.getNext() != null && !tt.getNext().chars.isAllLower()) {
                com.pullenti.ner.geo.internal.NameToken nam = com.pullenti.ner.geo.internal.NameToken.tryParse(tt.getNext(), com.pullenti.ner.geo.internal.NameTokenType.CITY, 0);
                if (nam != null && nam.name != null && nam.number == null) 
                    return _new205(tt, nam.getEndToken(), StreetItemType.NAME, tt.getMorph(), com.pullenti.ner.core.MiscHelper.getTextValue(tt, nam.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), nam.name);
            }
            if ((t.getPrevious() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class)).getValue(), "26")) {
                if (tt.isValue("БАКИНСКИЙ", null) || "БАКИНСК".startsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term)) {
                    com.pullenti.ner.Token tt2 = tt;
                    if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                        tt2 = tt2.getNext();
                    if (tt2.getNext() instanceof com.pullenti.ner.TextToken) {
                        tt2 = tt2.getNext();
                        if (tt2.isValue("КОМИССАР", null) || tt2.isValue("КОММИССАР", null) || "КОМИС".startsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.TextToken.class)).term)) {
                            if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                                tt2 = tt2.getNext();
                            StreetItemToken sit = _new206(tt, tt2, StreetItemType.STDNAME, true, "БАКИНСКИХ КОМИССАРОВ", tt2.getMorph());
                            return sit;
                        }
                    }
                }
            }
            if ((tt.getNext() != null && tt.getNext().isChar('.') && !tt.chars.isAllLower()) && (tt.getNext().getWhitespacesAfterCount() < 3) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.Token tt1 = tt.getNext().getNext();
                if (tt1 != null && tt1.isHiphen() && tt1.getNext() != null) 
                    tt1 = tt1.getNext();
                if (tt.getLengthChar() == 1 && tt1.getLengthChar() == 1 && (tt1.getNext() instanceof com.pullenti.ner.TextToken)) {
                    if (tt1.isAnd() && tt1.getNext().chars.isAllUpper() && tt1.getNext().getLengthChar() == 1) 
                        tt1 = tt1.getNext();
                    if ((tt1.chars.isAllUpper() && tt1.getNext().isChar('.') && (tt1.getNext().getWhitespacesAfterCount() < 3)) && (tt1.getNext().getNext() instanceof com.pullenti.ner.TextToken)) 
                        tt1 = tt1.getNext().getNext();
                }
                StreetItemToken sit = StreetItemToken.tryParse(tt1, null, false, null);
                if (sit != null && (tt1 instanceof com.pullenti.ner.TextToken)) {
                    String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term;
                    boolean ok = false;
                    com.pullenti.morph.MorphClass mc = tt1.getMorphClassInDictionary();
                    com.pullenti.morph.MorphClass cla = tt.getNext().getNext().getMorphClassInDictionary();
                    if (sit.isInDictionary) 
                        ok = true;
                    else if (sit._isSurname() || cla.isProperSurname()) 
                        ok = true;
                    else if (com.pullenti.morph.LanguageHelper.endsWith(str, "ОЙ") && ((cla.isProperSurname() || ((sit.typ == StreetItemType.NAME && sit.isInDictionary))))) 
                        ok = true;
                    else if (com.pullenti.morph.LanguageHelper.endsWithEx(str, "ГО", "ИХ", null, null)) 
                        ok = true;
                    else if ((tt1.isWhitespaceBefore() && !mc.isUndefined() && !mc.isProperSurname()) && !mc.isProperName()) {
                        if (AddressItemToken.checkHouseAfter(sit.getEndToken().getNext(), false, true)) 
                            ok = true;
                    }
                    else if (prev != null && prev.typ == StreetItemType.NOUN && ((!prev.isAbridge || prev.getLengthChar() > 2))) 
                        ok = true;
                    else if ((prev != null && prev.typ == StreetItemType.NAME && sit.typ == StreetItemType.NOUN) && AddressItemToken.checkHouseAfter(sit.getEndToken().getNext(), false, true)) 
                        ok = true;
                    else if (sit.typ == StreetItemType.NAME && AddressItemToken.checkHouseAfter(sit.getEndToken().getNext(), false, true)) {
                        if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(tt, false)) 
                            ok = true;
                        else {
                            com.pullenti.ner.geo.internal.GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
                            if (!ad.sRegime && SPEEDREGIME) {
                                ok = true;
                                sit.cond = com.pullenti.ner.geo.internal.Condition._new207(tt, true);
                            }
                        }
                    }
                    if (ok) {
                        sit.setBeginToken(tt);
                        sit.value = str;
                        sit.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(tt, sit.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        if (sit.altValue != null) 
                            sit.altValue = sit.altValue.replace("-", "");
                        return sit;
                    }
                }
            }
            if (tt.chars.isCyrillicLetter() && tt.getLengthChar() > 1 && !tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ГЕРОЙ", null) || tt.isValue("ЗАЩИТНИК", "ЗАХИСНИК")) {
                    com.pullenti.ner.Token tt2 = null;
                    if ((tt.getNext() instanceof com.pullenti.ner.ReferentToken) && (tt.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                        tt2 = tt.getNext();
                    else {
                        com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(tt.getNext());
                        if (npt2 != null && npt2.getMorph().getCase().isGenitive()) 
                            tt2 = npt2.getEndToken();
                        else {
                            com.pullenti.ner.core.IntOntologyToken tee = com.pullenti.ner.geo.internal.TerrItemToken.checkOntoItem(tt.getNext());
                            if (tee != null) 
                                tt2 = tee.getEndToken();
                            else if ((((tee = com.pullenti.ner.geo.internal.CityItemToken.checkOntoItem(tt.getNext())))) != null) 
                                tt2 = tee.getEndToken();
                        }
                    }
                    if (tt2 != null) {
                        StreetItemToken re = _new208(tt, tt2, StreetItemType.STDPARTOFNAME, com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt2, com.pullenti.ner.core.GetTextAttr.NO), true);
                        StreetItemToken sit = tryParse(tt2.getNext(), null, false, null);
                        if (sit == null || sit.typ != StreetItemType.NAME) {
                            boolean ok2 = false;
                            if (sit != null && ((sit.typ == StreetItemType.STDADJECTIVE || sit.typ == StreetItemType.NOUN))) 
                                ok2 = true;
                            else if (AddressItemToken.checkHouseAfter(tt2.getNext(), false, true)) 
                                ok2 = true;
                            else if (tt2.isNewlineAfter()) 
                                ok2 = true;
                            if (ok2) {
                                sit = _new209(tt, tt2, StreetItemType.NAME, true);
                                sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt2, com.pullenti.ner.core.GetTextAttr.NO);
                                return sit;
                            }
                            return re;
                        }
                        if (sit.value == null) 
                            sit.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(sit, com.pullenti.ner.core.GetTextAttr.NO);
                        if (sit.altValue == null) {
                            sit.altValue = sit.value;
                            sit.value = (re.value + " " + sit.value);
                        }
                        else 
                            sit.value = (re.value + " " + sit.value);
                        sit.setBeginToken(tt);
                        return sit;
                    }
                }
                com.pullenti.ner.NumberToken ani = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(t);
                if (ani != null) 
                    return _new210(t, ani.getEndToken(), StreetItemType.AGE, ani, ani.getValue().toString());
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                }
                else {
                    com.pullenti.ner.geo.internal.OrgItemToken __org = com.pullenti.ner.geo.internal.OrgItemToken.tryParse(t, null);
                    if (__org != null) {
                        if (__org.isGsk || __org.hasTerrKeyword) 
                            return _new73(t, __org.getEndToken(), StreetItemType.FIX, __org);
                    }
                }
                boolean ok1 = false;
                com.pullenti.ner.geo.internal.Condition _cond = null;
                if (!tt.chars.isAllLower()) {
                    AddressItemToken ait = AddressItemToken.tryParsePureItem(tt, null, null);
                    if (ait != null) {
                    }
                    else 
                        ok1 = true;
                }
                else if (prev != null && prev.typ == StreetItemType.NOUN) {
                    if (AddressItemToken.checkHouseAfter(tt.getNext(), false, false)) {
                        if (!AddressItemToken.checkHouseAfter(tt, false, false)) 
                            ok1 = true;
                    }
                    if (!ok1) {
                        com.pullenti.ner.Token tt1 = prev.getBeginToken().getPrevious();
                        if (tt1 != null && tt1.isComma()) 
                            tt1 = tt1.getPrevious();
                        if (tt1 != null && (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                            ok1 = true;
                        else {
                            com.pullenti.ner.geo.internal.GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
                            if (!ad.sRegime && SPEEDREGIME) {
                                ok1 = true;
                                _cond = com.pullenti.ner.geo.internal.Condition._new207(prev.getBeginToken(), true);
                            }
                        }
                    }
                }
                else if (tt.getWhitespacesAfterCount() < 2) {
                    com.pullenti.ner.core.TerminToken nex = m_Ontology.tryParse(tt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if (nex != null && nex.termin != null) {
                        if (com.pullenti.unisharp.Utils.stringsEq(nex.termin.getCanonicText(), "ПЛОЩАДЬ")) {
                            if (tt.isValue("ОБЩИЙ", null)) 
                                return null;
                        }
                        com.pullenti.ner.Token tt1 = tt.getPrevious();
                        if (tt1 != null && tt1.isComma()) 
                            tt1 = tt1.getPrevious();
                        if (tt1 != null && (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                            ok1 = true;
                        else if (AddressItemToken.checkHouseAfter(nex.getEndToken().getNext(), false, false)) 
                            ok1 = true;
                    }
                }
                if (ok1) {
                    com.pullenti.morph.MorphClass dc = tt.getMorphClassInDictionary();
                    if (dc.isAdverb()) {
                        if (!(dc.isProper())) 
                            return null;
                    }
                    StreetItemToken res = _new213(tt, tt, StreetItemType.NAME, tt.getMorph(), _cond);
                    if ((tt.getNext() != null && ((tt.getNext().isHiphen() || tt.getNext().isCharOf("\\/"))) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && !tt.isWhitespaceAfter() && !tt.getNext().isWhitespaceAfter()) {
                        boolean ok2 = AddressItemToken.checkHouseAfter(tt.getNext().getNext().getNext(), false, false) || tt.getNext().getNext().isNewlineAfter();
                        if (!ok2) {
                            StreetItemToken te2 = tryParse(tt.getNext().getNext().getNext(), null, false, null);
                            if (te2 != null && te2.typ == StreetItemType.NOUN) 
                                ok2 = true;
                        }
                        if (ok2) {
                            res.setEndToken(tt.getNext().getNext());
                            res.value = (com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO) + " " + com.pullenti.ner.core.MiscHelper.getTextValue(res.getEndToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                        }
                    }
                    else if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isLetter()) {
                        if (!AddressItemToken.checkHouseAfter(tt.getNext(), false, false) || tt.getNext().isNewlineAfter()) {
                            com.pullenti.ner.Token tt1 = tt.getNext();
                            boolean isPref = false;
                            if ((tt1 instanceof com.pullenti.ner.TextToken) && tt1.chars.isAllLower()) {
                                if (tt1.isValue("ДЕ", null) || tt1.isValue("ЛА", null)) {
                                    tt1 = tt1.getNext();
                                    isPref = true;
                                }
                            }
                            StreetItemToken nn = tryParse(tt1, null, false, null);
                            if (nn == null || nn.typ == StreetItemType.NAME) {
                                npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(tt);
                                if (npt != null) {
                                    if (npt.getBeginToken() == npt.getEndToken()) 
                                        npt = null;
                                    else if (m_Ontology.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                                        npt = null;
                                }
                                if (npt != null && ((npt.isNewlineAfter() || AddressItemToken.checkHouseAfter(npt.getEndToken().getNext(), false, false)))) {
                                    res.setEndToken(npt.getEndToken());
                                    if (npt.getMorph().getCase().isGenitive()) {
                                        res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                                        res.altValue = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                    }
                                    else {
                                        res.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                        res.altValue = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                                    }
                                }
                                else if (AddressItemToken.checkHouseAfter(tt1.getNext(), false, false) && tt1.chars.isCyrillicLetter() == tt.chars.isCyrillicLetter() && (t.getWhitespacesAfterCount() < 2)) {
                                    if (tt1.getMorph()._getClass().isVerb() && !tt1.isValue("ДАЛИ", null)) {
                                    }
                                    else if (npt == null && !tt1.chars.isAllLower() && !isPref) {
                                    }
                                    else {
                                        res.setEndToken(tt1);
                                        res.value = (com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO) + " " + com.pullenti.ner.core.MiscHelper.getTextValue(res.getEndToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                                    }
                                }
                            }
                            else if (nn.typ == StreetItemType.NOUN) {
                                npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(tt);
                                if (npt != null && npt.getEndToken() == nn.getEndToken()) {
                                    res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                                    String var = com.pullenti.morph.MorphologyService.getWordform(res.value, com.pullenti.morph.MorphBaseInfo._new214(com.pullenti.morph.MorphCase.NOMINATIVE, com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, npt.getMorph().getGender()));
                                    if (var != null && com.pullenti.unisharp.Utils.stringsNe(var, res.value)) {
                                        res.altValue = res.value;
                                        res.value = var;
                                    }
                                }
                            }
                        }
                    }
                    return res;
                }
            }
            if (tt.isValue("№", null) || tt.isValue("НОМЕР", null) || tt.isValue("НОМ", null)) {
                com.pullenti.ner.Token tt1 = tt.getNext();
                if (tt1 != null && tt1.isChar('.')) 
                    tt1 = tt1.getNext();
                if ((tt1 instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.NumberToken.class)).getIntValue() != null) 
                    return _new185(tt, tt1, StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.NumberToken.class), true);
            }
            if (tt.isHiphen() && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                    if (com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.morph.LanguageHelper.endsWith(prev.termin.getCanonicText(), "ГОРОДОК")) 
                        return _new185(tt, tt.getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class), true);
                }
            }
            if (((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && (tt.getWhitespacesBeforeCount() < 2)) && tt.chars.isLetter() && tt.chars.isAllUpper()) {
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                    if (com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(prev.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.morph.LanguageHelper.endsWith(prev.termin.getCanonicText(), "ГОРОДОК")) 
                        return _new186(tt, tt, StreetItemType.NAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                }
            }
        }
        com.pullenti.ner.Referent r = (t == null ? null : t.getReferent());
        if (r instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
            if (prev != null && prev.typ == StreetItemType.NOUN) {
                if (AddressItemToken.checkHouseAfter(t.getNext(), false, false)) 
                    return _new186(t, t, StreetItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, t, com.pullenti.ner.core.GetTextAttr.NO));
            }
        }
        if (((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isCapitalUpper() && tt.chars.isLatinLetter()) && (tt.getWhitespacesAfterCount() < 2)) {
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
                return null;
            com.pullenti.ner.Token tt2 = tt.getNext();
            if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(tt2)) 
                tt2 = tt2.getNext().getNext();
            com.pullenti.ner.core.TerminToken tok1 = m_Ontology.tryParse(tt2, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null) 
                return _new197(tt, tt2.getPrevious(), StreetItemType.NAME, tt.getMorph(), ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
        }
        if (((tt != null && tt.isValue("ПОДЪЕЗД", null) && prev != null) && prev.isRoad() && tt.getNext() != null) && tt.getNext().isValue("К", null) && tt.getNext().getNext() != null) {
            StreetItemToken sit = _new204(tt, tt.getNext(), StreetItemType.NAME);
            sit.isRoadName = true;
            com.pullenti.ner.Token t1 = tt.getNext().getNext();
            com.pullenti.ner.geo.GeoReferent g1 = null;
            for (; t1 != null; t1 = t1.getNext()) {
                if (t1.getWhitespacesBeforeCount() > 3) 
                    break;
                if ((((g1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.geo.GeoReferent.class)))) != null) 
                    break;
                if (t1.isChar('.') || (t1.getLengthChar() < 3)) 
                    continue;
                if ((t1.getLengthChar() < 4) && t1.chars.isAllLower()) 
                    continue;
                break;
            }
            if (g1 != null) {
                sit.setEndToken(t1);
                java.util.ArrayList<String> nams = g1.getStringValues(com.pullenti.ner.geo.GeoReferent.ATTR_NAME);
                if (nams == null || nams.size() == 0) 
                    return null;
                sit.value = "ПОДЪЕЗД - " + nams.get(0);
                if (nams.size() > 1) 
                    sit.altValue = "ПОДЪЕЗД - " + nams.get(1);
                return sit;
            }
            if ((t1 instanceof com.pullenti.ner.TextToken) && (t1.getWhitespacesBeforeCount() < 2) && t1.chars.isCapitalUpper()) {
                com.pullenti.ner.geo.internal.CityItemToken cit = com.pullenti.ner.geo.internal.CityItemToken.tryParse(t1, null, true, null);
                if (cit != null && ((cit.typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.PROPERNAME || cit.typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.CITY))) {
                    sit.setEndToken(cit.getEndToken());
                    sit.value = "ПОДЪЕЗД - " + cit.value;
                    return sit;
                }
            }
        }
        return null;
    }

    public static java.util.ArrayList<StreetItemToken> tryParseSpec(com.pullenti.ner.Token t, StreetItemToken prev) {
        if (t == null) 
            return null;
        java.util.ArrayList<StreetItemToken> res = null;
        StreetItemToken sit;
        if (t.getReferent() instanceof com.pullenti.ner.date.DateReferent) {
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.date.DateReferent.class);
            if (!(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken() instanceof com.pullenti.ner.NumberToken)) 
                return null;
            if (dr.getYear() == 0 && dr.getDay() > 0 && dr.getMonth() > 0) {
                res = new java.util.ArrayList<StreetItemToken>();
                res.add(_new183(t, t, StreetItemType.NUMBER, new com.pullenti.ner.NumberToken(t, t, ((Integer)dr.getDay()).toString(), com.pullenti.ner.NumberSpellingType.DIGIT, null)));
                String tmp = dr.toStringEx(false, t.getMorph().getLanguage(), 0);
                int i = tmp.indexOf(' ');
                res.add((sit = _new186(t, t, StreetItemType.STDNAME, tmp.substring(i + 1).toUpperCase())));
                sit.chars.setCapitalUpper(true);
                return res;
            }
            if (dr.getYear() > 0 && dr.getMonth() == 0) {
                res = new java.util.ArrayList<StreetItemToken>();
                res.add(_new183(t, t, StreetItemType.NUMBER, new com.pullenti.ner.NumberToken(t, t, ((Integer)dr.getYear()).toString(), com.pullenti.ner.NumberSpellingType.DIGIT, null)));
                res.add((sit = _new186(t, t, StreetItemType.STDNAME, (t.getMorph().getLanguage().isUa() ? "РОКУ" : "ГОДА"))));
                sit.chars.setCapitalUpper(true);
                return res;
            }
            return null;
        }
        if (prev != null && prev.typ == StreetItemType.AGE) {
            res = new java.util.ArrayList<StreetItemToken>();
            if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                res.add((sit = _new225(t, t, StreetItemType.NAME, t.getSourceText().toUpperCase(), t.getReferent().toStringEx(true, t.kit.baseLanguage, 0).toUpperCase())));
            else if (t.isValue("ГОРОД", null) || t.isValue("МІСТО", null)) 
                res.add((sit = _new186(t, t, StreetItemType.NAME, "ГОРОДА")));
            else 
                return null;
            return res;
        }
        if (prev != null && prev.typ == StreetItemType.NOUN) {
            com.pullenti.ner.NumberToken num = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if (num != null && num.getIntValue() != null) {
                res = new java.util.ArrayList<StreetItemToken>();
                res.add((sit = _new183(num.getBeginToken(), num.getEndToken(), StreetItemType.NUMBER, num)));
                t = num.getEndToken().getNext();
                if ((num.typ == com.pullenti.ner.NumberSpellingType.DIGIT && (t instanceof com.pullenti.ner.TextToken) && !t.isWhitespaceBefore()) && t.getLengthChar() == 1) {
                    sit.setEndToken(t);
                    sit.value = (num.getValue() + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    sit.number = null;
                }
                return res;
            }
        }
        if (prev != null && prev.isRoad() && (t.getWhitespacesBeforeCount() < 3)) {
            java.util.ArrayList<String> vals = null;
            com.pullenti.ner.Token t1 = null;
            boolean br = false;
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt.getWhitespacesBeforeCount() > 3) 
                    break;
                if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) {
                    if (tt == t) {
                        br = true;
                        continue;
                    }
                    break;
                }
                String val = null;
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
                    if (rt.getBeginToken() == rt.getEndToken() && (rt.getEndToken() instanceof com.pullenti.ner.TextToken)) 
                        val = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(rt.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                    else 
                        val = tt.getReferent().toStringEx(true, tt.kit.baseLanguage, 0).toUpperCase();
                    t1 = tt;
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isCapitalUpper()) {
                    com.pullenti.ner.geo.internal.CityItemToken cit = com.pullenti.ner.geo.internal.CityItemToken.tryParse(tt, null, true, null);
                    if (cit != null && cit.typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.PROPERNAME) {
                        val = cit.value;
                        t1 = (tt = cit.getEndToken());
                    }
                    else 
                        break;
                }
                else 
                    break;
                if (vals == null) 
                    vals = new java.util.ArrayList<String>();
                if (val.indexOf('-') > 0 && (tt instanceof com.pullenti.ner.TextToken)) 
                    com.pullenti.unisharp.Utils.addToArrayList(vals, java.util.Arrays.asList(com.pullenti.unisharp.Utils.split(val, String.valueOf('-'), false)));
                else 
                    vals.add(val);
                if (tt.getNext() != null && tt.getNext().isHiphen()) 
                    tt = tt.getNext();
                else 
                    break;
            }
            if (vals != null) {
                boolean ok = false;
                if (vals.size() > 1) 
                    ok = true;
                else if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(t, false)) 
                    ok = true;
                else {
                    StreetItemToken sit1 = tryParse(t1.getNext(), null, false, null);
                    if (sit1 != null && sit1.typ == StreetItemType.NUMBER && sit1.isNumberKm) 
                        ok = true;
                }
                if (ok) {
                    if (br) {
                        if (com.pullenti.ner.core.BracketHelper.isBracket(t1.getNext(), false)) 
                            t1 = t1.getNext();
                    }
                    res = new java.util.ArrayList<StreetItemToken>();
                    prev.nounIsDoubtCoef = 0;
                    prev.isAbridge = false;
                    res.add((sit = _new204(t, t1, StreetItemType.NAME)));
                    if (vals.size() == 1) 
                        sit.value = vals.get(0);
                    else if (vals.size() == 2) {
                        sit.value = (vals.get(0) + " - " + vals.get(1));
                        sit.altValue = (vals.get(1) + " - " + vals.get(0));
                    }
                    else if (vals.size() == 3) {
                        sit.value = (vals.get(0) + " - " + vals.get(1) + " - " + vals.get(2));
                        sit.altValue = (vals.get(2) + " - " + vals.get(1) + " - " + vals.get(0));
                    }
                    else if (vals.size() == 4) {
                        sit.value = (vals.get(0) + " - " + vals.get(1) + " - " + vals.get(2) + " - " + vals.get(3));
                        sit.altValue = (vals.get(3) + " - " + vals.get(2) + " - " + vals.get(1) + " - " + vals.get(0));
                    }
                    else 
                        return null;
                    return res;
                }
            }
            if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.chars.isLetter()) && t.getNext() != null) {
                com.pullenti.ner.Token tt = t.getNext();
                if (tt.isHiphen() && tt.getNext() != null) 
                    tt = tt.getNext();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    res = new java.util.ArrayList<StreetItemToken>();
                    prev.nounIsDoubtCoef = 0;
                    res.add((sit = _new204(t, tt, StreetItemType.NAME)));
                    char ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.charAt(0);
                    char ch0 = com.pullenti.morph.LanguageHelper.getCyrForLat(ch);
                    if (((int)ch0) != 0) 
                        ch = ch0;
                    sit.value = (String.valueOf(ch) + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
                    sit.isRoadName = true;
                    tt = tt.getNext();
                    com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br1 != null && (br1.getLengthChar() < 15)) 
                        sit.setEndToken(br1.getEndToken());
                    else if (tt != null && tt.getLengthChar() > 2 && !tt.chars.isAllLower()) {
                        if ((((((tt.isValue("ДОН", null) || tt.isValue("КАВКАЗ", null) || tt.isValue("УРАЛ", null)) || tt.isValue("БЕЛАРУСЬ", null) || tt.isValue("УКРАИНА", null)) || tt.isValue("КРЫМ", null) || tt.isValue("ВОЛГА", null)) || tt.isValue("ХОЛМОГОРЫ", null) || tt.isValue("БАЛТИЯ", null)) || tt.isValue("РОССИЯ", null) || tt.isValue("НЕВА", null)) || tt.isValue("КОЛА", null) || tt.isValue("КАСПИЙ", null)) 
                            sit.setEndToken(tt);
                    }
                    return res;
                }
            }
        }
        return null;
    }

    private static StreetItemToken _tryAttachRoadNum(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (!t.chars.isLetter() || t.getLengthChar() != 1) 
            return null;
        com.pullenti.ner.Token tt = t.getNext();
        if (tt != null && tt.isHiphen()) 
            tt = tt.getNext();
        if (!(tt instanceof com.pullenti.ner.NumberToken)) 
            return null;
        StreetItemToken res = _new204(t, tt, StreetItemType.NAME);
        res.value = (t.getSourceText().toUpperCase() + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue());
        return res;
    }

    public static java.util.ArrayList<StreetItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad.sLevel > 1) 
            return null;
        ad.sLevel++;
        java.util.ArrayList<StreetItemToken> res = _tryParseList(t, maxCount, ad);
        ad.sLevel--;
        return res;
    }

    private static java.util.ArrayList<StreetItemToken> _tryParseList(com.pullenti.ner.Token t, int maxCount, com.pullenti.ner.geo.internal.GeoAnalyzerData ad) {
        java.util.ArrayList<StreetItemToken> res = null;
        StreetItemToken sit = tryParse(t, null, false, ad);
        if (sit != null) {
            res = new java.util.ArrayList<StreetItemToken>();
            res.add(sit);
            t = sit.getEndToken().getNext();
        }
        else {
            res = tryParseSpec(t, null);
            if (res == null) 
                return null;
            sit = res.get(res.size() - 1);
            t = sit.getEndToken().getNext();
            StreetItemToken sit2 = tryParse(t, null, false, null);
            if (sit2 != null && sit2.typ == StreetItemType.NOUN) {
            }
            else if (AddressItemToken.checkHouseAfter(t, false, true)) {
            }
            else 
                return null;
        }
        for (; t != null; t = (t == null ? null : t.getNext())) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            if (t.isNewlineBefore()) {
                if (t.getNewlinesBeforeCount() > 1) 
                    break;
                if (((t.getWhitespacesAfterCount() < 15) && sit != null && sit.typ == StreetItemType.NOUN) && t.chars.isCapitalUpper()) {
                }
                else 
                    break;
            }
            if (t.isHiphen() && sit != null && ((sit.typ == StreetItemType.NAME || ((sit.typ == StreetItemType.STDADJECTIVE && !sit.isAbridge))))) {
                StreetItemToken sit1 = tryParse(t.getNext(), sit, false, ad);
                if (sit1 == null) 
                    break;
                if (sit1.typ == StreetItemType.NUMBER) {
                    com.pullenti.ner.Token tt = sit1.getEndToken().getNext();
                    if (tt != null && tt.isComma()) 
                        tt = tt.getNext();
                    boolean ok = false;
                    AddressItemToken ait = AddressItemToken.tryParsePureItem(tt, null, null);
                    if (ait != null) {
                        if (ait.getTyp() == AddressItemType.HOUSE) 
                            ok = true;
                    }
                    if (!ok) {
                        if (res.size() == 2 && res.get(0).typ == StreetItemType.NOUN) {
                            if (com.pullenti.unisharp.Utils.stringsEq(res.get(0).termin.getCanonicText(), "МИКРОРАЙОН")) 
                                ok = true;
                        }
                    }
                    if (!ok && t.isHiphen()) 
                        ok = true;
                    if (ok) {
                        sit = sit1;
                        res.add(sit);
                        t = sit.getEndToken();
                        sit.numberHasPrefix = true;
                        continue;
                    }
                }
                if (sit1.typ != StreetItemType.NAME && sit1.typ != StreetItemType.NAME) 
                    break;
                if (t.isWhitespaceBefore() && t.isWhitespaceAfter()) 
                    break;
                if (res.get(0).getBeginToken().getPrevious() != null) {
                    AddressItemToken aaa = AddressItemToken.tryParsePureItem(res.get(0).getBeginToken().getPrevious(), null, null);
                    if (aaa != null && aaa.getTyp() == AddressItemType.DETAIL && aaa.detailType == com.pullenti.ner.address.AddressDetailType.CROSS) 
                        break;
                }
                sit = sit1;
                res.add(sit);
                t = sit.getEndToken();
                continue;
            }
            else if (t.isHiphen() && sit != null && sit.typ == StreetItemType.NUMBER) {
                StreetItemToken sit1 = tryParse(t.getNext(), null, false, ad);
                if (sit1 != null && ((sit1.typ == StreetItemType.STDADJECTIVE || sit1.typ == StreetItemType.STDNAME || sit1.typ == StreetItemType.NAME))) {
                    sit.numberHasPrefix = true;
                    sit = sit1;
                    res.add(sit);
                    t = sit.getEndToken();
                    continue;
                }
            }
            if (t.isChar('.') && sit != null && sit.typ == StreetItemType.NOUN) {
                if (t.getWhitespacesAfterCount() > 1) 
                    break;
                sit = tryParse(t.getNext(), null, false, ad);
                if (sit == null) 
                    break;
                if (sit.typ == StreetItemType.NUMBER || sit.typ == StreetItemType.STDADJECTIVE) {
                    StreetItemToken sit1 = tryParse(sit.getEndToken().getNext(), null, false, ad);
                    if (sit1 != null && ((sit1.typ == StreetItemType.STDADJECTIVE || sit1.typ == StreetItemType.STDNAME || sit1.typ == StreetItemType.NAME))) {
                    }
                    else 
                        break;
                }
                else if (sit.typ != StreetItemType.NAME && sit.typ != StreetItemType.STDNAME && sit.typ != StreetItemType.AGE) 
                    break;
                if (t.getPrevious().getMorphClassInDictionary().isNoun()) {
                    if (!sit.isInDictionary) {
                        com.pullenti.ner.Token tt = sit.getEndToken().getNext();
                        boolean hasHouse = false;
                        for (; tt != null; tt = tt.getNext()) {
                            if (tt.isNewlineBefore()) 
                                break;
                            if (tt.isComma()) 
                                continue;
                            AddressItemToken ai = AddressItemToken.tryParsePureItem(tt, null, null);
                            if (ai != null && ((ai.getTyp() == AddressItemType.HOUSE || ai.getTyp() == AddressItemType.BUILDING || ai.getTyp() == AddressItemType.CORPUS))) {
                                hasHouse = true;
                                break;
                            }
                            StreetItemToken vv = tryParse(tt, null, false, ad);
                            if (vv == null || vv.typ == StreetItemType.NOUN) 
                                break;
                            tt = vv.getEndToken();
                        }
                        if (!hasHouse) 
                            break;
                    }
                    if (t.getPrevious().getPrevious() != null) {
                        com.pullenti.ner.core.NounPhraseToken npt11 = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(t.getPrevious().getPrevious());
                        if (npt11 != null && npt11.getEndToken() == t.getPrevious()) 
                            break;
                    }
                }
                res.add(sit);
            }
            else {
                sit = tryParse(t, res.get(res.size() - 1), false, ad);
                if (sit == null) {
                    java.util.ArrayList<StreetItemToken> spli = tryParseSpec(t, res.get(res.size() - 1));
                    if (spli != null && spli.size() > 0) {
                        com.pullenti.unisharp.Utils.addToArrayList(res, spli);
                        t = spli.get(spli.size() - 1).getEndToken();
                        continue;
                    }
                    if (((t instanceof com.pullenti.ner.TextToken) && ((res.size() == 2 || res.size() == 3)) && res.get(0).typ == StreetItemType.NOUN) && res.get(1).typ == StreetItemType.NUMBER && (((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ГОДА") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "МАЯ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "МАРТА")) || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "СЪЕЗДА")))) {
                        res.add((sit = _new186(t, t, StreetItemType.STDNAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term)));
                        continue;
                    }
                    sit = res.get(res.size() - 1);
                    if (t == null) 
                        break;
                    if (sit.typ == StreetItemType.NOUN && ((com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "МІКРОРАЙОН"))) && (t.getWhitespacesBeforeCount() < 2)) {
                        com.pullenti.ner.Token tt1 = t;
                        if (tt1.isHiphen() && tt1.getNext() != null) 
                            tt1 = tt1.getNext();
                        if (com.pullenti.ner.core.BracketHelper.isBracket(tt1, true) && tt1.getNext() != null) 
                            tt1 = tt1.getNext();
                        com.pullenti.ner.Token tt2 = tt1.getNext();
                        boolean br = false;
                        if (com.pullenti.ner.core.BracketHelper.isBracket(tt2, true)) {
                            tt2 = tt2.getNext();
                            br = true;
                        }
                        if (((tt1 instanceof com.pullenti.ner.TextToken) && tt1.getLengthChar() == 1 && tt1.chars.isLetter()) && ((AddressItemToken.checkHouseAfter(tt2, false, true) || tt2 == null))) {
                            sit = _new186(t, (br ? tt1.getNext() : tt1), StreetItemType.NAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term);
                            char ch1 = AddressItemToken.correctChar(sit.value.charAt(0));
                            if (((int)ch1) != 0 && ch1 != sit.value.charAt(0)) 
                                sit.altValue = (String.valueOf(ch1));
                            res.add(sit);
                            break;
                        }
                    }
                    if (t.isComma() && (((sit.typ == StreetItemType.NAME || sit.typ == StreetItemType.STDNAME || sit.typ == StreetItemType.STDPARTOFNAME) || sit.typ == StreetItemType.STDADJECTIVE || ((sit.typ == StreetItemType.NUMBER && res.size() > 1 && (((res.get(res.size() - 2).typ == StreetItemType.NAME || res.get(res.size() - 2).typ == StreetItemType.STDNAME || res.get(res.size() - 2).typ == StreetItemType.STDADJECTIVE) || res.get(res.size() - 2).typ == StreetItemType.STDPARTOFNAME))))))) {
                        sit = tryParse(t.getNext(), null, false, ad);
                        if (sit != null && sit.typ == StreetItemType.NOUN) {
                            com.pullenti.ner.Token ttt = sit.getEndToken().getNext();
                            if (ttt != null && ttt.isComma()) 
                                ttt = ttt.getNext();
                            AddressItemToken add = AddressItemToken.tryParsePureItem(ttt, null, null);
                            if (add != null && ((add.getTyp() == AddressItemType.HOUSE || add.getTyp() == AddressItemType.CORPUS || add.getTyp() == AddressItemType.BUILDING))) {
                                res.add(sit);
                                t = sit.getEndToken();
                                continue;
                            }
                        }
                    }
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                        StreetItemToken sit1 = res.get(res.size() - 1);
                        if (sit1.typ == StreetItemType.NOUN && ((sit1.nounIsDoubtCoef == 0 || (((t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()))))) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null && (br.getLengthChar() < 50)) {
                                StreetItemToken sit2 = tryParse(t.getNext(), null, false, ad);
                                if (sit2 != null && sit2.getEndToken().getNext() == br.getEndToken()) {
                                    if (sit2.value == null && sit2.typ == StreetItemType.NAME) 
                                        sit2.value = com.pullenti.ner.core.MiscHelper.getTextValue(sit2.getBeginToken(), sit2.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                                    sit2.setBeginToken(t);
                                    sit2.isInBrackets = true;
                                    t = sit2.setEndToken(br.getEndToken());
                                    res.add(sit2);
                                    continue;
                                }
                                res.add(_new233(t, br.getEndToken(), StreetItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), true));
                                t = br.getEndToken();
                                continue;
                            }
                        }
                    }
                    if (t.isHiphen() && (t.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        sit = res.get(res.size() - 1);
                        if (sit.typ == StreetItemType.NOUN && (((com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "ГОРОДОК")) || com.pullenti.unisharp.Utils.stringsEq(sit.termin.getCanonicText(), "МІКРОРАЙОН")))) {
                            sit = _new185(t, t.getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class), true);
                            res.add(sit);
                            t = t.getNext();
                            continue;
                        }
                    }
                    break;
                }
                res.add(sit);
                if (sit.typ == StreetItemType.NAME) {
                    int cou = 0;
                    int jj;
                    for (jj = res.size() - 1; jj >= 0; jj--) {
                        if (res.get(jj).typ == StreetItemType.NAME) 
                            cou++;
                        else 
                            break;
                    }
                    if (cou > 4) {
                        if (jj < 0) 
                            return null;
                        for(int jjj = jj + res.size() - jj - 1, mmm = jj; jjj >= mmm; jjj--) res.remove(jjj);
                        break;
                    }
                    if (res.size() > 1 && res.get(0).typ == StreetItemType.NOUN && res.get(0).isRoad()) {
                        com.pullenti.ner.Token tt = sit.getEndToken().getNext();
                        if (tt != null) {
                            if (tt.isValue("Ш", null) || tt.isValue("ШОССЕ", null) || tt.isValue("ШОС", null)) {
                                sit = sit.clone();
                                com.pullenti.unisharp.Utils.putArrayValue(res, res.size() - 1, sit);
                                sit.setEndToken(tt);
                                if (tt.getNext() != null && tt.getNext().isChar('.') && tt.getLengthChar() <= 3) 
                                    sit.setEndToken(sit.getEndToken().getNext());
                            }
                        }
                    }
                }
            }
            t = sit.getEndToken();
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.NAME && res.get(i + 1).typ == StreetItemType.NAME && (res.get(i).getWhitespacesAfterCount() < 3)) {
                boolean isProp = false;
                boolean isPers = false;
                if (res.get(i).getBeginToken().getMorph()._getClass().isNoun()) {
                    com.pullenti.ner.ReferentToken rt = res.get(i).kit.processReferent("PERSON", res.get(i).getBeginToken(), null);
                    if (rt != null) {
                        if (com.pullenti.unisharp.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY")) 
                            isProp = true;
                        else if (rt.getEndToken() == res.get(i + 1).getEndToken()) 
                            isPers = true;
                    }
                }
                if ((i == 0 && ((!isProp && !isPers)) && ((i + 2) < res.size())) && res.get(i + 2).typ == StreetItemType.NOUN && !res.get(i).getBeginToken().getMorph()._getClass().isAdjective()) {
                    if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(res.get(0).getBeginToken(), false) && res.get(0).getEndToken().getNext() == res.get(1).getBeginToken() && (res.get(0).getWhitespacesAfterCount() < 2)) {
                    }
                    else {
                        res.remove(i);
                        i--;
                        continue;
                    }
                }
                if (res.get(i).getMorph()._getClass().isAdjective() && res.get(i + 1).getMorph()._getClass().isAdjective()) {
                    if (res.get(i).getEndToken().getNext().isHiphen()) {
                    }
                    else if (i == 1 && res.get(0).typ == StreetItemType.NOUN && res.size() == 3) {
                    }
                    else if (i == 0 && res.size() == 3 && res.get(2).typ == StreetItemType.NOUN) {
                    }
                    else 
                        continue;
                }
                StreetItemToken r = res.get(i).clone();
                r.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if ((r.value.indexOf("-") >= 0)) 
                    r.value = r.value.replace('-', ' ');
                if (!res.get(i + 1).getBeginToken().getPrevious().isHiphen() && ((!res.get(i).getBeginToken().getMorph()._getClass().isAdjective() || isProp || isPers))) {
                    if (isPers && res.get(i + 1).getEndToken().getMorphClassInDictionary().isProperName()) 
                        r.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    else 
                        r.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i + 1).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if ((r.altValue.indexOf("-") >= 0)) 
                        r.altValue = r.altValue.replace('-', ' ');
                }
                r.setEndToken(res.get(i + 1).getEndToken());
                r.existStreet = null;
                r.isInDictionary = res.get(i + 1).isInDictionary || res.get(i).isInDictionary;
                com.pullenti.unisharp.Utils.putArrayValue(res, i, r);
                res.remove(i + 1);
                i--;
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.STDADJECTIVE && res.get(i).getEndToken().isChar('.') && res.get(i + 1)._isSurname()) {
                StreetItemToken r = res.get(i + 1).clone();
                r.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.get(i + 1).getBeginToken(), com.pullenti.ner.TextToken.class)).term;
                r.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                r.setBeginToken(res.get(i).getBeginToken());
                com.pullenti.unisharp.Utils.putArrayValue(res, i + 1, r);
                res.remove(i);
                break;
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i + 1).typ == StreetItemType.STDADJECTIVE && res.get(i + 1).getEndToken().isChar('.') && res.get(i + 1).getBeginToken().getLengthChar() == 1) && !res.get(i).getBeginToken().chars.isAllLower()) {
                if (res.get(i)._isSurname()) {
                    if (i == (res.size() - 2) || res.get(i + 2).typ != StreetItemType.NOUN) {
                        StreetItemToken r = res.get(i).clone();
                        r.setEndToken(res.get(i + 1).getEndToken());
                        com.pullenti.unisharp.Utils.putArrayValue(res, i, r);
                        res.remove(i + 1);
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.NAME || res.get(i).typ == StreetItemType.STDNAME || res.get(i).typ == StreetItemType.STDADJECTIVE) {
                if (res.get(i + 1).typ == StreetItemType.NOUN && !res.get(i + 1).isAbridge) {
                    java.util.ArrayList<StreetItemToken> res0 = new java.util.ArrayList<StreetItemToken>(res);
                    for(int jjj = 0 + i + 1 - 1, mmm = 0; jjj >= mmm; jjj--) res0.remove(jjj);
                    AddressItemToken rtt = StreetDefineHelper.tryParseStreet(res0, false, false, false);
                    if (rtt != null) 
                        continue;
                    int i0 = -1;
                    if (i == 1 && res.get(0).typ == StreetItemType.NOUN && res.size() == 3) 
                        i0 = 0;
                    else if (i == 0 && res.size() == 3 && res.get(2).typ == StreetItemType.NOUN) 
                        i0 = 2;
                    if (i0 < 0) 
                        continue;
                    if (res.get(i0).termin == res.get(i + 1).termin) 
                        continue;
                    StreetItemToken r = res.get(i).clone();
                    r.altValue = (res.get(i).value != null ? res.get(i).value : com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                    if (res.get(i).typ == StreetItemType.STDADJECTIVE) {
                        java.util.ArrayList<String> adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(res.get(i).getBeginToken(), res.get(i + 1).getMorph().getGender(), res.get(i + 1).getMorph().getNumber(), true);
                        if (adjs != null && adjs.size() > 0) 
                            r.altValue = adjs.get(0);
                    }
                    r.value = (r.altValue + " " + res.get(i + 1).termin.getCanonicText());
                    r.typ = StreetItemType.STDNAME;
                    r.setEndToken(res.get(i + 1).getEndToken());
                    com.pullenti.unisharp.Utils.putArrayValue(res, i, r);
                    StreetItemToken rr = res.get(i0).clone();
                    rr.altTermin = res.get(i + 1).termin;
                    com.pullenti.unisharp.Utils.putArrayValue(res, i0, rr);
                    res.remove(i + 1);
                    i--;
                }
            }
        }
        if ((res.size() >= 3 && res.get(0).typ == StreetItemType.NOUN && com.pullenti.unisharp.Utils.stringsEq(res.get(0).termin.getCanonicText(), "КВАРТАЛ")) && ((res.get(1).typ == StreetItemType.NAME || res.get(1).typ == StreetItemType.STDNAME)) && res.get(2).typ == StreetItemType.NOUN) {
            if (res.size() == 3 || res.get(3).typ == StreetItemType.NUMBER) {
                java.util.ArrayList<StreetItemToken> res0 = new java.util.ArrayList<StreetItemToken>(res);
                for(int jjj = 0 + 2 - 1, mmm = 0; jjj >= mmm; jjj--) res0.remove(jjj);
                AddressItemToken rtt = StreetDefineHelper.tryParseStreet(res0, false, false, false);
                if (rtt == null || res0.get(0).chars.isCapitalUpper()) {
                    StreetItemToken r = res.get(1).clone();
                    r.value = (com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res.get(1), com.pullenti.ner.core.GetTextAttr.NO) + " " + res.get(2).termin.getCanonicText());
                    r.setEndToken(res.get(2).getEndToken());
                    com.pullenti.unisharp.Utils.putArrayValue(res, 1, r);
                    res.remove(2);
                }
            }
        }
        if ((res.size() >= 3 && res.get(0).typ == StreetItemType.NOUN && com.pullenti.unisharp.Utils.stringsEq(res.get(0).termin.getCanonicText(), "КВАРТАЛ")) && ((res.get(2).typ == StreetItemType.NAME || res.get(2).typ == StreetItemType.STDNAME)) && res.get(1).typ == StreetItemType.NOUN) {
            if (res.size() == 3 || res.get(3).typ == StreetItemType.NUMBER) {
                StreetItemToken r = res.get(1).clone();
                r.value = (com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res.get(2), com.pullenti.ner.core.GetTextAttr.NO) + " " + res.get(1).termin.getCanonicText());
                r.setEndToken(res.get(2).getEndToken());
                r.typ = StreetItemType.NAME;
                com.pullenti.unisharp.Utils.putArrayValue(res, 1, r);
                res.remove(2);
            }
        }
        if ((res.size() >= 3 && res.get(0).typ == StreetItemType.NUMBER && !res.get(0).isNumberKm) && res.get(1).typ == StreetItemType.NOUN) {
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.get(0).getBeginToken(), com.pullenti.ner.NumberToken.class);
            if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT && nt.getMorph()._getClass().isUndefined()) 
                return null;
        }
        if (res.size() > 1 && res.get(0).typ == StreetItemType.NOUN) {
            if (res.get(1).typ == StreetItemType.NOUN && res.get(1).nounCanBeName) {
                StreetItemToken r = res.get(1).clone();
                r.typ = StreetItemType.NAME;
                r.value = res.get(1).termin.getCanonicText();
                com.pullenti.unisharp.Utils.putArrayValue(res, 1, r);
            }
            else if ((res.size() > 2 && res.get(1).typ == StreetItemType.NUMBER && res.get(2).typ == StreetItemType.NOUN) && res.get(2).nounCanBeName) {
                StreetItemToken r = res.get(2).clone();
                r.typ = StreetItemType.NAME;
                r.value = res.get(2).termin.getCanonicText();
                com.pullenti.unisharp.Utils.putArrayValue(res, 2, r);
            }
        }
        int ii0 = -1;
        int ii1 = -1;
        if (res.size() > 0 && res.get(0).typ == StreetItemType.NOUN && res.get(0).isRoad()) {
            ii0 = (ii1 = 0);
            if (((ii0 + 1) < res.size()) && res.get(ii0 + 1).typ == StreetItemType.NUMBER && res.get(ii0 + 1).isNumberKm) 
                ii0++;
        }
        else if ((res.size() > 1 && res.get(0).typ == StreetItemType.NUMBER && res.get(0).isNumberKm) && res.get(1).typ == StreetItemType.NOUN && res.get(1).isRoad()) 
            ii0 = (ii1 = 1);
        if (ii0 >= 0) {
            if (res.size() == (ii0 + 1)) {
                com.pullenti.ner.Token tt = res.get(ii0).getEndToken().getNext();
                StreetItemToken num = _tryAttachRoadNum(tt);
                if (num != null) {
                    res.add(num);
                    tt = num.getEndToken().getNext();
                    res.get(0).isAbridge = false;
                }
                if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent g1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    tt = tt.getNext();
                    if (tt != null && tt.isHiphen()) 
                        tt = tt.getNext();
                    com.pullenti.ner.geo.GeoReferent g2 = (tt == null ? null : (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class));
                    if (g2 != null) {
                        if (g1.isCity() && g2.isCity()) {
                            StreetItemToken nam = _new204(res.get(0).getEndToken().getNext(), tt, StreetItemType.NAME);
                            nam.value = (g1.toStringEx(true, tt.kit.baseLanguage, 0) + " - " + g2.toStringEx(true, tt.kit.baseLanguage, 0)).toUpperCase();
                            nam.altValue = (g2.toStringEx(true, tt.kit.baseLanguage, 0) + " - " + g1.toStringEx(true, tt.kit.baseLanguage, 0)).toUpperCase();
                            res.add(nam);
                        }
                    }
                }
                else if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        StreetItemToken nam = _new236(tt, br.getEndToken(), StreetItemType.NAME, true);
                        nam.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt.getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        res.add(nam);
                    }
                }
            }
            else if ((res.size() == (ii0 + 2) && res.get(ii0 + 1).typ == StreetItemType.NAME && res.get(ii0 + 1).getEndToken().getNext() != null) && res.get(ii0 + 1).getEndToken().getNext().isHiphen()) {
                com.pullenti.ner.Token tt = res.get(ii0 + 1).getEndToken().getNext().getNext();
                com.pullenti.ner.geo.GeoReferent g2 = (tt == null ? null : (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class));
                com.pullenti.ner.Token te = null;
                String name2 = null;
                if (g2 == null && tt != null) {
                    com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("GEO", tt, null);
                    if (rt != null) {
                        te = rt.getEndToken();
                        name2 = rt.referent.toStringEx(true, te.kit.baseLanguage, 0);
                    }
                    else {
                        java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> cits2 = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(tt, 2, null);
                        if (cits2 != null) {
                            if (cits2.size() == 1 && ((cits2.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.PROPERNAME || cits2.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.CITY))) {
                                if (cits2.get(0).ontoItem != null) 
                                    name2 = cits2.get(0).ontoItem.getCanonicText();
                                else 
                                    name2 = cits2.get(0).value;
                                te = cits2.get(0).getEndToken();
                            }
                        }
                    }
                }
                else {
                    te = tt;
                    name2 = g2.toStringEx(true, te.kit.baseLanguage, 0);
                }
                if (((g2 != null && g2.isCity())) || ((g2 == null && name2 != null))) {
                    StreetItemToken r = res.get(ii0 + 1).clone();
                    r.altValue = (name2 + " - " + ((res.get(ii0 + 1).value != null ? res.get(ii0 + 1).value : res.get(ii0 + 1).getSourceText()))).toUpperCase();
                    r.value = (((res.get(ii0 + 1).value != null ? res.get(ii0 + 1).value : res.get(ii0 + 1).getSourceText())) + " - " + name2).toUpperCase();
                    r.setEndToken(te);
                    com.pullenti.unisharp.Utils.putArrayValue(res, ii0 + 1, r);
                }
            }
            StreetItemToken nn = _tryAttachRoadNum(res.get(res.size() - 1).getEndToken().getNext());
            if (nn != null) {
                res.add(nn);
                res.get(ii1).isAbridge = false;
            }
            if (res.size() > (ii0 + 1) && res.get(ii0 + 1).typ == StreetItemType.NAME && com.pullenti.unisharp.Utils.stringsEq(res.get(ii1).termin.getCanonicText(), "АВТОДОРОГА")) {
                if (res.get(ii0 + 1).getBeginToken().isValue("ФЕДЕРАЛЬНЫЙ", null)) 
                    return null;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.geo.internal.MiscLocationHelper.tryParseNpt(res.get(ii0 + 1).getBeginToken());
                if (npt != null && npt.adjectives.size() > 0) {
                    if (npt.getEndToken().isValue("ЗНАЧЕНИЕ", null)) 
                        return null;
                }
            }
        }
        if (res.size() > 0) {
            StreetItemToken it = res.get(res.size() - 1);
            StreetItemToken it0 = (res.size() > 1 ? res.get(res.size() - 2) : null);
            if (it.typ == StreetItemType.NUMBER && !it.numberHasPrefix && !it.isNumberKm) {
                if (it.getBeginToken() instanceof com.pullenti.ner.NumberToken) {
                    if (!it.getBeginToken().getMorph()._getClass().isAdjective() || it.getBeginToken().getMorph()._getClass().isNoun()) {
                        if (AddressItemToken.checkHouseAfter(it.getEndToken().getNext(), false, true)) 
                            it.numberHasPrefix = true;
                        else if (it0 != null && it0.typ == StreetItemType.NOUN && (((com.pullenti.unisharp.Utils.stringsEq(it0.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(it0.termin.getCanonicText(), "МІКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(it0.termin.getCanonicText(), "КВАРТАЛ")) || com.pullenti.unisharp.Utils.stringsEq(it0.termin.getCanonicText(), "ГОРОДОК")))) {
                            AddressItemToken ait = AddressItemToken.tryParsePureItem(it.getBeginToken(), null, null);
                            if (ait != null && ait.getTyp() == AddressItemType.NUMBER && ait.getEndChar() > it.getEndChar()) {
                                it.number = null;
                                it.value = ait.value;
                                it.setEndToken(ait.getEndToken());
                                it.typ = StreetItemType.NAME;
                            }
                        }
                        else if (it0 != null && it0.termin != null && com.pullenti.unisharp.Utils.stringsEq(it0.termin.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ")) 
                            it.numberHasPrefix = true;
                        else if (it0 != null && it0.getBeginToken().isValue("ЛИНИЯ", null)) 
                            it.numberHasPrefix = true;
                        else if (res.size() == 2 && res.get(0).typ == StreetItemType.NOUN && (res.get(0).getWhitespacesAfterCount() < 2)) {
                        }
                        else 
                            res.remove(res.size() - 1);
                    }
                    else 
                        it.numberHasPrefix = true;
                }
            }
        }
        if (res.size() == 0) 
            return null;
        for (int i = 0; i < res.size(); i++) {
            if ((res.get(i).typ == StreetItemType.NOUN && res.get(i).chars.isCapitalUpper() && (((com.pullenti.unisharp.Utils.stringsEq(res.get(i).termin.getCanonicText(), "НАБЕРЕЖНАЯ") || com.pullenti.unisharp.Utils.stringsEq(res.get(i).termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(res.get(i).termin.getCanonicText(), "НАБЕРЕЖНА")) || com.pullenti.unisharp.Utils.stringsEq(res.get(i).termin.getCanonicText(), "МІКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(res.get(i).termin.getCanonicText(), "ГОРОДОК")))) && res.get(i).getBeginToken().isValue(res.get(i).termin.getCanonicText(), null)) {
                boolean ok = false;
                if (i > 0 && ((res.get(i - 1).typ == StreetItemType.NOUN || res.get(i - 1).typ == StreetItemType.STDADJECTIVE))) 
                    ok = true;
                else if (i > 1 && ((res.get(i - 1).typ == StreetItemType.STDADJECTIVE || res.get(i - 1).typ == StreetItemType.NUMBER)) && res.get(i - 2).typ == StreetItemType.NOUN) 
                    ok = true;
                if (ok) {
                    StreetItemToken r = res.get(i).clone();
                    r.termin = null;
                    r.typ = StreetItemType.NAME;
                    com.pullenti.unisharp.Utils.putArrayValue(res, i, r);
                }
            }
        }
        StreetItemToken last = res.get(res.size() - 1);
        for (int kk = 0; kk < 2; kk++) {
            com.pullenti.ner.Token ttt = last.getEndToken().getNext();
            if (((last.typ == StreetItemType.NAME && ttt != null && ttt.getLengthChar() == 1) && ttt.chars.isAllUpper() && (ttt.getWhitespacesBeforeCount() < 2)) && ttt.getNext() != null && ttt.getNext().isChar('.')) {
                if (AddressItemToken.tryParsePureItem(ttt, null, null) != null) 
                    break;
                last = last.clone();
                last.setEndToken(ttt.getNext());
                com.pullenti.unisharp.Utils.putArrayValue(res, res.size() - 1, last);
            }
        }
        if (res.size() > 1) {
            if (res.get(res.size() - 1)._org != null) 
                res.remove(res.size() - 1);
        }
        if (res.size() == 0) 
            return null;
        return res;
    }

    public static void initialize() throws Exception, java.io.IOException {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        m_OntologyEx = new com.pullenti.ner.core.TerminCollection();
        m_StdOntMisc = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new237("УЛИЦА", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("УЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new238("ВУЛИЦЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ВУЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("STREET", StreetItemType.NOUN);
        t.addAbridge("ST.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПЛОЩАДЬ", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ПЛ.");
        t.addAbridge("ПЛОЩ.");
        t.addAbridge("ПЛ-ДЬ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ПЛОЩА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ПЛ.");
        t.addAbridge("ПЛОЩ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("МАЙДАН", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("SQUARE", StreetItemType.NOUN);
        t.addAbridge("SQ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПРОЕЗД", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПР.");
        t.addAbridge("П-Д");
        t.addAbridge("ПР-Д");
        t.addAbridge("ПР-ЗД");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ПРОЕЗД", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПР.");
        t.addAbridge("П-Д");
        t.addAbridge("ПР-Д");
        t.addAbridge("ПР-ЗД");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ЛИНИЯ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ЛІНІЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 2, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        m_Prospect = (t = com.pullenti.ner.core.Termin._new240("ПРОСПЕКТ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE));
        t.addAbridge("ПРОС.");
        t.addAbridge("ПРКТ");
        t.addAbridge("ПРОСП.");
        t.addAbridge("ПР-Т");
        t.addAbridge("ПР-КТ");
        t.addAbridge("П-Т");
        t.addAbridge("П-КТ");
        t.addAbridge("ПР Т");
        t.addAbridge("ПР-ТЕ");
        t.addAbridge("ПР-КТЕ");
        t.addAbridge("П-ТЕ");
        t.addAbridge("П-КТЕ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПЕРЕУЛОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПЕР.");
        t.addAbridge("ПЕР-К");
        t.addVariant("ПРЕУЛОК", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПРОУЛОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПРОУЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ПРОВУЛОК", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПРОВ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("LANE", StreetItemType.NOUN, 0);
        t.addAbridge("LN.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ТУПИК", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ТУП.");
        t.addAbridge("Т.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("БУЛЬВАР", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("БУЛЬВ.");
        t.addAbridge("БУЛ.");
        t.addAbridge("Б-Р");
        t.addAbridge("Б-РЕ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("BOULEVARD", StreetItemType.NOUN, 0);
        t.addAbridge("BLVD");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("СКВЕР", StreetItemType.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("НАБЕРЕЖНАЯ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("НАБ.");
        t.addAbridge("НАБЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("НАБЕРЕЖНА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("НАБ.");
        t.addAbridge("НАБЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("АЛЛЕЯ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("АЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("АЛЕЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("АЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("ALLEY", StreetItemType.NOUN, 0);
        t.addAbridge("ALY.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПРОСЕКА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ПРОСЕК", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ПРОСІКА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ШОССЕ", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("Ш.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("ШОСЕ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("Ш.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("ROAD", StreetItemType.NOUN, 1);
        t.addAbridge("RD.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("МИКРОРАЙОН", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("МКР.");
        t.addAbridge("МИКР-Н");
        t.addAbridge("МКР-Н");
        t.addAbridge("МКРН.");
        t.addAbridge("М-Н");
        t.addAbridge("М-ОН");
        t.addAbridge("М.Р-Н");
        t.addAbridge("М/Р");
        t.addVariant("МІКРОРАЙОН", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("КВАРТАЛ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("КВАРТ.");
        t.addAbridge("КВ-Л");
        t.addAbridge("КВ.");
        m_Ontology.add(t);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new269("КВАРТАЛ ДАЧНОЙ ЗАСТРОЙКИ", "КВАРТАЛ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ПРОМЫШЛЕННЫЙ КВАРТАЛ", false);
        t.addVariant("ИНДУСТРИАЛЬНЫЙ КВАРТАЛ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new270("ЖИЛОЙ КОМПЛЕКС", StreetItemType.NOUN, "ЖК", 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ЖИЛКОМПЛЕКС", false);
        t.addAbridge("ЖИЛ.К.");
        t.addAbridge("Ж/К");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("БРИГАДА", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("БРИГ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ГОРОДОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("МІСТЕЧКО", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.NEUTER);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("HILL", StreetItemType.NOUN, 0);
        t.addAbridge("HL.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ВОЕННЫЙ ГОРОДОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("В.ГОРОДОК");
        t.addAbridge("В/Г");
        t.addAbridge("В/ГОРОДОК");
        t.addAbridge("В/ГОР");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПРОМЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ПРОМЫШЛЕННАЯ ЗОНА", false);
        t.addVariant("ПРОИЗВОДСТВЕННАЯ ЗОНА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ЖИЛАЯ ЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ЖИЛЗОНА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("КОММУНАЛЬНАЯ ЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("КОМЗОНА", false);
        t.addAbridge("КОММУН. ЗОНА");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("МАССИВ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ЖИЛОЙ МАССИВ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ЗОНА", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ЗОНА (МАССИВ)", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ИНДУСТРИАЛЬНЫЙ ПАРК", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("МОСТ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("МІСТ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new240("ПАРК", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new102("PLAZA", StreetItemType.NOUN, 1);
        t.addAbridge("PLZ");
        m_Ontology.add(t);
        m_Metro = (t = com.pullenti.ner.core.Termin._new269("СТАНЦИЯ МЕТРО", "МЕТРО", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE));
        t.addVariant("СТАНЦІЯ МЕТРО", false);
        t.addAbridge("СТ.МЕТРО");
        t.addAbridge("СТ.М.");
        t.addAbridge("МЕТРО");
        m_Ontology.add(t);
        m_Road = (t = com.pullenti.ner.core.Termin._new270("АВТОДОРОГА", StreetItemType.NOUN, "ФАД", 0, com.pullenti.morph.MorphGender.FEMINIE));
        t.addVariant("ФЕДЕРАЛЬНАЯ АВТОДОРОГА", false);
        t.addVariant("АВТОМОБИЛЬНАЯ ДОРОГА", false);
        t.addVariant("АВТОТРАССА", false);
        t.addVariant("ФЕДЕРАЛЬНАЯ ТРАССА", false);
        t.addVariant("ФЕДЕР ТРАССА", false);
        t.addVariant("АВТОМАГИСТРАЛЬ", false);
        t.addAbridge("А/Д");
        t.addAbridge("ФЕДЕР.ТРАССА");
        t.addAbridge("ФЕД.ТРАССА");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new269("ДОРОГА", "АВТОДОРОГА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ТРАССА", false);
        t.addVariant("МАГИСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new241("АВТОДОРОГА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ФЕДЕРАЛЬНА АВТОДОРОГА", false);
        t.addVariant("АВТОМОБІЛЬНА ДОРОГА", false);
        t.addVariant("АВТОТРАСА", false);
        t.addVariant("ФЕДЕРАЛЬНА ТРАСА", false);
        t.addVariant("АВТОМАГІСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new290("ДОРОГА", "АВТОДОРОГА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ТРАСА", false);
        t.addVariant("МАГІСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new291("МОСКОВСКАЯ КОЛЬЦЕВАЯ АВТОМОБИЛЬНАЯ ДОРОГА", "МКАД", StreetItemType.FIX, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("МОСКОВСКАЯ КОЛЬЦЕВАЯ АВТОДОРОГА", false);
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("САДОВОЕ КОЛЬЦО", StreetItemType.FIX));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("БУЛЬВАРНОЕ КОЛЬЦО", StreetItemType.FIX));
        m_Ontology.add(com.pullenti.ner.core.Termin._new100("ТРАНСПОРТНОЕ КОЛЬЦО", StreetItemType.FIX));
        t = com.pullenti.ner.core.Termin._new295("ПОЧТОВОЕ ОТДЕЛЕНИЕ", StreetItemType.NOUN, "ОПС", com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("П.О.");
        t.addAbridge("ПОЧТ.ОТД.");
        t.addAbridge("ПОЧТОВ.ОТД.");
        t.addAbridge("ПОЧТОВОЕ ОТД.");
        t.addAbridge("П/О");
        t.addVariant("ОТДЕЛЕНИЕ ПОЧТОВОЙ СВЯЗИ", false);
        t.addVariant("ПОЧТАМТ", false);
        t.addVariant("ГЛАВПОЧТАМТ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("БУДКА", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ЖЕЛЕЗНОДОРОЖНАЯ БУДКА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("КАЗАРМА", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ЖЕЛЕЗНОДОРОЖНАЯ КАЗАРМА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("МЕСТНОСТЬ", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("СТОЯНКА", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("ПУНКТ", StreetItemType.NOUN, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new237("РАЗЪЕЗД", StreetItemType.NOUN, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("БОЛЬШОЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("БОЛ.");
        t.addAbridge("Б.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new101("ВЕЛИКИЙ", StreetItemType.STDADJECTIVE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("В.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("МАЛЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("МАЛ.");
        t.addAbridge("М.");
        t.addVariant("МАЛИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("СРЕДНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("СРЕД.");
        t.addAbridge("СР.");
        t.addAbridge("С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new101("СЕРЕДНІЙ", StreetItemType.STDADJECTIVE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("СЕРЕД.");
        t.addAbridge("СЕР.");
        t.addAbridge("С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕРХНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("ВЕРХН.");
        t.addAbridge("ВЕРХ.");
        t.addAbridge("ВЕР.");
        t.addAbridge("В.");
        t.addVariant("ВЕРХНІЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НИЖНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("НИЖН.");
        t.addAbridge("НИЖ.");
        t.addAbridge("Н.");
        t.addVariant("НИЖНІЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("СТАРЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("СТАР.");
        t.addAbridge("СТ.");
        t.addVariant("СТАРИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НОВЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("НОВ.");
        t.addVariant("НОВИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НОМЕР", StreetItemType.STDADJECTIVE);
        t.addAbridge("N");
        t.addAbridge("№");
        t.addAbridge("НОМ.");
        m_Ontology.add(t);
        for (String s : new String[] {"ФРИДРИХА ЭНГЕЛЬСА", "КАРЛА МАРКСА", "РОЗЫ ЛЮКСЕМБУРГ"}) {
            t = com.pullenti.ner.core.Termin._new100(s, StreetItemType.STDNAME);
            t.addAllAbridges(0, 0, 0);
            m_Ontology.add(t);
        }
        for (String s : new String[] {"МАРТА", "МАЯ", "ОКТЯБРЯ", "НОЯБРЯ", "БЕРЕЗНЯ", "ТРАВНЯ", "ЖОВТНЯ", "ЛИСТОПАДА"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, StreetItemType.STDNAME));
        }
        for (String s : new String[] {"МАРШАЛА", "ГЕНЕРАЛА", "АДМИРАЛА", "КОСМОНАВТА", "ЛЕТЧИКА", "АВИАКОНСТРУКТОРА", "АРХИТЕКТОРА", "СКУЛЬПТОРА", "ХУДОЖНИКА", "КОНСТРУКТОРА", "АКАДЕМИКА", "ПРОФЕССОРА", "ЛЕЙТЕНАНТА", "КАПИТАНА", "МАЙОРА", "ПОДПОЛКОВНИКА", "ПОЛКОВНИКА", "ПОЛИЦИИ", "МИЛИЦИИ"}) {
            m_StdOntMisc.add(new com.pullenti.ner.core.Termin(s, null, false));
            t = com.pullenti.ner.core.Termin._new100(s, StreetItemType.STDPARTOFNAME);
            t.addAllAbridges(0, 0, 2);
            t.addAllAbridges(2, 5, 0);
            t.addAbridge("ГЛ." + s);
            t.addAbridge("ГЛАВ." + s);
            m_Ontology.add(t);
        }
        for (String s : new String[] {"МАРШАЛА", "ГЕНЕРАЛА", "АДМІРАЛА", "КОСМОНАВТА", "ЛЬОТЧИКА", "АВІАКОНСТРУКТОРА", "АРХІТЕКТОРА", "СКУЛЬПТОРА", "ХУДОЖНИКА", "КОНСТРУКТОРА", "АКАДЕМІКА", "ПРОФЕСОРА", "ЛЕЙТЕНАНТА", "КАПІТАН", "МАЙОР", "ПІДПОЛКОВНИК", "ПОЛКОВНИК", "ПОЛІЦІЇ", "МІЛІЦІЇ"}) {
            m_StdOntMisc.add(new com.pullenti.ner.core.Termin(s, null, false));
            t = com.pullenti.ner.core.Termin._new101(s, StreetItemType.STDPARTOFNAME, com.pullenti.morph.MorphLang.UA);
            t.addAllAbridges(0, 0, 2);
            t.addAllAbridges(2, 5, 0);
            t.addAbridge("ГЛ." + s);
            t.addAbridge("ГЛАВ." + s);
            m_Ontology.add(t);
        }
        t = com.pullenti.ner.core.Termin._new128("ВАСИЛЬЕВСКОГО ОСТРОВА", StreetItemType.STDNAME, "ВО");
        t.addAbridge("В.О.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПЕТРОГРАДСКОЙ СТОРОНЫ", StreetItemType.STDNAME);
        t.addAbridge("П.С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОЛИМПИЙСКАЯ ДЕРЕВНЯ", StreetItemType.FIX);
        t.addAbridge("ОЛИМП. ДЕРЕВНЯ");
        t.addAbridge("ОЛИМП. ДЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЛЕНИНСКИЕ ГОРЫ", StreetItemType.FIX);
        m_Ontology.add(t);
        byte[] obj = ResourceHelper.getBytes("s.dat");
        if (obj == null) 
            throw new Exception("Can't file resource file s.dat in Location analyzer");
        String streets = com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), com.pullenti.ner.geo.internal.MiscLocationHelper.deflate(obj), 0, -1);
        StringBuilder name = new StringBuilder();
        java.util.HashMap<String, Boolean> names = new java.util.HashMap<String, Boolean>();
        for (String line0 : com.pullenti.unisharp.Utils.split(streets, String.valueOf('\n'), false)) {
            String line = line0.trim();
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(line)) 
                continue;
            if (line.indexOf(';') >= 0) {
                String[] parts = com.pullenti.unisharp.Utils.split(line, String.valueOf(';'), false);
                t = com.pullenti.ner.core.Termin._new320(StreetItemType.NAME, true);
                t.initByNormalText(parts[0], null);
                for (int j = 1; j < parts.length; j++) {
                    t.addVariant(parts[j], true);
                }
            }
            else {
                t = com.pullenti.ner.core.Termin._new320(StreetItemType.NAME, true);
                t.initByNormalText(line, null);
            }
            if (t.terms.size() > 1) 
                t.tag = StreetItemType.STDNAME;
            m_OntologyEx.add(t);
        }
    }

    public static com.pullenti.ner.Token checkStdName(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        if (((StreetItemType)tok.termin.tag) == StreetItemType.STDNAME) 
            return tok.getEndToken();
        return null;
    }

    public static boolean checkKeyword(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return false;
        return ((StreetItemType)tok.termin.tag) == StreetItemType.NOUN;
    }

    public static boolean checkOnto(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        com.pullenti.ner.core.TerminToken tok = m_OntologyEx.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return false;
        return true;
    }

    public static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static com.pullenti.ner.core.TerminCollection m_OntologyEx;

    private static com.pullenti.ner.core.TerminCollection m_StdOntMisc;

    private static com.pullenti.ner.core.Termin m_Prospect;

    private static com.pullenti.ner.core.Termin m_Metro;

    private static com.pullenti.ner.core.Termin m_Road;

    private static String[] m_RegTails;

    public static boolean _isRegion(String txt) {
        txt = txt.toUpperCase();
        for (String v : m_RegTails) {
            if (com.pullenti.morph.LanguageHelper.endsWith(txt, v)) 
                return true;
        }
        return false;
    }

    private static String[] m_SpecTails;

    public static boolean _isSpec(String txt) {
        txt = txt.toUpperCase();
        for (String v : m_SpecTails) {
            if (com.pullenti.morph.LanguageHelper.endsWith(txt, v)) 
                return true;
        }
        return false;
    }

    public static StreetItemToken _new73(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.geo.internal.OrgItemToken _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res._org = _arg4;
        return res;
    }

    public static StreetItemToken _new181(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.core.Termin _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.termin = _arg4;
        return res;
    }

    public static StreetItemToken _new183(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        return res;
    }

    public static StreetItemToken _new184(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, com.pullenti.ner.MorphCollection _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static StreetItemToken _new185(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.numberHasPrefix = _arg5;
        return res;
    }

    public static StreetItemToken _new186(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static StreetItemToken _new187(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.hasStdSuffix = _arg3;
        return res;
    }

    public static StreetItemToken _new188(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.Termin _arg3, StreetItemType _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.termin = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static StreetItemToken _new189(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.Termin _arg3, StreetItemType _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.termin = _arg3;
        res.typ = _arg4;
        res.isAbridge = _arg5;
        return res;
    }

    public static StreetItemToken _new190(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.address.StreetReferent _arg4, com.pullenti.ner.MorphCollection _arg5, boolean _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.existStreet = _arg4;
        res.setMorph(_arg5);
        res.isInDictionary = _arg6;
        return res;
    }

    public static StreetItemToken _new193(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.numberHasPrefix = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static StreetItemToken _new194(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.core.Termin _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.termin = _arg4;
        res.isAbridge = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static StreetItemToken _new196(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.core.Termin _arg4, com.pullenti.ner.core.Termin _arg5, boolean _arg6, com.pullenti.ner.MorphCollection _arg7, int _arg8) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.termin = _arg4;
        res.altTermin = _arg5;
        res.isAbridge = _arg6;
        res.setMorph(_arg7);
        res.nounIsDoubtCoef = _arg8;
        return res;
    }

    public static StreetItemToken _new197(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.value = _arg5;
        return res;
    }

    public static StreetItemToken _new199(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, com.pullenti.ner.core.Termin _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.termin = _arg5;
        return res;
    }

    public static StreetItemToken _new202(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.isInDictionary = _arg5;
        return res;
    }

    public static StreetItemToken _new203(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, boolean _arg5, com.pullenti.ner.core.Termin _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.isInDictionary = _arg5;
        res.termin = _arg6;
        return res;
    }

    public static StreetItemToken _new204(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static StreetItemToken _new205(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5, String _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.value = _arg5;
        res.altValue = _arg6;
        return res;
    }

    public static StreetItemToken _new206(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4, String _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isInDictionary = _arg4;
        res.value = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static StreetItemToken _new208(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.noGeoInThisToken = _arg5;
        return res;
    }

    public static StreetItemToken _new209(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.noGeoInThisToken = _arg4;
        return res;
    }

    public static StreetItemToken _new210(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.value = _arg5;
        return res;
    }

    public static StreetItemToken _new213(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, com.pullenti.ner.geo.internal.Condition _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.cond = _arg5;
        return res;
    }

    public static StreetItemToken _new225(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.altValue = _arg5;
        return res;
    }

    public static StreetItemToken _new233(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.isInBrackets = _arg5;
        return res;
    }

    public static StreetItemToken _new236(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isInBrackets = _arg4;
        return res;
    }

    public static StreetItemToken _new1267(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isRailway = _arg4;
        return res;
    }

    public static StreetItemToken _new1268(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4, int _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isRailway = _arg4;
        res.nounIsDoubtCoef = _arg5;
        return res;
    }

    public StreetItemToken() {
        super();
    }
    
    static {
        m_RegTails = new String[] {"ГОРОДОК", "РАЙОН", "МАССИВ", "МАСИВ", "КОМПЛЕКС", "ЗОНА", "КВАРТАЛ", "ОТДЕЛЕНИЕ", "ПАРК", "МЕСТНОСТЬ", "РАЗЪЕЗД"};
        m_SpecTails = new String[] {"БУДКА", "КАЗАРМА"};
    }
}
