/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core.internal;

// Морфологический вариант для элемента именной группы
public class NounPhraseItemTextVar extends com.pullenti.morph.MorphBaseInfo {

    public NounPhraseItemTextVar(com.pullenti.morph.MorphBaseInfo src, com.pullenti.ner.Token t) {
        super();
        if (src != null) 
            this.copyFrom(src);
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(src, com.pullenti.morph.MorphWordForm.class);
        if (wf != null) {
            normalValue = wf.normalCase;
            if (wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && wf.normalFull != null) 
                singleNumberValue = wf.normalFull;
            undefCoef = (int)wf.undefCoef;
        }
        else if (t != null) 
            normalValue = t.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (getCase().isUndefined() && src != null) {
            if (src.containsAttr("неизм.", null)) 
                setCase(com.pullenti.morph.MorphCase.ALLCASES);
        }
    }

    public String normalValue;

    public String singleNumberValue;

    public int undefCoef;

    @Override
    public String toString() {
        return (normalValue + " " + super.toString());
    }

    public void copyFromItem(NounPhraseItemTextVar src) {
        this.copyFrom(src);
        normalValue = src.normalValue;
        singleNumberValue = src.singleNumberValue;
        undefCoef = src.undefCoef;
    }

    public void correctPrefix(com.pullenti.ner.TextToken t, boolean ignoreGender) {
        if (t == null) 
            return;
        for (com.pullenti.morph.MorphBaseInfo v : t.getMorph().getItems()) {
            if (v._getClass().equals(this._getClass()) && this.checkAccord(v, ignoreGender, false)) {
                normalValue = (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class)).normalCase + "-" + normalValue);
                if (singleNumberValue != null) 
                    singleNumberValue = (((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class)).normalFull != null ? ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class)).normalFull : ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class)).normalCase)) + "-" + singleNumberValue);
                return;
            }
        }
        normalValue = (t.term + "-" + normalValue);
        if (singleNumberValue != null) 
            singleNumberValue = (t.term + "-" + singleNumberValue);
    }
    public NounPhraseItemTextVar() {
        this(null, null);
    }
}
