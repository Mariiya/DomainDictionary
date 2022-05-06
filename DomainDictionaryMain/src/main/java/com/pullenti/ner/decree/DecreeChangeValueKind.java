/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Типы изменяющих структурный элемент значений
 */
public class DecreeChangeValueKind implements Comparable<DecreeChangeValueKind> {

    public static final DecreeChangeValueKind UNDEFINED; // 0

    /**
     * Текстовой фрагмент
     */
    public static final DecreeChangeValueKind TEXT; // 1

    /**
     * Слова (в точном значении)
     */
    public static final DecreeChangeValueKind WORDS; // 2

    /**
     * Слова (в неточном значений)
     */
    public static final DecreeChangeValueKind ROBUSTWORDS; // 3

    /**
     * Цифры
     */
    public static final DecreeChangeValueKind NUMBERS; // 4

    /**
     * Предложение
     */
    public static final DecreeChangeValueKind SEQUENCE; // 5

    /**
     * Сноска
     */
    public static final DecreeChangeValueKind FOOTNOTE; // 6

    /**
     * Блок со словами
     */
    public static final DecreeChangeValueKind BLOCK; // 7


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeChangeValueKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeChangeValueKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeChangeValueKind> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeChangeValueKind> mapStringToEnum; 
    private static DecreeChangeValueKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DecreeChangeValueKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeChangeValueKind item = new DecreeChangeValueKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeChangeValueKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DecreeChangeValueKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DecreeChangeValueKind>();
        mapStringToEnum = new java.util.HashMap<String, DecreeChangeValueKind>();
        UNDEFINED = new DecreeChangeValueKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        TEXT = new DecreeChangeValueKind(1, "TEXT");
        mapIntToEnum.put(TEXT.value(), TEXT);
        mapStringToEnum.put(TEXT.m_str.toUpperCase(), TEXT);
        WORDS = new DecreeChangeValueKind(2, "WORDS");
        mapIntToEnum.put(WORDS.value(), WORDS);
        mapStringToEnum.put(WORDS.m_str.toUpperCase(), WORDS);
        ROBUSTWORDS = new DecreeChangeValueKind(3, "ROBUSTWORDS");
        mapIntToEnum.put(ROBUSTWORDS.value(), ROBUSTWORDS);
        mapStringToEnum.put(ROBUSTWORDS.m_str.toUpperCase(), ROBUSTWORDS);
        NUMBERS = new DecreeChangeValueKind(4, "NUMBERS");
        mapIntToEnum.put(NUMBERS.value(), NUMBERS);
        mapStringToEnum.put(NUMBERS.m_str.toUpperCase(), NUMBERS);
        SEQUENCE = new DecreeChangeValueKind(5, "SEQUENCE");
        mapIntToEnum.put(SEQUENCE.value(), SEQUENCE);
        mapStringToEnum.put(SEQUENCE.m_str.toUpperCase(), SEQUENCE);
        FOOTNOTE = new DecreeChangeValueKind(6, "FOOTNOTE");
        mapIntToEnum.put(FOOTNOTE.value(), FOOTNOTE);
        mapStringToEnum.put(FOOTNOTE.m_str.toUpperCase(), FOOTNOTE);
        BLOCK = new DecreeChangeValueKind(7, "BLOCK");
        mapIntToEnum.put(BLOCK.value(), BLOCK);
        mapStringToEnum.put(BLOCK.m_str.toUpperCase(), BLOCK);
        java.util.Collection<DecreeChangeValueKind> col = mapIntToEnum.values();
        m_Values = new DecreeChangeValueKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
