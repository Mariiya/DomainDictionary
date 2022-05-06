/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.keyword;

/**
 * Тип ключевой комбинации
 */
public class KeywordType implements Comparable<KeywordType> {

    /**
     * Неопределён
     */
    public static final KeywordType UNDEFINED; // 0

    /**
     * Объект (именная группа)
     */
    public static final KeywordType OBJECT; // 1

    /**
     * Именованная сущность
     */
    public static final KeywordType REFERENT; // 2

    /**
     * Предикат (глагол)
     */
    public static final KeywordType PREDICATE; // 3

    /**
     * Автоаннотация всего текста
     */
    public static final KeywordType ANNOTATION; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private KeywordType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(KeywordType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, KeywordType> mapIntToEnum; 
    private static java.util.HashMap<String, KeywordType> mapStringToEnum; 
    private static KeywordType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static KeywordType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        KeywordType item = new KeywordType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static KeywordType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static KeywordType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, KeywordType>();
        mapStringToEnum = new java.util.HashMap<String, KeywordType>();
        UNDEFINED = new KeywordType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        OBJECT = new KeywordType(1, "OBJECT");
        mapIntToEnum.put(OBJECT.value(), OBJECT);
        mapStringToEnum.put(OBJECT.m_str.toUpperCase(), OBJECT);
        REFERENT = new KeywordType(2, "REFERENT");
        mapIntToEnum.put(REFERENT.value(), REFERENT);
        mapStringToEnum.put(REFERENT.m_str.toUpperCase(), REFERENT);
        PREDICATE = new KeywordType(3, "PREDICATE");
        mapIntToEnum.put(PREDICATE.value(), PREDICATE);
        mapStringToEnum.put(PREDICATE.m_str.toUpperCase(), PREDICATE);
        ANNOTATION = new KeywordType(4, "ANNOTATION");
        mapIntToEnum.put(ANNOTATION.value(), ANNOTATION);
        mapStringToEnum.put(ANNOTATION.m_str.toUpperCase(), ANNOTATION);
        java.util.Collection<KeywordType> col = mapIntToEnum.values();
        m_Values = new KeywordType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
