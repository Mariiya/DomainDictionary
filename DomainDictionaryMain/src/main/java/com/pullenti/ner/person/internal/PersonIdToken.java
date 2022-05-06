/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonIdToken extends com.pullenti.ner.MetaToken {

    public PersonIdToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        if(_globalInstance == null) return;
    }

    public Typs typ = Typs.KEYWORD;

    public String value;

    public com.pullenti.ner.Referent referent;

    public boolean hasPrefix;

    public static com.pullenti.ner.ReferentToken tryAttach(com.pullenti.ner.Token t) {
        if (t == null || !t.chars.isLetter()) 
            return null;
        PersonIdToken noun = tryParse(t, null);
        if (noun == null) 
            return null;
        java.util.ArrayList<PersonIdToken> li = new java.util.ArrayList<PersonIdToken>();
        for (t = noun.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            if (t.isCharOf(",:")) 
                continue;
            PersonIdToken idt = tryParse(t, (li.size() > 0 ? li.get(li.size() - 1) : noun));
            if (idt == null) {
                if (t.isValue("ОТДЕЛ", null) || t.isValue("ОТДЕЛЕНИЕ", null)) 
                    continue;
                break;
            }
            if (idt.typ == Typs.KEYWORD) 
                break;
            li.add(idt);
            t = idt.getEndToken();
        }
        if (li.size() == 0) 
            return null;
        String num = null;
        int i = 0;
        if (li.get(0).typ == Typs.NUMBER) {
            if (li.size() > 1 && li.get(1).typ == Typs.NUMBER && li.get(1).hasPrefix) {
                num = li.get(0).value + li.get(1).value;
                i = 2;
            }
            else {
                num = li.get(0).value;
                i = 1;
            }
        }
        else if (li.get(0).typ == Typs.SERIA && li.size() > 1 && li.get(1).typ == Typs.NUMBER) {
            num = li.get(0).value + li.get(1).value;
            i = 2;
        }
        else if (li.get(0).typ == Typs.SERIA && li.get(0).value.length() > 5) {
            num = li.get(0).value;
            i = 1;
        }
        else if (li.size() > 1 && li.get(0).typ == Typs.ORG && li.get(1).typ == Typs.NUMBER) {
            i = 0;
            num = li.get(1).value;
        }
        else 
            return null;
        com.pullenti.ner.person.PersonIdentityReferent pid = new com.pullenti.ner.person.PersonIdentityReferent();
        pid.setTyp(noun.value.toLowerCase());
        pid.setNumber(num);
        if (noun.referent instanceof com.pullenti.ner.geo.GeoReferent) 
            pid.setState(noun.referent);
        for (; i < li.size(); i++) {
            if (li.get(i).typ == Typs.VIDAN || li.get(i).typ == Typs.CODE) {
            }
            else if (li.get(i).typ == Typs.DATE && li.get(i).referent != null) {
                if (pid.findSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_DATE, null, true) != null) 
                    break;
                pid.addSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_DATE, li.get(i).referent, false, 0);
            }
            else if (li.get(i).typ == Typs.ADDRESS && li.get(i).referent != null) {
                if (pid.findSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ADDRESS, null, true) != null) 
                    break;
                pid.addSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ADDRESS, li.get(i).referent, false, 0);
            }
            else if (li.get(i).typ == Typs.ORG && li.get(i).referent != null) {
                if (pid.findSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ORG, null, true) != null) 
                    break;
                pid.addSlot(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ORG, li.get(i).referent, false, 0);
            }
            else 
                break;
        }
        return new com.pullenti.ner.ReferentToken(pid, noun.getBeginToken(), li.get(i - 1).getEndToken(), null);
    }

    private static PersonIdToken tryParse(com.pullenti.ner.Token t, PersonIdToken prev) {
        if (t == null) 
            return null;
        if (t.isValue("СВИДЕТЕЛЬСТВО", null)) {
            com.pullenti.ner.Token tt1 = t;
            boolean ip = false;
            boolean reg = false;
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isCommaAnd() || tt.getMorph()._getClass().isPreposition()) 
                    continue;
                if (tt.isValue("РЕГИСТРАЦИЯ", null) || tt.isValue("РЕЕСТР", null) || tt.isValue("ЗАРЕГИСТРИРОВАТЬ", null)) {
                    reg = true;
                    tt1 = tt;
                }
                else if (tt.isValue("ИНДИВИДУАЛЬНЫЙ", null) || tt.isValue("ИП", null)) {
                    ip = true;
                    tt1 = tt;
                }
                else if ((tt.isValue("ВНЕСЕНИЕ", null) || tt.isValue("ГОСУДАРСТВЕННЫЙ", null) || tt.isValue("ЕДИНЫЙ", null)) || tt.isValue("ЗАПИСЬ", null) || tt.isValue("ПРЕДПРИНИМАТЕЛЬ", null)) 
                    tt1 = tt;
                else if (tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "DATERANGE")) 
                    tt1 = tt;
                else 
                    break;
            }
            if (reg && ip) 
                return _new2710(t, tt1, Typs.KEYWORD, "СВИДЕТЕЛЬСТВО О ГОСУДАРСТВЕННОЙ РЕГИСТРАЦИИ ФИЗИЧЕСКОГО ЛИЦА В КАЧЕСТВЕ ИНДИВИДУАЛЬНОГО ПРЕДПРИНИМАТЕЛЯ");
        }
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            Typs ty = (Typs)tok.termin.tag;
            PersonIdToken res = _new2710(tok.getBeginToken(), tok.getEndToken(), ty, tok.termin.getCanonicText());
            if (prev == null) {
                if (ty != Typs.KEYWORD) 
                    return null;
                for (t = tok.getEndToken().getNext(); t != null; t = t.getNext()) {
                    com.pullenti.ner.Referent r = t.getReferent();
                    if (r != null && (r instanceof com.pullenti.ner.geo.GeoReferent)) {
                        res.referent = r;
                        res.setEndToken(t);
                        continue;
                    }
                    if (t.isValue("ГРАЖДАНИН", null) && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        res.referent = t.getNext().getReferent();
                        t = res.setEndToken(t.getNext());
                        continue;
                    }
                    if (r != null) 
                        break;
                    PersonAttrToken ait = PersonAttrToken.tryAttach(t, PersonAttrToken.PersonAttrAttachAttrs.NO);
                    if (ait != null) {
                        if (ait.referent != null) {
                            for (com.pullenti.ner.Slot s : ait.referent.getSlots()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                                    res.referent = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                            }
                        }
                        res.setEndToken(ait.getEndToken());
                        break;
                    }
                    if (t.isValue("ДАННЫЙ", null)) {
                        res.setEndToken(t);
                        continue;
                    }
                    break;
                }
                if ((res.referent instanceof com.pullenti.ner.geo.GeoReferent) && !((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(res.referent, com.pullenti.ner.geo.GeoReferent.class)).isState()) 
                    res.referent = null;
                return res;
            }
            if (ty == Typs.NUMBER) {
                StringBuilder tmp = new StringBuilder();
                com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                if (tt != null && tt.isChar(':')) 
                    tt = tt.getNext();
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isNewlineBefore()) 
                        break;
                    if (!(tt instanceof com.pullenti.ner.NumberToken) && !tt.isCharOf("\\/")) 
                        break;
                    tmp.append(tt.getSourceText());
                    res.setEndToken(tt);
                }
                if (tmp.length() < 1) 
                    return null;
                res.value = tmp.toString();
                res.hasPrefix = true;
                return res;
            }
            if (ty == Typs.SERIA) {
                StringBuilder tmp = new StringBuilder();
                com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                if (tt != null && tt.isChar(':')) 
                    tt = tt.getNext();
                boolean nextNum = false;
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isNewlineBefore()) 
                        break;
                    if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt) != null) {
                        nextNum = true;
                        break;
                    }
                    if (!(tt instanceof com.pullenti.ner.NumberToken)) {
                        if (!(tt instanceof com.pullenti.ner.TextToken)) 
                            break;
                        if (!tt.chars.isAllUpper()) 
                            break;
                        com.pullenti.ner.NumberToken nu = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt);
                        if (nu != null) {
                            tmp.append(nu.getSourceText());
                            tt = nu.getEndToken();
                        }
                        else if (tt.getLengthChar() != 2) 
                            break;
                        else {
                            tmp.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                            res.setEndToken(tt);
                        }
                        if (tt.getNext() != null && tt.getNext().isHiphen()) 
                            tt = tt.getNext();
                        continue;
                    }
                    if (tmp.length() >= 4) 
                        break;
                    tmp.append(tt.getSourceText());
                    res.setEndToken(tt);
                }
                if (tmp.length() < 4) {
                    if (tmp.length() < 2) 
                        return null;
                    com.pullenti.ner.Token tt1 = res.getEndToken().getNext();
                    if (tt1 != null && tt1.isComma()) 
                        tt1 = tt1.getNext();
                    PersonIdToken _next = tryParse(tt1, res);
                    if (_next != null && _next.typ == Typs.NUMBER) {
                    }
                    else 
                        return null;
                }
                res.value = tmp.toString();
                res.hasPrefix = true;
                return res;
            }
            if (ty == Typs.CODE) {
                for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isCharOf(":") || tt.isHiphen()) 
                        continue;
                    if (tt instanceof com.pullenti.ner.NumberToken) {
                        res.setEndToken(tt);
                        continue;
                    }
                    break;
                }
            }
            if (ty == Typs.ADDRESS) {
                if (t.getReferent() instanceof com.pullenti.ner.address.AddressReferent) {
                    res.referent = t.getReferent();
                    res.setEndToken(t);
                    return res;
                }
                for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isCharOf(":") || tt.isHiphen() || tt.getMorph()._getClass().isPreposition()) 
                        continue;
                    if (tt.getReferent() instanceof com.pullenti.ner.address.AddressReferent) {
                        res.referent = tt.getReferent();
                        res.setEndToken(tt);
                    }
                    break;
                }
                if (res.referent == null) 
                    return null;
            }
            return res;
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null) {
            if (npt.getEndToken().isValue("УДОСТОВЕРЕНИЕ", "ПОСВІДЧЕННЯ")) 
                return _new2710(t, npt.getEndToken(), Typs.KEYWORD, npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
        }
        if (prev == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t0);
        if (t1 != null) 
            t = t1;
        if (t instanceof com.pullenti.ner.NumberToken) {
            StringBuilder tmp = new StringBuilder();
            PersonIdToken res = _new2713(t0, t, Typs.NUMBER);
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore() || !(tt instanceof com.pullenti.ner.NumberToken)) 
                    break;
                tmp.append(tt.getSourceText());
                res.setEndToken(tt);
            }
            if (tmp.length() < 4) {
                if (tmp.length() < 2) 
                    return null;
                if (prev == null || prev.typ != Typs.KEYWORD) 
                    return null;
                PersonIdToken ne = tryParse(res.getEndToken().getNext(), prev);
                if (ne != null && ne.typ == Typs.NUMBER) 
                    res.typ = Typs.SERIA;
                else 
                    return null;
            }
            res.value = tmp.toString();
            if (t0 != t) 
                res.hasPrefix = true;
            return res;
        }
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.Referent r = t.getReferent();
            if (r != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE")) 
                    return _new2714(t, t, Typs.DATE, r);
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) 
                    return _new2714(t, t, Typs.ORG, r);
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ADDRESS")) 
                    return _new2714(t, t, Typs.ADDRESS, r);
            }
        }
        if ((prev != null && prev.typ == Typs.KEYWORD && (t instanceof com.pullenti.ner.TextToken)) && !t.chars.isAllLower() && t.chars.isLetter()) {
            PersonIdToken rr = tryParse(t.getNext(), prev);
            if (rr != null && rr.typ == Typs.NUMBER) 
                return _new2710(t, t, Typs.SERIA, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
        }
        if ((t != null && t.isValue("ОТ", "ВІД") && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && com.pullenti.unisharp.Utils.stringsEq(t.getNext().getReferent().getTypeName(), "DATE")) 
            return _new2714(t, t.getNext(), Typs.DATE, t.getNext().getReferent());
        return null;
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new100("ПАСПОРТ", Typs.KEYWORD);
        t.addVariant("ПАССПОРТ", false);
        t.addVariant("ПАСПОРТНЫЕ ДАННЫЕ", false);
        t.addVariant("ВНУТРЕННИЙ ПАСПОРТ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЗАГРАНИЧНЫЙ ПАСПОРТ", Typs.KEYWORD);
        t.addVariant("ЗАГРАНПАСПОРТ", false);
        t.addAbridge("ЗАГРАН. ПАСПОРТ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("УДОСТОВЕРЕНИЕ ЛИЧНОСТИ", Typs.KEYWORD);
        t.addVariant("УДОСТОВЕРЕНИЕ ЛИЧНОСТИ ОФИЦЕРА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("СВИДЕТЕЛЬСТВО О ГОСУДАРСТВЕННОЙ РЕГИСТРАЦИИ ФИЗИЧЕСКОГО ЛИЦА В КАЧЕСТВЕ ИНДИВИДУАЛЬНОГО ПРЕДПРИНИМАТЕЛЯ", Typs.KEYWORD);
        t.addVariant("СВИДЕТЕЛЬСТВО О ГОСУДАРСТВЕННОЙ РЕГИСТРАЦИИ ФИЗИЧЕСКОГО ЛИЦА В КАЧЕСТВЕ ИП", false);
        t.addVariant("СВИДЕТЕЛЬСТВО О ГОСРЕГИСТРАЦИИ ФИЗЛИЦА В КАЧЕСТВЕ ИП", false);
        t.addVariant("СВИДЕТЕЛЬСТВО ГОСУДАРСТВЕННОЙ РЕГИСТРАЦИИ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВОДИТЕЛЬСКОЕ УДОСТОВЕРЕНИЕ", Typs.KEYWORD);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЛИЦЕНЗИЯ", Typs.KEYWORD);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("СЕРИЯ", Typs.SERIA);
        t.addAbridge("СЕР.");
        t.addVariant("СЕРИ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("НОМЕР", Typs.NUMBER);
        t.addAbridge("НОМ.");
        t.addAbridge("Н-Р");
        t.addVariant("№", false);
        t.addVariant("N", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЫДАТЬ", Typs.VIDAN);
        t.addVariant("ВЫДАВАТЬ", false);
        t.addVariant("ДАТА ВЫДАЧИ", false);
        t.addVariant("ДАТА РЕГИСТРАЦИИ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("КОД ПОДРАЗДЕЛЕНИЯ", Typs.CODE);
        t.addAbridge("К/П");
        t.addAbridge("К.П.");
        t.addVariant("КОД", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("РЕГИСТРАЦИЯ", Typs.ADDRESS);
        t.addVariant("ЗАРЕГИСТРИРОВАН", false);
        t.addVariant("АДРЕС РЕГИСТРАЦИИ", false);
        t.addVariant("ЗАРЕГИСТРИРОВАННЫЙ", false);
        t.addAbridge("ПРОПИСАН");
        t.addVariant("АДРЕС ПРОПИСКИ", false);
        t.addVariant("АДРЕС ПО ПРОПИСКЕ", false);
        m_Ontology.add(t);
    }

    public static PersonIdToken _new2710(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, String _arg4) {
        PersonIdToken res = new PersonIdToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static PersonIdToken _new2713(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3) {
        PersonIdToken res = new PersonIdToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static PersonIdToken _new2714(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Typs _arg3, com.pullenti.ner.Referent _arg4) {
        PersonIdToken res = new PersonIdToken(_arg1, _arg2);
        res.typ = _arg3;
        res.referent = _arg4;
        return res;
    }

    public static class Typs implements Comparable<Typs> {
    
        public static final Typs KEYWORD; // 0
    
        public static final Typs SERIA; // 1
    
        public static final Typs NUMBER; // 2
    
        public static final Typs DATE; // 3
    
        public static final Typs ORG; // 4
    
        public static final Typs VIDAN; // 5
    
        public static final Typs CODE; // 6
    
        public static final Typs ADDRESS; // 7
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Typs(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Typs v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Typs> mapIntToEnum; 
        private static java.util.HashMap<String, Typs> mapStringToEnum; 
        private static Typs[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Typs of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Typs item = new Typs(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Typs of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Typs[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Typs>();
            mapStringToEnum = new java.util.HashMap<String, Typs>();
            KEYWORD = new Typs(0, "KEYWORD");
            mapIntToEnum.put(KEYWORD.value(), KEYWORD);
            mapStringToEnum.put(KEYWORD.m_str.toUpperCase(), KEYWORD);
            SERIA = new Typs(1, "SERIA");
            mapIntToEnum.put(SERIA.value(), SERIA);
            mapStringToEnum.put(SERIA.m_str.toUpperCase(), SERIA);
            NUMBER = new Typs(2, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            DATE = new Typs(3, "DATE");
            mapIntToEnum.put(DATE.value(), DATE);
            mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
            ORG = new Typs(4, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            VIDAN = new Typs(5, "VIDAN");
            mapIntToEnum.put(VIDAN.value(), VIDAN);
            mapStringToEnum.put(VIDAN.m_str.toUpperCase(), VIDAN);
            CODE = new Typs(6, "CODE");
            mapIntToEnum.put(CODE.value(), CODE);
            mapStringToEnum.put(CODE.m_str.toUpperCase(), CODE);
            ADDRESS = new Typs(7, "ADDRESS");
            mapIntToEnum.put(ADDRESS.value(), ADDRESS);
            mapStringToEnum.put(ADDRESS.m_str.toUpperCase(), ADDRESS);
            java.util.Collection<Typs> col = mapIntToEnum.values();
            m_Values = new Typs[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public PersonIdToken() {
        super();
    }
    public static PersonIdToken _globalInstance;
    
    static {
        try { _globalInstance = new PersonIdToken(); } 
        catch(Exception e) { }
    }
}
