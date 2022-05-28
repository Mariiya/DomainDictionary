/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org;

/**
 * Категории организаций. Не хранятся, а вычисляются на основе других атрибутов.
 */
public class OrganizationKind implements Comparable<OrganizationKind> {

    /**
     * Неопределённая
     */
    public static final OrganizationKind UNDEFINED; // 0

    /**
     * Правительственная
     */
    public static final OrganizationKind GOVENMENT; // 1

    /**
     * Политическая
     */
    public static final OrganizationKind PARTY; // 2

    /**
     * Образовательная
     */
    public static final OrganizationKind STUDY; // 3

    /**
     * Научно-исследовательская
     */
    public static final OrganizationKind SCIENCE; // 4

    /**
     * Пресса
     */
    public static final OrganizationKind PRESS; // 5

    /**
     * Масс-медиа
     */
    public static final OrganizationKind MEDIA; // 6

    /**
     * Производственная
     */
    public static final OrganizationKind FACTORY; // 7

    /**
     * Банковская
     */
    public static final OrganizationKind BANK; // 8

    /**
     * Культурная
     */
    public static final OrganizationKind CULTURE; // 9

    /**
     * Медицинская
     */
    public static final OrganizationKind MEDICAL; // 10

    /**
     * Торговая
     */
    public static final OrganizationKind TRADE; // 11

    /**
     * Холдинг
     */
    public static final OrganizationKind HOLDING; // 12

    /**
     * Подразделение
     */
    public static final OrganizationKind DEPARTMENT; // 13

    /**
     * Федерация, Союз и т.п. непонятность
     */
    public static final OrganizationKind FEDERATION; // 14

    /**
     * Отели, Санатории, Пансионаты ...
     */
    public static final OrganizationKind HOTEL; // 15

    /**
     * Суды, тюрьмы ...
     */
    public static final OrganizationKind JUSTICE; // 16

    /**
     * Церкви, религиозное
     */
    public static final OrganizationKind CHURCH; // 17

    /**
     * Военные
     */
    public static final OrganizationKind MILITARY; // 18

    /**
     * Аэропорт
     */
    public static final OrganizationKind AIRPORT; // 19

    /**
     * Морские порты
     */
    public static final OrganizationKind SEAPORT; // 20

