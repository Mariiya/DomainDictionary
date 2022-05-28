/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Анализатор ссылок на НПА
 */
public class DecreeAnalyzer extends com.pullenti.ner.Analyzer {

    public static java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts, com.pullenti.ner.decree.internal.DecreeToken baseTyp, com.pullenti.ner.core.AnalyzerData ad) {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = _TryAttach(dts, baseTyp, false, ad);
        return res;
    }

    private static java.util.ArrayList<com.pullenti.ner.ReferentToken> _TryAttach(java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts, com.pullenti.ner.decree.internal.DecreeToken baseTyp, boolean afterDecree, com.pullenti.ner.core.AnalyzerData ad) {
        if (dts == null || (dts.size() < 1)) 
            return null;
        if (dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.EDITION && dts.size() > 1) 
            dts.remove(0);
        if (dts.size() == 1) {
            if (dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DECREEREF && dts.get(0).ref != null) {
                if (baseTyp != null) {
                    com.pullenti.ner.Referent re = dts.get(0).ref.getReferent();
                    DecreeReferent dre = (DecreeReferent)com.pullenti.unisharp.Utils.cast(re, DecreeReferent.class);
                    if (dre == null && (re instanceof DecreePartReferent)) 
                        dre = ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(re, DecreePartReferent.class)).getOwner();
                    if (dre != null) {
                        if (com.pullenti.unisharp.Utils.stringsEq(dre.getTyp(), baseTyp.value) || com.pullenti.unisharp.Utils.stringsEq(dre.getTyp0(), baseTyp.value)) 
                            return null;
                    }
                }
                java.util.ArrayList<com.pullenti.ner.ReferentToken> reli = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                reli.add(new com.pullenti.ner.ReferentToken(dts.get(0).ref.referent, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null));
                return reli;
            }
        }
        DecreeReferent dec0 = null;
        boolean kodeks = false;
        boolean canbesingle = false;
        int maxEmpty = 30;
        for (com.pullenti.ner.Token t = dts.get(0).getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
            if (t.isCommaAnd()) 
                continue;
            if (t.isChar(')')) {
                int cou = 0;
                for (t = t.getPrevious(); t != null; t = t.getPrevious()) {
                    if (t.isChar('(')) 
                        break;
                    else if ((++cou) > 200) 
                        break;
                }
                if (t != null && t.isChar('(')) 
                    continue;
                break;
            }
            if ((--maxEmpty) < 0) 
                break;
            if (!t.chars.isLetter()) 
                continue;
            dec0 = (DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), DecreeReferent.class);
            if (dec0 != null) {
                if (com.pullenti.ner.decree.internal.DecreeToken.getKind(dec0.getTyp()) == DecreeKind.KODEX) 
                    kodeks = true;
                else if (dec0.getKind() == DecreeKind.PUBLISHER) 
                    dec0 = null;
            }
            break;
        }
        DecreeReferent dec = new DecreeReferent();
        int i = 0;
        com.pullenti.ner.MorphCollection _morph = null;
        boolean isNounDoubt = false;
        com.pullenti.ner.decree.internal.DecreeToken numTok = null;
        for (i = 0; i < dts.size(); i++) {
            if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                if (dts.get(i).value == null) 
                    break;
                if (dts.get(i).isNewlineBefore()) {
                    if (dec.getDate() != null || dec.getNumber() != null) 
                        break;
                }
                if (dec.getTyp() != null) {
                    if (((com.pullenti.unisharp.Utils.stringsEq(dec.getTyp(), "РЕШЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(dec.getTyp(), "РІШЕННЯ"))) && com.pullenti.unisharp.Utils.stringsEq(dts.get(i).value, "ПРОТОКОЛ")) {
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(dec.getTyp(), dts.get(i).value) && com.pullenti.unisharp.Utils.stringsEq(dec.getTyp(), "ГОСТ")) 
                        continue;
                    else 
                        break;
                }
                DecreeKind ki = com.pullenti.ner.decree.internal.DecreeToken.getKind(dts.get(i).value);
                if (ki == DecreeKind.STANDARD) {
                    if (i > 0) {
                        if (dts.size() == 2 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && com.pullenti.unisharp.Utils.stringsEq(dts.get(i).value, "ТЕХНИЧЕСКИЕ УСЛОВИЯ")) {
                        }
                        else 
                            return null;
                    }
                }
                if (ki == DecreeKind.KODEX) {
                    if (i > 0) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsNe(dts.get(i).value, "ОСНОВЫ ЗАКОНОДАТЕЛЬСТВА") && com.pullenti.unisharp.Utils.stringsNe(dts.get(i).value, "ОСНОВИ ЗАКОНОДАВСТВА")) 
                        kodeks = true;
                    else 
                        kodeks = false;
                }
                else 
                    kodeks = false;
                _morph = dts.get(i).getMorph();
                dec.setTyp(dts.get(i).value);
                if (dts.get(i).canBeSingleDecree) 
                    canbesingle = true;
                if (dts.get(i).fullValue != null) 
                    dec.addNameStr(dts.get(i).fullValue);
                isNounDoubt = dts.get(i).isDoubtful;
                if (isNounDoubt && i == 0) {
                    if (com.pullenti.ner.decree.internal.PartToken.isPartBefore(dts.get(i).getBeginToken())) 
                        isNounDoubt = false;
                }
                if (dts.get(i).ref != null) {
                    if (dec.findSlot(DecreeReferent.ATTR_GEO, null, true) == null) {
                        dec.addSlot(DecreeReferent.ATTR_GEO, dts.get(i).ref.referent, false, 0);
                        dec.addExtReferent(dts.get(i).ref);
                    }
                }
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                if (dec.getDate() != null) 
                    break;
                if (kodeks) {
                    if (i > 0 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    }
                    else 
                        break;
                }
                if (i == (dts.size() - 1)) {
                    if (!dts.get(i).getBeginToken().isValue("ОТ", "ВІД")) {
                        DecreeKind ty = com.pullenti.ner.decree.internal.DecreeToken.getKind(dec.getTyp());
                        if ((ty == DecreeKind.KONVENTION || ty == DecreeKind.CONTRACT || com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "ПИСЬМО")) || com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "ЛИСТ")) {
                        }
                        else 
                            break;
                    }
                }
                dec.addDate(dts.get(i));
                dec.addExtReferent(dts.get(i).ref);
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATERANGE) {
                if (dec.getKind() != DecreeKind.PROGRAM) 
                    break;
                dec.addDate(dts.get(i));
                dec.addExtReferent(dts.get(i).ref);
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.EDITION) {
                if (dts.get(i).isNewlineBefore() && !dts.get(i).getBeginToken().chars.isAllLower() && !dts.get(i).getBeginToken().isChar('(')) 
                    break;
                if (((i + 2) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                    break;
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                if (kodeks) {
                    if (((i + 1) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                    }
                    else 
                        break;
                }
                numTok = dts.get(i);
                if (dts.get(i).isDelo()) {
                    if (dec.getCaseNumber() != null) 
                        break;
                    dec.addSlot(DecreeReferent.ATTR_CASENUMBER, dts.get(i).value, true, 0);
                    continue;
                }
                if (dec.getNumber() != null) {
                    if (i > 2 && ((dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG)) && dts.get(i - 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    }
                    else 
                        break;
                }
                if (dts.get(i).isNewlineBefore()) {
                    if (dec.getTyp() == null && dec0 == null) 
                        break;
                }
                if (com.pullenti.morph.LanguageHelper.endsWith(dts.get(i).value, "ФЗ")) 
                    dec.setTyp("ФЕДЕРАЛЬНЫЙ ЗАКОН");
                if (com.pullenti.morph.LanguageHelper.endsWith(dts.get(i).value, "ФКЗ")) 
                    dec.setTyp("ФЕДЕРАЛЬНЫЙ КОНСТИТУЦИОННЫЙ ЗАКОН");
                if (dts.get(i).value != null && com.pullenti.unisharp.Utils.startsWithString(dts.get(i).value, "ПР", true) && dec.getTyp() == null) 
                    dec.setTyp("ПОРУЧЕНИЕ");
                if (dec.getTyp() == null) {
                    if (dec0 == null && !afterDecree && baseTyp == null) 
                        break;
                }
                dec.addNumber(dts.get(i));
                if (dts.get(i).children != null) {
                    int cou = 0;
                    for (com.pullenti.ner.Slot s : dec.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_SOURCE)) 
                            cou++;
                    }
                    if (cou == (dts.get(i).children.size() + 1)) {
                        for (com.pullenti.ner.decree.internal.DecreeToken dd : dts.get(i).children) {
                            dec.addNumber(dd);
                        }
                        dts.get(i).children = null;
                    }
                }
                continue;
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) {
                if (dec.getTyp() == null && dec.getNumber() == null && dec0 == null) 
                    break;
                if (dec.getStringValue(DecreeReferent.ATTR_NAME) != null) {
                    if (kodeks) 
                        break;
                    if (i > 0 && dts.get(i - 1).getEndToken().getNext() == dts.get(i).getBeginToken()) {
                    }
                    else 
                        break;
                }
                com.pullenti.ner.Token tt0 = dts.get(i).getBeginToken();
                com.pullenti.ner.Token tt1 = dts.get(i).getEndToken();
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt0, true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt1, true, tt0, false)) {
                    tt0 = tt0.getNext();
                    tt1 = tt1.getPrevious();
                    for (com.pullenti.ner.Token tt = tt0; tt != null && (tt.getEndChar() < tt1.getEndChar()); tt = tt.getNext()) {
                        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) {
                            com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br1 != null && br1.getEndToken() == dts.get(i).getEndToken()) {
                                tt1 = tt1.getNext();
                                break;
                            }
                        }
                    }
                }
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt1, com.pullenti.ner.core.GetTextAttr.RESTOREREGISTER);
                if (kodeks && !(nam.toUpperCase().indexOf("КОДЕКС") >= 0)) 
                    nam = "Кодекс " + nam;
                dec.addNameStr(nam);
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.BETWEEN) {
                if (dec.getKind() != DecreeKind.CONTRACT) 
                    break;
                for (com.pullenti.ner.decree.internal.DecreeToken chh : dts.get(i).children) {
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, chh.ref.referent, false, 0).setTag(chh.getSourceText());
                    if (chh.ref.referent instanceof com.pullenti.ner.person.PersonPropertyReferent) 
                        dec.addExtReferent(chh.ref);
                }
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                if (kodeks) 
                    break;
                if (dec.getName() != null) 
                    break;
                if (((i == 0 || i == (dts.size() - 1))) && dts.get(i).getBeginToken().chars.isAllLower()) 
                    break;
                if (i == 0 && dts.size() > 1 && dts.get(1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                    break;
                if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) != null) {
                }
                if (dts.get(i).ref != null) {
                    DecreeKind ty = com.pullenti.ner.decree.internal.DecreeToken.getKind(dec.getTyp());
                    if (ty == DecreeKind.USTAV) {
                        if (!(dts.get(i).ref.referent instanceof com.pullenti.ner._org.OrganizationReferent)) 
                            break;
                    }
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, dts.get(i).ref.referent, false, 0).setTag(dts.get(i).getSourceText());
                    if (dts.get(i).ref.referent instanceof com.pullenti.ner.person.PersonPropertyReferent) 
                        dec.addExtReferent(dts.get(i).ref);
                }
                else 
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(dts.get(i).value), false, 0).setTag(dts.get(i).getSourceText());
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) {
                if (kodeks) {
                    if (i != 1 || dts.get(i).getWhitespacesBeforeCount() > 3) 
                        break;
                    if (dts.size() == 2 || ((dts.size() == 3 && dts.get(2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.MISC))) {
                    }
                    else 
                        break;
                    isNounDoubt = false;
                }
                else if (dec.getName() != null) 
                    break;
                if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) != null) {
                    if (i > 2 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && ((dts.get(i - 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG || dts.get(i - 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER))) {
                    }
                    else if (dts.get(i).getBeginToken().getPrevious() != null && dts.get(i).getBeginToken().getPrevious().isAnd()) {
                    }
                    else if (i > 0 && ((dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG))) {
                    }
                    else 
                        break;
                }
                com.pullenti.ner.Slot sl = dec.addSlot(DecreeReferent.ATTR_SOURCE, dts.get(i).ref.referent, false, 0);
                sl.setTag(dts.get(i).getSourceText());
                if (((i + 2) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.UNKNOWN && (dts.get(i + 1).getWhitespacesBeforeCount() < 2)) {
                    if (dts.get(i + 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dts.get(i + 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                        sl.setTag((new com.pullenti.ner.MetaToken(dts.get(i).getBeginToken(), dts.get(i + 1).getEndToken(), null)).getSourceText());
                        i++;
                    }
                }
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                if (dec.findSlot(DecreeReferent.ATTR_GEO, null, true) != null) 
                    break;
                if (i > 0 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) 
                    break;
                if (dts.get(i).isNewlineBefore() && ((i + 1) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                    break;
                dec.addSlot(DecreeReferent.ATTR_GEO, dts.get(i).ref.referent, false, 0);
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.UNKNOWN) {
                if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) != null) 
                    break;
                if (kodeks) 
                    break;
                if ((dec.getKind() == DecreeKind.CONTRACT && i == 1 && ((i + 1) < dts.size())) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    dec.addNameStr(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(dts.get(i), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                    continue;
                }
                if (i == 0) {
                    if (dec0 == null && !afterDecree) 
                        break;
                    boolean ok1 = false;
                    if (((i + 1) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                        ok1 = true;
                    else if (((i + 2) < dts.size()) && dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && dts.get(i + 2).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                        ok1 = true;
                    if (!ok1) 
                        break;
                }
                else if (dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) 
                    continue;
                if ((i + 1) >= dts.size()) 
                    break;
                if (dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && dts.get(0).isDoubtful) 
                    break;
                if (dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) {
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, dts.get(i).value, false, 0).setTag(dts.get(i).getSourceText());
                    continue;
                }
                if (dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, dts.get(i).value, false, 0).setTag(dts.get(i).getSourceText());
                    continue;
                }
                if (dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                    String s = com.pullenti.ner.core.MiscHelper.getTextValue(dts.get(i).getBeginToken(), dts.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    dts.get(i).setEndToken(dts.get(i + 1).getEndToken());
                    dec.addSlot(DecreeReferent.ATTR_SOURCE, s, false, 0).setTag(dts.get(i).getSourceText());
                    i++;
                    continue;
                }
                break;
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.MISC) {
                if (i == 0 || kodeks) 
                    break;
                if ((i + 1) >= dts.size()) {
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(dts.get(i).getEndToken().getNext(), true, false)) 
                        continue;
                    if (i > 0 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                        if (com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(dts.get(i).getEndToken().getNext(), null, true, false) != null) 
                            continue;
                    }
                }
                else if (dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME || dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dts.get(i + 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                    continue;
                break;
            }
            else 
                break;
        }
        if (i == 0) 
            return null;
        if (dec.getTyp() == null || ((dec0 != null && dts.get(0).typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP))) {
            if (dec0 != null) {
                if (dec.getNumber() == null && dec.getDate() == null && dec.findSlot(DecreeReferent.ATTR_NAME, null, true) == null) 
                    return null;
                if (dec.getTyp() == null) 
                    dec.setTyp(dec0.getTyp());
                if (dec.findSlot(DecreeReferent.ATTR_GEO, null, true) == null) 
                    dec.addSlot(DecreeReferent.ATTR_GEO, dec0.getStringValue(DecreeReferent.ATTR_GEO), false, 0);
                if (dec.findSlot(DecreeReferent.ATTR_DATE, null, true) == null && dec0.getDate() != null) 
                    dec.addSlot(DecreeReferent.ATTR_DATE, dec0.getSlotValue(DecreeReferent.ATTR_DATE), false, 0);
                com.pullenti.ner.Slot sl;
                if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) == null) {
                    if ((((sl = dec0.findSlot(DecreeReferent.ATTR_SOURCE, null, true)))) != null) 
                        dec.addSlot(DecreeReferent.ATTR_SOURCE, sl.getValue(), false, 0).setTag(sl.getTag());
                }
            }
            else if (baseTyp != null && afterDecree) 
                dec.setTyp(baseTyp.value);
            else 
                return null;
        }
        com.pullenti.ner.Token et = dts.get(i - 1).getEndToken();
        if ((((!afterDecree && dts.size() == i && i == 3) && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) && dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) != null && et.getNext() != null) && et.getNext().isComma() && dec.getNumber() != null) {
            for (com.pullenti.ner.Token tt = et.getNext(); tt != null; tt = tt.getNext()) {
                if (!tt.isChar(',')) 
                    break;
                java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> ddd = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(tt.getNext(), dts.get(0), 10, false);
                if (ddd == null || (ddd.size() < 2) || ddd.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                    break;
                boolean hasNum = false;
                for (com.pullenti.ner.decree.internal.DecreeToken d : ddd) {
                    if (d.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                        hasNum = true;
                    else if (d.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                        hasNum = false;
                        break;
                    }
                }
                if (!hasNum) 
                    break;
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rtt = _TryAttach(ddd, dts.get(0), true, ad);
                if (rtt == null) 
                    break;
                dec.mergeSlots(rtt.get(0).referent, true);
                et = (tt = rtt.get(0).getEndToken());
            }
        }
        if (((et.getNext() != null && et.getNext().isChar('<') && (et.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && et.getNext().getNext().getNext() != null && et.getNext().getNext().getNext().isChar('>')) && com.pullenti.unisharp.Utils.stringsEq(et.getNext().getNext().getReferent().getTypeName(), "URI")) 
            et = et.getNext().getNext().getNext();
        String num = dec.getNumber();
        if ((dec.findSlot(DecreeReferent.ATTR_NAME, null, true) == null && (i < dts.size()) && dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) && dec.getKind() == DecreeKind.PROJECT) {
            java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts1 = new java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken>(dts);
            for(int jjj = 0 + i - 1, mmm = 0; jjj >= mmm; jjj--) dts1.remove(jjj);
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rt1 = _TryAttach(dts1, null, true, ad);
            if (rt1 != null) {
                dec.addNameStr(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(rt1.get(0), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                et = rt1.get(0).getEndToken();
            }
        }
        if (dec.findSlot(DecreeReferent.ATTR_NAME, null, true) == null && !kodeks && et.getNext() != null) {
            com.pullenti.ner.decree.internal.DecreeToken dn = com.pullenti.ner.decree.internal.DecreeToken.tryAttachName((et.getNext().isChar(':') ? et.getNext().getNext() : et.getNext()), dec.getTyp(), false, false);
            if (dn != null && et.getNext().chars.isAllLower() && num != null) {
                if (ad != null) {
                    for (com.pullenti.ner.Referent r : ad.getReferents()) {
                        if (r.findSlot(DecreeReferent.ATTR_NUMBER, num, true) != null) {
                            if (r.canBeEquals(dec, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                                if (r.findSlot(DecreeReferent.ATTR_NAME, dn.value, true) == null) 
                                    dn = null;
                                break;
                            }
                        }
                    }
                }
            }
            if (dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && dn != null && et.isNewlineAfter()) 
                dn = null;
            if (dn != null) {
                if (dec.getKind() == DecreeKind.PROGRAM) {
                    for (com.pullenti.ner.Token tt1 = dn.getEndToken().getPrevious(); tt1 != null && tt1.getBeginChar() > dn.getBeginChar(); tt1 = (tt1 == null ? null : tt1.getPrevious())) {
                        if (tt1.isChar(')') && tt1.getPrevious() != null) 
                            tt1 = tt1.getPrevious();
                        if (tt1.getReferent() instanceof com.pullenti.ner.date.DateRangeReferent) 
                            dec.addSlot(DecreeReferent.ATTR_DATE, tt1.getReferent(), false, 0);
                        else if ((tt1.getReferent() instanceof com.pullenti.ner.date.DateReferent) && tt1.getPrevious() != null && tt1.getPrevious().isValue("ДО", null)) {
                            com.pullenti.ner.ReferentToken rt11 = tt1.kit.processReferent("DATE", tt1.getPrevious(), null);
                            if (rt11 != null && (rt11.referent instanceof com.pullenti.ner.date.DateRangeReferent)) {
                                dec.addSlot(DecreeReferent.ATTR_DATE, rt11.referent, false, 0);
                                dec.addExtReferent(rt11);
                                tt1 = tt1.getPrevious();
                            }
                            else 
                                break;
                        }
                        else if ((tt1.getReferent() instanceof com.pullenti.ner.date.DateReferent) && tt1.getPrevious() != null && ((tt1.getPrevious().isValue("НА", null) || tt1.getPrevious().isValue("В", null)))) {
                            dec.addSlot(DecreeReferent.ATTR_DATE, tt1.getReferent(), false, 0);
                            tt1 = tt1.getPrevious();
                        }
                        else 
                            break;
                        for (tt1 = tt1.getPrevious(); tt1 != null && tt1.getBeginChar() > dn.getBeginChar(); tt1 = (tt1 == null ? null : tt1.getPrevious())) {
                            if (tt1.getMorph()._getClass().isConjunction() || tt1.getMorph()._getClass().isPreposition()) 
                                continue;
                            if (tt1.isValue("ПЕРИОД", "ПЕРІОД") || tt1.isValue("ПЕРСПЕКТИВА", null)) 
                                continue;
                            if (tt1.isChar('(')) 
                                continue;
                            break;
                        }
                        if (tt1 != null && tt1.getEndChar() > dn.getBeginChar()) {
                            if (dn.fullValue == null) 
                                dn.fullValue = dn.value;
                            dn.value = com.pullenti.ner.core.MiscHelper.getTextValue(dn.getBeginToken(), tt1, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                        }
                        tt1 = tt1.getNext();
                    }
                }
                if (dn.fullValue != null) 
                    dec.addNameStr(dn.fullValue);
                if ((dts.size() == 1 && dec.getKind() != DecreeKind.PROGRAM && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) && dn.getBeginToken().getMorph().getCase().isGenitive() && !dn.getBeginToken().getMorph()._getClass().isPreposition()) {
                    String str = com.pullenti.ner.core.MiscHelper.getTextValue(dts.get(0).getBeginToken(), dn.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    dec.addNameStr(str);
                }
                else 
                    dec.addNameStr(dn.value);
                et = dn.getEndToken();
                boolean br = false;
                for (com.pullenti.ner.Token tt = et.getNext(); tt != null; tt = tt.getNext()) {
                    if (tt.isChar('(')) {
                        br = true;
                        continue;
                    }
                    if (tt.isChar(')') && br) {
                        et = tt;
                        continue;
                    }
                    if ((tt.getReferent() instanceof com.pullenti.ner.date.DateRangeReferent) && dec.getKind() == DecreeKind.PROGRAM) {
                        dec.addSlot(DecreeReferent.ATTR_DATE, tt.getReferent(), false, 0);
                        et = tt;
                        continue;
                    }
                    dn = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
                    if (dn == null) 
                        break;
                    if (dn.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE && dec.getDate() == null) {
                        if (dec.addDate(dn)) {
                            et = (tt = dn.getEndToken());
                            continue;
                        }
                    }
                    if (dn.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER && dec.getNumber() == null) {
                        dec.addNumber(dn);
                        et = (tt = dn.getEndToken());
                        continue;
                    }
                    if (dn.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATERANGE && dec.getKind() == DecreeKind.PROGRAM) {
                        if (dec.addDate(dn)) {
                            et = (tt = dn.getEndToken());
                            continue;
                        }
                    }
                    if (dn.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR && dec.findSlot(DecreeReferent.ATTR_GEO, null, true) == null && dn.ref != null) {
                        dec.addSlot(DecreeReferent.ATTR_GEO, dn.ref.referent, false, 0);
                        et = (tt = dn.getEndToken());
                        continue;
                    }
                    break;
                }
            }
        }
        if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) == null) {
            com.pullenti.ner.Token tt0 = dts.get(0).getBeginToken().getPrevious();
            if ((tt0 != null && tt0.isValue("В", "У") && tt0.getPrevious() != null) && (tt0.getPrevious().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
                dec.addSlot(DecreeReferent.ATTR_SOURCE, tt0.getPrevious().getReferent(), false, 0);
        }
        if (!canbesingle && !dec.checkCorrection(isNounDoubt)) {
            String ty = dec.getTyp();
            com.pullenti.ner.Slot sl = null;
            if (dec0 != null && dec.getDate() != null && dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) == null) 
                sl = dec0.findSlot(DecreeReferent.ATTR_SOURCE, null, true);
            if (sl != null && (((((com.pullenti.unisharp.Utils.stringsEq(ty, "ПОСТАНОВЛЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(ty, "ПОСТАНОВА") || com.pullenti.unisharp.Utils.stringsEq(ty, "ОПРЕДЕЛЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(ty, "ВИЗНАЧЕННЯ") || com.pullenti.unisharp.Utils.stringsEq(ty, "РЕШЕНИЕ")) || com.pullenti.unisharp.Utils.stringsEq(ty, "РІШЕННЯ") || com.pullenti.unisharp.Utils.stringsEq(ty, "ПРИГОВОР")) || com.pullenti.unisharp.Utils.stringsEq(ty, "ВИРОК")))) 
                dec.addSlot(sl.getTypeName(), sl.getValue(), false, 0).setTag(sl.getTag());
            else {
                int eqDecs = 0;
                DecreeReferent dr0 = null;
                if (num != null) {
                    if (ad != null) {
                        for (com.pullenti.ner.Referent r : ad.getReferents()) {
                            if (r.findSlot(DecreeReferent.ATTR_NUMBER, num, true) != null) {
                                if (r.canBeEquals(dec, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                                    eqDecs++;
                                    dr0 = (DecreeReferent)com.pullenti.unisharp.Utils.cast(r, DecreeReferent.class);
                                }
                            }
                        }
                    }
                }
                if (eqDecs == 1) 
                    dec.mergeSlots(dr0, true);
                else {
                    boolean ok1 = false;
                    if (num != null) {
                        for (com.pullenti.ner.Token tt = dts.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                            if (tt.isCharOf(":,") || tt.isHiphen() || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                            }
                            else {
                                if (tt.isValue("ДАЛЕЕ", "ДАЛІ")) 
                                    ok1 = true;
                                break;
                            }
                        }
                    }
                    if (!ok1) 
                        return null;
                }
            }
        }
        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dec, dts.get(0).getBeginToken(), et, null);
        if (dec.getSlots().size() == 2 && com.pullenti.unisharp.Utils.stringsEq(dec.getSlots().get(0).getTypeName(), DecreeReferent.ATTR_TYPE) && com.pullenti.unisharp.Utils.stringsEq(dec.getSlots().get(1).getTypeName(), DecreeReferent.ATTR_NAME)) {
            boolean err = true;
            for (com.pullenti.ner.Token tt = rt.getBeginToken(); tt != null && tt.getEndChar() <= rt.getEndChar(); tt = tt.getNext()) {
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !tt.chars.isAllLower()) {
                    err = false;
                    break;
                }
            }
            if (err) 
                return null;
        }
        if (_morph != null) 
            rt.setMorph(_morph);
        if (rt.chars.isAllLower()) {
            if (com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "ДЕКЛАРАЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "ДЕКЛАРАЦІЯ")) 
                return null;
            if (((com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "КОНСТИТУЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(dec.getTyp0(), "КОНСТИТУЦІЯ"))) && rt.getBeginToken() == rt.getEndToken()) {
                boolean ok1 = false;
                int cou = 10;
                for (com.pullenti.ner.Token tt = rt.getBeginToken().getPrevious(); tt != null && cou > 0; tt = tt.getPrevious(),cou--) {
                    if (tt.isNewlineAfter()) 
                        break;
                    com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(tt, null, false, false);
                    if (pt != null && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX && pt.getEndToken().getNext() == rt.getBeginToken()) {
                        ok1 = true;
                        break;
                    }
                }
                if (!ok1) 
                    return null;
            }
        }
        if (num != null && ((num.indexOf('/') > 0 || num.indexOf(',') > 0))) {
            int cou = 0;
            for (com.pullenti.ner.Slot s : dec.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_NUMBER)) 
                    cou++;
            }
            if (cou == 1) {
                int owns = 0;
                for (com.pullenti.ner.Slot s : dec.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_SOURCE)) 
                        owns++;
                }
                if (owns > 1) {
                    String[] nums = com.pullenti.unisharp.Utils.split(num, String.valueOf('/'), false);
                    String[] nums2 = com.pullenti.unisharp.Utils.split(num, String.valueOf(','), false);
                    String strNum = null;
                    for (int ii = 0; ii < dts.size(); ii++) {
                        if (dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                            strNum = dts.get(ii).getSourceText();
                            break;
                        }
                    }
                    if (nums2.length == owns && owns > 1) {
                        dec.addSlot(DecreeReferent.ATTR_NUMBER, null, true, 0);
                        for (String n : nums2) {
                            dec.addSlot(DecreeReferent.ATTR_NUMBER, n.trim(), false, 0).setTag(strNum);
                        }
                    }
                    else if (nums.length == owns && owns > 1) {
                        dec.addSlot(DecreeReferent.ATTR_NUMBER, null, true, 0);
                        for (String n : nums) {
                            dec.addSlot(DecreeReferent.ATTR_NUMBER, n.trim(), false, 0).setTag(strNum);
                        }
                    }
                }
            }
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt.getBeginToken().getPrevious(), true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt.getEndToken().getNext(), true, rt.getBeginToken().getPrevious(), false)) {
            rt.setBeginToken(rt.getBeginToken().getPrevious());
            rt.setEndToken(rt.getEndToken().getNext());
            java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(rt.getEndToken().getNext(), null, 10, false);
            if (dts1 != null && dts1.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE && dec.findSlot(DecreeReferent.ATTR_DATE, null, true) == null) {
                dec.addDate(dts1.get(0));
                rt.setEndToken(dts1.get(0).getEndToken());
            }
        }
        if (dec.getKind() == DecreeKind.STANDARD && dec.getName() == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt.getEndToken().getNext(), true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(rt.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                dec.addNameStr(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                rt.setEndToken(br.getEndToken());
            }
        }
        if (dec.getKind() == DecreeKind.PROGRAM && dec.findSlot(DecreeReferent.ATTR_DATE, null, true) == null) {
            if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isValue("ПАСПОРТ", null)) {
                int cou = 0;
                for (com.pullenti.ner.Token tt = rt.getEndToken().getNext(); tt != null && (cou < 1000); tt = (tt == null ? null : tt.getNext())) {
                    if (tt.isValue("СРОК", "ТЕРМІН") && tt.getNext() != null && tt.getNext().isValue("РЕАЛИЗАЦИЯ", "РЕАЛІЗАЦІЯ")) {
                    }
                    else 
                        continue;
                    tt = tt.getNext().getNext();
                    if (tt == null) 
                        break;
                    com.pullenti.ner.decree.internal.DecreeToken dtok = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
                    if (dtok != null && dtok.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && ((com.pullenti.unisharp.Utils.stringsEq(dtok.value, "ПРОГРАММА") || com.pullenti.unisharp.Utils.stringsEq(dtok.value, "ПРОГРАМА")))) 
                        tt = dtok.getEndToken().getNext();
                    for (; tt != null; tt = tt.getNext()) {
                        if (tt.isHiphen() || tt.isTableControlChar() || tt.isValue("ПРОГРАММА", "ПРОГРАМА")) {
                        }
                        else if (tt.getReferent() instanceof com.pullenti.ner.date.DateRangeReferent) {
                            dec.addSlot(DecreeReferent.ATTR_DATE, tt.getReferent(), false, 0);
                            break;
                        }
                        else 
                            break;
                    }
                    break;
                }
            }
        }
        if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar('(')) {
            com.pullenti.ner.date.DateReferent dt = null;
            for (com.pullenti.ner.Token tt = rt.getEndToken().getNext().getNext(); tt != null; tt = tt.getNext()) {
                com.pullenti.ner.Referent r = tt.getReferent();
                if (r instanceof com.pullenti.ner.geo.GeoReferent) 
                    continue;
                if (r instanceof com.pullenti.ner.date.DateReferent) {
                    dt = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.date.DateReferent.class);
                    continue;
                }
                if (tt.getMorph()._getClass().isPreposition()) 
                    continue;
                if (tt.getMorph()._getClass().isVerb()) 
                    continue;
                if (tt.isChar(')') && dt != null) {
                    dec.addSlot(DecreeReferent.ATTR_DATE, dt, false, 0);
                    rt.setEndToken(tt);
                }
                break;
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rtLi = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        if (((i + 1) < dts.size()) && dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.EDITION && !dts.get(i).isNewlineBefore()) {
            for(int jjj = 0 + i + 1 - 1, mmm = 0; jjj >= mmm; jjj--) dts.remove(jjj);
            java.util.ArrayList<com.pullenti.ner.ReferentToken> ed = _TryAttach(dts, baseTyp, true, ad);
            if (ed != null && ed.size() > 0) {
                com.pullenti.unisharp.Utils.addToArrayList(rtLi, ed);
                for (com.pullenti.ner.ReferentToken e : ed) {
                    dec.addSlot(DecreeReferent.ATTR_EDITION, e.referent, false, 0);
                }
                rt.setEndToken(ed.get(ed.size() - 1).getEndToken());
            }
        }
        else if (((i < (dts.size() - 1)) && i > 0 && dts.get(i - 1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.EDITION) && !dts.get(i - 1).isNewlineBefore()) {
            for(int jjj = 0 + i - 1, mmm = 0; jjj >= mmm; jjj--) dts.remove(jjj);
            java.util.ArrayList<com.pullenti.ner.ReferentToken> ed = _TryAttach(dts, baseTyp, true, ad);
            if (ed != null && ed.size() > 0) {
                com.pullenti.unisharp.Utils.addToArrayList(rtLi, ed);
                for (com.pullenti.ner.ReferentToken e : ed) {
                    dec.addSlot(DecreeReferent.ATTR_EDITION, e.referent, false, 0);
                }
                rt.setEndToken(ed.get(ed.size() - 1).getEndToken());
            }
        }
        com.pullenti.ner.ReferentToken rt22 = DecreeAnalyzer._tryAttachApproved(rt.getEndToken().getNext(), ad, true);
        if (rt22 != null) {
            rt.setEndToken(rt22.getEndToken());
            DecreeReferent dr00 = (DecreeReferent)com.pullenti.unisharp.Utils.cast(rt22.referent, DecreeReferent.class);
            if (dr00.getTyp() == null) {
                for (com.pullenti.ner.Slot s : dr00.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_DATE) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_SOURCE)) {
                        if (dec.findSlot(s.getTypeName(), null, true) == null) 
                            dec.addSlot(s.getTypeName(), s.getValue(), false, 0);
                    }
                }
                dr00 = null;
            }
            if (dr00 != null) {
                rtLi.add(rt22);
                dec.addSlot(DecreeReferent.ATTR_EDITION, rt22.referent, false, 0);
            }
        }
        rtLi.add(rt);
        if (numTok != null && numTok.children != null) {
            com.pullenti.ner.Token end = rt.getEndToken();
            rt.setEndToken(numTok.children.get(0).getBeginToken().getPrevious());
            if (rt.getEndToken().isCommaAnd()) 
                rt.setEndToken(rt.getEndToken().getPrevious());
            for (int ii = 0; ii < numTok.children.size(); ii++) {
                DecreeReferent dr1 = new DecreeReferent();
                for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreeReferent.ATTR_NUMBER)) 
                        dr1.addSlot(s.getTypeName(), numTok.children.get(ii).value, false, 0).setTag(numTok.children.get(ii).getSourceText());
                    else {
                        com.pullenti.ner.Slot ss = dr1.addSlot(s.getTypeName(), s.getValue(), false, 0);
                        if (ss != null) 
                            ss.setTag(s.getTag());
                    }
                }
                com.pullenti.ner.ReferentToken rt1 = new com.pullenti.ner.ReferentToken(dr1, numTok.children.get(ii).getBeginToken(), numTok.children.get(ii).getEndToken(), null);
                if (ii == (numTok.children.size() - 1)) 
                    rt1.setEndToken(end);
                rtLi.add(rt1);
            }
        }
        if ((dts.size() == 2 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && dts.get(0).typKind == DecreeKind.STANDARD) && dts.get(1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
            for (com.pullenti.ner.Token ttt = dts.get(1).getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                if (!ttt.isCommaAnd()) 
                    break;
                com.pullenti.ner.decree.internal.DecreeToken nu = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt.getNext(), dts.get(0), false);
                if (nu == null || nu.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                    break;
                DecreeReferent dr1 = DecreeReferent._new1170(dec.getTyp());
                dr1.addNumber(nu);
                rtLi.add(new com.pullenti.ner.ReferentToken(dr1, ttt.getNext(), nu.getEndToken(), null));
                if (!ttt.isComma()) 
                    break;
                ttt = nu.getEndToken();
            }
        }
        return rtLi;
    }

    /**
     * Имя анализатора ("DECREE")
     */
    public static final String ANALYZER_NAME = "DECREE";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Законы и указы";
    }


    @Override
    public String getDescription() {
        return "Законы, указы, постановления, распоряжения и т.п.";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new DecreeAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.decree.internal.MetaDecree.GLOBALMETA, com.pullenti.ner.decree.internal.MetaDecreePart.GLOBALMETA, com.pullenti.ner.decree.internal.MetaDecreeChange.GLOBALMETA, com.pullenti.ner.decree.internal.MetaDecreeChangeValue.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.decree.internal.MetaDecree.DECREEIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decree.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecree.STANDADRIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decreestd.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecreePart.PARTIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("part.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecreePart.PARTLOCIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("document_into.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecree.PUBLISHIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("publish.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecreeChange.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decreechange.png"));
        res.put(com.pullenti.ner.decree.internal.MetaDecreeChangeValue.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decreechangevalue.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.date.DateReferent.OBJ_TYPENAME, com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, com.pullenti.ner._org.OrganizationReferent.OBJ_TYPENAME, com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, DecreeReferent.OBJ_TYPENAME)) 
            return new DecreeReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, DecreePartReferent.OBJ_TYPENAME)) 
            return new DecreePartReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, DecreeChangeReferent.OBJ_TYPENAME)) 
            return new DecreeChangeReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, DecreeChangeValueReferent.OBJ_TYPENAME)) 
            return new DecreeChangeValueReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 10;
    }


    public static com.pullenti.ner.core.AnalyzerData getData(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        return t.kit.getAnalyzerDataByAnalyzerName(ANALYZER_NAME);
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        com.pullenti.ner.decree.internal.DecreeToken baseTyp = null;
        com.pullenti.ner.Referent ref0 = null;
        com.pullenti.ner.core.TerminCollection aliases = new com.pullenti.ner.core.TerminCollection();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.Referent r = t.getReferent();
            if (r == null) 
                continue;
            if (!(r instanceof com.pullenti.ner._org.OrganizationReferent)) 
                continue;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (!rt.getBeginToken().chars.isAllUpper() || rt.getBeginToken().getLengthChar() > 4) 
                continue;
            com.pullenti.ner.decree.internal.DecreeToken dtr = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(rt.getBeginToken(), null, false);
            if (dtr == null || dtr.typKind != DecreeKind.KODEX) 
                continue;
            if (rt.getBeginToken() == rt.getEndToken()) {
            }
            else if (rt.getBeginToken().getNext() == rt.getEndToken() && (rt.getEndToken().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
            }
            else 
                continue;
            int cou = 0;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(tt, null, false, false);
                if (pt != null && pt.values.size() > 0) {
                    t = kit.debedToken(rt);
                    break;
                }
                if ((++cou) > 10) 
                    break;
            }
        }
        int lastDecDist = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext(),lastDecDist++) {
            if (t.isIgnored()) 
                continue;
            java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t, null, 10, lastDecDist > 1000);
            com.pullenti.ner.core.TerminToken tok = aliases.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && tok.getBeginToken() == tok.getEndToken() && tok.chars.isAllLower()) {
                boolean ok = false;
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && ((t.getEndChar() - tt.getEndChar()) < 20); tt = tt.getPrevious()) {
                    com.pullenti.ner.decree.internal.PartToken p = com.pullenti.ner.decree.internal.PartToken.tryAttach(tt, null, false, false);
                    if (p != null && p.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX && p.getEndToken().getNext() == t) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) 
                    tok = null;
            }
            if (tok != null) {
                com.pullenti.ner.ReferentToken rt0 = tryAttachApproved(t, ad);
                if (rt0 != null) 
                    tok = null;
            }
            if (tok != null) {
                DecreeReferent dec0 = (DecreeReferent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, DecreeReferent.class);
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.Referent.class), tok.getBeginToken(), tok.getEndToken(), null);
                if (dec0 != null && (rt0.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && (rt0.getEndToken().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(dec0.getSlotValue(DecreeReferent.ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
                    com.pullenti.ner.geo.GeoReferent geo1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt0.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    if (geo0 == null) {
                        dec0.addSlot(DecreeReferent.ATTR_GEO, geo1, false, 0);
                        rt0.setEndToken(rt0.getEndToken().getNext());
                    }
                    else if (geo0 == geo1) 
                        rt0.setEndToken(rt0.getEndToken().getNext());
                    else 
                        continue;
                }
                kit.embedToken(rt0);
                t = rt0;
                rt0.miscAttrs = 1;
                lastDecDist = 0;
                continue;
            }
            if (dts == null || dts.size() == 0 || ((dts.size() <= 2 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP))) {
                com.pullenti.ner.ReferentToken rt0 = tryAttachApproved(t, ad);
                if (rt0 != null) {
                    rt0.referent = ad.registerReferent(rt0.referent);
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter(rt0.getEndToken().getNext());
                    if (mt != null) {
                        if (aliases != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt0.referent, false);
                            aliases.add(term);
                        }
                        rt0.setEndToken(mt.getEndToken());
                    }
                    else if ((((mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(rt0.tag, com.pullenti.ner.MetaToken.class)))) != null) {
                        if (aliases != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt0.referent, false);
                            aliases.add(term);
                        }
                    }
                    kit.embedToken(rt0);
                    lastDecDist = 0;
                    t = rt0;
                    continue;
                }
                if (dts == null || dts.size() == 0) 
                    continue;
            }
            if (dts.get(0).isNewlineAfter() && dts.get(0).isNewlineBefore()) {
                boolean ignore = false;
                if (t == kit.firstToken) 
                    ignore = true;
                else if ((dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG && dts.size() > 1 && dts.get(1).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) && dts.get(1).isWhitespaceAfter()) 
                    ignore = true;
                if (ignore) {
                    t = dts.get(dts.size() - 1).getEndToken();
                    continue;
                }
            }
            if (baseTyp == null) {
                for (com.pullenti.ner.decree.internal.DecreeToken dd : dts) {
                    if (dd.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                        baseTyp = dd;
                        break;
                    }
                }
            }
            if (dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && com.pullenti.ner.decree.internal.DecreeToken.getKind(dts.get(0).value) == DecreeKind.PUBLISHER) {
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this.tryAttachPulishers(dts);
                if (rts != null) {
                    for (int i = 0; i < rts.size(); i++) {
                        com.pullenti.ner.ReferentToken rtt = rts.get(i);
                        if (rtt.referent instanceof DecreePartReferent) 
                            ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(rtt.referent, DecreePartReferent.class)).setOwner((DecreeReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(((DecreePartReferent)com.pullenti.unisharp.Utils.cast(rtt.referent, DecreePartReferent.class)).getOwner()), DecreeReferent.class));
                        rtt.referent = ad.registerReferent(rtt.referent);
                        kit.embedToken(rtt);
                        t = rtt;
                        if ((rtt.referent instanceof DecreeReferent) && ((i + 1) < rts.size()) && (rts.get(i + 1).referent instanceof DecreePartReferent)) 
                            rts.get(i + 1).setBeginToken(t);
                        lastDecDist = 0;
                    }
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter(t.getNext());
                    if (mt != null) {
                        for (com.pullenti.ner.Token tt = dts.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                            if (tt.isComma()) 
                                continue;
                            DecreeReferent d = (DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), DecreeReferent.class);
                            if (d != null) {
                                if (aliases != null) {
                                    com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                                    term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), d, false);
                                    aliases.add(term);
                                }
                                t = mt.getEndToken();
                            }
                            break;
                        }
                    }
                }
                continue;
            }
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rtli = tryAttach(dts, baseTyp, ad);
            if (rtli == null || ((rtli.size() == 1 && (dts.size() < 3) && com.pullenti.unisharp.Utils.stringsEq(dts.get(0).value, "РЕГЛАМЕНТ")))) {
                com.pullenti.ner.ReferentToken rt = tryAttachApproved(t, ad);
                if (rt != null) {
                    rtli = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                    rtli.add(rt);
                }
            }
            if (rtli != null) {
                for (int ii = 0; ii < rtli.size(); ii++) {
                    com.pullenti.ner.ReferentToken rt = rtli.get(ii);
                    lastDecDist = 0;
                    rt.referent = ad.registerReferent(rt.referent);
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter(rt.getEndToken().getNext());
                    if (mt != null) {
                        if (aliases != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt.referent, false);
                            aliases.add(term);
                        }
                        rt.setEndToken(mt.getEndToken());
                    }
                    else if ((((mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(rt.tag, com.pullenti.ner.MetaToken.class)))) != null) {
                        if (aliases != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, null, false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt.referent, false);
                            aliases.add(term);
                        }
                    }
                    ref0 = rt.referent;
                    kit.embedToken(rt);
                    t = rt;
                    if ((ii + 1) < rtli.size()) {
                        if (rt.getEndToken().getNext() == rtli.get(ii + 1).getBeginToken()) 
                            rtli.get(ii + 1).setBeginToken(rt);
                    }
                }
            }
            else if (dts.size() == 1 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                if (dts.get(0).chars.isCapitalUpper() && !dts.get(0).isDoubtful) {
                    lastDecDist = 0;
                    if (baseTyp != null && dts.get(0).ref != null) {
                        DecreeReferent drr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(dts.get(0).ref.getReferent(), DecreeReferent.class);
                        if (drr != null) {
                            if (com.pullenti.unisharp.Utils.stringsEq(baseTyp.value, drr.getTyp0()) || com.pullenti.unisharp.Utils.stringsEq(baseTyp.value, drr.getTyp())) 
                                continue;
                        }
                    }
                    com.pullenti.ner.ReferentToken rt0 = com.pullenti.ner.decree.internal.DecreeToken._findBackTyp(dts.get(0).getBeginToken().getPrevious(), dts.get(0).value);
                    if (rt0 != null) {
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(rt0.referent, dts.get(0).getBeginToken(), dts.get(0).getEndToken(), null);
                        kit.embedToken(rt);
                        t = rt;
                        rt.tag = rt0.referent;
                    }
                }
            }
        }
        if (ad.getReferents().size() > 0) {
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isIgnored()) 
                    continue;
                DecreeReferent dr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), DecreeReferent.class);
                if (dr == null) 
                    continue;
                java.util.ArrayList<DecreeReferent> li = null;
                for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                    if (!tt.isCommaAnd()) 
                        break;
                    if (tt.getNext() == null || !(tt.getNext().getReferent() instanceof DecreeReferent)) 
                        break;
                    if (li == null) {
                        li = new java.util.ArrayList<DecreeReferent>();
                        li.add(dr);
                    }
                    dr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getNext().getReferent(), DecreeReferent.class);
                    li.add(dr);
                    dr.tag = null;
                    tt = tt.getNext();
                    if (dr.getDate() != null) {
                        java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class)).getBeginToken(), null, 10, false);
                        if (dts != null) {
                            for (com.pullenti.ner.decree.internal.DecreeToken dt : dts) {
                                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                                    dr.tag = dr;
                            }
                        }
                    }
                }
                if (li == null) 
                    continue;
                int i;
                for (i = li.size() - 1; i > 0; i--) {
                    if (com.pullenti.unisharp.Utils.stringsEq(li.get(i).getTyp(), li.get(i - 1).getTyp())) {
                        if (li.get(i).getDate() != null && li.get(i).tag != null && li.get(i - 1).getDate() == null) 
                            li.get(i - 1).addSlot(DecreeReferent.ATTR_DATE, li.get(i).getSlotValue(DecreeReferent.ATTR_DATE), false, 0);
                    }
                }
                for (i = 0; i < (li.size() - 1); i++) {
                    if (com.pullenti.unisharp.Utils.stringsEq(li.get(i).getTyp(), li.get(i + 1).getTyp())) {
                        com.pullenti.ner.Slot sl = li.get(i).findSlot(DecreeReferent.ATTR_SOURCE, null, true);
                        if (sl != null && li.get(i + 1).findSlot(DecreeReferent.ATTR_SOURCE, null, true) == null) 
                            li.get(i + 1).addSlot(sl.getTypeName(), sl.getValue(), false, 0);
                    }
                }
                for (i = 0; i < li.size(); i++) {
                    if (li.get(i).getName() != null) 
                        break;
                }
                if (i == (li.size() - 1)) {
                    for (i = li.size() - 1; i > 0; i--) {
                        if (com.pullenti.unisharp.Utils.stringsEq(li.get(i - 1).getTyp(), li.get(i).getTyp())) 
                            li.get(i - 1).addName(li.get(i));
                    }
                }
            }
        }
        java.util.ArrayList<DecreePartReferent> undefinedDecrees = new java.util.ArrayList<DecreePartReferent>();
        DecreeChangeReferent rootChange = null;
        DecreeChangeReferent lastChange = null;
        java.util.ArrayList<com.pullenti.ner.Referent> changeStack = new java.util.ArrayList<com.pullenti.ner.Referent>();
        boolean expireRegime = false;
        int hasStartChange = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> dts = null;
            if (t.isNewlineBefore() && (t instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "25")) {
            }
            com.pullenti.ner.decree.internal.DecreeChangeToken dcht = null;
            if (t.isNewlineBefore()) 
                dcht = com.pullenti.ner.decree.internal.DecreeChangeToken.tryAttach(t, rootChange, false, changeStack, false);
            if (dcht != null && dcht.isStart()) {
                if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.STARTMULTU) {
                    expireRegime = false;
                    hasStartChange = 3;
                    rootChange = null;
                }
                else if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.SINGLE) {
                    com.pullenti.ner.decree.internal.DecreeChangeToken dcht1 = com.pullenti.ner.decree.internal.DecreeChangeToken.tryAttach(dcht.getEndToken().getNext(), rootChange, false, changeStack, false);
                    if (dcht1 != null && dcht1.isStart()) {
                        hasStartChange = 2;
                        if (dcht.decreeTok != null && dcht.decree != null) {
                            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dcht.decree, dcht.decreeTok.getBeginToken(), dcht.decreeTok.getEndToken(), null);
                            kit.embedToken(rt);
                            t = rt;
                            if (dcht.getEndChar() == t.getEndChar()) 
                                dcht.setEndToken(t);
                        }
                    }
                }
                else if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.STARTSINGLE && dcht.decree != null && !expireRegime) {
                    expireRegime = false;
                    hasStartChange = 2;
                    if (dcht.decreeTok != null) {
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dcht.decree, dcht.decreeTok.getBeginToken(), dcht.decreeTok.getEndToken(), null);
                        kit.embedToken(rt);
                        t = rt;
                        if (dcht.getEndChar() == t.getEndChar()) 
                            dcht.setEndToken(t);
                    }
                    else 
                        rootChange = null;
                }
                if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.STARTSINGLE && rootChange != null && dcht.decree == null) 
                    hasStartChange = 2;
                else if ((dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.SINGLE && dcht.decree != null && dcht.getEndToken().isChar(':')) && dcht.isNewlineAfter()) 
                    hasStartChange = 2;
                if (hasStartChange <= 0) {
                    dts = com.pullenti.ner.decree.internal.PartToken.tryAttachList(t, false, 40);
                    changeStack.clear();
                }
                else {
                    if (dcht.decree != null) {
                        changeStack.clear();
                        changeStack.add(dcht.decree);
                    }
                    else if (dcht.actKind == DecreeChangeKind.EXPIRE && dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.STARTMULTU) 
                        expireRegime = true;
                    dts = dcht.parts;
                }
            }
            else {
                dts = com.pullenti.ner.decree.internal.PartToken.tryAttachList(t, false, 40);
                if (dcht == null && t.isNewlineBefore()) {
                    expireRegime = false;
                    hasStartChange--;
                }
            }
            if (dts != null) {
            }
            java.util.ArrayList<com.pullenti.ner.MetaToken> rts = tryAttachParts(dts, baseTyp, (hasStartChange > 0 && changeStack.size() > 0 ? changeStack.get(0) : null));
            if (rts != null) {
            }
            java.util.ArrayList<DecreePartReferent> dprs = null;
            java.util.HashMap<DecreePartReferent, DecreePartReferent> diaps = null;
            java.util.HashMap<Integer, com.pullenti.ner.Token> begs = null;
            java.util.HashMap<Integer, com.pullenti.ner.Token> ends = null;
            if (rts != null) {
                for (com.pullenti.ner.MetaToken kp : rts) {
                    java.util.ArrayList<DecreePartReferent> dprList = (java.util.ArrayList<DecreePartReferent>)com.pullenti.unisharp.Utils.cast(kp.tag, java.util.ArrayList.class);
                    if (dprList == null) 
                        continue;
                    for (int i = 0; i < dprList.size(); i++) {
                        DecreePartReferent dr = dprList.get(i);
                        if (dr.getOwner() == null && dr.getClause() != null && dr.getLocalTyp() == null) {
                            if (!undefinedDecrees.contains(dr)) 
                                undefinedDecrees.add(dr);
                        }
                        if (dr.getOwner() != null && dr.getClause() != null) {
                            for (DecreePartReferent d : undefinedDecrees) {
                                d.setOwner(dr.getOwner());
                            }
                            undefinedDecrees.clear();
                        }
                        if (dcht != null && changeStack.size() > 0) {
                            while (changeStack.size() > 0) {
                                if (dr.isAllItemsLessLevel(changeStack.get(0), false)) {
                                    if (changeStack.get(0) instanceof DecreePartReferent) 
                                        dr.addHighLevelInfo((DecreePartReferent)com.pullenti.unisharp.Utils.cast(changeStack.get(0), DecreePartReferent.class));
                                    break;
                                }
                                if (changeStack.get(0) instanceof DecreePartReferent) 
                                    changeStack.remove(0);
                            }
                        }
                        if (lastChange != null && lastChange.getOwners().size() > 0) {
                            DecreePartReferent dr0 = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(lastChange.getOwners().get(0), DecreePartReferent.class);
                            if (dr0 != null && dr.getOwner() == dr0.getOwner()) {
                                int mle = dr.getMinLevel();
                                if (mle == 0 || mle <= com.pullenti.ner.decree.internal.PartToken._getRank(com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE)) {
                                }
                                else 
                                    dr.addHighLevelInfo(dr0);
                            }
                        }
                        dr = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(dr), DecreePartReferent.class);
                        if (dprs == null) 
                            dprs = new java.util.ArrayList<DecreePartReferent>();
                        dprs.add(dr);
                        com.pullenti.ner.ReferentToken rt;
                        if (i == 0) 
                            rt = new com.pullenti.ner.ReferentToken(dr, kp.getBeginToken(), kp.getEndToken(), null);
                        else 
                            rt = new com.pullenti.ner.ReferentToken(dr, t, t, null);
                        kit.embedToken(rt);
                        t = rt;
                        if (dprs.size() == 2 && t.getPrevious() != null && t.getPrevious().isHiphen()) {
                            if (diaps == null) 
                                diaps = new java.util.HashMap<DecreePartReferent, DecreePartReferent>();
                            if (!diaps.containsKey(dprs.get(0))) 
                                diaps.put(dprs.get(0), dprs.get(1));
                        }
                        if (begs == null) 
                            begs = new java.util.HashMap<Integer, com.pullenti.ner.Token>();
                        if (!begs.containsKey(t.getBeginChar())) 
                            begs.put(t.getBeginChar(), t);
                        else 
                            begs.put(t.getBeginChar(), t);
                        if (ends == null) 
                            ends = new java.util.HashMap<Integer, com.pullenti.ner.Token>();
                        if (!ends.containsKey(t.getEndChar())) 
                            ends.put(t.getEndChar(), t);
                        else 
                            ends.put(t.getEndChar(), t);
                        if (dcht != null) {
                            if (dcht.getBeginChar() == t.getBeginChar()) 
                                dcht.setBeginToken(t);
                            if (dcht.getEndChar() == t.getEndChar()) 
                                dcht.setEndToken(t);
                            if (t.getEndChar() > dcht.getEndChar()) 
                                dcht.setEndToken(t);
                        }
                    }
                }
            }
            if (dts != null && dts.size() > 0 && dts.get(dts.size() - 1).getEndChar() > t.getEndChar()) 
                t = dts.get(dts.size() - 1).getEndToken();
            if (dcht != null && hasStartChange > 0) {
                if (dcht.getEndChar() > t.getEndChar()) 
                    t = dcht.getEndToken();
                java.util.ArrayList<com.pullenti.ner.ReferentToken> chrt = null;
                if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.STARTMULTU) {
                    rootChange = null;
                    changeStack.clear();
                    if (dcht.decree != null) 
                        changeStack.add(dcht.decree);
                    if (dprs != null && dprs.size() > 0) {
                        if (changeStack.size() == 0 && dprs.get(0).getOwner() != null) 
                            changeStack.add(dprs.get(0).getOwner());
                        changeStack.add(0, dprs.get(0));
                    }
                    if (changeStack.size() > 0 || dcht.decree != null) {
                        rootChange = (DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(DecreeChangeReferent._new1171(DecreeChangeKind.CONTAINER)), DecreeChangeReferent.class);
                        if (changeStack.size() > 0) 
                            rootChange.addSlot(DecreeChangeReferent.ATTR_OWNER, changeStack.get(0), false, 0);
                        else 
                            rootChange.addSlot(DecreeChangeReferent.ATTR_OWNER, dcht.decree, false, 0);
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(rootChange, dcht.getBeginToken(), dcht.getEndToken(), null);
                        if (rt.getEndToken().isChar(':')) 
                            rt.setEndToken(rt.getEndToken().getPrevious());
                        kit.embedToken(rt);
                        t = rt;
                        if (t.getNext() != null && t.getNext().isChar(':')) 
                            t = t.getNext();
                    }
                    continue;
                }
                if (dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.SINGLE && dprs != null && dprs.size() == 1) {
                    while (changeStack.size() > 0) {
                        if (dprs.get(0).isAllItemsLessLevel(changeStack.get(0), true)) 
                            break;
                        else 
                            changeStack.remove(0);
                    }
                    changeStack.add(0, dprs.get(0));
                    if (dprs.get(0).getOwner() != null && changeStack.get(changeStack.size() - 1) != dprs.get(0).getOwner()) {
                        changeStack.clear();
                        changeStack.add(0, dprs.get(0).getOwner());
                        changeStack.add(0, dprs.get(0));
                    }
                    continue;
                }
                if (dprs == null && dcht.realPart != null) {
                    dprs = new java.util.ArrayList<DecreePartReferent>();
                    dprs.add(dcht.realPart);
                }
                if (dprs != null && dprs.size() > 0) {
                    chrt = com.pullenti.ner.decree.internal.DecreeChangeToken.attachReferents(dprs.get(0), dcht);
                    if (chrt == null && expireRegime) {
                        chrt = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                        DecreeChangeReferent dcr = DecreeChangeReferent._new1171(DecreeChangeKind.EXPIRE);
                        chrt.add(new com.pullenti.ner.ReferentToken(dcr, dcht.getBeginToken(), dcht.getEndToken(), null));
                    }
                }
                else if (dcht.actKind == DecreeChangeKind.APPEND) {
                    boolean ee = false;
                    if (dcht.partTyp != com.pullenti.ner.decree.internal.PartToken.ItemType.UNDEFINED) {
                        for (com.pullenti.ner.Referent ss : changeStack) {
                            if (ss instanceof DecreePartReferent) {
                                if (((DecreePartReferent)com.pullenti.unisharp.Utils.cast(ss, DecreePartReferent.class)).isAllItemsOverThisLevel(dcht.partTyp)) {
                                    ee = true;
                                    chrt = com.pullenti.ner.decree.internal.DecreeChangeToken.attachReferents(ss, dcht);
                                    break;
                                }
                            }
                            else if (ss instanceof DecreeReferent) {
                                ee = true;
                                chrt = com.pullenti.ner.decree.internal.DecreeChangeToken.attachReferents(ss, dcht);
                                break;
                            }
                        }
                    }
                    if (lastChange != null && !ee && lastChange.getOwners().size() > 0) 
                        chrt = com.pullenti.ner.decree.internal.DecreeChangeToken.attachReferents(lastChange.getOwners().get(0), dcht);
                }
                if (dprs == null && ((dcht.hasName || dcht.typ == com.pullenti.ner.decree.internal.DecreeChangeTokenTyp.VALUE || dcht.changeVal != null)) && changeStack.size() > 0) 
                    chrt = com.pullenti.ner.decree.internal.DecreeChangeToken.attachReferents(changeStack.get(0), dcht);
                if ((chrt == null && ((expireRegime || dcht.actKind == DecreeChangeKind.EXPIRE)) && dcht.decree != null) && dprs == null) {
                    chrt = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                    DecreeChangeReferent dcr = DecreeChangeReferent._new1171(DecreeChangeKind.EXPIRE);
                    dcr.addSlot(DecreeChangeReferent.ATTR_OWNER, dcht.decree, false, 0);
                    chrt.add(new com.pullenti.ner.ReferentToken(dcr, dcht.getBeginToken(), dcht.getEndToken(), null));
                    for (com.pullenti.ner.Token tt = dcht.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getNext() == null) 
                            break;
                        if (tt.isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null) {
                                tt = br.getEndToken();
                                chrt.get(chrt.size() - 1).setEndToken(tt);
                                continue;
                            }
                        }
                        if (!tt.isCommaAnd() && !tt.isChar(';')) 
                            break;
                        tt = tt.getNext();
                        if (tt.getReferent() instanceof DecreeReferent) {
                            dcr = DecreeChangeReferent._new1171(DecreeChangeKind.EXPIRE);
                            dcr.addSlot(DecreeChangeReferent.ATTR_OWNER, tt.getReferent(), false, 0);
                            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dcr, tt, tt, null);
                            if (tt.getNext() != null && tt.getNext().isChar('(')) {
                                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                                if (br != null) 
                                    rt.setEndToken((tt = br.getEndToken()));
                            }
                            chrt.add(rt);
                            continue;
                        }
                        break;
                    }
                }
                if (chrt != null) {
                    for (com.pullenti.ner.ReferentToken rt : chrt) {
                        rt.referent = ad.registerReferent(rt.referent);
                        if (rt.referent instanceof DecreeChangeReferent) {
                            lastChange = (DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DecreeChangeReferent.class);
                            if (dprs != null) {
                                int ii;
                                for (ii = 0; ii < (dprs.size() - 1); ii++) {
                                    lastChange.addSlot(DecreeChangeReferent.ATTR_OWNER, dprs.get(ii), false, 0);
                                }
                                if (diaps != null) {
                                    for (java.util.Map.Entry<DecreePartReferent, DecreePartReferent> kp : diaps.entrySet()) {
                                        java.util.ArrayList<DecreePartReferent> diap = com.pullenti.ner.decree.internal.PartToken.tryCreateBetween(kp.getKey(), kp.getValue());
                                        if (diap != null) {
                                            for (DecreePartReferent d : diap) {
                                                com.pullenti.ner.Referent dd = ad.registerReferent(d);
                                                lastChange.addSlot(DecreeChangeReferent.ATTR_OWNER, dd, false, 0);
                                            }
                                        }
                                    }
                                }
                                for (; ii < dprs.size(); ii++) {
                                    lastChange.addSlot(DecreeChangeReferent.ATTR_OWNER, dprs.get(ii), false, 0);
                                }
                            }
                        }
                        if (begs != null && begs.containsKey(rt.getBeginChar())) 
                            rt.setBeginToken(begs.get(rt.getBeginChar()));
                        if (ends != null && ends.containsKey(rt.getEndChar())) 
                            rt.setEndToken(ends.get(rt.getEndChar()));
                        if (rootChange != null && (rt.referent instanceof DecreeChangeReferent)) 
                            rootChange.addSlot(DecreeChangeReferent.ATTR_CHILD, rt.referent, false, 0);
                        kit.embedToken(rt);
                        t = rt;
                        if (begs == null) 
                            begs = new java.util.HashMap<Integer, com.pullenti.ner.Token>();
                        if (!begs.containsKey(t.getBeginChar())) 
                            begs.put(t.getBeginChar(), t);
                        else 
                            begs.put(t.getBeginChar(), t);
                        if (ends == null) 
                            ends = new java.util.HashMap<Integer, com.pullenti.ner.Token>();
                        if (!ends.containsKey(t.getEndChar())) 
                            ends.put(t.getEndChar(), t);
                        else 
                            ends.put(t.getEndChar(), t);
                    }
                }
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.tag != null && (t instanceof com.pullenti.ner.ReferentToken) && (t.tag instanceof DecreeReferent)) {
                t = kit.debedToken(t);
                if (t == null) 
                    break;
            }
        }
    }

    public static com.pullenti.ner.MetaToken _checkAliasAfter(com.pullenti.ner.Token t) {
        if ((t != null && t.isChar('<') && t.getNext() != null) && t.getNext().getNext() != null && t.getNext().getNext().isChar('>')) 
            t = t.getNext().getNext().getNext();
        if (t == null || t.getNext() == null || !t.isChar('(')) 
            return null;
        t = t.getNext();
        if (t.isValue("ДАЛЕЕ", "ДАЛІ")) {
        }
        else 
            return null;
        t = t.getNext();
        if (t != null && !t.chars.isLetter()) 
            t = t.getNext();
        if (t == null) 
            return null;
        com.pullenti.ner.Token t1 = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            else if (tt.isChar(')')) {
                t1 = tt.getPrevious();
                break;
            }
        }
        if (t1 == null) 
            return null;
        return new com.pullenti.ner.MetaToken(t, t1.getNext(), null);
    }

    public static com.pullenti.ner.ReferentToken tryAttachApproved(com.pullenti.ner.Token t, com.pullenti.ner.core.AnalyzerData ad) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
            br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
        else if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && t.getPrevious().getLengthChar() == 1 && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), true, false)) 
            br = com.pullenti.ner.core.BracketHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
        if (br != null && br.getLengthChar() > 20) {
            com.pullenti.ner.ReferentToken rt0 = _tryAttachApproved(br.getEndToken().getNext(), ad, false);
            if (rt0 != null) {
                DecreeReferent dr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, DecreeReferent.class);
                rt0.setBeginToken(br.getBeginToken());
                String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                if (dr.getTyp() == null) {
                    com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(br.getBeginToken().getNext(), null, false);
                    if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                        dr.setTyp(dt.value);
                        if (dt.getEndToken().getNext() != null && dt.getEndToken().getNext().isValue("О", null)) 
                            nam = com.pullenti.ner.core.MiscHelper.getTextValue(dt.getEndToken().getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                    }
                }
                if (nam != null) 
                    dr.addNameStr(nam);
                return rt0;
            }
        }
        if (!t.chars.isCyrillicLetter() || t.chars.isAllLower()) 
            return null;
        com.pullenti.ner.Token tt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false);
        if (tt == null || tt.getNext() == null) 
            return null;
        int cou = 0;
        com.pullenti.ner.MetaToken alias = null;
        com.pullenti.ner.Token aliasT0 = null;
        for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
            if ((++cou) > 100) 
                break;
            if (tt.isNewlineBefore()) {
                if (tt.isValue("ИСТОЧНИК", null)) 
                    break;
            }
            if ((((tt instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class)).getValue(), "1"))) || tt.isValue("ДРУГОЙ", null) || tt.isValue("ИНОЙ", null)) {
                if (tt.getNext() != null && tt.getNext().isValue("СТОРОНА", null)) 
                    return null;
            }
            if (tt.getWhitespacesBeforeCount() > 15) 
                break;
            if (tt.isChar('(')) {
                com.pullenti.ner.MetaToken mt = _checkAliasAfter(tt);
                if (mt != null) {
                    aliasT0 = tt;
                    alias = mt;
                    tt = mt.getEndToken();
                    continue;
                }
            }
            if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, false) != null && tt.chars.isCapitalUpper()) 
                break;
            com.pullenti.ner.ReferentToken rt0 = _tryAttachApproved(tt, ad, true);
            if (rt0 != null) {
                if (tt.isChar('(') && tt.getNext().isValue("ВВЕСТИ", null)) 
                    return null;
                com.pullenti.ner.Token t1 = tt.getPrevious();
                if (aliasT0 != null) 
                    t1 = aliasT0.getPrevious();
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
                com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                if (dt != null && dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && ((DecreeReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, DecreeReferent.class)).getTyp() == null) {
                    ((DecreeReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, DecreeReferent.class)).setTyp(dt.value);
                    if (dt.getEndToken().getNext() != null && dt.getEndToken().getNext().isValue("О", "ПРО")) 
                        nam = com.pullenti.ner.core.MiscHelper.getTextValue(dt.getEndToken().getNext(), t1, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER);
                }
                DecreeReferent dec = (DecreeReferent)com.pullenti.unisharp.Utils.cast(rt0.referent, DecreeReferent.class);
                if (nam != null) 
                    dec.addNameStr(nam);
                rt0.setBeginToken(t);
                rt0.tag = alias;
                if (dec.findSlot(DecreeReferent.ATTR_SOURCE, null, true) == null) {
                    if (t.getPrevious() != null && t.getPrevious().isValue("В", null) && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken)) {
                        if (t.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) 
                            dec.addSlot(DecreeReferent.ATTR_SOURCE, t.getPrevious().getPrevious().getReferent(), false, 0);
                    }
                }
                return rt0;
            }
            if (tt.isChar('.')) 
                break;
            if (tt.isNewlineBefore() && tt.getPrevious() != null && tt.getPrevious().isChar('.')) 
                break;
        }
        return null;
    }

    public static com.pullenti.ner.ReferentToken _tryAttachApproved(com.pullenti.ner.Token t, com.pullenti.ner.core.AnalyzerData ad, boolean mustBeComma) {
        if (t == null || t.getNext() == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        if (!t.isCharOf("(,")) {
            if (mustBeComma) 
                return null;
        }
        else 
            t = t.getNext();
        boolean ok = false;
        for (; t != null; t = t.getNext()) {
            if (t.isCommaAnd() || t.getMorph()._getClass().isPreposition()) 
                continue;
            if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class)).isCity()) 
                continue;
            if ((((((((t.isValue("УТВ", null) || t.isValue("УТВЕРЖДАТЬ", "СТВЕРДЖУВАТИ") || t.isValue("УТВЕРДИТЬ", "ЗАТВЕРДИТИ")) || t.isValue("УТВЕРЖДЕННЫЙ", "ЗАТВЕРДЖЕНИЙ") || t.isValue("ЗАТВЕРДЖУВАТИ", null)) || t.isValue("СТВЕРДИТИ", null) || t.isValue("ЗАТВЕРДИТИ", null)) || t.isValue("ПРИНЯТЬ", "ПРИЙНЯТИ") || t.isValue("ПРИНЯТЫЙ", "ПРИЙНЯТИЙ")) || t.isValue("ВВОДИТЬ", "ВВОДИТИ") || t.isValue("ВВЕСТИ", null)) || t.isValue("ВВЕДЕННЫЙ", "ВВЕДЕНИЙ") || t.isValue("ПОДПИСАТЬ", "ПІДПИСАТИ")) || t.isValue("ПОДПИСЫВАТЬ", "ПІДПИСУВАТИ") || t.isValue("ЗАКЛЮЧИТЬ", "УКЛАСТИ")) || t.isValue("ЗАКЛЮЧАТЬ", "УКЛАДАТИ")) {
                ok = true;
                if (t.getNext() != null && t.getNext().isChar('.')) 
                    t = t.getNext();
            }
            else if (t.isValue("ДЕЙСТВИЕ", null) || t.isValue("ДІЯ", null)) {
            }
            else 
                break;
        }
        if (!ok) 
            return null;
        if (t == null) 
            return null;
        com.pullenti.ner.core.AnalysisKit kit = t.kit;
        Object olev = null;
        int lev = 0;
        com.pullenti.unisharp.Outargwrapper<Object> wrapolev1175 = new com.pullenti.unisharp.Outargwrapper<Object>();
        boolean inoutres1176 = com.pullenti.unisharp.Utils.tryGetValue(kit.miscData, "dovr", wrapolev1175);
        olev = wrapolev1175.value;
        if (!inoutres1176) 
            kit.miscData.put("dovr", (lev = 1));
        else {
            lev = (int)olev;
            if (lev > 2) 
                return null;
            lev++;
            kit.miscData.put("dovr", lev);
        }
        try {
            java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(t, null, 10, false);
            if (dts == null) 
                return null;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rt = tryAttach(dts, null, ad);
            if (rt == null) {
                int hasDate = 0;
                int hasNum = 0;
                int hasOwn = 0;
                int hasTyp = 0;
                int ii;
                for (ii = 0; ii < dts.size(); ii++) {
                    if (dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                        hasNum++;
                    else if ((dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE && dts.get(ii).ref != null && (dts.get(ii).ref.referent instanceof com.pullenti.ner.date.DateReferent)) && ((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(dts.get(ii).ref.referent, com.pullenti.ner.date.DateReferent.class)).getDt() != null) 
                        hasDate++;
                    else if (dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER || dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) 
                        hasOwn++;
                    else if (dts.get(ii).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                        hasTyp++;
                    else 
                        break;
                }
                if (ii >= dts.size() && hasOwn > 0 && ((hasDate == 1 || hasNum == 1))) {
                    DecreeReferent dr = new DecreeReferent();
                    for (com.pullenti.ner.decree.internal.DecreeToken dt : dts) {
                        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) 
                            dr.addDate(dt);
                        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) 
                            dr.addNumber(dt);
                        else if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
                            dr.addSlot(DecreeReferent.ATTR_TYPE, dt.value, false, 0);
                        else {
                            Object val = dt.value;
                            if (dt.ref != null && dt.ref.referent != null) 
                                val = dt.ref.referent;
                            dr.addSlot(DecreeReferent.ATTR_SOURCE, val, false, 0).setTag(dt.getSourceText());
                            if (dt.ref != null && (dt.ref.referent instanceof com.pullenti.ner.person.PersonPropertyReferent)) 
                                dr.addExtReferent(dt.ref);
                        }
                    }
                    rt = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                    rt.add(new com.pullenti.ner.ReferentToken(dr, dts.get(0).getBeginToken(), dts.get(dts.size() - 1).getEndToken(), null));
                }
            }
            if (((rt == null && dts.size() == 1 && dts.get(0).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) && dts.get(0).ref != null && (dts.get(0).ref.referent instanceof com.pullenti.ner.date.DateReferent)) && ((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(dts.get(0).ref.referent, com.pullenti.ner.date.DateReferent.class)).getDt() != null) {
                DecreeReferent dr = new DecreeReferent();
                dr.addDate(dts.get(0));
                rt = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
                rt.add(new com.pullenti.ner.ReferentToken(dr, dts.get(0).getBeginToken(), dts.get(dts.size() - 1).getEndToken(), null));
            }
            if (rt == null) 
                return null;
            if (t0.isChar('(') && rt.get(0).getEndToken().getNext() != null && rt.get(0).getEndToken().getNext().isChar(')')) 
                rt.get(0).setEndToken(rt.get(0).getEndToken().getNext());
            rt.get(0).setBeginToken(t0);
            return rt.get(0);
        } finally {
            lev--;
            if (lev < 0) 
                lev = 0;
            kit.miscData.put("dovr", lev);
        }
    }

    public static DecreeReferent _getDecree(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.ReferentToken)) 
            return null;
        com.pullenti.ner.Referent r = t.getReferent();
        if (r instanceof DecreeReferent) 
            return (DecreeReferent)com.pullenti.unisharp.Utils.cast(r, DecreeReferent.class);
        if (r instanceof DecreePartReferent) 
            return ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(r, DecreePartReferent.class)).getOwner();
        return null;
    }

    public static com.pullenti.ner.Token _checkOtherTyp(com.pullenti.ner.Token t, boolean first) {
        if (t == null) 
            return null;
        com.pullenti.ner.decree.internal.DecreeToken dit = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
        com.pullenti.ner.core.NounPhraseToken npt = null;
        if (dit == null) {
            npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt != null && npt.getBeginToken() != npt.getEndToken()) 
                dit = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(npt.getEndToken(), null, false);
        }
        if (dit != null && dit.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
            if (dit.chars.isCapitalUpper() || first) {
                dit.getEndToken().tag = dit.value;
                return dit.getEndToken();
            }
            else 
                return null;
        }
        if (npt != null) 
            t = npt.getEndToken();
        if (t.chars.isCapitalUpper() || first) {
            if (t.getPrevious() != null && t.getPrevious().isChar('.') && !first) 
                return null;
            com.pullenti.ner.Token tt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false);
            if (tt != null) 
                return tt;
        }
        return null;
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttachPulishers(java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dts) {
        int i = 0;
        com.pullenti.ner.Token t1 = null;
        String typ = null;
        com.pullenti.ner.ReferentToken geo = null;
        com.pullenti.ner.ReferentToken _org = null;
        com.pullenti.ner.decree.internal.DecreeToken _date = null;
        for (i = 0; i < dts.size(); i++) {
            if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP && com.pullenti.ner.decree.internal.DecreeToken.getKind(dts.get(i).value) == DecreeKind.PUBLISHER) {
                typ = dts.get(i).value;
                if (dts.get(i).ref != null && (dts.get(i).ref.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                    geo = dts.get(i).ref;
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TERR) {
                geo = dts.get(i).ref;
                t1 = dts.get(i).getEndToken();
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                _date = dts.get(i);
                t1 = dts.get(i).getEndToken();
            }
            else if (dts.get(i).typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.ORG) {
                _org = dts.get(i).ref;
                t1 = dts.get(i).getEndToken();
            }
            else 
                break;
        }
        if (typ == null) 
            return null;
        com.pullenti.ner.Token t = dts.get(i - 1).getEndToken().getNext();
        if (t == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        com.pullenti.ner.decree.internal.DecreeToken num = null;
        com.pullenti.ner.Token t0 = dts.get(0).getBeginToken();
        if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, false, null, false)) {
            t = t.getNext();
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0.getPrevious(), false, false)) 
                t0 = t0.getPrevious();
        }
        DecreeReferent pub0 = null;
        DecreePartReferent pubPart0 = null;
        for (; t != null; t = t.getNext()) {
            if (t.isCharOf(",;.") || t.isAnd()) 
                continue;
            com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, dts.get(0), false);
            if (dt != null) {
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                    num = dt;
                    pub0 = null;
                    pubPart0 = null;
                    if (t0 == null) 
                        t0 = t;
                    t1 = (t = dt.getEndToken());
                    continue;
                }
                if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE) {
                    if (t0 == null) 
                        t0 = t;
                    _date = dt;
                    pub0 = null;
                    pubPart0 = null;
                    t1 = (t = dt.getEndToken());
                    continue;
                }
                if (dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.MISC && t.getLengthChar() > 2) 
                    break;
            }
            com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false);
            if (pt == null && t.isChar('(')) {
                pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t.getNext(), null, false, false);
                if (pt != null) {
                    if (pt.getEndToken().getNext() != null && pt.getEndToken().getNext().isChar(')')) 
                        pt.setEndToken(pt.getEndToken().getNext());
                    else 
                        pt = null;
                }
            }
            if (pt != null) {
                if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PAGE) {
                    t = pt.getEndToken();
                    continue;
                }
                if (pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PART && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PAGE) 
                    break;
                if (num == null) 
                    break;
                if (pubPart0 != null) {
                    if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART && pubPart0.getPart() == null) {
                    }
                    else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE && pubPart0.getClause() == null) {
                    }
                    else 
                        pubPart0 = null;
                }
                DecreeReferent pub = pub0;
                DecreePartReferent pubPart = pubPart0;
                if (pub == null) {
                    pub = new DecreeReferent();
                    pub.setTyp(typ);
                    if (geo != null) 
                        pub.addSlot(DecreeReferent.ATTR_GEO, geo.referent, false, 0);
                    if (_org != null) 
                        pub.addSlot(DecreeReferent.ATTR_SOURCE, _org.referent, false, 0).setTag(_org.getSourceText());
                    if (_date != null) 
                        pub.addDate(_date);
                    pub.addNumber(num);
                    res.add(new com.pullenti.ner.ReferentToken(pub, (t0 != null ? t0 : t), pt.getBeginToken().getPrevious(), null));
                }
                if (pubPart == null) {
                    pubPart = DecreePartReferent._new1177(pub);
                    res.add(new com.pullenti.ner.ReferentToken(pubPart, pt.getBeginToken(), pt.getEndToken(), null));
                }
                pub0 = pub;
                if (pt.values.size() == 1) {
                    if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE) 
                        pubPart.addSlot(DecreePartReferent.ATTR_CLAUSE, pt.values.get(0).value, false, 0).setTag(pt.values.get(0).getSourceValue());
                    else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART) 
                        pubPart.addSlot(DecreePartReferent.ATTR_PART, pt.values.get(0).value, false, 0).setTag(pt.values.get(0).getSourceValue());
                }
                else if (pt.values.size() > 1) {
                    for (int ii = 0; ii < pt.values.size(); ii++) {
                        if (ii > 0) {
                            pubPart = DecreePartReferent._new1177(pub);
                            res.add(new com.pullenti.ner.ReferentToken(pubPart, pt.values.get(ii).getBeginToken(), pt.values.get(ii).getEndToken(), null));
                        }
                        else 
                            res.get(res.size() - 1).setEndToken(pt.values.get(ii).getEndToken());
                        if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE) 
                            pubPart.addSlot(DecreePartReferent.ATTR_CLAUSE, pt.values.get(ii).value, false, 0).setTag(pt.values.get(ii).getSourceValue());
                        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART) 
                            pubPart.addSlot(DecreePartReferent.ATTR_PART, pt.values.get(ii).value, false, 0).setTag(pt.values.get(ii).getSourceValue());
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(pubPart.getClause(), "6878")) {
                }
                pubPart0 = pubPart;
                res.get(res.size() - 1).setEndToken(pt.getEndToken());
                t0 = null;
                t = pt.getEndToken();
                continue;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("DATE", t, null);
                if (rt != null) {
                    _date = com.pullenti.ner.decree.internal.DecreeToken._new906(rt.getBeginToken(), rt.getEndToken(), com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE);
                    _date.ref = rt;
                    pub0 = null;
                    pubPart0 = null;
                    if (t0 == null) 
                        t0 = t;
                    t1 = (t = rt.getEndToken());
                    continue;
                }
                if (t.getNext() != null && t.getNext().isChar(';')) {
                    if (pubPart0 != null && pubPart0.getClause() != null && pub0 != null) {
                        DecreePartReferent pubPart = new DecreePartReferent();
                        for (com.pullenti.ner.Slot s : pubPart0.getSlots()) {
                            pubPart.addSlot(s.getTypeName(), s.getValue(), false, 0);
                        }
                        pubPart0 = pubPart;
                        pubPart0.setClause(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString());
                        res.add(new com.pullenti.ner.ReferentToken(pubPart0, t, t, null));
                        continue;
                    }
                }
            }
            if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && (t.getLengthChar() < 3)) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                t = t.getNext();
                continue;
            }
            if ((t.isChar('(') && t.getNext() != null && t.getNext().getNext() != null) && t.getNext().getNext().isChar(')')) {
                t = t.getNext().getNext();
                continue;
            }
            break;
        }
        if ((res.size() == 0 && _date != null && ((num != null || typ.endsWith("ГАЗЕТА")))) && t1 != null) {
            DecreeReferent pub = new DecreeReferent();
            pub.setTyp(typ);
            if (geo != null) 
                pub.addSlot(DecreeReferent.ATTR_GEO, geo.referent, false, 0);
            if (_org != null) 
                pub.addSlot(DecreeReferent.ATTR_SOURCE, _org.referent, false, 0).setTag(_org.getSourceText());
            if (_date != null) 
                pub.addDate(_date);
            pub.addNumber(num);
            res.add(new com.pullenti.ner.ReferentToken(pub, t0, t1, null));
        }
        return res;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        com.pullenti.ner.ReferentToken rt = tryAttachApproved(begin, null);
        if (rt != null) 
            return rt;
        java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> dpli = com.pullenti.ner.decree.internal.DecreeToken.tryAttachList(begin, null, 10, true);
        if (dpli != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> lii = tryAttach(dpli, null, null);
            if (lii != null && lii.size() > 0) 
                return lii.get(0);
        }
        com.pullenti.ner.decree.internal.DecreeToken dp = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(begin, null, false);
        if (dp != null && dp.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) 
            return new com.pullenti.ner.ReferentToken(null, dp.getBeginToken(), dp.getEndToken(), null);
        return null;
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.decree.internal.MetaDecree.initialize();
        com.pullenti.ner.decree.internal.MetaDecreePart.initialize();
        com.pullenti.ner.decree.internal.MetaDecreeChange.initialize();
        com.pullenti.ner.decree.internal.MetaDecreeChangeValue.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
            com.pullenti.ner.decree.internal.DecreeChangeToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.decree.internal.DecreeToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new DecreeAnalyzer());
    }

    public static java.util.ArrayList<com.pullenti.ner.MetaToken> tryAttachParts(java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> parts, com.pullenti.ner.decree.internal.DecreeToken baseTyp, com.pullenti.ner.Referent _defOwner) {
        if (parts == null || parts.size() == 0) 
            return null;
        int i;
        int j;
        com.pullenti.ner.Token tt = parts.get(parts.size() - 1).getEndToken().getNext();
        if (_defOwner != null && tt != null) {
            if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null && br.getEndToken().getNext() != null) 
                    tt = br.getEndToken().getNext();
            }
            if (tt.getReferent() instanceof DecreeReferent) 
                _defOwner = null;
            else if (tt.isValue("К", null) && tt.getNext() != null && (tt.getNext().getReferent() instanceof DecreeReferent)) 
                _defOwner = null;
        }
        if ((parts.size() == 1 && parts.get(0).isNewlineBefore() && parts.get(0).getBeginToken().chars.isLetter()) && !parts.get(0).getBeginToken().chars.isAllLower()) {
            com.pullenti.ner.Token t1 = parts.get(0).getEndToken().getNext();
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) 
                t1 = br.getEndToken().getNext();
            if (t1 != null && (t1.getReferent() instanceof DecreeReferent) && !parts.get(0).isNewlineAfter()) {
            }
            else {
                com.pullenti.ner.instrument.internal.InstrToken1 li = com.pullenti.ner.instrument.internal.InstrToken1.parse(parts.get(0).getBeginToken(), true, null, 0, null, false, 0, false, false);
                if (li != null && li.hasVerb) {
                    if ((parts.size() == 1 && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART && (parts.get(0).toString().indexOf("резолют") >= 0)) && parts.get(0).isNewlineBefore()) 
                        return null;
                }
                else 
                    return null;
            }
        }
        ThisDecree thisDec = null;
        boolean isProgram = false;
        boolean isAddAgree = false;
        if (parts.get(parts.size() - 1).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPROGRAM && parts.get(parts.size() - 1).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.ADDAGREE) {
            thisDec = ThisDecree.tryAttach(parts.get(parts.size() - 1), baseTyp);
            if (thisDec != null) {
                if ((_defOwner instanceof DecreeReferent) && ((com.pullenti.unisharp.Utils.stringsEq(((DecreeReferent)com.pullenti.unisharp.Utils.cast(_defOwner, DecreeReferent.class)).getTyp0(), thisDec.typ) || ((((DecreeReferent)com.pullenti.unisharp.Utils.cast(_defOwner, DecreeReferent.class)).getKind() == DecreeKind.KODEX && com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, "КОДЕКС"))) || parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.APPENDIX))) {
                }
                else 
                    _defOwner = null;
            }
            if (thisDec == null && _defOwner == null) 
                thisDec = ThisDecree.tryAttachBack(parts.get(0).getBeginToken(), baseTyp);
            if (thisDec == null) {
                for (com.pullenti.ner.decree.internal.PartToken p : parts) {
                    if (p.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART) {
                        boolean hasClause = false;
                        for (com.pullenti.ner.decree.internal.PartToken pp : parts) {
                            if (pp != p) {
                                if (com.pullenti.ner.decree.internal.PartToken._getRank(pp.typ) >= com.pullenti.ner.decree.internal.PartToken._getRank(com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE)) 
                                    hasClause = true;
                            }
                        }
                        if (_defOwner instanceof DecreePartReferent) {
                            if (((DecreePartReferent)com.pullenti.unisharp.Utils.cast(_defOwner, DecreePartReferent.class)).getClause() != null) 
                                hasClause = true;
                        }
                        if (!hasClause) 
                            p.typ = com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART;
                        else if ((((p == parts.get(parts.size() - 1) && p.getEndToken().getNext() != null && p.values.size() == 1) && (p.getEndToken().getNext().getReferent() instanceof DecreeReferent) && (p.getBeginToken() instanceof com.pullenti.ner.TextToken)) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(p.getBeginToken(), com.pullenti.ner.TextToken.class)).term, "ЧАСТИ") && (p.getEndToken() instanceof com.pullenti.ner.NumberToken)) && p.getBeginToken().getNext() == p.getEndToken()) 
                            p.typ = com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART;
                    }
                }
            }
        }
        else if (parts.get(parts.size() - 1).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.ADDAGREE) 
            isAddAgree = true;
        else {
            if (parts.size() > 1) 
                for(int jjj = 0 + parts.size() - 1 - 1, mmm = 0; jjj >= mmm; jjj--) parts.remove(jjj);
            isProgram = true;
        }
        DecreeReferent defOwner = (DecreeReferent)com.pullenti.unisharp.Utils.cast(_defOwner, DecreeReferent.class);
        if (_defOwner instanceof DecreePartReferent) 
            defOwner = ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(_defOwner, DecreePartReferent.class)).getOwner();
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        boolean hasPrefix = false;
        if (parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) {
            parts.remove(0);
            hasPrefix = true;
            if (parts.size() == 0) 
                return null;
        }
        if ((parts.size() == 1 && thisDec == null && parts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPROGRAM) && parts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.ADDAGREE) {
            if (parts.get(0).isDoubt) 
                return null;
            if (parts.get(0).isNewlineBefore() && parts.get(0).values.size() <= 1) {
                com.pullenti.ner.Token tt1 = parts.get(0).getEndToken();
                if (tt1.getNext() == null) 
                    return null;
                tt1 = tt1.getNext();
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && br.getEndToken().getNext() != null) 
                        tt1 = br.getEndToken().getNext();
                }
                if (tt1.isChar(',')) {
                }
                else if (tt1.getReferent() instanceof DecreeReferent) {
                }
                else if (tt1.isValue("К", null) && tt1.getNext() != null && (tt1.getNext().getReferent() instanceof DecreeReferent)) {
                }
                else if (_checkOtherTyp(tt1, true) != null) {
                }
                else if (_defOwner == null) 
                    return null;
                else if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt1)) 
                    return null;
                else if (tt1.isChar('.')) 
                    return null;
            }
        }
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> asc = new java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken>();
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> desc = new java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken>();
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken.ItemType> typs = new java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken.ItemType>();
        int ascCount = 0;
        int descCount = 0;
        int terminators = 0;
        for (i = 0; i < (parts.size() - 1); i++) {
            if (!parts.get(i).hasTerminator) {
                if (parts.get(i).canBeNextNarrow(parts.get(i + 1))) 
                    ascCount++;
                if (parts.get(i + 1).canBeNextNarrow(parts.get(i))) 
                    descCount++;
            }
            else if ((ascCount > 0 && parts.get(i).values.size() == 1 && parts.get(i + 1).values.size() == 1) && parts.get(i).canBeNextNarrow(parts.get(i + 1))) 
                ascCount++;
            else if ((descCount > 0 && parts.get(i).values.size() == 1 && parts.get(i + 1).values.size() == 1) && parts.get(i + 1).canBeNextNarrow(parts.get(i))) 
                descCount++;
            else 
                terminators++;
        }
        if (terminators == 0 && ((((descCount > 0 && ascCount == 0)) || ((descCount == 0 && ascCount > 0))))) {
            for (i = 0; i < (parts.size() - 1); i++) {
                parts.get(i).hasTerminator = false;
            }
        }
        for (i = 0; i < parts.size(); i++) {
            if (parts.get(i).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) 
                continue;
            asc.clear();
            asc.add(parts.get(i));
            typs.clear();
            typs.add(parts.get(i).typ);
            for (j = i + 1; j < parts.size(); j++) {
                if (parts.get(j).values.size() == 0 && parts.get(j).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREAMBLE) 
                    break;
                else if (!typs.contains(parts.get(j).typ) && parts.get(j - 1).canBeNextNarrow(parts.get(j))) {
                    if (parts.get(j - 1).delimAfter && terminators == 0) {
                        if (descCount > ascCount) 
                            break;
                        if (((j + 1) < parts.size()) && !parts.get(j).delimAfter && !parts.get(j).hasTerminator) 
                            break;
                        if (parts.get(j - 1).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.ITEM && parts.get(j).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBITEM) {
                            if (parts.get(j).values.size() > 0 && (parts.get(j).values.get(0).toString().indexOf(".") >= 0)) 
                                break;
                        }
                    }
                    asc.add(parts.get(j));
                    typs.add(parts.get(j).typ);
                    if (parts.get(j).hasTerminator) 
                        break;
                }
                else 
                    break;
            }
            desc.clear();
            desc.add(parts.get(i));
            typs.clear();
            typs.add(parts.get(i).typ);
            for (j = i + 1; j < parts.size(); j++) {
                if (parts.get(j).values.size() == 0 && parts.get(j).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREAMBLE) 
                    break;
                else if (((!typs.contains(parts.get(j).typ) || parts.get(j).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBITEM)) && parts.get(j).canBeNextNarrow(parts.get(j - 1))) {
                    if (parts.get(j - 1).delimAfter && terminators == 0) {
                        if (descCount <= ascCount) 
                            break;
                    }
                    desc.add(parts.get(j));
                    typs.add(parts.get(j).typ);
                    if (parts.get(j).hasTerminator) 
                        break;
                }
                else if (((!typs.contains(parts.get(j).typ) && parts.get(j - 1).canBeNextNarrow(parts.get(j)) && (j + 1) == (parts.size() - 1)) && parts.get(j + 1).canBeNextNarrow(parts.get(j)) && parts.get(j + 1).canBeNextNarrow(parts.get(j - 1))) && !parts.get(j).hasTerminator) {
                    desc.add(desc.size() - 1, parts.get(j));
                    typs.add(parts.get(j).typ);
                }
                else 
                    break;
            }
            java.util.Collections.reverse(desc);
            java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> li = (asc.size() < desc.size() ? desc : asc);
            for (j = 0; j < li.size(); j++) {
                li.get(j).ind = 0;
            }
            while (true) {
                DecreePartReferent dr = new DecreePartReferent();
                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(dr, parts.get(i).getBeginToken(), parts.get((i + li.size()) - 1).getEndToken(), null);
                if (parts.get(i).name != null) 
                    dr.addSlot(DecreePartReferent.ATTR_NAME, parts.get(i).name, false, 0);
                res.add(rt);
                java.util.ArrayList<com.pullenti.ner.Slot> slList = new java.util.ArrayList<com.pullenti.ner.Slot>();
                for (com.pullenti.ner.decree.internal.PartToken p : li) {
                    String nam = com.pullenti.ner.decree.internal.PartToken._getAttrNameByTyp(p.typ);
                    if (nam != null) {
                        com.pullenti.ner.Slot sl = com.pullenti.ner.Slot._new1180(nam, p, 1);
                        slList.add(sl);
                        if (p.ind < p.values.size()) {
                            sl.setValue(p.values.get(p.ind));
                            if (com.pullenti.unisharp.Utils.isNullOrEmpty(p.values.get(p.ind).value)) 
                                sl.setValue("0");
                        }
                        else 
                            sl.setValue("0");
                    }
                    if (p.ind > 0) 
                        rt.setBeginToken(p.values.get(p.ind).getBeginToken());
                    if ((p.ind + 1) < p.values.size()) 
                        rt.setEndToken(p.values.get(p.ind).getEndToken());
                }
                for (com.pullenti.ner.decree.internal.PartToken p : parts) {
                    for (com.pullenti.ner.Slot s : slList) {
                        if (s.getTag() == p) {
                            dr.addSlot(s.getTypeName(), s.getValue(), false, 0);
                            break;
                        }
                    }
                }
                for (j = li.size() - 1; j >= 0; j--) {
                    if ((++li.get(j).ind) >= li.get(j).values.size()) 
                        li.get(j).ind = 0;
                    else 
                        break;
                }
                if (j < 0) 
                    break;
            }
            i += (li.size() - 1);
        }
        if (res.size() == 0) 
            return null;
        for (j = res.size() - 1; j > 0; j--) {
            DecreePartReferent d0 = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(j).referent, DecreePartReferent.class);
            DecreePartReferent d = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(j - 1).referent, DecreePartReferent.class);
            if (d0.getChapter() != null && d.getChapter() == null) 
                d.setChapter(d0.getChapter());
            if (d0.getSection() != null && d.getSection() == null) 
                d.setSection(d0.getSection());
            if (d0.getSubSection() != null && d.getSubSection() == null) 
                d.setSubSection(d0.getSubSection());
            if (d0.getClause() != null && d.getClause() == null) 
                d.setClause(d0.getClause());
            if (d0.getItem() != null && d.getItem() == null && ((d.getSubItem() != null || d.getIndention() != null))) {
                if (d0.getClause() != null && com.pullenti.unisharp.Utils.stringsNe(d0.getClause(), d.getClause())) {
                }
                else 
                    d.setItem(d0.getItem());
            }
            if ((d0.getSubItem() != null && d.getSubItem() == null && d.getIndention() != null) && com.pullenti.unisharp.Utils.stringsEq(d.getItem(), d0.getItem())) 
                d.setSubItem(d0.getSubItem());
        }
        tt = parts.get(i - 1).getEndToken();
        DecreeReferent owner = defOwner;
        com.pullenti.ner.Token te = tt.getNext();
        if ((te != null && owner == null && te.isChar('(')) && parts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPROGRAM && parts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.ADDAGREE) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(te, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                if (te.getNext().getMorph()._getClass().isAdverb()) {
                }
                else if (te.getNext().getReferent() instanceof DecreeReferent) {
                    if (owner == null && te.getNext().getNext() == br.getEndToken()) {
                        owner = (DecreeReferent)com.pullenti.unisharp.Utils.cast(te.getNext().getReferent(), DecreeReferent.class);
                        te = br.getEndToken();
                    }
                }
                else {
                    String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    if (s != null) {
                        com.pullenti.ner.ReferentToken rt = res.get(res.size() - 1);
                        ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DecreePartReferent.class)).addName(s);
                        rt.setEndToken(br.getEndToken());
                        te = rt.getEndToken().getNext();
                    }
                }
            }
        }
        if (te != null && te.isCharOf(",;")) 
            te = te.getNext();
        if (owner == null && (te instanceof com.pullenti.ner.ReferentToken)) {
            if ((((owner = (DecreeReferent)com.pullenti.unisharp.Utils.cast(te.getReferent(), DecreeReferent.class)))) != null) 
                res.get(res.size() - 1).setEndToken(te);
        }
        if (owner == null) {
            for (j = 0; j < i; j++) {
                if ((((owner = parts.get(j).decree))) != null) 
                    break;
            }
        }
        if (te != null && te.isValue("К", null) && te.getNext() != null) {
            if (te.getNext().getReferent() instanceof DecreeReferent) {
                te = te.getNext();
                res.get(res.size() - 1).setEndToken(te);
                owner = (DecreeReferent)com.pullenti.unisharp.Utils.cast(te.getReferent(), DecreeReferent.class);
            }
            else if (owner != null && thisDec != null && thisDec.getEndChar() > te.getEndChar()) 
                res.get(res.size() - 1).setEndToken(thisDec.getEndToken());
        }
        if (owner == null && thisDec != null) {
            com.pullenti.ner.Token tt0 = res.get(0).getBeginToken();
            if (tt0.getPrevious() != null && tt0.getPrevious().isChar('(')) 
                tt0 = tt0.getPrevious();
            if (tt0.getPrevious() != null) {
                if ((((owner = (DecreeReferent)com.pullenti.unisharp.Utils.cast(tt0.getPrevious().getReferent(), DecreeReferent.class)))) != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, owner.getTyp0())) 
                        thisDec = null;
                    else 
                        owner = null;
                }
            }
        }
        if (owner == null && thisDec != null && thisDec.real != null) 
            owner = thisDec.real;
        if (owner != null && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPROGRAM && owner.getKind() != DecreeKind.PROGRAM) 
            owner = null;
        if (owner != null && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.ADDAGREE && owner.getKind() != DecreeKind.CONTRACT) 
            owner = null;
        DecreePartReferent ownerPaer = null;
        String locTyp = null;
        if ((thisDec == null || !thisDec.hasThisRef)) {
            com.pullenti.ner.TextToken anaforRef = null;
            for (com.pullenti.ner.decree.internal.PartToken p : parts) {
                if ((((anaforRef = p.anaforRef))) != null) 
                    break;
            }
            boolean isChangeWordAfter = false;
            com.pullenti.ner.Token tt2 = res.get(res.size() - 1).getEndToken().getNext();
            if (tt2 != null) {
                if (((tt2.isChar(':') || tt2.isValue("ДОПОЛНИТЬ", null) || tt2.isValue("СЛОВО", null)) || tt2.isValue("ИСКЛЮЧИТЬ", null) || tt2.isValue("ИЗЛОЖИТЬ", null)) || tt2.isValue("СЧИТАТЬ", null) || tt2.isValue("ПРИЗНАТЬ", null)) 
                    isChangeWordAfter = true;
            }
            tt2 = parts.get(0).getBeginToken().getPrevious();
            if (tt2 != null) {
                if (((tt2.isValue("ДОПОЛНИТЬ", null) || tt2.isValue("ИСКЛЮЧИТЬ", null) || tt2.isValue("ИЗЛОЖИТЬ", null)) || tt2.isValue("СЧИТАТЬ", null) || tt2.isValue("УСТАНОВЛЕННЫЙ", null)) || tt2.isValue("ОПРЕДЕЛЕННЫЙ", null)) 
                    isChangeWordAfter = true;
            }
            int cou = 0;
            boolean ugolDelo = false;
            int brackLevel = 0;
            com.pullenti.ner.Token bt = null;
            int coefBefore = 0;
            boolean isOverBrr = false;
            if (parts.get(0).getBeginToken().getPrevious() != null && parts.get(0).getBeginToken().getPrevious().isChar('(')) {
                if (parts.get(parts.size() - 1).getEndToken().getNext() != null && parts.get(parts.size() - 1).getEndToken().getNext().isChar(')')) {
                    if (parts.size() == 1 && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.APPENDIX) {
                    }
                    else {
                        isOverBrr = true;
                        if (owner != null && _getDecree(parts.get(0).getBeginToken().getPrevious().getPrevious()) != null) 
                            owner = null;
                    }
                }
            }
            for (tt = parts.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious(),coefBefore++) {
                if (tt.isNewlineAfter()) {
                    coefBefore += 2;
                    if (((anaforRef == null && !isOverBrr && !ugolDelo) && !isChangeWordAfter && !isProgram) && !isAddAgree) {
                        if (thisDec == null) {
                            if (!tt.isTableControlChar()) 
                                break;
                        }
                    }
                }
                if (thisDec != null && thisDec.hasThisRef) 
                    break;
                if (tt.isTableControlChar()) 
                    break;
                if (tt.getMorph()._getClass().isPreposition()) {
                    coefBefore--;
                    continue;
                }
                if (tt instanceof com.pullenti.ner.TextToken) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, false, null, false)) {
                        brackLevel++;
                        continue;
                    }
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                        if (tt.isChar('(') && tt == parts.get(0).getBeginToken().getPrevious()) {
                        }
                        else {
                            brackLevel--;
                            coefBefore--;
                        }
                        continue;
                    }
                }
                if (tt.isNewlineBefore()) 
                    brackLevel = 0;
                if ((++cou) > 100) {
                    if (((ugolDelo || isProgram || isAddAgree) || anaforRef != null || thisDec != null) || isOverBrr) {
                        if (cou > 1000) 
                            break;
                    }
                    else if (isChangeWordAfter) {
                        if (cou > 250) 
                            break;
                    }
                    else 
                        break;
                }
                if (cou < 4) {
                    if (tt.isValue("УГОЛОВНЫЙ", "КРИМІНАЛЬНИЙ") && tt.getNext() != null && tt.getNext().isValue("ДЕЛО", "СПРАВА")) 
                        ugolDelo = true;
                }
                if (tt.isCharOf(".")) {
                    coefBefore += 50;
                    if (tt.isNewlineAfter()) 
                        coefBefore += 100;
                    continue;
                }
                if (brackLevel > 0) 
                    continue;
                DecreeReferent dr = _getDecree(tt);
                if (dr != null && dr.getKind() != DecreeKind.PUBLISHER) {
                    if (ugolDelo && ((com.pullenti.unisharp.Utils.stringsEq(dr.getName(), "УГОЛОВНЫЙ КОДЕКС") || com.pullenti.unisharp.Utils.stringsEq(dr.getName(), "КРИМІНАЛЬНИЙ КОДЕКС")))) 
                        coefBefore = 0;
                    if (dr.getKind() == DecreeKind.PROGRAM) {
                        if (isProgram) {
                            bt = tt;
                            break;
                        }
                        else 
                            continue;
                    }
                    if (dr.getKind() == DecreeKind.CONTRACT) {
                        if (isAddAgree) {
                            bt = tt;
                            break;
                        }
                        else if (thisDec != null && ((com.pullenti.unisharp.Utils.stringsEq(dr.getTyp(), thisDec.typ) || com.pullenti.unisharp.Utils.stringsEq(dr.getTyp0(), thisDec.typ)))) {
                            bt = tt;
                            break;
                        }
                        else 
                            continue;
                    }
                    if (thisDec != null) {
                        DecreePartReferent dpr = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), DecreePartReferent.class);
                        if (com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, dr.getTyp()) || com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, dr.getTyp0())) 
                            thisDec.real = dr;
                        else if (dr.getName() != null && thisDec.typ != null && dr.getName().startsWith(thisDec.typ)) 
                            thisDec.real = dr;
                        else if ((thisDec.hasOtherRef && dpr != null && dpr.getClause() != null) && ((com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, "СТАТЬЯ") || com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, "СТАТТЯ")))) {
                            for (com.pullenti.ner.ReferentToken r : res) {
                                DecreePartReferent dpr0 = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(r.referent, DecreePartReferent.class);
                                if (dpr0.getClause() == null) {
                                    dpr0.setClause(dpr.getClause());
                                    owner = dpr0.setOwner(dpr.getOwner());
                                }
                            }
                        }
                        else 
                            continue;
                    }
                    else if (isChangeWordAfter) {
                        if (owner == null) 
                            coefBefore = 0;
                        else if (owner == _getDecree(tt)) 
                            coefBefore = 0;
                    }
                    bt = tt;
                    break;
                }
                if (dr != null) 
                    continue;
                DecreePartReferent dpr2 = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), DecreePartReferent.class);
                if (dpr2 != null) {
                    if (thisDec != null) {
                        if (com.pullenti.unisharp.Utils.stringsNe(thisDec.typ, dpr2.getLocalTyp())) 
                            continue;
                    }
                    bt = tt;
                    break;
                }
                com.pullenti.ner.decree.internal.DecreeToken dit = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt, null, false);
                if (dit != null && dit.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                    if (thisDec != null) 
                        continue;
                    if (dit.chars.isCapitalUpper() || anaforRef != null) {
                        bt = tt;
                        break;
                    }
                }
            }
            cou = 0;
            com.pullenti.ner.Token at = null;
            int coefAfter = 0;
            String alocTyp = null;
            com.pullenti.ner.Token tt0 = parts.get(parts.size() - 1).getEndToken().getNext();
            boolean hasNewline = false;
            for (com.pullenti.ner.Token ttt = parts.get(parts.size() - 1).getBeginToken(); ttt.getEndChar() < parts.get(parts.size() - 1).getEndChar(); ttt = ttt.getNext()) {
                if (ttt.isNewlineAfter()) 
                    hasNewline = true;
            }
            for (tt = tt0; tt != null; tt = tt.getNext(),coefAfter++) {
                if (owner != null && coefAfter > 0) 
                    break;
                if (tt.isNewlineBefore()) 
                    break;
                if (tt.isTableControlChar()) 
                    break;
                if (tt.isValue("СМ", null)) 
                    break;
                if (anaforRef != null) 
                    break;
                if (thisDec != null) {
                    if (tt != tt0) 
                        break;
                    if (thisDec.real != null) 
                        break;
                }
                if (com.pullenti.ner.instrument.internal.InstrToken._checkEntered(tt) != null) 
                    break;
                if (tt.getMorph()._getClass().isPreposition() || tt.isCommaAnd()) {
                    coefAfter--;
                    continue;
                }
                if (tt.getMorph()._getClass().equals(com.pullenti.morph.MorphClass.VERB)) 
                    break;
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, false, null, false)) 
                    break;
                java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> pts = com.pullenti.ner.decree.internal.PartToken.tryAttachList(tt, false, 40);
                if (pts != null) {
                    tt = pts.get(pts.size() - 1).getEndToken();
                    coefAfter--;
                    com.pullenti.ner.Token ttnn = tt.getNext();
                    if (ttnn != null && ttnn.isChar('.')) 
                        ttnn = ttnn.getNext();
                    com.pullenti.ner.decree.internal.DecreeToken dit = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttnn, null, false);
                    if (dit != null && dit.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                        locTyp = dit.value;
                        break;
                    }
                    continue;
                }
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        coefAfter--;
                        tt = br.getEndToken();
                        continue;
                    }
                }
                if ((++cou) > 100) 
                    break;
                if (cou > 1 && hasNewline) 
                    break;
                if (tt.isCharOf(".")) {
                    coefAfter += 50;
                    if (tt.isNewlineAfter()) 
                        coefAfter += 100;
                    continue;
                }
                DecreeReferent dr = (DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), DecreeReferent.class);
                if (dr != null && dr.getKind() != DecreeKind.PUBLISHER) {
                    if (dr.getKind() == DecreeKind.PROGRAM) {
                        if (isProgram) {
                            at = tt;
                            break;
                        }
                        else 
                            continue;
                    }
                    if (dr.getKind() == DecreeKind.CONTRACT) {
                        if (isAddAgree) {
                            at = tt;
                            break;
                        }
                        else 
                            continue;
                    }
                    at = tt;
                    break;
                }
                if (isProgram || isAddAgree) 
                    break;
                if (dr != null) 
                    continue;
                com.pullenti.ner.Token tte2 = _checkOtherTyp(tt, tt == tt0);
                if (tte2 != null) {
                    at = tte2;
                    if (tt == tt0 && thisDec != null && thisDec.real == null) {
                        if (com.pullenti.unisharp.Utils.stringsEq(thisDec.typ, (String)com.pullenti.unisharp.Utils.cast(at.tag, String.class))) 
                            at = null;
                        else 
                            thisDec = null;
                    }
                    break;
                }
            }
            if (bt != null && at != null) {
                if (coefBefore < coefAfter) 
                    at = null;
                else if ((bt instanceof com.pullenti.ner.ReferentToken) && (at instanceof com.pullenti.ner.TextToken)) 
                    at = null;
                else 
                    bt = null;
            }
            if (owner == null) {
                if (at != null) {
                    owner = _getDecree(at);
                    if (at instanceof com.pullenti.ner.TextToken) {
                        if (at.tag instanceof String) 
                            locTyp = (String)com.pullenti.unisharp.Utils.cast(at.tag, String.class);
                        else 
                            locTyp = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(at, com.pullenti.ner.TextToken.class)).lemma;
                    }
                }
                else if (bt != null) {
                    owner = _getDecree(bt);
                    ownerPaer = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(bt.getReferent(), DecreePartReferent.class);
                    if (ownerPaer != null && locTyp == null) 
                        locTyp = ownerPaer.getLocalTyp();
                }
            }
            else if (coefAfter == 0 && at != null) 
                owner = _getDecree(at);
            else if (coefBefore == 0 && bt != null) {
                owner = _getDecree(bt);
                ownerPaer = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(bt.getReferent(), DecreePartReferent.class);
                if (ownerPaer != null && locTyp == null) 
                    locTyp = ownerPaer.getLocalTyp();
            }
            if (((bt != null && parts.size() == 1 && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.DOCPART) && (bt.getReferent() instanceof DecreePartReferent) && ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(bt.getReferent(), DecreePartReferent.class)).getClause() != null) && res.size() == 1 && owner == ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(bt.getReferent(), DecreePartReferent.class)).getOwner()) {
                for (com.pullenti.ner.Slot s : res.get(0).referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), DecreePartReferent.ATTR_DOCPART)) 
                        s.setTypeName(DecreePartReferent.ATTR_PART);
                }
                ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(0).referent, DecreePartReferent.class)).addHighLevelInfo((DecreePartReferent)com.pullenti.unisharp.Utils.cast(bt.getReferent(), DecreePartReferent.class));
            }
        }
        if (owner == null) {
            if (thisDec == null && locTyp == null) {
                if ((parts.size() == 1 && parts.get(0).values.size() == 1 && parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.APPENDIX) && parts.get(0).getBeginToken().chars.isCapitalUpper()) {
                }
                else if ((parts.get(0).getBeginToken().getPrevious() != null && parts.get(0).getBeginToken().getPrevious().isChar('(') && parts.get(parts.size() - 1).getEndToken().getNext() != null) && parts.get(parts.size() - 1).getEndToken().getNext().isChar(')')) {
                    if (parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PAGE) 
                        return null;
                }
                else 
                    return null;
            }
            for (com.pullenti.ner.ReferentToken r : res) {
                DecreePartReferent dr = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(r.referent, DecreePartReferent.class);
                if (thisDec != null) {
                    dr.setLocalTyp(thisDec.typ);
                    if (thisDec.getBeginChar() > r.getEndChar() && r == res.get(res.size() - 1)) 
                        r.setEndToken(thisDec.getEndToken());
                }
                else if (locTyp != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(locTyp, "СТАТЬЯ") && dr.getClause() != null) {
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(locTyp, "ГЛАВА") && dr.getChapter() != null) {
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(locTyp, "ПАРАГРАФ") && dr.getParagraph() != null) {
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(locTyp, "ЧАСТЬ") && dr.getPart() != null) {
                    }
                    else {
                        dr.setLocalTyp(locTyp);
                        if (r == res.get(res.size() - 1) && !r.isNewlineAfter()) {
                            com.pullenti.ner.Token ttt1 = r.getEndToken().getNext();
                            if (ttt1 != null && ttt1.isComma()) 
                                ttt1 = ttt1.getNext();
                            com.pullenti.ner.Token at = _checkOtherTyp(ttt1, true);
                            if (at != null && com.pullenti.unisharp.Utils.stringsEq((String)com.pullenti.unisharp.Utils.cast(at.tag, String.class), locTyp)) 
                                r.setEndToken(at);
                        }
                    }
                }
            }
        }
        else 
            for (com.pullenti.ner.ReferentToken r : res) {
                DecreePartReferent dr = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(r.referent, DecreePartReferent.class);
                dr.setOwner(owner);
                if (thisDec != null && thisDec.real == owner) {
                    if (thisDec.getBeginChar() > r.getEndChar() && r == res.get(res.size() - 1)) 
                        r.setEndToken(thisDec.getEndToken());
                }
            }
        if (res.size() > 0) {
            com.pullenti.ner.ReferentToken rt = res.get(res.size() - 1);
            tt = rt.getEndToken().getNext();
            if (owner != null && tt != null && tt.getReferent() == owner) {
                rt.setEndToken(tt);
                tt = tt.getNext();
            }
            if (tt != null && ((tt.isHiphen() || tt.isChar(':')))) 
                tt = tt.getNext();
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, (isProgram ? com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES : com.pullenti.ner.core.BracketParseAttr.NO), 100);
            if (br != null) {
                boolean ok = true;
                if (br.getOpenChar() == '(') {
                    if (parts.get(0).typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPROGRAM) 
                        ok = false;
                    else if (com.pullenti.ner.decree.internal.PartToken.tryAttach(tt.getNext(), null, false, false) != null) 
                        ok = false;
                    else 
                        for (com.pullenti.ner.Token ttt = tt.getNext(); ttt != null && (ttt.getEndChar() < br.getEndChar()); ttt = ttt.getNext()) {
                            if (ttt == tt.getNext() && tt.getNext().getMorph()._getClass().isAdverb()) 
                                ok = false;
                            if ((ttt.getReferent() instanceof DecreeReferent) || (ttt.getReferent() instanceof DecreePartReferent)) 
                                ok = false;
                            if (ttt.isValue("РЕДАКЦИЯ", null) && ttt == br.getEndToken().getPrevious()) 
                                ok = false;
                        }
                }
                if (ok) {
                    String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    if (s != null) {
                        ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DecreePartReferent.class)).addName(s);
                        rt.setEndToken(br.getEndToken());
                        if ((rt.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && rt.getEndToken().getNext().getReferent() == owner) 
                            rt.setEndToken(rt.getEndToken().getNext());
                    }
                }
            }
            else if ((isProgram && parts.get(0).values.size() > 0 && tt != null) && tt.isTableControlChar() && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext())) {
                for (com.pullenti.ner.Token tt1 = tt.getNext(); tt1 != null; tt1 = tt1.getNext()) {
                    if (tt1.isTableControlChar()) {
                        String s = com.pullenti.ner.core.MiscHelper.getTextValue(tt.getNext(), tt1.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                        if (s != null) {
                            ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(rt.referent, DecreePartReferent.class)).addName(s);
                            rt.setEndToken(tt1);
                        }
                        break;
                    }
                    else if (tt1.isNewlineBefore()) 
                        break;
                }
            }
            if (thisDec != null) {
                if (thisDec.getEndChar() > res.get(res.size() - 1).getEndChar()) 
                    res.get(res.size() - 1).setEndToken(thisDec.getEndToken());
            }
        }
        if (ownerPaer != null && thisDec == null) {
            for (int ii = 0; ii < res.size(); ii++) {
                ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(ii).referent, DecreePartReferent.class)).addHighLevelInfo((ii == 0 ? ownerPaer : (DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(ii - 1).referent, DecreePartReferent.class)));
            }
        }
        if (res.size() == 1 && ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(0).referent, DecreePartReferent.class)).getName() == null) {
            if ((res.get(0).getBeginToken().getPrevious() != null && res.get(0).getBeginToken().getPrevious().isChar('(') && res.get(0).getEndToken().getNext() != null) && res.get(0).getEndToken().getNext().isChar(')')) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(res.get(0).getBeginToken().getPrevious().getPrevious(), false, null, false)) {
                    com.pullenti.ner.Token beg = null;
                    for (tt = res.get(0).getBeginToken().getPrevious().getPrevious().getPrevious(); tt != null; tt = tt.getPrevious()) {
                        if (tt.isNewlineAfter()) 
                            break;
                        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null && ((br.getEndChar() + 10) < res.get(0).getBeginChar())) 
                                break;
                            if (tt.getNext().chars.isLetter() && !tt.getNext().chars.isAllLower()) 
                                beg = tt;
                        }
                    }
                    if (beg != null) {
                        ((DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(0).referent, DecreePartReferent.class)).addName(com.pullenti.ner.core.MiscHelper.getTextValue(beg, res.get(0).getBeginToken().getPrevious().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO));
                        res.get(0).setBeginToken(beg);
                        res.get(0).setEndToken(res.get(0).getEndToken().getNext());
                    }
                }
            }
        }
        if (isProgram) {
            for (i = res.size() - 1; i >= 0; i--) {
                DecreePartReferent pa = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, DecreePartReferent.class);
                if (pa.getSubprogram() == null) 
                    continue;
                if (pa.getOwner() == null || pa.getName() == null || pa.getOwner().getKind() != DecreeKind.PROGRAM) 
                    res.remove(i);
            }
        }
        if (isAddAgree) {
            for (i = res.size() - 1; i >= 0; i--) {
                DecreePartReferent pa = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, DecreePartReferent.class);
                if (pa.getAddagree() == null) 
                    continue;
                if (pa.getOwner() == null || pa.getOwner().getKind() != DecreeKind.CONTRACT) 
                    res.remove(i);
            }
        }
        java.util.ArrayList<com.pullenti.ner.MetaToken> res1 = new java.util.ArrayList<com.pullenti.ner.MetaToken>();
        for (i = 0; i < res.size(); i++) {
            java.util.ArrayList<DecreePartReferent> li = new java.util.ArrayList<DecreePartReferent>();
            for (j = i; j < res.size(); j++) {
                if (res.get(j).getBeginToken() != res.get(i).getBeginToken()) 
                    break;
                else 
                    li.add((DecreePartReferent)com.pullenti.unisharp.Utils.cast(res.get(j).referent, DecreePartReferent.class));
            }
            com.pullenti.ner.Token et;
            if (j < res.size()) 
                et = res.get(j).getBeginToken().getPrevious();
            else 
                et = res.get(res.size() - 1).getEndToken();
            while (et.getBeginChar() > res.get(i).getBeginChar()) {
                if (et.isChar(',') || et.getMorph()._getClass().isConjunction() || et.isHiphen()) 
                    et = et.getPrevious();
                else if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(et) != null) 
                    et = et.getPrevious();
                else 
                    break;
            }
            res1.add(com.pullenti.ner.MetaToken._new899(res.get(i).getBeginToken(), et, li));
            i = j - 1;
        }
        return res1;
    }

    public static class ThisDecree extends com.pullenti.ner.MetaToken {
    
        public ThisDecree(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
            super(b, e, null);
        }
    
        public String typ;
    
        public boolean hasThisRef;
    
        public boolean hasOtherRef;
    
        public com.pullenti.ner.decree.DecreeReferent real;
    
        @Override
        public String toString() {
            return (((typ != null ? typ : "?")) + " (" + (hasThisRef ? "This" : (hasOtherRef ? "Other" : "?")) + ")");
        }
    
        public static ThisDecree tryAttachBack(com.pullenti.ner.Token t, com.pullenti.ner.decree.internal.DecreeToken baseTyp) {
            if (t == null) 
                return null;
            com.pullenti.ner.Token ukaz = null;
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
                if (tt.isCharOf(",") || tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                    continue;
                if ((((((tt.isValue("ОПРЕДЕЛЕННЫЙ", "ПЕВНИЙ") || tt.isValue("ЗАДАННЫЙ", "ЗАДАНИЙ") || tt.isValue("ПРЕДУСМОТРЕННЫЙ", "ПЕРЕДБАЧЕНИЙ")) || tt.isValue("УКАЗАННЫЙ", "ЗАЗНАЧЕНИЙ") || tt.isValue("ПЕРЕЧИСЛЕННЫЙ", "ПЕРЕРАХОВАНИЙ")) || tt.isValue("ОПРЕДЕЛИТЬ", "ВИЗНАЧИТИ") || tt.isValue("ОПРЕДЕЛЯТЬ", null)) || tt.isValue("ЗАДАВАТЬ", "ЗАДАВАТИ") || tt.isValue("ПРЕДУСМАТРИВАТЬ", "ПЕРЕДБАЧАТИ")) || tt.isValue("УКАЗЫВАТЬ", "ВКАЗУВАТИ") || tt.isValue("УКАЗАТЬ", "ВКАЗАТИ")) || tt.isValue("СИЛА", "ЧИННІСТЬ")) {
                    ukaz = tt;
                    continue;
                }
                if (tt == t) 
                    continue;
                com.pullenti.ner.Token ttt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, false);
                if (tt != ttt || !(tt instanceof com.pullenti.ner.TextToken)) 
                    break;
                if (ttt.isValue("УСЛОВИЕ", null)) 
                    continue;
                if (ttt.isValue("ПОРЯДОК", null) && ukaz != null) 
                    return null;
                ThisDecree res = new ThisDecree(tt, tt);
                res.typ = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
                t = tt.getPrevious();
                if (t != null && ((t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isPronoun()))) {
                    if (t.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ") || t.isValue("ТЕКУЩИЙ", "ПОТОЧНИЙ") || t.isValue("ДАННЫЙ", "ДАНИЙ")) {
                        res.hasThisRef = true;
                        res.setBeginToken(t);
                    }
                    else if ((t.isValue("ЭТОТ", "ЦЕЙ") || t.isValue("ВЫШЕУКАЗАННЫЙ", "ВИЩЕВКАЗАНИЙ") || t.isValue("УКАЗАННЫЙ", "ЗАЗНАЧЕНИЙ")) || t.isValue("НАЗВАННЫЙ", "НАЗВАНИЙ")) {
                        res.hasOtherRef = true;
                        res.setBeginToken(t);
                    }
                }
                if (!res.hasThisRef && tt.isNewlineAfter()) 
                    return null;
                if (baseTyp != null && com.pullenti.unisharp.Utils.stringsEq(baseTyp.value, res.typ)) 
                    res.hasThisRef = true;
                return res;
            }
            if (ukaz != null) {
                if (baseTyp != null && baseTyp.value != null && (((baseTyp.value.indexOf("ДОГОВОР") >= 0) || (baseTyp.value.indexOf("ДОГОВІР") >= 0)))) 
                    return _new1182(ukaz, ukaz, true, baseTyp.value);
            }
            return null;
        }
    
        public static ThisDecree tryAttach(com.pullenti.ner.decree.internal.PartToken dtok, com.pullenti.ner.decree.internal.DecreeToken baseTyp) {
            com.pullenti.ner.Token t = dtok.getEndToken().getNext();
            if (t == null) 
                return null;
            if (t.isNewlineBefore()) {
                if (t.chars.isCyrillicLetter() && t.chars.isAllLower()) {
                }
                else 
                    return null;
            }
            com.pullenti.ner.Token t0 = t;
            if (t.isChar('.') && t.getNext() != null && !t.isNewlineAfter()) {
                if (dtok.isNewlineBefore()) 
                    return null;
                t = t.getNext();
            }
            if (t.isValue("К", null) && t.getNext() != null) 
                t = t.getNext();
            if (t != null && (t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                return null;
            com.pullenti.ner.Token tt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t, false);
            boolean br = false;
            if (tt == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                tt = com.pullenti.ner.decree.internal.DecreeToken.isKeyword(t.getNext(), false);
                if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt.getNext(), false, null, false)) 
                    br = true;
            }
            if (!(tt instanceof com.pullenti.ner.TextToken)) {
                if ((tt instanceof com.pullenti.ner.ReferentToken) && (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                    return _new1183(t, tt, (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class));
                return null;
            }
            if (tt.chars.isAllLower()) {
                if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(tt, true) != null) {
                    if (tt != t && t.chars.isCapitalUpper()) {
                    }
                    else 
                        return null;
                }
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                return null;
            ThisDecree res = new ThisDecree(t0, (br ? tt.getNext() : tt));
            res.typ = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).lemma;
            if (tt.getPrevious() instanceof com.pullenti.ner.TextToken) {
                com.pullenti.ner.Token tt1 = tt.getPrevious();
                com.pullenti.morph.MorphClass mc = tt1.getMorphClassInDictionary();
                if (mc.isAdjective() && !mc.isVerb() && !tt1.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ")) {
                    com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (nnn != null) 
                        res.typ = nnn.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (tt1.getPrevious() instanceof com.pullenti.ner.TextToken) {
                        tt1 = tt1.getPrevious();
                        mc = tt1.getMorphClassInDictionary();
                        if (mc.isAdjective() && !mc.isVerb() && !tt1.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ")) {
                            nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (nnn != null) 
                                res.typ = nnn.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        }
                    }
                }
            }
            if (tt.isChar('.') && (tt.getPrevious() instanceof com.pullenti.ner.TextToken)) 
                res.typ = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getPrevious(), com.pullenti.ner.TextToken.class)).lemma;
            if (t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isPronoun()) {
                if (t.isValue("НАСТОЯЩИЙ", "СПРАВЖНІЙ") || t.isValue("ТЕКУЩИЙ", "ПОТОЧНИЙ") || t.isValue("ДАННЫЙ", "ДАНИЙ")) 
                    res.hasThisRef = true;
                else if ((t.isValue("ЭТОТ", "ЦЕЙ") || t.isValue("ВЫШЕУКАЗАННЫЙ", "ВИЩЕВКАЗАНИЙ") || t.isValue("УКАЗАННЫЙ", "ЗАЗНАЧЕНИЙ")) || t.isValue("НАЗВАННЫЙ", "НАЗВАНИЙ")) 
                    res.hasOtherRef = true;
            }
            if (!tt.isNewlineAfter() && !res.hasThisRef) {
                com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt.getNext(), null, false);
                if (dt != null && dt.typ != com.pullenti.ner.decree.internal.DecreeToken.ItemType.MISC) 
                    return null;
                if (com.pullenti.ner.decree.internal.DecreeToken.tryAttachName(tt.getNext(), res.typ, false, false) != null) 
                    return null;
            }
            if (baseTyp != null && com.pullenti.unisharp.Utils.stringsEq(baseTyp.value, res.typ)) 
                res.hasThisRef = true;
            return res;
        }
    
        public static ThisDecree _new1182(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3, String _arg4) {
            ThisDecree res = new ThisDecree(_arg1, _arg2);
            res.hasThisRef = _arg3;
            res.typ = _arg4;
            return res;
        }
    
        public static ThisDecree _new1183(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.decree.DecreeReferent _arg3) {
            ThisDecree res = new ThisDecree(_arg1, _arg2);
            res.real = _arg3;
            return res;
        }
        public ThisDecree() {
            super();
        }
    }

    public DecreeAnalyzer() {
        super();
    }
    public static DecreeAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new DecreeAnalyzer(); } 
        catch(Exception e) { }
    }
}
