/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument;

/**
 * Анализатор структуры нормативных актов и договоров: восстановление иерархической структуры фрагментов, 
 * выделение фигурантов (для договоров и судебных документов), артефактов. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 */
public class InstrumentAnalyzer extends com.pullenti.ner.Analyzer {

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    /**
     * Имя анализатора ("INSTRUMENT")
     */
    public static final String ANALYZER_NAME = "INSTRUMENT";

    @Override
    public String getCaption() {
        return "Структура нормативно-правовых документов (НПА)";
    }


    @Override
    public String getDescription() {
        return "Разбор структуры НПА на разделы и подразделы";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new InstrumentAnalyzer();
    }

    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.instrument.internal.MetaInstrument.GLOBALMETA, com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA, com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.GLOBALMETA, com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.instrument.internal.MetaInstrument.DOCIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decree.png"));
        res.put(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.PARTIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("part.png"));
        res.put(com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("participant.png"));
        res.put(com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("artefact.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentReferent.OBJ_TYPENAME)) 
            return new InstrumentReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentBlockReferent.OBJ_TYPENAME)) 
            return new InstrumentBlockReferent(null);
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentParticipantReferent.OBJ_TYPENAME)) 
            return new InstrumentParticipantReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentArtefactReferent.OBJ_TYPENAME)) 
            return new InstrumentArtefactReferent();
        return null;
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.instrument.internal.FragToken dfr = com.pullenti.ner.instrument.internal.FragToken.createDocument(kit.firstToken, 0, InstrumentKind.UNDEFINED);
        boolean needTestNewDoc = true;
        if (dfr != null && dfr.m_Doc != null) {
            com.pullenti.ner.decree.DecreeKind ki = com.pullenti.ner.decree.internal.DecreeToken.getKind(dfr.m_Doc.getTyp());
            if (((ki == com.pullenti.ner.decree.DecreeKind.CONTRACT || ki == com.pullenti.ner.decree.DecreeKind.KODEX || ki == com.pullenti.ner.decree.DecreeKind.KONVENTION) || ki == com.pullenti.ner.decree.DecreeKind.LAW || ki == com.pullenti.ner.decree.DecreeKind.ORDER) || ki == com.pullenti.ner.decree.DecreeKind.PUBLISHER || ki == com.pullenti.ner.decree.DecreeKind.USTAV) 
                needTestNewDoc = false;
            else if (com.pullenti.ner.decree.internal.DecreeToken.isJustice(dfr.m_Doc.getTyp())) 
                needTestNewDoc = false;
            else if (dfr.children.size() > 0 && dfr.children.get(0).kind == InstrumentKind.HEAD) {
                for (com.pullenti.ner.instrument.internal.FragToken ch : dfr.children.get(0).children) {
                    if (ch.kind == InstrumentKind.APPROVED && ch.referents != null) {
                        for (com.pullenti.ner.Referent r : ch.referents) {
                            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREE")) {
                                ki = ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.decree.DecreeReferent.class)).getKind();
                                if (((ki == com.pullenti.ner.decree.DecreeKind.CONTRACT || ki == com.pullenti.ner.decree.DecreeKind.KODEX || ki == com.pullenti.ner.decree.DecreeKind.KONVENTION) || ki == com.pullenti.ner.decree.DecreeKind.LAW || ki == com.pullenti.ner.decree.DecreeKind.ORDER) || ki == com.pullenti.ner.decree.DecreeKind.PUBLISHER || ki == com.pullenti.ner.decree.DecreeKind.USTAV) 
                                    needTestNewDoc = false;
                            }
                        }
                    }
                }
            }
        }
        if (needTestNewDoc) {
            com.pullenti.ner.Analyzer aa = kit.processor.findAnalyzer("DOCUMENT");
            if (aa == null) {
                for (com.pullenti.ner.Analyzer a : com.pullenti.ner.ProcessorService.getAnalyzers()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(a.getName(), "DOCUMENT")) {
                        aa = a;
                        break;
                    }
                }
            }
            if (aa != null) {
                com.pullenti.ner.ReferentToken rt = aa.processReferent(kit.firstToken, "INSTRUMENT");
                if (rt != null) 
                    return;
            }
        }
        if (dfr == null) 
            return;
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        InstrumentBlockReferent res = dfr.createReferent(ad);
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.initialize();
        com.pullenti.ner.instrument.internal.MetaInstrumentBlock.initialize();
        com.pullenti.ner.instrument.internal.MetaInstrument.initialize();
        com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.instrument.internal.InstrToken.initialize();
            com.pullenti.ner.instrument.internal.ParticipantToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new InstrumentAnalyzer());
    }
    public InstrumentAnalyzer() {
        super();
    }
}
