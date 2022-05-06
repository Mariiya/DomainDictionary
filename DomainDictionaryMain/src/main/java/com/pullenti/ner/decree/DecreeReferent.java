/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Сущность - ссылка на НПА (закон, приказ, договор, постановление...)
 * 
 */
public class DecreeReferent extends com.pullenti.ner.Referent {

    public DecreeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.decree.internal.MetaDecree.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("DECREE")
     */
    public static final String OBJ_TYPENAME = "DECREE";

    /**
     * Имя атрибута - тип
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - наименование
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - регистрационный номер
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - дата принятия
     */
    public static final String ATTR_DATE = "DATE";

    /**
     * Имя атрибута - публикующий орган
     */
    public static final String ATTR_SOURCE = "SOURCE";

    /**
     * Имя атрибута - географический объект (GeoReferent)
     */
    public static final String ATTR_GEO = "GEO";

    /**
     * Имя атрибута - номер чтения
     */
    public static final String ATTR_READING = "READING";

    /**
     * Имя атрибута - номер судебного дела (для судебных документов)
     */
    public static final String ATTR_CASENUMBER = "CASENUMBER";

    /**
     * Имя атрибута - редакция
     */
    public static final String ATTR_EDITION = "EDITION";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        DecreeKind ki = getKind();
        boolean outPart = false;
        String nam = getName();
        if (getTyp() != null) {
            if ((nam != null && !nam.startsWith("О") && (nam.indexOf(this.getTyp()) >= 0)) && ki != DecreeKind.STANDARD) {
                res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(nam));
                nam = null;
            }
            else if (ki == DecreeKind.STANDARD && (getTyp().length() < 6)) 
                res.append(this.getTyp());
            else 
                res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(this.getTyp()));
        }
        else 
            res.append("?");
        boolean outSrc = true;
        if (ki == DecreeKind.CONTRACT && this.findSlot(ATTR_SOURCE, null, true) != null) {
            java.util.ArrayList<String> srcs = new java.util.ArrayList<String>();
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_SOURCE)) 
                    srcs.add(s.getValue().toString());
            }
            if (srcs.size() > 1) {
                for (int ii = 0; ii < srcs.size(); ii++) {
                    if (ii > 0 && ((ii + 1) < srcs.size())) 
                        res.append(", ");
                    else if (ii > 0) 
                        res.append(" и ");
                    else 
                        res.append(" между ");
                    res.append(srcs.get(ii));
                    outSrc = false;
                }
            }
        }
        String num = getNumber();
        if (num != null) {
            res.append(" № ").append(num);
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NUMBER)) {
                    String nn = s.getValue().toString();
                    if (com.pullenti.unisharp.Utils.stringsNe(nn, num)) 
                        res.append("/").append(nn);
                }
            }
        }
        if ((((num = this.getCaseNumber()))) != null) 
            res.append(" по делу № ").append(num);
        if (this.getStringValue(ATTR_DATE) != null) 
            res.append(" ").append((ki == DecreeKind.PROGRAM ? "" : "от ")).append(this.getStringValue(ATTR_DATE));
        if (outSrc && this.getSlotValue(ATTR_SOURCE) != null) 
            res.append("; ").append(this.getStringValue(ATTR_SOURCE));
        if (!shortVariant) {
            String s = this.getStringValue(ATTR_GEO);
            if (s != null) 
                res.append("; ").append(s);
            if (nam != null) {
                s = this._getShortName();
                if (s != null) 
                    res.append("; \"").append(s).append("\"");
            }
        }
        return res.toString().trim();
    }

    public String getName() {
        String nam = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                String n = s.getValue().toString();
                if (nam == null || nam.length() > n.length()) 
                    nam = n;
            }
        }
        return nam;
    }


    private String _getShortName() {
        String nam = getName();
        if (nam == null) 
            return null;
        if (nam.length() > 100) {
            int i = 100;
            for (; i < nam.length(); i++) {
                if (!Character.isLetter(nam.charAt(i))) 
                    break;
            }
            if (i < nam.length()) 
                nam = nam.substring(0, 0 + i) + "...";
        }
        return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(nam);
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NUMBER)) 
                res.add(s.getValue().toString());
        }
        if (res.size() == 0 && getTyp() != null) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_GEO)) 
                    res.add((this.getTyp() + " " + s.getValue().toString()));
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(getTyp(), "КОНСТИТУЦИЯ")) 
            res.add(this.getTyp());
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
    }

    public java.time.LocalDateTime getDate() {
        String s = this.getStringValue(ATTR_DATE);
        if (s == null) 
            return null;
        return com.pullenti.ner.decree.internal.DecreeHelper.parseDateTime(s);
    }


    public boolean addDate(com.pullenti.ner.decree.internal.DecreeToken dt) {
        if (dt == null) 
            return false;
        if (dt.ref != null && (dt.ref.referent instanceof com.pullenti.ner.date.DateReferent)) {
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(dt.ref.referent, com.pullenti.ner.date.DateReferent.class);
            if (dr.isRelative()) 
                return false;
            int year = dr.getYear();
            int mon = dr.getMonth();
            int day = dr.getDay();
            if (year == 0) 
                return false;
            StringBuilder tmp = new StringBuilder();
            tmp.append(year);
            if (mon > 0) 
                tmp.append(".").append(String.format("%02d", mon));
            if (day > 0) 
                tmp.append(".").append(String.format("%02d", day));
            this.addSlot(ATTR_DATE, tmp.toString(), false, 0);
            return true;
        }
        if (dt.ref != null && (dt.ref.referent instanceof com.pullenti.ner.date.DateRangeReferent)) {
            this.addSlot(ATTR_DATE, dt.ref.referent, false, 0);
            return true;
        }
        if (dt.value != null) {
            this.addSlot(ATTR_DATE, dt.value, false, 0);
            return true;
        }
        return false;
    }

    private java.util.ArrayList<Integer> _allYears() {
        java.util.ArrayList<Integer> res = new java.util.ArrayList<Integer>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DATE)) {
                String str = s.getValue().toString();
                int i = str.indexOf('.');
                if (i == 4) 
                    str = str.substring(0, 0 + 4);
                com.pullenti.unisharp.Outargwrapper<Integer> wrapi1186 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres1187 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1186);
                i = (wrapi1186.value != null ? wrapi1186.value : 0);
                if (inoutres1187) 
                    res.add(i);
            }
        }
        return res;
    }

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    public DecreeKind getKind() {
        return com.pullenti.ner.decree.internal.DecreeToken.getKind(this.getTyp());
    }


    public boolean isLaw() {
        return com.pullenti.ner.decree.internal.DecreeToken.isLaw(this.getTyp());
    }


    public String getTyp0() {
        String _typ = getTyp();
        if (_typ == null) 
            return null;
        int i = _typ.lastIndexOf(' ');
        if (i < 0) 
            return _typ;
        if (_typ.startsWith("ПАСПОРТ")) 
            return "ПАСПОРТ";
        if (_typ.startsWith("ОСНОВЫ") || _typ.startsWith("ОСНОВИ")) {
            i = _typ.indexOf(' ');
            return _typ.substring(0, 0 + i);
        }
        return _typ.substring(i + 1);
    }


    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }


    public String getCaseNumber() {
        return this.getStringValue(ATTR_CASENUMBER);
    }


    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (attrValue instanceof com.pullenti.ner.decree.internal.PartToken.PartValue) 
            attrValue = ((com.pullenti.ner.decree.internal.PartToken.PartValue)com.pullenti.unisharp.Utils.cast(attrValue, com.pullenti.ner.decree.internal.PartToken.PartValue.class)).value;
        com.pullenti.ner.Slot s = super.addSlot(attrName, attrValue, clearOldValue, statCount);
        if (attrValue instanceof com.pullenti.ner.decree.internal.PartToken.PartValue) 
            s.setTag(((com.pullenti.ner.decree.internal.PartToken.PartValue)com.pullenti.unisharp.Utils.cast(attrValue, com.pullenti.ner.decree.internal.PartToken.PartValue.class)).getSourceValue());
        return s;
    }

    public void addNumber(com.pullenti.ner.decree.internal.DecreeToken dt) {
        if (dt == null) 
            return;
        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
            if (dt.numYear > 0) 
                this.addSlot(ATTR_DATE, ((Integer)dt.numYear).toString(), false, 0);
        }
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(dt.value)) 
            return;
        String value = dt.value;
        if (".,".indexOf(value.charAt(value.length() - 1)) >= 0) 
            value = value.substring(0, 0 + value.length() - 1);
        this.addSlot(ATTR_NUMBER, value, false, 0);
    }

    public void addName(DecreeReferent dr) {
        com.pullenti.ner.Slot s = dr.findSlot(ATTR_NAME, null, true);
        if (s == null) 
            return;
        com.pullenti.ner.Slot ss = this.addSlot(ATTR_NAME, s.getValue(), false, 0);
        if (ss != null && ss.getTag() == null) 
            ss.setTag(s.getTag());
    }

    public void addNameStr(String _name) {
        if (_name == null || _name.length() == 0) 
            return;
        if (_name.charAt(_name.length() - 1) == '.') {
            if (_name.length() > 5 && Character.isLetter(_name.charAt(_name.length() - 2)) && !Character.isLetter(_name.charAt(_name.length() - 3))) {
            }
            else 
                _name = _name.substring(0, 0 + _name.length() - 1);
        }
        _name = _name.trim();
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(_name)) 
            return;
        if (Character.isLowerCase(_name.charAt(0))) 
            _name = (String.valueOf(Character.toUpperCase(_name.charAt(0))) + _name.substring(1));
        String uname = _name.toUpperCase();
        com.pullenti.ner.Slot s = this.addSlot(ATTR_NAME, uname, false, 0);
        if (com.pullenti.unisharp.Utils.stringsNe(uname, _name)) 
            s.setTag(_name);
    }

    private String _getNumberDigits(String num) {
        if (num == null) 
            return "";
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            if (Character.isDigit(num.charAt(i))) {
                if (num.charAt(i) == '0' && tmp.length() == 0) {
                }
                else if (num.charAt(i) == '3' && tmp.length() > 0 && num.charAt(i - 1) == 'Ф') {
                }
                else 
                    tmp.append(num.charAt(i));
            }
        }
        return tmp.toString();
    }

    private java.util.ArrayList<String> _allNumberDigits() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NUMBER)) 
                res.add(this._getNumberDigits((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class)));
        }
        return res;
    }

    private java.util.ArrayList<java.time.LocalDateTime> _allDates() {
        java.util.ArrayList<java.time.LocalDateTime> res = new java.util.ArrayList<java.time.LocalDateTime>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DATE)) {
                java.time.LocalDateTime dt = com.pullenti.ner.decree.internal.DecreeHelper.parseDateTime((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
                if (dt != null) 
                    res.add(dt);
            }
        }
        return res;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        boolean b = this._CanBeEquals(obj, _typ, false);
        return b;
    }

    private boolean _CanBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ, boolean ignoreGeo) {
        DecreeReferent dr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(obj, DecreeReferent.class);
        if (dr == null) 
            return false;
        if (dr.getTyp0() != null && getTyp0() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(dr.getTyp0(), getTyp0())) 
                return false;
        }
        int numEq = 0;
        if (getNumber() != null || dr.getNumber() != null) {
            if (getNumber() != null && dr.getNumber() != null) {
                java.util.ArrayList<String> di1 = this._allNumberDigits();
                java.util.ArrayList<String> di2 = dr._allNumberDigits();
                for (String d1 : di1) {
                    if (di2.contains(d1)) {
                        numEq = 1;
                        break;
                    }
                }
                if (numEq == 0 && !isLaw()) 
                    return false;
                for (com.pullenti.ner.Slot s : getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NUMBER)) {
                        if (dr.findSlot(s.getTypeName(), s.getValue(), true) != null) {
                            numEq = 2;
                            break;
                        }
                    }
                }
                if (numEq == 0) 
                    return false;
            }
        }
        if (getCaseNumber() != null && dr.getCaseNumber() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getCaseNumber(), dr.getCaseNumber())) 
                return false;
        }
        if (this.findSlot(ATTR_GEO, null, true) != null || dr.findSlot(ATTR_GEO, null, true) != null) {
            String geo1 = this.getStringValue(ATTR_GEO);
            String geo2 = dr.getStringValue(ATTR_GEO);
            if (geo1 != null && geo1 != null) {
                if (com.pullenti.unisharp.Utils.stringsNe(geo1, geo2)) 
                    return false;
            }
            else if (geo1 != null && dr.findSlot(ATTR_SOURCE, null, true) != null) 
                return false;
            else if (geo2 != null && this.findSlot(ATTR_SOURCE, null, true) != null) 
                return false;
        }
        boolean srcEq = false;
        boolean srcNotEq = false;
        com.pullenti.ner.Slot src = this.findSlot(ATTR_SOURCE, null, true);
        if (src != null && dr.findSlot(ATTR_SOURCE, null, true) != null) {
            if (dr.findSlot(src.getTypeName(), src.getValue(), true) == null) 
                srcNotEq = true;
            else 
                srcEq = true;
        }
        boolean dateNotEq = false;
        boolean dateIsEqu = false;
        boolean yearsIsEqu = false;
        String date1 = this.getStringValue(ATTR_DATE);
        String date2 = dr.getStringValue(ATTR_DATE);
        if (date1 != null || date2 != null) {
            if (isLaw()) {
                java.util.ArrayList<Integer> ys1 = this._allYears();
                java.util.ArrayList<Integer> ys2 = dr._allYears();
                for (int y1 : ys1) {
                    if (ys2.contains(y1)) {
                        yearsIsEqu = true;
                        break;
                    }
                }
                if (yearsIsEqu) {
                    java.util.ArrayList<java.time.LocalDateTime> dts1 = this._allDates();
                    java.util.ArrayList<java.time.LocalDateTime> dts2 = dr._allDates();
                    for (java.time.LocalDateTime d1 : dts1) {
                        if (dts2.contains(d1)) {
                            dateIsEqu = true;
                            break;
                        }
                    }
                }
                if (!dateIsEqu) {
                    if (com.pullenti.unisharp.Utils.stringsEq(getTyp(), "КОНСТИТУЦИЯ")) 
                        return false;
                    if (getDate() != null && dr.getDate() != null) 
                        dateNotEq = true;
                }
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(date1, date2) || ((this.getDate() != null && dr.getDate() != null && this.getDate().compareTo(dr.getDate()) == 0))) {
                if (numEq > 1) 
                    return true;
                dateIsEqu = true;
            }
            else if (getDate() != null && dr.getDate() != null) {
                if (getDate().getYear() != dr.getDate().getYear()) 
                    return false;
                if (numEq >= 1) {
                    if (srcEq) 
                        return true;
                    if (srcNotEq) 
                        return false;
                }
                else 
                    return false;
            }
            else if (_typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS || getKind() == DecreeKind.PUBLISHER) 
                dateNotEq = true;
        }
        if (this.findSlot(ATTR_NAME, null, true) != null && dr.findSlot(ATTR_NAME, null, true) != null) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                    if (dr.findSlot(s.getTypeName(), s.getValue(), true) != null) 
                        return true;
                    for (com.pullenti.ner.Slot ss : dr.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), s.getTypeName())) {
                            String n0 = s.getValue().toString();
                            String n1 = ss.getValue().toString();
                            if (n0.startsWith(n1) || n1.startsWith(n0)) 
                                return true;
                        }
                    }
                }
            }
            if (dateNotEq) 
                return false;
            if (isLaw() && !dateIsEqu) 
                return false;
            if (numEq > 0) {
                if (srcEq) 
                    return true;
                if (srcNotEq && _typ == com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS) 
                    return false;
                else if ((!srcNotEq && numEq > 1 && getDate() == null) && dr.getDate() == null) 
                    return true;
                return false;
            }
        }
        else if (isLaw() && dateNotEq) 
            return false;
        if (dateNotEq) 
            return false;
        String ty = getTyp();
        if (ty == null) 
            return numEq > 0;
        DecreeKind t = com.pullenti.ner.decree.internal.DecreeToken.getKind(ty);
        if (t == DecreeKind.USTAV || com.pullenti.unisharp.Utils.stringsEq(ty, "КОНСТИТУЦИЯ")) 
            return true;
        if (numEq > 0) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(this.toString(), obj.toString())) 
            return true;
        return false;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        if (!this._CanBeEquals(obj, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT, true)) 
            return false;
        com.pullenti.ner.geo.GeoReferent g1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
        com.pullenti.ner.geo.GeoReferent g2 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(obj.getSlotValue(ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
        if (g1 == null && g2 != null) 
            return true;
        return false;
    }

    public boolean checkCorrection(boolean nounIsDoubtful) {
        String _typ = getTyp0();
        if (_typ == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsEq(_typ, "КОНСТИТУЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "КОНСТИТУЦІЯ")) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(getTyp(), "ЕДИНЫЙ ОТРАСЛЕВОЙ СТАНДАРТ ЗАКУПОК")) 
            return true;
        if ((com.pullenti.unisharp.Utils.stringsEq(_typ, "КОДЕКС") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ОСНОВЫ ЗАКОНОДАТЕЛЬСТВА") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПРОГРАММА")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ОСНОВИ ЗАКОНОДАВСТВА") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПРОГРАМА")) {
            if (this.findSlot(ATTR_NAME, null, true) == null) {
                if (nounIsDoubtful) 
                    return false;
                if (getDate() != null && getNumber() != null) 
                    return true;
                return false;
            }
            if (this.findSlot(ATTR_GEO, null, true) != null) 
                return true;
            return !nounIsDoubtful;
        }
        if (_typ.startsWith("ОСНОВ")) {
            if (this.findSlot(ATTR_GEO, null, true) != null) 
                return true;
            return false;
        }
        if ((_typ.indexOf("ЗАКОН") >= 0)) {
            if (this.findSlot(ATTR_NAME, null, true) == null && getNumber() == null) 
                return false;
            return true;
        }
        if (((((_typ.indexOf("ОПРЕДЕЛЕНИЕ") >= 0) || (_typ.indexOf("РЕШЕНИЕ") >= 0) || (_typ.indexOf("ПОСТАНОВЛЕНИЕ") >= 0)) || (_typ.indexOf("ПРИГОВОР") >= 0) || (_typ.indexOf("ВИЗНАЧЕННЯ") >= 0)) || (_typ.indexOf("РІШЕННЯ") >= 0) || (_typ.indexOf("ПОСТАНОВА") >= 0)) || (_typ.indexOf("ВИРОК") >= 0)) {
            if (getNumber() != null) {
                if (this.findSlot(ATTR_DATE, null, true) != null || this.findSlot(ATTR_SOURCE, null, true) != null || this.findSlot(ATTR_NAME, null, true) != null) 
                    return true;
            }
            else if (this.findSlot(ATTR_DATE, null, true) != null && this.findSlot(ATTR_SOURCE, null, true) != null) 
                return true;
            return false;
        }
        DecreeKind ty = com.pullenti.ner.decree.internal.DecreeToken.getKind(_typ);
        if (ty == DecreeKind.USTAV) {
            if (this.findSlot(ATTR_SOURCE, null, true) != null) 
                return true;
            if (this.findSlot(ATTR_GEO, null, true) != null && getName() != null) 
                return true;
        }
        if (ty == DecreeKind.KONVENTION) {
            if (this.findSlot(ATTR_NAME, null, true) != null) {
                if (com.pullenti.unisharp.Utils.stringsNe(_typ, "ДОГОВОР") && com.pullenti.unisharp.Utils.stringsNe(_typ, "ДОГОВІР")) 
                    return true;
            }
        }
        if (ty == DecreeKind.STANDARD) {
            if (getNumber() != null && getNumber().length() > 4) 
                return true;
        }
        if (getNumber() == null) {
            if (this.findSlot(ATTR_NAME, null, true) == null || this.findSlot(ATTR_SOURCE, null, true) == null || this.findSlot(ATTR_DATE, null, true) == null) {
                if (ty == DecreeKind.CONTRACT && this.findSlot(ATTR_SOURCE, null, true) != null && this.findSlot(ATTR_DATE, null, true) != null) {
                }
                else if (this.findSlot(ATTR_NAME, "ПРАВИЛА ДОРОЖНОГО ДВИЖЕНИЯ", true) != null) {
                }
                else if (this.findSlot(ATTR_NAME, "ПРАВИЛА ДОРОЖНЬОГО РУХУ", true) != null) {
                }
                else if (getKind() == DecreeKind.PROJECT && this.findSlot(ATTR_NAME, null, true) != null) {
                }
                else 
                    return false;
            }
        }
        else {
            if ((com.pullenti.unisharp.Utils.stringsEq(_typ, "ПАСПОРТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ГОСТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПБУ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ФОРМА")) 
                return true;
            if (this.findSlot(ATTR_SOURCE, null, true) == null && this.findSlot(ATTR_DATE, null, true) == null && this.findSlot(ATTR_NAME, null, true) == null) 
                return false;
        }
        return true;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        for (int i = 0; i < (getSlots().size() - 1); i++) {
            for (int j = i + 1; j < getSlots().size(); j++) {
                if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), this.getSlots().get(j).getTypeName()) && this.getSlots().get(i).getValue() == this.getSlots().get(j).getValue()) {
                    getSlots().remove(j);
                    j--;
                }
            }
        }
        java.util.ArrayList<String> nums = this.getStringValues(ATTR_NUMBER);
        if (nums.size() > 1) {
            java.util.Collections.sort(nums);
            for (int i = 0; i < (nums.size() - 1); i++) {
                if (nums.get(i + 1).startsWith(nums.get(i)) && nums.get(i + 1).length() > nums.get(i).length() && !Character.isDigit(nums.get(i + 1).charAt(nums.get(i).length()))) {
                    com.pullenti.ner.Slot s = this.findSlot(ATTR_NUMBER, nums.get(i), true);
                    if (s != null) 
                        getSlots().remove(s);
                    nums.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String s = a.getValue().toString();
                if (!vars.contains(s)) 
                    vars.add(s);
            }
        }
        if (getNumber() != null) {
            for (String digs : this._allNumberDigits()) {
                if (!vars.contains(digs)) 
                    vars.add(digs);
            }
        }
        for (String v : vars) {
            oi.termins.add(new com.pullenti.ner.core.Termin(v, null, false));
        }
        return oi;
    }

    public static DecreeReferent _new1170(String _arg1) {
        DecreeReferent res = new DecreeReferent();
        res.setTyp(_arg1);
        return res;
    }
}
