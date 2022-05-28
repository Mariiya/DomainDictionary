/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Элемент модели управления
 */
public class ControlModelItem {

    /**
     * Тип элемента
     */
    public ControlModelItemType typ = ControlModelItemType.WORD;

    /**
     * Возможное слово (если Typ == ControlModelItemType.Word)
     */
    public String word;

    /**
     * Связи "вопрос - роль".
     */
    public java.util.HashMap<ControlModelQuestion, com.pullenti.semantic.core.SemanticRole> links = new java.util.HashMap<ControlModelQuestion, com.pullenti.semantic.core.SemanticRole>();

    /**
     * Именительный падеж м.б. агентом и пациентом (для возвратных глаголов). 
     * Например, "ЗАЩИЩАТЬСЯ"
     */
    public boolean nominativeCanBeAgentAndPacient;

    public boolean ignorable;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (ignorable) 
            res.append("IGNORE ");
        if (typ != ControlModelItemType.WORD) 
            res.append(typ.toString()).append(": ");
        else 
            res.append(((word != null ? word : "?"))).append(": ");
        for (java.util.Map.Entry<ControlModelQuestion, com.pullenti.semantic.core.SemanticRole> li : links.entrySet()) {
            if (li.getValue() == com.pullenti.semantic.core.SemanticRole.AGENT) 
                res.append("аг:");
            else if (li.getValue() == com.pullenti.semantic.core.SemanticRole.PACIENT) 
                res.append("пац:");
            else if (li.getValue() == com.pullenti.semantic.core.SemanticRole.STRONG) 
                res.append("сильн:");
            res.append(li.getKey().spelling).append("? ");
        }
        return res.toString();
    }
    public ControlModelItem() {
    }
}
