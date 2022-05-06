/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class NameTokenType implements Comparable<NameTokenType> {

    public static final NameTokenType ANY; // 0

    public static final NameTokenType ORG; // 1

    public static final NameTokenType STREET; // 2

    public static final NameTokenType CITY; // 3

    public static final NameTokenType TERR; // 4

    public static final NameTokenType STRONG; // 5


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NameTokenType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NameTokenType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NameTokenType> mapIntToEnum; 
    private static java.util.HashMap<String, NameTokenType> mapStringToEnum; 
    private static NameTokenType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NameTokenType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NameTokenType item = new NameTokenType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NameTokenType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NameTokenType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NameTokenType>();
        mapStringToEnum = new java.util.HashMap<String, NameTokenType>();
        ANY = new NameTokenType(0, "ANY");
        mapIntToEnum.put(ANY.value(), ANY);
        mapStringToEnum.put(ANY.m_str.toUpperCase(), ANY);
        ORG = new NameTokenType(1, "ORG");
        mapIntToEnum.put(ORG.value(), ORG);
        mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
        STREET = new NameTokenType(2, "STREET");
        mapIntToEnum.put(STREET.value(), STREET);
        mapStringToEnum.put(STREET.m_str.toUpperCase(), STREET);
        CITY = new NameTokenType(3, "CITY");
        mapIntToEnum.put(CITY.value(), CITY);
        mapStringToEnum.put(CITY.m_str.toUpperCase(), CITY);
        TERR = new NameTokenType(4, "TERR");
        mapIntToEnum.put(TERR.value(), TERR);
        mapStringToEnum.put(TERR.m_str.toUpperCase(), TERR);
        STRONG = new NameTokenType(5, "STRONG");
        mapIntToEnum.put(STRONG.value(), STRONG);
        mapStringToEnum.put(STRONG.m_str.toUpperCase(), STRONG);
        java.util.Collection<NameTokenType> col = mapIntToEnum.values();
        m_Values = new NameTokenType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
