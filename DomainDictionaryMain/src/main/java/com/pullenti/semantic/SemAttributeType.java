/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Типы семантических атрибутов
 */
public class SemAttributeType implements Comparable<SemAttributeType> {

    public static final SemAttributeType UNDEFINED; // 0

    /**
     * Очень
     */
    public static final SemAttributeType VERY; // 1

    /**
     * Уже
     */
    public static final SemAttributeType ALREADY; // 2

    /**
     * Ещё
     */
    public static final SemAttributeType STILL; // 3

    /**
     * Все
     */
    public static final SemAttributeType ALL; // 4

    /**
     * Любой, каждый
     */
    public static final SemAttributeType ANY; // 5

    /**
     * Некоторый
     */
    public static final SemAttributeType SOME; // 6

    /**
     * Один
     */
    public static final SemAttributeType ONE; // 7

    /**
     * Один из
     */
    public static final SemAttributeType ONEOF; // 8

    /**
     * Другой
     */
    public static final SemAttributeType OTHER; // 9

    /**
     * друг друга, один другого
     */
    public static final SemAttributeType EACHOTHER; // 10

    /**
     * Сам себя
     */
    public static final SemAttributeType HIMELF; // 11

    /**
     * Целиком, весь
     */
    public static final SemAttributeType WHOLE; // 12

    /**
     * Меньше
     */
    public static final SemAttributeType LESS; // 13

    /**
     * Больше
     */
    public static final SemAttributeType GREAT; // 14


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemAttributeType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemAttributeType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemAttributeType> mapIntToEnum; 
    private static java.util.HashMap<String, SemAttributeType> mapStringToEnum; 
    private static SemAttributeType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemAttributeType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemAttributeType item = new SemAttributeType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemAttributeType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemAttributeType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemAttributeType>();
        mapStringToEnum = new java.util.HashMap<String, SemAttributeType>();
        UNDEFINED = new SemAttributeType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        VERY = new SemAttributeType(1, "VERY");
        mapIntToEnum.put(VERY.value(), VERY);
        mapStringToEnum.put(VERY.m_str.toUpperCase(), VERY);
        ALREADY = new SemAttributeType(2, "ALREADY");
        mapIntToEnum.put(ALREADY.value(), ALREADY);
        mapStringToEnum.put(ALREADY.m_str.toUpperCase(), ALREADY);
        STILL = new SemAttributeType(3, "STILL");
        mapIntToEnum.put(STILL.value(), STILL);
        mapStringToEnum.put(STILL.m_str.toUpperCase(), STILL);
        ALL = new SemAttributeType(4, "ALL");
        mapIntToEnum.put(ALL.value(), ALL);
        mapStringToEnum.put(ALL.m_str.toUpperCase(), ALL);
        ANY = new SemAttributeType(5, "ANY");
        mapIntToEnum.put(ANY.value(), ANY);
        mapStringToEnum.put(ANY.m_str.toUpperCase(), ANY);
        SOME = new SemAttributeType(6, "SOME");
        mapIntToEnum.put(SOME.value(), SOME);
        mapStringToEnum.put(SOME.m_str.toUpperCase(), SOME);
        ONE = new SemAttributeType(7, "ONE");
        mapIntToEnum.put(ONE.value(), ONE);
        mapStringToEnum.put(ONE.m_str.toUpperCase(), ONE);
        ONEOF = new SemAttributeType(8, "ONEOF");
        mapIntToEnum.put(ONEOF.value(), ONEOF);
        mapStringToEnum.put(ONEOF.m_str.toUpperCase(), ONEOF);
        OTHER = new SemAttributeType(9, "OTHER");
        mapIntToEnum.put(OTHER.value(), OTHER);
        mapStringToEnum.put(OTHER.m_str.toUpperCase(), OTHER);
        EACHOTHER = new SemAttributeType(10, "EACHOTHER");
        mapIntToEnum.put(EACHOTHER.value(), EACHOTHER);
        mapStringToEnum.put(EACHOTHER.m_str.toUpperCase(), EACHOTHER);
        HIMELF = new SemAttributeType(11, "HIMELF");
        mapIntToEnum.put(HIMELF.value(), HIMELF);
        mapStringToEnum.put(HIMELF.m_str.toUpperCase(), HIMELF);
        WHOLE = new SemAttributeType(12, "WHOLE");
        mapIntToEnum.put(WHOLE.value(), WHOLE);
        mapStringToEnum.put(WHOLE.m_str.toUpperCase(), WHOLE);
        LESS = new SemAttributeType(13, "LESS");
        mapIntToEnum.put(LESS.value(), LESS);
        mapStringToEnum.put(LESS.m_str.toUpperCase(), LESS);
        GREAT = new SemAttributeType(14, "GREAT");
        mapIntToEnum.put(GREAT.value(), GREAT);
        mapStringToEnum.put(GREAT.m_str.toUpperCase(), GREAT);
        java.util.Collection<SemAttributeType> col = mapIntToEnum.values();
        m_Values = new SemAttributeType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
