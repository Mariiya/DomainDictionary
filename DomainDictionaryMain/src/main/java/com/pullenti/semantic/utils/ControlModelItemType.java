/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Тип элемента модели управления
 */
public class ControlModelItemType implements Comparable<ControlModelItemType> {

    public static final ControlModelItemType UNDEFINED; // 0

    /**
     * Конкретное слово (не относится ко всем остальным)
     */
    public static final ControlModelItemType WORD; // 1

    /**
     * Все глаголы (не Reflexive)
     */
    public static final ControlModelItemType VERB; // 2

    /**
     * Возвратные глаголы и страдательный залог
     */
    public static final ControlModelItemType REFLEXIVE; // 3

    /**
     * Существительное, которое можно отглаголить
     */
    public static final ControlModelItemType NOUN; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ControlModelItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ControlModelItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ControlModelItemType> mapIntToEnum; 
    private static java.util.HashMap<String, ControlModelItemType> mapStringToEnum; 
    private static ControlModelItemType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static ControlModelItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ControlModelItemType item = new ControlModelItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ControlModelItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static ControlModelItemType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, ControlModelItemType>();
        mapStringToEnum = new java.util.HashMap<String, ControlModelItemType>();
        UNDEFINED = new ControlModelItemType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        WORD = new ControlModelItemType(1, "WORD");
        mapIntToEnum.put(WORD.value(), WORD);
        mapStringToEnum.put(WORD.m_str.toUpperCase(), WORD);
        VERB = new ControlModelItemType(2, "VERB");
        mapIntToEnum.put(VERB.value(), VERB);
        mapStringToEnum.put(VERB.m_str.toUpperCase(), VERB);
        REFLEXIVE = new ControlModelItemType(3, "REFLEXIVE");
        mapIntToEnum.put(REFLEXIVE.value(), REFLEXIVE);
        mapStringToEnum.put(REFLEXIVE.m_str.toUpperCase(), REFLEXIVE);
        NOUN = new ControlModelItemType(4, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        java.util.Collection<ControlModelItemType> col = mapIntToEnum.values();
        m_Values = new ControlModelItemType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
