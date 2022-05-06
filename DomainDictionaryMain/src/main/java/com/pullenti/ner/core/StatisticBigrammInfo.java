/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Статистическая информация о биграмме - возвращается StatisticCollection.GetBigrammInfo
 * Статистика биграмм
 */
public class StatisticBigrammInfo {

    /**
     * Сколько всего первых словоформ в тексте
     */
    public int firstCount;

    /**
     * Сколько всего вторых словоформ в тексте
     */
    public int secondCount;

    /**
     * Сколько всего они встречаются вместе
     */
    public int pairCount;

    /**
     * Признак, что первая словоформа имеет и другие продолжения
     */
    public boolean firstHasOtherSecond;

    /**
     * Признак, что вторая словоформа имеет перед собой и другие слова
     */
    public boolean secondHasOtherFirst;

    public static StatisticBigrammInfo _new575(int _arg1, int _arg2) {
        StatisticBigrammInfo res = new StatisticBigrammInfo();
        res.firstCount = _arg1;
        res.secondCount = _arg2;
        return res;
    }
    public StatisticBigrammInfo() {
    }
}
