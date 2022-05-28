/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class OptimizerHelper {

    public static void optimize(com.pullenti.semantic.SemDocument doc, com.pullenti.semantic.SemProcessParams pars) {
        for (com.pullenti.semantic.SemBlock blk : doc.blocks) {
            for (com.pullenti.semantic.SemFragment fr : blk.fragments) {
                _optimizeGraph(fr.getGraph());
            }
            java.util.ArrayList<com.pullenti.semantic.SemObject> objs = new java.util.ArrayList<com.pullenti.semantic.SemObject>();
            com.pullenti.unisharp.Utils.addToArrayList(objs, blk.getGraph().objects);
            for (com.pullenti.semantic.SemFragment fr : blk.fragments) {
                com.pullenti.unisharp.Utils.addToArrayList(objs, fr.getGraph().objects);
            }
            for (com.pullenti.semantic.SemFragment fr : blk.fragments) {
                for (int i = fr.getGraph().links.size() - 1; i >= 0; i--) {
                    com.pullenti.semantic.SemLink li = fr.getGraph().links.get(i);
                    if (!objs.contains(li.getSource()) || !objs.contains(li.getTarget())) 
                        fr.getGraph().removeLink(li);
                }
                _processParticiples(fr.getGraph());
                _processLinks(fr.getGraph());
            }
            _sortObjects(objs);
            _processPointers(objs);
            _processFormulas(objs);
            if (pars.dontCreateAnafor) {
            }
            else {
                AnaforHelper.processAnafors(objs);
                for (com.pullenti.semantic.SemFragment fr : blk.fragments) {
                    _collapseAnafors(fr.getGraph());
                }
            }
        }
    }

    private static void _optimizeGraph(com.pullenti.semantic.SemGraph gr) {
        for (com.pullenti.semantic.SemObject o : gr.objects) {
            _optimizeTokens(o);
        }
        _sortObjects(gr.objects);
    }

    private static int _compareToks(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        if (t1.getBeginChar() < t2.getBeginChar()) 
            return -1;
        if (t1.getBeginChar() > t2.getBeginChar()) 
            return 1;
        if (t1.getEndChar() < t2.getEndChar()) 
            return -1;
        if (t1.getEndChar() > t2.getEndChar()) 
            return 1;
        return 0;
    }

    private static void _optimizeTokens(com.pullenti.semantic.SemObject o) {
        for (int i = 0; i < o.tokens.size(); i++) {
            boolean ch = false;
            for (int j = 0; j < (o.tokens.size() - 1); j++) {
                if (_compareToks(o.tokens.get(j), o.tokens.get(j + 1)) > 0) {
                    com.pullenti.ner.MetaToken t = o.tokens.get(j);
                    com.pullenti.unisharp.Utils.putArrayValue(o.tokens, j, o.tokens.get(j + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(o.tokens, j + 1, t);
                    ch = true;
                }
            }
            if (!ch) 
                break;
        }
        for (int i = 0; i < (o.tokens.size() - 1); i++) {
            if (o.tokens.get(i).getEndToken().getNext() == o.tokens.get(i + 1).getBeginToken()) {
                com.pullenti.unisharp.Utils.putArrayValue(o.tokens, i, new com.pullenti.ner.MetaToken(o.tokens.get(i).getBeginToken(), o.tokens.get(i + 1).getEndToken(), null));
                o.tokens.remove(i + 1);
                i--;
            }
        }
    }

    private static void _sortObjects(java.util.ArrayList<com.pullenti.semantic.SemObject> objs) {
        for (int i = 0; i < objs.size(); i++) {
            boolean ch = false;
            for (int j = 0; j < (objs.size() - 1); j++) {
                if (objs.get(j).compareTo(objs.get(j + 1)) > 0) {
                    com.pullenti.semantic.SemObject o = objs.get(j);
                    com.pullenti.unisharp.Utils.putArrayValue(objs, j, objs.get(j + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(objs, j + 1, o);
                    ch = true;
                }
            }
            if (!ch) 
                break;
        }
    }

    private static boolean _processParticiples(com.pullenti.semantic.SemGraph gr) {
        boolean ret = false;
        for (int i = 0; i < gr.objects.size(); i++) {
            com.pullenti.semantic.SemObject obj = gr.objects.get(i);
            if (obj.typ != com.pullenti.semantic.SemObjectType.PARTICIPLE) 
                continue;
            com.pullenti.semantic.SemLink own = null;
            boolean has = false;
            for (com.pullenti.semantic.SemLink li : obj.linksTo) {
                if (li.typ == com.pullenti.semantic.SemLinkType.PARTICIPLE) 
                    own = li;
                else 
                    has = true;
            }
            if (!has) 
                continue;
            if (own == null) {
                com.pullenti.semantic.SemObject dum = com.pullenti.semantic.SemObject._new3133(gr, com.pullenti.semantic.SemObjectType.NOUN);
                if (obj.morph != null) 
                    dum.morph = com.pullenti.morph.MorphWordForm._new3134(com.pullenti.morph.MorphClass.NOUN, obj.morph.getNumber(), obj.morph.getGender(), obj.morph.getCase());
                gr.objects.add(dum);
                own = gr.addLink(com.pullenti.semantic.SemLinkType.PARTICIPLE, dum, obj, "какой", false, null);
                ret = true;
            }
            for (int j = obj.linksTo.size() - 1; j >= 0; j--) {
                com.pullenti.semantic.SemLink li = obj.linksTo.get(j);
                if (li.typ == com.pullenti.semantic.SemLinkType.PARTICIPLE) 
                    continue;
                boolean exi = false;
                for (com.pullenti.semantic.SemLink ll : li.getSource().linksFrom) {
                    if (ll.getTarget() == own.getSource()) 
                        exi = true;
                }
                if (exi) 
                    gr.removeLink(li);
                else {
                    obj.linksTo.remove(j);
                    li.m_Target = own.getSource();
                }
                ret = true;
            }
        }
        return ret;
    }

    private static boolean _processLinks(com.pullenti.semantic.SemGraph gr) {
        boolean ret = false;
        for (int i = 0; i < gr.objects.size(); i++) {
            com.pullenti.semantic.SemObject obj = gr.objects.get(i);
            for (int j = obj.linksFrom.size() - 1; j >= 0; j--) {
                com.pullenti.semantic.SemLink li = obj.linksFrom.get(j);
                if (li.typ != com.pullenti.semantic.SemLinkType.PACIENT) 
                    continue;
                boolean exi = false;
                for (com.pullenti.semantic.SemLink ll : obj.linksFrom) {
                    if (ll != li && ll.typ == com.pullenti.semantic.SemLinkType.AGENT && ll.getTarget() == li.getTarget()) 
                        exi = true;
                }
                if (exi) {
                    if (obj.getBeginChar() > li.getTarget().getBeginChar()) {
                        gr.removeLink(li);
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    private static boolean _collapseAnafors(com.pullenti.semantic.SemGraph gr) {
        boolean ret = false;
        for (int i = 0; i < gr.objects.size(); i++) {
            com.pullenti.semantic.SemObject obj = gr.objects.get(i);
            if (obj.typ == com.pullenti.semantic.SemObjectType.PERSONALPRONOUN || com.pullenti.unisharp.Utils.stringsEq(obj.morph.normalFull, "КОТОРЫЙ")) {
            }
            else 
                continue;
            if (obj.attrs.size() > 0 || obj.quantity != null) 
                continue;
            if (obj.linksFrom.size() == 1 && obj.linksFrom.get(0).typ == com.pullenti.semantic.SemLinkType.ANAFOR) {
            }
            else if (obj.linksFrom.size() == 2 && obj.linksFrom.get(0).typ == com.pullenti.semantic.SemLinkType.ANAFOR && obj.linksFrom.get(0).altLink == obj.linksFrom.get(1)) {
            }
            else 
                continue;
            com.pullenti.semantic.SemLink alink = obj.linksFrom.get(0);
            for (com.pullenti.semantic.SemLink li : obj.linksTo) {
                com.pullenti.semantic.SemLink nli = gr.addLink(li.typ, li.getSource(), alink.getTarget(), li.question, li.isOr, li.preposition);
                if (alink.altLink != null) {
                    com.pullenti.semantic.SemLink nli2 = gr.addLink(li.typ, li.getSource(), alink.altLink.getTarget(), li.question, li.isOr, li.preposition);
                    nli2.altLink = nli;
                    nli.altLink = nli2;
                }
            }
            gr.removeObject(obj);
            i--;
            ret = true;
        }
        return ret;
    }

    private static boolean _processFormulas(java.util.ArrayList<com.pullenti.semantic.SemObject> objs) {
        boolean ret = false;
        for (int i = 0; i < objs.size(); i++) {
            com.pullenti.semantic.SemObject o = objs.get(i);
            if (o.typ != com.pullenti.semantic.SemObjectType.NOUN || !o.isValue("РАЗ", com.pullenti.semantic.SemObjectType.UNDEFINED)) 
                continue;
            if (o.quantity == null) 
                continue;
            if (o.linksFrom.size() == 0 && o.linksTo.size() == 1) {
            }
            else 
                continue;
            com.pullenti.semantic.SemObject frm = o.linksTo.get(0).getSource();
            for (int k = 0; k < 5; k++) {
                boolean brek = false;
                for (com.pullenti.semantic.SemLink li : frm.linksFrom) {
                    if (((li.typ == com.pullenti.semantic.SemLinkType.DETAIL || li.typ == com.pullenti.semantic.SemLinkType.PACIENT)) && li.getTarget() != o) {
                        if (o.getBeginChar() > frm.getEndChar() && (o.getBeginChar() < li.getTarget().getBeginChar())) {
                            brek = true;
                            o.graph.addLink(com.pullenti.semantic.SemLinkType.DETAIL, o, li.getTarget(), "чего", false, null);
                            o.graph.removeLink(li);
                        }
                        else 
                            frm = li.getTarget();
                        break;
                    }
                }
                if (brek) 
                    break;
            }
        }
        return ret;
    }

    private static boolean _processPointers(java.util.ArrayList<com.pullenti.semantic.SemObject> objs) {
        boolean ret = false;
        for (int i = 0; i < objs.size(); i++) {
            com.pullenti.semantic.SemObject o = objs.get(i);
            if (o.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                continue;
            if (o.quantity != null && com.pullenti.unisharp.Utils.stringsEq(o.quantity.spelling, "1")) {
            }
            else 
                continue;
            if (o.linksFrom.size() > 0) 
                continue;
            boolean ok = false;
            for (int j = i - 1; j >= 0; j--) {
                com.pullenti.semantic.SemObject oo = objs.get(j);
                if (oo.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                    continue;
                if (com.pullenti.unisharp.Utils.stringsNe(oo.morph.normalFull, o.morph.normalFull)) 
                    continue;
                if (oo.quantity != null && com.pullenti.unisharp.Utils.stringsNe(oo.quantity.spelling, "1")) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                for (int j = i + 1; j < objs.size(); j++) {
                    com.pullenti.semantic.SemObject oo = objs.get(j);
                    if (oo.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                        continue;
                    if (com.pullenti.unisharp.Utils.stringsNe(oo.morph.normalFull, o.morph.normalFull)) 
                        continue;
                    if (oo.findFromObject("ДРУГОЙ", com.pullenti.semantic.SemLinkType.UNDEFINED, com.pullenti.semantic.SemObjectType.UNDEFINED) != null || oo.findFromObject("ВТОРОЙ", com.pullenti.semantic.SemLinkType.UNDEFINED, com.pullenti.semantic.SemObjectType.UNDEFINED) != null) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) 
                continue;
            com.pullenti.semantic.SemObject first = com.pullenti.semantic.SemObject._new3133(o.graph, com.pullenti.semantic.SemObjectType.ADJECTIVE);
            first.tokens.add(o.tokens.get(0));
            first.morph.normalFull = "ПЕРВЫЙ";
            first.morph.normalCase = (((short)((o.morph.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? "ПЕРВАЯ" : (((short)((o.morph.getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? "ПЕРВОЕ" : "ПЕРВЫЙ"));
            first.morph.setGender(o.morph.getGender());
            o.graph.objects.add(first);
            o.graph.addLink(com.pullenti.semantic.SemLinkType.DETAIL, o, first, "какой", false, null);
            o.quantity = null;
            ret = true;
        }
        for (int i = 0; i < objs.size(); i++) {
            com.pullenti.semantic.SemObject o = objs.get(i);
            if (o.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                continue;
            if (o.quantity != null && com.pullenti.unisharp.Utils.stringsEq(o.quantity.spelling, "1")) {
            }
            else 
                continue;
            com.pullenti.semantic.SemObject other = o.findFromObject("ДРУГОЙ", com.pullenti.semantic.SemLinkType.UNDEFINED, com.pullenti.semantic.SemObjectType.UNDEFINED);
            if (other == null) 
                continue;
            boolean ok = false;
            for (int j = i - 1; j >= 0; j--) {
                com.pullenti.semantic.SemObject oo = objs.get(j);
                if (oo.typ != com.pullenti.semantic.SemObjectType.NOUN) 
                    continue;
                if (com.pullenti.unisharp.Utils.stringsNe(oo.morph.normalFull, o.morph.normalFull)) 
                    continue;
                if (oo.findFromObject("ПЕРВЫЙ", com.pullenti.semantic.SemLinkType.UNDEFINED, com.pullenti.semantic.SemObjectType.UNDEFINED) != null) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                other.morph.normalFull = "ВТОРОЙ";
                other.morph.normalCase = (((short)((o.morph.getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? "ВТОРАЯ" : (((short)((o.morph.getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value()) ? "ВТОРОЕ" : "ВТОРОЙ"));
            }
        }
        return ret;
    }
    public OptimizerHelper() {
    }
}
