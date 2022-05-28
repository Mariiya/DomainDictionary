/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class ExplanTreeNode {

    public java.util.HashMap<Short, ExplanTreeNode> nodes;

    public java.util.ArrayList<Integer> groups;

    public int lazyPos;

    public void deserialize(com.pullenti.morph.internal.ByteArrayWrapper str, DerivateDictionary dic, boolean lazyLoad, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int cou = str.deserializeShort(pos);
        java.util.ArrayList<Integer> li = (cou > 0 ? new java.util.ArrayList<Integer>() : null);
        for (; cou > 0; cou--) {
            int id = str.deserializeInt(pos);
            if (id > 0 && id <= dic.m_AllGroups.size()) {
                com.pullenti.semantic.utils.DerivateGroup gr = dic.m_AllGroups.get(id - 1);
                if (gr.lazyPos > 0) {
                    int p0 = pos.value;
                    pos.value = gr.lazyPos;
                    gr.deserialize(str, pos);
                    gr.lazyPos = 0;
                    pos.value = p0;
                }
            }
            li.add(id);
        }
        if (li != null) 
            groups = li;
        cou = str.deserializeShort(pos);
        if (cou == 0) 
            return;
        for (; cou > 0; cou--) {
            int ke = str.deserializeShort(pos);
            int p1 = str.deserializeInt(pos);
            ExplanTreeNode tn1 = new ExplanTreeNode();
            if (nodes == null) 
                nodes = new java.util.HashMap<Short, ExplanTreeNode>();
            short sh = (short)ke;
            if (lazyLoad) {
                tn1.lazyPos = pos.value;
                pos.value = p1;
            }
            else 
                tn1.deserialize(str, dic, false, pos);
            if (!nodes.containsKey(sh)) 
                nodes.put(sh, tn1);
        }
    }
    public ExplanTreeNode() {
    }
}
