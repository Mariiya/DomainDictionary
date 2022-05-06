/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Дополнительные параметры
 */
public class SemProcessParams {

    /**
     * Не делать анафору, оставлять всё как есть
     */
    public boolean dontCreateAnafor;

    /**
     * Максимальнкая длина (дальше этого символа обработки не будет)
     */
    public int maxChar = 0;

    /**
     * Для реализации бегущей строки
     */
    public com.pullenti.unisharp.ProgressEventHandler progress = null;
    public SemProcessParams() {
    }
}
