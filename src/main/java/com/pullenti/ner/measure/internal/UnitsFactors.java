/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

// Степени десяток
public class UnitsFactors implements Comparable<UnitsFactors> {

    public static final UnitsFactors NO; // 0

    public static final UnitsFactors KILO; // 3

    public static final UnitsFactors MEGA; // 6

    public static final UnitsFactors GIGA; // 9

    public static final UnitsFactors TERA; // 12

    public static final UnitsFactors DECI; // -1

    public static final UnitsFactors CENTI; // -2

    public static final UnitsFactors MILLI; // -3

    public static final UnitsFactors MICRO; // -6

    public static final UnitsFactors NANO; // -9

    public static final UnitsFactors PICO; // -12


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private UnitsFactors(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(UnitsFactors v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, UnitsFactors> mapIntToEnum; 
    private static java.util.HashMap<String, UnitsFactors> mapStringToEnum; 
    private static UnitsFactors[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static UnitsFactors of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        UnitsFactors item = new UnitsFactors(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static UnitsFactors of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static UnitsFactors[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, UnitsFactors>();
        mapStringToEnum = new java.util.HashMap<String, UnitsFactors>();
        NO = new UnitsFactors(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        KILO = new UnitsFactors(3, "KILO");
        mapIntToEnum.put(KILO.value(), KILO);
        mapStringToEnum.put(KILO.m_str.toUpperCase(), KILO);
        MEGA = new UnitsFactors(6, "MEGA");
        mapIntToEnum.put(MEGA.value(), MEGA);
        mapStringToEnum.put(MEGA.m_str.toUpperCase(), MEGA);
        GIGA = new UnitsFactors(9, "GIGA");
        mapIntToEnum.put(GIGA.value(), GIGA);
        mapStringToEnum.put(GIGA.m_str.toUpperCase(), GIGA);
        TERA = new UnitsFactors(12, "TERA");
        mapIntToEnum.put(TERA.value(), TERA);
        mapStringToEnum.put(TERA.m_str.toUpperCase(), TERA);
        DECI = new UnitsFactors(-1, "DECI");
        mapIntToEnum.put(DECI.value(), DECI);
        mapStringToEnum.put(DECI.m_str.toUpperCase(), DECI);
        CENTI = new UnitsFactors(-2, "CENTI");
        mapIntToEnum.put(CENTI.value(), CENTI);
        mapStringToEnum.put(CENTI.m_str.toUpperCase(), CENTI);
        MILLI = new UnitsFactors(-3, "MILLI");
        mapIntToEnum.put(MILLI.value(), MILLI);
        mapStringToEnum.put(MILLI.m_str.toUpperCase(), MILLI);
        MICRO = new UnitsFactors(-6, "MICRO");
        mapIntToEnum.put(MICRO.value(), MICRO);
        mapStringToEnum.put(MICRO.m_str.toUpperCase(), MICRO);
        NANO = new UnitsFactors(-9, "NANO");
        mapIntToEnum.put(NANO.value(), NANO);
        mapStringToEnum.put(NANO.m_str.toUpperCase(), NANO);
        PICO = new UnitsFactors(-12, "PICO");
        mapIntToEnum.put(PICO.value(), PICO);
        mapStringToEnum.put(PICO.m_str.toUpperCase(), PICO);
        java.util.Collection<UnitsFactors> col = mapIntToEnum.values();
        m_Values = new UnitsFactors[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
