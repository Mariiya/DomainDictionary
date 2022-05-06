/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods.internal;

public class GoodMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new GoodMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodReferent.ATTR_ATTR, "Атрибут", 1, 0).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.goods.GoodReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Товар";
    }


    public static String IMAGEID = "good";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static GoodMeta GLOBALMETA;
    public GoodMeta() {
        super();
    }
}
