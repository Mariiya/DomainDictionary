/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class NGLinkType implements Comparable<NGLinkType> {

    public static final NGLinkType UNDEFINED; // 0

    public static final NGLinkType LIST; // 1

    public static final NGLinkType GENETIVE; // 2

    public static final NGLinkType NAME; // 3

    public static final NGLinkType AGENT; // 4

    public static final NGLinkType PACIENT; // 5

    public static final NGLinkType ACTANT; // 6

    public static final NGLinkType PARTICIPLE; // 7

    public static final NGLinkType ADVERB; // 8

    public static final NGLinkType BE; // 9


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NGLinkType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NGLinkType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NGLinkType> mapIntToEnum; 
    private static java.util.HashMap<String, NGLinkType> mapStringToEnum; 
    private static NGLinkType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NGLinkType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NGLinkType item = new NGLinkType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NGLinkType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NGLinkType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NGLinkType>();
        mapStringToEnum = new java.util.HashMap<String, NGLinkType>();
        UNDEFINED = new NGLinkType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        LIST = new NGLinkType(1, "LIST");
        mapIntToEnum.put(LIST.value(), LIST);
        mapStringToEnum.put(LIST.m_str.toUpperCase(), LIST);
        GENETIVE = new NGLinkType(2, "GENETIVE");
        mapIntToEnum.put(GENETIVE.value(), GENETIVE);
        mapStringToEnum.put(GENETIVE.m_str.toUpperCase(), GENETIVE);
        NAME = new NGLinkType(3, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        AGENT = new NGLinkType(4, "AGENT");
        mapIntToEnum.put(AGENT.value(), AGENT);
        mapStringToEnum.put(AGENT.m_str.toUpperCase(), AGENT);
        PACIENT = new NGLinkType(5, "PACIENT");
        mapIntToEnum.put(PACIENT.value(), PACIENT);
        mapStringToEnum.put(PACIENT.m_str.toUpperCase(), PACIENT);
        ACTANT = new NGLinkType(6, "ACTANT");
        mapIntToEnum.put(ACTANT.value(), ACTANT);
        mapStringToEnum.put(ACTANT.m_str.toUpperCase(), ACTANT);
        PARTICIPLE = new NGLinkType(7, "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        ADVERB = new NGLinkType(8, "ADVERB");
        mapIntToEnum.put(ADVERB.value(), ADVERB);
        mapStringToEnum.put(ADVERB.m_str.toUpperCase(), ADVERB);
        BE = new NGLinkType(9, "BE");
        mapIntToEnum.put(BE.value(), BE);
        mapStringToEnum.put(BE.m_str.toUpperCase(), BE);
        java.util.Collection<NGLinkType> col = mapIntToEnum.values();
        m_Values = new NGLinkType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
