/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Форма
 */
public class MorphForm implements Comparable<MorphForm> {

    /**
     * Не определена
     */
    public static final MorphForm UNDEFINED; // (short)0

    /**
     * Краткая форма
     */
    public static final MorphForm SHORT; // (short)1

    /**
     * Синонимичная форма
     */
    public static final MorphForm SYNONYM; // (short)2


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphForm(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphForm v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphForm> mapIntToEnum; 
    private static java.util.HashMap<String, MorphForm> mapStringToEnum; 
    private static MorphForm[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphForm of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphForm item = new MorphForm(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphForm of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphForm[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphForm>();
        mapStringToEnum = new java.util.HashMap<String, MorphForm>();
        UNDEFINED = new MorphForm((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SHORT = new MorphForm((short)((short)1), "SHORT");
        mapIntToEnum.put(SHORT.value(), SHORT);
        mapStringToEnum.put(SHORT.m_str.toUpperCase(), SHORT);
        SYNONYM = new MorphForm((short)((short)2), "SYNONYM");
        mapIntToEnum.put(SYNONYM.value(), SYNONYM);
        mapStringToEnum.put(SYNONYM.m_str.toUpperCase(), SYNONYM);
        java.util.Collection<MorphForm> col = mapIntToEnum.values();
        m_Values = new MorphForm[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
