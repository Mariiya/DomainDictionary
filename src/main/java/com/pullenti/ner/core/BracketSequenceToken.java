/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Метатокен - представление последовательности, обрамлённой кавычками (скобками)
 * Кавычки и скобки
 */
public class BracketSequenceToken extends com.pullenti.ner.MetaToken {

    public BracketSequenceToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Внутренние подпоследовательности - список BracketSequenceToken. 
     * Например, "О внесении изменений (2010-2011)", содержит внутри (2010-2011)
     */
    public java.util.ArrayList<BracketSequenceToken> internal = new java.util.ArrayList<BracketSequenceToken>();

    public boolean isQuoteType() {
        return "{([".indexOf(this.getOpenChar()) < 0;
    }


    public char getOpenChar() {
        return getBeginToken().kit.getTextCharacter(this.getBeginToken().getBeginChar());
    }


    public char getCloseChar() {
        return getEndToken().kit.getTextCharacter(this.getEndToken().getBeginChar());
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        GetTextAttr attr = GetTextAttr.NO;
        if (num == com.pullenti.morph.MorphNumber.SINGULAR) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
        else 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
        if (keepChars) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.KEEPREGISTER.value()));
        return MiscHelper.getTextValue(this.getBeginToken(), this.getEndToken(), attr);
    }
    public BracketSequenceToken() {
        super();
    }
}
