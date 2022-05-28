/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemTypeTyp implements Comparable<OrgItemTypeTyp> {

    public static final OrgItemTypeTyp UNDEFINED; // 0

    public static final OrgItemTypeTyp ORG; // 1

    public static final OrgItemTypeTyp PREFIX; // 2

    public static final OrgItemTypeTyp DEP; // 3

    public static final OrgItemTypeTyp DEPADD; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private OrgItemTypeTyp(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(OrgItemTypeTyp v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, OrgItemTypeTyp> mapIntToEnum; 
    private static java.util.HashMap<String, OrgItemTypeTyp> mapStringToEnum; 
    private static OrgItemTypeTyp[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static OrgItemTypeTyp of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        OrgItemTypeTyp item = new OrgItemTypeTyp(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static OrgItemTypeTyp of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static OrgItemTypeTyp[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, OrgItemTypeTyp>();
        mapStringToEnum = new java.util.HashMap<String, OrgItemTypeTyp>();
        UNDEFINED = new OrgItemTypeTyp(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        ORG = new OrgItemTypeTyp(1, "ORG");
        mapIntToEnum.put(ORG.value(), ORG);
        mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
        PREFIX = new OrgItemTypeTyp(2, "PREFIX");
        mapIntToEnum.put(PREFIX.value(), PREFIX);
        mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
        DEP = new OrgItemTypeTyp(3, "DEP");
        mapIntToEnum.put(DEP.value(), DEP);
        mapStringToEnum.put(DEP.m_str.toUpperCase(), DEP);
        DEPADD = new OrgItemTypeTyp(4, "DEPADD");
        mapIntToEnum.put(DEPADD.value(), DEPADD);
        mapStringToEnum.put(DEPADD.m_str.toUpperCase(), DEPADD);
        java.util.Collection<OrgItemTypeTyp> col = mapIntToEnum.values();
        m_Values = new OrgItemTypeTyp[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
