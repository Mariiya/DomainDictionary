/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

public class MetaDateRange extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDateRange();
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateRangeReferent.ATTR_FROM, "Начало периода", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateRangeReferent.ATTR_TO, "Конец периода", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.date.DateRangeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Период";
    }


    public static String DATERANGEIMAGEID = "daterange";

    public static String DATERANGERELIMAGEID = "daterangerel";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.date.DateRangeReferent) {
            if (((com.pullenti.ner.date.DateRangeReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.date.DateRangeReferent.class)).isRelative()) 
                return DATERANGERELIMAGEID;
        }
        return DATERANGEIMAGEID;
    }

    public static MetaDateRange GLOBALMETA;
    public MetaDateRange() {
        super();
    }
}
