/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class DelimType implements Comparable<DelimType> {

    public static final DelimType UNDEFINED; // 0

    public static final DelimType AND; // 1

    public static final DelimType BUT; // 2

    public static final DelimType IF; // 4

    public static final DelimType THEN; // 8

    public static final DelimType ELSE; // 0x10

    public static final DelimType BECAUSE; // 0x20

    public static final DelimType FOR; // 0x40

    public static final DelimType WHAT; // 0x80


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DelimType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DelimType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DelimType> mapIntToEnum; 
    private static java.util.HashMap<String, DelimType> mapStringToEnum; 
    private static DelimType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DelimType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DelimType item = new DelimType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DelimType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DelimType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DelimType>();
        mapStringToEnum = new java.util.HashMap<String, DelimType>();
        UNDEFINED = new DelimType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        AND = new DelimType(1, "AND");
        mapIntToEnum.put(AND.value(), AND);
        mapStringToEnum.put(AND.m_str.toUpperCase(), AND);
        BUT = new DelimType(2, "BUT");
        mapIntToEnum.put(BUT.value(), BUT);
        mapStringToEnum.put(BUT.m_str.toUpperCase(), BUT);
        IF = new DelimType(4, "IF");
        mapIntToEnum.put(IF.value(), IF);
        mapStringToEnum.put(IF.m_str.toUpperCase(), IF);
        THEN = new DelimType(8, "THEN");
        mapIntToEnum.put(THEN.value(), THEN);
        mapStringToEnum.put(THEN.m_str.toUpperCase(), THEN);
        ELSE = new DelimType(0x10, "ELSE");
        mapIntToEnum.put(ELSE.value(), ELSE);
        mapStringToEnum.put(ELSE.m_str.toUpperCase(), ELSE);
        BECAUSE = new DelimType(0x20, "BECAUSE");
        mapIntToEnum.put(BECAUSE.value(), BECAUSE);
        mapStringToEnum.put(BECAUSE.m_str.toUpperCase(), BECAUSE);
        FOR = new DelimType(0x40, "FOR");
        mapIntToEnum.put(FOR.value(), FOR);
        mapStringToEnum.put(FOR.m_str.toUpperCase(), FOR);
        WHAT = new DelimType(0x80, "WHAT");
        mapIntToEnum.put(WHAT.value(), WHAT);
        mapStringToEnum.put(WHAT.m_str.toUpperCase(), WHAT);
        java.util.Collection<DelimType> col = mapIntToEnum.values();
        m_Values = new DelimType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
