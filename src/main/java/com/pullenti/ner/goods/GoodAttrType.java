/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods;

/**
 * Типы атрибута
 */
public class GoodAttrType implements Comparable<GoodAttrType> {

    /**
     * Неопределено
     */
    public static final GoodAttrType UNDEFINED; // 0

    /**
     * Ключевое слово (тип товара)
     */
    public static final GoodAttrType KEYWORD; // 1

    /**
     * Качественное свойство
     */
    public static final GoodAttrType CHARACTER; // 2

    /**
     * Собственное имя
     */
    public static final GoodAttrType PROPER; // 3

    /**
     * Модель
     */
    public static final GoodAttrType MODEL; // 4

    /**
     * Количественное свойство
     */
    public static final GoodAttrType NUMERIC; // 5

    /**
     * Ссылка на некоторую сущность (страна, организация - производитель, ГОСТ ...)
     */
    public static final GoodAttrType REFERENT; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private GoodAttrType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(GoodAttrType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, GoodAttrType> mapIntToEnum; 
    private static java.util.HashMap<String, GoodAttrType> mapStringToEnum; 
    private static GoodAttrType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static GoodAttrType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        GoodAttrType item = new GoodAttrType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static GoodAttrType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static GoodAttrType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, GoodAttrType>();
        mapStringToEnum = new java.util.HashMap<String, GoodAttrType>();
        UNDEFINED = new GoodAttrType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        KEYWORD = new GoodAttrType(1, "KEYWORD");
        mapIntToEnum.put(KEYWORD.value(), KEYWORD);
        mapStringToEnum.put(KEYWORD.m_str.toUpperCase(), KEYWORD);
        CHARACTER = new GoodAttrType(2, "CHARACTER");
        mapIntToEnum.put(CHARACTER.value(), CHARACTER);
        mapStringToEnum.put(CHARACTER.m_str.toUpperCase(), CHARACTER);
        PROPER = new GoodAttrType(3, "PROPER");
        mapIntToEnum.put(PROPER.value(), PROPER);
        mapStringToEnum.put(PROPER.m_str.toUpperCase(), PROPER);
        MODEL = new GoodAttrType(4, "MODEL");
        mapIntToEnum.put(MODEL.value(), MODEL);
        mapStringToEnum.put(MODEL.m_str.toUpperCase(), MODEL);
        NUMERIC = new GoodAttrType(5, "NUMERIC");
        mapIntToEnum.put(NUMERIC.value(), NUMERIC);
        mapStringToEnum.put(NUMERIC.m_str.toUpperCase(), NUMERIC);
        REFERENT = new GoodAttrType(6, "REFERENT");
        mapIntToEnum.put(REFERENT.value(), REFERENT);
        mapStringToEnum.put(REFERENT.m_str.toUpperCase(), REFERENT);
        java.util.Collection<GoodAttrType> col = mapIntToEnum.values();
        m_Values = new GoodAttrType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
