/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage;

/**
 * Сущность, описывающая информацию из заголовков статей, книг, диссертация и пр.
 * 
 */
public class TitlePageReferent extends com.pullenti.ner.Referent {

    public TitlePageReferent(String name) {
        super((name != null ? name : OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.titlepage.internal.MetaTitleInfo.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("TITLEPAGE")
     */
    public static final String OBJ_TYPENAME = "TITLEPAGE";

    /**
     * Имя атрибута - наименование
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - тип
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - автор (PersonReferent)
     */
    public static final String ATTR_AUTHOR = "AUTHOR";

    /**
     * Имя атрибута - руководитель (PersonReferent)
     */
    public static final String ATTR_SUPERVISOR = "SUPERVISOR";

    /**
     * Имя атрибута - редактор (PersonReferent)
     */
    public static final String ATTR_EDITOR = "EDITOR";

    /**
     * Имя атрибута - консультант (PersonReferent)
     */
    public static final String ATTR_CONSULTANT = "CONSULTANT";

    /**
     * Имя атрибута - оппонент (PersonReferent)
     */
    public static final String ATTR_OPPONENT = "OPPONENT";

    /**
     * Имя атрибута - переводчик (PersonReferent)
     */
    public static final String ATTR_TRANSLATOR = "TRANSLATOR";

    /**
     * Имя атрибута - утвердивший (PersonReferent)
     */
    public static final String ATTR_AFFIRMANT = "AFFIRMANT";

    /**
     * Имя атрибута - организации (OrganizationReferent)
     */
    public static final String ATTR_ORG = "ORGANIZATION";

    /**
     * Имя атрибута - курс студента
     */
    public static final String ATTR_STUDENTYEAR = "STUDENTYEAR";

    /**
     * Имя атрибута - дата (год)
     */
    public static final String ATTR_DATE = "DATE";

    /**
     * Имя атрибута - город (GeoReferent)
     */
    public static final String ATTR_CITY = "CITY";

    /**
     * Имя атрибута - специальность (для диссертаций)
     */
    public static final String ATTR_SPECIALITY = "SPECIALITY";

    /**
     * Имя атрибута - дополнительный атрибут
     */
    public static final String ATTR_ATTR = "ATTR";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = this.getStringValue(ATTR_NAME);
        res.append("\"").append(((str != null ? str : "?"))).append("\"");
        if (!shortVariant) {
            for (com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), ATTR_TYPE)) {
                    res.append(" (").append(r.getValue()).append(")");
                    break;
                }
            }
            for (com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), ATTR_AUTHOR) && (r.getValue() instanceof com.pullenti.ner.Referent)) 
                    res.append(", ").append(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(r.getValue(), com.pullenti.ner.Referent.class)).toStringEx(true, lang, 0));
            }
        }
        if (getCity() != null && !shortVariant) 
            res.append(", ").append(this.getCity().toStringEx(true, lang, 0));
        if (getDate() != null) {
            if (!shortVariant) 
                res.append(", ").append(this.getDate().toStringEx(true, lang, 0));
            else 
                res.append(", ").append(this.getDate().getYear());
        }
        return res.toString();
    }

    public java.util.ArrayList<String> getTypes() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public void addType(String typ) {
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(typ)) {
            this.addSlot(ATTR_TYPE, typ.toLowerCase(), false, 0);
            this.correctData();
        }
    }

    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public com.pullenti.ner.core.Termin addName(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(begin, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(begin, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.getEndToken() == end) {
                begin = begin.getNext();
                end = end.getPrevious();
            }
        }
        String val = com.pullenti.ner.core.MiscHelper.getTextValue(begin, end, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        if (val == null) 
            return null;
        if (val.endsWith(".") && !val.endsWith("..")) 
            val = val.substring(0, 0 + val.length() - 1).trim();
        this.addSlot(ATTR_NAME, val, false, 0);
        return new com.pullenti.ner.core.Termin(val.toUpperCase(), null, false);
    }

    private void correctData() {
    }

    public com.pullenti.ner.date.DateReferent getDate() {
        return (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DATE), com.pullenti.ner.date.DateReferent.class);
    }

    public com.pullenti.ner.date.DateReferent setDate(com.pullenti.ner.date.DateReferent value) {
        if (value == null) 
            return value;
        if (getDate() == null) {
            this.addSlot(ATTR_DATE, value, true, 0);
            return value;
        }
        if (getDate().getMonth() > 0 && value.getMonth() == 0) 
            return value;
        if (getDate().getDay() > 0 && value.getDay() == 0) 
            return value;
        this.addSlot(ATTR_DATE, value, true, 0);
        return value;
    }


    public int getStudentYear() {
        return this.getIntValue(ATTR_STUDENTYEAR, 0);
    }

    public int setStudentYear(int value) {
        this.addSlot(ATTR_STUDENTYEAR, value, true, 0);
        return value;
    }


    public com.pullenti.ner._org.OrganizationReferent getOrg() {
        return (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_ORG), com.pullenti.ner._org.OrganizationReferent.class);
    }

    public com.pullenti.ner._org.OrganizationReferent setOrg(com.pullenti.ner._org.OrganizationReferent value) {
        this.addSlot(ATTR_ORG, value, true, 0);
        return value;
    }


    public com.pullenti.ner.geo.GeoReferent getCity() {
        return (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_CITY), com.pullenti.ner.geo.GeoReferent.class);
    }

    public com.pullenti.ner.geo.GeoReferent setCity(com.pullenti.ner.geo.GeoReferent value) {
        this.addSlot(ATTR_CITY, value, true, 0);
        return value;
    }


    public String getSpeciality() {
        return this.getStringValue(ATTR_SPECIALITY);
    }

    public String setSpeciality(String value) {
        this.addSlot(ATTR_SPECIALITY, value, true, 0);
        return value;
    }

    public TitlePageReferent() {
        this(null);
    }
}
