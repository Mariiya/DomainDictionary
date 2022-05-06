/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Лицо (1, 2, 3)
 * Лицо
 */
public class MorphPerson implements Comparable<MorphPerson> {

    /**
     * Неопределено
     */
    public static final MorphPerson UNDEFINED; // (short)0

    /**
     * Первое
     */
    public static final MorphPerson FIRST; // (short)1

    /**
     * Второе
     */
    public static final MorphPerson SECOND; // (short)2

    /**
     * Третье
     */
    public static final MorphPerson THIRD; // (short)4


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphPerson(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphPerson v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphPerson> mapIntToEnum; 
    private static java.util.HashMap<String, MorphPerson> mapStringToEnum; 
    private static MorphPerson[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphPerson of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphPerson item = new MorphPerson(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphPerson of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphPerson[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphPerson>();
        mapStringToEnum = new java.util.HashMap<String, MorphPerson>();
        UNDEFINED = new MorphPerson((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        FIRST = new MorphPerson((short)((short)1), "FIRST");
        mapIntToEnum.put(FIRST.value(), FIRST);
        mapStringToEnum.put(FIRST.m_str.toUpperCase(), FIRST);
        SECOND = new MorphPerson((short)((short)2), "SECOND");
        mapIntToEnum.put(SECOND.value(), SECOND);
        mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
        THIRD = new MorphPerson((short)((short)4), "THIRD");
        mapIntToEnum.put(THIRD.value(), THIRD);
        mapStringToEnum.put(THIRD.m_str.toUpperCase(), THIRD);
        java.util.Collection<MorphPerson> col = mapIntToEnum.values();
        m_Values = new MorphPerson[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
