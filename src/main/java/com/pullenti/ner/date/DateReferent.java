/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date;

/**
 * Сущность, представляющая дату
 * 
 */
public class DateReferent extends com.pullenti.ner.Referent {

    public DateReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.date.internal.MetaDate.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("DATE")
     */
    public static final String OBJ_TYPENAME = "DATE";

    /**
     * Имя атрибута - век
     */
    public static final String ATTR_CENTURY = "CENTURY";

    /**
     * Имя атрибута - десятилетие
     */
    public static final String ATTR_DECADE = "DECADE";

    /**
     * Имя атрибута - год
     */
    public static final String ATTR_YEAR = "YEAR";

    /**
     * Имя атрибута - полгода
     */
    public static final String ATTR_HALFYEAR = "HALFYEAR";

    /**
     * Имя атрибута - квартал
     */
    public static final String ATTR_QUARTAL = "QUARTAL";

    /**
     * Имя атрибута - сезон (зима, весна ...)
     */
    public static final String ATTR_SEASON = "SEASON";

    /**
     * Имя атрибута - месяц
     */
    public static final String ATTR_MONTH = "MONTH";

    /**
     * Имя атрибута - неделя
     */
    public static final String ATTR_WEEK = "WEEK";

    /**
     * Имя атрибута - день
     */
    public static final String ATTR_DAY = "DAY";

    /**
     * Имя атрибута - день недели
     */
    public static final String ATTR_DAYOFWEEK = "DAYOFWEEK";

    /**
     * Имя атрибута - час
     */
    public static final String ATTR_HOUR = "HOUR";

    /**
     * Имя атрибута - минута
     */
    public static final String ATTR_MINUTE = "MINUTE";

    /**
     * Имя атрибута - секунда
     */
    public static final String ATTR_SECOND = "SECOND";

    /**
     * Имя атрибута - ссылка на вышележащуу сущность-дату
     */
    public static final String ATTR_HIGHER = "HIGHER";

    /**
     * Имя атрибута - дополнительный указатель
     */
    public static final String ATTR_POINTER = "POINTER";

    /**
     * Имя атрибута - ссылка на дату дового стиля (григорианскую)
     */
    public static final String ATTR_NEWSTYLE = "NEWSTYLE";

    /**
     * Имя атрибута - признак относительности
     */
    public static final String ATTR_ISRELATIVE = "ISRELATIVE";

