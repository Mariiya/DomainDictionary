/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class SentenceVariant implements Comparable<SentenceVariant> {

    public double coef;

    public java.util.ArrayList<NGSegmentVariant> segs = new java.util.ArrayList<NGSegmentVariant>();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(coef).append(": ");
        for (NGSegmentVariant s : segs) {
            if (s != segs.get(0)) 
                tmp.append("; \r\n");
            if (s != null) 
                tmp.append(s.toString());
            else 
                tmp.append("null");
        }
        return tmp.toString();
    }

    public double calcCoef() {
        coef = 0.0;
        for (int i = 0; i < segs.size(); i++) {
            if (segs.get(i) != null) 
                coef += segs.get(i).coef;
        }
        for (int i = 0; i < (segs.size() - 1); i++) {
            NGSegmentVariant seg0 = segs.get(i);
            if (seg0 == null) 
                continue;
            NGSegmentVariant seg1 = segs.get(i + 1);
            if (seg1 == null) 
                continue;
            boolean hasAgent = false;
            boolean hasPacient = false;
            for (NGLink li : seg0.links) {
                if (li != null && li.toVerb == seg1.source.beforeVerb) {
                    if (li.typ == NGLinkType.AGENT) 
                        hasAgent = true;
                    else if (li.typ == NGLinkType.PACIENT) {
                        hasPacient = true;
                        for (NGLink lii : li.from.links) {
                            if ((lii != null && lii.typ == NGLinkType.AGENT && lii.coef >= li.coef) && lii.toVerb == li.toVerb) {
                                for (NGLink liii : seg1.links) {
                                    if (liii != null && liii.toVerb == li.toVerb && liii.typ == NGLinkType.AGENT) {
                                        if (liii.coef < ((lii.coef / (3.0)))) 
                                            return coef = -1.0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (NGLink li : seg1.links) {
                if (li != null && li.toVerb == seg1.source.beforeVerb) {
                    if (li.typ == NGLinkType.AGENT && hasAgent) 
                        return coef = -1.0;
                    else if (li.typ == NGLinkType.PACIENT && hasPacient) 
                        return coef = -1.0;
                }
            }
        }
        return coef;
    }

    @Override
    public int compareTo(SentenceVariant other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public void createAltLinks() {
        double coef0 = coef;
        for (int i = 0; i < segs.size(); i++) {
            NGSegmentVariant seg = segs.get(i);
            if (seg == null) 
                continue;
            for (int j = 0; j < seg.links.size(); j++) {
                NGLink li = seg.links.get(j);
                if (li == null || li.typ == NGLinkType.LIST) 
                    continue;
                if (li.from.links.size() < 2) 
                    continue;
                if (li.from.source.typ == SentItemType.FORMULA) 
                    continue;
                if (li.to != null && li.to.source.typ == SentItemType.FORMULA) 
                    continue;
                for (NGLink l : li.from.links) {
                    if (l != li && l.typ != NGLinkType.LIST) {
                        if (l.to != null && l.to.source.typ == SentItemType.FORMULA) 
                            continue;
                        if (l.typ == NGLinkType.ACTANT) {
                            if (li.typ == NGLinkType.AGENT || li.typ == NGLinkType.PACIENT) 
                                continue;
                        }
                        com.pullenti.unisharp.Utils.putArrayValue(seg.links, j, l);
                        seg.calcCoef();
                        double _coef = coef0 - (100.0);
                        if (seg.coef > 0) {
                            this.calcCoef();
                            _coef = coef;
                        }
                        coef = coef0;
                        com.pullenti.unisharp.Utils.putArrayValue(seg.links, j, li);
                        seg.calcCoef();
                        if (_coef >= coef) {
                            li.altLink = l;
                            break;
                        }
                    }
                }
            }
        }
    }
    public SentenceVariant() {
    }
}
