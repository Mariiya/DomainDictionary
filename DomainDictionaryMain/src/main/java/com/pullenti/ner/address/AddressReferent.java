/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Сущность, представляющая адрес
 * 
 */
public class AddressReferent extends com.pullenti.ner.Referent {

    public AddressReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.address.internal.MetaAddress.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("ADDRESS")
     */
    public static final String OBJ_TYPENAME = "ADDRESS";

    /**
     * Имя атрибута - улица
     */
    public static final String ATTR_STREET = "STREET";

    /**
     * Имя атрибута - дом
     */
    public static final String ATTR_HOUSE = "HOUSE";

    /**
     * Имя атрибута - дом или участок (в тексте не указан)
     */
    public static final String ATTR_HOUSEORPLOT = "HOUSEORPLOT";

    /**
     * Имя атрибута - тип дома
     */
    public static final String ATTR_HOUSETYPE = "HOUSETYPE";

    /**
     * Имя атрибута - корпус
     */
    public static final String ATTR_CORPUS = "CORPUS";

    /**
     * Имя атрибута - строение
     */
    public static final String ATTR_BUILDING = "BUILDING";

    /**
     * Имя атрибута - тип строения
     */
    public static final String ATTR_BUILDINGTYPE = "BUILDINGTYPE";

    /**
     * Имя атрибута - корпус или квартира (когда неясно)
     */
    public static final String ATTR_CORPUSORFLAT = "CORPUSORFLAT";

    /**
     * Имя атрибута - подъезд
     */
    public static final String ATTR_PORCH = "PORCH";

    /**
     * Имя атрибута - этаж
     */
    public static final String ATTR_FLOOR = "FLOOR";

    /**
     * Имя атрибута - офис
     */
    public static final String ATTR_OFFICE = "OFFICE";

    /**
     * Имя атрибута - квартира
     */
    public static final String ATTR_FLAT = "FLAT";

    /**
     * Имя атрибута - павильон
     */
    public static final String ATTR_PAVILION = "PAVILION";

    /**
     * Имя атрибута - километр
     */
    public static final String ATTR_KILOMETER = "KILOMETER";

    /**
     * Имя атрибута - участок
     */
    public static final String ATTR_PLOT = "PLOT";

    /**
     * Имя атрибута - поле
     */
    public static final String ATTR_FIELD = "FIELD";

    /**
     * Имя атрибута - блок (ряд)
     */
    public static final String ATTR_BLOCK = "BLOCK";

    /**
     * Имя атрибута - бокс (гараж)
     */
    public static final String ATTR_BOX = "BOX";

    /**
     * Имя атрибута - географический объект (ближайший в иерархии)
     */
    public static final String ATTR_GEO = "GEO";

    /**
     * Имя атрибута - почтовый индекс
     */
    public static final String ATTR_ZIP = "ZIP";

    /**
     * Имя атрибута - почтовый ящик
     */
    public static final String ATTR_POSTOFFICEBOX = "POSTOFFICEBOX";

    /**
     * Имя атрибута - ГСП
     */
    public static final String ATTR_CSP = "CSP";

    /**
     * Имя атрибута - станция метро
     */
    public static final String ATTR_METRO = "METRO";

    /**
     * Имя атрибута - дополнительная информация (AddressDetailType)
     */
    public static final String ATTR_DETAIL = "DETAIL";

    /**
     * Имя атрибута - параметр дополнительной информации
     */
    public static final String ATTR_DETAILPARAM = "DETAILPARAM";

    /**
     * Ссылка на объект, связанный с дополнительной информацией
     */
    public static final String ATTR_DETAILREF = "DETAILREF";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    /**
     * Имя атрибута - код ФИАС (определяется анализатором FiasAnalyzer)
     */
    public static final String ATTR_FIAS = "FIAS";

    public static final String ATTR_BTI = "BTI";

