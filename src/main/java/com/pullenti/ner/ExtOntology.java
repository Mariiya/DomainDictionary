/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Внешняя "онтология". Содержит дополнительтную информацию для обработки (сущностей) - 
 * это список элементов, связанных с внешними сущностями. 
 * Подаётся необязательным параметром на вход методу Process() класса Processor.
 * Внешняя онтология
 */
public class ExtOntology {

    /**
     * Добавить элемент
     * @param extId произвольный объект
     * @param typeName имя типа сущности
     * @param _definition текстовое определение. Определение может содержать несколько 
     * отдельных фрагментов, которые разделяются точкой с запятой. 
     * Например, Министерство Обороны России; Минобороны
     * @return если null, то не получилось...
     */
    public ExtOntologyItem add(Object extId, String typeName, String _definition) {
        if (typeName == null || _definition == null) 
            return null;
        java.util.ArrayList<Referent> rs = this._createReferent(typeName, _definition);
        if (rs == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new3015(extId, rs.get(0), typeName);
        if (rs.size() > 1) {
            rs.remove(0);
            res.refs = rs;
        }
        items.add(res);
        return res;
    }

    /**
     * Добавить готовую сущность
     * @param extId произвольный объект
     * @param referent готовая сущность (например, сфомированная явно)
     * @return новая запись словаря
     */
    public ExtOntologyItem addReferent(Object extId, Referent referent) {
        if (referent == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new3015(extId, referent, referent.getTypeName());
        items.add(res);
        return res;
    }

    private java.util.ArrayList<Referent> _createReferent(String typeName, String _definition) {
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer3017 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres3018 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapanalyzer3017);
        analyzer = wrapanalyzer3017.value;
        if (!inoutres3018) 
            return null;
        SourceOfAnalysis sf = new SourceOfAnalysis(_definition);
        AnalysisResult ar = m_Processor._process(sf, true, true, null, null);
        if (ar == null || ar.firstToken == null) 
            return null;
        Referent r0 = ar.firstToken.getReferent();
        Token t = null;
        if (r0 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(r0.getTypeName(), typeName)) 
                r0 = null;
        }
        if (r0 != null) 
            t = ar.firstToken;
        else {
            ReferentToken rt = analyzer.processOntologyItem(ar.firstToken);
            if (rt == null) 
                return null;
            r0 = rt.referent;
            t = rt.getEndToken();
        }
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isChar(';') && t.getNext() != null) {
                Referent r1 = t.getNext().getReferent();
                if (r1 == null) {
                    ReferentToken rt = analyzer.processOntologyItem(t.getNext());
                    if (rt == null) 
                        continue;
                    t = rt.getEndToken();
                    r1 = rt.referent;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), typeName)) {
                    r0.mergeSlots(r1, true);
                    r1.tag = r0;
                }
            }
        }
        if (r0 == null) 
            return null;
        r0.tag = r0;
        r0 = analyzer.persistAnalizerData.registerReferent(r0);
        m_Processor._createRes(ar.firstToken.kit, ar, null, true);
        java.util.ArrayList<Referent> res = new java.util.ArrayList<Referent>();
        res.add(r0);
        for (Referent e : ar.getEntities()) {
            if (e.tag == null) 
                res.add(e);
        }
        return res;
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param _definition новое определение
     * @return признак успешности обновления
     */
    public boolean refresh(ExtOntologyItem item, String _definition) {
        if (item == null) 
            return false;
        java.util.ArrayList<Referent> rs = this._createReferent(item.typeName, _definition);
        if (rs == null) 
            return false;
        return this.refresh(item, rs.get(0));
    }

    /**
     * Обновить существующий элемент онтологии новой сущностью
     * @param item обновляемый элемент
     * @param newReferent сущность
     * @return признак успешности обновления
     */
    public boolean refresh(ExtOntologyItem item, Referent newReferent) {
        if (item == null) 
            return false;
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer3019 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres3020 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, item.typeName, wrapanalyzer3019);
        analyzer = wrapanalyzer3019.value;
        if (!inoutres3020) 
            return false;
        if (analyzer.persistAnalizerData == null) 
            return true;
        if (item.referent != null) 
            analyzer.persistAnalizerData.removeReferent(item.referent);
        Referent oldReferent = item.referent;
        newReferent = analyzer.persistAnalizerData.registerReferent(newReferent);
        item.referent = newReferent;
        m_Hash = null;
        if (oldReferent != null && newReferent != null) {
            for (Analyzer a : m_Processor.getAnalyzers()) {
                if (a.persistAnalizerData != null) {
                    for (Referent rr : a.persistAnalizerData.getReferents()) {
                        for (Slot s : newReferent.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                newReferent.uploadSlot(s, rr);
                        }
                        for (Slot s : rr.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                rr.uploadSlot(s, newReferent);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Список элементов внешней онтологии
     */
    public java.util.ArrayList<ExtOntologyItem> items = new java.util.ArrayList<ExtOntologyItem>();

    public ExtOntology(String specNames) {
        m_Specs = specNames;
        this._init();
    }

    private void _init() {
        m_Processor = ProcessorService.createSpecificProcessor(m_Specs);
        m_AnalByType = new java.util.HashMap<String, Analyzer>();
        for (Analyzer a : m_Processor.getAnalyzers()) {
            a.setPersistReferentsRegim(true);
            for (com.pullenti.ner.metadata.ReferentClass t : a.getTypeSystem()) {
                if (!m_AnalByType.containsKey(t.getName())) 
                    m_AnalByType.put(t.getName(), a);
            }
        }
    }

    private Processor m_Processor;

    private String m_Specs;

    private java.util.HashMap<String, Analyzer> m_AnalByType;

    /**
     * Сериализовать весь словарь в поток
     * @param stream поток для сериализации
     */
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Specs);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, items.size());
        for (ExtOntologyItem it : items) {
            it.serialize(stream);
        }
    }

    /**
     * Восстановить словарь из потока
     * @param stream поток для десериализации
     */
    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        m_Specs = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        this._init();
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (; cou > 0; cou--) {
            ExtOntologyItem it = new ExtOntologyItem(null);
            it.deserialize(stream);
            items.add(it);
        }
        this._initHash();
    }

    public com.pullenti.ner.core.AnalyzerData _getAnalyzerData(String typeName) {
        Analyzer a;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapa3021 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres3022 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapa3021);
        a = wrapa3021.value;
        if (!inoutres3022) 
            return null;
        return a.persistAnalizerData;
    }

    private java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection> m_Hash = null;

    private void _initHash() {
        m_Hash = new java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection>();
        for (ExtOntologyItem it : items) {
            if (it.referent != null) 
                it.referent.ontologyItems = null;
        }
        for (ExtOntologyItem it : items) {
            if (it.referent != null) {
                com.pullenti.ner.core.IntOntologyCollection ont;
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wrapont3024 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
                boolean inoutres3025 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, it.referent.getTypeName(), wrapont3024);
                ont = wrapont3024.value;
                if (!inoutres3025) 
                    m_Hash.put(it.referent.getTypeName(), (ont = com.pullenti.ner.core.IntOntologyCollection._new3023(true)));
                if (it.referent.ontologyItems == null) 
                    it.referent.ontologyItems = new java.util.ArrayList<ExtOntologyItem>();
                it.referent.ontologyItems.add(it);
                it.referent.intOntologyItem = null;
                ont.addReferent(it.referent);
            }
        }
    }

    /**
     * Привязать сущность к существующей записи
     * @param r внешняя сущность
     * @return null или список подходящих элементов
     */
    public java.util.ArrayList<ExtOntologyItem> attachReferent(Referent r) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto3026 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres3027 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, r.getTypeName(), wraponto3026);
        onto = wraponto3026.value;
        if (!inoutres3027) 
            return null;
        java.util.ArrayList<Referent> li = onto.tryAttachByReferent(r, null, false);
        if (li == null || li.size() == 0) 
            return null;
        java.util.ArrayList<ExtOntologyItem> res = null;
        for (Referent rr : li) {
            if (rr.ontologyItems != null) {
                if (res == null) 
                    res = new java.util.ArrayList<ExtOntologyItem>();
                com.pullenti.unisharp.Utils.addToArrayList(res, rr.ontologyItems);
            }
        }
        return res;
    }

    // Используется внутренним образом
    public java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> attachToken(String typeName, Token t) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto3028 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres3029 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, typeName, wraponto3028);
        onto = wraponto3028.value;
        if (!inoutres3029) 
            return null;
        return onto.tryAttach(t, null, false);
    }

    /**
     * Используется произвольным образом
     */
    public Object tag;
    public ExtOntology() {
        this(null);
    }
}
