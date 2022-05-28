/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.weapon;

/**
 * Сущность - оружие
 * 
 */
public class WeaponReferent extends com.pullenti.ner.Referent {

    public WeaponReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.weapon.internal.MetaWeapon.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("WEAPON")
     */
    public static final String OBJ_TYPENAME = "WEAPON";

    /**
     * Имя атрибута - тип
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - производитель (бренд)
     */
    public static final String ATTR_BRAND = "BRAND";

    /**
     * Имя атрибута - модель
     */
    public static final String ATTR_MODEL = "MODEL";

    /**
     * Имя атрибута - собственное имя (если есть)
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - регистрационный номер
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - дата выпуска
     */
    public static final String ATTR_DATE = "DATE";

    /**
     * Имя атрибута - ссылка на другую сущность
     */
    public static final String ATTR_REF = "REF";

    /**
     * Имя атрибута - калибр
     */
    public static final String ATTR_CALIBER = "CALIBER";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                String n = s.getValue().toString();
                if (str == null || (n.length() < str.length())) 
                    str = n;
            }
        }
        if (str != null) 
            res.append(str.toLowerCase());
        if ((((str = this.getStringValue(ATTR_BRAND)))) != null) 
            res.append(" ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str));
        if ((((str = this.getStringValue(ATTR_MODEL)))) != null) 
            res.append(" ").append(str);
        if ((((str = this.getStringValue(ATTR_NAME)))) != null) {
            res.append(" \"").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str)).append("\"");
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME) && com.pullenti.unisharp.Utils.stringsNe(str, s.getValue().toString())) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicChar(str.charAt(0)) != com.pullenti.morph.LanguageHelper.isCyrillicChar((s.getValue().toString()).charAt(0))) {
                        res.append(" (").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(s.getValue().toString())).append(")");
                        break;
                    }
                }
            }
        }
        if ((((str = this.getStringValue(ATTR_NUMBER)))) != null) 
            res.append(", номер ").append(str);
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        WeaponReferent tr = (WeaponReferent)com.pullenti.unisharp.Utils.cast(obj, WeaponReferent.class);
        if (tr == null) 
            return false;
        String s1 = this.getStringValue(ATTR_NUMBER);
        String s2 = tr.getStringValue(ATTR_NUMBER);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else {
                if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                    return false;
                return true;
            }
        }
        boolean eqTypes = false;
        for (String t : this.getStringValues(ATTR_TYPE)) {
            if (tr.findSlot(ATTR_TYPE, t, true) != null) {
                eqTypes = true;
                break;
            }
        }
        if (!eqTypes) 
            return false;
        s1 = this.getStringValue(ATTR_BRAND);
        s2 = tr.getStringValue(ATTR_BRAND);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                return false;
        }
        s1 = this.getStringValue(ATTR_MODEL);
        s2 = tr.getStringValue(ATTR_MODEL);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else {
                if (this.findSlot(ATTR_MODEL, s2, true) != null) 
                    return true;
                if (tr.findSlot(ATTR_MODEL, s1, true) != null) 
                    return true;
                return false;
            }
        }
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (tr.findSlot(ATTR_NAME, s.getValue(), true) != null) 
                    return true;
            }
        }
        if (s1 != null && s2 != null) 
            return true;
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
    }
}
