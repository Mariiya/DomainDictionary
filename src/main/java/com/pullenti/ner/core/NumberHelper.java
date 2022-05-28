/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Работа с числовыми значениями
 * 
 * Хелпер числовых представлений
 */
public class NumberHelper {

    /**
     * Попробовать создать числительное (без знака, целочисленное). 
     * Внимание! Этот метод всегда вызывается процессором при формировании цепочки токенов в методе Process(), 
     * так что все NumberToken уже созданы в основной цепочке, сфорированной для текста.
     * @param token начальный токен
     * @return число-метатокен
     */
    public static com.pullenti.ner.NumberToken tryParseNumber(com.pullenti.ner.Token token) {
        return _TryParse(token, null);
    }

    private static com.pullenti.ner.NumberToken _TryParse(com.pullenti.ner.Token token, com.pullenti.ner.NumberToken prevVal) {
        if (token instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        com.pullenti.ner.TextToken et = tt;
        String val = null;
        com.pullenti.ner.NumberSpellingType typ = com.pullenti.ner.NumberSpellingType.DIGIT;
        String term = tt.term;
        int i;
        int j;
        if (Character.isDigit(term.charAt(0))) 
            val = term;
        if (val != null) {
            boolean hiph = false;
            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isHiphen()) {
                if ((et.getWhitespacesAfterCount() < 2) && (et.getNext().getWhitespacesAfterCount() < 2)) {
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    hiph = true;
                }
            }
            com.pullenti.ner.MorphCollection mc = null;
            if (hiph || !et.isWhitespaceAfter()) {
                com.pullenti.ner.MetaToken rr = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), val);
                if (rr == null) 
                    et = tt;
                else {
                    mc = rr.getMorph();
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(rr.getEndToken(), com.pullenti.ner.TextToken.class);
                }
            }
            else 
                et = tt;
            if (et.getNext() != null && et.getNext().isChar('(')) {
                com.pullenti.ner.NumberToken num2 = tryParseNumber(et.getNext().getNext());
                if ((num2 != null && com.pullenti.unisharp.Utils.stringsEq(num2.getValue(), val) && num2.getEndToken().getNext() != null) && num2.getEndToken().getNext().isChar(')')) 
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(num2.getEndToken().getNext(), com.pullenti.ner.TextToken.class);
            }
            while ((et.getNext() instanceof com.pullenti.ner.TextToken) && !(et.getPrevious() instanceof com.pullenti.ner.NumberToken) && et.isWhitespaceBefore()) {
                if (et.getWhitespacesAfterCount() != 1) 
                    break;
                String sss = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(sss, "000")) {
                    val = val + "000";
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    continue;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(sss, "ООО") || com.pullenti.unisharp.Utils.stringsEq(sss, "OOO")) {
                    NumberExToken sub = tryParsePostfixOnly(et.getNext().getNext());
                    if (sub != null) {
                        val = val + "000";
                        et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        continue;
                    }
                }
                if (Character.isDigit(sss.charAt(0)) && sss.length() == 3) {
                    String val2 = val;
                    for (com.pullenti.ner.Token ttt = et.getNext(); ttt != null; ttt = ttt.getNext()) {
                        String ss = ttt.getSourceText();
                        if (ttt.getWhitespacesBeforeCount() == 1 && ttt.getLengthChar() == 3 && Character.isDigit(ss.charAt(0))) {
                            int ii;
                            com.pullenti.unisharp.Outargwrapper<Integer> wrapii517 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                            boolean inoutres518 = com.pullenti.unisharp.Utils.parseInteger(ss, 0, null, wrapii517);
                            ii = (wrapii517.value != null ? wrapii517.value : 0);
                            if (!inoutres518) 
                                break;
                            val2 += ss;
                            continue;
                        }
                        if ((ttt.isCharOf(".,") && !ttt.isWhitespaceBefore() && !ttt.isWhitespaceAfter()) && ttt.getNext() != null && Character.isDigit(ttt.getNext().getSourceText().charAt(0))) {
                            if (ttt.getNext().isWhitespaceAfter() && (ttt.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt.getPrevious(), com.pullenti.ner.TextToken.class);
                                val = val2;
                                break;
                            }
                        }
                        if (((((((ttt.isValue("ТРИЛЛИОН", "ТРИЛЬЙОН") || ttt.isValue("TRILLION", null) || ttt.isValue("ТРЛН", null)) || ttt.isValue("МИЛЛИАРД", "МІЛЬЯРД") || ttt.isValue("BILLION", null)) || ttt.isValue("BN", null) || ttt.isValue("МЛРД", null)) || ttt.isValue("МИЛЛИОН", "МІЛЬЙОН") || ttt.isValue("MILLION", null)) || ttt.isValue("МЛН", null) || ttt.isValue("ТЫСЯЧА", "ТИСЯЧА")) || ttt.isValue("THOUSAND", null) || ttt.isValue("ТЫС", null)) || ttt.isValue("ТИС", null)) {
                            et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt.getPrevious(), com.pullenti.ner.TextToken.class);
                            val = val2;
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            for (int k = 0; k < 4; k++) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().chars.isLetter()) {
                    tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    com.pullenti.ner.Token t0 = et;
                    String coef = null;
                    if (k == 0) {
                        coef = "000000000000";
                        if (tt.isValue("ТРИЛЛИОН", "ТРИЛЬЙОН") || tt.isValue("TRILLION", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("ТРЛН", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            continue;
                    }
                    else if (k == 1) {
                        coef = "000000000";
                        if (tt.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt.isValue("BILLION", null) || tt.isValue("BN", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("МЛРД", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            continue;
                    }
                    else if (k == 2) {
                        coef = "000000";
                        if (tt.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt.isValue("MILLION", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("МЛН", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "M")) {
                            if (NumberHelper.isMoneyChar(et.getPrevious()) != null) {
                                et = tt;
                                val += coef;
                            }
                            else 
                                break;
                        }
                        else 
                            continue;
                    }
                    else {
                        coef = "000";
                        if (tt.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt.isValue("THOUSAND", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("ТЫС", null) || tt.isValue("ТИС", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            break;
                    }
                    if (((t0 == token && t0.getLengthChar() <= 3 && t0.getPrevious() != null) && !t0.isWhitespaceBefore() && t0.getPrevious().isCharOf(",.")) && !t0.getPrevious().isWhitespaceBefore() && (((t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) || prevVal != null))) {
                        if (t0.getLengthChar() == 1) 
                            val = val.substring(0, 0 + val.length() - 1);
                        else if (t0.getLengthChar() == 2) 
                            val = val.substring(0, 0 + val.length() - 2);
                        else 
                            val = val.substring(0, 0 + val.length() - 3);
                        String hi = (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken ? ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class)).getValue() : prevVal.getValue());
                        int cou = coef.length() - val.length();
                        for (; cou > 0; cou--) {
                            hi = hi + "0";
                        }
                        val = hi + val;
                        token = t0.getPrevious().getPrevious();
                    }
                    com.pullenti.ner.NumberToken next = _TryParse(et.getNext(), null);
                    if (next == null || next.getValue().length() > coef.length()) 
                        break;
                    com.pullenti.ner.Token tt1 = next.getEndToken();
                    if (((tt1.getNext() instanceof com.pullenti.ner.TextToken) && !tt1.isWhitespaceAfter() && tt1.getNext().isCharOf(".,")) && !tt1.getNext().isWhitespaceAfter()) {
                        com.pullenti.ner.NumberToken re1 = _TryParse(tt1.getNext().getNext(), next);
                        if (re1 != null && re1.getBeginToken() == next.getBeginToken()) 
                            next = re1;
                    }
                    if (val.length() > next.getValue().length()) 
                        val = val.substring(0, 0 + val.length() - next.getValue().length());
                    val += next.getValue();
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(next.getEndToken(), com.pullenti.ner.TextToken.class);
                    break;
                }
            }
            com.pullenti.ner.NumberToken res = com.pullenti.ner.NumberToken._new519(token, et, val, typ, mc);
            if (et.getNext() != null && (res.getValue().length() < 4) && ((et.getNext().isHiphen() || et.getNext().isValue("ДО", null)))) {
                for (com.pullenti.ner.Token tt1 = et.getNext().getNext(); tt1 != null; tt1 = tt1.getNext()) {
                    if (!(tt1 instanceof com.pullenti.ner.TextToken)) 
                        break;
                    if (Character.isDigit(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term.charAt(0))) 
                        continue;
                    if (tt1.isCharOf(",.") || NumberHelper.isMoneyChar(tt1) != null) 
                        continue;
                    if (tt1.isValue("ТРИЛЛИОН", "ТРИЛЬЙОН") || tt1.isValue("ТРЛН", null) || tt1.isValue("TRILLION", null)) 
                        res.setValue(res.getValue() + "000000000000");
                    else if (tt1.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt1.isValue("МЛН", null) || tt1.isValue("MILLION", null)) 
                        res.setValue(res.getValue() + "000000");
                    else if ((tt1.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt1.isValue("МЛРД", null) || tt1.isValue("BILLION", null)) || tt1.isValue("BN", null)) 
                        res.setValue(res.getValue() + "000000000");
                    else if (tt1.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt1.isValue("ТЫС", "ТИС") || tt1.isValue("THOUSAND", null)) 
                        res.setValue(res.getValue() + "1000");
                    break;
                }
            }
            return res;
        }
        int intVal = 0;
        et = null;
        int locValue = 0;
        boolean isAdj = false;
        int jPrev = -1;
        for (com.pullenti.ner.TextToken t = tt; t != null; t = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)) {
            if (t != tt && t.getNewlinesBeforeCount() > 1) 
                break;
            term = t.term;
            if (!Character.isLetter(term.charAt(0))) 
                break;
            TerminToken num = m_Nums.tryParse(t, TerminParseAttr.FULLWORDSONLY);
            if (num == null) 
                break;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapj520 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres521 = com.pullenti.unisharp.Utils.parseInteger(num.termin.tag.toString(), 0, null, wrapj520);
            j = (wrapj520.value != null ? wrapj520.value : 0);
            if (!inoutres521) 
                break;
            if (jPrev > 0 && (jPrev < 20) && (j < 20)) 
                break;
            if ((intVal + locValue) > 0 && ((t.isValue("СТО", null) || t.isValue("ДЕСЯТЬ", null))) && t.getNext() != null) {
                if (t.getNext().isValue("ТЫСЯЧНЫЙ", "ТИСЯЧНИЙ") || t.getNext().isValue("МИЛЛИОННЫЙ", "МІЛЬЙОННИЙ") || t.getNext().isValue("МИЛЛИАРДНЫЙ", "МІЛЬЯРДНИЙ")) 
                    break;
            }
            isAdj = ((j & prilNumTagBit)) != 0;
            j &= (~prilNumTagBit);
            if (isAdj && t != tt) {
                if (((t.isValue("ДЕСЯТЫЙ", "ДЕСЯТИЙ") || t.isValue("СОТЫЙ", "СОТИЙ") || t.isValue("ТЫСЯЧНЫЙ", "ТИСЯЧНИЙ")) || t.isValue("ДЕСЯТИТЫСЯЧНЫЙ", "ДЕСЯТИТИСЯЧНИЙ") || t.isValue("МИЛЛИОННЫЙ", "МІЛЬЙОННИЙ")) || t.isValue("МИЛЛИАРДНЫЙ", "МІЛЬЯРДНИЙ")) 
                    break;
                if ((j < 9) && (jPrev < 20)) 
                    break;
            }
            if (j >= 1000) {
                if (locValue == 0) 
                    locValue = 1;
                intVal += (locValue * j);
                locValue = 0;
            }
            else {
                if (locValue > 0 && locValue <= j) 
                    break;
                locValue += j;
            }
            et = t;
            if (j == 1000 || j == 1000000) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                    t = (et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class));
            }
            jPrev = j;
        }
        if (locValue > 0) 
            intVal += locValue;
        if (intVal == 0 || et == null) 
            return null;
        com.pullenti.ner.NumberToken nt = new com.pullenti.ner.NumberToken(tt, et, ((Integer)intVal).toString(), com.pullenti.ner.NumberSpellingType.WORDS, null);
        if (et.getMorph() != null) {
            nt.setMorph(new com.pullenti.ner.MorphCollection(et.getMorph()));
            for (com.pullenti.morph.MorphBaseInfo wff : et.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                if (wf != null && wf.misc != null && wf.misc.getAttrs().contains("собир.")) {
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
                    break;
                }
            }
            if (!isAdj) {
                nt.getMorph().removeItems(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.NOUN), false);
                if (nt.getMorph()._getClass().isUndefined()) 
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
            }
            if (et.chars.isLatinLetter() && isAdj) 
                nt.getMorph()._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
        }
        return nt;
    }

    /**
     * Попробовать выделить число в римской записи
     * @param t начальный токен
     * @return числовой метатокен или null
     * 
     */
    public static com.pullenti.ner.NumberToken tryParseRoman(com.pullenti.ner.Token t) {
        if (t instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null || !t.chars.isLetter()) 
            return null;
        String term = tt.term;
        if (!_isRomVal(term)) 
            return null;
        if (tt.getMorph()._getClass().isPreposition()) {
            if (tt.chars.isAllLower()) 
                return null;
        }
        com.pullenti.ner.NumberToken res = new com.pullenti.ner.NumberToken(t, t, "", com.pullenti.ner.NumberSpellingType.ROMAN, null);
        java.util.ArrayList<Integer> nums = new java.util.ArrayList<Integer>();
        int val = 0;
        for (; t != null; t = t.getNext()) {
            if (t != res.getBeginToken() && t.isWhitespaceBefore()) 
                break;
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
            if (!_isRomVal(term)) 
                break;
            for (char s : term.toCharArray()) {
                int i = _romVal(s);
                if (i > 0) 
                    nums.add(i);
            }
            res.setEndToken(t);
        }
        if (nums.size() == 0) 
            return null;
        for (int i = 0; i < nums.size(); i++) {
            if ((i + 1) < nums.size()) {
                if (nums.get(i) == 1 && nums.get(i + 1) == 5) {
                    val += 4;
                    i++;
                }
                else if (nums.get(i) == 1 && nums.get(i + 1) == 10) {
                    val += 9;
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 50) {
                    val += 40;
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 100) {
                    val += 90;
                    i++;
                }
                else 
                    val += nums.get(i);
            }
            else 
                val += nums.get(i);
        }
        res.setIntValue(val);
        boolean hiph = false;
        com.pullenti.ner.Token et = res.getEndToken().getNext();
        if (et == null) 
            return res;
        if (et.getNext() != null && et.getNext().isHiphen()) {
            et = et.getNext();
            hiph = true;
        }
        if (hiph || !et.isWhitespaceAfter()) {
            com.pullenti.ner.MetaToken mc = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), res.getValue());
            if (mc != null) {
                res.setEndToken(mc.getEndToken());
                res.setMorph(mc.getMorph());
            }
        }
        if ((res.getBeginToken() == res.getEndToken() && val == 1 && res.getBeginToken().chars.isAllLower()) && res.getBeginToken().getMorph().getLanguage().isUa()) 
            return null;
        return res;
    }

