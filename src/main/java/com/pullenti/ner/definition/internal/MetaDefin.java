/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition.internal;

public class MetaDefin extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaDefin();
        globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_TERMIN, "Термин", 1, 0);
        globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_TERMIN_ADD, "Дополнение термина", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_VALUE, "Значение", 1, 0);
        globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_MISC, "Мелочь", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_DECREE, "Ссылка на НПА", 0, 0);
        com.pullenti.ner.metadata.Feature fi = globalMeta.addFeature(com.pullenti.ner.definition.DefinitionReferent.ATTR_KIND, "Тип", 1, 1);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.ASSERTATION.toString(), "Утверждение", null, null);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.DEFINITION.toString(), "Определение", null, null);
        fi.addValue(com.pullenti.ner.definition.DefinitionKind.NEGATION.toString(), "Отрицание", null, null);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.definition.DefinitionReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Тезис";
    }


    public static String IMAGEDEFID = "defin";

    public static String IMAGEASSID = "assert";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.definition.DefinitionReferent) {
            com.pullenti.ner.definition.DefinitionKind ki = ((com.pullenti.ner.definition.DefinitionReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.definition.DefinitionReferent.class)).getKind();
            if (ki == com.pullenti.ner.definition.DefinitionKind.DEFINITION) 
                return IMAGEDEFID;
        }
        return IMAGEASSID;
    }

    public static MetaDefin globalMeta;
    public MetaDefin() {
        super();
    }
}
