/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Служба лингвистических процессоров
 * 
 * Служба процессоров
 */
public class ProcessorService {

    public static String getVersion() {
        return "4.12";
    }


    public static String getVersionDate() {
        return "2022.03.23";
    }


    /**
     * Инициализация сервиса. Каждый анализатор нужно аинициализировать отдельно. 
     * Если вызывается Sdk.Initialize(), то там инициализация сервиса и всех анализаторов делается.
     * @param lang необходимые языки (по умолчанию, русский и английский)
     * 
     */
    public static void initialize(com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.morph.MorphologyService.initialize(lang);
        com.pullenti.semantic.utils.DerivateService.initialize(lang);
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        com.pullenti.ner.core.PrepositionHelper.initialize();
        com.pullenti.ner.core.ConjunctionHelper.initialize();
        com.pullenti.ner.core.internal.NounPhraseItem.initialize();
        com.pullenti.ner.core.NumberHelper.initialize();
        com.pullenti.ner.core.internal.NumberExHelper.initialize();
        com.pullenti.ner.core.internal.BlockLine.initialize();
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
    }

    private static boolean m_Inited;

    public static boolean isInitialized() {
        return m_Inited;
    }


    /**
     * Создать процессор со стандартным списком анализаторов (у которых свойство IsSpecific = false)
     * @return экземпляр процессора
     * 
     */
    public static Processor createProcessor() {
        if (!m_Inited) 
            return null;
        Processor proc = new Processor();
        for (Analyzer t : m_AnalizerInstances) {
            Analyzer a = t.clone();
            if (a != null && !a.isSpecific()) 
                proc.addAnalyzer(a);
        }
        return proc;
    }

    /**
     * Создать процессор с набором стандартных и указанных параметром специфических 
     * анализаторов.
     * @param specAnalyzerNames можно несколько, разделённые запятой или точкой с запятой. 
     * Если список пустой, то эквивалентно CreateProcessor()
     * @return Экземпляр процессора
     * 
     */
    public static Processor createSpecificProcessor(String specAnalyzerNames) {
        if (!m_Inited) 
            return null;
        Processor proc = new Processor();
        java.util.ArrayList<String> names = new java.util.ArrayList<String>(java.util.Arrays.asList(com.pullenti.unisharp.Utils.split(((specAnalyzerNames != null ? specAnalyzerNames : "")), String.valueOf(',') + String.valueOf(';') + String.valueOf(' '), false)));
        for (Analyzer t : m_AnalizerInstances) {
            Analyzer a = t.clone();
            if (a != null) {
                if (!a.isSpecific() || names.contains(a.getName())) 
                    proc.addAnalyzer(a);
            }
        }
        return proc;
    }

    /**
     * Создать экземпляр процессора с пустым списком анализаторов
     * @return Процессор без выделения сущностей
     * 
     */
    public static Processor createEmptyProcessor() {
        return new Processor();
    }

    // Регистрация анализатора. Вызывается при инициализации из инициализируемой сборки
    // (она сама знает, какие содержит анализаторы, и регистрирует их)
    public static void registerAnalyzer(Analyzer analyzer) {
        try {
            m_AnalizerInstances.add(analyzer);
            java.util.HashMap<String, byte[]> img = analyzer.getImages();
            if (img != null) {
                for (java.util.Map.Entry<String, byte[]> kp : img.entrySet()) {
                    if (!m_Images.containsKey(kp.getKey())) 
                        m_Images.put(kp.getKey(), com.pullenti.ner.metadata.ImageWrapper._new3053(kp.getKey(), kp.getValue()));
                }
            }
        } catch (Exception ex) {
        }
        _reorderCartridges();
    }

    private static java.util.ArrayList<Analyzer> m_AnalizerInstances;

