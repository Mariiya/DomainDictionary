/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class Sentence implements Comparable<Sentence> {

    private void _createLists(NGSegmentVariant s) {
        for (int i = 0; i < s.links.size(); i++) {
            java.util.ArrayList<NGItem> list = s.getList(i);
            if (list == null) 
                continue;
            if (list.get(0).source.result == null) 
                continue;
            SentItem root = list.get(0).source;
            root.resultList = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
            for (NGItem li : list) {
                if (li.source.result != null) 
                    root.resultList.add(li.source.result);
                if (li != list.get(0) && li.orBefore) 
                    root.resultListOr = true;
            }
        }
    }

    private void _setLastAltLinks(com.pullenti.semantic.SemGraph fr) {
        if (fr.links.size() > 1) {
            com.pullenti.semantic.SemLink li0 = fr.links.get(fr.links.size() - 2);
            com.pullenti.semantic.SemLink li1 = fr.links.get(fr.links.size() - 1);
            li0.altLink = li1;
            li1.altLink = li0;
        }
    }

    private void _createLinks(NGSegmentVariant s) {
        for (int i = 0; i < s.links.size(); i++) {
            NGLink link0 = s.links.get(i);
            if (link0 == null) 
                continue;
            if (link0.typ == NGLinkType.LIST) 
                continue;
            for (int k = 0; k < 2; k++) {
                NGLink li = link0;
                if (k == 1) 
                    li = li.altLink;
                if (li == null) 
                    break;
                if (li.from.getResObject() == null) 
                    continue;
                if (k == 1) {
                }
                com.pullenti.semantic.SemGraph gr = li.from.getResObject().graph;
                if (li.to != null && li.to.getResObject() != null) {
                    com.pullenti.semantic.SemLink link = null;
                    if (li.typ == NGLinkType.PARTICIPLE && li.from.source.subTyp == SentItemSubtype.WICH) {
                        link = gr.addLink(com.pullenti.semantic.SemLinkType.ANAFOR, li.from.getResObject(), li.to.getResObject(), null, false, null);
                        if (k > 0) 
                            this._setLastAltLinks(gr);
                        continue;
                    }
                    if (li.typ == NGLinkType.PARTICIPLE && li.from.source.typ == SentItemType.PARTBEFORE) {
                        link = gr.addLink(com.pullenti.semantic.SemLinkType.PARTICIPLE, li.to.getResObject(), li.from.getResObject(), "какой", false, null);
                        if (k > 0) 
                            this._setLastAltLinks(gr);
                        if (li.from.source.resultList != null && li.typ == NGLinkType.PARTICIPLE) {
                            link.isOr = li.from.source.resultListOr;
                            for (int ii = 1; ii < li.from.source.resultList.size(); ii++) {
                                gr.addLink(link.typ, link.getSource(), li.from.source.resultList.get(ii), link.question, link.isOr, null);
                            }
                        }
                        continue;
                    }
                    if (li.typ == NGLinkType.BE) {
                        if ((li.from.source.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) && li.to != null) {
                            gr.addLink(com.pullenti.semantic.SemLinkType.DETAIL, li.to.getResObject(), li.from.getResObject(), "какой", false, li.getFromPrep());
                            continue;
                        }
                        com.pullenti.semantic.SemObject be = com.pullenti.semantic.SemObject._new3133(gr, com.pullenti.semantic.SemObjectType.VERB);
                        be.tokens.add(li.from.source.source);
                        be.morph.normalCase = (be.morph.normalFull = "БЫТЬ");
                        gr.objects.add(be);
                        gr.addLink(com.pullenti.semantic.SemLinkType.AGENT, be, li.to.getResObject(), null, false, null);
                        gr.addLink(com.pullenti.semantic.SemLinkType.PACIENT, be, li.from.getResObject(), null, false, null);
                        continue;
                    }
                    com.pullenti.semantic.SemLinkType ty = com.pullenti.semantic.SemLinkType.UNDEFINED;
                    String ques = null;
                    if (li.typ == NGLinkType.GENETIVE) {
                        if (li.canBePacient) 
                            ty = com.pullenti.semantic.SemLinkType.PACIENT;
                        else {
                            ty = com.pullenti.semantic.SemLinkType.DETAIL;
                            ques = "чего";
                        }
                        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(li.getFromPrep())) 
                            ques = CreateHelper.createQuestion(li.from);
                    }
                    else if (li.typ == NGLinkType.NAME) 
                        ty = com.pullenti.semantic.SemLinkType.NAMING;
                    link = gr.addLink(ty, li.to.getResObject(), li.from.getResObject(), ques, false, li.getFromPrep());
                    if (li.from.source.resultList != null) {
                        link.isOr = li.from.source.resultListOr;
                        for (int ii = 1; ii < li.from.source.resultList.size(); ii++) {
                            com.pullenti.semantic.SemLink link1 = gr.addLink(ty, link.getSource(), li.from.source.resultList.get(ii), ques, link.isOr, null);
                            link1.preposition = link.preposition;
                        }
                    }
                    java.util.ArrayList<NGItem> list = null;
                    if (li.toAllListItems) {
                        list = s.getListByLastItem(li.to);
                        if (list != null) {
                            boolean ok = true;
                            for (int j = 0; j < (list.size() - 1); j++) {
                                if (list.get(j).getResObject() != null && list.get(j).getResObject().linksFrom.size() > 0) {
                                    ok = false;
                                    break;
                                }
                            }
                            if (ok) {
                                for (int j = 0; j < (list.size() - 1); j++) {
                                    gr.addLink(link.typ, list.get(j).getResObject(), link.getTarget(), link.question, false, link.preposition);
                                }
                            }
                        }
                    }
                    if (k > 0) 
                        this._setLastAltLinks(gr);
                }
                if (li.toVerb != null && li.from.getResObject() != null) {
                    com.pullenti.semantic.SemLink link = null;
                    SentItem vitem = null;
                    for (SentItem iii : items) {
                        if (iii.source == li.toVerb) {
                            vitem = iii;
                            break;
                        }
                    }
                    if (li.typ == NGLinkType.AGENT && vitem != null && vitem.result != null) {
                        com.pullenti.semantic.SemObject verb = vitem.result;
                        if (verb.typ == com.pullenti.semantic.SemObjectType.PARTICIPLE && li.canBeParticiple) 
                            link = gr.addLink(com.pullenti.semantic.SemLinkType.PARTICIPLE, li.from.getResObject(), verb, "какой", false, null);
                        else 
                            link = gr.addLink(com.pullenti.semantic.SemLinkType.AGENT, verb, li.from.getResObject(), null, false, null);
                        if (k > 0) 
                            this._setLastAltLinks(gr);
                    }
                    else if (((li.typ == NGLinkType.PACIENT || li.typ == NGLinkType.ACTANT)) && vitem != null && vitem.resultVerbLast != null) {
                        com.pullenti.semantic.SemObject verb = vitem.resultVerbLast;
                        String ques = null;
                        if (li.typ == NGLinkType.ACTANT) 
                            ques = CreateHelper.createQuestion(li.from);
                        if (verb.typ == com.pullenti.semantic.SemObjectType.PARTICIPLE && li.typ == NGLinkType.PACIENT && li.canBeParticiple) 
                            link = gr.addLink(com.pullenti.semantic.SemLinkType.PARTICIPLE, li.from.getResObject(), verb, "какой", false, null);
                        else 
                            link = gr.addLink((li.typ == NGLinkType.PACIENT ? com.pullenti.semantic.SemLinkType.PACIENT : com.pullenti.semantic.SemLinkType.DETAIL), verb, li.from.getResObject(), ques, false, li.getFromPrep());
                        if (k > 0) 
                            this._setLastAltLinks(gr);
                    }
                    if (link == null) 
                        continue;
                    if (li.from.source.resultList != null) {
                        link.isOr = li.from.source.resultListOr;
                        for (int jj = 1; jj < li.from.source.resultList.size(); jj++) {
                            if (link.typ == com.pullenti.semantic.SemLinkType.PARTICIPLE) 
                                gr.addLink(link.typ, li.from.source.resultList.get(jj), link.getTarget(), link.question, link.isOr, null);
                            else 
                                gr.addLink(link.typ, link.getSource(), li.from.source.resultList.get(jj), link.question, link.isOr, null);
                        }
                    }
                }
            }
        }
    }

    private void createResult(com.pullenti.semantic.SemBlock blk) {
        if (bestVar != null) {
            for (NGSegmentVariant s : bestVar.segs) {
                if (s != null) 
                    s.correctMorph();
            }
            bestVar.createAltLinks();
        }
        java.util.ArrayList<SentItem> allItems = new java.util.ArrayList<SentItem>();
        for (SentItem it : items) {
            if (it.getResGraph() == null) 
                continue;
            if (it.result == null) {
                if (it.source instanceof com.pullenti.ner.core.NounPhraseToken) {
                    com.pullenti.ner.core.NounPhraseToken npt = (com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.NounPhraseToken.class);
                    if (it.plural == 1 && ((short)((it.source.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        it.source.getMorph().removeItems(com.pullenti.morph.MorphNumber.PLURAL);
                    it.result = CreateHelper.createNounGroup(it.getResGraph(), npt);
                    if (npt.multiNouns && it.result.quantity == null) {
                        it.resultList = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
                        it.resultList.add(it.result);
                        if (npt.adjectives.size() > 0 && ((short)((npt.adjectives.get(0).getBeginToken().getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) == (com.pullenti.morph.MorphNumber.SINGULAR.value())) {
                            it.result.morph.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
                            if (it.result.morph.normalFull != null) 
                                it.result.morph.normalCase = it.result.morph.normalFull;
                        }
                        for (int i = 1; i < npt.adjectives.size(); i++) {
                            com.pullenti.semantic.SemObject so = com.pullenti.semantic.SemObject._new3133(it.getResGraph(), it.result.typ);
                            so.tokens.add(npt.noun);
                            com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm();
                            wf.copyFromWordForm(it.result.morph);
                            so.morph = wf;
                            for (com.pullenti.semantic.SemAttribute a : it.result.attrs) {
                                so.attrs.add(a);
                            }
                            so.concept = it.result.concept;
                            so.not = it.result.not;
                            com.pullenti.semantic.SemObject asem = CreateHelper.createNptAdj(it.getResGraph(), npt, npt.adjectives.get(i));
                            if (asem != null) 
                                it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.DETAIL, so, asem, "какой", false, null);
                            it.resultList.add(so);
                            it.getResGraph().objects.add(so);
                        }
                    }
                }
                else if (it.source instanceof com.pullenti.ner.core.VerbPhraseToken) {
                    it.result = CreateHelper.createVerbGroup(it.getResGraph(), (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class));
                    it.resultVerbLast = (com.pullenti.semantic.SemObject)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class)).getLastVerb().tag, com.pullenti.semantic.SemObject.class);
                }
                else if (it.source instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken) 
                    it.result = CreateHelper.createNumber(it.getResGraph(), (com.pullenti.ner.measure.internal.NumbersWithUnitToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.measure.internal.NumbersWithUnitToken.class));
                if (it.result != null && it.quant != null) 
                    it.result.quantity = it.quant;
                if (it.result != null && it.attrs != null) {
                    for (SemAttributeEx a : it.attrs) {
                        it.result.attrs.add(a.attr);
                        it.result.tokens.add(a.token);
                    }
                }
            }
            if (it.result != null) {
                if (it.result.graph != it.getResGraph()) {
                }
                allItems.add(it);
            }
        }
        if (bestVar != null) {
            for (NGSegmentVariant s : bestVar.segs) {
                if (s != null) 
                    this._createLists(s);
            }
        }
        if (bestVar != null) {
            for (NGSegmentVariant s : bestVar.segs) {
                if (s != null) 
                    this._createLinks(s);
            }
        }
        for (int i = 0; i < items.size(); i++) {
            SentItem it = items.get(i);
            if (it.typ != SentItemType.ADVERB || it.getResGraph() == null) 
                continue;
            AdverbToken adv = (AdverbToken)com.pullenti.unisharp.Utils.cast(it.source, AdverbToken.class);
            if (adv.typ != com.pullenti.semantic.SemAttributeType.UNDEFINED) 
                continue;
            SentItem before = null;
            SentItem after = null;
            for (int ii = i - 1; ii >= 0; ii--) {
                SentItem it0 = items.get(ii);
                if (it0.typ == SentItemType.VERB) {
                    before = it0;
                    break;
                }
                else if (it0.typ == SentItemType.ADVERB || it0.typ == SentItemType.NOUN) {
                }
                else 
                    break;
            }
            if (before == null) {
                for (int ii = i - 1; ii >= 0; ii--) {
                    SentItem it0 = items.get(ii);
                    if (it0.typ == SentItemType.VERB || it0.typ == SentItemType.NOUN) {
                        before = it0;
                        break;
                    }
                    else if (it0.typ == SentItemType.ADVERB) {
                    }
                    else 
                        break;
                }
            }
            boolean commaAfter = false;
            for (int ii = i + 1; ii < items.size(); ii++) {
                SentItem it0 = items.get(ii);
                if (it0.typ == SentItemType.VERB || it0.typ == SentItemType.NOUN) {
                    after = it0;
                    break;
                }
                else if (it0.typ == SentItemType.ADVERB) {
                }
                else if (it0.canBeCommaEnd()) {
                    if (before != null && before.typ == SentItemType.VERB) 
                        break;
                    if (((ii + 1) < items.size()) && ((items.get(ii + 1).typ == SentItemType.ADVERB || items.get(ii + 1).typ == SentItemType.VERB))) {
                    }
                    else 
                        commaAfter = true;
                }
                else 
                    break;
            }
            if (before != null && after != null) {
                if (commaAfter) 
                    after = null;
                else if (before.typ == SentItemType.NOUN && after.typ == SentItemType.VERB) 
                    before = null;
                else if (before.typ == SentItemType.VERB && after.typ == SentItemType.NOUN) 
                    after = null;
            }
            it.result = CreateHelper.createAdverb(it.getResGraph(), adv);
            if (it.attrs != null) {
                for (SemAttributeEx a : it.attrs) {
                    it.result.attrs.add(a.attr);
                    it.result.tokens.add(a.token);
                }
            }
            if (after != null || before != null) 
                it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.DETAIL, (after == null ? before.result : after.result), it.result, "как", false, null);
        }
        java.util.ArrayList<SentItem> preds = new java.util.ArrayList<SentItem>();
        SentItem agent = null;
        for (SentItem it : items) {
            if (it.result != null && it.typ == SentItemType.VERB && (it.source instanceof com.pullenti.ner.core.VerbPhraseToken)) {
                if (agent != null) {
                    boolean hasPac = false;
                    for (com.pullenti.semantic.SemLink li : it.getResGraph().links) {
                        if (li.typ == com.pullenti.semantic.SemLinkType.PACIENT && li.getSource() == it.result) {
                            hasPac = true;
                            break;
                        }
                    }
                    if (!hasPac) {
                        NGItem ni0 = NGItem._new3126(agent);
                        NGLink gli0 = NGLink._new3139(ni0, (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class), NGLinkType.PACIENT);
                        if (agent.resultList != null) {
                            gli0.fromIsPlural = true;
                            gli0.calcCoef(false);
                            if (gli0.coef > 0 && gli0.plural == 1) {
                                for (com.pullenti.semantic.SemObject ii : agent.resultList) {
                                    it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.PACIENT, it.result, ii, null, false, null);
                                }
                                coef += (1.0);
                            }
                        }
                        else {
                            gli0.calcCoef(true);
                            if (gli0.coef > 0) {
                                it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.PACIENT, it.result, agent.result, null, false, null);
                                coef += (1.0);
                            }
                        }
                    }
                }
                com.pullenti.semantic.SemLink ali = null;
                for (com.pullenti.semantic.SemLink li : it.getResGraph().links) {
                    if (li.typ == com.pullenti.semantic.SemLinkType.AGENT && li.getSource() == it.result) {
                        ali = li;
                        break;
                    }
                }
                if (ali != null) {
                    agent = this._findItemByRes(ali.getTarget());
                    continue;
                }
                if (agent == null) 
                    continue;
                NGItem ni = NGItem._new3126(agent);
                NGLink gli = NGLink._new3139(ni, (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(it.source, com.pullenti.ner.core.VerbPhraseToken.class), NGLinkType.AGENT);
                if (agent.resultList != null) {
                    gli.fromIsPlural = true;
                    gli.calcCoef(false);
                    if (gli.coef > 0 && gli.plural == 1) {
                        for (com.pullenti.semantic.SemObject ii : agent.resultList) {
                            it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, ii, null, false, null);
                        }
                        coef += (1.0);
                    }
                }
                else {
                    gli.calcCoef(true);
                    if (gli.coef > 0) {
                        it.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, agent.result, null, false, null);
                        coef += (1.0);
                    }
                }
            }
        }
        agent = null;
        for (int i = 0; i < items.size(); i++) {
            SentItem it = items.get(i);
            if (it.result != null && it.typ == SentItemType.DEEPART) {
            }
            else 
                continue;
            com.pullenti.semantic.SemLink link = null;
            for (int j = i - 1; j >= 0; j--) {
                SentItem itt = items.get(j);
                if (itt.typ != SentItemType.NOUN) 
                    continue;
                if (!(itt.source.getMorph().getCase().isNominative())) 
                    continue;
                boolean ispacad = false;
                for (com.pullenti.semantic.SemLink li : itt.getResGraph().links) {
                    if (((li.typ == com.pullenti.semantic.SemLinkType.AGENT || li.typ == com.pullenti.semantic.SemLinkType.PACIENT)) && li.getTarget() == itt.result) 
                        ispacad = true;
                }
                if (!ispacad) 
                    continue;
                if (link == null) 
                    link = itt.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, itt.result, null, false, null);
                else if (link.altLink == null) {
                    link.altLink = itt.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, itt.result, null, false, null);
                    link.altLink.altLink = link;
                    break;
                }
            }
            if (link == null) {
                for (int j = i + 1; j < items.size(); j++) {
                    SentItem itt = items.get(j);
                    if (itt.typ != SentItemType.NOUN) 
                        continue;
                    if (!(itt.source.getMorph().getCase().isNominative())) 
                        continue;
                    boolean ispacad = false;
                    for (com.pullenti.semantic.SemLink li : itt.getResGraph().links) {
                        if (((li.typ == com.pullenti.semantic.SemLinkType.AGENT || li.typ == com.pullenti.semantic.SemLinkType.PACIENT)) && li.getTarget() == itt.result) 
                            ispacad = true;
                    }
                    if (!ispacad) 
                        continue;
                    if (link == null) 
                        link = itt.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, itt.result, null, false, null);
                    else if (link.altLink == null) {
                        link.altLink = itt.getResGraph().addLink(com.pullenti.semantic.SemLinkType.AGENT, it.result, itt.result, null, false, null);
                        link.altLink.altLink = link;
                        break;
                    }
                }
            }
            if (link != null) 
                coef++;
        }
        for (com.pullenti.semantic.SemFragment fr : resBlock.fragments) {
            if (fr.canBeErrorStructure()) 
                coef /= (2.0);
        }
        if (resBlock.fragments.size() > 0 && resBlock.fragments.get(0).getGraph().objects.size() > 0) {
            com.pullenti.semantic.SemObject it = resBlock.fragments.get(0).getGraph().objects.get(0);
            if (lastChar != null && lastChar.isChar('?')) {
                if (com.pullenti.unisharp.Utils.stringsEq(it.morph.normalFull, "КАКОЙ") || com.pullenti.unisharp.Utils.stringsEq(it.morph.normalFull, "СКОЛЬКО")) 
                    it.typ = com.pullenti.semantic.SemObjectType.QUESTION;
            }
        }
    }

    private SentItem _findItemByRes(com.pullenti.semantic.SemObject s) {
        for (SentItem it : items) {
            if (it.result == s) 
                return it;
        }
        return null;
    }

    public java.util.ArrayList<SentItem> items = new java.util.ArrayList<SentItem>();

    public double coef = 0.0;

    public SentenceVariant bestVar;

    public java.util.ArrayList<Subsent> subs = new java.util.ArrayList<Subsent>();

    public com.pullenti.semantic.SemBlock resBlock;

    public NGLinkType lastNounToFirstVerb = NGLinkType.UNDEFINED;

    public NGLinkType notLastNounToFirstVerb = NGLinkType.UNDEFINED;

    public com.pullenti.ner.TextToken lastChar;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (coef > 0) 
            res.append(coef).append(": ");
        for (SentItem it : items) {
            if (it != items.get(0)) 
                res.append("; \r\n");
            res.append(it.toString());
        }
        return res.toString();
    }

    public void addToBlock(com.pullenti.semantic.SemBlock blk, com.pullenti.semantic.SemGraph gr) {
        if (resBlock != null) {
            if (gr == null) 
                blk.addFragments(resBlock);
            else 
                for (com.pullenti.semantic.SemFragment fr : resBlock.fragments) {
                    com.pullenti.unisharp.Utils.addToArrayList(gr.objects, fr.getGraph().objects);
                    com.pullenti.unisharp.Utils.addToArrayList(gr.links, fr.getGraph().links);
                }
        }
        for (SentItem it : items) {
            if (it.subSent != null) 
                it.subSent.addToBlock(blk, (gr != null ? gr : it.resFrag.getGraph()));
        }
    }

    public static java.util.ArrayList<Sentence> parseVariants(com.pullenti.ner.Token t0, com.pullenti.ner.Token t1, int lev, int maxCount, SentItemType regime) {
        if ((t0 == null || t1 == null || t0.getEndChar() > t1.getEndChar()) || lev > 100) 
            return null;
        java.util.ArrayList<Sentence> res = new java.util.ArrayList<Sentence>();
        Sentence sent = new Sentence();
        for (com.pullenti.ner.Token t = t0; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = br.getEndToken();
                    continue;
                }
            }
            java.util.ArrayList<SentItem> _items = SentItem.parseNearItems(t, t1, lev + 1, sent.items);
            if (_items == null || _items.size() == 0) 
                continue;
            if (_items.size() == 1 || ((maxCount > 0 && res.size() > maxCount))) {
                sent.items.add(_items.get(0));
                t = _items.get(0).getEndToken();
                if (regime != SentItemType.UNDEFINED) {
                    SentItem it = _items.get(0);
                    if (it.canBeNoun()) {
                    }
                    else if (it.typ == SentItemType.DELIM) 
                        break;
                    else if (it.typ == SentItemType.VERB) {
                        if (regime == SentItemType.PARTBEFORE) 
                            break;
                    }
                }
                continue;
            }
            java.util.HashMap<Integer, java.util.ArrayList<Sentence>> m_Nexts = new java.util.HashMap<Integer, java.util.ArrayList<Sentence>>();
            for (SentItem it : _items) {
                java.util.ArrayList<Sentence> nexts = null;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Sentence>> wrapnexts3142 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Sentence>>();
                boolean inoutres3143 = com.pullenti.unisharp.Utils.tryGetValue(m_Nexts, it.getEndToken().getEndChar(), wrapnexts3142);
                nexts = wrapnexts3142.value;
                if (!inoutres3143) {
                    nexts = parseVariants(it.getEndToken().getNext(), t1, lev + 1, maxCount, SentItemType.UNDEFINED);
                    m_Nexts.put(it.getEndToken().getEndChar(), nexts);
                }
                if (nexts == null || nexts.size() == 0) {
                    Sentence se = new Sentence();
                    for (SentItem itt : sent.items) {
                        SentItem itt1 = new SentItem(null);
                        itt1.copyFrom(itt);
                        se.items.add(itt1);
                    }
                    SentItem itt0 = new SentItem(null);
                    itt0.copyFrom(it);
                    se.items.add(itt0);
                    res.add(se);
                }
                else 
                    for (Sentence sn : nexts) {
                        Sentence se = new Sentence();
                        for (SentItem itt : sent.items) {
                            SentItem itt1 = new SentItem(null);
                            itt1.copyFrom(itt);
                            se.items.add(itt1);
                        }
                        SentItem itt0 = new SentItem(null);
                        itt0.copyFrom(it);
                        se.items.add(itt0);
                        for (SentItem itt : sn.items) {
                            SentItem itt1 = new SentItem(null);
                            itt1.copyFrom(itt);
                            se.items.add(itt1);
                        }
                        res.add(se);
                    }
            }
            return res;
        }
        if (sent.items.size() == 0) 
            return null;
        res.add(sent);
        return res;
    }

    @Override
    public int compareTo(Sentence other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public void calcCoef(boolean noResult) {
        coef = 0.0;
        for (int i = 0; i < items.size(); i++) {
            SentItem it = items.get(i);
            if (it.typ != SentItemType.ADVERB) 
                continue;
            AdverbToken adv = (AdverbToken)com.pullenti.unisharp.Utils.cast(it.source, AdverbToken.class);
            if (adv.typ == com.pullenti.semantic.SemAttributeType.UNDEFINED) 
                continue;
            SentItem before = null;
            SentItem after = null;
            for (int ii = i - 1; ii >= 0; ii--) {
                SentItem it0 = items.get(ii);
                if (it0.typ == SentItemType.VERB) {
                    before = it0;
                    break;
                }
                else if (it0.typ == SentItemType.ADVERB) {
                    if (((AdverbToken)com.pullenti.unisharp.Utils.cast(it0.source, AdverbToken.class)).typ == com.pullenti.semantic.SemAttributeType.UNDEFINED) {
                        before = it0;
                        break;
                    }
                }
                else if (it0.canBeCommaEnd()) 
                    break;
                else if (it0.typ == SentItemType.FORMULA && ((adv.typ == com.pullenti.semantic.SemAttributeType.GREAT || adv.typ == com.pullenti.semantic.SemAttributeType.LESS))) {
                    before = it0;
                    break;
                }
            }
            boolean commaAfter = false;
            for (int ii = i + 1; ii < items.size(); ii++) {
                SentItem it0 = items.get(ii);
                if (it0.typ == SentItemType.VERB) {
                    after = it0;
                    break;
                }
                else if (it0.typ == SentItemType.ADVERB) {
                    if (((AdverbToken)com.pullenti.unisharp.Utils.cast(it0.source, AdverbToken.class)).typ == com.pullenti.semantic.SemAttributeType.UNDEFINED) {
                        after = it0;
                        break;
                    }
                }
                else if (it0.canBeCommaEnd()) 
                    commaAfter = true;
                else if (it0.typ == SentItemType.FORMULA && ((adv.typ == com.pullenti.semantic.SemAttributeType.GREAT || adv.typ == com.pullenti.semantic.SemAttributeType.LESS))) {
                    before = it0;
                    break;
                }
                else if (it0.typ == SentItemType.NOUN) 
                    commaAfter = true;
                else 
                    break;
            }
            if (before != null && after != null) {
                if (before.typ == SentItemType.FORMULA) 
                    after = null;
                else if (after.typ == SentItemType.FORMULA) 
                    before = null;
                else if (commaAfter) 
                    after = null;
            }
            if (after != null) {
                after.addAttr(adv);
                items.remove(i);
                i--;
                continue;
            }
            if (before != null) {
                before.addAttr(adv);
                items.remove(i);
                i--;
                continue;
            }
        }
        java.util.ArrayList<NGSegment> segs = NGSegment.createSegments(this);
        if (lastNounToFirstVerb != NGLinkType.UNDEFINED || notLastNounToFirstVerb != NGLinkType.UNDEFINED) {
            if (segs.size() != 1 || segs.get(0).items.size() == 0) {
                if (lastNounToFirstVerb != NGLinkType.UNDEFINED) {
                    coef = -1.0;
                    return;
                }
            }
            else {
                NGItem last = segs.get(0).items.get(segs.get(0).items.size() - 1);
                for (int i = last.links.size() - 1; i >= 0; i--) {
                    NGLink li = last.links.get(i);
                    if (lastNounToFirstVerb != NGLinkType.UNDEFINED) {
                        if (li.typ == lastNounToFirstVerb && li.toVerb == segs.get(0).beforeVerb) 
                            li.canBeParticiple = true;
                        else 
                            last.links.remove(i);
                    }
                    else if (notLastNounToFirstVerb != NGLinkType.UNDEFINED) {
                        if (li.typ == notLastNounToFirstVerb && li.toVerb == segs.get(0).beforeVerb) {
                            last.links.remove(i);
                            break;
                        }
                    }
                }
                if (last.links.size() == 0) {
                    coef = -1.0;
                    return;
                }
            }
        }
        for (NGSegment seg : segs) {
            seg.ind = 0;
            seg.createVariants(100);
        }
        java.util.ArrayList<SentenceVariant> svars = new java.util.ArrayList<SentenceVariant>();
        SentenceVariant svar = null;
        for (int kkk = 0; kkk < 1000; kkk++) {
            if (svar == null) 
                svar = new SentenceVariant();
            else 
                svar.segs.clear();
            for (int i = 0; i < segs.size(); i++) {
                NGSegment it = segs.get(i);
                if (it.ind < it.variants.size()) 
                    svar.segs.add(it.variants.get(it.ind));
                else 
                    svar.segs.add(null);
            }
            svar.calcCoef();
            if (svar.coef >= 0) {
                svars.add(svar);
                svar = null;
                if (svars.size() > 100) {
                    this._sortVars(svars);
                    for(int jjj = 10 + svars.size() - 10 - 1, mmm = 10; jjj >= mmm; jjj--) svars.remove(jjj);
                }
            }
            int j;
            for (j = segs.size() - 1; j >= 0; j--) {
                NGSegment it = segs.get(j);
                if ((++it.ind) >= it.variants.size()) 
                    it.ind = 0;
                else 
                    break;
            }
            if (j < 0) 
                break;
        }
        this._sortVars(svars);
        if (svars.size() > 0) {
            bestVar = svars.get(0);
            coef = bestVar.coef;
        }
        else {
        }
        for (SentItem it : items) {
            if (it.subSent != null) 
                coef += it.subSent.coef;
        }
        for (SentItem it : items) {
            if (it.participleCoef > 0) 
                coef *= it.participleCoef;
        }
        subs = Subsent.createSubsents(this);
        if (items.size() == 0) 
            return;
        if (noResult) 
            return;
        resBlock = new com.pullenti.semantic.SemBlock(null);
        for (Subsent sub : subs) {
            sub.resFrag = new com.pullenti.semantic.SemFragment(resBlock);
            resBlock.fragments.add(sub.resFrag);
            sub.resFrag.isOr = sub.isOr;
            for (SentItem it : sub.items) {
                if (sub.resFrag.beginToken == null) 
                    sub.resFrag.beginToken = it.getBeginToken();
                sub.resFrag.endToken = it.getEndToken();
                if (it.getResGraph() != null) {
                }
                it.setResGraph(sub.resFrag.getGraph());
                it.resFrag = sub.resFrag;
            }
        }
        for (Subsent sub : subs) {
            if (sub.resFrag == null || sub.owner == null || sub.owner.resFrag == null) 
                continue;
            if (sub.typ == com.pullenti.semantic.SemFraglinkType.UNDEFINED) 
                continue;
            resBlock.addLink(sub.typ, sub.resFrag, sub.owner.resFrag, sub.question);
        }
        this.createResult(resBlock);
    }

    private void _sortVars(java.util.ArrayList<SentenceVariant> vars) {
        java.util.Collections.sort(vars);
    }

    public boolean truncOborot(boolean isParticiple) {
        if (bestVar == null || bestVar.segs.size() == 0) {
            if (items.size() > 1) {
                for(int jjj = 1 + items.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) items.remove(jjj);
                return true;
            }
            return false;
        }
        boolean ret = false;
        int ind = 0;
        if (bestVar.segs.get(0) == null && !isParticiple) {
            for (ind = 1; ind < items.size(); ind++) {
                if (items.get(ind).canBeCommaEnd()) 
                    break;
            }
        }
        else 
            for (NGSegmentVariant seg : bestVar.segs) {
                if (seg == null) 
                    break;
                for (NGLink li : seg.links) {
                    if (li == null) 
                        continue;
                    ret = true;
                    int ii = items.indexOf(li.from.source);
                    if (ii < 0) 
                        continue;
                    if (li.toVerb != null) {
                        if (li.toVerb == seg.source.beforeVerb) 
                            ind = ii + 1;
                        else if (!isParticiple && seg == bestVar.segs.get(0) && li.toVerb == seg.source.afterVerb) {
                            for (ii = ind; ii < items.size(); ii++) {
                                if (items.get(ii).source == li.toVerb) {
                                    ind = ii + 1;
                                    break;
                                }
                            }
                        }
                        else 
                            break;
                    }
                    else {
                        int jj = items.indexOf(li.to.source);
                        if (jj < 0) 
                            continue;
                        if (jj < ii) 
                            ind = ii + 1;
                        else 
                            break;
                    }
                }
                if (!isParticiple && seg == bestVar.segs.get(0)) {
                }
                else 
                    break;
            }
        if (!ret && ind == 0) {
            for (ind = 1; ind < items.size(); ind++) {
                if (items.get(ind).canBeCommaEnd()) 
                    break;
            }
        }
        if (ind > 0 && (ind < (items.size() - 1))) 
            for(int jjj = ind + items.size() - ind - 1, mmm = ind; jjj >= mmm; jjj--) items.remove(jjj);
        return ret;
    }

    public static Sentence _new3156(NGLinkType _arg1) {
        Sentence res = new Sentence();
        res.lastNounToFirstVerb = _arg1;
        return res;
    }
    public Sentence() {
    }
}
