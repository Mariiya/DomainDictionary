/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemEponymToken extends com.pullenti.ner.MetaToken {

    private OrgItemEponymToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public java.util.ArrayList<String> eponyms = new java.util.ArrayList<String>();

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("имени");
        for (String e : eponyms) {
            res.append(" ").append(e);
        }
        return res.toString();
    }

    public static OrgItemEponymToken tryAttach(com.pullenti.ner.Token t, boolean mustHasPrefix) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) {
            if (t == null) 
                return null;
            com.pullenti.ner.Referent r1 = t.getReferent();
            if (r1 != null && com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), "DATE")) {
                String str = r1.toString().toUpperCase();
                if ((com.pullenti.unisharp.Utils.stringsEq(str, "1 МАЯ") || com.pullenti.unisharp.Utils.stringsEq(str, "7 ОКТЯБРЯ") || com.pullenti.unisharp.Utils.stringsEq(str, "9 МАЯ")) || com.pullenti.unisharp.Utils.stringsEq(str, "8 МАРТА")) {
                    OrgItemEponymToken dt = _new1960(t, t, new java.util.ArrayList<String>());
                    dt.eponyms.add(str);
                    return dt;
                }
            }
            com.pullenti.ner.NumberToken age = com.pullenti.ner.core.NumberHelper.tryParseAge(t);
            if ((age != null && (((age.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) || (age.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken))) && (age.getWhitespacesAfterCount() < 3)) && !age.getEndToken().getNext().chars.isAllLower() && age.getEndToken().getNext().chars.isCyrillicLetter()) {
                OrgItemEponymToken dt = _new1960(t, age.getEndToken().getNext(), new java.util.ArrayList<String>());
                dt.eponyms.add((age.getValue() + " " + dt.getEndToken().getSourceText().toUpperCase()));
                return dt;
            }
            return null;
        }
        com.pullenti.ner.Token t1 = null;
        boolean full = false;
        boolean hasName = false;
        if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ИМЕНИ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ІМЕНІ")) {
            t1 = t.getNext();
            full = true;
            hasName = true;
        }
        else if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "ИМ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ІМ"))) && tt.getNext() != null) {
            if (tt.getNext().isChar('.')) {
                t1 = tt.getNext().getNext();
                full = true;
            }
            else if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.chars.isAllLower() && !tt.getNext().chars.isAllLower()) 
                t1 = tt.getNext();
            hasName = true;
        }
        else if (tt.getPrevious() != null && ((tt.getPrevious().isValue("ФОНД", null) || tt.getPrevious().isValue("ХРАМ", null) || tt.getPrevious().isValue("ЦЕРКОВЬ", "ЦЕРКВА")))) {
            if ((!tt.chars.isCyrillicLetter() || tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) || !tt.chars.isLetter()) 
                return null;
            if (tt.getWhitespacesBeforeCount() != 1) 
                return null;
            if (tt.chars.isAllLower()) 
                return null;
            if (tt.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && npt.getBeginToken() != npt.getEndToken()) 
                    return null;
            }
            OrgItemNameToken na = OrgItemNameToken.tryAttach(tt, null, false, true);
            if (na != null) {
                if (na.isEmptyWord || na.isStdName || na.isStdTail) 
                    return null;
            }
            t1 = tt;
        }
        if (t1 == null || ((t1.isNewlineBefore() && !full))) 
            return null;
        if (tt.getPrevious() != null && tt.getPrevious().getMorph()._getClass().isPreposition()) 
            return null;
        if (mustHasPrefix && !hasName) 
            return null;
        com.pullenti.ner.Referent r = t1.getReferent();
        if ((r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") && full) && r.findSlot("DAY", null, true) != null && r.findSlot("YEAR", null, true) == null) {
            OrgItemEponymToken dt = _new1960(t, t1, new java.util.ArrayList<String>());
            dt.eponyms.add(r.toString().toUpperCase());
            return dt;
        }
        boolean holy = false;
        if ((t1.isValue("СВЯТОЙ", null) || t1.isValue("СВЯТИЙ", null) || t1.isValue("СВ", null)) || t1.isValue("СВЯТ", null)) {
            t1 = t1.getNext();
            holy = true;
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
        }
        if (t1 == null) 
            return null;
        com.pullenti.morph.MorphClass cl = t1.getMorphClassInDictionary();
        if (cl.isNoun() || cl.isAdjective()) {
            com.pullenti.ner.ReferentToken rt = t1.kit.processReferent("PERSON", t1, null);
            if (rt != null && com.pullenti.unisharp.Utils.stringsEq(rt.referent.getTypeName(), "PERSON") && rt.getBeginToken() != rt.getEndToken()) {
                String e = rt.referent.getStringValue("LASTNAME");
                if (e != null) {
                    if (rt.getEndToken().isValue(e, null)) {
                        OrgItemEponymToken re = new OrgItemEponymToken(t, rt.getEndToken());
                        re.eponyms.add(rt.getEndToken().getSourceText());
                        return re;
                    }
                }
            }
        }
        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(t1);
        if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.AGE) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(nt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                String s = (nt.getValue() + "-" + (t.kit.baseLanguage.isUa() ? "РОКІВ" : "ЛЕТ") + " " + com.pullenti.ner.core.MiscHelper.getTextValue(npt.getBeginToken(), npt.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                OrgItemEponymToken res = new OrgItemEponymToken(t, npt.getEndToken());
                res.eponyms.add(s);
                return res;
            }
        }
        java.util.ArrayList<PersonItemToken> its = PersonItemToken.tryAttach(t1);
        if (its == null) {
            if ((t1 instanceof com.pullenti.ner.ReferentToken) && (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                String s = com.pullenti.ner.core.MiscHelper.getTextValue(t1, t1, com.pullenti.ner.core.GetTextAttr.NO);
                OrgItemEponymToken re = new OrgItemEponymToken(t, t1);
                re.eponyms.add(s);
                return re;
            }
            return null;
        }
        java.util.ArrayList<String> eponims = new java.util.ArrayList<String>();
        int i = 0;
        int j;
        if (its.get(i).typ == PersonItemType.LOCASEWORD) 
            i++;
        if (i >= its.size()) 
            return null;
        if (!full) {
            if (its.get(i).getBeginToken().getMorph()._getClass().isAdjective() && !its.get(i).getBeginToken().getMorph()._getClass().isProperSurname()) 
                return null;
        }
        if (its.get(i).typ == PersonItemType.INITIAL) {
            i++;
            while (true) {
                if ((i < its.size()) && its.get(i).typ == PersonItemType.INITIAL) 
                    i++;
                if (i >= its.size() || ((its.get(i).typ != PersonItemType.SURNAME && its.get(i).typ != PersonItemType.NAME))) 
                    break;
                eponims.add(its.get(i).value);
                t1 = its.get(i).getEndToken();
                if ((i + 2) >= its.size() || its.get(i + 1).typ != PersonItemType.AND || its.get(i + 2).typ != PersonItemType.INITIAL) 
                    break;
                i += 3;
            }
        }
        else if (((i + 1) < its.size()) && its.get(i).typ == PersonItemType.NAME && its.get(i + 1).typ == PersonItemType.SURNAME) {
            eponims.add(its.get(i + 1).value);
            t1 = its.get(i + 1).getEndToken();
            i += 2;
            if ((((i + 2) < its.size()) && its.get(i).typ == PersonItemType.AND && its.get(i + 1).typ == PersonItemType.NAME) && its.get(i + 2).typ == PersonItemType.SURNAME) {
                eponims.add(its.get(i + 2).value);
                t1 = its.get(i + 2).getEndToken();
            }
        }
        else if (its.get(i).typ == PersonItemType.SURNAME) {
            if (its.size() == (i + 2) && its.get(i).chars.equals(its.get(i + 1).chars)) {
                its.get(i).value += (" " + its.get(i + 1).value);
                its.get(i).setEndToken(its.get(i + 1).getEndToken());
                its.remove(i + 1);
            }
            eponims.add(its.get(i).value);
            if (((i + 1) < its.size()) && its.get(i + 1).typ == PersonItemType.NAME) {
                if ((i + 2) == its.size()) 
                    i++;
                else if (its.get(i + 2).typ != PersonItemType.SURNAME) 
                    i++;
            }
            else if (((i + 1) < its.size()) && its.get(i + 1).typ == PersonItemType.INITIAL) {
                if ((i + 2) == its.size()) 
                    i++;
                else if (its.get(i + 2).typ == PersonItemType.INITIAL && (i + 3) == its.size()) 
                    i += 2;
            }
            else if (((i + 2) < its.size()) && its.get(i + 1).typ == PersonItemType.AND && its.get(i + 2).typ == PersonItemType.SURNAME) {
                boolean ok = true;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(its.get(i + 2).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && !npt.getMorph().getCase().isGenitive() && !npt.getMorph().getCase().isUndefined()) 
                    ok = false;
                if (ok) {
                    eponims.add(its.get(i + 2).value);
                    i += 2;
                }
            }
            t1 = its.get(i).getEndToken();
        }
        else if (its.get(i).typ == PersonItemType.NAME && holy) {
            t1 = its.get(i).getEndToken();
            boolean sec = false;
            if (((i + 1) < its.size()) && its.get(i).chars.equals(its.get(i + 1).chars) && its.get(i + 1).typ != PersonItemType.INITIAL) {
                sec = true;
                t1 = its.get(i + 1).getEndToken();
            }
            if (sec) 
                eponims.add(("СВЯТ." + its.get(i).value + " " + its.get(i + 1).value));
            else 
                eponims.add(("СВЯТ." + its.get(i).value));
        }
        else if (full && (i + 1) == its.size() && ((its.get(i).typ == PersonItemType.NAME || its.get(i).typ == PersonItemType.SURNAME))) {
            t1 = its.get(i).getEndToken();
            eponims.add(its.get(i).value);
        }
        else if ((its.get(i).typ == PersonItemType.NAME && its.size() == 3 && its.get(i + 1).typ == PersonItemType.NAME) && its.get(i + 2).typ == PersonItemType.SURNAME) {
            t1 = its.get(i + 2).getEndToken();
            eponims.add((its.get(i).value + " " + its.get(i + 1).value + " " + its.get(i + 2).value));
            i += 2;
        }
        if (eponims.size() == 0) 
            return null;
        return _new1960(t, t1, eponims);
    }

    public static class PersonItemToken extends com.pullenti.ner.MetaToken {
    
        private PersonItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
            super(begin, end, null);
        }
    
        public com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType typ = com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.SURNAME;
    
        public String value;
    
        @Override
        public String toString() {
            return (typ.toString() + " " + ((value != null ? value : "")));
        }
    
        public static java.util.ArrayList<PersonItemToken> tryAttach(com.pullenti.ner.Token t) {
            java.util.ArrayList<PersonItemToken> res = new java.util.ArrayList<PersonItemToken>();
            for (; t != null; t = t.getNext()) {
                if (t.isNewlineBefore() && res.size() > 0) 
                    break;
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                if (tt == null) 
                    break;
                String s = tt.term;
                if (!Character.isLetter(s.charAt(0))) 
                    break;
                if (((s.length() == 1 || com.pullenti.unisharp.Utils.stringsEq(s, "ДЖ"))) && !tt.chars.isAllLower()) {
                    com.pullenti.ner.Token t1 = t;
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.add(_new1964(t, t1, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.INITIAL, s));
                    t = t1;
                    continue;
                }
                if (tt.isAnd()) {
                    res.add(_new1965(t, t, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.AND));
                    continue;
                }
                if (tt.getMorph()._getClass().isPronoun() || tt.getMorph()._getClass().isPersonalPronoun()) 
                    break;
                if (tt.chars.isAllLower()) {
                    com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                    if (mc.isPreposition() || mc.isVerb() || mc.isAdverb()) 
                        break;
                    com.pullenti.ner.Token t1 = t;
                    if (t1.getNext() != null && !t1.isWhitespaceAfter() && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.add(_new1964(t, t1, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.LOCASEWORD, s));
                    t = t1;
                    continue;
                }
                if (tt.getMorph()._getClass().isProperName()) 
                    res.add(_new1964(t, t, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.NAME, s));
                else if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && !t.getNext().isWhitespaceAfter()) {
                    res.add(_new1964(t, t.getNext().getNext(), com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.SURNAME, (s + "-" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class)).term)));
                    t = t.getNext().getNext();
                }
                else 
                    res.add(_new1964(t, t, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType.SURNAME, s));
            }
            return (res.size() > 0 ? res : null);
        }
    
        public static PersonItemToken _new1964(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType _arg3, String _arg4) {
            PersonItemToken res = new PersonItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            return res;
        }
    
        public static PersonItemToken _new1965(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner._org.internal.OrgItemEponymToken.PersonItemType _arg3) {
            PersonItemToken res = new PersonItemToken(_arg1, _arg2);
            res.typ = _arg3;
            return res;
        }
        public PersonItemToken() {
            super();
        }
    }


    public static OrgItemEponymToken _new1960(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, java.util.ArrayList<String> _arg3) {
        OrgItemEponymToken res = new OrgItemEponymToken(_arg1, _arg2);
        res.eponyms = _arg3;
        return res;
    }

    public static class PersonItemType implements Comparable<PersonItemType> {
    
        public static final PersonItemType SURNAME; // 0
    
        public static final PersonItemType NAME; // 1
    
        public static final PersonItemType INITIAL; // 2
    
        public static final PersonItemType AND; // 3
    
        public static final PersonItemType LOCASEWORD; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private PersonItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(PersonItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, PersonItemType> mapIntToEnum; 
        private static java.util.HashMap<String, PersonItemType> mapStringToEnum; 
        private static PersonItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static PersonItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            PersonItemType item = new PersonItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static PersonItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static PersonItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, PersonItemType>();
            mapStringToEnum = new java.util.HashMap<String, PersonItemType>();
            SURNAME = new PersonItemType(0, "SURNAME");
            mapIntToEnum.put(SURNAME.value(), SURNAME);
            mapStringToEnum.put(SURNAME.m_str.toUpperCase(), SURNAME);
            NAME = new PersonItemType(1, "NAME");
            mapIntToEnum.put(NAME.value(), NAME);
            mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
            INITIAL = new PersonItemType(2, "INITIAL");
            mapIntToEnum.put(INITIAL.value(), INITIAL);
            mapStringToEnum.put(INITIAL.m_str.toUpperCase(), INITIAL);
            AND = new PersonItemType(3, "AND");
            mapIntToEnum.put(AND.value(), AND);
            mapStringToEnum.put(AND.m_str.toUpperCase(), AND);
            LOCASEWORD = new PersonItemType(4, "LOCASEWORD");
            mapIntToEnum.put(LOCASEWORD.value(), LOCASEWORD);
            mapStringToEnum.put(LOCASEWORD.m_str.toUpperCase(), LOCASEWORD);
            java.util.Collection<PersonItemType> col = mapIntToEnum.values();
            m_Values = new PersonItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public OrgItemEponymToken() {
        super();
    }
    public static OrgItemEponymToken _globalInstance;
    
    static {
        try { _globalInstance = new OrgItemEponymToken(); } 
        catch(Exception e) { }
    }
}
