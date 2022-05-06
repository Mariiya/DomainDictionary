/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.denomination.internal;

public class MetaDenom extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaDenom();
        globalMeta.addFeature(com.pullenti.ner.denomination.DenominationReferent.ATTR_VALUE, "Значение", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.denomination.DenominationReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Обозначение";
    }


    public static String DENOMIMAGEID = "denom";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return DENOMIMAGEID;
    }

    public static MetaDenom globalMeta;
    public MetaDenom() {
        super();
    }
}
