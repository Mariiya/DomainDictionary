/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.named;

/**
 * Сущность "тип" + "имя" (планеты, памятники, здания, местоположения, планеты и пр.)
 * 
 */
public class NamedEntityReferent extends com.pullenti.ner.Referent {

    public NamedEntityReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.named.internal.MetaNamedEntity.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("NAMEDENTITY")
     */
    public static final String OBJ_TYPENAME = "NAMEDENTITY";

    /**
     * Имя атрибута - наименование
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - категория сущности (NamedEntityKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - тип
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - ссылка на другую сущность
     */
    public static final String ATTR_REF = "REF";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String typ = this.getStringValue(ATTR_TYPE);
        if (typ != null) 
            res.append(typ);
        String name = this.getStringValue(ATTR_NAME);
        if (name != null) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(name));
        }
        com.pullenti.ner.Referent re = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        if (re != null) {
            if (res.length() > 0) 
                res.append("; ");
            res.append(re.toStringEx(shortVariant, lang, lev + 1));
        }
        return res.toString();
    }

    public NamedEntityKind getKind() {
        String str = this.getStringValue(ATTR_KIND);
        if (str == null) 
            return NamedEntityKind.UNDEFINED;
        try {
            return (NamedEntityKind)NamedEntityKind.of(str);
        } catch (Exception ex1929) {
        }
        return NamedEntityKind.UNDEFINED;
    }

    public NamedEntityKind setKind(NamedEntityKind value) {
        this.addSlot(ATTR_KIND, value.toString().toLowerCase(), true, 0);
        return value;
    }


    @Override
    public String toSortString() {
        return getKind() + this.toStringEx(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                String str = s.getValue().toString();
                if (!res.contains(str)) 
                    res.add(str);
                if (str.indexOf(' ') > 0 || str.indexOf('-') > 0) {
                    str = str.replace(" ", "").replace("-", "");
                    if (!res.contains(str)) 
                        res.add(str);
                }
            }
        }
        if (res.size() == 0) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                    String t = s.getValue().toString();
                    if (!res.contains(t)) 
                        res.add(t);
                }
            }
        }
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        NamedEntityReferent ent = (NamedEntityReferent)com.pullenti.unisharp.Utils.cast(obj, NamedEntityReferent.class);
        if (ent == null) 
            return false;
        if (ent.getKind() != getKind()) 
            return false;
        java.util.ArrayList<String> names = this.getStringValues(ATTR_NAME);
        java.util.ArrayList<String> names2 = obj.getStringValues(ATTR_NAME);
        boolean eqNames = false;
        if ((names != null && names.size() > 0 && names2 != null) && names2.size() > 0) {
            for (String n : names) {
                if (names2.contains(n)) 
                    eqNames = true;
            }
            if (!eqNames) 
                return false;
        }
        java.util.ArrayList<String> typs = this.getStringValues(ATTR_TYPE);
        java.util.ArrayList<String> typs2 = obj.getStringValues(ATTR_TYPE);
        boolean eqTyps = false;
        if ((typs != null && typs.size() > 0 && typs2 != null) && typs2.size() > 0) {
            for (String ty : typs) {
                if (typs2.contains(ty)) 
                    eqTyps = true;
            }
            if (!eqTyps) 
                return false;
        }
        if (!eqTyps && !eqNames) 
            return false;
        com.pullenti.ner.Referent re1 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent re2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(obj.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        if (re1 != null && re2 != null) {
            if (!re1.canBeEquals(re2, typ)) 
                return false;
        }
        else if (re1 != null || re2 != null) {
        }
        return true;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        return this._CreateOntologyItem(2, false, false);
    }

    public com.pullenti.ner.core.IntOntologyItem _CreateOntologyItem(int minLen, boolean onlyNames, boolean pureNames) {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        java.util.ArrayList<String> typs = (java.util.ArrayList<String>)com.pullenti.unisharp.Utils.notnull(this.getStringValues(ATTR_TYPE), new java.util.ArrayList<String>());
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String s = a.getValue().toString().toUpperCase();
                if (!vars.contains(s)) 
                    vars.add(s);
                if (!pureNames) {
                    int sp = 0;
                    for (int jj = 0; jj < s.length(); jj++) {
                        if (s.charAt(jj) == ' ') 
                            sp++;
                    }
                    if (sp == 1) {
                        s = s.replace(" ", "");
                        if (!vars.contains(s)) 
                            vars.add(s);
                    }
                }
            }
        }
        if (!onlyNames) {
            if (vars.size() == 0) {
                for (String t : typs) {
                    String up = t.toUpperCase();
                    if (!vars.contains(up)) 
                        vars.add(up);
                }
            }
        }
        int max = 20;
        int cou = 0;
        for (String v : vars) {
            if (v.length() >= minLen) {
                oi.termins.add(new com.pullenti.ner.core.Termin(v, null, false));
                if ((++cou) >= max) 
                    break;
            }
        }
        if (oi.termins.size() == 0) 
            return null;
        return oi;
    }

    public static NamedEntityReferent _new1928(NamedEntityKind _arg1) {
        NamedEntityReferent res = new NamedEntityReferent();
        res.setKind(_arg1);
        return res;
    }
}
