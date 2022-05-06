/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition;

// Для поддержки выделений тезисов с числовыми данными
public class DefinitionWithNumericToken extends com.pullenti.ner.MetaToken {

    public int number;

    public int numberBeginChar;

    public int numberEndChar;

    public String noun;

    public String nounsGenetive;

    public String numberSubstring;

    public String text;

    @Override
    public String toString() {
        return (((Integer)number).toString() + " " + ((noun != null ? noun : "?")) + " (" + ((nounsGenetive != null ? nounsGenetive : "?")) + ")");
    }

    public DefinitionWithNumericToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public static DefinitionWithNumericToken tryParse(com.pullenti.ner.Token t) {
        if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
            return null;
        com.pullenti.ner.Token tt = t;
        com.pullenti.ner.core.NounPhraseToken _noun = null;
        com.pullenti.ner.NumberToken num = null;
        for (; tt != null; tt = tt.getNext()) {
            if (tt != t && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                return null;
            if (!(tt instanceof com.pullenti.ner.NumberToken)) 
                continue;
            if (tt.getWhitespacesAfterCount() > 2 || tt == t) 
                continue;
            if (tt.getMorph()._getClass().isAdjective()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken nn = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (nn == null) 
                continue;
            num = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class);
            _noun = nn;
            break;
        }
        if (num == null || num.getIntValue() == null) 
            return null;
        DefinitionWithNumericToken res = new DefinitionWithNumericToken(t, _noun.getEndToken());
        res.number = num.getIntValue();
        res.numberBeginChar = num.getBeginChar();
        res.numberEndChar = num.getEndChar();
        res.noun = _noun.getNormalCaseText(null, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.nounsGenetive = (String)com.pullenti.unisharp.Utils.notnull(_noun.getMorphVariant(com.pullenti.morph.MorphCase.GENITIVE, true), (res != null ? res.noun : null));
        res.text = com.pullenti.ner.core.MiscHelper.getTextValue(t, num.getPrevious(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        if (num.isWhitespaceBefore()) 
            res.text += " ";
        res.numberSubstring = com.pullenti.ner.core.MiscHelper.getTextValue(num, _noun.getEndToken(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        res.text += res.numberSubstring;
        for (tt = _noun.getEndToken(); tt != null; tt = tt.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                break;
            res.setEndToken(tt);
        }
        if (res.getEndToken() != _noun.getEndToken()) {
            if (_noun.isWhitespaceAfter()) 
                res.text += " ";
            res.text += com.pullenti.ner.core.MiscHelper.getTextValue(_noun.getEndToken().getNext(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        }
        return res;
    }
    public DefinitionWithNumericToken() {
        super();
    }
}
