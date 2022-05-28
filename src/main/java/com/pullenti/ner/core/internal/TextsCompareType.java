/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class TextsCompareType implements Comparable<TextsCompareType> {

    public static final TextsCompareType NONCOMPARABLE; // 0

    public static final TextsCompareType EQUIVALENT; // 1

    public static final TextsCompareType EARLY; // 2

    public static final TextsCompareType LATER; // 3

    public static final TextsCompareType IN; // 4

    public static final TextsCompareType CONTAINS; // 5

    public static final TextsCompareType INTERSECT; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private TextsCompareType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(TextsCompareType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, TextsCompareType> mapIntToEnum; 
    private static java.util.HashMap<String, TextsCompareType> mapStringToEnum; 
    private static TextsCompareType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static TextsCompareType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        TextsCompareType item = new TextsCompareType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static TextsCompareType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static TextsCompareType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, TextsCompareType>();
        mapStringToEnum = new java.util.HashMap<String, TextsCompareType>();
        NONCOMPARABLE = new TextsCompareType(0, "NONCOMPARABLE");
        mapIntToEnum.put(NONCOMPARABLE.value(), NONCOMPARABLE);
        mapStringToEnum.put(NONCOMPARABLE.m_str.toUpperCase(), NONCOMPARABLE);
        EQUIVALENT = new TextsCompareType(1, "EQUIVALENT");
        mapIntToEnum.put(EQUIVALENT.value(), EQUIVALENT);
        mapStringToEnum.put(EQUIVALENT.m_str.toUpperCase(), EQUIVALENT);
        EARLY = new TextsCompareType(2, "EARLY");
        mapIntToEnum.put(EARLY.value(), EARLY);
        mapStringToEnum.put(EARLY.m_str.toUpperCase(), EARLY);
        LATER = new TextsCompareType(3, "LATER");
        mapIntToEnum.put(LATER.value(), LATER);
        mapStringToEnum.put(LATER.m_str.toUpperCase(), LATER);
        IN = new TextsCompareType(4, "IN");
        mapIntToEnum.put(IN.value(), IN);
        mapStringToEnum.put(IN.m_str.toUpperCase(), IN);
        CONTAINS = new TextsCompareType(5, "CONTAINS");
        mapIntToEnum.put(CONTAINS.value(), CONTAINS);
        mapStringToEnum.put(CONTAINS.m_str.toUpperCase(), CONTAINS);
        INTERSECT = new TextsCompareType(6, "INTERSECT");
        mapIntToEnum.put(INTERSECT.value(), INTERSECT);
        mapStringToEnum.put(INTERSECT.m_str.toUpperCase(), INTERSECT);
        java.util.Collection<TextsCompareType> col = mapIntToEnum.values();
        m_Values = new TextsCompareType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
