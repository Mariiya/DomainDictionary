/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Лингвистический процессор
 * 
 * Процессор
 */
public class Processor implements AutoCloseable {

    public Processor() {
        if(_globalInstance == null) return;
        _ProgressChangedEventHandler_OnProgressHandler = new ProgressChangedEventHandler_OnProgressHandler(this);
        _CancelEventHandler_OnCancel = new CancelEventHandler_OnCancel(this);
    }

    /**
     * Добавить анализатор, если его ещё нет
     * @param a экземпляр анализатора
     */
    public void addAnalyzer(Analyzer a) {
        if (a == null || a.getName() == null || m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.put(a.getName(), a);
        m_Analyzers.add(a);
        a.progress.add(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.add(_CancelEventHandler_OnCancel);
    }

    /**
     * Удалить анализатор
     * @param a 
     */
    public void delAnalyzer(Analyzer a) {
        if (!m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.remove(a.getName());
        m_Analyzers.remove(a);
        a.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.remove(_CancelEventHandler_OnCancel);
    }

    @Override
    public void close() {
        for (Analyzer w : getAnalyzers()) {
            w.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
            w.cancel.remove(_CancelEventHandler_OnCancel);
        }
    }

    public java.util.Collection<Analyzer> getAnalyzers() {
        return m_Analyzers;
    }


    private java.util.ArrayList<Analyzer> m_Analyzers = new java.util.ArrayList<Analyzer>();

    private java.util.HashMap<String, Analyzer> m_AnalyzersHash = new java.util.HashMap<String, Analyzer>();

    /**
     * Найти анализатор по его имени
     * @param name 
     * @return 
     */
    public Analyzer findAnalyzer(String name) {
        Analyzer a;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapa3045 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres3046 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalyzersHash, (name != null ? name : ""), wrapa3045);
        a = wrapa3045.value;
        if (inoutres3046) 
            return a;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(name)) 
            return null;
        for (Analyzer aa : ProcessorService.getAnalyzers()) {
            if (com.pullenti.unisharp.Utils.stringsEq(aa.getName(), name)) {
                a = aa.clone();
                a.setIgnoreThisAnalyzer(true);
                m_Analyzers.add(a);
                m_AnalyzersHash.put(name, a);
                return a;
            }
        }
        return null;
    }

    /**
     * Обработать текст
     * @param text входной контейнер текста
     * @param extOntology внешняя онтология (null - не используется)
     * @param lang язык (если не задан, то будет определён автоматически)
     * @return аналитический контейнер с результатом
     * 
     */
    public AnalysisResult process(SourceOfAnalysis text, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        return this._process(text, false, false, extOntology, lang);
    }

    /**
     * Доделать результат, который был сделан другим процессором
     * @param ar то, что было сделано другим процессором
     */
    public void processNext(AnalysisResult ar) {
        if (ar == null) 
            return;
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new3047(this, ar.ontology);
        kit.initFrom(ar);
        this._process2(kit, ar, false);
        this._createRes(kit, ar, ar.ontology, false);
        ar.firstToken = kit.firstToken;
    }

    public AnalysisResult _process(SourceOfAnalysis text, boolean ontoRegine, boolean noLog, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        m_Breaked = false;
        this.prepareProgress();
        com.pullenti.unisharp.Stopwatch sw0 = com.pullenti.unisharp.Utils.startNewStopwatch();
        if (!noLog) 
            this.onProgressHandler(this, new com.pullenti.unisharp.ProgressEventArgs(0, "Морфологический анализ"));
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new3048(text, false, lang, _ProgressChangedEventHandler_OnProgressHandler, extOntology, this, ontoRegine);
        AnalysisResult ar = new AnalysisResult();
        sw0.stop();
        String msg;
        this.onProgressHandler(this, new com.pullenti.unisharp.ProgressEventArgs(100, "Морфологический анализ завершён"));
        int k = 0;
        for (Token t = kit.firstToken; t != null; t = t.getNext()) {
            k++;
        }
        if (!noLog) {
            msg = ("Из " + text.getText().length() + " символов текста выделено " + k + " термов за " + sw0.getElapsedMilliseconds() + " ms");
            if (!kit.baseLanguage.isUndefined()) 
                msg += (", базовый язык " + kit.baseLanguage.toString());
            this.onMessage(msg);
            ar.getLog().add(msg);
            if (text.crlfCorrectedCount > 0) 
                ar.getLog().add((((Integer)text.crlfCorrectedCount).toString() + " переходов на новую строку заменены на пробел"));
            if (kit.firstToken == null) 
                ar.getLog().add("Пустой текст");
        }
        sw0.start();
        if (kit.firstToken != null) 
            this._process2(kit, ar, noLog);
        if (!ontoRegine) 
            this._createRes(kit, ar, extOntology, noLog);
        sw0.stop();
        if (!noLog) {
            if (sw0.getElapsedMilliseconds() > (5000L)) {
                float f = (float)text.getText().length();
                f /= ((float)sw0.getElapsedMilliseconds());
                msg = ("Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds()) + " (" + f + " Kb/sec)");
            }
            else 
                msg = ("Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds()));
            this.onMessage(msg);
            ar.getLog().add(msg);
        }
        if (timeoutSeconds > 0) {
            if ((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now())).getSeconds() > timeoutSeconds) 
                ar.isTimeoutBreaked = true;
        }
        ar.setSofa(text);
        if (!ontoRegine) 
            com.pullenti.unisharp.Utils.addToArrayList(ar.getEntities(), kit.getEntities());
        ar.firstToken = kit.firstToken;
        ar.ontology = extOntology;
        ar.baseLanguage = kit.baseLanguage;
        ar.tag = kit;
        if (kit.msgs.size() > 0) 
            com.pullenti.unisharp.Utils.addToArrayList(ar.getLog(), kit.msgs);
        return ar;
    }

    private void _process2(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, boolean noLog) {
        String msg;
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        boolean stopByTimeout = false;
        java.util.ArrayList<Analyzer> anals = new java.util.ArrayList<Analyzer>(m_Analyzers);
        for (int ii = 0; ii < anals.size(); ii++) {
            Analyzer c = anals.get(ii);
            if (c.getIgnoreThisAnalyzer()) 
                continue;
            if (m_Breaked) {
                if (!noLog) {
                    msg = "Процесс прерван пользователем";
                    this.onMessage(msg);
                    ar.getLog().add(msg);
                }
                break;
            }
            if (timeoutSeconds > 0 && !stopByTimeout) {
                if ((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now())).getSeconds() > timeoutSeconds) {
                    m_Breaked = true;
                    if (!noLog) {
                        msg = "Процесс прерван по таймауту";
                        this.onMessage(msg);
                        ar.getLog().add(msg);
                    }
                    stopByTimeout = true;
                }
            }
            if (stopByTimeout) {
                if (com.pullenti.unisharp.Utils.stringsEq(c.getName(), "INSTRUMENT")) {
                }
                else 
                    continue;
            }
            if (!noLog) 
                this.onProgressHandler(c, new com.pullenti.unisharp.ProgressEventArgs(0, ("Работа \"" + c.getCaption() + "\"")));
            try {
                kit.m_AnalyzerStack.add(c.getName());
                sw.reset();
                sw.start();
                c.process(kit);
                sw.stop();
                kit.m_AnalyzerStack.remove(c.getName());
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (!noLog) {
                    msg = ("Анализатор \"" + c.getCaption() + "\" выделил " + (dat == null ? 0 : dat.getReferents().size()) + " объект(ов) за " + outSecs(sw.getElapsedMilliseconds()));
                    this.onMessage(msg);
                    ar.getLog().add(msg);
                }
            } catch (Exception ex) {
                if (kit.m_AnalyzerStack.contains(c.getName())) 
                    kit.m_AnalyzerStack.remove(c.getName());
                if (!noLog) {
                    ex = new Exception(("Ошибка в анализаторе \"" + c.getCaption() + "\" (" + ex.getMessage() + ")"), ex);
                    this.onMessage(ex);
                    ar.addException(ex);
                }
            }
        }
        if (!noLog) 
            this.onProgressHandler(null, new com.pullenti.unisharp.ProgressEventArgs(0, "Пересчёт отношений обобщения"));
        try {
            sw.reset();
            sw.start();
            com.pullenti.ner.core.internal.GeneralRelationHelper.refreshGenerals(this, kit);
            sw.stop();
            if (!noLog) {
                msg = ("Отношение обобщение пересчитано за " + outSecs(sw.getElapsedMilliseconds()));
                this.onMessage(msg);
                ar.getLog().add(msg);
            }
        } catch (Exception ex) {
            if (!noLog) {
                ex = new Exception("Ошибка пересчёта отношения обобщения", ex);
                this.onMessage(ex);
                ar.addException(ex);
            }
        }
    }

    public void _createRes(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, ExtOntology extOntology, boolean noLog) {
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        int ontoAttached = 0;
        for (int k = 0; k < 2; k++) {
            for (Analyzer c : getAnalyzers()) {
                if (k == 0) {
                    if (!c.isSpecific()) 
                        continue;
                }
                else if (c.isSpecific()) 
                    continue;
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (dat != null && dat.getReferents().size() > 0) {
                    if (extOntology != null) {
                        for (Referent r : dat.getReferents()) {
                            if (r.ontologyItems == null) {
                                if ((((r.ontologyItems = extOntology.attachReferent(r)))) != null) 
                                    ontoAttached++;
                            }
                        }
                    }
                    com.pullenti.unisharp.Utils.addToArrayList(ar.getEntities(), dat.getReferents());
                }
            }
        }
        for (Token t = kit.firstToken; t != null; t = t.getNext()) {
            this._clearTags(t, 0);
        }
        sw.stop();
        if (extOntology != null && !noLog) {
            String msg = ("Привязано " + ontoAttached + " объектов к внешней отнологии (" + extOntology.items.size() + " элементов) за " + outSecs(sw.getElapsedMilliseconds()));
            this.onMessage(msg);
            ar.getLog().add(msg);
        }
    }

    private void _clearTags(Token t, int lev) {
        if (lev > 10) 
            return;
        t.tag = null;
        if (t instanceof MetaToken) {
            for (Token tt = ((MetaToken)com.pullenti.unisharp.Utils.cast(t, MetaToken.class)).getBeginToken(); tt != null && tt.getEndChar() <= t.getEndChar(); tt = tt.getNext()) {
                this._clearTags(tt, lev + 1);
            }
        }
    }

    private static String outSecs(long ms) {
        if (ms < (4000L)) 
            return (((Long)ms).toString() + "ms");
        ms /= (1000L);
        if (ms < (120L)) 
            return (((Long)ms).toString() + "sec");
        return ((((Long)(ms / (60L))).toString()) + "min " + (ms % (60L)) + "sec");
    }

    /**
     * Событие обработки строки состояния процесса. 
     * Там-же в событии ProgressChangedEventArg в UserState выводятся информационные сообщения. 
     * Внимание, если ProgressPercentage &lt; 0, то учитывать только информационное сообщение в UserState.
     */
    public java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler>();

    private java.util.HashMap<Object, com.pullenti.ner.core.internal.ProgressPeace> m_ProgressPeaces = new java.util.HashMap<Object, com.pullenti.ner.core.internal.ProgressPeace>();

    private Object m_ProgressPeacesLock = new Object();

    /**
     * Прервать процесс анализа
     */
    public void breakProcess() {
        m_Breaked = true;
    }

    private boolean m_Breaked = false;

    /**
     * Максимальное время обработки, прервёт при превышении. 
     * По умолчанию (0) - неограничено.
     */
    public int timeoutSeconds = 0;

    private static final int morphCoef = 10;

    private void prepareProgress() {
        synchronized (m_ProgressPeacesLock) {
            lastPercent = -1;
            int co = morphCoef;
            int total = co;
            for (Analyzer wf : getAnalyzers()) {
                if (!wf.getIgnoreThisAnalyzer()) 
                    total += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
            }
            m_ProgressPeaces.clear();
            float max = (float)(co * 100);
            max /= ((float)total);
            m_ProgressPeaces.put(this, com.pullenti.ner.core.internal.ProgressPeace._new3049(0.0F, max));
            for (Analyzer wf : getAnalyzers()) {
                if (!wf.getIgnoreThisAnalyzer()) {
                    float min = max;
                    co += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
                    max = (float)(co * 100);
                    max /= ((float)total);
                    if (!m_ProgressPeaces.containsKey(wf)) 
                        m_ProgressPeaces.put(wf, com.pullenti.ner.core.internal.ProgressPeace._new3049(min, max));
                }
            }
        }
    }

    private void onProgressHandler(Object sender, com.pullenti.unisharp.ProgressEventArgs e) {
        if (progress.size() == 0) 
            return;
        if (e.getProgressPercentage() >= 0) {
            com.pullenti.ner.core.internal.ProgressPeace pi;
            synchronized (m_ProgressPeacesLock) {
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.internal.ProgressPeace> wrappi3051 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.internal.ProgressPeace>();
                boolean inoutres3052 = com.pullenti.unisharp.Utils.tryGetValue(m_ProgressPeaces, (sender != null ? sender : this), wrappi3051);
                pi = wrappi3051.value;
                if (inoutres3052) {
                    float p = (((float)e.getProgressPercentage()) * ((pi.max - pi.min))) / (100.0F);
                    p += pi.min;
                    int pers = (int)p;
                    if (pers == lastPercent && e.getUserState() == null && !m_Breaked) 
                        return;
                    e = new com.pullenti.unisharp.ProgressEventArgs((int)p, e.getUserState());
                    lastPercent = pers;
                }
            }
        }
        for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, e);
    }

