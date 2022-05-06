/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphRuleVariant extends com.pullenti.morph.MorphBaseInfo {

    public MorphRuleVariant() {
        super();
    }

    public void copyFromVariant(MorphRuleVariant src) {
        if (src == null) 
            return;
        tail = src.tail;
        this.copyFrom(src);
        miscInfoId = src.miscInfoId;
        normalTail = src.normalTail;
        fullNormalTail = src.fullNormalTail;
        ruleId = src.ruleId;
    }

    public String tail;

    public short miscInfoId;

    public short ruleId;

    public short id;

    public String normalTail;

    public String fullNormalTail;

    public Object tag;

    @Override
    public String toString() {
        return this.toStringEx(false);
    }

    public String toStringEx(boolean hideTails) {
        StringBuilder res = new StringBuilder();
        if (!hideTails) {
            res.append("-").append(tail);
            if (normalTail != null) 
                res.append(" [-").append(normalTail).append("]");
            if (fullNormalTail != null && com.pullenti.unisharp.Utils.stringsNe(fullNormalTail, normalTail)) 
                res.append(" [-").append(fullNormalTail).append("]");
        }
        res.append(" ").append(super.toString());
        return res.toString().trim();
    }

    public boolean compare(MorphRuleVariant mrv) {
        if ((!mrv._getClass().equals(this._getClass()) || mrv.getGender() != getGender() || mrv.getNumber() != getNumber()) || !mrv.getCase().equals(this.getCase())) 
            return false;
        if (mrv.miscInfoId != miscInfoId) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(mrv.normalTail, normalTail)) 
            return false;
        return true;
    }

    public boolean deserialize(ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int _id = str.deserializeShort(pos);
        if (_id <= 0) 
            return false;
        miscInfoId = (short)_id;
        int iii = str.deserializeShort(pos);
        com.pullenti.morph.MorphClass mc = new com.pullenti.morph.MorphClass();
        mc.value = (short)iii;
        if (mc.isMisc() && mc.isProper()) 
            mc.setMisc(false);
        _setClass(mc);
        byte bbb;
        bbb = str.deserializeByte(pos);
        setGender(com.pullenti.morph.MorphGender.of((short)Byte.toUnsignedInt(bbb)));
        bbb = str.deserializeByte(pos);
        setNumber(com.pullenti.morph.MorphNumber.of((short)Byte.toUnsignedInt(bbb)));
        bbb = str.deserializeByte(pos);
        com.pullenti.morph.MorphCase mca = new com.pullenti.morph.MorphCase();
        mca.value = (short)Byte.toUnsignedInt(bbb);
        setCase(mca);
        String s = str.deserializeString(pos);
        normalTail = s;
        s = str.deserializeString(pos);
        fullNormalTail = s;
        return true;
    }
}
