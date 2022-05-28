/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class MetaDecree extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDecree();
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_TYPE, "Тип", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_NUMBER, "Номер", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_CASENUMBER, "Номер дела", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_DATE, "Дата", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_SOURCE, "Источник", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_GEO, "Географический объект", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_NAME, "Наименование", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_READING, "Чтение", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeReferent.ATTR_EDITION, "В редакции", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающий объект", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.decree.DecreeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Декрет";
    }


    public static String DECREEIMAGEID = "decree";

    public static String PUBLISHIMAGEID = "publish";

    public static String STANDADRIMAGEID = "decreestd";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.decree.DecreeReferent) {
            com.pullenti.ner.decree.DecreeKind ki = ((com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.decree.DecreeReferent.class)).getKind();
            if (ki == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                return PUBLISHIMAGEID;
            if (ki == com.pullenti.ner.decree.DecreeKind.STANDARD) 
                return STANDADRIMAGEID;
        }
        return DECREEIMAGEID;
    }

    public static MetaDecree GLOBALMETA;
    public MetaDecree() {
        super();
    }
}
