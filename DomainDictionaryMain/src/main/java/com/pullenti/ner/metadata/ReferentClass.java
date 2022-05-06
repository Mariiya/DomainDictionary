/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.metadata;

/**
 * Описатель класса сущностей
 */
public class ReferentClass {

    public String getName() {
        return "?";
    }


    public String getCaption() {
        return null;
    }


    /**
     * Не выводить на графе объектов
     */
    public boolean hideInGraph = false;

    @Override
    public String toString() {
        return (String)com.pullenti.unisharp.Utils.notnull(getCaption(), getName());
    }

    public java.util.ArrayList<Feature> getFeatures() {
        return m_Features;
    }


    private java.util.ArrayList<Feature> m_Features = new java.util.ArrayList<Feature>();

    /**
     * Добавить атрибут
     * @param attrName 
     * @param attrCaption 
     * @param lowBound 
     * @param upBound 
     * @return 
     */
    public Feature addFeature(String attrName, String attrCaption, int lowBound, int upBound) {
        Feature res = Feature._new1909(attrName, attrCaption, lowBound, upBound);
        int ind = m_Features.size();
        m_Features.add(res);
        if (!m_Hash.containsKey(attrName)) 
            m_Hash.put(attrName, ind);
        else 
            m_Hash.put(attrName, ind);
        return m_Features.get(ind);
    }

    /**
     * Найти атрибут по его системному имени
     * @param _name 
     * @return 
     */
    public Feature findFeature(String _name) {
        int ind;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapind1910 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1911 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, _name, wrapind1910);
        ind = (wrapind1910.value != null ? wrapind1910.value : 0);
        if (!inoutres1911) 
            return null;
        else 
            return m_Features.get(ind);
    }

    private java.util.HashMap<String, Integer> m_Hash = new java.util.HashMap<String, Integer>();

    /**
     * Вычислить картинку
     * @param obj если null, то общая картинка для типа
     * @return идентификатор картинки, саму картинку можно будет получить через ProcessorService.GetImageById
     */
    public String getImageId(com.pullenti.ner.Referent obj) {
        return null;
    }
    public ReferentClass() {
    }
}
