/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgGlobal {

    public static com.pullenti.ner.core.IntOntologyCollection GLOBALORGS;

    public static com.pullenti.ner.core.IntOntologyCollection GLOBALORGSUA;

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (GLOBALORGS != null) 
            return;
        GLOBALORGS = new com.pullenti.ner.core.IntOntologyCollection();
        com.pullenti.ner._org.OrganizationReferent __org;
        com.pullenti.ner.core.IntOntologyItem oi;
        try (com.pullenti.ner.Processor geoProc = com.pullenti.ner.ProcessorService.createEmptyProcessor()) {
            geoProc.addAnalyzer(new com.pullenti.ner.geo.GeoAnalyzer());
            java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent> geos = new java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent>();
            for (int k = 0; k < 3; k++) {
                com.pullenti.morph.MorphLang lang = (k == 0 ? com.pullenti.morph.MorphLang.RU : (k == 1 ? com.pullenti.morph.MorphLang.EN : com.pullenti.morph.MorphLang.UA));
                String name = (k == 0 ? "Orgs_ru.dat" : (k == 1 ? "Orgs_en.dat" : "Orgs_ua.dat"));
                byte[] dat = ResourceHelper.getBytes(name);
                if (dat == null) 
                    throw new Exception(("Can't file resource file " + name + " in Organization analyzer"));
                try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(OrgItemTypeToken.deflate(dat))) {
                    tmp.setPosition(0L);
                    com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
                    xml.load(tmp);
                    for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                        __org = new com.pullenti.ner._org.OrganizationReferent();
                        String abbr = null;
                        for (org.w3c.dom.Node xx : (new com.pullenti.unisharp.XmlNodeListWrapper(x.getChildNodes())).arr) {
                            if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "typ")) 
                                __org.addSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE, xx.getTextContent(), false, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "nam")) 
                                __org.addSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, xx.getTextContent(), false, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "epo")) 
                                __org.addSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_EPONYM, xx.getTextContent(), false, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "prof")) 
                                __org.addSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_PROFILE, xx.getTextContent(), false, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "abbr")) 
                                abbr = xx.getTextContent();
                            else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xx), "geo")) {
                                com.pullenti.ner.geo.GeoReferent _geo;
                                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent> wrapgeo1930 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent>();
                                boolean inoutres1931 = com.pullenti.unisharp.Utils.tryGetValue(geos, xx.getTextContent(), wrapgeo1930);
                                _geo = wrapgeo1930.value;
                                if (!inoutres1931) {
                                    com.pullenti.ner.AnalysisResult ar = geoProc.process(new com.pullenti.ner.SourceOfAnalysis(xx.getTextContent()), null, lang);
                                    if (ar != null && ar.getEntities().size() == 1 && (ar.getEntities().get(0) instanceof com.pullenti.ner.geo.GeoReferent)) {
                                        _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(ar.getEntities().get(0), com.pullenti.ner.geo.GeoReferent.class);
                                        geos.put(xx.getTextContent(), _geo);
                                    }
                                    else {
                                    }
                                }
                                if (_geo != null) 
                                    __org.addSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_GEO, _geo, false, 0);
                            }
                        }
                        oi = __org.createOntologyItemEx(2, true, true);
                        if (oi == null) 
                            continue;
                        if (abbr != null) 
                            oi.termins.add(new com.pullenti.ner.core.Termin(abbr, null, true));
                        if (k == 2) 
                            GLOBALORGSUA.addItem(oi);
                        else 
                            GLOBALORGS.addItem(oi);
                    }
                }
            }
        }
        return;
    }

    public OrgGlobal() {
    }
    
    static {
        GLOBALORGS = null;
        GLOBALORGSUA = new com.pullenti.ner.core.IntOntologyCollection();
    }
}
