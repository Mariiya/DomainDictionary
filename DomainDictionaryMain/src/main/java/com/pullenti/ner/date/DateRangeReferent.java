/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date;

/**
 * Сущность, представляющая диапазон дат
 * 
 */
public class DateRangeReferent extends com.pullenti.ner.Referent {

    public DateRangeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.date.internal.MetaDateRange.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("DATERANGE")
     */
    public static final String OBJ_TYPENAME = "DATERANGE";

    /**
     * Имя атрибута - дата начала диапазона (DateReferent)
     */
    public static final String ATTR_FROM = "FROM";

    /**
     * Имя атрибута - дата окончания диапазона (DateReferent)
     */
    public static final String ATTR_TO = "TO";

    public DateReferent getDateFrom() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_FROM), DateReferent.class);
    }

    public DateReferent setDateFrom(DateReferent value) {
        this.addSlot(ATTR_FROM, value, true, 0);
        return value;
    }


    public DateReferent getDateTo() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_TO), DateReferent.class);
    }

    public DateReferent setDateTo(DateReferent value) {
        this.addSlot(ATTR_TO, value, true, 0);
        return value;
    }


    public boolean isRelative() {
        if (getDateFrom() != null && getDateFrom().isRelative()) 
            return true;
        if (getDateTo() != null && getDateTo().isRelative()) 
            return true;
        return false;
    }


    /**
     * Вычислить диапазон дат (если не диапазон, то from = to)
     * @param now текущая дата-время
     * @param from результирующее начало диапазона
     * @param to результирующий конец диапазона
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - используется 
     * при неоднозначных случаях. 
     * Например, 7 сентября, а сейчас лето, то какой это год? При +1 - этот, при -1 - предыдущий
     * @return признак корректности
     */
    public boolean calculateDateRange(java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        boolean inoutres841 = com.pullenti.ner.date.internal.DateRelHelper.calculateDateRange2(this, now, from, to, tense);
        return inoutres841;
    }

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (isRelative() && !shortVariant) {
            StringBuilder res = new StringBuilder();
            res.append(this.toStringEx(true, lang, lev));
            com.pullenti.ner.date.internal.DateRelHelper.appendToString2(this, res);
            return res.toString();
        }
        String fr = (getDateFrom() == null ? null : getDateFrom()._ToString(shortVariant, lang, lev, 1));
        String to = (getDateTo() == null ? null : getDateTo()._ToString(shortVariant, lang, lev, 2));
        if (fr != null && to != null) 
            return (fr + " " + (this.getDateTo().getCentury() > 0 && this.getDateTo().getYear() == 0 ? to : to.toLowerCase()));
        if (fr != null) 
            return fr.toString();
        if (to != null) 
            return to;
        return (String.valueOf((lang.isUa() ? 'з' : 'с')) + " ? по ?");
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        DateRangeReferent dr = (DateRangeReferent)com.pullenti.unisharp.Utils.cast(obj, DateRangeReferent.class);
        if (dr == null) 
            return false;
        if (getDateFrom() != null) {
            if (!getDateFrom().canBeEquals(dr.getDateFrom(), typ)) 
                return false;
        }
        else if (dr.getDateFrom() != null) 
            return false;
        if (getDateTo() != null) {
            if (!getDateTo().canBeEquals(dr.getDateTo(), typ)) 
                return false;
        }
        else if (dr.getDateTo() != null) 
            return false;
        return true;
    }

    public int getQuarterNumber() {
        if (getDateFrom() == null || getDateTo() == null || getDateFrom().getYear() != getDateTo().getYear()) 
            return 0;
        int m1 = getDateFrom().getMonth();
        int m2 = getDateTo().getMonth();
        if (m1 == 1 && m2 == 3) 
            return 1;
        if (m1 == 4 && m2 == 6) 
            return 2;
        if (m1 == 7 && m2 == 9) 
            return 3;
        if (m1 == 10 && m2 == 12) 
            return 4;
        return 0;
    }


    public int getHalfyearNumber() {
        if (getDateFrom() == null || getDateTo() == null || getDateFrom().getYear() != getDateTo().getYear()) 
            return 0;
        int m1 = getDateFrom().getMonth();
        int m2 = getDateTo().getMonth();
        if (m1 == 1 && m2 == 6) 
            return 1;
        if (m1 == 7 && m2 == 12) 
            return 2;
        return 0;
    }


    public static DateRangeReferent _new766(DateReferent _arg1, DateReferent _arg2) {
        DateRangeReferent res = new DateRangeReferent();
        res.setDateFrom(_arg1);
        res.setDateTo(_arg2);
        return res;
    }

    public static DateRangeReferent _new772(DateReferent _arg1) {
        DateRangeReferent res = new DateRangeReferent();
        res.setDateTo(_arg1);
        return res;
    }

    public static DateRangeReferent _new773(DateReferent _arg1) {
        DateRangeReferent res = new DateRangeReferent();
        res.setDateFrom(_arg1);
        return res;
    }
}