    private static int _romVal(char ch) {
        if (ch == 'Х' || ch == 'X') 
            return 10;
        if (ch == 'І' || ch == 'I') 
            return 1;
        if (ch == 'V') 
            return 5;
        if (ch == 'L') 
            return 50;
        if (ch == 'C' || ch == 'С') 
            return 100;
        return 0;
    }

    private static boolean _isRomVal(String str) {
        for (char ch : str.toCharArray()) {
            if (_romVal(ch) < 1) 
                return false;
        }
        return true;
    }

    /**
     * Выделить число в римской записи в обратном порядке
     * @param token токен на предполагаемой римской цифрой
     * @return число-метатокен или null
     */
    public static com.pullenti.ner.NumberToken tryParseRomanBack(com.pullenti.ner.Token token) {
        com.pullenti.ner.Token t = token;
        if (t == null) 
            return null;
        if ((t.chars.isAllLower() && t.getPrevious() != null && t.getPrevious().isHiphen()) && t.getPrevious().getPrevious() != null) 
            t = token.getPrevious().getPrevious();
        com.pullenti.ner.NumberToken res = null;
        for (; t != null; t = t.getPrevious()) {
            com.pullenti.ner.NumberToken nt = tryParseRoman(t);
            if (nt != null) {
                if (nt.getEndToken() == token) 
                    res = nt;
                else 
                    break;
            }
            if (t.isWhitespaceAfter()) 
                break;
        }
        return res;
    }

