/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.money.internal;

public class MoneyMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MoneyMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_CURRENCY, "Валюта", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_VALUE, "Значение", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_REST, "Остаток (100)", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_ALTVALUE, "Другое значение", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_ALTREST, "Другой остаток (100)", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.money.MoneyReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Денежная сумма";
    }


    public static String IMAGEID = "sum";

    public static String IMAGE2ID = "sumerr";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.money.MoneyReferent m = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.money.MoneyReferent.class);
        if (m != null) {
            if (m.getAltValue() != null || m.getAltRest() != null) 
                return IMAGE2ID;
        }
        return IMAGEID;
    }

    public static MoneyMeta GLOBALMETA;
    public MoneyMeta() {
        super();
    }
}
