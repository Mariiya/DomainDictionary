/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person;

/**
 * Сущность - персона
 * 
 */
public class PersonReferent extends com.pullenti.ner.Referent {

    public PersonReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.person.internal.MetaPerson.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("PERSON")
     */
    public static final String OBJ_TYPENAME = "PERSON";

    /**
     * Имя атрибута - пол
     */
    public static final String ATTR_SEX = "SEX";

    /**
     * Имя атрибута - слитно полное имя, если не удалось разбить на ФИО по отдельности 
     * (отдельно слова оформляются атрибутами ATTR_UNDEFNAME)
     */
    public static final String ATTR_IDENTITY = "IDENTITY";

    /**
     * Имя атрибута - имя
     */
    public static final String ATTR_FIRSTNAME = "FIRSTNAME";

    /**
     * Имя атрибута - отчество
     */
    public static final String ATTR_MIDDLENAME = "MIDDLENAME";

    /**
     * Имя атрибута - фамилия
     */
    public static final String ATTR_LASTNAME = "LASTNAME";

    /**
     * Имя атрибута - кличка или номер
     */
    public static final String ATTR_NICKNAME = "NICKNAME";

    /**
     * Имя атрибута - непонятное слово (то ли имя, то ли фамилия)
     */
    public static final String ATTR_UNDEFNAME = "UNDEFNAME";

    /**
     * Имя атрибута - тип наименования. Сейчас поддерживается значение china - 
     * когда фамилия сначала, имя портом (Мао Дзе Дун: "Мао" - фамилия, "Дзедун" - имя)
     */
    public static final String ATTR_NAMETYPE = "NAMETYPE";

    /**
     * Имя атрибута - свойство (PersonPropertyReferent)
     */
    public static final String ATTR_ATTR = "ATTRIBUTE";

    /**
     * Имя атрибута - возраст
     */
    public static final String ATTR_AGE = "AGE";

    /**
     * Имя атрибута - дата рождения
     */
    public static final String ATTR_BORN = "BORN";

    /**
     * Имя атрибута - дата смерти
     */
    public static final String ATTR_DIE = "DIE";

    /**
     * Имя атрибута - контактная информация
     */
    public static final String ATTR_CONTACT = "CONTACT";

    /**
     * Имя атрибута - удостоверяющий документ (PersonIdentityReferent)
     */
    public static final String ATTR_IDDOC = "IDDOC";

