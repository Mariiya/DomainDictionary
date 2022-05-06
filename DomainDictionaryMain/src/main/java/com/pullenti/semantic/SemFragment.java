/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Фрагмент блока (предложение)
 * 
 */
public class SemFragment implements ISemContainer {

    public SemFragment(SemBlock blk) {
        m_Higher = blk;
    }

    @Override
    public SemGraph getGraph() {
        return m_Graph;
    }


    private SemGraph m_Graph = new SemGraph();

    @Override
    public ISemContainer getHigher() {
        return m_Higher;
    }


    public SemBlock m_Higher;

    public SemBlock getBlock() {
        return m_Higher;
    }


    /**
     * Тип фрагмента
     */
    public SemFragmentType typ = SemFragmentType.UNDEFINED;

    /**
     * Корневые объекты объединены как ИЛИ (иначе И)
     */
    public boolean isOr;

    public java.util.ArrayList<SemObject> getRootObjects() {
        java.util.ArrayList<SemObject> res = new java.util.ArrayList<SemObject>();
        for (SemObject o : m_Graph.objects) {
            if (o.linksTo.size() == 0) 
                res.add(o);
        }
        return res;
    }


    public boolean canBeErrorStructure() {
        int cou = 0;
        int vcou = 0;
        for (SemObject o : m_Graph.objects) {
            if (o.linksTo.size() == 0) {
                if (o.typ == SemObjectType.VERB) 
                    vcou++;
                cou++;
            }
        }
        if (cou <= 1) 
            return false;
        return vcou < cou;
    }


    public String getSpelling() {
        return com.pullenti.ner.core.MiscHelper.getTextValue(beginToken, endToken, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
    }


    /**
     * Начальный токен
     */
    public com.pullenti.ner.Token beginToken;

    /**
     * Конечный токен
     */
    public com.pullenti.ner.Token endToken;

    @Override
    public int getBeginChar() {
        return (beginToken == null ? 0 : beginToken.getBeginChar());
    }


    @Override
    public int getEndChar() {
        return (endToken == null ? 0 : endToken.getEndChar());
    }


    /**
     * Используйте произвольным образом
     */
    public Object tag;

    @Override
    public String toString() {
        if (typ != SemFragmentType.UNDEFINED) 
            return (typ.toString() + ": " + ((String)com.pullenti.unisharp.Utils.notnull(this.getSpelling(), "?")));
        else 
            return (String)com.pullenti.unisharp.Utils.notnull(getSpelling(), "?");
    }
    public SemFragment() {
    }
}
