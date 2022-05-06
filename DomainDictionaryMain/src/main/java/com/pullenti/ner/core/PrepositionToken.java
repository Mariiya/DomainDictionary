/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - предлог (они могут быть из нескольких токенов, например, 
 * "несмотря на", "в соответствии с"). 
 * Создаётся методом PrepositionHelper.TryParse(t).
 * Предложная группа
 */
public class PrepositionToken extends com.pullenti.ner.MetaToken {

    public PrepositionToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    /**
     * Нормализованное значение (ПОДО -> ПОД,  ОБО, ОБ -> О ...)
     */
    public String normal;

    /**
     * Падежи для слов, используемых с предлогом
     */
    public com.pullenti.morph.MorphCase nextCase;

    @Override
    public String toString() {
        return normal;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        String res = normal;
        if (keepChars) {
            if (chars.isAllLower()) 
                res = res.toLowerCase();
            else if (chars.isAllUpper()) {
            }
            else if (chars.isCapitalUpper()) 
                res = MiscHelper.convertFirstCharUpperAndOtherLower(res);
        }
        return res;
    }

    public static PrepositionToken _new551(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.morph.MorphCase _arg4) {
        PrepositionToken res = new PrepositionToken(_arg1, _arg2);
        res.normal = _arg3;
        res.nextCase = _arg4;
        return res;
    }
    public PrepositionToken() {
        super();
    }
}
