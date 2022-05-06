/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Документ
 * 
 */
public class SemDocument implements ISemContainer {

    @Override
    public SemGraph getGraph() {
        return m_Graph;
    }


    private SemGraph m_Graph = new SemGraph();

    @Override
    public ISemContainer getHigher() {
        return null;
    }


    /**
     * Блоки документа - список SemBlock
     * 
     */
    public java.util.ArrayList<SemBlock> blocks = new java.util.ArrayList<SemBlock>();

    @Override
    public int getBeginChar() {
        return (blocks.size() == 0 ? 0 : blocks.get(0).getBeginChar());
    }


    @Override
    public int getEndChar() {
        return (blocks.size() == 0 ? 0 : blocks.get(blocks.size() - 1).getEndChar());
    }


    public void mergeAllBlocks() {
        if (blocks.size() < 2) 
            return;
        for (int i = 1; i < blocks.size(); i++) {
            blocks.get(0).mergeWith(blocks.get(i));
        }
        for(int jjj = 1 + blocks.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) blocks.remove(jjj);
    }
    public SemDocument() {
    }
}
