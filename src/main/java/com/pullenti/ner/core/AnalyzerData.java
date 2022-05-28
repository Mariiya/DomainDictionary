/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Данные, полученные в ходе обработки одним анализатором. Каждый анализатор сохраняет в своём класса свои данные, 
 * получаемые в ходе анализа. В конце процессор объединяет их все. Получить экземпляр, связанный с анализатором, 
 * можно методом AnalyzerKit.GetAnalyzerDataByAnalyzerName.
 * Данные анализа
 */
public class AnalyzerData {

    /**
     * Ссылка на аналитический контейнер
     */
    public AnalysisKit kit;

    /**
     * Это для контроля "переполнения стека"
     */
    public int level = 0;

    public java.util.Collection<com.pullenti.ner.Referent> getReferents() {
        return m_Referents;
    }

    public java.util.Collection<com.pullenti.ner.Referent> setReferents(java.util.Collection<com.pullenti.ner.Referent> value) {
        m_Referents.clear();
        if (value != null) 
            com.pullenti.unisharp.Utils.addToArrayList(m_Referents, value);
        return value;
    }


    protected java.util.ArrayList<com.pullenti.ner.Referent> m_Referents = new java.util.ArrayList<com.pullenti.ner.Referent>();

    private int m_RegRefLevel = 0;

    /**
     * Зарегистрировать новую сущность или привязать к существующей сущности. Сущности, получаемые в ходе анализа, 
     * должны сохраняться через эту функцию. Именно здесь решается задача кореференции, то есть объединения 
     * сущностей, соответствующих одному и тому же объекту текста.
     * @param referent сохраняемая сущность
     * @return этот же экземпляр referent или другой, если удалось объединиться с ранее выделенной сущностью
     */
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        if (referent == null) 
            return null;
        if (referent.m_ExtReferents != null) {
            if (m_RegRefLevel > 2) {
            }
            else {
                for (com.pullenti.ner.ReferentToken rt : referent.m_ExtReferents) {
                    com.pullenti.ner.Referent oldRef = rt.referent;
                    m_RegRefLevel++;
                    rt.saveToLocalOntology();
                    m_RegRefLevel--;
                    if (oldRef == rt.referent || rt.referent == null) 
                        continue;
                    for (com.pullenti.ner.Slot s : referent.getSlots()) {
                        if (s.getValue() == oldRef) 
                            referent.uploadSlot(s, rt.referent);
                    }
                    if (referent.m_ExtReferents != null) {
                        for (com.pullenti.ner.ReferentToken rtt : referent.m_ExtReferents) {
                            for (com.pullenti.ner.Slot s : rtt.referent.getSlots()) {
                                if (s.getValue() == oldRef) 
                                    referent.uploadSlot(s, rt.referent);
                            }
                        }
                    }
                }
                referent.m_ExtReferents = null;
            }
        }
        java.util.ArrayList<com.pullenti.ner.Referent> eq = null;
        if (m_Referents.contains(referent)) 
            return referent;
        for (int i = m_Referents.size() - 1; i >= 0 && ((m_Referents.size() - i) < 1000); i--) {
            com.pullenti.ner.Referent p = m_Referents.get(i);
            if (p.canBeEquals(referent, ReferentsEqualType.WITHINONETEXT)) {
                if (!p.canBeGeneralFor(referent) && !referent.canBeGeneralFor(p)) {
                    if (eq == null) 
                        eq = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    eq.add(p);
                }
            }
        }
        if (eq != null) {
            if (eq.size() == 1) {
                eq.get(0).mergeSlots(referent, true);
                return eq.get(0);
            }
            if (eq.size() > 1) {
                for (com.pullenti.ner.Referent e : eq) {
                    if (e.getSlots().size() != referent.getSlots().size()) 
                        continue;
                    boolean ok = true;
                    for (com.pullenti.ner.Slot s : referent.getSlots()) {
                        if (e.findSlot(s.getTypeName(), s.getValue(), true) == null) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        for (com.pullenti.ner.Slot s : e.getSlots()) {
                            if (referent.findSlot(s.getTypeName(), s.getValue(), true) == null) {
                                ok = false;
                                break;
                            }
                        }
                    }
                    if (ok) 
                        return e;
                }
            }
        }
        m_Referents.add(referent);
        return referent;
    }

    /**
     * Удалить сущность из списка
     * @param r удаляемая сущность
     */
    public void removeReferent(com.pullenti.ner.Referent r) {
        if (m_Referents.contains(r)) 
            m_Referents.remove(r);
    }

    public int overflowLevel = 0;
    public AnalyzerData() {
    }
}