    /**
     * Это выделение числительных типа 16-летие, 50-летний
     * @param t начальный токен
     * @return числовой метатокен или null
     */
    public static com.pullenti.ner.NumberToken tryParseAge(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token ntNext = null;
        if (nt != null) 
            ntNext = nt.getNext();
        else {
            if (t.isValue("AGED", null) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                return new com.pullenti.ner.NumberToken(t, t.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            if ((((nt = tryParseRoman(t)))) != null) 
                ntNext = nt.getEndToken().getNext();
        }
        if (nt != null) {
            if (ntNext != null) {
                com.pullenti.ner.Token t1 = ntNext;
                if (t1.isHiphen()) 
                    t1 = t1.getNext();
                if (t1 instanceof com.pullenti.ner.TextToken) {
                    String v = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term;
                    if ((com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЕ")) || com.pullenti.unisharp.Utils.stringsEq(v, "РІЧЧЯ")) 
                        return com.pullenti.ner.NumberToken._new519(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (t1.isValue("ЛЕТНИЙ", "РІЧНИЙ")) 
                        return com.pullenti.ner.NumberToken._new519(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (com.pullenti.unisharp.Utils.stringsEq(v, "Л") || ((com.pullenti.unisharp.Utils.stringsEq(v, "Р") && nt.getMorph().getLanguage().isUa()))) 
                        return new com.pullenti.ner.NumberToken(t, (t1.getNext() != null && t1.getNext().isChar('.') ? t1.getNext() : t1), nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
                }
            }
            return null;
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        String s = tt.term;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ЛЕТИЕ", "ЛЕТИЯ", "РІЧЧЯ", null)) {
            Termin term = m_Nums.find(s.substring(0, 0 + s.length() - 5));
            if (term != null) 
                return com.pullenti.ner.NumberToken._new519(tt, tt, term.tag.toString(), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
        }
        s = tt.lemma;
        int i = s.indexOf("ЛЕТН");
        if (i < 0) 
            i = s.indexOf("РІЧН");
        if (i > 3) {
            String ss = s.substring(0, 0 + i);
            Termin term = m_Nums.find(ss);
            if (term != null) 
                return com.pullenti.ner.NumberToken._new519(tt, tt, term.tag.toString(), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
            for (i = ss.length() - 2; i > 2; i--) {
                String ss1 = ss.substring(0, 0 + i);
                Termin term1 = m_Nums.find(ss1);
                if (term1 == null) 
                    continue;
                String ss2 = ss.substring(i);
                Termin term2 = m_Nums.find(ss2);
                if (term2 == null) 
                    continue;
                String num0 = term1.tag.toString();
                String num1 = term2.tag.toString();
                if (num0.length() > 1 && num0.charAt(num0.length() - 1) == '0' && num1.length() == 1) 
                    return com.pullenti.ner.NumberToken._new519(tt, tt, num0.substring(0, 0 + num0.length() - 1) + num1, com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
            }
        }
        return null;
    }

    /**
     * Выделение годовщин и летий (XX-летие) ...
     * @param t начальный токен
     * @return числовой метатокен или null
     */
    public static com.pullenti.ner.NumberToken tryParseAnniversary(com.pullenti.ner.Token t) {
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token t1 = null;
        if (nt != null) 
            t1 = nt.getNext();
        else {
            if ((((nt = tryParseRoman(t)))) == null) {
                if (t instanceof com.pullenti.ner.TextToken) {
                    String v = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    int num = 0;
                    if (v.endsWith("ЛЕТИЯ") || v.endsWith("ЛЕТИЕ")) {
                        if (v.startsWith("ВОСЕМЬСОТ") || v.startsWith("ВОСЬМИСОТ")) 
                            num = 800;
                    }
                    if (num > 0) 
                        return new com.pullenti.ner.NumberToken(t, t, ((Integer)num).toString(), com.pullenti.ner.NumberSpellingType.AGE, null);
                }
                return null;
            }
            t1 = nt.getEndToken().getNext();
        }
        if (t1 == null) 
            return null;
        if (t1.isHiphen()) 
            t1 = t1.getNext();
        if (t1 instanceof com.pullenti.ner.TextToken) {
            String v = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term;
            if ((com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЕ")) || t1.isValue("ГОДОВЩИНА", null)) 
                return new com.pullenti.ner.NumberToken(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            if (t1.getMorph().getLanguage().isUa()) {
                if (com.pullenti.unisharp.Utils.stringsEq(v, "РОКІВ") || com.pullenti.unisharp.Utils.stringsEq(v, "РІЧЧЯ") || t1.isValue("РІЧНИЦЯ", null)) 
                    return new com.pullenti.ner.NumberToken(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            }
        }
        return null;
    }

    private static String[] m_Samples;

    private static com.pullenti.ner.MetaToken analizeNumberTail(com.pullenti.ner.TextToken tt, String val) {
        if (!(tt instanceof com.pullenti.ner.TextToken)) 
            return null;
        String s = tt.term;
        com.pullenti.ner.MorphCollection mc = null;
        if (!tt.chars.isLetter()) {
            if (((com.pullenti.unisharp.Utils.stringsEq(s, "<") || com.pullenti.unisharp.Utils.stringsEq(s, "("))) && (tt.getNext() instanceof com.pullenti.ner.TextToken)) {
                s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class)).term;
                if ((com.pullenti.unisharp.Utils.stringsEq(s, "TH") || com.pullenti.unisharp.Utils.stringsEq(s, "ST") || com.pullenti.unisharp.Utils.stringsEq(s, "RD")) || com.pullenti.unisharp.Utils.stringsEq(s, "ND")) {
                    if (tt.getNext().getNext() != null && tt.getNext().getNext().isCharOf(">)")) {
                        mc = new com.pullenti.ner.MorphCollection(null);
                        mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
                        mc.setLanguage(com.pullenti.morph.MorphLang.EN);
                        return com.pullenti.ner.MetaToken._new527(tt, tt.getNext().getNext(), mc);
                    }
                }
            }
            return null;
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(s, "TH") || com.pullenti.unisharp.Utils.stringsEq(s, "ST") || com.pullenti.unisharp.Utils.stringsEq(s, "RD")) || com.pullenti.unisharp.Utils.stringsEq(s, "ND")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
            mc.setLanguage(com.pullenti.morph.MorphLang.EN);
            return com.pullenti.ner.MetaToken._new527(tt, tt, mc);
        }
        if (!tt.chars.isCyrillicLetter()) 
            return null;
        if (!tt.isWhitespaceAfter()) {
            if (tt.getNext() != null && tt.getNext().chars.isLetter()) 
                return null;
            if (tt.getLengthChar() == 1 && ((tt.isValue("X", null) || tt.isValue("Х", null)))) 
                return null;
        }
        if (!tt.chars.isAllLower()) {
            String ss = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
            if (com.pullenti.unisharp.Utils.stringsEq(ss, "Я") || com.pullenti.unisharp.Utils.stringsEq(ss, "Й") || com.pullenti.unisharp.Utils.stringsEq(ss, "Е")) {
            }
            else if (ss.length() == 2 && ((ss.charAt(1) == 'Я' || ss.charAt(1) == 'Й' || ss.charAt(1) == 'Е'))) {
            }
            else 
                return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, "М")) {
            if (tt.getPrevious() == null || !tt.getPrevious().isHiphen()) 
                return null;
        }
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(val)) 
            return null;
        int dig = (((int)val.charAt(val.length() - 1)) - ((int)'0'));
        if ((dig < 0) || dig >= 10) 
            return null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> vars = com.pullenti.morph.MorphologyService.getAllWordforms(m_Samples[dig], null);
        if (vars == null || vars.size() == 0) 
            return null;
        for (com.pullenti.morph.MorphWordForm v : vars) {
            if (v._getClass().isAdjective() && com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, s) && v.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (mc == null) 
                    mc = new com.pullenti.ner.MorphCollection(null);
                boolean ok = false;
                for (com.pullenti.morph.MorphBaseInfo it : mc.getItems()) {
                    if (it._getClass().equals(v._getClass()) && it.getNumber() == v.getNumber() && ((it.getGender() == v.getGender() || v.getNumber() == com.pullenti.morph.MorphNumber.PLURAL))) {
                        it.setCase(com.pullenti.morph.MorphCase.ooBitor(it.getCase(), v.getCase()));
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    com.pullenti.morph.MorphBaseInfo mm = new com.pullenti.morph.MorphBaseInfo();
                    mm.copyFrom(v);
                    mc.addItem(mm);
                }
            }
        }
        if (tt.getMorph().getLanguage().isUa() && mc == null && com.pullenti.unisharp.Utils.stringsEq(s, "Ї")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc.addItem(com.pullenti.morph.MorphBaseInfo._new529(com.pullenti.morph.MorphClass.ADJECTIVE));
        }
        if (mc != null) 
            return com.pullenti.ner.MetaToken._new527(tt, tt, mc);
        if ((((s.length() < 3) && !tt.isWhitespaceBefore() && tt.getPrevious() != null) && tt.getPrevious().isHiphen() && !tt.getPrevious().isWhitespaceBefore()) && tt.getWhitespacesAfterCount() == 1 && com.pullenti.unisharp.Utils.stringsNe(s, "А")) 
            return com.pullenti.ner.MetaToken._new527(tt, tt, com.pullenti.ner.MorphCollection._new531(com.pullenti.morph.MorphClass.ADJECTIVE));
        return null;
    }

    private static com.pullenti.ner.Token _tryParseFloat(com.pullenti.ner.NumberToken t, com.pullenti.unisharp.Outargwrapper<Double> d, boolean noWs) {
        d.value = 0.0;
        if (t == null || t.getNext() == null || t.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
            return null;
        for (com.pullenti.ner.Token tt = t.getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) 
                return null;
        }
        AnalysisKit kit = t.kit;
        java.util.ArrayList<com.pullenti.ner.NumberToken> ns = null;
        java.util.ArrayList<Character> sps = null;
        for (com.pullenti.ner.Token t1 = t; t1 != null; t1 = t1.getNext()) {
            if (t1.getNext() == null) 
                break;
            if (((t1.getNext() instanceof com.pullenti.ner.NumberToken) && (t1.getWhitespacesAfterCount() < 3) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && t1.getNext().getLengthChar() == 3) {
                if (ns == null) {
                    ns = new java.util.ArrayList<com.pullenti.ner.NumberToken>();
                    ns.add(t);
                    sps = new java.util.ArrayList<Character>();
                }
                else if (sps.get(0) != ' ') 
                    return null;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class));
                sps.add(' ');
                continue;
            }
            if ((t1.getNext().isCharOf(",.") && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && (t1.getWhitespacesAfterCount() < 2) && (t1.getNext().getWhitespacesAfterCount() < 2)) {
                if (noWs) {
                    if (t1.isWhitespaceAfter() || t1.getNext().isWhitespaceAfter()) 
                        break;
                }
                if (ns == null) {
                    ns = new java.util.ArrayList<com.pullenti.ner.NumberToken>();
                    ns.add(t);
                    sps = new java.util.ArrayList<Character>();
                }
                else if (t1.getNext().isWhitespaceAfter() && t1.getNext().getNext().getLengthChar() != 3 && ((t1.getNext().isChar('.') ? '.' : ',')) == sps.get(sps.size() - 1)) 
                    break;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class));
                sps.add((t1.getNext().isChar('.') ? '.' : ','));
                t1 = t1.getNext();
                continue;
            }
            break;
        }
        if (sps == null) 
            return null;
        boolean isLastDrob = false;
        boolean notSetDrob = false;
        boolean merge = false;
        char m_PrevPointChar = '.';
        if (sps.size() == 1) {
            if (sps.get(0) == ' ') 
                isLastDrob = false;
            else if (ns.get(1).getLengthChar() != 3) {
                isLastDrob = true;
                if (ns.size() == 2) {
                    if (ns.get(1).getEndToken().chars.isLetter()) 
                        merge = true;
                    else if (ns.get(1).getEndToken().isChar('.') && ns.get(1).getEndToken().getPrevious() != null && ns.get(1).getEndToken().getPrevious().chars.isLetter()) 
                        merge = true;
                    if (ns.get(1).isWhitespaceBefore()) {
                        if ((ns.get(1).getEndToken() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ns.get(1).getEndToken(), com.pullenti.ner.TextToken.class)).term.endsWith("000")) 
                            return null;
                    }
                }
            }
            else if (ns.get(0).getLengthChar() > 3 || ns.get(0).getRealValue() == 0) 
                isLastDrob = true;
            else {
                boolean ok = true;
                if (ns.size() == 2 && ns.get(1).getLengthChar() == 3) {
                    TerminToken ttt = com.pullenti.ner.core.internal.NumberExHelper.m_Postfixes.tryParse(ns.get(1).getEndToken().getNext(), TerminParseAttr.NO);
                    if (ttt != null && ((NumberExType)ttt.termin.tag) == NumberExType.MONEY) {
                        isLastDrob = false;
                        ok = false;
                        notSetDrob = false;
                    }
                    else if (ns.get(1).getEndToken().getNext() != null && ns.get(1).getEndToken().getNext().isChar('(') && (ns.get(1).getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        com.pullenti.ner.NumberToken nt1 = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ns.get(1).getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class);
                        if (nt1.getRealValue() == (((ns.get(0).getRealValue() * (1000.0)) + ns.get(1).getRealValue()))) {
                            isLastDrob = false;
                            ok = false;
                            notSetDrob = false;
                        }
                    }
                }
                if (ok) {
                    if (t.kit.miscData.containsKey("pt")) 
                        m_PrevPointChar = (char)t.kit.miscData.get("pt");
                    if (m_PrevPointChar == sps.get(0)) {
                        isLastDrob = true;
                        notSetDrob = true;
                    }
                    else {
                        isLastDrob = false;
                        notSetDrob = true;
                    }
                }
            }
        }
        else {
            char last = sps.get(sps.size() - 1);
            if (last == ' ' && sps.get(0) != last) 
                return null;
            for (int i = 0; i < (sps.size() - 1); i++) {
                if (sps.get(i) != sps.get(0)) 
                    return null;
                else if (ns.get(i + 1).getLengthChar() != 3) 
                    return null;
            }
            if (sps.get(0) != last) 
                isLastDrob = true;
            else if (ns.get(ns.size() - 1).getLengthChar() != 3) 
                return null;
            if (ns.get(0).getLengthChar() > 3) 
                return null;
        }
        for (int i = 0; i < ns.size(); i++) {
            if ((i < (ns.size() - 1)) || !isLastDrob) {
                if (i == 0) 
                    d.value = ns.get(i).getRealValue();
                else 
                    d.value = (d.value * (1000.0)) + ns.get(i).getRealValue();
                if (i == (ns.size() - 1) && !notSetDrob) {
                    if (sps.get(sps.size() - 1) == ',') 
                        m_PrevPointChar = '.';
                    else if (sps.get(sps.size() - 1) == '.') 
                        m_PrevPointChar = ',';
                }
            }
            else {
                if (!notSetDrob) {
                    m_PrevPointChar = sps.get(sps.size() - 1);
                    if (m_PrevPointChar == ',') {
                    }
                }
                double f2;
                if (merge) {
                    String sss = ns.get(i).getValue().toString();
                    int kkk;
                    for (kkk = 0; kkk < (sss.length() - ns.get(i).getBeginToken().getLengthChar()); kkk++) {
                        d.value *= (10.0);
                    }
                    f2 = ns.get(i).getRealValue();
                    for (kkk = 0; kkk < ns.get(i).getBeginToken().getLengthChar(); kkk++) {
                        f2 /= (10.0);
                    }
                    d.value += f2;
                }
                else {
                    f2 = ns.get(i).getRealValue();
                    for (int kkk = 0; kkk < ns.get(i).getLengthChar(); kkk++) {
                        f2 /= (10.0);
                    }
                    d.value += f2;
                }
            }
        }
        if (kit.miscData.containsKey("pt")) 
            kit.miscData.put("pt", m_PrevPointChar);
        else 
            kit.miscData.put("pt", m_PrevPointChar);
        return ns.get(ns.size() - 1);
    }

    /**
     * Выделить действительное число, знак также выделяется, 
     * разделители дроби могут быть точка или запятая, разделителями тысячных 
     * могут быть точки, пробелы и запятые.
     * @param t начальный токен
     * @param canBeInteger число может быть целым
     * @param noWhitespace не должно быть пробелов
     * @return числовой метатокен или null
     * 
     */
    public static com.pullenti.ner.NumberToken tryParseRealNumber(com.pullenti.ner.Token t, boolean canBeInteger, boolean noWhitespace) {
        boolean isNot = false;
        com.pullenti.ner.Token t0 = t;
        if (t != null) {
            if (t.isHiphen() || t.isValue("МИНУС", "МІНУС")) {
                t = t.getNext();
                isNot = true;
            }
            else if (t.isChar('+') || t.isValue("ПЛЮС", null)) 
                t = t.getNext();
        }
        if ((t instanceof com.pullenti.ner.TextToken) && ((t.isValue("НОЛЬ", null) || t.isValue("НУЛЬ", null)))) {
            if (t.getNext() == null) 
                return new com.pullenti.ner.NumberToken(t, t, "0", com.pullenti.ner.NumberSpellingType.WORDS, null);
            if (t.getNext().isValue("ЦЕЛЫЙ", "ЦІЛИЙ") && t.getNext().getNext() != null) 
                t = t.getNext();
            if (t.getNext().isCommaAnd() && t.getNext().getNext() != null) 
                t = t.getNext();
            com.pullenti.ner.NumberToken res0 = new com.pullenti.ner.NumberToken(t, t.getNext(), "0", com.pullenti.ner.NumberSpellingType.WORDS, null);
            t = t.getNext();
            if ((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                int val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
                if (t.getNext() != null && val > 0) 
                    res0._corrDrob((double)val);
                if (res0.getRealValue() == 0) {
                    res0.setEndToken(t);
                    res0.setValue(("0." + val));
                }
            }
            return res0;
        }
        if (t instanceof com.pullenti.ner.TextToken) {
            TerminToken tok = m_AfterPoints.tryParse(t, TerminParseAttr.NO);
            if (tok != null) {
                NumberExToken res0 = new NumberExToken(t, tok.getEndToken(), null, com.pullenti.ner.NumberSpellingType.WORDS, NumberExType.UNDEFINED);
                res0.setRealValue((double)(tok.termin.tag));
                return res0;
            }
        }
        if (t == null) 
            return null;
        if (!(t instanceof com.pullenti.ner.NumberToken)) {
            if (t.isValue("СОТНЯ", null)) 
                return new com.pullenti.ner.NumberToken(t, t, "100", com.pullenti.ner.NumberSpellingType.WORDS, null);
            if (t.isValue("ТЫЩА", null) || t.isValue("ТЫСЯЧА", "ТИСЯЧА")) 
                return new com.pullenti.ner.NumberToken(t, t, "1000", com.pullenti.ner.NumberSpellingType.WORDS, null);
            return null;
        }
        if (t.getNext() != null && t.getNext().isValue("ЦЕЛЫЙ", "ЦІЛИЙ")) {
            com.pullenti.ner.Token tt1 = t.getNext().getNext();
            if (tt1 != null && tt1.isCommaAnd()) 
                tt1 = tt1.getNext();
            if ((tt1 instanceof com.pullenti.ner.NumberToken) || (((tt1 instanceof com.pullenti.ner.TextToken) && tt1.isValue("НОЛЬ", "НУЛЬ")))) {
                NumberExToken res0 = new NumberExToken(t, t.getNext(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), com.pullenti.ner.NumberSpellingType.WORDS, NumberExType.UNDEFINED);
                t = tt1;
                double val = 0.0;
                if (t instanceof com.pullenti.ner.TextToken) {
                    res0.setEndToken(t);
                    t = t.getNext();
                }
                if (t instanceof com.pullenti.ner.NumberToken) {
                    res0.setEndToken(t);
                    val = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getRealValue();
                    t = t.getNext();
                    res0._corrDrob(val);
                }
                if (res0.getRealValue() == 0) {
                    String str = ("0." + val);
                    double dd = 0.0;
                    com.pullenti.unisharp.Outargwrapper<Double> wrapdd535 = new com.pullenti.unisharp.Outargwrapper<Double>();
                    boolean inoutres536 = com.pullenti.unisharp.Utils.parseDouble(str, null, wrapdd535);
                    dd = (wrapdd535.value != null ? wrapdd535.value : 0);
                    if (inoutres536) {
                    }
                    else {
                        com.pullenti.unisharp.Outargwrapper<Double> wrapdd533 = new com.pullenti.unisharp.Outargwrapper<Double>();
                        boolean inoutres534 = com.pullenti.unisharp.Utils.parseDouble(str.replace('.', ','), null, wrapdd533);
                        dd = (wrapdd533.value != null ? wrapdd533.value : 0);
                        if (inoutres534) {
                        }
                        else 
                            return null;
                    }
                    res0.setRealValue(dd + res0.getRealValue());
                }
                return res0;
            }
        }
        double d;
        com.pullenti.unisharp.Outargwrapper<Double> wrapd538 = new com.pullenti.unisharp.Outargwrapper<Double>();
        com.pullenti.ner.Token tt = _tryParseFloat((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class), wrapd538, noWhitespace);
        d = (wrapd538.value != null ? wrapd538.value : 0);
        if (tt == null) {
            if ((t.getNext() == null || t.isWhitespaceAfter() || t.getNext().chars.isLetter()) || canBeInteger) {
                tt = t;
                d = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getRealValue();
            }
            else 
                return null;
        }
        if (isNot) 
            d = -d;
        if (tt.getNext() != null && tt.getNext().isValue("ДЕСЯТОК", null)) {
            d *= (10.0);
            tt = tt.getNext();
        }
        return NumberExToken._new537(t0, tt, "", com.pullenti.ner.NumberSpellingType.DIGIT, NumberExType.UNDEFINED, d);
    }

    /**
     * Преобразовать целое число в записанное буквами числительное в нужном роде и числе именительного падежа. 
     * Например, 5 жен.ед. - ПЯТАЯ,  26 мн. - ДВАДЦАТЬ ШЕСТЫЕ.
     * @param value целочисленное значение
     * @param gender род
     * @param num число
     * @return значение
     * 
     */
    public static String getNumberAdjective(int value, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphNumber num) {
        if ((value < 1) || value >= 100) 
            return null;
        String[] words = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            words = m_PluralNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            words = m_WomanNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            words = m_NeutralNumberWords;
        else 
            words = m_ManNumberWords;
        if (value < 20) 
            return words[value - 1];
        int i = value / 10;
        int j = value % 10;
        i -= 2;
        if (i >= m_DecDumberWords.length) 
            return null;
        if (j > 0) 
            return (m_DecDumberWords[i] + " " + words[j - 1]);
        String[] decs = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            decs = m_PluralDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            decs = m_WomanDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            decs = m_NeutralDecDumberWords;
        else 
            decs = m_ManDecDumberWords;
        return decs[i];
    }

    private static String[] m_ManNumberWords;

    private static String[] m_NeutralNumberWords;

    private static String[] m_WomanNumberWords;

    private static String[] m_PluralNumberWords;

    private static String[] m_DecDumberWords;

    private static String[] m_ManDecDumberWords;

    private static String[] m_WomanDecDumberWords;

    private static String[] m_NeutralDecDumberWords;

    private static String[] m_PluralDecDumberWords;

    private static String[] m_100Words;

    private static String[] m_10Words;

    private static String[] m_1Words;

    public static String[] m_Romans;

    public static boolean checkPureNumber(com.pullenti.ner.Token t) {
        if (t instanceof com.pullenti.ner.NumberToken) 
            return checkPureNumber(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getEndToken());
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return false;
        for (String w : m_1Words) {
            if (t.isValue(w, null)) 
                return true;
        }
        for (String w : m_10Words) {
            if (t.isValue(w, null)) 
                return true;
        }
        for (String w : m_100Words) {
            if (t.isValue(w, null)) 
                return true;
        }
        return false;
    }

    /**
     * Получить для числа римскую запись
     * @param val целое число
     * @return римская запись
     */
    public static String getNumberRoman(int val) {
        if (val > 0 && val <= m_Romans.length) 
            return m_Romans[val - 1];
        return ((Integer)val).toString();
    }

    /**
     * Получить строковое представление целого числа. Например, GetNumberString(38, "попугай") => "тридцать восемь попугаев".
     * @param val значение
     * @param units единицы измерения (если не null, то они тоже будут преобразовываться в нужное число)
     * @return строковое представление (пока на русском языке)
     */
    public static String getNumberString(int val, String units) {
        if (val < 0) 
            return "минус " + getNumberString(-val, units);
        String res;
        if (val >= 1000000000) {
            int vv = val / 1000000000;
            res = getNumberString(vv, "миллиард");
            vv = val % 1000000000;
            if (vv != 0) 
                res = (res + " " + getNumberString(vv, units));
            else if (units != null) 
                res = (res + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.PLURAL, null));
            return res.toLowerCase();
        }
        if (val >= 1000000) {
            int vv = val / 1000000;
            res = getNumberString(vv, "миллион");
            vv = val % 1000000;
            if (vv != 0) 
                res = (res + " " + getNumberString(vv, units));
            else if (units != null) 
                res = (res + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.PLURAL, null));
            return res.toLowerCase();
        }
        if (val >= 1000) {
            int vv = val / 1000;
            res = getNumberString(vv, "тысяча");
            vv = val % 1000;
            if (vv != 0) 
                res = (res + " " + getNumberString(vv, units));
            else if (units != null) 
                res = (res + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.PLURAL, null));
            return res.toLowerCase();
        }
        if (val >= 100) {
            int vv = val / 100;
            res = m_100Words[vv - 1];
            vv = val % 100;
            if (vv != 0) 
                res = (res + " " + getNumberString(vv, units));
            else if (units != null) 
                res = (res + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.PLURAL, null));
            return res.toLowerCase();
        }
        if (val >= 20) {
            int vv = val / 10;
            res = m_10Words[vv - 1];
            vv = val % 10;
            if (vv != 0) 
                res = (res + " " + getNumberString(vv, units));
            else if (units != null) 
                res = (res + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.PLURAL, null));
            return res.toLowerCase();
        }
        if (units != null) {
            if (val == 1) {
                com.pullenti.morph.MorphWordForm bi = com.pullenti.morph.MorphologyService.getWordBaseInfo(units.toUpperCase(), null, false, false);
                if (((short)((bi.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) == (com.pullenti.morph.MorphGender.FEMINIE.value())) 
                    return "одна " + units;
                if (((short)((bi.getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) == (com.pullenti.morph.MorphGender.NEUTER.value())) 
                    return "одно " + units;
                return "один " + units;
            }
            if (val == 2) {
                com.pullenti.morph.MorphWordForm bi = com.pullenti.morph.MorphologyService.getWordBaseInfo(units.toUpperCase(), null, false, false);
                if (((short)((bi.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) == (com.pullenti.morph.MorphGender.FEMINIE.value())) 
                    return "две " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, null, com.pullenti.morph.MorphNumber.PLURAL, null);
            }
            return (m_1Words[val].toLowerCase() + " " + MiscHelper.getTextMorphVarByCaseAndNumberEx(units, com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphNumber.UNDEFINED, ((Integer)val).toString()));
        }
        return m_1Words[val].toLowerCase();
    }

    /**
     * Получить морфологическую информацию для последующей нормализации того, что идёт за числом. 
     * Например, для 38 вернёт то, в какую форму нужно преобразовать "ПОПУГАЙ" - множ.число именит.падеж.
     * @param numVal число (например, 123)
     * @param cla часть речи (существительное или прилагательное)
     * @return результат
     */
    public static com.pullenti.morph.MorphBaseInfo getNumberBaseInfo(String numVal, com.pullenti.morph.MorphClass cla) {
        char ch = numVal.charAt(numVal.length() - 1);
        int n = 0;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn540 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        com.pullenti.unisharp.Utils.parseInteger(numVal, 0, null, wrapn540);
        n = (wrapn540.value != null ? wrapn540.value : 0);
        com.pullenti.morph.MorphBaseInfo res = com.pullenti.morph.MorphBaseInfo._new529(cla);
        if (com.pullenti.unisharp.Utils.stringsEq(numVal, "1") || ((ch == '1' && n > 20 && ((n % 100)) != 11))) {
            res.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
            res.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
        }
        else if (((ch == '2' || ch == '3' || ch == '4')) && ((n < 10))) {
            if (cla.isAdjective()) {
                res.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                res.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
            }
            else {
                res.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
                res.setCase(com.pullenti.morph.MorphCase.GENITIVE);
            }
        }
        else {
            res.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
            res.setCase(com.pullenti.morph.MorphCase.GENITIVE);
        }
        return res;
    }

    // Выделение стандартных мер, типа: 10 кв.м.
    // УСТАРЕЛО. Вместо этого лучше использовать возможности MeasureReferent.
    public static NumberExToken tryParseNumberWithPostfix(com.pullenti.ner.Token t) {
        return com.pullenti.ner.core.internal.NumberExHelper.tryParseNumberWithPostfix(t);
    }

    // Это попробовать только тип (постфикс) без самого числа.
    // Например, куб.м.
    public static NumberExToken tryParsePostfixOnly(com.pullenti.ner.Token t) {
        return com.pullenti.ner.core.internal.NumberExHelper.tryAttachPostfixOnly(t);
    }

    // Если это обозначение денежной единицы (н-р, $), то возвращает код валюты
    public static String isMoneyChar(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken) || t.getLengthChar() != 1) 
            return null;
        char ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term.charAt(0);
        if (ch == '$') 
            return "USD";
        if (ch == '£' || ch == ((char)0xA3) || ch == ((char)0x20A4)) 
            return "GBP";
        if (ch == '€') 
            return "EUR";
        if (ch == '₿') 
            return "BTC";
        if (ch == '¥' || ch == ((char)0xA5)) 
            return "JPY";
        if (ch == ((char)0x20A9)) 
            return "KRW";
        if (ch == ((char)0xFFE5) || ch == 'Ұ' || ch == 'Ұ') 
            return "CNY";
        if (ch == ((char)0x20BD)) 
            return "RUB";
        if (ch == ((char)0x20B4)) 
            return "UAH";
        if (ch == ((char)0x20AB)) 
            return "VND";
        if (ch == ((char)0x20AD)) 
            return "LAK";
        if (ch == ((char)0x20BA)) 
            return "TRY";
        if (ch == ((char)0x20B1)) 
            return "PHP";
        if (ch == ((char)0x17DB)) 
            return "KHR";
        if (ch == ((char)0x20B9)) 
            return "INR";
        if (ch == ((char)0x20A8)) 
            return "IDR";
        if (ch == ((char)0x20B5)) 
            return "GHS";
        if (ch == ((char)0x09F3)) 
            return "BDT";
        if (ch == ((char)0x20B8)) 
            return "KZT";
        if (ch == ((char)0x20AE)) 
            return "MNT";
        if (ch == ((char)0x0192)) 
            return "HUF";
        if (ch == ((char)0x20AA)) 
            return "ILS";
        return null;
    }

    /**
     * Для парсинга действительного числа из строки используйте эту функцию, 
     * которая работает назависимо от локализьных настроек и на всех языках программирования.
     * @param str строка с действительным числом
     * @return double-число или null
     * 
     */
    public static Double stringToDouble(String str) {
        double res;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(str, "NaN")) 
            return Double.NaN;
        com.pullenti.unisharp.Outargwrapper<Double> wrapres545 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres546 = com.pullenti.unisharp.Utils.parseDouble(str, null, wrapres545);
        res = (wrapres545.value != null ? wrapres545.value : 0);
        if (inoutres546) 
            return res;
        com.pullenti.unisharp.Outargwrapper<Double> wrapres543 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres544 = com.pullenti.unisharp.Utils.parseDouble(str.replace('.', ','), null, wrapres543);
        res = (wrapres543.value != null ? wrapres543.value : 0);
        if (inoutres544) 
            return res;
        com.pullenti.unisharp.Outargwrapper<Double> wrapres541 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres542 = com.pullenti.unisharp.Utils.parseDouble(str.replace(',', '.'), null, wrapres541);
        res = (wrapres541.value != null ? wrapres541.value : 0);
        if (inoutres542) 
            return res;
        return null;
    }

    /**
     * Независимо от языка и локальных настроек выводит действительное число в строку, 
     * разделитель - всегда точка. Ситуация типа 1.0000000001 или 23.7299999999999, 
     * случающиеся на разных языках, округляются куда надо.
     * @param d число
     * @return строковый результат
     * 
     */
    public static String doubleToString(double d) {
        if (Double.isNaN(d)) 
            return "NaN";
        String res = null;
        if (com.pullenti.unisharp.Utils.mathTruncate(d) == 0.0) 
            res = ((Double)d).toString().replace(",", ".");
        else {
            double rest = Math.abs(d - com.pullenti.unisharp.Utils.mathTruncate(d));
            if ((rest < 0.000000001) && rest > 0) {
                res = ((Double)com.pullenti.unisharp.Utils.mathTruncate(d)).toString();
                if ((res.indexOf('E') < 0) && (res.indexOf('e') < 0)) {
                    int ii = res.indexOf('.');
                    if (ii < 0) 
                        ii = res.indexOf(',');
                    if (ii > 0) 
                        return res.substring(0, 0 + ii);
                    else 
                        return res;
                }
            }
            else 
                res = ((Double)d).toString().replace(",", ".");
        }
        if (res.endsWith(".0")) 
            res = res.substring(0, 0 + res.length() - 2);
        int i = res.indexOf('e');
        if (i < 0) 
            i = res.indexOf('E');
        if (i > 0) {
            int exp = 0;
            boolean neg = false;
            for (int jj = i + 1; jj < res.length(); jj++) {
                if (res.charAt(jj) == '+') {
                }
                else if (res.charAt(jj) == '-') 
                    neg = true;
                else 
                    exp = (exp * 10) + ((((int)res.charAt(jj)) - ((int)'0')));
            }
            res = res.substring(0, 0 + i);
            if (res.endsWith(".0")) 
                res = res.substring(0, 0 + res.length() - 2);
            boolean nneg = false;
            if (res.charAt(0) == '-') {
                nneg = true;
                res = res.substring(1);
            }
            StringBuilder v1 = new StringBuilder();
            StringBuilder v2 = new StringBuilder();
            i = res.indexOf('.');
            if (i < 0) 
                v1.append(res);
            else {
                v1.append(res.substring(0, 0 + i));
                v2.append(res.substring(i + 1));
            }
            for (; exp > 0; exp--) {
                if (neg) {
                    if (v1.length() > 0) {
                        v2.insert(0, v1.charAt(v1.length() - 1));
                        v1.setLength(v1.length() - 1);
                    }
                    else 
                        v2.insert(0, '0');
                }
                else if (v2.length() > 0) {
                    v1.append(v2.charAt(0));
                    v2.delete(0, 0 + 1);
                }
                else 
                    v1.append('0');
            }
            if (v2.length() == 0) 
                res = v1.toString();
            else if (v1.length() == 0) 
                res = "0." + v2;
            else 
                res = (v1.toString() + "." + v2.toString());
            if (nneg) 
                res = "-" + res;
        }
        i = res.indexOf('.');
        if (i < 0) 
            return res;
        i++;
        int j;
        for (j = i + 1; j < res.length(); j++) {
            if (res.charAt(j) == '9') {
                int k = 0;
                int jj;
                for (jj = j; jj < res.length(); jj++) {
                    if (res.charAt(jj) != '9') 
                        break;
                    else 
                        k++;
                }
                if (jj >= res.length() || ((jj == (res.length() - 1) && res.charAt(jj) == '8'))) {
                    if (k > 5) {
                        for (; j > i; j--) {
                            if (res.charAt(j) != '9') {
                                if (res.charAt(j) != '.') 
                                    return (res.substring(0, 0 + j) + (((((int)res.charAt(j)) - ((int)'0'))) + 1));
                            }
                        }
                        break;
                    }
                }
            }
        }
        return res;
    }

    private static final int prilNumTagBit = 0x40000000;

    public static void initialize() {
        if (m_Nums != null) 
            return;
        m_Nums = new TerminCollection();
        m_Nums.allAddStrsNormalized = true;
        m_Nums.addString("ОДИН", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ПЕРВЫЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ОДИН", 1, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ПЕРШИЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ОДНА", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ОДНО", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("FIRST", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("SEMEL", 1, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ONE", 1, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ДВА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ВТОРОЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ДВОЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ДВЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ДВУХ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ОБА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ОБЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ДРУГИЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ДВОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ДВІ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ДВОХ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ОБОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ОБИДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("SECOND", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("BIS", 2, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("TWO", 2, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ТРИ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ТРЕТИЙ", 3 | prilNumTagBit, null, false);
        m_Nums.addString("ТРЕХ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ТРОЕ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addString("ТРИ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ТРЕТІЙ", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ТРЬОХ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("ТРОЄ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addString("THIRD", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("TER", 3, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("THREE", 3, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ЧЕТЫРЕ", 4, null, false);
        m_Nums.addString("ЧЕТВЕРТЫЙ", 4 | prilNumTagBit, null, false);
        m_Nums.addString("ЧЕТЫРЕХ", 4, null, false);
        m_Nums.addString("ЧЕТВЕРО", 4, null, false);
        m_Nums.addString("ЧОТИРИ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧЕТВЕРТИЙ", 4 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧОТИРЬОХ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FORTH", 4 | prilNumTagBit, null, false);
        m_Nums.addString("QUATER", 4, null, false);
        m_Nums.addString("FOUR", 4, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ПЯТЬ", 5, null, false);
        m_Nums.addString("ПЯТЫЙ", 5 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТИ", 5, null, false);
        m_Nums.addString("ПЯТЕРО", 5, null, false);
        m_Nums.addString("ПЯТЬ", 5, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТИЙ", 5 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FIFTH", 5 | prilNumTagBit, null, false);
        m_Nums.addString("QUINQUIES", 5, null, false);
        m_Nums.addString("FIVE", 5, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addString("ШЕСТЬ", 6, null, false);
        m_Nums.addString("ШЕСТОЙ", 6 | prilNumTagBit, null, false);
        m_Nums.addString("ШЕСТИ", 6, null, false);
        m_Nums.addString("ШЕСТЕРО", 6, null, false);
        m_Nums.addString("ШІСТЬ", 6, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШОСТИЙ", 6 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SIX", 6, com.pullenti.morph.MorphLang.EN, false);
        m_Nums.addString("SIXTH", 6 | prilNumTagBit, null, false);
        m_Nums.addString("SEXIES ", 6, null, false);
        m_Nums.addString("СЕМЬ", 7, null, false);
        m_Nums.addString("СЕДЬМОЙ", 7 | prilNumTagBit, null, false);
        m_Nums.addString("СЕМИ", 7, null, false);
        m_Nums.addString("СЕМЕРО", 7, null, false);
        m_Nums.addString("СІМ", 7, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СЬОМИЙ", 7 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SEVEN", 7, null, false);
        m_Nums.addString("SEVENTH", 7 | prilNumTagBit, null, false);
        m_Nums.addString("SEPTIES", 7, null, false);
        m_Nums.addString("ВОСЕМЬ", 8, null, false);
        m_Nums.addString("ВОСЬМОЙ", 8 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЬМИ", 8, null, false);
        m_Nums.addString("ВОСЬМЕРО", 8, null, false);
        m_Nums.addString("ВІСІМ", 8, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВОСЬМИЙ", 8 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("EIGHT", 8, null, false);
        m_Nums.addString("EIGHTH", 8 | prilNumTagBit, null, false);
        m_Nums.addString("OCTIES", 8, null, false);
        m_Nums.addString("ДЕВЯТЬ", 9, null, false);
        m_Nums.addString("ДЕВЯТЫЙ", 9 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТИ", 9, null, false);
        m_Nums.addString("ДЕВЯТЕРО", 9, null, false);
        m_Nums.addString("ДЕВЯТЬ", 9, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯТИЙ", 9 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("NINE", 9, null, false);
        m_Nums.addString("NINTH", 9 | prilNumTagBit, null, false);
        m_Nums.addString("NOVIES", 9, null, false);
        m_Nums.addString("ДЕСЯТЬ", 10, null, false);
        m_Nums.addString("ДЕСЯТЫЙ", 10 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕСЯТИ", 10, null, false);
        m_Nums.addString("ДЕСЯТИРО", 10, null, false);
        m_Nums.addString("ДЕСЯТЬ", 10, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕСЯТИЙ", 10 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("TEN", 10, null, false);
        m_Nums.addString("TENTH", 10 | prilNumTagBit, null, false);
        m_Nums.addString("DECIES", 10, null, false);
        m_Nums.addString("ОДИННАДЦАТЬ", 11, null, false);
        m_Nums.addString("ОДИНАДЦАТЬ", 11, null, false);
        m_Nums.addString("ОДИННАДЦАТЫЙ", 11 | prilNumTagBit, null, false);
        m_Nums.addString("ОДИННАДЦАТИ", 11, null, false);
        m_Nums.addString("ОДИННАДЦАТИРО", 11, null, false);
        m_Nums.addString("ОДИНАДЦЯТЬ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ОДИНАДЦЯТИЙ", 11 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ОДИНАДЦЯТИ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ELEVEN", 11, null, false);
        m_Nums.addString("ELEVENTH", 11 | prilNumTagBit, null, false);
        m_Nums.addString("ДВЕНАДЦАТЬ", 12, null, false);
        m_Nums.addString("ДВЕНАДЦАТЫЙ", 12 | prilNumTagBit, null, false);
        m_Nums.addString("ДВЕНАДЦАТИ", 12, null, false);
        m_Nums.addString("ДВАНАДЦЯТЬ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВАНАДЦЯТИЙ", 12 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВАНАДЦЯТИ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("TWELVE", 12, null, false);
        m_Nums.addString("TWELFTH", 12 | prilNumTagBit, null, false);
        m_Nums.addString("ТРИНАДЦАТЬ", 13, null, false);
        m_Nums.addString("ТРИНАДЦАТЫЙ", 13 | prilNumTagBit, null, false);
        m_Nums.addString("ТРИНАДЦАТИ", 13, null, false);
        m_Nums.addString("ТРИНАДЦЯТЬ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИНАДЦЯТИЙ", 13 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИНАДЦЯТИ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("THIRTEEN", 13, null, false);
        m_Nums.addString("THIRTEENTH", 13 | prilNumTagBit, null, false);
        m_Nums.addString("ЧЕТЫРНАДЦАТЬ", 14, null, false);
        m_Nums.addString("ЧЕТЫРНАДЦАТЫЙ", 14 | prilNumTagBit, null, false);
        m_Nums.addString("ЧЕТЫРНАДЦАТИ", 14, null, false);
        m_Nums.addString("ЧОТИРНАДЦЯТЬ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧОТИРНАДЦЯТИЙ", 14 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧОТИРНАДЦЯТИ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FOURTEEN", 14, null, false);
        m_Nums.addString("FOURTEENTH", 14 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТНАДЦАТЬ", 15, null, false);
        m_Nums.addString("ПЯТНАДЦАТЫЙ", 15 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТНАДЦАТИ", 15, null, false);
        m_Nums.addString("ПЯТНАДЦЯТЬ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТНАДЦЯТИЙ", 15 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТНАДЦЯТИ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FIFTEEN", 15, null, false);
        m_Nums.addString("FIFTEENTH", 15 | prilNumTagBit, null, false);
        m_Nums.addString("ШЕСТНАДЦАТЬ", 16, null, false);
        m_Nums.addString("ШЕСТНАДЦАТЫЙ", 16 | prilNumTagBit, null, false);
        m_Nums.addString("ШЕСТНАДЦАТИ", 16, null, false);
        m_Nums.addString("ШІСТНАДЦЯТЬ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШІСТНАДЦЯТИЙ", 16 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШІСТНАДЦЯТИ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SIXTEEN", 16, null, false);
        m_Nums.addString("SIXTEENTH", 16 | prilNumTagBit, null, false);
        m_Nums.addString("СЕМНАДЦАТЬ", 17, null, false);
        m_Nums.addString("СЕМНАДЦАТЫЙ", 17 | prilNumTagBit, null, false);
        m_Nums.addString("СЕМНАДЦАТИ", 17, null, false);
        m_Nums.addString("СІМНАДЦЯТЬ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СІМНАДЦЯТИЙ", 17 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СІМНАДЦЯТИ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SEVENTEEN", 17, null, false);
        m_Nums.addString("SEVENTEENTH", 17 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЕМНАДЦАТЬ", 18, null, false);
        m_Nums.addString("ВОСЕМНАДЦАТЫЙ", 18 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЕМНАДЦАТИ", 18, null, false);
        m_Nums.addString("ВІСІМНАДЦЯТЬ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВІСІМНАДЦЯТИЙ", 18 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВІСІМНАДЦЯТИ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("EIGHTEEN", 18, null, false);
        m_Nums.addString("EIGHTEENTH", 18 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТНАДЦАТЬ", 19, null, false);
        m_Nums.addString("ДЕВЯТНАДЦАТЫЙ", 19 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТНАДЦАТИ", 19, null, false);
        m_Nums.addString("ДЕВЯТНАДЦЯТЬ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯТНАДЦЯТИЙ", 19 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯТНАДЦЯТИ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("NINETEEN", 19, null, false);
        m_Nums.addString("NINETEENTH", 19 | prilNumTagBit, null, false);
        m_Nums.addString("ДВАДЦАТЬ", 20, null, false);
        m_Nums.addString("ДВАДЦАТЫЙ", 20 | prilNumTagBit, null, false);
        m_Nums.addString("ДВАДЦАТИ", 20, null, false);
        m_Nums.addString("ДВАДЦЯТЬ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВАДЦЯТИЙ", 20 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВАДЦЯТИ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("TWENTY", 20, null, false);
        m_Nums.addString("TWENTIETH", 20 | prilNumTagBit, null, false);
        m_Nums.addString("ТРИДЦАТЬ", 30, null, false);
        m_Nums.addString("ТРИДЦАТЫЙ", 30 | prilNumTagBit, null, false);
        m_Nums.addString("ТРИДЦАТИ", 30, null, false);
        m_Nums.addString("ТРИДЦЯТЬ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИДЦЯТИЙ", 30 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИДЦЯТИ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("THIRTY", 30, null, false);
        m_Nums.addString("THIRTIETH", 30 | prilNumTagBit, null, false);
        m_Nums.addString("СОРОК", 40, null, false);
        m_Nums.addString("СОРОКОВОЙ", 40 | prilNumTagBit, null, false);
        m_Nums.addString("СОРОКА", 40, null, false);
        m_Nums.addString("СОРОК", 40, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СОРОКОВИЙ", 40 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FORTY", 40, null, false);
        m_Nums.addString("FORTIETH", 40 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТЬДЕСЯТ", 50, null, false);
        m_Nums.addString("ПЯТИДЕСЯТЫЙ", 50 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТИДЕСЯТИ", 50, null, false);
        m_Nums.addString("ПЯТДЕСЯТ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТДЕСЯТИЙ", 50 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТДЕСЯТИ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("FIFTY", 50, null, false);
        m_Nums.addString("FIFTIETH", 50 | prilNumTagBit, null, false);
        m_Nums.addString("ШЕСТЬДЕСЯТ", 60, null, false);
        m_Nums.addString("ШЕСТИДЕСЯТЫЙ", 60 | prilNumTagBit, null, false);
        m_Nums.addString("ШЕСТИДЕСЯТИ", 60, null, false);
        m_Nums.addString("ШІСТДЕСЯТ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШЕСИДЕСЯТЫЙ", 60 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШІСТДЕСЯТИ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SIXTY", 60, null, false);
        m_Nums.addString("SIXTIETH", 60 | prilNumTagBit, null, false);
        m_Nums.addString("СЕМЬДЕСЯТ", 70, null, false);
        m_Nums.addString("СЕМИДЕСЯТЫЙ", 70 | prilNumTagBit, null, false);
        m_Nums.addString("СЕМИДЕСЯТИ", 70, null, false);
        m_Nums.addString("СІМДЕСЯТ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СІМДЕСЯТИЙ", 70 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СІМДЕСЯТИ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("SEVENTY", 70, null, false);
        m_Nums.addString("SEVENTIETH", 70 | prilNumTagBit, null, false);
        m_Nums.addString("SEVENTIES", 70 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЕМЬДЕСЯТ", 80, null, false);
        m_Nums.addString("ВОСЬМИДЕСЯТЫЙ", 80 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЬМИДЕСЯТИ", 80, null, false);
        m_Nums.addString("ВІСІМДЕСЯТ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВОСЬМИДЕСЯТИЙ", 80 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВІСІМДЕСЯТИ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("EIGHTY", 80, null, false);
        m_Nums.addString("EIGHTIETH", 80 | prilNumTagBit, null, false);
        m_Nums.addString("EIGHTIES", 80 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯНОСТО", 90, null, false);
        m_Nums.addString("ДЕВЯНОСТЫЙ", 90 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯНОСТО", 90, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯНОСТИЙ", 90 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("NINETY", 90, null, false);
        m_Nums.addString("NINETIETH", 90 | prilNumTagBit, null, false);
        m_Nums.addString("NINETIES", 90 | prilNumTagBit, null, false);
        m_Nums.addString("СТО", 100, null, false);
        m_Nums.addString("СОТЫЙ", 100 | prilNumTagBit, null, false);
        m_Nums.addString("СТА", 100, null, false);
        m_Nums.addString("СТО", 100, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СОТИЙ", 100 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("HUNDRED", 100, null, false);
        m_Nums.addString("HUNDREDTH", 100 | prilNumTagBit, null, false);
        m_Nums.addString("ДВЕСТИ", 200, null, false);
        m_Nums.addString("ДВУХСОТЫЙ", 200 | prilNumTagBit, null, false);
        m_Nums.addString("ДВУХСОТ", 200, null, false);
        m_Nums.addString("ДВІСТІ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВОХСОТИЙ", 200 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВОХСОТ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИСТА", 300, null, false);
        m_Nums.addString("ТРЕХСОТЫЙ", 300 | prilNumTagBit, null, false);
        m_Nums.addString("ТРЕХСОТ", 300, null, false);
        m_Nums.addString("ТРИСТА", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРЬОХСОТИЙ", 300 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРЬОХСОТ", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧЕТЫРЕСТА", 400, null, false);
        m_Nums.addString("ЧЕТЫРЕХСОТЫЙ", 400 | prilNumTagBit, null, false);
        m_Nums.addString("ЧОТИРИСТА", 400, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ЧОТИРЬОХСОТИЙ", 400 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТЬСОТ", 500, null, false);
        m_Nums.addString("ПЯТИСОТЫЙ", 500 | prilNumTagBit, null, false);
        m_Nums.addString("ПЯТСОТ", 500, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ПЯТИСОТИЙ", 500 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШЕСТЬСОТ", 600, null, false);
        m_Nums.addString("ШЕСТИСОТЫЙ", 600 | prilNumTagBit, null, false);
        m_Nums.addString("ШІСТСОТ", 600, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ШЕСТИСОТИЙ", 600 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СЕМЬСОТ", 700, null, false);
        m_Nums.addString("СЕМИСОТЫЙ", 700 | prilNumTagBit, null, false);
        m_Nums.addString("СІМСОТ", 700, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("СЕМИСОТИЙ", 700 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВОСЕМЬСОТ", 800, null, false);
        m_Nums.addString("ВОСЕМЬСОТЫЙ", 800 | prilNumTagBit, null, false);
        m_Nums.addString("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, null, false);
        m_Nums.addString("ВІСІМСОТ", 800, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯТЬСОТ", 900, null, false);
        m_Nums.addString("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТИСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТСОТ", 900, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addString("ДЕВЯТИСОТИЙ", 900 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТЫС", 1000, null, false);
        m_Nums.addString("ТЫСЯЧА", 1000, null, false);
        m_Nums.addString("ТЫСЯЧНЫЙ", 1000 | prilNumTagBit, null, false);
        m_Nums.addString("ТИС", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТИСЯЧА", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТИСЯЧНИЙ", 1000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ДВУХТЫСЯЧНЫЙ", 2000 | prilNumTagBit, null, false);
        m_Nums.addString("ДВОХТИСЯЧНИЙ", 2000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("МИЛЛИОН", 1000000, null, false);
        m_Nums.addString("МЛН", 1000000, null, false);
        m_Nums.addString("МІЛЬЙОН", 1000000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("МИЛЛИАРД", 1000000000, null, false);
        m_Nums.addString("МІЛЬЯРД", 1000000000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addString("ТРИЛЛИОН", "1000000000000", null, false);
        m_Nums.addString("ТРИЛЬЙОН", "1000000000000", com.pullenti.morph.MorphLang.UA, false);
        m_AfterPoints = new TerminCollection();
        Termin t = Termin._new100("ПОЛОВИНА", 0.5);
        t.addVariant("ОДНА ВТОРАЯ", false);
        t.addVariant("ПОЛ", false);
        m_AfterPoints.add(t);
        t = Termin._new100("ТРЕТЬ", 0.33);
        t.addVariant("ОДНА ТРЕТЬ", false);
        m_AfterPoints.add(t);
        t = Termin._new100("ЧЕТВЕРТЬ", 0.25);
        t.addVariant("ОДНА ЧЕТВЕРТАЯ", false);
        m_AfterPoints.add(t);
        t = Termin._new100("ПЯТАЯ ЧАСТЬ", 0.2);
        t.addVariant("ОДНА ПЯТАЯ", false);
        m_AfterPoints.add(t);
    }

    public static TerminCollection m_Nums;

    private static TerminCollection m_AfterPoints;

    public NumberHelper() {
    }
    
    static {
        m_Samples = new String[] {"ДЕСЯТЫЙ", "ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ"};
        m_ManNumberWords = new String[] {"ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ", "ДЕСЯТЫЙ", "ОДИННАДЦАТЫЙ", "ДВЕНАДЦАТЫЙ", "ТРИНАДЦАТЫЙ", "ЧЕТЫРНАДЦАТЫЙ", "ПЯТНАДЦАТЫЙ", "ШЕСТНАДЦАТЫЙ", "СЕМНАДЦАТЫЙ", "ВОСЕМНАДЦАТЫЙ", "ДЕВЯТНАДЦАТЫЙ"};
        m_NeutralNumberWords = new String[] {"ПЕРВОЕ", "ВТОРОЕ", "ТРЕТЬЕ", "ЧЕТВЕРТОЕ", "ПЯТОЕ", "ШЕСТОЕ", "СЕДЬМОЕ", "ВОСЬМОЕ", "ДЕВЯТОЕ", "ДЕСЯТОЕ", "ОДИННАДЦАТОЕ", "ДВЕНАДЦАТОЕ", "ТРИНАДЦАТОЕ", "ЧЕТЫРНАДЦАТОЕ", "ПЯТНАДЦАТОЕ", "ШЕСТНАДЦАТОЕ", "СЕМНАДЦАТОЕ", "ВОСЕМНАДЦАТОЕ", "ДЕВЯТНАДЦАТОЕ"};
        m_WomanNumberWords = new String[] {"ПЕРВАЯ", "ВТОРАЯ", "ТРЕТЬЯ", "ЧЕТВЕРТАЯ", "ПЯТАЯ", "ШЕСТАЯ", "СЕДЬМАЯ", "ВОСЬМАЯ", "ДЕВЯТАЯ", "ДЕСЯТАЯ", "ОДИННАДЦАТАЯ", "ДВЕНАДЦАТАЯ", "ТРИНАДЦАТАЯ", "ЧЕТЫРНАДЦАТАЯ", "ПЯТНАДЦАТАЯ", "ШЕСТНАДЦАТАЯ", "СЕМНАДЦАТАЯ", "ВОСЕМНАДЦАТАЯ", "ДЕВЯТНАДЦАТАЯ"};
        m_PluralNumberWords = new String[] {"ПЕРВЫЕ", "ВТОРЫЕ", "ТРЕТЬИ", "ЧЕТВЕРТЫЕ", "ПЯТЫЕ", "ШЕСТЫЕ", "СЕДЬМЫЕ", "ВОСЬМЫЕ", "ДЕВЯТЫЕ", "ДЕСЯТЫЕ", "ОДИННАДЦАТЫЕ", "ДВЕНАДЦАТЫЕ", "ТРИНАДЦАТЫЕ", "ЧЕТЫРНАДЦАТЫЕ", "ПЯТНАДЦАТЫЕ", "ШЕСТНАДЦАТЫЕ", "СЕМНАДЦАТЫЕ", "ВОСЕМНАДЦАТЫЕ", "ДЕВЯТНАДЦАТЫЕ"};
        m_DecDumberWords = new String[] {"ДВАДЦАТЬ", "ТРИДЦАТЬ", "СОРОК", "ПЯТЬДЕСЯТ", "ШЕСТЬДЕСЯТ", "СЕМЬДЕСЯТ", "ВОСЕМЬДЕСЯТ", "ДЕВЯНОСТО"};
        m_ManDecDumberWords = new String[] {"ДВАДЦАТЫЙ", "ТРИДЦАТЫЙ", "СОРОКОВОЙ", "ПЯТЬДЕСЯТЫЙ", "ШЕСТЬДЕСЯТЫЙ", "СЕМЬДЕСЯТЫЙ", "ВОСЕМЬДЕСЯТЫЙ", "ДЕВЯНОСТЫЙ"};
        m_WomanDecDumberWords = new String[] {"ДВАДЦАТАЯ", "ТРИДЦАТАЯ", "СОРОКОВАЯ", "ПЯТЬДЕСЯТАЯ", "ШЕСТЬДЕСЯТАЯ", "СЕМЬДЕСЯТАЯ", "ВОСЕМЬДЕСЯТАЯ", "ДЕВЯНОСТАЯ"};
        m_NeutralDecDumberWords = new String[] {"ДВАДЦАТОЕ", "ТРИДЦАТОЕ", "СОРОКОВОЕ", "ПЯТЬДЕСЯТОЕ", "ШЕСТЬДЕСЯТОЕ", "СЕМЬДЕСЯТОЕ", "ВОСЕМЬДЕСЯТОЕ", "ДЕВЯНОСТОЕ"};
        m_PluralDecDumberWords = new String[] {"ДВАДЦАТЫЕ", "ТРИДЦАТЫЕ", "СОРОКОВЫЕ", "ПЯТЬДЕСЯТЫЕ", "ШЕСТЬДЕСЯТЫЕ", "СЕМЬДЕСЯТЫЕ", "ВОСЕМЬДЕСЯТЫЕ", "ДЕВЯНОСТЫЕ"};
        m_100Words = new String[] {"СТО", "ДВЕСТИ", "ТРИСТА", "ЧЕТЫРЕСТА", "ПЯТЬСОТ", "ШЕСТЬСОТ", "СЕМЬСОТ", "ВОСЕМЬСОТ", "ДЕВЯТЬСОТ"};
        m_10Words = new String[] {"ДЕСЯТЬ", "ДВАДЦАТЬ", "ТРИДЦАТЬ", "СОРОК", "ПЯТЬДЕСЯТ", "ШЕСТЬДЕСЯТ", "СЕМЬДЕСЯТ", "ВОСЕМЬДЕСЯТ", "ДЕВЯНОСТО"};
        m_1Words = new String[] {"НОЛЬ", "ОДИН", "ДВА", "ТРИ", "ЧЕТЫРЕ", "ПЯТЬ", "ШЕСТЬ", "СЕМЬ", "ВОСЕМЬ", "ДЕВЯТЬ", "ДЕСЯТЬ", "ОДИННАДЦАТЬ", "ДВЕНАДЦАТЬ", "ТРИНАДЦАТЬ", "ЧЕТЫРНАДЦАТЬ", "ПЯТНАДЦАТЬ", "ШЕСТНАДЦАТЬ", "СЕМНАДЦАТЬ", "ВОСЕМНАДЦАТЬ", "ДЕВЯТНАДЦАТЬ"};
        m_Romans = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX"};
    }
}
