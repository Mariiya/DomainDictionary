/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Информация о символах токена
 * 
 * Символьная информация
 */
public class CharsInfo {

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

    public boolean isAllUpper() {
        return this.getValue(0);
    }

    public boolean setAllUpper(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    public boolean isAllLower() {
        return this.getValue(1);
    }

    public boolean setAllLower(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    public boolean isCapitalUpper() {
        return this.getValue(2);
    }

    public boolean setCapitalUpper(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    public boolean isLastLower() {
        return this.getValue(3);
    }

    public boolean setLastLower(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    public boolean isLetter() {
        return this.getValue(4);
    }

    public boolean setLetter(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    public boolean isLatinLetter() {
        return this.getValue(5);
    }

    public boolean setLatinLetter(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    public boolean isCyrillicLetter() {
        return this.getValue(6);
    }

    public boolean setCyrillicLetter(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    @Override
    public String toString() {
        if (!isLetter()) 
            return "Nonletter";
        StringBuilder tmpStr = new StringBuilder();
        if (isAllUpper()) 
            tmpStr.append("AllUpper");
        else if (isAllLower()) 
            tmpStr.append("AllLower");
        else if (isCapitalUpper()) 
            tmpStr.append("CapitalUpper");
        else if (isLastLower()) 
            tmpStr.append("LastLower");
        else 
            tmpStr.append("Nonstandard");
        if (isLatinLetter()) 
            tmpStr.append(" Latin");
        else if (isCyrillicLetter()) 
            tmpStr.append(" Cyrillic");
        else if (isLetter()) 
            tmpStr.append(" Letter");
        return tmpStr.toString();
    }

    /**
     * Сравнение на совпадение значений всех полей
     * @param obj сравниваемый объект
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CharsInfo)) 
            return false;
        return value == ((CharsInfo)obj).value;
    }

    public static CharsInfo _new2546(boolean _arg1) {
        CharsInfo res = new CharsInfo();
        res.setCapitalUpper(_arg1);
        return res;
    }

    public static CharsInfo _new2751(boolean _arg1) {
        CharsInfo res = new CharsInfo();
        res.setCyrillicLetter(_arg1);
        return res;
    }

    public static CharsInfo _new2757(boolean _arg1, boolean _arg2) {
        CharsInfo res = new CharsInfo();
        res.setCyrillicLetter(_arg1);
        res.setCapitalUpper(_arg2);
        return res;
    }

    public static CharsInfo _new2762(boolean _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        CharsInfo res = new CharsInfo();
        res.setCapitalUpper(_arg1);
        res.setCyrillicLetter(_arg2);
        res.setLatinLetter(_arg3);
        res.setLetter(_arg4);
        return res;
    }

    public static CharsInfo _new2767(short _arg1) {
        CharsInfo res = new CharsInfo();
        res.value = _arg1;
        return res;
    }

    public static CharsInfo _new2786(boolean _arg1) {
        CharsInfo res = new CharsInfo();
        res.setLatinLetter(_arg1);
        return res;
    }
    public CharsInfo() {
    }
}
