/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class MetaPersonIdentity extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaPersonIdentity();
        globalMeta.addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_TYPE, "Тип", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_NUMBER, "Номер", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_DATE, "Дата выдачи", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ORG, "Кто выдал", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ADDRESS, "Адрес регистрации", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.person.PersonIdentityReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Удостоверение личности";
    }


    public static String IMAGEID = "identity";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaPersonIdentity globalMeta;
    public MetaPersonIdentity() {
        super();
    }
}
