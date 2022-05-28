/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class SentItemType implements Comparable<SentItemType> {

    public static final SentItemType UNDEFINED; // 0

    public static final SentItemType NOUN; // 1

    public static final SentItemType VERB; // 2

    public static final SentItemType CONJ; // 3

    public static final SentItemType DELIM; // 4

    public static final SentItemType ADVERB; // 5

    public static final SentItemType DEEPART; // 6

    public static final SentItemType PARTBEFORE; // 7

    public static final SentItemType PARTAFTER; // 8

    public static final SentItemType SUBSENT; // 9

    public static final SentItemType FORMULA; // 10


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SentItemType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SentItemType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SentItemType> mapIntToEnum; 
    private static java.util.HashMap<String, SentItemType> mapStringToEnum; 
    private static SentItemType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SentItemType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SentItemType item = new SentItemType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SentItemType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SentItemType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SentItemType>();
        mapStringToEnum = new java.util.HashMap<String, SentItemType>();
        UNDEFINED = new SentItemType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        NOUN = new SentItemType(1, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        VERB = new SentItemType(2, "VERB");
        mapIntToEnum.put(VERB.value(), VERB);
        mapStringToEnum.put(VERB.m_str.toUpperCase(), VERB);
        CONJ = new SentItemType(3, "CONJ");
        mapIntToEnum.put(CONJ.value(), CONJ);
        mapStringToEnum.put(CONJ.m_str.toUpperCase(), CONJ);
        DELIM = new SentItemType(4, "DELIM");
        mapIntToEnum.put(DELIM.value(), DELIM);
        mapStringToEnum.put(DELIM.m_str.toUpperCase(), DELIM);
        ADVERB = new SentItemType(5, "ADVERB");
        mapIntToEnum.put(ADVERB.value(), ADVERB);
        mapStringToEnum.put(ADVERB.m_str.toUpperCase(), ADVERB);
        DEEPART = new SentItemType(6, "DEEPART");
        mapIntToEnum.put(DEEPART.value(), DEEPART);
        mapStringToEnum.put(DEEPART.m_str.toUpperCase(), DEEPART);
        PARTBEFORE = new SentItemType(7, "PARTBEFORE");
        mapIntToEnum.put(PARTBEFORE.value(), PARTBEFORE);
        mapStringToEnum.put(PARTBEFORE.m_str.toUpperCase(), PARTBEFORE);
        PARTAFTER = new SentItemType(8, "PARTAFTER");
        mapIntToEnum.put(PARTAFTER.value(), PARTAFTER);
        mapStringToEnum.put(PARTAFTER.m_str.toUpperCase(), PARTAFTER);
        SUBSENT = new SentItemType(9, "SUBSENT");
        mapIntToEnum.put(SUBSENT.value(), SUBSENT);
        mapStringToEnum.put(SUBSENT.m_str.toUpperCase(), SUBSENT);
        FORMULA = new SentItemType(10, "FORMULA");
        mapIntToEnum.put(FORMULA.value(), FORMULA);
        mapStringToEnum.put(FORMULA.m_str.toUpperCase(), FORMULA);
        java.util.Collection<SentItemType> col = mapIntToEnum.values();
        m_Values = new SentItemType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
