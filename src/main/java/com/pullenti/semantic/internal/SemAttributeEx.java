/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class SemAttributeEx {

    public SemAttributeEx(com.pullenti.ner.MetaToken mt) {
        token = mt;
    }

    public com.pullenti.ner.MetaToken token;

    public com.pullenti.semantic.SemAttribute attr = new com.pullenti.semantic.SemAttribute();

    public static SemAttributeEx _new3145(com.pullenti.ner.MetaToken _arg1, com.pullenti.semantic.SemAttribute _arg2) {
        SemAttributeEx res = new SemAttributeEx(_arg1);
        res.attr = _arg2;
        return res;
    }
    public SemAttributeEx() {
    }
}
