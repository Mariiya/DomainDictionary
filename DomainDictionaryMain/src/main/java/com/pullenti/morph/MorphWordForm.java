/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Словоформа (вариант морфанализа лексемы)
 * 
 * словоформа
 */
public class MorphWordForm extends MorphBaseInfo {

    /**
     * Полная нормальная форма: 
     * - для существ. и местоимений - именит. падеж единств. число; 
     * - для прилаг.  - именит. падеж единств. число мужской род; 
     * - для глаголов - инфинитив;
     */
    public String normalFull;

    /**
     * Именительная нормальная форма (падежная нормализация - 
     * только приведение к именительному падежу, остальные хар-ки без изменений), 
     * для глаголов - инфинитив.
     */
    public String normalCase;

    /**
     * Дополнительная морф.информация
     */
    public MorphMiscInfo misc;

    public boolean isInDictionary() {
        return undefCoef == ((short)0);
    }


    /**
     * Коэффициент достоверности для неизвестных словоформ (чем больше, тем вероятнее)
     */
    public short undefCoef;

    // Используется произвольным образом
    public Object tag;

    public void copyFromWordForm(MorphWordForm src) {
        super.copyFrom(src);
        undefCoef = src.undefCoef;
        normalCase = src.normalCase;
        normalFull = src.normalFull;
        misc = src.misc;
    }

    public MorphWordForm() {
        super();
    }

    public MorphWordForm(com.pullenti.morph.internal.MorphRuleVariant v, String word, MorphMiscInfo mi) {
        super();
        if (v == null) 
            return;
        this.copyFrom(v);
        misc = mi;
        if (v.normalTail != null && word != null) {
            String wordBegin = word;
            if (LanguageHelper.endsWith(word, v.tail)) 
                wordBegin = word.substring(0, 0 + word.length() - v.tail.length());
            if (v.normalTail.length() > 0) 
                normalCase = wordBegin + v.normalTail;
            else 
                normalCase = wordBegin;
        }
        if (v.fullNormalTail != null && word != null) {
            String wordBegin = word;
            if (LanguageHelper.endsWith(word, v.tail)) 
                wordBegin = word.substring(0, 0 + word.length() - v.tail.length());
            if (v.fullNormalTail.length() > 0) 
                normalFull = wordBegin + v.fullNormalTail;
            else 
                normalFull = wordBegin;
        }
    }

    @Override
    public String toString() {
        return this.toStringEx(false);
    }

    public String toStringEx(boolean ignoreNormals) {
        StringBuilder res = new StringBuilder();
        if (!ignoreNormals) {
            res.append((normalCase != null ? normalCase : ""));
            if (normalFull != null && com.pullenti.unisharp.Utils.stringsNe(normalFull, normalCase)) 
                res.append("\\").append(normalFull);
            if (res.length() > 0) 
                res.append(' ');
        }
        res.append(super.toString());
        String s = (misc == null ? null : misc.toString());
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
            res.append(" ").append(s);
        if (undefCoef > ((short)0)) 
            res.append(" (? ").append(undefCoef).append(")");
        return res.toString();
    }

    @Override
    public boolean containsAttr(String attrValue, MorphClass cla) {
        if (misc != null && misc.getAttrs() != null) 
            return misc.getAttrs().contains(attrValue);
        return false;
    }

    public boolean hasMorphEquals(java.util.ArrayList<MorphWordForm> list) {
        for (MorphWordForm mr : list) {
            if ((_getClass().equals(mr._getClass()) && getNumber() == mr.getNumber() && getGender() == mr.getGender()) && com.pullenti.unisharp.Utils.stringsEq(normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(normalFull, mr.normalFull)) {
                mr.setCase(MorphCase.ooBitor(mr.getCase(), this.getCase()));
                MorphPerson p = misc.getPerson();
                if (p != MorphPerson.UNDEFINED && p != mr.misc.getPerson()) {
                    MorphMiscInfo mi = new MorphMiscInfo();
                    mi.copyFrom(mr.misc);
                    mi.setPerson(MorphPerson.of((short)((mr.misc.getPerson().value()) | (misc.getPerson().value()))));
                    mr.misc = mi;
                }
                return true;
            }
        }
        for (MorphWordForm mr : list) {
            if ((_getClass().equals(mr._getClass()) && getNumber() == mr.getNumber() && getCase().equals(mr.getCase())) && com.pullenti.unisharp.Utils.stringsEq(normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(normalFull, mr.normalFull)) {
                mr.setGender(MorphGender.of((short)((mr.getGender().value()) | (this.getGender().value()))));
                return true;
            }
        }
        for (MorphWordForm mr : list) {
            if ((_getClass().equals(mr._getClass()) && getGender() == mr.getGender() && getCase().equals(mr.getCase())) && com.pullenti.unisharp.Utils.stringsEq(normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(normalFull, mr.normalFull)) {
                mr.setNumber(MorphNumber.of((short)((mr.getNumber().value()) | (this.getNumber().value()))));
                return true;
            }
        }
        return false;
    }

    public static MorphWordForm _new5(String _arg1, MorphClass _arg2, short _arg3) {
        MorphWordForm res = new MorphWordForm();
        res.normalCase = _arg1;
        res._setClass(_arg2);
        res.undefCoef = _arg3;
        return res;
    }

    public static MorphWordForm _new629(MorphCase _arg1, MorphNumber _arg2, MorphGender _arg3) {
        MorphWordForm res = new MorphWordForm();
        res.setCase(_arg1);
        res.setNumber(_arg2);
        res.setGender(_arg3);
        return res;
    }

    public static MorphWordForm _new630(MorphClass _arg1, MorphMiscInfo _arg2) {
        MorphWordForm res = new MorphWordForm();
        res._setClass(_arg1);
        res.misc = _arg2;
        return res;
    }

    public static MorphWordForm _new3134(MorphClass _arg1, MorphNumber _arg2, MorphGender _arg3, MorphCase _arg4) {
        MorphWordForm res = new MorphWordForm();
        res._setClass(_arg1);
        res.setNumber(_arg2);
        res.setGender(_arg3);
        res.setCase(_arg4);
        return res;
    }
}
