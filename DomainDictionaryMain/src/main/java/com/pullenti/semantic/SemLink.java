/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Семантическая связь между объектами
 */
public class SemLink {

    public SemLink(SemGraph gr, SemObject src, SemObject tgt) {
        graph = gr;
        m_Source = src;
        m_Target = tgt;
        src.linksFrom.add(this);
        tgt.linksTo.add(this);
    }

    /**
     * Граф, владеющий связью (кстати, сами объекты у связи могут принадлежать разным графам).
     */
    public SemGraph graph;

    /**
     * Тип связи
     */
    public SemLinkType typ = SemLinkType.UNDEFINED;

    public SemObject getSource() {
        return m_Source;
    }


    private SemObject m_Source;

    public SemObject getTarget() {
        return m_Target;
    }


    public SemObject m_Target;

    /**
     * Альтернативная ссылка (парная, а та в свою очередь ссылается на эту). 
     * Используется для неоднозначных связях.
     */
    public SemLink altLink;

    /**
     * Вопрос, соответствующий связи
     */
    public String question;

    /**
     * Предлог, если есть
     */
    public String preposition;

    /**
     * Для нескольких однотипных связей из одного Source или Target обозначает логическое "или". 
     * Если false, то логическое "и".
     */
    public boolean isOr;

    /**
     * Используется произвольным образом
     */
    public Object tag;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (altLink != null) 
            tmp.append("??? ");
        if (isOr) 
            tmp.append("OR ");
        if (typ != SemLinkType.UNDEFINED) 
            tmp.append(typ.toString());
        if (question != null) 
            tmp.append(" ").append(question).append("?");
        if (getSource() != null) 
            tmp.append(" ").append(this.getSource().toString());
        if (getTarget() != null) 
            tmp.append(" -> ").append(this.getTarget().toString());
        return tmp.toString();
    }

    public static SemLink _new3173(SemGraph _arg1, SemObject _arg2, SemObject _arg3, SemLinkType _arg4, String _arg5, boolean _arg6, String _arg7) {
        SemLink res = new SemLink(_arg1, _arg2, _arg3);
        res.typ = _arg4;
        res.question = _arg5;
        res.isOr = _arg6;
        res.preposition = _arg7;
        return res;
    }
    public SemLink() {
    }
}
