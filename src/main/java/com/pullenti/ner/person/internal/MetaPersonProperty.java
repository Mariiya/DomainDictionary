/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class MetaPersonProperty extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaPersonProperty();
        globalMeta.addFeature(com.pullenti.ner.person.PersonPropertyReferent.ATTR_NAME, "Наименование", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonPropertyReferent.ATTR_HIGHER, "Вышестоящее свойство", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonPropertyReferent.ATTR_ATTR, "Атрибут", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, "Ссылка на объект", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающее свойство", 1, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.person.PersonPropertyReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Свойство персоны";
    }


    public static String PERSONPROPIMAGEID = "personprop";

    public static String PERSONPROPKINGIMAGEID = "king";

    public static String PERSONPROPBOSSIMAGEID = "boss";

    public static String PERSONPROPKINIMAGEID = "kin";

    public static String PERSONPROPMILITARYID = "militaryrank";

    public static String PERSONPROPNATIONID = "nationality";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.person.PersonPropertyKind ki = com.pullenti.ner.person.PersonPropertyKind.UNDEFINED;
        if (obj instanceof com.pullenti.ner.person.PersonPropertyReferent) 
            ki = ((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.person.PersonPropertyReferent.class)).getKind();
        if (ki == com.pullenti.ner.person.PersonPropertyKind.BOSS) 
            return PERSONPROPBOSSIMAGEID;
        if (ki == com.pullenti.ner.person.PersonPropertyKind.KING) 
            return PERSONPROPKINGIMAGEID;
        if (ki == com.pullenti.ner.person.PersonPropertyKind.KIN) 
            return PERSONPROPKINIMAGEID;
        if (ki == com.pullenti.ner.person.PersonPropertyKind.MILITARYRANK) 
            return PERSONPROPMILITARYID;
        if (ki == com.pullenti.ner.person.PersonPropertyKind.NATIONALITY) 
            return PERSONPROPNATIONID;
        return PERSONPROPIMAGEID;
    }

    public static MetaPersonProperty globalMeta;
    public MetaPersonProperty() {
        super();
    }
}
