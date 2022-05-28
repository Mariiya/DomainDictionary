/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphEngine {

    public Object m_Lock = new Object();

    private ByteArrayWrapper m_LazyBuf;

    private ByteArrayWrapper getLazyBuf() {
        return m_LazyBuf;
    }

    public MorphTreeNode m_Root = new MorphTreeNode();

    public MorphTreeNode m_RootReverce = new MorphTreeNode();

    private java.util.ArrayList<MorphRule> m_Rules = new java.util.ArrayList<MorphRule>();

    public void addRule(MorphRule r) {
        m_Rules.add(r);
    }

    public MorphRule getRule(int id) {
        if (id > 0 && id <= m_Rules.size()) 
            return m_Rules.get(id - 1);
        return null;
    }

    public MorphRule getMutRule(int id) {
        if (id > 0 && id <= m_Rules.size()) 
            return m_Rules.get(id - 1);
        return null;
    }

    public MorphRuleVariant getRuleVar(int rid, int vid) {
        MorphRule r = this.getRule(rid);
        if (r == null) 
            return null;
        return r.findVar(vid);
    }

    private java.util.ArrayList<com.pullenti.morph.MorphMiscInfo> m_MiscInfos = new java.util.ArrayList<com.pullenti.morph.MorphMiscInfo>();

    public void addMiscInfo(com.pullenti.morph.MorphMiscInfo mi) {
        if (mi.id == 0) 
            mi.id = m_MiscInfos.size() + 1;
        m_MiscInfos.add(mi);
    }

    public com.pullenti.morph.MorphMiscInfo getMiscInfo(int id) {
        if (id > 0 && id <= m_MiscInfos.size()) 
            return m_MiscInfos.get(id - 1);
        return null;
    }

    public boolean initialize(com.pullenti.morph.MorphLang lang, boolean lazyLoad) throws Exception, java.io.IOException {
        if (!language.isUndefined()) 
            return false;
        synchronized (m_Lock) {
            if (!language.isUndefined()) 
                return false;
            language = lang;
            
            String rsname = ("m_" + lang.toString() + ".dat");
            String[] names = com.pullenti.morph.internal.properties.Resources.getNames();
            for (String n : names) {
                if (com.pullenti.unisharp.Utils.endsWithString(n, rsname, true)) {
                    Object inf = com.pullenti.morph.internal.properties.Resources.getResourceInfo(n);
                    if (inf == null) 
                        continue;
                    try (com.pullenti.unisharp.Stream stream = com.pullenti.morph.internal.properties.Resources.getStream(n)) {
                        stream.setPosition(0L);
                        this.deserialize(stream, false, lazyLoad);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private void _loadTreeNode(MorphTreeNode tn) {
        synchronized (m_Lock) {
            int pos = tn.lazyPos;
            if (pos > 0) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos8 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                tn.deserializeLazy(m_LazyBuf, this, wrappos8);
                pos = (wrappos8.value != null ? wrappos8.value : 0);
            }
            tn.lazyPos = 0;
        }
    }

    public com.pullenti.morph.MorphLang language = new com.pullenti.morph.MorphLang();

    public long calcTotalWords() {
        if (m_Root == null) 
            return 0L;
        return this._calcWordsCount(m_Root);
    }

    private long _calcWordsCount(MorphTreeNode tn) {
        if (tn.lazyPos > 0) 
            this._loadTreeNode(tn);
        long res = 0L;
        if (tn.ruleIds != null) {
            for (int id : tn.ruleIds) {
                MorphRule rule = this.getRule(id);
                if (rule != null) {
                    if (rule.morphVars != null) {
                        for (java.util.ArrayList<MorphRuleVariant> v : rule.morphVars) {
                            res += ((long)v.size());
                        }
                    }
                }
            }
        }
        if (tn.nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> kp : tn.nodes.entrySet()) {
                res += this._calcWordsCount(kp.getValue());
            }
        }
        return res;
    }

    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> process(String word, boolean ignoreNoDict) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = null;
        int i;
        if (word.length() > 1) {
            for (i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                    break;
            }
            if (i >= word.length()) 
                return res;
        }
        java.util.ArrayList<MorphRuleVariant> mvs;
        MorphTreeNode tn = m_Root;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.ruleIds != null) {
                String wordBegin = null;
                String wordEnd = null;
                if (i == 0) 
                    wordEnd = word;
                else if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordEnd = "";
                if (res == null) 
                    res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                for (int rid : tn.ruleIds) {
                    MorphRule r = this.getRule(rid);
                    mvs = r.getVars(wordEnd);
                    if (mvs == null) 
                        continue;
                    if (wordBegin == null) {
                        if (i == word.length()) 
                            wordBegin = word;
                        else if (i > 0) 
                            wordBegin = word.substring(0, 0 + i);
                        else 
                            wordBegin = "";
                    }
                    this.processResult(res, wordBegin, mvs);
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn9 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres10 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn9);
            tn = wraptn9.value;
            if (!inoutres10) 
                break;
        }
        boolean needTestUnknownVars = true;
        if (res != null) {
            for (com.pullenti.morph.MorphWordForm r : res) {
                if ((r._getClass().isPronoun() || r._getClass().isNoun() || r._getClass().isAdjective()) || (r._getClass().isMisc() && r._getClass().isConjunction()) || r._getClass().isPreposition()) 
                    needTestUnknownVars = false;
                else if (r._getClass().isAdverb() && r.normalCase != null) {
                    if (!com.pullenti.morph.LanguageHelper.endsWithEx(r.normalCase, "О", "А", null, null)) 
                        needTestUnknownVars = false;
                    else if (com.pullenti.unisharp.Utils.stringsEq(r.normalCase, "МНОГО")) 
                        needTestUnknownVars = false;
                }
                else if (r._getClass().isVerb() && res.size() > 1) {
                    boolean ok = false;
                    for (com.pullenti.morph.MorphWordForm rr : res) {
                        if (rr != r && !rr._getClass().equals(r._getClass())) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok && !com.pullenti.morph.LanguageHelper.endsWith(word, "ИМ")) 
                        needTestUnknownVars = false;
                }
            }
        }
        if (needTestUnknownVars && com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            int gl = 0;
            int sog = 0;
            for (int j = 0; j < word.length(); j++) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(word.charAt(j))) 
                    gl++;
                else 
                    sog++;
            }
            if ((gl < 2) || (sog < 2)) 
                needTestUnknownVars = false;
        }
        if (needTestUnknownVars && res != null && res.size() == 1) {
            if (res.get(0)._getClass().isVerb()) {
                if (res.get(0).misc.getAttrs().contains("н.вр.") && res.get(0).misc.getAttrs().contains("нес.в.") && !res.get(0).misc.getAttrs().contains("страд.з.")) 
                    needTestUnknownVars = false;
                else if (res.get(0).misc.getAttrs().contains("б.вр.") && res.get(0).misc.getAttrs().contains("сов.в.")) 
                    needTestUnknownVars = false;
                else if (res.get(0).misc.getAttrs().contains("инф.") && res.get(0).misc.getAttrs().contains("сов.в.")) 
                    needTestUnknownVars = false;
                else if (res.get(0).normalCase != null && com.pullenti.morph.LanguageHelper.endsWith(res.get(0).normalCase, "СЯ")) 
                    needTestUnknownVars = false;
            }
            if (res.get(0)._getClass().isUndefined() && res.get(0).misc.getAttrs().contains("прдктв.")) 
                needTestUnknownVars = false;
        }
        if (needTestUnknownVars) {
            if (m_RootReverce == null) 
                return res;
            if (ignoreNoDict) 
                return res;
            tn = m_RootReverce;
            MorphTreeNode tn0 = m_RootReverce;
            for (i = word.length() - 1; i >= 0; i--) {
                if (tn.lazyPos > 0) 
                    this._loadTreeNode(tn);
                short ch = (short)word.charAt(i);
                if (tn.nodes == null) 
                    break;
                if (!tn.nodes.containsKey(ch)) 
                    break;
                tn = tn.nodes.get(ch);
                if (tn.lazyPos > 0) 
                    this._loadTreeNode(tn);
                if (tn.reverceVariants != null) {
                    tn0 = tn;
                    break;
                }
            }
            if (tn0 != m_RootReverce) {
                boolean glas = i < 4;
                for (; i >= 0; i--) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(word.charAt(i)) || com.pullenti.morph.LanguageHelper.isLatinVowel(word.charAt(i))) {
                        glas = true;
                        break;
                    }
                }
                if (glas) {
                    for (MorphRuleVariantRef mvref : tn0.reverceVariants) {
                        MorphRuleVariant mv = this.getRuleVar(mvref.ruleId, (int)mvref.variantId);
                        if (mv == null) 
                            continue;
                        if (((!mv._getClass().isVerb() && !mv._getClass().isAdjective() && !mv._getClass().isNoun()) && !mv._getClass().isProperSurname() && !mv._getClass().isProperGeo()) && !mv._getClass().isProperSecname()) 
                            continue;
                        boolean ok = false;
                        for (com.pullenti.morph.MorphWordForm rr : res) {
                            if (rr.isInDictionary()) {
                                if (rr._getClass().equals(mv._getClass()) || rr._getClass().isNoun()) {
                                    ok = true;
                                    break;
                                }
                                if (!mv._getClass().isAdjective() && rr._getClass().isVerb()) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                        if (ok) 
                            continue;
                        if (mv.tail.length() > 0 && !com.pullenti.morph.LanguageHelper.endsWith(word, mv.tail)) 
                            continue;
                        com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(mv, word, this.getMiscInfo((int)mv.miscInfoId));
                        if (!r.hasMorphEquals(res)) {
                            r.undefCoef = mvref.coef;
                            if (res == null) 
                                res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                            res.add(r);
                        }
                    }
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(word, "ПРИ") && res != null) {
            for (i = res.size() - 1; i >= 0; i--) {
                if (res.get(i)._getClass().isProperGeo()) 
                    res.remove(i);
            }
        }
        if (res == null || res.size() == 0) 
            return null;
        this.sort(res, word);
        for (com.pullenti.morph.MorphWordForm v : res) {
            if (v.normalCase == null) 
                v.normalCase = word;
            if (v._getClass().isVerb()) {
                if (v.normalFull == null && com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, "ТЬСЯ")) 
                    v.normalFull = v.normalCase.substring(0, 0 + v.normalCase.length() - 2);
            }
            v.setLanguage(language);
            if (v._getClass().isPreposition()) 
                v.normalCase = com.pullenti.morph.LanguageHelper.normalizePreposition(v.normalCase);
        }
        com.pullenti.morph.MorphClass mc = new com.pullenti.morph.MorphClass();
        for (i = res.size() - 1; i >= 0; i--) {
            if (!res.get(i).isInDictionary() && res.get(i)._getClass().isAdjective() && res.size() > 1) {
                if (res.get(i).misc.getAttrs().contains("к.ф.") || res.get(i).misc.getAttrs().contains("неизм.")) {
                    res.remove(i);
                    continue;
                }
            }
            if (res.get(i).isInDictionary()) 
                mc.value |= res.get(i)._getClass().value;
        }
        if (mc.equals(com.pullenti.morph.MorphClass.VERB) && res.size() > 1) {
            for (com.pullenti.morph.MorphWordForm r : res) {
                if (r.undefCoef > ((short)100) && r._getClass().equals(com.pullenti.morph.MorphClass.ADJECTIVE)) 
                    r.undefCoef = (short)0;
            }
        }
        if (res.size() == 0) 
            return null;
        return res;
    }

    private void processResult(java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, String wordBegin, java.util.ArrayList<MorphRuleVariant> mvs) {
        for (MorphRuleVariant mv : mvs) {
            com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(mv, null, this.getMiscInfo((int)mv.miscInfoId));{
                    if (mv.normalTail != null && mv.normalTail.length() > 0 && mv.normalTail.charAt(0) != '-') 
                        r.normalCase = wordBegin + mv.normalTail;
                    else 
                        r.normalCase = wordBegin;
                }
            if (mv.fullNormalTail != null) {
                if (mv.fullNormalTail.length() > 0 && mv.fullNormalTail.charAt(0) != '-') 
                    r.normalFull = wordBegin + mv.fullNormalTail;
                else 
                    r.normalFull = wordBegin;
            }
            if (!r.hasMorphEquals(res)) {
                r.undefCoef = (short)0;
                res.add(r);
            }
        }
    }

    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> getAllWordforms(String word) {
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
        int i;
        MorphTreeNode tn = m_Root;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.ruleIds != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (int rid : tn.ruleIds) {
                    MorphRule r = this.getRule(rid);
                    if (r.containsVar(wordEnd)) {
                        for (java.util.ArrayList<MorphRuleVariant> vl : r.morphVars) {
                            for (MorphRuleVariant v : vl) {
                                com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm(v, null, this.getMiscInfo((int)v.miscInfoId));
                                if (!wf.hasMorphEquals(res)) {
                                    wf.normalCase = wordBegin + v.tail;
                                    wf.undefCoef = (short)0;
                                    res.add(wf);
                                }
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn11 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres12 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn11);
            tn = wraptn11.value;
            if (!inoutres12) 
                break;
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphWordForm wf = res.get(i);
            if (wf.containsAttr("инф.", null)) 
                continue;
            com.pullenti.morph.MorphCase cas = wf.getCase();
            for (int j = i + 1; j < res.size(); j++) {
                com.pullenti.morph.MorphWordForm wf1 = res.get(j);
                if (wf1.containsAttr("инф.", null)) 
                    continue;
                if ((wf._getClass().equals(wf1._getClass()) && wf.getGender() == wf1.getGender() && wf.getNumber() == wf1.getNumber()) && com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, wf1.normalCase)) {
                    cas = com.pullenti.morph.MorphCase.ooBitor(cas, wf1.getCase());
                    res.remove(j);
                    j--;
                }
            }
            if (!cas.equals(wf.getCase())) 
                res.get(i).setCase(cas);
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphWordForm wf = res.get(i);
            if (wf.containsAttr("инф.", null)) 
                continue;
            for (int j = i + 1; j < res.size(); j++) {
                com.pullenti.morph.MorphWordForm wf1 = res.get(j);
                if (wf1.containsAttr("инф.", null)) 
                    continue;
                if ((wf._getClass().equals(wf1._getClass()) && wf.getCase().equals(wf1.getCase()) && wf.getNumber() == wf1.getNumber()) && com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, wf1.normalCase)) {
                    wf.setGender(com.pullenti.morph.MorphGender.of((short)((wf.getGender().value()) | (wf1.getGender().value()))));
                    res.remove(j);
                    j--;
                }
            }
        }
        return res;
    }

    public String getWordform(String word, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphWordForm addInfo) {
        int i;
        MorphTreeNode tn = m_Root;
        boolean find = false;
        String res = null;
        int maxCoef = -10;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.ruleIds != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (int rid : tn.ruleIds) {
                    MorphRule r = this.getRule(rid);
                    if (r != null && r.containsVar(wordEnd)) {
                        for (java.util.ArrayList<MorphRuleVariant> li : r.morphVars) {
                            for (MorphRuleVariant v : li) {
                                if (((((int)cla.value) & ((int)v._getClass().value))) != 0 && v.normalTail != null) {
                                    if (cas.isUndefined()) {
                                        if (v.getCase().isNominative() || v.getCase().isUndefined()) {
                                        }
                                        else 
                                            continue;
                                    }
                                    else if (((com.pullenti.morph.MorphCase.ooBitand(v.getCase(), cas))).isUndefined()) 
                                        continue;
                                    boolean sur = cla.isProperSurname();
                                    boolean sur0 = v._getClass().isProperSurname();
                                    if (sur || sur0) {
                                        if (sur != sur0) 
                                            continue;
                                    }
                                    find = true;
                                    if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                                        if (((short)((gender.value()) & (v.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                                            }
                                            else 
                                                continue;
                                        }
                                    }
                                    if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                        if (((short)((num.value()) & (v.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                                            continue;
                                    }
                                    String re = wordBegin + v.tail;
                                    int co = 0;
                                    if (addInfo != null) 
                                        co = this._calcEqCoef(v, addInfo);
                                    if (res == null || co > maxCoef) {
                                        res = re;
                                        maxCoef = co;
                                    }
                                    if (maxCoef == 0) {
                                        if (com.pullenti.unisharp.Utils.stringsEq(wordBegin + v.normalTail, word)) 
                                            return wordBegin + v.tail;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn13 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres14 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn13);
            tn = wraptn13.value;
            if (!inoutres14) 
                break;
        }
        if (find) 
            return res;
        tn = m_RootReverce;
        MorphTreeNode tn0 = m_RootReverce;
        for (i = word.length() - 1; i >= 0; i--) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            short ch = (short)word.charAt(i);
            if (tn.nodes == null) 
                break;
            if (!tn.nodes.containsKey(ch)) 
                break;
            tn = tn.nodes.get(ch);
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.reverceVariants != null) {
                tn0 = tn;
                break;
            }
        }
        if (tn0 == m_RootReverce) 
            return null;
        for (MorphRuleVariantRef mvr : tn0.reverceVariants) {
            MorphRule rule = this.getRule(mvr.ruleId);
            if (rule == null) 
                continue;
            MorphRuleVariant mv = rule.findVar((int)mvr.variantId);
            if (mv == null) 
                continue;
            if (((((int)mv._getClass().value) & ((int)cla.value))) != 0) {
                if (mv.tail.length() > 0 && !com.pullenti.morph.LanguageHelper.endsWith(word, mv.tail)) 
                    continue;
                String wordBegin = word.substring(0, 0 + word.length() - mv.tail.length());
                for (java.util.ArrayList<MorphRuleVariant> liv : rule.morphVars) {
                    for (MorphRuleVariant v : liv) {
                        if (((((int)v._getClass().value) & ((int)cla.value))) != 0) {
                            boolean sur = cla.isProperSurname();
                            boolean sur0 = v._getClass().isProperSurname();
                            if (sur || sur0) {
                                if (sur != sur0) 
                                    continue;
                            }
                            if (!cas.isUndefined()) {
                                if (((com.pullenti.morph.MorphCase.ooBitand(cas, v.getCase()))).isUndefined() && !v.getCase().isUndefined()) 
                                    continue;
                            }
                            if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                if (v.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                    if (((short)((v.getNumber().value()) & (num.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                                        continue;
                                }
                            }
                            if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                                if (v.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                                    if (((short)((v.getGender().value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                        continue;
                                }
                            }
                            if (addInfo != null) {
                                if (this._calcEqCoef(v, addInfo) < 0) 
                                    continue;
                            }
                            res = wordBegin + v.tail;
                            if (com.pullenti.unisharp.Utils.stringsEq(res, word)) 
                                return word;
                            return res;
                        }
                    }
                }
            }
        }
        if (cla.isProperSurname()) {
            if ((gender == com.pullenti.morph.MorphGender.FEMINIE && cla.isProperSurname() && !cas.isUndefined()) && !cas.isNominative()) {
                if (word.endsWith("ВА") || word.endsWith("НА")) {
                    if (cas.isAccusative()) 
                        return word.substring(0, 0 + word.length() - 1) + "У";
                    return word.substring(0, 0 + word.length() - 1) + "ОЙ";
                }
            }
            if (gender == com.pullenti.morph.MorphGender.FEMINIE) {
                char last = word.charAt(word.length() - 1);
                if (last == 'А' || last == 'Я' || last == 'О') 
                    return word;
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(last)) 
                    return word.substring(0, 0 + word.length() - 1) + "А";
                else if (last == 'Й') 
                    return word.substring(0, 0 + word.length() - 2) + "АЯ";
                else 
                    return word + "А";
            }
        }
        return res;
    }

    public java.util.ArrayList<String> correctWordByMorph(String word, boolean oneVar) {
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        StringBuilder tmp = new StringBuilder();
        for (int ch = 0; ch < word.length(); ch++) {
            tmp.setLength(0);
            tmp.append(word);
            tmp.setCharAt(ch, '*');
            String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
            if (var != null) {
                if (!vars.contains(var)) 
                    vars.add(var);
            }
        }
        if (vars.size() == 0 || !oneVar) {
            for (int ch = 1; ch < word.length(); ch++) {
                tmp.setLength(0);
                tmp.append(word);
                tmp.insert(ch, '*');
                String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
                if (var != null) {
                    if (!vars.contains(var)) 
                        vars.add(var);
                }
            }
        }
        if (vars.size() == 0 || !oneVar) {
            for (int ch = 0; ch < (word.length() - 1); ch++) {
                tmp.setLength(0);
                tmp.append(word);
                tmp.delete(ch, ch + 1);
                String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
                if (var != null) {
                    if (!vars.contains(var)) 
                        vars.add(var);
                }
            }
        }
        if (vars.size() == 0) 
            return null;
        return vars;
    }

    private String _checkCorrVar(String word, MorphTreeNode tn, int i) {
        for (; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.ruleIds != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (int rid : tn.ruleIds) {
                    MorphRule r = this.getRule(rid);
                    if (r.containsVar(wordEnd)) 
                        return wordBegin + wordEnd;
                    if (wordEnd.indexOf('*') >= 0) {
                        for (String v : r.tails) {
                            if (v.length() == wordEnd.length()) {
                                int j;
                                for (j = 0; j < v.length(); j++) {
                                    if (wordEnd.charAt(j) == '*' || wordEnd.charAt(j) == v.charAt(j)) {
                                    }
                                    else 
                                        break;
                                }
                                if (j >= v.length()) 
                                    return wordBegin + v;
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            if (ch != ((short)0x2A)) {
                if (!tn.nodes.containsKey(ch)) 
                    break;
                tn = tn.nodes.get(ch);
                continue;
            }
            if (tn.nodes != null) {
                for (java.util.Map.Entry<Short, MorphTreeNode> tnn : tn.nodes.entrySet()) {
                    String ww = word.replace('*', (char)(short)tnn.getKey());
                    String res = this._checkCorrVar(ww, tnn.getValue(), i + 1);
                    if (res != null) 
                        return res;
                }
            }
            break;
        }
        return null;
    }

    public void processSurnameVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res) {
        this.processProperVariants(word, res, false);
    }

    public void processGeoVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res) {
        this.processProperVariants(word, res, true);
    }

    private void processProperVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, boolean geo) {
        MorphTreeNode tn = m_RootReverce;
        java.util.ArrayList<MorphTreeNode> nodesWithVars = null;
        int i;
        for (i = word.length() - 1; i >= 0; i--) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            short ch = (short)word.charAt(i);
            if (tn.nodes == null) 
                break;
            if (!tn.nodes.containsKey(ch)) 
                break;
            tn = tn.nodes.get(ch);
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.reverceVariants != null) {
                if (nodesWithVars == null) 
                    nodesWithVars = new java.util.ArrayList<MorphTreeNode>();
                nodesWithVars.add(tn);
            }
        }
        if (nodesWithVars == null) 
            return;
        for (int j = nodesWithVars.size() - 1; j >= 0; j--) {
            tn = nodesWithVars.get(j);
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            boolean ok = false;
            for (MorphRuleVariantRef vr : tn.reverceVariants) {
                MorphRuleVariant v = this.getRuleVar(vr.ruleId, (int)vr.variantId);
                if (v == null) 
                    continue;
                if (geo && v._getClass().isProperGeo()) {
                }
                else if (!geo && v._getClass().isProperSurname()) {
                }
                else 
                    continue;
                com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(v, word, this.getMiscInfo((int)v.miscInfoId));
                if (!r.hasMorphEquals(res)) {
                    r.undefCoef = vr.coef;
                    res.add(r);
                }
                ok = true;
            }
            if (ok) 
                break;
        }
    }

    private int _compare(com.pullenti.morph.MorphWordForm x, com.pullenti.morph.MorphWordForm y) {
        if (x.isInDictionary() && !y.isInDictionary()) 
            return -1;
        if (!x.isInDictionary() && y.isInDictionary()) 
            return 1;
        if (x.undefCoef > ((short)0)) {
            if (x.undefCoef > (((int)y.undefCoef) * 2)) 
                return -1;
            if ((((int)x.undefCoef) * 2) < y.undefCoef) 
                return 1;
        }
        if (!x._getClass().equals(y._getClass())) {
            if ((x._getClass().isPreposition() || x._getClass().isConjunction() || x._getClass().isPronoun()) || x._getClass().isPersonalPronoun()) 
                return -1;
            if ((y._getClass().isPreposition() || y._getClass().isConjunction() || y._getClass().isPronoun()) || y._getClass().isPersonalPronoun()) 
                return 1;
            if (x._getClass().isVerb()) 
                return 1;
            if (y._getClass().isVerb()) 
                return -1;
            if (x._getClass().isNoun()) 
                return -1;
            if (y._getClass().isNoun()) 
                return 1;
        }
        int cx = this._calcCoef(x);
        int cy = this._calcCoef(y);
        if (cx > cy) 
            return -1;
        if (cx < cy) 
            return 1;
        if (x.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && y.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
            return 1;
        if (y.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && x.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
            return -1;
        return 0;
    }

    private int _calcCoef(com.pullenti.morph.MorphWordForm wf) {
        int k = 0;
        if (!wf.getCase().isUndefined()) 
            k++;
        if (wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
            k++;
        if (wf.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) 
            k++;
        if (wf.misc.isSynonymForm()) 
            k -= 3;
        if (wf.normalCase == null || (wf.normalCase.length() < 4)) 
            return k;
        if (wf._getClass().isAdjective() && wf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
            char last = wf.normalCase.charAt(wf.normalCase.length() - 1);
            char last1 = wf.normalCase.charAt(wf.normalCase.length() - 2);
            boolean ok = false;
            if (wf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                if (last == 'Я') 
                    ok = true;
            }
            if (wf.getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                if (last == 'Й') {
                    if (last1 == 'И') 
                        k++;
                    ok = true;
                }
            }
            if (wf.getGender() == com.pullenti.morph.MorphGender.NEUTER) {
                if (last == 'Е') 
                    ok = true;
            }
            if (ok) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(last1)) 
                    k++;
            }
        }
        else if (wf._getClass().isAdjective() && wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            char last = wf.normalCase.charAt(wf.normalCase.length() - 1);
            char last1 = wf.normalCase.charAt(wf.normalCase.length() - 2);
            if (last == 'Й' || last == 'Е') 
                k++;
        }
        return k;
    }

    private int _calcEqCoef(MorphRuleVariant v, com.pullenti.morph.MorphWordForm wf) {
        if (wf._getClass().value != ((short)0)) {
            if (((((int)v._getClass().value) & ((int)wf._getClass().value))) == 0) 
                return -1;
        }
        if (v.miscInfoId != wf.misc.id) {
            com.pullenti.morph.MorphMiscInfo vi = this.getMiscInfo((int)v.miscInfoId);
            if (vi.getMood() != com.pullenti.morph.MorphMood.UNDEFINED && wf.misc.getMood() != com.pullenti.morph.MorphMood.UNDEFINED) {
                if (vi.getMood() != wf.misc.getMood()) 
                    return -1;
            }
            if (vi.getTense() != com.pullenti.morph.MorphTense.UNDEFINED && wf.misc.getTense() != com.pullenti.morph.MorphTense.UNDEFINED) {
                if (((short)((vi.getTense().value()) & (wf.misc.getTense().value()))) == (com.pullenti.morph.MorphTense.UNDEFINED.value())) 
                    return -1;
            }
            if (vi.getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED && wf.misc.getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED) {
                if (vi.getVoice() != wf.misc.getVoice()) 
                    return -1;
            }
            if (vi.getPerson() != com.pullenti.morph.MorphPerson.UNDEFINED && wf.misc.getPerson() != com.pullenti.morph.MorphPerson.UNDEFINED) {
                if (((short)((vi.getPerson().value()) & (wf.misc.getPerson().value()))) == (com.pullenti.morph.MorphPerson.UNDEFINED.value())) 
                    return -1;
            }
            return 0;
        }
        if (!v.checkAccord(wf, false, false)) 
            return -1;
        return 1;
    }

    private void sort(java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, String word) {
        if (res == null || (res.size() < 2)) 
            return;
        for (int k = 0; k < res.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (res.size() - 1); i++) {
                int j = this._compare(res.get(i), res.get(i + 1));
                if (j > 0) {
                    com.pullenti.morph.MorphWordForm r1 = res.get(i);
                    com.pullenti.morph.MorphWordForm r2 = res.get(i + 1);
                    com.pullenti.unisharp.Utils.putArrayValue(res, i, r2);
                    com.pullenti.unisharp.Utils.putArrayValue(res, i + 1, r1);
                    ch = true;
                }
            }
            if (!ch) 
                break;
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            for (int j = i + 1; j < res.size(); j++) {
                if (this.comp1(res.get(i), res.get(j))) {
                    if ((res.get(i)._getClass().isAdjective() && res.get(j)._getClass().isNoun() && !res.get(j).isInDictionary()) && !res.get(i).isInDictionary()) 
                        res.remove(j);
                    else if ((res.get(i)._getClass().isNoun() && res.get(j)._getClass().isAdjective() && !res.get(j).isInDictionary()) && !res.get(i).isInDictionary()) 
                        res.remove(i);
                    else if (res.get(i)._getClass().isAdjective() && res.get(j)._getClass().isPronoun()) 
                        res.remove(i);
                    else if (res.get(i)._getClass().isPronoun() && res.get(j)._getClass().isAdjective()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(res.get(j).normalFull, "ОДИН") || com.pullenti.unisharp.Utils.stringsEq(res.get(j).normalCase, "ОДИН")) 
                            continue;
                        res.remove(j);
                    }
                    else 
                        continue;
                    i--;
                    break;
                }
            }
        }
    }

    private boolean comp1(com.pullenti.morph.MorphWordForm r1, com.pullenti.morph.MorphWordForm r2) {
        if (r1.getNumber() != r2.getNumber() || r1.getGender() != r2.getGender()) 
            return false;
        if (!r1.getCase().equals(r2.getCase())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(r1.normalCase, r2.normalCase)) 
            return false;
        return true;
    }

    public void deserialize(com.pullenti.unisharp.Stream str0, boolean ignoreRevTree, boolean lazyLoad) throws java.io.IOException, Exception {
        com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream();
        MorphDeserializer.deflateGzip(str0, tmp);
        byte[] arr = tmp.toByteArray();
        ByteArrayWrapper buf = new ByteArrayWrapper(arr);
        int pos = 0;
        com.pullenti.unisharp.Outargwrapper<Integer> wrappos23 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
        int cou = buf.deserializeInt(wrappos23);
        pos = (wrappos23.value != null ? wrappos23.value : 0);
        for (; cou > 0; cou--) {
            com.pullenti.morph.MorphMiscInfo mi = new com.pullenti.morph.MorphMiscInfo();
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos15 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            mi.deserialize(buf, wrappos15);
            pos = (wrappos15.value != null ? wrappos15.value : 0);
            this.addMiscInfo(mi);
        }
        com.pullenti.unisharp.Outargwrapper<Integer> wrappos22 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
        cou = buf.deserializeInt(wrappos22);
        pos = (wrappos22.value != null ? wrappos22.value : 0);
        for (; cou > 0; cou--) {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos17 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            int p1 = buf.deserializeInt(wrappos17);
            pos = (wrappos17.value != null ? wrappos17.value : 0);
            MorphRule r = new MorphRule();
            if (lazyLoad) {
                r.lazyPos = pos;
                pos = p1;
            }
            else {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos16 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                r.deserialize(buf, wrappos16);
                pos = (wrappos16.value != null ? wrappos16.value : 0);
            }
            this.addRule(r);
        }
        MorphTreeNode root = new MorphTreeNode();
        if (lazyLoad) {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos18 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            root.deserializeLazy(buf, this, wrappos18);
            pos = (wrappos18.value != null ? wrappos18.value : 0);
        }
        else {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos19 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            root.deserialize(buf, wrappos19);
            pos = (wrappos19.value != null ? wrappos19.value : 0);
        }
        m_Root = root;
        if (!ignoreRevTree) {
            MorphTreeNode rootRev = new MorphTreeNode();
            if (lazyLoad) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos20 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                rootRev.deserializeLazy(buf, this, wrappos20);
                pos = (wrappos20.value != null ? wrappos20.value : 0);
            }
            else {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos21 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                rootRev.deserialize(buf, wrappos21);
                pos = (wrappos21.value != null ? wrappos21.value : 0);
            }
            m_RootReverce = rootRev;
        }
        tmp.close();
        if (lazyLoad) 
            m_LazyBuf = buf;
    }
    public MorphEngine() {
    }
}
