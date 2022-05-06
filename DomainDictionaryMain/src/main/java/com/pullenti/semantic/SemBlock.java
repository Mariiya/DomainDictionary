/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic;

/**
 * Блок документа (абзац)
 * 
 */
public class SemBlock implements ISemContainer {

    public SemBlock(SemDocument blk) {
        m_Higher = blk;
    }

    @Override
    public SemGraph getGraph() {
        return m_Graph;
    }


    private SemGraph m_Graph = new SemGraph();

    @Override
    public ISemContainer getHigher() {
        return m_Higher;
    }


    public SemDocument m_Higher;

    public SemDocument getDocument() {
        return (SemDocument)com.pullenti.unisharp.Utils.cast(m_Higher, SemDocument.class);
    }


    /**
     * Фрагменты блока - список SemFragment
     * 
     */
    public java.util.ArrayList<SemFragment> fragments = new java.util.ArrayList<SemFragment>();

    /**
     * А это межфрагментные связи - список SemFraglink
     * 
     */
    public java.util.ArrayList<SemFraglink> links = new java.util.ArrayList<SemFraglink>();

    @Override
    public int getBeginChar() {
        return (fragments.size() == 0 ? 0 : fragments.get(0).getBeginChar());
    }


    @Override
    public int getEndChar() {
        return (fragments.size() == 0 ? 0 : fragments.get(fragments.size() - 1).getEndChar());
    }


    public void addFragments(SemBlock blk) {
        for (SemFragment fr : blk.fragments) {
            fr.m_Higher = this;
            fragments.add(fr);
        }
        for (SemFraglink li : blk.links) {
            links.add(li);
        }
    }

    public SemFraglink addLink(SemFraglinkType typ, SemFragment src, SemFragment tgt, String ques) {
        for (SemFraglink li : links) {
            if (li.typ == typ && li.source == src && li.target == tgt) 
                return li;
        }
        SemFraglink res = SemFraglink._new3172(typ, src, tgt, ques);
        links.add(res);
        return res;
    }

    public void mergeWith(SemBlock blk) {
        getGraph().mergeWith(blk.getGraph());
        for (SemFragment fr : blk.fragments) {
            fragments.add(fr);
            fr.m_Higher = this;
        }
        for (SemFraglink li : blk.links) {
            links.add(li);
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (SemFragment fr : fragments) {
            String spel = fr.getSpelling();
            if (spel.length() > 20) 
                spel = spel.substring(0, 0 + 20) + "...";
            tmp.append("[").append(spel).append("] ");
        }
        return tmp.toString();
    }
    public SemBlock() {
    }
}
