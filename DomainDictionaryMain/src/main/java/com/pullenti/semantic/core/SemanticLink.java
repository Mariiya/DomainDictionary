/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.core;

/**
 * Семантическая связь двух элементов
 * Семантическая связь
 */
public class SemanticLink implements Comparable<SemanticLink> {

    /**
     * Основной элемент (м.б. глагольная группа VerbPhraseToken или 
     * именная группа NounPhraseToken)
     */
    public com.pullenti.ner.MetaToken master;

    /**
     * Пристыкованный элемент (м.б. именная или глагольная группа)
     */
    public com.pullenti.ner.MetaToken slave;

    /**
     * Вопрос, на который отвечает связь
     */
    public com.pullenti.semantic.utils.ControlModelQuestion question;

    /**
     * Семантическая роль
     */
    public SemanticRole role = SemanticRole.COMMON;

    /**
     * Страдательный залог или возвратный глагол (роли Агент и Пациент подлежит коррекции)
     */
    public boolean isPassive = false;

    /**
     * Сила связи (для сравнения и выбора лучшей)
     */
    public double rank = 0.0;

    /**
     * Эта связь смоделирована, так как не нашлась подходящая модель управления основного элемента
     */
    public boolean modelled;

    /**
     * Идиомная связь (то есть устойчивое словосочетание)
     */
    public boolean idiom;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (modelled) 
            res.append("?");
        if (idiom) 
            res.append("!");
        if (role != SemanticRole.COMMON) 
            res.append(role.toString()).append(": ");
        if (isPassive) 
            res.append("Passive ");
        if (rank > 0) 
            res.append(rank).append(" ");
        if (question != null) 
            res.append(question.spellingEx).append("? ");
        res.append("[").append((master == null ? "?" : master.toString())).append("] <- [").append((slave == null ? "?" : slave.toString())).append("]");
        return res.toString();
    }

    @Override
    public int compareTo(SemanticLink other) {
        if (rank > other.rank) 
            return -1;
        if (rank < other.rank) 
            return 1;
        return 0;
    }

    public static SemanticLink _new3075(boolean _arg1, com.pullenti.ner.MetaToken _arg2, com.pullenti.ner.MetaToken _arg3, double _arg4, com.pullenti.semantic.utils.ControlModelQuestion _arg5) {
        SemanticLink res = new SemanticLink();
        res.modelled = _arg1;
        res.master = _arg2;
        res.slave = _arg3;
        res.rank = _arg4;
        res.question = _arg5;
        return res;
    }

    public static SemanticLink _new3076(SemanticRole _arg1, com.pullenti.ner.MetaToken _arg2, com.pullenti.ner.MetaToken _arg3, double _arg4) {
        SemanticLink res = new SemanticLink();
        res.role = _arg1;
        res.master = _arg2;
        res.slave = _arg3;
        res.rank = _arg4;
        return res;
    }

    public static SemanticLink _new3077(double _arg1, com.pullenti.semantic.utils.ControlModelQuestion _arg2) {
        SemanticLink res = new SemanticLink();
        res.rank = _arg1;
        res.question = _arg2;
        return res;
    }

    public static SemanticLink _new3078(com.pullenti.semantic.utils.ControlModelQuestion _arg1, SemanticRole _arg2, boolean _arg3) {
        SemanticLink res = new SemanticLink();
        res.question = _arg1;
        res.role = _arg2;
        res.idiom = _arg3;
        return res;
    }

    public static SemanticLink _new3079(boolean _arg1, SemanticRole _arg2, double _arg3, com.pullenti.semantic.utils.ControlModelQuestion _arg4, boolean _arg5) {
        SemanticLink res = new SemanticLink();
        res.modelled = _arg1;
        res.role = _arg2;
        res.rank = _arg3;
        res.question = _arg4;
        res.isPassive = _arg5;
        return res;
    }

    public static SemanticLink _new3080(SemanticRole _arg1, double _arg2, com.pullenti.semantic.utils.ControlModelQuestion _arg3, boolean _arg4) {
        SemanticLink res = new SemanticLink();
        res.role = _arg1;
        res.rank = _arg2;
        res.question = _arg3;
        res.isPassive = _arg4;
        return res;
    }

    public static SemanticLink _new3081(SemanticRole _arg1, double _arg2, com.pullenti.semantic.utils.ControlModelQuestion _arg3) {
        SemanticLink res = new SemanticLink();
        res.role = _arg1;
        res.rank = _arg2;
        res.question = _arg3;
        return res;
    }

    public static SemanticLink _new3084(com.pullenti.semantic.utils.ControlModelQuestion _arg1, double _arg2, SemanticRole _arg3) {
        SemanticLink res = new SemanticLink();
        res.question = _arg1;
        res.rank = _arg2;
        res.role = _arg3;
        return res;
    }

    public static SemanticLink _new3085(boolean _arg1, SemanticRole _arg2, double _arg3, com.pullenti.semantic.utils.ControlModelQuestion _arg4) {
        SemanticLink res = new SemanticLink();
        res.modelled = _arg1;
        res.role = _arg2;
        res.rank = _arg3;
        res.question = _arg4;
        return res;
    }

    public static SemanticLink _new3086(SemanticRole _arg1, com.pullenti.semantic.utils.ControlModelQuestion _arg2, boolean _arg3) {
        SemanticLink res = new SemanticLink();
        res.role = _arg1;
        res.question = _arg2;
        res.idiom = _arg3;
        return res;
    }
    public SemanticLink() {
    }
}
