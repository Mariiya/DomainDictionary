/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Наклонение (для глаголов)
 * Наклонение
 */
public class MorphMood implements Comparable<MorphMood> {

    /**
     * Неопределено
     */
    public static final MorphMood UNDEFINED; // (short)0

    /**
     * Изъявительное
     */
    public static final MorphMood INDICATIVE; // (short)1

    /**
     * Условное
     */
    public static final MorphMood SUBJUNCTIVE; // (short)2

    /**
     * Повелительное
     */
    public static final MorphMood IMPERATIVE; // (short)4


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphMood(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphMood v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphMood> mapIntToEnum; 
    private static java.util.HashMap<String, MorphMood> mapStringToEnum; 
    private static MorphMood[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphMood of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphMood item = new MorphMood(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphMood of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphMood[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphMood>();
        mapStringToEnum = new java.util.HashMap<String, MorphMood>();
        UNDEFINED = new MorphMood((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        INDICATIVE = new MorphMood((short)((short)1), "INDICATIVE");
        mapIntToEnum.put(INDICATIVE.value(), INDICATIVE);
        mapStringToEnum.put(INDICATIVE.m_str.toUpperCase(), INDICATIVE);
        SUBJUNCTIVE = new MorphMood((short)((short)2), "SUBJUNCTIVE");
        mapIntToEnum.put(SUBJUNCTIVE.value(), SUBJUNCTIVE);
        mapStringToEnum.put(SUBJUNCTIVE.m_str.toUpperCase(), SUBJUNCTIVE);
        IMPERATIVE = new MorphMood((short)((short)4), "IMPERATIVE");
        mapIntToEnum.put(IMPERATIVE.value(), IMPERATIVE);
        mapStringToEnum.put(IMPERATIVE.m_str.toUpperCase(), IMPERATIVE);
        java.util.Collection<MorphMood> col = mapIntToEnum.values();
        m_Values = new MorphMood[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
