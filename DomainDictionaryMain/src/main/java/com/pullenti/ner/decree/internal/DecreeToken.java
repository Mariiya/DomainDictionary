/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

// Примитив, из которых состоит декрет
public class DecreeToken extends com.pullenti.ner.MetaToken {

    public DecreeToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public ItemType typ = ItemType.TYP;

    public String value;

    public String fullValue;

    public com.pullenti.ner.ReferentToken ref;

    public java.util.ArrayList<DecreeToken> children = null;

    public boolean isDoubtful;

    public com.pullenti.ner.decree.DecreeKind typKind = com.pullenti.ner.decree.DecreeKind.UNDEFINED;

    public int numYear;

    public com.pullenti.ner.MetaToken aliasToken;

    public boolean canBeSingleDecree;

    public boolean isDelo() {
        if (getBeginToken().isValue("ДЕЛО", "СПРАВА")) 
            return true;
        if (getBeginToken().getNext() != null && getBeginToken().getNext().isValue("ДЕЛО", "СПРАВА")) 
            return true;
        return false;
    }


    @Override
    public String toString() {
        String v = value;
        if (v == null) 
            v = ref.referent.toStringEx(true, kit.baseLanguage, 0);
        return (typ.toString() + " " + v + " " + ((fullValue != null ? fullValue : "")));
    }

