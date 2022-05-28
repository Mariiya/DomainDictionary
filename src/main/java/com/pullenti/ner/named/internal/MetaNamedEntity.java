/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.named.internal;

public class MetaNamedEntity extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaNamedEntity();
        GLOBALMETA.addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_KIND, "Класс", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_TYPE, "Тип", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_NAME, "Наименование", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.named.NamedEntityReferent.ATTR_REF, "Ссылка", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.named.NamedEntityReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Именованная сущность";
    }


    public static String IMAGEID = "monument";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.named.NamedEntityReferent) 
            return ((com.pullenti.ner.named.NamedEntityReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.named.NamedEntityReferent.class)).getKind().toString();
        return IMAGEID;
    }

    public static MetaNamedEntity GLOBALMETA;
    public MetaNamedEntity() {
        super();
    }
}
