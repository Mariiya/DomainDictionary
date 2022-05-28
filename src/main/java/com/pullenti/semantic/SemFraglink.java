/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Связь между фрагментами
 */
public class SemFraglink {

    /**
     * Тип связи
     */
    public SemFraglinkType typ = SemFraglinkType.UNDEFINED;

    /**
     * Фрагмент-источник (откуда связь)
     */
    public SemFragment source;

    /**
     * Фрагмент-приёмник (куда связь)
     */
    public SemFragment target;

    /**
     * Возможный вопрос, на который даёт ответ связь
     */
    public String question;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (typ != SemFraglinkType.UNDEFINED) 
            tmp.append(typ.toString()).append(" ");
        if (question != null) 
            tmp.append(question).append("? ");
        if (source != null) 
            tmp.append(source.toString()).append(" ");
        if (target != null) 
            tmp.append("-> ").append(target.toString());
        return tmp.toString();
    }

    public static SemFraglink _new3172(SemFraglinkType _arg1, SemFragment _arg2, SemFragment _arg3, String _arg4) {
        SemFraglink res = new SemFraglink();
        res.typ = _arg1;
        res.source = _arg2;
        res.target = _arg3;
        res.question = _arg4;
        return res;
    }
    public SemFraglink() {
    }
}
