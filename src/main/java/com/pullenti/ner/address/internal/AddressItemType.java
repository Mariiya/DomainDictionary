/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class AddressItemType implements Comparable<AddressItemType> {

    public static final AddressItemType PREFIX; // 0

    public static final AddressItemType STREET; // 1

    public static final AddressItemType HOUSE; // 2

    public static final AddressItemType BUILDING; // 3

    public static final AddressItemType CORPUS; // 4

    public static final AddressItemType POTCH; // 5

    public static final AddressItemType FLOOR; // 6

    public static final AddressItemType FLAT; // 7

    public static final AddressItemType CORPUSORFLAT; // 8

    public static final AddressItemType OFFICE; // 9

    public static final AddressItemType PLOT; // 10

    public static final AddressItemType FIELD; // 11

    public static final AddressItemType PAVILION; // 12

    public static final AddressItemType BLOCK; // 13

    public static final AddressItemType BOX; // 14

    public static final AddressItemType CITY; // 15

    public static final AddressItemType REGION; // 16

    public static final AddressItemType COUNTRY; // 17

    public static final AddressItemType NUMBER; // 18

    public static final AddressItemType NONUMBER; // 19

    public static final AddressItemType KILOMETER; // 20

    public static final AddressItemType ZIP; // 21

    public static final AddressItemType POSTOFFICEBOX; // 22

    public static final AddressItemType CSP; // 23

    public static final AddressItemType DETAIL; // 24


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private AddressItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(AddressItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, AddressItemType> mapIntToEnum; 
    private static java.util.HashMap<String, AddressItemType> mapStringToEnum; 
    private static AddressItemType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static AddressItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        AddressItemType item = new AddressItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static AddressItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static AddressItemType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, AddressItemType>();
        mapStringToEnum = new java.util.HashMap<String, AddressItemType>();
        PREFIX = new AddressItemType(0, "PREFIX");
        mapIntToEnum.put(PREFIX.value(), PREFIX);
        mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
        STREET = new AddressItemType(1, "STREET");
        mapIntToEnum.put(STREET.value(), STREET);
        mapStringToEnum.put(STREET.m_str.toUpperCase(), STREET);
        HOUSE = new AddressItemType(2, "HOUSE");
        mapIntToEnum.put(HOUSE.value(), HOUSE);
        mapStringToEnum.put(HOUSE.m_str.toUpperCase(), HOUSE);
        BUILDING = new AddressItemType(3, "BUILDING");
        mapIntToEnum.put(BUILDING.value(), BUILDING);
        mapStringToEnum.put(BUILDING.m_str.toUpperCase(), BUILDING);
        CORPUS = new AddressItemType(4, "CORPUS");
        mapIntToEnum.put(CORPUS.value(), CORPUS);
        mapStringToEnum.put(CORPUS.m_str.toUpperCase(), CORPUS);
        POTCH = new AddressItemType(5, "POTCH");
        mapIntToEnum.put(POTCH.value(), POTCH);
        mapStringToEnum.put(POTCH.m_str.toUpperCase(), POTCH);
        FLOOR = new AddressItemType(6, "FLOOR");
        mapIntToEnum.put(FLOOR.value(), FLOOR);
        mapStringToEnum.put(FLOOR.m_str.toUpperCase(), FLOOR);
        FLAT = new AddressItemType(7, "FLAT");
        mapIntToEnum.put(FLAT.value(), FLAT);
        mapStringToEnum.put(FLAT.m_str.toUpperCase(), FLAT);
        CORPUSORFLAT = new AddressItemType(8, "CORPUSORFLAT");
        mapIntToEnum.put(CORPUSORFLAT.value(), CORPUSORFLAT);
        mapStringToEnum.put(CORPUSORFLAT.m_str.toUpperCase(), CORPUSORFLAT);
        OFFICE = new AddressItemType(9, "OFFICE");
        mapIntToEnum.put(OFFICE.value(), OFFICE);
        mapStringToEnum.put(OFFICE.m_str.toUpperCase(), OFFICE);
        PLOT = new AddressItemType(10, "PLOT");
        mapIntToEnum.put(PLOT.value(), PLOT);
        mapStringToEnum.put(PLOT.m_str.toUpperCase(), PLOT);
        FIELD = new AddressItemType(11, "FIELD");
        mapIntToEnum.put(FIELD.value(), FIELD);
        mapStringToEnum.put(FIELD.m_str.toUpperCase(), FIELD);
        PAVILION = new AddressItemType(12, "PAVILION");
        mapIntToEnum.put(PAVILION.value(), PAVILION);
        mapStringToEnum.put(PAVILION.m_str.toUpperCase(), PAVILION);
        BLOCK = new AddressItemType(13, "BLOCK");
        mapIntToEnum.put(BLOCK.value(), BLOCK);
        mapStringToEnum.put(BLOCK.m_str.toUpperCase(), BLOCK);
        BOX = new AddressItemType(14, "BOX");
        mapIntToEnum.put(BOX.value(), BOX);
        mapStringToEnum.put(BOX.m_str.toUpperCase(), BOX);
        CITY = new AddressItemType(15, "CITY");
        mapIntToEnum.put(CITY.value(), CITY);
        mapStringToEnum.put(CITY.m_str.toUpperCase(), CITY);
        REGION = new AddressItemType(16, "REGION");
        mapIntToEnum.put(REGION.value(), REGION);
        mapStringToEnum.put(REGION.m_str.toUpperCase(), REGION);
        COUNTRY = new AddressItemType(17, "COUNTRY");
        mapIntToEnum.put(COUNTRY.value(), COUNTRY);
        mapStringToEnum.put(COUNTRY.m_str.toUpperCase(), COUNTRY);
        NUMBER = new AddressItemType(18, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        NONUMBER = new AddressItemType(19, "NONUMBER");
        mapIntToEnum.put(NONUMBER.value(), NONUMBER);
        mapStringToEnum.put(NONUMBER.m_str.toUpperCase(), NONUMBER);
        KILOMETER = new AddressItemType(20, "KILOMETER");
        mapIntToEnum.put(KILOMETER.value(), KILOMETER);
        mapStringToEnum.put(KILOMETER.m_str.toUpperCase(), KILOMETER);
        ZIP = new AddressItemType(21, "ZIP");
        mapIntToEnum.put(ZIP.value(), ZIP);
        mapStringToEnum.put(ZIP.m_str.toUpperCase(), ZIP);
        POSTOFFICEBOX = new AddressItemType(22, "POSTOFFICEBOX");
        mapIntToEnum.put(POSTOFFICEBOX.value(), POSTOFFICEBOX);
        mapStringToEnum.put(POSTOFFICEBOX.m_str.toUpperCase(), POSTOFFICEBOX);
        CSP = new AddressItemType(23, "CSP");
        mapIntToEnum.put(CSP.value(), CSP);
        mapStringToEnum.put(CSP.m_str.toUpperCase(), CSP);
        DETAIL = new AddressItemType(24, "DETAIL");
        mapIntToEnum.put(DETAIL.value(), DETAIL);
        mapStringToEnum.put(DETAIL.m_str.toUpperCase(), DETAIL);
        java.util.Collection<AddressItemType> col = mapIntToEnum.values();
        m_Values = new AddressItemType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
