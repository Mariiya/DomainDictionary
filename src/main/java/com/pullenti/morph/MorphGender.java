/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Род (мужской-средний-женский)
 * Род
 */
public class MorphGender implements Comparable<MorphGender> {

    /**
     * Неопределён
     */
    public static final MorphGender UNDEFINED; // (short)0

    /**
     * Мужской
     */
    public static final MorphGender MASCULINE; // (short)1

    /**
     * Женский
     */
    public static final MorphGender FEMINIE; // (short)2

    /**
     * Средний
     */
    public static final MorphGender NEUTER; // (short)4


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphGender(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphGender v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphGender> mapIntToEnum; 
    private static java.util.HashMap<String, MorphGender> mapStringToEnum; 
    private static MorphGender[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphGender of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphGender item = new MorphGender(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphGender of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphGender[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphGender>();
        mapStringToEnum = new java.util.HashMap<String, MorphGender>();
        UNDEFINED = new MorphGender((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        MASCULINE = new MorphGender((short)((short)1), "MASCULINE");
        mapIntToEnum.put(MASCULINE.value(), MASCULINE);
        mapStringToEnum.put(MASCULINE.m_str.toUpperCase(), MASCULINE);
        FEMINIE = new MorphGender((short)((short)2), "FEMINIE");
        mapIntToEnum.put(FEMINIE.value(), FEMINIE);
        mapStringToEnum.put(FEMINIE.m_str.toUpperCase(), FEMINIE);
        NEUTER = new MorphGender((short)((short)4), "NEUTER");
        mapIntToEnum.put(NEUTER.value(), NEUTER);
        mapStringToEnum.put(NEUTER.m_str.toUpperCase(), NEUTER);
        java.util.Collection<MorphGender> col = mapIntToEnum.values();
        m_Values = new MorphGender[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
