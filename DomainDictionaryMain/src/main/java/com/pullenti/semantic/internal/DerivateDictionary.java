/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class DerivateDictionary {

    public com.pullenti.morph.MorphLang lang;

    private boolean m_Inited = false;

    private com.pullenti.morph.internal.ByteArrayWrapper m_Buf;

    public void load(byte[] dat) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.MemoryStream mem = new com.pullenti.unisharp.MemoryStream(dat)) {
            m_AllGroups.clear();
            m_Root = new ExplanTreeNode();
            this.deserialize(mem, true);
            m_Inited = true;
        }
    }

    public boolean init(com.pullenti.morph.MorphLang _lang, boolean lazy) throws Exception, java.io.IOException {
        if (m_Inited) 
            return true;
        
        String rsname = ("d_" + _lang.toString() + ".dat");
        String[] names = com.pullenti.semantic.utils.properties.Resources.getNames();
        for (String n : names) {
            if (com.pullenti.unisharp.Utils.endsWithString(n, rsname, true)) {
                Object inf = com.pullenti.semantic.utils.properties.Resources.getResourceInfo(n);
                if (inf == null) 
                    continue;
                try (com.pullenti.unisharp.Stream stream = com.pullenti.semantic.utils.properties.Resources.getStream(n)) {
                    stream.setPosition(0L);
                    m_AllGroups.clear();
                    this.deserialize(stream, lazy);
                    lang = _lang;
                }
                m_Inited = true;
                return true;
            }
        }
        return false;
    }

    public ExplanTreeNode m_Root = new ExplanTreeNode();

    public void unload() {
        m_Root = new ExplanTreeNode();
        m_AllGroups.clear();
        lang = new com.pullenti.morph.MorphLang();
    }

    public java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> m_AllGroups = new java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup>();

    public com.pullenti.semantic.utils.DerivateGroup getGroup(int id) {
        if (id >= 1 && id <= m_AllGroups.size()) 
            return m_AllGroups.get(id - 1);
        return null;
    }

    public Object m_Lock = new Object();

    private void _loadTreeNode(ExplanTreeNode tn) {
        synchronized (m_Lock) {
            int pos = tn.lazyPos;
            if (pos > 0) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos3121 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                tn.deserialize(m_Buf, this, true, wrappos3121);
                pos = (wrappos3121.value != null ? wrappos3121.value : 0);
            }
            tn.lazyPos = 0;
        }
    }

    public void deserialize(com.pullenti.unisharp.Stream str, boolean lazyLoad) throws Exception, java.io.IOException {
        com.pullenti.morph.internal.ByteArrayWrapper wr = null;
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream()) {
            com.pullenti.morph.internal.MorphDeserializer.deflateGzip(str, tmp);
            wr = new com.pullenti.morph.internal.ByteArrayWrapper(tmp.toByteArray());
            int pos = 0;
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos3125 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            int cou = wr.deserializeInt(wrappos3125);
            pos = (wrappos3125.value != null ? wrappos3125.value : 0);
            for (; cou > 0; cou--) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos3123 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                int p1 = wr.deserializeInt(wrappos3123);
                pos = (wrappos3123.value != null ? wrappos3123.value : 0);
                com.pullenti.semantic.utils.DerivateGroup ew = new com.pullenti.semantic.utils.DerivateGroup();
                if (lazyLoad) {
                    ew.lazyPos = pos;
                    pos = p1;
                }
                else {
                    com.pullenti.unisharp.Outargwrapper<Integer> wrappos3122 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                    ew.deserialize(wr, wrappos3122);
                    pos = (wrappos3122.value != null ? wrappos3122.value : 0);
                }
                ew.id = m_AllGroups.size() + 1;
                m_AllGroups.add(ew);
            }
            m_Root = new ExplanTreeNode();
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos3124 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            m_Root.deserialize(wr, this, lazyLoad, wrappos3124);
            pos = (wrappos3124.value != null ? wrappos3124.value : 0);
        }
        m_Buf = wr;
    }

    public java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> find(String word, boolean tryCreate, com.pullenti.morph.MorphLang _lang) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return null;
        ExplanTreeNode tn = m_Root;
        int i;
        for (i = 0; i < word.length(); i++) {
            short k = (short)word.charAt(i);
            if (tn.nodes == null) 
                break;
            if (!tn.nodes.containsKey(k)) 
                break;
            tn = tn.nodes.get(k);
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
        }
        java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup> li = null;
        if (i >= word.length() && tn.groups != null) {
            li = new java.util.ArrayList<com.pullenti.semantic.utils.DerivateGroup>();
            for (int g : tn.groups) {
                li.add(this.getGroup(g));
            }
            boolean gen = false;
            boolean nogen = false;
            for (com.pullenti.semantic.utils.DerivateGroup g : li) {
                if (g.isGenerated) 
                    gen = true;
                else 
                    nogen = true;
            }
            if (gen && nogen) {
                for (i = li.size() - 1; i >= 0; i--) {
                    if (li.get(i).isGenerated) 
                        li.remove(i);
                }
            }
        }
        if (li != null && _lang != null && !_lang.isUndefined()) {
            for (i = li.size() - 1; i >= 0; i--) {
                if (!li.get(i).containsWord(word, _lang)) 
                    li.remove(i);
            }
        }
        if (li != null && li.size() > 0) 
            return li;
        if (word.length() < 4) 
            return null;
        char ch0 = word.charAt(word.length() - 1);
        char ch1 = word.charAt(word.length() - 2);
        char ch2 = word.charAt(word.length() - 3);
        if (ch0 == 'О' || ((ch0 == 'И' && ch1 == 'К'))) {
            String word1 = word.substring(0, 0 + word.length() - 1);
            if ((((li = this.find(word1 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = this.find(word1 + "ЫЙ", false, _lang)))) != null) 
                return li;
            if (ch0 == 'О' && ch1 == 'Н') {
                if ((((li = this.find(word1 + "СКИЙ", false, _lang)))) != null) 
                    return li;
            }
        }
        else if (((ch0 == 'Я' || ch0 == 'Ь')) && (word.charAt(word.length() - 2) == 'С')) {
            String word1 = word.substring(0, 0 + word.length() - 2);
            if (com.pullenti.unisharp.Utils.stringsEq(word1, "ЯТЬ")) 
                return null;
            if ((((li = this.find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Е' && ch1 == 'Ь') {
            String word1 = word.substring(0, 0 + word.length() - 2) + "ИЕ";
            if ((((li = this.find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Й' && ch2 == 'Н' && tryCreate) {
            char ch3 = word.charAt(word.length() - 4);
            String word1 = null;
            if (ch3 != 'Н') {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch3)) 
                    word1 = word.substring(0, 0 + word.length() - 3) + "Н" + word.substring(word.length() - 3);
            }
            else 
                word1 = word.substring(0, 0 + word.length() - 4) + word.substring(word.length() - 3);
            if (word1 != null) {
                if ((((li = this.find(word1, false, _lang)))) != null) 
                    return li;
            }
        }
        if (ch0 == 'Й' && ch1 == 'О') {
            String word2 = word.substring(0, 0 + word.length() - 2);
            if ((((li = this.find(word2 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = this.find(word2 + "ЫЙ", false, _lang)))) != null) 
                return li;
        }
        if (!tryCreate) 
            return null;
        return null;
    }
    public DerivateDictionary() {
    }
}
