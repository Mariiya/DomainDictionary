/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Данные, полученные в ходе обработки, причём с поддержкой механизма онтологий
public class AnalyzerDataWithOntology extends AnalyzerData {

    public IntOntologyCollection localOntology = new IntOntologyCollection();

    public AnalyzerDataWithOntology() {
        super();
    }

    @Override
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        com.pullenti.ner.Referent res;
        java.util.ArrayList<com.pullenti.ner.Referent> li = localOntology.tryAttachByReferent(referent, null, true);
        if (li != null) {
            for (int i = li.size() - 1; i >= 0; i--) {
                if (li.get(i).canBeGeneralFor(referent) || referent.canBeGeneralFor(li.get(i))) 
                    li.remove(i);
            }
        }
        if (li != null && li.size() > 0) {
            res = li.get(0);
            if (res != referent) 
                res.mergeSlots(referent, true);
            if (li.size() > 1 && kit != null) {
                for (int i = 1; i < li.size(); i++) {
                    li.get(0).mergeSlots(li.get(i), true);
                    for (com.pullenti.ner.TextAnnotation ta : li.get(i).getOccurrence()) {
                        li.get(0).addOccurence(ta);
                    }
                    kit.replaceReferent(li.get(i), li.get(0));
                    localOntology.remove(li.get(i));
                }
            }
            if (res.m_ExtReferents != null) 
                res = super.registerReferent(res);
            localOntology.addReferent(res);
            return res;
        }
        res = super.registerReferent(referent);
        if (res == null) 
            return null;
        localOntology.addReferent(res);
        return res;
    }

    @Override
    public void removeReferent(com.pullenti.ner.Referent r) {
        localOntology.remove(r);
        super.removeReferent(r);
    }
}
