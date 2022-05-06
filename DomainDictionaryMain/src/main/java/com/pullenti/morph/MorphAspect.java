/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Аспект (для глаголов)
 * Аспект
 */
public class MorphAspect implements Comparable<MorphAspect> {

    /**
     * Неопределено
     */
    public static final MorphAspect UNDEFINED; // (short)0

    /**
     * Совершенный
     */
    public static final MorphAspect PERFECTIVE; // (short)1

    /**
     * Несовершенный
     */
    public static final MorphAspect IMPERFECTIVE; // (short)2


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphAspect(short val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Short)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(MorphAspect v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphAspect> mapIntToEnum; 
    private static java.util.HashMap<String, MorphAspect> mapStringToEnum; 
    private static MorphAspect[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphAspect of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphAspect item = new MorphAspect(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphAspect of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphAspect[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphAspect>();
        mapStringToEnum = new java.util.HashMap<String, MorphAspect>();
        UNDEFINED = new MorphAspect((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PERFECTIVE = new MorphAspect((short)((short)1), "PERFECTIVE");
        mapIntToEnum.put(PERFECTIVE.value(), PERFECTIVE);
        mapStringToEnum.put(PERFECTIVE.m_str.toUpperCase(), PERFECTIVE);
        IMPERFECTIVE = new MorphAspect((short)((short)2), "IMPERFECTIVE");
        mapIntToEnum.put(IMPERFECTIVE.value(), IMPERFECTIVE);
        mapStringToEnum.put(IMPERFECTIVE.m_str.toUpperCase(), IMPERFECTIVE);
        java.util.Collection<MorphAspect> col = mapIntToEnum.values();
        m_Values = new MorphAspect[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
