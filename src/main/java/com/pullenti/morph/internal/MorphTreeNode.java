/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphTreeNode {

    public java.util.HashMap<Short, MorphTreeNode> nodes;

    public java.util.ArrayList<Integer> ruleIds;

    public java.util.ArrayList<MorphRuleVariantRef> reverceVariants;

    public int calcTotalNodes() {
        int res = 0;
        if (nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> v : nodes.entrySet()) {
                res += (v.getValue().calcTotalNodes() + 1);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        int cou = (ruleIds == null ? 0 : ruleIds.size());
        return ("?" + " (" + this.calcTotalNodes() + ", " + cou + ")");
    }

    public int lazyPos;

    private void _deserializeBase(ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int cou = str.deserializeShort(pos);
        if (cou > 0) {
            ruleIds = new java.util.ArrayList<Integer>();
            for (; cou > 0; cou--) {
                int id = str.deserializeShort(pos);
                if (id == 0) {
                }
                ruleIds.add(id);
            }
        }
        cou = str.deserializeShort(pos);
        if (cou > 0) {
            reverceVariants = new java.util.ArrayList<MorphRuleVariantRef>();
            for (; cou > 0; cou--) {
                int rid = str.deserializeShort(pos);
                if (rid == 0) {
                }
                int id = str.deserializeShort(pos);
                int co = str.deserializeShort(pos);
                reverceVariants.add(new MorphRuleVariantRef(rid, (short)id, (short)co));
            }
        }
    }

    public int deserialize(ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int res = 0;
        this._deserializeBase(str, pos);
        int cou = str.deserializeShort(pos);
        if (cou > 0) {
            nodes = new java.util.HashMap<Short, MorphTreeNode>();
            for (; cou > 0; cou--) {
                int i = str.deserializeShort(pos);
                int pp = str.deserializeInt(pos);
                MorphTreeNode child = new MorphTreeNode();
                int res1 = child.deserialize(str, pos);
                res += (1 + res1);
                nodes.put((short)i, child);
            }
        }
        return res;
    }

    public void deserializeLazy(ByteArrayWrapper str, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        this._deserializeBase(str, pos);
        int cou = str.deserializeShort(pos);
        if (cou > 0) {
            nodes = new java.util.HashMap<Short, MorphTreeNode>();
            for (; cou > 0; cou--) {
                int i = str.deserializeShort(pos);
                int pp = str.deserializeInt(pos);
                MorphTreeNode child = new MorphTreeNode();
                child.lazyPos = pos.value;
                nodes.put((short)i, child);
                pos.value = pp;
            }
        }
        int p = pos.value;
        if (ruleIds != null) {
            for (int rid : ruleIds) {
                MorphRule r = me.getMutRule(rid);
                if (r.lazyPos > 0) {
                    pos.value = r.lazyPos;
                    r.deserialize(str, pos);
                    r.lazyPos = 0;
                }
            }
            pos.value = p;
        }
        if (reverceVariants != null) {
            for (MorphRuleVariantRef rv : reverceVariants) {
                MorphRule r = me.getMutRule(rv.ruleId);
                if (r.lazyPos > 0) {
                    pos.value = r.lazyPos;
                    r.deserialize(str, pos);
                    r.lazyPos = 0;
                }
            }
            pos.value = p;
        }
    }
    public MorphTreeNode() {
    }
}
