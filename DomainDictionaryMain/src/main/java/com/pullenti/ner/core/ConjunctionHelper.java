/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Поддержка работы с союзами (запятая тоже считается союзом). Союзы могут быть из нескольких слов, 
 * например, "а также и".
 * Хелпер союзов
 */
public class ConjunctionHelper {

    /**
     * Попытаться выделить союз с указанного токена.
     * @param t начальный токен
     * @return результат или null
     */
    public static ConjunctionToken tryParse(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        if (t.isComma()) {
            ConjunctionToken ne = tryParse(t.getNext());
            if (ne != null) {
                ne.setBeginToken(t);
                ne.isSimple = false;
                return ne;
            }
            return ConjunctionToken._new493(t, t, ConjunctionType.COMMA, true, ",");
        }
        TerminToken tok = m_Ontology.tryParse(t, TerminParseAttr.NO);
        if (tok != null) {
            if (t.isValue("ТО", null)) {
                NounPhraseToken npt = NounPhraseHelper.tryParse(t, NounPhraseParseAttr.PARSEADVERBS, 0, null);
                if (npt != null && npt.getEndChar() > tok.getEndToken().getEndChar()) 
                    return null;
            }
            if (tok.termin.tag2 != null) {
                if (!(tok.getEndToken() instanceof com.pullenti.ner.TextToken)) 
                    return null;
                if (tok.getEndToken().getMorphClassInDictionary().isVerb()) {
                    if (!((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tok.getEndToken(), com.pullenti.ner.TextToken.class)).term.endsWith("АЯ")) 
                        return null;
                }
            }
            return ConjunctionToken._new494(t, tok.getEndToken(), tok.termin.getCanonicText(), (ConjunctionType)tok.termin.tag);
        }
        if (!t.getMorphClassInDictionary().isConjunction()) 
            return null;
        if (t.isAnd() || t.isOr()) {
            ConjunctionToken res = ConjunctionToken._new495(t, t, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, true, (t.isOr() ? ConjunctionType.OR : ConjunctionType.AND));
            if (((t.getNext() != null && t.getNext().isChar('(') && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().isOr() && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(')')) 
                res.setEndToken(t.getNext().getNext().getNext());
            else if ((t.getNext() != null && t.getNext().isCharOf("\\/") && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().isOr()) 
                res.setEndToken(t.getNext().getNext());
            return res;
        }
        String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
        if (com.pullenti.unisharp.Utils.stringsEq(term, "НИ")) 
            return ConjunctionToken._new494(t, t, term, ConjunctionType.NOT);
        if ((com.pullenti.unisharp.Utils.stringsEq(term, "А") || com.pullenti.unisharp.Utils.stringsEq(term, "НО") || com.pullenti.unisharp.Utils.stringsEq(term, "ЗАТО")) || com.pullenti.unisharp.Utils.stringsEq(term, "ОДНАКО")) 
            return ConjunctionToken._new494(t, t, term, ConjunctionType.BUT);
        return null;
    }

    private static TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new TerminCollection();
        Termin te;
        te = Termin._new100("ТАКЖЕ", ConjunctionType.AND);
        te.addVariant("А ТАКЖЕ", false);
        te.addVariant("КАК И", false);
        te.addVariant("ТАК И", false);
        te.addVariant("А РАВНО", false);
        te.addVariant("А РАВНО И", false);
        m_Ontology.add(te);
        te = Termin._new100("ЕСЛИ", ConjunctionType.IF);
        m_Ontology.add(te);
        te = Termin._new100("ТО", ConjunctionType.THEN);
        m_Ontology.add(te);
        te = Termin._new100("ИНАЧЕ", ConjunctionType.ELSE);
        m_Ontology.add(te);
        te = Termin._new102("ИНАЧЕ КАК", ConjunctionType.EXCEPT, true);
        te.addVariant("ИНАЧЕ, КАК", false);
        te.addVariant("ЗА ИСКЛЮЧЕНИЕМ", false);
        te.addVariant("ИСКЛЮЧАЯ", false);
        te.addAbridge("КРОМЕ");
        te.addAbridge("КРОМЕ КАК");
        te.addAbridge("КРОМЕ, КАК");
        m_Ontology.add(te);
        te = Termin._new102("ВКЛЮЧАЯ", ConjunctionType.INCLUDE, true);
        te.addVariant("В ТОМ ЧИСЛЕ", false);
        m_Ontology.add(te);
    }
    public ConjunctionHelper() {
    }
}
