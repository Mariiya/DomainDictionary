/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage;

/**
 * Анализатор титульной информации - название произведения, авторы, год и другие книжные атрибуты. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 */
public class TitlePageAnalyzer extends com.pullenti.ner.Analyzer {

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    /**
     * Имя анализатора ("TITLEPAGE")
     */
    public static final String ANALYZER_NAME = "TITLEPAGE";

    @Override
    public String getCaption() {
        return "Титульный лист";
    }


    @Override
    public String getDescription() {
        return "Информация из титульных страниц и из заголовков статей, научных работ, дипломов и т.д.";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new TitlePageAnalyzer();
    }

    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.titlepage.internal.MetaTitleInfo.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.titlepage.internal.MetaTitleInfo.TITLEINFOIMAGEID, com.pullenti.ner.booklink.internal.ResourceHelper.getBytes("titleinfo.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, TitlePageReferent.OBJ_TYPENAME)) 
            return new TitlePageReferent(null);
        return null;
    }

    public com.pullenti.ner.ReferentToken processReferent1(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        com.pullenti.ner.Token et;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Token> wrapet2878 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Token>();
        TitlePageReferent tpr = _process(begin, (end == null ? 0 : end.getEndChar()), begin.kit, wrapet2878);
        et = wrapet2878.value;
        if (tpr == null) 
            return null;
        return new com.pullenti.ner.ReferentToken(tpr, begin, et, null);
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        com.pullenti.ner.Token et;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Token> wrapet2879 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Token>();
        TitlePageReferent tpr = _process(kit.firstToken, 0, kit, wrapet2879);
        et = wrapet2879.value;
        if (tpr != null) 
            ad.registerReferent(tpr);
    }

