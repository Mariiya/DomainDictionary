/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class TerrTermin extends com.pullenti.ner.core.Termin {

    public TerrTermin(String source, com.pullenti.morph.MorphLang _lang) {
        super(null, _lang, false);
        this.initByNormalText(source, _lang);
    }

    public boolean isState;

    public boolean isRegion;

    public boolean isAdjective;

    public boolean isAlwaysPrefix;

    public boolean isDoubt;

    public boolean isMoscowRegion;

    public boolean isStrong;

    public boolean isSpecificPrefix;

    public boolean isSovet;

    public static TerrTermin _new1339(String _arg1, com.pullenti.morph.MorphGender _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.setGender(_arg2);
        return res;
    }

    public static TerrTermin _new1340(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.morph.MorphGender _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.setGender(_arg3);
        return res;
    }

    public static TerrTermin _new1341(String _arg1, boolean _arg2, com.pullenti.morph.MorphGender _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static TerrTermin _new1342(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, com.pullenti.morph.MorphGender _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static TerrTermin _new1344(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isDoubt = _arg3;
        return res;
    }

    public static TerrTermin _new1345(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static TerrTermin _new1346(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        return res;
    }

    public static TerrTermin _new1347(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        return res;
    }

    public static TerrTermin _new1348(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1349(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1350(String _arg1, boolean _arg2, com.pullenti.morph.MorphGender _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static TerrTermin _new1352(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        return res;
    }

    public static TerrTermin _new1353(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, com.pullenti.morph.MorphGender _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static TerrTermin _new1354(String _arg1, boolean _arg2, String _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.acronym = _arg3;
        return res;
    }

    public static TerrTermin _new1355(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static TerrTermin _new1361(String _arg1, boolean _arg2, boolean _arg3, com.pullenti.morph.MorphGender _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAlwaysPrefix = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static TerrTermin _new1364(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4, com.pullenti.morph.MorphGender _arg5) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAlwaysPrefix = _arg4;
        res.setGender(_arg5);
        return res;
    }

    public static TerrTermin _new1368(String _arg1, boolean _arg2, com.pullenti.morph.MorphGender _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.setGender(_arg3);
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1369(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAlwaysPrefix = _arg3;
        return res;
    }

    public static TerrTermin _new1374(String _arg1, boolean _arg2, com.pullenti.morph.MorphGender _arg3, String _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.setGender(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static TerrTermin _new1375(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isStrong = _arg3;
        return res;
    }

    public static TerrTermin _new1378(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isStrong = _arg4;
        return res;
    }

    public static TerrTermin _new1382(String _arg1, String _arg2, boolean _arg3, com.pullenti.morph.MorphGender _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.isSovet = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static TerrTermin _new1385(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1386(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1387(String _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isSpecificPrefix = _arg3;
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1388(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isSpecificPrefix = _arg4;
        res.isAlwaysPrefix = _arg5;
        return res;
    }

    public static TerrTermin _new1389(String _arg1, String _arg2, com.pullenti.morph.MorphGender _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static TerrTermin _new1390(String _arg1, String _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        res.isRegion = _arg3;
        return res;
    }

    public static TerrTermin _new1391(String _arg1, String _arg2, String _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.acronym = _arg3;
        res.isRegion = _arg4;
        return res;
    }

    public static TerrTermin _new1392(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isMoscowRegion = _arg2;
        return res;
    }
    public TerrTermin() {
        super();
    }
}
