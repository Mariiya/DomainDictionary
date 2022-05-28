/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Абстрактные вопросы модели управления
 */
public class QuestionType implements Comparable<QuestionType> {

    /**
     * Обычный вопрос
     */
    public static final QuestionType UNDEFINED; // 0

    /**
     * Где
     */
    public static final QuestionType WHERE; // 1

    /**
     * Откуда
     */
    public static final QuestionType WHEREFROM; // 2

    /**
     * Куда
     */
    public static final QuestionType WHERETO; // 4

    /**
     * Когда
     */
    public static final QuestionType WHEN; // 8

    /**
     * Что делать (инфинитив за группой)
     */
    public static final QuestionType WHATTODO; // 0x10


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private QuestionType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(QuestionType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, QuestionType> mapIntToEnum; 
    private static java.util.HashMap<String, QuestionType> mapStringToEnum; 
    private static QuestionType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static QuestionType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        QuestionType item = new QuestionType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static QuestionType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static QuestionType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, QuestionType>();
        mapStringToEnum = new java.util.HashMap<String, QuestionType>();
        UNDEFINED = new QuestionType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        WHERE = new QuestionType(1, "WHERE");
        mapIntToEnum.put(WHERE.value(), WHERE);
        mapStringToEnum.put(WHERE.m_str.toUpperCase(), WHERE);
        WHEREFROM = new QuestionType(2, "WHEREFROM");
        mapIntToEnum.put(WHEREFROM.value(), WHEREFROM);
        mapStringToEnum.put(WHEREFROM.m_str.toUpperCase(), WHEREFROM);
        WHERETO = new QuestionType(4, "WHERETO");
        mapIntToEnum.put(WHERETO.value(), WHERETO);
        mapStringToEnum.put(WHERETO.m_str.toUpperCase(), WHERETO);
        WHEN = new QuestionType(8, "WHEN");
        mapIntToEnum.put(WHEN.value(), WHEN);
        mapStringToEnum.put(WHEN.m_str.toUpperCase(), WHEN);
        WHATTODO = new QuestionType(0x10, "WHATTODO");
        mapIntToEnum.put(WHATTODO.value(), WHATTODO);
        mapStringToEnum.put(WHATTODO.m_str.toUpperCase(), WHATTODO);
        java.util.Collection<QuestionType> col = mapIntToEnum.values();
        m_Values = new QuestionType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
