/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

// Простая единица измерения (для составной единицы используется UnitToken)
public class Unit {

    public String nameCyr;

    public String nameLat;

    public String fullnameCyr;

    public String fullnameLat;

    public com.pullenti.ner.measure.MeasureKind kind = com.pullenti.ner.measure.MeasureKind.UNDEFINED;

    public Unit(String _nameCyr, String _nameLat, String fnameCyr, String fnameLan) {
        nameCyr = _nameCyr;
        nameLat = _nameLat;
        fullnameCyr = fnameCyr;
        fullnameLat = fnameLan;
    }

    @Override
    public String toString() {
        return nameCyr;
    }

    public Unit baseUnit;

    public Unit multUnit;

    public double baseMultiplier;

    public UnitsFactors factor = UnitsFactors.NO;

    public java.util.ArrayList<String> keywords = new java.util.ArrayList<String>();

    public java.util.ArrayList<Unit> psevdo = new java.util.ArrayList<Unit>();

    public static Unit _new1801(String _arg1, String _arg2, String _arg3, String _arg4, com.pullenti.ner.measure.MeasureKind _arg5) {
        Unit res = new Unit(_arg1, _arg2, _arg3, _arg4);
        res.kind = _arg5;
        return res;
    }

    public static Unit _new1807(String _arg1, String _arg2, String _arg3, String _arg4, Unit _arg5, double _arg6, com.pullenti.ner.measure.MeasureKind _arg7) {
        Unit res = new Unit(_arg1, _arg2, _arg3, _arg4);
        res.baseUnit = _arg5;
        res.baseMultiplier = _arg6;
        res.kind = _arg7;
        return res;
    }

    public static Unit _new1856(String _arg1, String _arg2, String _arg3, String _arg4, Unit _arg5, double _arg6) {
        Unit res = new Unit(_arg1, _arg2, _arg3, _arg4);
        res.baseUnit = _arg5;
        res.baseMultiplier = _arg6;
        return res;
    }

    public static Unit _new1865(String _arg1, String _arg2, String _arg3, String _arg4, Unit _arg5, Unit _arg6) {
        Unit res = new Unit(_arg1, _arg2, _arg3, _arg4);
        res.baseUnit = _arg5;
        res.multUnit = _arg6;
        return res;
    }

    public static Unit _new1894(String _arg1, String _arg2, String _arg3, String _arg4, UnitsFactors _arg5, double _arg6, Unit _arg7, com.pullenti.ner.measure.MeasureKind _arg8, java.util.ArrayList<String> _arg9) {
        Unit res = new Unit(_arg1, _arg2, _arg3, _arg4);
        res.factor = _arg5;
        res.baseMultiplier = _arg6;
        res.baseUnit = _arg7;
        res.kind = _arg8;
        res.keywords = _arg9;
        return res;
    }
    public Unit() {
    }
}
