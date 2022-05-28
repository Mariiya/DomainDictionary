/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address;

/**
 * Анализатор адресов
 */
public class AddressAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("ADDRESS")
     */
    public static final String ANALYZER_NAME = "ADDRESS";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Адреса";
    }


    @Override
    public String getDescription() {
        return "Адреса (улицы, дома ...)";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new AddressAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.address.internal.MetaAddress.globalMeta, com.pullenti.ner.address.internal.MetaStreet.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.address.internal.MetaAddress.ADDRESSIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("address.png"));
        res.put(com.pullenti.ner.address.internal.MetaStreet.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("street.png"));
        res.put(com.pullenti.ner.address.internal.MetaStreet.IMAGETERRORGID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("terrorg.png"));
        res.put(com.pullenti.ner.address.internal.MetaStreet.IMAGETERRSPECID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("terrspec.png"));
        res.put(com.pullenti.ner.address.internal.MetaStreet.IMAGETERRID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("territory.png"));
        return res;
    }


    @Override
    public int getProgressWeight() {
        return 4;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, AddressReferent.OBJ_TYPENAME)) 
            return new AddressReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, StreetReferent.OBJ_TYPENAME)) 
            return new StreetReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, "PHONE", "URI"});
    }


    public static class AddressAnalyzerData extends com.pullenti.ner.core.AnalyzerData {
    
        private com.pullenti.ner.core.AnalyzerData m_Addresses = new com.pullenti.ner.core.AnalyzerData();
    
        public com.pullenti.ner.core.AnalyzerDataWithOntology streets = new com.pullenti.ner.core.AnalyzerDataWithOntology();
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            if (referent instanceof com.pullenti.ner.address.StreetReferent) {
                ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.address.StreetReferent.class)).correct();
                return streets.registerReferent(referent);
            }
            else 
                return m_Addresses.registerReferent(referent);
        }
    
        @Override
        public java.util.Collection<com.pullenti.ner.Referent> getReferents() {
            if (streets.getReferents().size() == 0) 
                return m_Addresses.getReferents();
            else if (m_Addresses.getReferents().size() == 0) 
                return streets.getReferents();
            java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>(streets.getReferents());
            com.pullenti.unisharp.Utils.addToArrayList(res, m_Addresses.getReferents());
            return res;
        }
    
        public AddressAnalyzerData() {
            super();
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new AddressAnalyzerData();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        AddressAnalyzerData ad = (AddressAnalyzerData)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), AddressAnalyzerData.class);
        com.pullenti.ner.geo.internal.GeoAnalyzerData gad = com.pullenti.ner.geo.GeoAnalyzer.getData(kit.firstToken);
        if (gad == null) 
            return;
        gad.allRegime = true;
        int steps = 1;
        int max = steps;
        int delta = 100000;
        int parts = (((kit.getSofa().getText().length() + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        max *= parts;
        int cur = 0;
        int nextPos = delta;
        com.pullenti.unisharp.Stopwatch sw = new com.pullenti.unisharp.Stopwatch();
        sw.reset();
        sw.start();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.getBeginChar() > nextPos) {
                nextPos += delta;
                cur++;
                if (!this.onProgress(cur, max, kit)) 
                    return;
            }
            java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken> li = com.pullenti.ner.address.internal.AddressItemToken.tryParseList(t, 20);
            if (li == null || li.size() == 0) 
                continue;
            if ((li.size() == 1 && li.get(0).getTyp() == com.pullenti.ner.address.internal.AddressItemType.STREET && ((StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, StreetReferent.class)).getKind() == StreetKind.RAILWAY) && ((StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, StreetReferent.class)).getNumber() == null) {
                t = li.get(0).getEndToken();
                continue;
            }
            com.pullenti.ner.Token tt = com.pullenti.ner.address.internal.AddressDefineHelper.tryDefine(li, t, ad);
            if (tt != null) 
                t = tt;
        }
        sw.stop();
        java.util.ArrayList<StreetReferent> sli = new java.util.ArrayList<StreetReferent>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = (t == null ? null : t.getNext())) {
            StreetReferent sr = (StreetReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), StreetReferent.class);
            if (sr == null) 
                continue;
            if (t.getNext() == null || !t.getNext().isCommaAnd()) 
                continue;
            sli.clear();
            sli.add(sr);
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isCommaAnd()) 
                    continue;
                if ((((sr = (StreetReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), StreetReferent.class)))) != null) {
                    sli.add(sr);
                    continue;
                }
                AddressReferent adr = (AddressReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), AddressReferent.class);
                if (adr == null) 
                    break;
                if (adr.getStreets().size() == 0) 
                    break;
                for (com.pullenti.ner.Referent ss : adr.getStreets()) {
                    if (ss instanceof StreetReferent) 
                        sli.add((StreetReferent)com.pullenti.unisharp.Utils.cast(ss, StreetReferent.class));
                }
            }
            if (sli.size() < 2) 
                continue;
            boolean ok = true;
            com.pullenti.ner.geo.GeoReferent hi = null;
            for (StreetReferent s : sli) {
                if (s.getGeos().size() == 0) 
                    continue;
                else if (s.getGeos().size() == 1) {
                    if (hi == null || hi == s.getGeos().get(0)) 
                        hi = s.getGeos().get(0);
                    else {
                        ok = false;
                        break;
                    }
                }
                else {
                    ok = false;
                    break;
                }
            }
            if (ok && hi != null) {
                for (StreetReferent s : sli) {
                    if (s.getGeos().size() == 0) 
                        s.addSlot(StreetReferent.ATTR_GEO, hi, false, 0);
                }
            }
        }
        for (com.pullenti.ner.Referent a : ad.getReferents()) {
            if (a instanceof AddressReferent) 
                ((AddressReferent)com.pullenti.unisharp.Utils.cast(a, AddressReferent.class)).correct();
        }
        gad.allRegime = false;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> li = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(begin, 10, null);
        if (li == null || (li.size() < 2)) 
            return null;
        com.pullenti.ner.address.internal.AddressItemToken rt = com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(li, true, false, false);
        if (rt == null) 
            return null;
        StreetReferent street = (StreetReferent)com.pullenti.unisharp.Utils.cast(rt.referent, StreetReferent.class);
        for (com.pullenti.ner.Token t = rt.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (!t.isChar(';')) 
                continue;
            t = t.getNext();
            if (t == null) 
                break;
            li = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(begin, 10, null);
            com.pullenti.ner.address.internal.AddressItemToken rt1 = com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(li, true, false, false);
            if (rt1 != null) {
                t = rt.setEndToken(rt1.getEndToken());
                street.mergeSlots(rt1.referent, true);
            }
            else {
                com.pullenti.ner.Token tt = null;
                for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isChar(';')) 
                        break;
                    else 
                        tt = ttt;
                }
                if (tt != null) {
                    String str = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt, com.pullenti.ner.core.GetTextAttr.NO);
                    if (str != null) 
                        street.addSlot(StreetReferent.ATTR_NAME, com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str), false, 0);
                    t = rt.setEndToken(tt);
                }
            }
        }
        return new com.pullenti.ner.ReferentToken(street, rt.getBeginToken(), rt.getEndToken(), null);
    }

    private static boolean m_Initialized = false;

    public static void initialize() throws Exception {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        try {
            com.pullenti.ner.address.internal.AddressItemToken.initialize();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new AddressAnalyzer());
    }
    public AddressAnalyzer() {
        super();
    }
    public static AddressAnalyzer _globalInstance;
    
    static {
        try { _globalInstance = new AddressAnalyzer(); } 
        catch(Exception e) { }
    }
}
