/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Тип дома
 */
public class AddressHouseType implements Comparable<AddressHouseType> {

    public static final AddressHouseType UNDEFINED; // 0

    /**
     * Владение
     */
    public static final AddressHouseType ESTATE; // 1

    /**
     * Просто дом
     */
    public static final AddressHouseType HOUSE; // 2

    /**
     * Домовладение
     */
    public static final AddressHouseType HOUSEESTATE; // 3


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private AddressHouseType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(AddressHouseType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, AddressHouseType> mapIntToEnum; 
    private static java.util.HashMap<String, AddressHouseType> mapStringToEnum; 
    private static AddressHouseType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static AddressHouseType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        AddressHouseType item = new AddressHouseType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static AddressHouseType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static AddressHouseType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, AddressHouseType>();
        mapStringToEnum = new java.util.HashMap<String, AddressHouseType>();
        UNDEFINED = new AddressHouseType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ESTATE = new AddressHouseType(1, "ESTATE");
        mapIntToEnum.put(ESTATE.value(), ESTATE);
        mapStringToEnum.put(ESTATE.m_str.toUpperCase(), ESTATE);
        HOUSE = new AddressHouseType(2, "HOUSE");
        mapIntToEnum.put(HOUSE.value(), HOUSE);
        mapStringToEnum.put(HOUSE.m_str.toUpperCase(), HOUSE);
        HOUSEESTATE = new AddressHouseType(3, "HOUSEESTATE");
        mapIntToEnum.put(HOUSEESTATE.value(), HOUSEESTATE);
        mapStringToEnum.put(HOUSEESTATE.m_str.toUpperCase(), HOUSEESTATE);
        java.util.Collection<AddressHouseType> col = mapIntToEnum.values();
        m_Values = new AddressHouseType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
