/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Падеж
 */
public class MorphCase {

    public short value;

    public boolean isUndefined() {
        return value == ((short)0);
    }

    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    private boolean getValue(int i) {
        return ((((((int)value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            value |= ((short)(1 << i));
        else 
            value &= ((short)(~(1 << i)));
    }

    public int getCount() {
        if (value == ((short)0)) 
            return 0;
        int cou = 0;
        for (int i = 0; i < 12; i++) {
            if (((((int)value) & (1 << i))) != 0) 
                cou++;
        }
        return cou;
    }


    public static MorphCase UNDEFINED;

    /**
     * Именительный падеж
     */
    public static MorphCase NOMINATIVE;

    /**
     * Родительный падеж
     */
    public static MorphCase GENITIVE;

    /**
     * Дательный падеж
     */
    public static MorphCase DATIVE;

    /**
     * Винительный падеж
     */
    public static MorphCase ACCUSATIVE;

    /**
     * Творительный падеж
     */
    public static MorphCase INSTRUMENTAL;

    /**
     * Предложный падеж
     */
    public static MorphCase PREPOSITIONAL;

    /**
     * Звательный падеж
     */
    public static MorphCase VOCATIVE;

    /**
     * Частичный падеж
     */
    public static MorphCase PARTIAL;

    /**
     * Общий падеж
     */
    public static MorphCase COMMON;

    /**
     * Притяжательный падеж
     */
    public static MorphCase POSSESSIVE;

    /**
     * Все падежи одновременно
     */
    public static MorphCase ALLCASES;

    public boolean isNominative() {
        return this.getValue(0);
    }

    public boolean setNominative(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    public boolean isGenitive() {
        return this.getValue(1);
    }

    public boolean setGenitive(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    public boolean isDative() {
        return this.getValue(2);
    }

    public boolean setDative(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    public boolean isAccusative() {
        return this.getValue(3);
    }

    public boolean setAccusative(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    public boolean isInstrumental() {
        return this.getValue(4);
    }

    public boolean setInstrumental(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    public boolean isPrepositional() {
        return this.getValue(5);
    }

    public boolean setPrepositional(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    public boolean isVocative() {
        return this.getValue(6);
    }

    public boolean setVocative(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    public boolean isPartial() {
        return this.getValue(7);
    }

    public boolean setPartial(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    public boolean isCommon() {
        return this.getValue(8);
    }

    public boolean setCommon(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    public boolean isPossessive() {
        return this.getValue(9);
    }

    public boolean setPossessive(boolean _value) {
        this.setValue(9, _value);
        return _value;
    }


    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isNominative()) 
            tmpStr.append("именит.|");
        if (isGenitive()) 
            tmpStr.append("родит.|");
        if (isDative()) 
            tmpStr.append("дател.|");
        if (isAccusative()) 
            tmpStr.append("винит.|");
        if (isInstrumental()) 
            tmpStr.append("творит.|");
        if (isPrepositional()) 
            tmpStr.append("предлож.|");
        if (isVocative()) 
            tmpStr.append("зват.|");
        if (isPartial()) 
            tmpStr.append("частич.|");
        if (isCommon()) 
            tmpStr.append("общ.|");
        if (isPossessive()) 
            tmpStr.append("притяж.|");
        if (tmpStr.length() > 0) 
            tmpStr.setLength(tmpStr.length() - 1);
        return tmpStr.toString();
    }

    private static String[] m_Names;

    /**
     * Восстановить падежи из строки, полученной ToString
     * @param str 
     * @return 
     */
    public static MorphCase parse(String str) {
        MorphCase res = new MorphCase();
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return res;
        for (String s : com.pullenti.unisharp.Utils.split(str, String.valueOf('|'), false)) {
            for (int i = 0; i < m_Names.length; i++) {
                if (com.pullenti.unisharp.Utils.stringsEq(s, m_Names[i])) {
                    res.setValue(i, true);
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Проверка на полное совпадение значений
     * @param obj 
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MorphCase)) 
            return false;
        return value == ((MorphCase)obj).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    /**
     * Моделирование побитного "AND"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 & arg2
     */
    public static MorphCase ooBitand(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new29((short)((((int)val1) & ((int)val2))));
    }

    /**
     * Моделирование побитного "OR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 | arg2
     */
    public static MorphCase ooBitor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new29((short)((((int)val1) | ((int)val2))));
    }

    /**
     * Моделирование побитного "XOR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 ^ arg2
     */
    public static MorphCase ooBitxor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new29((short)((((int)val1) ^ ((int)val2))));
    }

    public static MorphCase _new29(short _arg1) {
        MorphCase res = new MorphCase();
        res.value = _arg1;
        return res;
    }

    public MorphCase() {
    }
    
    static {
        UNDEFINED = _new29((short)0);
        NOMINATIVE = _new29((short)1);
        GENITIVE = _new29((short)2);
        DATIVE = _new29((short)4);
        ACCUSATIVE = _new29((short)8);
        INSTRUMENTAL = _new29((short)0x10);
        PREPOSITIONAL = _new29((short)0x20);
        VOCATIVE = _new29((short)0x40);
        PARTIAL = _new29((short)0x80);
        COMMON = _new29((short)0x100);
        POSSESSIVE = _new29((short)0x200);
        ALLCASES = _new29((short)0x3FF);
        m_Names = new String[] {"именит.", "родит.", "дател.", "винит.", "творит.", "предлож.", "зват.", "частич.", "общ.", "притяж."};
    }
}
