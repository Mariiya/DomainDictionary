/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.phone;

/**
 * Тип телефонного номера
 */
public class PhoneKind implements Comparable<PhoneKind> {

    public static final PhoneKind UNDEFINED; // 0

    /**
     * Домашний
     */
    public static final PhoneKind HOME; // 1

    /**
     * Мобильный
     */
    public static final PhoneKind MOBILE; // 2

    /**
     * Рабочий
     */
    public static final PhoneKind WORK; // 3

    /**
     * Факс
     */
    public static final PhoneKind FAX; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private PhoneKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(PhoneKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, PhoneKind> mapIntToEnum; 
    private static java.util.HashMap<String, PhoneKind> mapStringToEnum; 
    private static PhoneKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static PhoneKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        PhoneKind item = new PhoneKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static PhoneKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static PhoneKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, PhoneKind>();
        mapStringToEnum = new java.util.HashMap<String, PhoneKind>();
        UNDEFINED = new PhoneKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        HOME = new PhoneKind(1, "HOME");
        mapIntToEnum.put(HOME.value(), HOME);
        mapStringToEnum.put(HOME.m_str.toUpperCase(), HOME);
        MOBILE = new PhoneKind(2, "MOBILE");
        mapIntToEnum.put(MOBILE.value(), MOBILE);
        mapStringToEnum.put(MOBILE.m_str.toUpperCase(), MOBILE);
        WORK = new PhoneKind(3, "WORK");
        mapIntToEnum.put(WORK.value(), WORK);
        mapStringToEnum.put(WORK.m_str.toUpperCase(), WORK);
        FAX = new PhoneKind(4, "FAX");
        mapIntToEnum.put(FAX.value(), FAX);
        mapStringToEnum.put(FAX.m_str.toUpperCase(), FAX);
        java.util.Collection<PhoneKind> col = mapIntToEnum.values();
        m_Values = new PhoneKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
