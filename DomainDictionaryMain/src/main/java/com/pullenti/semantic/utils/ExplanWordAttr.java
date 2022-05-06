/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Атрибуты слова дериватной группы DerivateWord
 * Атрибуты слова группы
 */
public class ExplanWordAttr {

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


    public boolean isAnimated() {
        return this.getValue(0);
    }

    public boolean setAnimated(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    public boolean isNamed() {
        return this.getValue(1);
    }

    public boolean setNamed(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    public boolean isNumbered() {
        return this.getValue(2);
    }

    public boolean setNumbered(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    public boolean isMeasured() {
        return this.getValue(3);
    }

    public boolean setMeasured(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    public boolean isEmoPositive() {
        return this.getValue(4);
    }

    public boolean setEmoPositive(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    public boolean isEmoNegative() {
        return this.getValue(5);
    }

    public boolean setEmoNegative(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    public boolean isAnimal() {
        return this.getValue(6);
    }

    public boolean setAnimal(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    public boolean isMan() {
        return this.getValue(7);
    }

    public boolean setMan(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    public boolean isCanPersonAfter() {
        return this.getValue(8);
    }

    public boolean setPersonAfter(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    public boolean isSpaceObject() {
        return this.getValue(9);
    }

    public boolean setSpaceObject(boolean _value) {
        this.setValue(9, _value);
        return _value;
    }


    public boolean isTimeObject() {
        return this.getValue(10);
    }

    public boolean setTimeObject(boolean _value) {
        this.setValue(10, _value);
        return _value;
    }


    public boolean isVerbNoun() {
        return this.getValue(11);
    }

    public boolean setVerbNoun(boolean _value) {
        this.setValue(11, _value);
        return _value;
    }


    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isAnimated()) 
            tmpStr.append("одуш.");
        if (isAnimal()) 
            tmpStr.append("животн.");
        if (isMan()) 
            tmpStr.append("чел.");
        if (isSpaceObject()) 
            tmpStr.append("простр.");
        if (isTimeObject()) 
            tmpStr.append("времен.");
        if (isNamed()) 
            tmpStr.append("именов.");
        if (isNumbered()) 
            tmpStr.append("нумеруем.");
        if (isMeasured()) 
            tmpStr.append("измеряем.");
        if (isEmoPositive()) 
            tmpStr.append("позитив.");
        if (isEmoNegative()) 
            tmpStr.append("негатив.");
        if (isCanPersonAfter()) 
            tmpStr.append("персона_за_родит.");
        if (isVerbNoun()) 
            tmpStr.append("глаг.сущ.");
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExplanWordAttr)) 
            return false;
        return value == ((ExplanWordAttr)obj).value;
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
    public static ExplanWordAttr ooBitand(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new3170((short)((((int)val1) & ((int)val2))));
    }

    /**
     * Моделирование побитного "OR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 | arg2
     */
    public static ExplanWordAttr ooBitor(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new3170((short)((((int)val1) | ((int)val2))));
    }

    /**
     * Неопределённое
     */
    public static ExplanWordAttr UNDEFINED;

    public static ExplanWordAttr _new3170(short _arg1) {
        ExplanWordAttr res = new ExplanWordAttr();
        res.value = _arg1;
        return res;
    }

    public ExplanWordAttr() {
    }
    
    static {
        UNDEFINED = new ExplanWordAttr();
    }
}
