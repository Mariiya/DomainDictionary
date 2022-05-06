/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Сущность: улица, проспект, площадь, шоссе и т.п. Выделяется анализатором AddressAnalyzer.
 * 
 */
public class StreetReferent extends com.pullenti.ner.Referent {

    public StreetReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.address.internal.MetaStreet.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("STREET")
     */
    public static final String OBJ_TYPENAME = "STREET";

    /**
     * Имя атрибута - тип (улица, переулок, площадь...)
     */
    public static final String ATTR_TYP = "TYP";

    /**
     * Класс объекта (StreetKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - наименование (м.б. несколько вариантов)
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - номер
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - дополнительный номер
     */
    public static final String ATTR_SECNUMBER = "SECNUMBER";

    /**
     * Имя атрибута - вышележащая улица (например, улица в микрорайоне)
     */
    public static final String ATTR_HIGHER = "HIGHER";

    /**
     * Имя атрибута - географический объект
     */
    public static final String ATTR_GEO = "GEO";

    /**
     * Имя атрибута - дополнительная ссылка (для территории организации - на саму организацию)
     */
    public static final String ATTR_REF = "REF";

    /**
     * Имя атрибута - код ФИАС (определяется анализатором FiasAnalyzer)
     */
    public static final String ATTR_FIAS = "FIAS";

    public static final String ATTR_BTI = "BTI";

    public static final String ATTR_OKM = "OKM";

