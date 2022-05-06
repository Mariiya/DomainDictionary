/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class InstrumentParticipantMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new InstrumentParticipantMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_REF, "Ссылка на объект", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_DELEGATE, "Ссылка на представителя", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentParticipantReferent.ATTR_GROUND, "Основание", 0, 1).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentParticipantReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Участник";
    }


    public static String IMAGEID = "participant";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static InstrumentParticipantMeta GLOBALMETA;
    public InstrumentParticipantMeta() {
        super();
    }
}
