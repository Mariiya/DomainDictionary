/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Сервис для получение информации о словах. Однокоренные слова объединены в так называемые дериватные группы. 
 * В настоящий момент поддержаны русский и украинский языки.
 * 
 * Сервис дериватных групп
 */
public class DerivateService {

    /**
     * Инициализация внутренних словарей. 
     * Можно не вызывать, но тогда будет автоматически вызвано при первом обращении, 
     * и соответственно первое обращение отработает на несколько секунд дольше. 
     * Если инициализация идёт через Sdk.Initialize или ProcessorService.Initialize, то эту функцию вызывать не надо.
     * @param langs по умолчанию, русский с украинским
     */
    public static void initialize(com.pullenti.morph.MorphLang langs) throws Exception, java.io.IOException {
        if (langs == null || langs.isUndefined()) 
            langs = com.pullenti.morph.MorphLang.RU;
        ControlModelQuestion.initialize();
        loadLanguages(langs);
    }

    private static com.pullenti.semantic.internal.DerivateDictionary m_DerRu;

    public static com.pullenti.morph.MorphLang getLoadedLanguages() {
        if (m_DerRu.m_AllGroups.size() > 0) 
            return com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.RU, com.pullenti.morph.MorphLang.UA);
        return com.pullenti.morph.MorphLang.UNKNOWN;
    }


    public static void loadLanguages(com.pullenti.morph.MorphLang langs) throws Exception, java.io.IOException {
        if (langs.isRu() || langs.isUa()) {
            if (!m_DerRu.init(com.pullenti.morph.MorphLang.RU, true)) 
                throw new Exception("Not found resource file e_ru.dat in Enplanatory");
        }
        if (langs.isUa()) {
        }
    }

    public static void loadDictionaryRu(byte[] dat) throws Exception, java.io.IOException {
        m_DerRu.load(dat);
    }

    public static void unloadLanguages(com.pullenti.morph.MorphLang langs) {
        if (langs.isRu() || langs.isUa()) {
            if (langs.isRu() && langs.isUa()) 
                m_DerRu.unload();
        }
        System.gc();
    }

    /**
     * Найти для слова дериватные группы DerivateGroup, в которые входит это слово 
     * (групп может быть несколько, но в большинстве случаев - одна)
     * @param word слово в верхнем регистре и нормальной форме
     * @param tryVariants пытаться ли для неизвестных слов делать варианты
     * @param lang язык (по умолчанию, русский)
     * @return список дериватных групп DerivateGroup
     */
    public static java.util.ArrayList<DerivateGroup> findDerivates(String word, boolean tryVariants, com.pullenti.morph.MorphLang lang) {
        return m_DerRu.find(word, tryVariants, lang);
    }

    /**
     * Найти для слова его толковую информацию (среди дериватных групп)
     * @param word слово в верхнем регистре и нормальной форме
     * @param lang возможный язык
     * @return список слов DerivateWord
     */
    public static java.util.ArrayList<DerivateWord> findWords(String word, com.pullenti.morph.MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return null;
        java.util.ArrayList<DerivateWord> res = null;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (res == null) 
                        res = new java.util.ArrayList<DerivateWord>();
                    res.add(w);
                }
            }
        }
        return res;
    }

    /**
     * Получить слова однокоренное слово заданной части речи. 
     * Например, для существительного "ГЛАГОЛ" вариант прилагательного: "ГЛАГОЛЬНЫЙ"
     * @param word слово в верхнем регистре и нормальной форме
     * @param cla нужная часть речи
     * @param lang возможный язык
     * @return вариант или null при ненахождении
     * 
     */
    public static String getWordClassVar(String word, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return null;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (w._class.equals(cla)) 
                    return w.spelling;
            }
        }
        return null;
    }

    /**
     * Может ли быть одушевлённым
     * @param word слово в верхнем регистре и нормальной форме
     * @param lang язык (по умолчанию, русский)
     * @return да-нет
     */
    public static boolean isAnimated(String word, com.pullenti.morph.MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return false;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (w.attrs.isAnimated()) 
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Может ли иметь собственное имя
     * @param word слово в верхнем регистре и нормальной форме
     * @param lang язык (по умолчанию, русский)
     * @return да-нет
     */
    public static boolean isNamed(String word, com.pullenti.morph.MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return false;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (w.attrs.isNamed()) 
                        return true;
                }
            }
        }
        return false;
    }

    public static Object m_Lock;

    public static void setDictionary(com.pullenti.semantic.internal.DerivateDictionary dic) {
        m_DerRu = dic;
    }

    public DerivateService() {
    }
    
    static {
        m_DerRu = new com.pullenti.semantic.internal.DerivateDictionary();
        m_Lock = new Object();
    }
}
