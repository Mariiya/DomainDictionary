/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonAttrToken extends com.pullenti.ner.ReferentToken {

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (m_Termins != null) 
            return;
        PersonAttrTermin t;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        m_Termins.add(PersonAttrTermin._new2601("ТОВАРИЩ", PersonAttrTerminType.PREFIX));
        m_Termins.add(PersonAttrTermin._new2602("ТОВАРИШ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.PREFIX));
        for (String s : new String[] {"ГОСПОДИН", "ГРАЖДАНИН", "УРОЖЕНЕЦ", "ВЫХОДЕЦ ИЗ", "МИСТЕР", "СЭР", "СЕНЬОР", "МОНСЕНЬОР", "СИНЬОР", "МЕСЬЕ", "МСЬЕ", "ДОН", "МАЭСТРО", "МЭТР"}) {
            t = PersonAttrTermin._new2603(s, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.MASCULINE);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРАЖДАНИН")) {
                t.addAbridge("ГР.");
                t.addAbridge("ГРАЖД.");
                t.addAbridge("ГР-Н");
            }
            m_Termins.add(t);
        }
        for (String s : new String[] {"ПАН", "ГРОМАДЯНИН", "УРОДЖЕНЕЦЬ", "ВИХОДЕЦЬ З", "МІСТЕР", "СЕР", "СЕНЬЙОР", "МОНСЕНЬЙОР", "МЕСЬЄ", "МЕТР", "МАЕСТРО"}) {
            t = PersonAttrTermin._new2604(s, com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.MASCULINE);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРОМАДЯНИН")) {
                t.addAbridge("ГР.");
                t.addAbridge("ГР-Н");
            }
            m_Termins.add(t);
        }
        for (String s : new String[] {"ГОСПОЖА", "ПАНИ", "ГРАЖДАНКА", "УРОЖЕНКА", "СЕНЬОРА", "СЕНЬОРИТА", "СИНЬОРА", "СИНЬОРИТА", "МИСС", "МИССИС", "МАДАМ", "МАДЕМУАЗЕЛЬ", "ФРАУ", "ФРОЙЛЯЙН", "ЛЕДИ", "ДОННА"}) {
            t = PersonAttrTermin._new2603(s, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.FEMINIE);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРАЖДАНКА")) {
                t.addAbridge("ГР.");
                t.addAbridge("ГРАЖД.");
                t.addAbridge("ГР-КА");
            }
            m_Termins.add(t);
        }
        for (String s : new String[] {"ПАНІ", "ГРОМАДЯНКА", "УРОДЖЕНКА", "СЕНЬЙОРА", "СЕНЬЙОРА", "МІС", "МІСІС", "МАДАМ", "МАДЕМУАЗЕЛЬ", "ФРАУ", "ФРОЙЛЯЙН", "ЛЕДІ"}) {
            t = PersonAttrTermin._new2604(s, com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.FEMINIE);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРОМАДЯНКА")) {
                t.addAbridge("ГР.");
                t.addAbridge("ГР-КА");
            }
            m_Termins.add(t);
        }
        t = PersonAttrTermin._new2604("MISTER", com.pullenti.morph.MorphLang.EN, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("MR");
        t.addAbridge("MR.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2604("MISSIS", com.pullenti.morph.MorphLang.EN, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("MRS");
        t.addAbridge("MSR.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2604("MISS", com.pullenti.morph.MorphLang.EN, PersonAttrTerminType.PREFIX, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("MS");
        t.addAbridge("MS.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("БЕЗРАБОТНЫЙ", PersonAttrTerminType.POSITION);
        t.addVariant("НЕ РАБОТАЮЩИЙ", false);
        t.addVariant("НЕ РАБОТАЕТ", false);
        t.addVariant("ВРЕМЕННО НЕ РАБОТАЮЩИЙ", false);
        t.addVariant("ВРЕМЕННО НЕ РАБОТАЕТ", false);
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("БЕЗРОБІТНИЙ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addVariant("НЕ ПРАЦЮЮЧИЙ", false);
        t.addVariant("НЕ ПРАЦЮЄ", false);
        t.addVariant("ТИМЧАСОВО НЕ ПРАЦЮЮЧИЙ", false);
        t.addVariant("ТИМЧАСОВО НЕ ПРАЦЮЄ", false);
        m_Termins.add(t);
        t = PersonAttrTermin._new2612("ЗАМЕСТИТЕЛЬ", "заместитель", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        t.addVariant("ЗАМЕСТИТЕЛЬНИЦА", false);
        t.addAbridge("ЗАМ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2613("ЗАСТУПНИК", com.pullenti.morph.MorphLang.UA, "заступник", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        t.addVariant("ЗАСТУПНИЦЯ", false);
        t.addAbridge("ЗАМ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2612("УПОЛНОМОЧЕННЫЙ", "уполномоченный", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        t = PersonAttrTermin._new2613("УПОВНОВАЖЕНИЙ", com.pullenti.morph.MorphLang.UA, "уповноважений", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        t = PersonAttrTermin._new2612("ЭКС-УПОЛНОМОЧЕННЫЙ", "экс-уполномоченный", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        t = PersonAttrTermin._new2613("ЕКС-УПОВНОВАЖЕНИЙ", com.pullenti.morph.MorphLang.UA, "екс-уповноважений", PersonAttrTerminType2.IO2, PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        t = PersonAttrTermin._new2618("ИСПОЛНЯЮЩИЙ ОБЯЗАННОСТИ", PersonAttrTerminType2.IO, PersonAttrTerminType.POSITION);
        t.addAbridge("И.О.");
        t.setCanonicText((t.acronym = "ИО"));
        m_Termins.add(t);
        t = PersonAttrTermin._new2619("ВИКОНУЮЧИЙ ОБОВЯЗКИ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType2.IO, PersonAttrTerminType.POSITION);
        t.addAbridge("В.О.");
        t.setCanonicText((t.acronym = "ВО"));
        m_Termins.add(t);
        t = PersonAttrTermin._new2618("ВРЕМЕННО ИСПОЛНЯЮЩИЙ ОБЯЗАННОСТИ", PersonAttrTerminType2.IO, PersonAttrTerminType.POSITION);
        t.addAbridge("ВР.И.О.");
        t.setCanonicText((t.acronym = "ВРИО"));
        m_TerminVrio = t;
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("ЗАВЕДУЮЩИЙ", PersonAttrTerminType.POSITION);
        t.addAbridge("ЗАВЕД.");
        t.addAbridge("ЗАВ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("ЗАВІДУВАЧ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addAbridge("ЗАВІД.");
        t.addAbridge("ЗАВ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("СОТРУДНИК", PersonAttrTerminType.POSITION);
        t.addAbridge("СОТРУДН.");
        t.addAbridge("СОТР.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("СПІВРОБІТНИК", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addAbridge("СПІВРОБ.");
        t.addAbridge("СПІВ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("АКАДЕМИК", PersonAttrTerminType.POSITION);
        t.addAbridge("АКАД.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("АКАДЕМІК", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addAbridge("АКАД.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("ЧЛЕН-КОРРЕСПОНДЕНТ", PersonAttrTerminType.POSITION);
        t.addAbridge("ЧЛ.-КОРР.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("ЧЛЕН-КОРЕСПОНДЕНТ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addAbridge("ЧЛ.-КОР.");
        t.addAbridge("ЧЛ.-КОРР.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("ДОЦЕНТ", PersonAttrTerminType.POSITION);
        t.addAbridge("ДОЦ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("ПРОФЕССОР", PersonAttrTerminType.POSITION);
        t.addAbridge("ПРОФ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("ПРОФЕСОР", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        t.addAbridge("ПРОФ.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("PROFESSOR", com.pullenti.morph.MorphLang.EN, PersonAttrTerminType.POSITION);
        t.addAbridge("PROF.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2618("КАНДИДАТ", PersonAttrTerminType2.GRADE, PersonAttrTerminType.POSITION);
        t.addAbridge("КАНД.");
        t.addAbridge("КАН.");
        t.addAbridge("К-Т");
        t.addAbridge("К.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2618("ДОКТОР", PersonAttrTerminType2.GRADE, PersonAttrTerminType.POSITION);
        t.addAbridge("ДОКТ.");
        t.addAbridge("ДОК.");
        t.addAbridge("Д-Р");
        t.addAbridge("Д.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("DOCTOR", com.pullenti.morph.MorphLang.EN, PersonAttrTerminType.PREFIX);
        t.addAbridge("DR");
        t.addAbridge("DR.");
        m_Termins.add(t);
        t = PersonAttrTermin._new2601("ДОКТОРАНТ", PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        t = PersonAttrTermin._new2602("ДОКТОРАНТ", com.pullenti.morph.MorphLang.UA, PersonAttrTerminType.POSITION);
        m_Termins.add(t);
        for (String s : new String[] {"КФН", "КТН", "КХН"}) {
            t = PersonAttrTermin._new2638(s, "кандидат наук", PersonAttrTerminType.POSITION, PersonAttrTerminType2.ABBR);
            m_Termins.add(t);
        }
        for (String s : new String[] {"ГЛАВНЫЙ", "МЛАДШИЙ", "СТАРШИЙ", "ВЕДУЩИЙ", "НАУЧНЫЙ"}) {
            t = PersonAttrTermin._new2618(s, PersonAttrTerminType2.ADJ, PersonAttrTerminType.POSITION);
            t.addAllAbridges(0, 0, 2);
            m_Termins.add(t);
        }
        for (String s : new String[] {"ГОЛОВНИЙ", "МОЛОДШИЙ", "СТАРШИЙ", "ПРОВІДНИЙ", "НАУКОВИЙ"}) {
            t = PersonAttrTermin._new2640(s, PersonAttrTerminType2.ADJ, PersonAttrTerminType.POSITION, com.pullenti.morph.MorphLang.UA);
            t.addAllAbridges(0, 0, 2);
            m_Termins.add(t);
        }
        for (String s : new String[] {"НЫНЕШНИЙ", "НОВЫЙ", "CURRENT", "NEW"}) {
            t = PersonAttrTermin._new2618(s, PersonAttrTerminType2.IGNOREDADJ, PersonAttrTerminType.POSITION);
            m_Termins.add(t);
        }
        for (String s : new String[] {"НИНІШНІЙ", "НОВИЙ"}) {
            t = PersonAttrTermin._new2640(s, PersonAttrTerminType2.IGNOREDADJ, PersonAttrTerminType.POSITION, com.pullenti.morph.MorphLang.UA);
            m_Termins.add(t);
        }
        for (String s : new String[] {"ТОГДАШНИЙ", "БЫВШИЙ", "ПРЕДЫДУЩИЙ", "FORMER", "PREVIOUS", "THEN"}) {
            t = PersonAttrTermin._new2618(s, PersonAttrTerminType2.IO, PersonAttrTerminType.POSITION);
            m_Termins.add(t);
        }
        for (String s : new String[] {"ТОДІШНІЙ", "КОЛИШНІЙ"}) {
            t = PersonAttrTermin._new2640(s, PersonAttrTerminType2.IO, PersonAttrTerminType.POSITION, com.pullenti.morph.MorphLang.UA);
            m_Termins.add(t);
        }
        byte[] dat = ResourceHelper.getBytes("attr_ru.dat");
        if (dat == null) 
            throw new Exception("Not found resource file attr_ru.dat in Person analyzer");
        loadAttrs(m_Termins, dat, com.pullenti.morph.MorphLang.RU);
        if ((((dat = ResourceHelper.getBytes("attr_en.dat")))) == null) 
            throw new Exception("Not found resource file attr_en.dat in Person analyzer");
        loadAttrs(m_Termins, dat, com.pullenti.morph.MorphLang.EN);
        loadAttrs(m_Termins, ResourceHelper.getBytes("attr_ua.dat"), com.pullenti.morph.MorphLang.UA);
        m_TerminsGrade = new com.pullenti.ner.core.TerminCollection();
        String[] grFull = new String[] {"архитектуры", "биологических наук", "ветеринарных наук", "военных наук", "географических наук", "геолого-минералогических наук", "искусствоведения", "исторических наук", "культурологии", "медицинских наук", "педагогических наук", "политических наук", "психологических наук", "сельскохозяйственных наук", "социологических наук", "теологических наук", "технических наук", "фармацевтических наук", "физико-математических наук", "филологических наук", "философских наук", "химических наук", "экономических наук", "юридических наук"};
        String[] grSh1 = new String[] {"архитектуры", "биол. наук", "ветеринар. наук", "воен. наук", "геогр. наук", "геол.-минерал. наук", "искусствоведения", "ист. наук", "культурологии", "мед. наук", "пед. наук", "полит. наук", "психол. наук", "с.-х. наук", "социол. наук", "теол. наук", "техн. наук", "фарм. наук", "физ.-мат. наук", "филол. наук", "филос. наук", "хим. наук", "экон. наук", "юрид. наук"};
        String[] grSh2 = new String[] {"арх.", "б.н.", "вет.н.", "воен.н.", "г.н.", "г.-м.н.", "иск.", "и.н.", "культ.", "м.н.", "пед. н.", "полит. н.", "п. н.", "с.-х. н.", "социол. н.", "теол. н.", "т. н.", "фарм. н.", "ф.-м. н.", "ф. н.", "филос. н.", "х. н.", "э. н.", "ю. н."};
        for (int i = 0; i < grFull.length; i++) {
            t = new PersonAttrTermin(grFull[i].toUpperCase(), null);
            t.addAbridge(grSh1[i].toUpperCase());
            t.addAbridge(grSh2[i].toUpperCase());
            m_TerminsGrade.add(t);
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    private static PersonAttrTermin m_TerminVrio;

    private static com.pullenti.ner.core.TerminCollection m_TerminsGrade;

    private static byte[] deflate(byte[] zip) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.MemoryStream unzip = new com.pullenti.unisharp.MemoryStream()) {
            com.pullenti.unisharp.MemoryStream _data = new com.pullenti.unisharp.MemoryStream(zip);
            _data.setPosition(0L);
            com.pullenti.morph.internal.MorphDeserializer.deflateGzip(_data, unzip);
            _data.close();
            return unzip.toByteArray();
        }
    }

    private static void loadAttrs(com.pullenti.ner.core.TerminCollection termins, byte[] dat, com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (dat == null || dat.length == 0) 
            return;
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(deflate(dat))) {
            tmp.setPosition(0L);
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.load(tmp);
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                org.w3c.dom.Node a = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "v");
                if (a == null) 
                    continue;
                String val = a.getNodeValue();
                if (val == null) 
                    continue;
                String attrs = (com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "a") == null ? "" : ((String)com.pullenti.unisharp.Utils.notnull(com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "a").getTextContent(), "")));
                if (com.pullenti.unisharp.Utils.stringsEq(val, "ДЕВОЧКА")) {
                }
                PersonAttrTermin pat = PersonAttrTermin._new2645(val, PersonAttrTerminType.POSITION, lang);
                for (char ch : attrs.toCharArray()) {
                    if (ch == 'p') 
                        pat.canHasPersonAfter = 1;
                    else if (ch == 'P') 
                        pat.canHasPersonAfter = 2;
                    else if (ch == 's') 
                        pat.canBeSameSurname = true;
                    else if (ch == 'm') 
                        pat.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                    else if (ch == 'f') 
                        pat.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                    else if (ch == 'b') 
                        pat.isBoss = true;
                    else if (ch == 'r') 
                        pat.isMilitaryRank = true;
                    else if (ch == 'n') 
                        pat.isNation = true;
                    else if (ch == 'c') 
                        pat.typ = PersonAttrTerminType.KING;
                    else if (ch == 'q') 
                        pat.typ = PersonAttrTerminType.KING;
                    else if (ch == 'k') 
                        pat.isKin = true;
                    else if (ch == 'a') 
                        pat.typ2 = PersonAttrTerminType2.IO2;
                    else if (ch == '1') 
                        pat.canBeIndependant = true;
                    else if (ch == 'w') 
                        pat.isProfession = true;
                    else if (ch == 'd') 
                        pat.isPost = true;
                    else if (ch == '?') 
                        pat.isDoubt = true;
                }
                if (com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "alt") != null) {
                    pat.addVariant((val = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "alt").getTextContent()), false);
                    if (val.indexOf('.') > 0) 
                        pat.addAbridge(val);
                }
                if (x.getChildNodes().getLength() > 0) {
                    for (org.w3c.dom.Node xx : (new com.pullenti.unisharp.XmlNodeListWrapper(x.getChildNodes())).arr) {
                        if (com.pullenti.unisharp.Utils.stringsEq(xx.getNodeName(), "alt")) {
                            pat.addVariant((val = xx.getTextContent()), false);
                            if (val.indexOf('.') > 0) 
                                pat.addAbridge(val);
                        }
                    }
                }
                termins.add(pat);
            }
        }
    }

    public PersonAttrToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(null, begin, end, null);
        if(_globalInstance == null) return;
    }

    public PersonAttrTerminType typ = PersonAttrTerminType.PREFIX;

    public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;

    public String value;

    public String age;

    public com.pullenti.ner.person.PersonPropertyReferent getPropRef() {
        return (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.person.PersonPropertyReferent.class);
    }

    public com.pullenti.ner.person.PersonPropertyReferent setPropRef(com.pullenti.ner.person.PersonPropertyReferent _value) {
        referent = _value;
        return _value;
    }


    public PersonAttrToken higherPropRef;

    public boolean addOuterOrgAsRef = false;

    public com.pullenti.ner.Token anafor;

    public boolean canBeIndependentProperty() {
        if (getPropRef() == null) 
            return false;
        if (getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
            return false;
        if (higherPropRef != null && higherPropRef.canBeIndependentProperty()) 
            return true;
        if (canBeSinglePerson) 
            return true;
        if (typ != PersonAttrTerminType.POSITION) 
            return false;
        if (!m_CanBeIndependentProperty) {
            if (getPropRef().getKind() == com.pullenti.ner.person.PersonPropertyKind.BOSS) 
                return true;
            return false;
        }
        if (getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getPropRef().getName(), "член")) 
                return true;
        }
        return false;
    }

    public boolean setBeIndependentProperty(boolean _value) {
        m_CanBeIndependentProperty = _value;
        return _value;
    }


    private boolean m_CanBeIndependentProperty;

    public boolean canBeSinglePerson;

    public int canHasPersonAfter = 0;

    public boolean canBeSameSurname;

    public boolean isDoubt;

    @Override
    public String toString() {
        if (referent != null) 
            return super.toString();
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(": ").append(((value != null ? value : "")));
        if (getPropRef() != null) 
            res.append(" Ref: ").append(this.getPropRef().toString());
        if (gender != com.pullenti.morph.MorphGender.UNDEFINED) 
            res.append("; ").append(gender.toString());
        if (canHasPersonAfter >= 0) 
            res.append("; MayBePersonAfter=").append(canHasPersonAfter);
        if (canBeSameSurname) 
            res.append("; CanHasLikeSurname");
        if (m_CanBeIndependentProperty) 
            res.append("; CanBeIndependent");
        if (isDoubt) 
            res.append("; Doubt");
        if (age != null) 
            res.append("; Age=").append(age);
        if (!getMorph().getCase().isUndefined()) 
            res.append("; ").append(this.getMorph().getCase().toString());
        return res.toString();
    }

    @Override
    public void saveToLocalOntology() {
        com.pullenti.ner.core.AnalyzerData ad = data;
        if (ad == null || getPropRef() == null || higherPropRef == null) {
            super.saveToLocalOntology();
            return;
        }
        java.util.ArrayList<PersonAttrToken> li = new java.util.ArrayList<PersonAttrToken>();
        for (PersonAttrToken pr = this; pr != null && pr.getPropRef() != null; pr = pr.higherPropRef) {
            li.add(0, pr);
        }
        for (int i = 0; i < li.size(); i++) {
            li.get(i).data = ad;
            li.get(i).higherPropRef = null;
            li.get(i).saveToLocalOntology();
            if ((i + 1) < li.size()) 
                li.get(i + 1).getPropRef().setHigher(li.get(i).getPropRef());
        }
    }

    public static boolean SPEEDREGIME = false;

    public static void prepareAllData(com.pullenti.ner.Token t0) {
        if (!SPEEDREGIME) 
            return;
        PersonAnalyzerData ad = com.pullenti.ner.person.PersonAnalyzer.getData(t0);
        if (ad == null) 
            return;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            PersonTokenData d = (PersonTokenData)com.pullenti.unisharp.Utils.cast(t.tag, PersonTokenData.class);
            PersonAttrToken _typ = tryAttach(t, PersonAttrAttachAttrs.NO);
            if (_typ != null) {
                if (d == null) 
                    d = new PersonTokenData(t);
                d.attr = _typ;
            }
        }
    }

    public static PersonAttrToken tryAttach(com.pullenti.ner.Token t, PersonAttrAttachAttrs attrs) {
        if (t == null) 
            return null;
        PersonAnalyzerData ad = com.pullenti.ner.person.PersonAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (SPEEDREGIME && ad.aRegime && attrs == PersonAttrAttachAttrs.NO) {
            PersonTokenData d = (PersonTokenData)com.pullenti.unisharp.Utils.cast(t.tag, PersonTokenData.class);
            if (d != null) 
                return d.attr;
            return null;
        }
        if (ad.level > 2) 
            return null;
        ad.level++;
        PersonAttrToken res = _TryAttach(t, attrs);
        ad.level--;
        if (t.isValue("СПОРТСМЕНКА", null)) {
        }
        if (res == null) {
            if (t.getMorph()._getClass().isNoun()) {
                com.pullenti.ner.geo.GeoAnalyzer aterr = (com.pullenti.ner.geo.GeoAnalyzer)com.pullenti.unisharp.Utils.cast(t.kit.processor.findAnalyzer("GEO"), com.pullenti.ner.geo.GeoAnalyzer.class);
                if (aterr != null) {
                    com.pullenti.ner.ReferentToken rt = aterr.processCitizen(t);
                    if (rt != null) {
                        res = _new2646(rt.getBeginToken(), rt.getEndToken(), rt.getMorph());
                        res.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                        res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME, (t.kit.baseLanguage.isUa() ? "громадянин" : "гражданин"), true, 0);
                        res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rt.referent, true, 0);
                        res.getPropRef().addExtReferent(rt);
                        res.typ = PersonAttrTerminType.POSITION;
                        if ((res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ПО", null) && res.getEndToken().getNext().getNext() != null) && res.getEndToken().getNext().getNext().isValue("ПРОИСХОЖДЕНИЕ", null)) 
                            res.setEndToken(res.getEndToken().getNext().getNext());
                        return res;
                    }
                }
            }
            if ((((t instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "АК") && t.getNext() != null) && t.getNext().isChar('.') && t.getNext().getNext() != null) && !t.getNext().getNext().chars.isAllLower()) {
                res = _new2647(t, t.getNext(), PersonAttrTerminType.POSITION);
                res.setPropRef(com.pullenti.ner.person.PersonPropertyReferent._new2648("академик"));
                return res;
            }
            if ((t instanceof com.pullenti.ner.TextToken) && t.getNext() != null) {
                if (((t.isValue("ВИЦЕ", "ВІЦЕ") || t.isValue("ЭКС", "ЕКС") || t.isValue("ГЕН", null)) || t.isValue("VICE", null) || t.isValue("EX", null)) || t.isValue("DEPUTY", null)) {
                    com.pullenti.ner.Token tt = t.getNext();
                    if (tt.isHiphen() || tt.isChar('.')) 
                        tt = tt.getNext();
                    res = _TryAttach(tt, attrs);
                    if (res != null && res.getPropRef() != null) {
                        res.setBeginToken(t);
                        if (t.isValue("ГЕН", null)) 
                            res.getPropRef().setName(("генеральный " + res.getPropRef().getName()));
                        else 
                            res.getPropRef().setName((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.toLowerCase() + "-" + res.getPropRef().getName()));
                        return res;
                    }
                }
            }
            if (t.isValue("ГВАРДИИ", "ГВАРДІЇ")) {
                res = _TryAttach(t.getNext(), attrs);
                if (res != null) {
                    if (res.getPropRef() != null && res.getPropRef().getKind() == com.pullenti.ner.person.PersonPropertyKind.MILITARYRANK) {
                        res.setBeginToken(t);
                        return res;
                    }
                }
            }
            com.pullenti.ner.Token tt1 = t;
            if (tt1.getMorph()._getClass().isPreposition() && tt1.getNext() != null) 
                tt1 = tt1.getNext();
            if ((tt1.getNext() != null && tt1.isValue("НАЦИОНАЛЬНОСТЬ", "НАЦІОНАЛЬНІСТЬ")) || tt1.isValue("ПРОФЕССИЯ", "ПРОФЕСІЯ") || tt1.isValue("СПЕЦИАЛЬНОСТЬ", "СПЕЦІАЛЬНІСТЬ")) {
                tt1 = tt1.getNext();
                if (tt1 != null) {
                    if (tt1.isHiphen() || tt1.isChar(':')) 
                        tt1 = tt1.getNext();
                }
                res = _TryAttach(tt1, attrs);
                if (res != null) {
                    res.setBeginToken(t);
                    return res;
                }
            }
            return null;
        }
        if (res.typ == PersonAttrTerminType.OTHER && res.age != null && res.value == null) {
            PersonAttrToken res1 = _TryAttach(res.getEndToken().getNext(), attrs);
            if (res1 != null) {
                res1.setBeginToken(res.getBeginToken());
                res1.age = res.age;
                res = res1;
            }
        }
        if (res.getBeginToken().isValue("ГЛАВА", null)) {
            if (t.getPrevious() instanceof com.pullenti.ner.NumberToken) 
                return null;
        }
        else if (res.getBeginToken().isValue("АДВОКАТ", null)) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("РЕЕСТР", "РЕЄСТР") || t.getPrevious().isValue("УДОСТОВЕРЕНИЕ", "ПОСВІДЧЕННЯ")) 
                    return null;
            }
        }
        com.pullenti.morph.MorphClass mc = res.getBeginToken().getMorphClassInDictionary();
        if (mc.isAdjective()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getEndChar() > res.getEndChar()) {
                if (m_Termins.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) == null && npt.getEndToken().chars.isAllLower()) 
                    return null;
            }
        }
        if (res.typ == PersonAttrTerminType.PREFIX && ((((((com.pullenti.unisharp.Utils.stringsEq(res.value, "ГРАЖДАНИН") || com.pullenti.unisharp.Utils.stringsEq(res.value, "ГРАЖДАНКА") || com.pullenti.unisharp.Utils.stringsEq(res.value, "УРОЖЕНЕЦ")) || com.pullenti.unisharp.Utils.stringsEq(res.value, "УРОЖЕНКА") || com.pullenti.unisharp.Utils.stringsEq(res.value, "ВЫХОДЕЦ ИЗ")) || com.pullenti.unisharp.Utils.stringsEq(res.value, "ВИХОДЕЦЬ З") || com.pullenti.unisharp.Utils.stringsEq(res.value, "ГРОМАДЯНИН")) || com.pullenti.unisharp.Utils.stringsEq(res.value, "ГРОМАДЯНКА") || com.pullenti.unisharp.Utils.stringsEq(res.value, "УРОДЖЕНЕЦЬ")) || com.pullenti.unisharp.Utils.stringsEq(res.value, "УРОДЖЕНКА"))) && res.getEndToken().getNext() != null) {
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            if (((tt != null && tt.isChar('(') && tt.getNext() != null) && tt.getNext().isValue("КА", null) && tt.getNext().getNext() != null) && tt.getNext().getNext().isChar(')')) {
                res.setEndToken(tt.getNext().getNext());
                tt = res.getEndToken().getNext();
            }
            com.pullenti.ner.Referent r = (tt == null ? null : tt.getReferent());
            if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo)) {
                res.setEndToken(tt);
                res.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME, res.value.toLowerCase(), true, 0);
                res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, true, 0);
                res.typ = PersonAttrTerminType.POSITION;
                for (com.pullenti.ner.Token ttt = tt.getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (!ttt.isCommaAnd() || ttt.getNext() == null) 
                        break;
                    ttt = ttt.getNext();
                    r = ttt.getReferent();
                    if (r == null || com.pullenti.unisharp.Utils.stringsNe(r.getTypeName(), objNameGeo)) 
                        break;
                    res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                    res.setEndToken((tt = ttt));
                    if (ttt.getPrevious().isAnd()) 
                        break;
                }
                if (((res.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && (res.getWhitespacesAfterCount() < 3) && res.getEndToken().getNext().getReferent() != null) && com.pullenti.unisharp.Utils.stringsEq(res.getEndToken().getNext().getReferent().getTypeName(), objNameGeo)) {
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class), (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class), null, null)) {
                        res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, res.getEndToken().getNext().getReferent(), false, 0);
                        res.setEndToken(res.getEndToken().getNext());
                    }
                }
            }
            else if ((tt != null && tt.isAnd() && tt.getNext() != null) && tt.getNext().isValue("ЖИТЕЛЬ", null)) {
                PersonAttrToken aaa = _TryAttach(tt.getNext(), attrs);
                if (aaa != null && aaa.getPropRef() != null) {
                    aaa.setBeginToken(res.getBeginToken());
                    aaa.value = res.value;
                    aaa.getPropRef().setName(aaa.value.toLowerCase());
                    res = aaa;
                }
            }
            else {
                com.pullenti.ner.Token tt2 = tt;
                if (tt2.isCommaAnd()) 
                    tt2 = tt2.getNext();
                PersonAttrToken nex = _TryAttach(tt2, attrs);
                if (nex != null && nex.getPropRef() != null) {
                    for (com.pullenti.ner.Slot sss : nex.getPropRef().getSlots()) {
                        if (sss.getValue() instanceof com.pullenti.ner.geo.GeoReferent) {
                            if (res.getPropRef() == null) 
                                res.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                            res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME, res.value.toLowerCase(), false, 0);
                            res.getPropRef().addSlot(sss.getTypeName(), sss.getValue(), false, 0);
                            res.typ = PersonAttrTerminType.POSITION;
                        }
                    }
                }
            }
        }
        if (res.typ == PersonAttrTerminType.KING || res.typ == PersonAttrTerminType.POSITION) {
            if (res.getBeginToken() == res.getEndToken() && res.chars.isCapitalUpper() && res.getWhitespacesAfterCount() == 1) {
                PersonItemToken pit = PersonItemToken.tryAttach(t, PersonItemToken.ParseAttr.IGNOREATTRS, null);
                if (pit != null && pit.lastname != null && pit.lastname.isLastnameHasStdTail) {
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(t.getNext(), null);
                    if (rt1 != null && (rt1.referent instanceof com.pullenti.ner.person.PersonReferent)) {
                    }
                    else if ((((attrs.value()) & (PersonAttrAttachAttrs.INPROCESS.value()))) != (PersonAttrAttachAttrs.NO.value())) {
                    }
                    else 
                        return null;
                }
            }
        }
        if (res.getPropRef() == null) 
            return res;
        if (res.chars.isLatinLetter()) {
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            if (tt != null && tt.isHiphen()) 
                tt = tt.getNext();
            if (tt != null && tt.isValue("ELECT", null)) 
                res.setEndToken(tt);
        }
        if (!res.getBeginToken().chars.isAllLower()) {
            PersonItemToken pat = PersonItemToken.tryAttach(res.getBeginToken(), PersonItemToken.ParseAttr.IGNOREATTRS, null);
            if (pat != null && pat.lastname != null) {
                if (pat.lastname.isInDictionary || pat.lastname.isInOntology) {
                    if (checkKind(res.getPropRef()) != com.pullenti.ner.person.PersonPropertyKind.KING) 
                        return null;
                }
            }
        }
        String s = res.getPropRef().toString();
        if (com.pullenti.unisharp.Utils.stringsEq(s, "глава книги")) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(s, "глава") && res.getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null) 
            return null;
        if (((com.pullenti.unisharp.Utils.stringsEq(s, "королева") || com.pullenti.unisharp.Utils.stringsEq(s, "король") || com.pullenti.unisharp.Utils.stringsEq(s, "князь"))) && res.chars.isCapitalUpper()) {
            java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(res.getEndToken().getNext(), PersonItemToken.ParseAttr.NO, 10);
            if (pits != null && pits.size() > 0) {
                if (pits.get(0).typ == PersonItemToken.ItemType.INITIAL) 
                    return null;
                if (pits.get(0).firstname != null) {
                    if (pits.size() == 1) 
                        return null;
                    if (pits.size() == 2 && pits.get(1).middlename != null) 
                        return null;
                }
            }
            if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(s, "друг") || s.startsWith("друг ")) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("ДРУГ", null)) 
                    return null;
                if (t.getPrevious().getMorph()._getClass().isPreposition() && t.getPrevious().getPrevious() != null && t.getPrevious().getPrevious().isValue("ДРУГ", null)) 
                    return null;
            }
            if (t.getNext() != null) {
                if (t.getNext().isValue("ДРУГ", null)) 
                    return null;
                if (t.getNext().getMorph()._getClass().isPreposition() && t.getNext().getNext() != null && t.getNext().getNext().isValue("ДРУГ", null)) 
                    return null;
            }
        }
        if (res.chars.isLatinLetter() && ((res.isDoubt || com.pullenti.unisharp.Utils.stringsEq(s, "senior"))) && (res.getWhitespacesAfterCount() < 2)) {
            if (res.getPropRef() != null && res.getPropRef().getSlots().size() == 1) {
                com.pullenti.ner.Token tt2 = res.getEndToken().getNext();
                if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(tt2)) 
                    tt2 = tt2.getNext().getNext();
                PersonAttrToken res2 = _TryAttach(tt2, attrs);
                if ((res2 != null && res2.chars.isLatinLetter() && res2.typ == res.typ) && res2.getPropRef() != null) {
                    res2.getPropRef().setName((((String)com.pullenti.unisharp.Utils.notnull(res.getPropRef().getName(), "")) + " " + ((String)com.pullenti.unisharp.Utils.notnull(res2.getPropRef().getName(), ""))).trim());
                    res2.setBeginToken(res.getBeginToken());
                    res = res2;
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(res.getPropRef().getName(), "министр")) {
            com.pullenti.ner.ReferentToken rt1 = res.kit.processReferent("ORGANIZATION", res.getEndToken().getNext(), null);
            if (rt1 != null && rt1.referent.findSlot("TYPE", "министерство", true) != null) {
                com.pullenti.ner.Token t1 = rt1.getEndToken();
                if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    t1 = t1.getPrevious();
                if (rt1.getBeginChar() < t1.getEndChar()) {
                    String addStr = com.pullenti.ner.core.MiscHelper.getTextValue(rt1.getBeginToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
                    if (addStr != null) {
                        res.getPropRef().setName(res.getPropRef().getName() + (" " + addStr.toLowerCase()));
                        res.setEndToken(t1);
                    }
                }
            }
        }
        for (com.pullenti.ner.person.PersonPropertyReferent p = res.getPropRef(); p != null; p = p.getHigher()) {
            if (p.getName() != null && (p.getName().indexOf(" - ") >= 0)) 
                p.setName(p.getName().replace(" - ", "-"));
        }
        if (res.getBeginToken().getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.ReferentToken r = res.kit.processReferent("GEO", res.getBeginToken(), null);
            if (r != null) {
                res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r.referent, false, 0);
                res.getPropRef().addExtReferent(r);
                int i = res.getPropRef().getName().indexOf(' ');
                if (i > 0) 
                    res.getPropRef().setName(res.getPropRef().getName().substring(i).trim());
            }
        }
        if (res.getPropRef() != null && res.getPropRef().getKind() == com.pullenti.ner.person.PersonPropertyKind.KING) {
            com.pullenti.ner.Token t1 = res.getEndToken().getNext();
            if (com.pullenti.unisharp.Utils.stringsEq(res.getPropRef().getName(), "отец")) {
                if (t1 == null || !t1.chars.isCapitalUpper()) 
                    return null;
                if (((com.pullenti.morph.MorphCase.ooBitand(res.getMorph().getCase(), t1.getMorph().getCase()))).isUndefined()) 
                    return null;
                res.getPropRef().setName("священник");
                return res;
            }
            if (t1 != null && t1.chars.isCapitalUpper() && (t1.getWhitespacesBeforeCount() < 3)) {
                java.util.ArrayList<String> adjs = new java.util.ArrayList<String>();
                boolean _isAnd = false;
                com.pullenti.ner.Token t2 = null;
                for (com.pullenti.ner.Token tt = t1; tt != null; tt = tt.getNext()) {
                    if (((tt.isValue("ВСЕЙ", null) || tt.isValue("ВСЕЯ", "ВСІЄЇ"))) && (tt.getNext() instanceof com.pullenti.ner.ReferentToken) && (tt.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        adjs.add(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt.getNext(), com.pullenti.ner.core.GetTextAttr.NO)));
                        t2 = (tt = tt.getNext());
                    }
                    else if ((tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class)).getEndToken().getMorph()._getClass().isAdjective()) {
                        String v = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO);
                        adjs.add(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(v));
                        tt = (t2 = tt);
                    }
                    else {
                        if (!tt.chars.isCapitalUpper()) 
                            break;
                        PersonItemToken pit = PersonItemToken.tryAttach(tt, PersonItemToken.ParseAttr.IGNOREATTRS, null);
                        if (pit == null || pit.value == null || pit.firstname != null) 
                            break;
                        if (pit.lastname == null && !pit.value.endsWith("КИЙ")) 
                            break;
                        String v = null;
                        if (pit.lastname == null) 
                            v = pit.value;
                        else 
                            for (PersonItemToken.MorphPersonItemVariant fv : pit.lastname.vars) {
                                if (fv.value.endsWith("КИЙ")) {
                                    v = fv.value;
                                    break;
                                }
                            }
                        if (v == null) 
                            break;
                        adjs.add(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(v));
                        tt = (t2 = pit.getEndToken());
                    }
                    if (tt.getNext() == null) 
                        break;
                    tt = tt.getNext();
                    if (!tt.isCommaAnd()) 
                        break;
                    _isAnd = tt.isAnd();
                }
                if (adjs.size() > 1 && !_isAnd) {
                }
                else if (t2 != null) {
                    for (int i = 0; i < adjs.size(); i++) {
                        if (i > 0) 
                            res.getPropRef().setName((res.getPropRef().getName() + ((i + 1) < adjs.size() ? "," : " и")));
                        res.getPropRef().setName((res.getPropRef().getName() + " " + adjs.get(i)));
                    }
                    res.setEndToken(t2);
                }
            }
        }
        boolean containsGeo = false;
        for (com.pullenti.ner.Slot ss : res.getPropRef().getSlots()) {
            if (ss.getValue() instanceof com.pullenti.ner.Referent) {
                if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.Referent.class)).getTypeName(), objNameGeo)) {
                    containsGeo = true;
                    break;
                }
            }
        }
        if (!containsGeo && (res.getEndToken().getWhitespacesAfterCount() < 2)) {
            if ((res.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && com.pullenti.unisharp.Utils.stringsEq(res.getEndToken().getNext().getReferent().getTypeName(), objNameGeo)) {
                res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, res.getEndToken().getNext().getReferent(), false, 0);
                res.setEndToken(res.getEndToken().getNext());
            }
        }
        if (res.getEndToken().getWhitespacesAfterCount() < 2) {
            com.pullenti.ner.Token te = res.getEndToken().getNext();
            if (te != null && te.isValue("В", null)) {
                te = te.getNext();
                if ((te instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.unisharp.Utils.stringsEq(te.getReferent().getTypeName(), objNameDate) || com.pullenti.unisharp.Utils.stringsEq(te.getReferent().getTypeName(), objNameDateRange)))) 
                    res.setEndToken(te);
            }
            else if (te != null && te.isChar('(')) {
                te = te.getNext();
                if (((te instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.unisharp.Utils.stringsEq(te.getReferent().getTypeName(), objNameDate) || com.pullenti.unisharp.Utils.stringsEq(te.getReferent().getTypeName(), objNameDateRange))) && te.getNext() != null) && te.getNext().isChar(')')) 
                    res.setEndToken(te.getNext());
                else if (te instanceof com.pullenti.ner.NumberToken) {
                    com.pullenti.ner.ReferentToken rt1 = te.kit.processReferent("DATE", te, null);
                    if (rt1 != null && rt1.getEndToken().getNext() != null && rt1.getEndToken().getNext().isChar(')')) 
                        res.setEndToken(rt1.getEndToken().getNext());
                }
            }
        }
        if (res.getPropRef() != null && com.pullenti.unisharp.Utils.stringsEq(res.getPropRef().getName(), "отец")) {
            boolean isKing = false;
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.getMorphClassInDictionary().isProperName()) {
                if (!((com.pullenti.morph.MorphCase.ooBitand(res.getMorph().getCase(), tt.getMorph().getCase()))).isUndefined()) {
                    if (!tt.getMorph().getCase().isGenitive()) 
                        isKing = true;
                }
            }
            if (isKing) 
                res.getPropRef().setName("священник");
        }
        if (res.canHasPersonAfter > 0 && res.getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);{
                    com.pullenti.ner.Token tt0 = res.getBeginToken();
                    boolean have = false;
                    if ((tt0 instanceof com.pullenti.ner.TextToken) && tt0.getMorph()._getClass().isPersonalPronoun() && ((tt0.isValue("ОН", null) || tt0.isValue("ОНА", null)))) {
                    }
                    else {
                        tt0 = tt0.getPrevious();
                        if ((tt0 instanceof com.pullenti.ner.TextToken) && tt0.getMorph()._getClass().isPersonalPronoun() && ((tt0.isValue("ОН", null) || tt0.isValue("ОНА", null)))) {
                        }
                        else if ((tt0 instanceof com.pullenti.ner.TextToken) && tt0.getMorph()._getClass().isPronoun() && tt0.isValue("СВОЙ", null)) {
                        }
                        else if ((tt0 instanceof com.pullenti.ner.TextToken) && ((tt0.isValue("ИМЕТЬ", null) || ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt0, com.pullenti.ner.TextToken.class)).isVerbBe()))) 
                            have = true;
                        else 
                            tt0 = null;
                    }
                    if (tt0 != null) {
                        com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
                        int cou = 0;
                        if (!have) {
                            for (com.pullenti.morph.MorphBaseInfo wf : tt0.getMorph().getItems()) {
                                if (wf._getClass().isPersonalPronoun() || wf._getClass().isPronoun()) {
                                    if ((((gen = wf.getGender()))) == com.pullenti.morph.MorphGender.NEUTER) 
                                        gen = com.pullenti.morph.MorphGender.MASCULINE;
                                    break;
                                }
                            }
                        }
                        for (com.pullenti.ner.Token tt = tt0.getPrevious(); tt != null && (cou < 200); tt = tt.getPrevious(),cou++) {
                            com.pullenti.ner.person.PersonPropertyReferent pr = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.person.PersonPropertyReferent.class);
                            if (pr != null) {
                                if (((short)((tt.getMorph().getGender().value()) & (gen.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                    continue;
                                break;
                            }
                            com.pullenti.ner.person.PersonReferent p = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.person.PersonReferent.class);
                            if (p == null) 
                                continue;
                            if (have && (cou < 10)) {
                            }
                            else if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
                                if (p.isMale() && !p.isFemale()) 
                                    continue;
                            }
                            else if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
                                if (p.isFemale() && !p.isMale()) 
                                    continue;
                            }
                            else 
                                break;
                            res.setBeginToken((have ? tt0.getNext() : tt0));
                            res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, p, false, 0);
                            res.setBeIndependentProperty(true);
                            if (res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
                                res.canBeSinglePerson = true;
                            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt != null && npt.getBeginToken() != npt.getEndToken()) 
                                res.setMorph(npt.getMorph());
                            break;
                        }
                    }
                    else if (res.getWhitespacesAfterCount() == 1) {
                        com.pullenti.ner.person.PersonAnalyzer pa = (com.pullenti.ner.person.PersonAnalyzer)com.pullenti.unisharp.Utils.cast(res.kit.processor.findAnalyzer("PERSON"), com.pullenti.ner.person.PersonAnalyzer.class);
                        if (pa != null) {
                            com.pullenti.ner.Token t1 = res.getEndToken().getNext();
                            com.pullenti.ner.ReferentToken pr = com.pullenti.ner.person.PersonAnalyzer.tryAttachPerson(t1, false, 0, true);
                            if (pr != null && res.canHasPersonAfter == 1) {
                                if (pr.getBeginToken() == t1) {
                                    if (!pr.getMorph().getCase().isGenitive() && !pr.getMorph().getCase().isUndefined()) 
                                        pr = null;
                                    else if (!pr.getMorph().getCase().isUndefined() && !((com.pullenti.morph.MorphCase.ooBitand(res.getMorph().getCase(), pr.getMorph().getCase()))).isUndefined()) {
                                        if (com.pullenti.ner.person.PersonAnalyzer.tryAttachPerson(pr.getEndToken().getNext(), false, 0, true) != null) {
                                        }
                                        else 
                                            pr = null;
                                    }
                                }
                                else if (pr.getBeginToken().getPrevious() == t1) {
                                    pr = null;
                                    res.getPropRef().setName((res.getPropRef().getName() + " " + t1.getSourceText().toLowerCase()));
                                    res.setEndToken(t1);
                                }
                                else 
                                    pr = null;
                            }
                            else if (pr != null && res.canHasPersonAfter == 2) {
                                java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(t1, PersonItemToken.ParseAttr.NO, 10);
                                if (((pits != null && pits.size() > 1 && pits.get(0).firstname != null) && pits.get(1).firstname != null && pr.getEndChar() > pits.get(0).getEndChar()) && pits.get(0).getMorph().getCase().isGenitive()) {
                                    pr = null;
                                    int cou = 100;
                                    for (com.pullenti.ner.Token tt = t1.getPrevious(); tt != null && cou > 0; tt = tt.getPrevious(),cou--) {
                                        com.pullenti.ner.person.PersonReferent p0 = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.person.PersonReferent.class);
                                        if (p0 == null) 
                                            continue;
                                        for (PersonItemToken.MorphPersonItemVariant v : pits.get(0).firstname.vars) {
                                            if (p0.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, v.value, true) != null) {
                                                pr = new com.pullenti.ner.ReferentToken(p0, t1, pits.get(0).getEndToken(), null);
                                                break;
                                            }
                                        }
                                        if (pr != null) 
                                            break;
                                    }
                                }
                                else if (pits != null && pits.size() == 3 && ((pits.get(2).middlename != null || ((pits.get(1).typ == PersonItemToken.ItemType.INITIAL && pits.get(2).typ == PersonItemToken.ItemType.INITIAL))))) {
                                    com.pullenti.ner.Token tt = pits.get(2).getEndToken().getNext();
                                    if (tt != null && ((tt.isHiphen() || tt.isChar('(')))) {
                                        java.util.ArrayList<PersonItemToken> pits2 = PersonItemToken.tryAttachList(tt.getNext(), PersonItemToken.ParseAttr.NO, 10);
                                        if (pits2 != null && pits2.size() > 0) {
                                        }
                                        else 
                                            pr = null;
                                    }
                                    else 
                                        pr = null;
                                }
                            }
                            if (pr != null) {
                                res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, pr, false, 0);
                                res.setEndToken(pr.getEndToken());
                                res.setBeIndependentProperty(true);
                                if (res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
                                    res.canBeSinglePerson = true;
                            }
                        }
                    }
                }
        }
        if (res.getPropRef().getHigher() == null && res.getPropRef().getKind() == com.pullenti.ner.person.PersonPropertyKind.BOSS && res.getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null) {
            com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(res.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && tok.getEndToken() == res.getEndToken()) {
                int cou = 0;
                java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
                for (com.pullenti.ner.Token tt = tok.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.getWhitespacesAfterCount() > 15) 
                        break;
                    if (tt.isNewlineAfter()) 
                        cou += 10;
                    if ((++cou) > 1000) 
                        break;
                    if (!(tt instanceof com.pullenti.ner.ReferentToken)) 
                        continue;
                    java.util.ArrayList<com.pullenti.ner.Referent> li = tt.getReferents();
                    if (li == null) 
                        continue;
                    boolean breaks = false;
                    for (com.pullenti.ner.Referent r : li) {
                        if (((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO"))) && r.getParentReferent() == null) {
                            if (!refs.contains(r)) {
                                if (res.getPropRef().canHasRef(r)) 
                                    refs.add(r);
                            }
                        }
                        else if (r instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                            if (((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.person.PersonPropertyReferent.class)).findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) != null) 
                                breaks = true;
                        }
                        else if (r instanceof com.pullenti.ner.person.PersonReferent) 
                            breaks = true;
                    }
                    if (refs.size() > 1 || breaks) 
                        break;
                }
                if (refs.size() == 1) {
                    res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, refs.get(0), false, 0);
                    res.addOuterOrgAsRef = true;
                }
            }
        }
        if (res.chars.isLatinLetter() && res.getPropRef() != null && res.getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null) {
            if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isValue("S", null)) {
                if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(res.getBeginToken().getPrevious().getPrevious()) && (res.getBeginToken().getPrevious().getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken)) {
                    res.setBeginToken(res.getBeginToken().getPrevious().getPrevious().getPrevious());
                    res.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, res.getBeginToken().getReferent(), false, 0);
                }
            }
        }
        if (res.chars.isLatinLetter() && res.getPropRef() != null && (res.getWhitespacesAfterCount() < 2)) {
            PersonAttrToken rnext = tryAttach(res.getEndToken().getNext(), PersonAttrAttachAttrs.NO);
            if ((rnext != null && rnext.chars.isLatinLetter() && rnext.getPropRef() != null) && rnext.getPropRef().getSlots().size() == 1 && rnext.canHasPersonAfter > 0) {
                res.setEndToken(rnext.getEndToken());
                res.getPropRef().setName((res.getPropRef().getName() + " " + rnext.getPropRef().getName()));
            }
        }
        return res;
    }

    private static PersonAttrToken _TryAttach(com.pullenti.ner.Token t, PersonAttrAttachAttrs attrs) {
        if (t == null) 
            return null;
        if (t.getMorph()._getClass().isPronoun() && (((t.isValue("ЕГО", "ЙОГО") || t.isValue("ЕЕ", "ЇЇ") || t.isValue("HIS", null)) || t.isValue("HER", null)))) {
            PersonAttrToken res1 = tryAttach(t.getNext(), attrs);
            if (res1 != null && res1.getPropRef() != null) {
                int k = 0;
                for (com.pullenti.ner.Token tt2 = t.getPrevious(); tt2 != null && (k < 10); tt2 = tt2.getPrevious(),k++) {
                    com.pullenti.ner.Referent r = tt2.getReferent();
                    if (r == null) 
                        continue;
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg) || (r instanceof com.pullenti.ner.person.PersonReferent)) {
                        boolean ok = false;
                        if (t.isValue("ЕЕ", "ЇЇ") || t.isValue("HER", null)) {
                            if (tt2.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                                ok = true;
                        }
                        else if (((short)((tt2.getMorph().getGender().value()) & ((short)((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.NEUTER.value()))))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            ok = true;
                        if (ok) {
                            res1.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                            res1.setBeginToken(t);
                            return res1;
                        }
                        break;
                    }
                }
            }
            return null;
        }
        com.pullenti.ner.NumberToken nta = com.pullenti.ner.core.NumberHelper.tryParseAge(t);
        if (nta != null) {
            if (nta.getMorph()._getClass().isAdjective() || ((t.getPrevious() != null && t.getPrevious().isComma())) || ((nta.getEndToken().getNext() != null && nta.getEndToken().getNext().isCharOf(",.")))) 
                return _new2649(t, nta.getEndToken(), PersonAttrTerminType.OTHER, nta.getValue().toString(), nta.getMorph());
        }
        if (t.isNewlineBefore()) {
            com.pullenti.ner.mail.internal.MailLine li = com.pullenti.ner.mail.internal.MailLine.parse(t, 0, 0);
            if (li != null && li.typ == com.pullenti.ner.mail.internal.MailLine.Types.BESTREGARDS) 
                return _new2651(li.getBeginToken(), li.getEndToken(), PersonAttrTerminType.BESTREGARDS, com.pullenti.ner.MorphCollection._new2650(com.pullenti.morph.MorphCase.NOMINATIVE));
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) {
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (nt != null) {
                if (((com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "1") || com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "2") || com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "3"))) && nt.getMorph()._getClass().isAdjective()) {
                    PersonAttrToken pat0 = _TryAttach(t.getNext(), attrs);
                    if (pat0 != null && pat0.getPropRef() != null) {
                        pat0.setBeginToken(t);
                        for (com.pullenti.ner.Slot s : pat0.getPropRef().getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME)) {
                                if ((s.getValue().toString().indexOf("глава") >= 0)) 
                                    return null;
                                pat0.getPropRef().uploadSlot(s, ((pat0.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || t.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE ? (com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "1") ? "первая" : (com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "2") ? "вторая" : "третья")) : (com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "1") ? "первый" : (com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "2") ? "второй" : "третий"))) + " " + s.getValue()));
                            }
                        }
                        return pat0;
                    }
                }
            }
            com.pullenti.ner.Referent rr = null;
            if (t != null) 
                rr = t.getReferent();
            if (rr != null && (((rr instanceof com.pullenti.ner.geo.GeoReferent) || com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "ORGANIZATION")))) {
                com.pullenti.ner.Token ttt = t.getNext();
                if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(ttt)) 
                    ttt = ttt.getNext().getNext();
                if ((ttt instanceof com.pullenti.ner.TextToken) && ttt.getMorph().getLanguage().isEn() && (ttt.getWhitespacesBeforeCount() < 2)) {
                    PersonAttrToken res0 = _TryAttach(ttt, attrs);
                    if (res0 != null && res0.getPropRef() != null) {
                        res0.setBeginToken(t);
                        res0.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, t.getReferent(), false, 0);
                        return res0;
                    }
                }
            }
            if ((rr instanceof com.pullenti.ner.person.PersonReferent) && com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(t.getNext())) {
                PersonAttrToken res0 = _TryAttach(t.getNext().getNext().getNext(), attrs);
                if (res0 != null && res0.getPropRef() != null && res0.chars.isLatinLetter()) {
                    res0.setBeginToken(t);
                    res0.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, t.getReferent(), false, 0);
                    return res0;
                }
            }
            return null;
        }
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) {
            PersonAttrToken res0 = _TryAttach(t.getNext(), attrs);
            if (res0 != null) {
                res0.setBeginToken(t);
                return res0;
            }
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(tt.term, "Г") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ГР") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "М")) || com.pullenti.unisharp.Utils.stringsEq(tt.term, "Д")) {
            if (tt.getNext() != null && tt.getNext().isHiphen() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                String pref = tt.term;
                String tail = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class)).term;
                java.util.ArrayList<com.pullenti.morph.MorphWordForm> vars = null;
                if (com.pullenti.unisharp.Utils.stringsEq(pref, "Г")) 
                    vars = getStdForms(tail, "ГОСПОДИН", "ГОСПОЖА");
                else if (com.pullenti.unisharp.Utils.stringsEq(pref, "ГР")) 
                    vars = getStdForms(tail, "ГРАЖДАНИН", "ГРАЖДАНКА");
                else if (com.pullenti.unisharp.Utils.stringsEq(pref, "М")) 
                    vars = getStdForms(tail, "МИСТЕР", null);
                else if (com.pullenti.unisharp.Utils.stringsEq(pref, "Д")) {
                    if (_findGradeLast(tt.getNext().getNext().getNext(), tt) != null) {
                    }
                    else 
                        vars = getStdForms(tail, "ДОКТОР", null);
                }
                if (vars != null) {
                    PersonAttrToken res = _new2647(tt, tt.getNext().getNext(), PersonAttrTerminType.PREFIX);
                    for (com.pullenti.morph.MorphWordForm v : vars) {
                        res.getMorph().addItem(v);
                        if (res.value == null) {
                            res.value = v.normalCase;
                            res.gender = v.getGender();
                        }
                    }
                    return res;
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ГР") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ГРАЖД")) {
            com.pullenti.ner.Token t1 = tt;
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                t1 = tt.getNext();
            if (t1.getNext() instanceof com.pullenti.ner.NumberToken) 
                return null;
            return _new2653(tt, t1, PersonAttrTerminType.PREFIX, (tt.getMorph().getLanguage().isUa() ? "ГРОМАДЯНИН" : "ГРАЖДАНИН"));
        }
        com.pullenti.ner.core.NounPhraseToken npt0 = null;
        for (int step = 0; step < 2; step++) {
            java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks = m_Termins.tryParseAll(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (toks == null && t.isValue("ВРИО", null)) {
                toks = new java.util.ArrayList<com.pullenti.ner.core.TerminToken>();
                toks.add(com.pullenti.ner.core.TerminToken._new424(t, t, m_TerminVrio));
            }
            else if (toks == null && (t instanceof com.pullenti.ner.TextToken) && t.getMorph().getLanguage().isEn()) {
                String str = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (str.endsWith("MAN") || str.endsWith("PERSON") || str.endsWith("MIST")) {
                    toks = new java.util.ArrayList<com.pullenti.ner.core.TerminToken>();
                    toks.add(com.pullenti.ner.core.TerminToken._new424(t, t, PersonAttrTermin._new2602(str, t.getMorph().getLanguage(), PersonAttrTerminType.POSITION)));
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(str, "MODEL") && (t.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(t.getNext(), null);
                    if (rt != null && (rt.referent instanceof com.pullenti.ner.person.PersonReferent)) {
                        toks = new java.util.ArrayList<com.pullenti.ner.core.TerminToken>();
                        toks.add(com.pullenti.ner.core.TerminToken._new424(t, t, PersonAttrTermin._new2602(str, t.getMorph().getLanguage(), PersonAttrTerminType.POSITION)));
                    }
                }
            }
            if ((toks == null && step == 0 && t.chars.isLatinLetter()) && (t.getWhitespacesAfterCount() < 2)) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 != null && npt1.getBeginToken() != npt1.getEndToken()) {
                    java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(t, PersonItemToken.ParseAttr.of((PersonItemToken.ParseAttr.CANBELATIN.value()) | (PersonItemToken.ParseAttr.IGNOREATTRS.value())), 10);
                    if (pits != null && pits.size() > 1 && pits.get(0).firstname != null) 
                        npt1 = null;
                    int k = 0;
                    if (npt1 != null) {
                        for (com.pullenti.ner.Token tt2 = npt1.getBeginToken(); tt2 != null && tt2.getEndChar() <= npt1.getEndChar(); tt2 = tt2.getNext()) {
                            java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks1 = m_Termins.tryParseAll(tt2, com.pullenti.ner.core.TerminParseAttr.NO);
                            if (toks1 != null) {
                                step = 1;
                                toks = toks1;
                                npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, toks1.get(0).getEndChar(), null);
                                if (!((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(toks.get(0).termin, PersonAttrTermin.class)).isDoubt) {
                                    if (toks.get(0).getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                                    }
                                    else 
                                        break;
                                }
                            }
                            k++;
                            if (k >= 3 && t.chars.isAllLower()) {
                                if (!com.pullenti.ner.core.MiscHelper.isEngArticle(t.getPrevious())) 
                                    break;
                            }
                        }
                    }
                }
                else if (((npt1 == null || npt1.getEndToken() == t)) && t.chars.isCapitalUpper()) {
                    com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                    if ((mc.isMisc() || mc.isPreposition() || mc.isConjunction()) || mc.isPersonalPronoun() || mc.isPronoun()) {
                    }
                    else {
                        com.pullenti.ner.Token tt1 = null;
                        if ((t.getNext() != null && t.getNext().isHiphen() && !t.isWhitespaceAfter()) && !t.getNext().isWhitespaceAfter()) 
                            tt1 = t.getNext().getNext();
                        else if (npt1 == null) 
                            tt1 = t.getNext();
                        java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks1 = m_Termins.tryParseAll(tt1, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (toks1 != null && ((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(toks1.get(0).termin, PersonAttrTermin.class)).typ == PersonAttrTerminType.POSITION && (tt1.getWhitespacesBeforeCount() < 2)) {
                            step = 1;
                            toks = toks1;
                        }
                    }
                }
            }
            if (toks != null) {
                for (com.pullenti.ner.core.TerminToken tok : toks) {
                    if (((tok.getMorph()._getClass().isPreposition() || tok.getMorph().containsAttr("к.ф.", null))) && tok.getEndToken() == tok.getBeginToken()) 
                        continue;
                    PersonAttrTermin pat = (PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class);
                    if ((tok.getEndToken() instanceof com.pullenti.ner.TextToken) && pat.getCanonicText().startsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tok.getEndToken(), com.pullenti.ner.TextToken.class)).term)) {
                        if (tok.getLengthChar() < pat.getCanonicText().length()) {
                            if (tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isChar('.')) 
                                tok.setEndToken(tok.getEndToken().getNext());
                        }
                    }
                    if (pat.typ == PersonAttrTerminType.PREFIX) {
                        if (step == 0 || ((com.pullenti.unisharp.Utils.stringsNe(pat.getCanonicText(), "ГРАЖДАНИН") && com.pullenti.unisharp.Utils.stringsNe(pat.getCanonicText(), "ГРОМАДЯНИН")))) 
                            return _new2659(tok.getBeginToken(), tok.getEndToken(), PersonAttrTerminType.PREFIX, pat.getCanonicText(), tok.getMorph(), pat.getGender());
                    }
                    if (pat.typ == PersonAttrTerminType.BESTREGARDS) {
                        com.pullenti.ner.Token end = tok.getEndToken();
                        if (end.getNext() != null && end.getNext().isCharOf(",")) 
                            end = end.getNext();
                        return _new2651(tok.getBeginToken(), end, PersonAttrTerminType.BESTREGARDS, com.pullenti.ner.MorphCollection._new2650(com.pullenti.morph.MorphCase.NOMINATIVE));
                    }
                    if (pat.typ == PersonAttrTerminType.POSITION || pat.typ == PersonAttrTerminType.PREFIX || pat.typ == PersonAttrTerminType.KING) {
                        PersonAttrToken res = createAttrPosition(tok, attrs);
                        if (res != null) {
                            if (pat.typ == PersonAttrTerminType.KING) 
                                res.typ = pat.typ;
                            if (pat.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && res.gender == com.pullenti.morph.MorphGender.UNDEFINED) 
                                res.gender = pat.getGender();
                            if (pat.canHasPersonAfter > 0) {
                                if (res.getEndToken().isValue(pat.getCanonicText(), null)) 
                                    res.canHasPersonAfter = pat.canHasPersonAfter;
                                else 
                                    for (int ii = pat.getCanonicText().length() - 1; ii > 0; ii--) {
                                        if (!Character.isLetter(pat.getCanonicText().charAt(ii))) {
                                            if (res.getEndToken().isValue(pat.getCanonicText().substring(ii + 1), null)) 
                                                res.canHasPersonAfter = pat.canHasPersonAfter;
                                            break;
                                        }
                                    }
                            }
                            if (pat.canBeSameSurname) 
                                res.canBeSameSurname = true;
                            if (pat.canBeIndependant) 
                                res.setBeIndependentProperty(true);
                            if (pat.isDoubt) {
                                res.isDoubt = true;
                                if (res.getPropRef() != null && (res.getPropRef().findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) != null)) 
                                    res.isDoubt = false;
                            }
                            if ((t.getEndChar() < res.getBeginChar()) && res.getPropRef() != null) {
                                com.pullenti.ner.Token tt1 = res.getBeginToken().getPrevious();
                                if (tt1.isHiphen()) 
                                    res.getPropRef().setName((res.getPropRef().getName() + " " + com.pullenti.ner.core.MiscHelper.getTextValue(t, tt1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO).toLowerCase()));
                                else 
                                    res.getPropRef().setName((com.pullenti.ner.core.MiscHelper.getTextValue(t, tt1, com.pullenti.ner.core.GetTextAttr.NO).toLowerCase() + " " + res.getPropRef().getName()));
                                res.setBeginToken(t);
                            }
                        }
                        if (res != null) {
                            PersonItemToken pit = PersonItemToken.tryAttach(t, PersonItemToken.ParseAttr.IGNOREATTRS, null);
                            if (pit != null && pit.typ == PersonItemToken.ItemType.INITIAL) {
                                boolean ok = false;
                                pit = PersonItemToken.tryAttach(pit.getEndToken().getNext(), PersonItemToken.ParseAttr.IGNOREATTRS, null);
                                if (pit != null && pit.typ == PersonItemToken.ItemType.INITIAL) {
                                    pit = PersonItemToken.tryAttach(pit.getEndToken().getNext(), PersonItemToken.ParseAttr.IGNOREATTRS, null);
                                    if (pit != null && pit.typ == PersonItemToken.ItemType.INITIAL) 
                                        ok = true;
                                }
                                if (!ok) {
                                    if (_TryAttach(tok.getEndToken().getNext(), attrs) != null) 
                                        ok = true;
                                }
                                if (!ok) 
                                    return null;
                            }
                            if (npt0 != null) {
                                com.pullenti.ner.Token ttt1 = (npt0.adjectives.size() > 0 ? npt0.adjectives.get(0).getBeginToken() : npt0.getBeginToken());
                                if (ttt1.getBeginChar() < res.getBeginChar()) 
                                    res.setBeginToken(ttt1);
                                res.anafor = npt0.anafor;
                                String emptyAdj = null;
                                for (int i = 0; i < npt0.adjectives.size(); i++) {
                                    int j;
                                    for (j = 0; j < m_EmptyAdjs.length; j++) {
                                        if (npt0.adjectives.get(i).isValue(m_EmptyAdjs[j], null)) 
                                            break;
                                    }
                                    if (j < m_EmptyAdjs.length) {
                                        emptyAdj = m_EmptyAdjs[j].toLowerCase();
                                        npt0.adjectives.remove(i);
                                        break;
                                    }
                                }
                                String na0 = npt0.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                                String na1 = res.getPropRef().getName();
                                for (int i = 1; i < (na0.length() - 1); i++) {
                                    if (na1.startsWith(na0.substring(i))) {
                                        res.getPropRef().setName((na0.substring(0, 0 + i).trim() + " " + na1));
                                        break;
                                    }
                                }
                                if (emptyAdj != null) {
                                    PersonAttrToken res1 = _new2662(res.getBeginToken(), res.getEndToken(), npt0.getMorph(), res);
                                    res1.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                                    res1.getPropRef().setName(emptyAdj);
                                    res1.getPropRef().setHigher(res.getPropRef());
                                    res1.setBeIndependentProperty(res.canBeIndependentProperty());
                                    res1.typ = res.typ;
                                    if (res.getBeginToken() != res.getEndToken()) 
                                        res.setBeginToken(res.getBeginToken().getNext());
                                    res = res1;
                                }
                            }
                            if (res != null) 
                                res.getMorph().removeNotInDictionaryItems();
                            return res;
                        }
                    }
                }
            }
            if (step > 0 || t.chars.isLatinLetter()) 
                break;
            if (t.getMorph()._getClass().isAdjective() || t.chars.isLatinLetter()) {
            }
            else if (t.getNext() != null && t.getNext().isHiphen()) {
            }
            else 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null || npt.getEndToken() == t || npt.internalNoun != null) 
                break;
            if (npt.getEndToken().isValue("ВИЦЕ", "ВІЦЕ")) 
                break;
            t = npt.getEndToken();
            npt0 = npt;
        }
        if ((t instanceof com.pullenti.ner.TextToken) && (((t.isValue("ВИЦЕ", "ВІЦЕ") || t.isValue("ЭКС", "ЕКС") || t.isValue("VICE", null)) || t.isValue("EX", null) || t.isValue("DEPUTY", null))) && t.getNext() != null) {
            com.pullenti.ner.Token te = t.getNext();
            if (te.isHiphen()) 
                te = te.getNext();
            PersonAttrToken ppp = _TryAttach(te, attrs);
            if (ppp != null) {
                if (t.getBeginChar() < ppp.getBeginChar()) {
                    ppp.setBeginToken(t);
                    if (ppp.getPropRef() != null && ppp.getPropRef().getName() != null) 
                        ppp.getPropRef().setName((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.toLowerCase() + "-" + ppp.getPropRef().getName()));
                }
                return ppp;
            }
            if ((te != null && te.getPrevious().isHiphen() && !te.isWhitespaceAfter()) && !te.isWhitespaceBefore()) {
                if (com.pullenti.ner.core.BracketHelper.isBracket(te, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(te, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (te instanceof com.pullenti.ner.TextToken)) {
                        ppp = _new2646(t, br.getEndToken(), br.getEndToken().getPrevious().getMorph());
                        ppp.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                        ppp.getPropRef().setName((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term + "-" + com.pullenti.ner.core.MiscHelper.getTextValue(te.getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE)).toLowerCase());
                        return ppp;
                    }
                }
            }
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLatinLetter()) {
            if (t.isValue("STATE", null)) {
                com.pullenti.ner.Token tt1 = t.getNext();
                if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(tt1)) 
                    tt1 = tt1.getNext().getNext();
                PersonAttrToken res1 = _TryAttach(tt1, attrs);
                if (res1 != null && res1.getPropRef() != null) {
                    res1.setBeginToken(t);
                    res1.getPropRef().setName((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.toLowerCase() + " " + res1.getPropRef().getName()));
                    return res1;
                }
            }
        }
        return null;
    }

    private static String[] m_EmptyAdjs;

    private static java.util.HashMap<String, java.util.ArrayList<com.pullenti.morph.MorphWordForm>> m_StdForms;

    private static java.util.ArrayList<com.pullenti.morph.MorphWordForm> getStdForms(String tail, String w1, String w2) {
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li1 = null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li2 = null;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.morph.MorphWordForm>> wrapli12666 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.morph.MorphWordForm>>();
        boolean inoutres2667 = com.pullenti.unisharp.Utils.tryGetValue(m_StdForms, w1, wrapli12666);
        li1 = wrapli12666.value;
        if (!inoutres2667) {
            li1 = com.pullenti.morph.MorphologyService.getAllWordforms(w1, null);
            m_StdForms.put(w1, li1);
        }
        for (com.pullenti.morph.MorphWordForm v : li1) {
            if (com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, tail)) 
                res.add(v);
        }
        if (w2 != null) {
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.morph.MorphWordForm>> wrapli22664 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.morph.MorphWordForm>>();
            boolean inoutres2665 = com.pullenti.unisharp.Utils.tryGetValue(m_StdForms, w2, wrapli22664);
            li2 = wrapli22664.value;
            if (!inoutres2665) {
                li2 = com.pullenti.morph.MorphologyService.getAllWordforms(w2, null);
                m_StdForms.put(w2, li2);
            }
        }
        if (li2 != null) {
            for (com.pullenti.morph.MorphWordForm v : li2) {
                if (com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, tail)) 
                    res.add(v);
            }
        }
        return (res.size() > 0 ? res : null);
    }

    private static PersonAttrToken createAttrPosition(com.pullenti.ner.core.TerminToken tok, PersonAttrAttachAttrs attrs) {
        PersonAttrTerminType2 ty2 = ((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).typ2;
        if (ty2 == PersonAttrTerminType2.ABBR) {
            com.pullenti.ner.person.PersonPropertyReferent pr0 = new com.pullenti.ner.person.PersonPropertyReferent();
            pr0.setName(tok.termin.getCanonicText());
            return _new2668(tok.getBeginToken(), tok.getEndToken(), pr0, PersonAttrTerminType.POSITION);
        }
        if (ty2 == PersonAttrTerminType2.IO || ty2 == PersonAttrTerminType2.IO2) {
            for (int k = 0; ; k++) {
                if (k > 0) {
                    if (ty2 == PersonAttrTerminType2.IO) 
                        return null;
                    if (((short)((tok.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        return null;
                    break;
                }
                com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                if (tt != null && tt.getMorph()._getClass().isPreposition()) 
                    tt = tt.getNext();
                PersonAttrToken resPat = _new2647(tok.getBeginToken(), tok.getEndToken(), PersonAttrTerminType.POSITION);
                resPat.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                    resPat.setEndToken(tt);
                    resPat.getPropRef().setHigher((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.person.PersonPropertyReferent.class));
                }
                else {
                    PersonAttrAttachAttrs aa = attrs;
                    if (ty2 == PersonAttrTerminType2.IO2) 
                        aa = PersonAttrAttachAttrs.of((aa.value()) | (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()));
                    PersonAttrToken pat = tryAttach(tt, aa);
                    if (pat == null) {
                        if (!(tt instanceof com.pullenti.ner.TextToken)) 
                            continue;
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt == null || npt.getEndToken() == tok.getEndToken().getNext()) 
                            continue;
                        pat = tryAttach(npt.getEndToken(), PersonAttrAttachAttrs.NO);
                        if (pat == null || pat.getBeginToken() != tt) 
                            continue;
                    }
                    if (pat.typ != PersonAttrTerminType.POSITION) 
                        continue;
                    resPat.setEndToken(pat.getEndToken());
                    resPat.getPropRef().setHigher(pat.getPropRef());
                    resPat.higherPropRef = pat;
                }
                String nam = tok.termin.getCanonicText();
                com.pullenti.ner.Token ts = resPat.getEndToken().getNext();
                com.pullenti.ner.Token te = null;
                for (; ts != null; ts = ts.getNext()) {
                    if (ts.getMorph()._getClass().isPreposition()) {
                        if (ts.isValue("В", null) || ts.isValue("ПО", null)) {
                            if (ts.getNext() instanceof com.pullenti.ner.ReferentToken) {
                                com.pullenti.ner.Referent r = ts.getNext().getReferent();
                                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg)) {
                                    resPat.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                                    resPat.setEndToken(ts.getNext());
                                }
                                else 
                                    te = ts.getNext();
                                ts = ts.getNext();
                                continue;
                            }
                            com.pullenti.ner.ReferentToken rt11 = ts.kit.processReferent("NAMEDENTITY", ts.getNext(), null);
                            if (rt11 != null) {
                                resPat.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rt11, false, 0);
                                resPat.setEndToken(rt11.getEndToken());
                                ts = rt11.getEndToken();
                                continue;
                            }
                        }
                        if (ts.isValue("ПО", null) && ts.getNext() != null) {
                            com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(ts.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (nnn != null) 
                                ts = (te = nnn.getEndToken());
                            else if ((ts.getNext() instanceof com.pullenti.ner.TextToken) && ((!ts.getNext().chars.isAllLower() && !ts.getNext().chars.isCapitalUpper()))) 
                                ts = (te = ts.getNext());
                            else 
                                break;
                            if (ts.getNext() != null && ts.getNext().isAnd() && nnn != null) {
                                com.pullenti.ner.core.NounPhraseToken nnn2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(ts.getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                                if (nnn2 != null && !((com.pullenti.morph.MorphCase.ooBitand(nnn2.getMorph().getCase(), nnn.getMorph().getCase()))).isUndefined()) 
                                    ts = (te = nnn2.getEndToken());
                            }
                            continue;
                        }
                        break;
                    }
                    if (ts != resPat.getEndToken().getNext() && ts.chars.isAllLower()) {
                        com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(ts, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (nnn == null) 
                            break;
                        ts = (te = nnn.getEndToken());
                        continue;
                    }
                    break;
                }
                if (te != null) {
                    String s = com.pullenti.ner.core.MiscHelper.getTextValue(resPat.getEndToken().getNext(), te, com.pullenti.ner.core.GetTextAttr.NO);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                        nam = (nam + " " + s);
                        resPat.setEndToken(te);
                    }
                    if ((resPat.higherPropRef != null && (te.getWhitespacesAfterCount() < 4) && te.getNext().getReferent() != null) && com.pullenti.unisharp.Utils.stringsEq(te.getNext().getReferent().getTypeName(), objNameOrg)) {
                        resPat.setEndToken(resPat.higherPropRef.setEndToken(te.getNext()));
                        resPat.higherPropRef.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, te.getNext().getReferent(), false, 0);
                    }
                }
                com.pullenti.unisharp.Outargwrapper<String> wrapnam2670 = new com.pullenti.unisharp.Outargwrapper<String>(nam);
                resPat.setBeginToken(_analizeVise(resPat.getBeginToken(), wrapnam2670));
                nam = wrapnam2670.value;
                resPat.getPropRef().setName(nam.toLowerCase());
                resPat.setMorph(tok.getMorph());
                return resPat;
            }
        }
        if (ty2 == PersonAttrTerminType2.ADJ) {
            PersonAttrToken pat = _TryAttach(tok.getEndToken().getNext(), attrs);
            if (pat == null || pat.typ != PersonAttrTerminType.POSITION) 
                return null;
            if (tok.getBeginChar() == tok.getEndChar() && !tok.getBeginToken().getMorph()._getClass().isUndefined()) 
                return null;
            pat.setBeginToken(tok.getBeginToken());
            pat.getPropRef().setName((tok.termin.getCanonicText().toLowerCase() + " " + pat.getPropRef().getName()));
            pat.setMorph(tok.getMorph());
            return pat;
        }
        if (ty2 == PersonAttrTerminType2.IGNOREDADJ) {
            PersonAttrToken pat = _TryAttach(tok.getEndToken().getNext(), attrs);
            if (pat == null || pat.typ != PersonAttrTerminType.POSITION) 
                return null;
            pat.setBeginToken(tok.getBeginToken());
            pat.setMorph(tok.getMorph());
            return pat;
        }
        if (ty2 == PersonAttrTerminType2.GRADE) {
            PersonAttrToken gr = createAttrGrade(tok);
            if (gr != null) 
                return gr;
            if (tok.getBeginToken().isValue("КАНДИДАТ", null)) {
                com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                if (tt != null && tt.isValue("В", null)) 
                    tt = tt.getNext();
                else if ((tt != null && tt.isValue("НА", null) && tt.getNext() != null) && ((tt.getNext().isValue("ПОСТ", null) || tt.getNext().isValue("ДОЛЖНОСТЬ", null)))) 
                    tt = tt.getNext().getNext();
                else 
                    tt = null;
                if (tt != null) {
                    PersonAttrToken pat2 = _TryAttach(tt, PersonAttrAttachAttrs.NO);
                    if (pat2 != null) {
                        PersonAttrToken res0 = _new2647(tok.getBeginToken(), pat2.getEndToken(), PersonAttrTerminType.POSITION);
                        res0.setPropRef(com.pullenti.ner.person.PersonPropertyReferent._new2648("кандидат"));
                        res0.getPropRef().setHigher(pat2.getPropRef());
                        res0.higherPropRef = pat2;
                        res0.setMorph(tok.getMorph());
                        return res0;
                    }
                }
            }
            if (!tok.getBeginToken().isValue("ДОКТОР", null) && !tok.getBeginToken().isValue("КАНДИДАТ", null)) 
                return null;
        }
        String name = tok.termin.getCanonicText().toLowerCase();
        com.pullenti.ner.Token t0 = tok.getBeginToken();
        com.pullenti.ner.Token t1 = tok.getEndToken();
        com.pullenti.unisharp.Outargwrapper<String> wrapname2682 = new com.pullenti.unisharp.Outargwrapper<String>(name);
        t0 = _analizeVise(t0, wrapname2682);
        name = wrapname2682.value;
        com.pullenti.ner.person.PersonPropertyReferent pr = new com.pullenti.ner.person.PersonPropertyReferent();
        if ((t1.getNext() != null && t1.getNext().isHiphen() && !t1.isWhitespaceAfter()) && !t1.getNext().isWhitespaceAfter()) {
            if (t1.getNext().getNext().chars.equals(t1.chars) || m_Termins.tryParse(t1.getNext().getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null || ((t1.getNext().getNext().chars.isAllLower() && t1.getNext().getNext().chars.isCyrillicLetter()))) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getEndToken() == t1.getNext().getNext()) {
                    name = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                    t1 = npt.getEndToken();
                }
            }
        }
        com.pullenti.ner.Token tname0 = t1.getNext();
        com.pullenti.ner.Token tname1 = null;
        String category = null;
        com.pullenti.ner.core.NounPhraseToken npt0 = null;
        for (com.pullenti.ner.Token t = t1.getNext(); t != null; t = t.getNext()) {
            if ((((attrs.value()) & (PersonAttrAttachAttrs.ONLYKEYWORD.value()))) != (PersonAttrAttachAttrs.NO.value())) 
                break;
            if (t == t1.getNext()) {
                if (t.isValue("ИСТЕЦ", "ПОЗИВАЧ") || t.isValue("ОТВЕТЧИК", "ВІДПОВІДАЧ")) {
                    if (t1.isValue("ПРЕДСТАВИТЕЛЬ", "ПРЕДСТАВНИК")) 
                        return null;
                }
            }
            if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t) != null) 
                break;
            if (t.isNewlineBefore()) {
                boolean ok = false;
                if (t.getReferent() != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), objNameOrg) || (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        if (pr.findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null) 
                            ok = true;
                    }
                }
                if (t.getNewlinesBeforeCount() > 1 && !t.chars.isAllLower()) {
                    if (!ok) 
                        break;
                    if ((t.getNewlinesAfterCount() < 3) && tok.getBeginToken().isNewlineBefore()) {
                    }
                    else 
                        break;
                }
                if (tok.isNewlineBefore()) {
                    if (m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                        break;
                    else 
                        ok = true;
                }
                if (t0.getPrevious() != null && t0.getPrevious().isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br0 = com.pullenti.ner.core.BracketHelper.tryParse(t0.getPrevious(), com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES, 10);
                    if (br0 != null && br0.getEndChar() > t.getEndChar()) 
                        ok = true;
                }
                if (!ok) {
                    com.pullenti.ner.core.NounPhraseToken npt00 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                    if (npt00 != null && npt00.getEndToken().getNext() != null && !_isPerson(t)) {
                        com.pullenti.ner.Token tt1 = npt00.getEndToken();
                        boolean zap = false;
                        boolean and = false;
                        for (com.pullenti.ner.Token ttt = tt1.getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (!ttt.isCommaAnd()) 
                                break;
                            npt00 = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt00 == null) 
                                break;
                            tt1 = npt00.getEndToken();
                            if (ttt.isChar(',')) 
                                zap = true;
                            else {
                                and = true;
                                break;
                            }
                            ttt = npt00.getEndToken();
                        }
                        if (zap && !and) {
                        }
                        else if (tt1.getNext() == null) {
                        }
                        else {
                            if (_isPerson(tt1.getNext())) 
                                ok = true;
                            else if (tt1.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                                if (_isPerson(tt1.getNext().getNext())) 
                                    ok = true;
                                else {
                                    String ccc;
                                    com.pullenti.unisharp.Outargwrapper<String> wrapccc2673 = new com.pullenti.unisharp.Outargwrapper<String>();
                                    com.pullenti.ner.Token ttt = tryAttachCategory(tt1.getNext().getNext(), wrapccc2673);
                                    ccc = wrapccc2673.value;
                                    if (ttt != null) 
                                        ok = true;
                                }
                            }
                            if (ok) {
                                t = (t1 = (tname1 = tt1));
                                continue;
                            }
                        }
                    }
                    break;
                }
            }
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = br.getEndToken();
                    boolean ok = true;
                    for (com.pullenti.ner.Token ttt = br.getBeginToken(); ttt != br.getEndToken(); ttt = ttt.getNext()) {
                        if (ttt.chars.isLetter()) {
                            if (!ttt.chars.isAllLower()) {
                                ok = false;
                                break;
                            }
                        }
                    }
                    if (!ok) 
                        break;
                    continue;
                }
                else 
                    break;
            }
            com.pullenti.ner.Token tt2 = _analyzeRomanNums(t);
            if (tt2 != null) {
                t1 = (t = tt2);
                if (t.isValue("СОЗЫВ", null) && t.getNext() != null && t.getNext().isValue("ОТ", null)) {
                    t = t.getNext();
                    continue;
                }
                break;
            }
            PersonAttrToken pat = null;
            if ((((attrs.value()) & (PersonAttrAttachAttrs.ONLYKEYWORD.value()))) == (PersonAttrAttachAttrs.NO.value())) 
                pat = _TryAttach(t, PersonAttrAttachAttrs.ONLYKEYWORD);
            if (pat != null) {
                if (pat.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL && !pat.getMorph().getCase().isNominative()) {
                }
                else if (((tok.termin instanceof PersonAttrTermin) && ((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).isDoubt && pat.getPropRef() != null) && pat.getPropRef().getSlots().size() == 1 && tok.chars.isLatinLetter() == pat.chars.isLatinLetter()) {
                    t1 = (tname1 = (t = pat.getEndToken()));
                    continue;
                }
                else if ((!tok.getMorph().getCase().isGenitive() && (tok.termin instanceof PersonAttrTermin) && ((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).canHasPersonAfter == 1) && pat.getMorph().getCase().isGenitive()) {
                    com.pullenti.ner.ReferentToken rr = null;
                    if (!t.kit.miscData.containsKey("IgnorePersons")) {
                        t.kit.miscData.put("IgnorePersons", null);
                        rr = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(t, null);
                        if (t.kit.miscData.containsKey("IgnorePersons")) 
                            t.kit.miscData.remove("IgnorePersons");
                    }
                    if (rr != null && rr.getMorph().getCase().isGenitive()) {
                        pr.addExtReferent(rr);
                        pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rr.referent, false, 0);
                        t1 = (t = rr.getEndToken());
                    }
                    else 
                        t1 = (tname1 = (t = pat.getEndToken()));
                    continue;
                }
                else if (t.isValue("ГР", null) && (pat.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && !pat.getEndToken().getNext().chars.isAllLower()) {
                    com.pullenti.ner.ReferentToken ppp = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(pat.getEndToken().getNext().getNext(), null);
                    if (ppp != null) {
                        t1 = (tname1 = (t = pat.getEndToken()));
                        continue;
                    }
                    break;
                }
                else 
                    break;
            }
            com.pullenti.ner.Token te = t;
            if (te.getNext() != null && te.isCharOf(",в") && (((attrs.value()) & (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()))) == (PersonAttrAttachAttrs.NO.value())) {
                te = te.getNext();
                if (te.isValue("ОРГАНИЗАЦИЯ", null) && (te.getNext() instanceof com.pullenti.ner.ReferentToken) && com.pullenti.unisharp.Utils.stringsEq(te.getNext().getReferent().getTypeName(), objNameOrg)) 
                    te = te.getNext();
            }
            else if (te.getNext() != null && te.getMorph()._getClass().isPreposition()) {
                if ((((attrs.value()) & (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()))) == (PersonAttrAttachAttrs.AFTERZAMESTITEL.value())) 
                    break;
                if (((te.isValue("ИЗ", null) || te.isValue("ПРИ", null) || te.isValue("ПО", null)) || te.isValue("НА", null) || te.isValue("ОТ", null)) || te.isValue("OF", null)) 
                    te = te.getNext();
            }
            else if ((te.isHiphen() && te.getNext() != null && !te.isWhitespaceBefore()) && !te.isWhitespaceAfter() && te.getPrevious().chars.equals(te.getNext().chars)) 
                continue;
            else if (te.isValue("REPRESENT", null) && (te.getNext() instanceof com.pullenti.ner.ReferentToken)) 
                te = te.getNext();
            com.pullenti.ner.Referent r = te.getReferent();
            com.pullenti.ner.Referent r1;
            if ((te.chars.isLatinLetter() && te.getLengthChar() > 1 && !t0.chars.isLatinLetter()) && !te.chars.isAllLower()) {
                if (r == null || com.pullenti.unisharp.Utils.stringsNe(r.getTypeName(), objNameOrg)) {
                    com.pullenti.unisharp.Outargwrapper<String> wrapcategory2674 = new com.pullenti.unisharp.Outargwrapper<String>();
                    com.pullenti.ner.Token tt = tryAttachCategory(t, wrapcategory2674);
                    category = wrapcategory2674.value;
                    if (tt != null && name != null) {
                        t = (t1 = tt);
                        continue;
                    }
                    for (; te != null; te = te.getNext()) {
                        if (te.chars.isLetter()) {
                            if (!te.chars.isLatinLetter()) 
                                break;
                            t1 = (tname1 = (t = te));
                        }
                    }
                    continue;
                }
            }
            if (r != null) {
                if ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) && te.getPrevious() != null && te.getPrevious().isValue("ДЕЛО", "СПРАВІ")) && te.getPrevious().getPrevious() != null && te.getPrevious().getPrevious().isValue("ПО", null)) {
                    t1 = (tname1 = (t = te));
                    continue;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) && ((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).typ == PersonAttrTerminType.KING && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(te, com.pullenti.ner.ReferentToken.class)).getEndToken().getMorph()._getClass().isAdjective()) 
                    break;
                if ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameAddr) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE")) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameTransport)) {
                    if (t0.getPrevious() != null && t0.getPrevious().isValue("ОТ", null) && t.isNewlineBefore()) 
                        break;
                    t1 = te;
                    pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                    boolean posol = ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg))) && com.pullenti.morph.LanguageHelper.endsWithEx(name, "посол", "представитель", "консул", "представник");
                    if (posol) {
                        t = t1;
                        continue;
                    }
                    if ((((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) && t1.getNext() != null && t1.getNext().getMorph()._getClass().isPreposition()) && t1.getNext().getNext() != null && !t1.getNext().isValue("О", null)) && !t1.getNext().isValue("ОБ", null) && (((attrs.value()) & (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()))) == (PersonAttrAttachAttrs.NO.value())) && !((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).isBoss) {
                        if ((((r1 = t1.getNext().getNext().getReferent()))) != null) {
                            if (com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), objNameOrg)) {
                                pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r1, false, 0);
                                t = (t1 = t1.getNext().getNext());
                            }
                        }
                    }
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg)) {
                        for (t = te.getNext(); t != null; t = t.getNext()) {
                            if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() >= 2 && t.chars.isAllUpper()) && (t.getWhitespacesBeforeCount() < 2)) {
                                t1 = t;
                                continue;
                            }
                            if (!t.isCommaAnd() || !(t.getNext() instanceof com.pullenti.ner.ReferentToken)) 
                                break;
                            r = t.getNext().getReferent();
                            if (r == null) 
                                break;
                            if (com.pullenti.unisharp.Utils.stringsNe(r.getTypeName(), objNameOrg)) 
                                break;
                            pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                            t = t.getNext();
                            t1 = t;
                            if (t.getPrevious().isAnd()) {
                                t = t.getNext();
                                break;
                            }
                        }
                        for (; t != null; t = t.getNext()) {
                            if (t.isNewlineBefore()) 
                                break;
                            tt2 = _analyzeRomanNums(t);
                            if (tt2 != null) {
                                t1 = (t = tt2);
                                if (t.isValue("СОЗЫВ", null) && t.getNext() != null && t.getNext().isValue("ОТ", null)) 
                                    t = t.getNext();
                                else 
                                    break;
                            }
                            if (t.isValue("В", null) || t.isValue("ОТ", null) || t.isAnd()) 
                                continue;
                            if (t.getMorph().getLanguage().isUa()) {
                                if (t.isValue("ВІД", null)) 
                                    continue;
                            }
                            if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) && t.getPrevious().isValue("ОТ", "ВІД")) {
                                tname0 = t.getPrevious();
                                tname1 = (t1 = t);
                                continue;
                            }
                            if ((t instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false) && t.getPrevious().isValue("ОТ", "ВІД")) {
                                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                                if (br != null && (br.getLengthChar() < 100)) {
                                    tname0 = t.getPrevious();
                                    tname1 = (t1 = (t = br.getEndToken()));
                                    continue;
                                }
                            }
                            if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() > 2 && t.getPrevious().isValue("В", null)) && tname1 == null && t.getNext() != null) {
                                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(t.getNext(), null);
                                if (rt != null || (t.getNext().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
                                    tname0 = t.getPrevious();
                                    tname1 = (t1 = t);
                                    continue;
                                }
                            }
                            r = t.getReferent();
                            if (r == null) 
                                break;
                            if (com.pullenti.unisharp.Utils.stringsNe(r.getTypeName(), objNameGeo)) {
                                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg) && t.getPrevious() != null && ((t.getPrevious().isValue("ОТ", null) || t.getPrevious().isValue("ВІД", null)))) {
                                }
                                else 
                                    break;
                            }
                            pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                            t1 = t;
                        }
                    }
                }
                if ((t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2) && t1.getNext().chars.isLatinLetter()) && !t1.getNext().chars.isAllLower() && com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1.getNext()) == null) {
                    for (t = t1.getNext(); t != null; t = t.getNext()) {
                        if (!(t instanceof com.pullenti.ner.TextToken)) 
                            break;
                        if (!t.chars.isLetter()) 
                            break;
                        if (!t.chars.isLatinLetter()) 
                            break;
                        if (t.kit.baseLanguage.isEn()) 
                            break;
                        com.pullenti.ner.Token ttt = _analyzeRomanNums(t);
                        if (ttt != null) 
                            t = ttt;
                        t1 = (tname1 = t);
                    }
                }
                t = t1;
                if (((tname0 == t && tname1 == null && t.getNext() != null) && (((attrs.value()) & (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()))) == (PersonAttrAttachAttrs.NO.value()) && com.pullenti.unisharp.Utils.stringsNe(name, "президент")) && t.getNext().isValue("ПО", null)) {
                    tname0 = t.getNext();
                    continue;
                }
                break;
            }
            if (category == null) {
                com.pullenti.unisharp.Outargwrapper<String> wrapcategory2675 = new com.pullenti.unisharp.Outargwrapper<String>();
                com.pullenti.ner.Token tt = tryAttachCategory(t, wrapcategory2675);
                category = wrapcategory2675.value;
                if (tt != null && name != null) {
                    t = (t1 = tt);
                    continue;
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(name, "премьер")) 
                break;
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.isValue("ИМЕНИ", "ІМЕНІ")) 
                    break;
            }
            if (!t.chars.isAllLower()) {
                PersonItemToken pit = PersonItemToken.tryAttach(t, PersonItemToken.ParseAttr.of((PersonItemToken.ParseAttr.CANBELATIN.value()) | (PersonItemToken.ParseAttr.IGNOREATTRS.value())), null);
                if (pit != null) {
                    if (pit.referent != null) 
                        break;
                    if (pit.lastname != null && ((pit.lastname.isInDictionary || pit.lastname.isInOntology))) 
                        break;
                    if (pit.firstname != null && pit.firstname.isInDictionary) 
                        break;
                    if (((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).typ == PersonAttrTerminType.KING) {
                        if (pit.lastname != null || pit.value == null) 
                            break;
                        if (pit.value.endsWith("КИЙ")) 
                            break;
                    }
                    java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(t, PersonItemToken.ParseAttr.of((PersonItemToken.ParseAttr.NO.value()) | (PersonItemToken.ParseAttr.IGNOREATTRS.value())), 6);
                    if (pits != null && pits.size() > 0) {
                        if (pits.size() == 2) {
                            if (pits.get(1).lastname != null && pits.get(1).lastname.isInDictionary) 
                                break;
                            if (pits.get(1).typ == PersonItemToken.ItemType.INITIAL && pits.get(0).lastname != null) 
                                break;
                        }
                        if (pits.size() == 3) {
                            if (pits.get(2).lastname != null) {
                                if (pits.get(1).middlename != null) 
                                    break;
                                if (pits.get(0).firstname != null && pits.get(0).firstname.isInDictionary) 
                                    break;
                            }
                            if (pits.get(1).typ == PersonItemToken.ItemType.INITIAL && pits.get(2).typ == PersonItemToken.ItemType.INITIAL && pits.get(0).lastname != null) 
                                break;
                        }
                        if (pits.get(0).typ == PersonItemToken.ItemType.INITIAL) 
                            break;
                        if (((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).typ == PersonAttrTerminType.KING) {
                            if (pits.get(0).getMorph().getCase().isUndefined() && !pits.get(0).getMorph().getCase().isGenitive()) 
                                break;
                            if (pits.size() == 1) {
                                com.pullenti.morph.MorphClass mc1 = pits.get(0).getBeginToken().getMorphClassInDictionary();
                                if (mc1.isUndefined()) 
                                    break;
                            }
                        }
                    }
                }
            }
            boolean testPerson = false;
            if (!t.chars.isAllLower()) {
                if (t.kit.miscData.containsKey("TestAttr")) {
                }
                else {
                    java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(t, PersonItemToken.ParseAttr.IGNOREATTRS, 10);
                    if (pits != null && pits.size() > 1) {
                        com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        int iii = 1;
                        if (nnn != null && nnn.adjectives.size() > 0) 
                            iii += nnn.adjectives.size();
                        testPerson = true;
                        t.kit.miscData.put("TestAttr", null);
                        java.util.ArrayList<PersonIdentityToken> li = PersonIdentityToken.tryAttach(pits, 0, com.pullenti.morph.MorphBaseInfo._new2676(com.pullenti.morph.MorphCase.ALLCASES), null, false, false);
                        t.kit.miscData.remove("TestAttr");
                        if (li.size() > 0 && li.get(0).coef > 1) {
                            t.kit.miscData.put("TestAttr", null);
                            java.util.ArrayList<PersonIdentityToken> li1 = PersonIdentityToken.tryAttach(pits, iii, com.pullenti.morph.MorphBaseInfo._new2676(com.pullenti.morph.MorphCase.ALLCASES), null, false, false);
                            t.kit.miscData.remove("TestAttr");
                            if (li1.size() == 0) 
                                break;
                            if (li1.get(0).coef <= li.get(0).coef) 
                                break;
                        }
                        else {
                            t.kit.miscData.put("TestAttr", null);
                            java.util.ArrayList<PersonIdentityToken> li1 = PersonIdentityToken.tryAttach(pits, 1, com.pullenti.morph.MorphBaseInfo._new2676(com.pullenti.morph.MorphCase.ALLCASES), null, false, false);
                            t.kit.miscData.remove("TestAttr");
                            if (li1.size() > 0 && li1.get(0).coef >= 1 && li1.get(0).getBeginToken() == t) 
                                continue;
                        }
                    }
                }
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if ((br != null && t.getNext().getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getNext().getReferent().getTypeName(), objNameOrg)) && t.getNext().getNext() == br.getEndToken()) {
                    pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, t.getNext().getReferent(), false, 0);
                    t1 = br.getEndToken();
                    break;
                }
                else if (br != null && (br.getLengthChar() < 40)) {
                    t = (t1 = (tname1 = br.getEndToken()));
                    continue;
                }
            }
            if ((t instanceof com.pullenti.ner.NumberToken) && t.getPrevious().isValue("ГЛАВА", null)) 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if ((npt == null && (t instanceof com.pullenti.ner.NumberToken) && (t.getWhitespacesAfterCount() < 3)) && (t.getWhitespacesBeforeCount() < 3)) {
                com.pullenti.ner.core.NounPhraseToken npt00 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt00 != null) {
                    if (npt00.getEndToken().isValue("ОРДЕН", null) || npt00.getEndToken().isValue("МЕДАЛЬ", null)) 
                        npt = npt00;
                }
            }
            boolean test = false;
            if (npt != null) {
                if (_existsInDoctionary(npt.getEndToken()) && ((npt.getMorph().getCase().isGenitive() || npt.getMorph().getCase().isInstrumental()))) 
                    test = true;
                else if (npt.getBeginToken() == npt.getEndToken() && t.getLengthChar() > 1 && ((t.chars.isAllUpper() || t.chars.isLastLower()))) 
                    test = true;
            }
            else if (t.chars.isAllUpper() || t.chars.isLastLower()) 
                test = true;
            if (test) {
                com.pullenti.ner.ReferentToken rto = t.kit.processReferent("ORGANIZATION", t, null);
                if (rto != null) {
                    String str = rto.referent.toString().toUpperCase();
                    if (str.startsWith("ГОСУДАРСТВЕННАЯ ГРАЖДАНСКАЯ СЛУЖБА")) 
                        rto = null;
                }
                if (rto != null && rto.getEndChar() >= t.getEndChar() && rto.getBeginChar() == t.getBeginChar()) {
                    pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rto.referent, false, 0);
                    pr.addExtReferent(rto);
                    t = (t1 = rto.getEndToken());
                    if ((((attrs.value()) & (PersonAttrAttachAttrs.AFTERZAMESTITEL.value()))) != (PersonAttrAttachAttrs.NO.value())) 
                        break;
                    npt0 = npt;
                    if (t.getNext() != null && t.getNext().isAnd()) {
                        com.pullenti.ner.ReferentToken rto2 = t.kit.processReferent("ORGANIZATION", t.getNext().getNext(), null);
                        if (rto2 != null && rto2.getBeginChar() == t.getNext().getNext().getBeginChar()) {
                            pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rto2.referent, false, 0);
                            pr.addExtReferent(rto2);
                            t = (t1 = rto2.getEndToken());
                        }
                    }
                    continue;
                }
                rto = t.kit.processReferent("NAMEDENTITY", t, null);
                if (rto != null && rto.getMorph().getCase().isGenitive()) {
                    pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, rto.referent, false, 0);
                    pr.addExtReferent(rto);
                    t = (t1 = rto.getEndToken());
                    continue;
                }
                if (npt != null) {
                    t = (t1 = (tname1 = npt.getEndToken()));
                    npt0 = npt;
                    continue;
                }
            }
            if (t.getMorph()._getClass().isPreposition()) {
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt == null && t.getNext() != null && t.getNext().getMorph()._getClass().isAdverb()) 
                    npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && _existsInDoctionary(npt.getEndToken())) {
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(npt.getEndToken().getNext(), true, false)) {
                        if (((t.isValue("ПО", "ЗА") && npt.getEndToken().isValue("СПЕЦИАЛЬНОСТЬ", "ФАХОМ"))) || ((t.isValue("С", "ЗІ") && npt.getEndToken().isValue("СПЕЦИАЛИЗАЦИЯ", "СПЕЦІАЛІЗАЦІЯ")))) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(npt.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) {
                                t = (t1 = br.getEndToken());
                                continue;
                            }
                        }
                    }
                    boolean ok = false;
                    if ((t.isValue("ПО", null) && npt.getMorph().getCase().isDative() && !npt.noun.isValue("ИМЯ", "ІМЯ")) && !npt.noun.isValue("ПРОЗВИЩЕ", "ПРІЗВИСЬКО") && !npt.noun.isValue("ПРОЗВАНИЕ", "ПРОЗВАННЯ")) {
                        ok = true;
                        if (npt.noun.isValue("РАБОТА", "РОБОТА") || npt.noun.isValue("ПОДДЕРЖКА", "ПІДТРИМКА") || npt.noun.isValue("СОПРОВОЖДЕНИЕ", "СУПРОВІД")) {
                            com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                            if (npt2 != null) 
                                npt = npt2;
                        }
                    }
                    else if (npt.noun.isValue("ОТСТАВКА", null) || npt.noun.isValue("ВІДСТАВКА", null)) 
                        ok = true;
                    else if (com.pullenti.unisharp.Utils.stringsEq(name, "кандидат") && t.isValue("В", null)) 
                        ok = true;
                    if (ok) {
                        t = (t1 = (tname1 = npt.getEndToken()));
                        npt0 = npt;
                        continue;
                    }
                }
                if (t.isValue("OF", null)) 
                    continue;
            }
            else if (t.isAnd() && npt0 != null) {
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && !((com.pullenti.morph.MorphClass.ooBitand(npt.getMorph()._getClass(), npt0.getMorph()._getClass()))).isUndefined()) {
                    if (npt0.chars.equals(npt.chars)) {
                        t = (t1 = (tname1 = npt.getEndToken()));
                        npt0 = null;
                        continue;
                    }
                }
            }
            else if (t.isCommaAnd() && ((!t.isNewlineAfter() || tok.isNewlineBefore())) && npt0 != null) {
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && !((com.pullenti.morph.MorphClass.ooBitand(npt.getMorph()._getClass(), npt0.getMorph()._getClass()))).isUndefined()) {
                    if (npt0.chars.equals(npt.chars) && npt.getEndToken().getNext() != null && npt.getEndToken().getNext().isAnd()) {
                        com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken().getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt1 != null && !((com.pullenti.morph.MorphClass.ooBitand(npt1.getMorph()._getClass(), com.pullenti.morph.MorphClass.ooBitand(npt.getMorph()._getClass(), npt0.getMorph()._getClass())))).isUndefined()) {
                            if (npt0.chars.equals(npt1.chars)) {
                                t = (t1 = (tname1 = npt1.getEndToken()));
                                npt0 = null;
                                continue;
                            }
                        }
                    }
                }
            }
            else if (t.getMorph()._getClass().isAdjective() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null && (br.getLengthChar() < 100)) {
                    t = (t1 = (tname1 = br.getEndToken()));
                    npt0 = null;
                    continue;
                }
            }
            if (t.chars.isLatinLetter() && t.getPrevious().chars.isCyrillicLetter()) {
                for (; t != null; t = t.getNext()) {
                    if (!t.chars.isLatinLetter() || t.isNewlineBefore()) 
                        break;
                    else 
                        t1 = (tname1 = t);
                }
                break;
            }
            if (((t.chars.isAllUpper() || ((!t.chars.isAllLower() && !t.chars.isCapitalUpper())))) && t.getLengthChar() > 1 && !t0.chars.isAllUpper()) {
                t1 = (tname1 = t);
                continue;
            }
            if ((!t.chars.isAllLower() && !t.chars.isAllUpper() && !t.chars.isCapitalUpper()) && t.getLengthChar() > 2 && !t0.chars.isAllUpper()) {
                t1 = (tname1 = t);
                continue;
            }
            if (((t.chars.isLetter() && (t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) && !t.getMorph()._getClass().isPreposition() && !t.getMorph()._getClass().isConjunction()) && !t.getMorph()._getClass().isVerb()) {
                t1 = (tname1 = t);
                break;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getBeginToken().isValue("МИЛЛИОНОВ", null) || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getBeginToken().isValue("МІЛЬЙОНІВ", null)) {
                    t1 = (tname1 = t);
                    break;
                }
            }
            if (testPerson) {
                if (t.getNext() == null) 
                    break;
                te = t.getNext();
                if (((te.isCharOf(",в") || te.isValue("ИЗ", null))) && te.getNext() != null) 
                    te = te.getNext();
                if ((((r = te.getReferent()))) != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameGeo) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameOrg) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), objNameTransport)) {
                        t1 = (tname1 = t);
                        continue;
                    }
                }
                break;
            }
            if (t.getMorph().getLanguage().isEn()) 
                break;
            if (t.getMorph()._getClass().isNoun() && t.getMorphClassInDictionary().isUndefined() && (t.getWhitespacesBeforeCount() < 2)) {
                t1 = (tname1 = t);
                continue;
            }
            if (t.getMorph()._getClass().isPronoun()) 
                continue;
            if ((t.isHiphen() && tname1 == t.getPrevious() && !t.isWhitespaceBefore()) && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                t = t.getNext();
                t1 = (tname1 = t);
                continue;
            }
            break;
        }
        if (tname1 != null) {
            if (pr.findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, null, true) == null && ((((((tname1.isValue("КОМПАНИЯ", "КОМПАНІЯ") || tname1.isValue("ФИРМА", "ФІРМА") || tname1.isValue("ГРУППИРОВКА", "УГРУПОВАННЯ")) || tname1.isValue("ПРЕДПРИЯТИЕ", "ПІДПРИЄМСТВО") || tname1.isValue("ПРЕЗИДИУМ", "ПРЕЗИДІЯ")) || tname1.isValue("ЧАСТЬ", "ЧАСТИНА") || tname1.isValue("ФЕДЕРАЦИЯ", "ФЕДЕРАЦІЯ")) || tname1.isValue("ВЕДОМСТВО", "ВІДОМСТВО") || tname1.isValue("БАНК", null)) || tname1.isValue("КОРПОРАЦИЯ", "КОРПОРАЦІЯ")))) {
                if (tname1 == tname0 || ((tname0.isValue("ЭТОТ", "ЦЕЙ") && tname0.getNext() == tname1))) {
                    com.pullenti.ner.Referent __org = null;
                    int cou = 0;
                    for (com.pullenti.ner.Token tt0 = t0.getPrevious(); tt0 != null; tt0 = tt0.getPrevious()) {
                        if (tt0.isNewlineAfter()) 
                            cou += 10;
                        if ((++cou) > 500) 
                            break;
                        java.util.ArrayList<com.pullenti.ner.Referent> rs0 = tt0.getReferents();
                        if (rs0 == null) 
                            continue;
                        boolean hasOrg = false;
                        for (com.pullenti.ner.Referent r0 : rs0) {
                            if (com.pullenti.unisharp.Utils.stringsEq(r0.getTypeName(), objNameOrg)) {
                                hasOrg = true;
                                if (tname1.isValue("БАНК", null)) {
                                    if (r0.findSlot("TYPE", "банк", true) == null) 
                                        continue;
                                }
                                if (tname1.isValue("ЧАСТЬ", "ЧАСТИНА")) {
                                    boolean ok1 = false;
                                    for (com.pullenti.ner.Slot s : r0.getSlots()) {
                                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                                            if ((s.getValue().toString()).endsWith("часть") || (s.getValue().toString()).endsWith("частина")) 
                                                ok1 = true;
                                        }
                                    }
                                    if (!ok1) 
                                        continue;
                                }
                                __org = r0;
                                break;
                            }
                        }
                        if (__org != null || hasOrg) 
                            break;
                    }
                    if (__org != null) {
                        pr.addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, __org, false, 0);
                        tname1 = null;
                    }
                }
            }
        }
        if (tname1 != null) {
            String s = com.pullenti.ner.core.MiscHelper.getTextValue(tname0, tname1, com.pullenti.ner.core.GetTextAttr.NO);
            if (s != null) 
                name = (name + " " + s.toLowerCase());
        }
        if (category != null) 
            name = (name + " " + category);
        else {
            com.pullenti.unisharp.Outargwrapper<String> wrapcategory2679 = new com.pullenti.unisharp.Outargwrapper<String>();
            com.pullenti.ner.Token tt = tryAttachCategory(t1.getNext(), wrapcategory2679);
            category = wrapcategory2679.value;
            if (tt != null) {
                name = (name + " " + category);
                t1 = tt;
            }
        }
        pr.setName(name);
        PersonAttrToken res = _new2680(t0, t1, PersonAttrTerminType.POSITION, pr, tok.getMorph());
        res.setBeIndependentProperty(((PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class)).canBeUniqueIdentifier);
        int i = name.indexOf("заместитель ");
        if (i < 0) 
            i = name.indexOf("заступник ");
        if (i >= 0) {
            i += 11;
            PersonAttrToken res1 = _new2651(t0, t1, PersonAttrTerminType.POSITION, tok.getMorph());
            res1.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
            res1.getPropRef().setName(name.substring(0, 0 + i));
            res1.getPropRef().setHigher(res.getPropRef());
            res1.higherPropRef = res;
            res.getPropRef().setName(name.substring(i + 1));
            return res1;
        }
        return res;
    }

    private static boolean _existsInDoctionary(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                return true;
        }
        return false;
    }

    private static boolean _isPerson(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t instanceof com.pullenti.ner.ReferentToken) 
            return t.getReferent() instanceof com.pullenti.ner.person.PersonReferent;
        if (!t.chars.isLetter() || t.chars.isAllLower()) 
            return false;
        com.pullenti.ner.ReferentToken rt00 = com.pullenti.ner.person.PersonAnalyzer.processReferentStat(t, null);
        return rt00 != null && (rt00.referent instanceof com.pullenti.ner.person.PersonReferent);
    }

    private static com.pullenti.ner.Token _analyzeRomanNums(com.pullenti.ner.Token t) {
        com.pullenti.ner.Token tt2 = t;
        if (tt2.isValue("В", null) && tt2.getNext() != null) 
            tt2 = tt2.getNext();
        com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt2);
        if (lat == null) 
            return null;
        tt2 = lat.getEndToken();
        if (tt2.getNext() != null && tt2.getNext().isHiphen()) {
            com.pullenti.ner.NumberToken lat2 = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt2.getNext().getNext());
            if (lat2 != null) 
                tt2 = lat2.getEndToken();
        }
        else if (tt2.getNext() != null && tt2.getNext().isCommaAnd()) {
            com.pullenti.ner.NumberToken lat2 = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt2.getNext().getNext());
            if (lat2 != null) {
                tt2 = lat2.getEndToken();
                for (com.pullenti.ner.Token tt = tt2.getNext(); tt != null; tt = tt.getNext()) {
                    if (!tt.isCommaAnd() && !tt.isHiphen()) 
                        break;
                    lat2 = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt.getNext());
                    if (lat2 == null) 
                        break;
                    tt2 = lat2.getEndToken();
                    if (tt.isAnd()) 
                        break;
                    tt = tt2;
                }
            }
        }
        if (tt2.getNext() != null && ((tt2.getNext().isValue("ВЕК", null) || tt2.getNext().isValue("СТОЛЕТИЕ", null) || tt2.getNext().isValue("СОЗЫВ", null)))) 
            return tt2.getNext();
        if (tt2.getNext() != null && tt2.getNext().isValue("В", null)) {
            tt2 = tt2.getNext();
            if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                tt2 = tt2.getNext();
            return tt2;
        }
        return null;
    }

    private static com.pullenti.ner.Token _analizeVise(com.pullenti.ner.Token t0, com.pullenti.unisharp.Outargwrapper<String> name) {
        if (t0 == null) 
            return null;
        if (t0.getPrevious() != null && t0.getPrevious().isHiphen() && (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken)) {
            if (t0.getPrevious().getPrevious().isValue("ВИЦЕ", "ВІЦЕ")) {
                t0 = t0.getPrevious().getPrevious();
                name.value = ((t0.kit.baseLanguage.isUa() ? "віце-" : "вице-")) + name.value;
            }
            if (t0.getPrevious() != null && t0.getPrevious().getPrevious() != null) {
                if (t0.getPrevious().getPrevious().isValue("ЭКС", "ЕКС")) {
                    t0 = t0.getPrevious().getPrevious();
                    name.value = ((t0.kit.baseLanguage.isUa() ? "екс-" : "экс-")) + name.value;
                }
                else if (t0.getPrevious().getPrevious().chars.equals(t0.chars) && !t0.isWhitespaceBefore() && !t0.getPrevious().isWhitespaceBefore()) {
                    com.pullenti.ner.core.NounPhraseToken npt00 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t0.getPrevious().getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt00 != null) {
                        name.value = npt00.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                        t0 = t0.getPrevious().getPrevious();
                    }
                }
            }
        }
        return t0;
    }

    private static com.pullenti.ner.Token tryAttachCategory(com.pullenti.ner.Token t, com.pullenti.unisharp.Outargwrapper<String> cat) {
        cat.value = null;
        if (t == null || t.getNext() == null) 
            return null;
        com.pullenti.ner.Token tt = null;
        int num = -1;
        if (t instanceof com.pullenti.ner.NumberToken) {
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() == null) 
                return null;
            num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
            tt = t;
        }
        else {
            com.pullenti.ner.NumberToken npt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if (npt != null && npt.getIntValue() != null) {
                num = npt.getIntValue();
                tt = npt.getEndToken();
            }
        }
        if ((num < 0) && ((t.isValue("ВЫСШИЙ", null) || t.isValue("ВЫСШ", null) || t.isValue("ВИЩИЙ", null)))) {
            num = 0;
            tt = t;
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                tt = tt.getNext();
        }
        if (tt == null || tt.getNext() == null || (num < 0)) 
            return null;
        tt = tt.getNext();
        if (tt.isValue("КАТЕГОРИЯ", null) || tt.isValue("КАТЕГОРІЯ", null) || tt.isValue("КАТ", null)) {
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                tt = tt.getNext();
            if (num == 0) 
                cat.value = (tt.kit.baseLanguage.isUa() ? "вищої категорії" : "высшей категории");
            else 
                cat.value = (tt.kit.baseLanguage.isUa() ? (((Integer)num).toString() + " категорії") : (((Integer)num).toString() + " категории"));
            return tt;
        }
        if (tt.isValue("РАЗРЯД", null) || tt.isValue("РОЗРЯД", null)) {
            if (num == 0) 
                cat.value = (tt.kit.baseLanguage.isUa() ? "вищого розряду" : "высшего разряда");
            else 
                cat.value = (tt.kit.baseLanguage.isUa() ? (((Integer)num).toString() + " розряду") : (((Integer)num).toString() + " разряда"));
            return tt;
        }
        if (tt.isValue("КЛАСС", null) || tt.isValue("КЛАС", null)) {
            if (num == 0) 
                cat.value = (tt.kit.baseLanguage.isUa() ? "вищого класу" : "высшего класса");
            else 
                cat.value = (tt.kit.baseLanguage.isUa() ? (((Integer)num).toString() + " класу") : (((Integer)num).toString() + " класса"));
            return tt;
        }
        if (tt.isValue("РАНГ", null)) {
            if (num == 0) 
                return null;
            else 
                cat.value = (((Integer)num).toString() + " ранга");
            return tt;
        }
        if (tt.isValue("СОЗЫВ", null) || tt.isValue("СКЛИКАННЯ", null)) {
            if (num == 0) 
                return null;
            else 
                cat.value = (tt.kit.baseLanguage.isUa() ? (((Integer)num).toString() + " скликання") : (((Integer)num).toString() + " созыва"));
            return tt;
        }
        return null;
    }

    private static final String objNameGeo = "GEO";

    private static final String objNameAddr = "ADDRESS";

    private static final String objNameOrg = "ORGANIZATION";

    private static final String objNameTransport = "TRANSPORT";

    private static final String objNameDate = "DATE";

    private static final String objNameDateRange = "DATERANGE";

    private static PersonAttrToken createAttrGrade(com.pullenti.ner.core.TerminToken tok) {
        com.pullenti.ner.Token t1 = _findGradeLast(tok.getEndToken().getNext(), tok.getBeginToken());
        if (t1 == null) 
            return null;
        com.pullenti.ner.person.PersonPropertyReferent pr = new com.pullenti.ner.person.PersonPropertyReferent();
        String ss = (String)com.pullenti.unisharp.Utils.cast(t1.tag, String.class);
        if (ss == null) 
            ss = "наук";
        pr.setName((tok.termin.getCanonicText().toLowerCase() + " " + ss));
        return _new2683(tok.getBeginToken(), t1, PersonAttrTerminType.POSITION, pr, tok.getMorph(), false);
    }

    private static com.pullenti.ner.Token _findGradeLast(com.pullenti.ner.Token t, com.pullenti.ner.Token t0) {
        int i = 0;
        com.pullenti.ner.core.TerminToken te = m_TerminsGrade.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (te != null) {
            te.getEndToken().tag = te.termin.getCanonicText().toLowerCase();
            return te.getEndToken();
        }
        com.pullenti.ner.Token tt = t;
        com.pullenti.ner.Token t1 = null;
        for (; t != null; t = t.getNext()) {
            if (t.isValue("НАУК", null)) {
                if (tt.getNext() == t && tt.getMorphClassInDictionary().isAdjective()) 
                    t.tag = com.pullenti.ner.core.MiscHelper.getTextValue(tt, t, com.pullenti.ner.core.GetTextAttr.NO).toLowerCase();
                t1 = t;
                i++;
                break;
            }
            if (t.isValue("Н", null)) {
                if (t0.getLengthChar() > 1 || !t0.chars.equals(t.chars)) 
                    return null;
                if ((t.getNext() != null && t.getNext().isHiphen() && t.getNext().getNext() != null) && t.getNext().getNext().isValue("К", null)) {
                    t1 = t.getNext().getNext();
                    break;
                }
                if (t.getNext() != null && t.getNext().isChar('.')) {
                    t1 = t.getNext();
                    break;
                }
            }
            if (!t.chars.isAllLower() && t0.chars.isAllLower()) 
                break;
            if ((++i) > 2) 
                break;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
            if (t.getNext() != null && t.getNext().isHiphen()) 
                t = t.getNext();
        }
        if (t1 == null || i == 0) 
            return null;
        return t1;
    }

    public static com.pullenti.ner.person.PersonPropertyKind checkKind(com.pullenti.ner.person.PersonPropertyReferent pr) {
        if (pr == null) 
            return com.pullenti.ner.person.PersonPropertyKind.UNDEFINED;
        String n = pr.getStringValue(com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME);
        if (n == null) 
            return com.pullenti.ner.person.PersonPropertyKind.UNDEFINED;
        n = n.toUpperCase();
        for (String nn : com.pullenti.unisharp.Utils.split(n, String.valueOf(' ') + String.valueOf('-'), false)) {
            java.util.ArrayList<com.pullenti.ner.core.Termin> li = m_Termins.findTerminsByString(nn, com.pullenti.morph.MorphLang.RU);
            if (li == null || li.size() == 0) 
                li = m_Termins.findTerminsByString(n, com.pullenti.morph.MorphLang.UA);
            if (li != null && li.size() > 0) {
                PersonAttrTermin pat = (PersonAttrTermin)com.pullenti.unisharp.Utils.cast(li.get(0), PersonAttrTermin.class);
                if (pat.isBoss) 
                    return com.pullenti.ner.person.PersonPropertyKind.BOSS;
                if (pat.isKin) 
                    return com.pullenti.ner.person.PersonPropertyKind.KIN;
                if (pat.typ == PersonAttrTerminType.KING) {
                    if (com.pullenti.unisharp.Utils.stringsNe(n, "ДОН")) 
                        return com.pullenti.ner.person.PersonPropertyKind.KING;
                }
                if (pat.isMilitaryRank) {
                    if (com.pullenti.unisharp.Utils.stringsEq(nn, "ВИЦЕ")) 
                        continue;
                    if (com.pullenti.unisharp.Utils.stringsEq(nn, "КАПИТАН") || com.pullenti.unisharp.Utils.stringsEq(nn, "CAPTAIN") || com.pullenti.unisharp.Utils.stringsEq(nn, "КАПІТАН")) {
                        com.pullenti.ner.Referent __org = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(pr.getSlotValue(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF), com.pullenti.ner.Referent.class);
                        if (__org != null && com.pullenti.unisharp.Utils.stringsEq(__org.getTypeName(), "ORGANIZATION")) 
                            continue;
                    }
                    return com.pullenti.ner.person.PersonPropertyKind.MILITARYRANK;
                }
                if (pat.isNation) 
                    return com.pullenti.ner.person.PersonPropertyKind.NATIONALITY;
            }
        }
        return com.pullenti.ner.person.PersonPropertyKind.UNDEFINED;
    }

    public static com.pullenti.ner.core.TerminToken tryAttachWord(com.pullenti.ner.Token t, boolean ignoreIo) {
        com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if ((tok != null && tok.getBeginToken() == tok.getEndToken() && t.getLengthChar() == 1) && t.isValue("Д", null)) {
            if (com.pullenti.ner.core.BracketHelper.isBracket(t.getNext(), true) && !t.isWhitespaceAfter()) 
                return null;
        }
        if (tok != null && com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ГРАФ")) {
            tok.setMorph(new com.pullenti.ner.MorphCollection(t.getMorph()));
            tok.getMorph().removeItems(com.pullenti.morph.MorphGender.MASCULINE);
        }
        if (tok != null && ignoreIo) {
            PersonAttrTermin pat = (PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class);
            if (pat.typ2 != PersonAttrTerminType2.UNDEFINED && pat.typ2 != PersonAttrTerminType2.GRADE) 
                return null;
        }
        return tok;
    }

    public static com.pullenti.ner.core.TerminToken tryAttachPositionWord(com.pullenti.ner.Token t) {
        com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        PersonAttrTermin pat = (PersonAttrTermin)com.pullenti.unisharp.Utils.cast(tok.termin, PersonAttrTermin.class);
        if (pat == null) 
            return null;
        if (pat.typ != PersonAttrTerminType.POSITION) 
            return null;
        if (pat.typ2 != PersonAttrTerminType2.IO2 && pat.typ2 != PersonAttrTerminType2.UNDEFINED) 
            return null;
        return tok;
    }

    public static PersonAttrToken _new2646(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }

    public static PersonAttrToken _new2647(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static PersonAttrToken _new2649(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.age = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static PersonAttrToken _new2651(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static PersonAttrToken _new2653(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, String _arg4) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static PersonAttrToken _new2659(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5, com.pullenti.morph.MorphGender _arg6) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        res.gender = _arg6;
        return res;
    }

    public static PersonAttrToken _new2662(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, PersonAttrToken _arg4) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.higherPropRef = _arg4;
        return res;
    }

    public static PersonAttrToken _new2668(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.person.PersonPropertyReferent _arg3, PersonAttrTerminType _arg4) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.setPropRef(_arg3);
        res.typ = _arg4;
        return res;
    }

    public static PersonAttrToken _new2680(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, com.pullenti.ner.person.PersonPropertyReferent _arg4, com.pullenti.ner.MorphCollection _arg5) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setPropRef(_arg4);
        res.setMorph(_arg5);
        return res;
    }

    public static PersonAttrToken _new2683(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, PersonAttrTerminType _arg3, com.pullenti.ner.person.PersonPropertyReferent _arg4, com.pullenti.ner.MorphCollection _arg5, boolean _arg6) {
        PersonAttrToken res = new PersonAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setPropRef(_arg4);
        res.setMorph(_arg5);
        res.setBeIndependentProperty(_arg6);
        return res;
    }

    public static class PersonAttrAttachAttrs implements Comparable<PersonAttrAttachAttrs> {
    
        public static final PersonAttrAttachAttrs NO; // 0
    
        public static final PersonAttrAttachAttrs AFTERZAMESTITEL; // 1
    
        public static final PersonAttrAttachAttrs ONLYKEYWORD; // 2
    
        public static final PersonAttrAttachAttrs INPROCESS; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private PersonAttrAttachAttrs(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(PersonAttrAttachAttrs v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, PersonAttrAttachAttrs> mapIntToEnum; 
        private static java.util.HashMap<String, PersonAttrAttachAttrs> mapStringToEnum; 
        private static PersonAttrAttachAttrs[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static PersonAttrAttachAttrs of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            PersonAttrAttachAttrs item = new PersonAttrAttachAttrs(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static PersonAttrAttachAttrs of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static PersonAttrAttachAttrs[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, PersonAttrAttachAttrs>();
            mapStringToEnum = new java.util.HashMap<String, PersonAttrAttachAttrs>();
            NO = new PersonAttrAttachAttrs(0, "NO");
            mapIntToEnum.put(NO.value(), NO);
            mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
            AFTERZAMESTITEL = new PersonAttrAttachAttrs(1, "AFTERZAMESTITEL");
            mapIntToEnum.put(AFTERZAMESTITEL.value(), AFTERZAMESTITEL);
            mapStringToEnum.put(AFTERZAMESTITEL.m_str.toUpperCase(), AFTERZAMESTITEL);
            ONLYKEYWORD = new PersonAttrAttachAttrs(2, "ONLYKEYWORD");
            mapIntToEnum.put(ONLYKEYWORD.value(), ONLYKEYWORD);
            mapStringToEnum.put(ONLYKEYWORD.m_str.toUpperCase(), ONLYKEYWORD);
            INPROCESS = new PersonAttrAttachAttrs(4, "INPROCESS");
            mapIntToEnum.put(INPROCESS.value(), INPROCESS);
            mapStringToEnum.put(INPROCESS.m_str.toUpperCase(), INPROCESS);
            java.util.Collection<PersonAttrAttachAttrs> col = mapIntToEnum.values();
            m_Values = new PersonAttrAttachAttrs[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public PersonAttrToken() {
        super();
    }
    public static PersonAttrToken _globalInstance;
    
    static {
        try { _globalInstance = new PersonAttrToken(); } 
        catch(Exception e) { }
        m_EmptyAdjs = new String[] {"УСПЕШНЫЙ", "ИЗВЕСТНЫЙ", "ЗНАМЕНИТЫЙ", "ИЗВЕСТНЕЙШИЙ", "ПОПУЛЯРНЫЙ", "ГЕНИАЛЬНЫЙ", "ТАЛАНТЛИВЫЙ", "МОЛОДОЙ", "УСПІШНИЙ", "ВІДОМИЙ", "ЗНАМЕНИТИЙ", "ВІДОМИЙ", "ПОПУЛЯРНИЙ", "ГЕНІАЛЬНИЙ", "ТАЛАНОВИТИЙ", "МОЛОДИЙ"};
        m_StdForms = new java.util.HashMap<String, java.util.ArrayList<com.pullenti.morph.MorphWordForm>>();
    }
}
