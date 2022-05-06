/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.money;

/**
 * Анализатор для денежных сумм
 */
public class MoneyAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("MONEY")
     */
    public static final String ANALYZER_NAME = "MONEY";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Деньги";
    }


    @Override
    public String getDescription() {
        return "Деньги...";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new MoneyAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.money.internal.MoneyMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.money.internal.MoneyMeta.IMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("money2.png"));
        res.put(com.pullenti.ner.money.internal.MoneyMeta.IMAGE2ID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("moneyerr.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"GEO", "DATE"});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, MoneyReferent.OBJ_TYPENAME)) 
            return new MoneyReferent();
        return null;
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            com.pullenti.ner.ReferentToken mon = tryParse(t);
            if (mon != null) {
                mon.referent = ad.registerReferent(mon.referent);
                kit.embedToken(mon);
                t = mon;
                continue;
            }
        }
    }

    public static com.pullenti.ner.ReferentToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (!(t instanceof com.pullenti.ner.NumberToken) && t.getLengthChar() != 1) 
            return null;
        com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
        if (nex == null || nex.exTyp != com.pullenti.ner.core.NumberExType.MONEY) {
            if ((t instanceof com.pullenti.ner.NumberToken) && (t.getNext() instanceof com.pullenti.ner.TextToken) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                if (t.getNext().isHiphen() || t.getNext().getMorph()._getClass().isPreposition()) {
                    com.pullenti.ner.core.NumberExToken res1 = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t.getNext().getNext());
                    if (res1 != null && res1.exTyp == com.pullenti.ner.core.NumberExType.MONEY) {
                        MoneyReferent res0 = new MoneyReferent();
                        if ((t.getNext().isHiphen() && res1.getRealValue() == 0 && res1.getEndToken().getNext() != null) && res1.getEndToken().getNext().isChar('(')) {
                            com.pullenti.ner.core.NumberExToken nex2 = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(res1.getEndToken().getNext().getNext());
                            if ((nex2 != null && com.pullenti.unisharp.Utils.stringsEq(nex2.exTypParam, res1.exTypParam) && nex2.getEndToken().getNext() != null) && nex2.getEndToken().getNext().isChar(')')) {
                                if (com.pullenti.unisharp.Utils.stringsEq(nex2.getValue(), ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue())) {
                                    res0.setCurrency(nex2.exTypParam);
                                    res0.addSlot(MoneyReferent.ATTR_VALUE, nex2.getValue(), true, 0);
                                    return new com.pullenti.ner.ReferentToken(res0, t, nex2.getEndToken().getNext(), null);
                                }
                                if (t.getPrevious() instanceof com.pullenti.ner.NumberToken) {
                                    if (com.pullenti.unisharp.Utils.stringsEq(nex2.getValue(), ((((Double)(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class)).getRealValue() * (1000.0))).toString()) + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue()))) {
                                        res0.setCurrency(nex2.exTypParam);
                                        res0.addSlot(MoneyReferent.ATTR_VALUE, nex2.getValue(), true, 0);
                                        return new com.pullenti.ner.ReferentToken(res0, t.getPrevious(), nex2.getEndToken().getNext(), null);
                                    }
                                    else if (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) {
                                        if (nex2.getRealValue() == (((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class)).getRealValue() * (1000000.0)) + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class)).getRealValue() * (1000.0)) + ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getRealValue()))) {
                                            res0.setCurrency(nex2.exTypParam);
                                            res0.addSlot(MoneyReferent.ATTR_VALUE, nex2.getValue(), true, 0);
                                            return new com.pullenti.ner.ReferentToken(res0, t.getPrevious().getPrevious(), nex2.getEndToken().getNext(), null);
                                        }
                                    }
                                }
                            }
                        }
                        res0.setCurrency(res1.exTypParam);
                        res0.addSlot(MoneyReferent.ATTR_VALUE, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), false, 0);
                        return new com.pullenti.ner.ReferentToken(res0, t, t, null);
                    }
                }
            }
            return null;
        }
        MoneyReferent res = new MoneyReferent();
        res.setCurrency(nex.exTypParam);
        String val = nex.getValue();
        if (val.indexOf('.') > 0) 
            val = val.substring(0, 0 + val.indexOf('.'));
        res.addSlot(MoneyReferent.ATTR_VALUE, val, true, 0);
        int re = (int)com.pullenti.unisharp.Utils.mathRound(((nex.getRealValue() - res.getValue())) * (100.0), 6);
        if (re != 0) 
            res.addSlot(MoneyReferent.ATTR_REST, ((Integer)re).toString(), true, 0);
        if (nex.getRealValue() != nex.altRealValue) {
            if (Math.floor(res.getValue()) != Math.floor(nex.altRealValue)) {
                val = com.pullenti.ner.core.NumberHelper.doubleToString(nex.altRealValue);
                if (val.indexOf('.') > 0) 
                    val = val.substring(0, 0 + val.indexOf('.'));
                res.addSlot(MoneyReferent.ATTR_ALTVALUE, val, true, 0);
            }
            re = (int)com.pullenti.unisharp.Utils.mathRound(((nex.altRealValue - ((double)((long)nex.altRealValue)))) * (100.0), 6);
            if (re != res.getRest() && re != 0) 
                res.addSlot(MoneyReferent.ATTR_ALTREST, ((Integer)re).toString(), true, 0);
        }
        if (nex.altRestMoney > 0) 
            res.addSlot(MoneyReferent.ATTR_ALTREST, ((Integer)nex.altRestMoney).toString(), true, 0);
        com.pullenti.ner.Token t1 = nex.getEndToken();
        if (t1.getNext() != null && t1.getNext().isChar('(')) {
            com.pullenti.ner.ReferentToken rt = tryParse(t1.getNext().getNext());
            if ((rt != null && rt.referent.canBeEquals(res, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT) && rt.getEndToken().getNext() != null) && rt.getEndToken().getNext().isChar(')')) 
                t1 = rt.getEndToken().getNext();
            else {
                rt = tryParse(t1.getNext());
                if (rt != null && rt.referent.canBeEquals(res, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                    t1 = rt.getEndToken();
            }
        }
        if (res.getAltValue() != null && res.getAltValue() > res.getValue()) {
            if (t.getWhitespacesBeforeCount() == 1 && (t.getPrevious() instanceof com.pullenti.ner.NumberToken)) {
                int delt = (int)((res.getAltValue() - res.getValue()));
                if ((((res.getValue() < 1000) && ((delt % 1000)) == 0)) || (((res.getValue() < 1000000) && ((delt % 1000000)) == 0))) {
                    t = t.getPrevious();
                    res.addSlot(MoneyReferent.ATTR_VALUE, res.getStringValue(MoneyReferent.ATTR_ALTVALUE), true, 0);
                    res.addSlot(MoneyReferent.ATTR_ALTVALUE, null, true, 0);
                }
            }
        }
        return new com.pullenti.ner.ReferentToken(res, t, t1, null);
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        return tryParse(begin);
    }

    private static boolean m_Inited;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.money.internal.MoneyMeta.initialize();
        com.pullenti.ner.ProcessorService.registerAnalyzer(new MoneyAnalyzer());
    }
    public MoneyAnalyzer() {
        super();
    }
}
