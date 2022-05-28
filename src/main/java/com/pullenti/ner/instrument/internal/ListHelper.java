/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class ListHelper {

    public static void analyze(FragToken res) {
        if (res.number == 4) {
        }
        if (res.children.size() == 0) {
            com.pullenti.ner.instrument.InstrumentKind ki = res.kind;
            if (((ki == com.pullenti.ner.instrument.InstrumentKind.CHAPTER || ki == com.pullenti.ner.instrument.InstrumentKind.CLAUSE || ki == com.pullenti.ner.instrument.InstrumentKind.CONTENT) || ki == com.pullenti.ner.instrument.InstrumentKind.ITEM || ki == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) || ki == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART || ki == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
                java.util.ArrayList<FragToken> tmp = new java.util.ArrayList<FragToken>();
                tmp.add(res);
                _analizeListItems(tmp, 0);
            }
            return;
        }
        if (res.kind == com.pullenti.ner.instrument.InstrumentKind.CLAUSE && res.number == 12) {
        }
        for (int i = 0; i < res.children.size(); i++) {
            if (res.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION && ((res.children.get(i).getEndToken().isCharOf(":;") || ((((i + 1) < res.children.size()) && res.children.get(i + 1).kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS && res.children.get(i + 1).getEndToken().isCharOf(":;")))))) {
                int j;
                int cou = 1;
                char listBullet = (char)0;
                for (j = i + 1; j < res.children.size(); j++) {
                    FragToken ch = res.children.get(j);
                    if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                        continue;
                    if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                        break;
                    if (ch.getEndToken().isCharOf(";") || ((((j + 1) < res.children.size()) && res.children.get(j + 1).kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS && res.children.get(j + 1).getEndToken().isChar(';')))) {
                        cou++;
                        if ((ch.getBeginToken() instanceof com.pullenti.ner.TextToken) && !ch.chars.isLetter()) 
                            listBullet = ch.kit.getTextCharacter(ch.getBeginChar());
                        continue;
                    }
                    if (ch.getEndToken().isCharOf(".")) {
                        cou++;
                        j++;
                        break;
                    }
                    if (ch.getEndToken().isCharOf(":")) {
                        if (((int)listBullet) != 0 && ch.getBeginToken().isChar(listBullet)) {
                            for (com.pullenti.ner.Token tt = ch.getBeginToken().getNext(); tt != null && (tt.getEndChar() < ch.getEndChar()); tt = tt.getNext()) {
                                if (tt.getPrevious().isChar('.') && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) {
                                    FragToken ch2 = FragToken._new1511(tt, ch.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.INDENTION, ch.number);
                                    ch.setEndToken(tt.getPrevious());
                                    res.children.add(j + 1, ch2);
                                    for (int k = j + 1; k < res.children.size(); k++) {
                                        if (res.children.get(k).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                                            res.children.get(k).number++;
                                    }
                                    cou++;
                                    j++;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    cou++;
                    j++;
                    break;
                }
                if (cou < 3) {
                    i = j;
                    continue;
                }
                if ((i > 0 && !res.children.get(i).getEndToken().isChar(':') && res.children.get(i - 1).kind2 == com.pullenti.ner.instrument.InstrumentKind.UNDEFINED) && res.children.get(i - 1).getEndToken().isChar(':')) 
                    res.children.get(i - 1).kind2 = com.pullenti.ner.instrument.InstrumentKind.LISTHEAD;
                for (; i < j; i++) {
                    FragToken ch = res.children.get(i);
                    if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                        continue;
                    if (ch.getEndToken().isChar(':')) 
                        ch.kind2 = com.pullenti.ner.instrument.InstrumentKind.LISTHEAD;
                    else if (((i + 1) < j) && res.children.get(i + 1).kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS && res.children.get(i + 1).getEndToken().isChar(':')) 
                        ch.kind2 = com.pullenti.ner.instrument.InstrumentKind.LISTHEAD;
                    else 
                        ch.kind2 = com.pullenti.ner.instrument.InstrumentKind.LISTITEM;
                }
            }
        }
        java.util.ArrayList<FragToken> changed = new java.util.ArrayList<FragToken>();
        for (int i = 0; i < res.children.size(); i++) {
            if (res.number == 7) {
            }
            if (res.children.get(i).children.size() > 0) 
                analyze(res.children.get(i));
            else {
                int co = _analizeListItems(res.children, i);
                if (co > 0) {
                    changed.add(res.children.get(i));
                    if (co > 1) 
                        for(int jjj = i + 1 + co - 1 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.children.remove(jjj);
                    i += (co - 1);
                }
            }
        }
        for (int i = changed.size() - 1; i >= 0; i--) {
            if (changed.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                int j = res.children.indexOf(changed.get(i));
                if (j < 0) 
                    continue;
                res.children.remove(j);
                com.pullenti.unisharp.Utils.insertToArrayList(res.children, changed.get(i).children, j);
            }
        }
    }

    private static int _analizeListItems(java.util.ArrayList<FragToken> chi, int ind) {
        if (ind >= chi.size()) 
            return -1;
        FragToken res = chi.get(ind);
        com.pullenti.ner.instrument.InstrumentKind ki = res.kind;
        if (((ki == com.pullenti.ner.instrument.InstrumentKind.CHAPTER || ki == com.pullenti.ner.instrument.InstrumentKind.CLAUSE || ki == com.pullenti.ner.instrument.InstrumentKind.CONTENT) || ki == com.pullenti.ner.instrument.InstrumentKind.ITEM || ki == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) || ki == com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART || ki == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
        }
        else 
            return -1;
        if (res.getHasChanges() && res.getMultilineChangesValue() != null) {
            com.pullenti.ner.MetaToken ci = res.getMultilineChangesValue();
            FragToken cit = FragToken._new1494(ci.getBeginToken(), ci.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.CITATION);
            res.children.add(cit);
            if (com.pullenti.ner.core.BracketHelper.isBracket(cit.getBeginToken().getPrevious(), true)) 
                cit.setBeginToken(cit.getBeginToken().getPrevious());
            if (com.pullenti.ner.core.BracketHelper.isBracket(cit.getEndToken().getNext(), true)) {
                cit.setEndToken(cit.getEndToken().getNext());
                if (cit.getEndToken().getNext() != null && cit.getEndToken().getNext().isCharOf(";.")) 
                    cit.setEndToken(cit.getEndToken().getNext());
            }
            res.fillByContentChildren();
            if (res.children.get(0).getHasChanges()) {
            }
            com.pullenti.ner.instrument.InstrumentKind citKind = com.pullenti.ner.instrument.InstrumentKind.UNDEFINED;
            if (ci.tag instanceof com.pullenti.ner.decree.DecreeChangeReferent) {
                com.pullenti.ner.decree.DecreeChangeReferent dcr = (com.pullenti.ner.decree.DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(ci.tag, com.pullenti.ner.decree.DecreeChangeReferent.class);
                if (dcr.getValue() != null && dcr.getValue().getNewItems().size() > 0) {
                    String mnem = dcr.getValue().getNewItems().get(0);
                    int i;
                    if ((((i = mnem.indexOf(' ')))) > 0) 
                        mnem = mnem.substring(0, 0 + i);
                    citKind = com.pullenti.ner.decree.internal.PartToken._getInstrKindByTyp(com.pullenti.ner.decree.internal.PartToken._getTypeByAttrName(mnem));
                }
                else if (dcr.getOwners().size() > 0 && (dcr.getOwners().get(0) instanceof com.pullenti.ner.decree.DecreePartReferent) && dcr.getKind() == com.pullenti.ner.decree.DecreeChangeKind.NEW) {
                    com.pullenti.ner.decree.DecreePartReferent pat = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(dcr.getOwners().get(0), com.pullenti.ner.decree.DecreePartReferent.class);
                    int min = 0;
                    for (com.pullenti.ner.Slot s : pat.getSlots()) {
                        com.pullenti.ner.decree.internal.PartToken.ItemType ty = com.pullenti.ner.decree.internal.PartToken._getTypeByAttrName(s.getTypeName());
                        if (ty == com.pullenti.ner.decree.internal.PartToken.ItemType.UNDEFINED) 
                            continue;
                        int l = com.pullenti.ner.decree.internal.PartToken._getRank(ty);
                        if (l == 0) 
                            continue;
                        if (l > min || min == 0) {
                            min = l;
                            citKind = com.pullenti.ner.decree.internal.PartToken._getInstrKindByTyp(ty);
                        }
                    }
                }
            }
            FragToken sub = null;
            if (citKind != com.pullenti.ner.instrument.InstrumentKind.UNDEFINED && citKind != com.pullenti.ner.instrument.InstrumentKind.APPENDIX) {
                sub = new FragToken(ci.getBeginToken(), ci.getEndToken());
                ContentAnalyzeWhapper wr = new ContentAnalyzeWhapper();
                wr.analyze(sub, null, true, citKind);
                sub.kind = com.pullenti.ner.instrument.InstrumentKind.CONTENT;
            }
            else 
                sub = FragToken.createDocument(ci.getBeginToken(), ci.getEndChar(), citKind);
            if (sub == null || sub.children.size() == 0) {
            }
            else if ((sub.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && sub.children.size() > 0 && sub.children.get(0).getBeginToken() == sub.getBeginToken()) && sub.children.get(sub.children.size() - 1).getEndToken() == sub.getEndToken()) 
                com.pullenti.unisharp.Utils.addToArrayList(cit.children, sub.children);
            else 
                cit.children.add(sub);
            return 1;
        }
        int endChar = res.getEndChar();
        if (res.itok == null) 
            res.itok = InstrToken1.parse(res.getBeginToken(), true, null, 0, null, false, res.getEndChar(), false, false);
        java.util.ArrayList<LineToken> lines = LineToken.parseList(res.getBeginToken(), endChar, null);
        if (lines == null || (lines.size() < 1)) 
            return -1;
        int ret = 1;
        if (res.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
            for (int j = ind + 1; j < chi.size(); j++) {
                if (chi.get(j).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
                    java.util.ArrayList<LineToken> lines2 = LineToken.parseList(chi.get(j).getBeginToken(), chi.get(j).getEndChar(), lines.get(lines.size() - 1));
                    if (lines2 == null || (lines2.size() < 1)) 
                        break;
                    if (!lines2.get(0).isListItem) {
                        if ((lines2.size() > 1 && lines2.get(1).isListItem && lines2.get(0).getEndToken().isCharOf(":")) && !lines2.get(0).getBeginToken().chars.isCapitalUpper()) 
                            lines2.get(0).isListItem = true;
                        else 
                            break;
                    }
                    com.pullenti.unisharp.Utils.addToArrayList(lines, lines2);
                    ret = (j - ind) + 1;
                }
                else if (chi.get(j).kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS && chi.get(j).kind != com.pullenti.ner.instrument.InstrumentKind.COMMENT) 
                    break;
            }
        }
        if (lines.size() < 2) 
            return -1;
        if ((lines.size() > 1 && lines.get(0).isListItem && lines.get(1).isListItem) && lines.get(0).number != 1) {
            if (lines.size() == 2 || !lines.get(2).isListItem) 
                lines.get(0).isListItem = (lines.get(1).isListItem = false);
        }
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isListItem) {
                if (i > 0 && lines.get(i - 1).isListItem) 
                    continue;
                if (((i + 1) < lines.size()) && lines.get(i + 1).isListItem) {
                }
                else {
                    lines.get(i).isListItem = false;
                    continue;
                }
                int j;
                boolean newLine = false;
                for (j = i + 1; j < lines.size(); j++) {
                    if (!lines.get(j).isListItem) 
                        break;
                    else if (lines.get(j).isNewlineBefore()) 
                        newLine = true;
                }
                if (newLine) 
                    continue;
                if (i > 0 && lines.get(i - 1).getEndToken().isChar(':')) 
                    continue;
                for (j = i; j < lines.size(); j++) {
                    if (!lines.get(j).isListItem) 
                        break;
                    else 
                        lines.get(j).isListItem = false;
                }
            }
        }
        if (lines.size() > 2) {
            LineToken last = lines.get(lines.size() - 1);
            LineToken last2 = lines.get(lines.size() - 2);
            if ((!last.isListItem && last.getEndToken().isChar('.') && last2.isListItem) && last2.getEndToken().isChar(';')) {
                if ((last.getLengthChar() < (last2.getLengthChar() * 2)) || last.getBeginToken().chars.isAllLower()) 
                    last.isListItem = true;
            }
        }
        for (int i = 0; i < (lines.size() - 1); i++) {
            if (!lines.get(i).isListItem && !lines.get(i + 1).isListItem) {
                if (((i + 2) < lines.size()) && lines.get(i + 2).isListItem && lines.get(i + 1).getEndToken().isChar(':')) {
                }
                else {
                    lines.get(i).setEndToken(lines.get(i + 1).getEndToken());
                    lines.remove(i + 1);
                    i--;
                }
            }
        }
        for (int i = 0; i < (lines.size() - 1); i++) {
            if (lines.get(i).isListItem) {
                if (lines.get(i).number == 1) {
                    boolean ok = true;
                    int num = 1;
                    int nonum = 0;
                    for (int j = i + 1; j < lines.size(); j++) {
                        if (!lines.get(j).isListItem) {
                            ok = false;
                            break;
                        }
                        else if (lines.get(j).number > 0) {
                            num++;
                            if (lines.get(j).number != num) {
                                ok = false;
                                break;
                            }
                        }
                        else 
                            nonum++;
                    }
                    if (!ok || nonum == 0 || (num < 2)) 
                        break;
                    LineToken lt = lines.get(i);
                    for (int j = i + 1; j < lines.size(); j++) {
                        if (lines.get(j).number > 0) 
                            lt = lines.get(j);
                        else {
                            java.util.ArrayList<LineToken> chli = (java.util.ArrayList<LineToken>)com.pullenti.unisharp.Utils.cast(lt.tag, java.util.ArrayList.class);
                            if (chli == null) 
                                lt.tag = (chli = new java.util.ArrayList<LineToken>());
                            lt.setEndToken(lines.get(j).getEndToken());
                            chli.add(lines.get(j));
                            lines.remove(j);
                            j--;
                        }
                    }
                }
            }
        }
        int cou = 0;
        for (LineToken li : lines) {
            if (li.isListItem) 
                cou++;
        }
        if (cou < 2) 
            return -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isListItem) {
                int i0 = i;
                boolean ok = true;
                cou = 1;
                for (; i < lines.size(); i++,cou++) {
                    if (!lines.get(i).isListItem) 
                        break;
                    else if (lines.get(i).number != cou) 
                        ok = false;
                }
                if (!ok) {
                    for (i = i0; i < lines.size(); i++) {
                        if (!lines.get(i).isListItem) 
                            break;
                        else 
                            lines.get(i).number = 0;
                    }
                }
                if (cou > 3 && com.pullenti.unisharp.Utils.stringsNe(lines.get(i0).getBeginToken().getSourceText(), lines.get(i0 + 1).getBeginToken().getSourceText()) && com.pullenti.unisharp.Utils.stringsEq(lines.get(i0 + 1).getBeginToken().getSourceText(), lines.get(i0 + 2).getBeginToken().getSourceText())) {
                    String pref = lines.get(i0 + 1).getBeginToken().getSourceText();
                    ok = true;
                    for (int j = i0 + 2; j < i; j++) {
                        if (com.pullenti.unisharp.Utils.stringsNe(pref, lines.get(j).getBeginToken().getSourceText())) {
                            ok = false;
                            break;
                        }
                    }
                    if (!ok) 
                        continue;
                    com.pullenti.ner.Token tt = null;
                    ok = false;
                    for (tt = lines.get(i0).getEndToken().getPrevious(); tt != null && tt != lines.get(i0).getBeginToken(); tt = tt.getPrevious()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(tt.getSourceText(), pref)) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) {
                        LineToken li0 = new LineToken(lines.get(i0).getBeginToken(), tt.getPrevious());
                        lines.get(i0).setBeginToken(tt);
                        lines.add(i0, li0);
                        i++;
                    }
                }
            }
        }
        for (LineToken li : lines) {
            li.correctBeginToken();
            FragToken ch = FragToken._new1511(li.getBeginToken(), li.getEndToken(), (li.isListItem ? com.pullenti.ner.instrument.InstrumentKind.LISTITEM : com.pullenti.ner.instrument.InstrumentKind.CONTENT), li.number);
            if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT && ch.getEndToken().isChar(':')) 
                ch.kind = com.pullenti.ner.instrument.InstrumentKind.LISTHEAD;
            res.children.add(ch);
            java.util.ArrayList<LineToken> chli = (java.util.ArrayList<LineToken>)com.pullenti.unisharp.Utils.cast(li.tag, java.util.ArrayList.class);
            if (chli != null) {
                for (LineToken lt : chli) {
                    ch.children.add(FragToken._new1494(lt.getBeginToken(), lt.getEndToken(), com.pullenti.ner.instrument.InstrumentKind.LISTITEM));
                }
                if (ch.getBeginChar() < ch.children.get(0).getBeginChar()) 
                    ch.children.add(0, FragToken._new1494(ch.getBeginToken(), ch.children.get(0).getBeginToken().getPrevious(), com.pullenti.ner.instrument.InstrumentKind.CONTENT));
            }
        }
        return ret;
    }

    public static class LineToken extends com.pullenti.ner.MetaToken {
    
        public LineToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
            super(b, e, null);
        }
    
        public boolean isListItem;
    
        public boolean isListHead;
    
        public int number;
    
        public void correctBeginToken() {
            if (!isListItem) 
                return;
            if (getBeginToken().isHiphen() && getBeginToken().getNext() != null) 
                setBeginToken(this.getBeginToken().getNext());
            else if ((number > 0 && getBeginToken().getNext() != null && getBeginToken().getNext().isChar(')')) && getBeginToken().getNext().getNext() != null) 
                setBeginToken(this.getBeginToken().getNext().getNext());
        }
    
        @Override
        public String toString() {
            return ((isListItem ? "LISTITEM" : "TEXT") + ": " + this.getSourceText());
        }
    
        public static LineToken parse(com.pullenti.ner.Token t, int maxChar, LineToken prev) {
            if (t == null || t.getEndChar() > maxChar) 
                return null;
            LineToken res = new LineToken(t, t);
            for (; t != null && t.getEndChar() <= maxChar; t = t.getNext()) {
                if (t.isChar(':')) {
                    if (res.isNewlineBefore() && res.getBeginToken().isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) 
                        res.isListHead = true;
                    res.setEndToken(t);
                    break;
                }
                if (t.isChar(';')) {
                    if (!t.isWhitespaceAfter()) {
                    }
                    if (t.getPrevious() != null && (t.getPrevious().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                        if (!t.isWhitespaceAfter()) 
                            continue;
                        if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                            continue;
                    }
                    res.isListItem = true;
                    res.setEndToken(t);
                    break;
                }
                if (t.isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        res.setEndToken((t = br.getEndToken()));
                        continue;
                    }
                }
                if (t.isNewlineBefore() && t != res.getBeginToken()) {
                    boolean _next = true;
                    if (t.getPrevious().isComma() || t.getPrevious().isAnd() || t.isCharOf("(")) 
                        _next = false;
                    else if (t.chars.isLetter() || (t instanceof com.pullenti.ner.NumberToken)) {
                        if (t.chars.isAllLower()) 
                            _next = false;
                        else if (t.getPrevious().chars.isLetter()) 
                            _next = false;
                    }
                    if (_next) 
                        break;
                }
                res.setEndToken(t);
            }
            if (res.getBeginToken().isHiphen()) 
                res.isListItem = res.getBeginToken().getNext() != null && !res.getBeginToken().getNext().isHiphen();
            else if (res.getBeginToken().isCharOf("·")) {
                res.isListItem = true;
                res.setBeginToken(res.getBeginToken().getNext());
            }
            else if (res.getBeginToken().getNext() != null && ((res.getBeginToken().getNext().isChar(')') || ((prev != null && ((prev.isListItem || prev.isListHead))))))) {
                if (res.getBeginToken().getLengthChar() == 1 || (res.getBeginToken() instanceof com.pullenti.ner.NumberToken)) {
                    res.isListItem = true;
                    if ((res.getBeginToken() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) 
                        res.number = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue();
                    else if ((res.getBeginToken() instanceof com.pullenti.ner.TextToken) && res.getBeginToken().getLengthChar() == 1) {
                        String te = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.TextToken.class)).term;
                        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(te.charAt(0))) 
                            res.number = ((int)te.charAt(0)) - ((int)'А');
                        else if (com.pullenti.morph.LanguageHelper.isLatinChar(te.charAt(0))) 
                            res.number = ((int)te.charAt(0)) - ((int)'A');
                    }
                }
            }
            return res;
        }
    
        public static java.util.ArrayList<LineToken> parseList(com.pullenti.ner.Token t, int maxChar, LineToken prev) {
            LineToken lt = parse(t, maxChar, prev);
            if (lt == null) 
                return null;
            java.util.ArrayList<LineToken> res = new java.util.ArrayList<LineToken>();
            res.add(lt);
            String ss = lt.toString();
            for (t = lt.getEndToken().getNext(); t != null; t = t.getNext()) {
                LineToken lt0 = parse(t, maxChar, lt);
                if (lt0 == null) 
                    break;
                res.add((lt = lt0));
                t = lt0.getEndToken();
            }
            if ((res.size() < 2) && !res.get(0).isListItem) {
                if ((prev != null && prev.isListItem && res.get(0).getEndToken().isChar('.')) && !res.get(0).getBeginToken().chars.isCapitalUpper()) {
                    res.get(0).isListItem = true;
                    return res;
                }
                return null;
            }
            int i;
            for (i = 0; i < res.size(); i++) {
                if (res.get(i).isListItem) 
                    break;
            }
            if (i >= res.size()) 
                return null;
            int j;
            int cou = 0;
            for (j = i; j < res.size(); j++) {
                if (!res.get(j).isListItem) {
                    if (res.get(j - 1).isListItem && res.get(j).getEndToken().isChar('.')) {
                        if (com.pullenti.unisharp.Utils.stringsEq(res.get(j).getBeginToken().getSourceText(), res.get(i).getBeginToken().getSourceText()) || res.get(j).getBeginToken().chars.isAllLower()) {
                            res.get(j).isListItem = true;
                            j++;
                            cou++;
                        }
                    }
                }
                else 
                    cou++;
            }
            return res;
        }
        public LineToken() {
            super();
        }
    }


    public static void correctAppList(java.util.ArrayList<InstrToken1> lines) {
        for (int i = 0; i < (lines.size() - 1); i++) {
            if ((lines.get(i).typ == InstrToken1.Types.LINE && lines.get(i).numbers.size() == 0 && lines.get(i).getBeginToken().isValue("ПРИЛОЖЕНИЯ", "ДОДАТОК")) && lines.get(i + 1).numbers.size() > 0 && lines.get(i).getEndToken().isChar(':')) {
                int num = 1;
                for (++i; i < lines.size(); i++) {
                    if (lines.get(i).numbers.size() == 0) {{
                                if (((i + 1) < lines.size()) && lines.get(i + 1).numbers.size() == 1 && com.pullenti.unisharp.Utils.stringsEq(lines.get(i + 1).numbers.get(0), ((Integer)num).toString())) {
                                    lines.get(i - 1).setEndToken(lines.get(i).getEndToken());
                                    lines.remove(i);
                                    i--;
                                    continue;
                                }
                            }
                        break;
                    }
                    else {
                        int nn;
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapnn1707 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        boolean inoutres1708 = com.pullenti.unisharp.Utils.parseInteger(lines.get(i).numbers.get(0), 0, null, wrapnn1707);
                        nn = (wrapnn1707.value != null ? wrapnn1707.value : 0);
                        if (inoutres1708) 
                            num = nn + 1;
                        lines.get(i).numTyp = NumberTypes.UNDEFINED;
                        lines.get(i).numbers.clear();
                    }
                }
            }
        }
    }

    public static void correctIndex(java.util.ArrayList<InstrToken1> lines) {
        if (lines.size() < 10) 
            return;
        if (lines.get(0).typ == InstrToken1.Types.CLAUSE || lines.get(0).typ == InstrToken1.Types.CHAPTER) {
        }
        else 
            return;
        java.util.ArrayList<InstrToken1> index = new java.util.ArrayList<InstrToken1>();
        index.add(lines.get(0));
        java.util.ArrayList<InstrToken1> content = new java.util.ArrayList<InstrToken1>();
        int i;
        int indText = 0;
        int conText = 0;
        for (i = 1; i < lines.size(); i++) {
            if (lines.get(i).typ == lines.get(0).typ) {
                if (_canBeEquals(lines.get(i), lines.get(0))) 
                    break;
                else 
                    index.add(lines.get(i));
            }
            else 
                indText += lines.get(i).getLengthChar();
        }
        int cInd = i;
        for (; i < lines.size(); i++) {
            if (lines.get(i).typ == lines.get(0).typ) 
                content.add(lines.get(i));
            else 
                conText += lines.get(i).getLengthChar();
        }
        if (index.size() == content.size() && index.size() > 2) {
            if ((indText * 10) < conText) {
                com.pullenti.unisharp.Utils.putArrayValue(lines, 0, InstrToken1._new1709(lines.get(0).getBeginToken(), lines.get(cInd - 1).getEndToken(), true, InstrToken1.Types.INDEX));
                for(int jjj = 1 + cInd - 1 - 1, mmm = 1; jjj >= mmm; jjj--) lines.remove(jjj);
            }
        }
    }

    private static boolean _canBeEquals(InstrToken1 i1, InstrToken1 i2) {
        if (i1.typ != i2.typ) 
            return false;
        if (i1.numbers.size() > 0 && i2.numbers.size() > 0) {
            if (i1.numbers.size() != i2.numbers.size()) 
                return false;
            for (int i = 0; i < i1.numbers.size(); i++) {
                if (com.pullenti.unisharp.Utils.stringsNe(i1.numbers.get(i), i2.numbers.get(i))) 
                    return false;
            }
        }
        if (!com.pullenti.ner.core.MiscHelper.canBeEqualsEx(i1.value, i2.value, com.pullenti.ner.core.CanBeEqualsAttr.of((com.pullenti.ner.core.CanBeEqualsAttr.IGNORENONLETTERS.value()) | (com.pullenti.ner.core.CanBeEqualsAttr.IGNOREUPPERCASE.value())))) 
            return false;
        return true;
    }
    public ListHelper() {
    }
    public static ListHelper _globalInstance;
    
    static {
        try { _globalInstance = new ListHelper(); } 
        catch(Exception e) { }
    }
}
