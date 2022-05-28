/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person;

/**
 * Категории свойств персон
 */
public class PersonPropertyKind implements Comparable<PersonPropertyKind> {

    /**
     * Неопределена
     */
    public static final PersonPropertyKind UNDEFINED; // 0

    /**
     * Начальник
     */
    public static final PersonPropertyKind BOSS; // 1

    /**
     * Вельможные и духовные особы
     */
    public static final PersonPropertyKind KING; // 2

    /**
     * Родственники
     */
    public static final PersonPropertyKind KIN; // 3

    /**
     * Воинское звание
     */
    public static final PersonPropertyKind MILITARYRANK; // 4

    /**
     * Национальность
     */
    public static final PersonPropertyKind NATIONALITY; // 5


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PersonPropertyKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PersonPropertyKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PersonPropertyKind> mapIntToEnum; 
    private static java.util.HashMap<String, PersonPropertyKind> mapStringToEnum; 
    private static PersonPropertyKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static PersonPropertyKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PersonPropertyKind item = new PersonPropertyKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PersonPropertyKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static PersonPropertyKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, PersonPropertyKind>();
        mapStringToEnum = new java.util.HashMap<String, PersonPropertyKind>();
        UNDEFINED = new PersonPropertyKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        BOSS = new PersonPropertyKind(1, "BOSS");
        mapIntToEnum.put(BOSS.value(), BOSS);
        mapStringToEnum.put(BOSS.m_str.toUpperCase(), BOSS);
        KING = new PersonPropertyKind(2, "KING");
        mapIntToEnum.put(KING.value(), KING);
        mapStringToEnum.put(KING.m_str.toUpperCase(), KING);
        KIN = new PersonPropertyKind(3, "KIN");
        mapIntToEnum.put(KIN.value(), KIN);
        mapStringToEnum.put(KIN.m_str.toUpperCase(), KIN);
        MILITARYRANK = new PersonPropertyKind(4, "MILITARYRANK");
        mapIntToEnum.put(MILITARYRANK.value(), MILITARYRANK);
        mapStringToEnum.put(MILITARYRANK.m_str.toUpperCase(), MILITARYRANK);
        NATIONALITY = new PersonPropertyKind(5, "NATIONALITY");
        mapIntToEnum.put(NATIONALITY.value(), NATIONALITY);
        mapStringToEnum.put(NATIONALITY.m_str.toUpperCase(), NATIONALITY);
        java.util.Collection<PersonPropertyKind> col = mapIntToEnum.values();
        m_Values = new PersonPropertyKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
