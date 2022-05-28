/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class MetaStreet extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaStreet();
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "Тип", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_KIND, "Класс", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "Наименование", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, "Номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_SECNUMBER, "Доп.номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_HIGHER, "Вышележащая улица", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_GEO, "Географический объект", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_REF, "Ссылка на связанную сущность", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_FIAS, "Объект ФИАС", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_BTI, "Объект БТИ", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.StreetReferent.ATTR_OKM, "Код ОКМ УМ", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.address.StreetReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Улица";
    }


    public static String IMAGEID = "street";

    public static String IMAGETERRID = "territory";

    public static String IMAGETERRORGID = "terrorg";

    public static String IMAGETERRSPECID = "terrspec";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.address.StreetReferent s = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.address.StreetReferent.class);
        if (s != null) {
            if (s.getKind() == com.pullenti.ner.address.StreetKind.ORG) 
                return IMAGETERRORGID;
            if (s.getKind() == com.pullenti.ner.address.StreetKind.AREA) 
                return IMAGETERRID;
            if (s.getKind() == com.pullenti.ner.address.StreetKind.SPEC) 
                return IMAGETERRSPECID;
        }
        return IMAGEID;
    }

    public static MetaStreet globalMeta;
    public MetaStreet() {
        super();
    }
}
