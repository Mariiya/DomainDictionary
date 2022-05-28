/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person;

/**
 * Удостоверение личности (паспорт и пр.)
 */
public class PersonIdentityReferent extends com.pullenti.ner.Referent {

    public PersonIdentityReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.person.internal.MetaPersonIdentity.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("NAMEDENTITY")
     */
    public static final String OBJ_TYPENAME = "PERSONIDENTITY";

    /**
     * Имя атрибута - тип документа
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - серийный номер
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - дата выдачи
     */
    public static final String ATTR_DATE = "DATE";

    /**
     * Имя атрибута - выдавшая организация (OrganizationReferent)
     */
    public static final String ATTR_ORG = "ORG";

    /**
     * Имя атрибута - географический объект (GeoReferent)
     */
    public static final String ATTR_STATE = "STATE";

    /**
     * Имя атрибута - адрес регистрации (AddressReferent)
     */
    public static final String ATTR_ADDRESS = "ADDRESS";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?"));
        if (getNumber() != null) 
            res.append(" №").append(this.getNumber());
        if (getState() != null) 
            res.append(", ").append(this.getState().toStringEx(true, lang, lev + 1));
        if (!shortVariant) {
            String dat = this.getStringValue(ATTR_DATE);
            String __org = this.getStringValue(ATTR_ORG);
            if (dat != null || __org != null) {
                res.append(", выдан");
                if (dat != null) 
                    res.append(" ").append(dat);
                if (__org != null) 
                    res.append(" ").append(__org);
            }
        }
        return res.toString();
    }

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }

    public String setNumber(String value) {
        this.addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    public com.pullenti.ner.Referent getState() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_STATE), com.pullenti.ner.Referent.class);
    }

    public com.pullenti.ner.Referent setState(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_STATE, value, true, 0);
        return value;
    }


    public com.pullenti.ner.Referent getAddress() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_ADDRESS), com.pullenti.ner.Referent.class);
    }

    public com.pullenti.ner.Referent setAddress(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_ADDRESS, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        PersonIdentityReferent id = (PersonIdentityReferent)com.pullenti.unisharp.Utils.cast(obj, PersonIdentityReferent.class);
        if (id == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), id.getTyp())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getNumber(), id.getNumber())) 
            return false;
        if (getState() != null && id.getState() != null) {
            if (getState() != id.getState()) 
                return false;
        }
        return true;
    }
}
