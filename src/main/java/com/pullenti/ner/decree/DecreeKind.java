/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Классы нормативных актов
 */
public class DecreeKind implements Comparable<DecreeKind> {

    public static final DecreeKind UNDEFINED; // 0

    /**
     * Кодекс
     */
    public static final DecreeKind KODEX; // 1

    /**
     * Устав
     */
    public static final DecreeKind USTAV; // 2

    /**
     * Закон
     */
    public static final DecreeKind LAW; // 3

    /**
     * Приказ, указ, директива, распоряжение
     */
    public static final DecreeKind ORDER; // 4

    /**
     * Конвенция
     */
    public static final DecreeKind KONVENTION; // 5

    /**
     * Договор, контракт
     */
    public static final DecreeKind CONTRACT; // 6

    /**
     * Проект
     */
    public static final DecreeKind PROJECT; // 7

    /**
     * Источники опубликований
     */
    public static final DecreeKind PUBLISHER; // 8

    /**
     * Госпрограммы
     */
    public static final DecreeKind PROGRAM; // 9

    /**
     * Стандарт (ГОСТ, ТУ, ANSI и пр.)
     */
    public static final DecreeKind STANDARD; // 10


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeKind> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeKind> mapStringToEnum; 
    private static DecreeKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DecreeKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeKind item = new DecreeKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DecreeKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DecreeKind>();
        mapStringToEnum = new java.util.HashMap<String, DecreeKind>();
        UNDEFINED = new DecreeKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        KODEX = new DecreeKind(1, "KODEX");
        mapIntToEnum.put(KODEX.value(), KODEX);
        mapStringToEnum.put(KODEX.m_str.toUpperCase(), KODEX);
        USTAV = new DecreeKind(2, "USTAV");
        mapIntToEnum.put(USTAV.value(), USTAV);
        mapStringToEnum.put(USTAV.m_str.toUpperCase(), USTAV);
        LAW = new DecreeKind(3, "LAW");
        mapIntToEnum.put(LAW.value(), LAW);
        mapStringToEnum.put(LAW.m_str.toUpperCase(), LAW);
        ORDER = new DecreeKind(4, "ORDER");
        mapIntToEnum.put(ORDER.value(), ORDER);
        mapStringToEnum.put(ORDER.m_str.toUpperCase(), ORDER);
        KONVENTION = new DecreeKind(5, "KONVENTION");
        mapIntToEnum.put(KONVENTION.value(), KONVENTION);
        mapStringToEnum.put(KONVENTION.m_str.toUpperCase(), KONVENTION);
        CONTRACT = new DecreeKind(6, "CONTRACT");
        mapIntToEnum.put(CONTRACT.value(), CONTRACT);
        mapStringToEnum.put(CONTRACT.m_str.toUpperCase(), CONTRACT);
        PROJECT = new DecreeKind(7, "PROJECT");
        mapIntToEnum.put(PROJECT.value(), PROJECT);
        mapStringToEnum.put(PROJECT.m_str.toUpperCase(), PROJECT);
        PUBLISHER = new DecreeKind(8, "PUBLISHER");
        mapIntToEnum.put(PUBLISHER.value(), PUBLISHER);
        mapStringToEnum.put(PUBLISHER.m_str.toUpperCase(), PUBLISHER);
        PROGRAM = new DecreeKind(9, "PROGRAM");
        mapIntToEnum.put(PROGRAM.value(), PROGRAM);
        mapStringToEnum.put(PROGRAM.m_str.toUpperCase(), PROGRAM);
        STANDARD = new DecreeKind(10, "STANDARD");
        mapIntToEnum.put(STANDARD.value(), STANDARD);
        mapStringToEnum.put(STANDARD.m_str.toUpperCase(), STANDARD);
        java.util.Collection<DecreeKind> col = mapIntToEnum.values();
        m_Values = new DecreeKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
