/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Тип семантической связи
 */
public class SemLinkType implements Comparable<SemLinkType> {

    public static final SemLinkType UNDEFINED; // 0

    /**
     * Детализация (какой?)
     */
    public static final SemLinkType DETAIL; // 1

    /**
     * Именование
     */
    public static final SemLinkType NAMING; // 2

    /**
     * Агент (кто действует)
     */
    public static final SemLinkType AGENT; // 3

    /**
     * Пациент (на кого действуют)
     */
    public static final SemLinkType PACIENT; // 4

    /**
     * Причастный и деепричастный оборот
     */
    public static final SemLinkType PARTICIPLE; // 5

    /**
     * Анафорическая ссылка (он, который, ...)
     */
    public static final SemLinkType ANAFOR; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemLinkType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemLinkType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemLinkType> mapIntToEnum; 
    private static java.util.HashMap<String, SemLinkType> mapStringToEnum; 
    private static SemLinkType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemLinkType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemLinkType item = new SemLinkType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemLinkType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemLinkType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemLinkType>();
        mapStringToEnum = new java.util.HashMap<String, SemLinkType>();
        UNDEFINED = new SemLinkType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        DETAIL = new SemLinkType(1, "DETAIL");
        mapIntToEnum.put(DETAIL.value(), DETAIL);
        mapStringToEnum.put(DETAIL.m_str.toUpperCase(), DETAIL);
        NAMING = new SemLinkType(2, "NAMING");
        mapIntToEnum.put(NAMING.value(), NAMING);
        mapStringToEnum.put(NAMING.m_str.toUpperCase(), NAMING);
        AGENT = new SemLinkType(3, "AGENT");
        mapIntToEnum.put(AGENT.value(), AGENT);
        mapStringToEnum.put(AGENT.m_str.toUpperCase(), AGENT);
        PACIENT = new SemLinkType(4, "PACIENT");
        mapIntToEnum.put(PACIENT.value(), PACIENT);
        mapStringToEnum.put(PACIENT.m_str.toUpperCase(), PACIENT);
        PARTICIPLE = new SemLinkType(5, "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        ANAFOR = new SemLinkType(6, "ANAFOR");
        mapIntToEnum.put(ANAFOR.value(), ANAFOR);
        mapStringToEnum.put(ANAFOR.m_str.toUpperCase(), ANAFOR);
        java.util.Collection<SemLinkType> col = mapIntToEnum.values();
        m_Values = new SemLinkType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
