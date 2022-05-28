/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class CityItemToken extends com.pullenti.ner.MetaToken {

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.IntOntologyCollection();
        m_OntologyEx = new com.pullenti.ner.core.IntOntologyCollection();
        M_CITYADJECTIVES = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("ГОРОД", null, false);
        t.addAbridge("ГОР.");
        t.addAbridge("Г.");
        t.tag = ItemType.NOUN;
        t.addVariant("ГОРОД ФЕДЕРАЛЬНОГО ЗНАЧЕНИЯ", false);
        t.addVariant("ГОРОД ГОРОДСКОЕ ПОСЕЛЕНИЕ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ГОРОДОК", null, false);
        t.tag = ItemType.NOUN;
        t.addVariant("ШАХТЕРСКИЙ ГОРОДОК", false);
        t.addVariant("ПРИМОРСКИЙ ГОРОДОК", false);
        t.addVariant("МАЛЕНЬКИЙ ГОРОДОК", false);
        t.addVariant("НЕБОЛЬШОЙ ГОРОДОК", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("CITY", null, false);
        t.tag = ItemType.NOUN;
        t.addVariant("TOWN", false);
        t.addVariant("CAPITAL", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МІСТО", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("МІС.");
        t.addAbridge("М.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1189("ГОРОД-ГЕРОЙ", "ГОРОД");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1210("МІСТО-ГЕРОЙ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1189("ГОРОД-КУРОРТ", "ГОРОД");
        t.addAbridge("Г.К.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1210("МІСТО-КУРОРТ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.addAbridge("М.К.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛО", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДЕРЕВНЯ", null, false);
        t.addAbridge("ДЕР.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛЕНИЕ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛО", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОРТ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОРТ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОСЕЛЕНИЕ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОСЕЛОК", null, false);
        t.addAbridge("ПОС.");
        t.tag = ItemType.NOUN;
        t.addVariant("ЖИЛОЙ ПОСЕЛОК", false);
        t.addVariant("КУРОРТНЫЙ ПОСЕЛОК", false);
        t.addVariant("ВАХТОВЫЙ ПОСЕЛОК", false);
        t.addVariant("ШАХТЕРСКИЙ ПОСЕЛОК", false);
        t.addVariant("ПОСЕЛОК СОВХОЗА", false);
        t.addVariant("ПОСЕЛОК КОЛХОЗА", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СЕЛ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОСЕЛОК ГОРОДСКОГО ТИПА", null, false);
        t.acronym = (t.acronymSmart = "ПГТ");
        t.addAbridge("ПГТ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛИЩЕ МІСЬКОГО ТИПУ", com.pullenti.morph.MorphLang.UA, false);
        t.acronym = (t.acronymSmart = "СМТ");
        t.addAbridge("СМТ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("РАБОЧИЙ ПОСЕЛОК", null, false);
        t.addAbridge("Р.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("РАБ.П.");
        t.addAbridge("Р.ПОС.");
        t.addAbridge("РАБ.ПОС.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("РОБОЧЕ СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("Р.С.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЫЙ ПОСЕЛОК", null, false);
        t.addAbridge("Д.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("ДАЧ.П.");
        t.addAbridge("Д.ПОС.");
        t.addAbridge("ДАЧ.ПОС.");
        t.addVariant("ЖИЛИЩНО ДАЧНЫЙ ПОСЕЛОК", false);
        t.addVariant("ДАЧНОЕ ПОСЕЛЕНИЕ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЕ СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("Д.С.");
        t.tag = ItemType.NOUN;
        t.addAbridge("ДАЧ.С.");
        t.addAbridge("Д.СЕЛ.");
        t.addAbridge("ДАЧ.СЕЛ.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ГОРОДСКОЕ ПОСЕЛЕНИЕ", null, false);
        t.addAbridge("Г.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("Г.ПОС.");
        t.addAbridge("ГОР.П.");
        t.addAbridge("ГОР.ПОС.");
        t.addAbridge("Г.О.Г.");
        t.addAbridge("ГОРОДСКОЙ ОКРУГ Г.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new126("ПОСЕЛКОВОЕ ПОСЕЛЕНИЕ", "ПОСЕЛОК", ItemType.NOUN);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МІСЬКЕ ПОСЕЛЕННЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛЬСКОЕ ПОСЕЛЕНИЕ", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("С.ПОС.");
        t.addAbridge("С.П.");
        t.addVariant("СЕЛЬСОВЕТ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СІЛЬСЬКЕ ПОСЕЛЕННЯ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("С.ПОС.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНИЦА", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("СТ-ЦА");
        t.addAbridge("СТАН-ЦА");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНИЦЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1189("СТОЛИЦА", "ГОРОД");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1210("СТОЛИЦЯ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНЦИЯ", null, false);
        t.addAbridge("СТАНЦ.");
        t.addAbridge("СТ.");
        t.addAbridge("СТАН.");
        t.tag = ItemType.NOUN;
        t.addVariant("ПЛАТФОРМА", false);
        t.addAbridge("ПЛАТФ.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНЦІЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ЖЕЛЕЗНОДОРОЖНАЯ СТАНЦИЯ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ЗАЛІЗНИЧНА СТАНЦІЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("НАСЕЛЕННЫЙ ПУНКТ", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("Н.П.");
        t.addAbridge("Б.Н.П.");
        t.addAbridge("НП");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("НАСЕЛЕНИЙ ПУНКТ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("НП");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1189("РАЙОННЫЙ ЦЕНТР", "НАСЕЛЕННЫЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1210("РАЙОННИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, "НАСЕЛЕНИЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1189("ОБЛАСТНОЙ ЦЕНТР", "НАСЕЛЕННЫЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1210("ОБЛАСНИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, "НАСЕЛЕНИЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОЧИНОК", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ЗАИМКА", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ХУТОР", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("АУЛ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ААЛ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("АРБАН", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ВЫСЕЛКИ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МЕСТЕЧКО", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("УРОЧИЩЕ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("УСАДЬБА", null, false);
        t.tag = ItemType.NOUN;
        t.addVariant("ЦЕНТРАЛЬНАЯ УСАДЬБА", false);
        t.addAbridge("ЦЕНТР.УС.");
        t.addAbridge("ЦЕНТР.УСАДЬБА");
        t.addAbridge("Ц/У");
        t.addAbridge("УС-БА");
        t.addAbridge("ЦЕНТР.УС-БА");
        m_Ontology.add(t);
        for (String s : new String[] {"ЖИТЕЛЬ", "МЭР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, ItemType.MISC));
        }
        for (String s : new String[] {"ЖИТЕЛЬ", "МЕР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, ItemType.MISC));
        }
        t = com.pullenti.ner.core.Termin._new100("АДМИНИСТРАЦИЯ", ItemType.MISC);
        t.addAbridge("АДМ.");
        m_Ontology.add(t);
        m_StdAdjectives = new com.pullenti.ner.core.IntOntologyCollection();
        t = new com.pullenti.ner.core.Termin("ВЕЛИКИЙ", null, false);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("ВЕЛИК.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("БОЛЬШОЙ", null, false);
        t.addAbridge("БОЛ.");
        t.addAbridge("БОЛЬШ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("МАЛЫЙ", null, false);
        t.addAbridge("МАЛ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕРХНИЙ", null, false);
        t.addAbridge("ВЕР.");
        t.addAbridge("ВЕРХ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НИЖНИЙ", null, false);
        t.addAbridge("НИЖ.");
        t.addAbridge("НИЖН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СРЕДНИЙ", null, false);
        t.addAbridge("СРЕД.");
        t.addAbridge("СРЕДН.");
        t.addAbridge("СР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СТАРЫЙ", null, false);
        t.addAbridge("СТ.");
        t.addAbridge("СТАР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НОВЫЙ", null, false);
        t.addAbridge("НОВ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕЛИКИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("ВЕЛИК.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("МАЛИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("МАЛ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕРХНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("ВЕР.");
        t.addAbridge("ВЕРХ.");
        t.addAbridge("ВЕРХН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НИЖНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("НИЖ.");
        t.addAbridge("НИЖН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СЕРЕДНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СЕР.");
        t.addAbridge("СЕРЕД.");
        t.addAbridge("СЕРЕДН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СТАРИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СТ.");
        t.addAbridge("СТАР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НОВИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("НОВ.");
        m_StdAdjectives.add(t);
        m_StdAdjectives.add(new com.pullenti.ner.core.Termin("SAN", null, false));
        m_StdAdjectives.add(new com.pullenti.ner.core.Termin("LOS", null, false));
        m_SpecNames = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"ГОРОДОК ПИСАТЕЛЕЙ ПЕРЕДЕЛКИНО", "ЦЕНТРАЛЬНАЯ УСАДЬБА", "ГОРКИ ЛЕНИНСКИЕ"}) {
            m_SpecNames.add(com.pullenti.ner.core.Termin._new1223(s, true));
        }
        m_SpecAbbrs = new com.pullenti.ner.core.TerminCollection();
        t = new com.pullenti.ner.core.Termin("ЛЕСНИЧЕСТВО", null, false);
        t.addAbridge("ЛЕС-ВО");
        t.addAbridge("ЛЕСН-ВО");
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("ЛЕСОПАРК", null, false);
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("ЛЕСОУЧАСТОК", null, false);
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("РУДНИК", null, false);
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("ПРИИСК", null, false);
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("МЕСТОРОЖДЕНИЯ", null, false);
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("ЗАПОВЕДНИК", null, false);
        t.addAbridge("ЗАП-К");
        m_SpecAbbrs.add(t);
        t = new com.pullenti.ner.core.Termin("СОВХОЗ", null, false);
        t.addAbridge("С/Х");
        t.addAbridge("СВХ");
        m_SpecAbbrs.add(t);
        byte[] dat = com.pullenti.ner.address.internal.ResourceHelper.getBytes("c.dat");
        if (dat == null) 
            throw new Exception("Not found resource file c.dat in Analyzer.Location");
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(MiscLocationHelper.deflate(dat))) {
            tmp.setPosition(0L);
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.load(tmp);
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "bigcity")) 
                    loadBigCity(x);
                else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "city")) 
                    loadCity(x);
            }
        }
    }

    private static void loadCity(org.w3c.dom.Node xml) {
        com.pullenti.ner.core.IntOntologyItem ci = new com.pullenti.ner.core.IntOntologyItem(null);
        com.pullenti.ner.core.IntOntologyCollection onto = m_OntologyEx;
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
        if (com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l") != null && com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l").getTextContent(), "ua")) 
            lang = com.pullenti.morph.MorphLang.UA;
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, null, false);
                t.initByNormalText(v, lang);
                ci.termins.add(t);
                t.addStdAbridges();
                if (v.startsWith("SAINT ")) 
                    t.addAbridge("ST. " + v.substring(6));
                else if (v.startsWith("SAITNE ")) 
                    t.addAbridge("STE. " + v.substring(7));
            }
        }
        onto.addItem(ci);
    }

    private static void loadBigCity(org.w3c.dom.Node xml) {
        com.pullenti.ner.core.IntOntologyItem ci = new com.pullenti.ner.core.IntOntologyItem(null);
        ci.miscAttr = ci;
        String adj = null;
        com.pullenti.ner.core.IntOntologyCollection onto = m_OntologyEx;
        com.pullenti.ner.core.TerminCollection cityAdj = M_CITYADJECTIVES;
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
        if (com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l") != null) {
            String la = com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l").getTextContent();
            if (com.pullenti.unisharp.Utils.stringsEq(la, "ua")) 
                lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.unisharp.Utils.stringsEq(la, "en")) 
                lang = com.pullenti.morph.MorphLang.EN;
        }
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(v)) 
                    continue;
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, null, false);
                t.initByNormalText(v, lang);
                ci.termins.add(t);
                if (com.pullenti.unisharp.Utils.stringsEq(v, "САНКТ-ПЕТЕРБУРГ")) {
                    if (m_StPeterburg == null) 
                        m_StPeterburg = ci;
                    t.acronym = "СПБ";
                    t.addAbridge("С.ПЕТЕРБУРГ");
                    t.addAbridge("СП-Б");
                    ci.termins.add(new com.pullenti.ner.core.Termin("ПЕТЕРБУРГ", lang, false));
                }
                else if (v.startsWith("SAINT ")) 
                    t.addAbridge("ST. " + v.substring(6));
                else if (v.startsWith("SAITNE ")) 
                    t.addAbridge("STE. " + v.substring(7));
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "a")) 
                adj = x.getTextContent();
        }
        onto.addItem(ci);
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(adj)) {
            com.pullenti.ner.core.Termin at = new com.pullenti.ner.core.Termin(null, null, false);
            at.initByNormalText(adj, lang);
            at.tag = ci;
            cityAdj.add(at);
            boolean spb = com.pullenti.unisharp.Utils.stringsEq(adj, "САНКТ-ПЕТЕРБУРГСКИЙ") || com.pullenti.unisharp.Utils.stringsEq(adj, "САНКТ-ПЕТЕРБУРЗЬКИЙ");
            if (spb) 
                cityAdj.add(com.pullenti.ner.core.Termin._new372(adj.substring(6), lang, ci));
        }
    }

    private static com.pullenti.ner.core.IntOntologyCollection m_Ontology;

    private static com.pullenti.ner.core.IntOntologyCollection m_OntologyEx;

    private static com.pullenti.ner.core.IntOntologyItem m_StPeterburg;

    public static com.pullenti.ner.core.TerminCollection M_CITYADJECTIVES;

    private static com.pullenti.ner.core.IntOntologyCollection m_StdAdjectives;

    private static com.pullenti.ner.core.TerminCollection m_SpecNames;

    private static com.pullenti.ner.core.TerminCollection m_SpecAbbrs;

    public static com.pullenti.ner.core.IntOntologyToken checkOntoItem(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = m_OntologyEx.tryAttach(t, null, false);
        if (li != null) {
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null) 
                    return nt;
            }
        }
        return null;
    }

    public static com.pullenti.ner.core.IntOntologyToken checkKeyword(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = m_Ontology.tryAttach(t, null, false);
        if (li != null) {
            for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item == null) 
                    return nt;
            }
        }
        return null;
    }

    public static java.util.ArrayList<CityItemToken> tryParseList(com.pullenti.ner.Token t, int maxCount, GeoAnalyzerData ad) {
        CityItemToken ci = CityItemToken.tryParse(t, null, false, ad);
        if (ci == null) {
            if (t == null) 
                return null;
            if (((t instanceof com.pullenti.ner.TextToken) && t.isValue("МУНИЦИПАЛЬНЫЙ", null) && t.getNext() != null) && t.getNext().isValue("ОБРАЗОВАНИЕ", null)) {
                com.pullenti.ner.Token t1 = t.getNext().getNext();
                boolean br = false;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                    br = true;
                    t1 = t1.getNext();
                }
                java.util.ArrayList<CityItemToken> lii = tryParseList(t1, maxCount, null);
                if (lii != null && lii.get(0).typ == ItemType.NOUN) {
                    lii.get(0).setBeginToken(t);
                    lii.get(0).doubtful = false;
                    if (br && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(lii.get(lii.size() - 1).getEndToken().getNext(), false, null, false)) 
                        lii.get(lii.size() - 1).setEndToken(lii.get(lii.size() - 1).getEndToken().getNext());
                    return lii;
                }
            }
            return null;
        }
        if (ci.chars.isLatinLetter() && ci.typ == ItemType.NOUN && !t.chars.isAllLower()) 
            return null;
        java.util.ArrayList<CityItemToken> li = new java.util.ArrayList<CityItemToken>();
        li.add(ci);
        for (t = ci.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isNewlineBefore()) {
                if (t.getNewlinesBeforeCount() > 1) 
                    break;
                if (li.size() == 1 && li.get(0).typ == ItemType.NOUN) {
                }
                else 
                    break;
            }
            CityItemToken ci0 = CityItemToken.tryParse(t, ci, false, ad);
            if (ci0 == null) {
                if (t.isNewlineBefore()) 
                    break;
                if (ci.typ == ItemType.NOUN && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if ((br != null && (br.getLengthChar() < 50) && t.getNext().chars.isCyrillicLetter()) && !t.getNext().chars.isAllLower()) {
                        ci0 = _new1225(br.getBeginToken(), br.getEndToken(), ItemType.PROPERNAME);
                        com.pullenti.ner.Token tt = br.getEndToken().getPrevious();
                        String num = null;
                        if (tt instanceof com.pullenti.ner.NumberToken) {
                            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString();
                            tt = tt.getPrevious();
                            if (tt != null && tt.isHiphen()) 
                                tt = tt.getPrevious();
                        }
                        ci0.value = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (tt != br.getBeginToken().getNext()) 
                            ci0.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (com.pullenti.unisharp.Utils.isNullOrEmpty(ci0.value)) 
                            ci0 = null;
                        else if (num != null) {
                            ci0.value = (ci0.value + "-" + num);
                            if (ci0.altValue != null) 
                                ci0.altValue = (ci0.altValue + "-" + num);
                        }
                    }
                }
                if ((ci0 == null && ((ci.typ == ItemType.PROPERNAME || ci.typ == ItemType.CITY)) && t.isComma()) && li.get(0) == ci) {
                    com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(t.getNext());
                    if (npt != null) {
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.getEndChar() <= npt.getEndChar(); tt = tt.getNext()) {
                            CityItemToken ci00 = CityItemToken.tryParse(tt, ci, false, null);
                            if (ci00 != null && ci00.typ == ItemType.NOUN) {
                                CityItemToken ci01 = CityItemToken.tryParse(ci00.getEndToken().getNext(), ci, false, null);
                                if (ci01 == null) {
                                    ci0 = ci00;
                                    ci0.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(t.getNext(), ci00.getEndToken(), (t.kit.baseLanguage.isEn() ? com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES : com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE)).toLowerCase();
                                    break;
                                }
                            }
                            if (!tt.chars.isAllLower()) 
                                break;
                        }
                    }
                }
                if (ci0 == null) 
                    break;
            }
            if ((ci0.typ == ItemType.NOUN && ci0.value != null && com.pullenti.morph.LanguageHelper.endsWith(ci0.value, "УСАДЬБА")) && ci.typ == ItemType.NOUN) {
                ci.doubtful = false;
                t = ci.setEndToken(ci0.getEndToken());
                continue;
            }
            if (ci0.typ == ItemType.NOUN && ci.typ == ItemType.MISC && com.pullenti.unisharp.Utils.stringsEq(ci.value, "АДМИНИСТРАЦИЯ")) 
                ci0.doubtful = false;
            if (ci.mergeWithNext(ci0)) {
                t = ci.getEndToken();
                continue;
            }
            ci = ci0;
            li.add(ci);
            t = ci.getEndToken();
            if (maxCount > 0 && li.size() >= maxCount) 
                break;
        }
        if (li.size() > 1 && com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "СОВЕТ")) 
            return null;
        if (li.size() > 2 && li.get(0).typ == ItemType.NOUN && li.get(1).typ == ItemType.NOUN) {
            if (li.get(0).mergeWithNext(li.get(1))) 
                li.remove(1);
        }
        if (li.size() > 2 && li.get(0).isNewlineAfter()) 
            for(int jjj = 1 + li.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) li.remove(jjj);
        if (!li.get(0).geoObjectBefore) 
            li.get(0).geoObjectBefore = MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken(), false);
        if (!li.get(li.size() - 1).geoObjectAfter) 
            li.get(li.size() - 1).geoObjectAfter = MiscLocationHelper.checkGeoObjectAfter(li.get(li.size() - 1).getEndToken(), true, false);
        if ((li.size() == 2 && li.get(0).typ == ItemType.NOUN && li.get(1).typ == ItemType.NOUN) && ((li.get(0).geoObjectBefore || li.get(1).geoObjectAfter))) {
            if (li.get(0).chars.isCapitalUpper() && li.get(1).chars.isAllLower()) 
                li.get(0).typ = ItemType.PROPERNAME;
            else if (li.get(1).chars.isCapitalUpper() && li.get(0).chars.isAllLower()) 
                li.get(1).typ = ItemType.PROPERNAME;
        }
        return li;
    }

    public CityItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public ItemType typ = ItemType.PROPERNAME;

    public String value;

    public String altValue;

    public com.pullenti.ner.core.IntOntologyItem ontoItem;

    public boolean doubtful;

    public boolean geoObjectBefore;

    public boolean geoObjectAfter;

    public com.pullenti.ner.geo.GeoReferent higherGeo;

    public com.pullenti.ner.ReferentToken orgRef;

    public Condition cond;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (cond != null) 
            res.append("[").append(cond.toString()).append("] ");
        res.append(typ.toString());
        if (value != null) 
            res.append(" ").append(value);
        if (ontoItem != null) 
            res.append(" ").append(ontoItem.toString());
        if (doubtful) 
            res.append(" (?)");
        if (orgRef != null) 
            res.append(" (Org: ").append(orgRef.referent).append(")");
        if (geoObjectBefore) 
            res.append(" GeoBefore");
        if (geoObjectAfter) 
            res.append(" GeoAfter");
        return res.toString();
    }

    public boolean mergeWithNext(CityItemToken ne) {
        if (typ != ItemType.NOUN || ne.typ != ItemType.NOUN) 
            return false;
        boolean ok = false;
        if (com.pullenti.unisharp.Utils.stringsEq(value, "ГОРОДСКОЕ ПОСЕЛЕНИЕ") && com.pullenti.unisharp.Utils.stringsEq(ne.value, "ГОРОД")) 
            ok = true;
        if (!ok) 
            return false;
        setEndToken(ne.getEndToken());
        doubtful = false;
        return true;
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        GeoAnalyzerData ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t0);
        if (ad == null) 
            return;
        ad.cRegime = false;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            CityItemToken cit = tryParse(t, null, false, ad);
            if (cit != null) {
                if (d == null) 
                    d = new GeoTokenData(t);
                d.cit = cit;
            }
        }
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
            if (d == null || d.cit == null || d.cit.typ != ItemType.NOUN) 
                continue;
            com.pullenti.ner.Token tt = d.cit.getEndToken().getNext();
            if (tt == null) 
                continue;
            GeoTokenData dd = (GeoTokenData)com.pullenti.unisharp.Utils.cast(tt.tag, GeoTokenData.class);
            if (dd != null && dd.cit != null) 
                continue;
            CityItemToken cit = tryParse(tt, d.cit, false, ad);
            if (cit == null) 
                continue;
            if (dd == null) 
                dd = new GeoTokenData(tt);
            dd.cit = cit;
        }
        ad.cRegime = true;
    }

    public static CityItemToken tryParse(com.pullenti.ner.Token t, CityItemToken prev, boolean dontNormalize, GeoAnalyzerData ad) {
        if (t == null) 
            return null;
        if (ad == null) 
            ad = com.pullenti.ner.geo.GeoAnalyzer.getData(t);
        if (ad == null) 
            return null;
        GeoTokenData d = (GeoTokenData)com.pullenti.unisharp.Utils.cast(t.tag, GeoTokenData.class);
        if (t.isValue("НП", null)) {
        }
        if (d != null && d.noGeo) 
            return null;
        if (SPEEDREGIME && ((ad.cRegime || ad.allRegime)) && !dontNormalize) {
            if (d == null) 
                return null;
            if (d.cit == null) 
                return null;
            if (d.cit.cond != null) {
                if (ad.checkRegime) 
                    return null;
                ad.checkRegime = true;
                boolean b = d.cit.cond.check();
                ad.checkRegime = false;
                if (!b) 
                    return null;
            }
            return d.cit;
        }
        if (ad.cLevel > 1) 
            return null;
        ad.cLevel++;
        CityItemToken res = _tryParseInt(t, prev, dontNormalize, ad);
        ad.cLevel--;
        if (res != null && res.typ == ItemType.NOUN && (res.getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.core.NounPhraseToken nn = MiscLocationHelper.tryParseNpt(res.getEndToken().getNext());
            if (nn != null && ((nn.getEndToken().isValue("ЗНАЧЕНИЕ", "ЗНАЧЕННЯ") || nn.getEndToken().isValue("ТИП", null) || nn.getEndToken().isValue("ХОЗЯЙСТВО", "ХАЗЯЙСТВО")))) {
                if (OrgItemToken.tryParse(res.getEndToken().getNext(), ad) == null) 
                    res.setEndToken(nn.getEndToken());
            }
        }
        if (((res != null && res.typ == ItemType.PROPERNAME && res.value != null) && !res.doubtful && res.getBeginToken() == res.getEndToken()) && res.value.length() > 4) {
            if (com.pullenti.morph.LanguageHelper.endsWithEx(res.value, "ГРАД", "ГОРОД", null, null)) {
                res.altValue = null;
                res.typ = ItemType.CITY;
            }
            else if (com.pullenti.morph.LanguageHelper.endsWithEx(res.value, "СК", "ИНО", "ПОЛЬ", null) || com.pullenti.morph.LanguageHelper.endsWithEx(res.value, "ВЛЬ", "АС", "ЕС", null)) {
                java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> sits = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(res.getEndToken().getNext(), 3, ad);
                if (sits != null) {
                    if (sits.size() == 1 && sits.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                        return res;
                    if (sits.size() == 2 && sits.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NUMBER && sits.get(1).typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                        return res;
                }
                com.pullenti.morph.MorphClass mc = res.getEndToken().getMorphClassInDictionary();
                if (mc.isProperGeo() || mc.isUndefined()) {
                    res.altValue = null;
                    res.typ = ItemType.CITY;
                }
            }
            else if (com.pullenti.morph.LanguageHelper.endsWithEx(res.value, "АНЬ", "TOWN", null, null) || res.value.startsWith("SAN")) 
                res.typ = ItemType.CITY;
            else if (res.getEndToken() instanceof com.pullenti.ner.TextToken) {
                String lem = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class)).lemma;
                if (com.pullenti.morph.LanguageHelper.endsWithEx(lem, "ГРАД", "ГОРОД", "СК", null) || com.pullenti.morph.LanguageHelper.endsWithEx(lem, "АНЬ", "ПОЛЬ", null, null)) {
                    res.altValue = res.value;
                    res.value = lem;
                    int ii = res.altValue.indexOf('-');
                    if (ii >= 0) 
                        res.value = res.altValue.substring(0, 0 + ii + 1) + lem;
                    if (!com.pullenti.morph.LanguageHelper.endsWith(res.value, "АНЬ")) 
                        res.altValue = null;
                }
            }
        }
        return res;
    }

    private static CityItemToken _tryParseInt(com.pullenti.ner.Token t, CityItemToken prev, boolean dontNormalize, GeoAnalyzerData ad) {
        if (t == null || (((t instanceof com.pullenti.ner.TextToken) && !t.chars.isLetter()))) 
            return null;
        CityItemToken res = _TryParse(t, prev, dontNormalize, ad);
        if ((prev == null && t.chars.isCyrillicLetter() && t.chars.isAllUpper()) && t.getLengthChar() == 2) {
            if (t.isValue("ТА", null)) {
                res = _TryParse(t.getNext(), prev, dontNormalize, ad);
                if (res != null) {
                    if (res.typ == ItemType.NOUN) {
                        res.setBeginToken(t);
                        res.doubtful = false;
                    }
                    else 
                        res = null;
                }
            }
        }
        if (prev != null && prev.typ == ItemType.NOUN && ((com.pullenti.unisharp.Utils.stringsNe(prev.value, "ГОРОД") && com.pullenti.unisharp.Utils.stringsNe(prev.value, "МІСТО")))) {
            if (res == null) {
                OrgItemToken det = OrgItemToken.tryParse(t, null);
                if (det != null) {
                    int cou = 0;
                    for (com.pullenti.ner.Token ttt = det.getBeginToken(); ttt != null && ttt.getEndChar() <= det.getEndChar(); ttt = ttt.getNext()) {
                        if (ttt.chars.isLetter()) 
                            cou++;
                    }
                    if (cou < 6) {
                        CityItemToken re = _new1225(det.getBeginToken(), det.getEndToken(), ItemType.PROPERNAME);
                        if (com.pullenti.unisharp.Utils.stringsEq(det.referent.getTypeName(), "ORGANIZATION")) 
                            re.orgRef = det;
                        else {
                            re.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(det, com.pullenti.ner.core.GetTextAttr.NO);
                            re.altValue = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(det, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                        }
                        return re;
                    }
                }
            }
        }
        if (res != null && res.typ == ItemType.NOUN && (res.getWhitespacesAfterCount() < 3)) {
            com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(res.getEndToken().getNext());
            if (npt != null) {
                if (npt.getEndToken().isValue("ПОДЧИНЕНИЕ", "ПІДПОРЯДКУВАННЯ")) 
                    res.setEndToken(npt.getEndToken());
            }
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "НАСЕЛЕННЫЙ ПУНКТ")) {
                CityItemToken _next = _TryParse(res.getEndToken().getNext(), prev, dontNormalize, ad);
                if (_next != null && _next.typ == ItemType.NOUN) {
                    _next.setBeginToken(res.getBeginToken());
                    return _next;
                }
            }
        }
        if (res != null && t.chars.isAllUpper() && res.typ == ItemType.PROPERNAME) {
            com.pullenti.ner.Token tt = t.getPrevious();
            if (tt != null && tt.isComma()) 
                tt = tt.getPrevious();
            com.pullenti.ner.geo.GeoReferent geoPrev = null;
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                geoPrev = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geoPrev != null && ((geoPrev.isRegion() || geoPrev.isCity()))) {
                OrgItemToken det = OrgItemToken.tryParse(t, null);
                if (det != null) 
                    res = null;
            }
        }
        if (res != null && res.typ == ItemType.PROPERNAME) {
            if ((t.isValue("ДУМА", "РАДА") || t.isValue("ГЛАВА", "ГОЛОВА") || t.isValue("АДМИНИСТРАЦИЯ", "АДМІНІСТРАЦІЯ")) || t.isValue("МЭР", "МЕР") || t.isValue("ПРЕДСЕДАТЕЛЬ", "ГОЛОВА")) 
                return null;
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.value, "НАСЕЛЕННЫЙ ПУНКТ") && (res.getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.address.internal.StreetItemToken s = com.pullenti.ner.address.internal.StreetItemToken.tryParse(res.getEndToken().getNext(), null, false, null);
            if (s != null && s.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN && com.pullenti.unisharp.Utils.stringsEq(s.termin.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ")) 
                res.setEndToken(s.getEndToken());
        }
        com.pullenti.ner.geo.GeoReferent geoAfter = null;
        if (res == null) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    res = _TryParse(t.getNext(), null, false, ad);
                    if (res != null && ((res.typ == ItemType.PROPERNAME || res.typ == ItemType.CITY))) {
                        res.setBeginToken(t);
                        res.typ = ItemType.PROPERNAME;
                        res.setEndToken(br.getEndToken());
                        if (res.getEndToken().getNext() != br.getEndToken()) {
                            res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                            res.altValue = null;
                        }
                        return res;
                    }
                }
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                String txt = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(txt, "ИМ") || com.pullenti.unisharp.Utils.stringsEq(txt, "ИМЕНИ")) {
                    com.pullenti.ner.Token t1 = t.getNext();
                    if (t1 != null && t1.isChar('.')) 
                        t1 = t1.getNext();
                    res = _TryParse(t1, null, false, ad);
                    if (res != null && ((((res.typ == ItemType.CITY && res.doubtful)) || res.typ == ItemType.PROPERNAME))) {
                        res.setBeginToken(t);
                        res.setMorph(new com.pullenti.ner.MorphCollection(null));
                        return res;
                    }
                }
                if (t.chars.isCyrillicLetter() && t.getLengthChar() == 1 && t.chars.isAllUpper()) {
                    if ((t.getNext() != null && !t.isWhitespaceAfter() && ((t.getNext().isHiphen() || t.getNext().isChar('.')))) && (t.getNext().getWhitespacesAfterCount() < 2)) {
                        if (prev != null && prev.typ == ItemType.NOUN && (((!prev.doubtful || prev.geoObjectBefore || MiscLocationHelper.checkGeoObjectBefore(prev.getBeginToken(), false)) || MiscLocationHelper.checkGeoObjectBeforeBrief(prev.getBeginToken(), ad)))) {
                            CityItemToken res1 = _TryParse(t.getNext().getNext(), null, false, ad);
                            if (res1 != null && ((res1.typ == ItemType.PROPERNAME || res1.typ == ItemType.CITY))) {
                                java.util.ArrayList<String> adjs = MiscLocationHelper.getStdAdjFullStr(txt, res1.getMorph().getGender(), res1.getMorph().getNumber(), true);
                                if (adjs == null && prev != null && prev.typ == ItemType.NOUN) 
                                    adjs = MiscLocationHelper.getStdAdjFullStr(txt, prev.getMorph().getGender(), com.pullenti.morph.MorphNumber.UNDEFINED, true);
                                if (adjs == null) 
                                    adjs = MiscLocationHelper.getStdAdjFullStr(txt, res1.getMorph().getGender(), res1.getMorph().getNumber(), false);
                                if (adjs != null) {
                                    if (res1.value == null) 
                                        res1.value = res1.getSourceText().toUpperCase();
                                    if (res1.altValue != null) 
                                        res1.altValue = (adjs.get(0) + " " + res1.altValue);
                                    else if (adjs.size() > 1) 
                                        res1.altValue = (adjs.get(1) + " " + res1.value);
                                    res1.value = (adjs.get(0) + " " + res1.value);
                                    res1.setBeginToken(t);
                                    res1.typ = ItemType.PROPERNAME;
                                    return res1;
                                }
                            }
                        }
                    }
                }
            }
            com.pullenti.ner.Token tt = (prev == null ? t.getPrevious() : prev.getBeginToken().getPrevious());
            while (tt != null && tt.isCharOf(",.")) {
                tt = tt.getPrevious();
            }
            com.pullenti.ner.geo.GeoReferent geoPrev = null;
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                geoPrev = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            Condition _cond = null;
            com.pullenti.ner.Token tt0 = t;
            boolean ooo = false;
            boolean hasGeoAfter = false;
            if (geoPrev != null) 
                ooo = true;
            else if (MiscLocationHelper.checkNearBefore(t, ad) != null) 
                ooo = true;
            else if (MiscLocationHelper.checkGeoObjectBefore(t, false)) 
                ooo = true;
            else if (t.chars.isLetter()) {
                tt = t.getNext();
                if (tt != null && tt.isChar('.')) 
                    tt = tt.getNext();
                if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isAllLower()) {
                    if (MiscLocationHelper.checkGeoObjectAfterBrief(tt, ad)) 
                        ooo = (hasGeoAfter = true);
                    else if (MiscLocationHelper.checkGeoObjectAfter(tt, true, false)) 
                        ooo = (hasGeoAfter = true);
                    else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(tt.getNext(), false)) 
                        ooo = true;
                    else if (ad.cLevel == 0) {
                        CityItemToken cit2 = tryParse(tt, null, false, ad);
                        if (cit2 != null && cit2.getBeginToken() != cit2.getEndToken() && ((cit2.typ == ItemType.PROPERNAME || cit2.typ == ItemType.CITY))) {
                            if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(cit2.getEndToken().getNext(), false)) 
                                ooo = true;
                        }
                        if (cit2 != null && cit2.typ == ItemType.CITY && tt.getPrevious().isChar('.')) {
                            if (cit2.isWhitespaceAfter() || ((cit2.getEndToken().getNext() != null && cit2.getEndToken().getNext().getLengthChar() == 1))) {
                                ooo = true;
                                if (cit2.ontoItem != null) 
                                    geoAfter = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(cit2.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class);
                            }
                        }
                    }
                }
            }
            if ((ad != null && !ooo && !ad.cRegime) && SPEEDREGIME) {
                if (_cond == null) 
                    _cond = new Condition();
                _cond.geoBeforeToken = t;
                ooo = true;
            }
            if (ooo) {
                tt = t;
                for (com.pullenti.ner.Token ttt = tt; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isCharOf(",.")) {
                        tt = ttt.getNext();
                        continue;
                    }
                    if (ttt.isNewlineBefore()) 
                        break;
                    com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryParsePureItem(ttt, null, ad);
                    if (det != null && det.getTyp() == com.pullenti.ner.address.internal.AddressItemType.DETAIL) {
                        ttt = det.getEndToken();
                        tt = (tt0 = det.getEndToken().getNext());
                        continue;
                    }
                    OrgItemToken _org = OrgItemToken.tryParse(ttt, null);
                    if (_org != null && _org.isGsk) {
                        ttt = _org.getEndToken();
                        tt0 = (tt = _org.getEndToken().getNext());
                        continue;
                    }
                    com.pullenti.ner.address.internal.AddressItemToken ait = com.pullenti.ner.address.internal.AddressItemToken.tryParsePureItem(ttt, null, null);
                    if (ait != null && ait.getTyp() == com.pullenti.ner.address.internal.AddressItemType.PLOT) {
                        ttt = ait.getEndToken();
                        tt0 = (tt = ait.getEndToken().getNext());
                        continue;
                    }
                    break;
                }
                if (tt instanceof com.pullenti.ner.TextToken) {
                    if (tt0.isComma() && tt0.getNext() != null) 
                        tt0 = tt0.getNext();
                    String txt = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                    if ((((com.pullenti.unisharp.Utils.stringsEq(txt, "Д") || com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C")) || com.pullenti.unisharp.Utils.stringsEq(txt, "П") || com.pullenti.unisharp.Utils.stringsEq(txt, "Х"))) && ((tt.chars.isAllLower() || ((tt.getNext() != null && tt.getNext().isCharOf(".,")))))) {
                        com.pullenti.ner.Token tt1 = tt;
                        if (tt1.getNext() != null && tt1.getNext().isCharOf(",.")) 
                            tt1 = tt1.getNext();
                        else if (com.pullenti.unisharp.Utils.stringsEq(txt, "С") && tt1.getNext() != null && ((tt1.getNext().getMorph().getCase().isInstrumental() || tt1.getNext().getMorph().getCase().isGenitive()))) {
                            if (!(tt1.getNext() instanceof com.pullenti.ner.TextToken)) 
                                return null;
                            if (!tt.chars.isAllLower() || !tt1.getNext().chars.isCapitalUpper()) 
                                return null;
                            if (tt1.getNext().isNewlineAfter()) {
                            }
                            else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(tt1.getNext().getNext(), false)) {
                            }
                            else 
                                return null;
                        }
                        com.pullenti.ner.Token tt2 = tt1.getNext();
                        if ((tt2 != null && tt2.getLengthChar() == 1 && tt2.chars.isCyrillicLetter()) && tt2.chars.isAllUpper()) {
                            if (tt2.getNext() != null && ((tt2.getNext().isChar('.') || tt2.getNext().isHiphen())) && !tt2.isWhitespaceAfter()) 
                                tt2 = tt2.getNext().getNext();
                        }
                        else 
                            while (tt2 != null && tt2.isComma()) {
                                tt2 = tt2.getNext();
                            }
                        boolean ok = false;
                        if (com.pullenti.unisharp.Utils.stringsEq(txt, "Д") && (tt2 instanceof com.pullenti.ner.NumberToken) && !tt2.isNewlineBefore()) 
                            ok = false;
                        else if (((com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C"))) && (tt2 instanceof com.pullenti.ner.TextToken) && ((tt2.isValue("О", null) || tt2.isValue("O", null)))) 
                            ok = false;
                        else if (tt2 == null || tt2.isValue("ДОМ", null)) 
                            ok = true;
                        else if (tt2.isNewlineBefore() && tt2.getPrevious().isComma()) 
                            ok = true;
                        else if (tt2.chars.isCapitalUpper() && (tt2.getWhitespacesBeforeCount() < 2)) 
                            ok = tt.chars.isAllLower();
                        else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(tt2, false)) 
                            ok = true;
                        else if (tt2.chars.isAllUpper() && (tt2.getWhitespacesBeforeCount() < 2)) {
                            ok = true;
                            if (tt.chars.isAllUpper()) {
                                com.pullenti.ner.ReferentToken rtt = tt.kit.processReferent("PERSON", tt, null);
                                if (rtt != null) {
                                    ok = false;
                                    com.pullenti.ner.Token ttt2 = rtt.getEndToken().getNext();
                                    if (ttt2 != null && ttt2.isComma()) 
                                        ttt2 = ttt2.getNext();
                                    if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(ttt2, false, false) || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(ttt2, false)) 
                                        ok = true;
                                }
                                else if (tt.getPrevious() != null && tt.getPrevious().isChar('.')) 
                                    ok = false;
                            }
                            else if (tt1 == tt) 
                                ok = false;
                            if (!ok && tt1.getNext() != null) {
                                com.pullenti.ner.Token ttt2 = tt1.getNext().getNext();
                                if (ttt2 != null && ttt2.isComma()) 
                                    ttt2 = ttt2.getNext();
                                if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(ttt2, false, false) || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(ttt2, false)) {
                                    if (OrgItemToken.tryParse(tt1.getNext(), null) != null) {
                                    }
                                    else 
                                        ok = true;
                                }
                            }
                        }
                        else if (prev != null && prev.typ == ItemType.PROPERNAME && (tt.getWhitespacesBeforeCount() < 2)) {
                            if (MiscLocationHelper.checkGeoObjectBefore(prev.getBeginToken().getPrevious(), false)) 
                                ok = true;
                            if (com.pullenti.unisharp.Utils.stringsEq(txt, "П") && tt.getNext() != null && ((tt.getNext().isHiphen() || tt.getNext().isCharOf("\\/")))) {
                                com.pullenti.ner.address.internal.StreetItemToken sit = com.pullenti.ner.address.internal.StreetItemToken.tryParse(tt, null, false, null);
                                if (sit != null && sit.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                                    ok = false;
                            }
                        }
                        else if (prev == null) {
                            if (MiscLocationHelper.checkGeoObjectBefore(tt.getPrevious(), false)) {
                                if (tt1.isNewlineAfter()) {
                                }
                                else 
                                    ok = true;
                            }
                            else if (geoAfter != null || hasGeoAfter) 
                                ok = true;
                        }
                        if (tt.getPrevious() != null && tt.getPrevious().isHiphen() && !tt.isWhitespaceBefore()) {
                            if (tt.getNext() != null && tt.getNext().isChar('.')) {
                            }
                            else 
                                ok = false;
                        }
                        if (ok) {
                            int ii = 0;
                            for (com.pullenti.ner.Token ttt = t.getPrevious(); ttt != null && (ii < 4); ttt = ttt.getPrevious(),ii++) {
                                OrgItemToken oo = OrgItemToken.tryParse(ttt, null);
                                if (oo != null && oo.getEndChar() > tt.getEndChar()) 
                                    ok = false;
                            }
                        }
                        if (ok) {
                            res = _new1225(tt0, tt1, ItemType.NOUN);
                            if (tt1.isComma()) 
                                res.setEndToken(tt1.getPrevious());
                            res.value = (com.pullenti.unisharp.Utils.stringsEq(txt, "Д") ? "ДЕРЕВНЯ" : (com.pullenti.unisharp.Utils.stringsEq(txt, "П") ? "ПОСЕЛОК" : (com.pullenti.unisharp.Utils.stringsEq(txt, "Х") ? "ХУТОР" : "СЕЛО")));
                            if (com.pullenti.unisharp.Utils.stringsEq(txt, "П")) 
                                res.altValue = "ПОСЕЛЕНИЕ";
                            else if (com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C")) {
                                res.altValue = "СЕЛЕНИЕ";
                                if (tt0 == tt1) {
                                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS, 0, null);
                                    if (npt != null && npt.getMorph().getCase().isInstrumental()) 
                                        return null;
                                }
                            }
                            res.doubtful = true;
                            return res;
                        }
                    }
                    if ((com.pullenti.unisharp.Utils.stringsEq(txt, "СП") || com.pullenti.unisharp.Utils.stringsEq(txt, "РП") || com.pullenti.unisharp.Utils.stringsEq(txt, "ГП")) || com.pullenti.unisharp.Utils.stringsEq(txt, "ДП")) {
                        if (tt.getNext() != null && tt.getNext().isChar('.')) 
                            tt = tt.getNext();
                        if (tt.getNext() != null && tt.getNext().chars.isCapitalUpper()) 
                            return _new1229(tt0, tt, ItemType.NOUN, true, Condition._new1228(t), (com.pullenti.unisharp.Utils.stringsEq(txt, "РП") ? "РАБОЧИЙ ПОСЕЛОК" : (com.pullenti.unisharp.Utils.stringsEq(txt, "ГП") ? "ГОРОДСКОЕ ПОСЕЛЕНИЕ" : (com.pullenti.unisharp.Utils.stringsEq(txt, "ДП") ? "ДАЧНЫЙ ПОСЕЛОК" : "СЕЛЬСКОЕ ПОСЕЛЕНИЕ"))));
                    }
                    if (tt0 != tt && CityItemToken.checkKeyword(tt) != null) {
                        res = tryParse(tt, null, false, ad);
                        if (res != null && res.typ == ItemType.NOUN) {
                            res.geoObjectBefore = true;
                            res.setBeginToken(tt0);
                            return res;
                        }
                    }
                    if (tt.chars.isAllUpper() && tt.getLengthChar() > 2 && tt.chars.isCyrillicLetter()) 
                        return _new1230(tt, tt, ItemType.PROPERNAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                }
            }
            if ((t instanceof com.pullenti.ner.NumberToken) && t.getNext() != null) {
                com.pullenti.ner.core.NumberExToken net = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
                if (net != null && net.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER) 
                    return _new1230(t, net.getEndToken(), ItemType.PROPERNAME, ((((Integer)((int)net.getRealValue())).toString()) + "КМ"));
            }
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if ((rt != null && (rt.referent instanceof com.pullenti.ner.geo.GeoReferent) && rt.getBeginToken() == rt.getEndToken()) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) {
                if (t.getPrevious() == null) 
                    return null;
                if (t.getPrevious().getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && t.getMorph().getCase().isNominative() && !t.getMorph().getCase().isGenitive()) 
                    return _new1230(t, t, ItemType.PROPERNAME, rt.getSourceText().toUpperCase());
            }
            return null;
        }
        if (res.typ == ItemType.NOUN) {
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СЕЛО") && (t instanceof com.pullenti.ner.TextToken)) {
                if (t.getPrevious() == null) {
                }
                else if (t.getPrevious().getMorph()._getClass().isPreposition()) {
                }
                else 
                    res.doubtful = true;
                res.getMorph().setGender(com.pullenti.morph.MorphGender.NEUTER);
            }
            if (com.pullenti.morph.LanguageHelper.endsWith(res.value, "УСАДЬБА") && res.altValue == null) 
                res.altValue = "НАСЕЛЕННЫЙ ПУНКТ";
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНЦІЯ")) 
                res.doubtful = true;
            if (res.getEndToken().isValue("СТОЛИЦА", null) || res.getEndToken().isValue("СТОЛИЦЯ", null)) {
                res.doubtful = true;
                if (res.getEndToken().getNext() != null) {
                    com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    if (_geo != null && ((_geo.isRegion() || _geo.isState()))) {
                        res.higherGeo = _geo;
                        res.setEndToken(res.getEndToken().getNext());
                        res.doubtful = false;
                        res.value = "ГОРОД";
                        for (com.pullenti.ner.core.Termin it : TerrItemToken.m_CapitalsByState.termins) {
                            com.pullenti.ner.geo.GeoReferent ge = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(it.tag, com.pullenti.ner.geo.GeoReferent.class);
                            if (ge == null || !ge.canBeEquals(_geo, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                                continue;
                            com.pullenti.ner.core.TerminToken tok = TerrItemToken.m_CapitalsByState.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                            if (tok != null && tok.termin == it) 
                                break;
                            res.typ = ItemType.CITY;
                            res.value = it.getCanonicText();
                            return res;
                        }
                    }
                }
            }
            if ((res.getBeginToken().getLengthChar() == 1 && res.getBeginToken().chars.isAllUpper() && res.getBeginToken().getNext() != null) && res.getBeginToken().getNext().isChar('.')) {
                CityItemToken ne = _tryParseInt(res.getBeginToken().getNext().getNext(), null, false, ad);
                if (ne != null && ne.typ == ItemType.CITY && !ne.doubtful) {
                }
                else if (ne != null && ne.typ == ItemType.PROPERNAME && ((com.pullenti.morph.LanguageHelper.endsWithEx(ne.value, "К", "О", null, null) || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(ne.getEndToken().getNext(), false)))) {
                }
                else if (ne == null || ne.typ != ItemType.PROPERNAME) 
                    return null;
                else if (MiscLocationHelper.checkGeoObjectAfter(ne.getEndToken().getNext(), false, true)) {
                }
                else 
                    return null;
            }
        }
        if (res.typ == ItemType.PROPERNAME || res.typ == ItemType.CITY) {
            String val = (res.value != null ? res.value : ((res.ontoItem == null ? null : res.ontoItem.getCanonicText())));
            com.pullenti.ner.Token t1 = res.getEndToken();
            if (((!t1.isWhitespaceAfter() && t1.getNext() != null && t1.getNext().isHiphen()) && !t1.getNext().isWhitespaceAfter() && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() < 30)) {
                res.setEndToken(t1.getNext().getNext());
                res.value = (val + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                if (res.altValue != null) 
                    res.altValue = (res.altValue + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                res.typ = ItemType.PROPERNAME;
            }
            else if (t1.getWhitespacesAfterCount() == 1 && (t1.getNext() instanceof com.pullenti.ner.NumberToken) && t1.getNext().getMorph()._getClass().isAdjective()) {
                boolean ok = false;
                if (t1.getNext().getNext() == null || t1.getNext().isNewlineAfter()) 
                    ok = true;
                else if (!t1.getNext().isWhitespaceAfter() && t1.getNext().getNext() != null && t1.getNext().getNext().isCharOf(",")) 
                    ok = true;
                else if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(t1.getNext().getNext()) && t1.getWhitespacesAfterCount() <= t1.getNext().getWhitespacesAfterCount()) {
                    if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(t1.getNext().getNext(), false)) 
                        ok = true;
                }
                else if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(t1.getNext().getNext(), false, false)) 
                    ok = true;
                if (ok) {
                    res.setEndToken(t1.getNext());
                    res.value = (val + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    if (res.altValue != null) 
                        res.altValue = (res.altValue + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    res.typ = ItemType.PROPERNAME;
                }
            }
        }
        if (res.typ == ItemType.CITY && res.getBeginToken() == res.getEndToken()) {
            if (res.getBeginToken().getMorphClassInDictionary().isAdjective() && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                boolean ok = false;
                com.pullenti.ner.Token t1 = null;
                com.pullenti.ner.core.NounPhraseToken npt = MiscLocationHelper.tryParseNpt(res.getBeginToken());
                if (npt != null && npt.getEndToken() == res.getEndToken().getNext()) {
                    t1 = npt.getEndToken();
                    com.pullenti.morph.MorphClass mc = t1.getMorphClassInDictionary();
                    if (mc.isNoun()) {
                        if (res.getEndToken().getNext().chars.equals(res.getBeginToken().chars)) {
                            ok = true;
                            if (res.getBeginToken().chars.isAllUpper()) {
                                CityItemToken cii = _tryParseInt(res.getEndToken().getNext(), null, dontNormalize, ad);
                                if (cii != null && cii.typ == ItemType.NOUN) 
                                    ok = false;
                            }
                        }
                        else if (res.getEndToken().getNext().chars.isAllLower()) {
                            com.pullenti.ner.Token ttt = res.getEndToken().getNext().getNext();
                            if (ttt == null || ttt.isCharOf(",.")) 
                                ok = true;
                        }
                    }
                }
                else if (res.getEndToken().getNext().chars.equals(res.getBeginToken().chars) && res.getBeginToken().chars.isCapitalUpper()) {
                    com.pullenti.ner.Token ttt = res.getEndToken().getNext().getNext();
                    if (ttt == null || ttt.isCharOf(",.")) 
                        ok = true;
                    t1 = res.getEndToken().getNext();
                    npt = null;
                }
                if (ok && t1 != null) {
                    res.typ = ItemType.PROPERNAME;
                    res.ontoItem = null;
                    res.setEndToken(t1);
                    if (npt != null) {
                        res.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        res.setMorph(npt.getMorph());
                    }
                    else 
                        res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                }
            }
            if ((res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && !res.getEndToken().getNext().isWhitespaceAfter()) && !res.getEndToken().getNext().isWhitespaceBefore()) {
                CityItemToken res1 = _TryParse(res.getEndToken().getNext().getNext(), null, false, ad);
                if ((res1 != null && res1.typ == ItemType.PROPERNAME && res1.getBeginToken() == res1.getEndToken()) && res1.getBeginToken().chars.equals(res.getBeginToken().chars)) {
                    if (res1.ontoItem == null && res.ontoItem == null) {
                        res.typ = ItemType.PROPERNAME;
                        res.value = ((res.ontoItem == null ? res.value : res.ontoItem.getCanonicText()) + "-" + res1.value);
                        if (res.altValue != null) 
                            res.altValue = (res.altValue + "-" + res1.value);
                        res.ontoItem = null;
                        res.setEndToken(res1.getEndToken());
                        res.doubtful = false;
                    }
                }
                else if ((res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() < 30)) {
                    res.typ = ItemType.PROPERNAME;
                    res.value = ((res.ontoItem == null ? res.value : res.ontoItem.getCanonicText()) + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    if (res.altValue != null) 
                        res.altValue = (res.altValue + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class)).getValue());
                    res.ontoItem = null;
                    res.setEndToken(res.getEndToken().getNext().getNext());
                }
            }
            else if (res.getBeginToken().getMorphClassInDictionary().isProperName()) {
                if (res.getBeginToken().isValue("КИЇВ", null) || res.getBeginToken().isValue("АСТАНА", null) || res.getBeginToken().isValue("АЛМАТЫ", null)) {
                }
                else if ((res.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.LanguageHelper.endsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class)).term, "ВО")) {
                }
                else {
                    res.doubtful = true;
                    com.pullenti.ner.Token tt = res.getBeginToken().getPrevious();
                    if (tt != null && tt.getPrevious() != null) {
                        if (tt.isChar(',') || tt.getMorph()._getClass().isConjunction()) {
                            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getPrevious().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                            if (_geo != null && _geo.isCity()) 
                                res.doubtful = false;
                        }
                    }
                    if (tt != null && tt.isValue("В", null) && tt.chars.isAllLower()) {
                        com.pullenti.ner.core.NounPhraseToken npt1 = MiscLocationHelper.tryParseNpt(res.getBeginToken());
                        if (npt1 == null || npt1.getEndChar() <= res.getEndChar()) 
                            res.doubtful = false;
                    }
                }
            }
            if ((res.getBeginToken() == res.getEndToken() && res.typ == ItemType.CITY && res.ontoItem != null) && com.pullenti.unisharp.Utils.stringsEq(res.ontoItem.getCanonicText(), "САНКТ - ПЕТЕРБУРГ")) {
                for (com.pullenti.ner.Token tt = res.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isHiphen() || tt.isChar('.')) 
                        continue;
                    if (tt.isValue("С", null) || tt.isValue("C", null) || tt.isValue("САНКТ", null)) 
                        res.setBeginToken(tt);
                    break;
                }
            }
        }
        if ((res.getBeginToken() == res.getEndToken() && res.typ == ItemType.PROPERNAME && res.getWhitespacesAfterCount() == 1) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && res.getEndToken().chars.equals(res.getEndToken().getNext().chars)) {
            boolean ok = false;
            com.pullenti.ner.Token t1 = res.getEndToken();
            if (t1.getNext().getNext() == null || t1.getNext().isNewlineAfter()) 
                ok = true;
            else if (!t1.getNext().isWhitespaceAfter() && t1.getNext().getNext() != null && t1.getNext().getNext().isCharOf(",.")) 
                ok = true;
            if (ok) {
                CityItemToken pp = _TryParse(t1.getNext(), null, false, ad);
                if (pp != null && pp.typ == ItemType.NOUN) 
                    ok = false;
                if (ok) {
                    TerrItemToken te = TerrItemToken.tryParse(t1.getNext(), null, null);
                    if (te != null) {
                        if (te.terminItem != null) 
                            ok = false;
                        else if (te.ontoItem != null && !te.getMorph().getCase().isGenitive()) 
                            ok = false;
                    }
                    if (ok) {
                        if (com.pullenti.ner.address.internal.StreetItemToken.checkKeyword(t1.getNext())) 
                            ok = false;
                    }
                }
            }
            if (ok) {
                res.setEndToken(t1.getNext());
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                res.altValue = null;
                res.typ = ItemType.PROPERNAME;
            }
        }
        return res;
    }

    private static CityItemToken _TryParse(com.pullenti.ner.Token t, CityItemToken prev, boolean dontNormalize, GeoAnalyzerData ad) {
        if (!(t instanceof com.pullenti.ner.TextToken)) {
            if ((t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
                java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> aii = com.pullenti.ner.address.internal.StreetItemToken.tryParseSpec(t, null);
                if (aii != null) {
                    if (aii.size() > 1 && aii.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NUMBER && aii.get(1).typ == com.pullenti.ner.address.internal.StreetItemType.STDNAME) {
                        CityItemToken res2 = _new1225(t, aii.get(1).getEndToken(), ItemType.PROPERNAME);
                        res2.value = ((aii.get(0).number == null ? aii.get(0).value : aii.get(0).number.getIntValue().toString()) + " " + aii.get(1).value);
                        return res2;
                    }
                }
            }
            if ((((t instanceof com.pullenti.ner.NumberToken) && prev != null && prev.typ == ItemType.NOUN) && (t.getWhitespacesBeforeCount() < 3) && (t.getWhitespacesAfterCount() < 3)) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isCapitalUpper()) {
                if (prev.getBeginToken().isValue("СТ", null) || prev.getBeginToken().isValue("П", null)) 
                    return null;
                CityItemToken cit1 = tryParse(t.getNext(), null, false, ad);
                if (cit1 != null && cit1.typ == ItemType.PROPERNAME && cit1.value != null) {
                    cit1.setBeginToken(t);
                    cit1.value = (cit1.value + "-" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                    return cit1;
                }
            }
            return null;
        }
        CityItemToken spec = _tryParseSpec(t);
        if (spec != null) 
            return spec;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li0 = null;
        boolean isInLocOnto = false;
        if (t.kit.ontology != null && li == null) {
            if ((((li0 = t.kit.ontology.attachToken(com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, t)))) != null) {
                li = li0;
                isInLocOnto = true;
            }
        }
        if (li == null) {
            li = m_Ontology.tryAttach(t, null, false);
            if (li == null) 
                li = m_OntologyEx.tryAttach(t, null, false);
        }
        else if (prev == null) {
            com.pullenti.ner.address.internal.StreetItemToken stri = com.pullenti.ner.address.internal.StreetItemToken.tryParse(t.getPrevious(), null, false, ad);
            if (stri != null && stri.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                return null;
            stri = com.pullenti.ner.address.internal.StreetItemToken.tryParse(li.get(0).getEndToken().getNext(), null, false, ad);
            if (stri != null && stri.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                return null;
        }
        if (li != null && li.size() > 0) {
            if (t instanceof com.pullenti.ner.TextToken) {
                for (int i = li.size() - 1; i >= 0; i--) {
                    if (li.get(i).item != null) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).item.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null) {
                            if (!g.isCity()) {
                                li.remove(i);
                                continue;
                            }
                        }
                    }
                }
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                    if (nt.item != null && com.pullenti.unisharp.Utils.stringsEq(nt.item.getCanonicText(), tt.term)) {
                        if (!com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false)) {
                            CityItemToken ci = _new1234(nt.getBeginToken(), nt.getEndToken(), ItemType.CITY, nt.item, nt.getMorph());
                            if (nt.getBeginToken() == nt.getEndToken() && !isInLocOnto) 
                                ci.doubtful = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class));
                            com.pullenti.ner.Token tt1 = nt.getEndToken().getNext();
                            if ((((tt1 != null && tt1.isHiphen() && !tt1.isWhitespaceBefore()) && !tt1.isWhitespaceAfter() && prev != null) && prev.typ == ItemType.NOUN && (tt1.getNext() instanceof com.pullenti.ner.TextToken)) && tt1.getPrevious().chars.equals(tt1.getNext().chars)) {
                                li = null;
                                break;
                            }
                            return ci;
                        }
                    }
                }
                if (li != null) {
                    for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                        if (nt.item != null) {
                            if (!com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false)) {
                                CityItemToken ci = _new1234(nt.getBeginToken(), nt.getEndToken(), ItemType.CITY, nt.item, nt.getMorph());
                                if (nt.getBeginToken() == nt.getEndToken() && (nt.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                                    ci.doubtful = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class));
                                    String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class)).term;
                                    if (com.pullenti.unisharp.Utils.stringsNe(str, nt.item.getCanonicText())) {
                                        if (com.pullenti.morph.LanguageHelper.endsWithEx(str, "О", "А", null, null)) 
                                            ci.altValue = str;
                                    }
                                }
                                return ci;
                            }
                        }
                    }
                }
            }
            if (li != null) {
                for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                    if (nt.item == null) {
                        ItemType ty = (nt.termin.tag == null ? ItemType.NOUN : (ItemType)nt.termin.tag);
                        CityItemToken ci = _new1236(nt.getBeginToken(), nt.getEndToken(), ty, nt.getMorph());
                        ci.value = nt.termin.getCanonicText();
                        if (ty == ItemType.MISC && com.pullenti.unisharp.Utils.stringsEq(ci.value, "ЖИТЕЛЬ") && t.getPrevious() != null) {
                            if (t.getPrevious().isValue("МЕСТНЫЙ", "МІСЦЕВИЙ")) 
                                return null;
                            if (t.getPrevious().getMorph()._getClass().isPronoun()) 
                                return null;
                        }
                        if (ty == ItemType.NOUN && !t.chars.isAllLower()) {
                            if (t.getMorph()._getClass().isProperSurname()) 
                                ci.doubtful = true;
                        }
                        if (nt.getBeginToken() == nt.getEndToken()) {
                            if (t.isValue("СТ", null)) {
                                if (OrgItemToken.tryParse(t, null) != null) 
                                    return null;
                            }
                        }
                        if (nt.getBeginToken().kit.baseLanguage.isUa()) {
                            if (nt.getBeginToken().isValue("М", null) || nt.getBeginToken().isValue("Г", null)) {
                                if (!nt.getBeginToken().chars.isAllLower()) 
                                    return null;
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("МІС", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "МІС")) 
                                    return null;
                                ci.doubtful = true;
                            }
                        }
                        if (nt.getBeginToken().kit.baseLanguage.isRu()) {
                            if (nt.getBeginToken().isValue("Г", null)) {
                                if (nt.getBeginToken().getPrevious() != null && nt.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) {
                                }
                                else {
                                    boolean ok = true;
                                    if (!nt.getBeginToken().chars.isAllLower()) 
                                        ok = false;
                                    else if ((nt.getEndToken() == nt.getBeginToken() && nt.getEndToken().getNext() != null && !nt.getEndToken().isWhitespaceAfter()) && ((nt.getEndToken().getNext().isCharOf("\\/") || nt.getEndToken().getNext().isHiphen()))) 
                                        ok = false;
                                    else if (!t.isWhitespaceBefore() && t.getPrevious() != null && ((t.getPrevious().isCharOf("\\/") || t.getPrevious().isHiphen()))) 
                                        return null;
                                    if (!ok) {
                                        CityItemToken nex = tryParse(nt.getEndToken().getNext(), null, false, null);
                                        if (nex != null && nex.typ == ItemType.CITY && (nt.getEndToken().getWhitespacesAfterCount() < 4)) {
                                        }
                                        else if (nt.getEndToken().getNext() != null && com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(nt.getEndToken().getNext().getNext(), false)) {
                                        }
                                        else {
                                            if (nex == null || nex.typ != ItemType.PROPERNAME) 
                                                return null;
                                            if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(nex.getEndToken().getNext(), false)) {
                                            }
                                            else if (MiscLocationHelper.checkGeoObjectAfter(nex.getEndToken(), false, true)) {
                                            }
                                            else 
                                                return null;
                                        }
                                    }
                                }
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("ГОР", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ГОР")) {
                                    if (t.chars.isCapitalUpper()) {
                                        ci = null;
                                        break;
                                    }
                                    return null;
                                }
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("ПОС", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ПОС")) 
                                    return null;
                                ci.doubtful = true;
                            }
                        }
                        com.pullenti.ner.core.NounPhraseToken npt1 = MiscLocationHelper.tryParseNpt(t.getPrevious());
                        if (npt1 != null && npt1.adjectives.size() > 0) {
                            String s = npt1.adjectives.get(0).getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                            if ((com.pullenti.unisharp.Utils.stringsEq(s, "РОДНОЙ") || com.pullenti.unisharp.Utils.stringsEq(s, "ЛЮБИМЫЙ") || com.pullenti.unisharp.Utils.stringsEq(s, "РІДНИЙ")) || com.pullenti.unisharp.Utils.stringsEq(s, "КОХАНИЙ")) 
                                return null;
                        }
                        if (t.isValue("ПОСЕЛЕНИЕ", null)) {
                            if (t.getNext() != null && t.getNext().isValue("СТАНЦИЯ", null)) {
                                CityItemToken ci1 = tryParse(t.getNext().getNext(), null, false, null);
                                if (ci1 != null && ((ci1.typ == ItemType.PROPERNAME || ci1.typ == ItemType.CITY))) 
                                    ci.setEndToken(t.getNext());
                            }
                        }
                        return ci;
                    }
                }
            }
        }
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "СПБ") && !t.chars.isAllLower() && m_StPeterburg != null) 
            return _new1237(t, t, ItemType.CITY, m_StPeterburg, m_StPeterburg.getCanonicText());
        if (t.chars.isAllLower()) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> stds = m_StdAdjectives.tryAttach(t, null, false);
        if (stds != null) {
            CityItemToken cit = tryParse(stds.get(0).getEndToken().getNext(), null, false, ad);
            if (cit != null && ((((cit.typ == ItemType.PROPERNAME && cit.value != null)) || cit.typ == ItemType.CITY))) {
                String adj = stds.get(0).termin.getCanonicText();
                cit.value = (adj + " " + ((cit.value != null ? cit.value : (cit != null && cit.ontoItem != null ? cit.ontoItem.getCanonicText() : null))));
                if (cit.altValue != null) 
                    cit.altValue = (adj + " " + cit.altValue);
                cit.setBeginToken(t);
                com.pullenti.ner.core.NounPhraseToken npt0 = MiscLocationHelper.tryParseNpt(t);
                if (npt0 != null && npt0.getEndToken() == cit.getEndToken()) {
                    cit.setMorph(npt0.getMorph());
                    cit.value = npt0.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                }
                cit.typ = ItemType.PROPERNAME;
                cit.doubtful = false;
                return cit;
            }
        }
        com.pullenti.ner.Token t1 = t;
        boolean doubt = false;
        StringBuilder name = new StringBuilder();
        StringBuilder altname = null;
        int k = 0;
        boolean isPrep = false;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!tt.chars.isLetter() || ((tt.chars.isCyrillicLetter() != t.chars.isCyrillicLetter() && !tt.isValue("НА", null)))) 
                break;
            if (tt != t) {
                com.pullenti.ner.address.internal.StreetItemToken si = com.pullenti.ner.address.internal.StreetItemToken.tryParse(tt, null, false, null);
                if (si != null && si.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) {
                    if (si.getEndToken().getNext() == null || si.getEndToken().getNext().isCharOf(",.")) {
                    }
                    else 
                        break;
                }
                if (tt.getLengthChar() < 2) 
                    break;
                if ((tt.getLengthChar() < 3) && !tt.isValue("НА", null)) {
                    if (tt.isWhitespaceBefore()) 
                        break;
                }
            }
            if (name.length() > 0) {
                name.append('-');
                if (altname != null) 
                    altname.append('-');
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && ((isPrep || ((k > 0 && !tt.getMorphClassInDictionary().isProperGeo()))))) {
                name.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                if (altname != null) 
                    altname.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
            }
            else {
                String ss = (dontNormalize ? ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term : getNormalGeo(tt));
                if (com.pullenti.unisharp.Utils.stringsEq(ss, "ПОЛ") && tt.isValue("ПОЛЕ", null)) 
                    ss = "ПОЛЕ";
                if (com.pullenti.unisharp.Utils.stringsNe(ss, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term)) {
                    if (altname == null) 
                        altname = new StringBuilder();
                    altname.append(name.toString());
                    altname.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                }
                else if (altname != null) 
                    altname.append(ss);
                name.append(ss);
            }
            t1 = tt;
            isPrep = tt.getMorph()._getClass().isPreposition();
            if (tt.getNext() == null || tt.getNext().getNext() == null) 
                break;
            if (!tt.getNext().isHiphen()) 
                break;
            if (dontNormalize) 
                break;
            if (tt.isWhitespaceAfter() || tt.getNext().isWhitespaceAfter()) {
                if (tt.getWhitespacesAfterCount() > 1 || tt.getNext().getWhitespacesAfterCount() > 1) 
                    break;
                if (!tt.getNext().getNext().chars.equals(tt.chars)) 
                    break;
                com.pullenti.ner.Token ttt = tt.getNext().getNext().getNext();
                if (ttt != null && !ttt.isNewlineAfter()) {
                    if (ttt.chars.isLetter()) 
                        break;
                }
            }
            tt = tt.getNext();
            k++;
        }
        if (k > 0) {
            if (k > 2) 
                return null;
            CityItemToken reee = _new1238(t, t1, ItemType.PROPERNAME, name.toString(), doubt);
            if (altname != null) 
                reee.altValue = altname.toString();
            return reee;
        }
        if (t == null) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = (t.chars.isLatinLetter() ? null : com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null));
        if (npt != null && (npt.getEndToken() instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.ReferentToken.class)).getBeginToken() != ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.ReferentToken.class)).getEndToken()) 
            npt = null;
        if ((npt != null && npt.getEndToken() != t && npt.adjectives.size() > 0) && !npt.adjectives.get(0).getEndToken().getNext().isComma()) {
            CityItemToken cit = tryParse(t.getNext(), null, false, null);
            if (cit != null && cit.typ == ItemType.NOUN && ((com.pullenti.morph.LanguageHelper.endsWithEx(cit.value, "ПУНКТ", "ПОСЕЛЕНИЕ", "ПОСЕЛЕННЯ", "ПОСЕЛОК") || t.getNext().isValue("ГОРОДОК", null) || t.getNext().isValue("СЕЛО", null)))) {
                boolean ok2 = false;
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (!mc.isAdjective()) 
                    ok2 = true;
                else if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                    ok2 = true;
                else if (MiscLocationHelper.checkGeoObjectBefore(t, false)) 
                    ok2 = true;
                if (ok2) 
                    return _new1239(t, t, ItemType.CITY, t.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
            }
            boolean _check = true;
            if (!npt.getEndToken().chars.equals(t.chars)) {
                if (npt.getEndToken().isValue("КИЛОМЕТР", null)) 
                    npt = null;
                else if (OrgItemToken.tryParse(t.getNext(), null) != null) 
                    npt = null;
                else if (npt.getEndToken().chars.isAllLower() && ((npt.getEndToken().getNext() == null || npt.getEndToken().getNext().isComma() || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(npt.getEndToken().getNext(), false)))) {
                }
                else {
                    com.pullenti.ner.address.internal.AddressItemToken aid = com.pullenti.ner.address.internal.AddressItemToken.tryParse(t.getNext(), false, null, null);
                    if (aid != null) 
                        npt = null;
                    else if (prev != null && prev.typ == ItemType.NOUN && CityAttachHelper.checkCityAfter(t.getNext())) 
                        _check = false;
                    else {
                        com.pullenti.ner.ReferentToken rt1 = t.kit.processReferent("NAMEDENTITY", t, null);
                        if (rt1 != null && rt1.getEndToken() == npt.getEndToken()) {
                        }
                        else 
                            npt = null;
                    }
                }
            }
            if (_check && !dontNormalize && npt != null) {
                OrgItemToken org1 = OrgItemToken.tryParse(t.getNext(), null);
                if (org1 != null && !org1.isDoubt) {
                    OrgItemToken org0 = OrgItemToken.tryParse(t, null);
                    if (org0 != null && org0.isDoubt) 
                        npt = null;
                }
            }
            if (_check && !dontNormalize && npt != null) {
                if (npt.adjectives.size() != 1) 
                    return null;
                com.pullenti.ner.core.IntOntologyToken ter = TerrItemToken.checkOntoItem(npt.noun.getBeginToken());
                if (ter != null) 
                    npt = null;
                else {
                    com.pullenti.ner.core.NounPhraseToken npt1 = MiscLocationHelper.tryParseNpt(npt.getEndToken());
                    if (npt1 == null || npt1.adjectives.size() == 0) {
                        com.pullenti.ner.address.internal.StreetItemToken si = com.pullenti.ner.address.internal.StreetItemToken.tryParse(npt.getEndToken(), null, false, null);
                        if ((si == null || si.typ != com.pullenti.ner.address.internal.StreetItemType.NOUN || com.pullenti.unisharp.Utils.stringsEq(si.termin.getCanonicText(), "МОСТ")) || com.pullenti.unisharp.Utils.stringsEq(si.termin.getCanonicText(), "ПАРК") || com.pullenti.unisharp.Utils.stringsEq(si.termin.getCanonicText(), "САД")) {
                            t1 = npt.getEndToken();
                            doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class));
                            return _new1240(t, t1, ItemType.PROPERNAME, npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), doubt, npt.getMorph());
                        }
                    }
                }
            }
        }
        if (t.getNext() != null && t.getNext().chars.equals(t.chars) && !t.isNewlineAfter()) {
            boolean ok = false;
            if (TerrItemToken.checkOntoItem(t.getNext()) != null) {
            }
            else if (t.getNext().getNext() == null || !t.getNext().getNext().chars.equals(t.chars)) 
                ok = true;
            else if (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                ok = true;
            else {
                java.util.ArrayList<TerrItemToken> tis = TerrItemToken.tryParseList(t.getNext().getNext(), 2, null);
                if (tis != null && tis.size() > 1) {
                    if (tis.get(0).isAdjective && tis.get(1).terminItem != null) 
                        ok = true;
                }
            }
            if (ok && (((t.getNext() instanceof com.pullenti.ner.TextToken) || (((t.getNext() instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class)).getBeginToken() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class)).getEndToken()))))) {
                if (t.getNext() instanceof com.pullenti.ner.TextToken) 
                    doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class));
                com.pullenti.ner.core.StatisticBigrammInfo stat = t.kit.statistics.getBigrammInfo(t, t.getNext());
                boolean ok1 = false;
                if ((stat != null && stat.pairCount >= 2 && stat.pairCount == stat.secondCount) && !stat.secondHasOtherFirst) {
                    if (stat.pairCount > 2) 
                        doubt = false;
                    ok1 = true;
                }
                else if (m_StdAdjectives.tryAttach(t, null, false) != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) 
                    ok1 = true;
                else if (((t.getNext().getNext() == null || t.getNext().getNext().isComma())) && t.getMorph()._getClass().isNoun() && ((t.getNext().getMorph()._getClass().isAdjective() || t.getNext().getMorph()._getClass().isNoun()))) 
                    ok1 = true;
                if (!ok1 && t.getNext().chars.value == t.chars.value) {
                    if (t.getNext().getMorph().getCase().isGenitive() || (((((com.pullenti.morph.LanguageHelper.endsWith(name.toString(), "ОВ") || com.pullenti.morph.LanguageHelper.endsWith(name.toString(), "ВО"))) && (t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()) && t.getNext().getLengthChar() > 1 && !t.getNext().getMorphClassInDictionary().isUndefined()))) {
                        if (t.getNext().isNewlineAfter()) 
                            ok1 = true;
                        else if (MiscLocationHelper.checkGeoObjectAfter(t.getNext(), false, false)) 
                            ok1 = true;
                        else {
                            com.pullenti.ner.address.internal.AddressItemToken aid = com.pullenti.ner.address.internal.AddressItemToken.tryParse(t.getNext().getNext(), false, null, null);
                            if (aid != null) {
                                if (aid.getTyp() == com.pullenti.ner.address.internal.AddressItemType.STREET || aid.getTyp() == com.pullenti.ner.address.internal.AddressItemType.PLOT || aid.getTyp() == com.pullenti.ner.address.internal.AddressItemType.HOUSE) 
                                    ok1 = true;
                            }
                        }
                    }
                }
                if (ok1) {
                    CityItemToken tne = _tryParseInt(t.getNext(), null, false, ad);
                    if (tne != null && tne.typ == ItemType.NOUN) {
                    }
                    else {
                        if (t.getNext() instanceof com.pullenti.ner.TextToken) {
                            name.append(" ").append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                            if (altname != null) 
                                altname.append(" ").append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term);
                        }
                        else {
                            name.append(" ").append(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO));
                            if (altname != null) 
                                altname.append(" ").append(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO));
                        }
                        t1 = t.getNext();
                        return _new1241(t, t1, ItemType.PROPERNAME, name.toString(), (altname == null ? null : altname.toString()), doubt, t.getNext().getMorph());
                    }
                }
            }
        }
        if (t.getLengthChar() < 2) 
            return null;
        t1 = t;
        doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class));
        if (((t.getNext() != null && prev != null && prev.typ == ItemType.NOUN) && t.getNext().chars.isCyrillicLetter() && t.getNext().chars.isAllLower()) && t.getWhitespacesAfterCount() == 1) {
            com.pullenti.ner.Token tt = t.getNext();
            boolean ok = false;
            if (tt.getNext() == null || tt.getNext().isCharOf(",;")) 
                ok = true;
            if (ok && com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt.getNext(), false, null, null) == null) {
                t1 = tt;
                name.append(" ").append(t1.getSourceText().toUpperCase());
            }
        }
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(t)) 
            return null;
        CityItemToken res = _new1241(t, t1, ItemType.PROPERNAME, name.toString(), (altname == null ? null : altname.toString()), doubt, t.getMorph());
        if (t1 == t && (t1 instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term0 != null) 
            res.altValue = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term0;
        boolean sog = false;
        boolean glas = false;
        for (char ch : res.value.toCharArray()) {
            if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                glas = true;
            else 
                sog = true;
        }
        if (t.chars.isAllUpper() && t.getLengthChar() > 2) {
            if (!glas || !sog) 
                res.doubtful = true;
        }
        else if (!glas || !sog) 
            return null;
        if (t == t1 && (t instanceof com.pullenti.ner.TextToken)) {
            if (com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, res.value)) 
                res.altValue = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        }
        if ((res.getWhitespacesAfterCount() < 2) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
            com.pullenti.ner.core.TerminToken abbr = m_SpecAbbrs.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (abbr != null) {
                res.setEndToken(abbr.getEndToken());
                res.value = (res.value + " " + abbr.termin.getCanonicText());
                if (res.altValue != null) 
                    res.altValue = (res.altValue + " " + abbr.termin.getCanonicText());
            }
            else if (!res.getEndToken().getNext().chars.isAllLower()) {
                abbr = m_SpecAbbrs.tryParse(res.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (abbr != null && abbr.getEndToken() == res.getEndToken()) {
                    CityItemToken _next = _tryParseInt(res.getEndToken().getNext(), null, dontNormalize, ad);
                    if (_next != null && ((_next.typ == ItemType.PROPERNAME || _next.typ == ItemType.CITY))) {
                        res.setEndToken(_next.getEndToken());
                        res.altValue = (_next.value + " " + res.value);
                        res.value = (res.value + " " + _next.value);
                    }
                }
            }
        }
        return res;
    }

    private static CityItemToken _tryParseSpec(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok1 = m_SpecNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok1 != null) {
            CityItemToken res = _new1230(t, tok1.getEndToken(), ItemType.PROPERNAME, tok1.termin.getCanonicText());
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "ЦЕНТРАЛЬНАЯ УСАДЬБА")) {
                CityItemToken res1 = _tryParseSpec(res.getEndToken().getNext());
                if (res1 != null) {
                    res.value = (res1.value + " " + res.value);
                    res.setEndToken(res1.getEndToken());
                }
            }
            return res;
        }
        tok1 = m_SpecAbbrs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok1 != null && com.pullenti.unisharp.Utils.stringsEq(tok1.termin.getCanonicText(), "СОВХОЗ")) {
            com.pullenti.ner.Token tt = tok1.getEndToken().getNext();
            CityItemToken res = null;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    res = _new1225(t, br.getEndToken(), ItemType.PROPERNAME);
                    res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                }
            }
            else {
                CityItemToken cit = tryParse(tt, null, false, null);
                if (cit != null && ((cit.typ == ItemType.PROPERNAME || cit.typ == ItemType.CITY))) 
                    res = cit;
            }
            if (res != null) {
                res.typ = ItemType.PROPERNAME;
                tok1 = m_SpecNames.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null && com.pullenti.unisharp.Utils.stringsEq(tok1.termin.getCanonicText(), "ЦЕНТРАЛЬНАЯ УСАДЬБА")) {
                    res.value = (res.value + " " + tok1.termin.getCanonicText());
                    res.setEndToken(tok1.getEndToken());
                }
                return res;
            }
        }
        return null;
    }

    public static CityItemToken tryParseBack(com.pullenti.ner.Token t, boolean onlyNoun) {
        while (t != null && ((t.isCharOf("(,") || t.isAnd()))) {
            t = t.getPrevious();
        }
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                return null;
            if (!tt.chars.isLetter()) 
                continue;
            if (onlyNoun) {
                com.pullenti.ner.core.IntOntologyToken vv = checkKeyword(tt);
                if (vv != null && vv.getEndToken() == t) 
                    return _new1230(tt, t, ItemType.NOUN, vv.termin.getCanonicText());
            }
            else {
                CityItemToken res = tryParse(tt, null, false, null);
                if (res != null && res.getEndToken() == t) 
                    return res;
            }
            if ((++cou) > 2) 
                break;
        }
        return null;
    }

    private static String getNormalGeo(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        char ch = tt.term.charAt(tt.term.length() - 1);
        if (((ch == 'О' || ch == 'В' || ch == 'Ы') || ch == 'Х' || ch == 'Ь') || ch == 'Й') 
            return tt.term;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isProperGeo() && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase;
        }
        boolean geoEqTerm = false;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isProperGeo()) {
                String ggg = ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase;
                if (com.pullenti.unisharp.Utils.stringsEq(ggg, tt.term)) 
                    geoEqTerm = true;
                else if (!wf.getCase().isNominative()) 
                    return ggg;
            }
        }
        if (geoEqTerm) 
            return tt.term;
        if (tt.getMorph().getItemsCount() > 0) 
            return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(0), com.pullenti.morph.MorphWordForm.class)).normalCase;
        else 
            return tt.term;
    }

    private static boolean checkDoubtful(com.pullenti.ner.TextToken tt) {
        if (tt == null) 
            return true;
        if (tt.chars.isAllLower()) 
            return true;
        if (tt.getLengthChar() < 3) 
            return true;
        if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "СОЧИ") || tt.isValue("КИЕВ", null) || tt.isValue("ПСКОВ", null)) || tt.isValue("БОСТОН", null) || tt.isValue("РИГА", null)) || tt.isValue("АСТАНА", null) || tt.isValue("АЛМАТЫ", null)) 
            return false;
        if (com.pullenti.morph.LanguageHelper.endsWith(tt.term, "ВО")) 
            return false;
        if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesAfterCount() < 2) && !tt.getNext().chars.isAllLower()) {
            if (tt.chars.equals(tt.getNext().chars) && !tt.chars.isLatinLetter() && ((!tt.getMorph().getCase().isGenitive() && !tt.getMorph().getCase().isAccusative()))) {
                com.pullenti.morph.MorphClass mc = tt.getNext().getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperSecname()) 
                    return true;
            }
        }
        if ((tt.getPrevious() instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesBeforeCount() < 2) && !tt.getPrevious().chars.isAllLower()) {
            com.pullenti.morph.MorphClass mc = tt.getPrevious().getMorphClassInDictionary();
            if (mc.isProperSurname()) 
                return true;
        }
        boolean ok = false;
        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf.isInDictionary()) {
                if (!wf._getClass().isProper()) 
                    ok = true;
                if (wf._getClass().isProperSurname() || wf._getClass().isProperName() || wf._getClass().isProperSecname()) {
                    if (com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, "ЛОНДОН") && com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, "ЛОНДОНЕ")) 
                        ok = true;
                }
            }
            else if (wf._getClass().isProperSurname()) {
                String val = (wf.normalFull != null ? wf.normalFull : wf.normalCase);
                if (com.pullenti.morph.LanguageHelper.endsWithEx(val, "ОВ", "ЕВ", "ИН", null)) {
                    if (com.pullenti.unisharp.Utils.stringsNe(val, "БЕРЛИН")) {
                        if (tt.getPrevious() != null && tt.getPrevious().isValue("В", null)) {
                        }
                        else 
                            return true;
                    }
                }
            }
        }
        if (!ok) 
            return false;
        com.pullenti.ner.Token t0 = tt.getPrevious();
        if (t0 != null && ((t0.isChar(',') || t0.getMorph()._getClass().isConjunction()))) 
            t0 = t0.getPrevious();
        if (t0 != null && (t0.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
            return false;
        if (MiscLocationHelper.checkGeoObjectAfterBrief(tt, null)) 
            return false;
        return true;
    }

    public static CityItemToken _new1225(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static CityItemToken _new1229(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, boolean _arg4, Condition _arg5, String _arg6) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.doubtful = _arg4;
        res.cond = _arg5;
        res.value = _arg6;
        return res;
    }

    public static CityItemToken _new1230(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static CityItemToken _new1234(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.core.IntOntologyItem _arg4, com.pullenti.ner.MorphCollection _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ontoItem = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static CityItemToken _new1236(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static CityItemToken _new1237(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.core.IntOntologyItem _arg4, String _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ontoItem = _arg4;
        res.value = _arg5;
        return res;
    }

    public static CityItemToken _new1238(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, boolean _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.doubtful = _arg5;
        return res;
    }

    public static CityItemToken _new1239(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static CityItemToken _new1240(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.doubtful = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static CityItemToken _new1241(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, String _arg5, boolean _arg6, com.pullenti.ner.MorphCollection _arg7) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.altValue = _arg5;
        res.doubtful = _arg6;
        res.setMorph(_arg7);
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType PROPERNAME; // 0
    
        public static final ItemType CITY; // 1
    
        public static final ItemType NOUN; // 2
    
        public static final ItemType MISC; // 3
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ItemType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(ItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ItemType> mapIntToEnum; 
        private static java.util.HashMap<String, ItemType> mapStringToEnum; 
        private static ItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ItemType item = new ItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ItemType>();
            mapStringToEnum = new java.util.HashMap<String, ItemType>();
            PROPERNAME = new ItemType(0, "PROPERNAME");
            mapIntToEnum.put(PROPERNAME.value(), PROPERNAME);
            mapStringToEnum.put(PROPERNAME.m_str.toUpperCase(), PROPERNAME);
            CITY = new ItemType(1, "CITY");
            mapIntToEnum.put(CITY.value(), CITY);
            mapStringToEnum.put(CITY.m_str.toUpperCase(), CITY);
            NOUN = new ItemType(2, "NOUN");
            mapIntToEnum.put(NOUN.value(), NOUN);
            mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
            MISC = new ItemType(3, "MISC");
            mapIntToEnum.put(MISC.value(), MISC);
            mapStringToEnum.put(MISC.m_str.toUpperCase(), MISC);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public CityItemToken() {
        super();
    }
    public static CityItemToken _globalInstance;
    
    static {
        try { _globalInstance = new CityItemToken(); } 
        catch(Exception e) { }
    }
}
