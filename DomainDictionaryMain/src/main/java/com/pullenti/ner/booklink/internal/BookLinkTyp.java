/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink.internal;

public class BookLinkTyp implements Comparable<BookLinkTyp> {

    public static final BookLinkTyp UNDEFINED; // 0

    public static final BookLinkTyp NUMBER; // 1

    public static final BookLinkTyp PERSON; // 2

    public static final BookLinkTyp EDITORS; // 3

    public static final BookLinkTyp SOSTAVITEL; // 4

    public static final BookLinkTyp NAME; // 5

    public static final BookLinkTyp NAMETAIL; // 6

    public static final BookLinkTyp DELIMETER; // 7

    public static final BookLinkTyp TYPE; // 8

    public static final BookLinkTyp YEAR; // 9

    public static final BookLinkTyp PAGES; // 10

    public static final BookLinkTyp PAGERANGE; // 11

    public static final BookLinkTyp GEO; // 12

    public static final BookLinkTyp MISC; // 13

    public static final BookLinkTyp ANDOTHERS; // 14

    public static final BookLinkTyp TRANSLATE; // 15

    public static final BookLinkTyp PRESS; // 16

    public static final BookLinkTyp N; // 17

    public static final BookLinkTyp VOLUME; // 18

    public static final BookLinkTyp ELECTRONRES; // 19

    public static final BookLinkTyp URL; // 20

    public static final BookLinkTyp SEE; // 21

    public static final BookLinkTyp TAMZE; // 22


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BookLinkTyp(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BookLinkTyp v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BookLinkTyp> mapIntToEnum; 
    private static java.util.HashMap<String, BookLinkTyp> mapStringToEnum; 
    private static BookLinkTyp[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static BookLinkTyp of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BookLinkTyp item = new BookLinkTyp(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BookLinkTyp of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static BookLinkTyp[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, BookLinkTyp>();
        mapStringToEnum = new java.util.HashMap<String, BookLinkTyp>();
        UNDEFINED = new BookLinkTyp(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        NUMBER = new BookLinkTyp(1, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        PERSON = new BookLinkTyp(2, "PERSON");
        mapIntToEnum.put(PERSON.value(), PERSON);
        mapStringToEnum.put(PERSON.m_str.toUpperCase(), PERSON);
        EDITORS = new BookLinkTyp(3, "EDITORS");
        mapIntToEnum.put(EDITORS.value(), EDITORS);
        mapStringToEnum.put(EDITORS.m_str.toUpperCase(), EDITORS);
        SOSTAVITEL = new BookLinkTyp(4, "SOSTAVITEL");
        mapIntToEnum.put(SOSTAVITEL.value(), SOSTAVITEL);
        mapStringToEnum.put(SOSTAVITEL.m_str.toUpperCase(), SOSTAVITEL);
        NAME = new BookLinkTyp(5, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        NAMETAIL = new BookLinkTyp(6, "NAMETAIL");
        mapIntToEnum.put(NAMETAIL.value(), NAMETAIL);
        mapStringToEnum.put(NAMETAIL.m_str.toUpperCase(), NAMETAIL);
        DELIMETER = new BookLinkTyp(7, "DELIMETER");
        mapIntToEnum.put(DELIMETER.value(), DELIMETER);
        mapStringToEnum.put(DELIMETER.m_str.toUpperCase(), DELIMETER);
        TYPE = new BookLinkTyp(8, "TYPE");
        mapIntToEnum.put(TYPE.value(), TYPE);
        mapStringToEnum.put(TYPE.m_str.toUpperCase(), TYPE);
        YEAR = new BookLinkTyp(9, "YEAR");
        mapIntToEnum.put(YEAR.value(), YEAR);
        mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
        PAGES = new BookLinkTyp(10, "PAGES");
        mapIntToEnum.put(PAGES.value(), PAGES);
        mapStringToEnum.put(PAGES.m_str.toUpperCase(), PAGES);
        PAGERANGE = new BookLinkTyp(11, "PAGERANGE");
        mapIntToEnum.put(PAGERANGE.value(), PAGERANGE);
        mapStringToEnum.put(PAGERANGE.m_str.toUpperCase(), PAGERANGE);
        GEO = new BookLinkTyp(12, "GEO");
        mapIntToEnum.put(GEO.value(), GEO);
        mapStringToEnum.put(GEO.m_str.toUpperCase(), GEO);
        MISC = new BookLinkTyp(13, "MISC");
        mapIntToEnum.put(MISC.value(), MISC);
        mapStringToEnum.put(MISC.m_str.toUpperCase(), MISC);
        ANDOTHERS = new BookLinkTyp(14, "ANDOTHERS");
        mapIntToEnum.put(ANDOTHERS.value(), ANDOTHERS);
        mapStringToEnum.put(ANDOTHERS.m_str.toUpperCase(), ANDOTHERS);
        TRANSLATE = new BookLinkTyp(15, "TRANSLATE");
        mapIntToEnum.put(TRANSLATE.value(), TRANSLATE);
        mapStringToEnum.put(TRANSLATE.m_str.toUpperCase(), TRANSLATE);
        PRESS = new BookLinkTyp(16, "PRESS");
        mapIntToEnum.put(PRESS.value(), PRESS);
        mapStringToEnum.put(PRESS.m_str.toUpperCase(), PRESS);
        N = new BookLinkTyp(17, "N");
        mapIntToEnum.put(N.value(), N);
        mapStringToEnum.put(N.m_str.toUpperCase(), N);
        VOLUME = new BookLinkTyp(18, "VOLUME");
        mapIntToEnum.put(VOLUME.value(), VOLUME);
        mapStringToEnum.put(VOLUME.m_str.toUpperCase(), VOLUME);
        ELECTRONRES = new BookLinkTyp(19, "ELECTRONRES");
        mapIntToEnum.put(ELECTRONRES.value(), ELECTRONRES);
        mapStringToEnum.put(ELECTRONRES.m_str.toUpperCase(), ELECTRONRES);
        URL = new BookLinkTyp(20, "URL");
        mapIntToEnum.put(URL.value(), URL);
        mapStringToEnum.put(URL.m_str.toUpperCase(), URL);
        SEE = new BookLinkTyp(21, "SEE");
        mapIntToEnum.put(SEE.value(), SEE);
        mapStringToEnum.put(SEE.m_str.toUpperCase(), SEE);
        TAMZE = new BookLinkTyp(22, "TAMZE");
        mapIntToEnum.put(TAMZE.value(), TAMZE);
        mapStringToEnum.put(TAMZE.m_str.toUpperCase(), TAMZE);
        java.util.Collection<BookLinkTyp> col = mapIntToEnum.values();
        m_Values = new BookLinkTyp[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
