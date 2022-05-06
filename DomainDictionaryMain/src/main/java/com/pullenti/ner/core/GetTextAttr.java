/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Атрибуты получения текста методами GetTextValue и GetTextValueOfMetaToken класса MiscHelper. Битовая маска.
 * Атрибуты получения текста
 */
public class GetTextAttr implements Comparable<GetTextAttr> {

    /**
     * Не задано
     */
    public static final GetTextAttr NO; // 0

    /**
     * Сохранять ли регистр букв (по умолчанию, верхний регистр)
     */
    public static final GetTextAttr KEEPREGISTER; // 1

    /**
     * Первую именную группу преобразовывать к именительному падежу
     */
    public static final GetTextAttr FIRSTNOUNGROUPTONOMINATIVE; // 2

    /**
     * Первую именную группу преобразовывать к именительному падежу единственному числу
     */
    public static final GetTextAttr FIRSTNOUNGROUPTONOMINATIVESINGLE; // 4

    /**
     * Оставлять кавычки (по умолчанию, кавычки игнорируются). К скобкам это не относится.
     */
    public static final GetTextAttr KEEPQUOTES; // 8

    /**
     * Игнорировать географические объекты
     */
    public static final GetTextAttr IGNOREGEOREFERENT; // 0x10

    /**
     * Преобразовать ли числовые значения в цифры
     */
    public static final GetTextAttr NORMALIZENUMBERS; // 0x20

    /**
     * Если все слова в верхнем регистре, то попытаться восстановить слова в нижнем регистре 
     * на основе их встречаемости в других частях всего документа 
     * (то есть если слово есть в нижнем, то оно переводится в нижний)
     */
    public static final GetTextAttr RESTOREREGISTER; // 0x40

    /**
     * Для английского языка игнорировать артикли и суффикс 'S
     */
    public static final GetTextAttr IGNOREARTICLES; // 0x80


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private GetTextAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(GetTextAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, GetTextAttr> mapIntToEnum; 
    private static java.util.HashMap<String, GetTextAttr> mapStringToEnum; 
    private static GetTextAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static GetTextAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        GetTextAttr item = new GetTextAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static GetTextAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static GetTextAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, GetTextAttr>();
        mapStringToEnum = new java.util.HashMap<String, GetTextAttr>();
        NO = new GetTextAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        KEEPREGISTER = new GetTextAttr(1, "KEEPREGISTER");
        mapIntToEnum.put(KEEPREGISTER.value(), KEEPREGISTER);
        mapStringToEnum.put(KEEPREGISTER.m_str.toUpperCase(), KEEPREGISTER);
        FIRSTNOUNGROUPTONOMINATIVE = new GetTextAttr(2, "FIRSTNOUNGROUPTONOMINATIVE");
        mapIntToEnum.put(FIRSTNOUNGROUPTONOMINATIVE.value(), FIRSTNOUNGROUPTONOMINATIVE);
        mapStringToEnum.put(FIRSTNOUNGROUPTONOMINATIVE.m_str.toUpperCase(), FIRSTNOUNGROUPTONOMINATIVE);
        FIRSTNOUNGROUPTONOMINATIVESINGLE = new GetTextAttr(4, "FIRSTNOUNGROUPTONOMINATIVESINGLE");
        mapIntToEnum.put(FIRSTNOUNGROUPTONOMINATIVESINGLE.value(), FIRSTNOUNGROUPTONOMINATIVESINGLE);
        mapStringToEnum.put(FIRSTNOUNGROUPTONOMINATIVESINGLE.m_str.toUpperCase(), FIRSTNOUNGROUPTONOMINATIVESINGLE);
        KEEPQUOTES = new GetTextAttr(8, "KEEPQUOTES");
        mapIntToEnum.put(KEEPQUOTES.value(), KEEPQUOTES);
        mapStringToEnum.put(KEEPQUOTES.m_str.toUpperCase(), KEEPQUOTES);
        IGNOREGEOREFERENT = new GetTextAttr(0x10, "IGNOREGEOREFERENT");
        mapIntToEnum.put(IGNOREGEOREFERENT.value(), IGNOREGEOREFERENT);
        mapStringToEnum.put(IGNOREGEOREFERENT.m_str.toUpperCase(), IGNOREGEOREFERENT);
        NORMALIZENUMBERS = new GetTextAttr(0x20, "NORMALIZENUMBERS");
        mapIntToEnum.put(NORMALIZENUMBERS.value(), NORMALIZENUMBERS);
        mapStringToEnum.put(NORMALIZENUMBERS.m_str.toUpperCase(), NORMALIZENUMBERS);
        RESTOREREGISTER = new GetTextAttr(0x40, "RESTOREREGISTER");
        mapIntToEnum.put(RESTOREREGISTER.value(), RESTOREREGISTER);
        mapStringToEnum.put(RESTOREREGISTER.m_str.toUpperCase(), RESTOREREGISTER);
        IGNOREARTICLES = new GetTextAttr(0x80, "IGNOREARTICLES");
        mapIntToEnum.put(IGNOREARTICLES.value(), IGNOREARTICLES);
        mapStringToEnum.put(IGNOREARTICLES.m_str.toUpperCase(), IGNOREARTICLES);
        java.util.Collection<GetTextAttr> col = mapIntToEnum.values();
        m_Values = new GetTextAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
