/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class Condition {

    public com.pullenti.ner.Token geoBeforeToken;

    public boolean pureGeoBefore;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (geoBeforeToken != null) 
            tmp.append("GeoBefore=").append(geoBeforeToken);
        return tmp.toString();
    }

    public boolean check() {
        if (geoBeforeToken != null) {
            if (MiscLocationHelper.checkGeoObjectBefore(geoBeforeToken, pureGeoBefore)) 
                return true;
        }
        return false;
    }

    public static Condition _new207(com.pullenti.ner.Token _arg1, boolean _arg2) {
        Condition res = new Condition();
        res.geoBeforeToken = _arg1;
        res.pureGeoBefore = _arg2;
        return res;
    }

    public static Condition _new1228(com.pullenti.ner.Token _arg1) {
        Condition res = new Condition();
        res.geoBeforeToken = _arg1;
        return res;
    }
    public Condition() {
    }
}
