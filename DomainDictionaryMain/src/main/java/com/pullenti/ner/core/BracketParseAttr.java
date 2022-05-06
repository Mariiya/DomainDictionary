/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Атрибуты выделения последовательности между скобок-кавычек. Битовая маска.
 * Атрибуты выделения скобок и кавычек
 */
public class BracketParseAttr implements Comparable<BracketParseAttr> {

    /**
     * Нет
     */
    public static final BracketParseAttr NO; // 0

    /**
     * По умолчанию, посл-ть не должна содержать чистых глаголов (если есть, то null). 
     * Почему так? Да потому, что это используется в основном для имён у именованных 
     * сущностей, а там не может быть глаголов. 
     * Если же этот ключ указан, то глаголы не проверяются.
     */
    public static final BracketParseAttr CANCONTAINSVERBS; // 2

    /**
     * Брать первую же подходящую закрывающую кавычку. Если не задано, то может искать сложные 
     * случаи вложенных кавычек.
     */
    public static final BracketParseAttr NEARCLOSEBRACKET; // 4

    /**
     * Внутри могут быть переходы на новую строку (многострочный)
     */
    public static final BracketParseAttr CANBEMANYLINES; // 8


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BracketParseAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BracketParseAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BracketParseAttr> mapIntToEnum; 
    private static java.util.HashMap<String, BracketParseAttr> mapStringToEnum; 
    private static BracketParseAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static BracketParseAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BracketParseAttr item = new BracketParseAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BracketParseAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static BracketParseAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, BracketParseAttr>();
        mapStringToEnum = new java.util.HashMap<String, BracketParseAttr>();
        NO = new BracketParseAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        CANCONTAINSVERBS = new BracketParseAttr(2, "CANCONTAINSVERBS");
        mapIntToEnum.put(CANCONTAINSVERBS.value(), CANCONTAINSVERBS);
        mapStringToEnum.put(CANCONTAINSVERBS.m_str.toUpperCase(), CANCONTAINSVERBS);
        NEARCLOSEBRACKET = new BracketParseAttr(4, "NEARCLOSEBRACKET");
        mapIntToEnum.put(NEARCLOSEBRACKET.value(), NEARCLOSEBRACKET);
        mapStringToEnum.put(NEARCLOSEBRACKET.m_str.toUpperCase(), NEARCLOSEBRACKET);
        CANBEMANYLINES = new BracketParseAttr(8, "CANBEMANYLINES");
        mapIntToEnum.put(CANBEMANYLINES.value(), CANBEMANYLINES);
        mapStringToEnum.put(CANBEMANYLINES.m_str.toUpperCase(), CANBEMANYLINES);
        java.util.Collection<BracketParseAttr> col = mapIntToEnum.values();
        m_Values = new BracketParseAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
