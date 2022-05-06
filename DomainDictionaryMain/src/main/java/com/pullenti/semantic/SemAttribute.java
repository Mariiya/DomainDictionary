/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Семантический атрибут
 */
public class SemAttribute {

    /**
     * Тип атрибута
     */
    public SemAttributeType typ = SemAttributeType.UNDEFINED;

    /**
     * Написание (нормализованное)
     */
    public String spelling;

    /**
     * Признак отрицания
     */
    public boolean not;

    @Override
    public String toString() {
        return spelling;
    }

    public static SemAttribute _new3112(boolean _arg1, SemAttributeType _arg2, String _arg3) {
        SemAttribute res = new SemAttribute();
        res.not = _arg1;
        res.typ = _arg2;
        res.spelling = _arg3;
        return res;
    }

    public static SemAttribute _new3144(String _arg1, SemAttributeType _arg2, boolean _arg3) {
        SemAttribute res = new SemAttribute();
        res.spelling = _arg1;
        res.typ = _arg2;
        res.not = _arg3;
        return res;
    }

    public static SemAttribute _new3146(SemAttributeType _arg1, String _arg2) {
        SemAttribute res = new SemAttribute();
        res.typ = _arg1;
        res.spelling = _arg2;
        return res;
    }
    public SemAttribute() {
    }
}