    /**
     * События (фестиваль, чемпионат)
     */
    public static final OrganizationKind FESTIVAL; // 21


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private OrganizationKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(OrganizationKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, OrganizationKind> mapIntToEnum; 
    private static java.util.HashMap<String, OrganizationKind> mapStringToEnum; 
    private static OrganizationKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static OrganizationKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        OrganizationKind item = new OrganizationKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static OrganizationKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static OrganizationKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, OrganizationKind>();
        mapStringToEnum = new java.util.HashMap<String, OrganizationKind>();
        UNDEFINED = new OrganizationKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        GOVENMENT = new OrganizationKind(1, "GOVENMENT");
        mapIntToEnum.put(GOVENMENT.value(), GOVENMENT);
        mapStringToEnum.put(GOVENMENT.m_str.toUpperCase(), GOVENMENT);
        PARTY = new OrganizationKind(2, "PARTY");
        mapIntToEnum.put(PARTY.value(), PARTY);
        mapStringToEnum.put(PARTY.m_str.toUpperCase(), PARTY);
        STUDY = new OrganizationKind(3, "STUDY");
        mapIntToEnum.put(STUDY.value(), STUDY);
        mapStringToEnum.put(STUDY.m_str.toUpperCase(), STUDY);
        SCIENCE = new OrganizationKind(4, "SCIENCE");
        mapIntToEnum.put(SCIENCE.value(), SCIENCE);
        mapStringToEnum.put(SCIENCE.m_str.toUpperCase(), SCIENCE);
        PRESS = new OrganizationKind(5, "PRESS");
        mapIntToEnum.put(PRESS.value(), PRESS);
        mapStringToEnum.put(PRESS.m_str.toUpperCase(), PRESS);
        MEDIA = new OrganizationKind(6, "MEDIA");
        mapIntToEnum.put(MEDIA.value(), MEDIA);
        mapStringToEnum.put(MEDIA.m_str.toUpperCase(), MEDIA);
        FACTORY = new OrganizationKind(7, "FACTORY");
        mapIntToEnum.put(FACTORY.value(), FACTORY);
        mapStringToEnum.put(FACTORY.m_str.toUpperCase(), FACTORY);
        BANK = new OrganizationKind(8, "BANK");
        mapIntToEnum.put(BANK.value(), BANK);
        mapStringToEnum.put(BANK.m_str.toUpperCase(), BANK);
        CULTURE = new OrganizationKind(9, "CULTURE");
        mapIntToEnum.put(CULTURE.value(), CULTURE);
        mapStringToEnum.put(CULTURE.m_str.toUpperCase(), CULTURE);
        MEDICAL = new OrganizationKind(10, "MEDICAL");
        mapIntToEnum.put(MEDICAL.value(), MEDICAL);
        mapStringToEnum.put(MEDICAL.m_str.toUpperCase(), MEDICAL);
        TRADE = new OrganizationKind(11, "TRADE");
        mapIntToEnum.put(TRADE.value(), TRADE);
        mapStringToEnum.put(TRADE.m_str.toUpperCase(), TRADE);
        HOLDING = new OrganizationKind(12, "HOLDING");
        mapIntToEnum.put(HOLDING.value(), HOLDING);
        mapStringToEnum.put(HOLDING.m_str.toUpperCase(), HOLDING);
        DEPARTMENT = new OrganizationKind(13, "DEPARTMENT");
        mapIntToEnum.put(DEPARTMENT.value(), DEPARTMENT);
        mapStringToEnum.put(DEPARTMENT.m_str.toUpperCase(), DEPARTMENT);
        FEDERATION = new OrganizationKind(14, "FEDERATION");
        mapIntToEnum.put(FEDERATION.value(), FEDERATION);
        mapStringToEnum.put(FEDERATION.m_str.toUpperCase(), FEDERATION);
        HOTEL = new OrganizationKind(15, "HOTEL");
        mapIntToEnum.put(HOTEL.value(), HOTEL);
        mapStringToEnum.put(HOTEL.m_str.toUpperCase(), HOTEL);
        JUSTICE = new OrganizationKind(16, "JUSTICE");
        mapIntToEnum.put(JUSTICE.value(), JUSTICE);
        mapStringToEnum.put(JUSTICE.m_str.toUpperCase(), JUSTICE);
        CHURCH = new OrganizationKind(17, "CHURCH");
        mapIntToEnum.put(CHURCH.value(), CHURCH);
        mapStringToEnum.put(CHURCH.m_str.toUpperCase(), CHURCH);
        MILITARY = new OrganizationKind(18, "MILITARY");
        mapIntToEnum.put(MILITARY.value(), MILITARY);
        mapStringToEnum.put(MILITARY.m_str.toUpperCase(), MILITARY);
        AIRPORT = new OrganizationKind(19, "AIRPORT");
        mapIntToEnum.put(AIRPORT.value(), AIRPORT);
        mapStringToEnum.put(AIRPORT.m_str.toUpperCase(), AIRPORT);
        SEAPORT = new OrganizationKind(20, "SEAPORT");
        mapIntToEnum.put(SEAPORT.value(), SEAPORT);
        mapStringToEnum.put(SEAPORT.m_str.toUpperCase(), SEAPORT);
        FESTIVAL = new OrganizationKind(21, "FESTIVAL");
        mapIntToEnum.put(FESTIVAL.value(), FESTIVAL);
        mapStringToEnum.put(FESTIVAL.m_str.toUpperCase(), FESTIVAL);
        java.util.Collection<OrganizationKind> col = mapIntToEnum.values();
        m_Values = new OrganizationKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
