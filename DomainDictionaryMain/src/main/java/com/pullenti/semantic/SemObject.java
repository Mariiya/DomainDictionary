/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Семантический объект
 */
public class SemObject implements Comparable<SemObject> {

    public SemObject(SemGraph _graph) {
        graph = _graph;
    }

    /**
     * Ссылка на граф - владалец объекта.
     */
    public SemGraph graph;

    /**
     * Морфологическая информация (падеж отсутствует в принципе), 
     * часть речи Class тоже не задана - вместо этого поле Typ. 
     * NormalFull - полная нормализация, NormalCase - только падежная нормализация.
     */
    public com.pullenti.morph.MorphWordForm morph = new com.pullenti.morph.MorphWordForm();

    /**
     * Тип (определяется частью речи)
     */
    public SemObjectType typ = SemObjectType.UNDEFINED;

    /**
     * Количественная характеристика
     */
    public SemQuantity quantity;

    /**
     * Ссылка на концепт - это абстрактное понятие, используется вовне. 
     * Это и Referent, это и DerivateGroup. В принципе, приложения сами здесь будут расставлять 
     * свои объекты.
     */
    public Object concept;

    /**
     * Атрибуты, список SemAttribute. Формируются частично из наречий, частично из служебных частей речи.
     */
    public java.util.ArrayList<SemAttribute> attrs = new java.util.ArrayList<SemAttribute>();

    /**
     * Ну не знаю, потом нужно будет обобщить и куда-нибудь перенесём
     */
    public com.pullenti.ner.measure.MeasureKind measure = com.pullenti.ner.measure.MeasureKind.UNDEFINED;

    /**
     * Признак отрицания (потом перенесём в атрибуты)
     */
    public boolean not;

    /**
     * Токены MetaToken в исходном тексте
     */
    public java.util.ArrayList<com.pullenti.ner.MetaToken> tokens = new java.util.ArrayList<com.pullenti.ner.MetaToken>();

    public int getBeginChar() {
        return (tokens.size() > 0 ? tokens.get(0).getBeginChar() : 0);
    }


    public int getEndChar() {
        return (tokens.size() > 0 ? tokens.get(tokens.size() - 1).getEndChar() : 0);
    }


    /**
     * Исходящие связи SemLink (текущий объект выступает как Source)
     */
    public java.util.ArrayList<SemLink> linksFrom = new java.util.ArrayList<SemLink>();

    /**
     * Входящие связи SemLink (текущий объект выступает как Target)
     */
    public java.util.ArrayList<SemLink> linksTo = new java.util.ArrayList<SemLink>();

    /**
     * Используется произвольным образом
     */
    public Object tag;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (not) 
            res.append("НЕ ");
        for (SemAttribute a : attrs) {
            res.append(a.toString().toLowerCase()).append(" ");
        }
        if (quantity != null) 
            res.append(quantity).append(" ");
        else if (morph.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && typ == SemObjectType.NOUN) 
            res.append("* ");
        res.append((morph.normalCase != null ? morph.normalCase : "?"));
        return res.toString();
    }

    @Override
    public int compareTo(SemObject other) {
        if (tokens.size() == 0 || other.tokens.size() == 0) 
            return 0;
        if (tokens.get(0).getBeginChar() < other.tokens.get(0).getBeginChar()) 
            return -1;
        if (tokens.get(0).getBeginChar() > other.tokens.get(0).getBeginChar()) 
            return 1;
        if (tokens.get(tokens.size() - 1).getEndChar() < other.tokens.get(other.tokens.size() - 1).getEndChar()) 
            return -1;
        if (tokens.get(tokens.size() - 1).getEndChar() > other.tokens.get(other.tokens.size() - 1).getEndChar()) 
            return 1;
        return 0;
    }

    /**
     * Проверка значения
     * @param word 
     * @param _typ 
     * @return 
     */
    public boolean isValue(String word, SemObjectType _typ) {
        if (_typ != SemObjectType.UNDEFINED) {
            if (_typ != typ) 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(morph.normalFull, word) || com.pullenti.unisharp.Utils.stringsEq(morph.normalCase, word)) 
            return true;
        com.pullenti.semantic.utils.DerivateGroup gr = (com.pullenti.semantic.utils.DerivateGroup)com.pullenti.unisharp.Utils.cast(concept, com.pullenti.semantic.utils.DerivateGroup.class);
        if (gr != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(gr.words.get(0).spelling, word)) 
                return true;
        }
        return false;
    }

    /**
     * Найти объект, кторый связан с текущим исходящий связью (Source = this)
     * @param word 
     * @param _typ 
     * @param otyp 
     * @return 
     */
    public SemObject findFromObject(String word, SemLinkType _typ, SemObjectType otyp) {
        for (SemLink li : linksFrom) {
            if (_typ != SemLinkType.UNDEFINED && _typ != li.typ) 
                continue;
            if (li.getTarget().isValue(word, otyp)) 
                return li.getTarget();
        }
        return null;
    }

    /**
     * Найти атрибут указанного типа
     * @param _typ 
     * @return 
     */
    public SemAttribute findAttr(SemAttributeType _typ) {
        for (SemAttribute a : attrs) {
            if (a.typ == _typ) 
                return a;
        }
        return null;
    }

    public static SemObject _new3133(SemGraph _arg1, SemObjectType _arg2) {
        SemObject res = new SemObject(_arg1);
        res.typ = _arg2;
        return res;
    }
    public SemObject() {
    }
}
