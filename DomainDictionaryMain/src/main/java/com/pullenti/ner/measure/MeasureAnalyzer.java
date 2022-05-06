/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure;

/**
 * Анализатор для измеряемых величин. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor,
 */
public class MeasureAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("MEASURE")
     */
    public static final String ANALYZER_NAME = "MEASURE";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Измеряемые величины";
    }


    @Override
    public String getDescription() {
        return "Диапазоны и просто значения в некоторых единицах измерения";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new MeasureAnalyzer();
    }

    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.measure.internal.MeasureMeta.GLOBALMETA, com.pullenti.ner.measure.internal.UnitMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.measure.internal.MeasureMeta.IMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("measure.png"));
        res.put(com.pullenti.ner.measure.internal.UnitMeta.IMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("munit.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, MeasureReferent.OBJ_TYPENAME)) 
            return new MeasureReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, UnitReferent.OBJ_TYPENAME)) 
            return new UnitReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        com.pullenti.ner.core.TerminCollection addunits = null;
        if (kit.ontology != null) {
            addunits = new com.pullenti.ner.core.TerminCollection();
            for (com.pullenti.ner.ExtOntologyItem r : kit.ontology.items) {
                UnitReferent uu = (UnitReferent)com.pullenti.unisharp.Utils.cast(r.referent, UnitReferent.class);
                if (uu == null) 
                    continue;
                if (uu.m_Unit != null) 
                    continue;
                for (com.pullenti.ner.Slot s : uu.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), UnitReferent.ATTR_NAME) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), UnitReferent.ATTR_FULLNAME)) 
                        addunits.add(com.pullenti.ner.core.Termin._new100((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), uu));
                }
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            if (t.isTableControlChar()) 
                continue;
            com.pullenti.ner.measure.internal.MeasureToken mt = com.pullenti.ner.measure.internal.MeasureToken.tryParseMinimal(t, addunits, false);
            if (mt == null) 
                mt = com.pullenti.ner.measure.internal.MeasureToken.tryParse(t, addunits, true, false, false, false);
            if (mt == null) 
                continue;
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = mt.createRefenetsTokensWithRegister(ad, true);
            if (rts == null) 
                continue;
            for (int i = 0; i < rts.size(); i++) {
                com.pullenti.ner.ReferentToken rt = rts.get(i);
                t.kit.embedToken(rt);
                t = rt;
                for (int j = i + 1; j < rts.size(); j++) {
                    if (rts.get(j).getBeginToken() == rt.getBeginToken()) 
                        rts.get(j).setBeginToken(t);
                    if (rts.get(j).getEndToken() == rt.getEndToken()) 
                        rts.get(j).setEndToken(t);
                }
            }
        }
        if (kit.ontology != null) {
            for (com.pullenti.ner.Referent e : ad.getReferents()) {
                UnitReferent u = (UnitReferent)com.pullenti.unisharp.Utils.cast(e, UnitReferent.class);
                if (u == null) 
                    continue;
                for (com.pullenti.ner.ExtOntologyItem r : kit.ontology.items) {
                    UnitReferent uu = (UnitReferent)com.pullenti.unisharp.Utils.cast(r.referent, UnitReferent.class);
                    if (uu == null) 
                        continue;
                    boolean ok = false;
                    for (com.pullenti.ner.Slot s : uu.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), UnitReferent.ATTR_NAME) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), UnitReferent.ATTR_FULLNAME)) {
                            if (u.findSlot(null, s.getValue(), true) != null) {
                                ok = true;
                                break;
                            }
                        }
                    }
                    if (ok) {
                        u.ontologyItems = new java.util.ArrayList<com.pullenti.ner.ExtOntologyItem>();
                        u.ontologyItems.add(r);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        com.pullenti.ner.measure.internal.MeasureToken mt = com.pullenti.ner.measure.internal.MeasureToken.tryParseMinimal(begin, null, true);
        if (mt != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = mt.createRefenetsTokensWithRegister(null, true);
            if (rts != null) 
                return rts.get(rts.size() - 1);
        }
        return null;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (!(begin instanceof com.pullenti.ner.TextToken)) 
            return null;
        com.pullenti.ner.measure.internal.UnitToken ut = com.pullenti.ner.measure.internal.UnitToken.tryParse(begin, null, null, false);
        if (ut != null) 
            return new com.pullenti.ner.ReferentToken(ut.createReferentWithRegister(null), ut.getBeginToken(), ut.getEndToken(), null);
        UnitReferent u = new UnitReferent();
        u.addSlot(UnitReferent.ATTR_NAME, begin.getSourceText(), false, 0);
        return new com.pullenti.ner.ReferentToken(u, begin, begin, null);
    }

    private static boolean m_Initialized = false;

    private static Object m_Lock;

    public static void initialize() {
        synchronized (m_Lock) {
            if (m_Initialized) 
                return;
            m_Initialized = true;
            com.pullenti.ner.measure.internal.MeasureMeta.initialize();
            com.pullenti.ner.measure.internal.UnitMeta.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.measure.internal.UnitsHelper.initialize();
            com.pullenti.ner.measure.internal.NumbersWithUnitToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
            com.pullenti.ner.ProcessorService.registerAnalyzer(new MeasureAnalyzer());
        }
    }

    public MeasureAnalyzer() {
        super();
    }
    
    static {
        m_Lock = new Object();
    }
}
