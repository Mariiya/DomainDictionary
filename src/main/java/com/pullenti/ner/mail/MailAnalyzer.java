/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.mail;

/**
 * Анализатор текстов электронных писем и их блоков. Восстановление структуры, разбиение на блоки, 
 * анализ блока подписи. 
 * Специфический анализатор, то есть нужно явно создавать процессор через функцию CreateSpecificProcessor, 
 * указав имя анализатора.
 */
public class MailAnalyzer extends com.pullenti.ner.Analyzer {

    /**
     * Имя анализатора ("MAIL")
     */
    public static final String ANALYZER_NAME = "MAIL";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Блок письма";
    }


    @Override
    public String getDescription() {
        return "Блоки писем (e-mail) и их атрибуты";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new MailAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.metadata.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.metadata.ReferentClass[] {com.pullenti.ner.mail.internal.MetaLetter.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.mail.internal.MetaLetter.IMAGEID, com.pullenti.ner.person.internal.ResourceHelper.getBytes("mail.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, MailReferent.OBJ_TYPENAME)) 
            return new MailReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ORGANIZATION", "GEO", "ADDRESS", "PERSON"});
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
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine> lines = new java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.mail.internal.MailLine ml = com.pullenti.ner.mail.internal.MailLine.parse(t, 0, 0);
            if (ml == null) 
                continue;
            if (lines.size() == 91) {
            }
            lines.add(ml);
            t = ml.getEndToken();
        }
        if (lines.size() == 0) 
            return;
        int i;
        java.util.ArrayList<java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine>> blocks = new java.util.ArrayList<java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine>>();
        java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine> blk = null;
        for (i = 0; i < lines.size(); i++) {
            com.pullenti.ner.mail.internal.MailLine ml = lines.get(i);
            if (ml.typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                boolean isNew = ml.mustBeFirstLine || i == 0;
                if (((i + 2) < lines.size()) && (((lines.get(i + 1).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM || lines.get(i + 2).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM || lines.get(i + 1).typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO) || lines.get(i + 2).typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO))) 
                    isNew = true;
                if (!isNew) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (lines.get(j).typ != com.pullenti.ner.mail.internal.MailLine.Types.UNDEFINED) {
                            if (lines.get(j).typ == com.pullenti.ner.mail.internal.MailLine.Types.BESTREGARDS) 
                                isNew = true;
                            break;
                        }
                    }
                }
                if (!isNew) {
                    for (com.pullenti.ner.Token tt = ml.getBeginToken(); tt != null && tt.getEndChar() <= ml.getEndChar(); tt = tt.getNext()) {
                        if (tt.getReferent() != null) {
                            if (com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "DATE") || com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "URI")) 
                                isNew = true;
                        }
                    }
                }
                if (isNew) {
                    blk = new java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine>();
                    blocks.add(blk);
                    for (; i < lines.size(); i++) {
                        if (lines.get(i).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                            if (blk.size() > 0 && lines.get(i).mustBeFirstLine) 
                                break;
                            blk.add(lines.get(i));
                        }
                        else if (((i + 1) < lines.size()) && lines.get(i + 1).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                            int j;
                            for (j = 0; j < blk.size(); j++) {
                                if (blk.get(j).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                                    if (blk.get(j).isRealFrom() || blk.get(j).mustBeFirstLine || blk.get(j).getMailAddr() != null) 
                                        break;
                                }
                            }
                            if (j >= blk.size()) {
                                blk.add(lines.get(i));
                                continue;
                            }
                            boolean ok = false;
                            for (j = i + 1; j < lines.size(); j++) {
                                if (lines.get(j).typ != com.pullenti.ner.mail.internal.MailLine.Types.FROM) 
                                    break;
                                if (lines.get(j).isRealFrom() || lines.get(j).mustBeFirstLine) {
                                    ok = true;
                                    break;
                                }
                                if (lines.get(j).getMailAddr() != null) {
                                    ok = true;
                                    break;
                                }
                            }
                            if (ok) 
                                break;
                            blk.add(lines.get(i));
                        }
                        else 
                            break;
                    }
                    i--;
                    continue;
                }
            }
            if (blk == null) 
                blocks.add((blk = new java.util.ArrayList<com.pullenti.ner.mail.internal.MailLine>()));
            blk.add(lines.get(i));
        }
        if (blocks.size() == 0) 
            return;
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for (int j = 0; j < blocks.size(); j++) {
            lines = blocks.get(j);
            if (lines.size() == 0) 
                continue;
            i = 0;
            if (lines.get(0).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                com.pullenti.ner.Token t1 = lines.get(0).getEndToken();
                for (; i < lines.size(); i++) {
                    if (lines.get(i).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) 
                        t1 = lines.get(i).getEndToken();
                    else if (((i + 1) < lines.size()) && lines.get(i + 1).typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) {
                    }
                    else 
                        break;
                }
                MailReferent _mail = MailReferent._new1759(MailKind.HEAD);
                com.pullenti.ner.ReferentToken mt = new com.pullenti.ner.ReferentToken(_mail, lines.get(0).getBeginToken(), t1, null);
                _mail.setText(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(mt, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                ad.registerReferent(_mail);
                _mail.addOccurenceOfRefTok(mt);
            }
            int i0 = i;
            com.pullenti.ner.Token t2 = null;
            int err = 0;
            for (i = lines.size() - 1; i >= i0; i--) {
                com.pullenti.ner.mail.internal.MailLine li = lines.get(i);
                if (li.typ == com.pullenti.ner.mail.internal.MailLine.Types.BESTREGARDS) {
                    t2 = lines.get(i).getBeginToken();
                    for (--i; i >= i0; i--) {
                        if (lines.get(i).typ == com.pullenti.ner.mail.internal.MailLine.Types.BESTREGARDS && (lines.get(i).getWords() < 2)) 
                            t2 = lines.get(i).getBeginToken();
                        else if ((i > i0 && (lines.get(i).getWords() < 3) && lines.get(i - 1).typ == com.pullenti.ner.mail.internal.MailLine.Types.BESTREGARDS) && (lines.get(i - 1).getWords() < 2)) {
                            i--;
                            t2 = lines.get(i).getBeginToken();
                        }
                        else 
                            break;
                    }
                    break;
                }
                if (li.refs.size() > 0 && (li.getWords() < 3) && i > i0) {
                    err = 0;
                    t2 = li.getBeginToken();
                    continue;
                }
                if (li.getWords() > 10) {
                    t2 = null;
                    continue;
                }
                if (li.getWords() > 2) {
                    if ((++err) > 2) 
                        t2 = null;
                }
            }
            if (t2 == null) {
                for (i = lines.size() - 1; i >= i0; i--) {
                    com.pullenti.ner.mail.internal.MailLine li = lines.get(i);
                    if (li.typ == com.pullenti.ner.mail.internal.MailLine.Types.UNDEFINED) {
                        if (li.refs.size() > 0 && (li.refs.get(0) instanceof com.pullenti.ner.person.PersonReferent)) {
                            if (li.getWords() == 0 && i > i0) {
                                t2 = li.getBeginToken();
                                break;
                            }
                        }
                    }
                }
            }
            for (int ii = i0; ii < lines.size(); ii++) {
                if (lines.get(ii).typ == com.pullenti.ner.mail.internal.MailLine.Types.HELLO) {
                    MailReferent _mail = MailReferent._new1759(MailKind.HELLO);
                    com.pullenti.ner.ReferentToken mt = new com.pullenti.ner.ReferentToken(_mail, lines.get(i0).getBeginToken(), lines.get(ii).getEndToken(), null);
                    if (mt.getLengthChar() > 0) {
                        _mail.setText(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(mt, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                        ad.registerReferent(_mail);
                        _mail.addOccurenceOfRefTok(mt);
                        i0 = ii + 1;
                    }
                    break;
                }
                else if (lines.get(ii).typ != com.pullenti.ner.mail.internal.MailLine.Types.UNDEFINED || lines.get(ii).getWords() > 0 || lines.get(ii).refs.size() > 0) 
                    break;
            }
            if (i0 < lines.size()) {
                if (t2 != null && t2.getPrevious() == null) {
                }
                else {
                    MailReferent _mail = MailReferent._new1759(MailKind.BODY);
                    com.pullenti.ner.ReferentToken mt = new com.pullenti.ner.ReferentToken(_mail, lines.get(i0).getBeginToken(), (t2 != null && t2.getPrevious() != null ? t2.getPrevious() : lines.get(lines.size() - 1).getEndToken()), null);
                    if (mt.getLengthChar() > 0) {
                        _mail.setText(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(mt, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                        ad.registerReferent(_mail);
                        _mail.addOccurenceOfRefTok(mt);
                    }
                }
                if (t2 != null) {
                    MailReferent _mail = MailReferent._new1759(MailKind.TAIL);
                    com.pullenti.ner.ReferentToken mt = new com.pullenti.ner.ReferentToken(_mail, t2, lines.get(lines.size() - 1).getEndToken(), null);
                    if (mt.getLengthChar() > 0) {
                        _mail.setText(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(mt, com.pullenti.ner.core.GetTextAttr.KEEPREGISTER));
                        ad.registerReferent(_mail);
                        _mail.addOccurenceOfRefTok(mt);
                    }
                    for (i = i0; i < lines.size(); i++) {
                        if (lines.get(i).getBeginChar() >= t2.getBeginChar()) {
                            for (com.pullenti.ner.Referent r : lines.get(i).refs) {
                                _mail.addRef(r, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        try {
            com.pullenti.ner.mail.internal.MetaLetter.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.mail.internal.MailLine.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new MailAnalyzer());
    }
    public MailAnalyzer() {
        super();
    }
}
