/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class ILTypes implements Comparable<ILTypes> {

    public static final ILTypes UNDEFINED; // 0

    public static final ILTypes APPENDIX; // 1

    public static final ILTypes APPROVED; // 2

    public static final ILTypes ORGANIZATION; // 3

    public static final ILTypes REGNUMBER; // 4

    public static final ILTypes DATE; // 5

    public static final ILTypes GEO; // 6

    public static final ILTypes PERSON; // 7

    public static final ILTypes TYP; // 8

    public static final ILTypes VERB; // 9

    public static final ILTypes DIRECTIVE; // 10

    public static final ILTypes QUESTION; // 11


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ILTypes(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ILTypes v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ILTypes> mapIntToEnum; 
    private static java.util.HashMap<String, ILTypes> mapStringToEnum; 
    private static ILTypes[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static ILTypes of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ILTypes item = new ILTypes(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ILTypes of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static ILTypes[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, ILTypes>();
        mapStringToEnum = new java.util.HashMap<String, ILTypes>();
        UNDEFINED = new ILTypes(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        APPENDIX = new ILTypes(1, "APPENDIX");
        mapIntToEnum.put(APPENDIX.value(), APPENDIX);
        mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
        APPROVED = new ILTypes(2, "APPROVED");
        mapIntToEnum.put(APPROVED.value(), APPROVED);
        mapStringToEnum.put(APPROVED.m_str.toUpperCase(), APPROVED);
        ORGANIZATION = new ILTypes(3, "ORGANIZATION");
        mapIntToEnum.put(ORGANIZATION.value(), ORGANIZATION);
        mapStringToEnum.put(ORGANIZATION.m_str.toUpperCase(), ORGANIZATION);
        REGNUMBER = new ILTypes(4, "REGNUMBER");
        mapIntToEnum.put(REGNUMBER.value(), REGNUMBER);
        mapStringToEnum.put(REGNUMBER.m_str.toUpperCase(), REGNUMBER);
        DATE = new ILTypes(5, "DATE");
        mapIntToEnum.put(DATE.value(), DATE);
        mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
        GEO = new ILTypes(6, "GEO");
        mapIntToEnum.put(GEO.value(), GEO);
        mapStringToEnum.put(GEO.m_str.toUpperCase(), GEO);
        PERSON = new ILTypes(7, "PERSON");
        mapIntToEnum.put(PERSON.value(), PERSON);
        mapStringToEnum.put(PERSON.m_str.toUpperCase(), PERSON);
        TYP = new ILTypes(8, "TYP");
        mapIntToEnum.put(TYP.value(), TYP);
        mapStringToEnum.put(TYP.m_str.toUpperCase(), TYP);
        VERB = new ILTypes(9, "VERB");
        mapIntToEnum.put(VERB.value(), VERB);
        mapStringToEnum.put(VERB.m_str.toUpperCase(), VERB);
        DIRECTIVE = new ILTypes(10, "DIRECTIVE");
        mapIntToEnum.put(DIRECTIVE.value(), DIRECTIVE);
        mapStringToEnum.put(DIRECTIVE.m_str.toUpperCase(), DIRECTIVE);
        QUESTION = new ILTypes(11, "QUESTION");
        mapIntToEnum.put(QUESTION.value(), QUESTION);
        mapStringToEnum.put(QUESTION.m_str.toUpperCase(), QUESTION);
        java.util.Collection<ILTypes> col = mapIntToEnum.values();
        m_Values = new ILTypes[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
