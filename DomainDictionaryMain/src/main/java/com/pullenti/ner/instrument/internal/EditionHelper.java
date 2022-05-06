/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

/**
 * Поддержка анализа редакций для фрагментов НПА
 */
public class EditionHelper {

    public static void analizeEditions(FragToken root) {
        if (root.number == 6 && root.kind == com.pullenti.ner.instrument.InstrumentKind.SUBITEM) {
        }
        if (root.subNumber == 67) {
        }
        if (root.children.size() > 1 && root.children.get(0).kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER && root.children.get(1).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) {
            if (root.children.get(1).getBeginToken().isValue("УТРАТИТЬ", "ВТРАТИТИ") && root.children.get(1).getBeginToken().getNext() != null && root.children.get(1).getBeginToken().getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
                root.isExpired = true;
        }
        if ((!root.isExpired && root.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION && root.getBeginToken().isValue("АБЗАЦ", null)) && root.getBeginToken().getNext() != null && root.getBeginToken().getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) 
            root.isExpired = true;
        if (root.isExpired || ((root.itok != null && root.itok.isExpired))) {
            root.isExpired = true;
            if (root.referents == null) 
                root.referents = new java.util.ArrayList<com.pullenti.ner.Referent>();
            for (com.pullenti.ner.Token tt = root.getBeginToken(); tt != null && tt.getEndChar() <= root.getEndChar(); tt = tt.getNext()) {
                com.pullenti.ner.decree.DecreeReferent dec = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                if (dec != null) {
                    if (!root.referents.contains(dec)) 
                        root.referents.add(dec);
                }
            }
            return;
        }
        int i0;
        for (i0 = 0; i0 < root.children.size(); i0++) {
            FragToken ch = root.children.get(i0);
            if (((ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT || ch.kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD || ch.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER) || ch.kind == com.pullenti.ner.instrument.InstrumentKind.NAME || ch.kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT) || ch.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
            }
            else 
                break;
        }
        if (root.number > 0) {
            FragToken edt1 = _getLastChild(root);
            if (edt1 != null && edt1.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS && edt1.tag == null) {
                if (_canBeEditionFor(root, edt1) > 0) {
                    if (root.referents == null) 
                        root.referents = edt1.referents;
                    else 
                        for (com.pullenti.ner.Referent r : edt1.referents) {
                            if (!root.referents.contains(r)) 
                                root.referents.add(r);
                        }
                    edt1.tag = edt1;
                }
            }
        }
        if (i0 >= root.children.size()) {
            for (FragToken ch : root.children) {
                analizeEditions(ch);
            }
            return;
        }
        FragToken ch0 = root.children.get(i0);
        boolean ok = false;
        if (_canBeEditionFor(root, ch0) >= 0) {
            ok = true;
            if (i0 > 0 && ((root.children.get(i0 - 1).kind == com.pullenti.ner.instrument.InstrumentKind.CONTENT || root.children.get(i0 - 1).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION)) && ((i0 + 1) < root.children.size())) {
                if (_canBeEditionFor(root.children.get(i0 - 1), ch0) >= 0) 
                    ok = false;
            }
        }
        if (((i0 + 1) < root.children.size()) && _canBeEditionFor(root, root.children.get(root.children.size() - 1)) >= 0 && (_canBeEditionFor(root.children.get(root.children.size() - 1), root.children.get(root.children.size() - 1)) < 0)) {
            ok = true;
            ch0 = root.children.get(root.children.size() - 1);
        }
        if (ok && ch0.tag == null) {
            if (root.referents == null) 
                root.referents = ch0.referents;
            else 
                for (com.pullenti.ner.Referent r : ch0.referents) {
                    if (!root.referents.contains(r)) 
                        root.referents.add(r);
                }
            ch0.tag = ch0;
        }
        for (int i = 0; i < root.children.size(); i++) {
            FragToken ch = root.children.get(i);
            FragToken edt = null;
            FragToken edt2 = null;
            if (ch.number > 0 && i > 0) 
                edt = _getLastChild(root.children.get(i - 1));
            if (((i + 1) < root.children.size()) && root.children.get(i + 1).kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS) 
                edt2 = root.children.get(i + 1);
            if (edt != null) {
                if (_canBeEditionFor(ch, edt) < 1) 
                    edt = null;
            }
            if (edt2 != null) {
                if (_canBeEditionFor(ch, edt2) < 0) 
                    edt2 = null;
            }
            if (edt != null && edt.tag == null) {
                if (ch.referents == null) 
                    ch.referents = edt.referents;
                else 
                    for (com.pullenti.ner.Referent r : edt.referents) {
                        if (!ch.referents.contains(r)) 
                            ch.referents.add(r);
                    }
                edt.tag = ch;
            }
            if (edt2 != null && edt2.tag == null) {
                if (ch.referents == null) 
                    ch.referents = edt2.referents;
                else 
                    for (com.pullenti.ner.Referent r : edt2.referents) {
                        if (!ch.referents.contains(r)) 
                            ch.referents.add(r);
                    }
                edt2.tag = ch;
            }
        }
        for (FragToken ch : root.children) {
            analizeEditions(ch);
        }
    }

    private static FragToken _getLastChild(FragToken fr) {
        if (fr.children.size() == 0) 
            return fr;
        return _getLastChild(fr.children.get(fr.children.size() - 1));
    }

    private static int _canBeEditionFor(FragToken fr, FragToken edt) {
        if (edt == null || edt.kind != com.pullenti.ner.instrument.InstrumentKind.EDITIONS || edt.referents == null) 
            return -1;
        if (fr.subNumber3 == 67) {
        }
        com.pullenti.ner.Token t = edt.getBeginToken();
        if (t.isChar('(') && t.getNext() != null) 
            t = t.getNext();
        if (t.isValue("АБЗАЦ", null)) 
            return (fr.kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION ? 1 : -1);
        com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, false);
        if (pt == null) 
            pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(t, null, false, true);
        if (pt == null) 
            return 0;
        if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CLAUSE) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.CLAUSE) 
                return -1;
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PART) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && fr.kind != com.pullenti.ner.instrument.InstrumentKind.DOCPART && fr.kind != com.pullenti.ner.instrument.InstrumentKind.ITEM) 
                return -1;
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.ITEM) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART && fr.kind != com.pullenti.ner.instrument.InstrumentKind.ITEM && fr.kind != com.pullenti.ner.instrument.InstrumentKind.SUBITEM) 
                return -1;
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBITEM) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.SUBITEM) {
                if (fr.kind == com.pullenti.ner.instrument.InstrumentKind.ITEM && t.isValue("ПП", null)) {
                }
                else 
                    return -1;
            }
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.CHAPTER) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.CHAPTER) 
                return -1;
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.PARAGRAPH) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH) 
                return -1;
        }
        else if (pt.typ == com.pullenti.ner.decree.internal.PartToken.ItemType.SUBPARAGRAPH) {
            if (fr.kind != com.pullenti.ner.instrument.InstrumentKind.SUBPARAGRAPH) 
                return -1;
        }
        if (pt.values.size() == 0) 
            return 0;
        if (fr.number == 0) 
            return -1;
        if (com.pullenti.unisharp.Utils.stringsEq(fr.getNumberString(), pt.values.get(0).value)) 
            return 1;
        if (pt.values.get(0).value.endsWith("." + fr.getNumberString())) 
            return 0;
        if (fr.number == com.pullenti.ner.decree.internal.PartToken.getNumber(pt.values.get(0).value)) {
            if (fr.subNumber == 0) 
                return 1;
        }
        return -1;
    }
    public EditionHelper() {
    }
}
