/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Классы улиц
 */
public class StreetKind implements Comparable<StreetKind> {

    /**
     * Обычная улица-переулок-площадь
     */
    public static final StreetKind UNDEFINED; // 0

    /**
     * Автодорога
     */
    public static final StreetKind ROAD; // 1

    /**
     * Железная дорога
     */
    public static final StreetKind RAILWAY; // 2

    /**
     * Станция метро
     */
    public static final StreetKind METRO; // 3

    /**
     * Районы, кварталы
     */
    public static final StreetKind AREA; // 4

    /**
     * Территория организации или иного объекта
     */
    public static final StreetKind ORG; // 5

    /**
     * Спецобъекты (будки, казармы)
     */
    public static final StreetKind SPEC; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private StreetKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(StreetKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, StreetKind> mapIntToEnum; 
    private static java.util.HashMap<String, StreetKind> mapStringToEnum; 
    private static StreetKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static StreetKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        StreetKind item = new StreetKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static StreetKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static StreetKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, StreetKind>();
        mapStringToEnum = new java.util.HashMap<String, StreetKind>();
        UNDEFINED = new StreetKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ROAD = new StreetKind(1, "ROAD");
        mapIntToEnum.put(ROAD.value(), ROAD);
        mapStringToEnum.put(ROAD.m_str.toUpperCase(), ROAD);
        RAILWAY = new StreetKind(2, "RAILWAY");
        mapIntToEnum.put(RAILWAY.value(), RAILWAY);
        mapStringToEnum.put(RAILWAY.m_str.toUpperCase(), RAILWAY);
        METRO = new StreetKind(3, "METRO");
        mapIntToEnum.put(METRO.value(), METRO);
        mapStringToEnum.put(METRO.m_str.toUpperCase(), METRO);
        AREA = new StreetKind(4, "AREA");
        mapIntToEnum.put(AREA.value(), AREA);
        mapStringToEnum.put(AREA.m_str.toUpperCase(), AREA);
        ORG = new StreetKind(5, "ORG");
        mapIntToEnum.put(ORG.value(), ORG);
        mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
        SPEC = new StreetKind(6, "SPEC");
        mapIntToEnum.put(SPEC.value(), SPEC);
        mapStringToEnum.put(SPEC.m_str.toUpperCase(), SPEC);
        java.util.Collection<StreetKind> col = mapIntToEnum.values();
        m_Values = new StreetKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
