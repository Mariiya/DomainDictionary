/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgTokenData {

    public OrgTokenData(com.pullenti.ner.Token t) {
        tok = t;
        t.tag = this;
    }

    public com.pullenti.ner.Token tok;

    public OrgItemTypeToken typ;

    public OrgItemTypeToken typLow;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(tok.toString());
        if (typ != null) 
            tmp.append(" \r\nTyp: ").append(typ.toString());
        if (typLow != null) 
            tmp.append(" \r\nTypLow: ").append(typLow.toString());
        return tmp.toString();
    }
    public OrgTokenData() {
    }
}