    public boolean isMale() {
        return com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_SEX), com.pullenti.ner.person.internal.MetaPerson.ATTR_SEXMALE);
    }

    public boolean setMale(boolean value) {
        this.addSlot(ATTR_SEX, com.pullenti.ner.person.internal.MetaPerson.ATTR_SEXMALE, true, 0);
        return value;
    }


    public boolean isFemale() {
        return com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_SEX), com.pullenti.ner.person.internal.MetaPerson.ATTR_SEXFEMALE);
    }

    public boolean setFemale(boolean value) {
        this.addSlot(ATTR_SEX, com.pullenti.ner.person.internal.MetaPerson.ATTR_SEXFEMALE, true, 0);
        return value;
    }


    public int getAge() {
        int i = this.getIntValue(ATTR_AGE, 0);
        if (i > 0) 
            return i;
        return 0;
    }

    public int setAge(int value) {
        this.addSlot(ATTR_AGE, ((Integer)value).toString(), true, 0);
        return value;
    }


    public void addContact(com.pullenti.ner.Referent contact) {
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CONTACT)) {
                com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                if (r != null) {
                    if (r.canBeGeneralFor(contact)) {
                        this.uploadSlot(s, contact);
                        return;
                    }
                    if (r.canBeEquals(contact, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                        return;
                }
            }
        }
        this.addSlot(ATTR_CONTACT, contact, false, 0);
    }

    private String _getPrefix() {
        if (isMale()) 
            return "г-н ";
        if (isFemale()) 
            return "г-жа ";
        return "";
    }

    private String _findForSurname(String attrName, String surname, boolean findShortest) {
        boolean rus = com.pullenti.morph.LanguageHelper.isCyrillicChar(surname.charAt(0));
        String res = null;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), attrName)) {
                String v = a.getValue().toString();
                if (com.pullenti.morph.LanguageHelper.isCyrillicChar(v.charAt(0)) != rus) 
                    continue;
                if (res == null) 
                    res = v;
                else if (findShortest && (v.length() < res.length())) 
                    res = v;
            }
        }
        return res;
    }

    private String _findShortestValue(String attrName) {
        String res = null;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), attrName)) {
                String v = a.getValue().toString();
                if (res == null || (v.length() < res.length())) 
                    res = v;
            }
        }
        return res;
    }

    private String _findShortestKingTitul(boolean doName) {
        String res = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof PersonPropertyReferent) {
                PersonPropertyReferent pr = (PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), PersonPropertyReferent.class);
                if (pr.getKind() != PersonPropertyKind.KING) 
                    continue;
                for (com.pullenti.ner.Slot ss : pr.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), PersonPropertyReferent.ATTR_NAME)) {
                        String n = (String)com.pullenti.unisharp.Utils.cast(ss.getValue(), String.class);
                        if (res == null) 
                            res = n;
                        else if (res.length() > n.length()) 
                            res = n;
                    }
                }
            }
        }
        if (res != null || !doName) 
            return res;
        return null;
    }

    @Override
    public String toSortString() {
        String sur = null;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_IDENTITY)) 
                return a.getValue().toString();
            else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_LASTNAME)) {
                sur = a.getValue().toString();
                break;
            }
        }
        if (sur == null) {
            String tit = this._findShortestKingTitul(false);
            if (tit == null) 
                return "?";
            String s = this.getStringValue(ATTR_FIRSTNAME);
            if (s == null) 
                return "?";
            return (tit + " " + s);
        }
        String n = this._findForSurname(ATTR_FIRSTNAME, sur, false);
        if (n == null) 
            return sur;
        else 
            return (sur + " " + n);
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_LASTNAME) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_IDENTITY)) 
                res.add(s.getValue().toString());
        }
        String tit = this._findShortestKingTitul(false);
        if (tit != null) {
            String nam = this.getStringValue(ATTR_FIRSTNAME);
            if (nam != null) 
                res.add((tit + " " + nam));
        }
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
    }

    /**
     * При выводе в ToString() первым ставить фамилию, а не имя
     */
    public static boolean SHOWLASTNAMEONFIRSTPOSITION = false;

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (shortVariant) 
            return this.toShortString(lang);
        else {
            String res = this.toFullString(SHOWLASTNAMEONFIRSTPOSITION, lang, false);
            if (this.findSlot(ATTR_NICKNAME, null, true) == null) 
                return res;
            java.util.ArrayList<String> niks = this.getStringValues(ATTR_NICKNAME);
            if (niks.size() == 1) 
                return (res + " (" + _outNik(niks.get(0)) + ")");
            StringBuilder tmp = new StringBuilder();
            tmp.append(res);
            tmp.append(" (");
            for (String s : niks) {
                if (com.pullenti.unisharp.Utils.stringsNe(s, niks.get(0))) 
                    tmp.append(", ");
                tmp.append(_outNik(s));
            }
            tmp.append(")");
            return tmp.toString();
        }
    }

    private static String _outNik(String str) {
        if (str.length() < 4) 
            return str;
        return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str);
    }

    private String toShortString(com.pullenti.morph.MorphLang lang) {
        String id = null;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_IDENTITY)) {
                String s = a.getValue().toString();
                if (id == null || (s.length() < id.length())) 
                    id = s;
            }
        }
        if (id != null) 
            return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(id);
        String n = this.getStringValue(ATTR_LASTNAME);
        if (n != null) {
            StringBuilder res = new StringBuilder();
            res.append(n);
            String s = this._findForSurname(ATTR_FIRSTNAME, n, true);
            if (s != null) {
                res.append(" ").append(s.charAt(0)).append(".");
                s = this._findForSurname(ATTR_MIDDLENAME, n, false);
                if (s != null) 
                    res.append(s.charAt(0)).append(".");
            }
            return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(res.toString());
        }
        String tit = this._findShortestKingTitul(true);
        if (tit != null) {
            String nam = this.getStringValue(ATTR_FIRSTNAME);
            if (nam != null) 
                return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower((tit + " " + nam));
        }
        return this.toFullString(false, lang, true);
    }

    private String toFullString(boolean lastNameFirst, com.pullenti.morph.MorphLang lang, boolean outNiks) {
        String id = null;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_IDENTITY)) {
                String s = a.getValue().toString();
                if (id == null || s.length() > id.length()) 
                    id = s;
            }
        }
        if (id != null) 
            return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(id);
        String sss = this.getStringValue("NAMETYPE");
        if (com.pullenti.unisharp.Utils.stringsEq(sss, "china")) 
            lastNameFirst = true;
        String n = this.getStringValue(ATTR_LASTNAME);
        if (n != null) {
            StringBuilder res = new StringBuilder();
            if (lastNameFirst) 
                res.append(n).append(" ");
            String s = this._findForSurname(ATTR_FIRSTNAME, n, false);
            if (s != null) {
                res.append(s);
                if (isInitial(s)) 
                    res.append('.');
                else 
                    res.append(' ');
                s = this._findForSurname(ATTR_MIDDLENAME, n, false);
                if (s != null) {
                    res.append(s);
                    if (isInitial(s)) 
                        res.append('.');
                    else 
                        res.append(' ');
                }
            }
            if (!lastNameFirst) 
                res.append(n);
            else if (res.charAt(res.length() - 1) == ' ') 
                res.setLength(res.length() - 1);
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(n.charAt(0))) {
                String nl = null;
                for (com.pullenti.ner.Slot sl : getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(sl.getTypeName(), ATTR_LASTNAME)) {
                        String ss = (String)com.pullenti.unisharp.Utils.cast(sl.getValue(), String.class);
                        if (ss.length() > 0 && com.pullenti.morph.LanguageHelper.isLatinChar(ss.charAt(0))) {
                            nl = ss;
                            break;
                        }
                    }
                }
                if (nl != null) {
                    String nal = this._findForSurname(ATTR_FIRSTNAME, nl, false);
                    if (nal == null) 
                        res.append(" (").append(nl).append(")");
                    else if (SHOWLASTNAMEONFIRSTPOSITION) 
                        res.append(" (").append(nl).append(" ").append(nal).append(")");
                    else 
                        res.append(" (").append(nal).append(" ").append(nl).append(")");
                }
            }
            return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(res.toString());
        }
        else if ((((n = this.getStringValue(ATTR_FIRSTNAME)))) != null) {
            String s = this._findForSurname(ATTR_MIDDLENAME, n, false);
            if (s != null) 
                n = (n + " " + s);
            n = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(n);
            String tit = this._findShortestKingTitul(false);
            if (tit != null) 
                n = (tit + " " + n);
            if (outNiks) {
                String nik = this.getStringValue(ATTR_NICKNAME);
                if (nik != null) 
                    n = (n + " " + _outNik(nik));
            }
            return n;
        }
        return "?";
    }

    public com.pullenti.ner.person.internal.FioTemplateType m_PersonIdentityTyp = com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED;

    private java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection> m_SurnameOccurs = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection>();

    private java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection> m_NameOccurs = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection>();

    private java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection> m_SecOccurs = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection>();

    private java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection> m_IdentOccurs = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection>();

    public void addFioIdentity(com.pullenti.ner.person.internal.PersonMorphCollection lastName, com.pullenti.ner.person.internal.PersonMorphCollection firstName, Object middleName) {
        if (lastName != null) {
            if (lastName.number > 0) {
                String num = com.pullenti.ner.core.NumberHelper.getNumberRoman(lastName.number);
                if (num == null) 
                    num = ((Integer)lastName.number).toString();
                this.addSlot(ATTR_NICKNAME, num, false, 0);
            }
            else {
                lastName.correct();
                m_SurnameOccurs.add(lastName);
                for (String v : lastName.getValues()) {
                    this.addSlot(ATTR_LASTNAME, v, false, 0);
                }
            }
        }
        if (firstName != null) {
            firstName.correct();
            if (firstName.head != null && firstName.head.length() > 2) 
                m_NameOccurs.add(firstName);
            for (String v : firstName.getValues()) {
                this.addSlot(ATTR_FIRSTNAME, v, false, 0);
            }
            if (middleName instanceof String) 
                this.addSlot(ATTR_MIDDLENAME, middleName, false, 0);
            else if (middleName instanceof com.pullenti.ner.person.internal.PersonMorphCollection) {
                com.pullenti.ner.person.internal.PersonMorphCollection mm = (com.pullenti.ner.person.internal.PersonMorphCollection)com.pullenti.unisharp.Utils.cast(middleName, com.pullenti.ner.person.internal.PersonMorphCollection.class);
                if (mm.head != null && mm.head.length() > 2) 
                    m_SecOccurs.add(mm);
                for (String v : mm.getValues()) {
                    this.addSlot(ATTR_MIDDLENAME, v, false, 0);
                }
            }
        }
        this.correctData();
    }

    public void addIdentity(com.pullenti.ner.person.internal.PersonMorphCollection ident) {
        if (ident == null) 
            return;
        m_IdentOccurs.add(ident);
        for (String v : ident.getValues()) {
            this.addSlot(ATTR_IDENTITY, v, false, 0);
            String[] li = com.pullenti.unisharp.Utils.split(v, String.valueOf(' '), false);
            for (int i = 0; i < li.length; i++) {
                String a = ATTR_UNDEFNAME;
                if (i == 0 && li.length > 1) 
                    a = ATTR_FIRSTNAME;
                else if (i == (li.length - 1) && li.length > 1) 
                    a = ATTR_LASTNAME;
                this.addSlot(a, li[i], false, 0);
            }
        }
        this.correctData();
    }

    private static boolean isInitial(String str) {
        if (str == null) 
            return false;
        if (str.length() == 1) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(str, "ДЖ")) 
            return true;
        return false;
    }

    public void addAttribute(Object attr) {
        this.addSlot(ATTR_ATTR, attr, false, 0);
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        PersonReferent p = (PersonReferent)com.pullenti.unisharp.Utils.cast(obj, PersonReferent.class);
        if (p == null) 
            return false;
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_IDENTITY)) {
                for (com.pullenti.ner.Slot aa : p.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(aa.getTypeName(), a.getTypeName())) {
                        if (com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd((String)com.pullenti.unisharp.Utils.cast(a.getValue(), String.class)), _DelSurnameEnd((String)com.pullenti.unisharp.Utils.cast(aa.getValue(), String.class)))) 
                            return true;
                    }
                }
            }
        }
        String nick1 = this.getStringValue(ATTR_NICKNAME);
        String nick2 = obj.getStringValue(ATTR_NICKNAME);
        if (nick1 != null && nick2 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(nick1, nick2)) 
                return false;
        }
        if (this.findSlot(ATTR_LASTNAME, null, true) != null && p.findSlot(ATTR_LASTNAME, null, true) != null) {
            if (!this.compareSurnamesPers(p)) 
                return false;
            if (this.findSlot(ATTR_FIRSTNAME, null, true) != null && p.findSlot(ATTR_FIRSTNAME, null, true) != null) {
                if (!this.checkNames(ATTR_FIRSTNAME, p)) 
                    return false;
                if (this.findSlot(ATTR_MIDDLENAME, null, true) != null && p.findSlot(ATTR_MIDDLENAME, null, true) != null) {
                    if (!this.checkNames(ATTR_MIDDLENAME, p)) 
                        return false;
                }
                else if (typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) {
                    if (this.findSlot(ATTR_MIDDLENAME, null, true) != null || p.findSlot(ATTR_MIDDLENAME, null, true) != null) 
                        return com.pullenti.unisharp.Utils.stringsEq(this.toString(), p.toString());
                    java.util.ArrayList<String> names1 = new java.util.ArrayList<String>();
                    java.util.ArrayList<String> names2 = new java.util.ArrayList<String>();
                    for (com.pullenti.ner.Slot s : getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIRSTNAME)) {
                            String nam = s.getValue().toString();
                            if (!isInitial(nam)) 
                                names1.add(nam);
                        }
                    }
                    for (com.pullenti.ner.Slot s : p.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIRSTNAME)) {
                            String nam = s.getValue().toString();
                            if (!isInitial(nam)) {
                                if (names1.contains(nam)) 
                                    return true;
                                names2.add(nam);
                            }
                        }
                    }
                    if (names1.size() == 0 && names2.size() == 0) 
                        return true;
                    return false;
                }
            }
            else if (typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS && ((this.findSlot(ATTR_FIRSTNAME, null, true) != null || p.findSlot(ATTR_FIRSTNAME, null, true) != null))) 
                return false;
            return true;
        }
        String tit1 = this._findShortestKingTitul(false);
        String tit2 = p._findShortestKingTitul(false);
        if (((tit1 != null || tit2 != null)) || ((nick1 != null && com.pullenti.unisharp.Utils.stringsEq(nick1, nick2)))) {
            if (tit1 == null || tit2 == null) {
                if (nick1 != null && com.pullenti.unisharp.Utils.stringsEq(nick1, nick2)) {
                }
                else if (nick1 != null && this._checkNikEquSur(nick1, p.getStringValue(ATTR_LASTNAME))) {
                }
                else if (nick2 != null && this._checkNikEquSur(nick2, this.getStringValue(ATTR_LASTNAME))) {
                }
                else 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(tit1, tit2)) {
                if (!(tit1.indexOf(tit2) >= 0) && !(tit2.indexOf(tit1) >= 0)) 
                    return false;
            }
            if (this.findSlot(ATTR_FIRSTNAME, null, true) != null && p.findSlot(ATTR_FIRSTNAME, null, true) != null) {
                if (!this.checkNames(ATTR_FIRSTNAME, p)) 
                    return false;
                return true;
            }
        }
        if (this.findSlot(ATTR_UNDEFNAME, null, true) != null && p.findSlot(ATTR_UNDEFNAME, null, true) != null) {
            java.util.ArrayList<String> und1 = this._getAllIdents();
            java.util.ArrayList<String> und2 = p._getAllIdents();
            if (und1.size() <= und2.size()) {
                for (String u : und1) {
                    if (!und2.contains(u)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(u, und1.get(und1.size() - 1)) && com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd(u), _DelSurnameEnd(und2.get(und2.size() - 1)))) {
                        }
                        else 
                            return false;
                    }
                }
                return true;
            }
            else {
                for (String u : und2) {
                    if (!und1.contains(u)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(u, und2.get(und2.size() - 1)) && com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd(u), _DelSurnameEnd(und1.get(und1.size() - 1)))) {
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean _checkNikEquSur(String nik, String sur) {
        if (com.pullenti.unisharp.Utils.stringsEq(nik, "I")) 
            return com.pullenti.unisharp.Utils.stringsEq(sur, "ПЕРВЫЙ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ПЕРВАЯ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ПЕРШЕ");
        if (com.pullenti.unisharp.Utils.stringsEq(nik, "II")) 
            return (com.pullenti.unisharp.Utils.stringsEq(sur, "ВТОРОЙ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ВТОРАЯ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ДРУГЕ")) || com.pullenti.unisharp.Utils.stringsEq(sur, "ДРУГA");
        if (com.pullenti.unisharp.Utils.stringsEq(nik, "III")) 
            return com.pullenti.unisharp.Utils.stringsEq(sur, "ТРЕТИЙ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ТРЕТЬЯ") || com.pullenti.unisharp.Utils.stringsEq(sur, "ТРЕТИНА");
        return false;
    }

    private java.util.ArrayList<String> _getAllIdents() {
        java.util.ArrayList<String> res = this.getStringValues(ATTR_UNDEFNAME);
        String s = this.getStringValue(ATTR_FIRSTNAME);
        if (s != null) 
            res.add(0, s);
        s = this.getStringValue(ATTR_LASTNAME);
        if (s != null) 
            res.add(s);
        return res;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        if (!this.canBeEquals(obj, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
            return false;
        PersonReferent p = (PersonReferent)com.pullenti.unisharp.Utils.cast(obj, PersonReferent.class);
        if (p == null) 
            return false;
        if (this.findSlot(ATTR_LASTNAME, null, true) == null || p.findSlot(ATTR_LASTNAME, null, true) == null) 
            return false;
        if (!this.compareSurnamesPers(p)) 
            return false;
        if (this.findSlot(ATTR_FIRSTNAME, null, true) == null) {
            if (p.findSlot(ATTR_FIRSTNAME, null, true) != null) 
                return true;
            else 
                return false;
        }
        if (p.findSlot(ATTR_FIRSTNAME, null, true) == null) 
            return false;
        if (!this.checkNames(ATTR_FIRSTNAME, p)) 
            return false;
        if (this.findSlot(ATTR_MIDDLENAME, null, true) != null && p.findSlot(ATTR_MIDDLENAME, null, true) == null) {
            if (!isInitial(this.getStringValue(ATTR_FIRSTNAME))) 
                return false;
        }
        int nameInits = 0;
        int nameFulls = 0;
        int secInits = 0;
        int secFulls = 0;
        int nameInits1 = 0;
        int nameFulls1 = 0;
        int secInits1 = 0;
        int secFulls1 = 0;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIRSTNAME)) {
                if (isInitial((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class))) 
                    nameInits++;
                else 
                    nameFulls++;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_MIDDLENAME)) {
                if (isInitial((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class))) 
                    secInits++;
                else 
                    secFulls++;
            }
        }
        for (com.pullenti.ner.Slot s : p.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIRSTNAME)) {
                if (isInitial((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class))) 
                    nameInits1++;
                else 
                    nameFulls1++;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_MIDDLENAME)) {
                if (isInitial((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class))) 
                    secInits1++;
                else 
                    secFulls1++;
            }
        }
        if (secFulls > 0) 
            return false;
        if (nameInits == 0) {
            if (nameInits1 > 0) 
                return false;
        }
        else if (nameInits1 > 0) {
            if ((secInits + secFulls) > 0) 
                return false;
        }
        if (secInits == 0) {
            if ((secInits1 + secFulls1) == 0) {
                if (nameInits1 == 0 && nameInits > 0) 
                    return true;
                else 
                    return false;
            }
        }
        else if (secInits1 > 0) 
            return false;
        return true;
    }

    private boolean compareSurnamesPers(PersonReferent p) {
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_LASTNAME)) {
                String s = a.getValue().toString();
                for (com.pullenti.ner.Slot aa : p.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(aa.getTypeName(), a.getTypeName())) {
                        String ss = aa.getValue().toString();
                        if (this.compareSurnamesStrs(s, ss)) 
                            return true;
                    }
                }
            }
        }
        return false;
    }

    // Сравнение с учётом возможных окончаний
    private boolean compareSurnamesStrs(String s1, String s2) {
        if (s1.startsWith(s2) || s2.startsWith(s1)) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd(s1), _DelSurnameEnd(s2))) 
            return true;
        String n1 = com.pullenti.ner.core.MiscHelper.getAbsoluteNormalValue(s1, false);
        if (n1 != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(n1, com.pullenti.ner.core.MiscHelper.getAbsoluteNormalValue(s2, false))) 
                return true;
        }
        if (com.pullenti.ner.core.MiscHelper.canBeEquals(s1, s2, true, true, false)) 
            return true;
        return false;
    }

    public static String _DelSurnameEnd(String s) {
        if (s.length() < 3) 
            return s;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "А", "У", "Е", null)) 
            return s.substring(0, 0 + s.length() - 1);
        if (com.pullenti.morph.LanguageHelper.endsWith(s, "ОМ") || com.pullenti.morph.LanguageHelper.endsWith(s, "ЫМ")) 
            return s.substring(0, 0 + s.length() - 2);
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "Я", "Ю", null, null)) {
            char ch1 = s.charAt(s.length() - 2);
            if (ch1 == 'Н' || ch1 == 'Л') 
                return s.substring(0, 0 + s.length() - 1) + "Ь";
        }
        return s;
    }

    private boolean checkNames(String attrName, PersonReferent p) {
        java.util.ArrayList<String> names1 = new java.util.ArrayList<String>();
        java.util.ArrayList<String> inits1 = new java.util.ArrayList<String>();
        java.util.ArrayList<String> normn1 = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), attrName)) {
                String n = s.getValue().toString();
                if (isInitial(n)) 
                    inits1.add(n);
                else {
                    names1.add(n);
                    String sn = com.pullenti.ner.core.MiscHelper.getAbsoluteNormalValue(n, false);
                    if (sn != null) 
                        normn1.add(sn);
                }
            }
        }
        java.util.ArrayList<String> names2 = new java.util.ArrayList<String>();
        java.util.ArrayList<String> inits2 = new java.util.ArrayList<String>();
        java.util.ArrayList<String> normn2 = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : p.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), attrName)) {
                String n = s.getValue().toString();
                if (isInitial(n)) 
                    inits2.add(n);
                else {
                    names2.add(n);
                    String sn = com.pullenti.ner.core.MiscHelper.getAbsoluteNormalValue(n, false);
                    if (sn != null) 
                        normn2.add(sn);
                }
            }
        }
        if (names1.size() > 0 && names2.size() > 0) {
            for (String n : names1) {
                if (names2.contains(n)) 
                    return true;
            }
            for (String n : normn1) {
                if (normn2.contains(n)) 
                    return true;
            }
            return false;
        }
        if (inits1.size() > 0) {
            for (String n : inits1) {
                if (inits2.contains(n)) 
                    return true;
                for (String nn : names2) {
                    if (nn.startsWith(n)) 
                        return true;
                }
            }
        }
        if (inits2.size() > 0) {
            for (String n : inits2) {
                if (inits1.contains(n)) 
                    return true;
                for (String nn : names1) {
                    if (nn.startsWith(n)) 
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        PersonReferent p = (PersonReferent)com.pullenti.unisharp.Utils.cast(obj, PersonReferent.class);
        com.pullenti.unisharp.Utils.addToArrayList(m_SurnameOccurs, p.m_SurnameOccurs);
        com.pullenti.unisharp.Utils.addToArrayList(m_NameOccurs, p.m_NameOccurs);
        com.pullenti.unisharp.Utils.addToArrayList(m_SecOccurs, p.m_SecOccurs);
        com.pullenti.unisharp.Utils.addToArrayList(m_IdentOccurs, p.m_IdentOccurs);
        if (p.m_PersonIdentityTyp != com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED) 
            m_PersonIdentityTyp = p.m_PersonIdentityTyp;
        this.correctData();
    }

    public void correctData() {
        com.pullenti.morph.MorphGender g = com.pullenti.morph.MorphGender.UNDEFINED;
        while (true) {
            boolean ch = false;
            if (com.pullenti.ner.person.internal.PersonMorphCollection.intersect(m_SurnameOccurs)) 
                ch = true;
            if (com.pullenti.ner.person.internal.PersonMorphCollection.intersect(m_NameOccurs)) 
                ch = true;
            if (com.pullenti.ner.person.internal.PersonMorphCollection.intersect(m_SecOccurs)) 
                ch = true;
            if (com.pullenti.ner.person.internal.PersonMorphCollection.intersect(m_IdentOccurs)) 
                ch = true;
            if (!ch) 
                break;
            if (g == com.pullenti.morph.MorphGender.UNDEFINED && m_SurnameOccurs.size() > 0 && m_SurnameOccurs.get(0).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                g = m_SurnameOccurs.get(0).getGender();
            if (g == com.pullenti.morph.MorphGender.UNDEFINED && m_NameOccurs.size() > 0 && m_NameOccurs.get(0).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                g = m_NameOccurs.get(0).getGender();
            if (g == com.pullenti.morph.MorphGender.UNDEFINED && m_IdentOccurs.size() > 0 && m_IdentOccurs.get(0).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                g = m_IdentOccurs.get(0).getGender();
            if (g != com.pullenti.morph.MorphGender.UNDEFINED) {
                com.pullenti.ner.person.internal.PersonMorphCollection.setGender(m_SurnameOccurs, g);
                com.pullenti.ner.person.internal.PersonMorphCollection.setGender(m_NameOccurs, g);
                com.pullenti.ner.person.internal.PersonMorphCollection.setGender(m_SecOccurs, g);
                com.pullenti.ner.person.internal.PersonMorphCollection.setGender(m_IdentOccurs, g);
            }
        }
        if (g != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (!isFemale() && !isMale()) {
                if (g == com.pullenti.morph.MorphGender.MASCULINE) 
                    setMale(true);
                else 
                    setFemale(true);
            }
        }
        this.correctSurnames();
        this.correctIdentifiers();
        this.correctAttrs();
        this.removeSlots(ATTR_LASTNAME, m_SurnameOccurs);
        this.removeSlots(ATTR_FIRSTNAME, m_NameOccurs);
        this.removeSlots(ATTR_MIDDLENAME, m_SecOccurs);
        this.removeSlots(ATTR_IDENTITY, m_IdentOccurs);
        this.removeInitials(ATTR_FIRSTNAME);
        this.removeInitials(ATTR_MIDDLENAME);
    }

    private void correctSurnames() {
        boolean def = isMale() || isFemale();
        for (int i = 0; i < getSlots().size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_LASTNAME) && def) {
                String s = this.getSlots().get(i).getValue().toString();
                for (int j = i + 1; j < getSlots().size(); j++) {
                    if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(j).getTypeName(), ATTR_LASTNAME)) {
                        String s1 = this.getSlots().get(j).getValue().toString();
                        if (com.pullenti.unisharp.Utils.stringsNe(s, s1) && com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd(s), _DelSurnameEnd(s1)) && s1.length() != s.length()) {
                            if (isMale()) {
                                this.uploadSlot(this.getSlots().get(i), (s = _DelSurnameEnd(s)));
                                getSlots().remove(j);
                                j--;
                            }
                            else {
                                getSlots().remove(i);
                                i--;
                                break;
                            }
                        }
                    }
                }
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_UNDEFNAME)) {
                if (this.findSlot(ATTR_FIRSTNAME, this.getSlots().get(i).getValue(), true) != null || this.findSlot(ATTR_LASTNAME, this.getSlots().get(i).getValue(), true) != null) {
                    getSlots().remove(i);
                    i--;
                }
            }
        }
        String nik = this.getStringValue(ATTR_NICKNAME);
        if (nik != null) {
            for (int i = getSlots().size() - 1; i >= 0; i--) {
                if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_LASTNAME)) {
                    if (this._checkNikEquSur(nik, (String)com.pullenti.unisharp.Utils.cast(this.getSlots().get(i).getValue(), String.class))) 
                        getSlots().remove(i);
                }
            }
        }
    }

    private void correctIdentifiers() {
        if (isFemale()) 
            return;
        for (int i = 0; i < getSlots().size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_IDENTITY)) {
                String s = this.getSlots().get(i).getValue().toString();
                for (int j = i + 1; j < getSlots().size(); j++) {
                    if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(j).getTypeName(), ATTR_IDENTITY)) {
                        String s1 = this.getSlots().get(j).getValue().toString();
                        if (com.pullenti.unisharp.Utils.stringsNe(s, s1) && com.pullenti.unisharp.Utils.stringsEq(_DelSurnameEnd(s), _DelSurnameEnd(s1))) {
                            this.uploadSlot(this.getSlots().get(i), (s = _DelSurnameEnd(s)));
                            getSlots().remove(j);
                            j--;
                            setMale(true);
                        }
                    }
                }
            }
        }
    }

    private void removeSlots(String attrName, java.util.ArrayList<com.pullenti.ner.person.internal.PersonMorphCollection> cols) {
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        for (com.pullenti.ner.person.internal.PersonMorphCollection col : cols) {
            for (String v : col.getValues()) {
                if (!vars.contains(v)) 
                    vars.add(v);
            }
        }
        if (vars.size() < 1) 
            return;
        for (int i = getSlots().size() - 1; i >= 0; i--) {
            if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), attrName)) {
                String v = this.getSlots().get(i).getValue().toString();
                if (vars.contains(v)) 
                    continue;
                if (v.indexOf('-') > 0) {
                    if (vars.contains(v.replace("-", ""))) 
                        continue;
                }
                for (int j = 0; j < getSlots().size(); j++) {
                    if (j != i && com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(j).getTypeName(), this.getSlots().get(i).getTypeName())) {
                        if (com.pullenti.unisharp.Utils.stringsEq(attrName, ATTR_LASTNAME)) {
                            boolean ee = false;
                            for (String vv : vars) {
                                if (this.compareSurnamesStrs(v, vv)) 
                                    ee = true;
                            }
                            if (!ee) 
                                continue;
                        }
                        getSlots().remove(i);
                        break;
                    }
                }
            }
        }
    }

    private void removeInitials(String attrName) {
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), attrName)) {
                if (isInitial(s.getValue().toString())) {
                    for (com.pullenti.ner.Slot ss : getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), s.getTypeName()) && s != ss) {
                            String v = ss.getValue().toString();
                            if (!isInitial(v) && v.startsWith(s.getValue().toString())) {
                                if (com.pullenti.unisharp.Utils.stringsEq(attrName, ATTR_FIRSTNAME) && v.length() == 2 && this.findSlot(ATTR_MIDDLENAME, v.substring(1), true) != null) 
                                    getSlots().remove(ss);
                                else 
                                    getSlots().remove(s);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void correctAttrs() {
        java.util.ArrayList<PersonPropertyReferent> attrs = new java.util.ArrayList<PersonPropertyReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ATTR) && (s.getValue() instanceof PersonPropertyReferent)) 
                attrs.add((PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), PersonPropertyReferent.class));
        }
        if (attrs.size() < 2) 
            return;
        for (PersonPropertyReferent a : attrs) {
            a.tag = null;
        }
        for (int i = 0; i < (attrs.size() - 1); i++) {
            for (int j = i + 1; j < attrs.size(); j++) {
                if (attrs.get(i).getGeneralReferent() == attrs.get(j) || attrs.get(j).canBeGeneralFor(attrs.get(i))) 
                    attrs.get(j).tag = attrs.get(i);
                else if (attrs.get(j).getGeneralReferent() == attrs.get(i) || attrs.get(i).canBeGeneralFor(attrs.get(j))) 
                    attrs.get(i).tag = attrs.get(j);
            }
        }
        for (int i = getSlots().size() - 1; i >= 0; i--) {
            if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_ATTR) && (this.getSlots().get(i).getValue() instanceof PersonPropertyReferent)) {
                if (((PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(this.getSlots().get(i).getValue(), PersonPropertyReferent.class)).tag != null) {
                    PersonPropertyReferent pr = (PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(((PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(this.getSlots().get(i).getValue(), PersonPropertyReferent.class)).tag, PersonPropertyReferent.class);
                    if (pr != null && pr.getGeneralReferent() == null) 
                        pr.setGeneralReferent((PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(this.getSlots().get(i).getValue(), PersonPropertyReferent.class));
                    getSlots().remove(i);
                }
            }
        }
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        String tit = this._findShortestKingTitul(false);
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_IDENTITY)) 
                oi.termins.add(com.pullenti.ner.core.Termin._new1223(a.getValue().toString(), true));
            else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_FIRSTNAME) && tit != null) {
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin((tit + " " + a.getValue().toString()), null, false);
                if (isMale()) 
                    t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                else if (isFemale()) 
                    t.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                oi.termins.add(t);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_LASTNAME) || com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_FIRSTNAME) || com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_UNDEFNAME)) {
                if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_FIRSTNAME) && this.findSlot(ATTR_IDENTITY, null, true) == null) 
                    continue;
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(a.getValue().toString(), null, false);
                if (t.terms.size() > 20) {
                }
                if (isMale()) 
                    t.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                else if (isFemale()) 
                    t.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                oi.termins.add(t);
            }
        }
        return oi;
    }
}
