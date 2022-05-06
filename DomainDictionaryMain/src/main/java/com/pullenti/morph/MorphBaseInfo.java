/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Базовая часть морфологической информации
 * 
 * основная морф.информация
 */
public class MorphBaseInfo {

    public MorphClass _getClass() {
        return m_Cla;
    }

    public MorphClass _setClass(MorphClass value) {
        m_Cla = value;
        return value;
    }


    private MorphClass m_Cla = new MorphClass();

    private MorphGender _gender = MorphGender.UNDEFINED;

    public MorphGender getGender() {
        return _gender;
    }

    public MorphGender setGender(MorphGender value) {
        _gender = value;
        return _gender;
    }


    private MorphNumber _number = MorphNumber.UNDEFINED;

    public MorphNumber getNumber() {
        return _number;
    }

    public MorphNumber setNumber(MorphNumber value) {
        _number = value;
        return _number;
    }


    public MorphCase getCase() {
        return m_Cas;
    }

    public MorphCase setCase(MorphCase value) {
        m_Cas = value;
        return value;
    }


    private MorphCase m_Cas = new MorphCase();

    public MorphLang getLanguage() {
        return m_Lang;
    }

    public MorphLang setLanguage(MorphLang value) {
        m_Lang = value;
        return value;
    }


    private MorphLang m_Lang = new MorphLang();

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!_getClass().isUndefined()) 
            res.append(this._getClass().toString()).append(" ");
        if (getNumber() != MorphNumber.UNDEFINED) {
            if (getNumber() == MorphNumber.SINGULAR) 
                res.append("ед.ч. ");
            else if (getNumber() == MorphNumber.PLURAL) 
                res.append("мн.ч. ");
            else 
                res.append("ед.мн.ч. ");
        }
        if (getGender() != MorphGender.UNDEFINED) {
            if (getGender() == MorphGender.MASCULINE) 
                res.append("муж.р. ");
            else if (getGender() == MorphGender.NEUTER) 
                res.append("ср.р. ");
            else if (getGender() == MorphGender.FEMINIE) 
                res.append("жен.р. ");
            else if ((getGender().value()) == ((short)((MorphGender.MASCULINE.value()) | (MorphGender.NEUTER.value())))) 
                res.append("муж.ср.р. ");
            else if ((getGender().value()) == ((short)((MorphGender.FEMINIE.value()) | (MorphGender.NEUTER.value())))) 
                res.append("жен.ср.р. ");
            else if (((int)getGender().value()) == 7) 
                res.append("муж.жен.ср.р. ");
            else if ((getGender().value()) == ((short)((MorphGender.FEMINIE.value()) | (MorphGender.MASCULINE.value())))) 
                res.append("муж.жен.р. ");
        }
        if (!getCase().isUndefined()) 
            res.append(this.getCase().toString()).append(" ");
        if (!getLanguage().isUndefined() && !getLanguage().equals(MorphLang.RU)) 
            res.append(this.getLanguage().toString()).append(" ");
        return com.pullenti.unisharp.Utils.trimEnd(res.toString());
    }

    public void copyFrom(MorphBaseInfo src) {
        MorphClass cla = new MorphClass();
        cla.value = src._getClass().value;
        _setClass(cla);
        setGender(src.getGender());
        setNumber(src.getNumber());
        MorphCase cas = new MorphCase();
        cas.value = src.getCase().value;
        setCase(cas);
        MorphLang lng = new MorphLang();
        lng.value = src.getLanguage().value;
        setLanguage(lng);
    }

    public boolean containsAttr(String attrValue, MorphClass cla) {
        return false;
    }

    public boolean checkAccord(MorphBaseInfo v, boolean ignoreGender, boolean ignoreNumber) {
        if (!v.getLanguage().equals(this.getLanguage())) {
            if (v.getLanguage().isUndefined() && getLanguage().isUndefined()) 
                return false;
        }
        short num = (short)((v.getNumber().value()) & (getNumber().value()));
        if ((num) == (MorphNumber.UNDEFINED.value()) && !ignoreNumber) {
            if (v.getNumber() != MorphNumber.UNDEFINED && getNumber() != MorphNumber.UNDEFINED) {
                if (v.getNumber() == MorphNumber.SINGULAR && v.getCase().isGenitive()) {
                    if (getNumber() == MorphNumber.PLURAL && getCase().isGenitive()) {
                        if (((short)((v.getGender().value()) & (MorphGender.MASCULINE.value()))) == (MorphGender.MASCULINE.value())) 
                            return true;
                    }
                }
                return false;
            }
        }
        if (!ignoreGender && (num) != (MorphNumber.PLURAL.value())) {
            if (((short)((v.getGender().value()) & (this.getGender().value()))) == (MorphGender.UNDEFINED.value())) {
                if (v.getGender() != MorphGender.UNDEFINED && getGender() != MorphGender.UNDEFINED) 
                    return false;
            }
        }
        if (((MorphCase.ooBitand(v.getCase(), this.getCase()))).isUndefined()) {
            if (!v.getCase().isUndefined() && !getCase().isUndefined()) 
                return false;
        }
        return true;
    }

    public static MorphBaseInfo _new170(MorphClass _arg1, MorphNumber _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setNumber(_arg2);
        return res;
    }

    public static MorphBaseInfo _new171(MorphClass _arg1, MorphGender _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        return res;
    }

    public static MorphBaseInfo _new214(MorphCase _arg1, MorphClass _arg2, MorphNumber _arg3, MorphGender _arg4) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res._setClass(_arg2);
        res.setNumber(_arg3);
        res.setGender(_arg4);
        return res;
    }

    public static MorphBaseInfo _new416(MorphClass _arg1, MorphGender _arg2, MorphNumber _arg3, MorphCase _arg4, MorphLang _arg5) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        res.setCase(_arg4);
        res.setLanguage(_arg5);
        return res;
    }

    public static MorphBaseInfo _new507(MorphClass _arg1, MorphGender _arg2, MorphNumber _arg3, MorphLang _arg4) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        res.setLanguage(_arg4);
        return res;
    }

    public static MorphBaseInfo _new508(MorphGender _arg1, MorphCase _arg2, MorphNumber _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setCase(_arg2);
        res.setNumber(_arg3);
        return res;
    }

    public static MorphBaseInfo _new510(MorphClass _arg1, MorphCase _arg2, MorphNumber _arg3, MorphGender _arg4) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setCase(_arg2);
        res.setNumber(_arg3);
        res.setGender(_arg4);
        return res;
    }

    public static MorphBaseInfo _new511(MorphClass _arg1, MorphCase _arg2, MorphNumber _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setCase(_arg2);
        res.setNumber(_arg3);
        return res;
    }

    public static MorphBaseInfo _new514(MorphCase _arg1, MorphLang _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setLanguage(_arg2);
        return res;
    }

    public static MorphBaseInfo _new529(MorphClass _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        return res;
    }

    public static MorphBaseInfo _new1982(MorphCase _arg1, MorphGender _arg2, MorphNumber _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        res.setNumber(_arg3);
        return res;
    }

    public static MorphBaseInfo _new2583(MorphClass _arg1, MorphGender _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res._setClass(_arg1);
        res.setGender(_arg2);
        res.setLanguage(_arg3);
        return res;
    }

    public static MorphBaseInfo _new2676(MorphCase _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        return res;
    }

    public static MorphBaseInfo _new2689(MorphCase _arg1, MorphGender _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        return res;
    }

    public static MorphBaseInfo _new2708(MorphGender _arg1, MorphCase _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setCase(_arg2);
        return res;
    }

    public static MorphBaseInfo _new2731(MorphGender _arg1) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        return res;
    }

    public static MorphBaseInfo _new2779(MorphCase _arg1, MorphGender _arg2, MorphClass _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setCase(_arg1);
        res.setGender(_arg2);
        res._setClass(_arg3);
        return res;
    }

    public static MorphBaseInfo _new2817(MorphNumber _arg1, MorphLang _arg2) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setNumber(_arg1);
        res.setLanguage(_arg2);
        return res;
    }

    public static MorphBaseInfo _new2822(MorphGender _arg1, MorphNumber _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setGender(_arg1);
        res.setNumber(_arg2);
        res.setLanguage(_arg3);
        return res;
    }

    public static MorphBaseInfo _new2824(MorphNumber _arg1, MorphGender _arg2, MorphLang _arg3) {
        MorphBaseInfo res = new MorphBaseInfo();
        res.setNumber(_arg1);
        res.setGender(_arg2);
        res.setLanguage(_arg3);
        return res;
    }
    public MorphBaseInfo() {
    }
}
