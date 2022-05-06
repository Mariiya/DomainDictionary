/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemEngItem extends com.pullenti.ner.MetaToken {

    public OrgItemEngItem(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public String fullValue;

    public String shortValue;

    public boolean isBank() {
        return com.pullenti.unisharp.Utils.stringsEq(fullValue, "bank");
    }


    public static OrgItemEngItem tryAttach(com.pullenti.ner.Token t, boolean canBeCyr) {
        if (t == null || !(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.core.TerminToken tok = (canBeCyr ? m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) : null);
        if (!t.chars.isLatinLetter() && tok == null) {
            if (!t.isAnd() || t.getNext() == null) 
                return null;
            if (t.getNext().isValue("COMPANY", null) || t.getNext().isValue("CO", null)) {
                OrgItemEngItem res = new OrgItemEngItem(t, t.getNext());
                res.fullValue = "company";
                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
                    res.setEndToken(res.getEndToken().getNext());
                return res;
            }
            return null;
        }
        if (t.chars.isLatinLetter()) 
            tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            if (!_checkTok(tok)) 
                return null;
            OrgItemEngItem res = new OrgItemEngItem(tok.getBeginToken(), tok.getEndToken());
            res.fullValue = tok.termin.getCanonicText().toLowerCase();
            res.shortValue = tok.termin.acronym;
            return res;
        }
        return null;
    }

    private static boolean _checkTok(com.pullenti.ner.core.TerminToken tok) {
        if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.acronym, "SA")) {
            com.pullenti.ner.Token tt0 = tok.getBeginToken().getPrevious();
            if (tt0 != null && tt0.isChar('.')) 
                tt0 = tt0.getPrevious();
            if (tt0 instanceof com.pullenti.ner.TextToken) {
                if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt0, com.pullenti.ner.TextToken.class)).term, "U")) 
                    return false;
            }
        }
        else if (tok.getBeginToken().isValue("CO", null) && tok.getBeginToken() == tok.getEndToken()) {
            if (tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isHiphen()) 
                return false;
        }
        if (!tok.isWhitespaceAfter()) {
            if (tok.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) 
                return false;
        }
        return true;
    }

    public static com.pullenti.ner.ReferentToken tryAttachOrg(com.pullenti.ner.Token t, boolean canBeCyr) {
        if (t == null) 
            return null;
        boolean br = false;
        if (t.isChar('(') && t.getNext() != null) {
            t = t.getNext();
            br = true;
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS && t.getMorph()._getClass().isAdjective() && t.chars.isCapitalUpper()) {
            }
            else 
                return null;
        }
        else {
            if (t.chars.isAllLower()) 
                return null;
            if ((t.getLengthChar() < 3) && !t.chars.isLetter()) 
                return null;
            if (!t.chars.isLatinLetter()) {
                if (!canBeCyr || !t.chars.isCyrillicLetter()) 
                    return null;
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t0;
        int namWo = 0;
        OrgItemEngItem tok = null;
        com.pullenti.ner.geo.GeoReferent _geo = null;
        OrgItemTypeToken addTyp = null;
        for (; t != null; t = t.getNext()) {
            if (t != t0 && t.getWhitespacesBeforeCount() > 1) 
                break;
            if (t.isChar(')')) 
                break;
            if (t.isChar('(') && t.getNext() != null) {
                if ((t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                    _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    t = t.getNext().getNext();
                    continue;
                }
                OrgItemTypeToken typ = OrgItemTypeToken.tryAttach(t.getNext(), true);
                if ((typ != null && typ.getEndToken().getNext() != null && typ.getEndToken().getNext().isChar(')')) && typ.chars.isLatinLetter()) {
                    addTyp = typ;
                    t = typ.getEndToken().getNext();
                    continue;
                }
                if (((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) && t.getNext().chars.isCapitalUpper()) {
                    t1 = (t = t.getNext().getNext());
                    continue;
                }
                break;
            }
            tok = tryAttach(t, canBeCyr);
            if (tok == null && t.isCharOf(".,") && t.getNext() != null) {
                tok = tryAttach(t.getNext(), canBeCyr);
                if (tok == null && t.getNext().isCharOf(",.")) 
                    tok = tryAttach(t.getNext().getNext(), canBeCyr);
            }
            if (tok != null) {
                if (tok.getLengthChar() == 1 && t0.chars.isCyrillicLetter()) 
                    return null;
                break;
            }
            if (t.isHiphen() && !t.isWhitespaceAfter() && !t.isWhitespaceBefore()) 
                continue;
            if (t.isCharOf("&+") || t.isAnd()) 
                continue;
            if (t.isChar('.')) {
                if (t.getPrevious() != null && t.getPrevious().getLengthChar() == 1) 
                    continue;
                else if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t.getNext())) 
                    break;
            }
            if (!t.chars.isLatinLetter()) {
                if (!canBeCyr || !t.chars.isCyrillicLetter()) 
                    break;
            }
            if (t.chars.isAllLower()) {
                if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) 
                    continue;
                if (br) 
                    continue;
                break;
            }
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if (mc.isVerb()) {
                if (t.getNext() != null && t.getNext().getMorph()._getClass().isPreposition()) 
                    break;
            }
            if (t.getNext() != null && t.getNext().isValue("OF", null)) 
                break;
            if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            else if (t instanceof com.pullenti.ner.TextToken) 
                namWo++;
            t1 = t;
        }
        if (tok == null) 
            return null;
        if (t0 == tok.getBeginToken()) {
            com.pullenti.ner.core.BracketSequenceToken br2 = com.pullenti.ner.core.BracketHelper.tryParse(tok.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br2 != null) {
                com.pullenti.ner._org.OrganizationReferent org1 = new com.pullenti.ner._org.OrganizationReferent();
                if (tok.shortValue != null) 
                    org1.addTypeStr(tok.shortValue);
                org1.addTypeStr(tok.fullValue);
                String nam1 = com.pullenti.ner.core.MiscHelper.getTextValue(br2.getBeginToken(), br2.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (nam1 != null) {
                    org1.addName(nam1, true, null);
                    return new com.pullenti.ner.ReferentToken(org1, t0, br2.getEndToken(), null);
                }
            }
            return null;
        }
        com.pullenti.ner._org.OrganizationReferent __org = new com.pullenti.ner._org.OrganizationReferent();
        com.pullenti.ner.Token te = tok.getEndToken();
        if (tok.isBank()) 
            t1 = tok.getEndToken();
        if (com.pullenti.unisharp.Utils.stringsEq(tok.fullValue, "company") && (tok.getWhitespacesAfterCount() < 3)) {
            OrgItemEngItem tok1 = tryAttach(tok.getEndToken().getNext(), canBeCyr);
            if (tok1 != null) {
                t1 = tok.getEndToken();
                tok = tok1;
                te = tok.getEndToken();
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(tok.fullValue, "company")) {
            if (namWo == 0) 
                return null;
        }
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES);
        if (com.pullenti.unisharp.Utils.stringsEq(nam, "STOCK") && com.pullenti.unisharp.Utils.stringsEq(tok.fullValue, "company")) 
            return null;
        String altNam = null;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(nam)) 
            return null;
        if (nam.indexOf('(') > 0) {
            int i1 = nam.indexOf('(');
            int i2 = nam.indexOf(')');
            if (i1 < i2) {
                altNam = nam;
                String tai = null;
                if ((i2 + 1) < nam.length()) 
                    tai = nam.substring(i2).trim();
                nam = nam.substring(0, 0 + i1).trim();
                if (tai != null) 
                    nam = (nam + " " + tai);
            }
        }
        if (tok.isBank()) {
            __org.addTypeStr((tok.kit.baseLanguage.isEn() ? "bank" : "банк"));
            __org.addProfile(com.pullenti.ner._org.OrgProfile.FINANCE);
            if ((t1.getNext() != null && t1.getNext().isValue("OF", null) && t1.getNext().getNext() != null) && t1.getNext().getNext().chars.isLatinLetter()) {
                OrgItemNameToken nam0 = OrgItemNameToken.tryAttach(t1.getNext(), null, false, false);
                if (nam0 != null) 
                    te = nam0.getEndToken();
                else 
                    te = t1.getNext().getNext();
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(t0, te, com.pullenti.ner.core.GetTextAttr.NO);
                if (te.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    __org.addGeoObject((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(te.getReferent(), com.pullenti.ner.geo.GeoReferent.class));
            }
            else if (t0 == t1) 
                return null;
        }
        else {
            if (tok.shortValue != null) 
                __org.addTypeStr(tok.shortValue);
            __org.addTypeStr(tok.fullValue);
        }
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(nam)) 
            return null;
        __org.addName(nam, true, null);
        if (altNam != null) 
            __org.addName(altNam, true, null);
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(__org, t0, te, null);
        t = te;
        while (t.getNext() != null) {
            if (t.getNext().isCharOf(",.")) 
                t = t.getNext();
            else 
                break;
        }
        if (t.getWhitespacesAfterCount() < 2) {
            tok = tryAttach(t.getNext(), canBeCyr);
            if (tok != null) {
                if (tok.shortValue != null) 
                    __org.addTypeStr(tok.shortValue);
                __org.addTypeStr(tok.fullValue);
                res.setEndToken(tok.getEndToken());
            }
        }
        if (_geo != null) 
            __org.addGeoObject(_geo);
        if (addTyp != null) 
            __org.addType(addTyp, false);
        if (!br) 
            return res;
        t = res.getEndToken();
        if (t.getNext() == null) {
        }
        else if (t.getNext().isChar(')')) 
            res.setEndToken(t.getNext());
        else 
            return null;
        return res;
    }

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("BANK", null, false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Public Limited Company".toUpperCase(), "PLC");
        t.addAbridge("P.L.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Limited Liability Company".toUpperCase(), "LLC");
        t.addAbridge("L.L.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Limited Liability Partnership".toUpperCase(), "LLP");
        t.addAbridge("L.L.P.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Limited Liability Limited Partnership".toUpperCase(), "LLLP");
        t.addAbridge("L.L.L.P.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Limited Duration Company".toUpperCase(), "LDC");
        t.addAbridge("L.D.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("International Business Company".toUpperCase(), "IBC");
        t.addAbridge("I.B.S.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Joint stock company".toUpperCase(), "JSC");
        t.addAbridge("J.S.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Open Joint stock company".toUpperCase(), "OJSC");
        t.addAbridge("O.J.S.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Sosiedad Anonima".toUpperCase(), "SA");
        t.addVariant("Sociedad Anonima".toUpperCase(), false);
        t.addAbridge("S.A.");
        t.addVariant("SPA", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Société en commandite".toUpperCase(), "SC");
        t.addAbridge("S.C.");
        t.addVariant("SCS", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Societas Europaea".toUpperCase(), "SE");
        t.addAbridge("S.E.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Società in accomandita".toUpperCase(), "SAS");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Société en commandite par actions".toUpperCase(), "SCA");
        t.addAbridge("S.C.A.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Société en nom collectif".toUpperCase(), "SNC");
        t.addVariant("Società in nome collettivo".toUpperCase(), false);
        t.addAbridge("S.N.C.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("General Partnership".toUpperCase(), "GP");
        t.addVariant("General Partners", false);
        t.addAbridge("G.P.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Limited Partnership".toUpperCase(), "LP");
        t.addAbridge("L.P.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Kommanditaktiengesellschaft".toUpperCase(), "KGAA");
        t.addVariant("KOMMAG", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Societe a Responsidilite Limitee".toUpperCase(), "SRL");
        t.addAbridge("S.A.R.L.");
        t.addAbridge("S.R.L.");
        t.addVariant("SARL", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Società a garanzia limitata".toUpperCase(), "SAGL");
        t.addAbridge("S.A.G.L.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Società limitata".toUpperCase(), "SL");
        t.addAbridge("S.L.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Vennootschap Met Beperkte Aansparkelij kheid".toUpperCase(), "BV");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Vennootschap Met Beperkte Aansparkelij".toUpperCase(), "AVV");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Naamlose Vennootschap".toUpperCase(), "NV");
        t.addAbridge("N.V.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Gesellschaft mit beschrakter Haftung".toUpperCase(), "GMBH");
        t.addVariant("ГМБХ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Aktiengesellschaft".toUpperCase(), "AG");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("International Company".toUpperCase(), "IC");
        t.addAbridge("I.C.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("And Company".toUpperCase(), null, false);
        t.addVariant("& Company", false);
        t.addVariant("& Co", false);
        t.addVariant("& Company", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Kollektivgesellschaft".toUpperCase(), "KG");
        t.addAbridge("K.G.");
        t.addVariant("OHG", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("Kommanditgesellschaft".toUpperCase(), "KG");
        t.addVariant("KOMMG", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("LIMITED", null, false);
        t.addAbridge("LTD");
        t.addVariant("LTD", false);
        t.addVariant("ЛИМИТЕД", false);
        t.addVariant("ЛТД", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("PRIVATE LIMITED", null, false);
        t.addVariant("PTE LTD", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("INCORPORATED", null, false);
        t.addAbridge("INC");
        t.addVariant("INC", false);
        t.addVariant("ИНКОРПОРЕЙТЕД", false);
        t.addVariant("ИНК", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("CORPORATION", null, false);
        t.addVariant("CO", false);
        t.addVariant("СО", false);
        t.addVariant("КОРПОРЕЙШН", false);
        t.addVariant("КОРПОРЕЙШЕН", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("COMPANY", null, false);
        m_Ontology.add(t);
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;
    public OrgItemEngItem() {
        super();
    }
}
