/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Поддержка работы с собственными именами
public class ProperNameHelper {

    private static String corrChars(String str, com.pullenti.morph.CharsInfo ci, boolean keepChars) {
        if (!keepChars) 
            return str;
        if (ci.isAllLower()) 
            return str.toLowerCase();
        if (ci.isCapitalUpper()) 
            return MiscHelper.convertFirstCharUpperAndOtherLower(str);
        return str;
    }

    private static String getNameWithoutBrackets(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, boolean normalizeFirstNounGroup, boolean normalFirstGroupSingle, boolean ignoreGeoReferent) {
        String res = null;
        if (BracketHelper.canBeStartOfSequence(begin, false, false) && BracketHelper.canBeEndOfSequence(end, false, begin, false)) {
            begin = begin.getNext();
            end = end.getPrevious();
        }
        if (normalizeFirstNounGroup && !begin.getMorph()._getClass().isPreposition()) {
            NounPhraseToken npt = NounPhraseHelper.tryParse(begin, NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
            if (npt != null) {
                if (npt.noun.getMorphClassInDictionary().isUndefined() && npt.adjectives.size() == 0) 
                    npt = null;
            }
            if (npt != null && npt.getEndToken().getEndChar() > end.getEndChar()) 
                npt = null;
            if (npt != null) {
                res = npt.getNormalCaseText(null, (normalFirstGroupSingle ? com.pullenti.morph.MorphNumber.SINGULAR : com.pullenti.morph.MorphNumber.UNDEFINED), com.pullenti.morph.MorphGender.UNDEFINED, false);
                com.pullenti.ner.Token te = npt.getEndToken().getNext();
                if (((te != null && te.getNext() != null && te.isComma()) && (te.getNext() instanceof com.pullenti.ner.TextToken) && te.getNext().getEndChar() <= end.getEndChar()) && te.getNext().getMorph()._getClass().isVerb() && te.getNext().getMorph()._getClass().isAdjective()) {
                    for (com.pullenti.morph.MorphBaseInfo it : te.getNext().getMorph().getItems()) {
                        if (it.getGender() == npt.getMorph().getGender() || ((short)((it.getGender().value()) & (npt.getMorph().getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                            if (!((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), npt.getMorph().getCase()))).isUndefined()) {
                                if (it.getNumber() == npt.getMorph().getNumber() || ((short)((it.getNumber().value()) & (npt.getMorph().getNumber().value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                                    String var = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(te.getNext(), com.pullenti.ner.TextToken.class)).term;
                                    if (it instanceof com.pullenti.morph.MorphWordForm) 
                                        var = ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class)).normalCase;
                                    com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new507(com.pullenti.morph.MorphClass.ADJECTIVE, npt.getMorph().getGender(), npt.getMorph().getNumber(), npt.getMorph().getLanguage());
                                    var = com.pullenti.morph.MorphologyService.getWordform(var, bi);
                                    if (var != null) {
                                        res = (res + ", " + var);
                                        te = te.getNext().getNext();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                if (te != null && te.getEndChar() <= end.getEndChar()) {
                    String s = getNameEx(te, end, com.pullenti.morph.MorphClass.UNDEFINED, com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, true, ignoreGeoReferent);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                        if (!Character.isLetterOrDigit(s.charAt(0))) 
                            res = (res + s);
                        else 
                            res = (res + " " + s);
                    }
                }
            }
            else if ((begin instanceof com.pullenti.ner.TextToken) && begin.chars.isCyrillicLetter()) {
                com.pullenti.morph.MorphClass mm = begin.getMorphClassInDictionary();
                if (!mm.isUndefined()) {
                    res = begin.getNormalCaseText(mm, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (begin.getEndChar() < end.getEndChar()) 
                        res = (res + " " + getNameEx(begin.getNext(), end, com.pullenti.morph.MorphClass.UNDEFINED, com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, true, false));
                }
            }
        }
        if (res == null) 
            res = getNameEx(begin, end, com.pullenti.morph.MorphClass.UNDEFINED, com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, true, ignoreGeoReferent);
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(res)) {
            int k = 0;
            for (int i = res.length() - 1; i >= 0; i--,k++) {
                if (res.charAt(i) == '*' || com.pullenti.unisharp.Utils.isWhitespace(res.charAt(i))) {
                }
                else 
                    break;
            }
            if (k > 0) {
                if (k == res.length()) 
                    return null;
                res = res.substring(0, 0 + res.length() - k);
            }
        }
        return res;
    }

    private static String getName(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        String res = getNameEx(begin, end, com.pullenti.morph.MorphClass.UNDEFINED, com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false, false);
        return res;
    }

    public static String getNameEx(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphCase mc, com.pullenti.morph.MorphGender gender, boolean ignoreBracketsAndHiphens, boolean ignoreGeoReferent) {
        if (end == null || begin == null) 
            return null;
        if (begin.getEndChar() > end.getBeginChar() && begin != end) 
            return null;
        StringBuilder res = new StringBuilder();
        String prefix = null;
        for (com.pullenti.ner.Token t = begin; t != null && t.getEndChar() <= end.getEndChar(); t = t.getNext()) {
            if (res.length() > 1000) 
                break;
            if (t.isTableControlChar()) 
                continue;
            if (ignoreBracketsAndHiphens) {
                if (BracketHelper.isBracket(t, false)) {
                    if (t == end) 
                        break;
                    if (t.isCharOf("(<[")) {
                        BracketSequenceToken br = BracketHelper.tryParse(t, BracketParseAttr.NO, 100);
                        if (br != null && br.getEndChar() <= end.getEndChar()) {
                            String tmp = getNameEx(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.morph.MorphClass.UNDEFINED, com.pullenti.morph.MorphCase.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, ignoreBracketsAndHiphens, false);
                            if (tmp != null) {
                                if ((br.getEndChar() == end.getEndChar() && br.getBeginToken().getNext() == br.getEndToken().getPrevious() && !br.getBeginToken().getNext().chars.isLetter()) && !(br.getBeginToken().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                                }
                                else 
                                    res.append(" ").append(t.getSourceText()).append(tmp).append(br.getEndToken().getSourceText());
                            }
                            t = br.getEndToken();
                        }
                    }
                    continue;
                }
                if (t.isHiphen()) {
                    if (t == end) 
                        break;
                    else if (t.isWhitespaceBefore() || t.isWhitespaceAfter()) 
                        continue;
                }
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt != null) {
                if (!ignoreBracketsAndHiphens) {
                    if ((tt.getNext() != null && tt.getNext().isHiphen() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && tt != end && tt.getNext() != end) {
                        if (prefix == null) 
                            prefix = tt.term;
                        else 
                            prefix = (prefix + "-" + tt.term);
                        t = tt.getNext();
                        if (t == end) 
                            break;
                        else 
                            continue;
                    }
                }
                String s = null;
                if (cla.value != ((short)0) || !mc.isUndefined() || gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                    for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
                        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                        if (wf == null) 
                            continue;
                        if (cla.value != ((short)0)) {
                            if (((((int)wf._getClass().value) & ((int)cla.value))) == 0) 
                                continue;
                        }
                        if (!mc.isUndefined()) {
                            if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), mc))).isUndefined()) 
                                continue;
                        }
                        if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                            if (((short)((wf.getGender().value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                continue;
                        }
                        if (s == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, tt.term)) 
                            s = wf.normalCase;
                    }
                    if (s == null && gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
                            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                            if (wf == null) 
                                continue;
                            if (cla.value != ((short)0)) {
                                if (((((int)wf._getClass().value) & ((int)cla.value))) == 0) 
                                    continue;
                            }
                            if (!mc.isUndefined()) {
                                if (((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), mc))).isUndefined()) 
                                    continue;
                            }
                            if (s == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, tt.term)) 
                                s = wf.normalCase;
                        }
                    }
                }
                if (s == null) {
                    s = tt.term;
                    if (tt.chars.isLastLower() && tt.getLengthChar() > 2) {
                        s = tt.getSourceText();
                        for (int i = s.length() - 1; i >= 0; i--) {
                            if (Character.isUpperCase(s.charAt(i))) {
                                s = s.substring(0, 0 + i + 1);
                                break;
                            }
                        }
                    }
                }
                if (prefix != null) {
                    String delim = "-";
                    if (ignoreBracketsAndHiphens) 
                        delim = " ";
                    s = (prefix + delim + s);
                }
                prefix = null;
                if (res.length() > 0 && s.length() > 0) {
                    if (Character.isLetterOrDigit(s.charAt(0))) {
                        char ch0 = res.charAt(res.length() - 1);
                        if (ch0 == '-') {
                        }
                        else 
                            res.append(' ');
                    }
                    else if (!ignoreBracketsAndHiphens && BracketHelper.canBeStartOfSequence(tt, false, false)) 
                        res.append(' ');
                }
                res.append(s);
            }
            else if (t instanceof com.pullenti.ner.NumberToken) {
                if (res.length() > 0) {
                    if (!t.isWhitespaceBefore() && res.charAt(res.length() - 1) == '-') {
                    }
                    else 
                        res.append(' ');
                }
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if ((t.getMorph()._getClass().isAdjective() && nt.typ == com.pullenti.ner.NumberSpellingType.WORDS && nt.getBeginToken() == nt.getEndToken()) && (nt.getBeginToken() instanceof com.pullenti.ner.TextToken)) 
                    res.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class)).term);
                else 
                    res.append(nt.getValue());
            }
            else if (t instanceof com.pullenti.ner.MetaToken) {
                if ((ignoreGeoReferent && t != begin && t.getReferent() != null) && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "GEO")) 
                    continue;
                String s = getNameEx(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getBeginToken(), ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class)).getEndToken(), cla, mc, gender, ignoreBracketsAndHiphens, ignoreGeoReferent);
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (res.length() > 0) {
                        if (!t.isWhitespaceBefore() && res.charAt(res.length() - 1) == '-') {
                        }
                        else 
                            res.append(' ');
                    }
                    res.append(s);
                }
            }
            if (t == end) 
                break;
        }
        if (res.length() == 0) 
            return null;
        return res.toString();
    }
    public ProperNameHelper() {
    }
}
