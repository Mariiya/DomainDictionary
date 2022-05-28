/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Время (для глаголов)
 * Время
 */
public class MorphTense implements Comparable<MorphTense> {

    /**
     * Неопределено
     */
    public static final MorphTense UNDEFINED; // (short)0

    /**
     * Прошлое
     */
    public static final MorphTense PAST; // (short)1

    /**
     * Настоящее
     */
    public static final MorphTense PRESENT; // (short)2

    /**
     * Будущее
     */
    public static final MorphTense FUTURE; // (short)4


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphTense(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphTense v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphTense> mapIntToEnum; 
    private static java.util.HashMap<String, MorphTense> mapStringToEnum; 
    private static MorphTense[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphTense of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphTense item = new MorphTense(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphTense of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphTense[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphTense>();
        mapStringToEnum = new java.util.HashMap<String, MorphTense>();
        UNDEFINED = new MorphTense((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PAST = new MorphTense((short)((short)1), "PAST");
        mapIntToEnum.put(PAST.value(), PAST);
        mapStringToEnum.put(PAST.m_str.toUpperCase(), PAST);
        PRESENT = new MorphTense((short)((short)2), "PRESENT");
        mapIntToEnum.put(PRESENT.value(), PRESENT);
        mapStringToEnum.put(PRESENT.m_str.toUpperCase(), PRESENT);
        FUTURE = new MorphTense((short)((short)4), "FUTURE");
        mapIntToEnum.put(FUTURE.value(), FUTURE);
        mapStringToEnum.put(FUTURE.m_str.toUpperCase(), FUTURE);
        java.util.Collection<MorphTense> col = mapIntToEnum.values();
        m_Values = new MorphTense[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
