/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.mail;

/**
 * Тип блока письма
 */
public class MailKind implements Comparable<MailKind> {

    public static final MailKind UNDEFINED; // 0

    /**
     * Заголовок
     */
    public static final MailKind HEAD; // 1

    /**
     * Приветствие
     */
    public static final MailKind HELLO; // 2

    /**
     * Содержимое
     */
    public static final MailKind BODY; // 3

    /**
     * Подпись
     */
    public static final MailKind TAIL; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MailKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MailKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MailKind> mapIntToEnum; 
    private static java.util.HashMap<String, MailKind> mapStringToEnum; 
    private static MailKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MailKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MailKind item = new MailKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MailKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MailKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MailKind>();
        mapStringToEnum = new java.util.HashMap<String, MailKind>();
        UNDEFINED = new MailKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        HEAD = new MailKind(1, "HEAD");
        mapIntToEnum.put(HEAD.value(), HEAD);
        mapStringToEnum.put(HEAD.m_str.toUpperCase(), HEAD);
        HELLO = new MailKind(2, "HELLO");
        mapIntToEnum.put(HELLO.value(), HELLO);
        mapStringToEnum.put(HELLO.m_str.toUpperCase(), HELLO);
        BODY = new MailKind(3, "BODY");
        mapIntToEnum.put(BODY.value(), BODY);
        mapStringToEnum.put(BODY.m_str.toUpperCase(), BODY);
        TAIL = new MailKind(4, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
        java.util.Collection<MailKind> col = mapIntToEnum.values();
        m_Values = new MailKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
