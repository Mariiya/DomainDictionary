/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Для английских глаголов
 * 
 */
public class MorphFinite implements Comparable<MorphFinite> {

    public static final MorphFinite UNDEFINED; // (short)0

    public static final MorphFinite FINITE; // (short)1

    public static final MorphFinite INFINITIVE; // (short)2

    public static final MorphFinite PARTICIPLE; // (short)4

    public static final MorphFinite GERUND; // (short)8


    public short value() { return m_val; }
    private short m_val;
    private String m_str;
    private MorphFinite(short val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphFinite v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Short, MorphFinite> mapIntToEnum; 
    private static java.util.HashMap<String, MorphFinite> mapStringToEnum; 
    private static MorphFinite[] m_Values; 
    private static java.util.Collection<Short> m_Keys; 
    public static MorphFinite of(short val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphFinite item = new MorphFinite(val, ((Short)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphFinite of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Short) return m_Keys.contains((Short)val); 
        return false; 
    }
    public static MorphFinite[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Short, MorphFinite>();
        mapStringToEnum = new java.util.HashMap<String, MorphFinite>();
        UNDEFINED = new MorphFinite((short)((short)0), "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        FINITE = new MorphFinite((short)((short)1), "FINITE");
        mapIntToEnum.put(FINITE.value(), FINITE);
        mapStringToEnum.put(FINITE.m_str.toUpperCase(), FINITE);
        INFINITIVE = new MorphFinite((short)((short)2), "INFINITIVE");
        mapIntToEnum.put(INFINITIVE.value(), INFINITIVE);
        mapStringToEnum.put(INFINITIVE.m_str.toUpperCase(), INFINITIVE);
        PARTICIPLE = new MorphFinite((short)((short)4), "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        GERUND = new MorphFinite((short)((short)8), "GERUND");
        mapIntToEnum.put(GERUND.value(), GERUND);
        mapStringToEnum.put(GERUND.m_str.toUpperCase(), GERUND);
        java.util.Collection<MorphFinite> col = mapIntToEnum.values();
        m_Values = new MorphFinite[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Short>(mapIntToEnum.keySet());
    }
}
