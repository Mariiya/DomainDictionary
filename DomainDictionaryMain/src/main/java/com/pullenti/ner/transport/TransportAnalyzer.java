/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.transport;

/**
 * Анализатор транспортных стредств
 */
public class TransportAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("TRANSPORT")
     */
    public static final String ANALYZER_NAME = "TRANSPORT";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Транспорт";
    }


    @Override
    public String getDescription() {
        return "Техника, автомобили, самолёты, корабли...";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new TransportAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.transport.internal.MetaTransport.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(TransportKind.FLY.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("fly.png"));
        res.put(TransportKind.SHIP.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("ship.png"));
        res.put(TransportKind.SPACE.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("space.png"));
        res.put(TransportKind.TRAIN.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("train.png"));
        res.put(TransportKind.AUTO.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("auto.png"));
        res.put(com.pullenti.ner.transport.internal.MetaTransport.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("transport.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, TransportReferent.OBJ_TYPENAME)) 
            return new TransportReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, "ORGANIZATION"});
    }


    @Override
    public int getProgressWeight() {
        return 5;
    }


    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        com.pullenti.ner.core.TerminCollection models = new com.pullenti.ner.core.TerminCollection();
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> objsByModel = new java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>();
        com.pullenti.ner.core.TerminCollection objByNames = new com.pullenti.ner.core.TerminCollection();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            java.util.ArrayList<com.pullenti.ner.transport.internal.TransItemToken> its = com.pullenti.ner.transport.internal.TransItemToken.tryParseList(t, 10);
            if (its == null) 
                continue;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this.tryAttach(its, false);
            if (rts != null) {
                for (com.pullenti.ner.ReferentToken rt : rts) {
                    int cou = 0;
                    for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null && (cou < 1000); tt = tt.getPrevious(),cou++) {
                        TransportReferent tr = (TransportReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), TransportReferent.class);
                        if (tr == null) 
                            continue;
                        boolean ok = true;
                        for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                            if (tr.findSlot(s.getTypeName(), s.getValue(), true) == null) {
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            rt.referent = tr;
                            break;
                        }
                    }
                    rt.referent = ad.registerReferent(rt.referent);
                    kit.embedToken(rt);
                    t = rt;
                    for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), TransportReferent.ATTR_MODEL)) {
                            String mod = s.getValue().toString();
                            for (int k = 0; k < 2; k++) {
                                if (!Character.isDigit(mod.charAt(0))) {
                                    java.util.ArrayList<com.pullenti.ner.Referent> li;
                                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2909 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
                                    boolean inoutres2910 = com.pullenti.unisharp.Utils.tryGetValue(objsByModel, mod, wrapli2909);
                                    li = wrapli2909.value;
                                    if (!inoutres2910) 
                                        objsByModel.put(mod, (li = new java.util.ArrayList<com.pullenti.ner.Referent>()));
                                    if (!li.contains(rt.referent)) 
                                        li.add(rt.referent);
                                    models.addString(mod, li, null, false);
                                }
                                if (k > 0) 
                                    break;
                                String brand = rt.referent.getStringValue(TransportReferent.ATTR_BRAND);
                                if (brand == null) 
                                    break;
                                mod = (brand + " " + mod);
                            }
                        }
                        else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), TransportReferent.ATTR_NAME)) 
                            objByNames.add(com.pullenti.ner.core.Termin._new100(s.getValue().toString(), rt.referent));
                    }
                }
            }
        }
        if (objsByModel.size() == 0 && objByNames.termins.size() == 0) 
            return;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 10);
            if (br != null) {
                com.pullenti.ner.core.TerminToken toks = objByNames.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (toks != null && toks.getEndToken().getNext() == br.getEndToken()) {
                    com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(toks.termin.tag, com.pullenti.ner.Referent.class), br.getBeginToken(), br.getEndToken(), null);
                    kit.embedToken(rt0);
                    t = rt0;
                    continue;
                }
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                continue;
            if (!t.chars.isLetter()) 
                continue;
            com.pullenti.ner.core.TerminToken tok = models.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) {
                if (!t.chars.isAllLower()) 
                    tok = objByNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok == null) 
                    continue;
            }
            if (!tok.isWhitespaceAfter()) {
                if (tok.getEndToken().getNext() == null || !tok.getEndToken().getNext().isCharOf(",.)")) {
                    if (!com.pullenti.ner.core.BracketHelper.isBracket(tok.getEndToken().getNext(), false)) 
                        continue;
                }
            }
            com.pullenti.ner.Referent tr = null;
            java.util.ArrayList<com.pullenti.ner.Referent> li = (java.util.ArrayList<com.pullenti.ner.Referent>)com.pullenti.unisharp.Utils.cast(tok.termin.tag, java.util.ArrayList.class);
            if (li != null && li.size() == 1) 
                tr = li.get(0);
            else 
                tr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.Referent.class);
            if (tr != null) {
                com.pullenti.ner.transport.internal.TransItemToken tit = com.pullenti.ner.transport.internal.TransItemToken.tryParse(tok.getBeginToken().getPrevious(), null, false, true);
                if (tit != null && tit.typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.BRAND) {
                    tr.addSlot(TransportReferent.ATTR_BRAND, tit.value, false, 0);
                    tok.setBeginToken(tit.getBeginToken());
                }
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(tr, tok.getBeginToken(), tok.getEndToken(), null);
                kit.embedToken(rt0);
                t = rt0;
                continue;
            }
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        java.util.ArrayList<com.pullenti.ner.transport.internal.TransItemToken> its = com.pullenti.ner.transport.internal.TransItemToken.tryParseList(begin, 10);
        if (its == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rr = this.tryAttach(its, true);
        if (rr != null && rr.size() > 0) 
            return rr.get(0);
        return null;
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(java.util.ArrayList<com.pullenti.ner.transport.internal.TransItemToken> its, boolean attach) {
        TransportReferent tr = new TransportReferent();
        int i;
        com.pullenti.ner.Token t1 = null;
        boolean brandIsDoubt = false;
        for (i = 0; i < its.size(); i++) {
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.NOUN) {
                if (tr.findSlot(TransportReferent.ATTR_TYPE, null, true) != null) 
                    break;
                if (its.get(i).kind != TransportKind.UNDEFINED) {
                    if (tr.getKind() != TransportKind.UNDEFINED && its.get(i).kind != tr.getKind()) 
                        break;
                    else 
                        tr.setKind(its.get(i).kind);
                }
                tr.addSlot(TransportReferent.ATTR_TYPE, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(TransportReferent.ATTR_TYPE, its.get(i).altValue, false, 0);
                if (its.get(i).state != null) 
                    tr.addGeo(its.get(i).state);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.GEO) {
                if (its.get(i).state != null) 
                    tr.addGeo(its.get(i).state);
                else if (its.get(i).ref != null) 
                    tr.addGeo(its.get(i).ref);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.BRAND) {
                if (tr.findSlot(TransportReferent.ATTR_BRAND, null, true) != null) {
                    if (tr.findSlot(TransportReferent.ATTR_BRAND, its.get(i).value, true) == null) 
                        break;
                }
                if (its.get(i).kind != TransportKind.UNDEFINED) {
                    if (tr.getKind() != TransportKind.UNDEFINED && its.get(i).kind != tr.getKind()) 
                        break;
                    else 
                        tr.setKind(its.get(i).kind);
                }
                tr.addSlot(TransportReferent.ATTR_BRAND, its.get(i).value, false, 0);
                t1 = its.get(i).getEndToken();
                brandIsDoubt = its.get(i).isDoubt;
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.MODEL) {
                if (tr.findSlot(TransportReferent.ATTR_MODEL, null, true) != null) 
                    break;
                tr.addSlot(TransportReferent.ATTR_MODEL, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(TransportReferent.ATTR_MODEL, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.CLASS) {
                if (tr.findSlot(TransportReferent.ATTR_CLASS, null, true) != null) 
                    break;
                tr.addSlot(TransportReferent.ATTR_CLASS, its.get(i).value, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.NAME) {
                if (tr.findSlot(TransportReferent.ATTR_NAME, null, true) != null) 
                    break;
                tr.addSlot(TransportReferent.ATTR_NAME, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(TransportReferent.ATTR_NAME, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.NUMBER) {
                if (tr.findSlot(TransportReferent.ATTR_NUMBER, null, true) != null) 
                    break;
                if (its.get(i).kind != TransportKind.UNDEFINED) {
                    if (tr.getKind() != TransportKind.UNDEFINED && its.get(i).kind != tr.getKind()) 
                        break;
                    else 
                        tr.setKind(its.get(i).kind);
                }
                tr.addSlot(TransportReferent.ATTR_NUMBER, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(TransportReferent.ATTR_NUMBER_REGION, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.ORG) {
                if (tr.findSlot(TransportReferent.ATTR_ORG, null, true) != null) 
                    break;
                if (!its.get(i).getMorph().getCase().isUndefined() && !its.get(i).getMorph().getCase().isGenitive()) 
                    break;
                tr.addSlot(TransportReferent.ATTR_ORG, its.get(i).ref, true, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.DATE) {
                if (tr.findSlot(TransportReferent.ATTR_DATE, null, true) != null) 
                    break;
                tr.addSlot(TransportReferent.ATTR_DATE, its.get(i).ref, true, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.ROUTE) {
                if (tr.findSlot(TransportReferent.ATTR_ROUTEPOINT, null, true) != null) 
                    break;
                for (Object o : its.get(i).routeItems) {
                    tr.addSlot(TransportReferent.ATTR_ROUTEPOINT, o, false, 0);
                }
                t1 = its.get(i).getEndToken();
                continue;
            }
        }
        if (!tr.check(attach, brandIsDoubt)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(new com.pullenti.ner.ReferentToken(tr, its.get(0).getBeginToken(), t1, null));
        if ((i < its.size()) && tr.getKind() == TransportKind.SHIP && its.get(i - 1).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.NAME) {
            for (; i < its.size(); i++) {
                if (its.get(i).typ != com.pullenti.ner.transport.internal.TransItemToken.Typs.NAME || !its.get(i).isAfterConjunction) 
                    break;
                TransportReferent tr1 = new TransportReferent();
                tr1.mergeSlots(tr, true);
                tr1.addSlot(TransportReferent.ATTR_NAME, its.get(i).value, true, 0);
                res.add(new com.pullenti.ner.ReferentToken(tr1, its.get(i).getBeginToken(), its.get(i).getEndToken(), null));
            }
        }
        else if (i == its.size() && its.get(its.size() - 1).typ == com.pullenti.ner.transport.internal.TransItemToken.Typs.NUMBER) {
            for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                if (!tt.isCommaAnd()) 
                    break;
                com.pullenti.ner.transport.internal.TransItemToken nn = com.pullenti.ner.transport.internal.TransItemToken._attachRusAutoNumber(tt.getNext());
                if (nn == null) 
                    nn = com.pullenti.ner.transport.internal.TransItemToken._attachNumber(tt.getNext(), false);
                if (nn == null || nn.typ != com.pullenti.ner.transport.internal.TransItemToken.Typs.NUMBER) 
                    break;
                TransportReferent tr1 = new TransportReferent();
                for (com.pullenti.ner.Slot s : tr.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), TransportReferent.ATTR_NUMBER)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), TransportReferent.ATTR_NUMBER_REGION) && nn.altValue != null) 
                            continue;
                        tr1.addSlot(s.getTypeName(), s.getValue(), false, 0);
                    }
                }
                tr1.addSlot(TransportReferent.ATTR_NUMBER, nn.value, true, 0);
                if (nn.altValue != null) 
                    tr1.addSlot(TransportReferent.ATTR_NUMBER_REGION, nn.altValue, true, 0);
                res.add(new com.pullenti.ner.ReferentToken(tr1, nn.getBeginToken(), nn.getEndToken(), null));
                tt = nn.getEndToken();
            }
        }
        return res;
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.transport.internal.MetaTransport.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.transport.internal.TransItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new TransportAnalyzer());
    }
    public TransportAnalyzer() {
        super();
    }
}
