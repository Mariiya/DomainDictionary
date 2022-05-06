/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Поддержка работы с предлогами
 * Хелпер предлогов
 */
public class PrepositionHelper {

    /**
     * Попытаться выделить предлог с указанного токена
     * @param t начальный токен
     * @return результат или null
     */
    public static PrepositionToken tryParse(com.pullenti.ner.Token t) {
        if (!(t instanceof com.pullenti.ner.TextToken)) 
            return null;
        TerminToken tok = m_Ontology.tryParse(t, TerminParseAttr.NO);
        if (tok != null) 
            return PrepositionToken._new551(t, tok.getEndToken(), tok.termin.getCanonicText(), (com.pullenti.morph.MorphCase)tok.termin.tag);
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        if (!mc.isPreposition()) 
            return null;
        PrepositionToken res = new PrepositionToken(t, t);
        res.normal = t.getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.nextCase = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(res.normal);
        if ((t.getNext() != null && t.getNext().isHiphen() && !t.isWhitespaceAfter()) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getNext().getMorphClassInDictionary().isPreposition()) 
            res.setEndToken(t.getNext().getNext());
        return res;
    }

    private static TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new TerminCollection();
        for (String s : new String[] {"близко от", "в виде", "в зависимости от", "в интересах", "в качестве", "в лице", "в отличие от", "в отношении", "в пандан", "в пользу", "в преддверии", "в продолжение", "в результате", "в роли", "в силу", "в случае", "в течение", "в целях", "в честь", "во имя", "вплоть до", "впредь до", "за вычетом", "за исключением", "за счет", "исходя из", "на благо", "на виду у", "на глазах у", "начиная с", "невзирая на", "недалеко от", "независимо от", "от имени", "от лица", "по линии", "по мере", "по поводу", "по причине", "по случаю", "поблизости от", "под видом", "под эгидой", "при помощи", "с ведома", "с помощью", "с точки зрения", "с целью"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, com.pullenti.morph.MorphCase.GENITIVE));
        }
        for (String s : new String[] {"вдоль по", "по направлению к", "применительно к", "смотря по", "судя по"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, com.pullenti.morph.MorphCase.DATIVE));
        }
        for (String s : new String[] {"несмотря на", "с прицелом на"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, com.pullenti.morph.MorphCase.ACCUSATIVE));
        }
        for (String s : new String[] {"во славу"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, (com.pullenti.morph.MorphCase.ooBitor(com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphCase.DATIVE))));
        }
        for (String s : new String[] {"не считая"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, (com.pullenti.morph.MorphCase.ooBitor(com.pullenti.morph.MorphCase.GENITIVE, com.pullenti.morph.MorphCase.ACCUSATIVE))));
        }
        for (String s : new String[] {"в связи с", "в соответствии с", "вслед за", "лицом к лицу с", "наряду с", "по сравнению с", "рядом с", "следом за"}) {
            m_Ontology.add(Termin._new552(s.toUpperCase(), com.pullenti.morph.MorphLang.RU, true, com.pullenti.morph.MorphCase.INSTRUMENTAL));
        }
    }
    public PrepositionHelper() {
    }
}
