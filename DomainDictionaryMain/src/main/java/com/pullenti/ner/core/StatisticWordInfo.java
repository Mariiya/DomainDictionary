/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Статистическая информация о токене - возвращается StatisticCollection.GetWordInfo
 * Статистика токена
 */
public class StatisticWordInfo {

    /**
     * Нормализация
     */
    public String normal;

    @Override
    public String toString() {
        return normal;
    }

    /**
     * Общее число вхождений в тексте
     */
    public int totalCount;

    /**
     * Число вхождений словоформы написаний в нижнем регистре
     */
    public int lowerCount;

    /**
     * Число вхождений словоформы написаний в верхнем регистре
     */
    public int upperCount;

    /**
     * Число вхождений словоформы написаний с заглавной буквой
     */
    public int capitalCount;

    /**
     * Сколько раз за словом идёт глагол мужского рода
     */
    public int maleVerbsAfterCount;

    /**
     * Сколько раз за словом идёт глагол женского рода
     */
    public int femaleVerbsAfterCount;

    /**
     * Есть ли перед атрибут персон (вычисляется только в процессе отработки соотв. анализатора на его 1-м проходе)
     */
    public boolean hasBeforePersonAttr;

    /**
     * Количество слов перед этим, которые не тексты или в нижнем регистре 
     * (например, для проверки отчеств - фамилия ли это)
     */
    public int notCapitalBeforeCount;

    public java.util.HashMap<StatisticWordInfo, Integer> likeCharsBeforeWords;

    public java.util.HashMap<StatisticWordInfo, Integer> likeCharsAfterWords;

    public void addBefore(StatisticWordInfo w) {
        if (likeCharsBeforeWords == null) 
            likeCharsBeforeWords = new java.util.HashMap<StatisticWordInfo, Integer>();
        if (!likeCharsBeforeWords.containsKey(w)) 
            likeCharsBeforeWords.put(w, 1);
        else 
            likeCharsBeforeWords.put(w, likeCharsBeforeWords.get(w) + 1);
    }

    public void addAfter(StatisticWordInfo w) {
        if (likeCharsAfterWords == null) 
            likeCharsAfterWords = new java.util.HashMap<StatisticWordInfo, Integer>();
        if (!likeCharsAfterWords.containsKey(w)) 
            likeCharsAfterWords.put(w, 1);
        else 
            likeCharsAfterWords.put(w, likeCharsAfterWords.get(w) + 1);
    }

    public static StatisticWordInfo _new562(String _arg1) {
        StatisticWordInfo res = new StatisticWordInfo();
        res.normal = _arg1;
        return res;
    }
    public StatisticWordInfo() {
    }
}
