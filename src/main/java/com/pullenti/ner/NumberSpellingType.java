/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Тип написания числительного NumberToken
 * Тип числительного
 */
public class NumberSpellingType implements Comparable<NumberSpellingType> {

    /**
     * Цифрами
     */
    public static final NumberSpellingType DIGIT; // 0

    /**
     * Римскими цифрами
     */
    public static final NumberSpellingType ROMAN; // 1

    /**
     * Прописью (словами)
     */
    public static final NumberSpellingType WORDS; // 2

    /**
     * Возраст (летие)
     */
    public static final NumberSpellingType AGE; // 3


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NumberSpellingType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NumberSpellingType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NumberSpellingType> mapIntToEnum; 
    private static java.util.HashMap<String, NumberSpellingType> mapStringToEnum; 
    private static NumberSpellingType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NumberSpellingType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NumberSpellingType item = new NumberSpellingType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NumberSpellingType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NumberSpellingType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NumberSpellingType>();
        mapStringToEnum = new java.util.HashMap<String, NumberSpellingType>();
        DIGIT = new NumberSpellingType(0, "DIGIT");
        mapIntToEnum.put(DIGIT.value(), DIGIT);
        mapStringToEnum.put(DIGIT.m_str.toUpperCase(), DIGIT);
        ROMAN = new NumberSpellingType(1, "ROMAN");
        mapIntToEnum.put(ROMAN.value(), ROMAN);
        mapStringToEnum.put(ROMAN.m_str.toUpperCase(), ROMAN);
        WORDS = new NumberSpellingType(2, "WORDS");
        mapIntToEnum.put(WORDS.value(), WORDS);
        mapStringToEnum.put(WORDS.m_str.toUpperCase(), WORDS);
        AGE = new NumberSpellingType(3, "AGE");
        mapIntToEnum.put(AGE.value(), AGE);
        mapStringToEnum.put(AGE.m_str.toUpperCase(), AGE);
        java.util.Collection<NumberSpellingType> col = mapIntToEnum.values();
        m_Values = new NumberSpellingType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
