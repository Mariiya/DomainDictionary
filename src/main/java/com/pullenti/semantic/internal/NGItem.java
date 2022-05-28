/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class NGItem {

    public SentItem source;

    public int order;

    public boolean commaBefore;

    public boolean commaAfter;

    public boolean andBefore;

    public boolean andAfter;

    public boolean orBefore;

    public boolean orAfter;

    public java.util.ArrayList<NGLink> links = new java.util.ArrayList<NGLink>();

    public int ind;

    public com.pullenti.semantic.SemObject getResObject() {
        return (source == null ? null : source.result);
    }


    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (commaBefore) 
            tmp.append("[,] ");
        else if (orBefore) 
            tmp.append("[|] ");
        else if (andBefore) 
            tmp.append("[&] ");
        tmp.append(source.toString());
        if (commaAfter) 
            tmp.append(" [,]");
        else if (orAfter) 
            tmp.append(" [|]");
        else if (andAfter) 
            tmp.append(" [&]");
        return tmp.toString();
    }

    public void prepare() {
        links.clear();
    }

    public static NGItem _new3126(SentItem _arg1) {
        NGItem res = new NGItem();
        res.source = _arg1;
        return res;
    }

    public static NGItem _new3127(SentItem _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        NGItem res = new NGItem();
        res.source = _arg1;
        res.commaBefore = _arg2;
        res.andBefore = _arg3;
        res.orBefore = _arg4;
        return res;
    }

    public static NGItem _new3132(SentItem _arg1, int _arg2) {
        NGItem res = new NGItem();
        res.source = _arg1;
        res.order = _arg2;
        return res;
    }
    public NGItem() {
    }
}
