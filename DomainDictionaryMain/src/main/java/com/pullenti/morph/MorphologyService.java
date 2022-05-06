/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Сервис морфологического анализа текстов (POS-tagger).
 * 
 * Сервис морфологии
 */
public class MorphologyService {

    /**
     * Инициализация внутренних словарей. 
     * Можно не вызывать, но тогда будет автоматически вызвано при первом обращении к морфологии, 
     * и соответственно первый разбор отработает на несколько секунд дольше. 
     * Если используете Sdk.Initialize() или ProcessorService.Initialize(), то тогда эту функцию вызывать не нужно, 
     * так как там внутри это делается.
     * @param langs по умолчанию, русский и английский
     */
    public static void initialize(MorphLang langs) throws Exception, java.io.IOException {
        com.pullenti.morph.internal.UnicodeInfo.initialize();
        if (langs == null || langs.isUndefined()) 
            langs = MorphLang.ooBitor(MorphLang.RU, MorphLang.EN);
        m_Morph.loadLanguages(langs, LAZYLOAD);
    }

    public static MorphLang getLoadedLanguages() {
        return m_Morph.getLoadedLanguages();
    }


    /**
     * Загрузить язык(и), если они ещё не загружены
     * @param langs загружаемые языки
     */
    public static void loadLanguages(MorphLang langs) throws Exception, java.io.IOException {
        m_Morph.loadLanguages(langs, LAZYLOAD);
    }

    /**
     * Выгрузить язык(и), если они больше не нужны
     * @param langs выгружаемые языки
     */
    public static void unloadLanguages(MorphLang langs) {
        m_Morph.unloadLanguages(langs);
    }

    private static java.util.ArrayList<MorphWordForm> m_EmptyWordForms;

    private static MorphMiscInfo m_EmptyMisc;

