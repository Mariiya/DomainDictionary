/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

public class DateTokenData {

    public DateTokenData(com.pullenti.ner.Token t) {
        tok = t;
        t.tag = this;
    }

    public com.pullenti.ner.Token tok;

    public DateItemToken dat;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(tok.toString());
        if (dat != null) 
            tmp.append(" \r\nDat: ").append(dat.toString());
        return tmp.toString();
    }
    public DateTokenData() {
    }
}
