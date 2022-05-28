/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage.internal;

public class Line extends com.pullenti.ner.MetaToken {

    private Line(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public int getCharsCount() {
        int cou = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null; t = t.getNext()) {
            cou += t.getLengthChar();
            if (t == getEndToken()) 
                break;
        }
        return cou;
    }


    public boolean isPureEn() {
        int en = 0;
        int ru = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (en > 0 && ru == 0) 
            return true;
        return false;
    }


    public boolean isPureRu() {
        int en = 0;
        int ru = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (ru > 0 && en == 0) 
            return true;
        return false;
    }


    public static java.util.ArrayList<Line> parse(com.pullenti.ner.Token t0, int maxLines, int maxChars, int maxEndChar) {
        java.util.ArrayList<Line> res = new java.util.ArrayList<Line>();
        int totalChars = 0;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (maxEndChar > 0) {
                if (t.getBeginChar() > maxEndChar) 
                    break;
            }
            if (isStartOfIndex(t)) 
                break;
            com.pullenti.ner.Token t1;
            for (t1 = t; t1 != null && t1.getNext() != null; t1 = t1.getNext()) {
                if (t1.isNewlineAfter()) {
                    if (t1.getNext() == null || com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t1.getNext())) 
                        break;
                }
                if (t1 == t && t.isNewlineBefore() && (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
                    if (t1.getNext() == null) 
                        continue;
                    if ((t1.getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().chars.isLetter() && !t1.getNext().chars.isAllLower()) 
                        break;
                }
            }
            if (t1 == null) 
                t1 = t;
            TitleItemToken tit = TitleItemToken.tryAttach(t);
            if (tit != null) {
                if (tit.typ == TitleItemToken.Types.KEYWORDS) 
                    break;
            }
            com.pullenti.ner.core.internal.BlockTitleToken bl = com.pullenti.ner.core.internal.BlockTitleToken.tryAttach(t, false, null);
            if (bl != null) {
                if (bl.typ != com.pullenti.ner.core.internal.BlkTyps.UNDEFINED) 
                    break;
            }
            Line l = new Line(t, t1);
            res.add(l);
            totalChars += l.getCharsCount();
            if (res.size() >= maxLines || totalChars >= maxChars) 
                break;
            t = t1;
        }
        return res;
    }

    private static boolean isStartOfIndex(com.pullenti.ner.Token t) {
        if (t == null || !t.isNewlineBefore()) 
            return false;
        com.pullenti.ner.Token t1 = null;
        if (t.isValue("СОДЕРЖИМОЕ", "ВМІСТ") || t.isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || t.isValue("ОГЛАВЛЕНИЕ", "ЗМІСТ")) 
            t1 = t;
        else if (t.isValue("СПИСОК", null) && t.getNext() != null && t.getNext().isValue("РАЗДЕЛ", null)) 
            t1 = t1.getNext();
        if (t1 == null) 
            return false;
        if (!t1.isNewlineAfter()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getMorph().getCase().isGenitive()) 
                t1 = npt.getEndToken().getNext();
        }
        if (t1 != null && t1.isCharOf(":.;")) 
            t1 = t1.getNext();
        if (t1 == null || !t1.isNewlineBefore()) 
            return false;
        return true;
    }
    public Line() {
        super();
    }
}
