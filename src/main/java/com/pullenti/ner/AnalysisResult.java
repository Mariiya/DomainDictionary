/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Результат анализа
 * 
 */
public class AnalysisResult {

    public SourceOfAnalysis getSofa() {
        return m_Sofa;
    }

    public SourceOfAnalysis setSofa(SourceOfAnalysis value) {
        m_Sofa = value;
        return value;
    }


    private SourceOfAnalysis m_Sofa;

    public java.util.ArrayList<Referent> getEntities() {
        return m_Entities;
    }


    private java.util.ArrayList<Referent> m_Entities = new java.util.ArrayList<Referent>();

    /**
     * Ссылка на первый токен текста, который был проанализирован последним
     */
    public Token firstToken;

    /**
     * Используемая внешняя онтология
     */
    public ExtOntology ontology;

    /**
     * Базовый язык
     */
    public com.pullenti.morph.MorphLang baseLanguage;

    public java.util.ArrayList<String> getLog() {
        return m_Log;
    }


    private java.util.ArrayList<String> m_Log = new java.util.ArrayList<String>();

    /**
     * Возникшие исключения (одинаковые исключаются)
     */
    public java.util.ArrayList<Exception> exceptions = new java.util.ArrayList<Exception>();

    public void addException(Exception ex) {
        String str = ex.toString();
        for (Exception e : exceptions) {
            if (com.pullenti.unisharp.Utils.stringsEq(e.toString(), str)) 
                return;
        }
        exceptions.add(ex);
    }

    /**
     * Процесс был прерван по таймауту (если был задан)
     */
    public boolean isTimeoutBreaked = false;

    public Object tag;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Общая длина ").append(this.getSofa().getText().length()).append(" знаков");
        if (baseLanguage != null) 
            res.append(", базовый язык ").append(baseLanguage.toString());
        res.append(", найдено ").append(this.getEntities().size()).append(" сущностей");
        if (isTimeoutBreaked) 
            res.append(", прервано по таймауту");
        return res.toString();
    }

    public void serialize(com.pullenti.unisharp.Stream stream) {
    }
    public AnalysisResult() {
    }
}
