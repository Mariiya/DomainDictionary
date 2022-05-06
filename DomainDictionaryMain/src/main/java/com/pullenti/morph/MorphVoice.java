/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Залог (для глаголов)
 * Залог
 */
public class MorphVoice implements Comparable<MorphVoice> {

    /**
     * Неопределено
     */
    public static final MorphVoice UNDEFINED; // (short)0

    /**
     * Действительный
     */
    public static final MorphVoice ACTIVE; // (short)1

    /**
     * Страдательный
     */
    public static final MorphVoice PASSIVE; // (short)2

    /**
     * Средний
     */
    public static final MorphVoice MIDDLE; // (short)4


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphVoice(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphVoice v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphVoice> mapIntToEnum; 
    private static java.util.HashMap<String, MorphVoice> mapStringToEnum; 
    private static MorphVoice[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphVoice of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphVoice item = new MorphVoice(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphVoice of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphVoice[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphVoice>();
        mapStringToEnum = new java.util.HashMap<String, MorphVoice>();
        UNDEFINED = new MorphVoice((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ACTIVE = new MorphVoice((short)((short)1), "ACTIVE");
        mapIntToEnum.put(ACTIVE.value(), ACTIVE);
        mapStringToEnum.put(ACTIVE.m_str.toUpperCase(), ACTIVE);
        PASSIVE = new MorphVoice((short)((short)2), "PASSIVE");
        mapIntToEnum.put(PASSIVE.value(), PASSIVE);
        mapStringToEnum.put(PASSIVE.m_str.toUpperCase(), PASSIVE);
        MIDDLE = new MorphVoice((short)((short)4), "MIDDLE");
        mapIntToEnum.put(MIDDLE.value(), MIDDLE);
        mapStringToEnum.put(MIDDLE.m_str.toUpperCase(), MIDDLE);
        java.util.Collection<MorphVoice> col = mapIntToEnum.values();
        m_Values = new MorphVoice[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
