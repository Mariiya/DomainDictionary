/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class FragToken extends com.pullenti.ner.MetaToken {

    public void _analizeContent(FragToken topDoc, boolean isCitat, com.pullenti.ner.instrument.InstrumentKind rootKind) {
        kind = com.pullenti.ner.instrument.InstrumentKind.CONTENT;
        if (getBeginToken().getPrevious() != null && getBeginToken().getPrevious().isChar((char)0x1E)) 
            setBeginToken(this.getBeginToken().getPrevious());
        ContentAnalyzeWhapper wr = new ContentAnalyzeWhapper();
        wr.analyze(this, topDoc, isCitat, rootKind);
        for (FragToken ch : topDoc.children) {
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.HEAD) {
                for (FragToken chh : ch.children) {
                    if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS && chh.referents != null) {
                        if (topDoc.referents == null) 
                            topDoc.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        for (com.pullenti.ner.Referent r : chh.referents) {
                            if (!topDoc.referents.contains(r)) 
                                topDoc.referents.add(r);
                        }
                    }
                }
            }
        }
    }

    private static FragToken createZapiskaTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        int cou = 0;
        for (com.pullenti.ner.Token t = t0; t != null && (cou < 30); t = t.getNext(),cou++) {
            InstrToken1 li = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
            if (li == null) 
                break;
            if (li.numbers.size() > 0) 
                break;
            boolean ok = false;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getEndToken() == li.getEndToken()) {
                for (String kv : m_ZapiskaKeywords) {
                    if (npt.getEndToken().isValue(kv, null)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (t.isValue("ОТВЕТ", null)) {
                if (t.isNewlineAfter()) 
                    ok = true;
                else if (t.getNext() != null && t.getNext().isValue("НА", null)) 
                    ok = true;
            }
            if (ok) {
                FragToken res = _new1494(t0, li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.HEAD);
                if (li.getBeginToken() != t0) {
                    FragToken hh = _new1494(t0, li.getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.APPROVED);
                    res.children.add(hh);
                }
                res.children.add(_new1516(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true));
                return res;
            }
            t = li.getEndToken();
        }
        return null;
    }

    private static String[] m_ZapiskaKeywords;

    private static FragToken createContractTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        if (t0 == null) 
            return null;
        boolean isContract = false;
        while ((t0 instanceof com.pullenti.ner.TextToken) && t0.getNext() != null) {
            if (t0.isTableControlChar() || !t0.chars.isLetter()) 
                t0 = t0.getNext();
            else 
                break;
        }
        com.pullenti.ner.decree.internal.DecreeToken dt0 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t0, null, false);
        if (dt0 != null && dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
            isContract = ((dt0.value.indexOf("ДОГОВОР") >= 0) || (dt0.value.indexOf("ДОГОВІР") >= 0) || (dt0.value.indexOf("КОНТРАКТ") >= 0)) || (dt0.value.indexOf("СОГЛАШЕНИЕ") >= 0) || (dt0.value.indexOf("УГОДА") >= 0);
        int cou = 0;
        com.pullenti.ner.Token t;
        ParticipantToken par1 = null;
        for (t = t0; t != null; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.ReferentToken) {
                com.pullenti.ner.ReferentToken rtt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                if (rtt.getBeginToken() == rtt.getEndToken()) {
                    com.pullenti.ner.Referent r = t.getReferent();
                    if (r instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                        String str = r.toString();
                        if ((str.indexOf("директор") >= 0) || (str.indexOf("начальник") >= 0)) {
                        }
                        else 
                            t = t.kit.debedToken(t);
                    }
                    else if ((r instanceof com.pullenti.ner.person.PersonReferent) && (rtt.getBeginToken().getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                        String str = rtt.getBeginToken().getReferent().toString();
                        if ((str.indexOf("директор") >= 0) || (str.indexOf("начальник") >= 0)) {
                        }
                        else {
                            t = t.kit.debedToken(t);
                            t = t.kit.debedToken(t);
                        }
                    }
                }
            }
        }
        int newlines = 0;
        int types = 0;
        for (t = t0; t != null && (cou < 300); t = t.getNext(),cou++) {
            if (t.isChar('_')) {
                cou--;
                continue;
            }
            if (t.isNewlineBefore()) {
                newlines++;
                if (newlines > 10) 
                    break;
                while (t.isTableControlChar() && t.getNext() != null) {
                    t = t.getNext();
                }
                com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                    if (((((com.pullenti.unisharp.Utils.stringsEq(dt.value, "ОПРЕДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ПОСТАНОВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(dt.value, "РЕШЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ПРИГОВОР") || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ВИЗНАЧЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ПОСТАНОВА") || com.pullenti.unisharp.Utils.stringsEq(dt.value, "РІШЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ВИРОК") || dt.value.endsWith("ЗАЯВЛЕНИЕ")) || dt.value.endsWith("ЗАЯВА")) 
                        return null;
                    types++;
                }
                if (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) {
                    com.pullenti.ner._org.OrganizationKind ki = ((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner._org.OrganizationReferent.class)).getKind();
                    if (ki == com.pullenti.ner._org.OrganizationKind.JUSTICE) 
                        return null;
                }
            }
            if (t.isValue("ДАЛЕЕ", null)) {
            }
            if (t.isNewlineAfter()) 
                continue;
            par1 = ParticipantToken.tryAttach(t, null, null, isContract);
            if (par1 != null && ((par1.kind == ParticipantToken.Kinds.NAMEDAS || par1.kind == ParticipantToken.Kinds.NAMEDASPARTS))) {
                t = par1.getEndToken().getNext();
                break;
            }
            par1 = null;
        }
        if (par1 == null) 
            return null;
        ParticipantToken par2 = null;
        cou = 0;
        for (; t != null && (cou < 100); t = t.getNext(),cou++) {
            if (par1.kind == ParticipantToken.Kinds.NAMEDASPARTS) 
                break;
            if (t.isChar('_')) {
                cou--;
                continue;
            }
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br2 = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br2 != null) {
                    t = br2.getEndToken();
                    continue;
                }
            }
            if (t.isAnd()) {
            }
            par2 = ParticipantToken.tryAttach(t, null, null, true);
            if (par2 != null) {
                if (par2.kind == ParticipantToken.Kinds.NAMEDAS && com.pullenti.unisharp.Utils.stringsNe(par2.typ, par1.typ)) 
                    break;
                if (par2.kind == ParticipantToken.Kinds.PURE && com.pullenti.unisharp.Utils.stringsNe(par2.typ, par1.typ)) {
                    if (t.getPrevious().isAnd()) 
                        break;
                }
                t = par2.getEndToken();
            }
            par2 = null;
        }
        if (par1 != null && par2 != null && ((par1.typ == null || par2.typ == null))) {
            java.util.HashMap<String, Integer> stat = new java.util.HashMap<String, Integer>();
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                com.pullenti.ner.Token ttt = tt;
                if (tt instanceof com.pullenti.ner.MetaToken) 
                    ttt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken();
                com.pullenti.ner.core.TerminToken tok = ParticipantToken.m_Ontology.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok == null || tok.termin.tag == null) 
                    continue;
                String key = tok.termin.getCanonicText();
                if (com.pullenti.unisharp.Utils.stringsEq(key, par1.typ) || com.pullenti.unisharp.Utils.stringsEq(key, par2.typ) || com.pullenti.unisharp.Utils.stringsEq(key, "СТОРОНА")) 
                    continue;
                if (!stat.containsKey(key)) 
                    stat.put(key, 1);
                else 
                    stat.put(key, stat.get(key) + 1);
            }
            int max = 0;
            String bestTyp = null;
            for (java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
                if (kp.getValue() > max) {
                    max = kp.getValue();
                    bestTyp = kp.getKey();
                }
            }
            if (bestTyp != null) {
                if (par1.typ == null) 
                    par1.typ = bestTyp;
                else if (par2.typ == null) 
                    par2.typ = bestTyp;
            }
        }
        java.util.ArrayList<String> contrTyps = ParticipantToken.getDocTypes(par1.typ, (par2 == null ? null : par2.typ));
        com.pullenti.ner.Token t1 = par1.getBeginToken().getPrevious();
        com.pullenti.ner.Token lastT1 = null;
        for (; t1 != null && t1.getBeginChar() >= t0.getBeginChar(); t1 = t1.getPrevious()) {
            if (t1.isNewlineAfter()) {
                lastT1 = t1;
                if (t1.isChar(',')) 
                    continue;
                if (t1.getNext() == null) 
                    break;
                if (t1.getNext().chars.isLetter() && t1.getNext().chars.isAllLower()) 
                    continue;
                break;
            }
        }
        if (t1 == null) 
            t1 = lastT1;
        if (t1 == null) 
            return null;
        com.pullenti.ner.instrument.InstrumentParticipantReferent p1 = com.pullenti.ner.instrument.InstrumentParticipantReferent._new1542(par1.typ);
        if (par1.parts != null) {
            for (com.pullenti.ner.Referent p : par1.parts) {
                p1.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, p, false, 0);
            }
        }
        com.pullenti.ner.instrument.InstrumentParticipantReferent p2 = null;
        java.util.ArrayList<com.pullenti.ner.instrument.InstrumentParticipantReferent> allParts = new java.util.ArrayList<com.pullenti.ner.instrument.InstrumentParticipantReferent>();
        allParts.add(p1);
        if (par1.kind == ParticipantToken.Kinds.NAMEDASPARTS) {
            p1.setTyp("СТОРОНА 1");
            p1.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, par1.parts.get(0), false, 0);
            for (int ii = 1; ii < par1.parts.size(); ii++) {
                com.pullenti.ner.instrument.InstrumentParticipantReferent pp = com.pullenti.ner.instrument.InstrumentParticipantReferent._new1542(("СТОРОНА " + (ii + 1)));
                pp.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, par1.parts.get(ii), false, 0);
                if (ii == 1) 
                    p2 = pp;
                allParts.add(pp);
            }
            for (com.pullenti.ner.Referent pp : par1.parts) {
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, pp, false, 0);
            }
        }
        FragToken title = _new1494(t0, t0, com.pullenti.ner.instrument.InstrumentKind.HEAD);
        boolean add = false;
        com.pullenti.ner.Token namBeg = null;
        com.pullenti.ner.Token namEnd = null;
        String dttyp = null;
        com.pullenti.ner.decree.internal.DecreeToken dt00 = null;
        com.pullenti.ner.Token namBeg2 = null;
        com.pullenti.ner.Token namEnd2 = null;
        for (t = t0; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                if (t.isNewlineBefore() || ((t.getPrevious() != null && t.getPrevious().isTableControlChar()))) 
                    t = t.kit.debedToken(t);
            }
            boolean newLineBef = t.isNewlineBefore();
            if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) 
                newLineBef = true;
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, dt00, false);
            if (dt != null) {
                dt00 = dt;
                if ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && newLineBef))) || ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && newLineBef))) {
                    if (namBeg != null && namEnd == null) 
                        namEnd = t.getPrevious();
                    if (namBeg2 != null && namEnd2 == null) 
                        namEnd2 = t.getPrevious();
                    if (((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && doc.getTyp() != null && !(doc.getTyp().indexOf("ДОГОВОР") >= 0)) && !(doc.getTyp().indexOf("ДОГОВІР") >= 0) && !isContract) && dt.value != null && (((dt.value.indexOf("ДОГОВОР") >= 0) || (dt.value.indexOf("ДОГОВІР") >= 0)))) {
                        doc.setTyp(null);
                        doc.setNumber(0);
                        doc.setRegNumber(null);
                        isContract = true;
                        namBeg = (namEnd = null);
                        title.children.clear();
                    }
                    _addTitleAttr(doc, title, dt);
                    title.setEndToken((t = dt.getEndToken()));
                    if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                        dttyp = dt.value;
                    add = true;
                    continue;
                }
            }
            dt00 = null;
            if (newLineBef && t != t0) {
                FragToken edss = _createEditions(t);
                if (edss != null) {
                    if (namBeg != null && namEnd == null) 
                        namEnd = t.getPrevious();
                    if (namBeg2 != null && namEnd2 == null) 
                        namEnd2 = t.getPrevious();
                    title.children.add(edss);
                    title.setEndToken(edss.getEndToken());
                    break;
                }
                InstrToken1 it1 = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                if (it1 != null && it1.numbers.size() > 0 && it1.numTyp == NumberTypes.DIGIT) {
                    title.setEndToken(t.getPrevious());
                    if (namBeg != null && namEnd == null) 
                        namEnd = t.getPrevious();
                    if (namBeg2 != null && namEnd2 == null) 
                        namEnd2 = t.getPrevious();
                    break;
                }
                if ((t.isValue("О", "ПРО") || t.isValue("ОБ", null) || t.isValue("НА", null)) || t.isValue("ПО", null)) {
                    if (namBeg == null) {
                        namBeg = t;
                        continue;
                    }
                    else if (namBeg2 == null && namEnd != null) {
                        namBeg2 = t;
                        continue;
                    }
                }
                if (add) 
                    title.setEndToken(t.getPrevious());
                add = false;
                com.pullenti.ner.Referent r = t.getReferent();
                if ((r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner.decree.DecreeReferent)) {
                    if (namBeg != null && namEnd == null) 
                        namEnd = t.getPrevious();
                    if (namBeg2 != null && namEnd2 == null) 
                        namEnd2 = t.getPrevious();
                }
            }
            if ((dttyp != null && namBeg == null && t.chars.isCyrillicLetter()) && (t instanceof com.pullenti.ner.TextToken)) {
                if (t.isValue("МЕЖДУ", "МІЖ")) {
                    com.pullenti.ner.ReferentToken pp = ParticipantToken.tryAttachToExist(t.getNext(), p1, p2);
                    if (pp != null && pp.getEndToken().getNext() != null && pp.getEndToken().getNext().isAnd()) {
                        com.pullenti.ner.ReferentToken pp2 = ParticipantToken.tryAttachToExist(pp.getEndToken().getNext().getNext(), p1, p2);
                        if (pp2 != null) {
                            FragToken fr = _new1494(t, pp2.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.PLACE);
                            if (fr.referents == null) 
                                fr.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                            fr.referents.add(pp.referent);
                            fr.referents.add(pp2.referent);
                            title.children.add(fr);
                            t = title.setEndToken(fr.getEndToken());
                            if (t.getNext() != null) {
                                if (t.getNext().isValue("О", "ПРО") || t.getNext().isValue("ОБ", null)) {
                                    namBeg = t.getNext();
                                    namEnd = null;
                                    namBeg2 = (namEnd2 = null);
                                }
                            }
                            continue;
                        }
                    }
                }
                namBeg = t;
            }
            else if (t.isValue("МЕЖДУ", "МІЖ") || t.isValue("ЗАКЛЮЧИТЬ", "УКЛАСТИ")) {
                if (namBeg != null && namEnd == null) 
                    namEnd = t.getPrevious();
                if (namBeg2 != null && namEnd2 == null) 
                    namEnd2 = t.getPrevious();
            }
            if (((newLineBef && t.getWhitespacesBeforeCount() > 15)) || t.isTableControlChar()) {
                if (namBeg != null && namEnd == null && namBeg != t) 
                    namEnd = t.getPrevious();
                if (namBeg2 != null && namEnd2 == null && namBeg2 != t) 
                    namEnd2 = t.getPrevious();
            }
        }
        if (namBeg != null && namEnd == null && t1 != null) 
            namEnd = t1;
        if (namBeg2 != null && namEnd2 == null && t1 != null) 
            namEnd2 = t1;
        if (namEnd != null && namBeg != null) {
            String val = com.pullenti.ner.core.MiscHelper.getTextValue(namBeg, namEnd, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
            if (val != null && val.length() > 3) {
                FragToken nam = _new1546(namBeg, namEnd, com.pullenti.ner.instrument.InstrumentKind.NAME, val);
                title.children.add(nam);
                title.sortChildren();
                if (namEnd.getEndChar() > title.getEndChar()) 
                    title.setEndToken(namEnd);
                if (dttyp != null && !(val.indexOf(dttyp) >= 0)) 
                    val = (dttyp + " " + val);
                if (namBeg2 != null && namEnd2 != null) {
                    String val2 = com.pullenti.ner.core.MiscHelper.getTextValue(namBeg2, namEnd2, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
                    if (val2 != null && val2.length() > 3) {
                        nam = _new1546(namBeg2, namEnd2, com.pullenti.ner.instrument.InstrumentKind.NAME, val2);
                        title.children.add(nam);
                        title.sortChildren();
                        if (namEnd2.getEndChar() > title.getEndChar()) 
                            title.setEndToken(namEnd2);
                        val = (val + " " + val2);
                    }
                }
                doc.setName(val);
            }
        }
        if (title.children.size() > 0 && title.children.get(0).getBeginChar() > title.getBeginChar()) 
            title.children.add(0, _new1494(title.getBeginToken(), title.children.get(0).getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED));
        if (((com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "ДОГОВОР") || com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "ДОГОВІР"))) && par1.kind != ParticipantToken.Kinds.NAMEDASPARTS) {
            if (title.children.size() > 0 && title.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.TYP) {
                String addi = null;
                for (FragToken ch : title.children) {
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.NAME) {
                        if (ch.getBeginToken().getMorph()._getClass().isPreposition()) {
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ch.getBeginToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt != null) {
                                addi = npt.noun.getSourceText().toUpperCase();
                                java.util.ArrayList<com.pullenti.morph.MorphWordForm> vvv = com.pullenti.morph.MorphologyService.getAllWordforms(addi, null);
                                for (com.pullenti.morph.MorphWordForm fi : vvv) {
                                    if (fi.getCase().isGenitive()) {
                                        addi = fi.normalCase;
                                        if (addi.endsWith("НЬЯ")) 
                                            addi = addi.substring(0, 0 + addi.length() - 2) + "ИЯ";
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ch.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt != null && npt.getEndChar() <= ch.getEndChar()) 
                                addi = npt.noun.getSourceText().toUpperCase();
                        }
                        break;
                    }
                }
                if (addi != null) {
                    if (addi.startsWith("ОКАЗАН")) 
                        addi = "УСЛУГ";
                    else if (addi.startsWith("НАДАН")) 
                        addi = "ПОСЛУГ";
                    doc.setTyp((doc.getTyp() + " " + addi));
                    if (com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), doc.getName())) 
                        doc.setName(null);
                }
                else if (contrTyps.size() == 1) {
                    if (doc.getTyp() == null || (doc.getTyp().length() < contrTyps.get(0).length())) 
                        doc.setTyp(contrTyps.get(0));
                }
                else if (contrTyps.size() > 0 && doc.getTyp() == null) 
                    doc.setTyp(contrTyps.get(0));
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "ДОГОВОР УСЛУГ")) 
            doc.setTyp("ДОГОВОР ОКАЗАНИЯ УСЛУГ");
        if (com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "ДОГОВІР ПОСЛУГ")) 
            doc.setTyp("ДОГОВІР НАДАННЯ ПОСЛУГ");
        if (doc.getTyp() == null && contrTyps.size() > 0) 
            doc.setTyp(contrTyps.get(0));
        com.pullenti.ner.core.AnalyzerData ad = t0.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.instrument.InstrumentAnalyzer.ANALYZER_NAME);
        if (ad == null) 
            return null;
        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, p1, false, 0);
        com.pullenti.ner.ReferentToken rt = par1.attachFirst(p1, title.getEndChar() + 1, (par2 == null ? 0 : par2.getBeginChar() - 1));
        if (rt == null) 
            return null;
        if (par2 == null) {
            if (p1.getSlots().size() < 2) 
                return null;
            if (!isContract) 
                return null;
            com.pullenti.ner.Token tt2 = null;
            for (com.pullenti.ner.Token ttt = rt.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                if (ttt.isComma() || ttt.isAnd()) 
                    continue;
                if (ttt.getMorph()._getClass().isPreposition()) 
                    continue;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0, null);
                if (npt != null) {
                    if (npt.getEndToken().isValue("СТОРОНА", null)) {
                        ttt = npt.getEndToken();
                        continue;
                    }
                }
                tt2 = ttt;
                break;
            }
            if (tt2 != null && par1 != null) {
                java.util.HashMap<String, Integer> stat = new java.util.HashMap<String, Integer>();
                int cou1 = 0;
                for (com.pullenti.ner.Token ttt = tt2; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isValue(par1.typ, null)) {
                        cou1++;
                        continue;
                    }
                    com.pullenti.ner.core.TerminToken tok = ParticipantToken.m_Ontology.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok != null && tok.termin.tag != null && com.pullenti.unisharp.Utils.stringsNe(tok.termin.getCanonicText(), "СТОРОНА")) {
                        if (!stat.containsKey(tok.termin.getCanonicText())) 
                            stat.put(tok.termin.getCanonicText(), 1);
                        else 
                            stat.put(tok.termin.getCanonicText(), stat.get(tok.termin.getCanonicText()) + 1);
                    }
                }
                String typ2 = null;
                if (cou1 > 10) {
                    int minCou = (int)((((double)cou1) * 0.6));
                    int maxCou = (int)((((double)cou1) * 1.4));
                    for (java.util.Map.Entry<String, Integer> kp : stat.entrySet()) {
                        if (kp.getValue() >= minCou && kp.getValue() <= maxCou) {
                            typ2 = kp.getKey();
                            break;
                        }
                    }
                }
                if (typ2 != null) 
                    par2 = ParticipantToken._new1549(tt2, tt2, typ2);
            }
        }
        rt.referent = (p1 = (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(p1), com.pullenti.ner.instrument.InstrumentParticipantReferent.class));
        t0.kit.embedToken(rt);
        if (par2 != null) {
            p2 = com.pullenti.ner.instrument.InstrumentParticipantReferent._new1542(par2.typ);
            if (par2.parts != null) {
                for (com.pullenti.ner.Referent p : par2.parts) {
                    p2.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, p, false, 0);
                }
            }
            p2 = (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(p2), com.pullenti.ner.instrument.InstrumentParticipantReferent.class);
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, p2, false, 0);
            rt = par2.attachFirst(p2, rt.getEndChar() + 1, 0);
            if (rt == null) 
                return title;
            t0.kit.embedToken(rt);
        }
        else if (allParts.size() > 1) {
            for (com.pullenti.ner.instrument.InstrumentParticipantReferent pp : allParts) {
                com.pullenti.ner.instrument.InstrumentParticipantReferent ppp = (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(pp), com.pullenti.ner.instrument.InstrumentParticipantReferent.class);
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, ppp, false, 0);
                if (pp == allParts.get(1)) 
                    p2 = ppp;
            }
        }
        int reqRegim = 0;
        for (t = rt.getNext(); t != null; t = (t == null ? null : t.getNext())) {
            if (t.getBeginChar() >= 712 && (t.getBeginChar() < 740)) {
            }
            if (t.isNewlineBefore()) {
                InstrToken1 ii = InstrToken1.parse(t, true, null, 0, null, false, 0, true, false);
                if (ii != null && ii.titleTyp == InstrToken1.StdTitleType.REQUISITES) {
                    reqRegim = 5;
                    t = ii.getEndToken();
                    continue;
                }
            }
            if (t.isValue("ПРИЛОЖЕНИЕ", null) && t.isNewlineBefore()) {
            }
            if (reqRegim == 5 && t.isChar((char)0x1E)) {
                java.util.ArrayList<com.pullenti.ner.core.TableRowToken> rows = com.pullenti.ner.core.TableHelper.tryParseRows(t, 0, true);
                if (rows != null && rows.size() > 0 && ((rows.get(0).cells.size() == 2 || rows.get(0).cells.size() == 3))) {
                    int i0 = rows.get(0).cells.size() - 2;
                    com.pullenti.ner.ReferentToken rt0 = ParticipantToken.tryAttachToExist(rows.get(0).cells.get(i0).getBeginToken(), p1, p2);
                    com.pullenti.ner.ReferentToken rt1 = ParticipantToken.tryAttachToExist(rows.get(0).cells.get(i0 + 1).getBeginToken(), p1, p2);
                    if (rt0 != null && rt1 != null && rt1.referent != rt0.referent) {
                        for (int ii = 0; ii < rows.size(); ii++) {
                            if (rows.get(ii).cells.size() == rows.get(0).cells.size()) {
                                rt = ParticipantToken.tryAttachRequisites(rows.get(ii).cells.get(i0).getBeginToken(), (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class), (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class), false);
                                if (rt != null && rt.getEndChar() <= rows.get(ii).cells.get(i0).getEndChar()) 
                                    t0.kit.embedToken(rt);
                                rt = ParticipantToken.tryAttachRequisites(rows.get(ii).cells.get(i0 + 1).getBeginToken(), (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class), (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class), false);
                                if (rt != null && rt.getEndChar() <= rows.get(ii).cells.get(i0 + 1).getEndChar()) 
                                    t0.kit.embedToken(rt);
                            }
                        }
                        t = rows.get(rows.size() - 1).getEndToken();
                        reqRegim = 0;
                        continue;
                    }
                }
            }
            rt = ParticipantToken.tryAttachToExist(t, p1, p2);
            if (rt == null && reqRegim > 0) {
                for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                    if (tt.isTableControlChar()) {
                    }
                    else if (tt.isCharOf(".)") || (tt instanceof com.pullenti.ner.NumberToken)) {
                    }
                    else {
                        rt = ParticipantToken.tryAttachToExist(tt, p1, p2);
                        if (rt != null && !t.isTableControlChar()) 
                            rt.setBeginToken(t);
                        break;
                    }
                }
            }
            if (rt == null) {
                reqRegim--;
                continue;
            }
            java.util.ArrayList<com.pullenti.ner.instrument.InstrumentParticipantReferent> ps = new java.util.ArrayList<com.pullenti.ner.instrument.InstrumentParticipantReferent>();
            ps.add((com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class));
            if (reqRegim > 0) {
                com.pullenti.ner.ReferentToken rt1 = ParticipantToken.tryAttachRequisites(rt.getEndToken().getNext(), ps.get(0), (ps.get(0) == p1 ? p2 : p1), false);
                if (rt1 != null) 
                    rt.setEndToken(rt1.getEndToken());
            }
            t0.kit.embedToken(rt);
            t = rt;
            if (reqRegim <= 0) {
                if (t.isNewlineBefore()) {
                }
                else if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
                }
                else 
                    continue;
            }
            else {
            }
            if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isTableControlChar() && !rt.getEndToken().getNext().isChar((char)0x1E)) {
                for (com.pullenti.ner.Token tt = rt.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isTableControlChar()) {
                    }
                    else if (tt.isCharOf(".)") || (tt instanceof com.pullenti.ner.NumberToken)) {
                    }
                    else {
                        com.pullenti.ner.ReferentToken rt1 = ParticipantToken.tryAttachRequisites(tt, (ps.get(0) == p1 ? p2 : p1), ps.get(0), true);
                        if (rt1 != null) {
                            ps.add((com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(rt1.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class));
                            t0.kit.embedToken(rt1);
                            t = rt1;
                        }
                        break;
                    }
                }
            }
            t = t.getNext();
            if (t == null) 
                break;
            while (t.isTableControlChar() && t.getNext() != null) {
                t = t.getNext();
            }
            int cur = 0;
            for (; t != null; t = t.getNext()) {
                if (t.isTableControlChar() && t.isChar((char)0x1F)) {
                    reqRegim = 0;
                    break;
                }
                rt = ParticipantToken.tryAttachRequisites(t, ps.get(cur), (p1 == ps.get(cur) ? p2 : p1), reqRegim <= 0);
                if (rt != null) {
                    reqRegim = 5;
                    t0.kit.embedToken(rt);
                    t = rt;
                }
                else {
                    t = t.getPrevious();
                    break;
                }
                if (ps.size() == 2 && t.getNext().isTableControlChar()) {
                    com.pullenti.ner.Token tt = t.getNext();
                    for (; tt != null; tt = tt.getNext()) {
                        if (tt.isTableControlChar() && tt.isChar((char)0x1F)) 
                            break;
                        if (tt.isTableControlChar()) {
                            cur = 1 - cur;
                            if (com.pullenti.ner.core.TableHelper.isCellEnd(tt) && com.pullenti.ner.core.TableHelper.isRowEnd(tt.getNext())) 
                                tt = tt.getNext();
                            t = tt;
                            continue;
                        }
                        break;
                    }
                    continue;
                }
                if (t.isTableControlChar() && ps.size() == 2) {
                    if (com.pullenti.ner.core.TableHelper.isCellEnd(t) && com.pullenti.ner.core.TableHelper.isRowEnd(t.getNext())) 
                        t = t.getNext();
                    cur = 1 - cur;
                    continue;
                }
                if (!t.isNewlineAfter()) 
                    continue;
                InstrToken1 it1 = InstrToken1.parse(t.getNext(), true, null, 0, null, false, 0, false, false);
                if (it1 != null) {
                    if (it1.allUpper || it1.titleTyp != InstrToken1.StdTitleType.UNDEFINED || it1.numbers.size() > 0) 
                        break;
                }
            }
        }
        return title;
    }

    private static FragToken createProjectTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        if (t0 == null) 
            return null;
        boolean isProject = false;
        boolean isEntered = false;
        boolean isTyp = false;
        if (t0.isTableControlChar() && t0.getNext() != null) 
            t0 = t0.getNext();
        FragToken title = _new1494(t0, t0, com.pullenti.ner.instrument.InstrumentKind.HEAD);
        com.pullenti.ner.Token t;
        for (t = t0; t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                continue;
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) 
                t = t.kit.debedToken(t);
            if ((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ПРОЕКТ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ЗАКОНОПРОЕКТ")))) {
                if ((t.isValue("ПРОЕКТ", null) && t == t0 && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
                    return null;
                isProject = true;
                title.children.add(_new1513(t, t, com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true));
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_TYPE, "ПРОЕКТ", false, 0);
                continue;
            }
            com.pullenti.ner.Token tt = _attachProjectEnter(t);
            if (tt != null) {
                isEntered = true;
                title.children.add(_new1494(t, tt, com.pullenti.ner.instrument.InstrumentKind.APPROVED));
                t = tt;
                continue;
            }
            tt = _attachProjectMisc(t);
            if (tt != null) {
                title.children.add(_new1494(t, tt, (tt.isValue("ЧТЕНИЕ", "ЧИТАННЯ") ? com.pullenti.ner.instrument.InstrumentKind.EDITIONS : com.pullenti.ner.instrument.InstrumentKind.UNDEFINED)));
                t = tt;
                continue;
            }
            if (t.isNewlineBefore() && (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((isProject || isEntered))) 
                t = t.kit.debedToken(t);
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
            if (dt != null) {
                if ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    if (_addTitleAttr(doc, title, dt)) {
                        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                            isTyp = true;
                        t = dt.getEndToken();
                        continue;
                    }
                }
            }
            break;
        }
        if (isProject) {
        }
        else if (isEntered && isTyp) {
        }
        else 
            return null;
        title.setEndToken(t.getPrevious());
        com.pullenti.ner.Token t00 = t;
        com.pullenti.ner.Token t11 = null;
        boolean isBr = com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t00, false, false);
        for (t = t00; t != null; t = t.getNext()) {
            if (t.isNewlineAfter()) {
                if (t.getNext() != null && t.getNext().chars.isAllLower()) 
                    continue;
            }
            if (t.getWhitespacesAfterCount() > 15) {
                t11 = t;
                break;
            }
            else if (t.isNewlineAfter() && t.getNext() != null) {
                if (t.getNext().getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB)) {
                    t11 = t;
                    break;
                }
                if (t.getNext().chars.isCapitalUpper() && t.getNext().getMorph()._getClass().isVerb()) {
                    t11 = t;
                    break;
                }
            }
            if (t.isWhitespaceAfter() && isBr && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, false, null, false)) {
                t11 = t;
                break;
            }
            if (!t.isNewlineBefore()) 
                continue;
            InstrToken1 it = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
            if (it != null && it.numbers.size() > 0 && it.getLastNumber() == 1) {
                t11 = t.getPrevious();
                break;
            }
        }
        if (t11 == null) 
            return null;
        FragToken nam = _new1513(t00, t11, com.pullenti.ner.instrument.InstrumentKind.NAME, true);
        doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, nam.value, false, 0);
        title.children.add(nam);
        title.setEndToken(t11);
        FragToken appr1 = _createApproved(t11.getNext());
        if (appr1 != null) {
            title.children.add(appr1);
            title.setEndToken(appr1.getEndToken());
        }
        return title;
    }

    private static com.pullenti.ner.Token _attachProjectMisc(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        boolean br = false;
        if (t.isChar('(') && t.getNext() != null) {
            br = true;
            t = t.getNext();
        }
        if (t.getMorph()._getClass().isPreposition()) 
            t = t.getNext();
        if ((t instanceof com.pullenti.ner.NumberToken) && t.getNext() != null && t.getNext().isValue("ЧТЕНИЕ", "ЧИТАННЯ")) {
            t = t.getNext();
            if (br && t.getNext() != null && t.getNext().isChar(')')) 
                t = t.getNext();
            return t;
        }
        return null;
    }

    private static com.pullenti.ner.Token _attachProjectEnter(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isValue("ВНОСИТЬ", "ВНОСИТИ") || t.isValue("ВНЕСТИ", null)) {
        }
        else 
            return null;
        int cou = 0;
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) 
                continue;
            if (((t.isValue("ПЕРИОД", "ПЕРІОД") || t.isValue("РАССМОТРЕНИЕ", "РОЗГЛЯД") || t.isValue("ДЕПУТАТ", null)) || t.isValue("ПОЛНОМОЧИЕ", "ПОВНОВАЖЕННЯ") || t.isValue("ПЕРЕДАЧА", null)) || t.isValue("ИСПОЛНЕНИЕ", "ВИКОНАННЯ")) 
                continue;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                if (cou > 0 && t.isNewlineBefore()) 
                    return t.getPrevious();
                cou++;
                continue;
            }
            if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                cou++;
                continue;
            }
            if (t.isNewlineBefore()) 
                return t.getPrevious();
        }
        return null;
    }

    private static void _createJusticeParticipants(FragToken title, com.pullenti.ner.instrument.InstrumentReferent doc) {
        String typ = doc.getTyp();
        boolean ok = ((((com.pullenti.unisharp.Utils.stringsEq(typ, "ПОСТАНОВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(typ, "РЕШЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(typ, "ОПРЕДЕЛЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(typ, "ПРИГОВОР") || (((typ != null ? typ : ""))).endsWith("ЗАЯВЛЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(typ, "ПОСТАНОВА") || com.pullenti.unisharp.Utils.stringsEq(typ, "РІШЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(typ, "ВИЗНАЧЕННЯ") || com.pullenti.unisharp.Utils.stringsEq(typ, "ВИРОК")) || (((typ != null ? typ : ""))).endsWith("ЗАЯВА");
        for (com.pullenti.ner.Slot s : doc.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE) && (s.getValue() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                com.pullenti.ner._org.OrganizationKind ki = ((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner._org.OrganizationReferent.class)).getKind();
                if (ki == com.pullenti.ner._org.OrganizationKind.JUSTICE) 
                    ok = true;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.instrument.InstrumentReferent.ATTR_CASENUMBER)) 
                ok = true;
        }
        com.pullenti.ner.ReferentToken pIst = null;
        com.pullenti.ner.ReferentToken pOtv = null;
        com.pullenti.ner.ReferentToken pZayav = null;
        int cou = 0;
        com.pullenti.ner.Token t;
        StringBuilder tmp = new StringBuilder();
        for (t = title.getBeginToken(); t != null && t.getEndChar() <= title.getEndChar(); t = t.getNext()) {
            if (t.isNewlineBefore()) {
            }
            else if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
            }
            else 
                continue;
            if (t.getNext() != null && ((t.getNext().isChar(':') || t.getNext().isTableControlChar()))) {
                if (t.isValue("ЗАЯВИТЕЛЬ", "ЗАЯВНИК")) {
                    pZayav = _createJustParticipant(t.getNext(), null);
                    if (pZayav != null) {
                        pZayav.setBeginToken(t);
                        ((com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(pZayav.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class)).setTyp("ЗАЯВИТЕЛЬ");
                    }
                }
                else if (t.isValue("ИСТЕЦ", "ПОЗИВАЧ")) {
                    pIst = _createJustParticipant(t.getNext(), null);
                    if (pIst != null) {
                        pIst.setBeginToken(t);
                        ((com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(pIst.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class)).setTyp("ИСТЕЦ");
                    }
                }
                else if (t.isValue("ОТВЕТЧИК", "ВІДПОВІДАЧ") || t.isValue("ДОЛЖНИК", "БОРЖНИК")) {
                    pOtv = _createJustParticipant(t.getNext(), null);
                    if (pOtv != null) {
                        pOtv.setBeginToken(t);
                        ((com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(pOtv.referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class)).setTyp("ОТВЕТЧИК");
                    }
                }
            }
        }
        for (t = title.getEndToken().getNext(); t != null; t = t.getNext()) {
            if ((++cou) > 1000) 
                break;
            if (t.isValue("ЗАЯВЛЕНИЕ", "ЗАЯВА")) {
            }
            else if (t.isValue("ИСК", "ПОЗОВ") && t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition()) {
            }
            else 
                continue;
            if (t.getNext() != null && t.getNext().isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) 
                    t = br.getEndToken();
            }
            if (pIst != null) 
                break;
            pIst = _createJustParticipant(t.getNext(), (t.getNext().getMorph().getLanguage().isUa() ? "ПОЗИВАЧ" : "ИСТЕЦ"));
            if (pIst == null) 
                break;
            t = pIst.getEndToken().getNext();
            if (t != null && t.isChar(',')) 
                t = t.getNext();
            if (t == null) 
                break;
            if (pOtv != null) 
                break;
            if (t.isValue("О", "ПРО") && t.getNext() != null && t.getNext().isValue("ПРИВЛЕЧЕНИЕ", "ЗАЛУЧЕННЯ")) {
                if (t.getNext().getMorph().getLanguage().isUa()) 
                    tmp.append("ПРО ПРИТЯГНЕННЯ");
                else 
                    tmp.append("О ПРИВЛЕЧЕНИИ");
                t = t.getNext().getNext();
                pOtv = _createJustParticipant(t, (t.getNext().getMorph().getLanguage().isUa() ? "ВІДПОВІДАЧ" : "ОТВЕТЧИК"));
            }
            else if (t.isValue("О", "ПРО") && t.getNext() != null && t.getNext().isValue("ПРИЗНАНИЕ", "ВИЗНАННЯ")) {
                if (t.getNext().getMorph().getLanguage().isUa()) 
                    tmp.append("ПРО ВИЗНАННЯ");
                else 
                    tmp.append("О ПРИЗНАНИИ");
                t = t.getNext().getNext();
                pOtv = _createJustParticipant(t, (t.getNext().getMorph().getLanguage().isUa() ? "ВІДПОВІДАЧ" : "ОТВЕТЧИК"));
            }
            else if (t.isValue("О", "ПРО") && t.getNext() != null && t.getNext().isValue("ВЗЫСКАНИЕ", "СТЯГНЕННЯ")) {
                if (t.getNext().getMorph().getLanguage().isUa()) 
                    tmp.append("ПРО СТЯГНЕННЯ");
                else 
                    tmp.append("О ВЗЫСКАНИИ");
                t = t.getNext().getNext();
                if (t != null && t.getMorph()._getClass().isPreposition()) 
                    t = t.getNext();
                pOtv = _createJustParticipant(t, (t.getNext().getMorph().getLanguage().isUa() ? "ВІДПОВІДАЧ" : "ОТВЕТЧИК"));
            }
            else {
                if (t == null || !t.isValue("К", "ПРО")) 
                    break;
                pOtv = _createJustParticipant(t.getNext(), (t.getNext().getMorph().getLanguage().isUa() ? "ВІДПОВІДАЧ" : "ОТВЕТЧИК"));
            }
            if (pOtv != null) 
                t = pOtv.getEndToken().getNext();
            break;
        }
        if (((pIst == null && pZayav == null)) || ((pOtv == null && tmp.length() == 0))) 
            return;
        com.pullenti.ner.core.AnalyzerData ad = title.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.instrument.InstrumentAnalyzer.ANALYZER_NAME);
        if (pZayav != null) {
            pZayav.referent = ad.registerReferent(pZayav.referent);
            pZayav.kit.embedToken(pZayav);
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, pZayav.referent, false, 0);
        }
        if (pIst != null) {
            pIst.referent = ad.registerReferent(pIst.referent);
            pIst.kit.embedToken(pIst);
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, pIst.referent, false, 0);
        }
        if (pOtv != null) {
            pOtv.referent = ad.registerReferent(pOtv.referent);
            pOtv.kit.embedToken(pOtv);
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, pOtv.referent, false, 0);
        }
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        if (t == null) 
            return;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null && npt.getEndToken().isValue("ЛИЦО", "ОСОБА")) {
            t = npt.getEndToken().getNext();
            if (t != null && t.isChar(':')) 
                t = t.getNext();
            for (; t != null; t = t.getNext()) {
                if (t.isChar(',')) 
                    continue;
                com.pullenti.ner.ReferentToken tret = _createJustParticipant(t, (t.getMorph().getLanguage().isUa() ? "ТРЕТЯ ОСОБА" : "ТРЕТЬЕ ЛИЦО"));
                if (tret == null) 
                    break;
                tret.referent = ad.registerReferent(tret.referent);
                tret.kit.embedToken(tret);
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, tret.referent, false, 0);
                t = tret;
            }
        }
        com.pullenti.ner.Token tt00 = t;
        while (t != null) {
            com.pullenti.ner.Token t0 = t;
            if (!t.isValue("О", "ПРО") && !t.isValue("ОБ", null)) {
                if (tmp.length() == 0) {
                    if (t != tt00) 
                        break;
                    int cou2 = 0;
                    boolean hasIsk = true;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null && (cou2 < 140); tt = tt.getNext(),cou2++) {
                        if (tt.isValue("ЗАЯВЛЕНИЕ", "ЗАЯВА") || tt.isValue("ИСК", "ПОЗОВ")) {
                            cou2 = 0;
                            hasIsk = true;
                        }
                        if ((hasIsk && ((tt.isValue("О", "ПРО") || tt.isValue("ОБ", null))) && tt.getNext().getMorphClassInDictionary().isNoun()) && tt.getNext().getMorph().getCase().isPrepositional()) {
                            tmp.append(com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt.getNext(), com.pullenti.ner.core.GetTextAttr.NO));
                            t0 = tt;
                            t = tt.getNext().getNext();
                            break;
                        }
                    }
                    if (tmp.length() == 0 || t == null) 
                        break;
                }
            }
            java.util.ArrayList<com.pullenti.ner.Referent> arefs = new java.util.ArrayList<com.pullenti.ner.Referent>();
            com.pullenti.ner.Token t1 = null;
            for (; t != null; t = t.getNext()) {
                if (t.isNewlineBefore() && t != t0) {
                    if (t.getWhitespacesBeforeCount() > 15) 
                        break;
                }
                if (t.isValue("ПРИ", "ЗА") && t.getNext() != null && t.getNext().isValue("УЧАСТИЕ", "УЧАСТЬ")) 
                    break;
                if (t.isValue("БЕЗ", null) && t.getNext() != null && t.getNext().isValue("ВЫЗОВ", null)) 
                    break;
                com.pullenti.ner.Referent r = t.getReferent();
                if (r != null) {
                    if (r instanceof com.pullenti.ner.money.MoneyReferent) {
                        arefs.add(r);
                        if (t.getPrevious() != null && t.getPrevious().isValue("СУММА", "СУМА")) {
                        }
                        else 
                            tmp.append(" СУММЫ");
                        t1 = t;
                        continue;
                    }
                    if ((r instanceof com.pullenti.ner.decree.DecreePartReferent) || (r instanceof com.pullenti.ner.decree.DecreeReferent)) {
                        arefs.add(r);
                        if (t.getPrevious() != null && t.getPrevious().isValue("ПО", null)) 
                            tmp.setLength(tmp.length() - 3);
                        t1 = t;
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                            if (tt.isCommaAnd()) 
                                continue;
                            r = tt.getReferent();
                            if ((r instanceof com.pullenti.ner.decree.DecreePartReferent) || (r instanceof com.pullenti.ner.decree.DecreeReferent)) {
                                arefs.add(r);
                                t1 = (t = tt);
                                continue;
                            }
                            break;
                        }
                        break;
                    }
                    if (r instanceof com.pullenti.ner.person.PersonReferent) 
                        continue;
                    break;
                }
                if (t.isCharOf(",.") || t.isHiphen()) 
                    break;
                if (t instanceof com.pullenti.ner.TextToken) {
                    String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    if (com.pullenti.unisharp.Utils.stringsEq(term, "ИП")) 
                        continue;
                }
                if (t.isAnd()) {
                    if (t.getNext() == null) 
                        break;
                    if (t.getNext().isValue("О", "ПРО") || t.getNext().isValue("ОБ", null)) {
                        t = t.getNext();
                        break;
                    }
                }
                if (t.isNewlineAfter()) {
                    if (t.getNext() == null) {
                    }
                    else if (t.getNext().chars.isAllLower()) {
                    }
                    else 
                        break;
                }
                if (t.isWhitespaceBefore() && tmp.length() > 0) 
                    tmp.append(' ');
                tmp.append(com.pullenti.ner.core.MiscHelper.getTextValue(t, t, com.pullenti.ner.core.GetTextAttr.NO));
                t1 = t;
            }
            if (tmp.length() > 10 && t1 != null) {
                com.pullenti.ner.instrument.InstrumentArtefactReferent art = com.pullenti.ner.instrument.InstrumentArtefactReferent._new1556("предмет");
                String str = tmp.toString();
                str = str.replace("В РАЗМЕРЕ СУММЫ", "СУММЫ").trim();
                if (str.endsWith("В РАЗМЕРЕ")) 
                    str = str.substring(0, 0 + str.length() - 9) + "СУММЫ";
                if (str.endsWith("В СУММЕ")) 
                    str = str.substring(0, 0 + str.length() - 7) + "СУММЫ";
                art.setValue(str);
                for (com.pullenti.ner.Referent a : arefs) {
                    art.addSlot(com.pullenti.ner.instrument.InstrumentArtefactReferent.ATTR_REF, a, false, 0);
                }
                com.pullenti.ner.ReferentToken rta = new com.pullenti.ner.ReferentToken(art, t0, t1, null);
                rta.referent = ad.registerReferent(rta.referent);
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_ARTEFACT, rta.referent, false, 0);
                rta.kit.embedToken(rta);
                tmp.setLength(0);
            }
            else 
                break;
        }
        for (t = (pOtv == null ? t : pOtv.getNext()); t != null; t = t.getNext()) {
            com.pullenti.ner.ReferentToken rt = null;
            boolean checkDel = false;
            if (t.isValue("ИСТЕЦ", "ПОЗИВАЧ") && pIst != null) {
                rt = new com.pullenti.ner.ReferentToken(pIst.referent, t, t, null);
                checkDel = true;
            }
            else if (t.isValue("ЗАЯВИТЕЛЬ", "ЗАЯВНИК") && pZayav != null) {
                rt = new com.pullenti.ner.ReferentToken(pZayav.referent, t, t, null);
                checkDel = true;
            }
            else if (((t.isValue("ОТВЕТЧИК", "ВІДПОВІДАЧ") || t.isValue("ДОЛЖНИК", "БОРЖНИК"))) && pOtv != null) {
                rt = new com.pullenti.ner.ReferentToken(pOtv.referent, t, t, null);
                checkDel = true;
            }
            else {
                com.pullenti.ner.Referent r = t.getReferent();
                if (!(r instanceof com.pullenti.ner._org.OrganizationReferent) && !(r instanceof com.pullenti.ner.person.PersonReferent)) 
                    continue;
                if (pIst != null && pIst.referent.findSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, true) != null) 
                    rt = new com.pullenti.ner.ReferentToken(pIst.referent, t, t, null);
                else if (pZayav != null && pZayav.referent.findSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, true) != null) 
                    rt = new com.pullenti.ner.ReferentToken(pZayav.referent, t, t, null);
                else if (pOtv != null && pOtv.referent.findSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, true) != null) 
                    rt = new com.pullenti.ner.ReferentToken(pOtv.referent, t, t, null);
            }
            if (rt == null) 
                continue;
            if (checkDel && t.getPrevious() != null && t.getPrevious().isValue("ОТ", null)) {
                com.pullenti.ner.Token tt = t.getPrevious();
                if (tt.getPrevious() != null && tt.getPrevious().isHiphen()) 
                    tt = tt.getPrevious();
                if (tt.isWhitespaceBefore()) {
                    com.pullenti.ner.Token tt1 = t.getNext();
                    if (tt1 != null && ((tt1.isHiphen() || tt1.isChar(':')))) 
                        tt1 = tt1.getNext();
                    if (tt1.getReferent() instanceof com.pullenti.ner.person.PersonReferent) {
                        rt.setBeginToken(tt);
                        rt.setEndToken(tt1);
                        rt.referent.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_DELEGATE, (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(tt1.getReferent(), com.pullenti.ner.person.PersonReferent.class), false, 0);
                    }
                }
            }
            if (rt != null && rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar('(')) {
                com.pullenti.ner.Token tt = rt.getEndToken().getNext().getNext();
                if (tt != null && tt.getNext() != null && tt.getNext().isChar(')')) {
                    if (tt.isValue("ИСТЕЦ", "ПОЗИВАЧ") && pIst != null && rt.referent == pIst.referent) 
                        rt.setEndToken(tt.getNext());
                    else if (tt.isValue("ЗАЯВИТЕЛЬ", "ЗАЯВНИК") && pZayav != null && rt.referent == pZayav.referent) 
                        rt.setEndToken(tt.getNext());
                    else if (((tt.isValue("ОТВЕТЧИК", "ВІДПОВІДАЧ") || tt.isValue("ДОЛЖНИК", "БОРЖНИК"))) && pOtv != null && rt.referent == pOtv.referent) 
                        rt.setEndToken(tt.getNext());
                    else if ((tt.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (tt.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                        if (pIst != null && rt.referent == pIst.referent) {
                            if (pIst.referent.findSlot(null, tt.getReferent(), true) != null) 
                                rt.setEndToken(tt.getNext());
                            else if (pOtv != null && pOtv.referent.findSlot(null, tt.getReferent(), true) == null) {
                                rt.setEndToken(tt.getNext());
                                pIst.referent.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, tt.getReferent(), false, 0);
                            }
                        }
                        else if (pOtv != null && rt.referent == pOtv.referent) {
                            if (pOtv.referent.findSlot(null, tt.getReferent(), true) != null) 
                                rt.setEndToken(tt.getNext());
                            else if (pIst != null && pIst.referent.findSlot(null, tt.getReferent(), true) == null) {
                                rt.setEndToken(tt.getNext());
                                pOtv.referent.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, tt.getReferent(), false, 0);
                            }
                        }
                    }
                }
            }
            t.kit.embedToken(rt);
            t = rt;
        }
    }

    private static com.pullenti.ner.ReferentToken _createJustParticipant(com.pullenti.ner.Token t, String typ) {
        if (t == null) 
            return null;
        com.pullenti.ner.Referent r0 = null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        boolean ok = false;
        boolean br = false;
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (; t != null; t = t.getNext()) {
            if (t.isNewlineBefore() && t != t0) {
                if (t.getWhitespacesBeforeCount() > 15) 
                    break;
            }
            if (t.isHiphen() || t.isCharOf(":,") || t.isTableControlChar()) 
                continue;
            if (!br) {
                if (t.isValue("К", null) || t.isValue("О", "ПРО")) 
                    break;
            }
            if (t.isChar('(')) {
                if (br) 
                    break;
                br = true;
                continue;
            }
            if (t.isChar(')') && br) {
                br = false;
                t1 = t;
                break;
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent)) {
                if (r0 == null) {
                    refs.add(r);
                    r0 = r;
                    t1 = t;
                    ok = true;
                    continue;
                }
                break;
            }
            if (r instanceof com.pullenti.ner.uri.UriReferent) {
                com.pullenti.ner.uri.UriReferent ur = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.uri.UriReferent.class);
                if (com.pullenti.unisharp.Utils.stringsEq(ur.getScheme(), "ИНН") || com.pullenti.unisharp.Utils.stringsEq(ur.getScheme(), "ИИН") || com.pullenti.unisharp.Utils.stringsEq(ur.getScheme(), "ОГРН")) 
                    ok = true;
                refs.add(r);
                t1 = t;
                continue;
            }
            if (!br) {
                if ((r instanceof com.pullenti.ner.decree.DecreeReferent) || (r instanceof com.pullenti.ner.decree.DecreePartReferent)) 
                    break;
            }
            if (r != null || br) {
                if ((r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) 
                    refs.add(r);
                t1 = t;
                continue;
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken brr = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (brr != null) {
                    ok = true;
                    t1 = (t = brr.getEndToken());
                    continue;
                }
            }
            if (t.getPrevious().isComma() && !br) 
                break;
            if (t.getPrevious().getMorph()._getClass().isPreposition() && t.isValue("УЧАСТИЕ", "УЧАСТЬ")) 
                break;
            if ((t.getPrevious() instanceof com.pullenti.ner.NumberToken) && t.isValue("ЛИЦО", "ОСОБА")) 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if ((npt.noun.isValue("УЧРЕЖДЕНИЕ", "УСТАНОВА") || npt.noun.isValue("ПРЕДПРИЯТИЕ", "ПІДПРИЄМСТВО") || npt.noun.isValue("ОРГАНИЗАЦИЯ", "ОРГАНІЗАЦІЯ")) || npt.noun.isValue("КОМПЛЕКС", null)) {
                    t1 = (t = npt.getEndToken());
                    ok = true;
                    continue;
                }
            }
            com.pullenti.ner._org.internal.OrgItemTypeToken ty = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, true);
            if (ty != null) {
                t1 = (t = ty.getEndToken());
                ok = true;
                continue;
            }
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isCyrillicLetter() && t.chars.isAllLower()) {
                if (t.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB) || t.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.ADVERB)) 
                    break;
            }
            if (t.isNewlineBefore() && typ == null) 
                break;
            else if (!t.getMorph()._getClass().isPreposition() && !t.getMorph()._getClass().isConjunction()) 
                t1 = t;
            else if (t.isNewlineBefore()) 
                break;
        }
        if (!ok) 
            return null;
        com.pullenti.ner.instrument.InstrumentParticipantReferent pat = com.pullenti.ner.instrument.InstrumentParticipantReferent._new1542(typ);
        for (com.pullenti.ner.Referent r : refs) {
            pat.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, false, 0);
        }
        return new com.pullenti.ner.ReferentToken(pat, t0, t1, null);
    }

    private void _createJusticeResolution() {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.instrument.InstrumentAnalyzer.ANALYZER_NAME);
        if (ad == null) 
            return;
        java.util.ArrayList<FragToken> res = this._findResolution();
        if (res == null) 
            return;
        for (FragToken r : res) {
            for (com.pullenti.ner.Token t = r.getBeginToken(); t != null && t.getEndChar() <= r.getEndChar(); t = t.getNext()) {
                if (t == r.getBeginToken()) {
                }
                else if (t.getPrevious() != null && t.getPrevious().isChar('.') && t.isWhitespaceBefore()) {
                }
                else if (!t.isValue("ПРИЗНАТЬ", "ВИЗНАТИ")) 
                    continue;
                if (t.getMorph()._getClass().isPreposition() && t.getNext() != null) 
                    t = t.getNext();
                java.util.ArrayList<com.pullenti.ner.ReferentToken> arts = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                com.pullenti.ner.Token tt = null;
                com.pullenti.ner.Token te = null;
                if (t.isValue("ВЗЫСКАТЬ", "СТЯГНУТИ")) {
                    boolean gosposh = false;
                    com.pullenti.ner.money.MoneyReferent sum = null;
                    te = null;
                    for (tt = t.getNext(); tt != null && tt.getEndChar() <= r.getEndChar(); tt = tt.getNext()) {
                        if (tt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if (tt.isChar('.')) 
                            break;
                        if (tt.isValue("ТОМ", "ТОМУ") && tt.getNext() != null && tt.getNext().isValue("ЧИСЛО", null)) 
                            break;
                        if (tt.isValue("ГОСПОШЛИНА", "ДЕРЖМИТО")) 
                            gosposh = true;
                        else if (tt.isValue("ФЕДЕРАЛЬНЫЙ", "ФЕДЕРАЛЬНИЙ") && tt.getNext() != null && tt.getNext().isValue("БЮДЖЕТ", null)) 
                            gosposh = true;
                        if (tt.getReferent() instanceof com.pullenti.ner.money.MoneyReferent) {
                            te = tt;
                            sum = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.money.MoneyReferent.class);
                        }
                    }
                    if (sum != null) {
                        com.pullenti.ner.instrument.InstrumentArtefactReferent art = com.pullenti.ner.instrument.InstrumentArtefactReferent._new1556("РЕЗОЛЮЦИЯ");
                        if (gosposh) 
                            art.setValue("ВЗЫСКАТЬ ГОСПОШЛИНУ");
                        else 
                            art.setValue("ВЗЫСКАТЬ СУММУ");
                        art.addSlot(com.pullenti.ner.instrument.InstrumentArtefactReferent.ATTR_REF, sum, false, 0);
                        arts.add(new com.pullenti.ner.ReferentToken(art, t, te, null));
                    }
                }
                if ((t.isValue("ЗАЯВЛЕНИЕ", "ЗАЯВА") || t.isValue("ИСК", "ПОЗОВ") || t.isValue("ТРЕБОВАНИЕ", "ВИМОГА")) || t.isValue("ЗАЯВЛЕННЫЙ", "ЗАЯВЛЕНИЙ") || t.isValue("УДОВЛЕТВОРЕНИЕ", "ЗАДОВОЛЕННЯ")) {
                    for (tt = t.getNext(); tt != null && tt.getEndChar() <= r.getEndChar(); tt = tt.getNext()) {
                        if (tt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if (tt.isChar('.')) {
                            if (tt.isWhitespaceAfter()) 
                                break;
                        }
                        if (tt.isValue("УДОВЛЕТВОРИТЬ", "ЗАДОВОЛЬНИТИ")) {
                            String val = "УДОВЛЕТВОРИТЬ";
                            te = tt;
                            if (tt.getNext() != null && tt.getNext().isValue("ПОЛНОСТЬЮ", "ПОВНІСТЮ")) {
                                val += " ПОЛНОСТЬЮ";
                                te = tt.getNext();
                            }
                            else if (tt.getPrevious() != null && tt.getPrevious().isValue("ПОЛНОСТЬЮ", "ПОВНІСТЮ")) 
                                val += " ПОЛНОСТЬЮ";
                            com.pullenti.ner.instrument.InstrumentArtefactReferent art = com.pullenti.ner.instrument.InstrumentArtefactReferent._new1556("РЕЗОЛЮЦИЯ");
                            art.setValue(val);
                            arts.add(new com.pullenti.ner.ReferentToken(art, t, te, null));
                            break;
                        }
                        if (tt.isValue("ОТКАЗАТЬ", "ВІДМОВИТИ")) {
                            com.pullenti.ner.instrument.InstrumentArtefactReferent art = com.pullenti.ner.instrument.InstrumentArtefactReferent._new1556("РЕЗОЛЮЦИЯ");
                            art.setValue("ОТКАЗАТЬ");
                            arts.add(new com.pullenti.ner.ReferentToken(art, t, (te = tt), null));
                            break;
                        }
                    }
                }
                if (t.isValue("ПРИЗНАТЬ", "ВИЗНАТИ")) {
                    int zak = -1;
                    int otm = -1;
                    for (tt = t.getNext(); tt != null && tt.getEndChar() <= r.getEndChar(); tt = tt.getNext()) {
                        if (tt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if (tt.isChar('.')) 
                            break;
                        if (tt.isValue("НЕЗАКОННЫЙ", "НЕЗАКОННИЙ")) {
                            zak = 0;
                            te = tt;
                        }
                        else if (tt.isValue("ЗАКОННЫЙ", "ЗАКОННИЙ")) {
                            zak = 1;
                            te = tt;
                        }
                        else if (tt.isValue("ОТМЕНИТЬ", "СКАСУВАТИ")) {
                            otm = 1;
                            te = tt;
                        }
                    }
                    if (zak >= 0) {
                        String val = ("ПРИЗНАТЬ " + (zak > 0 ? "ЗАКОННЫМ" : "НЕЗАКОННЫМ"));
                        if (otm > 0) 
                            val += " И ОТМЕНИТЬ";
                        com.pullenti.ner.instrument.InstrumentArtefactReferent art = com.pullenti.ner.instrument.InstrumentArtefactReferent._new1556("РЕЗОЛЮЦИЯ");
                        art.setValue(val);
                        arts.add(new com.pullenti.ner.ReferentToken(art, t, te, null));
                    }
                    else 
                        continue;
                }
                for (com.pullenti.ner.ReferentToken rt : arts) {
                    rt.referent = ad.registerReferent(rt.referent);
                    m_Doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_ARTEFACT, rt.referent, false, 0);
                    if (r.getBeginToken() == rt.getBeginToken()) 
                        r.setBeginToken(rt);
                    if (r.getEndToken() == rt.getEndToken()) 
                        r.setEndToken(rt);
                    kit.embedToken(rt);
                    t = rt;
                }
            }
        }
    }

    private java.util.ArrayList<FragToken> _findResolution() {
        if (kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX) 
            return null;
        boolean dir = false;
        java.util.ArrayList<FragToken> res = null;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE && ((i + 1) < children.size())) {
                Object v = children.get(i).value;
                if (v == null) 
                    continue;
                String s = v.toString();
                if ((((com.pullenti.unisharp.Utils.stringsEq(s, "РЕШЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(s, "ОПРЕДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(s, "ПОСТАНОВЛЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(s, "ПРИГОВОР") || com.pullenti.unisharp.Utils.stringsEq(s, "РІШЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(s, "ВИЗНАЧЕННЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ПОСТАНОВА")) || com.pullenti.unisharp.Utils.stringsEq(s, "ВИРОК")) {
                    int ii = i + 1;
                    for (int j = ii + 1; j < children.size(); j++) {
                        ii = j;
                    }
                    if (res == null) 
                        res = new java.util.ArrayList<FragToken>();
                    if (ii == (i + 1)) 
                        res.add(children.get(i + 1));
                    else 
                        res.add(_new1494(children.get(i + 1).getBeginToken(), children.get(ii).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
                }
                else 
                    dir = true;
            }
        }
        if (res != null) 
            return res;
        if (dir) 
            return null;
        for (FragToken ch : children) {
            java.util.ArrayList<FragToken> re = ch._findResolution();
            if (re != null) {
                if (res == null) 
                    res = re;
                else 
                    com.pullenti.unisharp.Utils.addToArrayList(res, re);
            }
        }
        return res;
    }

    private static FragToken __createActionQuestion(com.pullenti.ner.Token t, int maxChar) {
        java.util.ArrayList<InstrToken> li = new java.util.ArrayList<InstrToken>();
        boolean ok = false;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            InstrToken it = InstrToken.parse(tt, maxChar, null);
            if (it == null) 
                break;
            li.add(it);
            if (li.size() > 5) 
                return null;
            if (it.typ == ILTypes.QUESTION) {
                ok = true;
                break;
            }
            tt = it.getEndToken();
        }
        if (!ok) 
            return null;
        com.pullenti.ner.Token t1 = li.get(li.size() - 1).getEndToken();
        java.util.ArrayList<InstrToken> li2 = new java.util.ArrayList<InstrToken>();
        ok = false;
        for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (!tt.isNewlineBefore()) 
                continue;
            InstrToken it = InstrToken.parse(tt, maxChar, null);
            if (it == null) 
                break;
            li2.add(it);
            tt = it.getEndToken();
            if (it.typ != ILTypes.TYP) 
                continue;
            InstrToken1 it1 = InstrToken1.parse(tt, true, null, 0, null, false, maxChar, false, false);
            if (it1 != null && it1.hasVerb) {
                tt = it1.getEndToken();
                continue;
            }
            com.pullenti.ner.Token tt2 = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(it.getBeginToken(), false);
            if (tt2 != null && tt2 == it.getEndToken()) {
                ok = true;
                break;
            }
        }
        if (!ok) 
            return null;
        com.pullenti.ner.Token t2 = li2.get(li2.size() - 1).getBeginToken();
        while (li2.size() > 1 && li2.get(li2.size() - 2).typ == ILTypes.ORGANIZATION) {
            t2 = li2.get(li2.size() - 2).getBeginToken();
            li2.remove(li2.size() - 1);
        }
        FragToken res = createDocument(t2, maxChar, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        if (res == null) 
            return null;
        FragToken ques = _new1494(t, t2.getPrevious(), com.pullenti.ner.instrument.InstrumentKind.QUESTION);
        res.children.add(0, ques);
        ques.children.add(_new1494(li.get(li.size() - 1).getBeginToken(), li.get(li.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.KEYWORD));
        FragToken content = _new1494(li.get(li.size() - 1).getEndToken().getNext(), t2.getPrevious(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
        ques.children.add(content);
        content._analizeContent(res, maxChar > 0, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        if (li.size() > 1) {
            FragToken fr = _new1516(t, li.get(li.size() - 2).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true);
            ques.children.add(0, fr);
        }
        res.setBeginToken(t);
        return res;
    }

    private static FragToken createGostTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        if (t0 == null) 
            return null;
        boolean ok = false;
        if (t0.isTableControlChar() && t0.getNext() != null) 
            t0 = t0.getNext();
        com.pullenti.ner.Token t;
        int cou = 0;
        for (t = t0; t != null && (cou < 300); t = t.getNext(),cou++) {
            com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (dr != null) {
                if (dr.getKind() == com.pullenti.ner.decree.DecreeKind.STANDARD) {
                    t = t.kit.debedToken(t);
                    if (t.getBeginChar() == t0.getBeginChar()) 
                        t0 = t;
                    ok = true;
                }
                break;
            }
            if (t.isTableControlChar()) 
                continue;
            if (t.isNewlineBefore() || ((t.getPrevious() != null && t.getPrevious().isTableControlChar()))) {
                if (_isStartOfBody(t, false)) 
                    break;
                com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                if (dt != null) {
                    if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                        if (dt.typKind == com.pullenti.ner.decree.DecreeKind.STANDARD) 
                            ok = true;
                        break;
                    }
                }
            }
        }
        if (!ok) 
            return null;
        FragToken title = _new1494(t0, t0, com.pullenti.ner.instrument.InstrumentKind.HEAD);
        cou = 0;
        boolean hasNum = false;
        for (t = t0; t != null && (cou < 100); t = t.getNext()) {
            if (t.isNewlineBefore() && t != t0) {
                title.setEndToken(t.getPrevious());
                if (t.isValue("ЧАСТЬ", null)) {
                    t = t.getNext();
                    com.pullenti.ner.Token tt1 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
                    if (tt1 != null) 
                        t = tt1;
                    if (t instanceof com.pullenti.ner.NumberToken) {
                        StringBuilder tmp = new StringBuilder();
                        for (; t != null; t = t.getNext()) {
                            if (t instanceof com.pullenti.ner.NumberToken) 
                                tmp.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                            else if (((t.isHiphen() || t.isChar('.'))) && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                                tmp.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                            else 
                                break;
                            if (t.isWhitespaceAfter()) 
                                break;
                        }
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, tmp.toString(), true, 0);
                    }
                    continue;
                }
                if (_isStartOfBody(t, false)) 
                    break;
                cou++;
            }
            if (!hasNum) {
                com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                if (dr != null && dr.getKind() == com.pullenti.ner.decree.DecreeKind.STANDARD) 
                    t = t.kit.debedToken(t);
            }
            title.setEndToken(t);
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
            if (dt == null) 
                continue;
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                if (dt.typKind != com.pullenti.ner.decree.DecreeKind.STANDARD) 
                    continue;
                _addTitleAttr(doc, title, dt);
                t = dt.getEndToken();
                if (!hasNum) {
                    com.pullenti.ner.decree.internal.DecreeToken num = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t.getNext(), dt, false);
                    if (num != null && num.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                        _addTitleAttr(doc, title, num);
                        if (num.numYear > 0) 
                            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_DATE, num.numYear, false, 0);
                        t = dt.getEndToken();
                        hasNum = true;
                    }
                }
                continue;
            }
        }
        title.tag = com.pullenti.ner.decree.DecreeKind.STANDARD;
        return title;
    }

    private static FragToken createDocTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        if (t0 == null) 
            return null;
        FragToken title = createContractTitle(t0, doc);
        if (title != null) 
            return title;
        title = createGostTitle(t0, doc);
        if (title != null) 
            return title;
        title = createZapiskaTitle(t0, doc);
        if (title != null) 
            return title;
        title = createTZTitle(t0, doc);
        if (title != null) 
            return title;
        doc.getSlots().clear();
        title = createProjectTitle(t0, doc);
        if (title != null) 
            return title;
        doc.getSlots().clear();
        title = _createDocTitle_(t0, doc);
        if (title != null && title.children.size() == 1 && title.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.NAME) {
            FragToken title2 = _createDocTitle_(title.getEndToken().getNext(), doc);
            if (title2 != null && doc.getTyp() != null) {
                com.pullenti.unisharp.Utils.addToArrayList(title.children, title2.children);
                title.setEndToken(title2.getEndToken());
            }
        }
        return title;
    }

    private static FragToken _createDocTitle_(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        for (; t0 != null; t0 = t0.getNext()) {
            if (!t0.isTableControlChar()) 
                break;
        }
        if (t0 == null) 
            return null;
        FragToken title = _new1494(t0, t0, com.pullenti.ner.instrument.InstrumentKind.HEAD);
        com.pullenti.ner.decree.internal.DecreeToken dt0 = null;
        com.pullenti.ner.Token t;
        com.pullenti.ner.Token t1 = null;
        String _name = null;
        com.pullenti.ner.Token nT0 = null;
        int emptyLines = 0;
        com.pullenti.ner.Token endEmptyLines = null;
        boolean ignoreEmptyLines = false;
        int attrs = 0;
        boolean canBeOrgs = true;
        java.util.ArrayList<FragToken> unknownOrgs = new java.util.ArrayList<FragToken>();
        boolean isContract = false;
        boolean startOfName = false;
        t = t0;
        if (t0.getReferent() != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(t0.getReferent().getTypeName(), "PERSON")) 
                return null;
        }
        FragToken appr0 = null;
        if (t0.isValue("УТВЕРДИТЬ", "ЗАТВЕРДИТИ") || t0.isValue("ПРИНЯТЬ", "ПРИЙНЯТИ") || t0.isValue("УТВЕРЖДАТЬ", null)) 
            appr0 = _createApproved(t);
        if (appr0 != null) {
            t1 = title.setEndToken(appr0.getEndToken());
            title.children.add(appr0);
            t = t1.getNext();
        }
        FragToken edi0 = null;
        if (t0.isValue("РЕДАКЦИЯ", null)) 
            edi0 = _createEditions(t0);
        if (edi0 != null) {
            t1 = title.setEndToken(edi0.getEndToken());
            title.children.add(edi0);
            t = t1.getNext();
        }
        if (t != null && t.isValue("ДЕЛО", "СПРАВА")) {
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t.getNext(), null, false);
            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                dt.setBeginToken(t);
                title.children.add(_new1546(t, t, com.pullenti.ner.instrument.InstrumentKind.KEYWORD, "ДЕЛО"));
                _addTitleAttr(doc, title, dt);
                t = dt.getEndToken().getNext();
                if (t != null && t.isValue("КОПИЯ", "КОПІЯ")) 
                    t = t.getNext();
                else if ((t.isChar('(') && t.getNext() != null && t.getNext().isValue("КОПИЯ", "КОПІЯ")) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) 
                    t = t.getNext().getNext();
            }
        }
        for (; t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                continue;
            if (t.isNewlineBefore() || ((t.getPrevious() != null && t.getPrevious().isTableControlChar()))) {
                if ((t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() != com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                    t = t.kit.debedToken(t);
                if (t.isValue("О", "ПРО") || t.isValue("ОБ", null) || t.isValue("ПО", null)) 
                    break;
                if (_isStartOfBody(t, false)) 
                    break;
                if (t.isCharOf("[") && _name == null) 
                    break;
                InstrToken1 iii = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                if (iii != null && iii.typ == InstrToken1.Types.COMMENT) {
                    FragToken cmt = _new1494(iii.getBeginToken(), iii.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.COMMENT);
                    title.children.add(cmt);
                    t = (t1 = title.setEndToken(iii.getEndToken()));
                    continue;
                }
                if (iii != null && iii.getEndToken().isChar('?')) {
                    FragToken cmt = _new1494(iii.getBeginToken(), iii.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME);
                    cmt.value = FragToken.getRestoredNameMT(iii, false);
                    title.children.add(cmt);
                    t = (t1 = title.setEndToken(iii.getEndToken()));
                    break;
                }
                if ((((t.isValue("ЗАЯВИТЕЛЬ", "ЗАЯВНИК") || t.isValue("ИСТЕЦ", "ПОЗИВАЧ") || t.isValue("ОТВЕТЧИК", "ВІДПОВІДАЧ")) || t.isValue("ДОЛЖНИК", "БОРЖНИК") || t.isValue("КОПИЯ", "КОПІЯ"))) && t.getNext() != null && ((t.getNext().isChar(':') || t.getNext().isTableControlChar()))) {
                    com.pullenti.ner.ReferentToken ptt = _createJustParticipant(t.getNext().getNext(), null);
                    if (ptt != null) {
                        if (t.isValue("КОПИЯ", null)) {
                        }
                        t1 = ptt.getEndToken();
                        while (t1.getNext() != null && t1.getNext().isTableControlChar()) {
                            t1 = t1.getNext();
                        }
                        if (t1.getNext() != null && t1.getNext().isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) 
                                t1 = br.getEndToken();
                        }
                        FragToken ft = _new1494(t, t1, com.pullenti.ner.instrument.InstrumentKind.INITIATOR);
                        title.children.add(ft);
                        t = title.setEndToken(t1);
                        continue;
                    }
                }
                if (t.isValue("ЦЕНА", "ЦІНА") && t.getNext() != null && t.getNext().isValue("ИСК", "ПОЗОВ")) {
                    boolean hasMoney = false;
                    com.pullenti.ner.Token tt;
                    for (tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getReferent() instanceof com.pullenti.ner.money.MoneyReferent) 
                            hasMoney = true;
                        if (tt.isNewlineAfter()) 
                            break;
                    }
                    if (tt != null && hasMoney) {
                        while (tt.getNext() != null && tt.getNext().isTableControlChar()) {
                            tt = tt.getNext();
                        }
                        if (tt.getNext() != null && tt.getNext().isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) 
                                tt = br.getEndToken();
                        }
                        title.children.add(_new1494(t, tt, com.pullenti.ner.instrument.InstrumentKind.CASEINFO));
                        t = title.setEndToken((t1 = tt));
                        continue;
                    }
                }
                if (t.isValue("В", "У")) {
                    com.pullenti.ner.Token tt = t.getNext();
                    if (tt != null && tt.isTableControlChar()) 
                        tt = tt.getNext();
                    if (tt != null && (tt.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                        com.pullenti.ner.Referent r = tt.getReferent();
                        while (tt.getNext() != null && tt.getNext().isTableControlChar()) {
                            tt = tt.getNext();
                        }
                        t1 = tt;
                        if (t1.getNext() != null && t1.getNext().isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) 
                                t1 = br.getEndToken();
                        }
                        FragToken ooo = _new1494(t, t1, com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION);
                        ooo.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        ooo.referents.add(r);
                        title.children.add(ooo);
                        t = title.setEndToken(t1);
                        continue;
                    }
                }
                if (t.getLengthChar() == 1 && t.chars.isLetter() && t.isWhitespaceAfter()) {
                    int ii;
                    for (ii = 0; ii < InstrToken.m_DirectivesNorm.size(); ii++) {
                        com.pullenti.ner.Token ee = com.pullenti.ner.core.MiscHelper.tryAttachWordByLetters(InstrToken.m_DirectivesNorm.get(ii), t, false);
                        if (ee != null && ee.isNewlineAfter()) {
                            FragToken ooo = _new1546(t, ee, com.pullenti.ner.instrument.InstrumentKind.KEYWORD, InstrToken.m_DirectivesNorm.get(ii));
                            title.children.add(ooo);
                            doc.setTyp(InstrToken.m_DirectivesNorm.get(ii));
                            t = title.setEndToken(ee);
                            break;
                        }
                    }
                    if (ii < InstrToken.m_DirectivesNorm.size()) 
                        continue;
                }
            }
            if (t.isHiphen() || t.isChar('_')) {
                char ch = t.getSourceText().charAt(0);
                for (; t != null; t = t.getNext()) {
                    if (!t.isChar(ch)) 
                        break;
                }
            }
            if (t == null) 
                break;
            FragToken casinf = _createCaseInfo(t);
            if (casinf != null) 
                break;
            com.pullenti.ner.decree.DecreeReferent dr0 = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (dr0 != null) {
                if (dr0.getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                    continue;
            }
            else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) {
                com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
                if (dpr != null) {
                    if (((dpr.getPart() == null && dpr.getDocPart() == null)) || dpr.getSlots().size() != 2) 
                        break;
                    if ((t.getNext() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).isPureVerb()) 
                        break;
                    dr0 = dpr.getOwner();
                }
            }
            if (dr0 != null) {
                if (doc.getTyp() == null || com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), dr0.getTyp())) {
                    com.pullenti.ner.Token tt1 = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken();
                    java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> li = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(tt1, null, 10, false);
                    if (li != null && li.size() > 0 && li.get(li.size() - 1).isNewlineAfter()) {
                        for (com.pullenti.ner.decree.internal.DecreeToken dd : li) {
                            _addTitleAttr(doc, title, dd);
                        }
                        com.pullenti.ner.Token ttt = li.get(li.size() - 1).getEndToken();
                        if (ttt.getEndChar() < t.getEndChar()) {
                            nT0 = ttt.getNext();
                            _name = FragToken.getRestoredName(ttt.getNext(), ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken(), false);
                        }
                        t1 = t;
                        if (_name != null && t1.isNewlineAfter()) {
                            t = t.getNext();
                            break;
                        }
                        if (com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "КОДЕКС")) {
                            com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t.getNext(), null, false, false);
                            if (pt != null) {
                                if (((pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PART && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART)) || pt.values.size() != 1) 
                                    pt = null;
                            }
                            if (pt != null && pt.values.size() > 0) {
                                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, pt.values.get(0).value, false, 0);
                                title.children.add(_new1546(pt.getBeginToken(), pt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART, pt.values.get(0).value));
                                t = pt.getEndToken();
                                continue;
                            }
                        }
                        if (doc.getName() != null) {
                            t = t.getNext();
                            break;
                        }
                    }
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(dr0.getTyp(), "КОДЕКС")) {
                    com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t.getNext(), null, false, false);
                    String nam = dr0.getName();
                    if (pt != null) {
                        if (((pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PART && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART)) || pt.values.size() != 1) 
                            pt = null;
                    }
                    if (pt != null && pt.values.size() > 0) 
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, pt.values.get(0).value, false, 0);
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, nam, false, 0);
                    doc.setTyp(dr0.getTyp());
                    Object _geo = dr0.getSlotValue(com.pullenti.ner.decree.DecreeReferent.ATTR_GEO);
                    if (_geo != null) 
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO, _geo, false, 0);
                    t1 = t;
                    title.children.add(_new1546(t, t, com.pullenti.ner.instrument.InstrumentKind.NAME, nam));
                    if (pt != null && pt.values.size() > 0) {
                        title.children.add(_new1546(pt.getBeginToken(), pt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART, pt.values.get(0).value));
                        t1 = pt.getEndToken();
                    }
                    t = t1;
                    continue;
                }
                t1 = t;
                ignoreEmptyLines = true;
                canBeOrgs = false;
                continue;
            }
            if (_isStartOfBody(t, false)) 
                break;
            if (t.isValue("ПРОЕКТ", null) && t.isNewlineAfter()) 
                continue;
            if (doc.getTyp() == null) {
                com.pullenti.ner.Token ttt1 = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false);
                if (ttt1 != null && ttt1.isNewlineAfter()) {
                    String typ = com.pullenti.ner.core.MiscHelper.getTextValue(t, ttt1, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
                    if (doc.getTyp() == null) 
                        doc.setTyp(typ);
                    title.children.add(_new1546(t, ttt1, com.pullenti.ner.instrument.InstrumentKind.TYP, typ));
                    dt0 = com.pullenti.ner.decree.internal.DecreeToken._new905(t, ttt1, com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP, typ);
                    canBeOrgs = false;
                    t1 = (t = ttt1);
                    continue;
                }
                if (t.isNewlineBefore() && ttt1 != null && com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false) == null) {
                    startOfName = true;
                    break;
                }
            }
            FragToken appr = _createApproved(t);
            if (appr != null) {
                t = (t1 = appr.getEndToken());
                title.children.add(appr);
                if (appr.getBeginChar() < title.getBeginChar()) 
                    title.setBeginToken(appr.getBeginToken());
                continue;
            }
            FragToken edss = _createEditions(t);
            if (edss != null) 
                break;
            FragToken misc = _createMisc(t);
            if (misc != null) {
                t = (t1 = misc.getEndToken());
                title.children.add(misc);
                continue;
            }
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, dt0, false);
            if (dt != null) {
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                    if (dt.getLengthChar() < 4) 
                        dt = null;
                }
            }
            if (dt == null && dt0 != null && ((dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG))) {
                if ((t instanceof com.pullenti.ner.NumberToken) && t.isNewlineAfter() && t.isNewlineBefore()) 
                    dt = com.pullenti.ner.decree.internal.DecreeToken._new905(t, t, com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            }
            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.UNKNOWN) 
                dt = null;
            if ((dt == null && (t instanceof com.pullenti.ner.NumberToken) && t.isNewlineBefore()) && t.isNewlineAfter()) {
                if (dt0 != null && dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT)) 
                    dt = com.pullenti.ner.decree.internal.DecreeToken._new905(t, t, com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            }
            if ((dt != null && dt0 != null && dt0.getEndToken().getNext() != dt.getBeginToken()) && ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG))) {
                if (!t.isNewlineBefore() && !t.getPrevious().isTableControlChar()) 
                    dt = null;
                else 
                    for (com.pullenti.ner.Token ttt = dt.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isNewlineBefore() || ttt.isTableControlChar()) 
                            break;
                        else if ((ttt instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.TextToken.class)).isPureVerb()) {
                            dt = null;
                            break;
                        }
                    }
            }
            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE && dt0 != null) {
                if (dt.isNewlineBefore() || dt.isNewlineAfter()) {
                }
                else if (dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                }
                else if (dt0.getEndToken().getNext() == dt.getBeginToken()) {
                }
                else 
                    dt = null;
            }
            if (dt == null) {
                if (t.getReferent() instanceof com.pullenti.ner.date.DateReferent) 
                    continue;
                if (t.isValue("ДАТА", null)) {
                    boolean ok = false;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if ((tt.isValue("ПОДПИСАНИЕ", "ПІДПИСАННЯ") || tt.isValue("ВВЕДЕНИЕ", "ВВЕДЕННЯ") || tt.isValue("ПРИНЯТИЕ", "ПРИЙНЯТТЯ")) || tt.isValue("ДЕЙСТВИЕ", "ДІЮ") || tt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter()) 
                            continue;
                        com.pullenti.ner.date.DateReferent da = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateReferent.class);
                        if (da != null) {
                            FragToken frdt = _new1494(t, tt, com.pullenti.ner.instrument.InstrumentKind.DATE);
                            title.children.add(frdt);
                            t = tt;
                            ok = true;
                            if (doc.getDate() == null) 
                                doc.addDate(da);
                        }
                        break;
                    }
                    if (ok) 
                        continue;
                }
                com.pullenti.ner.Referent r = t.getReferent();
                if ((r == null && t.getLengthChar() == 1 && !t.chars.isLetter()) && (t.getNext() instanceof com.pullenti.ner.ReferentToken) && !t.isNewlineAfter()) {
                    t = t.getNext();
                    r = t.getReferent();
                }
                if (((r instanceof com.pullenti.ner.address.AddressReferent) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent)) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) {
                    FragToken cnt = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.CONTACT);
                    cnt.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    cnt.referents.add(r);
                    title.children.add(cnt);
                    for (; t != null; t = t.getNext()) {
                        if (t.getNext() != null && t.getNext().isCharOf(",;.")) 
                            t = t.getNext();
                        if (t.getNext() == null) 
                            break;
                        r = t.getNext().getReferent();
                        if (((r instanceof com.pullenti.ner.address.AddressReferent) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent)) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) {
                            cnt.referents.add(r);
                            cnt.setEndToken(t.getNext());
                        }
                        else if (t.isNewlineAfter()) 
                            break;
                    }
                    continue;
                }
                com.pullenti.ner.decree.internal.PartToken pt = (t.isNewlineBefore() ? com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false) : null);
                if ((pt != null && ((pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART || pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART)) && pt.values.size() == 1) && pt.isNewlineAfter()) {
                    boolean ok = false;
                    if (dt0 != null && dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                        ok = true;
                    else {
                        com.pullenti.ner.decree.internal.DecreeToken ddd = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(pt.getEndToken().getNext(), null, false);
                        if (ddd != null && ddd.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                            ok = true;
                        else if (_createApproved(pt.getEndToken().getNext()) != null) 
                            ok = true;
                    }
                    if (ok) {
                        title.children.add(_new1546(pt.getBeginToken(), pt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART, pt.values.get(0).value));
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, pt.values.get(0).value, false, 0);
                        t = pt.getEndToken();
                        continue;
                    }
                }
                if (appr0 != null) 
                    break;
                if (canBeOrgs) {
                    if (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) {
                    }
                    else {
                        FragToken _org = _createOwner(t);
                        if (_org != null) {
                            unknownOrgs.add(_org);
                            t1 = (t = _org.getEndToken());
                            continue;
                        }
                    }
                }
                InstrToken stok = InstrToken.parse(t, 0, null);
                if (stok != null && ((stok.noWords || (stok.getLengthChar() < 5)))) {
                    if (t0 == t) 
                        t0 = stok.getEndToken().getNext();
                    t = stok.getEndToken();
                    continue;
                }
                if ((t.isNewlineBefore() && doc.getTyp() != null && (t instanceof com.pullenti.ner.TextToken)) && com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null) != null) 
                    break;
                if (t.isNewlineBefore() && t.isValue("К", "ДО")) 
                    break;
                if (((!ignoreEmptyLines && stok != null && stok.typ == ILTypes.UNDEFINED) && !stok.hasVerb && ((dt0 == null || dt0.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER))) && (emptyLines < 3)) {
                    if (stok.isNewlineAfter()) 
                        emptyLines++;
                    else if (dt0 != null) 
                        break;
                    t = (endEmptyLines = stok.getEndToken());
                    continue;
                }
                break;
            }
            if ((!ignoreEmptyLines && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && endEmptyLines != null) && dt0 == null) {
                if (dt.isNewlineAfter()) 
                    emptyLines++;
                t = (endEmptyLines = dt.getEndToken());
                continue;
            }
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                if (isContract) 
                    break;
                for (com.pullenti.ner.Token ttt = dt.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (ttt.getWhitespacesBeforeCount() > 15) 
                        break;
                    if (ttt.getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB)) {
                        dt = null;
                        break;
                    }
                    com.pullenti.ner.decree.internal.DecreeToken dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt, dt0, false);
                    if (dt1 != null) {
                        if ((dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) 
                            break;
                        dt.setEndToken(dt1.getEndToken());
                    }
                    else if (!ttt.chars.equals(dt.getBeginToken().chars) && ttt.isNewlineBefore()) 
                        break;
                    else 
                        dt.setEndToken(ttt);
                }
                if (dt == null) 
                    break;
            }
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                com.pullenti.ner.decree.DecreeKind typ = com.pullenti.ner.decree.internal.DecreeToken.getKind(dt.value);
                if (typ == com.pullenti.ner.decree.DecreeKind.PUBLISHER) {
                    for (; t != null; t = t.getNext()) {
                        if (t.isNewlineAfter()) 
                            break;
                    }
                    if (t == null) 
                        break;
                    continue;
                }
                if (typ == com.pullenti.ner.decree.DecreeKind.CONTRACT || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ДОВЕРЕННОСТЬ") || com.pullenti.unisharp.Utils.stringsEq(dt.value, "ДОВІРЕНІСТЬ")) 
                    isContract = true;
                else if (com.pullenti.unisharp.Utils.stringsEq(dt.value, "ПРОТОКОЛ") && !dt.isNewlineAfter()) {
                    com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(dt.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt1 != null) {
                        for (t = dt.getEndToken().getNext(); t != null; t = t.getNext()) {
                            dt.setEndToken(t);
                            if (t.isNewlineAfter()) 
                                break;
                        }
                    }
                }
                canBeOrgs = false;
            }
            dt0 = dt;
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && unknownOrgs.size() > 0) {
                for (FragToken _org : unknownOrgs) {
                    title.children.add(_org);
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, _org.value, false, 0);
                }
                unknownOrgs.clear();
            }
            if (!_addTitleAttr(doc, title, dt)) 
                break;
            else 
                attrs++;
            t1 = (t = dt.getEndToken());
        }
        title.sortChildren();
        if (t == null || (((doc.getTyp() == null && doc.getRegNumber() == null && appr0 == null) && !startOfName))) {
            if (t == t0) {
                com.pullenti.ner.decree.internal.DecreeToken nam = com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(t0, null, true, false);
                if (nam != null) {
                    _name = FragToken.getRestoredName(t0, nam.getEndToken(), false);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(_name)) {
                        t1 = nam.getEndToken();
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, _name.trim(), true, 0);
                        title.children.add(_new1546(t0, t1, com.pullenti.ner.instrument.InstrumentKind.NAME, _name.trim()));
                        for (; t1.getNext() != null; t1 = t1.getNext()) {
                            if (t1.isTableControlChar() && !t1.isChar((char)0x1F)) {
                            }
                            else 
                                break;
                        }
                        title.setEndToken(t1);
                        for (t = t1.getNext(); t != null; t = t.getNext()) {
                            if (_isStartOfBody(t, false)) 
                                break;
                            if (t.isTableControlChar()) 
                                continue;
                            FragToken appr1 = _createApproved(t);
                            if (appr1 != null) {
                                title.children.add(appr1);
                                t = title.setEndToken(appr1.getEndToken());
                                continue;
                            }
                            FragToken eds = _createEditions(t);
                            if (eds != null) {
                                title.children.add(eds);
                                t = title.setEndToken(eds.getEndToken());
                                break;
                            }
                            appr1 = _createMisc(t);
                            if (appr1 != null) {
                                title.children.add(appr1);
                                t = title.setEndToken(appr1.getEndToken());
                                continue;
                            }
                            com.pullenti.ner.decree.internal.DecreeToken dt00 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                            if (dt00 != null) {
                                if (dt00.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt00.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                                    _addTitleAttr(doc, title, dt00);
                                    t = title.setEndToken(dt00.getEndToken());
                                    continue;
                                }
                            }
                            break;
                        }
                        return title;
                    }
                }
            }
            if (t != null && t.isValue("О", null)) {
                com.pullenti.ner.decree.internal.DecreeToken nam = com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(t, null, true, false);
                if (nam != null) {
                    _name = FragToken.getRestoredName(t, nam.getEndToken(), false);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(_name)) {
                        t1 = nam.getEndToken();
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, _name.trim(), true, 0);
                        title.children.add(_new1546(t, t1, com.pullenti.ner.instrument.InstrumentKind.NAME, _name.trim()));
                    }
                }
            }
            if (attrs > 0) {
                title.setEndToken(t1);
                return title;
            }
            return null;
        }
        for (int j = 0; j < unknownOrgs.size(); j++) {
            title.children.add(j, unknownOrgs.get(j));
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, unknownOrgs.get(j).value, false, 0);
        }
        if (endEmptyLines != null && doc.findSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, null, true) == null) {
            String val = com.pullenti.ner.core.MiscHelper.getTextValue(t0, endEmptyLines, com.pullenti.ner.core.GetTextAttr.NO);
            doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, val, false, 0);
            title.children.add(0, _new1546(t0, endEmptyLines, com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION, val));
        }
        boolean isCase = false;
        for (FragToken ch : title.children) {
            if (ch.value == null && ch.kind != com.pullenti.ner.instrument.InstrumentKind.APPROVED && ch.kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                ch.value = com.pullenti.ner.core.MiscHelper.getTextValue(ch.getBeginToken(), ch.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CASENUMBER) 
                isCase = true;
        }
        if ((((_name != null || t.isNewlineBefore() || ((t.getPrevious() != null && t.getPrevious().isTableControlChar()))) || ((!t.isNewlineBefore() && title.children.size() > 0 && title.children.get(title.children.size() - 1).kind == com.pullenti.ner.instrument.InstrumentKind.TYP)))) && !isCase) {
            com.pullenti.ner.Token tt0 = t;
            InstrToken1 firstLine = null;
            boolean poDelu = false;
            if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("ДЕЛО", "СПРАВА")) 
                poDelu = true;
            for (; t != null; t = t.getNext()) {
                if (_isStartOfBody(t, false)) 
                    break;
                if ((_name != null && t == tt0 && t.isNewlineBefore()) && t.getWhitespacesBeforeCount() > 15) 
                    break;
                if (t.isTableControlChar()) 
                    break;
                if (t.isNewlineBefore()) {
                    com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false);
                    if (pt != null && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) 
                        break;
                    InstrToken1 ltt = InstrToken1.parse(t, false, null, 0, null, false, 0, true, false);
                    if (ltt == null) 
                        break;
                    if (t != tt0 && t.getWhitespacesBeforeCount() > 15) {
                        if (t.getNewlinesBeforeCount() > 2) 
                            break;
                        if (t.getNewlinesBeforeCount() > 1 && !t.chars.isAllUpper()) 
                            break;
                        if (t.isValue("О", "ПРО") || t.isValue("ОБ", null)) {
                        }
                        else if (ltt.allUpper && !ltt.getHasChanges()) {
                        }
                        else 
                            break;
                    }
                    if (ltt.numbers.size() > 0) 
                        break;
                    FragToken appr = _createApproved(t);
                    if (appr != null) {
                        if (t.getPrevious() != null && t.getPrevious().isChar(',')) {
                        }
                        else 
                            break;
                    }
                    if (_createEditions(t) != null || _createCaseInfo(t) != null) 
                        break;
                    if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                        if (t.isNewlineAfter()) 
                            break;
                        if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) 
                            break;
                    }
                    if (t.getReferent() instanceof com.pullenti.ner.date.DateReferent) {
                        if (t.isNewlineAfter()) 
                            break;
                        if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                            break;
                    }
                    if (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) 
                        break;
                    if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                        com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                        if (dr.getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                            break;
                    }
                    if (t.isChar('(')) {
                        if (_createEditions(t) != null) 
                            break;
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null && !br.isNewlineAfter()) {
                        }
                        else 
                            break;
                    }
                    if (ltt.hasVerb && !ltt.allUpper) {
                        if (t.isValue("О", "ПРО") && tt0 == t) {
                        }
                        else if (!poDelu) 
                            break;
                    }
                    if (ltt.typ == InstrToken1.Types.DIRECTIVE) 
                        break;
                    String str = ltt.toString();
                    if (t.getPrevious() != null && t.getPrevious().isValue("ИЗМЕНЕНИЕ", null)) {
                    }
                    else if ((str.indexOf("В СОСТАВЕ") >= 0) || (str.indexOf("В СКЛАДІ") >= 0) || (str.indexOf("У СКЛАДІ") >= 0)) {
                        if (!ltt.allUpper) 
                            break;
                    }
                    if (t.isValue("В", null) && t.getNext() != null && t.getNext().isValue("ЦЕЛЬ", "МЕТА")) 
                        break;
                    if (firstLine == null) 
                        firstLine = ltt;
                    else if (firstLine.allUpper && !ltt.allUpper && !com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) 
                        break;
                    t1 = (t = ltt.getEndToken());
                    if (t1.isTableControlChar()) {
                        t1 = (t = t1.getPrevious());
                        break;
                    }
                }
                else 
                    t1 = t;
            }
            com.pullenti.ner.Token tt1 = com.pullenti.ner.decree.internal.DecreeToken._tryAttachStdChangeName(tt0);
            if (tt1 != null) {
                if (t1 == null || (t1.getEndChar() < tt1.getEndChar())) 
                    t1 = tt1;
            }
            String val = (t1 != null && t1 != tt0 ? FragToken.getRestoredName(tt0, t1, false) : null);
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(val) && Character.isLetter(val.charAt(0)) && Character.isLowerCase(val.charAt(0))) 
                val = (String.valueOf(Character.toUpperCase(val.charAt(0)))) + val.substring(1);
            if (_name == null && title.children.size() > 0 && title.children.get(title.children.size() - 1).kind == com.pullenti.ner.instrument.InstrumentKind.TYP) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) {
                    if (npt.getMorph().getCase().isGenitive()) {
                        _name = (String)com.pullenti.unisharp.Utils.cast(title.children.get(title.children.size() - 1).value, String.class);
                        if (com.pullenti.ner.decree.internal.DecreeToken.tryAttach(title.children.get(title.children.size() - 1).getBeginToken(), null, false) == null) {
                            tt0 = title.children.get(title.children.size() - 1).getBeginToken();
                            title.children.remove(title.children.size() - 1);
                        }
                    }
                }
            }
            if (val == null) 
                val = _name;
            else if (_name != null) 
                val = (_name + " " + val);
            if (val != null) {
                if (nT0 != null) 
                    tt0 = nT0;
                val = val.trim();
                if (val.startsWith("[") && val.endsWith("]")) 
                    val = val.substring(1, 1 + val.length() - 2).trim();
                doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, val.trim(), true, 0);
                title.children.add(_new1546(tt0, t1, com.pullenti.ner.instrument.InstrumentKind.NAME, val.trim()));
                if ((val.indexOf("КОДЕКС") >= 0)) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.noun.isValue("КОДЕКС", null)) 
                        doc.setTyp("КОДЕКС");
                }
            }
        }
        if (t1 == null) 
            return null;
        title.setEndToken(t1);
        for (t1 = t1.getNext(); t1 != null; t1 = t1.getNext()) {
            if (t1.isNewlineBefore() && (t1.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && t1.isNewlineAfter()) {
                com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                title.children.add(_new1494(t1, t1, com.pullenti.ner.instrument.InstrumentKind.IGNORED));
                continue;
            }
            if (t1.isNewlineBefore() && t1.isValue("ЧАСТЬ", "ЧАСТИНА")) {
                com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t1, null, false, false);
                if (pt != null && pt.isNewlineAfter()) {
                    com.pullenti.ner.decree.internal.PartToken pt2 = com.pullenti.ner.decree.internal.PartToken.tryAttach(pt.getEndToken().getNext(), null, false, false);
                    if (pt2 != null && (((pt2.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SECTION || pt2.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBSECTION || pt2.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CHAPTER) || pt2.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE))) {
                    }
                    else {
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, pt.values.get(0).value, false, 0);
                        title.children.add(_new1546(t1, pt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART, pt.values.get(0).value));
                        t1 = title.setEndToken(pt.getEndToken());
                        continue;
                    }
                }
            }
            if (t1.isNewlineBefore()) {
                InstrToken1 iii = InstrToken1.parse(t1, true, null, 0, null, false, 0, false, false);
                if (iii != null && iii.typ == InstrToken1.Types.COMMENT) {
                    title.children.add(_new1494(t1, iii.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.COMMENT));
                    t1 = iii.getEndToken();
                    continue;
                }
            }
            FragToken appr1 = _createApproved(t1);
            if (appr1 != null) {
                t1 = appr1.getEndToken();
                title.children.add(appr1);
                title.setEndToken(appr1.getEndToken());
                continue;
            }
            FragToken cinf = _createCaseInfo(t1);
            if (cinf != null) {
                t1 = cinf.getEndToken();
                title.children.add(cinf);
                title.setEndToken(cinf.getEndToken());
                continue;
            }
            FragToken eds = _createEditions(t1);
            if (eds != null) {
                title.children.add(eds);
                title.setEndToken((t1 = eds.getEndToken()));
                continue;
            }
            appr1 = _createMisc(t1);
            if (appr1 != null) {
                t1 = appr1.getEndToken();
                title.children.add(appr1);
                title.setEndToken(appr1.getEndToken());
                continue;
            }
            if ((t1.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER && t1.isNewlineAfter()) {
                FragToken pub = _new1494(t1, t1, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
                pub.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                pub.referents.add(t1.getReferent());
                title.children.add(pub);
                title.setEndToken(t1);
                continue;
            }
            com.pullenti.ner.Token tt = t1;
            if (tt.getNext() != null && tt.isChar(',')) 
                tt = tt.getNext();
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
            if (dt != null && ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR || ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && ((dt.isDelo() || com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt) != null))))))) {
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                    if (doc.getDate() != null) 
                        break;
                    if (!dt.isNewlineAfter() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(dt.getEndToken().getNext())) {
                        com.pullenti.ner.Token ttt = dt.getEndToken().getNext();
                        if (ttt != null && (((ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) || ttt.isComma()))) {
                        }
                        else 
                            break;
                    }
                }
                if (!dt.isNewlineAfter()) {
                    InstrToken1 lll = InstrToken1.parse(tt, true, null, 0, null, false, 0, false, false);
                    if (lll != null && lll.hasVerb) 
                        break;
                }
                _addTitleAttr(doc, title, dt);
                t1 = title.setEndToken(dt.getEndToken());
                continue;
            }
            if (tt.isCharOf("([") && tt.isNewlineBefore()) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.of((com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES.value()) | (com.pullenti.ner.core.BracketParseAttr.CANCONTAINSVERBS.value())), 100);
                if (br != null) {
                    t1 = title.setEndToken(br.getEndToken());
                    title.children.add(_new1494(br.getBeginToken(), br.getEndToken(), (tt.isChar('[') ? com.pullenti.ner.instrument.InstrumentKind.NAME : com.pullenti.ner.instrument.InstrumentKind.COMMENT)));
                    continue;
                }
            }
            if (tt.isTableControlChar()) {
                title.setEndToken(tt);
                continue;
            }
            break;
        }
        t1 = title.getEndToken().getNext();
        if (t1 != null && t1.isNewlineBefore() && com.pullenti.unisharp.Utils.stringsEq(doc.getTyp(), "КОДЕКС")) {
            com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t1, null, false, false);
            if (pt != null && ((pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART || pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART)) && pt.values.size() > 0) {
                int cou = 0;
                for (t = pt.getEndToken(); t != null; t = t.getNext()) {
                    if (t.isNewlineBefore()) {
                        if ((++cou) > 4) 
                            break;
                        FragToken eds = _createEditions(t);
                        if (eds != null) {
                            title.children.add(eds);
                            title.setEndToken((t1 = eds.getEndToken()));
                            title.children.add(_new1546(pt.getBeginToken(), pt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART, pt.values.get(0).value));
                            if (doc.getName() != null && (doc.getName().indexOf("КОДЕКС") >= 0)) 
                                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, pt.values.get(0).value, false, 0);
                            break;
                        }
                    }
                }
            }
            else if (t1.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) {
                com.pullenti.ner.decree.DecreePartReferent dr0 = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
                if (dr0.getPart() != null || dr0.getDocPart() != null) {
                    int cou = 0;
                    for (t = t1.getNext(); t != null; t = t.getNext()) {
                        if (t.isNewlineBefore()) {
                            if ((++cou) > 4) 
                                break;
                            FragToken eds = _createEditions(t);
                            if (eds != null) {
                                title.children.add(eds);
                                title.setEndToken((t1 = eds.getEndToken()));
                                break;
                            }
                        }
                    }
                }
            }
        }
        return title;
    }

    private static FragToken createAppendixTitle(com.pullenti.ner.Token t0, FragToken app, com.pullenti.ner.instrument.InstrumentReferent doc, boolean isApp, boolean start) {
        if (t0 == null) 
            return null;
        if (t0 != t0.kit.firstToken) {
            if (t0.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) {
                if (((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t0.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class)).getAppendix() != null) 
                    t0 = t0.kit.debedToken(t0);
            }
        }
        com.pullenti.ner.Token t = t0;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.Referent rr = t.getReferent();
        if (rr != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(rr.getTypeName(), "PERSON")) 
                return null;
        }
        FragToken title = _new1494(t0, t, com.pullenti.ner.instrument.InstrumentKind.HEAD);
        boolean hasAppKeyword = false;
        FragToken appr0 = _createApproved(t0);
        if (appr0 != null) {
            title.setEndToken(appr0.getEndToken());
            title.children.add(appr0);
            t = appr0.getEndToken().getNext();
        }
        for (; t != null; t = t.getNext()) {
            InstrToken1 fr = InstrToken1.parse(t, true, null, 0, null, false, 0, true, false);
            if (fr == null) 
                break;
            if (fr.typ != InstrToken1.Types.APPENDIX && fr.typ != InstrToken1.Types.APPROVED) {
                if (fr.hasManySpecChars) {
                    t = fr.getEndToken();
                    continue;
                }
                if (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) {
                    t = fr.getEndToken();
                    continue;
                }
                if ((t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) && ((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class)).getAppendix() != null) {
                    t = t.kit.debedToken(t);
                    fr = InstrToken1.parse(t, true, null, 0, null, false, 0, true, false);
                    if (fr.typ != InstrToken1.Types.APPENDIX) 
                        break;
                }
                else 
                    break;
            }
            if (fr.typ == InstrToken1.Types.APPENDIX) {
                hasAppKeyword = true;
                app.kind = com.pullenti.ner.instrument.InstrumentKind.APPENDIX;
            }
            com.pullenti.ner.Token t2 = t;
            if (t.isValue("ОСОБЫЙ", "ОСОБЛИВИЙ") && t.getNext() != null) 
                t2 = t.getNext();
            if (t instanceof com.pullenti.ner.TextToken) 
                title.children.add(_new1513(t, t2, com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true));
            title.setEndToken((t = fr.getEndToken()));
            if (fr.typ == InstrToken1.Types.APPENDIX && fr.numBeginToken == null) {
                InstrToken1 fr1 = InstrToken1.parse(t.getNext(), true, null, 0, null, false, 0, false, false);
                if (fr1 != null && fr1.typ == InstrToken1.Types.APPROVED) {
                    t = fr1.getBeginToken();
                    title.children.add(_new1546(t, t, com.pullenti.ner.instrument.InstrumentKind.KEYWORD, t.getSourceText().toUpperCase()));
                    title.setEndToken((t = fr1.getEndToken()));
                    fr = fr1;
                }
            }
            appr0 = _createApproved(t);
            if (appr0 != null) {
                t = title.setEndToken(appr0.getEndToken());
                title.children.add(appr0);
                continue;
            }
            if (fr.numBeginToken != null && fr.numEndToken != null) {
                FragToken num = _new1546(fr.numBeginToken, fr.numEndToken, com.pullenti.ner.instrument.InstrumentKind.NUMBER, com.pullenti.ner.core.MiscHelper.getTextValue(fr.numBeginToken, fr.numEndToken, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                title.children.add(num);
                if (fr.numbers.size() > 0) 
                    app.number = com.pullenti.ner.decree.internal.PartToken.getNumber(fr.numbers.get(0));
                if (fr.numbers.size() > 1) {
                    app.subNumber = com.pullenti.ner.decree.internal.PartToken.getNumber(fr.numbers.get(1));
                    if (fr.numbers.size() > 2) 
                        app.subNumber2 = com.pullenti.ner.decree.internal.PartToken.getNumber(fr.numbers.get(2));
                }
                if (isApp) 
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_APPENDIX, (num.value != null ? num.value : "1"), false, 0);
            }
            else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                if (((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) {
                    FragToken ff = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
                    ff.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    ff.referents.add(t.getReferent());
                    title.children.add(ff);
                }
                else if (fr.typ == InstrToken1.Types.APPROVED && title.children.size() > 0 && title.children.get(title.children.size() - 1).kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD) {
                    FragToken kw = title.children.get(title.children.size() - 1);
                    FragToken appr = _new1494(kw.getBeginToken(), t, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
                    title.children.remove(title.children.size() - 1);
                    appr.children.add(kw);
                    appr.children.add(_new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE));
                    title.children.add(appr);
                }
                else 
                    title.children.add(_new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE));
            }
            else if (fr.typ == InstrToken1.Types.APPROVED && fr.getLengthChar() > 15 && fr.getBeginToken() != fr.getEndToken()) 
                title.children.add(_new1494(fr.getBeginToken().getNext(), t, com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE));
            else {
                java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t.getNext(), null, 10, false);
                if (dts != null && dts.size() > 0 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                    FragToken dref = _new1494(dts.get(0).getBeginToken(), dts.get(0).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE);
                    for (int i = 1; i < dts.size(); i++) {
                        if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                            break;
                        else if (dts.get(i).typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.UNKNOWN) 
                            dref.setEndToken(dts.get(i).getEndToken());
                    }
                    title.children.add(dref);
                    title.setEndToken((t = dref.getEndToken()));
                }
            }
            if (fr.typ == InstrToken1.Types.APPENDIX) {
                t = t.getNext();
                if (t != null) {
                    com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
                    if (dpr != null && dpr.getAppendix() != null) {
                        t = t.kit.debedToken(t);
                        t = t.getPrevious();
                        continue;
                    }
                    if (t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
                        t = t.getPrevious();
                        continue;
                    }
                }
                break;
            }
        }
        if (t == null) 
            return null;
        boolean hasForNpa = false;
        if (t.isValue("К", "ДО")) {
            hasForNpa = true;
            com.pullenti.ner.decree.DecreeReferent toDecr = null;
            java.util.ArrayList<InstrToken> toks = new java.util.ArrayList<InstrToken>();
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt != t.getNext() && tt.isTableControlChar()) 
                    break;
                if (tt.isNewlineBefore()) {
                    if (tt.getNewlinesBeforeCount() > 1) 
                        break;
                    InstrToken1 it1 = InstrToken1.parse(tt, false, null, 0, null, false, 0, false, false);
                    if (it1 != null && it1.numbers.size() > 0) 
                        break;
                    if (tt.chars.isAllLower()) {
                    }
                    else if (tt.getLengthChar() > 2) 
                        break;
                }
                if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) 
                    toDecr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                InstrToken tok = InstrToken.parse(tt, 0, null);
                if (tok == null) 
                    break;
                toks.add(tok);
                if (toks.size() > 20) 
                    break;
                if (tt == t.getNext() && tok.typ == ILTypes.UNDEFINED) {
                    com.pullenti.ner.Token ttt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, false);
                    if (ttt != null) {
                        tok.setEndToken(ttt);
                        tok.typ = ILTypes.TYP;
                    }
                }
                tt = tok.getEndToken();
                com.pullenti.ner.decree.internal.DecreeToken dtt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt.getNext(), null, false);
                if (dtt != null && dtt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                    tt = tok.setEndToken(dtt.getEndToken());
                if (tok.typ == ILTypes.TYP && !tt.isNewlineAfter()) {
                    com.pullenti.ner.decree.internal.DecreeToken nn = com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(tt.getNext(), null, false, true);
                    if (nn != null) {
                        tt = tok.setEndToken(nn.getEndToken());
                        break;
                    }
                }
            }
            int maxInd = -1;
            for (int ii = 0; ii < toks.size(); ii++) {
                InstrToken tok = toks.get(ii);
                if (tok.typ == ILTypes.TYP && ((com.pullenti.unisharp.Utils.stringsEq(tok.value, doc.getTyp()) || ii == 0))) 
                    maxInd = ii;
                else if (tok.typ == ILTypes.REGNUMBER && (((com.pullenti.unisharp.Utils.stringsEq(tok.value, doc.getRegNumber()) || com.pullenti.unisharp.Utils.stringsEq(tok.value, "?") || tok.isNewlineBefore()) || tok.isNewlineAfter() || tok.getHasTableChars()))) 
                    maxInd = ii;
                else if (tok.typ == ILTypes.DATE && doc.getDate() != null) {
                    if ((tok.ref instanceof com.pullenti.ner.date.DateReferent) && ((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(tok.ref, com.pullenti.ner.date.DateReferent.class)).getDt().compareTo(doc.getDate()) == 0) 
                        maxInd = ii;
                    else if (tok.ref instanceof com.pullenti.ner.ReferentToken) {
                        com.pullenti.ner.date.DateReferent dre = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tok.ref, com.pullenti.ner.ReferentToken.class)).referent, com.pullenti.ner.date.DateReferent.class);
                        if (dre != null && dre.getDt() != null && doc.getDate() != null) {
                            if (dre.getDt().compareTo(doc.getDate()) == 0) 
                                maxInd = ii;
                        }
                    }
                }
                else if (tok.typ == ILTypes.DATE && tok.getBeginToken().getPrevious() != null && tok.getBeginToken().getPrevious().isValue("ОТ", null)) 
                    maxInd = ii;
                else if (tok.typ == ILTypes.UNDEFINED && (tok.getBeginToken().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                    maxInd = ii;
                    break;
                }
                else if (ii == 0 && tok.typ == ILTypes.UNDEFINED && (tok.getBeginToken().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) {
                    com.pullenti.ner.decree.DecreePartReferent part = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(tok.getBeginToken().getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
                    if (part.getAppendix() != null) {
                        maxInd = ii;
                        break;
                    }
                }
                else if (tok.typ == ILTypes.ORGANIZATION && ii == 1) 
                    maxInd = ii;
                else if (tok.typ == ILTypes.UNDEFINED) {
                    if (tok.getBeginToken().isValue("ОТ", null) || !tok.isNewlineBefore()) 
                        maxInd = ii;
                    else if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tok.getBeginToken()) != null) 
                        maxInd = ii;
                }
                else if (tok.typ == ILTypes.GEO || tok.typ == ILTypes.ORGANIZATION) 
                    maxInd = ii;
            }
            if (toks.size() > 0 && com.pullenti.ner.decree.internal.DecreeToken.isKeyword(toks.get(toks.size() - 1).getEndToken().getNext(), false) != null) 
                maxInd = toks.size() - 1;
            com.pullenti.ner.Token te = null;
            if (maxInd >= 0) {
                te = toks.get(maxInd).getEndToken();
                if (!te.isNewlineAfter()) {
                    com.pullenti.ner.decree.internal.DecreeToken nn = com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(te.getNext(), null, false, true);
                    if (nn != null) 
                        te = nn.getEndToken();
                }
            }
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                te = t.getNext();
            if (te != null) {
                FragToken dr = _new1494(t, te, com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE);
                if (toDecr != null) {
                    dr.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    dr.referents.add(toDecr);
                }
                title.children.add(dr);
                title.setEndToken(te);
                if ((((t = te.getNext()))) == null) 
                    return title;
            }
        }
        if (title.children.size() == 0) {
            if (t != null && t.isValue("АКТ", null)) {
            }
            else 
                return null;
        }
        for (int kk = 0; kk < 10; kk++) {
            FragToken ta = _createApproved(t);
            if (ta != null) {
                title.children.add(ta);
                title.setEndToken((t = ta.getEndToken()));
                t = t.getNext();
                if (t == null) 
                    return title;
                continue;
            }
            FragToken ee = _createEditions(t);
            if (ee != null) {
                title.children.add(ee);
                title.setEndToken(ee.getEndToken());
                t = ee.getEndToken().getNext();
                if (t == null) 
                    return title;
                continue;
            }
            ta = _createMisc(t);
            if (ta != null) {
                title.children.add(ta);
                title.setEndToken((t = ta.getEndToken()));
                t = t.getNext();
                if (t == null) 
                    return title;
                continue;
            }
            break;
        }
        com.pullenti.ner.Token tt0 = t;
        if ((start && hasForNpa && hasAppKeyword) && tt0.isNewlineBefore()) {
            com.pullenti.ner.decree.internal.DecreeToken dty = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt0, null, false);
            if (dty != null && dty.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                FragToken sub = FragToken.createDocument(tt0, 0, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
                if (sub != null && sub.children.size() > 1 && sub.m_Doc.findSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_APPENDIX, null, true) == null) {
                    if (sub.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.HEAD && sub.children.get(0).children.size() > 1 && sub.children.get(0).children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.TYP) {
                        title.tag = sub;
                        return title;
                    }
                }
            }
        }
        com.pullenti.ner.Token nT0 = null;
        for (; t != null; t = t.getNext()) {
            if (t.isTableControlChar()) {
                if (t == tt0) {
                    if (t.isChar((char)0x1E)) {
                        java.util.ArrayList<com.pullenti.ner.core.TableRowToken> rows = com.pullenti.ner.core.TableHelper.tryParseRows(t, 0, true);
                        if (rows != null && rows.size() > 2) 
                            break;
                        break;
                    }
                    tt0 = t.getNext();
                    continue;
                }
                break;
            }
            if (t.isNewlineBefore() || t.getPrevious().isTableControlChar()) {
                if (_isStartOfBody(t, t == tt0)) 
                    break;
                if (_createApproved(t) != null) 
                    break;
                if (_createEditions(t) != null) 
                    break;
                if (t != tt0 && t.getWhitespacesBeforeCount() > 15) {
                    if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t.getPrevious(), false) == null) {
                        if (!t.getPrevious().isValue("ОБРАЗЕЦ", "ЗРАЗОК")) 
                            break;
                    }
                    if (t.getWhitespacesBeforeCount() > 25) 
                        break;
                }
                if (t.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) 
                    break;
                if (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) {
                    if (t.getWhitespacesBeforeCount() > 15) 
                        break;
                }
                com.pullenti.ner.decree.internal.DecreeToken dd = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                if (dd != null && ((dd.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dd.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR)) && dd.isNewlineAfter()) {
                    com.pullenti.ner.core.NounPhraseToken npt0 = null;
                    if (dd.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && (t instanceof com.pullenti.ner.ReferentToken)) 
                        npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt0 != null && !npt0.getMorph().getCase().isUndefined() && !npt0.getMorph().getCase().isNominative()) {
                    }
                    else {
                        _addTitleAttr(null, title, dd);
                        t = title.setEndToken(dd.getEndToken());
                        continue;
                    }
                }
                InstrToken1 ltt = InstrToken1.parse(t, true, null, 0, null, false, 0, true, false);
                if (ltt == null) 
                    break;
                if (ltt.numbers.size() > 0) 
                    break;
                if (ltt.typ == InstrToken1.Types.APPROVED) {
                    title.children.add(_new1494(ltt.getBeginToken(), ltt.getBeginToken(), com.pullenti.ner.instrument.InstrumentKind.APPROVED));
                    if (ltt.getBeginToken() != ltt.getEndToken()) 
                        title.children.add(_new1494(ltt.getBeginToken().getNext(), ltt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE));
                    t = ltt.getEndToken();
                    if (ltt.getBeginToken() == tt0) {
                        tt0 = t.getNext();
                        continue;
                    }
                    break;
                }
                if (ltt.hasVerb && !ltt.allUpper) {
                    if (t.chars.isLetter() && t.chars.isAllLower()) {
                    }
                    else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) {
                        com.pullenti.ner.decree.DecreeChangeReferent dch = (com.pullenti.ner.decree.DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeChangeReferent.class);
                        if (dch.getKind() == com.pullenti.ner.decree.DecreeChangeKind.CONTAINER && t.isValue("ИЗМЕНЕНИЕ", null)) {
                        }
                        else 
                            break;
                    }
                    else if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false) != null) {
                    }
                    else if ((t == tt0 && ltt.getEndToken().getNext() != null && ltt.getEndToken().getNext().isChar((char)0x1E)) && !ltt.getEndToken().isChar(':')) {
                    }
                    else 
                        break;
                }
                if (ltt.typ == InstrToken1.Types.DIRECTIVE) 
                    break;
                if (t.chars.isLetter() && t != tt0) {
                    if (!t.chars.isAllLower() && !t.chars.isAllUpper()) {
                        if (!(t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) && !(t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t.getPrevious(), false) == null) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                                if (npt != null && npt.getMorph().getCase().isGenitive()) {
                                }
                                else 
                                    break;
                            }
                        }
                    }
                }
                boolean hasWords = false;
                for (com.pullenti.ner.Token ttt = ltt.getBeginToken(); ttt != null; ttt = ttt.getNext()) {
                    if (ttt.getBeginChar() > ltt.getEndChar()) 
                        break;
                    if (ttt.chars.isCyrillicLetter()) {
                        hasWords = true;
                        break;
                    }
                    com.pullenti.ner.Referent r = ttt.getReferent();
                    if ((r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.decree.DecreeChangeReferent)) {
                        hasWords = true;
                        break;
                    }
                }
                if (!hasWords) 
                    break;
                FragToken eds = _createEditions(t);
                if (eds != null) {
                    if (t != tt0) 
                        break;
                    title.children.add(eds);
                    t1 = (t = title.setEndToken(eds.getEndToken()));
                    tt0 = t.getNext();
                    continue;
                }
                t1 = (t = ltt.getEndToken());
            }
            else 
                t1 = t;
        }
        String val = (t1 != null && tt0 != null ? FragToken.getRestoredName(tt0, t1, false) : null);
        if (val != null) {
            if (nT0 != null) 
                tt0 = nT0;
            title.children.add(_new1546(tt0, t1, com.pullenti.ner.instrument.InstrumentKind.NAME, val.trim()));
            title.setEndToken(t1);
            title.name = val;
        }
        while (title.getEndToken().getNext() != null) {
            FragToken eds = _createEditions(title.getEndToken().getNext());
            if (eds != null) {
                title.children.add(eds);
                title.setEndToken(eds.getEndToken());
                continue;
            }
            FragToken appr = _createApproved(title.getEndToken().getNext());
            if (appr != null) {
                title.children.add(appr);
                title.setEndToken(appr.getEndToken());
                continue;
            }
            break;
        }
        if (isApp) {
            if (doc.findSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_APPENDIX, null, true) == null) 
                doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_APPENDIX, "", false, 0);
            for (FragToken ch : title.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE) {
                    for (com.pullenti.ner.Token tt = ch.getBeginToken(); tt != null && tt.getEndChar() <= ch.getEndChar(); tt = tt.getNext()) {
                        if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                            for (com.pullenti.ner.Slot s : tt.getReferent().getSlots()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.decree.DecreeReferent.ATTR_TYPE)) 
                                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_TYPE, s.getValue(), false, 0);
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.decree.DecreeReferent.ATTR_NUMBER)) 
                                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_REGNUMBER, s.getValue(), false, 0);
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.decree.DecreeReferent.ATTR_DATE)) 
                                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_DATE, s.getValue(), false, 0);
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.decree.DecreeReferent.ATTR_SOURCE)) 
                                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, s.getValue(), false, 0);
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.decree.DecreeReferent.ATTR_GEO)) 
                                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO, s.getValue(), false, 0);
                            }
                            break;
                        }
                        com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
                        if (dt != null) {
                            if (_addTitleAttr(doc, null, dt)) 
                                tt = dt.getEndToken();
                        }
                    }
                    break;
                }
            }
        }
        if (title.children.size() == 0 && title.getEndToken() == title.getBeginToken()) 
            return null;
        for (t1 = title.getEndToken().getNext(); t1 != null; t1 = t1.getNext()) {
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t1, null, false);
            if (dt != null) {
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                    _addTitleAttr(null, title, dt);
                    t1 = title.setEndToken(dt.getEndToken());
                    continue;
                }
            }
            break;
        }
        while (title.getEndToken().getNext() != null) {
            if (title.getEndToken().getNext().isTableControlChar() && ((!title.getEndToken().getNext().isNewlineBefore() || title.getEndToken().getNext().isNewlineAfter() || ((title.getEndToken().getNext().getNext() != null && title.getEndToken().getNext().getNext().isChar((char)0x1F)))))) 
                title.setEndToken(title.getEndToken().getNext());
            else 
                break;
        }
        return title;
    }

    private static boolean _isStartOfBody(com.pullenti.ner.Token t, boolean isAppTitle) {
        if (t == null || !t.isNewlineBefore()) 
            return false;
        if (!isAppTitle) {
            com.pullenti.ner.core.internal.BlockTitleToken bl = com.pullenti.ner.core.internal.BlockTitleToken.tryAttach(t, false, null);
            if (bl != null) {
                if (bl.typ != com.pullenti.ner.core.internal.BlkTyps.UNDEFINED && bl.typ != com.pullenti.ner.core.internal.BlkTyps.LITERATURE) 
                    return true;
            }
        }
        com.pullenti.ner.mail.internal.MailLine li = com.pullenti.ner.mail.internal.MailLine.parse(t, 0, 0);
        if (li != null) {
            if (li.typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO) 
                return true;
        }
        InstrToken1 it1 = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
        if (it1 != null) {
            if (it1.typ == InstrToken1.Types.INDEX) 
                return true;
        }
        boolean ok = false;
        if (t.isValue("ВВЕДЕНИЕ", "ВВЕДЕННЯ") || t.isValue("АННОТАЦИЯ", "АНОТАЦІЯ") || t.isValue("ПРЕДИСЛОВИЕ", "ПЕРЕДМОВА")) 
            ok = true;
        else if (t.isValue("ОБЩИЙ", "ЗАГАЛЬНИЙ") && t.getNext() != null && t.getNext().isValue("ПОЛОЖЕНИЕ", "ПОЛОЖЕННЯ")) {
            t = t.getNext();
            ok = true;
        }
        else if ((t.getNext() != null && t.getNext().chars.isAllLower() && t.getMorph()._getClass().isPreposition()) && ((t.getNext().isValue("СВЯЗЬ", "ЗВЯЗОК") || t.getNext().isValue("ЦЕЛЬ", "МЕТА") || t.getNext().isValue("СООТВЕТСТВИЕ", "ВІДПОВІДНІСТЬ")))) 
            return true;
        if (ok) {
            com.pullenti.ner.Token t1 = t.getNext();
            if (t1 != null && t1.isChar(':')) 
                t1 = t1.getNext();
            if (t1 == null || t1.isNewlineBefore()) 
                return true;
            return false;
        }
        InstrToken1 it = InstrToken1.parse(t, false, null, 0, null, false, 0, false, false);
        if (it != null) {
            if (it.getTypContainerRank() > 0 || it.typ == InstrToken1.Types.DIRECTIVE) {
                if (t.isValue("ЧАСТЬ", "ЧАСТИНА") && it.numbers.size() == 1) {
                    if (_createApproved(it.getEndToken().getNext()) != null) 
                        return false;
                }
                return true;
            }
            if (it.numbers.size() > 0) {
                if (it.numbers.size() > 1 || it.numSuffix != null) 
                    return true;
            }
        }
        if ((t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) && t.getNext() != null) {
            if (t.getNext().isValue("СОСТАВ", "СКЛАД")) 
                return true;
            if (t.getNext().isValue("В", "У") && t.getNext().getNext() != null && t.getNext().getNext().isValue("СОСТАВ", "СКЛАД")) 
                return true;
        }
        return false;
    }

    private static boolean _addTitleAttr(com.pullenti.ner.instrument.InstrumentReferent doc, FragToken title, com.pullenti.ner.decree.internal.DecreeToken dt) {
        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
            if (doc != null) {
                if (doc.getTyp() != null && com.pullenti.unisharp.Utils.stringsNe(dt.value, doc.getTyp())) {
                    if (com.pullenti.unisharp.Utils.stringsNe(doc.getTyp(), "ПРОЕКТ")) 
                        return false;
                    if ((dt.value.indexOf("ЗАКОН") >= 0)) 
                        doc.setTyp("ПРОЕКТ ЗАКОНА");
                    else 
                        return false;
                }
                else 
                    doc.setTyp(dt.value);
                if (dt.fullValue != null && com.pullenti.unisharp.Utils.stringsNe(dt.fullValue, dt.value) && doc.getName() == null) 
                    doc.setName(dt.fullValue);
            }
            if (title != null) 
                title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TYP, (dt.fullValue != null ? dt.fullValue : dt.value)));
        }
        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
            if (dt.isDelo()) {
                if (doc != null) {
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_CASENUMBER, dt.value, false, 0);
                    if (com.pullenti.unisharp.Utils.stringsEq(doc.getRegNumber(), dt.value)) 
                        doc.setRegNumber(null);
                }
                if (title != null) 
                    title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CASENUMBER, dt.value));
            }
            else {
                if (com.pullenti.unisharp.Utils.stringsNe(dt.value, "?") && doc != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(doc.getStringValue(com.pullenti.ner.instrument.InstrumentReferent.ATTR_CASENUMBER), dt.value)) {
                    }
                    else 
                        doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NUMBER, dt.value, false, 0);
                }
                if (title != null) 
                    title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NUMBER, dt.value));
                if (doc != null && doc.getTyp() == null && dt.value != null) {
                    if (com.pullenti.morph.LanguageHelper.endsWith(dt.value, "ФКЗ")) 
                        doc.setTyp("ФЕДЕРАЛЬНЫЙ КОНСТИТУЦИОННЫЙ ЗАКОН");
                    else if (com.pullenti.morph.LanguageHelper.endsWith(dt.value, "ФЗ")) 
                        doc.setTyp("ФЕДЕРАЛЬНЫЙ ЗАКОН");
                }
            }
        }
        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) {
            if (doc != null) 
                doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, dt.value, false, 0);
            if (title != null) 
                title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, dt.value));
        }
        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
            if (doc == null || doc.addDate(dt)) {
                if (title != null) 
                    title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DATE, dt));
            }
        }
        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
            if (title != null) 
                title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.PLACE, dt));
            if (doc != null && dt.ref != null) {
                String _geo = doc.getStringValue(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO);
                if (com.pullenti.unisharp.Utils.stringsEq(_geo, "Россия")) {
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO, null, true, 0);
                    _geo = null;
                }
                if (_geo == null) 
                    doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO, dt.ref.referent.toString(), false, 0);
            }
        }
        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) {
            if (title != null) 
                title.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG ? com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION : com.pullenti.ner.instrument.InstrumentKind.INITIATOR), dt));
            if (doc != null) {
                if (dt.ref != null) {
                    doc.addSlot(com.pullenti.ner.decree.DecreeReferent.ATTR_SOURCE, dt.ref.referent, false, 0).setTag(dt.getSourceText());
                    if (dt.ref.referent instanceof com.pullenti.ner.person.PersonPropertyReferent) 
                        doc.addExtReferent(dt.ref);
                }
                else 
                    doc.addSlot(com.pullenti.ner.decree.DecreeReferent.ATTR_SOURCE, com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(dt.value), false, 0).setTag(dt.getSourceText());
            }
        }
        else 
            return false;
        return true;
    }

    public FragToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        for (com.pullenti.ner.Token t = getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isChar((char)7) || t.isChar((char)0x1F)) 
                setEndToken(t);
            else 
                break;
        }
    }

    public com.pullenti.ner.instrument.InstrumentKind kind = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;

    public com.pullenti.ner.instrument.InstrumentKind kind2 = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;

    public Object value;

    public String name;

    public int number;

    public int minNumber;

    public int subNumber;

    public int subNumber2;

    public int subNumber3;

    public java.util.ArrayList<com.pullenti.ner.Referent> referents = null;

    public boolean isExpired;

    public String getNumberString() {
        if (subNumber == 0) 
            return ((Integer)number).toString();
        StringBuilder tmp = new StringBuilder();
        tmp.append(number).append(".").append(subNumber);
        if (subNumber2 > 0) 
            tmp.append(".").append(subNumber2);
        if (subNumber3 > 0) 
            tmp.append(".").append(subNumber3);
        return tmp.toString();
    }


    public java.util.ArrayList<FragToken> children = new java.util.ArrayList<FragToken>();

    public void sortChildren() {
        for (int k = 0; k < children.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (children.size() - 1); i++) {
                if (children.get(i).compareTo(children.get(i + 1)) > 0) {
                    ch = true;
                    FragToken v = children.get(i);
                    com.pullenti.unisharp.Utils.putArrayValue(children, i, children.get(i + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(children, i + 1, v);
                }
            }
            if (!ch) 
                break;
        }
    }

    public FragToken findChild(com.pullenti.ner.instrument.InstrumentKind _kind) {
        for (FragToken ch : children) {
            if (ch.kind == _kind) 
                return ch;
        }
        return null;
    }

    public int compareTo(FragToken other) {
        if (getBeginChar() < other.getBeginChar()) 
            return -1;
        if (getBeginChar() > other.getBeginChar()) 
            return 1;
        if (getEndChar() < other.getEndChar()) 
            return -1;
        if (getEndChar() > other.getEndChar()) 
            return 1;
        return 0;
    }

    public int getMinChildNumber() {
        for (FragToken ch : children) {
            if (ch.number > 0) {
                if (ch.number != 1) {
                    if (ch.itok != null && ch.itok.numTyp == NumberTypes.LETTER) 
                        return 0;
                }
                return ch.number;
            }
        }
        return 0;
    }


    public int getMaxChildNumber() {
        int max = 0;
        for (FragToken ch : children) {
            if (ch.number > max) 
                max = ch.number;
        }
        return max;
    }


    public boolean getDefVal() {
        return false;
    }

    public boolean setDefVal(boolean _value) {
        String str = this.getSourceText();
        while (str.length() > 0) {
            char last = str.charAt(str.length() - 1);
            char first = str.charAt(0);
            if ((((int)last) == 0x1E || ((int)last) == 0x1F || ((int)last) == 7) || com.pullenti.unisharp.Utils.isWhitespace(last)) 
                str = str.substring(0, 0 + str.length() - 1);
            else if ((((int)first) == 0x1E || ((int)first) == 0x1F || ((int)first) == 7) || com.pullenti.unisharp.Utils.isWhitespace(first)) 
                str = str.substring(1);
            else 
                break;
        }
        value = str;
        return _value;
    }


    public boolean getDefVal2() {
        return false;
    }

    public boolean setDefVal2(boolean _value) {
        value = getRestoredNameMT(this, false);
        return _value;
    }


    public static String getRestoredNameMT(com.pullenti.ner.MetaToken mt, boolean indexItem) {
        return getRestoredName(mt.getBeginToken(), mt.getEndToken(), indexItem);
    }

    public static String getRestoredName(com.pullenti.ner.Token b, com.pullenti.ner.Token e, boolean indexItem) {
        com.pullenti.ner.Token e0 = e;
        for (; e != null && e.getBeginChar() > b.getEndChar(); e = e.getPrevious()) {
            if (e.isCharOf("*<") || e.isTableControlChar()) {
            }
            else if ((e.isCharOf(">") && (e.getPrevious() instanceof com.pullenti.ner.NumberToken) && e.getPrevious().getPrevious() != null) && e.getPrevious().getPrevious().isChar('<')) 
                e = e.getPrevious();
            else if (e.isCharOf(">") && e.getPrevious().isChar('*')) {
            }
            else if ((e instanceof com.pullenti.ner.NumberToken) && ((e == e0 || e.getNext().isTableControlChar())) && indexItem) {
            }
            else if (((e.isChar('.') || e.isHiphen())) && indexItem) {
            }
            else 
                break;
        }
        com.pullenti.ner.Token b0 = b;
        for (; b != null && b.getEndChar() <= e.getEndChar(); b = b.getNext()) {
            if (b.isTableControlChar()) {
            }
            else {
                b0 = b;
                break;
            }
        }
        String str = com.pullenti.ner.core.MiscHelper.getTextValue(b0, e, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.RESTOREREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(str)) {
            if (Character.isLowerCase(str.charAt(0))) 
                str = (String.valueOf(Character.toUpperCase(str.charAt(0))) + str.substring(1));
        }
        return str;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (kind != com.pullenti.ner.instrument.InstrumentKind.UNDEFINED) {
            tmp.append(kind.toString()).append(":");
            if (kind2 != com.pullenti.ner.instrument.InstrumentKind.UNDEFINED) 
                tmp.append(" (").append(kind2.toString()).append("):");
        }
        else if (itok != null) 
            tmp.append(itok).append(" ");
        if (number > 0) {
            if (minNumber > 0) 
                tmp.append(" Num=[").append(minNumber).append("..").append(number).append("]");
            else 
                tmp.append(" Num=").append(number);
            if (subNumber > 0) 
                tmp.append(".").append(subNumber);
            if (subNumber2 > 0) 
                tmp.append(".").append(subNumber2);
            if (subNumber3 > 0) 
                tmp.append(".").append(subNumber3);
        }
        if (isExpired) 
            tmp.append(" Expired");
        if (children.size() > 0) 
            tmp.append(" ChCount=").append(children.size());
        if (name != null) 
            tmp.append(" Nam='").append(name).append("'");
        if (value != null) 
            tmp.append(" Val='").append(value.toString()).append("'");
        if (tmp.length() == 0) 
            tmp.append(this.getSourceText());
        return tmp.toString();
    }

    public com.pullenti.ner.instrument.InstrumentBlockReferent createReferent(com.pullenti.ner.core.AnalyzerData ad) {
        return this._CreateReferent(ad, this);
    }

    public com.pullenti.ner.instrument.InstrumentBlockReferent _CreateReferent(com.pullenti.ner.core.AnalyzerData ad, FragToken bas) {
        com.pullenti.ner.instrument.InstrumentBlockReferent res = null;
        if (m_Doc != null) 
            res = m_Doc;
        else {
            res = new com.pullenti.ner.instrument.InstrumentBlockReferent(null);
            res.setKind(kind);
            res.setKind2(kind2);
            if (number > 0) 
                res.setNumber(number);
            if (minNumber > 0) 
                res.setMinNumber(minNumber);
            if (subNumber > 0) 
                res.setSubNumber(subNumber);
            if (subNumber2 > 0) 
                res.setSubNumber2(subNumber2);
            if (subNumber3 > 0) 
                res.setSubNumber3(subNumber3);
            if (isExpired) 
                res.setExpired(true);
            if (name != null && kind != com.pullenti.ner.instrument.InstrumentKind.HEAD) {
                com.pullenti.ner.Slot s = res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, name.toUpperCase(), false, 0);
                s.setTag(name);
            }
            if (value != null && kind != com.pullenti.ner.instrument.InstrumentKind.CONTACT) {
                if (value instanceof String) 
                    res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_VALUE, value, false, 0);
                else if (value instanceof com.pullenti.ner.Referent) 
                    res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, value, false, 0);
                else if (value instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.ReferentToken.class)).referent;
                    ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.ReferentToken.class)).saveToLocalOntology();
                    res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.ReferentToken.class)).referent, false, 0);
                    res.addExtReferent((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.ReferentToken.class));
                    com.pullenti.ner.Slot s = bas.m_Doc.findSlot(null, r, true);
                    if (s != null) 
                        s.setValue(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.ReferentToken.class)).referent);
                }
                else if (value instanceof com.pullenti.ner.decree.internal.DecreeToken) {
                    com.pullenti.ner.decree.internal.DecreeToken dt = (com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.unisharp.Utils.cast(value, com.pullenti.ner.decree.internal.DecreeToken.class);
                    if (dt.ref instanceof com.pullenti.ner.ReferentToken) {
                        com.pullenti.ner.Referent r = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt.ref, com.pullenti.ner.ReferentToken.class)).referent;
                        ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt.ref, com.pullenti.ner.ReferentToken.class)).saveToLocalOntology();
                        res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt.ref, com.pullenti.ner.ReferentToken.class)).referent, false, 0);
                        res.addExtReferent((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt.ref, com.pullenti.ner.ReferentToken.class));
                        com.pullenti.ner.Slot s = bas.m_Doc.findSlot(null, r, true);
                        if (s != null) 
                            s.setValue(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt.ref, com.pullenti.ner.ReferentToken.class)).referent);
                    }
                    else if (dt.value != null) 
                        res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_VALUE, dt.value, false, 0);
                }
            }
            if (referents != null) {
                for (com.pullenti.ner.Referent r : referents) {
                    res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, r, false, 0);
                }
            }
            if (children.size() == 0) {
                for (com.pullenti.ner.Token t = getBeginToken(); t != null && (t.getBeginChar() < getEndChar()); t = t.getNext()) {
                    if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) 
                        res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, t.getReferent(), false, 0);
                    if (t.getEndChar() > getEndChar()) 
                        break;
                }
            }
        }
        if (ad != null) {
            if (ad.getReferents().size() > 200000) 
                return null;
            ad.getReferents().add(res);
            res.addOccurenceOfRefTok(new com.pullenti.ner.ReferentToken(res, getBeginToken(), getEndToken(), null));
        }
        for (FragToken ch : children) {
            com.pullenti.ner.instrument.InstrumentBlockReferent ich = ch._CreateReferent(ad, bas);
            if (ich != null) 
                res.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_CHILD, ich, false, 0);
        }
        return res;
    }

    public void fillByContentChildren() {
        this.sortChildren();
        if (children.size() == 0) {
            children.add(_new1494(getBeginToken(), getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
            return;
        }
        if (getBeginChar() < children.get(0).getBeginChar()) 
            children.add(0, _new1494(getBeginToken(), children.get(0).getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
        for (int i = 0; i < (children.size() - 1); i++) {
            if (children.get(i).getEndToken().getNext() != children.get(i + 1).getBeginToken() && (children.get(i).getEndToken().getNext().getEndChar() < children.get(i + 1).getBeginChar())) 
                children.add(i + 1, _new1494(children.get(i).getEndToken().getNext(), children.get(i + 1).getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
        }
        if (children.get(children.size() - 1).getEndChar() < getEndChar()) 
            children.add(_new1494(children.get(children.size() - 1).getEndToken().getNext(), getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
    }

    public com.pullenti.ner.instrument.InstrumentReferent m_Doc;

    public InstrToken1 itok;

    public static FragToken createDocument(com.pullenti.ner.Token t, int maxChar, com.pullenti.ner.instrument.InstrumentKind rootKind) {
        if (t == null) 
            return null;
        while ((t instanceof com.pullenti.ner.TextToken) && t.getNext() != null) {
            if (t.isTableControlChar() || !t.chars.isLetter()) 
                t = t.getNext();
            else 
                break;
        }
        if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
            com.pullenti.ner.decree.DecreeReferent dec0 = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (dec0.getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                t = t.getNext();
            else 
                t = t.kit.debedToken(t);
        }
        else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) {
            com.pullenti.ner.decree.DecreePartReferent dp = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
            if ((dp.getClause() != null || dp.getItem() != null || dp.getSubItem() != null) || dp.getIndention() != null) {
            }
            else 
                t = t.kit.debedToken(t);
        }
        if (t == null) 
            return null;
        FragToken res = __createActionQuestion(t, maxChar);
        if (res != null) 
            return res;
        res = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.DOCUMENT);
        res.m_Doc = new com.pullenti.ner.instrument.InstrumentReferent();
        boolean isApp = false;
        int cou = 0;
        for (com.pullenti.ner.Token ttt = t; ttt != null && (cou < 5); ttt = ttt.getNext(),cou++) {
            if (ttt.isNewlineBefore() || ttt.getPrevious().isTableControlChar()) {
                if (ttt.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
                    isApp = true;
                    break;
                }
                if (ttt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) 
                    break;
                com.pullenti.ner.decree.internal.DecreeToken dtt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt, null, false);
                if (dtt != null && ((dtt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dtt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dtt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR))) 
                    break;
                if (ttt instanceof com.pullenti.ner.NumberToken) 
                    break;
            }
        }
        FragToken head = (isApp || maxChar > 0 ? null : createDocTitle(t, res.m_Doc));
        com.pullenti.ner.decree.DecreeKind headKind = com.pullenti.ner.decree.DecreeKind.UNDEFINED;
        if (head != null && (head.tag instanceof com.pullenti.ner.decree.DecreeKind)) 
            headKind = (com.pullenti.ner.decree.DecreeKind)head.tag;
        FragToken head1 = null;
        com.pullenti.ner.instrument.InstrumentReferent appDoc = new com.pullenti.ner.instrument.InstrumentReferent();
        if (maxChar > 0 && !isApp) {
        }
        else 
            head1 = createAppendixTitle(t, res, appDoc, true, true);
        if (head1 != null) {
            if (head1.tag instanceof FragToken) {
                res.m_Doc = appDoc;
                res.children.add(head1);
                res.children.add((FragToken)com.pullenti.unisharp.Utils.cast(head1.tag, FragToken.class));
                res.setEndToken(((FragToken)com.pullenti.unisharp.Utils.cast(head1.tag, FragToken.class)).getEndToken());
                return res;
            }
            boolean ee = false;
            if (head == null) 
                ee = true;
            else if (head1.getEndChar() > head.getEndChar() && ((com.pullenti.unisharp.Utils.stringsEq(res.m_Doc.getTyp(), "ПРИЛОЖЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(res.m_Doc.getTyp(), "ДОДАТОК")))) 
                ee = true;
            else if (head1.children.size() > head.children.size()) 
                ee = true;
            if (ee) {
                head = head1;
                res.m_Doc = appDoc;
            }
        }
        if (head != null) {
            if (maxChar == 0) 
                _createJusticeParticipants(head, res.m_Doc);
            head.sortChildren();
            res.children.add(head);
            res.setEndToken(head.getEndToken());
            if (head.getBeginChar() < res.getBeginChar()) 
                res.setBeginToken(head.getBeginToken());
            t = res.getEndToken().getNext();
        }
        if (t == null) {
            if (head != null && head.children.size() > 2) 
                return res;
            return null;
        }
        boolean isContract = false;
        if (res.m_Doc.getTyp() != null) {
            if ((res.m_Doc.getTyp().indexOf("ДОГОВОР") >= 0) || (res.m_Doc.getTyp().indexOf("ДОГОВІР") >= 0) || (res.m_Doc.getTyp().indexOf("КОНТРАКТ") >= 0)) 
                isContract = true;
        }
        com.pullenti.ner.Token t0 = t;
        java.util.ArrayList<InstrToken> li = InstrToken.parseList(t, maxChar);
        if (li == null || li.size() == 0) 
            return null;
        int i;
        if (isApp) {
            for (i = 0; i < li.size(); i++) {
                if (li.get(i).typ == ILTypes.APPROVED) 
                    li.get(i).typ = ILTypes.UNDEFINED;
                else if (li.get(i).typ == ILTypes.APPENDIX && com.pullenti.unisharp.Utils.stringsNe(li.get(i).value, "ПРИЛОЖЕНИЕ") && com.pullenti.unisharp.Utils.stringsNe(li.get(i).value, "ДОДАТОК")) 
                    li.get(i).typ = ILTypes.UNDEFINED;
            }
        }
        for (i = 0; i < (li.size() - 1); i++) {
            if (li.get(i).typ == ILTypes.APPENDIX) {
                if (i > 0 && li.get(i - 1).typ == ILTypes.PERSON) 
                    break;
                InstrToken1 num1 = InstrToken1.parse(li.get(i).getBeginToken(), true, null, 0, null, false, 0, false, false);
                int maxNum = i + 7;
                int i0 = i;
                for (int j = i + 1; (j < li.size()) && (j < maxNum); j++) {
                    if (li.get(j).typ == ILTypes.APPENDIX) {
                        if (com.pullenti.unisharp.Utils.stringsNe(li.get(j).value, li.get(i).value)) {
                            if (com.pullenti.unisharp.Utils.stringsEq(li.get(i).value, "ПРИЛОЖЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(li.get(i).value, "ДОДАТОК")) 
                                li.get(j).typ = ILTypes.UNDEFINED;
                            else if (com.pullenti.unisharp.Utils.stringsEq(li.get(j).value, "ПРИЛОЖЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(li.get(j).value, "ДОДАТОК")) {
                                li.get(i).typ = ILTypes.UNDEFINED;
                                break;
                            }
                        }
                        else {
                            int le = li.get(j).getBeginChar() - li.get(i0).getBeginChar();
                            if (le > 400) 
                                break;
                            i = j;
                            InstrToken1 num2 = InstrToken1.parse(li.get(j).getBeginToken(), true, null, 0, null, false, 0, false, false);
                            int d = NumberingHelper.calcDelta(num1, num2, true);
                            if (d == 1) {
                                li.get(i0).typ = ILTypes.UNDEFINED;
                                li.get(j).typ = ILTypes.UNDEFINED;
                                i0 = j;
                            }
                            num1 = num2;
                            maxNum = j + 7;
                        }
                    }
                    else if (li.get(j).typ == ILTypes.APPROVED) 
                        li.get(j).typ = ILTypes.UNDEFINED;
                }
            }
        }
        boolean hasApp = false;
        for (i = 0; i < li.size(); i++) {
            if (li.get(i).typ == ILTypes.APPENDIX || li.get(i).typ == ILTypes.APPROVED) {
                if (li.get(i).typ == ILTypes.APPROVED) {
                    hasApp = true;
                    if (i == 0) 
                        continue;
                    if (!li.get(i).isNewlineAfter()) {
                        if (((i + 1) < li.size()) && li.get(i + 1).isNewlineAfter() && li.get(i + 1).typ == ILTypes.UNDEFINED) {
                        }
                        else 
                            continue;
                    }
                }
                if (li.get(i).ref instanceof com.pullenti.ner.decree.DecreeReferent) {
                    com.pullenti.ner.decree.DecreeReferent drApp = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(li.get(i).ref, com.pullenti.ner.decree.DecreeReferent.class);
                    if (com.pullenti.unisharp.Utils.stringsNe(drApp.getTyp(), res.m_Doc.getTyp())) 
                        continue;
                    if (drApp.getNumber() != null && res.m_Doc.getRegNumber() != null) {
                        if (com.pullenti.unisharp.Utils.stringsNe(drApp.getNumber(), res.m_Doc.getRegNumber())) 
                            continue;
                    }
                }
                break;
            }
        }
        int i1 = i;
        if (maxChar == 0 && i1 == li.size()) {
            for (i = 0; i < li.size(); i++) {
                if (li.get(i).typ == ILTypes.PERSON && li.get(i).isNewlineBefore() && ((!li.get(i).getHasTableChars() || li.get(i).isPurePerson()))) {
                    int j;
                    boolean dat = false;
                    boolean num = false;
                    boolean _geo = false;
                    int pers = 0;
                    for (j = i + 1; j < li.size(); j++) {
                        if (li.get(j).typ == ILTypes.GEO) 
                            _geo = true;
                        else if (li.get(j).typ == ILTypes.REGNUMBER) 
                            num = true;
                        else if (li.get(j).typ == ILTypes.DATE) 
                            dat = true;
                        else if (li.get(j).typ == ILTypes.PERSON && li.get(j).isPurePerson()) {
                            if (((li.get(j).isNewlineBefore() || ((li.get(j - 1).typ == ILTypes.PERSON || li.get(j - 1).typ == ILTypes.DATE)))) && ((li.get(j).isNewlineAfter() || ((((j + 1) < li.size()) && ((li.get(j + 1).typ == ILTypes.PERSON || li.get(j + 1).typ == ILTypes.DATE))))))) 
                                pers++;
                            else 
                                break;
                        }
                        else if (li.get(j).noWords) {
                        }
                        else 
                            break;
                    }
                    int k = pers;
                    if (dat) 
                        k++;
                    if (num) 
                        k++;
                    if (_geo) 
                        k++;
                    if ((j < li.size()) && ((li.get(j).typ == ILTypes.APPENDIX || li.get(j).typ == ILTypes.APPROVED))) 
                        k += 2;
                    else if ((li.get(i).isPurePerson() && li.get(i).getBeginToken().getPrevious() != null && li.get(i).getBeginToken().getPrevious().isChar('.')) && li.get(i).isNewlineAfter()) {
                        InstrToken1 itt = InstrToken1.parse(li.get(i).getEndToken().getNext(), true, null, 0, null, false, 0, false, false);
                        if (itt != null && itt.numbers.size() > 0 && li.get(i + 1).typ == ILTypes.UNDEFINED) {
                        }
                        else 
                            k += 2;
                    }
                    if (k >= 2) {
                        i = j;
                        if ((i < li.size()) && ((li.get(i).typ == ILTypes.UNDEFINED || li.get(i).typ == ILTypes.TYP))) 
                            li.get(i).typ = ILTypes.APPROVED;
                        if ((i > (i1 + 10) && (i1 < li.size()) && li.get(i1).typ == ILTypes.APPENDIX) && li.get(i1).getWhitespacesBeforeCount() > 15) {
                        }
                        else 
                            i1 = i;
                        break;
                    }
                }
            }
        }
        if ((maxChar == 0 && (i1 < li.size()) && (i1 + 10) > li.size()) && !hasApp && ((li.get(li.size() - 1).getEndChar() - li.get(i1).getEndChar()) < 200)) {
            for (int ii = li.size() - 1; ii > i; ii--) {
                if (li.get(ii).typ == ILTypes.PERSON || li.get(ii).typ == ILTypes.DATE || ((li.get(ii).typ == ILTypes.REGNUMBER && li.get(ii).isNewlineBefore()))) {
                    i1 = ii + 1;
                    break;
                }
            }
        }
        int cMax = i1 - 1;
        FragToken tail = null;
        java.util.ArrayList<com.pullenti.ner.Referent> persList = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (i = i1 - 1; i > 0; i--) {
            if (maxChar > 0) 
                break;
            InstrToken lii = li.get(i);
            if (lii.getHasTableChars()) {
                if ((i < (i1 - 1)) && lii.typ != ILTypes.PERSON) 
                    break;
                if (isContract) 
                    break;
            }
            if ((lii.typ == ILTypes.PERSON || lii.typ == ILTypes.REGNUMBER || lii.typ == ILTypes.DATE) || lii.typ == ILTypes.GEO) {
                if (persList.size() > 0) {
                    if (lii.typ != ILTypes.PERSON && lii.typ != ILTypes.DATE) 
                        break;
                    if (!lii.isNewlineBefore() && !lii.isNewlineAfter() && !lii.getHasTableChars()) {
                        if (!lii.isNewlineBefore() && i > 0 && li.get(i - 1).typ == ILTypes.PERSON) {
                        }
                        else 
                            break;
                    }
                }
                if (lii.typ == ILTypes.PERSON && (lii.ref instanceof com.pullenti.ner.ReferentToken)) {
                    if (persList.contains(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(lii.ref, com.pullenti.ner.ReferentToken.class)).referent)) {
                        if (!lii.isNewlineBefore()) 
                            break;
                    }
                }
                if (!lii.isNewlineBefore() && !lii.getBeginToken().isTableControlChar() && ((lii.typ == ILTypes.GEO || li.get(i).typ == ILTypes.PERSON))) {
                    if (i > 0 && ((li.get(i - 1).typ == ILTypes.UNDEFINED && !li.get(i - 1).getEndToken().isTableControlChar()))) 
                        break;
                    if (lii.getEndToken().isCharOf(";.")) 
                        break;
                    if (!lii.isNewlineAfter()) {
                        if (lii.getEndToken().getNext() != null && !lii.getEndToken().getNext().isTableControlChar()) 
                            break;
                    }
                }
                if (tail == null) {
                    tail = _new1494(li.get(i).getBeginToken(), li.get(i1 - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TAIL);
                    if ((i1 - 1) > i) {
                    }
                }
                tail.setBeginToken(lii.getBeginToken());
                cMax = i - 1;
                FragToken fr = new FragToken(li.get(i).getBeginToken(), li.get(i).getEndToken());
                tail.children.add(0, fr);
                if (li.get(i).typ == ILTypes.PERSON) {
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.SIGNER;
                    if (li.get(i).ref instanceof com.pullenti.ner.ReferentToken) {
                        res.m_Doc.addSlot(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SIGNER, ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(li.get(i).ref, com.pullenti.ner.ReferentToken.class)).referent, false, 0);
                        res.m_Doc.addExtReferent((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(li.get(i).ref, com.pullenti.ner.ReferentToken.class));
                        fr.value = li.get(i).ref;
                        persList.add(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(li.get(i).ref, com.pullenti.ner.ReferentToken.class)).referent);
                    }
                }
                else if (li.get(i).typ == ILTypes.REGNUMBER) {
                    if (li.get(i).isNewlineBefore()) {
                        if (res.m_Doc.getRegNumber() == null || com.pullenti.unisharp.Utils.stringsEq(res.m_Doc.getRegNumber(), li.get(i).value)) {
                            fr.kind = com.pullenti.ner.instrument.InstrumentKind.NUMBER;
                            fr.value = li.get(i).value;
                            res.m_Doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NUMBER, li.get(i).value, false, 0);
                        }
                    }
                }
                else if (li.get(i).typ == ILTypes.DATE) {
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.DATE;
                    fr.value = li.get(i).value;
                    if (li.get(i).ref != null) {
                        res.m_Doc.addDate(li.get(i).ref);
                        fr.value = li.get(i).ref;
                    }
                    else if (li.get(i).value != null) 
                        res.m_Doc.addDate(li.get(i).value);
                }
                else if (li.get(i).typ == ILTypes.GEO) {
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.PLACE;
                    fr.value = li.get(i).ref;
                }
                if (fr.value == null) 
                    fr.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(fr, com.pullenti.ner.core.GetTextAttr.NO);
            }
            else {
                String ss = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(i).getBeginToken(), li.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
                if (ss == null || ss.length() == 0) 
                    continue;
                if (ss.charAt(ss.length() - 1) == ':') 
                    ss = ss.substring(0, 0 + ss.length() - 1);
                if (li.get(i).isPodpisStoron() && tail != null) {
                    tail.setBeginToken(li.get(i).getBeginToken());
                    tail.children.add(0, _new1546(li.get(i).getBeginToken(), li.get(i).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, ss));
                    cMax = i - 1;
                    break;
                }
                int jj;
                for (jj = 0; jj < ss.length(); jj++) {
                    if (Character.isLetterOrDigit(ss.charAt(jj))) 
                        break;
                }
                if (jj >= ss.length()) 
                    continue;
                if ((ss.length() < 100) && (((i1 - i) < 3))) 
                    continue;
                break;
            }
        }
        if (cMax < 0) {
            if (i1 > 0) 
                return null;
        }
        else {
            FragToken content = _new1494(li.get(0).getBeginToken(), li.get(cMax).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
            res.children.add(content);
            content._analizeContent(res, maxChar > 0, rootKind);
            if (maxChar > 0 && cMax == (li.size() - 1) && head == null) 
                res = content;
        }
        if (tail != null) {
            res.children.add(tail);
            for (; i1 < li.size(); i1++) {
                if (li.get(i1).getBeginToken() == li.get(i1).getEndToken() && (li.get(i1).getBeginToken().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(li.get(i1).getBeginToken().getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) {
                    FragToken ap = _new1494(li.get(i1).getBeginToken(), li.get(i1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.APPROVED);
                    ap.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    ap.referents.add((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(li.get(i1).getBeginToken().getReferent(), com.pullenti.ner.decree.DecreeReferent.class));
                    tail.children.add(ap);
                    tail.setEndToken(li.get(i1).getEndToken());
                }
                else 
                    break;
            }
            if (tail.children.size() > 0 && (tail.children.get(tail.children.size() - 1).getEndChar() < tail.getEndChar())) {
                FragToken unkw = _new1494(tail.children.get(tail.children.size() - 1).getEndToken().getNext(), tail.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
                tail.setEndToken(unkw.getBeginToken().getPrevious());
                res.children.add(unkw);
            }
        }
        boolean isAllApps = isApp;
        FragToken app0 = null;
        for (i = i1; i < li.size(); i++) {
            int j;
            FragToken app = new FragToken(li.get(i).getBeginToken(), li.get(i).getEndToken());
            FragToken title = createAppendixTitle(app.getBeginToken(), app, res.m_Doc, isAllApps, false);
            for (j = i + 1; j < li.size(); j++) {
                if (title != null && li.get(j).getEndChar() <= title.getEndChar()) 
                    continue;
                if (li.get(j).typ == ILTypes.APPENDIX) {
                    if (com.pullenti.unisharp.Utils.stringsEq(li.get(j).value, li.get(i1).value)) 
                        break;
                    if (li.get(j).value != null && li.get(i1).value == null) 
                        break;
                    continue;
                }
                else if (li.get(j).typ == ILTypes.APPROVED) {
                    if ((li.get(j).getBeginChar() - li.get(i).getEndChar()) > 200) 
                        break;
                }
            }
            app.setEndToken(li.get(j - 1).getEndToken());
            tail = null;
            if (li.get(j - 1).typ == ILTypes.PERSON && li.get(j - 1).isNewlineBefore() && li.get(j - 1).isNewlineAfter()) {
                tail = _new1494(li.get(j - 1).getBeginToken(), li.get(j - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TAIL);
                for (int jj = j - 1; jj > i; jj--) {
                    if (li.get(jj).typ != ILTypes.PERSON || !li.get(jj).isNewlineBefore() || !li.get(jj).isNewlineAfter()) 
                        break;
                    else {
                        FragToken fr = _new1494(li.get(jj).getBeginToken(), li.get(jj).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.SIGNER);
                        if (li.get(jj).ref instanceof com.pullenti.ner.ReferentToken) 
                            fr.value = li.get(jj).ref;
                        tail.children.add(0, fr);
                        tail.setBeginToken(fr.getBeginToken());
                        app.setEndToken(tail.getBeginToken().getPrevious());
                    }
                }
            }
            if (li.get(i).typ == ILTypes.APPENDIX || ((((i + 1) < li.size()) && li.get(i + 1).typ == ILTypes.APPENDIX))) 
                app.kind = com.pullenti.ner.instrument.InstrumentKind.APPENDIX;
            else if (app.kind != com.pullenti.ner.instrument.InstrumentKind.APPENDIX) 
                app.kind = com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT;
            if (title == null) {
                boolean ok = true;
                if (app.getLengthChar() < 500) 
                    ok = false;
                else {
                    app._analizeContent(app, false, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
                    if (app.children.size() < 2) 
                        ok = false;
                }
                if (ok) 
                    res.children.add(app);
                else {
                    app.kind = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;
                    res.children.get(res.children.size() - 1).children.add(app);
                    res.children.get(res.children.size() - 1).setEndToken(app.getEndToken());
                }
            }
            else {
                if (isApp && app.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX) {
                    if (res.children.size() > 0) 
                        res.setEndToken(res.children.get(res.children.size() - 1).getEndToken());
                    FragToken res0 = _new1629(res.getBeginToken(), res.getEndToken(), res.m_Doc, com.pullenti.ner.instrument.InstrumentKind.DOCUMENT);
                    res.m_Doc = null;
                    res.kind = com.pullenti.ner.instrument.InstrumentKind.APPENDIX;
                    res0.children.add(0, res);
                    res = res0;
                    isApp = false;
                }
                if ((app0 != null && !isApp && app0.kind == com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT) && app.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX) 
                    app0.children.add(app);
                else 
                    res.children.add(app);
                if (i == i1 && !isApp && app.kind == com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT) 
                    app0 = app;
                if (title.name != null) {
                    app.name = title.name;
                    title.name = null;
                }
                app.children.add(title);
                if (app.getEndChar() < title.getEndChar()) 
                    app.setEndToken(title.getEndToken());
                if (title.getEndToken().getNext() != null) {
                    if (title.getEndToken().getEndChar() < app.getEndToken().getBeginChar()) {
                        FragToken acontent = _new1494(title.getEndToken().getNext(), app.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
                        app.children.add(acontent);
                        acontent._analizeContent(app, false, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
                    }
                    else {
                    }
                }
                if (app.children.size() == 1 && app.kind != com.pullenti.ner.instrument.InstrumentKind.APPENDIX) {
                    app.children.clear();
                    app.kind = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;
                    app.name = null;
                }
            }
            if (tail != null) {
                app.children.add(tail);
                app.setEndToken(tail.getEndToken());
            }
            i = j - 1;
        }
        if (res.children.size() > 0) 
            res.setEndToken(res.children.get(res.children.size() - 1).getEndToken());
        java.util.ArrayList<FragToken> appendixes = new java.util.ArrayList<FragToken>();
        for (FragToken ch : res.children) {
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX) 
                appendixes.add(ch);
        }
        for (i = 1; i < appendixes.size(); i++) {
            int maxCoef = 0;
            int ii = -1;
            for (int j = i - 1; j >= 0; j--) {
                int coef = appendixes.get(i).calcOwnerCoef(appendixes.get(j));
                if (coef > maxCoef) {
                    maxCoef = coef;
                    ii = j;
                }
            }
            if (ii < 0) 
                continue;
            appendixes.get(ii).children.add(appendixes.get(i));
            res.children.remove(appendixes.get(i));
        }
        if (maxChar > 0) 
            return res;
        if (!isContract && headKind != com.pullenti.ner.decree.DecreeKind.STANDARD) {
            for (FragToken ch : res.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX || ch.kind == com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.HEAD) {
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX && res.m_Doc.getName() != null) 
                        continue;
                    FragToken hi = (ch.kind == com.pullenti.ner.instrument.InstrumentKind.HEAD ? ch : ch.findChild(com.pullenti.ner.instrument.InstrumentKind.HEAD));
                    if (hi != null) {
                        hi = hi.findChild(com.pullenti.ner.instrument.InstrumentKind.NAME);
                        if (hi != null && hi.value != null && hi.value.toString().length() > 20) 
                            res.m_Doc.addSlot(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, hi.value, false, 0);
                    }
                }
            }
        }
        if (res.m_Doc.getTyp() == null) {
            for (FragToken ch : res.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX) {
                    FragToken hi = ch.findChild(com.pullenti.ner.instrument.InstrumentKind.HEAD);
                    if (hi != null) 
                        hi = hi.findChild(com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE);
                    if (hi != null) {
                        com.pullenti.ner.Token t1 = hi.getBeginToken();
                        if (t1.isValue("К", "ДО") && t1.getNext() != null) 
                            t1 = t1.getNext();
                        com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                        if (dr != null && com.pullenti.unisharp.Utils.stringsEq(dr.getNumber(), res.m_Doc.getRegNumber())) 
                            res.m_Doc.setTyp(dr.getTyp());
                        else {
                            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t1, null, false);
                            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                                res.m_Doc.setTyp(dt.value);
                        }
                    }
                    break;
                }
            }
        }
        res._createJusticeResolution();
        if (res.m_Doc.getTyp() == null && ((res.m_Doc.getRegNumber() != null || res.m_Doc.getCaseNumber() != null))) {
            if ((res.children.size() > 1 && res.children.get(1).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && res.children.get(1).children.size() > 0) && res.children.get(1).children.get(res.children.get(1).children.size() - 1).kind == com.pullenti.ner.instrument.InstrumentKind.DOCPART) {
                FragToken part = res.children.get(1).children.get(res.children.get(1).children.size() - 1);
                for (FragToken ch : part.children) {
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE && ch.value != null) {
                        res.m_Doc.setTyp((String)com.pullenti.unisharp.Utils.cast(ch.value, String.class));
                        break;
                    }
                }
            }
        }
        return res;
    }

    private static FragToken _createCaseInfo(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (!t.isNewlineBefore()) 
            return null;
        boolean rez = false;
        com.pullenti.ner.Token t1 = null;
        if ((t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) {
            com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
            if (com.pullenti.unisharp.Utils.stringsEq(dpr.getPart(), "резолютивная")) 
                t1 = t;
        }
        else if (t.isValue("РЕЗОЛЮТИВНЫЙ", "РЕЗОЛЮТИВНЫЙ") && t.getNext() != null && t.getNext().isValue("ЧАСТЬ", "ЧАСТИНА")) 
            t1 = t.getNext();
        else if (t.isValue("ПОЛНЫЙ", "ПОВНИЙ") && t.getNext() != null && t.getNext().isValue("ТЕКСТ", null)) 
            t1 = t.getNext();
        if (t1 != null) {
            rez = true;
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t1.getNext(), null, false);
            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                t1 = dt.getEndToken();
        }
        if (!rez) {
            if ((t.isValue("ПОСТАНОВЛЕНИЕ", "ПОСТАНОВА") || t.isValue("РЕШЕНИЕ", "РІШЕННЯ") || t.isValue("ОПРЕДЕЛЕНИЕ", "ВИЗНАЧЕННЯ")) || t.isValue("ПРИГОВОР", "ВИРОК")) {
                if (t.isNewlineAfter() && t.chars.isAllUpper()) 
                    return null;
                t1 = t;
            }
        }
        if (t1 == null) 
            return null;
        if (t1.getNext() != null && t1.getNext().getMorph()._getClass().isPreposition()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1.getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) 
                t1 = npt.getEndToken();
        }
        if (t1.getNext() != null && t1.getNext().getMorph()._getClass().isVerb()) {
        }
        else 
            return null;
        boolean hasDate = false;
        for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                break;
            else {
                t1 = tt;
                if (t1.getReferent() instanceof com.pullenti.ner.date.DateReferent) 
                    hasDate = true;
            }
        }
        if ((!hasDate && t1.getNext() != null && (t1.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) && t1.getNext().isNewlineAfter()) 
            t1 = t1.getNext();
        return _new1494(t, t1, com.pullenti.ner.instrument.InstrumentKind.CASEINFO);
    }

    private static FragToken _createApproved(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        FragToken res = null;
        if (((t instanceof com.pullenti.ner.ReferentToken) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken().isChar('(') && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken().isChar(')')) && ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken().getNext().isValue("ПРОТОКОЛ", null)) {
            res = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
            res.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            res.referents.add(t.getReferent());
            return res;
        }
        com.pullenti.ner.Token tt = InstrToken._checkApproved(t);
        if (tt != null) 
            res = _new1494(t, tt, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
        else if ((t.isValue("ОДОБРИТЬ", "СХВАЛИТИ") || t.isValue("ПРИНЯТЬ", "ПРИЙНЯТИ") || t.isValue("УТВЕРДИТЬ", "ЗАТВЕРДИТИ")) || t.isValue("СОГЛАСОВАТЬ", null)) {
            if (t.getMorph().containsAttr("инф.", null) && t.getMorph().containsAttr("сов.в.", null)) {
            }
            else 
                res = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
        }
        else if ((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ИМЕНЕМ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ІМЕНЕМ")))) 
            res = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.APPROVED);
        if (res == null) 
            return null;
        t = res.getEndToken();
        if (t.getNext() == null) 
            return res;
        if (!t.isNewlineAfter() && com.pullenti.unisharp.Utils.stringsEq(t.getNext().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), res.getBeginToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false))) {
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isNewlineBefore() || com.pullenti.unisharp.Utils.stringsNe(t.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), res.getBeginToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false))) 
                    break;
                else 
                    res.setEndToken(t);
            }
            if (t.getNext() == null) 
                return res;
            com.pullenti.ner.Token tt0 = t.getNext();
            for (t = t.getNext(); t != null; t = t.getNext()) {
                com.pullenti.ner.decree.internal.DecreeToken dtt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                if (dtt != null) {
                    if (dtt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && t != tt0 && t.isNewlineBefore()) 
                        break;
                    res.setEndToken((t = dtt.getEndToken()));
                    continue;
                }
                if (t.getNewlinesBeforeCount() > 1) 
                    break;
                res.setEndToken(t);
            }
            return res;
        }
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isAnd() || t.getMorph()._getClass().isPreposition()) 
                continue;
            if (t.isValue("ВВЕСТИ", null) || t.isValue("ДЕЙСТВИЕ", "ДІЯ")) {
                res.setEndToken(t);
                continue;
            }
            break;
        }
        while (t != null) {
            if (t.isCharOf(":.,") || com.pullenti.ner.core.BracketHelper.isBracket(t, true)) 
                t = t.getNext();
            else 
                break;
        }
        if (t == null) 
            return res;
        java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t, null, 10, false);
        if (dts != null && dts.size() > 0) {
            for (int i = 0; i < dts.size(); i++) {
                com.pullenti.ner.decree.internal.DecreeToken dt = dts.get(i);
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) 
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION, dt));
                else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) 
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.INITIATOR, dt));
                else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DATE, dt));
                else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && i > 0 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NUMBER, dt));
                else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && i == 0) {
                    if (((i + 1) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                        i++;
                        dt = dts.get(i);
                    }
                }
                else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && res.getBeginToken().isValue("ИМЕНЕМ", "ІМЕНЕМ")) 
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.PLACE, dt));
                else 
                    break;
                res.setEndToken(dt.getEndToken());
            }
        }
        else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
            res.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            for (; t != null; t = t.getNext()) {
                if (t.isCommaAnd()) 
                    continue;
                if (t.isChar('.')) {
                    res.setEndToken(t);
                    continue;
                }
                com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                if (dr == null) 
                    break;
                if (res.referents.size() > 0 && t.getNewlinesBeforeCount() > 1) 
                    break;
                res.referents.add(dr);
                res.setEndToken(t);
            }
        }
        else if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
            res.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            for (; t != null; t = t.getNext()) {
                if (t.isCommaAnd()) 
                    continue;
                if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                    res.referents.add(t.getReferent());
                    res.setEndToken(t);
                }
                else 
                    break;
            }
        }
        if (res.children.size() == 0) {
            if (((!res.getBeginToken().isNewlineBefore() && !res.getBeginToken().getPrevious().isTableControlChar())) || ((!res.getEndToken().isNewlineAfter() && !res.getEndToken().getNext().isTableControlChar()))) 
                return null;
        }
        if (res.getEndToken().getNext() != null && (res.getEndToken().getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(res.getEndToken().getNext(), null, false);
            if (dt != null) {
                res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DATE, dt));
                res.setEndToken(dt.getEndToken());
                dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(res.getEndToken().getNext(), null, false);
                if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    res.children.add(_new1546(dt.getBeginToken(), dt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NUMBER, dt));
                    res.setEndToken(dt.getEndToken());
                }
            }
        }
        t = res.getEndToken().getNext();
        if (t != null && t.isComma()) 
            t = t.getNext();
        if (t != null && t.isValue("ПРОТОКОЛ", null)) {
            dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t, null, 10, false);
            if (dts != null && dts.size() > 0) 
                res.setEndToken(dts.get(dts.size() - 1).getEndToken());
            else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) 
                res.setEndToken(t);
        }
        if (!res.isNewlineBefore() && res.getBeginToken().getPrevious() != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getBeginToken().getPrevious(), true, false)) 
            res.setBeginToken(res.getBeginToken().getPrevious());
        return res;
    }

    public static FragToken _createMisc(com.pullenti.ner.Token t) {
        if (t == null || t.getNext() == null) 
            return null;
        if (t.isValue("ФОРМА", null) && t.getNext().isValue("ДОКУМЕНТА", null)) {
            com.pullenti.ner.decree.internal.DecreeToken num = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t.getNext().getNext(), null, false);
            if (num != null && num.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                return _new1494(t, num.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
            if ((t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext().isNewlineAfter()) 
                return _new1494(t, t.getNext().getNext(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        }
        if (t.isValue("С", null) && t.getNext().isValue("ИЗМЕНЕНИЕ", null) && t.getNext().getNext() != null) {
            com.pullenti.ner.Token tt = t.getNext().getNext();
            if (tt.getMorph()._getClass().isPreposition() && tt.getNext() != null) 
                tt = tt.getNext();
            if (tt.getReferent() instanceof com.pullenti.ner.date.DateReferent) {
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                return _new1494(t, tt, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
            }
        }
        com.pullenti.ner.Token t0 = t;
        while ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.getNext() != null) {
            t = t.getNext();
        }
        if (t.isValue("ЗАКАЗ", null)) {
            InstrToken1 itt = InstrToken1.parse(t, false, null, 0, null, false, 0, false, false);
            if (itt != null) 
                return _new1494(t, itt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        }
        int nums = 0;
        int spec = 0;
        int _chars = 0;
        int words = 0;
        for (com.pullenti.ner.Token t1 = t0; t1 != null; t1 = t1.getNext()) {
            com.pullenti.ner.decree.internal.DecreeToken ddd = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t1, null, false);
            if (ddd != null) 
                break;
            if (t1.isTableControlChar()) {
            }
            else if (t1 instanceof com.pullenti.ner.NumberToken) 
                nums++;
            else if (!(t1 instanceof com.pullenti.ner.TextToken) || t1.getLengthChar() > 7) 
                break;
            else if (!t1.chars.isLetter()) 
                spec++;
            else if (t1.getLengthChar() < 3) 
                _chars++;
            else if (t1.getMorphClassInDictionary().isUndefined()) 
                _chars++;
            else 
                words++;
            if (!t1.isNewlineAfter()) 
                continue;
            if ((nums + spec + _chars) <= 1) 
                break;
            if ((nums + spec + _chars) > (words * 3)) {
            }
            else if ((words < 2) && (nums + spec + _chars) > 3) {
            }
            else 
                break;
            return _new1494(t0, t1, com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        }
        if (t.getReferent() instanceof com.pullenti.ner.address.StreetReferent) {
            InstrToken1 lin = InstrToken1.parse(t0, true, null, 0, null, false, 0, false, false);
            if (lin != null && (lin.getLengthChar() < 70)) 
                return _new1494(t0, lin.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTACT);
        }
        if (t0.isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t0, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.isNewlineAfter()) 
                return _new1494(t0, br.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.UNDEFINED);
        }
        return null;
    }

    public static FragToken _createEditions(com.pullenti.ner.Token t) {
        if (t == null || t.getNext() == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        boolean isInBracks = false;
        boolean isInFig = false;
        boolean ok = false;
        if ((t.isNewlineBefore() && t.isValue("С", null) && t.getNext() != null) && t.getNext().isValue("ИЗМЕНЕНИЕ", null)) {
            FragToken eee = _new1494(t, t, com.pullenti.ner.instrument.InstrumentKind.EDITIONS);
            for (t = t.getNext().getNext(); t != null; t = t.getNext()) {
                if (t.isCommaAnd() || (t.getReferent() instanceof com.pullenti.ner.date.DateReferent)) 
                    eee.setEndToken(t);
                else if (t.isValue("ДОПОЛНЕНИЕ", null) || t.isCharOf(":;.") || t.isValue("ОТ", null)) 
                    eee.setEndToken(t);
                else {
                    com.pullenti.ner.date.internal.DateItemToken dd = com.pullenti.ner.date.internal.DateItemToken.tryParse(t, null, false);
                    if (dd != null) 
                        eee.setEndToken((t = dd.getEndToken()));
                    else 
                        break;
                }
            }
            return eee;
        }
        if (t.isValue("СПИСОК", null)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.noun.isValue("ДОКУМЕНТ", null)) {
                t = npt.getEndToken().getNext();
                if (t != null && t.isCharOf(":.")) 
                    t = t.getNext();
                if (t == null) 
                    return null;
            }
        }
        if (!t.isChar('(') && !t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
            if (t.isValue("В", "У") && t.getNext() != null && ((t.getNext().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ") || t.getNext().isValue("РЕД", null)))) {
            }
            else if (t.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) {
                com.pullenti.ner.decree.internal.DecreeToken dtt0 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t.getNext(), null, false);
                if (dtt0 != null) 
                    return _new1494(t, dtt0.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.EDITIONS);
            }
            else if (t.isChar('{') && t.isNewlineBefore()) 
                isInFig = true;
            else 
                return null;
        }
        else {
            isInBracks = t.isChar('(');
            t = t.getNext();
        }
        com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
        if (dt != null && ((dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE))) 
            t = dt.getEndToken().getNext();
        else if (t instanceof com.pullenti.ner.NumberToken) 
            t = t.getNext();
        com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, true);
        if (pt != null) 
            t = pt.getEndToken().getNext();
        else if (isInBracks && t != null && (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) 
            t = t.getNext();
        if (t == null) 
            return null;
        boolean isDoubt = false;
        while (((t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isAdverb())) && t.getNext() != null) {
            t = t.getNext();
        }
        if (t.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
            ok = true;
        else if (t.isValue("ОТФОРМАТИРОВАН", null)) 
            ok = true;
        else if (t.isValue("РЕД", null)) {
            ok = true;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
        }
        else if ((t.isValue("ИЗМ", null) || t.isValue("ИЗМЕНЕНИЕ", "ЗМІНА") || t.isValue("УЧЕТ", "ОБЛІК")) || t.isValue("ВКЛЮЧИТЬ", "ВКЛЮЧИТИ") || t.isValue("ДОПОЛНИТЬ", "ДОПОВНИТИ")) {
            if (t.isValue("УЧЕТ", "ОБЛІК")) 
                isDoubt = true;
            ok = true;
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.getNext() == null) 
                    break;
                if (t.isCharOf(",.")) 
                    continue;
                if (t.isValue("ВНЕСЕННЫЙ", "ВНЕСЕНИЙ") || t.isValue("ПОПРАВКА", null)) 
                    continue;
                t = t.getPrevious();
                break;
            }
        }
        else if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
            com.pullenti.ner.Token tt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken();
            if (tt.isValue("В", "У") && tt.getNext() != null) 
                tt = tt.getNext();
            if (tt.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
                ok = true;
            else if (tt.isValue("РЕД", null)) 
                ok = true;
            t = t.getPrevious();
        }
        if (t == null) 
            return null;
        if (!ok && !isInFig) 
            return null;
        java.util.ArrayList<com.pullenti.ner.decree.DecreeReferent> decrs = new java.util.ArrayList<com.pullenti.ner.decree.DecreeReferent>();
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (isInBracks) {
                if (t.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getLengthChar() < 200)) {
                        t = br.getEndToken();
                        continue;
                    }
                }
                if (t.isChar(')')) 
                    break;
            }
            if (isInFig && t.isChar('}') && t.isNewlineAfter()) 
                break;
            com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (dr != null) {
                decrs.add(dr);
                continue;
            }
            if (t.isCommaAnd()) 
                continue;
            if (t.isNewlineBefore() && !isInBracks) {
                t = t.getPrevious();
                break;
            }
        }
        if (t == null) 
            return null;
        ok = false;
        if (isInBracks) {
            ok = t.isChar(')');
            if (!t.isNewlineAfter()) {
                if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().isNewlineAfter() && !t.getNext().chars.isLetter()) {
                }
                else 
                    isDoubt = true;
            }
        }
        else if (isInFig && t.isChar('}')) 
            ok = true;
        else if (t.isChar('.') || t.isNewlineAfter()) 
            ok = true;
        if (decrs.size() > 0) 
            isDoubt = false;
        if (ok && !isDoubt) {
            FragToken eds = _new1494(t0, t, com.pullenti.ner.instrument.InstrumentKind.EDITIONS);
            eds.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            for (com.pullenti.ner.decree.DecreeReferent d : decrs) {
                eds.referents.add(d);
            }
            return eds;
        }
        return null;
    }

    private static FragToken _createOwner(com.pullenti.ner.Token t) {
        if (t == null || !t.isNewlineBefore()) 
            return null;
        if (!t.chars.isCyrillicLetter() || t.chars.isAllLower()) 
            return null;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.Token t11 = null;
        boolean ignoreCurLine = false;
        boolean keyword = false;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                t11 = t1;
            com.pullenti.ner.Referent r = tt.getReferent();
            if ((r instanceof com.pullenti.ner.decree.DecreeReferent) && keyword) {
                tt = tt.kit.debedToken(tt);
                r = tt.getReferent();
            }
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
            if (dt != null) {
                if ((dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER && dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG && dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.UNKNOWN) && dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.MISC) 
                    break;
                t1 = (tt = dt.getEndToken());
                continue;
            }
            if (tt != t && tt.getWhitespacesBeforeCount() > 15) {
                if (tt.getPrevious() != null && tt.getPrevious().isHiphen()) {
                }
                else 
                    break;
            }
            if (((((r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner.address.AddressReferent) || (r instanceof com.pullenti.ner.address.StreetReferent)) || (r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.uri.UriReferent)) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) || (r instanceof com.pullenti.ner.decree.DecreePartReferent) || (r instanceof com.pullenti.ner.decree.DecreeReferent)) {
                ignoreCurLine = true;
                t1 = t11;
                break;
            }
            if (tt.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB)) {
                ignoreCurLine = true;
                t1 = t11;
                break;
            }
            if ((r instanceof com.pullenti.ner.geo.GeoReferent) && tt.isNewlineBefore()) 
                break;
            t1 = tt;
            com.pullenti.ner.ReferentToken oo = tt.kit.processReferent("ORGANIZATION", tt, null);
            if (oo != null) {
                t1 = (tt = oo.getEndToken());
                continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if (tt == t) {
                    com.pullenti.ner._org.internal.OrgItemTypeToken typ = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(tt, false);
                    if (typ != null) {
                        keyword = true;
                        t1 = (tt = typ.getEndToken());
                        continue;
                    }
                }
                t1 = (tt = npt.getEndToken());
            }
        }
        if (t1 == null) 
            return null;
        FragToken fr = _new1513(t, t1, com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION, true);
        return fr;
    }

    private int calcOwnerCoef(FragToken owner) {
        java.util.ArrayList<String> ownTyps = new java.util.ArrayList<String>();
        FragToken ownName = null;
        for (FragToken ch : owner.children) {
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.HEAD) {
                for (FragToken chh : ch.children) {
                    if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.TYP || chh.kind == com.pullenti.ner.instrument.InstrumentKind.NAME || chh.kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD) {
                        com.pullenti.ner.Token t = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(chh.getBeginToken(), false);
                        if (t instanceof com.pullenti.ner.TextToken) 
                            ownTyps.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).lemma);
                        if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.NAME && ownName == null) 
                            ownName = chh;
                    }
                }
            }
        }
        for (FragToken ch : children) {
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.HEAD) {
                for (FragToken chh : ch.children) {
                    if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE) {
                        com.pullenti.ner.Token t = chh.getBeginToken();
                        if (t.getMorph()._getClass().isPreposition()) 
                            t = t.getNext();
                        com.pullenti.ner.Token tt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false);
                        if (tt instanceof com.pullenti.ner.TextToken) {
                            String ty = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
                            if (ownTyps.contains(ty)) 
                                return 1;
                            continue;
                        }
                        com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false);
                        if (pt != null) {
                            if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.APPENDIX) {
                                if (owner.number > 0) {
                                    for (com.pullenti.ner.decree.internal.PartToken.PartValue nn : pt.values) {
                                        if (com.pullenti.unisharp.Utils.stringsEq(nn.value, ((Integer)owner.number).toString())) 
                                            return 3;
                                    }
                                }
                            }
                        }
                        if (ownName != null && (ownName.value instanceof String)) {
                            String val0 = (String)com.pullenti.unisharp.Utils.cast(ownName.value, String.class);
                            String val1 = com.pullenti.ner.core.MiscHelper.getTextValue(t, chh.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                            if (com.pullenti.unisharp.Utils.stringsEq(val1, val0)) 
                                return 3;
                            if (com.pullenti.ner.core.MiscHelper.canBeEquals(val0, val1, true, true, false)) 
                                return 3;
                            if (val1 != null && ((val1.startsWith(val0) || val0.startsWith(val1)))) 
                                return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean getHasChanges() {
        if (getBeginToken().getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) 
            return true;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) 
                return true;
        }
        return false;
    }


    public com.pullenti.ner.MetaToken getMultilineChangesValue() {
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && (t.getBeginChar() < getEndChar()); t = t.getNext()) {
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) {
                com.pullenti.ner.decree.DecreeChangeReferent dcr = (com.pullenti.ner.decree.DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeChangeReferent.class);
                for (com.pullenti.ner.Token tt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
                    com.pullenti.ner.decree.DecreeChangeValueReferent dval = (com.pullenti.ner.decree.DecreeChangeValueReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeChangeValueReferent.class);
                    if (dval == null || dval.getKind() != com.pullenti.ner.decree.DecreeChangeValueKind.TEXT) 
                        continue;
                    String val = dval.getValue();
                    if (val == null || (val.length() < 100)) 
                        continue;
                    if ((val.indexOf('\r') < 0) && (val.indexOf('\n') < 0) && !tt.isNewlineBefore()) 
                        continue;
                    com.pullenti.ner.Token t0 = null;
                    for (t = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken(); t != null && t.getEndChar() <= tt.getEndChar(); t = t.getNext()) {
                        if (com.pullenti.ner.core.BracketHelper.isBracket(t, true) && ((t.isWhitespaceBefore() || t.getPrevious().isChar(':')))) {
                            t0 = t.getNext();
                            break;
                        }
                        else if (t.getPrevious() != null && t.getPrevious().isChar(':') && t.isNewlineBefore()) {
                            t0 = t;
                            break;
                        }
                    }
                    com.pullenti.ner.Token t1 = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getEndToken();
                    if (com.pullenti.ner.core.BracketHelper.isBracket(t1, true)) 
                        t1 = t1.getPrevious();
                    if (t0 != null && ((t0.getEndChar() + 50) < t1.getEndChar())) 
                        return com.pullenti.ner.MetaToken._new899(t0, t1, dcr);
                    return null;
                }
            }
            if (t.getEndChar() > getEndChar()) 
                break;
        }
        return null;
    }


    public boolean _analizeTables() {
        if (children.size() > 0) {
            int abzCount = 0;
            int cou = 0;
            for (FragToken ch : children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                    abzCount++;
                if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.KEYWORD && ch.kind != com.pullenti.ner.instrument.InstrumentKind.NUMBER && ch.kind != com.pullenti.ner.instrument.InstrumentKind.NUMBER) 
                    cou++;
            }
            if (abzCount == cou && cou > 0) {
                java.util.ArrayList<FragToken> chs = children;
                children = new java.util.ArrayList<FragToken>();
                boolean bb = this._analizeTables();
                children = chs;
                if (bb) {
                    for (int i = 0; i < children.size(); i++) {
                        if (children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
                            FragToken ch0 = (i > 0 ? children.get(i - 1) : null);
                            if (ch0 != null && ch0.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                                ch0.setEndToken(children.get(i).getEndToken());
                                children.remove(i);
                                i--;
                            }
                            else 
                                children.get(i).kind = com.pullenti.ner.instrument.InstrumentKind.CONTENT;
                        }
                    }
                }
            }
            java.util.ArrayList<FragToken> changed = new java.util.ArrayList<FragToken>();
            for (FragToken ch : children) {
                if (ch._analizeTables()) 
                    changed.add(ch);
            }
            for (int i = changed.size() - 1; i >= 0; i--) {
                if (changed.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                    int j = children.indexOf(changed.get(i));
                    if (j < 0) 
                        continue;
                    children.remove(j);
                    com.pullenti.unisharp.Utils.insertToArrayList(children, changed.get(i).children, j);
                }
            }
            return false;
        }
        if (((kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER || kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE || kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) || kind == com.pullenti.ner.instrument.InstrumentKind.ITEM || kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) || kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
        }
        else 
            return false;
        if (itok != null && itok.getHasChanges()) 
            return false;
        int _endChar = getEndChar();
        if (getEndToken().getNext() == null) 
            _endChar = kit.getSofa().getText().length() - 1;
        com.pullenti.ner.Token t0 = getBeginToken();
        boolean tabs = false;
        for (com.pullenti.ner.Token tt = getBeginToken(); tt != null && tt.getEndChar() <= _endChar; tt = tt.getNext()) {
            if (!tt.isNewlineBefore()) 
                continue;
            if (tt.isChar((char)0x1E)) {
            }
            java.util.ArrayList<com.pullenti.ner.core.TableRowToken> rows = com.pullenti.ner.core.TableHelper.tryParseRows(tt, _endChar, false);
            if (rows == null || (rows.size() < 2)) 
                continue;
            boolean ok = true;
            for (com.pullenti.ner.core.TableRowToken r : rows) {
                if (r.cells.size() > 15) 
                    ok = false;
            }
            if (!ok) {
                tt = rows.get(rows.size() - 1).getEndToken();
                continue;
            }
            if (t0.getEndChar() < rows.get(0).getBeginChar()) 
                children.add(_new1494(t0, rows.get(0).getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
            FragToken tab = _new1494(rows.get(0).getBeginToken(), rows.get(rows.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TABLE);
            children.add(tab);
            for (int i = 0; i < rows.size(); i++) {
                FragToken rr = _new1511(rows.get(i).getBeginToken(), rows.get(i).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TABLEROW, i + 1);
                tab.children.add(rr);
                tabs = true;
                int no = 0;
                int cols = 0;
                for (com.pullenti.ner.core.TableCellToken ce : rows.get(i).cells) {
                    FragToken cell = _new1511(ce.getBeginToken(), ce.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.TABLECELL, ++no);
                    if (ce.colSpan > 1) 
                        cols += (((cell.subNumber = ce.colSpan)));
                    else 
                        cols++;
                    if (ce.rowSpan > 1) 
                        cell.subNumber2 = ce.rowSpan;
                    rr.children.add(cell);
                }
                if (tab.number < cols) 
                    tab.number = cols;
                tt = rows.get(i).getEndToken();
            }
            if (tab.number > 1) {
                int[] rnums = new int[tab.number];
                int[] rnumsCols = new int[tab.number];
                for (FragToken r : tab.children) {
                    int no = 0;
                    for (int ii = 0; ii < r.children.size(); ii++) {
                        if ((no < rnums.length) && rnums[no] > 0) {
                            rnums[no]--;
                            no += rnumsCols[no];
                            ii--;
                            continue;
                        }
                        r.children.get(ii).number = no + 1;
                        if (r.children.get(ii).subNumber2 > 1 && (no < rnums.length)) {
                            rnums[no] = r.children.get(ii).subNumber2 - 1;
                            rnumsCols[no] = (r.children.get(ii).subNumber == 0 ? 1 : r.children.get(ii).subNumber);
                        }
                        no += (r.children.get(ii).subNumber == 0 ? 1 : r.children.get(ii).subNumber);
                    }
                }
            }
            t0 = tt.getNext();
        }
        if ((t0 != null && (t0.getEndChar() < getEndChar()) && tabs) && t0 != getEndToken()) 
            children.add(_new1494(t0, getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
        return tabs;
    }

    private static FragToken createTZTitle(com.pullenti.ner.Token t0, com.pullenti.ner.instrument.InstrumentReferent doc) {
        com.pullenti.ner.decree.internal.DecreeToken tz = null;
        int cou = 0;
        com.pullenti.ner.Token t;
        for (t = t0; t != null && (cou < 300); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() > 1) 
                cou++;
            if (!t.isNewlineBefore()) {
                if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
                }
                else 
                    continue;
            }
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
            if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                if (com.pullenti.unisharp.Utils.stringsEq(dt.value, "ТЕХНИЧЕСКОЕ ЗАДАНИЕ")) 
                    tz = dt;
                break;
            }
        }
        if (tz == null) 
            return null;
        FragToken title = _new1494(t0, tz.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.HEAD);
        for (t = t0; t != null; t = t.getNext()) {
            if (!t.isNewlineBefore()) {
                title.setEndToken(t);
                continue;
            }
            if (_isStartOfBody(t, false)) 
                break;
            if (t.isValue("СОДЕРЖИМОЕ", null) || t.isValue("СОДЕРЖАНИЕ", null) || t.isValue("ОГЛАВЛЕНИЕ", null)) 
                break;
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
            if (dt != null) {
                _addTitleAttr(doc, title, dt);
                title.setEndToken((t = dt.getEndToken()));
                if (dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                    continue;
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES, 100);
                if (br != null && com.pullenti.ner.core.BracketHelper.isBracket(t.getNext(), true)) {
                    FragToken nam = _new1516(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true);
                    title.children.add(nam);
                    title.setEndToken((t = br.getEndToken()));
                    continue;
                }
                if (t.getNext() != null && t.getNext().isValue("НА", null)) {
                    com.pullenti.ner.Token t1 = t.getNext();
                    for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isNewlineBefore()) {
                            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                                break;
                            if (tt.isValue("СОДЕРЖИМОЕ", null) || tt.isValue("СОДЕРЖАНИЕ", null) || tt.isValue("ОГЛАВЛЕНИЕ", null)) 
                                break;
                        }
                        com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br1 != null) {
                            t1 = (tt = br1.getEndToken());
                            continue;
                        }
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                        if (npt != null) 
                            tt = npt.getEndToken();
                        t1 = tt;
                    }
                    FragToken nam = _new1516(t.getNext(), t1, com.pullenti.ner.instrument.InstrumentKind.NAME, true);
                    title.children.add(nam);
                    title.setEndToken((t = t1));
                    continue;
                }
            }
            FragToken appr1 = _createApproved(t);
            if (appr1 != null) {
                t = appr1.getEndToken();
                title.children.add(appr1);
                title.setEndToken(appr1.getEndToken());
                continue;
            }
            FragToken eds = _createEditions(t);
            if (eds != null) {
                title.children.add(eds);
                title.setEndToken((t = eds.getEndToken()));
                continue;
            }
            appr1 = _createMisc(t);
            if (appr1 != null) {
                t = appr1.getEndToken();
                title.children.add(appr1);
                title.setEndToken(appr1.getEndToken());
                continue;
            }
        }
        return title;
    }

    public static FragToken _new1494(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        return res;
    }

    public static FragToken _new1495(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, InstrToken1 _arg3, boolean _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.itok = _arg3;
        res.isExpired = _arg4;
        return res;
    }

    public static FragToken _new1496(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, boolean _arg4, InstrToken1 _arg5) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.setDefVal2(_arg4);
        res.itok = _arg5;
        return res;
    }

    public static FragToken _new1503(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, InstrToken1 _arg3) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.itok = _arg3;
        return res;
    }

    public static FragToken _new1504(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, InstrToken1 _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.itok = _arg4;
        return res;
    }

    public static FragToken _new1511(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, int _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.number = _arg4;
        return res;
    }

    public static FragToken _new1513(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, boolean _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.setDefVal2(_arg4);
        return res;
    }

    public static FragToken _new1516(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, boolean _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.setDefVal(_arg4);
        return res;
    }

    public static FragToken _new1528(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, Object _arg4, InstrToken1 _arg5) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.value = _arg4;
        res.itok = _arg5;
        return res;
    }

    public static FragToken _new1533(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, int _arg4, boolean _arg5, java.util.ArrayList<com.pullenti.ner.Referent> _arg6) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.number = _arg4;
        res.isExpired = _arg5;
        res.referents = _arg6;
        return res;
    }

    public static FragToken _new1546(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, Object _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.value = _arg4;
        return res;
    }

    public static FragToken _new1629(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentReferent _arg3, com.pullenti.ner.instrument.InstrumentKind _arg4) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.m_Doc = _arg3;
        res.kind = _arg4;
        return res;
    }

    public static FragToken _new1716(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.instrument.InstrumentKind _arg3, boolean _arg4, InstrToken1 _arg5) {
        FragToken res = new FragToken(_arg1, _arg2);
        res.kind = _arg3;
        res.setDefVal(_arg4);
        res.itok = _arg5;
        return res;
    }

    public FragToken() {
        super();
    }
    
    static {
        m_ZapiskaKeywords = new String[] {"ЗАЯВЛЕНИЕ", "ЗАПИСКА", "РАПОРТ", "ДОКЛАД", "ОТЧЕТ"};
    }
}
