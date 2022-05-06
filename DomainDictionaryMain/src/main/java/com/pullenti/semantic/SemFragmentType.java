/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Тип фрагмента
 */
public class SemFragmentType implements Comparable<SemFragmentType> {

    public static final SemFragmentType UNDEFINED; // 0


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemFragmentType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemFragmentType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemFragmentType> mapIntToEnum; 
    private static java.util.HashMap<String, SemFragmentType> mapStringToEnum; 
    private static SemFragmentType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemFragmentType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemFragmentType item = new SemFragmentType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemFragmentType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemFragmentType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemFragmentType>();
        mapStringToEnum = new java.util.HashMap<String, SemFragmentType>();
        UNDEFINED = new SemFragmentType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        java.util.Collection<SemFragmentType> col = mapIntToEnum.values();
        m_Values = new SemFragmentType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
