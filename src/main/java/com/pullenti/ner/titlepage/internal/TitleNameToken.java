/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage.internal;

/**
 * Название статьи
 */
public class TitleNameToken extends com.pullenti.ner.MetaToken {

    private TitleNameToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Ранг
     */
    public int rank;

    public com.pullenti.ner.Token beginNameToken;

    public com.pullenti.ner.Token endNameToken;

    /**
     * Это значение типа работы (если есть)
     */
    public String typeValue;

    /**
     * Специальность (если есть)
     */
    public String speciality;

    @Override
    public String toString() {
        if (beginNameToken == null || endNameToken == null) 
            return "?";
        com.pullenti.ner.MetaToken mt = new com.pullenti.ner.MetaToken(beginNameToken, endNameToken, null);
        if (typeValue == null) 
            return (((Integer)rank).toString() + ": " + mt.toString());
        else 
            return (((Integer)rank).toString() + ": " + mt.toString() + " (" + typeValue + ")");
    }

    public static void sort(java.util.ArrayList<TitleNameToken> li) {
        for (int k = 0; k < li.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (li.size() - 1); i++) {
                if (li.get(i).rank < li.get(i + 1).rank) {
                    ch = true;
                    TitleNameToken v = li.get(i);
                    com.pullenti.unisharp.Utils.putArrayValue(li, i, li.get(i + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(li, i + 1, v);
                }
            }
            if (!ch) 
                break;
        }
    }

    public static boolean canBeStartOfTextOrContent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        com.pullenti.ner.Token t;
        if (begin.isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || begin.isValue("ОГЛАВЛЕНИЕ", null) || begin.isValue("СОДЕРЖИМОЕ", null)) {
            t = begin;
            if (t.getNext() != null && t.getNext().isCharOf(":.")) 
                t = t.getNext();
            if (t == end) 
                return true;
        }
        if (begin.isValue("ОТ", "ВІД") && begin.getNext() != null && begin.getNext().isValue("РЕДАКЦИЯ", "РЕДАКЦІЯ")) {
            if (begin.getNext().getNext() != null && begin.getNext().getNext().isChar(':')) 
                return true;
        }
        int words = 0;
        int verbs = 0;
        for (t = begin; t != end.getNext(); t = t.getNext()) {
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.chars.isLetter()) 
                    words++;
                if (t.chars.isAllLower() && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) 
                    verbs++;
            }
        }
        if (words > 10 && verbs > 1) 
            return true;
        return false;
    }

    public static TitleNameToken tryParse(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, int minNewlinesCount) {
        TitleNameToken res = new TitleNameToken(begin, end);
        if (!res.calcRankAndValue(minNewlinesCount)) 
            return null;
        if (res.beginNameToken == null || res.endNameToken == null) 
            return null;
        return res;
    }

    private boolean calcRankAndValue(int minNewlinesCount) {
        rank = 0;
        if (getBeginToken().chars.isAllLower()) 
            rank -= 30;
        int words = 0;
        int upWords = 0;
        int notwords = 0;
        int lineNumber = 0;
        com.pullenti.ner.Token tstart = getBeginToken();
        com.pullenti.ner.Token tend = getEndToken();
        for (com.pullenti.ner.Token t = getBeginToken(); t != getEndToken().getNext() && t != null && t.getEndChar() <= getEndToken().getEndChar(); t = t.getNext()) {
            if (t.isNewlineBefore()) {
            }
            TitleItemToken tit = TitleItemToken.tryAttach(t);
            if (tit != null) {
                if (tit.typ == TitleItemToken.Types.THEME || tit.typ == TitleItemToken.Types.TYPANDTHEME) {
                    if (t != getBeginToken()) {
                        if (lineNumber > 0) 
                            return false;
                        words = (upWords = (notwords = 0));
                        tstart = tit.getEndToken().getNext();
                    }
                    t = tit.getEndToken();
                    if (t.getNext() == null) 
                        return false;
                    if (t.getNext().chars.isLetter() && t.getNext().chars.isAllLower()) 
                        rank += 20;
                    else 
                        rank += 100;
                    tstart = t.getNext();
                    if (tit.typ == TitleItemToken.Types.TYPANDTHEME) 
                        typeValue = tit.value;
                    continue;
                }
                if (tit.typ == TitleItemToken.Types.TYP) {
                    if (t == getBeginToken()) {
                        if (tit.getEndToken().isNewlineAfter()) {
                            typeValue = tit.value;
                            rank += 5;
                            tstart = tit.getEndToken().getNext();
                        }
                    }
                    t = tit.getEndToken();
                    words++;
                    if (tit.getBeginToken() != tit.getEndToken()) 
                        words++;
                    if (tit.chars.isAllUpper()) 
                        upWords++;
                    continue;
                }
                if (tit.typ == TitleItemToken.Types.DUST || tit.typ == TitleItemToken.Types.SPECIALITY) {
                    if (t == getBeginToken()) 
                        return false;
                    rank -= 20;
                    if (tit.typ == TitleItemToken.Types.SPECIALITY) 
                        speciality = tit.value;
                    t = tit.getEndToken();
                    continue;
                }
                if (tit.typ == TitleItemToken.Types.CONSULTANT || tit.typ == TitleItemToken.Types.BOSS || tit.typ == TitleItemToken.Types.EDITOR) {
                    t = tit.getEndToken();
                    if (t.getNext() != null && ((t.getNext().isCharOf(":") || t.getNext().isHiphen() || t.getWhitespacesAfterCount() > 4))) 
                        rank -= 10;
                    else 
                        rank -= 2;
                    continue;
                }
                return false;
            }
            com.pullenti.ner.booklink.internal.BookLinkToken blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
            if (blt != null) {
                if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.MISC || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.N || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGES) 
                    rank -= 10;
                else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.N || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE) 
                    rank -= 20;
            }
            if (t == getBeginToken() && com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED) != null) 
                rank -= 20;
            if (t.isNewlineBefore() && t != getBeginToken()) {
                lineNumber++;
                if (lineNumber > 4) 
                    return false;
                if (t.chars.isAllLower()) 
                    rank += 10;
                else if (t.getPrevious().isChar('.')) 
                    rank -= 10;
                else if (t.getPrevious().isCharOf(",-")) 
                    rank += 10;
                else {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getEndChar() >= t.getEndChar()) 
                        rank += 10;
                }
            }
            if (t != getBeginToken() && t.getNewlinesBeforeCount() > minNewlinesCount) 
                rank -= (t.getNewlinesBeforeCount() - minNewlinesCount);
            com.pullenti.ner.core.BracketSequenceToken bst = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (bst != null && bst.isQuoteType() && bst.getEndToken().getEndChar() <= getEndToken().getEndChar()) {
                if (words == 0) {
                    tstart = bst.getBeginToken();
                    rank += 10;
                    if (bst.getEndToken() == getEndToken()) {
                        tend = this.getEndToken();
                        rank += 10;
                    }
                }
            }
            java.util.ArrayList<com.pullenti.ner.Referent> rli = t.getReferents();
            if (rli != null) {
                for (com.pullenti.ner.Referent r : rli) {
                    if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                        if (t.isNewlineBefore()) 
                            rank -= 10;
                        else 
                            rank -= 4;
                        continue;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.person.PersonReferent)) {
                        if (t.isNewlineBefore()) {
                            rank -= 5;
                            if (t.isNewlineAfter() || t.getNext() == null) 
                                rank -= 20;
                            else if (t.getNext().isHiphen() || (t.getNext() instanceof com.pullenti.ner.NumberToken) || (t.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) 
                                rank -= 20;
                            else if (t != getBeginToken()) 
                                rank -= 20;
                        }
                        continue;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.denomination.DenominationReferent)) 
                        continue;
                    if ((r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent)) 
                        return false;
                    if (t.isNewlineBefore()) 
                        rank -= 4;
                    else 
                        rank -= 2;
                    if (t == getBeginToken() && (getEndToken().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) 
                        rank -= 10;
                }
                words++;
                if (t.chars.isAllUpper()) 
                    upWords++;
                if (t == getBeginToken()) {
                    if (t.isNewlineAfter()) 
                        rank -= 10;
                    else if (t.getNext() != null && t.getNext().isChar('.') && t.getNext().isNewlineAfter()) 
                        rank -= 10;
                }
                continue;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                    words++;
                    if (t.chars.isAllUpper()) 
                        upWords++;
                }
                else 
                    notwords++;
                continue;
            }
            com.pullenti.ner.person.internal.PersonAttrToken pat = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(t, com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (pat != null) {
                if (t.isNewlineBefore()) {
                    if (!pat.getMorph().getCase().isUndefined() && !pat.getMorph().getCase().isNominative()) {
                    }
                    else if (pat.chars.isAllUpper()) {
                    }
                    else 
                        rank -= 20;
                }
                else if (t.chars.isAllLower()) 
                    rank--;
                for (; t != null; t = t.getNext()) {
                    words++;
                    if (t.chars.isAllUpper()) 
                        upWords++;
                    if (t == pat.getEndToken()) 
                        break;
                }
                continue;
            }
            com.pullenti.ner._org.internal.OrgItemTypeToken oitt = com.pullenti.ner._org.internal.OrgItemTypeToken.tryAttach(t, true);
            if (oitt != null) {
                if (oitt.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL && !oitt.isDoubtRootWord()) {
                    if (!oitt.getMorph().getCase().isUndefined() && !oitt.getMorph().getCase().isNominative()) {
                        words++;
                        if (t.chars.isAllUpper()) 
                            upWords++;
                    }
                    else {
                        rank -= 4;
                        if (t == getBeginToken()) 
                            rank -= 5;
                    }
                }
                else {
                    words += 1;
                    if (t.chars.isAllUpper()) 
                        upWords++;
                }
                t = oitt.getEndToken();
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt != null) {
                if (tt.isChar('©')) 
                    rank -= 10;
                if (tt.isChar('_')) 
                    rank--;
                if (tt.chars.isLetter()) {
                    if (tt.getLengthChar() > 2) {
                        words++;
                        if (t.chars.isAllUpper()) 
                            upWords++;
                    }
                }
                else if (!tt.isChar(',')) 
                    notwords++;
                if (tt.isPureVerb()) {{
                            rank -= 30;
                            words--;
                        }
                    break;
                }
                if (tt == getEndToken()) {
                    if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                        rank -= 10;
                    else if (tt.isChar('.')) 
                        rank += 5;
                }
                else if (tt.isCharOf("._")) 
                    rank -= 5;
            }
        }
        rank += words;
        rank -= notwords;
        if ((words < 1) && (rank < 50)) 
            return false;
        if (tstart == null || tend == null) 
            return false;
        if (tstart.getEndChar() > tend.getEndChar()) 
            return false;
        TitleItemToken tit1 = TitleItemToken.tryAttach(this.getEndToken().getNext());
        if (tit1 != null && ((tit1.typ == TitleItemToken.Types.TYP || tit1.typ == TitleItemToken.Types.SPECIALITY))) {
            if (tit1.getEndToken().isNewlineAfter()) 
                rank += 15;
            else 
                rank += 10;
            if (tit1.typ == TitleItemToken.Types.SPECIALITY) 
                speciality = tit1.value;
        }
        if (upWords > 4 && upWords > ((int)((0.8 * ((double)words))))) {
            if (tstart.getPrevious() != null && (tstart.getPrevious().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) 
                rank += (5 + upWords);
        }
        beginNameToken = tstart;
        endNameToken = tend;
        return true;
    }
    public TitleNameToken() {
        super();
    }
}
