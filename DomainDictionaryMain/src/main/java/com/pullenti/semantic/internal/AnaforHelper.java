/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class AnaforHelper {

    public static boolean processAnafors(java.util.ArrayList<com.pullenti.semantic.SemObject> objs) {
        for (int i = objs.size() - 1; i >= 0; i--) {
            com.pullenti.semantic.SemObject it = objs.get(i);
            if (it.typ == com.pullenti.semantic.SemObjectType.PERSONALPRONOUN) {
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(it.morph.normalFull, "КОТОРЫЙ") && it.linksFrom.size() == 0) {
            }
            else 
                continue;
            java.util.ArrayList<AnaforLink> vars = new java.util.ArrayList<AnaforLink>();
            for (int j = i - 1; j >= 0; j--) {
                AnaforLink a = AnaforLink.tryCreate(it, objs.get(j));
                if (a == null) 
                    continue;
                vars.add(a);
                a.correct();
            }
            if (vars.size() < 1) 
                continue;
            AnaforLink.sort(vars);
            if (vars.get(0).coef <= 0.1) 
                continue;
            if (vars.get(0).targetList != null) {
                for (com.pullenti.semantic.SemObject tgt : vars.get(0).targetList) {
                    it.graph.addLink(com.pullenti.semantic.SemLinkType.ANAFOR, it, tgt, null, false, null);
                }
            }
            else {
                com.pullenti.semantic.SemLink li = it.graph.addLink(com.pullenti.semantic.SemLinkType.ANAFOR, it, vars.get(0).target, null, false, null);
                if (vars.size() > 1 && vars.get(0).coef <= (vars.get(1).coef * (2.0)) && vars.get(1).targetList == null) {
                    com.pullenti.semantic.SemLink li1 = it.graph.addLink(com.pullenti.semantic.SemLinkType.ANAFOR, it, vars.get(1).target, null, false, null);
                    li1.altLink = li;
                    li.altLink = li1;
                }
            }
        }
        return false;
    }

    public static class AnaforLink implements Comparable<AnaforLink> {
    
        public double coef;
    
        public com.pullenti.semantic.SemObject target;
    
        public java.util.ArrayList<com.pullenti.semantic.SemObject> targetList = null;
    
        @Override
        public String toString() {
            if (targetList == null) 
                return (((Double)coef).toString() + ": " + target);
            StringBuilder tmp = new StringBuilder();
            tmp.append(coef).append(": ");
            for (com.pullenti.semantic.SemObject v : targetList) {
                tmp.append(v).append("; ");
            }
            return tmp.toString();
        }
    
        public static AnaforLink tryCreate(com.pullenti.semantic.SemObject src, com.pullenti.semantic.SemObject tgt) {
            if (tgt.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                return null;
            if (((short)((src.morph.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.PLURAL.value())) {
                if (((short)((tgt.morph.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return _new3108(1.0, tgt);
                AnaforLink res = _new3108(0.5, tgt);
                res.targetList = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
                for (com.pullenti.semantic.SemLink li : tgt.linksTo) {
                    com.pullenti.semantic.SemObject frm = li.getSource();
                    for (int i = 0; i < frm.linksFrom.size(); i++) {
                        res.targetList.clear();
                        com.pullenti.semantic.SemLink li0 = frm.linksFrom.get(i);
                        if (li0.getTarget().typ != com.pullenti.semantic.SemObjectType.NOUN) 
                            continue;
                        res.targetList.add(li0.getTarget());
                        for (int j = i + 1; j < frm.linksFrom.size(); j++) {
                            com.pullenti.semantic.SemLink li1 = frm.linksFrom.get(j);
                            if (li1.typ == li0.typ && com.pullenti.unisharp.Utils.stringsEq(li1.preposition, li0.preposition) && li1.getTarget().typ == li0.getTarget().typ) 
                                res.targetList.add(li1.getTarget());
                        }
                        if (res.targetList.size() > 1) 
                            return res;
                    }
                }
                return null;
            }
            if (tgt.morph.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && ((short)((tgt.morph.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                return null;
            if (tgt.morph.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (((short)((tgt.morph.getGender().value()) & (src.morph.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    return null;
                return _new3108(1.0, tgt);
            }
            return _new3108(0.1, tgt);
        }
    
        public static void sort(java.util.ArrayList<AnaforLink> li) {
            for (int i = 0; i < li.size(); i++) {
                boolean ch = false;
                for (int j = 0; j < (li.size() - 1); j++) {
                    if (li.get(j).compareTo(li.get(j + 1)) > 0) {
                        AnaforLink a = li.get(j);
                        com.pullenti.unisharp.Utils.putArrayValue(li, j, li.get(j + 1));
                        com.pullenti.unisharp.Utils.putArrayValue(li, j + 1, a);
                        ch = true;
                    }
                }
                if (!ch) 
                    break;
            }
        }
    
        public void correct() {
            for (com.pullenti.semantic.SemLink li : target.linksTo) {
                if (li.typ == com.pullenti.semantic.SemLinkType.NAMING) 
                    coef = 0.0;
                else if (li.typ == com.pullenti.semantic.SemLinkType.AGENT) 
                    coef *= (2.0);
                else if (li.typ == com.pullenti.semantic.SemLinkType.PACIENT) {
                    if (li.altLink == null) 
                        coef *= (2.0);
                }
                else if (!com.pullenti.unisharp.Utils.isNullOrEmpty(li.preposition)) 
                    coef /= (2.0);
            }
        }
    
        @Override
        public int compareTo(AnaforLink other) {
            if (coef > other.coef) 
                return -1;
            if (coef < other.coef) 
                return 1;
            return 0;
        }
    
        public static AnaforLink _new3108(double _arg1, com.pullenti.semantic.SemObject _arg2) {
            AnaforLink res = new AnaforLink();
            res.coef = _arg1;
            res.target = _arg2;
            return res;
        }
        public AnaforLink() {
        }
    }

    public AnaforHelper() {
    }
    public static AnaforHelper _globalInstance;
    
    static {
        try { _globalInstance = new AnaforHelper(); } 
        catch(Exception e) { }
    }
}
