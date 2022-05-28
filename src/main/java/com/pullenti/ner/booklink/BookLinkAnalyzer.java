/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink;

/**
 * Анализатор ссылок на внешнюю литературу (библиография)
 */
public class BookLinkAnalyzer extends com.pullenti.ner.Analyzer {

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    /**
     * Имя анализатора ("BOOKLINK")
     */
    public static final String ANALYZER_NAME = "BOOKLINK";

    @Override
    public String getCaption() {
        return "Ссылки на литературу";
    }


    @Override
    public String getDescription() {
        return "Ссылки из списка литературы";
    }


    @Override
    public boolean isSpecific() {
        return false;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new BookLinkAnalyzer();
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.date.DateReferent.OBJ_TYPENAME, com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, com.pullenti.ner._org.OrganizationReferent.OBJ_TYPENAME, com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME});
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.booklink.internal.MetaBookLink.globalMeta, com.pullenti.ner.booklink.internal.MetaBookLinkRef.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.booklink.internal.MetaBookLink.IMAGEID, com.pullenti.ner.decree.internal.ResourceHelper.getBytes("booklink.png"));
        res.put(com.pullenti.ner.booklink.internal.MetaBookLinkRef.IMAGEID, com.pullenti.ner.decree.internal.ResourceHelper.getBytes("booklinkref.png"));
        res.put(com.pullenti.ner.booklink.internal.MetaBookLinkRef.IMAGEIDINLINE, com.pullenti.ner.decree.internal.ResourceHelper.getBytes("booklinkrefinline.png"));
        res.put(com.pullenti.ner.booklink.internal.MetaBookLinkRef.IMAGEIDLAST, com.pullenti.ner.decree.internal.ResourceHelper.getBytes("booklinkreflast.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, BookLinkReferent.OBJ_TYPENAME)) 
            return new BookLinkReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, BookLinkRefReferent.OBJ_TYPENAME)) 
            return new BookLinkRefReferent();
        return null;
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        try {
            com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
            int isLitBlock = 0;
            java.util.HashMap<String, java.util.ArrayList<BookLinkRefReferent>> refsByNum = new java.util.HashMap<String, java.util.ArrayList<BookLinkRefReferent>>();
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts;
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isIgnored()) 
                    continue;
                if (t.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && br.getLengthChar() > 70 && (br.getLengthChar() < 400)) {
                        if (br.isNewlineAfter() || ((br.getEndToken().getNext() != null && br.getEndToken().getNext().isCharOf(".;")))) {
                            rts = tryParse(t.getNext(), false, br.getEndChar());
                            if (rts != null && rts.size() >= 1) {
                                if (rts.size() > 1) {
                                    rts.get(1).referent = ad.registerReferent(rts.get(1).referent);
                                    kit.embedToken(rts.get(1));
                                    ((BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(rts.get(0).referent, BookLinkRefReferent.class)).setBook((BookLinkReferent)com.pullenti.unisharp.Utils.cast(rts.get(1).referent, BookLinkReferent.class));
                                    if (rts.get(0).getBeginChar() == rts.get(1).getBeginChar()) 
                                        rts.get(0).setBeginToken(rts.get(1));
                                    if (rts.get(0).getEndChar() == rts.get(1).getEndChar()) 
                                        rts.get(0).setEndToken(rts.get(1));
                                }
                                rts.get(0).setBeginToken(t);
                                rts.get(0).setEndToken(br.getEndToken());
                                ((BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(rts.get(0).referent, BookLinkRefReferent.class)).setTyp(BookLinkRefType.INLINE);
                                rts.get(0).referent = ad.registerReferent(rts.get(0).referent);
                                kit.embedToken(rts.get(0));
                                t = rts.get(0);
                                continue;
                            }
                        }
                    }
                }
                if (!t.isNewlineBefore()) 
                    continue;
                if (isLitBlock <= 0) {
                    com.pullenti.ner.Token tt = com.pullenti.ner.booklink.internal.BookLinkToken.parseStartOfLitBlock(t);
                    if (tt != null) {
                        isLitBlock = 5;
                        t = tt;
                        continue;
                    }
                }
                rts = tryParse(t, isLitBlock > 0, 0);
                if (rts == null || (rts.size() < 1)) {
                    if ((--isLitBlock) < 0) 
                        isLitBlock = 0;
                    continue;
                }
                if ((++isLitBlock) > 5) 
                    isLitBlock = 5;
                if (rts.size() > 1) {
                    rts.get(1).referent = ad.registerReferent(rts.get(1).referent);
                    kit.embedToken(rts.get(1));
                    ((BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(rts.get(0).referent, BookLinkRefReferent.class)).setBook((BookLinkReferent)com.pullenti.unisharp.Utils.cast(rts.get(1).referent, BookLinkReferent.class));
                    if (rts.get(0).getBeginChar() == rts.get(1).getBeginChar()) 
                        rts.get(0).setBeginToken(rts.get(1));
                    if (rts.get(0).getEndChar() == rts.get(1).getEndChar()) 
                        rts.get(0).setEndToken(rts.get(1));
                }
                BookLinkRefReferent re = (BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(rts.get(0).referent, BookLinkRefReferent.class);
                re = (BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(re), BookLinkRefReferent.class);
                rts.get(0).referent = re;
                kit.embedToken(rts.get(0));
                t = rts.get(0);
                if (re.getNumber() != null) {
                    java.util.ArrayList<BookLinkRefReferent> li;
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<BookLinkRefReferent>> wrapli383 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<BookLinkRefReferent>>();
                    boolean inoutres384 = com.pullenti.unisharp.Utils.tryGetValue(refsByNum, re.getNumber(), wrapli383);
                    li = wrapli383.value;
                    if (!inoutres384) 
                        refsByNum.put(re.getNumber(), (li = new java.util.ArrayList<BookLinkRefReferent>()));
                    li.add(re);
                }
            }
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isIgnored()) 
                    continue;
                if (!(t instanceof com.pullenti.ner.TextToken)) 
                    continue;
                com.pullenti.ner.ReferentToken rt = tryParseShortInline(t);
                if (rt == null) 
                    continue;
                BookLinkRefReferent re = (BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(rt.referent, BookLinkRefReferent.class);
                java.util.ArrayList<BookLinkRefReferent> li;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<BookLinkRefReferent>> wrapli385 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<BookLinkRefReferent>>();
                boolean inoutres386 = com.pullenti.unisharp.Utils.tryGetValue(refsByNum, (String)com.pullenti.unisharp.Utils.notnull(re.getNumber(), ""), wrapli385);
                li = wrapli385.value;
                if (!inoutres386) 
                    continue;
                int i;
                for (i = 0; i < li.size(); i++) {
                    if (t.getBeginChar() < li.get(i).getOccurrence().get(0).beginChar) 
                        break;
                }
                if (i >= li.size()) 
                    continue;
                re.setBook(li.get(i).getBook());
                if (re.getPages() == null) 
                    re.setPages(li.get(i).getPages());
                re.setTyp(BookLinkRefType.INLINE);
                re = (BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(re), BookLinkRefReferent.class);
                rt.referent = re;
                kit.embedToken(rt);
                t = rt;
            }
        } catch (Exception ex123) {
        }
    }

    private static com.pullenti.ner.ReferentToken tryParseShortInline(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        BookLinkRefReferent re;
        if (t.isChar('[') && !t.isNewlineBefore()) {
            com.pullenti.ner.booklink.internal.BookLinkToken bb = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
            if (bb != null && bb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER) {
                re = new BookLinkRefReferent();
                re.setNumber(bb.value);
                return new com.pullenti.ner.ReferentToken(re, t, bb.getEndToken(), null);
            }
        }
        if (t.isChar('(')) {
            com.pullenti.ner.booklink.internal.BookLinkToken bbb = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t.getNext(), 0);
            if (bbb == null) 
                return null;
            if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.SEE) {
                for (com.pullenti.ner.Token tt = bbb.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isCharOf(",:.")) 
                        continue;
                    if (tt.isChar('[')) {
                        if (((tt.getNext() instanceof com.pullenti.ner.NumberToken) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar(']')) && tt.getNext().getNext() != null && tt.getNext().getNext().getNext().isChar(')')) {
                            re = new BookLinkRefReferent();
                            re.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class)).getValue().toString());
                            return new com.pullenti.ner.ReferentToken(re, t, tt.getNext().getNext().getNext(), null);
                        }
                    }
                    if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null && tt.getNext().isChar(')')) {
                        re = new BookLinkRefReferent();
                        re.setNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        return new com.pullenti.ner.ReferentToken(re, t, tt.getNext(), null);
                    }
                    break;
                }
                return null;
            }
            if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER) {
                com.pullenti.ner.Token tt1 = bbb.getEndToken().getNext();
                if (tt1 != null && tt1.isComma()) 
                    tt1 = tt1.getNext();
                com.pullenti.ner.booklink.internal.BookLinkToken bbb2 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt1, 0);
                if ((bbb2 != null && bbb2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE && bbb2.getEndToken().getNext() != null) && bbb2.getEndToken().getNext().isChar(')')) {
                    re = new BookLinkRefReferent();
                    re.setNumber(bbb.value);
                    re.setPages(bbb2.value);
                    return new com.pullenti.ner.ReferentToken(re, t, bbb2.getEndToken().getNext(), null);
                }
            }
        }
        return null;
    }

    private static java.util.ArrayList<com.pullenti.ner.ReferentToken> tryParse(com.pullenti.ner.Token t, boolean isInLit, int maxChar) throws NumberFormatException {
        if (t == null) 
            return null;
        boolean isBracketRegime = false;
        if (t.getPrevious() != null && t.getPrevious().isChar('(')) 
            isBracketRegime = true;
        com.pullenti.ner.booklink.internal.BookLinkToken blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
        if (blt == null) 
            blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED);
        if (blt == null && !isBracketRegime) 
            return null;
        com.pullenti.ner.Token t0 = t;
        double coef = 0.0;
        boolean isElectrRes = false;
        com.pullenti.ner.Token decree = null;
        RegionTyp regtyp = RegionTyp.UNDEFINED;
        String num = null;
        com.pullenti.ner.booklink.internal.BookLinkToken specSee = null;
        com.pullenti.ner.Referent bookPrev = null;
        if (isBracketRegime) 
            regtyp = RegionTyp.AUTHORS;
        else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PERSON) {
            if (!isInLit) 
                return null;
            regtyp = RegionTyp.AUTHORS;
        }
        else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER) {
            num = blt.value;
            t = blt.getEndToken().getNext();
            if (t == null || t.isNewlineBefore()) 
                return null;
            if (!t.isWhitespaceBefore()) {
                if (t instanceof com.pullenti.ner.NumberToken) {
                    String n = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue();
                    if ((((com.pullenti.unisharp.Utils.stringsEq(n, "3") || com.pullenti.unisharp.Utils.stringsEq(n, "0"))) && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isAllLower()) {
                    }
                    else 
                        return null;
                }
                else if (!(t instanceof com.pullenti.ner.TextToken) || t.chars.isAllLower()) {
                    com.pullenti.ner.Referent r = t.getReferent();
                    if (r instanceof com.pullenti.ner.person.PersonReferent) {
                    }
                    else if (isInLit && r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREE")) {
                    }
                    else 
                        return null;
                }
            }
            for (; t != null; t = t.getNext()) {
                if (t instanceof com.pullenti.ner.NumberToken) 
                    break;
                if (!(t instanceof com.pullenti.ner.TextToken)) 
                    break;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                    break;
                if (!t.chars.isLetter()) 
                    continue;
                com.pullenti.ner.booklink.internal.BookLinkToken bbb = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
                if (bbb != null) {
                    if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TAMZE) {
                        specSee = bbb;
                        t = bbb.getEndToken().getNext();
                        break;
                    }
                    if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.SEE) {
                        t = bbb.getEndToken();
                        continue;
                    }
                }
                break;
            }
            if (specSee != null && specSee.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TAMZE) {
                coef++;
                int max = 1000;
                for (com.pullenti.ner.Token tt = t0; tt != null && max > 0; tt = tt.getPrevious(),max--) {
                    if (tt.getReferent() instanceof BookLinkRefReferent) {
                        bookPrev = ((BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), BookLinkRefReferent.class)).getBook();
                        break;
                    }
                }
            }
            com.pullenti.ner.booklink.internal.BookLinkToken blt1 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED);
            if (blt1 != null && blt1.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PERSON) 
                regtyp = RegionTyp.AUTHORS;
            else {
                boolean ok = false;
                for (com.pullenti.ner.Token tt = t; tt != null; tt = (tt == null ? null : tt.getNext())) {
                    if (tt.isNewlineBefore()) 
                        break;
                    if (isInLit && tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "DECREE")) {
                        ok = true;
                        decree = tt;
                        break;
                    }
                    com.pullenti.ner.booklink.internal.BookLinkToken bbb = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt, 0);
                    if (bbb == null) 
                        continue;
                    if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ELECTRONRES) {
                        isElectrRes = true;
                        ok = true;
                        break;
                    }
                    if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.DELIMETER) {
                        tt = bbb.getEndToken().getNext();
                        if (com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(tt, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED) != null) {
                            ok = true;
                            break;
                        }
                        bbb = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt, 0);
                        if (bbb != null) {
                            if (bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.EDITORS || bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TRANSLATE || bbb.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.SOSTAVITEL) {
                                ok = true;
                                break;
                            }
                        }
                    }
                }
                if (!ok && !isInLit) {
                    if (com.pullenti.ner.booklink.internal.BookLinkToken.checkLinkBefore(t0, num)) {
                    }
                    else 
                        return null;
                }
                regtyp = RegionTyp.NAME;
            }
        }
        else 
            return null;
        BookLinkReferent res = new BookLinkReferent();
        java.util.ArrayList<com.pullenti.ner.ReferentToken> corrAuthors = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        com.pullenti.ner.Token t00 = t;
        com.pullenti.ner.booklink.internal.BookLinkToken blt00 = null;
        String startOfName = null;
        com.pullenti.ner.person.internal.FioTemplateType prevPersTempl = com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED;
        if (regtyp == RegionTyp.AUTHORS) {
            for (; t != null; t = t.getNext()) {
                if (maxChar > 0 && t.getBeginChar() >= maxChar) 
                    break;
                if (t.isCharOf(".;") || t.isCommaAnd()) 
                    continue;
                if (t.isChar('/')) 
                    break;
                if ((t.isChar('(') && t.getNext() != null && t.getNext().isValue("EDS", null)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                    t = t.getNext().getNext().getNext();
                    break;
                }
                blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, prevPersTempl);
                if (blt == null && t.getPrevious() != null && t.getPrevious().isAnd()) 
                    blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t.getPrevious(), com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED);
                if (blt == null) {
                    if ((t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) && blt00 != null) {
                        com.pullenti.ner.booklink.internal.BookLinkToken bbb2 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t.getNext(), 0);
                        if (bbb2 != null) {
                            if (bbb2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.YEAR) {
                                res.addSlot(BookLinkReferent.ATTR_AUTHOR, t.getReferent(), false, 0);
                                res.setYear(com.pullenti.unisharp.Utils.parseInteger(bbb2.value, 0, null));
                                coef += 0.5;
                                t = bbb2.getEndToken().getNext();
                            }
                        }
                    }
                    break;
                }
                if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PERSON) {
                    com.pullenti.ner.Token tt2 = blt.getEndToken().getNext();
                    com.pullenti.ner.booklink.internal.BookLinkToken bbb2 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt2, 0);
                    if (bbb2 != null) {
                        if (bbb2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.YEAR) {
                            res.setYear(com.pullenti.unisharp.Utils.parseInteger(bbb2.value, 0, null));
                            coef += 0.5;
                            blt.setEndToken(bbb2.getEndToken());
                            blt00 = null;
                        }
                    }
                    if (blt00 != null && ((blt00.getEndToken().getNext() == blt.getBeginToken() || blt.getBeginToken().getPrevious().isChar('.')))) {
                        com.pullenti.ner.Token tt11 = blt.getEndToken().getNext();
                        com.pullenti.ner.booklink.internal.BookLinkToken nex = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt11, 0);
                        if (nex != null && nex.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ANDOTHERS) {
                        }
                        else {
                            if (tt11 == null) 
                                break;
                            if (tt11.isChar('/') && tt11.getNext() != null && tt11.getNext().isChar('/')) 
                                break;
                            if (tt11.isChar(':')) 
                                break;
                            if ((blt.toString().indexOf('.') < 0) && blt00.toString().indexOf('.') > 0) 
                                break;
                            if ((tt11 instanceof com.pullenti.ner.TextToken) && tt11.chars.isAllLower()) 
                                break;
                            if (tt11.isCharOf(",.;") && tt11.getNext() != null) 
                                tt11 = tt11.getNext();
                            nex = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt11, 0);
                            if (nex != null && nex.typ != com.pullenti.ner.booklink.internal.BookLinkTyp.PERSON && nex.typ != com.pullenti.ner.booklink.internal.BookLinkTyp.ANDOTHERS) 
                                break;
                        }
                    }
                    else if ((blt00 != null && blt00.personTemplate != com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED && blt.personTemplate != blt00.personTemplate) && blt.personTemplate == com.pullenti.ner.person.internal.FioTemplateType.NAMESURNAME) {
                        if (blt.getEndToken().getNext() == null || !blt.getEndToken().getNext().isCommaAnd()) 
                            break;
                        if (com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(blt.getEndToken().getNext().getNext(), com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED) != null) {
                        }
                        else 
                            break;
                    }
                    if (blt00 == null && blt.personTemplate == com.pullenti.ner.person.internal.FioTemplateType.NAMESURNAME) {
                        com.pullenti.ner.Token tt = blt.getEndToken().getNext();
                        if (tt != null && tt.isHiphen()) 
                            tt = tt.getNext();
                        if (tt instanceof com.pullenti.ner.NumberToken) 
                            break;
                    }
                    _addAuthor(res, blt);
                    coef++;
                    t = blt.getEndToken();
                    if (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) 
                        corrAuthors.add((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                    blt00 = blt;
                    prevPersTempl = blt.personTemplate;
                    if ((((startOfName = blt.startOfName))) != null) {
                        t = t.getNext();
                        break;
                    }
                    continue;
                }
                if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ANDOTHERS) {
                    coef += 0.5;
                    t = blt.getEndToken().getNext();
                    res.setAuthorsAndOther(true);
                    break;
                }
                break;
            }
        }
        if (t == null) 
            return null;
        if ((t.isNewlineBefore() && t != t0 && num == null) && res.findSlot(BookLinkReferent.ATTR_AUTHOR, null, true) == null) 
            return null;
        if (startOfName == null) {
            if (t.chars.isAllLower()) 
                coef -= (1.0);
            if (t.chars.isLatinLetter() && !isElectrRes && num == null) {
                if (res.getSlotValue(BookLinkReferent.ATTR_AUTHOR) == null) 
                    return null;
            }
        }
        com.pullenti.ner.Token tn0 = t;
        com.pullenti.ner.Token tn1 = null;
        com.pullenti.ner.uri.UriReferent uri = null;
        String nextNum = null;
        int nn;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapnn391 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres392 = com.pullenti.unisharp.Utils.parseInteger((num != null ? num : ""), 0, null, wrapnn391);
        nn = (wrapnn391.value != null ? wrapnn391.value : 0);
        if (inoutres392) 
            nextNum = ((Integer)(nn + 1)).toString();
        com.pullenti.ner.core.BracketSequenceToken br = (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) ? com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.of((com.pullenti.ner.core.BracketParseAttr.CANCONTAINSVERBS.value()) | (com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES.value())), 100) : null);
        if (br != null) 
            t = t.getNext();
        com.pullenti.ner.booklink.internal.BookLinkToken pages = null;
        for (; t != null; t = t.getNext()) {
            if (maxChar > 0 && t.getBeginChar() >= maxChar) 
                break;
            if (br != null && br.getEndToken() == t) {
                tn1 = t;
                break;
            }
            com.pullenti.ner.booklink.internal.TitleItemToken tit = com.pullenti.ner.booklink.internal.TitleItemToken.tryAttach(t);
            if (tit != null) {
                if ((tit.typ == com.pullenti.ner.booklink.internal.TitleItemToken.Types.TYP && tn0 == t && br == null) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tit.getEndToken().getNext(), true, false)) {
                    br = com.pullenti.ner.core.BracketHelper.tryParse(tit.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        coef += (1.0);
                        if (num != null) 
                            coef++;
                        tn0 = br.getBeginToken();
                        tn1 = br.getEndToken();
                        res.setTyp(tit.value.toLowerCase());
                        t = br.getEndToken().getNext();
                        break;
                    }
                }
            }
            if (t.isNewlineBefore() && t != tn0) {
                if (br != null && (t.getEndChar() < br.getEndChar())) {
                }
                else if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                }
                else {
                    if (t.getNewlinesBeforeCount() > 1) 
                        break;
                    if ((t instanceof com.pullenti.ner.NumberToken) && num != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        if (com.pullenti.unisharp.Utils.stringsEq(num, ((Integer)(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() - 1)).toString())) 
                            break;
                    }
                    else if (num != null) {
                    }
                    else {
                        com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.of(((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value())) | (com.pullenti.ner.core.NounPhraseParseAttr.MULTILINES.value())), 0, null);
                        if (nnn != null && nnn.getEndChar() >= t.getEndChar()) {
                        }
                        else 
                            break;
                    }
                }
            }
            if (t.isCharOf(".;") && t.getWhitespacesAfterCount() > 0) {
                if ((((tit = com.pullenti.ner.booklink.internal.TitleItemToken.tryAttach(t.getNext())))) != null) {
                    if (tit.typ == com.pullenti.ner.booklink.internal.TitleItemToken.Types.TYP) 
                        break;
                }
                boolean stop = true;
                int words = 0;
                int notwords = 0;
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    com.pullenti.ner.booklink.internal.BookLinkToken blt0 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt, 0);
                    if (blt0 == null) {
                        if (tt.isNewlineBefore()) 
                            break;
                        if ((tt instanceof com.pullenti.ner.TextToken) && !tt.getMorphClassInDictionary().isUndefined()) 
                            words++;
                        else 
                            notwords++;
                        if (words > 6 && words > (notwords * 4)) {
                            stop = false;
                            break;
                        }
                        continue;
                    }
                    if ((blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.DELIMETER || blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TRANSLATE || blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TYPE) || blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO || blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PRESS) 
                        stop = false;
                    break;
                }
                if (br != null && br.getEndToken().getPrevious().getEndChar() > t.getEndChar()) 
                    stop = false;
                if (stop) 
                    break;
            }
            if (t == decree) {
                t = t.getNext();
                break;
            }
            blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
            if (blt == null) {
                tn1 = t;
                continue;
            }
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.DELIMETER) 
                break;
            if (((blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.MISC || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TRANSLATE || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NAMETAIL) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TYPE || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.VOLUME) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGES) {
                coef++;
                break;
            }
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PRESS) {
                if (t.getPrevious().isHiphen() || t.getPrevious().isCharOf(".;") || blt.addCoef > 0) 
                    break;
            }
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.YEAR) {
                if (t.getPrevious() != null && t.getPrevious().isComma()) 
                    break;
            }
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ELECTRONRES) {
                isElectrRes = true;
                break;
            }
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.URL) {
                if (t == tn0 || t.getPrevious().isCharOf(":.")) {
                    isElectrRes = true;
                    break;
                }
            }
            tn1 = t;
        }
        if (tn1 == null && startOfName == null) {
            if (isElectrRes) {
                BookLinkReferent uriRe = new BookLinkReferent();
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(uriRe, t00, t, null);
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts0 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                BookLinkRefReferent bref0 = BookLinkRefReferent._new387(uriRe);
                if (num != null) 
                    bref0.setNumber(num);
                com.pullenti.ner.ReferentToken rt01 = new com.pullenti.ner.ReferentToken(bref0, t0, rt0.getEndToken(), null);
                boolean ok = false;
                for (; t != null; t = t.getNext()) {
                    if (t.isNewlineBefore()) 
                        break;
                    com.pullenti.ner.booklink.internal.BookLinkToken blt0 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
                    if (blt0 != null) {
                        if (blt0.ref instanceof com.pullenti.ner.uri.UriReferent) {
                            uriRe.addSlot(BookLinkReferent.ATTR_URL, (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(blt0.ref, com.pullenti.ner.uri.UriReferent.class), false, 0);
                            ok = true;
                        }
                        t = blt0.getEndToken();
                    }
                    rt0.setEndToken(rt01.setEndToken(t));
                }
                if (ok) {
                    rts0.add(rt01);
                    rts0.add(rt0);
                    return rts0;
                }
            }
            if (decree != null && num != null) {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts0 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                BookLinkRefReferent bref0 = BookLinkRefReferent._new387(decree.getReferent());
                if (num != null) 
                    bref0.setNumber(num);
                com.pullenti.ner.ReferentToken rt01 = new com.pullenti.ner.ReferentToken(bref0, t0, decree, null);
                for (t = decree.getNext(); t != null; t = t.getNext()) {
                    if (t.isNewlineBefore()) 
                        break;
                    if (t instanceof com.pullenti.ner.TextToken) {
                        if (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).isPureVerb()) 
                            return null;
                    }
                    rt01.setEndToken(t);
                }
                rts0.add(rt01);
                return rts0;
            }
            if (bookPrev != null) {
                com.pullenti.ner.Token tt = t;
                while (tt != null && ((tt.isCharOf(",.") || tt.isHiphen()))) {
                    tt = tt.getNext();
                }
                com.pullenti.ner.booklink.internal.BookLinkToken blt0 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt, 0);
                if (blt0 != null && blt0.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> rts0 = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                    BookLinkRefReferent bref0 = BookLinkRefReferent._new387(bookPrev);
                    if (num != null) 
                        bref0.setNumber(num);
                    bref0.setPages(blt0.value);
                    com.pullenti.ner.ReferentToken rt00 = new com.pullenti.ner.ReferentToken(bref0, t0, blt0.getEndToken(), null);
                    rts0.add(rt00);
                    return rts0;
                }
            }
            return null;
        }
        if (br != null && ((tn1 == br.getEndToken() || tn1 == br.getEndToken().getPrevious()))) {
            tn0 = tn0.getNext();
            tn1 = tn1.getPrevious();
        }
        if (startOfName == null) {
            while (tn0 != null) {
                if (tn0.isCharOf(":,~")) 
                    tn0 = tn0.getNext();
                else 
                    break;
            }
        }
        for (; tn1 != null && tn1.getBeginChar() > tn0.getBeginChar(); tn1 = tn1.getPrevious()) {
            if (tn1.isCharOf(".;,:(~") || tn1.isHiphen() || tn1.isValue("РЕД", null)) {
            }
            else 
                break;
        }
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tn0, tn1, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        if (startOfName != null) {
            if (nam == null || (nam.length() < 3)) 
                nam = startOfName;
            else 
                nam = (startOfName + (tn0.isWhitespaceBefore() ? " " : "") + nam);
        }
        if (nam == null) 
            return null;
        res.setName(nam);
        if (num == null && !isInLit) {
            if (nam.length() < 20) 
                return null;
            coef -= (2.0);
        }
        if (nam.length() > 500) 
            coef -= ((double)(nam.length() / 500));
        if (isBracketRegime) 
            coef--;
        if (nam.length() > 200) {
            if (num == null) 
                return null;
            if (res.findSlot(BookLinkReferent.ATTR_AUTHOR, null, true) == null && !com.pullenti.ner.booklink.internal.BookLinkToken.checkLinkBefore(t0, num)) 
                return null;
        }
        int en = 0;
        int ru = 0;
        int ua = 0;
        int cha = 0;
        int nocha = 0;
        int chalen = 0;
        com.pullenti.ner.Token lt0 = tn0;
        com.pullenti.ner.Token lt1 = tn1;
        if (tn1 == null) {
            if (t == null) 
                return null;
            lt0 = t0;
            lt1 = t;
            tn1 = t.getPrevious();
        }
        for (com.pullenti.ner.Token tt = lt0; tt != null && tt.getEndChar() <= lt1.getEndChar(); tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) {
                if (tt.chars.isLatinLetter()) 
                    en++;
                else if (tt.getMorph().getLanguage().isUa()) 
                    ua++;
                else if (tt.getMorph().getLanguage().isRu()) 
                    ru++;
                if (tt.getLengthChar() > 2) {
                    cha++;
                    chalen += tt.getLengthChar();
                }
            }
            else if (!(tt instanceof com.pullenti.ner.ReferentToken)) 
                nocha++;
        }
        if (ru > (ua + en)) 
            res.setLang("RU");
        else if (ua > (ru + en)) 
            res.setLang("UA");
        else if (en > (ru + ua)) 
            res.setLang("EN");
        if (nocha > 3 && nocha > cha && startOfName == null) {
            if (nocha > (chalen / 3)) 
                coef -= (2.0);
        }
        if (com.pullenti.unisharp.Utils.stringsEq(res.getLang(), "EN")) {
            for (com.pullenti.ner.Token tt = tn0.getNext(); tt != null && (tt.getEndChar() < tn1.getEndChar()); tt = tt.getNext()) {
                if (tt.isComma() && tt.getNext() != null && ((!tt.getNext().chars.isAllLower() || (tt.getNext() instanceof com.pullenti.ner.ReferentToken)))) {
                    if (tt.getNext().getNext() != null && tt.getNext().getNext().isCommaAnd()) {
                        if (tt.getNext() instanceof com.pullenti.ner.ReferentToken) {
                        }
                        else 
                            continue;
                    }
                    nam = com.pullenti.ner.core.MiscHelper.getTextValue(tn0, tt.getPrevious(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
                    if (nam != null && nam.length() > 15) {
                        res.setName(nam);
                        break;
                    }
                }
            }
        }
        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(res, t00, tn1, null);
        boolean authors = true;
        boolean edits = false;
        br = null;
        for (; t != null; t = t.getNext()) {
            if (maxChar > 0 && t.getBeginChar() >= maxChar) 
                break;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES, 100);
                if (br != null && br.getLengthChar() > 300) 
                    br = null;
            }
            blt = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(t, 0);
            if (t.isNewlineBefore() && !t.isChar('/') && !t.getPrevious().isChar('/')) {
                if (blt != null && blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER) 
                    break;
                if (t.getPrevious().isCharOf(":")) {
                }
                else if (blt != null && ((((blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.DELIMETER || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGES) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PRESS) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.N))) {
                }
                else if (num != null && com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, com.pullenti.ner.person.internal.FioTemplateType.UNDEFINED) != null) {
                }
                else if (num != null && blt != null && blt.typ != com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER) {
                }
                else if (br != null && (t.getEndChar() < br.getEndChar()) && t.getBeginChar() > br.getBeginChar()) {
                }
                else {
                    boolean ok = false;
                    int mmm = 50;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null && mmm > 0; tt = tt.getNext(),mmm--) {
                        if (tt.isNewlineBefore()) {
                            com.pullenti.ner.booklink.internal.BookLinkToken blt2 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParse(tt, 0);
                            if (blt2 != null && blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NUMBER && com.pullenti.unisharp.Utils.stringsEq(blt2.value, nextNum)) {
                                ok = true;
                                break;
                            }
                            if (blt2 != null) {
                                if (blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGES || blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO || blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PRESS) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!ok) {
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.of(((com.pullenti.ner.core.NounPhraseParseAttr.MULTILINES.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEADVERBS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value())), 0, null);
                        if (npt != null && npt.getEndChar() >= t.getEndChar()) 
                            ok = true;
                    }
                    if (!ok) 
                        break;
                }
            }
            rt.setEndToken(t);
            if (blt != null) 
                rt.setEndToken(blt.getEndToken());
            if (t.isCharOf(".,") || t.isHiphen()) 
                continue;
            if (t.isValue("С", null)) {
            }
            if (regtyp == RegionTyp.FIRST && blt != null && blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.EDITORS) {
                edits = true;
                t = blt.getEndToken();
                coef++;
                continue;
            }
            if (regtyp == RegionTyp.FIRST && blt != null && blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.SOSTAVITEL) {
                edits = false;
                t = blt.getEndToken();
                coef++;
                continue;
            }
            if (regtyp == RegionTyp.FIRST && authors) {
                com.pullenti.ner.booklink.internal.BookLinkToken blt2 = com.pullenti.ner.booklink.internal.BookLinkToken.tryParseAuthor(t, prevPersTempl);
                if (blt2 != null && blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PERSON) {
                    prevPersTempl = blt2.personTemplate;
                    if (!edits) 
                        _addAuthor(res, blt2);
                    coef++;
                    t = blt2.getEndToken();
                    continue;
                }
                if (blt2 != null && blt2.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ANDOTHERS) {
                    if (!edits) 
                        res.setAuthorsAndOther(true);
                    coef++;
                    t = blt2.getEndToken();
                    continue;
                }
                authors = false;
            }
            if (blt == null) 
                continue;
            if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ELECTRONRES || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.URL) {
                isElectrRes = true;
                if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ELECTRONRES) 
                    coef += 1.5;
                else 
                    coef += 0.5;
                if (blt.ref instanceof com.pullenti.ner.uri.UriReferent) 
                    res.addSlot(BookLinkReferent.ATTR_URL, (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(blt.ref, com.pullenti.ner.uri.UriReferent.class), false, 0);
            }
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.YEAR) {
                if (res.getYear() == 0) {
                    res.setYear(com.pullenti.unisharp.Utils.parseInteger(blt.value, 0, null));
                    coef += 0.5;
                }
            }
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.DELIMETER) {
                coef++;
                if (blt.getLengthChar() == 2) 
                    regtyp = RegionTyp.SECOND;
                else 
                    regtyp = RegionTyp.FIRST;
            }
            else if ((((blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.MISC || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TYPE || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGES) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.NAMETAIL || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.TRANSLATE) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PRESS || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.VOLUME) || blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.N) 
                coef++;
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.PAGERANGE) {
                pages = blt;
                coef++;
                if (isBracketRegime && blt.getEndToken().getNext() != null && blt.getEndToken().getNext().isChar(')')) {
                    coef += (2.0);
                    if (res.getName() != null && res.findSlot(BookLinkReferent.ATTR_AUTHOR, null, true) != null) 
                        coef = 10.0;
                }
            }
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO && ((regtyp == RegionTyp.SECOND || regtyp == RegionTyp.FIRST))) 
                coef++;
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.GEO && t.getPrevious() != null && t.getPrevious().isChar('.')) 
                coef++;
            else if (blt.typ == com.pullenti.ner.booklink.internal.BookLinkTyp.ANDOTHERS) {
                coef++;
                if (authors) 
                    res.setAuthorsAndOther(true);
            }
            coef += blt.addCoef;
            t = blt.getEndToken();
        }
        if ((coef < 2.5) && num != null) {
            if (com.pullenti.ner.booklink.internal.BookLinkToken.checkLinkBefore(t0, num)) 
                coef += (2.0);
            else if (com.pullenti.ner.booklink.internal.BookLinkToken.checkLinkAfter(rt.getEndToken(), num)) 
                coef += (1.0);
        }
        if (rt.getLengthChar() > 500) 
            return null;
        if (isInLit) 
            coef++;
        if (coef < 2.5) {
            if (isElectrRes && uri != null) {
            }
            else if (coef >= 2 && isInLit) {
            }
            else 
                return null;
        }
        for (com.pullenti.ner.ReferentToken rr : corrAuthors) {
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits0 = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(rr.getBeginToken(), com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.CANINITIALBEDIGIT, 10);
            if (pits0 == null || (pits0.size() < 2)) 
                continue;
            if (pits0.get(0).typ == com.pullenti.ner.person.internal.PersonItemToken.ItemType.VALUE) {
                boolean exi = false;
                for (int i = rr.referent.getSlots().size() - 1; i >= 0; i--) {
                    com.pullenti.ner.Slot s = rr.referent.getSlots().get(i);
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME)) {
                        String ln = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        if (ln == null) 
                            continue;
                        if (com.pullenti.unisharp.Utils.stringsEq(ln, pits0.get(0).value)) {
                            exi = true;
                            continue;
                        }
                        if (ln.indexOf('-') > 0) 
                            ln = ln.substring(0, 0 + ln.indexOf('-'));
                        if (pits0.get(0).getBeginToken().isValue(ln, null)) 
                            rr.referent.getSlots().remove(i);
                    }
                }
                if (!exi) 
                    rr.referent.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pits0.get(0).value, false, 0);
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        BookLinkRefReferent bref = BookLinkRefReferent._new387(res);
        if (num != null) 
            bref.setNumber(num);
        com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(bref, t0, rt.getEndToken(), null);
        if (pages != null) {
            if (pages.value != null) 
                bref.setPages(pages.value);
            rt.setEndToken(pages.getBeginToken().getPrevious());
        }
        rts.add(rt1);
        rts.add(rt);
        return rts;
    }

    private static void _addAuthor(BookLinkReferent blr, com.pullenti.ner.booklink.internal.BookLinkToken tok) {
        if (tok.ref != null) 
            blr.addSlot(BookLinkReferent.ATTR_AUTHOR, tok.ref, false, 0);
        else if (tok.tok != null) {
            blr.addSlot(BookLinkReferent.ATTR_AUTHOR, tok.tok.referent, false, 0);
            blr.addExtReferent(tok.tok);
        }
        else if (tok.value != null) 
            blr.addSlot(BookLinkReferent.ATTR_AUTHOR, tok.value, false, 0);
    }

    public static void initialize() throws Exception {
        com.pullenti.ner.booklink.internal.MetaBookLink.initialize();
        com.pullenti.ner.booklink.internal.MetaBookLinkRef.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.booklink.internal.BookLinkToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new BookLinkAnalyzer());
    }

    public static class RegionTyp implements Comparable<RegionTyp> {
    
        public static final RegionTyp UNDEFINED; // 0
    
        public static final RegionTyp AUTHORS; // 1
    
        public static final RegionTyp NAME; // 2
    
        public static final RegionTyp FIRST; // 3
    
        public static final RegionTyp SECOND; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private RegionTyp(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(RegionTyp v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, RegionTyp> mapIntToEnum; 
        private static java.util.HashMap<String, RegionTyp> mapStringToEnum; 
        private static RegionTyp[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static RegionTyp of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            RegionTyp item = new RegionTyp(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static RegionTyp of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static RegionTyp[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, RegionTyp>();
            mapStringToEnum = new java.util.HashMap<String, RegionTyp>();
            UNDEFINED = new RegionTyp(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            AUTHORS = new RegionTyp(1, "AUTHORS");
            mapIntToEnum.put(AUTHORS.value(), AUTHORS);
            mapStringToEnum.put(AUTHORS.m_str.toUpperCase(), AUTHORS);
            NAME = new RegionTyp(2, "NAME");
            mapIntToEnum.put(NAME.value(), NAME);
            mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
            FIRST = new RegionTyp(3, "FIRST");
            mapIntToEnum.put(FIRST.value(), FIRST);
            mapStringToEnum.put(FIRST.m_str.toUpperCase(), FIRST);
            SECOND = new RegionTyp(4, "SECOND");
            mapIntToEnum.put(SECOND.value(), SECOND);
            mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
            java.util.Collection<RegionTyp> col = mapIntToEnum.values();
            m_Values = new RegionTyp[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public BookLinkAnalyzer() {
        super();
    }
    public static BookLinkAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new BookLinkAnalyzer(); } 
        catch(Exception e) { }
    }
}
