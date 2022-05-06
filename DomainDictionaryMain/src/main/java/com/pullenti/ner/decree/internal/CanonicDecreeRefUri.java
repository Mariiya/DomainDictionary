/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class CanonicDecreeRefUri {

    public CanonicDecreeRefUri(String txt) {
        text = txt;
    }

    public com.pullenti.ner.Referent ref;

    public int beginChar;

    public int endChar;

    public boolean isDiap = false;

    public boolean isAdopted = false;

    public String typeWithGeo;

    public String text;

    @Override
    public String toString() {
        return (text == null ? "?" : text.substring(beginChar, beginChar + (endChar + 1) - beginChar));
    }

    public static CanonicDecreeRefUri _new896(String _arg1, com.pullenti.ner.Referent _arg2, int _arg3, int _arg4) {
        CanonicDecreeRefUri res = new CanonicDecreeRefUri(_arg1);
        res.ref = _arg2;
        res.beginChar = _arg3;
        res.endChar = _arg4;
        return res;
    }

    public static CanonicDecreeRefUri _new898(String _arg1, com.pullenti.ner.Referent _arg2, int _arg3, int _arg4, boolean _arg5) {
        CanonicDecreeRefUri res = new CanonicDecreeRefUri(_arg1);
        res.ref = _arg2;
        res.beginChar = _arg3;
        res.endChar = _arg4;
        res.isDiap = _arg5;
        return res;
    }
    public CanonicDecreeRefUri() {
    }
}
