/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Сервис семантического анализа. Основная концепция изложена в документе Pullenti.Semantic. 
 * В реальных проектах не использовался, слабо отлажен, но ведется доработка данной функциональности.
 * 
 * Сервис семантики
 */
public class SemanticService {

    /**
     * Версия семантики
     */
    public static String VERSION = "0.2";

    /**
     * Если собираетесь использовать семантику, то необходимо вызывать в самом начале и только один раз 
     * (после инициализации ProcessorService.Initialize). Если вызвали Sdk.Initialize(), то там семантика инициализуется, 
     * и эту функцию вызывать уже не надо. 
     * Отметим, что для NER семантический анализ не используется.
     */
    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.semantic.internal.DelimToken.initialize();
        com.pullenti.semantic.internal.AdverbToken.initialize();
        com.pullenti.ner.measure.MeasureAnalyzer.initialize();
        com.pullenti.ner.money.MoneyAnalyzer.initialize();
    }

    private static boolean m_Inited;

    /**
     * Сделать семантический анализ поверх результатов морфологического анализа и NER
     * @param ar результат обработки Processor
     * @param pars дополнительные параметры (null по умолчанию)
     * @return результат анализа текста
     * 
     */
    public static SemDocument process(com.pullenti.ner.AnalysisResult ar, SemProcessParams pars) {
        return com.pullenti.semantic.internal.AnalyzeHelper.process(ar, (pars != null ? pars : new SemProcessParams()));
    }

    public static com.pullenti.semantic.internal.AlgoParams PARAMS;

    public SemanticService() {
    }
    
    static {
        PARAMS = new com.pullenti.semantic.internal.AlgoParams();
    }
}
