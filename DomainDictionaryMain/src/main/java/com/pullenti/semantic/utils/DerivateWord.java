/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Слово дериватной группы DerivateWord
 * 
 */
public class DerivateWord {

    /**
     * Само слово в нормальной форме
     */
    public String spelling;

    /**
     * Часть речи
     */
    public com.pullenti.morph.MorphClass _class;

    /**
     * Совершенный\несовершенный (для глаголов и причастий)
     */
    public com.pullenti.morph.MorphAspect aspect = com.pullenti.morph.MorphAspect.UNDEFINED;

    /**
     * Действительный\страдательный (для глаголов и причастий)
     */
    public com.pullenti.morph.MorphVoice voice = com.pullenti.morph.MorphVoice.UNDEFINED;

    /**
     * Время (для глаголов и причастий)
     */
    public com.pullenti.morph.MorphTense tense = com.pullenti.morph.MorphTense.UNDEFINED;

    /**
     * Возвратность (для глаголов и причастий)
     */
    public boolean reflexive;

    /**
     * Язык
     */
    public com.pullenti.morph.MorphLang lang;

    /**
     * Дополнительные характеристики
     */
    public ExplanWordAttr attrs = new ExplanWordAttr();

    /**
     * Возможные частые продолжения слова (идиомы)
     */
    public java.util.ArrayList<String> nextWords = null;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(spelling);
        if (_class != null && !_class.isUndefined()) 
            tmp.append(", ").append(_class.toString());
        if (aspect != com.pullenti.morph.MorphAspect.UNDEFINED) 
            tmp.append(", ").append((aspect == com.pullenti.morph.MorphAspect.PERFECTIVE ? "соверш." : "несоверш."));
        if (voice != com.pullenti.morph.MorphVoice.UNDEFINED) 
            tmp.append(", ").append((voice == com.pullenti.morph.MorphVoice.ACTIVE ? "действ." : (voice == com.pullenti.morph.MorphVoice.PASSIVE ? "страдат." : "средн.")));
        if (tense != com.pullenti.morph.MorphTense.UNDEFINED) 
            tmp.append(", ").append((tense == com.pullenti.morph.MorphTense.PAST ? "прош." : (tense == com.pullenti.morph.MorphTense.PRESENT ? "настоящ." : "будущ.")));
        if (reflexive) 
            tmp.append(", возвр.");
        if (attrs.value != ((short)0)) 
            tmp.append(", ").append(attrs.toString());
        return tmp.toString();
    }

    public static DerivateWord _new3169(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.morph.MorphClass _arg3, com.pullenti.morph.MorphAspect _arg4, boolean _arg5, com.pullenti.morph.MorphTense _arg6, com.pullenti.morph.MorphVoice _arg7, ExplanWordAttr _arg8) {
        DerivateWord res = new DerivateWord();
        res.spelling = _arg1;
        res.lang = _arg2;
        res._class = _arg3;
        res.aspect = _arg4;
        res.reflexive = _arg5;
        res.tense = _arg6;
        res.voice = _arg7;
        res.attrs = _arg8;
        return res;
    }
    public DerivateWord() {
    }
}
