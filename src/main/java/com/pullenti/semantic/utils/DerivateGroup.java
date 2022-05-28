/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Дериватная группа - группа, содержащая однокоренные слова разных частей речи и языков, 
 * а также модель управления (что может идти за словом).
 * 
 * Дериватная группа
 */
public class DerivateGroup {

    /**
     * Слова дериватной группы - неупорядоченный список Words
     */
    public java.util.ArrayList<DerivateWord> words = new java.util.ArrayList<DerivateWord>();

    public String prefix;

    public boolean isDummy;

    public boolean notGenerate;

    /**
     * Группа сгенерирована на основе перебора приставок (если параметр tryVariants=true у Explanatory.FindDerivates)
     */
    public boolean isGenerated;

    /**
     * Модель управления (информация для вычисления семантических связей, что может идти за словами этой группы)
     */
    public ControlModel model = new ControlModel();

    public int lazyPos;

    public int id;

    /**
     * Содержит ли группа слово
     * @param word слово в верхнем регистре и нормальной форме
     * @param lang возможный язык
     * @return да-нет
     */
    public boolean containsWord(String word, com.pullenti.morph.MorphLang lang) {
        for (DerivateWord w : words) {
            if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                if (lang == null || lang.isUndefined() || w.lang == null) 
                    return true;
                if (!((com.pullenti.morph.MorphLang.ooBitand(lang, w.lang))).isUndefined()) 
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "?";
        if (words.size() > 0) 
            res = ("<" + words.get(0).spelling + ">");
        if (isDummy) 
            res = ("DUMMY: " + res);
        else if (isGenerated) 
            res = ("GEN: " + res);
        return res;
    }

    public DerivateGroup createByPrefix(String pref, com.pullenti.morph.MorphLang lang) {
        DerivateGroup res = _new3168(true, pref);
        for (DerivateWord w : words) {
            if (lang != null && !lang.isUndefined() && ((com.pullenti.morph.MorphLang.ooBitand(w.lang, lang))).isUndefined()) 
                continue;
            DerivateWord rw = DerivateWord._new3169(pref + w.spelling, w.lang, w._class, w.aspect, w.reflexive, w.tense, w.voice, w.attrs);
            res.words.add(rw);
        }
        return res;
    }

    public void deserialize(com.pullenti.morph.internal.ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int attr = str.deserializeShort(pos);
        if (((attr & 1)) != 0) 
            isDummy = true;
        if (((attr & 2)) != 0) 
            notGenerate = true;
        prefix = str.deserializeString(pos);
        model.deserialize(str, pos);
        int cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            DerivateWord w = new DerivateWord();
            w.spelling = str.deserializeString(pos);
            int sh = str.deserializeShort(pos);
            w._class = new com.pullenti.morph.MorphClass();
            w._class.value = (short)sh;
            sh = str.deserializeShort(pos);
            w.lang = new com.pullenti.morph.MorphLang();
            w.lang.value = (short)sh;
            sh = str.deserializeShort(pos);
            w.attrs.value = (short)sh;
            byte b = str.deserializeByte(pos);
            w.aspect = com.pullenti.morph.MorphAspect.of((short)Byte.toUnsignedInt(b));
            b = str.deserializeByte(pos);
            w.tense = com.pullenti.morph.MorphTense.of((short)Byte.toUnsignedInt(b));
            b = str.deserializeByte(pos);
            w.voice = com.pullenti.morph.MorphVoice.of((short)Byte.toUnsignedInt(b));
            b = str.deserializeByte(pos);
            int cou1 = Byte.toUnsignedInt(b);
            for (; cou1 > 0; cou1--) {
                String n = str.deserializeString(pos);
                if (w.nextWords == null) 
                    w.nextWords = new java.util.ArrayList<String>();
                if (n != null) 
                    w.nextWords.add(n);
            }
            words.add(w);
        }
    }

    public static DerivateGroup _new3168(boolean _arg1, String _arg2) {
        DerivateGroup res = new DerivateGroup();
        res.isGenerated = _arg1;
        res.prefix = _arg2;
        return res;
    }
    public DerivateGroup() {
    }
}
