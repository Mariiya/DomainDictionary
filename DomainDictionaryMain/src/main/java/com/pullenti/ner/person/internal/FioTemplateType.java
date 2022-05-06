/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class FioTemplateType implements Comparable<FioTemplateType> {

    public static final FioTemplateType UNDEFINED; // 0

    public static final FioTemplateType SURNAMEII; // 1

    public static final FioTemplateType IISURNAME; // 2

    public static final FioTemplateType SURNAMEI; // 3

    public static final FioTemplateType ISURNAME; // 4

    public static final FioTemplateType SURNAMENAME; // 5

    public static final FioTemplateType SURNAMENAMESECNAME; // 6

    public static final FioTemplateType NAMESURNAME; // 7

    public static final FioTemplateType NAMESECNAMESURNAME; // 8

    public static final FioTemplateType NAMEISURNAME; // 9

    public static final FioTemplateType NAMESECNAME; // 10

    public static final FioTemplateType KING; // 11

    public static final FioTemplateType ASIANNAME; // 12

    public static final FioTemplateType ASIANSURNAMENAME; // 13

    public static final FioTemplateType ARABICLONG; // 14


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private FioTemplateType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(FioTemplateType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, FioTemplateType> mapIntToEnum; 
    private static java.util.HashMap<String, FioTemplateType> mapStringToEnum; 
    private static FioTemplateType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static FioTemplateType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        FioTemplateType item = new FioTemplateType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static FioTemplateType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static FioTemplateType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, FioTemplateType>();
        mapStringToEnum = new java.util.HashMap<String, FioTemplateType>();
        UNDEFINED = new FioTemplateType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SURNAMEII = new FioTemplateType(1, "SURNAMEII");
        mapIntToEnum.put(SURNAMEII.value(), SURNAMEII);
        mapStringToEnum.put(SURNAMEII.m_str.toUpperCase(), SURNAMEII);
        IISURNAME = new FioTemplateType(2, "IISURNAME");
        mapIntToEnum.put(IISURNAME.value(), IISURNAME);
        mapStringToEnum.put(IISURNAME.m_str.toUpperCase(), IISURNAME);
        SURNAMEI = new FioTemplateType(3, "SURNAMEI");
        mapIntToEnum.put(SURNAMEI.value(), SURNAMEI);
        mapStringToEnum.put(SURNAMEI.m_str.toUpperCase(), SURNAMEI);
        ISURNAME = new FioTemplateType(4, "ISURNAME");
        mapIntToEnum.put(ISURNAME.value(), ISURNAME);
        mapStringToEnum.put(ISURNAME.m_str.toUpperCase(), ISURNAME);
        SURNAMENAME = new FioTemplateType(5, "SURNAMENAME");
        mapIntToEnum.put(SURNAMENAME.value(), SURNAMENAME);
        mapStringToEnum.put(SURNAMENAME.m_str.toUpperCase(), SURNAMENAME);
        SURNAMENAMESECNAME = new FioTemplateType(6, "SURNAMENAMESECNAME");
        mapIntToEnum.put(SURNAMENAMESECNAME.value(), SURNAMENAMESECNAME);
        mapStringToEnum.put(SURNAMENAMESECNAME.m_str.toUpperCase(), SURNAMENAMESECNAME);
        NAMESURNAME = new FioTemplateType(7, "NAMESURNAME");
        mapIntToEnum.put(NAMESURNAME.value(), NAMESURNAME);
        mapStringToEnum.put(NAMESURNAME.m_str.toUpperCase(), NAMESURNAME);
        NAMESECNAMESURNAME = new FioTemplateType(8, "NAMESECNAMESURNAME");
        mapIntToEnum.put(NAMESECNAMESURNAME.value(), NAMESECNAMESURNAME);
        mapStringToEnum.put(NAMESECNAMESURNAME.m_str.toUpperCase(), NAMESECNAMESURNAME);
        NAMEISURNAME = new FioTemplateType(9, "NAMEISURNAME");
        mapIntToEnum.put(NAMEISURNAME.value(), NAMEISURNAME);
        mapStringToEnum.put(NAMEISURNAME.m_str.toUpperCase(), NAMEISURNAME);
        NAMESECNAME = new FioTemplateType(10, "NAMESECNAME");
        mapIntToEnum.put(NAMESECNAME.value(), NAMESECNAME);
        mapStringToEnum.put(NAMESECNAME.m_str.toUpperCase(), NAMESECNAME);
        KING = new FioTemplateType(11, "KING");
        mapIntToEnum.put(KING.value(), KING);
        mapStringToEnum.put(KING.m_str.toUpperCase(), KING);
        ASIANNAME = new FioTemplateType(12, "ASIANNAME");
        mapIntToEnum.put(ASIANNAME.value(), ASIANNAME);
        mapStringToEnum.put(ASIANNAME.m_str.toUpperCase(), ASIANNAME);
        ASIANSURNAMENAME = new FioTemplateType(13, "ASIANSURNAMENAME");
        mapIntToEnum.put(ASIANSURNAMENAME.value(), ASIANSURNAMENAME);
        mapStringToEnum.put(ASIANSURNAMENAME.m_str.toUpperCase(), ASIANSURNAMENAME);
        ARABICLONG = new FioTemplateType(14, "ARABICLONG");
        mapIntToEnum.put(ARABICLONG.value(), ARABICLONG);
        mapStringToEnum.put(ARABICLONG.m_str.toUpperCase(), ARABICLONG);
        java.util.Collection<FioTemplateType> col = mapIntToEnum.values();
        m_Values = new FioTemplateType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
