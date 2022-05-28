/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.phone.internal;

public class MetaPhone extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaPhone();
        globalMeta.addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_NUNBER, "Номер", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_ADDNUMBER, "Добавочный номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_COUNTRYCODE, "Код страны", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающий номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_KIND, "Тип", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.phone.PhoneReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Телефонный номер";
    }


    public static String PHONEIMAGEID = "phone";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return PHONEIMAGEID;
    }

    public static MetaPhone globalMeta;
    public MetaPhone() {
        super();
    }
}
