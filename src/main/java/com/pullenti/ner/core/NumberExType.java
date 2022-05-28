/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Единицы измерения для NumberExToken
public class NumberExType implements Comparable<NumberExType> {

    public static final NumberExType UNDEFINED; // 0

    public static final NumberExType PERCENT; // 1

    public static final NumberExType METER; // 2

    public static final NumberExType MILLIMETER; // 3

    public static final NumberExType KILOMETER; // 4

    public static final NumberExType SANTIMETER; // 5

    public static final NumberExType SANTIMETER2; // 6

    public static final NumberExType SANTIMETER3; // 7

    public static final NumberExType METER2; // 8

    public static final NumberExType AR; // 9

    public static final NumberExType GEKTAR; // 10

    public static final NumberExType KILOMETER2; // 11

    public static final NumberExType METER3; // 12

    public static final NumberExType MILE; // 13

    public static final NumberExType GRAMM; // 14

    public static final NumberExType MILLIGRAM; // 15

    public static final NumberExType KILOGRAM; // 16

    public static final NumberExType TONNA; // 17

    public static final NumberExType LITR; // 18

    public static final NumberExType MILLILITR; // 19

    public static final NumberExType HOUR; // 20

    public static final NumberExType MINUTE; // 21

    public static final NumberExType SECOND; // 22

    public static final NumberExType YEAR; // 23

    public static final NumberExType MONTH; // 24

    public static final NumberExType WEEK; // 25

    public static final NumberExType DAY; // 26

    public static final NumberExType MONEY; // 27

    public static final NumberExType SHUK; // 28

    public static final NumberExType UPAK; // 29

    public static final NumberExType RULON; // 30

    public static final NumberExType NABOR; // 31

    public static final NumberExType KOMPLEKT; // 32

    public static final NumberExType PARA; // 33

