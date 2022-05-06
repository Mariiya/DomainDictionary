/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.phone;

/**
 * Сущность - телефонный номер
 * 
 */
public class PhoneReferent extends com.pullenti.ner.Referent {

    public PhoneReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.phone.internal.MetaPhone.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("PHONE")
     */
    public static final String OBJ_TYPENAME = "PHONE";

    /**
     * Имя атрибута - номер (слитно, без кода страны)
     */
    public static final String ATTR_NUNBER = "NUMBER";

    /**
     * Имя атрибута - тип (PhoneKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - код страны
     */
    public static final String ATTR_COUNTRYCODE = "COUNTRYCODE";

    /**
     * Имя атрибута - добавочный номер
     */
    public static final String ATTR_ADDNUMBER = "ADDNUMBER";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getCountryCode() != null) 
            res.append((com.pullenti.unisharp.Utils.stringsNe(this.getCountryCode(), "8") ? "+" : "")).append(this.getCountryCode()).append(" ");
        String num = getNumber();
        if (num != null && num.length() >= 9) {
            int cou = 3;
            if (num.length() >= 11) 
                cou = num.length() - 7;
            res.append("(").append(num.substring(0, 0 + cou)).append(") ");
            num = num.substring(cou);
        }
        else if (num != null && num.length() == 8) {
            res.append("(").append(num.substring(0, 0 + 2)).append(") ");
            num = num.substring(2);
        }
        if (num == null) 
            res.append("???-??-??");
        else {
            res.append(num);
            if (num.length() > 5) {
                res.insert(res.length() - 4, '-');
                res.insert(res.length() - 2, '-');
            }
        }
        if (getAddNumber() != null) 
            res.append(" (доб.").append(this.getAddNumber()).append(")");
        return res.toString();
    }

    public String getNumber() {
        return this.getStringValue(ATTR_NUNBER);
    }

    public String setNumber(String value) {
        this.addSlot(ATTR_NUNBER, value, true, 0);
        return value;
    }


    public String getAddNumber() {
        return this.getStringValue(ATTR_ADDNUMBER);
    }

    public String setAddNumber(String value) {
        this.addSlot(ATTR_ADDNUMBER, value, true, 0);
        return value;
    }


    public String getCountryCode() {
        return this.getStringValue(ATTR_COUNTRYCODE);
    }

    public String setCountryCode(String value) {
        this.addSlot(ATTR_COUNTRYCODE, value, true, 0);
        return value;
    }


    public PhoneKind getKind() {
        String str = this.getStringValue(ATTR_KIND);
        if (str == null) 
            return PhoneKind.UNDEFINED;
        try {
            return (PhoneKind)PhoneKind.of(str);
        } catch (Exception ex) {
            return PhoneKind.UNDEFINED;
        }
    }

    public PhoneKind setKind(PhoneKind value) {
        if (value != PhoneKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString().toLowerCase(), true, 0);
        return value;
    }


    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        String num = getNumber();
        if (num == null) 
            return null;
        if (num.length() > 9) 
            num = num.substring(9);
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        res.add(num);
        String add = getAddNumber();
        if (add != null) 
            res.add((num + "*" + add));
        return res;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return this._canBeEqual(obj, typ, false);
    }

    private boolean _canBeEqual(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ, boolean ignoreAddNumber) {
        PhoneReferent ph = (PhoneReferent)com.pullenti.unisharp.Utils.cast(obj, PhoneReferent.class);
        if (ph == null) 
            return false;
        if (ph.getCountryCode() != null && getCountryCode() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(ph.getCountryCode(), getCountryCode())) 
                return false;
        }
        if (ignoreAddNumber) {
            if (getAddNumber() != null && ph.getAddNumber() != null) {
                if (com.pullenti.unisharp.Utils.stringsNe(ph.getAddNumber(), getAddNumber())) 
                    return false;
            }
        }
        else if (getAddNumber() != null || ph.getAddNumber() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getAddNumber(), ph.getAddNumber())) 
                return false;
        }
        if (getNumber() == null || ph.getNumber() == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsEq(getNumber(), ph.getNumber())) 
            return true;
        if (typ != com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) {
            if (com.pullenti.morph.LanguageHelper.endsWith(this.getNumber(), ph.getNumber()) || com.pullenti.morph.LanguageHelper.endsWith(ph.getNumber(), this.getNumber())) 
                return true;
        }
        return false;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        if (!this._canBeEqual(obj, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT, true)) 
            return false;
        PhoneReferent ph = (PhoneReferent)com.pullenti.unisharp.Utils.cast(obj, PhoneReferent.class);
        if (getCountryCode() != null && ph.getCountryCode() == null) 
            return false;
        if (getAddNumber() == null) {
            if (ph.getAddNumber() != null) 
                return false;
        }
        else if (ph.getAddNumber() == null) 
            return false;
        if (com.pullenti.morph.LanguageHelper.endsWith(ph.getNumber(), this.getNumber())) 
            return true;
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        PhoneReferent ph = (PhoneReferent)com.pullenti.unisharp.Utils.cast(obj, PhoneReferent.class);
        if (ph == null) 
            return;
        if (ph.getCountryCode() != null && getCountryCode() == null) 
            setCountryCode(ph.getCountryCode());
        if (ph.getNumber() != null && com.pullenti.morph.LanguageHelper.endsWith(ph.getNumber(), this.getNumber())) 
            setNumber(ph.getNumber());
    }

    public String m_Template;

    public void correct() {
        if (getKind() == PhoneKind.UNDEFINED) {
            if (this.findSlot(ATTR_ADDNUMBER, null, true) != null) 
                setKind(PhoneKind.WORK);
            else if (getCountryCode() == null || com.pullenti.unisharp.Utils.stringsEq(getCountryCode(), "7")) {
                String num = getNumber();
                if (num.length() == 10 && num.charAt(0) == '9') 
                    setKind(PhoneKind.MOBILE);
            }
        }
    }
}
