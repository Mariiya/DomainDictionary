/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class SentItem {

    public SentItem(com.pullenti.ner.MetaToken mt) {
        source = mt;
        if (mt instanceof com.pullenti.ner.core.NounPhraseToken) {
            com.pullenti.ner.core.NounPhraseToken npt = (com.pullenti.ner.core.NounPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.NounPhraseToken.class);
            if (npt.preposition != null) 
                prep = npt.preposition.normal;
            else 
                prep = "";
            typ = SentItemType.NOUN;
            String normal = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
            if (normal != null) 
                drGroups = com.pullenti.semantic.utils.DerivateService.findDerivates(normal, true, null);
        }
        else if ((mt instanceof com.pullenti.ner.ReferentToken) || (mt instanceof com.pullenti.ner.measure.internal.NumbersWithUnitToken)) 
            typ = SentItemType.NOUN;
        else if (mt instanceof AdverbToken) 
            typ = SentItemType.ADVERB;
        else if (mt instanceof com.pullenti.ner.core.ConjunctionToken) 
            typ = SentItemType.CONJ;
        else if (mt instanceof DelimToken) 
            typ = SentItemType.DELIM;
        else if (mt instanceof com.pullenti.ner.core.VerbPhraseToken) {
            com.pullenti.ner.core.VerbPhraseToken vpt = (com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.core.VerbPhraseToken.class);
            String normal = (vpt.getFirstVerb().getVerbMorph() == null ? null : (vpt.getFirstVerb().getVerbMorph().normalFull != null ? vpt.getFirstVerb().getVerbMorph().normalFull : vpt.getFirstVerb().getVerbMorph().normalCase));
            if (normal != null) 
                drGroups = com.pullenti.semantic.utils.DerivateService.findDerivates(normal, true, null);
            if (vpt.getFirstVerb() != vpt.getLastVerb()) {
                normal = (vpt.getLastVerb().getVerbMorph() == null ? vpt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false) : (vpt.getLastVerb().getVerbMorph().normalFull != null ? vpt.getLastVerb().getVerbMorph().normalFull : vpt.getLastVerb().getVerbMorph().normalCase));
                drGroups2 = com.pullenti.semantic.utils.DerivateService.findDerivates(normal, true, null);
            }
            else 
                drGroups2 = drGroups;
            prep = (vpt.preposition == null ? "" : vpt.preposition.normal);
            typ = SentItemType.VERB;
        }
    }

    public void copyFrom(SentItem it) {
        source = it.source;
        typ = it.typ;
        subTyp = it.subTyp;
        prep = it.prep;
        participleCoef = it.participleCoef;
        drGroups = it.drGroups;
        drGroups2 = it.drGroups2;
        partVerbTyp = it.partVerbTyp;
        m_BeginToken = it.m_BeginToken;
        m_EndToken = it.m_EndToken;
        plural = it.plural;
        subSent = it.subSent;
        quant = it.quant;
        attrs = it.attrs;
        canBeQuestion = it.canBeQuestion;
        result = it.result;
        resultList = it.resultList;
        resultListOr = it.resultListOr;
        resultVerbLast = it.resultVerbLast;
        setResGraph(it.getResGraph());
        resFrag = it.resFrag;
    }

    public com.pullenti.ner.MetaToken source;

    public String prep;

    public SentItemType typ = SentItemType.UNDEFINED;

    public SentItemSubtype subTyp = SentItemSubtype.UNDEFINED;

    public Sentence subSent;

    public int plural = -1;

    public java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> drGroups;

    public java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> drGroups2;

    public NGLinkType partVerbTyp = NGLinkType.UNDEFINED;

    public double participleCoef = 1.0;

    public com.pullenti.semantic.SemQuantity quant;

    public java.util.ArrayList<SemAttributeEx> attrs = null;

    public boolean canBeQuestion;

    public com.pullenti.semantic.SemObject result;

    public com.pullenti.semantic.SemObject resultVerbLast;

    public com.pullenti.semantic.SemGraph getResGraph() {
        return m_ResGraph;
    }

    public com.pullenti.semantic.SemGraph setResGraph(com.pullenti.semantic.SemGraph value) {
        if (m_ResGraph == null) 
            m_ResGraph = value;
        else if (value != null && m_ResGraph != value) 
            m_ResGraph = value;
        return value;
    }


    private com.pullenti.semantic.SemGraph m_ResGraph;

    public com.pullenti.semantic.SemFragment resFrag;

    public java.util.ArrayList<com.pullenti.semantic.SemObject> resultList = null;

    public boolean resultListOr;

    public void addAttr(AdverbToken adv) {
        com.pullenti.semantic.SemAttribute sa = com.pullenti.semantic.SemAttribute._new3144(adv.getSpelling(), adv.typ, adv.not);
        if (attrs == null) 
            attrs = new java.util.ArrayList<SemAttributeEx>();
        attrs.add(SemAttributeEx._new3145(adv, sa));
    }

    public com.pullenti.ner.Token getBeginToken() {
        if (m_BeginToken != null) 
            return m_BeginToken;
        if (source != null) 
            return source.getBeginToken();
        return null;
    }

    public com.pullenti.ner.Token setBeginToken(com.pullenti.ner.Token value) {
        m_BeginToken = value;
        return value;
    }


    private com.pullenti.ner.Token m_BeginToken;

    public com.pullenti.ner.Token getEndToken() {
        if (m_EndToken != null) 
            return m_EndToken;
        if (source != null) {
            com.pullenti.ner.Token ret = source.getEndToken();
            if (attrs != null) {
                for (SemAttributeEx a : attrs) {
                    if (a.token.getEndChar() > ret.getEndChar()) 
                        ret = a.token.getEndToken();
                }
            }
            return ret;
        }
        return null;
    }

    public com.pullenti.ner.Token setEndToken(com.pullenti.ner.Token value) {
        m_EndToken = value;
        return value;
    }


    private com.pullenti.ner.Token m_EndToken;

    public boolean canBeNoun() {
        if (((typ == SentItemType.NOUN || typ == SentItemType.DEEPART || typ == SentItemType.PARTAFTER) || typ == SentItemType.PARTBEFORE || typ == SentItemType.SUBSENT) || typ == SentItemType.FORMULA) 
            return true;
        if (source instanceof com.pullenti.ner.core.VerbPhraseToken) {
            if (((com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(source, com.pullenti.ner.core.VerbPhraseToken.class)).getFirstVerb().getVerbMorph() != null && ((com.pullenti.ner.core.VerbPhraseToken)com.pullenti.unisharp.Utils.cast(source, com.pullenti.ner.core.VerbPhraseToken.class)).getFirstVerb().getVerbMorph().containsAttr("инф.", null)) 
                return true;
        }
        return false;
    }


    public boolean canBeCommaEnd() {
        com.pullenti.ner.core.ConjunctionToken cnj = (com.pullenti.ner.core.ConjunctionToken)com.pullenti.unisharp.Utils.cast(source, com.pullenti.ner.core.ConjunctionToken.class);
        if (cnj == null) 
            return false;
        return cnj.typ == com.pullenti.ner.core.ConjunctionType.COMMA || cnj.typ == com.pullenti.ner.core.ConjunctionType.AND || cnj.typ == com.pullenti.ner.core.ConjunctionType.OR;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(prep)) 
            res.append(prep).append(" ");
        res.append(typ.toString()).append("(");
        if (subTyp != SentItemSubtype.UNDEFINED) 
            res.append(subTyp.toString()).append(":");
        if (source != null) {
            res.append(source.toString());
            if (subSent != null) 
                res.append(" <= ").append(subSent.toString());
        }
        else if (subSent != null) 
            res.append(subSent.toString());
        res.append(')');
        return res.toString();
    }

    public static java.util.ArrayList<SentItem> parseNearItems(com.pullenti.ner.Token t, com.pullenti.ner.Token t1, int lev, java.util.ArrayList<SentItem> prev) {
        if (lev > 100) 
            return null;
        if (t == null || t.getBeginChar() > t1.getEndChar()) 
            return null;
        java.util.ArrayList<SentItem> res = new java.util.ArrayList<SentItem>();
        if (t instanceof com.pullenti.ner.ReferentToken) {
            res.add(new SentItem((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)));
            return res;
        }
        DelimToken delim = DelimToken.tryParse(t);
        if (delim != null) {
            res.add(new SentItem(delim));
            return res;
        }
        com.pullenti.ner.core.ConjunctionToken conj = com.pullenti.ner.core.ConjunctionHelper.tryParse(t);
        if (conj != null) {
            res.add(new SentItem(conj));
            return res;
        }
        com.pullenti.ner.core.PrepositionToken _prep = com.pullenti.ner.core.PrepositionHelper.tryParse(t);
        com.pullenti.ner.Token t111 = (_prep == null ? t : _prep.getEndToken().getNext());
        if ((t111 instanceof com.pullenti.ner.NumberToken) && ((t111.getMorph()._getClass().isAdjective() && !t111.getMorph()._getClass().isNoun()))) 
            t111 = null;
        com.pullenti.ner.measure.internal.NumbersWithUnitToken num = (t111 == null ? null : com.pullenti.ner.measure.internal.NumbersWithUnitToken.tryParse(t111, null, com.pullenti.ner.measure.internal.NumberWithUnitParseAttr.NO));
        if (num != null) {
            if (num.units.size() == 0) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(num.getEndToken().getNext(), m_NptAttrs, 0, null);
                if (npt1 == null && num.getEndToken().getNext() != null && num.getEndToken().getNext().isValue("РАЗ", null)) {
                    npt1 = new com.pullenti.ner.core.NounPhraseToken(num.getEndToken().getNext(), num.getEndToken().getNext());
                    npt1.noun = new com.pullenti.ner.MetaToken(num.getEndToken().getNext(), num.getEndToken().getNext(), null);
                }
                if (npt1 != null && _prep != null) {
                    if (npt1.noun.getEndToken().isValue("РАЗ", null)) 
                        npt1.getMorph().removeItems(_prep.nextCase);
                    else if (((com.pullenti.morph.MorphCase.ooBitand(npt1.getMorph().getCase(), _prep.nextCase))).isUndefined()) 
                        npt1 = null;
                    else 
                        npt1.getMorph().removeItems(_prep.nextCase);
                }
                if ((npt1 != null && npt1.getEndToken().isValue("ОНИ", null) && npt1.preposition != null) && com.pullenti.unisharp.Utils.stringsEq(npt1.preposition.normal, "ИЗ")) {
                    npt1.setMorph(new com.pullenti.ner.MorphCollection(num.getEndToken().getMorph()));
                    npt1.preposition = null;
                    String nn = num.toString();
                    SentItem si1 = new SentItem(npt1);
                    if (com.pullenti.unisharp.Utils.stringsEq(nn, "1") && (num.getEndToken() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num.getEndToken(), com.pullenti.ner.NumberToken.class)).getEndToken().isValue("ОДИН", null)) {
                        com.pullenti.semantic.SemAttribute a = com.pullenti.semantic.SemAttribute._new3146(com.pullenti.semantic.SemAttributeType.ONEOF, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num.getEndToken(), com.pullenti.ner.NumberToken.class)).getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                        SemAttributeEx aex = SemAttributeEx._new3145(num, a);
                        si1.attrs = new java.util.ArrayList<SemAttributeEx>();
                        si1.attrs.add(aex);
                    }
                    else 
                        si1.quant = new com.pullenti.semantic.SemQuantity(nn, num.getBeginToken(), num.getEndToken());
                    if (_prep != null) 
                        si1.prep = _prep.normal;
                    res.add(si1);
                    return res;
                }
                if (npt1 != null) {
                    SentItem si1 = _new3148(npt1, new com.pullenti.semantic.SemQuantity(num.toString(), num.getBeginToken(), num.getEndToken()));
                    if (_prep != null) 
                        si1.prep = _prep.normal;
                    if (npt1.getEndToken().isValue("РАЗ", null)) 
                        si1.typ = SentItemType.FORMULA;
                    if (((short)((npt1.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value()) && com.pullenti.unisharp.Utils.stringsNe(si1.quant.spelling, "1")) {
                        boolean ok = false;
                        if (si1.quant.spelling.endsWith("1")) 
                            ok = true;
                        else if (si1.typ == SentItemType.FORMULA) 
                            ok = true;
                        else if (si1.quant.spelling.endsWith("2") && npt1.getMorph().getCase().isGenitive()) 
                            ok = true;
                        else if (si1.quant.spelling.endsWith("3") && npt1.getMorph().getCase().isGenitive()) 
                            ok = true;
                        else if (si1.quant.spelling.endsWith("4") && npt1.getMorph().getCase().isGenitive()) 
                            ok = true;
                        if (ok) {
                            npt1.setMorph(new com.pullenti.ner.MorphCollection(null));
                            npt1.getMorph().setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                        }
                    }
                    res.add(si1);
                    return res;
                }
            }
            num.setBeginToken(t);
            num.setMorph(new com.pullenti.ner.MorphCollection(num.getEndToken().getMorph()));
            SentItem si = new SentItem(num);
            if (_prep != null) 
                si.prep = _prep.normal;
            res.add(si);
            if (com.pullenti.unisharp.Utils.stringsEq(si.prep, "НА")) {
                AdverbToken aa = AdverbToken.tryParse(si.getEndToken().getNext());
                if (aa != null && ((aa.typ == com.pullenti.semantic.SemAttributeType.LESS || aa.typ == com.pullenti.semantic.SemAttributeType.GREAT))) {
                    si.addAttr(aa);
                    si.setEndToken(aa.getEndToken());
                }
            }
            return res;
        }
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        AdverbToken adv = AdverbToken.tryParse(t);
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, m_NptAttrs, 0, null);
        if (npt != null && (npt.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class)).term, "БЫЛИ")) 
            npt = null;
        if (npt != null && adv != null) {
            if (adv.getEndChar() > npt.getEndChar()) 
                npt = null;
            else if (adv.getEndChar() == npt.getEndChar()) {
                res.add(new SentItem(npt));
                res.add(new SentItem(adv));
                return res;
            }
        }
        if (npt != null && npt.adjectives.size() == 0) {
            if (npt.getEndToken().isValue("КОТОРЫЙ", null) && t.getPrevious() != null && t.getPrevious().isCommaAnd()) {
                java.util.ArrayList<SentItem> res1 = parseSubsent(npt, t1, lev + 1, prev);
                if (res1 != null) 
                    return res1;
            }
            if (npt.getEndToken().isValue("СКОЛЬКО", null)) {
                com.pullenti.ner.Token tt1 = npt.getEndToken().getNext();
                if (tt1 != null && tt1.isValue("ВСЕГО", null)) 
                    tt1 = tt1.getNext();
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt1 != null && !npt1.getMorph().getCase().isUndefined() && _prep != null) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(_prep.nextCase, npt1.getMorph().getCase()))).isUndefined()) 
                        npt1 = null;
                    else 
                        npt1.getMorph().removeItems(_prep.nextCase);
                }
                if (npt1 != null) {
                    npt1.setBeginToken(npt.getBeginToken());
                    npt1.preposition = npt.preposition;
                    npt1.adjectives.add(new com.pullenti.ner.MetaToken(npt.getEndToken(), npt.getEndToken(), null));
                    npt = npt1;
                }
            }
            if (npt.getEndToken().getMorph()._getClass().isAdjective()) {
                if (com.pullenti.ner.core.VerbPhraseHelper.tryParse(t, true, false, false) != null) 
                    npt = null;
            }
        }
        com.pullenti.ner.core.VerbPhraseToken vrb = null;
        if (npt != null && npt.adjectives.size() > 0) {
            vrb = com.pullenti.ner.core.VerbPhraseHelper.tryParse(t, true, false, false);
            if (vrb != null && vrb.getFirstVerb().isParticiple()) 
                npt = null;
        }
        else if (adv == null || npt != null) 
            vrb = com.pullenti.ner.core.VerbPhraseHelper.tryParse(t, true, false, false);
        if (npt != null) 
            res.add(new SentItem(npt));
        if (vrb != null && !vrb.getFirstVerb().isParticiple() && !vrb.getFirstVerb().isDeeParticiple()) {
            java.util.ArrayList<com.pullenti.morph.MorphWordForm> vars = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
            for (com.pullenti.morph.MorphBaseInfo wf : vrb.getFirstVerb().getMorph().getItems()) {
                if (wf._getClass().isVerb() && (wf instanceof com.pullenti.morph.MorphWordForm) && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) 
                    vars.add((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class));
            }
            if (vars.size() < 2) 
                res.add(new SentItem(vrb));
            else {
                vrb.getFirstVerb().setVerbMorph(vars.get(0));
                res.add(new SentItem(vrb));
                for (int i = 1; i < vars.size(); i++) {
                    vrb = com.pullenti.ner.core.VerbPhraseHelper.tryParse(t, false, false, false);
                    if (vrb == null) 
                        break;
                    vrb.getFirstVerb().setVerbMorph(vars.get(i));
                    res.add(new SentItem(vrb));
                }
                if (vars.get(0).misc.getMood() == com.pullenti.morph.MorphMood.IMPERATIVE && vars.get(1).misc.getMood() != com.pullenti.morph.MorphMood.IMPERATIVE) {
                    SentItem rr = res.get(0);
                    com.pullenti.unisharp.Utils.putArrayValue(res, 0, res.get(1));
                    com.pullenti.unisharp.Utils.putArrayValue(res, 1, rr);
                }
            }
            return res;
        }
        if (vrb != null) {
            java.util.ArrayList<SentItem> res1 = parseParticiples(vrb, t1, lev + 1);
            if (res1 != null) 
                com.pullenti.unisharp.Utils.addToArrayList(res, res1);
        }
        if (res.size() > 0) 
            return res;
        if (adv != null) {
            if (adv.typ == com.pullenti.semantic.SemAttributeType.OTHER) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(adv.getEndToken().getNext(), m_NptAttrs, 0, null);
                if (npt1 != null && npt1.getEndToken().isValue("ОНИ", null) && npt1.preposition != null) {
                    SentItem si1 = new SentItem(npt1);
                    com.pullenti.semantic.SemAttribute a = com.pullenti.semantic.SemAttribute._new3146(com.pullenti.semantic.SemAttributeType.OTHER, adv.getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    SemAttributeEx aex = SemAttributeEx._new3145(num, a);
                    si1.attrs = new java.util.ArrayList<SemAttributeEx>();
                    si1.attrs.add(aex);
                    if (_prep != null) 
                        si1.prep = _prep.normal;
                    res.add(si1);
                    return res;
                }
                for (int i = prev.size() - 1; i >= 0; i--) {
                    if (prev.get(i).attrs != null) {
                        for (SemAttributeEx a : prev.get(i).attrs) {
                            if (a.attr.typ == com.pullenti.semantic.SemAttributeType.ONEOF) {
                                SentItem si1 = new SentItem(prev.get(i).source);
                                com.pullenti.semantic.SemAttribute aa = com.pullenti.semantic.SemAttribute._new3146(com.pullenti.semantic.SemAttributeType.OTHER, adv.getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                                SemAttributeEx aex = SemAttributeEx._new3145(adv, aa);
                                si1.attrs = new java.util.ArrayList<SemAttributeEx>();
                                si1.attrs.add(aex);
                                if (_prep != null) 
                                    si1.prep = _prep.normal;
                                si1.setBeginToken(adv.getBeginToken());
                                si1.setEndToken(adv.getEndToken());
                                res.add(si1);
                                return res;
                            }
                        }
                    }
                }
            }
            res.add(new SentItem(adv));
            return res;
        }
        if (mc.isAdjective()) {
            npt = com.pullenti.ner.core.NounPhraseToken._new3153(t, t, new com.pullenti.ner.MorphCollection(t.getMorph()));
            npt.noun = new com.pullenti.ner.MetaToken(t, t, null);
            res.add(new SentItem(npt));
            return res;
        }
        return null;
    }

    private static java.util.ArrayList<SentItem> parseSubsent(com.pullenti.ner.core.NounPhraseToken npt, com.pullenti.ner.Token t1, int lev, java.util.ArrayList<SentItem> prev) {
        boolean ok = false;
        if (prev != null) {
            for (int i = prev.size() - 1; i >= 0; i--) {
                SentItem it = prev.get(i);
                if (it.typ == SentItemType.CONJ || it.typ == SentItemType.DELIM) {
                    ok = true;
                    break;
                }
                if (it.typ == SentItemType.VERB) 
                    break;
            }
        }
        if (!ok) 
            return null;
        java.util.ArrayList<Sentence> sents = (java.util.ArrayList<Sentence>)com.pullenti.unisharp.Utils.notnull(Sentence.parseVariants(npt.getEndToken().getNext(), t1, lev + 1, 20, SentItemType.SUBSENT), new java.util.ArrayList<Sentence>());
        java.util.ArrayList<Integer> endpos = new java.util.ArrayList<Integer>();
        java.util.ArrayList<SentItem> res = new java.util.ArrayList<SentItem>();
        for (Sentence s : sents) {
            s.items.add(0, new SentItem(npt));
            s.calcCoef(true);
            s.truncOborot(false);
            int end = s.items.get(s.items.size() - 1).getEndToken().getEndChar();
            if (endpos.contains(end)) 
                continue;
            endpos.add(end);
            s.calcCoef(false);
            SentItem part = new SentItem(npt);
            part.typ = SentItemType.SUBSENT;
            part.subTyp = SentItemSubtype.WICH;
            part.subSent = s;
            part.result = s.items.get(0).result;
            part.setEndToken(s.items.get(s.items.size() - 1).getEndToken());
            res.add(part);
        }
        return res;
    }

    private static java.util.ArrayList<SentItem> parseParticiples(com.pullenti.ner.core.VerbPhraseToken vb, com.pullenti.ner.Token t1, int lev) {
        java.util.ArrayList<Sentence> sents = (java.util.ArrayList<Sentence>)com.pullenti.unisharp.Utils.notnull(Sentence.parseVariants(vb.getEndToken().getNext(), t1, lev + 1, 20, SentItemType.PARTBEFORE), new java.util.ArrayList<Sentence>());
        NGLinkType _typ = NGLinkType.AGENT;
        if (vb.getFirstVerb().getMorph().containsAttr("страд.з.", null)) 
            _typ = NGLinkType.PACIENT;
        else if (vb.getFirstVerb().getMorph().containsAttr("возвр.", null)) 
            _typ = NGLinkType.PACIENT;
        java.util.ArrayList<Integer> endpos = new java.util.ArrayList<Integer>();
        java.util.ArrayList<SentItem> res = new java.util.ArrayList<SentItem>();
        boolean changed = false;
        for (Sentence s : sents) {
            if (vb.getFirstVerb().isDeeParticiple()) 
                break;
            for (int i = 0; i < s.items.size(); i++) {
                SentItem it = s.items.get(i);
                if (!it.canBeNoun() || it.typ == SentItemType.VERB) 
                    continue;
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(it.prep)) 
                    continue;
                if (it.typ == SentItemType.PARTBEFORE || it.typ == SentItemType.PARTAFTER) 
                    continue;
                NGLink li = NGLink._new3155(_typ, NGItem._new3126(it), vb);
                li.calcCoef(true);
                if (li.coef < 0) 
                    continue;
                if (endpos.contains(it.getEndToken().getEndChar())) 
                    continue;
                Sentence ss = Sentence._new3156(_typ);
                ss.items.add(new SentItem(vb));
                for (int j = 0; j <= i; j++) {
                    SentItem si = new SentItem(null);
                    si.copyFrom(s.items.get(j));
                    ss.items.add(si);
                }
                ss.calcCoef(false);
                changed = true;
                if (ss.coef < 0) 
                    continue;
                SentItem part = new SentItem(it.source);
                part.typ = SentItemType.PARTAFTER;
                part.subSent = ss;
                if (vb.preposition != null) 
                    part.prep = vb.preposition.normal;
                part.setBeginToken(vb.getBeginToken());
                part.setEndToken(it.source.getEndToken());
                if ((i + 1) < ss.items.size()) 
                    part.result = ss.items.get(i + 1).result;
                endpos.add(it.getEndToken().getEndChar());
                res.add(part);
            }
        }
        endpos.clear();
        if (changed) 
            sents = (java.util.ArrayList<Sentence>)com.pullenti.unisharp.Utils.notnull(Sentence.parseVariants(vb.getEndToken().getNext(), t1, lev + 1, 20, SentItemType.PARTBEFORE), new java.util.ArrayList<Sentence>());
        for (Sentence s : sents) {
            s.items.add(0, new SentItem(vb));
            s.calcCoef(true);
            s.truncOborot(true);
            int end = s.items.get(s.items.size() - 1).getEndToken().getEndChar();
            endpos.add(end);
            s.notLastNounToFirstVerb = _typ;
            s.calcCoef(false);
            SentItem part = new SentItem(vb);
            part.partVerbTyp = _typ;
            part.typ = (vb.getFirstVerb().isDeeParticiple() ? SentItemType.DEEPART : SentItemType.PARTBEFORE);
            part.subSent = s;
            part.result = s.items.get(0).result;
            part.resultVerbLast = s.items.get(0).resultVerbLast;
            part.setEndToken(s.items.get(s.items.size() - 1).getEndToken());
            res.add(part);
        }
        if (res.size() == 0 && sents.size() == 0) {
            SentItem part = new SentItem(vb);
            part.partVerbTyp = _typ;
            part.typ = (vb.getFirstVerb().isDeeParticiple() ? SentItemType.DEEPART : SentItemType.PARTBEFORE);
            res.add(part);
        }
        return res;
    }

    private static com.pullenti.ner.core.NounPhraseParseAttr m_NptAttrs;

    public static SentItem _new3148(com.pullenti.ner.MetaToken _arg1, com.pullenti.semantic.SemQuantity _arg2) {
        SentItem res = new SentItem(_arg1);
        res.quant = _arg2;
        return res;
    }

    public SentItem() {
    }
    
    static {
        m_NptAttrs = com.pullenti.ner.core.NounPhraseParseAttr.of(((((com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREBRACKETS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS.value())) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS.value())) | (com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.MULTINOUNS.value()));
    }
}
