/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class GeoAnalyzerData extends com.pullenti.ner.core.AnalyzerDataWithOntology {

    public boolean allRegime;

    public boolean tRegime;

    public boolean cRegime;

    public boolean oRegime;

    public boolean oTRegime;

    public boolean sRegime;

    public boolean aRegime;

    public boolean checkRegime;

    public int tLevel = 0;

    public int cLevel = 0;

    public int oLevel = 0;

    public int sLevel = 0;

    public int aLevel = 0;

    public int geoBefore = 0;

    public int step;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        if (allRegime) 
            tmp.append("AllRegime ");
        if (tRegime) 
            tmp.append("TRegime ");
        if (cRegime) 
            tmp.append("CRegime ");
        if (oRegime) 
            tmp.append("ORegime ");
        if (oTRegime) 
            tmp.append("OTRegime ");
        if (sRegime) 
            tmp.append("SRegime ");
        if (aRegime) 
            tmp.append("ARegime ");
        if (checkRegime) 
            tmp.append("CheckRegime ");
        if (tLevel > 0) 
            tmp.append("TLev=").append(tLevel).append(" ");
        if (cLevel > 0) 
            tmp.append("CLev=").append(cLevel).append(" ");
        if (oLevel > 0) 
            tmp.append("OLev=").append(oLevel).append(" ");
        if (sLevel > 0) 
            tmp.append("SLev=").append(sLevel).append(" ");
        if (aLevel > 0) 
            tmp.append("ALev=").append(aLevel).append(" ");
        tmp.append(this.getReferents().size()).append(" referents");
        return tmp.toString();
    }

    private static String[] ends;

    @Override
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.geo.GeoReferent.class);
        if (g != null) {
            if (g.isState()) {
            }
            else if (g.isRegion() || ((g.isCity() && !g.isBigCity()))) {
                java.util.ArrayList<String> names = new java.util.ArrayList<String>();
                com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
                String basNam = null;
                for (com.pullenti.ner.Slot s : g.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_NAME)) 
                        names.add((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
                    else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                        String typ = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        if (com.pullenti.morph.LanguageHelper.endsWithEx(typ, "район", "край", "округ", "улус")) 
                            gen = com.pullenti.morph.MorphGender.of((short)((gen.value()) | (com.pullenti.morph.MorphGender.MASCULINE.value())));
                        else if (com.pullenti.morph.LanguageHelper.endsWithEx(typ, "область", "территория", null, null)) 
                            gen = com.pullenti.morph.MorphGender.of((short)((gen.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                    }
                }
                for (int i = 0; i < names.size(); i++) {
                    String n = names.get(i);
                    int ii = n.indexOf(' ');
                    if (ii > 0) {
                        if (g.getSlotValue(com.pullenti.ner.geo.GeoReferent.ATTR_REF) instanceof com.pullenti.ner.Referent) 
                            continue;
                        String nn = (n.substring(ii + 1) + " " + n.substring(0, 0 + ii));
                        if (!names.contains(nn)) {
                            names.add(nn);
                            g.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, nn, false, 0);
                            continue;
                        }
                        continue;
                    }
                    for (String end : ends) {
                        if (com.pullenti.morph.LanguageHelper.endsWith(n, end)) {
                            String nn = n.substring(0, 0 + n.length() - 3);
                            for (String end2 : ends) {
                                if (com.pullenti.unisharp.Utils.stringsNe(end2, end)) {
                                    if (!names.contains(nn + end2)) {
                                        names.add(nn + end2);
                                        g.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, nn + end2, false, 0);
                                    }
                                }
                            }
                            if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
                                for (String na : names) {
                                    if (com.pullenti.morph.LanguageHelper.endsWith(na, "ИЙ")) 
                                        basNam = na;
                                }
                            }
                            else if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
                                for (String na : names) {
                                    if (com.pullenti.morph.LanguageHelper.endsWith(na, "АЯ")) 
                                        basNam = na;
                                }
                            }
                            else if (gen == com.pullenti.morph.MorphGender.NEUTER) {
                                for (String na : names) {
                                    if (com.pullenti.morph.LanguageHelper.endsWith(na, "ОЕ")) 
                                        basNam = na;
                                }
                            }
                            break;
                        }
                    }
                }
                if (basNam != null && names.size() > 0 && com.pullenti.unisharp.Utils.stringsNe(names.get(0), basNam)) {
                    com.pullenti.ner.Slot sl = g.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, basNam, true);
                    if (sl != null) {
                        g.getSlots().remove(sl);
                        g.getSlots().add(0, sl);
                    }
                }
            }
        }
        return super.registerReferent(referent);
    }

    public GeoAnalyzerData() {
        super();
    }
    
    static {
        ends = new String[] {"КИЙ", "КОЕ", "КАЯ"};
    }
}
