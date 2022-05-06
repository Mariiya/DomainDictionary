/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Семантический граф
 * 
 */
public class SemGraph {

    /**
     * Владелец графа (фрагмент, блок или документ)
     */
    public ISemContainer owner;

    public SemGraph getHigher() {
        if (owner != null && owner.getHigher() != null) 
            return owner.getHigher().getGraph();
        else 
            return null;
    }


    /**
     * Список объектов SemObject (узлы графа), упорядочиваются по первым позициям в тексте
     * 
     */
    public java.util.ArrayList<SemObject> objects = new java.util.ArrayList<SemObject>();

    /**
     * Список связей SemLink, неупорядоченный, дублируется у объектов в LinksFrom и LinksTo
     * 
     */
    public java.util.ArrayList<SemLink> links = new java.util.ArrayList<SemLink>();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(objects.size()).append("obj ").append(links.size()).append("links: ");
        for (SemLink li : links) {
            if (li != links.get(0)) 
                tmp.append("; ");
            tmp.append(li);
            if (tmp.length() > 100) 
                break;
        }
        if (links.size() == 0) {
            for (SemObject o : objects) {
                if (o != objects.get(0)) 
                    tmp.append("; ");
                tmp.append(o);
                if (tmp.length() > 100) 
                    break;
            }
        }
        return tmp.toString();
    }

    public SemLink addLink(SemLinkType typ, SemObject src, SemObject tgt, String ques, boolean or, String prep) {
        if (src == null || tgt == null) 
            return null;
        for (SemLink li : src.graph.links) {
            if (li.typ == typ && li.getSource() == src && li.getTarget() == tgt) 
                return li;
        }
        if (src.graph != tgt.graph) {
            for (SemLink li : tgt.graph.links) {
                if (li.typ == typ && li.getSource() == src && li.getTarget() == tgt) 
                    return li;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(tgt.morph.normalCase, "ДОМ")) {
        }
        SemLink res = SemLink._new3173(this, src, tgt, typ, ques, or, prep);
        links.add(res);
        return res;
    }

    public void removeLink(SemLink li) {
        if (links.contains(li)) 
            links.remove(li);
        if (li.getSource().linksFrom.contains(li)) 
            li.getSource().linksFrom.remove(li);
        if (li.getTarget().linksTo.contains(li)) 
            li.getTarget().linksTo.remove(li);
        if (li.altLink != null && li.altLink.altLink == li) 
            li.altLink.altLink = null;
    }

    public void mergeWith(SemGraph gr) {
        for (SemObject o : gr.objects) {
            if (!objects.contains(o)) {
                objects.add(o);
                o.graph = this;
            }
        }
        for (SemLink li : gr.links) {
            if (!links.contains(li)) 
                links.add(li);
        }
    }

    public void removeObject(SemObject obj) {
        for (SemLink li : obj.linksFrom) {
            if (li.getTarget().linksTo.contains(li)) 
                li.getTarget().linksTo.remove(li);
            if (links.contains(li)) 
                links.remove(li);
            else if (li.getTarget().graph.links.contains(li)) 
                li.getTarget().graph.links.remove(li);
        }
        for (SemLink li : obj.linksTo) {
            if (li.getSource().linksFrom.contains(li)) 
                li.getSource().linksFrom.remove(li);
            if (links.contains(li)) 
                links.remove(li);
            else if (li.getSource().graph.links.contains(li)) 
                li.getSource().graph.links.remove(li);
        }
        if (objects.contains(obj)) 
            objects.remove(obj);
    }
    public SemGraph() {
    }
}
