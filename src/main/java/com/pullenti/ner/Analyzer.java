/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех лингвистических анализаторов. Игнорируйте, если не собираетесь делать свой анализатор.
 * Анализатор процессора
 */
public abstract class Analyzer {

    /**
     * Запустить анализ
     * @param kit контейнер с данными
     */
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
    }

    public String getName() {
        return null;
    }


    public String getCaption() {
        return null;
    }


    public String getDescription() {
        return null;
    }


    @Override
    public String toString() {
        return (this.getCaption() + " (" + this.getName() + ")");
    }

    public Analyzer clone() {
        return null;
    }

    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return new java.util.ArrayList<com.pullenti.ner.metadata.ReferentClass>();
    }


    public java.util.HashMap<String, byte[]> getImages() {
        return null;
    }


    public boolean isSpecific() {
        return false;
    }


    /**
     * Создать сущность указанного типа
     * @param type тип сущности
     * @return экземпляр
     */
    public Referent createReferent(String type) {
        return null;
    }

    private static java.util.ArrayList<String> emptyList;

    public Iterable<String> getUsedExternObjectTypes() {
        return emptyList;
    }


    public int getProgressWeight() {
        return 0;
    }


    public java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler>();

    public java.util.ArrayList<com.pullenti.unisharp.CancelEventHandler> cancel = new java.util.ArrayList<com.pullenti.unisharp.CancelEventHandler>();

    protected boolean onProgress(int pos, int max, com.pullenti.ner.core.AnalysisKit kit) {
        boolean ret = true;
        if (progress.size() > 0) {
            if (pos >= 0 && pos <= max && max > 0) {
                int percent = pos;
                if (max > 1000000) 
                    percent /= ((max / 1000));
                else 
                    percent = ((100 * percent)) / max;
                if (percent != lastPercent) {
                    com.pullenti.unisharp.ProgressEventArgs arg = new com.pullenti.unisharp.ProgressEventArgs(percent, null);
                    for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, arg);
                    if (cancel.size() > 0) {
                        com.pullenti.unisharp.CancelEventArgs cea = new com.pullenti.unisharp.CancelEventArgs();
                        for(int iiid = 0; iiid < cancel.size(); iiid++) cancel.get(iiid).call(kit, cea);
                        ret = !cea.cancel;
                    }
                }
                lastPercent = percent;
            }
        }
        return ret;
    }

    private int lastPercent;

    protected boolean onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.unisharp.ProgressEventArgs(-1, message));
        return true;
    }

    private boolean _persistreferentsregim;

    public boolean getPersistReferentsRegim() {
        return _persistreferentsregim;
    }

    public boolean setPersistReferentsRegim(boolean value) {
        _persistreferentsregim = value;
        return _persistreferentsregim;
    }


    private boolean _ignorethisanalyzer;

    public boolean getIgnoreThisAnalyzer() {
        return _ignorethisanalyzer;
    }

    public boolean setIgnoreThisAnalyzer(boolean value) {
        _ignorethisanalyzer = value;
        return _ignorethisanalyzer;
    }


    public com.pullenti.ner.core.AnalyzerData persistAnalizerData;

    /**
     * Используется внутренним образом
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerData();
    }

    /**
     * Попытаться выделить сущность с указанного токена. 
     * (выделенная сущность не сохраняется в локальной онтологии - её нужно потом явно сохранять)
     * @param t токен
     * @param param параметр, поддерживаемый анализатором (если есть)
     * @return результат
     */
    public ReferentToken processReferent(Token t, String param) {
        return null;
    }

    /**
     * Это используется внутренним образом для обработки внешних онтологий
     * @param begin 
     * @return 
     */
    public ReferentToken processOntologyItem(Token begin) {
        return null;
    }

    public Analyzer() {
    }
    
    static {
        emptyList = new java.util.ArrayList<String>();
    }
}
