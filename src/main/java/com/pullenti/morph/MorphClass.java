/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Часть речи
 */
public class MorphClass {

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


    public boolean isNoun() {
        return this.getValue(0);
    }

    public boolean setNoun(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(0, _value);
        return _value;
    }


    public boolean isAdjective() {
        return this.getValue(1);
    }

    public boolean setAdjective(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(1, _value);
        return _value;
    }


    public boolean isVerb() {
        return this.getValue(2);
    }

    public boolean setVerb(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(2, _value);
        return _value;
    }


    public boolean isAdverb() {
        return this.getValue(3);
    }

    public boolean setAdverb(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(3, _value);
        return _value;
    }


    public boolean isPronoun() {
        return this.getValue(4);
    }

    public boolean setPronoun(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(4, _value);
        return _value;
    }


    public boolean isMisc() {
        return this.getValue(5);
    }

    public boolean setMisc(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(5, _value);
        return _value;
    }


    public boolean isPreposition() {
        return this.getValue(6);
    }

    public boolean setPreposition(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    public boolean isConjunction() {
        return this.getValue(7);
    }

    public boolean setConjunction(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    public boolean isProper() {
        return this.getValue(8);
    }

    public boolean setProper(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    public boolean isProperSurname() {
        return this.getValue(9);
    }

    public boolean setProperSurname(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(9, _value);
        return _value;
    }


    public boolean isProperName() {
        return this.getValue(10);
    }

    public boolean setProperName(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(10, _value);
        return _value;
    }


    public boolean isProperSecname() {
        return this.getValue(11);
    }

    public boolean setProperSecname(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(11, _value);
        return _value;
    }


    public boolean isProperGeo() {
        return this.getValue(12);
    }

    public boolean setProperGeo(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(12, _value);
        return _value;
    }


    public boolean isPersonalPronoun() {
        return this.getValue(13);
    }

    public boolean setPersonalPronoun(boolean _value) {
        this.setValue(13, _value);
        return _value;
    }


    private static String[] m_Names;

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isNoun()) 
            tmpStr.append("существ.|");
        if (isAdjective()) 
            tmpStr.append("прилаг.|");
        if (isVerb()) 
            tmpStr.append("глагол|");
        if (isAdverb()) 
            tmpStr.append("наречие|");
        if (isPronoun()) 
            tmpStr.append("местоим.|");
        if (isMisc()) {
            if (isConjunction() || isPreposition() || isProper()) {
            }
            else 
                tmpStr.append("разное|");
        }
        if (isPreposition()) 
            tmpStr.append("предлог|");
        if (isConjunction()) 
            tmpStr.append("союз|");
        if (isProper()) 
            tmpStr.append("собств.|");
        if (isProperSurname()) 
            tmpStr.append("фамилия|");
        if (isProperName()) 
            tmpStr.append("имя|");
        if (isProperSecname()) 
            tmpStr.append("отч.|");
        if (isProperGeo()) 
            tmpStr.append("геогр.|");
        if (isPersonalPronoun()) 
            tmpStr.append("личн.местоим.|");
        if (tmpStr.length() > 0) 
            tmpStr.setLength(tmpStr.length() - 1);
        return tmpStr.toString();
    }

    /**
     * Неопределённое
     */
    public static MorphClass UNDEFINED;

    /**
     * Существительное
     */
    public static MorphClass NOUN;

    /**
     * Местоимение
     */
    public static MorphClass PRONOUN;

    /**
     * Личное местоимение
     */
    public static MorphClass PERSONALPRONOUN;

    /**
     * Глагол
     */
    public static MorphClass VERB;

    /**
     * Прилагательное
     */
    public static MorphClass ADJECTIVE;

    /**
     * Наречие
     */
    public static MorphClass ADVERB;

    /**
     * Предлог
     */
    public static MorphClass PREPOSITION;

    /**
     * Союз
     */
    public static MorphClass CONJUNCTION;

    /**
     * Проверка на полное совпадение значений
     * @param obj 
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MorphClass)) 
            return false;
        return value == ((MorphClass)obj).value;
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
    public static MorphClass ooBitand(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new53((short)((((int)val1) & ((int)val2))));
    }

    /**
     * Моделирование побитного "OR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 | arg2
     */
    public static MorphClass ooBitor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new53((short)((((int)val1) | ((int)val2))));
    }

    /**
     * Моделирование побитного "XOR"
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @return arg1 ^ arg2
     */
    public static MorphClass ooBitxor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new53((short)((((int)val1) ^ ((int)val2))));
    }

    public static MorphClass _new44(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setUndefined(_arg1);
        return res;
    }

    public static MorphClass _new45(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setNoun(_arg1);
        return res;
    }

    public static MorphClass _new46(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setPronoun(_arg1);
        return res;
    }

    public static MorphClass _new47(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setPersonalPronoun(_arg1);
        return res;
    }

    public static MorphClass _new48(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setVerb(_arg1);
        return res;
    }

    public static MorphClass _new49(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setAdjective(_arg1);
        return res;
    }

    public static MorphClass _new50(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setAdverb(_arg1);
        return res;
    }

    public static MorphClass _new51(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setPreposition(_arg1);
        return res;
    }

    public static MorphClass _new52(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setConjunction(_arg1);
        return res;
    }

    public static MorphClass _new53(short _arg1) {
        MorphClass res = new MorphClass();
        res.value = _arg1;
        return res;
    }

    public static MorphClass _new2778(boolean _arg1) {
        MorphClass res = new MorphClass();
        res.setProperSurname(_arg1);
        return res;
    }

    public MorphClass() {
    }
    
    static {
        m_Names = new String[] {"существ.", "прилаг.", "глагол", "наречие", "местоим.", "разное", "предлог", "союз", "собств.", "фамилия", "имя", "отч.", "геогр.", "личн.местоим."};
        UNDEFINED = _new44(true);
        NOUN = _new45(true);
        PRONOUN = _new46(true);
        PERSONALPRONOUN = _new47(true);
        VERB = _new48(true);
        ADJECTIVE = _new49(true);
        ADVERB = _new50(true);
        PREPOSITION = _new51(true);
        CONJUNCTION = _new52(true);
    }
}
