/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class ContentAnalyzeWhapper {

    public com.pullenti.ner.decree.DecreeKind docTyp = com.pullenti.ner.decree.DecreeKind.UNDEFINED;

    public FragToken topDoc;

    public java.util.ArrayList<InstrToken1> lines;

    public com.pullenti.ner.instrument.InstrumentKind citKind = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;

    public void analyze(FragToken root, FragToken _topDoc, boolean isCitat, com.pullenti.ner.instrument.InstrumentKind rootKind) {
        topDoc = _topDoc;
        citKind = rootKind;
        java.util.ArrayList<InstrToken1> _lines = new java.util.ArrayList<InstrToken1>();
        int directives = 0;
        int parts = 0;
        if (_topDoc != null && _topDoc.m_Doc != null) {
            String ty = _topDoc.m_Doc.getTyp();
            if (ty != null) {
                if (((ty.indexOf("КОДЕКС") >= 0) || (ty.indexOf("ЗАКОН") >= 0) || (ty.indexOf("КОНСТИТУЦИЯ") >= 0)) || (ty.indexOf("КОНСТИТУЦІЯ") >= 0)) 
                    docTyp = com.pullenti.ner.decree.DecreeKind.KODEX;
                else if ((ty.indexOf("ДОГОВОР") >= 0) || (ty.indexOf("ДОГОВІР") >= 0) || (ty.indexOf("КОНТРАКТ") >= 0)) 
                    docTyp = com.pullenti.ner.decree.DecreeKind.CONTRACT;
            }
        }
        for (com.pullenti.ner.Token t = root.getBeginToken(); t != null; t = t.getNext()) {
            if (t.getBeginChar() > root.getEndToken().getEndChar()) 
                break;
            com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
            if (dpr != null && dpr.getLocalTyp() != null && (((dpr.getChapter() != null || dpr.getClause() != null || dpr.getSection() != null) || dpr.getSubSection() != null))) 
                t = t.kit.debedToken(t);
            if (_lines.size() == 120) {
            }
            InstrToken1 lt = InstrToken1.parse(t, false, _topDoc, 0, (_lines.size() > 0 ? _lines.get(_lines.size() - 1) : null), isCitat && t == root.getBeginToken(), root.getEndToken().getEndChar(), false, false);
            if (lt == null) 
                continue;
            if (lt.typ == InstrToken1.Types.CLAUSE && lt.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(lt.numbers.get(0), "13")) {
            }
            if (lt.numTyp == NumberTypes.DIGIT && lt.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(lt.numbers.get(0), "10")) {
            }
            if (lt.typ == InstrToken1.Types.EDITIONS) {
                if ((!lt.isNewlineAfter() && lt.getEndToken().getNext() != null && lt.getEndToken().getNext().isNewlineAfter()) && (lt.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && !lt.getEndToken().getNext().chars.isLetter()) 
                    lt.setEndToken(lt.getEndToken().getNext());
            }
            if (lt.numbers.size() > 0) {
            }
            if (_lines.size() == 0 && rootKind != com.pullenti.ner.instrument.InstrumentKind.UNDEFINED) {
                if ((rootKind == com.pullenti.ner.instrument.InstrumentKind.INDENTION || rootKind == com.pullenti.ner.instrument.InstrumentKind.ITEM || rootKind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) || rootKind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART) 
                    lt.typ = InstrToken1.Types.LINE;
                else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER) 
                    lt.typ = InstrToken1.Types.CHAPTER;
                else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) 
                    lt.typ = InstrToken1.Types.CLAUSE;
                else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.SECTION) 
                    lt.typ = InstrToken1.Types.SECTION;
                else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.SUBSECTION) 
                    lt.typ = InstrToken1.Types.SUBSECTION;
                else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.DOCPART) 
                    lt.typ = InstrToken1.Types.DOCPART;
            }
            if (lt.typ == InstrToken1.Types.CLAUSE && lt.getFirstNumber() == 103) {
            }
            if (lt.getEndChar() > root.getEndChar()) 
                lt.setEndToken(root.getEndToken());
            if (lt.typ == InstrToken1.Types.DIRECTIVE) 
                directives++;
            if ((lt.numTyp == NumberTypes.LETTER && lt.numbers.size() == 1 && lt.getLastNumber() > 1) && rootKind != com.pullenti.ner.instrument.InstrumentKind.SUBITEM && rootKind != com.pullenti.ner.instrument.InstrumentKind.ITEM) {
                boolean ok = false;
                for (int i = _lines.size() - 1; i >= 0; i--) {
                    if (_lines.get(i).numTyp == lt.numTyp) {
                        int j = lt.getLastNumber() - _lines.get(i).getLastNumber();
                        ok = j == 1 || j == 2;
                        break;
                    }
                }
                if (!ok) {
                    lt.numTyp = NumberTypes.UNDEFINED;
                    lt.numbers.clear();
                }
            }
            if (lt.getTypContainerRank() > 0 && !lt.isNumDoubt) 
                parts++;
            _lines.add(lt);
            t = lt.getEndToken();
        }
        ListHelper.correctIndex(_lines);
        ListHelper.correctAppList(_lines);
        if (directives > 0 && directives > parts) 
            this._analizeContentWithDirectives(root, _lines, _topDoc != null && _topDoc.m_Doc != null && _topDoc.m_Doc.getCaseNumber() != null);
        else 
            this._analizeContentWithContainers(root, _lines, 0, _topDoc);
        this._analizePreamble(root);
        root._analizeTables();
        if (docTyp == com.pullenti.ner.decree.DecreeKind.CONTRACT) {
        }
        else 
            this._correctKodexParts(root);
        this._analizeSections(root);
        this._correctNames(root, null);
        EditionHelper.analizeEditions(root);
        if (docTyp == com.pullenti.ner.decree.DecreeKind.CONTRACT) 
            ContractHelper.correctDummyNewlines(root);
        ListHelper.analyze(root);
        if (rootKind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART || rootKind == com.pullenti.ner.instrument.InstrumentKind.ITEM || rootKind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) {
            for (FragToken ch : root.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM) {
                    if (rootKind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART) {
                        ch.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART;
                        for (FragToken chh : ch.children) {
                            if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) 
                                chh.kind = com.pullenti.ner.instrument.InstrumentKind.ITEM;
                        }
                    }
                    else if (rootKind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) 
                        ch.kind = com.pullenti.ner.instrument.InstrumentKind.SUBITEM;
                }
            }
        }
        this._postCorrect(root, _lines);
    }

    private void _postCorrect(FragToken root, java.util.ArrayList<InstrToken1> _lines) {
        for (FragToken ch : root.children) {
            this._postCorrect(ch, _lines);
        }
        if (root.children.size() > 0) {
            if (root.getEndChar() < root.children.get(root.children.size() - 1).getEndChar()) 
                root.setEndToken(root.children.get(root.children.size() - 1).getEndToken());
            if (root.getBeginChar() > root.children.get(0).getBeginChar()) 
                root.setBeginToken(root.children.get(0).getBeginToken());
        }
    }

    private void _analizeContentWithContainers(FragToken root, java.util.ArrayList<InstrToken1> _lines, int topLevel, FragToken _topDoc) {
        java.util.ArrayList<InstrToken1> nums = new java.util.ArrayList<InstrToken1>();
        int k = 0;
        int lev = 100;
        InstrToken1 li0 = null;
        int koef = 0;
        if (((root.kind == com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH && _lines.size() > 10 && _lines.get(0).typ == InstrToken1.Types.LINE) && _lines.get(0).numbers.size() > 0 && !_lines.get(0).hasVerb) && _lines.get(1).typ == InstrToken1.Types.CLAUSE) {
            nums.add(_lines.get(0));
            for (int ii = 2; ii < (_lines.size() - 1); ii++) {
                InstrToken1 ch = _lines.get(ii);
                if (ch.typ != InstrToken1.Types.LINE || ch.hasVerb || ch.numbers.size() != nums.get(0).numbers.size()) 
                    continue;
                InstrToken1 la = nums.get(nums.size() - 1);
                if (NumberingHelper.calcDelta(la, ch, false) != 1) 
                    continue;
                if (la.numTyp != ch.numTyp || com.pullenti.unisharp.Utils.stringsNe(la.numSuffix, ch.numSuffix)) 
                    continue;
                if (ch.getEndToken().isChar('.')) {
                    if (!la.getEndToken().isChar('.')) 
                        continue;
                }
                boolean hasClause = false;
                for (int jj = ii + 1; jj < _lines.size(); jj++) {
                    if (_lines.get(jj).typ == InstrToken1.Types.CLAUSE) {
                        hasClause = true;
                        break;
                    }
                    else if (_lines.get(jj).typ != InstrToken1.Types.COMMENT && _lines.get(jj).typ != InstrToken1.Types.EDITIONS) 
                        break;
                }
                if (hasClause) 
                    nums.add(ch);
            }
            if (nums.size() < 2) 
                nums.clear();
            else {
                koef = 2;
                for (InstrToken1 nn : nums) {
                    nn.typ = InstrToken1.Types.SUBPARAGRAPH;
                    lev = nn.getTypContainerRank();
                }
            }
        }
        if (nums.size() == 0) {
            for (InstrToken1 li : _lines) {
                if (li.typ == InstrToken1.Types.COMMENT || li.typ == InstrToken1.Types.EDITIONS) 
                    continue;
                if (li0 == null) 
                    li0 = li;
                k++;
                if (li.getTypContainerRank() > topLevel) {
                    if (li.getTypContainerRank() < lev) {
                        if (nums.size() > 2 && li.numbers.size() == 0) {
                        }
                        else if (k > 20) {
                        }
                        else {
                            lev = li.getTypContainerRank();
                            nums.clear();
                        }
                    }
                    if (li.getTypContainerRank() == lev) 
                        nums.add(li);
                }
            }
            for (int i = 0; i < nums.size(); i++) {
                int d0 = (i > 0 ? NumberingHelper.calcDelta(nums.get(i - 1), nums.get(i), true) : 0);
                int d1 = ((i + 1) < nums.size() ? NumberingHelper.calcDelta(nums.get(i), nums.get(i + 1), true) : 0);
                int d01 = (i > 0 && ((i + 1) < nums.size()) ? NumberingHelper.calcDelta(nums.get(i - 1), nums.get(i + 1), true) : 0);
                if (d0 == 1) {
                    if (d1 == 1) 
                        continue;
                    if (d01 == 1 && !nums.get(i + 1).isNumDoubt && nums.get(i).isNumDoubt) {
                        nums.remove(i);
                        i--;
                    }
                    continue;
                }
                if (d01 == 1 && nums.get(i).isNumDoubt) {
                    nums.remove(i);
                    i--;
                    continue;
                }
            }
            for (int i = 1; i < nums.size(); i++) {
                int d = NumberingHelper.calcDelta(nums.get(i - 1), nums.get(i), true);
                if (d == 1) 
                    koef += 2;
                else if (d == 2) 
                    koef++;
                else if (d <= 0) 
                    koef--;
            }
            if (nums.size() > 0) {
                boolean hasNumBefore = false;
                for (InstrToken1 li : _lines) {
                    if (li == nums.get(0)) 
                        break;
                    else if (li.numbers.size() > 0) 
                        hasNumBefore = true;
                }
                if (!hasNumBefore && ((nums.get(0).getLastNumber() == 1 || ((nums.get(0) == li0 && nums.get(0).numSuffix != null))))) 
                    koef += 2;
                else if (nums.get(0).typ == InstrToken1.Types.CLAUSE && nums.get(0) == li0) 
                    koef += 2;
            }
        }
        boolean isChapters = false;
        if (nums.size() == 0) {
            int chaps = 0;
            int nons = 0;
            int clauses = 0;
            for (int i = 0; i < _lines.size(); i++) {
                InstrToken1 li = _lines.get(i);
                if (li.typ == InstrToken1.Types.CHAPTER) {
                    nums.add(li);
                    chaps++;
                    lev = li.getTypContainerRank();
                }
                else if (li.typ == InstrToken1.Types.LINE && li.titleTyp != InstrToken1.StdTitleType.UNDEFINED) {
                    nums.add(li);
                    nons++;
                }
                else if (li.typ == InstrToken1.Types.CLAUSE) 
                    clauses++;
            }
            if (chaps == 0) 
                nums.clear();
            else {
                koef += 2;
                isChapters = true;
            }
        }
        if (koef < 2) {
            if (topLevel < InstrToken1._calcRank(InstrToken1.Types.CHAPTER)) {
                if (this._analizeChapterWithoutKeywords(root, _lines, _topDoc)) 
                    return;
            }
            this._analizeContentWithoutContainers(root, _lines, false, false, false);
            return;
        }
        int n = 0;
        int names = 0;
        FragToken fr = null;
        java.util.ArrayList<InstrToken1> blk = new java.util.ArrayList<InstrToken1>();
        for (int i = 0; i <= _lines.size(); i++) {
            InstrToken1 li = (i < _lines.size() ? _lines.get(i) : null);
            if (li == null || (((n < nums.size()) && li == nums.get(n)))) {
                if (blk.size() > 0) {
                    if (fr == null) {
                        fr = FragToken._new1494(blk.get(0).getBeginToken(), blk.get(blk.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
                        if (blk.size() == 1) 
                            fr.itok = blk.get(0);
                        root.children.add(fr);
                    }
                    fr.setEndToken(blk.get(blk.size() - 1).getEndToken());
                    this._analizeContentWithContainers(fr, blk, lev, _topDoc);
                    blk.clear();
                    fr = null;
                }
            }
            if (li == null) 
                break;
            if ((n < nums.size()) && li == nums.get(n)) {
                n++;
                fr = FragToken._new1495(li.getBeginToken(), li.getEndToken(), li, li.isExpired);
                root.children.add(fr);
                if (li.typ == InstrToken1.Types.DOCPART) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.DOCPART;
                else if (li.typ == InstrToken1.Types.CLAUSEPART) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART;
                else if (li.typ == InstrToken1.Types.SECTION) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.SECTION;
                else if (li.typ == InstrToken1.Types.SUBSECTION) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.SUBSECTION;
                else if (li.typ == InstrToken1.Types.PARAGRAPH) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH;
                else if (li.typ == InstrToken1.Types.SUBPARAGRAPH) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.SUBPARAGRAPH;
                else if (li.typ == InstrToken1.Types.CHAPTER) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.CHAPTER;
                else if (li.typ == InstrToken1.Types.CLAUSE) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSE;
                else if (li.typ == InstrToken1.Types.NOTICE) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.NOTICE;
                else if (isChapters) 
                    fr.kind = com.pullenti.ner.instrument.InstrumentKind.CHAPTER;
                if (li.getBeginToken() != li.numBeginToken && li.numBeginToken != null) 
                    fr.children.add(FragToken._new1496(li.getBeginToken(), li.numBeginToken.getPrevious(), com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true, li));
                NumberingHelper.createNumber(fr, li);
                if (li.numEndToken != li.getEndToken() && li.numEndToken != null) {
                    if (!li.allUpper && ((((li.hasVerb && names == 0 && li.getEndToken().isCharOf(".:,"))) || li.getEndToken().isChar(':')))) 
                        fr.children.add(FragToken._new1494(li.numEndToken.getNext(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
                    else {
                        FragToken frName = FragToken._new1496(li.numEndToken.getNext(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                        fr.children.add(frName);
                        fr.name = FragToken.getRestoredNameMT(frName, false);
                        i = correctName(fr, frName, _lines, i);
                        names++;
                    }
                }
                else if (li.titleTyp != InstrToken1.StdTitleType.UNDEFINED) {
                    FragToken frName = FragToken._new1496(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                    fr.children.add(frName);
                    fr.name = FragToken.getRestoredNameMT(frName, false);
                    i = correctName(fr, frName, _lines, i);
                    names++;
                }
                else if ((((i + 1) < _lines.size()) && _lines.get(i + 1).numbers.size() == 0 && !_lines.get(i + 1).hasVerb) && !_lines.get(i + 1).hasManySpecChars) {
                    if (_lines.get(i + 1).allUpper || (_lines.get(i + 1).getBeginToken().isChar('[')) || _lines.get(i).getEndToken().isChar('.')) {
                        i++;
                        li = _lines.get(i);
                        fr.setEndToken(li.getEndToken());
                        FragToken frName = FragToken._new1496(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                        fr.children.add(frName);
                        fr.name = FragToken.getRestoredNameMT(frName, false);
                        i = correctName(fr, frName, _lines, i);
                        names++;
                    }
                }
                continue;
            }
            if (li.typ == InstrToken1.Types.EDITIONS && blk.size() == 0 && fr != null) 
                fr.children.add(FragToken._new1494(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.EDITIONS));
            else 
                blk.add(li);
        }
    }

    private static int correctName(FragToken fr, FragToken frName, java.util.ArrayList<InstrToken1> _lines, int i) {
        if ((i + 1) >= _lines.size()) 
            return i;
        InstrToken1 li = _lines.get(i);
        if (li.typ == InstrToken1.Types.SUBSECTION) {
        }
        if (fr.name != null && (fr.name.length() < 100)) {
            for (; (i + 1) < _lines.size(); i++) {
                if (fr.name.length() > 500) 
                    break;
                InstrToken1 lii = _lines.get(i + 1);
                if (lii.numbers.size() > 0 || lii.typ != InstrToken1.Types.LINE) 
                    break;
                if (lii.getEndToken().isChar(':')) 
                    break;
                if (li.getEndToken().isCharOf(";")) 
                    break;
                if (li.getEndToken().isChar('.')) {
                    if (lii.allUpper && li.allUpper) {
                    }
                    else 
                        break;
                }
                if (li.allUpper && !lii.allUpper) 
                    break;
                if ((li.getLengthChar() < (lii.getLengthChar() / 2)) && lii.hasVerb) 
                    break;
                if (li.hasManySpecChars) 
                    break;
                if (lii.getBeginToken().getWhitespacesBeforeCount() > 15) 
                    break;
                if (lii.getBeginToken().isValue("НЕТ", null) || lii.getBeginToken().isValue("НЕ", null) || lii.getBeginToken().isValue("ОТСУТСТВОВАТЬ", null)) 
                    break;
                if (!(lii.getBeginToken() instanceof com.pullenti.ner.TextToken)) 
                    break;
                com.pullenti.morph.MorphClass mc = lii.getBeginToken().getMorphClassInDictionary();
                if (mc.isUndefined()) 
                    break;
                com.pullenti.ner.Token tt = lii.getBeginToken();
                while (tt instanceof com.pullenti.ner.MetaToken) {
                    tt = ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken();
                }
                if (tt.chars.isCapitalUpper() || !tt.chars.isLetter() || mc.isPreposition()) {
                    if (!li.getEndToken().isChar(',') && !li.getEndToken().isHiphen() && !li.getEndToken().getMorph()._getClass().isConjunction()) {
                        if (!mc.isPreposition()) 
                            break;
                        if (li.hasVerb) 
                            break;
                        if (!li.allUpper) 
                            break;
                    }
                }
                li = lii;
                fr.setEndToken(frName.setEndToken(li.getEndToken()));
                frName.setDefVal2(true);
                fr.name = FragToken.getRestoredNameMT(frName, false);
            }
        }
        return i;
    }

    private boolean _analizeChapterWithoutKeywords(FragToken root, java.util.ArrayList<InstrToken1> _lines, FragToken _topDoc) {
        java.util.ArrayList<InstrToken1> nums = NumberingHelper.extractMainSequence(_lines, true, false);
        boolean isContractStruct = false;
        if (nums == null || nums.get(0).numbers.size() != 1 || com.pullenti.unisharp.Utils.stringsNe(nums.get(0).numbers.get(0), "1")) {
            if (docTyp == com.pullenti.ner.decree.DecreeKind.CONTRACT) {
                java.util.ArrayList<InstrToken1> nums1 = new java.util.ArrayList<InstrToken1>();
                String num0 = "1";
                boolean ok = true;
                for (int i = 1; i < _lines.size(); i++) {
                    InstrToken1 li = _lines.get(i);
                    InstrToken1 li0 = _lines.get(i - 1);
                    if ((nums1.size() > 0 && nums1.get(0).titleTyp == InstrToken1.StdTitleType.SUBJECT && nums1.get(0).numbers.size() == 0) && nums1.get(0).allUpper) {
                        if (li0.numbers.size() <= 1 && ((li0.allUpper || li0.titleTyp != InstrToken1.StdTitleType.UNDEFINED))) 
                            nums1.add(li0);
                        continue;
                    }
                    if (li.numbers.size() == 2 && com.pullenti.unisharp.Utils.stringsEq(li.numbers.get(0), num0) && com.pullenti.unisharp.Utils.stringsEq(li.numbers.get(1), "1")) {
                        if (li0.numbers.size() == 0 && !li0.getBeginToken().chars.isAllLower()) {
                        }
                        else if (li0.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(li0.numbers.get(0), num0)) {
                        }
                        else {
                            ok = false;
                            break;
                        }
                        nums1.add(li0);
                        num0 = ((Integer)(nums1.size() + 1)).toString();
                        continue;
                    }
                    if (li0.titleTyp != InstrToken1.StdTitleType.UNDEFINED || ((li0.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(li0.numbers.get(0), num0)))) {
                        nums1.add(li0);
                        num0 = ((Integer)(nums1.size() + 1)).toString();
                    }
                }
                if (ok && nums1.size() > 1) {
                    nums = nums1;
                    isContractStruct = true;
                }
            }
        }
        if (nums == null) 
            return false;
        if (nums.size() > 500) 
            return false;
        int n = 0;
        int err = 0;
        FragToken fr = null;
        java.util.ArrayList<InstrToken1> blk = new java.util.ArrayList<InstrToken1>();
        java.util.ArrayList<FragToken> childs = new java.util.ArrayList<FragToken>();
        for (int i = 0; i <= _lines.size(); i++) {
            InstrToken1 li = (i < _lines.size() ? _lines.get(i) : null);
            if (li == null || (((n < nums.size()) && li == nums.get(n))) || ((n >= nums.size() && li.titleTyp != InstrToken1.StdTitleType.UNDEFINED))) {
                if (blk.size() > 0) {
                    if (fr == null) {
                        fr = FragToken._new1494(blk.get(0).getBeginToken(), blk.get(blk.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
                        if (blk.size() == 1) 
                            fr.itok = blk.get(0);
                        childs.add(fr);
                    }
                    fr.setEndToken(blk.get(blk.size() - 1).getEndToken());
                    this._analizeContentWithoutContainers(fr, blk, false, false, false);
                    blk.clear();
                    fr = null;
                }
            }
            if (li == null) 
                break;
            if ((n < nums.size()) && li == nums.get(n)) {
                n++;
                if (!li.allUpper && li.hasVerb) {
                    if (((li.numTyp == NumberTypes.ROMAN && n >= 2 && childs.size() > 0) && childs.get(childs.size() - 1).kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER && li.numTyp == nums.get(n - 2).numTyp) && NumberingHelper.calcDelta(nums.get(n - 2), li, false) == 1) {
                    }
                    else {
                        blk.add(li);
                        continue;
                    }
                }
                fr = FragToken._new1503(li.getBeginToken(), li.getEndToken(), li);
                childs.add(fr);
                fr.kind = com.pullenti.ner.instrument.InstrumentKind.CHAPTER;
                NumberingHelper.createNumber(fr, li);
                if (li.numEndToken != li.getEndToken() && li.numEndToken != null) {
                    if (li.hasManySpecChars) 
                        fr.children.add(FragToken._new1504(li.numEndToken.getNext(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT, li));
                    else {
                        FragToken frName = FragToken._new1496(li.numEndToken.getNext(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                        fr.children.add(frName);
                        fr.name = FragToken.getRestoredNameMT(frName, false);
                        i = correctName(fr, frName, _lines, i);
                    }
                }
                else if (isContractStruct) {
                    FragToken frName = FragToken._new1496(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                    fr.children.add(frName);
                    fr.name = FragToken.getRestoredNameMT(frName, false);
                }
                continue;
            }
            else if (n >= nums.size() && li.titleTyp != InstrToken1.StdTitleType.UNDEFINED) {
                fr = FragToken._new1503(li.getBeginToken(), li.getEndToken(), li);
                fr.kind = childs.get(childs.size() - 1).kind;
                childs.add(fr);
                FragToken frName = FragToken._new1496(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, li);
                fr.children.add(frName);
                fr.name = FragToken.getRestoredNameMT(frName, false);
                i = correctName(fr, frName, _lines, i);
                continue;
            }
            if (blk.size() == 0 && li.hasManySpecChars) 
                err++;
            blk.add(li);
        }
        int coef = -err;
        for (int i = 0; i < childs.size(); i++) {
            FragToken chap = childs.get(i);
            if (i == 0 && chap.number == 0 && chap.getLengthChar() > 1000) 
                coef -= 1;
            else {
                String nam = chap.name;
                if (nam == null) 
                    coef--;
                else if (nam.length() > 300) 
                    coef -= (nam.length() / 300);
                else {
                    coef += 1;
                    int len = chap.getLengthChar() - nam.length();
                    if (len > 200) 
                        coef += 1;
                    else if (chap.children.size() < 3) 
                        coef--;
                }
            }
            for (FragToken ch : chap.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.NAME) {
                    if (ch.getEndToken().isCharOf(":;")) 
                        coef -= 2;
                    break;
                }
                if (ch.number == 0) 
                    continue;
                if (ch.itok == null) 
                    break;
                break;
            }
        }
        if (coef < 3) {
            if (err > 2) 
                return true;
            return false;
        }
        com.pullenti.unisharp.Utils.addToArrayList(root.children, childs);
        if (_topDoc != null && _topDoc.m_Doc != null && _topDoc.m_Doc.getTyp() != null) {
            String ty = _topDoc.m_Doc.getTyp();
            if (docTyp == com.pullenti.ner.decree.DecreeKind.CONTRACT) {
                boolean ok = true;
                for (FragToken ch : childs) {
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER) {
                        for (FragToken chh : ch.children) {
                            if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) 
                                ok = false;
                        }
                    }
                }
                if (ok) {
                    for (FragToken ch : childs) {
                        if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER) 
                            ch.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSE;
                    }
                }
            }
        }
        return true;
    }

    private void _addCommentOrEdition(FragToken fr, InstrToken1 li) {
        if (li.typ == InstrToken1.Types.COMMENT) 
            fr.children.add(FragToken._new1504(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.COMMENT, li));
        else if (li.typ == InstrToken1.Types.EDITIONS) {
            FragToken edt = FragToken._new1504(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.EDITIONS, li);
            fr.children.add(edt);
            edt.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            for (com.pullenti.ner.Token tt = li.getBeginToken(); tt != null; tt = tt.getNext()) {
                if (tt.getEndChar() > li.getEndToken().getEndChar()) 
                    break;
                com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                if (dr != null) {
                    if (!edt.referents.contains(dr)) 
                        edt.referents.add(dr);
                }
            }
        }
    }

    private void _analizeContentWithoutContainers(FragToken root, java.util.ArrayList<InstrToken1> _lines, boolean isSubitem, boolean isPreamble, boolean isKodex) {
        if (root.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER) {
        }
        if (root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE || ((root.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER && docTyp == com.pullenti.ner.decree.DecreeKind.CONTRACT))) {
            if (root.number == 8) {
            }
            while (_lines.size() > 0) {
                if (_lines.get(0).typ == InstrToken1.Types.COMMENT || _lines.get(0).typ == InstrToken1.Types.EDITIONS) {
                    this._addCommentOrEdition(root, _lines.get(0));
                    _lines.remove(0);
                }
                else 
                    break;
            }
            if (_lines.size() == 0) 
                return;
            if ((_lines.size() > 2 && _lines.get(0).numbers.size() == 0 && _lines.get(0).getEndToken().isCharOf(":")) && _lines.get(1).numbers.size() > 0) {
            }
            if (_lines.get(0).numbers.size() == 0 && docTyp != com.pullenti.ner.decree.DecreeKind.CONTRACT) {
                java.util.ArrayList<FragToken> parts = new java.util.ArrayList<FragToken>();
                java.util.ArrayList<InstrToken1> tmp = new java.util.ArrayList<InstrToken1>();
                FragToken part = null;
                for (int ii = 0; ii < _lines.size(); ii++) {
                    InstrToken1 li = _lines.get(ii);
                    if ((ii > 0 && li.numbers.size() == 0 && li.typ != InstrToken1.Types.EDITIONS) && li.typ != InstrToken1.Types.COMMENT && part != null) {
                        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(li.getBeginToken())) {
                            boolean end = true;
                            for (int j = ii - 1; j >= 0; j--) {
                                if (_lines.get(j).typ != InstrToken1.Types.COMMENT && _lines.get(j).typ != InstrToken1.Types.EDITIONS) {
                                    com.pullenti.ner.Token tt = _lines.get(j).getEndToken();
                                    if (!tt.isCharOf(".")) {
                                        if (tt.getNewlinesAfterCount() < 2) 
                                            end = false;
                                        else if (tt.isCharOf(":,;")) 
                                            end = false;
                                    }
                                    break;
                                }
                            }
                            if (end) {
                                this._analizeContentWithoutContainers(part, tmp, false, false, isKodex);
                                tmp.clear();
                                part = null;
                            }
                        }
                    }
                    if (part == null) {
                        part = FragToken._new1511(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART, parts.size() + 1);
                        parts.add(part);
                    }
                    if (li.getEndChar() > part.getEndChar()) 
                        part.setEndToken(li.getEndToken());
                    tmp.add(li);
                }
                if (part != null && tmp.size() > 0) 
                    this._analizeContentWithoutContainers(part, tmp, false, false, isKodex);
                boolean ok = true;
                if (root.kind != com.pullenti.ner.instrument.InstrumentKind.CLAUSE) {
                    int num = 0;
                    int tot = 0;
                    for (FragToken p : parts) {
                        for (FragToken ch : p.children) {
                            if (ch.number > 0) 
                                num++;
                            tot++;
                        }
                    }
                    if ((tot / 2) > num) 
                        ok = false;
                }
                if (ok) {
                    for (FragToken p : parts) {
                        NumberingHelper.correctChildNumbers(root, p.children);
                    }
                    if (parts.size() > 1) {
                        com.pullenti.unisharp.Utils.addToArrayList(root.children, parts);
                        return;
                    }
                    else if (parts.size() == 1) {
                        com.pullenti.unisharp.Utils.addToArrayList(root.children, parts.get(0).children);
                        return;
                    }
                }
            }
        }
        if (root.number == 11 && root.subNumber == 2) {
        }
        java.util.ArrayList<FragToken> notices = new java.util.ArrayList<FragToken>();
        for (int ii = 0; ii < _lines.size(); ii++) {
            if (_lines.get(ii).typ == InstrToken1.Types.NOTICE) {
                InstrToken1 li = _lines.get(ii);
                if (((li.numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(li.numbers.get(0), "1"))) || ((li.numbers.size() == 0 && ii == (_lines.size() - 1)))) {
                    for (int j = ii; j < _lines.size(); j++) {
                        li = _lines.get(j);
                        FragToken not = FragToken._new1504(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NOTICE, li);
                        notices.add(not);
                        if (li.numBeginToken != null && li.getBeginToken() != li.numBeginToken) 
                            not.children.add(FragToken._new1513(li.getBeginToken(), li.numBeginToken.getPrevious(), com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true));
                        if (li.numbers.size() > 0) 
                            NumberingHelper.createNumber(not, li);
                        if (not.children.size() > 0) 
                            not.children.add(FragToken._new1504((li.numEndToken != null ? li.numEndToken : li.getBeginToken()), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT, li));
                    }
                    for(int jjj = ii + _lines.size() - ii - 1, mmm = ii; jjj >= mmm; jjj--) _lines.remove(jjj);
                }
                break;
            }
        }
        java.util.ArrayList<InstrToken1> nums = NumberingHelper.extractMainSequence(_lines, docTyp != com.pullenti.ner.decree.DecreeKind.CONTRACT || topDoc.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX, docTyp != com.pullenti.ner.decree.DecreeKind.CONTRACT);
        if (_lines.size() > 5) {
        }
        if (isKodex && nums != null) {
            int errCou = 0;
            for (InstrToken1 nu : nums) {
                if (nu.numSuffix != null && com.pullenti.unisharp.Utils.stringsNe(nu.numSuffix, ")") && com.pullenti.unisharp.Utils.stringsNe(nu.numSuffix, ".")) 
                    errCou++;
            }
            if (errCou > 0) {
                if (errCou > (nums.size() / 2)) 
                    nums = null;
            }
        }
        if (nums == null) {
            FragToken last = (root.children.size() > 0 ? root.children.get(root.children.size() - 1) : null);
            for (InstrToken1 li : _lines) {
                if (li.typ == InstrToken1.Types.COMMENT || li.typ == InstrToken1.Types.EDITIONS) {
                    this._addCommentOrEdition(root, li);
                    last = null;
                    continue;
                }
                if (li.typ == InstrToken1.Types.INDEX) {
                    FragToken ind = FragToken._new1504(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.INDEX, li);
                    root.children.add(ind);
                    last = null;
                    com.pullenti.ner.Token tt = li.getBeginToken();
                    if (!li.indexNoKeyword) {
                        for (; tt != null && tt.getEndChar() <= li.getEndChar(); tt = tt.getNext()) {
                            if (tt.isNewlineAfter()) {
                                ind.children.add(FragToken._new1516(li.getBeginToken(), tt, com.pullenti.ner.instrument.InstrumentKind.NAME, true));
                                tt = tt.getNext();
                                break;
                            }
                        }
                    }
                    boolean isTab = false;
                    for (; tt != null && tt.getEndChar() <= li.getEndChar(); tt = tt.getNext()) {
                        InstrToken1 it1 = InstrToken1.parse(tt, true, null, 0, null, false, 0, false, true);
                        if (it1 == null) 
                            break;
                        if ((!isTab && it1.getEndChar() == li.getEndChar() && tt.isTableControlChar()) && it1.getLengthChar() > 100) {
                            InstrToken1 it2 = InstrToken1.parse(tt.getNext(), true, null, 0, null, false, 0, false, true);
                            if (it2 == null) 
                                break;
                            it1 = it2;
                            tt = tt.getNext();
                        }
                        if (com.pullenti.unisharp.Utils.stringsEq(it1.value, "СТР")) {
                            tt = it1.getEndToken();
                            continue;
                        }
                        if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) {
                            tt = tt.kit.debedToken(tt);
                            it1 = InstrToken1.parse(tt, true, null, 0, null, false, 0, false, false);
                        }
                        if (it1.typ == InstrToken1.Types.APPENDIX && !it1.isNewlineAfter()) {
                            for (com.pullenti.ner.Token ttt = it1.getEndToken(); ttt != null; ttt = ttt.getNext()) {
                                if (ttt.isTableControlChar() || ttt.isNewlineBefore()) 
                                    break;
                                it1.setEndToken(ttt);
                            }
                        }
                        FragToken indItem = FragToken._new1494(tt, it1.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.INDEXITEM);
                        ind.children.add(indItem);
                        FragToken nam = null;
                        if (it1.numEndToken != null && it1.numEndToken != it1.getEndToken()) {
                            if (it1.getBeginToken() != it1.numBeginToken) 
                                indItem.children.add(FragToken._new1513(it1.getBeginToken(), it1.numBeginToken.getPrevious(), com.pullenti.ner.instrument.InstrumentKind.KEYWORD, true));
                            NumberingHelper.createNumber(indItem, it1);
                            indItem.children.add((nam = FragToken._new1513(it1.numEndToken.getNext(), it1.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true)));
                            InstrToken1 it2 = InstrToken1.parse(it1.getEndToken().getNext(), true, null, 0, null, false, 0, false, true);
                            if ((it2 != null && (it1.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && it2.numbers.size() == 0) && it2.titleTyp == InstrToken1.StdTitleType.UNDEFINED && !it1.getEndToken().getNext().isTableControlChar()) {
                                InstrToken1 it3 = InstrToken1.parse(it2.getEndToken().getNext(), true, null, 0, null, false, 0, false, true);
                                if (it3 != null && it3.numbers.size() > 0) {
                                    nam.setEndToken(it2.getEndToken());
                                    nam.setDefVal2(true);
                                    indItem.setEndToken(it1.setEndToken(it2.getEndToken()));
                                }
                            }
                        }
                        else 
                            indItem.children.add((nam = FragToken._new1513(it1.getBeginToken(), it1.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true)));
                        indItem.name = FragToken.getRestoredNameMT(nam, true);
                        String val = (String)com.pullenti.unisharp.Utils.cast(nam.value, String.class);
                        if (val != null) {
                            while (val.length() > 4) {
                                char ch = val.charAt(val.length() - 1);
                                if ((ch == '.' || ch == '-' || Character.isDigit(ch)) || com.pullenti.unisharp.Utils.isWhitespace(ch) || ch == ((char)7)) 
                                    val = val.substring(0, 0 + val.length() - 1);
                                else 
                                    break;
                            }
                            nam.value = val;
                        }
                        tt = it1.getEndToken();
                    }
                    continue;
                }
                if (last != null && last.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) 
                    last.setEndToken(li.getEndToken());
                else 
                    root.children.add((last = FragToken._new1504(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT, li)));
            }
            if (!isPreamble) {
                if ((root.children.size() == 1 && root.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && root.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) && ((root.children.get(0).itok == null || !root.children.get(0).itok.getHasChanges()))) {
                    if (root.itok == null) 
                        root.itok = root.children.get(0).itok;
                    root.children.clear();
                }
                else if (root.children.size() == 1 && root.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT && root.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                    root.children.clear();
                    root.kind = com.pullenti.ner.instrument.InstrumentKind.COMMENT;
                }
            }
            com.pullenti.unisharp.Utils.addToArrayList(root.children, notices);
            return;
        }
        if (isSubitem) {
        }
        int n = 0;
        FragToken fr = null;
        java.util.ArrayList<InstrToken1> blk = new java.util.ArrayList<InstrToken1>();
        int i;
        for (i = 0; i < _lines.size(); i++) {
            if (_lines.get(i) == nums.get(0)) 
                break;
            else 
                blk.add(_lines.get(i));
        }
        if (blk.size() > 0) 
            this._analizeContentWithoutContainers(root, blk, false, true, isKodex);
        for (; i < _lines.size(); i++) {
            InstrToken1 li = _lines.get(i);
            int j;
            blk.clear();
            n++;
            for (j = i + 1; j < _lines.size(); j++) {
                if ((n < nums.size()) && _lines.get(j) == nums.get(n)) 
                    break;
                else if (n >= nums.size() && _lines.get(j).titleTyp != InstrToken1.StdTitleType.UNDEFINED && _lines.get(j).allUpper) 
                    break;
                else 
                    blk.add(_lines.get(j));
            }
            fr = FragToken._new1503(li.getBeginToken(), li.getEndToken(), li);
            root.children.add(fr);
            fr.kind = (isSubitem ? com.pullenti.ner.instrument.InstrumentKind.SUBITEM : com.pullenti.ner.instrument.InstrumentKind.ITEM);
            NumberingHelper.createNumber(fr, li);
            if (li.numEndToken != li.getEndToken() && li.numEndToken != null) 
                fr.children.add(FragToken._new1504(li.numEndToken.getNext(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT, li));
            else if (li.titleTyp != InstrToken1.StdTitleType.UNDEFINED && li.allUpper) {
                fr.kind = com.pullenti.ner.instrument.InstrumentKind.TAIL;
                fr.children.add(FragToken._new1516(li.getBeginToken(), li.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true));
            }
            if (blk.size() > 0) {
                fr.setEndToken(blk.get(blk.size() - 1).getEndToken());
                this._analizeContentWithoutContainers(fr, blk, true, false, isKodex);
            }
            i = j - 1;
        }
        NumberingHelper.correctChildNumbers(root, root.children);
        com.pullenti.unisharp.Utils.addToArrayList(root.children, notices);
    }

    private static java.util.ArrayList<InstrToken1> _extractDirectiveSequence(java.util.ArrayList<InstrToken1> _lines) {
        java.util.ArrayList<InstrToken1> res = new java.util.ArrayList<InstrToken1>();
        for (int i = 0; i < _lines.size(); i++) {
            if (_lines.get(i).typ == InstrToken1.Types.DIRECTIVE) {
                int j;
                for (j = i - 1; j >= 0; j--) {
                    InstrToken1 li = _lines.get(j);
                    if (li.typ == InstrToken1.Types.FIRSTLINE) {
                        j--;
                        break;
                    }
                    if (li.getBeginToken().isValue("РУКОВОДСТВУЯСЬ", null) || li.getBeginToken().isValue("ИССЛЕДОВАВ", null)) {
                        j--;
                        break;
                    }
                    if (li.getBeginToken().isValue("НА", null) && li.getBeginToken().getNext() != null && li.getBeginToken().getNext().isValue("ОСНОВАНИЕ", null)) {
                        j--;
                        break;
                    }
                    if (li.numbers.size() > 0) 
                        break;
                    if (li.typ == InstrToken1.Types.COMMENT) 
                        continue;
                    if (li.typ == InstrToken1.Types.LINE) 
                        continue;
                    break;
                }
                res.add(_lines.get(j + 1));
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.get(0) != _lines.get(0)) 
            res.add(0, _lines.get(0));
        return res;
    }

    private void _analizeContentWithDirectives(FragToken root, java.util.ArrayList<InstrToken1> _lines, boolean isJus) {
        java.util.ArrayList<InstrToken1> dirSeq = _extractDirectiveSequence(_lines);
        if (dirSeq == null) {
            this._analizeContentWithoutContainers(root, _lines, false, false, false);
            return;
        }
        if (dirSeq.size() > 1) {
        }
        java.util.ArrayList<FragToken> parts = new java.util.ArrayList<FragToken>();
        int n = 0;
        int j;
        for (int i = 0; i < _lines.size(); i++) {
            if (_lines.get(i) == dirSeq.get(n)) {
                java.util.ArrayList<InstrToken1> blk = new java.util.ArrayList<InstrToken1>();
                for (j = i; j < _lines.size(); j++) {
                    if (((n + 1) < dirSeq.size()) && dirSeq.get(n + 1) == _lines.get(j)) 
                        break;
                    else 
                        blk.add(_lines.get(j));
                }
                FragToken fr = this._createDirectivePart(blk);
                if (fr != null) 
                    parts.add(fr);
                i = j - 1;
                n++;
            }
        }
        if (parts.size() == 0) 
            return;
        if (parts.size() == 1 && parts.get(0).children.size() > 0) {
            com.pullenti.unisharp.Utils.addToArrayList(root.children, parts.get(0).children);
            return;
        }
        if (parts.size() > 2 || ((parts.size() > 1 && isJus))) {
            if (parts.get(0).name == null && parts.get(parts.size() - 1).name != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(parts.get(1).name, "МОТИВИРОВОЧНАЯ") && !parts.get(0).isNewlineAfter()) {
                    com.pullenti.unisharp.Utils.addToArrayList(parts.get(0).children, parts.get(1).children);
                    parts.get(0).name = parts.get(1).name;
                    parts.remove(1);
                    if (parts.get(0).children.size() > 1 && parts.get(0).children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && parts.get(0).children.get(1).kind == com.pullenti.ner.instrument.InstrumentKind.PREAMBLE) {
                        parts.get(0).children.get(1).setBeginToken(parts.get(0).children.get(0).getBeginToken());
                        parts.get(0).children.remove(0);
                    }
                }
                else {
                    parts.get(0).name = "ВВОДНАЯ";
                    parts.get(0).kind = com.pullenti.ner.instrument.InstrumentKind.DOCPART;
                    if (parts.get(0).children.size() == 0) 
                        parts.get(0).children.add(FragToken._new1504(parts.get(0).getBeginToken(), parts.get(0).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT, parts.get(0).itok));
                }
            }
            for (int i = 0; i < (parts.size() - 1); i++) {
                if (com.pullenti.unisharp.Utils.stringsEq(parts.get(i).name, "МОТИВИРОВОЧНАЯ") && parts.get(i + 1).name == null) {
                    com.pullenti.unisharp.Utils.addToArrayList(parts.get(i).children, parts.get(i + 1).children);
                    parts.remove(i + 1);
                    i--;
                }
            }
            boolean hasNull = false;
            for (FragToken p : parts) {
                if (p.name == null) 
                    hasNull = true;
            }
            if (!hasNull) {
                com.pullenti.unisharp.Utils.addToArrayList(root.children, parts);
                return;
            }
        }
        for (FragToken p : parts) {
            if (p.children.size() > 0) 
                com.pullenti.unisharp.Utils.addToArrayList(root.children, p.children);
            else 
                root.children.add(p);
        }
    }

    private FragToken _createDirectivePart(java.util.ArrayList<InstrToken1> _lines) {
        FragToken res = FragToken._new1494(_lines.get(0).getBeginToken(), _lines.get(_lines.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DOCPART);
        java.util.ArrayList<InstrToken1> head = new java.util.ArrayList<InstrToken1>();
        int i;
        for (i = 0; i < _lines.size(); i++) {
            if (_lines.get(i).typ == InstrToken1.Types.DIRECTIVE) 
                break;
            else 
                head.add(_lines.get(i));
        }
        if (i >= _lines.size()) {
            this._analizeContentWithoutContainers(res, _lines, false, false, false);
            return res;
        }
        if (head.size() > 0) {
            FragToken frHead = FragToken._new1494(head.get(0).getBeginToken(), head.get(head.size() - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT);
            this._analizeContentWithoutContainers(frHead, head, false, false, false);
            res.children.add(frHead);
        }
        if (res.children.size() == 1 && res.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) 
            res.children.get(0).kind = com.pullenti.ner.instrument.InstrumentKind.PREAMBLE;
        res.children.add(FragToken._new1528(_lines.get(i).getBeginToken(), _lines.get(i).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE, _lines.get(i).value, _lines.get(i)));
        String vvv = _lines.get(i).value;
        if (com.pullenti.unisharp.Utils.stringsEq(vvv, "УСТАНОВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(vvv, "ВСТАНОВЛЕННЯ")) 
            res.name = "МОТИВИРОВОЧНАЯ";
        else if (((((com.pullenti.unisharp.Utils.stringsEq(vvv, "ПОСТАНОВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(vvv, "ОПРЕДЕЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(vvv, "ПРИГОВОР")) || com.pullenti.unisharp.Utils.stringsEq(vvv, "ПРИКАЗ") || com.pullenti.unisharp.Utils.stringsEq(vvv, "РЕШЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(vvv, "ПОСТАНОВА") || com.pullenti.unisharp.Utils.stringsEq(vvv, "ВИЗНАЧЕННЯ")) || com.pullenti.unisharp.Utils.stringsEq(vvv, "ВИРОК") || com.pullenti.unisharp.Utils.stringsEq(vvv, "НАКАЗ")) || com.pullenti.unisharp.Utils.stringsEq(vvv, "РІШЕННЯ")) 
            res.name = "РЕЗОЛЮТИВНАЯ";
        for(int jjj = 0 + i + 1 - 1, mmm = 0; jjj >= mmm; jjj--) _lines.remove(jjj);
        if (_lines.size() > 0) 
            this._analizeContentWithoutContainers(res, _lines, false, false, false);
        return res;
    }

    private void _analizeSections(FragToken root) {
        for (int k = 0; k < 2; k++) {
            java.util.ArrayList<Integer> secs = new java.util.ArrayList<Integer>();
            java.util.ArrayList<Integer> items = new java.util.ArrayList<Integer>();
            for (FragToken ch : root.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER || ch.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) {
                    if (ch.number == 0 || (ch.children.size() < 2)) 
                        return;
                    java.util.ArrayList<FragToken> newChilds = new java.util.ArrayList<FragToken>();
                    int i = 0;
                    for (; i < ch.children.size(); i++) {
                        if (ch.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.NUMBER && ch.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.NAME && ch.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.KEYWORD) 
                            break;
                        else 
                            newChilds.add(ch.children.get(i));
                    }
                    if (i >= ch.children.size()) 
                        return;
                    FragToken sect = null;
                    if (ch.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                        if (ch.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.ITEM) 
                            return;
                    }
                    else {
                        sect = FragToken._new1513(ch.children.get(i).getBeginToken(), ch.children.get(i).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.SECTION, true);
                        sect.name = (String)com.pullenti.unisharp.Utils.cast(sect.value, String.class);
                        sect.value = null;
                        sect.children.add(FragToken._new1494(sect.getBeginToken(), sect.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME));
                        newChilds.add(sect);
                        if ((ch.children.get(i).getWhitespacesBeforeCount() < 15) || (ch.children.get(i).getWhitespacesAfterCount() < 15)) 
                            return;
                        i++;
                        if (((i + 1) < ch.children.size()) && ch.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT) 
                            i++;
                    }
                    int j;
                    int its = 0;
                    for (j = i; j < ch.children.size(); j++) {
                        if (ch.children.get(j).kind != com.pullenti.ner.instrument.InstrumentKind.ITEM) 
                            return;
                        its++;
                        if (sect != null) {
                            sect.children.add(ch.children.get(j));
                            sect.setEndToken(ch.children.get(j).getEndToken());
                        }
                        else 
                            newChilds.add(ch.children.get(j));
                        if ((ch.children.get(j).getWhitespacesAfterCount() < 15) || j == (ch.children.size() - 1)) 
                            continue;
                        FragToken la = _getLastChild(ch.children.get(j));
                        if (la.getWhitespacesAfterCount() < 15) 
                            continue;
                        FragToken nextSect = null;
                        for (com.pullenti.ner.Token tt = la.getEndToken(); tt != null && tt.getBeginChar() > la.getBeginChar(); tt = tt.getPrevious()) {
                            if (tt.isNewlineBefore()) {
                                if (tt.chars.isCyrillicLetter() && tt.chars.isAllLower()) 
                                    continue;
                                InstrToken1 it = InstrToken1.parse(tt, true, null, 0, null, false, 0, false, false);
                                if (it != null && it.numbers.size() > 0) 
                                    break;
                                if (tt.getWhitespacesBeforeCount() < 15) 
                                    continue;
                                if ((tt.getPrevious().getEndChar() - la.getBeginChar()) < 20) 
                                    break;
                                nextSect = FragToken._new1513(tt, la.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.SECTION, true);
                                nextSect.name = (String)com.pullenti.unisharp.Utils.cast(nextSect.value, String.class);
                                nextSect.value = null;
                                nextSect.children.add(FragToken._new1494(tt, la.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME));
                                break;
                            }
                        }
                        if (nextSect == null) 
                            continue;
                        if (sect == null) 
                            return;
                        if (k > 0) {
                            sect.setEndToken(la.setEndToken(nextSect.getBeginToken().getPrevious()));
                            if (ch.children.get(j).getEndChar() > la.getEndChar()) 
                                ch.children.get(j).setEndToken(la.getEndToken());
                        }
                        newChilds.add(nextSect);
                        sect = nextSect;
                    }
                    if (k > 0) 
                        ch.children = newChilds;
                    else {
                        items.add(its);
                        secs.add(newChilds.size());
                    }
                }
            }
            if (k > 0) 
                break;
            if (secs.size() < 3) 
                break;
            int allsecs = 0;
            int allits = 0;
            int okchapts = 0;
            for (int i = 0; i < items.size(); i++) {
                allits += items.get(i);
                allsecs += secs.get(i);
                if (secs.get(i) > 1) 
                    okchapts++;
            }
            float rr = ((float)allits) / ((float)allsecs);
            if (rr < 1.5) 
                break;
            if (okchapts < (items.size() / 2)) 
                break;
        }
    }

    private static FragToken _getLastChild(FragToken fr) {
        if (fr.children.size() == 0) 
            return fr;
        return _getLastChild(fr.children.get(fr.children.size() - 1));
    }

    private void _correctNames(FragToken root, FragToken parent) {
        int i;
        java.util.ArrayList<FragToken> frNams = null;
        for (i = 0; i < root.children.size(); i++) {
            FragToken ch = root.children.get(i);
            if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.CLAUSE && ch.kind != com.pullenti.ner.instrument.InstrumentKind.CHAPTER) 
                continue;
            if (ch.name != null) {
                frNams = null;
                break;
            }
            int j;
            boolean namHas = false;
            for (j = 0; j < ch.children.size(); j++) {
                FragToken chh = ch.children.get(j);
                if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD || chh.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER || chh.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                    continue;
                if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT || chh.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION || ((chh.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && chh.children.size() == 1))) {
                    if (chh.itok == null) 
                        chh.itok = InstrToken1.parse(chh.getBeginToken(), true, null, 0, null, false, 0, false, false);
                    if (chh.itok != null && !chh.itok.hasVerb) 
                        namHas = true;
                }
                break;
            }
            if (!namHas) {
                frNams = null;
                break;
            }
            if (frNams == null) {
                frNams = new java.util.ArrayList<FragToken>();
                frNams.add(ch);
            }
            else {
                if (frNams.get(frNams.size() - 1).kind != ch.kind) {
                    frNams = null;
                    break;
                }
                frNams.add(ch);
            }
        }
        if (frNams != null) {
            for (FragToken ch : frNams) {
                int j;
                for (j = 0; j < ch.children.size(); j++) {
                    FragToken chh = ch.children.get(j);
                    if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD || chh.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER || chh.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                        continue;
                    if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT || chh.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION || ((chh.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && chh.children.size() == 1))) 
                        break;
                }
                if (j >= ch.children.size()) 
                    continue;
                FragToken nam = ch.children.get(j);
                if (nam.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION || ((nam.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && nam.children.size() == 1))) {
                    nam.number = 0;
                    int cou = 0;
                    for (int jj = j + 1; jj < ch.children.size(); jj++) {
                        if (ch.children.get(jj).kind == nam.kind) {
                            ch.children.get(jj).number--;
                            cou++;
                        }
                        else 
                            break;
                    }
                    if (cou == 1) {
                        for (int jj = j + 1; jj < ch.children.size(); jj++) {
                            if (ch.children.get(jj).kind == nam.kind) {
                                boolean empty = true;
                                for (int k = jj + 1; k < ch.children.size(); k++) {
                                    if (ch.children.get(k).kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS && ch.children.get(k).kind != com.pullenti.ner.instrument.InstrumentKind.COMMENT) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    if (ch.children.get(jj).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION || ch.children.size() == 0) {
                                        ch.children.get(jj).kind = com.pullenti.ner.instrument.InstrumentKind.CONTENT;
                                        ch.children.get(jj).number = 0;
                                    }
                                    else {
                                        FragToken ch0 = ch.children.get(jj);
                                        ch.children.remove(jj);
                                        com.pullenti.unisharp.Utils.insertToArrayList(ch.children, ch0.children, jj);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                nam.number = 0;
                nam.kind = com.pullenti.ner.instrument.InstrumentKind.NAME;
                nam.setDefVal2(true);
                nam.children.clear();
                ch.name = (String)com.pullenti.unisharp.Utils.cast(nam.value, String.class);
            }
        }
        com.pullenti.ner.Token tt = root.getBeginToken();
        if (root.itok != null && root.itok.numEndToken != null) 
            tt = root.itok.numEndToken.getNext();
        if (tt != null) {
            if (parent != null && parent.isExpired) {
            }
            else {
                if (!tt.isValue("УТРАТИТЬ", "ВТРАТИТИ")) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null) 
                        tt = npt.getEndToken().getNext();
                }
                if ((tt != null && tt.isValue("УТРАТИТЬ", "ВТРАТИТИ") && tt.getNext() != null) && tt.getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
                    root.isExpired = true;
                else if (tt != null && tt.isValue("ИСКЛЮЧИТЬ", null)) {
                    if (tt.isNewlineAfter()) 
                        root.isExpired = true;
                    else if (tt.getNext() != null && ((tt.getNext().isHiphen() || tt.getNext().isCharOf(".;")))) 
                        root.isExpired = true;
                }
            }
        }
        for (FragToken ch : root.children) {
            this._correctNames(ch, root);
        }
    }

    private void _correctKodexParts(FragToken root) {
        if (root.number == 2 && root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) {
        }
        if (root.number == 11 && root.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM) {
        }
        int i;
        for (i = 0; i < root.children.size(); i++) {
            com.pullenti.ner.instrument.InstrumentKind ki = root.children.get(i).kind;
            if ((ki != com.pullenti.ner.instrument.InstrumentKind.KEYWORD && ki != com.pullenti.ner.instrument.InstrumentKind.NAME && ki != com.pullenti.ner.instrument.InstrumentKind.NUMBER) && ki != com.pullenti.ner.instrument.InstrumentKind.COMMENT && ki != com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                break;
        }
        if (i >= root.children.size()) 
            return;
        int i0 = i;
        if (root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE && docTyp != com.pullenti.ner.decree.DecreeKind.CONTRACT) {
            for (; i < root.children.size(); i++) {
                FragToken ch = root.children.get(i);
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM || ch.kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM || ((ch.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && ch.number > 0))) {
                    ch.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART;
                    for (FragToken chh : ch.children) {
                        if (chh.kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM && chh.number > 0) {
                            chh.kind = com.pullenti.ner.instrument.InstrumentKind.ITEM;
                            for (FragToken chhh : chh.children) {
                                if (chhh.number > 0) 
                                    chhh.kind = com.pullenti.ner.instrument.InstrumentKind.SUBITEM;
                            }
                        }
                    }
                }
                else if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) 
                    break;
            }
        }
        if (i == i0 && root.children.get(i0).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
            for (i = i0 + 1; i < root.children.size(); i++) {
                if (root.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS && root.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.COMMENT) 
                    break;
            }
            if ((i < root.children.size()) && ((((docTyp == com.pullenti.ner.decree.DecreeKind.KODEX || root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE || root.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM) || root.kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM || root.kind == com.pullenti.ner.instrument.InstrumentKind.CHAPTER) || root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART))) {
                if (root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.LISTITEM || root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.ITEM || root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) {
                    int num = 1;
                    root.children.get(i0).kind = com.pullenti.ner.instrument.InstrumentKind.INDENTION;
                    root.children.get(i0).number = num;
                    if (root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.LISTITEM) {
                        for (; i < root.children.size(); i++) {
                            if (root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.LISTITEM) {
                                root.children.get(i).kind = com.pullenti.ner.instrument.InstrumentKind.INDENTION;
                                root.children.get(i).number = ++num;
                            }
                            else if (root.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.COMMENT && root.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                                break;
                        }
                    }
                }
            }
        }
        int inds = 0;
        for (i = i0; i < root.children.size(); i++) {
            if (root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT) 
                continue;
            java.util.ArrayList<FragToken> lii = _splitContentByIndents(root.children.get(i), inds + 1);
            if (lii == null) 
                break;
            inds += lii.size();
        }
        if (inds > 1 && ((i >= root.children.size() || root.children.get(i).kind != com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE))) {
            if (root.number == 7 && root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART) {
            }
            int num = 1;
            for (i = i0; i < root.children.size(); i++) {
                if (root.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT) 
                    continue;
                java.util.ArrayList<FragToken> lii = _splitContentByIndents(root.children.get(i), num);
                if (lii == null) 
                    break;
                if (lii.size() == 0) 
                    continue;
                num += lii.size();
                root.children.remove(i);
                com.pullenti.unisharp.Utils.insertToArrayList(root.children, lii, i);
                i += (lii.size() - 1);
            }
            num = 1;
            for (i = i0 + 1; i < root.children.size(); i++) {
                FragToken ch = root.children.get(i);
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                    continue;
                if (ch.itok == null || ch.itok.numbers.size() != 1) 
                    break;
                if (ch.itok.getFirstNumber() != num) 
                    break;
                num++;
            }
            if (num > 1 && i >= root.children.size()) {
                for (i = i0 + 1; i < root.children.size(); i++) {
                    FragToken ch = root.children.get(i);
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                        continue;
                    if (root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART || root.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) 
                        ch.kind = com.pullenti.ner.instrument.InstrumentKind.ITEM;
                    else if (root.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM) 
                        ch.kind = com.pullenti.ner.instrument.InstrumentKind.SUBITEM;
                    else 
                        break;
                    NumberingHelper.createNumber(ch, ch.itok);
                    if (ch.children.size() == 1 && (ch.children.get(0).getEndChar() < ch.getEndChar())) 
                        ch.fillByContentChildren();
                }
            }
        }
        for (FragToken ch : root.children) {
            this._correctKodexParts(ch);
        }
    }

    private static java.util.ArrayList<FragToken> _splitContentByIndents(FragToken fr, int num) {
        if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.CONTENT && fr.kind != com.pullenti.ner.instrument.InstrumentKind.LISTITEM && fr.kind != com.pullenti.ner.instrument.InstrumentKind.PREAMBLE) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                return null;
            if (fr.getBeginToken().isValue("АБЗАЦ", null)) {
                com.pullenti.ner.Token t = fr.getBeginToken().getNext();
                if (!(t instanceof com.pullenti.ner.NumberToken) || com.pullenti.unisharp.Utils.stringsNe(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), ((Integer)num).toString())) 
                    return null;
                int next = num;
                t = t.getNext();
                if ((t != null && t.isHiphen() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                    next = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                    t = t.getNext().getNext();
                    if (next <= num) 
                        return null;
                }
                if ((t == null || !t.isValue("УТРАТИТЬ", "ВТРАТИТИ") || t.getNext() == null) || !t.getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
                    return null;
                java.util.ArrayList<FragToken> res0 = new java.util.ArrayList<FragToken>();
                for (int i = num; i <= next; i++) {
                    res0.add(FragToken._new1533(fr.getBeginToken(), fr.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.INDENTION, i, true, fr.referents));
                }
                return res0;
            }
            return new java.util.ArrayList<FragToken>();
        }
        if (fr.children.size() > 0) 
            return null;
        if (fr.itok == null) 
            fr.itok = InstrToken1.parse(fr.getBeginToken(), true, null, 0, null, false, 0, false, false);
        java.util.ArrayList<FragToken> res = new java.util.ArrayList<FragToken>();
        com.pullenti.ner.Token t0 = fr.getBeginToken();
        for (com.pullenti.ner.Token tt = t0; tt != null && tt.getEndChar() <= fr.getEndChar(); tt = tt.getNext()) {
            if (tt.getEndChar() == fr.getEndChar()) {
            }
            else if (!tt.isNewlineAfter()) 
                continue;
            else if (tt.isTableControlChar()) 
                continue;
            else if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext()) && !tt.isCharOf(":")) 
                continue;
            FragToken re = FragToken._new1511(t0, tt, com.pullenti.ner.instrument.InstrumentKind.INDENTION, num);
            num++;
            if (t0 == fr.getBeginToken() && tt == fr.getEndToken()) 
                re.itok = fr.itok;
            if (re.itok == null) 
                re.itok = InstrToken1.parse(t0, true, null, 0, null, false, 0, false, false);
            if (res.size() > 100) 
                return null;
            res.add(re);
            t0 = tt.getNext();
        }
        return res;
    }

    private void _analizePreamble(FragToken root) {
        int i;
        int cntCou = 0;
        FragToken ch = null;
        boolean ok = false;
        if ((root.children.size() > 1 && root.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && root.children.get(1).number > 0) && root.children.get(0).children.size() > 0) {
            for (i = 0; i < root.children.get(0).children.size(); i++) {
                FragToken ch2 = root.children.get(0).children.get(i);
                if ((ch2.kind != com.pullenti.ner.instrument.InstrumentKind.CONTENT && ch2.kind != com.pullenti.ner.instrument.InstrumentKind.INDENTION && ch2.kind != com.pullenti.ner.instrument.InstrumentKind.COMMENT) && ch2.kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                    break;
            }
            if (i >= root.children.get(0).children.size()) {
                FragToken chh = root.children.get(0);
                root.children.remove(0);
                com.pullenti.unisharp.Utils.insertToArrayList(root.children, chh.children, 0);
            }
        }
        for (i = 0; i < root.children.size(); i++) {
            ch = root.children.get(i);
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS || ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.INDEX) 
                continue;
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE) {
                ok = true;
                break;
            }
            if (ch.itok != null && ch.itok.getHasChanges()) 
                break;
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && ch.children.size() == 1 && ch.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.INDEX) {
                ch.kind = com.pullenti.ner.instrument.InstrumentKind.INDEX;
                ch.children = ch.children.get(0).children;
                continue;
            }
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
                for (com.pullenti.ner.Token t = ch.getBeginToken().getNext(); t != null && (t.getEndChar() < ch.getEndChar()); t = t.getNext()) {
                    if (t.isNewlineBefore()) {
                        if (t.getPrevious().isCharOf(".:;") && t.getPrevious().getPrevious() != null && ((t.getPrevious().getPrevious().isValue("НИЖЕСЛЕДУЮЩИЙ", null) || t.getPrevious().getPrevious().isValue("ДОГОВОР", null)))) {
                            InstrToken1 itt1 = InstrToken1.parse(t, true, null, 0, null, false, 0, false, false);
                            if (itt1 != null && !itt1.hasVerb && (itt1.getEndChar() < ch.getEndChar())) {
                                FragToken clau = FragToken._new1494(t, ch.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CHAPTER);
                                if (((i + 1) < root.children.size()) && root.children.get(i + 1).kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE) 
                                    clau.kind = com.pullenti.ner.instrument.InstrumentKind.CLAUSE;
                                FragToken nam = FragToken._new1496(t, itt1.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.NAME, true, itt1);
                                clau.children.add(nam);
                                clau.name = FragToken.getRestoredNameMT(nam, false);
                                clau.children.add(FragToken._new1494(itt1.getEndToken().getNext(), ch.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
                                ch.setEndToken(t.getPrevious());
                                root.children.add(i + 1, clau);
                            }
                            break;
                        }
                    }
                }
                boolean pream = false;
                if (ch.getBeginToken().isValue("ПРЕАМБУЛА", null)) 
                    pream = true;
                else if (ch.getLengthChar() > 1500) 
                    break;
                cntCou++;
                if (ch.getEndToken().isChar(':') || pream || ((ch.getEndToken().getPrevious() != null && ch.getEndToken().getPrevious().isValue("НИЖЕСЛЕДУЮЩИЙ", null)))) {
                    ok = true;
                    i++;
                    break;
                }
                continue;
            }
            break;
        }
        if (cntCou == 0 || cntCou > 3 || i >= root.children.size()) 
            return;
        if (ch.number > 0) 
            ok = true;
        if (!ok) 
            return;
        if (cntCou == 1) {
            for (int j = 0; j < i; j++) {
                if (root.children.get(j).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT || root.children.get(j).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
                    root.children.get(j).kind = com.pullenti.ner.instrument.InstrumentKind.PREAMBLE;
                    if (root.children.get(j).children.size() == 1 && root.children.get(j).children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.INDEX) {
                        root.children.get(j).kind = com.pullenti.ner.instrument.InstrumentKind.INDEX;
                        root.children.get(j).children = root.children.get(j).children.get(0).children;
                    }
                }
            }
        }
        else {
            FragToken prm = FragToken._new1494(root.children.get(0).getBeginToken(), root.children.get(i - 1).getEndToken(), com.pullenti.ner.instrument.InstrumentKind.PREAMBLE);
            for (int j = 0; j < i; j++) {
                prm.children.add(root.children.get(j));
            }
            for(int jjj = 0 + i - 1, mmm = 0; jjj >= mmm; jjj--) root.children.remove(jjj);
            root.children.add(0, prm);
        }
    }
    public ContentAnalyzeWhapper() {
    }
}
