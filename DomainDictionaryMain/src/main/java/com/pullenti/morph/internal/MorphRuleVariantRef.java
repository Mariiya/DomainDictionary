/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphRuleVariantRef implements Comparable<MorphRuleVariantRef> {

    public MorphRuleVariantRef(int rid, short vid, short co) {
        ruleId = rid;
        variantId = vid;
        coef = co;
    }

    public int ruleId;

    public short variantId;

    public short coef;

    @Override
    public String toString() {
        return (((Integer)ruleId).toString() + " " + variantId);
    }

    @Override
    public int compareTo(MorphRuleVariantRef other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }
    public MorphRuleVariantRef() {
    }
}