    private void onCancel(Object sender, com.pullenti.unisharp.CancelEventArgs e) {
        if (timeoutSeconds > 0) {
            if (sender instanceof com.pullenti.ner.core.AnalysisKit) {
                if ((java.time.Duration.between(((com.pullenti.ner.core.AnalysisKit)com.pullenti.unisharp.Utils.cast(sender, com.pullenti.ner.core.AnalysisKit.class)).startDate, java.time.LocalDateTime.now())).getSeconds() > timeoutSeconds) 
                    m_Breaked = true;
            }
        }
        e.cancel = m_Breaked;
    }

    private void onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.unisharp.ProgressEventArgs(-1, message));
    }

    private int lastPercent;

    /**
     * Используется произвольным образом
     */
    public Object tag;

    public static class ProgressChangedEventHandler_OnProgressHandler implements com.pullenti.unisharp.ProgressEventHandler {
    
        private Processor m_Source;
    
        private ProgressChangedEventHandler_OnProgressHandler(Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.unisharp.ProgressEventArgs e) {
            m_Source.onProgressHandler(sender, e);
        }
        public ProgressChangedEventHandler_OnProgressHandler() {
        }
    }


    private ProgressChangedEventHandler_OnProgressHandler _ProgressChangedEventHandler_OnProgressHandler;

    public static class CancelEventHandler_OnCancel implements com.pullenti.unisharp.CancelEventHandler {
    
        private Processor m_Source;
    
        private CancelEventHandler_OnCancel(Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.unisharp.CancelEventArgs e) {
            m_Source.onCancel(sender, e);
        }
        public CancelEventHandler_OnCancel() {
        }
    }


    private CancelEventHandler_OnCancel _CancelEventHandler_OnCancel;
    public static Processor _globalInstance;
    
    static {
        try { _globalInstance = new Processor(); } 
        catch(Exception e) { }
    }
}
