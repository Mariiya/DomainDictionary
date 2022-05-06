/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class Subsent {

    public Subsent owner;

    public Subsent getOwnerRoot() {
        int k = 0;
        for (Subsent s = this.owner; s != null && (k < 100); s = owner,k++) {
            if (s.owner == null) 
                return s;
        }
        return null;
    }


    public java.util.ArrayList<SentItem> items = new java.util.ArrayList<SentItem>();

    public java.util.ArrayList<com.pullenti.ner.MetaToken> delims = new java.util.ArrayList<com.pullenti.ner.MetaToken>();

    public com.pullenti.semantic.SemFragment resFrag;

    public boolean isOr;

    public boolean check(DelimType _typ) {
        for (com.pullenti.ner.MetaToken d : delims) {
            if ((d instanceof DelimToken) && (((((DelimToken)com.pullenti.unisharp.Utils.cast(d, DelimToken.class)).typ.value()) & (_typ.value()))) != (DelimType.UNDEFINED.value())) 
                return true;
            else if ((d instanceof com.pullenti.ner.core.ConjunctionToken) && _typ == DelimType.AND) 
                return true;
        }
        return false;
    }

    public boolean checkOr() {
        for (com.pullenti.ner.MetaToken d : delims) {
            if ((d instanceof com.pullenti.ner.core.ConjunctionToken) && ((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(d, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.OR) 
                return true;
        }
        return false;
    }

    public boolean onlyConj() {
        for (com.pullenti.ner.MetaToken d : delims) {
            if (d instanceof DelimToken) 
                return false;
        }
        return true;
    }

    public boolean canBeNextInList(Subsent next) {
        if (next.delims.size() == 0) 
            return true;
        for (com.pullenti.ner.MetaToken d : next.delims) {
            if (d instanceof DelimToken) {
                if (!this.check(((DelimToken)com.pullenti.unisharp.Utils.cast(d, DelimToken.class)).typ)) 
                    return false;
            }
        }
        return true;
    }

    public String question;

    public boolean isThenElseRoot = false;

    public com.pullenti.semantic.SemFraglinkType typ = com.pullenti.semantic.SemFraglinkType.UNDEFINED;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (isOr) 
            tmp.append("OR ");
        if (question != null) 
            tmp.append("(").append(question).append("?) ");
        for (com.pullenti.ner.MetaToken it : delims) {
            tmp.append("<").append(it).append("> ");
        }
        tmp.append('[');
        for (SentItem it : items) {
            if (it != items.get(0)) 
                tmp.append(", ");
            tmp.append(it);
        }
        tmp.append("]");
        if (owner != null) 
            tmp.append(" -> ").append(owner);
        return tmp.toString();
    }

    private boolean hasCommaAnd(int b, int e) {
        for (SentItem it : items) {
            if (it.typ == SentItemType.CONJ) {
                if (it.source.getBeginToken().getBeginChar() >= b && it.source.getEndToken().getEndChar() <= e) 
                    return true;
            }
        }
        return false;
    }

    public static java.util.ArrayList<Subsent> createSubsents(Sentence sent) {
        if (sent.items.size() == 0) 
            return null;
        java.util.ArrayList<Subsent> res = new java.util.ArrayList<Subsent>();
        int begin = sent.items.get(0).getBeginToken().getBeginChar();
        int end = sent.items.get(sent.items.size() - 1).getEndToken().getEndChar();
        byte[] map = new byte[(end + 1) - begin];
        if (sent.bestVar != null) {
            for (NGSegmentVariant seg : sent.bestVar.segs) {
                if (seg != null) {
                    for (NGLink li : seg.links) {
                        if (li != null && li.typ == NGLinkType.LIST) {
                            for (int i = (li.to == null ? li.toVerb.getBeginChar() : li.to.source.getBeginToken().getBeginChar()); i <= li.from.source.getEndToken().getEndChar(); i++) {
                                int po = i - begin;
                                if (po >= 0 && (po < map.length)) 
                                    map[po] = (byte)1;
                            }
                        }
                    }
                }
            }
        }
        Subsent ss = new Subsent();
        boolean hasVerb = false;
        for (int i = 0; i < sent.items.size(); i++) {
            SentItem it = sent.items.get(i);
            boolean delim = false;
            if (it.typ == SentItemType.DELIM) 
                delim = true;
            else if (it.typ == SentItemType.CONJ && map[it.getBeginToken().getBeginChar() - begin] == ((byte)0)) {
                delim = true;
                if (((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.COMMA) {
                    if (!hasVerb) 
                        delim = false;
                }
            }
            if (!delim) {
                if (it.typ == SentItemType.VERB) 
                    hasVerb = true;
                ss.items.add(it);
                continue;
            }
            if (ss.items.size() == 0) {
                ss.delims.add(it.source);
                continue;
            }
            if (ss.items.size() > 0) 
                res.add(ss);
            ss = new Subsent();
            hasVerb = false;
            ss.delims.add(it.source);
        }
        if (ss.items.size() > 0) 
            res.add(ss);
        for (int i = 0; i < res.size(); i++) {
            Subsent r = res.get(i);
            int j;
            if (r.check(DelimType.IF)) {
                boolean hasThen = false;
                boolean hasElse = false;
                for (j = i + 1; j < res.size(); j++) {
                    if (res.get(j).check(DelimType.THEN)) {
                        if (hasThen) 
                            break;
                        res.get(j).owner = r;
                        res.get(j).question = "если";
                        res.get(j).typ = com.pullenti.semantic.SemFraglinkType.IFTHEN;
                        hasThen = true;
                        r.isThenElseRoot = true;
                    }
                    else if (res.get(j).check(DelimType.ELSE)) {
                        if (hasElse) 
                            break;
                        res.get(j).owner = r;
                        res.get(j).question = "иначе";
                        res.get(j).typ = com.pullenti.semantic.SemFraglinkType.IFELSE;
                        hasElse = true;
                        r.isThenElseRoot = true;
                    }
                    else if (res.get(j).check(DelimType.IF)) {
                        if (res.get(j).check(DelimType.AND)) 
                            res.get(j).owner = r;
                        else 
                            break;
                    }
                }
                if (!hasThen && i > 0) {
                    if (res.get(0).owner == null && res.get(0).onlyConj()) {
                        res.get(0).owner = r;
                        res.get(0).question = "если";
                        r.isThenElseRoot = true;
                        res.get(0).typ = com.pullenti.semantic.SemFraglinkType.IFTHEN;
                    }
                    else if (res.get(0).owner != null) {
                        r.owner = res.get(0);
                        r.question = "если";
                        r.typ = com.pullenti.semantic.SemFraglinkType.IFTHEN;
                    }
                }
                continue;
            }
            if (r.check(DelimType.BECAUSE)) {
                boolean hasThen = false;
                for (j = i + 1; j < res.size(); j++) {
                    if (res.get(j).check(DelimType.THEN)) {
                        if (hasThen) 
                            break;
                        res.get(j).owner = r;
                        res.get(j).question = "по причине";
                        res.get(j).typ = com.pullenti.semantic.SemFraglinkType.BECAUSE;
                        hasThen = true;
                        r.isThenElseRoot = true;
                    }
                }
                if (!hasThen && i > 0) {
                    if (res.get(0).owner == null && res.get(0).onlyConj()) {
                        res.get(0).owner = r;
                        res.get(0).question = "по причине";
                        r.isThenElseRoot = true;
                        res.get(0).typ = com.pullenti.semantic.SemFraglinkType.BECAUSE;
                        continue;
                    }
                }
                if (!hasThen && ((i + 1) < res.size())) {
                    if (res.get(i + 1).owner == null && res.get(i + 1).onlyConj()) {
                        res.get(i + 1).owner = r;
                        res.get(i + 1).question = "по причине";
                        r.isThenElseRoot = true;
                        res.get(i + 1).typ = com.pullenti.semantic.SemFraglinkType.BECAUSE;
                        continue;
                    }
                }
                continue;
            }
            if (r.check(DelimType.BUT)) {
                if (i > 0) {
                    if (res.get(i - 1).owner == null && res.get(i - 1).onlyConj()) {
                        res.get(i - 1).owner = r;
                        res.get(i - 1).question = "но";
                        r.isThenElseRoot = true;
                        res.get(i - 1).typ = com.pullenti.semantic.SemFraglinkType.BUT;
                        continue;
                    }
                }
            }
            if (r.check(DelimType.WHAT)) {
                if (i > 0) {
                    if (res.get(i - 1).owner == null && res.get(i - 1).onlyConj()) {
                        res.get(i - 1).owner = r;
                        res.get(i - 1).question = "что";
                        r.isThenElseRoot = true;
                        res.get(i - 1).typ = com.pullenti.semantic.SemFraglinkType.WHAT;
                        continue;
                    }
                }
            }
            if (r.check(DelimType.FOR)) {
                if ((i + 1) < res.size()) {
                    if (res.get(i + 1).owner == null && res.get(i + 1).onlyConj()) {
                        res.get(i + 1).owner = r;
                        res.get(i + 1).question = "чтобы";
                        r.isThenElseRoot = true;
                        res.get(i + 1).typ = com.pullenti.semantic.SemFraglinkType.FOR;
                        continue;
                    }
                }
                if (i > 0) {
                    if (res.get(i - 1).owner == null && res.get(i - 1).onlyConj()) {
                        res.get(i - 1).owner = r;
                        res.get(i - 1).question = "чтобы";
                        r.isThenElseRoot = true;
                        res.get(i - 1).typ = com.pullenti.semantic.SemFraglinkType.FOR;
                        continue;
                    }
                }
            }
        }
        for (int i = 1; i < res.size(); i++) {
            Subsent r = res.get(i);
            if (!r.check(DelimType.AND) || r.owner != null) 
                continue;
            for (int j = i - 1; j >= 0; j--) {
                Subsent rr = res.get(j);
                if (rr.canBeNextInList(r) && ((rr.owner == null || ((rr.getOwnerRoot() != null && rr.getOwnerRoot().canBeNextInList(r)))))) {
                    if (r.checkOr()) 
                        rr.isOr = true;
                    com.pullenti.unisharp.Utils.addToArrayList(rr.items, r.items);
                    res.remove(i);
                    i--;
                    break;
                }
            }
        }
        return res;
    }
    public Subsent() {
    }
}
