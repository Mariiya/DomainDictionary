/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Типы изменений структурных элементов (СЭ)
 */
public class DecreeChangeKind implements Comparable<DecreeChangeKind> {

    public static final DecreeChangeKind UNDEFINED; // 0

    /**
     * Объединяет в себе другие изменения
     */
    public static final DecreeChangeKind CONTAINER; // 1

    /**
     * Дополнить другим СЭ-м или текстовыми конструкциями
     */
    public static final DecreeChangeKind APPEND; // 2

    /**
     * СЭ утратил силу
     */
    public static final DecreeChangeKind EXPIRE; // 3

    /**
     * Изложить в редакции
     */
    public static final DecreeChangeKind NEW; // 4

    /**
     * Заменить одни текстовые конструкции другими
     */
    public static final DecreeChangeKind EXCHANGE; // 5

    /**
     * Удалить текстовые конструкции
     */
    public static final DecreeChangeKind REMOVE; // 6

    /**
     * Считать как
     */
    public static final DecreeChangeKind CONSIDER; // 7


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeChangeKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeChangeKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeChangeKind> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeChangeKind> mapStringToEnum; 
    private static DecreeChangeKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DecreeChangeKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeChangeKind item = new DecreeChangeKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeChangeKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DecreeChangeKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DecreeChangeKind>();
        mapStringToEnum = new java.util.HashMap<String, DecreeChangeKind>();
        UNDEFINED = new DecreeChangeKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        CONTAINER = new DecreeChangeKind(1, "CONTAINER");
        mapIntToEnum.put(CONTAINER.value(), CONTAINER);
        mapStringToEnum.put(CONTAINER.m_str.toUpperCase(), CONTAINER);
        APPEND = new DecreeChangeKind(2, "APPEND");
        mapIntToEnum.put(APPEND.value(), APPEND);
        mapStringToEnum.put(APPEND.m_str.toUpperCase(), APPEND);
        EXPIRE = new DecreeChangeKind(3, "EXPIRE");
        mapIntToEnum.put(EXPIRE.value(), EXPIRE);
        mapStringToEnum.put(EXPIRE.m_str.toUpperCase(), EXPIRE);
        NEW = new DecreeChangeKind(4, "NEW");
        mapIntToEnum.put(NEW.value(), NEW);
        mapStringToEnum.put(NEW.m_str.toUpperCase(), NEW);
        EXCHANGE = new DecreeChangeKind(5, "EXCHANGE");
        mapIntToEnum.put(EXCHANGE.value(), EXCHANGE);
        mapStringToEnum.put(EXCHANGE.m_str.toUpperCase(), EXCHANGE);
        REMOVE = new DecreeChangeKind(6, "REMOVE");
        mapIntToEnum.put(REMOVE.value(), REMOVE);
        mapStringToEnum.put(REMOVE.m_str.toUpperCase(), REMOVE);
        CONSIDER = new DecreeChangeKind(7, "CONSIDER");
        mapIntToEnum.put(CONSIDER.value(), CONSIDER);
        mapStringToEnum.put(CONSIDER.m_str.toUpperCase(), CONSIDER);
        java.util.Collection<DecreeChangeKind> col = mapIntToEnum.values();
        m_Values = new DecreeChangeKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