    public static TitlePageReferent _process(com.pullenti.ner.Token begin, int maxCharPos, com.pullenti.ner.core.AnalysisKit kit, com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Token> endToken) {
        endToken.value = begin;
        TitlePageReferent res = new TitlePageReferent(null);
        com.pullenti.ner.core.Termin term = null;
        java.util.ArrayList<com.pullenti.ner.titlepage.internal.Line> lines = com.pullenti.ner.titlepage.internal.Line.parse(begin, 30, 1500, maxCharPos);
        if (lines.size() < 1) 
            return null;
        int cou = lines.size();
        int minNewlinesCount = 10;
        java.util.HashMap<Integer, Integer> linesCountStat = new java.util.HashMap<Integer, Integer>();
        for (int i = 0; i < lines.size(); i++) {
            if (com.pullenti.ner.titlepage.internal.TitleNameToken.canBeStartOfTextOrContent(lines.get(i).getBeginToken(), lines.get(i).getEndToken())) {
                cou = i;
                break;
            }
            int j = lines.get(i).getNewlinesBeforeCount();
            if (i > 0 && j > 0) {
                if (!linesCountStat.containsKey(j)) 
                    linesCountStat.put(j, 1);
                else 
                    linesCountStat.put(j, linesCountStat.get(j) + 1);
            }
        }
        int max = 0;
        for (java.util.Map.Entry<Integer, Integer> kp : linesCountStat.entrySet()) {
            if (kp.getValue() > max) {
                max = kp.getValue();
                minNewlinesCount = kp.getKey();
            }
        }
        int endChar = (cou > 0 ? lines.get(cou - 1).getEndChar() : 0);
        if (maxCharPos > 0 && endChar > maxCharPos) 
            endChar = maxCharPos;
        java.util.ArrayList<com.pullenti.ner.titlepage.internal.TitleNameToken> names = new java.util.ArrayList<com.pullenti.ner.titlepage.internal.TitleNameToken>();
        for (int i = 0; i < cou; i++) {
            if (i == 6) {
            }
            for (int j = i; (j < cou) && (j < (i + 5)); j++) {
                if (i == 6 && j == 8) {
                }
                if (j > i) {
                    if (lines.get(j - 1).isPureEn() && lines.get(j).isPureRu()) 
                        break;
                    if (lines.get(j - 1).isPureRu() && lines.get(j).isPureEn()) 
                        break;
                    if (lines.get(j).getNewlinesBeforeCount() >= (minNewlinesCount * 2)) 
                        break;
                }
                com.pullenti.ner.titlepage.internal.TitleNameToken ttt = com.pullenti.ner.titlepage.internal.TitleNameToken.tryParse(lines.get(i).getBeginToken(), lines.get(j).getEndToken(), minNewlinesCount);
                if (ttt != null) {
                    if (lines.get(i).isPureEn()) 
                        ttt.getMorph().setLanguage(com.pullenti.morph.MorphLang.EN);
                    else if (lines.get(i).isPureRu()) 
                        ttt.getMorph().setLanguage(com.pullenti.morph.MorphLang.RU);
                    names.add(ttt);
                }
            }
        }
        com.pullenti.ner.titlepage.internal.TitleNameToken.sort(names);
        com.pullenti.ner.ReferentToken nameRt = null;
        if (names.size() > 0) {
            int i0 = 0;
            if (names.get(i0).getMorph().getLanguage().isEn()) {
                for (int ii = 1; ii < names.size(); ii++) {
                    if (names.get(ii).getMorph().getLanguage().isRu() && names.get(ii).rank > 0) {
                        i0 = ii;
                        break;
                    }
                }
            }
            term = res.addName(names.get(i0).beginNameToken, names.get(i0).endNameToken);
            if (names.get(i0).typeValue != null) 
                res.addType(names.get(i0).typeValue);
            if (names.get(i0).speciality != null) 
                res.setSpeciality(names.get(i0).speciality);
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(res, names.get(i0).getBeginToken(), names.get(i0).getEndToken(), null);
            if (kit != null) 
                kit.embedToken(rt);
            else 
                res.addOccurence(new com.pullenti.ner.TextAnnotation(rt.getBeginToken(), rt.getEndToken(), null));
            endToken.value = rt.getEndToken();
            nameRt = rt;
            if (begin.getBeginChar() == rt.getBeginChar()) 
                begin = rt;
        }
        if (term != null && kit != null) {
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                com.pullenti.ner.core.TerminToken tok = term.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok == null) 
                    continue;
                com.pullenti.ner.Token t0 = t;
                com.pullenti.ner.Token t1 = tok.getEndToken();
                if (t1.getNext() != null && t1.getNext().isChar('.')) 
                    t1 = t1.getNext();
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0.getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) {
                    t0 = t0.getPrevious();
                    t1 = t1.getNext();
                }
                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(res, t0, t1, null);
                kit.embedToken(rt);
                t = rt;
            }
        }
        com.pullenti.ner.titlepage.internal.PersonRelations pr = new com.pullenti.ner.titlepage.internal.PersonRelations();
        com.pullenti.ner.titlepage.internal.TitleItemToken.Types persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED;
        java.util.ArrayList<com.pullenti.ner.titlepage.internal.TitleItemToken.Types> persTypes = pr.getRelTypes();
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            if (maxCharPos > 0 && t.getBeginChar() > maxCharPos) 
                break;
            if (t == nameRt) 
                continue;
            com.pullenti.ner.titlepage.internal.TitleItemToken tpt = com.pullenti.ner.titlepage.internal.TitleItemToken.tryAttach(t);
            if (tpt != null) {
                persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED;
                if (tpt.typ == com.pullenti.ner.titlepage.internal.TitleItemToken.Types.TYP) {
                    if (res.getTypes().size() == 0) 
                        res.addType(tpt.value);
                    else if (res.getTypes().size() == 1) {
                        String ty = res.getTypes().get(0).toUpperCase();
                        if (com.pullenti.unisharp.Utils.stringsEq(ty, "РЕФЕРАТ")) 
                            res.addType(tpt.value);
                        else if (com.pullenti.unisharp.Utils.stringsEq(ty, "АВТОРЕФЕРАТ")) {
                            if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "КАНДИДАТСКАЯ ДИССЕРТАЦИЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат кандидатской диссертации", true, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "ДОКТОРСКАЯ ДИССЕРТАЦИЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат докторской диссертации", true, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "МАГИСТЕРСКАЯ ДИССЕРТАЦИЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат магистерской диссертации", true, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "КАНДИДАТСЬКА ДИСЕРТАЦІЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат кандидатської дисертації", true, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "ДОКТОРСЬКА ДИСЕРТАЦІЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат докторської дисертації", true, 0);
                            else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "МАГІСТЕРСЬКА ДИСЕРТАЦІЯ")) 
                                res.addSlot(TitlePageReferent.ATTR_TYPE, "автореферат магістерської дисертації", true, 0);
                            else 
                                res.addType(tpt.value);
                        }
                        else if (com.pullenti.unisharp.Utils.stringsEq(tpt.value, "РЕФЕРАТ") || com.pullenti.unisharp.Utils.stringsEq(tpt.value, "АВТОРЕФЕРАТ")) {
                            if (!(ty.indexOf(tpt.value) >= 0)) 
                                res.addType(tpt.value);
                        }
                    }
                }
                else if (tpt.typ == com.pullenti.ner.titlepage.internal.TitleItemToken.Types.SPECIALITY) {
                    if (res.getSpeciality() == null) 
                        res.setSpeciality(tpt.value);
                }
                else if (persTypes.contains(tpt.typ)) 
                    persTyp = tpt.typ;
                t = tpt.getEndToken();
                if (t.getEndChar() > endToken.value.getEndChar()) 
                    endToken.value = t;
                if (t.getNext() != null && t.getNext().isCharOf(":-")) 
                    t = t.getNext();
                continue;
            }
            if (t.getEndChar() > endChar) 
                break;
            java.util.ArrayList<com.pullenti.ner.Referent> rli = t.getReferents();
            if (rli == null) 
                continue;
            if (!t.isNewlineBefore() && (t.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                String s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.TextToken.class)).term;
                if (com.pullenti.unisharp.Utils.stringsEq(s, "ИМЕНИ") || com.pullenti.unisharp.Utils.stringsEq(s, "ИМ")) 
                    continue;
                if (com.pullenti.unisharp.Utils.stringsEq(s, ".") && t.getPrevious().getPrevious() != null && t.getPrevious().getPrevious().isValue("ИМ", null)) 
                    continue;
            }
            for (com.pullenti.ner.Referent r : rli) {
                if (r instanceof com.pullenti.ner.person.PersonReferent) {
                    if (r != rli.get(0)) 
                        continue;
                    com.pullenti.ner.person.PersonReferent p = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.person.PersonReferent.class);
                    if (persTyp != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) {
                        if (t.getPrevious() != null && t.getPrevious().isChar('.')) 
                            persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED;
                    }
                    com.pullenti.ner.titlepage.internal.TitleItemToken.Types typ = pr.calcTypFromAttrs(p);
                    if (typ != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) {
                        pr.add(p, typ, 1.0F);
                        persTyp = typ;
                    }
                    else if (persTyp != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) 
                        pr.add(p, persTyp, 1.0F);
                    else if (t.getPrevious() != null && t.getPrevious().isChar('©')) {
                        persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.WORKER;
                        pr.add(p, persTyp, 1.0F);
                    }
                    else {
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                            com.pullenti.ner.Referent rr = tt.getReferent();
                            if (rr == res) {
                                persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.WORKER;
                                break;
                            }
                            if (rr instanceof com.pullenti.ner.person.PersonReferent) {
                                if (pr.calcTypFromAttrs((com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.person.PersonReferent.class)) != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) 
                                    break;
                                else 
                                    continue;
                            }
                            if (rr != null) 
                                break;
                            tpt = com.pullenti.ner.titlepage.internal.TitleItemToken.tryAttach(tt);
                            if (tpt != null) {
                                if (tpt.typ != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.TYP && tpt.typ != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.TYPANDTHEME) 
                                    break;
                                tt = tpt.getEndToken();
                                if (tt.getEndChar() > endToken.value.getEndChar()) 
                                    endToken.value = tt;
                                continue;
                            }
                        }
                        if (persTyp == com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) {
                            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                                com.pullenti.ner.Referent rr = tt.getReferent();
                                if (rr == res) {
                                    persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.WORKER;
                                    break;
                                }
                                if (rr != null) 
                                    break;
                                if ((tt.isValue("СТУДЕНТ", null) || tt.isValue("СТУДЕНТКА", null) || tt.isValue("СЛУШАТЕЛЬ", null)) || tt.isValue("ДИПЛОМНИК", null) || tt.isValue("ИСПОЛНИТЕЛЬ", null)) {
                                    persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.WORKER;
                                    break;
                                }
                                tpt = com.pullenti.ner.titlepage.internal.TitleItemToken.tryAttach(tt);
                                if (tpt != null && tpt.typ != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.TYP) 
                                    break;
                            }
                        }
                        if (persTyp != com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED) 
                            pr.add(p, persTyp, 1.0F);
                        else 
                            pr.add(p, persTyp, (float)0.5);
                        if (t.getEndChar() > endToken.value.getEndChar()) 
                            endToken.value = t;
                    }
                    continue;
                }
                if (r == rli.get(0)) 
                    persTyp = com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED;
                if (r instanceof com.pullenti.ner.date.DateReferent) {
                    if (res.getDate() == null) {
                        res.setDate((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.date.DateReferent.class));
                        if (t.getEndChar() > endToken.value.getEndChar()) 
                            endToken.value = t;
                    }
                }
                else if (r instanceof com.pullenti.ner.geo.GeoReferent) {
                    if (res.getCity() == null && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class)).isCity()) {
                        res.setCity((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class));
                        if (t.getEndChar() > endToken.value.getEndChar()) 
                            endToken.value = t;
                    }
                }
                if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                    com.pullenti.ner._org.OrganizationReferent __org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class);
                    if (__org.getTypes().contains("курс") && __org.getNumber() != null) {
                        int i;
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapi2880 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        boolean inoutres2881 = com.pullenti.unisharp.Utils.parseInteger(__org.getNumber(), 0, null, wrapi2880);
                        i = (wrapi2880.value != null ? wrapi2880.value : 0);
                        if (inoutres2881) {
                            if (i > 0 && (i < 8)) 
                                res.setStudentYear(i);
                        }
                    }
                    for (; __org.getHigher() != null; __org = __org.getHigher()) {
                        if (__org.getKind() != com.pullenti.ner._org.OrganizationKind.DEPARTMENT) 
                            break;
                    }
                    if (__org.getKind() != com.pullenti.ner._org.OrganizationKind.DEPARTMENT) {
                        if (res.getOrg() == null) 
                            res.setOrg(__org);
                        else if (com.pullenti.ner._org.OrganizationReferent.canBeHigher(res.getOrg(), __org)) 
                            res.setOrg(__org);
                    }
                    if (t.getEndChar() > endToken.value.getEndChar()) 
                        endToken.value = t;
                }
                if ((r instanceof com.pullenti.ner.uri.UriReferent) || (r instanceof com.pullenti.ner.geo.GeoReferent)) {
                    if (t.getEndChar() > endToken.value.getEndChar()) 
                        endToken.value = t;
                }
            }
        }
        for (com.pullenti.ner.titlepage.internal.TitleItemToken.Types ty : persTypes) {
            for (com.pullenti.ner.person.PersonReferent p : pr.getPersons(ty)) {
                if (pr.getAttrNameForType(ty) != null) 
                    res.addSlot(pr.getAttrNameForType(ty), p, false, 0);
            }
        }
        if (res.getSlotValue(TitlePageReferent.ATTR_AUTHOR) == null) {
            for (com.pullenti.ner.person.PersonReferent p : pr.getPersons(com.pullenti.ner.titlepage.internal.TitleItemToken.Types.UNDEFINED)) {
                res.addSlot(TitlePageReferent.ATTR_AUTHOR, p, false, 0);
                break;
            }
        }
        if (res.getCity() == null && res.getOrg() != null) {
            com.pullenti.ner.Slot s = res.getOrg().findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_GEO, null, true);
            if (s != null && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                if (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class)).isCity()) 
                    res.setCity((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            }
        }
        if (res.getDate() == null) {
            for (com.pullenti.ner.Token t = begin; t != null && t.getEndChar() <= endChar; t = t.getNext()) {
                com.pullenti.ner.geo.GeoReferent city = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (city == null) 
                    continue;
                if (t.getNext() instanceof com.pullenti.ner.TextToken) {
                    if (t.getNext().isCharOf(":,") || t.getNext().isHiphen()) 
                        t = t.getNext();
                }
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent(com.pullenti.ner.date.DateAnalyzer.ANALYZER_NAME, t.getNext(), null);
                if (rt != null) {
                    rt.saveToLocalOntology();
                    res.setDate((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.date.DateReferent.class));
                    if (kit != null) 
                        kit.embedToken(rt);
                    break;
                }
            }
        }
        if (res.getSlots().size() == 0) 
            return null;
        else 
            return res;
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.titlepage.internal.MetaTitleInfo.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.titlepage.internal.TitleItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new TitlePageAnalyzer());
    }
    public TitlePageAnalyzer() {
        super();
    }
}
