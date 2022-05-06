/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Элемент внутреннего онтологического словаря
public class IntOntologyItem {

    public IntOntologyItem(com.pullenti.ner.Referent r) {
        referent = r;
    }

    public java.util.ArrayList<Termin> termins = new java.util.ArrayList<Termin>();

    public String getCanonicText() {
        if (m_CanonicText == null && termins.size() > 0) 
            m_CanonicText = termins.get(0).getCanonicText();
        return (m_CanonicText != null ? m_CanonicText : "?");
    }

    public String setCanonicText(String value) {
        m_CanonicText = value;
        return value;
    }


    private String m_CanonicText;

    public void setShortestCanonicalText(boolean ignoreTerminsWithNotnullTags) {
        m_CanonicText = null;
        for (Termin t : termins) {
            if (ignoreTerminsWithNotnullTags && t.tag != null) 
                continue;
            if (t.terms.size() == 0) 
                continue;
            String s = t.getCanonicText();
            if (!com.pullenti.morph.LanguageHelper.isCyrillicChar(s.charAt(0))) 
                continue;
            if (m_CanonicText == null) 
                m_CanonicText = s;
            else if (s.length() < m_CanonicText.length()) 
                m_CanonicText = s;
        }
    }

    public String typ;

    public Object miscAttr;

    public IntOntologyCollection owner;

    public com.pullenti.ner.Referent referent;

    public Object tag;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (typ != null) 
            res.append(typ).append(": ");
        res.append(this.getCanonicText());
        for (Termin t : termins) {
            String tt = t.toString();
            if (com.pullenti.unisharp.Utils.stringsEq(tt, getCanonicText())) 
                continue;
            res.append("; ");
            res.append(tt);
        }
        if (referent != null) 
            res.append(" [").append(referent).append("]");
        return res.toString();
    }
    public IntOntologyItem() {
    }
}
