/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Тип связи между фрагментами
 */
public class SemFraglinkType implements Comparable<SemFraglinkType> {

    /**
     * Не определён
     */
    public static final SemFraglinkType UNDEFINED; // 0

    /**
     * Если - то
     */
    public static final SemFraglinkType IFTHEN; // 1

    /**
     * Если - иначе
     */
    public static final SemFraglinkType IFELSE; // 2

    /**
     * Потому что
     */
    public static final SemFraglinkType BECAUSE; // 3

    /**
     * Но (..., однако ...)
     */
    public static final SemFraglinkType BUT; // 4

    /**
     * Для того, чтобы...
     */
    public static final SemFraglinkType FOR; // 5

    /**
     * Что
     */
    public static final SemFraglinkType WHAT; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemFraglinkType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemFraglinkType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemFraglinkType> mapIntToEnum; 
    private static java.util.HashMap<String, SemFraglinkType> mapStringToEnum; 
    private static SemFraglinkType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemFraglinkType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemFraglinkType item = new SemFraglinkType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemFraglinkType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemFraglinkType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemFraglinkType>();
        mapStringToEnum = new java.util.HashMap<String, SemFraglinkType>();
        UNDEFINED = new SemFraglinkType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        IFTHEN = new SemFraglinkType(1, "IFTHEN");
        mapIntToEnum.put(IFTHEN.value(), IFTHEN);
        mapStringToEnum.put(IFTHEN.m_str.toUpperCase(), IFTHEN);
        IFELSE = new SemFraglinkType(2, "IFELSE");
        mapIntToEnum.put(IFELSE.value(), IFELSE);
        mapStringToEnum.put(IFELSE.m_str.toUpperCase(), IFELSE);
        BECAUSE = new SemFraglinkType(3, "BECAUSE");
        mapIntToEnum.put(BECAUSE.value(), BECAUSE);
        mapStringToEnum.put(BECAUSE.m_str.toUpperCase(), BECAUSE);
        BUT = new SemFraglinkType(4, "BUT");
        mapIntToEnum.put(BUT.value(), BUT);
        mapStringToEnum.put(BUT.m_str.toUpperCase(), BUT);
        FOR = new SemFraglinkType(5, "FOR");
        mapIntToEnum.put(FOR.value(), FOR);
        mapStringToEnum.put(FOR.m_str.toUpperCase(), FOR);
        WHAT = new SemFraglinkType(6, "WHAT");
        mapIntToEnum.put(WHAT.value(), WHAT);
        mapStringToEnum.put(WHAT.m_str.toUpperCase(), WHAT);
        java.util.Collection<SemFraglinkType> col = mapIntToEnum.values();
        m_Values = new SemFraglinkType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
