/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.phone;

/**
 * Анализатор для выделения телефонных номеров
 */
public class PhoneAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("PHONE")
     */
    public static final String ANALYZER_NAME = "PHONE";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Телефоны";
    }


    @Override
    public String getDescription() {
        return "Телефонные номера";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new PhoneAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.phone.internal.MetaPhone.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.phone.internal.MetaPhone.PHONEIMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("phone.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, PhoneReferent.OBJ_TYPENAME)) 
            return new PhoneReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 2;
    }


    public static class PhoneAnalizerData extends com.pullenti.ner.core.AnalyzerData {
    
        private java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent>> m_PhonesHash = new java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent>>();
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            com.pullenti.ner.phone.PhoneReferent _phone = (com.pullenti.ner.phone.PhoneReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.phone.PhoneReferent.class);
            if (_phone == null) 
                return null;
            String key = _phone.getNumber();
            if (key.length() >= 10) 
                key = key.substring(3);
            java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent> phLi;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent>> wrapphLi2859 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent>>();
            boolean inoutres2860 = com.pullenti.unisharp.Utils.tryGetValue(m_PhonesHash, key, wrapphLi2859);
            phLi = wrapphLi2859.value;
            if (!inoutres2860) 
                m_PhonesHash.put(key, (phLi = new java.util.ArrayList<com.pullenti.ner.phone.PhoneReferent>()));
            for (com.pullenti.ner.phone.PhoneReferent p : phLi) {
                if (p.canBeEquals(_phone, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                    p.mergeSlots(_phone, true);
                    return p;
                }
            }
            phLi.add(_phone);
            m_Referents.add(_phone);
            return _phone;
        }
        public PhoneAnalizerData() {
            super();
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new PhoneAnalizerData();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        PhoneAnalizerData ad = (PhoneAnalizerData)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), PhoneAnalizerData.class);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            java.util.ArrayList<com.pullenti.ner.phone.internal.PhoneItemToken> pli = com.pullenti.ner.phone.internal.PhoneItemToken.tryAttachAll(t, 15);
            if (pli == null || pli.size() == 0) 
                continue;
            PhoneReferent prevPhone = null;
            int kkk = 0;
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.getReferent() instanceof PhoneReferent) {
                    prevPhone = (PhoneReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), PhoneReferent.class);
                    break;
                }
                else if (tt instanceof com.pullenti.ner.ReferentToken) {
                }
                else if (tt.isChar(')')) {
                    com.pullenti.ner.Token ttt = tt.getPrevious();
                    int cou = 0;
                    for (; ttt != null; ttt = ttt.getPrevious()) {
                        if (ttt.isChar('(')) 
                            break;
                        else if ((++cou) > 100) 
                            break;
                    }
                    if (ttt == null || !ttt.isChar('(')) 
                        break;
                    tt = ttt;
                }
                else if (!tt.isCharOf(",;/\\") && !tt.isAnd()) {
                    if ((++kkk) > 5) 
                        break;
                    if (tt.isNewlineBefore() || tt.isNewlineAfter()) 
                        break;
                }
            }
            int j = 0;
            boolean isPhoneBefore = false;
            boolean isPref = false;
            PhoneKind ki = PhoneKind.UNDEFINED;
            PhoneKind ki2 = PhoneKind.UNDEFINED;
            while (j < pli.size()) {
                if (pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX) {
                    if (ki == PhoneKind.UNDEFINED) 
                        ki = pli.get(j).kind;
                    isPref = true;
                    if (ki2 == PhoneKind.UNDEFINED) 
                        ki2 = pli.get(j).kind2;
                    isPhoneBefore = true;
                    j++;
                    if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) 
                        j++;
                }
                else if (((j + 1) < pli.size()) && pli.get(j + 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && j == 0) {
                    if (ki == PhoneKind.UNDEFINED) 
                        ki = pli.get(0).kind;
                    isPref = true;
                    if (ki2 == PhoneKind.UNDEFINED) 
                        ki2 = pli.get(j).kind2;
                    pli.remove(0);
                }
                else 
                    break;
            }
            if (prevPhone != null) 
                isPhoneBefore = true;
            if (pli.size() == 1 && pli.get(0).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
                com.pullenti.ner.Token tt = t.getPrevious();
                if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isLetter()) 
                    tt = tt.getPrevious();
                if (tt instanceof com.pullenti.ner.TextToken) {
                    if (com.pullenti.ner.uri.UriAnalyzer.m_Schemes.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                        continue;
                }
            }
            java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this.tryAttach(pli, j, isPhoneBefore, prevPhone);
            if (rts == null) {
                for (j = 1; j < pli.size(); j++) {
                    if (pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX) {
                        for(int jjj = 0 + j - 1, mmm = 0; jjj >= mmm; jjj--) pli.remove(jjj);
                        rts = this.tryAttach(pli, 1, true, prevPhone);
                        break;
                    }
                }
            }
            if (rts == null) 
                t = pli.get(pli.size() - 1).getEndToken();
            else {
                if (ki == PhoneKind.UNDEFINED && prevPhone != null) {
                    if (prevPhone.tag instanceof PhoneKind) 
                        ki = (PhoneKind)prevPhone.tag;
                    else if (!isPref && prevPhone.getKind() != PhoneKind.MOBILE && kkk == 0) 
                        ki = prevPhone.getKind();
                }
                for (com.pullenti.ner.ReferentToken rt : rts) {
                    PhoneReferent ph = (PhoneReferent)com.pullenti.unisharp.Utils.cast(rt.referent, PhoneReferent.class);
                    if (ki2 != PhoneKind.UNDEFINED) {
                        if (rt == rts.get(0)) 
                            ph.tag = ki2;
                        else 
                            ph.setKind(ki2);
                    }
                    else if (ki != PhoneKind.UNDEFINED) 
                        ph.setKind(ki);
                    else {
                        if (rt == rts.get(0) && (rt.getWhitespacesBeforeCount() < 3)) {
                            com.pullenti.ner.Token tt1 = rt.getBeginToken().getPrevious();
                            if (tt1 != null && tt1.isTableControlChar()) 
                                tt1 = tt1.getPrevious();
                            if ((tt1 instanceof com.pullenti.ner.TextToken) && ((tt1.isNewlineBefore() || ((tt1.getPrevious() != null && tt1.getPrevious().isTableControlChar()))))) {
                                String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term;
                                if (com.pullenti.unisharp.Utils.stringsEq(term, "T") || com.pullenti.unisharp.Utils.stringsEq(term, "Т")) 
                                    rt.setBeginToken(tt1);
                                else if (com.pullenti.unisharp.Utils.stringsEq(term, "Ф") || com.pullenti.unisharp.Utils.stringsEq(term, "F")) {
                                    ph.setKind((ki = PhoneKind.FAX));
                                    rt.setBeginToken(tt1);
                                }
                                else if (com.pullenti.unisharp.Utils.stringsEq(term, "M") || com.pullenti.unisharp.Utils.stringsEq(term, "М")) {
                                    ph.setKind((ki = PhoneKind.MOBILE));
                                    rt.setBeginToken(tt1);
                                }
                            }
                        }
                        ph.correct();
                    }
                    rt.referent = ad.registerReferent(rt.referent);
                    kit.embedToken(rt);
                    t = rt;
                }
            }
        }
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttach(java.util.ArrayList<com.pullenti.ner.phone.internal.PhoneItemToken> pli, int ind, boolean isPhoneBefore, PhoneReferent prevPhone) {
        com.pullenti.ner.ReferentToken rt = this._TryAttach_(pli, ind, isPhoneBefore, prevPhone, 0);
        if (rt == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(rt);
        for (int i = 0; i < 5; i++) {
            PhoneReferent ph0 = (PhoneReferent)com.pullenti.unisharp.Utils.cast(rt.referent, PhoneReferent.class);
            if (ph0.getAddNumber() != null) 
                return res;
            com.pullenti.ner.phone.internal.PhoneItemToken alt = com.pullenti.ner.phone.internal.PhoneItemToken.tryAttachAlternate(rt.getEndToken().getNext(), ph0, pli);
            if (alt == null) 
                break;
            PhoneReferent ph = new PhoneReferent();
            for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                ph.addSlot(s.getTypeName(), s.getValue(), false, 0);
            }
            String num = ph.getNumber();
            if (num == null || num.length() <= alt.value.length()) 
                break;
            ph.setNumber(num.substring(0, 0 + num.length() - alt.value.length()) + alt.value);
            ph.m_Template = ph0.m_Template;
            com.pullenti.ner.ReferentToken rt2 = new com.pullenti.ner.ReferentToken(ph, alt.getBeginToken(), alt.getEndToken(), null);
            res.add(rt2);
            rt = rt2;
        }
        com.pullenti.ner.phone.internal.PhoneItemToken add = com.pullenti.ner.phone.internal.PhoneItemToken.tryAttachAdditional(rt.getEndToken().getNext());
        if (add != null) {
            for (com.pullenti.ner.ReferentToken rr : res) {
                ((PhoneReferent)com.pullenti.unisharp.Utils.cast(rr.referent, PhoneReferent.class)).setAddNumber(add.value);
            }
            res.get(res.size() - 1).setEndToken(add.getEndToken());
        }
        return res;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        java.util.ArrayList<com.pullenti.ner.phone.internal.PhoneItemToken> pli = com.pullenti.ner.phone.internal.PhoneItemToken.tryAttachAll(begin, 15);
        if (pli == null || pli.size() == 0) 
            return null;
        int i = 0;
        for (; i < pli.size(); i++) {
            if (pli.get(i).itemType != com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX) 
                break;
        }
        com.pullenti.ner.ReferentToken rt = this._TryAttach_(pli, i, true, null, 0);
        if (rt != null) {
            rt.setBeginToken(begin);
            return rt;
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _TryAttach_(java.util.ArrayList<com.pullenti.ner.phone.internal.PhoneItemToken> pli, int ind, boolean isPhoneBefore, PhoneReferent prevPhone, int lev) {
        if (ind >= pli.size() || lev > 4) 
            return null;
        String countryCode = null;
        String cityCode = null;
        int j = ind;
        if (prevPhone != null && prevPhone.m_Template != null && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
            StringBuilder tmp = new StringBuilder();
            for (int jj = j; jj < pli.size(); jj++) {
                if (pli.get(jj).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) 
                    tmp.append(pli.get(jj).value.length());
                else if (pli.get(jj).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) {
                    if (com.pullenti.unisharp.Utils.stringsEq(pli.get(jj).value, " ")) 
                        break;
                    tmp.append(pli.get(jj).value);
                    continue;
                }
                else 
                    break;
                String templ0 = tmp.toString();
                if (com.pullenti.unisharp.Utils.stringsEq(templ0, prevPhone.m_Template)) {
                    if ((jj + 1) < pli.size()) {
                        if (pli.get(jj + 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && (jj + 2) == pli.size()) {
                        }
                        else 
                            for(int jjj = jj + 1 + pli.size() - jj - 1 - 1, mmm = jj + 1; jjj >= mmm; jjj--) pli.remove(jjj);
                    }
                    break;
                }
            }
        }
        if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.COUNTRYCODE) {
            countryCode = pli.get(j).value;
            if (com.pullenti.unisharp.Utils.stringsNe(countryCode, "8")) {
                String cc = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(countryCode);
                if (cc != null && (cc.length() < countryCode.length())) {
                    cityCode = countryCode.substring(cc.length());
                    countryCode = cc;
                }
            }
            j++;
        }
        else if ((j < pli.size()) && pli.get(j).canBeCountryPrefix()) {
            int k = j + 1;
            if ((k < pli.size()) && pli.get(k).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) 
                k++;
            com.pullenti.ner.ReferentToken rrt = this._TryAttach_(pli, k, isPhoneBefore, null, lev + 1);
            if (rrt != null) {
                if ((((isPhoneBefore && pli.get(j + 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM && pli.get(j + 1).getBeginToken().isHiphen()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER && pli.get(j).value.length() == 3) && ((j + 2) < pli.size()) && pli.get(j + 2).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) && pli.get(j + 2).value.length() == 3) {
                }
                else {
                    countryCode = pli.get(j).value;
                    j++;
                }
            }
        }
        if (((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER && ((pli.get(j).value.charAt(0) == '8' || pli.get(j).value.charAt(0) == '7'))) && countryCode == null) {
            if (pli.get(j).value.length() == 1) {
                countryCode = pli.get(j).value;
                j++;
            }
            else if (pli.get(j).value.length() == 4) {
                countryCode = pli.get(j).value.substring(0, 0 + 1);
                if (cityCode == null) 
                    cityCode = pli.get(j).value.substring(1);
                else 
                    cityCode += pli.get(j).value.substring(1);
                j++;
            }
            else if (pli.get(j).value.length() == 11 && j == (pli.size() - 1) && isPhoneBefore) {
                PhoneReferent ph0 = new PhoneReferent();
                if (pli.get(j).value.charAt(0) != '8') 
                    ph0.setCountryCode(pli.get(j).value.substring(0, 0 + 1));
                ph0.setNumber(pli.get(j).value.substring(1, 1 + 3) + pli.get(j).value.substring(4));
                return new com.pullenti.ner.ReferentToken(ph0, pli.get(0).getBeginToken(), pli.get(j).getEndToken(), null);
            }
            else if (cityCode == null && pli.get(j).value.length() > 3 && ((j + 1) < pli.size())) {
                int sum = 0;
                for (com.pullenti.ner.phone.internal.PhoneItemToken it : pli) {
                    if (it.itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) 
                        sum += it.value.length();
                }
                if (sum == 11) {
                    cityCode = pli.get(j).value.substring(1);
                    j++;
                }
            }
        }
        if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.CITYCODE) {
            if (cityCode == null) 
                cityCode = pli.get(j).value;
            else 
                cityCode += pli.get(j).value;
            j++;
        }
        if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) 
            j++;
        if ((com.pullenti.unisharp.Utils.stringsEq(countryCode, "8") && cityCode == null && ((j + 3) < pli.size())) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
            if (pli.get(j).value.length() == 3 || pli.get(j).value.length() == 4) {
                cityCode = pli.get(j).value;
                j++;
                if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) 
                    j++;
            }
        }
        int normalNumLen = 0;
        if (com.pullenti.unisharp.Utils.stringsEq(countryCode, "421")) 
            normalNumLen = 9;
        StringBuilder num = new StringBuilder();
        StringBuilder templ = new StringBuilder();
        java.util.ArrayList<Integer> partLength = new java.util.ArrayList<Integer>();
        String delim = null;
        boolean ok = false;
        String additional = null;
        boolean std = false;
        if (countryCode != null && ((j + 4) < pli.size()) && j > 0) {
            if (((((com.pullenti.unisharp.Utils.stringsEq(pli.get(j - 1).value, "-") || pli.get(j - 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.COUNTRYCODE)) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER && pli.get(j + 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) && pli.get(j + 2).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER && pli.get(j + 3).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) && pli.get(j + 4).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
                if ((((pli.get(j).value.length() + pli.get(j + 2).value.length()) == 6 || ((pli.get(j).value.length() == 4 && pli.get(j + 2).value.length() == 5)))) && ((pli.get(j + 4).value.length() == 4 || pli.get(j + 4).value.length() == 1))) {
                    num.append(pli.get(j).value);
                    num.append(pli.get(j + 2).value);
                    num.append(pli.get(j + 4).value);
                    templ.append(pli.get(j).value.length()).append(pli.get(j + 1).value).append(pli.get(j + 2).value.length()).append(pli.get(j + 3).value).append(pli.get(j + 4).value.length());
                    std = true;
                    ok = true;
                    j += 5;
                }
            }
        }
        for (; j < pli.size(); j++) {
            if (std) 
                break;
            if (pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) {
                if (pli.get(j).isInBrackets) 
                    continue;
                if (j > 0 && pli.get(j - 1).isInBrackets) 
                    continue;
                if (templ.length() > 0) 
                    templ.append(pli.get(j).value);
                if (delim == null) 
                    delim = pli.get(j).value;
                else if (com.pullenti.unisharp.Utils.stringsNe(pli.get(j).value, delim)) {
                    if ((partLength.size() == 2 && ((partLength.get(0) == 3 || partLength.get(0) == 4)) && cityCode == null) && partLength.get(1) == 3) {
                        cityCode = num.toString().substring(0, 0 + partLength.get(0));
                        num.delete(0, 0 + partLength.get(0));
                        partLength.remove(0);
                        delim = pli.get(j).value;
                        continue;
                    }
                    if (isPhoneBefore && ((j + 1) < pli.size()) && pli.get(j + 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
                        if (num.length() < 6) 
                            continue;
                        if (normalNumLen > 0 && (num.length() + pli.get(j + 1).value.length()) == normalNumLen) 
                            continue;
                    }
                    break;
                }
                else 
                    continue;
                ok = false;
            }
            else if (pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.NUMBER) {
                if (num.length() == 0 && pli.get(j).getBeginToken().getPrevious() != null && pli.get(j).getBeginToken().getPrevious().isTableControlChar()) {
                    com.pullenti.ner.Token tt = pli.get(pli.size() - 1).getEndToken().getNext();
                    if (tt != null && tt.isCharOf(",.")) 
                        tt = tt.getNext();
                    if (tt instanceof com.pullenti.ner.NumberToken) 
                        return null;
                }
                if ((num.length() + pli.get(j).value.length()) > 13) {
                    if (j > 0 && pli.get(j - 1).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.DELIM) 
                        j--;
                    ok = true;
                    break;
                }
                num.append(pli.get(j).value);
                partLength.add(pli.get(j).value.length());
                templ.append(pli.get(j).value.length());
                ok = true;
                if (num.length() > 10) {
                    j++;
                    if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.ADDNUMBER) {
                        additional = pli.get(j).value;
                        j++;
                    }
                    break;
                }
            }
            else if (pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.ADDNUMBER) {
                additional = pli.get(j).value;
                j++;
                break;
            }
            else 
                break;
        }
        if ((j == (pli.size() - 1) && pli.get(j).isInBrackets && ((pli.get(j).value.length() == 3 || pli.get(j).value.length() == 4))) && additional == null) {
            additional = pli.get(j).value;
            j++;
        }
        if ((j < pli.size()) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && pli.get(j).isInBrackets) {
            isPhoneBefore = true;
            j++;
        }
        if ((countryCode == null && cityCode != null && cityCode.length() > 3) && (num.length() < 8) && cityCode.charAt(0) != '8') {
            if ((cityCode.length() + num.length()) == 10) {
            }
            else {
                String cc = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(cityCode);
                if (cc != null) {
                    if (cc.length() > 1 && (cityCode.length() - cc.length()) > 1) {
                        countryCode = cc;
                        cityCode = cityCode.substring(cc.length());
                    }
                }
            }
        }
        if (countryCode == null && cityCode != null && cityCode.startsWith("00")) {
            String cc = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(cityCode.substring(2));
            if (cc != null) {
                if (cityCode.length() > (cc.length() + 3)) {
                    countryCode = cc;
                    cityCode = cityCode.substring(cc.length() + 2);
                }
            }
        }
        if (num.length() == 0 && cityCode != null) {
            if (cityCode.length() == 10) {
                num.append(cityCode.substring(3));
                partLength.add(num.length());
                cityCode = cityCode.substring(0, 0 + 3);
                ok = true;
            }
            else if (((cityCode.length() == 9 || cityCode.length() == 11 || cityCode.length() == 8)) && ((isPhoneBefore || countryCode != null))) {
                num.append(cityCode);
                partLength.add(num.length());
                cityCode = null;
                ok = true;
            }
        }
        if (num.length() < 4) 
            ok = false;
        if (num.length() < 7) {
            if (cityCode != null && (cityCode.length() + num.length()) > 7) {
                if (!isPhoneBefore && cityCode.length() == 3) {
                    int ii;
                    for (ii = 0; ii < partLength.size(); ii++) {
                        if (partLength.get(ii) == 3) {
                        }
                        else if (partLength.get(ii) > 3) 
                            break;
                        else if ((ii < (partLength.size() - 1)) || (partLength.get(ii) < 2)) 
                            break;
                    }
                    if (ii >= partLength.size()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(countryCode, "61")) {
                        }
                        else 
                            ok = false;
                    }
                }
            }
            else if (((num.length() == 6 || num.length() == 5)) && ((partLength.size() >= 1 && partLength.size() <= 3)) && isPhoneBefore) {
                if (pli.get(0).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && pli.get(0).kind == PhoneKind.HOME) 
                    ok = false;
            }
            else if (prevPhone != null && prevPhone.getNumber() != null && ((prevPhone.getNumber().length() == num.length() || prevPhone.getNumber().length() == (num.length() + 3) || prevPhone.getNumber().length() == (num.length() + 4)))) {
            }
            else if (num.length() > 4 && prevPhone != null && com.pullenti.unisharp.Utils.stringsEq(templ.toString(), prevPhone.m_Template)) 
                ok = true;
            else 
                ok = false;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(delim, ".") && countryCode == null && cityCode == null) 
            ok = false;
        if ((isPhoneBefore && countryCode == null && cityCode == null) && num.length() > 10) {
            String cc = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(num.toString());
            if (cc != null) {
                if ((num.length() - cc.length()) == 9) {
                    countryCode = cc;
                    num.delete(0, 0 + cc.length());
                    ok = true;
                }
            }
        }
        if (ok) {
            if (std) {
            }
            else if (prevPhone != null && prevPhone.getNumber() != null && (((prevPhone.getNumber().length() == num.length() || prevPhone.getNumber().length() == (num.length() + 3) || prevPhone.getNumber().length() == (num.length() + 4)) || com.pullenti.unisharp.Utils.stringsEq(prevPhone.m_Template, templ.toString())))) {
            }
            else if ((partLength.size() == 3 && partLength.get(0) == 3 && partLength.get(1) == 2) && partLength.get(2) == 2) {
            }
            else if (partLength.size() == 3 && isPhoneBefore) {
            }
            else if ((partLength.size() == 4 && ((partLength.get(0) + partLength.get(1)) == 3) && partLength.get(2) == 2) && partLength.get(3) == 2) {
            }
            else if ((partLength.size() == 4 && partLength.get(0) == 3 && partLength.get(1) == 3) && partLength.get(2) == 2 && partLength.get(3) == 2) {
            }
            else if (partLength.size() == 5 && (partLength.get(1) + partLength.get(2)) == 4 && (partLength.get(3) + partLength.get(4)) == 4) {
            }
            else if (partLength.size() > 4) 
                ok = false;
            else if (partLength.size() > 3 && cityCode != null) 
                ok = false;
            else if ((isPhoneBefore || cityCode != null || countryCode != null) || additional != null) 
                ok = true;
            else {
                ok = false;
                if (((num.length() == 6 || num.length() == 7)) && (partLength.size() < 4) && j > 0) {
                    PhoneReferent nextPh = this.getNextPhone(pli.get(j - 1).getEndToken().getNext(), lev + 1);
                    if (nextPh != null) {
                        int d = nextPh.getNumber().length() - num.length();
                        if (d == 0 || d == 3 || d == 4) 
                            ok = true;
                    }
                }
            }
        }
        com.pullenti.ner.Token end = (j > 0 ? pli.get(j - 1).getEndToken() : null);
        if (end == null) 
            ok = false;
        if ((ok && cityCode == null && countryCode == null) && prevPhone == null && !isPhoneBefore) {
            if (!end.isWhitespaceAfter() && end.getNext() != null) {
                com.pullenti.ner.Token tt = end.getNext();
                if (tt.isCharOf(".,)") && tt.getNext() != null) 
                    tt = tt.getNext();
                if (!tt.isWhitespaceBefore()) 
                    ok = false;
            }
        }
        if (ok) {
            String stempl = templ.toString().trim();
            if (com.pullenti.unisharp.Utils.stringsEq(stempl, "4 3.4") || ((((com.pullenti.unisharp.Utils.stringsEq(stempl, "2.4") || com.pullenti.unisharp.Utils.stringsEq(stempl, "3.4"))) && pli.get(0).getLengthChar() == 4))) {
                if (pli.get(0).getBeginToken().getPrevious() != null && pli.get(0).getBeginToken().getPrevious().isChar('.') && (pli.get(0).getBeginToken().getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken)) 
                    ok = false;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(stempl, "4 3,4") || ((((com.pullenti.unisharp.Utils.stringsEq(stempl, "2,4") || com.pullenti.unisharp.Utils.stringsEq(stempl, "3,4"))) && pli.get(0).getLengthChar() == 4))) {
                if (pli.get(0).getBeginToken().getPrevious() != null && pli.get(0).getBeginToken().getPrevious().isChar(',') && (pli.get(0).getBeginToken().getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken)) 
                    ok = false;
            }
        }
        if (!ok) 
            return null;
        if (templ.length() > 0 && !Character.isDigit(templ.charAt(templ.length() - 1))) 
            templ.setLength(templ.length() - 1);
        if ((countryCode == null && cityCode != null && cityCode.length() > 3) && num.length() > 6) {
            String cc = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(cityCode);
            if (cc != null && ((cc.length() + 1) < cityCode.length())) {
                countryCode = cc;
                cityCode = cityCode.substring(cc.length());
            }
        }
        if (pli.get(0).getBeginToken().getPrevious() != null) {
            if (pli.get(0).getBeginToken().getPrevious().isValue("ГОСТ", null) || pli.get(0).getBeginToken().getPrevious().isValue("ТУ", null)) 
                return null;
        }
        PhoneReferent ph = new PhoneReferent();
        if (countryCode != null) 
            ph.setCountryCode(countryCode);
        String number = num.toString();
        if ((cityCode == null && num.length() > 7 && partLength.size() > 0) && (partLength.get(0) < 5)) {
            cityCode = number.substring(0, 0 + partLength.get(0));
            number = number.substring(partLength.get(0));
        }
        if (cityCode == null && num.length() == 11 && num.charAt(0) == '8') {
            cityCode = number.substring(1, 1 + 3);
            number = number.substring(4);
        }
        if (cityCode == null && num.length() == 10) {
            cityCode = number.substring(0, 0 + 3);
            number = number.substring(3);
        }
        if (cityCode != null) 
            number = cityCode + number;
        else if (countryCode == null && prevPhone != null) {
            boolean ok1 = false;
            if (prevPhone.getNumber().length() >= (number.length() + 2)) 
                ok1 = true;
            else if (templ.length() > 0 && prevPhone.m_Template != null && com.pullenti.morph.LanguageHelper.endsWith(prevPhone.m_Template, templ.toString())) 
                ok1 = true;
            if (ok1 && prevPhone.getNumber().length() > number.length()) 
                number = prevPhone.getNumber().substring(0, 0 + prevPhone.getNumber().length() - number.length()) + number;
        }
        if (ph.getCountryCode() == null && prevPhone != null && prevPhone.getCountryCode() != null) {
            if (prevPhone.getNumber().length() == number.length()) 
                ph.setCountryCode(prevPhone.getCountryCode());
        }
        ok = false;
        for (char d : number.toCharArray()) {
            if (d != '0') {
                ok = true;
                break;
            }
        }
        if (!ok) 
            return null;
        if (countryCode != null) {
            if (number.length() < 7) 
                return null;
        }
        else {
            String s = com.pullenti.ner.phone.internal.PhoneHelper.getCountryPrefix(number);
            if (s != null) {
                String num2 = number.substring(s.length());
                if (num2.length() >= 10 && num2.length() <= 11) {
                    number = num2;
                    if (com.pullenti.unisharp.Utils.stringsNe(s, "7")) 
                        ph.setCountryCode(s);
                }
            }
            if (number.length() == 8 && prevPhone == null) 
                return null;
        }
        if (number.length() > 11) {
            if ((number.length() < 14) && ((com.pullenti.unisharp.Utils.stringsEq(countryCode, "1") || com.pullenti.unisharp.Utils.stringsEq(countryCode, "43")))) {
            }
            else 
                return null;
        }
        ph.setNumber(number);
        if (additional != null) 
            ph.addSlot(PhoneReferent.ATTR_ADDNUMBER, additional, true, 0);
        if (!isPhoneBefore && end.getNext() != null && !end.isNewlineAfter()) {
            if (end.getNext().isCharOf("+=") || end.getNext().isHiphen()) 
                return null;
        }
        if (countryCode != null && com.pullenti.unisharp.Utils.stringsEq(countryCode, "7")) {
            if (number.length() != 10) 
                return null;
        }
        ph.m_Template = templ.toString();
        if (j == (pli.size() - 1) && pli.get(j).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && !pli.get(j).isNewlineBefore()) {
            end = pli.get(j).getEndToken();
            if (pli.get(j).kind != PhoneKind.UNDEFINED) 
                ph.setKind(pli.get(j).kind);
            if (pli.get(j).kind2 == PhoneKind.FAX) 
                ph.tag = pli.get(j).kind2;
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(ph, pli.get(0).getBeginToken(), end, null);
        if (pli.get(0).itemType == com.pullenti.ner.phone.internal.PhoneItemToken.PhoneItemType.PREFIX && pli.get(0).getEndToken().getNext().isTableControlChar()) 
            res.setBeginToken(pli.get(1).getBeginToken());
        return res;
    }

    private PhoneReferent getNextPhone(com.pullenti.ner.Token t, int lev) {
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        if (t == null || lev > 3) 
            return null;
        java.util.ArrayList<com.pullenti.ner.phone.internal.PhoneItemToken> its = com.pullenti.ner.phone.internal.PhoneItemToken.tryAttachAll(t, 15);
        if (its == null) 
            return null;
        com.pullenti.ner.ReferentToken rt = this._TryAttach_(its, 0, false, null, lev + 1);
        if (rt == null) 
            return null;
        return (PhoneReferent)com.pullenti.unisharp.Utils.cast(rt.referent, PhoneReferent.class);
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.phone.internal.MetaPhone.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.phone.internal.PhoneHelper.initialize();
            com.pullenti.ner.phone.internal.PhoneItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new PhoneAnalyzer());
    }
    public PhoneAnalyzer() {
        super();
    }
    public static PhoneAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new PhoneAnalyzer(); } 
        catch(Exception e) { }
    }
}
