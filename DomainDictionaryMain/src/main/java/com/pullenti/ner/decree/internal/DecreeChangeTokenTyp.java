/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class DecreeChangeTokenTyp implements Comparable<DecreeChangeTokenTyp> {

    public static final DecreeChangeTokenTyp UNDEFINED; // 0

    public static final DecreeChangeTokenTyp STARTMULTU; // 1

    public static final DecreeChangeTokenTyp STARTSINGLE; // 2

    public static final DecreeChangeTokenTyp SINGLE; // 3

    public static final DecreeChangeTokenTyp ACTION; // 4

    public static final DecreeChangeTokenTyp VALUE; // 5

    public static final DecreeChangeTokenTyp AFTERVALUE; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeChangeTokenTyp(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeChangeTokenTyp v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeChangeTokenTyp> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeChangeTokenTyp> mapStringToEnum; 
    private static DecreeChangeTokenTyp[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DecreeChangeTokenTyp of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeChangeTokenTyp item = new DecreeChangeTokenTyp(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeChangeTokenTyp of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DecreeChangeTokenTyp[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DecreeChangeTokenTyp>();
        mapStringToEnum = new java.util.HashMap<String, DecreeChangeTokenTyp>();
        UNDEFINED = new DecreeChangeTokenTyp(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        STARTMULTU = new DecreeChangeTokenTyp(1, "STARTMULTU");
        mapIntToEnum.put(STARTMULTU.value(), STARTMULTU);
        mapStringToEnum.put(STARTMULTU.m_str.toUpperCase(), STARTMULTU);
        STARTSINGLE = new DecreeChangeTokenTyp(2, "STARTSINGLE");
        mapIntToEnum.put(STARTSINGLE.value(), STARTSINGLE);
        mapStringToEnum.put(STARTSINGLE.m_str.toUpperCase(), STARTSINGLE);
        SINGLE = new DecreeChangeTokenTyp(3, "SINGLE");
        mapIntToEnum.put(SINGLE.value(), SINGLE);
        mapStringToEnum.put(SINGLE.m_str.toUpperCase(), SINGLE);
        ACTION = new DecreeChangeTokenTyp(4, "ACTION");
        mapIntToEnum.put(ACTION.value(), ACTION);
        mapStringToEnum.put(ACTION.m_str.toUpperCase(), ACTION);
        VALUE = new DecreeChangeTokenTyp(5, "VALUE");
        mapIntToEnum.put(VALUE.value(), VALUE);
        mapStringToEnum.put(VALUE.m_str.toUpperCase(), VALUE);
        AFTERVALUE = new DecreeChangeTokenTyp(6, "AFTERVALUE");
        mapIntToEnum.put(AFTERVALUE.value(), AFTERVALUE);
        mapStringToEnum.put(AFTERVALUE.m_str.toUpperCase(), AFTERVALUE);
        java.util.Collection<DecreeChangeTokenTyp> col = mapIntToEnum.values();
        m_Values = new DecreeChangeTokenTyp[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
