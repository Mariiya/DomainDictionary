/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class NumberTypes implements Comparable<NumberTypes> {

    public static final NumberTypes UNDEFINED; // 0

    public static final NumberTypes DIGIT; // 1

    public static final NumberTypes TWODIGITS; // 2

    public static final NumberTypes THREEDIGITS; // 3

    public static final NumberTypes FOURDIGITS; // 4

    public static final NumberTypes ROMAN; // 5

    public static final NumberTypes LETTER; // 6

    public static final NumberTypes COMBO; // 7


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NumberTypes(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(NumberTypes v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NumberTypes> mapIntToEnum; 
    private static java.util.HashMap<String, NumberTypes> mapStringToEnum; 
    private static NumberTypes[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NumberTypes of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NumberTypes item = new NumberTypes(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NumberTypes of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NumberTypes[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NumberTypes>();
        mapStringToEnum = new java.util.HashMap<String, NumberTypes>();
        UNDEFINED = new NumberTypes(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        DIGIT = new NumberTypes(1, "DIGIT");
        mapIntToEnum.put(DIGIT.value(), DIGIT);
        mapStringToEnum.put(DIGIT.m_str.toUpperCase(), DIGIT);
        TWODIGITS = new NumberTypes(2, "TWODIGITS");
        mapIntToEnum.put(TWODIGITS.value(), TWODIGITS);
        mapStringToEnum.put(TWODIGITS.m_str.toUpperCase(), TWODIGITS);
        THREEDIGITS = new NumberTypes(3, "THREEDIGITS");
        mapIntToEnum.put(THREEDIGITS.value(), THREEDIGITS);
        mapStringToEnum.put(THREEDIGITS.m_str.toUpperCase(), THREEDIGITS);
        FOURDIGITS = new NumberTypes(4, "FOURDIGITS");
        mapIntToEnum.put(FOURDIGITS.value(), FOURDIGITS);
        mapStringToEnum.put(FOURDIGITS.m_str.toUpperCase(), FOURDIGITS);
        ROMAN = new NumberTypes(5, "ROMAN");
        mapIntToEnum.put(ROMAN.value(), ROMAN);
        mapStringToEnum.put(ROMAN.m_str.toUpperCase(), ROMAN);
        LETTER = new NumberTypes(6, "LETTER");
        mapIntToEnum.put(LETTER.value(), LETTER);
        mapStringToEnum.put(LETTER.m_str.toUpperCase(), LETTER);
        COMBO = new NumberTypes(7, "COMBO");
        mapIntToEnum.put(COMBO.value(), COMBO);
        mapStringToEnum.put(COMBO.m_str.toUpperCase(), COMBO);
        java.util.Collection<NumberTypes> col = mapIntToEnum.values();
        m_Values = new NumberTypes[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
