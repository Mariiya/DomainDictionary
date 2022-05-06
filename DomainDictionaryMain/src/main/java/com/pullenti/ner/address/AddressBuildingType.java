/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Тип строения
 */
public class AddressBuildingType implements Comparable<AddressBuildingType> {

    public static final AddressBuildingType UNDEFINED; // 0

    /**
     * Строение
     */
    public static final AddressBuildingType BUILDING; // 1

    /**
     * Сооружение
     */
    public static final AddressBuildingType CONSTRUCTION; // 2

    /**
     * Литера
     */
    public static final AddressBuildingType LITER; // 3


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private AddressBuildingType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(AddressBuildingType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, AddressBuildingType> mapIntToEnum; 
    private static java.util.HashMap<String, AddressBuildingType> mapStringToEnum; 
    private static AddressBuildingType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static AddressBuildingType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        AddressBuildingType item = new AddressBuildingType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static AddressBuildingType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static AddressBuildingType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, AddressBuildingType>();
        mapStringToEnum = new java.util.HashMap<String, AddressBuildingType>();
        UNDEFINED = new AddressBuildingType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        BUILDING = new AddressBuildingType(1, "BUILDING");
        mapIntToEnum.put(BUILDING.value(), BUILDING);
        mapStringToEnum.put(BUILDING.m_str.toUpperCase(), BUILDING);
        CONSTRUCTION = new AddressBuildingType(2, "CONSTRUCTION");
        mapIntToEnum.put(CONSTRUCTION.value(), CONSTRUCTION);
        mapStringToEnum.put(CONSTRUCTION.m_str.toUpperCase(), CONSTRUCTION);
        LITER = new AddressBuildingType(3, "LITER");
        mapIntToEnum.put(LITER.value(), LITER);
        mapStringToEnum.put(LITER.m_str.toUpperCase(), LITER);
        java.util.Collection<AddressBuildingType> col = mapIntToEnum.values();
        m_Values = new AddressBuildingType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
