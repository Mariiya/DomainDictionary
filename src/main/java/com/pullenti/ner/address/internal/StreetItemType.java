/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class StreetItemType implements Comparable<StreetItemType> {

    public static final StreetItemType NOUN; // 0

    public static final StreetItemType NAME; // 1

    public static final StreetItemType NUMBER; // 2

    public static final StreetItemType STDADJECTIVE; // 3

    public static final StreetItemType STDNAME; // 4

    public static final StreetItemType STDPARTOFNAME; // 5

    public static final StreetItemType AGE; // 6

    public static final StreetItemType FIX; // 7


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private StreetItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(StreetItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, StreetItemType> mapIntToEnum; 
    private static java.util.HashMap<String, StreetItemType> mapStringToEnum; 
    private static StreetItemType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static StreetItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        StreetItemType item = new StreetItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static StreetItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static StreetItemType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, StreetItemType>();
        mapStringToEnum = new java.util.HashMap<String, StreetItemType>();
        NOUN = new StreetItemType(0, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        NAME = new StreetItemType(1, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        NUMBER = new StreetItemType(2, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        STDADJECTIVE = new StreetItemType(3, "STDADJECTIVE");
        mapIntToEnum.put(STDADJECTIVE.value(), STDADJECTIVE);
        mapStringToEnum.put(STDADJECTIVE.m_str.toUpperCase(), STDADJECTIVE);
        STDNAME = new StreetItemType(4, "STDNAME");
        mapIntToEnum.put(STDNAME.value(), STDNAME);
        mapStringToEnum.put(STDNAME.m_str.toUpperCase(), STDNAME);
        STDPARTOFNAME = new StreetItemType(5, "STDPARTOFNAME");
        mapIntToEnum.put(STDPARTOFNAME.value(), STDPARTOFNAME);
        mapStringToEnum.put(STDPARTOFNAME.m_str.toUpperCase(), STDPARTOFNAME);
        AGE = new StreetItemType(6, "AGE");
        mapIntToEnum.put(AGE.value(), AGE);
        mapStringToEnum.put(AGE.m_str.toUpperCase(), AGE);
        FIX = new StreetItemType(7, "FIX");
        mapIntToEnum.put(FIX.value(), FIX);
        mapStringToEnum.put(FIX.m_str.toUpperCase(), FIX);
        java.util.Collection<StreetItemType> col = mapIntToEnum.values();
        m_Values = new StreetItemType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
