/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink.internal;

public class BookLinkToken extends com.pullenti.ner.MetaToken {

    public BookLinkTyp typ = BookLinkTyp.UNDEFINED;

    public String value;

    public com.pullenti.ner.ReferentToken tok;

    public com.pullenti.ner.Referent ref;

    public double addCoef = 0.0;

    public com.pullenti.ner.person.internal.FioTemplateType personTemplate = com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED;

    public String startOfName;

    public BookLinkToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public static BookLinkToken tryParseAuthor(com.pullenti.ner.Token t, com.pullenti.ner.person.internal.FioTemplateType prevPersTemplate) {
        if (t == null) 
            return null;
        com.pullenti.ner.ReferentToken rtp = com.pullenti.ner.person.internal.PersonItemToken.tryParsePerson(t, prevPersTemplate);
        if (rtp != null) {
            BookLinkToken re;
            if (rtp.data == null) 
                re = _new326(t, (rtp == t ? t : rtp.getEndToken()), BookLinkTyp.PERSON, rtp.referent);
            else 
                re = _new327(t, rtp.getEndToken(), BookLinkTyp.PERSON, rtp);
            re.personTemplate = com.pullenti.ner.person.internal.FioTemplateType.of(rtp.miscAttrs);
            for (com.pullenti.ner.Token tt = rtp.getBeginToken(); tt != null && tt.getEndChar() <= rtp.getEndChar(); tt = tt.getNext()) {
                if (!(tt.getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent)) 
                    continue;
                com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
                if (rt.getBeginToken().chars.isCapitalUpper() && tt != rtp.getBeginToken()) {
                    re.startOfName = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(rt, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                    break;
                }
                return null;
            }
            return re;
        }
        if (t.isChar('[')) {
            BookLinkToken re = tryParseAuthor(t.getNext(), com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED);
            if (re != null && re.getEndToken().getNext() != null && re.getEndToken().getNext().isChar(']')) {
                re.setBeginToken(t);
                re.setEndToken(re.getEndToken().getNext());
                return re;
            }
        }
        if (((t.isValue("И", null) || t.isValue("ET", null))) && t.getNext() != null) {
            if (t.getNext().isValue("ДРУГИЕ", null) || t.getNext().isValue("ДР", null) || t.getNext().isValue("AL", null)) {
                BookLinkToken res = _new328(t, t.getNext(), BookLinkTyp.ANDOTHERS);
                if (t.getNext().getNext() != null && t.getNext().getNext().isChar('.')) 
                    res.setEndToken(res.getEndToken().getNext());
                return res;
            }
        }
        return null;
    }

    public static BookLinkToken tryParse(com.pullenti.ner.Token t, int lev) {
        if (t == null || lev > 3) 
            return null;
        BookLinkToken res = _tryParse(t, lev + 1);
        if (res == null) {
            if (t.isHiphen()) 
                res = _tryParse(t.getNext(), lev + 1);
            if (res == null) 
                return null;
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
            res.setEndToken(res.getEndToken().getNext());
        t = res.getEndToken().getNext();
        if (t != null && t.isComma()) 
            t = t.getNext();
        if (res.typ == BookLinkTyp.GEO || res.typ == BookLinkTyp.PRESS) {
            BookLinkToken re2 = _tryParse(t, lev + 1);
            if (re2 != null && ((re2.typ == BookLinkTyp.PRESS || re2.typ == BookLinkTyp.YEAR))) 
                res.addCoef += (1.0);
        }
        return res;
    }

    private static BookLinkToken _tryParse(com.pullenti.ner.Token t, int lev) {
        if (t == null || lev > 3) 
            return null;
        if (t.isChar('[')) {
            BookLinkToken re = _tryParse(t.getNext(), lev + 1);
            if (re != null && re.getEndToken().getNext() != null && re.getEndToken().getNext().isChar(']')) {
                re.setBeginToken(t);
                re.setEndToken(re.getEndToken().getNext());
                return re;
            }
            if (re != null && re.getEndToken().isChar(']')) {
                re.setBeginToken(t);
                return re;
            }
            if (re != null) {
                if (re.typ == BookLinkTyp.SOSTAVITEL || re.typ == BookLinkTyp.EDITORS) 
                    return re;
            }
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                if ((br.getEndToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && (br.getLengthChar() < 30)) 
                    return _new329(t, br.getEndToken(), BookLinkTyp.NUMBER, com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO));
            }
        }
        com.pullenti.ner.Token t0 = t;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            if (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) 
                return tryParseAuthor(t, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED);
            if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                return _new326(t, t, BookLinkTyp.GEO, t.getReferent());
            if (t.getReferent() instanceof com.pullenti.ner.date.DateReferent) {
                com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.date.DateReferent.class);
                if (dr.getSlots().size() == 1 && dr.getYear() > 0) 
                    return _new329(t, t, BookLinkTyp.YEAR, ((Integer)dr.getYear()).toString());
                if (dr.getYear() > 0 && t.getPrevious() != null && t.getPrevious().isComma()) 
                    return _new329(t, t, BookLinkTyp.YEAR, ((Integer)dr.getYear()).toString());
            }
            if (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) {
                com.pullenti.ner._org.OrganizationReferent _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
                if (_org.getKind() == com.pullenti.ner._org.OrganizationKind.PRESS) 
                    return _new326(t, t, BookLinkTyp.PRESS, _org);
            }
            if (t.getReferent() instanceof com.pullenti.ner.uri.UriReferent) {
                com.pullenti.ner.uri.UriReferent uri = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.uri.UriReferent.class);
                if ((com.pullenti.unisharp.Utils.stringsEq(uri.getScheme(), "http") || com.pullenti.unisharp.Utils.stringsEq(uri.getScheme(), "https") || com.pullenti.unisharp.Utils.stringsEq(uri.getScheme(), "ftp")) || uri.getScheme() == null) 
                    return _new326(t, t, BookLinkTyp.URL, uri);
            }
        }
        com.pullenti.ner.core.TerminToken _tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (_tok != null) {
            BookLinkTyp _typ = (BookLinkTyp)_tok.termin.tag;
            boolean ok = true;
            if (_typ == BookLinkTyp.TYPE || _typ == BookLinkTyp.NAMETAIL || _typ == BookLinkTyp.ELECTRONRES) {
                if (t.getPrevious() != null && ((t.getPrevious().isCharOf(".:[") || t.getPrevious().isHiphen()))) {
                }
                else 
                    ok = false;
            }
            if (ok) 
                return _new329(t, _tok.getEndToken(), _typ, _tok.termin.getCanonicText());
            if (_typ == BookLinkTyp.ELECTRONRES) {
                for (com.pullenti.ner.Token tt = _tok.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter()) 
                        continue;
                    if (tt.getReferent() instanceof com.pullenti.ner.uri.UriReferent) 
                        return _new326(t, tt, BookLinkTyp.ELECTRONRES, tt.getReferent());
                    break;
                }
            }
        }
        if (t.isChar('/')) {
            BookLinkToken res = _new329(t, t, BookLinkTyp.DELIMETER, "/");
            if (t.getNext() != null && t.getNext().isChar('/')) {
                res.setEndToken(t.getNext());
                res.value = "//";
            }
            if (!t.isWhitespaceBefore() && !t.isWhitespaceAfter()) {
                int coo = 3;
                boolean no = true;
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null && coo > 0; tt = tt.getNext(),coo--) {
                    BookLinkToken vvv = tryParse(tt, lev + 1);
                    if (vvv != null && vvv.typ != BookLinkTyp.NUMBER) {
                        no = false;
                        break;
                    }
                }
                if (no) 
                    return null;
            }
            return res;
        }
        if ((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
            BookLinkToken res = _new329(t, t, BookLinkTyp.NUMBER, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
            int val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
            if (val >= 1930 && (val < 2030)) 
                res.typ = BookLinkTyp.YEAR;
            if (t.getNext() != null && t.getNext().isChar('.')) 
                res.setEndToken(t.getNext());
            else if ((t.getNext() != null && t.getNext().getLengthChar() == 1 && !t.getNext().chars.isLetter()) && t.getNext().isWhitespaceAfter()) 
                res.setEndToken(t.getNext());
            else if (t.getNext() instanceof com.pullenti.ner.TextToken) {
                String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term;
                if (((com.pullenti.unisharp.Utils.stringsEq(term, "СТР") || com.pullenti.unisharp.Utils.stringsEq(term, "C") || com.pullenti.unisharp.Utils.stringsEq(term, "С")) || com.pullenti.unisharp.Utils.stringsEq(term, "P") || com.pullenti.unisharp.Utils.stringsEq(term, "S")) || com.pullenti.unisharp.Utils.stringsEq(term, "PAGES")) {
                    res.setEndToken(t.getNext());
                    res.typ = BookLinkTyp.PAGES;
                    res.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString();
                }
            }
            return res;
        }
        if (t instanceof com.pullenti.ner.TextToken) {
            String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
            if (((((((com.pullenti.unisharp.Utils.stringsEq(term, "СТР") || com.pullenti.unisharp.Utils.stringsEq(term, "C") || com.pullenti.unisharp.Utils.stringsEq(term, "С")) || com.pullenti.unisharp.Utils.stringsEq(term, "ТОМ") || com.pullenti.unisharp.Utils.stringsEq(term, "T")) || com.pullenti.unisharp.Utils.stringsEq(term, "Т") || com.pullenti.unisharp.Utils.stringsEq(term, "P")) || com.pullenti.unisharp.Utils.stringsEq(term, "PP") || com.pullenti.unisharp.Utils.stringsEq(term, "V")) || com.pullenti.unisharp.Utils.stringsEq(term, "VOL") || com.pullenti.unisharp.Utils.stringsEq(term, "S")) || com.pullenti.unisharp.Utils.stringsEq(term, "СТОР") || t.isValue("PAGE", null)) || t.isValue("СТРАНИЦА", "СТОРІНКА")) {
                com.pullenti.ner.Token tt = t.getNext();
                while (tt != null) {
                    if (tt.isCharOf(".:~")) 
                        tt = tt.getNext();
                    else 
                        break;
                }
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    BookLinkToken res = _new328(t, tt, BookLinkTyp.PAGERANGE);
                    com.pullenti.ner.Token tt0 = tt;
                    com.pullenti.ner.Token tt1 = tt;
                    for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isCharOf(",") || tt.isHiphen()) {
                            if (tt.getNext() instanceof com.pullenti.ner.NumberToken) {
                                tt = tt.getNext();
                                res.setEndToken(tt);
                                tt1 = tt;
                                continue;
                            }
                        }
                        break;
                    }
                    res.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt1, com.pullenti.ner.core.GetTextAttr.NO);
                    return res;
                }
            }
            if ((com.pullenti.unisharp.Utils.stringsEq(term, "M") || com.pullenti.unisharp.Utils.stringsEq(term, "М") || com.pullenti.unisharp.Utils.stringsEq(term, "СПБ")) || com.pullenti.unisharp.Utils.stringsEq(term, "K") || com.pullenti.unisharp.Utils.stringsEq(term, "К")) {
                if (t.getNext() != null && t.getNext().isCharOf(":;")) {
                    BookLinkToken re = _new328(t, t.getNext(), BookLinkTyp.GEO);
                    return re;
                }
                if (t.getNext() != null && t.getNext().isCharOf(".")) {
                    BookLinkToken res = _new328(t, t.getNext(), BookLinkTyp.GEO);
                    if (t.getNext().getNext() != null && t.getNext().getNext().isCharOf(":;")) 
                        res.setEndToken(t.getNext().getNext());
                    else if (t.getNext().getNext() != null && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    }
                    else if (t.getNext().getNext() != null && t.getNext().getNext().isComma() && (t.getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    }
                    else 
                        return null;
                    return res;
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(term, "ПЕР") || com.pullenti.unisharp.Utils.stringsEq(term, "ПЕРЕВ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПЕРЕВОД")) {
                com.pullenti.ner.Token tt = t;
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                if (tt.getNext() != null && ((tt.getNext().isValue("C", null) || tt.getNext().isValue("С", null)))) {
                    tt = tt.getNext();
                    if (tt.getNext() == null || tt.getWhitespacesAfterCount() > 2) 
                        return null;
                    BookLinkToken re = _new328(t, tt.getNext(), BookLinkTyp.TRANSLATE);
                    return re;
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(term, "ТАМ") || com.pullenti.unisharp.Utils.stringsEq(term, "ТАМЖЕ")) {
                BookLinkToken res = _new328(t, t, BookLinkTyp.TAMZE);
                if (t.getNext() != null && t.getNext().isValue("ЖЕ", null)) 
                    res.setEndToken(t.getNext());
                return res;
            }
            if (((com.pullenti.unisharp.Utils.stringsEq(term, "СМ") || com.pullenti.unisharp.Utils.stringsEq(term, "CM") || com.pullenti.unisharp.Utils.stringsEq(term, "НАПР")) || com.pullenti.unisharp.Utils.stringsEq(term, "НАПРИМЕР") || com.pullenti.unisharp.Utils.stringsEq(term, "SEE")) || com.pullenti.unisharp.Utils.stringsEq(term, "ПОДРОБНЕЕ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПОДРОБНО")) {
                BookLinkToken res = _new328(t, t, BookLinkTyp.SEE);
                for (t = t.getNext(); t != null; t = t.getNext()) {
                    if (t.isCharOf(".:") || t.isValue("ALSO", null)) {
                        res.setEndToken(t);
                        continue;
                    }
                    if (t.isValue("В", null) || t.isValue("IN", null)) {
                        res.setEndToken(t);
                        continue;
                    }
                    BookLinkToken vvv = _tryParse(t, lev + 1);
                    if (vvv != null && vvv.typ == BookLinkTyp.SEE) {
                        res.setEndToken(vvv.getEndToken());
                        break;
                    }
                    break;
                }
                return res;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(term, "БОЛЕЕ")) {
                BookLinkToken vvv = _tryParse(t.getNext(), lev + 1);
                if (vvv != null && vvv.typ == BookLinkTyp.SEE) {
                    vvv.setBeginToken(t);
                    return vvv;
                }
            }
            com.pullenti.ner.Token no = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
            if (no instanceof com.pullenti.ner.NumberToken) 
                return _new328(t, no, BookLinkTyp.N);
            if (((com.pullenti.unisharp.Utils.stringsEq(term, "B") || com.pullenti.unisharp.Utils.stringsEq(term, "В"))) && (t.getNext() instanceof com.pullenti.ner.NumberToken) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                String term2 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class)).term;
                if (((com.pullenti.unisharp.Utils.stringsEq(term2, "Т") || com.pullenti.unisharp.Utils.stringsEq(term2, "T") || term2.startsWith("ТОМ")) || com.pullenti.unisharp.Utils.stringsEq(term2, "TT") || com.pullenti.unisharp.Utils.stringsEq(term2, "ТТ")) || com.pullenti.unisharp.Utils.stringsEq(term2, "КН") || term2.startsWith("КНИГ")) 
                    return _new328(t, t.getNext().getNext(), BookLinkTyp.VOLUME);
            }
            if ((t.getLengthChar() == 1 && t.isNewlineBefore() && t.getNext() != null) && t.getNext().isChar('.') && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                BookLinkToken res = _new328(t, t.getNext().getNext(), BookLinkTyp.NUMBER);
                t = t.getNext().getNext();
                res.value = (term + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                if (t.getNext() != null && t.getNext().isChar('.')) {
                    res.setEndToken((t = t.getNext()));
                    if (t.getNext() != null && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        res.setEndToken((t = t.getNext()));
                        res.value = (res.value + "." + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue());
                        if (t.getNext() != null && t.getNext().isChar('.')) 
                            res.setEndToken((t = t.getNext()));
                    }
                }
                return res;
            }
        }
        if (t.isChar('(')) {
            if (((t.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null && t.getNext().getNext() != null) && t.getNext().getNext().isChar(')')) {
                Integer num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                if (num > 1900 && num <= 2040) {
                    if (num <= java.time.LocalDateTime.now().getYear()) 
                        return _new329(t, t.getNext().getNext(), BookLinkTyp.YEAR, num.toString());
                }
            }
            if (((t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent) && t.getNext().getNext() != null) && t.getNext().getNext().isChar(')')) {
                int num = ((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.date.DateReferent.class)).getYear();
                if (num > 0) 
                    return _new329(t, t.getNext().getNext(), BookLinkTyp.YEAR, ((Integer)num).toString());
            }
        }
        return null;
    }

    public static boolean checkLinkBefore(com.pullenti.ner.Token t0, String num) {
        if (num == null || t0 == null) 
            return false;
        int n1 = _getNum(num);
        if (t0.getPrevious() != null && (t0.getPrevious().getReferent() instanceof com.pullenti.ner.booklink.BookLinkRefReferent)) {
            int n0 = _getNum(((com.pullenti.ner.booklink.BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(t0.getPrevious().getReferent(), com.pullenti.ner.booklink.BookLinkRefReferent.class)).getNumber());
            if (n0 > 0 && (n0 + 1) == n1) 
                return true;
        }
        return false;
    }

    private static int _getNum(String val) {
        if (val == null) 
            return 0;
        for (int i = val.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(val.charAt(i))) {
                val = val.substring(i + 1);
                break;
            }
        }
        int n;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn350 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres351 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapn350);
        n = (wrapn350.value != null ? wrapn350.value : 0);
        if (!inoutres351) 
            return 0;
        return n;
    }

    public static boolean checkLinkAfter(com.pullenti.ner.Token t1, String num) {
        if (num == null || t1 == null) 
            return false;
        if (t1.isNewlineAfter()) {
            BookLinkToken bbb = BookLinkToken.tryParse(t1.getNext(), 0);
            int n0 = _getNum(num);
            if (bbb != null && bbb.typ == BookLinkTyp.NUMBER && n0 > 0) {
                if ((n0 + 1) == _getNum(bbb.value)) 
                    return true;
            }
        }
        return false;
    }

    public static void initialize() {
        if (m_Termins != null) 
            return;
        TitleItemToken.initialize();
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin tt;
        tt = com.pullenti.ner.core.Termin._new100("ТЕКСТ", BookLinkTyp.NAMETAIL);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("ЭЛЕКТРОННЫЙ РЕСУРС", BookLinkTyp.ELECTRONRES);
        tt.addVariant("ЕЛЕКТРОННИЙ РЕСУРС", false);
        tt.addVariant("MODE OF ACCESS", false);
        tt.addVariant("URL", false);
        tt.addVariant("URLS", false);
        tt.addVariant("ELECTRONIC RESOURCE", false);
        tt.addVariant("ON LINE", false);
        tt.addVariant("ONLINE", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("РЕЖИМ ДОСТУПА", BookLinkTyp.MISC);
        tt.addVariant("РЕЖИМ ДОСТУПУ", false);
        tt.addVariant("AVAILABLE", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("МОНОГРАФИЯ", BookLinkTyp.TYPE);
        tt.addVariant("МОНОГРАФІЯ", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("УЧЕБНОЕ ПОСОБИЕ", BookLinkTyp.TYPE);
        tt.addAbridge("УЧ.ПОСОБИЕ");
        tt.addAbridge("УЧЕБ.");
        tt.addAbridge("УЧЕБН.");
        tt.addVariant("УЧЕБНИК", false);
        tt.addVariant("ПОСОБИЕ", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new101("НАВЧАЛЬНИЙ ПОСІБНИК", BookLinkTyp.TYPE, com.pullenti.morph.MorphLang.UA);
        tt.addAbridge("НАВЧ.ПОСІБНИК");
        tt.addAbridge("НАВЧ.ПОСІБ");
        tt.addVariant("ПІДРУЧНИК", false);
        tt.addVariant("ПІДРУЧ", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("АВТОРЕФЕРАТ", BookLinkTyp.TYPE);
        tt.addAbridge("АВТОРЕФ.");
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("ДИССЕРТАЦИЯ", BookLinkTyp.TYPE);
        tt.addVariant("ДИСС", false);
        tt.addAbridge("ДИС.");
        tt.addVariant("ДИСЕРТАЦІЯ", false);
        tt.addVariant("DISSERTATION", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("ДОКЛАД", BookLinkTyp.TYPE);
        tt.addVariant("ДОКЛ", false);
        tt.addAbridge("ДОКЛ.");
        tt.addVariant("ДОПОВІДЬ", false);
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("ПОД РЕДАКЦИЕЙ", BookLinkTyp.EDITORS);
        tt.addAbridge("ПОД РЕД");
        tt.addAbridge("ОТВ.РЕД");
        tt.addAbridge("ОТВ.РЕДАКТОР");
        tt.addVariant("ПОД ОБЩЕЙ РЕДАКЦИЕЙ", false);
        tt.addAbridge("ОТВ.РЕД");
        tt.addAbridge("ОТВ.РЕДАКТОР");
        tt.addAbridge("ПОД ОБЩ. РЕД");
        tt.addAbridge("ПОД ОБЩЕЙ РЕД");
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new101("ПІД РЕДАКЦІЄЮ", BookLinkTyp.EDITORS, com.pullenti.morph.MorphLang.UA);
        tt.addAbridge("ПІД РЕД");
        tt.addAbridge("ОТВ.РЕД");
        tt.addAbridge("ВІД. РЕДАКТОР");
        tt.addVariant("ЗА ЗАГ.РЕД", false);
        tt.addAbridge("ВІДПОВІДАЛЬНИЙ РЕДАКТОР");
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new100("СОСТАВИТЕЛЬ", BookLinkTyp.SOSTAVITEL);
        tt.addAbridge("СОСТ.");
        m_Termins.add(tt);
        tt = com.pullenti.ner.core.Termin._new101("УКЛАДАЧ", BookLinkTyp.SOSTAVITEL, com.pullenti.morph.MorphLang.UA);
        tt.addAbridge("УКЛ.");
        m_Termins.add(tt);
        for (String s : new String[] {"Политиздат", "Прогресс", "Мысль", "Просвещение", "Наука", "Физматлит", "Физматкнига", "Инфра-М", "Питер", "Интеллект", "Аспект пресс", "Аспект-пресс", "АСВ", "Радиотехника", "Радио и связь", "Лань", "Академия", "Академкнига", "URSS", "Академический проект", "БИНОМ", "БВХ", "Вильямс", "Владос", "Волтерс Клувер", "Wolters Kluwer", "Восток-Запад", "Высшая школа", "ГЕО", "Дашков и К", "Кнорус", "Когито-Центр", "КолосС", "Проспект", "РХД", "Статистика", "Финансы и статистика", "Флинта", "Юнити-дана"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s.toUpperCase(), BookLinkTyp.PRESS));
        }
        tt = com.pullenti.ner.core.Termin._new100("ИЗДАТЕЛЬСТВО", BookLinkTyp.PRESS);
        tt.addAbridge("ИЗ-ВО");
        tt.addAbridge("ИЗД-ВО");
        tt.addAbridge("ИЗДАТ-ВО");
        tt.addVariant("ISSN", false);
        tt.addVariant("PRESS", false);
        tt.addVariant("VERLAG", false);
        tt.addVariant("JOURNAL", false);
        m_Termins.add(tt);
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    public static com.pullenti.ner.Token parseStartOfLitBlock(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.internal.BlockLine bl = com.pullenti.ner.core.internal.BlockLine.create(t, null);
        if (bl != null && bl.typ == com.pullenti.ner.core.internal.BlkTyps.LITERATURE) 
            return bl.getEndToken();
        return null;
    }

    public static BookLinkToken _new326(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BookLinkTyp _arg3, com.pullenti.ner.Referent _arg4) {
        BookLinkToken res = new BookLinkToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static BookLinkToken _new327(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BookLinkTyp _arg3, com.pullenti.ner.ReferentToken _arg4) {
        BookLinkToken res = new BookLinkToken(_arg1, _arg2);
        res.typ = _arg3;
        res.tok = _arg4;
        return res;
    }

    public static BookLinkToken _new328(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BookLinkTyp _arg3) {
        BookLinkToken res = new BookLinkToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static BookLinkToken _new329(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BookLinkTyp _arg3, String _arg4) {
        BookLinkToken res = new BookLinkToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }
    public BookLinkToken() {
        super();
    }
}
