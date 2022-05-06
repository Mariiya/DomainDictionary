/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - представление союзов и других служебных слов. Они могут быть из нескольких токенов, например, "из-за того что". 
 * Получить можно с помощью ConjunctionHelper.TryParse(t)
 * Союзная группа
 */
public class ConjunctionToken extends com.pullenti.ner.MetaToken {

    public ConjunctionToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    /**
     * Нормализованное значение
     */
    public String normal;

    /**
     * Возможный тип союза
     */
    public ConjunctionType typ = ConjunctionType.UNDEFINED;

    /**
     * Это когда союз простой (запятая, и, ...), а не такой "а также", "также, как и"
     */
    public boolean isSimple;

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

    public static ConjunctionToken _new493(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ConjunctionType _arg3, boolean _arg4, String _arg5) {
        ConjunctionToken res = new ConjunctionToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isSimple = _arg4;
        res.normal = _arg5;
        return res;
    }

    public static ConjunctionToken _new494(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, ConjunctionType _arg4) {
        ConjunctionToken res = new ConjunctionToken(_arg1, _arg2);
        res.normal = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static ConjunctionToken _new495(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4, ConjunctionType _arg5) {
        ConjunctionToken res = new ConjunctionToken(_arg1, _arg2);
        res.normal = _arg3;
        res.isSimple = _arg4;
        res.typ = _arg5;
        return res;
    }
    public ConjunctionToken() {
        super();
    }
}
