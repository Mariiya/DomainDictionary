/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

public class BlockTitleToken extends com.pullenti.ner.MetaToken {

    public BlockTitleToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public BlkTyps typ = BlkTyps.UNDEFINED;

    public String value;

    @Override
    public String toString() {
        return (typ.toString() + " " + ((value != null ? value : "")) + " " + this.getSourceText());
    }

    public static java.util.ArrayList<BlockTitleToken> tryAttachList(com.pullenti.ner.Token t) {
        BlockTitleToken content = null;
        BlockTitleToken intro = null;
        java.util.ArrayList<BlockTitleToken> lits = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) {
                BlockTitleToken btt = tryAttach(tt, false, null);
                if (btt == null) 
                    continue;
                if (btt.typ == BlkTyps.INDEX) {
                    content = btt;
                    break;
                }
                if (btt.typ == BlkTyps.INTRO) {
                    com.pullenti.ner.Token tt2 = btt.getEndToken().getNext();
                    for (int k = 0; k < 5; k++) {
                        BlockLine li = BlockLine.create(tt2, null);
                        if (li == null) 
                            break;
                        if (li.hasContentItemTail || li.typ == BlkTyps.INDEXITEM) {
                            content = btt;
                            break;
                        }
                        if (li.hasVerb) 
                            break;
                        if (li.typ != BlkTyps.UNDEFINED) {
                            if ((li.getBeginChar() - btt.getEndChar()) < 400) {
                                content = btt;
                                break;
                            }
                        }
                        tt2 = li.getEndToken().getNext();
                    }
                    if (content == null) 
                        intro = btt;
                    break;
                }
                if (btt.typ == BlkTyps.LITERATURE) {
                    if (lits == null) 
                        lits = new java.util.ArrayList<BlockTitleToken>();
                    lits.add(btt);
                }
            }
        }
        if (content == null && intro == null && ((lits == null || lits.size() != 1))) 
            return null;
        java.util.ArrayList<BlockTitleToken> res = new java.util.ArrayList<BlockTitleToken>();
        com.pullenti.ner.core.TerminCollection chapterNames = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.Token t0 = null;
        if (content != null) {
            res.add(content);
            int cou = 0;
            int err = 0;
            for (com.pullenti.ner.Token tt = content.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (!tt.isNewlineBefore()) 
                    continue;
                BlockLine li = BlockLine.create(tt, null);
                if (li == null) 
                    break;
                if (li.hasVerb) {
                    if (li.getEndToken().isChar('.')) 
                        break;
                    if (li.getLengthChar() > 100) 
                        break;
                }
                BlockTitleToken btt = tryAttach(tt, true, null);
                if (btt == null) 
                    continue;
                err = 0;
                if (btt.typ == BlkTyps.INTRO) {
                    if (content.typ == BlkTyps.INTRO || cou > 2) 
                        break;
                }
                cou++;
                tt = content.setEndToken(btt.getEndToken());
                if (btt.value != null) 
                    chapterNames.addString(btt.value, null, null, false);
            }
            content.typ = BlkTyps.INDEX;
            t0 = content.getEndToken().getNext();
        }
        else if (intro != null) 
            t0 = intro.getBeginToken();
        else if (lits != null) 
            t0 = t;
        else 
            return null;
        boolean first = true;
        for (com.pullenti.ner.Token tt = t0; tt != null; tt = tt.getNext()) {
            if (!tt.isNewlineBefore()) 
                continue;
            if (tt.isValue("СЛАБОЕ", null)) {
            }
            BlockTitleToken btt = tryAttach(tt, false, chapterNames);
            if (btt == null) 
                continue;
            if (res.size() == 104) {
            }
            tt = btt.getEndToken();
            if (content != null && btt.typ == BlkTyps.INDEX) 
                continue;
            if (res.size() > 0 && res.get(res.size() - 1).typ == BlkTyps.LITERATURE) {
                if (btt.typ != BlkTyps.APPENDIX && btt.typ != BlkTyps.MISC && btt.typ != BlkTyps.LITERATURE) {
                    if (btt.typ == BlkTyps.CHAPTER && (res.get(res.size() - 1).getEndChar() < ((tt.kit.getSofa().getText().length() * 3) / 4))) {
                    }
                    else 
                        continue;
                }
            }
            if (first) {
                if ((tt.getBeginChar() - t0.getBeginChar()) > 300) {
                    BlockTitleToken btt0 = new BlockTitleToken(t0, (t0.getPrevious() == null ? t0 : t0.getPrevious()));
                    btt0.typ = BlkTyps.CHAPTER;
                    btt0.value = "Похоже на начало";
                    res.add(btt0);
                }
            }
            res.add(btt);
            tt = btt.getEndToken();
            first = false;
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == BlkTyps.LITERATURE && res.get(i + 1).typ == res.get(i).typ) {
                res.remove(i + 1);
                i--;
            }
        }
        return res;
    }

    public static BlockTitleToken tryAttach(com.pullenti.ner.Token t, boolean isContentItem, com.pullenti.ner.core.TerminCollection names) {
        if (t == null) 
            return null;
        if (!t.isNewlineBefore()) 
            return null;
        if (t.chars.isAllLower()) 
            return null;
        BlockLine li = BlockLine.create(t, names);
        if (li == null) 
            return null;
        if (li.words == 0 && li.typ == BlkTyps.UNDEFINED) 
            return null;
        if (li.typ == BlkTyps.INDEX) {
        }
        if (li.isExistName) 
            return _new407(t, li.getEndToken(), li.typ);
        if (li.getEndToken() == li.numberEnd || ((li.getEndToken().isCharOf(".:") && li.getEndToken().getPrevious() == li.numberEnd))) {
            BlockTitleToken res2 = _new407(t, li.getEndToken(), li.typ);
            if (li.typ == BlkTyps.CHAPTER || li.typ == BlkTyps.APPENDIX) {
                BlockLine li2 = BlockLine.create(li.getEndToken().getNext(), names);
                if ((li2 != null && li2.typ == BlkTyps.UNDEFINED && li2.isAllUpper) && li2.words > 0) {
                    res2.setEndToken(li2.getEndToken());
                    for (com.pullenti.ner.Token tt = res2.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                        li2 = BlockLine.create(tt, names);
                        if (li2 == null) 
                            break;
                        if (li2.typ != BlkTyps.UNDEFINED || !li2.isAllUpper || li2.words == 0) 
                            break;
                        tt = res2.setEndToken(li2.getEndToken());
                    }
                }
            }
            return res2;
        }
        if (li.numberEnd == null) 
            return null;
        BlockTitleToken res = _new407(t, li.getEndToken(), li.typ);
        if (res.typ == BlkTyps.UNDEFINED) {
            if (li.words < 1) 
                return null;
            if (li.hasVerb) 
                return null;
            if (!isContentItem) {
                if (!li.isAllUpper || li.notWords > (li.words / 2)) 
                    return null;
            }
            res.typ = BlkTyps.CHAPTER;
            if ((li.numberEnd.getEndChar() - t.getBeginChar()) == 7 && li.numberEnd.getNext() != null && li.numberEnd.getNext().isHiphen()) 
                res.typ = BlkTyps.UNDEFINED;
        }
        if (li.hasContentItemTail && isContentItem) 
            res.typ = BlkTyps.INDEXITEM;
        if (res.typ == BlkTyps.CHAPTER || res.typ == BlkTyps.APPENDIX) {
            if (li.hasVerb) 
                return null;
            if (li.notWords > li.words && !isContentItem) 
                return null;
            for (t = li.getEndToken().getNext(); t != null; t = t.getNext()) {
                BlockLine li2 = BlockLine.create(t, names);
                if (li2 == null) 
                    break;
                if (li2.hasVerb || (li2.words < 1)) 
                    break;
                if (!li2.isAllUpper && !isContentItem) 
                    break;
                if (li2.typ != BlkTyps.UNDEFINED || li2.numberEnd != null) 
                    break;
                t = res.setEndToken(li2.getEndToken());
                if (isContentItem && li2.hasContentItemTail) {
                    res.typ = BlkTyps.INDEXITEM;
                    break;
                }
            }
        }
        for (com.pullenti.ner.Token tt = res.getEndToken(); tt != null && tt.getBeginChar() > li.numberEnd.getEndChar(); tt = tt.getPrevious()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) {
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(li.numberEnd.getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                break;
            }
        }
        if ((res.typ == BlkTyps.INDEX || res.typ == BlkTyps.INTRO || res.typ == BlkTyps.CONSLUSION) || res.typ == BlkTyps.LITERATURE) {
            if (res.value != null && res.value.length() > 100) 
                return null;
            if (li.words < li.notWords) 
                return null;
        }
        return res;
    }

    public static BlockTitleToken _new407(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BlkTyps _arg3) {
        BlockTitleToken res = new BlockTitleToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public BlockTitleToken() {
        super();
    }
}
