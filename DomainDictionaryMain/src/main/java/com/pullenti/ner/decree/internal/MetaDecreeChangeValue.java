/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class MetaDecreeChangeValue extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDecreeChangeValue();
        com.pullenti.ner.metadata.Feature fi = GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeValueReferent.ATTR_KIND, "Тип", 1, 1);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.TEXT.toString(), "Текст", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.WORDS.toString(), "Слова", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.ROBUSTWORDS.toString(), "Слова (неточно)", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.NUMBERS.toString(), "Цифры", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.SEQUENCE.toString(), "Предложение", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.FOOTNOTE.toString(), "Сноска", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeValueKind.BLOCK.toString(), "Блок", null, null);
        KINDFEATURE = fi;
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeValueReferent.ATTR_VALUE, "Значение", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeValueReferent.ATTR_NUMBER, "Номер", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeValueReferent.ATTR_NEWITEM, "Новый структурный элемент", 0, 0);
    }

    public static com.pullenti.ner.metadata.Feature KINDFEATURE;

    @Override
    public String getName() {
        return com.pullenti.ner.decree.DecreeChangeValueReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Значение изменения СЭ НПА";
    }


    public static String IMAGEID = "decreechangevalue";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaDecreeChangeValue GLOBALMETA;
    public MetaDecreeChangeValue() {
        super();
    }
}
