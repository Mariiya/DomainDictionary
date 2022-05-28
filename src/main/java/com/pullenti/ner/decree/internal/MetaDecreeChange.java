/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class MetaDecreeChange extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDecreeChange();
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_OWNER, "Структурный элемент", 1, 0);
        com.pullenti.ner.metadata.Feature fi = GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_KIND, "Тип", 1, 1);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.APPEND.toString(), "Дополнить", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.EXPIRE.toString(), "Утратить силу", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.NEW.toString(), "В редакции", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.EXCHANGE.toString(), "Заменить", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.REMOVE.toString(), "Исключить", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.CONSIDER.toString(), "Считать", null, null);
        fi.addValue(com.pullenti.ner.decree.DecreeChangeKind.CONTAINER.toString(), "Внести изменение", null, null);
        KINDFEATURE = fi;
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_CHILD, "Дочернее изменение", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_VALUE, "Значение", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_PARAM, "Параметр", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreeChangeReferent.ATTR_MISC, "Разное", 0, 0);
    }

    public static com.pullenti.ner.metadata.Feature KINDFEATURE;

    @Override
    public String getName() {
        return com.pullenti.ner.decree.DecreeChangeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Изменение СЭ НПА";
    }


    public static String IMAGEID = "decreechange";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaDecreeChange GLOBALMETA;
    public MetaDecreeChange() {
        super();
    }
}
