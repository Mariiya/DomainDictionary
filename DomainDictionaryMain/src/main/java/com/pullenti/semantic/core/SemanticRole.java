/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.core;

/**
 * Семантические роли
 */
public class SemanticRole implements Comparable<SemanticRole> {

    /**
     * Обычная
     */
    public static final SemanticRole COMMON; // 0

    /**
     * Агент
     */
    public static final SemanticRole AGENT; // 1

    /**
     * Пациент
     */
    public static final SemanticRole PACIENT; // 2

    /**
     * Сильная связь
     */
    public static final SemanticRole STRONG; // 3


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemanticRole(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemanticRole v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemanticRole> mapIntToEnum; 
    private static java.util.HashMap<String, SemanticRole> mapStringToEnum; 
    private static SemanticRole[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemanticRole of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemanticRole item = new SemanticRole(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemanticRole of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemanticRole[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemanticRole>();
        mapStringToEnum = new java.util.HashMap<String, SemanticRole>();
        COMMON = new SemanticRole(0, "COMMON");
        mapIntToEnum.put(COMMON.value(), COMMON);
        mapStringToEnum.put(COMMON.m_str.toUpperCase(), COMMON);
        AGENT = new SemanticRole(1, "AGENT");
        mapIntToEnum.put(AGENT.value(), AGENT);
        mapStringToEnum.put(AGENT.m_str.toUpperCase(), AGENT);
        PACIENT = new SemanticRole(2, "PACIENT");
        mapIntToEnum.put(PACIENT.value(), PACIENT);
        mapStringToEnum.put(PACIENT.m_str.toUpperCase(), PACIENT);
        STRONG = new SemanticRole(3, "STRONG");
        mapIntToEnum.put(STRONG.value(), STRONG);
        mapStringToEnum.put(STRONG.m_str.toUpperCase(), STRONG);
        java.util.Collection<SemanticRole> col = mapIntToEnum.values();
        m_Values = new SemanticRole[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
