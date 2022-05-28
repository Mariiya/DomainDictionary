/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.money;

/**
 * Сущность - денежная сумма
 * 
 */
public class MoneyReferent extends com.pullenti.ner.Referent {

    public MoneyReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.money.internal.MoneyMeta.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("MONEY")
     */
    public static final String OBJ_TYPENAME = "MONEY";

    /**
     * Имя атрибута - валюта (3-х значный код ISO 4217)
     */
    public static final String ATTR_CURRENCY = "CURRENCY";

    /**
     * Имя атрибута - значение (целая часть)
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - альтернативное значение (когда в скобках ошибочно указано другле число)
     */
    public static final String ATTR_ALTVALUE = "ALTVALUE";

    /**
     * Имя атрибута - дробная часть ("копейки")
     */
    public static final String ATTR_REST = "REST";

    /**
     * Имя атрибута - альтернативная дробная часть (когда в скобках указано другое число)
     */
    public static final String ATTR_ALTREST = "ALTREST";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String v = this.getStringValue(ATTR_VALUE);
        int r = getRest();
        if (v != null || r > 0) {
            res.append((v != null ? v : "0"));
            int cou = 0;
            for (int i = res.length() - 1; i > 0; i--) {
                if ((++cou) == 3) {
                    res.insert(i, '.');
                    cou = 0;
                }
            }
        }
        else 
            res.append("?");
        if (r > 0) 
            res.append(",").append(String.format("%02d", r));
        res.append(" ").append(this.getCurrency());
        return res.toString();
    }

    public String getCurrency() {
        return this.getStringValue(ATTR_CURRENCY);
    }

    public String setCurrency(String _value) {
        this.addSlot(ATTR_CURRENCY, _value, true, 0);
        return _value;
    }


    public double getValue() {
        String val = this.getStringValue(ATTR_VALUE);
        if (val == null) 
            return 0.0;
        double v;
        com.pullenti.unisharp.Outargwrapper<Double> wrapv1912 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres1913 = com.pullenti.unisharp.Utils.parseDouble(val, null, wrapv1912);
        v = (wrapv1912.value != null ? wrapv1912.value : 0);
        if (!inoutres1913) 
            return 0.0;
        return v;
    }


    public Double getAltValue() {
        String val = this.getStringValue(ATTR_ALTVALUE);
        if (val == null) 
            return null;
        double v;
        com.pullenti.unisharp.Outargwrapper<Double> wrapv1914 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres1915 = com.pullenti.unisharp.Utils.parseDouble(val, null, wrapv1914);
        v = (wrapv1914.value != null ? wrapv1914.value : 0);
        if (!inoutres1915) 
            return null;
        return v;
    }


    public int getRest() {
        String val = this.getStringValue(ATTR_REST);
        if (val == null) 
            return 0;
        int v;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapv1916 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1917 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapv1916);
        v = (wrapv1916.value != null ? wrapv1916.value : 0);
        if (!inoutres1917) 
            return 0;
        return v;
    }


    public Integer getAltRest() {
        String val = this.getStringValue(ATTR_ALTREST);
        if (val == null) 
            return null;
        int v;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapv1918 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1919 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapv1918);
        v = (wrapv1918.value != null ? wrapv1918.value : 0);
        if (!inoutres1919) 
            return null;
        return v;
    }


    public double getRealValue() {
        return (getValue()) + ((((double)this.getRest()) / (100.0)));
    }

    public double setRealValue(double _value) {
        String val = com.pullenti.ner.core.NumberHelper.doubleToString(_value);
        int ii = val.indexOf('.');
        if (ii > 0) 
            val = val.substring(0, 0 + ii);
        this.addSlot(ATTR_VALUE, val, true, 0);
        double re = ((_value - this.getValue())) * (100.0);
        this.addSlot(ATTR_REST, ((Integer)(int)((re + 0.0001))).toString(), true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        MoneyReferent s = (MoneyReferent)com.pullenti.unisharp.Utils.cast(obj, MoneyReferent.class);
        if (s == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(s.getCurrency(), getCurrency())) 
            return false;
        if (s.getValue() != getValue()) 
            return false;
        if (s.getRest() != getRest()) 
            return false;
        if (s.getAltValue() != getAltValue()) 
            return false;
        if (s.getAltRest() != getAltRest()) 
            return false;
        return true;
    }

    public static MoneyReferent _new901(String _arg1, double _arg2) {
        MoneyReferent res = new MoneyReferent();
        res.setCurrency(_arg1);
        res.setRealValue(_arg2);
        return res;
    }
}
