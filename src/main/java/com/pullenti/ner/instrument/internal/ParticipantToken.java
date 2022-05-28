/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class ParticipantToken extends com.pullenti.ner.MetaToken {

    public ParticipantToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        kind = Kinds.UNDEFINED;
    }

    public String typ;

    public Kinds kind = Kinds.UNDEFINED;

    public java.util.ArrayList<com.pullenti.ner.Referent> parts = null;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(kind.toString()).append(": ").append(((typ != null ? typ : "?")));
        if (parts != null) {
            for (com.pullenti.ner.Referent p : parts) {
                res.append("; ").append(p.toStringEx(true, null, 0));
            }
        }
        return res.toString();
    }

    public static ParticipantToken tryAttach(com.pullenti.ner.Token t, com.pullenti.ner.instrument.InstrumentParticipantReferent p1, com.pullenti.ner.instrument.InstrumentParticipantReferent p2, boolean isContract) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token tt = t;
        boolean br = false;
        if (p1 == null && p2 == null && isContract) {
            com.pullenti.ner.Referent r1 = t.getReferent();
            if ((r1 != null && t.getNext() != null && t.getNext().isCommaAnd()) && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.Referent r2 = t.getNext().getNext().getReferent();
                if (com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), r2.getTypeName())) {
                    com.pullenti.ner.Token ttt = t.getNext().getNext().getNext();
                    java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    refs.add(r1);
                    refs.add(r2);
                    for (; ttt != null; ttt = ttt.getNext()) {
                        if ((ttt.isCommaAnd() && ttt.getNext() != null && ttt.getNext().getReferent() != null) && com.pullenti.unisharp.Utils.stringsEq(ttt.getNext().getReferent().getTypeName(), r1.getTypeName())) {
                            ttt = ttt.getNext();
                            if (!refs.contains(ttt.getReferent())) 
                                refs.add(ttt.getReferent());
                            continue;
                        }
                        break;
                    }
                    for (; ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isComma() || ttt.getMorph()._getClass().isPreposition()) 
                            continue;
                        if ((ttt.isValue("ИМЕНОВАТЬ", null) || ttt.isValue("ДАЛЬНЕЙШИЙ", null) || ttt.isValue("ДАЛЕЕ", null)) || ttt.isValue("ТЕКСТ", null)) 
                            continue;
                        if (ttt.isValue("ДОГОВАРИВАТЬСЯ", null)) 
                            continue;
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt != null && npt.noun.isValue("СТОРОНА", null) && npt.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                            ParticipantToken re = _new1727(t, npt.getEndToken(), Kinds.NAMEDASPARTS);
                            re.parts = refs;
                            return re;
                        }
                        break;
                    }
                }
            }
            if ((r1 instanceof com.pullenti.ner._org.OrganizationReferent) || (r1 instanceof com.pullenti.ner.person.PersonReferent)) {
                boolean hasBr = false;
                boolean hasNamed = false;
                if (r1 instanceof com.pullenti.ner.person.PersonReferent) {
                    if (t.getPrevious() != null && t.getPrevious().isValue("ЛИЦО", null)) 
                        return null;
                }
                else if (t.getPrevious() != null && ((t.getPrevious().isValue("ВЫДАВАТЬ", null) || t.getPrevious().isValue("ВЫДАТЬ", null)))) 
                    return null;
                for (com.pullenti.ner.Token ttt = ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken(); ttt != null && (ttt.getEndChar() < t.getEndChar()); ttt = ttt.getNext()) {
                    if (ttt.isChar('(')) 
                        hasBr = true;
                    else if ((ttt.isValue("ИМЕНОВАТЬ", null) || ttt.isValue("ДАЛЬНЕЙШИЙ", null) || ttt.isValue("ДАЛЕЕ", null)) || ttt.isValue("ТЕКСТ", null)) 
                        hasNamed = true;
                    else if ((ttt.isComma() || ttt.getMorph()._getClass().isPreposition() || ttt.isHiphen()) || ttt.isChar(':')) {
                    }
                    else if (ttt instanceof com.pullenti.ner.ReferentToken) {
                    }
                    else if (hasBr || hasNamed) {
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                        if (npt == null) 
                            break;
                        if (hasBr) {
                            if (npt.getEndToken().getNext() == null || !npt.getEndToken().getNext().isChar(')')) 
                                break;
                        }
                        if (!hasNamed) {
                            if (m_Ontology.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO) == null) 
                                break;
                        }
                        ParticipantToken re = _new1727(t, t, Kinds.NAMEDAS);
                        re.typ = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        re.parts = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        re.parts.add(r1);
                        return re;
                    }
                }
                hasBr = false;
                hasNamed = false;
                com.pullenti.ner.Token endSide = null;
                com.pullenti.ner.core.BracketSequenceToken brr = null;
                java.util.ArrayList<com.pullenti.ner.Referent> addRefs = null;
                for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null; ttt = ttt.getNext()) {
                    if ((ttt instanceof com.pullenti.ner.NumberToken) && (ttt.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt.getNext(), com.pullenti.ner.TextToken.class)).term, "СТОРОНЫ")) {
                        endSide = (ttt = ttt.getNext());
                        if (ttt.getNext() != null && ttt.getNext().isComma()) 
                            ttt = ttt.getNext();
                        if (ttt.getNext() != null && ttt.getNext().isAnd()) 
                            break;
                    }
                    if (brr != null && ttt.getBeginChar() > brr.getEndChar()) 
                        brr = null;
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt, false, false)) {
                        brr = com.pullenti.ner.core.BracketHelper.tryParse(ttt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (brr != null && (brr.getLengthChar() < 7) && ttt.isChar('(')) {
                            ttt = brr.getEndToken();
                            brr = null;
                            continue;
                        }
                    }
                    else if ((ttt.isValue("ИМЕНОВАТЬ", null) || ttt.isValue("ДАЛЬНЕЙШИЙ", null) || ttt.isValue("ДАЛЕЕ", null)) || ttt.isValue("ТЕКСТ", null)) 
                        hasNamed = true;
                    else if ((ttt.isComma() || ttt.getMorph()._getClass().isPreposition() || ttt.isHiphen()) || ttt.isChar(':')) {
                    }
                    else if (brr != null || hasNamed) {
                        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt, true, false)) 
                            ttt = ttt.getNext();
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0, null);
                        String typ22 = null;
                        if (npt != null) {
                            ttt = npt.getEndToken();
                            if (npt.getEndToken().isValue("ДОГОВОР", null)) 
                                continue;
                        }
                        else {
                            com.pullenti.ner.core.TerminToken ttok = null;
                            if (ttt instanceof com.pullenti.ner.MetaToken) 
                                ttok = m_Ontology.tryParse(((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(ttt, com.pullenti.ner.MetaToken.class)).getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                            if (ttok != null) 
                                typ22 = ttok.termin.getCanonicText();
                            else if (hasNamed && ttt.getMorph()._getClass().isAdjective()) 
                                typ22 = ttt.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                            else if (brr != null) 
                                continue;
                            else 
                                break;
                        }
                        if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(ttt.getNext(), true, null, false)) 
                            ttt = ttt.getNext();
                        if (brr != null) {
                            if (ttt.getNext() == null) {
                                ttt = brr.getEndToken();
                                continue;
                            }
                            ttt = ttt.getNext();
                        }
                        if (!hasNamed && typ22 == null) {
                            if (m_Ontology.tryParse(npt.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) == null) 
                                break;
                        }
                        ParticipantToken re = _new1727(t, ttt, Kinds.NAMEDAS);
                        re.typ = (typ22 != null ? typ22 : npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                        re.parts = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        re.parts.add(r1);
                        return re;
                    }
                    else if ((ttt.isValue("ЗАРЕГИСТРИРОВАННЫЙ", null) || ttt.isValue("КАЧЕСТВО", null) || ttt.isValue("ПРОЖИВАЮЩИЙ", null)) || ttt.isValue("ЗАРЕГ", null)) {
                    }
                    else if (ttt.getReferent() == r1) {
                    }
                    else if ((ttt.getReferent() instanceof com.pullenti.ner.person.PersonIdentityReferent) || (ttt.getReferent() instanceof com.pullenti.ner.address.AddressReferent)) {
                        if (addRefs == null) 
                            addRefs = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        addRefs.add(ttt.getReferent());
                    }
                    else {
                        com.pullenti.ner.ReferentToken prr = ttt.kit.processReferent("PERSONPROPERTY", ttt, null);
                        if (prr != null) {
                            ttt = prr.getEndToken();
                            continue;
                        }
                        if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            continue;
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt != null) {
                            if ((npt.noun.isValue("МЕСТО", null) || npt.noun.isValue("ЖИТЕЛЬСТВО", null) || npt.noun.isValue("ПРЕДПРИНИМАТЕЛЬ", null)) || npt.noun.isValue("ПОЛ", null) || npt.noun.isValue("РОЖДЕНИЕ", null)) {
                                ttt = npt.getEndToken();
                                continue;
                            }
                        }
                        if (ttt.isNewlineBefore()) 
                            break;
                        if (ttt.getLengthChar() < 3) 
                            continue;
                        com.pullenti.morph.MorphClass mc = ttt.getMorphClassInDictionary();
                        if (mc.isAdverb() || mc.isAdjective()) 
                            continue;
                        if (ttt.chars.isAllUpper()) 
                            continue;
                        break;
                    }
                }
                if (endSide != null || ((addRefs != null && t.getPrevious() != null && t.getPrevious().isAnd()))) {
                    ParticipantToken re = _new1727(t, (endSide != null ? endSide : t), Kinds.NAMEDAS);
                    re.typ = null;
                    re.parts = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    re.parts.add(r1);
                    if (addRefs != null) 
                        com.pullenti.unisharp.Utils.addToArrayList(re.parts, addRefs);
                    return re;
                }
            }
            com.pullenti.ner.core.TerminToken too = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (too != null) {
                if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && t.getPrevious().isValue("ЛИЦО", null)) 
                    too = null;
            }
            if (too != null && too.termin.tag != null && com.pullenti.unisharp.Utils.stringsNe(too.termin.getCanonicText(), "СТОРОНА")) {
                com.pullenti.ner.Token tt1 = too.getEndToken().getNext();
                if (tt1 != null) {
                    if (tt1.isHiphen() || tt1.isChar(':')) 
                        tt1 = tt1.getNext();
                }
                if (tt1 instanceof com.pullenti.ner.ReferentToken) {
                    r1 = tt1.getReferent();
                    if ((r1 instanceof com.pullenti.ner.person.PersonReferent) || (r1 instanceof com.pullenti.ner._org.OrganizationReferent)) {
                        ParticipantToken re = _new1727(t, tt1, Kinds.NAMEDAS);
                        re.typ = too.termin.getCanonicText();
                        re.parts = new java.util.ArrayList<com.pullenti.ner.Referent>();
                        re.parts.add(r1);
                        return re;
                    }
                }
            }
        }
        String addTyp1 = (p1 == null ? null : p1.getTyp());
        String addTyp2 = (p2 == null ? null : p2.getTyp());
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false) && tt.getNext() != null) {
            br = true;
            tt = tt.getNext();
        }
        com.pullenti.ner.core.Termin term1 = null;
        com.pullenti.ner.core.Termin term2 = null;
        if (addTyp1 != null && addTyp1.indexOf(' ') > 0 && !addTyp1.startsWith("СТОРОНА")) 
            term1 = new com.pullenti.ner.core.Termin(addTyp1, null, false);
        if (addTyp2 != null && addTyp2.indexOf(' ') > 0 && !addTyp2.startsWith("СТОРОНА")) 
            term2 = new com.pullenti.ner.core.Termin(addTyp2, null, false);
        boolean named = false;
        String _typ = null;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.Token t0 = tt;
        for (; tt != null; tt = tt.getNext()) {
            if (tt.getMorph()._getClass().isPreposition() && _typ != null) 
                continue;
            if (tt.isCharOf("(:)") || tt.isHiphen()) 
                continue;
            if (tt.isTableControlChar()) 
                break;
            if (tt.isNewlineBefore() && tt != t0) {
                if (tt instanceof com.pullenti.ner.NumberToken) 
                    break;
                if ((tt instanceof com.pullenti.ner.TextToken) && (tt.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                    if (tt.getPrevious().isValue(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, null)) 
                        break;
                }
            }
            if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) 
                continue;
            com.pullenti.ner.core.TerminToken tok = (m_Ontology != null ? m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) : null);
            if (tok != null && (tt.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                if (tt.getPrevious().isValue("ЛИЦО", null)) 
                    return null;
            }
            if (tok == null) {
                if (addTyp1 != null && ((com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError(addTyp1, tt) || (((tt instanceof com.pullenti.ner.MetaToken) && ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken().isValue(addTyp1, null)))))) {
                    if (_typ != null) {
                        if (!_isTypesEqual(addTyp1, _typ)) 
                            break;
                    }
                    _typ = addTyp1;
                    t1 = tt;
                    continue;
                }
                if (addTyp2 != null && ((com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError(addTyp2, tt) || (((tt instanceof com.pullenti.ner.MetaToken) && ((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class)).getBeginToken().isValue(addTyp2, null)))))) {
                    if (_typ != null) {
                        if (!_isTypesEqual(addTyp2, _typ)) 
                            break;
                    }
                    _typ = addTyp2;
                    t1 = tt;
                    continue;
                }
                if (tt.chars.isLetter()) {
                    if (term1 != null) {
                        com.pullenti.ner.core.TerminToken tok1 = term1.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok1 != null) {
                            if (_typ != null) {
                                if (!_isTypesEqual(addTyp1, _typ)) 
                                    break;
                            }
                            _typ = addTyp1;
                            t1 = (tt = tok1.getEndToken());
                            continue;
                        }
                    }
                    if (term2 != null) {
                        com.pullenti.ner.core.TerminToken tok2 = term2.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null) {
                            if (_typ != null) {
                                if (!_isTypesEqual(addTyp2, _typ)) 
                                    break;
                            }
                            _typ = addTyp2;
                            t1 = (tt = tok2.getEndToken());
                            continue;
                        }
                    }
                    if (named && tt.getMorphClassInDictionary().isNoun()) {
                        if (!tt.chars.isAllLower() || com.pullenti.ner.core.BracketHelper.isBracket(tt.getPrevious(), true)) {
                            if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, false) == null) {
                                String val = tt.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                if (_typ != null) {
                                    if (!_isTypesEqual(_typ, val)) 
                                        break;
                                }
                                _typ = val;
                                t1 = tt;
                                continue;
                            }
                        }
                    }
                }
                if (named && _typ == null && isContract) {
                    if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isCyrillicLetter() && tt.chars.isCapitalUpper()) {
                        com.pullenti.morph.MorphClass dc = tt.getMorphClassInDictionary();
                        if (dc.isUndefined() || dc.isNoun()) {
                            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
                            boolean ok = true;
                            if (dt != null) 
                                ok = false;
                            else if (tt.isValue("СТОРОНА", null)) 
                                ok = false;
                            if (ok) {
                                _typ = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
                                t1 = tt;
                                continue;
                            }
                        }
                        if (dc.isAdjective()) {
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt != null && npt.adjectives.size() > 0 && npt.noun.getMorphClassInDictionary().isNoun()) {
                                _typ = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                t1 = npt.getEndToken();
                                continue;
                            }
                        }
                    }
                }
                if (tt == t) 
                    break;
                if ((tt instanceof com.pullenti.ner.NumberToken) || tt.isChar('.')) 
                    break;
                if (tt.getLengthChar() < 4) {
                    if (_typ != null) 
                        continue;
                }
                break;
            }
            if (tok.termin.tag == null) 
                named = true;
            else {
                if (_typ != null) 
                    break;
                if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "СТОРОНА")) {
                    com.pullenti.ner.Token tt1 = tt.getNext();
                    if (tt1 != null && tt1.isHiphen()) 
                        tt1 = tt1.getNext();
                    if (!(tt1 instanceof com.pullenti.ner.NumberToken)) 
                        break;
                    if (tt1.isNewlineBefore()) 
                        break;
                    _typ = (tok.termin.getCanonicText() + " " + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.NumberToken.class)).getValue());
                    t1 = tt1;
                }
                else {
                    _typ = tok.termin.getCanonicText();
                    t1 = tok.getEndToken();
                }
                break;
            }
            tt = tok.getEndToken();
        }
        if (_typ == null) 
            return null;
        if (!named && t1 != t && !_typ.startsWith("СТОРОНА")) {
            if (!_isTypesEqual(_typ, addTyp1) && !_isTypesEqual(_typ, addTyp2)) 
                return null;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) {
            t1 = t1.getNext();
            if (!t.isWhitespaceBefore() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)) 
                t = t.getPrevious();
        }
        else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), true, t, true)) 
            t1 = t1.getNext();
        if (br && t1.getNext() != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
            t1 = t1.getNext();
        ParticipantToken res = _new1732(t, t1, (named ? Kinds.NAMEDAS : Kinds.PURE), _typ);
        if (t.isChar(':')) 
            res.setBeginToken(t.getNext());
        return res;
    }

    private static boolean _isTypesEqual(String t1, String t2) {
        if (com.pullenti.unisharp.Utils.stringsEq(t1, t2)) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(t1, "ЗАЙМОДАВЕЦ") || com.pullenti.unisharp.Utils.stringsEq(t1, "ЗАИМОДАВЕЦ")) 
            t1 = "ЗАИМОДАТЕЛЬ";
        if (com.pullenti.unisharp.Utils.stringsEq(t2, "ЗАЙМОДАВЕЦ") || com.pullenti.unisharp.Utils.stringsEq(t2, "ЗАИМОДАВЕЦ")) 
            t2 = "ЗАИМОДАТЕЛЬ";
        if (com.pullenti.unisharp.Utils.stringsEq(t1, "ПРОДАВЕЦ")) 
            t1 = "ПОСТАВЩИК";
        if (com.pullenti.unisharp.Utils.stringsEq(t2, "ПРОДАВЕЦ")) 
            t2 = "ПОСТАВЩИК";
        if (com.pullenti.unisharp.Utils.stringsEq(t1, "ПОКУПАТЕЛЬ")) 
            t1 = "ЗАКАЗЧИК";
        if (com.pullenti.unisharp.Utils.stringsEq(t2, "ПОКУПАТЕЛЬ")) 
            t2 = "ЗАКАЗЧИК";
        return com.pullenti.unisharp.Utils.stringsEq(t1, t2);
    }

    public static com.pullenti.ner.ReferentToken tryAttachToExist(com.pullenti.ner.Token t, com.pullenti.ner.instrument.InstrumentParticipantReferent p1, com.pullenti.ner.instrument.InstrumentParticipantReferent p2) {
        if (t == null) 
            return null;
        if (t.getBeginChar() >= 7674 && (t.getBeginChar() < 7680)) {
        }
        ParticipantToken pp = ParticipantToken.tryAttach(t, p1, p2, false);
        com.pullenti.ner.instrument.InstrumentParticipantReferent p = null;
        com.pullenti.ner.ReferentToken rt = null;
        if (pp == null || pp.kind != Kinds.PURE) {
            com.pullenti.ner.Referent pers = t.getReferent();
            if ((pers instanceof com.pullenti.ner.person.PersonReferent) || (pers instanceof com.pullenti.ner.geo.GeoReferent) || (pers instanceof com.pullenti.ner._org.OrganizationReferent)) {
                if (p1 != null && p1.containsRef(pers)) 
                    p = p1;
                else if (p2 != null && p2.containsRef(pers)) 
                    p = p2;
                if (p != null) 
                    rt = new com.pullenti.ner.ReferentToken(p, t, t, null);
            }
        }
        else {
            if (p1 != null && _isTypesEqual(pp.typ, p1.getTyp())) 
                p = p1;
            else if (p2 != null && _isTypesEqual(pp.typ, p2.getTyp())) 
                p = p2;
            if (p != null) {
                rt = new com.pullenti.ner.ReferentToken(p, pp.getBeginToken(), pp.getEndToken(), null);
                if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isValue("ОТ", null)) 
                    rt.setBeginToken(rt.getBeginToken().getPrevious());
            }
        }
        if (rt == null) 
            return null;
        if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar(':')) {
            com.pullenti.ner.ReferentToken rt1 = tryAttachRequisites(rt.getEndToken().getNext().getNext(), p, (p == p1 ? p2 : p1), false);
            if (rt1 != null) {
                rt1.setBeginToken(rt.getBeginToken());
                return rt1;
            }
            rt.setEndToken(rt.getEndToken().getNext());
        }
        while (rt.getEndToken().getNext() != null && (rt.getEndToken().getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
            com.pullenti.ner._org.OrganizationReferent _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.getEndToken().getNext().getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
            if (rt.referent.findSlot(null, _org, true) != null) {
                rt.setEndToken(rt.getEndToken().getNext());
                continue;
            }
            break;
        }
        return rt;
    }

    public static com.pullenti.ner.ReferentToken tryAttachRequisites(com.pullenti.ner.Token t, com.pullenti.ner.instrument.InstrumentParticipantReferent cur, com.pullenti.ner.instrument.InstrumentParticipantReferent other, boolean cantBeEmpty) {
        if (t == null || cur == null) 
            return null;
        if (t.isTableControlChar()) 
            return null;
        int err = 0;
        int specChars = 0;
        com.pullenti.ner.ReferentToken rt = null;
        com.pullenti.ner.Token t0 = t;
        boolean isInTabCell = false;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null && (cou < 300); tt = tt.getNext(),cou++) {
            if (tt.isTableControlChar()) {
                isInTabCell = true;
                break;
            }
        }
        for (; t != null; t = t.getNext()) {
            if (t.getBeginChar() == 8923) {
            }
            if (t.isTableControlChar()) {
                if (t != t0) {
                    if (rt != null) 
                        rt.setEndToken(t.getPrevious());
                    else if (!cantBeEmpty) 
                        rt = new com.pullenti.ner.ReferentToken(cur, t0, t.getPrevious(), null);
                    break;
                }
                else 
                    continue;
            }
            if ((t.isCharOf(":.") || t.isValue("М", null) || t.isValue("M", null)) || t.isValue("П", null)) {
                if (rt != null) 
                    rt.setEndToken(t);
                continue;
            }
            com.pullenti.ner.ReferentToken pp = ParticipantToken.tryAttachToExist(t, cur, other);
            if (pp != null) {
                if (pp.referent != cur) 
                    break;
                if (rt == null) 
                    rt = new com.pullenti.ner.ReferentToken(cur, t, t, null);
                rt.setEndToken(pp.getEndToken());
                err = 0;
                continue;
            }
            if (t.isNewlineBefore()) {
                InstrToken iii = InstrToken.parse(t, 0, null);
                if (iii != null) {
                    if (iii.typ == ILTypes.APPENDIX) 
                        break;
                }
            }
            if (t.getWhitespacesBeforeCount() > 25 && !isInTabCell) {
                if (t != t0) {
                    if (t.getPrevious() != null && t.getPrevious().isCharOf(",;")) {
                    }
                    else if (t.getNewlinesBeforeCount() > 1) 
                        break;
                }
                if ((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                    if (!cur.containsRef(t.getReferent())) 
                        break;
                }
            }
            if ((t.isCharOf(";:,.") || t.isHiphen() || t.getMorph()._getClass().isPreposition()) || t.getMorph()._getClass().isConjunction()) 
                continue;
            if (t.isCharOf("_/\\")) {
                if ((++specChars) > 10 && rt == null) 
                    rt = new com.pullenti.ner.ReferentToken(cur, t0, t, null);
                if (rt != null) 
                    rt.setEndToken(t);
                continue;
            }
            if (t.isNewlineBefore() && (t instanceof com.pullenti.ner.NumberToken)) 
                break;
            if (t.isValue("ОФИС", null)) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        t = br.getEndToken();
                        continue;
                    }
                }
                if ((t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()) 
                    t = t.getNext();
                continue;
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if ((((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.address.AddressReferent) || (r instanceof com.pullenti.ner.uri.UriReferent)) || (r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent)) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) {
                if (other != null && other.findSlot(null, r, true) != null) {
                    if (!(r instanceof com.pullenti.ner.uri.UriReferent)) 
                        break;
                }
                if (rt == null) 
                    rt = new com.pullenti.ner.ReferentToken(cur, t, t, null);
                if (cur.findSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_DELEGATE, r, true) != null) {
                }
                else 
                    cur.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, false, 0);
                rt.setEndToken(t);
                err = 0;
            }
            else {
                if ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() > 1) 
                    ++err;
                if (isInTabCell && rt != null) {
                    if (err > 300) 
                        break;
                }
                else if (err > 4) 
                    break;
            }
        }
        return rt;
    }

    public com.pullenti.ner.ReferentToken attachFirst(com.pullenti.ner.instrument.InstrumentParticipantReferent p, int minChar, int maxChar) {
        com.pullenti.ner.Token t;
        com.pullenti.ner.Token tt0 = getBeginToken();
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (t = tt0.getPrevious(); t != null && t.getBeginChar() >= minChar; t = t.getPrevious()) {
            if (t.isNewlineAfter()) {
                if (t.getNewlinesAfterCount() > 1) 
                    break;
                if (t.getNext() instanceof com.pullenti.ner.NumberToken) 
                    break;
            }
            com.pullenti.ner.Token tt = _tryAttachContractGround(t, p, false);
            if (tt != null) 
                continue;
            com.pullenti.ner.Referent r = t.getReferent();
            if (((((r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.person.PersonReferent)) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent)) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) {
                if (!refs.contains(r)) 
                    refs.add(0, r);
                tt0 = t;
            }
        }
        if (refs.size() > 0) {
            for (com.pullenti.ner.Referent r : refs) {
                if (r != refs.get(0) && (refs.get(0) instanceof com.pullenti.ner._org.OrganizationReferent) && (((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent)))) 
                    p.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_DELEGATE, r, false, 0);
                else 
                    p.addSlot(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, r, false, 0);
            }
        }
        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(p, tt0, getEndToken(), null);
        t = this.getEndToken().getNext();
        if (com.pullenti.ner.core.BracketHelper.isBracket(t, false)) 
            t = t.getNext();
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        for (; t != null && ((maxChar == 0 || t.getBeginChar() <= maxChar)); t = t.getNext()) {
            if (t.isValue("СТОРОНА", null)) 
                break;
            com.pullenti.ner.Referent r = t.getReferent();
            if (((((r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.phone.PhoneReferent) || (r instanceof com.pullenti.ner.person.PersonReferent)) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) || (r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.person.PersonIdentityReferent)) || (r instanceof com.pullenti.ner.bank.BankDataReferent)) {
                if ((((r instanceof com.pullenti.ner.person.PersonPropertyReferent) && t.getNext() != null && t.getNext().isComma()) && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) && !t.getNext().isNewlineAfter()) {
                    com.pullenti.ner.person.PersonReferent pe = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getNext().getReferent(), com.pullenti.ner.person.PersonReferent.class);
                    pe.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, r, false, 0);
                    r = pe;
                    t = t.getNext().getNext();
                }
                boolean isDelegate = false;
                if (t.getPrevious().isValue("ЛИЦО", null) || t.getPrevious().isValue("ИМЯ", null)) 
                    isDelegate = true;
                if (t.getPrevious().isValue("КОТОРЫЙ", null) && t.getPrevious().getPrevious() != null && ((t.getPrevious().getPrevious().isValue("ИМЯ", null) || t.getPrevious().getPrevious().isValue("ЛИЦО", null)))) 
                    isDelegate = true;
                p.addSlot(((((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent))) && isDelegate ? com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_DELEGATE : com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF), r, false, 0);
                rt.setEndToken(t);
                continue;
            }
            com.pullenti.ner.Token tt = _tryAttachContractGround(t, p, false);
            if (tt != null) {
                t = rt.setEndToken(tt);
                if (rt.getBeginChar() == tt.getBeginChar()) 
                    rt.setBeginToken(tt);
                continue;
            }
            if (t.isValue("В", null) && t.getNext() != null && t.getNext().isValue("ЛИЦО", null)) {
                t = t.getNext();
                continue;
            }
            if (t.isValue("ОТ", null) && t.getNext() != null && t.getNext().isValue("ИМЯ", null)) {
                t = t.getNext();
                continue;
            }
            if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("ПОРУЧЕНИЕ", null)) {
                t = t.getNext();
                continue;
            }
            if (t.isNewlineBefore()) 
                break;
            if (t.getMorphClassInDictionary().equals(com.pullenti.morph.MorphClass.VERB)) {
                if ((!t.isValue("УДОСТОВЕРЯТЬ", null) && !t.isValue("ПРОЖИВАТЬ", null) && !t.isValue("ЗАРЕГИСТРИРОВАТЬ", null)) && !t.isValue("ДЕЙСТВОВАТЬ", null)) 
                    break;
            }
            if (t.isAnd() && t.getPrevious() != null && t.getPrevious().isComma()) 
                break;
            if (t.isAnd() && t.getNext().getReferent() != null) {
                if (t.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) 
                    break;
                com.pullenti.ner.person.PersonReferent pe = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.person.PersonReferent.class);
                if (pe != null) {
                    boolean hasIp = false;
                    for (com.pullenti.ner.Slot s : pe.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR)) {
                            if (s.getValue().toString().startsWith("индивидуальный предприниматель")) {
                                hasIp = true;
                                break;
                            }
                        }
                    }
                    if (hasIp) 
                        break;
                }
            }
        }
        for (t = rt.getBeginToken(); t != null && t.getEndChar() <= rt.getEndChar(); t = t.getNext()) {
            com.pullenti.ner.Token tt = _tryAttachContractGround(t, p, true);
            if (tt != null) {
                if (tt.getEndChar() > rt.getEndChar()) 
                    rt.setEndToken(tt);
                t = tt;
            }
        }
        return rt;
    }

    private static com.pullenti.ner.Token _tryAttachContractGround(com.pullenti.ner.Token t, com.pullenti.ner.instrument.InstrumentParticipantReferent ip, boolean canBePassport) {
        boolean ok = false;
        for (; t != null; t = t.getNext()) {
            if (t.isChar(',') || t.getMorph()._getClass().isPreposition()) 
                continue;
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = br.getEndToken();
                    continue;
                }
            }
            if (t.isValue("ОСНОВАНИЕ", null) || t.isValue("ДЕЙСТВОВАТЬ", null) || t.isValue("ДЕЙСТВУЮЩИЙ", null)) {
                ok = true;
                if (t.getNext() != null && t.getNext().isChar('(')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getLengthChar() < 10)) 
                        t = br.getEndToken();
                }
                continue;
            }
            com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
            if (dr != null) {
                ip.setGround(dr);
                return t;
            }
            com.pullenti.ner.person.PersonIdentityReferent pir = (com.pullenti.ner.person.PersonIdentityReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.person.PersonIdentityReferent.class);
            if (pir != null && canBePassport) {
                if (pir.getTyp() != null && !(pir.getTyp().indexOf("паспорт") >= 0)) {
                    ip.setGround(pir);
                    return t;
                }
            }
            if (t.isValue("УСТАВ", null)) {
                ip.setGround(t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false));
                return t;
            }
            if (t.isValue("ДОВЕРЕННОСТЬ", null)) {
                java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t.getNext(), null, 10, false);
                if (dts == null) {
                    boolean hasSpec = false;
                    for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null && ((ttt.getEndChar() - t.getEndChar()) < 200); ttt = ttt.getNext()) {
                        if (ttt.isComma()) 
                            continue;
                        if (ttt.isValue("УДОСТОВЕРИТЬ", null) || ttt.isValue("УДОСТОВЕРЯТЬ", null)) {
                            hasSpec = true;
                            continue;
                        }
                        com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt, null, false);
                        if (dt != null) {
                            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                                dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(ttt, null, 10, false);
                                break;
                            }
                        }
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                        if (npt != null) {
                            if (npt.getEndToken().isValue("НОТАРИУС", null)) {
                                ttt = npt.getEndToken();
                                hasSpec = true;
                                continue;
                            }
                        }
                        if (ttt.getReferent() != null) {
                            if (hasSpec) 
                                continue;
                        }
                        break;
                    }
                }
                if (dts != null && dts.size() > 0) {
                    com.pullenti.ner.Token t0 = t;
                    dr = new com.pullenti.ner.decree.DecreeReferent();
                    dr.setTyp("ДОВЕРЕННОСТЬ");
                    for (com.pullenti.ner.decree.internal.DecreeToken d : dts) {
                        if (d.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                            dr.addDate(d);
                            t = d.getEndToken();
                        }
                        else if (d.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                            dr.addNumber(d);
                            t = d.getEndToken();
                        }
                        else 
                            break;
                    }
                    com.pullenti.ner.core.AnalyzerData ad = t.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.instrument.InstrumentAnalyzer.ANALYZER_NAME);
                    ip.setGround(ad.registerReferent(dr));
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(ip.getGround(), com.pullenti.ner.Referent.class), t0, t, null);
                    t.kit.embedToken(rt);
                    return rt;
                }
                ip.setGround("ДОВЕРЕННОСТЬ");
                return t;
            }
            break;
        }
        return null;
    }

    public static java.util.ArrayList<String> getDocTypes(String name, String name2) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        if (name == null) 
            return res;
        if (com.pullenti.unisharp.Utils.stringsEq(name, "АРЕНДОДАТЕЛЬ")) {
            res.add("ДОГОВОР АРЕНДЫ");
            res.add("ДОГОВОР СУБАРЕНДЫ");
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "АРЕНДАТОР")) 
            res.add("ДОГОВОР АРЕНДЫ");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "СУБАРЕНДАТОР")) 
            res.add("ДОГОВОР СУБАРЕНДЫ");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "НАЙМОДАТЕЛЬ") || com.pullenti.unisharp.Utils.stringsEq(name, "НАНИМАТЕЛЬ")) 
            res.add("ДОГОВОР НАЙМА");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "АГЕНТ") || com.pullenti.unisharp.Utils.stringsEq(name, "ПРИНЦИПАЛ")) 
            res.add("АГЕНТСКИЙ ДОГОВОР");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "ПРОДАВЕЦ") || com.pullenti.unisharp.Utils.stringsEq(name, "ПОКУПАТЕЛЬ")) 
            res.add("ДОГОВОР КУПЛИ-ПРОДАЖИ");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "ЗАКАЗЧИК") || com.pullenti.unisharp.Utils.stringsEq(name, "ИСПОЛНИТЕЛЬ") || com.pullenti.morph.LanguageHelper.endsWith(name, "ПОДРЯДЧИК")) 
            res.add("ДОГОВОР УСЛУГ");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "ПОСТАВЩИК")) 
            res.add("ДОГОВОР ПОСТАВКИ");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "ЛИЦЕНЗИАР") || com.pullenti.unisharp.Utils.stringsEq(name, "ЛИЦЕНЗИАТ")) 
            res.add("ЛИЦЕНЗИОННЫЙ ДОГОВОР");
        else if (com.pullenti.unisharp.Utils.stringsEq(name, "СТРАХОВЩИК") || com.pullenti.unisharp.Utils.stringsEq(name, "СТРАХОВАТЕЛЬ")) 
            res.add("ДОГОВОР СТРАХОВАНИЯ");
        if (name2 == null) 
            return res;
        java.util.ArrayList<String> tmp = getDocTypes(name2, null);
        for (int i = res.size() - 1; i >= 0; i--) {
            if (!tmp.contains(res.get(i))) 
                res.remove(i);
        }
        return res;
    }

    public static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        for (String s : new String[] {"АРЕНДОДАТЕЛЬ", "АРЕНДАТОР", "СУБАРЕНДАТОР", "НАЙМОДАТЕЛЬ", "НАНИМАТЕЛЬ", "АГЕНТ", "ПРИНЦИПАЛ", "ПРОДАВЕЦ", "ПОКУПАТЕЛЬ", "ЗАКАЗЧИК", "ИСПОЛНИТЕЛЬ", "ПОСТАВЩИК", "ПОДРЯДЧИК", "СУБПОДРЯДЧИК", "СТОРОНА", "ЛИЦЕНЗИАР", "ЛИЦЕНЗИАТ", "СТРАХОВЩИК", "СТРАХОВАТЕЛЬ", "ПРОВАЙДЕР", "АБОНЕНТ", "ЗАСТРОЙЩИК", "УЧАСТНИК ДОЛЕВОГО СТРОИТЕЛЬСТВА", "КЛИЕНТ", "ЗАЕМЩИК", "УПРАВЛЯЮЩИЙ"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new100(s, m_Ontology));
        }
        t = com.pullenti.ner.core.Termin._new100("ГЕНПОДРЯДЧИК", m_Ontology);
        t.addVariant("ГЕНЕРАЛЬНЫЙ ПОДРЯДЧИК", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new100("ЗАИМОДАТЕЛЬ", m_Ontology);
        t.addVariant("ЗАЙМОДАТЕЛЬ", false);
        t.addVariant("ЗАЙМОДАВЕЦ", false);
        t.addVariant("ЗАИМОДАВЕЦ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ИМЕНУЕМЫЙ", null, false);
        t.addVariant("ИМЕНОВАТЬСЯ", false);
        t.addVariant("ИМЕНУЕМ", false);
        t.addVariant("ДАЛЬНЕЙШИЙ", false);
        t.addVariant("ДАЛЕЕ", false);
        t.addVariant("ДАЛЕЕ ПО ТЕКСТУ", false);
        m_Ontology.add(t);
    }

    public static ParticipantToken _new1549(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        ParticipantToken res = new ParticipantToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static ParticipantToken _new1727(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Kinds _arg3) {
        ParticipantToken res = new ParticipantToken(_arg1, _arg2);
        res.kind = _arg3;
        return res;
    }

    public static ParticipantToken _new1732(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Kinds _arg3, String _arg4) {
        ParticipantToken res = new ParticipantToken(_arg1, _arg2);
        res.kind = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static class Kinds implements Comparable<Kinds> {
    
        public static final Kinds UNDEFINED; // 0
    
        public static final Kinds PURE; // 1
    
        public static final Kinds NAMEDAS; // 2
    
        public static final Kinds NAMEDASPARTS; // 3
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Kinds(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Kinds v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Kinds> mapIntToEnum; 
        private static java.util.HashMap<String, Kinds> mapStringToEnum; 
        private static Kinds[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Kinds of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Kinds item = new Kinds(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Kinds of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Kinds[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Kinds>();
            mapStringToEnum = new java.util.HashMap<String, Kinds>();
            UNDEFINED = new Kinds(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            PURE = new Kinds(1, "PURE");
            mapIntToEnum.put(PURE.value(), PURE);
            mapStringToEnum.put(PURE.m_str.toUpperCase(), PURE);
            NAMEDAS = new Kinds(2, "NAMEDAS");
            mapIntToEnum.put(NAMEDAS.value(), NAMEDAS);
            mapStringToEnum.put(NAMEDAS.m_str.toUpperCase(), NAMEDAS);
            NAMEDASPARTS = new Kinds(3, "NAMEDASPARTS");
            mapIntToEnum.put(NAMEDASPARTS.value(), NAMEDASPARTS);
            mapStringToEnum.put(NAMEDASPARTS.m_str.toUpperCase(), NAMEDASPARTS);
            java.util.Collection<Kinds> col = mapIntToEnum.values();
            m_Values = new Kinds[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public ParticipantToken() {
        super();
        if(_globalInstance == null) return;
        kind = Kinds.UNDEFINED;
    }
    public static ParticipantToken _globalInstance;
    
    static {
        try { _globalInstance = new ParticipantToken(); } 
        catch(Exception e) { }
    }
}
