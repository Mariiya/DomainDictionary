/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class OrgTypToken extends com.pullenti.ner.MetaToken {

    public OrgTypToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public boolean isDoubt;

    public java.util.ArrayList<String> vals = new java.util.ArrayList<String>();

    public OrgTypToken clone() {
        OrgTypToken res = new OrgTypToken(getBeginToken(), getEndToken());
        com.pullenti.unisharp.Utils.addToArrayList(res.vals, vals);
        res.isDoubt = isDoubt;
        return res;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (isDoubt) 
            tmp.append("? ");
        for (int i = 0; i < vals.size(); i++) {
            if (i > 0) 
                tmp.append(" / ");
            tmp.append(vals.get(i));
        }
        return tmp.toString();
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.oTRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            boolean afterTerr = false;
            com.pullenti.ner.Token tt = MiscLocationHelper.checkTerritory(t);
            if (tt != null && tt.getNext() != null) {
                afterTerr = true;
                t = tt.getNext();
            }
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            OrgTypToken ty = tryParse(t, afterTerr, ad);
            if (ty != null) {
                if (d == null) 
                    d = new GeoTokenData(t);
                d.orgTyp = ty;
                t = ty.getEndToken();
            }
        }
        ad.oTRegime = true;
    }

    public static OrgTypToken tryParse(com.pullenti.ner.Token t, boolean afterTerr, GeoAnalyzerData ad) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (t.getLengthChar() == 1 && !t.chars.isLetter()) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad != null && SPEEDREGIME && ((ad.oTRegime || ad.allRegime))) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            if (d != null) 
                return d.orgTyp;
            return null;
        }
        if (ad.oLevel > 2) 
            return null;
        ad.oLevel++;
        OrgTypToken res = _TryParse(t, afterTerr, 0);
        ad.oLevel--;
        return res;
    }

    private static OrgTypToken _TryParse(com.pullenti.ner.Token t, boolean afterTerr, int lev) {
        if (t == null) 
            return null;
        if (t.isValue("СП", null)) {
            if (!afterTerr && t.chars.isAllLower()) 
                return null;
        }
        if (t.isValue("НП", null)) {
            if (!afterTerr && t.chars.isAllLower()) 
                return null;
        }
        if ((t.isValue("ОФИС", null) || t.isValue("ФАД", null) || t.isValue("АД", null)) || t.isValue("КОРПУС", null)) 
            return null;
        if (t.isValue("ФЕДЕРАЦИЯ", null) || t.isValue("СОЮЗ", null) || t.isValue("ПРЕФЕКТУРА", null)) 
            return null;
        com.pullenti.ner.Token t1 = null;
        java.util.ArrayList<String> typs = null;
        boolean doubt = false;
        com.pullenti.ner.MorphCollection _morph = null;
        com.pullenti.ner.core.TerminToken tok = m_OrgOntology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            t1 = tok.getEndToken();
            typs = new java.util.ArrayList<String>();
            _morph = tok.getMorph();
            typs.add(tok.termin.getCanonicText().toLowerCase());
            if (tok.termin.acronym != null) 
                typs.add(tok.termin.acronym);
            if (tok.getEndToken() == t) {
                if ((t.getLengthChar() < 4) && (t instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.LanguageHelper.endsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "К")) {
                    com.pullenti.ner.core.IntOntologyToken oi = TerrItemToken.checkOntoItem(t.getNext());
                    if (oi != null) {
                        if (t.getNext().getMorphClassInDictionary().isAdjective() && oi.getBeginToken() == oi.getEndToken()) {
                        }
                        else 
                            return null;
                    }
                    if ((!afterTerr && t.chars.isAllUpper() && t.getNext() != null) && t.getNext().chars.isAllUpper() && t.getNext().getLengthChar() > 1) 
                        return null;
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "МЕСТОРОЖДЕНИЕ") && (tok.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && tok.getEndToken().getNext().chars.isAllLower()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tok.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.chars.isAllLower()) 
                    tok.setEndToken(npt.getEndToken());
            }
            if ((((t.chars.isAllUpper() && t.getLengthChar() == 1 && t.getNext() != null) && t.getNext().isChar('.') && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().getLengthChar() == 1 && t.getNext().getNext().chars.isAllUpper()) && t.getNext().getNext().getNext() == tok.getEndToken() && tok.getEndToken().isChar('.')) 
                return null;
        }
        else {
            if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(t)) 
                return null;
            com.pullenti.ner.ReferentToken rtok = t.kit.processReferent("ORGANIZATION", t, "MINTYPE");
            if (rtok != null) {
                if (rtok.getEndToken() == t && t.isValue("ТК", null)) {
                    if (TerrItemToken.checkOntoItem(t.getNext()) != null) 
                        return null;
                    if (t.chars.isAllUpper() && t.getNext() != null && t.getNext().chars.isAllUpper()) 
                        return null;
                }
                String prof = rtok.referent.getStringValue("PROFILE");
                if (com.pullenti.unisharp.Utils.stringsCompare((prof != null ? prof : ""), "UNIT", true) == 0) 
                    doubt = true;
                t1 = rtok.getEndToken();
                typs = rtok.referent.getStringValues("TYPE");
                _morph = rtok.getMorph();
            }
        }
        if (((t1 == null && (t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() >= 2) && t.getLengthChar() <= 4 && t.chars.isAllUpper()) && t.chars.isCyrillicLetter()) {
            if (com.pullenti.ner.address.internal.AddressItemToken.tryParsePureItem(t, null, null) != null) 
                return null;
            if (t.getLengthChar() == 2) 
                return null;
            if (TerrItemToken.checkOntoItem(t) != null) 
                return null;
            typs = new java.util.ArrayList<String>();
            typs.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
            t1 = t;
            doubt = true;
        }
        if (t1 == null && afterTerr) {
            com.pullenti.ner.core.TerminToken pt = com.pullenti.ner.address.internal.AddressItemToken.M_PLOT.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (pt != null) {
                typs = new java.util.ArrayList<String>();
                typs.add("участок");
                t1 = pt.getEndToken();
                doubt = true;
            }
            else if ((((pt = com.pullenti.ner.address.internal.AddressItemToken.M_OWNER.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                typs = new java.util.ArrayList<String>();
                typs.add("владение");
                t1 = pt.getEndToken();
                doubt = true;
            }
        }
        if (t1 == null) 
            return null;
        if (_morph == null) 
            _morph = t1.getMorph();
        OrgTypToken res = _new1269(t, t1, doubt, typs, _morph);
        if ((t == t1 && (t.getLengthChar() < 3) && t.getNext() != null) && t.getNext().isChar('.')) 
            res.setEndToken(t1.getNext());
        if ((lev < 2) && (res.getWhitespacesAfterCount() < 3)) {
            OrgTypToken _next = tryParse(res.getEndToken().getNext(), afterTerr, null);
            if (_next != null && !_next.getBeginToken().chars.isAllLower()) {
                NameToken nam = NameToken.tryParse(_next.getEndToken().getNext(), NameTokenType.ORG, 0);
                if (nam == null || _next.getWhitespacesAfterCount() > 3) 
                    _next = null;
                else if ((nam.number != null && nam.name == null && _next.getLengthChar() > 2) && _next.isDoubt) 
                    _next = null;
            }
            if (_next != null) {
                if (!_next.isDoubt) 
                    res.isDoubt = false;
                res.mergeWith(_next);
            }
        }
        return res;
    }

    public void mergeWith(OrgTypToken ty) {
        for (String v : ty.vals) {
            if (!vals.contains(v)) 
                vals.add(v);
        }
        setEndToken(ty.getEndToken());
    }

    public static java.util.ArrayList<com.pullenti.ner.core.Termin> findTerminByAcronym(String abbr) {
        com.pullenti.ner.core.Termin te = com.pullenti.ner.core.Termin._new969(abbr, abbr);
        return m_OrgOntology.findTerminsByTermin(te);
    }

    public static void initialize() {
        m_OrgOntology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new969("САДОВОЕ ТОВАРИЩЕСТВО", "СТ");
        t.addVariant("САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        t.acronym = "СТ";
        t.addAbridge("С/ТОВ");
        t.addAbridge("ПК СТ");
        t.addAbridge("САД.ТОВ.");
        t.addAbridge("САДОВ.ТОВ.");
        t.addAbridge("С/Т");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ ТОВАРИЩЕСТВО", null, false);
        t.addAbridge("Д/Т");
        t.addAbridge("ДАЧ/Т");
        t.acronym = "ДТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ЖИЛИЩНОЕ ТОВАРИЩЕСТВО", null, false);
        t.addAbridge("Ж/Т");
        t.addAbridge("ЖИЛ/Т");
        t.acronym = "ЖТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("С/К");
        t.acronym = "СК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", null, false);
        t.addVariant("ПОТРЕБКООПЕРАТИВ", false);
        t.acronym = "ПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВОЕ ОБЩЕСТВО", null, false);
        t.addAbridge("С/О");
        t.acronym = "СО";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВОДЧЕСКОЕ ДАЧНОЕ ТОВАРИЩЕСТВО", null, false);
        t.addVariant("САДОВОЕ ДАЧНОЕ ТОВАРИЩЕСТВО", false);
        t.acronym = "СДТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", null, false);
        t.acronym = "ДНО";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", null, false);
        t.acronym = "ДНП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", null, false);
        t.acronym = "ДНТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЫЙ ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", null, false);
        t.acronym = "ДПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", null, false);
        t.addVariant("ДАЧНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", false);
        t.acronym = "ДСК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", null, false);
        t.acronym = "СПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", null, false);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        t.addVariant("ТСНСТ", false);
        t.acronym = "СНТ";
        t.acronymCanBeLower = true;
        t.addAbridge("САДОВОЕ НЕКОМ-Е ТОВАРИЩЕСТВО");
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", "СНО", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", "СНП", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1274("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", "СНТ", "СНТ", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("САДОВОДЧЕСКОЕ ОГОРОДНИЧЕСКОЕ ТОВАРИЩЕСТВО", "СОТ", true);
        t.addVariant("САДОВОЕ ОГОРОДНИЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", "ДНТ", true);
        t.addVariant("ДАЧНО НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("НЕКОММЕРЧЕСКОЕ САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", "НСТ", true);
        t.addVariant("НЕКОММЕРЧЕСКОЕ САДОВОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ОБЪЕДИНЕННОЕ НЕКОММЕРЧЕСКОЕ САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", "ОНСТ", true);
        t.addVariant("ОБЪЕДИНЕННОЕ НЕКОММЕРЧЕСКОЕ САДОВОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("САДОВОДЧЕСКАЯ ПОТРЕБИТЕЛЬСКАЯ КООПЕРАЦИЯ", "СПК", true);
        t.addVariant("САДОВАЯ ПОТРЕБИТЕЛЬСКАЯ КООПЕРАЦИЯ", false);
        t.addVariant("САДОВОДЧЕСКИЙ ПОТРЕБИТЕЛЬНЫЙ КООПЕРАТИВ", false);
        t.addVariant("САДОВОДЧЕСКИЙ ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ДАЧНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ДСК", true);
        t.addVariant("ДАЧНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", false);
        m_OrgOntology.add(t);
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ДАЧНО СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", "ДСПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ЖИЛИЩНЫЙ СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", "ЖСПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ЖИЛИЩНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ЖСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ЖИЛИЩНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "ЖСКИЗ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", "ОНО", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", "ОНП", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", "ОНТ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ОГОРОДНИЧЕСКИЙ ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ОПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "СТСН", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "ТСН", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ТОВАРИЩЕСТВО СОБСТВЕННИКОВ ЖИЛЬЯ", "ТСЖ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("САДОВЫЕ ЗЕМЕЛЬНЫЕ УЧАСТКИ", "СЗУ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ТОВАРИЩЕСТВО ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "ТИЗ", true));
        t = com.pullenti.ner.core.Termin._new1272("КОЛЛЕКТИВ ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "КИЗ", true);
        t.addVariant("КИЗК", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "СНТСН", true);
        t.addVariant("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", false);
        t.addVariant("СНТ СН", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО СОБСТВЕННИКОВ", "НПС", true);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ЛИЧНОЕ ПОДСОБНОЕ ХОЗЯЙСТВО", "ЛПХ", true);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ОБЪЕДИНЕНИЕ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СОВМЕСТНОЕ ПРЕДПРИЯТИЕ", null, false);
        t.acronym = "СП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", null, false);
        t.acronym = "НП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("АВТОМОБИЛЬНЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("А/К");
        t.acronym = "АК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ГАРАЖНЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("Г/К");
        t.addAbridge("ГР.КОП.");
        t.addAbridge("ГАР.КОП.");
        t.addAbridge("ГАР.КООП.");
        t.addVariant("ГАРАЖНЫЙ КООП", false);
        t.acronym = "ГК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПРОИЗВОДСТВЕННЫЙ СЕЛЬСКОХОЗЯЙСТВЕННЫЙ КООПЕРАТИВ", null, false);
        t.addVariant("ПРОИЗВОДСТВЕННО СЕЛЬСКОХОЗЯЙСТВЕННЫЙ КООПЕРАТИВ", false);
        t.acronym = "ПСК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ГСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ГАРАЖНО ЭКСПЛУАТАЦИОННЫЙ КООПЕРАТИВ", "ГЭК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ГАРАЖНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ГПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ПОТРЕБИТЕЛЬСКИЙ ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ПГСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ГАРАЖНЫЙ СТРОИТЕЛЬНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ГСПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ПОТРЕБИТЕЛЬСКИЙ ГАРАЖНЫЙ КООПЕРАТИВ", "ПГК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new1272("ИНДИВИДУАЛЬНОЕ ЖИЛИЩНОЕ СТРОИТЕЛЬСТВО", "ИЖС", true));
        m_OrgOntology.add(new com.pullenti.ner.core.Termin("ЖИВОТНОВОДЧЕСКАЯ ТОЧКА", null, false));
        t = com.pullenti.ner.core.Termin._new1272("СТАНЦИЯ ТЕХНИЧЕСКОГО ОБСЛУЖИВАНИЯ", "СТО", true);
        t.addVariant("СТАНЦИЯ ТЕХОБСЛУЖИВАНИЯ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("АВТО ЗАПРАВОЧНАЯ СТАНЦИЯ", "АЗС", true);
        t.addVariant("АВТОЗАПРАВОЧНАЯ СТАНЦИЯ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ДАЧНАЯ ЗАСТРОЙКА", "ДЗ", true);
        t.addVariant("КВАРТАЛ ДАЧНОЙ ЗАСТРОЙКИ", false);
        t.addVariant("ЗОНА ДАЧНОЙ ЗАСТРОЙКИ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("КОТТЕДЖНЫЙ ПОСЕЛОК", "КП", true);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ДАЧНЫЙ ПОСЕЛОК", "ДП", true);
        t.addAbridge("Д/П");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САНАТОРИЙ", null, false);
        t.addAbridge("САН.");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДОМ ОТДЫХА", null, false);
        t.addAbridge("Д/О");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("БАЗА ОТДЫХА", null, false);
        t.addAbridge("Б/О");
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ФЕРМЕРСКОЕ ХОЗЯЙСТВО", "ФХ", true);
        t.addAbridge("Ф/Х");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СОВХОЗ", null, false);
        t.addAbridge("С-ЗА");
        t.addAbridge("С/ЗА");
        t.addAbridge("С/З");
        t.addAbridge("СХ.");
        t.addAbridge("С/Х");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПИОНЕРСКИЙ ЛАГЕРЬ", null, false);
        t.addAbridge("П/Л");
        t.addAbridge("П.Л.");
        t.addAbridge("ПИОНЕР.ЛАГ.");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("КУРОРТ", null, false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("КОЛЛЕКТИВ ИНДИВИДУАЛЬНЫХ ВЛАДЕЛЬЦЕВ", "КИВ", true);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОДСОБНОЕ ХОЗЯЙСТВО", null, false);
        t.addAbridge("ПОДСОБНОЕ Х-ВО");
        t.addAbridge("ПОДСОБНОЕ ХОЗ-ВО");
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("БИЗНЕС ЦЕНТР", "БЦ", true);
        t.addVariant("БІЗНЕС ЦЕНТР", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ТОРГОВЫЙ ЦЕНТР", "ТЦ", true);
        t.addVariant("ТОРГОВИЙ ЦЕНТР", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ТОРГОВО РАЗВЛЕКАТЕЛЬНЫЙ ЦЕНТР", "ТРЦ", true);
        t.addVariant("ТОРГОВО РОЗВАЖАЛЬНИЙ ЦЕНТР", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ТОРГОВО РАЗВЛЕКАТЕЛЬНЫЙ КОМПЛЕКС", "ТРК", true);
        t.addVariant("ТОРГОВО РОЗВАЖАЛЬНИЙ КОМПЛЕКС", false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("АЭРОПОРТ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("АЭРОДРОМ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ГИДРОУЗЕЛ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ВОДОЗАБОР", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОЛЕВОЙ СТАН", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ЧАБАНСКАЯ СТОЯНКА", null, false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("ВОЙСКОВАЯ ЧАСТЬ", "ВЧ", true);
        t.addVariant("ВОИНСКАЯ ЧАСТЬ", false);
        t.addAbridge("В/Ч");
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new1272("КВАРТИРНО ЭКСПЛУАТАЦИОННАЯ ЧАСТЬ", "КЭЧ", true);
        m_OrgOntology.add(t);
        m_OrgOntology.add(new com.pullenti.ner.core.Termin("КАРЬЕР", null, false));
        m_OrgOntology.add(new com.pullenti.ner.core.Termin("РУДНИК", null, false));
        m_OrgOntology.add(new com.pullenti.ner.core.Termin("ПРИИСК", null, false));
        t = new com.pullenti.ner.core.Termin("МЕСТОРОЖДЕНИЕ", null, false);
        t.addAbridge("МЕСТОРОЖД.");
        m_OrgOntology.add(t);
    }

    public static com.pullenti.ner.core.TerminCollection m_OrgOntology;

    public static OrgTypToken _new1269(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3, java.util.ArrayList<String> _arg4, com.pullenti.ner.MorphCollection _arg5) {
        OrgTypToken res = new OrgTypToken(_arg1, _arg2);
        res.isDoubt = _arg3;
        res.vals = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public OrgTypToken() {
        super();
    }
}