    public java.util.ArrayList<String> getTyps() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYP)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }

    public String setNumber(String value) {
        this.addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    public String getSecNumber() {
        return this.getStringValue(ATTR_SECNUMBER);
    }

    public String setSecNumber(String value) {
        this.addSlot(ATTR_SECNUMBER, value, true, 0);
        return value;
    }


    public StreetReferent getHigher() {
        return (StreetReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_HIGHER), StreetReferent.class);
    }

    public StreetReferent setHigher(StreetReferent value) {
        this.addSlot(ATTR_HIGHER, value, true, 0);
        return value;
    }


    public java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> getGeos() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> res = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_GEO) && (a.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.geo.GeoReferent.class));
        }
        return res;
    }


    public com.pullenti.ner.geo.GeoReferent getCity() {
        for (com.pullenti.ner.geo.GeoReferent g : getGeos()) {
            if (g.isCity()) 
                return g;
            else if (g.getHigher() != null && g.getHigher().isCity()) 
                return g.getHigher();
        }
        return null;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        StreetReferent hi = getHigher();
        if (hi != null) 
            return hi;
        return (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder tmp = new StringBuilder();
        String nam = this.getStringValue(ATTR_NAME);
        java.util.ArrayList<String> _typs = getTyps();
        if (_typs.size() > 0) {
            for (int i = 0; i < _typs.size(); i++) {
                if (nam != null && (nam.indexOf(_typs.get(i).toUpperCase()) >= 0)) 
                    continue;
                if (tmp.length() > 0) 
                    tmp.append('/');
                tmp.append(_typs.get(i));
            }
        }
        else 
            tmp.append((lang != null && lang.isUa() ? "вулиця" : "улица"));
        String num = getNumber();
        StreetKind ki = getKind();
        if ((num != null && !num.endsWith("км") && ki != StreetKind.ORG) && ki != StreetKind.AREA) {
            tmp.append(" ").append(num);
            if (getSecNumber() != null) 
                tmp.append(" ").append(this.getSecNumber());
        }
        if (nam != null) 
            tmp.append(" ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(nam));
        if (num != null && num.endsWith("км")) 
            tmp.append(" ").append(num);
        else if (num != null && ((ki == StreetKind.ORG || ki == StreetKind.AREA))) 
            tmp.append("-").append(num);
        if (!shortVariant) {
            Object kladr = this.getSlotValue(ATTR_FIAS);
            if (kladr instanceof com.pullenti.ner.Referent) {
                tmp.append(" (ФИАС: ").append(((String)com.pullenti.unisharp.Utils.notnull(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(kladr, com.pullenti.ner.Referent.class)).getStringValue("GUID"), "?")));
                for (com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIAS) && (s.getValue() instanceof com.pullenti.ner.Referent) && s.getValue() != kladr) 
                        tmp.append(", ").append(((String)com.pullenti.unisharp.Utils.notnull(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).getStringValue("GUID"), "?")));
                }
                tmp.append(')');
            }
            String bti = this.getStringValue(ATTR_BTI);
            if (bti != null) 
                tmp.append(" (БТИ ").append(bti).append(")");
            String okm = this.getStringValue(ATTR_OKM);
            if (okm != null) 
                tmp.append(" (ОКМ УМ ").append(okm).append(")");
        }
        if (!shortVariant && getCity() != null) 
            tmp.append("; ").append(this.getCity().toStringEx(true, lang, lev + 1));
        return tmp.toString();
    }

    public StreetKind getKind() {
        String str = this.getStringValue(ATTR_KIND);
        if (str == null) 
            return StreetKind.UNDEFINED;
        try {
            return (StreetKind)StreetKind.of(str);
        } catch (Exception ex325) {
        }
        return StreetKind.UNDEFINED;
    }

    public StreetKind setKind(StreetKind value) {
        this.addSlot(ATTR_KIND, value.toString().toUpperCase(), true, 0);
        return value;
    }


    public void addTyp(String typ) {
        this.addSlot(ATTR_TYP, typ, false, 0);
        if (getKind() == StreetKind.UNDEFINED) {
            if (com.pullenti.unisharp.Utils.stringsEq(typ, "железная дорога")) 
                setKind(StreetKind.RAILWAY);
            else if ((typ.indexOf("дорога") >= 0) || com.pullenti.unisharp.Utils.stringsEq(typ, "шоссе")) 
                setKind(StreetKind.ROAD);
            else if ((typ.indexOf("метро") >= 0)) 
                setKind(StreetKind.METRO);
            else if (com.pullenti.unisharp.Utils.stringsEq(typ, "территория")) 
                setKind(StreetKind.AREA);
            else if (com.pullenti.ner.address.internal.StreetItemToken._isRegion(typ)) 
                setKind(StreetKind.AREA);
            else if (com.pullenti.ner.address.internal.StreetItemToken._isSpec(typ)) 
                setKind(StreetKind.SPEC);
        }
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return this._canBeEquals(obj, typ, false);
    }

    private boolean _canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ, boolean ignoreGeo) {
        StreetReferent stri = (StreetReferent)com.pullenti.unisharp.Utils.cast(obj, StreetReferent.class);
        if (stri == null) 
            return false;
        if (getKind() != stri.getKind()) 
            return false;
        java.util.ArrayList<String> typs1 = getTyps();
        java.util.ArrayList<String> typs2 = stri.getTyps();
        boolean ok = false;
        if (typs1.size() > 0 && typs2.size() > 0) {
            for (String t : typs1) {
                if (typs2.contains(t)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return false;
        }
        String num = getNumber();
        String num1 = stri.getNumber();
        if (num != null || num1 != null) {
            if (num == null || num1 == null) 
                return false;
            String sec = getSecNumber();
            String sec1 = stri.getSecNumber();
            if (sec == null && sec1 == null) {
                if (com.pullenti.unisharp.Utils.stringsNe(num, num1)) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(num, num1)) {
                if (com.pullenti.unisharp.Utils.stringsNe(sec, sec1)) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(sec, num1) && com.pullenti.unisharp.Utils.stringsEq(sec1, num)) {
            }
            else 
                return false;
        }
        java.util.ArrayList<String> names1 = getNames();
        java.util.ArrayList<String> names2 = stri.getNames();
        if (names1.size() > 0 || names2.size() > 0) {
            ok = false;
            for (String n : names1) {
                if (names2.contains(n)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return false;
        }
        if (getHigher() != null && stri.getHigher() != null) {
            if (!getHigher().canBeEquals(stri.getHigher(), typ)) 
                return false;
        }
        if (ignoreGeo) 
            return true;
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeos();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = stri.getGeos();
        if (geos1.size() > 0 && geos2.size() > 0) {
            ok = false;
            for (com.pullenti.ner.geo.GeoReferent g1 : geos1) {
                for (com.pullenti.ner.geo.GeoReferent g2 : geos2) {
                    if (g1.canBeEquals(g2, typ)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) {
                if (getCity() != null && stri.getCity() != null) 
                    ok = this.getCity().canBeEquals(stri.getCity(), typ);
            }
            if (!ok) 
                return false;
        }
        return true;
    }

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (com.pullenti.unisharp.Utils.stringsEq(attrName, ATTR_NAME) && (attrValue instanceof String)) {
            String str = (String)com.pullenti.unisharp.Utils.cast(attrValue, String.class);
            if (str.indexOf('.') > 0) {
                for (int i = 1; i < (str.length() - 1); i++) {
                    if (str.charAt(i) == '.' && str.charAt(i + 1) != ' ') 
                        str = str.substring(0, 0 + i + 1) + " " + str.substring(i + 1);
                }
            }
            attrValue = str;
        }
        return super.addSlot(attrName, attrValue, clearOldValue, statCount);
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        if (this.findSlot(ATTR_SECNUMBER, null, true) != null) {
            java.util.ArrayList<String> nums = this.getStringValues(ATTR_NUMBER);
            java.util.ArrayList<String> nums2 = this.getStringValues(ATTR_SECNUMBER);
            if ((nums.size() == 2 && nums2.size() == 2 && nums2.contains(nums.get(0))) && nums2.contains(nums.get(1))) {
                com.pullenti.ner.Slot s = this.findSlot(ATTR_NUMBER, nums.get(1), true);
                if (s != null) 
                    getSlots().remove(s);
                s = this.findSlot(ATTR_SECNUMBER, nums.get(0), true);
                if (s != null) 
                    getSlots().remove(s);
            }
        }
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        if (!this._canBeEquals(obj, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT, true)) 
            return false;
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeos();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = ((StreetReferent)com.pullenti.unisharp.Utils.cast(obj, StreetReferent.class)).getGeos();
        if (geos2.size() == 0 || geos1.size() > 0) 
            return false;
        return true;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        java.util.ArrayList<String> _names = getNames();
        for (String n : _names) {
            oi.termins.add(new com.pullenti.ner.core.Termin(n, null, false));
        }
        return oi;
    }

    public void correct() {
        java.util.ArrayList<String> _names = getNames();
        for (int i = _names.size() - 1; i >= 0; i--) {
            String ss = _names.get(i);
            int jj = ss.indexOf(' ');
            if (jj < 0) 
                continue;
            if (ss.lastIndexOf(' ') != jj) 
                continue;
            String[] pp = com.pullenti.unisharp.Utils.split(ss, String.valueOf(' '), false);
            if (pp.length == 2) {
                String ss2 = (pp[1] + " " + pp[0]);
                if (!_names.contains(ss2)) 
                    this.addSlot(ATTR_NAME, ss2, false, 0);
            }
        }
    }
}