    public static final NumberExType FLAKON; // 34


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NumberExType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NumberExType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NumberExType> mapIntToEnum; 
    private static java.util.HashMap<String, NumberExType> mapStringToEnum; 
    private static NumberExType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NumberExType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NumberExType item = new NumberExType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NumberExType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NumberExType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NumberExType>();
        mapStringToEnum = new java.util.HashMap<String, NumberExType>();
        UNDEFINED = new NumberExType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PERCENT = new NumberExType(1, "PERCENT");
        mapIntToEnum.put(PERCENT.value(), PERCENT);
        mapStringToEnum.put(PERCENT.m_str.toUpperCase(), PERCENT);
        METER = new NumberExType(2, "METER");
        mapIntToEnum.put(METER.value(), METER);
        mapStringToEnum.put(METER.m_str.toUpperCase(), METER);
        MILLIMETER = new NumberExType(3, "MILLIMETER");
        mapIntToEnum.put(MILLIMETER.value(), MILLIMETER);
        mapStringToEnum.put(MILLIMETER.m_str.toUpperCase(), MILLIMETER);
        KILOMETER = new NumberExType(4, "KILOMETER");
        mapIntToEnum.put(KILOMETER.value(), KILOMETER);
        mapStringToEnum.put(KILOMETER.m_str.toUpperCase(), KILOMETER);
        SANTIMETER = new NumberExType(5, "SANTIMETER");
        mapIntToEnum.put(SANTIMETER.value(), SANTIMETER);
        mapStringToEnum.put(SANTIMETER.m_str.toUpperCase(), SANTIMETER);
        SANTIMETER2 = new NumberExType(6, "SANTIMETER2");
        mapIntToEnum.put(SANTIMETER2.value(), SANTIMETER2);
        mapStringToEnum.put(SANTIMETER2.m_str.toUpperCase(), SANTIMETER2);
        SANTIMETER3 = new NumberExType(7, "SANTIMETER3");
        mapIntToEnum.put(SANTIMETER3.value(), SANTIMETER3);
        mapStringToEnum.put(SANTIMETER3.m_str.toUpperCase(), SANTIMETER3);
        METER2 = new NumberExType(8, "METER2");
        mapIntToEnum.put(METER2.value(), METER2);
        mapStringToEnum.put(METER2.m_str.toUpperCase(), METER2);
        AR = new NumberExType(9, "AR");
        mapIntToEnum.put(AR.value(), AR);
        mapStringToEnum.put(AR.m_str.toUpperCase(), AR);
        GEKTAR = new NumberExType(10, "GEKTAR");
        mapIntToEnum.put(GEKTAR.value(), GEKTAR);
        mapStringToEnum.put(GEKTAR.m_str.toUpperCase(), GEKTAR);
        KILOMETER2 = new NumberExType(11, "KILOMETER2");
        mapIntToEnum.put(KILOMETER2.value(), KILOMETER2);
        mapStringToEnum.put(KILOMETER2.m_str.toUpperCase(), KILOMETER2);
        METER3 = new NumberExType(12, "METER3");
        mapIntToEnum.put(METER3.value(), METER3);
        mapStringToEnum.put(METER3.m_str.toUpperCase(), METER3);
        MILE = new NumberExType(13, "MILE");
        mapIntToEnum.put(MILE.value(), MILE);
        mapStringToEnum.put(MILE.m_str.toUpperCase(), MILE);
        GRAMM = new NumberExType(14, "GRAMM");
        mapIntToEnum.put(GRAMM.value(), GRAMM);
        mapStringToEnum.put(GRAMM.m_str.toUpperCase(), GRAMM);
        MILLIGRAM = new NumberExType(15, "MILLIGRAM");
        mapIntToEnum.put(MILLIGRAM.value(), MILLIGRAM);
        mapStringToEnum.put(MILLIGRAM.m_str.toUpperCase(), MILLIGRAM);
        KILOGRAM = new NumberExType(16, "KILOGRAM");
        mapIntToEnum.put(KILOGRAM.value(), KILOGRAM);
        mapStringToEnum.put(KILOGRAM.m_str.toUpperCase(), KILOGRAM);
        TONNA = new NumberExType(17, "TONNA");
        mapIntToEnum.put(TONNA.value(), TONNA);
        mapStringToEnum.put(TONNA.m_str.toUpperCase(), TONNA);
        LITR = new NumberExType(18, "LITR");
        mapIntToEnum.put(LITR.value(), LITR);
        mapStringToEnum.put(LITR.m_str.toUpperCase(), LITR);
        MILLILITR = new NumberExType(19, "MILLILITR");
        mapIntToEnum.put(MILLILITR.value(), MILLILITR);
        mapStringToEnum.put(MILLILITR.m_str.toUpperCase(), MILLILITR);
        HOUR = new NumberExType(20, "HOUR");
        mapIntToEnum.put(HOUR.value(), HOUR);
        mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
        MINUTE = new NumberExType(21, "MINUTE");
        mapIntToEnum.put(MINUTE.value(), MINUTE);
        mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
        SECOND = new NumberExType(22, "SECOND");
        mapIntToEnum.put(SECOND.value(), SECOND);
        mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
        YEAR = new NumberExType(23, "YEAR");
        mapIntToEnum.put(YEAR.value(), YEAR);
        mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
        MONTH = new NumberExType(24, "MONTH");
        mapIntToEnum.put(MONTH.value(), MONTH);
        mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
        WEEK = new NumberExType(25, "WEEK");
        mapIntToEnum.put(WEEK.value(), WEEK);
        mapStringToEnum.put(WEEK.m_str.toUpperCase(), WEEK);
        DAY = new NumberExType(26, "DAY");
        mapIntToEnum.put(DAY.value(), DAY);
        mapStringToEnum.put(DAY.m_str.toUpperCase(), DAY);
        MONEY = new NumberExType(27, "MONEY");
        mapIntToEnum.put(MONEY.value(), MONEY);
        mapStringToEnum.put(MONEY.m_str.toUpperCase(), MONEY);
        SHUK = new NumberExType(28, "SHUK");
        mapIntToEnum.put(SHUK.value(), SHUK);
        mapStringToEnum.put(SHUK.m_str.toUpperCase(), SHUK);
        UPAK = new NumberExType(29, "UPAK");
        mapIntToEnum.put(UPAK.value(), UPAK);
        mapStringToEnum.put(UPAK.m_str.toUpperCase(), UPAK);
        RULON = new NumberExType(30, "RULON");
        mapIntToEnum.put(RULON.value(), RULON);
        mapStringToEnum.put(RULON.m_str.toUpperCase(), RULON);
        NABOR = new NumberExType(31, "NABOR");
        mapIntToEnum.put(NABOR.value(), NABOR);
        mapStringToEnum.put(NABOR.m_str.toUpperCase(), NABOR);
        KOMPLEKT = new NumberExType(32, "KOMPLEKT");
        mapIntToEnum.put(KOMPLEKT.value(), KOMPLEKT);
        mapStringToEnum.put(KOMPLEKT.m_str.toUpperCase(), KOMPLEKT);
        PARA = new NumberExType(33, "PARA");
        mapIntToEnum.put(PARA.value(), PARA);
        mapStringToEnum.put(PARA.m_str.toUpperCase(), PARA);
        FLAKON = new NumberExType(34, "FLAKON");
        mapIntToEnum.put(FLAKON.value(), FLAKON);
        mapStringToEnum.put(FLAKON.m_str.toUpperCase(), FLAKON);
        java.util.Collection<NumberExType> col = mapIntToEnum.values();
        m_Values = new NumberExType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
