/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

public class DateAnalyzerData extends com.pullenti.ner.core.AnalyzerData {

    private java.util.HashMap<String, com.pullenti.ner.Referent> m_Hash = new java.util.HashMap<String, com.pullenti.ner.Referent>();

    @Override
    public java.util.Collection<com.pullenti.ner.Referent> getReferents() {
        return m_Hash.values();
    }


    @Override
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        String key = referent.toString();
        com.pullenti.ner.Referent dr;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wrapdr631 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
        boolean inoutres632 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, key, wrapdr631);
        dr = wrapdr631.value;
        if (inoutres632) 
            return dr;
        m_Hash.put(key, referent);
        return referent;
    }

    public boolean dRegime = false;
    public DateAnalyzerData() {
        super();
    }
}
