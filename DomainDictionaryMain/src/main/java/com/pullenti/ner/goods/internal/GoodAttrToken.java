/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods.internal;

public class GoodAttrToken extends com.pullenti.ner.MetaToken {

    public GoodAttrToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public com.pullenti.ner.goods.GoodAttrType typ = com.pullenti.ner.goods.GoodAttrType.UNDEFINED;

    public String value;

    public String altValue;

    public String name;

    public com.pullenti.ner.Referent ref;

    public com.pullenti.ner.ReferentToken refTok;

    @Override
    public String toString() {
        return (typ.toString() + ": " + value + (altValue == null ? "" : (" / " + altValue)) + " " + (ref == null ? "" : ref.toString()));
    }

    public com.pullenti.ner.goods.GoodAttributeReferent _createAttr() {
        if (ref instanceof com.pullenti.ner.goods.GoodAttributeReferent) 
            return (com.pullenti.ner.goods.GoodAttributeReferent)com.pullenti.unisharp.Utils.cast(ref, com.pullenti.ner.goods.GoodAttributeReferent.class);
        com.pullenti.ner.goods.GoodAttributeReferent ar = new com.pullenti.ner.goods.GoodAttributeReferent();
        if (typ != com.pullenti.ner.goods.GoodAttrType.UNDEFINED) 
            ar.setTyp(typ);
        if (name != null) 
            ar.addSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_NAME, name, false, 0);
        if (ref != null) 
            ar.addSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_REF, ref, true, 0);
        else if (refTok != null) {
            ar.addSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_REF, refTok.referent, true, 0);
            ar.addExtReferent(refTok);
        }
        if (typ == com.pullenti.ner.goods.GoodAttrType.NUMERIC) {
        }
        java.util.ArrayList<String> vals = null;{
                vals = new java.util.ArrayList<String>();
                if (value != null) 
                    vals.add(value);
                if (altValue != null) 
                    vals.add(altValue);
            }
        for (String v : vals) {
            String v1 = v;
            if (ar.getTyp() == com.pullenti.ner.goods.GoodAttrType.PROPER) {
                v1 = v.toUpperCase();
                if (v1.indexOf('\'') >= 0) 
                    v1 = v1.replace("'", "");
            }
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(v1)) 
                continue;
            ar.addSlot((com.pullenti.unisharp.Utils.stringsEq(v, value) ? com.pullenti.ner.goods.GoodAttributeReferent.ATTR_VALUE : com.pullenti.ner.goods.GoodAttributeReferent.ATTR_ALTVALUE), v1, false, 0);
            if ((v1.length() < 10) && com.pullenti.morph.LanguageHelper.isLatinChar(v1.charAt(0)) && ar.getTyp() == com.pullenti.ner.goods.GoodAttrType.PROPER) {
                java.util.ArrayList<String> rus = com.pullenti.ner.core.internal.RusLatAccord.getVariants(v1);
                if (rus == null || rus.size() == 0) 
                    continue;
                for (String vv : rus) {
                    if (ar.findSlot(null, vv, true) == null) {
                        ar.addSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_ALTVALUE, vv, false, 0);
                        if (ar.getSlots().size() > 20) 
                            break;
                    }
                }
            }
        }
        if (ar.findSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_VALUE, null, true) == null && ar.findSlot(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_REF, null, true) == null) 
            return null;
        return ar;
    }

    public static java.util.ArrayList<com.pullenti.ner.ReferentToken> tryParseList(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        java.util.ArrayList<GoodAttrToken> li = _tryParseList(t);
        if (li == null || li.size() == 0) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        for (GoodAttrToken a : li) {
            com.pullenti.ner.goods.GoodAttributeReferent attr = a._createAttr();
            if (attr != null) 
                res.add(new com.pullenti.ner.ReferentToken(attr, a.getBeginToken(), a.getEndToken(), null));
        }
        return res;
    }

    private static java.util.ArrayList<GoodAttrToken> _tryParseList(com.pullenti.ner.Token t) {
        java.util.ArrayList<GoodAttrToken> res = new java.util.ArrayList<GoodAttrToken>();
        GoodAttrToken key = null;
        boolean nextSeq = false;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt != t && tt.isNewlineBefore()) 
                break;
            if (tt != t && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt) && !tt.isChar('(')) {
                nextSeq = true;
                if (key == null) 
                    break;
                GoodAttrToken re2 = GoodAttrToken.tryParse(tt, key, t != tt, false);
                if (re2 != null && ((re2.typ == com.pullenti.ner.goods.GoodAttrType.NUMERIC || re2.typ == com.pullenti.ner.goods.GoodAttrType.MODEL))) {
                }
                else if (re2 != null && ((re2.refTok != null || re2.ref != null))) 
                    nextSeq = false;
                else if ((tt.getMorphClassInDictionary().isVerb() && re2 != null && re2.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER) && _isSpecVerb(tt)) {
                }
                else {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt == null) 
                        break;
                    String noun = npt.noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (key.value == null) {
                        if (key.ref == null) 
                            break;
                        if ((key.ref.toString().toUpperCase().indexOf(noun) >= 0)) {
                        }
                        else 
                            break;
                    }
                    else if ((noun.indexOf(key.value) >= 0) || (key.value.indexOf(noun) >= 0)) {
                    }
                    else 
                        break;
                }
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && nextSeq) {
                com.pullenti.morph.MorphClass dc = tt.getMorphClassInDictionary();
                if (dc.equals(com.pullenti.morph.MorphClass.VERB)) {
                    if (!_isSpecVerb(tt)) 
                        break;
                }
            }
            if (tt.isValue("ДОЛЖЕН", null) || tt.isValue("ДОЛЖНА", null) || tt.isValue("ДОЛЖНО", null)) {
                if (tt.getNext() != null && tt.getNext().getMorphClassInDictionary().isVerb()) 
                    tt = tt.getNext();
                continue;
            }
            GoodAttrToken re = GoodAttrToken.tryParse(tt, key, tt != t, false);
            if (re != null) {
                if (key == null) {
                    if (re.typ == com.pullenti.ner.goods.GoodAttrType.KEYWORD) 
                        key = re;
                    else if (re.typ == com.pullenti.ner.goods.GoodAttrType.NUMERIC || re.typ == com.pullenti.ner.goods.GoodAttrType.MODEL) 
                        return null;
                }
                res.add(re);
                tt = re.getEndToken();
                continue;
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (tt instanceof com.pullenti.ner.NumberToken) 
                res.add(_new1429(tt, tt, tt.getSourceText()));
        }
        if (res.size() > 0 && res.get(res.size() - 1).typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER) {
            if (res.get(res.size() - 1).getEndToken() == res.get(res.size() - 1).getBeginToken() && res.get(res.size() - 1).getEndToken().getMorphClassInDictionary().isAdverb()) 
                res.remove(res.size() - 1);
        }
        return res;
    }

    private static boolean _isSpecVerb(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if ((t.isValue("ПРИМЕНЯТЬ", null) || t.isValue("ИСПОЛЬЗОВАТЬ", null) || t.isValue("ИЗГОТАВЛИВАТЬ", null)) || t.isValue("ПРИМЕНЯТЬ", null) || t.isValue("ИЗГОТОВИТЬ", null)) 
            return true;
        return false;
    }

    public static GoodAttrToken tryParse(com.pullenti.ner.Token t, GoodAttrToken key, boolean canBeMeasure, boolean isChars) {
        GoodAttrToken res = _tryParse_(t, key, canBeMeasure, isChars);
        if (res == null || res.value == null) 
            return res;
        if ((res != null && res.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER && ((res.getEndToken() == res.getBeginToken() || (res.value.indexOf(' ') < 0)))) && res.altValue == null) {
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "ДЛЯ")) 
                return tryParse(t.getNext(), key, false, false);
            if (res.value != null) {
                if (com.pullenti.unisharp.Utils.startsWithString(res.value, "ДВУ", true) && !com.pullenti.unisharp.Utils.startsWithString(res.value, "ДВУХ", true)) 
                    res.value = "ДВУХ" + res.value.substring(3);
            }
        }
        if ((res != null && res.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER && res.getBeginToken().getMorph()._getClass().isPreposition()) && res.getEndToken() != res.getBeginToken() && res.altValue == null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getEndToken() == res.getEndToken()) 
                res.altValue = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
        }
        return res;
    }

    private static GoodAttrToken _tryParse_(com.pullenti.ner.Token t, GoodAttrToken key, boolean canBeMeasure, boolean isChars) {
        if (t == null) 
            return null;
        if (t.isValue("ПРЕДНАЗНАЧЕН", null)) {
        }
        GoodAttrToken res;
        com.pullenti.ner.Referent r = t.getReferent();
        if (r != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO")) 
                return _new1430(t, t, com.pullenti.ner.goods.GoodAttrType.REFERENT, r);
        }
        if (canBeMeasure) {
            if ((((res = _tryParseNum(t)))) != null) 
                return res;
        }
        if (isChars) {
            if ((((res = _tryParseChars(t)))) != null) 
                return res;
        }
        com.pullenti.ner.measure.internal.MeasureToken ms = com.pullenti.ner.measure.internal.MeasureToken.tryParse(t, null, true, false, false, false);
        if (ms != null && ms.nums != null) {
            GoodAttrToken nres = _new1431(t, ms.getEndToken(), com.pullenti.ner.goods.GoodAttrType.NUMERIC);
            nres.name = ms.name;
            nres.value = ms.getNormValues();
            return nres;
        }
        if (t.kit.ontology != null) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = t.kit.ontology.attachToken(com.pullenti.ner.goods.GoodAttributeReferent.OBJ_TYPENAME, t);
            if (li != null && li.get(0).item != null && (li.get(0).item.referent instanceof com.pullenti.ner.goods.GoodAttributeReferent)) {
                res = new GoodAttrToken(li.get(0).getBeginToken(), li.get(0).getEndToken());
                res.typ = ((com.pullenti.ner.goods.GoodAttributeReferent)com.pullenti.unisharp.Utils.cast(li.get(0).item.referent, com.pullenti.ner.goods.GoodAttributeReferent.class)).getTyp();
                res.ref = li.get(0).item.referent.clone();
                return res;
            }
        }
        com.pullenti.ner.core.TerminToken tok;
        if ((((tok = m_StdAbbrs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
            com.pullenti.ner.goods.GoodAttrType ty = (com.pullenti.ner.goods.GoodAttrType)tok.termin.tag;
            if (ty == com.pullenti.ner.goods.GoodAttrType.UNDEFINED && tok.termin.tag2 != null) {
                com.pullenti.ner.Token tt2 = tok.getEndToken().getNext();
                if (tt2 != null && ((tt2.isChar(':') || tt2.isHiphen()))) 
                    tt2 = tt2.getNext();
                res = _tryParse_(tt2, key, false, isChars);
                if (res != null && ((res.typ == com.pullenti.ner.goods.GoodAttrType.PROPER || res.typ == com.pullenti.ner.goods.GoodAttrType.MODEL))) {
                    res.setBeginToken(t);
                    res.name = tok.termin.getCanonicText();
                    return res;
                }
                com.pullenti.ner.core.TerminToken tok2 = m_StdAbbrs.tryParse(tt2, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok2 != null && com.pullenti.unisharp.Utils.stringsEq((String)com.pullenti.unisharp.Utils.cast(tok2.termin.tag2, String.class), "NO")) {
                    res = _new1431(t, tok2.getEndToken(), com.pullenti.ner.goods.GoodAttrType.UNDEFINED);
                    return res;
                }
                res = _tryParseModel(tt2);
                if (res != null) {
                    res.setBeginToken(t);
                    res.name = tok.termin.getCanonicText();
                    return res;
                }
            }
            if (ty != com.pullenti.ner.goods.GoodAttrType.REFERENT) {
                res = _new1433(t, tok.getEndToken(), ty, tok.termin.getCanonicText(), tok.getMorph());
                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
                    res.setEndToken(res.getEndToken().getNext());
                return res;
            }
            if (ty == com.pullenti.ner.goods.GoodAttrType.REFERENT) {
                com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isNewlineBefore()) 
                        break;
                    if (tt.isHiphen() || tt.isCharOf(":")) 
                        continue;
                    if (tt.getMorphClassInDictionary().isAdverb()) 
                        continue;
                    com.pullenti.ner.core.TerminToken tok2 = m_StdAbbrs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null) {
                        com.pullenti.ner.goods.GoodAttrType ty2 = (com.pullenti.ner.goods.GoodAttrType)tok2.termin.tag;
                        if (ty2 == com.pullenti.ner.goods.GoodAttrType.REFERENT || ty2 == com.pullenti.ner.goods.GoodAttrType.UNDEFINED) {
                            tt = tok2.getEndToken();
                            continue;
                        }
                    }
                    break;
                }
                if (tt == null) 
                    return null;
                if (tt.getReferent() != null) 
                    return _new1434(t, tt, tt.getReferent(), com.pullenti.ner.goods.GoodAttrType.REFERENT);
                if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isAllLower() && tt.chars.isLetter()) {
                    com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("ORGANIZATION", tt, null);
                    if (rt != null) 
                        return _new1435(t, rt.getEndToken(), rt, com.pullenti.ner.goods.GoodAttrType.REFERENT);
                }
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                    com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("ORGANIZATION", tt.getNext(), null);
                    if (rt != null) {
                        com.pullenti.ner.Token t1 = rt.getEndToken();
                        if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
                            t1 = t1.getNext();
                        return _new1435(t, t1, rt, com.pullenti.ner.goods.GoodAttrType.REFERENT);
                    }
                }
            }
        }
        if (t.isValue("КАТАЛОЖНЫЙ", null)) {
            com.pullenti.ner.Token tt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t.getNext());
            if (tt != null) {
                if (tt.isCharOf(":") || tt.isHiphen()) 
                    tt = tt.getNext();
                res = _tryParseModel(tt);
                if (res != null) {
                    res.setBeginToken(t);
                    res.name = "КАТАЛОЖНЫЙ НОМЕР";
                    return res;
                }
            }
        }
        if (t.isValue("ФАСОВКА", null) || t.isValue("УПАКОВКА", null)) {
            if (!(t.getPrevious() instanceof com.pullenti.ner.NumberToken)) {
                com.pullenti.ner.Token tt = t.getNext();
                if (tt != null) {
                    if (tt.isCharOf(":") || tt.isHiphen()) 
                        tt = tt.getNext();
                }
                if (tt == null) 
                    return null;
                res = _new1437(t, tt, com.pullenti.ner.goods.GoodAttrType.NUMERIC, "ФАСОВКА");
                com.pullenti.ner.Token et = null;
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isComma()) 
                        break;
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                        break;
                    if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !tt.chars.isAllLower()) 
                        break;
                    et = tt;
                }
                if (et != null) {
                    res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getEndToken(), et, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                    res.setEndToken(et);
                }
                return res;
            }
        }
        if ((t instanceof com.pullenti.ner.ReferentToken) && (((t.getReferent() instanceof com.pullenti.ner.uri.UriReferent) || com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "DECREE")))) {
            res = _new1437(t, t, com.pullenti.ner.goods.GoodAttrType.MODEL, "СПЕЦИФИКАЦИЯ");
            res.value = t.getReferent().toString();
            return res;
        }
        if (key == null && !isChars) {
            boolean isAllUpper = true;
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt != t && tt.isNewlineBefore()) 
                    break;
                if (tt.chars.isCyrillicLetter() && !tt.chars.isAllUpper()) {
                    isAllUpper = false;
                    break;
                }
            }
            if ((((!t.chars.isAllUpper() || isAllUpper)) && ((t.getMorph()._getClass().isNoun() || t.getMorph()._getClass().isUndefined())) && t.chars.isCyrillicLetter()) && (t instanceof com.pullenti.ner.TextToken)) {
                if (t.isValue("СООТВЕТСТВИЕ", null)) {
                    com.pullenti.ner.Token tt1 = t.getNext();
                    if (tt1 != null && ((tt1.isChar(':') || tt1.isHiphen()))) 
                        tt1 = tt1.getNext();
                    res = _tryParse_(tt1, key, false, isChars);
                    if (res != null) 
                        res.setBeginToken(t);
                    return res;
                }
                boolean ok = true;
                if (t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isVerb()) {
                    com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS, 0, null);
                    if (npt1 != null && npt1.getEndToken() != t && npt1.adjectives.size() > 0) 
                        ok = false;
                }
                if (ok) {
                    res = _new1439(t, t, com.pullenti.ner.goods.GoodAttrType.KEYWORD, t.getMorph());
                    res.value = t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && ((t.getNext().getNext().chars.isAllLower() || t.getNext().getNext().chars.equals(t.chars)))) {
                        if (!t.isWhitespaceAfter() && !t.getNext().isWhitespaceAfter()) {
                            res.setEndToken((t = t.getNext().getNext()));
                            res.value = (res.value + "-" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                        }
                    }
                    return res;
                }
            }
        }
        if ((t.isWhitespaceBefore() && (t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) && (t.getLengthChar() < 5) && !isChars) {
            com.pullenti.ner.ReferentToken rt = m_DenomAn.tryAttach(t, false);
            if ((rt == null && t.getWhitespacesAfterCount() == 1 && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && (t.getLengthChar() < 3) && _tryParseNum(t.getNext()) == null) 
                rt = m_DenomAn.tryAttach(t, true);
            if (rt != null) {
                res = _new1431(t, rt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.MODEL);
                com.pullenti.ner.denomination.DenominationReferent dr = (com.pullenti.ner.denomination.DenominationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.denomination.DenominationReferent.class);
                for (com.pullenti.ner.Slot s : dr.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.denomination.DenominationReferent.ATTR_VALUE)) {
                        if (res.value == null) 
                            res.value = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        else 
                            res.altValue = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                    }
                }
                return res;
            }
            if (!t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken) && _tryParseNum(t.getNext()) == null) {
                res = _tryParseModel(t);
                return res;
            }
        }
        if (t.chars.isLatinLetter() && t.isWhitespaceBefore()) {
            res = _new1431(t, t, com.pullenti.ner.goods.GoodAttrType.PROPER);
            for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null; ttt = ttt.getNext()) {
                if (ttt.chars.isLatinLetter() && ttt.chars.equals(t.chars)) 
                    res.setEndToken(ttt);
                else if (((ttt instanceof com.pullenti.ner.TextToken) && !ttt.isLetters() && ttt.getNext() != null) && ttt.getNext().chars.isLatinLetter()) {
                }
                else 
                    break;
            }
            if (res.getEndToken().isWhitespaceAfter()) {
                res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.NO);
                if (res.value.indexOf(' ') > 0) 
                    res.altValue = res.value.replace(" ", "");
                if (res.getLengthChar() < 2) 
                    return null;
                return res;
            }
        }
        String pref = null;
        com.pullenti.ner.Token t0 = t;
        if (t.getMorph()._getClass().isPreposition() && t.getNext() != null && t.getNext().chars.isLetter()) {
            pref = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
            t = t.getNext();
            if ((t.isCommaAnd() && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getMorph()._getClass().isPreposition()) && t.getNext().getNext() != null) {
                pref = (pref + " И " + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false));
                t = t.getNext().getNext();
            }
        }
        else if ((((((t.isValue("Д", null) || t.isValue("Б", null) || t.isValue("Н", null)) || t.isValue("H", null))) && t.getNext() != null && t.getNext().isCharOf("\\/")) && !t.isWhitespaceAfter() && !t.getNext().isWhitespaceAfter()) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
            pref = (t.isValue("Д", null) ? "ДЛЯ" : (t.isValue("Б", null) ? "БЕЗ" : "НЕ"));
            t = t.getNext().getNext();
            if (com.pullenti.unisharp.Utils.stringsEq(pref, "НЕ")) {
                GoodAttrToken re = _tryParse_(t, key, false, isChars);
                if (re != null && re.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER && re.value != null) {
                    re.setBeginToken(t0);
                    re.value = "НЕ" + re.value;
                    if (re.altValue != null) 
                        re.altValue = "НЕ" + re.altValue;
                    return re;
                }
            }
        }
        if (pref != null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null && t.getMorphClassInDictionary().isAdverb()) 
                npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && ((npt.chars.isAllLower() || npt.chars.isAllUpper())) && npt.chars.isCyrillicLetter()) {
                GoodAttrToken re = _new1431(t0, npt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase();
                for (com.pullenti.ner.Token tt = npt.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isNewlineBefore() || tt.isChar(';')) 
                        break;
                    if (tt.isCommaAnd() && tt.getNext() != null) 
                        tt = tt.getNext();
                    com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                    if (npt1 == null && tt.getMorphClassInDictionary().isAdverb()) 
                        npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt1 == null) 
                        break;
                    if (!npt1.chars.equals(npt.chars)) 
                        break;
                    if (tt.getPrevious().isComma()) {
                        if (!cas.isUndefined() && ((com.pullenti.morph.MorphCase.ooBitand(cas, npt1.getMorph().getCase()))).isUndefined()) 
                            break;
                        GoodAttrToken re2 = _tryParseNum(tt);
                        if (re2 != null && re2.typ == com.pullenti.ner.goods.GoodAttrType.NUMERIC) 
                            break;
                    }
                    tt = re.setEndToken(npt1.getEndToken());
                    cas = npt1.getMorph().getCase();
                }
                re.value = com.pullenti.ner.core.MiscHelper.getTextValue(npt.getBeginToken(), re.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (npt.getEndToken() == re.getEndToken() && npt.adjectives.size() == 0) {
                    if (com.pullenti.unisharp.Utils.stringsEq(pref, "ДЛЯ") || com.pullenti.unisharp.Utils.stringsEq(pref, "ИЗ")) {
                        String noun = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> grs = com.pullenti.semantic.utils.DerivateService.findDerivates(noun, true, null);
                        if (grs != null) {
                            for (com.pullenti.semantic.utils.DerivateGroup g : grs) {
                                if (re.altValue != null) 
                                    break;
                                for (com.pullenti.semantic.utils.DerivateWord v : g.words) {
                                    if (v._class.isAdjective()) {
                                        re.altValue = v.spelling;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (pref != null) 
                    re.value = (pref + " " + re.value);
                return re;
            }
        }
        if (t.chars.isCyrillicLetter() || (t instanceof com.pullenti.ner.NumberToken)) {
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value())), 0, null);
            if (npt1 != null) {
                if (((npt1.noun.getBeginToken().isValue("СОРТ", null) || npt1.noun.getBeginToken().isValue("КЛАСС", null) || npt1.noun.getBeginToken().isValue("ГРУППА", null)) || npt1.noun.getBeginToken().isValue("КАТЕГОРИЯ", null) || npt1.noun.getBeginToken().isValue("ТИП", null)) || npt1.noun.getBeginToken().isValue("ПОДТИП", null)) {
                    res = _new1431(t, npt1.getEndToken(), com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                    res.value = npt1.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (res.getBeginToken() == res.getEndToken()) {
                        if (t.getNext() != null && t.getNext().isValue("ВЫСШ", null)) {
                            res.value = ((((short)((npt1.noun.getBeginToken().getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? "ВЫСШАЯ" : "ВЫСШИЙ ")) + res.value;
                            res.setEndToken(t.getNext());
                            if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
                                res.setEndToken(res.getEndToken().getNext());
                        }
                        else if (t.getWhitespacesAfterCount() < 2) {
                            if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                                res.value = (com.pullenti.ner.core.NumberHelper.getNumberAdjective(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue(), (((short)((npt1.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE), com.pullenti.morph.MorphNumber.SINGULAR) + " " + t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                                res.setEndToken(t.getNext());
                            }
                            else {
                                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t.getNext());
                                if (rom != null && rom.getIntValue() != null) {
                                    res.value = (com.pullenti.ner.core.NumberHelper.getNumberAdjective(rom.getIntValue(), (((short)((npt1.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE), com.pullenti.morph.MorphNumber.SINGULAR) + " " + t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                                    res.setEndToken(rom.getEndToken());
                                }
                            }
                        }
                    }
                    if (res.getBeginToken() != res.getEndToken()) 
                        return res;
                }
            }
            if (((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && (t.getNext() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesAfterCount() < 2)) {
                if (((t.getNext().isValue("СОРТ", null) || t.getNext().isValue("КЛАСС", null) || t.getNext().isValue("ГРУППА", null)) || t.getNext().isValue("КАТЕГОРИЯ", null) || t.getNext().isValue("ТИП", null)) || t.getNext().isValue("ПОДТИП", null)) {
                    res = _new1431(t, t.getNext(), com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                    res.value = (com.pullenti.ner.core.NumberHelper.getNumberAdjective(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue(), (((short)((t.getNext().getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE), com.pullenti.morph.MorphNumber.SINGULAR) + " " + t.getNext().getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    return res;
                }
            }
            if (npt1 != null && npt1.noun.getBeginToken().isValue("ХАРАКТЕРИСТИКА", null)) {
                com.pullenti.ner.Token t11 = npt1.getEndToken().getNext();
                if (t11 != null && ((t11.isValue("УКАЗАТЬ", null) || t11.isValue("УКАЗЫВАТЬ", null)))) {
                    res = _new1431(t, t11, com.pullenti.ner.goods.GoodAttrType.UNDEFINED);
                    com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t11.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                    if (npt2 != null) 
                        res.setEndToken(npt2.getEndToken());
                    else if (t11.getNext() != null && t11.getNext().isValue("В", null)) {
                        res.setEndToken(t11.getNext());
                        if (res.getEndToken().getNext() != null) 
                            res.setEndToken(res.getEndToken().getNext());
                    }
                    return res;
                }
            }
        }
        if ((t.chars.isCyrillicLetter() && pref == null && (t instanceof com.pullenti.ner.TextToken)) && t.getMorph()._getClass().isAdjective()) {
            if (t.getMorph().containsAttr("к.ф.", null) && t.getNext() != null && t.getNext().isHiphen()) {
                String val = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                com.pullenti.ner.Token tt;
                for (tt = t.getNext().getNext(); tt != null; ) {
                    if (((tt instanceof com.pullenti.ner.TextToken) && tt.getNext() != null && tt.getNext().isHiphen()) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                        val = (val + "-" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
                        tt = tt.getNext().getNext();
                        continue;
                    }
                    GoodAttrToken re = _tryParse_(tt, key, false, isChars);
                    if (re != null && re.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER) {
                        re.setBeginToken(t);
                        re.value = (val + "-" + re.value);
                        return re;
                    }
                    break;
                }
            }
            boolean _isChar = false;
            if (key != null && t.getMorph().checkAccord(key.getMorph(), false, false) && ((t.chars.isAllLower() || com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)))) 
                _isChar = true;
            else if (t.getMorphClassInDictionary().isAdjective() && !t.getMorph().containsAttr("неизм.", null)) 
                _isChar = true;
            if (_isChar && t.getMorph()._getClass().isVerb()) {
                if ((t.isValue("ПРЕДНАЗНАЧИТЬ", null) || t.isValue("ПРЕДНАЗНАЧАТЬ", null) || t.isValue("ИЗГОТОВИТЬ", null)) || t.isValue("ИЗГОТОВЛЯТЬ", null)) 
                    _isChar = false;
            }
            if (_isChar) {
                res = _new1431(t, t, com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                res.value = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
                return res;
            }
        }
        if ((t.chars.isCyrillicLetter() && pref == null && (t instanceof com.pullenti.ner.TextToken)) && t.getMorph()._getClass().isVerb()) {
            GoodAttrToken re = _tryParse_(t.getNext(), key, false, isChars);
            if (re != null && re.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER) {
                re.setBeginToken(t);
                re.altValue = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term + " " + re.value);
                return re;
            }
        }
        if (t.chars.isCyrillicLetter()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS, 0, null);
            if ((npt != null && npt.adjectives.size() > 0 && npt.adjectives.get(0).chars.isAllLower()) && !npt.noun.chars.isAllLower()) 
                npt = null;
            if (pref == null && npt != null && npt.noun.getEndToken().getMorphClassInDictionary().isAdjective()) 
                npt = null;
            if (npt != null && !npt.getEndToken().chars.isCyrillicLetter()) 
                npt = null;
            if (npt != null) {
                boolean isProp = false;
                if (pref != null) 
                    isProp = true;
                else if (npt.chars.isAllLower()) 
                    isProp = true;
                if (npt.adjectives.size() > 0 && pref == null) {
                    if (key == null) 
                        return _new1447(t0, npt.adjectives.get(0).getEndToken(), com.pullenti.ner.goods.GoodAttrType.CHARACTER, npt.adjectives.get(0).getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false));
                }
                if (pref == null && key != null && npt.noun.isValue(key.value, null)) {
                    if (npt.adjectives.size() == 0) 
                        return _new1433(t0, npt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.KEYWORD, npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
                    return _new1447(t0, npt.adjectives.get(0).getEndToken(), com.pullenti.ner.goods.GoodAttrType.CHARACTER, npt.adjectives.get(0).getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false));
                }
                if (isProp) {
                    res = _new1431(t0, npt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                    res.value = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    return res;
                }
                if (!npt.chars.isAllLower()) 
                    return _new1433(t0, npt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.PROPER, npt.getSourceText(), npt.getMorph());
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                if (((t.getMorphClassInDictionary().isAdjective() || t.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.ADJECTIVE))) && pref == null) 
                    return _new1433(t0, t, com.pullenti.ner.goods.GoodAttrType.CHARACTER, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).lemma, t.getMorph());
            }
            if ((t instanceof com.pullenti.ner.NumberToken) && pref != null) {
                GoodAttrToken num = _tryParseNum(t);
                if (num != null) {
                    num.setBeginToken(t0);
                    return num;
                }
            }
            if (pref != null && t.getMorph()._getClass().isAdjective() && (t instanceof com.pullenti.ner.TextToken)) {
                res = _new1431(t0, t, com.pullenti.ner.goods.GoodAttrType.CHARACTER);
                res.value = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.MASCULINE, false);
                return res;
            }
            if (pref != null && t.getNext() != null && t.getNext().isValue("WC", null)) 
                return _new1447(t, t.getNext(), com.pullenti.ner.goods.GoodAttrType.CHARACTER, "туалет");
            if (pref != null) 
                return null;
        }
        if (t != null && t.isValue("№", null) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
            return _new1447(t, t.getNext(), com.pullenti.ner.goods.GoodAttrType.MODEL, ("№" + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue()));
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
            if (t.getLengthChar() > 2 && ((!t.chars.isAllLower() || t.chars.isLatinLetter()))) 
                return _new1447(t, t, com.pullenti.ner.goods.GoodAttrType.PROPER, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
            return null;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                GoodAttrToken res1 = _tryParse_(t.getNext(), key, false, isChars);
                if (res1 != null && res1.getEndToken().getNext() == br.getEndToken()) {
                    if (res1.typ == com.pullenti.ner.goods.GoodAttrType.CHARACTER) 
                        res1.typ = com.pullenti.ner.goods.GoodAttrType.PROPER;
                    res1.setBeginToken(t);
                    res1.setEndToken(br.getEndToken());
                }
                else {
                    res1 = _new1431(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.goods.GoodAttrType.PROPER);
                    res1.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                }
                return res1;
            }
        }
        if (t.isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                if (t.getNext().isValue("ПРИЛОЖЕНИЕ", null)) 
                    return _new1431(t, br.getEndToken(), com.pullenti.ner.goods.GoodAttrType.UNDEFINED);
            }
        }
        GoodAttrToken nnn = _tryParseNum2(t);
        if (nnn != null) 
            return nnn;
        return null;
    }

    private static GoodAttrToken _tryParseModel(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        GoodAttrToken res = _new1431(t, t, com.pullenti.ner.goods.GoodAttrType.MODEL);
        StringBuilder tmp = new StringBuilder();
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isWhitespaceBefore() && tt != t) 
                break;
            if (tt instanceof com.pullenti.ner.NumberToken) {
                if (tmp.length() > 0 && Character.isDigit(tmp.charAt(tmp.length() - 1))) 
                    tmp.append('-');
                tmp.append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getSourceText());
                res.setEndToken(tt);
                continue;
            }
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                com.pullenti.ner.denomination.DenominationReferent den = (com.pullenti.ner.denomination.DenominationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.denomination.DenominationReferent.class);
                if (den != null) {
                    tmp.append(den.getValue());
                    continue;
                }
            }
            if (!(tt instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!tt.chars.isLetter()) {
                if (tt.isCharOf("\\/-:")) {
                    if (tt.isCharOf(":") && tt.isWhitespaceAfter()) 
                        break;
                    tmp.append('-');
                }
                else if (tt.isChar('.')) {
                    if (tt.isWhitespaceAfter()) 
                        break;
                    tmp.append('.');
                }
                else 
                    break;
            }
            else 
                tmp.append(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term);
            res.setEndToken(tt);
        }
        res.value = tmp.toString();
        return res;
    }

    private static GoodAttrToken _tryParseNum(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.measure.internal.MeasureToken mt = com.pullenti.ner.measure.internal.MeasureToken.tryParse(t, null, true, false, false, false);
        if (mt == null) 
            mt = com.pullenti.ner.measure.internal.MeasureToken.tryParseMinimal(t, null, false);
        if (mt != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> mrs = mt.createRefenetsTokensWithRegister(null, false);
            if (mrs != null && mrs.size() > 0 && (mrs.get(mrs.size() - 1).referent instanceof com.pullenti.ner.measure.MeasureReferent)) {
                com.pullenti.ner.measure.MeasureReferent mr = (com.pullenti.ner.measure.MeasureReferent)com.pullenti.unisharp.Utils.cast(mrs.get(mrs.size() - 1).referent, com.pullenti.ner.measure.MeasureReferent.class);
                GoodAttrToken res = _new1437(t, mt.getEndToken(), com.pullenti.ner.goods.GoodAttrType.NUMERIC, mr.getStringValue(com.pullenti.ner.measure.MeasureReferent.ATTR_NAME));
                res.value = mr.toStringEx(true, null, 0);
                return res;
            }
        }
        java.util.ArrayList<com.pullenti.ner.measure.internal.NumbersWithUnitToken> mts = com.pullenti.ner.measure.internal.NumbersWithUnitToken.tryParseMulti(t, null, com.pullenti.ner.measure.internal.NumberWithUnitParseAttr.NO);
        if ((mts != null && mts.size() == 1 && mts.get(0).units != null) && mts.get(0).units.size() > 0) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> mrs = mts.get(0).createRefenetsTokensWithRegister(null, null, true);
            com.pullenti.ner.ReferentToken mr = mrs.get(mrs.size() - 1);
            GoodAttrToken res = _new1431(t, mr.getEndToken(), com.pullenti.ner.goods.GoodAttrType.NUMERIC);
            res.value = mr.referent.toStringEx(true, null, 0);
            return res;
        }
        return null;
    }

    private static GoodAttrToken _tryParseNum2(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.NumberToken) || ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() == null) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_NumSuff.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && (t.getWhitespacesAfterCount() < 3)) {
            GoodAttrToken res = _new1431(t, tok.getEndToken(), com.pullenti.ner.goods.GoodAttrType.NUMERIC);
            res.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue() + tok.termin.getCanonicText().toLowerCase();
            if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
                res.setEndToken(res.getEndToken().getNext());
            return res;
        }
        com.pullenti.ner.NumberToken num = com.pullenti.ner.core.NumberHelper.tryParseRealNumber(t, true, false);
        if (num != null) {
            com.pullenti.ner.Token tt = num.getEndToken();
            if (tt instanceof com.pullenti.ner.MetaToken) {
                if (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getEndToken().isValue("СП", null)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "1")) 
                        return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "односпальный");
                    if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "1.5")) 
                        return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "полутораспальный");
                    if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "2")) 
                        return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "вдухспальный");
                }
            }
            tt = tt.getNext();
            if (tt != null && tt.isHiphen()) 
                tt = tt.getNext();
            if (tt != null && tt.isValue("СП", null)) {
                if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "1")) 
                    return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "односпальный");
                if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "1.5")) 
                    return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "полутораспальный");
                if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "2")) 
                    return _new1447(t, tt, com.pullenti.ner.goods.GoodAttrType.CHARACTER, "вдухспальный");
            }
            return _new1447(t, num.getEndToken(), com.pullenti.ner.goods.GoodAttrType.NUMERIC, num.getValue());
        }
        return null;
    }

    private static GoodAttrToken _tryParseChars(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null) 
            t1 = npt.getEndToken();
        else if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() > 2 && t.getMorphClassInDictionary().isUndefined()) && !t.chars.isAllLower()) 
            t1 = t;
        if (t1 == null) 
            return null;
        com.pullenti.ner.Token t11 = t1;
        com.pullenti.ner.Token t2 = null;
        for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt) || tt.isChar(';')) 
                break;
            if (tt.isChar(':') || tt.isHiphen()) {
                t2 = tt.getNext();
                break;
            }
            if (tt.isValue("ДА", null) || tt.isValue("НЕТ", null)) {
                t2 = tt;
                break;
            }
            com.pullenti.ner.core.VerbPhraseToken vvv = com.pullenti.ner.core.VerbPhraseHelper.tryParse(tt, false, false, false);
            if (vvv != null) {
                t2 = vvv.getEndToken().getNext();
                break;
            }
            t1 = tt;
        }
        if (t2 == null) {
            if (t11.getNext() != null && t11.getNext().getMorphClassInDictionary().isAdjective() && com.pullenti.ner.core.NounPhraseHelper.tryParse(t11.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null) == null) {
                t1 = t11;
                t2 = t11.getNext();
            }
        }
        if (t2 == null) 
            return null;
        com.pullenti.ner.Token t3 = t2;
        for (com.pullenti.ner.Token tt = t2; tt != null; tt = tt.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                break;
            if (tt.isChar(';')) 
                break;
            t3 = tt;
        }
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.NO);
        String val = com.pullenti.ner.core.MiscHelper.getTextValue(t2, (t3.isChar('.') ? t3.getPrevious() : t3), com.pullenti.ner.core.GetTextAttr.NO);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(val)) 
            return null;
        return _new1470(t, t3, com.pullenti.ner.goods.GoodAttrType.CHARACTER, _name, val);
    }

    private static com.pullenti.ner.core.TerminCollection m_NumSuff;

    private static com.pullenti.ner.core.TerminCollection m_StdAbbrs;

    private static com.pullenti.ner.denomination.DenominationAnalyzer m_DenomAn;

    private static boolean m_Inited = false;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("ПР", null, false);
        t.addVariant("ПРЕДМЕТ", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("ШТ", null, false);
        t.addVariant("ШТУКА", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("УП", null, false);
        t.addVariant("УПАКОВКА", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("ЯЩ", null, false);
        t.addVariant("ЯЩИК", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("КОРОБ", null, false);
        t.addVariant("КОРОБКА", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("БУТ", null, false);
        t.addVariant("БУТЫЛКА", false);
        m_NumSuff.add(t);
        t = new com.pullenti.ner.core.Termin("МЕШ", null, false);
        t.addVariant("МЕШОК", false);
        m_NumSuff.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЕРШ", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addVariant("ЕРШИК", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("КОНДИЦИОНЕР", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addVariant("КОНДИЦ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("УДЛИНИТЕЛЬ", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addAbridge("УДЛ-ЛЬ");
        t.addAbridge("УДЛИН-ЛЬ");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("УСТРОЙСТВО", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addAbridge("УСТР-ВО");
        t.addAbridge("УСТР.");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПРОКЛАДКИ", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addVariant("ПРОКЛ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ДЕЗОДОРАНТ", com.pullenti.ner.goods.GoodAttrType.KEYWORD);
        t.addVariant("ДЕЗ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ОХЛАЖДЕННЫЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("ОХЛ", false);
        t.addVariant("ОХЛАЖД", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("МЕДИЦИНСКИЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("МЕД", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("СТЕРИЛЬНЫЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("СТЕР", false);
        t.addVariant("СТ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ХЛОПЧАТОБУМАЖНЫЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addAbridge("Х/Б");
        t.addAbridge("ХБ");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ДЕТСКИЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("ДЕТ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("МУЖСКОЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("МУЖ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЖЕНСКИЙ", com.pullenti.ner.goods.GoodAttrType.CHARACTER);
        t.addVariant("ЖЕН", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("СТРАНА", com.pullenti.ner.goods.GoodAttrType.REFERENT);
        t.addVariant("СТРАНА ПРОИСХОЖДЕНИЯ", false);
        t.addVariant("ПРОИСХОЖДЕНИЕ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("ПРОИЗВОДИТЕЛЬ", com.pullenti.ner.goods.GoodAttrType.REFERENT);
        t.addAbridge("ПР-ЛЬ");
        t.addAbridge("ПРОИЗВ-ЛЬ");
        t.addAbridge("ПРОИЗВ.");
        t.addVariant("ПРОИЗВОДСТВО", false);
        t.addAbridge("ПР-ВО");
        t.addVariant("ПРОИЗВЕСТИ", false);
        t.addVariant("КОМПАНИЯ", false);
        t.addVariant("ФИРМА", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("ТОВАРНЫЙ ЗНАК", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("КАТАЛОЖНЫЙ НОМЕР", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "");
        t.addVariant("НОМЕР В КАТАЛОГЕ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("МАРКА", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("ФИРМА", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("МОДЕЛЬ", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "");
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new102("НЕТ", com.pullenti.ner.goods.GoodAttrType.UNDEFINED, "NO");
        t.addVariant("ОТСУТСТВОВАТЬ", false);
        t.addVariant("НЕ ИМЕТЬ", false);
        m_StdAbbrs.add(t);
        t = com.pullenti.ner.core.Termin._new100("БОЛЕЕ", com.pullenti.ner.goods.GoodAttrType.UNDEFINED);
        t.addVariant("МЕНЕЕ", false);
        t.addVariant("НЕ БОЛЕЕ", false);
        t.addVariant("НЕ МЕНЕЕ", false);
        m_StdAbbrs.add(t);
    }

    public static GoodAttrToken _new1429(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.value = _arg3;
        return res;
    }

    public static GoodAttrToken _new1430(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, com.pullenti.ner.Referent _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ref = _arg4;
        return res;
    }

    public static GoodAttrToken _new1431(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static GoodAttrToken _new1433(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static GoodAttrToken _new1434(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Referent _arg3, com.pullenti.ner.goods.GoodAttrType _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.ref = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static GoodAttrToken _new1435(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.ReferentToken _arg3, com.pullenti.ner.goods.GoodAttrType _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.refTok = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static GoodAttrToken _new1437(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, String _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.name = _arg4;
        return res;
    }

    public static GoodAttrToken _new1439(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static GoodAttrToken _new1447(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, String _arg4) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static GoodAttrToken _new1470(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.goods.GoodAttrType _arg3, String _arg4, String _arg5) {
        GoodAttrToken res = new GoodAttrToken(_arg1, _arg2);
        res.typ = _arg3;
        res.name = _arg4;
        res.value = _arg5;
        return res;
    }

    public GoodAttrToken() {
        super();
    }
    
    static {
        m_NumSuff = new com.pullenti.ner.core.TerminCollection();
        m_StdAbbrs = new com.pullenti.ner.core.TerminCollection();
        m_DenomAn = new com.pullenti.ner.denomination.DenominationAnalyzer();
    }
}