    public java.time.LocalDateTime getDt() {
        if (getYear() > 0 && getMonth() > 0 && getDay() > 0) {
            if (getMonth() > 12) 
                return null;
            if (getDay() > com.pullenti.unisharp.Utils.daysInMonth(this.getYear(), this.getMonth())) 
                return null;
            int h = getHour();
            int m = getMinute();
            int s = getSecond();
            if (h < 0) 
                h = 0;
            if (m < 0) 
                m = 0;
            if (s < 0) 
                s = 0;
            try {
                return java.time.LocalDateTime.of(getYear(), getMonth(), getDay(), h, m, (s >= 0 && (s < 60) ? s : 0));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    public java.time.LocalDateTime setDt(java.time.LocalDateTime value) {
        return value;
    }


    public boolean isRelative() {
        if (com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_ISRELATIVE), "true")) 
            return true;
        if (getPointer() == DatePointerType.TODAY) 
            return true;
        if (getHigher() == null) 
            return false;
        return getHigher().isRelative();
    }

    public boolean setRelative(boolean value) {
        this.addSlot(ATTR_ISRELATIVE, (value ? "true" : null), true, 0);
        return value;
    }


    /**
     * Вычислить дату-время (одну)
     * @param now текущая дата (для относительных дат)
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - испрользуется 
     * при неоднозначных случаях
     * @return дата-время или null
     */
    public java.time.LocalDateTime calculateDate(java.time.LocalDateTime now, int tense) {
        return com.pullenti.ner.date.internal.DateRelHelper.calculateDate(this, now, tense);
    }

    /**
     * Вычислить диапазон дат (если не диапазон, то from = to)
     * @param now текущая дата-время
     * @param from результирующее начало диапазона
     * @param to результирующий конец диапазона
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - используется 
     * при неоднозначных случаях 
     * Например, 7 сентября, а сейчас лето, то какой это год? При +1 - этот, при -1 - предыдущий
     * @return признак корректности
     */
    public boolean calculateDateRange(java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        boolean inoutres842 = com.pullenti.ner.date.internal.DateRelHelper.calculateDateRange(this, now, from, to, tense);
        return inoutres842;
    }

    public int getCentury() {
        if (getHigher() != null) 
            return getHigher().getCentury();
        int cent = this.getIntValue(ATTR_CENTURY, 0);
        if (cent != 0) 
            return cent;
        int _year = getYear();
        if (_year > 0) {
            cent = _year / 100;
            cent++;
            return cent;
        }
        else if (_year < 0) {
            cent = _year / 100;
            cent--;
            return cent;
        }
        return 0;
    }

    public int setCentury(int value) {
        this.addSlot(ATTR_CENTURY, value, true, 0);
        return value;
    }


    public int getDecade() {
        if (getHigher() != null) 
            return getHigher().getDecade();
        else 
            return this.getIntValue(ATTR_DECADE, 0);
    }

    public int setDecade(int value) {
        this.addSlot(ATTR_DECADE, value, true, 0);
        return value;
    }


    public int getYear() {
        if (getHigher() != null) 
            return getHigher().getYear();
        else 
            return this.getIntValue(ATTR_YEAR, 0);
    }

    public int setYear(int value) {
        this.addSlot(ATTR_YEAR, value, true, 0);
        return value;
    }


    public int getHalfyear() {
        if (getHigher() != null) 
            return getHigher().getHalfyear();
        else 
            return this.getIntValue(ATTR_HALFYEAR, 0);
    }

    public int setHalfyear(int value) {
        this.addSlot(ATTR_HALFYEAR, value, true, 0);
        return value;
    }


    public int getQuartal() {
        if (this.findSlot(ATTR_QUARTAL, null, true) == null && getHigher() != null) 
            return getHigher().getQuartal();
        else 
            return this.getIntValue(ATTR_QUARTAL, 0);
    }

    public int setQuartal(int value) {
        this.addSlot(ATTR_QUARTAL, value, true, 0);
        return value;
    }


    public int getSeason() {
        if (this.findSlot(ATTR_SEASON, null, true) == null && getHigher() != null) 
            return getHigher().getSeason();
        else 
            return this.getIntValue(ATTR_SEASON, 0);
    }

    public int setSeason(int value) {
        this.addSlot(ATTR_SEASON, value, true, 0);
        return value;
    }


    public int getMonth() {
        if (this.findSlot(ATTR_MONTH, null, true) == null && getHigher() != null) 
            return getHigher().getMonth();
        else 
            return this.getIntValue(ATTR_MONTH, 0);
    }

    public int setMonth(int value) {
        this.addSlot(ATTR_MONTH, value, true, 0);
        return value;
    }


    public int getWeek() {
        if (this.findSlot(ATTR_WEEK, null, true) == null && getHigher() != null) 
            return getHigher().getWeek();
        else 
            return this.getIntValue(ATTR_WEEK, 0);
    }

    public int setWeek(int value) {
        this.addSlot(ATTR_WEEK, value, true, 0);
        return value;
    }


    public int getDay() {
        if (this.findSlot(ATTR_DAY, null, true) == null && getHigher() != null) 
            return getHigher().getDay();
        else 
            return this.getIntValue(ATTR_DAY, 0);
    }

    public int setDay(int value) {
        this.addSlot(ATTR_DAY, value, true, 0);
        return value;
    }


    public int getDayOfWeek() {
        if (this.findSlot(ATTR_DAYOFWEEK, null, true) == null && getHigher() != null) 
            return getHigher().getDayOfWeek();
        else 
            return this.getIntValue(ATTR_DAYOFWEEK, 0);
    }

    public int setDayOfWeek(int value) {
        this.addSlot(ATTR_DAYOFWEEK, value, true, 0);
        return value;
    }


    public int getHour() {
        return this.getIntValue(ATTR_HOUR, -1);
    }

    public int setHour(int value) {
        this.addSlot(ATTR_HOUR, value, true, 0);
        return value;
    }


    public int getMinute() {
        return this.getIntValue(ATTR_MINUTE, -1);
    }

    public int setMinute(int value) {
        this.addSlot(ATTR_MINUTE, value, true, 0);
        return value;
    }


    public int getSecond() {
        return this.getIntValue(ATTR_SECOND, -1);
    }

    public int setSecond(int value) {
        this.addSlot(ATTR_SECOND, value, true, 0);
        return value;
    }


    public DateReferent getHigher() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_HIGHER), DateReferent.class);
    }

    public DateReferent setHigher(DateReferent value) {
        this.addSlot(ATTR_HIGHER, value, true, 0);
        return value;
    }


    public DatePointerType getPointer() {
        String s = this.getStringValue(ATTR_POINTER);
        if (s == null) 
            return DatePointerType.NO;
        try {
            Object res = DatePointerType.of(s);
            if (res instanceof DatePointerType) 
                return (DatePointerType)res;
        } catch (Exception ex843) {
        }
        return DatePointerType.NO;
    }

    public DatePointerType setPointer(DatePointerType value) {
        if (value != DatePointerType.NO) 
            this.addSlot(ATTR_POINTER, value.toString(), true, 0);
        return value;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getHigher();
    }


    public static boolean canBeHigher(DateReferent hi, DateReferent lo) {
        if (lo == null || hi == null) 
            return false;
        if (lo.getHigher() == hi) 
            return true;
        if (lo.getHigher() != null && lo.getHigher().canBeEquals(hi, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
            return true;
        if (lo.getHigher() != null) 
            return false;
        if (lo.getHour() >= 0) {
            if (hi.getHour() >= 0) 
                return false;
            if (lo.getDay() > 0) 
                return false;
            return true;
        }
        if (hi.getYear() > 0 && lo.getYear() <= 0) {
            if (hi.getMonth() > 0) 
                return false;
            return true;
        }
        return false;
    }

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return this._ToString(shortVariant, lang, lev, 0);
    }

    public String _ToString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev, int fromRange) {
        StringBuilder res = new StringBuilder();
        DatePointerType p = getPointer();
        if (lang == null) 
            lang = com.pullenti.morph.MorphLang.RU;
        if (isRelative()) {
            if (getPointer() == DatePointerType.TODAY) {
                res.append("сейчас");
                if (!shortVariant) 
                    com.pullenti.ner.date.internal.DateRelHelper.appendToString(this, res);
                return res.toString();
            }
            String word = null;
            int val = 0;
            boolean back = false;
            boolean isLocalRel = com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_ISRELATIVE), "true");
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CENTURY)) {
                    word = "век";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval844 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval844);
                    val = (wrapval844.value != null ? wrapval844.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DECADE)) {
                    word = "декада";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval845 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval845);
                    val = (wrapval845.value != null ? wrapval845.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_HALFYEAR)) {
                    word = "полугодие";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval846 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval846);
                    val = (wrapval846.value != null ? wrapval846.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_SEASON)) {
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval847 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval847);
                    val = (wrapval847.value != null ? wrapval847.value : 0);
                    if (val == 1) 
                        res.append((lang.isEn() ? "winter" : "зима"));
                    else if (val == 2) 
                        res.append((lang.isEn() ? "spring" : "весна"));
                    else if (val == 3) 
                        res.append((lang.isEn() ? "summer" : (lang.isUa() ? "літо" : "лето")));
                    else if (val == 4) 
                        res.append((lang.isEn() ? "autumn" : (lang.isUa() ? "осені" : "осень")));
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_YEAR)) {
                    word = "год";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval848 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval848);
                    val = (wrapval848.value != null ? wrapval848.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_MONTH)) {
                    word = "месяц";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval849 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval849);
                    val = (wrapval849.value != null ? wrapval849.value : 0);
                    if (!isLocalRel && val >= 1 && val <= 12) 
                        res.append(m_Month0[val - 1]);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DAY)) {
                    word = "день";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval850 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval850);
                    val = (wrapval850.value != null ? wrapval850.value : 0);
                    if ((!isLocalRel && getMonth() > 0 && getMonth() <= 12) && getHigher() != null && com.pullenti.unisharp.Utils.stringsNe(getHigher().getStringValue(ATTR_ISRELATIVE), "true")) 
                        res.append(val).append(" ").append(m_Month[this.getMonth() - 1]);
                    else if (!isLocalRel) 
                        res.append(val).append(" число");
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_QUARTAL)) {
                    word = "квартал";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval851 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval851);
                    val = (wrapval851.value != null ? wrapval851.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_WEEK)) {
                    word = "неделя";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval852 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval852);
                    val = (wrapval852.value != null ? wrapval852.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_HOUR)) {
                    word = "час";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval853 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval853);
                    val = (wrapval853.value != null ? wrapval853.value : 0);
                    if (!isLocalRel) 
                        res.append(String.format("%02d", val)).append(":").append(String.format("%02d", this.getMinute()));
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_MINUTE)) {
                    word = "минута";
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval854 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval854);
                    val = (wrapval854.value != null ? wrapval854.value : 0);
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DAYOFWEEK)) {
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapval855 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapval855);
                    val = (wrapval855.value != null ? wrapval855.value : 0);
                    if (!isLocalRel) 
                        res.append((val >= 1 && val <= 7 ? m_WeekDayEx[val - 1] : "?"));
                    else {
                        if (val < 0) {
                            val = -val;
                            back = true;
                        }
                        if (val > 0 && val <= 7) {
                            res.append((val == 7 ? (back ? "прошлое" : "будущее") : ((val == 3 || val == 6) ? (back ? "прошлая" : "будущая") : (back ? "прошлый" : "будущий")))).append(" ").append(m_WeekDayEx[val - 1]);
                            break;
                        }
                    }
                }
            }
            if (word != null && isLocalRel) {
                if (val == 0) 
                    res.append((com.pullenti.unisharp.Utils.stringsEq(word, "неделя") || com.pullenti.unisharp.Utils.stringsEq(word, "минута") ? "текущая" : "текущий")).append(" ").append(word);
                else if (val > 0 && !back) 
                    res.append(val).append(" ").append(com.pullenti.ner.core.MiscHelper.getTextMorphVarByCaseAndNumberEx(word, null, com.pullenti.morph.MorphNumber.UNDEFINED, ((Integer)val).toString())).append(" вперёд");
                else {
                    val = -val;
                    res.append(val).append(" ").append(com.pullenti.ner.core.MiscHelper.getTextMorphVarByCaseAndNumberEx(word, null, com.pullenti.morph.MorphNumber.UNDEFINED, ((Integer)val).toString())).append(" назад");
                }
            }
            else if (!isLocalRel && res.length() == 0) 
                res.append(val).append(" ").append(com.pullenti.ner.core.MiscHelper.getTextMorphVarByCaseAndNumberEx(word, null, com.pullenti.morph.MorphNumber.UNDEFINED, ((Integer)val).toString()));
            if (!shortVariant) 
                com.pullenti.ner.date.internal.DateRelHelper.appendToString(this, res);
            if (fromRange == 1) 
                res.insert(0, ((lang.isUa() ? "з" : (lang.isEn() ? "from" : "с")) + " "));
            else if (fromRange == 2) 
                res.insert(0, (lang.isEn() ? "to " : "по "));
            return res.toString();
        }
        if (fromRange == 1) 
            res.append((lang.isUa() ? "з" : (lang.isEn() ? "from" : "с"))).append(" ");
        else if (fromRange == 2) 
            res.append((lang.isEn() ? "to " : "по "));
        if (p != DatePointerType.NO) {
            String val = com.pullenti.ner.date.internal.MetaDate.POINTER.convertInnerValueToOuterValue(p.toString(), lang);
            if (fromRange == 0 || lang.isEn()) {
            }
            else if (fromRange == 1) {
                if (p == DatePointerType.BEGIN) 
                    val = (lang.isUa() ? "початку" : "начала");
                else if (p == DatePointerType.CENTER) 
                    val = (lang.isUa() ? "середини" : "середины");
                else if (p == DatePointerType.END) 
                    val = (lang.isUa() ? "кінця" : "конца");
                else if (p == DatePointerType.TODAY) 
                    val = (lang.isUa() ? "цього часу" : "настоящего времени");
            }
            else if (fromRange == 2) {
                if (p == DatePointerType.BEGIN) 
                    val = (lang.isUa() ? "початок" : "начало");
                else if (p == DatePointerType.CENTER) 
                    val = (lang.isUa() ? "середину" : "середину");
                else if (p == DatePointerType.END) 
                    val = (lang.isUa() ? "кінець" : "конец");
                else if (p == DatePointerType.TODAY) 
                    val = (lang.isUa() ? "теперішній час" : "настоящее время");
            }
            res.append(val).append(" ");
        }
        if (getDayOfWeek() > 0) {
            if (lang.isEn()) 
                res.append(m_WeekDayEn[this.getDayOfWeek() - 1]).append(", ");
            else 
                res.append(m_WeekDay[this.getDayOfWeek() - 1]).append(", ");
        }
        int y = getYear();
        int m = getMonth();
        int d = getDay();
        int cent = getCentury();
        int ten = getDecade();
        if (y == 0 && cent != 0) {
            boolean isBc = cent < 0;
            if (cent < 0) 
                cent = -cent;
            res.append(com.pullenti.ner.core.NumberHelper.getNumberRoman(cent));
            if (lang.isUa()) 
                res.append(" century");
            else if (m > 0 || p != DatePointerType.NO || fromRange == 1) 
                res.append((lang.isUa() ? " віка" : " века"));
            else 
                res.append((lang.isUa() ? " вік" : " век"));
            if (isBc) 
                res.append((lang.isUa() ? " до н.е." : " до н.э."));
            return res.toString();
        }
        if (y == 0 && ten != 0) 
            res.append(ten).append(" ").append((lang.isEn() ? "decade" : "декада"));
        if (d > 0) 
            res.append(d);
        if (m > 0 && m <= 12) {
            if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') 
                res.append(' ');
            if (lang.isUa()) 
                res.append((d > 0 || p != DatePointerType.NO || fromRange != 0 ? m_MonthUA[m - 1] : m_Month0UA[m - 1]));
            else if (lang.isEn()) 
                res.append(m_MonthEN[m - 1]);
            else 
                res.append((d > 0 || p != DatePointerType.NO || fromRange != 0 ? m_Month[m - 1] : m_Month0[m - 1]));
        }
        if (y != 0) {
            boolean isBc = y < 0;
            if (y < 0) 
                y = -y;
            if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') 
                res.append(' ');
            if (lang != null && lang.isEn()) 
                res.append(y);
            else if (shortVariant) 
                res.append(y).append((lang.isUa() ? "р" : "г"));
            else if (m > 0 || p != DatePointerType.NO || fromRange == 1) 
                res.append(y).append(" ").append((lang.isUa() ? "року" : "года"));
            else 
                res.append(y).append(" ").append((lang.isUa() ? "рік" : "год"));
            if (isBc) 
                res.append((lang.isUa() ? " до н.е." : (lang.isEn() ? "BC" : " до н.э.")));
        }
        int h = getHour();
        int mi = getMinute();
        int se = getSecond();
        if (h >= 0 && mi >= 0) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(String.format("%02d", h)).append(":").append(String.format("%02d", mi));
            if (se >= 0) 
                res.append(":").append(String.format("%02d", se));
        }
        if (res.length() == 0) {
            if (getQuartal() != 0) 
                res.append(this.getQuartal()).append("-й квартал");
        }
        if (res.length() == 0) 
            return "?";
        while (res.charAt(res.length() - 1) == ' ' || res.charAt(res.length() - 1) == ',') {
            res.setLength(res.length() - 1);
        }
        if (!shortVariant && isRelative()) 
            com.pullenti.ner.date.internal.DateRelHelper.appendToString(this, res);
        if (!shortVariant && this.findSlot(ATTR_NEWSTYLE, null, true) != null) {
            DateReferent _dt = (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_NEWSTYLE), DateReferent.class);
            if (_dt != null) 
                res.append(" (новый стиль: ").append(_dt.toStringEx(shortVariant, lang, lev + 1)).append(")");
        }
        return res.toString().trim();
    }

    private static String[] m_Month;

    private static String[] m_Month0;

    private static String[] m_MonthEN;

    private static String[] m_MonthUA;

    private static String[] m_Month0UA;

    private static String[] m_WeekDay;

    private static String[] m_WeekDayEx;

    private static String[] m_WeekDayEn;

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        DateReferent sd = (DateReferent)com.pullenti.unisharp.Utils.cast(obj, DateReferent.class);
        if (sd == null) 
            return false;
        if (sd.isRelative() != isRelative()) 
            return false;
        if (sd.getCentury() != getCentury()) 
            return false;
        if (sd.getDecade() != getDecade()) 
            return false;
        if (sd.getSeason() != getSeason()) 
            return false;
        if (sd.getYear() != getYear()) 
            return false;
        if (sd.getHalfyear() != getHalfyear()) 
            return false;
        if (sd.getMonth() != getMonth()) 
            return false;
        if (sd.getDay() != getDay()) 
            return false;
        if (sd.getHour() != getHour()) 
            return false;
        if (sd.getMinute() != getMinute()) 
            return false;
        if (sd.getSecond() != getSecond()) 
            return false;
        if (sd.getPointer() != getPointer()) 
            return false;
        if (sd.getDayOfWeek() > 0 && getDayOfWeek() > 0) {
            if (sd.getDayOfWeek() != getDayOfWeek()) 
                return false;
        }
        return true;
    }

    public static int compare(DateReferent d1, DateReferent d2) {
        if (d1.getCentury() < d2.getCentury()) 
            return -1;
        if (d1.getCentury() > d2.getCentury()) 
            return 1;
        if (d1.getDecade() < d2.getDecade()) 
            return -1;
        if (d1.getDecade() > d2.getDecade()) 
            return 1;
        if (d1.getYear() < d2.getYear()) 
            return -1;
        if (d1.getYear() > d2.getYear()) 
            return 1;
        if (d1.getQuartal() < d2.getQuartal()) 
            return -1;
        if (d1.getQuartal() > d2.getQuartal()) 
            return 1;
        if (d1.getMonth() < d2.getMonth()) 
            return -1;
        if (d1.getMonth() > d2.getMonth()) 
            return 1;
        if (d1.getDay() < d2.getDay()) 
            return -1;
        if (d1.getDay() > d2.getDay()) 
            return 1;
        if (d1.getHour() < d2.getHour()) 
            return -1;
        if (d1.getHour() > d2.getHour()) 
            return 1;
        if (d1.getMinute() < d2.getMinute()) 
            return -1;
        if (d1.getMinute() > d2.getMinute()) 
            return 1;
        if (d1.getSecond() > d2.getSecond()) 
            return -1;
        if (d1.getSecond() < d2.getSecond()) 
            return 1;
        if (d1.getPointer() != DatePointerType.NO && d2.getPointer() != DatePointerType.NO) {
            DatePointerType p1 = d1.getPointer();
            DatePointerType p2 = d2.getPointer();
            if (p1 == DatePointerType.BEGIN || p1 == DatePointerType.CENTER || p1 == DatePointerType.END) {
                if (p2 == DatePointerType.BEGIN || p2 == DatePointerType.CENTER || p2 == DatePointerType.END) {
                    if ((p1.value()) < (p2.value())) 
                        return -1;
                    if ((p1.value()) > (p2.value())) 
                        return 1;
                }
            }
            if ((p1 == DatePointerType.WINTER || p1 == DatePointerType.SPRING || p1 == DatePointerType.SUMMER) || p1 == DatePointerType.AUTUMN) {
                if ((p2 == DatePointerType.WINTER || p2 == DatePointerType.SPRING || p2 == DatePointerType.SUMMER) || p2 == DatePointerType.AUTUMN) {
                    if ((p1.value()) < (p2.value())) 
                        return -1;
                    if ((p1.value()) > (p2.value())) 
                        return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Проверка, что дата или диапазон определены с точностью до одного месяца
     * @param obj 
     * @return 
     */
    public static boolean isMonthDefined(com.pullenti.ner.Referent obj) {
        DateReferent sd = (DateReferent)com.pullenti.unisharp.Utils.cast(obj, DateReferent.class);
        if (sd != null) 
            return (sd.getYear() > 0 && sd.getMonth() > 0);
        DateRangeReferent sdr = (DateRangeReferent)com.pullenti.unisharp.Utils.cast(obj, DateRangeReferent.class);
        if (sdr != null) {
            if (sdr.getDateFrom() == null || sdr.getDateTo() == null) 
                return false;
            if (sdr.getDateFrom().getYear() == 0 || sdr.getDateTo().getYear() != sdr.getDateFrom().getYear()) 
                return false;
            if (sdr.getDateFrom().getMonth() == 0 || sdr.getDateTo().getMonth() != sdr.getDateFrom().getMonth()) 
                return false;
            return true;
        }
        return false;
    }

    public static DateReferent _new767(DateReferent _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        res.setDay(_arg2);
        return res;
    }

    public static DateReferent _new768(int _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        res.setDay(_arg2);
        return res;
    }

    public static DateReferent _new769(int _arg1) {
        DateReferent res = new DateReferent();
        res.setYear(_arg1);
        return res;
    }

    public static DateReferent _new774(int _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHour(_arg1);
        res.setMinute(_arg2);
        return res;
    }

    public static DateReferent _new775(DatePointerType _arg1) {
        DateReferent res = new DateReferent();
        res.setPointer(_arg1);
        return res;
    }

    public static DateReferent _new787(int _arg1, DateReferent _arg2) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        res.setHigher(_arg2);
        return res;
    }

    public static DateReferent _new792(int _arg1, DateReferent _arg2) {
        DateReferent res = new DateReferent();
        res.setDay(_arg1);
        res.setHigher(_arg2);
        return res;
    }

    public static DateReferent _new814(int _arg1) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        return res;
    }

    public static DateReferent _new815(int _arg1, boolean _arg2) {
        DateReferent res = new DateReferent();
        res.setCentury(_arg1);
        res.setRelative(_arg2);
        return res;
    }

    public static DateReferent _new816(int _arg1, boolean _arg2) {
        DateReferent res = new DateReferent();
        res.setDecade(_arg1);
        res.setRelative(_arg2);
        return res;
    }

    public static DateReferent _new823(int _arg1) {
        DateReferent res = new DateReferent();
        res.setDay(_arg1);
        return res;
    }

    public static DateReferent _new825(DateReferent _arg1) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        return res;
    }

    public static DateReferent _new826(DateReferent _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        res.setMonth(_arg2);
        return res;
    }

    public static DateReferent _new835(int _arg1) {
        DateReferent res = new DateReferent();
        res.setDayOfWeek(_arg1);
        return res;
    }

    
    static {
        m_Month = new String[] {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        m_Month0 = new String[] {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        m_MonthEN = new String[] {"jan", "fab", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        m_MonthUA = new String[] {"січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"};
        m_Month0UA = new String[] {"січень", "лютий", "березень", "квітень", "травень", "червень", "липень", "серпень", "вересень", "жовтень", "листопад", "грудень"};
        m_WeekDay = new String[] {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        m_WeekDayEx = new String[] {"понедельник", "вторник", "среда", "четверг", "пятница", "суббота", "воскресенье"};
        m_WeekDayEn = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }
}
