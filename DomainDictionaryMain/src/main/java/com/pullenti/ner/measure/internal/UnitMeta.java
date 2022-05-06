/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.measure.internal;

public class UnitMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new UnitMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_NAME, "Краткое наименование", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_FULLNAME, "Полное наименование", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_POW, "Степень", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_BASEFACTOR, "Мультипликатор для базовой единицы", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_BASEUNIT, "Базовая единица", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.UnitReferent.ATTR_UNKNOWN, "Неизвестная метрика", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.measure.UnitReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Единицы измерения";
    }


    public static String IMAGEID = "munit";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static UnitMeta GLOBALMETA;
    public UnitMeta() {
        super();
    }
}
