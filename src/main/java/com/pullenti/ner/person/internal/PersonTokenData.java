/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonTokenData {

    public PersonTokenData(com.pullenti.ner.Token t) {
        tok = t;
        t.tag = this;
    }

    public com.pullenti.ner.Token tok;

    public PersonAttrToken attr;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(tok.toString());
        if (attr != null) 
            tmp.append(" \r\nAttr: ").append(attr.toString());
        return tmp.toString();
    }
    public PersonTokenData() {
    }
}