    private static void _reorderCartridges() {
        if (m_AnalizerInstances.size() == 0) 
            return;
        for (int k = 0; k < m_AnalizerInstances.size(); k++) {
            for (int i = 0; i < (m_AnalizerInstances.size() - 1); i++) {
                int maxInd = -1;
                Iterable<String> li = m_AnalizerInstances.get(i).getUsedExternObjectTypes();
                if (li != null) {
                    for (String v : m_AnalizerInstances.get(i).getUsedExternObjectTypes()) {
                        for (int j = i + 1; j < m_AnalizerInstances.size(); j++) {
                            if (m_AnalizerInstances.get(j).getTypeSystem() != null) {
                                for (com.pullenti.ner.metadata.ReferentClass st : m_AnalizerInstances.get(j).getTypeSystem()) {
                                    if (com.pullenti.unisharp.Utils.stringsEq(st.getName(), v)) {
                                        if ((maxInd < 0) || (maxInd < j)) 
                                            maxInd = j;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (maxInd <= i) {
                    if (m_AnalizerInstances.get(i).isSpecific() && !m_AnalizerInstances.get(i + 1).isSpecific()) {
                    }
                    else 
                        continue;
                }
                Analyzer cart = m_AnalizerInstances.get(i);
                m_AnalizerInstances.remove(i);
                m_AnalizerInstances.add(cart);
            }
        }
    }

    public static java.util.Collection<Analyzer> getAnalyzers() {
        return m_AnalizerInstances;
    }


    /**
     * Создать экземпляр объекта заданного типа
     * @param typeName имя типа
     * @return результат
     */
    public static Referent createReferent(String typeName) {
        for (Analyzer cart : m_AnalizerInstances) {
            Referent obj = cart.createReferent(typeName);
            if (obj != null) 
                return obj;
        }
        return new Referent(typeName);
    }

    private static java.util.HashMap<String, com.pullenti.ner.metadata.ImageWrapper> m_Images;

    private static com.pullenti.ner.metadata.ImageWrapper m_UnknownImage;

    /**
     * Получить иконку по идентификатору иконки
     * @param imageId идентификатор иконки
     * @return обёртка над телом иконки
     */
    public static com.pullenti.ner.metadata.ImageWrapper getImageById(String imageId) {
        if (imageId != null) {
            com.pullenti.ner.metadata.ImageWrapper res;
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.metadata.ImageWrapper> wrapres3054 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.metadata.ImageWrapper>();
            boolean inoutres3055 = com.pullenti.unisharp.Utils.tryGetValue(m_Images, imageId, wrapres3054);
            res = wrapres3054.value;
            if (inoutres3055) 
                return res;
        }
        if (m_UnknownImage == null) 
            m_UnknownImage = com.pullenti.ner.metadata.ImageWrapper._new3053("unknown", com.pullenti.ner.core.internal.ResourceHelper.getBytes("unknown.png"));
        return m_UnknownImage;
    }

    /**
     * Добавить специфическую иконку
     * @param imageId идентификатор (возвращаемый Referent.GetImageId())
     * @param content содержимое иконки
     */
    public static void addImage(String imageId, byte[] content) {
        if (imageId == null) 
            return;
        com.pullenti.ner.metadata.ImageWrapper wr = com.pullenti.ner.metadata.ImageWrapper._new3053(imageId, content);
        if (m_Images.containsKey(imageId)) 
            m_Images.put(imageId, wr);
        else 
            m_Images.put(imageId, wr);
    }

    private static Processor m_EmptyProcessor;

    public static Processor getEmptyProcessor() {
        if (m_EmptyProcessor == null) 
            m_EmptyProcessor = createEmptyProcessor();
        return m_EmptyProcessor;
    }


    private static Processor m_StandardProcessor;

    public static Processor getStandardProcessor() {
        if (m_StandardProcessor == null) 
            m_StandardProcessor = createProcessor();
        return m_StandardProcessor;
    }


    /**
     * Это нужно для автотестов, чтобы фиксировать дату-время, относительно которой 
     * идут вычисления (если не задана, то берётся текущая)
     */
    public static java.time.LocalDateTime DEBUGCURRENTDATETIME;

    public ProcessorService() {
    }
    
    static {
        m_AnalizerInstances = new java.util.ArrayList<Analyzer>();
        m_Images = new java.util.HashMap<String, com.pullenti.ner.metadata.ImageWrapper>();
    }
}