    public java.util.ArrayList<com.pullenti.ner.Referent> getStreets() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_STREET) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    public String getHouse() {
        return this.getStringValue(ATTR_HOUSE);
    }

    public String setHouse(String value) {
        this.addSlot(ATTR_HOUSE, value, true, 0);
        return value;
    }


    public AddressHouseType getHouseType() {
        String str = this.getStringValue(ATTR_HOUSETYPE);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return AddressHouseType.HOUSE;
        try {
            return (AddressHouseType)AddressHouseType.of(str);
        } catch (Exception ex322) {
            return AddressHouseType.HOUSE;
        }
    }

    public AddressHouseType setHouseType(AddressHouseType value) {
        this.addSlot(ATTR_HOUSETYPE, value.toString().toUpperCase(), true, 0);
        return value;
    }


    public String getBuilding() {
        return this.getStringValue(ATTR_BUILDING);
    }

    public String setBuilding(String value) {
        this.addSlot(ATTR_BUILDING, value, true, 0);
        return value;
    }


    public AddressBuildingType getBuildingType() {
        String str = this.getStringValue(ATTR_BUILDINGTYPE);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return AddressBuildingType.BUILDING;
        try {
            return (AddressBuildingType)AddressBuildingType.of(str);
        } catch (Exception ex323) {
            return AddressBuildingType.BUILDING;
        }
    }

    public AddressBuildingType setBuildingType(AddressBuildingType value) {
        this.addSlot(ATTR_BUILDINGTYPE, value.toString().toUpperCase(), true, 0);
        return value;
    }


    public String getCorpus() {
        return this.getStringValue(ATTR_CORPUS);
    }

    public String setCorpus(String value) {
        this.addSlot(ATTR_CORPUS, value, true, 0);
        return value;
    }


    public String getCorpusOrFlat() {
        return this.getStringValue(ATTR_CORPUSORFLAT);
    }

    public String setCorpusOrFlat(String value) {
        this.addSlot(ATTR_CORPUSORFLAT, value, true, 0);
        return value;
    }


    public String getFloor() {
        return this.getStringValue(ATTR_FLOOR);
    }

    public String setFloor(String value) {
        this.addSlot(ATTR_FLOOR, value, true, 0);
        return value;
    }


    public String getPotch() {
        return this.getStringValue(ATTR_PORCH);
    }

    public String setPotch(String value) {
        this.addSlot(ATTR_PORCH, value, true, 0);
        return value;
    }


    public String getFlat() {
        return this.getStringValue(ATTR_FLAT);
    }

    public String setFlat(String value) {
        this.addSlot(ATTR_FLAT, value, true, 0);
        return value;
    }


    public String getPavilion() {
        return this.getStringValue(ATTR_PAVILION);
    }

    public String setPavilion(String value) {
        this.addSlot(ATTR_PAVILION, value, true, 0);
        return value;
    }


    public String getOffice() {
        return this.getStringValue(ATTR_OFFICE);
    }

    public String setOffice(String value) {
        this.addSlot(ATTR_OFFICE, value, true, 0);
        return value;
    }


    public String getPlot() {
        return this.getStringValue(ATTR_PLOT);
    }

    public String setPlot(String value) {
        this.addSlot(ATTR_PLOT, value, true, 0);
        return value;
    }


    public String getHouseOrPlot() {
        return this.getStringValue(ATTR_HOUSEORPLOT);
    }

    public String setHouseOrPlot(String value) {
        this.addSlot(ATTR_HOUSEORPLOT, value, true, 0);
        return value;
    }


    public String getField() {
        return this.getStringValue(ATTR_FIELD);
    }

    public String setField(String value) {
        this.addSlot(ATTR_FIELD, value, true, 0);
        return value;
    }


    public String getBlock() {
        return this.getStringValue(ATTR_BLOCK);
    }

    public String setBlock(String value) {
        this.addSlot(ATTR_BLOCK, value, true, 0);
        return value;
    }


    public String getBox() {
        return this.getStringValue(ATTR_BOX);
    }

    public String setBox(String value) {
        this.addSlot(ATTR_BOX, value, true, 0);
        return value;
    }


    public String getMetro() {
        return this.getStringValue(ATTR_METRO);
    }

    public String setMetro(String value) {
        this.addSlot(ATTR_METRO, value, true, 0);
        return value;
    }


    public String getKilometer() {
        return this.getStringValue(ATTR_KILOMETER);
    }

    public String setKilometer(String value) {
        this.addSlot(ATTR_KILOMETER, value, true, 0);
        return value;
    }


    public String getZip() {
        return this.getStringValue(ATTR_ZIP);
    }

    public String setZip(String value) {
        this.addSlot(ATTR_ZIP, value, true, 0);
        return value;
    }


    public String getPostOfficeBox() {
        return this.getStringValue(ATTR_POSTOFFICEBOX);
    }

    public String setPostOfficeBox(String value) {
        this.addSlot(ATTR_POSTOFFICEBOX, value, true, 0);
        return value;
    }


    public String getCSP() {
        return this.getStringValue(ATTR_CSP);
    }

    public String setCSP(String value) {
        this.addSlot(ATTR_CSP, value, true, 0);
        return value;
    }


    public java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> getGeos() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> res = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_GEO) && (a.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_STREET) && (a.getValue() instanceof com.pullenti.ner.Referent)) {
                for (com.pullenti.ner.Slot s : ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.Referent.class)).getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent) 
                        res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
                }
            }
        }
        for (int i = res.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (_isHigher(res.get(i), res.get(j))) {
                    res.remove(i);
                    break;
                }
                else if (_isHigher(res.get(j), res.get(i))) {
                    res.remove(j);
                    i--;
                }
            }
        }
        return res;
    }


    private static boolean _isHigher(com.pullenti.ner.geo.GeoReferent gHi, com.pullenti.ner.geo.GeoReferent gLo) {
        int i = 0;
        for (; gLo != null && (i < 10); gLo = gLo.getHigher(),i++) {
            if (gLo.canBeEquals(gHi, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                return true;
        }
        return false;
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        com.pullenti.ner.Referent sr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_STREET), com.pullenti.ner.Referent.class);
        if (sr != null) 
            return sr;
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> _geos = getGeos();
        for (com.pullenti.ner.geo.GeoReferent g : _geos) {
            if (g.isCity()) 
                return g;
        }
        for (com.pullenti.ner.geo.GeoReferent g : _geos) {
            if (g.isRegion() && !g.isState()) 
                return g;
        }
        if (_geos.size() > 0) 
            return _geos.get(0);
        return null;
    }


    public void addReferent(com.pullenti.ner.Referent r) {
        if (r == null) 
            return;
        com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
        if (geo != null) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_GEO)) {
                    com.pullenti.ner.geo.GeoReferent geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class);
                    if (geo0 == null) 
                        continue;
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo0, geo, null, null)) {
                        if (geo.getHigher() == geo0 || geo.isCity()) {
                            this.uploadSlot(s, geo);
                            return;
                        }
                    }
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo, geo0, null, null)) 
                        return;
                }
            }
            this.addSlot(ATTR_GEO, r, false, 0);
        }
        else if ((r instanceof StreetReferent) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) 
            this.addSlot(ATTR_STREET, r, false, 0);
    }

    public AddressDetailType getDetail() {
        String s = this.getStringValue(ATTR_DETAIL);
        if (s == null) 
            return AddressDetailType.UNDEFINED;
        try {
            return (AddressDetailType)AddressDetailType.of(s);
        } catch (Exception ex324) {
        }
        return AddressDetailType.UNDEFINED;
    }

    public AddressDetailType setDetail(AddressDetailType value) {
        if (value != AddressDetailType.UNDEFINED) 
            this.addSlot(ATTR_DETAIL, value.toString().toUpperCase(), true, 0);
        return value;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        java.util.ArrayList<com.pullenti.ner.Referent> strs = getStreets();
        if (strs.size() == 0) {
            if (getMetro() != null) {
                if (res.length() > 0) 
                    res.append(' ');
                res.append((String)com.pullenti.unisharp.Utils.notnull(this.getMetro(), ""));
            }
        }
        else {
            if (res.length() > 0) 
                res.append(' ');
            for (int i = 0; i < strs.size(); i++) {
                if (i > 0) 
                    res.append(", ");
                res.append(strs.get(i).toStringEx(true, lang, 0));
            }
        }
        this._outHouse(res);
        Object kladr = this.getSlotValue(ATTR_FIAS);
        if (kladr instanceof com.pullenti.ner.Referent) {
            res.append(" (ФИАС: ").append(((String)com.pullenti.unisharp.Utils.notnull(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(kladr, com.pullenti.ner.Referent.class)).getStringValue("GUID"), "?")));
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FIAS) && (s.getValue() instanceof com.pullenti.ner.Referent) && s.getValue() != kladr) 
                    res.append(", ").append(((String)com.pullenti.unisharp.Utils.notnull(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).getStringValue("GUID"), "?")));
            }
            res.append(')');
        }
        String bti = this.getStringValue(ATTR_BTI);
        if (bti != null) 
            res.append(" (БТИ ").append(bti).append(")");
        for (com.pullenti.ner.geo.GeoReferent g : getGeos()) {
            if (res.length() > 0 && res.charAt(res.length() - 1) == ' ') 
                res.setLength(res.length() - 1);
            if (res.length() > 0 && res.charAt(res.length() - 1) == ']') {
            }
            else if (res.length() > 0) 
                res.append(';');
            res.append(" ").append(g.toStringEx(true, lang, lev + 1));
        }
        if (getZip() != null) 
            res.append("; ").append(this.getZip());
        String str = this.getStringValue(ATTR_DETAIL);
        if (str != null) 
            str = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.address.internal.MetaAddress.globalMeta.detailFeature.convertInnerValueToOuterValue(str, lang), String.class);
        if (str != null) {
            res.append(" [").append(str.toLowerCase());
            if ((((str = this.getStringValue(ATTR_DETAILPARAM)))) != null) 
                res.append(", ").append(str);
            if (!shortVariant) {
                com.pullenti.ner.Referent dd = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DETAILREF), com.pullenti.ner.Referent.class);
                if (dd != null) 
                    res.append(", ").append(dd.toStringEx(true, lang, lev + 1));
            }
            res.append(']');
        }
        else {
            com.pullenti.ner.Referent dd = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DETAILREF), com.pullenti.ner.Referent.class);
            if (dd != null) 
                res.append(" [").append(dd.toStringEx(true, lang, lev + 1)).append("]");
        }
        return res.toString().trim();
    }

    private void _outHouse(StringBuilder res) {
        if (getKilometer() != null) 
            res.append(" ").append(this.getKilometer()).append("км.");
        if (getHouse() != null) {
            AddressHouseType ty = getHouseType();
            if (ty == AddressHouseType.ESTATE) 
                res.append(" влад.");
            else if (ty == AddressHouseType.HOUSEESTATE) 
                res.append(" домовл.");
            else 
                res.append(" д.");
            res.append((com.pullenti.unisharp.Utils.stringsEq(this.getHouse(), "0") ? "Б/Н" : this.getHouse()));
        }
        else if (getHouseOrPlot() != null) 
            res.append(" ").append(this.getHouseOrPlot());
        if (getCorpus() != null) 
            res.append(" корп.").append(((com.pullenti.unisharp.Utils.stringsEq(this.getCorpus(), "0") ? "Б/Н" : this.getCorpus())));
        if (getBuilding() != null) {
            AddressBuildingType ty = getBuildingType();
            if (ty == AddressBuildingType.CONSTRUCTION) 
                res.append(" сооруж.");
            else if (ty == AddressBuildingType.LITER) 
                res.append(" лит.");
            else 
                res.append(" стр.");
            res.append((com.pullenti.unisharp.Utils.stringsEq(this.getBuilding(), "0") ? "Б/Н" : this.getBuilding()));
        }
        if (getPotch() != null) 
            res.append(" под.").append(this.getPotch());
        if (getFloor() != null) 
            res.append(" эт.").append(this.getFloor());
        if (getFlat() != null) 
            res.append(" кв.").append(this.getFlat());
        if (getCorpusOrFlat() != null) 
            res.append(" корп.(кв.?)").append(this.getCorpusOrFlat());
        if (getOffice() != null) 
            res.append(" оф.").append(this.getOffice());
        if (getPavilion() != null) 
            res.append(" пав.").append(this.getPavilion());
        if (getBlock() != null) 
            res.append(" блок ").append(this.getBlock());
        if (getPlot() != null) 
            res.append(" уч.").append(this.getPlot());
        if (getField() != null) 
            res.append(" поле ").append(this.getField());
        if (getBox() != null) 
            res.append(" бокс ").append(this.getBox());
        if (getPostOfficeBox() != null) 
            res.append(" а\\я").append(this.getPostOfficeBox());
        if (getCSP() != null) 
            res.append(" ГСП-").append(this.getCSP());
    }

    /**
     * Вывод адреса в каноническом виде (сначала индекс, потом страна, город, улица и т.д.)
     * @return 
     */
    public String toStringClassic() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> _geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
        com.pullenti.ner.geo.GeoReferent geo = null;
        StreetReferent street = (StreetReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_STREET), StreetReferent.class);
        if (street != null) 
            geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(street.getSlotValue(StreetReferent.ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
        if (geo == null) 
            geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
        if (geo != null) {
            for (int i = 0; i < 10; i++) {
                if (!_geos.contains(geo)) 
                    _geos.add(0, geo);
                geo = geo.getHigher();
                if (geo == null) 
                    break;
            }
        }
        if (_geos.size() == 0) 
            return this.toString();
        StringBuilder res = new StringBuilder();
        if (getZip() != null) 
            res.append(this.getZip()).append(", ");
        for (com.pullenti.ner.geo.GeoReferent g : _geos) {
            com.pullenti.ner.geo.GeoReferent hi = g.getHigher();
            g.setHigher(null);
            res.append(g.toString()).append(", ");
            g.setHigher(hi);
        }
        if (street != null) 
            res.append(street.toStringEx(true, null, 0));
        else if (res.length() > 1) {
            if (res.charAt(res.length() - 1) == ' ') 
                res.setLength(res.length() - 1);
            if (res.charAt(res.length() - 1) == ',') 
                res.setLength(res.length() - 1);
        }
        StringBuilder tmp2 = new StringBuilder();
        this._outHouse(tmp2);
        if (tmp2.length() > 0) 
            res.append(", ").append(tmp2.toString());
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        AddressReferent addr = (AddressReferent)com.pullenti.unisharp.Utils.cast(obj, AddressReferent.class);
        if (addr == null) 
            return false;
        java.util.ArrayList<com.pullenti.ner.Referent> strs1 = getStreets();
        java.util.ArrayList<com.pullenti.ner.Referent> strs2 = addr.getStreets();
        if (strs1.size() > 0 || strs2.size() > 0) {
            boolean ok = false;
            for (com.pullenti.ner.Referent s : strs1) {
                for (com.pullenti.ner.Referent ss : strs2) {
                    if (ss.canBeEquals(s, typ)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) 
                return false;
        }
        if (addr.getHouse() != null || getHouse() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getHouse(), getHouse())) 
                return false;
        }
        if (addr.getHouseOrPlot() != null || getHouseOrPlot() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getHouseOrPlot(), getHouseOrPlot())) 
                return false;
        }
        if (addr.getBuilding() != null || getBuilding() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getBuilding(), getBuilding())) 
                return false;
        }
        if (addr.getPlot() != null || getPlot() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getPlot(), getPlot())) 
                return false;
        }
        if (addr.getField() != null || getField() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getField(), getField())) 
                return false;
        }
        if (addr.getBox() != null || getBox() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getBox(), getBox())) 
                return false;
        }
        if (addr.getBlock() != null || getBlock() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getBlock(), getBlock())) 
                return false;
        }
        if (addr.getCorpus() != null || getCorpus() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getCorpus(), getCorpus())) {
                if (addr.getCorpus() != null && com.pullenti.unisharp.Utils.stringsEq(addr.getCorpus(), getCorpusOrFlat())) {
                }
                else if (getCorpus() != null && com.pullenti.unisharp.Utils.stringsEq(addr.getCorpusOrFlat(), getCorpus())) {
                }
                else 
                    return false;
            }
        }
        if (addr.getFlat() != null || getFlat() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getFlat(), getFlat())) {
                if (addr.getFlat() != null && com.pullenti.unisharp.Utils.stringsEq(addr.getFlat(), getCorpusOrFlat())) {
                }
                else if (getFlat() != null && com.pullenti.unisharp.Utils.stringsEq(addr.getCorpusOrFlat(), getFlat())) {
                }
                else 
                    return false;
            }
        }
        if (addr.getCorpusOrFlat() != null || getCorpusOrFlat() != null) {
            if (getCorpusOrFlat() != null && addr.getCorpusOrFlat() != null) {
                if (com.pullenti.unisharp.Utils.stringsNe(getCorpusOrFlat(), addr.getCorpusOrFlat())) 
                    return false;
            }
            else if (getCorpusOrFlat() == null) {
                if (getCorpus() == null && getFlat() == null) 
                    return false;
            }
            else if (addr.getCorpusOrFlat() == null) {
                if (addr.getCorpus() == null && addr.getFlat() == null) 
                    return false;
            }
        }
        if (addr.getPavilion() != null || getPavilion() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getPavilion(), getPavilion())) 
                return false;
        }
        if (addr.getOffice() != null || getOffice() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getOffice(), getOffice())) 
                return false;
        }
        if (addr.getPotch() != null || getPotch() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getPotch(), getPotch())) 
                return false;
        }
        if (addr.getFloor() != null || getFloor() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getFloor(), getFloor())) 
                return false;
        }
        if (addr.getPostOfficeBox() != null || getPostOfficeBox() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getPostOfficeBox(), getPostOfficeBox())) 
                return false;
        }
        if (addr.getCSP() != null && getCSP() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(addr.getCSP(), getCSP())) 
                return false;
        }
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeos();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = addr.getGeos();
        if (geos1.size() > 0 && geos2.size() > 0) {
            boolean ok = false;
            for (com.pullenti.ner.geo.GeoReferent g1 : geos1) {
                for (com.pullenti.ner.geo.GeoReferent g2 : geos2) {
                    if (g1.canBeEquals(g2, typ)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) 
                return false;
        }
        return true;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        if (getCorpusOrFlat() != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(getFlat(), getCorpusOrFlat())) 
                setCorpusOrFlat(null);
            else if (com.pullenti.unisharp.Utils.stringsEq(getCorpus(), getCorpusOrFlat())) 
                setCorpusOrFlat(null);
        }
        this.correct();
    }

    public void correct() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> _geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_GEO) && (a.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                _geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            else if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_STREET) && (a.getValue() instanceof com.pullenti.ner.Referent)) {
                for (com.pullenti.ner.Slot s : ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.Referent.class)).getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent) 
                        _geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
                }
            }
        }
        for (int i = _geos.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (_isHigher(_geos.get(i), _geos.get(j))) {
                    com.pullenti.ner.Slot s = this.findSlot(ATTR_GEO, _geos.get(i), true);
                    if (s != null) 
                        getSlots().remove(s);
                    _geos.remove(i);
                    break;
                }
                else if (_isHigher(_geos.get(j), _geos.get(i))) {
                    com.pullenti.ner.Slot s = this.findSlot(ATTR_GEO, _geos.get(j), true);
                    if (s != null) 
                        getSlots().remove(s);
                    _geos.remove(j);
                    i--;
                }
            }
        }
        if (_geos.size() == 2) {
            com.pullenti.ner.geo.GeoReferent reg = null;
            com.pullenti.ner.geo.GeoReferent cit = null;
            if (_geos.get(0).isCity() && _geos.get(1).isRegion()) {
                cit = _geos.get(0);
                reg = _geos.get(1);
            }
            else if (_geos.get(1).isCity() && _geos.get(0).isRegion()) {
                cit = _geos.get(1);
                reg = _geos.get(0);
            }
            if (cit != null && cit.getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(reg, cit, null, null)) {
                cit.setHigher(reg);
                com.pullenti.ner.Slot ss = this.findSlot(ATTR_GEO, reg, true);
                if (ss != null) 
                    getSlots().remove(ss);
                _geos = this.getGeos();
            }
            else {
                com.pullenti.ner.geo.GeoReferent stat = null;
                com.pullenti.ner.geo.GeoReferent geo = null;
                if (_geos.get(0).isState() && !_geos.get(1).isState()) {
                    stat = _geos.get(0);
                    geo = _geos.get(1);
                }
                else if (_geos.get(1).isState() && !_geos.get(0).isState()) {
                    stat = _geos.get(1);
                    geo = _geos.get(0);
                }
                if (stat != null) {
                    geo = geo.getTopHigher();
                    if (geo.getHigher() == null) {
                        geo.setHigher(stat);
                        com.pullenti.ner.Slot s = this.findSlot(ATTR_GEO, stat, true);
                        if (s != null) 
                            getSlots().remove(s);
                    }
                }
            }
        }
    }
}
