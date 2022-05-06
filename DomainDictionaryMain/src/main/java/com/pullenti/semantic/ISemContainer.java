/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Интерфейс владельца семантического графа
 */
public interface ISemContainer {

    SemGraph getGraph();


    ISemContainer getHigher();


    int getBeginChar();


    int getEndChar();

}
