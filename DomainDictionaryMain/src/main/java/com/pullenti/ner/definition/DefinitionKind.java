/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition;

/**
 * Тип тезиса
 */
public class DefinitionKind implements Comparable<DefinitionKind> {

    /**
     * Непонятно
     */
    public static final DefinitionKind UNDEFINED; // 0

    /**
     * Просто утрерждение
     */
    public static final DefinitionKind ASSERTATION; // 1

    /**
     * Строгое определение
     */
    public static final DefinitionKind DEFINITION; // 2

    /**
     * Отрицание
     */
    public static final DefinitionKind NEGATION; // 3


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DefinitionKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DefinitionKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DefinitionKind> mapIntToEnum; 
    private static java.util.HashMap<String, DefinitionKind> mapStringToEnum; 
    private static DefinitionKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DefinitionKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DefinitionKind item = new DefinitionKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DefinitionKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DefinitionKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DefinitionKind>();
        mapStringToEnum = new java.util.HashMap<String, DefinitionKind>();
        UNDEFINED = new DefinitionKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ASSERTATION = new DefinitionKind(1, "ASSERTATION");
        mapIntToEnum.put(ASSERTATION.value(), ASSERTATION);
        mapStringToEnum.put(ASSERTATION.m_str.toUpperCase(), ASSERTATION);
        DEFINITION = new DefinitionKind(2, "DEFINITION");
        mapIntToEnum.put(DEFINITION.value(), DEFINITION);
        mapStringToEnum.put(DEFINITION.m_str.toUpperCase(), DEFINITION);
        NEGATION = new DefinitionKind(3, "NEGATION");
        mapIntToEnum.put(NEGATION.value(), NEGATION);
        mapStringToEnum.put(NEGATION.m_str.toUpperCase(), NEGATION);
        java.util.Collection<DefinitionKind> col = mapIntToEnum.values();
        m_Values = new DefinitionKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
