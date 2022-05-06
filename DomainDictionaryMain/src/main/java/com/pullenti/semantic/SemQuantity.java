/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Количественная характеристика. Планируется переработка этого класса 
 * (поддержка сложной модели диапазонов, составных значений и пр.).
 * Количество
 */
public class SemQuantity extends com.pullenti.ner.MetaToken {

    public SemQuantity(String _spelling, com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        spelling = _spelling;
    }

    /**
     * Суммарное написание
     */
    public String spelling;

    @Override
    public String toString() {
        return spelling;
    }
    public SemQuantity() {
        super();
    }
}
