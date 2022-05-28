/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date;

/**
 * Дополнительные указатели для дат
 */
public class DatePointerType implements Comparable<DatePointerType> {

    public static final DatePointerType NO; // 0

    /**
     * В начале
     */
    public static final DatePointerType BEGIN; // 1

    /**
     * В середине
     */
    public static final DatePointerType CENTER; // 2

    /**
     * В конце
     */
    public static final DatePointerType END; // 3

    /**
     * В настоящее время, сегодня
     */
    public static final DatePointerType TODAY; // 4

    /**
     * Зимой
     */
    public static final DatePointerType WINTER; // 5

    /**
     * Весной
     */
    public static final DatePointerType SPRING; // 6

    /**
     * Летом
     */
    public static final DatePointerType SUMMER; // 7

    /**
     * Осенью
     */
    public static final DatePointerType AUTUMN; // 8

    /**
     * Около, примерно
     */
    public static final DatePointerType ABOUT; // 9

    /**
     * Неопределено (например, 20__ года )
     */
    public static final DatePointerType UNDEFINED; // 10


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DatePointerType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DatePointerType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DatePointerType> mapIntToEnum; 
    private static java.util.HashMap<String, DatePointerType> mapStringToEnum; 
    private static DatePointerType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DatePointerType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DatePointerType item = new DatePointerType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DatePointerType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DatePointerType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DatePointerType>();
        mapStringToEnum = new java.util.HashMap<String, DatePointerType>();
        NO = new DatePointerType(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        BEGIN = new DatePointerType(1, "BEGIN");
        mapIntToEnum.put(BEGIN.value(), BEGIN);
        mapStringToEnum.put(BEGIN.m_str.toUpperCase(), BEGIN);
        CENTER = new DatePointerType(2, "CENTER");
        mapIntToEnum.put(CENTER.value(), CENTER);
        mapStringToEnum.put(CENTER.m_str.toUpperCase(), CENTER);
        END = new DatePointerType(3, "END");
        mapIntToEnum.put(END.value(), END);
        mapStringToEnum.put(END.m_str.toUpperCase(), END);
        TODAY = new DatePointerType(4, "TODAY");
        mapIntToEnum.put(TODAY.value(), TODAY);
        mapStringToEnum.put(TODAY.m_str.toUpperCase(), TODAY);
        WINTER = new DatePointerType(5, "WINTER");
        mapIntToEnum.put(WINTER.value(), WINTER);
        mapStringToEnum.put(WINTER.m_str.toUpperCase(), WINTER);
        SPRING = new DatePointerType(6, "SPRING");
        mapIntToEnum.put(SPRING.value(), SPRING);
        mapStringToEnum.put(SPRING.m_str.toUpperCase(), SPRING);
        SUMMER = new DatePointerType(7, "SUMMER");
        mapIntToEnum.put(SUMMER.value(), SUMMER);
        mapStringToEnum.put(SUMMER.m_str.toUpperCase(), SUMMER);
        AUTUMN = new DatePointerType(8, "AUTUMN");
        mapIntToEnum.put(AUTUMN.value(), AUTUMN);
        mapStringToEnum.put(AUTUMN.m_str.toUpperCase(), AUTUMN);
        ABOUT = new DatePointerType(9, "ABOUT");
        mapIntToEnum.put(ABOUT.value(), ABOUT);
        mapStringToEnum.put(ABOUT.m_str.toUpperCase(), ABOUT);
        UNDEFINED = new DatePointerType(10, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        java.util.Collection<DatePointerType> col = mapIntToEnum.values();
        m_Values = new DatePointerType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