    public static DecreeToken tryAttach(com.pullenti.ner.Token t, DecreeToken prev, boolean mustByTyp) {
        if (t == null) 
            return null;
        if (t.isValue("НАЗВАННЫЙ", null)) {
        }
        com.pullenti.ner.core.AnalyzerData ad = com.pullenti.ner.decree.DecreeAnalyzer.getData(t);
        if (ad == null) 
            return null;
        if (ad.level > 1) 
            return null;
        ad.level++;
        DecreeToken res = _TryAttach(t, prev, 0, mustByTyp);
        ad.level--;
        if (res == null) {
            if (t.isHiphen()) {
                res = _TryAttach(t.getNext(), prev, 0, mustByTyp);
                if (res != null && res.typ == ItemType.NAME) 
                    return res;
            }
            if ((t instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ЗУ") && (t.getWhitespacesAfterCount() < 3)) {
                DecreeToken _next = tryAttachName(t.getNext(), null, false, false);
                boolean ok = false;
                if (_next != null) 
                    ok = true;
                else {
                    _next = tryAttach(t.getNext(), null, false);
                    if (_next != null && _next.typ == ItemType.NUMBER) {
                        DecreeToken next2 = tryAttach(_next.getEndToken().getNext(), null, false);
                        if (next2 != null && next2.typ == ItemType.DATE) 
                            ok = true;
                    }
                }
                if (ok) {
                    res = _new905(t, t, ItemType.TYP, "ЗАКОН");
                    return res;
                }
            }
            if (t.isValue("ПРОЕКТ", null)) {
                ad.level++;
                res = _TryAttach(t.getNext(), prev, 0, false);
                ad.level--;
                if (res != null && res.typ == ItemType.TYP && res.value != null) {
                    if ((res.value.indexOf("ЗАКОН") >= 0) || !(res.getEndToken() instanceof com.pullenti.ner.TextToken)) 
                        res.value = "ПРОЕКТ ЗАКОНА";
                    else 
                        res.value = "ПРОЕКТ " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                    res.setBeginToken(t);
                    return res;
                }
                else if (res != null && res.typ == ItemType.NUMBER) {
                    ad.level++;
                    DecreeToken res1 = _TryAttach(res.getEndToken().getNext(), prev, 0, false);
                    ad.level--;
                    if (res1 != null && res1.typ == ItemType.TYP && (res1.getEndToken() instanceof com.pullenti.ner.TextToken)) {
                        res = _new906(t, t, ItemType.TYP);
                        res.value = "ПРОЕКТ " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res1.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                        return res;
                    }
                }
            }
            if (t.isValue("ИНФОРМАЦИЯ", "ІНФОРМАЦІЯ") && (t.getWhitespacesAfterCount() < 3)) {
                ad.level++;
                java.util.ArrayList<DecreeToken> dts = tryAttachList(t.getNext(), null, 10, false);
                ad.level--;
                if (dts == null || (dts.size() < 2)) 
                    return null;
                boolean hasNum = false;
                boolean hasOwn = false;
                boolean hasDate = false;
                boolean hasName = false;
                for (DecreeToken dt : dts) {
                    if (dt.typ == ItemType.NUMBER) 
                        hasNum = true;
                    else if (dt.typ == ItemType.OWNER || dt.typ == ItemType.ORG) 
                        hasOwn = true;
                    else if (dt.typ == ItemType.DATE) 
                        hasDate = true;
                    else if (dt.typ == ItemType.NAME) 
                        hasName = true;
                }
                if (hasOwn && ((hasNum || ((hasDate && hasName))))) {
                    res = _new906(t, t, ItemType.TYP);
                    res.value = "ИНФОРМАЦИЯ";
                    return res;
                }
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if ((npt.getEndToken().isValue("СОБРАНИЕ", null) || npt.getEndToken().isValue("УЧАСТНИК", null) || npt.getEndToken().isValue("СОБСТВЕННИК", null)) || npt.getEndToken().isValue("УЧРЕДИТЕЛЬ", null)) {
                    res = _new906(t, npt.getEndToken(), ItemType.OWNER);
                    com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt2 != null && npt2.getMorph().getCase().isGenitive()) 
                        res.setEndToken(npt2.getEndToken());
                    res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t, res.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    return res;
                }
            }
            return null;
        }
        if (res.typ == ItemType.DATE) {
            if (res.ref == null) 
                return null;
            com.pullenti.ner.date.DateReferent dre = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(res.ref.referent, com.pullenti.ner.date.DateReferent.class);
            if (dre == null) 
                return null;
        }
        if (res.getBeginToken().getBeginChar() > res.getEndToken().getEndChar()) {
        }
        if (res.typ == ItemType.NUMBER) {
            for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (!tt.isCommaAnd() || tt.isNewlineBefore()) 
                    break;
                tt = tt.getNext();
                if (!(tt instanceof com.pullenti.ner.NumberToken)) 
                    break;
                if (tt.getWhitespacesBeforeCount() > 2) 
                    break;
                DecreeToken ddd = _TryAttach(tt, res, 0, false);
                if (ddd != null) {
                    if (ddd.typ != ItemType.NUMBER) 
                        break;
                    if (res.children == null) 
                        res.children = new java.util.ArrayList<DecreeToken>();
                    res.children.add(ddd);
                    res.setEndToken(ddd.getEndToken());
                    continue;
                }
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getIntValue() > 1970) 
                    break;
                if (tt.isWhitespaceAfter()) {
                }
                else if (!tt.getNext().isCharOf(",.")) {
                }
                else 
                    break;
                StringBuilder tmp = new StringBuilder();
                com.pullenti.ner.Token tee = _tryAttachNumber(tt, tmp, true);
                if (res.children == null) 
                    res.children = new java.util.ArrayList<DecreeToken>();
                DecreeToken add = _new905(tt, tee, ItemType.NUMBER, tmp.toString());
                res.children.add(add);
                res.setEndToken((tt = tee));
            }
        }
        if (res.typ != ItemType.TYP) 
            return res;
        if (res.getBeginToken() == res.getEndToken()) {
            com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(res.getBeginToken().getPrevious(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && (tok.termin.tag instanceof ItemType) && tok.getEndToken() == res.getEndToken()) {
                if (((ItemType)tok.termin.tag) == ItemType.TYP) 
                    return null;
            }
        }
        if (((prev != null && prev.typ == ItemType.TYP && prev.value != null) && (((prev.value.indexOf("ДОГОВОР") >= 0) || (prev.value.indexOf("ДОГОВІР") >= 0))) && res.value != null) && !(res.value.indexOf("ДОГОВОР") >= 0) && !(res.value.indexOf("ДОГОВІР") >= 0)) 
            return null;
        for (String e : m_EmptyAdjectives) {
            if (t.isValue(e, null)) {
                res = _TryAttach(t.getNext(), prev, 0, false);
                if (res == null || res.typ != ItemType.TYP) 
                    return null;
                break;
            }
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('(')) {
            ad.level++;
            DecreeToken res1 = _TryAttach(res.getEndToken().getNext(), prev, 0, false);
            ad.level--;
            if (res1 != null && res1.getEndToken().isChar(')')) {
                if (com.pullenti.unisharp.Utils.stringsEq(res1.value, res.value) && res.typ == ItemType.TYP) 
                    res.setEndToken(res1.getEndToken());
                else if (com.pullenti.unisharp.Utils.stringsEq(res.value, "ЕДИНЫЙ ОТРАСЛЕВОЙ СТАНДАРТ ЗАКУПОК") && res1.value != null && res1.value.startsWith("ПОЛОЖЕНИЕ О ЗАКУПК")) 
                    res.setEndToken(res1.getEndToken());
            }
        }
        if (res.value != null && (res.value.indexOf(" ") >= 0)) {
            for (String s : m_AllTypesRU) {
                if ((res.value.indexOf(s) >= 0) && com.pullenti.unisharp.Utils.stringsNe(res.value, s)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s, "КОДЕКС")) {
                        res.fullValue = res.value;
                        res.value = s;
                        break;
                    }
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(res.value, "КОДЕКС") && res.fullValue == null) {
            com.pullenti.ner.Token t1 = res.getEndToken();
            for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore()) 
                    break;
                DecreeChangeToken cha = DecreeChangeToken.tryAttach(tt, null, false, null, false);
                if (cha != null) 
                    break;
                if (tt == t1.getNext() && res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ")) 
                    break;
                if (!(tt instanceof com.pullenti.ner.TextToken)) 
                    break;
                if (tt == t1.getNext() && tt.isValue("ЗАКОН", null)) {
                    if (tt.getNext() != null && ((tt.getNext().isValue("О", null) || tt.getNext().isValue("ПРО", null)))) {
                        com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt0 == null || !npt0.getMorph().getCase().isPrepositional()) 
                            break;
                        t1 = npt0.getEndToken();
                        break;
                    }
                }
                boolean ooo = false;
                if (tt.getMorph()._getClass().isPreposition() && tt.getNext() != null) {
                    if (tt.isValue("ПО", null)) 
                        tt = tt.getNext();
                    else if (tt.isValue("О", null) || tt.isValue("ОБ", null) || tt.isValue("ПРО", null)) {
                        ooo = true;
                        tt = tt.getNext();
                    }
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt == null) 
                    break;
                if (tt == t1.getNext() && npt.getMorph().getCase().isGenitive()) {
                    t1 = (tt = npt.getEndToken());
                    if (tt.getNext() != null && tt.getNext().isAnd()) {
                        com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt2 != null && npt2.getMorph().getCase().isGenitive()) {
                            if (tryAttach(tt.getNext().getNext(), null, false) == null) 
                                t1 = (tt = npt2.getEndToken());
                        }
                    }
                }
                else if (ooo && npt.getMorph().getCase().isPrepositional()) {
                    t1 = (tt = npt.getEndToken());
                    for (com.pullenti.ner.Token ttt = tt.getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (!ttt.isCommaAnd()) 
                            break;
                        npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt == null || !npt.getMorph().getCase().isPrepositional()) 
                            break;
                        t1 = (tt = npt.getEndToken());
                        if (ttt.isAnd()) 
                            break;
                        ttt = npt.getEndToken();
                    }
                }
                else 
                    break;
            }
            if (t1 != res.getEndToken()) {
                res.setEndToken(t1);
                res.fullValue = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            }
        }
        if (res.value != null && ((res.value.startsWith("ВЕДОМОСТИ СЪЕЗДА") || res.value.startsWith("ВІДОМОСТІ ЗЇЗДУ")))) {
            com.pullenti.ner.Token tt = res.getEndToken().getNext();
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                res.ref = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
                res.setEndToken(tt);
                tt = tt.getNext();
            }
            if (tt != null && tt.isAnd()) 
                tt = tt.getNext();
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                res.setEndToken(tt);
                tt = tt.getNext();
            }
        }
        return res;
    }

    private static DecreeToken _TryAttach(com.pullenti.ner.Token t, DecreeToken prev, int lev, boolean mustByTyp) {
        if (t == null || lev > 4) 
            return null;
        if (prev != null && prev.typ == ItemType.TYP) {
            while (t.isCharOf(":-") && t.getNext() != null && !t.isNewlineAfter()) {
                t = t.getNext();
            }
        }
        if (prev != null) {
            if (t.isValue("ПРИ", "ЗА") && t.getNext() != null) 
                t = t.getNext();
        }
        if ((!mustByTyp && t.isValue("МЕЖДУ", "МІЖ") && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && t.getNext().getNext() != null) {
            com.pullenti.ner.Token t11 = t.getNext().getNext();
            boolean isBr = false;
            if ((t11.isChar('(') && (t11.getNext() instanceof com.pullenti.ner.TextToken) && t11.getNext().getNext() != null) && t11.getNext().getNext().isChar(')')) {
                t11 = t11.getNext().getNext().getNext();
                isBr = true;
            }
            if (t11 != null && t11.isCommaAnd() && (t11.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                DecreeToken rr = _new906(t, t11.getNext(), ItemType.BETWEEN);
                rr.children = new java.util.ArrayList<DecreeToken>();
                rr.children.add(_new911(t.getNext(), t.getNext(), ItemType.OWNER, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class)));
                rr.children.add(_new911(t11.getNext(), t11.getNext(), ItemType.OWNER, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t11.getNext(), com.pullenti.ner.ReferentToken.class)));
                for (t = rr.getEndToken().getNext(); t != null; t = t.getNext()) {
                    if ((isBr && t.isChar('(') && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                        t = t.getNext().getNext();
                        rr.setEndToken(t);
                        rr.children.get(rr.children.size() - 1).setEndToken(t);
                        continue;
                    }
                    if ((t.isCommaAnd() && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && !(t.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
                        rr.children.add(_new911(t.getNext(), t.getNext(), ItemType.OWNER, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class)));
                        t = rr.setEndToken(t.getNext());
                        continue;
                    }
                    break;
                }
                return rr;
            }
        }
        com.pullenti.ner.Referent r = t.getReferent();
        if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            com.pullenti.ner._org.OrganizationReferent _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class);
            DecreeToken res1 = null;
            if (_org.containsProfile(com.pullenti.ner._org.OrgProfile.MEDIA)) {
                com.pullenti.ner.Token tt1 = rt.getBeginToken();
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1, false, false)) 
                    tt1 = tt1.getNext();
                res1 = _TryAttach(tt1, prev, lev + 1, false);
                if (res1 != null && res1.typ == ItemType.TYP) 
                    res1.setBeginToken(res1.setEndToken(t));
                else 
                    res1 = null;
            }
            if (res1 == null && _org.containsProfile(com.pullenti.ner._org.OrgProfile.PRESS)) {
                res1 = _new906(t, t, ItemType.TYP);
                res1.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.NO);
            }
            if (res1 != null) {
                com.pullenti.ner.Token t11 = res1.getEndToken();
                if (t11.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    res1.ref = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t11, com.pullenti.ner.ReferentToken.class);
                else if (t11 instanceof com.pullenti.ner.MetaToken) 
                    t11 = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t11, com.pullenti.ner.MetaToken.class)).getEndToken();
                if (t11.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    res1.ref = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t11, com.pullenti.ner.ReferentToken.class);
                else if (com.pullenti.ner.core.BracketHelper.isBracket(t11, false) && (t11.getPrevious().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                    res1.ref = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t11.getPrevious(), com.pullenti.ner.ReferentToken.class);
                return res1;
            }
        }
        if (r != null && !mustByTyp) {
            if (r instanceof com.pullenti.ner.geo.GeoReferent) 
                return _new915(t, t, ItemType.TERR, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), r.toStringEx(true, t.kit.baseLanguage, 0));
            if (r instanceof com.pullenti.ner.date.DateReferent) {
                if (prev != null && prev.typ == ItemType.TYP && prev.typKind == com.pullenti.ner.decree.DecreeKind.STANDARD) {
                    DecreeToken ree = tryAttach(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(), prev, false);
                    if ((ree != null && ree.typ == ItemType.NUMBER && ree.numYear > 0) && ((ree.getEndToken() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken() || ree.getEndToken().isChar('*')))) {
                        if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().isChar('*')) 
                            t = t.getNext();
                        ree.setBeginToken(ree.setEndToken(t));
                        return ree;
                    }
                }
                if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition() && t.getPrevious().isValue("ДО", null)) 
                    return null;
                return _new911(t, t, ItemType.DATE, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
            }
            if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                if ((t.getNext() != null && t.getNext().isValue("В", "У") && t.getNext().getNext() != null) && t.getNext().getNext().isValue("СОСТАВ", "СКЛАДІ")) 
                    return null;
                return _new915(t, t, ItemType.ORG, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), r.toString());
            }
            if (r instanceof com.pullenti.ner.person.PersonReferent) {
                boolean ok = false;
                if (prev != null && ((prev.typ == ItemType.TYP || prev.typ == ItemType.DATE))) 
                    ok = true;
                else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                    ok = true;
                else {
                    DecreeToken ne = _TryAttach(t.getNext(), null, lev + 1, false);
                    if (ne != null && ((ne.typ == ItemType.TYP || ne.typ == ItemType.DATE || ne.typ == ItemType.OWNER))) 
                        ok = true;
                }
                if (ok) {
                    com.pullenti.ner.person.PersonPropertyReferent prop = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(r.getSlotValue(com.pullenti.ner.person.PersonReferent.ATTR_ATTR), com.pullenti.ner.person.PersonPropertyReferent.class);
                    if (prop != null) {
                        boolean ok1 = prop.getKind() == com.pullenti.ner.person.PersonPropertyKind.BOSS;
                        String str = prop.toString();
                        if ((str.indexOf("глава") >= 0) || (str.indexOf("председатель") >= 0)) 
                            ok1 = true;
                        if (ok1) 
                            return _new911(t, t, ItemType.OWNER, new com.pullenti.ner.ReferentToken(prop, t, t, null));
                    }
                }
            }
            if (r instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                if (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken() == ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getEndToken()) 
                    return null;
                String str = r.toString();
                if ((str.indexOf("исполнитель") >= 0) || (str.indexOf("виконавець") >= 0)) 
                    return null;
                return _new911(t, t, ItemType.OWNER, new com.pullenti.ner.ReferentToken(r, t, t, null));
            }
            if (r instanceof com.pullenti.ner.denomination.DenominationReferent) {
                String s = r.toString();
                if (s.length() > 1 && ((s.charAt(0) == 'A' || s.charAt(0) == 'А')) && Character.isDigit(s.charAt(1))) 
                    return _new905(t, t, ItemType.NUMBER, s);
            }
            return null;
        }
        if (!mustByTyp) {
            com.pullenti.ner.Token tdat = null;
            if (t.isValue("ОТ", "ВІД") || t.isValue("ПРИНЯТЬ", "ПРИЙНЯТИ")) 
                tdat = t.getNext();
            else if (t.isValue("ВВЕСТИ", null) || t.isValue("ВВОДИТЬ", "ВВОДИТИ")) {
                tdat = t.getNext();
                if (tdat != null && tdat.isValue("В", "У")) 
                    tdat = tdat.getNext();
                if (tdat != null && tdat.isValue("ДЕЙСТВИЕ", "ДІЯ")) 
                    tdat = tdat.getNext();
            }
            if (tdat != null) {
                if (tdat.getNext() != null && tdat.getMorph()._getClass().isPreposition()) 
                    tdat = tdat.getNext();
                if (tdat.getReferent() instanceof com.pullenti.ner.date.DateReferent) 
                    return _new911(t, tdat, ItemType.DATE, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tdat, com.pullenti.ner.ReferentToken.class));
                com.pullenti.ner.ReferentToken dr = t.kit.processReferent("DATE", tdat, null);
                if (dr != null) 
                    return _new911(t, dr.getEndToken(), ItemType.DATE, dr);
            }
            if (t.isValue("НА", null) && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.date.DateRangeReferent)) 
                return _new911(t, t.getNext(), ItemType.DATERANGE, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class));
            if (t.isChar('(')) {
                com.pullenti.ner.Token tt = _isEdition(t.getNext());
                if (tt != null) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) 
                        return _new906(t, br.getEndToken(), ItemType.EDITION);
                }
                if (t.getNext() != null && t.getNext().isValue("ПРОЕКТ", null)) 
                    return _new905(t.getNext(), t.getNext(), ItemType.TYP, "ПРОЕКТ");
                if ((t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.date.DateRangeReferent) && t.getNext().getNext() != null) && t.getNext().getNext().isChar(')')) 
                    return _new911(t, t.getNext().getNext(), ItemType.DATERANGE, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class));
            }
            else {
                com.pullenti.ner.Token tt = _isEdition(t);
                if (tt != null) 
                    tt = tt.getNext();
                if (tt != null) {
                    DecreeToken xxx = DecreeToken.tryAttach(tt, null, false);
                    if (xxx != null) 
                        return _new906(t, tt.getPrevious(), ItemType.EDITION);
                }
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (prev != null && ((prev.typ == ItemType.TYP || prev.typ == ItemType.DATE))) {
                    StringBuilder tmp = new StringBuilder();
                    com.pullenti.ner.Token t11 = _tryAttachNumber(t, tmp, false);
                    if (t11 != null) {
                        DecreeToken ne = _TryAttach(t11.getNext(), null, lev + 1, false);
                        String valnum = tmp.toString();
                        if (ne != null && ((ne.typ == ItemType.DATE || ne.typ == ItemType.OWNER || ne.typ == ItemType.NAME))) 
                            return _new905(t, t11, ItemType.NUMBER, valnum);
                        if (com.pullenti.morph.LanguageHelper.endsWithEx(valnum, "ФЗ", "ФКЗ", null, null)) 
                            return _new905(t, t11, ItemType.NUMBER, valnum);
                        if ((ne != null && ne.typ == ItemType.TYP && ne.getBeginToken().isValue("ФЗ", null)) && (ne.getWhitespacesBeforeCount() < 3)) 
                            return _new905(t, ne.getBeginToken(), ItemType.NUMBER, valnum + "-ФЗ");
                        int year = 0;
                        if (prev.typ == ItemType.TYP) {
                            boolean ok = false;
                            if (prev.typKind == com.pullenti.ner.decree.DecreeKind.STANDARD) {
                                ok = true;
                                if (t11.getNext() != null && t11.getNext().isChar('*')) 
                                    t11 = t11.getNext();
                                if (com.pullenti.unisharp.Utils.endsWithString(valnum, "(E)", true)) 
                                    valnum = valnum.substring(0, 0 + valnum.length() - 3).trim();
                                while (true) {
                                    if ((t11.getWhitespacesAfterCount() < 2) && (t11.getNext() instanceof com.pullenti.ner.NumberToken)) {
                                        tmp.setLength(0);
                                        com.pullenti.ner.Token t22 = _tryAttachNumber(t11.getNext(), tmp, false);
                                        if (t22 == null) 
                                            break;
                                        valnum = (valnum + "." + tmp.toString());
                                        t11 = t22;
                                    }
                                    else 
                                        break;
                                }
                                for (int ii = valnum.length() - 1; ii >= 0; ii--) {
                                    if (!Character.isDigit(valnum.charAt(ii))) {
                                        if (ii == valnum.length() || ii == 0) 
                                            break;
                                        if ((valnum.charAt(ii) != '-' && valnum.charAt(ii) != ':' && valnum.charAt(ii) != '.') && valnum.charAt(ii) != '/' && valnum.charAt(ii) != '\\') 
                                            break;
                                        int nn = 0;
                                        String ss = valnum.substring(ii + 1);
                                        if (ss.length() != 2 && ss.length() != 4) 
                                            break;
                                        if (ss.charAt(0) == '0' && ss.length() == 2) 
                                            nn = 2000 + ((((int)ss.charAt(1)) - ((int)'0')));
                                        else {
                                            com.pullenti.unisharp.Outargwrapper<Integer> wrapnn931 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                                            boolean inoutres932 = com.pullenti.unisharp.Utils.parseInteger(ss, 0, null, wrapnn931);
                                            nn = (wrapnn931.value != null ? wrapnn931.value : 0);
                                            if (inoutres932) {
                                                if (nn > 50 && nn <= 99) 
                                                    nn += 1900;
                                                else if (ss.length() == 2 && ((2000 + nn) <= java.time.LocalDateTime.now().getYear())) 
                                                    nn += 2000;
                                            }
                                        }
                                        if (nn >= 1950 && nn <= java.time.LocalDateTime.now().getYear()) {
                                            year = nn;
                                            valnum = valnum.substring(0, 0 + ii);
                                        }
                                        break;
                                    }
                                }
                                valnum = valnum.replace('-', '.');
                                if (year < 1) {
                                    if (t11.getNext() != null && t11.getNext().isHiphen()) {
                                        if ((t11.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t11.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                                            int nn = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t11.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                                            if (nn > 50 && nn <= 99) 
                                                nn += 1900;
                                            if (nn >= 1950 && nn <= java.time.LocalDateTime.now().getYear()) {
                                                year = nn;
                                                t11 = t11.getNext().getNext();
                                            }
                                        }
                                    }
                                }
                            }
                            else if (prev.getBeginToken() == prev.getEndToken() && prev.getBeginToken().chars.isAllUpper() && ((prev.getBeginToken().isValue("ФЗ", null) || prev.getBeginToken().isValue("ФКЗ", null)))) 
                                ok = true;
                            if (ok) 
                                return _new933(t, t11, ItemType.NUMBER, valnum, year);
                        }
                    }
                    if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        int val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
                        if (val > 1910 && (val < 2030)) 
                            return _new905(t, t, ItemType.DATE, ((Integer)val).toString());
                    }
                }
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t, null);
                if (rt != null) {
                    com.pullenti.ner.person.PersonPropertyReferent pr = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.person.PersonPropertyReferent.class);
                    if (pr != null) 
                        return _new935(rt.getBeginToken(), rt.getEndToken(), ItemType.OWNER, rt, rt.getMorph());
                }
                if (t.getNext() != null && t.getNext().chars.isLetter() && (t.getWhitespacesAfterCount() < 3)) {
                    DecreeToken res1 = _TryAttach(t.getNext(), prev, lev + 1, false);
                    if (res1 != null && res1.typ == ItemType.OWNER) {
                        res1.setBeginToken(t);
                        return res1;
                    }
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks = null;
        if (!(t instanceof com.pullenti.ner.TextToken)) {
            if ((t instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "100")) {
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getBeginToken().isValue("СТО", null) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getBeginToken().chars.isAllUpper()) {
                    toks = m_Termins.tryParseAll(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if (toks != null && toks.size() == 1) 
                        toks.get(0).setBeginToken(toks.get(0).setEndToken(t));
                }
            }
            if (toks == null) 
                return null;
        }
        else 
            toks = m_Termins.tryParseAll(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (toks != null) {
            for (com.pullenti.ner.core.TerminToken tok : toks) {
                if (tok.getEndToken().isChar('.') && tok.getBeginToken() != tok.getEndToken()) 
                    tok.setEndToken(tok.getEndToken().getPrevious());
                if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "РЕГИСТРАЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "РЕЄСТРАЦІЯ")) {
                    if (tok.getEndToken().getNext() != null && ((tok.getEndToken().getNext().isValue("В", null) || tok.getEndToken().getNext().isValue("ПО", null)))) 
                        tok.setEndToken(tok.getEndToken().getNext());
                }
                boolean doubt = false;
                if ((tok.getEndChar() - tok.getBeginChar()) < 3) {
                    if (t.isValue("СП", null)) {
                        if (!(t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                            if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t.getNext()) == null) 
                                return null;
                        }
                    }
                    if (t.isValue("УК", null)) {
                        if ((t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                            String str = t.getNext().getReferent().toString();
                            if ((str.indexOf("Союз") >= 0)) {
                            }
                            else 
                                return null;
                        }
                    }
                    doubt = true;
                    if (tok.getEndToken().getNext() == null || !tok.chars.isAllUpper()) {
                    }
                    else {
                        r = tok.getEndToken().getNext().getReferent();
                        if (r instanceof com.pullenti.ner.geo.GeoReferent) 
                            doubt = false;
                    }
                }
                if (tok.getBeginToken() == tok.getEndToken() && (tok.getLengthChar() < 4) && toks.size() > 1) {
                    int cou = 0;
                    for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (cou < 500); tt = tt.getPrevious(),cou++) {
                        com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                        if (dr == null) 
                            continue;
                        for (com.pullenti.ner.core.TerminToken tok1 : toks) {
                            if (dr.findSlot(com.pullenti.ner.decree.DecreeReferent.ATTR_NAME, tok1.termin.getCanonicText(), true) != null) 
                                return _new936(tok.getBeginToken(), tok.getEndToken(), (ItemType)tok1.termin.tag, tok1.termin.getCanonicText(), tok1.getMorph());
                        }
                    }
                    if (tok.getBeginToken().isValue("ТК", null) && tok.termin.getCanonicText().startsWith("ТРУД")) {
                        boolean hasTamoz = false;
                        cou = 0;
                        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (cou < 500); tt = tt.getPrevious(),cou++) {
                            if (tt.isValue("ТАМОЖНЯ", null) || tt.isValue("ТАМОЖЕННЫЙ", null) || tt.isValue("ГРАНИЦА", null)) {
                                hasTamoz = true;
                                break;
                            }
                        }
                        if (hasTamoz) 
                            continue;
                        cou = 0;
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null && (cou < 500); tt = tt.getNext(),cou++) {
                            if (tt.isValue("ТАМОЖНЯ", null) || tt.isValue("ТАМОЖЕННЫЙ", null) || tt.isValue("ГРАНИЦА", null)) {
                                hasTamoz = true;
                                break;
                            }
                        }
                        if (hasTamoz) 
                            continue;
                    }
                }
                if (doubt && tok.chars.isAllUpper()) {
                    if (PartToken.isPartBefore(tok.getBeginToken())) 
                        doubt = false;
                    else if (tok.getSourceText().endsWith("ТС")) 
                        doubt = false;
                }
                DecreeToken res = _new937(tok.getBeginToken(), tok.getEndToken(), (ItemType)tok.termin.tag, tok.termin.getCanonicText(), tok.getMorph(), doubt);
                if (tok.termin.tag2 instanceof com.pullenti.ner.decree.DecreeKind) 
                    res.typKind = (com.pullenti.ner.decree.DecreeKind)tok.termin.tag2;
                if (com.pullenti.unisharp.Utils.stringsEq(res.value, "ГОСТ") && tok.getEndToken().getNext() != null) {
                    if (tok.getEndToken().getNext().isValue("Р", null) || tok.getEndToken().getNext().isValue("P", null)) 
                        res.setEndToken(tok.getEndToken().getNext());
                    else {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tok.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null && ((com.pullenti.unisharp.Utils.stringsEq(g.getAlpha2(), "RU") || com.pullenti.unisharp.Utils.stringsEq(g.getAlpha2(), "SU")))) 
                            res.setEndToken(tok.getEndToken().getNext());
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(res.value, "КОНСТИТУЦИЯ") && tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isChar('(')) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tok.getEndToken().getNext().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if ((npt != null && npt.getEndToken().isValue("ЗАКОН", null) && npt.getEndToken().getNext() != null) && npt.getEndToken().getNext().isChar(')')) 
                        res.setEndToken(npt.getEndToken().getNext());
                }
                if ((tok.termin.tag2 instanceof String) && res.typ == ItemType.TYP) {
                    res.fullValue = tok.termin.getCanonicText();
                    res.value = (String)com.pullenti.unisharp.Utils.cast(tok.termin.tag2, String.class);
                    res.isDoubtful = false;
                }
                if (res.typ == ItemType.TYP && (tok.termin.tag3 instanceof Boolean)) 
                    res.canBeSingleDecree = true;
                if (res.typKind == com.pullenti.ner.decree.DecreeKind.STANDARD) {
                    int cou = 0;
                    for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null && (cou < 3); tt = tt.getNext(),cou++) {
                        if (tt.getWhitespacesBeforeCount() > 2) 
                            break;
                        com.pullenti.ner.core.TerminToken tok2 = m_Termins.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null) {
                            if ((tok2.termin.tag2 instanceof com.pullenti.ner.decree.DecreeKind) && ((com.pullenti.ner.decree.DecreeKind)tok2.termin.tag2) == com.pullenti.ner.decree.DecreeKind.STANDARD) {
                                tt = res.setEndToken(tok2.getEndToken());
                                res.isDoubtful = false;
                                if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНДАРТ")) 
                                    res.value = tok2.termin.getCanonicText();
                                continue;
                            }
                        }
                        if ((tt instanceof com.pullenti.ner.TextToken) && (tt.getLengthChar() < 4) && tt.chars.isAllUpper()) {
                            res.setEndToken(tt);
                            continue;
                        }
                        if (((tt.isCharOf("/\\") || tt.isHiphen())) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isAllUpper()) {
                            tt = tt.getNext();
                            res.setEndToken(tt);
                            continue;
                        }
                        break;
                    }
                    if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНДАРТ")) 
                        res.isDoubtful = true;
                    if (res.isDoubtful && !res.isNewlineAfter()) {
                        DecreeToken num1 = tryAttach(res.getEndToken().getNext(), res, false);
                        if (num1 != null && num1.typ == ItemType.NUMBER) {
                            if (num1.numYear > 0) 
                                res.isDoubtful = false;
                        }
                    }
                    if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНДАРТ") && res.isDoubtful) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(res.value, "КОДЕКС")) {
                    DecreeToken _next = tryAttach(res.getEndToken().getNext(), null, false);
                    if ((_next != null && _next.typ == ItemType.TYP && _next.value != null) && (_next.value.indexOf("КОДЕКС") >= 0)) {
                        _next.setBeginToken(res.getBeginToken());
                        return _next;
                    }
                }
                return res;
            }
        }
        if (((t.getMorph()._getClass().isAdjective() && ((t.isValue("УКАЗАННЫЙ", "ЗАЗНАЧЕНИЙ") || t.isValue("ВЫШЕУКАЗАННЫЙ", "ВИЩЕВКАЗАНИЙ") || t.isValue("НАЗВАННЫЙ", "НАЗВАНИЙ"))))) || ((t.getMorph()._getClass().isPronoun() && (((t.isValue("ЭТОТ", "ЦЕЙ") || t.isValue("ТОТ", "ТОЙ") || t.isValue("ДАННЫЙ", "ДАНИЙ")) || t.isValue("САМЫЙ", "САМИЙ")))))) {
            com.pullenti.ner.Token t11 = t.getNext();
            if (t11 != null && t11.isValue("ЖЕ", null)) 
                t11 = t11.getNext();
            com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            com.pullenti.ner.core.TerminToken tok;
            if ((((tok = m_Termins.tryParse(t11, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                if (((short)((tok.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value()) || ((nnn != null && nnn.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR))) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && ((short)((npt.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                    }
                    else {
                        com.pullenti.ner.ReferentToken te = _findBackTyp(t.getPrevious(), tok.termin.getCanonicText());
                        if (te != null) 
                            return _new911(t, tok.getEndToken(), ItemType.DECREEREF, te);
                    }
                }
            }
        }
        if (t.getMorph()._getClass().isAdjective() && t.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ")) {
            com.pullenti.ner.core.TerminToken tok;
            if ((((tok = m_Termins.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) 
                return _new911(t, tok.getEndToken(), ItemType.DECREEREF, null);
        }
        if (mustByTyp) 
            return null;
        if ((((t instanceof com.pullenti.ner.TextToken) && prev != null && prev.typ == ItemType.TYP) && t.chars.isAllUpper() && t.getLengthChar() >= 2) && t.getLengthChar() <= 5) {
            if (((com.pullenti.unisharp.Utils.stringsEq(prev.value, "ТЕХНИЧЕСКИЕ УСЛОВИЯ") && t.getNext() != null && t.getNext().isChar('.')) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar('.') && (t.getNext().getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                DecreeToken res = _new905(t, t, ItemType.NUMBER, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                t = t.getNext().getNext();
                res.value = (res.value + "." + t.getSourceText() + "." + t.getNext().getNext().getSourceText());
                res.setEndToken((t = t.getNext().getNext()));
                if ((t.getWhitespacesAfterCount() < 2) && t.getNext() != null && t.getNext().isValue("ТУ", null)) 
                    res.setEndToken(res.getEndToken().getNext());
                return res;
            }
        }
        if ((((((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 4 && t.chars.isAllUpper()) && t.getNext() != null && !t.isWhitespaceAfter()) && t.getNext().isChar('.') && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar('.') && (t.getNext().getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
            if (t.getNext().getNext().getNext().getNext().getNext() != null && t.getNext().getNext().getNext().getNext().getNext().isValue("ТУ", null)) {
                DecreeToken res = _new905(t, t.getNext().getNext().getNext().getNext(), ItemType.NUMBER, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                res.value = (t.getSourceText() + "." + t.getNext().getNext().getSourceText() + "." + t.getNext().getNext().getNext().getNext().getSourceText());
                return res;
            }
        }
        if (t.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isVerb()) {
            DecreeToken dt = _TryAttach(t.getNext(), prev, lev + 1, false);
            if (dt != null && dt.ref == null) {
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t, null);
                if (rt != null) {
                    dt.ref = rt;
                    dt.setBeginToken(t);
                    return dt;
                }
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.internalNoun != null) 
                npt = null;
            if ((npt != null && dt != null && dt.typ == ItemType.TYP) && com.pullenti.unisharp.Utils.stringsEq(dt.value, "КОДЕКС")) {
                dt.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                dt.setBeginToken(t);
                dt.isDoubtful = true;
                return dt;
            }
            if (npt != null && ((npt.getEndToken().isValue("ДОГОВОР", "ДОГОВІР") || npt.getEndToken().isValue("КОНТРАКТ", null)))) {
                dt = _new906(t, npt.getEndToken(), ItemType.TYP);
                dt.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                if (t.getMorphClassInDictionary().isVerb()) 
                    dt.value = npt.getEndToken().getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                return dt;
            }
            boolean tryNpt = false;
            com.pullenti.ner.core.TerminToken tok;
            if (!t.chars.isAllLower()) 
                tryNpt = true;
            else 
                for (String a : m_StdAdjectives) {
                    if (t.isValue(a, null)) {
                        tryNpt = true;
                        break;
                    }
                }
            if (tryNpt) {
                if (npt != null) {
                    if (npt.getEndToken().isValue("ГАЗЕТА", null) || npt.getEndToken().isValue("БЮЛЛЕТЕНЬ", "БЮЛЕТЕНЬ")) 
                        return _new936(t, npt.getEndToken(), ItemType.TYP, npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
                    if (npt.adjectives.size() > 0 && npt.getEndToken().getMorphClassInDictionary().isNoun()) {
                        if ((((tok = m_Termins.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                            if (npt.getBeginToken().isValue("ОБЩИЙ", "ЗАГАЛЬНИЙ")) 
                                return null;
                            return _new944(npt.getBeginToken(), tok.getEndToken(), npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
                        }
                    }
                    if (prev != null && prev.typ == ItemType.TYP) {
                        if (npt.getEndToken().isValue("КОЛЛЕГИЯ", "КОЛЕГІЯ")) {
                            DecreeToken res1 = _new936(t, npt.getEndToken(), ItemType.OWNER, npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
                            for (t = npt.getEndToken().getNext(); t != null; t = t.getNext()) {
                                if (t.isAnd() || t.getMorph()._getClass().isPreposition()) 
                                    continue;
                                com.pullenti.ner.Referent re = t.getReferent();
                                if ((re instanceof com.pullenti.ner.geo.GeoReferent) || (re instanceof com.pullenti.ner._org.OrganizationReferent)) {
                                    res1.setEndToken(t);
                                    continue;
                                }
                                else if (re != null) 
                                    break;
                                DecreeToken dt1 = _TryAttach(t, res1, lev + 1, false);
                                if (dt1 != null && dt1.typ != ItemType.UNKNOWN) {
                                    if (dt1.typ != ItemType.OWNER) 
                                        break;
                                    t = res1.setEndToken(dt1.getEndToken());
                                    continue;
                                }
                                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                                if (npt1 == null) 
                                    break;
                                t = res1.setEndToken(npt1.getEndToken());
                            }
                            if (res1.getEndToken() != npt.getEndToken()) 
                                res1.value = (res1.value + " " + com.pullenti.ner.core.MiscHelper.getTextValue(npt.getEndToken().getNext(), res1.getEndToken(), com.pullenti.ner.core.GetTextAttr.KEEPQUOTES));
                            return res1;
                        }
                    }
                }
            }
        }
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.Token t0 = t;
        boolean num = false;
        if ((((t1 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t)))) != null) {
            num = true;
            if ((t1 instanceof com.pullenti.ner.TextToken) && !t1.chars.isAllUpper() && t1.getLengthChar() > 3) {
                com.pullenti.morph.MorphClass mc1 = t1.getMorphClassInDictionary();
                if (mc1.isNoun()) 
                    return null;
            }
        }
        else if (_isJusNumber(t)) 
            t1 = t;
        if (t1 != null) {
            if ((t1.getWhitespacesBeforeCount() < 15) && ((!t1.isNewlineBefore() || (t1 instanceof com.pullenti.ner.NumberToken) || _isJusNumber(t1)))) {
                StringBuilder tmp = new StringBuilder();
                com.pullenti.ner.Token t11 = _tryAttachNumber(t1, tmp, num);
                if (t11 != null) {
                    if (t11.getNext() != null && t11.getNext().isValue("ДСП", null)) {
                        t11 = t11.getNext();
                        tmp.append("ДСП");
                    }
                    return _new905(t0, t11, ItemType.NUMBER, tmp.toString());
                }
            }
            if (t1.isNewlineBefore() && num) 
                return _new906(t0, t1.getPrevious(), ItemType.NUMBER);
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) && ((((t.getNext().isValue("О", null) || t.getNext().isValue("ОБ", null) || t.getNext().isValue("ПРО", null)) || t.getNext().isValue("ПО", null) || t.chars.isCapitalUpper()) || ((prev != null && (t.getNext() instanceof com.pullenti.ner.TextToken) && ((prev.typ == ItemType.DATE || prev.typ == ItemType.NUMBER))))))) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.CANCONTAINSVERBS, 200);
                if (br != null) {
                    com.pullenti.ner.Token tt = br.getEndToken();
                    if (tt.getPrevious() != null && tt.getPrevious().isChar('>')) 
                        tt = tt.getPrevious();
                    if ((tt.isChar('>') && (tt.getPrevious() instanceof com.pullenti.ner.NumberToken) && tt.getPrevious().getPrevious() != null) && tt.getPrevious().getPrevious().isChar('<')) {
                        tt = tt.getPrevious().getPrevious().getPrevious();
                        if (tt == null || tt.getBeginChar() <= br.getBeginChar()) 
                            return null;
                        br.setEndToken(tt);
                    }
                    com.pullenti.ner.Token tt1 = _tryAttachStdChangeName(t.getNext());
                    if (tt1 != null && tt1.getEndChar() > br.getEndChar()) 
                        br.setEndToken(tt1);
                    else 
                        for (tt = br.getBeginToken().getNext(); tt != null && (tt.getEndChar() < br.getEndChar()); tt = tt.getNext()) {
                            if (tt.isChar('(')) {
                                DecreeToken dt = DecreeToken.tryAttach(tt.getNext(), null, false);
                                if (dt == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt.getNext(), true, false)) 
                                    dt = DecreeToken.tryAttach(tt.getNext().getNext(), null, false);
                                if (dt != null && dt.typ == ItemType.TYP) {
                                    if (DecreeToken.getKind(dt.value) == com.pullenti.ner.decree.DecreeKind.PUBLISHER) {
                                        br.setEndToken(tt.getPrevious());
                                        break;
                                    }
                                }
                            }
                        }
                    return _new905(br.getBeginToken(), br.getEndToken(), ItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO));
                }
                else {
                    com.pullenti.ner.Token tt1 = _tryAttachStdChangeName(t.getNext());
                    if (tt1 != null) 
                        return _new905(t, tt1, ItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, tt1, com.pullenti.ner.core.GetTextAttr.NO));
                }
            }
            else if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    if (!t.getNext().isValue("ДАЛЕЕ", "ДАЛІ")) {
                        if ((br.getEndChar() - br.getBeginChar()) < 30) 
                            return _new905(br.getBeginToken(), br.getEndToken(), ItemType.MISC, com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO));
                    }
                }
            }
        }
        if (t.getInnerBool()) {
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t, null);
            if (rt != null) {
                com.pullenti.ner.person.PersonPropertyReferent pr = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.person.PersonPropertyReferent.class);
                if (pr == null) 
                    return null;
                if (pr.getKind() != com.pullenti.ner.person.PersonPropertyKind.UNDEFINED) {
                }
                else if (com.pullenti.unisharp.Utils.startsWithString(pr.getName(), "ГРАЖДАН", true) || com.pullenti.unisharp.Utils.startsWithString(pr.getName(), "ГРОМАДЯН", true)) 
                    return null;
                if (pr.getKind() == com.pullenti.ner.person.PersonPropertyKind.BOSS) {
                }
                else if (prev != null && prev.typ == ItemType.TYP) {
                }
                else 
                    return null;
                return _new935(rt.getBeginToken(), rt.getEndToken(), ItemType.OWNER, rt, rt.getMorph());
            }
        }
        if (t.isValue("О", null) || t.isValue("ОБ", null) || t.isValue("ПРО", null)) {
            com.pullenti.ner.Token et = null;
            if ((t.getNext() != null && t.getNext().isValue("ВНЕСЕНИЕ", "ВНЕСЕННЯ") && t.getNext().getNext() != null) && t.getNext().getNext().isValue("ИЗМЕНЕНИЕ", "ЗМІНА")) 
                et = t.getNext();
            else if (t.getNext() != null && t.getNext().isValue("ПОПРАВКА", null)) 
                et = t.getNext();
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
                et = t.getNext();
            if (et != null && et.getNext() != null && et.getNext().getMorph()._getClass().isPreposition()) 
                et = et.getNext();
            if (et != null && et.getNext() != null) {
                java.util.ArrayList<DecreeToken> dts2 = tryAttachList(et.getNext(), null, 10, false);
                if (dts2 != null && dts2.get(0).typ == ItemType.TYP) {
                    et = dts2.get(0).getEndToken();
                    if (dts2.size() > 1 && dts2.get(1).typ == ItemType.TERR) 
                        et = dts2.get(1).getEndToken();
                    return _new905(t, et, ItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, et, com.pullenti.ner.core.GetTextAttr.NO));
                }
                if (et.getNext().isCharOf(",(") || (et instanceof com.pullenti.ner.ReferentToken)) 
                    return _new905(t, et, ItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, et, com.pullenti.ner.core.GetTextAttr.NO));
            }
            else if (et != null) 
                return _new905(t, et, ItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, et, com.pullenti.ner.core.GetTextAttr.NO));
            return null;
        }
        if (t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) 
            return null;
        if ((t instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ОД") && (t.getWhitespacesAfterCount() < 3)) {
            StringBuilder tmp = new StringBuilder();
            com.pullenti.ner.Token ttt = _tryAttachNumber(t.getNext(), tmp, true);
            if (ttt != null) 
                return _new905(t, ttt, ItemType.NUMBER, "ОД" + tmp);
        }
        if (prev != null && prev.typ == ItemType.TYP) {
            if (t.isValue("ПРАВИТЕЛЬСТВО", "УРЯД") || t.isValue("ПРЕЗИДЕНТ", null)) 
                return _new956(t, t, ItemType.OWNER, t.getMorph(), t.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
        }
        com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
        if (npt2 != null) {
            if (npt2.getEndToken().isValue("ПОЗИЦИЯ", null)) 
                return null;
        }
        if ((((t.chars.isCyrillicLetter() && ((!t.chars.isAllLower() || ((prev != null && prev.typ == ItemType.UNKNOWN))))) || t.isValue("ЗАСЕДАНИЕ", "ЗАСІДАННЯ") || t.isValue("СОБРАНИЕ", "ЗБОРИ")) || t.isValue("ПЛЕНУМ", null) || t.isValue("КОЛЛЕГИЯ", "КОЛЕГІЯ")) || t.isValue("АДМИНИСТРАЦИЯ", "АДМІНІСТРАЦІЯ")) {
            boolean ok = false;
            if (prev != null && ((prev.typ == ItemType.TYP || prev.typ == ItemType.OWNER || prev.typ == ItemType.ORG))) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (!mc.isPreposition() && !mc.isConjunction() && !mc.isVerb()) 
                    ok = true;
            }
            else if (prev != null && prev.typ == ItemType.UNKNOWN && !t.getMorph()._getClass().isVerb()) 
                ok = true;
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && !t.isValue("ИМЕНЕМ", null)) 
                ok = true;
            else if ((t.getPrevious() != null && t.getPrevious().isChar(',') && t.getPrevious().getPrevious() != null) && (t.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                ok = true;
            if (ok) {
                if (PartToken.tryAttach(t, null, false, false) != null) 
                    ok = false;
            }
            if (ok) {
                t1 = t;
                ItemType ty = ItemType.UNKNOWN;
                StringBuilder tmp = new StringBuilder();
                for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                    if (!(tt instanceof com.pullenti.ner.TextToken)) {
                        com.pullenti.ner._org.OrganizationReferent _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
                        if (_org != null && tt.getPrevious() == t1) {
                            ty = ItemType.OWNER;
                            if (tmp.length() > 0) 
                                tmp.append(' ');
                            tmp.append(tt.getSourceText().toUpperCase());
                            t1 = tt;
                            break;
                        }
                        break;
                    }
                    if (tt.isNewlineBefore() && tt != t1) 
                        break;
                    if (!tt.chars.isCyrillicLetter()) 
                        break;
                    if (tt != t) {
                        if (_TryAttach(tt, null, lev + 1, false) != null) 
                            break;
                    }
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (tt.chars.isAllLower() && tt != t) {
                        if (npt != null && npt.getMorph().getCase().isGenitive()) {
                        }
                        else 
                            break;
                    }
                    if (npt != null) {
                        if (tmp.length() > 0) 
                            tmp.append(" ").append(npt.getSourceText());
                        else 
                            tmp.append(npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                        t1 = (tt = npt.getEndToken());
                    }
                    else if (tmp.length() > 0) {
                        tmp.append(" ").append(tt.getSourceText());
                        t1 = tt;
                    }
                    else {
                        String s = null;
                        if (tt == t) 
                            s = tt.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        if (s == null) 
                            s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
                        tmp.append(s);
                        t1 = tt;
                    }
                }
                String ss = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(tmp.toString());
                if (t1.getNext() != null && t1.getNext().isComma() && (t1.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                    if (t1.getNext().getNext().isValue("ЧТО", "ЩО") || ((t1.getMorphClassInDictionary().isAdjective() && t1.getMorphClassInDictionary().isVerb()))) {
                        for (com.pullenti.ner.Token tt = t1.getNext().getNext(); tt != null; tt = tt.getNext()) {
                            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                                break;
                            else if (tt.isComma()) {
                                DecreeToken dd = tryAttach(tt.getNext(), null, false);
                                if (dd != null && ((dd.typ == ItemType.DATE || dd.typ == ItemType.NUMBER))) {
                                    t1 = tt;
                                    ss = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt.getPrevious(), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                                }
                                break;
                            }
                            else {
                                DecreeToken dd = tryAttach(tt, null, false);
                                if (dd != null && ((dd.typ == ItemType.DATE || dd.typ == ItemType.NUMBER))) {
                                    t1 = tt.getPrevious();
                                    ss = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt.getPrevious(), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                                }
                                if (dd != null) 
                                    break;
                            }
                        }
                    }
                }
                return _new905(t, t1, ty, ss);
            }
        }
        if (t.isValue("ДАТА", null)) {
            t1 = t.getNext();
            if (t1 != null && t1.getMorph().getCase().isGenitive()) 
                t1 = t1.getNext();
            if (t1 != null && t1.isChar(':')) 
                t1 = t1.getNext();
            DecreeToken res1 = _TryAttach(t1, prev, lev + 1, false);
            if (res1 != null && res1.typ == ItemType.DATE) {
                res1.setBeginToken(t);
                return res1;
            }
        }
        if (t.isValue("ВЕСТНИК", "ВІСНИК") || t.isValue("БЮЛЛЕТЕНЬ", "БЮЛЕТЕНЬ")) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) 
                return _new905(t, npt.getEndToken(), ItemType.TYP, com.pullenti.ner.core.MiscHelper.getTextValue(t, npt.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
                return _new905(t, t.getNext(), ItemType.TYP, com.pullenti.ner.core.MiscHelper.getTextValue(t, t.getNext(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
        }
        if ((prev != null && prev.typ == ItemType.TYP && prev.value != null) && (((prev.value.indexOf("ДОГОВОР") >= 0) || (prev.value.indexOf("ДОГОВІР") >= 0)))) {
            DecreeToken nn = tryAttachName(t, prev.value, false, false);
            if (nn != null) 
                return nn;
            t1 = null;
            for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                if (ttt.isNewlineBefore()) 
                    break;
                DecreeToken ddt1 = _TryAttach(ttt, null, lev + 1, false);
                if (ddt1 != null) 
                    break;
                if (ttt.getMorph()._getClass().isPreposition() || ttt.getMorph()._getClass().isConjunction()) 
                    continue;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt == null) 
                    break;
                ttt = (t1 = npt.getEndToken());
            }
            if (t1 != null) {
                nn = _new906(t, t1, ItemType.NAME);
                nn.value = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.NO);
                return nn;
            }
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.getNext() != null) {
            if ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "Б") && t.getNext().isCharOf("\\/") && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class)).term, "Н")) 
                return _new905(t, t.getNext().getNext(), ItemType.NUMBER, "Б/Н");
        }
        return null;
    }

    private static boolean _isJusNumber(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(tt.term, "A") && com.pullenti.unisharp.Utils.stringsNe(tt.term, "А")) 
            return false;
        if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && (t.getWhitespacesAfterCount() < 2)) {
            if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() > 20) 
                return true;
            return false;
        }
        return false;
    }

    private static com.pullenti.ner.Token _isEdition(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.getMorph()._getClass().isPreposition() && t.getNext() != null) 
            t = t.getNext();
        if (t.isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ") || t.isValue("РЕД", null)) {
            if (t.getNext() != null && t.getNext().isChar('.')) 
                return t.getNext();
            else 
                return t;
        }
        if (t.isValue("ИЗМЕНЕНИЕ", "ЗМІНА") || t.isValue("ИЗМ", null)) {
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
            if ((t.getNext() != null && t.getNext().isComma() && t.getNext().getNext() != null) && t.getNext().getNext().isValue("ВНЕСЕННЫЙ", "ВНЕСЕНИЙ")) 
                return t.getNext().getNext();
            return t;
        }
        if ((t instanceof com.pullenti.ner.NumberToken) && t.getNext() != null && t.getNext().isValue("ЧТЕНИЕ", "ЧИТАННЯ")) 
            return t.getNext().getNext();
        return null;
    }

    public static com.pullenti.ner.ReferentToken _findBackTyp(com.pullenti.ner.Token t, String typeName) {
        if (t == null) 
            return null;
        if (t.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ")) 
            return null;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            cou++;
            if (tt.isNewlineBefore()) 
                cou += 10;
            if (cou > 500) 
                break;
            com.pullenti.ner.decree.DecreeReferent d = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (d == null && (tt.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) 
                d = ((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class)).getOwner();
            if (d == null) 
                continue;
            if (com.pullenti.unisharp.Utils.stringsEq(d.getTyp0(), typeName) || com.pullenti.unisharp.Utils.stringsEq(d.getTyp(), typeName)) 
                return (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
        }
        return null;
    }

    private static com.pullenti.ner.Token _tryAttachNumber(com.pullenti.ner.Token t, StringBuilder tmp, boolean afterNum) {
        com.pullenti.ner.Token t2 = t;
        com.pullenti.ner.Token res = null;
        boolean digs = false;
        boolean br = false;
        for (; t2 != null; t2 = t2.getNext()) {
            if (t2.isCharOf("(),;")) 
                break;
            if (t2.isTableControlChar()) 
                break;
            if (t2.isChar('.') && t2.isWhitespaceAfter()) 
                break;
            if (t2 != t && t2.getWhitespacesBeforeCount() > 1) 
                break;
            if (com.pullenti.ner.core.BracketHelper.isBracket(t2, false)) {
                if (!afterNum) 
                    break;
                if (!br && t2 != t) 
                    break;
                res = t2;
                if (br) 
                    break;
                br = true;
                continue;
            }
            if (!(t2 instanceof com.pullenti.ner.NumberToken) && !(t2 instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(t2.getReferent(), com.pullenti.ner.date.DateReferent.class);
                if (dr != null && !dr.isRelative() && ((t2 == t || !t2.isWhitespaceBefore()))) {
                    if (dr.getYear() > 0 && t2.getLengthChar() == 4) {
                        res = t2;
                        tmp.append(dr.getYear());
                        digs = true;
                        continue;
                    }
                }
                com.pullenti.ner.denomination.DenominationReferent den = (com.pullenti.ner.denomination.DenominationReferent)com.pullenti.unisharp.Utils.cast(t2.getReferent(), com.pullenti.ner.denomination.DenominationReferent.class);
                if (den != null) {
                    res = t2;
                    tmp.append(t2.getSourceText().toUpperCase());
                    for (char c : den.getValue().toCharArray()) {
                        if (Character.isDigit(c)) 
                            digs = true;
                    }
                    if (t2.isWhitespaceAfter()) 
                        break;
                    continue;
                }
                if ((t2.getLengthChar() < 10) && afterNum && !t2.isWhitespaceBefore()) {
                }
                else 
                    break;
            }
            String s = t2.getSourceText();
            if (s == null) 
                break;
            if (t2.isHiphen()) 
                s = "-";
            if (t2.isValue("ОТ", "ВІД")) 
                break;
            if (com.pullenti.unisharp.Utils.stringsEq(s, "\\")) 
                s = "/";
            if (Character.isDigit(s.charAt(0))) {
                for (char d : s.toCharArray()) {
                    digs = true;
                }
            }
            if (!t2.isCharOf("_@")) 
                tmp.append(s);
            res = t2;
            if (t2.isWhitespaceAfter()) {
                if (t2.getWhitespacesAfterCount() > 1) 
                    break;
                if (digs) {
                    if ((t2.getNext() != null && ((t2.getNext().isHiphen() || t2.getNext().isCharOf(".:"))) && !t2.getNext().isWhitespaceAfter()) && (t2.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                        continue;
                }
                if (!afterNum) 
                    break;
                if (t2.isHiphen()) {
                    if (t2.getNext() != null && t2.getNext().isValue("СМ", null)) 
                        break;
                    continue;
                }
                if (t2.isChar('/')) 
                    continue;
                if (t2.getNext() != null) {
                    if (((t2.getNext().isHiphen() || (t2.getNext() instanceof com.pullenti.ner.NumberToken))) && !digs) 
                        continue;
                }
                if (t2 == t && t2.chars.isAllUpper()) 
                    continue;
                if (t2.getNext() instanceof com.pullenti.ner.NumberToken) {
                    if (t2 instanceof com.pullenti.ner.NumberToken) 
                        tmp.append(" ");
                    continue;
                }
                if (((t2.getNext() != null && digs && t2.getNext().isHiphen()) && (t2.getNext().getNext() instanceof com.pullenti.ner.TextToken) && (t2.getNext().getNext().getLengthChar() < 3)) && t2.getNext().getNext().chars.isLetter() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t2.getNext().getNext().getNext(), false, false)) {
                    res = (t2 = t2.getNext().getNext());
                    tmp.append("-").append(t2.getSourceText());
                    continue;
                }
                break;
            }
        }
        if (tmp.length() == 0) {
            if (t != null && t.isChar('_')) {
                for (t2 = t; t2 != null; t2 = t2.getNext()) {
                    if (!t2.isChar('_') || ((t2 != t && t2.isWhitespaceBefore()))) {
                        tmp.append('?');
                        return t2.getPrevious();
                    }
                }
            }
            return null;
        }
        if (!digs && !afterNum) 
            return null;
        char ch = tmp.charAt(tmp.length() - 1);
        if (!Character.isLetterOrDigit(ch) && (res instanceof com.pullenti.ner.TextToken) && !res.isChar('_')) {
            tmp.setLength(tmp.length() - 1);
            res = res.getPrevious();
        }
        if ((res.getNext() != null && res.getNext().isHiphen() && (res.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
            int min;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapmin962 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres963 = com.pullenti.unisharp.Utils.parseInteger(tmp.toString(), 0, null, wrapmin962);
            min = (wrapmin962.value != null ? wrapmin962.value : 0);
            if (inoutres963) {
                if (min < ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getNext().getNext(), com.pullenti.ner.NumberToken.class)).getIntValue()) {
                    res = res.getNext().getNext();
                    tmp.append("-").append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res, com.pullenti.ner.NumberToken.class)).getValue());
                }
            }
        }
        if (res.getNext() != null && !res.isWhitespaceAfter() && res.getNext().isChar('(')) {
            int cou = 0;
            StringBuilder tmp2 = new StringBuilder();
            for (com.pullenti.ner.Token tt = res.getNext().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isChar(')')) {
                    tmp.append("(").append(tmp2.toString()).append(")");
                    res = tt;
                    break;
                }
                if ((++cou) > 5) 
                    break;
                if (tt.isWhitespaceBefore() || tt.isWhitespaceAfter()) 
                    break;
                if (tt instanceof com.pullenti.ner.ReferentToken) 
                    break;
                tmp2.append(tt.getSourceText());
            }
        }
        if (tmp.length() > 2) {
            if (tmp.charAt(tmp.length() - 1) == '3') {
                if (tmp.charAt(tmp.length() - 2) == 'К' || tmp.charAt(tmp.length() - 2) == 'Ф') 
                    tmp.setCharAt(tmp.length() - 1, 'З');
            }
        }
        if ((res.getNext() instanceof com.pullenti.ner.TextToken) && (res.getWhitespacesAfterCount() < 2) && res.getNext().chars.isAllUpper()) {
            if (res.getNext().isValue("РД", null) || res.getNext().isValue("ПД", null)) {
                tmp.append(" ").append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getNext(), com.pullenti.ner.TextToken.class)).term);
                res = res.getNext();
            }
        }
        if ((res.getNext() instanceof com.pullenti.ner.TextToken) && res.getNext().isChar('*')) 
            res = res.getNext();
        return res;
    }

    public static java.util.ArrayList<DecreeToken> tryAttachList(com.pullenti.ner.Token t, DecreeToken prev, int maxCount, boolean mustStartByTyp) {
        DecreeToken p = tryAttach(t, prev, mustStartByTyp);
        if (p == null) 
            return null;
        if (p.typ == ItemType.ORG || p.typ == ItemType.OWNER) {
            if (t.getPrevious() != null && t.getPrevious().isValue("РАССМОТРЕНИЕ", "РОЗГЛЯД")) 
                return null;
        }
        if (p.typ == ItemType.NUMBER && (t instanceof com.pullenti.ner.NumberToken)) {
        }
        java.util.ArrayList<DecreeToken> res = new java.util.ArrayList<DecreeToken>();
        res.add(p);
        if (p.typ == ItemType.TYP) {
            for (com.pullenti.ner.Token ttt = p.getBeginToken().getNext(); ttt != null && ttt.getEndChar() <= p.getEndChar(); ttt = ttt.getNext()) {
                if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    res.add(_new911(ttt, p.getEndToken(), ItemType.TERR, (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.ReferentToken.class)));
                    break;
                }
            }
        }
        com.pullenti.ner.Token tt = p.getEndToken().getNext();
        if (tt != null && t.getPrevious() != null) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, false, null, false)) {
                p.setBeginToken(t.getPrevious());
                p.setEndToken(tt);
                tt = tt.getNext();
            }
        }
        for (; tt != null; tt = tt.getNext()) {
            boolean ws = false;
            if (tt.getWhitespacesBeforeCount() > 15) 
                ws = true;
            if (maxCount > 0 && res.size() >= maxCount) {
                DecreeToken la = res.get(res.size() - 1);
                if (la.typ != ItemType.TYP && la.typ != ItemType.DATE && la.typ != ItemType.NUMBER) 
                    break;
                if (res.size() > (maxCount * 3)) 
                    break;
            }
            DecreeToken p0 = tryAttach(tt, (prev != null ? prev : p), false);
            if (ws) {
                if (p0 == null || p == null) 
                    break;
                if ((((p.typ == ItemType.TYP && p0.typ == ItemType.NUMBER)) || ((p.typ == ItemType.DATE && p0.typ == ItemType.NUMBER)) || ((p0.typ == ItemType.NAME && p.typ != ItemType.NAME))) || ((p0.typ == ItemType.ORG && p.typ == ItemType.ORG))) {
                }
                else if ((((p0.typ == ItemType.DATE || p0.typ == ItemType.NUMBER)) && p.typ == ItemType.ORG && res.size() == 2) && res.get(0).typ == ItemType.TYP) {
                }
                else 
                    break;
            }
            if ((p0 != null && p0.typ == ItemType.TYP && p0.isNewlineBefore()) && res.get(0).typ == ItemType.TYP) 
                break;
            if (p0 == null) {
                if (tt.isNewlineBefore()) 
                    break;
                if (tt.getMorph()._getClass().isPreposition() && res.get(0).typ == ItemType.TYP) 
                    continue;
                if (tt.isChar('.') && p.typ == ItemType.NUMBER && (tt.getWhitespacesAfterCount() < 3)) {
                    p0 = _TryAttach(tt.getNext(), p, 0, false);
                    if (p0 != null && ((p0.typ == ItemType.NAME || p0.typ == ItemType.DATE))) 
                        continue;
                    p0 = null;
                }
                if (((tt.isCommaAnd() || tt.isHiphen())) && res.get(0).typ == ItemType.TYP) {
                    p0 = tryAttach(tt.getNext(), p, false);
                    if (p0 != null) {
                        ItemType ty0 = p0.typ;
                        if (ty0 == ItemType.ORG || ty0 == ItemType.OWNER) 
                            ty0 = ItemType.UNKNOWN;
                        ItemType ty = p.typ;
                        if (ty == ItemType.ORG || ty == ItemType.OWNER) 
                            ty = ItemType.UNKNOWN;
                        if (ty0 == ty || p0.typ == ItemType.EDITION) {
                            p = p0;
                            res.add(p);
                            tt = p.getEndToken();
                            continue;
                        }
                    }
                    p0 = null;
                }
                if (tt.isChar(':')) {
                    p0 = tryAttach(tt.getNext(), p, false);
                    if (p0 != null) {
                        if (p0.typ == ItemType.NUMBER || p0.typ == ItemType.DATE) {
                            p = p0;
                            res.add(p);
                            tt = p.getEndToken();
                            continue;
                        }
                    }
                }
                if (tt.isComma() && p.typ == ItemType.NUMBER) {
                    p0 = tryAttach(tt.getNext(), p, false);
                    if (p0 != null && p0.typ == ItemType.DATE) {
                        p = p0;
                        res.add(p);
                        tt = p.getEndToken();
                        continue;
                    }
                    int cou = 0;
                    if (res.get(0).typ == ItemType.TYP) {
                        for (int ii = 1; ii < res.size(); ii++) {
                            if ((res.get(ii).typ == ItemType.ORG || res.get(ii).typ == ItemType.TERR || res.get(ii).typ == ItemType.UNKNOWN) || res.get(ii).typ == ItemType.OWNER) 
                                cou++;
                            else 
                                break;
                        }
                        if (cou > 1) {
                            StringBuilder num = new StringBuilder(p.value);
                            StringBuilder tmp = new StringBuilder();
                            com.pullenti.ner.Token tEnd = null;
                            for (com.pullenti.ner.Token tt1 = tt; tt1 != null; tt1 = tt1.getNext()) {
                                if (!tt1.isCommaAnd()) 
                                    break;
                                DecreeToken pp = tryAttach(tt1.getNext(), p, false);
                                if (pp != null) 
                                    break;
                                if (!(tt1.getNext() instanceof com.pullenti.ner.NumberToken)) 
                                    break;
                                tmp.setLength(0);
                                com.pullenti.ner.Token tt2 = _tryAttachNumber(tt1.getNext(), tmp, true);
                                if (tt2 == null) 
                                    break;
                                num.append(",").append(tmp.toString());
                                cou--;
                                tt1 = (tEnd = tt2);
                            }
                            if (cou == 1) {
                                p.value = num.toString();
                                tt = p.setEndToken(tEnd);
                                continue;
                            }
                        }
                    }
                    p0 = null;
                }
                if (tt.isComma() && p.typ == ItemType.DATE) {
                    p0 = tryAttach(tt.getNext(), p, false);
                    if (p0 != null && p0.typ == ItemType.NUMBER) {
                        p = p0;
                        res.add(p);
                        tt = p.getEndToken();
                        continue;
                    }
                    p0 = null;
                }
                if (tt.isCommaAnd() && ((p.typ == ItemType.ORG || p.typ == ItemType.OWNER))) {
                    p0 = tryAttach(tt.getNext(), p, false);
                    if (p0 != null && ((p0.typ == ItemType.ORG || p.typ == ItemType.OWNER))) {
                        p = p0;
                        res.add(p);
                        tt = p.getEndToken();
                        continue;
                    }
                    p0 = null;
                }
                if (res.get(0).typ == ItemType.TYP) {
                    if (getKind(res.get(0).value) == com.pullenti.ner.decree.DecreeKind.PUBLISHER) {
                        if (tt.isCharOf(",;")) 
                            continue;
                        if ((((p = tryAttach(tt, (prev != null ? prev : res.get(0)), false)))) != null) {
                            res.add(p);
                            tt = p.getEndToken();
                            continue;
                        }
                    }
                }
                if (res.get(res.size() - 1).typ == ItemType.UNKNOWN && prev != null) {
                    p0 = tryAttach(tt, res.get(res.size() - 1), false);
                    if (p0 != null) {
                        p = p0;
                        res.add(p);
                        tt = p.getEndToken();
                        continue;
                    }
                }
                if ((((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllUpper() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt.getNext(), false, false)) && res.size() > 1 && res.get(res.size() - 1).typ == ItemType.NUMBER) && res.get(res.size() - 2).typ == ItemType.TYP && res.get(res.size() - 2).typKind == com.pullenti.ner.decree.DecreeKind.STANDARD) 
                    continue;
                if (tt.isChar('(')) {
                    p = tryAttach(tt.getNext(), null, false);
                    if (p != null && p.typ == ItemType.EDITION) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null) {
                            res.add(p);
                            for (tt = p.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                                if (tt.getEndChar() >= br.getEndChar()) 
                                    break;
                                p = tryAttach(tt, null, false);
                                if (p != null) {
                                    res.add(p);
                                    tt = p.getEndToken();
                                }
                            }
                            tt = res.get(res.size() - 1).setEndToken(br.getEndToken());
                            continue;
                        }
                    }
                }
                if ((tt instanceof com.pullenti.ner.NumberToken) && res.get(res.size() - 1).typ == ItemType.DATE) {
                    if (tt.getPrevious() != null && tt.getPrevious().getMorph()._getClass().isPreposition()) {
                    }
                    else if (com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt) != null) {
                    }
                    else {
                        StringBuilder tmp = new StringBuilder();
                        com.pullenti.ner.Token t11 = _tryAttachNumber(tt, tmp, false);
                        if (t11 != null) 
                            p0 = _new905(tt, t11, ItemType.NUMBER, tmp.toString());
                    }
                }
                if (p0 == null) 
                    break;
            }
            if ((!p0.isNewlineBefore() && p.typ == ItemType.NAME && ((p0.typ == ItemType.ORG || p0.typ == ItemType.OWNER || p0.typ == ItemType.TERR))) && !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(p.getEndToken(), true, null, false)) {
                tt = p.setEndToken(p0.getEndToken());
                p.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(p, com.pullenti.ner.core.GetTextAttr.NO);
                continue;
            }
            p = p0;
            res.add(p);
            tt = p.getEndToken();
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).getEndToken().getNext() != null && res.get(i).getEndToken().getNext().isComma()) 
                continue;
            if (res.get(i).typ == ItemType.UNKNOWN && res.get(i + 1).typ == ItemType.UNKNOWN) {
                res.get(i).value = (res.get(i).value + " " + res.get(i + 1).value);
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.remove(i + 1);
                i--;
            }
            else if (((res.get(i).typ == ItemType.ORG || res.get(i).typ == ItemType.OWNER)) && res.get(i + 1).typ == ItemType.UNKNOWN) {
                boolean ok = false;
                if (res.get(i + 1).getBeginToken().getPrevious().isComma()) {
                }
                else if (((i + 2) < res.size()) && res.get(i + 2).typ == ItemType.DATE) 
                    ok = true;
                if (ok) {
                    res.get(i).typ = ItemType.OWNER;
                    res.get(i).value = (res.get(i).value + " " + res.get(i + 1).value);
                    res.get(i).setEndToken(res.get(i + 1).getEndToken());
                    res.get(i).ref = null;
                    res.remove(i + 1);
                    i--;
                }
            }
            else if (((res.get(i).typ == ItemType.UNKNOWN || res.get(i).typ == ItemType.OWNER)) && ((res.get(i + 1).typ == ItemType.ORG || res.get(i + 1).typ == ItemType.OWNER))) {
                boolean ok = false;
                if ((res.get(i).typ == ItemType.OWNER || res.get(i + 1).typ == ItemType.OWNER || com.pullenti.unisharp.Utils.stringsEq(res.get(i).value, "Пленум")) || com.pullenti.unisharp.Utils.stringsEq(res.get(i).value, "Сессия") || com.pullenti.unisharp.Utils.stringsEq(res.get(i).value, "Съезд")) 
                    ok = true;
                if (ok) {
                    res.get(i).typ = ItemType.OWNER;
                    res.get(i).setEndToken(res.get(i + 1).getEndToken());
                    if (res.get(i).value != null) {
                        String s1 = res.get(i + 1).value;
                        if (s1 == null) 
                            s1 = res.get(i + 1).ref.referent.toString();
                        res.get(i).value = (res.get(i).value + ", " + s1);
                    }
                    res.remove(i + 1);
                    i--;
                }
            }
            else if ((res.get(i).typ == ItemType.TYP && res.get(i + 1).typ == ItemType.TERR && ((i + 2) < res.size())) && res.get(i + 2).typ == ItemType.STDNAME) {
                res.get(i).fullValue = (res.get(i).value + " " + res.get(i + 2).value);
                res.get(i + 1).setEndToken(res.get(i + 2).getEndToken());
                res.remove(i + 2);
                i--;
            }
            else {
                boolean ok = false;
                if (res.get(i).typ == ItemType.UNKNOWN && ((((res.get(i + 1).typ == ItemType.TERR && prev != null)) || res.get(i + 1).typ == ItemType.OWNER))) 
                    ok = true;
                else if (((res.get(i).typ == ItemType.UNKNOWN || res.get(i).typ == ItemType.ORG || res.get(i).typ == ItemType.OWNER)) && res.get(i + 1).typ == ItemType.TERR) 
                    ok = true;
                if (ok) {
                    res.get(i).typ = ItemType.OWNER;
                    res.get(i).setEndToken(res.get(i + 1).getEndToken());
                    String s1 = res.get(i + 1).value;
                    if (s1 == null) 
                        s1 = res.get(i + 1).ref.referent.toString();
                    res.get(i).value = (res.get(i).value + ", " + s1);
                    res.remove(i + 1);
                    i--;
                }
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == ItemType.UNKNOWN) {
                int j;
                boolean ok = false;
                for (j = i + 1; j < res.size(); j++) {
                    if (res.get(j).getBeginToken().getPrevious().isComma()) 
                        break;
                    else if (res.get(j).typ == ItemType.DATE || res.get(j).typ == ItemType.NUMBER) {
                        ok = true;
                        break;
                    }
                    else if (res.get(j).typ == ItemType.TERR || res.get(j).typ == ItemType.ORG || res.get(j).typ == ItemType.UNKNOWN) {
                    }
                    else 
                        break;
                }
                if (!ok) 
                    continue;
                if (j == (i + 1)) {
                    if (res.get(i).getBeginToken().getPrevious().isComma()) 
                        res.get(i).typ = ItemType.OWNER;
                    continue;
                }
                StringBuilder tmp = new StringBuilder();
                for (int ii = i; ii < j; ii++) {
                    if (ii > i) {
                        if (res.get(ii).typ == ItemType.TERR) 
                            tmp.append(", ");
                        else 
                            tmp.append(' ');
                    }
                    if (res.get(ii).value != null) 
                        tmp.append(res.get(ii).value);
                    else if (res.get(ii).ref != null && res.get(ii).ref.referent != null) 
                        tmp.append(res.get(ii).ref.referent.toString());
                }
                res.get(i).value = tmp.toString();
                res.get(i).setEndToken(res.get(j - 1).getEndToken());
                res.get(i).typ = ItemType.OWNER;
                for(int jjj = i + 1 + j - i - 1 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
            }
        }
        if ((res.size() == 3 && res.get(0).typ == ItemType.TYP && ((res.get(1).typ == ItemType.OWNER || res.get(1).typ == ItemType.ORG || res.get(1).typ == ItemType.TERR))) && res.get(2).typ == ItemType.NUMBER) {
            com.pullenti.ner.Token te = res.get(2).getEndToken().getNext();
            for (; te != null; te = te.getNext()) {
                if (!te.isChar(',') || te.getNext() == null) 
                    break;
                java.util.ArrayList<DecreeToken> res1 = tryAttachList(te.getNext(), res.get(0), 10, false);
                if (res1 == null || (res1.size() < 2)) 
                    break;
                if (((res1.get(0).typ == ItemType.OWNER || res1.get(0).typ == ItemType.ORG || res1.get(0).typ == ItemType.TERR)) && res1.get(1).typ == ItemType.NUMBER) {
                    com.pullenti.unisharp.Utils.addToArrayList(res, res1);
                    te = res1.get(res1.size() - 1).getEndToken();
                }
                else 
                    break;
            }
        }
        if (res.size() > 1 && ((res.get(res.size() - 1).typ == ItemType.OWNER || res.get(res.size() - 1).typ == ItemType.ORG))) {
            com.pullenti.ner.Token te = res.get(res.size() - 1).getEndToken().getNext();
            if (te != null && te.isCommaAnd()) {
                java.util.ArrayList<DecreeToken> res1 = tryAttachList(te.getNext(), res.get(0), 10, false);
                if (res1 != null && res1.size() > 0) {
                    if (res1.get(0).typ == ItemType.OWNER || res1.get(0).typ == ItemType.ORG) 
                        com.pullenti.unisharp.Utils.addToArrayList(res, res1);
                }
            }
        }
        return res;
    }

    public static DecreeToken tryAttachName(com.pullenti.ner.Token t, String _typ, boolean veryProbable, boolean inTitleDocRef) {
        if (t == null) 
            return null;
        if (t.isChar(';')) 
            t = t.getNext();
        if (t == null) 
            return null;
        com.pullenti.ner.mail.internal.MailLine li = com.pullenti.ner.mail.internal.MailLine.parse(t, 0, 20);
        if (li != null) {
            if (li.typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO) 
                return null;
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        boolean abou = false;
        com.pullenti.ner.decree.DecreeKind ty = getKind(_typ);
        if (t.isValue("О", null) || t.isValue("ОБ", null) || t.isValue("ПРО", null)) {
            t = t.getNext();
            abou = true;
        }
        else if (t.isValue("ПО", null)) {
            if (com.pullenti.morph.LanguageHelper.endsWith(_typ, "ЗАКОН")) 
                return null;
            t = t.getNext();
            abou = true;
            if (t != null) {
                if (t.isValue("ПОЗИЦИЯ", null)) 
                    return null;
            }
        }
        else {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null && br.isQuoteType()) {
                    com.pullenti.ner.Referent re = t.getNext().getReferent();
                    if (re != null && com.pullenti.unisharp.Utils.stringsEq(re.getTypeName(), "URI")) 
                        return null;
                    if (t.getNext().chars.isLetter()) {
                        if (t.getNext().chars.isAllLower() || (((t.getNext() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).isPureVerb()))) 
                            return null;
                    }
                    t1 = br.getEndToken();
                    com.pullenti.ner.Token tt1 = _tryAttachStdChangeName(t.getNext());
                    if (tt1 != null) 
                        t1 = tt1;
                    String s0 = com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                    if (com.pullenti.unisharp.Utils.isNullOrEmpty(s0)) 
                        return null;
                    if ((s0.length() < 10) && com.pullenti.unisharp.Utils.stringsNe(_typ, "ПРОГРАММА") && com.pullenti.unisharp.Utils.stringsNe(_typ, "ПРОГРАМА")) 
                        return null;
                    return _new905(t, t1, ItemType.NAME, s0);
                }
                DecreeToken dt = tryAttachName(t.getNext(), _typ, false, false);
                if (dt != null) {
                    dt.setBeginToken(t);
                    return dt;
                }
            }
            if (ty != com.pullenti.ner.decree.DecreeKind.KONVENTION && ty != com.pullenti.ner.decree.DecreeKind.PROGRAM && ty != com.pullenti.ner.decree.DecreeKind.USTAV) 
                return null;
        }
        if (t == null) 
            return null;
        if (t.isValue("ЗАЯВЛЕНИЕ", "ЗАЯВА")) 
            return null;
        com.pullenti.ner.Token tt;
        int cou = 0;
        java.util.ArrayList<com.pullenti.ner.core.NounPhraseToken> npts = new java.util.ArrayList<com.pullenti.ner.core.NounPhraseToken>();
        java.util.ArrayList<com.pullenti.ner.core.ConjunctionToken> conjs = null;
        for (tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore() && tt != t) {
                if (tt.getWhitespacesBeforeCount() > 15 || !abou) 
                    break;
                if (tt.isValue("ИСТОЧНИК", null)) 
                    break;
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && tt.chars.isAllLower()) {
                }
                else 
                    break;
            }
            if (tt.isCharOf("(,") && tt.getNext() != null) {
                if (tt.getNext().isValue("УТВЕРЖДЕННЫЙ", "ЗАТВЕРДЖЕНИЙ") || tt.getNext().isValue("ПРИНЯТЫЙ", "ПРИЙНЯТИЙ") || tt.getNext().isValue("УТВ", "ЗАТВ")) {
                    com.pullenti.ner.Token ttt = tt.getNext().getNext();
                    if (ttt != null && ttt.isChar('.') && tt.getNext().isValue("УТВ", null)) 
                        ttt = ttt.getNext();
                    DecreeToken dt = DecreeToken.tryAttach(ttt, null, false);
                    if (dt != null && dt.typ == ItemType.TYP) 
                        break;
                    if (dt != null && ((dt.typ == ItemType.ORG || dt.typ == ItemType.OWNER))) {
                        DecreeToken dt2 = DecreeToken.tryAttach(dt.getEndToken().getNext(), null, false);
                        if (dt2 != null && dt2.typ == ItemType.DATE) 
                            break;
                    }
                }
            }
            if (veryProbable && abou && !tt.isNewlineBefore()) {
                t1 = tt;
                continue;
            }
            if (tt.isValue("ОТ", "ВІД")) {
                DecreeToken dt = DecreeToken.tryAttach(tt, null, false);
                if (dt != null) 
                    break;
            }
            if (tt.getMorph()._getClass().isPreposition() && tt.getNext() != null && (((tt.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent) || (tt.getNext().getReferent() instanceof com.pullenti.ner.date.DateRangeReferent)))) 
                break;
            if (inTitleDocRef) {
                t1 = tt;
                continue;
            }
            com.pullenti.ner.core.ConjunctionToken conj = com.pullenti.ner.core.ConjunctionHelper.tryParse(tt);
            if (conj != null) {
                if (cou == 0) 
                    break;
                if (conj.getEndToken().getNext() == null) 
                    break;
                tt = conj.getEndToken();
                if (conjs == null) 
                    conjs = new java.util.ArrayList<com.pullenti.ner.core.ConjunctionToken>();
                conjs.add(conj);
                continue;
            }
            if (!tt.chars.isCyrillicLetter()) 
                break;
            if (tt.getMorph()._getClass().isPersonalPronoun() || tt.getMorph()._getClass().isPronoun()) {
                if (!tt.isValue("ВСЕ", "ВСІ") && !tt.isValue("ВСЯКИЙ", null) && !tt.isValue("ДАННЫЙ", "ДАНИЙ")) 
                    break;
            }
            if (tt instanceof com.pullenti.ner.NumberToken) 
                break;
            PartToken pit = PartToken.tryAttach(tt, null, false, false);
            if (pit != null) 
                break;
            com.pullenti.ner.Referent r = tt.getReferent();
            if (r != null) {
                if (((r instanceof com.pullenti.ner.decree.DecreeReferent) || (r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent)) || (r instanceof com.pullenti.ner.geo.GeoReferent) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "NAMEDENTITY")) {
                    if (tt.isNewlineBefore()) 
                        break;
                    if (ty == com.pullenti.ner.decree.DecreeKind.USTAV || ty == com.pullenti.ner.decree.DecreeKind.KODEX) {
                        if (r instanceof com.pullenti.ner.geo.GeoReferent) 
                            break;
                    }
                    t1 = tt;
                    continue;
                }
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS.value())), 0, null);
            if (npt == null) {
                if (tt.getMorph()._getClass().isPreposition()) 
                    continue;
                break;
            }
            com.pullenti.morph.MorphClass dd = npt.getEndToken().getMorphClassInDictionary();
            if (dd.isVerb() && npt.getEndToken() == npt.getBeginToken()) {
                if (!dd.isNoun()) 
                    break;
                if (tt.isValue("БЫТЬ", "БУТИ")) 
                    break;
            }
            if (!npt.getMorph().getCase().isGenitive()) {
                if (cou > 0) {
                    if ((npt.getMorph().getCase().isInstrumental() && tt.getPrevious() != null && tt.getPrevious().getPrevious() != null) && (tt.getPrevious().getPrevious().isValue("РАБОТА", "РОБОТА"))) {
                    }
                    else if (abou && veryProbable) {
                    }
                    else if (npt.noun.isValue("ГОД", "РІК") || npt.noun.isValue("ПЕРИОД", "ПЕРІОД")) {
                    }
                    else {
                        boolean ok = false;
                        for (com.pullenti.ner.core.NounPhraseToken n : npts) {
                            java.util.ArrayList<com.pullenti.semantic.core.SemanticLink> links = com.pullenti.semantic.core.SemanticHelper.tryCreateLinks(n, npt, null);
                            if (links.size() > 0) {
                                ok = true;
                                break;
                            }
                            if (n == npts.get(npts.size() - 1)) {
                                if (!((com.pullenti.morph.MorphCase.ooBitand(npts.get(npts.size() - 1).getMorph().getCase(), npt.getMorph().getCase()))).isUndefined()) 
                                    ok = true;
                            }
                        }
                        if (!ok) 
                            break;
                    }
                }
                if (!abou) 
                    break;
            }
            cou++;
            tt = (t1 = npt.getEndToken());
            if (npt.noun.isValue("НАЛОГОПЛАТЕЛЬЩИК", null)) {
                com.pullenti.ner.Token ttn = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt.getNext());
                if ((ttn instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ttn, com.pullenti.ner.NumberToken.class)).getValue(), "1")) 
                    tt = (t1 = ttn);
            }
            npts.add(npt);
        }
        if (tt == t) 
            return null;
        if (abou) {
            com.pullenti.ner.Token tt1 = _tryAttachStdChangeName(t0);
            if (tt1 != null && tt1.getEndChar() > t1.getEndChar()) 
                t1 = tt1;
        }
        if (conjs != null) {
            for (int i = conjs.size() - 1; i >= 0; i--) {
                if (conjs.get(i).typ == com.pullenti.ner.core.ConjunctionType.AND) 
                    break;
                else {
                    t1 = conjs.get(i).getBeginToken().getPrevious();
                    if (t1.getMorphClassInDictionary().isPreposition()) 
                        t1 = t1.getPrevious();
                }
            }
        }
        if (t0.getPrevious() != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0.getPrevious(), true, false) && !com.pullenti.ner.core.BracketHelper.isBracket(t1, false)) {
            int co = 0;
            for (com.pullenti.ner.Token ttt = t1.getNext(); ttt != null && (cou < 40); ttt = ttt.getNext(),co++) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(ttt, true, t0.getPrevious(), false)) {
                    t1 = ttt;
                    t0 = t0.getPrevious();
                    break;
                }
                if (com.pullenti.ner.core.BracketHelper.isBracket(ttt, true)) 
                    break;
            }
        }
        String s = com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(s) || (s.length() < 4)) 
            return null;
        if ((s.length() < 10) && ty != com.pullenti.ner.decree.DecreeKind.KODEX && ty != com.pullenti.ner.decree.DecreeKind.USTAV) 
            return null;
        return _new905(t0, t1, ItemType.NAME, s);
    }

    public static com.pullenti.ner.Token _tryAttachStdChangeName(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken) || t.getNext() == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        if ((com.pullenti.unisharp.Utils.stringsNe(term, "О") && com.pullenti.unisharp.Utils.stringsNe(term, "O") && com.pullenti.unisharp.Utils.stringsNe(term, "ОБ")) && com.pullenti.unisharp.Utils.stringsNe(term, "ПРО")) 
            return null;
        t = t.getNext();
        if (((t.isValue("ВНЕСЕНИЕ", "ВНЕСЕННЯ") || t.isValue("УТВЕРЖДЕНИЕ", "ТВЕРДЖЕННЯ") || t.isValue("ПРИНЯТИЕ", "ПРИЙНЯТТЯ")) || t.isValue("ВВЕДЕНИЕ", "ВВЕДЕННЯ") || t.isValue("ПРИОСТАНОВЛЕНИЕ", "ПРИЗУПИНЕННЯ")) || t.isValue("ОТМЕНА", "СКАСУВАННЯ") || t.isValue("МЕРА", "ЗАХІД")) {
        }
        else if (t.isValue("ПРИЗНАНИЕ", "ВИЗНАННЯ") && t.getNext() != null && t.getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) {
        }
        else 
            return null;
        com.pullenti.ner.Token t1 = t;
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) {
            }
            if (tt.getWhitespacesBeforeCount() > 15) 
                break;
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                break;
            if (tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if (tt.isComma()) 
                continue;
            com.pullenti.ner.ReferentToken rtt = com.pullenti.ner.decree.DecreeAnalyzer.tryAttachApproved(tt, null);
            if (rtt != null && t0.getPrevious() != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0.getPrevious(), true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t0.getPrevious(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null && (br.getEndChar() < rtt.getEndChar())) 
                    rtt = null;
            }
            if (rtt != null) {
                t1 = (tt = rtt.getEndToken());
                continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null) {
                if ((((((npt.noun.isValue("ВВЕДЕНИЕ", "ВВЕДЕННЯ") || npt.noun.isValue("ПРИОСТАНОВЛЕНИЕ", "ПРИЗУПИНЕННЯ") || npt.noun.isValue("ВНЕСЕНИЕ", "ВНЕСЕННЯ")) || npt.noun.isValue("ИЗМЕНЕНИЕ", "ЗМІНА") || npt.noun.isValue("ДОПОЛНЕНИЕ", "ДОДАТОК")) || npt.noun.isValue("АКТ", null) || npt.noun.isValue("ПРИЗНАНИЕ", "ВИЗНАННЯ")) || npt.noun.isValue("ПРИНЯТИЕ", "ПРИЙНЯТТЯ") || npt.noun.isValue("СИЛА", "ЧИННІСТЬ")) || npt.noun.isValue("ДЕЙСТВИЕ", "ДІЯ") || npt.noun.isValue("СВЯЗЬ", "ЗВЯЗОК")) || npt.noun.isValue("РЕАЛИЗАЦИЯ", "РЕАЛІЗАЦІЯ") || npt.noun.isValue("РЯД", null)) {
                    t1 = (tt = npt.getEndToken());
                    continue;
                }
            }
            if (tt.isValue("ТАКЖЕ", "ТАКОЖ") || tt.isValue("НЕОБХОДИМЫЙ", "НЕОБХІДНИЙ")) 
                continue;
            com.pullenti.ner.Referent r = tt.getReferent();
            if ((r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.decree.DecreeReferent) || (r instanceof com.pullenti.ner.decree.DecreePartReferent)) {
                t1 = tt;
                continue;
            }
            if ((r instanceof com.pullenti.ner._org.OrganizationReferent) && tt.isNewlineAfter()) {
                t1 = tt;
                continue;
            }
            java.util.ArrayList<PartToken> pts = PartToken.tryAttachList(tt, false, 40);
            while (pts != null && pts.size() > 0) {
                if (pts.get(0).typ == PartToken.ItemType.PREFIX) 
                    pts.remove(0);
                else 
                    break;
            }
            if (pts != null && pts.size() > 0) {
                t1 = (tt = pts.get(pts.size() - 1).getEndToken());
                continue;
            }
            java.util.ArrayList<DecreeToken> dts = DecreeToken.tryAttachList(tt, null, 10, false);
            if (dts != null && dts.size() > 0) {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = com.pullenti.ner.decree.DecreeAnalyzer.tryAttach(dts, null, null);
                if (rts != null) {
                    t1 = (tt = rts.get(0).getEndToken());
                    continue;
                }
                if (dts.get(0).typ == ItemType.TYP) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.decree.DecreeAnalyzer.tryAttachApproved(tt, null);
                    if (rt != null) {
                        t1 = (tt = rt.getEndToken());
                        continue;
                    }
                }
            }
            com.pullenti.ner.Token tt1 = isKeyword(tt, false);
            if (tt1 != null) {
                t1 = (tt = tt1);
                continue;
            }
            if (tt instanceof com.pullenti.ner.NumberToken) 
                continue;
            if (!tt.chars.isAllLower() && tt.getLengthChar() > 2 && tt.getMorphClassInDictionary().isUndefined()) {
                t1 = tt;
                continue;
            }
            break;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0.getPrevious(), true, false)) {
            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), true, t0.getPrevious(), false)) 
                t1 = t1.getNext();
        }
        return t1;
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    private static com.pullenti.ner.core.TerminCollection m_Keywords;

    public static void initialize() {
        if (m_Termins != null) 
            return;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        m_Keywords = new com.pullenti.ner.core.TerminCollection();
        for (String s : m_MiscTypesRU) {
            m_Keywords.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        for (String s : m_MiscTypesUA) {
            m_Keywords.add(com.pullenti.ner.core.Termin._new968(s, com.pullenti.morph.MorphLang.UA));
        }
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new969("ТЕХНИЧЕСКОЕ ЗАДАНИЕ", "ТЗ");
        t.addVariant("ТЕХЗАДАНИЕ", false);
        t.addAbridge("ТЕХ. ЗАДАНИЕ");
        m_Keywords.add(t);
        t = com.pullenti.ner.core.Termin._new969("ТЕХНИКО КОММЕРЧЕСКОЕ ПРЕДЛОЖЕНИЕ", "ТКП");
        t.addVariant("КОММЕРЧЕСКОЕ ПРЕДЛОЖЕНИЕ", false);
        m_Keywords.add(t);
        for (String s : m_AllTypesRU) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, ItemType.TYP));
            m_Keywords.add(com.pullenti.ner.core.Termin._new100(s, ItemType.TYP));
        }
        for (String s : m_AllTypesUA) {
            m_Termins.add(com.pullenti.ner.core.Termin._new101(s, ItemType.TYP, com.pullenti.morph.MorphLang.UA));
            m_Keywords.add(com.pullenti.ner.core.Termin._new101(s, ItemType.TYP, com.pullenti.morph.MorphLang.UA));
        }
        m_Termins.add(com.pullenti.ner.core.Termin._new100("ОТРАСЛЕВОЕ СОГЛАШЕНИЕ", ItemType.TYP));
        m_Termins.add(com.pullenti.ner.core.Termin._new372("ГАЛУЗЕВА УГОДА", com.pullenti.morph.MorphLang.UA, ItemType.TYP));
        m_Termins.add(com.pullenti.ner.core.Termin._new100("МЕЖОТРАСЛЕВОЕ СОГЛАШЕНИЕ", ItemType.TYP));
        m_Termins.add(com.pullenti.ner.core.Termin._new372("МІЖГАЛУЗЕВА УГОДА", com.pullenti.morph.MorphLang.UA, ItemType.TYP));
        m_Termins.add(com.pullenti.ner.core.Termin._new102("ОСНОВЫ ЗАКОНОДАТЕЛЬСТВА", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX));
        m_Termins.add(com.pullenti.ner.core.Termin._new980("ОСНОВИ ЗАКОНОДАВСТВА", com.pullenti.morph.MorphLang.UA, ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX));
        m_Termins.add(com.pullenti.ner.core.Termin._new102("ОСНОВЫ ГРАЖДАНСКОГО ЗАКОНОДАТЕЛЬСТВА", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX));
        m_Termins.add(com.pullenti.ner.core.Termin._new980("ОСНОВИ ЦИВІЛЬНОГО ЗАКОНОДАВСТВА", com.pullenti.morph.MorphLang.UA, ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX));
        t = com.pullenti.ner.core.Termin._new128("ФЕДЕРАЛЬНЫЙ ЗАКОН", ItemType.TYP, "ФЗ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new984("ФЕДЕРАЛЬНИЙ ЗАКОН", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ФЗ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПРОЕКТ ЗАКОНА", ItemType.TYP);
        t.addVariant("ЗАКОНОПРОЕКТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПАСПОРТ ПРОЕКТА", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new372("ПРОЕКТ ЗАКОНУ", com.pullenti.morph.MorphLang.UA, ItemType.TYP);
        t.addVariant("ЗАКОНОПРОЕКТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new372("ПАСПОРТ ПРОЕКТУ", com.pullenti.morph.MorphLang.UA, ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new126("ГОСУДАРСТВЕННАЯ ПРОГРАММА", "ПРОГРАММА", ItemType.TYP);
        t.addVariant("ГОСУДАРСТВЕННАЯ ЦЕЛЕВАЯ ПРОГРАММА", false);
        t.addVariant("ФЕДЕРАЛЬНАЯ ЦЕЛЕВАЯ ПРОГРАММА", false);
        t.addAbridge("ФЕДЕРАЛЬНАЯ ПРОГРАММА");
        t.addVariant("МЕЖГОСУДАРСТВЕННАЯ ЦЕЛЕВАЯ ПРОГРАММА", false);
        t.addAbridge("МЕЖГОСУДАРСТВЕННАЯ ПРОГРАММА");
        t.addVariant("ГОСПРОГРАММА", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new990("ДЕРЖАВНА ПРОГРАМА", "ПРОГРАМА", com.pullenti.morph.MorphLang.UA, ItemType.TYP);
        t.addVariant("ДЕРЖАВНА ЦІЛЬОВА ПРОГРАМА", false);
        t.addVariant("ФЕДЕРАЛЬНА ЦІЛЬОВА ПРОГРАМА", false);
        t.addAbridge("ФЕДЕРАЛЬНА ПРОГРАМА");
        t.addVariant("ДЕРЖПРОГРАМА", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new128("ФЕДЕРАЛЬНЫЙ КОНСТИТУЦИОННЫЙ ЗАКОН", ItemType.TYP, "ФКЗ");
        t.addVariant("КОНСТИТУЦИОННЫЙ ЗАКОН", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new984("ФЕДЕРАЛЬНИЙ КОНСТИТУЦІЙНИЙ ЗАКОН", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ФКЗ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("КОДЕКС", ItemType.TYP);
        t.addVariant("МЕЖДУНАРОДНЫЙ КОДЕКС", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("УГОЛОВНЫЙ КОДЕКС", ItemType.TYP, "УК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КРИМИНАЛЬНЫЙ КОДЕКС", ItemType.TYP, "КК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КРИМІНАЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("УГОЛОВНО-ПРОЦЕССУАЛЬНЫЙ КОДЕКС", ItemType.TYP, "УПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КРИМІНАЛЬНО-ПРОЦЕСУАЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("УГОЛОВНО-ИСПОЛНИТЕЛЬНЫЙ КОДЕКС", ItemType.TYP, "УИК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КРИМІНАЛЬНО-ВИКОНАВЧИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КВК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ГРАЖДАНСКИЙ КОДЕКС", ItemType.TYP, "ГК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ЦИВІЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ЦК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ГРАЖДАНСКИЙ ПРОЦЕССУАЛЬНЫЙ КОДЕКС", ItemType.TYP, "ГПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ЦИВІЛЬНИЙ ПРОЦЕСУАЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ЦПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ГРАДОСТРОИТЕЛЬНЫЙ КОДЕКС", ItemType.TYP, "ГРК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("МІСТОБУДІВНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "МБК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ХОЗЯЙСТВЕННЫЙ КОДЕКС", ItemType.TYP, "ХК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ГОСПОДАРСЬКИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ГК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ХОЗЯЙСТВЕННЫЙ ПРОЦЕССУАЛЬНЫЙ КОДЕКС", ItemType.TYP, "ХПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ГОСПОДАРСЬКИЙ ПРОЦЕСУАЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ГПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("АРБИТРАЖНЫЙ ПРОЦЕССУАЛЬНЫЙ КОДЕКС", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addAbridge("АПК");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new980("АРБІТРАЖНИЙ ПРОЦЕСУАЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addAbridge("АПК");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КОДЕКС ВНУТРЕННЕГО ВОДНОГО ТРАНСПОРТА", ItemType.TYP, "КВВТ", com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addVariant("КВ ВТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ТРУДОВОЙ КОДЕКС", ItemType.TYP, "ТК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ТРУДОВИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ТК", com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addVariant("ВИПРАВНО ТРУДОВИЙ КОДЕКС", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("КОДЕКС ЗАКОНОВ О ТРУДЕ", "КЗОТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КОДЕКС ЗАКОНІВ ПРО ПРАЦЮ", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КЗПП", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ЖИЛИЩНЫЙ КОДЕКС", ItemType.TYP, "ЖК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ЖИТЛОВИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ЖК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ЗЕМЕЛЬНЫЙ КОДЕКС", ItemType.TYP, "ЗК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ЗЕМЕЛЬНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ЗК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ЛЕСНОЙ КОДЕКС", ItemType.TYP, "ЛК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ЛІСОВИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ЛК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("БЮДЖЕТНЫЙ КОДЕКС", ItemType.TYP, "БК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("БЮДЖЕТНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "БК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("НАЛОГОВЫЙ КОДЕКС", ItemType.TYP, "НК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ПОДАТКОВИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("СЕМЕЙНЫЙ КОДЕКС", ItemType.TYP, "СК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("СІМЕЙНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "СК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ВОДНЫЙ КОДЕКС", ItemType.TYP, "ВК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ВОДНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ВК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ВОЗДУШНЫЙ КОДЕКС", ItemType.TYP, "ВК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ПОВІТРЯНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ПК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КОДЕКС ОБ АДМИНИСТРАТИВНЫХ ПРАВОНАРУШЕНИЯХ", ItemType.TYP, "КОАП", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КОДЕКС ПРО АДМІНІСТРАТИВНІ ПРАВОПОРУШЕННЯ", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КПАП", com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addVariant("КУПАП", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОБ АДМИНИСТРАТИВНЫХ ПРАВОНАРУШЕНИЯХ", ItemType.STDNAME);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new372("ПРО АДМІНІСТРАТИВНІ ПРАВОПОРУШЕННЯ", com.pullenti.morph.MorphLang.UA, ItemType.STDNAME);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new372("ПРО НАДРА", com.pullenti.morph.MorphLang.UA, ItemType.STDNAME);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КОДЕКС ОБ АДМИНИСТРАТИВНЫХ ПРАВОНАРУШЕНИЯХ", ItemType.TYP, "КРКОАП", com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addVariant("КРК ОБ АП", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КОДЕКС АДМИНИСТРАТИВНОГО СУДОПРОИЗВОДСТВА", ItemType.TYP, "КАС", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КОДЕКС АДМІНІСТРАТИВНОГО СУДОЧИНСТВА", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КАС", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ТАМОЖЕННЫЙ КОДЕКС", ItemType.TYP, "ТК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("МИТНИЙ КОДЕКС", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "МК", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new980("КОДЕКС З ПРОЦЕДУР БАНКРУТСТВА", com.pullenti.morph.MorphLang.UA, ItemType.TYP, com.pullenti.ner.decree.DecreeKind.KODEX);
        t.addVariant("КОДЕКС УКРАЇНИ З ПРОЦЕДУР БАНКРУТСТВА", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("КОДЕКС ТОРГОВОГО МОРЕПЛАВАНИЯ", ItemType.TYP, "КТМ", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("КОДЕКС ТОРГОВЕЛЬНОГО МОРЕПЛАВСТВА", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "КТМ", com.pullenti.ner.decree.DecreeKind.KODEX);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new994("ПРАВИЛА ДОРОЖНОГО ДВИЖЕНИЯ", ItemType.TYP, "ПДД", "ПРАВИЛА");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new996("ПРАВИЛА ДОРОЖНЬОГО РУХУ", com.pullenti.morph.MorphLang.UA, ItemType.TYP, "ПДР", "ПРАВИЛА");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("СОБРАНИЕ ЗАКОНОДАТЕЛЬСТВА", ItemType.TYP);
        t.addAbridge("СЗ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОФИЦИАЛЬНЫЙ ВЕСТНИК", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new372("ОФІЦІЙНИЙ ВІСНИК", com.pullenti.morph.MorphLang.UA, ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("СВОД ЗАКОНОВ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЮЛЛЕТЕНЬ НОРМАТИВНЫХ АКТОВ ФЕДЕРАЛЬНЫХ ОРГАНОВ ИСПОЛНИТЕЛЬНОЙ ВЛАСТИ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЮЛЛЕТЕНЬ МЕЖДУНАРОДНЫХ ДОГОВОРОВ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЮЛЛЕТЕНЬ ВЕРХОВНОГО СУДА", ItemType.TYP);
        t.addVariant("БЮЛЛЕТЕНЬ ВС", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕСТНИК ВЫСШЕГО АРБИТРАЖНОГО СУДА", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕСТНИК БАНКА РОССИИ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("РОССИЙСКАЯ ГАЗЕТА", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("РОССИЙСКИЕ ВЕСТИ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("СОБРАНИЕ АКТОВ ПРЕЗИДЕНТА И ПРАВИТЕЛЬСТВА", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕДОМОСТИ ВЕРХОВНОГО СОВЕТА", ItemType.TYP);
        t.addVariant("ВЕДОМОСТИ ВС", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ВЕДОМОСТИ СЪЕЗДА НАРОДНЫХ ДЕПУТАТОВ И ВЕРХОВНОГО СОВЕТА", ItemType.TYP);
        t.addVariant("ВЕДОМОСТИ СЪЕЗДА НАРОДНЫХ ДЕПУТАТОВ РФ И ВЕРХОВНОГО СОВЕТА", false);
        t.addVariant("ВЕДОМОСТИ СЪЕЗДА НАРОДНЫХ ДЕПУТАТОВ", false);
        t.addVariant("ВЕДОМОСТИ СНД РФ И ВС", false);
        t.addVariant("ВЕДОМОСТИ СНД И ВС", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("БЮЛЛЕТЕНЬ НОРМАТИВНЫХ АКТОВ МИНИСТЕРСТВ И ВЕДОМСТВ", ItemType.TYP);
        m_Termins.add(t);
        m_Termins.add(com.pullenti.ner.core.Termin._new100("СВОД ЗАКОНОВ", ItemType.TYP));
        m_Termins.add(com.pullenti.ner.core.Termin._new100("ВЕДОМОСТИ", ItemType.TYP));
        t = com.pullenti.ner.core.Termin._new126("ЗАРЕГИСТРИРОВАТЬ", "РЕГИСТРАЦИЯ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1067("ЗАРЕЄСТРУВАТИ", com.pullenti.morph.MorphLang.UA, "РЕЄСТРАЦІЯ", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("СТАНДАРТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("МЕЖДУНАРОДНЫЙ СТАНДАРТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new128("ЕДИНЫЙ ОТРАСЛЕВОЙ СТАНДАРТ ЗАКУПОК", ItemType.TYP, "ЕОСЗ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЕДИНЫЙ ОТРАСЛЕВОЙ ПОРЯДОК", ItemType.TYP);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("ГОСТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ГОСУДАРСТВЕННЫЙ СТАНДАРТ", false);
        t.addVariant("ГОССТАНДАРТ", false);
        t.addVariant("НАЦИОНАЛЬНЫЙ СТАНДАРТ", false);
        t.addVariant("МЕЖГОСУДАРСТВЕННЫЙ СТАНДАРТ", false);
        t.addVariant("ДЕРЖАВНИЙ СТАНДАРТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("ОСТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ОТРАСЛЕВОЙ СТАНДАРТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("ПНСТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ПРЕДВАРИТЕЛЬНЫЙ НАЦИОНАЛЬНЫЙ СТАНДАРТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("РСТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("РЕСПУБЛИКАНСКИЙ СТАНДАРТ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("ПБУ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ПОЛОЖЕНИЕ ПО БУХГАЛТЕРСКОМУ УЧЕТУ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("ISO", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ИСО", false);
        t.addVariant("ISO/IEC", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ТЕХНИЧЕСКИЕ УСЛОВИЯ", "ТУ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ТЕХУСЛОВИЯ", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ФЕДЕРАЛЬНЫЕ НОРМЫ И ПРАВИЛА", "ФНП", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("НОРМАТИВНЫЕ ПРАВИЛА", "НП", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("СТРОИТЕЛЬНЫЕ НОРМЫ И ПРАВИЛА", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("СНИП", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("СТРОИТЕЛЬНЫЕ НОРМЫ", "СН", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("CH", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ВЕДОМСТВЕННЫЕ СТРОИТЕЛЬНЫЕ НОРМЫ", "ВСН", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("BCH", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("РЕСПУБЛИКАНСКИЕ СТРОИТЕЛЬНЫЕ НОРМЫ", "РСН", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("PCH", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ПРАВИЛА БЕЗОПАСНОСТИ", "ПБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("НОРМЫ РАДИАЦИОННОЙ БЕЗОПАСНОСТИ", "НРБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ПРАВИЛА РАДИАЦИОННОЙ БЕЗОПАСНОСТИ", "ПРБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("НОРМЫ ПОЖАРНОЙ БЕЗОПАСНОСТИ", "НПБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ПРАВИЛА ПОЖАРНОЙ БЕЗОПАСНОСТИ", "ППБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("СТРОИТЕЛЬНЫЕ ПРАВИЛА", "СП", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("МОСКОВСКИЕ ГОРОДСКИЕ СТРОИТЕЛЬНЫЕ НОРМЫ", "МГСН", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("АВОК", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ABOK", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ТЕХНИЧЕСКИЙ РЕГЛАМЕНТ", "ТР", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ОБЩИЕ ПРАВИЛА БЕЗОПАСНОСТИ", "ОПБ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ПРАВИЛА ЯДЕРНОЙ БЕЗОПАСНОСТИ", "ПБЯ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("СТАНДАРТ ОРГАНИЗАЦИИ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("СТО", false);
        t.addVariant("STO", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ПРАВИЛА ПО ОХРАНЕ ТРУДА", "ПОТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ПРАВИЛА ОХРАНЫ ТРУДА", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("ИНСТРУКЦИЯ ПО ОХРАНЕ ТРУДА", "ИОТ", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("ИНСТРУКЦИЯ ОХРАНЫ ТРУДА", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new1016("РУКОВОДЯЩИЙ ДОКУМЕНТ", "РД", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new102("САНИТАРНЫЕ НОРМЫ И ПРАВИЛА", ItemType.TYP, com.pullenti.ner.decree.DecreeKind.STANDARD);
        t.addVariant("САНПИН", false);
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new128("ТЕХНИЧЕСКОЕ ЗАДАНИЕ", ItemType.TYP, "ТЗ");
        t.addVariant("ТЕХЗАДАНИЕ", false);
        t.addAbridge("ТЕХ. ЗАДАНИЕ");
        m_Termins.add(t);
        t = com.pullenti.ner.core.Termin._new969("ТЕХНИКО КОММЕРЧЕСКОЕ ПРЕДЛОЖЕНИЕ", "ТКП");
        t.addVariant("КОММЕРЧЕСКОЕ ПРЕДЛОЖЕНИЕ", false);
        m_Keywords.add(t);
        m_Termins.add(com.pullenti.ner.core.Termin._new1102("ВСЕОБЩАЯ ДЕКЛАРАЦИЯ ПРАВ ЧЕЛОВЕКА", ItemType.TYP, true));
        m_Termins.add(com.pullenti.ner.core.Termin._new1103("ЗАГАЛЬНА ДЕКЛАРАЦІЯ ПРАВ ЛЮДИНИ", com.pullenti.morph.MorphLang.UA, ItemType.TYP, true));
    }

    public static com.pullenti.ner.Token isKeyword(com.pullenti.ner.Token t, boolean isMiscTypeOnly) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Keywords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            if (isMiscTypeOnly && tok.termin.tag != null) 
                return null;
            tok.getEndToken().tag = tok.termin.getCanonicText();
            return tok.getEndToken();
        }
        if (!t.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isPronoun()) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt == null || npt.getBeginToken() == npt.getEndToken()) {
            if ((t.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ") || t.isValue("НАЗВАННЫЙ", "НАЗВАНИЙ") || t.isValue("ДАННЫЙ", "ДАНИЙ")) || ((t.getMorphClassInDictionary().isVerb() && t.getMorphClassInDictionary().isAdjective()))) {
                if ((((tok = m_Keywords.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                    tok.getEndToken().tag = tok.termin.getCanonicText();
                    return tok.getEndToken();
                }
            }
            return null;
        }
        if ((((tok = m_Keywords.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
            if (isMiscTypeOnly && tok.termin.tag != null) 
                return null;
            tok.getEndToken().tag = tok.termin.getCanonicText();
            return tok.getEndToken();
        }
        PartToken pp = PartToken.tryAttach(npt.getEndToken(), null, false, true);
        if (pp != null) 
            return pp.getEndToken();
        return null;
    }

    public static boolean isKeywordStr(String word, boolean isMiscTypeOnly) {
        if (!isMiscTypeOnly) {
            if (m_AllTypesRU.contains(word) || m_AllTypesUA.contains(word)) 
                return true;
        }
        if (m_MiscTypesRU.contains(word) || m_MiscTypesUA.contains(word)) 
            return true;
        return false;
    }

    public static void addNewType(String _typ, String acronym) {
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new128(_typ, ItemType.TYP, acronym);
        m_Termins.add(t);
        m_Keywords.add(com.pullenti.ner.core.Termin._new100(_typ, ItemType.TYP));
    }

    private static java.util.ArrayList<String> m_AllTypesRU;

    private static java.util.ArrayList<String> m_AllTypesUA;

    private static java.util.ArrayList<String> m_MiscTypesRU;

    private static java.util.ArrayList<String> m_MiscTypesUA;

    private static java.util.ArrayList<String> m_StdAdjectives;

    private static java.util.ArrayList<String> m_EmptyAdjectives;

    public static com.pullenti.ner.decree.DecreeKind getKind(String _typ) {
        if (_typ == null) 
            return com.pullenti.ner.decree.DecreeKind.UNDEFINED;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "КОНСТИТУЦИЯ", "КОНСТИТУЦІЯ", "КОДЕКС", null)) 
            return com.pullenti.ner.decree.DecreeKind.KODEX;
        if (_typ.startsWith("ОСНОВ") && com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ЗАКОНОДАТЕЛЬСТВА", "ЗАКОНОДАВСТВА", null, null)) 
            return com.pullenti.ner.decree.DecreeKind.KODEX;
        if ((com.pullenti.unisharp.Utils.stringsEq(_typ, "УСТАВ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "СТАТУТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ХАРТИЯ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ХАРТІЯ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "РЕГЛАМЕНТ")) 
            return com.pullenti.ner.decree.DecreeKind.USTAV;
        if (((_typ.indexOf("ДОГОВОР") >= 0) || (_typ.indexOf("ДОГОВІР") >= 0) || (_typ.indexOf("КОНТРАКТ") >= 0)) || (_typ.indexOf("СОГЛАШЕНИЕ") >= 0) || (_typ.indexOf("ПРОТОКОЛ") >= 0)) 
            return com.pullenti.ner.decree.DecreeKind.CONTRACT;
        if (_typ.startsWith("ПРОЕКТ")) 
            return com.pullenti.ner.decree.DecreeKind.PROJECT;
        if (com.pullenti.unisharp.Utils.stringsEq(_typ, "ПРОГРАММА") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПРОГРАМА")) 
            return com.pullenti.ner.decree.DecreeKind.PROGRAM;
        if (((((com.pullenti.unisharp.Utils.stringsEq(_typ, "ГОСТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ОСТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ISO")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "СНИП") || com.pullenti.unisharp.Utils.stringsEq(_typ, "RFC")) || (_typ.indexOf("НОРМЫ") >= 0) || (_typ.indexOf("ПРАВИЛА") >= 0)) || (_typ.indexOf("УСЛОВИЯ") >= 0) || (_typ.indexOf("СТАНДАРТ") >= 0)) || com.pullenti.unisharp.Utils.stringsEq(_typ, "РУКОВОДЯЩИЙ ДОКУМЕНТ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "АВОК")) 
            return com.pullenti.ner.decree.DecreeKind.STANDARD;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ЗАКОН", null, null, null)) 
            return com.pullenti.ner.decree.DecreeKind.LAW;
        if ((com.pullenti.unisharp.Utils.stringsEq(_typ, "ПРИКАЗ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "УКАЗ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "РАСПОРЯЖЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПОСТАНОВЛЕНИЕ")) 
            return com.pullenti.ner.decree.DecreeKind.ORDER;
        if ((com.pullenti.unisharp.Utils.stringsEq(_typ, "НАКАЗ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "УКАЗ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "РОЗПОРЯДЖЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПОСТАНОВА")) 
            return com.pullenti.ner.decree.DecreeKind.ORDER;
        if ((com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "КОНВЕНЦИЯ", "КОНВЕНЦІЯ", null, null) || com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ДОГОВОР", "ДОГОВІР", null, null) || com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ПАКТ", "БИЛЛЬ", "БІЛЛЬ", null)) || com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ДЕКЛАРАЦИЯ", "ДЕКЛАРАЦІЯ", null, null)) 
            return com.pullenti.ner.decree.DecreeKind.KONVENTION;
        if ((((((_typ.startsWith("СОБРАНИЕ") || _typ.startsWith("ЗБОРИ") || _typ.startsWith("РЕГИСТРАЦИЯ")) || _typ.startsWith("РЕЄСТРАЦІЯ") || (_typ.indexOf("БЮЛЛЕТЕНЬ") >= 0)) || (_typ.indexOf("БЮЛЕТЕНЬ") >= 0) || (_typ.indexOf("ВЕДОМОСТИ") >= 0)) || (_typ.indexOf("ВІДОМОСТІ") >= 0) || _typ.startsWith("СВОД")) || _typ.startsWith("ЗВЕДЕННЯ") || com.pullenti.morph.LanguageHelper.endsWithEx(_typ, "ГАЗЕТА", "ВЕСТИ", "ВІСТІ", null)) || (_typ.indexOf("ВЕСТНИК") >= 0) || com.pullenti.morph.LanguageHelper.endsWith(_typ, "ВІСНИК")) 
            return com.pullenti.ner.decree.DecreeKind.PUBLISHER;
        return com.pullenti.ner.decree.DecreeKind.UNDEFINED;
    }

    public static boolean isLaw(String _typ) {
        if (_typ == null) 
            return false;
        com.pullenti.ner.decree.DecreeKind ki = getKind(_typ);
        if (ki == com.pullenti.ner.decree.DecreeKind.KODEX || ki == com.pullenti.ner.decree.DecreeKind.LAW) 
            return true;
        return false;
    }

    public static boolean isJustice(String _typ) {
        if ((((com.pullenti.unisharp.Utils.stringsEq(_typ, "ОПРЕДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "РЕШЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПОСТАНОВЛЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ОПРЕДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "УХВАЛА")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ВИЗНАЧЕННЯ") || com.pullenti.unisharp.Utils.stringsEq(_typ, "РІШЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(_typ, "ПОСТАНОВА")) 
            return true;
        return false;
    }

    public static DecreeToken _new905(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static DecreeToken _new906(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static DecreeToken _new911(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.ReferentToken _arg4) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static DecreeToken _new915(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.ReferentToken _arg4, String _arg5) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        res.value = _arg5;
        return res;
    }

    public static DecreeToken _new933(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, int _arg5) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.numYear = _arg5;
        return res;
    }

    public static DecreeToken _new935(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.ReferentToken _arg4, com.pullenti.ner.MorphCollection _arg5) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static DecreeToken _new936(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static DecreeToken _new937(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5, boolean _arg6) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        res.isDoubtful = _arg6;
        return res;
    }

    public static DecreeToken _new944(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.MorphCollection _arg4) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.value = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static DecreeToken _new956(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5) {
        DecreeToken res = new DecreeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.value = _arg5;
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType TYP; // 0
    
        public static final ItemType OWNER; // 1
    
        public static final ItemType DATE; // 2
    
        public static final ItemType EDITION; // 3
    
        public static final ItemType NUMBER; // 4
    
        public static final ItemType NAME; // 5
    
        public static final ItemType STDNAME; // 6
    
        public static final ItemType TERR; // 7
    
        public static final ItemType ORG; // 8
    
        public static final ItemType UNKNOWN; // 9
    
        public static final ItemType MISC; // 10
    
        public static final ItemType DECREEREF; // 11
    
        public static final ItemType DATERANGE; // 12
    
        public static final ItemType BETWEEN; // 13
    
        public static final ItemType READING; // 14
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ItemType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(ItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ItemType> mapIntToEnum; 
        private static java.util.HashMap<String, ItemType> mapStringToEnum; 
        private static ItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ItemType item = new ItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ItemType>();
            mapStringToEnum = new java.util.HashMap<String, ItemType>();
            TYP = new ItemType(0, "TYP");
            mapIntToEnum.put(TYP.value(), TYP);
            mapStringToEnum.put(TYP.m_str.toUpperCase(), TYP);
            OWNER = new ItemType(1, "OWNER");
            mapIntToEnum.put(OWNER.value(), OWNER);
            mapStringToEnum.put(OWNER.m_str.toUpperCase(), OWNER);
            DATE = new ItemType(2, "DATE");
            mapIntToEnum.put(DATE.value(), DATE);
            mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
            EDITION = new ItemType(3, "EDITION");
            mapIntToEnum.put(EDITION.value(), EDITION);
            mapStringToEnum.put(EDITION.m_str.toUpperCase(), EDITION);
            NUMBER = new ItemType(4, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            NAME = new ItemType(5, "NAME");
            mapIntToEnum.put(NAME.value(), NAME);
            mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
            STDNAME = new ItemType(6, "STDNAME");
            mapIntToEnum.put(STDNAME.value(), STDNAME);
            mapStringToEnum.put(STDNAME.m_str.toUpperCase(), STDNAME);
            TERR = new ItemType(7, "TERR");
            mapIntToEnum.put(TERR.value(), TERR);
            mapStringToEnum.put(TERR.m_str.toUpperCase(), TERR);
            ORG = new ItemType(8, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            UNKNOWN = new ItemType(9, "UNKNOWN");
            mapIntToEnum.put(UNKNOWN.value(), UNKNOWN);
            mapStringToEnum.put(UNKNOWN.m_str.toUpperCase(), UNKNOWN);
            MISC = new ItemType(10, "MISC");
            mapIntToEnum.put(MISC.value(), MISC);
            mapStringToEnum.put(MISC.m_str.toUpperCase(), MISC);
            DECREEREF = new ItemType(11, "DECREEREF");
            mapIntToEnum.put(DECREEREF.value(), DECREEREF);
            mapStringToEnum.put(DECREEREF.m_str.toUpperCase(), DECREEREF);
            DATERANGE = new ItemType(12, "DATERANGE");
            mapIntToEnum.put(DATERANGE.value(), DATERANGE);
            mapStringToEnum.put(DATERANGE.m_str.toUpperCase(), DATERANGE);
            BETWEEN = new ItemType(13, "BETWEEN");
            mapIntToEnum.put(BETWEEN.value(), BETWEEN);
            mapStringToEnum.put(BETWEEN.m_str.toUpperCase(), BETWEEN);
            READING = new ItemType(14, "READING");
            mapIntToEnum.put(READING.value(), READING);
            mapStringToEnum.put(READING.m_str.toUpperCase(), READING);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public DecreeToken() {
        super();
    }
    public static DecreeToken _globalInstance;
    
    static {
        try { _globalInstance = new DecreeToken(); } 
        catch(Exception e) { }
        m_AllTypesRU = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"УКАЗ", "УКАЗАНИЕ", "ПОСТАНОВЛЕНИЕ", "РАСПОРЯЖЕНИЕ", "ПРИКАЗ", "ДИРЕКТИВА", "ПИСЬМО", "ЗАПИСКА", "ИНФОРМАЦИОННОЕ ПИСЬМО", "ИНСТРУКЦИЯ", "ЗАКОН", "КОДЕКС", "КОНСТИТУЦИЯ", "РЕШЕНИЕ", "ПОЛОЖЕНИЕ", "РАСПОРЯЖЕНИЕ", "ПОРУЧЕНИЕ", "РЕЗОЛЮЦИЯ", "ДОГОВОР", "СУБДОГОВОР", "АГЕНТСКИЙ ДОГОВОР", "ДОВЕРЕННОСТЬ", "КОММЕРЧЕСКОЕ ПРЕДЛОЖЕНИЕ", "КОНТРАКТ", "ГОСУДАРСТВЕННЫЙ КОНТРАКТ", "ОПРЕДЕЛЕНИЕ", "ПРИГОВОР", "СОГЛАШЕНИЕ", "ПРОТОКОЛ", "ЗАЯВЛЕНИЕ", "УВЕДОМЛЕНИЕ", "РАЗЪЯСНЕНИЕ", "УСТАВ", "ХАРТИЯ", "КОНВЕНЦИЯ", "ПАКТ", "БИЛЛЬ", "ДЕКЛАРАЦИЯ", "РЕГЛАМЕНТ", "ТЕЛЕГРАММА", "ТЕЛЕФОНОГРАММА", "ТЕЛЕФАКСОГРАММА", "ТЕЛЕТАЙПОГРАММА", "ФАКСОГРАММА", "ОТВЕТЫ НА ВОПРОСЫ", "ВЫПИСКА ИЗ ПРОТОКОЛА", "ЗАКЛЮЧЕНИЕ", "ДЕКРЕТ"}));
        m_AllTypesUA = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"УКАЗ", "НАКАЗ", "ПОСТАНОВА", "РОЗПОРЯДЖЕННЯ", "НАКАЗ", "ДИРЕКТИВА", "ЛИСТ", "ЗАПИСКА", "ІНФОРМАЦІЙНИЙ ЛИСТ", "ІНСТРУКЦІЯ", "ЗАКОН", "КОДЕКС", "КОНСТИТУЦІЯ", "РІШЕННЯ", "ПОЛОЖЕННЯ", "РОЗПОРЯДЖЕННЯ", "ДОРУЧЕННЯ", "РЕЗОЛЮЦІЯ", "ДОГОВІР", "СУБКОНТРАКТ", "АГЕНТСЬКИЙ ДОГОВІР", "ДОРУЧЕННЯ", "КОМЕРЦІЙНА ПРОПОЗИЦІЯ", "КОНТРАКТ", "ДЕРЖАВНИЙ КОНТРАКТ", "ВИЗНАЧЕННЯ", "УХВАЛА", "ВИРОК", "УГОДА", "ПРОТОКОЛ", "ЗАЯВА", "ПОВІДОМЛЕННЯ", "РОЗ'ЯСНЕННЯ", "СТАТУТ", "ХАРТІЯ", "КОНВЕНЦІЯ", "ПАКТ", "БІЛЛЬ", "ДЕКЛАРАЦІЯ", "РЕГЛАМЕНТ", "ТЕЛЕГРАМА", "ТЕЛЕФОНОГРАМА", "ТЕЛЕФАКСОГРАММА", "ТЕЛЕТАЙПОГРАМА", "ФАКСОГРАМА", "ВІДПОВІДІ НА ЗАПИТАННЯ", "ВИТЯГ З ПРОТОКОЛУ", "ВИСНОВОК", "ДЕКРЕТ"}));
        m_MiscTypesRU = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ПРАВИЛО", "ПРОГРАММА", "ПЕРЕЧЕНЬ", "ПОСОБИЕ", "РЕКОМЕНДАЦИЯ", "НАСТАВЛЕНИЕ", "СТАНДАРТ", "СОГЛАШЕНИЕ", "МЕТОДИКА", "ТРЕБОВАНИЕ", "ПОЛОЖЕНИЕ", "СПИСОК", "ЛИСТ", "ТАБЛИЦА", "ЗАЯВКА", "АКТ", "ФОРМА", "НОРМАТИВ", "РЕЕСТР", "ПОРЯДОК", "ИНФОРМАЦИЯ", "НОМЕНКЛАТУРА", "ОСНОВА", "ОБЗОР", "КОНЦЕПЦИЯ", "СТРАТЕГИЯ", "СТРУКТУРА", "УСЛОВИЕ", "КЛАССИФИКАТОР", "ОБЩЕРОССИЙСКИЙ КЛАССИФИКАТОР", "СПЕЦИФИКАЦИЯ", "ОБРАЗЕЦ"}));
        m_MiscTypesUA = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ПРАВИЛО", "ПРОГРАМА", "ПЕРЕЛІК", "ДОПОМОГА", "РЕКОМЕНДАЦІЯ", "ПОВЧАННЯ", "СТАНДАРТ", "УГОДА", "МЕТОДИКА", "ВИМОГА", "ПОЛОЖЕННЯ", "СПИСОК", "ТАБЛИЦЯ", "ЗАЯВКА", "АКТ", "ФОРМА", "НОРМАТИВ", "РЕЄСТР", "ПОРЯДОК", "ІНФОРМАЦІЯ", "НОМЕНКЛАТУРА", "ОСНОВА", "ОГЛЯД", "КОНЦЕПЦІЯ", "СТРАТЕГІЯ", "СТРУКТУРА", "УМОВА", "КЛАСИФІКАТОР", "ЗАГАЛЬНОРОСІЙСЬКИЙ КЛАСИФІКАТОР", "СПЕЦИФІКАЦІЯ", "ЗРАЗОК"}));
        m_StdAdjectives = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ВСЕОБЩИЙ", "МЕЖДУНАРОДНЫЙ", "ЗАГАЛЬНИЙ", "МІЖНАРОДНИЙ", "НОРМАТИВНЫЙ", "НОРМАТИВНИЙ", "КАССАЦИОННЫЙ", "АПЕЛЛЯЦИОННЫЙ", "КАСАЦІЙНИЙ", "АПЕЛЯЦІЙНИЙ"}));
        m_EmptyAdjectives = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"НЫНЕШНИЙ", "ПРЕДЫДУЩИЙ", "ДЕЙСТВУЮЩИЙ", "НАСТОЯЩИЙ", "НИНІШНІЙ", "ПОПЕРЕДНІЙ", "СПРАВЖНІЙ"}));
    }
}
