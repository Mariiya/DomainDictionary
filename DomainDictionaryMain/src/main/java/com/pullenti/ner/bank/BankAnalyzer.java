/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.bank;

/**
 * Анализатор банковских данных (счетов, платёжных реквизитов...)
 */
public class BankAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("BANKDATA")
     */
    public static final String ANALYZER_NAME = "BANKDATA";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Банковские данные";
    }


    @Override
    public String getDescription() {
        return "Банковские реквизиты, счета и пр.";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new BankAnalyzer();
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.bank.internal.MetaBank.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.bank.internal.MetaBank.IMAGEID, com.pullenti.ner.bank.internal.ResourceHelper.getBytes("dollar.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, BankDataReferent.OBJ_TYPENAME)) 
            return new BankDataReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"URI", "ORGANIZATION"});
    }


    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isIgnored()) 
                continue;
            com.pullenti.ner.ReferentToken rt = null;
            if (t.chars.isLetter()) {
                com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) {
                    com.pullenti.ner.Token tt = tok.getEndToken().getNext();
                    if (tt != null && tt.isChar(':')) 
                        tt = tt.getNext();
                    rt = this.tryAttach(tt, true);
                    if (rt != null) 
                        rt.setBeginToken(t);
                }
            }
            if (rt == null && (((t instanceof com.pullenti.ner.ReferentToken) || t.isNewlineBefore()))) 
                rt = this.tryAttach(t, false);
            if (rt != null) {
                rt.referent = ad.registerReferent(rt.referent);
                kit.embedToken(rt);
                t = rt;
            }
        }
    }

    private static boolean _isBankReq(String txt) {
        if (((((((com.pullenti.unisharp.Utils.stringsEq(txt, "Р/С") || com.pullenti.unisharp.Utils.stringsEq(txt, "К/С") || com.pullenti.unisharp.Utils.stringsEq(txt, "Л/С")) || com.pullenti.unisharp.Utils.stringsEq(txt, "ОКФС") || com.pullenti.unisharp.Utils.stringsEq(txt, "ОКАТО")) || com.pullenti.unisharp.Utils.stringsEq(txt, "ОГРН") || com.pullenti.unisharp.Utils.stringsEq(txt, "БИК")) || com.pullenti.unisharp.Utils.stringsEq(txt, "SWIFT") || com.pullenti.unisharp.Utils.stringsEq(txt, "ОКПО")) || com.pullenti.unisharp.Utils.stringsEq(txt, "ОКВЭД") || com.pullenti.unisharp.Utils.stringsEq(txt, "ОКОНХ")) || com.pullenti.unisharp.Utils.stringsEq(txt, "КБК") || com.pullenti.unisharp.Utils.stringsEq(txt, "ИНН")) || com.pullenti.unisharp.Utils.stringsEq(txt, "КПП")) 
            return true;
        else 
            return false;
    }

    private com.pullenti.ner.ReferentToken tryAttach(com.pullenti.ner.Token t, boolean keyWord) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        java.util.ArrayList<String> urisKeys = null;
        java.util.ArrayList<com.pullenti.ner.uri.UriReferent> uris = null;
        com.pullenti.ner.Referent _org = null;
        com.pullenti.ner.Referent corOrg = null;
        boolean orgIsBank = false;
        int empty = 0;
        com.pullenti.ner.uri.UriReferent lastUri = null;
        for (; t != null; t = t.getNext()) {
            if (t.isTableControlChar() && t != t0) 
                break;
            if (t.isComma() || t.getMorph()._getClass().isPreposition() || t.isCharOf("/\\")) 
                continue;
            boolean bankKeyword = false;
            if (t.isValue("ПОЛНЫЙ", null) && t.getNext() != null && ((t.getNext().isValue("НАИМЕНОВАНИЕ", null) || t.getNext().isValue("НАЗВАНИЕ", null)))) {
                t = t.getNext().getNext();
                if (t == null) 
                    break;
            }
            if (t.isValue("БАНК", null)) {
                if ((t instanceof com.pullenti.ner.ReferentToken) && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "ORGANIZATION")) 
                    bankKeyword = true;
                com.pullenti.ner.Token tt = t.getNext();
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) 
                    tt = npt.getEndToken().getNext();
                if (tt != null && tt.isChar(':')) 
                    tt = tt.getNext();
                if (tt != null) {
                    if (!bankKeyword) {
                        t = tt;
                        bankKeyword = true;
                    }
                    else if (tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "ORGANIZATION")) 
                        t = tt;
                }
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) {
                boolean isBank = false;
                int kk = 0;
                for (com.pullenti.ner.Referent rr = r; rr != null && (kk < 4); rr = rr.getParentReferent(),kk++) {
                    isBank = com.pullenti.unisharp.Utils.stringsCompare((String)com.pullenti.unisharp.Utils.notnull(rr.getStringValue("KIND"), ""), "Bank", true) == 0;
                    if (isBank) 
                        break;
                }
                if (!isBank && bankKeyword) 
                    isBank = true;
                if (!isBank && uris != null && urisKeys.contains("ИНН")) 
                    return null;
                if ((lastUri != null && com.pullenti.unisharp.Utils.stringsEq(lastUri.getScheme(), "К/С") && t.getPrevious() != null) && t.getPrevious().isValue("В", null)) {
                    corOrg = r;
                    t1 = t;
                }
                else if (_org == null || ((!orgIsBank && isBank))) {
                    _org = r;
                    t1 = t;
                    orgIsBank = isBank;
                    if (isBank) 
                        continue;
                }
                if (uris == null && !keyWord) 
                    return null;
                continue;
            }
            if (r instanceof com.pullenti.ner.uri.UriReferent) {
                com.pullenti.ner.uri.UriReferent u = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.uri.UriReferent.class);
                if (uris == null) {
                    if (!_isBankReq(u.getScheme())) 
                        return null;
                    if (com.pullenti.unisharp.Utils.stringsEq(u.getScheme(), "ИНН") && t.isNewlineAfter()) 
                        return null;
                    uris = new java.util.ArrayList<com.pullenti.ner.uri.UriReferent>();
                    urisKeys = new java.util.ArrayList<String>();
                }
                else {
                    if (!_isBankReq(u.getScheme())) 
                        break;
                    if (urisKeys.contains(u.getScheme())) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsEq(u.getScheme(), "ИНН")) {
                        if (empty > 0) 
                            break;
                    }
                }
                urisKeys.add(u.getScheme());
                uris.add(u);
                lastUri = u;
                t1 = t;
                empty = 0;
                continue;
            }
            else if (uris == null && !keyWord && !orgIsBank) 
                return null;
            if (r != null && ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "GEO") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ADDRESS")))) {
                empty++;
                continue;
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.isValue("ПОЛНЫЙ", null) || t.isValue("НАИМЕНОВАНИЕ", null) || t.isValue("НАЗВАНИЕ", null)) {
                }
                else if (t.chars.isLetter()) {
                    com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok != null) {
                        t = tok.getEndToken();
                        empty = 0;
                    }
                    else {
                        empty++;
                        if (t.isNewlineBefore()) {
                            com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (nnn != null && nnn.getEndToken().getNext() != null && nnn.getEndToken().getNext().isChar(':')) 
                                break;
                        }
                    }
                    if (uris == null) 
                        break;
                }
            }
            if (empty > 2) 
                break;
            if (empty > 0 && t.isChar(':') && t.isNewlineAfter()) 
                break;
            if (((t instanceof com.pullenti.ner.NumberToken) && t.isNewlineBefore() && t.getNext() != null) && !t.getNext().chars.isLetter()) 
                break;
        }
        if (uris == null) 
            return null;
        if (!urisKeys.contains("Р/С") && !urisKeys.contains("Л/С")) 
            return null;
        boolean ok = false;
        if ((uris.size() < 2) && _org == null) 
            return null;
        BankDataReferent bdr = new BankDataReferent();
        for (com.pullenti.ner.uri.UriReferent u : uris) {
            bdr.addSlot(BankDataReferent.ATTR_ITEM, u, false, 0);
        }
        if (_org != null) 
            bdr.addSlot(BankDataReferent.ATTR_BANK, _org, false, 0);
        if (corOrg != null) 
            bdr.addSlot(BankDataReferent.ATTR_CORBANK, corOrg, false, 0);
        com.pullenti.ner.Referent org0 = (t0.getPrevious() == null ? null : t0.getPrevious().getReferent());
        if (org0 != null && com.pullenti.unisharp.Utils.stringsEq(org0.getTypeName(), "ORGANIZATION")) {
            for (com.pullenti.ner.Slot s : org0.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.uri.UriReferent) {
                    com.pullenti.ner.uri.UriReferent u = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class);
                    if (_isBankReq(u.getScheme())) {
                        if (!urisKeys.contains(u.getScheme())) 
                            bdr.addSlot(BankDataReferent.ATTR_ITEM, u, false, 0);
                    }
                }
            }
        }
        return new com.pullenti.ner.ReferentToken(bdr, t0, t1, null);
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        com.pullenti.ner.bank.internal.MetaBank.initialize();
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin("БАНКОВСКИЕ РЕКВИЗИТЫ", null, true);
        t.addVariant("ПЛАТЕЖНЫЕ РЕКВИЗИТЫ", false);
        t.addVariant("РЕКВИЗИТЫ", false);
        m_Ontology.add(t);
        com.pullenti.ner.ProcessorService.registerAnalyzer(new BankAnalyzer());
    }
    public BankAnalyzer() {
        super();
    }
}
