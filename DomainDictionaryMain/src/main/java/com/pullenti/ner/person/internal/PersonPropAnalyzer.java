/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonPropAnalyzer extends com.pullenti.ner.Analyzer {

    public PersonPropAnalyzer() {
        super();
        setIgnoreThisAnalyzer(true);
    }

    public static final String ANALYZER_NAME = "PERSONPROPERTY";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Используется внутренним образом";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new PersonPropAnalyzer();
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, String param) {
        PersonAttrToken pat = PersonAttrToken.tryAttach(begin, PersonAttrToken.PersonAttrAttachAttrs.INPROCESS);
        if (pat != null && pat.getPropRef() != null) 
            return com.pullenti.ner.ReferentToken._new2801(pat.getPropRef(), pat.getBeginToken(), pat.getEndToken(), pat.getMorph(), pat);
        return null;
    }
}
