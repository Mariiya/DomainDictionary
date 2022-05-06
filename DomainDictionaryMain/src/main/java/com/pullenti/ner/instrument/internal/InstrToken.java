/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class InstrToken extends com.pullenti.ner.MetaToken {

    public InstrToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public ILTypes typ = ILTypes.UNDEFINED;

    public String value;

    public Object ref;

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("МЕСТО ПЕЧАТИ", null, false);
        t.addAbridge("М.П.");
        t.addAbridge("M.П.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МІСЦЕ ПЕЧАТКИ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("М.П.");
        t.addAbridge("M.П.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОДПИСЬ", null, false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПІДПИС", com.pullenti.morph.MorphLang.UA, false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new969("ФАМИЛИЯ ИМЯ ОТЧЕСТВО", "ФИО");
        t.addAbridge("Ф.И.О.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1664("ПРІЗВИЩЕ ІМЯ ПО БАТЬКОВІ", com.pullenti.morph.MorphLang.UA, "ФИО");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ФАМИЛИЯ", null, false);
        t.addAbridge("ФАМ.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПРІЗВИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("ФАМ.");
        m_Ontology.add(t);
        m_Ontology.add(new com.pullenti.ner.core.Termin("ИМЯ", null, false));
        m_Ontology.add(new com.pullenti.ner.core.Termin("ІМЯ", com.pullenti.morph.MorphLang.UA, false));
    }

    public boolean isPurePerson() {
        if (ref instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ref, com.pullenti.ner.ReferentToken.class);
            if ((rt.referent instanceof com.pullenti.ner.person.PersonReferent) || (rt.referent instanceof com.pullenti.ner.person.PersonPropertyReferent)) 
                return true;
            if (rt.referent instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) {
                for (com.pullenti.ner.Token t = rt.getBeginToken(); t != null && t.getEndChar() <= rt.getEndChar(); t = t.getNext()) {
                    if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) 
                        return true;
                    else if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) 
                        break;
                }
                return false;
            }
        }
        return ref instanceof com.pullenti.ner.person.PersonReferent;
    }


    public boolean isPodpisStoron() {
        if (!isNewlineBefore() || !isNewlineAfter()) 
            return false;
        if (!getBeginToken().isValue("ПОДПИСЬ", "ПІДПИС")) 
            return false;
        com.pullenti.ner.Token t = getBeginToken().getNext();
        if (t != null && t.isValue("СТОРОНА", null)) 
            t = t.getNext();
        if (t != null && t.isCharOf(":.")) 
            t = t.getNext();
        if (getEndToken().getNext() == t) 
            return true;
        return false;
    }


    public boolean hasVerb;

    public boolean noWords;

    public boolean getHasTableChars() {
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if (t.isTableControlChar()) 
                return true;
        }
        if (getEndToken().getNext() != null && getEndToken().getNext().isTableControlChar() && !getEndToken().getNext().isChar((char)0x1E)) 
            return true;
        if (getBeginToken().getPrevious() != null && getBeginToken().getPrevious().isTableControlChar() && !getBeginToken().getPrevious().isChar((char)0x1F)) 
            return true;
        return false;
    }


    public boolean getOnlyPerson() {
        boolean hasPers = false;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) 
                hasPers = true;
            else if ((t instanceof com.pullenti.ner.TextToken) && (t.getLengthChar() < 2)) {
            }
            else 
                return false;
        }
        return hasPers;
    }


    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (isNewlineBefore()) 
            tmp.append("<<");
        tmp.append(typ.toString());
        if (value != null) 
            tmp.append(" '").append(value).append("'");
        if (ref != null) 
            tmp.append(" -> ").append(ref.toString());
        if (hasVerb) 
            tmp.append(" HasVerb");
        if (noWords) 
            tmp.append(" NoWords");
        if (getHasTableChars()) 
            tmp.append(" HasTableChars");
        if (isNewlineAfter()) 
            tmp.append(">>");
        tmp.append(": ").append(this.getSourceText());
        return tmp.toString();
    }

    public static java.util.ArrayList<InstrToken> parseList(com.pullenti.ner.Token t0, int maxChar) {
        java.util.ArrayList<InstrToken> res = new java.util.ArrayList<InstrToken>();
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (maxChar > 0) {
                if (t.getBeginChar() > maxChar) 
                    break;
            }
            if (res.size() == 272) {
            }
            InstrToken it = InstrToken.parse(t, maxChar, (res.size() > 0 ? res.get(res.size() - 1) : null));
            if (it == null) 
                break;
            if (res.size() == 286) {
            }
            if (it.typ == ILTypes.APPENDIX) {
            }
            if (it.typ == ILTypes.TYP) {
            }
            if (res.size() > 0) {
                if (res.get(res.size() - 1).getEndChar() > it.getBeginChar()) 
                    break;
            }
            if ((it.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && it.getEndToken().getNext().isChar('.')) 
                it.setEndToken(it.getEndToken().getNext());
            if (it.typ == ILTypes.UNDEFINED && t.isNewlineBefore()) {
                InstrToken1 it1 = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                if (it1 != null && it1.getHasChanges() && it1.getEndChar() > it.getEndChar()) 
                    it.setEndToken(it1.getEndToken());
            }
            res.add(it);
            if (it.getEndChar() > t.getBeginChar()) 
                t = it.getEndToken();
        }
        return res;
    }

    private static InstrToken _correctPerson(InstrToken res) {
        int specChars = 0;
        if (!res.isPurePerson()) {
            res.typ = ILTypes.UNDEFINED;
            return res;
        }
        for (com.pullenti.ner.Token t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.ReferentToken) && (res.ref instanceof com.pullenti.ner.ReferentToken)) {
                boolean ok = false;
                if (t.getReferent() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(res.ref, com.pullenti.ner.ReferentToken.class)).referent) 
                    ok = true;
                com.pullenti.ner.instrument.InstrumentParticipantReferent ip = (com.pullenti.ner.instrument.InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(res.ref, com.pullenti.ner.ReferentToken.class)).referent, com.pullenti.ner.instrument.InstrumentParticipantReferent.class);
                if (ip != null && ip.containsRef(t.getReferent())) 
                    ok = true;
                if (!ok && t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
                    if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(res.ref, com.pullenti.ner.ReferentToken.class)).referent instanceof com.pullenti.ner.person.PersonPropertyReferent) && (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
                        ok = true;
                        res.ref = t;
                    }
                }
                if (ok) {
                    res.setEndToken(t);
                    continue;
                }
            }
            com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                if ((((com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ПОДПИСЬ") || com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ПІДПИС"))) && t.isNewlineBefore() && t.getNext() != null) && t.getNext().isValue("СТОРОНА", null)) 
                    break;
                res.setEndToken((t = tok.getEndToken()));
                continue;
            }
            if (t.isChar(',')) 
                continue;
            if (t.isTableControlChar() && !t.isNewlineBefore()) 
                continue;
            if (t.isCharOf("_/\\")) {
                res.setEndToken(t);
                specChars++;
                continue;
            }
            if (t.isChar('(') && t.getNext() != null) {
                if ((((tok = m_Ontology.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        res.setEndToken((t = br.getEndToken()));
                        continue;
                    }
                }
            }
            break;
        }
        com.pullenti.ner.ReferentToken rt0 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(res.ref, com.pullenti.ner.ReferentToken.class);
        if (rt0 != null && (rt0.referent instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)) {
            for (com.pullenti.ner.Token tt = res.getBeginToken(); tt != null && tt.getEndChar() <= res.getEndChar(); tt = tt.getNext()) {
                if ((tt.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (tt.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                    res.ref = tt;
                    return res;
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && tt.isCharOf("_/\\")) 
                    specChars++;
                else if (tt instanceof com.pullenti.ner.MetaToken) {
                    for (com.pullenti.ner.Token ttt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken(); ttt != null && ttt.getEndChar() <= tt.getEndChar(); ttt = ttt.getNext()) {
                        if ((ttt.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (ttt.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                            res.ref = ttt;
                            return res;
                        }
                        else if ((ttt instanceof com.pullenti.ner.TextToken) && ttt.isCharOf("_/\\")) 
                            specChars++;
                    }
                }
            }
            if (specChars < 10) 
                res.typ = ILTypes.UNDEFINED;
        }
        return res;
    }

    public static InstrToken parse(com.pullenti.ner.Token t, int maxChar, InstrToken prev) {
        boolean isStartOfLine = false;
        com.pullenti.ner.Token t00 = t;
        if (t != null) {
            isStartOfLine = t00.isNewlineBefore();
            while (t != null) {
                if (t.isTableControlChar() && !t.isChar((char)0x1F)) {
                    if (t.isNewlineAfter() && !isStartOfLine) 
                        isStartOfLine = true;
                    t = t.getNext();
                }
                else 
                    break;
            }
        }
        if (t == null) 
            return null;
        if (t.isNewlineBefore()) 
            isStartOfLine = true;
        if (isStartOfLine) {
            if ((t.isValue("СОДЕРЖИМОЕ", "ВМІСТ") || t.isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || t.isValue("ОГЛАВЛЕНИЕ", "ЗМІСТ")) || ((t.isValue("СПИСОК", null) && t.getNext() != null && t.getNext().isValue("РАЗДЕЛ", null)))) {
                InstrToken1 cont = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                if (cont != null && cont.typ == InstrToken1.Types.INDEX) 
                    return new InstrToken(t, cont.getEndToken());
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = null;
        boolean hasWord = false;
        for (; t != null; t = t.getNext()) {
            if (t.isNewlineBefore() && t != t0) 
                break;
            if (maxChar > 0 && t.getBeginChar() > maxChar) 
                break;
            if (isStartOfLine && t == t0) {
                if (t.isValue("ГЛАВА", null)) {
                    InstrToken _next = parse(t.getNext(), 0, null);
                    if (_next != null && _next.typ == ILTypes.PERSON) {
                        _next.setBeginToken(t);
                        return _next;
                    }
                }
                com.pullenti.ner.Token tt = null;
                if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent) || (t.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)) 
                    return _correctPerson(_new1665(t00, t, ILTypes.PERSON, t));
                boolean isRef = false;
                if (t.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                    tt = t.getNext();
                    isRef = true;
                }
                else if (prev != null && prev.typ == ILTypes.PERSON) {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent(com.pullenti.ner.person.PersonAnalyzer.ANALYZER_NAME, t, null);
                    if (rt != null) {
                        if (rt.referent instanceof com.pullenti.ner.person.PersonReferent) 
                            return _new1666(t00, rt.getEndToken(), ILTypes.PERSON);
                        tt = rt.getEndToken().getNext();
                    }
                }
                int cou = 0;
                com.pullenti.ner.Token t11 = (tt == null ? null : tt.getPrevious());
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isTableControlChar()) 
                        continue;
                    com.pullenti.ner.Referent re = tt.getReferent();
                    if (re instanceof com.pullenti.ner.person.PersonReferent) 
                        return _new1665(t00, tt, ILTypes.PERSON, tt);
                    if (re instanceof com.pullenti.ner.geo.GeoReferent) {
                        t11 = tt;
                        continue;
                    }
                    if (re != null) 
                        break;
                    if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, false) != null) 
                        break;
                    if (tt.isNewlineBefore()) {
                        if ((++cou) > 4) 
                            break;
                    }
                }
                if (tt == null && isRef) 
                    return _new1665(t00, (t11 != null ? t11 : t), ILTypes.PERSON, t);
            }
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
            if (dt != null) {
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && !t.chars.isAllLower()) {
                    if (t != t0) 
                        break;
                    boolean _hasVerb = false;
                    for (com.pullenti.ner.Token tt = dt.getEndToken(); tt != null; tt = tt.getNext()) {
                        if (tt.isNewlineBefore()) 
                            break;
                        else if ((tt instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).isPureVerb()) {
                            _hasVerb = true;
                            break;
                        }
                    }
                    if (!_hasVerb) {
                        InstrToken res2 = _new1669(t0, dt.getEndToken(), ILTypes.TYP, (dt.fullValue != null ? dt.fullValue : dt.value));
                        if (com.pullenti.unisharp.Utils.stringsEq(res2.value, "ДОПОЛНИТЕЛЬНОЕ СОГЛАШЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(res2.value, "ДОДАТКОВА УГОДА")) {
                            if (res2.getBeginChar() > 500 && res2.getNewlinesBeforeCount() > 1) 
                                res2.typ = ILTypes.APPENDIX;
                        }
                        return res2;
                    }
                }
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    if (t != t0) 
                        break;
                    return _new1669(t0, dt.getEndToken(), ILTypes.REGNUMBER, dt.value);
                }
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) {
                    if (t != t0) 
                        break;
                    return _new1671(t0, dt.getEndToken(), ILTypes.ORGANIZATION, dt.ref, dt.value);
                }
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                    if (t != t0) 
                        break;
                    InstrToken re = _new1671(t0, dt.getEndToken(), ILTypes.GEO, dt.ref, dt.value);
                    t1 = re.getEndToken().getNext();
                    if (t1 != null && t1.isChar(',')) 
                        t1 = t1.getNext();
                    if (t1 != null && t1.isValue("КРЕМЛЬ", null)) 
                        re.setEndToken(t1);
                    else if ((t1 != null && t1.isValue("ДОМ", "БУДИНОК") && t1.getNext() != null) && t1.getNext().isValue("СОВЕТ", "РАД")) {
                        re.setEndToken(t1.getNext());
                        if (t1.getNext().getNext() != null && (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                            re.setEndToken(t1.getNext().getNext());
                    }
                    return re;
                }
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                    if (t != t0) 
                        break;
                    if (dt.ref != null && dt.ref.referent.toString().startsWith("агент")) 
                        dt = null;
                    if (dt != null) {
                        InstrToken res1 = _new1671(t0, dt.getEndToken(), ILTypes.PERSON, dt.ref, dt.value);
                        return _correctPerson(res1);
                    }
                }
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = (t1 = br.getEndToken());
                    continue;
                }
                if (t.getNext() != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext(), false, null, false)) {
                    t = (t1 = t.getNext());
                    continue;
                }
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.isChar('_')) {
                    t1 = t;
                    continue;
                }
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof com.pullenti.ner.date.DateReferent) {
                com.pullenti.ner.Token tt = t;
                if (tt.getNext() != null && tt.getNext().isCharOf(",;")) 
                    tt = tt.getNext();
                if (!t.isNewlineBefore() && !tt.isNewlineAfter()) {
                    t1 = tt;
                    continue;
                }
                if (!hasWord) 
                    return _new1665(t, tt, ILTypes.DATE, t);
                if (t != t0) 
                    break;
            }
            hasWord = true;
            if (r instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) {
                for (com.pullenti.ner.Token tt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(); tt != null && (tt.getEndChar() < t.getEndChar()); tt = tt.getNext()) {
                    com.pullenti.ner.Referent rr = tt.getReferent();
                    if (rr == null) 
                        continue;
                    if ((rr instanceof com.pullenti.ner._org.OrganizationReferent) || (rr instanceof com.pullenti.ner.bank.BankDataReferent) || (rr instanceof com.pullenti.ner.uri.UriReferent)) {
                        r = null;
                        break;
                    }
                }
            }
            if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || (r instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)) {
                if (t != t0) 
                    break;
                if (r instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) {
                }
                InstrToken res1 = _new1665(t, t, ILTypes.PERSON, t);
                return _correctPerson(res1);
            }
            if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                if (t != t0) 
                    break;
                return _new1665(t, t, ILTypes.ORGANIZATION, t);
            }
            if (r instanceof com.pullenti.ner.decree.DecreePartReferent) {
                com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.decree.DecreePartReferent.class);
                if (dpr.getAppendix() != null) {
                    if (t.isNewlineBefore() || isStartOfLine) {
                        if (t.isNewlineAfter() || t.getWhitespacesBeforeCount() > 30) 
                            return _new1669(t, t, ILTypes.APPENDIX, "ПРИЛОЖЕНИЕ");
                        boolean ok = true;
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                            if (tt.isNewlineBefore()) 
                                break;
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt != null) {
                                tt = npt.getEndToken();
                                continue;
                            }
                            ok = false;
                            break;
                        }
                        if (ok) 
                            return _new1669(t, t, ILTypes.APPENDIX, "ПРИЛОЖЕНИЕ");
                    }
                }
            }
            if ((r instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER && t == t0) {
                InstrToken res1 = _new1666(t, t, ILTypes.APPROVED);
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isCharOf(",;")) 
                        continue;
                    if ((tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class)).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                        res1.setEndToken(t);
                    else 
                        break;
                }
                return res1;
            }
            if (t.isValue("ЗА", null) && t.getNext() != null && t.isNewlineBefore()) {
                com.pullenti.ner.Referent rr = t.getNext().getReferent();
                if ((rr instanceof com.pullenti.ner.person.PersonReferent) || (rr instanceof com.pullenti.ner.person.PersonPropertyReferent) || (rr instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent)) {
                    if (t != t0) 
                        break;
                    InstrToken res1 = _new1665(t, t.getNext(), ILTypes.PERSON, t.getNext());
                    t = t.getNext().getNext();
                    if ((rr instanceof com.pullenti.ner.instrument.InstrumentParticipantReferent) && t != null) {
                        if ((((r = t.getReferent()))) != null) {
                            if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                                res1.setEndToken(t);
                                res1.ref = t;
                            }
                        }
                    }
                    return res1;
                }
            }
            for (int ii = 0; ii < m_Directives.size(); ii++) {
                if (t.isValue(m_Directives.get(ii), null)) {
                    if (t.getNext() != null && t.getNext().isValue("СЛЕДУЮЩЕЕ", "НАСТУПНЕ")) {
                        if (t != t0) 
                            break;
                        com.pullenti.ner.Token t11 = t.getNext();
                        boolean ok = false;
                        if (t11.getNext() != null && t11.getNext().isCharOf(":.") && t11.getNext().isNewlineAfter()) {
                            ok = true;
                            t11 = t11.getNext();
                        }
                        if (ok) 
                            return _new1669(t, t11, ILTypes.DIRECTIVE, m_DirectivesNorm.get(ii));
                    }
                    if (t.isNewlineAfter() || ((t.getNext() != null && t.getNext().isChar(':') && t.getNext().isNewlineAfter()))) {
                        if (t != t0) 
                            break;
                        if (!t.isNewlineBefore()) {
                            if ((com.pullenti.unisharp.Utils.stringsNe(m_DirectivesNorm.get(ii), "ПРИКАЗ") && com.pullenti.unisharp.Utils.stringsNe(m_DirectivesNorm.get(ii), "ПОСТАНОВЛЕНИЕ") && com.pullenti.unisharp.Utils.stringsNe(m_DirectivesNorm.get(ii), "НАКАЗ")) && com.pullenti.unisharp.Utils.stringsNe(m_DirectivesNorm.get(ii), "ПОСТАНОВУ")) 
                                break;
                        }
                        return _new1669(t, (t.isNewlineAfter() ? t : t.getNext()), ILTypes.DIRECTIVE, m_DirectivesNorm.get(ii));
                    }
                    break;
                }
            }
            if (t.isNewlineBefore() && t.chars.isLetter() && t.getLengthChar() == 1) {
                for (String d : m_Directives) {
                    com.pullenti.ner.Token t11 = com.pullenti.ner.core.MiscHelper.tryAttachWordByLetters(d, t, true);
                    if (t11 != null) {
                        if (t11.getNext() != null && t11.getNext().isChar(':')) 
                            t11 = t11.getNext();
                        return _new1666(t, t11, ILTypes.DIRECTIVE);
                    }
                }
            }
            com.pullenti.ner.Token tte = (t instanceof com.pullenti.ner.MetaToken ? ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken() : t);
            String term = (tte instanceof com.pullenti.ner.TextToken ? ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tte, com.pullenti.ner.TextToken.class)).term : null);
            if (isStartOfLine && !tte.chars.isAllLower() && t == t0) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tte, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null && ((com.pullenti.unisharp.Utils.stringsEq(term, "ПРИЛОЖЕНИЯ") || com.pullenti.unisharp.Utils.stringsEq(term, "ДОДАТКИ")))) 
                    // if (tte.Next != null && tte.Next.IsChar(':'))
                    npt = null;
                if (npt != null && npt.getMorph().getCase().isNominative() && (npt.getEndToken() instanceof com.pullenti.ner.TextToken)) {
                    String term1 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                    if (((com.pullenti.unisharp.Utils.stringsEq(term1, "ПРИЛОЖЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(term1, "ДОДАТОК") || com.pullenti.unisharp.Utils.stringsEq(term1, "МНЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(term1, "ДУМКА") || com.pullenti.unisharp.Utils.stringsEq(term1, "АКТ")) || com.pullenti.unisharp.Utils.stringsEq(term1, "ФОРМА") || com.pullenti.unisharp.Utils.stringsEq(term, "ЗАЯВКА")) {
                        com.pullenti.ner.Token tt1 = npt.getEndToken().getNext();
                        com.pullenti.ner.decree.internal.DecreeToken dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt1, null, false);
                        if (dt1 != null && dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                            tt1 = dt1.getEndToken().getNext();
                        else if (tt1 instanceof com.pullenti.ner.NumberToken) 
                            tt1 = tt1.getNext();
                        else if ((tt1 instanceof com.pullenti.ner.TextToken) && tt1.getLengthChar() == 1 && tt1.chars.isLetter()) 
                            tt1 = tt1.getNext();
                        boolean ok = true;
                        if (tt1 == null) 
                            ok = false;
                        else if (tt1.isValue("В", "У")) 
                            ok = false;
                        else if (tt1.isValue("К", null) && tt1.isNewlineBefore()) 
                            return _new1669(t, t, ILTypes.APPENDIX, term1);
                        else if (!tt1.isNewlineBefore() && _checkEntered(tt1) != null) 
                            ok = false;
                        else if (tt1 == t.getNext() && ((tt1.isChar(':') || ((tt1.isValue("НА", null) && com.pullenti.unisharp.Utils.stringsNe(term1, "ЗАЯВКА")))))) 
                            ok = false;
                        if (ok) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) {
                                tt1 = br.getEndToken().getNext();
                                if (br.getEndToken().getNext() == null || !br.getEndToken().isNewlineAfter() || br.getEndToken().getNext().isCharOf(";,")) 
                                    ok = false;
                                if (tt1 != null && tt1.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) 
                                    ok = false;
                            }
                        }
                        if (prev != null && prev.typ == ILTypes.APPENDIX) 
                            ok = false;
                        if (ok) {
                            int cou = 0;
                            for (com.pullenti.ner.Token ttt = tte.getPrevious(); ttt != null && (cou < 300); ttt = ttt.getPrevious(),cou++) {
                                if (ttt.isTableControlChar()) {
                                    if (!ttt.isChar((char)0x1F)) {
                                        if (ttt == tte.getPrevious() && ttt.isChar((char)0x1E)) {
                                        }
                                        else 
                                            ok = false;
                                    }
                                    break;
                                }
                            }
                        }
                        if (ok) {
                            InstrToken1 it1 = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                            if (it1 != null) {
                                if (it1.hasVerb) 
                                    ok = false;
                            }
                        }
                        if (ok && t.getPrevious() != null) {
                            for (com.pullenti.ner.Token ttp = t.getPrevious(); ttp != null; ttp = ttp.getPrevious()) {
                                if (ttp.isTableControlChar() && !ttp.isChar((char)0x1F)) 
                                    continue;
                                if (com.pullenti.ner.core.BracketHelper.isBracket(ttp, false) && !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(ttp, false, null, false)) 
                                    continue;
                                if (ttp.isCharOf(";:")) 
                                    ok = false;
                                break;
                            }
                        }
                        if ((ok && t.getPrevious() != null && (t.getNewlinesBeforeCount() < 3)) && !t.isNewlineAfter()) {
                            int lines = 0;
                            for (com.pullenti.ner.Token ttp = t.getPrevious(); ttp != null; ttp = ttp.getPrevious()) {
                                if (!ttp.isNewlineBefore()) 
                                    continue;
                                for (; ttp != null && (ttp.getEndChar() < t.getBeginChar()); ttp = ttp.getNext()) {
                                    if (ttp instanceof com.pullenti.ner.NumberToken) {
                                    }
                                    else if ((ttp instanceof com.pullenti.ner.TextToken) && ttp.getLengthChar() > 1) {
                                        if (ttp.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) 
                                            ok = false;
                                        break;
                                    }
                                    else 
                                        break;
                                }
                                if ((++lines) > 1) 
                                    break;
                            }
                        }
                        if (ok && ((com.pullenti.unisharp.Utils.stringsNe(term1, "ПРИЛОЖЕНИЕ") && com.pullenti.unisharp.Utils.stringsNe(term1, "ДОДАТОК") && com.pullenti.unisharp.Utils.stringsNe(term1, "МНЕНИЕ")))) {
                            if (t.getNewlinesBeforeCount() < 3) 
                                ok = false;
                        }
                        if (ok) 
                            return _new1669(t, t, ILTypes.APPENDIX, term1);
                    }
                }
            }
            boolean app = false;
            if ((((com.pullenti.unisharp.Utils.stringsEq(term, "ОСОБОЕ") || com.pullenti.unisharp.Utils.stringsEq(term, "ОСОБЛИВЕ"))) && t.getNext() != null && t.getNext().isValue("МНЕНИЕ", "ДУМКА")) && t == t0 && isStartOfLine) 
                app = true;
            if ((((com.pullenti.unisharp.Utils.stringsEq(term, "ДОПОЛНИТЕЛЬНОЕ") || com.pullenti.unisharp.Utils.stringsEq(term, "ДОДАТКОВА"))) && t.getNext() != null && t.getNext().isValue("СОГЛАШЕНИЕ", "УГОДА")) && t == t0 && isStartOfLine) 
                app = true;
            if (app) {
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isNewlineBefore()) 
                        break;
                    else if (tt.getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB)) {
                        app = false;
                        break;
                    }
                }
                if (app) 
                    return _new1666(t, t.getNext(), ILTypes.APPENDIX);
            }
            if (!t.chars.isAllLower() && t == t0) {
                com.pullenti.ner.Token tt = _checkApproved(t);
                if (tt != null) {
                    if (tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                        return _new1665(t, tt, ILTypes.APPROVED, tt.getNext().getReferent());
                    com.pullenti.ner.decree.internal.DecreeToken dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt.getNext(), null, false);
                    if (dt1 != null && dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                        return _new1666(t, tt, ILTypes.APPROVED);
                }
            }
            t1 = t;
            isStartOfLine = false;
        }
        if (t1 == null) 
            return null;
        InstrToken res = _new1666(t00, t1, ILTypes.UNDEFINED);
        res.noWords = true;
        for (t = t0; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
            if (!(t instanceof com.pullenti.ner.TextToken)) {
                if (t instanceof com.pullenti.ner.ReferentToken) 
                    res.noWords = false;
                continue;
            }
            if (!t.chars.isLetter()) 
                continue;
            res.noWords = false;
            if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) 
                res.hasVerb = true;
        }
        if (t0.isValue("ВОПРОС", "ПИТАННЯ") && t0.getNext() != null && t0.getNext().isCharOf(":.")) 
            res.typ = ILTypes.QUESTION;
        return res;
    }

    public static com.pullenti.ner.Token _checkApproved(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (((!t.isValue("УТВЕРЖДЕН", "ЗАТВЕРДЖЕНИЙ") && !t.isValue("УТВЕРЖДАТЬ", "СТВЕРДЖУВАТИ") && !t.isValue("УТВЕРДИТЬ", "ЗАТВЕРДИТИ")) && !t.isValue("ВВЕСТИ", null) && !t.isValue("СОГЛАСОВАНО", "ПОГОДЖЕНО")) && !t.isValue("СОГЛАСОВАТЬ", "ПОГОДИТИ")) 
            return null;
        if (t.getMorph().containsAttr("инф.", null) && t.getMorph().containsAttr("сов.в.", null)) 
            return null;
        if (t.getMorph().containsAttr("возвр.", null)) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) 
                continue;
            if (t.isChar(':')) 
                continue;
            if (t.isValue("ДЕЙСТВИЕ", "ДІЯ") || t.isValue("ВВЕСТИ", null) || t.isValue("ВВОДИТЬ", "ВВОДИТИ")) {
                t1 = t;
                continue;
            }
            com.pullenti.ner.Token tt = _checkApproved(t);
            if (tt != null) {
                if (!tt.isNewlineBefore() && com.pullenti.unisharp.Utils.stringsNe(tt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), t0.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false))) {
                    t1 = (tt = t);
                    continue;
                }
            }
            break;
        }
        return t1;
    }

    public static com.pullenti.ner.Token _checkEntered(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if ((((t.isValue("ВСТУПАТЬ", "ВСТУПАТИ") || t.isValue("ВСТУПИТЬ", "ВСТУПИТИ"))) && t.getNext() != null && t.getNext().isValue("В", "У")) && t.getNext().getNext() != null && t.getNext().getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
            return t.getNext().getNext();
        if (t.isValue("УТРАТИТЬ", "ВТРАТИТИ") && t.getNext() != null && t.getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
            return t.getNext();
        if (t.isValue("ДЕЙСТВОВАТЬ", "ДІЯТИ") && t.getNext() != null && t.getNext().isValue("ДО", null)) 
            return t.getNext();
        if (((t.isValue("В", null) || t.isValue("B", null))) && t.getNext() != null) {
            if (t.getNext().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
                return t.getNext();
            if (t.getNext().isValue("РЕД", null)) {
                if (t.getNext().getNext() != null && t.getNext().getNext().isChar('.')) 
                    return t.getNext().getNext();
                return t.getNext();
            }
        }
        if (t.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) 
            return t.getNext();
        if (t.isValue("РЕД", null)) {
            if (t.getNext() != null && t.getNext().isChar('.')) 
                return t.getNext();
            return t;
        }
        return _checkApproved(t);
    }

    public static java.util.ArrayList<String> m_Directives;

    public static java.util.ArrayList<String> m_DirectivesNorm;

    public static InstrToken _new1665(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ILTypes _arg3, Object _arg4) {
        InstrToken res = new InstrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static InstrToken _new1666(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ILTypes _arg3) {
        InstrToken res = new InstrToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static InstrToken _new1669(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ILTypes _arg3, String _arg4) {
        InstrToken res = new InstrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static InstrToken _new1671(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ILTypes _arg3, Object _arg4, String _arg5) {
        InstrToken res = new InstrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        res.value = _arg5;
        return res;
    }

    public InstrToken() {
        super();
    }
    
    static {
        m_Directives = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ПРИКАЗЫВАТЬ", "ПРИКАЗАТЬ", "ОПРЕДЕЛЯТЬ", "ОПРЕДЕЛЯТЬ", "ОПРЕДЕЛИТЬ", "ПОСТАНОВЛЯТЬ", "ПОСТАНОВИТЬ", "УСТАНОВИТЬ", "РЕШИЛ", "РЕШИТЬ", "ПРОСИТЬ", "ПРИГОВАРИВАТЬ", "ПРИГОВОРИТЬ", "НАКАЗУВАТИ", "ВИЗНАЧАТИ", "ВИЗНАЧИТИ", "УХВАЛЮВАТИ", "УХВАЛИТИ", "ПОСТАНОВЛЯТИ", "ПОСТАНОВИТИ", "ВСТАНОВИТИ", "ВИРІШИВ", "ВИРІШИТИ", "ПРОСИТИ", "ПРИМОВЛЯТИ", "ЗАСУДИТИ"}));
        m_DirectivesNorm = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ПРИКАЗ", "ПРИКАЗ", "ОПРЕДЕЛЕНИЕ", "ОПРЕДЕЛЕНИЕ", "ОПРЕДЕЛЕНИЕ", "ПОСТАНОВЛЕНИЕ", "ПОСТАНОВЛЕНИЕ", "УСТАНОВЛЕНИЕ", "РЕШЕНИЕ", "РЕШЕНИЕ", "ЗАЯВЛЕНИЕ", "ПРИГОВОР", "ПРИГОВОР", "НАКАЗ", "УХВАЛА", "УХВАЛА", "УХВАЛА", "УХВАЛА", "ПОСТАНОВА", "ПОСТАНОВА", "ВСТАНОВЛЕННЯ", "РІШЕННЯ", "РІШЕННЯ", "ЗАЯВА", "ВИРОК", "ВИРОК"}));
    }
}
