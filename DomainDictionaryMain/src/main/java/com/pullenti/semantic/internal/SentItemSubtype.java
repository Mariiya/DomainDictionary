/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class SentItemSubtype implements Comparable<SentItemSubtype> {

    public static final SentItemSubtype UNDEFINED; // 0

    public static final SentItemSubtype WICH; // 1

    public static final SentItemSubtype WHAT; // 2

    public static final SentItemSubtype HOW; // 3

    public static final SentItemSubtype HOWMANY; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SentItemSubtype(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SentItemSubtype v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SentItemSubtype> mapIntToEnum; 
    private static java.util.HashMap<String, SentItemSubtype> mapStringToEnum; 
    private static SentItemSubtype[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SentItemSubtype of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SentItemSubtype item = new SentItemSubtype(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SentItemSubtype of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SentItemSubtype[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SentItemSubtype>();
        mapStringToEnum = new java.util.HashMap<String, SentItemSubtype>();
        UNDEFINED = new SentItemSubtype(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        WICH = new SentItemSubtype(1, "WICH");
        mapIntToEnum.put(WICH.value(), WICH);
        mapStringToEnum.put(WICH.m_str.toUpperCase(), WICH);
        WHAT = new SentItemSubtype(2, "WHAT");
        mapIntToEnum.put(WHAT.value(), WHAT);
        mapStringToEnum.put(WHAT.m_str.toUpperCase(), WHAT);
        HOW = new SentItemSubtype(3, "HOW");
        mapIntToEnum.put(HOW.value(), HOW);
        mapStringToEnum.put(HOW.m_str.toUpperCase(), HOW);
        HOWMANY = new SentItemSubtype(4, "HOWMANY");
        mapIntToEnum.put(HOWMANY.value(), HOWMANY);
        mapStringToEnum.put(HOWMANY.m_str.toUpperCase(), HOWMANY);
        java.util.Collection<SentItemSubtype> col = mapIntToEnum.values();
        m_Values = new SentItemSubtype[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
