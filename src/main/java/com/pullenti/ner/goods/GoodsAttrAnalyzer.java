/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods;

/**
 * Анализатор характеристик товаров. Используется, если нужно выделятть только отдельные характеристики, а не товар в целом. 
 * Если примеряется GoodsAnalyzer, то данный анализатор задействовать не нужно. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора. 
 * Выделение происходит из небольшого фрагмента текста, содержащего только характеристики. 
 * Выделять из большого текста такие фрагменты - это не задача анализатора.
 */
public class GoodsAttrAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("GOODSATTR")
     */
    public static final String ANALYZER_NAME = "GOODSATTR";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Атрибуты товара";
    }


    @Override
    public String getDescription() {
        return "Выделяет только атрибуты (из раздела Характеристик)";
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new GoodsAttrAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.goods.internal.AttrMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.goods.internal.AttrMeta.ATTRIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("bullet_ball_glass_grey.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, GoodAttributeReferent.OBJ_TYPENAME)) 
            return new GoodAttributeReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 100;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        int delta = 100000;
        int parts = (((kit.getSofa().getText().length() + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        int cur = 0;
        int nextPos = 0;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.getBeginChar() > nextPos) {
                nextPos += delta;
                cur++;
                if (!this.onProgress(cur, parts, kit)) 
                    break;
            }
            com.pullenti.ner.goods.internal.GoodAttrToken at = com.pullenti.ner.goods.internal.GoodAttrToken.tryParse(t, null, true, true);
            if (at == null) 
                continue;
            GoodAttributeReferent attr = at._createAttr();
            if (attr == null) {
                t = at.getEndToken();
                continue;
            }
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(attr, at.getBeginToken(), at.getEndToken(), null);
            rt.referent = ad.registerReferent(attr);
            kit.embedToken(rt);
            t = rt;
        }
    }

    private static boolean m_Initialized = false;

    private static Object m_Lock;

    public static void initialize() {
        synchronized (m_Lock) {
            if (m_Initialized) 
                return;
            m_Initialized = true;
            com.pullenti.ner.ProcessorService.registerAnalyzer(new GoodsAttrAnalyzer());
        }
    }

    public GoodsAttrAnalyzer() {
        super();
    }
    
    static {
        m_Lock = new Object();
    }
}
