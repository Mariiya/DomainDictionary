/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.denomination;

/**
 * Анализатор деноминаций и обозначений (типа C#, A-320) 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 * Анализатор деноминаций
 */
public class DenominationAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("DENOMINATION")
     */
    public static final String ANALYZER_NAME = "DENOMINATION";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Деноминации";
    }


    @Override
    public String getDescription() {
        return "Деноминации и обозначения типа СС-300, АН-24, С++";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new DenominationAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 5;
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.denomination.internal.MetaDenom.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.denomination.internal.MetaDenom.DENOMIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("denom.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, DenominationReferent.OBJ_TYPENAME)) 
            return new DenominationReferent();
        return null;
    }

    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    // Основная функция выделения объектов
    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerDataWithOntology ad = (com.pullenti.ner.core.AnalyzerDataWithOntology)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), com.pullenti.ner.core.AnalyzerDataWithOntology.class);
        for (int k = 0; k < 2; k++) {
            boolean detectNewDenoms = false;
            java.time.LocalDateTime dt = java.time.LocalDateTime.now();
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isIgnored()) 
                    continue;
                if (t.isWhitespaceBefore()) {
                }
                else if (t.getPrevious() != null && ((t.getPrevious().isCharOf(",") || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)))) {
                }
                else 
                    continue;
                com.pullenti.ner.ReferentToken rt0 = this.tryAttachSpec(t);
                if (rt0 != null) {
                    rt0.referent = ad.registerReferent(rt0.referent);
                    kit.embedToken(rt0);
                    t = rt0;
                    continue;
                }
                if (!t.chars.isLetter()) 
                    continue;
                if (!this.canBeStartOfDenom(t)) 
                    continue;
                if ((java.time.Duration.between(dt, java.time.LocalDateTime.now())).toMinutes() > (1L)) 
                    break;
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ot = null;
                ot = ad.localOntology.tryAttach(t, null, false);
                if (ot != null && (ot.get(0).item.referent instanceof DenominationReferent)) {
                    if (this.checkAttach(ot.get(0).getBeginToken(), ot.get(0).getEndToken())) {
                        DenominationReferent cl = (DenominationReferent)com.pullenti.unisharp.Utils.cast(ot.get(0).item.referent.clone(), DenominationReferent.class);
                        cl.getOccurrence().clear();
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(cl, ot.get(0).getBeginToken(), ot.get(0).getEndToken(), null);
                        kit.embedToken(rt);
                        t = rt;
                        continue;
                    }
                }
                if (k > 0) 
                    continue;
                if (t != null && t.kit.ontology != null) {
                    if ((((ot = t.kit.ontology.attachToken(DenominationReferent.OBJ_TYPENAME, t)))) != null) {
                        if (this.checkAttach(ot.get(0).getBeginToken(), ot.get(0).getEndToken())) {
                            DenominationReferent dr = new DenominationReferent();
                            dr.mergeSlots(ot.get(0).item.referent, true);
                            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(dr), ot.get(0).getBeginToken(), ot.get(0).getEndToken(), null);
                            kit.embedToken(rt);
                            t = rt;
                            continue;
                        }
                    }
                }
                rt0 = this.tryAttach(t, false);
                if (rt0 != null) {
                    rt0.referent = ad.registerReferent(rt0.referent);
                    kit.embedToken(rt0);
                    detectNewDenoms = true;
                    t = rt0;
                    if (ad.localOntology.getItems().size() > 1000) 
                        break;
                }
            }
            if (!detectNewDenoms) 
                break;
        }
    }

    private boolean canBeStartOfDenom(com.pullenti.ner.Token t) {
        if ((t == null || !t.chars.isLetter() || t.getNext() == null) || t.isNewlineAfter()) 
            return false;
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return false;
        if (t.getLengthChar() > 4) 
            return false;
        t = t.getNext();
        if (t.chars.isLetter()) 
            return false;
        if (t instanceof com.pullenti.ner.NumberToken) 
            return true;
        if (t.isCharOf("/\\") || t.isHiphen()) 
            return t.getNext() instanceof com.pullenti.ner.NumberToken;
        if (t.isCharOf("+*&^#@!_")) 
            return true;
        return false;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        return this.tryAttach(begin, false);
    }

    public com.pullenti.ner.ReferentToken tryAttach(com.pullenti.ner.Token t, boolean forOntology) {
        if (t == null) 
            return null;
        com.pullenti.ner.ReferentToken rt0 = this.tryAttachSpec(t);
        if (rt0 != null) 
            return rt0;
        if (t.chars.isAllLower()) {
            if (!t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                if (t.getPrevious() == null || t.isWhitespaceBefore() || t.getPrevious().isCharOf(",:")) {
                }
                else 
                    return null;
            }
            else 
                return null;
        }
        StringBuilder tmp = new StringBuilder();
        com.pullenti.ner.Token t1 = t;
        boolean hiph = false;
        boolean ok = true;
        int nums = 0;
        int chars = 0;
        for (com.pullenti.ner.Token w = t1.getNext(); w != null; w = w.getNext()) {
            if (w.isWhitespaceBefore() && !forOntology) 
                break;
            if (w.isCharOf("/\\_") || w.isHiphen()) {
                hiph = true;
                tmp.append('-');
                continue;
            }
            hiph = false;
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(w, com.pullenti.ner.NumberToken.class);
            if (nt != null) {
                if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
                    break;
                t1 = nt;
                tmp.append(nt.getSourceText());
                nums++;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(w, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            if (tt.getLengthChar() > 3) {
                ok = false;
                break;
            }
            if (!Character.isLetter(tt.term.charAt(0))) {
                if (tt.isCharOf(",:") || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt, false, null, false)) 
                    break;
                if (!tt.isCharOf("+*&^#@!")) {
                    ok = false;
                    break;
                }
                chars++;
            }
            t1 = tt;
            tmp.append(tt.getSourceText());
        }
        if (!forOntology) {
            if ((tmp.length() < 1) || !ok || hiph) 
                return null;
            if (tmp.length() > 12) 
                return null;
            char last = tmp.charAt(tmp.length() - 1);
            if (last == '!') 
                return null;
            if ((nums + chars) == 0) 
                return null;
            if (!this.checkAttach(t, t1)) 
                return null;
        }
        DenominationReferent newDr = new DenominationReferent();
        newDr.addValue(t, t1);
        return new com.pullenti.ner.ReferentToken(newDr, t, t1, null);
    }

    // Некоторые специфические случаи
    private com.pullenti.ner.ReferentToken tryAttachSpec(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT && com.pullenti.unisharp.Utils.stringsEq(nt.getValue(), "1")) {
            if (t.getNext() != null && t.getNext().isHiphen()) 
                t = t.getNext();
            if ((t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().isWhitespaceBefore()) {
                if (t.getNext().isValue("C", null) || t.getNext().isValue("С", null)) {
                    DenominationReferent dr = new DenominationReferent();
                    dr.addSlot(DenominationReferent.ATTR_VALUE, "1С", false, 0);
                    dr.addSlot(DenominationReferent.ATTR_VALUE, "1C", false, 0);
                    return new com.pullenti.ner.ReferentToken(dr, t0, t.getNext(), null);
                }
            }
        }
        if (((nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT && (t.getNext() instanceof com.pullenti.ner.TextToken)) && !t.isWhitespaceAfter() && !t.getNext().chars.isAllLower()) && t.getNext().chars.isLetter()) {
            DenominationReferent dr = new DenominationReferent();
            dr.addSlot(DenominationReferent.ATTR_VALUE, (nt.getSourceText() + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term), false, 0);
            return new com.pullenti.ner.ReferentToken(dr, t0, t.getNext(), null);
        }
        return null;
    }

    private boolean checkAttach(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        for (com.pullenti.ner.Token t = begin; t != null && t != end.getNext(); t = t.getNext()) {
            if (t != begin) {
                int co = t.getWhitespacesBeforeCount();
                if (co > 0) {
                    if (co > 1) 
                        return false;
                    if (t.chars.isAllLower()) 
                        return false;
                    if (t.getPrevious().chars.isAllLower()) 
                        return false;
                }
            }
        }
        if (!end.isWhitespaceAfter() && end.getNext() != null) {
            if (!end.getNext().isCharOf(",;") && !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(end.getNext(), false, null, false)) 
                return false;
        }
        return true;
    }

    private static boolean m_Inites = false;

    public static void initialize() {
        if (m_Inites) 
            return;
        m_Inites = true;
        com.pullenti.ner.denomination.internal.MetaDenom.initialize();
        com.pullenti.ner.ProcessorService.registerAnalyzer(new DenominationAnalyzer());
    }
    public DenominationAnalyzer() {
        super();
    }
}
