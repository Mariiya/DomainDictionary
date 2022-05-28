/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class GeneralRelationHelper {

    public static void refreshGenerals(com.pullenti.ner.Processor proc, com.pullenti.ner.core.AnalysisKit kit) {
        java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> all = new java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        java.util.ArrayList<Node> allRefs = new java.util.ArrayList<Node>();
        for (com.pullenti.ner.Analyzer a : proc.getAnalyzers()) {
            com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(a);
            if (ad == null) 
                continue;
            for (com.pullenti.ner.Referent r : ad.getReferents()) {
                Node nod = Node._new410(r, ad);
                allRefs.add(nod);
                r.tag = nod;
                java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> si;
                com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapsi413 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
                boolean inoutres414 = com.pullenti.unisharp.Utils.tryGetValue(all, a.getName(), wrapsi413);
                si = wrapsi413.value;
                if (!inoutres414) 
                    all.put(a.getName(), (si = new java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>()));
                java.util.ArrayList<String> strs = r.getCompareStrings();
                if (strs == null || strs.size() == 0) 
                    continue;
                for (String s : strs) {
                    if (s == null) 
                        continue;
                    java.util.ArrayList<com.pullenti.ner.Referent> li;
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli411 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
                    boolean inoutres412 = com.pullenti.unisharp.Utils.tryGetValue(si, s, wrapli411);
                    li = wrapli411.value;
                    if (!inoutres412) 
                        si.put(s, (li = new java.util.ArrayList<com.pullenti.ner.Referent>()));
                    li.add(r);
                }
            }
        }
        for (Node r : allRefs) {
            for (com.pullenti.ner.Slot s : r.ref.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    com.pullenti.ner.Referent to = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                    Node tn = (Node)com.pullenti.unisharp.Utils.cast(to.tag, Node.class);
                    if (tn == null) 
                        continue;
                    if (tn.refsFrom == null) 
                        tn.refsFrom = new java.util.ArrayList<Node>();
                    tn.refsFrom.add(r);
                    if (r.refsTo == null) 
                        r.refsTo = new java.util.ArrayList<Node>();
                    r.refsTo.add(tn);
                }
            }
        }
        for (java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> ty : all.values()) {
            for (java.util.ArrayList<com.pullenti.ner.Referent> li : ty.values()) {
                if (li.size() < 2) 
                    continue;
                if (li.size() > 3000) 
                    continue;
                for (int i = 0; i < li.size(); i++) {
                    for (int j = i + 1; j < li.size(); j++) {
                        Node n1 = null;
                        Node n2 = null;
                        if (li.get(i).canBeGeneralFor(li.get(j)) && !li.get(j).canBeGeneralFor(li.get(i))) {
                            n1 = (Node)com.pullenti.unisharp.Utils.cast(li.get(i).tag, Node.class);
                            n2 = (Node)com.pullenti.unisharp.Utils.cast(li.get(j).tag, Node.class);
                        }
                        else if (li.get(j).canBeGeneralFor(li.get(i)) && !li.get(i).canBeGeneralFor(li.get(j))) {
                            n1 = (Node)com.pullenti.unisharp.Utils.cast(li.get(j).tag, Node.class);
                            n2 = (Node)com.pullenti.unisharp.Utils.cast(li.get(i).tag, Node.class);
                        }
                        if (n1 != null && n2 != null) {
                            if (n1.genFrom == null) 
                                n1.genFrom = new java.util.ArrayList<Node>();
                            if (!n1.genFrom.contains(n2)) 
                                n1.genFrom.add(n2);
                            if (n2.genTo == null) 
                                n2.genTo = new java.util.ArrayList<Node>();
                            if (!n2.genTo.contains(n1)) 
                                n2.genTo.add(n1);
                        }
                    }
                }
            }
        }
        for (Node n : allRefs) {
            if (n.genTo != null && n.genTo.size() > 1) {
                for (int i = n.genTo.size() - 1; i >= 0; i--) {
                    Node p = n.genTo.get(i);
                    boolean del = false;
                    for (int j = 0; j < n.genTo.size(); j++) {
                        if (j != i && n.genTo.get(j).isInGenParentsOrHigher(p)) 
                            del = true;
                    }
                    if (del) {
                        p.genFrom.remove(n);
                        n.genTo.remove(i);
                    }
                }
            }
        }
        for (Node n : allRefs) {
            if (!n.deleted && n.genTo != null && n.genTo.size() == 1) {
                Node p = n.genTo.get(0);
                if (p.genFrom.size() == 1) {
                    n.ref.mergeSlots(p.ref, true);
                    p.ref.tag = n.ref;
                    if (n.ref.getGeneralReferent() == p.ref) 
                        n.ref.setGeneralReferent(null);
                    p.replaceValues(n);
                    for (com.pullenti.ner.TextAnnotation o : p.ref.getOccurrence()) {
                        n.ref.addOccurence(o);
                    }
                    p.deleted = true;
                }
                else 
                    n.ref.setGeneralReferent(p.ref);
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            _correctReferents(t);
        }
        for (Node n : allRefs) {
            if (n.deleted) 
                n.ad.removeReferent(n.ref);
            n.ref.tag = null;
        }
    }

    private static void _correctReferents(com.pullenti.ner.Token t) {
        com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        if (rt == null) 
            return;
        if (rt.referent != null && (rt.referent.tag instanceof com.pullenti.ner.Referent)) 
            rt.referent = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(rt.referent.tag, com.pullenti.ner.Referent.class);
        for (com.pullenti.ner.Token tt = rt.getBeginToken(); tt != null && tt.getEndChar() <= rt.getEndChar(); tt = tt.getNext()) {
            _correctReferents(tt);
        }
    }

    public static class Node {
    
        public com.pullenti.ner.Referent ref;
    
        public com.pullenti.ner.core.AnalyzerData ad;
    
        public java.util.ArrayList<Node> refsTo;
    
        public java.util.ArrayList<Node> refsFrom;
    
        public java.util.ArrayList<Node> genTo;
    
        public java.util.ArrayList<Node> genFrom;
    
        public boolean deleted;
    
        @Override
        public String toString() {
            return ref.toString();
        }
    
        public boolean isInGenParentsOrHigher(Node n) {
            if (genTo == null) 
                return false;
            for (Node p : genTo) {
                if (p == n) 
                    return true;
                else if (p.isInGenParentsOrHigher(n)) 
                    return true;
            }
            return false;
        }
    
        public void replaceValues(Node newNode) {
            if (refsFrom != null) {
                for (Node fr : refsFrom) {
                    boolean ch = false;
                    for (com.pullenti.ner.Slot s : fr.ref.getSlots()) {
                        if (s.getValue() == ref) {
                            fr.ref.uploadSlot(s, newNode.ref);
                            ch = true;
                        }
                    }
                    if (!ch) 
                        continue;
                    for (int i = 0; i < (fr.ref.getSlots().size() - 1); i++) {
                        for (int j = i + 1; j < fr.ref.getSlots().size(); j++) {
                            if (com.pullenti.unisharp.Utils.stringsEq(fr.ref.getSlots().get(i).getTypeName(), fr.ref.getSlots().get(j).getTypeName()) && fr.ref.getSlots().get(i).getValue() == fr.ref.getSlots().get(j).getValue()) {
                                fr.ref.getSlots().remove(j);
                                j--;
                            }
                        }
                    }
                }
            }
        }
    
        public static Node _new410(com.pullenti.ner.Referent _arg1, com.pullenti.ner.core.AnalyzerData _arg2) {
            Node res = new Node();
            res.ref = _arg1;
            res.ad = _arg2;
            return res;
        }
        public Node() {
        }
    }

    public GeneralRelationHelper() {
    }
    public static GeneralRelationHelper _globalInstance;
    
    static {
        try { _globalInstance = new GeneralRelationHelper(); } 
        catch(Exception e) { }
    }
}