    /**
     * Произвести чистую токенизацию без формирования морф-вариантов
     * @param text исходный текст
     * @return последовательность результирующих лексем
     */
    public static java.util.ArrayList<MorphToken> tokenize(String text) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        java.util.ArrayList<MorphToken> res = m_Morph.run(text, true, MorphLang.UNKNOWN, false, null);
        if (res != null) {
            for (MorphToken r : res) {
                if (r.wordForms == null) 
                    r.wordForms = m_EmptyWordForms;
                for (MorphWordForm wf : r.wordForms) {
                    if (wf.misc == null) 
                        wf.misc = m_EmptyMisc;
                }
            }
        }
        return res;
    }

    /**
     * Произвести морфологический анализ текста. Если используете морфологию в составе лингвистического процессора из 
     * ProcessorService, то эту функцию явно вызывать не придётся.
     * @param text исходный текст
     * @param lang базовый язык (если null, то будет определён автоматически)
     * @param progress это для бегунка
     * @return последовательность результирующих лексем MorphToken
     * 
     */
    public static java.util.ArrayList<MorphToken> process(String text, MorphLang lang, com.pullenti.unisharp.ProgressEventHandler progress) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        java.util.ArrayList<MorphToken> res = m_Morph.run(text, false, lang, false, progress);
        if (res != null) {
            for (MorphToken r : res) {
                if (r.wordForms == null) 
                    r.wordForms = m_EmptyWordForms;
                for (MorphWordForm wf : r.wordForms) {
                    if (wf.misc == null) 
                        wf.misc = m_EmptyMisc;
                }
            }
        }
        return res;
    }

    /**
     * Получить все варианты словоформ для нормальной формы слова
     * @param word нормальная форма слова (лемма), в верхнем регистре
     * @param lang язык (по умолчанию, русский)
     * @return список словоформ MorphWordForm
     */
    public static java.util.ArrayList<MorphWordForm> getAllWordforms(String word, MorphLang lang) {
        if (word == null) 
            return null;
        for (char ch : word.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                word = word.toUpperCase();
                break;
            }
        }
        java.util.ArrayList<MorphWordForm> res = m_Morph.getAllWordforms(word, lang);
        if (res != null) {
            for (MorphWordForm r : res) {
                if (r.misc == null) 
                    r.misc = m_EmptyMisc;
            }
        }
        return res;
    }

    /**
     * Получить вариант написания словоформы
     * @param word слово
     * @param morphInfo морфологическая информация
     * @return вариант написания
     */
    public static String getWordform(String word, MorphBaseInfo morphInfo) {
        if (morphInfo == null || com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return word;
        MorphClass cla = morphInfo._getClass();
        if (cla.isUndefined()) {
            MorphWordForm mi0 = getWordBaseInfo(word, null, false, false);
            if (mi0 != null) 
                cla = mi0._getClass();
        }
        String word1 = word;
        for (char ch : word.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                word1 = word.toUpperCase();
                break;
            }
        }
        MorphWordForm wf = (MorphWordForm)com.pullenti.unisharp.Utils.cast(morphInfo, MorphWordForm.class);
        String res = m_Morph.getWordform(word1, cla, morphInfo.getGender(), morphInfo.getCase(), morphInfo.getNumber(), morphInfo.getLanguage(), wf);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(res)) 
            return word;
        return res;
    }

    /**
     * Получить для словоформы род\число\падеж
     * @param word словоформа
     * @param lang возможный язык
     * @param isCaseNominative исходное слово в именительном падеже (иначе считается падеж любым)
     * @param inDictOnly при true не строить гипотезы для несловарных слов
     * @return базовая морфологическая информация
     */
    public static MorphWordForm getWordBaseInfo(String word, MorphLang lang, boolean isCaseNominative, boolean inDictOnly) {
        java.util.ArrayList<MorphToken> mt = m_Morph.run(word, false, lang, false, null);
        MorphWordForm bi = new MorphWordForm();
        MorphClass cla = new MorphClass();
        if (mt != null && mt.size() > 0) {
            for (int k = 0; k < 2; k++) {
                boolean ok = false;
                for (MorphWordForm wf : mt.get(0).wordForms) {
                    if (k == 0) {
                        if (!wf.isInDictionary()) 
                            continue;
                    }
                    else if (wf.isInDictionary()) 
                        continue;
                    if (isCaseNominative) {
                        if (!wf.getCase().isNominative() && !wf.getCase().isUndefined()) 
                            continue;
                    }
                    cla.value |= wf._getClass().value;
                    bi.setGender(MorphGender.of((short)((bi.getGender().value()) | (wf.getGender().value()))));
                    bi.setCase(MorphCase.ooBitor(bi.getCase(), wf.getCase()));
                    bi.setNumber(MorphNumber.of((short)((bi.getNumber().value()) | (wf.getNumber().value()))));
                    if (wf.misc != null && bi.misc == null) 
                        bi.misc = wf.misc;
                    ok = true;
                }
                if (ok || inDictOnly) 
                    break;
            }
        }
        bi._setClass(cla);
        return bi;
    }

    /**
     * Попробовать откорректировать одну букву словоформы, чтобы получилось словарное слово. 
     * Делается изменение одной буквы, удаление одной буквы и вставка одной буквы. 
     * Если получается несколько вариантов, то возвращается null. Для получение всех вариантов используйте CorrectWordEx.
     * @param word искаженное слово
     * @param lang возможный язык
     * @return откорректированное слово или null при невозможности
     */
    public static String correctWord(String word, MorphLang lang) {
        java.util.ArrayList<String> vars = m_Morph.correctWordByMorph(word, lang, true);
        if (vars == null || vars.size() != 1) 
            return null;
        return vars.get(0);
    }

    /**
     * Попробовать откорректировать одну букву словоформы, чтобы получилось словарное слово. 
     * Делается изменение одной буквы, удаление одной буквы и вставка одной буквы.
     * @param word искаженное слово
     * @param lang возможный язык
     * @return "правильные" варианты или null
     */
    public static java.util.ArrayList<String> correctWordEx(String word, MorphLang lang) {
        return m_Morph.correctWordByMorph(word, lang, false);
    }

    /**
     * Преобразовать наречие в прилагательное (это пока только для русского языка)
     * @param adverb наречие
     * @param bi род число падеж
     * @return прилагательное
     */
    public static String convertAdverbToAdjective(String adverb, MorphBaseInfo bi) {
        if (adverb == null || (adverb.length() < 4)) 
            return null;
        char last = adverb.charAt(adverb.length() - 1);
        if (last != 'О' && last != 'Е') 
            return adverb;
        String var1 = adverb.substring(0, 0 + adverb.length() - 1) + "ИЙ";
        String var2 = adverb.substring(0, 0 + adverb.length() - 1) + "ЫЙ";
        MorphWordForm bi1 = getWordBaseInfo(var1, null, false, false);
        MorphWordForm bi2 = getWordBaseInfo(var2, null, false, false);
        String var = var1;
        if (!bi1._getClass().isAdjective() && bi2._getClass().isAdjective()) 
            var = var2;
        if (bi == null) 
            return var;
        return (String)com.pullenti.unisharp.Utils.notnull(m_Morph.getWordform(var, MorphClass.ADJECTIVE, bi.getGender(), bi.getCase(), bi.getNumber(), MorphLang.UNKNOWN, null), var);
    }

    // При и нициализации не грузить всю морфологиюю сразу в память, а подгружать по мере необходимости.
    // Сильно экономит время (при инициализации) и память.
    public static boolean LAZYLOAD = true;

    private static com.pullenti.morph.internal.InnerMorphology m_Morph;

    public MorphologyService() {
    }
    
    static {
        m_EmptyWordForms = new java.util.ArrayList<MorphWordForm>();
        m_EmptyMisc = new MorphMiscInfo();
        m_Morph = new com.pullenti.morph.internal.InnerMorphology();
    }
}
