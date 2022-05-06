/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Число (единственное-множественное)
 * Число
 */
public class MorphNumber implements Comparable<MorphNumber> {

    /**
     * Неопределено
     */
    public static final MorphNumber UNDEFINED; // (short)0

    /**
     * Единственное
     */
    public static final MorphNumber SINGULAR; // (short)1

    /**
     * Множественное
     */
    public static final MorphNumber PLURAL; // (short)2


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphNumber(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphNumber v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphNumber> mapIntToEnum; 
    private static java.util.HashMap<String, MorphNumber> mapStringToEnum; 
    private static MorphNumber[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphNumber of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphNumber item = new MorphNumber(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphNumber of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphNumber[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphNumber>();
        mapStringToEnum = new java.util.HashMap<String, MorphNumber>();
        UNDEFINED = new MorphNumber((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SINGULAR = new MorphNumber((short)((short)1), "SINGULAR");
        mapIntToEnum.put(SINGULAR.value(), SINGULAR);
        mapStringToEnum.put(SINGULAR.m_str.toUpperCase(), SINGULAR);
        PLURAL = new MorphNumber((short)((short)2), "PLURAL");
        mapIntToEnum.put(PLURAL.value(), PLURAL);
        mapStringToEnum.put(PLURAL.m_str.toUpperCase(), PLURAL);
        java.util.Collection<MorphNumber> col = mapIntToEnum.values();
        m_Values = new MorphNumber[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
