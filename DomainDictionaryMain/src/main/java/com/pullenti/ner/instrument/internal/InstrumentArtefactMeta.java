/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class InstrumentArtefactMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new InstrumentArtefactMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentArtefactReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentArtefactReferent.ATTR_VALUE, "Значение", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentArtefactReferent.ATTR_REF, "Ссылка на объект", 0, 1).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentParticipantReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Артефакт";
    }


    public static String IMAGEID = "artefact";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static InstrumentArtefactMeta GLOBALMETA;
    public InstrumentArtefactMeta() {
        super();
    }
}
