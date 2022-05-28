/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Атрибуты функции CanBeEqualsEx класса MiscHelper. Битовая маска.
 * Атрибуты сравнения
 */
public class CanBeEqualsAttr implements Comparable<CanBeEqualsAttr> {

    public static final CanBeEqualsAttr NO; // 0

    /**
     * Игнорировать небуквенные символы (они как бы выбрасываются)
     */
    public static final CanBeEqualsAttr IGNORENONLETTERS; // 1

    /**
     * Игнорировать регистр символов
     */
    public static final CanBeEqualsAttr IGNOREUPPERCASE; // 2

    /**
     * После первого существительного слова должны полностью совпадать 
     * (иначе совпадение с точностью до морфологии)
     */
    public static final CanBeEqualsAttr CHECKMORPHEQUAFTERFIRSTNOUN; // 4

    /**
     * Даже если указано IgnoreNonletters, кавычки проверять!
     */
    public static final CanBeEqualsAttr USEBRACKETS; // 8

    /**
     * Игнорировать регистр символов только первого слова
     */
    public static final CanBeEqualsAttr IGNOREUPPERCASEFIRSTWORD; // 0x10

    /**
     * Первое слово может быть короче (то есть второе должно начинаться на первое слово)
     */
    public static final CanBeEqualsAttr FIRSTCANBESHORTER; // 0x20


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CanBeEqualsAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(CanBeEqualsAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CanBeEqualsAttr> mapIntToEnum; 
    private static java.util.HashMap<String, CanBeEqualsAttr> mapStringToEnum; 
    private static CanBeEqualsAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static CanBeEqualsAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CanBeEqualsAttr item = new CanBeEqualsAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CanBeEqualsAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static CanBeEqualsAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, CanBeEqualsAttr>();
        mapStringToEnum = new java.util.HashMap<String, CanBeEqualsAttr>();
        NO = new CanBeEqualsAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        IGNORENONLETTERS = new CanBeEqualsAttr(1, "IGNORENONLETTERS");
        mapIntToEnum.put(IGNORENONLETTERS.value(), IGNORENONLETTERS);
        mapStringToEnum.put(IGNORENONLETTERS.m_str.toUpperCase(), IGNORENONLETTERS);
        IGNOREUPPERCASE = new CanBeEqualsAttr(2, "IGNOREUPPERCASE");
        mapIntToEnum.put(IGNOREUPPERCASE.value(), IGNOREUPPERCASE);
        mapStringToEnum.put(IGNOREUPPERCASE.m_str.toUpperCase(), IGNOREUPPERCASE);
        CHECKMORPHEQUAFTERFIRSTNOUN = new CanBeEqualsAttr(4, "CHECKMORPHEQUAFTERFIRSTNOUN");
        mapIntToEnum.put(CHECKMORPHEQUAFTERFIRSTNOUN.value(), CHECKMORPHEQUAFTERFIRSTNOUN);
        mapStringToEnum.put(CHECKMORPHEQUAFTERFIRSTNOUN.m_str.toUpperCase(), CHECKMORPHEQUAFTERFIRSTNOUN);
        USEBRACKETS = new CanBeEqualsAttr(8, "USEBRACKETS");
        mapIntToEnum.put(USEBRACKETS.value(), USEBRACKETS);
        mapStringToEnum.put(USEBRACKETS.m_str.toUpperCase(), USEBRACKETS);
        IGNOREUPPERCASEFIRSTWORD = new CanBeEqualsAttr(0x10, "IGNOREUPPERCASEFIRSTWORD");
        mapIntToEnum.put(IGNOREUPPERCASEFIRSTWORD.value(), IGNOREUPPERCASEFIRSTWORD);
        mapStringToEnum.put(IGNOREUPPERCASEFIRSTWORD.m_str.toUpperCase(), IGNOREUPPERCASEFIRSTWORD);
        FIRSTCANBESHORTER = new CanBeEqualsAttr(0x20, "FIRSTCANBESHORTER");
        mapIntToEnum.put(FIRSTCANBESHORTER.value(), FIRSTCANBESHORTER);
        mapStringToEnum.put(FIRSTCANBESHORTER.m_str.toUpperCase(), FIRSTCANBESHORTER);
        java.util.Collection<CanBeEqualsAttr> col = mapIntToEnum.values();
        m_Values = new CanBeEqualsAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
