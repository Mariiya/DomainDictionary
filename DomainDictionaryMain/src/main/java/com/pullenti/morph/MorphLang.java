/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Язык
 */
public class MorphLang {

    public short value;

    private boolean getValue(int i) {
        return ((((((int)value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            value |= ((short)(1 << i));
        else 
            value &= ((short)(~(1 << i)));
    }

    public boolean isUndefined() {
        return value == ((short)0);
    }

    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    public boolean isRu() {
        return this.getValue(0);
    }

    public boolean setRu(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    public boolean isUa() {
        return this.getValue(1);
    }

    public boolean setUa(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    public boolean isBy() {
        return this.getValue(2);
    }

    public boolean setBy(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    public boolean isCyrillic() {
        return (isRu() | isUa() | isBy()) | isKz();
    }


    public boolean isEn() {
        return this.getValue(3);
    }

    public boolean setEn(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    public boolean isIt() {
        return this.getValue(4);
    }

    public boolean setIt(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    public boolean isKz() {
        return this.getValue(5);
    }

    public boolean setKz(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    private static String[] m_Names;

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isRu()) 
            tmpStr.append("RU;");
        if (isUa()) 
            tmpStr.append("UA;");
        if (isBy()) 
            tmpStr.append("BY;");
        if (isEn()) 
            tmpStr.append("EN;");
        if (isIt()) 
            tmpStr.append("IT;");
        if (isKz()) 
            tmpStr.append("KZ;");
        if (tmpStr.length() > 0) 
            tmpStr.setLength(tmpStr.length() - 1);
        return tmpStr.toString();
    }

    /**
     * Сравнение значение (полное совпадение)
     * @param obj 
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MorphLang)) 
            return false;
        return value == ((MorphLang)obj).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    /**
     * Преобразовать из строки
     * @param str 
     * @param lang 
     * @return 
     */
    public static boolean tryParse(String str, com.pullenti.unisharp.Outargwrapper<MorphLang> lang) {
        lang.value = new MorphLang();
        while (!com.pullenti.unisharp.Utils.isNullOrEmpty(str)) {
            int i;
            for (i = 0; i < m_Names.length; i++) {
                if (com.pullenti.unisharp.Utils.startsWithString(str, m_Names[i], true)) 
                    break;
            }
            if (i >= m_Names.length) 
                break;
            lang.value.value |= ((short)(1 << i));
            for (i = 2; i < str.length(); i++) {
                if (Character.isLetter(str.charAt(i))) 
                    break;
            }
            if (i >= str.length()) 
                break;
            str = str.substring(i);
        }
        if (lang.value.isUndefined()) 
            return false;
        return true;
    }

    /**
     * Моделирование побитного "AND"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 & arg2
     */
    public static MorphLang ooBitand(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new56((short)((((int)val1) & ((int)val2))));
    }

    /**
     * Моделирование побитного "OR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 | arg2
     */
    public static MorphLang ooBitor(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new56((short)((((int)val1) | ((int)val2))));
    }

    /**
     * Неопределённое
     */
    public static MorphLang UNKNOWN;

    /**
     * Русский
     */
    public static MorphLang RU;

    /**
     * Украинский
     */
    public static MorphLang UA;

    /**
     * Белорусский
     */
    public static MorphLang BY;

    /**
     * Английский
     */
    public static MorphLang EN;

    /**
     * Итальянский
     */
    public static MorphLang IT;

    /**
     * Казахский
     */
    public static MorphLang KZ;

    public static MorphLang _new56(short _arg1) {
        MorphLang res = new MorphLang();
        res.value = _arg1;
        return res;
    }

    public static MorphLang _new58(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setRu(_arg1);
        return res;
    }

    public static MorphLang _new59(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setUa(_arg1);
        return res;
    }

    public static MorphLang _new60(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setBy(_arg1);
        return res;
    }

    public static MorphLang _new61(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setEn(_arg1);
        return res;
    }

    public static MorphLang _new62(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setIt(_arg1);
        return res;
    }

    public static MorphLang _new63(boolean _arg1) {
        MorphLang res = new MorphLang();
        res.setKz(_arg1);
        return res;
    }

    public MorphLang() {
    }
    
    static {
        m_Names = new String[] {"RU", "UA", "BY", "EN", "IT", "KZ"};
        UNKNOWN = new MorphLang();
        RU = _new58(true);
        UA = _new59(true);
        BY = _new60(true);
        EN = _new61(true);
        IT = _new62(true);
        KZ = _new63(true);
    }
}
