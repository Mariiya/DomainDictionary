/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods;

/**
 * Анализатор названий товаров (номенклатур) и выделение из них характеристик. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора. 
 * Выделение происходит из небольшого фрагмента текста, содержащего только один товар и его характеристики. 
 * Выделять из большого текста такие фрагменты - это не задача анализатора. 
 * Примеры текстов: "Плед OXFORD Cashnere Touch Herringbone 1.5-сп с эффектом кашемира", 
 * "Имплантат размером 5 см х 5 см предназначен для реконструкции твердой мозговой оболочки. Изготовлен из биологически совместимого материала на коллагеновой основе.".
 */
public class GoodsAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("GOODS")
     */
    public static final String ANALYZER_NAME = "GOODS";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Товары и атрибуты";
    }


    @Override
    public String getDescription() {
        return "Товары и их атрибуты";
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new GoodsAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.goods.internal.AttrMeta.GLOBALMETA, com.pullenti.ner.goods.internal.GoodMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.goods.internal.AttrMeta.ATTRIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("bullet_ball_glass_grey.png"));
        res.put(com.pullenti.ner.goods.internal.GoodMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("shoppingcart.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, GoodAttributeReferent.OBJ_TYPENAME)) 
            return new GoodAttributeReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, GoodReferent.OBJ_TYPENAME)) 
            return new GoodReferent();
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
        java.util.ArrayList<GoodReferent> _goods = new java.util.ArrayList<GoodReferent>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (!t.isNewlineBefore()) 
                continue;
            if (t.getBeginChar() > nextPos) {
                nextPos += delta;
                cur++;
                if (!this.onProgress(cur, parts, kit)) 
                    break;
            }
            if (!t.chars.isLetter() && t.getNext() != null) 
                t = t.getNext();
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = com.pullenti.ner.goods.internal.GoodAttrToken.tryParseList(t);
            if (rts == null || rts.size() == 0) 
                continue;
            GoodReferent good = new GoodReferent();
            for (com.pullenti.ner.ReferentToken rt : rts) {
                rt.referent = ad.registerReferent(rt.referent);
                if (good.findSlot(GoodReferent.ATTR_ATTR, rt.referent, true) == null) 
                    good.addSlot(GoodReferent.ATTR_ATTR, rt.referent, false, 0);
                kit.embedToken(rt);
            }
            _goods.add(good);
            com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(good, rts.get(0), rts.get(rts.size() - 1), null);
            kit.embedToken(rt0);
            t = rt0;
        }
        for (GoodReferent g : _goods) {
            ad.getReferents().add(g);
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (begin == null) 
            return null;
        GoodAttributeReferent ga = new GoodAttributeReferent();
        if (begin.chars.isLatinLetter()) {
            if (begin.isValue("KEYWORD", null)) {
                ga.setTyp(GoodAttrType.KEYWORD);
                begin = begin.getNext();
            }
            else if (begin.isValue("CHARACTER", null)) {
                ga.setTyp(GoodAttrType.CHARACTER);
                begin = begin.getNext();
            }
            else if (begin.isValue("PROPER", null)) {
                ga.setTyp(GoodAttrType.PROPER);
                begin = begin.getNext();
            }
            else if (begin.isValue("MODEL", null)) {
                ga.setTyp(GoodAttrType.MODEL);
                begin = begin.getNext();
            }
            if (begin == null) 
                return null;
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(ga, begin, begin, null);
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            if (t.isChar(';')) {
                ga.addSlot(GoodAttributeReferent.ATTR_VALUE, com.pullenti.ner.core.MiscHelper.getTextValue(begin, t.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO), false, 0);
                begin = t.getNext();
                continue;
            }
            res.setEndToken(t);
        }
        if (res.getEndChar() > begin.getBeginChar()) 
            ga.addSlot(GoodAttributeReferent.ATTR_VALUE, com.pullenti.ner.core.MiscHelper.getTextValue(begin, res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), false, 0);
        if (ga.getTyp() == GoodAttrType.UNDEFINED) {
            if (!begin.chars.isAllLower()) 
                ga.setTyp(GoodAttrType.PROPER);
        }
        return res;
    }

    private static boolean m_Initialized = false;

    private static Object m_Lock;

    public static void initialize() throws Exception {
        synchronized (m_Lock) {
            if (m_Initialized) 
                return;
            m_Initialized = true;
            com.pullenti.ner.goods.internal.AttrMeta.initialize();
            com.pullenti.ner.goods.internal.GoodMeta.initialize();
            try {
                com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
                com.pullenti.ner.goods.internal.GoodAttrToken.initialize();
                com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
            } catch (Exception ex) {
                throw new Exception(ex.getMessage(), ex);
            }
            com.pullenti.ner.ProcessorService.registerAnalyzer(new GoodsAnalyzer());
        }
    }

    public GoodsAnalyzer() {
        super();
    }
    
    static {
        m_Lock = new Object();
    }
}
