/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.named;

/**
 * Категории мелких именованных сущностей
 */
public class NamedEntityKind implements Comparable<NamedEntityKind> {

    /**
     * Неопределённая
     */
    public static final NamedEntityKind UNDEFINED; // 0

    /**
     * Планеты
     */
    public static final NamedEntityKind PLANET; // 1

    /**
     * Разные географические объекты (не города) - реки, моря, континенты ...
     */
    public static final NamedEntityKind LOCATION; // 2

    /**
     * Памятники и монументы
     */
    public static final NamedEntityKind MONUMENT; // 3

    /**
     * Выдающиеся здания
     */
    public static final NamedEntityKind BUILDING; // 4

    /**
     * Произведения искусства (книги, фильмы, спектакли ...)
     */
    public static final NamedEntityKind ART; // 5

    /**
     * Награды, ордена, титулы (пока не реализовано)
     */
    public static final NamedEntityKind AWARD; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NamedEntityKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NamedEntityKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NamedEntityKind> mapIntToEnum; 
    private static java.util.HashMap<String, NamedEntityKind> mapStringToEnum; 
    private static NamedEntityKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NamedEntityKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NamedEntityKind item = new NamedEntityKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NamedEntityKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NamedEntityKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NamedEntityKind>();
        mapStringToEnum = new java.util.HashMap<String, NamedEntityKind>();
        UNDEFINED = new NamedEntityKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PLANET = new NamedEntityKind(1, "PLANET");
        mapIntToEnum.put(PLANET.value(), PLANET);
        mapStringToEnum.put(PLANET.m_str.toUpperCase(), PLANET);
        LOCATION = new NamedEntityKind(2, "LOCATION");
        mapIntToEnum.put(LOCATION.value(), LOCATION);
        mapStringToEnum.put(LOCATION.m_str.toUpperCase(), LOCATION);
        MONUMENT = new NamedEntityKind(3, "MONUMENT");
        mapIntToEnum.put(MONUMENT.value(), MONUMENT);
        mapStringToEnum.put(MONUMENT.m_str.toUpperCase(), MONUMENT);
        BUILDING = new NamedEntityKind(4, "BUILDING");
        mapIntToEnum.put(BUILDING.value(), BUILDING);
        mapStringToEnum.put(BUILDING.m_str.toUpperCase(), BUILDING);
        ART = new NamedEntityKind(5, "ART");
        mapIntToEnum.put(ART.value(), ART);
        mapStringToEnum.put(ART.m_str.toUpperCase(), ART);
        AWARD = new NamedEntityKind(6, "AWARD");
        mapIntToEnum.put(AWARD.value(), AWARD);
        mapStringToEnum.put(AWARD.m_str.toUpperCase(), AWARD);
        java.util.Collection<NamedEntityKind> col = mapIntToEnum.values();
        m_Values = new NamedEntityKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
