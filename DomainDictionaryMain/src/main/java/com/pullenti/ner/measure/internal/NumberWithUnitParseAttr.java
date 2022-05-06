/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

public class NumberWithUnitParseAttr implements Comparable<NumberWithUnitParseAttr> {

    public static final NumberWithUnitParseAttr NO; // 0

    public static final NumberWithUnitParseAttr CANOMITNUMBER; // 1

    public static final NumberWithUnitParseAttr NOT; // 2

    public static final NumberWithUnitParseAttr CANBENON; // 4

    public static final NumberWithUnitParseAttr ISSECOND; // 8


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NumberWithUnitParseAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NumberWithUnitParseAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NumberWithUnitParseAttr> mapIntToEnum; 
    private static java.util.HashMap<String, NumberWithUnitParseAttr> mapStringToEnum; 
    private static NumberWithUnitParseAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NumberWithUnitParseAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NumberWithUnitParseAttr item = new NumberWithUnitParseAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NumberWithUnitParseAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NumberWithUnitParseAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NumberWithUnitParseAttr>();
        mapStringToEnum = new java.util.HashMap<String, NumberWithUnitParseAttr>();
        NO = new NumberWithUnitParseAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        CANOMITNUMBER = new NumberWithUnitParseAttr(1, "CANOMITNUMBER");
        mapIntToEnum.put(CANOMITNUMBER.value(), CANOMITNUMBER);
        mapStringToEnum.put(CANOMITNUMBER.m_str.toUpperCase(), CANOMITNUMBER);
        NOT = new NumberWithUnitParseAttr(2, "NOT");
        mapIntToEnum.put(NOT.value(), NOT);
        mapStringToEnum.put(NOT.m_str.toUpperCase(), NOT);
        CANBENON = new NumberWithUnitParseAttr(4, "CANBENON");
        mapIntToEnum.put(CANBENON.value(), CANBENON);
        mapStringToEnum.put(CANBENON.m_str.toUpperCase(), CANBENON);
        ISSECOND = new NumberWithUnitParseAttr(8, "ISSECOND");
        mapIntToEnum.put(ISSECOND.value(), ISSECOND);
        mapStringToEnum.put(ISSECOND.m_str.toUpperCase(), ISSECOND);
        java.util.Collection<NumberWithUnitParseAttr> col = mapIntToEnum.values();
        m_Values = new NumberWithUnitParseAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
