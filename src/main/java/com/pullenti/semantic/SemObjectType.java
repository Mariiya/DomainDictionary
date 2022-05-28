/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Тип семантического объекта
 */
public class SemObjectType implements Comparable<SemObjectType> {

    public static final SemObjectType UNDEFINED; // 0

    /**
     * Существительное (в широком смысле, например, сущности)
     */
    public static final SemObjectType NOUN; // 1

    /**
     * Прилагательное
     */
    public static final SemObjectType ADJECTIVE; // 2

    /**
     * Предикат (глагол)
     */
    public static final SemObjectType VERB; // 3

    /**
     * Причастие или деепричастие
     */
    public static final SemObjectType PARTICIPLE; // 4

    /**
     * Наречие
     */
    public static final SemObjectType ADVERB; // 5

    /**
     * Местоимение
     */
    public static final SemObjectType PRONOUN; // 6

    /**
     * Личное местоимение
     */
    public static final SemObjectType PERSONALPRONOUN; // 7

    /**
     * Вопрос
     */
    public static final SemObjectType QUESTION; // 8


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SemObjectType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SemObjectType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SemObjectType> mapIntToEnum; 
    private static java.util.HashMap<String, SemObjectType> mapStringToEnum; 
    private static SemObjectType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SemObjectType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SemObjectType item = new SemObjectType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SemObjectType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SemObjectType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SemObjectType>();
        mapStringToEnum = new java.util.HashMap<String, SemObjectType>();
        UNDEFINED = new SemObjectType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        NOUN = new SemObjectType(1, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        ADJECTIVE = new SemObjectType(2, "ADJECTIVE");
        mapIntToEnum.put(ADJECTIVE.value(), ADJECTIVE);
        mapStringToEnum.put(ADJECTIVE.m_str.toUpperCase(), ADJECTIVE);
        VERB = new SemObjectType(3, "VERB");
        mapIntToEnum.put(VERB.value(), VERB);
        mapStringToEnum.put(VERB.m_str.toUpperCase(), VERB);
        PARTICIPLE = new SemObjectType(4, "PARTICIPLE");
        mapIntToEnum.put(PARTICIPLE.value(), PARTICIPLE);
        mapStringToEnum.put(PARTICIPLE.m_str.toUpperCase(), PARTICIPLE);
        ADVERB = new SemObjectType(5, "ADVERB");
        mapIntToEnum.put(ADVERB.value(), ADVERB);
        mapStringToEnum.put(ADVERB.m_str.toUpperCase(), ADVERB);
        PRONOUN = new SemObjectType(6, "PRONOUN");
        mapIntToEnum.put(PRONOUN.value(), PRONOUN);
        mapStringToEnum.put(PRONOUN.m_str.toUpperCase(), PRONOUN);
        PERSONALPRONOUN = new SemObjectType(7, "PERSONALPRONOUN");
        mapIntToEnum.put(PERSONALPRONOUN.value(), PERSONALPRONOUN);
        mapStringToEnum.put(PERSONALPRONOUN.m_str.toUpperCase(), PERSONALPRONOUN);
        QUESTION = new SemObjectType(8, "QUESTION");
        mapIntToEnum.put(QUESTION.value(), QUESTION);
        mapStringToEnum.put(QUESTION.m_str.toUpperCase(), QUESTION);
        java.util.Collection<SemObjectType> col = mapIntToEnum.values();
        m_Values = new SemObjectType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
