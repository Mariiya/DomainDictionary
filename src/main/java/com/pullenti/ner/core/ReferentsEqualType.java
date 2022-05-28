/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Атрибут сравнения сущностей (методом Referent.CanBeEquals)
 * Атрибут сравнения сущностей
 */
public class ReferentsEqualType implements Comparable<ReferentsEqualType> {

    /**
     * Сущности в рамках одного текста
     */
    public static final ReferentsEqualType WITHINONETEXT; // 0

    /**
     * Сущности из разных текстов
     */
    public static final ReferentsEqualType DIFFERENTTEXTS; // 1

    /**
     * Проверка для потенциального объединения сущностей
     */
    public static final ReferentsEqualType FORMERGING; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ReferentsEqualType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ReferentsEqualType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ReferentsEqualType> mapIntToEnum; 
    private static java.util.HashMap<String, ReferentsEqualType> mapStringToEnum; 
    private static ReferentsEqualType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static ReferentsEqualType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ReferentsEqualType item = new ReferentsEqualType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ReferentsEqualType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static ReferentsEqualType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, ReferentsEqualType>();
        mapStringToEnum = new java.util.HashMap<String, ReferentsEqualType>();
        WITHINONETEXT = new ReferentsEqualType(0, "WITHINONETEXT");
        mapIntToEnum.put(WITHINONETEXT.value(), WITHINONETEXT);
        mapStringToEnum.put(WITHINONETEXT.m_str.toUpperCase(), WITHINONETEXT);
        DIFFERENTTEXTS = new ReferentsEqualType(1, "DIFFERENTTEXTS");
        mapIntToEnum.put(DIFFERENTTEXTS.value(), DIFFERENTTEXTS);
        mapStringToEnum.put(DIFFERENTTEXTS.m_str.toUpperCase(), DIFFERENTTEXTS);
        FORMERGING = new ReferentsEqualType(2, "FORMERGING");
        mapIntToEnum.put(FORMERGING.value(), FORMERGING);
        mapStringToEnum.put(FORMERGING.m_str.toUpperCase(), FORMERGING);
        java.util.Collection<ReferentsEqualType> col = mapIntToEnum.values();
        m_Values = new ReferentsEqualType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
