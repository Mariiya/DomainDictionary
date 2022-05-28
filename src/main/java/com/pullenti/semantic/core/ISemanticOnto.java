/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.core;

/**
 * Интерфейс внешней дополнительной онтологии 
 * (для улучшения качества семантичсекой обработки)
 * Внешняя онтология
 */
public interface ISemanticOnto {

    /**
     * Проверка, что в онтологии слова master и slave образуют устойчивую пару
     * @param master 
     * @param slave 
     * @return 
     */
    boolean checkLink(String master, String slave);
}
