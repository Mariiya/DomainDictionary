/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphRule {

    public int id;

    public java.util.ArrayList<String> tails = new java.util.ArrayList<String>();

    public java.util.ArrayList<java.util.ArrayList<MorphRuleVariant>> morphVars = new java.util.ArrayList<java.util.ArrayList<MorphRuleVariant>>();

    public boolean containsVar(String tail) {
        return tails.indexOf(tail) >= 0;
    }

    public java.util.ArrayList<MorphRuleVariant> getVars(String key) {
        int i = tails.indexOf(key);
        if (i >= 0) 
            return morphVars.get(i);
        return null;
    }

    public MorphRuleVariant findVar(int _id) {
        for (java.util.ArrayList<MorphRuleVariant> li : morphVars) {
            for (MorphRuleVariant v : li) {
                if (v.id == _id) 
                    return v;
            }
        }
        return null;
    }

    public void add(String tail, java.util.ArrayList<MorphRuleVariant> vars) {
        tails.add(tail);
        morphVars.add(vars);
    }

    public int lazyPos;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tails.size(); i++) {
            if (res.length() > 0) 
                res.append(", ");
            res.append("-").append(tails.get(i));
        }
        return res.toString();
    }

    public void deserialize(ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int ii = str.deserializeShort(pos);
        id = ii;
        short _id = (short)1;
        while (!str.isEOF(pos.value)) {
            byte b = str.deserializeByte(pos);
            if (b == ((byte)0xFF)) 
                break;
            pos.value--;
            String key = str.deserializeString(pos);
            if (key == null) 
                key = "";
            java.util.ArrayList<MorphRuleVariant> li = new java.util.ArrayList<MorphRuleVariant>();
            while (!str.isEOF(pos.value)) {
                MorphRuleVariant mrv = new MorphRuleVariant();
                boolean inoutres24 = mrv.deserialize(str, pos);
                if (!inoutres24) 
                    break;
                mrv.tail = key;
                mrv.ruleId = (short)ii;
                mrv.id = _id++;
                li.add(mrv);
            }
            this.add(key, li);
        }
    }
    public MorphRule() {
    }
}
