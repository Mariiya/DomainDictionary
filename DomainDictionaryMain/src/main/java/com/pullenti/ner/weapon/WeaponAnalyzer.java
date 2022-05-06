/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.weapon;

/**
 * Анализатор оружия
 */
public class WeaponAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("WEAPON")
     */
    public static final String ANALYZER_NAME = "WEAPON";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Оружие";
    }


    @Override
    public String getDescription() {
        return "Оружие (пистолеты, пулемёты)";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new WeaponAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.weapon.internal.MetaWeapon.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.weapon.internal.MetaWeapon.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("weapon.jpg"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, WeaponReferent.OBJ_TYPENAME)) 
            return new WeaponReferent();
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
            java.util.ArrayList<com.pullenti.ner.weapon.internal.WeaponItemToken> its = com.pullenti.ner.weapon.internal.WeaponItemToken.tryParseList(t, 10);
            if (its == null) 
                continue;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this.tryAttach(its, false);
            if (rts != null) {
                for (com.pullenti.ner.ReferentToken rt : rts) {
                    rt.referent = ad.registerReferent(rt.referent);
                    kit.embedToken(rt);
                    t = rt;
                    for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_MODEL)) {
                            String mod = s.getValue().toString();
                            for (int k = 0; k < 2; k++) {
                                if (!Character.isDigit(mod.charAt(0))) {
                                    java.util.ArrayList<com.pullenti.ner.Referent> li;
                                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli3012 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
                                    boolean inoutres3013 = com.pullenti.unisharp.Utils.tryGetValue(objsByModel, mod, wrapli3012);
                                    li = wrapli3012.value;
                                    if (!inoutres3013) 
                                        objsByModel.put(mod, (li = new java.util.ArrayList<com.pullenti.ner.Referent>()));
                                    if (!li.contains(rt.referent)) 
                                        li.add(rt.referent);
                                    models.addString(mod, li, null, false);
                                }
                                if (k > 0) 
                                    break;
                                String brand = rt.referent.getStringValue(WeaponReferent.ATTR_BRAND);
                                if (brand == null) 
                                    break;
                                mod = (brand + " " + mod);
                            }
                        }
                        else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_NAME)) 
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
                com.pullenti.ner.weapon.internal.WeaponItemToken tit = com.pullenti.ner.weapon.internal.WeaponItemToken.tryParse(tok.getBeginToken().getPrevious(), null, false, true);
                if (tit != null && tit.typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.BRAND) {
                    tr.addSlot(WeaponReferent.ATTR_BRAND, tit.value, false, 0);
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
        java.util.ArrayList<com.pullenti.ner.weapon.internal.WeaponItemToken> its = com.pullenti.ner.weapon.internal.WeaponItemToken.tryParseList(begin, 10);
        if (its == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> rr = this.tryAttach(its, true);
        if (rr != null && rr.size() > 0) 
            return rr.get(0);
        return null;
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(java.util.ArrayList<com.pullenti.ner.weapon.internal.WeaponItemToken> its, boolean attach) {
        WeaponReferent tr = new WeaponReferent();
        int i;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.weapon.internal.WeaponItemToken noun = null;
        com.pullenti.ner.weapon.internal.WeaponItemToken brand = null;
        com.pullenti.ner.weapon.internal.WeaponItemToken model = null;
        for (i = 0; i < its.size(); i++) {
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.NOUN) {
                if (its.size() == 1) 
                    return null;
                if (tr.findSlot(WeaponReferent.ATTR_TYPE, null, true) != null) {
                    if (tr.findSlot(WeaponReferent.ATTR_TYPE, its.get(i).value, true) == null) 
                        break;
                }
                if (!its.get(i).isInternal) 
                    noun = its.get(i);
                tr.addSlot(WeaponReferent.ATTR_TYPE, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(WeaponReferent.ATTR_TYPE, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.BRAND) {
                if (tr.findSlot(WeaponReferent.ATTR_BRAND, null, true) != null) {
                    if (tr.findSlot(WeaponReferent.ATTR_BRAND, its.get(i).value, true) == null) 
                        break;
                }
                if (!its.get(i).isInternal) {
                    if (noun != null && noun.isDoubt) 
                        noun.isDoubt = false;
                }
                brand = its.get(i);
                tr.addSlot(WeaponReferent.ATTR_BRAND, its.get(i).value, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.MODEL) {
                if (tr.findSlot(WeaponReferent.ATTR_MODEL, null, true) != null) {
                    if (tr.findSlot(WeaponReferent.ATTR_MODEL, its.get(i).value, true) == null) 
                        break;
                }
                model = its.get(i);
                tr.addSlot(WeaponReferent.ATTR_MODEL, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(WeaponReferent.ATTR_MODEL, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.NAME) {
                if (tr.findSlot(WeaponReferent.ATTR_NAME, null, true) != null) 
                    break;
                tr.addSlot(WeaponReferent.ATTR_NAME, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(WeaponReferent.ATTR_NAME, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.NUMBER) {
                if (tr.findSlot(WeaponReferent.ATTR_NUMBER, null, true) != null) 
                    break;
                tr.addSlot(WeaponReferent.ATTR_NUMBER, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(WeaponReferent.ATTR_NUMBER, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.CALIBER) {
                if (tr.findSlot(WeaponReferent.ATTR_CALIBER, null, true) != null) 
                    break;
                tr.addSlot(WeaponReferent.ATTR_CALIBER, its.get(i).value, false, 0);
                if (its.get(i).altValue != null) 
                    tr.addSlot(WeaponReferent.ATTR_CALIBER, its.get(i).altValue, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.DEVELOPER) {
                tr.addSlot(WeaponReferent.ATTR_REF, its.get(i).ref, false, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
            if (its.get(i).typ == com.pullenti.ner.weapon.internal.WeaponItemToken.Typs.DATE) {
                if (tr.findSlot(WeaponReferent.ATTR_DATE, null, true) != null) 
                    break;
                tr.addSlot(WeaponReferent.ATTR_DATE, its.get(i).ref, true, 0);
                t1 = its.get(i).getEndToken();
                continue;
            }
        }
        boolean hasGoodNoun = (noun == null ? false : !noun.isDoubt);
        WeaponReferent prev = null;
        if (noun == null) {
            for (com.pullenti.ner.Token tt = its.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                if ((((prev = (WeaponReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), WeaponReferent.class)))) != null) {
                    java.util.ArrayList<com.pullenti.ner.Slot> addSlots = new java.util.ArrayList<com.pullenti.ner.Slot>();
                    for (com.pullenti.ner.Slot s : prev.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_TYPE)) 
                            tr.addSlot(s.getTypeName(), s.getValue(), false, 0);
                        else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_BRAND) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_BRAND) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_MODEL)) {
                            if (tr.findSlot(s.getTypeName(), null, true) == null) 
                                addSlots.add(s);
                        }
                    }
                    for (com.pullenti.ner.Slot s : addSlots) {
                        tr.addSlot(s.getTypeName(), s.getValue(), false, 0);
                    }
                    hasGoodNoun = true;
                    break;
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && ((!tt.chars.isLetter() || tt.getMorph()._getClass().isConjunction()))) {
                }
                else 
                    break;
            }
        }
        if (noun == null && model != null) {
            int cou = 0;
            for (com.pullenti.ner.Token tt = its.get(0).getBeginToken().getPrevious(); tt != null && (cou < 100); tt = tt.getPrevious(),cou++) {
                if ((((prev = (WeaponReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), WeaponReferent.class)))) != null) {
                    if (prev.findSlot(WeaponReferent.ATTR_MODEL, model.value, true) == null) 
                        continue;
                    java.util.ArrayList<com.pullenti.ner.Slot> addSlots = new java.util.ArrayList<com.pullenti.ner.Slot>();
                    for (com.pullenti.ner.Slot s : prev.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_TYPE)) 
                            tr.addSlot(s.getTypeName(), s.getValue(), false, 0);
                        else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_BRAND) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), WeaponReferent.ATTR_BRAND)) {
                            if (tr.findSlot(s.getTypeName(), null, true) == null) 
                                addSlots.add(s);
                        }
                    }
                    for (com.pullenti.ner.Slot s : addSlots) {
                        tr.addSlot(s.getTypeName(), s.getValue(), false, 0);
                    }
                    hasGoodNoun = true;
                    break;
                }
            }
        }
        if (hasGoodNoun) {
        }
        else if (noun != null) {
            if (model != null || ((brand != null && !brand.isDoubt))) {
            }
            else 
                return null;
        }
        else {
            if (model == null) 
                return null;
            int cou = 0;
            boolean ok = false;
            for (com.pullenti.ner.Token tt = t1.getPrevious(); tt != null && (cou < 20); tt = tt.getPrevious(),cou++) {
                if ((tt.isValue("ОРУЖИЕ", null) || tt.isValue("ВООРУЖЕНИЕ", null) || tt.isValue("ВЫСТРЕЛ", null)) || tt.isValue("ВЫСТРЕЛИТЬ", null)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return null;
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(new com.pullenti.ner.ReferentToken(tr, its.get(0).getBeginToken(), t1, null));
        return res;
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.measure.MeasureAnalyzer.initialize();
        com.pullenti.ner.weapon.internal.MetaWeapon.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.weapon.internal.WeaponItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new WeaponAnalyzer());
    }
    public WeaponAnalyzer() {
        super();
    }
}
