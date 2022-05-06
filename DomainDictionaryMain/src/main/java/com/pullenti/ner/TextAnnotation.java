/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Аннотация слитного фрагмента текста (фрагмент вхождения сущности в текст)
 * 
 * Аннотация
 */
public class TextAnnotation {

    public TextAnnotation(Token begin, Token end, Referent r) {
        if (begin != null) {
            sofa = begin.kit.getSofa();
            beginChar = begin.getBeginChar();
        }
        if (end != null) 
            endChar = end.getEndChar();
        setOccurenceOf(r);
    }

    /**
     * Ссылка на текст
     */
    public SourceOfAnalysis sofa;

    /**
     * Начальная позиция фрагмента
     */
    public int beginChar;

    /**
     * Конечная позиция фрагмента
     */
    public int endChar;

    public Referent getOccurenceOf() {
        return m_OccurenceOf;
    }

    public Referent setOccurenceOf(Referent value) {
        m_OccurenceOf = value;
        return value;
    }


    private Referent m_OccurenceOf;

    /**
     * Указание на то, что текущая сущность была выделена на основе правил 
     * именно на данном фрагменте текста.
     */
    public boolean essentialForOccurence;

    @Override
    public String toString() {
        if (sofa == null) 
            return (((Integer)beginChar).toString() + ":" + endChar);
        return this.getText();
    }

    /**
     * Извлечь фрагмент исходного текста, соответствующий аннотации
     * @return фрагмент текста
     */
    public String getText() {
        if (sofa == null || sofa.getText() == null) 
            return null;
        int len = (endChar + 1) - beginChar;
        if (len <= 0) 
            return null;
        return sofa.getText().substring(beginChar, beginChar + len);
    }

    public com.pullenti.ner.core.internal.TextsCompareType compareWith(TextAnnotation loc) {
        if (loc.sofa != sofa) 
            return com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE;
        return this.compare(loc.beginChar, loc.endChar);
    }

    public com.pullenti.ner.core.internal.TextsCompareType compare(int pos, int pos1) {
        if (endChar < pos) 
            return com.pullenti.ner.core.internal.TextsCompareType.EARLY;
        if (pos1 < beginChar) 
            return com.pullenti.ner.core.internal.TextsCompareType.LATER;
        if (beginChar == pos && endChar == pos1) 
            return com.pullenti.ner.core.internal.TextsCompareType.EQUIVALENT;
        if (beginChar >= pos && endChar <= pos1) 
            return com.pullenti.ner.core.internal.TextsCompareType.IN;
        if (pos >= beginChar && pos1 <= endChar) 
            return com.pullenti.ner.core.internal.TextsCompareType.CONTAINS;
        return com.pullenti.ner.core.internal.TextsCompareType.INTERSECT;
    }

    public void merge(TextAnnotation loc) {
        if (loc.sofa != sofa) 
            return;
        if (loc.beginChar < beginChar) 
            beginChar = loc.beginChar;
        if (endChar < loc.endChar) 
            endChar = loc.endChar;
        if (loc.essentialForOccurence) 
            essentialForOccurence = true;
    }

    public Object tag;

    public static TextAnnotation _new489(SourceOfAnalysis _arg1, int _arg2, int _arg3) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.beginChar = _arg2;
        res.endChar = _arg3;
        return res;
    }

    public static TextAnnotation _new765(SourceOfAnalysis _arg1, int _arg2, int _arg3, Referent _arg4) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.beginChar = _arg2;
        res.endChar = _arg3;
        res.setOccurenceOf(_arg4);
        return res;
    }

    public static TextAnnotation _new1748(int _arg1, int _arg2, Referent _arg3, SourceOfAnalysis _arg4) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.beginChar = _arg1;
        res.endChar = _arg2;
        res.setOccurenceOf(_arg3);
        res.sofa = _arg4;
        return res;
    }

    public static TextAnnotation _new3061(int _arg1, int _arg2, SourceOfAnalysis _arg3) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.beginChar = _arg1;
        res.endChar = _arg2;
        res.sofa = _arg3;
        return res;
    }

    public static TextAnnotation _new3063(SourceOfAnalysis _arg1, Referent _arg2) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.setOccurenceOf(_arg2);
        return res;
    }
    public TextAnnotation() {
        this(null, null, null);
    }
}
