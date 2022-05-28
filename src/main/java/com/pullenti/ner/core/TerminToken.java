/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - результат привязки термина Termin словаря TerminCollection. Формируется методом TryParse или TryParseAll у TerminCollection.
 * Токен привязки к словарю
 */
public class TerminToken extends com.pullenti.ner.MetaToken {

    public TerminToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Ссылка на термин словаря
     */
    public Termin termin;

    public boolean abridgeWithoutPoint;

    public static TerminToken _new424(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Termin _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.termin = _arg3;
        return res;
    }

    public static TerminToken _new586(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.abridgeWithoutPoint = _arg3;
        return res;
    }

    public static TerminToken _new594(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }

    public static TerminToken _new600(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, Termin _arg4) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.termin = _arg4;
        return res;
    }
    public TerminToken() {
        super();
    }
}
