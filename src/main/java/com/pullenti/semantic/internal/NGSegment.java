/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class NGSegment {

    public com.pullenti.ner.core.VerbPhraseToken beforeVerb;

    public java.util.ArrayList<NGItem> items = new java.util.ArrayList<NGItem>();

    public com.pullenti.ner.core.VerbPhraseToken afterVerb;

    public java.util.ArrayList<NGSegmentVariant> variants = new java.util.ArrayList<NGSegmentVariant>();

    public int ind;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (beforeVerb != null) 
            tmp.append("<").append(beforeVerb.toString()).append(">: ");
        for (NGItem it : items) {
            if (it != items.get(0)) 
                tmp.append("; \r\n");
            tmp.append(it.toString());
        }
        if (afterVerb != null) 
            tmp.append(" :<").append(afterVerb.toString()).append(">");
        return tmp.toString();
    }

    public static java.util.ArrayList<NGSegment> createSegments(Sentence s) {
        java.util.ArrayList<NGSegment> res = new java.util.ArrayList<NGSegment>();
        for (int i = 0; i < s.items.size(); i++) {
            SentItem it = s.items.get(i);
            if (it.typ == SentItemType.VERB || it.typ == SentItemType.DELIM) 
                continue;
            NGSegment seg = new NGSegment();
            NGItem nit = NGItem._new3126(it);
            for (int j = i - 1; j >= 0; j--) {
                it = s.items.get(j);
                if (it.typ == SentItemType.VERB) {
                    seg.beforeVerb = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class);
                    break;
                }
                if (it.typ == SentItemType.DELIM) 
                    break;
                if (it.canBeCommaEnd()) {
                    if (((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.COMMA) 
                        nit.commaBefore = true;
                    else {
                        nit.andBefore = true;
                        if (((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.OR) 
                            nit.orBefore = true;
                    }
                }
                if (it.typ == SentItemType.CONJ || it.canBeNoun()) 
                    break;
            }
            boolean comma = false;
            boolean and = false;
            boolean or = false;
            for (; i < s.items.size(); i++) {
                it = s.items.get(i);
                if (it.canBeCommaEnd()) {
                    comma = false;
                    and = false;
                    or = false;
                    if (((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.COMMA) 
                        comma = true;
                    else {
                        and = true;
                        if (((com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.ConjunctionToken.class)).typ == com.pullenti.ner.core.ConjunctionType.OR) 
                            or = true;
                    }
                    if (seg.items.size() > 0) {
                        if (comma) 
                            seg.items.get(seg.items.size() - 1).commaAfter = true;
                        else {
                            seg.items.get(seg.items.size() - 1).andAfter = true;
                            if (or) 
                                seg.items.get(seg.items.size() - 1).orAfter = true;
                        }
                    }
                    continue;
                }
                if (it.canBeNoun() || it.typ == SentItemType.ADVERB) {
                    nit = NGItem._new3127(it, comma, and, or);
                    seg.items.add(nit);
                    comma = false;
                    and = false;
                    or = false;
                }
                else if (it.typ == SentItemType.VERB || it.typ == SentItemType.CONJ || it.typ == SentItemType.DELIM) 
                    break;
            }
            for (int j = i; j < s.items.size(); j++) {
                it = s.items.get(j);
                if (it.typ == SentItemType.VERB) {
                    seg.afterVerb = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class);
                    break;
                }
                if ((it.typ == SentItemType.CONJ || it.canBeNoun() || it.typ == SentItemType.DELIM) || it.typ == SentItemType.ADVERB) 
                    break;
            }
            res.add(seg);
        }
        for (NGSegment ss : res) {
            ss.createLinks(false);
        }
        return res;
    }

    public void createLinks(boolean afterPart) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).order = i;
            items.get(i).prepare();
        }
        NGLink li = null;
        for (int i = 0; i < items.size(); i++) {
            NGItem it = items.get(i);
            if (it.source.typ == SentItemType.ADVERB) 
                continue;
            boolean ignoreBefore = false;
            double mult = 1.0;
            if (it.commaBefore || it.andBefore) {
                for (int j = i - 1; j >= 0; j--) {
                    if (li == null) 
                        li = new NGLink();
                    li.typ = NGLinkType.LIST;
                    li.from = it;
                    li.to = items.get(j);
                    li.toVerb = null;
                    li.calcCoef(false);
                    if (li.coef >= 0) {
                        it.links.add(li);
                        li = null;
                    }
                    if (it.source.typ == SentItemType.PARTBEFORE || it.source.typ == SentItemType.SUBSENT || it.source.typ == SentItemType.DEEPART) {
                        if (it.commaBefore) {
                            if (li == null) 
                                li = new NGLink();
                            li.typ = NGLinkType.PARTICIPLE;
                            li.from = it;
                            li.to = items.get(j);
                            li.toVerb = null;
                            li.calcCoef(false);
                            if (li.coef >= 0) {
                                it.links.add(li);
                                li = null;
                            }
                        }
                    }
                    if ((!it.andBefore && it.source.typ == SentItemType.NOUN && items.get(j).source.typ == SentItemType.NOUN) && items.get(i - 1).source.typ == SentItemType.PARTBEFORE) {
                        boolean ok = true;
                        for (int jj = j + 1; jj < i; jj++) {
                            if ((items.get(jj).source.typ == SentItemType.DELIM || items.get(jj).source.typ == SentItemType.NOUN || items.get(jj).source.typ == SentItemType.SUBSENT) || items.get(jj).source.typ == SentItemType.PARTBEFORE) {
                            }
                            else {
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            if (li == null) 
                                li = new NGLink();
                            li.typ = NGLinkType.GENETIVE;
                            li.from = it;
                            li.to = items.get(j);
                            li.toVerb = null;
                            li.calcCoef(false);
                            if (li.coef >= 0) {
                                it.links.add(li);
                                li = null;
                            }
                        }
                    }
                }
                ignoreBefore = true;
            }
            else {
                for (int j = i - 1; j >= 0; j--) {
                    if (items.get(j).source.typ == SentItemType.SUBSENT) 
                        continue;
                    if (li == null) 
                        li = new NGLink();
                    li.typ = NGLinkType.GENETIVE;
                    li.from = it;
                    li.to = items.get(j);
                    li.toVerb = null;
                    li.calcCoef(false);
                    if (li.coef >= 0) {
                        it.links.add(li);
                        li = null;
                    }
                    if (li == null) 
                        li = new NGLink();
                    li.typ = NGLinkType.NAME;
                    li.from = it;
                    li.to = items.get(j);
                    li.toVerb = null;
                    li.calcCoef(false);
                    if (li.coef >= 0) {
                        it.links.add(li);
                        li = null;
                    }
                    boolean nodelim = true;
                    for (int jj = j + 1; jj <= i; jj++) {
                        if (items.get(jj).commaBefore || items.get(jj).andBefore) {
                            nodelim = false;
                            break;
                        }
                    }
                    if (nodelim) {
                        if (li == null) 
                            li = new NGLink();
                        li.typ = NGLinkType.BE;
                        li.from = it;
                        li.to = items.get(j);
                        li.toVerb = null;
                        li.calcCoef(false);
                        if (li.coef >= 0) {
                            it.links.add(li);
                            li = null;
                        }
                    }
                    if (it.source.typ == SentItemType.PARTBEFORE || it.source.typ == SentItemType.SUBSENT || it.source.typ == SentItemType.DEEPART) {
                        boolean hasDelim = false;
                        for (int jj = i - 1; jj > j; jj--) {
                            if (items.get(jj).source.canBeCommaEnd()) {
                                hasDelim = true;
                                break;
                            }
                        }
                        if (hasDelim) {
                            if (li == null) 
                                li = new NGLink();
                            li.typ = NGLinkType.PARTICIPLE;
                            li.from = it;
                            li.to = items.get(j);
                            li.toVerb = null;
                            li.calcCoef(false);
                            if (li.coef >= 0) {
                                it.links.add(li);
                                li = null;
                            }
                        }
                    }
                    if (items.get(j).source.typ == SentItemType.PARTBEFORE) 
                        mult *= 0.5;
                    if (items.get(j).source.typ == SentItemType.VERB) {
                        ignoreBefore = true;
                        break;
                    }
                }
                if (beforeVerb != null && !ignoreBefore && it.source.typ != SentItemType.DEEPART) {
                    boolean ok = false;
                    if (li == null) 
                        li = new NGLink();
                    li.typ = NGLinkType.AGENT;
                    li.from = it;
                    li.to = null;
                    li.toVerb = beforeVerb;
                    li.calcCoef(false);
                    li.coef *= mult;
                    if (li.coef >= 0) {
                        it.links.add(li);
                        ok = true;
                        li = null;
                    }
                    if (li == null) 
                        li = new NGLink();
                    li.typ = NGLinkType.PACIENT;
                    li.from = it;
                    li.to = null;
                    li.toVerb = beforeVerb;
                    li.calcCoef(false);
                    li.coef *= mult;
                    if (li.coef >= 0) {
                        it.links.add(li);
                        ok = true;
                        li = null;
                    }{
                            if (li == null) 
                                li = new NGLink();
                            li.typ = NGLinkType.ACTANT;
                            li.from = it;
                            li.to = null;
                            li.toVerb = beforeVerb;
                            li.calcCoef(false);
                            li.coef *= mult;
                            if (ok) 
                                li.coef /= (2.0);
                            if (li.coef >= 0) {
                                it.links.add(li);
                                ok = true;
                                li = null;
                            }
                        }
                }
            }
            if (afterVerb != null && it.source.typ != SentItemType.DEEPART) {
                boolean ok = false;
                if (afterPart && beforeVerb != null) {
                    for (NGLink l : it.links) {
                        if (l.toVerb == beforeVerb && ((l.typ == NGLinkType.AGENT || l.typ == NGLinkType.PACIENT))) 
                            ok = true;
                    }
                    if (ok) 
                        continue;
                }
                if (li == null) 
                    li = new NGLink();
                li.typ = NGLinkType.AGENT;
                li.from = it;
                li.to = null;
                li.toVerb = afterVerb;
                li.calcCoef(false);
                if (li.coef >= 0) {
                    it.links.add(li);
                    ok = true;
                    li = null;
                }
                if (li == null) 
                    li = new NGLink();
                li.typ = NGLinkType.PACIENT;
                li.from = it;
                li.to = null;
                li.toVerb = afterVerb;
                li.calcCoef(false);
                if (li.coef >= 0) {
                    it.links.add(li);
                    ok = true;
                    li = null;
                }
                if (li == null) 
                    li = new NGLink();
                li.typ = NGLinkType.ACTANT;
                li.from = it;
                li.to = null;
                li.toVerb = afterVerb;
                li.calcCoef(false);
                if (li.coef >= 0) {
                    it.links.add(li);
                    ok = true;
                    li = null;
                }
            }
        }
        for (int i = 1; i < items.size(); i++) {
            NGItem it = items.get(i);
            if (it.source.typ != SentItemType.NOUN) 
                continue;
            NGItem it0 = items.get(i - 1);
            if (it0.source.typ != SentItemType.NOUN) 
                continue;
            if (it0.links.size() > 0) 
                continue;
            li = NGLink._new3128(NGLinkType.GENETIVE, it0, it, true);
            li.calcCoef(true);
            if (li.coef > 0) 
                it0.links.add(li);
        }
    }

    public void createVariants(int maxCount) {
        variants.clear();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).ind = 0;
        }
        NGSegmentVariant var = null;
        for (int kkk = 0; kkk < 1000; kkk++) {
            if (var == null) 
                var = NGSegmentVariant._new3129(this);
            else 
                var.links.clear();
            for (int i = 0; i < items.size(); i++) {
                NGItem it = items.get(i);
                if (it.ind < it.links.size()) 
                    var.links.add(it.links.get(it.ind));
                else 
                    var.links.add(null);
            }
            var.calcCoef();
            if (var.coef >= 0) {
                variants.add(var);
                var = null;
                if (variants.size() > (maxCount * 5)) {
                    _sortVars(variants);
                    for(int jjj = maxCount + variants.size() - maxCount - 1, mmm = maxCount; jjj >= mmm; jjj--) variants.remove(jjj);
                }
            }
            int j;
            for (j = items.size() - 1; j >= 0; j--) {
                NGItem it = items.get(j);
                if ((++it.ind) >= it.links.size()) 
                    it.ind = 0;
                else 
                    break;
            }
            if (j < 0) 
                break;
        }
        _sortVars(variants);
        if (variants.size() > maxCount) 
            for(int jjj = maxCount + variants.size() - maxCount - 1, mmm = maxCount; jjj >= mmm; jjj--) variants.remove(jjj);
    }

    private static void _sortVars(java.util.ArrayList<NGSegmentVariant> vars) {
        java.util.Collections.sort(vars);
    }
    public NGSegment() {
    }
}
