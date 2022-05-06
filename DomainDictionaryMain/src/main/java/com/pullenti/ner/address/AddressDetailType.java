/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Детализация местоположения
 */
public class AddressDetailType implements Comparable<AddressDetailType> {

    public static final AddressDetailType UNDEFINED; // 0

    /**
     * Пересечение
     */
    public static final AddressDetailType CROSS; // 1

    /**
     * Возле
     */
    public static final AddressDetailType NEAR; // 2

    /**
     * Общежитие
     */
    public static final AddressDetailType HOSTEL; // 3

    /**
     * На север
     */
    public static final AddressDetailType NORTH; // 4

    /**
     * На юг
     */
    public static final AddressDetailType SOUTH; // 5

    /**
     * На запад
     */
    public static final AddressDetailType WEST; // 6

    /**
     * На восток
     */
    public static final AddressDetailType EAST; // 7

    /**
     * На сереро-запад
     */
    public static final AddressDetailType NORTHWEST; // 8

    /**
     * На северо-восток
     */
    public static final AddressDetailType NORTHEAST; // 9

    /**
     * На юго-запад
     */
    public static final AddressDetailType SOUTHWEST; // 10

    /**
     * На юго-восток
     */
    public static final AddressDetailType SOUTHEAST; // 11


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private AddressDetailType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(AddressDetailType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, AddressDetailType> mapIntToEnum; 
    private static java.util.HashMap<String, AddressDetailType> mapStringToEnum; 
    private static AddressDetailType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static AddressDetailType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        AddressDetailType item = new AddressDetailType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static AddressDetailType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static AddressDetailType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, AddressDetailType>();
        mapStringToEnum = new java.util.HashMap<String, AddressDetailType>();
        UNDEFINED = new AddressDetailType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        CROSS = new AddressDetailType(1, "CROSS");
        mapIntToEnum.put(CROSS.value(), CROSS);
        mapStringToEnum.put(CROSS.m_str.toUpperCase(), CROSS);
        NEAR = new AddressDetailType(2, "NEAR");
        mapIntToEnum.put(NEAR.value(), NEAR);
        mapStringToEnum.put(NEAR.m_str.toUpperCase(), NEAR);
        HOSTEL = new AddressDetailType(3, "HOSTEL");
        mapIntToEnum.put(HOSTEL.value(), HOSTEL);
        mapStringToEnum.put(HOSTEL.m_str.toUpperCase(), HOSTEL);
        NORTH = new AddressDetailType(4, "NORTH");
        mapIntToEnum.put(NORTH.value(), NORTH);
        mapStringToEnum.put(NORTH.m_str.toUpperCase(), NORTH);
        SOUTH = new AddressDetailType(5, "SOUTH");
        mapIntToEnum.put(SOUTH.value(), SOUTH);
        mapStringToEnum.put(SOUTH.m_str.toUpperCase(), SOUTH);
        WEST = new AddressDetailType(6, "WEST");
        mapIntToEnum.put(WEST.value(), WEST);
        mapStringToEnum.put(WEST.m_str.toUpperCase(), WEST);
        EAST = new AddressDetailType(7, "EAST");
        mapIntToEnum.put(EAST.value(), EAST);
        mapStringToEnum.put(EAST.m_str.toUpperCase(), EAST);
        NORTHWEST = new AddressDetailType(8, "NORTHWEST");
        mapIntToEnum.put(NORTHWEST.value(), NORTHWEST);
        mapStringToEnum.put(NORTHWEST.m_str.toUpperCase(), NORTHWEST);
        NORTHEAST = new AddressDetailType(9, "NORTHEAST");
        mapIntToEnum.put(NORTHEAST.value(), NORTHEAST);
        mapStringToEnum.put(NORTHEAST.m_str.toUpperCase(), NORTHEAST);
        SOUTHWEST = new AddressDetailType(10, "SOUTHWEST");
        mapIntToEnum.put(SOUTHWEST.value(), SOUTHWEST);
        mapStringToEnum.put(SOUTHWEST.m_str.toUpperCase(), SOUTHWEST);
        SOUTHEAST = new AddressDetailType(11, "SOUTHEAST");
        mapIntToEnum.put(SOUTHEAST.value(), SOUTHEAST);
        mapStringToEnum.put(SOUTHEAST.m_str.toUpperCase(), SOUTHEAST);
        java.util.Collection<AddressDetailType> col = mapIntToEnum.values();
        m_Values = new AddressDetailType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
