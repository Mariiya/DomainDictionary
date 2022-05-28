/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class ShortNameHelper {

    private static java.util.HashMap<String, java.util.ArrayList<ShortnameVar>> m_Shorts_Names;

    public static class ShortnameVar {
    
        public String name;
    
        public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;
    
        @Override
        public String toString() {
            return name;
        }
    
        public static ShortnameVar _new2804(String _arg1, com.pullenti.morph.MorphGender _arg2) {
            ShortnameVar res = new ShortnameVar();
            res.name = _arg1;
            res.gender = _arg2;
            return res;
        }
        public ShortnameVar() {
        }
    }


    public static java.util.ArrayList<String> getShortnamesForName(String name) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (java.util.Map.Entry<String, java.util.ArrayList<ShortnameVar>> kp : m_Shorts_Names.entrySet()) {
            for (ShortnameVar v : kp.getValue()) {
                if (com.pullenti.unisharp.Utils.stringsEq(v.name, name)) {
                    if (!res.contains(kp.getKey())) 
                        res.add(kp.getKey());
                }
            }
        }
        return res;
    }

    public static java.util.ArrayList<ShortnameVar> getNamesForShortname(String shortname) {
        java.util.ArrayList<ShortnameVar> res;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<ShortnameVar>> wrapres2802 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<ShortnameVar>>();
        boolean inoutres2803 = com.pullenti.unisharp.Utils.tryGetValue(m_Shorts_Names, shortname, wrapres2802);
        res = wrapres2802.value;
        if (!inoutres2803) 
            return null;
        else 
            return res;
    }

    private static boolean m_Inited = false;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        String obj = ResourceHelper.getString("ShortNames.txt");
        if (obj != null) {
            com.pullenti.ner.core.AnalysisKit kit = new com.pullenti.ner.core.AnalysisKit(new com.pullenti.ner.SourceOfAnalysis(obj), false, null, null);
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.isNewlineBefore()) {
                    com.pullenti.morph.MorphGender g = (t.isValue("F", null) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE);
                    t = t.getNext();
                    String nam = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                    java.util.ArrayList<String> shos = new java.util.ArrayList<String>();
                    for (t = t.getNext(); t != null; t = t.getNext()) {
                        if (t.isNewlineBefore()) 
                            break;
                        else 
                            shos.add(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
                    }
                    for (String s : shos) {
                        java.util.ArrayList<ShortnameVar> li = null;
                        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<ShortnameVar>> wrapli2805 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<ShortnameVar>>();
                        boolean inoutres2806 = com.pullenti.unisharp.Utils.tryGetValue(m_Shorts_Names, s, wrapli2805);
                        li = wrapli2805.value;
                        if (!inoutres2806) 
                            m_Shorts_Names.put(s, (li = new java.util.ArrayList<ShortnameVar>()));
                        li.add(ShortnameVar._new2804(nam, g));
                    }
                    if (t == null) 
                        break;
                    t = t.getPrevious();
                }
            }
        }
    }

    public ShortNameHelper() {
    }
    public static ShortNameHelper _globalInstance;
    
    static {
        try { _globalInstance = new ShortNameHelper(); } 
        catch(Exception e) { }
        m_Shorts_Names = new java.util.HashMap<String, java.util.ArrayList<ShortnameVar>>();
    }
}
