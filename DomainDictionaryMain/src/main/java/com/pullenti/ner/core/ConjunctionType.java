/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Типы союзов и служебных слов
 */
public class ConjunctionType implements Comparable<ConjunctionType> {

    /**
     * Неопределено
     */
    public static final ConjunctionType UNDEFINED; // 0

    /**
     * Запятая
     */
    public static final ConjunctionType COMMA; // 1

    /**
     * И
     */
    public static final ConjunctionType AND; // 2

    /**
     * Или
     */
    public static final ConjunctionType OR; // 3

    /**
     * ни ... ни ...
     */
    public static final ConjunctionType NOT; // 4

    /**
     * Но
     */
    public static final ConjunctionType BUT; // 5

    /**
     * Если
     */
    public static final ConjunctionType IF; // 6

    /**
     * То
     */
    public static final ConjunctionType THEN; // 7

    /**
     * Иначе
     */
    public static final ConjunctionType ELSE; // 8

    /**
     * Когда
     */
    public static final ConjunctionType WHEN; // 9

    /**
     * Потому что
     */
    public static final ConjunctionType BECAUSE; // 10

    /**
     * Включая
     */
    public static final ConjunctionType INCLUDE; // 11

    /**
     * Исключая
     */
    public static final ConjunctionType EXCEPT; // 12


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ConjunctionType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ConjunctionType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ConjunctionType> mapIntToEnum; 
    private static java.util.HashMap<String, ConjunctionType> mapStringToEnum; 
    private static ConjunctionType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static ConjunctionType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ConjunctionType item = new ConjunctionType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ConjunctionType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static ConjunctionType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, ConjunctionType>();
        mapStringToEnum = new java.util.HashMap<String, ConjunctionType>();
        UNDEFINED = new ConjunctionType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        COMMA = new ConjunctionType(1, "COMMA");
        mapIntToEnum.put(COMMA.value(), COMMA);
        mapStringToEnum.put(COMMA.m_str.toUpperCase(), COMMA);
        AND = new ConjunctionType(2, "AND");
        mapIntToEnum.put(AND.value(), AND);
        mapStringToEnum.put(AND.m_str.toUpperCase(), AND);
        OR = new ConjunctionType(3, "OR");
        mapIntToEnum.put(OR.value(), OR);
        mapStringToEnum.put(OR.m_str.toUpperCase(), OR);
        NOT = new ConjunctionType(4, "NOT");
        mapIntToEnum.put(NOT.value(), NOT);
        mapStringToEnum.put(NOT.m_str.toUpperCase(), NOT);
        BUT = new ConjunctionType(5, "BUT");
        mapIntToEnum.put(BUT.value(), BUT);
        mapStringToEnum.put(BUT.m_str.toUpperCase(), BUT);
        IF = new ConjunctionType(6, "IF");
        mapIntToEnum.put(IF.value(), IF);
        mapStringToEnum.put(IF.m_str.toUpperCase(), IF);
        THEN = new ConjunctionType(7, "THEN");
        mapIntToEnum.put(THEN.value(), THEN);
        mapStringToEnum.put(THEN.m_str.toUpperCase(), THEN);
        ELSE = new ConjunctionType(8, "ELSE");
        mapIntToEnum.put(ELSE.value(), ELSE);
        mapStringToEnum.put(ELSE.m_str.toUpperCase(), ELSE);
        WHEN = new ConjunctionType(9, "WHEN");
        mapIntToEnum.put(WHEN.value(), WHEN);
        mapStringToEnum.put(WHEN.m_str.toUpperCase(), WHEN);
        BECAUSE = new ConjunctionType(10, "BECAUSE");
        mapIntToEnum.put(BECAUSE.value(), BECAUSE);
        mapStringToEnum.put(BECAUSE.m_str.toUpperCase(), BECAUSE);
        INCLUDE = new ConjunctionType(11, "INCLUDE");
        mapIntToEnum.put(INCLUDE.value(), INCLUDE);
        mapStringToEnum.put(INCLUDE.m_str.toUpperCase(), INCLUDE);
        EXCEPT = new ConjunctionType(12, "EXCEPT");
        mapIntToEnum.put(EXCEPT.value(), EXCEPT);
        mapStringToEnum.put(EXCEPT.m_str.toUpperCase(), EXCEPT);
        java.util.Collection<ConjunctionType> col = mapIntToEnum.values();
        m_Values = new ConjunctionType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
