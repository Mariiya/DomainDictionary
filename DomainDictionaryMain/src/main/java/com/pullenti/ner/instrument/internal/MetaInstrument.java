/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class MetaInstrument extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaInstrument();
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NUMBER, "Номер", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_CASENUMBER, "Номер дела", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_DATE, "Дата", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SOURCE, "Публикующий орган", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_GEO, "Географический объект", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, "Наименование", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_CHILD, "Внутренний элемент", 0, 0).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_SIGNER, "Подписант", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PART, "Часть", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_APPENDIX, "Приложение", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_PARTICIPANT, "Участник", 0, 0).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentReferent.ATTR_ARTEFACT, "Артефакт", 0, 0).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Нормативно-правовой акт";
    }


    public static String DOCIMAGEID = "decree";

    public static String PARTIMAGEID = "part";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return DOCIMAGEID;
    }

    public static MetaInstrument GLOBALMETA;
    public MetaInstrument() {
        super();
    }
}
