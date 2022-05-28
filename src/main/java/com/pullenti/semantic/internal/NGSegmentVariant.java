/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class NGSegmentVariant implements Comparable<NGSegmentVariant> {

    public double coef;

    public NGSegment source;

    public java.util.ArrayList<NGLink> links = new java.util.ArrayList<NGLink>();

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(coef).append(" = ");
        for (NGLink it : links) {
            if (it != links.get(0)) 
                res.append("; \r\n");
            if (it == null) 
                res.append("<null>");
            else 
                res.append(it.toString());
        }
        return res.toString();
    }

    private static boolean _compareListItemTails(com.pullenti.ner.MetaToken mt1, com.pullenti.ner.MetaToken mt2) {
        com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(mt1.getEndToken(), com.pullenti.ner.TextToken.class);
        com.pullenti.ner.TextToken t2 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(mt2.getEndToken(), com.pullenti.ner.TextToken.class);
        if (t1 == null || t2 == null) 
            return true;
        int k = 0;
        int i1 = t1.term.length() - 1;
        int i2 = t2.term.length() - 1;
        for (; i1 > 0 && i2 > 0; i1--,i2--,k++) {
            if (t1.term.charAt(i1) != t2.term.charAt(i2)) 
                break;
        }
        if (k >= 2) 
            return true;
        String nn = t2.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (t1.isValue(nn, null)) 
            return true;
        if (((short)((t1.getMorph().getNumber().value()) & (t2.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
            return false;
        if (((com.pullenti.morph.MorphCase.ooBitand(t1.getMorph().getCase(), t2.getMorph().getCase()))).isUndefined()) 
            return false;
        if (t1.getMorph()._getClass().isVerb() != t2.getMorph()._getClass().isVerb() && t1.getMorph()._getClass().isAdjective() != t2.getMorph()._getClass().isAdjective()) 
            return false;
        return true;
    }

    public double calcCoef() {
        coef = 0.0;
        for (NGLink it : links) {
            if (it != null) 
                coef += it.coef;
        }
        for (int i = 0; i < links.size(); i++) {
            NGLink li1 = links.get(i);
            if (li1 == null || li1.to == null) 
                continue;
            if (li1.reverce) 
                continue;
            int i0 = li1.to.order;
            if (i0 >= li1.from.order) 
                return coef = -1.0;
            for (int k = i0 + 1; k < i; k++) {
                NGLink li = links.get(k);
                if (li == null) 
                    continue;
                if (li.toVerb != null) 
                    return coef = -1.0;
                int i1 = li.to.order;
                if ((i1 < i0) || i1 > i) 
                    return coef = -1.0;
                if (li.typ == NGLinkType.LIST && li1.typ == NGLinkType.LIST && i0 == i1) 
                    return coef = -1.0;
            }
        }
        for (int i = 0; i < links.size(); i++) {
            java.util.ArrayList<NGItem> list = this.getList(i);
            if (list == null) 
                continue;
            int k;
            for (k = 1; k < (list.size() - 1); k++) {
                if (list.get(k).andBefore) 
                    break;
            }
            if (k >= (list.size() - 1) && list.get(k).andBefore) 
                coef += com.pullenti.semantic.SemanticService.PARAMS.list;
            else {
                int ors = 0;
                int ands = 0;
                for (k = 1; k < list.size(); k++) {
                    if (list.get(k).orBefore) 
                        ors++;
                    else if (list.get(k).andBefore) 
                        ands++;
                }
                if (ands > 0 && ors > 0) 
                    return coef = -1.0;
                for (k = 1; k < list.size(); k++) {
                    if (!list.get(k).andBefore) 
                        break;
                }
                if (k >= list.size()) {
                }
                else 
                    return coef = -1.0;
            }
            NGLink ngli = NGLink._new3130(NGLinkType.LIST);
            for (k = 0; k < (list.size() - 2); k++) {
                for (int kk = k + 2; kk < list.size(); kk++) {
                    ngli.from = list.get(kk);
                    ngli.to = list.get(k);
                    ngli.calcCoef(false);
                    if (ngli.coef < 0) 
                        return coef = -1.0;
                }
            }
            boolean prepIsNotExiAll = false;
            for (k = 0; k < (list.size() - 1); k++) {
                for (int kk = k + 1; kk < list.size(); kk++) {
                    if (!_compareListItemTails(list.get(k).source.source, list.get(kk).source.source)) 
                        coef /= (2.0);
                    if (com.pullenti.unisharp.Utils.isNullOrEmpty(list.get(k).source.prep) != com.pullenti.unisharp.Utils.isNullOrEmpty(list.get(kk).source.prep)) {
                        String str1 = list.get(k).source.getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        String str2 = list.get(kk).source.getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        if (com.pullenti.unisharp.Utils.stringsNe(str1, str2)) 
                            prepIsNotExiAll = true;
                    }
                }
            }
            if (prepIsNotExiAll) 
                coef /= (2.0);
            NGItem last = list.get(list.size() - 1);
            boolean ok = true;
            NGLink lalink = null;
            for (NGLink ll : links) {
                if (ll != null && ll.typ == NGLinkType.GENETIVE) {
                    if (ll.to == last) 
                        lalink = ll;
                    else if (list.contains(ll.to)) {
                        ok = false;
                        break;
                    }
                }
            }
            if (!ok || lalink == null) 
                continue;
            NGLink test = NGLink._new3131(lalink.from, lalink.typ);
            int j;
            for (j = 0; j < (list.size() - 1); j++) {
                test.to = list.get(j);
                int ord = test.to.order;
                test.to.order = last.order;
                test.calcCoef(false);
                test.to.order = ord;
                if (test.coef < 0) 
                    break;
            }
            if (j >= (list.size() - 1)) 
                lalink.toAllListItems = true;
        }
        int befAg = 0;
        int befPac = 0;
        int aftAg = 0;
        int aftPac = 0;
        for (int i = 0; i < links.size(); i++) {
            NGLink li = links.get(i);
            if (li == null) 
                continue;
            if (li.typ == NGLinkType.LIST) 
                continue;
            if (li.typ == NGLinkType.PARTICIPLE) {
                if (li.from.source.partVerbTyp != NGLinkType.UNDEFINED) {
                }
            }
            if ((li.typ == NGLinkType.AGENT || li.typ == NGLinkType.PACIENT || li.typ == NGLinkType.GENETIVE) || li.typ == NGLinkType.PARTICIPLE) {
                if (li.plural == 1) {
                    boolean ok = false;
                    if (li.typ == NGLinkType.PARTICIPLE && li.to != null && this.getList(li.to.order) != null) 
                        ok = true;
                    else if (li.typ != NGLinkType.PARTICIPLE && this.getList(i) != null) 
                        ok = true;
                    else {
                        double co = li.coef;
                        li.calcCoef(true);
                        if (li.coef > 0) 
                            ok = true;
                        li.coef = co;
                        li.plural = 1;
                    }
                    if (!ok) 
                        return coef = -1.0;
                }
                else if (li.plural == 0) {
                    if (li.typ != NGLinkType.PARTICIPLE && this.getList(i) != null) 
                        return coef = -1.0;
                    if (li.typ == NGLinkType.PARTICIPLE && li.to != null && this.getList(li.to.order) != null) 
                        return coef = -1.0;
                }
            }
            if (li.typ == NGLinkType.AGENT || li.typ == NGLinkType.PACIENT || li.typ == NGLinkType.ACTANT) {
            }
            else 
                continue;
            if (li.toVerb != null && li.toVerb == source.beforeVerb) {
                if (source.afterVerb != null && !source.beforeVerb.getFirstVerb().isParticiple()) {
                    boolean hasDelim = false;
                    int ind = li.from.order;
                    java.util.ArrayList<NGItem> list = this.getList(ind);
                    if (list != null) 
                        ind = list.get(list.size() - 1).order;
                    for (int ii = ind; ii < source.items.size(); ii++) {
                        if (source.items.get(ii).andAfter || source.items.get(ii).commaAfter) 
                            hasDelim = true;
                    }
                    if (!hasDelim) 
                        return coef = -1.0;
                }
                if (li.typ == NGLinkType.AGENT && li.toVerb.getFirstVerb().isDeeParticiple()) {
                    boolean hasDelim = false;
                    for (int ii = 0; ii <= li.from.order; ii++) {
                        if (source.items.get(ii).andBefore || source.items.get(ii).commaBefore) 
                            hasDelim = true;
                    }
                    if (!hasDelim) 
                        return coef = -1.0;
                }
                if (li.typ == NGLinkType.AGENT) 
                    befAg++;
                else if (li.typ == NGLinkType.PACIENT) 
                    befPac++;
                if (li.from.source.subSent != null) 
                    continue;
            }
            else if (li.toVerb != null && li.toVerb == source.afterVerb) {
                if (source.beforeVerb != null && !source.beforeVerb.getFirstVerb().isParticiple()) {
                    boolean hasDelim = false;
                    for (int ii = 0; ii <= li.from.order; ii++) {
                        if (source.items.get(ii).andBefore || source.items.get(ii).commaBefore) 
                            hasDelim = true;
                    }
                    if (!hasDelim) 
                        return coef = -1.0;
                }
                if (li.from.source.subSent != null) 
                    continue;
                if (li.typ == NGLinkType.AGENT) 
                    aftAg++;
                else if (li.typ == NGLinkType.PACIENT) 
                    aftPac++;
            }
            if (li.typ == NGLinkType.ACTANT) 
                continue;
        }
        if ((befAg > 1 || befPac > 1 || aftAg > 1) || aftPac > 1) 
            return coef = -1.0;
        for (int i = 0; i < links.size(); i++) {
            NGLink li = links.get(i);
            if (li == null) 
                continue;
            if (li.typ != NGLinkType.ACTANT || li.toVerb == null) 
                continue;
        }
        for (int i = 0; i < links.size(); i++) {
            NGLink li = links.get(i);
            if (li == null) 
                continue;
            if (li.typ != NGLinkType.GENETIVE || li.to == null) 
                continue;
            if (li.from.source.typ == SentItemType.FORMULA) {
                for (NGLink li0 : links) {
                    if ((li0 != null && li0 != li && li0.typ == NGLinkType.GENETIVE) && li0.from == li.to) 
                        coef /= (2.0);
                }
            }
            if (li.to.source.typ == SentItemType.FORMULA) {
                for (NGLink li0 : links) {
                    if ((li0 != null && li0 != li && li0.typ == NGLinkType.GENETIVE) && li0.to == li.to) {
                        if (li0.from.order < li.from.order) 
                            coef /= (2.0);
                    }
                }
            }
        }
        return coef;
    }

    @Override
    public int compareTo(NGSegmentVariant other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public java.util.ArrayList<NGItem> getListByLastItem(NGItem it) {
        java.util.ArrayList<NGItem> res = new java.util.ArrayList<NGItem>();
        res.add(it);
        for (int i = links.size() - 1; i >= 0; i--) {
            if ((links.get(i) != null && links.get(i).from == it && links.get(i).typ == NGLinkType.LIST) && links.get(i).to != null) {
                it = links.get(i).to;
                res.add(0, it);
            }
        }
        if (res.size() > 1) 
            return res;
        return null;
    }

    public java.util.ArrayList<NGItem> getList(int ord) {
        if (ord >= source.items.size()) 
            return null;
        NGLink li = links.get(ord);
        if (li == null) 
            return null;
        java.util.ArrayList<NGItem> res = null;
        NGItem ngit = source.items.get(ord);
        if (li.typ == NGLinkType.LIST) {
            if (li.toVerb == null) 
                return null;
            res = new java.util.ArrayList<NGItem>();
            res.add(NGItem._new3132(new SentItem(li.toVerb), ord - 1));
            res.add(ngit);
        }
        for (int i = ord + 1; i < links.size(); i++) {
            li = links.get(i);
            if (li == null || li.typ != NGLinkType.LIST || li.to == null) 
                continue;
            if (li.to == ngit) {
                if (res == null) {
                    res = new java.util.ArrayList<NGItem>();
                    res.add(ngit);
                }
                ngit = source.items.get(i);
                res.add(ngit);
            }
        }
        return res;
    }

    public void correctMorph() {
        for (int i = 0; i < links.size(); i++) {
            NGLink li = links.get(i);
            if (li == null) 
                continue;
            if (li.typ == NGLinkType.AGENT || li.typ == NGLinkType.PACIENT) {
                if (li.plural == 1) {
                    java.util.ArrayList<NGItem> list = this.getList(i);
                    if (list != null) 
                        continue;
                    li.from.source.plural = 1;
                }
            }
        }
    }

    public static NGSegmentVariant _new3129(NGSegment _arg1) {
        NGSegmentVariant res = new NGSegmentVariant();
        res.source = _arg1;
        return res;
    }
    public NGSegmentVariant() {
    }
}
