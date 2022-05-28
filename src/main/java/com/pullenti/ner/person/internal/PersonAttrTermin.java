/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonAttrTermin extends com.pullenti.ner.core.Termin {

    public PersonAttrTermin(String v, com.pullenti.morph.MorphLang _lang) {
        super(null, _lang, false);
        this.initByNormalText(v, _lang);
    }

    public PersonAttrTerminType typ = PersonAttrTerminType.OTHER;

    public PersonAttrTerminType2 typ2 = PersonAttrTerminType2.UNDEFINED;

    public boolean canBeUniqueIdentifier;

    public int canHasPersonAfter;

    public boolean canBeSameSurname;

    public boolean canBeIndependant;

    public boolean isBoss;

    public boolean isKin;

    public boolean isMilitaryRank;

    public boolean isNation;

    public boolean isPost;

    public boolean isProfession;

    public boolean isDoubt;

    public static PersonAttrTermin _new2601(String _arg1, PersonAttrTerminType _arg2) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        return res;
    }

    public static PersonAttrTermin _new2602(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static PersonAttrTermin _new2603(String _arg1, PersonAttrTerminType _arg2, com.pullenti.morph.MorphGender _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static PersonAttrTermin _new2604(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType _arg3, com.pullenti.morph.MorphGender _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static PersonAttrTermin _new2612(String _arg1, String _arg2, PersonAttrTerminType2 _arg3, PersonAttrTerminType _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.typ2 = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2613(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, PersonAttrTerminType2 _arg4, PersonAttrTerminType _arg5) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.setCanonicText(_arg3);
        res.typ2 = _arg4;
        res.typ = _arg5;
        return res;
    }

    public static PersonAttrTermin _new2618(String _arg1, PersonAttrTerminType2 _arg2, PersonAttrTerminType _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ2 = _arg2;
        res.typ = _arg3;
        return res;
    }

    public static PersonAttrTermin _new2619(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType2 _arg3, PersonAttrTerminType _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ2 = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2638(String _arg1, String _arg2, PersonAttrTerminType _arg3, PersonAttrTerminType2 _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.typ = _arg3;
        res.typ2 = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2640(String _arg1, PersonAttrTerminType2 _arg2, PersonAttrTerminType _arg3, com.pullenti.morph.MorphLang _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ2 = _arg2;
        res.typ = _arg3;
        res.lang = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2645(String _arg1, PersonAttrTerminType _arg2, com.pullenti.morph.MorphLang _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        res.lang = _arg3;
        return res;
    }
    public PersonAttrTermin() {
        super();
    }
}
